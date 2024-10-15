var app = angular.module("shopping-cart-app", []);
app.controller("shopping-cart-ctrl", function ($scope, $http) {
	$scope.cart = {
		items: [],

		add(id) {
			var item = this.items.find((item) => item.id == id);
			if (item) {
				item.qty++;
				this.saveToLocalStorage();
			} else {
				$http.get(`/rest/products/${id}`).then((resp) => {
				resp.data.qty = 1;
				this.items.push(resp.data);
				this.saveToLocalStorage();
				});
			}
		},

		remove(id) {
			var index = this.items.findIndex((item) => item.id == id);
			this.items.splice(index, 1);
			this.saveToLocalStorage();
		},

		clear() {
			this.items = [];
			this.saveToLocalStorage();
		},

		amt_of(item) {},

		get count() {
			return this.items
			.map((item) => item.qty)
			.reduce((total, qty) => (total += qty), 0);
		},

		get amount() {
			return this.items
			.map((item) => item.qty * item.price)
			.reduce((total, qty) => (total += qty), 0);
		},

		saveToLocalStorage() {
			var json = JSON.stringify(angular.copy(this.items));
			localStorage.setItem("cart", json);
		},

		loadFromLocalStorage() {
			var json = localStorage.getItem("cart");
			this.items = json ? JSON.parse(json) : [];
		},
	};  
	$scope.cart.loadFromLocalStorage();



  // Khởi tạo các mảng dữ liệu
	$scope.provinces = [];
	$scope.districts = [];
	$scope.wards = [];
	$scope.order = {};

  // Tải danh sách Tỉnh/Thành phố từ API
	$http.get("https://provinces.open-api.vn/api/?depth=1").then(function (response) {
			$scope.provinces = response.data;
	});

  // Khi chọn Tỉnh/Thành phố, tải Quận/Huyện
	$scope.loadDistricts = function () {
		const provinceCode = $scope.selectedProvince;
		$scope.districts = [];
		$scope.wards = [];

		if (provinceCode) {
			$http.get(`https://provinces.open-api.vn/api/p/${provinceCode}?depth=2`).then(function (response) {
			$scope.districts = response.data.districts;
			});
		}
	};
	


  // Khi chọn Quận/Huyện, tải Phường/Xã
	$scope.loadWards = function () {
		const districtCode = $scope.selectedDistrict;
		$scope.wards = [];

		if (districtCode) {
			$http.get(`https://provinces.open-api.vn/api/d/${districtCode}?depth=2`).then(function (response) {
				$scope.wards = response.data.wards;
			});
		}
	};

	// Tạo địa chỉ đầy đủ và gán vào order.address
	$scope.generateFullAddress = function() {
		const houseNumber = $scope.houseNumber;
		console.log($scope.wards);
		console.log($scope.selectedWard);
		const ward1 = $scope.wards.find(w => w.code === parseInt($scope.selectedWard))?.name;
		console.log(ward1)
		const district = $scope.districts.find(d => d.code === parseInt($scope.selectedDistrict))?.name || '';
		const province = $scope.provinces.find(p => p.code === parseInt($scope.selectedProvince))?.name || '';

		$scope.order.address = `${houseNumber}, ${ward1}, ${district}, ${province}`;
	};

	$scope.order = {
		createDate: new Date(),
		address: "",
		account: { username: $("#username").text() }, get orderDetails() {
		return $scope.cart.items.map((item) => {
			return {
				product: { id: item.id },
				price: item.price,
				quantity: item.qty,
			};
		});
		},
		purchase() {
			var order = angular.copy(this);
			$http.post("/rest/order", order).then((resp) => {
				alert("Đặt hàng thành công");
				$scope.cart.clear();
				location.href = "/order/detail/" + resp.data.id;
			}).catch((error) => {
				alert("Đặt hàng lỗi!");
				console.log(error);
			});
		},
	};
});
