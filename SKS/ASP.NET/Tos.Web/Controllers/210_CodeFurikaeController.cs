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
using System.Data;
using System.Data.Entity.Infrastructure;
using System.Data.Common;

namespace Tos.Web.Controllers
{
    //created from 【SearchInputController(Ver1.7)】 Template
    public class CodeFurikaeController : ApiController
    {
        #region "Controllerで公開するAPI"
        public readonly byte? KubunNmacsKojyo = 2;
        public readonly int? cd_bunrui_28 = 28;
        public readonly byte? KubunHin_1 = 1;
        public readonly byte? KubunHin_2 = 2;
        public readonly byte? KubunHin_3 = 3;
        public readonly byte? KubunHin_4 = 4;
        public readonly byte? KubunHin_5 = 5;
        public readonly byte? KubunHin_6 = 6;
        public readonly byte? KubunHin_9 = 9;
        #region "queryInsertHaigoMeiHyoji"
        public readonly string queryInsertHaigoMeiHyoji210 = "INSERT INTO [dbo].[ma_haigo_mei_hyoji]"
                                                               + " ([cd_haigo]"
                                                               + " ,[nm_haigo]"
                                                               + " ,[nm_haigo_r]"
                                                               + " ,[kbn_hin]"
                                                               + " ,[cd_bunrui]"
                                                               + " ,[budomari]"
                                                               + " ,[qty_kihon]"
                                                               + " ,[ritsu_kihon]"
                                                               + " ,[cd_setsubi]"
                                                               + " ,[flg_gasan]"
                                                               + " ,[qty_max]"
                                                               + " ,[no_han]"
                                                               + " ,[qty_haigo_h]"
                                                               + " ,[qty_haigo_kei]"
                                                               + " ,[biko]"
                                                               + " ,[no_seiho]"
                                                               + " ,[cd_tanto_seizo]"
                                                               + " ,[dt_seizo]"
                                                               + " ,[cd_tanto_hinkan]"
                                                               + " ,[dt_hinkan]"
                                                               + " ,[dt_from]"
                                                               + " ,[dt_to]"
                                                               + " ,[kbn_vw]"
                                                               + " ,[hijyu]"
                                                               + " ,[flg_shorihin]"
                                                               + " ,[flg_hinkan]"
                                                               + " ,[flg_seizo]"
                                                               + " ,[flg_sakujyo]"
                                                               + " ,[flg_mishiyo]"
                                                               + " ,[dt_toroku]"
                                                               + " ,[dt_henko]"
                                                               + " ,[cd_koshin]"
                                                               + " ,[kbn_shiagari]"
                                                               + " ,[nm_haigo_rm]"
                                                               + " ,[cd_haigo_seiho]"
                                                               + " ,[flg_seiho_base]"
                                                               + " ,[nm_seiho])";
        #endregion

        #region "queryInsertHaigoMei"
        public readonly string queryInsertHaigoMei210 = "INSERT INTO [dbo].[ma_haigo_mei]"
           + " ([cd_haigo]"
           + " ,[nm_haigo]"
           + " ,[nm_haigo_r]"
           + " ,[kbn_hin]"
           + " ,[cd_bunrui]"
           + " ,[budomari]"
           + " ,[qty_kihon]"
           + " ,[ritsu_kihon]"
           + " ,[cd_setsubi]"
           + " ,[flg_gasan]"
           + " ,[qty_max]"
           + " ,[no_han]"
           + " ,[qty_haigo_h]"
           + " ,[qty_haigo_kei]"
           + " ,[biko]"
           + " ,[no_seiho]"
           + " ,[cd_tanto_seizo]"
           + " ,[dt_seizo]"
           + " ,[cd_tanto_hinkan]"
           + " ,[dt_hinkan]"
           + " ,[dt_from]"
           + " ,[dt_to]"
           + " ,[kbn_vw]"
           + " ,[hijyu]"
           + " ,[flg_shorihin]"
           + " ,[flg_hinkan]"
           + " ,[flg_seizo]"
           + " ,[flg_sakujyo]"
           + " ,[flg_mishiyo]"
           + " ,[dt_toroku]"
           + " ,[dt_henko]"
           + " ,[cd_koshin]"
           + " ,[kbn_shiagari]"
           + " ,[nm_haigo_rm]"
           + " ,[cd_haigo_seiho]"
           + " ,[flg_seiho_base]"
           + " ,[nm_seiho]"
           + " ,[kbn_koshin]"
           + " ,[kin_keihi])";
           //+ " ,[flg_tenkai]"
           //+ " ,[qty_haigo_auto]"
           //+ " ,[su_kigen_min]"
           //+ " ,[su_kigen_max]"
           //+ " ,[flg_mishiyo_seizo]"
           //+ " ,[qty_kowake]"
           //+ " ,[su_kowake]"
           //+ " ,[kikan_shomi]"
           //+ " ,[qty_haigo_genka]"
           //+ " ,[no_han_genka]"
           //+ " ,[flg_unten]"
           //+ " ,[cd_tanto_unten]"
           //+ " ,[dt_unten])";
        #endregion

        #region "queryInsertHaigoRecipeHyoji"
        public readonly string queryInsertHaigoRecipeHyoji210 = "INSERT INTO [dbo].[ma_haigo_recipe_hyoji]"
                                                                               + " ([cd_haigo]"
                                                                               + " ,[no_han]"
                                                                               + " ,[qty_haigo_h]"
                                                                               + " ,[no_kotei]"
                                                                               + " ,[no_tonyu]"
                                                                               + " ,[cd_hin]"
                                                                               + " ,[kbn_hin]"
                                                                               + " ,[nm_hin]"
                                                                               + " ,[cd_mark]"
                                                                               + " ,[qty]"
                                                                               + " ,[qty_haigo]"
                                                                               + " ,[qty_nisugata]"
                                                                               + " ,[su_nisugata]"
                                                                               + " ,[qty_kowake]"
                                                                               + " ,[su_kowake]"
                                                                               + " ,[cd_futai]"
                                                                               + " ,[qty_futai]"
                                                                               + " ,[hijyu]"
                                                                               + " ,[budomari]"
                                                                               + " ,[flg_sakujyo]"
                                                                               + " ,[flg_mishiyo]"
                                                                               + " ,[dt_toroku]"
                                                                               + " ,[dt_henko]"
                                                                               + " ,[cd_koshin]"
                                                                               + " ,[kbn_jyotai]"
                                                                               + " ,[kbn_shitei]"
                                                                               + " ,[kbn_bunkatsu])";
        #endregion

        #region "queryInsertHaigoRecipes"
        public readonly string queryInsertHaigoRecipe210 = "INSERT INTO [dbo].[ma_haigo_recipe]"
                                                                   + " ([cd_haigo]"
                                                                   + " ,[no_han]"
                                                                   + " ,[qty_haigo_h]"
                                                                   + " ,[no_kotei]"
                                                                   + " ,[no_tonyu]"
                                                                   + " ,[cd_hin]"
                                                                   + " ,[kbn_hin]"
                                                                   + " ,[nm_hin]"
                                                                   + " ,[cd_mark]"
                                                                   + " ,[qty]"
                                                                   + " ,[qty_haigo]"
                                                                   + " ,[qty_nisugata]"
                                                                   + " ,[su_nisugata]"
                                                                   + " ,[qty_kowake]"
                                                                   + " ,[su_kowake]"
                                                                   + " ,[cd_futai]"
                                                                   + " ,[qty_futai]"
                                                                   + " ,[hijyu]"
                                                                   + " ,[budomari]"
                                                                   + " ,[flg_sakujyo]"
                                                                   + " ,[flg_mishiyo]"
                                                                   + " ,[dt_toroku]"
                                                                   + " ,[dt_henko]"
                                                                   + " ,[cd_koshin]"
                                                                   + " ,[kbn_jyotai]"
                                                                   + " ,[kbn_shitei]"
                                                                   + " ,[kbn_bunkatsu])";
                                                                   //+ " ,[flg_kowake_nochk]"
                                                                   //+ " ,[cd_shokuba]"
                                                                   //+ " ,[keisu]"
                                                                   //+ " ,[flg_hijyu]"
                                                                   //+ " ,[cd_station]"
                                                                   //+ " ,[su_leadtime_kura])";
        #endregion

        #region "queryInsertSeizoLineHyoji"
        public readonly string queryInsertSeizoLineHyoji210 = "INSERT INTO [dbo].[ma_seizo_line_hyoji]"
                                                                               + "([cd_haigo]"
                                                                               + ",[no_yusen]"
                                                                               + ",[cd_line]"
                                                                               + ",[flg_sakujyo]"
                                                                               + ",[flg_mishiyo]"
                                                                               + ",[dt_toroku]"
                                                                               + ",[dt_henko]"
                                                                               + ",[cd_koshin]"
                                                                               + ",[msrepl_tran_version])";
        #endregion

        #region "queryInsertSeizoLine"
        public readonly string queryInsertSeizoLine210 = "INSERT INTO [dbo].[ma_seizo_line]"
                                                                               + "([cd_haigo]"
                                                                               + ",[no_yusen]"
                                                                               + ",[cd_line]"
                                                                               + ",[flg_sakujyo]"
                                                                               + ",[flg_mishiyo]"
                                                                               + ",[dt_toroku]"
                                                                               + ",[dt_henko]"
                                                                               + ",[cd_koshin])";
                                                                               //+ ",[rowguid]"
                                                                               //+ ",[qty_noryoku])";
        #endregion

        /// <summary>
        /// Get data ma_shokuba
        /// </summary>
        /// <param name="cd_kaisha"> </param>
        /// <param name="cd_kojyo"> </param>
        /// <returns>Get data</returns>
        public object GetShokuba(int cd_kaisha, int cd_kojyo, string cd_shokuba) {
            CommonController common = new CommonController();
            if (!common.CheckKaishaKojyo(cd_kaisha, cd_kojyo))
            {
                return null;
            }
            using (FOODPROCSEntities context = new FOODPROCSEntities(cd_kaisha, cd_kojyo))
            {
                string kbn_hin_haigo = Properties.Resources.kbn_hin_haigo,
                    kbn_hin_shikakarihin = Properties.Resources.kbn_hin_shikakarihin;
                var query = "SELECT cd_shokuba, nm_shokuba"
                            + " FROM ma_shokuba"
                            + " WHERE (@cd_shokuba IS NULL OR cd_shokuba = @cd_shokuba)"
                            + " AND flg_sakujyo = 0"
                            + " AND flg_mishiyo = 0"
                            + " ORDER BY cd_shokuba";

                var parameters = new object[]
                    {
                        new SqlParameter("@cd_shokuba", SqlDbType.VarChar, 2) { Value = (object)cd_shokuba ?? DBNull.Value }
                    };

                return context.Database.SqlQuery<ShokubaData>(query, parameters).ToList();
            }
        }

