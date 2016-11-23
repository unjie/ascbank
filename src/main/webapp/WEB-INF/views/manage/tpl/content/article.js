app.controller('ArticlesCtrl', ['$scope', '$http', function($scope,$http) {
	///http://angular-ui.github.io/ui-grid/
	$scope.articleSelections=[];
	
	$scope.titleOptions = {
			placeholderText: 'Add a Title',
			charCounterCount: false,
			toolbarInline: true,
			events: {
				'froalaEditor.initialized': function() {
					console.log('initialized');
				}
			}
		};
	$scope.froalaOptions = {
	        toolbarButtons : ["bold", "italic", "underline", "|", "align", "formatOL", "formatUL"],
	        fileUploadURL : "file/upload",
	        events: {
	            'froalaEditor.initialized': function () {
	                // Use the methods like this.
	                $scope.froalaOptions.froalaEditor('selection.get');
	            }
	        }
	}
	$scope.initialize = function(initControls) {
		$scope.initControls = initControls;
		$scope.deleteAll = function() {
			initControls.getEditor()('html.set', '');
		};
	};
	
    $scope.filterOptions = {
        filterText: "",
        useExternalFilter: true
    }; 
    
    $scope.editorOptions= {
            language: 'ru'
                // uiColor: '#000000'
            };
    
    $scope.totalServerItems = 0;
    $scope.pagingOptions = {
        pageSizes: [250, 500, 1000],
        pageSize: 250,
        currentPage: 1
    };  
    $scope.setPagingData = function(data, page, pageSize){  
    	
        var pagedData = data.slice((page - 1) * pageSize, page * pageSize);
        $scope.articlesData = pagedData;
        
        console.log($scope.articlesData);
        $scope.totalServerItems = data.length;
        if (!$scope.$$phase) {
            $scope.$apply();
        }
    };
    $scope.getPagedDataAsync = function (pageSize, page, searchText) {
        setTimeout(function () {
            var data,url='./article/reads';
            if (searchText) {
                var ft = searchText.toLowerCase();
                $http.get(url).success(function (largeLoad) {    
                    data = largeLoad.data.filter(function(item) {
                        return JSON.stringify(item).toLowerCase().indexOf(ft) != -1;
                    });
                    if(data!=null)
                    $scope.setPagingData(data,page,pageSize);
                });            
            } else {
                $http.get(url).success(function (largeLoad) {
                	if(largeLoad.data!=null)
                    $scope.setPagingData(largeLoad.data,page,pageSize);
                });
            }
        }, 100);
    };

    $scope.getPagedDataAsync($scope.pagingOptions.pageSize, $scope.pagingOptions.currentPage);

    $scope.add=function(articleData){
	    	$scope.articleData={
											id: null,
											context: "请输入文本类容,可以使用html javascript css 等....",
											menuId:null,
											uploadtime:new Date(),
					    				 };
    }
    
    
    $scope.save=function(articleData){
    	console.log(articleData);
    	var data= articleData,url='./article/update',param={method:'PATCH' ,'url':url,'data':data};
    	if(data.id==null){
    		param.method='PUT';
    		param.url= './article/create';
    	}
    	 $http(param).success(function (largeLoad) {
    		
    		 if(articleData.id==null && largeLoad.data.id !=null){
    			 console.log($scope.articlesData);
    			 $scope.articlesData.push(largeLoad.data);
    		 }
    		 $scope.articleData= largeLoad.data;
    	 });
    }
    $scope.afterSelectionChange=function(rowItem, event){
    	console.log(rowItem);
    	console.log(event);
    	$scope.articleData=rowItem.entity;
    }
    
    $scope.$watch('pagingOptions', function (newVal, oldVal) {
        if (newVal !== oldVal && newVal.currentPage !== oldVal.currentPage) {
          $scope.getPagedDataAsync($scope.pagingOptions.pageSize, $scope.pagingOptions.currentPage, $scope.filterOptions.filterText);
        }
    }, true);
    $scope.$watch('filterOptions', function (newVal, oldVal) {
        if (newVal !== oldVal) {
          $scope.getPagedDataAsync($scope.pagingOptions.pageSize, $scope.pagingOptions.currentPage, $scope.filterOptions.filterText);
        }
    }, true);

    $scope.gridOptions = {
        data: 'articlesData',
        enablePaging: true,
        showFooter: true,
       // enableCellEdit: true,
        showFilter:true,
        selectedItems: $scope.articleSelections,
        afterSelectionChange : $scope.afterSelectionChange,
        multiSelect: false,
        totalServerItems: 'totalServerItems',
        pagingOptions: $scope.pagingOptions,
        filterOptions: $scope.filterOptions
    };
    
    
    
    
    
}]);