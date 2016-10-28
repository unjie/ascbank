/*
Project: angular-gantt v1.2.14 - Gantt chart component for AngularJS
Authors: Marco Schweighauser, Rémi Alvergnat
License: MIT
Homepage: https://www.angular-gantt.com
Github: https://github.com/angular-gantt/angular-gantt.git
*/
(function(){
    'use strict';
    angular.module('gantt', ['gantt.templates', 'angularMoment'])
        .directive('gantt', ['Gantt', 'ganttEnableNgAnimate', '$timeout', '$templateCache', function(Gantt, enableNgAnimate, $timeout, $templateCache) {
        return {
            restrict: 'A',
            transclude: true,
            templateUrl: function(tElement, tAttrs) {
                var templateUrl;
                if (tAttrs.templateUrl === undefined) {
                    templateUrl = 'template/gantt.tmpl.html';
                } else {
                    templateUrl = tAttrs.templateUrl;
                }
                if (tAttrs.template !== undefined) {
                    $templateCache.put(templateUrl, tAttrs.template);
                }
                return templateUrl;
            },
            scope: {
                sortMode: '=?',
                filterTask: '=?',
                filterTaskComparator: '=?',
                filterRow: '=?',
                filterRowComparator: '=?',
                viewScale: '=?',
                columnWidth: '=?',
                expandToFit: '=?',
                shrinkToFit: '=?',
                showSide: '=?',
                allowSideResizing: '=?',
                fromDate: '=?',
                toDate: '=?',
                currentDateValue: '=?',
                currentDate: '=?',
                daily: '=?',
                autoExpand: '=?',
                taskOutOfRange: '=?',
                taskContent: '=?',
                rowContent: '=?',
                maxHeight: '=?',
                sideWidth: '=?',
                headers: '=?',
                headersFormats: '=?',
                headersScales: '=?',
                timeFrames: '=?',
                dateFrames: '=?',
                timeFramesWorkingMode: '=?',
                timeFramesNonWorkingMode: '=?',
                timespans: '=?',
                columnMagnet: '=?',
                shiftColumnMagnet: '=?',
                timeFramesMagnet: '=?',
                data: '=?',
                api: '=?',
                options: '=?'
            },
            controller: ['$scope', '$element', function($scope, $element) {
                for (var option in $scope.options) {
                    $scope[option] = $scope.options[option];
                }

                // Disable animation if ngAnimate is present, as it drops down performance.
                enableNgAnimate($element, false);

                $scope.gantt = new Gantt($scope, $element);
                this.gantt = $scope.gantt;
            }],
            link: function(scope, element) {
                scope.gantt.api.directives.raise.new('gantt', scope, element);
                scope.$on('$destroy', function() {
                    scope.gantt.api.directives.raise.destroy('gantt', scope, element);
                });

                $timeout(function() {
                    scope.gantt.initialized();
                });
            }
        };
    }]);
}());


// This file is adapted from Angular UI ngGrid project
// MIT License
// https://github.com/angular-ui/ng-grid/blob/v3.0.0-rc.20/src/js/core/factories/GridApi.js
(function() {
    'use strict';

    angular.module('gantt')
        .factory('GanttApi', ['$q', '$rootScope', 'ganttUtils',
            function($q, $rootScope, utils) {
                /**
                 * @ngdoc function
                 * @name gantt.class:GanttApi
                 * @description GanttApi provides the ability to register public methods events inside the gantt and allow
                 * for other components to use the api via featureName.raise.methodName and featureName.on.eventName(function(args){}.
                 * @param {object} gantt gantt that owns api
                 */
                var GanttApi = function GanttApi(gantt) {
                    this.gantt = gantt;
                    this.listeners = [];
                    this.apiId = utils.newId();
                };

                function registerEventWithAngular(eventId, handler, gantt, _this) {
                    return $rootScope.$on(eventId, function() {
                        var args = Array.prototype.slice.call(arguments);
                        args.splice(0, 1); //remove evt argument
                        handler.apply(_this ? _this : gantt.api, args);
                    });
                }

                /**
                 * @ngdoc function
                 * @name gantt.class:suppressEvents
                 * @methodOf gantt.class:GanttApi
                 * @description Used to execute a function while disabling the specified event listeners.
                 * Disables the listenerFunctions, executes the callbackFn, and then enables
                 * the listenerFunctions again
                 * @param {object} listenerFuncs listenerFunc or array of listenerFuncs to suppress. These must be the same
                 * functions that were used in the .on.eventName method
                 * @param {object} callBackFn function to execute
                 * @example
                 * <pre>
                 *    var navigate = function (newRowCol, oldRowCol){
                 *       //do something on navigate
                 *    }
                 *
                 *    ganttApi.cellNav.on.navigate(scope,navigate);
                 *
                 *
                 *    //call the scrollTo event and suppress our navigate listener
                 *    //scrollTo will still raise the event for other listeners
                 *    ganttApi.suppressEvents(navigate, function(){
                 *       ganttApi.cellNav.scrollTo(aRow, aCol);
                 *    });
                 *
                 * </pre>
                 */
                GanttApi.prototype.suppressEvents = function(listenerFuncs, callBackFn) {
                    var self = this;
                    var listeners = angular.isArray(listenerFuncs) ? listenerFuncs : [listenerFuncs];

                    //find all registered listeners
                    var foundListeners = [];
                    listeners.forEach(function(l) {
                        foundListeners = self.listeners.filter(function(lstnr) {
                            return l === lstnr.handler;
                        });
                    });

                    //deregister all the listeners
                    foundListeners.forEach(function(l) {
                        l.dereg();
                    });

                    callBackFn();

                    //reregister all the listeners
                    foundListeners.forEach(function(l) {
                        l.dereg = registerEventWithAngular(l.eventId, l.handler, self.gantt, l._this);
                    });

                };

                /**
                 * @ngdoc function
                 * @name registerEvent
                 * @methodOf gantt.class:GanttApi
                 * @description Registers a new event for the given feature.  The event will get a
                 * .raise and .on prepended to it
                 * <br>
                 * .raise.eventName() - takes no arguments
                 * <br/>
                 * <br/>
                 * .on.eventName(scope, callBackFn, _this)
                 * <br/>
                 * scope - a scope reference to add a deregister call to the scopes .$on('destroy')
                 * <br/>
                 * callBackFn - The function to call
                 * <br/>
                 * _this - optional this context variable for callbackFn. If omitted, gantt.api will be used for the context
                 * <br/>
                 * .on.eventName returns a dereg funtion that will remove the listener.  It's not necessary to use it as the listener
                 * will be removed when the scope is destroyed.
                 * @param {string} featureName name of the feature that raises the event
                 * @param {string} eventName  name of the event
                 */
                GanttApi.prototype.registerEvent = function(featureName, eventName) {
                    var self = this;
                    if (!self[featureName]) {
                        self[featureName] = {};
                    }

                    var feature = self[featureName];
                    if (!feature.on) {
                        feature.on = {};
                        feature.raise = {};
                    }

                    var eventId = 'event:gantt:' + this.apiId + ':' + featureName + ':' + eventName;

                    // Creating raise event method featureName.raise.eventName
                    feature.raise[eventName] = function() {
                        $rootScope.$emit.apply($rootScope, [eventId].concat(Array.prototype.slice.call(arguments)));
                    };

                    // Creating on event method featureName.oneventName
                    feature.on[eventName] = function(scope, handler, _this) {
                        var deregAngularOn = registerEventWithAngular(eventId, handler, self.gantt, _this);

                        //track our listener so we can turn off and on
                        var listener = {
                            handler: handler,
                            dereg: deregAngularOn,
                            eventId: eventId,
                            scope: scope,
                            _this: _this
                        };
                        self.listeners.push(listener);

                        var removeListener = function() {
                            listener.dereg();
                            var index = self.listeners.indexOf(listener);
                            self.listeners.splice(index, 1);
                        };

                        //destroy tracking when scope is destroyed
                        scope.$on('$destroy', function() {
                            removeListener();
                        });

                        return removeListener;
                    };
                };

                /**
                 * @ngdoc function
                 * @name registerEventsFromObject
                 * @methodOf gantt.class:GanttApi
                 * @description Registers features and events from a simple objectMap.
                 * eventObjectMap must be in this format (multiple features allowed)
                 * <pre>
                 * {featureName:
                 *        {
                 *          eventNameOne:function(args){},
                 *          eventNameTwo:function(args){}
                 *        }
                 *  }
                 * </pre>
                 * @param {object} eventObjectMap map of feature/event names
                 */
                GanttApi.prototype.registerEventsFromObject = function(eventObjectMap) {
                    var self = this;
                    var features = [];
                    angular.forEach(eventObjectMap, function(featProp, featPropName) {
                        var feature = {name: featPropName, events: []};
                        angular.forEach(featProp, function(prop, propName) {
                            feature.events.push(propName);
                        });
                        features.push(feature);
                    });

                    features.forEach(function(feature) {
                        feature.events.forEach(function(event) {
                            self.registerEvent(feature.name, event);
                        });
                    });

                };

                /**
                 * @ngdoc function
                 * @name registerMethod
                 * @methodOf gantt.class:GanttApi
                 * @description Registers a new event for the given feature
                 * @param {string} featureName name of the feature
                 * @param {string} methodName  name of the method
                 * @param {object} callBackFn function to execute
                 * @param {object} _this binds callBackFn 'this' to _this.  Defaults to ganttApi.gantt
                 */
                GanttApi.prototype.registerMethod = function(featureName, methodName, callBackFn, _this) {
                    if (!this[featureName]) {
                        this[featureName] = {};
                    }

                    var feature = this[featureName];

                    feature[methodName] = utils.createBoundedWrapper(_this || this.gantt, callBackFn);
                };

                /**
                 * @ngdoc function
                 * @name registerMethodsFromObject
                 * @methodOf gantt.class:GanttApi
                 * @description Registers features and methods from a simple objectMap.
                 * eventObjectMap must be in this format (multiple features allowed)
                 * <br>
                 * {featureName:
                 *        {
                 *          methodNameOne:function(args){},
                 *          methodNameTwo:function(args){}
                 *        }
                 * @param {object} eventObjectMap map of feature/event names
                 * @param {object} _this binds this to _this for all functions.  Defaults to ganttApi.gantt
                 */
                GanttApi.prototype.registerMethodsFromObject = function(methodMap, _this) {
                    var self = this;
                    var features = [];
                    angular.forEach(methodMap, function(featProp, featPropName) {
                        var feature = {name: featPropName, methods: []};
                        angular.forEach(featProp, function(prop, propName) {
                            feature.methods.push({name: propName, fn: prop});
                        });
                        features.push(feature);
                    });

                    features.forEach(function(feature) {
                        feature.methods.forEach(function(method) {
                            self.registerMethod(feature.name, method.name, method.fn, _this);
                        });
                    });

                };

                return GanttApi;

            }]);

})();

(function() {
    'use strict';
    angular.module('gantt').factory('GanttOptions', [function() {
        var GanttOptions = function(values, defaultValues) {
            this.defaultValues = defaultValues;
            this.values = values;

            this.defaultValue = function(optionName) {
                var defaultValue = this.defaultValues[optionName];
                if (angular.isFunction(defaultValue)) {
                    defaultValue = defaultValue();
                }

                return defaultValue;
            };

            this.sanitize = function(optionName, optionValue) {
                if (!optionValue) {
                    var defaultValue = this.defaultValue(optionName);
                    if (defaultValue !== undefined) {
                        if (optionValue !== undefined && typeof defaultValue === 'boolean') {
                            return optionValue;
                        }

                        return defaultValue;
                    }
                }

                return optionValue;
            };

            this.value = function(optionName) {
                return this.sanitize(optionName, this.values[optionName]);
            };

            this.set = function(optionName, optionValue) {
                this.values[optionName] = optionValue;
            };

            this.initialize = function() {
                for (var optionName in this.values) {
                    var optionValue = this.values[optionName];
                    if (this.values.hasOwnProperty(optionName)) {
                        this.values[optionName] = this.value(optionName, optionValue);
                    }
                }
                return this.values;
            };
        };

        return GanttOptions;
    }]);
}());

(function(){
    'use strict';
    /**
     * Calendar factory is used to define working periods, non working periods, and other specific period of time,
     * and retrieve effective timeFrames for each day of the gantt.
     */
    angular.module('gantt').factory('GanttCalendar', ['$filter', 'moment', function($filter, moment) {
        /**
         * TimeFrame represents time frame in any day. parameters are given using options object.
         *
         * @param {moment|string} start start of timeFrame. If a string is given, it will be parsed as a moment.
         * @param {moment|string} end end of timeFrame. If a string is given, it will be parsed as a moment.
         * @param {boolean} working is this timeFrame flagged as working.
         * @param {boolean} magnet is this timeFrame will magnet.
         * @param {boolean} default is this timeFrame will be used as default.
         * @param {color} css color attached to this timeFrame.
         * @param {string} classes css classes attached to this timeFrame.
         *
         * @constructor
         */
        var TimeFrame = function(options) {
            if (options === undefined) {
                options = {};
            }

            this.start = options.start;
            this.end = options.end;
            this.working = options.working;
            this.magnet = options.magnet !== undefined ? options.magnet : true;
            this.default = options.default;
            this.color = options.color;
            this.classes = options.classes;
            this.internal = options.internal;
        };

        TimeFrame.prototype.updateView = function() {
            if (this.$element) {
                var cssStyles = {};

                if (this.left !== undefined) {
                    cssStyles.left = this.left + 'px';
                } else {
                    cssStyles.left = '';
                }
                if (this.width !== undefined) {
                    cssStyles.width = this.width + 'px';
                } else {
                    cssStyles.width = '';
                }

                if (this.color !== undefined) {
                    cssStyles['background-color'] = this.color;
                } else {
                    cssStyles['background-color'] = '';
                }

                this.$element.css(cssStyles);

                var classes = ['gantt-timeframe' + (this.working ? '' : '-non') + '-working'];
                if (this.classes) {
                    classes = classes.concat(this.classes);
                }
                for (var i = 0, l = classes.length; i < l; i++) {
                    this.$element.toggleClass(classes[i], true);
                }
            }
        };

        TimeFrame.prototype.getDuration = function() {
            if (this.end !== undefined && this.start !== undefined) {
                return this.end.diff(this.start, 'milliseconds');
            }
        };

        TimeFrame.prototype.clone = function() {
            return new TimeFrame(this);
        };

        /**
         * TimeFrameMapping defines how timeFrames will be placed for each days. parameters are given using options object.
         *
         * @param {function} func a function with date parameter, that will be evaluated for each distinct day of the gantt.
         *                        this function must return an array of timeFrame names to apply.
         * @constructor
         */
        var TimeFrameMapping = function(func) {
            this.func = func;
        };

        TimeFrameMapping.prototype.getTimeFrames = function(date) {
            var ret = this.func(date);
            if (!(ret instanceof Array)) {
                ret = [ret];
            }
            return ret;
        };

        TimeFrameMapping.prototype.clone = function() {
            return new TimeFrameMapping(this.func);
        };

        /**
         * A DateFrame is date range that will use a specific TimeFrameMapping, configured using a function (evaluator),
         * a date (date) or a date range (start, end). parameters are given using options object.
         *
         * @param {function} evaluator a function with date parameter, that will be evaluated for each distinct day of the gantt.
         *                   this function must return a boolean representing matching of this dateFrame or not.
         * @param {moment} date date of dateFrame.
         * @param {moment} start start of date frame.
         * @param {moment} end end of date frame.
         * @param {array} targets array of TimeFrameMappings/TimeFrames names to use for this date frame.
         * @param {boolean} default is this dateFrame will be used as default.
         * @constructor
         */
        var DateFrame = function(options) {
            this.evaluator = options.evaluator;
            if (options.date) {
                this.start = moment(options.date).startOf('day');
                this.end = moment(options.date).endOf('day');
            } else {
                this.start = options.start;
                this.end = options.end;
            }
            if (options.targets instanceof Array) {
                this.targets = options.targets;
            } else {
                this.targets = [options.targets];
            }
            this.default = options.default;
        };

        DateFrame.prototype.dateMatch = function(date) {
            if (this.evaluator) {
                return this.evaluator(date);
            } else if (this.start && this.end) {
                return date >= this.start && date <= this.end;
            } else {
                return false;
            }
        };

        DateFrame.prototype.clone = function() {
            return new DateFrame(this);
        };



        /**
         * Register TimeFrame, TimeFrameMapping and DateMapping objects into Calendar object,
         * and use Calendar#getTimeFrames(date) function to retrieve effective timeFrames for a specific day.
         *
         * @constructor
         */
        var Calendar = function() {
            this.timeFrames = {};
            this.timeFrameMappings = {};
            this.dateFrames = {};
        };

        /**
         * Remove all objects.
         */
        Calendar.prototype.clear = function() {
            this.timeFrames = {};
            this.timeFrameMappings = {};
            this.dateFrames = {};
        };

        /**
         * Register TimeFrame objects.
         *
         * @param {object} timeFrames with names of timeFrames for keys and TimeFrame objects for values.
         */
        Calendar.prototype.registerTimeFrames = function(timeFrames) {
            angular.forEach(timeFrames, function(timeFrame, name) {
                this.timeFrames[name] = new TimeFrame(timeFrame);
            }, this);
        };

        /**
         * Removes TimeFrame objects.
         *
         * @param {array} timeFrames names of timeFrames to remove.
         */
        Calendar.prototype.removeTimeFrames = function(timeFrames) {
            angular.forEach(timeFrames, function(name) {
                delete this.timeFrames[name];
            }, this);
        };

        /**
         * Remove all TimeFrame objects.
         */
        Calendar.prototype.clearTimeFrames = function() {
            this.timeFrames = {};
        };

        /**
         * Register TimeFrameMapping objects.
         *
         * @param {object} mappings object with names of timeFrames mappings for keys and TimeFrameMapping objects for values.
         */
        Calendar.prototype.registerTimeFrameMappings = function(mappings) {
            angular.forEach(mappings, function(timeFrameMapping, name) {
                this.timeFrameMappings[name] = new TimeFrameMapping(timeFrameMapping);
            }, this);
        };

        /**
         * Removes TimeFrameMapping objects.
         *
         * @param {array} mappings names of timeFrame mappings to remove.
         */
        Calendar.prototype.removeTimeFrameMappings = function(mappings) {
            angular.forEach(mappings, function(name) {
                delete this.timeFrameMappings[name];
            }, this);
        };

        /**
         * Removes all TimeFrameMapping objects.
         */
        Calendar.prototype.clearTimeFrameMappings = function() {
            this.timeFrameMappings = {};
        };

        /**
         * Register DateFrame objects.
         *
         * @param {object} dateFrames object with names of dateFrames for keys and DateFrame objects for values.
         */
        Calendar.prototype.registerDateFrames = function(dateFrames) {
            angular.forEach(dateFrames, function(dateFrame, name) {
                this.dateFrames[name] = new DateFrame(dateFrame);
            }, this);
        };

        /**
         * Remove DateFrame objects.
         *
         * @param {array} mappings names of date frames to remove.
         */
        Calendar.prototype.removeDateFrames = function(dateFrames) {
            angular.forEach(dateFrames, function(name) {
                delete this.dateFrames[name];
            }, this);
        };

        /**
         * Removes all DateFrame objects.
         */
        Calendar.prototype.clearDateFrames = function() {
            this.dateFrames = {};
        };

        var filterDateFrames = function(inputDateFrames, date) {
            var dateFrames = [];
            angular.forEach(inputDateFrames, function(dateFrame) {
                if (dateFrame.dateMatch(date)) {
                    dateFrames.push(dateFrame);
                }
            });
            if (dateFrames.length === 0) {
                angular.forEach(inputDateFrames, function(dateFrame) {
                    if (dateFrame.default) {
                        dateFrames.push(dateFrame);
                    }
                });
            }
            return dateFrames;
        };

        /**
         * Retrieves TimeFrame objects for a given date, using whole configuration for this Calendar object.
         *
         * @param {moment} date
         *
         * @return {array} an array of TimeFrame objects.
         */
        Calendar.prototype.getTimeFrames = function(date) {
            var timeFrames = [];
            var dateFrames = filterDateFrames(this.dateFrames, date);

            for (var i=0; i < dateFrames.length; i++) {
                if (dateFrames[i] !== undefined) {
                    var targets = dateFrames[i].targets;

                    for (var j=0; j < targets.length; j++) {
                        var timeFrameMapping = this.timeFrameMappings[targets[j]];
                        if (timeFrameMapping !== undefined) {
                            // If a timeFrame mapping is found
                            timeFrames.push(timeFrameMapping.getTimeFrames());
                        } else {
                            // If no timeFrame mapping is found, try using direct timeFrame
                            var timeFrame = this.timeFrames[targets[j]];
                            if (timeFrame !== undefined) {
                                timeFrames.push(timeFrame);
                            }
                        }
                    }
                }
            }

            var dateYear = date.year();
            var dateMonth = date.month();
            var dateDate = date.date();

            var validatedTimeFrames = [];
            if (timeFrames.length === 0) {
                angular.forEach(this.timeFrames, function(timeFrame) {
                    if (timeFrame.default) {
                        timeFrames.push(timeFrame);
                    }
                });
            }

            for (i=0; i < timeFrames.length; i++) {
                var cTimeFrame = timeFrames[i].clone();

                if (cTimeFrame.start !== undefined) {
                    cTimeFrame.start.year(dateYear);
                    cTimeFrame.start.month(dateMonth);
                    cTimeFrame.start.date(dateDate);
                }

                if (cTimeFrame.end !== undefined) {
                    cTimeFrame.end.year(dateYear);
                    cTimeFrame.end.month(dateMonth);
                    cTimeFrame.end.date(dateDate);

                    if (moment(cTimeFrame.end).startOf('day') === cTimeFrame.end) {
                        cTimeFrame.end.add(1, 'day');
                    }
                }

                validatedTimeFrames.push(cTimeFrame);
            }

            return validatedTimeFrames;
        };

        /**
         * Solve timeFrames.
         *
         * Smaller timeFrames have priority over larger one.
         *
         * @param {array} timeFrames Array of timeFrames to solve
         * @param {moment} startDate
         * @param {moment} endDate
         */
        Calendar.prototype.solve = function(timeFrames, startDate, endDate) {
            var color;
            var classes;
            var minDate;
            var maxDate;

            for (var i=0; i<timeFrames.length; i++) {
                var timeFrame = timeFrames[i];
                if (minDate === undefined || minDate > timeFrame.start) {
                    minDate = timeFrame.start;
                }
                if (maxDate === undefined || maxDate < timeFrame.end) {
                    maxDate = timeFrame.end;
                }
                if (color === undefined && timeFrame.color) {
                    color = timeFrame.color;
                }
                if (timeFrame.classes !== undefined) {
                    if (classes === undefined) {
                        classes = [];
                    }
                    classes = classes.concat(timeFrame.classes);
                }
            }

            if (startDate === undefined) {
                startDate = minDate;
            }

            if (endDate === undefined) {
                endDate = maxDate;
            }

            var solvedTimeFrames = [new TimeFrame({start: startDate, end: endDate, internal: true})];

            timeFrames = $filter('filter')(timeFrames, function(timeFrame) {
                return (timeFrame.start === undefined || timeFrame.start < endDate) && (timeFrame.end === undefined || timeFrame.end > startDate);
            });

            for (i=0; i<timeFrames.length; i++) {
                var cTimeFrame = timeFrames[i];
                if (!cTimeFrame.start) {
                    cTimeFrame.start = startDate;
                }
                if (!cTimeFrame.end) {
                    cTimeFrame.end = endDate;
                }
            }

            var orderedTimeFrames = $filter('orderBy')(timeFrames, function(timeFrame) {
                return -timeFrame.getDuration();
            });

            var k;
            for (i=0; i<orderedTimeFrames.length; i++) {
                var oTimeFrame = orderedTimeFrames[i];

                var tmpSolvedTimeFrames = solvedTimeFrames.slice();

                k=0;
                var dispatched = false;
                var treated = false;

                for (var j=0; j<solvedTimeFrames.length; j++) {
                    var sTimeFrame = solvedTimeFrames[j];

                    if (!treated) {
                        if (!oTimeFrame.end && !oTimeFrame.start) {
                            // timeFrame is infinite.
                            tmpSolvedTimeFrames.splice(k, 0, oTimeFrame);
                            treated = true;
                            dispatched = false;
                        } else if (oTimeFrame.end > sTimeFrame.start && oTimeFrame.start < sTimeFrame.end) {
                            // timeFrame is included in this solvedTimeFrame.
                            // solvedTimeFrame:|ssssssssssssssssssssssssssssssssss|
                            //       timeFrame:          |tttttt|
                            //          result:|sssssssss|tttttt|sssssssssssssssss|

                            var newSolvedTimeFrame = sTimeFrame.clone();

                            sTimeFrame.end = moment(oTimeFrame.start);
                            newSolvedTimeFrame.start = moment(oTimeFrame.end);

                            tmpSolvedTimeFrames.splice(k + 1, 0, oTimeFrame.clone(), newSolvedTimeFrame);
                            treated = true;
                            dispatched = false;
                        } else if (!dispatched && oTimeFrame.start < sTimeFrame.end) {
                            // timeFrame is dispatched on two solvedTimeFrame.
                            // First part
                            // solvedTimeFrame:|sssssssssssssssssssssssssssssssssss|s+1;s+1;s+1;s+1;s+1;s+1|
                            //       timeFrame:                                |tttttt|
                            //          result:|sssssssssssssssssssssssssssssss|tttttt|;s+1;s+1;s+1;s+1;s+1|

                            sTimeFrame.end = moment(oTimeFrame.start);
                            tmpSolvedTimeFrames.splice(k + 1, 0, oTimeFrame.clone());

                            dispatched = true;
                        } else if (dispatched && oTimeFrame.end > sTimeFrame.start) {
                            // timeFrame is dispatched on two solvedTimeFrame.
                            // Second part

                            sTimeFrame.start = moment(oTimeFrame.end);
                            dispatched = false;
                            treated = true;
                        }
                        k++;
                    }
                }

                solvedTimeFrames = tmpSolvedTimeFrames;
            }

            solvedTimeFrames = $filter('filter')(solvedTimeFrames, function(timeFrame) {
                return !timeFrame.internal &&
                    (timeFrame.start === undefined || timeFrame.start < endDate) &&
                    (timeFrame.end === undefined || timeFrame.end > startDate);
            });

            return solvedTimeFrames;

        };

        return Calendar;
    }]);
}());

(function(){
    'use strict';
    angular.module('gantt').factory('GanttCurrentDateManager', ['moment', function(moment) {
        var GanttCurrentDateManager = function(gantt) {
            var self = this;

            this.gantt = gantt;

            this.date = undefined;
            this.position = undefined;
            this.currentDateColumn = undefined;

            this.gantt.$scope.simplifyMoment = function(d) {
                return moment.isMoment(d) ? d.unix() : d;
            };

            this.gantt.$scope.$watchGroup(['currentDate', 'simplifyMoment(currentDateValue)'], function(newValues, oldValues) {
                if (newValues !== oldValues) {
                    self.setCurrentDate(self.gantt.options.value('currentDateValue'));
                }
            });
        };

        GanttCurrentDateManager.prototype.setCurrentDate = function(currentDate) {
            this.date = currentDate;
            var oldColumn = this.currentDateColumn;
            var newColumn;

            if (this.date !== undefined && this.gantt.options.value('currentDate') === 'column') {
                newColumn = this.gantt.columnsManager.getColumnByDate(this.date, true);
            }
            this.currentDateColumn = newColumn;

            if (oldColumn !== newColumn) {
                if (oldColumn !== undefined) {
                    oldColumn.currentDate = false;
                    oldColumn.updateView();
                }
                if (newColumn !== undefined) {
                    newColumn.currentDate = true;
                    newColumn.updateView();
                }
            }

            this.position = this.gantt.getPositionByDate(this.date, true);
        };
        return GanttCurrentDateManager;
    }]);
}());

(function() {
    'use strict';
    angular.module('gantt').factory('GanttColumn', ['moment', function(moment) {
        // Used to display the Gantt grid and header.
        // The columns are generated by the column generator.
        var Column = function(date, endDate, left, width, calendar, timeFramesWorkingMode, timeFramesNonWorkingMode) {
            this.date = date;
            this.endDate = endDate;
            this.left = left;
            this.width = width;
            this.calendar = calendar;
            this.duration = this.endDate.diff(this.date, 'milliseconds');
            this.timeFramesWorkingMode = timeFramesWorkingMode;
            this.timeFramesNonWorkingMode = timeFramesNonWorkingMode;
            this.timeFrames = [];
            this.currentDate = false;
            this.visibleTimeFrames = [];
            this.daysTimeFrames = {};
            this.cropped = false;
            this.originalSize = {left: this.left, width: this.width};
            this.updateTimeFrames();
        };

        var getDateKey = function(date) {
            return date.year() + '-' + date.month() + '-' + date.date();
        };

        Column.prototype.updateView = function() {
            if (this.$element) {
                if (this.currentDate) {
                    this.$element.addClass('gantt-foreground-col-current-date');
                } else {
                    this.$element.removeClass('gantt-foreground-col-current-date');
                }

                this.$element.css({'left': this.left + 'px', 'width': this.width + 'px'});

                for (var i = 0, l = this.timeFrames.length; i < l; i++) {
                    this.timeFrames[i].updateView();
                }
            }
        };

        Column.prototype.updateTimeFrames = function() {
            var self = this;

            if (self.calendar !== undefined && (self.timeFramesNonWorkingMode !== 'hidden' || self.timeFramesWorkingMode !== 'hidden')) {
                var cDate = self.date;
                var cDateStartOfDay = moment(cDate).startOf('day');
                var cDateNextDay = cDateStartOfDay.add(1, 'day');
                var i;
                while (cDate < self.endDate) {
                    var timeFrames = self.calendar.getTimeFrames(cDate);
                    var nextCDate = moment.min(cDateNextDay, self.endDate);
                    timeFrames = self.calendar.solve(timeFrames, cDate, nextCDate);
                    var cTimeFrames = [];
                    for (i=0; i < timeFrames.length; i++) {
                        var cTimeFrame = timeFrames[i];

                        var start = cTimeFrame.start;
                        if (start === undefined) {
                            start = cDate;
                        }

                        var end = cTimeFrame.end;
                        if (end === undefined) {
                            end = nextCDate;
                        }

                        if (start < self.date) {
                            start = self.date;
                        }

                        if (end > self.endDate) {
                            end = self.endDate;
                        }

                        cTimeFrame = cTimeFrame.clone();

                        cTimeFrame.start = moment(start);
                        cTimeFrame.end = moment(end);

                        cTimeFrames.push(cTimeFrame);
                    }
                    self.timeFrames = self.timeFrames.concat(cTimeFrames);

                    var cDateKey = getDateKey(cDate);
                    self.daysTimeFrames[cDateKey] = cTimeFrames;

                    cDate = nextCDate;
                    cDateStartOfDay = moment(cDate).startOf('day');
                    cDateNextDay = cDateStartOfDay.add(1, 'day');
                }

                for (i=0; i < self.timeFrames.length; i++) {
                    var timeFrame = self.timeFrames[i];

                    var positionDuration = timeFrame.start.diff(self.date, 'milliseconds');
                    var position = positionDuration / self.duration * self.width;

                    var timeFrameDuration = timeFrame.end.diff(timeFrame.start, 'milliseconds');
                    var timeFramePosition = timeFrameDuration / self.duration * self.width;

                    var hidden = false;
                    if (timeFrame.working && self.timeFramesWorkingMode !== 'visible') {
                        hidden = true;
                    } else if (!timeFrame.working && self.timeFramesNonWorkingMode !== 'visible') {
                        hidden = true;
                    }

                    if (!hidden) {
                        self.visibleTimeFrames.push(timeFrame);
                    }

                    timeFrame.hidden = hidden;
                    timeFrame.left = position;
                    timeFrame.width = timeFramePosition;
                    timeFrame.originalSize = {left: timeFrame.left, width: timeFrame.width};
                }

                if (self.timeFramesNonWorkingMode === 'cropped' || self.timeFramesWorkingMode === 'cropped') {
                    var timeFramesWidth = 0;
                    for (var j=0; j < self.timeFrames.length; j++) {
                        var aTimeFrame = self.timeFrames[j];
                        if (!aTimeFrame.working && self.timeFramesNonWorkingMode !== 'cropped' ||
                            aTimeFrame.working && self.timeFramesWorkingMode !== 'cropped') {
                            timeFramesWidth += aTimeFrame.width;
                        }
                    }

                    if (timeFramesWidth !== self.width) {
                        var croppedRatio = self.width / timeFramesWidth;
                        var croppedWidth = 0;
                        var originalCroppedWidth = 0;

                        var allCropped = true;

                        for (j=0; j < self.timeFrames.length; j++) {
                            var bTimeFrame = self.timeFrames[j];

                            if (!bTimeFrame.working && self.timeFramesNonWorkingMode !== 'cropped' ||
                                bTimeFrame.working && self.timeFramesWorkingMode !== 'cropped') {
                                bTimeFrame.left = (bTimeFrame.left - croppedWidth) * croppedRatio;
                                bTimeFrame.width = bTimeFrame.width * croppedRatio;
                                bTimeFrame.originalSize.left = (bTimeFrame.originalSize.left - originalCroppedWidth) * croppedRatio;
                                bTimeFrame.originalSize.width = bTimeFrame.originalSize.width * croppedRatio;
                                bTimeFrame.cropped = false;
                                allCropped = false;
                            } else {
                                croppedWidth += bTimeFrame.width;
                                originalCroppedWidth += bTimeFrame.originalSize.width;
                                bTimeFrame.left = undefined;
                                bTimeFrame.width = 0;
                                bTimeFrame.originalSize = {left: undefined, width: 0};
                                bTimeFrame.cropped = true;
                            }
                        }

                        self.cropped = allCropped;
                    } else {
                        self.cropped = false;
                    }
                }
            }
        };

        Column.prototype.clone = function() {
            return new Column(moment(this.date), moment(this.endDate), this.left, this.width, this.calendar);
        };

        Column.prototype.containsDate = function(date) {
            return date > this.date && date <= this.endDate;
        };

        Column.prototype.equals = function(other) {
            return this.date === other.date;
        };

        Column.prototype.roundTo = function(date, unit, offset, midpoint) {
            // Waiting merge of https://github.com/moment/moment/pull/1794
            if (unit === 'day') {
                // Inconsistency in units in momentJS.
                unit = 'date';
            }

            offset = offset || 1;
            var value = date.get(unit);

            switch (midpoint) {
                case 'up':
                    value = Math.ceil(value / offset);
                    break;
                case 'down':
                    value = Math.floor(value / offset);
                    break;
                default:
                    value = Math.round(value / offset);
                    break;
            }

            var units = ['millisecond', 'second', 'minute', 'hour', 'date', 'month', 'year'];
            date.set(unit, value * offset);

            var indexOf = units.indexOf(unit);
            for (var i = 0; i < indexOf; i++) {
                date.set(units[i], 0);
            }

            return date;
        };

        Column.prototype.getMagnetDate = function(date, magnetValue, magnetUnit, timeFramesMagnet) {
            if (magnetValue > 0 && magnetUnit !== undefined) {
                var initialDate = date;
                date = moment(date);

                if (magnetUnit === 'column') {
                    // Snap to column borders only.
                    var position = this.getPositionByDate(date);

                    if (position < this.width / 2) {
                        date = moment(this.date);
                    } else {
                        date = moment(this.endDate);
                    }
                } else {
                    // Round the value
                    date = this.roundTo(date, magnetUnit, magnetValue);

                    // Snap to column borders if date overflows.
                    if (date < this.date) {
                        date = moment(this.date);
                    } else if (date > this.endDate) {
                        date = moment(this.endDate);
                    }
                }

                if (timeFramesMagnet) {
                    var maxTimeFrameDiff = Math.abs(initialDate.diff(date, 'milliseconds'));
                    var currentTimeFrameDiff;

                    for (var i=0; i<this.timeFrames.length; i++) {
                        var timeFrame = this.timeFrames[i];
                        if (timeFrame.magnet) {
                            var previousTimeFrame = this.timeFrames[i-1];
                            var nextTimeFrame = this.timeFrames[i+1];
                            var timeFrameDiff;

                            if (previousTimeFrame === undefined || previousTimeFrame.working !== timeFrame.working) {
                                timeFrameDiff = Math.abs(initialDate.diff(timeFrame.start, 'milliseconds'));
                                if (timeFrameDiff < maxTimeFrameDiff && (currentTimeFrameDiff === undefined || timeFrameDiff < currentTimeFrameDiff)) {
                                    currentTimeFrameDiff = timeFrameDiff;
                                    date = timeFrame.start;
                                }
                            }

                            if (nextTimeFrame === undefined || nextTimeFrame.working !== timeFrame.working) {
                                timeFrameDiff = Math.abs(initialDate.diff(timeFrame.end, 'milliseconds'));
                                if (timeFrameDiff < maxTimeFrameDiff && (currentTimeFrameDiff === undefined || timeFrameDiff < currentTimeFrameDiff)) {
                                    currentTimeFrameDiff = timeFrameDiff;
                                    date = timeFrame.end;
                                }
                            }
                        }
                    }
                }
            }
            return date;
        };

        Column.prototype.getDateByPositionUsingTimeFrames = function(position) {
            for (var i = 0, l = this.timeFrames.length; i < l; i++) {
                // TODO: performance optimization could be done.
                var timeFrame = this.timeFrames[i];
                if (!timeFrame.cropped && position >= timeFrame.left && position <= timeFrame.left + timeFrame.width) {
                    var positionDuration = timeFrame.getDuration() / timeFrame.width * (position - timeFrame.left);
                    var date = moment(timeFrame.start).add(positionDuration, 'milliseconds');
                    return date;
                }
            }
        };

        Column.prototype.getDateByPosition = function(position, magnetValue, magnetUnit, timeFramesMagnet) {
            var positionDuration;
            var date;

            if (position < 0) {
                position = 0;
            }
            if (position > this.width) {
                position = this.width;
            }

            if (this.timeFramesNonWorkingMode === 'cropped' || this.timeFramesWorkingMode === 'cropped') {
                date = this.getDateByPositionUsingTimeFrames(position);
            }

            if (date === undefined) {
                positionDuration = this.duration / this.width * position;
                date = moment(this.date).add(positionDuration, 'milliseconds');
            }

            date = this.getMagnetDate(date, magnetValue, magnetUnit, timeFramesMagnet);

            return date;
        };

        Column.prototype.getDayTimeFrame = function(date) {
            var dtf = this.daysTimeFrames[getDateKey(date)];
            if (dtf === undefined) {
                return [];
            }
            return dtf;
        };

        Column.prototype.getPositionByDate = function(date) {
            var positionDuration;
            var position;

            if (this.timeFramesNonWorkingMode === 'cropped' || this.timeFramesWorkingMode === 'cropped') {
                var croppedDate = date;
                var timeFrames = this.getDayTimeFrame(croppedDate);
                for (var i = 0; i < timeFrames.length; i++) {
                    var timeFrame = timeFrames[i];
                    if (croppedDate >= timeFrame.start && croppedDate <= timeFrame.end) {
                        if (timeFrame.cropped) {
                            if (timeFrames.length > i + 1) {
                                croppedDate = timeFrames[i + 1].start;
                            } else {
                                croppedDate = timeFrame.end;
                            }
                        } else {
                            positionDuration = croppedDate.diff(timeFrame.start, 'milliseconds');
                            position = positionDuration / timeFrame.getDuration() * timeFrame.width;
                            return this.left + timeFrame.left + position;
                        }
                    }
                }
            }

            positionDuration = date.diff(this.date, 'milliseconds');
            position = positionDuration / this.duration * this.width;

            if (position < 0) {
                position = 0;
            }

            if (position > this.width) {
                position = this.width;
            }

            return this.left + position;
        };

        return Column;
    }]);
}());


