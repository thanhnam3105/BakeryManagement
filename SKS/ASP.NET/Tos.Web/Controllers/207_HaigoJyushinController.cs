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
using System.Data.Entity.Infrastructure;
using System.Data.Common;
using System.Transactions;
using System.Data;
using Tos.Web.Logging;
using System.Threading;

namespace Tos.Web.Controllers
{
    //created from 【SearchInputController(Ver1.7)】 Template
    public class HaigoJyushinController : ApiController
    {
        #region "Controllerで公開するAPI"
        public readonly string FlgFlase = "0";
        public readonly string FlgTrue = "1";
        public readonly string NoHanFirst = "1";
        public readonly byte? KubunNmacsKojyo = 2;
        public readonly byte? KubunHin_1 = 1;
        public readonly byte? KubunHin_3 = 3;
        public readonly byte? KubunHin_4 = 4;
        public readonly byte? KubunHin_5 = 5;
        public readonly byte? KubunHin_6 = 6;
        public readonly byte? KubunHin_9 = 9;
        private static List<processID> prioritySaveProcessing = new List<processID>();
        private static long p_ID = 0;

        /// <summary>
        /// パラメータで受け渡された 値に該当するすべてのtarget情報を StoredProcedure を利用して取得します。
        /// </summary>
        /// <param name="options"></param>
        /// <returns>target情報</returns>
        public StoredProcedureResult<sp_shohinkaihatsu_search_207_Result> Get([FromUri] HaigoJyushinPara value)
        {
            // TODO:target情報を管理しているDbContextとtargetの型を指定します。
            StoredProcedureResult<sp_shohinkaihatsu_search_207_Result> result = new StoredProcedureResult<sp_shohinkaihatsu_search_207_Result>();
            using (ShohinKaihatsuEntities context = new ShohinKaihatsuEntities())
            {
                context.Configuration.ProxyCreationEnabled = false;

                //TODO: DbContext のメソッドとしてストアドプロシージャを呼び出します。※ストアドのアウトプットパラメータとしてcountが返却される前提です
                //ObjectParameter count = new ObjectParameter("rowcount", typeof(int));
                string cd_category_25 = Properties.Resources.cd_category_25;
                short? kbn_status_4 = short.Parse(Properties.Resources.denso_zumi_status);

                result.Items = context.sp_shohinkaihatsu_search_207(value.cd_kaisha, value.cd_kojyo, value.flg_denso_jyotai
                                                                , value.dt_denso_toroku_from, value.dt_denso_toroku_to, value.kbn_sort
                                                                , cd_category_25, kbn_status_4, value.skip, value.top).ToList();
                if (result.Items.Count() > 0)
                {
                    result.Count = (int)result.Items.FirstOrDefault().total_rows;
                }
            }

            using (FOODPROCSEntities foodproc = new FOODPROCSEntities(value.cd_kaisha, value.cd_kojyo))
            {
                foodproc.Configuration.ProxyCreationEnabled = false;

                foreach (var item in result.Items)
                {
                    var haigoQuery = "SELECT TOP 1 cd_haigo";
                    haigoQuery = haigoQuery + " FROM ma_haigo_mei_hyoji ";
                    haigoQuery = haigoQuery + " WHERE cd_haigo_seiho = @cd_haigo";
                    haigoQuery = haigoQuery + " AND flg_sakujyo <> @FlgTrue";

                    var haigoNew = foodproc.Database.SqlQuery<string>(haigoQuery, new SqlParameter("@cd_haigo", item.cd_haigo.ToString())
                                                                            , new SqlParameter("@FlgTrue", FlgTrue)).FirstOrDefault();
                    if (haigoNew != null)
                    {
                        item.cd_haigo_new = haigoNew;
                    }

                    string haigoCode = (item.cd_haigo + "0000000000000").Substring(0, (value.su_code_standard + 1));

                    if (item.kbn_nmacs_kojyo == KubunNmacsKojyo)
                    {
                        if (CountDataFoodproc(foodproc, haigoCode, value.su_code_standard, 1))
                        {
                            item.flg_zyufuku_hyoji = 1;
                        }
                        if (CountDataFoodproc(foodproc, haigoCode, value.su_code_standard, 2))
                        {
                            item.flg_zyufuku = 1;
                        }
                    }
                    else
                    {
                        if (CountDataFoodproc(foodproc, haigoCode, value.su_code_standard, 3))
                        {
                            item.flg_zyufuku_hyoji = 1;
                        }
                        if (CountDataFoodproc(foodproc, haigoCode, value.su_code_standard, 4))
                        {
                            item.flg_zyufuku = 1;
                        }
                    }
                }
            }


            //TODO: DbContext のメソッドとしてデータカウントを取得するストアドプロシージャを呼び出します。
            return result;
        }

