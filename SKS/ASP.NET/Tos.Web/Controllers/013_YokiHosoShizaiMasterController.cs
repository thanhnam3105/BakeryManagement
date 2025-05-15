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
using System.Threading;

namespace Tos.Web.Controllers
{
    //created from 【SearchInputController(Ver1.7)】 Template
    public class _013_YokiHosoShizaiMasterController : ApiController
    {
        #region "Static variables"
        private static List<processID> priorityInsertQueue = new List<processID>();
        private static long p_ID = 0;
        #endregion

        #region "Controllerで公開するAPI"

        /// <summary>
        /// パラメータで受け渡された 値に該当するすべてのtarget情報を StoredProcedure を利用して取得します。
        /// </summary>
        /// <param name="options"></param>
        /// <returns>target情報</returns>
        [HttpPost]
        public StoredProcedureResult<sp_shohinkaihatsu_search_013_Result> Search([FromBody]YokiHosoShizaiMasterparamSearch value)
        {
            // TODO:target情報を管理しているDbContextとtargetの型を指定します。

            ShohinKaihatsuEntities context = new ShohinKaihatsuEntities();
            StoredProcedureResult<sp_shohinkaihatsu_search_013_Result> result = new StoredProcedureResult<sp_shohinkaihatsu_search_013_Result>();
            context.Configuration.ProxyCreationEnabled = false;

            // TODO: DbContext のメソッドとしてストアドプロシージャを呼び出します。※ストアドのアウトプットパラメータとしてcountが返却される前提です
            result.Items = context.sp_shohinkaihatsu_search_013(value.cd_yoki_hoso_shizai
                                                                , value.nm_yoki_hoso_shizai
                                                                , value.nm_maker
                                                                , value.nm_recycle_hyoji
                                                                , value.no_shizai_kikakusho
                                                                , value.nm_size
                                                                , value.nm_zaishitsu
                                                                , value.skip
                                                                , value.top
                                                                ).ToList();
            result.Count = result.Items.Count();

            // TODO: DbContext のメソッドとしてデータカウントを取得するストアドプロシージャを呼び出します。
            return result;

            // TODO: 上記実装を行った後に下の行は削除します
            //return null;
        }

        /// <summary>
        /// パラメータで受け渡された target 情報をもとにエントリーを一括更新（追加・更新・削除）します。
        /// </summary>
        /// <param name="value"></param>
        /// <returns></returns>
        public HttpResponseMessage Post([FromBody]ChangeSet<ma_yoki_hoso_shizai> value)
        {
            if (value == null)
            {
                return Request.CreateErrorResponse(HttpStatusCode.InternalServerError, Properties.Resources.NotNullAllow);
            }

            // TODO: キー項目の重複チェックを行います。
            //InvalidationSet<ma_yoki_hoso_shizai> headerInvalidations = IsAlreadyExists(value);
            //if (headerInvalidations.Count > 0)
            //{
            //    return Request.CreateResponse<InvalidationSet<ma_yoki_hoso_shizai>>(HttpStatusCode.BadRequest, headerInvalidations);
            //}
            ShohinKaihatsuEntities context = new ShohinKaihatsuEntities();

            //Single runing
            var passID = getProcessPass();
            try
            {

            var data = from d in context.ma_yoki_hoso_shizai select d;
            int cntdata = data.Count();
            if (cntdata == 0)
            {
                value.Created[0].cd_yoki_hoso_shizai = 1;
            }
            else
            {
                var cd_yoki_hoso_new = context.ma_yoki_hoso_shizai.Select(x => Math.Abs(x.cd_yoki_hoso_shizai)).Max() + 1;
                value.Created[0].cd_yoki_hoso_shizai = (short)cd_yoki_hoso_new;
            }

            // TODO: 保存処理を実行します。
            var result = SaveData(value);
            return Request.CreateResponse<YokiHosoShizaiMasterChangeResponse>(HttpStatusCode.OK, result);

            }
            finally
            {
                //Remove the pass when completing current request
                priorityInsertQueue.Remove(passID);
            }

        }

