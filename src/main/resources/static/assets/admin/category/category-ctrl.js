app.controller("category-ctrl", function($scope, $http) {
    $scope.items =[]
    $scope.form = {}

	$scope.series =[]
	$scope.Sform = {}

    $scope.initialize = function() {
        $http.get("/rest/categories").then(resp => {
            $scope.items = resp.data;
        });

		$http.get("/rest/series").then(resp => {
            $scope.series = resp.data;
        });

        console.log(JSON.stringify($scope.items))
    };

    $scope.edit = function(item) {
        $scope.form = angular.copy(item);
    }

    $scope.reset = function() {
        $scope.form = {};
    }

    $scope.create = function() {
        var item = angular.copy($scope.form)
        console.log(item);
        $http.post(`/rest/categories`, item).then(resp => {
            $scope.items.push(resp.data)
            $scope.reset()
            alert("create success")
        }).catch(error => {
            alert("create fail")
            console.log(error)
        })
    }

    $scope.update = function(){
		var item = angular.copy($scope.form);
		$http.put(`/rest/categories/${item.id}`, item).then(resp => {
			var index = $scope.items.findIndex(p => p.id == item.id);
			$scope.items[index] = item;
			alert("update success");
		}).catch(error => {
			alert("update fail");
			console.log("Error", error);
		});
	}

    $scope.delete = function(item){
		$http.delete(`/rest/categories/${item.id}`).then(resp => {
			var index = $scope.items.findIndex(p => p.id == item.id);
			$scope.items.splice(index, 1);
			$scope.reset();
			alert("delete success");
		}).catch(error => {
			alert("delete fail");
			console.log("Error", error);
		});
	}

	// Series controller
	$scope.edit1 = function(item) {
        $scope.Sform = angular.copy(item);
    }

    $scope.reset1 = function() {
        $scope.Sform = {};
    }

    $scope.create1 = function() {
        var item = angular.copy($scope.Sform)
        console.log(item);
        $http.post(`/rest/series`, item).then(resp => {
            $scope.series.push(resp.data)
            $scope.reset()
            alert("create success")
        }).catch(error => {
            alert("create fail")
            console.log(error)
        })
    }

    $scope.update1 = function(){
		var item = angular.copy($scope.Sform);
		$http.put(`/rest/series/${item.id}`, item).then(resp => {
			var index = $scope.series.findIndex(p => p.id == item.id);
			$scope.series[index] = item;
			alert("update success");
		}).catch(error => {
			alert("update fail");
			console.log("Error", error);
		});
	}

    $scope.delete1 = function(item){
		$http.delete(`/rest/series/${item.id}`).then(resp => {
			var index = $scope.series.findIndex(p => p.id == item.id);
			$scope.series.splice(index, 1);
			$scope.reset1();
			alert("delete success");
		}).catch(error => {
			alert("delete fail");
			console.log("Error", error);
		});
	}

    $scope.pager = {
		page: 0,
		size: 10,
		get items(){
			var start = this.page * this.size;
			return $scope.items.slice(start, start + this.size);
		},
		get count(){
			return Math.ceil(1.0 * $scope.items.length / this.size);
		},
		first(){
			this.page = 0;
		},
		prev(){
			this.page--;
			if(this.page < 0){
				this.last();
			}
		},
		next(){
			this.page++;
			if(this.page >= this.count){
				this.first();
			}
		},
		last(){
			this.page = this.count - 1;
		}
	}

    $scope.initialize();


});