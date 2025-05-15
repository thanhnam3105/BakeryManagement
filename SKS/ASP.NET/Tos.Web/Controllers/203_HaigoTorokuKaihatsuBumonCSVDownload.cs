using System;
using System.Collections;
using System.Collections.Generic;
using System.Data.Common;
using System.Data.Entity.Infrastructure;
using System.IO;
using System.IO.Compression;
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
    public class _203_HaigoTorokuKaihatsuBumonCSVDownloadController : ApiController
    {
        #region "CSVファイルの項目設定"

        private static readonly TextFieldSetting[] CSV203_DownloadSeiho = new TextFieldSetting[]
        {
            
            new TextFieldSetting() { PropertyName = "no_seiho_SEIHO", DisplayName = "製法番号"},
            new TextFieldSetting() { PropertyName = "nm_seiho", DisplayName = "製法名",WrapChar="\""},
            new TextFieldSetting() { PropertyName = "cd_haigo_HAIGO", DisplayName = "配合コード",},
            new TextFieldSetting() { PropertyName = "nm_haigo", DisplayName = "配合名",WrapChar="\""},
            new TextFieldSetting() { PropertyName = "nm_haigo_r", DisplayName = "配合名略",WrapChar="\""},
            new TextFieldSetting() { PropertyName = "cd_kaisha_daiyo", DisplayName = "代表会社コード",},
            new TextFieldSetting() { PropertyName = "cd_kojyo_daihyo", DisplayName = "代表工場コード",},
            new TextFieldSetting() { PropertyName = "kbn_hin", DisplayName = "品区分コード",},
            new TextFieldSetting() { PropertyName = "cd_bunrui", DisplayName = "分類コード",},
            new TextFieldSetting() { PropertyName = "budomari", DisplayName = "歩留",},
            new TextFieldSetting() { PropertyName = "qty_kihon", DisplayName = "基本重量", WrapChar = "\""},
            new TextFieldSetting() { PropertyName = "ritsu_kihon", DisplayName = "基本倍率",},
            new TextFieldSetting() { PropertyName = "cd_setsubi", DisplayName = "設備コード",},
            new TextFieldSetting() { PropertyName = "flg_gasan", DisplayName = "仕込み合算フラグ",},
            new TextFieldSetting() { PropertyName = "qty_max", DisplayName = "仕込み最大重量",},
            new TextFieldSetting() { PropertyName = "qty_haigo_kei", DisplayName = "配合合計重量",},
            new TextFieldSetting() { PropertyName = "biko", DisplayName = "備考",WrapChar="\""},
            new TextFieldSetting() { PropertyName = "no_seiho_HAIGO", DisplayName = "製法番号",},
            new TextFieldSetting() { PropertyName = "cd_kaisha_HAIHO", DisplayName = "会社コード",},
            new TextFieldSetting() { PropertyName = "kbn_vw", DisplayName = "V/W区分",},
            new TextFieldSetting() { PropertyName = "hijyu", DisplayName = "比重",},
            new TextFieldSetting() { PropertyName = "flg_mishiyo", DisplayName = "未使用フラグ", WrapChar = "\""},
            new TextFieldSetting() { PropertyName = "kbn_haishi", DisplayName = "廃止区分",},
            new TextFieldSetting() { PropertyName = "kbn_shiagari", DisplayName = "仕上区分",},
            new TextFieldSetting() { PropertyName = "statu", DisplayName = "ステータス",},
            new TextFieldSetting() { PropertyName = "cd_seiho_bunrui", DisplayName = "製法分類",},
            new TextFieldSetting() { PropertyName = "no_seiho_sanko", DisplayName = "参考製法番号",},
            new TextFieldSetting() { PropertyName = "dt_toruko", DisplayName = "登録日時",},
            new TextFieldSetting() { PropertyName = "cd_toroku_kaisha", DisplayName = "登録者会社コード",},
            new TextFieldSetting() { PropertyName = "nm_kaisha_toroku", DisplayName = "登録者会社名",WrapChar="\""},
            new TextFieldSetting() { PropertyName = "cd_toroku", DisplayName = "登録者コード",},
            new TextFieldSetting() { PropertyName = "dt_henko_HAIGO", DisplayName = "変更日時", Format="yyyy/MM/dd HH:mm:ss"},
            new TextFieldSetting() { PropertyName = "cd_koshin_HAIGO", DisplayName = "変更者会社コード", WrapChar = "\""},
            new TextFieldSetting() { PropertyName = "nm_kaisha_koshin", DisplayName = "変更者会社名",WrapChar="\""},
            new TextFieldSetting() { PropertyName = "cd_koshin", DisplayName = "変更者コード",},
            new TextFieldSetting() { PropertyName = "cd_haigo_sanko", DisplayName = "参考配合コード",},
            new TextFieldSetting() { PropertyName = "cd_haigo_SEIZO", DisplayName = "配合コード",},
            new TextFieldSetting() { PropertyName = "no_yusen", DisplayName = "優先順位",},
            new TextFieldSetting() { PropertyName = "cd_line", DisplayName = "ラインコード",},
            new TextFieldSetting() { PropertyName = "dt_henko_SEIZO", DisplayName = "変更日時", Format="yyyy/MM/dd HH:mm:ss"},
            new TextFieldSetting() { PropertyName = "cd_koshin_kaisha", DisplayName = "更新者会社コード",},
            new TextFieldSetting() { PropertyName = "nm_kaisha_seizo", DisplayName = "更新者会社名",WrapChar="\""},
            new TextFieldSetting() { PropertyName = "cd_koshin_SEIZO", DisplayName = "更新者コード",},

        };
        private static readonly TextFieldSetting[] CSV203_DownloadDetail = new TextFieldSetting[]
        {
            new TextFieldSetting() { PropertyName = "no_seiho_SEIHO", DisplayName = "製法番号",},
            new TextFieldSetting() { PropertyName = "nm_seiho_SEIHO", DisplayName = "製法名",WrapChar="\""},
            new TextFieldSetting() { PropertyName = "cd_haigo", DisplayName = "配合コード",},
            new TextFieldSetting() { PropertyName = "nm_haigo", DisplayName = "配合名",WrapChar="\""},
            new TextFieldSetting() { PropertyName = "nm_haigo_r", DisplayName = "配合名略",WrapChar="\""},
            new TextFieldSetting() { PropertyName = "cd_kaisha_daiyo", DisplayName = "代表会社コード",},
            new TextFieldSetting() { PropertyName = "cd_kojyo_daihyo", DisplayName = "代表工場コード",},
            new TextFieldSetting() { PropertyName = "kbn_hin", DisplayName = "品区分コード",},
            new TextFieldSetting() { PropertyName = "cd_bunrui", DisplayName = "分類コード",},
            new TextFieldSetting() { PropertyName = "budomari", DisplayName = "歩留",},
            new TextFieldSetting() { PropertyName = "qty_kihon", DisplayName = "基本重量",},
            new TextFieldSetting() { PropertyName = "ritsu_kihon", DisplayName = "基本倍率",},
            new TextFieldSetting() { PropertyName = "cd_setsubi", DisplayName = "設備コード",},
            new TextFieldSetting() { PropertyName = "flg_gasan", DisplayName = "仕込み合算フラグ",},
            new TextFieldSetting() { PropertyName = "qty_max", DisplayName = "仕込み最大重量",},
            new TextFieldSetting() { PropertyName = "qty_haigo_kei", DisplayName = "配合合計重量",},
            new TextFieldSetting() { PropertyName = "biko", DisplayName = "備考",WrapChar="\""},
            new TextFieldSetting() { PropertyName = "kbn_vw", DisplayName = "V/W区分",},
            new TextFieldSetting() { PropertyName = "hijyu", DisplayName = "比重",},
            new TextFieldSetting() { PropertyName = "flg_mishiyo", DisplayName = "未使用フラグ",},
            new TextFieldSetting() { PropertyName = "kbn_haishi", DisplayName = "廃止区分",},
            new TextFieldSetting() { PropertyName = "kbn_shiagari", DisplayName = "仕上区分",},
            new TextFieldSetting() { PropertyName = "statu", DisplayName = "ステータス",},
            new TextFieldSetting() { PropertyName = "cd_seiho_bunrui", DisplayName = "製法分類",},
            new TextFieldSetting() { PropertyName = "no_seiho_sanko", DisplayName = "参考製法番号",},
            new TextFieldSetting() { PropertyName = "dt_toroku", DisplayName = "登録日時",Format="yyyy/MM/dd HH:mm:ss"},
            new TextFieldSetting() { PropertyName = "cd_toroku_kaisha", DisplayName = "登録者会社コード",},
            new TextFieldSetting() { PropertyName = "nm_kaisha_toruku", DisplayName = "登録者会社名",WrapChar="\""},
            new TextFieldSetting() { PropertyName = "cd_toroku", DisplayName = "登録者コード",},
            new TextFieldSetting() { PropertyName = "dt_henko", DisplayName = "変更日時",Format="yyyy/MM/dd HH:mm:ss"},
            new TextFieldSetting() { PropertyName = "cd_koshin_kaisha", DisplayName = "変更者会社コード",},
            new TextFieldSetting() { PropertyName = "nm_kaisha_koshin", DisplayName = "変更者会社名",WrapChar="\""},
            new TextFieldSetting() { PropertyName = "cd_koshin", DisplayName = "変更者コード",},
            new TextFieldSetting() { PropertyName = "cd_haigo_sanko", DisplayName = "参考配合コード",},
            new TextFieldSetting() { PropertyName = "kbn_bunkatsu", DisplayName = "分割区分",},
            new TextFieldSetting() { PropertyName = "no_kotei", DisplayName = "工程番号",},
            new TextFieldSetting() { PropertyName = "no_tonyu", DisplayName = "投入順",},
            new TextFieldSetting() { PropertyName = "cd_hin", DisplayName = "原料コード",},
            new TextFieldSetting() { PropertyName = "flg_shitei", DisplayName = "指定原料フラグ",},
            new TextFieldSetting() { PropertyName = "kbn_hin_MEISAI", DisplayName = "品区分",},
            new TextFieldSetting() { PropertyName = "kbn_shikakari", DisplayName = "仕掛品区分",},
            new TextFieldSetting() { PropertyName = "nm_hin", DisplayName = "原料名(作業指示・工程名)",WrapChar="\""},
            new TextFieldSetting() { PropertyName = "cd_mark", DisplayName = "マークコード",},
            new TextFieldSetting() { PropertyName = "mark", DisplayName = "マーク",WrapChar="\""},
            new TextFieldSetting() { PropertyName = "qty_haigo", DisplayName = "配合重量",},
            new TextFieldSetting() { PropertyName = "hijyu_VH", DisplayName = "比重",},
            new TextFieldSetting() { PropertyName = "qty_nisugata", DisplayName = "荷姿重量",},
            new TextFieldSetting() { PropertyName = "gosa", DisplayName = "誤差",},
            new TextFieldSetting() { PropertyName = "budomari_MEISAI", DisplayName = "歩留",},
           
        };

        #endregion

        #region "Controllerで公開するAPI"

        /// <summary>
        /// Download CSV 配合製造ライン 203
        /// </summary>
        /// <param name="options"></param>
        /// <returns>203_配合製造ラインCSV</returns>
        public HttpResponseMessage Get([FromUri] FilterCSV param)
        {
            // 保存CSVファイル名（ユーザID_CsvDownload_現在日時.csv）
            decimal _userName = Convert.ToDecimal(UserInfo.GetUserNameFromIdentity(this.User.Identity));
            string userName = "_" + _userName.ToString() + "_";
            string filename = Properties.Resources.CSV203_DownloadSeiho + userName;
            string csvname = filename + DateTime.Now.ToString("yyyyMMddHHmmss") + Properties.Resources.CsvExtension;
            using (ShohinKaihatsuEntities context = new ShohinKaihatsuEntities())
            {
                var results = context.sp_shohinkaihatsu_download_203(param.cd_haigo).ToList();
                
                MemoryStream stream = new MemoryStream();
                TextFieldFile<sp_shohinkaihatsu_download_203_Result> tFile
                                    = new TextFieldFile<sp_shohinkaihatsu_download_203_Result>(stream,
                                                                          Encoding.GetEncoding(Properties.Resources.Encoding), CSV203_DownloadSeiho);
                tFile.Delimiters = new string[] { "," };
                // TODO: ヘッダーを有効にするには IsFirstRowHeader を true に設定にします。
                tFile.IsFirstRowHeader = true;
                tFile.WriteFields(results as IEnumerable<sp_shohinkaihatsu_download_203_Result>);
                return FileUploadDownloadUtility.CreateFileResponse(stream, csvname);
            }
        }
        /// <summary>
        /// Download CSV 配合明細 203
        /// </summary>
        /// <param name="param"></param>
        /// <returns>203_配合明細CSV</returns>
        public HttpResponseMessage GetCSV_2([FromUri] FilterCSV param)
        {
            // 保存CSVファイル名（ユーザID_CsvDownload_現在日時.csv）
            decimal _userName = Convert.ToDecimal(UserInfo.GetUserNameFromIdentity(this.User.Identity));
            string userName = "_" + _userName.ToString() + "_";
            string filename = Properties.Resources.CSV203_DownloadDetail + userName;
            string csvname = filename + DateTime.Now.ToString("yyyyMMddHHmmss") + Properties.Resources.CsvExtension;
            var kbn_bunkatsu = Convert.ToInt32(Properties.Resources.kbn_bunkatsu);
            using (ShohinKaihatsuEntities context = new ShohinKaihatsuEntities())
            {
                var results = context.sp_shohinkaihatsu_download_seiho_203(
                                    kbn_bunkatsu,
                                    param.cd_haigo,
                                    param.cd_kaisha_daihyo,
                                    param.cd_kojyo_daihyo,
                                    param.no_seiho_kaisha
                    ).ToList();
                MemoryStream stream = new MemoryStream();
                TextFieldFile<sp_shohinkaihatsu_download_seiho_203_Result> tFile
                                    = new TextFieldFile<sp_shohinkaihatsu_download_seiho_203_Result>(stream,
                                                                          Encoding.GetEncoding(Properties.Resources.Encoding), CSV203_DownloadDetail);
                
                tFile.Delimiters = new string[] { "," };
                // TODO: ヘッダーを有効にするには IsFirstRowHeader を true に設定にします。
                tFile.IsFirstRowHeader = true;
                tFile.WriteFields(results as IEnumerable<sp_shohinkaihatsu_download_seiho_203_Result>);
                return FileUploadDownloadUtility.CreateFileResponse(stream, csvname);
            }
        }


        #endregion
    }
}