        /// <summary>
        /// パラメーターで受け渡されたtarget情報を更新します
        /// </summary>
        /// <param name="value"></param>
        /// <returns></returns>
        public HttpResponseMessage Put([FromBody]ChangeSet<ma_yoki_hoso_shizai> value)
        {

            var result = SaveData(value);
            return Request.CreateResponse<YokiHosoShizaiMasterChangeResponse>(HttpStatusCode.OK, result);

        }

        /// <summary>
        /// パラメーターで受け渡されたtarget情報を削除します
        /// </summary>
        /// <param name="value"></param>
        /// <returns></returns>
        public HttpResponseMessage Delete([FromBody]ChangeSet<ma_yoki_hoso_shizai> value)
        {

            var result = SaveData(value);
            return Request.CreateResponse<YokiHosoShizaiMasterChangeResponse>(HttpStatusCode.OK, result);

        }

        #endregion

        #region "Controller内で利用する関数群"

        /// <summary>
        /// target 情報の主キーによる重複チェックを行います。
        /// </summary>
        /// <param name="value">target 情報の変更セット</param>
        /// <returns></returns>
        //private InvalidationSet<ma_yoki_hoso_shizai> IsAlreadyExists(ChangeSet<ma_yoki_hoso_shizai> value)
        //{
        //    InvalidationSet<ma_yoki_hoso_shizai> result = new InvalidationSet<ma_yoki_hoso_shizai>();

        //    using (ShohinKaihatsuEntities context = new ShohinKaihatsuEntities())
        //    {
        //        foreach (var item in value.Created)
        //        {
        //            // TODO: 変更セット内で重複したキーが入力されたかどうかのチェック、およびデータベース上に重複した主キーがあるかどうかのチェックを行います。
        //            bool isDepulicate = false;
            
        //            var createdCount = value.Created.Count(target => target.cd_yoki_hoso_shizai == item.cd_yoki_hoso_shizai);
        //            var isDeleted = value.Deleted.Exists(target => target.cd_yoki_hoso_shizai == item.cd_yoki_hoso_shizai);
        //            var isDatabaseExists = (context.ma_yoki_hoso_shizai.Find(item.cd_yoki_hoso_shizai) != null);

        //            isDepulicate |= (createdCount > 1);
        //            isDepulicate |= (!isDeleted && createdCount == 1 && isDatabaseExists);

        //            if (isDepulicate)
        //            {
        //                result.Add(new Invalidation<ma_yoki_hoso_shizai>(Properties.Resources.ValidationKey, item, "cd_yoki_hoso_shizai"));
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
        private YokiHosoShizaiMasterChangeResponse SaveData(ChangeSet<ma_yoki_hoso_shizai> value)
        {
            using (ShohinKaihatsuEntities context = new ShohinKaihatsuEntities())
            {

                value.SetSeihoDataSaveInfo(this.User.Identity);                
                value.AttachTo(context);
                context.SaveChanges();
            }

            // TODO: 返却用のオブジェクトを生成します。
            var result = new YokiHosoShizaiMasterChangeResponse();
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

    public class YokiHosoShizaiMasterparamSearch
    {
        public string cd_yoki_hoso_shizai { get; set; }
        public string nm_yoki_hoso_shizai { get; set; }
        public string nm_maker { get; set; }
        public string nm_recycle_hyoji { get; set; }
        public string no_shizai_kikakusho { get; set; }
        public string nm_size { get; set; }
        public string nm_zaishitsu { get; set; }
        public int skip { get; set; }
        public int top { get; set; }

    }
    public class YokiHosoShizaiMasterChangeResponse
    {
        public YokiHosoShizaiMasterChangeResponse()
        {
            this.Detail = new List<ma_yoki_hoso_shizai>();
        }

        public List<ma_yoki_hoso_shizai> Detail { get; set; }
    }
    #endregion
}
