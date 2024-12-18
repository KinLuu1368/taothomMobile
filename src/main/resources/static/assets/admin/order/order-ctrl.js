app.controller("order-ctrl", function($scope, $http) {
    $scope.items =[]
    $scope.cates = []
    $scope.form = {}

    $scope.initialize = function() {
        $http.get("/rest/order").then(resp => {
            $scope.items = resp.data;
            $scope.ordercount = $scope.items.length;
            console.log($scope.items);
        });
    };

	$scope.delete = function(item) {
		$http.delete(`/rest/order/${item.id}`).then(resp => {
			var index = $scope.items.findIndex(p => p.id == item.id);
			$scope.items.splice(index, 1);
			alert("delete success");
		}).catch(error => {
			alert("delete fail");
			console.log("Error", error);
		});
	};

    $scope.pager = {
		page: 0,
		size: 30,
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