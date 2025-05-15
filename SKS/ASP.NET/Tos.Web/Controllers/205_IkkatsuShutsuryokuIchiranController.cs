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
using System.Data.Objects;
using System.IO;
using System.Security.Principal;
using System.Runtime.InteropServices;

namespace Tos.Web.Controllers
{

    public class _205_IkkatsuShutsuryokuIchiranController : ApiController
    {
        #region "Controllerで公開するAPI"
        ///// <summary>
        ///// パラメータで受け渡された 値に該当するすべてのtarget情報を StoredProcedure を利用して取得します。
        ///// </summary>
        ///// <param name="options"></param>
        ///// <returns>target情報</returns>
        public StoredProcedureResult<sp_shohinkaihatsu_search_205_Result> Get([FromUri] Param_Ikkatsu_Search options)
        {
            // TODO:target情報を管理しているDbContextとtargetの型を指定します。

            ShohinKaihatsuEntities context = new ShohinKaihatsuEntities();
            context.Configuration.ProxyCreationEnabled = false;

            // TODO: DbContext のメソッドとしてストアドプロシージャを呼び出します。※ストアドのアウトプットパラメータとしてcountが返却される前提です
            // ObjectParameter count = new ObjectParameter("TotalRows", typeof(Int32));
            var user = UserInfo.CreateFromAuthorityMaster(this.User.Identity);
            StoredProcedureResult<sp_shohinkaihatsu_search_205_Result> result = new StoredProcedureResult<sp_shohinkaihatsu_search_205_Result>();
            var data = context.sp_shohinkaihatsu_search_205(
                                                                     options.nm_user,
                                                                     options.dt_from,
                                                                     options.dt_to,
                                                                     options.seq_check,
                                                                     user.cd_kengen,
                                                                     Convert.ToInt32(user.cd_kaisha),
                                                                     options.skip,
                                                                     options.top
                                                               ).ToList();
            result.Items = data;
            if (result.Items.Count() > 0)
                result.Count = int.Parse(data[0].cnt.ToString());
            else
                result.Count = 0;
            // TODO: DbContext のメソッドとしてデータカウントを取得するストアドプロシージャを呼び出します。
            return result;

            // TODO: 上記実装を行った後に下の行は削除します
            //return null;
        }

        public class Param_Ikkatsu_Search
        {
            public string nm_user { get; set; }
            public DateTime? dt_from { get; set; }
            public DateTime? dt_to { get; set; }
            public int seq_check { get; set; }
            public int? skip { get; set; }
            public int? top { get; set; }

        }
        [DllImport("advapi32.DLL", SetLastError = true)]
        public static extern int LogonUser(string lpszUsername, string lpszDomain, string lpszPassword, int dwLogonType, int dwLogonProvider, ref IntPtr phToken);
        ///// <summary>
        ///// get open folder
        ///// </summary>
        ///// <param name="options"></param>
        ///// <returns>target情報</returns>
        public PathResponse GetFolder(int no_ikkatsu)
        {
            // TODO:target情報を管理しているDbContextとtargetの型を指定します。
            PathResponse result = new PathResponse();
            //result.path = HttpContext.Current.Server.MapPath(Properties.Settings.Default.destinationPath_no_ikkatsu + no_ikkatsu);
            result.path = Properties.Settings.Default.destinationPath_no_ikkatsu + no_ikkatsu;
            result.error = true;
            if (Properties.Settings.Default.userServer == "" || Properties.Settings.Default.passServer == "")
            {
                if (!Directory.Exists(result.path))
                {
                    return result;
                }
            }
            else
            {
                IntPtr admin_token = default(IntPtr);
                //Added these 3 lines
                WindowsIdentity wid_current = WindowsIdentity.GetCurrent();
                WindowsIdentity wid_admin = null;
                WindowsImpersonationContext wic = null;
                string[] separators = { "//" };
                string domain = Properties.Settings.Default.destinationPath_no_ikkatsu.ToString().Split(separators, StringSplitOptions.None)[1];
                domain = domain.Split(new[] { "/" }, StringSplitOptions.None)[0];
                if (LogonUser(Properties.Settings.Default.userServer, domain, Properties.Settings.Default.passServer, 9, 0, ref admin_token) != 0)
                {
                    wid_admin = new WindowsIdentity(admin_token);
                    wic = wid_admin.Impersonate();
                    if (!Directory.Exists(result.path))
                    {
                        return result;
                    }
                }
            }
            //result.path = Properties.Settings.Default.pathServer + Properties.Settings.Default.destinationPath_no_ikkatsu.Replace("~", "") + no_ikkatsu;
            result.path = Properties.Settings.Default.pathServer + no_ikkatsu;
            result.error = false;
            return result;
        }