(function() {
    'use strict';
    angular.module('gantt').factory('GanttColumnBuilder', ['GanttColumn', function(Column) {
        // Builder for columns, based of data given by column generator and columnsManager.
        var ColumnBuilder = function(columnsManager) {
            this.columnsManager = columnsManager;
        };

        ColumnBuilder.prototype.newColumn = function(date, endDate, left, width) {
            var calendar = this.columnsManager.gantt.calendar;
            var timeFramesWorkingMode = this.columnsManager.gantt.options.value('timeFramesWorkingMode');
            var timeFramesNonWorkingMode = this.columnsManager.gantt.options.value('timeFramesNonWorkingMode');

            return new Column(date, endDate, left, width, calendar, timeFramesWorkingMode, timeFramesNonWorkingMode);
        };

        return ColumnBuilder;
    }]);
}());


(function() {
    'use strict';
    angular.module('gantt').service('GanttColumnGenerator', ['moment', function(moment) {

        // Columns are generated including or excluding the to date.
        // If the To date is the first day of month and the time is 00:00 then no new column is generated for this month.

        var isToDateToExclude = function(to, value, unit) {
            return moment(to).add(value, unit).startOf(unit) === to;
        };


        var getFirstValue = function(unit) {
            if (['hour', 'minute', 'second', 'millisecond'].indexOf(unit) >= 0) {
                return 0;
            }
        };

        var ensureNoUnitOverflow = function(unit, startDate, endDate) {
            var v1 = startDate.get(unit);
            var v2 = endDate.get(unit);
            var firstValue = getFirstValue(unit);
            if (firstValue !== undefined && v2 !== firstValue && v2 < v1) {
                endDate.set(unit, firstValue);
            }
        };

        // Generates one column for each time unit between the given from and to date.
        this.generate = function(builder, from, to, viewScale, columnWidth, maximumWidth, leftOffset, reverse) {
            if (!to && !maximumWidth) {
                throw 'to or maximumWidth must be defined';
            }

            viewScale = viewScale.trim();
            if (viewScale.charAt(viewScale.length - 1) === 's') {
                viewScale = viewScale.substring(0, viewScale.length - 1);
            }
            var viewScaleValue;
            var viewScaleUnit;
            var splittedViewScale;

            if (viewScale) {
                splittedViewScale = viewScale.split(' ');
            }
            if (splittedViewScale && splittedViewScale.length > 1) {
                viewScaleValue = parseFloat(splittedViewScale[0]);
                viewScaleUnit = splittedViewScale[splittedViewScale.length - 1];
            } else {
                viewScaleValue = 1;
                viewScaleUnit = viewScale;
            }

            var excludeTo = false;
            from = moment(from).startOf(viewScaleUnit);
            if (to) {
                excludeTo = isToDateToExclude(to);
                to = moment(to).startOf(viewScaleUnit);
            }

            var left = 0;
            var date = moment(from).startOf(viewScaleUnit);
            if (reverse) {
                date.add(-viewScaleValue, viewScaleUnit);
                left -= columnWidth;
            }
            var generatedCols = [];

            while (true) {
                if (maximumWidth && Math.abs(left) > maximumWidth + columnWidth) {
                    break;
                }

                var startDate = moment(date);
                var endDate = moment(startDate).add(viewScaleValue, viewScaleUnit);
                ensureNoUnitOverflow(viewScaleUnit, startDate, endDate);

                var column = builder.newColumn(startDate, endDate, leftOffset ? left + leftOffset : left, columnWidth);

                if (!column.cropped) {
                    generatedCols.push(column);
                    if (reverse) {
                        left -= columnWidth;
                    } else {
                        left += columnWidth;
                    }
                }
                if (to) {
                    if (reverse) {
                        if (excludeTo && date < to || !excludeTo && date <= to) {
                            break;
                        }
                    } else {
                        if (excludeTo && date > to || !excludeTo && date >= to) {
                            break;
                        }
                    }
                }
                if (reverse) {
                    date.add(-viewScaleValue, viewScaleUnit);
                    ensureNoUnitOverflow(viewScaleUnit, date, startDate);
                } else {
                    date.add(viewScaleValue, viewScaleUnit);
                    ensureNoUnitOverflow(viewScaleUnit, startDate, date);
                }
            }

            if (reverse) {
                if (isToDateToExclude(from, viewScaleValue, viewScaleUnit)) {
                    generatedCols.shift();
                }
                generatedCols.reverse();
            }

            return generatedCols;
        };
    }]);
}());

(function(){
    'use strict';
    angular.module('gantt').factory('GanttColumnHeader', [ 'moment', 'GanttColumn', function(moment, Column) {
        // Used to display the Gantt grid and header.
        // The columns are generated by the column generator.

        var ColumnHeader = function(startDate, endDate, viewScaleUnit, left, width, labelFormat) {
            startDate = moment(startDate);
            endDate = moment(endDate);

            var column = new Column(startDate, endDate, left, width);
            column.unit = viewScaleUnit;
            column.label = angular.isFunction(labelFormat) ? labelFormat(column): startDate.format(labelFormat);

            return column;
        };
        return ColumnHeader;
    }]);
}());


(function(){
    'use strict';
    angular.module('gantt').factory('GanttColumnsManager', ['GanttColumnGenerator', 'GanttColumnBuilder', 'GanttHeadersGenerator', '$filter', '$timeout', 'ganttLayout', 'ganttBinarySearch', 'moment', function(ColumnGenerator, ColumnBuilder, HeadersGenerator, $filter, $timeout, layout, bs, moment) {
        var ColumnsManager = function(gantt) {
            var self = this;

            this.gantt = gantt;

            this.from = undefined;
            this.to = undefined;

            this.columns = [];
            this.visibleColumns = [];
            this.previousColumns = [];
            this.nextColumns = [];

            this.headers = [];
            this.visibleHeaders = [];

            this.scrollAnchor = undefined;

            this.columnBuilder = new ColumnBuilder(this);

            // Add a watcher if a view related setting changed from outside of the Gantt. Update the gantt accordingly if so.
            // All those changes need a recalculation of the header columns
            this.gantt.$scope.$watchGroup(['viewScale', 'columnWidth', 'timeFramesWorkingMode', 'timeFramesNonWorkingMode', 'fromDate', 'toDate', 'autoExpand', 'taskOutOfRange'], function(newValues, oldValues) {
                if (newValues !== oldValues && self.gantt.rendered) {
                    self.generateColumns();
                }
            });

            this.gantt.$scope.$watchCollection('headers', function(newValues, oldValues) {
                if (newValues !== oldValues && self.gantt.rendered) {
                    self.generateColumns();
                }
            });

            this.gantt.$scope.$watchCollection('headersFormats', function(newValues, oldValues) {
                if (newValues !== oldValues && self.gantt.rendered) {
                    self.generateColumns();
                }
            });

            this.gantt.$scope.$watchGroup(['ganttElementWidth', 'showSide', 'sideWidth', 'maxHeight', 'daily'], function(newValues, oldValues) {
                if (newValues !== oldValues && self.gantt.rendered) {
                    self.updateColumnsMeta();
                }
            });

            this.gantt.api.data.on.load(this.gantt.$scope, function() {
                if ((self.from === undefined || self.to === undefined ||
                    self.from > self.gantt.rowsManager.getDefaultFrom() ||
                    self.to < self.gantt.rowsManager.getDefaultTo()) && self.gantt.rendered) {
                    self.generateColumns();
                }

                self.gantt.rowsManager.sortRows();
            });

            this.gantt.api.data.on.remove(this.gantt.$scope, function() {
                self.gantt.rowsManager.sortRows();
            });

            this.gantt.api.registerMethod('columns', 'clear', this.clearColumns, this);
            this.gantt.api.registerMethod('columns', 'generate', this.generateColumns, this);
            this.gantt.api.registerMethod('columns', 'refresh', this.updateColumnsMeta, this);
            this.gantt.api.registerMethod('columns', 'getColumnsWidth', this.getColumnsWidth, this);
            this.gantt.api.registerMethod('columns', 'getColumnsWidthToFit', this.getColumnsWidthToFit, this);
            this.gantt.api.registerMethod('columns', 'getDateRange', this.getDateRange, this);

            this.gantt.api.registerEvent('columns', 'clear');
            this.gantt.api.registerEvent('columns', 'generate');
            this.gantt.api.registerEvent('columns', 'refresh');
        };

        ColumnsManager.prototype.setScrollAnchor = function() {
            if (this.gantt.scroll.$element && this.columns.length > 0) {
                var el = this.gantt.scroll.$element[0];
                var center = el.scrollLeft + el.offsetWidth / 2;

                this.scrollAnchor = this.gantt.getDateByPosition(center);
            }
        };

        ColumnsManager.prototype.scrollToScrollAnchor = function() {
            var self = this;

            if (this.columns.length > 0 && this.scrollAnchor !== undefined) {
                // Ugly but prevents screen flickering (unlike $timeout)
                this.gantt.$scope.$$postDigest(function() {
                    self.gantt.api.scroll.toDate(self.scrollAnchor);
                });
            }
        };

        ColumnsManager.prototype.clearColumns = function() {
            this.setScrollAnchor();

            this.from = undefined;
            this.to = undefined;

            this.columns = [];
            this.visibleColumns = [];
            this.previousColumns = [];
            this.nextColumns = [];

            this.headers = [];
            this.visibleHeaders = [];

            this.gantt.api.columns.raise.clear();
        };

        ColumnsManager.prototype.generateColumns = function(from, to) {
            if (!from) {
                from = this.gantt.options.value('fromDate');
            }

            if (!to) {
                to = this.gantt.options.value('toDate');
            }

            if (!from || (moment.isMoment(from) && !from.isValid())) {
                from = this.gantt.rowsManager.getDefaultFrom();
                if (!from) {
                    return false;
                }
            }

            if (!to || (moment.isMoment(to) && !to.isValid())) {
                to = this.gantt.rowsManager.getDefaultTo();
                if (!to) {
                    return false;
                }
            }

            if (from !== undefined && !moment.isMoment(from)) {
                from = moment(from);
            }

            if (to !== undefined && !moment.isMoment(to)) {
                to = moment(to);
            }

            if (this.gantt.options.value('taskOutOfRange') === 'expand') {
                from = this.gantt.rowsManager.getExpandedFrom(from);
                to = this.gantt.rowsManager.getExpandedTo(to);
            }

            this.setScrollAnchor();

            this.from = from;
            this.to = to;

            this.columns = ColumnGenerator.generate(this.columnBuilder, from, to, this.gantt.options.value('viewScale'), this.getColumnsWidth());
            this.headers = HeadersGenerator.generate(this);
            this.previousColumns = [];
            this.nextColumns = [];

            this.updateColumnsMeta();
            this.scrollToScrollAnchor();

            this.gantt.api.columns.raise.generate(this.columns, this.headers);
        };

        ColumnsManager.prototype.updateColumnsMeta = function() {
            this.gantt.isRefreshingColumns = true;

            var lastColumn = this.getLastColumn();
            this.gantt.originalWidth = lastColumn !== undefined ? lastColumn.originalSize.left + lastColumn.originalSize.width : 0;

            var columnsWidthChanged = this.updateColumnsWidths(this.columns,  this.headers, this.previousColumns, this.nextColumns);

            this.gantt.width = lastColumn !== undefined ? lastColumn.left + lastColumn.width : 0;

            var showSide = this.gantt.options.value('showSide');
            var sideShown = this.gantt.side.isShown();
            var sideVisibilityChanged = showSide !== sideShown;

            if (sideVisibilityChanged && !showSide) {
                // Prevent unnecessary v-scrollbar if side is hidden here
                this.gantt.side.show(false);
            }

            this.gantt.rowsManager.updateTasksPosAndSize();
            this.gantt.timespansManager.updateTimespansPosAndSize();

            this.updateVisibleColumns(columnsWidthChanged);

            this.gantt.rowsManager.updateVisibleObjects();

            var currentDateValue = this.gantt.options.value('currentDateValue');
            this.gantt.currentDateManager.setCurrentDate(currentDateValue);

            if (sideVisibilityChanged && showSide) {
                // Prevent unnecessary v-scrollbar if side is shown here
                this.gantt.side.show(true);
            }

            this.gantt.isRefreshingColumns = false;
            this.gantt.api.columns.raise.refresh(this.columns, this.headers);
        };

        // Returns the last Gantt column or undefined
        ColumnsManager.prototype.getLastColumn = function(extended) {
            var columns = this.columns;
            if (extended) {
                columns = this.nextColumns;
            }
            if (columns && columns.length > 0) {
                return columns[columns.length - 1];
            } else {
                return undefined;
            }
        };

        // Returns the first Gantt column or undefined
        ColumnsManager.prototype.getFirstColumn = function(extended) {
            var columns = this.columns;
            if (extended) {
                columns = this.previousColumns;
            }

            if (columns && columns.length > 0) {
                return columns[0];
            } else {
                return undefined;
            }
        };

        // Returns the column at the given or next possible date
        ColumnsManager.prototype.getColumnByDate = function(date, disableExpand) {
            if (!disableExpand) {
                this.expandExtendedColumnsForDate(date);
            }
            var extendedColumns = this.previousColumns.concat(this.columns, this.nextColumns);
            var columns = bs.get(extendedColumns, date, function(c) {
                return c.date;
            }, true);
            return columns[0] === undefined ? columns[1] : columns[0];
        };

        // Returns the column at the given position x (in em)
        ColumnsManager.prototype.getColumnByPosition = function(x, disableExpand) {
            if (!disableExpand) {
                this.expandExtendedColumnsForPosition(x);
            }
            var extendedColumns = this.previousColumns.concat(this.columns, this.nextColumns);
            var columns = bs.get(extendedColumns, x, function(c) {
                return c.left;
            }, true);
            return columns[0] === undefined ? columns[1]: columns[0];
        };

        ColumnsManager.prototype.updateColumnsWidths = function(columns,  headers, previousColumns, nextColumns) {
            var columnWidth = this.gantt.options.value('columnWidth');
            var expandToFit = this.gantt.options.value('expandToFit');
            var shrinkToFit = this.gantt.options.value('shrinkToFit');

            if (columnWidth === undefined || expandToFit || shrinkToFit) {
                var newWidth = this.gantt.getBodyAvailableWidth();

                var lastColumn = this.gantt.columnsManager.getLastColumn(false);
                if (lastColumn !== undefined) {
                    var currentWidth = lastColumn.originalSize.left + lastColumn.originalSize.width;

                    if (expandToFit && currentWidth < newWidth ||
                        shrinkToFit && currentWidth > newWidth ||
                        columnWidth === undefined
                    ) {
                        var widthFactor = newWidth / currentWidth;

                        layout.setColumnsWidthFactor(columns, widthFactor);
                        for (var i=0; i< headers.length; i++) {
                            layout.setColumnsWidthFactor(headers[i], widthFactor);
                        }
                        // previous and next columns will be generated again on need.
                        previousColumns.splice(0, this.previousColumns.length);
                        nextColumns.splice(0, this.nextColumns.length);
                        return true;
                    }
                }
            }
            return false;
        };

        ColumnsManager.prototype.getColumnsWidth = function() {
            var columnWidth = this.gantt.options.value('columnWidth');
            if (columnWidth === undefined) {
                if (!this.gantt.width || this.gantt.width <= 0) {
                    columnWidth = 20;
                } else {
                    columnWidth = this.gantt.width / this.columns.length;
                }
            }
            return columnWidth;
        };

        ColumnsManager.prototype.getColumnsWidthToFit = function() {
            return this.gantt.getBodyAvailableWidth() / this.columns.length;
        };

        ColumnsManager.prototype.expandExtendedColumnsForPosition = function(x) {
            var viewScale;
            if (x < 0) {
                var firstColumn = this.getFirstColumn();
                var from = firstColumn.date;
                var firstExtendedColumn = this.getFirstColumn(true);
                if (!firstExtendedColumn || firstExtendedColumn.left > x) {
                    viewScale = this.gantt.options.value('viewScale');
                    this.previousColumns = ColumnGenerator.generate(this.columnBuilder, from, undefined, viewScale, this.getColumnsWidth(), -x, 0, true);
                }
                return true;
            } else if (x > this.gantt.width) {
                var lastColumn = this.getLastColumn();
                var endDate = lastColumn.getDateByPosition(lastColumn.width);
                var lastExtendedColumn = this.getLastColumn(true);
                if (!lastExtendedColumn || lastExtendedColumn.left + lastExtendedColumn.width < x) {
                    viewScale = this.gantt.options.value('viewScale');
                    this.nextColumns = ColumnGenerator.generate(this.columnBuilder, endDate, undefined, viewScale, this.getColumnsWidth(), x - this.gantt.width, this.gantt.width, false);
                }
                return true;
            }
            return false;
        };

        ColumnsManager.prototype.expandExtendedColumnsForDate = function(date) {
            var firstColumn = this.getFirstColumn();
            var from;
            if (firstColumn) {
                from = firstColumn.date;
            }

            var lastColumn = this.getLastColumn();
            var endDate;
            if (lastColumn) {
                endDate = lastColumn.getDateByPosition(lastColumn.width);
            }

            var viewScale;
            if (from && date < from) {
                var firstExtendedColumn = this.getFirstColumn(true);
                if (!firstExtendedColumn || firstExtendedColumn.date > date) {
                    viewScale = this.gantt.options.value('viewScale');
                    this.previousColumns = ColumnGenerator.generate(this.columnBuilder, from, date, viewScale, this.getColumnsWidth(), undefined, 0, true);
                }
                return true;
            } else if (endDate && date >= endDate) {
                var lastExtendedColumn = this.getLastColumn(true);
                if (!lastExtendedColumn || lastExtendedColumn.date < endDate) {
                    viewScale = this.gantt.options.value('viewScale');
                    this.nextColumns = ColumnGenerator.generate(this.columnBuilder, endDate, date, viewScale, this.getColumnsWidth(), undefined, this.gantt.width, false);
                }
                return true;
            }
            return false;
        };

        // Returns the number of active headers
        ColumnsManager.prototype.getActiveHeadersCount = function() {
            return this.headers.length;
        };

        ColumnsManager.prototype.updateVisibleColumns = function(includeViews) {
            this.visibleColumns = $filter('ganttColumnLimit')(this.columns, this.gantt);

            this.visibleHeaders = [];
            for (var i=0; i< this.headers.length; i++) {
                this.visibleHeaders.push($filter('ganttColumnLimit')(this.headers[i], this.gantt));
            }

            if (includeViews) {
                for (i=0; i<this.visibleColumns.length; i++) {
                    this.visibleColumns[i].updateView();
                }
                for (i=0; i<this.visibleHeaders.length; i++) {
                    var headerRow = this.visibleHeaders[i];
                    for (var j=0; j<headerRow.length; j++) {
                        headerRow[j].updateView();
                    }
                }
            }

            var currentDateValue = this.gantt.options.value('currentDateValue');
            this.gantt.currentDateManager.setCurrentDate(currentDateValue);
        };

        var defaultHeadersFormats = {'year': 'YYYY', 'quarter': '[Q]Q YYYY', month: 'MMMM YYYY', week: 'w', day: 'D', hour: 'H', minute:'HH:mm'};
        var defaultDayHeadersFormats = {day: 'LL', hour: 'H', minute:'HH:mm'};
        var defaultYearHeadersFormats = {'year': 'YYYY', 'quarter': '[Q]Q', month: 'MMMM'};

        ColumnsManager.prototype.getHeaderFormat = function(unit) {
            var format;
            var headersFormats = this.gantt.options.value('headersFormats');
            if (headersFormats !== undefined) {
                format = headersFormats[unit];
            }
            if (format === undefined) {
                var viewScale = this.gantt.options.value('viewScale');
                viewScale = viewScale.trim();
                if (viewScale.charAt(viewScale.length - 1) === 's') {
                    viewScale = viewScale.substring(0, viewScale.length - 1);
                }

                var viewScaleUnit;
                var splittedViewScale;

                if (viewScale) {
                    splittedViewScale = viewScale.split(' ');
                }
                if (splittedViewScale && splittedViewScale.length > 1) {
                    viewScaleUnit = splittedViewScale[splittedViewScale.length - 1];
                } else {
                    viewScaleUnit = viewScale;
                }

                if (['millisecond', 'second', 'minute', 'hour'].indexOf(viewScaleUnit) > -1) {
                    format = defaultDayHeadersFormats[unit];
                } else if (['month', 'quarter', 'year'].indexOf(viewScaleUnit) > -1) {
                    format = defaultYearHeadersFormats[unit];
                }
                if (format === undefined) {
                    format = defaultHeadersFormats[unit];
                }
            }
            return format;
        };

        ColumnsManager.prototype.getHeaderScale = function(header) {
            var scale;
            var headersScales = this.gantt.options.value('headersScales');
            if (headersScales !== undefined) {
                scale = headersScales[header];
            }
            if (scale === undefined) {
                scale = header;
            }
            if (['second', 'minute', 'hour', 'day', 'week', 'month', 'quarter', 'year'].indexOf(scale) === -1) {
                scale = 'day';
            }
            return scale;
        };

        ColumnsManager.prototype.getDateRange = function(visibleOnly) {
            var firstColumn, lastColumn;

            if (visibleOnly) {
                if (this.visibleColumns && this.visibleColumns.length > 0) {
                    firstColumn = this.visibleColumns[0];
                    lastColumn = this.visibleColumns[this.visibleColumns.length - 1];
                }
            } else {
                firstColumn = this.getFirstColumn();
                lastColumn = this.getLastColumn();
            }

            return firstColumn && lastColumn ? [firstColumn.date, lastColumn.endDate]: undefined;
        };

        return ColumnsManager;
    }]);
}());

(function(){
    'use strict';
    angular.module('gantt').service('GanttHeadersGenerator', ['GanttColumnHeader', 'moment', function(ColumnHeader, moment) {
        var generateHeader = function(columnsManager, value) {
            var generatedHeaders = [];
            var header;

            var viewScale = columnsManager.getHeaderScale(value);

            var viewScaleValue;
            var viewScaleUnit;
            var splittedViewScale;

            if (viewScale) {
                splittedViewScale = viewScale.split(' ');
            }
            if (splittedViewScale && splittedViewScale.length > 1) {
                viewScaleValue = parseFloat(splittedViewScale[0]);
                viewScaleUnit = splittedViewScale[splittedViewScale.length - 1];
            } else {
                viewScaleValue = 1;
                viewScaleUnit = viewScale;
            }

            var currentColumn = columnsManager.columns[0];
            var currentDate = moment(currentColumn.date).startOf(viewScaleUnit);

            var maximumDate = moment(columnsManager.columns[columnsManager.columns.length - 1].endDate);

            while (true) {
                var currentPosition = currentColumn.getPositionByDate(currentDate);

                var endDate = moment.min(moment(currentDate).add(viewScaleValue, viewScaleUnit), maximumDate);

                var column = columnsManager.getColumnByDate(endDate, true);

                var left = column.getPositionByDate(endDate);

                var width = left - currentPosition;

                if (width > 0) {
                    var labelFormat = columnsManager.getHeaderFormat(value);

                    header = new ColumnHeader(currentDate, endDate, viewScaleUnit, currentPosition, width, labelFormat);
                    generatedHeaders.push(header);
                }

                if (endDate.isSame(maximumDate) || endDate.isAfter(maximumDate)) {
                    break;
                }

                currentColumn = column;
                currentDate = endDate;
            }

            return generatedHeaders;
        };

        this.generate = function(columnsManager) {
            var units = [];
            if (columnsManager.gantt.options.value('headers') === undefined) {
                var viewScale = columnsManager.gantt.options.value('viewScale');
                viewScale = viewScale.trim();
                if (viewScale.charAt(viewScale.length - 1) === 's') {
                    viewScale = viewScale.substring(0, viewScale.length - 1);
                }

                var viewScaleUnit;
                var splittedViewScale;

                if (viewScale) {
                    splittedViewScale = viewScale.split(' ');
                }
                if (splittedViewScale && splittedViewScale.length > 1) {
                    viewScaleUnit = splittedViewScale[splittedViewScale.length - 1];
                } else {
                    viewScaleUnit = viewScale;
                }

                if (['quarter','month'].indexOf(viewScaleUnit) > -1) {
                    units.push('year');
                }
                if (['day', 'week'].indexOf(viewScaleUnit) > -1) {
                    units.push('month');
                }
                if (['day'].indexOf(viewScaleUnit) > -1) {
                    units.push('week');
                }
                if (['hour'].indexOf(viewScaleUnit) > -1) {
                    units.push('day');
                }
                if (['minute', 'second'].indexOf(viewScaleUnit) > -1) {
                    units.push('hour');
                }
                if (['second'].indexOf(viewScaleUnit) > -1) {
                    units.push('minute');
                }
                units.push(viewScale);
            } else {
                units = columnsManager.gantt.options.value('headers');
            }

            var headers = [];
            for (var i=0; i<units.length; i++) {
                headers.push(generateHeader(columnsManager, units[i]));
            }

            return headers;
        };
    }]);
}());


(function() {
    'use strict';
    angular.module('gantt').factory('Gantt', [
        'GanttApi', 'GanttOptions', 'GanttCalendar', 'GanttScroll', 'GanttBody', 'GanttRowHeader', 'GanttHeader', 'GanttSide', 'GanttObjectModel', 'GanttRowsManager', 'GanttColumnsManager', 'GanttTimespansManager', 'GanttCurrentDateManager', 'ganttArrays', 'moment', '$document', '$timeout',
        function(GanttApi, Options, Calendar, Scroll, Body, RowHeader, Header, Side, ObjectModel, RowsManager, ColumnsManager, TimespansManager, CurrentDateManager, arrays, moment, $document, $timeout) {
            // Gantt logic. Manages the columns, rows and sorting functionality.
            var Gantt = function($scope, $element) {
                var self = this;

                this.$scope = $scope;
                this.$element = $element;

                this.options = new Options($scope, {
                    'api': angular.noop,
                    'data': [],
                    'timespans': [],
                    'viewScale': 'day',
                    'columnMagnet': '15 minutes',
                    'timeFramesMagnet': true,
                    'showSide': true,
                    'allowSideResizing': true,
                    'currentDate': 'line',
                    'currentDateValue': moment,
                    'autoExpand': 'none',
                    'taskOutOfRange': 'truncate',
                    'taskContent': '{{task.model.name}}',
                    'rowContent': '{{row.model.name}}',
                    'maxHeight': 0,
                    'timeFrames': [],
                    'dateFrames': [],
                    'timeFramesWorkingMode': 'hidden',
                    'timeFramesNonWorkingMode': 'visible'
                });

                this.api = new GanttApi(this);

                this.api.registerEvent('core', 'ready');
                this.api.registerEvent('core', 'rendered');

                this.api.registerEvent('directives', 'controller');
                this.api.registerEvent('directives', 'preLink');
                this.api.registerEvent('directives', 'postLink');
                this.api.registerEvent('directives', 'new');
                this.api.registerEvent('directives', 'destroy');

                this.api.registerEvent('data', 'change');
                this.api.registerEvent('data', 'load');
                this.api.registerEvent('data', 'remove');
                this.api.registerEvent('data', 'clear');

                this.api.registerMethod('core', 'getDateByPosition', this.getDateByPosition, this);
                this.api.registerMethod('core', 'getPositionByDate', this.getPositionByDate, this);

                this.api.registerMethod('data', 'load', this.loadData, this);
                this.api.registerMethod('data', 'remove', this.removeData, this);
                this.api.registerMethod('data', 'clear', this.clearData, this);
                this.api.registerMethod('data', 'get', this.getData, this);

                this.calendar = new Calendar(this);
                this.calendar.registerTimeFrames(this.options.value('timeFrames'));
                this.calendar.registerDateFrames(this.options.value('dateFrames'));

                this.api.registerMethod('timeframes', 'registerTimeFrames', this.calendar.registerTimeFrames, this.calendar);
                this.api.registerMethod('timeframes', 'clearTimeframes', this.calendar.clearTimeFrames, this.calendar);
                this.api.registerMethod('timeframes', 'registerDateFrames', this.calendar.registerDateFrames, this.calendar);
                this.api.registerMethod('timeframes', 'clearDateFrames', this.calendar.clearDateFrames, this.calendar);
                this.api.registerMethod('timeframes', 'registerTimeFrameMappings', this.calendar.registerTimeFrameMappings, this.calendar);
                this.api.registerMethod('timeframes', 'clearTimeFrameMappings', this.calendar.clearTimeFrameMappings, this.calendar);

                $scope.$watchGroup(['timeFrames', 'dateFrames'], function(newValues, oldValues) {
                    if (newValues !== oldValues) {
                        var timeFrames = newValues[0];
                        var dateFrames = newValues[1];

                        var oldTimeFrames = oldValues[0];
                        var oldDateFrames = oldValues[1];

                        var framesChanged = false;

                        if (!angular.equals(timeFrames, oldTimeFrames)) {
                            self.calendar.clearTimeFrames();
                            self.calendar.registerTimeFrames(timeFrames);
                            framesChanged = true;
                        }

                        if (!angular.equals(dateFrames, oldDateFrames)) {
                            self.calendar.clearDateFrames();
                            self.calendar.registerDateFrames(dateFrames);
                            framesChanged = true;
                        }

                        if (framesChanged) {
                            self.columnsManager.generateColumns();
                        }
                    }
                });

                $scope.$watch('columnMagnet', function() {
                    var splittedColumnMagnet;
                    var columnMagnet = self.options.value('columnMagnet');
                    if (columnMagnet) {
                        splittedColumnMagnet = columnMagnet.trim().split(' ');
                    }
                    if (splittedColumnMagnet && splittedColumnMagnet.length > 1) {
                        self.columnMagnetValue = parseFloat(splittedColumnMagnet[0]);
                        self.columnMagnetUnit = moment.normalizeUnits(splittedColumnMagnet[splittedColumnMagnet.length - 1]);
                    } else {
                        self.columnMagnetValue = 1;
                        self.columnMagnetUnit = moment.normalizeUnits(columnMagnet);
                    }
                });

                $scope.$watchGroup(['shiftColumnMagnet', 'viewScale'], function() {
                    var splittedColumnMagnet;
                    var shiftColumnMagnet = self.options.value('shiftColumnMagnet');
                    if (shiftColumnMagnet) {
                        splittedColumnMagnet = shiftColumnMagnet.trim().split(' ');
                    }
                    if (splittedColumnMagnet !== undefined && splittedColumnMagnet.length > 1) {
                        self.shiftColumnMagnetValue = parseFloat(splittedColumnMagnet[0]);
                        self.shiftColumnMagnetUnit = moment.normalizeUnits(splittedColumnMagnet[splittedColumnMagnet.length - 1]);
                    } else {
                        self.shiftColumnMagnetValue = 1;
                        self.shiftColumnMagnetUnit = moment.normalizeUnits(shiftColumnMagnet);
                    }
                });

                var keyHandler = function(e) {
                    self.shiftKey = e.shiftKey;
                    return true;
                };

                $document.on('keyup keydown', keyHandler);

                $scope.$on('$destroy', function() {
                    $document.off('keyup keydown', keyHandler);
                });

                this.scroll = new Scroll(this);
                this.body = new Body(this);
                this.header = new Header(this);
                this.side = new Side(this);

                this.objectModel = new ObjectModel(this.api);

                this.rowsManager = new RowsManager(this);
                this.columnsManager = new ColumnsManager(this);
                this.timespansManager = new TimespansManager(this);
                this.currentDateManager = new CurrentDateManager(this);

                this.originalWidth = 0;
                this.width = 0;

                if (angular.isFunction(this.$scope.api)) {
                    this.$scope.api(this.api);
                }

                var hasRowModelOrderChanged = function(data1, data2) {
                    if (data2 === undefined || data1.length !== data2.length) {
                        return true;
                    }

                    for (var i = 0, l = data1.length; i < l; i++) {
                        if (data1[i].id !== data2[i].id) {
                            return true;
                        }
                    }

                    return false;
                };

                $scope.$watchCollection('data', function(newData, oldData) {
                    if (oldData !== undefined) {
                        var toRemoveIds = arrays.getRemovedIds(newData, oldData);
                        if (toRemoveIds.length === oldData.length) {
                            self.rowsManager.removeAll();

                            // DEPRECATED
                            self.api.data.raise.clear();
                        } else {
                            for (var i = 0, l = toRemoveIds.length; i < l; i++) {
                                var toRemoveId = toRemoveIds[i];
                                self.rowsManager.removeRow(toRemoveId);
                            }

                            // DEPRECATED
                            var removedRows = [];
                            for(i = 0, l = oldData.length; i < l; i++){
                                if (toRemoveIds.indexOf(oldData[i].id) > -1) {
                                    removedRows.push(oldData[i]);
                                }
                            }
                            self.api.data.raise.remove(removedRows);
                        }
                    }

                    if (newData !== undefined) {
                        var modelOrderChanged = hasRowModelOrderChanged(newData, oldData);

                        if (modelOrderChanged) {
                            self.rowsManager.resetNonModelLists();
                        }

                        for (var j = 0, k = newData.length; j < k; j++) {
                            var rowData = newData[j];
                            self.rowsManager.addRow(rowData, modelOrderChanged);
                        }

                        self.api.data.raise.change(newData, oldData);

                        // DEPRECATED
                        self.api.data.raise.load(newData);
                    }
                });
            };

            // Returns the exact column date at the given position x (in em)
            Gantt.prototype.getDateByPosition = function(x, magnet, disableExpand) {
                var column = this.columnsManager.getColumnByPosition(x, disableExpand);
                if (column !== undefined) {
                    var magnetValue;
                    var magnetUnit;
                    if (magnet) {
                        if (this.shiftKey) {
                            if (this.shiftColumnMagnetValue !== undefined && this.shiftColumnMagnetUnit !== undefined) {
                                magnetValue = this.shiftColumnMagnetValue;
                                magnetUnit = this.shiftColumnMagnetUnit;
                            } else {
                                var viewScale = this.options.value('viewScale');
                                viewScale = viewScale.trim();
                                var viewScaleValue;
                                var viewScaleUnit;
                                var splittedViewScale;

                                if (viewScale) {
                                    splittedViewScale = viewScale.split(' ');
                                }
                                if (splittedViewScale && splittedViewScale.length > 1) {
                                    viewScaleValue = parseFloat(splittedViewScale[0]);
                                    viewScaleUnit = moment.normalizeUnits(splittedViewScale[splittedViewScale.length - 1]);
                                } else {
                                    viewScaleValue = 1;
                                    viewScaleUnit = moment.normalizeUnits(viewScale);
                                }
                                magnetValue = viewScaleValue * 0.25;
                                magnetUnit = viewScaleUnit;
                            }
                        } else {
                            magnetValue = this.columnMagnetValue;
                            magnetUnit = this.columnMagnetUnit;
                        }
                    }

                    return column.getDateByPosition(x - column.left, magnetValue, magnetUnit, this.options.value('timeFramesMagnet'));
                } else {
                    return undefined;
                }
            };

            Gantt.prototype.getBodyAvailableWidth = function() {
                var scrollWidth = this.getWidth() - this.side.getWidth();
                var borderWidth = this.scroll.getBordersWidth();
                var availableWidth = scrollWidth - (borderWidth !== undefined ? this.scroll.getBordersWidth() : 0);
                // Remove 1 pixel because of rounding issue in some cases.
                availableWidth = availableWidth - 1;
                return availableWidth;
            };

            // Returns the position inside the Gantt calculated by the given date
            Gantt.prototype.getPositionByDate = function(date, disableExpand) {
                if (date === undefined) {
                    return undefined;
                }

                if (!moment.isMoment(moment)) {
                    date = moment(date);
                }

                var column = this.columnsManager.getColumnByDate(date, disableExpand);
                if (column !== undefined) {
                    return column.getPositionByDate(date);
                } else {
                    return undefined;
                }
            };

            // DEPRECATED - Use $data instead.
            Gantt.prototype.loadData = function(data) {
                if (!angular.isArray(data)) {
                    data = data !== undefined ? [data] : [];
                }

                if (this.$scope.data === undefined) {
                    this.$scope.data = data;
                } else {
                    for (var i = 0, l = data.length; i < l; i++) {
                        var row = data[i];

                        var j = arrays.indexOfId(this.$scope.data, row.id);
                        if (j > -1) {
                            this.$scope.data[j] = row;
                        } else {
                            this.$scope.data.push(row);
                        }
                    }
                }

                var w = this.side.getWidth();
                if (w > 0) {
                    this.options.set('sideWidth', w);
                }
            };

            Gantt.prototype.getData = function() {
                return this.$scope.data;
            };

            // DEPRECATED - Use $data instead.
            Gantt.prototype.removeData = function(data) {
                if (!angular.isArray(data)) {
                    data = data !== undefined ? [data] : [];
                }

                if (this.$scope.data !== undefined) {
                    for (var i = 0, l = data.length; i < l; i++) {
                        var rowToRemove = data[i];

                        var j = arrays.indexOfId(this.$scope.data, rowToRemove.id);
                        if (j > -1) {
                            if (rowToRemove.tasks === undefined || rowToRemove.tasks.length === 0) {
                                // Remove complete row
                                this.$scope.data.splice(j, 1);
                            } else {
                                // Remove single tasks
                                var row = this.$scope.data[j];
                                for (var ti = 0, tl = rowToRemove.tasks.length; ti < tl; ti++) {
                                    var taskToRemove = rowToRemove.tasks[ti];

                                    var tj = arrays.indexOfId(row.tasks, taskToRemove.id);
                                    if (tj > -1) {
                                        row.tasks.splice(tj, 1);
                                    }
                                }
                            }
                        }
                    }
                }
            };

            // DEPRECATED - Use $data instead.
            Gantt.prototype.clearData = function() {
                this.$scope.data = undefined;
            };

            Gantt.prototype.getWidth = function() {
                return this.$scope.ganttElementWidth;
            };

            Gantt.prototype.initialized = function() {
                // Gantt is initialized. Signal that the Gantt is ready.
                this.api.core.raise.ready(this.api);

                this.rendered = true;
                this.columnsManager.generateColumns();

                var gantt = this;
                var renderedFunction = function() {
                    var w = gantt.side.getWidth();
                    if (w > 0) {
                        gantt.options.set('sideWidth', w);
                    }
                    gantt.api.core.raise.rendered(gantt.api);
                };
                $timeout(renderedFunction);
            };

            return Gantt;
        }]);
}());

