USE [shisaquick_20250604]
GO
/****** Object:  StoredProcedure [dbo].[sp_shohinkaihatsu_search_101_haigo_list]    Script Date: 2025/06/10 16:45:05 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
/*****************************************************																							
機能			：試作データ
ファイル名	：sp_shohinkaihatsu_search_101_haigo_list																				
入力引数		：試作コード、試作コード（年）、試作コード（追番）				
戻り値		：																				
備考			：配合表タブの原料を検索する。												
			：																				
作成日		：2019.03.29 Cuong.n																			
更新日		：2023.07.20 TOsVN-19075 : Shohin support 2023														
*****************************************************/	
ALTER PROCEDURE [dbo].[sp_shohinkaihatsu_search_101_haigo_list]
	@cd_shain		DECIMAL,		--試作コード
	@nen			DECIMAL,		--試作コード（年）
	@no_oi			DECIMAL,		--試作コード（追番）			
	@hyoujigaisha	VARCHAR(20),
	@show_tanka_1	INT,			--リテラルマスタ.リテラル値1 = '9' 又は'1'の時、単価開示許可
	@show_tanka_9	INT,			--リテラルマスタ.リテラル値1 = '9' 又は'1'の時、単価開示許可
	@daihyo_kaisha	INT,			--研究所_会社
	@daihyo_kojo	INT				--研究所_部署