        /// <summary>
        /// パラメータで受け渡された target 情報をもとにエントリーを一括更新（追加・更新・削除）します。
        /// </summary>
        /// <param name="value"></param>
        /// <returns></returns>
        public bool CountDataFoodproc(FOODPROCSEntities foodproc, string cd_haigo, byte? su_code_standard, short mode)
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
        /// パラメータで受け渡された target 情報をもとにエントリーを一括更新（追加・更新・削除）します。
        /// </summary>
        /// <param name="value"></param>
        /// <returns></returns>
        public HttpResponseMessage Post([FromBody]ParaHaigoJyushinSave value)
        {
            if (value == null || value.DataUpdate == null)
            {
                return Request.CreateErrorResponse(HttpStatusCode.InternalServerError, Properties.Resources.NotNullAllow);
            }
            processID processID = new processID() { ID = p_ID++, waitTime = 0 };
            try
            {
                // Insert conflict prevent
                preventConflict(processID);

                var kbn_nmacs_kojyo = GetKubunNmacKojyo(value.cd_kaisha, value.cd_kojyo);
                using (FOODPROCSEntities foodproc = new FOODPROCSEntities(value.cd_kaisha, value.cd_kojyo))
                {
                    foodproc.Configuration.ProxyCreationEnabled = false;

                    foreach (var item in value.DataUpdate.Updated)
                    {
                        if (item.flg_denso_jyotai)
                        {
                            if (item.kbn_nmacs_kojyo == KubunNmacsKojyo)
                            {
                                if (CountDataFoodproc(foodproc, item.cd_haigo_new, value.su_code_standard, 1))
                                {
                                    return Request.CreateResponse<string>(HttpStatusCode.BadRequest, "AP0058");
                                }
                            }
                            else
                            {
                                if (CountDataFoodproc(foodproc, item.cd_haigo_new, value.su_code_standard, 3))
                                {
                                    return Request.CreateResponse<string>(HttpStatusCode.BadRequest, "AP0058");
                                }
                            }
                        }
                    }

                }

                using (ShohinKaihatsuEntities context = new ShohinKaihatsuEntities())
                {
                    foreach (var item in value.DataUpdate.Updated)
                    {
                        if (item.flg_denso_jyotai)
                        {
                            decimal cd_haigo = decimal.Parse(item.cd_haigo);
                            var m_header = context.ma_haigo_header.Where(m => m.cd_haigo == cd_haigo && m.kbn_haishi != 1 && m.status < 4).ToList();
                            if (m_header.Count() > 0)
                            {
                                return Request.CreateResponse<string>(HttpStatusCode.BadRequest, "AP0132");
                            }
                        }
                    }
                }
                int flg_check_error_all = 0;

                List<sp_shohinkaihatsu_juyshin_207_Result> dataHaigoInsert = new List<sp_shohinkaihatsu_juyshin_207_Result>();
                List<SeizoLineData> SeizoLine = new List<SeizoLineData>();
                List<SeihinSeihoData> SeihinSeizo = new List<SeihinSeihoData>();
                int codeStandard = int.Parse(value.su_code_standard.ToString());
                string haigoCodeSeihin = "XXXXXXXXXXXXX".Substring(0, (codeStandard + 1));

                var JushinchuStatus = byte.Parse(Properties.Resources.JushinchuStatus);
                var DensoZumiStatus = byte.Parse(Properties.Resources.denso_zumi_status);

                using (ShohinKaihatsuEntities context = new ShohinKaihatsuEntities())
                {
                    var strHaigoAll = "";
                    List<string> HaigoDataUpdate = new List<string>();
                    var spaceHaigo = "             ";
                    foreach (var item in value.DataUpdate.Updated)
                    {
                        if (item.flg_denso_jyotai)
                        {
                            var strHaigo = (item.cd_haigo + spaceHaigo).Substring(0, 13);
                            var strHaigoNew = (item.cd_haigo_new + spaceHaigo).Substring(0, 13);
                            HaigoDataUpdate.Add(strHaigo + strHaigoNew);

                            var HaigoCode = decimal.Parse(item.cd_haigo);
                            var sezoLineHaigo = context.ma_seizo_line.Where(m => m.cd_haigo == HaigoCode).ToList();
                            foreach (var line in sezoLineHaigo)
                            {
                                SeizoLineData addLine = new SeizoLineData();
                                addLine.cd_haigo = line.cd_haigo.ToString();
                                addLine.cd_haigo_new = item.cd_haigo_new;
                                if (item.su_linecode_standard != item.su_linecode_standard_saki)
                                {
                                    addLine.cd_line = item.cd_line_saki.ToString();
                                }
                                else
                                {
                                    addLine.cd_line = line.cd_line.ToString();
                                }
                                addLine.no_yusen = line.no_yusen;
                                int su_linecode_standard_saki = int.Parse(item.su_linecode_standard_saki.ToString());
                                addLine.cd_line = ("000" + addLine.cd_line).Substring((("000" + addLine.cd_line).Length - su_linecode_standard_saki), su_linecode_standard_saki);

                                SeizoLine.Add(addLine);
                            }

                            var seihinSeihoData = context.ma_seihin_seiho.Where(m => m.cd_haigo == HaigoCode).ToList();

                            foreach (var seiho in seihinSeihoData)
                            {
                                SeihinSeihoData addSeiho = new SeihinSeihoData();

                                addSeiho.cd_seihin = seiho.cd_hin.ToString();
                                addSeiho.nm_seihin = seiho.nm_seihin;
                                addSeiho.nisugata_hyoji = (seiho.nisugata_hyoji == null ? "" : seiho.nisugata_hyoji);
                                addSeiho.su_iri = 1;
                                addSeiho.qty = 1;
                                addSeiho.no_yusen = seiho.no_yusen;
                                addSeiho.cd_haigo_seihin = seiho.cd_haigo;
                                addSeiho.cd_haigo_new = item.cd_haigo_new;

                                SeihinSeizo.Add(addSeiho);
                            }
                        }
                    }

                    strHaigoAll = string.Join(",", HaigoDataUpdate);
                
                    dataHaigoInsert = context.sp_shohinkaihatsu_juyshin_207(strHaigoAll, value.cd_kaisha, value.cd_kojyo,
                                                                                KubunHin_1, KubunHin_3, KubunHin_4, KubunHin_5, KubunHin_6, KubunHin_9
                                                                                , DensoZumiStatus, JushinchuStatus, value.su_code_standard).ToList();

                    foreach (var item in dataHaigoInsert) {
                        if (item.kbn_hin_m == KubunHin_4 && item.kbn_shikakari_m == 0)
                        {
                            var isDataExists = value.DataUpdate.Updated.Where(m => m.flg_denso_jyotai == true && m.cd_haigo == item.cd_hin_m.ToString()).FirstOrDefault();
                            if (isDataExists != null) {
                                item.cd_hin_m_convert = isDataExists.cd_haigo_new;
                            }
                        }
                    }

                }
                try
                {
                    using (var scope = new TransactionScope(TransactionScopeOption.Required))
                    {
                        using (FOODPROCSEntities foodproc = new FOODPROCSEntities(value.cd_kaisha, value.cd_kojyo))
                        {
                            foreach (var item in value.DataUpdate.Updated)
                            {
                                if (item.flg_denso_jyotai)
                                {
                                    var haigoMei = dataHaigoInsert.Where(m => m.cd_haigo == item.cd_haigo).FirstOrDefault();
                                    if (haigoMei != null)
                                    {
                                        InsertHaigoMeiHyoji(foodproc, haigoMei);
                                    }
                                }
                            }

                            foreach (var item in dataHaigoInsert)
                            {
                                flg_check_error_all = flg_check_error_all + InsertHaigoRecipeHyoji(foodproc, item, value.su_code_standard);
                            }

                            InsertSeizoLineHyoji(foodproc, value.su_code_standard, SeizoLine);
                            UpdateSeihinSeiho(foodproc, int.Parse(value.su_code_standard.ToString()), SeihinSeizo, haigoCodeSeihin, kbn_nmacs_kojyo);
                        }
                        using (var scopeTwo = new TransactionScope(TransactionScopeOption.RequiresNew))
                        {
                            using (ShohinKaihatsuEntities context = new ShohinKaihatsuEntities())
                            {
                                foreach (var item in value.DataUpdate.Updated)
                                {
                                    if (item.flg_denso_jyotai)
                                    {
                                        UpdateHeaderStatus(context, item);
                                    }
                                }
                                CommonController common = new CommonController();
                                string userName = decimal.Parse(UserInfo.GetUserNameFromIdentity(this.User.Identity)).ToString();
                                if (dataHaigoInsert.Count() > 0)
                                {
                                    foreach (var item in value.DataUpdate.Updated)
                                    {
                                        if (item.flg_denso_jyotai)
                                        {
                                            var seihoDenso = context.ma_seiho_denso.Where(m => m.cd_kaisha == value.cd_kaisha
                                                                                        && m.cd_kojyo == value.cd_kojyo
                                                                                        && m.no_seiho == item.no_seiho).FirstOrDefault();
                                            if (seihoDenso != null)
                                            {
                                                seihoDenso.flg_denso_jyotai = true;
                                                seihoDenso.dt_denso_kanryo = DateTime.Now;

                                                context.ma_seiho_denso.Attach(seihoDenso);
                                                context.Entry<ma_seiho_denso>(seihoDenso).State = EntityState.Modified;
                                            }

                                            tr_event_log log = new tr_event_log();
                                            log.no_seiho = item.no_seiho;
                                            log.cd_tanto_kaisha = value.cd_kaisha;
                                            log.cd_tanto = userName;
                                            log.cd_koshin = userName;
                                            log.nm_shori = "配合受信";
                                            log.nm_ope = "登録";
                                            log.dt_shori = DateTime.Now;
                                            log.ip_address = common.GetIPClientAddress();
                                            log.kbn_system = 1;

                                            context.sp_shohinkaihatsu_insert_200_event_log(log.no_seiho
                                                                        , log.cd_tanto_kaisha
                                                                        , log.cd_tanto
                                                                        , log.cd_koshin
                                                                        , log.nm_shori
                                                                        , log.nm_ope
                                                                        , log.dt_shori
                                                                        , log.ip_address
                                                                        , log.kbn_system);
                                        }
                                    }
                                }
                                context.SaveChanges();
                            }
                            scopeTwo.Complete();
                        }
                        scope.Complete();
                    }
                }
                catch (Exception ex)
                {
                    //Rollback database
                    using (ShohinKaihatsuEntities context = new ShohinKaihatsuEntities())
                    {
                        foreach (var item in value.DataUpdate.Updated)
                        {
                            if (item.flg_denso_jyotai)
                            {
                                decimal haigoCode = decimal.Parse(item.cd_haigo);
                                var haigoHeader = context.ma_haigo_header.Where(m => m.cd_haigo == haigoCode && m.status == JushinchuStatus).ToList();
                                foreach (var header in haigoHeader)
                                {
                                    header.status = DensoZumiStatus;

                                    context.ma_haigo_header.Attach(header);
                                    context.Entry<ma_haigo_header>(header).State = EntityState.Modified;
                                }
                            }
                        }
                        context.SaveChanges();
                    }

                    Logger.App.Error(ex.Message, ex);
                    return Request.CreateResponse(HttpStatusCode.BadGateway, ex.Message);
                }

                return Request.CreateResponse<int>(HttpStatusCode.OK, flg_check_error_all);
            }
            catch (Exception ex)
            {
                // Process in error
                Logger.App.Error(ex.Message, ex);
                return Request.CreateErrorResponse(HttpStatusCode.NotFound, ex.Message);
            }
            finally
            {
                // Remove completed process
                prioritySaveProcessing.Remove(processID);
            }

        }

