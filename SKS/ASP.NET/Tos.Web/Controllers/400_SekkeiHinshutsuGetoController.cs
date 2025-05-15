using System;
using System.Collections.Generic;
using System.Data.Entity.Infrastructure;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web;
using System.Web.Http;
using System.Web.Http.OData.Query;
using Tos.Web.Data;
using Tos.Web.Controllers;
using System.Web.Http.OData;
using System.Data.Common;
using System.Data.Objects;
using Tos.Web.Logging;
using System.IO;

namespace Tos.Web.Controllers
{
    //created from 【HeaderDetailController(Ver1.7)】 Template
    public class SekkeiHinshutsuGetoController : ApiController
    {
        private static List<OpeningSekkeiHinshutsuGeto> lstOpening = new List<OpeningSekkeiHinshutsuGeto>();
        #region "Controllerで公開するAPI"

        /// <summary>
        /// get data for each gate
        /// </summary>
        /// <param name="param"></param>
        /// <returns>sp_shohinkaihatsu_searchGate_400_Result</returns>
        public StoredProcedureResult<sp_shohinkaihatsu_searchGate_400_Result> Get([FromUri]paramSearchSekkeiHinshutsuGeto param)
        {
            using (ShohinKaihatsuEntities context = new ShohinKaihatsuEntities())
            {
                ((IObjectContextAdapter)context).ObjectContext.CommandTimeout = 0;
                context.Configuration.ProxyCreationEnabled = false;
                StoredProcedureResult<sp_shohinkaihatsu_searchGate_400_Result> result = new StoredProcedureResult<sp_shohinkaihatsu_searchGate_400_Result>();
                result.Items = context.sp_shohinkaihatsu_searchGate_400(param.cd_shain, param.nen, param.no_oi, param.no_gate, param.no_bunrui).ToList();
                return result;
            }
        }

        /// <summary>
        /// get data bunrui
        /// </summary>
        /// <param name="param"></param>
        /// <returns>list sp_shohinkaihatsu_getDataGateBunrui_400_Result</returns>
        public List<sp_shohinkaihatsu_getDataGateBunrui_400_Result> GetGateBunrui([FromUri]paramSearchSekkeiHinshutsuGeto param)
        {
            using (ShohinKaihatsuEntities context = new ShohinKaihatsuEntities())
            {
                ((IObjectContextAdapter)context).ObjectContext.CommandTimeout = 0;
                context.Configuration.ProxyCreationEnabled = false;
                List<sp_shohinkaihatsu_getDataGateBunrui_400_Result> result = new List<sp_shohinkaihatsu_getDataGateBunrui_400_Result>();
                result = context.sp_shohinkaihatsu_getDataGateBunrui_400(param.cd_shain, param.nen, param.no_oi, param.no_gate).ToList();
                return result;
            }
        }

        /// <summary>
        /// Set lstOpening variable for go to mode edit 
        /// </summary>
        [HttpGet]
        public OpeningSekkeiHinshutsuGeto UpdateOpening(string no_seiho, string id, string kaisha, string busho, string user)
        {
            var data = lstOpening.Where(x => x.no_seiho == no_seiho).FirstOrDefault();
            if (data == null)
            {
                data = new OpeningSekkeiHinshutsuGeto()
                {
                    no_seiho = no_seiho,
                    lastActive = DateTime.Now,
                    id = id,
                    kaisha = kaisha,
                    busho = busho,
                    user = user
                };
                lstOpening.Add(data);
                data.flgErr = true;
                return data;
            }
            if (DateTime.Now.Ticks - data.lastActive.Ticks > 5 * TimeSpan.TicksPerSecond || data.id == id)
            {
                data.lastActive = DateTime.Now;
                data.id = id;
                data.kaisha = kaisha;
                data.busho = busho;
                data.user = user;
                data.flgErr = true;
                return data;
            }
            data.flgErr = false;
            return data;
        }

        /// <summary>
        /// check Haita
        /// </summary>
        [HttpGet]
        public sp_shohinkaihatsu_haita_check_400_Result checkHaita(decimal cd_shain, decimal nen, decimal no_oi, decimal? EmployeeCD, bool isOpen)
        {
            using (ShohinKaihatsuEntities context = new ShohinKaihatsuEntities())
            {
                return context.sp_shohinkaihatsu_haita_check_400(cd_shain, nen, no_oi, EmployeeCD, isOpen).FirstOrDefault();
            }
        }

