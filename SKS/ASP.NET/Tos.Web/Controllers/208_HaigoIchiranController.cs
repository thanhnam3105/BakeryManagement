using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Web;
using System.Net.Http;
using System.Web.Http;
using System.Web.Http.OData;
using System.Web.Http.OData.Query;
using Tos.Web.Data;
using Tos.Web.DataFP;
using System.Data.SqlClient;
using System.Collections;
using System.Data;

namespace Tos.Web.Controllers
{
    //created from 【SearchInputController(Ver1.7)】 Template
    public class HaigoIchiranController : ApiController
    {
        #region "Controllerで公開するAPI"
        //Tab search
        //配合情報で検索
        private readonly short HaigoTab = 1;
        //展開情報で検索
        private readonly short TenkaiTab = 2;
        //原料情報で検索
        private readonly short GenryoTab = 3;
        public readonly string FlgFlase = "0";
        public readonly string FlgTrue = "1";
        public readonly string NoHanFirst = "1";
        public readonly string KubunShikakari = "3";
        public readonly string NameShikakari = "配";
        public readonly string KubunHaigo = "4";
        public readonly string NameHaigo = "仕";

        /// <summary>
        /// パラメータで受け渡された target のキー項目をもとに target情報を取得します。
        /// </summary>
        /// <param name="id"> target のキー項目</param>
        /// <returns> target 情報</returns>
        public StoredProcedureResult<ResultData> Get([FromUri] HaigoIchiranPara value)
        {
            // TODO: target 情報を管理しているDbContextと target の型を指定します。
            StoredProcedureResult<ResultData> results = new StoredProcedureResult<ResultData>();
            CommonController common = new CommonController();
            if (!common.CheckKaishaKojyo(value.cd_kaisha, value.cd_kojyo))
            {
                results.Count = 0;
                results.Items = null;
                return results;
            }
            using (FOODPROCSEntities context = new FOODPROCSEntities(value.cd_kaisha, value.cd_kojyo))
             {
                 context.Configuration.ProxyCreationEnabled = false;

                var declareValue = " DECLARE @start NUMERIC = @skip + 1,";
                    declareValue = declareValue + " @end NUMERIC = @top  + @skip;";
                    declareValue = declareValue + " DECLARE @dateSystem DATETIME = CONVERT(VARCHAR(10), GETDATE(), 111);";

                var tableTemp = " CREATE TABLE #haigomei( cd_haigo VARCHAR(13) );";
                var insertTableTemp = " INSERT INTO #haigomei ( cd_haigo )";
                insertTableTemp = insertTableTemp + " SELECT cd_haigo";
                insertTableTemp = insertTableTemp + " FROM ma_haigo_mei_hyoji haigo_hyoji";
                insertTableTemp = insertTableTemp + " WHERE haigo_hyoji.flg_seiho_base = @FlgTrue";
                insertTableTemp = insertTableTemp + " GROUP BY haigo_hyoji.cd_haigo;";

                var query = "";
                if (value.m_kirikae == Properties.Resources.m_kirikae_hyoji)
                {
                    if (value.kansakujyoho == HaigoTab) { 
                        
                        query = query + " WITH CTE AS (";
                        query = query + " SELECT DISTINCT";
				        query = query + " haigo_hyoji.cd_haigo";
				        query = query + " , haigo_hyoji.nm_haigo";
				        query = query + " , haigo_hyoji.no_seiho";
				        query = query + " , haigo_hyoji.nm_seiho";
				        query = query + " , CASE ";
                        query = query + " WHEN haigo_hyoji.kbn_hin = @KubunShikakari THEN @NameShikakari";
                        query = query + " WHEN haigo_hyoji.kbn_hin = @KubunHaigo THEN @NameHaigo";
				        query = query + " END AS nm_kbn_hin";
				        query = query + " , haigo_hyoji.no_han";
				        query = query + " , haigo_hyoji.ritsu_kihon";
				        query = query + " , haigo_hyoji.flg_hinkan";
				        query = query + " , haigo_hyoji.flg_seizo";
				        query = query + " , haigo_hyoji.flg_mishiyo";
				        query = query + " , CASE";
					    query = query + " WHEN haigo_mei.cd_haigo IS NULL THEN 0";
					    query = query + " ELSE 1";
				        query = query + " END AS chk_haigo";
				        query = query + " , haigo_hyoji.qty_haigo_h";
                        query = query + " , CONVERT(VARCHAR(10), haigo_hyoji.dt_from, 111) AS dt_from";
                        query = query + " , CONVERT(VARCHAR(10), haigo_hyoji.dt_to, 111) AS dt_to";
				        query = query + " , haigo_hyoji.flg_seiho_base";

				        query = query + " , CASE";
					    query = query + " WHEN haigo_original.cd_haigo IS NOT NULL THEN 1";
					    query = query + " ELSE 0";
				        query = query + " END AS flg_original";

			            query = query + " FROM ma_haigo_mei_hyoji haigo_hyoji";

			            query = query + " LEFT JOIN ma_haigo_mei haigo_mei";
			            query = query + " ON haigo_hyoji.cd_haigo = haigo_mei.cd_haigo";
			            query = query + " AND haigo_hyoji.no_han = haigo_mei.no_han";
			            query = query + " AND haigo_mei.flg_sakujyo = 0";

			            query = query + " LEFT JOIN ma_seihin seihin";
                        query = query + " ON haigo_hyoji.cd_haigo = seihin.cd_haigo_hyoji";
                        query = query + " AND seihin.flg_sakujyo = @FlgFlase";
                        query = query + " AND seihin.flg_mishiyo = @FlgFlase";

			            query = query + " LEFT JOIN #haigomei haigo_original";
			            query = query + " ON haigo_hyoji.cd_haigo = haigo_original.cd_haigo";

                        query = query + " WHERE";
                        query = query + " haigo_hyoji.flg_sakujyo = @FlgFlase";
                        //配合コード指定";
                        query = query + " AND (@cd_haigo IS NULL OR haigo_hyoji.cd_haigo = @cd_haigo)";
                        //製法番号(XXXX-XXX-XX-XXXX)";
                        query = query + " AND (@no_seiho_kaisha IS NULL OR SUBSTRING(haigo_hyoji.no_seiho, 1, 4) = @no_seiho_kaisha)";
                        query = query + " AND (@no_seiho_shurui IS NULL OR SUBSTRING(haigo_hyoji.no_seiho, 6, 3) = @no_seiho_shurui)";
                        query = query + " AND (@no_seiho_nen IS NULL OR SUBSTRING(haigo_hyoji.no_seiho, 10, 2) = @no_seiho_nen)	";
                        query = query + " AND (@no_seiho_renban IS NULL OR SUBSTRING(haigo_hyoji.no_seiho, 13, 4) = @no_seiho_renban)	";
                        //code and name 配合名/コード";
                        query = query + " AND ( @nm_haigo IS NULL OR (haigo_hyoji.nm_haigo LIKE ('%!'+@nm_haigo+'%') ESCAPE '!')";
                        query = query + " OR (haigo_hyoji.cd_haigo LIKE ('%!'+@nm_haigo+'%') ESCAPE '!') )";
                        //code and name 製品名/コード";
                        query = query + " AND ( @nm_seihin IS NULL OR (seihin.nm_hin LIKE ('%!'+@nm_seihin+'%') ESCAPE '!')";
                        query = query + " OR (seihin.cd_hin LIKE ('%!'+@nm_seihin+'%') ESCAPE '!') )";
                        //品区分";
                        query = query + " AND (@kbn_hin IS NULL OR haigo_hyoji.kbn_hin = @kbn_hin)";
                        //仕掛品分類";
                        query = query + " AND (@cd_bunrui IS NULL OR haigo_hyoji.cd_bunrui = @cd_bunrui)";
                        //品管未確認のみ";
                        query = query + " AND (@flg_hinkan IS NULL OR haigo_hyoji.flg_hinkan = @flg_hinkan)";
                        //製造未確認のみ";
                        query = query + " AND (@flg_seizo IS NULL OR haigo_hyoji.flg_seizo = @flg_seizo)";
                        //原本も含む";
                        query = query + " AND (@flg_seiho_base IS NULL OR ISNULL(haigo_hyoji.flg_seiho_base, 0) = @flg_seiho_base)";
                        //未使用を含む";
                        query = query + " AND (@flg_mishiyo IS NULL OR haigo_hyoji.flg_mishiyo = @flg_mishiyo)";
                        //現在有効な版を対象";
                        query = query + " AND (@flg_genzai IS NULL ";
                        query = query + " OR ( haigo_hyoji.dt_from <= @dateSystem";
                        query = query + " AND haigo_hyoji.dt_to >= @dateSystem";
                        query = query + " AND (";
                        query = query + " (haigo_hyoji.flg_mishiyo = @FlgFlase";
                        query = query + " AND haigo_hyoji.no_han > 1)";

                        query = query + " OR (haigo_hyoji.no_han = @NoHanFirst ";
                        query = query + " AND NOT EXISTS( SELECT ";
                        query = query + " haigo_hyoji_exists.cd_haigo";
                        query = query + " FROM ma_haigo_mei_hyoji haigo_hyoji_exists";
                        query = query + " WHERE haigo_hyoji.cd_haigo = haigo_hyoji_exists.cd_haigo";
                        query = query + " AND haigo_hyoji_exists.flg_sakujyo = @FlgFlase";
                        query = query + " AND haigo_hyoji_exists.flg_mishiyo = @FlgFlase";
                        query = query + " AND @dateSystem BETWEEN haigo_hyoji_exists.dt_from AND haigo_hyoji_exists.dt_to";
                        query = query + " AND haigo_hyoji_exists.no_han > 1";
                        query = query + " ))))))";

                    }
                    else if (value.kansakujyoho == TenkaiTab)
                    {
                        query = query + " WITH CTE AS (";
                        query = query + " SELECT DISTINCT ";
                        query = query + " haigo_hyoji.cd_haigo";
                        query = query + " , haigo_hyoji.nm_haigo";
                        query = query + " , haigo_hyoji.no_seiho";
                        query = query + " , haigo_hyoji.nm_seiho";
                        query = query + " , haigo_hyoji.nm_kbn_hin";
                        query = query + " , haigo_hyoji.no_han";
                        query = query + " , haigo_hyoji.ritsu_kihon";
                        query = query + " , haigo_hyoji.flg_hinkan";
                        query = query + " , haigo_hyoji.flg_seizo";
                        query = query + " , haigo_hyoji.flg_mishiyo";
                        query = query + " , haigo_hyoji.chk_haigo";
                        query = query + " , haigo_hyoji.qty_haigo_h";
                        query = query + " , haigo_hyoji.dt_from";
                        query = query + " , haigo_hyoji.dt_to";
                        query = query + " , haigo_hyoji.flg_seiho_base";
                        query = query + " , 0 AS flg_original";
                        query = query + " FROM fnHaigoichiranTenkai(@id_system, @dt_hidzuke, @cd_haigo_parttwo, @cd_hin_parttwo, 10) AS haigo_hyoji)";
			
                    }
                    else if(value.kansakujyoho == GenryoTab) {
                        query = query + " WITH TENPURU AS (";
                        query = query + " SELECT haigo_recipe_hyoji.cd_haigo";
                        query = query + " , haigo_recipe_hyoji.no_han";
                        query = query + " , haigo_recipe_hyoji.cd_hin";
                        query = query + " , haigo_recipe_hyoji.kbn_hin";
                        query = query + " , haigo_recipe_hyoji.nm_hin AS nm_hin_recipe_hyoji";
                        query = query + " , haigo_recipe_hyoji.qty_haigo_h";
                        query = query + " , genshizai_seihin.nm_hin";
                        query = query + " , genshizai_seihin.no_kikaku";
                        query = query + " , genshizai_seihin.nm_kikaku";
                        query = query + " , genshizai_seihin.nm_hanbai";
                        query = query + " , genshizai_seihin.nm_seizo";
                        query = query + " FROM ma_haigo_recipe_hyoji haigo_recipe_hyoji";

                        query = query + " LEFT JOIN (";
                        query = query + " SELECT genshizai.cd_hin";
                        query = query + " , genshizai.nm_hin";
                        query = query + " , genshizai.no_kikaku";
                        query = query + " , genshizai.nm_kikaku";
                        query = query + " , genshizai.nm_hanbai";
                        query = query + " , genshizai.nm_seizo ";
                        query = query + " FROM ma_genshizai genshizai";
                        query = query + " WHERE genshizai.flg_mishiyo = @FlgFlase";
                        query = query + " AND genshizai.flg_sakujyo = @FlgFlase";
                        query = query + " UNION ALL";
                        query = query + " SELECT seihin.cd_hin";
                        query = query + " , seihin.nm_hin";
                        query = query + " , seihin.no_kikaku";
                        query = query + " , seihin.nm_kikaku";
                        query = query + " , seihin.nm_hanbai";
                        query = query + " , seihin.nm_seizo ";
                        query = query + " FROM ma_seihin seihin";

                        query = query + " WHERE seihin.kbn_seihin = 1";
                        query = query + " AND seihin.flg_mishiyo = @FlgFlase";
                        query = query + " AND seihin.flg_sakujyo = @FlgFlase";
                        query = query + " ) genshizai_seihin";
                        query = query + " ON haigo_recipe_hyoji.cd_hin = genshizai_seihin.cd_hin";

                        query = query + " WHERE haigo_recipe_hyoji.flg_mishiyo = @FlgFlase";
                        query = query + " AND haigo_recipe_hyoji.flg_sakujyo = @FlgFlase";
                        query = query + " ),";

                        query = query + " CTE AS( SELECT DISTINCT";
                        query = query + " haigo_mei_hyoji.cd_haigo";
                        query = query + " , haigo_mei_hyoji.nm_haigo";
                        query = query + " , haigo_mei_hyoji.no_seiho";
                        query = query + " , haigo_mei_hyoji.nm_seiho";
                        query = query + " , CASE WHEN haigo_mei_hyoji.kbn_hin = @KubunShikakari THEN @NameShikakari";
                        query = query + " WHEN haigo_mei_hyoji.kbn_hin = @KubunHaigo THEN @NameHaigo";
                        query = query + " END AS nm_kbn_hin";
                        query = query + " , haigo_mei_hyoji.no_han";
                        query = query + " , haigo_mei_hyoji.ritsu_kihon";
                        query = query + " , haigo_mei_hyoji.flg_hinkan";
                        query = query + " , haigo_mei_hyoji.flg_seizo";
                        query = query + " , haigo_mei_hyoji.flg_mishiyo";
                        query = query + " , CASE WHEN haigo_mei.cd_haigo IS NULL THEN 0";
                        query = query + " ELSE 1 END AS chk_haigo";
                        query = query + " , haigo_mei_hyoji.qty_haigo_h";
                        query = query + " , CONVERT(VARCHAR(10), haigo_mei_hyoji.dt_from, 111) AS dt_from";
                        query = query + " , CONVERT(VARCHAR(10), haigo_mei_hyoji.dt_to, 111) AS dt_to";
                        query = query + " , haigo_mei_hyoji.flg_seiho_base";
                        query = query + " , CASE WHEN haigo_original.cd_haigo IS NOT NULL THEN 1";
                        query = query + " ELSE 0";
                        query = query + " END AS flg_original";
                        query = query + " FROM ma_haigo_mei_hyoji haigo_mei_hyoji";

                        query = query + " INNER JOIN TENPURU tenpuru";
                        query = query + " ON haigo_mei_hyoji.cd_haigo = tenpuru.cd_haigo";
                        query = query + " AND haigo_mei_hyoji.no_han = tenpuru.no_han";
                        //query = query + " AND haigo_mei_hyoji.qty_haigo_h = tenpuru.qty_haigo_h";

                        query = query + " LEFT JOIN ma_haigo_mei haigo_mei";
                        query = query + " ON haigo_mei_hyoji.cd_haigo = haigo_mei.cd_haigo";
                        query = query + " AND haigo_mei_hyoji.no_han = haigo_mei.no_han";
                        query = query + " AND haigo_mei.flg_sakujyo = @FlgFlase";

                        query = query + " LEFT JOIN #haigomei haigo_original";
                        query = query + " ON haigo_mei_hyoji.cd_haigo = haigo_original.cd_haigo";

                        query = query + " WHERE haigo_mei_hyoji.flg_sakujyo = @FlgFlase";
                        //原料コード
                        query = query + " AND (@cd_genryo IS NULL OR tenpuru.cd_hin = @cd_genryo)  ";
                        query = query + " AND (@kbn_hin_genryo IS NULL OR tenpuru.kbn_hin = @kbn_hin_genryo)";
                        //原料名
                        query = query + " AND (( @nm_genryo_text IS NULL OR (tenpuru.nm_hin LIKE ('%!'+@nm_genryo_text+'%') ESCAPE '!') )";
                        query = query + " OR ( @nm_genryo_text IS NULL OR (tenpuru.nm_hin_recipe_hyoji LIKE ('%!'+@nm_genryo_text+'%') ESCAPE '!') ))";
                        //企画書No.
                        query = query + " AND (@cd_kikaku IS NULL OR tenpuru.no_kikaku LIKE ( + @cd_kikaku + '%'))";
                        //規格書商品名
                        query = query + " AND (@nm_kikaku_shohin IS NULL OR (tenpuru.nm_kikaku LIKE ('%!'+@nm_kikaku_shohin+'%') ESCAPE '!'))";
                        //製造社名
                        query = query + " AND (@nm_seiho_mei IS NULL OR (tenpuru.nm_seizo LIKE ('%!'+@nm_seiho_mei+'%') ESCAPE '!'))";
                        //販売社名
                        query = query + " AND (@nm_hanbai_mei IS NULL OR (tenpuru.nm_hanbai LIKE ('%!'+@nm_hanbai_mei+'%') ESCAPE '!')))";
                    }
                }
                else {
                    if (value.kansakujyoho == HaigoTab)
                    {
                        query = query + " WITH CTE AS (";
                        query = query + " SELECT DISTINCT";
                        query = query + " haigo_hyoji.cd_haigo";
                        query = query + " , haigo_hyoji.nm_haigo";
                        query = query + " , haigo_hyoji.no_seiho";
                        query = query + " , haigo_hyoji.nm_seiho";
                        query = query + " , CASE WHEN haigo_hyoji.kbn_hin = @KubunShikakari THEN @NameShikakari";
                        query = query + " WHEN haigo_hyoji.kbn_hin = @KubunHaigo THEN @NameHaigo";
                        query = query + " END AS nm_kbn_hin";
                        query = query + " , haigo_hyoji.no_han";
                        query = query + " , MAX(haigo_hyoji.no_han) OVER(PARTITION BY haigo_hyoji.cd_haigo) AS max_no_han";
                        query = query + " , haigo_hyoji.ritsu_kihon";
                        query = query + " , haigo_hyoji.flg_hinkan";
                        query = query + " , haigo_hyoji.flg_seizo";
                        query = query + " , haigo_hyoji.flg_mishiyo";
                        query = query + " , CASE WHEN haigo_mei.cd_haigo IS NULL THEN 0";
                        query = query + " ELSE 1 END AS chk_haigo";
                        query = query + " , haigo_hyoji.qty_haigo_h";
                        query = query + " , CONVERT(VARCHAR(10), haigo_hyoji.dt_from, 111) AS dt_from";
                        query = query + " , CONVERT(VARCHAR(10), haigo_hyoji.dt_to, 111) AS dt_to";
                        query = query + " , haigo_hyoji.flg_seiho_base";
                        query = query + " , 0 AS flg_original";

                        query = query + " FROM ma_haigo_mei haigo_hyoji";

                        query = query + " LEFT JOIN ma_haigo_mei_hyoji haigo_mei";
                        query = query + " ON haigo_hyoji.cd_haigo = haigo_mei.cd_haigo";
                        query = query + " AND haigo_hyoji.no_han = haigo_mei.no_han";
                        query = query + " AND haigo_mei.flg_sakujyo = @FlgFlase";

                        query = query + " LEFT JOIN ma_seihin seihin";
                        query = query + " ON haigo_hyoji.cd_haigo = seihin.cd_haigo";
                        query = query + " AND seihin.flg_sakujyo = @FlgFlase";
                        query = query + " AND seihin.flg_mishiyo = @FlgFlase";

                        query = query + " WHERE haigo_hyoji.flg_sakujyo = @FlgFlase";
                        query = query + " AND haigo_hyoji.qty_kihon = haigo_hyoji.qty_haigo_h";
                        //配合コード指定
                        query = query + " AND (@cd_haigo IS NULL OR haigo_hyoji.cd_haigo = @cd_haigo)";
                        //製法番号(XXXX-XXX-XX-XXXX)
                        query = query + " AND (@no_seiho_kaisha IS NULL OR SUBSTRING(haigo_hyoji.no_seiho, 1, 4) = @no_seiho_kaisha)";	
                        query = query + " AND (@no_seiho_shurui IS NULL OR SUBSTRING(haigo_hyoji.no_seiho, 6, 3) = @no_seiho_shurui)";			
                        query = query + " AND (@no_seiho_nen IS NULL OR SUBSTRING(haigo_hyoji.no_seiho, 10, 2) = @no_seiho_nen)	";
                        query = query + " AND (@no_seiho_renban IS NULL OR SUBSTRING(haigo_hyoji.no_seiho, 13, 4) = @no_seiho_renban)";		
                        //code and name 配合名/コード
                        query = query + " AND ( @nm_haigo IS NULL OR (haigo_hyoji.nm_haigo LIKE ('%!'+@nm_haigo+'%') ESCAPE '!')";
                        query = query + " OR (haigo_hyoji.cd_haigo LIKE ('%!'+@nm_haigo+'%') ESCAPE '!') )";
                        //code and name 製品名/コード
                        query = query + " AND ( @nm_seihin IS NULL OR (seihin.nm_hin LIKE ('%!'+@nm_seihin+'%') ESCAPE '!')";
                        query = query + " OR (seihin.cd_hin LIKE ('%!'+@nm_seihin+'%') ESCAPE '!') )";
                        //品区分
                        query = query + " AND (@kbn_hin IS NULL OR haigo_hyoji.kbn_hin = @kbn_hin)";
                        //仕掛品分類
                        query = query + " AND (@cd_bunrui IS NULL OR haigo_hyoji.cd_bunrui = @cd_bunrui)";
                        //品管未確認のみ
                        query = query + " AND (@flg_hinkan IS NULL OR haigo_hyoji.flg_hinkan = @flg_hinkan)";
                        //製造未確認のみ
                        query = query + " AND (@flg_seizo IS NULL OR haigo_hyoji.flg_seizo = @flg_seizo)";
                        //原本も含む
                        query = query + " AND (@flg_seiho_base IS NULL OR ISNULL(haigo_hyoji.flg_seiho_base, 0) = @flg_seiho_base)";
                        //未使用を含む
                        query = query + " AND (@flg_mishiyo IS NULL OR haigo_hyoji.flg_mishiyo = @flg_mishiyo)";
                        //現在有効な版を対象
                        query = query + " AND (@flg_genzai IS NULL"; 
                        query = query + " OR ( haigo_hyoji.dt_from <= @dateSystem";
                        query = query + " AND haigo_hyoji.dt_to >= @dateSystem";
                        query = query + " AND ( (haigo_hyoji.flg_mishiyo = @FlgFlase";
                        query = query + " AND haigo_hyoji.no_han > 1)";

                        query = query + " OR (haigo_hyoji.no_han = @NoHanFirst ";
                        query = query + " AND NOT EXISTS(";
                        query = query + " SELECT haigo_hyoji_exists.cd_haigo";
                        query = query + " FROM ma_haigo_mei haigo_hyoji_exists";
                        query = query + " WHERE  haigo_hyoji.cd_haigo = haigo_hyoji_exists.cd_haigo";
                        query = query + " AND haigo_hyoji_exists.flg_sakujyo = @FlgFlase";
                        query = query + " AND haigo_hyoji_exists.flg_mishiyo = @FlgFlase";
                        query = query + " AND haigo_hyoji_exists.qty_kihon = haigo_hyoji_exists.qty_haigo_h";
                        query = query + " AND @dateSystem BETWEEN haigo_hyoji_exists.dt_from AND haigo_hyoji_exists.dt_to";
                        query = query + " AND haigo_hyoji_exists.no_han > 1";
                        query = query + " ))))))";
                    }
                    else if (value.kansakujyoho == TenkaiTab)
                    {
                        query = query + " WITH CTE AS (";
                        query = query + " SELECT DISTINCT ";
                        query = query + " haigo_hyoji.cd_haigo";
                        query = query + " , haigo_hyoji.nm_haigo";
                        query = query + " , haigo_hyoji.no_seiho";
                        query = query + " , haigo_hyoji.nm_seiho";
                        query = query + " , haigo_hyoji.nm_kbn_hin";
                        query = query + " , haigo_hyoji.no_han";
                        query = query + " , haigo_hyoji.ritsu_kihon";
                        query = query + " , haigo_hyoji.flg_hinkan";
                        query = query + " , haigo_hyoji.flg_seizo";
                        query = query + " , haigo_hyoji.flg_mishiyo";
                        query = query + " , haigo_hyoji.chk_haigo";
                        query = query + " , haigo_hyoji.qty_haigo_h";
                        query = query + " , haigo_hyoji.dt_from";
                        query = query + " , haigo_hyoji.dt_to";
                        query = query + " , haigo_hyoji.flg_seiho_base";
                        query = query + " , 0 AS flg_original";
                        query = query + " FROM fnHaigoichiranTenkai(@id_system, @dt_hidzuke, @cd_haigo_parttwo, @cd_hin_parttwo, 10) AS haigo_hyoji)";
                    }
                    else if (value.kansakujyoho == GenryoTab)
                    {
                        query = query + " WITH TENPURU AS (";
                        query = query + " SELECT haigo_recipe.cd_haigo";
                        query = query + " , haigo_recipe.no_han";
                        query = query + " , haigo_recipe.cd_hin";
                        query = query + " , haigo_recipe.kbn_hin";
                        query = query + " , haigo_recipe.nm_hin AS nm_hin_recipe";
                        query = query + " , haigo_recipe.qty_haigo_h";
                        query = query + " , genshizai_seihin.nm_hin";
                        query = query + " , genshizai_seihin.no_kikaku";
                        query = query + " , genshizai_seihin.nm_kikaku";
                        query = query + " , genshizai_seihin.nm_hanbai";
                        query = query + " , genshizai_seihin.nm_seizo";
                        query = query + " FROM ma_haigo_recipe haigo_recipe";

                        query = query + " LEFT JOIN (";
                        query = query + " SELECT genshizai.cd_hin";
                        query = query + " , genshizai.nm_hin";
                        query = query + " , genshizai.no_kikaku";
                        query = query + " , genshizai.nm_kikaku";
                        query = query + " , genshizai.nm_hanbai";
                        query = query + " , genshizai.nm_seizo ";
                        query = query + " FROM ma_genshizai genshizai";

                        query = query + " WHERE genshizai.flg_mishiyo = @FlgFlase";
                        query = query + " AND genshizai.flg_sakujyo = @FlgFlase";
                        query = query + " UNION ALL";
                        query = query + " SELECT seihin.cd_hin";
                        query = query + " , seihin.nm_hin";
                        query = query + " , seihin.no_kikaku";
                        query = query + " , seihin.nm_kikaku";
                        query = query + " , seihin.nm_hanbai";
                        query = query + " , seihin.nm_seizo ";
                        query = query + " FROM ma_seihin seihin";

                        query = query + " WHERE seihin.kbn_seihin = 1";
                        query = query + " AND seihin.flg_mishiyo = @FlgFlase";
                        query = query + " AND seihin.flg_sakujyo = @FlgFlase";
                        query = query + " ) genshizai_seihin";
                        query = query + " ON haigo_recipe.cd_hin = genshizai_seihin.cd_hin";
                        query = query + " WHERE haigo_recipe.flg_mishiyo = @FlgFlase";
                        query = query + " AND haigo_recipe.flg_sakujyo = @FlgFlase";
                        query = query + " ),";
                        query = query + " CTE AS(";
                        query = query + " SELECT DISTINCT";
                        query = query + " haigo_mei.cd_haigo";
                        query = query + " , haigo_mei.nm_haigo";
                        query = query + " , haigo_mei.no_seiho";
                        query = query + " , haigo_mei.nm_seiho";
                        query = query + " , CASE WHEN haigo_mei.kbn_hin = @KubunShikakari THEN @NameShikakari";
                        query = query + " WHEN haigo_mei.kbn_hin = @KubunHaigo THEN @NameHaigo";
                        query = query + " END AS nm_kbn_hin";
                        query = query + " , haigo_mei.no_han";
                        query = query + " , haigo_mei.ritsu_kihon";
                        query = query + " , haigo_mei.flg_hinkan";
                        query = query + " , haigo_mei.flg_seizo";
                        query = query + " , haigo_mei.flg_mishiyo";
                        query = query + " , CASE WHEN haigo_mei_hyoji.cd_haigo IS NULL THEN 0";
                        query = query + " ELSE 1 END AS chk_haigo";
                        query = query + " , haigo_mei.qty_haigo_h";
                        query = query + " , CONVERT(VARCHAR(10), haigo_mei.dt_from, 111) AS dt_from";
                        query = query + " , CONVERT(VARCHAR(10), haigo_mei.dt_to, 111) AS dt_to";
                        query = query + " , haigo_mei.flg_seiho_base";
                        query = query + " , 0 AS flg_original";
                        query = query + " FROM ma_haigo_mei haigo_mei";

                        query = query + " INNER JOIN TENPURU tenpuru";
                        query = query + " ON haigo_mei.cd_haigo = tenpuru.cd_haigo";
                        query = query + " AND haigo_mei.no_han = tenpuru.no_han";
                        query = query + " AND haigo_mei.qty_haigo_h = tenpuru.qty_haigo_h";

                        query = query + " LEFT JOIN ma_haigo_mei_hyoji haigo_mei_hyoji";
                        query = query + " ON  haigo_mei.cd_haigo = haigo_mei_hyoji.cd_haigo";
                        query = query + " AND haigo_mei.no_han = haigo_mei_hyoji.no_han";
                        query = query + " AND haigo_mei_hyoji.flg_sakujyo = @FlgFlase";

                        query = query + " LEFT JOIN SS_vw_hin vw_hin";
                        query = query + " ON  tenpuru.cd_hin = vw_hin.cd_hin";
                        query = query + " AND tenpuru.kbn_hin = vw_hin.kbn_hin_toroku";

                        query = query + " WHERE haigo_mei.flg_sakujyo = @FlgFlase";
                        query = query + " AND haigo_mei.qty_kihon = haigo_mei.qty_haigo_h";
                        //原料コード
                        query = query + " AND (@cd_genryo IS NULL OR tenpuru.cd_hin = @cd_genryo) ";
                        query = query + " AND (@kbn_hin_genryo IS NULL OR vw_hin.kbn_hin = @kbn_hin_genryo)";
                        //原料名
                        query = query + " AND (( @nm_genryo_text IS NULL OR (tenpuru.nm_hin LIKE ('%!'+@nm_genryo_text+'%') ESCAPE '!') )";
                        query = query + " OR ( @nm_genryo_text IS NULL OR (tenpuru.nm_hin_recipe LIKE ('%!'+@nm_genryo_text+'%') ESCAPE '!') ))";
                        //企画書No.
                        query = query + " AND (@cd_kikaku IS NULL OR tenpuru.no_kikaku LIKE ( + @cd_kikaku + '%'))";
                        //規格書商品名
                        query = query + " AND (@nm_kikaku_shohin IS NULL OR (tenpuru.nm_kikaku LIKE ('%!'+@nm_kikaku_shohin+'%') ESCAPE '!'))";
                        //製造社名
                        query = query + " AND (@nm_seiho_mei IS NULL OR (tenpuru.nm_seizo LIKE ('%!'+@nm_seiho_mei+'%') ESCAPE '!'))";
                        //販売社名
                        query = query + " AND (@nm_hanbai_mei IS NULL OR (tenpuru.nm_hanbai LIKE ('%!'+@nm_hanbai_mei+'%') ESCAPE '!')))";
                    }
                }

                var searchData = " SELECT total_rows, RN, cd_haigo, nm_haigo, no_seiho";
                searchData = searchData + " , nm_seiho, nm_kbn_hin, no_han, ritsu_kihon, CAST(flg_hinkan AS BIT) AS flg_hinkan, CAST(flg_seizo AS BIT) AS flg_seizo, CAST(flg_mishiyo AS BIT) AS flg_mishiyo";
                searchData = searchData + " , CAST(chk_haigo AS BIT) AS chk_haigo, qty_haigo_h, dt_from, dt_to, CAST(flg_seiho_base AS BIT) AS flg_seiho_base, CAST(flg_original AS BIT) AS flg_original";
                searchData = searchData + " FROM (";
                searchData = searchData + " SELECT COUNT(RN) OVER() AS total_rows";
                searchData = searchData + " , RN, cd_haigo, nm_haigo, no_seiho, nm_seiho, nm_kbn_hin, no_han, ritsu_kihon";
                searchData = searchData + " , flg_hinkan, flg_seizo, flg_mishiyo, chk_haigo, qty_haigo_h, dt_from";
                searchData = searchData + " , dt_to, flg_seiho_base, flg_original";
                searchData = searchData + " FROM (";
                searchData = searchData + " SELECT ROW_NUMBER() OVER(ORDER BY ";
                searchData = searchData + " (CASE @kbn_sort WHEN 1 THEN cd_haigo END) ASC";
                searchData = searchData + " , (CASE @kbn_sort WHEN 2 THEN cd_haigo END) DESC";
                searchData = searchData + " , (CASE @kbn_sort WHEN 3 THEN no_seiho END) ASC";
                searchData = searchData + " , (CASE @kbn_sort WHEN 4 THEN no_seiho END) DESC";
                searchData = searchData + " , CASE @kbn_sort WHEN 3 THEN cd_haigo END";
                searchData = searchData + " , CASE @kbn_sort WHEN 4 THEN cd_haigo END";
                searchData = searchData + " , no_han) AS RN";
                searchData = searchData + " , cd_haigo, nm_haigo, no_seiho, nm_seiho, nm_kbn_hin, no_han";
                searchData = searchData + " , ritsu_kihon, flg_hinkan, flg_seizo, flg_mishiyo, chk_haigo, qty_haigo_h, dt_from, dt_to, flg_seiho_base, flg_original";
                searchData = searchData + " FROM CTE ";
                if (value.m_kirikae != Properties.Resources.m_kirikae_hyoji && value.kansakujyoho == HaigoTab)
                {
                    searchData = searchData + " WHERE @flg_genzai IS NULL OR no_han = max_no_han ";
                }
                searchData = searchData + " ) DATA) DATA";
                searchData = searchData + " WHERE (@skip = 0 AND @top = 0) OR ( RN BETWEEN @start AND @end ) ";



                var dropTempTable = "DROP TABLE #haigomei";

                var querySearch = "";
                if (value.m_kirikae == Properties.Resources.m_kirikae_hyoji && value.kansakujyoho != TenkaiTab)
                {
                    querySearch = declareValue + tableTemp + insertTableTemp + query + searchData + dropTempTable;
                }
                else {
                    querySearch = declareValue + query + searchData;
                }

                var parameters = new object[]
                {
                    new SqlParameter("@id_system", SqlDbType.SmallInt) { Value = value.id_system },
                    new SqlParameter("@kbn_sort", SqlDbType.SmallInt) { Value = value.kbn_sort },

                    new SqlParameter("@cd_haigo", SqlDbType.VarChar, 13) { Value = (object)value.cd_haigo ?? DBNull.Value },
                    new SqlParameter("@flg_seiho_base", SqlDbType.SmallInt) { Value = (object)value.flg_seiho_base ?? DBNull.Value },
                    new SqlParameter("@no_seiho_kaisha", SqlDbType.VarChar, 4) { Value = (object)value.no_seiho_kaisha ?? DBNull.Value },
                    new SqlParameter("@no_seiho_shurui", SqlDbType.VarChar, 3) { Value = (object)value.no_seiho_shurui ?? DBNull.Value },
                    new SqlParameter("@no_seiho_nen", SqlDbType.VarChar, 2) { Value = (object)value.no_seiho_nen ?? DBNull.Value },
                    new SqlParameter("@no_seiho_renban", SqlDbType.VarChar, 4) { Value = (object)value.no_seiho_renban ?? DBNull.Value },
                    new SqlParameter("@nm_haigo", SqlDbType.VarChar, 60) { Value = (object)value.nm_haigo ?? DBNull.Value },
                    new SqlParameter("@kbn_hin", SqlDbType.VarChar, 1) { Value = (object)value.kbn_hin ?? DBNull.Value },
                    new SqlParameter("@cd_bunrui", SqlDbType.VarChar, 2) { Value = (object)value.cd_bunrui ?? DBNull.Value },
                    new SqlParameter("@nm_seihin", SqlDbType.VarChar, 60) { Value = (object)value.nm_seihin ?? DBNull.Value },
                    new SqlParameter("@flg_hinkan", SqlDbType.SmallInt) { Value = (object)value.flg_hinkan ?? DBNull.Value },
                    new SqlParameter("@flg_seizo", SqlDbType.SmallInt) { Value = (object)value.flg_seizo ?? DBNull.Value },
                    new SqlParameter("@flg_genzai", SqlDbType.SmallInt) { Value = (object)value.flg_genzai ?? DBNull.Value },
                    new SqlParameter("@flg_mishiyo", SqlDbType.SmallInt) { Value = (object)value.flg_mishiyo ?? DBNull.Value },

                    new SqlParameter("@dt_hidzuke", SqlDbType.DateTime) { Value = (object)value.dt_hidzuke ?? DBNull.Value },
                    new SqlParameter("@cd_haigo_parttwo", SqlDbType.VarChar, 13) { Value = (object)value.cd_haigo_parttwo ?? DBNull.Value },
                    new SqlParameter("@cd_hin_parttwo", SqlDbType.VarChar, 13) { Value = (object)value.cd_hin_parttwo ?? DBNull.Value },

                    new SqlParameter("@cd_genryo", SqlDbType.VarChar, 13) { Value = (object)value.cd_genryo ?? DBNull.Value },
                    new SqlParameter("@kbn_hin_genryo", SqlDbType.VarChar, 1) { Value = (object)value.kbn_hin_genryo ?? DBNull.Value },
                    new SqlParameter("@nm_genryo_text", SqlDbType.VarChar, 60) { Value = (object)value.nm_genryo_text ?? DBNull.Value },
                    new SqlParameter("@cd_kikaku", SqlDbType.VarChar, 17) { Value = (object)value.cd_kikaku ?? DBNull.Value },
                    new SqlParameter("@nm_kikaku_shohin", SqlDbType.VarChar, 60) { Value = (object)value.nm_kikaku_shohin ?? DBNull.Value },
                    new SqlParameter("@nm_seiho_mei", SqlDbType.VarChar, 60) { Value = (object)value.nm_seiho_mei ?? DBNull.Value },
                    new SqlParameter("@nm_hanbai_mei", SqlDbType.VarChar, 60) { Value = (object)value.nm_hanbai_mei ?? DBNull.Value },

                    new SqlParameter("@skip", SqlDbType.Int) { Value = value.skip },
                    new SqlParameter("@top", SqlDbType.Int) { Value = value.top },

                    new SqlParameter("@FlgFlase", SqlDbType.VarChar, 1) { Value = FlgFlase },
                    new SqlParameter("@FlgTrue", SqlDbType.VarChar, 1) { Value = FlgTrue },
                    new SqlParameter("@NoHanFirst", SqlDbType.VarChar, 1) { Value = NoHanFirst },
                    new SqlParameter("@KubunShikakari", SqlDbType.VarChar, 1) { Value = KubunShikakari },
                    new SqlParameter("@NameShikakari", SqlDbType.VarChar, 2) { Value = NameShikakari },
                    new SqlParameter("@KubunHaigo", SqlDbType.VarChar, 1) { Value = KubunHaigo },
                    new SqlParameter("@NameHaigo", SqlDbType.VarChar, 2) { Value = NameHaigo }

                };

                results.Items = context.Database.SqlQuery<ResultData>(querySearch, parameters).ToList();
             }

            return results;
        }