        /// <summary>
        /// Insert 表示用配合名マスタ（ma_haigo_mei_hyoji）table
        /// </summary>
        /// <param name="value"></param>
        /// <returns></returns>
        public bool InsertHaigoMeiHyoji(FOODPROCSEntities foodproc, sp_shohinkaihatsu_juyshin_207_Result item)
        {
            //var queryGetLength = "SELECT CHARACTER_MAXIMUM_LENGTH"
            //                    + " FROM information_schema.columns "
            //                    + " WHERE TABLE_NAME = 'ma_haigo_mei_hyoji'"
            //                    + " AND COLUMN_NAME = 'nm_haigo_r'";
            //int? NmhaigoRLength = foodproc.Database.SqlQuery<int>(queryGetLength).FirstOrDefault();

            var queryInsert = " DECLARE @dateNow DATETIME = GETDATE();" +

                            " INSERT INTO [dbo].[ma_haigo_mei_hyoji]" +
                                   " ([cd_haigo]" +
                                   " ,[nm_haigo]" +
                                   " ,[nm_haigo_r]" +
                                   " ,[kbn_hin]" +
                                   " ,[cd_bunrui] " +
                                   " ,[budomari]" +
                                   " ,[qty_kihon]" +
                                   " ,[ritsu_kihon]" +
                                   " ,[cd_setsubi]" +
                                   " ,[flg_gasan]" +
                                   " ,[qty_max]" +
                                   " ,[no_han]" +
                                   " ,[qty_haigo_h]" +
                                   " ,[qty_haigo_kei]" +
                                   " ,[biko]" +
                                   " ,[no_seiho]" +
                                   " ,[cd_tanto_seizo]" +
                                   " ,[dt_seizo]" +
                                   " ,[cd_tanto_hinkan]" +
                                   " ,[dt_hinkan]" +
                                   " ,[dt_from]" +
                                   " ,[dt_to]" +
                                   " ,[kbn_vw]" +
                                   " ,[hijyu]" +
                                   " ,[flg_shorihin]" +
                                   " ,[flg_hinkan]" +
                                   " ,[flg_seizo]" +
                                   " ,[flg_sakujyo]" +
                                   " ,[flg_mishiyo]" +
                                   " ,[dt_toroku]" +
                                   " ,[dt_henko]" +
                                   " ,[cd_koshin]" +
                                   " ,[kbn_shiagari]" +
                                   " ,[nm_haigo_rm]" +
                                   " ,[cd_haigo_seiho]" +
                                   " ,[flg_seiho_base]" +
                                   " ,[nm_seiho])" +
                                " VALUES" +
                                   " ( @cd_haigo" +
                                   " , @nm_haigo" +
                                   " , @nm_haigo_r" +
                                   " , @kbn_hin" +
                                   " , @cd_bunrui" +
                                   " , @budomari" +
                                   " , @qty_kihon" +
                                   " , @ritsu_kihon" +
                                   " , @cd_setsubi" +
                                   " , '0'" +
                                   " , @qty_max" +
                                   " , '1'" + //@no_han
                                   " , @qty_haigo_h" +
                                   " , @qty_haigo_kei" +
                                   " , @biko" +
                                   " , @no_seiho" +
                                   " , NULL" + //@cd_tanto_seizo
                                   " , NULL" + //@dt_seizo
                                   " , NULL" + //@cd_tanto_hinkan
                                   " , NULL" + //@dt_hinkan
                                   " , @dt_from" +
                                   " , @dt_to" +
                                   " , @kbn_vw" +
                                   " , @hijyu" +
                                   " , '0'" + //@flg_shorihin
                                   " , '0'" + //@flg_hinkan
                                   " , '0'" + //@flg_seizo
                                   " , '0'" + //@flg_sakujyo
                                   " , '0'" + //@flg_mishiyo
                                   " , @dateNow" + //@dt_toroku
                                   " , @dateNow" + //@dt_henko
                                   " , @cd_koshin" +
                                   " , @kbn_shiagari" +
                                   " , NULL" + //@nm_haigo_rm
                                   " , @cd_haigo_seiho" +
                                   " , 1" + //@flg_seiho_base
                                   " , @nm_seiho);";

            string userName = decimal.Parse(UserInfo.GetUserNameFromIdentity(this.User.Identity)).ToString();
            var parameters = new object[]
                {
                    new SqlParameter("@cd_haigo", SqlDbType.VarChar, 13) { Value = (object)item.cd_haigo_new ?? DBNull.Value },
                    new SqlParameter("@nm_haigo", SqlDbType.VarChar, 60) { Value = (object)item.nm_haigo ?? DBNull.Value },
                    new SqlParameter("@nm_haigo_r", SqlDbType.VarChar, 100) { Value = (object)item.nm_haigo_r ?? "" },
                    new SqlParameter("@kbn_hin", SqlDbType.VarChar, 1) { Value = (object)item.kbn_hin ?? DBNull.Value },
                    new SqlParameter("@cd_bunrui", SqlDbType.VarChar, 2) { Value = (object)item.cd_bunrui ?? "" },
                    new SqlParameter("@budomari", SqlDbType.Float) { Value = (object)item.budomari ?? DBNull.Value },
                    new SqlParameter("@qty_kihon", SqlDbType.Int) { Value = (object)item.qty_kihon ?? DBNull.Value },
                    new SqlParameter("@ritsu_kihon", SqlDbType.Float) { Value = (object)item.ritsu_kihon ?? DBNull.Value },
                    new SqlParameter("@cd_setsubi", SqlDbType.VarChar, 2) { Value = (object)item.cd_setsubi ?? DBNull.Value },
                    new SqlParameter("@flg_gasan", SqlDbType.Bit) { Value = (object)item.flg_gasan ?? DBNull.Value },
                    new SqlParameter("@qty_max", SqlDbType.Float) { Value = (object)item.qty_max ?? DBNull.Value },
                    new SqlParameter("@qty_haigo_h", SqlDbType.Int) { Value = (object)item.qty_kihon ?? DBNull.Value },
                    new SqlParameter("@qty_haigo_kei", SqlDbType.Float) { Value = (object)item.qty_haigo_kei ?? DBNull.Value },
                    new SqlParameter("@biko", SqlDbType.VarChar, 300) { Value = (object)item.biko ?? "" },
                    new SqlParameter("@no_seiho", SqlDbType.VarChar, 20) { Value = (object)item.no_seiho ?? "" },
                    new SqlParameter("@dt_from", SqlDbType.DateTime) { Value = "1950/01/01"},
                    new SqlParameter("@dt_to", SqlDbType.DateTime) { Value = "3000/12/31" },
                    new SqlParameter("@kbn_vw", SqlDbType.VarChar, 2) { Value = (object)item.kbn_vw ?? DBNull.Value },
                    new SqlParameter("@hijyu", SqlDbType.Float) { Value = (object)item.hijyu ?? DBNull.Value },
                    new SqlParameter("@cd_koshin", SqlDbType.VarChar, 10) { Value = userName },
                    new SqlParameter("@kbn_shiagari", SqlDbType.Bit) { Value = (object)item.kbn_shiagari ?? DBNull.Value },
                    new SqlParameter("@cd_haigo_seiho", SqlDbType.VarChar, 13) { Value = (object)item.cd_haigo ?? DBNull.Value },
                    new SqlParameter("@nm_seiho", SqlDbType.VarChar, 120) { Value = (object)item.nm_seiho ?? DBNull.Value }
                };
            foodproc.Database.ExecuteSqlCommand(queryInsert, parameters);

            return true;

        }

