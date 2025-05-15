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

    public class _300_SeihoBunshoSakusei_RirekiHyo_TabController : ApiController
    {
        #region "Const"

        #endregion

        #region "Controllerで公開するAPI"

        /// <summary>
        /// 検索処理
        /// </summary>
        /// <param name="Conditions">Request param</param>
        /// <returns>Tab9_商品開発履歴表</returns>
        public StoredProcedureResult<sp_shohinkaihatsu_search_300_rirekihyo_Result> Get([FromUri] RirekiHyoTabSearchRequest Conditions)
        {
            using (ShohinKaihatsuEntities context = new ShohinKaihatsuEntities())
            {
                StoredProcedureResult<sp_shohinkaihatsu_search_300_rirekihyo_Result> result = new StoredProcedureResult<sp_shohinkaihatsu_search_300_rirekihyo_Result>();
                result.Items = context.sp_shohinkaihatsu_search_300_rirekihyo(Conditions.no_seiho, Conditions.kbn_haigo).ToList();
                result.Count = result.Items.Count();
                return result;
            }
        }

        #endregion

        
    }

    #region "ControllerのAPIで利用するリクエスト・レスポンスクラスです"

    /// <summary>
    /// Rireki hyo request param
    /// </summary>
    public class RirekiHyoTabSearchRequest
    {
        public string no_seiho { get; set; }
        public int kbn_haigo { get; set; }
        public int mode { get; set; }

    }
    #endregion
}
