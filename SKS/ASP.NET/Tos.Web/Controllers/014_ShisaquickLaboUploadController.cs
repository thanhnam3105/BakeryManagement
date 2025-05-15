using System;
using System.Collections;
using System.Collections.Generic;
using System.Data.Common;
using System.Data.Entity.Infrastructure;
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
    public class _014_ShisaquickLaboUploadController : ApiController
    {
        #region "CSVファイルの項目設定"

        private static readonly TextFieldSetting[] CsvFileSettings = new TextFieldSetting[]
        {
            new TextFieldSetting() { PropertyName = "cd_kaisha", DisplayName = "会社コード", 
                ValidateRules = new Dictionary<string,object>()
                {
                    {"required", true},
                    {"number", false}
                }
            },
            new TextFieldSetting() { PropertyName = "cd_genryo", DisplayName = "品コード",
                ValidateRules = new Dictionary<string,object>()
                {
                    {"required", true},
                    {"maxmojisu", 11}
                }
            },
            new TextFieldSetting() { PropertyName = "nm_hinmei", DisplayName = "品名"},
            new TextFieldSetting() { PropertyName = "nisugata_hyoji", DisplayName = "荷姿", 
                ValidateRules = new Dictionary<string,object>()
                {
                    {"maxmojisu", 60}
                }
            },
            new TextFieldSetting() { PropertyName = "sakkin_chomieki_yohi", DisplayName = "殺菌調味液要否", 
                ValidateRules = new Dictionary<string,object>()
                {
                    {"maxlength", 12},
                }
            },
            new TextFieldSetting() { PropertyName = "sakkin_chomieki_joken", DisplayName = "殺菌調味液条件",
                ValidateRules = new Dictionary<string,object>()
                {
                    {"maxmojisu", 500}
                }
            },
            new TextFieldSetting() { PropertyName = "NB_genteigenryo", DisplayName = "NB限定原料", 
                ValidateRules = new Dictionary<string,object>()
                {
                    {"maxlength", 12}
                }
            },
            new TextFieldSetting() { PropertyName = "NB_joken", DisplayName = "NB限定原料条件",
                ValidateRules = new Dictionary<string,object>()
                {
                    {"maxmojisu", 160}
                }
            },
            new TextFieldSetting() { PropertyName = "NB_riyu", DisplayName = "NB限定原料設定理由",
                ValidateRules = new Dictionary<string,object>()
                {
                    {"maxmojisu", 200}
                }
            },
            new TextFieldSetting() { PropertyName = "kasseikoso", DisplayName = "活性酵素", 
                ValidateRules = new Dictionary<string,object>()
                {
                    {"maxlength", 6}
                }
            },
            new TextFieldSetting() { PropertyName = "gosei_tenkabutu", DisplayName = "合成添加物申請", 
                ValidateRules = new Dictionary<string,object>()
                {
                    {"maxlength", 6}
                }
            },
            new TextFieldSetting() { PropertyName = "trouble_joho", DisplayName = "過去トラブル情報", 
                ValidateRules = new Dictionary<string,object>()
                {
                    {"maxlength", 6}
                }
            },
            new TextFieldSetting() { PropertyName = "trouble_gaiyo", DisplayName = "トラブル概要",
                ValidateRules = new Dictionary<string,object>()
                {
                    {"maxmojisu", 700}
                }
            },
            new TextFieldSetting() { PropertyName = "trouble_naiyo_shosai", DisplayName = "トラブル内容詳細",
                ValidateRules = new Dictionary<string,object>()
                {
                    {"maxmojisu", 700}
                }
            },
            new TextFieldSetting() { PropertyName = "biko", DisplayName = "備考",
                ValidateRules = new Dictionary<string,object>()
                {
                    {"maxmojisu", 360}
                }
            },
            new TextFieldSetting() { PropertyName = "dt_nyuryoku", DisplayName = "入力日", 
                ValidateRules = new Dictionary<string,object>()
                {
                }
            },
            new TextFieldSetting() { PropertyName = "nm_nyuryoku", DisplayName = "入力者", 
                ValidateRules = new Dictionary<string,object>()
                {
                }
            },
            new TextFieldSetting() { PropertyName = "nm_syonin", DisplayName = "承認者", 
                ValidateRules = new Dictionary<string,object>()
                {
                }
            },
            new TextFieldSetting() { PropertyName = "shiyokahi_genryo", DisplayName = "原料使用可否",
                ValidateRules = new Dictionary<string,object>()
                {
                    {"maxlength", 12}
                }
            },
            new TextFieldSetting() { PropertyName = "shiyokahi_riyu", DisplayName = "原料使用可否設定理由",
                ValidateRules = new Dictionary<string,object>()
                {
                    {"maxmojisu", 360}
                }
            },
            new TextFieldSetting() { PropertyName = "shiyokahi_cd_genryo_daitai", DisplayName = "代替推奨原料コード", 
                ValidateRules = new Dictionary<string,object>()
                {
                    {"maxlength", 11}
                }
            },
            new TextFieldSetting() { PropertyName = "shiyokahi_nm_genryo_daitai", DisplayName = "代替推奨原料名",
                ValidateRules = new Dictionary<string,object>()
                {
                    {"maxmojisu", 70}
                }
            },
            new TextFieldSetting() { PropertyName = "genko_genryo_nm_kaisha", DisplayName = "現行原料納品者名_会社名", 
                ValidateRules = new Dictionary<string,object>()
                {
                }
            },
            new TextFieldSetting() { PropertyName = "rank_ibutsu", DisplayName = "異物ランク", 
                ValidateRules = new Dictionary<string,object>()
                {
                    {"maxlength", 6}
                }
            },
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

            using (TextFieldFile<GenryoMasterCsv> tFile
                = new TextFieldFile<GenryoMasterCsv>(file.LocalFileName,
                    Encoding.GetEncoding(Properties.Resources.EncodingUTF8), CsvFileSettings))
            {
                SaveUploadCsv(tFile);

                if (tFile.RecordCount == 0)
                {
                    return Request.CreateErrorResponse(HttpStatusCode.BadRequest, Properties.Resources.NoUploadDataError);
                }

                if (tFile.HasError)
                {
                    return createErrorCsv(tFile);
                }

                return Request.CreateResponse(HttpStatusCode.OK, Properties.Resources.FileSaveSuccessMessage);
            }
        }

        #endregion

        #region "Controller内で利用する関数群"

        /// <summary>
        /// アップロードCSVを読み込みます
        /// </summary>
        /// <returns></returns>
        private TextFieldFile<GenryoMasterCsv> SaveUploadCsv(TextFieldFile<GenryoMasterCsv> tFile)
        {
            tFile.Delimiters = new string[] { "," };
            // TODO: 1行目をヘッダーとして読み飛ばすには IsFirstRowHeader を true に設定にします。
            tFile.IsFirstRowHeader = true;
            // TODO: 1カラム目を更新区分として利用するには IsUseUpdateColumn を true に設定にします。
            //tFile.IsUseUpdateColumn = true;


            string userName = UserInfo.GetUserNameFromIdentity(this.User.Identity);
            DateTime nowDate = DateTime.Now;

            using (ShohinKaihatsuEntities context = new ShohinKaihatsuEntities())
            {
                IObjectContextAdapter adapter = context as IObjectContextAdapter;
                DbConnection connection = adapter.ObjectContext.Connection;
                connection.Open();
                using (DbTransaction transaction = connection.BeginTransaction())
                {
                    while (!tFile.EndOfData)
                    {
                        GenryoMasterCsv target = tFile.ReadFields();
                        if (target == null)
                        {
                            continue;
                        }

                        // TODO: 複合チェックやマスタチェックを行う場合にはここに実装し、エラー時に AddError メソッドを実行して下さい。
                        //if (target.biko == null && target.nm_hinmei == null) {
                        //    tFile.AddError("備考", target.biko, "備考、品名どちらかの値を設定してください。");
                        //}

                        if(target.cd_genryo != null || target.cd_kaisha != 0){

                        SaveData(context, tFile, target, userName, nowDate);

                            }
                    }

                    if (tFile.RecordCount == 0 || tFile.HasError)
                    {
                        return tFile;
                    }

                    context.SaveChanges();
                    transaction.Commit();
                }
            }

            return tFile;
        }

        /// <summary>
        /// 読み込んだデータをDBに保存します
        /// </summary>
        /// <returns></returns>
        private void SaveData(ShohinKaihatsuEntities context, TextFieldFile<GenryoMasterCsv> tFile,
                                    GenryoMasterCsv target, string userName, DateTime nowDate)
        {
            //target.id_koshin = Convert.ToDecimal(userName);
            //target.dt_koshin = nowDate;
            //id_koshin_shisaquick 対応　2022/07/12 mano.n add st
            target.id_koshin_shisaquicklab = Convert.ToDecimal(userName);
            target.dt_koshin_shisaquicklab = nowDate;
            //id_koshin_shisaquick 対応　2022/07/12 mano.n add ed
            target.cd_genryo = target.cd_genryo.PadLeft(6, '0');

            ma_genryo dbTarget = context.ma_genryo.Find(target.cd_kaisha, target.cd_genryo);

            if (target.shiyokahi_nm_genryo_daitai != null) {
                target.shiyokahi_nm_genryo_daitai = target.shiyokahi_nm_genryo_daitai.Replace("#N/A", null);
            }

            /*TODO:1カラム目を更新区分として利用する場合は、以下のロジックもコメント解除する */
            //if (target.update.ToString() == CsvUpdateColumn.Delete)
            //{
            //    if (dbTarget != null) 
            //    {
            //        context./*TODO:【1】CsvUploadTableの型を指定します*/.Remove(dbTarget);
            //    }
            //}
            //else if (target.update.ToString() == CsvUpdateColumn.CreateUpdate)
            //{
            if (dbTarget == null)
            {
                //target.cd_create = userName;
                //target.dt_create = nowDate;
                //ma_genryo newTarget = DataCopier.ReFill</*TODO:【1】CsvUploadTableの型を指定します*/>(target);
                //context.ma_genryo.Add(target);
            }
            else
            {
                //target.cd_create = dbTarget.cd_create;
                ////target.dt_create = dbTarget.dt_create;
                target.cd_kaisha = dbTarget.cd_kaisha;
                target.cd_genryo = dbTarget.cd_genryo;
                dbTarget.nisugata_hyoji = target.nisugata_hyoji;
                dbTarget.rank_ibutsu = target.rank_ibutsu;
                dbTarget.shiyokahi_genryo = target.shiyokahi_genryo;
                dbTarget.shiyokahi_riyu = target.shiyokahi_riyu;
                dbTarget.shiyokahi_cd_genryo_daitai = target.shiyokahi_cd_genryo_daitai;
                dbTarget.shiyokahi_nm_genryo_daitai = target.shiyokahi_nm_genryo_daitai;
                dbTarget.trouble_joho = target.trouble_joho;
                dbTarget.trouble_gaiyo = target.trouble_gaiyo;
                dbTarget.trouble_naiyo_shosai = target.trouble_naiyo_shosai;
                dbTarget.sakkin_chomieki_yohi = target.sakkin_chomieki_yohi;
                dbTarget.sakkin_chomieki_joken = target.sakkin_chomieki_joken;
                dbTarget.NB_genteigenryo = target.NB_genteigenryo;
                dbTarget.NB_joken = target.NB_joken;
                dbTarget.NB_riyu = target.NB_riyu;
                dbTarget.kasseikoso = target.kasseikoso;
                dbTarget.gosei_tenkabutu = target.gosei_tenkabutu;
                dbTarget.biko = target.biko;
                //dbTarget.dt_koshin = target.dt_koshin;
                //dbTarget.id_koshin = target.id_koshin;
                //mano.n add
                dbTarget.dt_koshin_shisaquicklab = target.dt_koshin_shisaquicklab;
                dbTarget.id_koshin_shisaquicklab = target.id_koshin_shisaquicklab;
                //mano.n end
                target.dt_toroku = dbTarget.dt_toroku;
                target.dt_kakunin = dbTarget.dt_kakunin;

                //DataCopier.ReFill(target, dbTarget);
            }
            /*TODO:1カラム目を更新区分として利用する場合は、以下のロジックもコメント解除する */
            //}

            /*TODO:データ保存時のSQLエラーもエラーCSVに書き出す場合は、以下のロジックもコメント解除する　※取込処理のレスポンスが落ちるため注意！ */
            //try
            //{
            //    context.SaveChanges();
            //}
            //catch (Exception ex)
            //{
            //    // SQL発行時に発生したエラーをクライアントに返却する用のメッセージを設定します
            //    List<TextFieldError> errorList = TextFiedlFileUtility.GetExceptionMessage(ex);
            //    foreach (TextFieldError error in errorList)
            //    {
            //        tFile.AddError(error.ColumnName, error.ErrorValue, error.ErrorMessage);
            //    }
            //    try
            //    {
            //        // エラーが発生した対象行の状態を変更なしに設定します
            //        if (dbTarget == null)
            //        {
            //            context.Entry<Object/*TODO:【1】CsvUploadTableの型を指定します*/>(target).State = System.Data.EntityState.Detached;
            //        }
            //        else
            //        {
            //            context.Entry<Object/*TODO:【1】CsvUploadTableの型を指定します*/>(dbTarget).State = System.Data.EntityState.Detached;
            //        }
            //    }
            //    catch (Exception) { }
            //}
        }

        /// <summary>
        /// エラー情報CSVを作成します
        /// </summary>
        /// <returns></returns>
        private HttpResponseMessage createErrorCsv(TextFieldFile<GenryoMasterCsv> tFile)
        {
            // 保存Csvファイル名（ユーザID_downloadError_現在日時.csv）
            string name = UserInfo.GetUserNameFromIdentity(this.User.Identity);
            string filename = name + "_Genryo_"/*Properties.Resources.//TODO:UploadErrorFileNameの定数を指定します*/;
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
    public class GenryoMasterCsv : ma_genryo
    {
        public string nm_hinmei { get; set; }
        public string genko_genryo_nm_kaisha { get; set; }
        public string nm_syonin { get; set; }
        public string dt_nyuryoku { get; set; }
        public string nm_nyuryoku { get; set; }
    }

    #endregion

}
