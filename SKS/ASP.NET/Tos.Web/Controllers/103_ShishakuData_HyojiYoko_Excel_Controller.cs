using DocumentFormat.OpenXml;
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
using System.Web.Http.Hosting;
using System.Web.Http.OData.Query;
using Tos.Web.Controllers.Helpers;
using Tos.Web.Data;
using Tos.Web.Logging;

namespace Tos.Web.Controllers
{
    //created from 【CsvUploadDownloadController(Ver2.0)】 Template
    public class _103_ShishakuData_HyojiYoko_Excel_Controller : ApiController
    {

        private const string BUNDO_FORMAT = "0.00";
        private const string LEFT_ALIGN = "Left";
        private const string CENTER_ALIGN = "Center";
        /// 検索条件に一致するデータを抽出しExcelを作成します。（Downloadフォルダに実体ファイル作成後、パスを返却）
        /// </summary>
        /// <param name="options">検索条件</param>
        /// <returns>作成されたExcelファイルのパス</returns>
        public HttpResponseMessage GetExcel([FromUri]paramExcel103 options)
        {
            //該当データが存在していること
            if (!isNotExitShisa(options))
            {
                return Request.CreateResponse<string>(HttpStatusCode.BadRequest, "AP0007");
            }
            // 読込テンプレートファイルパス
            string templatepath = HttpContext.Current.Server.MapPath(Properties.Settings.Default.ExcelTemplateFolder);
            string dirTemlates = templatepath + "\\" + Properties.Resources.ShisakuHyojiYohoExcelTemplate + Properties.Resources.ExcelXlsmExtension;
            // 一時保存フォルダパスの取得
            string dirDownload = HttpContext.Current.Server.MapPath(Properties.Settings.Default.DownloadTempFolder);
            // 保存Excelファイル名（ユーザID_download_現在日時.xlsx）
            var userInfo = Tos.Web.Data.UserInfo.CreateFromAuthorityMaster(this.User.Identity);
            decimal id_user = userInfo.EmployeeCD;

            var shain_nen_oi = options.cd_shain.ToString().PadLeft(10, '0') + "-"
                                    + options.nen.ToString().PadLeft(2, '0') + "-"
                                    + options.no_oi.ToString().PadLeft(3, '0');

            string filename = Properties.Resources.ShisakuHyojiYohoExcelName + "_" + shain_nen_oi + "_" + id_user;
            string excelname = filename + "_" + DateTime.Now.ToString("yyyyMMdd_HHmmss") + Properties.Resources.ExcelXlsmExtension;
            // 既存ファイル削除（前回までの一時保存ファイルを削除）
            FileUploadDownloadUtility.deleteTempFile(dirDownload, filename);

            ExcelFile excelFile = new ExcelFile(dirTemlates);

            /////ファイル作成（ファイルが存在しているときは、上書きする）
            //using (FileStream fs = new FileStream(dirDownload + "\\" + excelname, FileMode.Create, FileAccess.Write))
            //{
            //    //バイト型配列の内容をすべて書き込む
            //    fs.Write(
            //        ((MemoryStream)excelFile.Stream).ToArray(),
            //        0,
            //        ((MemoryStream)excelFile.Stream).ToArray().Length
            //    );
            //}
            //((MemoryStream)excelFile.Stream).Close();

            paramSearch param = new paramSearch();
            //会社コード
            param.cd_shain = options.cd_shain;
            //年
            param.nen = options.nen;
            //追番
            param.no_oi = options.no_oi;
            var controller = new ShishakuDataController
            {
                Request = new HttpRequestMessage(HttpMethod.Post, Request.RequestUri.AbsoluteUri.Replace("/current/route", "/route/to_call"))
            };
            controller.Request.Properties[HttpPropertyKeys.HttpConfigurationKey] = new HttpConfiguration();
            HttpResponseMessage dataShisakuHin = controller.Get(param);
            if (dataShisakuHin.StatusCode != HttpStatusCode.OK)
            {
                return dataShisakuHin;
            }

            //shisakuhinResult dataHin = (shisakuhinResult)dataShisakuHin.Content.ReadAsAsync(typeof(shisakuhinResult)).Result;
            // Excelテンプレートを読み込み、必要な情報をマッピングしてクライアントへ返却
            Stream stream = EditExcelFile(excelFile, options);
            return FileUploadDownloadUtility.CreateFileResponse((MemoryStream)stream, excelname);
        }

        /// <summary>
        /// 該当データが存在していること
        /// </summary>
        /// <returns></returns>
        public bool isNotExitShisa(paramExcel103 options)
        {
            using (ShohinKaihatsuEntities context = new ShohinKaihatsuEntities())
            {
                var exits = (from c in options.sample
                             join m in context.tr_shisaku.Where(p => p.cd_shain == options.cd_shain && p.nen == options.nen && p.no_oi == options.no_oi) on c equals m.seq_shisaku
                             select new tr_shisaku
                             {
                                 cd_shain = m.cd_shain,
                                 nen = m.nen,
                                 no_oi = m.no_oi,
                                 seq_shisaku = m.seq_shisaku
                             }).ToList();

                if (options.sample.Count() != exits.Count())
                {
                    return false;
                }

                return true;
            }
        }

