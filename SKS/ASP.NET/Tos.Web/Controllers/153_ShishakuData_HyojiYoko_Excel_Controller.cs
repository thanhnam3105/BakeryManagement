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
    public class _153_ShishakuData_HyojiYoko_Excel_Controller : ApiController
    {

        private const string BUNDO_FORMAT = "0.00";
        private const string LEFT_ALIGN = "Left";
        /// 検索条件に一致するデータを抽出しExcelを作成します。（Downloadフォルダに実体ファイル作成後、パスを返却）
        /// </summary>
        /// <param name="options">検索条件</param>
        /// <returns>作成されたExcelファイルのパス</returns>
        public HttpResponseMessage GetExcel([FromUri]paramExcel153 options)
        {
            //該当データが存在していること
            if (!isNotExitShisa(options))
            {
                return Request.CreateResponse<string>(HttpStatusCode.BadRequest, "AP0007");
            }
            // 読込テンプレートファイルパス
            string templatepath = HttpContext.Current.Server.MapPath(Properties.Settings.Default.ExcelTemplateFolder);
            string dirTemlates = templatepath + "\\" + Properties.Resources.ShisakuHyojiYoho153ExcelName + Properties.Resources.ExcelXlsmExtension;
            // 一時保存フォルダパスの取得
            string dirDownload = HttpContext.Current.Server.MapPath(Properties.Settings.Default.DownloadTempFolder);
            // 保存Excelファイル名（ユーザID_download_現在日時.xlsx）
            var userInfo = Tos.Web.Data.UserInfo.CreateFromAuthorityMaster(this.User.Identity);
            decimal id_user = userInfo.EmployeeCD;

            var shain_nen_oi = options.cd_shain.ToString().PadLeft(10, '0') + "-"
                                    + options.nen.ToString().PadLeft(2, '0') + "-"
                                    + options.no_oi.ToString().PadLeft(3, '0');

            string filename = Properties.Resources.ShisakuHyojiYoho153ExcelName + "_" + shain_nen_oi + "_" + id_user;
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

            paramSearch_151 param = new paramSearch_151();
            //会社コード
            param.cd_shain = options.cd_shain;
            //年
            param.nen = options.nen;
            //追番
            param.no_oi = options.no_oi;
            var controller = new _151_ShishakuDataController
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
        public bool isNotExitShisa(paramExcel153 options)
        {
            using (ShohinKaihatsuEntities context = new ShohinKaihatsuEntities())
            {
                var exits = (from c in options.sample
                             join m in context.tr_shisaku_bf.Where(p => p.cd_shain == options.cd_shain && p.nen == options.nen && p.no_oi == options.no_oi) on c equals m.seq_shisaku
                             select new tr_shisaku_bf
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
        private Stream EditExcelFile(ExcelFile excelFile, paramExcel153 options)
        {
            string nameSheetDefault = "試作表";

            using (ShohinKaihatsuEntities context = new ShohinKaihatsuEntities())
            {
                string dbNumberFormat = "";
                UpdateCell153 Cells = new UpdateCell153();
                Cells.file = excelFile;

                //Get nm_user
                var nm_user = context.tr_shisakuhin_bf.GroupJoin(context.ma_user, x => x.id_toroku,
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

                //Get data tr_shisakuhin_bf, ma_kaisha, ma_busho, ma_literal -->remove:
                var shisakuhin = context.sp_shohinkaihatsu_search_153_shisakuhin(options.cd_shain, options.nen, options.no_oi).FirstOrDefault();
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
                    //No 8: 選択工場 shisakuhin.yoryo_cd_tanti
                    Cells.UpdateValue(nameSheetDefault, "B8", shisakuhin.yoryo_cd_tanti, 0, true, false);
                    //No 18: 総合メモ
                    Cells.UpdateValue(nameSheetDefault, "CA8", shisakuhin.memo, 0, true, false);
                    //No 19: 試作メモ
                    Cells.UpdateValue(nameSheetDefault, "CC8", shisakuhin.memo_shisaku, 0, true, false);

                    if (shisakuhin.keta_shosu != null)
                    {
                        dbNumberFormat = CommonController.addTrailingZero("0", shisakuhin.keta_shosu);
                    }
                    else
                    {
                        dbNumberFormat = "0";
                    }
                }

                //Get data tr_shisaku_bf, tr_cyuui_bf, tr_genryo -- > remove:
                var tr_Shisaku_Cyuui_bf = context.sp_shohinkaihatsu_search_153_shisaku(options.cd_shain, options.nen, options.no_oi).ToList();

                for (int i = 0; i < tr_Shisaku_Cyuui_bf.Count; i++)
                {
                    //int indexTitle = 316;
                    int indexTitle = 320;
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

                    //No 9: 日付
                    Cells.UpdateValue(nameSheetDefault, sheet1 + 6, tr_Shisaku_Cyuui_bf[i].dt_shisaku != null ? tr_Shisaku_Cyuui_bf[i].dt_shisaku.Value.ToString("yyyy/MM/dd") : null, 0, true, false);

                    //No 10: NO
                    Cells.UpdateValue(nameSheetDefault, sheet1 + 7, tr_Shisaku_Cyuui_bf[i].nm_sample, 0, true, false);

                    //No 11: メモ
                    Cells.UpdateValue(nameSheetDefault, sheet1 + 8, tr_Shisaku_Cyuui_bf[i].memo, 0, true, false);

                    //No 12: コメント内容_NO
                    Cells.UpdateValue(nameSheetDefault, sheet2 + 7, tr_Shisaku_Cyuui_bf[i].nm_sample, 0, true, false);

                    //No 13 + 14: コメント内容_作成メモ
                    //var memo_sakusei = tr_Shisaku_Cyuui_bf[i].flg_memo == 1 ? tr_Shisaku_Cyuui_bf[i].memo_sakusei : null;

                    //var hyoka = tr_Shisaku_Cyuui_bf[i].flg_hyoka == 1 ? tr_Shisaku_Cyuui_bf[i].hyoka : null;
                    Cells.UpdateValue(nameSheetDefault, sheet2 + 8, "【作成メモ】" + "\n" + tr_Shisaku_Cyuui_bf[i].memo_sakusei + "\n\n" + "【評価】" + "\n" + tr_Shisaku_Cyuui_bf[i].hyoka, 0, true, false);

                    if (i < 3)
                    {
                        //No 15: 製品情報_NO
                        Cells.UpdateValue(nameSheetDefault, "B" + Convert.ToChar('U' + i * 2) + 7, "サンプル:" + tr_Shisaku_Cyuui_bf[i].nm_sample, 0, true, false);
                        //No 16: 製品情報_工程版
                        Cells.UpdateValue(nameSheetDefault, "B" + Convert.ToChar('U' + i * 2) + 8, tr_Shisaku_Cyuui_bf[i].no_chui != null ? "工程版:" + tr_Shisaku_Cyuui_bf[i].no_chui : "工程版: 0", 0, true, false);
                        //No 17: 製品情報_製造工程/注意事項
                        Cells.UpdateValue(nameSheetDefault, "B" + Convert.ToChar('U' + i * 2) + 9, tr_Shisaku_Cyuui_bf[i].chuijiko, 0, true, false);
                    }

                    //No 26: 原料費(Kg)
                    //Cells.UpdateValue(nameSheetDefault, sheet1 + 314, tr_Shisaku_Cyuui_bf[i].genryohi, 0, false, false, "0.00");
                    //No 27: 原料費(1個)
                    //Cells.UpdateValue(nameSheetDefault, sheet1 + 315, tr_Shisaku_Cyuui_bf[i].genryohi1, 0, false, false, "0.00");

                    if (i < 1)
                    {
                        //Title No 37 -> 41
                        updateValue_Title_flg_Shisaku_37to41(excelFile, nameSheetDefault, tr_Shisaku_Cyuui_bf, indexTitle);

                        //indexTitle = 316;
                        indexTitle = 320;
                    }

                    //No 37 -> 41
                    updateValueExcel_flg_Shisaku_37to41(excelFile, nameSheetDefault, tr_Shisaku_Cyuui_bf, i, indexTitle);
                }

                int index = 10;

                //No 20 -> 25:
                //Get data tr_haigo_bf:
                var tr_haigo_bf_list = context.sp_shohinkaihatsu_search_153_kotei(options.cd_shain, options.nen, options.no_oi).ToList();
                foreach (var tr_haigo_bf in tr_haigo_bf_list)
                {
                    //No 20-21: コード(工程の場合) / コード(原料の場合)
                    Cells.UpdateValue(nameSheetDefault, "A" + index, tr_haigo_bf.cd_genryo_disp, 0, true, false);

                    //No 22-23: 原料名(工程の場合) / 原料名(原料の場合)
                    Cells.UpdateValue(nameSheetDefault, "B" + index, tr_haigo_bf.nm_genryo_disp, 0, true, false);

                    if (tr_haigo_bf.cd_genryo_disp != "---")
                    {
                        //No 24: 単価
                        Cells.UpdateValue(nameSheetDefault, "F" + index, tr_haigo_bf.tanka != null ? tr_haigo_bf.tanka.ToString() : "0", 0, false, false, BUNDO_FORMAT);

                        //No 25: 歩留
                        Cells.UpdateValue(nameSheetDefault, "G" + index, tr_haigo_bf.budomari != null ? tr_haigo_bf.budomari.ToString() : "0", 0, false, false, BUNDO_FORMAT);
                    }

                    index++;
                }

                //No 26 -> 25:
                if (tr_Shisaku_Cyuui_bf.Count != 0)
                {
                    for (int i = 0; i < tr_Shisaku_Cyuui_bf.Count; i++)
                    {
                        int k = 0;
                        int h = 0;
                        index = 10;
                        var seq_shisaku = tr_Shisaku_Cyuui_bf[i].seq_shisaku;
                        string sheet = Convert.ToChar('M' + i).ToString();
                        if (i >= 14)
                        {
                            sheet = "A" + Convert.ToChar('A' + (i - 14));
                        }

                        //Get data tr_shisaku_bf_list
                        var tr_shisakulist_bf_list = context.sp_shohinkaihatsu_search_153_shisakulist(options.cd_shain, options.nen, options.no_oi, seq_shisaku).ToList();
                        for (int j = 0; j < tr_shisakulist_bf_list.Count; j++)
                        {
                            if (tr_shisakulist_bf_list[j].sort_kotei == 30000)
                            {
                                //No 27: 工程(Kg)
                                if (i < 1)
                                {
                                    Cells.UpdateValue(nameSheetDefault, "B" + (160 + k), (k + 1).ToString() + "工程(Kg)", 0, true, false);
                                }
                                //string strValue = getStrValue(tr_shisakulist_bf_list[j].quantity);
                                string strValue = getStrValue(tr_shisakulist_bf_list[j].quantity_kanzan);
                                Cells.UpdateValue(nameSheetDefault, sheet + (160 + k), strValue, 0, false, false, dbNumberFormat);
                                k++;
                            }
                            else if (tr_shisakulist_bf_list[j].sort_kotei == 30001)
                            {
                                string strValue = null;
                                //No 29: 合計重量(Kg)
                                //strValue = getStrValue(tr_shisakulist_bf_list[j].quantity);
                                strValue = getStrValue(tr_shisakulist_bf_list[j].quantity_kanzan);
                                Cells.UpdateValue(nameSheetDefault, sheet + 312, strValue, 0, false, false, dbNumberFormat);

                                //No 30: 合計仕上重量(Kg)
                                strValue = getStrValue(tr_Shisaku_Cyuui_bf[i].juryo_shiagari_g);
                                Cells.UpdateValue(nameSheetDefault, sheet + 313, strValue, 0, false, false, dbNumberFormat);

                                //No 31: レシピコスト(円/Kg)
                                strValue = getStrValue(tr_Shisaku_Cyuui_bf[i].kg_costrecipe);
                                Cells.UpdateValue(nameSheetDefault, sheet + 314, strValue, 0, false, false, BUNDO_FORMAT);

                                //No 32: レシピコスト(円/個)
                                strValue = getStrValue(tr_Shisaku_Cyuui_bf[i].ko_costrecipe);
                                Cells.UpdateValue(nameSheetDefault, sheet + 315, strValue, 0, false, false, BUNDO_FORMAT);

                                //No 33:FC
                                strValue = getStrValue(tr_Shisaku_Cyuui_bf[i].su_fc);
                                Cells.UpdateValue(nameSheetDefault, sheet + 316, strValue, 0, false, false, "0.0");

                                //No 34: Brix(%)
                                strValue = getStrValue(tr_Shisaku_Cyuui_bf[i].su_brix);
                                Cells.UpdateValue(nameSheetDefault, sheet + 317, strValue, 0, false, false, "0.0");

                                //No 35: pH
                                strValue = getStrValue(tr_Shisaku_Cyuui_bf[i].su_ph);
                                Cells.UpdateValue(nameSheetDefault, sheet + 318, strValue, 0, false, false, BUNDO_FORMAT);

                                //No 36: CA
                                strValue = getStrValue(tr_Shisaku_Cyuui_bf[i].su_ca);
                                Cells.UpdateValue(nameSheetDefault, sheet + 319, strValue, 0, false, false, BUNDO_FORMAT);
                            }
                            else if (tr_shisakulist_bf_list[j].sort_kotei == 30002)
                            {
                                //Title No 28: 工程仕上重量(Kg)
                                if (i < 1)
                                {
                                    Cells.UpdateValue(nameSheetDefault, "B" + (236 + h), (h + 1).ToString() + "工程仕上重量(Kg)", 0, true, false);
                                }
                                //No 28: 工程仕上重量(Kg)
                                //string strValue = getStrValue(tr_shisakulist_bf_list[j].quantity);
                                string strValue = getStrValue(tr_shisakulist_bf_list[j].quantity_kanzan);
                                Cells.UpdateValue(nameSheetDefault, sheet + (236 + h), strValue, 0, false, false, dbNumberFormat);
                                h++;
                            }
                            else
                            {
                                //No 26: 配合量 --- CHANGE
                                //string strValue = getStrValue(tr_shisakulist_bf_list[j].quantity);
                                string strValue = getStrValue(tr_shisakulist_bf_list[j].quantity_kanzan);
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
        ///Update titles No 7, 29 -> 36 for Excel
        /// </summary>
        /// <returns></returns>
        private void updateValue_Title_Excel(ExcelFile excelFile, string nameSheetDefault)
        {
            //No 7: 発行日
            excelFile.UpdateValue(nameSheetDefault, "B7", DateTime.Now.ToString("yyyy/MM/dd HH:mm"), 0, true, false);
            //Title No 29:
            excelFile.UpdateValue(nameSheetDefault, "B312", "合計重量(Kg)", 0, true, false);
            //Title No 30:
            excelFile.UpdateValue(nameSheetDefault, "B313", "合計仕上重量(Kg)", 0, true, false);
            //Title No 31:
            excelFile.UpdateValue(nameSheetDefault, "B314", "レシピコスト(円/Kg)", 0, true, false);
            //Title No 32:
            excelFile.UpdateValue(nameSheetDefault, "B315", "レシピコスト(円/個)", 0, true, false);
            //Title No 33:
            excelFile.UpdateValue(nameSheetDefault, "B316", "FC", 0, true, false);
            //Title No 34:
            excelFile.UpdateValue(nameSheetDefault, "B317", "Brix(%)", 0, true, false);
            //Title No 35:
            excelFile.UpdateValue(nameSheetDefault, "B318", "pH", 0, true, false);
            //Title No 36:
            excelFile.UpdateValue(nameSheetDefault, "B319", "CA(%)", 0, true, false);
        }

        /// <summary>
        ///Update titles No 37 -> 41 for Excel
        /// </summary>
        /// <returns></returns>
        private void updateValue_Title_flg_Shisaku_37to41(ExcelFile excelFile, string nameSheetDefault, List<sp_shohinkaihatsu_search_153_shisaku_Result> tr_Shisaku_Cyuui_bf, int indexTitle)
        {
            UpdateCell153 Cells = new UpdateCell153();
            Cells.file = excelFile;
            
            //Title No 37:
            if (tr_Shisaku_Cyuui_bf[0].flg_Brix == 1)
            {
                Cells.UpdateValue(nameSheetDefault, "B" + indexTitle, "Brix(%)", 0, true, false);
                indexTitle++;
            }
            //Title No 38:
            if (tr_Shisaku_Cyuui_bf[0].flg_ph == 1)
            {
                Cells.UpdateValue(nameSheetDefault, "B" + indexTitle, "pH", 0, true, false);
                indexTitle++;
            }
            //Title No 39:
            if (tr_Shisaku_Cyuui_bf[0].flg_CA == 1)
            {
                Cells.UpdateValue(nameSheetDefault, "B" + indexTitle, "CA(%)", 0, true, false);
                indexTitle++;
            }

            //No 40-41: フリータイトル1
            if (tr_Shisaku_Cyuui_bf[0].flg_free1 == 1)
            {
                Cells.UpdateValue(nameSheetDefault, "B" + indexTitle, tr_Shisaku_Cyuui_bf[0].free_title1, 0, true, false);
                indexTitle++;
            }

            //No 40-41: フリータイトル2
            if (tr_Shisaku_Cyuui_bf[0].flg_free2 == 1)
            {
                Cells.UpdateValue(nameSheetDefault, "B" + indexTitle, tr_Shisaku_Cyuui_bf[0].free_title2, 0, true, false);
                indexTitle++;
            }

            //No 40-41: フリータイトル3
            if (tr_Shisaku_Cyuui_bf[0].flg_free3 == 1)
            {
                Cells.UpdateValue(nameSheetDefault, "B" + indexTitle, tr_Shisaku_Cyuui_bf[0].free_title3, 0, true, false);
                indexTitle++;
            }

            //No 40-41: フリータイトル4
            if (tr_Shisaku_Cyuui_bf[0].flg_free4 == 1)
            {
                Cells.UpdateValue(nameSheetDefault, "B" + indexTitle, tr_Shisaku_Cyuui_bf[0].free_title4, 0, true, false);
                indexTitle++;
            }

            //No 40-41: フリータイトル5
            if (tr_Shisaku_Cyuui_bf[0].flg_free5 == 1)
            {
                Cells.UpdateValue(nameSheetDefault, "B" + indexTitle, tr_Shisaku_Cyuui_bf[0].free_title5, 0, true, false);
                indexTitle++;
            }

            //No 40-41: フリータイトル6
            if (tr_Shisaku_Cyuui_bf[0].flg_free6 == 1)
            {
                Cells.UpdateValue(nameSheetDefault, "B" + indexTitle, tr_Shisaku_Cyuui_bf[0].free_title6, 0, true, false);
                indexTitle++;
            }

            //No 40-41: フリータイトル7
            if (tr_Shisaku_Cyuui_bf[0].flg_free7 == 1)
            {
                Cells.UpdateValue(nameSheetDefault, "B" + indexTitle, tr_Shisaku_Cyuui_bf[0].free_title7, 0, true, false);
                indexTitle++;
            }

            //No 40-41: フリータイトル8
            if (tr_Shisaku_Cyuui_bf[0].flg_free8 == 1)
            {
                Cells.UpdateValue(nameSheetDefault, "B" + indexTitle, tr_Shisaku_Cyuui_bf[0].free_title8, 0, true, false);
                indexTitle++;
            }

            //No 40-41: フリータイトル9
            if (tr_Shisaku_Cyuui_bf[0].flg_free9 == 1)
            {
                Cells.UpdateValue(nameSheetDefault, "B" + indexTitle, tr_Shisaku_Cyuui_bf[0].free_title9, 0, true, false);
                indexTitle++;
            }

            //No 40-41: フリータイトル10
            if (tr_Shisaku_Cyuui_bf[0].flg_free10 == 1)
            {
                Cells.UpdateValue(nameSheetDefault, "B" + indexTitle, tr_Shisaku_Cyuui_bf[0].free_title10, 0, true, false);
                indexTitle++;
            }

            //No 40-41: フリータイトル11
            if (tr_Shisaku_Cyuui_bf[0].flg_free11 == 1)
            {
                Cells.UpdateValue(nameSheetDefault, "B" + indexTitle, tr_Shisaku_Cyuui_bf[0].free_title11, 0, true, false);
                indexTitle++;
            }

            //No 40-41: フリータイトル12
            if (tr_Shisaku_Cyuui_bf[0].flg_free12 == 1)
            {
                Cells.UpdateValue(nameSheetDefault, "B" + indexTitle, tr_Shisaku_Cyuui_bf[0].free_title12, 0, true, false);
                indexTitle++;
            }

            //No 40-41: フリータイトル13
            if (tr_Shisaku_Cyuui_bf[0].flg_free13 == 1)
            {
                Cells.UpdateValue(nameSheetDefault, "B" + indexTitle, tr_Shisaku_Cyuui_bf[0].free_title13, 0, true, false);
                indexTitle++;
            }

            //No 40-41: フリータイトル14
            if (tr_Shisaku_Cyuui_bf[0].flg_free14 == 1)
            {
                Cells.UpdateValue(nameSheetDefault, "B" + indexTitle, tr_Shisaku_Cyuui_bf[0].free_title14, 0, true, false);
                indexTitle++;
            }

            //No 40-41: フリータイトル15
            if (tr_Shisaku_Cyuui_bf[0].flg_free15 == 1)
            {
                Cells.UpdateValue(nameSheetDefault, "B" + indexTitle, tr_Shisaku_Cyuui_bf[0].free_title15, 0, true, false);
                indexTitle++;
            }

            //No 40-41: フリータイトル16
            if (tr_Shisaku_Cyuui_bf[0].flg_free16 == 1)
            {
                Cells.UpdateValue(nameSheetDefault, "B" + indexTitle, tr_Shisaku_Cyuui_bf[0].free_title16, 0, true, false);
                indexTitle++;
            }

            //No 40-41: フリータイトル17
            if (tr_Shisaku_Cyuui_bf[0].flg_free17 == 1)
            {
                Cells.UpdateValue(nameSheetDefault, "B" + indexTitle, tr_Shisaku_Cyuui_bf[0].free_title17, 0, true, false);
                indexTitle++;
            }

            //No 40-41: フリータイトル18
            if (tr_Shisaku_Cyuui_bf[0].flg_free18 == 1)
            {
                Cells.UpdateValue(nameSheetDefault, "B" + indexTitle, tr_Shisaku_Cyuui_bf[0].free_title18, 0, true, false);
                indexTitle++;
            }

            //No 40-41: フリータイトル19
            if (tr_Shisaku_Cyuui_bf[0].flg_free19 == 1)
            {
                Cells.UpdateValue(nameSheetDefault, "B" + indexTitle, tr_Shisaku_Cyuui_bf[0].free_title19, 0, true, false);
                indexTitle++;
            }

            //No 40-41: フリータイトル20
            if (tr_Shisaku_Cyuui_bf[0].flg_free20 == 1)
            {
                Cells.UpdateValue(nameSheetDefault, "B" + indexTitle, tr_Shisaku_Cyuui_bf[0].free_title20, 0, true, false);
                indexTitle++;
            }
        }

        /// <summary>
        ///Update value No 37 -> 41
        /// </summary>
        /// <returns></returns>
        private void updateValueExcel_flg_Shisaku_37to41(ExcelFile excelFile, string nameSheetDefault, List<sp_shohinkaihatsu_search_153_shisaku_Result> tr_Shisaku_Cyuui_bf, int i, int indexTitle)
        {
            UpdateCell153 Cells = new UpdateCell153();
            Cells.file = excelFile;

            string sheet = Convert.ToChar('M' + i).ToString();
            if (i >= 14)
            {
                sheet = "A" + Convert.ToChar('A' + (i - 14));
            }

            //No 37: Brix(%)
            if (tr_Shisaku_Cyuui_bf[i].flg_Brix == 1)
            {
                Cells.UpdateValue(nameSheetDefault, sheet + indexTitle, tr_Shisaku_Cyuui_bf[i].Brix != null ? tr_Shisaku_Cyuui_bf[i].Brix.ToString() : null, 0, false, false, "0.0");
                indexTitle++;
            }
            //No 38: pH
            if (tr_Shisaku_Cyuui_bf[i].flg_ph == 1)
            {
                Cells.UpdateValue(nameSheetDefault, sheet + indexTitle, tr_Shisaku_Cyuui_bf[i].ph, 0, true, false, BUNDO_FORMAT);
                indexTitle++;
            }
            //No 39: CA(%)
            if (tr_Shisaku_Cyuui_bf[i].flg_CA == 1)
            {
                Cells.UpdateValue(nameSheetDefault, sheet + indexTitle, tr_Shisaku_Cyuui_bf[i].CA != null ? tr_Shisaku_Cyuui_bf[i].CA.ToString() : null, 0, false, false, BUNDO_FORMAT);
                indexTitle++;
            }
            //No 40-41: フリー内容1
            if (tr_Shisaku_Cyuui_bf[i].flg_free1 == 1)
            {
                Cells.UpdateValue(nameSheetDefault, sheet + indexTitle, tr_Shisaku_Cyuui_bf[i].free_value1, 0, true, false, null, LEFT_ALIGN);
                indexTitle++;
            }
            //No 40-41: フリー内容2
            if (tr_Shisaku_Cyuui_bf[i].flg_free2 == 1)
            {
                Cells.UpdateValue(nameSheetDefault, sheet + indexTitle, tr_Shisaku_Cyuui_bf[i].free_value2, 0, true, false, null, LEFT_ALIGN);
                indexTitle++;
            }
            //No 40-41: フリー内容3
            if (tr_Shisaku_Cyuui_bf[i].flg_free3 == 1)
            {
                Cells.UpdateValue(nameSheetDefault, sheet + indexTitle, tr_Shisaku_Cyuui_bf[i].free_value3, 0, true, false, null, LEFT_ALIGN);
                indexTitle++;
            }
            //No 40-41: フリー内容4
            if (tr_Shisaku_Cyuui_bf[i].flg_free4 == 1)
            {
                Cells.UpdateValue(nameSheetDefault, sheet + indexTitle, tr_Shisaku_Cyuui_bf[i].free_value4, 0, true, false, null, LEFT_ALIGN);
                indexTitle++;
            }
            //No 40-41: フリー内容5
            if (tr_Shisaku_Cyuui_bf[i].flg_free5 == 1)
            {
                Cells.UpdateValue(nameSheetDefault, sheet + indexTitle, tr_Shisaku_Cyuui_bf[i].free_value5, 0, true, false, null, LEFT_ALIGN);
                indexTitle++;
            }
            //No 40-41: フリー内容6
            if (tr_Shisaku_Cyuui_bf[i].flg_free6 == 1)
            {
                Cells.UpdateValue(nameSheetDefault, sheet + indexTitle, tr_Shisaku_Cyuui_bf[i].free_value6, 0, true, false, null, LEFT_ALIGN);
                indexTitle++;
            }
            //No 40-41: フリー内容7
            if (tr_Shisaku_Cyuui_bf[i].flg_free7 == 1)
            {
                Cells.UpdateValue(nameSheetDefault, sheet + indexTitle, tr_Shisaku_Cyuui_bf[i].free_value7, 0, true, false, null, LEFT_ALIGN);
                indexTitle++;
            }
            //No 40-41: フリー内容8
            if (tr_Shisaku_Cyuui_bf[i].flg_free8 == 1)
            {
                Cells.UpdateValue(nameSheetDefault, sheet + indexTitle, tr_Shisaku_Cyuui_bf[i].free_value8, 0, true, false, null, LEFT_ALIGN);
                indexTitle++;
            }
            //No 40-41: フリー内容9
            if (tr_Shisaku_Cyuui_bf[i].flg_free9 == 1)
            {
                Cells.UpdateValue(nameSheetDefault, sheet + indexTitle, tr_Shisaku_Cyuui_bf[i].free_value9, 0, true, false, null, LEFT_ALIGN);
                indexTitle++;
            }
            //No 40-41: フリー内容10
            if (tr_Shisaku_Cyuui_bf[i].flg_free10 == 1)
            {
                Cells.UpdateValue(nameSheetDefault, sheet + indexTitle, tr_Shisaku_Cyuui_bf[i].free_value10, 0, true, false, null, LEFT_ALIGN);
                indexTitle++;
            }
            //No 40-41: フリー内容11
            if (tr_Shisaku_Cyuui_bf[i].flg_free11 == 1)
            {
                Cells.UpdateValue(nameSheetDefault, sheet + indexTitle, tr_Shisaku_Cyuui_bf[i].free_value11, 0, true, false, null, LEFT_ALIGN);
                indexTitle++;
            }
            //No 40-41: フリー内容12
            if (tr_Shisaku_Cyuui_bf[i].flg_free12 == 1)
            {
                Cells.UpdateValue(nameSheetDefault, sheet + indexTitle, tr_Shisaku_Cyuui_bf[i].free_value12, 0, true, false, null, LEFT_ALIGN);
                indexTitle++;
            }
            //No 40-41: フリー内容13
            if (tr_Shisaku_Cyuui_bf[i].flg_free13 == 1)
            {
                Cells.UpdateValue(nameSheetDefault, sheet + indexTitle, tr_Shisaku_Cyuui_bf[i].free_value13, 0, true, false, null, LEFT_ALIGN);
                indexTitle++;
            }
            //No 40-41: フリー内容14
            if (tr_Shisaku_Cyuui_bf[i].flg_free14 == 1)
            {
                Cells.UpdateValue(nameSheetDefault, sheet + indexTitle, tr_Shisaku_Cyuui_bf[i].free_value14, 0, true, false, null, LEFT_ALIGN);
                indexTitle++;
            }
            //No 40-41: フリー内容15
            if (tr_Shisaku_Cyuui_bf[i].flg_free15 == 1)
            {
                Cells.UpdateValue(nameSheetDefault, sheet + indexTitle, tr_Shisaku_Cyuui_bf[i].free_value15, 0, true, false, null, LEFT_ALIGN);
                indexTitle++;
            }
            //No 40-41: フリー内容16
            if (tr_Shisaku_Cyuui_bf[i].flg_free16 == 1)
            {
                Cells.UpdateValue(nameSheetDefault, sheet + indexTitle, tr_Shisaku_Cyuui_bf[i].free_value16, 0, true, false, null, LEFT_ALIGN);
                indexTitle++;
            }
            //No 40-41: フリー内容17
            if (tr_Shisaku_Cyuui_bf[i].flg_free17 == 1)
            {
                Cells.UpdateValue(nameSheetDefault, sheet + indexTitle, tr_Shisaku_Cyuui_bf[i].free_value17, 0, true, false, null, LEFT_ALIGN);
                indexTitle++;
            }
            //No 40-41: フリー内容18
            if (tr_Shisaku_Cyuui_bf[i].flg_free18 == 1)
            {
                Cells.UpdateValue(nameSheetDefault, sheet + indexTitle, tr_Shisaku_Cyuui_bf[i].free_value18, 0, true, false, null, LEFT_ALIGN);
                indexTitle++;
            }
            //No 40-41: フリー内容19
            if (tr_Shisaku_Cyuui_bf[i].flg_free19 == 1)
            {
                Cells.UpdateValue(nameSheetDefault, sheet + indexTitle, tr_Shisaku_Cyuui_bf[i].free_value19, 0, true, false, null, LEFT_ALIGN);
                indexTitle++;
            }
            //No 40-41: フリー内容20
            if (tr_Shisaku_Cyuui_bf[i].flg_free20 == 1)
            {
                Cells.UpdateValue(nameSheetDefault, sheet + indexTitle, tr_Shisaku_Cyuui_bf[i].free_value20, 0, true, false, null, LEFT_ALIGN);
                indexTitle++;
            }
            
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
    public class paramExcel153
    {
        public decimal cd_shain { get; set; }
        public decimal nen { get; set; }
        public decimal no_oi { get; set; }
        public List<int> sample { get; set; }
    }

    /// <summary>
    /// Update without null value
    /// </summary>
    public class UpdateCell153
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
