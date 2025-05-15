using ClosedXML.Excel;
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
    public class _108_ShisakuData_GenkaShisan_Sonota_Excel_Controller : ApiController
    {
        #region "Controllerで公開するAPI"

        /// 検索条件に一致するデータを抽出しExcelを作成します。（Downloadフォルダに実体ファイル作成後、パスを返却）
        /// </summary>
        /// <param name="options">検索条件</param>
        /// <returns>作成されたExcelファイルのパス</returns>
        public HttpResponseMessage GetExcel([FromUri]SonotaGenkaShisanHyoExcelCondition options)
        {
            //該当データが存在していること
            if (!isNotExitShisa(options))
            {
                return Request.CreateResponse<string>(HttpStatusCode.BadRequest, "AP0007");
            }

            ShohinKaihatsuEntities context = new ShohinKaihatsuEntities();
            var haigo_shisaku_list = context.sp_shohinkaihatsu_search_108_haigo_shisaku_list(options.cd_shain, options.nen, options.no_oi).Count();
            if (haigo_shisaku_list == 0)
            {
                return Request.CreateResponse<string>(HttpStatusCode.BadRequest, "AP0007");
            }
            // 読込テンプレートファイルパス
            string templatepath = HttpContext.Current.Server.MapPath(Properties.Settings.Default.ExcelTemplateFolder);
            string dirTemlates = templatepath + "\\" + Properties.Resources.SonotaGenkaShisanHyoTemplateExcelName + Properties.Resources.ExcelXlsmExtension;
            // 一時保存フォルダパスの取得
            string dirDownload = HttpContext.Current.Server.MapPath(Properties.Settings.Default.DownloadTempFolder);
            // 保存Excelファイル名（ユーザID_download_現在日時.xlsx）
            var userInfo = Tos.Web.Data.UserInfo.CreateFromAuthorityMaster(this.User.Identity);
            decimal id_user = userInfo.EmployeeCD;

            var shain_nen_oi = options.cd_shain.ToString().PadLeft(10, '0') + "-"
                                    + options.nen.ToString().PadLeft(2, '0') + "-"
                                    + options.no_oi.ToString().PadLeft(3, '0');
            string filename = Properties.Resources.SonotaGenkaShisanHyoExcelName + "-" + shain_nen_oi + "_" + id_user;
            string excelname = filename + "_" + DateTime.Now.ToString("yyyyMMdd_HHmmss") + Properties.Resources.ExcelXlsmExtension;
            // 既存ファイル削除（前回までの一時保存ファイルを削除）
            FileUploadDownloadUtility.deleteTempFile(dirDownload, filename);

            ExcelFile excelFile = new ExcelFile(dirTemlates);

            // Excelテンプレートを読み込み、必要な情報をマッピングしてクライアントへ返却
            Stream stream = EditExcelFile(excelFile, options);
            return FileUploadDownloadUtility.CreateFileResponse((MemoryStream)stream, excelname);
        }

        /// <summary>
        /// 該当データが存在していること
        /// </summary>
        /// <returns></returns>
        public bool isNotExitShisa(SonotaGenkaShisanHyoExcelCondition options)
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
        private Stream EditExcelFile(ExcelFile excelFile, SonotaGenkaShisanHyoExcelCondition data)
        {
            string nameSheetDefault = "原価試算";

            // ユーザID取得
            string name = UserInfo.GetUserNameFromIdentity(this.User.Identity);
            decimal cd_user = decimal.Parse(name);

            using (ShohinKaihatsuEntities context = new ShohinKaihatsuEntities())
            {
                //Get data tr_shisakuhin, ma_literal and ma_busho:
                var data_shisa_lit_bu = context.sp_shohinkaihatsu_search_108_shisakuhin(data.cd_shain, data.nen, data.no_oi).FirstOrDefault();
                //Insert data to cell 1, 2, 4, 6, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30:
                if (data_shisa_lit_bu != null)
                {
                    //No 1: 品名
                    excelFile.UpdateValue(nameSheetDefault, "D2", data_shisa_lit_bu.nm_hin, 0, true, false);
                    //No 2: 依頼番号                                            
                    excelFile.UpdateValue(nameSheetDefault, "G1", data_shisa_lit_bu.no_irai, 0, true, false);
                    //No 4: 工場番号
                    excelFile.UpdateValue(nameSheetDefault, "D11", data_shisa_lit_bu.cd_kojo != null ? data_shisa_lit_bu.cd_kojo.ToString() : null, 0, false, false);
                    //No 6: 営業
                    excelFile.UpdateValue(nameSheetDefault, "S6", data_shisa_lit_bu.cd_eigyo != null ? data_shisa_lit_bu.cd_eigyo.ToString() : null, 0, false, false);
                    //No 21: 製造工場
                    excelFile.UpdateValue(nameSheetDefault, "E152", data_shisa_lit_bu.nm_busho, 0, true, false);
                    //No 22: 製造方法
                    excelFile.UpdateValue(nameSheetDefault, "E153", data_shisa_lit_bu.nm_literal22, 0, true, false);
                    //No 23: 充填方法
                    excelFile.UpdateValue(nameSheetDefault, "E154", data_shisa_lit_bu.nm_literal23, 0, true, false);
                    //No 24: 殺菌方法
                    excelFile.UpdateValue(nameSheetDefault, "E155", data_shisa_lit_bu.hoho_sakin, 0, true, false);
                    //No 25: 取扱温度
                    excelFile.UpdateValue(nameSheetDefault, "E156", data_shisa_lit_bu.nm_literal25, 0, true, false);
                    //No 26: 容器・包材
                    excelFile.UpdateValue(nameSheetDefault, "E157", data_shisa_lit_bu.youki, 0, true, false);
                    //No 27: 荷姿
                    excelFile.UpdateValue(nameSheetDefault, "E158", data_shisa_lit_bu.cd_nisugata, 0, true, false);
                    //No 28: 内容量（ｍｌ/ｇ）
                    excelFile.UpdateValue(nameSheetDefault, "E159", data_shisa_lit_bu.yoryo, 0, true, false);
                    //No 29: 入り数
                    excelFile.UpdateValue(nameSheetDefault, "E160", data_shisa_lit_bu.su_iri, 0, true, false);
                    //No 30: 賞味期間
                    excelFile.UpdateValue(nameSheetDefault, "E161", data_shisa_lit_bu.shomikikan + data_shisa_lit_bu.nm_literal30, 0, true, false);
                }

                //Insert data to cell 3, 5
                var ma_user = context.ma_user_togo.Where(x => x.id_user == cd_user).FirstOrDefault();
                //No 5: 研究所
                excelFile.UpdateValue(nameSheetDefault, "Q6", ma_user.nm_user, 0, true, false);
                //No 3: 日付
                excelFile.UpdateValue(nameSheetDefault, "I1", DateTime.Now.ToString("yyyy年MM月dd日"), 0, true, false);


                //Get data tr_shisaku, tr_cyuui:
                var data_shisaku_cyuui = context.sp_shohinkaihatsu_search_108_shisaku(data.cd_shain, data.nen, data.no_oi).FirstOrDefault();
                //Insert data to cell 7, 8, 9, 20:
                if (data_shisaku_cyuui != null)
                {
                    //No 7: サンプルNo
                    excelFile.UpdateValue(nameSheetDefault, "I13", data_shisaku_cyuui.nm_sample1, 0, true, false);
                    //No 7: サンプルNo
                    excelFile.UpdateValue(nameSheetDefault, "S15", "サンプル:" + data_shisaku_cyuui.nm_sample1, 0, true, false);
                    //No 8: 工程版
                    excelFile.UpdateValue(nameSheetDefault, "S16", (data_shisaku_cyuui.no_chui1 != null) ? "工程版:" + data_shisaku_cyuui.no_chui1 : null, 0, true, false);
                    //No 9: 製品情報
                    excelFile.UpdateValue(nameSheetDefault, "S17", (data_shisaku_cyuui.no_chui1 != null) ? data_shisaku_cyuui.chuijiko1 : null, 0, true, false);
                    //No 20: 総仕上がり
                    excelFile.UpdateValue(nameSheetDefault, "H149", data_shisaku_cyuui.juryo_shiagari_g1 != null ? data_shisaku_cyuui.juryo_shiagari_g1.ToString() : "0", 0, false, false);

                    //NO 31:
                    excelFile.UpdateValue(nameSheetDefault, "I171", data_shisaku_cyuui.buturyo1, 0, true, false);
                    //NO 32:
                    excelFile.UpdateValue(nameSheetDefault, "I172", data_shisaku_cyuui.dt_hatubai1, 0, true, false);
                    //NO 33:
                    excelFile.UpdateValue(nameSheetDefault, "I174", data_shisaku_cyuui.genka1, 0, true, false);
                    //NO 34:
                    excelFile.UpdateValue(nameSheetDefault, "I175", data_shisaku_cyuui.baika1, 0, true, false);

                    if (data_shisaku_cyuui.isSelect2 != null)
                    {
                        //No 7: サンプルNo
                        excelFile.UpdateValue(nameSheetDefault, "L13", data_shisaku_cyuui.nm_sample2, 0, true, false);
                        //No 7: サンプルNo
                        excelFile.UpdateValue(nameSheetDefault, "U15", "サンプル:" + data_shisaku_cyuui.nm_sample2, 0, true, false);
                        //No 8: 工程版
                        excelFile.UpdateValue(nameSheetDefault, "U16", (data_shisaku_cyuui.no_chui2 != null) ? "工程版:" + data_shisaku_cyuui.no_chui2 : null, 0, true, false);
                        //No 9: 製品情報
                        excelFile.UpdateValue(nameSheetDefault, "U17", (data_shisaku_cyuui.no_chui2 != null) ? data_shisaku_cyuui.chuijiko2 : null, 0, true, false);
                        //No 20: 総仕上がり
                        excelFile.UpdateValue(nameSheetDefault, "K149", data_shisaku_cyuui.juryo_shiagari_g2 != null ? data_shisaku_cyuui.juryo_shiagari_g2.ToString() : "0", 0, false, false);

                        //NO 31:
                        excelFile.UpdateValue(nameSheetDefault, "L171", data_shisaku_cyuui.buturyo2, 0, true, false);
                        //NO 32:
                        excelFile.UpdateValue(nameSheetDefault, "L172", data_shisaku_cyuui.dt_hatubai2, 0, true, false);
                        //NO 33:
                        excelFile.UpdateValue(nameSheetDefault, "L174", data_shisaku_cyuui.genka2, 0, true, false);
                        //NO 34:
                        excelFile.UpdateValue(nameSheetDefault, "L175", data_shisaku_cyuui.baika2, 0, true, false);
                    }

                    if (data_shisaku_cyuui.isSelect3 != null)
                    {
                        //No 7: サンプルNo
                        excelFile.UpdateValue(nameSheetDefault, "O13", data_shisaku_cyuui.nm_sample3, 0, true, false);
                        //No 7: サンプルNo
                        excelFile.UpdateValue(nameSheetDefault, "W15", "サンプル:" + data_shisaku_cyuui.nm_sample3, 0, true, false);
                        //No 8: 工程版
                        excelFile.UpdateValue(nameSheetDefault, "W16", (data_shisaku_cyuui.no_chui3 != null) ? "工程版:" + data_shisaku_cyuui.no_chui3 : null, 0, true, false);
                        //No 9: 製品情報
                        excelFile.UpdateValue(nameSheetDefault, "W17", (data_shisaku_cyuui.no_chui3 != null) ? data_shisaku_cyuui.chuijiko3 : null, 0, true, false);
                        //No 20: 総仕上がり
                        excelFile.UpdateValue(nameSheetDefault, "N149", data_shisaku_cyuui.juryo_shiagari_g3 != null ? data_shisaku_cyuui.juryo_shiagari_g3.ToString() : "0", 0, false, false);

                        //NO 31:
                        excelFile.UpdateValue(nameSheetDefault, "O171", data_shisaku_cyuui.buturyo3, 0, true, false);
                        //NO 32:
                        excelFile.UpdateValue(nameSheetDefault, "O172", data_shisaku_cyuui.dt_hatubai3, 0, true, false);
                        //NO 33:
                        excelFile.UpdateValue(nameSheetDefault, "O174", data_shisaku_cyuui.genka3, 0, true, false);
                        //NO 34:
                        excelFile.UpdateValue(nameSheetDefault, "O175", data_shisaku_cyuui.baika3, 0, true, false);
                    }
                }

                //Get data tr_shisaku_list, tr_haigo join ma_literal
                var haigo_shisaku_list = context.sp_shohinkaihatsu_search_108_haigo_shisaku_list(data.cd_shain, data.nen, data.no_oi).ToList();

                var sort_kotei_list = (from x in haigo_shisaku_list
                                       group x by new { x.kotei, x.sort_kotei } into playerGroup
                                       select new
                                       {
                                           kotei = playerGroup.Key.kotei,
                                           sort_kotei = playerGroup.Key.sort_kotei,
                                           count_sort_kotei = playerGroup.Count(),
                                       }).ToList();
                List<short?> temp = new List<short?>();
                int a = 0;
                int b = 0;
                foreach (var sort_kotei in sort_kotei_list)
                {
                    if (sort_kotei.kotei == 1)
                    {
                        temp.Add(sort_kotei.sort_kotei);
                        a += sort_kotei.count_sort_kotei;
                        if (a > 98)
                        {
                            break;
                        }
                    }
                    else if (sort_kotei.kotei == 2)
                    {
                        temp.Add(sort_kotei.sort_kotei);
                        b += sort_kotei.count_sort_kotei;
                        if (b > 29)
                        {
                            break;
                        }
                    }
                }

                int index1 = 16;
                int index2 = 116;

                foreach (var sort_kotei in temp)
                {
                    //Get data tr_shisaku_list, tr_haigo join ma_literal with ma_literal.value1 = 2 and with ma_literal.value1 = 1 
                    var values1 = haigo_shisaku_list.Where(x => x.kotei == 1 && x.sort_kotei == sort_kotei).Take(98).ToList();
                    if (values1.Count() != 0)
                    {
                        foreach (var values in values1)
                        {
                            if (index1 == 114)
                                break;

                            //Insert data to cell 10, 11, 12, 13, 14

                            //No 10: ソース　品コード
                            excelFile.UpdateValue(nameSheetDefault, "D" + index1, values.cd_genryo, 0, true, false);
                            //No 11: ソース　原材料名
                            excelFile.UpdateValue(nameSheetDefault, "E" + index1, values.nm_genryo, 0, true, false);
                            //No 12: ソース　単価
                            excelFile.UpdateValue(nameSheetDefault, "F" + index1, values.tanka != null ? values.tanka.ToString() : "0", 0, false, false);
                            //No 13: ソース　歩留
                            excelFile.UpdateValue(nameSheetDefault, "G" + index1, values.budomari != null ? (values.budomari / 100).ToString() : "0", 0, false, false);
                            //No 14: ソース　配合量
                            excelFile.UpdateValue(nameSheetDefault, "H" + index1, values.quantity1 != null ? values.quantity1.ToString() : "0", 0, false, false);
                            if (values.isSelect2 != null)
                            {
                                //No 14: ソース　配合量
                                excelFile.UpdateValue(nameSheetDefault, "K" + index1, values.quantity2 != null ? values.quantity2.ToString() : "0", 0, false, false);
                            }
                            if (values.isSelect3 != null)
                            {
                                //No 14: ソース　配合量
                                excelFile.UpdateValue(nameSheetDefault, "N" + index1, values.quantity3 != null ? values.quantity3.ToString() : "0", 0, false, false);
                            }

                            index1++;

                        }

                    }

                    //Get data tr_shisaku_list, tr_haigo join ma_literal with ma_literal.value1 = 2 and with ma_literal.value1 = 2 
                    var values2 = haigo_shisaku_list.Where(x => x.kotei == 2 && x.sort_kotei == sort_kotei).Take(29).ToList();
                    if (values2.Count() != 0)
                    {
                        foreach (var values in values2)
                        {
                            if (index2 == 145)
                                break;

                            //Insert data to cell 15, 16, 17, 18, 19:

                            //No 15: 別充填用具材　品コード
                            excelFile.UpdateValue(nameSheetDefault, "D" + index2, values.cd_genryo, 0, true, false);
                            //No 16: 別充填用具材　原材料名
                            excelFile.UpdateValue(nameSheetDefault, "E" + index2, values.nm_genryo, 0, true, false);
                            //No 17: 別充填用具材　単価
                            excelFile.UpdateValue(nameSheetDefault, "F" + index2, values.tanka != null ? values.tanka.ToString() : "0", 0, false, false);
                            //No 18: 別充填用具材　歩留
                            excelFile.UpdateValue(nameSheetDefault, "G" + index2, values.budomari != null ? (values.budomari / 100).ToString() : "0", 0, false, false);
                            //No 19: 別充填用具材　配合量
                            excelFile.UpdateValue(nameSheetDefault, "H" + index2, values.quantity1 != null ? values.quantity1.ToString() : "0", 0, false, false);
                            if (values.isSelect2 != null)
                            {
                                //No 19: 別充填用具材　配合量
                                excelFile.UpdateValue(nameSheetDefault, "K" + index2, values.quantity2 != null ? values.quantity2.ToString() : "0", 0, false, false);
                            }
                            if (values.isSelect3 != null)
                            {
                                //No 19: 別充填用具材　配合量
                                excelFile.UpdateValue(nameSheetDefault, "N" + index2, values.quantity3 != null ? values.quantity3.ToString() : "0", 0, false, false);
                            }

                            index2++;
                        }
                    }
                }

                // シートの保護（シート名）
                //excelFile.ProtectedSheet(nameSheetDefault, "", false);
                // セルの編集の許可（シート名、編集許可名、編集許可範囲）
                //excelFile.ProtectCancelCells(nameSheetDefault, "title", "A2", "E", false);
                // シートの保存（シート名）
                excelFile.SaveSheet(nameSheetDefault);
            }
            return excelFile.Stream;
        }

        #endregion
    }

    #region "OOP contructions - Base Excel objects"

    /// <summary>
    /// Excel request param
    /// </summary>
    public class SonotaGenkaShisanHyoExcelCondition
    {
        public decimal cd_shain { get; set; }
        public decimal nen { get; set; }
        public decimal no_oi { get; set; }
        public List<int> sample { get; set; }
    }

    #endregion
}