(function(){
    'use strict';
    angular.module('gantt').factory('GanttObjectModel', ['ganttUtils', 'moment', function(utils, moment) {
        var ObjectModel = function(api) {
            this.api = api;

            this.api.registerEvent('tasks', 'clean');
            this.api.registerEvent('rows', 'clean');
            this.api.registerEvent('timespans', 'clean');
        };

        ObjectModel.prototype.cleanTask = function(model) {
            if (model.id === undefined) {
                model.id = utils.randomUuid();
            }

            if (model.from !== undefined && !moment.isMoment(model.from)) {
                model.from = moment(model.from);
            }

            if (model.to !== undefined && !moment.isMoment(model.to)) {
                model.to = moment(model.to);
            }

            this.api.tasks.raise.clean(model);
        };

        ObjectModel.prototype.cleanRow = function(model) {
            if (model.id === undefined) {
                model.id = utils.randomUuid();
            }

            if (model.from !== undefined && !moment.isMoment(model.from)) {
                model.from = moment(model.from);
            }

            if (model.to !== undefined && !moment.isMoment(model.to)) {
                model.to = moment(model.to);
            }

            this.api.rows.raise.clean(model);
        };

        ObjectModel.prototype.cleanTimespan = function(model) {
            if (model.id === undefined) {
                model.id = utils.randomUuid();
            }

            if (model.from !== undefined && !moment.isMoment(model.from)) {
                model.from = moment(model.from);
            }

            if (model.to !== undefined && !moment.isMoment(model.to)) {
                model.to = moment(model.to);
            }

            this.api.timespans.raise.clean(model);
        };

        return ObjectModel;
    }]);
}());


(function(){
    'use strict';
    angular.module('gantt').factory('GanttRow', ['GanttTask', 'moment', '$filter', function(Task, moment, $filter) {
        var Row = function(rowsManager, model) {
            this.rowsManager = rowsManager;
            this.model = model;

            this.from = undefined;
            this.to = undefined;

            this.tasksMap = {};
            this.tasks = [];
            this.filteredTasks = [];
            this.visibleTasks = [];
        };

        Row.prototype.addTaskImpl = function(task, viewOnly) {
            this.tasksMap[task.model.id] = task;
            this.tasks.push(task);

            if (!viewOnly) {
                if (this.model.tasks === undefined) {
                    this.model.tasks = [];
                }
                if (this.model.tasks.indexOf(task.model) === -1) {
                    this.model.tasks.push(task.model);
                }
            }

        };

        // Adds a task to a specific row. Merges the task if there is already one with the same id
        Row.prototype.addTask = function(taskModel, viewOnly) {
            // Copy to new task (add) or merge with existing (update)
            var task, isUpdate = false;

            this.rowsManager.gantt.objectModel.cleanTask(taskModel);
            if (taskModel.id in this.tasksMap) {
                task = this.tasksMap[taskModel.id];
                if (task.model === taskModel) {
                    return task;
                }
                task.model = taskModel;
                isUpdate = true;
            } else {
                task = new Task(this, taskModel);
                this.addTaskImpl(task, viewOnly);
            }

            this.sortTasks();
            this.setFromToByTask(task);

            if (!viewOnly) {
                if (isUpdate) {
                    this.rowsManager.gantt.api.tasks.raise.change(task);
                } else {
                    this.rowsManager.gantt.api.tasks.raise.add(task);
                }
            }

            return task;
        };

        // Removes the task from the existing row and adds it to he current one
        Row.prototype.moveTaskToRow = function(task, viewOnly) {
            var oldRow = task.row;
            oldRow.removeTask(task.model.id, viewOnly, true);

            task.row = this;
            this.addTaskImpl(task, viewOnly);

            this.sortTasks();
            this.setFromToByTask(task);

            task.updatePosAndSize();
            this.updateVisibleTasks();

            oldRow.$scope.$digest();
            task.row.$scope.$digest();

            this.rowsManager.gantt.api.tasks.raise.viewRowChange(task, oldRow);
            if (!viewOnly) {
                this.rowsManager.gantt.api.tasks.raise.rowChange(task, oldRow);
            }
        };

        Row.prototype.updateVisibleTasks = function() {
            var filterTask = this.rowsManager.gantt.options.value('filterTask');
            if (filterTask) {
                if (typeof(filterTask) === 'object') {
                    filterTask = {model: filterTask};
                }

                var filterTaskComparator = this.rowsManager.gantt.options.value('filterTaskComparator');
                if (typeof(filterTaskComparator) === 'function') {
                    filterTaskComparator = function(actual, expected) {
                        return filterTaskComparator(actual.model, expected.model);
                    };
                }

                this.filteredTasks = $filter('filter')(this.tasks, filterTask, filterTaskComparator);
            } else {
                this.filteredTasks = this.tasks.slice(0);
            }
            this.visibleTasks = $filter('ganttTaskLimit')(this.filteredTasks, this.rowsManager.gantt);
        };

        Row.prototype.updateTasksPosAndSize = function() {
            for (var j = 0, k = this.tasks.length; j < k; j++) {
                this.tasks[j].updatePosAndSize();
            }
        };

        // Remove the specified task from the row
        Row.prototype.removeTask = function(taskId, viewOnly, silent) {
            if (taskId in this.tasksMap) {
                var removedTask = this.tasksMap[taskId];
                var task;
                var i;

                for (i = this.tasks.length - 1; i >= 0; i--) {
                    task = this.tasks[i];
                    if (task.model.id === taskId) {
                        this.tasks.splice(i, 1); // Remove from array

                        // Update earliest or latest date info as this may change
                        if (this.from - task.model.from === 0 || this.to - task.model.to === 0) {
                            this.setFromTo();
                        }

                        break;
                    }
                }

                for (i = this.filteredTasks.length - 1; i >= 0; i--) {
                    task = this.filteredTasks[i];
                    if (task.model.id === taskId) {
                        this.filteredTasks.splice(i, 1); // Remove from filtered array
                        break;
                    }
                }

                for (i = this.visibleTasks.length - 1; i >= 0; i--) {
                    task = this.visibleTasks[i];
                    if (task.model.id === taskId) {
                        this.visibleTasks.splice(i, 1); // Remove from visible array
                        break;
                    }
                }

                if (!viewOnly) {
                    delete this.tasksMap[taskId]; // Remove from map

                    if (this.model.tasks !== undefined) {
                        var taskIndex = this.model.tasks.indexOf(removedTask.model);
                        if (taskIndex > -1) {
                            this.model.tasks.splice(taskIndex, 1);
                        }
                    }

                    if (!silent) {
                        this.rowsManager.gantt.api.tasks.raise.remove(removedTask);
                    }
                }

                return removedTask;
            }
        };

        Row.prototype.removeAllTasks = function() {
            this.from = undefined;
            this.to = undefined;

            this.tasksMap = {};
            this.tasks = [];
            this.filteredTasks = [];
            this.visibleTasks = [];
        };

        // Calculate the earliest from and latest to date of all tasks in a row
        Row.prototype.setFromTo = function() {
            this.from = undefined;
            this.to = undefined;
            for (var j = 0, k = this.tasks.length; j < k; j++) {
                this.setFromToByTask(this.tasks[j]);
            }
        };

        Row.prototype.setFromToByTask = function(task) {
            this.setFromToByValues(task.model.from, task.model.to);
        };

        Row.prototype.setFromToByValues = function(from, to) {
            if (from !== undefined) {
                if (this.from === undefined) {
                    this.from = moment(from);
                } else if (from < this.from) {
                    this.from = moment(from);
                }
            }

            if (to !== undefined) {
                if (this.to === undefined) {
                    this.to = moment(to);
                } else if (to > this.to) {
                    this.to = moment(to);
                }
            }

        };

        Row.prototype.sortTasks = function() {
            this.tasks.sort(function(t1, t2) {
                return t1.left - t2.left;
            });
        };

        Row.prototype.clone = function() {
            var clone = new Row(this.rowsManager, angular.copy(this));
            for (var i = 0, l = this.tasks.length; i < l; i++) {
                clone.addTask(this.tasks[i].model);
            }
            return clone;
        };

        return Row;
    }]);
}());


(function(){
    'use strict';
    angular.module('gantt').factory('GanttRowHeader', [function() {
        var RowHeader = function(gantt) {
            this.gantt = gantt;
        };
        return RowHeader;
    }]);
}());


(function() {
    'use strict';
    angular.module('gantt').factory('GanttRowsManager', ['GanttRow', 'ganttArrays', '$filter', '$timeout', 'moment', function(Row, arrays, $filter, $timeout, moment) {
        var RowsManager = function(gantt) {
            var self = this;

            this.gantt = gantt;

            this.rowsMap = {};
            this.rows = [];
            this.sortedRows = [];
            this.filteredRows = [];
            this.customFilteredRows = [];
            this.visibleRows = [];
            this.rowsTaskWatchers = [];

            this._defaultFilterImpl = function(sortedRows, filterRow, filterRowComparator) {
                return $filter('filter')(sortedRows, filterRow, filterRowComparator);
            };
            this.filterImpl = this._defaultFilterImpl;

            this.customRowSorters = [];
            this.customRowFilters = [];

            this.gantt.$scope.$watchGroup(['filterTask', 'filterTaskComparator'], function(newValues, oldValues) {
                if (newValues !== oldValues) {
                    self.updateVisibleTasks();
                }
            });

            this.gantt.$scope.$watchGroup(['filterRow', 'filterRowComparator'], function(newValues, oldValues) {
                if (newValues !== oldValues) {
                    self.updateVisibleRows();
                }
            });

            this.gantt.$scope.$watch('sortMode', function(newValue, oldValue) {
                if (newValue !== oldValue) {
                    self.sortRows();
                }
            });

            // Listen to vertical scrollbar visibility changes to update columns width
            var _oldVScrollbarVisible = this.gantt.scroll.isVScrollbarVisible();
            this.gantt.$scope.$watchGroup(['maxHeight', 'gantt.rowsManager.visibleRows.length'], function(newValue, oldValue) {
                if (newValue !== oldValue) {
                    $timeout(function() {
                        var newVScrollbarVisible = self.gantt.scroll.isVScrollbarVisible();
                        if (newVScrollbarVisible !== _oldVScrollbarVisible) {
                            _oldVScrollbarVisible = newVScrollbarVisible;
                            self.gantt.columnsManager.updateColumnsMeta();
                        }
                    });
                }
            });

            this.gantt.api.registerMethod('rows', 'sort', RowsManager.prototype.sortRows, this);
            this.gantt.api.registerMethod('rows', 'applySort', RowsManager.prototype.applySort, this);
            this.gantt.api.registerMethod('rows', 'refresh', RowsManager.prototype.updateVisibleObjects, this);

            this.gantt.api.registerMethod('rows', 'removeRowSorter', RowsManager.prototype.removeCustomRowSorter, this);
            this.gantt.api.registerMethod('rows', 'addRowSorter', RowsManager.prototype.addCustomRowSorter, this);

            this.gantt.api.registerMethod('rows', 'removeRowFilter', RowsManager.prototype.removeCustomRowFilter, this);
            this.gantt.api.registerMethod('rows', 'addRowFilter', RowsManager.prototype.addCustomRowFilter, this);

            this.gantt.api.registerMethod('rows', 'setFilterImpl', RowsManager.prototype.setFilterImpl, this);

            this.gantt.api.registerEvent('tasks', 'add');
            this.gantt.api.registerEvent('tasks', 'change');
            this.gantt.api.registerEvent('tasks', 'viewChange');

            this.gantt.api.registerEvent('tasks', 'rowChange');
            this.gantt.api.registerEvent('tasks', 'viewRowChange');
            this.gantt.api.registerEvent('tasks', 'remove');
            this.gantt.api.registerEvent('tasks', 'filter');

            this.gantt.api.registerEvent('tasks', 'displayed');

            this.gantt.api.registerEvent('rows', 'add');
            this.gantt.api.registerEvent('rows', 'change');
            this.gantt.api.registerEvent('rows', 'remove');
            this.gantt.api.registerEvent('rows', 'move');

            this.gantt.api.registerEvent('rows', 'displayed');

            this.gantt.api.registerEvent('rows', 'filter');

            this.updateVisibleObjects();
        };

        RowsManager.prototype.resetNonModelLists = function() {
            this.rows = [];
            this.sortedRows = [];
            this.filteredRows = [];
            this.customFilteredRows = [];
            this.visibleRows = [];
        };

        RowsManager.prototype.addRow = function(rowModel, modelOrderChanged) {
            // Copy to new row (add) or merge with existing (update)
            var row, i, l, isUpdate = false;

            this.gantt.objectModel.cleanRow(rowModel);

            if (rowModel.id in this.rowsMap) {
                row = this.rowsMap[rowModel.id];

                if (modelOrderChanged) {
                    this.rows.push(row);
                    this.sortedRows.push(row);
                    this.filteredRows.push(row);
                    this.customFilteredRows.push(row);
                    this.visibleRows.push(row);
                }

                if (row.model === rowModel) {
                    return;
                }

                var toRemoveIds = arrays.getRemovedIds(rowModel.tasks, row.model.tasks);
                for (i = 0, l = toRemoveIds.length; i < l; i++) {
                    var toRemoveId = toRemoveIds[i];
                    row.removeTask(toRemoveId);
                }

                row.model = rowModel;
                isUpdate = true;
            } else {
                row = new Row(this, rowModel);
                this.rowsMap[rowModel.id] = row;
                this.rows.push(row);
                this.sortedRows.push(row);
                this.filteredRows.push(row);
                this.customFilteredRows.push(row);
                this.visibleRows.push(row);
            }

            if (rowModel.tasks !== undefined && rowModel.tasks.length > 0) {
                for (i = 0, l = rowModel.tasks.length; i < l; i++) {
                    var taskModel = rowModel.tasks[i];
                    row.addTask(taskModel);
                }

                row.updateVisibleTasks();
            }

            if (isUpdate) {
                this.gantt.api.rows.raise.change(row);
            } else {
                this.gantt.api.rows.raise.add(row);
            }

            if (!isUpdate) {
                var watcher = this.gantt.$scope.$watchCollection(function() {
                    return rowModel.tasks;
                }, function(newTasks, oldTasks) {
                    if (newTasks !== oldTasks) {
                        var i, l;

                        var toRemoveIds = arrays.getRemovedIds(newTasks, oldTasks);
                        for (i = 0, l = toRemoveIds.length; i < l; i++) {
                            var toRemove = toRemoveIds[i];
                            row.removeTask(toRemove);
                        }

                        if (newTasks !== undefined) {
                            for (i = 0, l = newTasks.length; i < l; i++) {
                                var toAdd = newTasks[i];
                                row.addTask(toAdd);
                            }

                            row.updateVisibleTasks();
                        }
                    }
                });

                this.rowsTaskWatchers.push(watcher);
            }

            return isUpdate;
        };

        RowsManager.prototype.removeRow = function(rowId) {
            if (rowId in this.rowsMap) {
                delete this.rowsMap[rowId]; // Remove from map

                var removedRow;
                var row;

                var indexOf = arrays.indexOfId(this.rows, rowId, ['model', 'id']);
                if (indexOf > -1) {
                    removedRow = this.rows.splice(indexOf, 1)[0]; // Remove from array
                    var deregisterFunction = this.rowsTaskWatchers.splice(indexOf, 1)[0]; // Remove watcher
                    deregisterFunction();
                }

                arrays.removeId(this.sortedRows, rowId, ['model', 'id']);
                arrays.removeId(this.filteredRows, rowId, ['model', 'id']);
                arrays.removeId(this.customFilteredRows, rowId, ['model', 'id']);
                arrays.removeId(this.visibleRows, rowId, ['model', 'id']);

                this.gantt.api.rows.raise.remove(removedRow);
                return row;
            }

            return undefined;
        };

        RowsManager.prototype.removeAll = function() {
            this.rowsMap = {};
            this.rows = [];
            this.sortedRows = [];
            this.filteredRows = [];
            this.customFilteredRows = [];
            this.visibleRows = [];

            for (var i = 0, l = this.rowsTaskWatchers.length; i < l; i++) {
                var deregisterFunction = this.rowsTaskWatchers[i];
                deregisterFunction();
            }
            this.rowsTaskWatchers = [];
        };

        RowsManager.prototype.sortRows = function() {
            var expression = this.gantt.options.value('sortMode');

            if (expression !== undefined) {
                var reverse = false;
                if (angular.isString(expression) && expression.charAt(0) === '-') {
                    reverse = true;
                    expression = expression.substr(1);
                }

                var angularOrderBy = $filter('orderBy');
                this.sortedRows = angularOrderBy(this.rows, expression, reverse);
            } else {
                this.sortedRows = this.rows.slice();
            }

            this.sortedRows = this.applyCustomRowSorters(this.sortedRows);

            this.updateVisibleRows();
        };

        RowsManager.prototype.removeCustomRowSorter = function(sorterFunction) {
            var i = this.customRowSorters.indexOf(sorterFunction);
            if (i > -1) {
                this.customRowSorters.splice(i, 1);
            }
        };

        RowsManager.prototype.addCustomRowSorter = function(sorterFunction) {
            this.customRowSorters.push(sorterFunction);
        };

        RowsManager.prototype.applyCustomRowSorters = function(sortedRows) {
            for (var i = 0; i < this.customRowSorters.length; i++) {
                sortedRows = this.customRowSorters[i](sortedRows);
            }
            return sortedRows;
        };

        /**
         * Applies current view sort to data model.
         */
        RowsManager.prototype.applySort = function() {
            var data = this.gantt.$scope.data;
            data.splice(0, data.length); // empty data.
            var rows = [];
            for (var i = 0, l = this.sortedRows.length; i < l; i++) {
                data.push(this.sortedRows[i].model);
                rows.push(this.sortedRows[i]);
            }

            this.rows = rows;
        };

        RowsManager.prototype.moveRow = function(row, targetRow) {
            var sortMode = this.gantt.options.value('sortMode');
            if (sortMode !== undefined) {
                // Apply current sort to model
                this.applySort();
                this.gantt.options.set('sortMode', undefined);
            }

            var targetRowIndex = this.rows.indexOf(targetRow);
            var rowIndex = this.rows.indexOf(row);

            if (targetRowIndex > -1 && rowIndex > -1 && targetRowIndex !== rowIndex) {
                arrays.moveToIndex(this.rows, rowIndex, targetRowIndex);
                arrays.moveToIndex(this.rowsTaskWatchers, rowIndex, targetRowIndex);
                arrays.moveToIndex(this.gantt.$scope.data, rowIndex, targetRowIndex);

                this.gantt.api.rows.raise.change(row);
                this.gantt.api.rows.raise.move(row, rowIndex, targetRowIndex);

                this.updateVisibleObjects();
                this.sortRows();
            }
        };

        RowsManager.prototype.updateVisibleObjects = function() {
            this.updateVisibleRows();
            this.updateVisibleTasks();
        };

        RowsManager.prototype.updateVisibleRows = function() {
            var oldFilteredRows = this.filteredRows;
            var filterRow = this.gantt.options.value('filterRow');
            if (filterRow) {
                if (typeof(filterRow) === 'object') {
                    filterRow = {model: filterRow};
                }

                var filterRowComparator = this.gantt.options.value('filterRowComparator');
                if (typeof(filterRowComparator) === 'function') {
                    //fix issue this.gantt is undefined
                    //
                    var gantt = this.gantt;
                    filterRowComparator = function(actual, expected) {
                        //fix actual.model is undefined
                        return gantt.options.value('filterRowComparator')(actual, expected);
                    };
                }

                this.filteredRows = this.filterImpl(this.sortedRows, filterRow, filterRowComparator);
            } else {
                this.filteredRows = this.sortedRows.slice(0);
            }

            var raiseEvent = !angular.equals(oldFilteredRows, this.filteredRows);
            this.customFilteredRows = this.applyCustomRowFilters(this.filteredRows);

            // TODO: Implement rowLimit like columnLimit to enhance performance for gantt with many rows
            this.visibleRows = this.customFilteredRows;

            this.gantt.api.rows.raise.displayed(this.sortedRows, this.filteredRows, this.visibleRows);

            if (raiseEvent) {
                this.gantt.api.rows.raise.filter(this.sortedRows, this.filteredRows);
            }
        };

        RowsManager.prototype.removeCustomRowFilter = function(filterFunction) {
            var i = this.customRowFilters.indexOf(filterFunction);
            if (i > -1) {
                this.customRowFilters.splice(i, 1);
            }
        };

        RowsManager.prototype.addCustomRowFilter = function(filterFunction) {
            this.customRowFilters.push(filterFunction);
        };

        RowsManager.prototype.applyCustomRowFilters = function(filteredRows) {
            for (var i = 0; i < this.customRowFilters.length; i++) {
                filteredRows = this.customRowFilters[i](filteredRows);
            }
            return filteredRows;
        };

        RowsManager.prototype.setFilterImpl = function(filterImpl) {
            if (!filterImpl) {
                this.filterImpl = this._defaultFilterImpl;
            } else {
                this.filterImpl = filterImpl;
            }
        };

        RowsManager.prototype.updateVisibleTasks = function() {
            var oldFilteredTasks = [];
            var filteredTasks = [];
            var tasks = [];
            var visibleTasks = [];

            for (var i = 0; i < this.rows.length; i++) {
                var row = this.rows[i];
                oldFilteredTasks = oldFilteredTasks.concat(row.filteredTasks);
                row.updateVisibleTasks();
                filteredTasks = filteredTasks.concat(row.filteredTasks);
                visibleTasks = visibleTasks.concat(row.visibleTasks);
                tasks = tasks.concat(row.tasks);
            }

            this.gantt.api.tasks.raise.displayed(tasks, filteredTasks, visibleTasks);

            var filterEvent = !angular.equals(oldFilteredTasks, filteredTasks);

            if (filterEvent) {
                this.gantt.api.tasks.raise.filter(tasks, filteredTasks, visibleTasks);
            }
        };

        // Update the position/size of all tasks in the Gantt
        RowsManager.prototype.updateTasksPosAndSize = function() {
            for (var i = 0, l = this.rows.length; i < l; i++) {
                this.rows[i].updateTasksPosAndSize();
            }
        };

        RowsManager.prototype.getExpandedFrom = function(from) {
            from = from ? moment(from) : from;

            var minRowFrom = from;
            for (var i = 0; i < this.rows.length; i++) {
                if (minRowFrom === undefined || minRowFrom > this.rows[i].from) {
                    minRowFrom = this.rows[i];
                }
            }
            if (minRowFrom && (!from || minRowFrom < from)) {
                return minRowFrom;
            }
            return from;
        };

        RowsManager.prototype.getExpandedTo = function(to) {
            to = to ? moment(to) : to;

            var maxRowTo = to;
            for (var i = 0; i < this.rows.length; i++) {
                if (maxRowTo === undefined || maxRowTo < this.rows[i].to) {
                    maxRowTo = this.rows[i].to;
                }
            }
            var toDate = this.gantt.options.value('toDate');
            if (maxRowTo && (!toDate || maxRowTo > toDate)) {
                return maxRowTo;
            }
            return to;
        };

        RowsManager.prototype.getDefaultFrom = function() {
            var defaultFrom;
            for (var i = 0; i < this.rows.length; i++) {
                if (defaultFrom === undefined || this.rows[i].from < defaultFrom) {
                    defaultFrom = this.rows[i].from;
                }
            }
            return defaultFrom;
        };

        RowsManager.prototype.getDefaultTo = function() {
            var defaultTo;
            for (var i = 0; i < this.rows.length; i++) {
                if (defaultTo === undefined || this.rows[i].to > defaultTo) {
                    defaultTo = this.rows[i].to;
                }
            }
            return defaultTo;
        };

        return RowsManager;
    }]);
}());

(function() {
    'use strict';
    angular.module('gantt').factory('GanttTask', ['moment', function(moment) {
        var Task = function(row, model) {
            this.rowsManager = row.rowsManager;
            this.row = row;
            this.model = model;
            this.truncatedLeft = false;
            this.truncatedRight = false;
        };

        Task.prototype.isMilestone = function() {
            return !this.model.to || this.model.from - this.model.to === 0;
        };

        Task.prototype.isOutOfRange = function() {
            var firstColumn = this.rowsManager.gantt.columnsManager.getFirstColumn();
            var lastColumn = this.rowsManager.gantt.columnsManager.getLastColumn();

            return (firstColumn === undefined || this.model.to < firstColumn.date ||
            lastColumn === undefined || this.model.from > lastColumn.endDate);
        };

        // Updates the pos and size of the task according to the from - to date
        Task.prototype.updatePosAndSize = function() {
            var oldViewLeft = this.left;
            var oldViewWidth = this.width;
            var oldTruncatedRight = this.truncatedRight;
            var oldTruncatedLeft = this.truncatedLeft;

            if (!this.isMoving && this.isOutOfRange()) {
                this.modelLeft = undefined;
                this.modelWidth = undefined;
            } else {
                this.modelLeft = this.rowsManager.gantt.getPositionByDate(this.model.from);
                this.modelWidth = this.rowsManager.gantt.getPositionByDate(this.model.to) - this.modelLeft;
            }

            var lastColumn = this.rowsManager.gantt.columnsManager.getLastColumn();
            var maxModelLeft = lastColumn ? lastColumn.left + lastColumn.width : 0;

            var modelLeft = this.modelLeft;
            var modelWidth = this.modelWidth;

            if (this.rowsManager.gantt.options.value('daily')) {
                modelLeft = this.rowsManager.gantt.getPositionByDate(moment(this.model.from).startOf('day'));
                modelWidth = this.rowsManager.gantt.getPositionByDate(moment(this.model.to).endOf('day')) - modelLeft;
            }

            if (modelLeft === undefined || modelWidth === undefined ||
                modelLeft + modelWidth < 0 || modelLeft > maxModelLeft) {
                this.left = undefined;
                this.width = undefined;
            } else {
                this.left = Math.min(Math.max(modelLeft, 0), this.rowsManager.gantt.width);
                if (modelLeft < 0) {
                    this.truncatedLeft = true;
                    if (modelWidth + modelLeft > this.rowsManager.gantt.width) {
                        this.truncatedRight = true;
                        this.width = this.rowsManager.gantt.width;
                    } else {
                        this.truncatedRight = false;
                        this.width = modelWidth + modelLeft;
                    }
                } else if (modelWidth + modelLeft > this.rowsManager.gantt.width) {
                    this.truncatedRight = true;
                    this.truncatedLeft = false;
                    this.width = this.rowsManager.gantt.width - modelLeft;
                } else {
                    this.truncatedLeft = false;
                    this.truncatedRight = false;
                    this.width = modelWidth;
                }

                if (this.width < 0) {
                    this.left = this.left + this.width;
                    this.width = -this.width;
                }
            }

            this.updateView();
            if (!this.rowsManager.gantt.isRefreshingColumns &&
                (oldViewLeft !== this.left ||
                oldViewWidth !== this.width ||
                oldTruncatedRight !== this.truncatedRight ||
                oldTruncatedLeft !== this.truncatedLeft)) {
                this.rowsManager.gantt.api.tasks.raise.viewChange(this);
            }
        };

        Task.prototype.updateView = function() {
            if (this.$element) {
                if (this.left === undefined || this.width === undefined) {
                    this.$element.css('display', 'none');
                } else {
                    this.$element.css({'left': this.left + 'px', 'width': this.width + 'px', 'display': ''});

                    if (this.model.priority > 0) {
                        var priority = this.model.priority;
                        var children = this.$element.children();
                        for (var i = 0; i < children.length; i++) {
                            angular.element(children[i]).css('z-index', priority);
                        }
                    }

                    this.$element.toggleClass('gantt-task-milestone', this.isMilestone());
                }
            }
        };

        Task.prototype.getBackgroundElement = function() {
            if (this.$element !== undefined) {
                var backgroundElement = this.$element[0].querySelector('.gantt-task-background');
                if (backgroundElement !== undefined) {
                    backgroundElement = angular.element(backgroundElement);
                }
                return backgroundElement;
            }
        };

        Task.prototype.getContentElement = function() {
            if (this.$element !== undefined) {
                var contentElement = this.$element[0].querySelector('.gantt-task-content');
                if (contentElement !== undefined) {
                    contentElement = angular.element(contentElement);
                }
                return contentElement;
            }
        };

        Task.prototype.getForegroundElement = function() {
            if (this.$element !== undefined) {
                var foregroundElement = this.$element[0].querySelector('.gantt-task-foreground');
                if (foregroundElement !== undefined) {
                    foregroundElement = angular.element(foregroundElement);
                }
                return foregroundElement;
            }
        };

        // Expands the start of the task to the specified position (in em)
        Task.prototype.setFrom = function(x, magnetEnabled) {
            this.model.from = this.rowsManager.gantt.getDateByPosition(x, magnetEnabled);
            this.row.setFromTo();
            this.updatePosAndSize();
        };

        // Expands the end of the task to the specified position (in em)
        Task.prototype.setTo = function(x, magnetEnabled) {
            this.model.to = this.rowsManager.gantt.getDateByPosition(x, magnetEnabled);
            this.row.setFromTo();
            this.updatePosAndSize();
        };

        // Moves the task to the specified position (in em)
        Task.prototype.moveTo = function(x, magnetEnabled) {
            var newTaskRight;
            var newTaskLeft;
            if (x > this.modelLeft) {
                // Driven by right/to side.
                this.model.to = this.rowsManager.gantt.getDateByPosition(x + this.modelWidth, magnetEnabled);
                newTaskRight = this.rowsManager.gantt.getPositionByDate(this.model.to);
                newTaskLeft = newTaskRight - this.modelWidth;
                this.model.from = this.rowsManager.gantt.getDateByPosition(newTaskLeft, false);
            } else {
                // Drive by left/from side.
                this.model.from = this.rowsManager.gantt.getDateByPosition(x, magnetEnabled);
                newTaskLeft = this.rowsManager.gantt.getPositionByDate(this.model.from);
                newTaskRight = newTaskLeft + this.modelWidth;
                this.model.to = this.rowsManager.gantt.getDateByPosition(newTaskRight, false);
            }

            this.row.setFromTo();
            this.updatePosAndSize();
        };

        Task.prototype.clone = function() {
            return new Task(this.row, angular.copy(this.model));
        };

        return Task;
    }]);
}());


(function(){
    'use strict';
    angular.module('gantt').factory('GanttBody', ['GanttBodyColumns', 'GanttBodyRows', 'GanttBodyBackground', 'GanttBodyForeground', function(BodyColumns, BodyRows, BodyBackground, BodyForeground) {
        var Body= function(gantt) {
            this.gantt = gantt;

            this.background = new BodyBackground(this);
            this.foreground = new BodyForeground(this);
            this.columns = new BodyColumns(this);
            this.rows = new BodyRows(this);
        };
        return Body;
    }]);
}());


(function(){
    'use strict';
    angular.module('gantt').factory('GanttBodyBackground', [function() {
        var GanttBodyBackground = function(body) {
            this.body = body;
        };
        return GanttBodyBackground;
    }]);
}());

(function(){
    'use strict';
    angular.module('gantt').factory('GanttBodyColumns', [function() {
        var BodyColumns = function(body) {
            this.body = body;
        };
        return BodyColumns;
    }]);
}());


(function(){
    'use strict';
    angular.module('gantt').factory('GanttBodyForeground', [function() {
        var GanttBodyForeground = function(body) {
            this.body = body;
        };
        return GanttBodyForeground;
    }]);
}());


(function(){
    'use strict';
    angular.module('gantt').factory('GanttBodyRows', [function() {
        var BodyRows = function(body) {
            this.body = body;
        };
        return BodyRows;
    }]);
}());


(function(){
    'use strict';
    angular.module('gantt').factory('GanttHeader', ['GanttHeaderColumns', function(HeaderColumns) {
        var Header = function(gantt) {
            this.gantt = gantt;
            this.columns = new HeaderColumns(this);

            this.getHeight = function() {
                return this.$element[0].offsetHeight;
            };
        };
        return Header;
    }]);
}());


(function(){
    'use strict';
    angular.module('gantt').factory('GanttHeaderColumns', [function() {
        var HeaderColumns = function($element) {
            this.$element = $element;
        };
        return HeaderColumns;
    }]);
}());


