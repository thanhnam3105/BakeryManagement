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
using Tos.Web.Controllers.Helpers;
using System.Data.Entity.Infrastructure;
using System.Data.Common;
using Tos.Web.Logging;
using System.IO;

namespace Tos.Web.Controllers
{
    //created from 【SearchInputController(Ver1.7)】 Template
    public class _403_KanrenShiryoController : ApiController
    {
        #region "Controllerで公開するAPI"

        /// <summary>
        /// パラメータで受け渡された 値に該当するすべてのtarget情報を StoredProcedure を利用して取得します。
        /// </summary>
        /// <param name="options"></param>
        /// <returns>target情報</returns>
        /// <author>Nam.PT</author>
        /// <date>19/03/2021</date>
        public KanrenShiryoChangeResponse Get([FromUri]KanrenShiryoCriteria option)
        {
            using (ShohinKaihatsuEntities context = new ShohinKaihatsuEntities())
            {
                KanrenShiryoChangeResponse result = new KanrenShiryoChangeResponse();

                var data = context.sp_kanrenshiryo_search_403(option.cd_shain
                                                            , option.nen
                                                            , option.no_oi
                                                            , option.no_gate
                                                            , option.no_bunrui
                                                            , option.no_check
                                                            , option.no_meisai
                                                            , option.cd_kaisha).ToList();
                if (result.Items.Count() > 0)
                {
                    result.Count = (int)data.First().cnt;
                }
                for (int i = 0; i < data.Count; i++)
                {
                    var data2 = data[i];
                    tr_gate_attachment_new item = new tr_gate_attachment_new();
                    DataCopier.ReFill(data2, item);
                    item.ts = data2.ts.ToArray();
                    item.isSekkei = option.isSekkei;
                    item.kbn_btn = option.kbn_btn;
                    item.mode = option.mode;

                    string folderName = item.no_gate + "_" + item.no_bunrui + "_" + item.no_check;

                    //If the page call from 400
                    if (option.isSekkei && option.kbn_btn == 1)
                    {
                        folderName = item.cd_shain + "_" + item.nen + "_" + item.no_oi + "_" + item.no_gate + "_" + item.no_bunrui + "_" + item.no_meisai;
                    }

                    string filePath = getFilePath(folderName, item.nm_attach);

                    if (filePath != String.Empty && File.Exists(filePath))
                    {
                        item.isExistFile = true;
                    }
                    result.Items.Add(item);
                }
                return result;
            }
        }