        /// <summary>
        /// パラメータで受け渡された target 情報をもとにエントリーを一括更新（追加・更新・削除）します。
        /// </summary>
        /// <param name="value"></param>
        /// <returns></returns>
        public HttpResponseMessage Post([FromBody]ChangeSet<object/*TODO:targetの型を指定します*/> value)
        {
            if (value == null)
            {
                return Request.CreateErrorResponse(HttpStatusCode.InternalServerError, Properties.Resources.NotNullAllow);
            }

            // TODO: キー項目の重複チェックを行います。
            InvalidationSet<object/*TODO:targetの型を指定します*/> headerInvalidations = IsAlreadyExists(value);
            if (headerInvalidations.Count > 0)
            {
                return Request.CreateResponse<InvalidationSet<object/*TODO:targetの型を指定します*/>>(HttpStatusCode.BadRequest, headerInvalidations);
            }

            // TODO: 保存処理を実行します。
            var result = SaveData(value);
            return Request.CreateResponse<SearchInputChangeResponseNotUse>(HttpStatusCode.OK, result);

        }

        /// <summary>
        /// Check seiho。
        /// </summary>
        /// <param name="value"></param>
        /// <returns></returns>
        [HttpGet]
        public bool CheckSeiho(int cd_kaisha, int cd_kojyo, string no_seiho)
        { 
            bool result = false;
            using (ShohinKaihatsuEntities context = new ShohinKaihatsuEntities()) {
                var dataSeiho = context.ma_seiho_denso.Where(m => m.cd_kaisha == cd_kaisha && m.cd_kojyo == cd_kojyo && m.no_seiho == no_seiho && m.flg_denso_jyotai == true).FirstOrDefault();
                if (dataSeiho != null) {
                    result = true;
                }
            }
            return result;
        }