        /// <summary>
        /// Insert 表示用配合レシピマスタ(ma_haigo_recipe_hyoji) table
        /// </summary>
        /// <param name="value"></param>
        /// <returns></returns>
        public int InsertHaigoRecipeHyoji(FOODPROCSEntities foodproc, sp_shohinkaihatsu_juyshin_207_Result item, byte? su_code_standard)
        {
            var e_genryo = "0000000000000";
            int flg_check_err = 0;
            int maxStandard = (int)su_code_standard;
            e_genryo = e_genryo.Substring(0, maxStandard);

            if (item.kbn_hin_m == KubunHin_1)
            {
                if (item.cd_hin_m_convert != e_genryo)
                {
                    var query = "SELECT cd_hin, ISNULL(kbn_jyotai, '1') AS kbn_jyotai"
                                + " FROM ma_genshizai"
                                + " WHERE cd_hin = @cd_hin_m"
                                + " AND flg_sakujyo = @FlgFlase"
                                + " AND flg_mishiyo = @FlgFlase";

                    var result = foodproc.Database.SqlQuery<genshizai_data>(query, new SqlParameter("@cd_hin_m", item.cd_hin_m_convert)
                                                                            , new SqlParameter("@FlgFlase", FlgFlase)).FirstOrDefault();

                    if (result != null)
                    {
                        item.kbn_jyotai_m = byte.Parse(result.kbn_jyotai);
                    }
                    else
                    {
                        flg_check_err++;
                        item.cd_hin_m_convert = e_genryo;
                    }

                }
                else
                {
                    flg_check_err++;
                }
            }
            else if (item.kbn_hin_m == KubunHin_4)
            {
                if (item.kbn_shikakari_m == 0)
                {
                    if (item.cd_hin_m_convert.Length == su_code_standard)
                    {
                        var queryHaigo = "SELECT cd_haigo"
                                + " FROM ma_haigo_mei_hyoji"
                                + " WHERE cd_haigo_seiho = @cd_hin_m"
                                + " AND flg_sakujyo = @FlgFlase"
                                + " AND flg_mishiyo = @FlgFlase";

                        var result = foodproc.Database.SqlQuery<string>(queryHaigo, new SqlParameter("@cd_hin_m", item.cd_hin_m_convert)
                                                                                , new SqlParameter("@FlgFlase", FlgFlase)).FirstOrDefault();
                        if (result == null)
                        {
                            item.cd_hin_m_convert = e_genryo + "0";
                            flg_check_err++;
                        }
                        else {
                            item.cd_hin_m = decimal.Parse(result);
                            item.cd_hin_m_convert = result;
                        }
                    }
                    else if (item.cd_hin_m_convert.Length != (su_code_standard + 1))
                    {
                        item.cd_hin_m_convert = e_genryo + "0";
                        flg_check_err++;
                    }
                }
                else if (item.kbn_shikakari_m == 1)
                {
                    if (item.flg_daihyo_kojyo == true)
                    {
                        var queryHaigo = "SELECT cd_haigo"
                                    + " FROM ma_haigo_mei_hyoji"
                                    + " WHERE cd_haigo = @cd_hin_m"
                                    + " AND flg_sakujyo = @FlgFlase"
                                    + " AND flg_mishiyo = @FlgFlase";

                        var result = foodproc.Database.SqlQuery<string>(queryHaigo, new SqlParameter("@cd_hin_m", item.cd_hin_m_convert)
                                                                                , new SqlParameter("@FlgFlase", FlgFlase)).FirstOrDefault();
                        if (result == null)
                        {
                            item.cd_hin_m_convert = e_genryo + "0";
                            flg_check_err++;
                        }
                        else
                        {
                            item.cd_hin_m = decimal.Parse(result);
                            item.cd_hin_m_convert = result;
                        }
                    }
                    else
                    {
                        item.cd_hin_m_convert = e_genryo + "0";
                        flg_check_err++;
                    }
                }
            }
            else if (item.kbn_hin_m == KubunHin_5)
            {
                if (item.flg_daihyo_kojyo == true)
                {
                    var queryHaigo = "SELECT cd_maeshori"
                                   + " FROM ma_maeshori"
                                   + " WHERE cd_maeshori = @cd_hin_m"
                                   + " AND flg_sakujyo = @FlgFlase"
                                   + " AND flg_mishiyo = @FlgFlase";

                    var result = foodproc.Database.SqlQuery<string>(queryHaigo, new SqlParameter("@cd_hin_m", item.cd_hin_m_convert)
                                                                              , new SqlParameter("@FlgFlase", FlgFlase)).FirstOrDefault();
                    if (result == null)
                    {
                        item.cd_hin_m_convert = e_genryo + "0";
                        flg_check_err++;
                    }
                    else
                    {
                        var queryMaeshori = "SELECT cd_maeshori AS cd_hin, ISNULL(genshizai.kbn_jyotai, 1) AS kbn_jyotai"
                                            + " FROM ma_maeshori maeshori"

                                             + " INNER JOIN ma_genshizai genshizai"
                                             + " ON maeshori.cd_hin = genshizai.cd_hin"

                                             + " WHERE maeshori.cd_hin = @cd_hin_m"
                                             + " AND maeshori.flg_sakujyo = @FlgFlase"
                                             + " AND maeshori.flg_mishiyo = @FlgFlase"
                                             + " AND genshizai.flg_mishiyo = @FlgFlase";
                        var maeshoriData = foodproc.Database.SqlQuery<genshizai_data>(queryMaeshori, new SqlParameter("@cd_hin_m", item.cd_hin_m_convert)
                                                                                                   , new SqlParameter("@FlgFlase", FlgFlase)).FirstOrDefault();

                        if (maeshoriData != null)
                        {
                            item.kbn_jyotai_m = byte.Parse(maeshoriData.kbn_jyotai);
                        }
                    }
                }
                else
                {
                    item.cd_hin_m_convert = e_genryo + "0";
                    flg_check_err++;
                }
            }
            else if (item.kbn_hin_m == KubunHin_6)
            {
                item.kbn_hin_m = 1;

                if (item.flg_daihyo_kojyo == true)
                {
                    var querySeihin = "SELECT cd_hin, ISNULL(kbn_jyotai, 1) AS kbn_jyotai"
                                   + " FROM ma_seihin"
                                   + " WHERE cd_hin = @cd_hin_m"
                                   + " AND flg_sakujyo = @FlgFlase"
                                   + " AND flg_mishiyo = @FlgFlase";

                    var sehinData = foodproc.Database.SqlQuery<genshizai_data>(querySeihin, new SqlParameter("@cd_hin_m", item.cd_hin_m_convert)
                                                                                          , new SqlParameter("@FlgFlase", FlgFlase)).FirstOrDefault();
                    if (sehinData == null)
                    {
                        item.cd_hin_m_convert = e_genryo;
                        flg_check_err++;
                    }
                    else
                    {
                        item.kbn_jyotai_m = byte.Parse(sehinData.kbn_jyotai);
                    }
                }
                else
                {
                    item.cd_hin_m_convert = e_genryo;
                    flg_check_err++;
                }
            }

            var queryInsertHaigoRecipeHyoji = " DECLARE @dateNow DATETIME = GETDATE();"
                                              + " INSERT INTO [dbo].[ma_haigo_recipe_hyoji]"
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
                                                   + " ,[kbn_bunkatsu])"
                                             + " VALUES"
                                                   + " ( @cd_haigo"
                                                   + " , 1"   //@no_han
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
                                                   + " , 0"     //@su_nisugata
                                                   + " , 0"     //@qty_kowake
                                                   + " , 0"     //@su_kowake
                                                   + " , ''"    //@cd_futai
                                                   + " , NULL " //@qty_futai
                                                   + " , @hijyu"
                                                   + " , @budomari"
                                                   + " , 0"     //@flg_sakujyo
                                                   + " , 0"     //@flg_mishiyo
                                                   + " , @dateNow" //@dt_toroku
                                                   + " , @dateNow"  //@dt_henko
                                                   + " , @cd_koshin"
                                                   + " , @kbn_jyotai"
                                                   + " , @kbn_shitei"
                                                   + " , @kbn_bunkatsu)";

            string userName = decimal.Parse(UserInfo.GetUserNameFromIdentity(this.User.Identity)).ToString();
            var parameters = new object[]
                {
                    new SqlParameter("@cd_haigo", SqlDbType.VarChar, 13) { Value = (object)item.cd_haigo_new ?? DBNull.Value },
                    new SqlParameter("@qty_haigo_h", SqlDbType.Int) { Value = (object)item.qty_kihon ?? DBNull.Value },
                    new SqlParameter("@no_kotei", SqlDbType.Int) { Value = (object)item.no_kotei_m ?? DBNull.Value },
                    new SqlParameter("@no_tonyu", SqlDbType.Int) { Value = (object)item.no_tonyu_m ?? DBNull.Value },
                    new SqlParameter("@cd_hin", SqlDbType.VarChar, 13) { Value = (object)item.cd_hin_m_convert ?? DBNull.Value },
                    new SqlParameter("@kbn_hin", SqlDbType.VarChar, 1) { Value = (object)item.kbn_hin_m ?? DBNull.Value },
                    new SqlParameter("@nm_hin", SqlDbType.VarChar, 60) { Value = (object)item.nm_hin_m ?? DBNull.Value },
                    new SqlParameter("@cd_mark", SqlDbType.VarChar, 2) { Value = (object)item.cd_mark_m ?? DBNull.Value },
                    new SqlParameter("@qty", SqlDbType.Float) { Value = (object)(item.qty_haigo_m == null? 0: item.qty_haigo_m) ?? DBNull.Value },
                    new SqlParameter("@qty_haigo", SqlDbType.Float) { Value = (object)(item.qty_haigo_m == null? 0: item.qty_haigo_m) ?? DBNull.Value },
                    new SqlParameter("@qty_nisugata", SqlDbType.Float) { Value = (object)(item.qty_nisugata_m == null? 0: item.qty_nisugata_m) ?? DBNull.Value  },
                    new SqlParameter("@hijyu", SqlDbType.Float) { Value = (object)(item.hijyu_m == null? 0: item.hijyu_m) ?? DBNull.Value },
                    new SqlParameter("@budomari", SqlDbType.Float) { Value = (object)(item.budomari_m == null ? 100: item.budomari_m) ?? DBNull.Value },
                    new SqlParameter("@cd_koshin", SqlDbType.VarChar, 10) { Value = userName },
                    new SqlParameter("@kbn_jyotai", SqlDbType.VarChar, 1) { Value = (object)item.kbn_jyotai_m ?? DBNull.Value },
                    new SqlParameter("@kbn_shitei", SqlDbType.Bit) { Value = (object)(item.flg_shitei_m  == null? false : item.flg_shitei_m) ?? DBNull.Value },
                    new SqlParameter("@kbn_bunkatsu", SqlDbType.SmallInt) { Value = (object)item.kbn_bunkatsu_m ?? DBNull.Value }
                };
            foodproc.Database.ExecuteSqlCommand(queryInsertHaigoRecipeHyoji, parameters);


            return flg_check_err;
        }

