/****** Object:  Table [dbo].[ma_group]    Script Date: 2025/05/15 16:13:32 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[ma_group](
	[cd_group] [smallint] NOT NULL,
	[nm_group] [nvarchar](100) NOT NULL,
	[id_toroku] [decimal](10, 0) NULL,
	[dt_toroku] [datetime] NULL,
	[id_koshin] [decimal](10, 0) NULL,
	[dt_koshin] [datetime] NULL,
	[flg_hyoji] [smallint] NULL,
	[cd_kaisha] [int] NULL,
	[ts] [timestamp] NOT NULL,
 CONSTRAINT [PK_ma_group] PRIMARY KEY CLUSTERED 
(
	[cd_group] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
INSERT [dbo].[ma_group] ([cd_group], [nm_group], [id_toroku], [dt_toroku], [id_koshin], [dt_koshin], [flg_hyoji], [cd_kaisha]) VALUES (1, N'商品開発研究所', CAST(9999999999 AS Decimal(10, 0)), CAST(N'2009-05-25T21:32:14.297' AS DateTime), CAST(8888888888 AS Decimal(10, 0)), CAST(N'2019-07-02T10:35:47.573' AS DateTime), 1, 1)
INSERT [dbo].[ma_group] ([cd_group], [nm_group], [id_toroku], [dt_toroku], [id_koshin], [dt_koshin], [flg_hyoji], [cd_kaisha]) VALUES (12, N'　', CAST(6505 AS Decimal(10, 0)), CAST(N'2011-12-08T13:38:41.200' AS DateTime), CAST(88044 AS Decimal(10, 0)), CAST(N'2020-05-18T11:09:05.977' AS DateTime), NULL, 1)
INSERT [dbo].[ma_group] ([cd_group], [nm_group], [id_toroku], [dt_toroku], [id_koshin], [dt_koshin], [flg_hyoji], [cd_kaisha]) VALUES (13, N'テストグループ', CAST(9999999999 AS Decimal(10, 0)), CAST(N'2013-11-18T09:42:26.217' AS DateTime), CAST(9999999999 AS Decimal(10, 0)), CAST(N'2013-11-18T09:42:26.217' AS DateTime), 2, 1128)
INSERT [dbo].[ma_group] ([cd_group], [nm_group], [id_toroku], [dt_toroku], [id_koshin], [dt_koshin], [flg_hyoji], [cd_kaisha]) VALUES (14, N'KPC生産管理', CAST(9999999999 AS Decimal(10, 0)), CAST(N'2013-11-18T10:17:23.643' AS DateTime), CAST(9999999999 AS Decimal(10, 0)), CAST(N'2013-11-18T10:17:23.643' AS DateTime), 3, 1128)
INSERT [dbo].[ma_group] ([cd_group], [nm_group], [id_toroku], [dt_toroku], [id_koshin], [dt_koshin], [flg_hyoji], [cd_kaisha]) VALUES (15, N'KPC原資材調達', CAST(9999999999 AS Decimal(10, 0)), CAST(N'2013-12-06T17:35:25.233' AS DateTime), CAST(9999999999 AS Decimal(10, 0)), CAST(N'2013-12-06T17:35:25.233' AS DateTime), 4, 1128)
INSERT [dbo].[ma_group] ([cd_group], [nm_group], [id_toroku], [dt_toroku], [id_koshin], [dt_koshin], [flg_hyoji], [cd_kaisha]) VALUES (16, N'KPC製造工場', CAST(9999999999 AS Decimal(10, 0)), CAST(N'2013-12-06T17:36:03.997' AS DateTime), CAST(9999999999 AS Decimal(10, 0)), CAST(N'2013-12-06T17:36:03.997' AS DateTime), 5, 1128)
INSERT [dbo].[ma_group] ([cd_group], [nm_group], [id_toroku], [dt_toroku], [id_koshin], [dt_koshin], [flg_hyoji], [cd_kaisha]) VALUES (17, N'COP生産管理', CAST(9999999999 AS Decimal(10, 0)), CAST(N'2014-06-29T14:33:41.127' AS DateTime), CAST(9999999999 AS Decimal(10, 0)), CAST(N'2014-06-29T14:33:41.127' AS DateTime), 6, 1105)
INSERT [dbo].[ma_group] ([cd_group], [nm_group], [id_toroku], [dt_toroku], [id_koshin], [dt_koshin], [flg_hyoji], [cd_kaisha]) VALUES (18, N'COP原資材調達', CAST(9999999999 AS Decimal(10, 0)), CAST(N'2014-06-29T14:33:55.683' AS DateTime), CAST(9999999999 AS Decimal(10, 0)), CAST(N'2014-06-29T14:33:55.683' AS DateTime), 7, 1105)
INSERT [dbo].[ma_group] ([cd_group], [nm_group], [id_toroku], [dt_toroku], [id_koshin], [dt_koshin], [flg_hyoji], [cd_kaisha]) VALUES (19, N'COP製造工場', CAST(9999999999 AS Decimal(10, 0)), CAST(N'2014-06-29T14:34:09.090' AS DateTime), CAST(9999999999 AS Decimal(10, 0)), CAST(N'2014-06-29T14:34:09.090' AS DateTime), 8, 1105)
INSERT [dbo].[ma_group] ([cd_group], [nm_group], [id_toroku], [dt_toroku], [id_koshin], [dt_koshin], [flg_hyoji], [cd_kaisha]) VALUES (20, N'テスト', CAST(9999999999 AS Decimal(10, 0)), CAST(N'2021-04-13T18:24:14.433' AS DateTime), CAST(9999999999 AS Decimal(10, 0)), CAST(N'2021-04-13T18:24:14.433' AS DateTime), NULL, 1)
INSERT [dbo].[ma_group] ([cd_group], [nm_group], [id_toroku], [dt_toroku], [id_koshin], [dt_koshin], [flg_hyoji], [cd_kaisha]) VALUES (21, N'アヲハタ開発', CAST(9999999999 AS Decimal(10, 0)), CAST(N'2023-11-12T09:31:54.990' AS DateTime), CAST(9999999999 AS Decimal(10, 0)), CAST(N'2023-11-12T09:31:54.990' AS DateTime), 19, 3004)
INSERT [dbo].[ma_group] ([cd_group], [nm_group], [id_toroku], [dt_toroku], [id_koshin], [dt_koshin], [flg_hyoji], [cd_kaisha]) VALUES (90, N'研究所グループ_新任者', CAST(9999999999 AS Decimal(10, 0)), CAST(N'2016-10-20T00:00:00.000' AS DateTime), CAST(9999999999 AS Decimal(10, 0)), CAST(N'2016-10-20T00:00:00.000' AS DateTime), 10, 1)
INSERT [dbo].[ma_group] ([cd_group], [nm_group], [id_toroku], [dt_toroku], [id_koshin], [dt_koshin], [flg_hyoji], [cd_kaisha]) VALUES (91, N'設定対象外', CAST(9999999999 AS Decimal(10, 0)), CAST(N'2016-10-20T00:00:00.000' AS DateTime), CAST(9999999999 AS Decimal(10, 0)), CAST(N'2016-10-20T00:00:00.000' AS DateTime), 11, 1)
INSERT [dbo].[ma_group] ([cd_group], [nm_group], [id_toroku], [dt_toroku], [id_koshin], [dt_koshin], [flg_hyoji], [cd_kaisha]) VALUES (92, N'設定対象外', CAST(9999999999 AS Decimal(10, 0)), CAST(N'2016-10-20T00:00:00.000' AS DateTime), CAST(9999999999 AS Decimal(10, 0)), CAST(N'2016-10-20T00:00:00.000' AS DateTime), 12, 1100)
INSERT [dbo].[ma_group] ([cd_group], [nm_group], [id_toroku], [dt_toroku], [id_koshin], [dt_koshin], [flg_hyoji], [cd_kaisha]) VALUES (93, N'設定対象外', CAST(9999999999 AS Decimal(10, 0)), CAST(N'2016-10-20T00:00:00.000' AS DateTime), CAST(9999999999 AS Decimal(10, 0)), CAST(N'2016-10-20T00:00:00.000' AS DateTime), 13, 1105)
INSERT [dbo].[ma_group] ([cd_group], [nm_group], [id_toroku], [dt_toroku], [id_koshin], [dt_koshin], [flg_hyoji], [cd_kaisha]) VALUES (94, N'設定対象外', CAST(9999999999 AS Decimal(10, 0)), CAST(N'2016-10-20T00:00:00.000' AS DateTime), CAST(9999999999 AS Decimal(10, 0)), CAST(N'2016-10-20T00:00:00.000' AS DateTime), 14, 1107)
INSERT [dbo].[ma_group] ([cd_group], [nm_group], [id_toroku], [dt_toroku], [id_koshin], [dt_koshin], [flg_hyoji], [cd_kaisha]) VALUES (95, N'設定対象外', CAST(9999999999 AS Decimal(10, 0)), CAST(N'2016-10-20T00:00:00.000' AS DateTime), CAST(9999999999 AS Decimal(10, 0)), CAST(N'2016-10-20T00:00:00.000' AS DateTime), 15, 1121)
INSERT [dbo].[ma_group] ([cd_group], [nm_group], [id_toroku], [dt_toroku], [id_koshin], [dt_koshin], [flg_hyoji], [cd_kaisha]) VALUES (96, N'設定対象外', CAST(9999999999 AS Decimal(10, 0)), CAST(N'2016-10-20T00:00:00.000' AS DateTime), CAST(9999999999 AS Decimal(10, 0)), CAST(N'2016-10-20T00:00:00.000' AS DateTime), 16, 1128)
INSERT [dbo].[ma_group] ([cd_group], [nm_group], [id_toroku], [dt_toroku], [id_koshin], [dt_koshin], [flg_hyoji], [cd_kaisha]) VALUES (97, N'設定対象外', CAST(9999999999 AS Decimal(10, 0)), CAST(N'2016-10-20T00:00:00.000' AS DateTime), CAST(9999999999 AS Decimal(10, 0)), CAST(N'2016-10-20T00:00:00.000' AS DateTime), 17, 3004)
INSERT [dbo].[ma_group] ([cd_group], [nm_group], [id_toroku], [dt_toroku], [id_koshin], [dt_koshin], [flg_hyoji], [cd_kaisha]) VALUES (98, N'設定対象外', CAST(9999999999 AS Decimal(10, 0)), CAST(N'2016-10-20T00:00:00.000' AS DateTime), CAST(9999999999 AS Decimal(10, 0)), CAST(N'2016-10-20T00:00:00.000' AS DateTime), 18, 6000)
INSERT [dbo].[ma_group] ([cd_group], [nm_group], [id_toroku], [dt_toroku], [id_koshin], [dt_koshin], [flg_hyoji], [cd_kaisha]) VALUES (99, N'生産本部', CAST(9999999999 AS Decimal(10, 0)), CAST(N'2009-11-27T13:34:18.110' AS DateTime), CAST(9999999999 AS Decimal(10, 0)), CAST(N'2009-11-27T13:34:18.110' AS DateTime), 9, 1)
GO