(function(){
    'use strict';
    angular.module('gantt').factory('GanttScroll', [function() {
        var Scroll = function(gantt) {
            this.gantt = gantt;

            this.gantt.api.registerEvent('scroll', 'scroll');

            this.gantt.api.registerMethod('scroll', 'to', Scroll.prototype.scrollTo, this);
            this.gantt.api.registerMethod('scroll', 'toDate', Scroll.prototype.scrollToDate, this);
            this.gantt.api.registerMethod('scroll', 'left', Scroll.prototype.scrollToLeft, this);
            this.gantt.api.registerMethod('scroll', 'right', Scroll.prototype.scrollToRight, this);

            this.gantt.api.registerMethod('scroll', 'setWidth', Scroll.prototype.setWidth, this);
        };

        Scroll.prototype.getScrollLeft = function() {
            if (this.$element === undefined) {
                return undefined;
            } else {
                if (this.cachedScrollLeft === undefined) {
                    this.cachedScrollLeft = this.$element[0].scrollLeft;
                }

                return this.cachedScrollLeft;
            }
        };

        Scroll.prototype.getScrollWidth = function() {
            return this.$element === undefined ? undefined : this.$element[0].scrollWidth;
        };

        Scroll.prototype.getWidth = function() {
            return this.$element === undefined ? undefined : this.$element[0].offsetWidth;
        };

        Scroll.prototype.setWidth = function(width) {
            if (this.$element[0]) {
                this.$element[0].offsetWidth = width;
            }
        };

        Scroll.prototype.getBordersWidth = function() {
            if (this.$element === undefined) {
               return undefined;
            }

            if (this.$element[0].clientWidth) {
               return this.$element[0].offsetWidth - this.$element[0].clientWidth;
            } else {
               //fix for IE11
               var borderLeft = window.getComputedStyle(this.$element[0]).getPropertyValue('border-left-width') ? window.getComputedStyle(this.$element[0]).getPropertyValue('border-left-width').match(/\d+/)[0] : 0;
               var borderRight = window.getComputedStyle(this.$element[0]).getPropertyValue('border-right-width') ? window.getComputedStyle(this.$element[0]).getPropertyValue('border-right-width').match(/\d+/)[0] : 0;

               return parseInt(borderLeft) + parseInt(borderRight);
            }
        };

        Scroll.prototype.getBordersHeight = function() {
            return this.$element === undefined ? undefined : (this.$element[0].offsetHeight - this.$element[0].clientHeight);
        };

        Scroll.prototype.isVScrollbarVisible = function () {
            if (this.$element !== undefined) {
                return this.$element[0].scrollHeight > this.$element[0].offsetHeight;
            }
        };

        Scroll.prototype.isHScrollbarVisible = function () {
            if (this.$element !== undefined) {
                return this.$element[0].scrollWidth > this.$element[0].offsetWidth;
            }
        };

        /**
         * Scroll to a position
         *
         * @param {number} position Position to scroll to.
         */
        Scroll.prototype.scrollTo = function(position) {
            this.$element[0].scrollLeft = position;
            this.$element.triggerHandler('scroll');
        };

        /**
         * Scroll to the left side
         *
         * @param {number} offset Offset to scroll.
         */
        Scroll.prototype.scrollToLeft = function(offset) {
            this.$element[0].scrollLeft -= offset;
            this.$element.triggerHandler('scroll');
        };

        /**
         * Scroll to the right side
         *
         * @param {number} offset Offset to scroll.
         */
        Scroll.prototype.scrollToRight = function(offset) {
            this.$element[0].scrollLeft += offset;
            this.$element.triggerHandler('scroll');
        };

        /**
         * Scroll to a date
         *
         * @param {moment} date moment to scroll to.
         */
        Scroll.prototype.scrollToDate = function(date) {
            var position = this.gantt.getPositionByDate(date);

            if (position !== undefined) {
                this.$element[0].scrollLeft = position - this.$element[0].offsetWidth / 2;
            }
        };

        return Scroll;
    }]);
}());

(function(){
    'use strict';

    angular.module('gantt').factory('GanttSide', [function() {
        var Side= function(gantt) {
            this.gantt = gantt;
        };
        Side.prototype.getWidth = function() {
            if (this.gantt.options.value('showSide')) {
                var width = this.gantt.options.value('sideWidth');
                if (width === undefined && this.$element !== undefined) {
                    if (this.$element.css('width') !== undefined) {
                        this.$element.css('width', '');
                    }
                }
                if (this.$element !== undefined) {
                    width = this.$element[0].offsetWidth;
                }
                if (width !== undefined) {
                    return width;
                }
            }
            return 0;
        };
        Side.prototype.show = function(value) {
            if (this.$element !== undefined) {
                this.$element.toggleClass('ng-hide', !value);
            }
        };
        Side.prototype.isShown = function() {
            if (this.$element !== undefined) {
                return !this.$element.hasClass('ng-hide');
            }
        };

        return Side;
    }]);
}());


(function(){
    'use strict';
    angular.module('gantt').factory('GanttTimespan', [function() {
        var Timespan = function(gantt, model) {
            this.gantt = gantt;
            this.model = model;
        };

        // Updates the pos and size of the timespan according to the from - to date
        Timespan.prototype.updatePosAndSize = function() {
            this.modelLeft = this.gantt.getPositionByDate(this.model.from);
            this.modelWidth = this.gantt.getPositionByDate(this.model.to) - this.modelLeft;

            var lastColumn = this.gantt.columnsManager.getLastColumn();
            var maxModelLeft = lastColumn ? lastColumn.left + lastColumn.width : 0;

            if (this.modelLeft + this.modelWidth < 0 || this.modelLeft > maxModelLeft) {
                this.left = undefined;
                this.width = undefined;
            } else {
                this.left = Math.min(Math.max(this.modelLeft, 0), this.gantt.width);
                if (this.modelLeft < 0) {
                    this.truncatedLeft = true;
                    if (this.modelWidth + this.modelLeft > this.gantt.width) {
                        this.truncatedRight = true;
                        this.width = this.gantt.width;
                    } else {
                        this.truncatedRight = false;
                        this.width = this.modelWidth + this.modelLeft;
                    }
                } else if (this.modelWidth + this.modelLeft > this.gantt.width) {
                    this.truncatedRight = true;
                    this.truncatedLeft = false;
                    this.width = this.gantt.width - this.modelLeft;
                } else {
                    this.truncatedLeft = false;
                    this.truncatedRight = false;
                    this.width = this.modelWidth;
                }

                if (this.width < 0) {
                    this.left = this.left + this.width;
                    this.width = -this.width;
                }
            }


            this.updateView();
        };

        Timespan.prototype.updateView = function() {
            if (this.$element) {
                if (this.left === undefined || this.width === undefined) {
                    this.$element.css('display', 'none');
                } else {
                    this.$element.css('display', '');
                    this.$element.css('left', this.left + 'px');
                    this.$element.css('width', this.width + 'px');
                }
            }
        };

        // Expands the start of the timespan to the specified position (in em)
        Timespan.prototype.setFrom = function(x) {
            this.from = this.gantt.getDateByPosition(x);
            this.updatePosAndSize();
        };

        // Expands the end of the timespan to the specified position (in em)
        Timespan.prototype.setTo = function(x) {
            this.to = this.gantt.getDateByPosition(x);
            this.updatePosAndSize();
        };

        // Moves the timespan to the specified position (in em)
        Timespan.prototype.moveTo = function(x) {
            this.from = this.gantt.getDateByPosition(x);
            this.to = this.gantt.getDateByPosition(x + this.width);
            this.updatePosAndSize();
        };

        Timespan.prototype.clone = function() {
            return new Timespan(this.gantt, angular.copy(this.model));
        };

        return Timespan;
    }]);
}());


(function(){
    'use strict';
    angular.module('gantt').factory('GanttTimespansManager', ['GanttTimespan', function(Timespan) {
        var GanttTimespansManager = function(gantt) {
            var self = this;

            this.gantt = gantt;

            this.timespansMap = {};
            this.timespans = [];

            this.gantt.$scope.$watchCollection('timespans', function(newValue) {
                self.clearTimespans();
                self.loadTimespans(newValue);
            });

            this.gantt.api.registerMethod('timespans', 'load', this.loadTimespans, this);
            this.gantt.api.registerMethod('timespans', 'remove', this.removeTimespans, this);
            this.gantt.api.registerMethod('timespans', 'clear', this.clearTimespans, this);

            this.gantt.api.registerEvent('timespans', 'add');
            this.gantt.api.registerEvent('timespans', 'remove');
            this.gantt.api.registerEvent('timespans', 'change');
        };

        // Adds or updates timespans
        GanttTimespansManager.prototype.loadTimespans = function(timespans) {
            if (!angular.isArray(timespans)) {
                timespans = timespans !== undefined ? [timespans] : [];
            }

            this.gantt.$scope.timespans = timespans;
            for (var i = 0, l = timespans.length; i < l; i++) {
                var timespanModel = timespans[i];
                this.gantt.objectModel.cleanTimespan(timespanModel);
                this.loadTimespan(timespanModel);
            }
        };

        // Adds a timespan or merges the timespan if there is already one with the same id
        GanttTimespansManager.prototype.loadTimespan = function(timespanModel) {
            // Copy to new timespan (add) or merge with existing (update)
            var timespan, isUpdate = false;

            if (timespanModel.id in this.timespansMap) {
                timespan = this.timespansMap[timespanModel.id];
                timespan.model = timespanModel;
                isUpdate = true;
                this.gantt.api.timespans.raise.change(timespan);
            } else {
                timespan = new Timespan(this.gantt, timespanModel);
                this.timespansMap[timespanModel.id] = timespan;
                this.timespans.push(timespan);
                this.gantt.api.timespans.raise.add(timespan);
            }

            timespan.updatePosAndSize();
            return isUpdate;
        };

        GanttTimespansManager.prototype.removeTimespans = function(timespans) {
            if (!angular.isArray(timespans)) {
                timespans = [timespans];
            }

            for (var i = 0, l = timespans.length; i < l; i++) {
                var timespanData = timespans[i];
                // Delete the timespan
                this.removeTimespan(timespanData.id);
            }
            this.updateVisibleObjects();
        };

        GanttTimespansManager.prototype.removeTimespan = function(timespanId) {
            if (timespanId in this.timespansMap) {
                delete this.timespansMap[timespanId]; // Remove from map

                var removedTimespan;
                var timespan;
                for (var i = this.timespans.length - 1; i >= 0; i--) {
                    timespan = this.timespans[i];
                    if (timespan.model.id === timespanId) {
                        removedTimespan = timespan;
                        this.timespans.splice(i, 1); // Remove from array
                        break;
                    }
                }

                this.gantt.api.timespans.raise.remove(removedTimespan);
                return removedTimespan;
            }

            return undefined;
        };

        // Removes all timespans
        GanttTimespansManager.prototype.clearTimespans = function() {
            this.timespansMap = {};
            this.timespans = [];
        };

        GanttTimespansManager.prototype.updateTimespansPosAndSize = function() {
            for (var i = 0, l = this.timespans.length; i < l; i++) {
                this.timespans[i].updatePosAndSize();
            }
        };

        return GanttTimespansManager;
    }]);
}());

(function(){
    'use strict';
    angular.module('gantt').service('ganttArrays', [function() {
        return {
            moveToIndex: function(array, oldIndex, newIndex) {
                if (newIndex >= array.length) {
                    var k = newIndex - array.length;
                    while ((k--) + 1) {
                        array.push(undefined);
                    }
                }
                array.splice(newIndex, 0, array.splice(oldIndex, 1)[0]);
                return array;
            },
            getRemovedIds: function(newArray, oldArray, idProperty) {
                if (idProperty === undefined) {
                    idProperty = 'id';
                }

                var i, l;
                var removedIds = [];

                if (oldArray !== undefined) {
                    for (i = 0, l = oldArray.length; i < l; i++) {
                        removedIds.push(oldArray[i][idProperty]);
                    }
                }

                if (newArray !== undefined) {
                    for (i = 0, l = newArray.length; i < l; i++) {
                        var newObject = newArray[i];

                        if (newObject[idProperty] !== undefined) {
                            var newObjectIndex = removedIds.indexOf(newObject[idProperty]);
                            if (newObjectIndex > -1) {
                                removedIds.splice(newObjectIndex, 1);
                            }
                        }
                    }
                }

                return removedIds;
            },
            indexOfId: function(array, value, idProperties) {
                var i;
                if (idProperties === undefined) {
                    idProperties = 'id';
                } else if (idProperties instanceof Array) {
                    for (i = array.length - 1; i >= 0; i--) {
                        var arrayValue = array[i];
                        for (var k = 0, l = idProperties.length; k < l; k++) {
                            arrayValue = arrayValue[idProperties[k]];
                        }
                        if (arrayValue === value) {
                            return i;
                        }
                    }
                    return -1;
                }
                for (i = array.length - 1; i >= 0; i--) {
                    if (array[i][idProperties] === value) {
                        return i;
                    }
                }
                return -1;
            },
            removeId: function(array, value, idProperties) {
                var indexOf = this.indexOfId(array, value, idProperties);
                if (indexOf > -1) {
                    return array.splice(indexOf, 1)[0];
                }
            },
            remove: function(array, value) {
                var index = array.indexOf(value);
                if (index > -1) {
                    array.splice(index, 1);
                    return true;
                }
                return false;
            }
        };
    }]);
}());


(function(){
    'use strict';
    angular.module('gantt').service('ganttBinarySearch', [ function() {
        // Returns the object on the left and right in an array using the given cmp function.
        // The compare function defined which property of the value to compare (e.g.: c => c.left)

        return {
            getIndicesOnly: function(input, value, comparer, strict) {
                var lo = -1, hi = input.length;
                while (hi - lo > 1) {
                    var mid = Math.floor((lo + hi) / 2);
                    if (strict ? comparer(input[mid]) < value : comparer(input[mid]) <= value) {
                        lo = mid;
                    } else {
                        hi = mid;
                    }
                }
                if (!strict && input[lo] !== undefined && comparer(input[lo]) === value) {
                    hi = lo;
                }
                return [lo, hi];
            },
            get: function(input, value, comparer, strict) {
                var res = this.getIndicesOnly(input, value, comparer, strict);
                return [input[res[0]], input[res[1]]];
            }
        };
    }]);
}());

(function() {
    'use strict';

    angular.module('gantt').factory('GanttHierarchy', [function() {
        var Hierarchy = function() {
            var self = this;

            var nameToRow = {};

            var idToRow = {};

            var nameToChildren = {};
            var idToChildren = {};

            var nameToParent = {};
            var idToParent = {};

            var registerChildRow = function(row, childRow) {
                if (childRow !== undefined) {
                    var nameChildren = nameToChildren[row.model.name];
                    if (nameChildren === undefined) {
                        nameChildren = [];
                        nameToChildren[row.model.name] = nameChildren;
                    }
                    nameChildren.push(childRow);


                    var idChildren = idToChildren[row.model.id];
                    if (idChildren === undefined) {
                        idChildren = [];
                        idToChildren[row.model.id] = idChildren;
                    }
                    idChildren.push(childRow);

                    nameToParent[childRow.model.name] = row;
                    idToParent[childRow.model.id] = row;
                }
            };

            this.refresh = function(rows) {
                nameToRow = {};
                idToRow = {};

                nameToChildren = {};
                idToChildren = {};

                nameToParent = {};
                idToParent = {};

                var row;

                for (var i = 0; i < rows.length; i++) {
                    row = rows[i];
                    nameToRow[row.model.name] = row;
                    idToRow[row.model.id] = row;
                }

                for (i = 0; i < rows.length; i++) {
                    row = rows[i];
                    if (row.model.parent !== undefined) {
                        var parentRow = nameToRow[row.model.parent];
                        if (parentRow === undefined) {
                            parentRow = idToRow[row.model.parent];
                        }

                        if (parentRow !== undefined) {
                            registerChildRow(parentRow, row);
                        }
                    }

                    if (row.model.children !== undefined) {
                        var children = row.model.children;
                        for (var j = 0; j<children.length; j++) {
                            var childRowNameOrId = children[j];
                            var childRow = nameToRow[childRowNameOrId];
                            if (childRow === undefined) {
                                childRow = idToRow[childRowNameOrId];
                            }

                            if (childRow !== undefined) {
                                registerChildRow(row, childRow);
                            }
                        }
                    }
                }

                var rootRows = [];
                for (i = 0; i < rows.length; i++) {
                    row = rows[i];
                    if (self.parent(row) === undefined) {
                        rootRows.push(row);
                    }
                }

                return rootRows;
            };

            this.children = function(row) {
                var children = idToChildren[row.model.id];
                return children;
            };

            this.descendants = function(row) {
                var descendants = [];

                var children = self.children(row);
                descendants.push.apply(descendants, children);
                if (children !== undefined) {
                    for (var i=0; i<children.length; i++) {
                        var childDescendants = self.descendants(children[i]);
                        descendants.push.apply(descendants, childDescendants);
                    }
                }

                return descendants;
            };

            this.parent = function(row) {
                var parent = idToParent[row.model.id];
                return parent;
            };

            this.ancestors = function(row) {
                var ancestors = [];

                var parent = self.parent(row);
                while (parent !== undefined) {
                    ancestors.push(parent);
                    parent = self.parent(parent);
                }

                return ancestors;
            };
        };

        return Hierarchy;
    }]);
}());

(function() {
    'use strict';
    angular.module('gantt').service('ganttUtils', [function() {
        return {
            createBoundedWrapper: function(object, method) {
                return function() {
                    return method.apply(object, arguments);
                };
            },
            firstProperty: function(objects, propertyName, defaultValue) {
                for (var i = 0, l = objects.length; i < l; i++) {
                    var object = objects[i];
                    if (object !== undefined && propertyName in object) {
                        if (object[propertyName] !== undefined) {
                            return object[propertyName];
                        }
                    }
                }
                return defaultValue;
            },
            angularIndexOf: function(arr, obj) {
                for (var i = 0; i < arr.length; i++) {
                    if (angular.equals(arr[i], obj)) {
                        return i;
                    }
                }
                return -1;
            },
            random4: function() {
                return Math.floor((1 + Math.random()) * 0x10000)
                    .toString(16)
                    .substring(1);
            },
            randomUuid: function() {
                return this.random4() + this.random4() + '-' + this.random4() + '-' + this.random4() + '-' +
                    this.random4() + '-' + this.random4() + this.random4() + this.random4();
            },
            newId: (function() {
                var seedId = new Date().getTime();
                return function() {
                    return seedId += 1;
                };
            })()
        };
    }]);
}());


(function(){
    'use strict';
    angular.module('gantt').filter('ganttColumnLimit', [ 'ganttBinarySearch', function(bs) {
        // Returns only the columns which are visible on the screen
        var leftComparator = function(c) {
            return c.left;
        };

        return function(input, gantt) {
            var scrollLeft = gantt.scroll.getScrollLeft();
            var scrollContainerWidth = gantt.getWidth() - gantt.side.getWidth();

            if (scrollContainerWidth > 0) {
                var start = bs.getIndicesOnly(input, scrollLeft, leftComparator)[0];
                var end = bs.getIndicesOnly(input, scrollLeft + scrollContainerWidth, leftComparator)[1];
                return input.slice(start, end);
            } else {
                return input.slice();
            }
        };
    }]);
}());


(function(){
    'use strict';
    angular.module('gantt').filter('ganttTaskLimit', [function() {
        // Returns only the tasks which are visible on the screen
        // Use the task width and position to decide if a task is still visible

        return function(input, gantt) {
            var firstColumn = gantt.columnsManager.getFirstColumn();
            var lastColumn = gantt.columnsManager.getLastColumn();

            if (firstColumn !== undefined && lastColumn !== undefined) {
                var fromDate = firstColumn.date;
                var toDate = lastColumn.endDate;

                var res = [];

                var scrollLeft = gantt.scroll.getScrollLeft();
                var scrollContainerWidth = gantt.getWidth() - gantt.side.getWidth();

                for (var i = 0, l = input.length; i < l; i++) {
                    var task = input[i];

                    if (task.active) {
                        res.push(task);
                    } else {
                        // If the task can be drawn with gantt columns only.
                        if (task.model.to >= fromDate && task.model.from <= toDate) {

                            if (task.left === undefined) {
                                task.updatePosAndSize();
                            }

                            // If task has a visible part on the screen
                            if (!scrollContainerWidth ||
                                task.left >= scrollLeft && task.left <= scrollLeft + scrollContainerWidth ||
                                task.left + task.width >= scrollLeft && task.left + task.width <= scrollLeft + scrollContainerWidth ||
                                task.left < scrollLeft && task.left + task.width > scrollLeft + scrollContainerWidth) {

                                res.push(task);
                            }
                        }
                    }
                }

                return res;
            } else {
                return input.splice();
            }
        };
    }]);
}());


(function() {
    'use strict';

    angular.module('gantt').directive('ganttResizer', ['$document', '$parse', '$timeout', 'ganttMouseOffset', function($document, $parse, $timeout, mouseOffset) {
        return {
            restrict: 'A',
            require: '^gantt',
            scope: {
                targetElement: '=ganttResizer',
                enabled: '@?ganttResizerEnabled'
            },
            link: function ($scope, $element, $attrs, ganttCtrl) {
                var api = ganttCtrl.gantt.api;
                var eventTopic = $attrs.ganttResizerEventTopic;

                if ($scope.enabled === undefined) {
                    $scope.enabled = true;
                }

                function getWidth() {
                    return ganttCtrl.gantt.options.value($attrs.resizerWidth);
                }

                function setWidth(width) {
                    if (width !== getWidth()) {
                        ganttCtrl.gantt.options.set($attrs.resizerWidth, width);

                        if (eventTopic !== undefined) {
                            api[eventTopic].raise.resize(width);
                        }

                        $timeout(function() {
                            ganttCtrl.gantt.columnsManager.updateColumnsMeta();
                        });
                    }
                }

                function dblclick(event) {
                    event.preventDefault();
                    setWidth(undefined);
                }

                function mousemove(event) {
                    $scope.$evalAsync(function (){
                        var offset = mouseOffset.getOffsetForElement($scope.targetElement[0], event);
                        var maxWidth = ganttCtrl.gantt.getWidth()-ganttCtrl.gantt.scroll.getBordersWidth();
                        var width = Math.min(Math.max(offset.x, 0), maxWidth);
                        setWidth(width);
                    });
                }

                function mouseup() {
                    if (eventTopic !== undefined) {
                        api[eventTopic].raise.resizeEnd(getWidth());
                    }
                    $document.unbind('mousemove', mousemove);
                    $document.unbind('mouseup', mouseup);
                }


                function mousedown(event) {
                    event.preventDefault();

                    if (eventTopic !== undefined) {
                        api[eventTopic].raise.resizeBegin(getWidth());
                    }
                    $document.on('mousemove', mousemove);
                    $document.on('mouseup', mouseup);
                }

                $attrs.$observe('ganttResizerEnabled', function(value) {
                    $scope.enabled = $parse(value)();
                });

                $scope.$watch('enabled', function (value) {
                    if (value === undefined) {
                        value = true;
                    }

                    $element.toggleClass('gantt-resizer-enabled', value);

                    if (value) {
                        $element.on('dblclick', dblclick);
                        $element.on('mousedown', mousedown);
                    } else {
                        $element.off('dblclick', dblclick);
                        $element.off('mousedown', mousedown);
                    }
                });

                $scope.$watch(function() {
                    return getWidth();
                }, function(newValue, oldValue) {
                    if (newValue !== oldValue) {
                        $scope.targetElement.css('width', newValue + 'px');
                        // Setting width again is required when min-width of max-width is set on targetElement.
                        // This avoid going to a smaller or bigger value than targetElement capabilities.
                        // Call of 'offsetWidth' is slow. Behaviour needs to be improved.
                        if ($scope.targetElement[0].offsetWidth > 0) {
                            setWidth($scope.targetElement[0].offsetWidth);
                        }
                    }
                });

                if (eventTopic) {
                    api.registerEvent(eventTopic, 'resize');
                    api.registerEvent(eventTopic, 'resizeBegin');
                    api.registerEvent(eventTopic, 'resizeEnd');
                    api.registerMethod(eventTopic, 'setWidth', setWidth, this);
                    api.registerMethod(eventTopic, 'getWidth', getWidth, this);
                }
            }
        };
    }]);
}());

(function(){
    'use strict';
    angular.module('gantt').directive('ganttHorizontalScrollReceiver', function() {
        // The element with this attribute will scroll at the same time as the scrollSender element

        return {
            restrict: 'A',
            require: '^ganttScrollManager',
            link: function(scope, element, attrs, ganttScrollManagerCtrl) {
                ganttScrollManagerCtrl.registerHorizontalReceiver(element);
            }
        };
    });
}());

(function(){
    'use strict';
    angular.module('gantt').directive('ganttScrollManager', function() {
        // The element with this attribute will scroll at the same time as the scrollSender element

        return {
            restrict: 'A',
            scope: {},
            controller: ['$scope', function($scope) {
                $scope.horizontal = [];
                $scope.vertical = [];

                this.registerVerticalReceiver = function (element) {
                    element.css('position', 'relative');
                    $scope.vertical.push(element[0]);
                };

                this.registerHorizontalReceiver = function (element) {
                    element.css('position', 'relative');
                    $scope.horizontal.push(element[0]);
                };

                this.getHorizontalRecievers = function() {
                    return $scope.horizontal;
                };

                this.getVerticalRecievers = function() {
                    return $scope.vertical;
                };
            }]
        };
    });
}());


(function(){
    'use strict';
    angular.module('gantt').directive('ganttScrollSender', [function() {
        // Updates the element which are registered for the horizontal or vertical scroll event

        return {
            restrict: 'A',
            require: ['^gantt', '^ganttScrollManager'],
            link: function(scope, element, attrs, controllers) {
                var el = element[0];

                var updateListeners = function() {
                    var i, l;

                    var vertical = controllers[1].getVerticalRecievers();
                    for (i = 0, l = vertical.length; i < l; i++) {
                        var vElement = vertical[i];
                        if (vElement.parentNode.scrollTop !== el.scrollTop) {
                            vElement.parentNode.scrollTop = el.scrollTop;
                        }
                    }

                    var horizontal = controllers[1].getHorizontalRecievers();
                    for (i = 0, l = horizontal.length; i < l; i++) {
                        var hElement = horizontal[i];
                        if (hElement.parentNode.scrollLeft !== el.scrollLeft) {
                            hElement.parentNode.scrollLeft  = el.scrollLeft;
                        }
                    }
                };

                element.bind('scroll', updateListeners);

                scope.$watch(function() {
                    return controllers[0].gantt.width;
                }, function(newValue, oldValue) {
                    if (newValue !== oldValue) {
                        var horizontal = controllers[1].getHorizontalRecievers();
                        for (var i = 0, l = horizontal.length; i < l; i++) {
                            var hElement = horizontal[i];
                            hElement.style.width = newValue + 'px';
                        }
                    }
                });
            }
        };
    }]);
}());


(function(){
    'use strict';
    angular.module('gantt').directive('ganttScrollable', ['GanttDirectiveBuilder', '$timeout', 'ganttDebounce', 'moment', function(Builder, $timeout, debounce, moment) {
        var builder = new Builder('ganttScrollable');
        builder.controller = function($scope, $element) {
            $scope.gantt.scroll.$element = $element;
            var lastScrollLeft;
            var autoExpandTimer;

            var autoExpandColumns = function(el, date, direction) {
                var autoExpand = $scope.gantt.options.value('autoExpand');
                if (autoExpand !== 'both' && autoExpand !== true && autoExpand !== direction) {
                    return;
                }

                var from, to;

                var viewScale = $scope.gantt.options.value('viewScale');
                viewScale = viewScale.trim();
                if (viewScale.charAt(viewScale.length - 1) === 's') {
                    viewScale = viewScale.substring(0, viewScale.length - 1);
                }
                var viewScaleValue;
                var viewScaleUnit;
                var splittedViewScale;

                if (viewScale) {
                    splittedViewScale = viewScale.split(' ');
                }
                if (splittedViewScale && splittedViewScale.length > 1) {
                    viewScaleValue = parseFloat(splittedViewScale[0]);
                    viewScaleUnit = splittedViewScale[splittedViewScale.length - 1];
                } else {
                    viewScaleValue = 1;
                    viewScaleUnit = viewScale;
                }

                if (direction === 'left') {
                    from = moment(date).add(-5 * viewScaleValue, viewScaleUnit);
                    $scope.fromDate = from;
                } else {
                    to = moment(date).add(5 * viewScaleValue, viewScaleUnit);
                    $scope.toDate = to;
                }

                $scope.gantt.api.scroll.raise.scroll(el.scrollLeft, date, direction);
            };

            $element.bind('scroll', debounce(function() {
                var el = $element[0];
                var currentScrollLeft = el.scrollLeft;
                var direction;
                var date;

                $scope.gantt.scroll.cachedScrollLeft = currentScrollLeft;
                $scope.gantt.columnsManager.updateVisibleColumns();
                $scope.gantt.rowsManager.updateVisibleObjects();

                if (currentScrollLeft < lastScrollLeft && currentScrollLeft === 0) {
                    direction = 'left';
                    date = $scope.gantt.columnsManager.from;
                } else if (currentScrollLeft > lastScrollLeft && el.offsetWidth + currentScrollLeft >= el.scrollWidth - 1) {
                    direction = 'right';
                    date = $scope.gantt.columnsManager.to;
                }

                lastScrollLeft = currentScrollLeft;

                if (date !== undefined) {
                    if (autoExpandTimer) {
                        $timeout.cancel(autoExpandTimer);
                    }

                    autoExpandTimer = $timeout(function() {
                        autoExpandColumns(el, date, direction);
                    }, 300);
                } else {
                    $scope.gantt.api.scroll.raise.scroll(currentScrollLeft);
                }
            }, 5));

            $scope.getScrollableCss = function() {
                var css = {};

                var maxHeight = $scope.gantt.options.value('maxHeight');
                if (maxHeight > 0) {
                    css['max-height'] = maxHeight - $scope.gantt.header.getHeight() + 'px';
                    css['overflow-y'] = 'auto';

                    if ($scope.gantt.scroll.isVScrollbarVisible()) {
                        css['border-right'] = 'none';
                    }
                }

                var columnWidth = this.gantt.options.value('columnWidth');
                var bodySmallerThanGantt = $scope.gantt.width === 0 ? false: $scope.gantt.width < $scope.gantt.getWidth() - $scope.gantt.side.getWidth();
                if (columnWidth !== undefined && bodySmallerThanGantt) {
                    css.width = ($scope.gantt.width + this.gantt.scroll.getBordersWidth()) + 'px';
                }

                return css;
            };
        };
        return builder.build();
    }]);
}());


(function(){
    'use strict';
    angular.module('gantt').directive('ganttVerticalScrollReceiver', function() {
        // The element with this attribute will scroll at the same time as the scrollSender element

        return {
            restrict: 'A',
            require: '^ganttScrollManager',
            link: function(scope, element, attrs, ganttScrollManagerCtrl) {
                ganttScrollManagerCtrl.registerVerticalReceiver(element);
            }
        };
    });
}());


(function(){
    'use strict';
    angular.module('gantt').directive('ganttElementHeightListener', [function() {
        return {
            restrict: 'A',
            controller: ['$scope', '$element', '$attrs', function($scope, $element, $attrs) {
                var scopeVariable = $attrs.ganttElementHeightListener;
                if (scopeVariable === '') {
                    scopeVariable = 'ganttElementHeight';
                }

                var effectiveScope = $scope;

                while(scopeVariable.indexOf('$parent.') === 0) {
                    scopeVariable = scopeVariable.substring('$parent.'.length);
                    effectiveScope = effectiveScope.$parent;
                }

                effectiveScope.$watch(function() {
                    return $element[0].offsetHeight;
                }, function(newValue) {
                    if (newValue > 0) {
                        effectiveScope[scopeVariable] = newValue;
                    }
                });
            }]
        };
    }]);
}());


(function(){
    'use strict';
    angular.module('gantt').directive('ganttElementWidthListener', [function() {
        return {
            restrict: 'A',
            controller: ['$scope', '$element', '$attrs', function($scope, $element, $attrs) {
                var scopeVariable = $attrs.ganttElementWidthListener;
                if (scopeVariable === '') {
                    scopeVariable = 'ganttElementWidth';
                }

                var effectiveScope = $scope;

                while(scopeVariable.indexOf('$parent.') === 0) {
                    scopeVariable = scopeVariable.substring('$parent.'.length);
                    effectiveScope = effectiveScope.$parent;
                }

                effectiveScope.$watch(function() {
                    return $element[0].offsetWidth;
                }, function(newValue) {
                    if (newValue > 0) {
                        effectiveScope[scopeVariable] = newValue;
                    }
                });
            }]
        };
    }]);
}());


(function(){
    'use strict';
    angular.module('gantt').directive('ganttBody', ['GanttDirectiveBuilder', function(Builder) {
        var builder = new Builder('ganttBody');
        builder.controller = function($scope, $element) {
            $scope.gantt.body.$element = $element;
            $scope.gantt.body.$scope = $scope;
        };
        return builder.build();
    }]);
}());


(function(){
    'use strict';
    angular.module('gantt').directive('ganttBodyBackground', ['GanttDirectiveBuilder', function(Builder) {
        var builder = new Builder('ganttBodyBackground');
        builder.controller = function($scope, $element) {
            $scope.gantt.body.background.$element = $element;
            $scope.gantt.body.background.$scope = $scope;
        };
        return builder.build();
    }]);
}());


(function(){
    'use strict';
    angular.module('gantt').directive('ganttBodyColumns', ['GanttDirectiveBuilder', function(Builder) {
        var builder = new Builder('ganttBodyColumns');
        builder.controller = function($scope, $element) {
            $scope.gantt.body.columns.$element = $element;
            $scope.gantt.body.background.$scope = $scope;
        };
        return builder.build();
    }]);
}());


(function(){
    'use strict';
    angular.module('gantt').directive('ganttBodyForeground', ['GanttDirectiveBuilder', function(Builder) {
        var builder = new Builder('ganttBodyForeground');
        builder.controller = function($scope, $element) {
            $scope.gantt.body.foreground.$element = $element;
            $scope.gantt.body.foreground.$scope = $scope;
        };
        return builder.build();
    }]);
}());


(function(){
    'use strict';
    angular.module('gantt').directive('ganttBodyRows', ['GanttDirectiveBuilder', function(Builder) {
        var builder = new Builder('ganttBodyRows');
        builder.controller = function($scope, $element) {
            $scope.gantt.body.rows.$element = $element;
            $scope.gantt.body.rows.$scope = $scope;
        };
        return builder.build();
    }]);
}());


(function(){
    'use strict';
    angular.module('gantt').directive('ganttColumn', ['GanttDirectiveBuilder', function(Builder) {
        var builder = new Builder('ganttColumn');
        builder.controller = function($scope, $element) {
            $scope.column.$element = $element;
            $scope.column.$scope = $scope;
            $scope.column.updateView();
        };
        return builder.build();
    }]);
}());


(function(){
    'use strict';
    angular.module('gantt').directive('ganttColumnHeader', ['GanttDirectiveBuilder', function(Builder) {
        var builder = new Builder('ganttColumnHeader');
        builder.controller = function($scope, $element) {
            $scope.column.$element = $element;
            $scope.column.$scope = $scope;
            $scope.column.updateView();
        };
        return builder.build();
    }]);
}());


(function(){
    'use strict';
    angular.module('gantt').directive('ganttHeader', ['GanttDirectiveBuilder', function(Builder) {
        var builder = new Builder('ganttHeader');
        builder.controller = function($scope, $element) {
            $scope.gantt.header.$element = $element;
            $scope.gantt.header.$scope = $scope;
        };
        return builder.build();
    }]);
}());


(function(){
    'use strict';
    angular.module('gantt').directive('ganttHeaderColumns', ['GanttDirectiveBuilder', function(Builder) {
        var builder = new Builder('ganttHeaderColumns');
        builder.controller = function($scope, $element) {
            $scope.gantt.header.columns.$element = $element;
            $scope.gantt.header.columns.$scope = $scope;
        };
        return builder.build();
    }]);
}());


(function(){
    'use strict';
    angular.module('gantt').directive('ganttRow', ['GanttDirectiveBuilder', function(Builder) {
        var builder = new Builder('ganttRow');
        builder.controller = function($scope, $element) {
            $scope.row.$element = $element;
            $scope.row.$scope = $scope;
        };
        return builder.build();
    }]);
}());


(function(){
    'use strict';
    angular.module('gantt').directive('ganttRowBackground', ['GanttDirectiveBuilder', function(Builder) {
        var builder = new Builder('ganttRowBackground');
        return builder.build();
    }]);
}());


(function(){
    'use strict';
    angular.module('gantt').directive('ganttRowLabel', ['GanttDirectiveBuilder', function(Builder) {
        var builder = new Builder('ganttRowLabel');
        builder.restrict = 'A';
        builder.templateUrl = undefined;
        return builder.build();
    }]);
}());


(function(){
    'use strict';
    angular.module('gantt').directive('ganttScrollableHeader', ['GanttDirectiveBuilder', 'ganttLayout', function(Builder, layout) {
        var builder = new Builder('ganttScrollableHeader');
        builder.controller = function($scope) {
            var scrollBarWidth = layout.getScrollBarWidth();
            //var oldMaxHeightActivated = false;
            $scope.getScrollableHeaderCss = function() {
                var css = {};

                var maxHeightActivated = $scope.gantt.scroll.isVScrollbarVisible();
                var vScrollbarWidth = maxHeightActivated ? scrollBarWidth: 0;
                var columnWidth = this.gantt.options.value('columnWidth');
                var bodySmallerThanGantt = $scope.gantt.width === 0 ? false: $scope.gantt.width < $scope.gantt.getWidth() - $scope.gantt.side.getWidth();

                if (columnWidth !== undefined && bodySmallerThanGantt) {
                    css.width = ($scope.gantt.width - vScrollbarWidth + this.gantt.scroll.getBordersWidth()) + 'px';
                } else if (maxHeightActivated) {
                    css.width = $scope.gantt.getWidth() - $scope.gantt.side.getWidth() - vScrollbarWidth + 'px';
                }

                /*
                if (oldMaxHeightActivated !== maxHeightActivated) {
                    oldMaxHeightActivated = maxHeightActivated;
                    $scope.gantt.columnsManager.updateColumnsMeta();
                }
                */

                return css;
            };
        };
        return builder.build();
    }]);
}());


