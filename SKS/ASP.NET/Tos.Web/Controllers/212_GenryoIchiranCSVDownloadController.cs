using System;
using System.Collections;
using System.Collections.Generic;
using System.Data;
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
    public class _212_GenryoIchiranCSVDownloadController : ApiController
    {
        #region "CSVファイルの項目設定"
        private readonly int sort_cd_hin = 1;
        private static readonly TextFieldSetting[] CSV212_Download_Lab = new TextFieldSetting[]
        {
            new TextFieldSetting() { PropertyName = "cd_kaisha", DisplayName = "会社コード"},
            new TextFieldSetting() { PropertyName = "nm_kaisha_", DisplayName = "会社名", WrapChar = "\"" },
            new TextFieldSetting() { PropertyName = "cd_kojyo", DisplayName = "工場コード"},
            new TextFieldSetting() { PropertyName = "nm_busho_busho", DisplayName = "工場名",WrapChar = "\""},
            new TextFieldSetting() { PropertyName = "kbn_hin", DisplayName = "品区分名称"},
            new TextFieldSetting() { PropertyName = "cd_hin", DisplayName = "原料コード"},
            new TextFieldSetting() { PropertyName = "nm_hin", DisplayName = "原料名",WrapChar = "\""},
            new TextFieldSetting() { PropertyName = "nm_hin_r", DisplayName = "原料名略称",WrapChar = "\""},
            new TextFieldSetting() { PropertyName = "cd_bunrui", DisplayName = "分類コード"},
            new TextFieldSetting() { PropertyName = "nm_bunrui_bunrui", DisplayName = "分類名",WrapChar = "\""},
            new TextFieldSetting() { PropertyName = "cd_shukei", DisplayName = "集計コード"},
            new TextFieldSetting() { PropertyName = "flg_mishiyo", DisplayName = "未使用フラグ"},
            new TextFieldSetting() { PropertyName = "nisugata_hyoji", DisplayName = "荷姿表示用"},
            new TextFieldSetting() { PropertyName = "nisugata_qty", DisplayName = "荷姿内容量"},
            new TextFieldSetting() { PropertyName = "su_iri", DisplayName = "入数"},
            new TextFieldSetting() { PropertyName = "size_hachu", DisplayName = "発注ロットサイズ"},
            new TextFieldSetting() { PropertyName = "qty", DisplayName = "一個の量"},
            new TextFieldSetting() { PropertyName = "cd_tani_shiyo", DisplayName = "一個の量単位"},
            new TextFieldSetting() { PropertyName = "nm_tani_shiyo", DisplayName = "一個の量単位名称",WrapChar = "\""},
            new TextFieldSetting() { PropertyName = "hijyu", DisplayName = "比重"},
            new TextFieldSetting() { PropertyName = "budomari", DisplayName = "歩留"},
            new TextFieldSetting() { PropertyName = "kin_tanka", DisplayName = "単価"},
            new TextFieldSetting() { PropertyName = "cd_tani_nonyu", DisplayName = "納入単位コード"},
            new TextFieldSetting() { PropertyName = "nm_tani_nonyu", DisplayName = "納入単位名称"},
            new TextFieldSetting() { PropertyName = "su_leadtime", DisplayName = "納入リードタイム"},
            new TextFieldSetting() { PropertyName = "cd_maker_hin", DisplayName = "メーカーコード"},
            new TextFieldSetting() { PropertyName = "su_saitei", DisplayName = "最低在庫"},
            new TextFieldSetting() { PropertyName = "cd_kura", DisplayName = "倉場所コード"},
            new TextFieldSetting() { PropertyName = "nm_kura", DisplayName = "倉場所名",WrapChar="\""},
            new TextFieldSetting() { PropertyName = "cd_hini", DisplayName = "品位状態コード"},
            new TextFieldSetting() { PropertyName = "nm_hini", DisplayName = "品位状態名",WrapChar="\""},
            new TextFieldSetting() { PropertyName = "kbn_zei", DisplayName = "税区分コード"},
            new TextFieldSetting() { PropertyName = "nm_zei", DisplayName = "税区分名称",WrapChar="\""},
            new TextFieldSetting() { PropertyName = "kbn_jyotai", DisplayName = "状態区分コード"},
            new TextFieldSetting() { PropertyName = "nm_literal_21", DisplayName = "状態区分名称",WrapChar="\""},
            new TextFieldSetting() { PropertyName = "cd_niuke", DisplayName = "荷受場所コード"},
            new TextFieldSetting() { PropertyName = "nm_niuke", DisplayName = "荷受場所名",WrapChar="\""},
            new TextFieldSetting() { PropertyName = "kbn_ksys_denso", DisplayName = "KSYS向け加工残伝送フラグ"},
            new TextFieldSetting() { PropertyName = "kikan_shomi", DisplayName = "使用期限"},
            new TextFieldSetting() { PropertyName = "tani_shomi", DisplayName = "使用期限単位"},
            new TextFieldSetting() { PropertyName = "nm_literal_23_shomi", DisplayName = "使用期限単位名称"},
            new TextFieldSetting() { PropertyName = "kikan_shomi_kaifu", DisplayName = "開封後使用期限"},
            new TextFieldSetting() { PropertyName = "tani_shomi_kaifu", DisplayName = "開封後使用期限単位"},
            new TextFieldSetting() { PropertyName = "nm_literal_23_kaifu", DisplayName = "開封後使用期限単位名称"},
            new TextFieldSetting() { PropertyName = "biko", DisplayName = "備考",WrapChar="\""},
            new TextFieldSetting() { PropertyName = "no_kikaku", DisplayName = "規格書No."},
            new TextFieldSetting() { PropertyName = "dt_toroku_kikaku", DisplayName = "規格書登録日",Format="yyyy/MM/dd HH:mm:ss"},
            new TextFieldSetting() { PropertyName = "nm_kikaku", DisplayName = "規格書商品名",WrapChar="\""},
            new TextFieldSetting() { PropertyName = "nm_bussitsu", DisplayName = "物質名",WrapChar="\""},
            new TextFieldSetting() { PropertyName = "cd_hini_gmat", DisplayName = "品位コード"},
            new TextFieldSetting() { PropertyName = "nm_literal_22", DisplayName = "品位名称",WrapChar="\""},
            new TextFieldSetting() { PropertyName = "nm_hanbai", DisplayName = "販売会社",WrapChar="\""},
            new TextFieldSetting() { PropertyName = "nm_seizo", DisplayName = "製造会社",WrapChar="\""},
            new TextFieldSetting() { PropertyName = "cd_doto_hin", DisplayName = "同等品コード"},
            new TextFieldSetting() { PropertyName = "nm_hin_hin", DisplayName = "同等品名",WrapChar="\""},
            new TextFieldSetting() { PropertyName = "cd_doto_kaisha", DisplayName = "同等品会社コード"},
            new TextFieldSetting() { PropertyName = "nm_kaisha_doto", DisplayName = "同等品会社名",WrapChar="\""},
            new TextFieldSetting() { PropertyName = "dt_doto_SET_DATE", DisplayName = "同等品コード設定日",Format="yyyy/MM/dd HH:mm:ss"},
            new TextFieldSetting() { PropertyName = "kbn_haishi", DisplayName = "廃止区分",WrapChar="\""},
            new TextFieldSetting() { PropertyName = "dt_toroku", DisplayName = "登録日",Format="yyyy/MM/dd HH:mm:ss"},
            new TextFieldSetting() { PropertyName = "dt_henko", DisplayName = "更新日",Format="yyyy/MM/dd HH:mm:ss"},
            new TextFieldSetting() { PropertyName = "cd_koshin", DisplayName = "更新者コード",WrapChar="\""},
            new TextFieldSetting() { PropertyName = "nm_user", DisplayName = "更新社名",WrapChar="\""},
        };
        private static readonly TextFieldSetting[] CSV212_Download_Factory = new TextFieldSetting[]
        {
            new TextFieldSetting() { PropertyName = "kbn_hin", DisplayName = "品区分名称",WrapChar="\""},
            new TextFieldSetting() { PropertyName = "cd_hin", DisplayName = "原料コード",WrapChar="\""},
            new TextFieldSetting() { PropertyName = "nm_hin", DisplayName = "原料名",WrapChar="\""},
            new TextFieldSetting() { PropertyName = "nm_hin_r", DisplayName = "原料名略称",WrapChar="\""},
            new TextFieldSetting() { PropertyName = "nisugata_qty", DisplayName = "荷姿内容量"},
            new TextFieldSetting() { PropertyName = "nisugata_hyoji", DisplayName = "荷姿表示用",WrapChar="\""},
            new TextFieldSetting() { PropertyName = "su_iri", DisplayName = "入数"},
            new TextFieldSetting() { PropertyName = "qty", DisplayName = "一個の量"},
            new TextFieldSetting() { PropertyName = "cd_tani_shiyo", DisplayName = "一個の量単位",WrapChar="\""},
            new TextFieldSetting() { PropertyName = "nm_tani_TANI", DisplayName = "一個の量単位名称",WrapChar="\""},
            new TextFieldSetting() { PropertyName = "cd_tani_nonyu", DisplayName = "納入単位コード",WrapChar="\""},
            new TextFieldSetting() { PropertyName = "nm_tani_NONYU", DisplayName = "納入単位名称",WrapChar="\""},
            new TextFieldSetting() { PropertyName = "cd_shukei", DisplayName = "集計コード",WrapChar="\""},
            new TextFieldSetting() { PropertyName = "budomari", DisplayName = "歩留"},
            new TextFieldSetting() { PropertyName = "hijyu", DisplayName = "比重"},
            new TextFieldSetting() { PropertyName = "cd_bunrui", DisplayName = "分類コード",WrapChar="\""},
            new TextFieldSetting() { PropertyName = "nm_bunrui_BUNRUI", DisplayName = "分類名",WrapChar="\""},
            new TextFieldSetting() { PropertyName = "size_hachu", DisplayName = "発注ロットサイズ"},
            new TextFieldSetting() { PropertyName = "su_leadtime", DisplayName = "納入リードタイム"},
            new TextFieldSetting() { PropertyName = "su_saitei", DisplayName = "最低在庫"},
            new TextFieldSetting() { PropertyName = "cd_kura", DisplayName = "倉場所コード",WrapChar="\""},
            new TextFieldSetting() { PropertyName = "nm_kura_KURA", DisplayName = "倉場所名",WrapChar="\""},
            new TextFieldSetting() { PropertyName = "kin_tanka", DisplayName = "単価"},
            new TextFieldSetting() { PropertyName = "kbn_zei", DisplayName = "税区分コード",WrapChar="\""},
            new TextFieldSetting() { PropertyName = "nm_zei_ZEI", DisplayName = "税区分名称",WrapChar="\""},
            new TextFieldSetting() { PropertyName = "cd_hini", DisplayName = "品位状態コード",WrapChar="\""},
            new TextFieldSetting() { PropertyName = "nm_hini_HINI", DisplayName = "品位状態名",WrapChar="\""},
            new TextFieldSetting() { PropertyName = "kbn_jyotai", DisplayName = "状態区分コード",WrapChar="\""},
            new TextFieldSetting() { PropertyName = "nm_kino_21", DisplayName = "状態区分名称",WrapChar="\""},
            new TextFieldSetting() { PropertyName = "biko", DisplayName = "備考",WrapChar="\""},
            new TextFieldSetting() { PropertyName = "cd_niuke", DisplayName = "荷受場所コード",WrapChar="\""},
            new TextFieldSetting() { PropertyName = "nm_niuke_NIUKE", DisplayName = "荷受場所名",WrapChar="\""},
            new TextFieldSetting() { PropertyName = "cd_maker_hin", DisplayName = "メーカーコード",WrapChar="\""},
            new TextFieldSetting() { PropertyName = "kikan_shomi", DisplayName = "使用期限"},
            new TextFieldSetting() { PropertyName = "tani_shomi", DisplayName = "使用期限単位",WrapChar="\""},
            new TextFieldSetting() { PropertyName = "nm_kino_23_shomi", DisplayName = "使用期限単位名称",WrapChar="\""},
            new TextFieldSetting() { PropertyName = "kikan_shomi_kaifu", DisplayName = "開封後使用期限",WrapChar="\""},
            new TextFieldSetting() { PropertyName = "tani_shomi_kaifu", DisplayName = "開封後使用期限単位",WrapChar="\""},
            new TextFieldSetting() { PropertyName = "nm_kino_23_kaifu", DisplayName = "開封後使用期限単位名称",WrapChar="\""},
            new TextFieldSetting() { PropertyName = "kbn_ksys_denso", DisplayName = "KSYS向け加工残伝送フラグ",WrapChar="\""},
            new TextFieldSetting() { PropertyName = "flg_mishiyo", DisplayName = "未使用フラグ"},
            new TextFieldSetting() { PropertyName = "dt_toroku", DisplayName = "登録日",Format="yyyy/MM/dd HH:mm:ss"},
            new TextFieldSetting() { PropertyName = "dt_henko", DisplayName = "更新日",Format="yyyy/MM/dd HH:mm:ss"},
            new TextFieldSetting() { PropertyName = "cd_koshin", DisplayName = "更新者コード",WrapChar="\""},
            new TextFieldSetting() { PropertyName = "nm_tanto_TANTO", DisplayName = "更新者名",WrapChar="\""},
            new TextFieldSetting() { PropertyName = "no_kikaku", DisplayName = "規格書No.",WrapChar="\""},
            new TextFieldSetting() { PropertyName = "nm_kikaku", DisplayName = "規格書商品名",WrapChar="\""},
            new TextFieldSetting() { PropertyName = "cd_hini_gmat", DisplayName = "品位コード",WrapChar="\""},
            new TextFieldSetting() { PropertyName = "nm_kino_22", DisplayName = "品位名称",WrapChar="\""},
            new TextFieldSetting() { PropertyName = "nm_seizo", DisplayName = "製造会社",WrapChar="\""},
            new TextFieldSetting() { PropertyName = "nm_hanbai", DisplayName = "販売会社",WrapChar="\""},
            new TextFieldSetting() { PropertyName = "dt_toroku_kikaku", DisplayName = "規格書登録日",Format="yyyy/MM/dd HH:mm:ss"},
            new TextFieldSetting() { PropertyName = "nm_bussitsu", DisplayName = "物質名",WrapChar="\""},
            new TextFieldSetting() { PropertyName = "cd_doto_hin", DisplayName = "同等品コード",WrapChar="\""},
            new TextFieldSetting() { PropertyName = "cd_doto_kaisha", DisplayName = "同等品会社コード",WrapChar="\""},
            new TextFieldSetting() { PropertyName = "dt_doto_SET_DATE", DisplayName = "同等品コード設定日"},

        };
        #endregion

        #region "Controllerで公開するAPI"

        /// <summary>
        /// Download CSV 212
        /// </summary>
        /// <param name="options"></param>
        /// <returns>List</returns>
        public HttpResponseMessage Get([FromUri] Filter_212 param)
        {
            // 保存CSVファイル名（ユーザID_CsvDownload_現在日時.csv）
            decimal _userName = Convert.ToDecimal(UserInfo.GetUserNameFromIdentity(this.User.Identity));
            string userName = "_" + _userName.ToString() + "_";
            string filename = Properties.Resources.CSV212_Download + userName;
            string csvname = filename + DateTime.Now.ToString("yyyyMMddHHmmss") + Properties.Resources.CsvExtension;
            if (param.Kbn_bumon == 1)
            {
                using (ShohinKaihatsuEntities context = new ShohinKaihatsuEntities())
                {
                    //テーブル（ビュー）の項目のみでダウンロードする場合は以下を使用します
                    //テーブル（ビュー）の項目＋更新区分を追加してダウンロードする場合は以下を使用します
                    param.zero = Convert.ToInt16(Properties.Resources.cd_jyotai_0);
                    param.one = Convert.ToInt16(Properties.Resources.cd_jyotai_1);
                    param.two = Convert.ToInt16(Properties.Resources.cd_jyotai_2);
                    var results = context.sp_shohinkaihatsu_download_lab_212(
                                                                          param.cd_kaisha
                                                                        , param.cd_bunrui
                                                                        , param.cd_hin
                                                                        , param.nm_hin
                                                                        , param.nm_bussitsu
                                                                        , param.no_kikaku1
                                                                        , param.no_kikaku2
                                                                        , param.no_kikaku3
                                                                        , param.nm_kikaku
                                                                        , param.nm_hanbai
                                                                        , param.nm_seizo
                                                                        , param.cd_kojyo
                                                                        , param.zero
                                                                        , param.one
                                                                        , param.two
                                                                        , param.kbn_hin
                                                                        , param.sort
                                                                        , param.check_left
                                                                        , param.check_right1
                                                                        , param.check_right2
                                                                        , param.check_right3
                                                                        ).ToList();
                    MemoryStream stream = new MemoryStream();
                    TextFieldFile<sp_shohinkaihatsu_download_lab_212_Result> tFile
                                        = new TextFieldFile<sp_shohinkaihatsu_download_lab_212_Result>(stream,
                                                                              Encoding.GetEncoding(Properties.Resources.Encoding), CSV212_Download_Lab);
                    tFile.Delimiters = new string[] { "," };
                    // TODO: ヘッダーを有効にするには IsFirstRowHeader を true に設定にします。
                    tFile.IsFirstRowHeader = true;
                    tFile.WriteFields(results as IEnumerable<sp_shohinkaihatsu_download_lab_212_Result>);

                    return FileUploadDownloadUtility.CreateFileResponse(stream, csvname);
                }
            }
            else {
                var sqlparams = new object[] {
                        new SqlParameter("@cd_bunrui",SqlDbType.VarChar,2) { Value = (object)param.cd_bunrui_str ?? DBNull.Value},
                        new SqlParameter("@cd_hin",SqlDbType.VarChar,13){ Value = (object)param.cd_hin_str ?? DBNull.Value},
                        new SqlParameter("@nm_hin",SqlDbType.VarChar,60){ Value = (object)param.nm_hin ?? DBNull.Value},
                        new SqlParameter("@nm_bussitsu",SqlDbType.VarChar,74){ Value = (object)param.nm_bussitsu ?? DBNull.Value},
                        new SqlParameter("@no_kikaku1",SqlDbType.VarChar,17){ Value = (object)param.no_kikaku1 ?? DBNull.Value},
                        new SqlParameter("@no_kikaku2",SqlDbType.VarChar,17){ Value = (object)param.no_kikaku2 ?? DBNull.Value},
                        new SqlParameter("@no_kikaku3",SqlDbType.VarChar,17){ Value = (object)param.no_kikaku3 ?? DBNull.Value},
                        new SqlParameter("@nm_kikaku",SqlDbType.VarChar,80){ Value = (object)param.nm_kikaku ?? DBNull.Value},
                        new SqlParameter("@nm_hanbai",SqlDbType.VarChar,80){ Value = (object)param.nm_hanbai ?? DBNull.Value},
                        new SqlParameter("@nm_seizo",SqlDbType.VarChar,80){ Value = (object)param.nm_seizo ?? DBNull.Value},
                        new SqlParameter("@kbn_hin",SqlDbType.VarChar,1){ Value = (object)param.kbn_hin ?? DBNull.Value},
                        new SqlParameter("@sort",SqlDbType.Int){ Value = param.sort},
                      };
                using (FOODPROCSEntities context = new FOODPROCSEntities(param.cd_kaisha, (int)param.cd_kojyo))
                {
                    var query = "WITH CTE AS( " +
                                "SELECT " +
                                    "CASE " +
                                    "WHEN GENSHIZAI.kbn_hin = '1' THEN '原料' " +
                                    "WHEN GENSHIZAI.kbn_hin = '6' THEN '自家原料' " +
                                    "ELSE '''' " +
                                    "END AS kbn_hin " +
                                    ",GENSHIZAI.cd_hin " +
                                    ",GENSHIZAI.nm_hin " +
                                    ",GENSHIZAI.nm_hin_r " +
                                    ",GENSHIZAI.nisugata_qty " +
                                    ",GENSHIZAI.nisugata_hyoji " +
                                    ",GENSHIZAI.su_iri " +
                                    ",GENSHIZAI.qty " +
                                    ",GENSHIZAI.cd_tani_shiyo " +
                                    ",TANI_SHIYO.nm_tani AS nm_tani_TANI " +
                                    ",GENSHIZAI.cd_tani_nonyu " +
                                    ",TANI_NONYU.nm_tani AS nm_tani_NONYU " +
                                    ",GENSHIZAI.cd_shukei " +
                                    ",GENSHIZAI.budomari " +
                                    ",GENSHIZAI.hijyu " +
                                    ",GENSHIZAI.cd_bunrui " +
                                    ",BUNRUI.nm_bunrui AS nm_bunrui_BUNRUI " +
                                    ",GENSHIZAI.size_hachu " +
                                    ",GENSHIZAI.su_leadtime " +
                                    ",GENSHIZAI.su_saitei " +
                                    ",GENSHIZAI.cd_kura " +
                                    ",KURA.nm_kura AS nm_kura_KURA " +
                                    ",GENSHIZAI.kin_tanka " +
                                    ",GENSHIZAI.kbn_zei " +
                                    ",ZEI.nm_zei AS nm_zei_ZEI " +
                                    ",GENSHIZAI.cd_hini " +
                                    ",HINI.nm_hini AS nm_hini_HINI " +
                                    ",GENSHIZAI.kbn_jyotai " +
                                    ",SS_21.nm_kino AS nm_kino_21 " +
                                    ",GENSHIZAI.biko " +
                                    ",GENSHIZAI.cd_niuke " +
                                    ",NIUKE.nm_niuke AS nm_niuke_NIUKE " +
                                    ",GENSHIZAI.cd_maker_hin " +
                                    ",GENSHIZAI.kikan_shomi " +
                                    ",GENSHIZAI.tani_shomi " +
                                    ",SS_23_shomi.nm_kino AS nm_kino_23_shomi " +
                                    ",GENSHIZAI.kikan_shomi_kaifu " +
                                    ",GENSHIZAI.tani_shomi_kaifu " +
                                    ",SS_23_kaifu.nm_kino AS nm_kino_23_kaifu " +
                                    ",GENSHIZAI.kbn_ksys_denso " +
                                    ",GENSHIZAI.flg_mishiyo " +
                                    ",GENSHIZAI.dt_toroku " +
                                    ",GENSHIZAI.dt_henko " +
                                    ",GENSHIZAI.cd_koshin " +
                                    ",TANTO.nm_tanto AS nm_tanto_TANTO " +
                                    ",GENSHIZAI.no_kikaku " +
                                    ",GENSHIZAI.nm_kikaku " +
                                    ",GENSHIZAI.cd_hini_gmat " +
                                    ",SS_22.nm_kino AS nm_kino_22 " +
                                    ",GENSHIZAI.nm_seizo " +
                                    ",GENSHIZAI.nm_hanbai " +
                                    ",GENSHIZAI.dt_toroku_kikaku " +
                                    ",GENSHIZAI.nm_bussitsu " +
                                    ",GENSHIZAI.cd_doto_hin " +
                                    ",GENSHIZAI.cd_doto_kaisha " +
                                    ",GENSHIZAI.dt_doto_SET_DATE " +
                            "FROM ma_genshizai GENSHIZAI " +

                            "LEFT JOIN ma_tani TANI_NONYU " +
                            "ON GENSHIZAI.cd_tani_nonyu = TANI_NONYU.cd_tani " +
                            "AND TANI_NONYU.flg_mishiyo = 0 " +

                            "LEFT JOIN ma_tani TANI_SHIYO " +
                            "ON GENSHIZAI.cd_tani_shiyo = TANI_SHIYO.cd_tani " +
                            "AND TANI_SHIYO.flg_mishiyo = 0 " +

                            "LEFT JOIN ma_kura KURA " +
                            "ON GENSHIZAI.cd_kura = KURA.cd_kura " +
                            "AND KURA.flg_mishiyo = 0 AND KURA.flg_sakujyo = 0 " +

                            "LEFT JOIN ma_bunrui BUNRUI " +
                            "ON GENSHIZAI.kbn_hin = BUNRUI.kbn_hin " +
                            "AND GENSHIZAI.cd_bunrui = BUNRUI.cd_bunrui " +
                            "AND BUNRUI.flg_mishiyo = 0 AND BUNRUI.flg_sakujyo = 0 " +

                            "LEFT JOIN ma_niuke NIUKE " +
                            "ON GENSHIZAI.cd_niuke = NIUKE.cd_niuke " +
                            "AND NIUKE.flg_mishiyo = 0 AND NIUKE.flg_sakujyo = 0 " +

                            "LEFT JOIN ma_zei ZEI " +
                            "ON GENSHIZAI.kbn_zei = ZEI.kbn_zei " +

                            "LEFT JOIN ma_hini HINI " +
                            "ON GENSHIZAI.cd_hini = HINI.cd_hini " +
                            "AND HINI.flg_mishiyo = 0 AND HINI.flg_sakujyo = 0 " +

                            "LEFT JOIN SS_ma_name SS_21 " +
                            "ON GENSHIZAI.kbn_jyotai = SS_21.cd_code " +
                            "AND SS_21.cd_bunrui = 21 " +

                            "LEFT JOIN  SS_ma_name SS_22 " +
                            "ON GENSHIZAI.cd_hini_gmat = SS_22.cd_code " +
                            "AND SS_22.cd_bunrui = 22 " +

                            "LEFT JOIN SS_ma_name SS_23_shomi " +
                            "ON GENSHIZAI.tani_shomi = SS_23_shomi.cd_code " +
                            "AND SS_23_shomi.cd_bunrui = 23 " +

                            "LEFT JOIN SS_ma_name SS_23_kaifu " +
                            "ON GENSHIZAI.tani_shomi_kaifu = SS_23_kaifu.cd_code " +
                            "AND SS_23_kaifu.cd_bunrui = 23 " +

                            "LEFT JOIN ma_tanto TANTO " +
                            "ON GENSHIZAI.cd_koshin = TANTO.cd_tanto " +
                            "AND TANTO.flg_mishiyo = 0 AND TANTO.flg_sakujyo = 0 ";
                if (param.check_right3 == true) {
                   query += "INNER JOIN SS_vw_genryo_tokushu TOKUSHU " +
                            "ON GENSHIZAI.cd_hin = TOKUSHU.cd_hin ";
                }

               query +=     "WHERE " +
                                "GENSHIZAI.flg_sakujyo = 0 " +
                                "AND (@cd_bunrui IS NULL OR GENSHIZAI.cd_bunrui = @cd_bunrui) ";
                    if (param.check_left == false) {
                        query += "AND (@cd_hin IS NULL OR GENSHIZAI.cd_hin = @cd_hin) ";
                    }
                                
               query +=         "AND (@nm_hin IS NULL OR ((GENSHIZAI.nm_hin LIKE ('%!'  +@nm_hin+  '%') ESCAPE '!') " +
                                                            "OR CAST(GENSHIZAI.cd_hin AS VARCHAR(10)) LIKE ('%!' + CAST(@cd_hin AS VARCHAR(10))+  '%') ESCAPE '!')) " +
                                "AND (@nm_bussitsu IS NULL OR (GENSHIZAI.nm_bussitsu LIKE ('%!'+@nm_bussitsu+'%') ESCAPE '!') ) " +
                                "AND (@no_kikaku1 IS NULL OR SUBSTRING(GENSHIZAI.no_kikaku,1,4) = @no_kikaku1) " +
                                "AND (@no_kikaku2 IS NULL OR SUBSTRING(GENSHIZAI.no_kikaku,6,6) = @no_kikaku2) " +
                                "AND (@no_kikaku3 IS NULL OR SUBSTRING(GENSHIZAI.no_kikaku,13,5) = @no_kikaku3) " +
                                "AND (@nm_kikaku IS NULL OR (GENSHIZAI.nm_kikaku LIKE ('%!'+@nm_kikaku+'%') ESCAPE '!') ) " +
                                "AND (@nm_hanbai IS NULL OR (GENSHIZAI.nm_hanbai LIKE ('%!'+@nm_hanbai+'%') ESCAPE '!') ) " +
                                "AND (@nm_seizo IS NULL OR (GENSHIZAI.nm_seizo LIKE ('%!'+@nm_seizo+'%') ESCAPE '!' ) ) " +
                                "AND GENSHIZAI.kbn_hin = '1' " +
                                "AND GENSHIZAI.cd_hin <> '000000' " +
                                "AND GENSHIZAI.cd_hin <> '00000000000' ";
                    if (param.check_right2 == false) {
                       query += "AND GENSHIZAI.flg_mishiyo = 0 ";
                    }
                    if (param.check_left == true) {
                        query += "AND ( GENSHIZAI.cd_doto_hin = ( SELECT " +
                                 "                                   cd_doto_hin" +
                                 "                                 FROM ma_genshizai " +
                                 "                                  WHERE cd_hin = @cd_hin ) " +
                                 "                OR GENSHIZAI.cd_doto_hin = @cd_hin " +
                                 "                OR GENSHIZAI.cd_hin = ( SELECT " +
                                 "                                            cd_doto_hin " +
                                 "                                          FROM ma_genshizai" +
                                 "                                          WHERE cd_hin = @cd_hin)) " +
                                 "        AND (GENSHIZAI.cd_doto_kaisha = (SELECT " +
                                 "                                            cd_doto_kaisha " +
                                 "                                         FROM ma_genshizai" +
                                 "                                         WHERE cd_hin = @cd_hin) " +
                                 "                OR GENSHIZAI.cd_hin = @cd_hin)";
                    }

                    query += ") " +
                                 "SELECT " +
                                     "CTE.kbn_hin " +
                                     ",CTE.cd_hin " +
                                     ",CTE.nm_hin " +
                                     ",CTE.nm_hin_r " +
                                     ",CTE.nisugata_qty " +
                                     ",CTE.nisugata_hyoji " +
                                     ",CTE.su_iri " +
                                     ",CTE.qty " +
                                     ",CTE.cd_tani_shiyo " +
                                     ",CTE.nm_tani_TANI " +
                                     ",CTE.cd_tani_nonyu " +
                                     ",CTE.nm_tani_NONYU " +
                                     ",CTE.cd_shukei " +
                                     ",CTE.budomari " +
                                     ",CTE.hijyu " +
                                     ",CTE.cd_bunrui " +
                                     ",CTE.nm_bunrui_BUNRUI " +
                                     ",CTE.size_hachu " +
                                     ",CTE.su_leadtime " +
                                     ",CTE.su_saitei " +
                                     ",CTE.cd_kura " +
                                     ",CTE.nm_kura_KURA " +
                                     ",CTE.kin_tanka " +
                                     ",CTE.kbn_zei " +
                                     ",CTE.nm_zei_ZEI " +
                                     ",CTE.cd_hini " +
                                     ",CTE.nm_hini_HINI " +
                                     ",CTE.kbn_jyotai " +
                                     ",CTE.nm_kino_21 " +
                                     ",CTE.biko " +
                                     ",CTE.cd_niuke " +
                                     ",CTE.nm_niuke_NIUKE " +
                                     ",CTE.cd_maker_hin " +
                                     ",CTE.kikan_shomi " +
                                     ",CTE.tani_shomi " +
                                     ",CTE.nm_kino_23_shomi " +
                                     ",CTE.kikan_shomi_kaifu " +
                                     ",CTE.tani_shomi_kaifu " +
                                     ",CTE.nm_kino_23_kaifu " +
                                     ",CTE.kbn_ksys_denso " +
                                     ",CAST(CTE.flg_mishiyo AS INT) AS flg_mishiyo " +
                                     ",CTE.dt_toroku " +
                                     ",CTE.dt_henko " +
                                     ",CTE.cd_koshin " +
                                     ",CTE.nm_tanto_TANTO " +
                                     ",CTE.no_kikaku " +
                                     ",CTE.nm_kikaku " +
                                     ",CTE.cd_hini_gmat " +
                                     ",CTE.nm_kino_22 " +
                                     ",CTE.nm_seizo " +
                                     ",CTE.nm_hanbai " +
                                     ",CTE.dt_toroku_kikaku " +
                                     ",CTE.nm_bussitsu " +
                                     ",CTE.cd_doto_hin " +
                                     ",CTE.cd_doto_kaisha " +
                                     ",CTE.dt_doto_SET_DATE  " +
                                 "FROM " +
                                     "CTE " +
                                 "ORDER BY ";
                 if (param.sort == sort_cd_hin)
                {
                    query += "  CTE.cd_hin ASC,CTE.nm_hin ASC ";
                }
                else
                {
                    query += "  CTE.nm_hin ASC,CTE.cd_hin ASC ";
                }
                                //"CASE @sort WHEN 1 THEN CTE.cd_hin END ASC "+
                                //",CASE @sort WHEN 2 THEN CTE.nm_hin END ASC ";
               var results = context.Database.SqlQuery<ResultGenryo_CSV_factory>(query, sqlparams).ToList();
                    MemoryStream stream = new MemoryStream();
                    TextFieldFile<ResultGenryo_CSV_factory> tFile
                                        = new TextFieldFile<ResultGenryo_CSV_factory>(stream,
                                                                              Encoding.GetEncoding(Properties.Resources.Encoding), CSV212_Download_Factory);
                    tFile.Delimiters = new string[] { "," };
                    // TODO: ヘッダーを有効にするには IsFirstRowHeader を true に設定にします。
                    tFile.IsFirstRowHeader = true;
                    tFile.WriteFields(results as IEnumerable<ResultGenryo_CSV_factory>);

                    return FileUploadDownloadUtility.CreateFileResponse(stream, csvname);
                }
                
            }
        }
        #endregion
    }

    #region "ControllerのAPIで利用するリクエスト・レスポンスクラスです"

    /// <summary>
    /// テーブル（ビュー）の項目＋更新区分を追加してアップロード・ダウンロードする場合に、追加する項目を定義します
    /// </summary>
    public class Filter_212
    {
        public short? cd_bunrui { get; set; }
        public int? cd_hin { get; set; }
        public string cd_hin_str { get; set; }
        public string cd_bunrui_str { get; set; }
        public short cd_kaisha { get; set; }
        public short? cd_kojyo { get; set; }
        public bool check_left { get; set; }
        public bool check_right1 { get; set; }
        public bool check_right2 { get; set; }
        public bool check_right3 { get; set; }
        public int Kbn_bumon { get; set; }
        public byte? kbn_hin { get; set; }
        public string nm_bussitsu { get; set; }
        public string nm_hanbai { get; set; }
        public string nm_hin { get; set; }
        public string nm_kikaku { get; set; }
        public string nm_seizo { get; set; }
        public string no_kikaku1 { get; set; }
        public string no_kikaku2 { get; set; }
        public string no_kikaku3 { get; set; }
        public int sort { get; set; }
        public int top { get; set; }
        public int skip { get; set; }
        public short zero { get; set; }
        public short one { get; set; }
        public short two { get; set; }
    }
    public class ResultGenryo_CSV_lab
    {
        public short cd_kaisha { get; set; }
        public short cd_kojyo { get; set; }
        public string kbn_hin { get; set; }
        public decimal cd_hin { get; set; }
        public string nm_hin { get; set; }
        public string nm_hin_r { get; set; }
        public Nullable<short> cd_bunrui { get; set; }
        public string cd_shukei { get; set; }
        public Nullable<bool> flg_mishiyo { get; set; }
        public string nisugata_hyoji { get; set; }
        public Nullable<double> nisugata_qty { get; set; }
        public Nullable<int> su_iri { get; set; }
        public Nullable<int> size_hachu { get; set; }
        public Nullable<double> qty { get; set; }
        public Nullable<byte> cd_tani_shiyo { get; set; }
        public double hijyu { get; set; }
        public Nullable<double> budomari { get; set; }
        public Nullable<double> kin_tanka { get; set; }
        public Nullable<byte> cd_tani_nonyu { get; set; }
        public Nullable<int> su_leadtime { get; set; }
        public string cd_maker_hin { get; set; }
        public Nullable<double> su_saitei { get; set; }
        public string cd_kura { get; set; }
        public string nm_kura { get; set; }
        public Nullable<byte> cd_hini { get; set; }
        public string nm_hini { get; set; }
        public Nullable<byte> kbn_zei { get; set; }
        public string kbn_jyotai { get; set; }
        public string cd_niuke { get; set; }
        public string nm_niuke { get; set; }
        public string kbn_ksys_denso { get; set; }
        public Nullable<int> kikan_shomi { get; set; }
        public string tani_shomi { get; set; }
        public Nullable<int> kikan_shomi_kaifu { get; set; }
        public string tani_shomi_kaifu { get; set; }
        public string biko { get; set; }
        public string no_kikaku { get; set; }
        public Nullable<System.DateTime> dt_toroku_kikaku { get; set; }
        public string nm_kikaku { get; set; }
        public string nm_bussitsu { get; set; }
        public Nullable<byte> cd_hini_gmat { get; set; }
        public string nm_hanbai { get; set; }
        public string nm_seizo { get; set; }
        public Nullable<decimal> cd_doto_hin { get; set; }
        public string cd_doto_kaisha { get; set; }
        public Nullable<System.DateTime> dt_doto_SET_DATE { get; set; }
        public Nullable<byte> kbn_haishi { get; set; }
        public Nullable<System.DateTime> dt_toroku { get; set; }
        public Nullable<System.DateTime> dt_henko { get; set; }
        public string cd_koshin { get; set; }
        public string nm_literal_21 { get; set; }
        public string nm_literal_22 { get; set; }
        public string nm_literal_23_shomi { get; set; }
        public string nm_literal_23_kaifu { get; set; }
        public string nm_zei { get; set; }
        public string nm_bunrui_bunrui { get; set; }
        public string nm_user { get; set; }
        public string nm_kaisha_ { get; set; }
        public string nm_kaisha_doto { get; set; }
        public string nm_tani_nonyu { get; set; }
        public string nm_tani_shiyo { get; set; }
        public string nm_hin_hin { get; set; }
        public string nm_busho_busho { get; set; }
    }
    public class ResultGenryo_CSV_factory
    {
        public string kbn_hin { get; set; }
        public string cd_hin { get; set; }
        public string nm_hin { get; set; }
        public string nm_hin_r { get; set; }
        public Nullable<double> nisugata_qty { get; set; }
        public string nisugata_hyoji { get; set; }
        public Nullable<int> su_iri { get; set; }
        public Nullable<double> qty { get; set; }
        public string cd_tani_shiyo { get; set; }
        public string nm_tani_TANI { get; set; }
        public string cd_tani_nonyu { get; set; }
        public string nm_tani_NONYU { get; set; }
        public string cd_shukei { get; set; }
        public Nullable<double> budomari { get; set; }
        public double hijyu { get; set; }
        public string cd_bunrui { get; set; }
        public string nm_bunrui_BUNRUI { get; set; }
        public Nullable<double> size_hachu { get; set; }
        public int su_leadtime { get; set; }
        public double su_saitei { get; set; }
        public string cd_kura { get; set; }
        public string nm_kura_KURA { get; set; }
        public Nullable<double> kin_tanka { get; set; }
        public string kbn_zei { get; set; }
        public string nm_zei_ZEI { get; set; }
        public string cd_hini { get; set; }
        public string nm_hini_HINI { get; set; }
        public string kbn_jyotai { get; set; }
        public string nm_kino_21 { get; set; }
        public string biko { get; set; }
        public string cd_niuke { get; set; }
        public string nm_niuke_NIUKE { get; set; }
        public string cd_maker_hin { get; set; }
        public Nullable<int> kikan_shomi { get; set; }
        public string tani_shomi { get; set; }
        public string nm_kino_23_shomi { get; set; }
        public Nullable<int> kikan_shomi_kaifu { get; set; }
        public string tani_shomi_kaifu { get; set; }
        public string nm_kino_23_kaifu { get; set; }
        public string kbn_ksys_denso { get; set; }
        public Nullable<int> flg_mishiyo { get; set; }
        public Nullable<System.DateTime> dt_toroku { get; set; }
        public Nullable<System.DateTime> dt_henko { get; set; }
        public string cd_koshin { get; set; }
        public string nm_tanto_TANTO { get; set; }
        public string no_kikaku { get; set; }
        public string nm_kikaku { get; set; }
        public string cd_hini_gmat { get; set; }
        public string nm_kino_22 { get; set; }
        public string nm_seizo { get; set; }
        public string nm_hanbai { get; set; }
        public Nullable<System.DateTime> dt_toroku_kikaku { get; set; }
        public string nm_bussitsu { get; set; }
        public string cd_doto_hin { get; set; }
        public string cd_doto_kaisha { get; set; }
        public Nullable<System.DateTime> dt_doto_SET_DATE { get; set; }
    }
    #endregion

}