        /// <summary>
        /// EXCELファイルへの値のマッピング
        /// </summary>
        /// <returns></returns>
        private Stream EditExcelFile(ExcelFile excelFile, paramExcel103 options)
        {
            string nameSheetDefault = "試作表";

            using (ShohinKaihatsuEntities context = new ShohinKaihatsuEntities())
            {
                string dbNumberFormat = "";
                UpdateCell103 Cells = new UpdateCell103();
                Cells.file = excelFile;

                //Get nm_user
                var nm_user = context.tr_shisakuhin.GroupJoin(context.ma_user_togo, x => x.id_toroku,
                                                                               y => y.id_user,
                                                                               (x, y) => new { x, y })
                                                   .SelectMany(x => x.y.DefaultIfEmpty(),
                                                                   (x, y) => new { x.x, y })
                                                   .Where(z => z.x.cd_shain == options.cd_shain
                                                            && z.x.nen == options.nen
                                                            && z.x.no_oi == options.no_oi)
                                                   .Select(z => z.y.nm_user).FirstOrDefault();
                //No 6: 試作者
                Cells.UpdateValue(nameSheetDefault, "B6", nm_user, 0, true, false);

                //Add titles:
                updateValue_Title_Excel(excelFile, nameSheetDefault);

                //Get data tr_shisakuhin, ma_kaisha, ma_busho, ma_literal:
                var shisakuhin = context.sp_shohinkaihatsu_search_103_shisakuhin(options.cd_shain, options.nen, options.no_oi).FirstOrDefault();
                if (shisakuhin != null)
                {
                    var shain_nen_oi = shisakuhin.cd_shain.ToString().PadLeft(10, '0') + "-"
                                    + shisakuhin.nen.ToString().PadLeft(2, '0') + "-"
                                    + shisakuhin.no_oi.ToString().PadLeft(3, '0');
                    //No 1: 試作コード
                    Cells.UpdateValue(nameSheetDefault, "B1", shain_nen_oi, 0, true, false);
                    //No 2: 依頼番号IR@
                    Cells.UpdateValue(nameSheetDefault, "E1", shisakuhin.no_irai, 0, true, false);
                    //No 3: 品名
                    Cells.UpdateValue(nameSheetDefault, "B2", shisakuhin.nm_hin, 0, true, false);
                    //No 4: 所属
                    Cells.UpdateValue(nameSheetDefault, "B4", shisakuhin.nm_kaisha, 0, true, false);
                    //No 5: 選択工場
                    Cells.UpdateValue(nameSheetDefault, "B5", shisakuhin.nm_busho, 0, true, false);
                    //No 15: 総合メモ
                    Cells.UpdateValue(nameSheetDefault, "CA8", shisakuhin.memo, 0, true, false);
                    //No 16: 試作メモ
                    Cells.UpdateValue(nameSheetDefault, "CC8", shisakuhin.memo_shisaku, 0, true, false);

                    if (shisakuhin.keta_shosu != null)
                    {
                        dbNumberFormat = CommonController.addTrailingZero("0", shisakuhin.keta_shosu);
                    }
                    else
                    {
                        dbNumberFormat = null;
                    }
                }

                //Get data tr_shisaku, tr_cyuui, tr_genryo:
                var tr_Shisaku_Cyuui = context.sp_shohinkaihatsu_search_103_shisaku(options.cd_shain, options.nen, options.no_oi).ToList();

                for (int i = 0; i < tr_Shisaku_Cyuui.Count; i++)
                {
                    int indexTitle = 316;
                    string sheet1 = Convert.ToChar('M' + i).ToString();
                    string sheet2 = "A" + Convert.ToChar('G' + i * 2);

                    if (i >= 10)
                    {
                        sheet2 = "B" + Convert.ToChar('A' + (i - 10) * 2);
                    }

                    if (i >= 14)
                    {
                        sheet1 = "A" + Convert.ToChar('A' + (i - 14));
                    }

                    //No 8: 日付
                    Cells.UpdateValue(nameSheetDefault, sheet1 + 6, tr_Shisaku_Cyuui[i].dt_shisaku != null ? tr_Shisaku_Cyuui[i].dt_shisaku.Value.ToString("yyyy/MM/dd") : null, 0, true, false);

                    //No 9: NO
                    Cells.UpdateValue(nameSheetDefault, sheet1 + 7, tr_Shisaku_Cyuui[i].nm_sample, 0, true, false);

                    //No 10: メモ
                    Cells.UpdateValue(nameSheetDefault, sheet1 + 8, tr_Shisaku_Cyuui[i].memo, 0, true, false);

                    //No 11: NO(コメント内容)
                    Cells.UpdateValue(nameSheetDefault, sheet2 + 7, tr_Shisaku_Cyuui[i].nm_sample, 0, true, false);

                    //No 12: コメント内容
                    //var memo_sakusei = tr_Shisaku_Cyuui[i].flg_memo == 1 ? tr_Shisaku_Cyuui[i].memo_sakusei : null;
                    //var hyoka = tr_Shisaku_Cyuui[i].flg_hyoka == 1 ? tr_Shisaku_Cyuui[i].hyoka : null;
                    Cells.UpdateValue(nameSheetDefault, sheet2 + 8, "【作成メモ】" + "\n" + tr_Shisaku_Cyuui[i].memo_sakusei + "\n\n" + "【評価】" + "\n" + tr_Shisaku_Cyuui[i].hyoka, 0, true, false);

                    if (i < 3)
                    {
                        //No 9: NO
                        Cells.UpdateValue(nameSheetDefault, "B" + Convert.ToChar('U' + i * 2) + 7, "サンプル:" + tr_Shisaku_Cyuui[i].nm_sample, 0, true, false);
                        //No 13: 工程版
                        Cells.UpdateValue(nameSheetDefault, "B" + Convert.ToChar('U' + i * 2) + 8, tr_Shisaku_Cyuui[i].no_chui != null ? "工程版:" + tr_Shisaku_Cyuui[i].no_chui : "工程版: 0", 0, true, false);
                        //No 14: 製造工程/注意事項
                        Cells.UpdateValue(nameSheetDefault, "B" + Convert.ToChar('U' + i * 2) + 9, tr_Shisaku_Cyuui[i].chuijiko, 0, true, false);
                    }

                    //No 26: 原料費(Kg)
                    Cells.UpdateValue(nameSheetDefault, sheet1 + 314, tr_Shisaku_Cyuui[i].genryohi, 0, false, false, "0.00");
                    //No 27: 原料費(1個)
                    Cells.UpdateValue(nameSheetDefault, sheet1 + 315, tr_Shisaku_Cyuui[i].genryohi1, 0, false, false, "0.00");

                    if (i < 1)
                    {
                        //Title No 28 -> 106
                        updateValue_Title_flg_Shisaku_28to106(excelFile, nameSheetDefault, tr_Shisaku_Cyuui, indexTitle);

                        indexTitle = 316;
                    }

                    //No 28 -> 107
                    updateValueExcel_flg_Shisaku_28to107(excelFile, nameSheetDefault, tr_Shisaku_Cyuui, i, indexTitle);
                }

                int index = 10;

                //No 17 -> 20:
                //Get data tr_haigo:
                var tr_haigo_list = context.sp_shohinkaihatsu_search_103_kotei(options.cd_shain, options.nen, options.no_oi).ToList();
                foreach (var tr_haigo in tr_haigo_list)
                {
                    //No 17: コード(工程の場合) / コード(原料の場合)
                    Cells.UpdateValue(nameSheetDefault, "A" + index, tr_haigo.cd_genryo_disp, 0, true, false);

                    //No 18: 原料名(工程の場合) / 原料名(原料の場合)
                    Cells.UpdateValue(nameSheetDefault, "B" + index, tr_haigo.nm_genryo_disp, 0, true, false);

                    if (tr_haigo.cd_genryo_disp != "---")
                    {
                        //No 19: 単価
                        Cells.UpdateValue(nameSheetDefault, "F" + index, tr_haigo.tanka != null ? tr_haigo.tanka.ToString() : "0", 0, false, false, BUNDO_FORMAT);

                        //No 20: 歩留
                        Cells.UpdateValue(nameSheetDefault, "G" + index, tr_haigo.budomari != null ? tr_haigo.budomari.ToString() : "0", 0, false, false, BUNDO_FORMAT);

                        //TOsVN - 19075 - 2023/08/28: st ShohinKaihatuSupport Modify 2023 - Bug#2242
                        //原料-原産地
                        Cells.UpdateValue(nameSheetDefault, "H" + index, tr_haigo.flg_gensanchi, 0, true, false, null, CENTER_ALIGN);
                        // -ed Bug#2242
                    }

                    index++;
                }

                //No 21 -> 25:
                if (tr_Shisaku_Cyuui.Count != 0)
                {
                    for (int i = 0; i < tr_Shisaku_Cyuui.Count; i++)
                    {
                        int k = 0;
                        int h = 0;
                        index = 10;
                        var seq_shisaku = tr_Shisaku_Cyuui[i].seq_shisaku;
                        string sheet = Convert.ToChar('M' + i).ToString();
                        if (i >= 14)
                        {
                            sheet = "A" + Convert.ToChar('A' + (i - 14));
                        }

                        //Get data tr_shisaku_list
                        var tr_shisakulist_list = context.sp_shohinkaihatsu_search_103_shisakulist(options.cd_shain, options.nen, options.no_oi, seq_shisaku).ToList();
                        for (int j = 0; j < tr_shisakulist_list.Count; j++)
                        {
                            if (tr_shisakulist_list[j].sort_kotei == 30000)
                            {
                                //No 22: 工程合計重量(g)
                                if (i < 1)
                                {
                                    Cells.UpdateValue(nameSheetDefault, "B" + (160 + k), (k + 1).ToString() + "工程(g)", 0, true, false);
                                }
                                string strValue = getStrValue(tr_shisakulist_list[j].quantity);
                                Cells.UpdateValue(nameSheetDefault, sheet + (160 + k), strValue, 0, false, false, dbNumberFormat);
                                k++;
                            }
                            else if (tr_shisakulist_list[j].sort_kotei == 30001)
                            {
                                string strValue = null;
                                //No 23: 合計重量(g)
                                strValue = getStrValue(tr_shisakulist_list[j].quantity);
                                Cells.UpdateValue(nameSheetDefault, sheet + 312, strValue, 0, false, false, dbNumberFormat);

                                //No 25: 合計仕上重量(g)
                                strValue = getStrValue(tr_Shisaku_Cyuui[i].juryo_shiagari_g);
                                Cells.UpdateValue(nameSheetDefault, sheet + 313, strValue, 0, false, false, dbNumberFormat);
                            }
                            else if (tr_shisakulist_list[j].sort_kotei == 30002)
                            {
                                //Title No 24: 
                                if (i < 1)
                                {
                                    Cells.UpdateValue(nameSheetDefault, "B" + (236 + h), (h + 1).ToString() + "工程仕上重量(g)", 0, true, false);
                                }
                                //No 24: 工程仕上重量(g)
                                string strValue = getStrValue(tr_shisakulist_list[j].quantity);
                                Cells.UpdateValue(nameSheetDefault, sheet + (236 + h), strValue, 0, false, false, dbNumberFormat);
                                h++;
                            }
                            else
                            {
                                //No 21: 配合量
                                string strValue = getStrValue(tr_shisakulist_list[j].quantity);
                                Cells.UpdateValue(nameSheetDefault, sheet + index, strValue, 0, false, false, dbNumberFormat);
                            }

                            index++;
                        }
                    }
                }

                // シートの保存（シート名）
                excelFile.SaveSheet(nameSheetDefault);
            }
            return excelFile.Stream;
        }

