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
using System.Data.Entity.Infrastructure;
using System.Data.Common;
using Tos.Web.Logging;

namespace Tos.Web.Controllers
{
    //created from 【SearchInputController(Ver1.7)】 Template
    public class _404_KoshinRirekiController : ApiController
    {
        #region "Controllerで公開するAPI"


        /// <summary>
        /// パラメータで受け渡された 値に該当するすべてのtarget情報を StoredProcedure を利用して取得します。
        /// </summary>
        /// <param name="options"></param>
        /// <returns>target情報</returns>
        /// <author>Nam.PT</author>
        /// <date>15/03/2021</date>
        public StoredProcedureResult<sp_koshinrireki_search_404_Result> Get([FromUri]KoshinRirekiCriteria option)
        {
            // TODO:target情報を管理しているDbContextとtargetの型を指定します。
            ShohinKaihatsuEntities context = new ShohinKaihatsuEntities();
            context.Configuration.ProxyCreationEnabled = false;
            StoredProcedureResult<sp_koshinrireki_search_404_Result> result = new StoredProcedureResult<sp_koshinrireki_search_404_Result>();
            result.Items = context.sp_koshinrireki_search_404(option.id_function, option.cd_kaisha, option.skip, option.top).ToList();
            if (result.Items.Count() > 0)
            {
                result.Count = (int)result.Items.First().cnt;
            }
            return result;
        }

        /// <summary>
        /// パラメータで受け渡された target 情報をもとにエントリーを一括更新（追加・更新・削除）します。
        /// </summary>
        /// <param name="value"></param>
        /// <returns></returns>
        /// <author>Nam.PT</author>
        /// <date>15/03/2021</date>
        public HttpResponseMessage Post([FromBody]ChangeSet<tr_change_log> value)
        {
            if (value == null)
            {
                return Request.CreateErrorResponse(HttpStatusCode.InternalServerError, Properties.Resources.NotNullAllow);
            }
            InvalidationSet<tr_change_log> headerInvalidations = IsAlreadyExists(value);
            if (headerInvalidations.Count > 0)
            {
                return Request.CreateResponse<InvalidationSet<tr_change_log>>(HttpStatusCode.BadRequest, headerInvalidations);
            }
            using (ShohinKaihatsuEntities context = new ShohinKaihatsuEntities())
            {
                IObjectContextAdapter adapter = context as IObjectContextAdapter;
                DbConnection connection = adapter.ObjectContext.Connection;
                connection.Open();

                using (DbTransaction transaction = connection.BeginTransaction())
                {
                    try
                    {
                        var result = SaveData(value);
                        transaction.Commit();
                        return Request.CreateResponse<KoshinRirekiChangeResponse>(HttpStatusCode.OK, result);
                    }
                    catch (DbUpdateConcurrencyException ex)
                    {
                        // 排他エラー
                        transaction.Rollback();
                        Logger.App.Error(ex.Message, ex);
                        return Request.CreateErrorResponse(HttpStatusCode.Conflict, Properties.Resources.ServiceError);
                    }
                    catch (Exception ex)
                    {
                        // 例外をエラーログに出力します。
                        transaction.Rollback();
                        Logger.App.Error(ex.Message, ex);
                        return Request.CreateResponse(HttpStatusCode.BadRequest, ex);
                    }
                }
            }
        }


        #endregion

        #region "Controller内で利用する関数群"

        /// <summary>
        /// target 情報の一括更新（追加・更新・削除）を実行します
        /// </summary>
        /// <param name="value">target 情報の変更セット</param>
        /// <returns>target 情報の更新結果オブジェクト</returns>
        /// <author>Nam.PT</author>
        /// <date>15/03/2021</date>
        private KoshinRirekiChangeResponse SaveData(ChangeSet<tr_change_log> value)
        {
            using (ShohinKaihatsuEntities context = new ShohinKaihatsuEntities())
            {
                value.AttachTo(context);
                context.SaveChanges();
            }

            var result = new KoshinRirekiChangeResponse();
            result.Detail.AddRange(value.Flatten());
            return result;
        }

        /// <summary>
        /// target 情報の主キーによる重複チェックを行います。
        /// </summary>
        /// <param name="value">target 情報の変更セット</param>
        /// <returns></returns>
        /// <author>Nam.PT</author>
        /// <date>01/04/2021</date>
        private InvalidationSet<tr_change_log> IsAlreadyExists(ChangeSet<tr_change_log> value)
        {
            InvalidationSet<tr_change_log> result = new InvalidationSet<tr_change_log>();

            using (ShohinKaihatsuEntities context = new ShohinKaihatsuEntities())
            {
                foreach (var item in value.Created)
                {
                    // TODO: 変更セット内で重複したキーが入力されたかどうかのチェック、およびデータベース上に重複した主キーがあるかどうかのチェックを行います。
                    bool isDepulicate = false;

                    var createdCount = value.Created.Count(target => target.no_rireki == item.no_rireki);
                    var isDeleted = value.Deleted.Exists(target => target.no_rireki == item.no_rireki);
                    var isDatabaseExists = (context.tr_change_log.Count(target => target.no_rireki == item.no_rireki) > 0);
                    isDepulicate |= (createdCount > 1);
                    isDepulicate |= (!isDeleted && createdCount == 1 && isDatabaseExists);

                    if (isDepulicate)
                    {
                        result.Add(new Invalidation<tr_change_log>(Properties.Resources.ValidationKey, item, "no_rireki"));
                    }
                }
            }

            return result;
        }
        #endregion
    }

    #region "ControllerのAPIで利用するリクエスト・レスポンスクラスです"

    public class KoshinRirekiRequest
    {
        public ChangeSet<tr_change_log> ChangeLog { get; set; }
    }

    public class KoshinRirekiChangeResponse
    {
        public KoshinRirekiChangeResponse()
        {
            this.Detail = new List<tr_change_log>();
        }

        public List<tr_change_log> Detail { get; set; }
    }

    public class KoshinRirekiCriteria
    {
        public string id_function { set; get; }
        public int cd_kaisha { get; set; }
        public int skip { set; get; }
        public int top { set; get; }
    }
    #endregion
}
