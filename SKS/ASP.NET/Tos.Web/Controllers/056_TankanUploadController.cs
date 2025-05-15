using System;
using System.Collections;
using System.Collections.Generic;
using System.Data.Common;
using System.Data.Entity.Infrastructure;
using System.Globalization;
using System.IO;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Text;
using System.Web;
using System.Web.Http;
using System.Web.Http.OData.Query;
using Tos.Web.Controllers.Helpers;
using Tos.Web.Data;
using Tos.Web.Logging;

namespace Tos.Web.Controllers
{
    //created from 【CsvUploadDownloadController(Ver2.0)】 Template
    public class _056_TankanUploadController : ApiController
    {
        #region "CSVファイルの項目設定"
        //CREATE TABLE TEMP
        string createTableTemp = "CREATE TABLE #wk_tanka_upload_bf("
                                    + "[SEQ] [int] NULL,"
                                    + "[hin_bf] [varchar](15) NULL,"
                                    + "[busho_bf] [varchar](15) NULL,"
                                    + "[tanka_bf] [varchar](15) NULL,)ON [PRIMARY]";
        string insertTableTemp = "INSERT INTO #wk_tanka_upload_bf([SEQ],[hin_bf],[busho_bf],[tanka_bf])values('{0}','{1}','{2}','{3}')";
        /// <summary>
        /// nameColumnErr
        /// </summary>
        string strHin = "品コード", strBusho = "部署コード", strTanka = "単価";

        private static readonly TextFieldSetting[] CsvFileSettings = new TextFieldSetting[]
        {
            new TextFieldSetting() { PropertyName = "hin_bf", DisplayName = "品コード", 
                ValidateRules = new Dictionary<string,object>()
                {
                    {"required", true},
                    {"number", true},
                    {"maxlengthcustom", 11}
                }
            },
            new TextFieldSetting() { PropertyName = "busho_bf", DisplayName = "部署コード", 
                ValidateRules = new Dictionary<string,object>()
                {
                    {"required", true},
                    {"maxlengthcustom", 15}
                }
            },
            new TextFieldSetting() { PropertyName = "tanka_bf", DisplayName = "単価", 
                ValidateRules = new Dictionary<string,object>()
                {
                    {"required", true},
                    //{"number", true},
                    {"pointlengthcustom",new TextFieldSetting.PointLength(){BeforePoint = 8 , AfterPoint = 2 ,AllowNegativeNumber = false}},
                    {"rangecustom",new TextFieldSetting.Range(){Min = 0 , Max = 99999999.99}},
                    
                }
            }
        };

        private static readonly TextFieldSetting[] CsvFileCheckColumn = new TextFieldSetting[]
        {
            new TextFieldSetting() { PropertyName = "hin_bf", DisplayName = "品コード"},
            new TextFieldSetting() { PropertyName = "busho_bf", DisplayName = "部署コード"},
            new TextFieldSetting() { PropertyName = "tanka_bf", DisplayName = "単価"}
        };
        #endregion

        #region "Controllerで公開するAPI"

        /// <summary>
        /// 検索条件に一致するデータを抽出しCSVを作成します。（Downloadフォルダに実体ファイル作成後、パスを返却）
        /// </summary>
        /// <param name="options">検索条件</param>
        /// <returns>作成されたCSVファイルのパス</returns>
        //public HttpResponseMessage Get(ODataQueryOptions<object/*TODO:【1】CsvDownloadTableの型を指定します*/> options)
        //{
        //    // 保存CSVファイル名（ユーザID_CsvDownload_現在日時.csv）
        //    string userName = UserInfo.GetUserNameFromIdentity(this.User.Identity);
        //    string filename = userName + "CsvDownload_"/*Properties.Resources.//TODO:DownloadFileNameの定数を指定します*/;
        //    string csvname = filename + DateTime.Now.ToString("yyyyMMddHHmmss") + Properties.Resources.CsvExtension;

        //    //using (/*TODO:CsvDownloadTable情報を管理しているDbContextを指定します*/ context = new /*TODO:CsvDownloadTable情報を管理しているDbContextを指定します*/())
        //    //{
        //    //     //テーブル（ビュー）の項目のみでダウンロードする場合は以下を使用します
        //    //    IQueryable results = options.ApplyTo(context./*TODO:【1】CsvDownloadTableの型を指定します*/.AsQueryable());

        //    //     //テーブル（ビュー）の項目＋更新区分を追加してダウンロードする場合は以下を使用します
        //    //    IEnumerable results = (from m in options.ApplyTo(context./*TODO:【1】CsvDownloadTableの型を指定します*/.AsQueryable()) as IEnumerable<Object/*TODO:【1】CsvDownloadTableの型を指定します*/>
        //    //                            select new Model_CsvUploadDownload()
        //    //                            {
        //    //                                update = 0,
        //    //                                cd_buhin = m.cd_buhin,
        //    //                                nm_buhin = m.nm_buhin,
        //    //                                kin_shiire = m.kin_shiire,
        //    //                                nm_tani = m.nm_tani
        //    //                            }).ToList();

