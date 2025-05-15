using System;
using System.Collections;
using System.Collections.Generic;
using System.Data.Common;
using System.Data.Entity.Infrastructure;
using System.Data.SqlClient;
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
using Tos.Web.DataFP;
using Tos.Web.Logging;

namespace Tos.Web.Controllers
{
    //created from 【CsvUploadDownloadController(Ver2.0)】 Template
    public class _209_HaigoTorokuKojyoBumonDownloadCSVController : ApiController
    {
        #region "CSVファイルの項目設定"

        private static readonly TextFieldSetting[] CSV209_Download = new TextFieldSetting[]
        {
            new TextFieldSetting() { PropertyName = "no_seq", DisplayName = "シーケンス番号",},
            new TextFieldSetting() { PropertyName = "cd_haigo", DisplayName = "配合コード",WrapChar="\""},
            new TextFieldSetting() { PropertyName = "nm_haigo", DisplayName = "配合名",WrapChar="\""},
            new TextFieldSetting() { PropertyName = "nm_haigo_r", DisplayName = "配合名略",WrapChar="\""},
            new TextFieldSetting() { PropertyName = "kbn_hin", DisplayName = "品区分コード",WrapChar="\""},
            new TextFieldSetting() { PropertyName = "cd_bunrui", DisplayName = "分類コード",WrapChar="\""},
            new TextFieldSetting() { PropertyName = "budomari", DisplayName = "歩留",},
            new TextFieldSetting() { PropertyName = "qty_kihon", DisplayName = "基本重量",},
            new TextFieldSetting() { PropertyName = "ritsu_kihon", DisplayName = "基本倍率",},
            new TextFieldSetting() { PropertyName = "cd_setsubi", DisplayName = "設備コード",WrapChar="\""},
            new TextFieldSetting() { PropertyName = "flg_gasan", DisplayName = "仕込み合算フラグ",},
            new TextFieldSetting() { PropertyName = "qty_max", DisplayName = "仕込み最大重量",},
            new TextFieldSetting() { PropertyName = "no_han", DisplayName = "版番号",},
            new TextFieldSetting() { PropertyName = "qty_haigo_h", DisplayName = "配合重量",},
            new TextFieldSetting() { PropertyName = "qty_haigo_kei", DisplayName = "配合合計重量",},
            new TextFieldSetting() { PropertyName = "biko", DisplayName = "備考",WrapChar="\""},
            new TextFieldSetting() { PropertyName = "no_seiho", DisplayName = "製法番号",WrapChar="\""},
            new TextFieldSetting() { PropertyName = "cd_tanto_seizo", DisplayName = "製造担当者コード",WrapChar="\""},
            new TextFieldSetting() { PropertyName = "dt_seizo", DisplayName = "製造更新日付",Format="yyyy/MM/dd HH:mm:ss"},
            new TextFieldSetting() { PropertyName = "cd_tanto_hinkan", DisplayName = "品管担当者コード",WrapChar="\""},
            new TextFieldSetting() { PropertyName = "dt_hinkan", DisplayName = "品管更新日付",Format="yyyy/MM/dd HH:mm:ss"},
            new TextFieldSetting() { PropertyName = "dt_from", DisplayName = "有効日付（開始）",Format="yyyy/MM/dd"},
            new TextFieldSetting() { PropertyName = "dt_to", DisplayName = "有効日付（終了）",Format="yyyy/MM/dd"},
            new TextFieldSetting() { PropertyName = "kbn_vw", DisplayName = "V/W区分（Kg/L）",WrapChar="\""},
            new TextFieldSetting() { PropertyName = "hijyu", DisplayName = "比重",},
            new TextFieldSetting() { PropertyName = "flg_shorihin", DisplayName = "処理品フラグ",},
            new TextFieldSetting() { PropertyName = "flg_hinkan", DisplayName = "品管担当フラグ",},
            new TextFieldSetting() { PropertyName = "flg_seizo", DisplayName = "製造担当フラグ",},
            new TextFieldSetting() { PropertyName = "flg_sakujyo", DisplayName = "削除フラグ",},
            new TextFieldSetting() { PropertyName = "flg_mishiyo", DisplayName = "未使用フラグ",},
            new TextFieldSetting() { PropertyName = "dt_toroku", DisplayName = "登録日時",Format="yyyy/MM/dd HH:mm:ss"},
            new TextFieldSetting() { PropertyName = "dt_henko", DisplayName = "変更日時",Format="yyyy/MM/dd HH:mm:ss"},
            new TextFieldSetting() { PropertyName = "cd_koshin", DisplayName = "更新者コード",WrapChar="\""},
            new TextFieldSetting() { PropertyName = "kbn_shiagari", DisplayName = "仕上区分",},
            new TextFieldSetting() { PropertyName = "cd_haigo_seiho", DisplayName = "配合コード（製法）",WrapChar="\""},
            new TextFieldSetting() { PropertyName = "flg_seiho_base", DisplayName = "製法原本フラグ",},
            new TextFieldSetting() { PropertyName = "nm_seiho", DisplayName = "製法名",WrapChar="\""},
            new TextFieldSetting() { PropertyName = "no_seq_LINE", DisplayName = "シーケンス番号",},
            new TextFieldSetting() { PropertyName = "cd_haigo_LINE", DisplayName = "配合コード",},
            new TextFieldSetting() { PropertyName = "no_yusen_LINE", DisplayName = "優先順位",},
            new TextFieldSetting() { PropertyName = "cd_line", DisplayName = "ラインコード",},
            new TextFieldSetting() { PropertyName = "flg_sakujyo_LINE", DisplayName = "削除フラグ",},
            new TextFieldSetting() { PropertyName = "flg_mishiyo_LINE", DisplayName = "未使用フラグ",},
            new TextFieldSetting() { PropertyName = "dt_toroku_LINE", DisplayName = "登録日時",Format="yyyy/MM/dd HH:mm:ss"},
            new TextFieldSetting() { PropertyName = "dt_kenko_LINE", DisplayName = "変更日時",Format="yyyy/MM/dd HH:mm:ss"},
            new TextFieldSetting() { PropertyName = "cd_koshin_LINE", DisplayName = "更新者コード",WrapChar="\""},
        };
        private static readonly TextFieldSetting[] CSV209_DownloadDetail = new TextFieldSetting[]
        {
            new TextFieldSetting() { PropertyName = "no_seq", DisplayName = "シーケンス番号",},
            new TextFieldSetting() { PropertyName = "cd_haigo", DisplayName = "配合コード",WrapChar="\""},
            new TextFieldSetting() { PropertyName = "nm_haigo", DisplayName = "配合名",WrapChar="\""},
            new TextFieldSetting() { PropertyName = "nm_haigo_r", DisplayName = "配合名略",WrapChar="\""},
            new TextFieldSetting() { PropertyName = "kbn_hin", DisplayName = "品区分コード",WrapChar="\""},
            new TextFieldSetting() { PropertyName = "cd_bunrui", DisplayName = "分類コード",WrapChar="\""},
            new TextFieldSetting() { PropertyName = "budomari", DisplayName = "歩留",},
            new TextFieldSetting() { PropertyName = "qty_kihon", DisplayName = "基本重量",},
            new TextFieldSetting() { PropertyName = "ritsu_kihon", DisplayName = "基本倍率",},
            new TextFieldSetting() { PropertyName = "cd_setsubi", DisplayName = "設備コード",WrapChar="\""},
            new TextFieldSetting() { PropertyName = "flg_gasan", DisplayName = "仕込み合算フラグ",},
            new TextFieldSetting() { PropertyName = "qty_max", DisplayName = "仕込み最大重量",},
            new TextFieldSetting() { PropertyName = "no_han", DisplayName = "版番号",},
            new TextFieldSetting() { PropertyName = "qty_haigo_h", DisplayName = "配合重量",},
            new TextFieldSetting() { PropertyName = "qty_haigo_kei", DisplayName = "配合合計重量",},
            new TextFieldSetting() { PropertyName = "biko", DisplayName = "備考",WrapChar="\""},
            new TextFieldSetting() { PropertyName = "no_seiho", DisplayName = "製法番号",WrapChar="\""},
            new TextFieldSetting() { PropertyName = "cd_tanto_seizo", DisplayName = "製造担当者コード",WrapChar="\""},
            new TextFieldSetting() { PropertyName = "dt_seizo", DisplayName = "製造更新日付",Format="yyyy/MM/dd HH:mm:ss"},
            new TextFieldSetting() { PropertyName = "cd_tanto_hinkan", DisplayName = "品管担当者コード",WrapChar="\""},
            new TextFieldSetting() { PropertyName = "dt_hinkan", DisplayName = "品管更新日付",Format="yyyy/MM/dd HH:mm:ss"},
            new TextFieldSetting() { PropertyName = "dt_from", DisplayName = "有効日付（開始）",Format="yyyy/MM/dd"},
            new TextFieldSetting() { PropertyName = "dt_to", DisplayName = "有効日付（終了）",Format="yyyy/MM/dd"},
            new TextFieldSetting() { PropertyName = "kbn_vw", DisplayName = "V/W区分（Kg/L）",WrapChar="\""},
            new TextFieldSetting() { PropertyName = "hijyu", DisplayName = "比重",},
            new TextFieldSetting() { PropertyName = "flg_shorihin", DisplayName = "処理品フラグ",},
            new TextFieldSetting() { PropertyName = "flg_hinkan", DisplayName = "品管担当フラグ",},
            new TextFieldSetting() { PropertyName = "flg_seizo", DisplayName = "製造担当フラグ",},
            new TextFieldSetting() { PropertyName = "flg_sakujyo", DisplayName = "削除フラグ",},
            new TextFieldSetting() { PropertyName = "flg_mishiyo", DisplayName = "未使用フラグ",},
            new TextFieldSetting() { PropertyName = "dt_toroku", DisplayName = "登録日時",Format="yyyy/MM/dd HH:mm:ss"},
            new TextFieldSetting() { PropertyName = "dt_henko", DisplayName = "変更日時",Format="yyyy/MM/dd HH:mm:ss"},
            new TextFieldSetting() { PropertyName = "cd_koshin", DisplayName = "更新者コード",WrapChar="\""},
            new TextFieldSetting() { PropertyName = "kbn_shiagari", DisplayName = "仕上区分",},
            new TextFieldSetting() { PropertyName = "cd_haigo_seiho", DisplayName = "配合コード（製法）",WrapChar="\""},
            new TextFieldSetting() { PropertyName = "flg_seiho_base", DisplayName = "製法原本フラグ",},
            new TextFieldSetting() { PropertyName = "nm_seiho", DisplayName = "製法名",WrapChar="\""},
            new TextFieldSetting() { PropertyName = "no_seq_r", DisplayName = "シーケンス番号",},
            new TextFieldSetting() { PropertyName = "cd_haigo_r", DisplayName = "配合コード(配合名)",WrapChar="\""},
            new TextFieldSetting() { PropertyName = "no_han_r", DisplayName = "版番号(配合名)",},
            new TextFieldSetting() { PropertyName = "qty_haigo_h_r", DisplayName = "配合重量(配合名)",},
            new TextFieldSetting() { PropertyName = "kbn_bunkatsu_r", DisplayName = "分割区分",},
            new TextFieldSetting() { PropertyName = "no_kotei_r", DisplayName = "工程番号",},
            new TextFieldSetting() { PropertyName = "no_tonyu_r", DisplayName = "投入順",},
            new TextFieldSetting() { PropertyName = "cd_hin_r", DisplayName = "原料コード",},
            new TextFieldSetting() { PropertyName = "kbn_hin_r", DisplayName = "品区分",WrapChar="\""},
            new TextFieldSetting() { PropertyName = "nm_hin_r", DisplayName = "原料名",WrapChar="\""},
            new TextFieldSetting() { PropertyName = "cd_mark_r", DisplayName = "マークコード",WrapChar="\""},
            new TextFieldSetting() { PropertyName = "qty_r", DisplayName = "基本重量",},
            new TextFieldSetting() { PropertyName = "qty_haigo_r", DisplayName = "配合重量",},
            new TextFieldSetting() { PropertyName = "qty_nisugata_r", DisplayName = "荷姿重量",},
            new TextFieldSetting() { PropertyName = "su_nisugata_r", DisplayName = "(荷姿重量)数",},
            new TextFieldSetting() { PropertyName = "qty_kowake_r", DisplayName = "小分け重量",},
            new TextFieldSetting() { PropertyName = "su_kowake_r", DisplayName = "(小分け重量)数",},
            new TextFieldSetting() { PropertyName = "cd_futai_r", DisplayName = "風袋コード",WrapChar="\""},
            new TextFieldSetting() { PropertyName = "qty_futai_r", DisplayName = "風袋重量",},
            new TextFieldSetting() { PropertyName = "hijyu_r", DisplayName = "比重",},
            new TextFieldSetting() { PropertyName = "budomari_r", DisplayName = "歩留",},
            new TextFieldSetting() { PropertyName = "flg_sakujyo_r", DisplayName = "削除フラグ",},
            new TextFieldSetting() { PropertyName = "flg_mishiyo_r", DisplayName = "未使用フラグ",},
            new TextFieldSetting() { PropertyName = "dt_toroku_r", DisplayName = "登録日時",Format="yyyy/MM/dd HH:mm:ss"},
            new TextFieldSetting() { PropertyName = "dt_kenko_r", DisplayName = "変更日時",Format="yyyy/MM/dd HH:mm:ss"},
            new TextFieldSetting() { PropertyName = "cd_koshin_r", DisplayName = "更新者コード",WrapChar="\""},
            new TextFieldSetting() { PropertyName = "kbn_jyotai_r", DisplayName = "状態区分",},
            new TextFieldSetting() { PropertyName = "kbn_shitei_r", DisplayName = "指定原料区分",},
        };
        #endregion
        public readonly int flgFalse = 0; 
        #region "Controllerで公開するAPI"

