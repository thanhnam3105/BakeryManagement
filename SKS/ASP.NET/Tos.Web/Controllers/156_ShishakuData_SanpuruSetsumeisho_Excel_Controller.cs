using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Web;
using System.Net.Http;
using System.Web.Http;
using System.Web.Http.OData;
using System.Web.Http.OData.Query;
using Tos.Web.Data;
using System.IO;
using Tos.Web.Controllers.Helpers;
using ClosedXML.Excel;
using DocumentFormat.OpenXml.Spreadsheet;
using DocumentFormat.OpenXml.Packaging;
using System.Data.Entity.Infrastructure;
using Tos.Web.Properties;

namespace Tos.Web.Controllers
{
    //created from 【SearchInputController(Ver1.7)】 Template
    public class _156_ShishakuData_SanpuruSetsumeisho_Excel_Controller : ApiController
    {
        /// <summary>
        /// 検索条件に一致するデータを抽出しExcelを作成します。（Downloadフォルダに実体ファイル作成後、パスを返却）
        /// </summary>
        /// <param name="options">検索条件</param>
        /// <returns>作成されたExcelファイルのパス</returns>
        public HttpResponseMessage Get([FromUri] ParaDownLoad156 value)
        {

            //該当データが存在していること
            if (!isNotExitShisa(value))
            {
                return Request.CreateResponse<string>(HttpStatusCode.BadRequest, "AP0007");
            }
            // 読込テンプレートファイルパス
            string templatepath = HttpContext.Current.Server.MapPath(Properties.Settings.Default.ExcelTemplateFolder);
            string dirTemlates = templatepath + "\\" + Properties.Resources.SanpuruSetsumeisho156Excel + Properties.Resources.ExcelXlsmExtension;
            string filename = Properties.Resources.SanpuruSetsumeisho156Excel;
            var userInfo = Tos.Web.Data.UserInfo.CreateFromAuthorityMaster(this.User.Identity);
            decimal id_user = userInfo.EmployeeCD;

            using (ShohinKaihatsuEntities context = new ShohinKaihatsuEntities())
            {
                context.Configuration.ProxyCreationEnabled = false;
                //set timeout
                ((IObjectContextAdapter)context).ObjectContext.CommandTimeout = int.Parse(Settings.Default.TimeOutCSV_200);


                //List<Sheet> sheets = excelFile.GetAllSheets();
                List<string> sheets = new List<string>();
                sheets.Add("１");
                sheets.Add("２");
                sheets.Add("３");
                sheets.Add("４");

                List<short> seqDownload = context.tr_shisaku_bf.Where(m => m.cd_shain == value.cd_shain
                                                            && m.nen == value.nen
                                                            && m.no_oi == value.no_oi
                                                            && m.flg_print == 1).OrderBy(m => m.sort_shisaku).Select(m => m.seq_shisaku).ToList();
                foreach (var item in seqDownload)
                {
                    //var isError = context.sp_shohinkaihatsu_check_data_105(value.cd_shain, value.nen, value.no_oi, item).FirstOrDefault();
                    var isError = context.sp_shohinkaihatsu_check_data_156(value.cd_shain, value.nen, value.no_oi, item).FirstOrDefault();
                    if (isError == null)
                    {
                        return Request.CreateResponse<string>(HttpStatusCode.BadRequest, "AP0007");
                    }
                }

                //List<sp_shohinkaihatsu_search_105_sanpuru_setsumeisho_Result> results = GetData105(value, context);
                List<sp_shohinkaihatsu_search_156_sanpuru_setsumeisho_Result> results = GetData156(value, context);
                
                // 保存Excelファイル名（ユーザID_download_現在日時.xlsx）
                //string name = UserInfo.GetUserNameFromIdentity(this.User.Identity);
                var shain_nen_oi = value.cd_shain.ToString().PadLeft(10, '0') + "-"
                                    + value.nen.ToString().PadLeft(2, '0') + "-"
                                    + value.no_oi.ToString().PadLeft(3, '0');
                string excelname = filename + "_" + shain_nen_oi + "_" + id_user + "_" + DateTime.Now.ToString("yyyyMMdd_HHmmss") + Properties.Resources.ExcelXlsmExtension;



                ExcelFile excelFile = new ExcelFile(dirTemlates);

                // Excelテンプレートを読み込み、必要な情報をマッピングしてクライアントへ返却
                MemoryStream stream = EditExcelFileByClosedXML(excelFile, sheets, results, seqDownload);

                return FileUploadDownloadUtility.CreateFileResponse(stream, excelname);
            }


        }

