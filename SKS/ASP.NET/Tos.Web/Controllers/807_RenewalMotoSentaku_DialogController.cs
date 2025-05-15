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
using System.Data.Entity.Infrastructure;

namespace Tos.Web.Controllers
{
    public class _807_RenewalMotoSentaku_DialogController : ApiController
    {
        #region "Controllerで公開するAPI"
        
        /// <summary>
        /// 試作列選択
        /// </summary>
        /// <returns></returns>
        public StoredProcedureResult<sp_shohinkaihatsu_search_807_Result> Get([FromUri]RenewalMotoSentakuSearchRequest Conditions)
        {
            using (ShohinKaihatsuEntities context = new ShohinKaihatsuEntities()) 
            {
                context.Configuration.ProxyCreationEnabled = false;
                ((IObjectContextAdapter)context).ObjectContext.CommandTimeout = 0;
                StoredProcedureResult<sp_shohinkaihatsu_search_807_Result> result = new StoredProcedureResult<sp_shohinkaihatsu_search_807_Result>();
                result.Items = context.sp_shohinkaihatsu_search_807(
                                            Conditions.no_seiho_kaisha,
                                            Conditions.no_seiho_shurui,
                                            Conditions.no_seiho_nen,
                                            Conditions.no_seiho_renban,
                                            Conditions.nm_seiho,
                                            Conditions.cd_haigo,
                                            Conditions.nm_haigo,
                                            Conditions.mode,
                                            Conditions.login_kaisha,
                                            Conditions.top
                                            ).ToList();
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
    /// 製法番号選択 - Search request parameters
    /// </summary>
    public class RenewalMotoSentakuSearchRequest
    {
        public string no_seiho_kaisha { get; set; }
        public string no_seiho_shurui { get; set; }
        public string no_seiho_nen { get; set; }
        public string no_seiho_renban { get; set; }
        public string nm_seiho { get; set; }
        public decimal? cd_haigo { get; set; }
        public string nm_haigo { get; set; }
        public int mode { get; set; }
        public string login_kaisha { get; set; }
        public int top { get; set; }
    }

    #endregion
}
