DROP PROCEDURE [dbo].[kk12g011a011_sp]
GO
SET ANSI_NULLS OFF
GO
SET QUOTED_IDENTIFIER OFF
GO

/*****************************************************
機能			：
ファイル名	：kk12g011a011_sp
入力引数		：
出力引数		：
戻り値		：
作成日		：
*****************************************************/

CREATE PROCEDURE [dbo].[kk12g011a011_sp]
	@dt_seizo			DATE		-- 仕込日
	, @shikakari		INT			-- 仕掛品
	, @maeshori			INT			-- 前処理品
	, @maedaoshi		INT			-- 前倒し
	, @kurikosi			INT			-- 繰越し
AS
BEGIN

	DECLARE @cnt_shikakari INT = 0
			, @cnt_maeshori INT = 0

	SELECT @cnt_shikakari = COUNT(*) 
	FROM tr_hendo_shikakari AS ths
	LEFT JOIN ma_haigo_maedaoshi AS mhm
		ON ths.cd_haigo = mhm.cd_haigo
	WHERE 
		@shikakari = 1
		AND ths.dt_seizo = @dt_seizo
		AND (
			(@maedaoshi = 1 
			 AND ths.flg_maedaoshi_sumi = 0 
			 AND (ths.ryo_maedaoshi > 0 OR ths.ritsu_maedaoshi > 0))
			OR
			(@kurikosi = 1 
			 AND ths.flg_kurikoshi_sumi = 0 
			 AND (ths.ryo_kurikoshi > 0 OR ths.ritsu_kurikoshi > 0))
		)
		AND (ths.ryo_hitsuyo_yotei > 0 OR mhm.flg_data_hissu = 1)


	SELECT @cnt_maeshori = COUNT(*)
	FROM tr_hendo_maeshori AS thm
	LEFT JOIN ma_maeshori_maedaoshi AS mmm
		ON thm.cd_maeshori = mmm.cd_maeshori
	WHERE 
		@maeshori = 1
		AND thm.dt_seizo = @dt_seizo
		AND (
			(@maedaoshi = 1 
			 AND thm.flg_maedaoshi_sumi = 0 
			 AND (thm.ryo_maedaoshi > 0 OR thm.ritsu_maedaoshi > 0))
			OR
			(@kurikosi = 1 
			 AND thm.flg_kurikoshi_sumi = 0 
			 AND (thm.ryo_kurikoshi > 0 OR thm.ritsu_kurikoshi > 0))
		)
		AND (thm.ryo_hitsuyo_yotei > 0 OR mmm.flg_data_hissu = 1)

	SELECT @cnt_shikakari + @cnt_maeshori AS cnt
END

