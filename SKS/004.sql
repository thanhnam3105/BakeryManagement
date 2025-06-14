USE [shisaquick_20250604]
GO
/****** Object:  StoredProcedure [dbo].[sp_shohinkaihatsu_search_004]    Script Date: 2025/06/10 16:45:30 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER OFF
GO

/*****************************************************																							
機能		：商品開発サポートの試作分析データ確認の検索処理
ファイル名	：sp_shohinkaihatsu_search_004																		
入力引数	：@cd_kaisha/@cd_busho/@cd_genryo/@nm_genryo/
              @checkGenryo/@checkNGenryo/@flg_shiyo/
			  @id_session/@skip/@top				
戻り値		：																				
備考		：原料マスタのデータを検索する。												
			：																				
作成日		：2019.05.10 kinoshita.h																
更新日		：																				
*****************************************************/

ALTER PROC [dbo].[sp_shohinkaihatsu_search_004]
	
	@cd_kaisha			INT
	,@cd_busho			VARCHAR(10)
	,@cd_genryo			NVARCHAR(11)
	,@nm_genryo			NVARCHAR(60)
	,@checkGenryo		BIT
	,@checkNGenryo		BIT
	,@flg_shiyo			SMALLINT
    ,@id_session		INT

	-- next search
	,@skip				INT
	,@top				INT
AS

/************************************************************
検索前処理
 ************************************************************/
DELETE FROM wk_shisaku_genryo_delivery
WHERE dt_toroku <= (GETDATE()-7);

/************************************************************
 シサクイックの原料取得
 ************************************************************/
BEGIN
--/* キャッシュクリア */
--DBCC DROPCLEANBUFFERS;
--DBCC FREEPROCCACHE;
SET @top = @skip + @top

IF @flg_shiyo = 0
SET @flg_shiyo = NULL

IF @top = 0
SET @top = NULL

IF @cd_kaisha = 0
SET @cd_kaisha = NULL

IF @id_session <> 0
SET @cd_kaisha = NULL

;WITH
vw_shisaquick_genryo AS (
SELECT
	id_session
	,cd_kaisha
	,cd_kojo AS cd_busho
	,sort_kotei AS sort_key1
	,sort_genryo AS sort_key2
	,cd_genryo
	,nm_genryo
	,quantity
	,ROW_NUMBER() OVER(ORDER BY sort_kotei, sort_genryo) AS ROWNUMBER
FROM wk_shisaku_genryo_delivery
WHERE @id_session <> 0 AND id_session = @id_session

UNION 

SELECT
     0 AS id_session
	,ge.cd_kaisha
	,geko.cd_busho
	,NULL AS sort_key1
	,NULL AS sort_key2
	,ge.cd_genryo
	,geko.nm_genryo
	,NULL AS quantity
	,ROW_NUMBER() OVER(ORDER BY ge.cd_genryo, geko.cd_busho) AS ROWNUMBER
FROM ma_genryo ge

/* 原料工場マスタとJOIN */
INNER JOIN ma_genryokojo geko
ON ge.cd_kaisha = geko.cd_kaisha
AND ge.cd_genryo = geko.cd_genryo

WHERE @id_session = 0
	AND(@cd_kaisha IS NULL OR @cd_kaisha IS NOT NULL AND ge.cd_kaisha = @cd_kaisha)
	AND(@cd_busho IS NULL  OR @cd_busho  IS NOT NULL AND geko.cd_busho = @cd_busho)
	AND(@cd_genryo IS NULL OR ge.cd_genryo LIKE N'%'+@cd_genryo+'%')
	AND(@nm_genryo IS NULL OR geko.nm_genryo LIKE '%'+@nm_genryo+'%')

	/* 新規原料□  既存原料□  または 新規原料? 既存原料? */
	AND (@checkNGenryo = @checkGenryo
	/* 新規原料? 既存原料□  */
	OR   @checkNGenryo = 1 AND ge.cd_genryo     LIKE 'N%'
	/* 新規原料□  既存原料? */
	OR   @checkGenryo = 1  AND ge.cd_genryo NOT LIKE 'N%')
	AND (@flg_shiyo IS NULL OR @flg_shiyo IS NOT NULL AND geko.flg_shiyo =  @flg_shiyo)
)

