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

    public class _300_SeihoBunshoSakusei_Hyoshi_TabController : ApiController
    {
        #region "Const"
        // PAGE MODE
        private const string NEW = "new";
        private const string NEW_COPY = "new_copy";
        private const string EDIT = "edit";
        private const string EDIT_COPY = "edit_copy";
        private const string VIEW = "view";
        // SP MODE
        private const int SP_NEW_MODE = 1;
        private const int SP_EDIT_MODE = 2;
        #endregion

        #region "Controllerで公開するAPI"

        /// <summary>
        /// 検索処理
        /// </summary>
        /// <param name="Conditions">Request param</param>
        /// <returns>Tab9_商品開発履歴表</returns>
        public HyoshiTabSearchResponse Get([FromUri] HyoshiTabSearchRequest Conditions)
        {
            using (ShohinKaihatsuEntities context = new ShohinKaihatsuEntities())
            {
                HyoshiTabSearchResponse result = new HyoshiTabSearchResponse();
                switch (Conditions.mode)
                {
                    //新規
                    case NEW:
                        result.currentData = context.sp_shohinkaihatsu_search_300_hyoshi(Conditions.no_seiho, Conditions.kbn_haigo, Conditions.kbn_tokisaki, Conditions.kbn_brand, SP_NEW_MODE).FirstOrDefault();
                        break;
                    case NEW_COPY:
                        result.currentData = context.sp_shohinkaihatsu_search_300_hyoshi(Conditions.no_seiho, Conditions.kbn_haigo, Conditions.kbn_tokisaki, Conditions.kbn_brand, SP_NEW_MODE).FirstOrDefault();
                        result.copyData = context.ma_seiho_bunsho_hyoshi.Where(x => x.no_seiho == Conditions.no_seiho_copy).FirstOrDefault();
                        break;
                    //編集
                    case EDIT:
                    //Read-only
                    case VIEW:
                        result.currentData = context.sp_shohinkaihatsu_search_300_hyoshi(Conditions.no_seiho, Conditions.kbn_haigo, Conditions.kbn_tokisaki, Conditions.kbn_brand, SP_EDIT_MODE).FirstOrDefault();
                        break;
                    //編集 - Copy
                    case EDIT_COPY:
                        result.currentData = context.sp_shohinkaihatsu_search_300_hyoshi(Conditions.no_seiho, Conditions.kbn_haigo, Conditions.kbn_tokisaki, Conditions.kbn_brand, SP_EDIT_MODE).FirstOrDefault();
                        result.copyData = context.ma_seiho_bunsho_hyoshi.Where(x => x.no_seiho == Conditions.no_seiho_copy).FirstOrDefault();
                        break;
                }
                return result;
            }
        }
        
        /// <summary>
        /// Reload copy data 
        /// </summary>
        /// <param name="Conditions"></param>
        /// <returns></returns>
        [HttpGet]
        public ma_seiho_bunsho_hyoshi getCopyData([FromUri] HyoshiTabSearchRequest Conditions)
        {
            using (ShohinKaihatsuEntities context = new ShohinKaihatsuEntities())
            {
                return context.ma_seiho_bunsho_hyoshi.Where(x => x.no_seiho == Conditions.no_seiho_copy).FirstOrDefault();
            }
        }

        #endregion

        #region "Get excel data"

        /// <summary>
        /// Get all value in TAB 1 for excel
        /// </summary>
        /// <param name="Conditions"></param>
        /// <returns></returns>
        public static HyoshiExcelData getExcelData(SeihoBunshoExcelCondition Conditions, bool isGetAll = true)
        {
            HyoshiExcelData result = new HyoshiExcelData();
            using (ShohinKaihatsuEntities context = new ShohinKaihatsuEntities())
            {
                result.singleData = new ma_seiho_bunsho_hyoshi_EXT();
                // 表紙
                var hyoshiData = context.sp_shohinkaihatsu_search_300_hyoshi(Conditions.no_seiho, Conditions.kbn_haigo, Conditions.kbn_tokisaki, Conditions.kbn_brand, SP_EDIT_MODE).FirstOrDefault();
                if (hyoshiData != null)
                {
                    DataCopier.ReFill(hyoshiData, result.singleData);
                }
                // 得意先名
                result.singleData.nm_tokisaki = (from x in context.ma_literal
                                                 where x.cd_category == Conditions.kbn_tokisaki && x.cd_literal == result.singleData.cd_tokisaki
                                                 select x.nm_literal).FirstOrDefault();
                // ブランド名
                result.singleData.nm_brand = (from x in context.ma_literal
                                              where x.cd_category == Conditions.kbn_brand && x.cd_literal == result.singleData.cd_brand
                                              select x.nm_literal).FirstOrDefault();
                // 製品種類
                string cd_hin_syurui = Conditions.no_seiho.Substring(5, 1);
                result.singleData.nm_hin_syurui = (from x in context.ma_hin_syurui
                                                   where x.cd_hin_syurui == cd_hin_syurui
                                                   select x.nm_hin_syurui).FirstOrDefault();
            }
            if (isGetAll)
            {
                result.arrayData = _804_SeihinSansho_DialogController.getExcelData(Conditions);
            }
            return result;
        }
        #endregion

    }

    #region "ControllerのAPIで利用するリクエスト・レスポンスクラスです"

    /// <summary>
    /// Hyoshi request param
    /// </summary>
    public class HyoshiTabSearchRequest
    {
        public string no_seiho { get; set; }
        public string no_seiho_copy { get; set; }
        public int kbn_haigo { get; set; }
        public string kbn_tokisaki { get; set; }
        public string kbn_brand { get; set; }
        public string mode { get; set; }

    }

    /// <summary>
    /// Page search response data
    /// </summary>
    public class HyoshiTabSearchResponse
    {
        public sp_shohinkaihatsu_search_300_hyoshi_Result currentData { get; set; }
        public ma_seiho_bunsho_hyoshi copyData  { get; set; }
    }

    public class ma_seiho_bunsho_hyoshi_EXT : sp_shohinkaihatsu_search_300_hyoshi_Result
    {
        public string nm_tokisaki { get; set; }
        public string nm_brand { get; set; }
        public string nm_hin_syurui { get; set; }
    }

    /// <summary>
    /// Excel data
    /// </summary>
    public class HyoshiExcelData
    {
        public ma_seiho_bunsho_hyoshi_EXT singleData { get; set; }
        public List<sp_shohinkaihatsu_search_804_Result> arrayData { get; set; }
    }

    #endregion
}
