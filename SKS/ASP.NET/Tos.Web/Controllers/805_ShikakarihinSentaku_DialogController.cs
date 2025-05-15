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
    public class _805_ShikakarihinSentaku_DialogController : ApiController
    {
        #region "Controllerで公開するAPI"
        
        /// <summary>
        /// 仕掛品選択-検索処理
        /// </summary>
        /// <returns>list of src_haigo, list of desc_haigo</returns>
        public ShikakarihinSentakuSearchResponse Get([FromUri]ShikakarihinSentakuSearchRequest Conditions)
        {
            using (ShohinKaihatsuEntities context = new ShohinKaihatsuEntities()) 
            {
                ShikakarihinSentakuSearchResponse result = new ShikakarihinSentakuSearchResponse();
                result.src_haigo = context.sp_shohinkaihatsu_search_300_seizokoteizu_header(null, Conditions.no_seiho_copy, Conditions.kbn_haigo, Conditions.kbn_shikakari, Conditions.loop_count).ToList();
                result.desc_haigo = context.sp_shohinkaihatsu_search_300_seizokoteizu_header(null, Conditions.no_seiho_sakusei, Conditions.kbn_haigo, Conditions.kbn_shikakari, Conditions.loop_count).ToList();
                return result;
            }
        }
        #endregion

    }



    #region "ControllerのAPIで利用するリクエスト・レスポンスクラスです"

    public class ShikakarihinSentakuSearchResponse
    {
        public List<sp_shohinkaihatsu_search_300_seizokoteizu_header_Result> src_haigo { get; set; }
        public List<sp_shohinkaihatsu_search_300_seizokoteizu_header_Result> desc_haigo { get; set; }
    }

    public class ShikakarihinSentakuSearchRequest
    {
        public string no_seiho_copy { get; set; }
        public string no_seiho_sakusei { get; set; }
        public int kbn_haigo { get; set; }
        public int kbn_shikakari { get; set; }
        public int loop_count { get; set; }
    }

    #endregion
}