        //    MemoryStream stream = new MemoryStream();
        //    //TextFieldFile<Object/*TODO:【2】CsvDownloadTableの型、もしくはModel_CsvUploadDownloadクラスを指定します*/> tFile 
        //    //                    = new TextFieldFile<Object/*TODO:【2】CsvDownloadTableの型、もしくはModel_CsvUploadDownloadクラスを指定します*/>(stream, 
        //    //                                                          Encoding.GetEncoding(Properties.Resources.Encoding), CsvFileSettings);
        //    //tFile.Delimiters = new string[] { "," };
        //    //// TODO: ヘッダーを有効にするには IsFirstRowHeader を true に設定にします。
        //    //tFile.IsFirstRowHeader = true;
        //    //tFile.WriteFields(results as IEnumerable<Object/*TODO:【2】CsvDownloadTableの型、もしくはModel_CsvUploadDownloadクラスを指定します*/>);

        //    return FileUploadDownloadUtility.CreateFileResponse(stream, csvname);
        //    //}
        //}

        /// <summary>
        /// アップロードCSVから対象テーブルを更新します
        /// </summary>
        /// <returns></returns>
        public HttpResponseMessage Post()
        {
            string mapPath = HttpContext.Current.Server.MapPath(Properties.Settings.Default.UploadTempFolder);

            MultipartFormDataStreamProvider streamProvider = FileUploadDownloadUtility.ReadAsMultiPart(Request, mapPath);
            var file = streamProvider.FileData.FirstOrDefault();
            if (file == null)
            {
                return Request.CreateErrorResponse(HttpStatusCode.BadRequest, Properties.Resources.NoUploadDataError);
            }
            using (TextFieldFile<TankaMasterCsv> tFile
                = new TextFieldFile<TankaMasterCsv>(file.LocalFileName,
                    Encoding.GetEncoding(Properties.Resources.EncodingUTF8), CsvFileSettings))
            {
                SaveUploadCsv(tFile);
                
                if (tFile.RecordCount == 0)
                {
                    return Request.CreateErrorResponse(HttpStatusCode.BadRequest, Properties.Resources.NoUploadDataError);
                }

                if (tFile.HasError && tFile.EndOfData)
                {
                    return createErrorCsv(tFile);
                }

                //if (tFile.HasError && !tFile.EndOfData)
                //{
                //    //return Request.CreateErrorResponse(HttpStatusCode.PreconditionFailed, Properties.Resources.NoUploadDataError);
                //}

                return Request.CreateResponse(HttpStatusCode.OK, Properties.Resources.FileSaveSuccessMessage);
            }
        }

        #endregion

        #region "Controller内で利用する関数群"

        /// <summary>
        /// アップロードCSVを読み込みます
        /// </summary>
        /// <returns></returns>
        private TextFieldFile<TankaMasterCsv> SaveUploadCsv(TextFieldFile<TankaMasterCsv> tFile)
        {
            tFile.Delimiters = new string[] { "," };
            // TODO: 1行目をヘッダーとして読み飛ばすには IsFirstRowHeader を true に設定にします。
            tFile.IsFirstRowHeader = false;
            // TODO: 1カラム目を更新区分として利用するには IsUseUpdateColumn を true に設定にします。
            //tFile.IsUseUpdateColumn = true;
            DateTime nowDate = DateTime.Now;
            var listDataKey = new List<TankaMasterCsv>();
            var changeSet = new List<TankaMasterCsv>();
            using (ShohinKaihatsuEntities context = new ShohinKaihatsuEntities())
            {
                IObjectContextAdapter adapter = context as IObjectContextAdapter;
                DbConnection connection = adapter.ObjectContext.Connection;
                connection.Open();
                using (DbTransaction transaction = connection.BeginTransaction())
                {
                    while (!tFile.EndOfData)
                    {
                        TankaMasterCsv target = tFile.ReadFields();
                        if (target == null)
                        {
                            continue;
                        }
                        var isBooleanStatus = isBooleanValidation(context, tFile, target);
                        if (isBooleanStatus)
                        {
                            TankaMasterCsv item = new TankaMasterCsv();
                            item.hin_bf = target.hin_bf;
                            item.busho_bf = target.busho_bf;
                            item.tanka_bf = Decimal.Parse(target.tanka_bf).ToString();
                            item.row = tFile.RecordCount;
                            listDataKey.Add(item);
                        }
                        
                    }
                    if (tFile.RecordCount == 0)
                    {
                        return tFile;
                    }
                    if (listDataKey.Count > 0)
                    {
                        SaveData(context, listDataKey, tFile);
                        transaction.Commit();
                    }
                }
            }

            return tFile;
        }
        /// <summary>
        /// 読み込んだデータをDBに保存します
        /// </summary>
        /// <returns></returns>
        private void SaveData(ShohinKaihatsuEntities context, List<TankaMasterCsv> listDataKey, TextFieldFile<TankaMasterCsv> tFile)
        {
            string userName = UserInfo.GetUserNameFromIdentity(this.User.Identity);
            int noOfRowInserted = context.Database.ExecuteSqlCommand(createTableTemp);
            foreach (var item in listDataKey)
            {
                int insert = context.Database.ExecuteSqlCommand(string.Format(insertTableTemp, item.row, item.hin_bf, item.busho_bf, item.tanka_bf));
            }

            var insertErrduplicate = context.sp_tankan_upload_056(userName).ToList();
            if (insertErrduplicate.Count>0)
            {
                foreach (var itemErr in insertErrduplicate)
                {
                    switchcaseErrMess(tFile, itemErr);
                }
            }
            
        }