        /// <summary>
        /// Update delete data。
        /// </summary>
        /// <param name="value"></param>
        /// <returns></returns>
        [HttpGet]
        public bool DeleteData(string m_kirikae, int cd_kaisha, int cd_kojyo, string cd_haigo)
        {
            using (FOODPROCSEntities context = new FOODPROCSEntities(cd_kaisha, cd_kojyo))
            {
                context.Configuration.ProxyCreationEnabled = false;

                
                var query = "";
                query = query + " DECLARE @dateNow DATETIME = GETDATE();";
                if (m_kirikae == Properties.Resources.m_kirikae_hyoji)
                {
                    query = query + " UPDATE ma_haigo_mei_hyoji";
                }
                else {
                    query = query + " UPDATE ma_haigo_mei";
                }
                query = query + " SET ";
                query = query + " flg_sakujyo = 1";
                query = query + " , flg_mishiyo = 1";
                query = query + " , dt_henko = @dateNow";
                query = query + " , cd_koshin = @userLogin";
                query = query + " WHERE ";
                query = query + " cd_haigo = @cd_haigo";
                query = query + " AND flg_sakujyo = 0";

                if (m_kirikae == Properties.Resources.m_kirikae_hyoji)
                {
                    query = query + " UPDATE ma_haigo_recipe_hyoji";
                }
                else
                {
                    query = query + " UPDATE ma_haigo_recipe";
                }
                query = query + " SET ";
                query = query + " flg_sakujyo = 1";
                query = query + " , flg_mishiyo = 1";
                query = query + " , dt_henko = @dateNow";
                query = query + " , cd_koshin = @userLogin";
                query = query + " WHERE ";
                query = query + " cd_haigo = @cd_haigo";
                query = query + " AND flg_sakujyo = 0";

                if (m_kirikae == Properties.Resources.m_kirikae_hyoji)
                {
                    query = query + " UPDATE ma_seizo_line_hyoji";
                }
                else
                {
                    query = query + " UPDATE ma_seizo_line";
                }
                query = query + " SET ";
                query = query + " flg_sakujyo = 1";
                query = query + " , flg_mishiyo = 1";
                query = query + " , dt_henko = @dateNow";
                query = query + " , cd_koshin = @userLogin";
                query = query + " WHERE ";
                query = query + " cd_haigo = @cd_haigo";
                query = query + " AND flg_sakujyo = 0";

                string userName = decimal.Parse(UserInfo.GetUserNameFromIdentity(this.User.Identity)).ToString();
                var parameters = new object[]
                {
                    new SqlParameter("@cd_haigo", SqlDbType.VarChar, 13) { Value = cd_haigo },
                    new SqlParameter("@userLogin", SqlDbType.VarChar, 10) { Value = userName }
                };

                context.Database.ExecuteSqlCommand(query, parameters);
            }

            return true;
        }

