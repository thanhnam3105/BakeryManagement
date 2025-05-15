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
using Tos.Web.Properties;

namespace Tos.Web.Controllers
{
    //created from 【SearchInputController(Ver1.7)】 Template
    public class TantoshaIchiranEigyo_DialogController : ApiController
    {
        #region "Controllerで公開するAPI"

        /// <summary>
        /// GetData
        /// </summary>
        /// <param name="param"></param>
        /// <returns> sp_shohinkaihatsu_search_011_Result </returns>
        public StoredProcedureResult<sp_shohinkaihatsu_search_501_Result> GetData([FromUri] DialogParamSearch param)
        {
            // TODO:target情報を管理しているDbContextとtargetの型を指定します。

            ShohinKaihatsuEntities context = new ShohinKaihatsuEntities();
            context.Configuration.ProxyCreationEnabled = false;

            //TODO: DbContext のメソッドとしてストアドプロシージャを呼び出します。※ストアドのアウトプットパラメータとしてcountが返却される前提です
            //ObjectParameter count = new ObjectParameter("rowcount", typeof(int));
            StoredProcedureResult<sp_shohinkaihatsu_search_501_Result> result = new StoredProcedureResult<sp_shohinkaihatsu_search_501_Result>();
            result.Items = context.sp_shohinkaihatsu_search_501(
                                    param.id_user, param.nm_user, param.cd_kaisha, param.cd_busho,
                                    short.Parse(Resources.kengen_eigyo_48), short.Parse(Resources.kengen_eigyo_114), short.Parse(Resources.kengen_eigyo_50)
                           ).ToList();

            if (result.Items.Count() > 0)
            {

                result.Count = result.Items.Count();
            }

            // TODO: DbContext のメソッドとしてデータカウントを取得するストアドプロシージャを呼び出します。
            return result;
        }

        /// <summary>
        /// パラメータで受け渡された target のキー項目をもとに target情報を取得します。
        /// </summary>
        /// <param name="id"> target のキー項目</param>
        /// <returns> target 情報</returns>
        public object Get(int id)
        {
            // TODO: target 情報を管理しているDbContextと target の型を指定します。

            // using (/*TODO: target 情報を管理しているDbContextを指定します*/ context = new /*TODO: target 情報を管理しているDbContextを指定します*/())
            // {
            //     context.Configuration.ProxyCreationEnabled = false;

            //     return (from m in context./*TODO: target の型を指定します*/
            //             where m./*TODO:headerのキー項目を指定します*/ == id
            //             select m).SingleOrDefault();
            // }

            // TODO: 上記実装を行った後に下の行は削除します
            return null;
        }  

        #endregion

    }

    #region "ControllerのAPIで利用するリクエスト・レスポンスクラスです"

    public class SearchChangeResponse
    {
        public SearchChangeResponse()
        {
            this.Detail = new List<object/*TODO: target の型を指定します*/>();
        }

        public List<object/*TODO: target の型を指定します*/> Detail { get; set; }
    }

    /// <summary>
    /// param search
    /// </summary>
    public class DialogParamSearch
    {
        public Nullable<decimal> id_user { get; set; }
        public string nm_user { get; set; }
        public Nullable<int> cd_kaisha { get; set; }
        public Nullable<int> cd_busho { get; set; }
    }

    #endregion
}
