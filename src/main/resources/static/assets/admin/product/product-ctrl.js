app.controller("product-ctrl", function($scope, $http) {
    $scope.items =[]
    $scope.cates = []
    $scope.form = {}
	$scope.series = []
	$scope.pm = []

    $scope.initialize = function() {
        $http.get("/rest/products").then(resp => {
            $scope.items = resp.data;
            $scope.items.forEach(item => {
                item.createDate = new Date(item.createDate);
            });
			console.log($scope.items)
        });

        $http.get("/rest/categories").then(resp => {
            $scope.cates = resp.data
        })

		$http.get("/rest/ProductModel").then(resp => {
            $scope.pm = resp.data
        })


		$http.get("/rest/series").then(resp => {
            $scope.series = resp.data;
        });

        console.log(JSON.stringify($scope.cates))
    };

    $scope.edit = function(item) {
        $scope.form = angular.copy(item);
    }

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

    $scope.reset = function() {
        $scope.form = {};
    }

    $scope.create = function() {
        var item = angular.copy($scope.form)
        $http.post(`/rest/products`, item).then(resp => {
            resp.data.createDate = new Date(resp.data.createDate)
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
		$http.put(`/rest/products/${item.id}`, item).then(resp => {
			var index = $scope.items.findIndex(p => p.id == item.id);
			$scope.items[index] = item;
			alert("update success");
		}).catch(error => {
			alert("update fail");
			console.log("Error", error);
		});
	}

    $scope.delete = function(item){
		$http.delete(`/rest/products/${item.id}`).then(resp => {
			var index = $scope.items.findIndex(p => p.id == item.id);
			$scope.items.splice(index, 1);
			$scope.reset();
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