SELECT
	  /* 原料マスタ(シサクイック)の出力項目 */
           s.cd_genryo
		  ,s.nm_genryo
          ,s.cd_kaisha
		  ,s.cd_busho
          ,bu.nm_busho
		  ,CASE /* 三ヶ月 */
             WHEN ge.cd_genryo LIKE 'N%' THEN '－'
             WHEN geko.flg_shiyo = '0' THEN '×'
             WHEN geko.flg_shiyo = '1' THEN '○'
             ELSE ''
           END AS shiyo
          ,CASE geko.flg_mishiyo /* 未使用 */
             WHEN '0' THEN ''
             WHEN '1' THEN '○'
             ELSE ''
           END AS mishiyo
          ,ge.ritu_sakusan
          ,ge.ritu_shokuen
          ,ge.ritu_sousan
          ,ge.ritu_abura
          ,ge.ritu_msg
          ,ge.hyojian
          ,ge.tenkabutu
		  ,g.RMLABELSAMPLE
		  ,g.AMS_YO
          ,g.AMS_FUYO
          ,g.ALG_ALR
          ,g.ALG_FUYO
          ,ge.memo
		  ,ge.nisugata_hyoji
          ,ge.rank_ibutsu
          ,ge.shiyokahi_genryo
          ,ge.shiyokahi_riyu
          ,ge.shiyokahi_cd_genryo_daitai
          ,ge.shiyokahi_nm_genryo_daitai
          ,ge.trouble_joho
          ,ge.trouble_gaiyo
          ,ge.trouble_naiyo_shosai
          ,ge.sakkin_chomieki_yohi
          ,ge.sakkin_chomieki_joken
          ,ge.NB_genteigenryo
          ,ge.NB_joken
          ,ge.NB_riyu
          ,ge.kasseikoso
          ,ge.gosei_tenkabutu
          ,ge.biko
          ,CONVERT(NVARCHAR, geko.dt_koshin, 111) AS dt_koshin
          ,ge.cd_kakutei
          ,CASE ge.kbn_haishi /* 廃止区分 */
             WHEN '0' THEN '使用可能'
             WHEN '1' THEN '廃止'
             ELSE ''
           END AS haishi
          ,s.quantity
		  ,s.sort_key1
		  ,s.sort_key2
		  ,s.ROWNUMBER
		  ,(SELECT MAX(ROWNUMBER) FROM vw_shisaquick_genryo) AS COUNT_ALL
		  ,geko.dt_toroku
		  ,geko.id_toroku
		  ,geko.id_koshin
		  ,geko.ts
/*原料マスタとJOIN*/
FROM vw_shisaquick_genryo AS s
LEFT JOIN ma_genryo AS ge
ON s.cd_genryo = ge.cd_genryo
AND s.cd_kaisha = ge.cd_kaisha

/*原料工場マスタとJOIN*/
LEFT JOIN ma_genryokojo AS geko
ON geko.cd_kaisha = ge.cd_kaisha
AND geko.cd_genryo = ge.cd_genryo
AND geko.cd_busho = s.cd_busho

/*部署マスタとJOIN*/
LEFT JOIN ma_busho bu
ON s.cd_kaisha = bu.cd_kaisha
AND s.cd_busho  = bu.cd_busho

/* シサクイックとG-Merの原料データをJOIN */
LEFT JOIN dbo.fn_gmer_genryo(@cd_kaisha) AS g
ON s.cd_kaisha = g.cd_kaisha
AND s.cd_genryo = g.cd_genryo COLLATE japanese_CS_AS_KS_WS

WHERE 
	s.ROWNUMBER > @skip 
	AND s.ROWNUMBER <= @top
ORDER BY s.ROWNUMBER

END
























