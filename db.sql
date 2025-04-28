/****** Object:  Table [dbo].[AuditLogs]    Script Date: 2025/04/28 14:59:31 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[AuditLogs](
	[AuditID] [int] IDENTITY(1,1) NOT NULL,
	[UserID] [int] NULL,
	[Action] [nvarchar](50) NULL,
	[TableAffected] [nvarchar](100) NULL,
	[RecordID] [int] NULL,
	[ChangeData] [nvarchar](max) NULL,
	[CreatedAt] [datetime] NULL,
PRIMARY KEY CLUSTERED 
(
	[AuditID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Branches]    Script Date: 2025/04/28 14:59:31 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Branches](
	[BranchID] [int] IDENTITY(1,1) NOT NULL,
	[BranchName] [nvarchar](255) NOT NULL,
	[Address] [nvarchar](max) NULL,
	[PhoneNumber] [nvarchar](20) NULL,
	[ManagerName] [nvarchar](255) NULL,
	[CreatedAt] [datetime] NULL,
PRIMARY KEY CLUSTERED 
(
	[BranchID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[CakeCategories]    Script Date: 2025/04/28 14:59:31 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[CakeCategories](
	[CategoryID] [int] IDENTITY(1,1) NOT NULL,
	[CategoryName] [nvarchar](255) NOT NULL,
	[Description] [nvarchar](max) NULL,
	[CreatedAt] [datetime] NULL,
PRIMARY KEY CLUSTERED 
(
	[CategoryID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[CakeRecipes]    Script Date: 2025/04/28 14:59:31 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[CakeRecipes](
	[RecipeID] [int] IDENTITY(1,1) NOT NULL,
	[CakeID] [varchar](10) NULL,
	[IngredientID] [int] NULL,
	[QuantityNeeded] [float] NULL,
PRIMARY KEY CLUSTERED 
(
	[RecipeID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Cakes]    Script Date: 2025/04/28 14:59:31 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Cakes](
	[CakeID] [varchar](10) NOT NULL,
	[CakeName] [nvarchar](255) NOT NULL,
	[Description] [nvarchar](max) NULL,
	[Price] [decimal](18, 2) NULL,
	[ImageURL] [nvarchar](255) NULL,
	[CategoryID] [int] NULL,
	[Status] [nvarchar](50) NULL,
	[BranchID] [int] NULL,
	[CreatedAt] [datetime] NULL,
PRIMARY KEY CLUSTERED 
(
	[CakeID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Customers]    Script Date: 2025/04/28 14:59:31 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Customers](
	[CustomerID] [varchar](10) NOT NULL,
	[FullName] [nvarchar](255) NULL,
	[PhoneNumber] [nvarchar](20) NULL,
	[Email] [nvarchar](255) NULL,
	[Address] [nvarchar](max) NULL,
	[LoyaltyPoints] [int] NULL,
	[CreatedAt] [datetime] NULL,
PRIMARY KEY CLUSTERED 
(
	[CustomerID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Deliveries]    Script Date: 2025/04/28 14:59:31 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Deliveries](
	[DeliveryID] [int] IDENTITY(1,1) NOT NULL,
	[OrderID] [int] NULL,
	[DeliveryAddress] [nvarchar](max) NULL,
	[ShipperName] [nvarchar](255) NULL,
	[ShipperPhone] [nvarchar](20) NULL,
	[DeliveryStatus] [nvarchar](50) NULL,
	[CreatedAt] [datetime] NULL,
PRIMARY KEY CLUSTERED 
(
	[DeliveryID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Ingredients]    Script Date: 2025/04/28 14:59:31 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Ingredients](
	[IngredientID] [int] IDENTITY(1,1) NOT NULL,
	[IngredientName] [nvarchar](255) NOT NULL,
	[Unit] [nvarchar](50) NULL,
	[CurrentStock] [float] NULL,
	[PricePerUnit] [decimal](18, 2) NULL,
	[CreatedAt] [datetime] NULL,
PRIMARY KEY CLUSTERED 
(
	[IngredientID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[OrderDetails]    Script Date: 2025/04/28 14:59:31 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[OrderDetails](
	[OrderDetailID] [int] IDENTITY(1,1) NOT NULL,
	[OrderID] [int] NULL,
	[CakeID] [varchar](10) NULL,
	[Quantity] [int] NULL,
	[UnitPrice] [decimal](18, 2) NULL,
PRIMARY KEY CLUSTERED 
(
	[OrderDetailID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[OrderPromotions]    Script Date: 2025/04/28 14:59:31 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[OrderPromotions](
	[OrderPromotionID] [int] IDENTITY(1,1) NOT NULL,
	[OrderID] [int] NULL,
	[PromotionID] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[OrderPromotionID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Orders]    Script Date: 2025/04/28 14:59:31 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Orders](
	[OrderID] [int] IDENTITY(1,1) NOT NULL,
	[CustomerID] [varchar](10) NULL,
	[BranchID] [int] NULL,
	[TotalAmount] [decimal](18, 2) NULL,
	[OrderStatus] [nvarchar](50) NULL,
	[CreatedAt] [datetime] NULL,
PRIMARY KEY CLUSTERED 
(
	[OrderID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Permissions]    Script Date: 2025/04/28 14:59:31 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Permissions](
	[PermissionID] [int] IDENTITY(1,1) NOT NULL,
	[RoleID] [int] NULL,
	[PermissionName] [nvarchar](255) NULL,
	[IsAllowed] [bit] NULL,
PRIMARY KEY CLUSTERED 
(
	[PermissionID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Promotions]    Script Date: 2025/04/28 14:59:31 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Promotions](
	[PromotionID] [int] IDENTITY(1,1) NOT NULL,
	[PromotionName] [nvarchar](255) NULL,
	[DiscountPercent] [float] NULL,
	[StartDate] [date] NULL,
	[EndDate] [date] NULL,
PRIMARY KEY CLUSTERED 
(
	[PromotionID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Roles]    Script Date: 2025/04/28 14:59:31 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Roles](
	[RoleID] [int] IDENTITY(1,1) NOT NULL,
	[RoleName] [nvarchar](100) NULL,
	[Description] [nvarchar](max) NULL,
PRIMARY KEY CLUSTERED 
(
	[RoleID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Staffs]    Script Date: 2025/04/28 14:59:31 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Staffs](
	[StaffID] [int] IDENTITY(1,1) NOT NULL,
	[FullName] [nvarchar](255) NULL,
	[PhoneNumber] [nvarchar](20) NULL,
	[Email] [nvarchar](255) NULL,
	[Position] [nvarchar](100) NULL,
	[BranchID] [int] NULL,
	[CreatedAt] [datetime] NULL,
PRIMARY KEY CLUSTERED 
(
	[StaffID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[StockIn]    Script Date: 2025/04/28 14:59:31 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[StockIn](
	[StockInID] [int] IDENTITY(1,1) NOT NULL,
	[IngredientID] [int] NULL,
	[SupplierID] [int] NULL,
	[BranchID] [int] NULL,
	[Quantity] [float] NULL,
	[Price] [decimal](18, 2) NULL,
	[CreatedAt] [datetime] NULL,
PRIMARY KEY CLUSTERED 
(
	[StockInID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Suppliers]    Script Date: 2025/04/28 14:59:31 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Suppliers](
	[SupplierID] [int] IDENTITY(1,1) NOT NULL,
	[SupplierName] [nvarchar](255) NOT NULL,
	[Address] [nvarchar](max) NULL,
	[PhoneNumber] [nvarchar](20) NULL,
	[Email] [nvarchar](255) NULL,
	[CreatedAt] [datetime] NULL,
PRIMARY KEY CLUSTERED 
(
	[SupplierID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Users]    Script Date: 2025/04/28 14:59:31 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Users](
	[UserID] [int] IDENTITY(1,1) NOT NULL,
	[Username] [nvarchar](100) NULL,
	[PasswordHash] [nvarchar](255) NULL,
	[RoleID] [int] NULL,
	[StaffID] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[UserID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
SET IDENTITY_INSERT [dbo].[AuditLogs] ON 

INSERT [dbo].[AuditLogs] ([AuditID], [UserID], [Action], [TableAffected], [RecordID], [ChangeData], [CreatedAt]) VALUES (1, 1, N'Create', N'Cakes', 1, N'{"CakeName": "Banh Kem Dau", "Price": 250000}', CAST(N'2025-04-28T09:01:39.510' AS DateTime))
INSERT [dbo].[AuditLogs] ([AuditID], [UserID], [Action], [TableAffected], [RecordID], [ChangeData], [CreatedAt]) VALUES (2, 2, N'Update', N'Orders', 2, N'{"OrderStatus": "Pending"}', CAST(N'2025-04-28T09:01:39.510' AS DateTime))
SET IDENTITY_INSERT [dbo].[AuditLogs] OFF
GO
SET IDENTITY_INSERT [dbo].[Branches] ON 

INSERT [dbo].[Branches] ([BranchID], [BranchName], [Address], [PhoneNumber], [ManagerName], [CreatedAt]) VALUES (1, N'Banh Ng?t Q1', N'123 Le L?i, Qu?n 1, TP.HCM', N'0909123456', N'Nguy?n V?n A', CAST(N'2025-04-28T09:06:00.267' AS DateTime))
INSERT [dbo].[Branches] ([BranchID], [BranchName], [Address], [PhoneNumber], [ManagerName], [CreatedAt]) VALUES (2, N'Banh Ng?t Q3', N'456 Vo V?n T?n, Qu?n 3, TP.HCM', N'0909234567', N'Tr?n Th? B', CAST(N'2025-04-28T09:06:00.267' AS DateTime))
INSERT [dbo].[Branches] ([BranchID], [BranchName], [Address], [PhoneNumber], [ManagerName], [CreatedAt]) VALUES (3, N'Banh Ng?t Binh Th?nh', N'789 ?i?n Bien Ph?, Binh Th?nh, TP.HCM', N'0909345678', N'Ph?m V?n C', CAST(N'2025-04-28T09:06:00.267' AS DateTime))
SET IDENTITY_INSERT [dbo].[Branches] OFF
GO
SET IDENTITY_INSERT [dbo].[CakeCategories] ON 

INSERT [dbo].[CakeCategories] ([CategoryID], [CategoryName], [Description], [CreatedAt]) VALUES (1, N'Banh Sinh Nh?t', N'Banh kem trang tri sinh nh?t', CAST(N'2025-04-28T08:59:06.840' AS DateTime))
INSERT [dbo].[CakeCategories] ([CategoryID], [CategoryName], [Description], [CreatedAt]) VALUES (2, N'Banh Mi Ng?t', N'Cac lo?i banh mi nhan ng?t', CAST(N'2025-04-28T08:59:06.840' AS DateTime))
INSERT [dbo].[CakeCategories] ([CategoryID], [CategoryName], [Description], [CreatedAt]) VALUES (3, N'Banh Pho Mai', N'Banh cheesecake cac lo?i', CAST(N'2025-04-28T08:59:06.840' AS DateTime))
SET IDENTITY_INSERT [dbo].[CakeCategories] OFF
GO
SET IDENTITY_INSERT [dbo].[CakeRecipes] ON 

INSERT [dbo].[CakeRecipes] ([RecipeID], [CakeID], [IngredientID], [QuantityNeeded]) VALUES (1, N'SP00000001', 1, 0.5)
INSERT [dbo].[CakeRecipes] ([RecipeID], [CakeID], [IngredientID], [QuantityNeeded]) VALUES (2, N'SP00000001', 2, 0.2)
INSERT [dbo].[CakeRecipes] ([RecipeID], [CakeID], [IngredientID], [QuantityNeeded]) VALUES (3, N'SP00000001', 3, 3)
INSERT [dbo].[CakeRecipes] ([RecipeID], [CakeID], [IngredientID], [QuantityNeeded]) VALUES (4, N'SP00000002', 1, 0.2)
INSERT [dbo].[CakeRecipes] ([RecipeID], [CakeID], [IngredientID], [QuantityNeeded]) VALUES (5, N'SP00000002', 2, 0.1)
INSERT [dbo].[CakeRecipes] ([RecipeID], [CakeID], [IngredientID], [QuantityNeeded]) VALUES (6, N'SP00000003', 1, 0.6)
INSERT [dbo].[CakeRecipes] ([RecipeID], [CakeID], [IngredientID], [QuantityNeeded]) VALUES (7, N'SP00000003', 4, 0.3)
SET IDENTITY_INSERT [dbo].[CakeRecipes] OFF
GO
INSERT [dbo].[Cakes] ([CakeID], [CakeName], [Description], [Price], [ImageURL], [CategoryID], [Status], [BranchID], [CreatedAt]) VALUES (N'SP00000001', N'Banh Kem Dau', N'Banh sinh nh?t kem t??i va dau tay', CAST(250000.00 AS Decimal(18, 2)), N'cake_dau.jpg', 1, N'Active', 1, CAST(N'2025-04-28T08:59:06.850' AS DateTime))
INSERT [dbo].[Cakes] ([CakeID], [CakeName], [Description], [Price], [ImageURL], [CategoryID], [Status], [BranchID], [CreatedAt]) VALUES (N'SP00000002', N'Banh Mi S?a', N'Banh mi m?m nhan s?a ng?t', CAST(30000.00 AS Decimal(18, 2)), N'bread_sua.jpg', 2, N'Active', 2, CAST(N'2025-04-28T08:59:06.850' AS DateTime))
INSERT [dbo].[Cakes] ([CakeID], [CakeName], [Description], [Price], [ImageURL], [CategoryID], [Status], [BranchID], [CreatedAt]) VALUES (N'SP00000003', N'Cheesecake Nh?t', N'Banh cheesecake x?p m?m ki?u Nh?t', CAST(450000.00 AS Decimal(18, 2)), N'cheesecake_nhat.jpg', 3, N'Active', 1, CAST(N'2025-04-28T08:59:06.850' AS DateTime))
GO
INSERT [dbo].[Customers] ([CustomerID], [FullName], [PhoneNumber], [Email], [Address], [LoyaltyPoints], [CreatedAt]) VALUES (N'KH00000001', N'Le Th? Hoa', N'0911222333', N'hoa.le@gmail.com', N'102 Nguy?n ?inh Chi?u, Q3', 120, CAST(N'2025-04-28T08:59:06.833' AS DateTime))
INSERT [dbo].[Customers] ([CustomerID], [FullName], [PhoneNumber], [Email], [Address], [LoyaltyPoints], [CreatedAt]) VALUES (N'KH00000002', N'Nguy?n Minh Tam', N'0933444555', N'tam.nguyen@gmail.com', N'59 Pasteur, Q1', 90, CAST(N'2025-04-28T08:59:06.833' AS DateTime))
INSERT [dbo].[Customers] ([CustomerID], [FullName], [PhoneNumber], [Email], [Address], [LoyaltyPoints], [CreatedAt]) VALUES (N'KH00000003', N'Ph?m V?n Hung', N'0988777666', N'hungpham@gmail.com', N'24 Tr??ng Chinh, Tan Binh', 200, CAST(N'2025-04-28T08:59:06.833' AS DateTime))
GO
SET IDENTITY_INSERT [dbo].[Deliveries] ON 

INSERT [dbo].[Deliveries] ([DeliveryID], [OrderID], [DeliveryAddress], [ShipperName], [ShipperPhone], [DeliveryStatus], [CreatedAt]) VALUES (1, 3, N'24 Tr??ng Chinh, Tan Binh', N'Tr?n Th? Lan', N'0902345678', N'Shipping', CAST(N'2025-04-28T09:01:12.007' AS DateTime))
SET IDENTITY_INSERT [dbo].[Deliveries] OFF
GO
SET IDENTITY_INSERT [dbo].[Ingredients] ON 

INSERT [dbo].[Ingredients] ([IngredientID], [IngredientName], [Unit], [CurrentStock], [PricePerUnit], [CreatedAt]) VALUES (1, N'B?t mi', N'kg', 100, CAST(20000.00 AS Decimal(18, 2)), CAST(N'2025-04-28T09:06:00.270' AS DateTime))
INSERT [dbo].[Ingredients] ([IngredientID], [IngredientName], [Unit], [CurrentStock], [PricePerUnit], [CreatedAt]) VALUES (2, N'???ng tr?ng', N'kg', 50, CAST(15000.00 AS Decimal(18, 2)), CAST(N'2025-04-28T09:06:00.270' AS DateTime))
INSERT [dbo].[Ingredients] ([IngredientID], [IngredientName], [Unit], [CurrentStock], [PricePerUnit], [CreatedAt]) VALUES (3, N'Tr?ng ga', N'qu?', 500, CAST(3000.00 AS Decimal(18, 2)), CAST(N'2025-04-28T09:06:00.270' AS DateTime))
INSERT [dbo].[Ingredients] ([IngredientID], [IngredientName], [Unit], [CurrentStock], [PricePerUnit], [CreatedAt]) VALUES (4, N'S?a t??i', N'lit', 30, CAST(25000.00 AS Decimal(18, 2)), CAST(N'2025-04-28T09:06:00.270' AS DateTime))
INSERT [dbo].[Ingredients] ([IngredientID], [IngredientName], [Unit], [CurrentStock], [PricePerUnit], [CreatedAt]) VALUES (5, N'B? l?t', N'kg', 20, CAST(120000.00 AS Decimal(18, 2)), CAST(N'2025-04-28T09:06:00.270' AS DateTime))
SET IDENTITY_INSERT [dbo].[Ingredients] OFF
GO
SET IDENTITY_INSERT [dbo].[OrderDetails] ON 

INSERT [dbo].[OrderDetails] ([OrderDetailID], [OrderID], [CakeID], [Quantity], [UnitPrice]) VALUES (1, 1, N'SP00000001', 1, CAST(250000.00 AS Decimal(18, 2)))
INSERT [dbo].[OrderDetails] ([OrderDetailID], [OrderID], [CakeID], [Quantity], [UnitPrice]) VALUES (2, 1, N'SP00000002', 1, CAST(30000.00 AS Decimal(18, 2)))
INSERT [dbo].[OrderDetails] ([OrderDetailID], [OrderID], [CakeID], [Quantity], [UnitPrice]) VALUES (3, 2, N'SP00000002', 1, CAST(30000.00 AS Decimal(18, 2)))
INSERT [dbo].[OrderDetails] ([OrderDetailID], [OrderID], [CakeID], [Quantity], [UnitPrice]) VALUES (4, 3, N'SP00000003', 1, CAST(450000.00 AS Decimal(18, 2)))
SET IDENTITY_INSERT [dbo].[OrderDetails] OFF
GO
SET IDENTITY_INSERT [dbo].[OrderPromotions] ON 

INSERT [dbo].[OrderPromotions] ([OrderPromotionID], [OrderID], [PromotionID]) VALUES (1, 1, 1)
INSERT [dbo].[OrderPromotions] ([OrderPromotionID], [OrderID], [PromotionID]) VALUES (2, 2, 2)
SET IDENTITY_INSERT [dbo].[OrderPromotions] OFF
GO
SET IDENTITY_INSERT [dbo].[Orders] ON 

INSERT [dbo].[Orders] ([OrderID], [CustomerID], [BranchID], [TotalAmount], [OrderStatus], [CreatedAt]) VALUES (1, N'KH00000001', 1, CAST(280000.00 AS Decimal(18, 2)), N'Completed', CAST(N'2025-04-28T08:59:06.857' AS DateTime))
INSERT [dbo].[Orders] ([OrderID], [CustomerID], [BranchID], [TotalAmount], [OrderStatus], [CreatedAt]) VALUES (2, N'KH00000002', 2, CAST(30000.00 AS Decimal(18, 2)), N'Pending', CAST(N'2025-04-28T08:59:06.857' AS DateTime))
INSERT [dbo].[Orders] ([OrderID], [CustomerID], [BranchID], [TotalAmount], [OrderStatus], [CreatedAt]) VALUES (3, N'KH00000003', 1, CAST(450000.00 AS Decimal(18, 2)), N'Shipping', CAST(N'2025-04-28T08:59:06.857' AS DateTime))
SET IDENTITY_INSERT [dbo].[Orders] OFF
GO
SET IDENTITY_INSERT [dbo].[Permissions] ON 

INSERT [dbo].[Permissions] ([PermissionID], [RoleID], [PermissionName], [IsAllowed]) VALUES (1, 1, N'Manage_Users', 1)
INSERT [dbo].[Permissions] ([PermissionID], [RoleID], [PermissionName], [IsAllowed]) VALUES (2, 1, N'View_Orders', 1)
INSERT [dbo].[Permissions] ([PermissionID], [RoleID], [PermissionName], [IsAllowed]) VALUES (3, 2, N'View_Orders', 1)
INSERT [dbo].[Permissions] ([PermissionID], [RoleID], [PermissionName], [IsAllowed]) VALUES (4, 2, N'Create_Order', 1)
INSERT [dbo].[Permissions] ([PermissionID], [RoleID], [PermissionName], [IsAllowed]) VALUES (5, 3, N'View_Deliveries', 1)
SET IDENTITY_INSERT [dbo].[Permissions] OFF
GO
SET IDENTITY_INSERT [dbo].[Promotions] ON 

INSERT [dbo].[Promotions] ([PromotionID], [PromotionName], [DiscountPercent], [StartDate], [EndDate]) VALUES (1, N'Gi?m 10% Sinh Nh?t', 10, CAST(N'2025-04-01' AS Date), CAST(N'2025-04-30' AS Date))
INSERT [dbo].[Promotions] ([PromotionID], [PromotionName], [DiscountPercent], [StartDate], [EndDate]) VALUES (2, N'Flash Sale 20%', 20, CAST(N'2025-04-15' AS Date), CAST(N'2025-04-15' AS Date))
SET IDENTITY_INSERT [dbo].[Promotions] OFF
GO
SET IDENTITY_INSERT [dbo].[Roles] ON 

INSERT [dbo].[Roles] ([RoleID], [RoleName], [Description]) VALUES (1, N'Admin', N'Qu?n tr? h? th?ng')
INSERT [dbo].[Roles] ([RoleID], [RoleName], [Description]) VALUES (2, N'Cashier', N'Thu ngan ban hang')
INSERT [dbo].[Roles] ([RoleID], [RoleName], [Description]) VALUES (3, N'Shipper', N'Giao hang')
SET IDENTITY_INSERT [dbo].[Roles] OFF
GO
SET IDENTITY_INSERT [dbo].[Staffs] ON 

INSERT [dbo].[Staffs] ([StaffID], [FullName], [PhoneNumber], [Email], [Position], [BranchID], [CreatedAt]) VALUES (1, N'Nguy?n V?n Hung', N'0901234567', N'hungnv@bakery.vn', N'Cashier', 1, CAST(N'2025-04-28T09:00:25.013' AS DateTime))
INSERT [dbo].[Staffs] ([StaffID], [FullName], [PhoneNumber], [Email], [Position], [BranchID], [CreatedAt]) VALUES (2, N'Tr?n Th? Lan', N'0902345678', N'lantran@bakery.vn', N'Shipper', 2, CAST(N'2025-04-28T09:00:25.013' AS DateTime))
INSERT [dbo].[Staffs] ([StaffID], [FullName], [PhoneNumber], [Email], [Position], [BranchID], [CreatedAt]) VALUES (3, N'Le Qu?c B?o', N'0903456789', N'baole@bakery.vn', N'Manager', 1, CAST(N'2025-04-28T09:00:25.013' AS DateTime))
SET IDENTITY_INSERT [dbo].[Staffs] OFF
GO
SET IDENTITY_INSERT [dbo].[StockIn] ON 

INSERT [dbo].[StockIn] ([StockInID], [IngredientID], [SupplierID], [BranchID], [Quantity], [Price], [CreatedAt]) VALUES (1, 1, 1, 1, 50, CAST(1000000.00 AS Decimal(18, 2)), CAST(N'2025-04-28T08:59:57.160' AS DateTime))
INSERT [dbo].[StockIn] ([StockInID], [IngredientID], [SupplierID], [BranchID], [Quantity], [Price], [CreatedAt]) VALUES (2, 2, 1, 1, 20, CAST(300000.00 AS Decimal(18, 2)), CAST(N'2025-04-28T08:59:57.160' AS DateTime))
INSERT [dbo].[StockIn] ([StockInID], [IngredientID], [SupplierID], [BranchID], [Quantity], [Price], [CreatedAt]) VALUES (3, 3, 2, 2, 300, CAST(900000.00 AS Decimal(18, 2)), CAST(N'2025-04-28T08:59:57.160' AS DateTime))
INSERT [dbo].[StockIn] ([StockInID], [IngredientID], [SupplierID], [BranchID], [Quantity], [Price], [CreatedAt]) VALUES (4, 4, 1, 3, 10, CAST(250000.00 AS Decimal(18, 2)), CAST(N'2025-04-28T08:59:57.160' AS DateTime))
INSERT [dbo].[StockIn] ([StockInID], [IngredientID], [SupplierID], [BranchID], [Quantity], [Price], [CreatedAt]) VALUES (5, 5, 1, 1, 5, CAST(600000.00 AS Decimal(18, 2)), CAST(N'2025-04-28T08:59:57.160' AS DateTime))
SET IDENTITY_INSERT [dbo].[StockIn] OFF
GO
SET IDENTITY_INSERT [dbo].[Suppliers] ON 

INSERT [dbo].[Suppliers] ([SupplierID], [SupplierName], [Address], [PhoneNumber], [Email], [CreatedAt]) VALUES (1, N'Cong ty B?t Mi ABC', N'12 Nguy?n Trai, Qu?n 5, TP.HCM', N'0281234567', N'contact@abcflour.vn', CAST(N'2025-04-28T08:59:25.200' AS DateTime))
INSERT [dbo].[Suppliers] ([SupplierID], [SupplierName], [Address], [PhoneNumber], [Email], [CreatedAt]) VALUES (2, N'Nha cung c?p Tr?ng XYZ', N'98 Ly Th??ng Ki?t, Qu?n 10, TP.HCM', N'0282345678', N'sales@xyzegg.vn', CAST(N'2025-04-28T08:59:25.200' AS DateTime))
SET IDENTITY_INSERT [dbo].[Suppliers] OFF
GO
SET IDENTITY_INSERT [dbo].[Users] ON 

INSERT [dbo].[Users] ([UserID], [Username], [PasswordHash], [RoleID], [StaffID]) VALUES (1, N'admin', N'hashedpassword123', 1, 3)
INSERT [dbo].[Users] ([UserID], [Username], [PasswordHash], [RoleID], [StaffID]) VALUES (2, N'cashier1', N'hashedpassword456', 2, 1)
INSERT [dbo].[Users] ([UserID], [Username], [PasswordHash], [RoleID], [StaffID]) VALUES (3, N'shipper1', N'hashedpassword789', 3, 2)
SET IDENTITY_INSERT [dbo].[Users] OFF
GO
ALTER TABLE [dbo].[AuditLogs] ADD  DEFAULT (getdate()) FOR [CreatedAt]
GO
ALTER TABLE [dbo].[Branches] ADD  DEFAULT (getdate()) FOR [CreatedAt]
GO
ALTER TABLE [dbo].[CakeCategories] ADD  DEFAULT (getdate()) FOR [CreatedAt]
GO
ALTER TABLE [dbo].[Cakes] ADD  DEFAULT (getdate()) FOR [CreatedAt]
GO
ALTER TABLE [dbo].[Customers] ADD  DEFAULT ((0)) FOR [LoyaltyPoints]
GO
ALTER TABLE [dbo].[Customers] ADD  DEFAULT (getdate()) FOR [CreatedAt]
GO
ALTER TABLE [dbo].[Deliveries] ADD  DEFAULT (getdate()) FOR [CreatedAt]
GO
ALTER TABLE [dbo].[Ingredients] ADD  DEFAULT (getdate()) FOR [CreatedAt]
GO
ALTER TABLE [dbo].[Orders] ADD  DEFAULT (getdate()) FOR [CreatedAt]
GO
ALTER TABLE [dbo].[Staffs] ADD  DEFAULT (getdate()) FOR [CreatedAt]
GO
ALTER TABLE [dbo].[StockIn] ADD  DEFAULT (getdate()) FOR [CreatedAt]
GO
ALTER TABLE [dbo].[Suppliers] ADD  DEFAULT (getdate()) FOR [CreatedAt]
GO
