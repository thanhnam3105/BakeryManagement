using System;
using System.Collections.Generic;
using System.Data.Entity.Infrastructure;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web;
using System.Web.Http;
using System.Web.Http.OData.Query;
using Tos.Web.Data;
using Tos.Web.Controllers;
using System.Web.Http.OData;
using System.Data.Common;
using System.Data.Objects;

namespace Tos.Web.Controllers
{

    public class _300_SeihoBunshoSakusei_HaigoHyo_TabController : ApiController
    {

        #region "Controllerで公開するAPI"

        /// <summary>
        /// 検索処理
        /// </summary>
        /// <param name="no_seiho">画面．製法番号</param>
        /// <param name="cd_haigo">画面．本配合コード</param>
        /// <param name="kbn_shikakari">仕掛品	[4]</param>
        /// <returns>ヘッダー情報の検索処理, 明細情報の検索処理, Comboboxs Data</returns>
        public HaigoHyoSearchRespone Get([FromUri] HaigoHyoSearchRequest Conditions)
        {
            using (ShohinKaihatsuEntities context = new ShohinKaihatsuEntities())
            {
                HaigoHyoSearchRespone result = new HaigoHyoSearchRespone();
                context.Configuration.ProxyCreationEnabled = false;
                // ヘッダー情報の検索処理
                var lstHaigo = context.sp_shohinkaihatsu_search_300_seizokoteizu_header(
                                                                                            null, 
                                                                                            Conditions.no_seiho,
                                                                                            Conditions.kbn_haigo,
                                                                                            Conditions.kbn_shikakari,
                                                                                            Conditions.loop_count
                                                                                         ).ToList();
                // 明細情報の検索処理
                List<HaigoHyoSearchRespone.comboboxHaigo> lstCDHaigo = new List<HaigoHyoSearchRespone.comboboxHaigo>();
                List<decimal?> lstStrHaigo = new List<decimal?>();
                for (var i = 0; i < lstHaigo.Count; i++)
                {
                    lstCDHaigo.Add(new HaigoHyoSearchRespone.comboboxHaigo {
                        cd_haigo = lstHaigo[i].cd_hin,
                        flg_mishiyo = lstHaigo[i].flg_mishiyo
                    });
                    lstStrHaigo.Add(lstHaigo[i].cd_hin);
                }
                string strHaigo = String.Join(",", lstStrHaigo.ToArray());
                var lstHaigoHeader = context.sp_shohinkaihatsu_search_300_haigohyo_header(strHaigo, Conditions.cd_literal_status).ToList();
                var lstHaigoDetail = context.sp_shohinkaihatsu_search_300_haigohyo_detail(
                                                                                              strHaigo, 
                                                                                              Conditions.kbn_genryo, 
                                                                                              Conditions.kbn_haigo, 
                                                                                              Conditions.kbn_shikakari, 
                                                                                              Conditions.kbn_maeshori, 
                                                                                              Conditions.kbn_sagyo, 
                                                                                              Conditions.su_code_standard, 
                                                                                              Conditions.kbn_tani_kg, 
                                                                                              Conditions.kbn_tani_l,
                                                                                              Conditions.kbn_gam,
                                                                                              Conditions.kbn_shikakari_kaihatsu,
                                                                                              Conditions.kbn_shikakari_FP,
                                                                                              Conditions.flg_mishiyo
                                                                                          ).ToList();
                result.lstHaigo = lstCDHaigo;
                result.lstHaigoHeader = lstHaigoHeader;
                result.lstHaigoDetail = lstHaigoDetail;
                
                return result;
            }
        }

        #endregion

        #region "Controllerで公開するAPI"

