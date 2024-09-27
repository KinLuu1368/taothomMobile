app = angular.module("admin-app",["ngRoute"]);

app.config(function ($routeProvider){
	$routeProvider
	.when("/category",{
		templateUrl:"/assets/admin/category/category.html",
		controller:"category-ctrl"
	})
	.when("/model",{
		templateUrl:"/assets/admin/model/model.html",
		controller:"model-ctrl"
	})
	.when("/product",{
		templateUrl:"/assets/admin/product/index.html",
		controller:"product-ctrl"
	})
	.when("/authorize",{
		templateUrl:"/assets/admin/authority/index.html",
		controller:"authority-ctrl"
	})
	.when("/unthorized",{
		templateUrl:"/assets/admin/authority/unauthorized.html",
		controller:"authority-ctrl"
	})
	.when("/order", {
		templateUrl:"/assets/admin/order/order.html",
		controller:"order-ctrl"
	})
	.when("/statistic", {
		templateUrl:"/assets/admin/order/statistic.html",
		controller:"statistic-ctrl"
	})
	.otherwise({
		template:"<h1 class='text-center'>TaoThomStore Administation</h1>"
	});
})