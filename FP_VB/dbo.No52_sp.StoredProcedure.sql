use NMACSSET02_20084
--DROP PROCEDURE [dbo].[20084_sp]
GO
SET ANSI_NULLS OFF
GO
SET QUOTED_IDENTIFIER OFF
GO

/*****************************************************
機能			：
ファイル名	：cm99a351_sp
入力引数		：
出力引数		：
戻り値		：
作成日		：
*****************************************************/

SELECT 
    OBJECT_NAME(sys.sql_modules.object_id) AS ObjectName,
    OBJECT_DEFINITION(sys.sql_modules.object_id) AS ObjectDefinition,
    type_desc AS ObjectType
FROM sys.sql_modules
JOIN sys.objects ON sys.sql_modules.object_id = sys.objects.object_id
WHERE OBJECT_DEFINITION(sys.sql_modules.object_id) LIKE '%cm99a351_sp%'
ORDER BY ObjectType, ObjectName;


DECLARE
--CREATE PROCEDURE [dbo].[20084_sp]
	@date			DATE	='2025-08-06'	-- 仕込日
	, @checkbox_1	INT						-- 仕掛品
	, @checkbox_2	INT						-- 前処理品
	, @radio_1		INT						-- 前倒し
	, @radio_2		INT						-- 繰越し
--AS

DECLARE @errCode	INT
DECLARE @num	INT


IF @checkbox_1 = 1 AND @radio_1 = 1
BEGIN 

	DECLARE
		@dt_shori DATETIME,
		@cd_hin VARCHAR(50),
		@cd_bunrui VARCHAR(50),
		@cd_seihin VARCHAR(50),
		@cd_haigo VARCHAR(50),
		@dt_hitsuyo DATETIME,
		@qty_hitsuyo DECIMAL(18, 3),
		@cd_shokuba VARCHAR(50),
		@cd_team VARCHAR(50),
		@cd_line VARCHAR(50),
		@su_label INT,
		@qty_shikomi DECIMAL(18, 3),
		@ritsu DECIMAL(5, 2),
		@su_batch INT,
		@kaiso_shikomi INT,
		@flg_shikomi BIT,
		@cd_setsubi VARCHAR(50),
		@flg_sakujyo BIT,
		@no_lot_seihin VARCHAR(50),
		@no_lot_shikakari VARCHAR(50),
		@no_lot_org VARCHAR(50),
		@no_lot_parent VARCHAR(50),
		@no_lot_bunkatsu_parent VARCHAR(50),
		@flg_bunkatsu BIT,
		@flg_maedaoshi BIT,
		@flg_kurikoshi BIT


	SELECT ths.*
	INTO #tr_hendo_shikakari_maedaoshi
	FROM tr_hendo_shikakari ths

	LEFT JOIN ma_haigo_maedaoshi mhm
	ON ths.cd_haigo = mhm.cd_haigo

	WHERE
		CAST(ths.dt_seizo AS DATE) = @date
		AND ths.flg_maedaoshi_sumi = 0
		AND (ths.ryo_maedaoshi > 0 OR ths.ritsu_maedaoshi > 0)
		AND (ths.ryo_hitsuyo_yotei > 0 OR (mhm.flg_data_hissu = 1 AND ths.ryo_maedaoshi > 0))


		--a. 仕掛品-前倒し
		DECLARE cursor_name CURSOR FOR

		SELECT 
			ths.dt_seizo AS dt_shori
			, ths.cd_haigo AS cd_hin
			, ths.cd_bunrui
			, '' AS cd_seihin
			, '' AS cd_haigo
			, ths.dt_hitsuyo
			, '' AS qty_hitsuyo
			, LINE.cd_shokuba
			, LINE.cd_team
			, LINE.cd_line
			, 0 AS su_label
			, CASE
				WHEN ryo_kurikoshi IS NULL OR ryo_kurikoshi = 0 THEN ryo_hitsuyo_yotei_yokujitsu * ritsu_kurikoshi
				WHEN su_jisseki_zaiko_zenjitsu IS NULL OR su_jisseki_zaiko_zenjitsu = 0 THEN ryo_kurikoshi_zenjitsu
			  END AS qty_shikomi
			, NULL AS ritsu
			, NULL AS su_batch
			, 1 AS kaiso_shikomi
			, 0 AS flg_shikomi
			, NULL AS cd_setsubi
			, NULL AS flg_sakujyo
			, '' AS no_lot_seihin
			--,  no_lot_shikakari
			--, ths.no_lot_org
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
			AND mhm.qty_haigo_h = mhm.qty_kihon -- QA


		OPEN cursor_name
		FETCH NEXT FROM cursor_name INTO 
			@dt_shori
			, @cd_hin
			, @cd_bunrui
			, @cd_seihin
			, @cd_haigo
			, @dt_hitsuyo
			, @qty_hitsuyo
			, @cd_shokuba
			, @cd_team
			, @cd_line
			, @su_label
			, @qty_shikomi
			, @ritsu
			, @su_batch
			, @kaiso_shikomi
			, @flg_shikomi
			, @cd_setsubi
			, @flg_sakujyo
			, @no_lot_seihin
			--, @no_lot_shikakari
			--, @no_lot_org
			, @no_lot_parent
			, @no_lot_bunkatsu_parent
			, @flg_bunkatsu
			, @flg_maedaoshi
			, @flg_kurikoshi		

		WHILE @@FETCH_STATUS = 0
		BEGIN
			--iii. Update saiban
			--iv. Update/ Insert 採番トラン (tr_saiban)
			--Exec @rtnCode = cm99a351_sp @dt_shori, @no_lot_shikakari OUTPUT
			Exec cm99a351_sp @dt_shori, @no_lot_shikakari OUTPUT

			--ii. Insert  table 仕込明細トラン（tr_shikomi）
			INSERT INTO tr_shikomi
			(
				dt_shori
				, cd_hin
				, cd_bunrui
				, cd_seihin
				, cd_haigo
				, dt_hitsuyo
				, qty_hitsuyo
				, cd_shokuba
				, cd_team
				, cd_line
				, su_label
				, qty_shikomi
				, ritsu
				, su_batch
				, kaiso_shikomi
				, flg_shikomi
				, cd_setsubi
				, flg_sakujyo
				, no_lot_seihin
				, no_lot_shikakari
				, no_lot_org
				, no_lot_parent
				, no_lot_bunkatsu_parent
				, flg_bunkatsu
				, flg_maedaoshi				  -- QA
				, flg_kurikoshi				  -- QA
			)
			VALUES (
				@dt_shori
				, @cd_hin
				, @cd_bunrui
				, @cd_seihin
				, @cd_haigo
				, @dt_hitsuyo
				, @qty_hitsuyo
				, @cd_shokuba
				, @cd_team
				, @cd_line
				, @su_label
				, @qty_shikomi
				, @ritsu
				, @su_batch
				, @kaiso_shikomi
				, @flg_shikomi
				, @cd_setsubi
				, @flg_sakujyo
				, @no_lot_seihin
				, @no_lot_shikakari --no_lot_shikakari
				, @no_lot_shikakari --no_lot_org
				, @no_lot_parent
				, @no_lot_bunkatsu_parent
				, @flg_bunkatsu
				, @flg_maedaoshi				  -- QA
				, @flg_kurikoshi				  -- QA
				)

			--v. Update 変動トラン.前倒し・繰越し済フラグ
			UPDATE HENDO
			SET HENDO.flg_maedaoshi_sumi = 1
			FROM tr_hendo_shikakari HENDO
			WHERE EXISTS
			(
				SELECT 1
				FROM #tr_hendo_shikakari_maedaoshi ths
				WHERE
					HENDO.tr_hendo_shikakari.cd_haigo = ths.cd_haigo
					AND HENDO.tr_hendo_shikakari.dt_seizo = ths.dt_seizo
			)


			FETCH NEXT FROM cursor_name INTO 
				@dt_shori
				, @cd_hin
				, @cd_bunrui
				, @cd_seihin
				, @cd_haigo
				, @dt_hitsuyo
				, @qty_hitsuyo
				, @cd_shokuba
				, @cd_team
				, @cd_line
				, @su_label
				, @qty_shikomi
				, @ritsu
				, @su_batch
				, @kaiso_shikomi
				, @flg_shikomi
				, @cd_setsubi
				, @flg_sakujyo
				, @no_lot_seihin
				--, @no_lot_shikakari
				--, @no_lot_org
				, @no_lot_parent
				, @no_lot_bunkatsu_parent
				, @flg_bunkatsu
				, @flg_maedaoshi
				, @flg_kurikoshi				
		END

		CLOSE cursor_name
		DEALLOCATE cursor_name