        #region validate

        private Boolean isBooleanValidation(ShohinKaihatsuEntities context, TextFieldFile<TankaMasterCsv> tFile,TankaMasterCsv target)
        {
            Boolean hasErr = true;
            if (string.IsNullOrEmpty(target.hin_bf) || string.IsNullOrEmpty(target.busho_bf) || string.IsNullOrEmpty(target.tanka_bf))
            {
                checkIsEmpty(strHin, target.hin_bf, tFile);
                checkIsEmpty(strBusho, target.busho_bf, tFile);
                checkIsEmpty(strTanka, target.tanka_bf, tFile);
                hasErr = false;
            }
            
            return hasErr;
        }
        public void switchcaseErrMess(TextFieldFile<TankaMasterCsv> tFile, sp_tankan_upload_056_Result itemErr)
        {
            if (itemErr != null)
            {
                switch (itemErr.err_msg)
                {
                    case "1_HinNotExist":
                        tFile.AddError(strHin, itemErr.col_hin, string.Format(Properties.Resources.CodeNotfound, strHin), itemErr.SEQ);
                        break;
                    case "2_bushoNotExist":
                        tFile.AddError(strBusho, itemErr.col_busho, string.Format(Properties.Resources.CodeNotfound, strBusho), itemErr.SEQ);
                        break;
                    default:
                        tFile.AddError(strHin, itemErr.col_hin, Properties.Resources.Duplicate, itemErr.SEQ);
                        break;
                }
            }
        }

        /// convert string to decimal. when error return 0
        public static decimal StringToDecimal(string data, string nameErr, ref  TextFieldFile<TankaMasterCsv> tFile, ref Boolean hasErr)
        {

            data = (data == "" || data == null) ? "0" : data;
            decimal value = 0;
            try
            {
                value = decimal.Parse(data, CultureInfo.InvariantCulture);
            }
            catch
            {
                tFile.AddError(nameErr, data, Properties.Resources.InvalidNumberFormatError);
                hasErr = false;
                value = 0;
            }

            if (value < 0)
            {
                //tFile.AddError(null, "", Properties.Resources.);
            }
            return value;
        }

        //format decimal number.xx or 0.xx 
        public static string FormatDecimalOrInteger(string value)
        {
            decimal number = decimal.Parse(value, CultureInfo.InvariantCulture);
            if (number == Math.Truncate(number))
            {
                return number.ToString("0");
            }
            else
            {
                string formatted = number.ToString("0.00");

                if (number < 1 && number >= 0 && !formatted.StartsWith("0"))
                {
                    formatted = "0" + formatted;
                }

                return formatted;
            }
        }

        //check value is empty
        public void checkIsEmpty(string columnName, string value, TextFieldFile<TankaMasterCsv> tFile)
        {
            if (string.IsNullOrEmpty(value))
            {
                tFile.AddError(columnName, value, Properties.Resources.RequiredTextField);
            }
        }
        #endregion validate

        /// <summary>
        /// エラー情報CSVを作成します
        /// </summary>
        /// <returns></returns>
        private HttpResponseMessage createErrorCsv(TextFieldFile<TankaMasterCsv> tFile)
        {
            // 保存Csvファイル名（ユーザID_downloadError_現在日時.csv）
            string name = UserInfo.GetUserNameFromIdentity(this.User.Identity);
            string filename = name + "_UploadError_"/*Properties.Resources.//TODO:UploadErrorFileNameの定数を指定します*/;
            string csvName = filename + DateTime.Now.ToString("yyyyMMddHHmmss") + Properties.Resources.CsvExtension;
            MemoryStream stream = tFile.GetErrorStream();
            stream.Position = 0;
            return FileUploadDownloadUtility.CreateFileResponse(HttpStatusCode.BadRequest, stream, csvName);
        }

        #endregion
    }

    #region "ControllerのAPIで利用するリクエスト・レスポンスクラスです"

    /// <summary>
    /// テーブル（ビュー）の項目＋更新区分を追加してアップロード・ダウンロードする場合に、追加する項目を定義します
    /// </summary>
    public class TankaMasterCsv
    {
        public string hin_bf { get; set; }
        public string busho_bf{ get; set; }
        public string tanka_bf{ get; set; }
        public long row { get; set; }
    }

    #endregion

}
