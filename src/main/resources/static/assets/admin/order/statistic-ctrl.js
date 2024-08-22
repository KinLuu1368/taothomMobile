app.controller("statistic-ctrl", function($scope, $http) {
    $scope.orders =[]
    $scope.orderdetails =[]
    $scope.cates = []
    $scope.form = {}

    $scope.initialize = function() {
        $http.get("/rest/order").then(resp => {
            $scope.ordercount = resp.data.length;            
        });
        $http.get("/rest/accounts").then(resp => {
            $scope.accountscount = resp.data.length;
        });
        $http.get("/rest/order/orderdetail").then(resp => {
            $scope.orderdetails = resp.data;
            $scope.revenue = $scope.calculateTotalPrice();
            console.log($scope.revenue)
        });
    };

    $scope.calculateTotalPrice = function() {
        let total = 0;
        for(let i = 0; i < $scope.orderdetails.length; i++) {
            total += ($scope.orderdetails[i].price * $scope.orderdetails[i].quantity);
        }
        return total;
    };

    $scope.initialize();
});