        /// <summary>
        /// Save change data
        /// Process for save btn -> insert new row to DB and new file
        /// </summary>
        /// <param name="value"></param>
        /// <returns></returns>
        /// <author>Nam.PT</author>
        /// <date>19/03/2021</date>
        [HttpPost]
        public HttpResponseMessage Post([FromBody]KanrenShiryoRequest data)
        {
            if (data == null)
            {
                return Request.CreateErrorResponse(HttpStatusCode.InternalServerError, Properties.Resources.NotNullAllow);
            }

            InvalidationSet<tr_gate_attachment> headerInvalidations = IsAlreadyExists(data.value);
            if (headerInvalidations.Count > 0)
            {
                return Request.CreateResponse<InvalidationSet<tr_gate_attachment>>(HttpStatusCode.BadRequest, headerInvalidations);
            }

            if (data.value.Created.Count > 0)
            {
                var item = data.value.Created[0];
                using (ShohinKaihatsuEntities context = new ShohinKaihatsuEntities())
                {
                    var isNameFileExists = context.tr_gate_attachment.Where(target => target.no_gate == item.no_gate
                                                                        && target.nen == item.nen
                                                                        && target.no_oi == item.no_oi
                                                                        && target.cd_shain == item.cd_shain
                                                                        && target.no_meisai == item.no_meisai
                                                                        && target.no_bunrui == item.no_bunrui
                                                                        && target.no_check == item.no_check
                                                                        && target.nm_attach == item.nm_attach).ToList();
                    if (isNameFileExists.Count() > 0)
                    {
                        data.value.Deleted.AddRange(isNameFileExists);
                        if (!data.allowEdit && data.isSekkei)
                        {
                            bool err = false;
                            foreach (var dataItem in isNameFileExists)
                            {
                                if (dataItem.id_toroku != data.userLogin)
                                {
                                    err = true;
                                }
                            }
                            if (err)
                            {
                                return Request.CreateErrorResponse(HttpStatusCode.BadRequest, item.kbn_attach == true ? "AP0172" : "AP0183");
                            }
                        }
                    }
                }
            }

            using (ShohinKaihatsuEntities context = new ShohinKaihatsuEntities())
            {
                IObjectContextAdapter adapter = context as IObjectContextAdapter;
                DbConnection connection = adapter.ObjectContext.Connection;
                connection.Open();

                using (DbTransaction transaction = connection.BeginTransaction())
                {
                    string tempFilePath = data.tempFilePath;
                    string uploadedFilePath = String.Empty;
                    string deletedFilePath = getTempPath(DateTime.Now.Ticks.ToString());

                    try
                    {
                        foreach (var item in data.value.Created)
                        {
                            string folderName = item.no_gate + "_" + item.no_bunrui + "_" + item.no_check;

                            //If the page call from 400
                            if (data.isSekkei)
                            {
                                folderName = item.cd_shain + "_" + item.nen + "_" + item.no_oi + "_" + item.no_gate + "_" + item.no_bunrui + "_" + item.no_meisai;
                            }

                            if (item.kbn_attach == false)
                            {
                                uploadedFilePath = getFilePath(folderName, item.nm_attach);
                                if (File.Exists(uploadedFilePath))
                                {
                                    File.Move(uploadedFilePath, deletedFilePath);
                                }

                                createFolder(folderName);
                                // Move new file to primary folder
                                File.Move(tempFilePath, uploadedFilePath);

                                // Delete old file when the process is done
                                deleteTempFile(deletedFilePath);
                            }
                        }
                        data.value.SetDataSaveInfo(this.User.Identity);
                        data.value.AttachTo(context);
                        context.SaveChanges();
                        transaction.Commit();
                    }
                    catch (DbUpdateConcurrencyException ex)
                    {
                        // 排他エラー
                        transaction.Rollback();
                        Logger.App.Error(ex.Message, ex);
                        if (data != null)
                        {
                            // Delete new file
                            deleteTempFile(data.tempFilePath);
                            deleteTempFile(uploadedFilePath);
                            // Recovery old file
                            if (File.Exists(deletedFilePath))
                            {
                                File.Move(deletedFilePath, uploadedFilePath);
                            }
                        }
                        return Request.CreateErrorResponse(HttpStatusCode.Conflict, Properties.Resources.ServiceError);
                    }
                    catch (Exception ex)
                    {
                        // 例外をエラーログに出力します。
                        transaction.Rollback();
                        Logger.App.Error(ex.Message, ex);
                        if (data != null)
                        {
                            // Delete new file
                            deleteTempFile(data.tempFilePath);
                            deleteTempFile(uploadedFilePath);
                            // Recovery old file
                            if (File.Exists(deletedFilePath))
                            {
                                File.Move(deletedFilePath, uploadedFilePath);
                            }
                        }
                        return Request.CreateResponse(HttpStatusCode.BadRequest, ex);
                    }
                }

            }
            return Request.CreateResponse(HttpStatusCode.OK, "");
        }