        /// <summary>
        /// Insert 表示用製造ライン（ma_seizo_line_hyoji） table
        /// </summary>
        /// <param name="value"></param>
        /// <returns></returns>
        public void InsertSeizoLineHyoji(FOODPROCSEntities foodproc, byte? su_code_standard, List<SeizoLineData> seizoLineData)
        {
            foreach (var item in seizoLineData)
            {
                var insertSeizoLine = " DECLARE @dateNow DATETIME = GETDATE();"
                                    + " INSERT INTO [dbo].[ma_seizo_line_hyoji]"
                                            + " ([cd_haigo]"
                                            + " ,[no_yusen]"
                                            + " ,[cd_line]"
                                            + " ,[flg_sakujyo]"
                                            + " ,[flg_mishiyo]"
                                            + " ,[dt_toroku]"
                                            + " ,[dt_henko]"
                                            + " ,[cd_koshin]"
                                            + " ,[msrepl_tran_version])"
                                     + " VALUES"
                                           + " ( @cd_haigo"
                                           + " , @no_yusen"
                                           + " , @cd_line"
                                           + " , 0"  //@flg_sakujyo
                                           + " , 0"  //@flg_mishiyo
                                           + " , @dateNow"    //@dt_toroku
                                           + " , @dateNow"     //@dt_henko
                                           + " , @cd_koshin"
                                           + " , NEWID());";
                string userName = decimal.Parse(UserInfo.GetUserNameFromIdentity(this.User.Identity)).ToString();
                var parameters = new object[]
                {
                    new SqlParameter("@cd_haigo", SqlDbType.VarChar, 13) { Value = item.cd_haigo_new },
                    new SqlParameter("@no_yusen", SqlDbType.Int) { Value = item.no_yusen },
                    new SqlParameter("@cd_line", SqlDbType.VarChar, 3) { Value = item.cd_line },
                    new SqlParameter("@cd_koshin", SqlDbType.VarChar, 10) { Value = userName }
                };
                foodproc.Database.ExecuteSqlCommand(insertSeizoLine, parameters);
            }

        }