        /// <summary>
        /// user info
        /// </summary>
        [HttpGet]
        public vw_user_info userInfo(decimal cd_shain)
        {
            using (ShohinKaihatsuEntities context = new ShohinKaihatsuEntities())
            {
                vw_user_info user_info = new vw_user_info();
                user_info = context.vw_user_info.Where(w => w.id_user == cd_shain).FirstOrDefault();
                if (user_info != null)
                {
                    var ma_busho = context.ma_busho.Where(w => w.cd_kaisha == user_info.cd_kaisha && w.cd_busho == user_info.cd_busho).FirstOrDefault();
                    user_info.nm_busho = ma_busho.nm_busho;
                }
                return user_info;
            }
        }

        /// <summary>
        /// delete data unuse
        /// </summary>
        [HttpPost]
        public HttpResponseMessage deleteAttachment([FromBody]paramSearchSekkeiHinshutsuGeto param)
        {
            List<sp_shohinkaihatsu_getUnusedDataAttachment_400_Result> lstGateAttachment = new List<sp_shohinkaihatsu_getUnusedDataAttachment_400_Result>();
            ChangeSet<tr_gate_attachment> gateAttachment = new ChangeSet<tr_gate_attachment>();
            string deletedFolderPath = Properties.Settings.Default.ShisakuUploadFolder + "Temp" + "/" + DateTime.Now.Ticks.ToString();
            string fileDir = Properties.Settings.Default.ShisakuUploadFolder;
            if (!System.IO.Directory.Exists(deletedFolderPath))
            {
                System.IO.Directory.CreateDirectory(deletedFolderPath);
            }
            List<string> lstDel = new List<string>();
            using (ShohinKaihatsuEntities context = new ShohinKaihatsuEntities())
            {
                IObjectContextAdapter adapter = context as IObjectContextAdapter;
                DbConnection connection = adapter.ObjectContext.Connection;
                connection.Open();
                using (DbTransaction transaction = connection.BeginTransaction())
               {
                   try
                   {
                       lstGateAttachment = context.sp_shohinkaihatsu_getUnusedDataAttachment_400(param.cd_shain, param.nen, param.no_oi).ToList();
                       string folder = "";
                       foreach (var item in lstGateAttachment)
                       {
                           gateAttachment.Deleted.Add(new tr_gate_attachment { 
                               cd_shain = item.cd_shain,
                               nen = item.nen,
                               no_oi = item.no_oi,
                               no_gate = item.no_gate,
                               no_bunrui = item.no_bunrui,
                               no_check = item.no_check,
                               no_meisai = item.no_meisai,
                               no_attach = item.no_attach,
                               kbn_attach = item.kbn_attach,
                               nm_attach = item.nm_attach,
                               nm_attach_note = item.nm_attach_note,
                               dt_toroku = item.dt_toroku,
                               id_toroku = item.id_toroku,
                               dt_koshin = item.dt_koshin,
                               id_koshin = item.id_koshin,
                               ts = item.ts
                           });
                           if (folder != item.cd_shain.ToString() + "_" + item.nen.ToString() + "_" + item.no_oi.ToString() + "_" + item.no_gate.ToString() + "_" + item.no_bunrui.ToString() + "_" + item.no_meisai.ToString())
                           {
                               folder = item.cd_shain.ToString() + "_" + item.nen.ToString() + "_" + item.no_oi.ToString() + "_" + item.no_gate.ToString() + "_" + item.no_bunrui.ToString() + "_" + item.no_meisai.ToString();
                               if (System.IO.Directory.Exists(fileDir + folder))
                               {
                                   Directory.Move(fileDir + folder, deletedFolderPath + "/" + folder);
                               }
                               
                               lstDel.Add(folder);
                           }
                       }
                       decimal userName = 0;
                       Decimal.TryParse(UserInfo.GetUserNameFromIdentity(this.User.Identity), out userName);
                       DateTime date = DateTime.Now;
                       SaveAttachment(context, gateAttachment, userName, date);
                       Directory.Delete(deletedFolderPath, true);
                       transaction.Commit();
                   }
                   catch (Exception ex)
                   {
                       Logger.App.Error(ex.Message, ex);
                       transaction.Rollback();
                       foreach (var item in lstDel)
                       {
                           Directory.Move(deletedFolderPath + "\\" + item, fileDir + item);
                       }
                       Directory.Delete(deletedFolderPath, true);
                       return Request.CreateErrorResponse(HttpStatusCode.BadRequest, ex.Message);
                   }
                }
            }
            return Request.CreateResponse(HttpStatusCode.OK, "");
        }