        /// <summary>
        ///Update titles No 7, 23 -> 27 for Excel
        /// </summary>
        /// <returns></returns>
        private void updateValue_Title_Excel(ExcelFile excelFile, string nameSheetDefault)
        {
            //No 7: 発行日
            excelFile.UpdateValue(nameSheetDefault, "B7", DateTime.Now.ToString("yyyy/MM/dd hh:mm"), 0, true, false);
            //Title No 23:
            excelFile.UpdateValue(nameSheetDefault, "B312", "合計重量(g)", 0, true, false);
            //Title No 25:
            excelFile.UpdateValue(nameSheetDefault, "B313", "合計仕上重量(g)", 0, true, false);
            //Title No 26:
            excelFile.UpdateValue(nameSheetDefault, "B314", "原料費(Kg)", 0, true, false);
            //Title No 27:
            excelFile.UpdateValue(nameSheetDefault, "B315", "原料費(1個)", 0, true, false);
        }

        /// <summary>
        ///Update titles No 28 -> 106 for Excel
        /// </summary>
        /// <returns></returns>
        private void updateValue_Title_flg_Shisaku_28to106(ExcelFile excelFile, string nameSheetDefault, List<sp_shohinkaihatsu_search_103_shisaku_Result> tr_Shisaku_Cyuui, int indexTitle)
        {
            UpdateCell103 Cells = new UpdateCell103();
            Cells.file = excelFile;

            //Title No 28:
            if (tr_Shisaku_Cyuui[0].flg_sousan == 1)
            {
                Cells.UpdateValue(nameSheetDefault, "B" + indexTitle, "総酸", 0, true, false);
                indexTitle++;
            }
            //Title No 29:
            if (tr_Shisaku_Cyuui[0].flg_shokuen == 1)
            {
                Cells.UpdateValue(nameSheetDefault, "B" + indexTitle, "食塩", 0, true, false);
                indexTitle++;
            }
            //Title No 30:
            if (tr_Shisaku_Cyuui[0].flg_sousan_bunseki == 1)
            {
                Cells.UpdateValue(nameSheetDefault, "B" + indexTitle, "総酸：分析", 0, true, false);
                indexTitle++;
            }
            //Title No 31:
            if (tr_Shisaku_Cyuui[0].flg_shokuen_bunseki == 1)
            {
                Cells.UpdateValue(nameSheetDefault, "B" + indexTitle, "食塩：分析", 0, true, false);
                indexTitle++;
            }
            //Title No 32:
            if (tr_Shisaku_Cyuui[0].flg_sando_bunseki_suiso == 1)
            {
                Cells.UpdateValue(nameSheetDefault, "B" + indexTitle, "水相中酸度：分析", 0, true, false);
                indexTitle++;
            }
            //Title No 33:
            if (tr_Shisaku_Cyuui[0].flg_shokuen_bunseki_suiso == 1)
            {
                Cells.UpdateValue(nameSheetDefault, "B" + indexTitle, "水相中食塩：分析", 0, true, false);
                indexTitle++;
            }
            //Title No 34:
            if (tr_Shisaku_Cyuui[0].flg_Brix == 1)
            {
                Cells.UpdateValue(nameSheetDefault, "B" + indexTitle, "Brix", 0, true, false);
                indexTitle++;
            }
            //Title No 35:
            if (tr_Shisaku_Cyuui[0].flg_ph == 1)
            {
                Cells.UpdateValue(nameSheetDefault, "B" + indexTitle, "pH", 0, true, false);
                indexTitle++;
            }
            //Title No 36:
            if (tr_Shisaku_Cyuui[0].flg_nendo_cream == 1)
            {
                Cells.UpdateValue(nameSheetDefault, "B" + indexTitle, "充填前（クリーム）粘度", 0, true, false);
                indexTitle++;
            }
            //Title No 37:
            if (tr_Shisaku_Cyuui[0].flg_ondo_cream == 1)
            {
                Cells.UpdateValue(nameSheetDefault, "B" + indexTitle, "充填前（クリーム）粘度測定温度", 0, true, false);
                indexTitle++;
            }
            //Title No 38:
            if (tr_Shisaku_Cyuui[0].flg_no_rotor_cream == 1)
            {
                Cells.UpdateValue(nameSheetDefault, "B" + indexTitle, "充填前（クリーム）粘度ローターNo", 0, true, false);
                indexTitle++;
            }
            //Title No 39:
            if (tr_Shisaku_Cyuui[0].flg_speed_cream == 1)
            {
                Cells.UpdateValue(nameSheetDefault, "B" + indexTitle, "充填前（クリーム）粘度スピード", 0, true, false);
                indexTitle++;
            }
            //Title No 40:
            if (tr_Shisaku_Cyuui[0].flg_AW == 1)
            {
                Cells.UpdateValue(nameSheetDefault, "B" + indexTitle, "AW", 0, true, false);
                indexTitle++;
            }
            //Title No 41:
            if (tr_Shisaku_Cyuui[0].flg_nendo == 1)
            {
                Cells.UpdateValue(nameSheetDefault, "B" + indexTitle, "粘度（製品）", 0, true, false);
                indexTitle++;
            }
            //Title No 42:
            if (tr_Shisaku_Cyuui[0].flg_ondo == 1)
            {
                Cells.UpdateValue(nameSheetDefault, "B" + indexTitle, "粘度（製品）－測定時品温", 0, true, false);
                indexTitle++;
            }
            //Title No 43:
            if (tr_Shisaku_Cyuui[0].flg_no_rotor == 1)
            {
                Cells.UpdateValue(nameSheetDefault, "B" + indexTitle, "粘度（製品）－ローターＮｏ", 0, true, false);
                indexTitle++;
            }
            //Title No 44:
            if (tr_Shisaku_Cyuui[0].flg_speed == 1)
            {
                Cells.UpdateValue(nameSheetDefault, "B" + indexTitle, "粘度（製品）－スピード", 0, true, false);
                indexTitle++;
            }
            //Title No 45:
            if (tr_Shisaku_Cyuui[0].flg_hiju == 1)
            {
                Cells.UpdateValue(nameSheetDefault, "B" + indexTitle, "製品比重", 0, true, false);
                indexTitle++;
            }
            //Title No 46:
            if (tr_Shisaku_Cyuui[0].flg_hiju_sui == 1)
            {
                Cells.UpdateValue(nameSheetDefault, "B" + indexTitle, "水相比重", 0, true, false);
                indexTitle++;
            }
            //Title No 47:
            if (tr_Shisaku_Cyuui[0].flg_sando_suiso == 1)
            {
                Cells.UpdateValue(nameSheetDefault, "B" + indexTitle, "水相中酸度", 0, true, false);
                indexTitle++;
            }
            //Title No 48:
            if (tr_Shisaku_Cyuui[0].flg_shokuen_suiso == 1)
            {
                Cells.UpdateValue(nameSheetDefault, "B" + indexTitle, "水相中食塩", 0, true, false);
                indexTitle++;
            }
            //Title No 49:
            if (tr_Shisaku_Cyuui[0].flg_sakusan_suiso == 1)
            {
                Cells.UpdateValue(nameSheetDefault, "B" + indexTitle, "水相中酢酸", 0, true, false);
                indexTitle++;
            }
            //Title No 50:
            if (tr_Shisaku_Cyuui[0].flg_msg_suiso == 1)
            {
                Cells.UpdateValue(nameSheetDefault, "B" + indexTitle, "水相中ＭＳＧ", 0, true, false);
                indexTitle++;
            }
            //Title No 51:
            if (tr_Shisaku_Cyuui[0].flg_jikkoSakusanNodo == 1)
            {
                Cells.UpdateValue(nameSheetDefault, "B" + indexTitle, "実効酢酸濃度", 0, true, false);
                indexTitle++;
            }
            //Title No 52:
            if (tr_Shisaku_Cyuui[0].flg_rank_biseibutsu == 1)
            {
                Cells.UpdateValue(nameSheetDefault, "B" + indexTitle, "微生物ランク", 0, true, false);
                indexTitle++;
            }
            //Title No 53:
            if (tr_Shisaku_Cyuui[0].flg_jikkoHikairiSakusanSando == 1)
            {
                Cells.UpdateValue(nameSheetDefault, "B" + indexTitle, "水相中非解離酢酸酸度", 0, true, false);
                indexTitle++;
            }
            //Title No 54:
            if (tr_Shisaku_Cyuui[0].flg_haigo_kyodo == 1)
            {
                Cells.UpdateValue(nameSheetDefault, "B" + indexTitle, "配合強度", 0, true, false);
                indexTitle++;
            }
            //Title No 55:
            if (tr_Shisaku_Cyuui[0].flg_oilmustard == 1)
            {
                Cells.UpdateValue(nameSheetDefault, "B" + indexTitle, "製品オイルマスタード含有量", 0, true, false);
                indexTitle++;
            }
            //No 56: フリータイトル1
            if (tr_Shisaku_Cyuui[0].flg_free1 == 1)
            {
                Cells.UpdateValue(nameSheetDefault, "B" + indexTitle, tr_Shisaku_Cyuui[0].free_title1, 0, true, false);
                indexTitle++;
            }

            //No 57: フリータイトル2
            if (tr_Shisaku_Cyuui[0].flg_free2 == 1)
            {
                Cells.UpdateValue(nameSheetDefault, "B" + indexTitle, tr_Shisaku_Cyuui[0].free_title2, 0, true, false);
                indexTitle++;
            }

            //No 58: フリータイトル3
            if (tr_Shisaku_Cyuui[0].flg_free3 == 1)
            {
                Cells.UpdateValue(nameSheetDefault, "B" + indexTitle, tr_Shisaku_Cyuui[0].free_title3, 0, true, false);
                indexTitle++;
            }

            //No 59: フリータイトル4
            if (tr_Shisaku_Cyuui[0].flg_free4 == 1)
            {
                Cells.UpdateValue(nameSheetDefault, "B" + indexTitle, tr_Shisaku_Cyuui[0].free_title4, 0, true, false);
                indexTitle++;
            }

            //No 60: フリータイトル5
            if (tr_Shisaku_Cyuui[0].flg_free5 == 1)
            {
                Cells.UpdateValue(nameSheetDefault, "B" + indexTitle, tr_Shisaku_Cyuui[0].free_title5, 0, true, false);
                indexTitle++;
            }

            //No 61: フリータイトル6
            if (tr_Shisaku_Cyuui[0].flg_free6 == 1)
            {
                Cells.UpdateValue(nameSheetDefault, "B" + indexTitle, tr_Shisaku_Cyuui[0].free_title6, 0, true, false);
                indexTitle++;
            }

            //No 62: フリータイトル7
            if (tr_Shisaku_Cyuui[0].flg_free7 == 1)
            {
                Cells.UpdateValue(nameSheetDefault, "B" + indexTitle, tr_Shisaku_Cyuui[0].free_title7, 0, true, false);
                indexTitle++;
            }

            //No 63: フリータイトル8
            if (tr_Shisaku_Cyuui[0].flg_free8 == 1)
            {
                Cells.UpdateValue(nameSheetDefault, "B" + indexTitle, tr_Shisaku_Cyuui[0].free_title8, 0, true, false);
                indexTitle++;
            }

            //No 64: フリータイトル9
            if (tr_Shisaku_Cyuui[0].flg_free9 == 1)
            {
                Cells.UpdateValue(nameSheetDefault, "B" + indexTitle, tr_Shisaku_Cyuui[0].free_title9, 0, true, false);
                indexTitle++;
            }

            //No 65: フリータイトル10
            if (tr_Shisaku_Cyuui[0].flg_free10 == 1)
            {
                Cells.UpdateValue(nameSheetDefault, "B" + indexTitle, tr_Shisaku_Cyuui[0].free_title10, 0, true, false);
                indexTitle++;
            }

            //No 66: フリータイトル11
            if (tr_Shisaku_Cyuui[0].flg_free11 == 1)
            {
                Cells.UpdateValue(nameSheetDefault, "B" + indexTitle, tr_Shisaku_Cyuui[0].free_title11, 0, true, false);
                indexTitle++;
            }

            //No 67: フリータイトル12
            if (tr_Shisaku_Cyuui[0].flg_free12 == 1)
            {
                Cells.UpdateValue(nameSheetDefault, "B" + indexTitle, tr_Shisaku_Cyuui[0].free_title12, 0, true, false);
                indexTitle++;
            }

            //No 68: フリータイトル13
            if (tr_Shisaku_Cyuui[0].flg_free13 == 1)
            {
                Cells.UpdateValue(nameSheetDefault, "B" + indexTitle, tr_Shisaku_Cyuui[0].free_title13, 0, true, false);
                indexTitle++;
            }

            //No 70: フリータイトル14
            if (tr_Shisaku_Cyuui[0].flg_free14 == 1)
            {
                Cells.UpdateValue(nameSheetDefault, "B" + indexTitle, tr_Shisaku_Cyuui[0].free_title14, 0, true, false);
                indexTitle++;
            }

            //No 71: フリータイトル15
            if (tr_Shisaku_Cyuui[0].flg_free15 == 1)
            {
                Cells.UpdateValue(nameSheetDefault, "B" + indexTitle, tr_Shisaku_Cyuui[0].free_title15, 0, true, false);
                indexTitle++;
            }

            //No 72: フリータイトル16
            if (tr_Shisaku_Cyuui[0].flg_free16 == 1)
            {
                Cells.UpdateValue(nameSheetDefault, "B" + indexTitle, tr_Shisaku_Cyuui[0].free_title16, 0, true, false);
                indexTitle++;
            }

            //No 73: フリータイトル17
            if (tr_Shisaku_Cyuui[0].flg_free17 == 1)
            {
                Cells.UpdateValue(nameSheetDefault, "B" + indexTitle, tr_Shisaku_Cyuui[0].free_title17, 0, true, false);
                indexTitle++;
            }

            //No 74: フリータイトル18
            if (tr_Shisaku_Cyuui[0].flg_free18 == 1)
            {
                Cells.UpdateValue(nameSheetDefault, "B" + indexTitle, tr_Shisaku_Cyuui[0].free_title18, 0, true, false);
                indexTitle++;
            }

            //No 75: フリータイトル19
            if (tr_Shisaku_Cyuui[0].flg_free19 == 1)
            {
                Cells.UpdateValue(nameSheetDefault, "B" + indexTitle, tr_Shisaku_Cyuui[0].free_title19, 0, true, false);
                indexTitle++;
            }

            //No 76: フリータイトル20
            if (tr_Shisaku_Cyuui[0].flg_free20 == 1)
            {
                Cells.UpdateValue(nameSheetDefault, "B" + indexTitle, tr_Shisaku_Cyuui[0].free_title20, 0, true, false);
                indexTitle++;
            }
            //Title No 77:
            if (tr_Shisaku_Cyuui[0].flg_toudo == 1)
            {
                Cells.UpdateValue(nameSheetDefault, "B" + indexTitle, "糖度", 0, true, false);
                indexTitle++;
            }
            //Title No 78:
            if (tr_Shisaku_Cyuui[0].flg_suibun_kasei == 1)
            {
                Cells.UpdateValue(nameSheetDefault, "B" + indexTitle, "水分活性", 0, true, false);
                indexTitle++;
            }
            //Title No 79:
            if (tr_Shisaku_Cyuui[0].flg_alcohol == 1)
            {
                Cells.UpdateValue(nameSheetDefault, "B" + indexTitle, "アルコール", 0, true, false);
                indexTitle++;
            }
            //Title No 80:
            if (tr_Shisaku_Cyuui[0].flg_msg == 1)
            {
                Cells.UpdateValue(nameSheetDefault, "B" + indexTitle, "率ＭＳＧ", 0, true, false);
                indexTitle++;
            }
            //No 81: フリー粘度タイトル
            if (tr_Shisaku_Cyuui[0].flg_freeNendo == 1)
            {
                Cells.UpdateValue(nameSheetDefault, "B" + indexTitle, tr_Shisaku_Cyuui[0].freetitle_nendo, 0, true, false);
                indexTitle++;
            }
            //No 82: フリー水分活性タイトル
            if (tr_Shisaku_Cyuui[0].flg_freeSuibunKasei == 1)
            {
                Cells.UpdateValue(nameSheetDefault, "B" + indexTitle, tr_Shisaku_Cyuui[0].freetitle_suibun_kasei, 0, true, false);
                indexTitle++;
            }
            //No 83: フリーアルコールタイトル
            if (tr_Shisaku_Cyuui[0].flg_freeAlchol == 1)
            {
                Cells.UpdateValue(nameSheetDefault, "B" + indexTitle, tr_Shisaku_Cyuui[0].freetitle_alcohol, 0, true, false);
                indexTitle++;
            }
            //Title No 45:
            //R.#15485
            //if (tr_Shisaku_Cyuui[0].flg_sousan_bunseki_suiso == 1)
            //{
            //    Cells.UpdateValue(nameSheetDefault, "B" + indexTitle, "水相中総酸：分析", 0, true, false);
            //    indexTitle++;
            //}
            //Title No 46:
            //R.#15485
            //if (tr_Shisaku_Cyuui[0].flg_shokuen_bunseki_suiso == 1)
            //{
            //    Cells.UpdateValue(nameSheetDefault, "B" + indexTitle, "水相中食塩：分析", 0, true, false);
            //    indexTitle++;
            //}
        }

