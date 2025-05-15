/****** Object:  Table [dbo].[ma_group_set]    Script Date: 2025/05/15 16:13:32 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[ma_group_set](
	[cd_group] [smallint] NOT NULL,
	[nm_group] [nvarchar](100) NOT NULL,
	[id_toroku] [decimal](10, 0) NULL,
	[dt_toroku] [datetime] NULL,
	[id_koshin] [decimal](10, 0) NULL,
	[dt_koshin] [datetime] NULL,
	[flg_hyoji] [smallint] NULL
) ON [PRIMARY]
GO
INSERT [dbo].[ma_group_set] ([cd_group], [nm_group], [id_toroku], [dt_toroku], [id_koshin], [dt_koshin], [flg_hyoji]) VALUES (1, N'商品開発センター', CAST(9999999999 AS Decimal(10, 0)), CAST(N'2009-05-25T21:32:14.297' AS DateTime), CAST(4510 AS Decimal(10, 0)), CAST(N'2009-09-18T15:28:35.270' AS DateTime), 1)
INSERT [dbo].[ma_group_set] ([cd_group], [nm_group], [id_toroku], [dt_toroku], [id_koshin], [dt_koshin], [flg_hyoji]) VALUES (4, N'惣菜開発センター', CAST(9999999999 AS Decimal(10, 0)), CAST(N'2009-05-26T09:39:16.067' AS DateTime), CAST(91007 AS Decimal(10, 0)), CAST(N'2009-07-15T17:18:49.720' AS DateTime), 2)
INSERT [dbo].[ma_group_set] ([cd_group], [nm_group], [id_toroku], [dt_toroku], [id_koshin], [dt_koshin], [flg_hyoji]) VALUES (5, N'タマゴR&Dセンター', CAST(4510 AS Decimal(10, 0)), CAST(N'2009-05-27T18:21:02.947' AS DateTime), CAST(4510 AS Decimal(10, 0)), CAST(N'2009-05-27T18:23:32.413' AS DateTime), 3)
INSERT [dbo].[ma_group_set] ([cd_group], [nm_group], [id_toroku], [dt_toroku], [id_koshin], [dt_koshin], [flg_hyoji]) VALUES (11, N'健康機能R＆Dセンター', CAST(97003 AS Decimal(10, 0)), CAST(N'2009-06-08T14:23:42.200' AS DateTime), CAST(88704 AS Decimal(10, 0)), CAST(N'2009-06-08T14:38:33.343' AS DateTime), 4)
GO
