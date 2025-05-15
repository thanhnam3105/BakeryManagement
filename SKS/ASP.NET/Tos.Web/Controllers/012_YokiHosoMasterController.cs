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
using System.Data;
using System.Threading;

namespace Tos.Web.Controllers
{
    //created from 【SearchInputController(Ver1.7)】 Template
    public class _012_YokiHosoMasterController : ApiController
    {
        #region "Static variables"
        private static List<processID> priorityInsertQueue = new List<processID>();
        private static long p_ID = 0;
        #endregion

        #region "Controllerで公開するAPI"

        /// <summary>
        /// パラメータで受け渡された OData クエリに該当するすべてのtarget情報を取得します。
        /// </summary>
        /// <param name="options"> OData クエリ</param>
        /// <returns>target情報</returns>
        //public PageResult<ma_yoki_hoso> Get(ODataQueryOptions<ma_yoki_hoso> options)
        //{
        //    // TODO:target情報を管理しているDbContextとtargetの型を指定します。

        //    ShohinKaihatsuEntities context = new ShohinKaihatsuEntities();
        //    context.Configuration.ProxyCreationEnabled = false;
        //    IQueryable results = options.ApplyTo(context.ma_yoki_hoso.AsQueryable());
        //    return new PageResult<ma_yoki_hoso>(results as IEnumerable<ma_yoki_hoso>, Request.GetNextPageLink(), Request.GetInlineCount());

        //    // TODO: 上記実装を行った後に下の行は削除します
        //    //return null;
        //}

        /// <summary>
        /// パラメータで受け渡された 値に該当するすべてのtarget情報を StoredProcedure を利用して取得します。
        /// </summary>
        /// <param name="options"></param>
        /// <returns>target情報</returns>
        public StoredProcedureResult<sp_shohinkaihatsu_search_012_Result> Get([FromUri]YokiHosoShizaiNameSearch value)
        {
            // TODO:target情報を管理しているDbContextとtargetの型を指定します。

            ShohinKaihatsuEntities context = new ShohinKaihatsuEntities();
            context.Configuration.ProxyCreationEnabled = false;


            StoredProcedureResult<sp_shohinkaihatsu_search_012_Result> result = new StoredProcedureResult<sp_shohinkaihatsu_search_012_Result>();

            //ObjectParameter p_cd_yoki_hoso = new ObjectParameter("cd_yoki_hoso", typeof(string));
            //context.sp_yoki_hoso_search_shizainame_get_012();
            //result.Items = context.sp_yoki_hoso_search_shizainame_get_012().ToList();


             //TODO: DbContext のメソッドとしてストアドプロシージャを呼び出します。※ストアドのアウトプットパラメータとしてcountが返却される前提です
            result.Items = context.sp_shohinkaihatsu_search_012(value.nm_yoki_hoso
                                                                , value.nm_yoki_hoso_shizai
                                                                , value.skip
                                                                , value.top
                                                             ).ToList();
            result.Count = result.Items.Count();
             //ObjectParameter count = new ObjectParameter("rowcount", typeof(int));
             //StoredProcedureResult<ma_kengen_view> result = new StoredProcedureResult<ma_kengen_view>();
             //result.Items = context.sp_yoki_hoso_search_shizainame_get_012(cd_shain, count).ToList();
             //result.Count = (int)count.Value;

             //TODO: DbContext のメソッドとしてデータカウントを取得するストアドプロシージャを呼び出します。
             return result;

            // TODO: 上記実装を行った後に下の行は削除します
            //return null;
        }

        ///// <summary>
        ///// パラメータで受け渡された target のキー項目をもとに target情報を取得します。
        ///// </summary>
        ///// <param name="id"> target のキー項目</param>
        ///// <returns> target 情報</returns>
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
        public HttpResponseMessage Post([FromBody]ChangeSet<ma_yoki_hoso> value)
        {
            if (value == null)
            {
                return Request.CreateErrorResponse(HttpStatusCode.InternalServerError, Properties.Resources.NotNullAllow);
            }

            // TODO: キー項目の重複チェックを行います。
            //InvalidationSet<ma_yoki_hoso> headerInvalidations = IsAlreadyExists(value);
            //if (headerInvalidations.Count > 0)
            //{
            //    return Request.CreateResponse<InvalidationSet<ma_yoki_hoso>>(HttpStatusCode.BadRequest, headerInvalidations);
            //}



            using (ShohinKaihatsuEntities context = new ShohinKaihatsuEntities())
            {
                //if (value.Created.Count > 0)
                //{
                //    int cd_yoki_hoso_new = context.ma_yoki_hoso.Select(x => (int?)(Math.Abs(x.cd_yoki_hoso))?? 0).Max() + 1;
                //    //value.Created[0].cd_yoki_hoso = (short)cd_yoki_hoso_new;

                //    //ループ処理を行う
                //    for (int i = 0; i < value.Created.Count; i++)
                //    {                       
                //        value.Created[i].cd_yoki_hoso = cd_yoki_hoso_new;
                //        cd_yoki_hoso_new = cd_yoki_hoso_new + 1;
                //    }
                //}
                // Single runing
                var passID = getProcessPass();
                try
                {
                    if (value.Created.Count() > 0)
                    {
                        int cd_yoki_hoso_new = context.ma_yoki_hoso.Select(x => x.cd_yoki_hoso).FirstOrDefault();

                        if (cd_yoki_hoso_new == 0)
                        {
                            //ma_yoki_hoso add = new ma_yoki_hoso();
                            //add.cd_yoki_hoso = 1;

                            //context.ma_yoki_hoso.Add(add);
                            cd_yoki_hoso_new = 1;

                            for (int i = 0; i < value.Created.Count; i++)
                            {
                                value.Created[i].cd_yoki_hoso = cd_yoki_hoso_new;
                                cd_yoki_hoso_new = cd_yoki_hoso_new + 1;
                            }

                        }
                        else
                        {
                            cd_yoki_hoso_new = context.ma_yoki_hoso.Select(x => Math.Abs(x.cd_yoki_hoso)).Max() + 1;

                            for (int i = 0; i < value.Created.Count; i++)
                            {
                                value.Created[i].cd_yoki_hoso = cd_yoki_hoso_new;
                                cd_yoki_hoso_new = cd_yoki_hoso_new + 1;
                            }
                        }

                    }
                    // TODO: 保存処理を実行します。
                    var result = SaveData(value);
                    return Request.CreateResponse<YokiHosoMasterChangeResponse>(HttpStatusCode.OK, result);
                }
                finally
                {
                    // Remove the pass when completing current request
                    priorityInsertQueue.Remove(passID);
                }
            }


        }

