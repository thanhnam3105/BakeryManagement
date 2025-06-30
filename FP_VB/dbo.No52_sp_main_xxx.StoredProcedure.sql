--use NMACS_SYS_2025_20084
use NMACSSYU25_2025_20084	--MAIN
--DROP PROCEDURE [dbo].[sp_update_xxx]
GO
SET ANSI_NULLS OFF
GO
SET QUOTED_IDENTIFIER OFF
GO

/*****************************************************
機能			：
ファイル名	：sp_update_xxx
入力引数		：
出力引数		：
戻り値		：
作成日		：
*****************************************************/
IF OBJECT_ID('tempdb..#tr_hendo_shikakari_maedaoshi') IS NOT NULL
BEGIN
	DROP TABLE #tr_hendo_shikakari_maedaoshi;
END
IF OBJECT_ID('tempdb..#tr_hendo_shikakari_kurikoshi_taisho') IS NOT NULL
BEGIN
	DROP TABLE tr_hendo_shikakari_kurikoshi_taisho;
END


DECLARE
--CREATE PROCEDURE [dbo].[sp_update_xxx]
	@dt_seizo			VARCHAR(8)	='20250806'	-- 仕込日
	, @shikakari		INT			=1			-- 仕掛品
	, @maeshori			INT			=1			-- 前処理品
	, @maedaoshi		INT			=1			-- 前倒し
	, @kurikosi			INT			=0			-- 繰越し
--AS

DECLARE @errCode	INT
DECLARE @num		INT

