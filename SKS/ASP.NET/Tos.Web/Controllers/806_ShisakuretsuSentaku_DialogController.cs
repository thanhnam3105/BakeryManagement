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
    public class _806_ShisakuretsuSentaku_DialogController : ApiController
    {
        #region "Controllerで公開するAPI"
        
        /// <summary>
        /// 試作列選択
        /// </summary>
        /// <returns></returns>
        public StoredProcedureResult<sp_shohinkaihatsu_search_806_Result> Get([FromUri]ShisakuretsuSentakuSearchRequest Conditions)
        {
            using (ShohinKaihatsuEntities context = new ShohinKaihatsuEntities()) 
            {
                StoredProcedureResult<sp_shohinkaihatsu_search_806_Result> result = new StoredProcedureResult<sp_shohinkaihatsu_search_806_Result>();
                result.Items = context.sp_shohinkaihatsu_search_806(Conditions.cd_shain, Conditions.nen, Conditions.no_oi, Conditions.nm_hin, Conditions.yoryo_tani, Conditions.top).ToList();
                if (result.Items.Count() > 0)
                {
                    result.Count = result.Items.First().cnt ?? 0;
                }
                return result;
            }
        }
        #endregion

    }



    #region "ControllerのAPIで利用するリクエスト・レスポンスクラスです"

    /// <summary>
    /// 試作列選択 - Search request parameters
    /// </summary>
    public class ShisakuretsuSentakuSearchRequest
    {
        public decimal? cd_shain { get; set; }
        public decimal? nen { get; set; }
        public decimal? no_oi { get; set; }
        public string nm_hin { get; set; }
        public string yoryo_tani { get; set; }
        public int top { get; set; }
    }

    #endregion
}
