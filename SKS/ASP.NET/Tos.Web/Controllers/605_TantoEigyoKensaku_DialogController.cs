using System;
using System.Collections.Generic;
using System.Data.Entity.Infrastructure;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web;
using System.Web.Http;
using System.Web.Http.OData.Query;
// TODO:DbContextの名前空間を指定します。
using Tos.Web.Data;
using Tos.Web.Controllers;
using System.Web.Http.OData;
using System.Data.Common;
using System.Data.Objects;

namespace Tos.Web.Controllers
{
    //created from 【HeaderDetailController(Ver1.7)】 Template
    public class _605_TantoEigyoKensaku_DialogController : ApiController
    {
        #region "Controllerで公開するAPI"

        /// <summary>
        /// パラメータで受け渡されたheaderのキー項目をもとにheaderとdetailの情報を取得します。
        /// </summary>
        /// <param name="no_seq">headerのキー項目</param>
        /// <returns>ChangeResponse</returns>
        public List<Kaisha> GetKaisha()
        {
            // TODO:header情報を管理しているDbContextとheader,detailの型を指定します。

             using (ShohinKaihatsuEntities context = new ShohinKaihatsuEntities())
             {
                 context.Configuration.ProxyCreationEnabled = false;
                 
                 // header検索
                  var result = (from busho in context.ma_busho
                               join kaisha_temp in context.ma_kaisha on busho.cd_kaisha equals kaisha_temp.cd_kaisha into kaisa_temp_
                               from kaisha in kaisa_temp_.DefaultIfEmpty()
                               where busho.flg_eigyo == 1
                         select new Kaisha {
                             cd_kaisha= busho.cd_kaisha,
                             nm_kaisha= kaisha == null ? "" :  (kaisha.nm_kaisha == null ? "" : kaisha.nm_kaisha)
                         }).Distinct().OrderBy(x => x.cd_kaisha).ToList();
                 
                 return result;
             }
        }

        

        /// <summary>
        /// パラメータで受け渡された 値に該当するすべてのheader情報を StoredProcedure を利用して取得します。
        /// </summary>
        /// <param name="options"></param>
        /// <returns>header情報</returns>
        public StoredProcedureResult<sp_shohinkaihatsu_search_605_Result> Get([FromUri] Infor param)
        {
            // TODO:header情報を管理しているDbContextとheaderの型を指定します。

             ShohinKaihatsuEntities context = new ShohinKaihatsuEntities();
             context.Configuration.ProxyCreationEnabled = false;

             //TODO: DbContext のメソッドとしてストアドプロシージャを呼び出します。※ストアドのアウトプットパラメータとしてcountが返却される前提です

             StoredProcedureResult<sp_shohinkaihatsu_search_605_Result> result = new StoredProcedureResult<sp_shohinkaihatsu_search_605_Result>();
             result.Items = context.sp_shohinkaihatsu_search_605(param.nm_user
                                                                , param.cd_kaisha
                                                                ,param.cd_busho).ToList();
             result.Count = result.Items.Count();

             //TODO: DbContext のメソッドとしてデータカウントを取得するストアドプロシージャを呼び出します。
             return result;
        }

        public class Kaisha
        {
            public Nullable<int> cd_kaisha { get; set; }
            public string nm_kaisha { get; set; }
        }

        public class Infor
        {
            public Nullable<int> cd_kaisha { get; set; }
            public Nullable<int> cd_busho{ get; set; }
            public string nm_user { get; set; }
        }

        #endregion
    }
}
