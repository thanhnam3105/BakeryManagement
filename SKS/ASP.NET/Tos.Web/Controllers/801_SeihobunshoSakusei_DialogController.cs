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
    public class _801_SeihobunshoSakusei_DialogController : ApiController
    {
        #region "Controllerで公開するAPI"
        
        /// <summary>
        /// 製法文書作成方法の選択-検索処理
        /// </summary>
        /// <returns>Buttons display status</returns>
        public SeihobunshoSakuseiSearchResponse Get([FromUri]SeihobunshoSakuseiSearchRequest Conditions)
        {
            using (ShohinKaihatsuEntities context = new ShohinKaihatsuEntities()) 
            {
                SeihobunshoSakuseiSearchResponse result = new SeihobunshoSakuseiSearchResponse();
                result.no_seiho = Conditions.no_seiho;
                result.recipeStatus = 1;
                ma_haigo_header haigo = context.ma_haigo_header.Where(x => x.no_seiho == Conditions.no_seiho).FirstOrDefault();
                if (haigo != null)
                {
                    result.no_seiho_sanko = haigo.no_seiho_sanko;
                    if (result.no_seiho_sanko != null)
                    {
                        ma_seiho_bunsho_hyoshi existBunshoSanko = context.ma_seiho_bunsho_hyoshi.Where(x => x.no_seiho == result.no_seiho_sanko).FirstOrDefault();
                        if (existBunshoSanko != null)
                        {
                            result.methodStatus = 1;
                        }
                    }
                    ma_seiho_bunsho_hyoshi existBunsho = context.ma_seiho_bunsho_hyoshi.Where(x => x.no_seiho == Conditions.no_seiho).FirstOrDefault();
                    if (existBunsho != null)
                    {
                        result.editStatus = 1;
                    }
                    else
                    {
                        result.newStatus = 1;
                    }
                }
                return result;
            }
        }
        #endregion

    }



    #region "ControllerのAPIで利用するリクエスト・レスポンスクラスです"

    /// <summary>
    /// 製法文書作成方法の選択 - Search response
    /// </summary>
    public class SeihobunshoSakuseiSearchResponse
    {
        public string no_seiho { get; set; }
        public string no_seiho_sanko { get; set; }
        public int recipeStatus { get; set; }
        public int methodStatus { get; set; }
        public int newStatus { get; set; }
        public int editStatus { get; set; }
    }

    /// <summary>
    /// 製法文書作成方法の選択 - Search request
    /// </summary>
    public class SeihobunshoSakuseiSearchRequest
    {
        public string no_seiho { get; set; }
    }

    #endregion
}