(function(){
    'use strict';
    angular.module('gantt').directive('ganttSide', ['GanttDirectiveBuilder', function(Builder) {
        var builder = new Builder('ganttSide');
        builder.controller = function($scope, $element) {
            $scope.gantt.side.$element = $element;
            $scope.gantt.side.$scope = $scope;
        };
        return builder.build();
    }]);
}());


(function(){
    'use strict';
    angular.module('gantt').directive('ganttSideBackground', ['GanttDirectiveBuilder', 'ganttLayout', function(Builder, layout) {
        var builder = new Builder('ganttSideBackground');
        builder.controller = function($scope) {
            var hScrollBarHeight = layout.getScrollBarHeight();

            $scope.getMaxHeightCss = function() {
                var css = {};

                if ($scope.maxHeight) {
                    var bodyScrollBarHeight = $scope.gantt.scroll.isHScrollbarVisible() ? hScrollBarHeight : 0;
                    css['max-height'] = $scope.maxHeight - bodyScrollBarHeight - $scope.gantt.header.getHeight() + 'px';
                }

                return css;
            };
        };
        return builder.build();
    }]);
}());


(function(){
    'use strict';
    angular.module('gantt').directive('ganttSideContent', ['GanttDirectiveBuilder', function(Builder) {
        var builder = new Builder('ganttSideContent');
        return builder.build();
    }]);
}());


(function(){
    'use strict';
    angular.module('gantt').directive('ganttTask', ['GanttDirectiveBuilder', 'moment', function(Builder, moment) {
        var builder = new Builder('ganttTask');
        builder.controller = function($scope, $element) {
            $scope.task.$element = $element;
            $scope.task.$scope = $scope;

            $scope.getTaskContent = function() {
                if ($scope.task.model.content !== undefined) {
                    return $scope.task.model.content;
                }
                return $scope.task.rowsManager.gantt.options.value('taskContent');
            };

            $scope.simplifyMoment = function(d) {
                return moment.isMoment(d) ? d.unix() : d;
            };

            $scope.$watchGroup(['simplifyMoment(task.model.from)', 'simplifyMoment(task.model.to)'], function() {
                $scope.task.updatePosAndSize();
            });
        };
        return builder.build();
    }]);
}());

(function(){
    'use strict';
    angular.module('gantt').directive('ganttTaskBackground', ['GanttDirectiveBuilder', function(Builder) {
        var builder = new Builder('ganttTaskBackground');
        return builder.build();
    }]);
}());


(function(){
    'use strict';
    angular.module('gantt').directive('ganttTaskContent', ['GanttDirectiveBuilder', function(Builder) {
        var builder = new Builder('ganttTaskContent');
        return builder.build();
    }]);
}());


(function(){
    'use strict';
    angular.module('gantt').directive('ganttTaskForeground', ['GanttDirectiveBuilder', function(Builder) {
        var builder = new Builder('ganttTaskForeground');
        return builder.build();
    }]);
}());


(function(){
    'use strict';
    angular.module('gantt').directive('ganttTimeFrame', ['GanttDirectiveBuilder', function(Builder) {
        var builder = new Builder('ganttTimeFrame');
        builder.controller = function($scope, $element) {
            $scope.timeFrame.$element = $element;
            $scope.timeFrame.$scope = $scope;
            $scope.timeFrame.updateView();
        };
        return builder.build();
    }]);
}());


(function(){
    'use strict';
    angular.module('gantt').directive('ganttTimespan', ['GanttDirectiveBuilder', function(Builder) {
        var builder = new Builder('ganttTimespan');
        builder.controller = function($scope, $element) {
            $scope.timespan.$element = $element;
            $scope.timespan.$scope = $scope;
            $scope.timespan.updateView();
        };
        return builder.build();
    }]);
}());


(function(){
    'use strict';
    angular.module('gantt').factory('ganttDebounce', ['$timeout', function($timeout) {
        function debounce(fn, timeout, invokeApply) {
            var nthCall = 0;
            return function() {
                var self = this;
                var argz = arguments;
                nthCall++;
                var later = (function(version) {
                    return function() {
                        if (version === nthCall) {
                            return fn.apply(self, argz);
                        }
                    };
                })(nthCall);
                return $timeout(later, timeout, invokeApply === undefined ? true: invokeApply);
            };
        }

        return debounce;
    }]);
}());

(function(){
    'use strict';
    angular.module('gantt').service('GanttDirectiveBuilder', ['$templateCache', function($templateCache) {
        var DirectiveBuilder = function DirectiveBuilder(directiveName, templateUrl, require, restrict) {
            var self = this;

            this.directiveName = directiveName;
            this.templateUrl = templateUrl === undefined ? 'template/' + directiveName + '.tmpl.html' : templateUrl;
            this.require = require === undefined ? '^gantt' : require;
            this.restrict = restrict === undefined ? 'E' : restrict;
            this.scope = false;
            this.transclude = true;
            this.replace = true;

            this.build = function() {
                var directiveName = self.directiveName;
                var templateUrl = self.templateUrl;
                var controllerFunction = self.controller;

                var directive = {
                    restrict: self.restrict,
                    require: self.require,
                    transclude: self.transclude,
                    replace: self.replace,
                    scope: self.scope,
                    templateUrl: function(tElement, tAttrs) {
                        if (tAttrs.templateUrl !== undefined) {
                            templateUrl = tAttrs.templateUrl;
                        }
                        if (tAttrs.template !== undefined) {
                            $templateCache.put(templateUrl, tAttrs.template);
                        }
                        return templateUrl;
                    },
                    compile: function () {
                        return {
                            pre: function preLink(scope, iElement, iAttrs, controller) {
                                scope.gantt.api.directives.raise.preLink(directiveName, scope, iElement, iAttrs, controller);
                            },
                            post: function postLink(scope, iElement, iAttrs, controller) {
                                scope.gantt.api.directives.raise.postLink(directiveName, scope, iElement, iAttrs, controller);

                            }
                        };
                    },
                    controller: ['$scope', '$element', '$attrs', function($scope, $element, $attrs) {
                        var controller = this;

                        if (controllerFunction !== undefined) {
                            controllerFunction($scope, $element, $attrs, controller);
                        }

                        $scope.gantt.api.directives.raise.controller(directiveName, $scope, $element, $attrs, controller);
                        $scope.$on('$destroy', function() {
                            $scope.gantt.api.directives.raise.destroy(directiveName, $scope, $element, $attrs, controller);
                        });

                        $scope.$applyAsync(function() {
                            $scope.gantt.api.directives.raise.new(directiveName, $scope, $element, $attrs, controller);
                        });
                    }]
                };

                if (!templateUrl) {
                    delete directive.templateUrl;
                    delete directive.replace;
                    delete directive.transclude;
                }

                return directive;
            };
        };

        return DirectiveBuilder;
    }]);
}());

(function() {
    'use strict';
    angular.module('gantt').service('ganttDom', ['$document', function($document) {
        return {
            elementFromPoint: function(x, y) {
                return $document[0].elementFromPoint(x, y);
            },
            elementsFromPoint: function(x, y, depth) {
                var elements = [], previousPointerEvents = [], cDepth = 0, current, i, l, d;

                // get all elements via elementFromPoint, and remove them from hit-testing in order
                while ((current = this.elementFromPoint(x, y)) && elements.indexOf(current) === -1 && current !== null &&
                (depth === undefined || cDepth < depth)) {

                    // push the element and its current style
                    elements.push(current);
                    previousPointerEvents.push({
                        value: current.style.getPropertyValue('visibility'),
                        priority: current.style.getPropertyPriority('visibility')
                    });

                    // add "pointer-events: none", to get to the underlying element
                    current.style.setProperty('visibility', 'hidden', 'important');

                    cDepth++;
                }

                // restore the previous pointer-events values
                for (i = 0, l = previousPointerEvents.length; i < l; i++) {
                    d = previousPointerEvents[i];
                    elements[i].style.setProperty('visibility', d.value ? d.value : '', d.priority);
                }

                return elements;
            },
            findElementFromPoint: function(x, y, checkFunction) {
                var elements = [], previousPointerEvents = [], cDepth = 0, current, found, i, l, d;

                // get all elements via elementFromPoint, and remove them from hit-testing in order
                while ((current = this.elementFromPoint(x, y)) && elements.indexOf(current) === -1 && current !== null) {

                    // push the element and its current style
                    elements.push(current);
                    previousPointerEvents.push({
                        value: current.style.getPropertyValue('visibility'),
                        priority: current.style.getPropertyPriority('visibility')
                    });

                    // add "visibility: hidden", to get to the underlying element.
                    // Would be better with pointer-events: none, but IE<11 doesn't support this.
                    current.style.setProperty('visibility', 'hidden', 'important');

                    cDepth++;

                    if (checkFunction(current)) {
                        found = current;
                        break;
                    }
                }

                // restore the previous pointer-events values
                for (i = 0, l = previousPointerEvents.length; i < l; i++) {
                    d = previousPointerEvents[i];
                    elements[i].style.setProperty('visibility', d.value ? d.value : '', d.priority);
                }

                return found;
            },
            isElementVisible: function(element) {
                return element.offsetParent !== undefined && element.offsetParent !== null;
            }
        };
    }]);
}());

(function(){
    'use strict';
    angular.module('gantt').service('ganttEnableNgAnimate', ['$injector', function($injector) {
        var ngAnimate;
        try {
            ngAnimate = $injector.get('$animate');
        } catch (e) {
        }

        if (ngAnimate !== undefined) {
            return function(element, enabled) {
                if (angular.version.major >= 1 && angular.version.minor >= 4) {
                    // AngularJS 1.4 breaking change, arguments are flipped.
                    ngAnimate.enabled(element, enabled);
                } else {
                    ngAnimate.enabled(enabled, element);
                }

            };
        } else {
            return angular.noop;
        }


    }]);
}());


(function() {
    'use strict';
    angular.module('gantt').directive('ganttBindCompileHtml', ['$compile', function($compile) {
        return {
            restrict: 'A',
            require: '^gantt',
            link: function(scope, element, attrs, ganttCtrl) {
                scope.scope = ganttCtrl.gantt.$scope.$parent;
                scope.$watch(function() {
                    return scope.$eval(attrs.ganttBindCompileHtml);
                }, function(value) {
                    element.html(value);
                    $compile(element.contents())(scope);
                });
            }
        };
    }]);
}());

(function(){
    'use strict';
    angular.module('gantt').service('ganttLayout', ['$document', function($document) {
        return {
            /**
             * Compute the width of scrollbar.
             *
             * @returns {number} width of the scrollbar, in px.
             */
            getScrollBarWidth: function() {
                var inner = $document[0].createElement('p');
                inner.style.width = '100%';
                inner.style.height = '200px';

                var outer = $document[0].createElement('div');
                outer.style.position = 'absolute';
                outer.style.top = '0px';
                outer.style.left = '0px';
                outer.style.visibility = 'hidden';
                outer.style.width = '200px';
                outer.style.height = '150px';
                outer.style.overflow = 'hidden';
                outer.appendChild (inner);

                $document[0].body.appendChild (outer);

                var w1 = inner.offsetWidth;
                outer.style.overflow = 'scroll';

                var w2 = inner.offsetWidth;
                if (w1 === w2) {
                    w2 = outer.clientWidth;
                }

                $document[0].body.removeChild (outer);

                return (w1 - w2);
            },
            /**
             * Compute the height of scrollbar.
             *
             * @returns {number} height of the scrollbar, in px.
             */
            getScrollBarHeight: function() {
                var inner = $document[0].createElement('p');
                inner.style.width = '200px;';
                inner.style.height = '100%';

                var outer = $document[0].createElement('div');
                outer.style.position = 'absolute';
                outer.style.top = '0px';
                outer.style.left = '0px';
                outer.style.visibility = 'hidden';
                outer.style.width = '150px';
                outer.style.height = '200px';
                outer.style.overflow = 'hidden';
                outer.appendChild (inner);

                $document[0].body.appendChild (outer);

                var h1 = inner.offsetHeight;
                outer.style.overflow = 'scroll';

                var h2 = inner.offsetHeight;
                if (h1 === h2) {
                    h2 = outer.clientHeight;
                }

                $document[0].body.removeChild (outer);

                return (h1 - h2);
            },

            setColumnsWidthFactor: function(columns, widthFactor, originalLeftOffset) {
                if (!columns) {
                    return;
                }

                if (!originalLeftOffset) {
                    originalLeftOffset = 0;
                }

                for (var i=0; i<columns.length; i++) {
                    var column = columns[i];
                    column.left = (widthFactor * (column.originalSize.left + originalLeftOffset)) - originalLeftOffset;
                    column.width = widthFactor * column.originalSize.width;

                    for (var j=0; j<column.timeFrames.length; j++) {
                        var timeFrame = column.timeFrames[j];
                        timeFrame.left = widthFactor * timeFrame.originalSize.left;
                        timeFrame.width = widthFactor * timeFrame.originalSize.width;
                    }
                }
            }
        };
    }]);
}());


(function(){
    'use strict';
    angular.module('gantt').service('ganttMouseButton', [ function() {
        // Mouse button cross browser normalization

        return {
            getButton: function(e) {
                e = e || window.event;

                if (!e.which) {
                    if (e.button === undefined) {
                        return 1;
                    }
                    return e.button < 2 ? 1 : e.button === 4 ? 2 : 3;
                } else {
                    return e.which;
                }
            }
        };
    }]);
}());

(function(){
    'use strict';
    angular.module('gantt').service('ganttMouseOffset', [ function() {
        // Mouse offset support for lesser browsers (read IE 8)

        return {
            getTouch: function(evt) {
                if (evt.touches !== undefined) {
                    return evt.touches[0];
                }
                return evt;
            },
            getOffset: function(evt) {
                if (evt.offsetX && evt.offsetY) {
                    return { x: evt.offsetX, y: evt.offsetY };
                }
                if (evt.layerX && evt.layerY) {
                    return { x: evt.layerX, y: evt.layerY };
                }
                return this.getOffsetForElement(evt.target, evt);
            },
            getOffsetForElement: function(el, evt) {
                var bb = el.getBoundingClientRect();
                return { x: evt.clientX - bb.left, y: evt.clientY - bb.top };
            }
        };
    }]);
}());

(function(){
    'use strict';
    angular.module('gantt').factory('ganttSmartEvent', [function() {
        // Auto released the binding when the scope is destroyed. Use if an event is registered on another element than the scope.

        function smartEvent($scope, $element, event, fn) {
            $scope.$on('$destroy', function() {
                $element.unbind(event, fn);
            });

            return {
                bindOnce: function() {
                    $element.one(event, fn);
                },
                bind: function() {
                    $element.bind(event, fn);
                },
                unbind: function() {
                    $element.unbind(event, fn);
                }
            };
        }

        return smartEvent;
    }]);
}());

angular.module('gantt.templates', []).run(['$templateCache', function($templateCache) {
    $templateCache.put('template/gantt.tmpl.html',
        '<div class="gantt unselectable" ng-cloak gantt-scroll-manager gantt-element-width-listener="ganttElementWidth">\n' +
        '    <gantt-side>\n' +
        '        <gantt-side-background>\n' +
        '        </gantt-side-background>\n' +
        '        <gantt-side-content>\n' +
        '        </gantt-side-content>\n' +
        '        <div gantt-resizer="gantt.side.$element" gantt-resizer-event-topic="side" gantt-resizer-enabled="{{$parent.gantt.options.value(\'allowSideResizing\')}}" resizer-width="sideWidth" class="gantt-resizer">\n' +
        '            <div ng-show="$parent.gantt.options.value(\'allowSideResizing\')" class="gantt-resizer-display"></div>\n' +
        '        </div>\n' +
        '    </gantt-side>\n' +
        '    <gantt-scrollable-header>\n' +
        '        <gantt-header gantt-element-height-listener="$parent.ganttHeaderHeight">\n' +
        '            <gantt-header-columns>\n' +
        '                <div ng-repeat="header in gantt.columnsManager.visibleHeaders track by $index">\n' +
        '                    <div class="gantt-header-row" ng-class="{\'gantt-header-row-last\': $last, \'gantt-header-row-first\': $first}">\n' +
        '                        <gantt-column-header ng-repeat="column in header"></gantt-column-header>\n' +
        '                    </div>\n' +
        '                </div>\n' +
        '            </gantt-header-columns>\n' +
        '        </gantt-header>\n' +
        '    </gantt-scrollable-header>\n' +
        '    <gantt-scrollable>\n' +
        '        <gantt-body>\n' +
        '            <gantt-body-background>\n' +
        '                <gantt-row-background ng-repeat="row in gantt.rowsManager.visibleRows track by row.model.id"></gantt-row-background>\n' +
        '            </gantt-body-background>\n' +
        '            <gantt-body-foreground>\n' +
        '                <div class="gantt-current-date-line" ng-show="currentDate === \'line\' && gantt.currentDateManager.position >= 0 && gantt.currentDateManager.position <= gantt.width" ng-style="{\'left\': gantt.currentDateManager.position + \'px\' }"></div>\n' +
        '            </gantt-body-foreground>\n' +
        '            <gantt-body-columns>\n' +
        '                <gantt-column ng-repeat="column in gantt.columnsManager.visibleColumns">\n' +
        '                    <gantt-time-frame ng-repeat="timeFrame in column.visibleTimeFrames"></gantt-time-frame>\n' +
        '                </gantt-column>\n' +
        '            </gantt-body-columns>\n' +
        '            <div ng-if="gantt.columnsManager.visibleColumns == 0" style="background-color: #808080"></div>\n' +
        '            <gantt-body-rows>\n' +
        '                <gantt-timespan ng-repeat="timespan in gantt.timespansManager.timespans track by timespan.model.id"></gantt-timespan>\n' +
        '                <gantt-row ng-repeat="row in gantt.rowsManager.visibleRows track by row.model.id">\n' +
        '                    <gantt-task ng-repeat="task in row.visibleTasks track by task.model.id">\n' +
        '                    </gantt-task>\n' +
        '                </gantt-row>\n' +
        '            </gantt-body-rows>\n' +
        '        </gantt-body>\n' +
        '    </gantt-scrollable>\n' +
        '\n' +
        '    <!-- Plugins -->\n' +
        '    <ng-transclude></ng-transclude>\n' +
        '\n' +
        '    <!--\n' +
        '    ******* Inline templates *******\n' +
        '    You can specify your own templates by either changing the default ones below or by\n' +
        '    adding an attribute template-url="<url to your template>" on the specific element.\n' +
        '    -->\n' +
        '\n' +
        '    <!-- Body template -->\n' +
        '    <script type="text/ng-template" id="template/ganttBody.tmpl.html">\n' +
        '        <div ng-transclude class="gantt-body" ng-style="{\'width\': gantt.width > 0 ? gantt.width +\'px\' : undefined}"></div>\n' +
        '    </script>\n' +
        '\n' +
        '    <!-- Header template -->\n' +
        '    <script type="text/ng-template" id="template/ganttHeader.tmpl.html">\n' +
        '        <div ng-transclude class="gantt-header"\n' +
        '             ng-show="gantt.columnsManager.columns.length > 0 && gantt.columnsManager.headers.length > 0"></div>\n' +
        '    </script>\n' +
        '\n' +
        '    <!-- Side template -->\n' +
        '    <script type="text/ng-template" id="template/ganttSide.tmpl.html">\n' +
        '        <div ng-transclude class="gantt-side" style="width: auto;"></div>\n' +
        '    </script>\n' +
        '\n' +
        '    <!-- Side content template-->\n' +
        '    <script type="text/ng-template" id="template/ganttSideContent.tmpl.html">\n' +
        '        <div class="gantt-side-content">\n' +
        '        </div>\n' +
        '    </script>\n' +
        '\n' +
        '    <!-- Header columns template -->\n' +
        '    <script type="text/ng-template" id="template/ganttHeaderColumns.tmpl.html">\n' +
        '        <div ng-transclude class="gantt-header-columns"\n' +
        '              gantt-horizontal-scroll-receiver></div>\n' +
        '    </script>\n' +
        '\n' +
        '    <script type="text/ng-template" id="template/ganttColumnHeader.tmpl.html">\n' +
        '        <div class="gantt-column-header" ng-class="{\'gantt-column-header-last\': $last, \'gantt-column-header-first\': $first}">{{::column.label}}</div>\n' +
        '    </script>\n' +
        '\n' +
        '    <!-- Body background template -->\n' +
        '    <script type="text/ng-template" id="template/ganttBodyBackground.tmpl.html">\n' +
        '        <div ng-transclude class="gantt-body-background"></div>\n' +
        '    </script>\n' +
        '\n' +
        '    <!-- Body foreground template -->\n' +
        '    <script type="text/ng-template" id="template/ganttBodyForeground.tmpl.html">\n' +
        '        <div ng-transclude class="gantt-body-foreground"></div>\n' +
        '    </script>\n' +
        '\n' +
        '    <!-- Body columns template -->\n' +
        '    <script type="text/ng-template" id="template/ganttBodyColumns.tmpl.html">\n' +
        '        <div ng-transclude class="gantt-body-columns"></div>\n' +
        '    </script>\n' +
        '\n' +
        '    <script type="text/ng-template" id="template/ganttColumn.tmpl.html">\n' +
        '        <div ng-transclude class="gantt-column gantt-foreground-col" ng-class="{\'gantt-column-last\': $last, \'gantt-column-first\': $first}"></div>\n' +
        '    </script>\n' +
        '\n' +
        '    <script type="text/ng-template" id="template/ganttTimeFrame.tmpl.html">\n' +
        '        <div class="gantt-timeframe"></div>\n' +
        '    </script>\n' +
        '\n' +
        '    <!-- Scrollable template -->\n' +
        '    <script type="text/ng-template" id="template/ganttScrollable.tmpl.html">\n' +
        '        <div ng-transclude class="gantt-scrollable" gantt-scroll-sender ng-style="getScrollableCss()"></div>\n' +
        '    </script>\n' +
        '\n' +
        '    <script type="text/ng-template" id="template/ganttScrollableHeader.tmpl.html">\n' +
        '        <div ng-transclude class="gantt-scrollable-header" ng-style="getScrollableHeaderCss()"></div>\n' +
        '    </script>\n' +
        '\n' +
        '    <!-- Rows template -->\n' +
        '    <script type="text/ng-template" id="template/ganttBodyRows.tmpl.html">\n' +
        '        <div ng-transclude class="gantt-body-rows"></div>\n' +
        '    </script>\n' +
        '\n' +
        '    <!-- Timespan template -->\n' +
        '    <script type="text/ng-template" id="template/ganttTimespan.tmpl.html">\n' +
        '        <div class="gantt-timespan" ng-class="timespan.model.classes">\n' +
        '        </div>\n' +
        '    </script>\n' +
        '\n' +
        '    <!-- Task template -->\n' +
        '    <script type="text/ng-template" id="template/ganttTask.tmpl.html">\n' +
        '        <div class="gantt-task" ng-class="task.model.classes">\n' +
        '            <gantt-task-background></gantt-task-background>\n' +
        '            <gantt-task-foreground></gantt-task-foreground>\n' +
        '            <gantt-task-content></gantt-task-content>\n' +
        '        </div>\n' +
        '    </script>\n' +
        '\n' +
        '    <script type="text/ng-template" id="template/ganttTaskBackground.tmpl.html">\n' +
        '        <div class="gantt-task-background" ng-style="{\'background-color\': task.model.color}"></div>\n' +
        '    </script>\n' +
        '\n' +
        '    <script type="text/ng-template" id="template/ganttTaskForeground.tmpl.html">\n' +
        '        <div class="gantt-task-foreground">\n' +
        '            <div ng-if="task.truncatedRight" class="gantt-task-truncated-right">&gt;</div>\n' +
        '            <div ng-if="task.truncatedLeft" class="gantt-task-truncated-left">&lt;</div>\n' +
        '        </div>\n' +
        '    </script>\n' +
        '\n' +
        '    <!-- Task content template -->\n' +
        '    <script type="text/ng-template" id="template/ganttTaskContent.tmpl.html">\n' +
        '        <div class="gantt-task-content" unselectable="on"><span unselectable="on" gantt-bind-compile-html="getTaskContent()"/></div>\n' +
        '    </script>\n' +
        '\n' +
        '\n' +
        '    <!-- Row background template -->\n' +
        '    <script type="text/ng-template" id="template/ganttRowBackground.tmpl.html">\n' +
        '        <div class="gantt-row gantt-row-height"\n' +
        '             ng-class="row.model.classes"\n' +
        '             ng-class-odd="\'gantt-row-odd\'"\n' +
        '             ng-class-even="\'gantt-row-even\'"\n' +
        '             ng-style="{\'height\': row.model.height}">\n' +
        '            <div class="gantt-row-background"\n' +
        '                 ng-style="{\'background-color\': row.model.color}">\n' +
        '            </div>\n' +
        '        </div>\n' +
        '    </script>\n' +
        '\n' +
        '    <!-- Row template -->\n' +
        '    <script type="text/ng-template" id="template/ganttRow.tmpl.html">\n' +
        '        <div class="gantt-row gantt-row-height"\n' +
        '             ng-class="row.model.classes"\n' +
        '             ng-class-odd="\'gantt-row-odd\'"\n' +
        '             ng-class-even="\'gantt-row-even\'"\n' +
        '             ng-style="{\'height\': row.model.height}">\n' +
        '            <div ng-transclude class="gantt-row-content"></div>\n' +
        '        </div>\n' +
        '    </script>\n' +
        '\n' +
        '    <!-- Side background template -->\n' +
        '    <script type="text/ng-template" id="template/ganttSideBackground.tmpl.html">\n' +
        '        <div class="gantt-side-background">\n' +
        '            <div class="gantt-side-background-header" ng-style="{height: $parent.ganttHeaderHeight + \'px\'}">\n' +
        '                <div ng-show="$parent.ganttHeaderHeight" class="gantt-header-row gantt-side-header-row"></div>\n' +
        '            </div>\n' +
        '            <div class="gantt-side-background-body" ng-style="getMaxHeightCss()">\n' +
        '                <div gantt-vertical-scroll-receiver>\n' +
        '                    <div class="gantt-row gantt-row-height "\n' +
        '                         ng-class-odd="\'gantt-row-odd\'"\n' +
        '                         ng-class-even="\'gantt-row-even\'"\n' +
        '                         ng-class="row.model.classes"\n' +
        '                         ng-repeat="row in gantt.rowsManager.visibleRows track by row.model.id"\n' +
        '                         ng-style="{\'height\': row.model.height}">\n' +
        '                        <div gantt-row-label class="gantt-row-label gantt-row-background"\n' +
        '                             ng-style="{\'background-color\': row.model.color}">\n' +
        '                        </div>\n' +
        '                    </div>\n' +
        '                </div>\n' +
        '            </div>\n' +
        '        </div>\n' +
        '    </script>\n' +
        '</div>\n' +
        '');
}]);

//# sourceMappingURL=angular-gantt.js.map




/*
Project: angular-gantt v1.2.14 - Gantt chart component for AngularJS
Authors: Marco Schweighauser, Rémi Alvergnat
License: MIT
Homepage: https://www.angular-gantt.com
Github: https://github.com/angular-gantt/angular-gantt.git
*/
(function(){
    'use strict';
    angular.module('gantt.bounds', ['gantt', 'gantt.bounds.templates']).directive('ganttBounds', ['moment', '$compile', '$document', function(moment, $compile, $document) {
        return {
            restrict: 'E',
            require: '^gantt',
            scope: {
                enabled: '=?'
            },
            link: function(scope, element, attrs, ganttCtrl) {
                var api = ganttCtrl.gantt.api;

                // Load options from global options attribute.
                if (scope.options && typeof(scope.options.bounds) === 'object') {
                    for (var option in scope.options.bounds) {
                        scope[option] = scope.options[option];
                    }
                }

                if (scope.enabled === undefined) {
                    scope.enabled = true;
                }

                api.directives.on.new(scope, function(directiveName, taskScope, taskElement) {
                    if (directiveName === 'ganttTask') {
                        var boundsScope = taskScope.$new();
                        boundsScope.pluginScope = scope;

                        var ifElement = $document[0].createElement('div');
                        angular.element(ifElement).attr('data-ng-if', 'task.model.est && task.model.lct && pluginScope.enabled');
                        var boundsElement = $document[0].createElement('gantt-task-bounds');
                        if (attrs.templateUrl !== undefined) {
                            angular.element(boundsElement).attr('data-template-url', attrs.templateUrl);
                        }
                        if (attrs.template !== undefined) {
                            angular.element(boundsElement).attr('data-template', attrs.template);
                        }
                        angular.element(ifElement).append(boundsElement);
                        taskElement.append($compile(ifElement)(boundsScope));
                    }
                });

                api.tasks.on.clean(scope, function(model) {
                    if (model.est !== undefined && !moment.isMoment(model.est)) {
                        model.est = moment(model.est);  //Earliest Start Time
                    }
                    if (model.lct !== undefined && !moment.isMoment(model.lct)) {
                        model.lct = moment(model.lct);  //Latest Completion Time
                    }
                });
            }
        };
    }]);
}());


(function() {
    'use strict';
    angular.module('gantt.dependencies', ['gantt', 'gantt.dependencies.templates']).directive('ganttDependencies',
        ['$timeout', '$document', 'ganttDebounce', 'GanttDependenciesManager', 'GanttDependenciesChecker',
            function($timeout, $document, debounce, DependenciesManager, DependenciesChecker) {
                return {
                    restrict: 'E',
                    require: '^gantt',
                    scope: {
                        enabled: '=?',
                        readOnly: '=?',
                        jsPlumbDefaults: '=?',
                        endpoints: '=?',
                        fallbackEndpoints: '=?',
                        conflictChecker: '=?'
                    },
                    link: function(scope, element, attrs, ganttCtrl) {
                        var api = ganttCtrl.gantt.api;

                        // Load options from global options attribute.
                        if (scope.options && typeof(scope.options.dependencies) === 'object') {
                            for (var option in scope.options.dependencies) {
                                scope[option] = scope.options[option];
                            }
                        }

                        if (scope.enabled === undefined) {
                            scope.enabled = true;
                        }

                        if (scope.readOnly === undefined) {
                            scope.readOnly = false;
                        }

                        if (scope.jsPlumbDefaults === undefined) {
                            // https://jsplumbtoolkit.com/community/doc/defaults.html
                            scope.jsPlumbDefaults = {
                                Endpoint: ['Dot', {radius: 4}],
                                EndpointStyle: {fillStyle: '#456', strokeStyle: '#456', lineWidth: 1},
                                Connector: 'Flowchart',
                                ConnectionOverlays: [['Arrow', {location: 1, length: 12, width: 12}]]
                            };
                        }

                        function createLeftOverlay() {
                            return angular.element('<span><span class="gantt-endpoint-overlay start-endpoint arrow-right"></span></span>');
                        }

                        function createRightOverlay() {
                            return angular.element('<span><span class="gantt-endpoint-overlay end-endpoint arrow-right"></span></span>');
                        }

                        function createLeftFallbackOverlay() {
                            return angular.element('<span><span class="gantt-endpoint-overlay start-endpoint fallback-endpoint"></span></span>');
                        }

                        function createRightFallbackOverlay() {
                            return angular.element('<span><span class="gantt-endpoint-overlay end-endpoint fallback-endpoint"></span></span>');
                        }

                        if (scope.endpoints === undefined) {
                            scope.endpoints = [
                                {
                                    anchor: 'Left',
                                    isSource: false,
                                    isTarget: true,
                                    maxConnections: -1,
                                    cssClass: 'gantt-endpoint start-endpoint target-endpoint',
                                    overlays: [
                                        ['Custom', {create: createLeftOverlay}]
                                    ]

                                },
                                {
                                    anchor: 'Right',
                                    isSource: true,
                                    isTarget: false,
                                    maxConnections: -1,
                                    cssClass: 'gantt-endpoint end-endpoint source-endpoint',
                                    overlays: [
                                        ['Custom', {create: createRightOverlay}]
                                    ]
                                }
                            ];
                        }

                        if (scope.fallbackEndpoints === undefined) {
                            scope.fallbackEndpoints = [
                                {
                                    endpoint: 'Blank',
                                    anchor: 'Left',
                                    isSource: false,
                                    isTarget: true,
                                    maxConnections: 0,
                                    cssClass: 'gantt-endpoint start-endpoint fallback-endpoint',
                                    overlays: [
                                        ['Custom', {create: createLeftFallbackOverlay}]
                                    ]
                                },
                                {
                                    endpoint: 'Blank',
                                    anchor: 'Right',
                                    isSource: true,
                                    isTarget: false,
                                    maxConnections: 0,
                                    cssClass: 'gantt-endpoint end-endpoint fallback-endpoint',
                                    overlays: [
                                        ['Custom', {create: createRightFallbackOverlay}]
                                    ]
                                }
                            ];
                        }

                        if (scope.conflictChecker === undefined) {
                            scope.conflictChecker = false;
                        }

                        var manager = new DependenciesManager(ganttCtrl.gantt, scope, api);
                        var checker = new DependenciesChecker(manager, scope, api);

                        scope.$watchGroup(['conflictChecker', 'enabled'], function(newValue, oldValue) {
                            if (newValue !== oldValue) {
                                var rows = ganttCtrl.gantt.rowsManager.rows;
                                var allTasks = [];
                                for (var i = 0; i < rows.length; i++) {
                                    allTasks.push.apply(allTasks, rows[i].tasks);
                                }
                                if (scope.conflictChecker && scope.enabled) {
                                    checker.refresh(allTasks);
                                } else {
                                    checker.clear(allTasks);
                                }

                            }
                        });

                        api.directives.on.new(scope, function(directiveName, directiveScope, directiveElement) {
                            if (directiveName === 'ganttBody') {
                                manager.plumb.setContainer(directiveElement);
                            }
                        });

                        api.tasks.on.add(scope, function(task) {
                            manager.addDependenciesFromTask(task);
                        });

                        api.tasks.on.remove(scope, function(task) {
                            manager.removeDependenciesFromTask(task);
                        });

                        api.tasks.on.displayed(scope, debounce(function(tasks) {
                            manager.setTasks(tasks);
                            manager.refresh();
                            if (scope.conflictChecker && scope.enabled) {
                                checker.refresh(tasks);
                            }
                        }));

                        api.rows.on.displayed(scope, function() {
                            manager.refresh();
                        });

                        api.tasks.on.viewChange(scope, function(task) {
                            if (task.$element) {
                                manager.plumb.revalidate(task.$element[0]);
                            }
                            if (scope.conflictChecker && scope.enabled) {
                                checker.refresh([task]);
                            }
                        });

                        api.tasks.on.viewRowChange(scope, function(task) {
                            manager.setTask(task);
                            if (scope.conflictChecker && scope.enabled) {
                                checker.refresh([task]);
                            }
                        });

                        api.dependencies.on.add(scope, function(dependency) {
                            if (scope.conflictChecker && scope.enabled) {
                                checker.refresh([dependency.getFromTask(), dependency.getToTask()]);
                            }
                        });

                        api.dependencies.on.change(scope, function(dependency) {
                            if (scope.conflictChecker && scope.enabled) {
                                checker.refresh([dependency.getFromTask(), dependency.getToTask()]);
                            }
                        });

                        api.dependencies.on.remove(scope, function(dependency) {
                            if (scope.conflictChecker && scope.enabled) {
                                checker.refresh([dependency.getFromTask(), dependency.getToTask()]);
                            }
                        });


                    }
                };
            }]);
}());