        /// <summary>
        /// Get hin data
        /// </summary>
        /// <param name="cd_kaisha"> </param>
        /// <param name="cd_kojyo"> </param>
        /// <returns>Get data</returns>
        public object GetHinData(int cd_kaisha, int cd_kojyo, int modeSearch, string m_kirikae, string cd_hin, string kbn_hin)
        {
            object results;
            using (FOODPROCSEntities foodproc = new FOODPROCSEntities(cd_kaisha, cd_kojyo))
            {
                var queryHaigo = "";
                var queryHin = "";
                if (modeSearch == 1)
                {
                     queryHaigo = " SELECT DISTINCT"
                                    + " A.cd_haigo AS cd_hin,"
                                    + " A.nm_haigo AS nm_hin,"
                                    + " A.kbn_hin,"
                                    + " B.nm_kbn_hin AS nm_kbn_hin,"
                                    + " '' AS qty_nisugata";
                    if(m_kirikae == Properties.Resources.m_kirikae_hyoji){
                        queryHaigo = queryHaigo + " FROM ma_haigo_mei_hyoji A";
                    } else {
                        queryHaigo = queryHaigo + " FROM ma_haigo_mei A";
                    }
                    queryHaigo = queryHaigo + " LEFT JOIN ma_kbn_hin B"
                                    + " ON A.kbn_hin = B.kbn_hin"
                                    + " WHERE A.cd_haigo = @cd_hin"
                                    + " AND A.flg_mishiyo = @FlgFlase"
                                    + " AND A.flg_sakujyo = @FlgFlase";

                    results = foodproc.Database.SqlQuery<HaigoCodeData210>(queryHaigo, new SqlParameter("@cd_hin", cd_hin)
                                                                            , new SqlParameter("@FlgFlase", "0")).FirstOrDefault();
                }
                else {
                    queryHin = " SELECT a.cd_hin_var AS cd_hin,"
                        + " a.nm_hin,"
                        + " a.kbn_hin,"
                        + " CASE a.kbn_hin WHEN 6 THEN '自家原料'"
                        + " ELSE b.nm_kbn_hin"
                        + " END AS nm_kbn_hin,"
                        + " a.kbn_jyotai,"
                        + " a.hijyu,"
                        + " a.cd_tani_hin,"
                        + " e.nm_tani,"
                        + " CASE a.kbn_hin WHEN 1 THEN c.qty"
                        + " WHEN 6 THEN d.qty ELSE ''"
                        + " END AS qty_nisugata";
                        if(m_kirikae == Properties.Resources.m_kirikae_hyoji){
                            queryHin = queryHin + " FROM SS_vw_hin_hyoji a";
                        } else {
                            queryHin = queryHin + " FROM SS_vw_hin a";
                        }
                        
                        queryHin = queryHin + " LEFT JOIN ma_kbn_hin b"
                        + " ON a.kbn_hin = b.kbn_hin"
                        + " LEFT JOIN ma_genshizai c"
                        + " ON a.cd_hin_var = c.cd_hin"
                        + " LEFT JOIN ma_konpo d"
                        + " ON a.cd_hin_var = d.cd_hin"
                        + " LEFT JOIN ma_tani e"
                        + " ON a.cd_tani_hin = e.cd_tani"
                        + " WHERE a.cd_hin = @cd_hin"
                        + " AND (@kbn_hin IS NULL OR a.kbn_hin = @kbn_hin)"
                        + " ORDER BY a.kbn_hin";

                    var parameters = new object[]
                    {
                        new SqlParameter("@cd_hin", SqlDbType.Float) { Value = (object)cd_hin ?? DBNull.Value },
                        new SqlParameter("@kbn_hin", SqlDbType.VarChar, 1) { Value = (object)kbn_hin ?? DBNull.Value }
                    };
                    results = foodproc.Database.SqlQuery<HinCodeData210>(queryHin, parameters).FirstOrDefault();
                }
            }

            return results;
        }

        /// <summary>
        /// Get kbn_nmac_kojyo
        /// </summary>
        /// <returns>Get kbn_nmac_kojyo</returns>
        public byte? GetKubunNmacKojyo210(int cd_kaisha, int cd_kojyo)
        {
            ShohinKaihatsuEntities context = new ShohinKaihatsuEntities();
            context.Configuration.ProxyCreationEnabled = false;

            var kojyoData = context.ma_kojyo.Where(m => m.cd_kaisha == cd_kaisha && m.cd_kojyo == cd_kojyo).FirstOrDefault();
            if (kojyoData != null)
            {
                return kojyoData.kbn_nmacs_kojyo;
            }

            return null;
        }

        /// <summary>
        /// パラメータで受け渡された target 情報をもとにエントリーを一括更新（追加・更新・削除）します。
        /// </summary>
        /// <param name="value"></param>
        /// <returns></returns>
        public bool CountDataFoodproc210(FOODPROCSEntities foodproc, string cd_haigo, byte? su_code_standard, short mode)
        {
            object result = null;

            if (mode == 1)
            {
                result = foodproc.SS_Hin_Check_sp_hyoji_qpnmacs(cd_haigo, su_code_standard).FirstOrDefault();
            }
            else if (mode == 2)
            {
                result = foodproc.SS_Hin_Check_sp_qpnmacs(cd_haigo, su_code_standard).FirstOrDefault();
            }
            else if (mode == 3)
            {
                result = foodproc.SS_Hin_Check_sp_hyoji(cd_haigo, su_code_standard).FirstOrDefault();
            }
            else if (mode == 4)
            {
                result = foodproc.SS_Hin_Check_sp(cd_haigo, su_code_standard).FirstOrDefault();
            }

            if (result != null)
            {
                return true;
            }

            return false;
        }

        /// <summary>
        /// Check haigo new with mode 配合コード変換。
        /// </summary>
        /// <param name=""></param>
        /// <returns>Return bool</returns>
        [HttpGet]
        public bool CheckHinCode(int cd_kaisha, int cd_kojyo, byte su_code_standard, string m_kirikae, string cd_hin_new)
        {
            var kbn_nmacs_kojyo = GetKubunNmacKojyo210(cd_kaisha, cd_kojyo);

            if (kbn_nmacs_kojyo == null) {
                return false;
            }
            FOODPROCSEntities foodproc = new FOODPROCSEntities(cd_kaisha, cd_kojyo);

            if (m_kirikae == Properties.Resources.m_kirikae_hyoji)
            {
                if (kbn_nmacs_kojyo == KubunNmacsKojyo)
                {
                    if (CountDataFoodproc210(foodproc, cd_hin_new, su_code_standard, 1))
                    {
                        return false;
                    }
                }
                else {
                    if (CountDataFoodproc210(foodproc, cd_hin_new, su_code_standard, 3))
                    {
                        return false;
                    }
                }
            }
            else {
                if (kbn_nmacs_kojyo == KubunNmacsKojyo)
                {
                    if (CountDataFoodproc210(foodproc, cd_hin_new, su_code_standard, 2))
                    {
                        return false;
                    }
                }
                else {
                    if (CountDataFoodproc210(foodproc, cd_hin_new, su_code_standard, 4))
                    {
                        return false;
                    }
                }
            }
            
            return true;
        }

        /// <summary>
        /// パラメータで受け渡された 値に該当するすべてのtarget情報を StoredProcedure を利用して取得します。
        /// </summary>
        /// <param name="options"></param>
        /// <returns>target情報</returns>
        public DataSearch Get([FromUri] CodeFurikaePara value)
        {
            DataSearch data = new DataSearch();
            using (FOODPROCSEntities foodproc = new FOODPROCSEntities(value.cd_kaisha, value.cd_kojyo))
            {
                byte? kbn_nmacs_kojyo = GetKubunNmacKojyo210(value.cd_kaisha, value.cd_kojyo);
                if (value.kbn_hin_old == KubunHin_3) {
                    data.Items1 = GetKubunHin3(foodproc, value);
                }
                else if (value.kbn_hin_old == KubunHin_4)
                {
                    if (value.modeBind == 1)
                    {
                        data.Items1 = GetKubunHin4ModeHaigo(foodproc, value, kbn_nmacs_kojyo);
                    }
                    else {
                        data.Items2 = GetKubunHin4ModeOther(foodproc, value, kbn_nmacs_kojyo);
                    }
                }
                else if (value.kbn_hin_old == KubunHin_6 || value.kbn_hin_old == KubunHin_5)
                {
                    data.Items2 = GetKubunHin4ModeOther(foodproc, value, kbn_nmacs_kojyo);
                }
                else if (value.kbn_hin_old == KubunHin_1) {
                    if (value.flg_genryo == null)
                    {
                        data.Items2 = GetKubunHin4ModeOther(foodproc, value, kbn_nmacs_kojyo);
                    }
                    else {
                        data.Items2 = GetKubunHin1Other(foodproc, value, kbn_nmacs_kojyo);
                    }
                }
                data.kbn_nmacs_kojyo = kbn_nmacs_kojyo;
            }

            if (data.Items1 != null) {
                if (data.Items1.Count() > 0)
                {
                    data.Count1 = data.Items1.FirstOrDefault().CountAll;
                }
            }

            if (data.Items2 != null)
            {
                if (data.Items2.Count() > 0)
                {
                    data.Count2 = data.Items2.FirstOrDefault().CountAll;
                }
            }

            data.modeBind = value.modeBind;
            

            return data;
        }

