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
using Tos.Web.Properties;

namespace Tos.Web.Controllers
{
    //created from 【SearchInputController(Ver1.7)】 Template
    public class _401_BunruiIchiranController : ApiController
    {
        #region "Controllerで公開するAPI"


        /// <summary>
        /// パラメータで受け渡された 値に該当するすべてのtarget情報を StoredProcedure を利用して取得します。
        /// </summary>
        /// <param name="options"></param>
        /// <returns>target情報</returns>
        /// <author>Nam.PT</author>
        /// <date>11/03/2021</date>
        public StoredProcedureResult<sp_bunruiichiran_search_401_Result> Get([FromUri]BunruiIchiranCriteria option)
        {
            // TODO:target情報を管理しているDbContextとtargetの型を指定します。
            ShohinKaihatsuEntities context = new ShohinKaihatsuEntities();
            context.Configuration.ProxyCreationEnabled = false;
            StoredProcedureResult<sp_bunruiichiran_search_401_Result> result = new StoredProcedureResult<sp_bunruiichiran_search_401_Result>();
            result.Items = context.sp_bunruiichiran_search_401(option.no_gate, option.skip, option.top).ToList();
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
        /// <date>11/03/2021</date>
        public HttpResponseMessage Post([FromBody]BunruiIchiranRequest value)
        {
            if (value == null)
            {
                return Request.CreateErrorResponse(HttpStatusCode.InternalServerError, Properties.Resources.NotNullAllow);
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
                        DateTimeOffset date = DateTimeOffset.Now;
                        decimal idKoshin = 0;
                        int idFunction = 1;
                        Decimal.TryParse(UserInfo.GetUserNameFromIdentity(this.User.Identity), out idKoshin);

                        if (value.Bunrui.Created.Count > 0)
                        {
                            int noGate = value.Bunrui.Created[0].no_gate;
                            var data = context.ma_gate_bunrui.Where(n => n.no_gate == noGate).Count();

                            if (!value.isSearch)
                            {
                                for (int i = 0; i < value.Bunrui.Created.Count; i++)
                                {
                                    int? noSort = 1;
                                    if (data > 0)
                                    {
                                        noSort = context.ma_gate_bunrui.Where(n => n.no_gate == noGate).Max(n => n.no_sort) + 1;
                                    }
                                    value.Bunrui.Created[i].no_sort = noSort;
                                } 
                            }
                            for (int i = 0; i < value.Bunrui.Created.Count; i++)
                            {
                                short maxBunrui = getSaiban(Resources.saiban401Key1, string.Empty);
                                value.Bunrui.Created[i].no_bunrui = maxBunrui;
                            }
                        }

                        context.sp_shohinkaihatsu_insert_log_401(idFunction, null, date, idKoshin);

                        var result = SaveData(value);
                        transaction.Commit();
                        return Request.CreateResponse<BunruiIchiranChangeResponse>(HttpStatusCode.OK, result);
                    }
                    catch (DbUpdateConcurrencyException ex)
                    {
                        // 排他エラー
                        transaction.Rollback();
                        return Request.CreateErrorResponse(HttpStatusCode.Conflict, Properties.Resources.ServiceError);
                    }
                    catch (Exception ex)
                    {
                        // 例外をエラーログに出力します。
                        transaction.Rollback();
                        return Request.CreateResponse(HttpStatusCode.BadRequest, ex);
                    }
                }
            }
        }

        #endregion

        #region "Controller内で利用する関数群"

        /// <summary>
        /// Get next saiban, if not exist then create once
        /// </summary>
        /// <param name="key1"></param>
        /// <param name="key2"></param>
        /// <returns></returns>
        /// <author>Nam.PT</author>
        /// <date>14/04/2021</date>
        public short getSaiban(string key1, string key2)
        {
            using (ShohinKaihatsuEntities context = new ShohinKaihatsuEntities())
            {
                short result = 1;
                key2 = key2 == null ? "" : key2;
                decimal user_id = decimal.Parse(UserInfo.GetUserNameFromIdentity(this.User.Identity));
                DateTime dateNow = DateTime.Now;
                ma_saiban ma_saiban = context.ma_saiban.Where(x => x.key1 == key1 && x.key2 == key2).FirstOrDefault();
                if (ma_saiban == null)
                {
                    context.ma_saiban.Add(createSaiban(key1, key2, user_id, dateNow));
                }
                else
                {
                    ma_saiban.no_seq++;
                    ma_saiban.dt_koshin = dateNow;
                    ma_saiban.id_koshin = user_id;
                    result = (short)ma_saiban.no_seq;
                }
                context.SaveChanges();
                return result;
            }
        }

        /// <summary>
        /// Create new saiban when it not exist
        /// </summary>
        /// <param name="key1"></param>
        /// <param name="key2"></param>
        /// <returns></returns>
        /// <author>Nam.PT</author>
        /// <date>14/04/2021</date>
        private ma_saiban createSaiban(string key1, string key2, decimal user_id, DateTime dateNow)
        {
            return new ma_saiban()
            {
                no_seq = 1,
                key1 = key1,
                key2 = key2,
                id_toroku = user_id,
                dt_toroku = dateNow,
                id_koshin = user_id,
                dt_koshin = dateNow
            };
        }

        /// <summary>
        /// target 情報の一括更新（追加・更新・削除）を実行します
        /// </summary>
        /// <param name="value">target 情報の変更セット</param>
        /// <returns>target 情報の更新結果オブジェクト</returns>
        /// <author>Nam.PT</author>
        /// <date>11/03/2021</date>
        private BunruiIchiranChangeResponse SaveData(BunruiIchiranRequest value)
        {
            using (ShohinKaihatsuEntities context = new ShohinKaihatsuEntities())
            {
                value.Bunrui.SetDataSaveInfo(this.User.Identity);
                value.Bunrui.AttachTo(context);
                context.SaveChanges();
            }

            var result = new BunruiIchiranChangeResponse();
            result.Detail.AddRange(value.Bunrui.Flatten());
            return result;
        }

        #endregion
    }

    #region "ControllerのAPIで利用するリクエスト・レスポンスクラスです"

    public class BunruiIchiranRequest
    {
        public ChangeSet<ma_gate_bunrui> Bunrui { get; set; }
        public bool isSearch { get; set; }
    }

    public class BunruiIchiranChangeResponse
    {
        public BunruiIchiranChangeResponse()
        {
            this.Detail = new List<ma_gate_bunrui>();
        }

        public List<ma_gate_bunrui> Detail { get; set; }
    }

    public class BunruiIchiranCriteria
    {
        public int no_gate { get; set; }
        public int skip { set; get; }
        public int top { set; get; }
    }
    #endregion
}
