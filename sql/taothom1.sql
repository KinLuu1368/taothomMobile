-- Tạo Cơ sở dữ liệu TaoThomStore
drop DATABASE TaoThomStore1
CREATE DATABASE TaoThomStore1

-- Chọn cơ sở dữ liệu vừa tạo
USE TaoThomStore1;
GO

-- Tạo bảng Accounts
CREATE TABLE Accounts (
    Username NVARCHAR(50) NOT NULL PRIMARY KEY,
    Password NVARCHAR(50) NOT NULL,
    Fullname NVARCHAR(50) NOT NULL,
    Email NVARCHAR(50) NOT NULL,
    Photo NVARCHAR(50) DEFAULT 'default.png'
);

-- Tạo bảng Roles
CREATE TABLE Roles (
    Id NVARCHAR(10) NOT NULL PRIMARY KEY,
    Name NVARCHAR(50) NOT NULL
);

-- Tạo bảng Authorities (ràng buộc với Accounts và Roles)
CREATE TABLE Authorities (
    Id INT IDENTITY(1,1) PRIMARY KEY,
    Username NVARCHAR(50) NOT NULL,
    RoleId NVARCHAR(10) NOT NULL,
    CONSTRAINT FK_Authorities_Accounts FOREIGN KEY (Username) REFERENCES Accounts (Username) ON DELETE CASCADE,
    CONSTRAINT FK_Authorities_Roles FOREIGN KEY (RoleId) REFERENCES Roles (Id) ON DELETE CASCADE
);

-- Tạo bảng Categories
CREATE TABLE Categories (
    Id CHAR(4) NOT NULL PRIMARY KEY,
    Name NVARCHAR(50) NOT NULL
);

-- Tạo bảng Products (ràng buộc với Categories)
CREATE TABLE Products (
    Id INT IDENTITY(1,1) PRIMARY KEY,
    Name NVARCHAR(50) NOT NULL,
    Image NVARCHAR(50) DEFAULT 'Product.gif',
    Price FLOAT DEFAULT 0,
    CreateDate DATE DEFAULT GETDATE(),
    Available BIT DEFAULT 1,
    CategoryId CHAR(4) NOT NULL,
    CONSTRAINT FK_Products_Categories FOREIGN KEY (CategoryId) REFERENCES Categories (Id) ON DELETE CASCADE
);

-- Tạo bảng Orders (ràng buộc với Accounts)
CREATE TABLE Orders (
    Id BIGINT IDENTITY(1,1) PRIMARY KEY,
    Username NVARCHAR(50) NOT NULL,
    CreateDate DATETIME DEFAULT GETDATE(),
    Address NVARCHAR(100) NOT NULL,
    CONSTRAINT FK_Orders_Accounts FOREIGN KEY (Username) REFERENCES Accounts (Username) ON DELETE CASCADE
);

-- Tạo bảng OrderDetails (ràng buộc với Orders và Products)
CREATE TABLE OrderDetails (
    Id BIGINT IDENTITY(1,1) PRIMARY KEY,
    OrderId BIGINT NOT NULL,
    ProductId INT NOT NULL,
    Price FLOAT DEFAULT 0,
    Quantity INT DEFAULT 1,
    CONSTRAINT FK_OrderDetails_Orders FOREIGN KEY (OrderId) REFERENCES Orders (Id) ON DELETE CASCADE,
    CONSTRAINT FK_OrderDetails_Products FOREIGN KEY (ProductId) REFERENCES Products (Id) ON DELETE CASCADE
);

CREATE TABLE Series (
    Id INT IDENTITY(1,1) PRIMARY KEY,
    Name NVARCHAR(50) NOT NULL -- Tên của series sản phẩm (vd: iPhone 12 series)
);

-- Thêm cột SeriesId vào bảng Products để liên kết với bảng Series
ALTER TABLE Products
ADD SeriesId INT NOT NULL, -- Liên kết với bảng Series
    Color NVARCHAR(30) DEFAULT 'Default Color', -- Màu sắc sản phẩm
    Capacity NVARCHAR(30) DEFAULT 'Default Capacity'; -- Dung lượng sản phẩm

-- Tạo khóa ngoại giữa Products và Series
ALTER TABLE Products
ADD CONSTRAINT FK_Products_Series FOREIGN KEY (SeriesId) REFERENCES Series(Id) ON DELETE CASCADE;

-- Tạo bảng ProductModel
CREATE TABLE ProductModel (
    Id INT IDENTITY(1,1) PRIMARY KEY,
    Name NVARCHAR(50) NOT NULL, -- Mô hình của sản phẩm (vd: Pro, Max, Standard)
    Image NVARCHAR(50) DEFAULT 'ModelDefault.jpg', -- Hình ảnh đại diện cho model
	Price float
);

-- Thêm cột ProductModelId vào bảng Products để liên kết với bảng ProductModels
ALTER TABLE Products
ADD ProductModelId INT NULL;

-- Tạo khóa ngoại giữa Products và ProductModel
ALTER TABLE Products
ADD CONSTRAINT FK_Products_ProductModel FOREIGN KEY (ProductModelId) REFERENCES ProductModel(Id) ON DELETE CASCADE;

-- Xóa khóa ngoại giữa bảng Products và Series
ALTER TABLE Products
DROP CONSTRAINT FK_Products_Series;

-- Xóa cột SeriesId khỏi bảng Products
ALTER TABLE Products
DROP COLUMN SeriesId;

-- Thêm cột SeriesId vào bảng ProductModel để liên kết với bảng Series
ALTER TABLE ProductModel
ADD SeriesId INT NOT NULL;

-- Tạo khóa ngoại giữa ProductModel và Series
ALTER TABLE ProductModel
ADD CONSTRAINT FK_ProductModel_Series FOREIGN KEY (SeriesId) REFERENCES Series(Id) ON DELETE CASCADE;

-- Thêm mô tả cho các cột mới nếu cần
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'Mã Series sản phẩm', @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'ProductModel', @level2type=N'COLUMN',@level2name=N'SeriesId';


-- Thêm mô tả cho các cột mới nếu cần
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'Mã Model sản phẩm', @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'Products', @level2type=N'COLUMN',@level2name=N'ProductModelId';
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'Mô hình của sản phẩm', @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'ProductModels', @level2type=N'COLUMN',@level2name=N'Model';
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'Hình ảnh đại diện của mô hình sản phẩm', @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'ProductModels', @level2type=N'COLUMN',@level2name=N'Image';

GO

-- Thêm các mô tả cho các cột (nếu cần)
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'Mã khách hàng', @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'Accounts', @level2type=N'COLUMN',@level2name=N'Username';
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'Tên khách hàng', @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'Accounts', @level2type=N'COLUMN',@level2name=N'Fullname';
-- Thêm mô tả cho các cột mới
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'Mã Series sản phẩm', @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'Products', @level2type=N'COLUMN',@level2name=N'SeriesId';
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'Màu sắc của sản phẩm', @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'Products', @level2type=N'COLUMN',@level2name=N'Color';
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'Dung lượng của sản phẩm', @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'Products', @level2type=N'COLUMN',@level2name=N'Capacity';

-- Tiếp tục thêm các thuộc tính tương tự cho các cột khác nếu cần thiết
GO