        /// <summary>
        /// Get data with kbn_hin is 3。
        /// </summary>
        /// <param name="options"></param>
        /// <returns>Data with kbn_hin is 3</returns>
        public List<GetKubunHin3_4_1> GetKubunHin3(FOODPROCSEntities foodproc, CodeFurikaePara value)
        {
            List<GetKubunHin3_4_1> results;
            var declareQuery = "DECLARE @start NUMERIC = @skip + 1,"
                + "@end NUMERIC = @top  + @skip;";

            var queryMain = " WITH CTE AS (SELECT DISTINCT"
                + " A.cd_hin,"
                + " A.nm_hin,"
                + " '6' AS kbn_hin,"
                + " B.nm_kino AS nm_kbn_hin"

                + " FROM ma_seihin A"

                + " INNER JOIN ss_ma_name B"
                + " ON A.kbn_seihin = B.cd_code"
                + " AND B.cd_bunrui = @cd_bunrui_28";//28

            if (value.m_kirikae == Properties.Resources.m_kirikae_hyoji)
            {
                queryMain = queryMain + " WHERE A.cd_haigo_hyoji = @cd_hin_old";
            } else {
                queryMain = queryMain + " WHERE A.cd_haigo = @cd_hin_old";
            }
                queryMain = queryMain + " AND A.flg_sakujyo = 0)";

            var quesyNextSearch = " SELECT CountAll, row_num, cd_hin, nm_hin, kbn_hin, nm_kbn_hin"
                + " FROM ("
                + " SELECT"
                + " ROW_NUMBER() OVER(ORDER BY cd_hin) AS row_num"
                + " ,COUNT(cd_hin) OVER() AS CountAll"
                + " ,cd_hin, nm_hin, kbn_hin, nm_kbn_hin"
                + " FROM CTE"
                + " ) data"
                + " WHERE (@start = 1 AND @end = 0) OR ( DATA.row_num BETWEEN @start AND @end)";
            var queryRun = declareQuery + queryMain + quesyNextSearch;
            var parameters = new object[]
            {
                new SqlParameter("@cd_hin_old", SqlDbType.VarChar, 13) { Value = (object)value.cd_hin_old ?? DBNull.Value },
                new SqlParameter("@cd_bunrui_28", SqlDbType.Int) { Value = (object)cd_bunrui_28 ?? DBNull.Value },
                new SqlParameter("@skip", SqlDbType.Int) { Value = (object)value.skip ?? DBNull.Value },
                new SqlParameter("@top", SqlDbType.Int) { Value = (object)value.top ?? DBNull.Value }
            };

            results = foodproc.Database.SqlQuery<GetKubunHin3_4_1>(queryRun, parameters).ToList();

            return results;
        }

        /// <summary>
        /// Get data with kbn_hin is 4。
        /// </summary>
        /// <param name="options"></param>
        /// <returns>Data with kbn_hin is 4</returns>
        public List<GetKubunHin3_4_1> GetKubunHin4ModeHaigo(FOODPROCSEntities foodproc, CodeFurikaePara value, byte? kbn_nmacs_kojyo)
        {
            List<GetKubunHin3_4_1> results;
            var declareQuery = "DECLARE @start NUMERIC = @skip + 1,"
                + "@end NUMERIC = @top  + @skip;";

            var queryMain = " WITH CTE AS (SELECT DISTINCT"
                + " A.cd_haigo AS cd_hin,"
                + " A.nm_haigo AS nm_hin,"
                + " A.kbn_hin,"
                + " C.nm_kbn_hin AS nm_kbn_hin,"
                + " A.qty_haigo_h, A.no_han ";

            if (value.m_kirikae == Properties.Resources.m_kirikae_hyoji)
            {
                queryMain = queryMain + " FROM ma_haigo_mei_hyoji A"
                                        + " INNER JOIN ma_haigo_recipe_hyoji B";
            }
            else {
                queryMain = queryMain + " FROM ma_haigo_mei A"
                                        + " INNER JOIN ma_haigo_recipe B";
            }
            queryMain = queryMain + " ON A.cd_haigo = B.cd_haigo"
                + " AND A.no_han = B.no_han"
                + " AND B.flg_sakujyo = 0"
                + " AND B.flg_mishiyo = 0";
            if (value.m_kirikae == Properties.Resources.m_kirikae_foodprocs)
            {
                queryMain = queryMain + " AND A.qty_haigo_h = B.qty_haigo_h";
            }
            queryMain = queryMain + " LEFT JOIN ma_kbn_hin C"
                + " ON A.kbn_hin = C.kbn_hin"

                + " WHERE B.cd_hin = @cd_hin_old"
                + (value.flg_mishiyo == null ? "" : " AND A.flg_mishiyo = 0")
                + " AND A.flg_sakujyo = 0";
            if (value.m_kirikae == Properties.Resources.m_kirikae_foodprocs && kbn_nmacs_kojyo != KubunNmacsKojyo)
            {
                queryMain = queryMain + " AND A.qty_haigo_h = A.qty_kihon";
            }

            queryMain = queryMain  + " )";

            var quesyNextSearch = " SELECT CountAll, row_num, cd_hin, nm_hin, kbn_hin, nm_kbn_hin"
                + " FROM ("
                + " SELECT"
                + " ROW_NUMBER() OVER(ORDER BY cd_hin, qty_haigo_h, no_han) AS row_num"
                + " ,COUNT(cd_hin) OVER() AS CountAll"
                + " ,cd_hin, nm_hin, kbn_hin, nm_kbn_hin"
                + " FROM CTE"
                + " ) data"
                + " WHERE (@start = 1 AND @end = 0) OR ( data.row_num BETWEEN @start AND @end ) ORDER BY row_num";

            var queryRun = declareQuery + queryMain + quesyNextSearch;

            var parameters = new object[]
            {
                new SqlParameter("@cd_hin_old", SqlDbType.VarChar, 13) { Value = (object)value.cd_hin_old ?? DBNull.Value },
                new SqlParameter("@skip", SqlDbType.Int) { Value = (object)value.skip ?? DBNull.Value },
                new SqlParameter("@top", SqlDbType.Int) { Value = (object)value.top ?? DBNull.Value }
            };

            results = foodproc.Database.SqlQuery<GetKubunHin3_4_1>(queryRun, parameters).ToList();

            return results;
        }

        /// <summary>
        /// Get data with kbn_hin is 4。
        /// </summary>
        /// <param name="options"></param>
        /// <returns>Data with kbn_hin is 4</returns>
        public List<GetKubunHin4Other> GetKubunHin4ModeOther(FOODPROCSEntities foodproc, CodeFurikaePara value, byte? kbn_nmacs_kojyo)
        {
            List<GetKubunHin4Other> results;
            var declareQuery = "DECLARE @start NUMERIC = @skip + 1,"
                + "@end NUMERIC = @top  + @skip;";

            var queryMain = " DECLARE @DateNow DATETIME = GETDATE();"
                        + " WITH CTE AS(SELECT"
                        + " m.cd_haigo"
                        + " , m.nm_haigo"
                        + " , m.no_han"
                        + " , MAX(m.no_han) OVER(PARTITION BY m.cd_haigo, m.qty_haigo_h) AS max_no_han "
                        + " , m.kbn_hin"
                        + " , m.flg_seiho_base"
                        + " , m.qty_haigo_h";
            if (value.m_kirikae == Properties.Resources.m_kirikae_hyoji)
            {
                queryMain = queryMain + " FROM ma_haigo_mei_hyoji m";
            }
            else {
                queryMain = queryMain + " FROM ma_haigo_mei m";
            }
            queryMain = queryMain + " INNER JOIN ("
                        + " SELECT DISTINCT "
                        + " A.cd_haigo"
                        + " , A.no_han";
            if (value.m_kirikae == Properties.Resources.m_kirikae_hyoji)
            {
                queryMain = queryMain + " FROM ma_haigo_mei_hyoji A"
                            + " INNER JOIN ma_haigo_recipe_hyoji B";
            }
            else {
                queryMain = queryMain + " , A.qty_haigo_h";
                queryMain = queryMain + " FROM ma_haigo_mei A"
                                + " INNER JOIN ma_haigo_recipe B";
            }

            queryMain = queryMain + " ON A.cd_haigo = B.cd_haigo"
                        + " AND A.no_han = B.no_han";
            if (value.m_kirikae == Properties.Resources.m_kirikae_foodprocs)
            {
                queryMain = queryMain + " AND A.qty_haigo_h = B.qty_haigo_h";
            }

            queryMain = queryMain + " WHERE A.flg_sakujyo = 0";
            if (value.m_kirikae == Properties.Resources.m_kirikae_foodprocs && kbn_nmacs_kojyo != KubunNmacsKojyo)
            {
                queryMain = queryMain + " AND A.qty_haigo_h = A.qty_kihon";
            }
            queryMain = queryMain + " AND (@flg_genzai IS NULL OR"
                        + " (@DateNow BETWEEN A.dt_from AND A.dt_to AND"
                        + " ( (A.no_han>1 AND A.flg_mishiyo=0) OR (A.no_han=1 AND NOT EXISTS"
                        + " (SELECT aa.cd_haigo";
            if (value.m_kirikae == Properties.Resources.m_kirikae_hyoji)
            {
                queryMain = queryMain + " FROM ma_haigo_mei_hyoji aa";
            }
            else
            {
                queryMain = queryMain + " FROM ma_haigo_mei aa";
            }
            queryMain = queryMain + " WHERE aa.cd_haigo = A.cd_haigo";
            if (value.m_kirikae == Properties.Resources.m_kirikae_foodprocs)
            {
                queryMain = queryMain + " AND aa.qty_haigo_h = A.qty_haigo_h";
            }
            queryMain = queryMain + " AND aa.flg_sakujyo = 0"
                        + (value.flg_mishiyo == null ? "" : " AND aa.flg_mishiyo = 0")
                        + " AND @DateNow BETWEEN aa.dt_from AND aa.dt_to"
                        + " AND aa.no_han>1"
                        + " )))))"
                        + " AND B.cd_hin = @cd_hin_old"
                        + " AND B.flg_sakujyo = 0"
                        + " ) s"
                        + " ON m.cd_haigo = s.cd_haigo"
                        + " AND m.no_han = s.no_han";
            if (value.m_kirikae == Properties.Resources.m_kirikae_foodprocs)
            {
                queryMain = queryMain + " AND m.qty_haigo_h = s.qty_haigo_h";
            }

            queryMain = queryMain + " WHERE m.cd_haigo IN ("
                        + " SELECT DISTINCT"
                        + " A.cd_haigo";
            if (value.m_kirikae == Properties.Resources.m_kirikae_hyoji)
            {
                queryMain = queryMain + " FROM ma_seizo_line_hyoji A";
            }
            else {
                queryMain = queryMain + " FROM ma_seizo_line A";
            }
            queryMain = queryMain + " INNER JOIN ma_line B"
                        + " ON A.cd_line = B.cd_line"
                        + " WHERE (@cd_shokuba IS NULL OR B.cd_shokuba = @cd_shokuba)"
                        + " AND (@flg_mishiyo IS NULL OR m.flg_mishiyo = @flg_mishiyo)"
                        + " AND m.flg_sakujyo = 0))";

            var quesyNextSearch = ", CTE2 AS ("
                        + " SELECT"
                        + " ROW_NUMBER() OVER(ORDER BY CTE.cd_haigo, CTE.qty_haigo_h, CTE.no_han) AS row_num"
                        + " ,COUNT(CTE.cd_haigo) OVER() AS CountAll"
                        + " , CTE.cd_haigo AS cd_hin"
                        + " , CTE.nm_haigo AS nm_hin"
                        + " , CTE.kbn_hin"
                        + " , CTE.no_han"
                        + " , CTE.flg_seiho_base"
                        + " , kbn_hin.nm_kbn_hin"
                        + " , CASE WHEN EXISTS (SELECT r.kbn_shitei";
            if (value.m_kirikae == Properties.Resources.m_kirikae_hyoji)
            {
                quesyNextSearch = quesyNextSearch + " FROM ma_haigo_recipe_hyoji r";
            }
            else {
                quesyNextSearch = quesyNextSearch + " FROM ma_haigo_recipe r";
            }
            quesyNextSearch = quesyNextSearch + " WHERE r.cd_haigo = CTE.cd_haigo"
                        + " AND r.no_han = CTE.no_han";
            if (value.m_kirikae == Properties.Resources.m_kirikae_foodprocs)
            {
                quesyNextSearch = quesyNextSearch + " AND r.qty_haigo_h = CTE.qty_haigo_h";
            }
            quesyNextSearch = quesyNextSearch + " AND r.cd_hin = @cd_hin_old"
                        + " AND r.flg_sakujyo = 0"
                        + " AND r.kbn_shitei = '1')"
                        + " THEN '●' ELSE '' END AS kbn_shitei"
                        + " , CTE.qty_haigo_h"
                        + " FROM CTE CTE"
                        + " LEFT JOIN ma_kbn_hin kbn_hin"
                        + " ON CTE.kbn_hin = kbn_hin.kbn_hin";
            if (value.m_kirikae == Properties.Resources.m_kirikae_foodprocs)
            {
                quesyNextSearch = quesyNextSearch + " WHERE @flg_genzai IS NULL OR CTE.no_han = CTE.max_no_han";
            }

            quesyNextSearch = quesyNextSearch + ") SELECT CountAll, row_num, cd_hin, nm_hin, kbn_hin, no_han, flg_seiho_base, nm_kbn_hin, kbn_shitei, qty_haigo_h"
                + " FROM CTE2"
                + " WHERE (@start = 1 AND @end = 0) OR ( row_num BETWEEN @start AND @end ) ORDER BY row_num";

            var queryRun = declareQuery + queryMain + quesyNextSearch;
            var parameters = new object[]
            {
                new SqlParameter("@cd_hin_old", SqlDbType.VarChar, 13) { Value = (object)value.cd_hin_old ?? DBNull.Value },
                new SqlParameter("@cd_shokuba", SqlDbType.VarChar, 2) { Value = (object)value.cd_shokuba ?? DBNull.Value },
                new SqlParameter("@flg_genzai", SqlDbType.Int) { Value = (object)value.flg_genzai ?? DBNull.Value },
                new SqlParameter("@flg_mishiyo", SqlDbType.Int) { Value = (object)value.flg_mishiyo ?? DBNull.Value },
                new SqlParameter("@skip", SqlDbType.Int) { Value = (object)value.skip ?? DBNull.Value },
                new SqlParameter("@top", SqlDbType.Int) { Value = (object)value.top ?? DBNull.Value }
            };

            results = foodproc.Database.SqlQuery<GetKubunHin4Other>(queryRun, parameters).ToList();

            return results;
        }