AS
BEGIN

	--原価計算依頼されている
	DECLARE @flg_shisanIrai SMALLINT = 1;

	--原価計算依頼されている
	DECLARE @shisanIrai_temp TABLE(
		cd_shain		DECIMAL(10, 0) NOT NULL,
		nen				DECIMAL(2, 0) NOT NULL,
		no_oi			DECIMAL(3, 0) NOT NULL,
		cd_kotei		SMALLINT NOT NULL,
		seq_kotei		SMALLINT NOT NULL,
		flg_shisanIrai	INT
	)
	INSERT INTO @shisanIrai_temp
	SELECT 
		shisa_list.cd_shain
		,shisa_list.nen
		,shisa_list.no_oi
		,shisa_list.cd_kotei
		,shisa_list.seq_kotei
		,MAX(shisa.flg_shisanIrai) AS flg_shisanIrai
	FROM 
	(
		SELECT 
			cd_shain
			,nen
			,no_oi
			,seq_shisaku
			,cd_kotei
			,seq_kotei
		FROM dbo.tr_shisaku_list
		WHERE 
			cd_shain = @cd_shain
			AND nen = @nen
			AND no_oi = @no_oi
			AND (quantity IS NOT NULL AND quantity <> 0)
	) shisa_list
	INNER JOIN tr_shisaku shisa
	ON shisa_list.cd_shain = shisa.cd_shain
	AND shisa_list.nen = shisa.nen
	AND shisa_list.no_oi = shisa.no_oi
	AND shisa_list.seq_shisaku = shisa.seq_shisaku
	AND shisa.flg_shisanIrai = @flg_shisanIrai
	GROUP BY 
		shisa_list.cd_shain
		,shisa_list.nen
		,shisa_list.no_oi
		,shisa_list.cd_kotei
		,shisa_list.seq_kotei

	SELECT 
		haigo.cd_shain
		,haigo.nen
		,haigo.no_oi
		,haigo.cd_kotei
		,haigo.seq_kotei
		,haigo.nm_kotei
		,haigo.zoku_kotei
		,haigo.sort_kotei
		,haigo.sort_genryo
		,haigo.cd_genryo
		,haigo.cd_kaisha
		,haigo.cd_kaisha AS cd_kaisha_genryo
		,haigo.cd_busho
		,haigo.cd_busho AS cd_kojo_genryo
		,haigo.nm_genryo
		,haigo.tanka
		,ISNULL(literal.value1,0) AS flg_tanka_hyoji		
		,ISNULL(grkojo.flg_disabled_tanka,0) AS flg_disabled_tanka
		,haigo.budomari
		,tankakojo.budomari AS budomari_gry
		,haigo.ritu_abura
		,haigo.ritu_sakusan
		,haigo.ritu_shokuen
		,haigo.ritu_sousan
		,haigo.color
		,haigo.id_toroku
		,haigo.dt_toroku
		,haigo.id_koshin
		,haigo.dt_koshin
		,haigo.ritu_msg
		,haigo.ts
		,irai.flg_shisanIrai
		,irai_kotei.flg_shisanIrai AS flg_shisanIrai_kotei
		-- st Shohin support 2023
		,CASE
			WHEN gen.cd_kaisha IS NOT NULL 
				AND bushoGensanchi.cd_kaisha IS NOT NULL THEN 
															CASE gen.flg_gensanchi
															WHEN 1 THEN '○'
															ELSE ''
														END
			WHEN gen.cd_kaisha IS NULL AND bushoGensanchi.cd_kaisha IS NOT NULL THEN '' -- != kaisha and cd_busho != null
			WHEN gen.cd_kaisha IS NOT NULL AND bushoGensanchi.cd_kaisha IS NULL THEN ''	-- exitst ma_genryo and not exists ma_genryokojo
			ELSE ''
		END AS gensanchi
		-- ed Shohin support 2023
	FROM
	(
		SELECT
			haigo.cd_shain
			,haigo.nen
			,haigo.no_oi
			,haigo.cd_kotei
			,haigo.seq_kotei
			,haigo.nm_kotei
			,haigo.zoku_kotei
			,haigo.sort_kotei
			,haigo.sort_genryo
			,haigo.cd_genryo
			,haigo.cd_kaisha
			,haigo.cd_busho
			,haigo.nm_genryo
			,haigo.tanka
			,haigo.budomari
			,haigo.ritu_abura
			,haigo.ritu_sakusan
			,haigo.ritu_shokuen
			,haigo.ritu_sousan
			,haigo.color
			,haigo.id_toroku
			,haigo.dt_toroku
			,haigo.id_koshin
			,haigo.dt_koshin
			,haigo.ritu_msg
			,haigo.ts
		FROM dbo.tr_haigo haigo
		WHERE 
			haigo.cd_shain = @cd_shain
			AND haigo.nen = @nen
			AND haigo.no_oi = @no_oi
	)haigo

	LEFT OUTER JOIN @shisanIrai_temp irai
	ON haigo.cd_shain = irai.cd_shain
	AND haigo.nen = irai.nen
	AND haigo.no_oi = irai.no_oi
	AND haigo.cd_kotei = irai.cd_kotei
	AND haigo.seq_kotei = irai.seq_kotei

	LEFT OUTER JOIN 
	(
		SELECT		
			cd_shain,		
			nen,				
			no_oi,			
			cd_kotei,	
			MAX(flg_shisanIrai) AS flg_shisanIrai
		FROM @shisanIrai_temp
		GROUP BY 
			cd_shain
			,nen
			,no_oi
			,cd_kotei

	)irai_kotei
	ON haigo.cd_shain = irai_kotei.cd_shain
	AND haigo.nen = irai_kotei.nen
	AND haigo.no_oi = irai_kotei.no_oi
	AND haigo.cd_kotei = irai_kotei.cd_kotei

	LEFT OUTER JOIN dbo.fn_shohinkaihatsu_101_original_budomari(@cd_shain,@nen,@no_oi,@daihyo_kaisha,@daihyo_kojo) grkojo
	ON haigo.cd_shain = grkojo.cd_shain
	AND haigo.nen = grkojo.nen
	AND haigo.no_oi = grkojo.no_oi
	AND haigo.cd_kotei = grkojo.cd_kotei
	AND haigo.seq_kotei = grkojo.seq_kotei

	LEFT OUTER JOIN dbo.ma_genryokojo tankakojo
	ON haigo.cd_kaisha = tankakojo.cd_kaisha
	AND haigo.cd_genryo = tankakojo.cd_genryo
	AND haigo.cd_busho = tankakojo.cd_busho

	-- st Shohin support 2023
	LEFT JOIN  ma_genryo gen
	ON haigo.cd_kaisha = gen.cd_kaisha
	AND haigo.cd_genryo = gen.cd_genryo

	LEFT JOIN
	(
		SELECT
			 cd_kaisha
			, cd_genryo

		FROM dbo.ma_genryokojo

		GROUP BY
			cd_kaisha
			, cd_genryo
	) bushoGensanchi
	ON bushoGensanchi.cd_kaisha = haigo.cd_kaisha
	AND bushoGensanchi.cd_genryo = haigo.cd_genryo
	-- ed Shohin support 2023

	LEFT OUTER JOIN ma_literal literal
	ON literal.cd_category = @hyoujigaisha
	AND CAST(haigo.cd_kaisha AS VARCHAR(10)) = literal.cd_literal
	AND literal.value1 IN (@show_tanka_1,@show_tanka_9)

	ORDER BY
		 haigo.sort_kotei,
		 haigo.sort_genryo

END