        /// <summary>
        ///Update value No 28 -> 107
        /// </summary>
        /// <returns></returns>
        private void updateValueExcel_flg_Shisaku_28to107(ExcelFile excelFile, string nameSheetDefault, List<sp_shohinkaihatsu_search_103_shisaku_Result> tr_Shisaku_Cyuui, int i, int indexTitle)
        {
            UpdateCell103 Cells = new UpdateCell103();
            Cells.file = excelFile;

            string sheet = Convert.ToChar('M' + i).ToString();
            if (i >= 14)
            {
                sheet = "A" + Convert.ToChar('A' + (i - 14));
            }

            //No 28: 総酸
            if (tr_Shisaku_Cyuui[i].flg_sousan == 1)
            {
                Cells.UpdateValue(nameSheetDefault, sheet + indexTitle, tr_Shisaku_Cyuui[i].ritu_sousan != null ? tr_Shisaku_Cyuui[i].ritu_sousan.ToString() : null, 0, false, false, "0.00");
                indexTitle++;
            }

            //No 29: 食塩
            if (tr_Shisaku_Cyuui[i].flg_shokuen == 1)
            {
                Cells.UpdateValue(nameSheetDefault, sheet + indexTitle, tr_Shisaku_Cyuui[i].ritu_shokuen != null ? tr_Shisaku_Cyuui[i].ritu_shokuen.ToString() : null, 0, false, false, "0.00");
                indexTitle++;
            }
            //No 30: 総酸：分析
            if (tr_Shisaku_Cyuui[i].flg_sousan_bunseki == 1)
            {
                Cells.UpdateValue(nameSheetDefault, sheet + indexTitle, tr_Shisaku_Cyuui[i].ritu_sousan_bunseki, 0, true, false);
                indexTitle++;
            }
            //No 31: 食塩：分析
            if (tr_Shisaku_Cyuui[i].flg_shokuen_bunseki == 1)
            {
                Cells.UpdateValue(nameSheetDefault, sheet + indexTitle, tr_Shisaku_Cyuui[i].ritu_shokuen_bunseki, 0, true, false);
                indexTitle++;
            }
            //No 32: 水相中酸度：分析
            if (tr_Shisaku_Cyuui[i].flg_sando_bunseki_suiso == 1)
            {
                Cells.UpdateValue(nameSheetDefault, sheet + indexTitle, tr_Shisaku_Cyuui[i].ritu_sando_bunseki_suiso, 0, true, false);
                indexTitle++;
            }
            //No 33: 水相中食塩：分析
            if (tr_Shisaku_Cyuui[i].flg_shokuen_bunseki_suiso == 1)
            {
                Cells.UpdateValue(nameSheetDefault, sheet + indexTitle, tr_Shisaku_Cyuui[i].ritu_shokuen_bunseki_suiso, 0, true, false);
                indexTitle++;
            }
            //No 34: Brix
            if (tr_Shisaku_Cyuui[i].flg_Brix == 1)
            {
                Cells.UpdateValue(nameSheetDefault, sheet + indexTitle, tr_Shisaku_Cyuui[i].Brix != null ? tr_Shisaku_Cyuui[i].Brix.ToString() : null, 0, false, false, "0.0");
                indexTitle++;
            }
            //No 35: pH
            if (tr_Shisaku_Cyuui[i].flg_ph == 1)
            {
                Cells.UpdateValue(nameSheetDefault, sheet + indexTitle, tr_Shisaku_Cyuui[i].ph, 0, true, false);
                indexTitle++;
            }
            //No 36: 充填前（クリーム）粘度
            if (tr_Shisaku_Cyuui[i].flg_nendo_cream == 1)
            {
                Cells.UpdateValue(nameSheetDefault, sheet + indexTitle, tr_Shisaku_Cyuui[i].nendo_cream, 0, true, false);
                indexTitle++;
            }
            //No 37: 充填前（クリーム）粘度測定温度
            if (tr_Shisaku_Cyuui[i].flg_ondo_cream == 1)
            {
                Cells.UpdateValue(nameSheetDefault, sheet + indexTitle, tr_Shisaku_Cyuui[i].ondo_cream, 0, true, false);
                indexTitle++;
            }
            //No 38: 充填前（クリーム）粘度ローターNo
            if (tr_Shisaku_Cyuui[i].flg_no_rotor_cream == 1)
            {
                Cells.UpdateValue(nameSheetDefault, sheet + indexTitle, tr_Shisaku_Cyuui[i].no_rotor_cream, 0, true, false);
                indexTitle++;
            }
            //No 39: 充填前（クリーム）粘度スピード
            if (tr_Shisaku_Cyuui[i].flg_speed_cream == 1)
            {
                Cells.UpdateValue(nameSheetDefault, sheet + indexTitle, tr_Shisaku_Cyuui[i].speed_cream, 0, true, false);
                indexTitle++;
            }
            //No 40: AW
            if (tr_Shisaku_Cyuui[i].flg_AW == 1)
            {
                Cells.UpdateValue(nameSheetDefault, sheet + indexTitle, tr_Shisaku_Cyuui[i].AW != null ? tr_Shisaku_Cyuui[i].AW.ToString() : null, 0, false, false, "0.000");
                indexTitle++;
            }
            //No 41: 粘度（製品）
            if (tr_Shisaku_Cyuui[i].flg_nendo == 1)
            {
                Cells.UpdateValue(nameSheetDefault, sheet + indexTitle, tr_Shisaku_Cyuui[i].nendo, 0, true, false);
                indexTitle++;
            }
            //No 42: 粘度（製品）－測定時品温
            if (tr_Shisaku_Cyuui[i].flg_ondo == 1)
            {
                Cells.UpdateValue(nameSheetDefault, sheet + indexTitle, tr_Shisaku_Cyuui[i].ondo, 0, true, false);
                indexTitle++;
            }
            //No 43: 粘度（製品）－ローターＮｏ
            if (tr_Shisaku_Cyuui[i].flg_no_rotor == 1)
            {
                Cells.UpdateValue(nameSheetDefault, sheet + indexTitle, tr_Shisaku_Cyuui[i].no_rotor, 0, true, false);
                indexTitle++;
            }
            //No 44: 粘度スピード
            if (tr_Shisaku_Cyuui[i].flg_speed == 1)
            {
                Cells.UpdateValue(nameSheetDefault, sheet + indexTitle, tr_Shisaku_Cyuui[i].speed, 0, true, false);
                indexTitle++;
            }
            //No 45: 製品比重
            if (tr_Shisaku_Cyuui[i].flg_hiju == 1)
            {
                Cells.UpdateValue(nameSheetDefault, sheet + indexTitle, tr_Shisaku_Cyuui[i].hiju, 0, true, false);
                indexTitle++;
            }
            //No 46: 水相比重
            if (tr_Shisaku_Cyuui[i].flg_hiju_sui == 1)
            {
                Cells.UpdateValue(nameSheetDefault, sheet + indexTitle, tr_Shisaku_Cyuui[i].hiju_sui, 0, true, false);
                indexTitle++;
            }
            //No 47: 水相中酸度
            if (tr_Shisaku_Cyuui[i].flg_sando_suiso == 1)
            {
                Cells.UpdateValue(nameSheetDefault, sheet + indexTitle, tr_Shisaku_Cyuui[i].sando_suiso != null ? tr_Shisaku_Cyuui[i].sando_suiso.ToString() : null, 0, false, false, "0.00");
                indexTitle++;
            }
            //No 48: 水相中食塩
            if (tr_Shisaku_Cyuui[i].flg_shokuen_suiso == 1)
            {
                Cells.UpdateValue(nameSheetDefault, sheet + indexTitle, tr_Shisaku_Cyuui[i].shokuen_suiso != null ? tr_Shisaku_Cyuui[i].shokuen_suiso.ToString() : null, 0, false, false, "0.00");
                indexTitle++;
            }
            //No 49: 水相中酢酸
            if (tr_Shisaku_Cyuui[i].flg_sakusan_suiso == 1)
            {
                Cells.UpdateValue(nameSheetDefault, sheet + indexTitle, tr_Shisaku_Cyuui[i].sakusan_suiso != null ? tr_Shisaku_Cyuui[i].sakusan_suiso.ToString() : null, 0, false, false, "0.00");
                indexTitle++;
            }
            //No 50: 水相中ＭＳＧ
            if (tr_Shisaku_Cyuui[i].flg_msg_suiso == 1)
            {
                Cells.UpdateValue(nameSheetDefault, sheet + indexTitle, tr_Shisaku_Cyuui[i].msg_suiso != null ? tr_Shisaku_Cyuui[i].msg_suiso.ToString() : null, 0, false, false, "0.00");
                indexTitle++;
            }
            //No 51: 実効酢酸濃度
            if (tr_Shisaku_Cyuui[i].flg_jikkoSakusanNodo == 1)
            {
                Cells.UpdateValue(nameSheetDefault, sheet + indexTitle, tr_Shisaku_Cyuui[i].jikkoSakusanNodo != null ? tr_Shisaku_Cyuui[i].jikkoSakusanNodo.ToString() : null, 0, false, false, "0.00");
                indexTitle++;
            }
            //No 52: 微生物ランク
            if (tr_Shisaku_Cyuui[i].flg_rank_biseibutsu == 1)
            {
                Cells.UpdateValue(nameSheetDefault, sheet + indexTitle, tr_Shisaku_Cyuui[i].rank_biseibutsu, 0, true, false);
                indexTitle++;
            }
            //No 53: 水相中非解離酢酸酸度
            if (tr_Shisaku_Cyuui[i].flg_jikkoHikairiSakusanSando == 1)
            {
                Cells.UpdateValue(nameSheetDefault, sheet + indexTitle, tr_Shisaku_Cyuui[i].jikkoHikairiSakusanSando != null ? tr_Shisaku_Cyuui[i].jikkoHikairiSakusanSando.ToString() : null, 0, false, false, "0.00");
                indexTitle++;
            }
            //No 54: 配合強度
            if (tr_Shisaku_Cyuui[i].flg_haigo_kyodo == 1)
            {
                //Cells.UpdateValue(nameSheetDefault, sheet + indexTitle, tr_Shisaku_Cyuui[i].haigo_kyodo, 0, true, false);
                Cells.UpdateValue(nameSheetDefault, sheet + indexTitle, tr_Shisaku_Cyuui[i].nm_literal, 0, true, false);
                indexTitle++;
            }
            //No 55: 製品オイルマスタード含有量
            if (tr_Shisaku_Cyuui[i].flg_oilmustard == 1)
            {
                string oilmustard = tr_Shisaku_Cyuui[i].oilmustard;
                if (oilmustard != null)
                {
                    oilmustard = Math.Round(decimal.Parse(oilmustard), 0, MidpointRounding.AwayFromZero).ToString();
                }
                Cells.UpdateValue(nameSheetDefault, sheet + indexTitle, tr_Shisaku_Cyuui[i].oilmustard, 0, false, false, "0");
                indexTitle++;
            }
            //No 56: フリー内容1
            if (tr_Shisaku_Cyuui[i].flg_free1 == 1)
            {
                Cells.UpdateValue(nameSheetDefault, sheet + indexTitle, tr_Shisaku_Cyuui[i].free_value1, 0, true, false, null, LEFT_ALIGN);
                indexTitle++;
            }
            //No 57: フリー内容2
            if (tr_Shisaku_Cyuui[i].flg_free2 == 1)
            {
                Cells.UpdateValue(nameSheetDefault, sheet + indexTitle, tr_Shisaku_Cyuui[i].free_value2, 0, true, false, null, LEFT_ALIGN);
                indexTitle++;
            }
            //No 58: フリー内容3
            if (tr_Shisaku_Cyuui[i].flg_free3 == 1)
            {
                Cells.UpdateValue(nameSheetDefault, sheet + indexTitle, tr_Shisaku_Cyuui[i].free_value3, 0, true, false, null, LEFT_ALIGN);
                indexTitle++;
            }
            //No 59: フリー内容4
            if (tr_Shisaku_Cyuui[i].flg_free4 == 1)
            {
                Cells.UpdateValue(nameSheetDefault, sheet + indexTitle, tr_Shisaku_Cyuui[i].free_value4, 0, true, false, null, LEFT_ALIGN);
                indexTitle++;
            }
            //No 60: フリー内容5
            if (tr_Shisaku_Cyuui[i].flg_free5 == 1)
            {
                Cells.UpdateValue(nameSheetDefault, sheet + indexTitle, tr_Shisaku_Cyuui[i].free_value5, 0, true, false, null, LEFT_ALIGN);
                indexTitle++;
            }
            //No 61: フリー内容6
            if (tr_Shisaku_Cyuui[i].flg_free6 == 1)
            {
                Cells.UpdateValue(nameSheetDefault, sheet + indexTitle, tr_Shisaku_Cyuui[i].free_value6, 0, true, false, null, LEFT_ALIGN);
                indexTitle++;
            }
            //No 62: フリー内容7
            if (tr_Shisaku_Cyuui[i].flg_free7 == 1)
            {
                Cells.UpdateValue(nameSheetDefault, sheet + indexTitle, tr_Shisaku_Cyuui[i].free_value7, 0, true, false, null, LEFT_ALIGN);
                indexTitle++;
            }
            //No 63: フリー内容8
            if (tr_Shisaku_Cyuui[i].flg_free8 == 1)
            {
                Cells.UpdateValue(nameSheetDefault, sheet + indexTitle, tr_Shisaku_Cyuui[i].free_value8, 0, true, false, null, LEFT_ALIGN);
                indexTitle++;
            }
            //No 64: フリー内容9
            if (tr_Shisaku_Cyuui[i].flg_free9 == 1)
            {
                Cells.UpdateValue(nameSheetDefault, sheet + indexTitle, tr_Shisaku_Cyuui[i].free_value9, 0, true, false, null, LEFT_ALIGN);
                indexTitle++;
            }
            //No 65: フリー内容10
            if (tr_Shisaku_Cyuui[i].flg_free10 == 1)
            {
                Cells.UpdateValue(nameSheetDefault, sheet + indexTitle, tr_Shisaku_Cyuui[i].free_value10, 0, true, false, null, LEFT_ALIGN);
                indexTitle++;
            }
            //No 66: フリー内容11
            if (tr_Shisaku_Cyuui[i].flg_free11 == 1)
            {
                Cells.UpdateValue(nameSheetDefault, sheet + indexTitle, tr_Shisaku_Cyuui[i].free_value11, 0, true, false, null, LEFT_ALIGN);
                indexTitle++;
            }
            //No 67: フリー内容12
            if (tr_Shisaku_Cyuui[i].flg_free12 == 1)
            {
                Cells.UpdateValue(nameSheetDefault, sheet + indexTitle, tr_Shisaku_Cyuui[i].free_value12, 0, true, false, null, LEFT_ALIGN);
                indexTitle++;
            }
            //No 68: フリー内容13
            if (tr_Shisaku_Cyuui[i].flg_free13 == 1)
            {
                Cells.UpdateValue(nameSheetDefault, sheet + indexTitle, tr_Shisaku_Cyuui[i].free_value13, 0, true, false, null, LEFT_ALIGN);
                indexTitle++;
            }
            //No 69: フリー内容14
            if (tr_Shisaku_Cyuui[i].flg_free14 == 1)
            {
                Cells.UpdateValue(nameSheetDefault, sheet + indexTitle, tr_Shisaku_Cyuui[i].free_value14, 0, true, false, null, LEFT_ALIGN);
                indexTitle++;
            }
            //No 70: フリー内容15
            if (tr_Shisaku_Cyuui[i].flg_free15 == 1)
            {
                Cells.UpdateValue(nameSheetDefault, sheet + indexTitle, tr_Shisaku_Cyuui[i].free_value15, 0, true, false, null, LEFT_ALIGN);
                indexTitle++;
            }
            //No 71: フリー内容16
            if (tr_Shisaku_Cyuui[i].flg_free16 == 1)
            {
                Cells.UpdateValue(nameSheetDefault, sheet + indexTitle, tr_Shisaku_Cyuui[i].free_value16, 0, true, false, null, LEFT_ALIGN);
                indexTitle++;
            }
            //No 72: フリー内容17
            if (tr_Shisaku_Cyuui[i].flg_free17 == 1)
            {
                Cells.UpdateValue(nameSheetDefault, sheet + indexTitle, tr_Shisaku_Cyuui[i].free_value17, 0, true, false, null, LEFT_ALIGN);
                indexTitle++;
            }
            //No 73: フリー内容18
            if (tr_Shisaku_Cyuui[i].flg_free18 == 1)
            {
                Cells.UpdateValue(nameSheetDefault, sheet + indexTitle, tr_Shisaku_Cyuui[i].free_value18, 0, true, false, null, LEFT_ALIGN);
                indexTitle++;
            }
            //No 74: フリー内容19
            if (tr_Shisaku_Cyuui[i].flg_free19 == 1)
            {
                Cells.UpdateValue(nameSheetDefault, sheet + indexTitle, tr_Shisaku_Cyuui[i].free_value19, 0, true, false, null, LEFT_ALIGN);
                indexTitle++;
            }
            //No 75: フリー内容20
            if (tr_Shisaku_Cyuui[i].flg_free20 == 1)
            {
                Cells.UpdateValue(nameSheetDefault, sheet + indexTitle, tr_Shisaku_Cyuui[i].free_value20, 0, true, false, null, LEFT_ALIGN);
                indexTitle++;
            }
            //No 76: 糖度
            if (tr_Shisaku_Cyuui[i].flg_toudo == 1)
            {
                Cells.UpdateValue(nameSheetDefault, sheet + indexTitle, tr_Shisaku_Cyuui[i].toudo, 0, true, false);
                indexTitle++;
            }
            //No 77: 水分活性
            if (tr_Shisaku_Cyuui[i].flg_suibun_kasei == 1)
            {
                Cells.UpdateValue(nameSheetDefault, sheet + indexTitle, tr_Shisaku_Cyuui[i].suibun_kasei, 0, true, false);
                indexTitle++;
            }
            //No 78: アルコール
            if (tr_Shisaku_Cyuui[i].flg_alcohol == 1)
            {
                Cells.UpdateValue(nameSheetDefault, sheet + indexTitle, tr_Shisaku_Cyuui[i].alcohol, 0, true, false);
                indexTitle++;
            }
            //No 79: 率ＭＳＧ
            if (tr_Shisaku_Cyuui[i].flg_msg == 1)
            {
                Cells.UpdateValue(nameSheetDefault, sheet + indexTitle, tr_Shisaku_Cyuui[i].ritu_msg != null ? tr_Shisaku_Cyuui[i].ritu_msg.ToString() : null, 0, false, false, "0.00");
                indexTitle++;
            }
            //No 80: フリー粘度
            if (tr_Shisaku_Cyuui[i].flg_freeNendo == 1)
            {
                Cells.UpdateValue(nameSheetDefault, sheet + indexTitle, tr_Shisaku_Cyuui[i].free_nendo, 0, true, false);
                indexTitle++;
            }
            //No 81: フリー温度
            if (tr_Shisaku_Cyuui[i].flg_freeOndo == 1)
            {
                Cells.UpdateValue(nameSheetDefault, sheet + indexTitle, tr_Shisaku_Cyuui[i].free_ondo, 0, true, false, null, LEFT_ALIGN);
                indexTitle++;
            }
            //No 82: フリー水分活性
            if (tr_Shisaku_Cyuui[i].flg_freeSuibunKasei == 1)
            {
                Cells.UpdateValue(nameSheetDefault, sheet + indexTitle, tr_Shisaku_Cyuui[i].free_suibun_kasei, 0, true, false, null, LEFT_ALIGN);
                indexTitle++;
            }
            //No 83: フリーアルコール
            if (tr_Shisaku_Cyuui[i].flg_freeAlchol == 1)
            {
                Cells.UpdateValue(nameSheetDefault, sheet + indexTitle, tr_Shisaku_Cyuui[i].free_alcohol, 0, true, false, null, LEFT_ALIGN);
                indexTitle++;
            }
            //No 45: 水相中総酸：分析
            // R.#15485
            //if (tr_Shisaku_Cyuui[i].flg_sousan_bunseki_suiso == 1)
            //{
            //    Cells.UpdateValue(nameSheetDefault, sheet + indexTitle, tr_Shisaku_Cyuui[i].ritu_sousan_bunseki_suiso, 0, true, false);
            //    indexTitle++;
            //}

            //No 46: 水相中食塩：分析
            // R.#15485
            //if (tr_Shisaku_Cyuui[i].flg_shokuen_bunseki_suiso == 1)
            //{
            //    Cells.UpdateValue(nameSheetDefault, sheet + indexTitle, tr_Shisaku_Cyuui[i].ritu_shokuen_bunseki_suiso, 0, true, false);
            //    indexTitle++;
            //}
        }