        /// <summary>
        /// Get data with kbn_hin is 1。
        /// </summary>
        /// <param name="options"></param>
        /// <returns>Data with kbn_hin is 1</returns>
        public List<GetKubunHin4Other> GetKubunHin1Other(FOODPROCSEntities foodproc, CodeFurikaePara value, byte? kbn_nmacs_kojyo)
        {
            List<GetKubunHin4Other> results;
            var declareQuery = "DECLARE @start NUMERIC = @skip + 1,"
                + "@end NUMERIC = @top  + @skip;";

            var queryMain = "DECLARE @DateNow DATETIME = GETDATE();"
                        + " WITH CTE AS(SELECT"
                        + " m.cd_haigo"
                        + " , m.nm_haigo"
                        + " , m.no_han"
                        + " , MAX(m.no_han) OVER(PARTITION BY m.cd_haigo, m.qty_haigo_h) AS max_no_han "
                        + " , m.kbn_hin"
                        + " , m.flg_seiho_base"
                        + " , m.qty_haigo_h";
            if (value.m_kirikae == Properties.Resources.m_kirikae_hyoji)
            {
                queryMain = queryMain + " FROM ma_haigo_mei_hyoji m";
            }
            else {
                queryMain = queryMain + " FROM ma_haigo_mei m";
            }
            queryMain = queryMain + " INNER JOIN ("
                        + " SELECT DISTINCT "
                        + " A.cd_haigo"
                        + " , A.no_han";
            if (value.m_kirikae == Properties.Resources.m_kirikae_hyoji)
            {
                queryMain = queryMain + " FROM ma_haigo_mei_hyoji A"
                       + " INNER JOIN ma_haigo_recipe_hyoji B";
            }
            else {
                queryMain = queryMain + " , A.qty_haigo_h"
                            + " FROM ma_haigo_mei A"
                           + " INNER JOIN ma_haigo_recipe B";
            }


            queryMain = queryMain + " ON A.cd_haigo = B.cd_haigo"
                        + " AND A.no_han = B.no_han";
            if (value.m_kirikae == Properties.Resources.m_kirikae_foodprocs)
            {
                queryMain = queryMain + " AND A.qty_haigo_h = B.qty_haigo_h";
            }
            queryMain = queryMain + " WHERE A.flg_sakujyo = 0";
            if (value.m_kirikae == Properties.Resources.m_kirikae_foodprocs && kbn_nmacs_kojyo != KubunNmacsKojyo)
            {
                queryMain = queryMain + " AND A.qty_haigo_h = A.qty_kihon";
            }
            queryMain = queryMain + " AND (@flg_genzai IS NULL OR"
                        + " (@DateNow BETWEEN A.dt_from AND A.dt_to AND"
                        + " ( (A.no_han>1 AND A.flg_mishiyo=0) OR (A.no_han=1 AND NOT EXISTS"
                        + " (SELECT aa.cd_haigo";
            if (value.m_kirikae == Properties.Resources.m_kirikae_hyoji)
            {
                queryMain = queryMain + " FROM ma_haigo_mei_hyoji aa";
            }
            else {
                queryMain = queryMain + " FROM ma_haigo_mei aa";
            }

            queryMain = queryMain + " WHERE aa.cd_haigo = A.cd_haigo";
            if (value.m_kirikae == Properties.Resources.m_kirikae_foodprocs)
            {
                queryMain = queryMain + " AND aa.qty_haigo_h = A.qty_haigo_h";
            }
            queryMain = queryMain + " AND aa.flg_sakujyo = 0"
                        + (value.flg_mishiyo == null ? "" : " AND aa.flg_mishiyo = 0")
                        + " AND @DateNow BETWEEN aa.dt_from AND aa.dt_to"
                        + " AND aa.no_han>1"
                        + " )))))"
                        + " AND B.cd_hin = @cd_hin_old"
                        + " AND B.flg_sakujyo = 0"
                        + " ) s"
                        + " ON m.cd_haigo = s.cd_haigo"
                        + " AND m.no_han = s.no_han";
            if (value.m_kirikae == Properties.Resources.m_kirikae_foodprocs)
            {
                queryMain = queryMain + " AND m.qty_haigo_h = s.qty_haigo_h";
            }
            queryMain = queryMain + " WHERE m.cd_haigo IN ("
                        + " SELECT DISTINCT"
                        + " A.cd_haigo";
            if (value.m_kirikae == Properties.Resources.m_kirikae_hyoji)
            {
                queryMain = queryMain + " FROM ma_seizo_line_hyoji A";
            }
            else {
                queryMain = queryMain + " FROM ma_seizo_line A";
            }

            queryMain = queryMain + " INNER JOIN ma_line B"
                        + " ON A.cd_line = B.cd_line"
                        + " WHERE (@cd_shokuba IS NULL OR B.cd_shokuba = @cd_shokuba)"
                        + " AND (@flg_mishiyo IS NULL OR m.flg_mishiyo = @flg_mishiyo)"
                        + " AND m.flg_sakujyo = 0))";

            var quesyNextSearch = ", CTE2 AS ("
                                + " SELECT"
                                + " ROW_NUMBER() OVER(ORDER BY data.cd_hin, ISNULL(data.qty_haigo_h, 0), ISNULL(data.no_han, 0)) AS row_num"
                                + " ,COUNT(data.cd_hin) OVER() AS CountAll"
                                + " , data.cd_hin"
                                + " , data.nm_hin"
                                + " , data.kbn_hin"
                                + " , data.no_han"
                                + " , data.flg_seiho_base"
                                + " , kbn_hin.nm_kbn_hin"
                                + " , CASE WHEN EXISTS (SELECT r.kbn_shitei";
            if (value.m_kirikae == Properties.Resources.m_kirikae_hyoji)
            {
                quesyNextSearch = quesyNextSearch + " FROM ma_haigo_recipe_hyoji r";
            }
            else {
                quesyNextSearch = quesyNextSearch + " FROM ma_haigo_recipe r";
            }

            quesyNextSearch = quesyNextSearch + " WHERE r.cd_haigo = data.cd_hin"
                                + " AND r.no_han = data.no_han";
            if (value.m_kirikae == Properties.Resources.m_kirikae_foodprocs)
            {
                quesyNextSearch = quesyNextSearch + " AND r.qty_haigo_h = data.qty_haigo_h";
            }
            quesyNextSearch = quesyNextSearch + " AND r.cd_hin = @cd_hin_old"
                                + " AND r.flg_sakujyo = 0"
                                + " AND r.kbn_shitei = '1')"
                                + " THEN '●' ELSE '' END AS kbn_shitei"
                                + " , data.qty_haigo_h"
                                + " FROM("
                                + " SELECT CTE.cd_haigo AS cd_hin"
                                + " , CTE.nm_haigo AS nm_hin"
                                + " , CTE.kbn_hin"
                                + " , CTE.no_han"
                                + " , CTE.flg_seiho_base"
                                + " , CTE.qty_haigo_h"
                                + " FROM CTE ";
            if (value.m_kirikae == Properties.Resources.m_kirikae_foodprocs)
            {
                quesyNextSearch = quesyNextSearch + " WHERE @flg_genzai IS NULL OR no_han = max_no_han ";
            }
            quesyNextSearch = quesyNextSearch +" UNION ALL"
                                + " SELECT DISTINCT"
                                + " cd_maeshori AS cd_hin"
                                + " , nm_maeshori AS nm_hin"
                                + " , '5' AS kbn_hin"
                                + " , NULL AS no_han"
                                + " , CAST(0 AS BIT) AS flg_seiho_base "
                                + " , NULL AS qty_haigo_h"
                                + " FROM ma_maeshori"
                                + " WHERE cd_hin = @cd_hin_old"
                                + " AND (@flg_mishiyo IS NULL OR flg_mishiyo = @flg_mishiyo)"
                                + " AND flg_sakujyo = 0"
                                + " ) data"
                                + " LEFT JOIN ma_kbn_hin kbn_hin"
                                + " ON data.kbn_hin = kbn_hin.kbn_hin)";

            quesyNextSearch = quesyNextSearch + " SELECT CountAll, row_num, cd_hin, nm_hin, kbn_hin, no_han, flg_seiho_base, nm_kbn_hin, kbn_shitei, qty_haigo_h"
                                                + " FROM CTE2"
                                                + " WHERE (@start = 1 AND @end = 0) OR ( row_num BETWEEN @start AND @end ) ORDER BY row_num";

            var queryRun = declareQuery + queryMain + quesyNextSearch;
            var parameters = new object[]
            {
                new SqlParameter("@cd_hin_old", SqlDbType.VarChar, 13) { Value = (object)value.cd_hin_old ?? DBNull.Value },
                new SqlParameter("@cd_shokuba", SqlDbType.VarChar, 2) { Value = (object)value.cd_shokuba ?? DBNull.Value },
                new SqlParameter("@flg_genzai", SqlDbType.Int) { Value = (object)value.flg_genzai ?? DBNull.Value },
                new SqlParameter("@flg_mishiyo", SqlDbType.Int) { Value = (object)value.flg_mishiyo ?? DBNull.Value },
                new SqlParameter("@skip", SqlDbType.Int) { Value = (object)value.skip ?? DBNull.Value },
                new SqlParameter("@top", SqlDbType.Int) { Value = (object)value.top ?? DBNull.Value }
            };

            results = foodproc.Database.SqlQuery<GetKubunHin4Other>(queryRun, parameters).ToList();

            return results;
        }

