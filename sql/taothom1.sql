-- Xóa cơ sở dữ liệu cũ nếu tồn tại
DROP DATABASE IF EXISTS TaoThomStore1;

-- Tạo mới cơ sở dữ liệu TaoThomStore1
CREATE DATABASE TaoThomStore1;
GO

-- Chọn cơ sở dữ liệu vừa tạo
USE TaoThomStore1;
GO

-- Tạo bảng Accounts (quản lý người dùng)
CREATE TABLE Accounts (
    Username NVARCHAR(50) NOT NULL PRIMARY KEY,
    Password NVARCHAR(50) NOT NULL,
    Fullname NVARCHAR(50) NOT NULL,
    Email NVARCHAR(50) NOT NULL,
    Photo NVARCHAR(50) DEFAULT 'default.png'
);
INSERT INTO Accounts (Username, Password, Fullname, Email)
VALUES ('admin', '123', 'Administrator', 'admin@example.com');

-- Tạo bảng Roles (quản lý vai trò)
CREATE TABLE Roles (
    Id NVARCHAR(10) NOT NULL PRIMARY KEY,
    Name NVARCHAR(50) NOT NULL
);
INSERT INTO Roles (Id, Name) values
	('DIRE', 'Director'),
	('STAF', 'Staff'),
	('CUST', 'Customer');

-- Tạo bảng Authorities (liên kết Accounts và Roles)
CREATE TABLE Authorities (
    Id INT IDENTITY(1,1) PRIMARY KEY,
    Username NVARCHAR(50) NOT NULL,
    RoleId NVARCHAR(10) NOT NULL
);
-- Thêm vai trò cho người dùng admin
INSERT INTO Authorities (Username, RoleId)
VALUES ('admin', 'DIRE');

-- Tạo bảng Categories (quản lý danh mục sản phẩm)
CREATE TABLE Categories (
    Id CHAR(4) NOT NULL PRIMARY KEY,
    Name NVARCHAR(50) NOT NULL
);
INSERT INTO Categories (id, name) VALUES 
(1001, 'iPhone 13 Series'),
(1002, 'iPhone 14 Series'),
(1003, 'iPhone 15 Series'),
(1004, 'iPhone 16 Series');


-- Tạo bảng ProductModel (quản lý model sản phẩm)
CREATE TABLE ProductModel (
    Id INT IDENTITY(1,1) PRIMARY KEY,
    Name NVARCHAR(50) NOT NULL, -- Tên model (VD: Pro, Max, Standard)
    Image NVARCHAR(50) DEFAULT 'ModelDefault.jpg', -- Hình ảnh đại diện
);

-- Tạo bảng ProductColors (quản lý màu sắc sản phẩm theo model)
CREATE TABLE ProductColors (
    Id INT IDENTITY(1,1) PRIMARY KEY,
    Color NVARCHAR(50) NOT NULL,
    ProductModelId INT NOT NULL
);

-- Tạo bảng ProductCapacities (quản lý dung lượng sản phẩm theo model)
CREATE TABLE ProductCapacities (
    Id INT IDENTITY(1,1) PRIMARY KEY,
    Capacity NVARCHAR(50) NOT NULL,
    ProductModelId INT NOT NULL
);

-- Tạo bảng Products (quản lý sản phẩm)
CREATE TABLE Products (
    Id INT IDENTITY(1,1) PRIMARY KEY,
    Image NVARCHAR(50) DEFAULT 'Product.gif',
    Price FLOAT DEFAULT 0,
    CreateDate DATE DEFAULT GETDATE(),
    Available BIT DEFAULT 1,
    CategoryId CHAR(4) NOT NULL,
    ProductModelId INT NOT NULL,
    ProductColorsId INT NOT NULL,
    ProductCapacitiesId INT NOT NULL
);

select * from Products

-- Tạo bảng Orders (quản lý đơn hàng)
CREATE TABLE Orders (
    Id BIGINT IDENTITY(1,1) PRIMARY KEY,
    Username NVARCHAR(50) NOT NULL,
    CreateDate DATETIME DEFAULT GETDATE(),
    Address NVARCHAR(100) NOT NULL
);

-- Tạo bảng OrderDetails (chi tiết đơn hàng)
CREATE TABLE OrderDetails (
    Id BIGINT IDENTITY(1,1) PRIMARY KEY,
    OrderId BIGINT NOT NULL,
    ProductId INT NOT NULL,
    Price FLOAT DEFAULT 0,
    Quantity INT DEFAULT 1,
    UserId NVARCHAR(50) NOT NULL
);

-- -------------------------------------------------------
-- Tạo các khóa ngoại bên ngoài sau khi các bảng đã tồn tại
-- -------------------------------------------------------

-- Liên kết bảng Authorities với Accounts và Roles
ALTER TABLE Authorities
ADD CONSTRAINT FK_Authorities_Accounts FOREIGN KEY (Username) REFERENCES Accounts (Username) ON DELETE CASCADE;

ALTER TABLE Authorities
ADD CONSTRAINT FK_Authorities_Roles FOREIGN KEY (RoleId) REFERENCES Roles (Id) ON DELETE NO ACTION;

-- Liên kết bảng Products với Categories, ProductModel, ProductColors và ProductCapacities
ALTER TABLE Products
ADD CONSTRAINT FK_Products_Categories FOREIGN KEY (CategoryId) REFERENCES Categories (Id) ON DELETE NO ACTION;

ALTER TABLE Products
ADD CONSTRAINT FK_Products_ProductModel FOREIGN KEY (ProductModelId) REFERENCES ProductModel(Id) ON DELETE NO ACTION;

ALTER TABLE Products
ADD CONSTRAINT FK_Products_ProductColor FOREIGN KEY (ProductColorsId) REFERENCES ProductColors(Id) ON DELETE NO ACTION;

ALTER TABLE Products
ADD CONSTRAINT FK_Products_ProductCapacity FOREIGN KEY (ProductCapacitiesId) REFERENCES ProductCapacities(Id) ON DELETE NO ACTION;

-- Liên kết bảng ProductColors với ProductModel
ALTER TABLE ProductColors
ADD CONSTRAINT FK_ProductColors_ProductModel FOREIGN KEY (ProductModelId) REFERENCES ProductModel(Id) ON DELETE NO ACTION;

-- Liên kết bảng ProductCapacities với ProductModel
ALTER TABLE ProductCapacities
ADD CONSTRAINT FK_ProductCapacities_ProductModel FOREIGN KEY (ProductModelId) REFERENCES ProductModel(Id) ON DELETE NO ACTION;

-- Liên kết bảng Orders với Accounts
ALTER TABLE Orders
ADD CONSTRAINT FK_Orders_Accounts FOREIGN KEY (Username) REFERENCES Accounts(Username) ON DELETE NO ACTION;

-- Liên kết bảng OrderDetails với Orders, Products và Accounts (người xử lý đơn hàng)
ALTER TABLE OrderDetails
ADD CONSTRAINT FK_OrderDetails_Orders FOREIGN KEY (OrderId) REFERENCES Orders(Id) ON DELETE NO ACTION;

ALTER TABLE OrderDetails
ADD CONSTRAINT FK_OrderDetails_Products FOREIGN KEY (ProductId) REFERENCES Products(Id) ON DELETE NO ACTION;

ALTER TABLE OrderDetails
ADD CONSTRAINT FK_OrderDetails_UserId FOREIGN KEY (UserId) REFERENCES Accounts(Username) ON DELETE NO ACTION;

