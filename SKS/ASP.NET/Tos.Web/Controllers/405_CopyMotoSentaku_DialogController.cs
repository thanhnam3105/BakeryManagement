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
using Tos.Web.Logging;
using Tos.Web.Controllers.Helpers;
using System.IO;

namespace Tos.Web.Controllers
{
    //created from 【HeaderDetailController(Ver1.7)】 Template
    public class CopyMotoSentaku_DialogController : ApiController
    {
        #region "Controllerで公開するAPI"

        /// <summary>
        /// copy data
        /// </summary>
        /// <param name="value"></param>
        /// <returns></returns>
        public HttpResponseMessage Post([FromBody]ChangeRequestCopyMotoSentaku value)
        {

            if (value == null)
            {
                return Request.CreateErrorResponse(HttpStatusCode.InternalServerError, Properties.Resources.NotNullAllow);
            }
            string deletedFolderPath = Properties.Settings.Default.ShisakuUploadFolder + "Temp" + "/" + DateTime.Now.Ticks.ToString();
            if (!System.IO.Directory.Exists(deletedFolderPath))
            {
                System.IO.Directory.CreateDirectory(deletedFolderPath);
            }
            string fileDir = Properties.Settings.Default.ShisakuUploadFolder;
            ChangeSet<tr_gate_header> gateHeader = new ChangeSet<tr_gate_header>();
            ChangeSet<tr_gate_meisai> gateMeisai = new ChangeSet<tr_gate_meisai>();
            ChangeSet<tr_gate_attachment> gateAttachment = new ChangeSet<tr_gate_attachment>();
            ChangeSet<tr_gate_bunrui> gateBunrui = new ChangeSet<tr_gate_bunrui>();
            //Source
            List<tr_gate_header> lstGateHeaderSource = new List<tr_gate_header>();
            List<tr_gate_meisai> lstGateMeisaiSource = new List<tr_gate_meisai>();
            List<tr_gate_attachment> lstGateAttachmentSource = new List<tr_gate_attachment>();
            List<tr_gate_bunrui> lstGateBunruiSource = new List<tr_gate_bunrui>();
            //Destination
            List<tr_gate_header> lstGateHeaderDestination = new List<tr_gate_header>();
            List<tr_gate_meisai> lstGateMeisaiDestination = new List<tr_gate_meisai>();
            List<tr_gate_attachment> lstGateAttachmentDestination = new List<tr_gate_attachment>();
            List<tr_gate_bunrui> lstGateBunruiDestination = new List<tr_gate_bunrui>();
            NoSeiho noSeihoSource = value.noSeihoSource;
            NoSeiho noSeihoDestination = value.noSeihoDestination;
            List<string> lstDel = new List<string>();
            List<string> lstAdd = new List<string>();
            string folder = "";
            using (ShohinKaihatsuEntities context = new ShohinKaihatsuEntities())
            {
                //gate data source
                lstGateHeaderSource = context.tr_gate_header.Where(w => w.cd_shain == noSeihoSource.cd_shain && w.nen == noSeihoSource.nen && w.no_oi == noSeihoSource.no_oi).ToList();
                lstGateMeisaiSource = context.tr_gate_meisai.Where(w => w.cd_shain == noSeihoSource.cd_shain && w.nen == noSeihoSource.nen && w.no_oi == noSeihoSource.no_oi).ToList();
                lstGateAttachmentSource = context.tr_gate_attachment.Where(w => w.cd_shain == noSeihoSource.cd_shain && w.nen == noSeihoSource.nen && w.no_oi == noSeihoSource.no_oi).ToList();
                lstGateBunruiSource = context.tr_gate_bunrui.Where(w => w.cd_shain == noSeihoSource.cd_shain && w.nen == noSeihoSource.nen && w.no_oi == noSeihoSource.no_oi).ToList();
                //gate data destination
                lstGateHeaderDestination = context.tr_gate_header.Where(w => w.cd_shain == noSeihoDestination.cd_shain && w.nen == noSeihoDestination.nen && w.no_oi == noSeihoDestination.no_oi).ToList();
                lstGateMeisaiDestination = context.tr_gate_meisai.Where(w => w.cd_shain == noSeihoDestination.cd_shain && w.nen == noSeihoDestination.nen && w.no_oi == noSeihoDestination.no_oi).ToList();
                lstGateAttachmentDestination = context.tr_gate_attachment.Where(w => w.cd_shain == noSeihoDestination.cd_shain && w.nen == noSeihoDestination.nen && w.no_oi == noSeihoDestination.no_oi).ToList();
                lstGateBunruiDestination = context.tr_gate_bunrui.Where(w => w.cd_shain == noSeihoDestination.cd_shain && w.nen == noSeihoDestination.nen && w.no_oi == noSeihoDestination.no_oi).ToList();
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
                       //delete data old(data destination)
                       gateHeader.Deleted.AddRange(lstGateHeaderDestination);
                       gateMeisai.Deleted.AddRange(lstGateMeisaiDestination);
                       gateAttachment.Deleted.AddRange(lstGateAttachmentDestination);
                       gateBunrui.Deleted.AddRange(lstGateBunruiDestination);
                       foreach (var item in lstGateAttachmentDestination)
                       {
                            // Move current folder to temp folder
                            folder = item.cd_shain.ToString() + "_" + item.nen.ToString() + "_" + item.no_oi.ToString() + "_" + item.no_gate.ToString() + "_" + item.no_bunrui.ToString() + "_" + item.no_meisai.ToString();
                            if (System.IO.Directory.Exists(fileDir + folder))
                            {
                                Directory.Move(fileDir + folder, deletedFolderPath + "/" + folder);
                                lstDel.Add(folder);
                            }
                       }
                       //add data copy
                       folder = "";
                       string folderCopy = "";
                       foreach (var item in lstGateHeaderSource)
                       {
                           //tr_gate_header
                           item.cd_shain = noSeihoDestination.cd_shain;
                           item.nen = noSeihoDestination.nen;
                           item.no_oi = noSeihoDestination.no_oi;
                           gateHeader.Created.Add(item);
                       }
                       foreach (var item in lstGateMeisaiSource)
                       {
                           //tr_gate_meisai
                           item.cd_shain = noSeihoDestination.cd_shain;
                           item.nen = noSeihoDestination.nen;
                           item.no_oi = noSeihoDestination.no_oi;
                           gateMeisai.Created.Add(item);
                       }
                       foreach (var item in lstGateAttachmentSource)
                       {
                           folderCopy = item.cd_shain.ToString() + "_" + item.nen.ToString() + "_" + item.no_oi.ToString() + "_" + item.no_gate.ToString() + "_" + item.no_bunrui.ToString() + "_" + item.no_meisai.ToString();
                           item.cd_shain = noSeihoDestination.cd_shain;
                           item.nen = noSeihoDestination.nen;
                           item.no_oi = noSeihoDestination.no_oi;
                           gateAttachment.Created.Add(item);
                           //copy file to folder destination
                           if (item.kbn_attach == false)
                           {
                               folder = item.cd_shain.ToString() + "_" + item.nen.ToString() + "_" + item.no_oi.ToString() + "_" + item.no_gate.ToString() + "_" + item.no_bunrui.ToString() + "_" + item.no_meisai.ToString();
                               if (!System.IO.Directory.Exists(fileDir + folder))
                               {
                                   System.IO.Directory.CreateDirectory(fileDir + folder);
                                   lstAdd.Add(folder);
                               }
                               File.Copy(fileDir + folderCopy + "/" + item.nm_attach, fileDir + folder + "/" + item.nm_attach);
                           }

                       }
                       foreach (var item in lstGateBunruiSource)
                       {
                           //tr_gate_bunrui
                           item.cd_shain = noSeihoDestination.cd_shain;
                           item.nen = noSeihoDestination.nen;
                           item.no_oi = noSeihoDestination.no_oi;
                           gateBunrui.Created.Add(item);
                       }
                       SaveHeader(context, gateHeader);
                       SaveMeisai(context, gateMeisai);
                       SaveAttachment(context, gateAttachment);
                       SaveBunrui(context, gateBunrui);
                       Directory.Delete(deletedFolderPath, true);
                       transaction.Commit();
                   }
                   catch (Exception ex)
                   {
                       Logger.App.Error(ex.Message, ex);
                       transaction.Rollback();
                       foreach (var item in lstAdd)
                       {
                           Directory.Delete(fileDir + item, true);
                       }
                       foreach (var item in lstDel)
                       {
                           Directory.Move(deletedFolderPath + "\\" + item, fileDir + item);
                       }
                       Directory.Delete(deletedFolderPath, true);
                       return Request.CreateErrorResponse(HttpStatusCode.BadRequest, ex);
                   }
               }
            }