        /// <summary>
        /// Process for delete btn -> remove the data in DB then remove the file
        /// </summary>
        /// <param name="data"></param>
        /// <returns></returns>
        ///  <author>Nam.PT</author>
        /// <date>19/03/2021</date>
        [HttpPost]
        public HttpResponseMessage Delete([FromBody]KanrenShiryoRequest data)
        {
            using (ShohinKaihatsuEntities context = new ShohinKaihatsuEntities())
            {
                IObjectContextAdapter adapter = context as IObjectContextAdapter;
                DbConnection connection = adapter.ObjectContext.Connection;
                connection.Open();

                using (DbTransaction transaction = connection.BeginTransaction())
                {
                    string uploadedFilePath = String.Empty;
                    try
                    {
                        data.value.AttachTo(context);
                        context.SaveChanges();

                        foreach (var item in data.value.Deleted)
                        {
                            string folderName = item.no_gate + "_" + item.no_bunrui + "_" + item.no_check;

                            //If the page call from 400
                            if (data.isSekkei)
                            {
                                folderName = item.cd_shain + "_" + item.nen + "_" + item.no_oi + "_" + item.no_gate + "_" + item.no_bunrui + "_" + item.no_meisai;
                            }

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
                        transaction.Commit();
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
            return Request.CreateResponse(HttpStatusCode.OK, "");
        }

        

        /// <summary>
        /// target 情報の主キーによる重複チェックを行います。
        /// </summary>
        /// <param name="value">target 情報の変更セット</param>
        /// <returns></returns>
        /// <author>Nam.PT</author>
        /// <date>11/03/2021</date>
        private InvalidationSet<tr_gate_attachment> IsAlreadyExists(ChangeSet<tr_gate_attachment> value)
        {
            InvalidationSet<tr_gate_attachment> result = new InvalidationSet<tr_gate_attachment>();

            using (ShohinKaihatsuEntities context = new ShohinKaihatsuEntities())
            {
                foreach (var item in value.Created)
                {
                    bool isDepulicate = false;

                    var createdCount = value.Created.Count(target => target.no_gate == item.no_gate
                                                                  && target.nen == item.nen
                                                                  && target.no_oi == item.no_oi
                                                                  && target.cd_shain == item.cd_shain
                                                                  && target.no_meisai == item.no_meisai
                                                                  && target.no_attach == item.no_attach
                                                                  && target.no_bunrui == item.no_bunrui
                                                                  && target.no_check == item.no_check);
                    var isDeleted = value.Deleted.Exists(target => target.no_gate == item.no_gate
                                                                  && target.nen == item.nen
                                                                  && target.no_oi == item.no_oi
                                                                  && target.cd_shain == item.cd_shain
                                                                  && target.no_meisai == item.no_meisai
                                                                  && target.no_attach == item.no_attach
                                                                  && target.no_bunrui == item.no_bunrui
                                                                  && target.no_check == item.no_check);
                    var isDatabaseExists = (context.tr_gate_attachment.Count(target => target.no_gate == item.no_gate
                                                                            && target.nen == item.nen
                                                                            && target.no_oi == item.no_oi
                                                                            && target.cd_shain == item.cd_shain
                                                                            && target.no_meisai == item.no_meisai
                                                                            && target.no_attach == item.no_attach
                                                                            && target.no_bunrui == item.no_bunrui
                                                                            && target.no_check == item.no_check) > 0);
                    isDepulicate |= (createdCount > 1);
                    isDepulicate |= (!isDeleted && createdCount == 1 && isDatabaseExists);

                    if (isDepulicate)
                    {
                        result.Add(new Invalidation<tr_gate_attachment>(Properties.Resources.ValidationKey, item, "no_gate"));
                    }
                }
            }

            return result;
        }

        /// <summary>
        /// Check Exist file 
        /// </summary>
        /// <param name="data"></param>
        /// <returns></returns>
        /// <author>Nam.PT</author>
        /// <date>29/03/2021</date>
        private bool IsExistFile(tr_gate_attachment data) {
            bool result = false;
            using (ShohinKaihatsuEntities context = new ShohinKaihatsuEntities())
            {
                var model = context.tr_gate_attachment.Where(n => n.cd_shain == data.cd_shain
                                                                && n.nen == data.nen
                                                                && n.no_oi == data.no_oi
                                                                && n.no_gate == data.no_gate
                                                                && n.no_bunrui == data.no_bunrui
                                                                && n.no_check == data.no_check
                                                                && n.no_meisai == data.no_meisai
                                                                && n.kbn_attach == false
                                                                && n.nm_attach == data.nm_attach);
                if (model.Count() > 0) {
                    result = true;
                }
            }
            return result;
        }

        /// <summary>
        /// Get Max No Attach
        /// </summary>
        /// <param name="cd_shain"></param>
        /// <param name="nen"></param>
        /// <param name="no_oi"></param>
        /// <param name="no_gate"></param>
        /// <param name="no_bunrui"></param>
        /// <param name="no_check"></param>
        /// <param name="no_meisai"></param>
        /// <returns></returns>
        /// <author>Nam.PT</author>
        /// <date>23/03/2021</date>
        [HttpGet]
        public int GetMaxNoAttach(decimal cd_shain, decimal nen, decimal no_oi, int no_gate, int no_bunrui, int no_check, int no_meisai)
        {
            int result = 1;
            using (ShohinKaihatsuEntities context = new ShohinKaihatsuEntities())
            {
                var model = context.tr_gate_attachment.Where(n => n.cd_shain == cd_shain
                                                            && n.nen == nen
                                                            && n.no_oi == no_oi
                                                            && n.no_gate == no_gate
                                                            && n.no_bunrui == no_bunrui
                                                            && n.no_check == no_check
                                                            && n.no_meisai == no_meisai).ToList();

                if (model.Count() > 0)
                {
                    result = model.Max(n => n.no_attach) + 1;
                }
                return result;
            }
        }

        /// <summary>
        /// Upload file to temp table
        /// </summary>
        /// <returns></returns>
        ///  <author>Nam.PT</author>
        /// <date>19/03/2021</date>
        public string Upload()
        {
            string mapPath = HttpContext.Current.Server.MapPath(Properties.Settings.Default.UploadTempFolder);
            MultipartFormDataStreamProvider streamProvider = FileUploadDownloadUtility.ReadAsMultiPart(Request, mapPath);
            MultipartFileData file = streamProvider.FileData.FirstOrDefault();
            string newTempFile = getTempPath(DateTime.Now.Ticks.ToString());
            try
            {
                if (file != null && file.LocalFileName != null)
                {
                    File.Move(file.LocalFileName, newTempFile);
                }
                return newTempFile;
            }
            catch (Exception ex)
            {
                if (file != null && file.LocalFileName != null)
                {
                    deleteTempFile(file.LocalFileName);
                }
                throw ex;
            }
        }

        /// <summary>
        /// Download file
        /// </summary>
        /// <param name="data"></param>
        /// <returns></returns>
        ///  <author>Nam.PT</author>
        /// <date>19/03/2021</date>
        [HttpPost]
        public HttpResponseMessage DowloadFile([FromBody]tr_gate_attachment_new data)
        {
            MemoryStream stream = new MemoryStream();
            string folderName = data.no_gate + "_" + data.no_bunrui + "_" + data.no_check;
            if (data.isSekkei && data.kbn_btn == 1)
            {
                folderName = data.cd_shain + "_" + data.nen + "_" + data.no_oi + "_" + data.no_gate + "_" + data.no_bunrui + "_" + data.no_meisai;
            }
            string filePath = getFilePath(folderName, data.nm_attach);
            if (!File.Exists(filePath))
            {
                return Request.CreateResponse(HttpStatusCode.BadRequest, "AP0147");
            }
            // Write file to stream
            using (FileStream file = new FileStream(filePath, FileMode.Open, FileAccess.Read))
            {
                byte[] bytes = new byte[file.Length];
                file.Read(bytes, 0, (int)file.Length);
                stream.Write(bytes, 0, (int)file.Length);
            }
            // Return the file
            return FileUploadDownloadUtility.CreateFileResponse(stream, data.nm_attach);
        }

        /// <summary>
        /// get temp file path = UploadTempFolder + tempfile
        /// </summary>
        /// <param name="fileName"></param>
        /// <returns></returns>
        ///  <author>Nam.PT</author>
        /// <date>19/03/2021</date>
        private string getTempPath(string fileName)
        {
            if (fileName == null || fileName == String.Empty)
            {
                return String.Empty;
            }
            string fileDir = HttpContext.Current.Server.MapPath(Properties.Settings.Default.UploadTempFolder);
            return (fileDir + "\\" + fileName);
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

        /// <summary>
        /// Delete temp file after processed
        /// </summary>
        /// <param name="tempFilePath"></param>
        private void deleteTempFile(string tempFilePath)
        {
            if (tempFilePath != string.Empty && File.Exists(tempFilePath))
            {
                File.Delete(tempFilePath);
            }
        }

        /// <summary>
        /// Create folder when not exists
        /// </summary>
        /// <param name="no_seiho"></param>
        /// <author>Nam.PT</author>
        /// <date>19/03/2021</date>
        private void createFolder(string folderName)
        {
            //string fileDir = HttpContext.Current.Server.MapPath(Properties.Settings.Default.ShisakuUploadFolder + folderName);
            string fileDir = Properties.Settings.Default.ShisakuUploadFolder + folderName;
            if (!System.IO.Directory.Exists(fileDir))
            {
                System.IO.Directory.CreateDirectory(fileDir);
            }
        }

        #endregion
    }

    #region "ControllerのAPIで利用するリクエスト・レスポンスクラスです"

    public class KanrenShiryoRequest
    {
        public ChangeSet<tr_gate_attachment> value { get; set; }
        public string tempFilePath { get; set; }
        public bool isSekkei { get; set; }
        public decimal userLogin { get; set; }
        public bool allowEdit { get; set; }
    }

    public class KanrenShiryoChangeResponse
    {
        public List<tr_gate_attachment_new> Items { get; set; }
        public int Count { get; set; }

        public KanrenShiryoChangeResponse()
        {
            Items = new List<tr_gate_attachment_new>();
        }
    }

    public class tr_gate_attachment_new : tr_gate_attachment
    {
        public bool isExistFile { get; set; }
        public string nm_koshin { get; set; }
        public bool isSekkei { get; set; }
        public int kbn_btn { get; set; }
        public int mode { get; set; }
    }
    public class KanrenShiryoCriteria
    {
        public decimal cd_shain { set; get; }
        public decimal nen { set; get; }
        public decimal no_oi { set; get; }
        public int no_gate { set; get; }
        public int no_bunrui { set; get; }
        public int no_check { set; get; }
        public int no_meisai { set; get; }
        public int cd_kaisha { get; set; }
        public int skip { set; get; }
        public int top { set; get; }
        public bool isSekkei { get; set; }
        public int kbn_btn { get; set; }
        public int mode { get; set; }
    }
    #endregion
}
