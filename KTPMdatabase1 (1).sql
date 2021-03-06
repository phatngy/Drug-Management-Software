USE [master]
GO
/****** Object:  Database [KTPMdatabase1]    Script Date: 12/18/2019 11:03:48 PM ******/
CREATE DATABASE [KTPMdatabase1]
 CONTAINMENT = NONE
 ON  PRIMARY 
( NAME = N'KTPMdatabase1', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL15.MSSQLSERVER\MSSQL\DATA\KTPMdatabase1.mdf' , SIZE = 8192KB , MAXSIZE = UNLIMITED, FILEGROWTH = 65536KB )
 LOG ON 
( NAME = N'KTPMdatabase1_log', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL15.MSSQLSERVER\MSSQL\DATA\KTPMdatabase1_log.ldf' , SIZE = 8192KB , MAXSIZE = 2048GB , FILEGROWTH = 65536KB )
 WITH CATALOG_COLLATION = DATABASE_DEFAULT
GO
ALTER DATABASE [KTPMdatabase1] SET COMPATIBILITY_LEVEL = 150
GO
IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [KTPMdatabase1].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO
ALTER DATABASE [KTPMdatabase1] SET ANSI_NULL_DEFAULT OFF 
GO
ALTER DATABASE [KTPMdatabase1] SET ANSI_NULLS OFF 
GO
ALTER DATABASE [KTPMdatabase1] SET ANSI_PADDING OFF 
GO
ALTER DATABASE [KTPMdatabase1] SET ANSI_WARNINGS OFF 
GO
ALTER DATABASE [KTPMdatabase1] SET ARITHABORT OFF 
GO
ALTER DATABASE [KTPMdatabase1] SET AUTO_CLOSE OFF 
GO
ALTER DATABASE [KTPMdatabase1] SET AUTO_SHRINK OFF 
GO
ALTER DATABASE [KTPMdatabase1] SET AUTO_UPDATE_STATISTICS ON 
GO
ALTER DATABASE [KTPMdatabase1] SET CURSOR_CLOSE_ON_COMMIT OFF 
GO
ALTER DATABASE [KTPMdatabase1] SET CURSOR_DEFAULT  GLOBAL 
GO
ALTER DATABASE [KTPMdatabase1] SET CONCAT_NULL_YIELDS_NULL OFF 
GO
ALTER DATABASE [KTPMdatabase1] SET NUMERIC_ROUNDABORT OFF 
GO
ALTER DATABASE [KTPMdatabase1] SET QUOTED_IDENTIFIER OFF 
GO
ALTER DATABASE [KTPMdatabase1] SET RECURSIVE_TRIGGERS OFF 
GO
ALTER DATABASE [KTPMdatabase1] SET  ENABLE_BROKER 
GO
ALTER DATABASE [KTPMdatabase1] SET AUTO_UPDATE_STATISTICS_ASYNC OFF 
GO
ALTER DATABASE [KTPMdatabase1] SET DATE_CORRELATION_OPTIMIZATION OFF 
GO
ALTER DATABASE [KTPMdatabase1] SET TRUSTWORTHY OFF 
GO
ALTER DATABASE [KTPMdatabase1] SET ALLOW_SNAPSHOT_ISOLATION OFF 
GO
ALTER DATABASE [KTPMdatabase1] SET PARAMETERIZATION SIMPLE 
GO
ALTER DATABASE [KTPMdatabase1] SET READ_COMMITTED_SNAPSHOT OFF 
GO
ALTER DATABASE [KTPMdatabase1] SET HONOR_BROKER_PRIORITY OFF 
GO
ALTER DATABASE [KTPMdatabase1] SET RECOVERY FULL 
GO
ALTER DATABASE [KTPMdatabase1] SET  MULTI_USER 
GO
ALTER DATABASE [KTPMdatabase1] SET PAGE_VERIFY CHECKSUM  
GO
ALTER DATABASE [KTPMdatabase1] SET DB_CHAINING OFF 
GO
ALTER DATABASE [KTPMdatabase1] SET FILESTREAM( NON_TRANSACTED_ACCESS = OFF ) 
GO
ALTER DATABASE [KTPMdatabase1] SET TARGET_RECOVERY_TIME = 60 SECONDS 
GO
ALTER DATABASE [KTPMdatabase1] SET DELAYED_DURABILITY = DISABLED 
GO
EXEC sys.sp_db_vardecimal_storage_format N'KTPMdatabase1', N'ON'
GO
ALTER DATABASE [KTPMdatabase1] SET QUERY_STORE = OFF
GO
USE [KTPMdatabase1]
GO
/****** Object:  Table [dbo].[Bill]    Script Date: 12/18/2019 11:03:49 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Bill](
	[ID_Bill] [nvarchar](10) NOT NULL,
	[ID_NV] [nvarchar](10) NULL,
	[Total] [nvarchar](50) NULL,
	[Time] [nvarchar](30) NULL,
	[Date] [date] NULL,
PRIMARY KEY CLUSTERED 
(
	[ID_Bill] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[BillDetail]    Script Date: 12/18/2019 11:03:49 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[BillDetail](
	[ID_Bill] [nvarchar](10) NOT NULL,
	[ID_Drug] [nvarchar](10) NOT NULL,
	[Amount] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[ID_Bill] ASC,
	[ID_Drug] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Drug]    Script Date: 12/18/2019 11:03:49 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Drug](
	[ID_Drug] [nvarchar](10) NOT NULL,
	[NameDrug] [nvarchar](20) NULL,
	[MainComponent] [nvarchar](10) NULL,
	[Expiry] [date] NULL,
	[Quantity] [int] NULL,
	[Unit] [nvarchar](10) NULL,
	[Price] [nvarchar](100) NULL,
PRIMARY KEY CLUSTERED 
(
	[ID_Drug] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Employee]    Script Date: 12/18/2019 11:03:49 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Employee](
	[ID_NV] [nvarchar](10) NOT NULL,
	[FullName] [nvarchar](30) NULL,
	[Address] [nvarchar](30) NULL,
	[Phone] [nvarchar](20) NULL,
	[Birthday] [date] NULL,
	[Sex] [nvarchar](10) NULL,
	[Mail] [nvarchar](30) NULL,
	[Position] [nvarchar](30) NULL,
	[Payroll] [nvarchar](50) NULL,
	[Level] [int] NULL,
	[UserName] [nvarchar](50) NULL,
	[PassWord] [nvarchar](30) NULL,
PRIMARY KEY CLUSTERED 
(
	[ID_NV] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[MainComponent]    Script Date: 12/18/2019 11:03:49 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[MainComponent](
	[ID_Component] [nvarchar](10) NOT NULL,
	[NameMainComponent] [nvarchar](50) NULL,
	[virtue] [nvarchar](50) NULL,
PRIMARY KEY CLUSTERED 
(
	[ID_Component] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Position]    Script Date: 12/18/2019 11:03:49 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Position](
	[Position] [nvarchar](30) NOT NULL,
	[Payroll] [nvarchar](50) NULL,
PRIMARY KEY CLUSTERED 
(
	[Position] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
INSERT [dbo].[Bill] ([ID_Bill], [ID_NV], [Total], [Time], [Date]) VALUES (N'11', N'1', N'213,000', N'22:46:50', CAST(N'2019-12-18' AS Date))
INSERT [dbo].[Bill] ([ID_Bill], [ID_NV], [Total], [Time], [Date]) VALUES (N'12', N'6', N'588,000', N'22:37:47', CAST(N'2019-12-18' AS Date))
INSERT [dbo].[Bill] ([ID_Bill], [ID_NV], [Total], [Time], [Date]) VALUES (N'123', N'1', N'120,000,000', N'19:48:11', CAST(N'2019-12-18' AS Date))
INSERT [dbo].[Bill] ([ID_Bill], [ID_NV], [Total], [Time], [Date]) VALUES (N'12321', N'6', N'345,000', N'21:12:11', CAST(N'2019-12-18' AS Date))
INSERT [dbo].[Bill] ([ID_Bill], [ID_NV], [Total], [Time], [Date]) VALUES (N'1234', N'6', N'117,000', N'20:51:02', CAST(N'2019-12-18' AS Date))
INSERT [dbo].[Bill] ([ID_Bill], [ID_NV], [Total], [Time], [Date]) VALUES (N'124', N'6', N'70,000', N'20:49:05', CAST(N'2019-12-18' AS Date))
INSERT [dbo].[Drug] ([ID_Drug], [NameDrug], [MainComponent], [Expiry], [Quantity], [Unit], [Price]) VALUES (N'1', N'Ambroco', N'1', CAST(N'2020-09-17' AS Date), 5, N'lọ', N'37000')
INSERT [dbo].[Drug] ([ID_Drug], [NameDrug], [MainComponent], [Expiry], [Quantity], [Unit], [Price]) VALUES (N'10', N'Decolgen', N'1', CAST(N'2021-11-12' AS Date), 50, N'viên', N'38000')
INSERT [dbo].[Drug] ([ID_Drug], [NameDrug], [MainComponent], [Expiry], [Quantity], [Unit], [Price]) VALUES (N'11', N'Tiffy vỉ', N'8', CAST(N'2021-11-12' AS Date), 50, N'lọ', N'31000')
INSERT [dbo].[Drug] ([ID_Drug], [NameDrug], [MainComponent], [Expiry], [Quantity], [Unit], [Price]) VALUES (N'2', N'Bisovol', N'3', CAST(N'2021-03-09' AS Date), 50, N'lọ', N'30000')
INSERT [dbo].[Drug] ([ID_Drug], [NameDrug], [MainComponent], [Expiry], [Quantity], [Unit], [Price]) VALUES (N'3', N'Coje ho', N'2', CAST(N'2020-08-07' AS Date), 50, N'vỉ', N'35000')
INSERT [dbo].[Drug] ([ID_Drug], [NameDrug], [MainComponent], [Expiry], [Quantity], [Unit], [Price]) VALUES (N'4', N'Dotusal', N'1', CAST(N'2021-07-06' AS Date), 50, N'vỉ', N'31000')
INSERT [dbo].[Drug] ([ID_Drug], [NameDrug], [MainComponent], [Expiry], [Quantity], [Unit], [Price]) VALUES (N'6', N'Hapacol Extra', N'7', CAST(N'2021-08-07' AS Date), 50, N'viên', N'20500')
INSERT [dbo].[Drug] ([ID_Drug], [NameDrug], [MainComponent], [Expiry], [Quantity], [Unit], [Price]) VALUES (N'7', N'SKDOL cảm cúm', N'4', CAST(N'2021-06-17' AS Date), 50, N'lọ', N'39000')
INSERT [dbo].[Drug] ([ID_Drug], [NameDrug], [MainComponent], [Expiry], [Quantity], [Unit], [Price]) VALUES (N'8', N'AMEFLU', N'6', CAST(N'2021-08-11' AS Date), 50, N'lọ', N'27000')
INSERT [dbo].[Drug] ([ID_Drug], [NameDrug], [MainComponent], [Expiry], [Quantity], [Unit], [Price]) VALUES (N'9', N'Coldacmin', N'7', CAST(N'2021-07-08' AS Date), 50, N'vỉ', N'24000')
INSERT [dbo].[Drug] ([ID_Drug], [NameDrug], [MainComponent], [Expiry], [Quantity], [Unit], [Price]) VALUES (N'CM12', N'Clohidric', N'3', CAST(N'2019-12-31' AS Date), 23, N'vỉ', N'12,000')
INSERT [dbo].[Employee] ([ID_NV], [FullName], [Address], [Phone], [Birthday], [Sex], [Mail], [Position], [Payroll], [Level], [UserName], [PassWord]) VALUES (N'1', N'Nguyen Thi Tuoi', N'Ha Noi', N'0342675345', CAST(N'1994-05-07' AS Date), N'Nu', N'tuoi12345@gmail.com', N'NhanVien', N'7,000,000', 2, N'Titi97', N'1234')
INSERT [dbo].[Employee] ([ID_NV], [FullName], [Address], [Phone], [Birthday], [Sex], [Mail], [Position], [Payroll], [Level], [UserName], [PassWord]) VALUES (N'2', N'Nguyen Van Anh', N'Ha Noi', N'0342647481', CAST(N'1992-05-07' AS Date), N'Nu', N'anhanh@gmail.com', N'NhanVien', N'6,000,000', 3, N'nightmare', N'12')
INSERT [dbo].[Employee] ([ID_NV], [FullName], [Address], [Phone], [Birthday], [Sex], [Mail], [Position], [Payroll], [Level], [UserName], [PassWord]) VALUES (N'3', N'Nguyen Thi Tham', N'Ha Nam', N'0987653682', CAST(N'1993-09-09' AS Date), N'Nu', N'tham321@gmail.com', N'NhanVien', N'8,000,000', 3, N'Huong', N'abcd')
INSERT [dbo].[Employee] ([ID_NV], [FullName], [Address], [Phone], [Birthday], [Sex], [Mail], [Position], [Payroll], [Level], [UserName], [PassWord]) VALUES (N'4', N'Nguyen Trong Hoang', N'Bac Giang', N'0909876534', CAST(N'1992-09-07' AS Date), N'Nam', N'hoangbo@gmail.com', N'Quanly', N'1500000', 6, N'Thuythuy', N'12ab')
INSERT [dbo].[Employee] ([ID_NV], [FullName], [Address], [Phone], [Birthday], [Sex], [Mail], [Position], [Payroll], [Level], [UserName], [PassWord]) VALUES (N'5', N'Nguyen Ha Phuong', N'Thai Binh', N'0908007666', CAST(N'1991-02-09' AS Date), N'Nu', N'phuongcute@gmail.com', N'Quanly', N'1500000', 7, N'helo', N'987654321')
INSERT [dbo].[Employee] ([ID_NV], [FullName], [Address], [Phone], [Birthday], [Sex], [Mail], [Position], [Payroll], [Level], [UserName], [PassWord]) VALUES (N'6', N'Nguyen Van Phat', N'Nghe An', N'0987652435', CAST(N'1996-02-01' AS Date), N'Nam', N'phatnguyen@gmail.com', N'NhanVien', N'1500000', 3, N'Admin', N'123')
INSERT [dbo].[Employee] ([ID_NV], [FullName], [Address], [Phone], [Birthday], [Sex], [Mail], [Position], [Payroll], [Level], [UserName], [PassWord]) VALUES (N'7', N'Nguyen Thi Ty', N'Bac Ninh', N'0322111222', CAST(N'2019-12-19' AS Date), N'Nữ', N'ninh@gmail.com', N'NhanVien', N'15,000,000', 5, N'Ty', N'1')
INSERT [dbo].[Employee] ([ID_NV], [FullName], [Address], [Phone], [Birthday], [Sex], [Mail], [Position], [Payroll], [Level], [UserName], [PassWord]) VALUES (N'8', N'Nguyen Van Teo', N'Phu Tho', N'0222333444', CAST(N'1992-12-07' AS Date), N'Nam', N'teo@gmail.com', N'QuanLy', N'24,000,000', 4, N'Ty', N'1')
INSERT [dbo].[MainComponent] ([ID_Component], [NameMainComponent], [virtue]) VALUES (N'1', N'Ambroxol Hydrochloride', N'Ch?a ho')
INSERT [dbo].[MainComponent] ([ID_Component], [NameMainComponent], [virtue]) VALUES (N'2', N'Paracetamol', N'Gi?m dau')
INSERT [dbo].[MainComponent] ([ID_Component], [NameMainComponent], [virtue]) VALUES (N'3', N'Phenylephrin HCl', N'C?m cúm, dau d?u')
INSERT [dbo].[MainComponent] ([ID_Component], [NameMainComponent], [virtue]) VALUES (N'4', N'Bromhexine hydrochloride', N'Ðau d?u')
INSERT [dbo].[MainComponent] ([ID_Component], [NameMainComponent], [virtue]) VALUES (N'5', N'Dextromethorphan HBr', N'Gi?m dau, S?t')
INSERT [dbo].[MainComponent] ([ID_Component], [NameMainComponent], [virtue]) VALUES (N'6', N'Guaifenesin', N'Gi?m dau')
INSERT [dbo].[MainComponent] ([ID_Component], [NameMainComponent], [virtue]) VALUES (N'7', N'Acetaminophen', N'S?t')
INSERT [dbo].[MainComponent] ([ID_Component], [NameMainComponent], [virtue]) VALUES (N'8', N'Chlorpheniramin maleat', N'Ðau d?u, C?m cúm')
INSERT [dbo].[Position] ([Position], [Payroll]) VALUES (N'NhanVien', N'3000000')
INSERT [dbo].[Position] ([Position], [Payroll]) VALUES (N'QuanLy', N'6000000')
ALTER TABLE [dbo].[Bill]  WITH CHECK ADD  CONSTRAINT [FK_Bill_Employee] FOREIGN KEY([ID_NV])
REFERENCES [dbo].[Employee] ([ID_NV])
GO
ALTER TABLE [dbo].[Bill] CHECK CONSTRAINT [FK_Bill_Employee]
GO
ALTER TABLE [dbo].[BillDetail]  WITH CHECK ADD  CONSTRAINT [FK_BillDetail_Bill] FOREIGN KEY([ID_Bill])
REFERENCES [dbo].[Bill] ([ID_Bill])
GO
ALTER TABLE [dbo].[BillDetail] CHECK CONSTRAINT [FK_BillDetail_Bill]
GO
ALTER TABLE [dbo].[BillDetail]  WITH CHECK ADD  CONSTRAINT [FK_BillDetail_Drug] FOREIGN KEY([ID_Drug])
REFERENCES [dbo].[Drug] ([ID_Drug])
GO
ALTER TABLE [dbo].[BillDetail] CHECK CONSTRAINT [FK_BillDetail_Drug]
GO
ALTER TABLE [dbo].[Drug]  WITH CHECK ADD  CONSTRAINT [FK_Drug_MainComponent] FOREIGN KEY([MainComponent])
REFERENCES [dbo].[MainComponent] ([ID_Component])
GO
ALTER TABLE [dbo].[Drug] CHECK CONSTRAINT [FK_Drug_MainComponent]
GO
ALTER TABLE [dbo].[Employee]  WITH CHECK ADD  CONSTRAINT [FK_Employee_Position] FOREIGN KEY([Position])
REFERENCES [dbo].[Position] ([Position])
GO
ALTER TABLE [dbo].[Employee] CHECK CONSTRAINT [FK_Employee_Position]
GO
USE [master]
GO
ALTER DATABASE [KTPMdatabase1] SET  READ_WRITE 
GO