        /// <summary>
        /// Update 製品マスタ(ma_seihin_seiho) table
        /// </summary>
        /// <param name="value"></param>
        /// <returns></returns>
        public void UpdateSeihinSeiho(FOODPROCSEntities foodproc, int su_code_standard, List<SeihinSeihoData> SeihinSeizo, string haigoCodeSeihin, byte? kbn_nmacs_kojyo)
        {

            foreach (var item in SeihinSeizo)
            {
                item.cd_seihin = ("000000000000" + item.cd_seihin).Substring(((("000000000000" + item.cd_seihin)).Length - su_code_standard), su_code_standard);

                var querySeihin = " SELECT cd_hin"
                                + " FROM ma_seihin"
                                + " WHERE cd_hin = @cd_seihin"
                                + " AND flg_sakujyo = @FlgFlase";

                var sehinData = foodproc.Database.SqlQuery<string>(querySeihin, new SqlParameter("@cd_seihin", item.cd_seihin)
                                                                            , new SqlParameter("@FlgFlase", FlgFlase)).FirstOrDefault();
                string userName = decimal.Parse(UserInfo.GetUserNameFromIdentity(this.User.Identity)).ToString();
                if (sehinData == null)
                {
                    var queryInsertSeihin = " DECLARE @nm_seihin_r VARCHAR(20)"
                                            + " SET @nm_seihin_r = @nm_hin_r"
                                            + " SET @nm_seihin_r = LEFT(@nm_seihin_r, 20);"
                                            + " IF(DATALENGTH(@nm_seihin_r) > 20)"
                                            + " BEGIN"
                                            + " WHILE(DATALENGTH(@nm_seihin_r) > 20)"
                                            + " BEGIN"
                                            + " SET @nm_seihin_r = LEFT(@nm_seihin_r, LEN(@nm_seihin_r)-1);"
                                            + " END"
                                            + " END"
                                            + " DECLARE @dateNow DATETIME = GETDATE();"
                                            + "INSERT INTO [dbo].[ma_seihin]"
                                                   + " ([cd_hin]"
                                                   + " ,[nm_hin]"
                                                   + " ,[nm_hin_r]"
                                                   + " ,[cd_tani_jyuchu]"
                                                   + " ,[cd_tani_shiyo]"
                                                   + " ,[cd_niuke]"
                                                   + " ,[budomari]"
                                                   + " ,[hijyu]"
                                                   + " ,[kbn_jyotai]"
                                                   + " ,[su_saitei]"
                                                   + " ,[tani_shomi]"
                                                   + " ,[cd_hini]"
                                                   + " ,[cd_haigo]"
                                                   + " ,[no_yusen]"
                                                   + " ,[cd_haigo_hyoji]"
                                                   + " ,[no_yusen_hyoji]"
                                                   + " ,[kbn_seisan]"
                                                   + " ,[biko]"
                                                   + " ,[kbn_seihin]"
                                                   + " ,[kbn_kuraire]"
                                                   + " ,[kbn_vw]"
                                                   + " ,[flg_sakujyo]"
                                                   + " ,[flg_mishiyo]"
                                                   + " ,[dt_toroku]"
                                                   + " ,[dt_henko]"
                                                   + " ,[cd_koshin]"
                                                   + (kbn_nmacs_kojyo != KubunNmacsKojyo ?
                                                     (", [kbn_sakusei])") :
                                                     (
                                                          " ,[cd_itf]"
                                                        + " ,[kbn_kisan_shomi]"
                                                        + " ,[kikan_shomi_kaifu2]"
                                                        + " ,[tani_shomi_kaifu2]"
                                                        + " ,[kbn_hokan_kaifu1]"
                                                        + " ,[kbn_hokan_kaifu2]"
                                                        + " ,[su_saidai]"
                                                        + " ,[kin_tanka_shiire]"
                                                        + " ,[cd_kura]"
                                                        + " ,[cd_shokuba_hachu]"
                                                        + " ,[kin_tanka_zaiko]"
                                                        + " ,[kbn_shiyo_tanka]"
                                                        + " ,[cd_route_uriage]"
                                                        + " ,[kbn_deki]"
                                                        + " ,[kbn_kura]"
                                                        + " ,[kbn_auto_kura]"
                                                        + " ,[kbn_azuke_kura]"
                                                        + " ,[kbn_alarm_zaiko]"
                                                        + " ,[kbn_jit]"
                                                        + " ,[kbn_rat_shomi]"
                                                        + " ,[cd_route]"
                                                        + " ,[cd_tani_a]"
                                                        + " ,[cd_tani_b]"
                                                        + " ,[cd_kamoku_b]"
                                                        + " ,[kbn_genryo_shiyo]"
                                                        + " ,[cd_basho_kowake]"
                                                        + " ,[kin_tanka_nisugata]"
                                                        + " ,[kbn_conv_1]"
                                                        + " ,[kbn_conv_2]"
                                                        + " ,[kbn_conv_3]"
                                                        + " ,[kbn_shokuba]"
                                                        + " ,[su_kanzan_kama]"
                                                        + ", [kbn_sakusei])"
                                                     ))
                    + " VALUES"
                            + "( @cd_hin"
                            + ", @nm_hin"
                            + ", @nm_seihin_r"
                            + ", ''" //cd_tani_jyuchu
                            + ", ''" //cd_tani_shiyo
                            + ", ''" //cd_niuke
                            + ", ''" //budomari
                            + ", '1'" //hijyu
                            + ", ''" //kbn_jyotai
                            + ", ''" //su_saitei
                            + ", ''" //tani_shomi
                            + ", ''" //cd_hini
                            + ", @cd_haigo"
                            + ", '1'" //no_yusen
                            + ", @cd_haigo_hyoji"
                            + ", @no_yusen_hyoji"
                            + ", '1'" //kbn_seisan
                            + ", '製法支援の配合受信で追加'"// biko
                            + ", '0'" //kbn_seihin
                            + ", '0'" //kbn_kuraire
                            + ", '04'" //kbn_vw
                            + ", '0'" //flg_sakujyo
                            + ", '0'" //flg_mishiyo
                            + ", @dateNow" //dt_toroku
                            + ", @dateNow" //dt_henko
                            + ", ''" //cd_koshin
                            + (kbn_nmacs_kojyo != KubunNmacsKojyo ?
                            (", '1')") : //kbn_sakusei
                            ( ", NULL" //cd_itf
                            + ", '1'" //kbn_kisan_shomi
                            + ", NULL" //kikan_shomi_kaifu2
                            + ", NULL" //tani_shomi_kaifu2
                            + ", NULL" //kbn_hokan_kaifu1
                            + ", NULL" //kbn_hokan_kaifu2
                            + ", NULL" //su_saidai
                            + ", NULL" //kin_tanka_shiire
                            + ", NULL" //cd_kura
                            + ", NULL" //cd_shokuba_hachu
                            + ", NULL" //kin_tanka_zaiko
                            + ", NULL" //kbn_shiyo_tanka
                            + ", NULL" //cd_route_uriage
                            + ", '1'" //kbn_deki
                            + ", '1'" //kbn_kura
                            + ", '0'" //kbn_auto_kura
                            + ", '0'" //kbn_azuke_kura
                            + ", '0'" //kbn_alarm_zaiko
                            + ", '0'" //kbn_jit
                            + ", NULL" //kbn_rat_shomi
                            + ", NULL" //cd_route
                            + ", '01'" //cd_tani_a
                            + ", ''" //cd_tani_b
                            + ", ''" //cd_kamoku_b
                            + ", ''" //kbn_genryo_shiyo
                            + ", ''" //cd_basho_kowake
                            + ", NULL" //kin_tanka_nisugata
                            + ", NULL" //kbn_conv_1
                            + ", NULL" //kbn_conv_2
                            + ", NULL" //kbn_conv_3
                            + ", NULL" //kbn_shokuba
                            + ", NULL" //su_kanzan_kama
                            + ", '1')")); //kbn_sakusei


                    var parameters = new object[]
                    {
                        new SqlParameter("@cd_hin", SqlDbType.VarChar, 13) { Value = (object)item.cd_seihin ?? DBNull.Value },
                        new SqlParameter("@nm_hin", SqlDbType.VarChar, 60) { Value = (object)item.nm_seihin ?? DBNull.Value },
                        new SqlParameter("@nm_hin_r", SqlDbType.VarChar, 20) { Value = (object)(item.nm_seihin == null ? "" : item.nm_seihin) ?? DBNull.Value },
                        new SqlParameter("@cd_haigo", SqlDbType.VarChar, 13) { Value = (object)haigoCodeSeihin ?? DBNull.Value},
                        new SqlParameter("@cd_haigo_hyoji", SqlDbType.VarChar, 13) { Value = (object)item.cd_haigo_new ?? DBNull.Value },
                        new SqlParameter("@no_yusen_hyoji", SqlDbType.Int) { Value = (object)item.no_yusen ?? DBNull.Value },
                    };

                    foodproc.Database.ExecuteSqlCommand(queryInsertSeihin, parameters);

                }
                else
                {
                    var queryUpdateSeihin = "DECLARE @dateNow DATETIME = GETDATE();"
                                         + " UPDATE ma_seihin"
                                         + " SET "
                                         + " no_yusen_hyoji = @no_yusen"
                                         + " ,cd_haigo_hyoji = @cd_haigo_new"
                                         + " , dt_henko = @dateNow"
                                         + " , cd_koshin = @userCode"
                                         + " WHERE cd_hin = @cd_seihin";

                    var parameters = new object[]
                    {
                        new SqlParameter("@no_yusen", SqlDbType.Int) { Value = (object)item.no_yusen ?? DBNull.Value },
                        new SqlParameter("@cd_haigo_new", SqlDbType.VarChar, 13) { Value = (object)item.cd_haigo_new ?? DBNull.Value  },
                        new SqlParameter("@userCode", SqlDbType.VarChar, 10) { Value = userName },
                        new SqlParameter("@cd_seihin", SqlDbType.VarChar, 13) { Value = (object)item.cd_seihin ?? DBNull.Value  }
                    };

                    foodproc.Database.ExecuteSqlCommand(queryUpdateSeihin, parameters);

                }

                //Insert/Update ma_konpo
                UpdateKonpo(foodproc, item);

            }
        }


