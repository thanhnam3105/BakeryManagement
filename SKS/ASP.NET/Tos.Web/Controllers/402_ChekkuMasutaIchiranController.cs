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
using System.IO;

namespace Tos.Web.Controllers
{
    //created from 【SearchInputController(Ver1.7)】 Template
    public class _402_ChekkuMasutaIchiranController : ApiController
    {
        #region "Controllerで公開するAPI"


        /// <summary>
        /// パラメータで受け渡された 値に該当するすべてのtarget情報を StoredProcedure を利用して取得します。
        /// </summary>
        /// <param name="options"></param>
        /// <returns>target情報</returns>
        /// <author>Nam.PT</author>
        /// <date>11/03/2021</date>
        public StoredProcedureResult<sp_chekkumasutaichiran_search_402_Result> Get([FromUri]ChekkuMasutaIchiranCriteria option)
        {
            // TODO:target情報を管理しているDbContextとtargetの型を指定します。
            ShohinKaihatsuEntities context = new ShohinKaihatsuEntities();
            context.Configuration.ProxyCreationEnabled = false;
            StoredProcedureResult<sp_chekkumasutaichiran_search_402_Result> result = new StoredProcedureResult<sp_chekkumasutaichiran_search_402_Result>();
            result.Items = context.sp_chekkumasutaichiran_search_402(
                option.no_gate
                , option.no_bunrui
                , option.nm_ari
                , option.nm_nashi
                , Properties.Settings.Default.cd_shain_0
                , Properties.Settings.Default.nen_0
                , Properties.Settings.Default.no_oi_0
                , Properties.Settings.Default.no_meisai_0
                , option.skip
                , option.top
                ).ToList();
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
        public HttpResponseMessage Post([FromBody]ChangeSet<ma_gate_check> value)
        {
            if (value == null)
            {
                return Request.CreateErrorResponse(HttpStatusCode.InternalServerError, Properties.Resources.NotNullAllow);
            }

            InvalidationSet<ma_gate_check> headerInvalidations = IsAlreadyExists(value);
            if (headerInvalidations.Count > 0)
            {
                return Request.CreateResponse<InvalidationSet<ma_gate_check>>(HttpStatusCode.BadRequest, headerInvalidations);
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
                        int idFunction = 2;
                        Decimal.TryParse(UserInfo.GetUserNameFromIdentity(this.User.Identity), out idKoshin);

                        if (value.Deleted.Count > 0)
                        {
                            foreach (var item in value.Deleted)
                            {
                                string folderName = item.no_gate + "_" + item.no_bunrui + "_" + item.no_check;
                                //string fileDir = HttpContext.Current.Server.MapPath(Properties.Settings.Default.ShisakuUploadFolder + folderName);
                                string fileDir = Properties.Settings.Default.ShisakuUploadFolder + folderName;
                                if (System.IO.Directory.Exists(fileDir))
                                {
                                    System.IO.Directory.Delete(fileDir, true);
                                }

                                context.sp_chekkumasutaichiran_delete_402(Properties.Settings.Default.cd_shain_0
                                                                        , Properties.Settings.Default.nen_0
                                                                        , Properties.Settings.Default.no_oi_0
                                                                        , item.no_gate
                                                                        , item.no_bunrui
                                                                        , item.no_check
                                                                        , Properties.Settings.Default.no_meisai_0);
                            }

                        }
                        if (value.Created.Count > 0 || value.Updated.Count == 1 || value.Deleted.Count > 0)
                        {
                            context.sp_shohinkaihatsu_insert_log_401(idFunction, null, date, idKoshin);
                        }

                        var result = SaveData(value);
                        transaction.Commit();
                        return Request.CreateResponse<ChekkuMasutaIchiranChangeResponse>(HttpStatusCode.OK, result);
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

        /// <summary>
        /// Delete temp file after processed
        /// </summary>
        /// <param name="tempFilePath"></param>
        /// <author>Nam.PT</author>
        /// <date>19/03/2021</date>
        private void deleteTempFile(string tempFilePath)
        {
            if (tempFilePath != string.Empty && File.Exists(tempFilePath))
            {
                File.Delete(tempFilePath);
            }

        }
        /// <summary>
        /// get primary file path = saved dir + file name
        /// </summary>
        /// <param name="data"></param>
        /// <returns></returns>
        ///  <author>Nam.PT</author>
        /// <date>19/03/2021</date>
        private string getFilePath(string folderName, string value)
        {
            if (string.IsNullOrEmpty(value))
            {
                return String.Empty;
            }
            //string fileDir = HttpContext.Current.Server.MapPath(Properties.Settings.Default.ShisakuUploadFolder);
            string fileDir = Properties.Settings.Default.ShisakuUploadFolder;
            return String.Format(fileDir + "{0}\\{1}", folderName, value);
        }
        #endregion

        #region "Controller内で利用する関数群"

        /// <summary>
        /// target 情報の主キーによる重複チェックを行います。
        /// </summary>
        /// <param name="value">target 情報の変更セット</param>
        /// <returns></returns>
        /// <author>Nam.PT</author>
        /// <date>11/03/2021</date>
        private InvalidationSet<ma_gate_check> IsAlreadyExists(ChangeSet<ma_gate_check> value)
        {
            InvalidationSet<ma_gate_check> result = new InvalidationSet<ma_gate_check>();

            using (ShohinKaihatsuEntities context = new ShohinKaihatsuEntities())
            {
                foreach (var item in value.Created)
                {
                    bool isDepulicate = false;

                    var createdCount = value.Created.Count(target => target.no_gate == item.no_gate
                                                                  && target.no_bunrui == item.no_bunrui
                                                                  && target.no_check == item.no_check);
                    var isDeleted = value.Deleted.Exists(target => target.no_gate == item.no_gate
                                                                && target.no_bunrui == item.no_bunrui
                                                                && target.no_check == item.no_check);
                    var isDatabaseExists = (context.ma_gate_check.Count(target => target.no_gate == item.no_gate
                                                                                   && target.no_bunrui == item.no_bunrui
                                                                                   && target.no_check == item.no_check) > 0);
                    isDepulicate |= (createdCount > 1);
                    isDepulicate |= (!isDeleted && createdCount == 1 && isDatabaseExists);

                    if (isDepulicate)
                    {
                        result.Add(new Invalidation<ma_gate_check>(Properties.Resources.ValidationKey, item, "no_gate"));
                    }
                }
            }

            return result;
        }

        /// <summary>
        /// Delete All data and files not exist in tr_gate_attachment
        /// </summary>
        /// <param name="isSekkei">TRUE: page 400, FALSE : page 402</param>
        /// <author>Nam.PT</author>
        /// <date>29/03/2021</date>
        [HttpPost]
        public HttpResponseMessage DeleteFiles([FromBody]KanrenShiryoRequest value)
        {
            using (ShohinKaihatsuEntities context = new ShohinKaihatsuEntities())
            {
                IObjectContextAdapter adapter = context as IObjectContextAdapter;
                DbConnection connection = adapter.ObjectContext.Connection;
                connection.Open();

                using (DbTransaction transaction = connection.BeginTransaction())
                {
                    try
                    {
                        //If the page call from 402
                        if (!value.isSekkei)
                        {
                            var dataDelete = context.vw_tr_gate_attachment.Where(n => n.id_koshin == value.userLogin).ToList();
                            foreach (var item in dataDelete)
                            {
                                string uploadedFilePath = String.Empty;
                                string folderName = item.no_gate + "_" + item.no_bunrui + "_" + item.no_check;
                                //string fileDir = HttpContext.Current.Server.MapPath(Properties.Settings.Default.ShisakuUploadFolder + folderName);
                                uploadedFilePath = getFilePath(folderName, item.nm_attach);
                                deleteTempFile(uploadedFilePath);
                                //Delete folder empty
                                if (System.IO.Directory.Exists(Properties.Settings.Default.ShisakuUploadFolder + folderName))
                                {
                                    string[] lstFiles = Directory.GetFiles(Properties.Settings.Default.ShisakuUploadFolder + folderName);
                                    if (lstFiles.Count() == 0)
                                    {
                                        Directory.Delete(Properties.Settings.Default.ShisakuUploadFolder + folderName);
                                    }
                                }
                            }
                            context.sp_kanrenshiryo_delete_402(value.userLogin);
                        }

                        transaction.Commit();
                    }
                    catch (Exception ex)
                    {
                        transaction.Rollback();
                        Logger.App.Error(ex.Message, ex);
                        return Request.CreateResponse(HttpStatusCode.BadRequest, ex);
                    }
                }
            }
            return Request.CreateResponse(HttpStatusCode.OK, "");
        }

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
        private ChekkuMasutaIchiranChangeResponse SaveData(ChangeSet<ma_gate_check> value)
        {
            using (ShohinKaihatsuEntities context = new ShohinKaihatsuEntities())
            {
                value.SetDataSaveInfo(this.User.Identity);
                value.AttachTo(context);
                context.SaveChanges();
            }
            var result = new ChekkuMasutaIchiranChangeResponse();
            result.GateCheck.AddRange(value.Flatten());
            return result;
        }
        #endregion
    }

    #region "ControllerのAPIで利用するリクエスト・レスポンスクラスです"

    public class ChekkuMasutaIchiranChangeResponse
    {
        public ChekkuMasutaIchiranChangeResponse()
        {
            this.GateCheck = new List<ma_gate_check>();
            this.GateAttachment = new List<tr_gate_attachment>();
        }

        public List<ma_gate_check> GateCheck { get; set; }
        public List<tr_gate_attachment> GateAttachment { get; set; }
    }

    public class ChekkuMasutaIchiranCriteria
    {
        public int no_gate { get; set; }
        public int no_bunrui { set; get; }
        public string nm_ari { set; get; }
        public string nm_nashi { set; get; }
        public int skip { set; get; }
        public int top { set; get; }
    }
    #endregion
}