        /// <summary>
        /// パラメーターで受け渡されたtarget情報を更新します
        /// </summary>
        /// <param name="value"></param>
        /// <returns></returns>
        public HttpResponseMessage Put([FromBody]ChangeSet<object/*TODO:targetの型を指定します*/> value)
        {

            var result = SaveData(value);
            return Request.CreateResponse<SearchInputChangeResponseNotUse>(HttpStatusCode.OK, result);

        }

        /// <summary>
        /// パラメーターで受け渡されたtarget情報を削除します
        /// </summary>
        /// <param name="value"></param>
        /// <returns></returns>
        public HttpResponseMessage Delete([FromBody]ChangeSet<object/*TODO:targetの型を指定します*/> value)
        {

            var result = SaveData(value);
            return Request.CreateResponse<SearchInputChangeResponseNotUse>(HttpStatusCode.OK, result);

        }

        #endregion

        #region "Controller内で利用する関数群"

        /// <summary>
        /// target 情報の主キーによる重複チェックを行います。
        /// </summary>
        /// <param name="value">target 情報の変更セット</param>
        /// <returns></returns>
        private InvalidationSet<object/*TODO:targetの型を指定します*/> IsAlreadyExists(ChangeSet<object/*TODO:targetの型を指定します*/> value)
        {
            InvalidationSet<object/*TODO:targetの型を指定します*/> result = new InvalidationSet<object/*TODO:targetの型を指定します*/>();

            //using (/*TODO:target情報を管理しているDbContextを指定します*/ context = new /*TODO:target情報を管理しているDbContextを指定します*/())
            //{
            //    foreach (var item in value.Created)
            //    {
            //        // TODO: 変更セット内で重複したキーが入力されたかどうかのチェック、およびデータベース上に重複した主キーがあるかどうかのチェックを行います。
            //        bool isDepulicate = false;
            //
            //        var createdCount = value.Created.Count(target => target.no_seq == item.no_seq);
            //        var isDeleted = value.Deleted.Exists(target => target.no_seq == item.no_seq);
            //        var isDatabaseExists = (context./*TODO: target の型を指定します*/.Find(item.no_seq) != null);

            //        isDepulicate |= (createdCount > 1);
            //        isDepulicate |= (!isDeleted && createdCount == 1 && isDatabaseExists);

            //        if (isDepulicate)
            //        {
            //            result.Add(new Invalidation<object/*TODO:targetの型を指定します*/>(Properties.Resources.ValidationKey, item, "no_seq"));
            //        }
            //    }
            //}

            return result;
        }

