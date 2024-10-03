app.controller("model-ctrl", function($scope, $http) {
    $scope.items =[]
    $scope.form = {}

	$scope.series =[]
	$scope.Sform = {}

	$scope.colors = []
	$scope.colorform = {}

	$scope.caps = []
	$scope.capform = {}

    $scope.initialize = function() {
        $http.get("/rest/ProductModel").then(resp => {
            $scope.items = resp.data;
        });

		$http.get("/rest/series").then(resp => {
            $scope.series = resp.data;
        });

		$http.get("/rest/productcolors").then(resp => {
            $scope.colors = resp.data;
        });

		$http.get("/rest/productcapacities").then(resp => {
            $scope.caps = resp.data;
        });

        console.log(JSON.stringify($scope.items))
    };

    $scope.imageChanged = function(files){
		var data = new FormData();
		data.append('file', files[0]);
		$http.post('/rest/upload/images', data,{ //LINK FILE Ở CHỖ NÀY
			transformRequest: angular.identity,
			headers:{'Content-Type':undefined}
		}).then(resp => {
			$scope.form.image = resp.data.name;
		}).catch(error => {
			alert("Lỗi upload hình ảnh");
			console.log("Error", error);
		})
	}

    $scope.edit = function(item) {
        $scope.form = angular.copy(item);
    }

    $scope.reset = function() {
        $scope.form = {};
    }

    $scope.create = function() {
        var item = angular.copy($scope.form)
        console.log(item);
        $http.post(`/rest/ProductModel`, item).then(resp => {
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
		$http.put(`/rest/ProductModel/${item.id}`, item).then(resp => {
			var index = $scope.items.findIndex(p => p.id == item.id);
			$scope.items[index] = item;
            $scope.reset()
			alert("update success");
		}).catch(error => {
			alert("update fail");
			console.log("Error", error);
		});
	}

    $scope.delete = function(item){
		$http.delete(`/rest/ProductModel/${item.id}`).then(resp => {
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
            $scope.reset1()
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
            $scope.reset1()
			alert("update success");
		}).catch(error => {
			alert("update fail");
			console.log("Error", error);
		});
	}

    $scope.delete1 = function(item){
		$http.delete(`/rest/productcolors/${item.id}`).then(resp => {
			var index = $scope.series.findIndex(p => p.id == item.id);
			$scope.series.splice(index, 1);
			$scope.reset1();
			alert("delete success");
		}).catch(error => {
			alert("delete fail");
			console.log("Error", error);
		});
	}

	// color controller
	$scope.edit2 = function(item) {
        $scope.colorform = angular.copy(item);
    }

    $scope.reset2 = function() {
        $scope.colorform = {};
    }

    $scope.create2 = function() {
        var item = angular.copy($scope.colorform)
        console.log(item);
        $http.post(`/rest/productcolors`, item).then(resp => {
            $scope.colors.push(resp.data)
            $scope.reset2()
            alert("create success")
        }).catch(error => {
            alert("create fail")
			$scope.reset2()
            console.log(error)
        })
    }

    $scope.update2 = function(){
		var item = angular.copy($scope.colorform);
		$http.put(`/rest/productcolors/${item.id}`, item).then(resp => {
			var index = $scope.colors.findIndex(p => p.id == item.id);
			$scope.colors[index] = item;
            $scope.reset2()
			alert("update success");
		}).catch(error => {
			alert("update fail");
			console.log("Error", error);
		});
	}

    $scope.delete2 = function(item){
		$http.delete(`/rest/productcolors/${item.id}`).then(resp => {
			var index = $scope.colors.findIndex(p => p.id == item.id);
			$scope.colors.splice(index, 1);
			$scope.reset2();
			alert("delete success");
		}).catch(error => {
			alert("delete fail");
			console.log("Error", error);
		});
	}

	// capacity controller
	$scope.edit3 = function(item) {
        $scope.capform = angular.copy(item);
    }

    $scope.reset3 = function() {
        $scope.capform = {};
    }

    $scope.create3 = function() {
        var item = angular.copy($scope.capform)
        console.log(item);
        $http.post(`/rest/productcapacities`, item).then(resp => {
            $scope.caps.push(resp.data)
            $scope.reset3()
            alert("create success")
        }).catch(error => {
            alert("create fail")
			$scope.reset3()
            console.log(error)
        })
    }

    $scope.update3 = function(){
		var item = angular.copy($scope.capform);
		$http.put(`/rest/productcapacities/${item.id}`, item).then(resp => {
			var index = $scope.caps.findIndex(p => p.id == item.id);
			$scope.caps[index] = item;
            $scope.reset3()
			alert("update success");
		}).catch(error => {
			alert("update fail");
			console.log("Error", error);
		});
	}

    $scope.delete3 = function(item){
		$http.delete(`/rest/productcapacities/${item.id}`).then(resp => {
			var index = $scope.caps.findIndex(p => p.id == item.id);
			$scope.caps.splice(index, 1);
			$scope.reset3();
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