--a. 仕掛品-前倒し
IF @shikakari = 1 AND @maedaoshi = 1
BEGIN 
	DECLARE  @dt_seizo_datetime			DATETIME
			, @no_shikomi_saiban		INT
			, @no_shikomi_saiban_from	INT
	
	SET @dt_seizo_datetime = CONVERT(DATETIME,@dt_seizo,112)

	SELECT 
		@no_shikomi_saiban = ISNULL(no_shikomi_saiban,0)	
	FROM tr_saiban
	WHERE
		dt_hizuke = @dt_seizo_datetime															
																
	--i. 対象データ抽出
	SELECT ths.*
	INTO #tr_hendo_shikakari_maedaoshi
	FROM tr_hendo_shikakari ths

	LEFT JOIN ma_haigo_maedaoshi mhm
	ON ths.cd_haigo = mhm.cd_haigo

	WHERE
		ths.dt_seizo = @dt_seizo_datetime
		AND ths.flg_maedaoshi_sumi = 0
		AND (ths.ryo_maedaoshi > 0 OR ths.ritsu_maedaoshi > 0)
		AND (ths.ryo_hitsuyo_yotei > 0 OR (mhm.flg_data_hissu = 1 AND ths.ryo_maedaoshi > 0))



	--ii. 仕込明細トラン（tr_shikomi）に登録
	--INSERT INTO tr_shikomi
	--(
	--	dt_shori
	--	, cd_hin
	--	, cd_bunrui
	--	, cd_seihin
	--	, cd_haigo
	--	, dt_hitsuyo
	--	, qty_hitsuyo
	--	, cd_shokuba
	--	, cd_team
	--	, cd_line
	--	, su_label
	--	, qty_shikomi
	--	, ritsu
	--	, su_batch
	--	, kaiso_shikomi
	--	, flg_shikomi
	--	, cd_setsubi
	--	, flg_sakujyo
	--	, no_lot_seihin
	--	, no_lot_shikakari
	--	, no_lot_org
	--	, no_lot_parent
	--	, no_lot_bunkatsu_parent
	--	, flg_bunkatsu
	--	, flg_maedaoshi				  
	--	, flg_kurikoshi				  
	--)
	SELECT 
		ths.dt_seizo AS dt_shori
		, ths.cd_haigo AS cd_hin
		, ths.cd_bunrui
		, '' AS cd_seihin
		, '' AS cd_haigo
		, ths.dt_hitsuyo
		, 0 as qty_hitsuyo
		, LINE.cd_shokuba
		, LINE.cd_team
		, LINE.cd_line
		, 0 AS su_label
		, CASE
			WHEN ryo_maedaoshi IS NULL OR ryo_maedaoshi = 0 THEN FLOOR((ths.ryo_hitsuyo_yotei * ths.ritsu_maedaoshi) / mhm.qty_max) * mhm.qty_max
			ELSE FLOOR(ths.ryo_maedaoshi / mhm.qty_max) * mhm.qty_max
			END AS qty_shikomi
		, NULL AS ritsu
		, NULL AS su_batch
		, 1 AS kaiso_shikomi
		, 0 AS flg_shikomi
		, NULL AS cd_setsubi
		, NULL AS flg_sakujyo
		, '' AS no_lot_seihin
		, 'S' + CONVERT(CHAR(8), @dt_seizo, 112) + RIGHT('00000' + CONVERT(VARCHAR, @no_shikomi_saiban + (ROW_NUMBER() OVER (ORDER BY (SELECT NULL)))), 5) AS no_lot_shikakari
		, 'S' + CONVERT(CHAR(8), @dt_seizo, 112) + RIGHT('00000' + CONVERT(VARCHAR, @no_shikomi_saiban + (ROW_NUMBER() OVER (ORDER BY (SELECT NULL)))), 5) AS no_lot_org
		, '' AS no_lot_parent
		, NULL AS no_lot_bunkatsu_parent
		, NULL AS flg_bunkatsu
		, 0 AS flg_maedaoshi				 
		, 1 AS flg_kurikoshi				 

	FROM #tr_hendo_shikakari_maedaoshi ths

	INNER JOIN ma_haigo_mei mhm
	ON ths.cd_haigo = mhm.cd_haigo

	LEFT JOIN ma_line LINE
	ON ths.cd_line_maedaoshi = LINE.cd_line
	AND LINE.flg_sakujyo = 0

	WHERE
		mhm.flg_mishiyo = 0
		AND mhm.flg_sakujyo = 0
		AND mhm.no_han = 1
		AND mhm.qty_haigo_h = mhm.qty_kihon

	DECLARE @cntUpdate INT = (SELECT @@ROWCOUNT);

	--iii. 採番変数更新
	SET @no_shikomi_saiban_from = @no_shikomi_saiban + 1
	SET @no_shikomi_saiban = @no_shikomi_saiban + @cntUpdate

	----iv. 採番トラン更新・登録
	
	UPDATE tr_saiban 
	SET no_shikomi_saiban = @no_shikomi_saiban 
	WHERE dt_hizuke = @dt_seizo_datetime

	IF @@ROWCOUNT = 0
	BEGIN
		INSERT INTO tr_saiban 
		(
			dt_hizuke
			, no_seihin_saiban
			, no_shikomi_saiban
			, no_meisai_saiban
			, no_naibu_saiban
		)
		VALUES 
		(
			@dt_seizo_datetime		
			, 0						
			, @no_shikomi_saiban	
			, 0.0					
			, 0.0					
		)
	END

	--v. 変動トランの前倒し・繰越し済フラグ更新
	UPDATE HENDO
	SET 
		HENDO.flg_maedaoshi_sumi = 1
		, HENDO.no_lot_shikakari_maedaoshi = shikomi.no_lot_shikakari

	FROM tr_hendo_shikakari HENDO

	INNER JOIN 
	(
		SELECT 
			HENDO_SUB.no_seq
			, SHIKOMI_SUB.no_lot_shikakari

		FROM tr_shikomi SHIKOMI_SUB

		INNER JOIN tr_hendo_shikakari HENDO_SUB
		ON HENDO_SUB.cd_haigo = SHIKOMI_SUB.cd_haigo

		WHERE 
			SHIKOMI_SUB.no_lot_shikakari BETWEEN @no_shikomi_saiban_from AND @no_shikomi_saiban
			AND HENDO_SUB.dt_seizo = @dt_seizo_datetime
	)shikomi
	ON shikomi.no_seq = HENDO.no_seq

END



--b. 仕掛品-繰越し
--IF @shikakari = 1 AND @kurikosi = 1
--BEGIN 
--	DECLARE @dt_shori_1 DATETIME
	
--	SELECT 
--		ths.no_seq
--		, ths.cd_haigo
--		, ths.dt_seizo
--		, ths.ryo_hitsuyo_yotei
--		, ths.ritsu_maedaoshi
--		, ths.ryo_maedaoshi
--		, ths.cd_line_maedaoshi
--		, ths.ritsu_kurikoshi
--		, ths.ryo_kurikoshi
--		, ths.su_jisseki_zaiko
--		, ths.memo
--		, ths.flg_maedaoshi_sumi
--		, ths.flg_kurikoshi_sumi
--		, ths.cd_bunrui
--		, ths.dt_hitsuyo
--		, ths.dt_toroku
--		, ths.flg_gyotsuika
--		, ths.dt_henko
--		, ths.cd_koshin

--		, mhm.flg_meisai_sakusei
--		, mhm.flg_data_hissu

--		, ml.cd_team
--		, ml.cd_line
--		, ml.cd_shokuba

--		, yokujitsu.ryo_hitsuyo_yotei_yokujitsu
--		, zenjitsu.su_jisseki_zaiko_zenjitsu 
--		, zenjitsu.ryo_kurikoshi_zenjitsu

