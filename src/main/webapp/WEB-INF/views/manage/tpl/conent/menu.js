app
		.controller(
				'MenuCtrl',
				[
						'$scope',
						'$http',
						'$timeout',
						'$uibModal',
						function($scope,$http, $timeout,$uibModal) {
							$scope.data = [];
							// $scope.styles=[{'name':'Alticle','class':'ARTICLE'},{'name':'Page','class':'PAGE'},{'name':'URL','class':'URL'}
							// ];
							setTimeout(function() {
								var data, url = './menu/stems/root';
								if ($scope.data.length < 1) {
									$http.get(url).success(function(largeLoad) {
										$scope.data = largeLoad.data;
									});
								}
							}, 100);

							$scope.removeSubItem = function(scope) {
								console.log(scope);
								var nodeData = this.$modelValue;
								if (!(nodeData.id) && !(nodeData.stem)) {
									scope.remove();
								} else {
									$http
											.get(
													"./menu/destroy/stem/"
															+ nodeData.stem
															+ nodeData.id + ',')
											.success(
													function(largeLoad) {
														if (largeLoad.success) {
															$http(
																	{
																		url : "./menu/destroy/"
																				+ nodeData.id,
																		method : 'DELETE'
																	})
																	.success(
																			function(
																					largeLoad) {
																				if (largeLoad.success) {
																					scope
																							.remove();
																				}
																			});
														}
													});
								}

							};
							var newMenu = function(nodeData) {
								return {
									'id' : null,
									'title' : nodeData.title + '.'
											+ (nodeData.children.length + 1),
									'parentId' : nodeData.id,
									'stem' : nodeData.stem
											+ ((nodeData.id === null) ? ''
													: nodeData.id) + ',',
									'children' : [],
									'alias' : null,
									'author' : null,
									'clicks' : 0,
									'description' : null,
									'edittime' : new Date(),
									'isNavigation' : true,
									'isPublish' : true,
									'keyword' : nodeData.title,
									'sort' : 0,
									'style' : "ARTICLE",
									'thumb' : null,
									'url' : null
								};
							}

							var menuSave = function(data) {
								url = './menu/update', param = {
									method : 'PATCH',
									'url' : url,
									'data' : data
								};
								if (data.id == null) {
									param.method = 'PUT';
									param.url = './menu/create';
								}
								$http(param)
										.success(
												function(largeLoad) {
													console.log(largeLoad);
													if (largeLoad.success
															&& (!!largeLoad.data)
															&& (!!largeLoad.data.id)) {
														$scope.menuData.id = largeLoad.data.id;
														// articleSave();
													}
												});
							}
							var articleSave = function(data) {
								url = './article/update', param = {
									method : 'PATCH',
									'url' : url,
									'data' : data
								};
								if (data.id === null) {
									param.method = 'PUT';
									param.url = './article/create';
								}
								$http(param)
										.success(
												function(largeLoad) {
													if (largeLoad.success
															&& (!!largeLoad.data)
															&& (!!largeLoad.data.id)) {
														menuData.article.id = largeLoad.data.id;
													}
												});
							}

							$scope.save = function(data) {
								console.log(data);

								if (typeof data.title === 'undefined') {
									articleSave(data)
								} else {
									menuSave(data)
								}

							}
							$scope.newMenuRoot = function() {

								var newData = newMenu({
									id : null,
									title : 'Root ' + (new Date().getTime()),
									stem : '',
									children : []
								});
								$scope.data.push(newData);
								$scope.menuData = newData;
							}

							// add menu
							$scope.newSubItem = function(nodeData) {
								var newData;
								if (!!nodeData && nodeData.id == null) {
									alert("请保存上级节点后,在添加子节点");
									return;
								}
								if (nodeData.children == null)
									nodeData.children = [];
								newData = newMenu(nodeData);
								nodeData.children.push(newData);
								$scope.menuData = newData;
							};

							var getArticle = function(nodeData, fun) {
								if (typeof nodeData.article === 'undefined') {
									$http
											.get(
													'./article/read/menuid/'
															+ nodeData.id)
											.success(
													function(largeLoad) {
														if (largeLoad.success) {
															if (!!largeLoad.data &&!!largeLoad.data.id) {
																nodeData.article = largeLoad.data;
															} else {
																nodeData.article = {
																	id : null,
																	menuId : nodeData.id,
																	context : "请输入文本类容,可以使用html javascript css 等....",
																	uploadtime : new Date()
																};
															}
															fun(nodeData);
														} else {
															alert(largeLoad.message);
															console
																	.log('Error','获取Article 失败');
														}

													})

								} else {
									nodeData.article;
									fun(nodeData);
								}
							}
							// Editot Artirlce
							$scope.editorArticle = function(nodeData) {
								console.log(nodeData);
								getArticle(
										nodeData,
										function(data) {
											console.log(data);
											$uibModal.open({
											    size: 'lg m-n no-padder',//aside
												templateUrl : './manage/tpl/conent/articleFrom.html',
												controller: function($scope) {
													$scope.articleData =  data.article; 
												}
											});

										});
							}

							$scope.spread = function(scope) {
								console.log(scope);
								if (scope.$modelValue.children == null) {
									setTimeout(
											function() {
												var data, url = './menu/children/'
														+ scope.$modelValue.id;
												$http
														.get(url)
														.success(
																function(largeLoad) {
																	scope.$modelValue.children = largeLoad.data;
																	scope.toggle();
																});

											}, 100);
								} else {
									scope.toggle();
								}

							};

							$scope.editSubItem = function(scope) {
								console.log(scope);
								$scope.menuData = scope;

							}

						} ]);