        //Convert number 1 byte to 2 byte
        private string toTwoByte(string value)
        {
            if (value == null || value == String.Empty)
            {
                return value;
            }
            string regex = "０１２３４５６７８９";
            string result = string.Empty;
            for (var i = 0; i < value.Length; i++)
            {
                string indVal = value[i].ToString();
                int numVal = -1;
                if (Int32.TryParse(indVal, out numVal))
                {
                    result += regex[numVal];
                }
                else
                {
                    result += value[i];
                }
            }
            return result;
        }

        /// <summary>
        /// convert object data to string data
        /// </summary>
        /// <param name="data"></param>
        /// <returns></returns>
        private string getStrValue(object data)
        {
            if (data == null)
            {
                return null;
            }
            return data.ToString();
        }
    }

    #region "OOP contructions - Base Excel objects"

    /// <summary>
    /// Excel request param
    /// </summary>
    /// <returns></returns>
    public class paramExcel103
    {
        public decimal cd_shain { get; set; }
        public decimal nen { get; set; }
        public decimal no_oi { get; set; }
        public List<int> sample { get; set; }
    }

    /// <summary>
    /// Update without null value
    /// </summary>
    public class UpdateCell103
    {
        public ExcelFile file { get; set; }
        public void UpdateValue(string sheetName, string addressName, string value, UInt32Value styleIndex, bool isString, bool isSave = true, string numberingFormat = null, string align = null)
        {
            if (file == null || value == null)
            {
                return;
            }
            file.UpdateValue(sheetName, addressName, value, styleIndex, isString, isSave, numberingFormat, align);
        }
    }
    #endregion
}
