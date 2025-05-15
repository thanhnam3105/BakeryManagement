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
    public class HaigoCopy_DialogController : ApiController
    {
        #region "Controllerで公開するAPI"
        public readonly string FlgFlase = "0";
        public readonly string FlgTrue = "1";
        public readonly string NoHanFirst = "1";
        public readonly byte? KubunNmacsKojyo = 2;
        public readonly byte? KubunHin_1 = 1;
        public readonly byte? KubunHin_2 = 2;
        public readonly byte? KubunHin_3 = 3;
        public readonly byte? KubunHin_4 = 4;
        public readonly byte? KubunHin_5 = 5;
        public readonly byte? KubunHin_6 = 6;
        public readonly byte? KubunHin_9 = 9;
        public readonly int KBN_NMACS_RELATE = 1;

        #region "queryInsertHaigoMeiHyoji"
        public readonly string queryInsertHaigoMeiHyoji = "INSERT INTO [dbo].[ma_haigo_mei_hyoji]"
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
        public readonly string queryInsertHaigoMei = "INSERT INTO [dbo].[ma_haigo_mei]"
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
           + " ,[kbn_koshin]{0})";
        #endregion

        #region "queryInsertHaigoRecipeHyoji"
        public readonly string queryInsertHaigoRecipeHyoji = "INSERT INTO [dbo].[ma_haigo_recipe_hyoji]"
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
        public readonly string queryInsertHaigoRecipe = "INSERT INTO [dbo].[ma_haigo_recipe]"
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
        public readonly string queryInsertSeizoLineHyoji = "INSERT INTO [dbo].[ma_seizo_line_hyoji]"
                                                                               + "([cd_haigo]"
                                                                               + ",[no_yusen]"
                                                                               + ",[cd_line]"
                                                                               + ",[flg_sakujyo]"
                                                                               + ",[flg_mishiyo]"
                                                                               + ",[dt_toroku]"
                                                                               + ",[dt_henko]"
                                                                               + ",[cd_koshin])";
        #endregion

        #region "queryInsertSeizoLine"
        public readonly string queryInsertSeizoLine = "INSERT INTO [dbo].[ma_seizo_line]"
                                                                               + "([cd_haigo]"
                                                                               + ",[no_yusen]"
                                                                               + ",[cd_line]"
                                                                               + ",[flg_sakujyo]"
                                                                               + ",[flg_mishiyo]"
                                                                               + ",[dt_toroku]"
                                                                               + ",[dt_henko]"
                                                                               + ",[cd_koshin])";
        #endregion

        /// <summary>
        /// パラメータで受け渡された 値に該当するすべてのtarget情報を StoredProcedure を利用して取得します。
        /// </summary>
        /// <param name="options"></param>
        /// <returns>target情報</returns>
        public HaigoData Get([FromUri] HaigoCopyPara value)
        {
            // TODO:target情報を管理しているDbContextとtargetの型を指定します。
            CommonController common = new CommonController();
            if (!common.CheckKaishaKojyo(value.cd_kaisha, value.cd_kojyo))
            {
                return null;
            }

            FOODPROCSEntities foodproc = new FOODPROCSEntities(value.cd_kaisha, value.cd_kojyo);
            foodproc.Configuration.ProxyCreationEnabled = false;

            HaigoData results = new HaigoData();

            var haigofrom = "SELECT TOP 1 cd_haigo, nm_haigo, kbn_hin, kbn_vw, qty_kihon";
            if (value.m_kirikae == Properties.Resources.m_kirikae_hyoji)
            {
                haigofrom = haigofrom + " FROM ma_haigo_mei_hyoji";
            }
            else
            {
                haigofrom = haigofrom + " FROM ma_haigo_mei";
            }
            haigofrom = haigofrom + " WHERE flg_sakujyo = @FlgFlase"
                          + " AND cd_haigo = @cd_haigo"
                          + " AND no_han = 1";

            if (value.m_kirikae == Properties.Resources.m_kirikae_foodprocs)
            {
                haigofrom = haigofrom + " AND qty_kihon = qty_haigo_h";
            }
            haigofrom = haigofrom + " ORDER BY no_han";

            results.DataFrom = foodproc.Database.SqlQuery<HaiGoFrom>(haigofrom, new SqlParameter("@cd_haigo", value.cd_haigo)
                                                                    , new SqlParameter("@FlgFlase", "0")).ToList();

            float outputHaigo;
            var isNumericHaigo = float.TryParse(value.cd_haigo, out outputHaigo);
            if (!isNumericHaigo)
            {
                results.DataTo = null;
                return results;
            }

            results.DataTo = GetHaigoTo(value);

            return results;
        }

        /// <summary>
        /// パラメータで受け渡された 値に該当するすべてのtarget情報を StoredProcedure を利用して取得します。
        /// </summary>
        /// <param name="options"></param>
        /// <returns>target情報</returns>
        public List<HaiGoTo> GetHaigoTo([FromUri] HaigoCopyPara value)
        {
            CommonController common = new CommonController();
            if (!common.CheckKaishaKojyo(value.cd_kaisha, value.cd_kojyo))
            {
                return null;
            }
            FOODPROCSEntities foodproc = new FOODPROCSEntities(value.cd_kaisha, value.cd_kojyo);
            foodproc.Configuration.ProxyCreationEnabled = false;

            List<HaiGoTo> haigoTo = new List<HaiGoTo>();
            HaiGoTo haigoData = new HaiGoTo();
            var query = " SELECT TOP 1 cd_hin_var AS cd_genryo"
                                + " , nm_hin AS nm_genryo"
                                + " , kbn_hin"
                                + " , flg_mishiyo";
            if (value.radio_kirikae == Properties.Resources.m_kirikae_hyoji)
            {
                query = query + " FROM SS_vw_hincheck_hyoji";
            }
            else
            {
                query = query + " FROM SS_vw_hincheck";
            }
            query = query + " WHERE cd_hin = @cd_haigo";
            query = query + " ORDER BY kbn_hin";

            haigoData = foodproc.Database.SqlQuery<HaiGoTo>(query, new SqlParameter("@cd_haigo", value.cd_haigo)).FirstOrDefault();

            if (haigoData != null)
            {
                if (haigoData.kbn_hin == KubunHin_3)
                {
                    var querySeihin = " SELECT COUNT(*) AS count"
                                    + " FROM ma_seihin";

                    if (value.radio_kirikae == Properties.Resources.m_kirikae_hyoji)
                    {
                        querySeihin = querySeihin + " WHERE cd_haigo_hyoji = @cd_haigo";
                    }
                    else
                    {
                        querySeihin = querySeihin + " WHERE cd_haigo = @cd_haigo";
                    }
                    querySeihin = querySeihin + " AND flg_sakujyo = @FlgFlase"
                                                + " AND flg_mishiyo = @FlgFlase";

                    haigoData.countSeihin = foodproc.Database.SqlQuery<int>(querySeihin, new SqlParameter("@cd_haigo", value.cd_haigo)
                                                                                        , new SqlParameter("@FlgFlase", FlgFlase)).FirstOrDefault();
                }

                byte? kbn_nmacs_kojyo = GetKubunNmacKojyo(value.cd_kaisha, value.cd_kojyo);
                if (kbn_nmacs_kojyo == KubunNmacsKojyo && haigoData.kbn_hin == KubunHin_6)
                {
                    //No duplicate
                    haigoData.isDuplicate = 0;
                    haigoData.modeBind = 1;
                }
                else if (haigoData.kbn_hin == KubunHin_3 || haigoData.kbn_hin == KubunHin_4)
                {
                    //duplicate
                    haigoData.isDuplicate = 1;
                    haigoData.modeBind = 2;
                    var queryKbnvw = "SELECT TOP 1 kbn_vw, qty_kihon";
                    if (value.radio_kirikae == Properties.Resources.m_kirikae_hyoji)
                    {
                        queryKbnvw = queryKbnvw + " FROM ma_haigo_mei_hyoji";
                    }
                    else
                    {
                        queryKbnvw = queryKbnvw + " FROM ma_haigo_mei";
                    }
                    queryKbnvw = queryKbnvw + " WHERE flg_sakujyo = @FlgFlase"
                                              + " AND cd_haigo = @cd_haigo"
                                              + " AND no_han = 1";

                    if (value.radio_kirikae == Properties.Resources.m_kirikae_foodprocs)
                    {
                        queryKbnvw = queryKbnvw + " AND qty_kihon = qty_haigo_h";
                    }
                    queryKbnvw = queryKbnvw + " ORDER BY no_han";

                    var KubunVw = foodproc.Database.SqlQuery<KubunVw>(queryKbnvw, new SqlParameter("@cd_haigo", value.cd_haigo)
                                                                                            , new SqlParameter("@FlgFlase", "0")).FirstOrDefault();

                    if (KubunVw != null)
                    {
                        haigoData.kbn_vw = KubunVw.kbn_vw;
                        haigoData.qty_kihon = KubunVw.qty_kihon;
                    }


                }
                else
                {
                    //Add para message AP0058
                    if (haigoData.kbn_hin == KubunHin_1)
                    {
                        haigoData.isError = "原料";
                    }
                    else if (haigoData.kbn_hin == KubunHin_2)
                    {
                        haigoData.isError = "資材";
                    }
                    else if (haigoData.kbn_hin == KubunHin_5)
                    {
                        haigoData.isError = "前処理原料";
                    }
                    else if (haigoData.kbn_hin == KubunHin_6)
                    {
                        haigoData.isError = "製品";
                    }
                    else
                    {
                        haigoData.isError = "AP0058";
                    }

                }

                haigoData.no_han_max = GetMaxNoHan(value);

                haigoTo.Add(haigoData);
            }

            return haigoTo;
        }

        /// <summary>
        /// Get kbn_nmac_kojyo
        /// </summary>
        /// <returns>Get kbn_nmac_kojyo</returns>
        public byte? GetKubunNmacKojyo(int cd_kaisha, int cd_kojyo)
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
        /// GetMaxNoHan
        /// </summary>
        /// <returns>Get max no_han</returns>
        public int? GetMaxNoHan([FromUri] HaigoCopyPara value)
        {
            int? results;
            CommonController common = new CommonController();
            if (!common.CheckKaishaKojyo(value.cd_kaisha, value.cd_kojyo))
            {
                return null;
            }
            using (FOODPROCSEntities foodproc = new FOODPROCSEntities(value.cd_kaisha, value.cd_kojyo))
            {
                foodproc.Configuration.ProxyCreationEnabled = false;

                var query = "SELECT MAX(no_han) AS max_no_han";
                if (value.radio_kirikae == Properties.Resources.m_kirikae_hyoji)
                {
                    query = query + " FROM ma_haigo_mei_hyoji";
                }
                else
                {
                    query = query + " FROM ma_haigo_mei";
                }
                query = query + " WHERE flg_sakujyo = @FlgFlase"
                              + " AND cd_haigo = @cd_haigo";

                if (value.radio_kirikae == Properties.Resources.m_kirikae_foodprocs)
                {
                    query = query + " AND qty_kihon = qty_haigo_h";
                }

                results = foodproc.Database.SqlQuery<int?>(query, new SqlParameter("@cd_haigo", value.cd_haigo)
                                                                        , new SqlParameter("@FlgFlase", "0")).FirstOrDefault();

            }

            return results;
        }

        /// <summary>
        /// Get no_han
        /// </summary>
        /// <returns>Get list no_han</returns>
        public object GetNoHanList([FromUri] HaigoCopyPara value)
        {
            object results;
            CommonController common = new CommonController();
            if (!common.CheckKaishaKojyo(value.cd_kaisha, value.cd_kojyo))
            {
                return null;
            }
            using (FOODPROCSEntities foodproc = new FOODPROCSEntities(value.cd_kaisha, value.cd_kojyo))
            {
                foodproc.Configuration.ProxyCreationEnabled = false;

                var query = "SELECT DISTINCT no_han";
                if (value.m_kirikae == Properties.Resources.m_kirikae_hyoji)
                {
                    query = query + " FROM ma_haigo_mei_hyoji";
                }
                else
                {
                    query = query + " FROM ma_haigo_mei";
                }
                query = query + " WHERE flg_sakujyo = @FlgFlase"
                              + " AND cd_haigo = @cd_haigo";

                if (value.m_kirikae == Properties.Resources.m_kirikae_foodprocs)
                {
                    query = query + " AND qty_kihon = qty_haigo_h";
                }
                query = query + " ORDER BY no_han";

                results = foodproc.Database.SqlQuery<NoHanList>(query, new SqlParameter("@cd_haigo", value.cd_haigo)
                                                                        , new SqlParameter("@FlgFlase", "0")).ToList();

            }

            return results;
        }

        /// <summary>
        /// Get no_han
        /// </summary>
        /// <returns>Get list no_han</returns>
        public string CheckExistsData(ParaHaigoSave value)
        {
            string results;
            CommonController common = new CommonController();
            var haigoFrom = value.HaigoFrom;
            var haigoTo = value.HaigoTo;

            if (!common.CheckKaishaKojyo(haigoFrom.cd_kaisha_from, haigoTo.cd_kojyo_to))
            {
                return null;
            }
            using (FOODPROCSEntities foodproc = new FOODPROCSEntities(haigoFrom.cd_kaisha_from, haigoTo.cd_kojyo_to))
            {
                int? no_han_to = haigoTo.no_han_to;
                if (value.HaigoFrom.zenban_chk == 1)
                {
                    no_han_to = null;
                }
                foodproc.Configuration.ProxyCreationEnabled = false;

                var query = "SELECT TOP 1 cd_haigo";
                if (haigoTo.radio_kirikae == Properties.Resources.m_kirikae_hyoji)
                {
                    query = query + " FROM ma_haigo_mei_hyoji";
                }
                else
                {
                    query = query + " FROM ma_haigo_mei";
                }
                query = query + " WHERE flg_sakujyo = @FlgFlase"
                              + " AND cd_haigo = @cd_haigo_to"
                              + " AND (@no_han_to IS NULL OR no_han = @no_han_to)";

                if (haigoTo.radio_kirikae == Properties.Resources.m_kirikae_foodprocs)
                {
                    query = query + " AND qty_kihon = qty_haigo_h";
                }
                query = query + " ORDER BY no_han";

                var parameters = new object[]
                {
                    new SqlParameter("@cd_haigo_to", SqlDbType.VarChar, 13) { Value = (object)haigoTo.cd_haigo_to ?? DBNull.Value },
                    new SqlParameter("@no_han_to", SqlDbType.Int) { Value = (object)no_han_to ?? DBNull.Value },
                    new SqlParameter("@FlgFlase", SqlDbType.VarChar, 1) { Value = (object)FlgFlase ?? DBNull.Value },
                };
                results = foodproc.Database.SqlQuery<string>(query, parameters).FirstOrDefault();
            }

            return results;
        }

        /// <summary>
        /// パラメータで受け渡された target 情報をもとにエントリーを一括更新（追加・更新・削除）します。
        /// </summary>
        /// <param name="value"></param>
        /// <returns></returns>
        public HttpResponseMessage Post([FromBody] ParaHaigoSave value)
        {
            if (value == null || value.HaigoFrom == null || value.HaigoTo == null)
            {
                return Request.CreateErrorResponse(HttpStatusCode.InternalServerError, Properties.Resources.NotNullAllow);
            }

            var hasErrorExists = CheckExistsData(value);
            if (hasErrorExists != null) {
                return Request.CreateResponse<string>(HttpStatusCode.BadRequest, "MS0017");
            }

            var haigoFrom = value.HaigoFrom;
            var haigoTo = value.HaigoTo;
            int isOtherLineCode = 0;
            string cd_line_to = "0000000000000";
            int kbn_nmacs_kojyo = 0;
            using (ShohinKaihatsuEntities context = new ShohinKaihatsuEntities())
            {
                if (haigoFrom.cd_kojyo_from != haigoTo.cd_kojyo_to)
                {
                    var lineCodeFrom = context.ma_kojyo.Where(m => m.cd_kaisha == haigoFrom.cd_kaisha_from && m.cd_kojyo == haigoFrom.cd_kojyo_from).FirstOrDefault();
                    var lineCodeTo = context.ma_kojyo.Where(m => m.cd_kaisha == haigoFrom.cd_kaisha_from && m.cd_kojyo == haigoTo.cd_kojyo_to).FirstOrDefault();

                    if (lineCodeFrom != null && lineCodeTo != null)
                    {
                        if (lineCodeFrom.su_linecode_standard != lineCodeTo.su_linecode_standard)
                        {
                            isOtherLineCode = 1;
                            int length = (cd_line_to + lineCodeTo.cd_line.ToString()).Length;
                            int lengthStandard = length - lineCodeTo.su_linecode_standard;
                            cd_line_to = (cd_line_to + lineCodeTo.cd_line.ToString()).Substring(lengthStandard, lineCodeTo.su_linecode_standard);
                        }
                    }
                }

                //Get kbn_nmacs_kojyo
                vw_kaisha_kojyo kaisha_kojyo = new vw_kaisha_kojyo();
                kaisha_kojyo = (from m in context.vw_kaisha_kojyo
                                where m.cd_kaisha == haigoFrom.cd_kaisha_from
                                && m.cd_kojyo == haigoTo.cd_kojyo_to
                                select m).FirstOrDefault();
                if (kaisha_kojyo != null) 
                {
                    kbn_nmacs_kojyo = kaisha_kojyo.kbn_nmacs_kojyo;
                }
            }

            List<HeaderData_From> HeaderFrom = new List<HeaderData_From>();
            List<DetailData_From> DetailFrom = new List<DetailData_From>();
            List<DataSeizoLineFrom> DataSeizoLineFrom = new List<DataSeizoLineFrom>();
            List<DataSeihinFrom> DataSeihinKonpo = new List<DataSeihinFrom>();
            DataBeforeUpdate DataFirstFrom = new DataBeforeUpdate();
            using (FOODPROCSEntities foodproc = new FOODPROCSEntities(haigoFrom.cd_kaisha_from, haigoFrom.cd_kojyo_from))
            {
                if (haigoFrom.zenban_chk == 1 || (haigoFrom.zenban_chk == 0 && haigoTo.isDuplicate == 0))
                {
                    HeaderFrom = HeaderDataFrom(foodproc, value);
                }
                DataFirstFrom = DataBeforeUpdateFrom(foodproc, value);
                DetailFrom = GetDataDetail(foodproc, value);
                DataSeizoLineFrom = GetDataSeizoLineFrom(foodproc, value, isOtherLineCode, cd_line_to);
                DataSeihinKonpo = GetSeihinKonpo(foodproc, value);
            }

            using (FOODPROCSEntities foodproc = new FOODPROCSEntities(haigoFrom.cd_kaisha_from, haigoTo.cd_kojyo_to))
            {
                if (!(haigoFrom.zenban_chk == 1 || (haigoFrom.zenban_chk == 0 && haigoTo.isDuplicate == 0)))
                {
                    HeaderFrom = HeaderDataFrom(foodproc, value);
                }
                IObjectContextAdapter adapter = foodproc as IObjectContextAdapter;
                DbConnection connection = adapter.ObjectContext.Connection;
                connection.Open();

                using (DbTransaction transaction = connection.BeginTransaction())
                {
                    if (haigoTo.isDuplicate == 1)
                    {
                        var countRecipe = CheckDataRecipe(foodproc, value);
                        if (countRecipe == 0)
                        {
                            return Request.CreateResponse<string>(HttpStatusCode.BadRequest, "AP0113");
                        }
                    }

                    if (haigoFrom.zenban_chk == 0 && (haigoTo.radio_kirikae == "1" || kbn_nmacs_kojyo == KBN_NMACS_RELATE))
                    {
                        if (isDuplicateData(foodproc, value))
                        {
                            return Request.CreateResponse<string>(HttpStatusCode.BadRequest, "AP0115");
                        }
                    }
                    InsertDataHeader(foodproc, value, HeaderFrom, DataFirstFrom);
                    var isError = InsertDataDetail(foodproc, value, DetailFrom);
                    if (!isError)
                    {
                        return Request.CreateResponse<string>(HttpStatusCode.BadRequest, "AP0009");
                    }

                    InsertSeizoLine(foodproc, value, DataSeizoLineFrom);
                    UpdateSeihinKonpo(foodproc, value, DataSeihinKonpo);

                    transaction.Commit();
                }
            }

            return Request.CreateResponse<SearchInputChangeResponseNotUse>(HttpStatusCode.OK, null);

        }

        /// <summary>
        /// Duplicate data
        /// </summary>
        /// <param name="value"></param>
        /// <returns></returns>
        public bool isDuplicateData(FOODPROCSEntities foodproc, ParaHaigoSave value)
        {
            var haigoFrom = value.HaigoFrom;
            var haigoTo = value.HaigoTo;

            var queryDuplicate = "SELECT COUNT(cd_haigo) AS countData";
            if (haigoTo.radio_kirikae == Properties.Resources.m_kirikae_hyoji)
            {
                queryDuplicate = queryDuplicate + " FROM ma_haigo_mei_hyoji";
            }
            else
            {
                queryDuplicate = queryDuplicate + " FROM ma_haigo_mei";
            }
            queryDuplicate = queryDuplicate + " WHERE cd_haigo = @cd_haigo_to"
                + " AND no_han >= 2"
                + " AND no_han <> @no_han_to"
                + " AND ((@dt_from <= dt_from AND dt_from <= @dt_to )"
                + " OR (@dt_from <= dt_to AND dt_to <= @dt_to )"
                + " OR (dt_from <= @dt_from AND @dt_to <= dt_to))"
                + " AND flg_sakujyo = 0";
            if (haigoTo.radio_kirikae == Properties.Resources.m_kirikae_foodprocs)
            {
                queryDuplicate = queryDuplicate + " AND qty_kihon = qty_haigo_h";
            }
            var parameters = new object[]
                {
                    new SqlParameter("@cd_haigo_to", SqlDbType.VarChar, 13) { Value = (object)haigoTo.cd_haigo_to ?? DBNull.Value },
                    new SqlParameter("@no_han_to", SqlDbType.Int) { Value = (object)haigoTo.no_han_to ?? DBNull.Value },
                    new SqlParameter("@dt_from", SqlDbType.DateTime) { Value = (object)haigoTo.dt_from ?? DBNull.Value },
                    new SqlParameter("@dt_to", SqlDbType.DateTime) { Value = (object)haigoTo.dt_to ?? DBNull.Value },
                };

            int CountData = foodproc.Database.SqlQuery<int>(queryDuplicate, parameters).FirstOrDefault();
            if (CountData > 0)
            {
                return true;
            }

            return false;
        }

        /// <summary>
        /// GetDataHeaderFrom
        /// </summary>
        /// <param name="value"></param>
        /// <returns></returns>
        public DataBeforeUpdate DataBeforeUpdateFrom(FOODPROCSEntities foodproc, ParaHaigoSave value) {
            var haigoFrom = value.HaigoFrom;
            var haigoTo = value.HaigoTo;
            DataBeforeUpdate results = new DataBeforeUpdate();

            var queryUpdateHaigoTo =  " SELECT qty_haigo_kei"
                                            + " , biko"
                                            + " , no_seiho"
                                            + " , kbn_shiagari"
                                            + " , nm_seiho";
            if (haigoFrom.m_kirikae == Properties.Resources.m_kirikae_hyoji)
            {
                queryUpdateHaigoTo = queryUpdateHaigoTo + " FROM ma_haigo_mei_hyoji";
            }
            else
            {
                queryUpdateHaigoTo = queryUpdateHaigoTo + " FROM ma_haigo_mei";
            }
            queryUpdateHaigoTo = queryUpdateHaigoTo + " WHERE cd_haigo = @cd_haigo_from"
                                                    + " AND no_han = @no_han_from"
                                                    + " AND flg_sakujyo = 0";
            if (haigoFrom.m_kirikae == Properties.Resources.m_kirikae_foodprocs)
            {
                queryUpdateHaigoTo = queryUpdateHaigoTo + " AND qty_kihon = qty_haigo_h";
            }

            var parametersGetData = new object[]
                {
                    new SqlParameter("@cd_haigo_from", SqlDbType.VarChar, 13) { Value = (object)haigoFrom.cd_haigo_from ?? DBNull.Value },
                    new SqlParameter("@no_han_from", SqlDbType.Int) { Value = (object)haigoFrom.no_han_from ?? DBNull.Value },
                };

            results = foodproc.Database.SqlQuery<DataBeforeUpdate>(queryUpdateHaigoTo, parametersGetData).FirstOrDefault();

            return results;

        }

        /// <summary>
        /// GetDataHeaderFrom
        /// </summary>
        /// <param name="value"></param>
        /// <returns></returns>
        public List<HeaderData_From> HeaderDataFrom(FOODPROCSEntities foodproc, ParaHaigoSave value)
        {
            var haigoFrom = value.HaigoFrom;
            var haigoTo = value.HaigoTo;

            string userName = decimal.Parse(UserInfo.GetUserNameFromIdentity(this.User.Identity)).ToString();
            List<HeaderData_From> results = new List<HeaderData_From>();
            //Checked 全版対象, not checked 全版対象 and exists Haigo TO
            if (haigoFrom.zenban_chk == 1 || (haigoFrom.zenban_chk == 0 && haigoTo.isDuplicate == 0))
            {
                var querySelect = " SELECT @cd_haigo_to AS cd_haigo"
                        + " ,m.nm_haigo"
                        + " ,m.nm_haigo_r"
                        + " ,m.kbn_hin"
                        + " ,m.cd_bunrui"
                        + " ,m.budomari"
                        + " ,m.qty_kihon"
                        + " ,m.ritsu_kihon"
                        + " ,m.cd_setsubi"
                        + " ,m.flg_gasan"
                        + " ,m.qty_max"
                        + " ,CASE WHEN (@zenban_chk = 1) THEN m.no_han ELSE @no_han_to END AS no_han"
                        + " ,m.qty_haigo_h"
                        + " , CASE WHEN m.kbn_shiagari = 1 THEN ROUND(m.qty_haigo_kei*@ritsu_kihon_to, 6) ELSE 0 END AS qty_haigo_kei"
                        + " ,m.biko"
                        + " ,CASE WHEN @other_kojyo = 1 THEN '' ELSE m.no_seiho END AS no_seiho"
                        + " ,NULL AS cd_tanto_seizo"
                        + " ,NULL AS dt_seizo"
                        + " ,@userLogin AS cd_tanto_hinkan"
                        + " ,@dateSystem AS dt_hinkan"
                        + " ,CASE WHEN (@zenban_chk = 1) THEN m.dt_from ELSE @dt_from END AS dt_from"
                        + " ,CASE WHEN (@zenban_chk = 1) THEN m.dt_to ELSE @dt_to END AS dt_to"
                        + " ,m.kbn_vw"
                        + " ,CASE WHEN (@zenban_chk = 1 AND @isDuplicate = 0) THEN m1.hijyu ELSE m.hijyu  END AS hijyu  "
                        + " ,m.flg_shorihin"
                        + " ,CAST(0 AS BIT) AS flg_hinkan"
                        + " ,CAST(0 AS BIT) AS flg_seizo"
                        + " ,CAST(0 AS BIT) AS flg_sakujyo"
                        + " ,CAST(0 AS BIT) AS flg_mishiyo"
                        + " ,@dateSystem AS dt_toroku"
                        + " ,@dateSystem AS dt_henko"
                        + " ,@userLogin AS cd_koshin"
                        + " ,m.kbn_shiagari"
                        + " ,m.nm_haigo_rm"
                        + " ,NULL AS cd_haigo_seiho"
                        + " ,CAST(0 AS BIT) AS flg_seiho_base"
                        + " ,CASE WHEN (@other_kojyo = 1) THEN NULL ELSE m.nm_seiho END AS nm_seiho";


                if (haigoTo.radio_kirikae == Properties.Resources.m_kirikae_foodprocs)
                {
                    querySelect = querySelect + " , 1 AS  kbn_koshin";
                }
                else
                {
                    querySelect = querySelect + " , NULL AS  kbn_koshin";
                }

                var queryHeader = "";
                if (haigoFrom.m_kirikae == Properties.Resources.m_kirikae_hyoji)
                {
                    queryHeader = queryHeader + " FROM  ma_haigo_mei_hyoji m"
                                                + " INNER JOIN ma_haigo_mei_hyoji m1";
                }
                else
                {
                    queryHeader = queryHeader + " FROM  ma_haigo_mei m"
                                                    + " INNER JOIN ma_haigo_mei m1";
                }
                queryHeader = queryHeader + " ON  m.cd_haigo = m1.cd_haigo"
                                + " AND m1.no_han = 1"
                                + " AND m1.flg_sakujyo = 0";
                if (haigoFrom.m_kirikae == Properties.Resources.m_kirikae_foodprocs)
                {
                    queryHeader = queryHeader + " AND m1.qty_kihon = m1.qty_haigo_h";
                }
                queryHeader = queryHeader + " WHERE m.cd_haigo = @cd_haigo_from"
                                            + " AND m.flg_sakujyo = 0";
                if (haigoFrom.zenban_chk == 0 && haigoTo.isDuplicate == 0)
                {
                    queryHeader = queryHeader + " AND m.no_han = @no_han_from";
                }
                if (haigoFrom.m_kirikae == Properties.Resources.m_kirikae_foodprocs)
                {
                    queryHeader = queryHeader + " AND m.qty_kihon = m.qty_haigo_h";
                }
                queryHeader = queryHeader + " ORDER BY m.no_han";

                //Excute queryHeader
                //Insert data
                var queryExcute = " DECLARE @dateSystem DATETIME = GETDATE();";

                queryExcute = queryExcute
                    + querySelect       //SELECT data 
                    + queryHeader;       //FROM table;

                int other_kojyo = 0;
                if (haigoFrom.cd_kojyo_from != haigoTo.cd_kojyo_to)
                {
                    other_kojyo = 1;
                }
                var parameters = new object[]
                    {
                        new SqlParameter("@cd_haigo_to", SqlDbType.VarChar, 13) { Value = (object)haigoTo.cd_haigo_to ?? DBNull.Value },
                        new SqlParameter("@zenban_chk", SqlDbType.Int) { Value = (object)haigoFrom.zenban_chk ?? DBNull.Value },
                        new SqlParameter("@ritsu_kihon_to", SqlDbType.VarChar, 60) { Value = (object)haigoTo.ritsu_kihon ?? DBNull.Value },
                        new SqlParameter("@other_kojyo", SqlDbType.Int) { Value = (object)other_kojyo ?? DBNull.Value },
                        new SqlParameter("@userLogin", SqlDbType.VarChar, 10) { Value = (object)userName ?? DBNull.Value },
                        new SqlParameter("@cd_haigo_from", SqlDbType.VarChar, 13) { Value = (object)haigoFrom.cd_haigo_from ?? DBNull.Value },
                        new SqlParameter("@no_han_from", SqlDbType.Int) { Value = (object)haigoFrom.no_han_from ?? DBNull.Value },
                        new SqlParameter("@no_han_to", SqlDbType.Int) { Value = (object)haigoTo.no_han_to ?? DBNull.Value },
                        new SqlParameter("@dt_from", SqlDbType.DateTime) { Value = (object)haigoTo.dt_from ?? DBNull.Value },
                        new SqlParameter("@dt_to", SqlDbType.DateTime) { Value = (object)haigoTo.dt_to ?? DBNull.Value },
                        new SqlParameter("@isDuplicate", SqlDbType.Int) { Value = (object)haigoTo.isDuplicate ?? DBNull.Value }
                    };
                results = foodproc.Database.SqlQuery<HeaderData_From>(queryExcute, parameters).ToList();
            }
            else
            {
                var querySelectNoHanFrist = "SELECT cd_haigo "
                                            + ", nm_haigo "
                                            + ", nm_haigo_r "
                                            + ", kbn_hin "
                                            + ", cd_bunrui "
                                            + ", budomari "
                                            + ", qty_kihon "
                                            + ", ritsu_kihon "
                                            + ", cd_setsubi "
                                            + ", flg_gasan "
                                            + ", qty_max "
                                            + ", @no_han_to AS no_han "
                                            + ", qty_haigo_h "
                                            + " , CASE WHEN kbn_shiagari = 1 THEN ROUND(qty_haigo_kei*@ritsu_kihon_to, 6) ELSE 0 END AS qty_haigo_kei"
                                            + ", biko "
                                             + " ,CASE WHEN @other_kojyo = 1 THEN '' ELSE no_seiho END AS no_seiho"
                                            + ", cd_tanto_seizo "
                                            + ", dt_seizo "
                                            + ", cd_tanto_hinkan "
                                            + ", dt_hinkan "
                                            + " ,CASE WHEN (@zenban_chk = 1) THEN dt_from ELSE @dt_from END AS dt_from"
                                            + " ,CASE WHEN (@zenban_chk = 1) THEN dt_to ELSE @dt_to END AS dt_to"
                                            + ", kbn_vw "
                                            + " , hijyu"
                                            + ", flg_shorihin "
                                            + ", flg_hinkan "
                                            + ", flg_seizo "
                                            + ", flg_sakujyo "
                                            + ", flg_mishiyo "
                                            + ", dt_toroku "
                                            + ", dt_henko "
                                            + ", cd_koshin "
                                            + ", kbn_shiagari "
                                            + ", nm_haigo_rm "
                                            + ", cd_haigo_seiho "
                                            + " ,CAST(0 AS BIT) AS flg_seiho_base"
                                            + " ,CASE WHEN (@other_kojyo = 1) THEN NULL ELSE nm_seiho END AS nm_seiho";
                var queryDuplicate = "";
                if (haigoTo.radio_kirikae == Properties.Resources.m_kirikae_hyoji)
                {
                    queryDuplicate = queryDuplicate + " FROM  ma_haigo_mei_hyoji";
                }
                else
                {
                    queryDuplicate = queryDuplicate + " FROM  ma_haigo_mei";
                }
                queryDuplicate = queryDuplicate + " WHERE cd_haigo = @cd_haigo_from"
                                                + " AND no_han = 1"
                                                + " AND flg_sakujyo = 0";
                if (haigoTo.radio_kirikae == Properties.Resources.m_kirikae_foodprocs)
                {
                    queryDuplicate = queryDuplicate + " AND qty_kihon = qty_haigo_h";
                }

                //Excute queryDuplicate
                var queryExcute = querySelectNoHanFrist     //SELECT data 
                    + queryDuplicate;              //FROM table;

                int other_kojyo = 0;
                if (haigoFrom.cd_kojyo_from != haigoTo.cd_kojyo_to)
                {
                    other_kojyo = 1;
                }

                var parameters = new object[]
                    {
                        new SqlParameter("@cd_haigo_from", SqlDbType.VarChar, 13) { Value = (object)haigoTo.cd_haigo_to ?? DBNull.Value },
                        new SqlParameter("@no_han_to", SqlDbType.Int) { Value = (object)haigoTo.no_han_to ?? DBNull.Value },
                        new SqlParameter("@zenban_chk", SqlDbType.Int) { Value = (object)haigoFrom.zenban_chk ?? DBNull.Value },
                        new SqlParameter("@dt_from", SqlDbType.DateTime) { Value = (object)haigoTo.dt_from ?? DBNull.Value },
                        new SqlParameter("@dt_to", SqlDbType.DateTime) { Value = (object)haigoTo.dt_to ?? DBNull.Value },
                        new SqlParameter("@ritsu_kihon_to", SqlDbType.VarChar, 60) { Value = (object)haigoTo.ritsu_kihon ?? DBNull.Value },
                        new SqlParameter("@other_kojyo", SqlDbType.Int) { Value = (object)other_kojyo ?? DBNull.Value },
                        new SqlParameter("@isDuplicate", SqlDbType.Int) { Value = (object)haigoTo.isDuplicate ?? DBNull.Value }
                    };
                results = foodproc.Database.SqlQuery<HeaderData_From>(queryExcute, parameters).ToList();
            }
            return results;
        }


        /// <summary>
        /// GetDataHeader
        /// </summary>
        /// <param name="value"></param>
        /// <returns></returns>
        public void InsertDataHeader(FOODPROCSEntities foodproc, ParaHaigoSave value, List<HeaderData_From> dataHeaderFrom, DataBeforeUpdate dataFirstFrom)
        {
            var haigoFrom = value.HaigoFrom;
            var haigoTo = value.HaigoTo;
            byte? nmac_from = GetKubunNmacKojyo(haigoFrom.cd_kaisha_from, haigoFrom.cd_kojyo_from);
            byte? nmac_to = GetKubunNmacKojyo(haigoFrom.cd_kaisha_from, haigoTo.cd_kojyo_to);

            string userName = decimal.Parse(UserInfo.GetUserNameFromIdentity(this.User.Identity)).ToString();

            foreach (var item in dataHeaderFrom)
            {
                var queryExcute = "";
                ma_haigo_mei_def_col defCol = null;
                bool isInsertDefCol = false;
                int no_han = item.no_han;
                if (haigoTo.radio_kirikae == Properties.Resources.m_kirikae_hyoji)
                {
                    queryExcute = queryExcute + queryInsertHaigoMeiHyoji; //InSERT INTO table
                }
                else
                {
                    queryExcute = queryExcute + queryInsertHaigoMei;//InSERT INTO table   
                    if ((nmac_from == KubunNmacsKojyo && nmac_to == KubunNmacsKojyo && haigoFrom.zenban_chk == 1)
                        || (nmac_to == KubunNmacsKojyo && item.no_han > 1 && haigoFrom.zenban_chk == 0))
                    {
                        // If mode copy all
                        if (haigoFrom.zenban_chk == 1)
                        {
                            defCol = getDefColValue(haigoFrom.cd_kaisha_from, haigoFrom.cd_kojyo_from, haigoFrom.cd_haigo_from, item.no_han);
                        }
                        else
                        {
                            // If mode add no_han
                            if (item.no_han > 1)
                            {
                                defCol = getDefColValue(haigoFrom.cd_kaisha_from, haigoTo.cd_kojyo_to, haigoTo.cd_haigo_to, 1);
                            }
                        }
                        if (defCol != null)
                        {
                            queryExcute = String.Format(queryExcute, @"
                                                    ,flg_tenkai
                                                    ,qty_haigo_auto
                                                    ,su_kigen_min
                                                    ,su_kigen_max
                                                    ,flg_mishiyo_seizo
                                                ");
                            isInsertDefCol = true;
                        }
                        else
                        {
                            queryExcute = String.Format(queryExcute, "");
                        }
                    }
                    else
                    {
                        queryExcute = String.Format(queryExcute, "");
                    }
                }

                if (defCol == null) 
                {
                    defCol = new ma_haigo_mei_def_col();
                }

                queryExcute = queryExcute + " VALUES("
                    + " @cd_haigo"          //cd_haigo
                    + " ,@nm_haigo"         //nm_haigo
                    + " ,@nm_haigo_r"       //nm_haigo_r
                    + " ,@kbn_hin"          //kbn_hin
                    + " ,@cd_bunrui"        //cd_bunrui
                    + " ,@budomari"         //budomari
                    + " ,@qty_kihon"        //qty_kihon
                    + " ,@ritsu_kihon"      //ritsu_kihon
                    + " ,@cd_setsubi"       //cd_setsubi
                    + " ,@flg_gasan"        //flg_gasan
                    + " ,@qty_max"          //qty_max
                    + " ,@no_han"           //no_han 
                    + " ,@qty_haigo_h"      //qty_haigo_h
                    + " ,@qty_haigo_kei"    //qty_haigo_kei
                    + " ,@biko"             //biko
                    + " ,@no_seiho"         //no_seiho
                    + " ,@cd_tanto_seizo"   //cd_tanto_seizo
                    + " ,@dt_seizo"         //dt_seizo
                    + " ,@cd_tanto_hinkan"  //cd_tanto_hinkan
                    + " ,@dt_hinkan"        //dt_hinkan
                    + " ,@dt_from"          //dt_from
                    + " ,@dt_to"            //dt_to
                    + " ,@kbn_vw"           //kbn_vw
                    + " ,@hijyu"            //hijyu
                    + " ,@flg_shorihin"     //flg_shorihin
                    + " ,@flg_hinkan"       //flg_hinkan
                    + " ,@flg_seizo"        //flg_seizo
                    + " ,@flg_sakujyo"      //flg_sakujyo
                    + " ,@flg_mishiyo"      //flg_mishiyo
                    + " ,@dt_toroku"        //dt_toroku
                    + " ,@dt_henko"         //dt_henko
                    + " ,@cd_koshin"        //cd_koshin
                    + " ,@kbn_shiagari"     //kbn_shiagari
                    + " ,@nm_haigo_rm"      //nm_haigo_rm
                    + " ,@cd_haigo_seiho"   //cd_haigo_seiho
                    + " ,@flg_seiho_base"   //flg_seiho_base
                    + " ,@nm_seiho";        //nm_seiho
                if (haigoTo.radio_kirikae == Properties.Resources.m_kirikae_foodprocs)
                {
                    queryExcute = queryExcute
                    + " , 1";//[kbn_koshin]
                    //+ " , NULL"//[kin_keihi]
                    if (isInsertDefCol)
                    {
                        queryExcute = queryExcute + @"
                            ,@flg_tenkai
                            ,@qty_haigo_auto
                            ,@su_kigen_min
                            ,@su_kigen_max
                            ,@flg_mishiyo_seizo
                        ";
                    }
                    //+ " , NULL"//[qty_kowake]
                    //+ " , NULL"//[su_kowake]
                    //+ " , NULL"//[kikan_shomi]
                    //+ " , NULL"//[qty_haigo_genka]
                    //+ " , NULL"//[no_han_genka]
                    //+ " , NULL"//[flg_unten]
                    //+ " , NULL"//[cd_tanto_unten]
                    //+ " , NULL";//[dt_unten]
                }

                queryExcute = queryExcute + " );";
                var parametersInsert = new object[]
                {
                    new SqlParameter("@cd_haigo", SqlDbType.VarChar, 13) { Value = (object)haigoTo.cd_haigo_to ?? DBNull.Value },
                    new SqlParameter("@nm_haigo", SqlDbType.VarChar, 60) { Value = (object)item.nm_haigo ?? DBNull.Value },
                    new SqlParameter("@nm_haigo_r", SqlDbType.VarChar, 20) { Value = (object)item.nm_haigo_r ?? DBNull.Value },
                    new SqlParameter("@kbn_hin", SqlDbType.VarChar, 1) { Value = (object)item.kbn_hin ?? DBNull.Value },
                    new SqlParameter("@cd_bunrui", SqlDbType.VarChar, 2) { Value = (object)item.cd_bunrui ?? DBNull.Value },
                    new SqlParameter("@budomari", SqlDbType.Float) { Value = (object)item.budomari ?? DBNull.Value },
                    new SqlParameter("@qty_kihon", SqlDbType.Int) { Value = (object)haigoTo.qty_kihon ?? DBNull.Value },
                    new SqlParameter("@ritsu_kihon", SqlDbType.Float) { Value = (object)item.ritsu_kihon ?? DBNull.Value },
                    new SqlParameter("@cd_setsubi", SqlDbType.VarChar, 2) { Value = (object)item.cd_setsubi ?? DBNull.Value },
                    new SqlParameter("@flg_gasan", SqlDbType.Bit) { Value = (object)item.flg_gasan ?? DBNull.Value },
                    new SqlParameter("@qty_max", SqlDbType.Float) { Value = (object)item.qty_max ?? DBNull.Value },
                    new SqlParameter("@no_han", SqlDbType.Int) { Value = (object)item.no_han ?? DBNull.Value },
                    new SqlParameter("@qty_haigo_h", SqlDbType.Int) { Value = (object)haigoTo.qty_kihon ?? DBNull.Value },
                    new SqlParameter("@qty_haigo_kei", SqlDbType.Float) { Value = (object)item.qty_haigo_kei ?? DBNull.Value },
                    new SqlParameter("@biko", SqlDbType.VarChar, 300) { Value = (object)item.biko ?? DBNull.Value },
                    new SqlParameter("@no_seiho", SqlDbType.VarChar, 20) { Value = (object)item.no_seiho ?? DBNull.Value },
                    new SqlParameter("@cd_tanto_seizo", SqlDbType.VarChar, 10) { Value = (object)item.cd_tanto_seizo ?? DBNull.Value },
                    new SqlParameter("@dt_seizo", SqlDbType.DateTime) { Value = (object)item.dt_seizo ?? DBNull.Value },
                    new SqlParameter("@cd_tanto_hinkan", SqlDbType.VarChar, 10) { Value = (object)item.cd_tanto_hinkan ?? DBNull.Value },
                    new SqlParameter("@dt_hinkan", SqlDbType.DateTime) { Value = (object)item.dt_hinkan ?? DBNull.Value },
                    new SqlParameter("@dt_from", SqlDbType.DateTime) { Value = (object)item.dt_from ?? DBNull.Value },
                    new SqlParameter("@dt_to", SqlDbType.DateTime) { Value = (object)item.dt_to ?? DBNull.Value },
                    new SqlParameter("@kbn_vw", SqlDbType.VarChar, 2) { Value = (object)item.kbn_vw ?? DBNull.Value },
                    new SqlParameter("@hijyu", SqlDbType.Float) { Value = (object)item.hijyu ?? DBNull.Value },
                    new SqlParameter("@flg_shorihin", SqlDbType.Bit) { Value = (object)item.flg_shorihin ?? DBNull.Value },
                    new SqlParameter("@flg_hinkan", SqlDbType.Bit) { Value = (object)item.flg_hinkan ?? DBNull.Value },
                    new SqlParameter("@flg_seizo", SqlDbType.Bit) { Value = (object)item.flg_seizo ?? DBNull.Value },
                    new SqlParameter("@flg_sakujyo", SqlDbType.Bit) { Value = (object)item.flg_sakujyo ?? DBNull.Value },
                    new SqlParameter("@flg_mishiyo", SqlDbType.Bit) { Value = (object)item.flg_mishiyo ?? DBNull.Value },
                    new SqlParameter("@dt_toroku", SqlDbType.DateTime) { Value = (object)item.dt_toroku ?? DBNull.Value },
                    new SqlParameter("@dt_henko", SqlDbType.DateTime) { Value = (object)item.dt_henko ?? DBNull.Value },
                    new SqlParameter("@cd_koshin", SqlDbType.VarChar, 10) { Value = (object)item.cd_koshin ?? DBNull.Value },
                    new SqlParameter("@kbn_shiagari", SqlDbType.Bit) { Value = (object)item.kbn_shiagari ?? DBNull.Value },
                    new SqlParameter("@nm_haigo_rm", SqlDbType.VarChar, 60) { Value = (object)item.nm_haigo_rm ?? DBNull.Value },
                    new SqlParameter("@cd_haigo_seiho", SqlDbType.VarChar, 13) { Value = (object)item.cd_haigo_seiho ?? DBNull.Value },
                    new SqlParameter("@flg_seiho_base", SqlDbType.Bit) { Value = (object)item.flg_seiho_base ?? DBNull.Value },
                    new SqlParameter("@nm_seiho", SqlDbType.VarChar, 120) { Value = (object)item.nm_seiho ?? DBNull.Value },
                    // Default value
                    new SqlParameter("@flg_tenkai", SqlDbType.Bit) {  Value = (object)defCol.flg_tenkai ?? DBNull.Value },
                    new SqlParameter("@qty_haigo_auto", SqlDbType.Float) {  Value = (object)defCol.qty_haigo_auto ?? DBNull.Value },
                    new SqlParameter("@su_kigen_min", SqlDbType.Int) {  Value = (object)defCol.su_kigen_min ?? DBNull.Value },
                    new SqlParameter("@su_kigen_max", SqlDbType.Int) {  Value = (object)defCol.su_kigen_max ?? DBNull.Value },
                    new SqlParameter("@flg_mishiyo_seizo", SqlDbType.Bit) {  Value = (object)defCol.flg_mishiyo_seizo ?? DBNull.Value }
                };
                foodproc.Database.ExecuteSqlCommand(queryExcute, parametersInsert);
            }

            if (haigoFrom.zenban_chk == 0 && haigoTo.isDuplicate == 1)
            {

                var queryUpdateHaigoTo = "";
                if (haigoTo.radio_kirikae == Properties.Resources.m_kirikae_hyoji)
                {
                    queryUpdateHaigoTo = queryUpdateHaigoTo + " UPDATE ma_haigo_mei_hyoji";
                }
                else
                {
                    queryUpdateHaigoTo = queryUpdateHaigoTo + " UPDATE ma_haigo_mei";
                }
                queryUpdateHaigoTo = queryUpdateHaigoTo + " SET qty_haigo_kei = CASE WHEN @kbn_shiagari = 1 THEN ROUND(CAST(@qty_haigo_kei*@ritsu_kihon_to AS DECIMAL(24,7)), 6) ELSE 0 END"
                + " , biko = @biko"
                + " , no_seiho = CASE WHEN @other_kojyo = 1 THEN '' ELSE @no_seiho END"
                + " , cd_tanto_hinkan = @userLogin"
                + " , dt_hinkan = GETDATE()"
                + " , flg_hinkan = 0"
                + " , flg_seizo = 0"
                + " , flg_sakujyo = 0"
                + " , flg_mishiyo = 0"
                + " , dt_toroku = GETDATE()"
                + " , dt_henko = GETDATE()"
                + " , kbn_shiagari = @kbn_shiagari"
                + " , nm_seiho = CASE WHEN @other_kojyo = 1 THEN NULL ELSE @nm_seiho END";
                if (haigoTo.radio_kirikae == Properties.Resources.m_kirikae_foodprocs)
                {
                    queryUpdateHaigoTo = queryUpdateHaigoTo + " , kbn_koshin = 1";
                }

                queryUpdateHaigoTo = queryUpdateHaigoTo + " WHERE cd_haigo = @cd_haigo_to"
                + " AND no_han = @no_han_to"
                + " AND flg_sakujyo = 0";
                if (haigoTo.radio_kirikae == Properties.Resources.m_kirikae_foodprocs)
                {
                    queryUpdateHaigoTo = queryUpdateHaigoTo + " AND qty_haigo_h = qty_kihon";
                }
                int other_kojyo = 0;
                if (haigoFrom.cd_kojyo_from != haigoTo.cd_kojyo_to)
                {
                    other_kojyo = 1;
                }

                var parametersUpdate = new object[]
                {
                    new SqlParameter("@cd_haigo_from", SqlDbType.VarChar, 13) { Value = (object)haigoFrom.cd_haigo_from ?? DBNull.Value },
                    new SqlParameter("@no_han_from", SqlDbType.Int) { Value = (object)haigoFrom.no_han_from ?? DBNull.Value },
                    new SqlParameter("@userLogin", SqlDbType.VarChar, 10) { Value = (object)userName ?? DBNull.Value },
                    new SqlParameter("@cd_haigo_to", SqlDbType.VarChar, 13) { Value = (object)haigoTo.cd_haigo_to ?? DBNull.Value },
                    new SqlParameter("@no_han_to", SqlDbType.Int) { Value = (object)haigoTo.no_han_to ?? DBNull.Value },
                    new SqlParameter("@qty_haigo_kei", SqlDbType.Float) { Value = (object)dataFirstFrom.qty_haigo_kei ?? DBNull.Value },
                    new SqlParameter("@biko", SqlDbType.VarChar, 300) { Value = (object)dataFirstFrom.biko ?? DBNull.Value },
                    new SqlParameter("@no_seiho", SqlDbType.VarChar, 20) { Value = (object)dataFirstFrom.no_seiho ?? DBNull.Value },
                    new SqlParameter("@kbn_shiagari", SqlDbType.Bit) { Value = (object)dataFirstFrom.kbn_shiagari ?? DBNull.Value },
                    new SqlParameter("@nm_seiho", SqlDbType.VarChar, 120) { Value = (object)dataFirstFrom.nm_seiho ?? DBNull.Value },
                    new SqlParameter("@other_kojyo", SqlDbType.Int) { Value = (object)other_kojyo ?? DBNull.Value },
                    new SqlParameter("@ritsu_kihon_to", SqlDbType.VarChar, 60) { Value = (object)haigoTo.ritsu_kihon ?? DBNull.Value },
                };

                foodproc.Database.ExecuteSqlCommand(queryUpdateHaigoTo, parametersUpdate);
            }
        }

        /// <summary>
        /// GetDataDetail
        /// </summary>
        /// <param name="value"></param>
        /// <returns></returns>
        public List<DetailData_From> GetDataDetail(FOODPROCSEntities foodproc, ParaHaigoSave value)
        {
            var haigoFrom = value.HaigoFrom;
            var haigoTo = value.HaigoTo;
            string userName = decimal.Parse(UserInfo.GetUserNameFromIdentity(this.User.Identity)).ToString();
            List<DetailData_From> results = new List<DetailData_From>();

            var querySelect = "SELECT"
            + " @cd_haigo_to AS cd_haigo"
            + " , CASE WHEN @zenban_chk = 1 THEN r.no_han ELSE @no_han_to END AS no_han"
            + " , @qty_haigo_h AS qty_haigo_h"
            + " , r.no_kotei"
            + " , r.no_tonyu"
            + " , r.cd_hin"
            + " , r.kbn_hin"
            + " , r.nm_hin"
            + " , r.cd_mark"
            + " , CAST(ROUND(CAST(ISNULL(r.qty*@ritsu_kihon, 0) AS DECIMAL(24,7)), CASE WHEN mk.cd_tani_shiyo = 3 THEN 6 ELSE 3 END) AS FLOAT) AS qty"
            + " , CAST(ROUND(CAST(ISNULL(r.qty_haigo*@ritsu_kihon, 0) AS DECIMAL(24,7)), CASE WHEN mk.cd_tani_shiyo = 3 THEN 6 ELSE 3 END) AS FLOAT) AS qty_haigo"
            + " , r.qty_nisugata"
            + " , r.su_nisugata"
            + " , r.qty_kowake"
            + " , r.su_kowake"
            + " , r.cd_futai"
            + " , r.qty_futai"
            + " , r.hijyu"
            + " , r.budomari"
            + " , 0 AS flg_sakujyo"
            + " , 0 AS flg_mishiyo"
            + " , GETDATE() AS dt_toroku"
            + " , GETDATE() AS dt_henko"
            + " , @userLogin AS cd_koshin"
            + " , ISNULL(r.kbn_jyotai, '') AS kbn_jyotai"
            + " , r.kbn_shitei"
            + " , r.kbn_bunkatsu"

            + " , CASE WHEN h.hijyu IS NULL OR h.hijyu = 0 OR h.hijyu = '' THEN 1 ELSE h.hijyu END AS hijyu_calc"
            + " , CASE WHEN r.kbn_hin = 9 THEN mk.cd_tani_shiyo"
            + " ELSE"
            + " CASE WHEN h.cd_tani_hin = 4 THEN 4"
            + " WHEN h.cd_tani_hin = 11 THEN 11"
            + " ELSE 4 END"
            + " END AS cd_tani"
            + " , CASE WHEN mk.cd_tani_shiyo = 3 THEN 6 ELSE 3 END AS cd_tani_shiyo";

            var queryCondition = "";
            if (haigoFrom.m_kirikae == Properties.Resources.m_kirikae_hyoji)
            {
                queryCondition = queryCondition + " FROM ma_haigo_recipe_hyoji r"
                                        + " LEFT JOIN SS_vw_hin_hyoji h";
            }
            else
            {
                queryCondition = queryCondition + " FROM ma_haigo_recipe r"
                                        + " LEFT JOIN SS_vw_hin h";
            }
            queryCondition = queryCondition + " ON h.cd_hin = r.cd_hin"
                        + " AND h.kbn_hin_toroku = r.kbn_hin"
                        + " LEFT JOIN ma_mark mk"
                        + " ON mk.cd_mark=r.cd_mark";
            if (haigoFrom.m_kirikae == Properties.Resources.m_kirikae_foodprocs)
            {
                queryCondition = queryCondition + " INNER JOIN ma_haigo_mei m"
                                        + " ON  m.cd_haigo = r.cd_haigo"
                                        + " AND m.no_han = r.no_han"
                                        + " AND m.qty_haigo_h = r.qty_haigo_h"
                                        + " AND m.qty_kihon = m.qty_haigo_h"
                                        + " AND m.flg_sakujyo = 0"
                                        + " AND m.flg_mishiyo = 0";
            }

            queryCondition = queryCondition + " WHERE r.cd_haigo = @cd_haigo_from"
                                    + " AND r.flg_sakujyo = 0 "
                                    + " AND r.flg_mishiyo = 0";
            if (haigoFrom.zenban_chk == 0)
            {
                queryCondition = queryCondition + " AND r.no_han = @no_han_from";
            }
            queryCondition = queryCondition + " ;";

            var queryExcute = querySelect + queryCondition;
            var parameters = new object[]
                    {
                        new SqlParameter("@cd_haigo_to", SqlDbType.VarChar, 13) { Value = (object)haigoTo.cd_haigo_to ?? DBNull.Value },
                        new SqlParameter("@no_han_to", SqlDbType.Int) { Value = (object)haigoTo.no_han_to ?? DBNull.Value },
                        new SqlParameter("@qty_haigo_h", SqlDbType.Int) { Value = (object) haigoTo.qty_kihon?? DBNull.Value },
                        new SqlParameter("@ritsu_kihon", SqlDbType.Float) { Value = (object)haigoTo.ritsu_kihon ?? DBNull.Value },
                        new SqlParameter("@userLogin", SqlDbType.VarChar, 10) { Value = (object)userName ?? DBNull.Value },
                        new SqlParameter("@cd_haigo_from", SqlDbType.VarChar, 13) { Value = (object)haigoFrom.cd_haigo_from ?? DBNull.Value },
                        new SqlParameter("@no_han_from", SqlDbType.Int) { Value = (object)haigoFrom.no_han_from ?? DBNull.Value },
                        new SqlParameter("@zenban_chk", SqlDbType.Int) { Value = (object)haigoFrom.zenban_chk ?? DBNull.Value }

                    };

            results = foodproc.Database.SqlQuery<DetailData_From>(queryExcute, parameters).ToList();

            return results;

        }


        /// <summary>
        /// InsertDataDetail
        /// </summary>
        /// <param name="value"></param>
        /// <returns></returns>
        public bool InsertDataDetail(FOODPROCSEntities foodproc, ParaHaigoSave value, List<DetailData_From> DetailDataFrom)
        {
            var haigoFrom = value.HaigoFrom;
            var haigoTo = value.HaigoTo;

            double max_qty_haigo = 99999999.999;
            var isErrorCount = DetailDataFrom.Where(m => m.qty > max_qty_haigo || m.qty_haigo > max_qty_haigo).FirstOrDefault();
            if (isErrorCount != null)
            {
                return false;
            }
            else
            {

                string userName = decimal.Parse(UserInfo.GetUserNameFromIdentity(this.User.Identity)).ToString();
                foreach(var item in DetailDataFrom){
                    var queryDo = "";
                    //Insert data
                    if (haigoTo.radio_kirikae == Properties.Resources.m_kirikae_hyoji)
                    {
                        queryDo = queryDo + queryInsertHaigoRecipeHyoji;
                    }
                    else
                    {
                        queryDo = queryDo + queryInsertHaigoRecipe;
                    }
                    queryDo = queryDo + " VALUES( @cd_haigo"
                    + " , @no_han"
                    + " , @qty_haigo_h"
                    + " , @no_kotei"
                    + " , @no_tonyu"
                    + " , @cd_hin"
                    + " , @kbn_hin"
                    + " , @nm_hin"
                    + " , @cd_mark"
                    + " , @qty"
                    + " , @qty_haigo"
                    + " , @qty_nisugata"
                    + " , @su_nisugata"
                    + " , @qty_kowake"
                    + " , @su_kowake"
                    + " , @cd_futai"
                    + " , @qty_futai"
                    + " , @hijyu"
                    + " , @budomari"
                    + " , @flg_sakujyo"
                    + " , @flg_mishiyo"
                    + " , @dt_toroku"
                    + " , @dt_henko"
                    + " , @cd_koshin"
                    + " , @kbn_jyotai"
                    + " , @kbn_shitei"
                    + " , @kbn_bunkatsu";
                    //Case other
                    if (haigoTo.radio_kirikae == Properties.Resources.m_kirikae_foodprocs)
                    {
                        queryDo = queryDo; //+ " , NULL" //flg_kowake_nochk
                        //+ " , NULL" //cd_shokuba
                        //+ " , NULL"     // keisu
                        //+ " , NULL"     // flg_hijyu
                        //+ " , NULL"    // cd_station
                        //+ " , NULL";    // su_leadtime_kura
                    }
                    queryDo = queryDo + ");";

                    //Insert data
                    var parametersInsert = new object[]
                    {
                        new SqlParameter("@cd_haigo", SqlDbType.VarChar, 13) { Value = (object)item.cd_haigo ?? DBNull.Value },
                        new SqlParameter("@no_han", SqlDbType.Int) { Value = (object)item.no_han ?? DBNull.Value },
                        new SqlParameter("@qty_haigo_h", SqlDbType.Int) { Value = (object)item.qty_haigo_h ?? DBNull.Value },
                        new SqlParameter("@no_kotei", SqlDbType.Int) { Value = (object)item.no_kotei ?? DBNull.Value },
                        new SqlParameter("@no_tonyu", SqlDbType.Int) { Value = (object)item.no_tonyu ?? DBNull.Value },
                        new SqlParameter("@cd_hin", SqlDbType.VarChar, 13) { Value = (object)item.cd_hin ?? DBNull.Value },
                        new SqlParameter("@kbn_hin", SqlDbType.VarChar, 1) { Value = (object)item.kbn_hin ?? DBNull.Value },
                        new SqlParameter("@nm_hin", SqlDbType.VarChar, 60) { Value = (object)item.nm_hin ?? DBNull.Value },
                        new SqlParameter("@cd_mark", SqlDbType.VarChar, 2) { Value = (object)item.cd_mark ?? DBNull.Value },
                        new SqlParameter("@qty", SqlDbType.Float) { Value = (object)item.qty ?? DBNull.Value },
                        new SqlParameter("@qty_haigo", SqlDbType.Float) { Value = (object)item.qty_haigo ?? DBNull.Value },
                        new SqlParameter("@qty_nisugata", SqlDbType.Float) { Value = (object)item.qty_nisugata ?? DBNull.Value },
                        new SqlParameter("@su_nisugata", SqlDbType.Int) { Value = (object)item.su_nisugata ?? DBNull.Value },
                        new SqlParameter("@qty_kowake", SqlDbType.Float) { Value = (object)item.qty_kowake ?? DBNull.Value },
                        new SqlParameter("@su_kowake", SqlDbType.Int) { Value = (object)item.su_kowake ?? DBNull.Value },
                        new SqlParameter("@cd_futai", SqlDbType.VarChar, 2) { Value = (object)item.cd_futai ?? DBNull.Value },
                        new SqlParameter("@qty_futai", SqlDbType.Float) { Value = (object)item.qty_futai ?? DBNull.Value },
                        new SqlParameter("@hijyu", SqlDbType.Float) { Value = (object)item.hijyu ?? DBNull.Value },
                        new SqlParameter("@budomari", SqlDbType.Float) { Value = (object)item.budomari ?? DBNull.Value },
                        new SqlParameter("@flg_sakujyo", SqlDbType.Bit) { Value = (object)item.flg_sakujyo ?? DBNull.Value },
                        new SqlParameter("@flg_mishiyo", SqlDbType.Bit) { Value = (object)item.flg_mishiyo ?? DBNull.Value },
                        new SqlParameter("@dt_toroku", SqlDbType.DateTime) { Value = (object)item.dt_toroku ?? DBNull.Value },
                        new SqlParameter("@dt_henko", SqlDbType.DateTime) { Value = (object)item.dt_henko ?? DBNull.Value },
                        new SqlParameter("@cd_koshin", SqlDbType.VarChar, 10) { Value = (object)item.cd_koshin ?? DBNull.Value },
                        new SqlParameter("@kbn_jyotai", SqlDbType.VarChar, 1) { Value = (object)item.kbn_jyotai ?? DBNull.Value },
                        new SqlParameter("@kbn_shitei", SqlDbType.VarChar, 1) { Value = (object)item.kbn_shitei ?? DBNull.Value },
                        new SqlParameter("@kbn_bunkatsu", SqlDbType.TinyInt) { Value = (object)item.kbn_bunkatsu ?? DBNull.Value }
                    };

                    foodproc.Database.ExecuteSqlCommand(queryDo, parametersInsert);
                }

                var queryUpdate = " SELECT kbn_shiagari"
                + " , kbn_vw"
                + " , no_han AS no_han_to";

                if (haigoTo.radio_kirikae == Properties.Resources.m_kirikae_hyoji)
                {
                    queryUpdate = queryUpdate + " FROM  ma_haigo_mei_hyoji";
                }
                else
                {
                    queryUpdate = queryUpdate + " FROM  ma_haigo_mei";
                }

                queryUpdate = queryUpdate + " WHERE cd_haigo = @cd_haigo_to";
                if (haigoFrom.zenban_chk == 0)
                {
                    queryUpdate = queryUpdate + " AND no_han = @no_han_to";
                }
                queryUpdate = queryUpdate + " AND flg_sakujyo = 0";

                if (haigoTo.radio_kirikae == Properties.Resources.m_kirikae_foodprocs)
                {
                    queryUpdate = queryUpdate + " AND qty_haigo_h = qty_kihon";
                }
                var parametersHaigoKei = new object[]
                    {
                        new SqlParameter("@cd_haigo_to", SqlDbType.VarChar, 13) { Value = (object)haigoTo.cd_haigo_to ?? DBNull.Value },
                        new SqlParameter("@no_han_to", SqlDbType.Int) { Value = (object)haigoTo.no_han_to ?? DBNull.Value },

                    };

                List<UpdatehaigoKei> dataUpdateHaigoKei = foodproc.Database.SqlQuery<UpdatehaigoKei>(queryUpdate, parametersHaigoKei).ToList();
                foreach(var item in dataUpdateHaigoKei){
                    var queryHaigoKei = "";
                    if(item.kbn_shiagari != true){
                        queryHaigoKei = queryHaigoKei + " DECLARE @qty_haigo_kei FLOAT = 0;"
                        + " SELECT"
                        + " @qty_haigo_kei = SUM("
                        + " CASE "
                        + " WHEN @kbn_vw = '04' THEN"
                        + " CASE WHEN (CASE WHEN h.cd_tani_hin = '04' THEN '04' WHEN h.cd_tani_hin = '11' THEN '11' ELSE '04' END) = '04' THEN qty_haigo"
                        + " WHEN (CASE WHEN h.cd_tani_hin = '04' THEN '04' WHEN h.cd_tani_hin = '11' THEN '11' ELSE '04' END) = '11' THEN r.qty_haigo*(CASE WHEN h.hijyu IS NULL OR h.hijyu = 0 OR h.hijyu = '' THEN 1 ELSE h.hijyu END)"
                        + " ELSE 0"
                        + " END"
                        + " WHEN @kbn_vw = '11' THEN"
                        + " CASE WHEN (CASE WHEN h.cd_tani_hin = '04' THEN '04' WHEN h.cd_tani_hin = '11' THEN '11' ELSE '04' END) = '04' THEN r.qty_haigo/(CASE WHEN h.hijyu IS NULL OR h.hijyu = 0 OR h.hijyu = '' THEN 1 ELSE h.hijyu END)"
                        + " WHEN (CASE WHEN h.cd_tani_hin = '04' THEN '04' WHEN h.cd_tani_hin = '11' THEN '11' ELSE '04' END) = '11' THEN qty_haigo"
                        + " ELSE 0"
                        + " END"
                        + " ELSE 0"
                        + " END)";
                        if (haigoTo.radio_kirikae == Properties.Resources.m_kirikae_hyoji)
                        {
                            queryHaigoKei = queryHaigoKei + " FROM ma_haigo_recipe_hyoji r"
                                                            + " LEFT JOIN SS_vw_hin_hyoji h";
                        }
                        else
                        {
                            queryHaigoKei = queryHaigoKei + " FROM ma_haigo_recipe r"
                                                        + " LEFT JOIN SS_vw_hin h";
                        }
                        queryHaigoKei = queryHaigoKei + " ON h.cd_hin = r.cd_hin"
                                                    + " AND h.kbn_hin_toroku = r.kbn_hin";

                        if (haigoTo.radio_kirikae == Properties.Resources.m_kirikae_foodprocs)
                        {
                            queryHaigoKei = queryHaigoKei + " INNER JOIN ma_haigo_mei m"
                                                    + " ON  m.cd_haigo = r.cd_haigo"
                                                    + " AND m.no_han = r.no_han"
                                                    + " AND m.qty_haigo_h = r.qty_haigo_h"
                                                    + " AND m.qty_kihon = m.qty_haigo_h"
                                                    + " AND m.flg_sakujyo = 0"
                                                    + " AND m.flg_mishiyo = 0";
                        }

                        queryHaigoKei = queryHaigoKei + " WHERE r.cd_haigo = @cd_haigo_to"
                            + " AND r.flg_sakujyo = 0"
                            + " AND r.no_han = @no_han_to";
                        //if (haigoTo.radio_kirikae == Properties.Resources.m_kirikae_foodprocs)
                        //{
                            //queryHaigoKei = queryHaigoKei + " AND r.qty_haigo_h = r.qty_kihon;";
                        //}

                        if (haigoTo.radio_kirikae == Properties.Resources.m_kirikae_foodprocs)
                        {
                            queryHaigoKei = queryHaigoKei + " UPDATE ma_haigo_mei";
                        }
                        else
                        {
                            queryHaigoKei = queryHaigoKei + " UPDATE ma_haigo_mei_hyoji";
                        }
                        queryHaigoKei = queryHaigoKei + " SET qty_haigo_kei = ROUND(ISNULL(@qty_haigo_kei, 0), 6)"
                        //WHERE
                        + " WHERE cd_haigo = @cd_haigo_to"
                        + " AND no_han = @no_han_to"
                        + " AND flg_sakujyo = 0";
                        if (haigoTo.radio_kirikae == Properties.Resources.m_kirikae_foodprocs)
                        {
                            queryHaigoKei = queryHaigoKei + " AND qty_haigo_h = qty_kihon";
                        }

                        //Excute queryDetail
                        var parametersHaigoKeiHeader = new object[]
                            {
                                new SqlParameter("@cd_haigo_to", SqlDbType.VarChar, 13) { Value = (object)haigoTo.cd_haigo_to ?? DBNull.Value },
                                new SqlParameter("@no_han_to", SqlDbType.Int) { Value = (object)item.no_han_to ?? DBNull.Value },
                                new SqlParameter("@kbn_vw", SqlDbType.VarChar, 2) { Value = (object)item.kbn_vw ?? DBNull.Value }

                            };

                        foodproc.Database.ExecuteSqlCommand(queryHaigoKei, parametersHaigoKeiHeader);
                    }
                }
                return true;
            }

        }

        /// <summary>
        /// Get data ma_seizo_line
        /// </summary>
        /// <param name="value"></param>
        /// <returns></returns>
        public List<DataSeizoLineFrom> GetDataSeizoLineFrom(FOODPROCSEntities foodproc, ParaHaigoSave value, int isOtherLineCode, string cd_line_to)
        { 
            var haigoFrom = value.HaigoFrom;
            var haigoTo = value.HaigoTo;
            string userName = decimal.Parse(UserInfo.GetUserNameFromIdentity(this.User.Identity)).ToString();
            List<DataSeizoLineFrom> results = new List<DataSeizoLineFrom>();
            if (haigoTo.isDuplicate == 0)
            {
                var queryData = " SELECT "
                + " @cd_haigo_to AS cd_haigo"
                + " , no_yusen "
                + " , CASE WHEN @isOtherLineCode = 0 THEN cd_line"
                + "  ELSE @cd_line_to END AS cd_line"
                + " , flg_sakujyo "
                + " , flg_mishiyo "
                + " , GETDATE() AS dt_toroku "
                + " , GETDATE() AS dt_henko "
                + " , @userLogin AS cd_koshin";

                if (haigoFrom.m_kirikae == Properties.Resources.m_kirikae_foodprocs)
                {
                    queryData = queryData + " FROM ma_seizo_line";
                }
                else
                {
                    queryData = queryData + " , NULL AS qty_noryoku "
                                            + " FROM ma_seizo_line_hyoji";
                }

                queryData = queryData + " WHERE cd_haigo = @cd_haigo_from"
                + " AND flg_mishiyo = 0"
                + " AND flg_sakujyo = 0"
                + " ORDER BY no_yusen";

                var parameters = new object[]
                {
                    new SqlParameter("@cd_haigo_to", SqlDbType.VarChar, 13) { Value = (object)haigoTo.cd_haigo_to ?? DBNull.Value },
                    new SqlParameter("@isOtherLineCode", SqlDbType.Int) { Value = (object)isOtherLineCode ?? DBNull.Value },
                    new SqlParameter("@cd_line_to", SqlDbType.VarChar, 13) { Value = (object)cd_line_to ?? DBNull.Value },
                    new SqlParameter("@userLogin", SqlDbType.VarChar, 10) { Value = (object)userName ?? DBNull.Value },
                    new SqlParameter("@cd_haigo_from", SqlDbType.VarChar, 13) { Value = (object)haigoFrom.cd_haigo_from ?? DBNull.Value }
                };

                results = foodproc.Database.SqlQuery<DataSeizoLineFrom>(queryData, parameters).ToList();

            }
            return results;
        }


        /// <summary>
        /// Insert ma_seizo_line
        /// </summary>
        /// <param name="value"></param>
        /// <returns></returns>
        public void InsertSeizoLine(FOODPROCSEntities foodproc, ParaHaigoSave value, List<DataSeizoLineFrom> DataSeizoLineFrom)
        {
            var haigoFrom = value.HaigoFrom;
            var haigoTo = value.HaigoTo;
            string userName = decimal.Parse(UserInfo.GetUserNameFromIdentity(this.User.Identity)).ToString();

            foreach(var item in DataSeizoLineFrom) {
                //Insert data
                var queryExcute = "";
                if (haigoTo.radio_kirikae == Properties.Resources.m_kirikae_hyoji)
                {
                    queryExcute = queryInsertSeizoLineHyoji;
                }
                else {
                    queryExcute = queryInsertSeizoLine;
                }
                queryExcute = queryExcute + " VALUES( @cd_haigo_to"
                    + " ,@no_yusen"
                    + " ,@cd_line"
                    + " ,@flg_sakujyo"
                    + " ,@flg_mishiyo"
                    + " ,@dt_toroku"
                    + " ,@dt_henko"
                    + " ,@cd_koshin)";

                var parameters = new object[]
                {
                    new SqlParameter("@cd_haigo_to", SqlDbType.VarChar, 13) { Value = (object)item.cd_haigo ?? DBNull.Value },
                    new SqlParameter("@no_yusen", SqlDbType.Int) { Value = (object)item.no_yusen ?? DBNull.Value },
                    new SqlParameter("@cd_line", SqlDbType.VarChar, 3) { Value = (object)item.cd_line ?? DBNull.Value },
                    new SqlParameter("@flg_sakujyo", SqlDbType.Bit) { Value = (object)item.flg_sakujyo ?? DBNull.Value },
                    new SqlParameter("@flg_mishiyo", SqlDbType.Bit) { Value = (object)item.flg_mishiyo ?? DBNull.Value },
                    new SqlParameter("@dt_toroku", SqlDbType.DateTime) { Value = (object)item.dt_toroku ?? DBNull.Value },
                    new SqlParameter("@dt_henko", SqlDbType.DateTime) { Value = (object)item.dt_henko ?? DBNull.Value },
                    new SqlParameter("@cd_koshin", SqlDbType.VarChar, 10) { Value = (object)item.cd_koshin ?? DBNull.Value }
                };

                foodproc.Database.ExecuteSqlCommand(queryExcute, parameters);
            }
        }

        /// <summary>
        /// Get data Seihin update
        /// </summary>
        /// <param name="value"></param>
        /// <returns></returns>
        public List<DataSeihinFrom> GetSeihinKonpo(FOODPROCSEntities foodproc, ParaHaigoSave value)
        {
            var haigoFrom = value.HaigoFrom;
            var haigoTo = value.HaigoTo;
            List<DataSeihinFrom> results = new List<DataSeihinFrom>();
            if (haigoFrom.seihin_chk == 1)
            {
                var querySelect = "";
                if (haigoFrom.m_kirikae == Properties.Resources.m_kirikae_hyoji)
                {
                    querySelect = "SELECT "
                    + " cd_hin"
                    + " , no_yusen"
                    + " , no_yusen_hyoji"
                    + " FROM ma_seihin "
                    + " WHERE cd_haigo_hyoji = @cd_haigo_from"
                    + " AND flg_sakujyo = 0"
                    + " ORDER BY no_yusen";
                }
                else
                {
                    querySelect = "SELECT "
                    + " cd_hin"
                    + " , no_yusen"
                    + " , no_yusen_hyoji"
                    + " FROM ma_seihin "
                    + " WHERE cd_haigo = @cd_haigo_from"
                    + " AND flg_sakujyo = 0"
                    + " ORDER BY no_yusen";
                }



                var parameters = new object[]
                {
                    new SqlParameter("@cd_haigo_from", SqlDbType.VarChar, 13) { Value = (object)haigoFrom.cd_haigo_from ?? DBNull.Value }
                };
                results = foodproc.Database.SqlQuery<DataSeihinFrom>(querySelect, parameters).ToList();
            }

            return results;
        }

        /// <summary>
        /// Insert ma_seizo_line
        /// </summary>
        /// <param name="value"></param>
        /// <returns></returns>
        public void UpdateSeihinKonpo(FOODPROCSEntities foodproc, ParaHaigoSave value, List<DataSeihinFrom> SeihinFrom)
        {
            var haigoFrom = value.HaigoFrom;
            var haigoTo = value.HaigoTo;
            string userName = decimal.Parse(UserInfo.GetUserNameFromIdentity(this.User.Identity)).ToString();

            foreach (var item in SeihinFrom)
            {
                if (haigoFrom.seihin_chk == 1)
                {
                    var queryUpdateKonpo = " UPDATE ma_konpo"
                        + " SET cd_koshin = @userLogin"
                        + " , dt_henko = GETDATE()"
                        + " ,flg_sakujyo = 0"
                        + " WHERE cd_hin = @cd_hin"
                        + " AND flg_sakujyo = 0 ";

                    var queryUpdateSeihin = "";
                    if (haigoTo.radio_kirikae == Properties.Resources.m_kirikae_foodprocs)
                    {
                        queryUpdateSeihin = " UPDATE ma_seihin"
                        + " SET cd_haigo = @cd_haigo_to"
                        + " , no_yusen = @no_yusen"
                        + " , cd_koshin = @userLogin"
                        + " , dt_henko = GETDATE()"
                        + " WHERE cd_hin = @cd_hin"
                        + " AND flg_sakujyo = 0";
                    }
                    else
                    {
                        queryUpdateSeihin = " UPDATE ma_seihin"
                        + " SET cd_haigo_hyoji = @cd_haigo_to"
                        + " , no_yusen_hyoji = @no_yusen_hyoji"
                        + " , cd_koshin = @userLogin"
                        + " , dt_henko = GETDATE()"
                        + " WHERE cd_hin = @cd_hin"
                        + " AND flg_sakujyo = 0";
                    }

                    var queryExcute = queryUpdateKonpo + queryUpdateSeihin;

                    var parameters = new object[]
                {
                    new SqlParameter("@userLogin", SqlDbType.VarChar, 10) { Value = (object)userName ?? DBNull.Value },
                    new SqlParameter("@no_yusen", SqlDbType.VarChar, 10) { Value = (object)item.no_yusen ?? DBNull.Value },
                    new SqlParameter("@no_yusen_hyoji", SqlDbType.VarChar, 10) { Value = (object)item.no_yusen_hyoji ?? DBNull.Value },
                    new SqlParameter("@cd_haigo_to", SqlDbType.VarChar, 13) { Value = (object)haigoTo.cd_haigo_to ?? DBNull.Value },
                    new SqlParameter("@cd_hin", SqlDbType.VarChar, 13) { Value = (object)item.cd_hin ?? DBNull.Value },

                };

                    foodproc.Database.ExecuteSqlCommand(queryExcute, parameters);

                }
            }
        }

        /// <summary>
        /// Check data recipe
        /// </summary>
        /// <param name="value"></param>
        /// <returns></returns>
        public int? CheckDataRecipe(FOODPROCSEntities foodproc, ParaHaigoSave value)
        {
            var haigoFrom = value.HaigoFrom;
            var haigoTo = value.HaigoTo;
            int results = 0;

            var queryCheckRecipe = "SELECT COUNT(*) AS countData"
                + " FROM ma_haigo_recipe r"

                + " INNER JOIN ma_haigo_mei m"
                + " ON m.cd_haigo = r.cd_haigo"
                + " AND m.no_han = r.no_han"
                + " AND m.qty_haigo_h = r.qty_haigo_h"
                + " AND m.qty_kihon = m.qty_haigo_h"
                + " AND m.flg_sakujyo = @FlgFlase"

                + " WHERE r.cd_haigo = @cd_haigo_to"
                + " AND r.no_han=1 "
                + " AND r.flg_sakujyo = @FlgFlase";
            var queryCheckRecipehyoji = " SELECT count(*) AS countData"
                   + " FROM ma_haigo_recipe_hyoji"

                   + " WHERE cd_haigo = @cd_haigo_to"
                   + " AND no_han = 1"
                   + " AND flg_sakujyo = @FlgFlase";
            var queryDoWork = "";
            if (haigoTo.radio_kirikae == Properties.Resources.m_kirikae_hyoji)
            {
                queryDoWork = queryCheckRecipehyoji;
            }
            else
            {
                queryDoWork = queryCheckRecipe;
            }

            results = foodproc.Database.SqlQuery<int>(queryDoWork, new SqlParameter("@cd_haigo_to", haigoTo.cd_haigo_to)
                                                                    , new SqlParameter("@FlgFlase", "0")).FirstOrDefault();

            return results;

        }

        /// <summary>
        /// Get def value base on contructed haigo
        /// </summary>
        /// <param name="cd_kaisha"></param>
        /// <param name="cd_kojyo"></param>
        /// <param name="cd_haigo"></param>
        /// <param name="no_han"></param>
        /// <returns></returns>
        private ma_haigo_mei_def_col getDefColValue(int cd_kaisha, int cd_kojyo, string cd_haigo, int no_han)
        {
            using (FOODPROCSEntities contextFrom = new FOODPROCSEntities(cd_kaisha, cd_kojyo))
            {
                ma_haigo_mei_def_col result;
                string query = @"
                    SELECT TOP 1
                        flg_tenkai
                        , qty_haigo_auto
                        , su_kigen_min
                        , su_kigen_max
                        , flg_mishiyo_seizo
                    FROM ma_haigo_mei   with (nolock)
                    WHERE
                        cd_haigo = @cd_haigo
                        AND no_han = @no_han
                        AND flg_sakujyo = 0
                        AND qty_kihon = qty_haigo_h
                ";
                result = contextFrom.Database.SqlQuery<ma_haigo_mei_def_col>(query, new SqlParameter("@cd_haigo", cd_haigo), new SqlParameter("@no_han", no_han)).FirstOrDefault();
                return result;
            }
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
    //Parameter from screen
    public class HaigoCopyPara
    {
        public string m_kirikae { get; set; }
        public string cd_haigo { get; set; }
        public int cd_kaisha { get; set; }
        public int cd_kojyo { get; set; }
        public string radio_kirikae { get; set; }
    }

    //List data NoHan
    public class NoHanList
    {
        public int no_han { get; set; }
    }

    //Result data
    public class HaigoData
    {
        public List<HaiGoFrom> DataFrom { get; set; }
        public List<HaiGoTo> DataTo { get; set; }
    }

    //Result data haigo FROM
    public class HaiGoFrom
    {
        public string cd_haigo { get; set; }
        public string nm_haigo { get; set; }
        public string kbn_hin { get; set; }
        public string kbn_vw { get; set; }
        public int qty_kihon { get; set; }
    }

    //Result kbn_vw
    public class KubunVw
    {
        public string kbn_vw { get; set; }
        public int qty_kihon { get; set; }
    }

    //Result data haigo TO
    public class HaiGoTo
    {
        public string cd_genryo { get; set; }
        public string nm_genryo { get; set; }
        public int kbn_hin { get; set; }
        public bool flg_mishiyo { get; set; }
        public short modeBind { get; set; }
        public int countSeihin { get; set; }
        public string kbn_vw { get; set; }
        public int isDuplicate { get; set; }
        public string isError { get; set; }
        public int qty_kihon { get; set; }
        public int? no_han_max { get; set; }
    }
    //Parameter Save 
    public class ParaHaigoSave
    {
        public ParaHaigoFromSave HaigoFrom { get; set; }
        public ParaHaigoToSave HaigoTo { get; set; }
    }
    //Parameter Save 
    public class ParaHaigoFromSave
    {
        public string m_kirikae { get; set; }
        public string cd_haigo_from { get; set; }
        public int cd_kaisha_from { get; set; }
        public int cd_kojyo_from { get; set; }
        public int no_han_from { get; set; }
        public int zenban_chk { get; set; }
        public int seihin_chk { get; set; }
    }
    //Parameter Save 
    public class ParaHaigoToSave
    {
        public string cd_haigo_to { get; set; }
        public int cd_kojyo_to { get; set; }
        public string radio_kirikae { get; set; }

        public int no_han_to { get; set; }
        public string dt_from { get; set; }
        public string dt_to { get; set; }
        public decimal qty_kihon { get; set; }
        public decimal ritsu_kihon { get; set; }

        public int kbn_hin { get; set; }
        public bool flg_mishiyo { get; set; }
        public int isDuplicate { get; set; }
        public string isError { get; set; }
    }

    public class HeaderData_From
    {
        public string cd_haigo { get; set; }
        public string nm_haigo { get; set; }
        public string nm_haigo_r { get; set; }
        public string kbn_hin { get; set; }
        public string cd_bunrui { get; set; }
        public double budomari { get; set; }
        public int qty_kihon { get; set; }
        public Nullable<double> ritsu_kihon { get; set; }
        public string cd_setsubi { get; set; }
        public bool flg_gasan { get; set; }
        public Nullable<double> qty_max { get; set; }
        public int no_han { get; set; }
        public int qty_haigo_h { get; set; }
        public Nullable<double> qty_haigo_kei { get; set; }
        public string biko { get; set; }
        public string no_seiho { get; set; }
        public string cd_tanto_seizo { get; set; }
        public Nullable<System.DateTime> dt_seizo { get; set; }
        public string cd_tanto_hinkan { get; set; }
        public Nullable<System.DateTime> dt_hinkan { get; set; }
        public System.DateTime dt_from { get; set; }
        public System.DateTime dt_to { get; set; }
        public string kbn_vw { get; set; }
        public Nullable<double> hijyu { get; set; }
        public bool flg_shorihin { get; set; }
        public bool flg_hinkan { get; set; }
        public bool flg_seizo { get; set; }
        public bool flg_sakujyo { get; set; }
        public bool flg_mishiyo { get; set; }
        public System.DateTime dt_toroku { get; set; }
        public System.DateTime dt_henko { get; set; }
        public string cd_koshin { get; set; }
        public Nullable<bool> kbn_shiagari { get; set; }
        public string nm_haigo_rm { get; set; }
        public string cd_haigo_seiho { get; set; }
        public Nullable<bool> flg_seiho_base { get; set; }
        public string nm_seiho { get; set; }
    }

    //Get data Update
    public class DataBeforeUpdate {
        public double? qty_haigo_kei { get; set; }
        public string biko { get; set; }
        public string no_seiho { get; set; }
        public bool? kbn_shiagari { get; set; }
        public string nm_seiho { get; set; }
    }

    public class DetailData_From
    { 
        public string cd_haigo { get; set; }
        public int no_han { get; set; }
        public int qty_haigo_h { get; set; }
        public int no_kotei { get; set; }
        public int no_tonyu { get; set; }
        public string cd_hin { get; set; }
        public string kbn_hin { get; set; }
        public string nm_hin { get; set; }
        public string cd_mark { get; set; }
        public double qty { get; set; }
        public double qty_haigo { get; set; }
        public double? qty_nisugata { get; set; }
        public int? su_nisugata { get; set; }
        public double? qty_kowake { get; set; }
        public int? su_kowake { get; set; }
        public string cd_futai { get; set; }
        public double? qty_futai { get; set; }
        public double? hijyu { get; set; }
        public double? budomari { get; set; }
        public int? flg_sakujyo { get; set; }
        public int? flg_mishiyo { get; set; }
        public DateTime? dt_toroku { get; set; }
        public DateTime? dt_henko { get; set; }
        public string cd_koshin { get; set; }
        public string kbn_jyotai { get; set; }
        public string kbn_shitei { get; set; }
        public byte? kbn_bunkatsu { get; set; }
        public double? hijyu_calc { get; set; }
        public int? cd_tani { get; set; }
        public int cd_tani_shiyo  { get; set; }
    }

    //Data Update qty_haigo_kei
    public class UpdatehaigoKei
    {
        public bool? kbn_shiagari { get; set; }
        public string kbn_vw { get; set; }
        public int no_han_to { get; set; }
    }

    public class DataSeizoLineFrom
    {
        public int no_seq { get; set; }
        public string cd_haigo { get; set; }
        public int no_yusen { get; set; }
        public string cd_line { get; set; }
        public bool flg_sakujyo { get; set; }
        public bool flg_mishiyo { get; set; }
        public System.DateTime dt_toroku { get; set; }
        public System.DateTime dt_henko { get; set; }
        public string cd_koshin { get; set; }
    }
    
    //Get Seihin From
    public class DataSeihinFrom {
        public string cd_hin { get; set; }
        public int? no_yusen { get; set; }
        public int? no_yusen_hyoji { get; set; }    
    }
    #endregion
}