            return Request.CreateResponse(HttpStatusCode.OK, "");
        }

        #endregion

        #region "Controller内で利用する関数群"

        /// <summary>
        /// header情報の一括更新（追加・更新・削除）を実行します
        /// </summary>
        /// <param name="context">DbContext</param>
        /// <param name="detail">header情報</param>
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
        private void SaveMeisai(ShohinKaihatsuEntities context, ChangeSet<tr_gate_meisai> detail)
        {
            detail.SetDataSaveInfo(this.User.Identity);
            detail.AttachTo(context);
            context.SaveChanges();
        }

        /// <summary>
        /// detail情報の一括更新（追加・更新・削除）を実行します
        /// </summary>
        /// <param name="context">DbContext</param>
        /// <param name="detail">detail情報</param>
        private void SaveAttachment(ShohinKaihatsuEntities context, ChangeSet<tr_gate_attachment> detail)
        {
            detail.SetDataSaveInfo(this.User.Identity);
            detail.AttachTo(context);
            context.SaveChanges();
        }

        /// <summary>
        /// detail情報の一括更新（追加・更新・削除）を実行します
        /// </summary>
        /// <param name="context">DbContext</param>
        /// <param name="detail">detail情報</param>
        private void SaveBunrui(ShohinKaihatsuEntities context, ChangeSet<tr_gate_bunrui> detail)
        {
            detail.SetDataSaveInfo(this.User.Identity);
            detail.AttachTo(context);
            context.SaveChanges();
        }
        #endregion
    }

    #region "ControllerのAPIで利用するリクエスト・レスポンスクラスです"

    public class ChangeRequestCopyMotoSentaku
    {
        public NoSeiho noSeihoSource { get; set; }
        public NoSeiho noSeihoDestination { get; set; }
    }

    public class NoSeiho
    {
        public decimal cd_shain { get; set; }
        public decimal nen { get; set; }
        public decimal no_oi { get; set; }
    }

    #endregion
}
