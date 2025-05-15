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
    public class _107_ShisakuData_GenkaShisan_Chomiryo_Excel_Controller : ApiController
    {
        #region "Controllerで公開するAPI"

        /// 検索条件に一致するデータを抽出しExcelを作成します。（Downloadフォルダに実体ファイル作成後、パスを返却）
        /// </summary>
        /// <param name="options">検索条件</param>
        /// <returns>作成されたExcelファイルのパス</returns>
        public HttpResponseMessage GetExcel([FromUri]ChomiryoGenkaShisanHyoExcelCondition options)
        {
            //該当データが存在していること
            if (!isNotExitShisa(options))
            {
                return Request.CreateResponse<string>(HttpStatusCode.BadRequest, "AP0007");
            }
            ShohinKaihatsuEntities context = new ShohinKaihatsuEntities();
            var haigo_shisaku_list = context.sp_shohinkaihatsu_search_107_haigo_shisaku_list(options.cd_shain, options.nen, options.no_oi).Count();
            if (haigo_shisaku_list == 0)
            {
                return Request.CreateResponse<string>(HttpStatusCode.BadRequest, "AP0007");
            }
            // 読込テンプレートファイルパス
            string templatepath = HttpContext.Current.Server.MapPath(Properties.Settings.Default.ExcelTemplateFolder);
            string dirTemlates = templatepath + "\\" + Properties.Resources.ChomiryoGenkaShisanHyoTemplateExcelName + Properties.Resources.ExcelXlsmExtension;
            // 一時保存フォルダパスの取得
            string dirDownload = HttpContext.Current.Server.MapPath(Properties.Settings.Default.DownloadTempFolder);
            // 保存Excelファイル名（ユーザID_download_現在日時.xlsx）
            var userInfo = Tos.Web.Data.UserInfo.CreateFromAuthorityMaster(this.User.Identity);
            decimal id_user = userInfo.EmployeeCD;
            
            var shain_nen_oi = options.cd_shain.ToString().PadLeft(10, '0') + "-"
                                    + options.nen.ToString().PadLeft(2, '0') + "-"
                                    + options.no_oi.ToString().PadLeft(3, '0');

            string filename = Properties.Resources.ChomiryoGenkaShisanHyoExcelName + "_" + shain_nen_oi + "_" + id_user;
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
        public bool isNotExitShisa(ChomiryoGenkaShisanHyoExcelCondition options)
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
        private Stream EditExcelFile(ExcelFile excelFile, ChomiryoGenkaShisanHyoExcelCondition data)
        {
            string nameSheetDefault = "原価試算";

            // ユーザID取得
            string name = UserInfo.GetUserNameFromIdentity(this.User.Identity);
            decimal cd_user = decimal.Parse(name);

            using (ShohinKaihatsuEntities context = new ShohinKaihatsuEntities())
            {
                //Get data tr_shisakuhin, ma_literal and ma_busho:
                var data_shisa_lit_bu = context.sp_shohinkaihatsu_search_108_shisakuhin(data.cd_shain, data.nen, data.no_oi).FirstOrDefault();
                //Insert data to cell 1, 2, 4, 6, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37:
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
                    //No 28: 製造工場
                    excelFile.UpdateValue(nameSheetDefault, "E149", data_shisa_lit_bu.nm_busho, 0, true, false);
                    //No 29: 製造方法
                    excelFile.UpdateValue(nameSheetDefault, "E150", data_shisa_lit_bu.nm_literal22, 0, true, false);
                    //No 30: 充填方法
                    excelFile.UpdateValue(nameSheetDefault, "E151", data_shisa_lit_bu.nm_literal23, 0, true, false);
                    //No 31: 殺菌方法
                    excelFile.UpdateValue(nameSheetDefault, "E152", data_shisa_lit_bu.hoho_sakin, 0, true, false);
                    //No 32: 取扱温度
                    excelFile.UpdateValue(nameSheetDefault, "E153", data_shisa_lit_bu.nm_literal25, 0, true, false);
                    //No 33: 容器・包材
                    excelFile.UpdateValue(nameSheetDefault, "E154", data_shisa_lit_bu.youki, 0, true, false);
                    //No 34: 荷姿
                    excelFile.UpdateValue(nameSheetDefault, "E155", data_shisa_lit_bu.cd_nisugata, 0, true, false);
                    //No 35: 内容量（ｍｌ/ｇ）
                    excelFile.UpdateValue(nameSheetDefault, "E156", data_shisa_lit_bu.yoryo, 0, true, false);
                    //No 36: 入り数
                    excelFile.UpdateValue(nameSheetDefault, "E157", data_shisa_lit_bu.su_iri, 0, true, false);
                    //No 37: 賞味期間
                    excelFile.UpdateValue(nameSheetDefault, "E158", data_shisa_lit_bu.shomikikan + data_shisa_lit_bu.nm_literal30, 0, true, false);
                }

                //Insert data to cell 3, 5
                var ma_user = context.ma_user_togo.Where(x => x.id_user == cd_user).FirstOrDefault();
                //No 5: 研究所
                excelFile.UpdateValue(nameSheetDefault, "Q6", ma_user.nm_user, 0, true, false);
                //No 3: 日付
                excelFile.UpdateValue(nameSheetDefault, "I1", DateTime.Now.ToString("yyyy年MM月dd日"), 0, true, false);


                //Get data tr_shisakuhin, ma_literal and ma_busho:
                var data_shisaku_cyuui = context.sp_shohinkaihatsu_search_107_shisaku(data.cd_shain, data.nen, data.no_oi).FirstOrDefault();
                //Insert data to cell 7, 8, 9, 25, 26, 27:
                if (data_shisaku_cyuui != null)
                {
                    //No 7: 製品情報
                    excelFile.UpdateValue(nameSheetDefault, "I13", data_shisaku_cyuui.nm_sample1, 0, true, false);
                    //No 7: 製品情報
                    excelFile.UpdateValue(nameSheetDefault, "S15", "サンプル:" + data_shisaku_cyuui.nm_sample1, 0, true, false);
                    //No 8: サンプルNo
                    excelFile.UpdateValue(nameSheetDefault, "S16", (data_shisaku_cyuui.no_chui1 != null) ? "工程版:" + data_shisaku_cyuui.no_chui1 : null, 0, true, false);
                    //No 9: 殺菌調味液　品コード
                    excelFile.UpdateValue(nameSheetDefault, "S17", (data_shisaku_cyuui.no_chui1 != null) ? data_shisaku_cyuui.chuijiko1 : null, 0, true, false);
                    //No 25: 水相(g)
                    excelFile.UpdateValue(nameSheetDefault, "I136", data_shisaku_cyuui.zyusui1 != null ? data_shisaku_cyuui.zyusui1.ToString() : "0", 0, false, false);
                    //No 26: 油相(g)
                    excelFile.UpdateValue(nameSheetDefault, "I137", data_shisaku_cyuui.zyuabura1 != null ? data_shisaku_cyuui.zyuabura1.ToString() : "0", 0, false, false);
                    //No 27: 比重
                    excelFile.UpdateValue(nameSheetDefault, "I138", data_shisaku_cyuui.hiju1 != null ? data_shisaku_cyuui.hiju1.ToString() : "0", 0, false, false);

                    //NO 38:
                    excelFile.UpdateValue(nameSheetDefault, "I162", data_shisaku_cyuui.buturyo1, 0, true, false);
                    //NO 39:
                    excelFile.UpdateValue(nameSheetDefault, "I163", data_shisaku_cyuui.dt_hatubai1, 0, true, false);
                    //NO 40:
                    excelFile.UpdateValue(nameSheetDefault, "I165", data_shisaku_cyuui.genka1, 0, true, false);
                    //NO 41:
                    excelFile.UpdateValue(nameSheetDefault, "I166", data_shisaku_cyuui.baika1, 0, true, false);

                    if (data_shisaku_cyuui.isSelect2 != null)
                    {
                        //No 7: 製品情報
                        excelFile.UpdateValue(nameSheetDefault, "L13", data_shisaku_cyuui.nm_sample2, 0, true, false);
                        //No 7: 製品情報
                        excelFile.UpdateValue(nameSheetDefault, "U15", "サンプル:" + data_shisaku_cyuui.nm_sample2, 0, true, false);
                        //No 8: サンプルNo
                        excelFile.UpdateValue(nameSheetDefault, "U16", (data_shisaku_cyuui.no_chui2 != null) ? "工程版:" + data_shisaku_cyuui.no_chui2 : null, 0, true, false);
                        //No 9: 殺菌調味液　品コード
                        excelFile.UpdateValue(nameSheetDefault, "U17", (data_shisaku_cyuui.no_chui2 != null) ? data_shisaku_cyuui.chuijiko2 : null, 0, true, false);
                        //No 25: 水相(g)
                        excelFile.UpdateValue(nameSheetDefault, "L136", data_shisaku_cyuui.zyusui2 != null ? data_shisaku_cyuui.zyusui2.ToString() : "0", 0, false, false);
                        //No 26: 油相(g)
                        excelFile.UpdateValue(nameSheetDefault, "L137", data_shisaku_cyuui.zyuabura2 != null ? data_shisaku_cyuui.zyuabura2.ToString() : "0", 0, false, false);
                        //No 27: 比重
                        excelFile.UpdateValue(nameSheetDefault, "L138", data_shisaku_cyuui.hiju2 != null ? data_shisaku_cyuui.hiju2.ToString() : "0", 0, false, false);

                        //NO 38:
                        excelFile.UpdateValue(nameSheetDefault, "L162", data_shisaku_cyuui.buturyo2, 0, true, false);
                        //NO 39:
                        excelFile.UpdateValue(nameSheetDefault, "L163", data_shisaku_cyuui.dt_hatubai2, 0, true, false);
                        //NO 40:
                        excelFile.UpdateValue(nameSheetDefault, "L165", data_shisaku_cyuui.genka2, 0, true, false);
                        //NO 41:
                        excelFile.UpdateValue(nameSheetDefault, "L166", data_shisaku_cyuui.baika2, 0, true, false);
                    }

                    if (data_shisaku_cyuui.isSelect3 != null)
                    {
                        //No 7: 製品情報
                        excelFile.UpdateValue(nameSheetDefault, "O13", data_shisaku_cyuui.nm_sample3, 0, true, false);
                        //No 7: 製品情報
                        excelFile.UpdateValue(nameSheetDefault, "W15", "サンプル:" + data_shisaku_cyuui.nm_sample3, 0, true, false);
                        //No 8: サンプルNo
                        excelFile.UpdateValue(nameSheetDefault, "W16", (data_shisaku_cyuui.no_chui3 != null) ? "工程版:" + data_shisaku_cyuui.no_chui3 : null, 0, true, false);
                        //No 9: 殺菌調味液　品コード
                        excelFile.UpdateValue(nameSheetDefault, "W17", (data_shisaku_cyuui.no_chui3 != null) ? data_shisaku_cyuui.chuijiko3 : null, 0, true, false);
                        //No 25: 水相(g)
                        excelFile.UpdateValue(nameSheetDefault, "O136", data_shisaku_cyuui.zyusui3 != null ? data_shisaku_cyuui.zyusui3.ToString() : "0", 0, false, false);
                        //No 26: 油相(g)
                        excelFile.UpdateValue(nameSheetDefault, "O137", data_shisaku_cyuui.zyuabura3 != null ? data_shisaku_cyuui.zyuabura3.ToString() : "0", 0, false, false);
                        //No 27: 比重
                        excelFile.UpdateValue(nameSheetDefault, "O138", data_shisaku_cyuui.hiju3 != null ? data_shisaku_cyuui.hiju3.ToString() : "0", 0, false, false);

                        //NO 38:
                        excelFile.UpdateValue(nameSheetDefault, "O162", data_shisaku_cyuui.buturyo3, 0, true, false);
                        //NO 39:
                        excelFile.UpdateValue(nameSheetDefault, "O163", data_shisaku_cyuui.dt_hatubai3, 0, true, false);
                        //NO 40:
                        excelFile.UpdateValue(nameSheetDefault, "O165", data_shisaku_cyuui.genka3, 0, true, false);
                        //NO 41:
                        excelFile.UpdateValue(nameSheetDefault, "O166", data_shisaku_cyuui.baika3, 0, true, false);
                    }
                }

                //Get data tr_shisaku_list, tr_haigo join ma_literal
                var haigo_shisaku_list = context.sp_shohinkaihatsu_search_107_haigo_shisaku_list(data.cd_shain, data.nen, data.no_oi).ToList();

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
                int c = 0;
                foreach (var sort_kotei in sort_kotei_list)
                {
                    if (sort_kotei.kotei == 1)
                    {
                        temp.Add(sort_kotei.sort_kotei);
                        a += sort_kotei.count_sort_kotei;
                        if (a > 39)
                        {
                            break;
                        }
                    }
                    else if (sort_kotei.kotei == 2)
                    {
                        temp.Add(sort_kotei.sort_kotei);
                        b += sort_kotei.count_sort_kotei;
                        if (b > 37)
                        {
                            break;
                        }
                    }
                    else if (sort_kotei.kotei == 3)
                    {
                        temp.Add(sort_kotei.sort_kotei);
                        c += sort_kotei.count_sort_kotei;
                        if (c > 39)
                        {
                            break;
                        }
                    }
                }


                int index1 = 16;
                int index2 = 57;
                int index3 = 96;

                foreach (var sort_kotei in temp)
                {
                    //Get data tr_shisaku_list, tr_haigo join ma_literal with ma_literal.value1 = 1 and with ma_literal.value1 = 1 , count = 39
                    var values1 = haigo_shisaku_list.Where(x => x.kotei == 1 && x.sort_kotei == sort_kotei).Take(39).ToList();
                    //Get data tr_shisaku_list, tr_haigo join ma_literal with ma_literal.value1 = 1 and with ma_literal.value1 = 2 , count = 37
                    var values2 = haigo_shisaku_list.Where(x => x.kotei == 2 && x.sort_kotei == sort_kotei).Take(37).ToList();
                    //Get data tr_shisaku_list, tr_haigo join ma_literal with ma_literal.value1 = 1 and with ma_literal.value1 = 3 , count = 39
                    var values3 = haigo_shisaku_list.Where(x => x.kotei == 3 && x.sort_kotei == sort_kotei).Take(39).ToList();

                    if (values1.Count() != 0)
                    {
                        foreach (var values in values1)
                        {
                            if (index1 == 55)
                                break;

                            //Insert data to cell 10, 11, 12, 13, 14
                            //No 10: 殺菌調味液　品コード
                            excelFile.UpdateValue(nameSheetDefault, "D" + index1, values.cd_genryo, 0, true, false);
                            //No 11: 殺菌調味液　原材料名
                            excelFile.UpdateValue(nameSheetDefault, "E" + index1, values.nm_genryo, 0, true, false);
                            //No 12: 殺菌調味液　歩留
                            excelFile.UpdateValue(nameSheetDefault, "F" + index1, values.tanka != null ? values.tanka.ToString() : "0", 0, false, false);
                            //No 13: 殺菌調味液　配合量
                            excelFile.UpdateValue(nameSheetDefault, "G" + index1, values.budomari != null ? (values.budomari / 100).ToString() : "0", 0, false, false);
                            //No 14: 殺菌調味液　配合量
                            excelFile.UpdateValue(nameSheetDefault, "H" + index1, values.quantity1 != null ? values.quantity1.ToString() : "0", 0, false, false);
                            if (values.isSelect2 != null)
                            {
                                //No 14: 殺菌調味液　配合量
                                excelFile.UpdateValue(nameSheetDefault, "K" + index1, values.quantity2 != null ? values.quantity2.ToString() : "0", 0, false, false);
                            }
                            if (values.isSelect3 != null)
                            {
                                //No 14: 殺菌調味液　配合量
                                excelFile.UpdateValue(nameSheetDefault, "N" + index1, values.quantity3 != null ? values.quantity3.ToString() : "0", 0, false, false);
                            }

                            index1++;
                        }

                    }

                    if (values2.Count() != 0)
                    {
                        //int index = 57;
                        foreach (var values in values2)
                        {
                            if (index2 == 94)
                                break;

                            //Insert data to cell 15, 16, 17, 18, 19:

                            //No 15: 水相　品コード
                            excelFile.UpdateValue(nameSheetDefault, "D" + index2, values.cd_genryo, 0, true, false);
                            //No 16: 水相　原材料名
                            excelFile.UpdateValue(nameSheetDefault, "E" + index2, values.nm_genryo, 0, true, false);
                            //No 17: 水相　単価
                            excelFile.UpdateValue(nameSheetDefault, "F" + index2, values.tanka != null ? values.tanka.ToString() : "0", 0, false, false);
                            //No 18: 水相　歩留
                            excelFile.UpdateValue(nameSheetDefault, "G" + index2, values.budomari != null ? (values.budomari / 100).ToString() : "0", 0, false, false);
                            //No 19: 水相　配合量
                            excelFile.UpdateValue(nameSheetDefault, "H" + index2, values.quantity1 != null ? values.quantity1.ToString() : "0", 0, false, false);
                            if (values.isSelect2 != null)
                            {
                                //No 19: 水相　配合量
                                excelFile.UpdateValue(nameSheetDefault, "K" + index2, values.quantity2 != null ? values.quantity2.ToString() : "0", 0, false, false);
                            }
                            if (values.isSelect3 != null)
                            {
                                //No 19: 水相　配合量
                                excelFile.UpdateValue(nameSheetDefault, "N" + index2, values.quantity3 != null ? values.quantity3.ToString() : "0", 0, false, false);
                            }

                            index2++;
                        }
                    }

                    if (values3.Count() != 0)
                    {
                        //int index = 96;
                        foreach (var values in values3)
                        {
                            if (index3 == 135)
                                break;

                            //Insert data to cell 20, 21, 22, 23, 24:
                            if ((values.quantity1 != null && values.quantity1 != 0) || (values.quantity2 != null && values.quantity2 != 0) || (values.quantity3 != null && values.quantity3 != 0))
                            {
                                //No 20: ドレ２液油相　品コード
                                excelFile.UpdateValue(nameSheetDefault, "D" + index3, values.cd_genryo, 0, true, false);
                                //No 21: ドレ２液油相　原材料名
                                excelFile.UpdateValue(nameSheetDefault, "E" + index3, values.nm_genryo, 0, true, false);
                                //No 22: ドレ２液油相　単価
                                excelFile.UpdateValue(nameSheetDefault, "F" + index3, values.tanka != null ? values.tanka.ToString() : "0", 0, false, false);
                                //No 23: ドレ２液油相　歩留
                                excelFile.UpdateValue(nameSheetDefault, "G" + index3, values.budomari != null ? (values.budomari / 100).ToString() : "0", 0, false, false);
                                //No 24: ドレ２液油相　配合量
                                excelFile.UpdateValue(nameSheetDefault, "H" + index3, values.quantity1 != null ? values.quantity1.ToString() : "0", 0, false, false);
                                if (values.isSelect2 != null)
                                {
                                    //No 24: ドレ２液油相　配合量
                                    excelFile.UpdateValue(nameSheetDefault, "K" + index3, values.quantity2 != null ? values.quantity2.ToString() : "0", 0, false, false);
                                }
                                if (values.isSelect3 != null)
                                {
                                    //No 24: ドレ２液油相　配合量
                                    excelFile.UpdateValue(nameSheetDefault, "N" + index3, values.quantity3 != null ? values.quantity3.ToString() : "0", 0, false, false);
                                }

                                index3++;
                            }
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
    /// Chōmiryō genka shisan-hyō
    public class ChomiryoGenkaShisanHyoExcelCondition
    {
        public decimal cd_shain { get; set; }
        public decimal nen { get; set; }
        public decimal no_oi { get; set; }
        public List<int> sample { get; set; }
    }

    #endregion

}