        /// <summary>
        /// パラメータで受け渡された target 情報をもとにエントリーを一括更新（追加・更新・削除）します。
        /// </summary>
        /// <param name="value"></param>
        /// <returns></returns>
        public HttpResponseMessage Post([FromBody]ParaSave value)
        {
            if (value == null)
            {
                return Request.CreateErrorResponse(HttpStatusCode.InternalServerError, Properties.Resources.NotNullAllow);
            }

            // TODO: キー項目の重複チェックを行います。
            //InvalidationSet<object/*TODO:targetの型を指定します*/> headerInvalidations = IsAlreadyExists(value);
            //if (headerInvalidations.Count > 0)
            //{
                //return Request.CreateResponse<InvalidationSet<object/*TODO:targetの型を指定します*/>>(HttpStatusCode.BadRequest, headerInvalidations);
            //}

            // TODO: 保存処理を実行します。
            //var result = SaveData(value);
            using (FOODPROCSEntities foodproc = new FOODPROCSEntities(value.cd_kaisha, value.cd_kojyo))
            {
                IObjectContextAdapter adapter = foodproc as IObjectContextAdapter;
                DbConnection connection = adapter.ObjectContext.Connection;
                connection.Open();
                using (DbTransaction transaction = connection.BeginTransaction())
                {
                    //配合コード変換
                    if (value.modeBind == 1)
                    {
                        //表示用配合名マスタ(ma_haigo_mei_hyoji)UPDATE
                        //配合名マスタ(ma_haigo_mei)UPDATE
                        UpdateHeader(foodproc, value);
                        //表示用配合レシピマスタ(ma_haigo_recipe_hyoji)UPDATE
                        //配合レシピマスタ(ma_haigo_recipe)UPDATE
                        UpdateDetail(foodproc, value);
                        //表示用製造ラインマスタ(ma_seizo_line_hyoji)UPDATE
                        //製造ラインマスタ(ma_seizo_line)UPDATE
                        UpdateSeizoLine(foodproc, value);
                        //Checked「製品マスタの配合コードも対象」
                        if (value.flg_seihin == 1)
                        {
                            //梱包マスタ(ma_konpo)UPDATE
                            UpdateKonpo(foodproc, value);
                            //製品マスタ(ma_seihin)UPDATE
                            UpdateSeihin(foodproc, value);
                        }
                    }
                    //品コード入替
                    else if (value.modeBind == 2)
                    {
                        foreach (var item in value.Detail.Updated) {
                            if (item.kbn_hin == KubunHin_3.ToString() || item.kbn_hin == KubunHin_4.ToString())
                            {
                                UpdateHeaderHin(foodproc, value, item);
                                if (value.kbn_hin_new == KubunHin_6)
                                {
                                    value.kbn_hin_new = int.Parse(KubunHin_1.ToString());
                                }
                                UpdateDetailHin(foodproc, value, item);
                                if (value.m_kirikae == Properties.Resources.m_kirikae_foodprocs)
                                {
                                    UpdateKoshinHin(foodproc, value, item);
                                }
                            }
                            if (value.flg_genryo == 1 && item.kbn_hin == KubunHin_5.ToString())
                            {
                                UpdateMaeshoriHin(foodproc, value, item);
                            }
                        }
                    }
                    // 品名入替
                    else if (value.modeBind == 3)
                    {
                        foreach (var item in value.Detail.Updated)
                        {
                            if (item.kbn_hin == KubunHin_3.ToString() || item.kbn_hin == KubunHin_4.ToString())
                            {
                                UpdateNamelHin(foodproc, value, item);
                                if (value.m_kirikae == Properties.Resources.m_kirikae_foodprocs)
                                {
                                    UpdateKoshinHin(foodproc, value, item);
                                }
                            }
                        }
                    }

                    transaction.Commit();
                }
            }

            return Request.CreateResponse<SearchInputChangeResponseNotUse>(HttpStatusCode.OK, null);

        }


        /// <summary>
        /// 表示用製造ラインマスタ(ma_seizo_line_hyoji)UPDATE
        /// 製造ラインマスタ(ma_seizo_line)UPDATE
        /// </summary>
        /// <param name="value"></param>
        /// <returns></returns>
        public void UpdateHeader(FOODPROCSEntities foodproc, ParaSave value) {
            //Declare variable
            string userName = decimal.Parse(UserInfo.GetUserNameFromIdentity(this.User.Identity)).ToString();

            //Update flg_sakujyo
            var queryDelete = "";
            if (value.m_kirikae == Properties.Resources.m_kirikae_hyoji)
            {
                queryDelete = "UPDATE ma_haigo_mei_hyoji";
                queryDelete = queryDelete + " SET flg_sakujyo = 1";
            }
            else
            {
                queryDelete = "UPDATE ma_haigo_mei";
                queryDelete = queryDelete + " SET flg_sakujyo = 1"
                              + ", kbn_koshin = 1 ";
            }
            queryDelete = queryDelete + " , dt_henko = GETDATE()"
                + " , cd_koshin = '0000000000'"

                + " WHERE cd_haigo = @cd_hin_old"
                + " AND flg_sakujyo <> 1";
            var parametersDelete = new object[]
                {
                    new SqlParameter("@cd_hin_old", SqlDbType.VarChar, 13) { Value = (object)value.cd_hin_old ?? DBNull.Value }
                };

            foodproc.Database.ExecuteSqlCommand(queryDelete, parametersDelete);

            //Insert new data
            var queryAdd = "";
            if (value.m_kirikae == Properties.Resources.m_kirikae_hyoji)
            {
                queryAdd = queryAdd + queryInsertHaigoMeiHyoji210;
            }
            else {
                queryAdd = queryAdd + queryInsertHaigoMei210;
            }

            queryAdd = queryAdd + " SELECT"
                + " @cd_hin_new AS cd_haigo"
                + " , nm_haigo"
                + " , nm_haigo_r"
                + " , kbn_hin"
                + " , cd_bunrui"
                + " , budomari"
                + " , qty_kihon"
                + " , ritsu_kihon"
                + " , cd_setsubi"
                + " , flg_gasan"
                + " , qty_max"
                + " , no_han"
                + " , qty_haigo_h"
                + " , qty_haigo_kei"
                + " , biko"
                + " , no_seiho"
                + " , cd_tanto_seizo"
                + " , dt_seizo"
                + " , cd_tanto_hinkan"
                + " , dt_hinkan"
                + " , dt_from"
                + " , dt_to"
                + " , kbn_vw"
                + " , hijyu"
                + " , flg_shorihin"
                + " , flg_hinkan"
                + " , flg_seizo"
                + " , 0 AS flg_sakujyo"
                + " , flg_mishiyo"
                + " , dt_toroku"
                + " , GETDATE() AS dt_henko"
                + " , @userLogin AS cd_koshin"
                + " , kbn_shiagari"
                + " , nm_haigo_rm"
                + " , cd_haigo_seiho"
                + " , flg_seiho_base"
                + " , nm_seiho";

            if (value.m_kirikae == Properties.Resources.m_kirikae_foodprocs)
            {
                queryAdd = queryAdd + " , 1 AS kbn_koshin"
                    + " , kin_keihi";
                    //+ " , flg_tenkai"
                    //+ " , qty_haigo_auto"
                    //+ " , su_kigen_min"
                    //+ " , su_kigen_max"
                    //+ " , flg_mishiyo_seizo"
                    //+ " , qty_kowake"
                    //+ " , su_kowake"
                    //+ " , kikan_shomi"
                    //+ " , qty_haigo_genka"
                    //+ " , no_han_genka"
                    //+ " , flg_unten"
                    //+ " , cd_tanto_unten"
                    //+ " , dt_unten";
            }
            if (value.m_kirikae == Properties.Resources.m_kirikae_hyoji)
            {
                queryAdd = queryAdd + " FROM ma_haigo_mei_hyoji";
            }
            else {
                queryAdd = queryAdd + " FROM ma_haigo_mei";
            }
            queryAdd = queryAdd + " WHERE cd_haigo = @cd_hin_old"
                + " AND cd_koshin = '0000000000'";

            var parametersAdd = new object[]
                {
                    new SqlParameter("@cd_hin_old", SqlDbType.VarChar, 13) { Value = (object)value.cd_hin_old ?? DBNull.Value },
                    new SqlParameter("@cd_hin_new", SqlDbType.VarChar, 13) { Value = (object)value.cd_hin_new ?? DBNull.Value },
                    new SqlParameter("@userLogin", SqlDbType.VarChar, 10) { Value = (object)userName ?? DBNull.Value },
                };
            foodproc.Database.ExecuteSqlCommand(queryAdd, parametersAdd);

            //Update flg_mishiyo
            var queryUpdate = "";
            if (value.m_kirikae == Properties.Resources.m_kirikae_hyoji)
            {
                queryUpdate = "UPDATE ma_haigo_mei_hyoji";
            }
            else {
                queryUpdate = "UPDATE ma_haigo_mei";
            }
            queryUpdate = queryUpdate + " SET"
                + " flg_mishiyo = 1"
                + " , cd_koshin = @userLogin"
                + " WHERE cd_haigo = @cd_hin_old"
                + " AND cd_koshin = '0000000000'";
            var parametersUpdate = new object[]
                {
                    new SqlParameter("@cd_hin_old", SqlDbType.VarChar, 13) { Value = (object)value.cd_hin_old ?? DBNull.Value },
                    new SqlParameter("@userLogin", SqlDbType.VarChar, 10) { Value = (object)userName ?? DBNull.Value },
                };

            foodproc.Database.ExecuteSqlCommand(queryUpdate, parametersUpdate);
        }