        /// <summary>
        /// パラメータで受け渡された target のキー項目をもとに target情報を取得します。
        /// </summary>
        /// <param name="id"> target のキー項目</param>
        /// <returns> target 情報</returns>
        /// <summary>
        /// パラメータで受け渡された target 情報をもとにエントリーを一括更新（追加・更新・削除）します。
        /// </summary>
        /// <param name="value"></param>
        /// <returns></returns>
        public HttpResponseMessage Post([FromBody]ChangeSet<object/*TODO:targetの型を指定します*/> value)
        {
            if (value == null)
            {
                return Request.CreateErrorResponse(HttpStatusCode.InternalServerError, Properties.Resources.NotNullAllow);
            }

            // TODO: キー項目の重複チェックを行います。
            InvalidationSet<object/*TODO:targetの型を指定します*/> headerInvalidations = IsAlreadyExists(value);
            if (headerInvalidations.Count > 0)
            {
                return Request.CreateResponse<InvalidationSet<object/*TODO:targetの型を指定します*/>>(HttpStatusCode.BadRequest, headerInvalidations);
            }

            // TODO: 保存処理を実行します。
            var result = SaveData(value);
            return Request.CreateResponse<SearchInputChangeResponseNotUse>(HttpStatusCode.OK, result);

        }

        /// <summary>
        /// パラメーターで受け渡されたtarget情報を更新します
        /// </summary>
        /// <param name="value"></param>
        /// <returns></returns>
        public HttpResponseMessage Put([FromBody]ChangeSet<object/*TODO:targetの型を指定します*/> value)
        {

            var result = SaveData(value);
            return Request.CreateResponse<SearchInputChangeResponseNotUse>(HttpStatusCode.OK, result);

        }

        /// <summary>
        /// パラメーターで受け渡されたtarget情報を削除します
        /// </summary>
        /// <param name="value"></param>
        /// <returns></returns>
        public HttpResponseMessage Delete([FromBody]ChangeSet<object/*TODO:targetの型を指定します*/> value)
        {

            var result = SaveData(value);
            return Request.CreateResponse<SearchInputChangeResponseNotUse>(HttpStatusCode.OK, result);

        }

        #endregion

        #region "Controller内で利用する関数群"

        /// <summary>
        /// target 情報の主キーによる重複チェックを行います。
        /// </summary>
        /// <param name="value">target 情報の変更セット</param>
        /// <returns></returns>
        private InvalidationSet<object/*TODO:targetの型を指定します*/> IsAlreadyExists(ChangeSet<object/*TODO:targetの型を指定します*/> value)
        {
            InvalidationSet<object/*TODO:targetの型を指定します*/> result = new InvalidationSet<object/*TODO:targetの型を指定します*/>();

            //using (/*TODO:target情報を管理しているDbContextを指定します*/ context = new /*TODO:target情報を管理しているDbContextを指定します*/())
            //{
            //    foreach (var item in value.Created)
            //    {
            //        // TODO: 変更セット内で重複したキーが入力されたかどうかのチェック、およびデータベース上に重複した主キーがあるかどうかのチェックを行います。
            //        bool isDepulicate = false;
            //
            //        var createdCount = value.Created.Count(target => target.no_seq == item.no_seq);
            //        var isDeleted = value.Deleted.Exists(target => target.no_seq == item.no_seq);
            //        var isDatabaseExists = (context./*TODO: target の型を指定します*/.Find(item.no_seq) != null);

            //        isDepulicate |= (createdCount > 1);
            //        isDepulicate |= (!isDeleted && createdCount == 1 && isDatabaseExists);

            //        if (isDepulicate)
            //        {
            //            result.Add(new Invalidation<object/*TODO:targetの型を指定します*/>(Properties.Resources.ValidationKey, item, "no_seq"));
            //        }
            //    }
            //}

            return result;
        }

        /// <summary>
        /// target 情報の一括更新（追加・更新・削除）を実行します
        /// </summary>
        /// <param name="value">target 情報の変更セット</param>
        /// <returns>target 情報の更新結果オブジェクト</returns>
        private SearchInputChangeResponseNotUse SaveData(ChangeSet<object/*TODO:targetの型を指定します*/> value)
        {
            //using (/*TODO:target情報を管理しているDbContextを指定します*/ context = new /*TODO:target情報を管理しているDbContextを指定します*/())
            //{

            //    value.SetDataSaveInfo(this.User.Identity);                
            //    value.AttachTo(context);
            //    context.SaveChanges();
            //}

            // TODO: 返却用のオブジェクトを生成します。
            //var result = new SearchInputChangeResponse();
            //result.Detail.AddRange(value.Flatten());
            //return result;

            // TODO: 上記実装を行った後に下の行は削除します
            return null;
        }

        #endregion
    }

    #region "ControllerのAPIで利用するリクエスト・レスポンスクラスです"

    //public class SearchInputChangeResponse
    //{
    //    public SearchInputChangeResponse()
    //    {
    //        this.Detail = new List<object/*TODO: target の型を指定します*/>();
    //    }

    //    public List<object/*TODO: target の型を指定します*/> Detail { get; set; }
    //}
    public class PathResponse
    {
        public string path { get; set; }
        public bool error { get; set; }
    }
    #endregion
}
