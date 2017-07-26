if (typeof com === "undefined") {
	com = {};
}
com.tatesuke = com.tatesuke || {};
com.tatesuke.notter = com.tatesuke.notter || {};
com.tatesuke.notter.notterApp = angular.module('notterApp', []);

(function(notterApp) {
	notterApp.service("noteServie", ["$http", function($http){
		
		this.findAll = function() {
			return $http({
				method: 'POST',
				url: 'note/list',
				data: {
				}
			});
		}
		
		this.add = function(text, tags) {
			return $http({
				method: 'POST',
				url: 'note/add',
				data: {
					text : text,
					tags : tags,
				}
			});
		}
		
		this.remove = function(note) {
			return $http({
				method: 'POST',
				url: 'note/remove',
				data: note
			});
		}
		
		this.update = function(note) {
			return $http({
				method: 'POST',
				url: 'note/update',
				data: note
			});
		}
		
		this.toggleTag = function(note, tag) {
			let i;
			for (i = 0; i < note.tags.length; i++) {
				if (note.tags[i].text == tag) {
					break;
				}
			}
			
			if (i == note.tags.length) {
				note.tags.push({
					text: tag
				});
			} else {
				note.tags.splice(i, 1);
			}
		
			return $http({
				method: 'POST',
				url: 'note/update',
				data: note
			});
		}
	}]);
	
	notterApp.directive("markdown", function(){
		return {
			restrict: "E",
			transclude: false,
			template: '<div ng-bind-html="render(value)"></div>',
			scope:{
				value: "=",
			},
			controller: ["$scope", "$sce" ,function($scope, $sce){
				$scope.render = function(value) {
					marked.setOptions({
						renderer: new marked.Renderer(),
						breaks: true,
						sanitize: true,
					});
					let html = marked(value);
					return $sce.trustAsHtml(html);
				}
			}] 
		};
	});
})(com.tatesuke.notter.notterApp);
