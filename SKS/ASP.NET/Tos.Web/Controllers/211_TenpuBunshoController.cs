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
using System.IO;
using Tos.Web.Controllers.Helpers;
using System.Data.Entity.Infrastructure;
using System.Data.Common;
using Tos.Web.Logging;

namespace Tos.Web.Controllers
{
    //created from 【SearchInputController(Ver1.7)】 Template
    public class _211_TenpuBunshoController : ApiController
    {
        #region "Controllerで公開するAPI"

        /// <summary>
        /// Search of 211_添付文書
        /// </summary>
        /// <param name="Conditions"></param>
        /// <returns>Detail resultset</returns>
        public TenpuBunshoSearchResponse Get([FromUri]TenpuBunshoSearchRequest Conditions)
        {
            using (ShohinKaihatsuEntities context = new ShohinKaihatsuEntities())
            {
                // Init result set
                TenpuBunshoSearchResponse result = new TenpuBunshoSearchResponse();
                var currentData = context.tr_tenpu_bunsho.Where(x => x.no_seiho == Conditions.no_seiho).OrderByDescending(x => x.dt_henko).ToList();
                result.Count = currentData.Count;
                // Paging
                //currentData = currentData.Skip(Conditions.skip).Take(Conditions.top).ToList();
                for (int i = 0; i < currentData.Count; i++)
                {
                    var tenpu = currentData[i];
                    TenpuBunshoSearch item = new TenpuBunshoSearch();
                    // Get db data
                    DataCopier.ReFill(tenpu, item);
                    item.ts = tenpu.ts.ToArray();
                    // Get file meta info
                    string filePath = getFilePath(tenpu);
                    if (filePath != String.Empty && File.Exists(filePath))
                    {
                        FileInfo info = new FileInfo(filePath);
                        // File size in KB
                        item.file_size = Math.Ceiling(Convert.ToDouble(info.Length) / 1024);
                        item.dt_create = info.CreationTime;
                        item.dt_update = info.LastWriteTime;
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
        public HttpResponseMessage Post([FromBody]TenpuBunshoChangeRequest data)
        {
            using (ShohinKaihatsuEntities context = new ShohinKaihatsuEntities())
            {
                IObjectContextAdapter adapter = context as IObjectContextAdapter;
                DbConnection connection = adapter.ObjectContext.Connection;
                connection.Open();

                using (DbTransaction transaction = connection.BeginTransaction())
                {
                    string uploadedFilePath = String.Empty;
                    string deletedFilePath = getTempPath(DateTime.Now.Ticks.ToString());
                    try
                    {
                        DateTime Now = DateTime.Now;
                        foreach (var item in data.value.Created)
                        {
                            string fileName = item.nm_file;
                            uploadedFilePath = getFilePath(item);
                            string tempFilePath = data.tempFilePath;
                            item.dt_henko = Now;
                            // Relocate temp file
                            // Move current file to temp folder
                            if (File.Exists(uploadedFilePath))
                            {
                                File.Move(uploadedFilePath, deletedFilePath);
                            }
                            createFolder(item.no_seiho);
                            // Move new file to primary folder
                            File.Move(tempFilePath, uploadedFilePath);
                            // Write action to log
                            if (data.value.Deleted.Count > 0)
                            {
                                toLog(item, data.kbn_system, "更新", context);
                            }
                            else
                            {
                                toLog(item, data.kbn_system, "登録", context);
                            }
                        }
                        if (data.value.Created.Count > 0 && data.value.Deleted.Count == 0)
                        { 
                            var currentData = data.value.Created[0];
                            var existsData = context.tr_tenpu_bunsho.Where(x => x.no_seiho == currentData.no_seiho && x.nm_file == currentData.nm_file).FirstOrDefault();
                            if (existsData != null)
                            {
                                // Duplicate error
                                // 同一キーのデータが既に存在します。[no_seiho], [nm_file]
                                return Request.CreateErrorResponse(HttpStatusCode.BadRequest, Properties.Resources.ValidationKey + currentData.no_seiho + ", " + currentData.nm_file);
                            }
                        }
                        data.value.AttachTo(context);
                        context.SaveChanges();
                        // Delete old file when the process is done
                        deleteTempFile(deletedFilePath);
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
        [HttpPost]
        public HttpResponseMessage Delete([FromBody]TenpuBunshoChangeRequest data)
        {
            using (ShohinKaihatsuEntities context = new ShohinKaihatsuEntities())
            {
                IObjectContextAdapter adapter = context as IObjectContextAdapter;
                DbConnection connection = adapter.ObjectContext.Connection;
                connection.Open();

                using (DbTransaction transaction = connection.BeginTransaction())
                {
                    string uploadedFilePath = String.Empty;
                    DateTime Now = DateTime.Now;
                    bool isLogged = false;
                    try
                    {
                        data.value.AttachTo(context);
                        context.SaveChanges();

                        foreach (var item in data.value.Deleted)
                        {
                            uploadedFilePath = getFilePath(item);
                            deleteTempFile(uploadedFilePath);
                            item.dt_henko = Now;
                            // Write action to log
                            if (!isLogged)
                            {
                                toLog(item, data.kbn_system, "削除", context);
                                isLogged = true;
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
        /// Download file
        /// </summary>
        /// <param name="Conditions"></param>
        /// <returns></returns>
        public HttpResponseMessage DowloadFile([FromBody]tr_tenpu_bunsho Conditions)
        {
            MemoryStream stream = new MemoryStream();
            string filePath = getFilePath(Conditions);
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
            return FileUploadDownloadUtility.CreateFileResponse(stream, Conditions.nm_file);
        }

        /// <summary>
        /// Upload file to temp table
        /// </summary>
        /// <returns></returns>
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

        #endregion

        #region "Controller内で利用する関数群"

        /// <summary>
        /// get temp file path = UploadTempFolder + tempfile
        /// </summary>
        /// <param name="fileName"></param>
        /// <returns></returns>
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
        /// get primary file path = saved dir + no_seiho + file name
        /// </summary>
        /// <param name="data"></param>
        /// <returns></returns>
        private string getFilePath(tr_tenpu_bunsho data)
        {
            if (data == null)
            {
                return String.Empty;
            }
            string fileDir = HttpContext.Current.Server.MapPath(Properties.Settings.Default.TenpuBunshoUploadFolder);
            return String.Format(fileDir + "{0}\\{1}", data.no_seiho, data.nm_file);
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
        private void createFolder(string no_seiho)
        {
            string fileDir = HttpContext.Current.Server.MapPath(Properties.Settings.Default.TenpuBunshoUploadFolder + no_seiho);
            if (!System.IO.Directory.Exists(fileDir))
            {
                System.IO.Directory.CreateDirectory(fileDir);
            }
        }

        /// <summary>
        /// Write change action to log
        /// </summary>
        /// <param name="LogData"></param>
        /// <param name="kbn_system"></param>
        /// <param name="action"></param>
        /// <param name="context"></param>
        private void toLog(tr_tenpu_bunsho LogData, byte kbn_system, string action, ShohinKaihatsuEntities context)
        {
            // Save created LOG
            context.sp_shohinkaihatsu_insert_200_event_log(LogData.no_seiho
                                                         , LogData.cd_toroku_kaisha
                                                         , LogData.cd_toroku
                                                         , LogData.cd_toroku
                                                         , "添付文書"
                                                         , action
                                                         , LogData.dt_henko
                                                         , (new CommonController()).GetIPClientAddress()
                                                         , kbn_system);
        }

        #endregion
    }

    #region "ControllerのAPIで利用するリクエスト・レスポンスクラスです"

    /// <summary>
    /// Search param
    /// </summary>
    public class TenpuBunshoSearchRequest
    {
        public string no_seiho { get; set; }
        public int skip { get; set; }
        public int top { get; set; }
    }

    /// <summary>
    /// Search result set
    /// </summary>
    public class TenpuBunshoSearchResponse
    {
        public List<TenpuBunshoSearch> Items { get; set; }
        public int Count { get; set; }

        public TenpuBunshoSearchResponse()
        {
            Items = new List<TenpuBunshoSearch>();
        }
    }

    /// <summary>
    /// Search result model
    /// </summary>
    public class TenpuBunshoSearch : tr_tenpu_bunsho
    {
        public Double? file_size { get; set; }
        public DateTime? dt_create { get; set; }
        public DateTime? dt_update { get; set; }
        public bool isExistFile { get; set; }
    }

    /// <summary>
    /// Change set
    /// </summary>
    public class TenpuBunshoChangeRequest
    {
        public ChangeSet<tr_tenpu_bunsho> value { get; set; }
        public string tempFilePath { get; set; }
        public byte kbn_system { get; set; }
    }

    #endregion
}
