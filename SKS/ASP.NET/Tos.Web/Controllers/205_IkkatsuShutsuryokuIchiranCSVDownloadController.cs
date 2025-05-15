using System;
using System.Collections;
using System.Collections.Generic;
using System.Data.Common;
using System.Data.Entity.Infrastructure;
using System.Data.Objects.SqlClient;
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
    public class _205_IkkatsuShutsuryokuIchiranCSVDownloadController : ApiController
    {
        #region "CSVファイルの項目設定"

        private static readonly TextFieldSetting[] CsvFileSettings = new TextFieldSetting[]
        {
              new TextFieldSetting() { PropertyName = "no_seiho", DisplayName = "製法番号",
                ValidateRules = new Dictionary<string,object>()
                {
                    {"required", true},
                }
            },
             new TextFieldSetting() { PropertyName = "nm_seiho", DisplayName = "製法名",
                ValidateRules = new Dictionary<string,object>()
                {}
            },
            new TextFieldSetting() { PropertyName = "nm_literal", DisplayName = "ステータス",
                ValidateRules = new Dictionary<string,object>()
                {}
            },
             new TextFieldSetting() { PropertyName = "dt_seiho_sakusei", DisplayName = "製法作成日",
                ValidateRules = new Dictionary<string,object>()
                {}
            },
               new TextFieldSetting() { PropertyName = "dt_denso_toroku", DisplayName = "伝送登録日",
                ValidateRules = new Dictionary<string,object>()
                { }
            },
               new TextFieldSetting() { PropertyName = "dt_denso_kanryo", DisplayName = "工場確認済日",
                ValidateRules = new Dictionary<string,object>(){}
            },
             new TextFieldSetting() { PropertyName = "no_seiho1", DisplayName = "製法番号（会社）",
                ValidateRules = new Dictionary<string,object>()
                {}
            },
             new TextFieldSetting() { PropertyName = "no_seiho2", DisplayName = "製法番号（製品種類）",
                ValidateRules = new Dictionary<string,object>()
                {}
            },
             new TextFieldSetting() { PropertyName = "no_seiho3", DisplayName = "製法番号（製法種別）",
                ValidateRules = new Dictionary<string,object>()
                { }
            },
                new TextFieldSetting() { PropertyName = "no_seiho4", DisplayName = "製法番号（年度）",
                ValidateRules = new Dictionary<string,object>()
                {}
            },
             new TextFieldSetting() { PropertyName = "no_seiho5", DisplayName = "製法番号（連番）",
                ValidateRules = new Dictionary<string,object>()
                { }
            }
        };

        #endregion
        /// <summary>
        /// 検索条件に一致するデータを抽出しCSVを作成します。（Downloadフォルダに実体ファイル作成後、パスを返却）
        /// </summary>
        /// <param name="options">検索条件</param>
        /// <returns>作成されたCSVファイルのパス</returns>
        public HttpResponseMessage Get([FromUri] string[] options)
        {
            // 保存CSVファイル名（ユーザID_CsvDownload_現在日時.csv）
            string no_ikkatsu = string.Join(",", options);
            var user = UserInfo.CreateFromAuthorityMaster(this.User.Identity);
            string filename = Properties.Resources.IkkatsuShutsuryokuIchiran + "_" + user.EmployeeCD + "_"/*Properties.Resources.//TODO:DownloadFileNameの定数を指定します*/;
            string csvname = filename + DateTime.Now.ToString("yyyyMMddHHmmss") + Properties.Resources.CsvExtension;
            using (ShohinKaihatsuEntities context = new ShohinKaihatsuEntities())
            {
                StoredProcedureResult<sp_shohinkaihatsu_csv_205_Result> results = new StoredProcedureResult<sp_shohinkaihatsu_csv_205_Result>();
                var data = context.sp_shohinkaihatsu_csv_205(no_ikkatsu).ToList();
                MemoryStream stream = new MemoryStream();
                TextFieldFile<sp_shohinkaihatsu_csv_205_Result> tFile
                                = new TextFieldFile<sp_shohinkaihatsu_csv_205_Result>(stream, Encoding.GetEncoding(Properties.Resources.Encoding), CsvFileSettings);
                tFile.Delimiters = new string[] { "," };
                //// TODO: ヘッダーを有効にするには IsFirstRowHeader を true に設定にします。
                results.Items = data;
                tFile.IsFirstRowHeader = true;
                tFile.WriteFields(data as IEnumerable<sp_shohinkaihatsu_csv_205_Result>);
                return FileUploadDownloadUtility.CreateFileResponse(stream, csvname);

            }
        }
    }

}