(function(){
    'use strict';
    angular.module('gantt.drawtask', ['gantt']).directive('ganttDrawTask', ['$document', 'ganttMouseOffset', 'ganttUtils', 'moment', function(document, mouseOffset, utils, moment) {
        return {
            restrict: 'E',
            require: '^gantt',
            scope: {
                enabled: '=?',
                moveThreshold: '=?',
                taskFactory: '=?'
            },
            link: function(scope, element, attrs, ganttCtrl) {
                var api = ganttCtrl.gantt.api;

                if (scope.enabled === undefined) {
                    scope.enabled = true;
                }

                if (scope.moveThreshold === undefined) {
                    scope.moveThreshold = 0;
                }

                if (scope.taskFactory === undefined) {
                    scope.taskFactory = function() {
                        return {}; // New empty task.
                    };
                }

                api.registerEvent('tasks', 'draw');
                api.registerEvent('tasks', 'drawBegin');
                api.registerEvent('tasks', 'drawEnd');

                var newTaskModel = function(row) {
                    if (row.model.drawTask && angular.isFunction(row.model.drawTask.taskFactory)) {
                        return row.model.drawTask.taskFactory();
                    } else {
                        return scope.taskFactory();
                    }
                };

                api.directives.on.new(scope, function(directiveName, directiveScope, element) {
                    if (directiveName === 'ganttRow') {
                        var addNewTask = function(x) {
                            var startDate = api.core.getDateByPosition(x, true);
                            var endDate = moment(startDate);

                            var taskModel = newTaskModel(directiveScope.row);
                            taskModel.from = startDate;
                            taskModel.to = endDate;

                            var task = directiveScope.row.addTask(taskModel);
                            task.isResizing = true;
                            task.updatePosAndSize();
                            directiveScope.row.updateVisibleTasks();

                            directiveScope.row.$scope.$digest();

                            return task;
                        };

                        var addEventListeners = function(task) {
                            var raiseDrawEvent = function() {
                                directiveScope.row.rowsManager.gantt.api.tasks.raise.draw(task);
                            };

                            directiveScope.row.rowsManager.gantt.api.tasks.raise.drawBegin(task);

                            document.on('mousemove', raiseDrawEvent);

                            document.one('mouseup', function() {
                                directiveScope.row.rowsManager.gantt.api.tasks.raise.drawEnd(task);
                                document.off('mousemove', raiseDrawEvent);
                            });
                        };

                        var deferDrawing = function(startX) {
                            var moveTrigger = function(evt) {
                                var currentX = mouseOffset.getOffset(evt).x;

                                if (Math.abs(startX - currentX) >= scope.moveThreshold) {
                                    element.off('mousemove', moveTrigger);
                                    var task = addNewTask(startX);
                                    addEventListeners(task);
                                }
                            };

                            element.on('mousemove', moveTrigger);
                            document.one('mouseup', function() {
                                element.off('mousemove', moveTrigger);
                            });
                        };

                        var drawHandler = function(evt) {
                            var evtTarget = (evt.target ? evt.target : evt.srcElement);

                            var rowDrawTask = directiveScope.row.model.drawTask;

                            if (typeof(rowDrawTask) === 'boolean' || angular.isFunction(rowDrawTask)) {
                                rowDrawTask = {enabled: rowDrawTask};
                            }

                            var enabledValue = utils.firstProperty([rowDrawTask], 'enabled', scope.enabled);
                            var enabled = angular.isFunction(enabledValue) ? enabledValue(evt, directiveScope.row) : enabledValue;
                            if (enabled && evtTarget.className.indexOf('gantt-row') > -1) {
                                var x = mouseOffset.getOffset(evt).x;

                                if (scope.moveThreshold === 0) {
                                    var task = addNewTask(x);
                                    addEventListeners(task);
                                } else {
                                    deferDrawing(x);
                                }
                            }
                        };

                        element.on('mousedown', drawHandler);
                        directiveScope.drawTaskHandler = drawHandler;
                    }
                });

                api.directives.on.destroy(scope, function(directiveName, directiveScope, element) {
                    if (directiveName === 'ganttRow') {
                        element.off('mousedown', directiveScope.drawTaskHandler);
                        delete directiveScope.drawTaskHandler;
                    }
                });
            }
        };
    }]);
}());


(function(){
    'use strict';
    angular.module('gantt.groups', ['gantt', 'gantt.groups.templates']).directive('ganttGroups', ['ganttUtils', 'GanttHierarchy', '$compile', '$document', function(utils, Hierarchy, $compile, $document) {
        // Provides the row sort functionality to any Gantt row
        // Uses the sortableState to share the current row

        return {
            restrict: 'E',
            require: '^gantt',
            scope: {
                enabled: '=?',
                display: '=?'
            },
            link: function(scope, element, attrs, ganttCtrl) {
                var api = ganttCtrl.gantt.api;

                // Load options from global options attribute.
                if (scope.options && typeof(scope.options.sortable) === 'object') {
                    for (var option in scope.options.sortable) {
                        scope[option] = scope.options[option];
                    }
                }

                if (scope.enabled === undefined) {
                    scope.enabled = true;
                }

                if (scope.display === undefined) {
                    scope.display = 'group';
                }

                scope.hierarchy = new Hierarchy();

                function refresh() {
                    scope.hierarchy.refresh(ganttCtrl.gantt.rowsManager.filteredRows);
                }

                ganttCtrl.gantt.api.registerMethod('groups', 'refresh', refresh, this);
                ganttCtrl.gantt.$scope.$watchCollection('gantt.rowsManager.filteredRows', function() {
                    refresh();
                });

                api.directives.on.new(scope, function(directiveName, rowScope, rowElement) {
                    if (directiveName === 'ganttRow') {
                        var taskGroupScope = rowScope.$new();
                        taskGroupScope.pluginScope = scope;

                        var ifElement = $document[0].createElement('div');
                        angular.element(ifElement).attr('data-ng-if', 'pluginScope.enabled');

                        var taskGroupElement = $document[0].createElement('gantt-task-group');
                        if (attrs.templateUrl !== undefined) {
                            angular.element(taskGroupElement).attr('data-template-url', attrs.templateUrl);
                        }
                        if (attrs.template !== undefined) {
                            angular.element(taskGroupElement).attr('data-template', attrs.template);
                        }

                        angular.element(ifElement).append(taskGroupElement);

                        rowElement.append($compile(ifElement)(taskGroupScope));
                    }
                });
            }
        };
    }]);
}());


(function(){
    'use strict';
    angular.module('gantt.labels', ['gantt', 'gantt.labels.templates']).directive('ganttLabels', ['ganttUtils', '$compile', '$document', '$log', function(utils, $compile, $document, $log) {
        // Provides the row sort functionality to any Gantt row
        // Uses the sortableState to share the current row

        return {
            restrict: 'E',
            require: '^gantt',
            scope: {
                enabled: '=?',
                header: '=?'
            },
            link: function(scope, element, attrs, ganttCtrl) {
                var api = ganttCtrl.gantt.api;

                $log.warn('Angular Gantt Labels plugin is deprecated. Please use Table plugin instead.');

                // Load options from global options attribute.
                if (scope.options && typeof(scope.options.sortable) === 'object') {
                    for (var option in scope.options.sortable) {
                        scope[option] = scope.options[option];
                    }
                }

                if (scope.enabled === undefined) {
                    scope.enabled = true;
                }

                if (scope.header === undefined) {
                    scope.header = 'Name';
                }

                api.directives.on.new(scope, function(directiveName, sideContentScope, sideContentElement) {
                    if (directiveName === 'ganttSideContent') {
                        var labelsScope = sideContentScope.$new();
                        labelsScope.pluginScope = scope;

                        var ifElement = $document[0].createElement('div');
                        angular.element(ifElement).attr('data-ng-if', 'pluginScope.enabled');
                        angular.element(ifElement).addClass('side-element');

                        var labelsElement = $document[0].createElement('gantt-side-content-labels');
                        angular.element(ifElement).append(labelsElement);

                        sideContentElement.append($compile(ifElement)(labelsScope));
                    }
                });

                function fitSideWidthToLabels() {
                    var labels = ganttCtrl.gantt.side.$element[0].getElementsByClassName('gantt-row-label');
                    var newSideWidth = 0;

                    for (var i=0; i<labels.length; i++) {
                        var width = labels[i].children[0].offsetWidth;
                        newSideWidth = Math.max(newSideWidth, width);
                    }

                    if (newSideWidth >= 0) {
                        api.side.setWidth(newSideWidth);
                    }
                }

                api.registerMethod('labels', 'fitSideWidth', fitSideWidthToLabels, this);
            }
        };
    }]);
}());


(function(){
    'use strict';

    /* jshint latedef: false */
    angular.module('gantt.movable', ['gantt']).directive('ganttMovable', ['ganttMouseButton', 'ganttMouseOffset', 'ganttSmartEvent', 'ganttMovableOptions', 'ganttUtils', 'ganttDom', '$window', '$document', '$timeout',
        function(mouseButton, mouseOffset, smartEvent, movableOptions, utils, dom, $window, $document, $timeout) {
            // Provides moving and resizing of tasks
            return {
                restrict: 'E',
                require: '^gantt',
                scope: {
                    enabled: '=?',
                    allowMoving: '=?',
                    allowResizing: '=?',
                    allowRowSwitching: '=?'
                },
                link: function(scope, element, attrs, ganttCtrl) {
                    var api = ganttCtrl.gantt.api;

                    // Load options from global options attribute.
                    if (scope.options && typeof(scope.options.movable) === 'object') {
                        for (var option in scope.options.movable) {
                            scope[option] = scope.options[option];
                        }
                    }

                    movableOptions.initialize(scope);

                    api.registerEvent('tasks', 'move');
                    api.registerEvent('tasks', 'moveBegin');
                    api.registerEvent('tasks', 'moveEnd');
                    api.registerEvent('tasks', 'resize');
                    api.registerEvent('tasks', 'resizeBegin');
                    api.registerEvent('tasks', 'resizeEnd');
                    api.registerEvent('tasks', 'change');

                    var _hasTouch = ('ontouchstart' in $window) || $window.DocumentTouch && $document[0] instanceof $window.DocumentTouch;
                    var _pressEvents = 'touchstart mousedown';
                    var _moveEvents = 'touchmove mousemove';
                    var _releaseEvents = 'touchend mouseup';

                    var taskWithSmallWidth = 15;
                    var resizeAreaWidthBig = 5;
                    var resizeAreaWidthSmall = 3;
                    var scrollSpeed = 15;
                    var scrollTriggerDistance = 5;
                    var mouseStartOffsetX;
                    var moveStartX;

                    api.directives.on.new(scope, function(directiveName, taskScope, taskElement) {
                        if (directiveName === 'ganttTask') {
                            var windowElement = angular.element($window);
                            var ganttBodyElement = taskScope.row.rowsManager.gantt.body.$element;
                            var ganttScrollElement = taskScope.row.rowsManager.gantt.scroll.$element;

                            var taskHasBeenChanged = false;
                            var taskHasBeenMovedFromAnotherRow = false;
                            var scrollInterval;

                            var foregroundElement = taskScope.task.getForegroundElement();

                            // IE<11 doesn't support `pointer-events: none`
                            // So task content element must be added to support moving properly.
                            var contentElement = taskScope.task.getContentElement();

                            var onPressEvents = function(evt) {
                                evt.preventDefault();
                                if (_hasTouch) {
                                    evt = mouseOffset.getTouch(evt);
                                }
                                var taskMovable = taskScope.task.model.movable;
                                var rowMovable = taskScope.task.row.model.movable;

                                if (typeof(taskMovable) === 'boolean' || angular.isFunction(taskMovable)) {
                                    taskMovable = {enabled: taskMovable};
                                }

                                if (typeof(rowMovable) === 'boolean' || angular.isFunction(rowMovable)) {
                                    rowMovable = {enabled: rowMovable};
                                }

                                var enabledValue = utils.firstProperty([taskMovable, rowMovable], 'enabled', scope.enabled);
                                var enabled = angular.isFunction(enabledValue) ? enabledValue(evt, taskScope.task): enabledValue;
                                if (enabled) {
                                    var taskOffsetX = mouseOffset.getOffsetForElement(foregroundElement[0], evt).x;
                                    var mode = getMoveMode(taskOffsetX);
                                    if (mode !== '' && mouseButton.getButton(evt) === 1) {
                                        var bodyOffsetX = mouseOffset.getOffsetForElement(ganttBodyElement[0], evt).x;
                                        enableMoveMode(mode, bodyOffsetX);
                                    }
                                    taskScope.$digest();
                                }
                            };
                            foregroundElement.on(_pressEvents, onPressEvents);
                            contentElement.on(_pressEvents, onPressEvents);

                            var onMousemove = function (evt) {
                                var taskMovable = taskScope.task.model.movable;
                                var rowMovable = taskScope.task.row.model.movable;

                                if (typeof(taskMovable) === 'boolean' || angular.isFunction(taskMovable)) {
                                    taskMovable = {enabled: taskMovable};
                                }

                                if (typeof(rowMovable) === 'boolean' || angular.isFunction(rowMovable)) {
                                    rowMovable = {enabled: rowMovable};
                                }

                                var enabledValue = utils.firstProperty([taskMovable, rowMovable], 'enabled', scope.enabled);
                                var enabled = angular.isFunction(enabledValue) ? enabledValue(evt, taskScope.task): enabledValue;
                                if (enabled && !taskScope.task.isMoving) {
                                    var taskOffsetX = mouseOffset.getOffsetForElement(foregroundElement[0], evt).x;
                                    var mode = getMoveMode(taskOffsetX);
                                    if (mode !== '' && mode !== 'M') {
                                        foregroundElement.css('cursor', getCursor(mode));
                                        contentElement.css('cursor', getCursor(mode));
                                    } else {
                                        foregroundElement.css('cursor', '');
                                        contentElement.css('cursor', '');
                                    }
                                }
                            };
                            foregroundElement.on('mousemove', onMousemove);
                            contentElement.on('mousemove', onMousemove);

                            var handleMove = function(evt) {
                                if (taskScope.task.isMoving && !taskScope.destroyed) {
                                    clearScrollInterval();
                                    moveTask(evt);
                                    scrollScreen(evt);
                                }
                            };

                            var moveTask = function(evt) {
                                var oldTaskHasBeenChanged = taskHasBeenChanged;

                                var mousePos = mouseOffset.getOffsetForElement(ganttBodyElement[0], evt);
                                var x = mousePos.x;
                                taskScope.task.mouseOffsetX = x;
                                var taskOutOfRange = taskScope.task.row.rowsManager.gantt.options.value('taskOutOfRange');

                                var taskMovable = taskScope.task.model.movable;
                                var rowMovable = taskScope.task.row.model.movable;

                                if (typeof(taskMovable) === 'boolean' || angular.isFunction(taskMovable)) {
                                    taskMovable = {enabled: taskMovable};
                                }

                                if (typeof(rowMovable) === 'boolean' || angular.isFunction(rowMovable)) {
                                    rowMovable = {enabled: rowMovable};
                                }

                                if (taskScope.task.moveMode === 'M') {
                                    var allowRowSwitching = utils.firstProperty([taskMovable, rowMovable], 'allowRowSwitching', scope.allowRowSwitching);
                                    if (allowRowSwitching) {
                                        var scrollRect = ganttScrollElement[0].getBoundingClientRect();
                                        var rowCenterLeft = scrollRect.left + scrollRect.width / 2;
                                        var ganttBody = angular.element($document[0].querySelectorAll('.gantt-body'));
                                        ganttBody.css('pointer-events', 'auto'); // pointer-events must be enabled for following to work.
                                        var targetRowElement = dom.findElementFromPoint(rowCenterLeft, evt.clientY, function(element) {
                                            return angular.element(element).hasClass('gantt-row');
                                        });
                                        ganttBody.css('pointer-events', '');

                                        var rows = ganttCtrl.gantt.rowsManager.rows;
                                        var targetRow;
                                        for (var i= 0, l=rows.length; i<l; i++) {
                                            if (targetRowElement === rows[i].$element[0]) {
                                                targetRow = rows[i];
                                                break;
                                            }
                                        }

                                        var sourceRow = taskScope.task.row;

                                        if (targetRow !== undefined && sourceRow !== targetRow) {
                                            targetRow.moveTaskToRow(taskScope.task, true);
                                            taskHasBeenChanged = true;
                                        }
                                    }

                                    var allowMoving = utils.firstProperty([taskMovable, rowMovable], 'allowMoving', scope.allowMoving);
                                    if (allowMoving) {
                                        x = x - mouseStartOffsetX;

                                        if (taskOutOfRange !== 'truncate') {
                                            if (x < 0) {
                                                x = 0;
                                            } else if (x + taskScope.task.width >= taskScope.gantt.width) {
                                                x = taskScope.gantt.width - taskScope.task.width;
                                            }
                                        }

                                        taskScope.task.moveTo(x, true);
                                        taskScope.$digest();

                                        if (taskHasBeenChanged) {
                                            taskScope.row.rowsManager.gantt.api.tasks.raise.move(taskScope.task);
                                        }
                                        taskHasBeenChanged = true;
                                    }
                                } else if (taskScope.task.moveMode === 'E') {
                                    if (x <= taskScope.task.left) {
                                        x = taskScope.task.left;
                                        taskScope.task.moveMode = 'W';
                                        setGlobalCursor(getCursor(taskScope.task.moveMode ));
                                    }

                                    if (taskOutOfRange !== 'truncate' && x >= taskScope.gantt.width) {
                                        x = taskScope.gantt.width;
                                    }

                                    taskScope.task.setTo(x, true);
                                    taskScope.$digest();

                                    if (taskHasBeenChanged) {
                                        taskScope.row.rowsManager.gantt.api.tasks.raise.resize(taskScope.task);
                                    }
                                    taskHasBeenChanged = true;
                                } else {
                                    if (x > taskScope.task.left + taskScope.task.width) {
                                        x = taskScope.task.left + taskScope.task.width;
                                        taskScope.task.moveMode = 'E';
                                        setGlobalCursor(getCursor(taskScope.task.moveMode ));
                                    }

                                    if (taskOutOfRange !== 'truncate' && x < 0) {
                                        x = 0;
                                    }

                                    taskScope.task.setFrom(x, true);
                                    taskScope.$digest();

                                    if (taskHasBeenChanged) {
                                        taskScope.row.rowsManager.gantt.api.tasks.raise.resize(taskScope.task);
                                    }
                                    taskHasBeenChanged = true;
                                }

                                if (!oldTaskHasBeenChanged && taskHasBeenChanged && !taskHasBeenMovedFromAnotherRow) {
                                    var backgroundElement = taskScope.task.getBackgroundElement();
                                    if (taskScope.task.moveMode === 'M') {
                                        backgroundElement.addClass('gantt-task-moving');
                                        taskScope.row.rowsManager.gantt.api.tasks.raise.moveBegin(taskScope.task);
                                    } else {
                                        backgroundElement.addClass('gantt-task-resizing');
                                        taskScope.row.rowsManager.gantt.api.tasks.raise.resizeBegin(taskScope.task);
                                    }
                                }
                            };

                            var scrollScreen = function(evt) {
                                var mousePos = mouseOffset.getOffsetForElement(ganttBodyElement[0], evt);
                                var leftScreenBorder = ganttScrollElement[0].scrollLeft;
                                var screenWidth = ganttScrollElement[0].offsetWidth;
                                var scrollWidth = ganttScrollElement[0].scrollWidth;
                                var rightScreenBorder = leftScreenBorder + screenWidth;
                                var keepOnScrolling = false;

                                if (mousePos.x < moveStartX) {
                                    // Scroll to the left
                                    if (leftScreenBorder > 0 && mousePos.x <= leftScreenBorder + scrollTriggerDistance) {
                                        mousePos.x -= scrollSpeed;
                                        keepOnScrolling = true;
                                        taskScope.row.rowsManager.gantt.api.scroll.left(scrollSpeed);
                                    }
                                } else {
                                    // Scroll to the right
                                    if (rightScreenBorder < scrollWidth && mousePos.x >= rightScreenBorder - scrollTriggerDistance) {
                                        mousePos.x += scrollSpeed;
                                        keepOnScrolling = true;
                                        taskScope.row.rowsManager.gantt.api.scroll.right(scrollSpeed);
                                    }
                                }

                                if (keepOnScrolling) {
                                    scrollInterval = $timeout(function() {
                                        handleMove(evt);
                                    }, 100, true);
                                }
                            };

                            var clearScrollInterval = function() {
                                if (scrollInterval !== undefined) {
                                    $timeout.cancel(scrollInterval);
                                    scrollInterval = undefined;
                                }
                            };

                            var getMoveMode = function(x) {
                                var distance = 0;


                                var taskMovable = taskScope.task.model.movable;
                                var rowMovable = taskScope.task.row.model.movable;

                                if (typeof(taskMovable) === 'boolean') {
                                    taskMovable = {enabled: taskMovable};
                                }

                                if (typeof(rowMovable) === 'boolean') {
                                    rowMovable = {enabled: rowMovable};
                                }

                                var allowResizing = utils.firstProperty([taskMovable, rowMovable], 'allowResizing', scope.allowResizing);
                                var allowRowSwitching = utils.firstProperty([taskMovable, rowMovable], 'allowRowSwitching', scope.allowRowSwitching);
                                var allowMoving = utils.firstProperty([taskMovable, rowMovable], 'allowMoving', scope.allowMoving);

                                // Define resize&move area. Make sure the move area does not get too small.
                                if (allowResizing) {
                                    distance = foregroundElement[0].offsetWidth < taskWithSmallWidth ? resizeAreaWidthSmall : resizeAreaWidthBig;
                                }

                                if (allowResizing && x > foregroundElement[0].offsetWidth - distance) {
                                    return 'E';
                                } else if (allowResizing && x < distance) {
                                    return 'W';
                                } else if ((allowMoving || allowRowSwitching) && x >= distance && x <= foregroundElement[0].offsetWidth - distance) {
                                    return 'M';
                                } else {
                                    return '';
                                }
                            };

                            var getCursor = function(mode) {
                                switch (mode) {
                                    case 'E':
                                        return 'e-resize';
                                    case 'W':
                                        return 'w-resize';
                                    case 'M':
                                        return 'move';
                                }
                            };

                            var setGlobalCursor = function(cursor) {
                                taskElement.css('cursor', cursor);
                                angular.element($document[0].body).css({
                                 '-moz-user-select': cursor === '' ? '': '-moz-none',
                                 '-webkit-user-select': cursor === '' ? '': 'none',
                                 '-ms-user-select': cursor === '' ? '': 'none',
                                 'user-select': cursor === '' ? '': 'none',
                                 'cursor': cursor
                                 });
                            };

                            var enableMoveMode = function(mode, x) {
                                // Clone taskModel
                                if (taskScope.task.originalModel === undefined) {
                                    taskScope.task.originalRow = taskScope.task.row;
                                    taskScope.task.originalModel = taskScope.task.model;
                                    taskScope.task.model = angular.copy(taskScope.task.originalModel);
                                }

                                // Init mouse start variables
                                if (!taskHasBeenMovedFromAnotherRow) {
                                    moveStartX = x;
                                    mouseStartOffsetX = x - taskScope.task.modelLeft;
                                }

                                // Init task move
                                taskHasBeenChanged = false;
                                taskScope.task.moveMode = mode;
                                taskScope.task.isMoving = true;
                                taskScope.task.active = true;

                                // Add move event handler
                                var taskMoveHandler = function(evt) {
                                    evt.stopImmediatePropagation();
                                    if (_hasTouch) {
                                        evt = mouseOffset.getTouch(evt);
                                    }

                                    handleMove(evt);
                                };
                                var moveSmartEvent = smartEvent(taskScope, windowElement, _moveEvents, taskMoveHandler);
                                moveSmartEvent.bind();

                                // Remove move event handler on mouse up / touch end
                                smartEvent(taskScope, windowElement, _releaseEvents, function(evt) {
                                    if (_hasTouch) {
                                        evt = mouseOffset.getTouch(evt);
                                    }
                                    moveSmartEvent.unbind();
                                    disableMoveMode(evt);
                                    taskScope.$digest();
                                }).bindOnce();

                                setGlobalCursor(getCursor(mode));
                            };

                            var disableMoveMode = function() {
                                var getBackgroundElement = taskScope.task.getBackgroundElement();
                                getBackgroundElement.removeClass('gantt-task-moving');
                                getBackgroundElement.removeClass('gantt-task-resizing');

                                if (taskScope.task.originalModel !== undefined) {

                                    taskScope.task.originalModel.from = taskScope.task.model.from;
                                    taskScope.task.originalModel.to = taskScope.task.model.to;
                                    taskScope.task.originalModel.lct = taskScope.task.model.lct;
                                    taskScope.task.originalModel.est = taskScope.task.model.est;

                                    taskScope.task.model = taskScope.task.originalModel;
                                    if (taskScope.task.row.model.id !== taskScope.task.originalRow.model.id) {
                                        var targetRow = taskScope.task.row;
                                        targetRow.removeTask(taskScope.task.model.id, false, true);
                                        taskScope.task.row = taskScope.task.originalRow;
                                        targetRow.moveTaskToRow(taskScope.task, false);
                                    }
                                    delete taskScope.task.originalModel;
                                    delete taskScope.task.originalRow;

                                    taskScope.$apply();
                                }

                                taskHasBeenMovedFromAnotherRow = false;
                                taskScope.task.isMoving = false;
                                taskScope.task.active = false;

                                // Stop any active auto scroll
                                clearScrollInterval();

                                // Set mouse cursor back to default
                                setGlobalCursor('');

                                // Raise task changed event
                                if (taskHasBeenChanged === true) {
                                    // Raise move end event
                                    if (taskScope.task.moveMode === 'M') {
                                        taskScope.row.rowsManager.gantt.api.tasks.raise.moveEnd(taskScope.task);
                                    } else {
                                        taskScope.row.rowsManager.gantt.api.tasks.raise.resizeEnd(taskScope.task);
                                    }

                                    taskHasBeenChanged = false;
                                    taskScope.task.row.sortTasks(); // Sort tasks so they have the right z-order
                                    taskScope.row.rowsManager.gantt.api.tasks.raise.change(taskScope.task);
                                }

                                taskScope.task.moveMode = undefined;
                            };

                            // Stop scroll cycle (if running) when scope is destroyed.
                            // This is needed when the task is moved to a new row during scroll because
                            // the old scope will continue to scroll otherwise
                            taskScope.$on('$destroy', function() {
                                taskScope.destroyed = true;
                                clearScrollInterval();
                            });

                            if (taskScope.task.isResizing) {
                                taskHasBeenMovedFromAnotherRow = true;
                                enableMoveMode('E', taskScope.task.mouseOffsetX);
                                delete taskScope.task.isResizing;
                            } else if (taskScope.task.isMoving) {
                                // In case the task has been moved to another row a new controller is is created by angular.
                                // Enable the move mode again if this was the case.
                                taskHasBeenMovedFromAnotherRow = true;
                                enableMoveMode('M', taskScope.task.mouseOffsetX);
                            }
                        }
                    });
                }
            };
        }]);
}());


(function() {
    'use strict';
    angular.module('gantt.overlap', ['gantt', 'gantt.overlap.templates']).directive('ganttOverlap', ['moment', '$timeout', function(moment, $timeout) {
        return {
            restrict: 'E',
            require: '^gantt',
            scope: {
                enabled: '=?',
                global: '=?'
            },
            link: function(scope, element, attrs, ganttCtrl) {
                var api = ganttCtrl.gantt.api;

                if (scope.enabled === undefined) {
                    scope.enabled = true;
                }

                if (scope.global === undefined) {
                    scope.global = false;
                }

                function getStartEnd(task) {
                    var start, end;

                    if (task.model.from.isBefore(task.model.to)) {
                        start = task.model.from;
                        end = task.model.to;
                    } else {
                        start = task.model.to;
                        end = task.model.from;
                    }

                    return [start, end];
                }

                function getRange(task) {
                    var startEnd = getStartEnd(task);
                    return moment().range(startEnd[0], startEnd[1]);
                }

                function handleTaskOverlap(overlapsList, task) {
                    if (!(task.model.id in overlapsList) && task.$element) {
                        task.$element.addClass('gantt-task-overlaps');
                        overlapsList[task.model.id] = task;
                    }
                }

                function handleTaskNonOverlaps(overlapsList, allTasks) {
                    for (var i = 0, l = allTasks.length; i < l; i++) {
                        var task = allTasks[i];
                        if (!(task.model.id in overlapsList) && task.$element) {
                            task.$element.removeClass('gantt-task-overlaps');
                        }
                    }
                }

                function handleOverlaps(tasks) {
                    // Assume that tasks are ordered with from date.
                    var newOverlapsTasks = {};

                    if (tasks.length > 1) {
                        var previousTask = tasks[0];
                        var previousRange = getRange(previousTask);

                        for (var i = 1, l = tasks.length; i < l; i++) {
                            var task = tasks[i];
                            var range = getRange(task);

                            if (range.overlaps(previousRange)) {
                                handleTaskOverlap(newOverlapsTasks, task);
                                handleTaskOverlap(newOverlapsTasks, previousTask);
                            }

                            if (previousTask.left + previousTask.width < task.left + task.width) {
                                previousTask = task;
                                previousRange = range;
                            }
                        }
                    }

                    handleTaskNonOverlaps(newOverlapsTasks, tasks);
                }

                function sortOn(array, supplier) {
                    return array.sort(function(a, b) {
                        if (supplier(a) < supplier(b)) {
                            return -1;
                        } else if (supplier(a) > supplier(b)) {
                            return 1;
                        }
                        return 0;
                    });
                }

                function handleGlobalOverlaps(rows) {
                    var globalTasks = [];
                    for (var i = 0; i < rows.length; i++) {
                        globalTasks.push.apply(globalTasks, rows[i].tasks);
                    }

                    globalTasks = sortOn(globalTasks, function(task) {
                        return task.model.from;
                    });

                    handleOverlaps(globalTasks);
                }

                if (scope.enabled) {
                    api.data.on.change(scope, function() {
                        $timeout(function() {
                            var rows = api.gantt.rowsManager.rows;

                            if (scope.global) {
                                handleGlobalOverlaps(rows);
                            } else {
                                for (var i = 0; i < rows.length; i++) {
                                    handleOverlaps(rows[i].tasks);
                                }
                            }
                        });
                    });

                    api.tasks.on.change(scope, function(task) {
                        if (scope.global) {
                            var rows = task.row.rowsManager.rows;
                            handleGlobalOverlaps(rows);
                        } else {
                            handleOverlaps(task.row.tasks);
                        }
                    });

                    api.tasks.on.rowChange(scope, function(task, oldRow) {
                        if (scope.global) {
                            var rows = oldRow.rowsManager.rows;
                            handleGlobalOverlaps(rows);
                        } else {
                            handleOverlaps(oldRow.tasks);
                        }
                    });

                    api.tasks.on.add(scope, function(task) {
                        // TODO: Mimicked functionality from api.data.on.change to defer until element creation, but not ideal.  Refactor necessary to raise 'add' event after task is fully drawn.
                        $timeout(function() {
                            if (scope.global) {
                                var rows = task.row.rowsManager.rows;
                                handleGlobalOverlaps(rows);
                            } else {
                                handleOverlaps(task.row.tasks);
                            }
                        });
                    });
                }
            }
        };
    }]);
}());

(function(){
    'use strict';
    angular.module('gantt.progress', ['gantt', 'gantt.progress.templates']).directive('ganttProgress', ['moment', '$compile', '$document', function(moment, $compile, $document) {
        return {
            restrict: 'E',
            require: '^gantt',
            scope: {
                enabled: '=?'
            },
            link: function(scope, element, attrs, ganttCtrl) {
                var api = ganttCtrl.gantt.api;

                // Load options from global options attribute.
                if (scope.options && typeof(scope.options.progress) === 'object') {
                    for (var option in scope.options.progress) {
                        scope[option] = scope.options[option];
                    }
                }

                if (scope.enabled === undefined) {
                    scope.enabled = true;
                }

                api.directives.on.new(scope, function(directiveName, taskScope, taskElement) {
                    if (directiveName === 'ganttTaskBackground') {
                        var progressScope = taskScope.$new();
                        progressScope.pluginScope = scope;

                        var ifElement = $document[0].createElement('div');
                        angular.element(ifElement).attr('data-ng-if', 'task.model.progress !== undefined && pluginScope.enabled');

                        var progressElement = $document[0].createElement('gantt-task-progress');
                        if (attrs.templateUrl !== undefined) {
                            angular.element(progressElement).attr('data-template-url', attrs.templateUrl);
                        }
                        if (attrs.template !== undefined) {
                            angular.element(progressElement).attr('data-template', attrs.template);
                        }
                        angular.element(ifElement).append(progressElement);
                        taskElement.append($compile(ifElement)(progressScope));
                    }
                });

                api.tasks.on.clean(scope, function(model) {
                    if (model.est !== undefined && !moment.isMoment(model.est)) {
                        model.est = moment(model.est); //Earliest Start Time
                    }

                    if (model.lct !== undefined && !moment.isMoment(model.lct)) {
                        model.lct = moment(model.lct); //Latest Completion Time
                    }
                });
            }
        };
    }]);
}());


(function(){
    /* global ResizeSensor: false */
    /* global ElementQueries: false */
    'use strict';
    angular.module('gantt.resizeSensor', ['gantt']).directive('ganttResizeSensor', [function() {
        return {
            restrict: 'E',
            require: '^gantt',
            scope: {
                enabled: '=?'
            },
            link: function(scope, element, attrs, ganttCtrl) {
                var api = ganttCtrl.gantt.api;

                // Load options from global options attribute.
                if (scope.options && typeof(scope.options.progress) === 'object') {
                    for (var option in scope.options.progress) {
                        scope[option] = scope.options[option];
                    }
                }

                if (scope.enabled === undefined) {
                    scope.enabled = true;
                }

                function buildSensor() {
                    var ganttElement = element.parent().parent().parent()[0].querySelectorAll('div.gantt')[0];
                    return new ResizeSensor(ganttElement, function() {
                        ganttCtrl.gantt.$scope.ganttElementWidth = ganttElement.clientWidth;
                        ganttCtrl.gantt.$scope.$apply();
                    });
                }

                var rendered = false;
                var sensor;

                api.core.on.rendered(scope, function() {
                    rendered = true;
                    if (sensor !== undefined) {
                        sensor.detach();
                    }
                    if (scope.enabled) {
                        ElementQueries.update();
                        sensor = buildSensor();
                    }
                });

                scope.$watch('enabled', function(newValue) {
                    if (rendered) {
                        if (newValue && sensor === undefined) {
                            ElementQueries.update();
                            sensor = buildSensor();
                        } else if (!newValue && sensor !== undefined) {
                            sensor.detach();
                            sensor = undefined;
                        }
                    }
                });
            }
        };
    }]);
}());


(function(){
    'use strict';

    var moduleName = 'gantt.sortable';
    var directiveName = 'ganttSortable';
    var pluginDependencies = [
        'gantt',
        {module:'ang-drag-drop', url:'https://github.com/ganarajpr/angular-dragdrop.git#master'}
    ];

    var failedDependencies = [];
    var loadedDependencies = [];
    var failedDependency;

    for (var i = 0, l = pluginDependencies.length; i < l; i++) {
        var currentDependency = pluginDependencies[i];
        try {
            if (angular.isString(currentDependency)) {
                currentDependency = {module: currentDependency};
                pluginDependencies[i] = currentDependency;
            }
            angular.module(currentDependency.module);
            loadedDependencies.push(currentDependency.module);
        } catch (e) {
            currentDependency.exception = e;
            failedDependencies.push(currentDependency);
        }
    }

    if (failedDependencies.length > 0) {
        angular.module(moduleName, []).directive(directiveName, ['$log', function($log) {
            return {
                restrict: 'E',
                require: '^gantt',
                scope: {
                    enabled: '=?'
                },
                link: function() {
                    $log.warn(moduleName + ' module can\'t require some dependencies:');
                    for (var i= 0,l =failedDependencies.length; i<l; i++) {
                        failedDependency = failedDependencies[i];

                        var errorMessage = failedDependency.module;
                        if (failedDependency.url) {
                            errorMessage += ' (' + failedDependency.url + ')';
                        }
                        if (failedDependency.exception && failedDependency.exception.message) {
                            errorMessage += ': ' + failedDependency.exception.message;
                        }

                        $log.warn(errorMessage);
                    }
                    $log.warn(directiveName + ' plugin directive won\'t be available');
                }
            };
        }]);
    } else {
        angular.module(moduleName, loadedDependencies).directive(directiveName, ['ganttUtils', '$compile', function(utils, $compile) {
            // Provides the row sort functionality to any Gantt row
            // Uses the sortableState to share the current row

            return {
                restrict: 'E',
                require: '^gantt',
                scope: {
                    enabled: '=?'
                },
                link: function(scope, element, attrs, ganttCtrl) {
                    var api = ganttCtrl.gantt.api;

                    // Load options from global options attribute.
                    if (scope.options && typeof(scope.options.sortable) === 'object') {
                        for (var option in scope.options.sortable) {
                            scope[option] = scope.options[option];
                        }
                    }

                    if (scope.enabled === undefined) {
                        scope.enabled = true;
                    }

                    api.directives.on.new(scope, function(directiveName, rowScope, rowElement) {
                        if (directiveName === 'ganttRowLabel' && rowElement.attr('drag') === undefined) {
                            rowScope.checkDraggable = function() {
                                var rowSortable = rowScope.row.model.sortable;

                                if (typeof(rowSortable) === 'boolean') {
                                    rowSortable = {enabled: rowSortable};
                                }

                                return utils.firstProperty([rowSortable], 'enabled', scope.enabled);
                            };

                            rowScope.onDropSuccess = function() {
                                rowScope.$evalAsync();
                            };

                            rowScope.onDrop = function(evt, data) {
                                var row = rowScope.row.rowsManager.rowsMap[data.id];
                                if (row !== rowScope) {
                                    rowScope.row.rowsManager.moveRow(row, rowScope.row);
                                    rowScope.$evalAsync();
                                }
                            };

                            rowElement.attr('ui-draggable', '{{checkDraggable()}}');
                            rowElement.attr('drag-channel', '\'sortable\'');
                            rowElement.attr('ui-on-drop', 'onDrop($event, $data)');
                            rowElement.attr('on-drop-success', 'onDropSuccess()');

                            rowElement.attr('drop-channel', '\'sortable\'');
                            rowElement.attr('drag', 'row.model');

                            $compile(rowElement)(rowScope);
                        }
                    });

                }
            };
        }]);
    }

}());


