SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[tr_shikomi](
	[dt_shori] [datetime] NOT NULL,
	[cd_hin] [varchar](13) COLLATE Japanese_CI_AS NOT NULL,
	[cd_bunrui] [varchar](2) COLLATE Japanese_CI_AS NULL,
	[cd_seihin] [varchar](13) COLLATE Japanese_CI_AS NOT NULL,
	[cd_haigo] [varchar](13) COLLATE Japanese_CI_AS NOT NULL,
	[dt_hitsuyo] [datetime] NOT NULL,
	[qty_hitsuyo] [float] NOT NULL,
	[cd_shokuba] [varchar](2) COLLATE Japanese_CI_AS NOT NULL,
	[cd_team] [varchar](2) COLLATE Japanese_CI_AS NULL,
	[cd_line] [varchar](2) COLLATE Japanese_CI_AS NOT NULL,
	[su_label] [int] NOT NULL,
	[qty_shikomi] [float] NOT NULL,
	[ritsu] [float] NULL,
	[su_batch] [float] NULL,
	[kaiso_shikomi] [int] NOT NULL,
	[flg_shikomi] [bit] NOT NULL,
	[cd_setsubi] [varchar](2) COLLATE Japanese_CI_AS NULL,
	[flg_sakujyo] [bit] NULL,
	[no_lot_seihin] [varchar](14) COLLATE Japanese_CI_AS NOT NULL,
	[no_lot_shikakari] [varchar](14) COLLATE Japanese_CI_AS NOT NULL,
	[no_lot_org] [varchar](14) COLLATE Japanese_CI_AS NOT NULL,
	[no_lot_parent] [varchar](14) COLLATE Japanese_CI_AS NOT NULL,
	[no_lot_bunkatsu_parent] [varchar](14) COLLATE Japanese_CI_AS NULL,
	[flg_bunkatsu] [bit] NULL,
	[flg_maedaoshi] [bit] NULL,
	[flg_kurikoshi] [bit] NULL,
	[ts] [timestamp] NOT NULL
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
CREATE UNIQUE NONCLUSTERED INDEX [IX_tr_shikomi] ON [dbo].[tr_shikomi] 
(
	[no_lot_org] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON, FILLFACTOR = 90) ON [PRIMARY]
GO
CREATE NONCLUSTERED INDEX [IX_tr_shikomi_1] ON [dbo].[tr_shikomi] 
(
	[no_lot_seihin] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON, FILLFACTOR = 90) ON [PRIMARY]
GO
CREATE NONCLUSTERED INDEX [IX_tr_shikomi_2] ON [dbo].[tr_shikomi] 
(
	[dt_hitsuyo] ASC,
	[cd_shokuba] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON, FILLFACTOR = 90) ON [PRIMARY]
GO
CREATE NONCLUSTERED INDEX [IX_tr_shikomi_3] ON [dbo].[tr_shikomi] 
(
	[dt_shori] ASC,
	[cd_hin] ASC,
	[cd_line] ASC,
	[cd_team] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON, FILLFACTOR = 90) ON [PRIMARY]
GO
CREATE NONCLUSTERED INDEX [IX_tr_shikomi_4] ON [dbo].[tr_shikomi] 
(
	[no_lot_shikakari] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON, FILLFACTOR = 90) ON [PRIMARY]
GO
CREATE NONCLUSTERED INDEX [IX_tr_shikomi_5] ON [dbo].[tr_shikomi] 
(
	[no_lot_parent] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON, FILLFACTOR = 90) ON [PRIMARY]
GO
ALTER TABLE [dbo].[tr_shikomi] ADD  CONSTRAINT [DF_tr_shikomi_flg_sakujyo]  DEFAULT (null) FOR [flg_sakujyo]
GO