        public static HaigoHyoExcelRespone getExcelData(SeihoBunshoExcelCondition Conditions)
        {
            using (ShohinKaihatsuEntities context = new ShohinKaihatsuEntities())
            {
                HaigoHyoExcelRespone result = new HaigoHyoExcelRespone();
                context.Configuration.ProxyCreationEnabled = false;
                ((IObjectContextAdapter)context).ObjectContext.CommandTimeout = 0;
                // ヘッダー情報の検索処理
                var lstHaigo = context.sp_shohinkaihatsu_search_300_seizokoteizu_header(
                                                                                            null,
                                                                                            Conditions.no_seiho,
                                                                                            Conditions.kbn_haigo,
                                                                                            Conditions.kbn_shikakari,
                                                                                            Conditions.loop_count
                                                                                         ).ToList();
                // 明細情報の検索処理
                List<decimal?> lstStrHaigo = new List<decimal?>();
                for (var i = 0; i < lstHaigo.Count; i++)
                {
                    lstStrHaigo.Add(lstHaigo[i].cd_hin);
                }
                string strHaigo = String.Join(",", lstStrHaigo.ToArray());
                var lstHaigoHeader = context.sp_shohinkaihatsu_search_300_haigohyo_header(strHaigo, Conditions.cd_literal_status).ToList();
                var lstHaigoDetail = context.sp_shohinkaihatsu_search_300_haigohyo_detail(
                                                                                              strHaigo,
                                                                                              Conditions.kbn_genryo,
                                                                                              Conditions.kbn_haigo,
                                                                                              Conditions.kbn_shikakari,
                                                                                              Conditions.kbn_maeshori,
                                                                                              Conditions.kbn_sagyo,
                                                                                              Conditions.su_code_standard,
                                                                                              Conditions.kbn_tani_kg,
                                                                                              Conditions.kbn_tani_l,
                                                                                              Conditions.kbn_gam,
                                                                                              Conditions.kbn_shikakari_kaihatsu,
                                                                                              Conditions.kbn_shikakari_FP,
                                                                                              Conditions.flg_mishiyo
                                                                                          ).ToList();
                if (lstHaigoHeader.Count > 1)
                {
                    string id_session = Conditions.loginCD + DateTime.Now.Ticks.ToString();
                    context.sp_get_haigoList(id_session, Conditions.no_seiho);
                    result.totalData = context.sp_shohinkaihatsu_search_300_haigohyo_excel(id_session, Conditions.kbn_sagyo).ToList();
                }

                result.lstHaigoHeader = lstHaigoHeader;
                result.lstHaigoDetail = lstHaigoDetail;
                return result;
            }
        }
        #endregion
    }

    #region "ControllerのAPIで利用するリクエスト・レスポンスクラスです"

    public class HaigoHyoSearchRequest
    {
        public string no_seiho { get; set; }
        // const
        public int loop_count { get; set; }
        public string cd_literal_status { get; set; }
        public short kbn_genryo { get; set; }
        public short kbn_haigo { get; set; }
        public short kbn_shikakari { get; set; }
        public short kbn_maeshori { get; set; }
        public short kbn_sagyo { get; set; }
        public short su_code_standard { get; set; }
        public string kbn_tani_kg { get; set; }
        public string kbn_tani_l { get; set; }
        public string kbn_gam { get; set; }
        public short kbn_shikakari_kaihatsu { get; set; }
        public short kbn_shikakari_FP { get; set; }
        public bool flg_mishiyo { get; set; }
    }

    // return value for [検索処理]
    public class HaigoHyoSearchRespone
    {
        // list cd_haigo in combobox
        public List<comboboxHaigo> lstHaigo { get; set; }

        // list Haigo header info
        public List<sp_shohinkaihatsu_search_300_haigohyo_header_Result> lstHaigoHeader { get; set; }

        // list Haigo detail info
        public List<sp_shohinkaihatsu_search_300_haigohyo_detail_Result> lstHaigoDetail { get; set; }

        public class comboboxHaigo
        {
            public decimal? cd_haigo { get; set; }
            public int flg_mishiyo { get; set; }
        }
    }

    // return value for EXCEL
    public class HaigoHyoExcelRespone
    {
        // list Haigo header info
        public List<sp_shohinkaihatsu_search_300_haigohyo_header_Result> lstHaigoHeader { get; set; }

        // list Haigo detail info
        public List<sp_shohinkaihatsu_search_300_haigohyo_detail_Result> lstHaigoDetail { get; set; }

        // list Total data
        public List<sp_shohinkaihatsu_search_300_haigohyo_excel_Result> totalData { get; set; }
    }
    #endregion
}