(function(){
    'use strict';
    angular.module('gantt.table', ['gantt', 'gantt.table.templates']).directive('ganttTable', ['ganttUtils', '$compile', '$document', function(utils, $compile, $document) {
        // Provides the row sort functionality to any Gantt row
        // Uses the sortableState to share the current row

        return {
            restrict: 'E',
            require: '^gantt',
            scope: {
                enabled: '=?',
                columns: '=?',
                headers: '=?',
                classes: '=?',
                contents: '=?',
                headerContents: '=?',
                formatters: '=?',
                headerFormatter: '=?'
            },
            link: function(scope, element, attrs, ganttCtrl) {
                var api = ganttCtrl.gantt.api;

                // Load options from global options attribute.
                if (scope.options && typeof(scope.options.sortable) === 'object') {
                    for (var option in scope.options.sortable) {
                        scope[option] = scope.options[option];
                    }
                }

                if (scope.enabled === undefined) {
                    scope.enabled = true;
                }

                if (scope.columns === undefined) {
                    scope.columns = ['model.name'];
                }

                if (scope.headers === undefined) {
                    scope.headers = {'model.name': 'Name'};
                }

                if (scope.contents === undefined) {
                    scope.contents = {};
                }

                if (scope.headerContents === undefined) {
                    scope.headerContents = {};
                }

                if (scope.classes === undefined) {
                    scope.classes = {};
                }

                if (scope.formatters === undefined) {
                    scope.formatters = {};
                }

                api.directives.on.new(scope, function(directiveName, sideContentScope, sideContentElement) {
                    if (directiveName === 'ganttSideContent') {
                        var tableScope = sideContentScope.$new();
                        tableScope.pluginScope = scope;

                        var ifElement = $document[0].createElement('div');
                        angular.element(ifElement).attr('data-ng-if', 'pluginScope.enabled');
                        angular.element(ifElement).addClass('side-element');

                        var tableElement = $document[0].createElement('gantt-side-content-table');
                        angular.element(ifElement).append(tableElement);

                        sideContentElement.append($compile(ifElement)(tableScope));
                    }
                });

            }
        };
    }]);
}());


(function(){
    'use strict';
    angular.module('gantt.tooltips', ['gantt', 'gantt.tooltips.templates']).directive('ganttTooltips', ['$compile', '$document', function($compile, $document) {
        return {
            restrict: 'E',
            require: '^gantt',
            scope: {
                enabled: '=?',
                dateFormat: '=?',
                content: '=?',
                delay: '=?'
            },
            link: function(scope, element, attrs, ganttCtrl) {
                var api = ganttCtrl.gantt.api;

                // Load options from global options attribute.
                if (scope.options && typeof(scope.options.tooltips) === 'object') {
                    for (var option in scope.options.tooltips) {
                        scope[option] = scope.options[option];
                    }
                }

                if (scope.enabled === undefined) {
                    scope.enabled = true;
                }
                if (scope.dateFormat === undefined) {
                    scope.dateFormat = 'MMM DD, HH:mm';
                }
                if (scope.delay === undefined) {
                    scope.delay = 500;
                }
                if (scope.content === undefined) {
                    scope.content = '{{task.model.name}}</br>'+
                                    '<small>'+
                                    '{{task.isMilestone() === true && getFromLabel() || getFromLabel() + \' - \' + getToLabel()}}'+
                                    '</small>';
                }

                scope.api = api;

                api.directives.on.new(scope, function(directiveName, taskScope, taskElement) {
                    if (directiveName === 'ganttTask') {
                        var tooltipScope = taskScope.$new();

                        tooltipScope.pluginScope = scope;
                        var ifElement = $document[0].createElement('div');
                        angular.element(ifElement).attr('data-ng-if', 'pluginScope.enabled');

                        var tooltipElement = $document[0].createElement('gantt-tooltip');
                        if (attrs.templateUrl !== undefined) {
                            angular.element(tooltipElement).attr('data-template-url', attrs.templateUrl);
                        }
                        if (attrs.template !== undefined) {
                            angular.element(tooltipElement).attr('data-template', attrs.template);
                        }

                        angular.element(ifElement).append(tooltipElement);
                        taskElement.append($compile(ifElement)(tooltipScope));
                    }
                });
            }
        };
    }]);
}());


(function(){
    'use strict';
    angular.module('gantt.tree', ['gantt', 'gantt.tree.templates', 'ui.tree']).directive('ganttTree', ['ganttUtils', '$compile', '$document', function(utils, $compile, $document) {
        // Provides the row sort functionality to any Gantt row
        // Uses the sortableState to share the current row

        return {
            restrict: 'E',
            require: '^gantt',
            scope: {
                enabled: '=?',
                header: '=?',
                content: '=?',
                headerContent: '=?',
                keepAncestorOnFilterRow: '=?'
            },
            link: function(scope, element, attrs, ganttCtrl) {
                var api = ganttCtrl.gantt.api;

                // Load options from global options attribute.
                if (scope.options && typeof(scope.options.sortable) === 'object') {
                    for (var option in scope.options.sortable) {
                        scope[option] = scope.options[option];
                    }
                }

                if (scope.enabled === undefined) {
                    scope.enabled = true;
                }

                if (scope.header === undefined) {
                    scope.header = 'Name';
                }

                if (scope.headerContent === undefined) {
                    scope.headerContent = '{{getHeader()}}';
                }

                if (scope.keepAncestorOnFilterRow === undefined) {
                    scope.keepAncestorOnFilterRow = false;
                }

                api.directives.on.new(scope, function(directiveName, sideContentScope, sideContentElement) {
                    if (directiveName === 'ganttSideContent') {
                        var labelsScope = sideContentScope.$new();
                        labelsScope.pluginScope = scope;

                        var ifElement = $document[0].createElement('div');
                        angular.element(ifElement).attr('data-ng-if', 'pluginScope.enabled');
                        angular.element(ifElement).addClass('side-element');

                        var labelsElement = $document[0].createElement('gantt-side-content-tree');
                        angular.element(ifElement).append(labelsElement);

                        sideContentElement.append($compile(ifElement)(labelsScope));
                    }
                });
            }
        };
    }]);
}());


(function(){
    'use strict';
    angular.module('gantt.bounds').directive('ganttTaskBounds', ['$templateCache', 'moment', function($templateCache, moment) {
        // Displays a box representing the earliest allowable start time and latest completion time for a job

        return {
            restrict: 'E',
            templateUrl: function(tElement, tAttrs) {
                var templateUrl;
                if (tAttrs.templateUrl === undefined) {
                    templateUrl = 'plugins/bounds/taskBounds.tmpl.html';
                } else {
                    templateUrl = tAttrs.templateUrl;
                }
                if (tAttrs.template) {
                    $templateCache.put(templateUrl, tAttrs.template);
                }
                return templateUrl;
            },
            replace: true,
            scope: true,
            controller: ['$scope', '$element', function($scope, $element) {
                $element.toggleClass('ng-hide', true);

                $scope.simplifyMoment = function(d) {
                    return moment.isMoment(d) ? d.unix() : d;
                };

                $scope.$watchGroup(['simplifyMoment(task.model.est)', 'simplifyMoment(task.model.lct)', 'task.left', 'task.width'], function() {
                    var left = $scope.task.rowsManager.gantt.getPositionByDate($scope.task.model.est);
                    var right = $scope.task.rowsManager.gantt.getPositionByDate($scope.task.model.lct);

                    $element.css('left', left - $scope.task.left + 'px');
                    $element.css('width', right - left + 'px');

                    $element.toggleClass('gantt-task-bounds-in', false);
                    $element.toggleClass('gantt-task-bounds-out', false);
                    if ($scope.task.model.est === undefined || $scope.task.model.lct === undefined) {
                        $element.toggleClass('gantt-task-bounds-in', true);
                    } else if ($scope.task.model.est > $scope.task.model.from) {
                        $element.toggleClass('gantt-task-bounds-out', true);
                    }
                    else if ($scope.task.model.lct < $scope.task.model.to) {
                        $element.toggleClass('gantt-task-bounds-out', true);
                    } else {
                        $element.toggleClass('gantt-task-bounds-in', true);
                    }
                });

                $scope.task.$element.bind('mouseenter', function() {
                    $element.toggleClass('ng-hide', false);
                });

                $scope.task.$element.bind('mouseleave', function() {
                    $element.toggleClass('ng-hide', true);
                });

                $scope.task.rowsManager.gantt.api.directives.raise.new('ganttBounds', $scope, $element);
                $scope.$on('$destroy', function() {
                    $scope.task.rowsManager.gantt.api.directives.raise.destroy('ganttBounds', $scope, $element);
                });
            }]
        };
    }]);
}());


(function() {
    'use strict';

    angular.module('gantt.dependencies').factory('GanttDependenciesChecker', [function() {
        /**
         * Creates a new DependenciesChecker object.
         *
         * @constructor
         */
        var GanttDependenciesChecker = function(manager) {
            function handleTaskConflict(conflictsList, task) {
                if (!(task.model.id in conflictsList) && task.$element) {
                    task.$element.addClass('gantt-task-conflict');
                    conflictsList[task.model.id] = task;
                }
            }

            function handleTaskNonConflict(conflictsList, allTasks) {
                for (var i = 0, l = allTasks.length; i < l; i++) {
                    var task = allTasks[i];
                    if (!(task.model.id in conflictsList) && task.$element) {
                        task.$element.removeClass('gantt-task-conflict');
                    }
                }
            }

            /**
             * Refresh the conflict status of given tasks.
             *
             * @param tasks
             */
            this.refresh = function(tasks) {
                var allTasks = tasks.slice(0);
                var conflictsList = [];

                for (var i = 0; i < tasks.length; i++) {
                    var taskDependencies = manager.getTaskDependencies(tasks[i]);

                    for (var j = 0; j < taskDependencies.length; j++) {
                        var dependency = taskDependencies[j];

                        var fromTask = dependency.getFromTask();
                        var toTask = dependency.getToTask();

                        if (!(fromTask in allTasks)) {
                            allTasks.push(fromTask);
                        }

                        if (!(toTask in allTasks)) {
                            allTasks.push(toTask);
                        }

                        if (fromTask.model.to > toTask.model.from) {
                            handleTaskConflict(conflictsList, fromTask);
                            handleTaskConflict(conflictsList, toTask);
                        }
                    }
                }

                handleTaskNonConflict(conflictsList, allTasks);
            };

            /**
             * Remove the conflict status of given tasks.
             *
             * @param tasks
             */
            this.clear = function(tasks) {
                var allTasks = tasks.slice(0);
                handleTaskNonConflict([], allTasks);
            };

        };
        return GanttDependenciesChecker;
    }]);
}());

(function() {
    'use strict';

    angular.module('gantt.dependencies').factory('GanttDependenciesEvents', [function() {
        /**
         * Creates a new DependenciesEvents object.
         *
         * @param manager DependenciesManager object
         * @constructor
         */
        var DependenciesEvents = function(manager) {
            var self = this;

            this.manager = manager;

            // Deny the start of a drag when in readonly
            var denyDragWhenReadOnly = function () {
                return !self.manager.pluginScope.readOnly;
            };

            this.manager.plumb.bind('beforeDrag', denyDragWhenReadOnly);
            this.manager.plumb.bind('beforeStartDetach', denyDragWhenReadOnly);

            // Deny drop on the same task.
            var denyDropOnSameTask = function(params) {
                return params.sourceId !== params.targetId;
            };

            this.manager.plumb.bind('beforeDrop', denyDropOnSameTask);


            // Notify the manager that a connection is being created.
            this.manager.plumb.bind('connectionDrag', function(connection) {
                self.manager.setDraggingConnection(connection);
            });

            this.manager.plumb.bind('connectionDragStop', function() {
                self.manager.setDraggingConnection(undefined);
            });

            this.manager.plumb.bind('beforeDrop', function() {
                self.manager.setDraggingConnection(undefined);
                return true;
            });

            var createConnection = function(info, mouseEvent) {
                if (mouseEvent) {
                    var oldDependency;
                    if (info.connection.$dependency) {
                        oldDependency = info.connection.$dependency;
                    }

                    var sourceEndpoint = info.sourceEndpoint;
                    var targetEndpoint = info.targetEndpoint;

                    var sourceModel = sourceEndpoint.$task.model;

                    var dependenciesModel = sourceModel.dependencies;
                    if (dependenciesModel === undefined) {
                        dependenciesModel = [];
                        sourceModel.dependencies = dependenciesModel;
                    }

                    var connectionModel = {to: targetEndpoint.$task.model.id};
                    dependenciesModel.push(connectionModel);

                    if (oldDependency) {
                        oldDependency.removeFromTaskModel();
                        self.manager.removeDependency(oldDependency, true); // Connection will be disconnected later by jsPlumb.
                    }

                    var dependency = self.manager.addDependency(sourceEndpoint.$task, connectionModel);
                    info.connection.$dependency = dependency;
                    dependency.connection = info.connection;

                    self.manager.api.dependencies.raise.add(dependency);

                }
            };

            var updateConnection = function(info, mouseEvent) {
                if (mouseEvent) {
                    var oldDependency;
                    if (info.connection.$dependency) {
                        oldDependency = info.connection.$dependency;
                    }

                    var sourceEndpoint = info.newSourceEndpoint;
                    var targetEndpoint = info.newTargetEndpoint;

                    var sourceModel = sourceEndpoint.$task.model;

                    var dependenciesModel = sourceModel.dependencies;
                    if (dependenciesModel === undefined) {
                        dependenciesModel = [];
                        sourceModel.dependencies = dependenciesModel;
                    }

                    var connectionModel = {to: targetEndpoint.$task.model.id};
                    dependenciesModel.push(connectionModel);

                    if (oldDependency) {
                        oldDependency.removeFromTaskModel();
                        self.manager.removeDependency(oldDependency, true); // Connection will be disconnected later by jsPlumb.
                    }

                    var dependency = self.manager.addDependency(sourceEndpoint.$task, connectionModel);
                    info.connection.$dependency = dependency;
                    dependency.connection = info.connection;

                    self.manager.api.dependencies.raise.change(dependency, oldDependency);
                }
            };

            var deleteConnection = function(info, mouseEvent) {
                if (mouseEvent) {
                    var dependency = info.connection.$dependency;

                    dependency.removeFromTaskModel();
                    self.manager.removeDependency(dependency, true); // Connection will be disconnected later by jsPlumb.
                    self.manager.api.dependencies.raise.remove(dependency);
                }
            };

            this.manager.plumb.bind('connectionMoved', updateConnection);
            this.manager.plumb.bind('connection', createConnection);
            this.manager.plumb.bind('connectionDetached', deleteConnection);

        };
        return DependenciesEvents;
    }]);
}());

/* globals jsPlumb */
(function() {
    'use strict';

    angular.module('gantt.dependencies').factory('GanttDependenciesManager', ['GanttDependency', 'GanttDependenciesEvents', 'GanttDependencyTaskMouseHandler', function(Dependency, DependenciesEvents, TaskMouseHandler) {
        var DependenciesManager = function(gantt, pluginScope, api) {
            var self = this;

            this.gantt = gantt;
            this.pluginScope = pluginScope;
            this.api = api;

            this.api.registerEvent('dependencies', 'add');
            this.api.registerEvent('dependencies', 'change');
            this.api.registerEvent('dependencies', 'remove');

            this.plumb = jsPlumb.getInstance();
            this.plumb.importDefaults(this.pluginScope.jsPlumbDefaults);

            this.dependenciesFrom = {};
            this.dependenciesTo = {};

            this.tasksList = [];
            this.tasks = {};

            this.events = new DependenciesEvents(this);

            this.pluginScope.$watch('enabled', function(newValue, oldValue) {
                if (newValue !== oldValue) {
                    self.refresh();
                }
            });

            this.pluginScope.$watch('readOnly', function(newValue, oldValue) {
                if (newValue !== oldValue) {
                    self.setTasks(self.tasksList);
                    self.refresh();
                }
            });

            this.pluginScope.$watch('jsPlumbDefaults', function(newValue, oldValue) {
                if (newValue !== oldValue) {
                    self.plumb.importDefaults(newValue);
                    self.refresh();
                }
            }, true);

            /**
             * Add all dependencies defined from a task. Dependencies will be added only if plugin is enabled.
             *
             * @param task
             */
            this.addDependenciesFromTask = function(task) {
                if (this.pluginScope.enabled) {
                    var taskDependencies = task.model.dependencies;

                    if (taskDependencies !== undefined && taskDependencies) {
                        if (!angular.isArray(taskDependencies)) {
                            taskDependencies = [taskDependencies];
                            task.model.dependencies = taskDependencies;
                        }

                        for (var i = 0, l = taskDependencies.length; i < l; i++) {
                            var dependency = self.addDependency(task, taskDependencies[i]);
                            dependency.connect();
                        }
                    }
                }
            };

            /**
             * Remove all dependencies defined for a task.
             *
             * @param task
             * @param keepConnection if true, dependency will not be disconnected.
             */
            this.removeDependenciesFromTask = function(task, keepConnection) {
                var dependencies = this.getTaskDependencies(task);

                if (dependencies) {
                    for (var i = 0; i < dependencies.length; i++) {
                        if (!keepConnection) {
                            dependencies[i].disconnect();
                        }
                        self.removeDependency(dependencies[i]);
                    }
                }
            };

            /**
             * Add definition of a dependency.
             *
             * @param task Task defining the dependency.
             * @param model Model object for the dependency.
             */
            this.addDependency = function(task, model) {
                var dependency = new Dependency(this, task, model);

                var fromTaskId = dependency.getFromTaskId();
                var toTaskId = dependency.getToTaskId();

                if (!(fromTaskId in this.dependenciesFrom)) {
                    this.dependenciesFrom[fromTaskId] = [];
                }
                if (!(toTaskId in this.dependenciesTo)) {
                    this.dependenciesTo[toTaskId] = [];
                }

                if (fromTaskId) {
                    this.dependenciesFrom[fromTaskId].push(dependency);
                }

                if (toTaskId) {
                    this.dependenciesTo[toTaskId].push(dependency);
                }

                return dependency;
            };

            /**
             * Remove definition of a dependency
             *
             * @param dependency Dependency object
             * @param keepConnection if true, dependency will not be disconnected.
             */
            this.removeDependency = function(dependency, keepConnection) {
                var fromDependencies = this.dependenciesFrom[dependency.getFromTaskId()];
                var fromRemove = [];
                var i;

                if (fromDependencies) {
                    for (i = 0; i < fromDependencies.length; i++) {
                        if (dependency === fromDependencies[i]) {
                            fromRemove.push(dependency);
                        }
                    }
                }

                var toDependencies = this.dependenciesTo[dependency.getToTaskId()];
                var toRemove = [];

                if (toDependencies) {
                    for (i = 0; i < toDependencies.length; i++) {
                        if (dependency === toDependencies[i]) {
                            toRemove.push(dependency);
                        }
                    }
                }

                for (i = 0; i < fromRemove.length; i++) {
                    if (!keepConnection) {
                        fromRemove[i].disconnect();
                    }
                    fromDependencies.splice(fromDependencies.indexOf(dependency), 1);
                }

                for (i = 0; i < toRemove.length; i++) {
                    if (!keepConnection) {
                        toRemove[i].disconnect();
                    }
                    toDependencies.splice(toDependencies.indexOf(dependency), 1);
                }

                if (this.dependenciesFrom[dependency.getFromTaskId()] &&
                    this.dependenciesFrom[dependency.getFromTaskId()].length === 0) {
                    delete this.dependenciesFrom[dependency.getFromTaskId()];
                }

                if (this.dependenciesTo[dependency.getToTaskId()] &&
                    this.dependenciesTo[dependency.getToTaskId()].length === 0) {
                    delete this.dependenciesTo[dependency.getToTaskId()];
                }
            };

            this.getTaskDependencies = function(task) {
                var dependencies = [];

                var fromDependencies = self.dependenciesFrom[task.model.id];
                if (fromDependencies) {
                    dependencies = dependencies.concat(fromDependencies);
                }

                var toDependencies = self.dependenciesTo[task.model.id];
                if (toDependencies) {
                    dependencies = dependencies.concat(toDependencies);
                }

                return dependencies;
            };

            this.setDraggingConnection = function(connection) {
                if (connection) {
                    self.draggingConnection = connection;
                    angular.forEach(self.tasks, function(task) {
                        task.dependencies.mouseHandler.release();
                    });
                } else {
                    self.draggingConnection = undefined;
                    angular.forEach(self.tasks, function(task) {
                        task.dependencies.mouseHandler.install();
                    });
                }
            };

            var isTaskEnabled = function(task) {
                var rowDependencies = task.row.model.dependencies;
                if (rowDependencies !== undefined) {
                    return rowDependencies !== false;
                }
                var taskDependencies = task.model.dependencies;
                if (taskDependencies !== undefined) {
                    return taskDependencies !== false;
                }
                return true;
            };

            var addTaskEndpoints = function(task) {
                if (!task.dependencies) {
                    task.dependencies = {};
                }

                task.dependencies.endpoints = [];

                if (self.pluginScope.endpoints && task.$element) {
                    for (var i = 0; i < self.pluginScope.endpoints.length; i++) {
                        var endpointObject = self.plumb.addEndpoint(task.$element, self.pluginScope.endpoints[i]);
                        endpointObject.setVisible(false, true, true); // hide endpoint
                        endpointObject.$task = task;
                        task.dependencies.endpoints.push(endpointObject);
                    }
                }

            };

            var removeTaskEndpoint = function(task) {
                if (task.dependencies.endpoints) {
                    for (var i = 0; i < task.dependencies.endpoints.length; i++) {
                        var endpointObject = task.dependencies.endpoints[i];
                        self.plumb.deleteEndpoint(endpointObject);
                        endpointObject.$task = undefined;
                    }

                    task.dependencies.endpoints = undefined;
                }
            };

            var addTaskMouseHandler = function(task) {
                if (!task.dependencies) {
                    task.dependencies = {};
                }

                if (!self.pluginScope.readOnly) {
                    task.dependencies.mouseHandler = new TaskMouseHandler(self, task);
                    task.dependencies.mouseHandler.install();
                }
            };

            var removeTaskMouseHandler = function(task) {
                if (task.dependencies.mouseHandler) {
                    task.dependencies.mouseHandler.release();
                    task.dependencies.mouseHandler = undefined;
                }
            };

            /**
             * Set tasks objects that can be used to display dependencies.
             *
             * @param tasks
             */
            this.setTasks = function(tasks) {
                angular.forEach(self.tasks, function(task) {
                    removeTaskMouseHandler(task);
                    removeTaskEndpoint(task);
                });

                var newTasks = {};
                var tasksList = [];
                for (var i = 0; i < tasks.length; i++) {
                    var task = tasks[i];
                    if (isTaskEnabled(task)) {
                        newTasks[task.model.id] = task;
                        tasksList.push(task);
                        addTaskEndpoints(task);
                        addTaskMouseHandler(task);
                    }
                }
                self.tasks = newTasks;
                self.tasksList = tasks;
            };

            var disconnectTaskDependencies = function(task) {
                var dependencies = self.getTaskDependencies(task);
                if (dependencies) {
                    for (var i = 0; i < dependencies.length; i++) {
                        dependencies[i].disconnect();
                    }
                }
                return dependencies;
            };

            var connectTaskDependencies = function(task) {
                var dependencies = self.getTaskDependencies(task);
                if (dependencies) {
                    for (var i = 0; i < dependencies.length; i++) {
                        dependencies[i].connect();
                    }
                }
                return dependencies;
            };

            /**
             * Set task object in replacement of an existing with the same id.
             *
             * @param task
             */
            this.setTask = function(task) {
                self.plumb.setSuspendDrawing(true);
                try {
                    var oldTask = self.tasks[task.model.id];
                    if (oldTask !== undefined) {
                        disconnectTaskDependencies(oldTask);
                        removeTaskMouseHandler(oldTask);
                        removeTaskEndpoint(oldTask);
                    }
                    if (isTaskEnabled(task)) {
                        self.tasks[task.model.id] = task;
                        addTaskEndpoints(task);
                        addTaskMouseHandler(task);
                        connectTaskDependencies(task);
                    }
                } finally {
                    self.plumb.setSuspendDrawing(false, true);
                }
            };

            /**
             * Retrieve the task from it's id.
             *
             * @param taskId id of the task element to retrieve.
             * @returns {*}
             */
            this.getTask = function(taskId) {
                return self.tasks[taskId];
            };

            var getSourceEndpoints = function(task) {
                return task.dependencies.endpoints.filter(function(endpoint) {
                    return endpoint.isSource;
                });
            };

            var getTargetEndpoints = function(task) {
                return task.dependencies.endpoints.filter(function(endpoint) {
                    return endpoint.isTarget;
                });
            };

            /**
             * Connects two tasks together using source endpoint from fromTask and target endpoint from toTask.
             *
             * @param fromTask
             * @param toTask
             * @param model
             * @returns connection object
             */
            this.connect = function(fromTask, toTask, model) {
                var sourceEndpoints = getSourceEndpoints(fromTask);
                var targetEndpoints = getTargetEndpoints(toTask);
                if (sourceEndpoints && targetEndpoints) {
                    var sourceEndpoint;
                    var targetEndpoint;

                    if (model.connectParameters && model.connectParameters.sourceEndpointIndex) {
                        sourceEndpoint = sourceEndpoints[model.connectParameters.sourceEndpointIndex];
                    } else {
                        sourceEndpoint = sourceEndpoints[0];
                    }

                    if (model.connectParameters && model.connectParameters.targetEndpointIndex) {
                        targetEndpoint = targetEndpoints[model.connectParameters.targetEndpointIndex];
                    } else {
                        targetEndpoint = targetEndpoints[0];
                    }

                    var connection = self.plumb.connect({
                        source: sourceEndpoint,
                        target: targetEndpoint
                    }, model.connectParameters);
                    return connection;
                }
            };

            /**
             * Get all defined dependencies.
             *
             * @returns {Array}
             */
            this.getDependencies = function() {
                var allDependencies = [];

                angular.forEach(this.dependenciesFrom, function(dependencies) {
                    for (var i = 0; i < dependencies.length; i++) {
                        if (!(dependencies[i] in allDependencies)) {
                            allDependencies.push(dependencies[i]);
                        }
                    }
                });

                return allDependencies;
            };

            /**
             * Refresh jsplumb status based on tasks dependencies models.
             */
            this.refresh = function(tasks) {
                self.plumb.setSuspendDrawing(true);

                try {
                    var tasksDependencies;
                    var i;
                    if (tasks && !angular.isArray(tasks)) {
                        tasks = [tasks];
                    }

                    if (tasks === undefined) {
                        tasks = this.tasks;
                        tasksDependencies = this.getDependencies();
                    } else {
                        tasksDependencies = [];
                        angular.forEach(tasks, function(task) {
                            var taskDependencies = self.getTaskDependencies(task);
                            angular.forEach(taskDependencies, function(taskDependency) {
                                if (!(taskDependency in tasksDependencies)) {
                                    tasksDependencies.push(taskDependency);
                                }
                            });
                        });
                    }

                    for (i = 0; i < tasksDependencies.length; i++) {
                        self.removeDependency(tasksDependencies[i]);
                    }

                    angular.forEach(tasks, function(task) {
                        self.addDependenciesFromTask(task);
                    });
                } finally {
                    self.plumb.setSuspendDrawing(false, true);
                }
            };

            this.api.registerMethod('dependencies', 'refresh', this.refresh, this);
        };
        return DependenciesManager;
    }]);
}());

(function() {
    'use strict';

    angular.module('gantt.dependencies').factory('GanttDependency', ['ganttUtils', 'ganttDom', function(utils, dom) {
        /**
         * Constructor of Dependency object.
         * 
         * @param manager Dependency manager used by this dependency
         * @param task Task declaring the dependency
         * @param model model of the dependency
         *
         * @constructor
         *
         * @see https://jsplumbtoolkit.com/community/apidocs/classes/jsPlumb.html#method_connect
         */
        var Dependency = function(manager, task, model) {
            var self = this;

            this.manager = manager;
            this.task = task;
            this.model = model;
            this.connection = undefined;
            this.fallbackEndpoints = [];

            /**
             * Check if this dependency is connected.
             *
             * @returns {boolean}
             */
            this.isConnected = function() {
                if (this.connection) {
                    return true;
                }
                return false;
            };

            /**
             * Disconnect this dependency.
             */
            this.disconnect = function() {
                if (this.connection) {
                    if (this.connection.endpoints) {
                        this.manager.plumb.detach(this.connection);
                    }
                    this.connection.$dependency = undefined;
                    this.connection = undefined;
                }

                this.deleteFallbackEndpoints();
            };

            this.deleteFallbackEndpoints = function() {
                if (this.fallbackEndpoints) {
                    for (var i=0; i<this.fallbackEndpoints.length; i++) {
                        self.manager.plumb.deleteEndpoint(this.fallbackEndpoints[i]);
                    }
                    this.fallbackEndpoints = [];
                }
            };

            this.getFromTaskId = function() {
                if (this.model.from !== undefined) {
                    return this.model.from;
                }
                return this.task.model.id;
            };

            this.getToTaskId = function() {
                if (this.model.to !== undefined) {
                    return this.model.to;
                }
                return this.task.model.id;
            };

            this.getFromTask = function() {
                if (this.model.from !== undefined) {
                    return this.manager.getTask(this.model.from);
                }
                return this.task;
            };

            this.getToTask = function() {
                if (this.model.to !== undefined) {
                    return this.manager.getTask(this.model.to);
                }
                return this.task;
            };

            this.removeFromTaskModel = function() {
                var modelIndex = utils.angularIndexOf(this.task.model.dependencies, this.model);
                if (modelIndex >= 0) {
                    this.task.model.dependencies.splice(modelIndex, 1);
                }
                return modelIndex;
            };

            var isTaskVisible = function(task) {
                if (task === undefined || task.$element === undefined) {
                    return false;
                }
                var element = task.$element[0];
                return dom.isElementVisible(element);
            };

            /**
             * Connect this dependency if both elements are available.
             *
             * @returns {boolean}
             */
            this.connect = function() {
                var fromTask = this.getFromTask();
                var toTask = this.getToTask();

                if (!isTaskVisible(fromTask)) {
                    fromTask = undefined;
                }

                if (!isTaskVisible(toTask)) {
                    toTask = undefined;
                }

                if (fromTask && toTask) {
                    var connection = this.manager.connect(fromTask, toTask, this.model);
                    if (connection) {
                        connection.$dependency = this;
                        this.connection = connection;
                        return true;
                    }
                }

                this.deleteFallbackEndpoints();
                if (fromTask !== undefined) {
                    var toFallbackEndpoint = this.manager.pluginScope.fallbackEndpoints[1];
                    this.fallbackEndpoints.push(this.manager.plumb.addEndpoint(fromTask.$element, toFallbackEndpoint));
                }
                if (toTask !== undefined) {
                    var fromFallbackEndpoint = this.manager.pluginScope.fallbackEndpoints[0];
                    this.fallbackEndpoints.push(this.manager.plumb.addEndpoint(toTask.$element, fromFallbackEndpoint));
                }
                return false;
            };
        };
        return Dependency;
    }]);
}());

(function() {
    'use strict';

    angular.module('gantt.dependencies').factory('GanttDependencyTaskMouseHandler', ['$timeout', function($timeout) {
        var TaskMouseHandler = function(manager, task) {
            var self = this;

            this.manager = manager;
            this.task = task;
            this.installed = false;
            this.elementHandlers = [];

            this.display = true;
            this.hideEndpointsPromise = undefined;

            /**
             * Handler for a single DOM element.
             *
             * @param element
             * @constructor
             */
            var ElementHandler = function(element) {
                this.element = element;

                this.mouseExitHandler = function() {
                    $timeout.cancel(self.hideEndpointsPromise);
                    self.hideEndpointsPromise = $timeout(self.hideEndpoints, 1000, false);
                };

                this.mouseEnterHandler = function() {
                    $timeout.cancel(self.hideEndpointsPromise);
                    self.displayEndpoints();
                };

                this.install = function() {
                    this.element.bind('mouseenter', this.mouseEnterHandler);
                    this.element.bind('mouseleave', this.mouseExitHandler);
                };

                this.release = function() {
                    this.element.unbind('mouseenter', this.mouseEnterHandler);
                    this.element.unbind('mouseleave', this.mouseExitHandler);
                    $timeout.cancel(self.hideEndpointsPromise);
                };

            };



            /**
             * Install mouse handler for this task, and hide all endpoints.
             */
            this.install = function() {
                if (!self.installed) {
                    self.hideEndpoints();

                    if (self.task.getContentElement()) {
                        self.elementHandlers.push(new ElementHandler(self.task.getContentElement()));
                        angular.forEach(self.task.dependencies.endpoints, function(endpoint) {
                            self.elementHandlers.push(new ElementHandler(angular.element(endpoint.canvas)));
                        });

                        angular.forEach(self.elementHandlers, function(elementHandler) {
                            elementHandler.install();
                        });

                        self.installed = true;
                    }
                }
            };

            /**
             * Release mouse handler for this task, and display all endpoints.
             */
            this.release = function() {
                if (self.installed) {
                    angular.forEach(self.elementHandlers, function(elementHandler) {
                        elementHandler.release();
                    });

                    self.elementHandlers = [];

                    self.displayEndpoints();
                    self.installed = false;
                }
            };

            /**
             * Display all endpoints for this task.
             */
            this.displayEndpoints = function() {
                self.display = true;
                angular.forEach(self.task.dependencies.endpoints, function(endpoint) {
                    endpoint.setVisible(true, true, true);
                });
            };

            /**
             * Hide all endpoints for this task.
             */
            this.hideEndpoints = function() {
                angular.forEach(self.task.dependencies.endpoints, function(endpoint) {
                    endpoint.setVisible(false, true, true);
                });
                self.display = false;
            };
        };
        return TaskMouseHandler;
    }]);
}());

(function(){
    'use strict';
    angular.module('gantt.groups').controller('GanttGroupController', ['$scope', 'GanttTaskGroup', 'ganttUtils', function($scope, TaskGroup, utils) {
        var updateTaskGroup = function() {
            var rowGroups = $scope.row.model.groups;

            if (typeof(rowGroups) === 'boolean') {
                rowGroups = {enabled: rowGroups};
            }

            var enabledValue = utils.firstProperty([rowGroups], 'enabled', $scope.pluginScope.enabled);
            if (enabledValue) {
                $scope.display = utils.firstProperty([rowGroups], 'display', $scope.pluginScope.display);
                $scope.taskGroup = new TaskGroup($scope.row, $scope.pluginScope);

                $scope.row.setFromTo();
                $scope.row.setFromToByValues($scope.taskGroup.from, $scope.taskGroup.to);
            } else {
                $scope.taskGroup = undefined;
                $scope.display = undefined;
            }
        };

        $scope.gantt.api.tasks.on.viewChange($scope, function(task) {
            if ($scope.taskGroup !== undefined) {
                if ($scope.taskGroup.tasks.indexOf(task) > -1) {
                    updateTaskGroup();
                    if(!$scope.$$phase && !$scope.$root.$$phase) {
                        $scope.$digest();
                    }
                } else {
                    var descendants = $scope.pluginScope.hierarchy.descendants($scope.row);
                    if (descendants.indexOf(task.row) > -1) {
                        updateTaskGroup();
                        if(!$scope.$$phase && !$scope.$root.$$phase) {
                            $scope.$digest();
                        }
                    }
                }
            }
        });

        var removeWatch = $scope.pluginScope.$watch('display', updateTaskGroup);

        $scope.$watchCollection('gantt.rowsManager.filteredRows', updateTaskGroup);

        $scope.gantt.api.columns.on.refresh($scope, updateTaskGroup);

        $scope.$on('$destroy', removeWatch);
    }]);
}());


(function(){
    'use strict';
    angular.module('gantt.groups').directive('ganttTaskGroup', ['GanttDirectiveBuilder', function(Builder) {
        var builder = new Builder('ganttTaskGroup', 'plugins/groups/taskGroup.tmpl.html');
        return builder.build();
    }]);
}());


