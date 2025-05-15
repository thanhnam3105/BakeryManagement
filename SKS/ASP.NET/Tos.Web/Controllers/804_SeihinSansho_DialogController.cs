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

namespace Tos.Web.Controllers
{
    public class _804_SeihinSansho_DialogController : ApiController
    {
        #region "Controllerで公開するAPI"
        
        /// <summary>
        /// 製品参照-検索処理
        /// </summary>
        /// <returns></returns>
        public StoredProcedureResult<sp_shohinkaihatsu_search_804_Result> Get([FromUri]SeihinSanshoSearchRequest Conditions)
        {
            using (ShohinKaihatsuEntities context = new ShohinKaihatsuEntities()) 
            {
                StoredProcedureResult<sp_shohinkaihatsu_search_804_Result> result = new StoredProcedureResult<sp_shohinkaihatsu_search_804_Result>();
                result.Items = context.sp_shohinkaihatsu_search_804(Conditions.lst_haigo).ToList();
                return result;
            }
        }
        #endregion

        #region "Get excel data"
        /// <summary>
        /// Get seihin data for Excel
        /// </summary>
        /// <param name="Conditions"></param>
        /// <returns></returns>
        public static List<sp_shohinkaihatsu_search_804_Result> getExcelData(SeihoBunshoExcelCondition Conditions)
        {
            using (ShohinKaihatsuEntities context = new ShohinKaihatsuEntities())
            {
                List<sp_shohinkaihatsu_search_804_Result> result = new List<sp_shohinkaihatsu_search_804_Result>();
                result = context.sp_shohinkaihatsu_search_804(Conditions.lst_haigo).ToList();
                return result;
            }
        }
        #endregion

    }



    #region "ControllerのAPIで利用するリクエスト・レスポンスクラスです"

    /// <summary>
    /// 製品参照 - Search request param
    /// </summary>
    public class SeihinSanshoSearchRequest
    {
        public string lst_haigo { get; set; }
    }

    #endregion
}