        /// <summary>
        /// 表示用配合レシピマスタ(ma_haigo_recipe_hyoji)UPDATE
        /// 配合レシピマスタ(ma_haigo_recipe)UPDATE
        /// </summary>
        /// <param name="value"></param>
        /// <returns></returns>
        public void UpdateDetail(FOODPROCSEntities foodproc, ParaSave value)
        {
            //Declare variable
            string userName = decimal.Parse(UserInfo.GetUserNameFromIdentity(this.User.Identity)).ToString();

            //Update flg_sakujyo
            var queryDelete = "";
            if (value.m_kirikae == Properties.Resources.m_kirikae_hyoji)
            {
                queryDelete = "UPDATE ma_haigo_recipe_hyoji";
            }
            else {
                queryDelete = "UPDATE ma_haigo_recipe";
            }
            queryDelete = queryDelete + " SET flg_sakujyo = 1"
                + " , dt_henko = GETDATE()"
                + " , cd_koshin = '0000000000'"
                + " WHERE cd_haigo = @cd_hin_old"
                + " AND flg_sakujyo <> 1";
            var parametersDelete = new object[]
                {
                    new SqlParameter("@cd_hin_old", SqlDbType.VarChar, 13) { Value = (object)value.cd_hin_old ?? DBNull.Value }
                };

            foodproc.Database.ExecuteSqlCommand(queryDelete, parametersDelete);

            //Insert new data
            var queryAdd = "";
            if (value.m_kirikae == Properties.Resources.m_kirikae_hyoji)
            {
                queryAdd = queryAdd + queryInsertHaigoRecipeHyoji210;
            }
            else {
                queryAdd = queryAdd + queryInsertHaigoRecipe210;
            }

            queryAdd = queryAdd + " SELECT"
                + " @cd_hin_new AS cd_haigo"
                + " ,no_han"
                + " ,qty_haigo_h"
                + " ,no_kotei"
                + " ,no_tonyu"
                + " ,cd_hin"
                + " ,kbn_hin"
                + " ,nm_hin"
                + " ,cd_mark"
                + " ,qty"
                + " ,qty_haigo"
                + " ,qty_nisugata"
                + " ,su_nisugata"
                + " ,qty_kowake"
                + " ,su_kowake"
                + " ,cd_futai"
                + " ,qty_futai"
                + " ,hijyu"
                + " ,budomari"
                + " , 0 AS flg_sakujyo"
                + " ,flg_mishiyo"
                + " ,dt_toroku"
                + " , GETDATE() AS dt_henko"
                + " , @userLogin AS cd_koshin"
                + " ,kbn_jyotai"
                + " ,kbn_shitei"
                + " ,kbn_bunkatsu";
            if (value.m_kirikae == Properties.Resources.m_kirikae_foodprocs)
            {
                queryAdd = queryAdd; //+ " , flg_kowake_nochk"
                    //+ " , cd_shokuba"
                    //+ " , keisu"
                    //+ " , flg_hijyu"
                    //+ " , cd_station"
                    //+ " , su_leadtime_kura";
            }
            if (value.m_kirikae == Properties.Resources.m_kirikae_hyoji)
            {
                queryAdd = queryAdd + " FROM ma_haigo_recipe_hyoji";
            }
            else {
                queryAdd = queryAdd + " FROM ma_haigo_recipe";
            }

            queryAdd = queryAdd + " WHERE cd_haigo = @cd_hin_old"
                + " AND cd_koshin = '0000000000'";

            var parametersAdd = new object[]
                {
                    new SqlParameter("@cd_hin_old", SqlDbType.VarChar, 13) { Value = (object)value.cd_hin_old ?? DBNull.Value },
                    new SqlParameter("@cd_hin_new", SqlDbType.VarChar, 13) { Value = (object)value.cd_hin_new ?? DBNull.Value },
                    new SqlParameter("@userLogin", SqlDbType.VarChar, 10) { Value = (object)userName ?? DBNull.Value },
                };
            foodproc.Database.ExecuteSqlCommand(queryAdd, parametersAdd);

            //Update flg_mishiyo
            var queryUpdate = "";
            if (value.m_kirikae == Properties.Resources.m_kirikae_hyoji)
            {
                queryUpdate = "UPDATE ma_haigo_recipe_hyoji";
            }
            else {
                queryUpdate = "UPDATE ma_haigo_recipe";
            }

            queryUpdate = queryUpdate + " SET"
                + " flg_mishiyo = 1"
                + " , cd_koshin = @userLogin"
                + " WHERE cd_haigo = @cd_hin_old"
                + " AND cd_koshin = '0000000000'";
            var parametersUpdate = new object[]
                {
                    new SqlParameter("@cd_hin_old", SqlDbType.VarChar, 13) { Value = (object)value.cd_hin_old ?? DBNull.Value },
                    new SqlParameter("@userLogin", SqlDbType.VarChar, 10) { Value = (object)userName ?? DBNull.Value },
                };

            foodproc.Database.ExecuteSqlCommand(queryUpdate, parametersUpdate);

            if (value.kbn_hin_old == KubunHin_4) {
                //Update flg_mishiyo
                var queryAll = "";
                if (value.m_kirikae == Properties.Resources.m_kirikae_hyoji)
                {
                    queryAll = "UPDATE ma_haigo_recipe_hyoji";
                }
                else {
                    queryAll = "UPDATE ma_haigo_recipe";
                }
                queryAll = queryAll + " SET"
                    + " dt_henko = GETDATE()"
                    + " , cd_koshin = @userLogin"
                    + " , cd_hin = @cd_hin_new"
                    + " WHERE cd_hin = @cd_hin_old"
                    + " AND flg_sakujyo <> 1";
                var parametersAll = new object[]
                {
                    new SqlParameter("@cd_hin_old", SqlDbType.VarChar, 13) { Value = (object)value.cd_hin_old ?? DBNull.Value },
                    new SqlParameter("@cd_hin_new", SqlDbType.VarChar, 13) { Value = (object)value.cd_hin_new ?? DBNull.Value },
                    new SqlParameter("@userLogin", SqlDbType.VarChar, 10) { Value = (object)userName ?? DBNull.Value },
                };

                foodproc.Database.ExecuteSqlCommand(queryAll, parametersAll);
            }
        }

        /// <summary>
        /// 表示用配合レシピマスタ(ma_haigo_recipe_hyoji)UPDATE
        /// 配合レシピマスタ(ma_haigo_recipe)UPDATE
        /// </summary>
        /// <param name="value"></param>
        /// <returns></returns>
        public void UpdateSeizoLine(FOODPROCSEntities foodproc, ParaSave value)
        {
            //Declare variable
            string userName = decimal.Parse(UserInfo.GetUserNameFromIdentity(this.User.Identity)).ToString();

            //Update flg_sakujyo
            var queryDelete = "";

            if (value.m_kirikae == Properties.Resources.m_kirikae_hyoji)
            {
                queryDelete = "UPDATE ma_seizo_line_hyoji";
            }
            else {
                queryDelete = "UPDATE ma_seizo_line";
            }
            queryDelete = queryDelete + " SET flg_sakujyo = 1"
                + " , dt_henko = GETDATE()"
                + " , cd_koshin = '0000000000'"
                + " WHERE cd_haigo = @cd_hin_old"
                + " AND flg_sakujyo <> 1";
            var parametersDelete = new object[]
                {
                    new SqlParameter("@cd_hin_old", SqlDbType.VarChar, 13) { Value = (object)value.cd_hin_old ?? DBNull.Value }
                };

            foodproc.Database.ExecuteSqlCommand(queryDelete, parametersDelete);

            //Insert new data
            var queryAdd = "";
            if (value.m_kirikae == Properties.Resources.m_kirikae_hyoji)
            {
                queryAdd = queryAdd + queryInsertSeizoLineHyoji210;
            }
            else {
                queryAdd = queryAdd + queryInsertSeizoLine210;
            }

            queryAdd = queryAdd + " SELECT"
                + " @cd_hin_new AS cd_haigo"
                + " ,no_yusen"
                + " ,cd_line"
                + " , 0 AS flg_sakujyo"
                + " ,flg_mishiyo"
                + " ,dt_toroku"
                + " , GETDATE() AS dt_henko"
                + " ,@userLogin AS cd_koshin";
            if (value.m_kirikae == Properties.Resources.m_kirikae_hyoji)
            {
                queryAdd = queryAdd + " ,msrepl_tran_version";
            }
            //else {
            //    queryAdd = queryAdd + " ,NEWID() AS rowguid"; //, qty_noryoku;
            //}
            if (value.m_kirikae == Properties.Resources.m_kirikae_hyoji)
            {
                queryAdd = queryAdd + " FROM ma_seizo_line_hyoji";
            }
            else {
                queryAdd = queryAdd + " FROM ma_seizo_line";
            }

            queryAdd = queryAdd + " WHERE cd_haigo = @cd_hin_old"
                + " AND cd_koshin = '0000000000'";

            var parametersAdd = new object[]
                {
                    new SqlParameter("@cd_hin_old", SqlDbType.VarChar, 13) { Value = (object)value.cd_hin_old ?? DBNull.Value },
                    new SqlParameter("@cd_hin_new", SqlDbType.VarChar, 13) { Value = (object)value.cd_hin_new ?? DBNull.Value },
                    new SqlParameter("@userLogin", SqlDbType.VarChar, 10) { Value = (object)userName ?? DBNull.Value },
                };
            foodproc.Database.ExecuteSqlCommand(queryAdd, parametersAdd);

            //Update flg_mishiyo
            var queryUpdate = "";
            if (value.m_kirikae == Properties.Resources.m_kirikae_hyoji)
            {
                queryUpdate = "UPDATE ma_seizo_line_hyoji";
            }
            else {
                queryUpdate = "UPDATE ma_seizo_line";
            }
            queryUpdate = queryUpdate + " SET"
                + " flg_mishiyo = 1"
                + " , cd_koshin = @userLogin"
                + " WHERE cd_haigo = @cd_hin_old"
                + " AND cd_koshin = '0000000000'";
            var parametersUpdate = new object[]
                {
                    new SqlParameter("@cd_hin_old", SqlDbType.VarChar, 13) { Value = (object)value.cd_hin_old ?? DBNull.Value },
                    new SqlParameter("@userLogin", SqlDbType.VarChar, 10) { Value = (object)userName ?? DBNull.Value },
                };

            foodproc.Database.ExecuteSqlCommand(queryUpdate, parametersUpdate);
        }

