/****** Object:  Table [dbo].[ma_kengen_set]    Script Date: 2025/05/15 16:13:32 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[ma_kengen_set](
	[cd_kengen] [smallint] NOT NULL,
	[nm_kengen] [nvarchar](60) NOT NULL,
	[id_toroku] [decimal](10, 0) NOT NULL,
	[dt_toroku] [datetime] NOT NULL,
	[id_koshin] [decimal](10, 0) NOT NULL,
	[dt_koshin] [datetime] NOT NULL
) ON [PRIMARY]
GO
INSERT [dbo].[ma_kengen_set] ([cd_kengen], [nm_kengen], [id_toroku], [dt_toroku], [id_koshin], [dt_koshin]) VALUES (1, N'ＱＰ研究所', CAST(1 AS Decimal(10, 0)), CAST(N'2009-03-29T00:00:00.000' AS DateTime), CAST(9999999999 AS Decimal(10, 0)), CAST(N'2011-02-21T11:27:27.280' AS DateTime))
INSERT [dbo].[ma_kengen_set] ([cd_kengen], [nm_kengen], [id_toroku], [dt_toroku], [id_koshin], [dt_koshin]) VALUES (2, N'製造工場', CAST(1 AS Decimal(10, 0)), CAST(N'2010-07-27T17:57:29.483' AS DateTime), CAST(9999999999 AS Decimal(10, 0)), CAST(N'2011-05-10T14:35:49.287' AS DateTime))
INSERT [dbo].[ma_kengen_set] ([cd_kengen], [nm_kengen], [id_toroku], [dt_toroku], [id_koshin], [dt_koshin]) VALUES (3, N'生産管理部', CAST(9999999999 AS Decimal(10, 0)), CAST(N'2011-03-25T14:20:50.450' AS DateTime), CAST(9999999999 AS Decimal(10, 0)), CAST(N'2011-03-25T14:21:12.783' AS DateTime))
INSERT [dbo].[ma_kengen_set] ([cd_kengen], [nm_kengen], [id_toroku], [dt_toroku], [id_koshin], [dt_koshin]) VALUES (4, N'調達部', CAST(1 AS Decimal(10, 0)), CAST(N'2009-03-29T00:00:00.000' AS DateTime), CAST(9999999999 AS Decimal(10, 0)), CAST(N'2011-04-14T11:40:15.500' AS DateTime))
INSERT [dbo].[ma_kengen_set] ([cd_kengen], [nm_kengen], [id_toroku], [dt_toroku], [id_koshin], [dt_koshin]) VALUES (5, N'マスタ権限', CAST(1 AS Decimal(10, 0)), CAST(N'2009-03-29T00:00:00.000' AS DateTime), CAST(9999999999 AS Decimal(10, 0)), CAST(N'2009-05-25T21:38:48.710' AS DateTime))
INSERT [dbo].[ma_kengen_set] ([cd_kengen], [nm_kengen], [id_toroku], [dt_toroku], [id_koshin], [dt_koshin]) VALUES (6, N'システム管理者', CAST(1 AS Decimal(10, 0)), CAST(N'2009-04-07T00:00:00.000' AS DateTime), CAST(9999999999 AS Decimal(10, 0)), CAST(N'2011-02-15T17:21:07.333' AS DateTime))
INSERT [dbo].[ma_kengen_set] ([cd_kengen], [nm_kengen], [id_toroku], [dt_toroku], [id_koshin], [dt_koshin]) VALUES (40, N'製法委員', CAST(9999999999 AS Decimal(10, 0)), CAST(N'2009-06-10T15:35:03.843' AS DateTime), CAST(9999999999 AS Decimal(10, 0)), CAST(N'2011-03-29T14:25:32.020' AS DateTime))
INSERT [dbo].[ma_kengen_set] ([cd_kengen], [nm_kengen], [id_toroku], [dt_toroku], [id_koshin], [dt_koshin]) VALUES (45, N'営業（一般）', CAST(9999999999 AS Decimal(10, 0)), CAST(N'2011-02-15T15:32:55.993' AS DateTime), CAST(9999999999 AS Decimal(10, 0)), CAST(N'2011-02-15T15:32:55.993' AS DateTime))
INSERT [dbo].[ma_kengen_set] ([cd_kengen], [nm_kengen], [id_toroku], [dt_toroku], [id_koshin], [dt_koshin]) VALUES (46, N'営業（本部権限）', CAST(9999999999 AS Decimal(10, 0)), CAST(N'2011-02-15T15:34:55.787' AS DateTime), CAST(9999999999 AS Decimal(10, 0)), CAST(N'2011-02-15T15:34:55.787' AS DateTime))
INSERT [dbo].[ma_kengen_set] ([cd_kengen], [nm_kengen], [id_toroku], [dt_toroku], [id_koshin], [dt_koshin]) VALUES (47, N'営業（システム管理者）', CAST(9999999999 AS Decimal(10, 0)), CAST(N'2011-02-15T15:36:52.330' AS DateTime), CAST(9999999999 AS Decimal(10, 0)), CAST(N'2011-02-15T15:36:52.330' AS DateTime))
INSERT [dbo].[ma_kengen_set] ([cd_kengen], [nm_kengen], [id_toroku], [dt_toroku], [id_koshin], [dt_koshin]) VALUES (48, N'営業（登録用の仮ユーザ）', CAST(9999999999 AS Decimal(10, 0)), CAST(N'2011-02-15T15:37:56.873' AS DateTime), CAST(9999999999 AS Decimal(10, 0)), CAST(N'2011-02-15T15:37:56.873' AS DateTime))
GO