        /// <summary>
        /// target 情報の一括更新（追加・更新・削除）を実行します
        /// </summary>
        /// <param name="value">target 情報の変更セット</param>
        /// <returns>target 情報の更新結果オブジェクト</returns>
        private SearchInputChangeResponseNotUse SaveData(ChangeSet<object/*TODO:targetの型を指定します*/> value)
        {
            //using (/*TODO:target情報を管理しているDbContextを指定します*/ context = new /*TODO:target情報を管理しているDbContextを指定します*/())
            //{

            //    value.SetDataSaveInfo(this.User.Identity);                
            //    value.AttachTo(context);
            //    context.SaveChanges();
            //}

            // TODO: 返却用のオブジェクトを生成します。
            //var result = new SearchInputChangeResponse();
            //result.Detail.AddRange(value.Flatten());
            //return result;

            // TODO: 上記実装を行った後に下の行は削除します
            return null;
        }

        #endregion
    }

    #region "ControllerのAPIで利用するリクエスト・レスポンスクラスです"

    public class SearchInputChangeResponseNotUse
    {
        public SearchInputChangeResponseNotUse()
        {
            this.Detail = new List<object/*TODO: target の型を指定します*/>();
        }

        public List<object/*TODO: target の型を指定します*/> Detail { get; set; }
    }