        /// <summary>
        /// パラメーターで受け渡されたtarget情報を更新します
        /// </summary>
        /// <param name="value"></param>
        /// <returns></returns>
        public HttpResponseMessage Put([FromBody]ChangeSet<ma_yoki_hoso> value)
        {


            var result = SaveData(value);
            return Request.CreateResponse<YokiHosoMasterChangeResponse>(HttpStatusCode.OK, result);

        }

        /// <summary>
        /// パラメーターで受け渡されたtarget情報を削除します
        /// </summary>
        /// <param name="value"></param>
        /// <returns></returns>
        public HttpResponseMessage Delete([FromBody]ChangeSet<ma_yoki_hoso> value)
        {

            var result = SaveData(value);
            return Request.CreateResponse<YokiHosoMasterChangeResponse>(HttpStatusCode.OK, result);

        }

        #endregion

        #region "Controller内で利用する関数群"

        /// <summary>
        /// target 情報の主キーによる重複チェックを行います。
        /// </summary>
        /// <param name="value">target 情報の変更セット</param>
        /// <returns></returns>
        //private InvalidationSet<ma_yoki_hoso> IsAlreadyExists(ChangeSet<ma_yoki_hoso> value)
        //{
        //    InvalidationSet<ma_yoki_hoso> result = new InvalidationSet<ma_yoki_hoso>();

        //    using (ShohinKaihatsuEntities context = new ShohinKaihatsuEntities())
        //    {
        //        foreach (var item in value.Created)
        //        {
        //            // TODO: 変更セット内で重複したキーが入力されたかどうかのチェック、およびデータベース上に重複した主キーがあるかどうかのチェックを行います。
        //            bool isDepulicate = false;

        //            var createdCount = value.Created.Count(target => target.cd_yoki_hoso == item.cd_yoki_hoso);
        //            var isDeleted = value.Deleted.Exists(target => target.cd_yoki_hoso == item.cd_yoki_hoso);
        //            var isDatabaseExists = (context.ma_yoki_hoso_shizai.Find(item.cd_yoki_hoso) != null);

        //            isDepulicate |= (createdCount > 1);
        //            isDepulicate |= (!isDeleted && createdCount == 1 && isDatabaseExists);

        //            if (isDepulicate)
        //            {
        //                result.Add(new Invalidation<ma_yoki_hoso>(Properties.Resources.ValidationKey, item, "cd_yoki_hoso"));
        //            }
        //        }
        //    }

        //    return result;
        //}

        /// <summary>
        /// target 情報の一括更新（追加・更新・削除）を実行します
        /// </summary>
        /// <param name="value">target 情報の変更セット</param>
        /// <returns>target 情報の更新結果オブジェクト</returns>
        private YokiHosoMasterChangeResponse SaveData(ChangeSet<ma_yoki_hoso> value)
        {
            using (ShohinKaihatsuEntities context = new ShohinKaihatsuEntities())
            {

                value.SetSeihoDataSaveInfo(this.User.Identity);                
                value.AttachTo(context);
                context.SaveChanges();
            }

            // TODO: 返却用のオブジェクトを生成します。
            var result = new YokiHosoMasterChangeResponse();
            result.Detail.AddRange(value.Flatten());
            return result;

            // TODO: 上記実装を行った後に下の行は削除します
            //return null;
        }

        /// <summary>
        /// Fn support prevent conflict when insert new data
        /// Current insert request will wait until previous request is completed
        /// </summary>
        /// <returns></returns>
        private processID getProcessPass()
        {
            // Create new pass
            processID result = new processID() { 
                ID = p_ID++,
                waitTime = 0
            };
            // Add pass to priority queue
            priorityInsertQueue.Add(result);
            // Check if current process is the first of priority
            while (priorityInsertQueue[0].ID != result.ID)
            {
                result.waitTime += processID.singleWaitingTime;
                if (result.waitTime > processID.maxWaiting)
                {
                    throw (new Exception("Request timeout."));
                }
                Thread.Sleep(processID.singleWaitingTime);
            }
            return result;
        }

        #endregion
    }

    #region "ControllerのAPIで利用するリクエスト・レスポンスクラスです"
    
    public class YokiHosoShizaiNameSearch
    {
        public string nm_yoki_hoso { get; set; }
        public string nm_yoki_hoso_shizai { get; set; }
        public int skip { get; set; }
        public int top { get; set; }
    }
    public class YokiHosoMasterChangeResponse
    {
        public YokiHosoMasterChangeResponse()
        {
            this.Detail = new List<ma_yoki_hoso>();
        }

        public List<ma_yoki_hoso> Detail { get; set; }
    }

    #endregion
}
