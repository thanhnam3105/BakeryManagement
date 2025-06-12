use NMACSSET02_20084
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

DECLARE
--CREATE PROCEDURE [dbo].[sp_update_xxx]
	@dt_seizo			DATE	='2025-08-06'	-- 仕込日
	, @shikakari	INT						-- 仕掛品
	, @maeshori	INT						-- 前処理品
	, @maedaoshi		INT						-- 前倒し
	, @kurikosi		INT						-- 繰越し
--AS

DECLARE @errCode	INT
DECLARE @num	INT

--a. 仕掛品-前倒し
IF @shikakari = 1 AND @maedaoshi = 1
BEGIN 

	DECLARE
		@dt_shori                     DATETIME                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               
		, @cd_hin                     VARCHAR(50)                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                  
		, @cd_bunrui                  VARCHAR(50)                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                     
		, @cd_seihin                  VARCHAR(50)                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                     
		, @cd_haigo                   VARCHAR(50)                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    
		, @dt_hitsuyo                 DATETIME                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   
		, @qty_hitsuyo                DECIMAL(18,3)                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                
		, @cd_shokuba                 VARCHAR(50)                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                      
		, @cd_team                    VARCHAR(50)                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   
		, @cd_line                    VARCHAR(50)                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   
		, @su_label                   INT                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            
		, @qty_shikomi                DECIMAL(18,3)                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                
		, @ritsu                      DECIMAL(5,2)                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                         
		, @su_batch                   INT                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            
		, @kaiso_shikomi              INT                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                 
		, @flg_shikomi                BIT                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               
		, @cd_setsubi                 VARCHAR(50)                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                      
		, @flg_sakujyo                BIT                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               
		, @no_lot_seihin              VARCHAR(50)                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                         
		, @no_lot_shikakari           VARCHAR(50)                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            
		, @no_lot_org                 VARCHAR(50)                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                      
		, @no_lot_parent              VARCHAR(50)                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                         
		, @no_lot_bunkatsu_parent     VARCHAR(50)                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                  
		, @flg_bunkatsu               BIT                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                
		, @flg_maedaoshi              BIT                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                 
		, @flg_kurikoshi              BIT


	SELECT ths.*
	INTO #tr_hendo_shikakari_maedaoshi
	FROM tr_hendo_shikakari ths

	LEFT JOIN ma_haigo_maedaoshi mhm
	ON ths.cd_haigo = mhm.cd_haigo

	WHERE
		CAST(ths.dt_seizo AS DATE) = @dt_seizo
		AND ths.flg_maedaoshi_sumi = 0
		AND (ths.ryo_maedaoshi > 0 OR ths.ritsu_maedaoshi > 0)
		AND (ths.ryo_hitsuyo_yotei > 0 OR (mhm.flg_data_hissu = 1 AND ths.ryo_maedaoshi > 0))

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
			Exec cm99a351_sp @dt_seizo, @no_lot_shikakari OUTPUT

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
					HENDO.cd_haigo = ths.cd_haigo
					AND HENDO.dt_seizo = ths.dt_seizo
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
IF @shikakari = 1 AND @kurikosi = 1
BEGIN 
	DECLARE @dt_shori_1 DATETIME
	
	SELECT 
		ths.no_seq
		, ths.cd_haigo
		, ths.dt_seizo
		, ths.ryo_hitsuyo_yotei
		, ths.ritsu_maedaoshi
		, ths.ryo_maedaoshi
		, ths.cd_line_maedaoshi
		, ths.ritsu_kurikoshi
		, ths.ryo_kurikoshi
		, ths.su_jisseki_zaiko
		, ths.memo
		, ths.flg_maedaoshi_sumi
		, ths.flg_kurikoshi_sumi
		, ths.cd_bunrui
		, ths.dt_hitsuyo
		, ths.dt_toroku
		, ths.flg_gyotsuika
		, ths.dt_henko
		, ths.cd_koshin

		, mhm.flg_meisai_sakusei
		, mhm.flg_data_hissu

		, ml.cd_team
		, ml.cd_line
		, ml.cd_shokuba

		, tr_hendo_shikakari_yokujitsu.ryo_hitsuyo_yotei_yokujitsu
		, tr_hendo_shikakari_zenjitsu.su_jisseki_zaiko_zenjitsu 
		, tr_hendo_shikakari_zenjitsu.ryo_kurikoshi_zenjitsu

	INTO #tr_hendo_shikakari_kurikoshi_taisho
	FROM tr_hendo_shikakari ths

	LEFT JOIN ma_haigo_maedaoshi AS mhm 
	ON ths.cd_haigo = mhm.cd_haigo

	LEFT JOIN 
	(
		SELECT 
			ml.no_seq
			, ms.cd_line
			, ms.cd_haigo
			, ml.nm_line
			, ml.cd_shokuba
			, ml.cd_team
			, ml.flg_shuka_2
			, ml.flg_sakujyo
			, ml.dt_toroku
			, ml.dt_henko
			, ml.cd_koshin
			, ml.rowguid

		FROM ma_line ml

		CROSS APPLY (
			SELECT TOP 1 *
			FROM ma_seizo_line ms
			WHERE 
				ml.cd_line = ms.cd_line
				AND ms.flg_sakujyo = 0
				AND ms.flg_mishiyo = 0
			ORDER BY 
				no_yusen ASC
		) AS ms

		WHERE ml.flg_sakujyo = 0
	)AS ml 
	ON ths.cd_haigo = ml.cd_haigo 

	OUTER APPLY (
		SELECT TOP 1 ryo_hitsuyo_yotei AS ryo_hitsuyo_yotei_yokujitsu
		FROM tr_hendo_shikakari
		WHERE 
			cd_haigo = ths.cd_haigo
			AND dt_seizo > @dt_seizo -- 仕込日
		ORDER BY 
			dt_seizo ASC
	) AS tr_hendo_shikakari_yokujitsu

	OUTER APPLY (
		SELECT TOP 1 
			su_jisseki_zaiko AS su_jisseki_zaiko_zenjitsu
			, ryo_kurikoshi AS ryo_kurikoshi_zenjitsu
		FROM tr_hendo_shikakari
		WHERE 
			cd_haigo = ths.cd_haigo
			AND dt_seizo < @dt_seizo -- 仕込日
		ORDER BY 
			dt_seizo DESC
	) AS tr_hendo_shikakari_zenjitsu

	WHERE 
		ths.dt_seizo = @dt_seizo	--仕込日
		AND ths.flg_maedaoshi_sumi = 0
		AND 
		(
			ths.ryo_kurikoshi > 0 
			OR ths.ritsu_kurikoshi > 0
		)
		AND (
			tr_hendo_shikakari_yokujitsu.ryo_hitsuyo_yotei_yokujitsu > 0 
			OR 
			(
				mhm.flg_data_hissu = 1 
				AND ths.ryo_kurikoshi > 0
			)
		)



	--ii. 繰越し
	SELECT 
		ths.*
	INTO #tr_hendo_shikakari_kurikoshi_taisho
	FROM #tr_hendo_shikakari_maedaoshi AS ths

	INNER JOIN su_shikomi AS ss
	ON ths.dt_seizo   = ss.dt_shori
	AND ths.cd_haigo   = ss.cd_hin
	AND ths.cd_shokuba = ss.cd_shokuba
	AND ths.cd_team    = ss.cd_team
	AND ths.cd_line    = ss.cd_line

	--iii. 
	SELECT cd_haigo
	INTO #su_shikomi_nodata
	FROM #tr_hendo_shikakari_maedaoshi

	EXCEPT

	SELECT cd_haigo
	FROM #tr_hendo_shikakari_kurikoshi_taisho;



	--iv. Insert table 仕込明細トラン (tr_shikomi）

	DECLARE cursor_name CURSOR FOR

	SELECT 
		dt_seizo AS dt_shori
		, cd_haigo AS cd_hin
		, cd_bunrui
		, '' AS cd_seihin
		, '' AS cd_haigo
		, dt_hitsuyo
		, '' AS qty_hitsuyo
		, cd_shokuba
		, cd_team
		, cd_line
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
		--, no_lot_shikakari
		--, no_lot_org
		, '' AS no_lot_parent
		, NULL AS no_lot_bunkatsu_parent
		, NULL AS flg_bunkatsu
		, 0 AS flg_maedaoshi				  -- QA
		, 1 AS flg_kurikoshi			 

	FROM #tr_hendo_shikakari_kurikoshi_taisho 

	OPEN cursor_name
	FETCH NEXT FROM cursor_name INTO 
		@dt_shori
		, @cd_hin
		

	WHILE @@FETCH_STATUS = 0
	BEGIN
			
		Exec cm99a351_sp @dt_seizo, @no_lot_shikakari OUTPUT

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
		SET HENDO.flg_kurikoshi_sumi = 1
		FROM tr_hendo_shikakari HENDO
		WHERE EXISTS
		(
			SELECT 1
			FROM #tr_hendo_shikakari_maedaoshi ths
			WHERE
				HENDO.cd_haigo = ths.cd_haigo
				AND HENDO.dt_seizo = ths.dt_seizo
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

	SELECT STRING_AGG(cd_haigo, ',') AS cd_haigo_list
	FROM #su_shikomi_nodata

	--SELECT STUFF((
	--	SELECT ',' + cd_haigo
	--	FROM #su_shikomi_nodata
	--	FOR XML PATH(''), TYPE
	--).value('.', 'NVARCHAR(MAX)'), 1, 1, '') AS cd_haigo_list;
END

