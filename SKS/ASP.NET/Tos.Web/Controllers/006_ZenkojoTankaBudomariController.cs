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
    //created from 【SearchInputController(Ver1.7)】 Template
    public class _006_ZenkojoTankaBudomariController : ApiController
    {
        #region "Controllerで公開するAPI"

        /// <summary>
        /// パラメータで受け渡された OData クエリに該当するすべてのtarget情報を取得します。
        /// </summary>
        /// <param name="options"> OData クエリ</param>
        /// <returns>target情報</returns>
        public ma_shizaiChangeResponse Get_shizai(ODataQueryOptions<ma_shizai> options, int cd_kaisha)
        {
            // TODO:header情報を管理しているDbContextとheader,detailの型を指定します。

            using (ShohinKaihatsuEntities context = new ShohinKaihatsuEntities())
            {
                context.Configuration.ProxyCreationEnabled = false;

                ma_shizaiChangeResponse result = new ma_shizaiChangeResponse();
                
                // header検索
                result.Header = context.sp_shohinkaihatsu_search_006_header(cd_kaisha).ToList();

                if (result.Header == null)
                {
                    throw new HttpException((int)HttpStatusCode.NotFound, Properties.Resources.NoDataExsists);
                }
               
                // detail検索
                var query = options.ApplyTo(context.ma_shizai.AsQueryable()) as IEnumerable<ma_shizai>;
                result.Detail = query.ToList();

                return result;
            }
        }
        public ma_genryokojoChangeResponse Get_genryo(ODataQueryOptions<ma_genryokojo> options, int cd_kaisha)
        {
            // TODO:header情報を管理しているDbContextとheader,detailの型を指定します。

            using (ShohinKaihatsuEntities context = new ShohinKaihatsuEntities())
            {
                context.Configuration.ProxyCreationEnabled = false;

                ma_genryokojoChangeResponse result = new ma_genryokojoChangeResponse();

                // header検索
                result.Header = context.sp_shohinkaihatsu_search_006_header(cd_kaisha).ToList();

                if (result.Header == null)
                {
                    throw new HttpException((int)HttpStatusCode.NotFound, Properties.Resources.NoDataExsists);
                }
               
                // detail検索
                var query = options.ApplyTo(context.ma_genryokojo.AsQueryable()) as IEnumerable<ma_genryokojo>;
                result.Detail = query.ToList();

                return result;
            }
        }

        public StoredProcedureResult<sp_shohinkaihatsu_search_006_tantokaisha_Result> Get_kaisha(decimal id_user)
        {
            // TODO:header情報を管理しているDbContextとheaderの型を指定します。

            ShohinKaihatsuEntities context = new ShohinKaihatsuEntities();
            context.Configuration.ProxyCreationEnabled = false;


            // TODO: DbContext のメソッドとしてストアドプロシージャを呼び出します。
            //ObjectParameter count = new ObjectParameter("rowcount", typeof(int));
            StoredProcedureResult<sp_shohinkaihatsu_search_006_tantokaisha_Result> result = new StoredProcedureResult<sp_shohinkaihatsu_search_006_tantokaisha_Result>();
            result.Items = context.sp_shohinkaihatsu_search_006_tantokaisha(id_user).ToList();

            // TODO: DbContext のメソッドとしてデータカウントを取得するストアドプロシージャを呼び出します。
            return result;

        }
        /// <summary>
        /// パラメータで受け渡された 値に該当するすべてのtarget情報を StoredProcedure を利用して取得します。
        /// </summary>
        /// <param name="options"></param>
        /// <returns>target情報</returns>
        //public StoredProcedureResult<object /* StoredProcedure の戻り値となる複合型を指定します。 */> Get(/*TODO: StoredProcedure のパラメータを指定します*/)
        //{
        //    // TODO:target情報を管理しているDbContextとtargetの型を指定します。

        //    // /*TODO:target情報を管理しているDbContextを指定します*/ context = new /*TODO:target情報を管理しているDbContextを指定します*/();
        //    // context.Configuration.ProxyCreationEnabled = false;

        //    // TODO: DbContext のメソッドとしてストアドプロシージャを呼び出します。※ストアドのアウトプットパラメータとしてcountが返却される前提です
        //    // ObjectParameter count = new ObjectParameter("rowcount", typeof(int));
        //    // StoredProcedureResult<ma_kengen_view> result = new StoredProcedureResult<ma_kengen_view>();
        //    // result.Items = context.CallStoredProcedure(cd_shain, count).ToList();
        //    // result.Count = (int)count.Value;

        //    // TODO: DbContext のメソッドとしてデータカウントを取得するストアドプロシージャを呼び出します。
        //    // return result;

        //    // TODO: 上記実装を行った後に下の行は削除します
        //    return null;
        //}

        /// <summary>
        /// パラメータで受け渡された target のキー項目をもとに target情報を取得します。
        /// </summary>
        /// <param name="id"> target のキー項目</param>
        /// <returns> target 情報</returns>
        //public object Get(int id)
        //{
        //    // TODO: target 情報を管理しているDbContextと target の型を指定します。

        //    // using (/*TODO: target 情報を管理しているDbContextを指定します*/ context = new /*TODO: target 情報を管理しているDbContextを指定します*/())
        //    // {
        //    //     context.Configuration.ProxyCreationEnabled = false;

        //    //     return (from m in context./*TODO: target の型を指定します*/
        //    //             where m./*TODO:headerのキー項目を指定します*/ == id
        //    //             select m).SingleOrDefault();
        //    // }

        //    // TODO: 上記実装を行った後に下の行は削除します
        //    return null;
        //}

        /// <summary>
        /// パラメータで受け渡された target 情報をもとにエントリーを一括更新（追加・更新・削除）します。
        /// </summary>
        /// <param name="value"></param>
        /// <returns></returns>
        //public HttpResponseMessage Post([FromBody]ChangeSet<object/*TODO:targetの型を指定します*/> value)
        //{
        //    if (value == null)
        //    {
        //        return Request.CreateErrorResponse(HttpStatusCode.InternalServerError, Properties.Resources.NotNullAllow);
        //    }

        //    // TODO: キー項目の重複チェックを行います。
        //    InvalidationSet<object/*TODO:targetの型を指定します*/> headerInvalidations = IsAlreadyExists(value);
        //    if (headerInvalidations.Count > 0)
        //    {
        //        return Request.CreateResponse<InvalidationSet<object/*TODO:targetの型を指定します*/>>(HttpStatusCode.BadRequest, headerInvalidations);
        //    }

        //    // TODO: 保存処理を実行します。
        //    var result = SaveData(value);
        //    return Request.CreateResponse<SearchInputChangeResponse>(HttpStatusCode.OK, result);

        //}

        /// <summary>
        /// パラメーターで受け渡されたtarget情報を更新します
        /// </summary>
        /// <param name="value"></param>
        /// <returns></returns>
        //public HttpResponseMessage Put([FromBody]ChangeSet<object/*TODO:targetの型を指定します*/> value)
        //{

        //    var result = SaveData(value);
        //    return Request.CreateResponse<SearchInputChangeResponse>(HttpStatusCode.OK, result);

        //}

        ///// <summary>
        ///// パラメーターで受け渡されたtarget情報を削除します
        ///// </summary>
        ///// <param name="value"></param>
        ///// <returns></returns>
        //public HttpResponseMessage Delete([FromBody]ChangeSet<object/*TODO:targetの型を指定します*/> value)
        //{

        //    var result = SaveData(value);
        //    return Request.CreateResponse<SearchInputChangeResponse>(HttpStatusCode.OK, result);

        //}

        #endregion

        #region "Controller内で利用する関数群"

        /// <summary>
        /// target 情報の主キーによる重複チェックを行います。
        /// </summary>
        /// <param name="value">target 情報の変更セット</param>
        /// <returns></returns>
        //private InvalidationSet<object/*TODO:targetの型を指定します*/> IsAlreadyExists(ChangeSet<object/*TODO:targetの型を指定します*/> value)
        //{
        //    InvalidationSet<object/*TODO:targetの型を指定します*/> result = new InvalidationSet<object/*TODO:targetの型を指定します*/>();

        //    //using (/*TODO:target情報を管理しているDbContextを指定します*/ context = new /*TODO:target情報を管理しているDbContextを指定します*/())
        //    //{
        //    //    foreach (var item in value.Created)
        //    //    {
        //    //        // TODO: 変更セット内で重複したキーが入力されたかどうかのチェック、およびデータベース上に重複した主キーがあるかどうかのチェックを行います。
        //    //        bool isDepulicate = false;
        //    //
        //    //        var createdCount = value.Created.Count(target => target.no_seq == item.no_seq);
        //    //        var isDeleted = value.Deleted.Exists(target => target.no_seq == item.no_seq);
        //    //        var isDatabaseExists = (context./*TODO: target の型を指定します*/.Find(item.no_seq) != null);

        //    //        isDepulicate |= (createdCount > 1);
        //    //        isDepulicate |= (!isDeleted && createdCount == 1 && isDatabaseExists);

        //    //        if (isDepulicate)
        //    //        {
        //    //            result.Add(new Invalidation<object/*TODO:targetの型を指定します*/>(Properties.Resources.ValidationKey, item, "no_seq"));
        //    //        }
        //    //    }
        //    //}

        //    return result;
        //}

        /// <summary>
        /// target 情報の一括更新（追加・更新・削除）を実行します
        /// </summary>
        /// <param name="value">target 情報の変更セット</param>
        /// <returns>target 情報の更新結果オブジェクト</returns>
        //private SearchInputChangeResponse SaveData(ChangeSet<object/*TODO:targetの型を指定します*/> value)
        //{
        //    //using (/*TODO:target情報を管理しているDbContextを指定します*/ context = new /*TODO:target情報を管理しているDbContextを指定します*/())
        //    //{

        //    //    value.SetDataSaveInfo(this.User.Identity);                
        //    //    value.AttachTo(context);
        //    //    context.SaveChanges();
        //    //}

        //    // TODO: 返却用のオブジェクトを生成します。
        //    //var result = new SearchInputChangeResponse();
        //    //result.Detail.AddRange(value.Flatten());
        //    //return result;

        //    // TODO: 上記実装を行った後に下の行は削除します
        //    return null;
        //}

        #endregion
    }

    #region "ControllerのAPIで利用するリクエスト・レスポンスクラスです"

    public class ma_shizaiChangeResponse
    {
        public ma_shizaiChangeResponse()
        {
            this.Header = new List<sp_shohinkaihatsu_search_006_header_Result>();
            this.Detail = new List<ma_shizai>();
        }

        public List<sp_shohinkaihatsu_search_006_header_Result> Header { get; set; }
        public List<ma_shizai> Detail { get; set; }
    }
    public class ma_genryokojoChangeResponse
    {
        public ma_genryokojoChangeResponse()
        {
            this.Header = new List<sp_shohinkaihatsu_search_006_header_Result>();
            this.Detail = new List<ma_genryokojo>();
        }

        public List<sp_shohinkaihatsu_search_006_header_Result> Header { get; set; }
        public List<ma_genryokojo> Detail { get; set; }
    }

    #endregion
}
