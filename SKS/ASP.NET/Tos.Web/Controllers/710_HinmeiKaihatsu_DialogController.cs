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

namespace Tos.Web.Controllers
{
    //created from 【SearchInputController(Ver1.7)】 Template
    public class _710_HinmeiKaihatsu_DialogController : ApiController
    {
        #region "Controllerで公開するAPI"
        public const bool FLG_FALSE = false;
        #endregion

        #region "Controllerで公開するAPI"

        /// <summary>
        /// パラメータで受け渡された target のキー項目をもとに target情報を取得します。
        /// </summary>
        /// <param name="id"> target のキー項目</param>
        /// <returns> target 情報</returns>
        public List<SeihoCodeResponse> GetSeihoCode()
        {
             using (ShohinKaihatsuEntities context = new ShohinKaihatsuEntities())
             {
                 context.Configuration.ProxyCreationEnabled = false;

                 return (from m in context.ma_hin_syurui
                         from n in context.ma_literal
                         where n.cd_category == "25"
                         select new SeihoCodeResponse()
                         {
                            seiho_shurui = m.cd_hin_syurui + n.cd_literal
                         }).OrderBy(x => x.seiho_shurui).ToList();
             }
        }

        /// <summary>
        /// Get kaisha name + kojo name for [代表工場]
        /// </summary>
        /// <param name="cd_kaisha"></param>
        /// <param name="cd_kojyo"></param>
        /// <returns></returns>
        public HinmeiKaihatsuDlgHeader GetKojyoName([FromUri] short cd_kaisha, short cd_kojyo)
        {
            using (ShohinKaihatsuEntities context = new ShohinKaihatsuEntities())
            {
                var result = new HinmeiKaihatsuDlgHeader();
                var nm_kojyo = (from m in context.ma_kaisha
                                join n in context.ma_busho
                                on m.cd_kaisha equals n.cd_kaisha
                                join i in context.vw_ma_kojyo
                                on m.cd_kaisha equals i.cd_kaisha
                                where m.cd_kaisha == cd_kaisha 
                                      && n.cd_busho == cd_kojyo
                                      && i.cd_kojyo == cd_kojyo
                                select new NameKojyo()
                                {
                                    nm_kojyo = m.nm_kaisha + " " + n.nm_busho
                                }).FirstOrDefault();

                result.nm_kojyo = nm_kojyo == null ? "" : nm_kojyo.nm_kojyo;
                int? su_code_standard = context.ma_kaisha.Where(x => x.cd_kaisha == cd_kaisha).Select(x => x.su_code_standard).FirstOrDefault();
                if (su_code_standard != null)
                {
                    result.su_code_standard = su_code_standard ?? 0;
                }
                return result;
            }
        }

        /// <summary>
        /// get list of bunrui in Kojyo DB based on kbn_hin
        /// </summary>
        /// <param name="cd_kaisha"></param>
        /// <param name="cd_kojyo"></param>
        /// <param name="kbn_hin"></param>
        /// <returns>list of ma_bunrui in FP DB</returns>
        public object getFBBunrui([FromUri] int cd_kaisha, int cd_kojyo, string kbn_hin)
        {
            object results;
            var commonFunc = new CommonController();

            if (!commonFunc.CheckKaishaKojyo(cd_kaisha, cd_kojyo))
            {
                return null;
            }
            using (FOODPROCSEntities context = new FOODPROCSEntities(cd_kaisha, cd_kojyo))
            {
                context.Configuration.ProxyCreationEnabled = false;

                var query = "SELECT cd_bunrui, nm_bunrui"
                            + " FROM ma_bunrui"
                            + " WHERE kbn_hin = @kbn_hin"
                            + " AND flg_sakujyo = @FlgFlase"
                            + " AND flg_mishiyo = @FlgFlase"
                            + " ORDER BY cd_bunrui";

                results = context.Database.SqlQuery<ma_bunrui_fp>(query, new SqlParameter("@kbn_hin", kbn_hin)
                                                                        , new SqlParameter("@FlgFlase", false)).ToList();
            }

            return results;
        }
        #endregion

        /// <summary>
        /// Default Get for search data. 
        /// This method can crash when the limit length of URI reached (because massive of Parameters).
        /// This method is replaced with Search (post method).
        /// </summary>
        /// <returns></returns>
        public string Get() 
        {
            return "";
        }
        