        /// <summary>
        /// パラメータで受け渡されたheader情報・detail情報をもとにエントリーheader情報・detail情報を一括更新（追加・更新・削除）します。
        /// </summary>
        /// <param name="value"></param>
        /// <returns></returns>
        public HttpResponseMessage Post([FromBody]ChangeRequestSekkeiHinshutsuGeto value)
        {

            if (value == null)
            {
                return Request.CreateErrorResponse(HttpStatusCode.InternalServerError, Properties.Resources.NotNullAllow);
            }
            
            InvalidationSet<tr_gate_meisai> invalidations = IsAlreadyExists(value);
            if (invalidations.Count > 0)
            {
                return Request.CreateResponse<InvalidationSet<tr_gate_meisai>>(HttpStatusCode.BadRequest, invalidations);
            }
            decimal userName = 0;
            Decimal.TryParse(UserInfo.GetUserNameFromIdentity(this.User.Identity), out userName);
            DateTime date = DateTime.Now;
            ChangeSet<tr_gate_attachment> gateAttachmentAllTab = setData(value.DetailAllTab.Deleted);
            ChangeSet<tr_gate_attachment> gateAttachmentBacterialcontrolTab = setData(value.DetailBacterialcontrolTab.Deleted);
            ChangeSet<tr_gate_attachment> gateAttachmentPriorconfirmationTab = setData(value.DetailPriorconfirmationTab.Deleted);
            ChangeSet<tr_gate_attachment> gateAttachmentValidityTab = setData(value.DetailValidityTab.Deleted);
            ChangeSet<tr_gate_bunrui> gateBunrui = new ChangeSet<tr_gate_bunrui>();
            string deletedFolderPath = Properties.Settings.Default.ShisakuUploadFolder +"Temp" + "/" + DateTime.Now.Ticks.ToString();
            string fileDir = Properties.Settings.Default.ShisakuUploadFolder;
            List<string> lstDel = new List<string>();
            if (!System.IO.Directory.Exists(deletedFolderPath))
            {
                System.IO.Directory.CreateDirectory(deletedFolderPath);
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
                       foreach (var item in value.LstGate)
                       {
                           tr_gate_bunrui bunrui = new tr_gate_bunrui();
                           bunrui = context.tr_gate_bunrui.Where(w => w.cd_shain == item.cd_shain && w.nen == item.nen && w.no_oi == item.no_oi && w.no_gate == item.no_gate).FirstOrDefault();
                           if (bunrui == null)
                           {
                               gateBunrui.Created.Add(new tr_gate_bunrui() { 
                                   cd_shain = item.cd_shain,
                                   nen = item.nen,
                                   no_oi = item.no_oi,
                                   no_gate = item.no_gate.Value,
                                   no_bunrui = item.no_bunrui
                               });
                           }
                           else
                           {
                               bunrui.no_bunrui = item.no_bunrui;
                               gateBunrui.Updated.Add(bunrui);
                           }
                       }
                       SaveHeader(context, value.Header);
                       SaveBunrui(context, gateBunrui, userName, date);
                       SaveDetail(context, value.DetailAllTab, userName, date);//全体
                       SaveAttachment(context, gateAttachmentAllTab, userName, date);//全体
                       string folder = "";
                       foreach (var item in gateAttachmentAllTab.Deleted)
                       {
                           // Move current folder to temp folder
                           folder = item.cd_shain.ToString() + "_" + item.nen.ToString() + "_" + item.no_oi.ToString() + "_" + item.no_gate.ToString() + "_" + item.no_bunrui.ToString() + "_" + item.no_meisai.ToString();
                           if (System.IO.Directory.Exists(fileDir + folder))
                           {
                               Directory.Move(fileDir + folder, deletedFolderPath + "/" + folder);
                               lstDel.Add(folder);
                               break;
                           }
                       }
                       SaveDetail(context, value.DetailBacterialcontrolTab, userName, date);//菌制御
                       SaveAttachment(context, gateAttachmentBacterialcontrolTab, userName, date);//菌制御
                       foreach (var item in gateAttachmentBacterialcontrolTab.Deleted)
                       {
                           // Move current folder to temp folder
                           folder = item.cd_shain.ToString() + "_" + item.nen.ToString() + "_" + item.no_oi.ToString() + "_" + item.no_gate.ToString() + "_" + item.no_bunrui.ToString() + "_" + item.no_meisai.ToString();
                           if (System.IO.Directory.Exists(fileDir + folder))
                           {
                               Directory.Move(fileDir + folder, deletedFolderPath + "/" + folder);
                               lstDel.Add(folder);
                               break;
                           }
                       }
                       SaveDetail(context, value.DetailPriorconfirmationTab, userName, date);//事前確認
                       SaveAttachment(context, gateAttachmentPriorconfirmationTab, userName, date);//事前確認
                       foreach (var item in gateAttachmentPriorconfirmationTab.Deleted)
                       {
                           // Move current folder to temp folder
                           folder = item.cd_shain.ToString() + "_" + item.nen.ToString() + "_" + item.no_oi.ToString() + "_" + item.no_gate.ToString() + "_" + item.no_bunrui.ToString() + "_" + item.no_meisai.ToString();
                           if (System.IO.Directory.Exists(fileDir + folder))
                           {
                               Directory.Move(fileDir + folder, deletedFolderPath + "/" + folder);
                               lstDel.Add(folder);
                               break;
                           }
                       }
                       SaveDetail(context, value.DetailValidityTab, userName, date);//妥当性
                       SaveAttachment(context, gateAttachmentValidityTab, userName, date);//妥当性
                       foreach (var item in gateAttachmentValidityTab.Deleted)
                       {
                           // Move current folder to temp folder
                           folder = item.cd_shain.ToString() + "_" + item.nen.ToString() + "_" + item.no_oi.ToString() + "_" + item.no_gate.ToString() + "_" + item.no_bunrui.ToString() + "_" + item.no_meisai.ToString();
                           if (System.IO.Directory.Exists(fileDir + folder))
                           {
                               Directory.Move(fileDir + folder, deletedFolderPath + "/" + folder);
                               lstDel.Add(folder);
                               break;
                           }
                       }
                       Directory.Delete(deletedFolderPath, true);
                       foreach (var item in value.LstGate)
                       {
                           context.sp_shohinkaihatsu_updateDataNewest_400(item.cd_shain, item.nen, item.no_oi, item.no_gate, item.no_bunrui, date, userName);
                       }
                       transaction.Commit();
                   }
                   catch (DbUpdateConcurrencyException ex)
                   {
                       Logger.App.Error(ex.Message, ex);
                       transaction.Rollback();
                       foreach (var item in lstDel)
                       {
                           Directory.Move(deletedFolderPath + "\\" + item, fileDir + item);
                       }
                       Directory.Delete(deletedFolderPath, true);
                       return Request.CreateErrorResponse(HttpStatusCode.Conflict, ex);
                   }
                   catch (Exception ex)
                   {
                       Logger.App.Error(ex.Message, ex);
                       transaction.Rollback();
                       foreach (var item in lstDel)
                       {
                           Directory.Move(deletedFolderPath + "\\" + item, fileDir + item);
                       }
                       if (System.IO.Directory.Exists(deletedFolderPath))
                       {
                           Directory.Delete(deletedFolderPath, true);
                       }
                       return Request.CreateErrorResponse(HttpStatusCode.ExpectationFailed, ex);
                   }
               }
            }
            return Request.CreateResponse(HttpStatusCode.OK, "");
        }

        #endregion

        #region "Controller内で利用する関数群"

        /// <summary>
        /// target 情報の主キーによる重複チェックを行います。
        /// </summary>
        /// <param name="value">target 情報の変更セット</param>
        /// <returns></returns>
        private InvalidationSet<tr_gate_meisai> IsAlreadyExists(ChangeRequestSekkeiHinshutsuGeto value)
        {

            InvalidationSet<tr_gate_meisai> result = new InvalidationSet<tr_gate_meisai>();

            using (ShohinKaihatsuEntities context = new ShohinKaihatsuEntities())
            {
                //全体
                foreach (var item in value.DetailAllTab.Created)
                {
                    bool isDepulicate = false;

                    var createdCount = value.DetailAllTab.Created.Count(target => target.cd_shain == item.cd_shain
                                                                                  && target.nen == item.nen
                                                                                  && target.no_oi == item.no_oi
                                                                                  && target.no_gate == item.no_gate
                                                                                  && target.no_bunrui == item.no_bunrui
                                                                                  && target.no_check == item.no_check
                                                                                  && target.no_meisai == item.no_meisai);
                    var isDeleted = value.DetailAllTab.Deleted.Exists(target => target.cd_shain == item.cd_shain
                                                                                  && target.nen == item.nen
                                                                                  && target.no_oi == item.no_oi
                                                                                  && target.no_gate == item.no_gate
                                                                                  && target.no_bunrui == item.no_bunrui
                                                                                  && target.no_check == item.no_check
                                                                                  && target.no_meisai == item.no_meisai);
                    var isDatabaseExists = (context.tr_gate_meisai.Find(item.cd_shain
                                                                        , item.nen
                                                                        , item.no_oi
                                                                        , item.no_gate
                                                                        , item.no_bunrui
                                                                        , item.no_check
                                                                        , item.no_meisai) != null);

                    isDepulicate |= (createdCount > 1);
                    isDepulicate |= (!isDeleted && createdCount == 1 && isDatabaseExists);

                    if (isDepulicate)
                    {
                        result.Add(new Invalidation<tr_gate_meisai>(Properties.Resources.ValidationKey, item, "cd_shain"));
                    }
                }
                //菌制御
                foreach (var item in value.DetailBacterialcontrolTab.Created)
                {
                    bool isDepulicate = false;

                    var createdCount = value.DetailBacterialcontrolTab.Created.Count(target => target.cd_shain == item.cd_shain
                                                                                  && target.nen == item.nen
                                                                                  && target.no_oi == item.no_oi
                                                                                  && target.no_gate == item.no_gate
                                                                                  && target.no_bunrui == item.no_bunrui
                                                                                  && target.no_check == item.no_check
                                                                                  && target.no_meisai == item.no_meisai);
                    var isDeleted = value.DetailBacterialcontrolTab.Deleted.Exists(target => target.cd_shain == item.cd_shain
                                                                                  && target.nen == item.nen
                                                                                  && target.no_oi == item.no_oi
                                                                                  && target.no_gate == item.no_gate
                                                                                  && target.no_bunrui == item.no_bunrui
                                                                                  && target.no_check == item.no_check
                                                                                  && target.no_meisai == item.no_meisai);
                    var isDatabaseExists = (context.tr_gate_meisai.Find(item.cd_shain
                                                                        , item.nen
                                                                        , item.no_oi
                                                                        , item.no_gate
                                                                        , item.no_bunrui
                                                                        , item.no_check
                                                                        , item.no_meisai) != null);

                    isDepulicate |= (createdCount > 1);
                    isDepulicate |= (!isDeleted && createdCount == 1 && isDatabaseExists);

                    if (isDepulicate)
                    {
                        result.Add(new Invalidation<tr_gate_meisai>(Properties.Resources.ValidationKey, item, "cd_shain"));
                    }
                }
                //事前確認
                foreach (var item in value.DetailPriorconfirmationTab.Created)
                {
                    bool isDepulicate = false;

                    var createdCount = value.DetailPriorconfirmationTab.Created.Count(target => target.cd_shain == item.cd_shain
                                                                                  && target.nen == item.nen
                                                                                  && target.no_oi == item.no_oi
                                                                                  && target.no_gate == item.no_gate
                                                                                  && target.no_bunrui == item.no_bunrui
                                                                                  && target.no_check == item.no_check
                                                                                  && target.no_meisai == item.no_meisai);
                    var isDeleted = value.DetailPriorconfirmationTab.Deleted.Exists(target => target.cd_shain == item.cd_shain
                                                                                  && target.nen == item.nen
                                                                                  && target.no_oi == item.no_oi
                                                                                  && target.no_gate == item.no_gate
                                                                                  && target.no_bunrui == item.no_bunrui
                                                                                  && target.no_check == item.no_check
                                                                                  && target.no_meisai == item.no_meisai);
                    var isDatabaseExists = (context.tr_gate_meisai.Find(item.cd_shain
                                                                        , item.nen
                                                                        , item.no_oi
                                                                        , item.no_gate
                                                                        , item.no_bunrui
                                                                        , item.no_check
                                                                        , item.no_meisai) != null);

                    isDepulicate |= (createdCount > 1);
                    isDepulicate |= (!isDeleted && createdCount == 1 && isDatabaseExists);

                    if (isDepulicate)
                    {
                        result.Add(new Invalidation<tr_gate_meisai>(Properties.Resources.ValidationKey, item, "cd_shain"));
                    }
                }
                //妥当性
                foreach (var item in value.DetailValidityTab.Created)
                {
                    bool isDepulicate = false;

                    var createdCount = value.DetailValidityTab.Created.Count(target => target.cd_shain == item.cd_shain
                                                                                  && target.nen == item.nen
                                                                                  && target.no_oi == item.no_oi
                                                                                  && target.no_gate == item.no_gate
                                                                                  && target.no_bunrui == item.no_bunrui
                                                                                  && target.no_check == item.no_check
                                                                                  && target.no_meisai == item.no_meisai);
                    var isDeleted = value.DetailValidityTab.Deleted.Exists(target => target.cd_shain == item.cd_shain
                                                                                  && target.nen == item.nen
                                                                                  && target.no_oi == item.no_oi
                                                                                  && target.no_gate == item.no_gate
                                                                                  && target.no_bunrui == item.no_bunrui
                                                                                  && target.no_check == item.no_check
                                                                                  && target.no_meisai == item.no_meisai);
                    var isDatabaseExists = (context.tr_gate_meisai.Find(item.cd_shain
                                                                        , item.nen
                                                                        , item.no_oi
                                                                        , item.no_gate
                                                                        , item.no_bunrui
                                                                        , item.no_check
                                                                        , item.no_meisai) != null);

                    isDepulicate |= (createdCount > 1);
                    isDepulicate |= (!isDeleted && createdCount == 1 && isDatabaseExists);

                    if (isDepulicate)
                    {
                        result.Add(new Invalidation<tr_gate_meisai>(Properties.Resources.ValidationKey, item, "cd_shain"));
                    }
                }
            }

            return result;
        }

        /// <summary>
        /// set data attachment in changeSet delete
        /// </summary>
        /// <param name="gateMeisai">List<tr_gate_meisai></param>
        private ChangeSet<tr_gate_attachment> setData( List<tr_gate_meisai> gateMeisai)
        {
            using (ShohinKaihatsuEntities context = new ShohinKaihatsuEntities())
            {
                ChangeSet<tr_gate_attachment> gateAttachment = new ChangeSet<tr_gate_attachment>();
                foreach (var item in gateMeisai)
                {
                    List<tr_gate_attachment> lstGate = context.tr_gate_attachment.Where(w => w.cd_shain == item.cd_shain && w.nen == item.nen && w.no_oi == item.no_oi
                                                                                         && w.no_gate == item.no_gate && w.no_bunrui == item.no_bunrui && w.no_check == item.no_check
                                                                                         && w.no_meisai == item.no_meisai).ToList();
                    foreach (var row in lstGate)
                    {
                        gateAttachment.Deleted.Add(row);
                    }
                }
                return gateAttachment;
            }
        }

        /// <summary>
        /// hedaer情報の一括更新（追加・更新・削除）を実行します
        /// </summary>
        /// <param name="context">DbContext</param>
        /// <param name="header">hedaer情報</param>
        private void SaveHeader(ShohinKaihatsuEntities context, ChangeSet<tr_gate_header> header)
        {
            header.SetDataSaveInfo(this.User.Identity);
            header.AttachTo(context);
            context.SaveChanges();
        }

        /// <summary>
        /// detail情報の一括更新（追加・更新・削除）を実行します
        /// </summary>
        /// <param name="context">DbContext</param>
        /// <param name="detail">detail情報</param>
        /// <param name="userName">userName</param>
        /// <param name="date">date</param>
        private void SaveDetail(ShohinKaihatsuEntities context, ChangeSet<tr_gate_meisai> detail, decimal userName, DateTime date)
        {
            //detail.SetDataSaveInfo(this.User.Identity);
            foreach (dynamic value in detail.Created)
            {
                // TODO: プロジェクトで利用するテーブルの共通項目に応じて変更します
                value.id_toroku = userName;
                value.dt_toroku = date;

                value.id_koshin = userName;
                value.dt_koshin = date;
            }
            foreach (dynamic value in detail.Updated)
            {
                // TODO: プロジェクトで利用するテーブルの共通項目に応じて変更します
                value.id_koshin = userName;
                value.dt_koshin = date;
            }
            detail.AttachTo(context);
            context.SaveChanges();
        }

        /// <summary>
        /// detail情報の一括更新（追加・更新・削除）を実行します
        /// </summary>
        /// <param name="context">DbContext</param>
        /// <param name="detail">detail情報</param>
        /// <param name="userName">userName</param>
        /// <param name="date">date</param>
        private void SaveAttachment(ShohinKaihatsuEntities context, ChangeSet<tr_gate_attachment> detail, decimal userName, DateTime date)
        {
            //detail.SetDataSaveInfo(this.User.Identity);
            foreach (dynamic value in detail.Created)
            {
                // TODO: プロジェクトで利用するテーブルの共通項目に応じて変更します
                value.id_toroku = userName;
                value.dt_toroku = date;

                value.id_koshin = userName;
                value.dt_koshin = date;
            }
            foreach (dynamic value in detail.Updated)
            {
                // TODO: プロジェクトで利用するテーブルの共通項目に応じて変更します
                value.id_koshin = userName;
                value.dt_koshin = date;
            }
            detail.AttachTo(context);
            context.SaveChanges();
        }

        /// <summary>
        /// detail情報の一括更新（追加・更新・削除）を実行します
        /// </summary>
        /// <param name="context">DbContext</param>
        /// <param name="detail">detail情報</param>
        /// <param name="userName">userName</param>
        /// <param name="date">date</param>
        private void SaveBunrui(ShohinKaihatsuEntities context, ChangeSet<tr_gate_bunrui> detail, decimal userName, DateTime date)
        {
            //detail.SetDataSaveInfo(this.User.Identity);
            foreach (dynamic value in detail.Created)
            {
                // TODO: プロジェクトで利用するテーブルの共通項目に応じて変更します
                value.id_toroku = userName;
                value.dt_toroku = date;

                value.id_koshin = userName;
                value.dt_koshin = date;
            }
            foreach (dynamic value in detail.Updated)
            {
                // TODO: プロジェクトで利用するテーブルの共通項目に応じて変更します
                value.id_koshin = userName;
                value.dt_koshin = date;
            }
            detail.AttachTo(context);
            context.SaveChanges();
        }
        #endregion
    }

    #region "ControllerのAPIで利用するリクエスト・レスポンスクラスです"

    public class ChangeRequestSekkeiHinshutsuGeto
    {
        public ChangeSet<tr_gate_header> Header { get; set; }
        public ChangeSet<tr_gate_meisai> DetailAllTab { get; set; }
        public ChangeSet<tr_gate_meisai> DetailBacterialcontrolTab { get; set; }
        public ChangeSet<tr_gate_meisai> DetailPriorconfirmationTab { get; set; }
        public ChangeSet<tr_gate_meisai> DetailValidityTab { get; set; }
        public List<paramSearchSekkeiHinshutsuGeto> LstGate { get; set; }
    }

    /// <summary>
    /// parameter search
    /// </summary>
    public class paramSearchSekkeiHinshutsuGeto
    {
        public decimal cd_shain { get; set; }
        public decimal nen { get; set; }
        public decimal no_oi { get; set; }
        public int? no_gate { get; set; }
        public int no_bunrui { get; set; }
    }

    // <summary>
    /// parameter check haita
    /// </summary>
    public class OpeningSekkeiHinshutsuGeto
    {
        public string no_seiho { get; set; }
        public DateTime lastActive { get; set; }
        public string id { get; set; }
        public string kaisha { get; set; }
        public string busho { get; set; }
        public string user { get; set; }
        public bool flgErr { get; set; }
    }

    /// <summary>
    /// infor user using shisakuhin
    /// </summary>
    public class inforDataHaita
    {
        public string userHaitaKaisha { get; set; }
        public string userHaitaBusho { get; set; }
        public string userHaitaName { get; set; }
    }

    #endregion
}