        /// <summary>
        /// 梱包マスタ(ma_konpo)UPDATE
        /// </summary>
        /// <param name="value"></param>
        /// <returns></returns>
        public void UpdateKonpo(FOODPROCSEntities foodproc, ParaSave value)
        {
            //Declare variable
            string userName = decimal.Parse(UserInfo.GetUserNameFromIdentity(this.User.Identity)).ToString();

            //Update ma_konpo
            var queryUpdate = "";
            queryUpdate = "UPDATE ma_konpo";
            queryUpdate = queryUpdate + " SET "
                + " dt_henko = GETDATE()"
                + " , cd_koshin = @userLogin"
                + " WHERE cd_hin IN("
                + " SELECT cd_hin"
                + " FROM ma_seihin";
            if (value.m_kirikae == Properties.Resources.m_kirikae_hyoji)
            {
                queryUpdate = queryUpdate + " WHERE cd_haigo_hyoji = @cd_hin_old";
            } else { 
                queryUpdate = queryUpdate + " WHERE cd_haigo = @cd_hin_old";
            }
            queryUpdate = queryUpdate + " AND flg_sakujyo <> 1"
                + " )"
                + " AND flg_sakujyo <> 1";
            var parametersUpdate = new object[]
                {
                    new SqlParameter("@cd_hin_old", SqlDbType.VarChar, 13) { Value = (object)value.cd_hin_old ?? DBNull.Value },
                    new SqlParameter("@userLogin", SqlDbType.VarChar, 10) { Value = (object)userName ?? DBNull.Value },
                };

            foodproc.Database.ExecuteSqlCommand(queryUpdate, parametersUpdate);
        }

        /// <summary>
        /// 製品マスタ(ma_seihin)UPDATE
        /// </summary>
        /// <param name="value"></param>
        /// <returns></returns>
        public void UpdateSeihin(FOODPROCSEntities foodproc, ParaSave value)
        {
            //Declare variable
            string userName = decimal.Parse(UserInfo.GetUserNameFromIdentity(this.User.Identity)).ToString();

            //Update ma_seihin
            var queryUpdate = "";
            queryUpdate = "UPDATE ma_seihin";
            if (value.m_kirikae == Properties.Resources.m_kirikae_hyoji)
            {
                queryUpdate = queryUpdate + " SET cd_haigo_hyoji = @cd_hin_new";
            }
            else {
                queryUpdate = queryUpdate + " SET cd_haigo = @cd_hin_new";
            }

            queryUpdate = queryUpdate + " , dt_henko = GETDATE()"
                + " , cd_koshin = @userLogin";
            if (value.m_kirikae == Properties.Resources.m_kirikae_hyoji)
            {
                queryUpdate = queryUpdate + " WHERE cd_haigo_hyoji = @cd_hin_old";
            } else{
                queryUpdate = queryUpdate + " WHERE cd_haigo = @cd_hin_old";
            }
                queryUpdate = queryUpdate + " AND flg_sakujyo <> 1";
            var parametersUpdate = new object[]
                {
                    new SqlParameter("@cd_hin_old", SqlDbType.VarChar, 13) { Value = (object)value.cd_hin_old ?? DBNull.Value },
                    new SqlParameter("@cd_hin_new", SqlDbType.VarChar, 13) { Value = (object)value.cd_hin_new ?? DBNull.Value },
                    new SqlParameter("@userLogin", SqlDbType.VarChar, 10) { Value = (object)userName ?? DBNull.Value },
                };

            foodproc.Database.ExecuteSqlCommand(queryUpdate, parametersUpdate);
        }

        /// <summary>
        /// 表示用配合名マスタ(ma_haigo_mei_hyoji)UPDATE
        /// 配合名マスタ(ma_haigo_mei)UPDATE
        /// </summary>
        /// <param name="value"></param>
        /// <returns></returns>
        public void UpdateHeaderHin(FOODPROCSEntities foodproc, ParaSave value, GetKubunHin4Other item) {
            //Declare variable
            string userName = decimal.Parse(UserInfo.GetUserNameFromIdentity(this.User.Identity)).ToString();

            //Update ma_seihin
            var queryUpdate = "";
            queryUpdate = "UPDATE mm";
            queryUpdate = queryUpdate + " SET mm.flg_hinkan = 0"
                + " , mm.flg_seizo = 0"
                + " , mm.cd_koshin = @userLogin"
                + " , mm.dt_henko = GETDATE()";
            if (value.m_kirikae == Properties.Resources.m_kirikae_hyoji)
            {
                queryUpdate = queryUpdate + " FROM ma_haigo_mei_hyoji mm"
                    + " INNER JOIN ma_haigo_recipe_hyoji mr";
            }
            else {
                queryUpdate = queryUpdate + " FROM ma_haigo_mei mm"
                        + " INNER JOIN ma_haigo_recipe mr";
            }

            queryUpdate = queryUpdate + " ON mm.cd_haigo = mr.cd_haigo"
                + " AND mm.no_han = mr.no_han"
                + " AND mr.flg_sakujyo = 0"
                + " AND mr.flg_sakujyo = 0";
            if (value.m_kirikae == Properties.Resources.m_kirikae_foodprocs)
            {
                queryUpdate = queryUpdate + " AND mm.qty_haigo_h = mr.qty_haigo_h";
            }


            queryUpdate = queryUpdate + " WHERE mr.cd_hin = @cd_hin_old"
                + " AND mr.kbn_shitei = '1'"
                + " AND mr.cd_haigo = @cd_hin"
                + " AND mr.no_han = @no_han";
            if (value.m_kirikae == Properties.Resources.m_kirikae_foodprocs)
            {
                queryUpdate = queryUpdate + " AND mr.qty_haigo_h = @qty_haigo_h";
            }
            var parametersUpdate = new object[]
                {
                    new SqlParameter("@cd_hin_old", SqlDbType.VarChar, 13) { Value = (object)value.cd_hin_old ?? DBNull.Value },
                    new SqlParameter("@cd_hin_new", SqlDbType.VarChar, 13) { Value = (object)value.cd_hin_new ?? DBNull.Value },
                    new SqlParameter("@userLogin", SqlDbType.VarChar, 10) { Value = (object)userName ?? DBNull.Value },
                    new SqlParameter("@cd_hin", SqlDbType.VarChar, 13) { Value = (object)item.cd_hin ?? DBNull.Value },
                    new SqlParameter("@no_han", SqlDbType.Int) { Value = (object)item.no_han ?? DBNull.Value },
                    new SqlParameter("@qty_haigo_h", SqlDbType.Int) { Value = (object)item.qty_haigo_h ?? DBNull.Value },
                };

            foodproc.Database.ExecuteSqlCommand(queryUpdate, parametersUpdate);
        }

        /// <summary>
        /// 表示用配合レシピマスタ(ma_haigo_recipe_hyoji)UPDATE
        /// 配合レシピマスタ(ma_haigo_recipe)UPDATE
        /// </summary>
        /// <param name="value"></param>
        /// <returns></returns>
        public void UpdateDetailHin(FOODPROCSEntities foodproc, ParaSave value, GetKubunHin4Other item)
        {
            //Declare variable
            string userName = decimal.Parse(UserInfo.GetUserNameFromIdentity(this.User.Identity)).ToString();

            //Update ma_seihin
            var queryUpdate = "";
            if (value.m_kirikae == Properties.Resources.m_kirikae_hyoji)
            {
                queryUpdate = "UPDATE ma_haigo_recipe_hyoji";
            }
            else {
                queryUpdate = "UPDATE ma_haigo_recipe";
            }
            queryUpdate = queryUpdate + " SET cd_hin = @cd_hin_new"
                + " , nm_hin = @nm_hin_new"
                + " , kbn_hin = @kbn_hin_new"
                + " , kbn_jyotai = @kbn_jyotai_new"
                + " , dt_henko = GETDATE()"
                + " , cd_koshin = @userLogin";

            if (value.flg_haigo == 1)
            {
                if (value.kbn_hin_new == KubunHin_1 || value.kbn_hin_new == KubunHin_6)
                {
                    queryUpdate = queryUpdate + " , qty_nisugata = @qty_nisugata_new";
                }
                if (value.kbn_hin_new == KubunHin_5) {
                    queryUpdate = queryUpdate + " , qty_nisugata = 0";
                }
            }

            queryUpdate = queryUpdate + " WHERE cd_hin = @cd_hin_old"
                + " AND flg_sakujyo = 0"
                + " AND cd_haigo = @cd_hin"
                + " AND no_han = @no_han";
            if (value.m_kirikae == Properties.Resources.m_kirikae_foodprocs)
            {
                queryUpdate = queryUpdate + " AND qty_haigo_h = @qty_haigo_h";
            }

            var parametersUpdate = new object[]
                {
                    new SqlParameter("@cd_hin_old", SqlDbType.VarChar, 13) { Value = (object)value.cd_hin_old ?? DBNull.Value },
                    new SqlParameter("@cd_hin_new", SqlDbType.VarChar, 13) { Value = (object)value.cd_hin_new ?? DBNull.Value },
                    new SqlParameter("@nm_hin_new", SqlDbType.VarChar, 60) { Value = (object)value.nm_hin_new ?? DBNull.Value },
                    new SqlParameter("@kbn_jyotai_new", SqlDbType.VarChar, 1) { Value = (object)value.kbn_jyotai_new ?? DBNull.Value },
                    new SqlParameter("@kbn_hin_new", SqlDbType.VarChar, 1) { Value = (object)value.kbn_hin_new ?? DBNull.Value },
                    new SqlParameter("@userLogin", SqlDbType.VarChar, 10) { Value = (object)userName ?? DBNull.Value },
                    new SqlParameter("@cd_hin", SqlDbType.VarChar, 13) { Value = (object)item.cd_hin ?? DBNull.Value },
                    new SqlParameter("@no_han", SqlDbType.Int) { Value = (object)item.no_han ?? DBNull.Value },
                    new SqlParameter("@qty_haigo_h", SqlDbType.Int) { Value = (object)item.qty_haigo_h ?? DBNull.Value },
                    new SqlParameter("@qty_nisugata_new", SqlDbType.Float) { Value = (object)value.qty_nisugata_new ?? DBNull.Value },
                };

            foodproc.Database.ExecuteSqlCommand(queryUpdate, parametersUpdate);
        }

