-- Xóa cơ sở dữ liệu cũ nếu tồn tại
DROP DATABASE IF EXISTS TaoThomStore2;

-- Tạo mới cơ sở dữ liệu
CREATE DATABASE TaoThomStore2;
GO

-- Chọn cơ sở dữ liệu vừa tạo
USE TaoThomStore2;
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
INSERT INTO Authorities (Username, RoleId)
VALUES ('admin', 'DIRE');

-- Tạo bảng Categories (quản lý danh mục sản phẩm)
CREATE TABLE Categories (
    Id CHAR(4) NOT NULL PRIMARY KEY,
    Name NVARCHAR(50) NOT NULL
);
INSERT INTO Categories (Id, Name) VALUES 
    ('1001', 'iPhone 13 Series'),
    ('1002', 'iPhone 14 Series'),
    ('1003', 'iPhone 15 Series'),
    ('1004', 'iPhone 16 Series');

-- Tạo bảng ProductModel (quản lý model sản phẩm)
CREATE TABLE ProductModel (
    Id INT IDENTITY(1,1) PRIMARY KEY,
    Name NVARCHAR(50) NOT NULL,
    Image NVARCHAR(50) DEFAULT 'ModelDefault.jpg'
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
    ProductCapacitiesId INT NOT NULL,
    Quantity INT DEFAULT 0
);

-- Tạo bảng Orders (quản lý đơn hàng)
CREATE TABLE Orders (
    Id BIGINT IDENTITY(1,1) PRIMARY KEY,
    Username NVARCHAR(50) NOT NULL,
	phonenumber NVARCHAR(15) NOT NULL,
    CreateDate DATETIME DEFAULT GETDATE(),
    Address NVARCHAR(100) NOT NULL
);

-- Tạo bảng OrderStatus (quản lý tình trạng đơn hàng) không sử dụng tự tăng
CREATE TABLE OrderStatus (
    Id INT PRIMARY KEY,
    Status NVARCHAR(50) NOT NULL -- Tên trạng thái (ví dụ: 'Đang xử lý', 'Đã giao', 'Đã hủy')
);

-- Thêm một số trạng thái mẫu với Id được chỉ định thủ công
INSERT INTO OrderStatus (Id, Status) VALUES 
(1, N'Đang xử lý'),
(2, N'Đã xử lý'),
(3, N'Đã hủy');

-- Cập nhật bảng Orders để thêm cột OrderStatusId
ALTER TABLE Orders
ADD OrderStatusId INT DEFAULT 1; -- Thiết lập mặc định là 'Đang xử lý'

-- Tạo bảng OrderDetails (chi tiết đơn hàng)
CREATE TABLE OrderDetails (
    Id BIGINT IDENTITY(1,1) PRIMARY KEY,
    OrderId BIGINT NOT NULL,
    ProductId INT NOT NULL,
    Price FLOAT DEFAULT 0,
    Quantity INT DEFAULT 1
);

-- -------------------------------------------------------
-- Tạo các khóa ngoại
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

-- Liên kết bảng OrderDetails với Orders, Products và Accounts
ALTER TABLE OrderDetails
ADD CONSTRAINT FK_OrderDetails_Orders FOREIGN KEY (OrderId) REFERENCES Orders(Id) ON DELETE NO ACTION;

ALTER TABLE OrderDetails
ADD CONSTRAINT FK_OrderDetails_Products FOREIGN KEY (ProductId) REFERENCES Products(Id) ON DELETE NO ACTION;

-- Thêm khóa ngoại liên kết Orders với OrderStatus
ALTER TABLE Orders
ADD CONSTRAINT FK_Orders_OrderStatus FOREIGN KEY (OrderStatusId) REFERENCES OrderStatus(Id) ON DELETE NO ACTION;

-- -------------------------------------------------------
-- Tạo các Trigger xử lý
-- -------------------------------------------------------

-- Trigger giảm số lượng sản phẩm khi thêm chi tiết đơn hàng
GO
CREATE TRIGGER trg_UpdateProductQuantity
ON OrderDetails
AFTER INSERT
AS
BEGIN
    UPDATE p
    SET p.Quantity = p.Quantity - i.Quantity
    FROM Products p
    INNER JOIN inserted i ON p.Id = i.ProductId
    WHERE p.Quantity >= i.Quantity; -- Đảm bảo kho đủ hàng
END;

-- Trigger khôi phục số lượng sản phẩm khi xóa chi tiết đơn hàng
GO
CREATE TRIGGER trg_RestoreProductQuantity
ON OrderDetails
AFTER DELETE
AS
BEGIN
    UPDATE p
    SET p.Quantity = p.Quantity + d.Quantity
    FROM Products p
    INNER JOIN deleted d ON p.Id = d.ProductId;
END;
GO