        /// <summary>
        /// Download CSV 配合製造ライン 209
        /// </summary>
        /// <param name="options"></param>
        /// <returns>209_配合製造ラインCSV</returns>
        public HttpResponseMessage Get([FromUri]FilterCSV209 paramaters)
        {
            // 保存CSVファイル名（ユーザID_CsvDownload_現在日時.csv）
            decimal _userName = Convert.ToDecimal(UserInfo.GetUserNameFromIdentity(this.User.Identity));
            string userName = "_" + _userName.ToString() + "_";
            string filename = Properties.Resources.CSV209_Download + userName;
            string csvname = filename + DateTime.Now.ToString("yyyyMMddHHmmss") + Properties.Resources.CsvExtension;

            using (FOODPROCSEntities context = new FOODPROCSEntities(paramaters.cd_kaisha,paramaters.cd_kojyo))
             {
                 context.Configuration.ProxyCreationEnabled = false;
            //     //テーブル（ビュー）の項目のみでダウンロードする場合は以下を使用します
                    
            //     //テーブル（ビュー）の項目＋更新区分を追加してダウンロードする場合は以下を使用します
                 IEnumerable results="";
                 if (paramaters.M_kirikae == Convert.ToInt32(Properties.Resources.m_kirikae_hyoji))
                 {
                     var query = "SELECT                                                                 " +
                                 "     MEI_HYOJI.no_seq                 AS no_seq                        " +
                                 "    ,MEI_HYOJI.cd_haigo               AS cd_haigo                      " +
                                 "    ,MEI_HYOJI.nm_haigo               AS nm_haigo                      " +
                                 "    ,MEI_HYOJI.nm_haigo_r             AS nm_haigo_r                    " +
                                 "    ,MEI_HYOJI.kbn_hin                AS kbn_hin                       " +
                                 "    ,MEI_HYOJI.cd_bunrui              AS cd_bunrui                     " +
                                 "    ,MEI_HYOJI.budomari               AS budomari                      " +
                                 "    ,MEI_HYOJI.qty_kihon              AS qty_kihon                     " +
                                 "    ,MEI_HYOJI.ritsu_kihon            AS ritsu_kihon                   " +
                                 "    ,MEI_HYOJI.cd_setsubi             AS cd_setsubi                    " +
                                 "    ,MEI_HYOJI.flg_gasan              AS flg_gasan                     " +
                                 "    ,MEI_HYOJI.qty_max                AS qty_max                       " +
                                 "    ,MEI_HYOJI.no_han                 AS no_han                        " +
                                 "    ,MEI_HYOJI.qty_haigo_h            AS qty_haigo_h                   " +
                                 "    ,MEI_HYOJI.qty_haigo_kei          AS qty_haigo_kei                 " +
                                 "    ,MEI_HYOJI.biko                   AS biko                          " +
                                 "    ,MEI_HYOJI.no_seiho               AS no_seiho                      " +
                                 "    ,MEI_HYOJI.cd_tanto_seizo         AS cd_tanto_seizo                " +
                                 "    ,MEI_HYOJI.dt_seizo               AS dt_seizo                      " +
                                 "    ,MEI_HYOJI.cd_tanto_hinkan        AS cd_tanto_hinkan               " +
                                 "    ,MEI_HYOJI.dt_hinkan              AS dt_hinkan                     " +
                                 "    ,MEI_HYOJI.dt_from                AS dt_from                       " +
                                 "    ,MEI_HYOJI.dt_to                  AS dt_to                         " +
                                 "    ,MEI_HYOJI.kbn_vw                 AS kbn_vw                        " +
                                 "    ,MEI_HYOJI.hijyu                  AS hijyu                         " +
                                 "    ,MEI_HYOJI.flg_shorihin           AS flg_shorihin                  " +
                                 "    ,MEI_HYOJI.flg_hinkan             AS flg_hinkan                    " +
                                 "    ,MEI_HYOJI.flg_seizo              AS flg_seizo                     " +
                                 "    ,MEI_HYOJI.flg_sakujyo            AS flg_sakujyo                   " +
                                 "    ,MEI_HYOJI.flg_mishiyo            AS flg_mishiyo                   " +
                                 "    ,MEI_HYOJI.dt_toroku              AS dt_toroku                     " +
                                 "    ,MEI_HYOJI.dt_henko               AS dt_henko                      " +
                                 "    ,MEI_HYOJI.cd_koshin              AS cd_koshin                     " +
                                 "    ,MEI_HYOJI.kbn_shiagari           AS kbn_shiagari                  " +
                                 "    ,MEI_HYOJI.cd_haigo_seiho         AS cd_haigo_seiho                " +
                                 "    ,MEI_HYOJI.flg_seiho_base         AS flg_seiho_base                " +
                                 "    ,MEI_HYOJI.nm_seiho               AS nm_seiho                      " +
                                 "    ,LINE_HYOJI.no_seq                AS no_seq_LINE                   " +
                                 "    ,LINE_HYOJI.cd_haigo              AS cd_haigo_LINE                 " +
                                 "    ,LINE_HYOJI.no_yusen              AS no_yusen_LINE                 " +
                                 "    ,LINE_HYOJI.cd_line               AS cd_line                       " +
                                 "    ,LINE_HYOJI.flg_sakujyo           AS flg_sakujyo_LINE              " +
                                 "    ,LINE_HYOJI.flg_mishiyo           AS flg_mishiyo_LINE              " +
                                 "    ,LINE_HYOJI.dt_toroku             AS dt_toroku_LINE                " +
                                 "    ,LINE_HYOJI.dt_henko              AS dt_kenko_LINE                 " +
                                 "    ,LINE_HYOJI.cd_koshin             AS cd_koshin_LINE                " +
                                 "FROM                                                                   " +
                                 "(SELECT                                                                " +
                                 "     no_seq                                                            " +
                                 "    ,cd_haigo                                                          " +
                                 "    ,nm_haigo                                                          " +
                                 "    ,nm_haigo_r                                                        " +
                                 "    ,kbn_hin                                                           " +
                                 "    ,cd_bunrui                                                         " +
                                 "    ,budomari                                                          " +
                                 "    ,qty_kihon                                                         " +
                                 "    ,ritsu_kihon                                                       " +
                                 "    ,cd_setsubi                                                        " +
                                 "    ,flg_gasan                                                         " +
                                 "    ,qty_max                                                           " +
                                 "    ,no_han                                                            " +
                                 "    ,qty_haigo_h                                                       " +
                                 "    ,qty_haigo_kei                                                     " +
                                 "    ,biko                                                              " +
                                 "    ,no_seiho                                                          " +
                                 "    ,cd_tanto_seizo                                                    " +
                                 "    ,dt_seizo                                                          " +
                                 "    ,cd_tanto_hinkan                                                   " +
                                 "    ,dt_hinkan                                                         " +
                                 "    ,dt_from                                                           " +
                                 "    ,dt_to                                                             " +
                                 "    ,kbn_vw                                                            " +
                                 "    ,hijyu                                                             " +
                                 "    ,flg_shorihin                                                      " +
                                 "    ,flg_hinkan                                                        " +
                                 "    ,flg_seizo                                                         " +
                                 "    ,flg_sakujyo                                                       " +
                                 "    ,flg_mishiyo                                                       " +
                                 "    ,dt_toroku                                                         " +
                                 "    ,dt_henko                                                          " +
                                 "    ,cd_koshin                                                         " +
                                 "    ,kbn_shiagari                                                      " +
                                 "    ,nm_haigo_rm                                                       " +
                                 "    ,cd_haigo_seiho                                                    " +
                                 "    ,flg_seiho_base                                                    " +
                                 "    ,nm_seiho                                                          " +
                                 "FROM ma_haigo_mei_hyoji) MEI_HYOJI                                     " +
                                 "INNER JOIN ma_seizo_line_hyoji LINE_HYOJI                              " +
                                 "ON MEI_HYOJI.cd_haigo = LINE_HYOJI.cd_haigo                            " +

                                 "WHERE                                                                  " +
                                 "    (@cd_haigo IS NULL OR MEI_HYOJI.cd_haigo = @cd_haigo)              " +
                                 "    AND (@no_han IS NULL OR MEI_HYOJI.no_han = @no_han)                " +
                                 "    AND (MEI_HYOJI.flg_sakujyo = @flgFalse)                            " +
                                 "    AND (LINE_HYOJI.flg_mishiyo = @flgFalse)                           " +
                                 "    AND (LINE_HYOJI.flg_sakujyo = @flgFalse)                           " +
                                 "ORDER BY LINE_HYOJI.no_yusen ASC                                       ";          
                    var param =new[]{
                                        new SqlParameter("@cd_haigo",paramaters.cd_haigo),
                                        new SqlParameter("@no_han",paramaters.no_han),
                                        new SqlParameter("@flgFalse",flgFalse) 
                                };
                    results = context.Database.SqlQuery<CSV209_Download>(query, param).ToList();
                 }
                 else if (paramaters.M_kirikae == Convert.ToInt32(Properties.Resources.m_kirikae_foodprocs))
                 {
                     var query = "SELECT                                                                  " +
                                  "     MEI.no_seq                 AS no_seq                              " +
                                  "    ,MEI.cd_haigo               AS cd_haigo                            " +
                                  "    ,MEI.nm_haigo               AS nm_haigo                            " +
                                  "    ,MEI.nm_haigo_r             AS nm_haigo_r                          " +
                                  "    ,MEI.kbn_hin                AS kbn_hin                             " +
                                  "    ,MEI.cd_bunrui              AS cd_bunrui                           " +
                                  "    ,MEI.budomari               AS budomari                            " +
                                  "    ,MEI.qty_kihon              AS qty_kihon                           " +
                                  "    ,MEI.ritsu_kihon            AS ritsu_kihon                         " +
                                  "    ,MEI.cd_setsubi             AS cd_setsubi                          " +
                                  "    ,MEI.flg_gasan              AS flg_gasan                           " +
                                  "    ,MEI.qty_max                AS qty_max                             " +
                                  "    ,MEI.no_han                 AS no_han                              " +
                                  "    ,MEI.qty_haigo_h            AS qty_haigo_h                         " +
                                  "    ,MEI.qty_haigo_kei          AS qty_haigo_kei                       " +
                                  "    ,MEI.biko                   AS biko                                " +
                                  "    ,MEI.no_seiho               AS no_seiho                            " +
                                  "    ,MEI.cd_tanto_seizo         AS cd_tanto_seizo                      " +
                                  "    ,MEI.dt_seizo               AS dt_seizo                            " +
                                  "    ,MEI.cd_tanto_hinkan        AS cd_tanto_hinkan                     " +
                                  "    ,MEI.dt_hinkan              AS dt_hinkan                           " +
                                  "    ,MEI.dt_from                AS dt_from                             " +
                                  "    ,MEI.dt_to                  AS dt_to                               " +
                                  "    ,MEI.kbn_vw                 AS kbn_vw                              " +
                                  "    ,MEI.hijyu                  AS hijyu                               " +
                                  "    ,MEI.flg_shorihin           AS flg_shorihin                        " +
                                  "    ,MEI.flg_hinkan             AS flg_hinkan                          " +
                                  "    ,MEI.flg_seizo              AS flg_seizo                           " +
                                  "    ,MEI.flg_sakujyo            AS flg_sakujyo                         " +
                                  "    ,MEI.flg_mishiyo            AS flg_mishiyo                         " +
                                  "    ,MEI.dt_toroku              AS dt_toroku                           " +
                                  "    ,MEI.dt_henko               AS dt_henko                            " +
                                  "    ,MEI.cd_koshin              AS cd_koshin                           " +
                                  "    ,MEI.kbn_shiagari           AS kbn_shiagari                        " +
                                  "    ,MEI.cd_haigo_seiho         AS cd_haigo_seiho                      " +
                                  "    ,MEI.flg_seiho_base         AS flg_seiho_base                      " +
                                  "    ,MEI.nm_seiho               AS nm_seiho                            " +
                                  "    ,LINE.no_seq                AS no_seq_LINE                         " +
                                  "    ,LINE.cd_haigo              AS cd_haigo_LINE                       " +
                                  "    ,LINE.no_yusen              AS no_yusen_LINE                       " +
                                  "    ,LINE.cd_line               AS cd_line                             " +
                                  "    ,LINE.flg_sakujyo           AS flg_sakujyo_LINE                    " +
                                  "    ,LINE.flg_mishiyo           AS flg_mishiyo_LINE                    " +
                                  "    ,LINE.dt_toroku             AS dt_toroku_LINE                      " +
                                  "    ,LINE.dt_henko              AS dt_kenko_LINE                       " +
                                  "    ,LINE.cd_koshin             AS cd_koshin_LINE                      " +
                                  "FROM                                                                   " +
                                  "(SELECT                                                                " +
                                  "     no_seq                                                            " +
                                  "    ,cd_haigo                                                          " +
                                  "    ,nm_haigo                                                          " +
                                  "    ,nm_haigo_r                                                        " +
                                  "    ,kbn_hin                                                           " +
                                  "    ,cd_bunrui                                                         " +
                                  "    ,budomari                                                          " +
                                  "    ,qty_kihon                                                         " +
                                  "    ,ritsu_kihon                                                       " +
                                  "    ,cd_setsubi                                                        " +
                                  "    ,flg_gasan                                                         " +
                                  "    ,qty_max                                                           " +
                                  "    ,no_han                                                            " +
                                  "    ,qty_haigo_h                                                       " +
                                  "    ,qty_haigo_kei                                                     " +
                                  "    ,biko                                                              " +
                                  "    ,no_seiho                                                          " +
                                  "    ,cd_tanto_seizo                                                    " +
                                  "    ,dt_seizo                                                          " +
                                  "    ,cd_tanto_hinkan                                                   " +
                                  "    ,dt_hinkan                                                         " +
                                  "    ,dt_from                                                           " +
                                  "    ,dt_to                                                             " +
                                  "    ,kbn_vw                                                            " +
                                  "    ,hijyu                                                             " +
                                  "    ,flg_shorihin                                                      " +
                                  "    ,flg_hinkan                                                        " +
                                  "    ,flg_seizo                                                         " +
                                  "    ,flg_sakujyo                                                       " +
                                  "    ,flg_mishiyo                                                       " +
                                  "    ,dt_toroku                                                         " +
                                  "    ,dt_henko                                                          " +
                                  "    ,cd_koshin                                                         " +
                                  "    ,kbn_shiagari                                                      " +
                                  "    ,nm_haigo_rm                                                       " +
                                  "    ,cd_haigo_seiho                                                    " +
                                  "    ,flg_seiho_base                                                    " +
                                  "    ,nm_seiho                                                          " +
                                  "FROM ma_haigo_mei) MEI                                                 " +
                                  "INNER JOIN ma_seizo_line LINE                                          " +
                                  "ON MEI.cd_haigo = LINE.cd_haigo                                        " +

                                  "WHERE                                                                  " +
                                  "    (@cd_haigo IS NULL OR MEI.cd_haigo = @cd_haigo)                    " +
                                  "    AND (@no_han IS NULL OR MEI.no_han = @no_han)                      " +
                                  "    AND (MEI.flg_sakujyo = @flgFalse)                                  " +
                                  "    AND (LINE.flg_mishiyo = @flgFalse)                                 " +
                                  "    AND (LINE.flg_sakujyo = @flgFalse)                                 " +
                                  "    AND (MEI.qty_kihon = MEI.qty_haigo_h)                              " + 
                                  "ORDER BY LINE.no_yusen ASC                                             ";
                     var param = new[]{
                                        new SqlParameter("@cd_haigo",paramaters.cd_haigo),
                                        new SqlParameter("@no_han",paramaters.no_han),
                                        new SqlParameter("@flgFalse",flgFalse)
                                };
                     results = context.Database.SqlQuery<CSV209_Download>(query, param).ToList();
                 }

            MemoryStream stream = new MemoryStream();
            TextFieldFile<CSV209_Download> tFile
                                = new TextFieldFile<CSV209_Download>(stream,
                                                                      Encoding.GetEncoding(Properties.Resources.Encoding), CSV209_Download);
            tFile.Delimiters = new string[] { "," };
            // TODO: ヘッダーを有効にするには IsFirstRowHeader を true に設定にします。
            tFile.IsFirstRowHeader = true;
            tFile.WriteFields(results as IEnumerable<CSV209_Download>);

            return FileUploadDownloadUtility.CreateFileResponse(stream, csvname);
            }
        }
        /// <summary>
        /// Download CSV 配合レシピ 209
        /// </summary>
        /// <param name="paramaters"></param>
        /// <returns>209_配合レシピCSV</returns>
        public HttpResponseMessage GetCSV_2([FromUri]FilterCSV209 paramaters)
        {
            // 保存CSVファイル名（ユーザID_CsvDownload_現在日時.csv）
            decimal _userName = Convert.ToDecimal(UserInfo.GetUserNameFromIdentity(this.User.Identity));
            string userName = "_" + _userName.ToString() + "_";
            string filename = Properties.Resources.CSV209_DownloadDetail + userName;
            string csvname = filename + DateTime.Now.ToString("yyyyMMddHHmmss") + Properties.Resources.CsvExtension;

            using (FOODPROCSEntities context = new FOODPROCSEntities(paramaters.cd_kaisha, paramaters.cd_kojyo))
            {
                context.Configuration.ProxyCreationEnabled = false;
                //     //テーブル（ビュー）の項目のみでダウンロードする場合は以下を使用します

                //     //テーブル（ビュー）の項目＋更新区分を追加してダウンロードする場合は以下を使用します
                IEnumerable results = "";
                if (paramaters.M_kirikae == Convert.ToInt32(Properties.Resources.m_kirikae_hyoji))
                {
                    var query = "SELECT                                                                         " +
                                "      h.no_seq                 AS no_seq                                         " +
                                "    ,h.cd_haigo               AS cd_haigo                                       " +
                                "    ,h.nm_haigo               AS nm_haigo                                       " +
                                "    ,h.nm_haigo_r             AS nm_haigo_r                                     " +
                                "    ,h.kbn_hin                AS kbn_hin                                        " +
                                "    ,h.cd_bunrui              AS cd_bunrui                                      " +
                                "    ,h.budomari               AS budomari                                       " +
                                "    ,h.qty_kihon              AS qty_kihon                                      " +
                                "    ,h.ritsu_kihon            AS ritsu_kihon                                    " +
                                "    ,h.cd_setsubi             AS cd_setsubi                                     " +
                                "    ,h.flg_gasan              AS flg_gasan                                      " +
                                "    ,h.qty_max                AS qty_max                                        " +
                                "    ,h.no_han                 AS no_han                                         " +
                                "    ,h.qty_haigo_h            AS qty_haigo_h                                    " +
                                "    ,h.qty_haigo_kei          AS qty_haigo_kei                                  " +
                                "    ,h.biko                   AS biko                                           " +
                                "    ,h.no_seiho               AS no_seiho                                       " +
                                "    ,h.cd_tanto_seizo         AS cd_tanto_seizo                                 " +
                                "    ,h.dt_seizo               AS dt_seizo                                       " +
                                "    ,h.cd_tanto_hinkan        AS cd_tanto_hinkan                                " +
                                "    ,h.dt_hinkan              AS dt_hinkan                                      " +
                                "    ,h.dt_from                AS dt_from                                        " +    
                                "    ,h.dt_to                  AS dt_to                                          " +
                                "    ,h.kbn_vw                 AS kbn_vw                                         " +
                                "    ,h.hijyu                  AS hijyu                                          " +
                                "    ,h.flg_shorihin           AS flg_shorihin                                   " +
                                "    ,h.flg_hinkan             AS flg_hinkan                                     " +
                                "    ,h.flg_seizo              AS flg_seizo                                      " +
                                "    ,h.flg_sakujyo            AS flg_sakujyo                                    " +
                                "    ,h.flg_mishiyo            AS flg_mishiyo                                    " +
                                "    ,h.dt_toroku              AS dt_toroku                                      " +
                                "    ,h.dt_henko               AS dt_henko                                       " +
                                "    ,h.cd_koshin              AS cd_koshin                                      " +
                                "    ,h.kbn_shiagari           AS kbn_shiagari                                   " +
                                "    ,h.cd_haigo_seiho         AS cd_haigo_seiho                                 " +
                                "    ,h.flg_seiho_base         AS flg_seiho_base                                 " +
                                "    ,h.nm_seiho               AS nm_seiho                                       " +
                                "    ,r.no_seq                 AS no_seq_r                                       " +
                                "    ,r.cd_haigo               AS cd_haigo_r                                     " +
 								"	,r.no_han				  AS no_han_r                                       " +
								"	,r.qty_haigo_h			  AS qty_haigo_h_r                                  " +
								"	,CASE                                                                       " +    
								"		WHEN r.kbn_bunkatsu = 0 THEN ''                                         " +
								"		WHEN r.kbn_bunkatsu <> 0 THEN CAST(r.kbn_bunkatsu AS VARCHAR(10))       " +
								"		END AS kbn_bunkatsu_r                                                   " +        
								"	,r.no_kotei			      AS no_kotei_r                                     " +
								"	,r.no_tonyu				  AS no_tonyu_r	                                    " +        
								"	,r.cd_hin				  AS cd_hin_r                                       " +
								"	,r.kbn_hin				  AS kbn_hin_r                                      " +        
								"	,r.nm_hin				  AS nm_hin_r                                       " +    
								"	,r.cd_mark				  AS cd_mark_r                                      " +
								"	,r.qty					  AS qty_r                                          " +
								"	,r.qty_haigo			  AS qty_haigo_r                                    " +
								"	,r.qty_nisugata			  AS qty_nisugata_r                                 " +
								"	,r.su_nisugata			  AS su_nisugata_r                                  " +        
								"	,r.qty_kowake			  AS qty_kowake_r                                   " +
								"	,r.su_kowake			  AS su_kowake_r                                    " +
								"	,r.cd_futai				  AS cd_futai_r                                     " +
								"	,r.qty_futai			  AS qty_futai_r                                    " +    
								"	,r.hijyu				  AS hijyu_r                                        " +
								"	,r.budomari				  AS budomari_r                                     " +    
                                "    ,r.flg_sakujyo            AS flg_sakujyo_r                                  " +
                                "    ,r.flg_mishiyo            AS flg_mishiyo_r                                  " +
                                "    ,r.dt_toroku              AS dt_toroku_r                                    " +
                                "    ,r.dt_henko               AS dt_kenko_r                                     " +
                                "   ,r.cd_koshin              AS cd_koshin_r                                    " +
								"	,r.kbn_jyotai			  AS kbn_jyotai_r                                   " +
								"	,r.kbn_shitei			  AS kbn_shitei_r                                   " +
                                "FROM                                                                            " +
                                "(SELECT                                                                         " +
                                "     no_seq                                                                     " +    
                                "    ,cd_haigo                                                                   " +
                                "    ,nm_haigo                                                                   " +
                                "    ,nm_haigo_r                                                                 " +
                                "    ,kbn_hin                                                                    " +
                                "    ,cd_bunrui                                                                  " +
                                "    ,budomari                                                                   " +
                                "    ,qty_kihon                                                                  " +
                                "    ,ritsu_kihon                                                                " +
                                "    ,cd_setsubi                                                                 " +
                                "    ,flg_gasan                                                                  " +
                                "    ,qty_max                                                                    " +
                                "    ,no_han                                                                     " +
                                "    ,qty_haigo_h                                                                " +
                                "    ,qty_haigo_kei                                                              " +
                                "    ,biko                                                                       " +
                                "    ,no_seiho                                                                   " +
                                "    ,cd_tanto_seizo                                                             " +
                                "    ,dt_seizo                                                                   " +
                                "    ,cd_tanto_hinkan                                                            " +
                                "    ,dt_hinkan                                                                  " +
                                "    ,dt_from                                                                    " +
                                "    ,dt_to                                                                      " +
                                "    ,kbn_vw                                                                     " +
                                "    ,hijyu                                                                      " +
                                "    ,flg_shorihin                                                               " +
                                "    ,flg_hinkan                                                                 " +
                                "    ,flg_seizo                                                                  " +
                                "    ,flg_sakujyo                                                                " +
                                "    ,flg_mishiyo                                                                " +
                                "    ,dt_toroku                                                                  " +
                                "    ,dt_henko                                                                   " +
                                "    ,cd_koshin                                                                  " +
                                "    ,kbn_shiagari                                                               " +
                                "    ,nm_haigo_rm                                                                " +
                                "    ,cd_haigo_seiho                                                             " +
                                "    ,flg_seiho_base                                                             " +
                                "    ,nm_seiho                                                                   " +
                                "FROM ma_haigo_mei_hyoji) h                                                      " +
                                "INNER JOIN ma_haigo_recipe_hyoji r                                              " +
                                "ON h.cd_haigo = r.cd_haigo                                                      " +
                                "AND h.no_han = r.no_han                                                         " +    

                                "WHERE                                                                           " +
                                "    (@cd_haigo IS NULL OR h.cd_haigo = @cd_haigo)                             " +
                                "    AND (@no_han IS NULL OR h.no_han = @no_han)                                " +
                                "    AND (h.flg_sakujyo = @flgFalse)                                            " +
                                "    AND (r.flg_mishiyo = @flgFalse)                                            " +
                                "    AND (r.flg_sakujyo = @flgFalse)                                            " +
                                "ORDER BY r.no_kotei ASC                                                         " +
								"		,r.no_tonyu ASC                                                          " ;
                    var param = new[]{
                                        new SqlParameter("@cd_haigo",paramaters.cd_haigo),
                                        new SqlParameter("@no_han",paramaters.no_han),
                                        new SqlParameter("@flgFalse",flgFalse)
                                };
                    results = context.Database.SqlQuery<CSV209_DownloadDetail>(query, param).ToList();
                }
                else if (paramaters.M_kirikae == Convert.ToInt32(Properties.Resources.m_kirikae_foodprocs))
                {
                    var query = "SELECT                                                                         " +
                                "      h.no_seq                 AS no_seq                                         " +
                                "    ,h.cd_haigo               AS cd_haigo                                       " +
                                "    ,h.nm_haigo               AS nm_haigo                                       " +
                                "    ,h.nm_haigo_r             AS nm_haigo_r                                     " +
                                "    ,h.kbn_hin                AS kbn_hin                                        " +
                                "    ,h.cd_bunrui              AS cd_bunrui                                      " +
                                "    ,h.budomari               AS budomari                                       " +
                                "    ,h.qty_kihon              AS qty_kihon                                      " +
                                "    ,h.ritsu_kihon            AS ritsu_kihon                                    " +
                                "    ,h.cd_setsubi             AS cd_setsubi                                     " +
                                "    ,h.flg_gasan              AS flg_gasan                                      " +
                                "    ,h.qty_max                AS qty_max                                        " +
                                "    ,h.no_han                 AS no_han                                         " +
                                "    ,h.qty_haigo_h            AS qty_haigo_h                                    " +
                                "    ,h.qty_haigo_kei          AS qty_haigo_kei                                  " +
                                "    ,h.biko                   AS biko                                           " +
                                "    ,h.no_seiho               AS no_seiho                                       " +
                                "    ,h.cd_tanto_seizo         AS cd_tanto_seizo                                 " +
                                "    ,h.dt_seizo               AS dt_seizo                                       " +
                                "    ,h.cd_tanto_hinkan        AS cd_tanto_hinkan                                " +
                                "    ,h.dt_hinkan              AS dt_hinkan                                      " +
                                "    ,h.dt_from                AS dt_from                                        " +
                                "    ,h.dt_to                  AS dt_to                                          " +
                                "    ,h.kbn_vw                 AS kbn_vw                                         " +
                                "    ,h.hijyu                  AS hijyu                                          " +
                                "    ,h.flg_shorihin           AS flg_shorihin                                   " +
                                "    ,h.flg_hinkan             AS flg_hinkan                                     " +
                                "    ,h.flg_seizo              AS flg_seizo                                      " +
                                "    ,h.flg_sakujyo            AS flg_sakujyo                                    " +
                                "    ,h.flg_mishiyo            AS flg_mishiyo                                    " +
                                "    ,h.dt_toroku              AS dt_toroku                                      " +
                                "    ,h.dt_henko               AS dt_henko                                       " +
                                "    ,h.cd_koshin              AS cd_koshin                                      " +
                                "    ,h.kbn_shiagari           AS kbn_shiagari                                   " +
                                "    ,h.cd_haigo_seiho         AS cd_haigo_seiho                                 " +
                                "    ,h.flg_seiho_base         AS flg_seiho_base                                 " +
                                "    ,h.nm_seiho               AS nm_seiho                                       " +
                                "    ,r.no_seq                 AS no_seq_r                                       " +
                                "    ,r.cd_haigo               AS cd_haigo_r                                     " +
                                "	,r.no_han				  AS no_han_r                                       " +
                                "	,r.qty_haigo_h			  AS qty_haigo_h_r                                  " +
                                "	,CASE                                                                       " +
                                "		WHEN r.kbn_bunkatsu = 0 THEN ''                                         " +
                                "		WHEN r.kbn_bunkatsu <> 0 THEN CAST(r.kbn_bunkatsu AS VARCHAR(10))       " +
                                "		END AS kbn_bunkatsu_r                                                   " +
                                "	,r.no_kotei			      AS no_kotei_r                                     " +
                                "	,r.no_tonyu				  AS no_tonyu_r	                                    " +
                                "	,r.cd_hin				  AS cd_hin_r                                       " +
                                "	,r.kbn_hin				  AS kbn_hin_r                                      " +
                                "	,r.nm_hin				  AS nm_hin_r                                       " +
                                "	,r.cd_mark				  AS cd_mark_r                                      " +
                                "	,r.qty					  AS qty_r                                          " +
                                "	,r.qty_haigo			  AS qty_haigo_r                                    " +
                                "	,r.qty_nisugata			  AS qty_nisugata_r                                 " +
                                "	,r.su_nisugata			  AS su_nisugata_r                                  " +
                                "	,r.qty_kowake			  AS qty_kowake_r                                   " +
                                "	,r.su_kowake			  AS su_kowake_r                                    " +
                                "	,r.cd_futai				  AS cd_futai_r                                     " +
                                "	,r.qty_futai			  AS qty_futai_r                                    " +
                                "	,r.hijyu				  AS hijyu_r                                        " +
                                "	,r.budomari				  AS budomari_r                                     " +
                                "    ,r.flg_sakujyo            AS flg_sakujyo_r                                  " +
                                "    ,r.flg_mishiyo            AS flg_mishiyo_r                                  " +
                                "    ,r.dt_toroku              AS dt_toroku_r                                    " +
                                "    ,r.dt_henko               AS dt_kenko_r                                     " +
                                "   ,r.cd_koshin              AS cd_koshin_r                                    " +
                                "	,r.kbn_jyotai			  AS kbn_jyotai_r                                   " +
                                "	,r.kbn_shitei			  AS kbn_shitei_r                                   " +
                                "FROM                                                                            " +
                                "(SELECT                                                                         " +
                                "     no_seq                                                                     " +
                                "    ,cd_haigo                                                                   " +
                                "    ,nm_haigo                                                                   " +
                                "    ,nm_haigo_r                                                                 " +
                                "    ,kbn_hin                                                                    " +
                                "    ,cd_bunrui                                                                  " +
                                "    ,budomari                                                                   " +
                                "    ,qty_kihon                                                                  " +
                                "    ,ritsu_kihon                                                                " +
                                "    ,cd_setsubi                                                                 " +
                                "    ,flg_gasan                                                                  " +
                                "    ,qty_max                                                                    " +
                                "    ,no_han                                                                     " +
                                "    ,qty_haigo_h                                                                " +
                                "    ,qty_haigo_kei                                                              " +
                                "    ,biko                                                                       " +
                                "    ,no_seiho                                                                   " +
                                "    ,cd_tanto_seizo                                                             " +
                                "    ,dt_seizo                                                                   " +
                                "    ,cd_tanto_hinkan                                                            " +
                                "    ,dt_hinkan                                                                  " +
                                "    ,dt_from                                                                    " +
                                "    ,dt_to                                                                      " +
                                "    ,kbn_vw                                                                     " +
                                "    ,hijyu                                                                      " +
                                "    ,flg_shorihin                                                               " +
                                "    ,flg_hinkan                                                                 " +
                                "    ,flg_seizo                                                                  " +
                                "    ,flg_sakujyo                                                                " +
                                "    ,flg_mishiyo                                                                " +
                                "    ,dt_toroku                                                                  " +
                                "    ,dt_henko                                                                   " +
                                "    ,cd_koshin                                                                  " +
                                "    ,kbn_shiagari                                                               " +
                                "    ,nm_haigo_rm                                                                " +
                                "    ,cd_haigo_seiho                                                             " +
                                "    ,flg_seiho_base                                                             " +
                                "    ,nm_seiho                                                                   " +
                                "FROM ma_haigo_mei) h                                                      " +
                                "INNER JOIN ma_haigo_recipe r                                              " +
                                "ON h.cd_haigo = r.cd_haigo    AND  h.qty_kihon = h.qty_haigo_h                  " +
                                "AND h.no_han = r.no_han       AND  r.qty_haigo_h = h.qty_haigo_h                " +

                                "WHERE                                                                           " +
                                "    (@cd_haigo IS NULL OR h.cd_haigo = @cd_haigo)                             " +
                                "    AND (@no_han IS NULL OR h.no_han = @no_han)                                " +
                                "    AND (h.flg_sakujyo = @flgFalse)                                            " +
                                "    AND (r.flg_mishiyo = @flgFalse)                                            " +
                                "    AND (r.flg_sakujyo = @flgFalse)                                            " +
                                "    AND (h.qty_kihon = h.qty_haigo_h)                                           " +   
                                "ORDER BY r.no_kotei ASC                                                         " +
                                "		,r.no_tonyu ASC                                                          ";
                    var param = new[]{
                                        new SqlParameter("@cd_haigo",paramaters.cd_haigo),
                                        new SqlParameter("@no_han",paramaters.no_han),
                                        new SqlParameter("@flgFalse",flgFalse)
                                };
                    results = context.Database.SqlQuery<CSV209_DownloadDetail>(query, param).ToList();
                }

                MemoryStream stream = new MemoryStream();
                TextFieldFile<CSV209_DownloadDetail> tFile
                                    = new TextFieldFile<CSV209_DownloadDetail>(stream,
                                                                          Encoding.GetEncoding(Properties.Resources.Encoding), CSV209_DownloadDetail);
                tFile.Delimiters = new string[] { "," };
                // TODO: ヘッダーを有効にするには IsFirstRowHeader を true に設定にします。
                tFile.IsFirstRowHeader = true;
                tFile.WriteFields(results as IEnumerable<CSV209_DownloadDetail>);

                return FileUploadDownloadUtility.CreateFileResponse(stream, csvname);
            }
        }
        #endregion
    }

