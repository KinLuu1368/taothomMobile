app.controller("product-ctrl", function($scope, $http) {
    $scope.items = [];
    $scope.cates = [];
    $scope.series = [];
    $scope.pm = [];
    $scope.form = {};
    $scope.availableColors = [];
    $scope.availableCapacities = [];

	$scope.colors = [];
	$scope.caps = [];

    $scope.initialize = function() {
        // Lấy danh sách sản phẩm
        $http.get("/rest/products").then(resp => {
            $scope.items = resp.data;
            $scope.items.forEach(item => {
                item.createDate = new Date(item.createDate);
            });
        });

        // Lấy danh sách danh mục
        $http.get("/rest/categories").then(resp => {
            $scope.cates = resp.data;
        });

        // Lấy danh sách ProductModel
        $http.get("/rest/ProductModel").then(resp => {
            $scope.pm = resp.data;
        });

        // Lấy danh sách Series
        $http.get("/rest/series").then(resp => {
            $scope.series = resp.data;
        });

		// Lấy danh sách màu sắc
		$http.get("/rest/productcolors").then(resp => {
            $scope.colors = resp.data;
        });

		// Lấy danh sách dung lượng
		$http.get("/rest/productcapacities").then(resp => {
            $scope.caps = resp.data;
        });
    };


	//Đổ màu sắc theo productmodel
    $scope.onProductModelChange = function(productModelId) {
		// Lọc danh sách màu sắc theo ProductModel đã chọn
		$scope.availableColors = $scope.colors.filter(color => color.productModel.id === productModelId);
		
		// Lọc danh sách dung lượng theo ProductModel đã chọn
		$scope.availableCapacities = $scope.caps.filter(capacity => capacity.productModel.id === productModelId);
		
		// Gán giá trị mặc định nếu có dữ liệu
		$scope.form.color = $scope.availableColors.length > 0 ? $scope.availableColors[0].color : 'Default Color';
		$scope.form.capacity = $scope.availableCapacities.length > 0 ? $scope.availableCapacities[0].capacity : 'Default Capacity';
	};
	

    // Upload hình ảnh
    $scope.imageChanged = function(files) {
        var data = new FormData();
        data.append('file', files[0]);
        $http.post('/rest/upload/images', data, {
            transformRequest: angular.identity,
            headers: {'Content-Type': undefined}
        }).then(resp => {
            $scope.form.image = resp.data.name;
        }).catch(error => {
            alert("Lỗi upload hình ảnh");
            console.log("Error", error);
        });
    };

	$scope.edit = function(item) {
		$scope.form = angular.copy(item)
	}

    // Thêm mới sản phẩm
    $scope.create = function() {
		console.log($scope.form)
        var item = angular.copy($scope.form);
		console.log(item)
        $http.post(`/rest/products`, item).then(resp => {
            resp.data.createDate = new Date(resp.data.createDate);
            $scope.items.push(resp.data);
            $scope.reset();
            alert("Create success");
        }).catch(error => {
            alert("Create failed");
            console.log(error);
        });
    };

    // Cập nhật sản phẩm
    $scope.update = function() {
        var item = angular.copy($scope.form);
        $http.put(`/rest/products/${item.id}`, item).then(resp => {
            var index = $scope.items.findIndex(p => p.id == item.id);
            $scope.items[index] = item;
            alert("Update success");
        }).catch(error => {
            alert("Update failed");
            console.log("Error", error);
        });
    };

    // Xóa sản phẩm
    $scope.delete = function(item) {
        $http.delete(`/rest/products/${item.id}`).then(resp => {
            var index = $scope.items.findIndex(p => p.id == item.id);
            $scope.items.splice(index, 1);
            $scope.reset();
            alert("Delete success");
        }).catch(error => {
            alert("Delete failed");
            console.log("Error", error);
        });
    };

    // Reset form
    $scope.reset = function() {
        $scope.form = {};
    };

    // Phân trang
    $scope.pager = {
        page: 0,
        size: 10,
        get items() {
            var start = this.page * this.size;
            return $scope.items.slice(start, start + this.size);
        },
        get count() {
            return Math.ceil(1.0 * $scope.items.length / this.size);
        },
        first() {
            this.page = 0;
        },
        prev() {
            this.page--;
            if (this.page < 0) {
                this.last();
            }
        },
        next() {
            this.page++;
            if (this.page >= this.count) {
                this.first();
            }
        },
        last() {
            this.page = this.count - 1;
        }
    };

    // Gọi hàm khởi tạo
    $scope.initialize();
});