        /// <summary>
        /// 検索処理
        /// Altered for default get method.
        /// </summary>
        /// <returns></returns>
        [HttpPost]
        public HinmeiSearchResponse Search([FromBody]HinmeiSearchRequest Conditions)
        {
            HinmeiSearchResponse result = new HinmeiSearchResponse();
            if (Conditions == null || Conditions.cd_kaisha == null || Conditions.cd_kojyo == null || Conditions.mode == null)
            {
                return result;
            }
            int cd_kaisha = Conditions.cd_kaisha ?? 0;
            int cd_kojyo = Conditions.cd_kojyo ?? 0;
            switch (Conditions.mode)
            {
                // 起動元画面は開発部門　（画面ID＝２００（製法一覧画面）または２０３（配合登録_開発画面））の場合は
                //--------------------------------------------------------------------------------------------
                // 品区分＝１（原料）で検索の時
                case 1:
                // 品区分＝４（仕掛品）で検索の時
                // 画面で開発部門ラジオボタンを選択する場合は
                case 2:
                // 品区分＝４（仕掛品）で検索の時
                // 画面でFOODPROCSラジオボタンを選択する場合は
                case 3:
                // 品区分＝５（前処理原料）で検索の時
                case 4:
                // 品区分＝６（自家原料）で検索の時
                case 5:
                // 品区分＝９（作業指示）で検索の時 (２０３（配合登録画面）のみ）
                case 6:
                    using (ShohinKaihatsuEntities context = new ShohinKaihatsuEntities())
                    {
                        int? cd_bunrui = null;
                        if (Conditions.cd_bunrui != null)
                        {
                            cd_bunrui = Int32.Parse(Conditions.cd_bunrui);
                        }
                        result.Items = context.sp_shohinkaihatsu_search_710(Conditions.cd_kaisha,
                                                                            Conditions.cd_kojyo,
                                                                            cd_bunrui,
                                                                            Conditions.nm_hinmei,
                                                                            Conditions.no_seiho_kaisha,
                                                                            Conditions.seiho_kaisha,
                                                                            Conditions.seiho_shurui,
                                                                            Conditions.seiho_nen,
                                                                            Conditions.seiho_renban,
                                                                            Conditions.kbn_kojo,
                                                                            Conditions.genryo,
                                                                            Conditions.shikakari,
                                                                            Conditions.jikaGenryo,
                                                                            Conditions.su_code_standard,
                                                                            Conditions.mode,
                                                                            Conditions.top).ToList();
                    }
                    break;
                // 起動元画面は工場（画面ID＝２０8（配合一覧（表示・FP））、210_コード振替（表示・FP）または２０9（配合登録_工場部門（表示・FP））の場合は
                // 入力がない項目は抽出条件に加えない
                //-------------------------------------------------------------------------------------------------------------------------------
                // 品区分＝１（原料）で検索の時
                case 7:
                    using (FOODPROCSEntities context = new FOODPROCSEntities(cd_kaisha, cd_kojyo))
                    {

                        context.Configuration.ProxyCreationEnabled = false;

                        string kbn_genryo = Conditions.genryo.ToString();
                        string cd_bunrui = Conditions.cd_bunrui;
                        string nm_hinmei = Conditions.nm_hinmei;
                        var query =   " SELECT TOP (@top) *"
                                    + " FROM ("
                                    + "     SELECT"
                                    + "         cd_hin, cd_hin AS cd_hin_disp, nm_hin, CAST(kbn_hin AS INT) AS kbn_hin, no_kikaku,"
                                    + "         COUNT(*) OVER() AS cnt"
                                    + "     FROM ma_genshizai"
                                    + "     WHERE"
                                    + "         kbn_hin = @kbn_genryo"
                                    + "         AND flg_mishiyo = @FLgFalse"
                                    + "         AND flg_sakujyo = @FLgFalse"
                                    + "         AND ("
                                    + "             @cd_bunrui IS NULL"
                                    + "             OR cd_bunrui =  @cd_bunrui"
                                    + "         )"
                                    + "         AND ("
                                    + "             @nm_hinmei IS NULL"
                                    + "             OR cd_hin LIKE '%' + @nm_hinmei + '%'"
                                    + "             OR nm_hin LIKE '%' + @nm_hinmei + '%'"
                                    + "         )"
                                    + "     GROUP BY cd_hin, nm_hin, kbn_hin, no_kikaku"
                                    + " ) DATA"
                                    + " ORDER BY cd_hin";

                        result.Items = context.Database.SqlQuery<sp_shohinkaihatsu_search_710_Result>(query, new SqlParameter("@kbn_genryo", kbn_genryo)
                                                                                                           , new SqlParameter("@FLgFalse", false)
                                                                                                           , getNullSQLParam ("@cd_bunrui", cd_bunrui)
                                                                                                           , getNullSQLParam ("@nm_hinmei", nm_hinmei)
                                                                                                           , new SqlParameter("@top", Conditions.top)).ToList();
                    }
                    break;
                // 品区分＝４（仕掛品）または３（配合）で検索の時
                case 8:
                    using (FOODPROCSEntities context = new FOODPROCSEntities(cd_kaisha, cd_kojyo))
                    {
                        string kbn_hin = Conditions.kbn_hin.ToString();
                        string cd_bunrui = Conditions.cd_bunrui;
                        string nm_hinmei = Conditions.nm_hinmei;

                        var query =   " SELECT TOP (@top) *"
                                    + " FROM ("
                                    + "     SELECT"
                                    + "         cd_haigo AS cd_hin, cd_haigo AS cd_hin_disp, nm_haigo AS nm_hin, CAST(kbn_hin AS INT) AS kbn_hin, NULL AS no_kikaku,"
                                    + "         COUNT(*) OVER() AS cnt"
                                    + "     FROM ma_haigo_mei_hyoji"
                                    + "     WHERE"
                                    + "         kbn_hin = @kbn_hin"
                                    + "         AND flg_mishiyo = @FLgFalse"
                                    + "         AND flg_sakujyo = @FLgFalse"
                                    + "         AND ("
                                    + "             @cd_bunrui IS NULL"
                                    + "             OR cd_bunrui =  @cd_bunrui"
                                    + "         )"
                                    + "         AND ("
                                    + "             @nm_hinmei IS NULL"
                                    + "             OR cd_haigo LIKE '%' + @nm_hinmei + '%'"
                                    + "             OR nm_haigo LIKE '%' + @nm_hinmei + '%'"
                                    + "         )"
                                    + "         AND ("
                                    + "             @seiho_kaisha IS NULL"
                                    + "             OR LEFT(no_seiho, 4) = @seiho_kaisha"
                                    + "         )"
                                    + "         AND ("
                                    + "             @seiho_shurui IS NULL"
                                    + "             OR SUBSTRING(no_seiho, 6, 3) = @seiho_shurui"
                                    + "         )"
                                    + "         AND ("
                                    + "             @seiho_nen IS NULL"
                                    + "             OR SUBSTRING(no_seiho, 10, 2) = @seiho_nen"
                                    + "         )"
                                    + "         AND ("
                                    + "             @seiho_renban IS NULL"
                                    + "             OR SUBSTRING(no_seiho, 13, 4) = @seiho_renban"
                                    + "         )"
                                    + "         AND no_han = 1"
                                    + "     GROUP BY cd_haigo, nm_haigo, kbn_hin"
                                    + " ) DATA"
                                    + " ORDER BY cd_hin";

                        result.Items = context.Database.SqlQuery<sp_shohinkaihatsu_search_710_Result>(query, new SqlParameter("@kbn_hin", kbn_hin)
                                                                                                           , new SqlParameter("@FLgFalse", false)
                                                                                                           , getNullSQLParam ("@cd_bunrui", cd_bunrui)
                                                                                                           , getNullSQLParam ("@nm_hinmei", nm_hinmei)
                                                                                                           , getNullSQLParam ("@seiho_kaisha", Conditions.seiho_kaisha)
                                                                                                           , getNullSQLParam ("@seiho_shurui", Conditions.seiho_shurui)
                                                                                                           , getNullSQLParam ("@seiho_nen", Conditions.seiho_nen)
                                                                                                           , getNullSQLParam ("@seiho_renban", Conditions.seiho_renban)
                                                                                                           , new SqlParameter("@top", Conditions.top)).ToList();
                    }
                    break;
                // 切替モード (M_kirikae)　＝　２（FOODPROCS）の　場合は
                case 9:
                    using (FOODPROCSEntities context = new FOODPROCSEntities(cd_kaisha, cd_kojyo))
                    {
                        string kbn_hin = Conditions.kbn_hin.ToString();
                        string cd_bunrui = Conditions.cd_bunrui;
                        string nm_hinmei = Conditions.nm_hinmei;

                        var query =   " SELECT TOP (@top) *"
                                    + " FROM ("
                                    + "     SELECT"
                                    + "         cd_haigo AS cd_hin, cd_haigo AS cd_hin_disp, nm_haigo AS nm_hin, CAST(kbn_hin AS INT) AS kbn_hin, NULL AS no_kikaku,"
                                    + "         COUNT(*) OVER() AS cnt"
                                    + "     FROM ma_haigo_mei"
                                    + "     WHERE"
                                    + "         kbn_hin = @kbn_hin"
                                    + "         AND flg_mishiyo = @FLgFalse"
                                    + "         AND flg_sakujyo = @FLgFalse"
                                    + "         AND ("
                                    + "             @cd_bunrui IS NULL"
                                    + "             OR cd_bunrui =  @cd_bunrui"
                                    + "         )"
                                    + "         AND ("
                                    + "             @nm_hinmei IS NULL"
                                    + "             OR cd_haigo LIKE '%' + @nm_hinmei + '%'"
                                    + "             OR nm_haigo LIKE '%' + @nm_hinmei + '%'"
                                    + "         )"
                                    + "         AND ("
                                    + "             @seiho_kaisha IS NULL"
                                    + "             OR LEFT(no_seiho, 4) = @seiho_kaisha"
                                    + "         )"
                                    + "         AND ("
                                    + "             @seiho_shurui IS NULL"
                                    + "             OR SUBSTRING(no_seiho, 6, 3) = @seiho_shurui"
                                    + "         )"
                                    + "         AND ("
                                    + "             @seiho_nen IS NULL"
                                    + "             OR SUBSTRING(no_seiho, 10, 2) = @seiho_nen"
                                    + "         )"
                                    + "         AND ("
                                    + "             @seiho_renban IS NULL"
                                    + "             OR SUBSTRING(no_seiho, 13, 4) = @seiho_renban"
                                    + "         )"
                                    + "         AND no_han = 1"
                                    + "     GROUP BY cd_haigo, nm_haigo, kbn_hin"
                                    + " ) DATA"
                                    + " ORDER BY cd_hin";

                        result.Items = context.Database.SqlQuery<sp_shohinkaihatsu_search_710_Result>(query, new SqlParameter("@kbn_hin", kbn_hin)
                                                                                                           , new SqlParameter("@FLgFalse", false)
                                                                                                           , getNullSQLParam ("@cd_bunrui", cd_bunrui)
                                                                                                           , getNullSQLParam ("@nm_hinmei", nm_hinmei)
                                                                                                           , getNullSQLParam ("@seiho_kaisha", Conditions.seiho_kaisha)
                                                                                                           , getNullSQLParam ("@seiho_shurui", Conditions.seiho_shurui)
                                                                                                           , getNullSQLParam ("@seiho_nen", Conditions.seiho_nen)
                                                                                                           , getNullSQLParam ("@seiho_renban", Conditions.seiho_renban)
                                                                                                           , new SqlParameter("@top", Conditions.top)).ToList();
                    }
                    break;
                // 品区分＝５（前処理原料）で検索の時
                case 10:
                    using (FOODPROCSEntities context = new FOODPROCSEntities(cd_kaisha, cd_kojyo))
                    {
                        int kbn_shori_genryo = Conditions.maeshoriGenryo ?? 5; // 固定値 5
                        string nm_hinmei = Conditions.nm_hinmei;

                        var query =   " SELECT TOP (@top) *"
                                    + " FROM ("
                                    + "     SELECT"
                                    + "         SHORI.cd_maeshori AS cd_hin, SHORI.cd_maeshori AS cd_hin_disp, SHORI.nm_maeshori AS nm_hin, @kbn_shori_genryo AS kbn_hin, SHIZAI.no_kikaku,"
                                    + "         COUNT(*) OVER() AS cnt"
                                    + "     FROM ma_maeshori        SHORI"
                                    + "     LEFT JOIN ma_genshizai  SHIZAI"
                                    + "     ON SHORI.cd_hin = SHIZAI.cd_hin"
                                    + "     AND SHIZAI.flg_mishiyo = @FLgFalse"
                                    + "     AND SHIZAI.flg_sakujyo = @FLgFalse"
                                    + "     WHERE"
                                    + "         SHORI.flg_mishiyo = @FLgFalse"
                                    + "         AND SHORI.flg_sakujyo = @FLgFalse"
                                    + "         AND ("
                                    + "             @nm_hinmei IS NULL"
                                    + "             OR SHORI.cd_maeshori LIKE '%' + @nm_hinmei + '%'"
                                    + "             OR SHORI.nm_maeshori LIKE '%' + @nm_hinmei + '%'"
                                    + "         )"
                                    + "     GROUP BY SHORI.cd_maeshori, SHORI.nm_maeshori, SHIZAI.no_kikaku"
                                    + " ) DATA"
                                    + " ORDER BY cd_hin";

                        result.Items = context.Database.SqlQuery<sp_shohinkaihatsu_search_710_Result>(query, new SqlParameter("@kbn_shori_genryo", kbn_shori_genryo)
                                                                                                           , new SqlParameter("@FLgFalse", false)
                                                                                                           , getNullSQLParam ("@nm_hinmei", nm_hinmei)
                                                                                                           , new SqlParameter("@top", Conditions.top)).ToList();
                    }
                    break;
                // 品区分＝６（自家原料）で検索の時
                case 11:
                    using (FOODPROCSEntities context = new FOODPROCSEntities(cd_kaisha, cd_kojyo))
                    {
                        int jikaGenryo = Conditions.jikaGenryo ?? 6;   // 固定値 6
                        string nm_hinmei = Conditions.nm_hinmei;
                        var query =   " SELECT TOP (@top) *"
                                    + " FROM ("
                                    + "     SELECT"
                                    + "         cd_hin, cd_hin AS cd_hin_disp, nm_hin, @jikaGenryo AS kbn_hin, no_kikaku,"
                                    + "         COUNT(*) OVER() AS cnt"
                                    + "     FROM ma_seihin"
                                    + "     WHERE"
                                    + "         kbn_seihin = 1"
                                    + "         AND flg_mishiyo = @FLgFalse"
                                    + "         AND flg_sakujyo = @FLgFalse"
                                    + "         AND ("
                                    + "             @nm_hinmei IS NULL"
                                    + "             OR cd_hin LIKE '%' + @nm_hinmei + '%'"
                                    + "             OR nm_hin LIKE '%' + @nm_hinmei + '%'"
                                    + "         )"
                                    + "     GROUP BY cd_hin, nm_hin, no_kikaku"
                                    + " ) DATA"
                                    + " ORDER BY cd_hin";

                        result.Items = context.Database.SqlQuery<sp_shohinkaihatsu_search_710_Result>(query, new SqlParameter("@jikaGenryo", jikaGenryo)
                                                                                                           , new SqlParameter("@FLgFalse", false)
                                                                                                           , getNullSQLParam ("@nm_hinmei", nm_hinmei)
                                                                                                           , new SqlParameter("@top", Conditions.top)).ToList();

                    }
                    break;
                // 品区分＝９（作業指示）で検索の時
                case 12:
                    using (FOODPROCSEntities context = new FOODPROCSEntities(cd_kaisha, cd_kojyo))
                    {
                        int kbn_sagyo = Conditions.sagyo ?? 9;      // 固定値 9
                        string nm_hinmei = Conditions.nm_hinmei;

                        var query = " SELECT TOP (@top) *"
                                    + " FROM ("
                                    + "     SELECT"
                                    + "         cd_sagyo AS cd_hin, cd_sagyo AS cd_hin_disp, nm_sagyo AS nm_hin, @kbn_sagyo AS kbn_hin, NULL AS no_kikaku,"
                                    + "         COUNT(*) OVER() AS cnt"
                                    + "     FROM ma_sagyo"
                                    + "     WHERE"
                                    + "         flg_mishiyo = @FLgFalse"
                                    + "         AND flg_sakujyo = @FLgFalse"
                                    + "         AND ("
                                    + "             @nm_hinmei IS NULL"
                                    + "             OR cd_sagyo LIKE '%' + @nm_hinmei + '%'"
                                    + "             OR nm_sagyo LIKE '%' + @nm_hinmei + '%'"
                                    + "         )"
                                    + "     GROUP BY cd_sagyo, nm_sagyo"
                                    + " ) DATA"
                                    + " ORDER BY cd_hin";

                        result.Items = context.Database.SqlQuery<sp_shohinkaihatsu_search_710_Result>(query, new SqlParameter("@kbn_sagyo", kbn_sagyo)
                                                                                                           , new SqlParameter("@FLgFalse", false)
                                                                                                           , getNullSQLParam ("@nm_hinmei", nm_hinmei)
                                                                                                           , new SqlParameter("@top", Conditions.top)).ToList();
                    }
                    break;
            }
            if (result.Items != null && result.Items.Count > 0)
            {
                result.Count = result.Items[0].cnt ?? 0;
            }
            return result;
        }