    //Para search
    public class HaigoIchiranPara {
        public int cd_kaisha { get; set; }
        public int cd_kojyo { get; set; }
        public string m_kirikae { get; set; }
        public short id_system { get; set; }
        public short kbn_sort { get; set; }
        public short kansakujyoho { get; set; }
        public string cd_haigo { get; set; }
        public string flg_seiho_base { get; set; }
        public string no_seiho_kaisha { get; set; }
        public string no_seiho_shurui { get; set; }
        public string no_seiho_nen { get; set; }
        public string no_seiho_renban { get; set; }
        public string nm_haigo { get; set; }
        public string kbn_hin { get; set; }
        public string cd_bunrui { get; set; }
        public string nm_seihin { get; set; }
        public string flg_hinkan { get; set; }
        public string flg_seizo { get; set; }
        public string flg_genzai { get; set; }
        public string flg_mishiyo { get; set; }
        public string dt_hidzuke { get; set; }
        public string cd_haigo_parttwo { get; set; }
        public string cd_hin_parttwo { get; set; }
        public string cd_genryo { get; set; }
        public string kbn_hin_genryo { get; set; }
        public string nm_genryo_text { get; set; }
        public string cd_kikaku { get; set; }
        public string nm_kikaku_shohin { get; set; }
        public string nm_seiho_mei { get; set; }
        public string nm_hanbai_mei { get; set; }
        public string skip { get; set; }
        public string top { get; set; }
    }

    public class ResultData {
        public Nullable<int> total_rows { get; set; }
        public Nullable<long> RN { get; set; }
        public string cd_haigo { get; set; }
        public string nm_haigo { get; set; }
        public string no_seiho { get; set; }
        public string nm_seiho { get; set; }
        public string nm_kbn_hin { get; set; }
        public int no_han { get; set; }
        public Nullable<double> ritsu_kihon { get; set; }
        public bool? flg_hinkan { get; set; }
        public bool? flg_seizo { get; set; }
        public bool? flg_mishiyo { get; set; }
        public bool? chk_haigo { get; set; }
        public int qty_haigo_h { get; set; }
        public string dt_from { get; set; }
        public string dt_to { get; set; }
        public bool? flg_seiho_base { get; set; }
        public bool? flg_original { get; set; }
    }

    #endregion
}
