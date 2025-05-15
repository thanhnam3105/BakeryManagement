using System.Collections.Generic;
using System.Linq;
using System.Web.Http;
//using System.Web.Http.OData.Query;
// TODO:DbContextの名前空間を指定します。
using Tos.Web.Data;
//using System.Web.Http.OData;

namespace Tos.Web.Controllers
{
    //created from 【HeaderDetailController(Ver1.7)】 Template
    public class ShishakuData_TokuseiChi_Tab_Controller : ApiController
    {
        #region "Controllerで公開するAPI"

        /// <summary>
        /// パラメータで受け渡されたheaderのキー項目をもとにheaderとdetailの情報を取得します。
        /// </summary>
        /// <param name="no_seq">headerのキー項目</param>
        /// <returns>ChangeResponse</returns>
        public ShishakuHyoTokuseichiSearchResponse Get([FromUri] paramSearchGenryo paraSearch)
        {

            //return sampleItem;
            // TODO:header情報を管理しているDbContextとheader,detailの型を指定します。

            using (ShohinKaihatsuEntities context = new ShohinKaihatsuEntities())
            {
                context.Configuration.ProxyCreationEnabled = false;

                ShishakuHyoTokuseichiSearchResponse SearchResponse = new ShishakuHyoTokuseichiSearchResponse();
                //get data shisaku
                SearchResponse.shisaku = context.tr_shisaku.Where(x => x.cd_shain == paraSearch.cd_shain && x.nen == paraSearch.nen && x.no_oi == paraSearch.no_oi).OrderBy(x => x.sort_shisaku).ToList();                
                // TODO: 上記実装を行った後に下の行は削除します
                return SearchResponse;
            }
        }
        #endregion
    }

    #region "ControllerのAPIで利用するリクエスト・レスポンスクラスです"

    public class ChangeResponseShishakuHyoShisakuhyo
    {
        public ChangeSet<object/*TODO:headerの型を指定します*/> Header { get; set; }

        public ChangeSet<object/*TODO:detailの型を指定します*/> Detail { get; set; }
    }

    /// <summary>
    /// 
    /// </summary>
    public class ShishakuHyoTokuseichiSearchResponse
    {
        public ShishakuHyoTokuseichiSearchResponse()
        {
            this.shisaku = new List<tr_shisaku>();
        }
        public List<tr_shisaku> shisaku { get; set; }
    }

    /// <summary>
    /// parameter search
    /// </summary>
    public class paramSearchGenryo
    {
        public decimal cd_shain { get; set; }
        public decimal nen { get; set; }
        public decimal no_oi { get; set; }
    }
    #endregion
}
