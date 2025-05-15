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
    public class _200_SeihoIchiranCSVDownloadController : ApiController
    {
        #region "CSVファイルの項目設定"

        private static readonly TextFieldSetting[] CSV200_Download = new TextFieldSetting[]
        {
            new TextFieldSetting() { PropertyName = "no_seiho", DisplayName = "製法番号"},
            new TextFieldSetting() { PropertyName = "nm_seiho", DisplayName = "製法名", WrapChar = "\"" },
            new TextFieldSetting() { PropertyName = "cd_hin", DisplayName = "製品コード"},
            new TextFieldSetting() { PropertyName = "nm_seihin", DisplayName = "製品名",WrapChar = "\""},
            new TextFieldSetting() { PropertyName = "dt_seiho_sakusei_0", DisplayName = "製法作成日",Format="yyyy/MM/dd"},
            new TextFieldSetting() { PropertyName = "nm_seiho_sakusei_1", DisplayName = "製法作成者１",WrapChar = "\""},
            new TextFieldSetting() { PropertyName = "nm_seiho_sakusei_2", DisplayName = "製法作成者２",WrapChar = "\""},
            new TextFieldSetting() { PropertyName = "nm_seiho_bunsho_before", DisplayName = "製法文書名・前",WrapChar = "\""},
            new TextFieldSetting() { PropertyName = "cd_shinsei_tanto", DisplayName = "製法申請者コード",WrapChar = "\""},
            new TextFieldSetting() { PropertyName = "nm_user_seiho", DisplayName = "製法申請者名",WrapChar = "\""},
            new TextFieldSetting() { PropertyName = "nm_seiho_sekinin", DisplayName = "製法責任者",WrapChar = "\""},
            new TextFieldSetting() { PropertyName = "cd_haigo", DisplayName = "配合コード"},
            new TextFieldSetting() { PropertyName = "nm_haigo", DisplayName = "配合名",WrapChar = "\""},
            new TextFieldSetting() { PropertyName = "nm_haigo_r", DisplayName = "配合名略",WrapChar = "\""},
            new TextFieldSetting() { PropertyName = "cd_kaisha_daihyo", DisplayName = "代表会社コード"},
            new TextFieldSetting() { PropertyName = "nm_kaisha_daihyo", DisplayName = "代表会社名",WrapChar = "\""},
            new TextFieldSetting() { PropertyName = "cd_kojyo_daihyo", DisplayName = "代表工場コード"},
            new TextFieldSetting() { PropertyName = "nm_kojyo", DisplayName = "代表工場名",WrapChar = "\""},
            new TextFieldSetting() { PropertyName = "kbn_hin", DisplayName = "品区分コード"},
            new TextFieldSetting() { PropertyName = "nm_kbn_hin", DisplayName = "品区分名",WrapChar = "\""},
            new TextFieldSetting() { PropertyName = "cd_bunrui", DisplayName = "分類コード"},
            new TextFieldSetting() { PropertyName = "nm_bunrui", DisplayName = "分類名",WrapChar = "\""},
            new TextFieldSetting() { PropertyName = "budomari", DisplayName = "歩留"},
            new TextFieldSetting() { PropertyName = "qty_kihon", DisplayName = "基本重量"},
            new TextFieldSetting() { PropertyName = "ritsu_kihon", DisplayName = "基本倍率"},
            new TextFieldSetting() { PropertyName = "cd_setsubi", DisplayName = "設備コード"},
            new TextFieldSetting() { PropertyName = "nm_setsubi", DisplayName = "設備名",WrapChar = "\""},
            new TextFieldSetting() { PropertyName = "flg_gasan", DisplayName = "仕込み合算フラグ"},
            new TextFieldSetting() { PropertyName = "qty_max", DisplayName = "仕込み最大重量"},
            new TextFieldSetting() { PropertyName = "qty_haigo_kei", DisplayName = "配合合計重量"}, 
            new TextFieldSetting() { PropertyName = "biko", DisplayName = "備考",WrapChar="\""},
            new TextFieldSetting() { PropertyName = "no_seiho_haigo", DisplayName = "製法番号"},
            new TextFieldSetting() { PropertyName = "cd_kaisha", DisplayName = "会社コード"},
            new TextFieldSetting() { PropertyName = "kbn_vw", DisplayName = "V/W区分（Kg/L）",WrapChar="\""},
            new TextFieldSetting() { PropertyName = "hijyu", DisplayName = "比重"},
            new TextFieldSetting() { PropertyName = "flg_mishiyo", DisplayName = "未使用フラグ"},
            new TextFieldSetting() { PropertyName = "kbn_haishi", DisplayName = "廃止区分"},
            new TextFieldSetting() { PropertyName = "kbn_shiagari", DisplayName = "仕上区分"},
            new TextFieldSetting() { PropertyName = "status", DisplayName = "ステータス"},
            new TextFieldSetting() { PropertyName = "nm_literal", DisplayName = "ステータス名",WrapChar = "\""},
            new TextFieldSetting() { PropertyName = "cd_seiho_bunrui", DisplayName = "製法分類"},
            new TextFieldSetting() { PropertyName = "nm_seiho_bunrui", DisplayName = "製法分類名",WrapChar = "\""},
            new TextFieldSetting() { PropertyName = "no_seiho_sanko", DisplayName = "参考製法番号",WrapChar = "\""},
            new TextFieldSetting() { PropertyName = "dt_toroku", DisplayName = "登録日時",Format="yyyy/MM/dd HH:mm:ss"},
            new TextFieldSetting() { PropertyName = "cd_toroku_kaisha", DisplayName = "登録者会社コード"},
            new TextFieldSetting() { PropertyName = "nm_kaisha_toroku", DisplayName = "登録者会社名",WrapChar = "\""},
            new TextFieldSetting() { PropertyName = "cd_toroku", DisplayName = "登録者コード"},
            new TextFieldSetting() { PropertyName = "nm_user_toroku", DisplayName = "登録者名",WrapChar = "\""},
            new TextFieldSetting() { PropertyName = "dt_henko", DisplayName = "変更日時",Format="yyyy/MM/dd HH:mm:ss"},
            new TextFieldSetting() { PropertyName = "cd_koshin_kaisha", DisplayName = "更新者会社コード"},
            new TextFieldSetting() { PropertyName = "nm_kaisha_koshin", DisplayName = "更新者会社名",WrapChar = "\""},
            new TextFieldSetting() { PropertyName = "cd_koshin", DisplayName = "更新者コード"},
            new TextFieldSetting() { PropertyName = "nm_user_koshin", DisplayName = "更新者名",WrapChar = "\""},
            new TextFieldSetting() { PropertyName = "cd_haigo_sanko", DisplayName = "参考配合コード"},
        };

        #endregion

        #region "Controllerで公開するAPI"

        /// <summary>
        /// Download_CSV_200_製法一覧CSV
        /// </summary>
        /// <param name="options">検索条件</param>
        /// <returns>200_製法一覧CSV</returns>
        public HttpResponseMessage Get([FromUri] Filter param)
        {
            // 保存CSVファイル名（ユーザID_CsvDownload_現在日時.csv）
            decimal _userName = Convert.ToDecimal(UserInfo.GetUserNameFromIdentity(this.User.Identity));
            string userName = "_" + _userName.ToString() + "_";
            string filename = Properties.Resources.CSV200_Download + userName;
            string csvname =  filename + DateTime.Now.ToString("yyyyMMddHHmmss") + Properties.Resources.CsvExtension;

            using (ShohinKaihatsuEntities context = new ShohinKaihatsuEntities())
            {
                //テーブル（ビュー）の項目のみでダウンロードする場合は以下を使用します
                param.set_haishi = Convert.ToByte(Properties.Resources.kbn_haishi);
                param.set_hin = Convert.ToByte(Properties.Resources.kbn_hin);
                param.set_category = Properties.Resources.cd_category.ToString();
                param.set_kengen = Convert.ToInt32(Properties.Resources.kbn_kengen);
                param.set_mishiyo = Convert.ToByte(Properties.Resources.flg_mishiyo);
                param.set_denso_taisho = Convert.ToByte(Properties.Resources.flg_denso_taisho);
                ((IObjectContextAdapter)context).ObjectContext.CommandTimeout = Convert.ToInt32(Properties.Settings.Default.TimeOutCSV_200);
                var results = context.sp_shohinkaihatsu_download_200(
                               param.no_seiho1,
                               param.no_seiho2,
                               param.no_seiho3,
                               param.no_seiho4,
                               param.nm_seiho,
                               param.cd_seiho_bunrui,
                               param.dt_seiho_sakusei_start,
                               param.dt_seiho_sakusei_end,
                               param.nm_seiho_sakusei,
                               param.status,
                               param.kbn_haishi,
                               param.cd_seihin,
                               param.nm_seihin,
                               param.nm_haigo,
                               param.cd_kaisha_daihyo,
                               param.cd_kojyo_daihyo,
                               param.nm_seizo,
                               param.cd_hin,
                               param.kbn_shikakari,
                               param.nm_hin,
                               param.no_kikaku1,
                               param.no_kikaku2,
                               param.no_kikaku3,
                               param.nm_kikaku,
                               param.nm_hanbai,
                               param.kbn_hin,
                               param.kbn_kengen,
                               param.cd_kaisha,
                               param.sort,
                               param.set_haishi,
                               param.set_hin,
                               param.set_category,
                               param.set_kengen,
                               param.set_denso_taisho,
                               param.set_mishiyo

                    ).ToList();
                
                MemoryStream stream = new MemoryStream();
                TextFieldFile<sp_shohinkaihatsu_download_200_Result> tFile
                                    = new TextFieldFile<sp_shohinkaihatsu_download_200_Result>(stream,
                                                                          Encoding.GetEncoding(Properties.Resources.Encoding), CSV200_Download);
                tFile.Delimiters = new string[] { "," };
                // TODO: ヘッダーを有効にするには IsFirstRowHeader を true に設定にします。
                tFile.IsFirstRowHeader = true;
                tFile.WriteFields(results as IEnumerable<sp_shohinkaihatsu_download_200_Result>);

                return FileUploadDownloadUtility.CreateFileResponse(stream, csvname);
                //}
            }
        }

        #endregion

      
    }


}