        /// <summary>
        /// 該当データが存在していること
        /// </summary>
        /// <returns></returns>
        public bool isNotExitShisa(ParaDownLoad156 options)
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
        /// Excelファイルの作成（ClosedXML使用）
        /// </summary>
        /// <param name="dirTemlates">検索条件</param>
        /// <param name="data">バインドデータ</param>
        /// <returns>作成されたExcelファイルのMemoryStream</returns>
        private MemoryStream EditExcelFileByClosedXML(ExcelFile excelFile, List<string> sheets, IEnumerable<sp_shohinkaihatsu_search_156_sanpuru_setsumeisho_Result> results, List<short> seqDownload)
        {
            var k = 0;
            var sheetName3 = "サンプル説明書";
            var userInfo = Tos.Web.Data.UserInfo.CreateFromAuthorityMaster(this.User.Identity);

            foreach (var no_seq in seqDownload)
            {
                //Data write to Excel file.
                var data = results.Where(m => m.seq_shisaku == no_seq).OrderBy(m=>m.sort_kotei).ThenBy(m=>m.sort_genryo).Take(150).ToList();
                
                var itemHeader = data.FirstOrDefault();
                var sheetName1 = "原料情報" + sheets[k];
                excelFile.UpdateValue(sheetName1, "B1", itemHeader.nm_hin, 0, true, false);
                excelFile.UpdateValue(sheetName1, "B2", itemHeader.nm_shain_nen_sample, 0, true, false);
                var no_row = 4;
                UInt32 styleIndexA = 0;
                UInt32 styleIndexC = 0;
                UInt32 styleIndexD = 0;
                UInt32 styleIndexTotal = 0;

                foreach (var itemDetail in data)
                {
                    styleIndexA = excelFile.SetAlignmentStyle(sheetName1, "A" + no_row, "Right", "Center", false, false);
                    styleIndexC = excelFile.SetAlignmentStyle(sheetName1, "C" + no_row, "Right", "Center", false, false);
                    styleIndexD = excelFile.SetAlignmentStyle(sheetName1, "D" + no_row, "Right", "Center", false, false);

                    excelFile.UpdateValue(sheetName1, "A" + no_row, itemDetail.cd_genryo, styleIndexA, true, false);
                    excelFile.UpdateValue(sheetName1, "B" + no_row, itemDetail.nm_genryo, 0, true, false);

                    //change quantity --> quantity_kanzan
                    if (itemDetail.quantity_kanzan != null) {
                        string quantity_kanzan = itemDetail.quantity_kanzan.ToString();
                        var listNuber = quantity_kanzan.Split('.');
                        int keta_shosu = int.Parse(itemDetail.keta_shosu == null ? "0" : itemDetail.keta_shosu.ToString());
                        if (keta_shosu == 0 || listNuber.Length == 1)
                        {
                            itemDetail.quantity_kanzan = decimal.Parse(listNuber[0]);
                        }
                        else
                        {
                            listNuber[1] = listNuber[1] + "0000";
                            itemDetail.quantity_kanzan = decimal.Parse(listNuber[0] + "." + listNuber[1].Substring(0, keta_shosu));
                        }
                    }
                    excelFile.UpdateValue(sheetName1, "C" + no_row, itemDetail.quantity_kanzan.ToString(), styleIndexC, true, false);
                    string budomari = "";
                    if (itemDetail.budomari == 0 || itemDetail.budomari == null)
                    {
                        budomari = "0.000";
                    }
                    else {
                        string isNumberBudomari = itemDetail.budomari.ToString();
                        var listBudomari = isNumberBudomari.Split('.');
                        budomari = listBudomari[0] + "." + listBudomari[1].PadRight(3, '0');
                    }
                    excelFile.UpdateValue(sheetName1, "D" + no_row, budomari, styleIndexD, true, false);
                    excelFile.UpdateValue(sheetName1, "E" + no_row, itemDetail.hyojian, 0, true, false);
                    excelFile.UpdateValue(sheetName1, "F" + no_row, itemDetail.RMLABELSAMPLE, 0, true, false);
                    excelFile.UpdateValue(sheetName1, "G" + no_row, itemDetail.tenkabutu, 0, true, false);

                    //excelFile.UpdateValue(sheetName1, "H" + no_row, itemDetail.AMS_YO, 0, true, false);
                    //excelFile.UpdateValue(sheetName1, "I" + no_row, itemDetail.AMS_FUYO, 0, true, false);
                    //excelFile.UpdateValue(sheetName1, "J" + no_row, itemDetail.ALG_ALR, 0, true, false);
                    //excelFile.UpdateValue(sheetName1, "K" + no_row, itemDetail.ALG_FUYO, 0, true, false);

                    excelFile.UpdateValue(sheetName1, "H" + no_row, itemDetail.AMSNM_1, 0, true, false);
                    excelFile.UpdateValue(sheetName1, "I" + no_row, itemDetail.AMSNM_2, 0, true, false);
                    excelFile.UpdateValue(sheetName1, "J" + no_row, itemDetail.ALG_NM_1, 0, true, false);
                    excelFile.UpdateValue(sheetName1, "K" + no_row, itemDetail.ALG_NM_2, 0, true, false);

                    //var no_eiyo = (itemDetail.no_eiyo1 == "" ? "" : itemDetail.no_eiyo1)
                    //            + (itemDetail.no_eiyo2 == "" ? "" : ("," + itemDetail.no_eiyo2))
                    //            + (itemDetail.no_eiyo3 == "" ? "" : ("," + itemDetail.no_eiyo3))
                    //            + (itemDetail.no_eiyo4 == "" ? "" : ("," + itemDetail.no_eiyo4))
                    //            + (itemDetail.no_eiyo5 == "" ? "" : ("," + itemDetail.no_eiyo5));
                    //excelFile.UpdateValue(sheetName1, "L" + no_row, no_eiyo, 0, true, false);
                    excelFile.UpdateValue(sheetName1, "L" + no_row, itemDetail.memo, 0, true, false);

                    no_row++;
                }
                if (itemHeader.quantityTotal != null)
                {
                    string quantityTotal = itemHeader.quantityTotal.ToString();
                    var listNuber = quantityTotal.Split('.');
                    int keta_shosu = int.Parse(itemHeader.keta_shosu == null ? "0" : itemHeader.keta_shosu.ToString());

                    if (keta_shosu == 0 || listNuber.Length == 1)
                    {
                        itemHeader.quantityTotal = decimal.Parse(listNuber[0]);
                    }
                    else
                    {
                        listNuber[1] = listNuber[1] + "0000";
                        itemHeader.quantityTotal = decimal.Parse(listNuber[0] + "." + listNuber[1].Substring(0, keta_shosu));
                    }
                }
                styleIndexTotal = excelFile.SetAlignmentStyle(sheetName1, "C104", "Right", "Center", false, false);
                excelFile.UpdateValue(sheetName1, "B155", "合計重量(Kg)", 0, true, false);
                excelFile.UpdateValue(sheetName1, "C155", itemHeader.quantityTotal.ToString(), styleIndexTotal, true, false);

                excelFile.SaveSheet(sheetName1);

                //Bind sheet other
                var sheetName2 = "表示案" + sheets[k];

                excelFile.UpdateValue(sheetName2, "G59", itemHeader.juryo_shiagari_g.ToString(), 0, true, false);
                excelFile.UpdateValue(sheetName2, "G64", itemHeader.nm_toriatukaiondo, 0, true, false);
                excelFile.UpdateValue(sheetName2, "I64", itemHeader.nm_busho, 0, true, false);
                excelFile.UpdateValue(sheetName2, "S64", itemHeader.nm_team + itemHeader.nm_user, 0, true, false);
                //excelFile.UpdateValue(sheetName2, "U64", itemHeader.nm_user, 0, true, false);
                excelFile.UpdateValue(sheetName2, "G65", itemHeader.shomikikan + (itemHeader.nm_shomikikan_tani == null ? "" : itemHeader.nm_shomikikan_tani), 0, true, false);
                excelFile.UpdateValue(sheetName2, "I65", itemHeader.youki, 0, true, false);
                excelFile.UpdateValue(sheetName2, "L65", (itemHeader.yoryo == null ? "" : itemHeader.yoryo) + (itemHeader.nm_tani == null ? "" : itemHeader.nm_tani), 0, true, false);
                excelFile.UpdateValue(sheetName2, "H66", itemHeader.no_seiho1, 0, true, false);
                excelFile.UpdateValue(sheetName2, "H67", itemHeader.nm_shain_nen_oi, 0, true, false);

                excelFile.SaveSheet(sheetName2);
                k++;
                string address = getAddressSwitchCase(k);
                
                if (k == 1) {
                    
                    excelFile.UpdateValue(sheetName3, "F3", itemHeader.nm_team, 0, true, false);
                    excelFile.UpdateValue(sheetName3, "F4", itemHeader.nm_user, 0, true, false);
                }
                //Brix
                excelFile.UpdateValue(sheetName3, address + "67", itemHeader.su_brix.ToString(), 0, false, false, "0.0");
                excelFile.UpdateValue(sheetName3, address + "68", itemHeader.su_ph.ToString(), 0, true, false);
                excelFile.UpdateValue(sheetName3, address + "69", itemHeader.su_ca.ToString(), 0, true, false);
                excelFile.SaveSheet(sheetName3);
            }

            excelFile.SaveWorkbook();

            return (MemoryStream)excelFile.Stream;
        }