--	INTO #tr_hendo_shikakari_kurikoshi_taisho
--	FROM tr_hendo_shikakari ths

--	LEFT JOIN ma_haigo_maedaoshi AS mhm 
--	ON ths.cd_haigo = mhm.cd_haigo

--	LEFT JOIN 
--	(
--		SELECT 
--			ml.no_seq
--			, ms.cd_line
--			, ms.cd_haigo
--			, ml.nm_line
--			, ml.cd_shokuba
--			, ml.cd_team
--			, ml.flg_shuka_2
--			, ml.flg_sakujyo
--			, ml.dt_toroku
--			, ml.dt_henko
--			, ml.cd_koshin
--			, ml.rowguid

--		FROM ma_line ml

--		CROSS APPLY (
--			SELECT TOP 1 *
--			FROM ma_seizo_line ms
--			WHERE 
--				ml.cd_line = ms.cd_line
--				AND ms.flg_sakujyo = 0
--				AND ms.flg_mishiyo = 0
--			ORDER BY 
--				no_yusen ASC
--		) AS ms

--		WHERE ml.flg_sakujyo = 0
--	)AS ml 
--	ON ths.cd_haigo = ml.cd_haigo 

--	--tr_hendo_shikakari_yokujitsu
--	OUTER APPLY (
--		SELECT TOP 1 ryo_hitsuyo_yotei AS ryo_hitsuyo_yotei_yokujitsu
--		FROM tr_hendo_shikakari
--		WHERE 
--			cd_haigo = ths.cd_haigo
--			AND dt_seizo > DATEADD(DAY,1,CONVERT(DATE, @dt_seizo_datetime, 112)) -- 仕込日
--			AND flg_gyotsuika = 0
--		ORDER BY 
--			dt_seizo ASC
--	) AS yokujitsu

--	--tr_hendo_shikakari_zenjitsu
--	OUTER APPLY (
--		SELECT TOP 1 
--			su_jisseki_zaiko AS su_jisseki_zaiko_zenjitsu
--			, ryo_kurikoshi AS ryo_kurikoshi_zenjitsu
--		FROM tr_hendo_shikakari
--		WHERE 
--			cd_haigo = ths.cd_haigo
--			AND dt_seizo < DATEADD(DAY,-1,CONVERT(DATE, @dt_seizo_datetime, 112)) -- 仕込日
--			AND flg_gyotsuika = 0
--		ORDER BY 
--			dt_seizo DESC
--	) AS zenjitsu

--	WHERE 
--		ths.dt_seizo = @dt_seizo_datetime	--仕込日
--		AND ths.flg_maedaoshi_sumi = 0
--		AND 
--		(
--			ths.ryo_kurikoshi > 0 
--			OR ths.ritsu_kurikoshi > 0
--		)
--		AND (
--			yokujitsu.ryo_hitsuyo_yotei_yokujitsu > 0 
--			OR 
--			(
--				mhm.flg_data_hissu = 1 
--				AND ths.ryo_kurikoshi > 0
--			)
--		)


--	--ii. 繰越し
--	SELECT 
--		ths.*
--	--INTO #tr_hendo_shikakari_kurikoshi_taisho
--	FROM #tr_hendo_shikakari_maedaoshi AS ths

--	INNER JOIN su_shikomi AS ss
--	ON ths.dt_seizo   = ss.dt_shori
--	AND ths.cd_haigo   = ss.cd_hin
--	AND ths.cd_shokuba = ss.cd_shokuba
--	AND ths.cd_team    = ss.cd_team
--	AND ths.cd_line    = ss.cd_line

--	--iii. 
--	SELECT cd_haigo
--	INTO #su_shikomi_nodata
--	FROM #tr_hendo_shikakari_maedaoshi

--	EXCEPT

--	SELECT cd_haigo
--	FROM #tr_hendo_shikakari_kurikoshi_taisho;