        /// <summary>
        /// Update (ma_konpo) table
        /// </summary>
        /// <param name="value"></param>
        /// <returns></returns>
        //S207504対応のため、rowguidをインサート項目から外す
        public void UpdateKonpo(FOODPROCSEntities foodproc, SeihinSeihoData seihin)
        {
            var queryKonpo = "SELECT cd_hin"
                            + " FROM ma_konpo"
                            + " WHERE cd_hin = @cd_sehin"
                            + " AND flg_sakujyo = @FlgFlase";
            var konpoData = foodproc.Database.SqlQuery<string>(queryKonpo, new SqlParameter("@cd_sehin", seihin.cd_seihin)
                                                                       , new SqlParameter("@FlgFlase", FlgFlase)).FirstOrDefault();

            if (konpoData == null)
            {
                var queryInsertKonpo = " DECLARE @dateNow DATETIME = GETDATE();"
                                            + " INSERT INTO [dbo].[ma_konpo]"
                                               + " ([cd_hin]"
                                               + " ,[nisugata_qty]"
                                               + " ,[nisugata_hyoji]"
                                               + " ,[su_iri]"
                                               + " ,[qty]"
                                               + " ,[kbn_hako]"
                                               + " ,[kbn_data_convert]"
                                               + " ,[kbn_ksys_denso]"
                                               + " ,[flg_sakujyo]"
                                               + " ,[flg_mishiyo]"
                                               + " ,[dt_toroku]"
                                               + " ,[dt_henko]"
                                               + " ,[cd_koshin]"
                                              // + " ,[rowguid]"
                                               + " ,[cd_tani_keiri])"
                                         + " VALUES"
                                               + " ( @cd_seihin" //cd_hin
                                               + " , NULL" //nisugata_qty
                                               + " , @nisugata_hyoji" //nisugata_hyoji
                                               + " , @su_iri" //su_iri
                                               + " , @qty" //qty
                                               + " , 0" //kbn_hako
                                               + " , 0" //kbn_data_convert
                                               + " , 0" //kbn_ksys_denso
                                               + " , 0" //flg_sakujyo
                                               + " , 0" //flg_mishiyo
                                               + " , @dateNow" //dt_toroku
                                               + " , @dateNow" //dt_henko
                                               + " , ''" //cd_koshin
                                              // + " , NEWID()" //rowguid
                                               + " , NULL)"; //cd_tani_keiri


                string userName = decimal.Parse(UserInfo.GetUserNameFromIdentity(this.User.Identity)).ToString();
                var parameters = new object[]
                {
                    new SqlParameter("@cd_seihin", SqlDbType.VarChar, 13) { Value = seihin.cd_seihin },
                    new SqlParameter("@nisugata_hyoji", SqlDbType.VarChar, 26) { Value = seihin.nisugata_hyoji },
                    new SqlParameter("@su_iri", SqlDbType.Int) { Value = seihin.su_iri },
                    new SqlParameter("@qty", SqlDbType.Int) { Value = seihin.qty }
                };
                foodproc.Database.ExecuteSqlCommand(queryInsertKonpo, parameters);
            }
            else
            {
                var queryUpdateKonpo = " DECLARE @dateNow DATETIME = GETDATE();"
                                        + " UPDATE ma_konpo"
                                        + " SET "
                                        + " dt_henko = @dateNow"
                                        + " ,cd_koshin = @userCode"
                                        + " WHERE cd_hin = @cd_seihin"
                                        + " AND flg_sakujyo = @FlgFlase";


                string userName = decimal.Parse(UserInfo.GetUserNameFromIdentity(this.User.Identity)).ToString();
                var parameters = new object[]
                {
                    new SqlParameter("@cd_seihin", SqlDbType.VarChar, 13) { Value = seihin.cd_seihin },
                    new SqlParameter("@userCode", SqlDbType.VarChar, 10) { Value = userName },
                    new SqlParameter("@FlgFlase", SqlDbType.VarChar, 1) { Value = FlgFlase }
                };
                foodproc.Database.ExecuteSqlCommand(queryUpdateKonpo, parameters);

            }
        }