        //private List<sp_shohinkaihatsu_search_710_Result> convertData(List<HinmeiSearchResponse> value)
        //{
        //    List<sp_shohinkaihatsu_search_710_Result> result = new List<sp_shohinkaihatsu_search_710_Result>();
        //    for (var i = 0; i < value.Count; i++)
        //    {
        //        var data = value[i];
        //        sp_shohinkaihatsu_search_710_Result converted = new sp_shohinkaihatsu_search_710_Result();
        //        converted.cd_hin = decimal.Parse(data.cd_hin);
        //        converted.nm_hin = data.nm_hin;
        //        converted.kbn_hin = Int32.Parse(data.kbn_hin);
        //        converted.no_kikaku = data.no_kikaku;
        //        converted.cnt = data.cnt;
        //        result.Add(converted);
        //    }
        //    return result;
        //}

        /// <summary>
        /// Get SQL param with value can null
        /// </summary>
        /// <param name="param"></param>
        /// <param name="value"></param>
        /// <returns></returns>
        private SqlParameter getNullSQLParam(string param, object value)
        {
            var result = new SqlParameter(param, value);
            if (value == null)
            {
                result.Value = DBNull.Value;
            }
            return result;
        }
       
    }



    #region "ControllerのAPIで利用するリクエスト・レスポンスクラスです"

