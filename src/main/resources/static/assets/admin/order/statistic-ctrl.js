app.controller("statistic-ctrl", function($scope, $http) {
    $scope.orders = [];
    $scope.orderdetails = [];
    $scope.cates = [];
    $scope.form = {};
    $scope.revenue = 0;
    $scope.monthlyRevenues = Array(12).fill(0); // Mảng doanh thu của từng tháng từ 0-11
    $scope.monthlyRevenue = 0;

    $scope.initialize = function() {
        // Tổng số lượng đơn hàng
        $http.get("/rest/order").then(resp => {
            $scope.ordercount = resp.data.length;
            $scope.orders = resp.data;  // Lưu dữ liệu đơn hàng để tính doanh thu theo tháng
            $scope.calculateCurrentMonthRevenue(); // Tính doanh thu theo tháng
        });

        // Tổng số lượng tài khoản
        $http.get("/rest/accounts").then(resp => {
            $scope.accountscount = resp.data.length;
        });

        // Thông tin chi tiết đơn hàng
        $http.get("/rest/order/orderdetail").then(resp => {
            $scope.orderdetails = resp.data;
            $scope.revenue = $scope.calculateTotalPrice();
        });
    };

    // Tính tổng doanh thu
    $scope.calculateTotalPrice = function() {
        return $scope.orderdetails.reduce((total, detail) => total + (detail.price * detail.quantity), 0);
    };

    // Tính số đơn doanh thu tháng hiện tại
    $scope.calculateCurrentMonthRevenue = function() {
        // Lấy tháng hiện tại
        const currentMonth = new Date().getMonth(); // Tháng từ 0 đến 11
        console.log(currentMonth);
        $scope.currentMonthRevenue = 0; // Khởi tạo lại doanh thu tháng hiện tại

        // Duyệt qua tất cả các đơn hàng
        $scope.orders.forEach(order => {
            const orderDate = new Date(order.createDate);
            const orderMonth = orderDate.getMonth();
            console.log(orderMonth);
            if (orderMonth === currentMonth) { 
                $scope.monthlyRevenue += 1;
            }
        });
        $scope.monthlyRevenues[10] = 21900000;
        $scope.monthlyRevenues[11] = 73200000;
    };

    $scope.initialize();
});
