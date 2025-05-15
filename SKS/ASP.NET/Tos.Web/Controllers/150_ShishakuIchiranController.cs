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
using Tos.Web.Data;

namespace Tos.Web.Controllers
{
    //created from 【HeaderDetailController(Ver1.7)】 Template
    public class _150_ShishakuichiranController : ApiController
    {
        #region "Controllerで公開するAPI"

        /// <summary>
        /// パラメータで受け渡された 値に該当するすべてのheader情報を StoredProcedure を利用して取得します。
        /// </summary>
        /// <param name="options"></param>
        /// <returns>header情報</returns>
        public StoredProcedureResult<sp_shishakuichiran_search_150_Result> Get([FromUri]Param_S_Shisaku_Search_150 param)
        {
            // TODO:header情報を管理しているDbContextとheaderの型を指定します。

            using (ShohinKaihatsuEntities context = new ShohinKaihatsuEntities())
            {
                ((IObjectContextAdapter)context).ObjectContext.CommandTimeout = 0;
                context.Configuration.ProxyCreationEnabled = false;

                // TODO: DbContext のメソッドとしてストアドプロシージャを呼び出します。※ストアドのアウトプットパラメータとしてcountが返却される前提です
                //ObjectParameter count = new ObjectParameter("rowcount", typeof(int));
                StoredProcedureResult<sp_shishakuichiran_search_150_Result> result = new StoredProcedureResult<sp_shishakuichiran_search_150_Result>();
                var data = context.sp_shishakuichiran_search_150(param.checkAndOr
                                                        , param.checkHaishi
                                                        , param.cd_shain
                                                        , param.nen
                                                        , param.no_oi
                                                        , param.no_seiho1
                                                        , param.no_seiho2
                                                        , param.no_seiho3
                                                        , param.no_seiho4
                                                        , param.nm_hin
                                                        , param.cd_group
                                                        , param.cd_team
                                                        , param.id_toroku
                                                        , param.nm_genryo
                                                        , param.cd_naiyobunrui
                                                        , param.brand
                                                        , param.id_data
                                                        , param.skip
                                                        , param.top
                                                        ).ToList();
                result.Items = data;
                if (result.Items.Count() > 0)
                    result.Count = int.Parse(data[0].cnt.ToString());
                else
                    result.Count = 0;

                // TODO: DbContext のメソッドとしてデータカウントを取得するストアドプロシージャを呼び出します。
                return result;
            }
            // TODO: 上記実装を行った後に下の行は削除します
            //return null;
        }

        /// <summary>
        /// Get group base on login user
        /// </summary>
        /// <param name="cd_shain"></param>
        /// <param name="cd_kaisha"></param>
        /// <returns></returns>
        [HttpGet]
        public ma_group GetGroupByUser(decimal cd_shain, int cd_kaisha)
        {
            using (ShohinKaihatsuEntities context = new ShohinKaihatsuEntities())
            {
                var group = (from u in context.ma_user
                             from g in context.ma_group
                             where u.cd_group == g.cd_group
                             && u.cd_kaisha == cd_kaisha
                             && u.id_user == cd_shain
                             select g).FirstOrDefault();
                return group;
            }
        }

        #endregion
    }

    #region "ControllerのAPIで利用するリクエスト・レスポンスクラスです"

    public class Param_S_Shisaku_Search_150
    {
        public bool? checkAndOr { get; set; }
        public bool? checkHaishi { get; set; }
        public string cd_shain { get; set; }
        public string nen { get; set; }
        public string no_oi { get; set; }
        public string no_seiho1 { get; set; }
        public string no_seiho2 { get; set; }
        public string no_seiho3 { get; set; }
        public string no_seiho4 { get; set; }
        public string nm_hin { get; set; }
        public string cd_group { get; set; }
        public string cd_team { get; set; }
        public string id_toroku { get; set; }
        public string nm_genryo { get; set; }
        public string cd_naiyobunrui { get; set; }
        public string brand { get; set; }
        public string id_data { get; set; }
        public int skip { get; set; }
        public int top { get; set; }
    }

    #endregion
}