    #region "ControllerのAPIで利用するリクエスト・レスポンスクラスです"

    /// <summary>
    /// テーブル（ビュー）の項目＋更新区分を追加してアップロード・ダウンロードする場合に、追加する項目を定義します
    /// </summary>
    public class FilterCSV209
    {
        public int M_kirikae { get; set; }
        public string cd_haigo { get; set; }
        public int no_han { get; set; }
        public int cd_kaisha { get; set; }
        public int cd_kojyo { get; set; }
    }
    public class CSV209_Download
    {
        public int no_seq { get; set; }
        public string cd_haigo { get; set; }
        public string nm_haigo { get; set; }
        public string nm_haigo_r { get; set; }
        public string kbn_hin { get; set; }
        public string cd_bunrui { get; set; }
        public double budomari { get; set; }
        public int qty_kihon { get; set; }
        public Nullable<double> ritsu_kihon { get; set; }
        public string cd_setsubi { get; set; }
        public bool flg_gasan { get; set; }
        public Nullable<double> qty_max { get; set; }
        public int no_han { get; set; }
        public int qty_haigo_h { get; set; }
        public Nullable<double> qty_haigo_kei { get; set; }
        public string biko { get; set; }
        public string no_seiho { get; set; }
        public string cd_tanto_seizo { get; set; }
        public Nullable<System.DateTime> dt_seizo { get; set; }
        public string cd_tanto_hinkan { get; set; }
        public Nullable<System.DateTime> dt_hinkan { get; set; }
        public System.DateTime dt_from { get; set; }
        public System.DateTime dt_to { get; set; }
        public string kbn_vw { get; set; }
        public Nullable<double> hijyu { get; set; }
        public bool flg_shorihin { get; set; }
        public bool flg_hinkan { get; set; }
        public bool flg_seizo { get; set; }
        public bool flg_sakujyo { get; set; }
        public bool flg_mishiyo { get; set; }
        public System.DateTime dt_toroku { get; set; }
        public System.DateTime dt_henko { get; set; }
        public string cd_koshin { get; set; }
        public Nullable<bool> kbn_shiagari { get; set; }
        public string cd_haigo_seiho { get; set; }
        public Nullable<bool> flg_seiho_base { get; set; }
        public string nm_seiho { get; set; }
        public int no_seq_LINE { get; set; }
        public string cd_haigo_LINE { get; set; }
        public int no_yusen_LINE { get; set; }
        public string cd_line { get; set; }
        public bool flg_sakujyo_LINE { get; set; }
        public bool flg_mishiyo_LINE { get; set; }
        public System.DateTime dt_toroku_LINE { get; set; }
        public System.DateTime dt_kenko_LINE { get; set; }
        public string cd_koshin_LINE { get; set; }
    }
    public class CSV209_DownloadDetail
    {
        public int no_seq { get; set; }
        public string cd_haigo { get; set; }
        public string nm_haigo { get; set; }
        public string nm_haigo_r { get; set; }
        public string kbn_hin { get; set; }
        public string cd_bunrui { get; set; }
        public double budomari { get; set; }
        public int qty_kihon { get; set; }
        public Nullable<double> ritsu_kihon { get; set; }
        public string cd_setsubi { get; set; }
        public bool flg_gasan { get; set; }
        public Nullable<double> qty_max { get; set; }
        public int no_han { get; set; }
        public int qty_haigo_h { get; set; }
        public Nullable<double> qty_haigo_kei { get; set; }
        public string biko { get; set; }
        public string no_seiho { get; set; }
        public string cd_tanto_seizo { get; set; }
        public Nullable<System.DateTime> dt_seizo { get; set; }
        public string cd_tanto_hinkan { get; set; }
        public Nullable<System.DateTime> dt_hinkan { get; set; }
        public System.DateTime dt_from { get; set; }
        public System.DateTime dt_to { get; set; }
        public string kbn_vw { get; set; }
        public Nullable<double> hijyu { get; set; }
        public bool flg_shorihin { get; set; }
        public bool flg_hinkan { get; set; }
        public bool flg_seizo { get; set; }
        public bool flg_sakujyo { get; set; }
        public bool flg_mishiyo { get; set; }
        public System.DateTime dt_toroku { get; set; }
        public System.DateTime dt_henko { get; set; }
        public string cd_koshin { get; set; }
        public Nullable<bool> kbn_shiagari { get; set; }
        public string cd_haigo_seiho { get; set; }
        public Nullable<bool> flg_seiho_base { get; set; }
        public string nm_seiho { get; set; }
        public int no_seq_r { get; set; }
        public string cd_haigo_r { get; set; }
        public int no_han_r { get; set; }
        public int qty_haigo_h_r { get; set; }
        public string kbn_bunkatsu_r { get; set; }
        public int no_kotei_r { get; set; }
        public int no_tonyu_r { get; set; }
        public string cd_hin_r { get; set; }
        public string kbn_hin_r { get; set; }
        public string nm_hin_r { get; set; }
        public string cd_mark_r { get; set; }
        public Nullable<double> qty_r { get; set; }
        public Nullable<double> qty_haigo_r { get; set; }
        public Nullable<double> qty_nisugata_r { get; set; }
        public Nullable<int> su_nisugata_r { get; set; }
        public Nullable<double> qty_kowake_r { get; set; }
        public Nullable<int> su_kowake_r { get; set; }
        public string cd_futai_r { get; set; }
        public Nullable<double> qty_futai_r { get; set; }
        public Nullable<double> hijyu_r { get; set; }
        public Nullable<double> budomari_r { get; set; }
        public bool flg_sakujyo_r { get; set; }
        public bool flg_mishiyo_r { get; set; }
        public System.DateTime dt_toroku_r { get; set; }
        public System.DateTime dt_kenko_r { get; set; }
        public string cd_koshin_r { get; set; }
        public string kbn_jyotai_r { get; set; }
        public string kbn_shitei_r { get; set; }
    }
    #endregion

}