(function() {
    'use strict';

    angular.module('gantt').factory('GanttTaskGroup', ['ganttUtils', 'GanttTask', function(utils, Task) {
        var TaskGroup = function(row, pluginScope) {
            var self = this;

            self.row = row;
            self.pluginScope = pluginScope;

            self.descendants = self.pluginScope.hierarchy.descendants(self.row);

            self.tasks = [];
            self.overviewTasks = [];
            self.promotedTasks = [];
            self.showGrouping = false;

            var groupRowGroups = self.row.model.groups;
            if (typeof(groupRowGroups) === 'boolean') {
                groupRowGroups = {enabled: groupRowGroups};
            }

            var getTaskDisplay = function(task) {
                var taskGroups = task.model.groups;
                if (typeof(taskGroups) === 'boolean') {
                    taskGroups = {enabled: taskGroups};
                }

                var rowGroups = task.row.model.groups;
                if (typeof(rowGroups) === 'boolean') {
                    rowGroups = {enabled: rowGroups};
                }

                var enabledValue = utils.firstProperty([taskGroups, rowGroups, groupRowGroups], 'enabled', self.pluginScope.enabled);

                if (enabledValue) {
                    var display = utils.firstProperty([taskGroups, rowGroups, groupRowGroups], 'display', self.pluginScope.display);
                    return display;
                }
            };

            for (var i = 0; i < self.descendants.length; i++) {
                var tasks = self.descendants[i].tasks;

                for (var j = 0; j < tasks.length; j++) {
                    var task = tasks[j];

                    var taskDisplay = getTaskDisplay(task);
                    if (taskDisplay !== undefined) {
                        self.tasks.push(task);
                        var clone = new Task(self.row, task.model);

                        if (taskDisplay === 'overview') {
                            self.overviewTasks.push(clone);
                        } else if (taskDisplay === 'promote') {
                            self.promotedTasks.push(clone);
                        } else {
                            self.showGrouping = true;
                        }
                    }
                }
            }

            self.from = undefined;
            if (groupRowGroups) {
                self.from = groupRowGroups.from;
            }
            if (self.from === undefined) {
                for (i=0; i<self.tasks.length; i++) {
                    if (self.from === undefined || self.tasks[i].model.from < self.from) {
                        self.from = self.tasks[i].model.from;
                    }
                }
            }

            self.to = undefined;
            if (groupRowGroups) {
                self.to = groupRowGroups.to;
            }
            if (self.to === undefined) {
                for (i=0; i<self.tasks.length; i++) {
                    if (self.to === undefined || self.tasks[i].model.to > self.to) {
                        self.to = self.tasks[i].model.to;
                    }
                }
            }

            if (self.showGrouping) {
                self.left = row.rowsManager.gantt.getPositionByDate(self.from);
                self.width = row.rowsManager.gantt.getPositionByDate(self.to) - self.left;
            }
        };
        return TaskGroup;
    }]);
}());

(function(){
    'use strict';
    angular.module('gantt').directive('ganttTaskOverview', ['GanttDirectiveBuilder', function(Builder) {
        var builder = new Builder('ganttTaskOverview', 'plugins/groups/taskOverview.tmpl.html');
        builder.controller = function($scope, $element) {
            $scope.task.$element = $element;
            $scope.task.$scope = $scope;
            $scope.task.updatePosAndSize();
        };
        return builder.build();
    }]);
}());

(function(){
    'use strict';
    angular.module('gantt.labels').directive('ganttLabelsBody', ['GanttDirectiveBuilder', 'ganttLayout', function(Builder, layout) {
        var builder = new Builder('ganttLabelsBody', 'plugins/labels/labelsBody.tmpl.html');
        builder.controller = function($scope) {
            var hScrollBarHeight = layout.getScrollBarHeight();

            $scope.getLabelsCss = function() {
                var css = {};

                if ($scope.maxHeight) {
                    var bodyScrollBarHeight = $scope.gantt.scroll.isHScrollbarVisible() ? hScrollBarHeight : 0;
                    css['max-height'] = $scope.maxHeight - bodyScrollBarHeight - $scope.gantt.header.getHeight() + 'px';
                }

                return css;
            };
        };
        return builder.build();
    }]);
}());



(function(){
    'use strict';
    angular.module('gantt.labels').directive('ganttLabelsHeader', ['GanttDirectiveBuilder', function(Builder) {
        var builder = new Builder('ganttLabelsHeader', 'plugins/labels/labelsHeader.tmpl.html');
        return builder.build();
    }]);
}());


(function(){
    'use strict';
    angular.module('gantt.labels').directive('ganttSideContentLabels', ['GanttDirectiveBuilder', function(Builder) {
        var builder = new Builder('ganttSideContentLabels', 'plugins/labels/sideContentLabels.tmpl.html');
        return builder.build();
    }]);
}());


(function(){
    'use strict';
    angular.module('gantt.movable').factory('ganttMovableOptions', [function() {
        return {
            initialize: function(options) {

                options.enabled = options.enabled !== undefined ? options.enabled : true;
                options.allowMoving = options.allowMoving !== undefined ? !!options.allowMoving : true;
                options.allowResizing = options.allowResizing !== undefined ? !!options.allowResizing : true;
                options.allowRowSwitching = options.allowRowSwitching !== undefined ? !!options.allowRowSwitching : true;

                return options;
            }
        };
    }]);
}());


(function(){
    'use strict';
    angular.module('gantt.progress').directive('ganttTaskProgress', ['$templateCache', function($templateCache) {
        return {
            restrict: 'E',
            requires: '^ganttTask',
            templateUrl: function(tElement, tAttrs) {
                var templateUrl;
                if (tAttrs.templateUrl === undefined) {
                    templateUrl = 'plugins/progress/taskProgress.tmpl.html';
                } else {
                    templateUrl = tAttrs.templateUrl;
                }
                if (tAttrs.template !== undefined) {
                    $templateCache.put(templateUrl, tAttrs.template);
                }
                return templateUrl;
            },
            replace: true,
            scope: true,
            controller: ['$scope', '$element', function($scope, $element) {
                $scope.getClasses = function() {
                    var classes = [];

                    if (typeof($scope.task.model.progress) === 'object') {
                        classes = $scope.task.model.progress.classes;
                    }

                    return classes;
                };

                $scope.getCss = function() {
                    var css = {};

                    var progress;
                    if ($scope.task.model.progress !== undefined) {
                        if (typeof($scope.task.model.progress) === 'object') {
                            progress = $scope.task.model.progress;
                        } else {
                            progress = {percent: $scope.task.model.progress};
                        }
                    }

                    if (progress) {
                        if (progress.color) {
                            css['background-color'] = progress.color;
                        } else {
                            css['background-color'] = '#6BC443';
                        }

                        css.width = progress.percent + '%';
                    }

                    return css;
                };

                $scope.task.rowsManager.gantt.api.directives.raise.new('ganttTaskProgress', $scope, $element);
                $scope.$on('$destroy', function() {
                    $scope.task.rowsManager.gantt.api.directives.raise.destroy('ganttTaskProgress', $scope, $element);
                });
            }]
        };
    }]);
}());


(function(){
    'use strict';
    angular.module('gantt.table').directive('ganttSideContentTable', ['GanttDirectiveBuilder', 'ganttLayout', function(Builder, layout) {
        var builder = new Builder('ganttSideContentTable', 'plugins/table/sideContentTable.tmpl.html');
        builder.controller = function($scope) {
            var hScrollBarHeight = layout.getScrollBarHeight();

            $scope.getMaxHeightCss = function() {
                var css = {};

                if ($scope.maxHeight) {
                    var bodyScrollBarHeight = $scope.gantt.scroll.isHScrollbarVisible() ? hScrollBarHeight : 0;
                    css['max-height'] = $scope.maxHeight - bodyScrollBarHeight - $scope.gantt.header.getHeight() + 'px';
                }

                return css;
            };
        };
        return builder.build();
    }]);
}());


(function() {
    'use strict';
    angular.module('gantt.table').controller('TableColumnController', ['$scope', function($scope) {
        $scope.getHeader = function() {
            var header = $scope.pluginScope.headers[$scope.column];
            if (header !== undefined) {
                return header;
            }
            if ($scope.pluginScope.headerFormatter !== undefined) {
                header = $scope.pluginScope.headerFormatter($scope.column);
            }
            if (header !== undefined) {
                return header;
            }
            return header;
        };

        $scope.getHeaderContent = function() {
            var headerContent = $scope.pluginScope.headerContents[$scope.column];
            if (headerContent === undefined) {
                return '{{getHeader()}}';
            }
            return headerContent;
        };

        $scope.getClass = function() {
            return $scope.pluginScope.classes[$scope.column];
        };
    }]);
}());


(function() {
    'use strict';
    angular.module('gantt.table').controller('TableColumnRowController', ['$scope', function($scope) {
        $scope.getValue = function() {
            var value = $scope.$eval($scope.column, $scope.row);

            var formatter = $scope.pluginScope.formatters[$scope.column];
            if (formatter !== undefined) {
                value = formatter(value, $scope.column, $scope.row);
            }

            return value;
        };

        $scope.getRowContent = function() {
            var content;
            if ($scope.row.model.columnContents) {
                content = $scope.row.model.columnContents[$scope.column];
            }
            if (content === undefined && $scope.column === 'model.name') {
                content = $scope.row.model.content;
            }
            if (content === undefined) {
                content = $scope.pluginScope.contents[$scope.column];
            }
            if (content === undefined && $scope.column === 'model.name') {
                content = $scope.row.rowsManager.gantt.options.value('rowContent');
            }
            if (content === undefined && $scope.pluginScope.content !== undefined) {
                content = $scope.pluginScope.content;
            }
            if (content === undefined) {
                return '{{getValue()}}';
            }
            return content;
        };
    }]);
}());


(function() {
    'use strict';
    angular.module('gantt.tooltips').directive('ganttTooltip', ['$log','$timeout', '$compile', '$document', '$templateCache', 'ganttDebounce', 'ganttSmartEvent', function($log, $timeout, $compile, $document, $templateCache, debounce, smartEvent) {
        // This tooltip displays more information about a task

        return {
            restrict: 'E',
            templateUrl: function(tElement, tAttrs) {
                var templateUrl;
                if (tAttrs.templateUrl === undefined) {
                    templateUrl = 'plugins/tooltips/tooltip.tmpl.html';
                } else {
                    templateUrl = tAttrs.templateUrl;
                }
                if (tAttrs.template !== undefined) {
                    $templateCache.put(templateUrl, tAttrs.template);
                }
                return templateUrl;
            },
            scope: true,
            replace: true,
            controller: ['$scope', '$element', 'ganttUtils', function($scope, $element, utils) {
                var bodyElement = angular.element($document[0].body);
                var parentElement = $scope.task.$element;
                var showTooltipPromise;
                var visible = false;
                var mouseEnterX;
                var mouseMoveHandler;

                var getViewPortWidth = function() {
                    var d = $document[0];
                    return d.documentElement.clientWidth || d.documentElement.getElementById('body')[0].clientWidth;
                };

                var updateTooltip = function(x) {
                    // Check if info is overlapping with view port
                    if (x + $element[0].offsetWidth > getViewPortWidth()) {
                        $element.css('left', (x + 20 - $element[0].offsetWidth) + 'px');
                        $scope.isRightAligned = true;
                    } else {
                        $element.css('left', (x - 20) + 'px');
                        $scope.isRightAligned = false;
                    }
                };

                var showTooltip = function(x) {
                    visible = true;
                    mouseMoveHandler.bind();

                    $scope.displayed = true;

                    $scope.$evalAsync(function() {
                        var restoreNgHide;
                        if ($element.hasClass('ng-hide')) {
                            $element.removeClass('ng-hide');
                            restoreNgHide = true;
                        }
                        $scope.elementHeight = $element[0].offsetHeight;
                        if (restoreNgHide) {
                            $element.addClass('ng-hide');
                        }
                        $scope.taskRect = parentElement[0].getBoundingClientRect();
                        updateTooltip(x);
                    });
                };

                var hideTooltip = function() {
                    visible = false;
                    mouseMoveHandler.unbind();
                    $scope.$evalAsync(function() {
                        $scope.displayed = false;
                    });
                };

                var displayTooltip = function(newValue, showDelayed) {
                    if (showTooltipPromise) {
                        $timeout.cancel(showTooltipPromise);
                    }

                    var taskTooltips = $scope.task.model.tooltips;
                    var rowTooltips = $scope.task.row.model.tooltips;

                    if (typeof(taskTooltips) === 'boolean') {
                        taskTooltips = {enabled: taskTooltips};
                    }

                    if (typeof(rowTooltips) === 'boolean') {
                        rowTooltips = {enabled: rowTooltips};
                    }

                    var enabled = utils.firstProperty([taskTooltips, rowTooltips], 'enabled', $scope.pluginScope.enabled);
                    if (enabled && !visible && mouseEnterX !== undefined && newValue) {
                        if (showDelayed) {
                            showTooltipPromise = $timeout(function() {
                                showTooltip(mouseEnterX);
                            }, $scope.pluginScope.delay, false);
                        } else {
                            showTooltip(mouseEnterX);
                        }
                    } else if (!newValue) {
                        if (!$scope.task.active) {
                            hideTooltip();
                        }
                    }
                };

                mouseMoveHandler = smartEvent($scope, bodyElement, 'mousemove', debounce(function(e) {
                    if (!visible) {
                        mouseEnterX = e.clientX;
                        displayTooltip(true, false);
                    } else {
                        // check if mouse goes outside the parent
                        if(
                            !$scope.taskRect ||
                            e.clientX < $scope.taskRect.left ||
                            e.clientX > $scope.taskRect.right ||
                            e.clientY > $scope.taskRect.bottom ||
                            e.clientY < $scope.taskRect.top
                        ) {
                            displayTooltip(false, false);
                        }

                        updateTooltip(e.clientX);
                    }
                }, 5, false));

                $scope.getFromLabel = function() {
                    var taskTooltips = $scope.task.model.tooltips;
                    var rowTooltips = $scope.task.row.model.tooltips;

                    if (typeof(taskTooltips) === 'boolean') {
                        taskTooltips = {enabled: taskTooltips};
                    }

                    if (typeof(rowTooltips) === 'boolean') {
                        rowTooltips = {enabled: rowTooltips};
                    }

                    var dateFormat = utils.firstProperty([taskTooltips, rowTooltips], 'dateFormat', $scope.pluginScope.dateFormat);
                    return $scope.task.model.from.format(dateFormat);
                };

                $scope.getToLabel = function() {
                    var taskTooltips = $scope.task.model.tooltips;
                    var rowTooltips = $scope.task.row.model.tooltips;

                    if (typeof(taskTooltips) === 'boolean') {
                        taskTooltips = {enabled: taskTooltips};
                    }

                    if (typeof(rowTooltips) === 'boolean') {
                        rowTooltips = {enabled: rowTooltips};
                    }

                    var dateFormat = utils.firstProperty([taskTooltips, rowTooltips], 'dateFormat', $scope.pluginScope.dateFormat);
                    return $scope.task.model.to.format(dateFormat);
                };

                $scope.task.getContentElement().bind('mousemove', function(evt) {
                    mouseEnterX = evt.clientX;
                });

                $scope.task.getContentElement().bind('mouseenter', function(evt) {
                    mouseEnterX = evt.clientX;
                    displayTooltip(true, true);
                });

                $scope.task.getContentElement().bind('mouseleave', function() {
                    displayTooltip(false);
                });

                if ($scope.pluginScope.api.tasks.on.moveBegin) {
                    $scope.pluginScope.api.tasks.on.moveBegin($scope, function(task) {
                        if (task === $scope.task) {
                            displayTooltip(true);
                        }
                    });

                    $scope.pluginScope.api.tasks.on.moveEnd($scope, function(task) {
                        if (task === $scope.task) {
                            displayTooltip(false);
                        }
                    });

                    $scope.pluginScope.api.tasks.on.resizeBegin($scope, function(task) {
                        if (task === $scope.task) {
                            displayTooltip(true);
                        }
                    });

                    $scope.pluginScope.api.tasks.on.resizeEnd($scope, function(task) {
                        if (task === $scope.task) {
                            displayTooltip(false);
                        }
                    });
                }

                if ($scope.task.isMoving) {
                    // Display tooltip because task has been moved to a new row
                    displayTooltip(true, false);
                }

                $scope.gantt.api.directives.raise.new('ganttTooltip', $scope, $element);
                $scope.$on('$destroy', function() {
                    $scope.gantt.api.directives.raise.destroy('ganttTooltip', $scope, $element);
                });
            }]
        };
    }]);
}());


(function(){
    'use strict';
    angular.module('gantt.tree').directive('ganttRowTreeLabel', ['GanttDirectiveBuilder', function(Builder) {
        var builder = new Builder('ganttRowTreeLabel');
        builder.restrict = 'A';
        builder.templateUrl = undefined;
        return builder.build();
    }]);
}());


(function(){
    'use strict';
    angular.module('gantt.tree').directive('ganttSideContentTree', ['GanttDirectiveBuilder', function(Builder) {
        var builder = new Builder('ganttSideContentTree', 'plugins/tree/sideContentTree.tmpl.html');
        return builder.build();
    }]);
}());


(function() {
    'use strict';
    angular.module('gantt.tree').controller('GanttTreeController', ['$scope', '$filter', 'GanttHierarchy', function($scope, $filter, Hierarchy) {
        $scope.rootRows = [];

        $scope.getHeader = function() {
            return $scope.pluginScope.header;
        };

        var hierarchy = new Hierarchy();

        $scope.pluginScope.$watchGroup(['keepAncestorOnFilterRow', 'enabled'], function(value) {
            var keepAncestor = value[0] && value[1];

            if (keepAncestor) {
                var filterImpl = function(sortedRows, filterRow, filterRowComparator) {
                    hierarchy.refresh(sortedRows);

                    var leaves = [];
                    for (var i = 0; i < sortedRows.length; i++) {
                        var children = hierarchy.children(sortedRows[i]);
                        if (!children || children.length === 0) {
                            leaves.push(sortedRows[i]);
                        }
                    }

                    var filteredLeaves = $filter('filter')(leaves, filterRow, filterRowComparator);

                    var filterRowKeepAncestor = function(row) {
                        if (filteredLeaves.indexOf(row) > -1) {
                            return true;
                        }

                        var descendants = hierarchy.descendants(row);

                        for (var i = 0; i < descendants.length; i++) {
                            if (filteredLeaves.indexOf(descendants[i]) > -1) {
                                return true;
                            }
                        }

                        return false;
                    };

                    return $filter('filter')(sortedRows, filterRowKeepAncestor, filterRowComparator);
                };
                $scope.gantt.rowsManager.setFilterImpl(filterImpl);
            } else {
                $scope.gantt.rowsManager.setFilterImpl(false);
            }
        });

        var isVisible = function(row) {
            var parentRow = $scope.parent(row);
            while (parentRow !== undefined) {
                if (parentRow !== undefined && parentRow._collapsed) {
                    return false;
                }
                parentRow = $scope.parent(parentRow);
            }
            return true;
        };

        var filterRowsFunction = function(rows) {
            return rows.filter(function(row) {
                return isVisible(row);
            });
        };

        var sortRowsFunction = function(rows) {
            var sortedRows = [];
            var rootRows = [];

            var hasParent = false;

            for (var i=0; i<rows.length; i++) {
                var rowParent = $scope.parent(rows[i]);
                if (rowParent === undefined) {
                    rootRows.push(rows[i]);
                } else {
                    hasParent = true;
                }
            }

            var handleChildren = function(row) {
                sortedRows.push(row);
                var children = $scope.children(row);


                if (children !== undefined && children.length > 0) {
                    var sortedChildren = children.sort(function(a, b) {
                        return rows.indexOf(a) - rows.indexOf(b);
                    });

                    for (var i=0;i<sortedChildren.length;i++) {
                        handleChildren(sortedChildren[i]);
                    }
                }
            };

            for (i=0; i<rootRows.length; i++) {
                handleChildren(rootRows[i]);
            }

            return sortedRows;
        };
        $scope.gantt.api.rows.addRowSorter(sortRowsFunction);
        $scope.gantt.api.rows.addRowFilter(filterRowsFunction);

        $scope.$on('$destroy', function() {
            $scope.gantt.api.rows.removeRowSorter(sortRowsFunction);
            $scope.gantt.api.rows.removeRowFilter(filterRowsFunction);
        });

        var refresh = function() {
            $scope.rootRows = hierarchy.refresh($scope.gantt.rowsManager.filteredRows);

            if ($scope.gantt.rowsManager.filteredRows.length > 0) {
                $scope.gantt.api.rows.sort();
                $scope.gantt.api.rows.refresh();
            }
        };

        $scope.gantt.api.rows.on.remove($scope, refresh);
        $scope.gantt.api.rows.on.add($scope, refresh);

        var isRowCollapsed = function(rowId) {
            var row;
            if (typeof rowId === 'string') {
                row = $scope.gantt.rowsManager.rowsMap[rowId];
            } else {
                row = rowId;
            }
            if (row === undefined) {
                return undefined;
            }
            if (row._collapsed === undefined) {
                return false;
            }
            return row._collapsed;
        };

        var expandRow = function(rowId) {
            var row;
            if (typeof rowId === 'string') {
                row = $scope.gantt.rowsManager.rowsMap[rowId];
            } else {
                row = rowId;
            }
            if (row === undefined) {
                return;
            }

            var rowScope = $scope.nodeScopes[row.model.id];
            if (rowScope.collapsed) {
                rowScope.toggle();
            }
        };

        var collapseRow = function(rowId) {
            var row;
            if (typeof rowId === 'string') {
                row = $scope.gantt.rowsManager.rowsMap[rowId];
            } else {
                row = rowId;
            }
            if (row === undefined) {
                return;
            }

            var rowScope = $scope.nodeScopes[row.model.id];
            if (!rowScope.collapsed) {
                rowScope.toggle();
            }
        };

        var getHierarchy = function() {
            return hierarchy;
        };

        $scope.getHeaderContent = function() {
            return $scope.pluginScope.headerContent;
        };

        $scope.gantt.api.registerMethod('tree', 'refresh', refresh, this);
        $scope.gantt.api.registerMethod('tree', 'isCollapsed', isRowCollapsed, this);
        $scope.gantt.api.registerMethod('tree', 'expand', expandRow, this);
        $scope.gantt.api.registerMethod('tree', 'collapse', collapseRow, this);

        $scope.gantt.api.registerEvent('tree', 'collapsed');

        $scope.gantt.api.registerMethod('tree', 'getHierarchy', getHierarchy, this);

        $scope.$watchCollection('gantt.rowsManager.filteredRows', function() {
            refresh();
        });

        $scope.children = function(row) {
            if (row === undefined) {
                return $scope.rootRows;
            }
            return hierarchy.children(row);
        };

        $scope.parent = function(row) {
            return hierarchy.parent(row);
        };

        $scope.nodeScopes = {};
    }]).controller('GanttUiTreeController', ['$scope', function($scope) {
        var collapseAll = function() {
            $scope.$broadcast('angular-ui-tree:collapse-all');
        };

        var expandAll = function() {
            $scope.$broadcast('angular-ui-tree:expand-all');
        };

        $scope.gantt.api.registerMethod('tree', 'collapseAll', collapseAll, $scope);
        $scope.gantt.api.registerMethod('tree', 'expandAll', expandAll, $scope);
    }]).controller('GanttTreeNodeController', ['$scope', function($scope) {
        $scope.$parent.nodeScopes[$scope.row.model.id] = $scope;
        $scope.$on('$destroy', function() {
            delete $scope.$parent.nodeScopes[$scope.row.model.id];
        });

        $scope.$watch('children(row)', function(newValue) {
            if (newValue) {
                // Children rows may have been filtered out
                // So we need to filter the raw hierarchy before displaying children in tree.
                var visibleRows = $scope.row.rowsManager.filteredRows;

                var filteredChildrenRows = [];
                for (var i = 0; i < newValue.length; i++) {
                    var childRow = newValue[i];
                    if (visibleRows.indexOf(childRow) > -1) {
                        filteredChildrenRows.push(childRow);
                    }
                }

                $scope.$parent.childrenRows = filteredChildrenRows;
            } else {
                $scope.$parent.childrenRows = newValue;
            }
        });

        $scope.isCollapseDisabled = function() {
            return  false;//!$scope.$parent.childrenRows || $scope.$parent.childrenRows.length === 0;
        };

        $scope.getValue = function() {
            return $scope.row.model.name;
        };

        $scope.getRowContent = function() {
            if ($scope.row.model.content !== undefined) {
                return $scope.row.model.content;
            }
            if ($scope.pluginScope.content !== undefined) {
                return $scope.pluginScope.content;
            }

            var content = $scope.row.rowsManager.gantt.options.value('rowContent');
            if (content === undefined) {
                content = '{{row.model.name}}';
            }
            return content;
        };

        $scope.$watch('collapsed', function(newValue) {
            if ($scope.$modelValue._collapsed !== newValue) {
                var oldValue = $scope.$modelValue._collapsed;
                $scope.$modelValue._collapsed = newValue; // $modelValue contains the Row object
                if (oldValue !== undefined && newValue !== oldValue) {
                    $scope.gantt.api.tree.raise.collapsed($scope, $scope.$modelValue, newValue);
                    $scope.gantt.api.rows.refresh();
                }
            }
        });
    }]);
}());


(function(){
    'use strict';
    angular.module('gantt.tree').directive('ganttTreeBody', ['GanttDirectiveBuilder', 'ganttLayout', function(Builder, layout) {
        var builder = new Builder('ganttTreeBody', 'plugins/tree/treeBody.tmpl.html');
        builder.controller = function($scope) {
            var hScrollBarHeight = layout.getScrollBarHeight();

            $scope.getLabelsCss = function() {
                var css = {};

                if ($scope.maxHeight) {
                    var bodyScrollBarHeight = $scope.gantt.scroll.isHScrollbarVisible() ? hScrollBarHeight : 0;
                    css['max-height'] = $scope.maxHeight - bodyScrollBarHeight - $scope.gantt.header.getHeight() + 'px';
                }

                return css;
            };
        };
        return builder.build();
    }]);
}());



(function(){
    'use strict';
    angular.module('gantt.tree').directive('ganttTreeHeader', ['GanttDirectiveBuilder', function(Builder) {
        var builder = new Builder('ganttTreeHeader', 'plugins/tree/treeHeader.tmpl.html');
        return builder.build();
    }]);
}());


angular.module('gantt.bounds.templates', []).run(['$templateCache', function($templateCache) {
    $templateCache.put('plugins/bounds/taskBounds.tmpl.html',
        '<div ng-cloak class="gantt-task-bounds" ng-style="getCss()" ng-class="getClass()"></div>\n' +
        '');
}]);

angular.module('gantt.dependencies.templates', []).run(['$templateCache', function($templateCache) {

}]);

angular.module('gantt.drawtask.templates', []).run(['$templateCache', function($templateCache) {

}]);

angular.module('gantt.groups.templates', []).run(['$templateCache', function($templateCache) {
    $templateCache.put('plugins/groups/taskGroup.tmpl.html',
        '<div ng-controller="GanttGroupController">\n' +
        '    <div class="gantt-task-group-overview" ng-if="taskGroup.overviewTasks.length > 0">\n' +
        '        <gantt-task-overview ng-repeat="task in taskGroup.overviewTasks"></gantt-task-overview>\n' +
        '    </div>\n' +
        '    <div class="gantt-task-group-promote" ng-if="taskGroup.row._collapsed && taskGroup.promotedTasks.length > 0">\n' +
        '        <gantt-task ng-repeat="task in taskGroup.promotedTasks"></gantt-task>\n' +
        '    </div>\n' +
        '    <div class="gantt-task-group"\n' +
        '         ng-if="taskGroup.showGrouping"\n' +
        '         ng-style="{\'left\': taskGroup.left + \'px\', \'width\': taskGroup.width + \'px\'}">\n' +
        '        <div class="gantt-task-group-left-main"></div>\n' +
        '        <div class="gantt-task-group-right-main"></div>\n' +
        '        <div class="gantt-task-group-left-symbol"></div>\n' +
        '        <div class="gantt-task-group-right-symbol"></div>\n' +
        '    </div>\n' +
        '</div>\n' +
        '\n' +
        '');
    $templateCache.put('plugins/groups/taskOverview.tmpl.html',
        '<div class="gantt-task gantt-task-overview" ng-class="task.model.classes">\n' +
        '    <gantt-task-background></gantt-task-background>\n' +
        '    <gantt-task-content></gantt-task-content>\n' +
        '    <gantt-task-foreground></gantt-task-foreground>\n' +
        '</div>\n' +
        '\n' +
        '');
}]);

angular.module('gantt.labels.templates', []).run(['$templateCache', function($templateCache) {
    $templateCache.put('plugins/labels/labelsBody.tmpl.html',
        '<div class="gantt-labels-body" ng-style="getLabelsCss()">\n' +
        '    <div gantt-vertical-scroll-receiver>\n' +
        '        <div ng-repeat="row in gantt.rowsManager.visibleRows track by row.model.id">\n' +
        '            <div gantt-row-label\n' +
        '                 class="gantt-row-label gantt-row-height"\n' +
        '                 ng-class="row.model.classes"\n' +
        '                 ng-style="{\'height\': row.model.height}">\n' +
        '                <span class="gantt-label-text">{{row.model.name}}</span>\n' +
        '            </div>\n' +
        '        </div>\n' +
        '    </div>\n' +
        '</div>\n' +
        '');
    $templateCache.put('plugins/labels/labelsHeader.tmpl.html',
        '<div class="gantt-labels-header">\n' +
        '    <div ng-show="gantt.columnsManager.columns.length > 0 && gantt.columnsManager.headers.length > 0">\n' +
        '        <div ng-repeat="header in gantt.columnsManager.headers">\n' +
        '            <div class="gantt-row-height" ng-class="{\'gantt-labels-header-row\': $last, \'gantt-labels-header-row-last\': $last}"><span>{{$last ? pluginScope.header : ""}}</span></div>\n' +
        '        </div>\n' +
        '    </div>\n' +
        '</div>\n' +
        '');
    $templateCache.put('plugins/labels/sideContentLabels.tmpl.html',
        '<div class="gantt-side-content-labels">\n' +
        '    <gantt-labels-header>\n' +
        '    </gantt-labels-header>\n' +
        '    <gantt-labels-body>\n' +
        '    </gantt-labels-body>\n' +
        '</div>\n' +
        '');
}]);

angular.module('gantt.movable.templates', []).run(['$templateCache', function($templateCache) {

}]);

angular.module('gantt.overlap.templates', []).run(['$templateCache', function($templateCache) {

}]);

angular.module('gantt.progress.templates', []).run(['$templateCache', function($templateCache) {
    $templateCache.put('plugins/progress/taskProgress.tmpl.html',
        '<div ng-cloak class="gantt-task-progress" ng-style="getCss()" ng-class="getClasses()"></div>\n' +
        '');
}]);

angular.module('gantt.resizeSensor.templates', []).run(['$templateCache', function($templateCache) {

}]);

angular.module('gantt.sortable.templates', []).run(['$templateCache', function($templateCache) {

}]);

angular.module('gantt.table.templates', []).run(['$templateCache', function($templateCache) {
    $templateCache.put('plugins/table/sideContentTable.tmpl.html',
        '<div class="gantt-side-content-table">\n' +
        '\n' +
        '    <div class="gantt-table-column {{getClass()}}" ng-repeat="column in pluginScope.columns" ng-controller="TableColumnController">\n' +
        '\n' +
        '        <div class="gantt-table-header" ng-style="{height: ganttHeaderHeight + \'px\'}">\n' +
        '            <div ng-show="ganttHeaderHeight" class="gantt-row-label-header gantt-row-label gantt-table-row gantt-table-header-row">\n' +
        '                <span class="gantt-label-text" gantt-bind-compile-html="getHeaderContent()"/>\n' +
        '            </div>\n' +
        '        </div>\n' +
        '\n' +
        '        <div class="gantt-table-content" ng-style="getMaxHeightCss()">\n' +
        '            <div gantt-vertical-scroll-receiver>\n' +
        '                <div class="gantt-table-row" ng-repeat="row in gantt.rowsManager.visibleRows track by row.model.id" ng-controller="TableColumnRowController">\n' +
        '                    <div gantt-row-label class="gantt-row-label gantt-row-height" ng-class="row.model.classes" ng-style="{\'height\': row.model.height}">\n' +
        '                        <div class="gantt-valign-container">\n' +
        '                            <div class="gantt-valign-content">\n' +
        '                                <span class="gantt-label-text" gantt-bind-compile-html="getRowContent()"></span>\n' +
        '                            </div>\n' +
        '                        </div>\n' +
        '                    </div>\n' +
        '                </div>\n' +
        '            </div>\n' +
        '        </div>\n' +
        '\n' +
        '    </div>\n' +
        '</div>\n' +
        '');
}]);

angular.module('gantt.tooltips.templates', []).run(['$templateCache', function($templateCache) {
    $templateCache.put('plugins/tooltips/tooltip.tmpl.html',
        '<div ng-cloak\n' +
        '     class="gantt-task-info"\n' +
        '     ng-show="displayed"\n' +
        '     ng-class="isRightAligned ? \'gantt-task-infoArrowR\' : \'gantt-task-infoArrow\'"\n' +
        '     ng-style="{top: taskRect.top + \'px\', marginTop: -elementHeight - 8 + \'px\'}">\n' +
        '    <div class="gantt-task-info-content">\n' +
        '        <div gantt-bind-compile-html="pluginScope.content"></div>\n' +
        '    </div>\n' +
        '</div>\n' +
        '');
}]);

angular.module('gantt.tree.templates', []).run(['$templateCache', function($templateCache) {
    $templateCache.put('plugins/tree/sideContentTree.tmpl.html',
        '<div class="gantt-side-content-tree" ng-controller="GanttTreeController">\n' +
        '    <gantt-tree-header>\n' +
        '    </gantt-tree-header>\n' +
        '    <gantt-tree-body>\n' +
        '    </gantt-tree-body>\n' +
        '</div>\n' +
        '');
    $templateCache.put('plugins/tree/treeBody.tmpl.html',
        '<div class="gantt-tree-body" ng-style="getLabelsCss()">\n' +
        '    <div gantt-vertical-scroll-receiver>\n' +
        '        <div class="gantt-row-label-background">\n' +
        '            <div class="gantt-row-label gantt-row-height"\n' +
        '                 ng-class="row.model.classes"\n' +
        '                 ng-style="{\'height\': row.model.height}"\n' +
        '                 ng-repeat="row in gantt.rowsManager.visibleRows track by row.model.id">\n' +
        '                &nbsp;\n' +
        '            </div>\n' +
        '        </div>\n' +
        '        <div ui-tree ng-controller="GanttUiTreeController" data-drag-enabled="false" data-empty-place-holder-enabled="false">\n' +
        '            <ol class="gantt-tree-root" ui-tree-nodes ng-model="rootRows">\n' +
        '                <li ng-repeat="row in rootRows" ui-tree-node data-collapsed="true"    \n' +
        '                    ng-include="\'plugins/tree/treeBodyChildren.tmpl.html\'">\n' +
        '                </li>\n' +
        '            </ol>\n' +
        '        </div>\n' +
        '    </div>\n' +
        '</div>\n' +
        '');
    $templateCache.put('plugins/tree/treeBodyChildren.tmpl.html',
        '<div ng-controller="GanttTreeNodeController"\n' +
        '     class="gantt-row-label gantt-row-height"\n' +
        '     ng-class="row.model.classes"\n' +
        '     ng-style="{\'height\': row.model.height}">\n' +
        '    <div class="gantt-valign-container">\n' +
        '        <div class="gantt-valign-content">\n' +
        '            <a ng-disabled="isCollapseDisabled()" data-nodrag\n' +
        '               class="gantt-tree-handle-button btn btn-xs"\n' +
        '               ng-class="{\'gantt-tree-collapsed\': collapsed, \'gantt-tree-expanded\': !collapsed}"\n' +
        '               ng-click="!isCollapseDisabled() && toggle()"><span\n' +
        '                class="gantt-tree-handle glyphicon glyphicon-chevron-down"\n' +
        '                ng-class="{\n' +
        '                \'glyphicon-chevron-right\': collapsed, \'glyphicon-chevron-down\': !collapsed,\n' +
        '                \'gantt-tree-collapsed\': collapsed, \'gantt-tree-expanded\': !collapsed}"></span>\n' +
        '            </a>\n' +
        '            <span gantt-row-label class="gantt-label-text" gantt-bind-compile-html="getRowContent()"/>\n' +
        '        </div>\n' +
        '    </div>\n' +
        '</div>\n' +
        '<ol ui-tree-nodes ng-class="{hidden: collapsed}" ng-model="childrenRows">\n' +
        '    <li ng-repeat="row in childrenRows" ui-tree-node>\n' +
        '        <div ng-include="\'plugins/tree/treeBodyChildren.tmpl.html\'"></div>\n' +
        '    </li>\n' +
        '</ol>\n' +
        '');
    $templateCache.put('plugins/tree/treeHeader.tmpl.html',
        '<div class="gantt-tree-header" ng-style="{height: $parent.ganttHeaderHeight + \'px\'}">\n' +
        '    <div ng-if="$parent.ganttHeaderHeight" class="gantt-row-label gantt-row-label-header gantt-tree-row gantt-tree-header-row"><span class="gantt-label-text" gantt-bind-compile-html="getHeaderContent()"/></div>\n' +
        '</div>\n' +
        '');
}]);

//# sourceMappingURL=angular-gantt-plugins.js.map