    public class SeihoCodeResponse
    {
        public string seiho_shurui { get; set; }
    }

    public class NameKojyo
    {
        public string nm_kojyo { get; set; }
    }

    public class HinmeiKaihatsuDlgHeader
    {
        public string nm_kojyo { get; set; }
        public int su_code_standard { get; set; }
    }

    public class HinmeiSearchResponse
    {
        public List<sp_shohinkaihatsu_search_710_Result> Items { get; set; }
        public int Count { get; set; }
    }

    public class BunruiCodeResponse
    {
        public string cd_bunrui { get; set; }
        public string nm_bunrui { get; set; }
    }

    public class HinmeiSearchRequest
    {
        // Search conditions
        public int? mode { get; set; }
        public int? cd_kaisha { get; set; }
        public int? cd_kojyo { get; set; }
        public int? kbn_hin { get; set; }
        public int? kbn_hin_search { get; set; }
        public string cd_bunrui { get; set; }
        public string nm_hinmei { get; set; }
        //public string no_seiho { get; set; }
        public short? no_seiho_kaisha { get; set; }
        public string seiho_kaisha { get; set; }
        public string seiho_shurui { get; set; }
        public string seiho_nen { get; set; }
        public string seiho_renban { get; set; }
        public string kbn_kojo { get; set; }
        // Const settings
        public int? genryo { get; set; }        // 原料
        public int? shikakari { get; set; }     // 仕掛品
        public int? maeshoriGenryo { get; set; }   // 前処理原料
        public int? jikaGenryo { get; set; }    // 自家原料
        public int? sagyo { get; set; }         // 作業指示
        // su_code_standard
        public int su_code_standard { get; set; }
        // Pagging
        public int top { get; set; }
    }

    #endregion
}