        /// <summary>
        /// Get kbn_nmac_kojyo
        /// </summary>
        /// <returns>Get kbn_nmac_kojyo</returns>
        private byte? GetKubunNmacKojyo(int cd_kaisha, int cd_kojyo)
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
        /// Update (ma_konpo) table
        /// </summary>
        /// <param name="value"></param>
        /// <returns></returns>
        public void UpdateHeaderStatus(ShohinKaihatsuEntities context, sp_shohinkaihatsu_search_207_Result item)
        {
            var JushinchuStatus = byte.Parse(Properties.Resources.JushinchuStatus);
            var KakuninStatus = byte.Parse(Properties.Resources.KakuninStatus);
            var cd_haigo = decimal.Parse(item.cd_haigo);
            var seihoDenso = context.ma_haigo_header.Where(m => m.cd_haigo == cd_haigo && m.status == JushinchuStatus).FirstOrDefault();
            if (seihoDenso != null)
            {

                seihoDenso.status = KakuninStatus;

                context.ma_haigo_header.Attach(seihoDenso);
                context.Entry<ma_haigo_header>(seihoDenso).State = EntityState.Modified;
            }

            context.SaveChanges();

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

        /// <summary>
        /// Check the waiting queue 
        /// FIFO
        /// </summary>
        /// <param name="processID"></param>
        private void preventConflict(processID processID)
        {
            // Prevent conflict when insert the new seiho
            prioritySaveProcessing.Add(processID);
            // Check if current process is the first of priority
            while (prioritySaveProcessing[0].ID != processID.ID)
            {
                processID.waitTime += processID.singleWaitingTime;
                if (processID.waitTime > processID.maxWaiting)
                {
                    throw (new Exception("Request timeout. Many user in processing. Please try again later."));
                }
                Thread.Sleep(processID.singleWaitingTime);
            }
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

    public class ParaHaigoJyushinSave
    {
        public ChangeSet<sp_shohinkaihatsu_search_207_Result> DataUpdate { get; set; }
        public byte? su_code_standard { get; set; }
        public int cd_kaisha { get; set; }
        public int cd_kojyo { get; set; }
    }

    //Para search
    public class HaigoJyushinPara
    {
        public int cd_kaisha { get; set; }
        public int cd_kojyo { get; set; }
        public short kbn_sort { get; set; }
        public DateTime? dt_denso_toroku_from { get; set; }
        public DateTime? dt_denso_toroku_to { get; set; }
        public short? flg_denso_jyotai { get; set; }
        public byte su_code_standard { get; set; }

        public short skip { get; set; }
        public short top { get; set; }
    }

    //Get ma_genshizai
    public class genshizai_data
    {
        public string cd_hin { get; set; }
        public string kbn_jyotai { get; set; }
    }

    //Get ma_seizo_line
    public class SeizoLineData
    {
        public string cd_haigo { get; set; }
        public string cd_haigo_new { get; set; }
        public string cd_line { get; set; }
        public int no_yusen { get; set; }
    }

    //Get ma_seihin_seiho
    public class SeihinSeihoData
    {
        public string cd_seihin { get; set; }
        public string nm_seihin { get; set; }
        public string nisugata_hyoji { get; set; }
        public int su_iri { get; set; }
        public int qty { get; set; }
        public int? no_yusen { get; set; }
        public decimal? cd_haigo_seihin { get; set; }
        public string cd_haigo_new { get; set; }
    }
    #endregion
}