        /// <summary>
        /// 前処理マスタ(ma_maeshori)UPDATE
        /// </summary>
        /// <param name="value"></param>
        /// <returns></returns>
        public void UpdateMaeshoriHin(FOODPROCSEntities foodproc, ParaSave value, GetKubunHin4Other item)
        {
            //Declare variable
            string userName = decimal.Parse(UserInfo.GetUserNameFromIdentity(this.User.Identity)).ToString();

            //Update ma_seihin
            var queryUpdate = "";
            queryUpdate = "UPDATE ma_maeshori";
            queryUpdate = queryUpdate + " SET cd_hin = @cd_hin_new"
                + " , dt_henko = GETDATE()"
                + " , cd_koshin = @userLogin";

            queryUpdate = queryUpdate + " WHERE cd_hin = @cd_hin_old"
                + " AND flg_sakujyo = 0"
                + " AND cd_maeshori = @cd_hin";

            var parametersUpdate = new object[]
                {
                    new SqlParameter("@cd_hin_old", SqlDbType.VarChar, 13) { Value = (object)value.cd_hin_old ?? DBNull.Value },
                    new SqlParameter("@cd_hin_new", SqlDbType.VarChar, 13) { Value = (object)value.cd_hin_new ?? DBNull.Value },
                    new SqlParameter("@userLogin", SqlDbType.VarChar, 10) { Value = (object)userName ?? DBNull.Value },
                    new SqlParameter("@cd_hin", SqlDbType.VarChar, 13) { Value = (object)item.cd_hin ?? DBNull.Value },
                };

            foodproc.Database.ExecuteSqlCommand(queryUpdate, parametersUpdate);
        }

        /// <summary>
        /// 表示用配合レシピマスタ(ma_haigo_recipe_hyoji)UPDATE
        /// 配合レシピマスタ(ma_haigo_recipe)UPDATE
        /// </summary>
        /// <param name="value"></param>
        /// <returns></returns>
        public void UpdateNamelHin(FOODPROCSEntities foodproc, ParaSave value, GetKubunHin4Other item)
        {
            //Declare variable
            string userName = decimal.Parse(UserInfo.GetUserNameFromIdentity(this.User.Identity)).ToString();

            //Update ma_seihin
            var queryUpdate = "";
            if (value.m_kirikae == Properties.Resources.m_kirikae_hyoji)
            {
                queryUpdate = "UPDATE ma_haigo_recipe_hyoji";
            }
            else {
                queryUpdate = "UPDATE ma_haigo_recipe";
            }
            queryUpdate = queryUpdate + " SET nm_hin = @nm_hin_new"
                + " , dt_henko = GETDATE()"
                + " , cd_koshin = @userLogin";

            queryUpdate = queryUpdate + " WHERE cd_hin = @cd_hin_old"
                + " AND flg_sakujyo = 0"
                + " AND cd_haigo = @cd_hin"
                + " AND no_han = @no_han";

            if (value.m_kirikae == Properties.Resources.m_kirikae_foodprocs)
            {
                queryUpdate = queryUpdate + " AND qty_haigo_h = @qty_haigo_h";
            }

            var parametersUpdate = new object[]
                {
                    new SqlParameter("@cd_hin_old", SqlDbType.VarChar, 13) { Value = (object)value.cd_hin_old ?? DBNull.Value },
                    new SqlParameter("@nm_hin_new", SqlDbType.VarChar, 60) { Value = (object)value.nm_hin_new ?? DBNull.Value },
                    new SqlParameter("@userLogin", SqlDbType.VarChar, 10) { Value = (object)userName ?? DBNull.Value },
                    new SqlParameter("@cd_hin", SqlDbType.VarChar, 13) { Value = (object)item.cd_hin ?? DBNull.Value },
                    new SqlParameter("@no_han", SqlDbType.Int) { Value = (object)item.no_han ?? DBNull.Value },
                    new SqlParameter("@qty_haigo_h", SqlDbType.Int) { Value = (object)item.qty_haigo_h ?? DBNull.Value },
                };

            foodproc.Database.ExecuteSqlCommand(queryUpdate, parametersUpdate);
        }

        /// <summary>
        /// 配合名マスタ(ma_haigo_mei)
        /// </summary>
        /// <param name="value"></param>
        /// <returns></returns>
        public void UpdateKoshinHin(FOODPROCSEntities foodproc, ParaSave value, GetKubunHin4Other item)
        {
            //Declare variable
            string userName = decimal.Parse(UserInfo.GetUserNameFromIdentity(this.User.Identity)).ToString();

            //Update ma_haigo_mei
            var queryUpdate = "";
            queryUpdate = "UPDATE ma_haigo_mei";
            queryUpdate = queryUpdate + " SET kbn_koshin = 1"
                + " , dt_henko = GETDATE()"
                + " , cd_koshin = @userLogin";

            queryUpdate = queryUpdate + " WHERE flg_sakujyo = 0"
                + " AND cd_haigo = @cd_hin"
                + " AND no_han = @no_han"
                + " AND qty_haigo_h = @qty_haigo_h";

            var parametersUpdate = new object[]
                {
                    new SqlParameter("@userLogin", SqlDbType.VarChar, 10) { Value = (object)userName ?? DBNull.Value },
                    new SqlParameter("@cd_hin", SqlDbType.VarChar, 13) { Value = (object)item.cd_hin ?? DBNull.Value },
                    new SqlParameter("@no_han", SqlDbType.Int) { Value = (object)item.no_han ?? DBNull.Value },
                    new SqlParameter("@qty_haigo_h", SqlDbType.Int) { Value = (object)item.qty_haigo_h ?? DBNull.Value },
                };

            foodproc.Database.ExecuteSqlCommand(queryUpdate, parametersUpdate);
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

    //public class SearchInputChangeResponse
    //{
    //    public SearchInputChangeResponse()
    //    {
    //        this.Detail = new List<object/*TODO: target の型を指定します*/>();
    //    }

    //    public List<object/*TODO: target の型を指定します*/> Detail { get; set; }
    //}

    //Data ma_shokuba
    public class ShokubaData {
        public string cd_shokuba { get; set; }
        public string nm_shokuba { get; set; }
    }

    //Data haigo
    public class HaigoCodeData210 {
        public string cd_hin { get; set; }
        public string nm_hin { get; set; }
        public string kbn_hin { get; set; }
        public string nm_kbn_hin { get; set; }
        public string qty_nisugata { get; set; }
    }

    //Data haigo
    public class HinCodeData210
    {
        public string cd_hin { get; set; }
        public string nm_hin { get; set; }
        public int kbn_hin { get; set; }
        public string nm_kbn_hin { get; set; }
        public int? kbn_jyotai { get; set; }
        public double? hijyu { get; set; }
        public string cd_tani_hin { get; set; }
        public string nm_tani { get; set; }
        public double? qty_nisugata { get; set; }
    }

    //Parameter Search
    public class CodeFurikaePara {
        public string m_kirikae { get; set; }
        public int cd_kaisha { get; set; }
        public int cd_kojyo { get; set; }
        public string cd_hin_old { get; set; }
        //public string cd_hin_new { get; set; }
        public int kbn_hin_old { get; set; }
        //public int kbn_hin_new { get; set; }
        public int? flg_seihin { get; set; }
        public int? flg_genryo { get; set; }
        public int? flg_haigo { get; set; }
        public int? flg_genzai { get; set; }
        public int? flg_mishiyo { get; set; }
        public string cd_shokuba { get; set; }
        public int modeBind { get; set; }
        public int skip { get; set; }
        public int top { get; set; }

    }

    //Data GetKubunHin 3 and 4 Haigo
    public class GetKubunHin3_4_1
    {
        public string cd_hin { get; set; }
        public string nm_hin { get; set; }
        public string kbn_hin { get; set; }
        public string nm_kbn_hin { get; set; }
        public int? CountAll { get; set; }
        public long? row_num { get; set; }
    }

    //Data GetKubunHin 4 Other
    public class GetKubunHin4Other
    {
        public string cd_hin { get; set; }
        public string nm_hin { get; set; }
        public string kbn_hin { get; set; }
        public int? no_han { get; set; }
        public bool? flg_seiho_base { get; set; }
        public string nm_kbn_hin { get; set; }
        public string kbn_shitei { get; set; }
        public int? qty_haigo_h { get; set; }
        public int? CountAll { get; set; }
        public long? row_num { get; set; }
    }


    //Data search
    public class DataSearch {
        public List<GetKubunHin3_4_1> Items1 { get; set; }
        public List<GetKubunHin4Other> Items2 { get; set; }
        public int? Count1 { get; set; }
        public int? Count2 { get; set; }
        public int modeBind { get; set; }
        public byte? kbn_nmacs_kojyo { get; set; }
    }

    //Para save
    public class ParaSave {
        public ChangeSet<GetKubunHin4Other> Detail { get; set; }
        public string m_kirikae { get; set; }
        public int modeBind { get; set; }
        public int cd_kaisha { get; set; }
        public int cd_kojyo { get; set; }
        public string cd_hin_old { get; set; }
        public string cd_hin_new { get; set; }
        public int kbn_hin_old { get; set; }
        public int kbn_hin_new { get; set; }
        public string nm_hin_new { get; set; }
        public int flg_seihin { get; set; }
        public int flg_genryo { get; set; }
        public int flg_haigo { get; set; }
        public int flg_genzai { get; set; }
        public int flg_mishiyo { get; set; }
        public string cd_shokuba { get; set; }
        public string kbn_jyotai_new { get; set; }
        public double? qty_nisugata_new { get; set; }
    }
    #endregion
}
