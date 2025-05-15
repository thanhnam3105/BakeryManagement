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
    public class _015_RoguShutsuryokuCSVDownloadController : ApiController
    {
        #region "CSVファイルの項目設定"

        private static readonly TextFieldSetting[] CsvFileSettings = new TextFieldSetting[]
        {
            new TextFieldSetting() { PropertyName = "nm_literal", DisplayName = "画面（操作）",},
            new TextFieldSetting() { PropertyName = "cd_taisho_data", DisplayName = "対象データ",},
            new TextFieldSetting() { PropertyName = "nm_mode", DisplayName = "モード" },
            new TextFieldSetting() { PropertyName = "nm_kaisha", DisplayName = "作業者会社" },
            new TextFieldSetting() { PropertyName = "cd_tanto", DisplayName = "作業者ID" },
            new TextFieldSetting() { PropertyName = "nm_user", DisplayName = "作業者名" },
            //new TextFieldSetting() { PropertyName = "nm_mascot", DisplayName = "マスコット番号" },
            new TextFieldSetting() { PropertyName = "dt_operation", DisplayName = "作業日", Format="yyyy/MM/dd HH:mm"},
        };

        #endregion

        #region "Controllerで公開するAPI"

        /// <summary>
        /// 検索条件に一致するデータを抽出しCSVを作成します。（Downloadフォルダに実体ファイル作成後、パスを返却）
        /// </summary>
        /// <param name="options">検索条件</param>
        /// <returns>作成されたCSVファイルのパス</returns>
        public HttpResponseMessage Get([FromUri]RoguShutsuryokuRequest options)
        {
            // 保存CSVファイル名（ユーザID_CsvDownload_現在日時.csv）
            string userName = UserInfo.GetUserNameFromIdentity(this.User.Identity);
            string filename = "ログ出力_" + userName + "_";
            string csvname = filename + DateTime.Now.ToString("yyyyMMddHHmmss") + Properties.Resources.CsvExtension;

            using (ShohinKaihatsuEntities context = new ShohinKaihatsuEntities())
            {

                //テーブル（ビュー）の項目＋更新区分を追加してダウンロードする場合は以下を使用します
                List<vw_shohinkaihatsu_log_operation> results = new List<vw_shohinkaihatsu_log_operation>();
                results = context.vw_shohinkaihatsu_log_operation
                        .Where(x => (x.d_operation >= options.dt_from || options.dt_from == null) && (x.d_operation <= options.dt_to || options.dt_to == null))
                        .OrderBy(x => x.dt_operation)
                        .ToList();

                MemoryStream stream = new MemoryStream();
                TextFieldFile<vw_shohinkaihatsu_log_operation> tFile = new TextFieldFile<vw_shohinkaihatsu_log_operation>(stream, Encoding.GetEncoding(Properties.Resources.Encoding), CsvFileSettings);
                tFile.Delimiters = new string[] { "," };
                // TODO: ヘッダーを有効にするには IsFirstRowHeader を true に設定にします。
                tFile.IsFirstRowHeader = true;
                tFile.WriteFields(results as IEnumerable<vw_shohinkaihatsu_log_operation>);

                return FileUploadDownloadUtility.CreateFileResponse(stream, csvname);
            }
        }

        #endregion
    }

    #region "ControllerのAPIで利用するリクエスト・レスポンスクラスです"

    public class RoguShutsuryokuRequest
    {
        public DateTime? dt_from { get; set; }
        public DateTime? dt_to { get; set; }
    }

    #endregion

}