--	--iv. Insert table 仕込明細トラン (tr_shikomi）

--	DECLARE cursor_name CURSOR FOR

--	SELECT 
--		dt_seizo AS dt_shori
--		, cd_haigo AS cd_hin
--		, cd_bunrui
--		, '' AS cd_seihin
--		, '' AS cd_haigo
--		, dt_hitsuyo
--		, '' AS qty_hitsuyo
--		, cd_shokuba
--		, cd_team
--		, cd_line
--		, 0 AS su_label
--		, CASE
--				WHEN ryo_kurikoshi IS NULL OR ryo_kurikoshi = 0 THEN ryo_hitsuyo_yotei_yokujitsu * ritsu_kurikoshi
--				WHEN su_jisseki_zaiko_zenjitsu IS NULL OR su_jisseki_zaiko_zenjitsu = 0 THEN ryo_kurikoshi_zenjitsu
--		  END AS qty_shikomi
--		, NULL AS ritsu
--		, NULL AS su_batch
--		, 1 AS kaiso_shikomi
--		, 0 AS flg_shikomi
--		, NULL AS cd_setsubi
--		, NULL AS flg_sakujyo
--		, '' AS no_lot_seihin
--		--, no_lot_shikakari
--		--, no_lot_org
--		, '' AS no_lot_parent
--		, NULL AS no_lot_bunkatsu_parent
--		, NULL AS flg_bunkatsu
--		, 0 AS flg_maedaoshi				  
--		, 1 AS flg_kurikoshi			 

--	FROM #tr_hendo_shikakari_kurikoshi_taisho 

--	OPEN cursor_name
--	FETCH NEXT FROM cursor_name INTO 
--		@dt_shori
--		, @cd_hin
		

--	WHILE @@FETCH_STATUS = 0
--	BEGIN
			
--		Exec cm99a351_sp @dt_seizo, @no_lot_shikakari OUTPUT

--		--ii. Insert  table 仕込明細トラン（tr_shikomi）
--		INSERT INTO tr_shikomi
--		(
--			dt_shori
--			, cd_hin
--			, cd_bunrui
--			, cd_seihin
--			, cd_haigo
--			, dt_hitsuyo
--			, qty_hitsuyo
--			, cd_shokuba
--			, cd_team
--			, cd_line
--			, su_label
--			, qty_shikomi
--			, ritsu
--			, su_batch
--			, kaiso_shikomi
--			, flg_shikomi
--			, cd_setsubi
--			, flg_sakujyo
--			, no_lot_seihin
--			, no_lot_shikakari
--			, no_lot_org
--			, no_lot_parent
--			, no_lot_bunkatsu_parent
--			, flg_bunkatsu
--			, flg_maedaoshi				  
--			, flg_kurikoshi				  
--		)
--		VALUES (
--			@dt_shori
--			, @cd_hin
--			, @cd_bunrui
--			, @cd_seihin
--			, @cd_haigo
--			, @dt_hitsuyo
--			, @qty_hitsuyo
--			, @cd_shokuba
--			, @cd_team
--			, @cd_line
--			, @su_label
--			, @qty_shikomi
--			, @ritsu
--			, @su_batch
--			, @kaiso_shikomi
--			, @flg_shikomi
--			, @cd_setsubi
--			, @flg_sakujyo
--			, @no_lot_seihin
--			, @no_lot_shikakari --no_lot_shikakari
--			, @no_lot_shikakari --no_lot_org
--			, @no_lot_parent
--			, @no_lot_bunkatsu_parent
--			, @flg_bunkatsu
--			, @flg_maedaoshi				  
--			, @flg_kurikoshi				  
--			)

--		--v. Update 変動トラン.前倒し・繰越し済フラグ
--		UPDATE HENDO
--		SET 
--			HENDO.flg_kurikoshi_sumi = 1
--			, HENDO.no_lot_shikakari_maedaoshi = shikomi.no_lot_shikakari
--		FROM tr_hendo_shikakari HENDO

--		INNER JOIN tr_shikomi shikomi
--		ON HENDO.cd_haigo = shikomi.cd_haigo

--		WHERE
--			shikomi.no_lot_shikakari = @no_lot_shikakari
--			AND HENDO.dt_seizo = @dt_seizo


--		FETCH NEXT FROM cursor_name INTO 
--			@dt_shori
--			, @cd_hin
--			, @cd_bunrui
--			, @cd_seihin
--			, @cd_haigo
--			, @dt_hitsuyo
--			, @qty_hitsuyo
--			, @cd_shokuba
--			, @cd_team
--			, @cd_line
--			, @su_label
--			, @qty_shikomi
--			, @ritsu
--			, @su_batch
--			, @kaiso_shikomi
--			, @flg_shikomi
--			, @cd_setsubi
--			, @flg_sakujyo
--			, @no_lot_seihin
--			--, @no_lot_shikakari
--			--, @no_lot_org
--			, @no_lot_parent
--			, @no_lot_bunkatsu_parent
--			, @flg_bunkatsu
--			, @flg_maedaoshi
--			, @flg_kurikoshi
			
--	END

--	CLOSE cursor_name
--	DEALLOCATE cursor_name

	--SELECT STRING_AGG(cd_haigo, ',') AS cd_haigo_list
	--FROM #su_shikomi_nodata

	--SELECT STUFF((
	--	SELECT ',' + cd_haigo
	--	FROM #su_shikomi_nodata
	--	FOR XML PATH(''), TYPE
	--).value('.', 'NVARCHAR(MAX)'), 1, 1, '') AS cd_haigo_list;
--END