        /// <summary>
        /// Get data sheets in excel template
        /// </summary>
        /// <param name="dirTemlates">検索条件</param>
        /// <param name="data">バインドデータ</param>
        /// <returns>作成されたExcelファイルのMemoryStream</returns>
        public List<Sheet> GetAllSheets(SpreadsheetDocument spTemplateDoc)
        {
            List<Sheet> sheets = new List<Sheet>();

            WorkbookPart wbPart = spTemplateDoc.WorkbookPart;
            //Get all sheet name
            sheets = wbPart.Workbook.Descendants<Sheet>().ToList();

            return sheets;
        }
        public string getAddressSwitchCase(int number)
        {
            string address = "";
            switch (number)
            {
                case 1:
                    address = "D";
                    break;
                case 2:
                    address = "H";
                    break;
                case 3:
                    address = "L";
                    break;
                case 4:
                    address = "P";
                    break;
            }
            return address;
        }

        /// <summary>
        /// Get data download
        /// </summary>
        /// <returns>List data</returns>
        public List<sp_shohinkaihatsu_search_156_sanpuru_setsumeisho_Result> GetData156(ParaDownLoad156 value, ShohinKaihatsuEntities context)
        {
            List<sp_shohinkaihatsu_search_156_sanpuru_setsumeisho_Result> results = context.sp_shohinkaihatsu_search_156_sanpuru_setsumeisho(value.cd_shain, value.nen, value.no_oi).ToList();

            return results;
        }
    }




    #region "ControllerのAPIで利用するリクエスト・レスポンスクラスです"

    public class ParaDownLoad156
    {
        public decimal cd_shain { get; set; }
        public decimal nen { get; set; }
        public decimal no_oi { get; set; }
        public List<int> sample { get; set; }
        public int mode { get; set; }
    }
    #endregion
}
