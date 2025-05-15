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

    public class _300_SeihoBunshoSakusei_SeizoKoteizu_TabController : ApiController
    {
        #region "Const"
        private const string NEW = "new";
        private const string NEW_COPY = "new_copy";
        //private const string EDIT = "edit";
        private const string EDIT_COPY = "edit_copy";
        //private const string VIEW = "view";
        #endregion

        #region "Controllerで公開するAPI"

        /// <summary>
        /// Get method is replaced by [Search] method below
        /// Because the get method could be crashed when recive massive of request parameters
        /// </summary>
        /// <returns></returns>
        public string Get()
        {
            return "";
        }

        /// <summary>
        /// 検索処理
        /// </summary>
        /// <param name="no_seiho">画面．製法番号</param>
        /// <param name="cd_haigo">画面．本配合コード</param>
        /// <param name="kbn_shikakari">仕掛品	[4]</param>
        /// <returns>ヘッダー情報の検索処理, 明細情報の検索処理, Comboboxs Data</returns>
        public SeizoKoteizuTabSearchRespone Search([FromBody]SeizoKoteizuTabSearchRequest Conditions)
        {
            using (ShohinKaihatsuEntities context = new ShohinKaihatsuEntities())
            {
                SeizoKoteizuTabSearchRespone result = new SeizoKoteizuTabSearchRespone();
                context.Configuration.ProxyCreationEnabled = false;
                // ヘッダー情報の検索処理
                result.headerData = context.sp_shohinkaihatsu_search_300_seizokoteizu_header(null, Conditions.no_seiho, Conditions.kbn_haigo, Conditions.kbn_shikakari, Conditions.loop_count).ToList();
                // 明細情報の検索処理
                List<decimal?> lstCDHaigo = new List<decimal?>();
                for (var i = 0; i < result.headerData.Count; i++)
                {
                    decimal? cd_haigo = result.headerData[i].cd_hin;
                    // Get general haigo
                    lstCDHaigo.Add(result.headerData[i].cd_hin);
                }
                // Get all detail data
                var currentDetailData = context.ma_seiho_bunsho_koteizu_b.Where(x => x.no_seiho == Conditions.no_seiho).ToList();
                // Wasted data that be changed in page [203]
                result.wastedDetailData = currentDetailData.Where(x => !lstCDHaigo.Contains(x.cd_haigo)).ToList();
                // Get TAB 5 detail
                result.detailData = currentDetailData.Where(x => lstCDHaigo.Contains(x.cd_haigo)).ToList();
                if (Conditions.mode == NEW || Conditions.mode == NEW_COPY || Conditions.mode == EDIT_COPY)
                {
                    // Get kotei data list
                    result.koteiData = context.ma_haigo_meisai.Where(x => lstCDHaigo.Contains(x.cd_haigo) && x.kbn_hin != Properties.Settings.Default.kbn_hin).OrderBy(x => x.no_kotei).ThenBy(x => x.no_tonyu).ToList();
                }
                else {
                    result.koteiData = new List<ma_haigo_meisai>();
                }
                // Get TAB 5 detail copy
                if ((Conditions.mode == NEW_COPY || Conditions.mode == EDIT_COPY) && Conditions.lstHaigoCopy != null && Conditions.lstHaigoCopy.Count > 0)
                {
                    result.detailDataCopy = context.ma_seiho_bunsho_koteizu_b.Where(x => Conditions.lstHaigoCopy.Contains(x.cd_haigo) && x.no_seiho == Conditions.no_seiho_copy).ToList();
                }
                // Comboboxs Data
                result.masterData.kikaiData = (from x in context.ma_seiho_bunsho_koteizu_b
                                               where x.kbn_nyuryoku == Conditions.kbn_kikai || x.kbn_nyuryoku == Conditions.kbn_shikake_kikai
                                               select new SeizoKoteizuTabSearchRespone.masterDat()
                                               {
                                                   nm_hin = x.nm_input_genryo
                                               }).Distinct().OrderBy(x => x.nm_hin).ToList();

                result.masterData.nyuryokuData = (from x in context.ma_seiho_bunsho_koteizu_b
                                                  where x.kbn_nyuryoku == Conditions.kbn_kotei || x.kbn_nyuryoku == Conditions.kbn_shikake_kotei
                                                  select new SeizoKoteizuTabSearchRespone.masterDat()
                                                  {
                                                      nm_hin = x.nm_input_genryo
                                                  }).Distinct().OrderBy(x => x.nm_hin).ToList();

                result.masterData.genryoData = context.sp_shohinkaihatsu_search_300_seizokoteizu_genryo(
                                                                                                            String.Join(",", lstCDHaigo), 
                                                                                                            Conditions.kbn_genryo,
                                                                                                            Conditions.kbn_haigo,
                                                                                                            Conditions.kbn_shikakari,
                                                                                                            Conditions.kbn_maeshori,
                                                                                                            Conditions.kbn_sagyo,
                                                                                                            Conditions.kbn_shikakari_kaihatsu,
                                                                                                            Conditions.kbn_shikakari_FP,
                                                                                                            Conditions.su_code_standard
                                                                                                       ).ToList();
                return result;
            }
        }

        /// <summary>
        /// Reload copy data
        /// </summary>
        /// <param name="Conditions"></param>
        /// <returns></returns>
        [HttpPost]
        public List<ma_seiho_bunsho_koteizu_b> getCopyData([FromBody]SeizoKoteizuTabSearchRequest Conditions)
        { 
            using (ShohinKaihatsuEntities context = new ShohinKaihatsuEntities())
            {
                return context.ma_seiho_bunsho_koteizu_b.Where(x => Conditions.lstHaigoCopy.Contains(x.cd_haigo) && x.no_seiho == Conditions.no_seiho_copy).ToList();
            }
        }

        #endregion

        #region EXCEL export data
       
        public static SeizoKoteizuTabSearchRespone getExcelData(SeihoBunshoExcelCondition Conditions)
        {
            using (ShohinKaihatsuEntities context = new ShohinKaihatsuEntities())
            {
                SeizoKoteizuTabSearchRespone result = new SeizoKoteizuTabSearchRespone();
                // ヘッダー情報の検索処理
                result.headerData = context.sp_shohinkaihatsu_search_300_seizokoteizu_header(null, Conditions.no_seiho, Conditions.kbn_haigo, Conditions.kbn_shikakari, Conditions.loop_count).ToList();
                // 明細情報の検索処理
                List<decimal?> lstCDHaigo = new List<decimal?>();
                for (var i = 0; i < result.headerData.Count; i++)
                {
                    decimal? cd_haigo = result.headerData[i].cd_hin;
                    // Get general haigo
                    lstCDHaigo.Add(result.headerData[i].cd_hin);
                }

                // Get TAB 5 detail
                // Check exist bunsho
                var isExistsTab1Data = context.ma_seiho_bunsho_hyoshi.Where(x => x.no_seiho == Conditions.no_seiho).Count();
                if (isExistsTab1Data > 0)
                {
                    result.detailData = context.ma_seiho_bunsho_koteizu_b.Where(x => lstCDHaigo.Contains(x.cd_haigo) && x.no_seiho == Conditions.no_seiho).ToList();
                }
                else
                {
                    result.koteiData = context.ma_haigo_meisai.Where(x => lstCDHaigo.Contains(x.cd_haigo) && x.kbn_hin != Properties.Settings.Default.kbn_hin).OrderBy(x => x.no_kotei).ThenBy(x => x.no_tonyu).ToList();
                    result.detailData = ConvertBunshoData(result.koteiData, Conditions.no_seiho);
                }
                result.masterData.genryoData = context.sp_shohinkaihatsu_search_300_seizokoteizu_genryo(
                                                                                                           String.Join(",", lstCDHaigo),
                                                                                                            Conditions.kbn_genryo,
                                                                                                            Conditions.kbn_haigo,
                                                                                                            Conditions.kbn_shikakari,
                                                                                                            Conditions.kbn_maeshori,
                                                                                                            Conditions.kbn_sagyo,
                                                                                                            Conditions.kbn_shikakari_kaihatsu,
                                                                                                            Conditions.kbn_shikakari_FP,
                                                                                                            Conditions.su_code_standard
                                                                                                        ).ToList();

                return result;
            }
        }

        /// <summary>
        /// Convert from ma_haigo_meisai to ma_seiho_bunsho_koteizu_b
        /// </summary>
        /// <param name="koteiData"></param>
        /// <returns>tab 5 data list</returns>
        private static List<ma_seiho_bunsho_koteizu_b> ConvertBunshoData(List<ma_haigo_meisai> koteiData, string no_seiho)
        {
            List<ma_seiho_bunsho_koteizu_b> result = new List<ma_seiho_bunsho_koteizu_b>();
            foreach (var item in koteiData)
            {
                ma_seiho_bunsho_koteizu_b bData = new ma_seiho_bunsho_koteizu_b() {
                    no_seiho = no_seiho,
                    cd_haigo = item.cd_haigo,
                    no_seq_sakusei_ho = 0,
                    kbn_nyuryoku = 1,
                    cd_input_genryo = item.cd_hin.ToString(),
                    nm_input_genryo = item.nm_hin
                };
                result.Add(bData);
            }
            return result;
        }
        #endregion

    }

    #region "ControllerのAPIで利用するリクエスト・レスポンスクラスです"

    /// <summary>
    /// Search request parameters
    /// </summary>
    public class SeizoKoteizuTabSearchRequest
    {
        // TAB param
        public string no_seiho { get; set; }
        public string no_seiho_copy { get; set; }
        public decimal cd_haigo { get; set; }
        // Hin kbn
        public int kbn_genryo { get; set; }
        public int kbn_haigo { get; set; }
        public int kbn_shikakari { get; set; }
        public int kbn_maeshori { get; set; }
        public int kbn_sagyo { get; set; }
        // Cbb kbn (kbn_nyuryoku)
        public int kbn_kikai { get; set; }
        public int kbn_shikake_kikai { get; set; }
        public int kbn_kotei { get; set; }
        public int kbn_shikake_kotei { get; set; }
        // kbn_shikakari
        public short kbn_shikakari_kaihatsu { get; set; }
        public short kbn_shikakari_FP { get; set; }
        // SP param
        public int loop_count { get; set; }
        public List<decimal?> lstHaigoCopy { get; set; }
        public string mode { get; set; }
        public int su_code_standard { get; set; }
    }

    // return value for [検索処理]
    public class SeizoKoteizuTabSearchRespone
    {
        // ヘッダー情報の検索処理
        public List<sp_shohinkaihatsu_search_300_seizokoteizu_header_Result> headerData { get; set; }
        // 明細情報の検索処理 - old
        public List<ma_seiho_bunsho_koteizu_b> wastedDetailData { get; set; }
        // 明細情報の検索処理
        public List<ma_seiho_bunsho_koteizu_b> detailData { get; set; }
        // 明細情報の検索処理
        public List<ma_seiho_bunsho_koteizu_b> detailDataCopy { get; set; }
        // Comboboxs Data
        public SeizoKoteizuTabMasterData masterData { get; set; }
        // Kotei data from SR 203
        public List<ma_haigo_meisai> koteiData { get; set; }

        public class SeizoKoteizuTabMasterData
        {
            // 入力種別 = 「原料・仕掛原料」の場合
            public List<sp_shohinkaihatsu_search_300_seizokoteizu_genryo_Result> genryoData { get; set; }
            // 入力種別 = 「機械・仕掛機械」の場合
            public List<masterDat> kikaiData { get; set; }
            // 入力種別 = 「工程・仕掛工程」の場合
            public List<masterDat> nyuryokuData { get; set; }
        }

        public class masterDat
        {
            public decimal cd_haigo { get; set; }
            public decimal cd_hin { get; set; }
            public string nm_hin { get; set; }
        }

        public SeizoKoteizuTabSearchRespone() 
        {
            masterData = new SeizoKoteizuTabMasterData();        
        }
    }
    #endregion
}