END







	--b. 仕掛品-繰越し

	DECLARE cursor_name CURSOR FOR

	SELECT 
		ths.dt_seizo AS dt_shori
		, ths.cd_haigo AS cd_hin
		, ths.cd_bunrui
		, '' AS cd_seihin
		, '' AS cd_haigo
		, ths.dt_hitsuyo
		, '' AS qty_hitsuyo
		, LINE.cd_shokuba
		, LINE.cd_team
		, LINE.cd_line
		, 0 AS su_label
		, CASE
			WHEN ryo_kurikoshi IS NULL OR ryo_kurikoshi = 0 THEN ryo_hitsuyo_yotei_yokujitsu * ritsu_kurikoshi
			WHEN su_jisseki_zaiko_zenjitsu IS NULL OR su_jisseki_zaiko_zenjitsu = 0 THEN ryo_kurikoshi_zenjitsu
		  END AS qty_shikomi
		, NULL AS ritsu
		, NULL AS su_batch
		, 1 AS kaiso_shikomi
		, 0 AS flg_shikomi
		, NULL AS cd_setsubi
		, NULL AS flg_sakujyo
		, '' AS no_lot_seihin
		--,  no_lot_shikakari
		--, ths.no_lot_org
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
		AND mhm.qty_haigo_h = mhm.qty_kihon -- QA


	OPEN cursor_name
	FETCH NEXT FROM cursor_name INTO 
		@dt_shori
		

	WHILE @@FETCH_STATUS = 0
	BEGIN
		Exec cm99a351_sp @dt_shori, @no_lot_shikakari OUTPUT

		--ii. Insert  table 仕込明細トラン（tr_shikomi）
		INSERT INTO tr_shikomi
		(
			dt_shori
			
		)
		VALUES (
			@dt_shori
			, @cd_hin
			
			)

		--v. Update 変動トラン.前倒し・繰越し済フラグ
		UPDATE HENDO
		SET HENDO.flg_maedaoshi_sumi = 1
		FROM tr_hendo_shikakari HENDO
		WHERE EXISTS
		(
			SELECT 1
			FROM #tr_hendo_shikakari_maedaoshi ths
			WHERE
				HENDO.tr_hendo_shikakari.cd_haigo = ths.cd_haigo
				AND HENDO.tr_hendo_shikakari.dt_seizo = ths.dt_seizo
		)


		FETCH NEXT FROM cursor_name INTO 
			@dt_shori
				
	END

	CLOSE cursor_name
	DEALLOCATE cursor_name
GO


SELECT * FROM tr_saiban


--EXEC @rtnCode = cm99a351_sp @dt_hitsuyo, @no_lot_shikakari OUTPUT