using ClosedXML.Excel;
using DocumentFormat.OpenXml;
using DocumentFormat.OpenXml.Packaging;
using DocumentFormat.OpenXml.Spreadsheet;
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Net.Http;
using System.Web;
using System.Web.Http;
using Tos.Web.Controllers.Helpers;
using Tos.Web.Data;

using System.Runtime.InteropServices;
using System.Dynamic;
using System.Text.RegularExpressions;

namespace Tos.Web.Controllers
{
    public class _104_ShishakuData_HaigoHyo_Excel_Controller : ApiController
    {
        #region "Controllerで公開するAPI"

        /// <summary>
        /// Download Excel when click btn excel download
        /// </summary>
        /// <param name="Conditions">検索条件</param>
        /// <returns>Excel file</returns>
        [HttpGet]
        public HttpResponseMessage Get([FromUri] HaigoHyoExcelCondition Conditions)
        {
            // 読込テンプレートファイルパス
            string templatepath = HttpContext.Current.Server.MapPath(Properties.Settings.Default.ExcelTemplateFolder);
            //string dirTemlates = templatepath + "\\" + Properties.Resources.BunshoTemplateExcelName + Properties.Resources.ExcelExtension;
            string dirTemlates = templatepath + "\\" + Properties.Resources.ShisakuHaigoHyoTemplateExcel + Properties.Resources.ExcelXlsmExtension;
            // 保存Excelファイル名 (製法文書_[製法番号]_[yyyyMMddhhmmss].xlsx)
            string excelname = Properties.Resources.ShisakuHaigoHyoOutputExcel + "_{0}-{1}-{2}_{3}_" + DateTime.Now.ToString("yyyyMMdd_HHmmss") + Properties.Resources.ExcelXlsmExtension;
            var userInfo = Tos.Web.Data.UserInfo.CreateFromAuthorityMaster(this.User.Identity);
            Conditions.id_user = userInfo.EmployeeCD;
            Conditions.nm_user = userInfo.Name;

            // Excelテンプレートを読み込み、必要な情報をマッピングしてクライアントへ返却
            HaigoHyoExcel HaigoHyoExcel = EditExcelFile(dirTemlates, Conditions);
            string cd_shain = CommonController.getFullString(Conditions.cd_shain.ToString(), 10);
            string nen = CommonController.getFullString(Conditions.nen.ToString(), 2);
            string no_oi = CommonController.getFullString(Conditions.no_oi.ToString(), 3);
            excelname = string.Format(excelname, cd_shain, nen, no_oi, Conditions.id_user.ToString());
            return FileUploadDownloadUtility.CreateFileResponse(HaigoHyoExcel.getStream(), excelname);
        }

        /// <summary>
        /// Excelファイルの作成（ClosedXML使用）
        /// </summary>
        /// <param name="dirTemlates">検索条件</param>
        /// <returns>作成されたExcelファイルのMemoryStream</returns>
        private HaigoHyoExcel EditExcelFile(string dirTemlates, HaigoHyoExcelCondition Conditions)
        {
            HaigoHyoExcel HaigoHyoExcel = new HaigoHyoExcel();
            HaigoHyoExcel.getExcel(dirTemlates, Conditions);
            return HaigoHyoExcel;
        }
        #endregion

    }

    #region "OOP contructions - Base Excel objects"
    
    /// <summary>
    /// Base contruct for excel-cell
    /// </summary>
    public class HaigoHyoCell
    {
        // Data type
        public static string NUMBER_TYPE = "number";
        public static string DATE_TYPE = "date";
        public static string DATETIME_TYPE = "date_time";
        public static string STRING_TYPE = "string";
        public static string INDEX_IDENTIFY = "index";
        // Cell base informations
        public string property { get; set; }
        public string type { get; set; }
        public object defaultNull { get; set; }
        public string toStringFormat { get; set; }
        public int? fixedLength { get; set; }
        public int? trailingZero { get; set; }
        public string format { get; set; }
        public string align { get; set; }
        public int Col { get; set; }
        public int Row { get; set; }
        public string position { get; set; }
        // Set value to cell
        public void setValue(object data, ExcelFile Excel, string sheetName, int distanceCol = 0, int distanceRow = 0, bool isAutoGetValue = true)
        {
            if (Excel == null || data == null)
            {
                return;
            }
            // For single data binding
            HaigoHyoCell bindPosition = this;
            // For array data binding
            if (distanceCol != 0 || distanceRow != 0)
            {
                bindPosition = this.getNextPosition(distanceCol, distanceRow);
            }
            object value = null;
            // Get target data
            // Auto index, only use for array data
            if (this.type == INDEX_IDENTIFY)
            {
                value = distanceRow + 1;
            }
            // Get value in the object data
            else
            {
                if (isAutoGetValue)
                {
                    value = data.GetType().GetProperty(this.property).GetValue(data, null);
                }
                else
                {
                    value = data;
                }
            }
            // Get default data when the data is null
            if (value == null)
            {
                value = defaultNull;
            }
            string strValue = string.Empty;
            // Convert data base on cell format
            if (value != null)
            {
                if (type == DATE_TYPE)
                {
                    strValue = ((DateTime)value).ToString("yyyy/MM/dd");
                }
                else if (type == DATETIME_TYPE)
                {
                    strValue = ((DateTime)value).ToString("yyyy/MM/dd hh:mm:ss");
                }
                else
                {
                    strValue = value.ToString();
                }
                // Auto fill '0'
                if (this.fixedLength != null)
                {
                    strValue = CommonController.getFullString(strValue, fixedLength);
                }
                // Auto trailing '0'
                if (this.trailingZero != null)
                {
                    //strValue = CommonController.addTrailingZero(strValue, trailingZero);
                }
                UpdateValue(Excel, sheetName, bindPosition, strValue);
            }
        }
        // update value to a cell in work sheet
        private void UpdateValue(ExcelFile Excel, string sheetName, HaigoHyoCell position, string value)
        {
            //var Cell = Excel.Worksheet(sheetName).Cell(position.Row, position.Col);
            //if (this.type != NUMBER_TYPE)
            //{
            //    // Update data type
            //    Cell.Style.NumberFormat.Format = "@";
            //}
            //else
            //{
            //    if (format != null)
            //    {
            //        Cell.Style.NumberFormat.Format = format;
            //    }
            //}
            // Update value
            //Cell.Value = value;
            Excel.UpdateValue(sheetName, position.position, value ?? "", 0, this.type != NUMBER_TYPE, false, this.format, this.align);
        }
        // Get position with y distance
        public HaigoHyoCell getNextPosition(int distanceCol, int distanceRow)
        {
            HaigoHyoCell result = new HaigoHyoCell();
            DataCopier.ReFill(this, result);
            result.Row += distanceRow;
            result.Col += distanceCol;
            result.ColumnNumberToName();
            return result;
        }
        // Return the column name for this column number.
        public void ColumnNumberToName()
        {
            int dividend = this.Col;
            string columnName = String.Empty;
            int modulo;

            while (dividend > 0)
            {
                modulo = (dividend - 1) % 26;
                columnName = Convert.ToChar(65 + modulo).ToString() + columnName;
                dividend = (int)((dividend - modulo) / 26);
            }

            this.position = columnName + this.Row.ToString();
        }
        // Return the column number for this column name.
        public void ColumnNameToNumber()
        {
            this.Col = 0;
            string Row = ""; 
            // Process each letter.
            for (int i = 0; i < this.position.Length; i++)
            {
                if (this.position[i] < 'A' || this.position[i] > 'Z')
                {
                    Row += this.position[i];
                    continue;
                }
                this.Col *= 26;
                char letter = this.position[i];
                // See if it's out of bounds.
                if (letter < 'A') letter = 'A';
                if (letter > 'Z') letter = 'Z';
                // Add in the value of this letter.
                this.Col += (int)letter - (int)'A' + 1;
            }
            this.Row = Int32.Parse(Row);
        }
        // Base contruction
        public HaigoHyoCell() { }
        // Adv contruction
        public HaigoHyoCell(string property, string position, string type = null, string format = null, object defaultNull = null) 
        {
            this.property = property;
            this.position = position;
            this.type = type;
            this.format = format;
            this.defaultNull = defaultNull;
            this.ColumnNameToNumber();
        }
    }



    /// <summary>
    /// Base contruct for excel
    /// </summary>
    public class HaigoHyoExcel
    {
        // Const
        private const short FLG_TRUE = 1;
        private const string SHEETNAME = "試作表";
        // Sort seq
        private const int SUM_QTA_KOTEI_SORT_SEQ = 30000;
        private const int SUM_QTA_SORT_SEQ = 30001;
        private const int INP_QTA_KOTEI_SORT_SEQ = 30002;
        private const int MAX_SORT_SEQ = 30009;
        // Begin index
        private const int SUM_QTA_KOTEI_BEG_IND = 160;
        private const int SUM_QTA_BEG_IND = 236;
        private const int INP_QTA_KOTEI_BEG_IND = 312; 
        // Numbering format index code
        private const string ONE_POINT = "0.0";
        private const string TWO_POINT = "0.00";
        private const string THREE_POINT = "0.000";
        private const string FOUR_POINT = "0.0000";

        // Main work book
        //public XLWorkbook Excel { get; set; }
        ExcelFile Excel { get; set; }
        private MemoryStream stream { get; set; }
        // Base excel param
        public HaigoHyoExcelCondition Conditions { get; set; }
        // Temporary file name: Now.getTicks
        private string tempFileName { get; set; }
        // Temporary download file path
        private string tempDownload { get; set; }
        // Shisaku hin data
        private tr_shisakuhin_EXT ShisakuHinData { get; set; }
        // tr_cyuui
        private List<tr_cyuui> cyuuiData { get; set; }
        // Shisaku data
        private List<tr_shisaku> ShisakuData { get; set; }
        // Genryo data
        private List<tr_genryo> GenryoData { get; set; }
        // Shisaku list data
        private List<sp_shohinkaihatsu_search_104_shisakulist_Result> ShisakuListData { get; set; }
        // Kotei list literal
        private List<sp_shohinkaihatsu_search_104_kotei_Result> KoteiListLiteral { get; set; }
        // List of seq to print
        private List<int> PrintSeqList { get; set; }
        // ShisakuHin mapping list
        private List<HaigoHyoCell> ShisakuHinMapping { get; set; }
        // Shisaku mapping list
        private List<HaigoHyoCell> ShisakuMapping { get; set; }
        // Shisaku list mapping list
        private List<HaigoHyoCell> ShisakuListMapping { get; set; }
        // Shisaku data with the begin index
        private int shisakuDataBegIndex { get; set; }
        // shisaku list format number
        private string formatNumber { get; set; }
        // shisaku property index
        private int shisa_prop_index { get; set; }
        // Haigo Kyodo Name
        private List<HaigoKyodoName> haigoKyodoName { get; set; }
        // Get no_shisaku
        public string getNoShisaku()
        {
            if (Conditions == null)
            {
                return String.Empty;
            }
            string cd_shain = CommonController.getFullString(Conditions.cd_shain.ToString(), 10);
            string nen = CommonController.getFullString(Conditions.nen.ToString(), 2);
            string no_oi = CommonController.getFullString(Conditions.no_oi.ToString(), 3);
            return String.Format("{0}-{1}-{2}", cd_shain, nen, no_oi);
        }
        // Get shisakuhin data
        private void getShisakuHinData()
        {
            using (ShohinKaihatsuEntities context = new ShohinKaihatsuEntities())
            {
                var ShisakuHinData = context.sp_shohinkaihatsu_search_104_shisakuhin(Conditions.cd_shain, Conditions.nen, Conditions.no_oi).FirstOrDefault();
                this.ShisakuHinData = new tr_shisakuhin_EXT();
                DataCopier.ReFill(ShisakuHinData, this.ShisakuHinData);
                // System date
                this.ShisakuHinData.sys_date = DateTime.Now.ToString("yyyy/MM/dd hh:mm");
                // No-shisaku
                this.ShisakuHinData.no_shisaku = this.getNoShisaku();
                // get shisaku list number format
                //string keta_shosu = CommonController.getFullString((ShisakuHinData.keta_shosu ?? 0).ToString(), 3);
                //var trailingZeroLength = context.ma_literal.Where(x => x.cd_category == "K_shosu" && x.cd_literal == keta_shosu).Select(x => x.nm_literal).FirstOrDefault();
                string keta_shosu = (ShisakuHinData.keta_shosu ?? 0).ToString();
                this.formatNumber = CommonController.addTrailingZero("0", Int32.Parse(keta_shosu ?? "0"));
            }
        }
        // Get shisaku data
        private void getShisakuData()
        {
            using (ShohinKaihatsuEntities context = new ShohinKaihatsuEntities())
            {
                this.ShisakuData = context.tr_shisaku.Where(x => x.cd_shain == Conditions.cd_shain && x.nen == Conditions.nen && x.no_oi == Conditions.no_oi && x.flg_print == FLG_TRUE).OrderBy(x => x.sort_shisaku).ToList();
                // Get print list
                this.PrintSeqList = new List<int>();
                foreach (var shisaku in ShisakuData)
                {
                    PrintSeqList.Add(shisaku.seq_shisaku);
                    if (shisaku.oilmustard != null)
                    {
                        shisaku.oilmustard = Math.Round(shisaku.oilmustard ?? 0, 0, MidpointRounding.AwayFromZero);
                    }
                }
                this.GenryoData = context.tr_genryo.Where(x => x.cd_shain == Conditions.cd_shain && x.nen == Conditions.nen && x.no_oi == Conditions.no_oi && PrintSeqList.Contains(x.seq_shisaku)).ToList();
            }
        }
        // Get shisaku list data
        private void getShisakuListData()
        {
            using (ShohinKaihatsuEntities context = new ShohinKaihatsuEntities())
            {
                this.ShisakuListData = context.sp_shohinkaihatsu_search_104_shisakulist(Conditions.cd_shain, Conditions.nen, Conditions.no_oi).ToList();
            }
        }
        // Get tr_cyuui base on shisaku data
        private tr_cyuui getCyuuiData(tr_shisaku data)
        {
            if (data.no_chui == null)
            {
                return null;
            }
            using (ShohinKaihatsuEntities context = new ShohinKaihatsuEntities())
            {
                return context.tr_cyuui.Where(x => x.cd_shain == data.cd_shain && x.nen == data.nen && x.no_oi == data.no_oi && x.no_chui == data.no_chui).FirstOrDefault();
            }
        }
        // Get kotei list literal
        private void getKoteiListLiteral()
        {
            using (ShohinKaihatsuEntities context = new ShohinKaihatsuEntities())
            {
                HaigoHyoCell baseCell = new HaigoHyoCell() { Col = 13, Row = 0 };
                this.KoteiListLiteral = context.sp_shohinkaihatsu_search_104_kotei(Conditions.cd_shain, Conditions.nen, Conditions.no_oi).ToList();
                this.KoteiListLiteral.Add(new sp_shohinkaihatsu_search_104_kotei_Result() { nm_genryo_disp = "合計仕上重量(g)", sort_kotei = MAX_SORT_SEQ });
                this.KoteiListLiteral.Add(new sp_shohinkaihatsu_search_104_kotei_Result() { nm_genryo_disp = "原料費(Kg)", sort_kotei = MAX_SORT_SEQ });
                this.KoteiListLiteral.Add(new sp_shohinkaihatsu_search_104_kotei_Result() { nm_genryo_disp = "原料費(1個)", sort_kotei = MAX_SORT_SEQ }); 
                if (this.ShisakuData.Count > 0)
                {
                    shisa_prop_index = INP_QTA_KOTEI_BEG_IND + 4;
                    string leftAlign = "Left";
                    var ShisakuFirstData = this.ShisakuData.First();
                    // 総酸
                    if (ShisakuFirstData.flg_sousan == 1) 
                    {
                        this.insertLiteral("総酸", "ritu_sousan", baseCell, HaigoHyoCell.NUMBER_TYPE, "0.00");
                    }
                    // 食塩
                    if (ShisakuFirstData.flg_shokuen == 1)
                    {
                        this.insertLiteral("食塩", "ritu_shokuen", baseCell, HaigoHyoCell.NUMBER_TYPE, "0.00");
                    }
                    // 総酸：分析
                    if (ShisakuFirstData.flg_sousan_bunseki == 1)
                    {
                        this.insertLiteral("総酸：分析", "ritu_sousan_bunseki", baseCell);
                    }
                    // 食塩：分析
                    if (ShisakuFirstData.flg_shokuen_bunseki == 1)
                    {
                        this.insertLiteral("食塩：分析", "ritu_shokuen_bunseki", baseCell);
                    }
                    // 水相中酸度：分析
                    if (ShisakuFirstData.flg_sando_bunseki_suiso == 1)
                    {
                        this.insertLiteral("水相中酸度：分析", "ritu_sando_bunseki_suiso", baseCell);
                    }
                    // 水相中食塩：分析
                    if (ShisakuFirstData.flg_shokuen_bunseki_suiso == 1)
                    {
                        this.insertLiteral("水相中食塩：分析", "ritu_shokuen_bunseki_suiso", baseCell);
                    }
                    // Brix
                    if (ShisakuFirstData.flg_Brix == 1)
                    {
                        this.insertLiteral("Brix", "Brix", baseCell, HaigoHyoCell.NUMBER_TYPE, "0.0");
                    }
                    // pH
                    if (ShisakuFirstData.flg_ph == 1)
                    {
                        this.insertLiteral("pH", "ph", baseCell);
                    }
                    // 充填前（クリーム）粘度
                    if (ShisakuFirstData.flg_nendo_cream == 1)
                    {
                        this.insertLiteral("充填前（クリーム）粘度", "nendo_cream", baseCell);
                    }
                    // 充填前（クリーム）粘度測定温度
                    if (ShisakuFirstData.flg_ondo_cream == 1)
                    {
                        this.insertLiteral("充填前（クリーム）粘度測定温度", "ondo_cream", baseCell);
                    }
                    // 充填前（クリーム）粘度ローターNo
                    if (ShisakuFirstData.flg_no_rotor_cream == 1)
                    {
                        this.insertLiteral("充填前（クリーム）粘度ローターNo", "no_rotor_cream", baseCell);
                    }
                    // 充填前（クリーム）粘度スピード
                    if (ShisakuFirstData.flg_speed_cream == 1)
                    {
                        this.insertLiteral("充填前（クリーム）粘度スピード", "speed_cream", baseCell);
                    }
                    // AW
                    if (ShisakuFirstData.flg_AW == 1)
                    {
                        this.insertLiteral("AW", "AW", baseCell, HaigoHyoCell.NUMBER_TYPE, "0.000");
                    }
                    // 粘度（製品）
                    if (ShisakuFirstData.flg_nendo == 1)
                    {
                        this.insertLiteral("粘度（製品）", "nendo", baseCell);
                    }
                    // 粘度（製品）－測定時品温
                    if (ShisakuFirstData.flg_ondo == 1)
                    {
                        this.insertLiteral("粘度（製品）－測定時品温", "ondo", baseCell);
                    }
                    // 粘度（製品）－ローターＮｏ
                    if (ShisakuFirstData.flg_no_rotor == 1)
                    {
                        this.insertLiteral("粘度（製品）－ローターＮｏ", "no_rotor", baseCell);
                    }
                    // 粘度スピード
                    if (ShisakuFirstData.flg_speed == 1)
                    {
                        this.insertLiteral("粘度（製品）－スピード", "speed", baseCell);
                    }
                    // 製品比重
                    if (ShisakuFirstData.flg_hiju == 1)
                    {
                        this.insertLiteral("製品比重", "hiju", baseCell);
                    }
                    // 水相比重
                    if (ShisakuFirstData.flg_hiju_sui == 1)
                    {
                        this.insertLiteral("水相比重", "hiju_sui", baseCell);
                    }
                    // 水相中酸度
                    if (ShisakuFirstData.flg_sando_suiso == 1)
                    {
                        this.insertLiteral("水相中酸度", "sando_suiso", baseCell, HaigoHyoCell.NUMBER_TYPE, "0.00");
                    }
                    // 水相中食塩
                    if (ShisakuFirstData.flg_shokuen_suiso == 1)
                    {
                        this.insertLiteral("水相中食塩", "shokuen_suiso", baseCell, HaigoHyoCell.NUMBER_TYPE, "0.00");
                    }
                    // 水相中酢酸
                    if (ShisakuFirstData.flg_sakusan_suiso == 1)
                    {
                        this.insertLiteral("水相中酢酸", "sakusan_suiso", baseCell, HaigoHyoCell.NUMBER_TYPE, "0.00");
                    }
                    // 水相中ＭＳＧ
                    if (ShisakuFirstData.flg_msg_suiso == 1)
                    {
                        this.insertLiteral("水相中ＭＳＧ", "msg_suiso", baseCell, HaigoHyoCell.NUMBER_TYPE, "0.00");
                    }
                    // 実効酢酸濃度
                    if (ShisakuFirstData.flg_jikkoSakusanNodo == 1)
                    {
                        this.insertLiteral("実効酢酸濃度", "jikkoSakusanNodo", baseCell, HaigoHyoCell.NUMBER_TYPE, "0.00");
                    }
                    // 微生物ランク
                    if (ShisakuFirstData.flg_rank_biseibutsu == 1)
                    {
                        this.insertLiteral("微生物ランク", "rank_biseibutsu", baseCell);
                    }
                    // 水相中非解離酢酸酸度
                    if (ShisakuFirstData.flg_jikkoHikairiSakusanSando == 1)
                    {
                        this.insertLiteral("水相中非解離酢酸酸度", "jikkoHikairiSakusanSando", baseCell, HaigoHyoCell.NUMBER_TYPE, "0.00");
                    }
                    // 配合強度
                    if (ShisakuFirstData.flg_haigo_kyodo == 1)
                    {
                        this.insertLiteral("配合強度", "haigo_kyodo", baseCell);
                        this.getHaigoKyodoName();
                    }
                    // 製品オイルマスタード含有量
                    if (ShisakuFirstData.flg_oilmustard == 1)
                    {
                        this.insertLiteral("製品オイルマスタード含有量", "oilmustard", baseCell, HaigoHyoCell.NUMBER_TYPE, "0");
                    }
                    // フリータイトル1
                    if (ShisakuFirstData.flg_free1 == 1)
                    {
                        this.insertLiteral(ShisakuFirstData.free_title1, "free_value1", baseCell, HaigoHyoCell.STRING_TYPE, null, leftAlign);
                    }
                    // フリータイトル2
                    if (ShisakuFirstData.flg_free2 == 1)
                    {
                        this.insertLiteral(ShisakuFirstData.free_title2, "free_value2", baseCell, HaigoHyoCell.STRING_TYPE, null, leftAlign);
                    }
                    // フリータイトル3
                    if (ShisakuFirstData.flg_free3 == 1)
                    {
                        this.insertLiteral(ShisakuFirstData.free_title3, "free_value3", baseCell, HaigoHyoCell.STRING_TYPE, null, leftAlign);
                    }
                    // フリータイトル4
                    if (ShisakuFirstData.flg_free4 == 1)
                    {
                        this.insertLiteral(ShisakuFirstData.free_title4, "free_value4", baseCell, HaigoHyoCell.STRING_TYPE, null, leftAlign);
                    }
                    // フリータイトル5
                    if (ShisakuFirstData.flg_free5 == 1)
                    {
                        this.insertLiteral(ShisakuFirstData.free_title5, "free_value5", baseCell, HaigoHyoCell.STRING_TYPE, null, leftAlign);
                    }
                    // フリータイトル6
                    if (ShisakuFirstData.flg_free6 == 1)
                    {
                        this.insertLiteral(ShisakuFirstData.free_title6, "free_value6", baseCell, HaigoHyoCell.STRING_TYPE, null, leftAlign);
                    }
                    // フリータイトル7
                    if (ShisakuFirstData.flg_free7 == 1)
                    {
                        this.insertLiteral(ShisakuFirstData.free_title7, "free_value7", baseCell, HaigoHyoCell.STRING_TYPE, null, leftAlign);
                    }
                    // フリータイトル8
                    if (ShisakuFirstData.flg_free8 == 1)
                    {
                        this.insertLiteral(ShisakuFirstData.free_title8, "free_value8", baseCell, HaigoHyoCell.STRING_TYPE, null, leftAlign);
                    }
                    // フリータイトル9
                    if (ShisakuFirstData.flg_free9 == 1)
                    {
                        this.insertLiteral(ShisakuFirstData.free_title9, "free_value9", baseCell, HaigoHyoCell.STRING_TYPE, null, leftAlign);
                    }
                    // フリータイトル10
                    if (ShisakuFirstData.flg_free10 == 1)
                    {
                        this.insertLiteral(ShisakuFirstData.free_title10, "free_value10", baseCell, HaigoHyoCell.STRING_TYPE, null, leftAlign);
                    }
                    // フリータイトル11
                    if (ShisakuFirstData.flg_free11 == 1)
                    {
                        this.insertLiteral(ShisakuFirstData.free_title11, "free_value11", baseCell, HaigoHyoCell.STRING_TYPE, null, leftAlign);
                    }
                    // フリータイトル12
                    if (ShisakuFirstData.flg_free12 == 1)
                    {
                        this.insertLiteral(ShisakuFirstData.free_title12, "free_value12", baseCell, HaigoHyoCell.STRING_TYPE, null, leftAlign);
                    }
                    // フリータイトル13
                    if (ShisakuFirstData.flg_free13 == 1)
                    {
                        this.insertLiteral(ShisakuFirstData.free_title13, "free_value13", baseCell, HaigoHyoCell.STRING_TYPE, null, leftAlign);
                    }
                    // フリータイトル14
                    if (ShisakuFirstData.flg_free14 == 1)
                    {
                        this.insertLiteral(ShisakuFirstData.free_title14, "free_value14", baseCell, HaigoHyoCell.STRING_TYPE, null, leftAlign);
                    }
                    // フリータイトル15
                    if (ShisakuFirstData.flg_free15 == 1)
                    {
                        this.insertLiteral(ShisakuFirstData.free_title15, "free_value15", baseCell, HaigoHyoCell.STRING_TYPE, null, leftAlign);
                    }
                    // フリータイトル16
                    if (ShisakuFirstData.flg_free16 == 1)
                    {
                        this.insertLiteral(ShisakuFirstData.free_title16, "free_value16", baseCell, HaigoHyoCell.STRING_TYPE, null, leftAlign);
                    }
                    // フリータイトル17
                    if (ShisakuFirstData.flg_free17 == 1)
                    {
                        this.insertLiteral(ShisakuFirstData.free_title17, "free_value17", baseCell, HaigoHyoCell.STRING_TYPE, null, leftAlign);
                    }
                    // フリータイトル18
                    if (ShisakuFirstData.flg_free18 == 1)
                    {
                        this.insertLiteral(ShisakuFirstData.free_title18, "free_value18", baseCell, HaigoHyoCell.STRING_TYPE, null, leftAlign);
                    }
                    // フリータイトル19
                    if (ShisakuFirstData.flg_free19 == 1)
                    {
                        this.insertLiteral(ShisakuFirstData.free_title19, "free_value19", baseCell, HaigoHyoCell.STRING_TYPE, null, leftAlign);
                    }
                    // フリータイトル20
                    if (ShisakuFirstData.flg_free20 == 1)
                    {
                        this.insertLiteral(ShisakuFirstData.free_title20, "free_value20", baseCell, HaigoHyoCell.STRING_TYPE, null, leftAlign);
                    }
                    // 糖度
                    if (ShisakuFirstData.flg_toudo == 1)
                    {
                        this.insertLiteral("糖度", "toudo", baseCell);
                    }
                    // 水分活性
                    if (ShisakuFirstData.flg_suibun_kasei == 1)
                    {
                        this.insertLiteral("水分活性", "suibun_kasei", baseCell);
                    }
                    // アルコール
                    if (ShisakuFirstData.flg_alcohol == 1)
                    {
                        this.insertLiteral("アルコール", "alcohol", baseCell);
                    }
                    // 率ＭＳＧ
                    if (ShisakuFirstData.flg_msg == 1)
                    {
                        this.insertLiteral("率ＭＳＧ", "ritu_msg", baseCell, HaigoHyoCell.NUMBER_TYPE, "0.00");
                    }
                    // フリー粘度タイトル, フリー粘度
                    if (ShisakuFirstData.flg_freeNendo == 1)
                    {
                        this.insertLiteral(ShisakuFirstData.freetitle_nendo, "free_nendo", baseCell, HaigoHyoCell.STRING_TYPE, null, leftAlign);
                    }
                    // フリー温度タイトル,フリー温度
                    if (ShisakuFirstData.flg_freeOndo == 1)
                    {
                        this.insertLiteral(ShisakuFirstData.freetitle_ondo, "free_ondo", baseCell, HaigoHyoCell.STRING_TYPE, null, leftAlign);
                    }
                    // フリー水分活性タイトル, フリー水分活性
                    if (ShisakuFirstData.flg_freeSuibunKasei == 1)
                    {
                        this.insertLiteral(ShisakuFirstData.freetitle_suibun_kasei, "free_suibun_kasei", baseCell, HaigoHyoCell.STRING_TYPE, null, leftAlign);
                    }
                    // フリーアルコールタイトル, フリーアルコール
                    if (ShisakuFirstData.flg_freeAlchol == 1)
                    {
                        this.insertLiteral(ShisakuFirstData.freetitle_alcohol, "free_alcohol", baseCell, HaigoHyoCell.STRING_TYPE, null, leftAlign);
                    }
                    // 水相中総酸：分析
                    // R.#15485
                    //if (ShisakuFirstData.flg_sousan_bunseki_suiso == 1)
                    //{
                    //    this.insertLiteral("水相中総酸：分析", "ritu_sousan_bunseki_suiso", baseCell);
                    //}
                    // 水相中食塩：分析
                    // R.#15485
                    //if (ShisakuFirstData.flg_shokuen_bunseki_suiso == 1)
                    //{
                    //    this.insertLiteral("水相中食塩：分析", "ritu_shokuen_bunseki_suiso", baseCell);
                    //}
                }
            }
        }
        // Insert literal to literal list
        private HaigoHyoCell insertLiteral(string nm_genryo_disp, string property, HaigoHyoCell baseCell, string cellType = null, string format = null, string align = null)
        {
            this.KoteiListLiteral.Add(new sp_shohinkaihatsu_search_104_kotei_Result() { nm_genryo_disp = nm_genryo_disp, sort_kotei = MAX_SORT_SEQ });
            var currentCell = baseCell.getNextPosition(0, shisa_prop_index);
            currentCell.property = property;
            currentCell.type = cellType;
            currentCell.format = format;
            currentCell.align = align;
            this.ShisakuMapping.Add(currentCell);
            shisa_prop_index++;
            return currentCell;
        }
        // Bind shisakuHin data
        private void bindShisakuhinData()
        {
            // for each in mapping and set the value to the sheet
            foreach (var prop in this.ShisakuHinMapping)
            {
                prop.setValue(this.ShisakuHinData, this.Excel, SHEETNAME);
            }
        }
        // Bind left literal
        private void bindLiteral()
        {
            var LiteralMapping = new List<HaigoHyoCell>();
            LiteralMapping.Add(new HaigoHyoCell("cd_genryo_disp", "A10"));
            LiteralMapping.Add(new HaigoHyoCell("nm_genryo_disp", "B10"));
            LiteralMapping.Add(new HaigoHyoCell("tanka", "F10", HaigoHyoCell.NUMBER_TYPE, TWO_POINT, 0));
            LiteralMapping.Add(new HaigoHyoCell("budomari", "G10", HaigoHyoCell.NUMBER_TYPE, TWO_POINT, 0));

            //TOsVN - 19075 - 2023/08/28: st ShohinKaihatuSupport Modify 2023 - Bug#2242
            var gensanchi = new HaigoHyoCell("flg_gensanchi", "H10");
            gensanchi.align = "Center";
            LiteralMapping.Add(gensanchi);
            // -ed - Bug#2242

            int? sortSeq = -1;
            int index = 0;
            for (var i = 0; i < this.KoteiListLiteral.Count; i++)
            {
                var data = this.KoteiListLiteral[i];
                if (sortSeq != data.sort_kotei)
                {
                    sortSeq = data.sort_kotei;
                    switch (data.sort_kotei)
                    {
                        //工程(g)
                        case SUM_QTA_KOTEI_SORT_SEQ:
                            index = SUM_QTA_KOTEI_BEG_IND - 10;
                            break;
                        // 工程仕上重量(g)
                        case SUM_QTA_SORT_SEQ:
                            index = SUM_QTA_BEG_IND - 10;
                            break;
                        // 合計重量(g)
                        case INP_QTA_KOTEI_SORT_SEQ:
                            index = INP_QTA_KOTEI_BEG_IND - 10;
                            break;
                    }
                }
                //if (sortSeq == SUM_QTA_KOTEI_SORT_SEQ || sortSeq == SUM_QTA_SORT_SEQ || sortSeq == INP_QTA_KOTEI_SORT_SEQ)
                //{
                //    this.KoteiListLiteral[i].nm_genryo_disp = toTwoByte(this.KoteiListLiteral[i].nm_genryo_disp);
                //}
                var dataK = this.KoteiListLiteral[i];

                // for each in mapping and set the value to the sheet
                foreach (var prop in LiteralMapping)
                {
                    // Not display 0 as defaut in kotei name row
                    if (prop.property == "tanka" || prop.property == "budomari")
                    {
                        if (dataK.sort_kotei >= SUM_QTA_KOTEI_SORT_SEQ)
                        {
                            continue;
                        }
                        if (dataK.cd_genryo_disp == "---")
                        {
                            prop.defaultNull = null;
                        }
                        else
                        {
                            prop.defaultNull = 0;
                        }
                    }

                    prop.setValue(dataK, this.Excel, SHEETNAME, 0, index);
                }
                index++;
            }
        }
        // Bind shisakudata
        private void bindShisakuData()
        {
            this.ShisakuMapping.Add(new HaigoHyoCell("dt_shisaku", "M6", HaigoHyoCell.DATE_TYPE));
            this.ShisakuMapping.Add(new HaigoHyoCell("nm_sample", "M7"));
            this.ShisakuMapping.Add(new HaigoHyoCell("memo", "M8"));
            this.ShisakuMapping.Add(new HaigoHyoCell("juryo_shiagari_g", "M313", HaigoHyoCell.NUMBER_TYPE, this.formatNumber));

            List<HaigoHyoCell> GenryoMapping = new List<HaigoHyoCell>();
            GenryoMapping.Add(new HaigoHyoCell("genryohi", "M314", HaigoHyoCell.NUMBER_TYPE, "0.00", 0));
            GenryoMapping.Add(new HaigoHyoCell("genryohi1", "M315", HaigoHyoCell.NUMBER_TYPE, "0.00", 0));

            List<HaigoHyoCell> CyuuiMapping = new List<HaigoHyoCell>();
            CyuuiMapping.Add(new HaigoHyoCell("cyuui_nm_sample", "AG7"));
            CyuuiMapping.Add(new HaigoHyoCell("cyuui_no_chui", "AG8", HaigoHyoCell.STRING_TYPE, null, "工程版: 0"));
            CyuuiMapping.Add(new HaigoHyoCell("chuijiko", "AG9"));

            for (var i = 0; i < this.ShisakuData.Count; i++)
            {
                // Bind shisaku data
                var objData = this.ShisakuData[i];
                //if (objData.juryo_shiagari_g == null)
                //{
                //    objData.juryo_shiagari_g = this.ShisakuListData.Where(x => x.seq_shisaku == objData.seq_shisaku && x.sort_kotei == SUM_QTA_SORT_SEQ).Select(x => x.quantity).FirstOrDefault();
                //}
                // for each in mapping and set the value to the sheet
                foreach (var prop in this.ShisakuMapping)
                {
                    if (prop.property == "haigo_kyodo")
                    {
                        var name = this.haigoKyodoName.Where(x => x.haigo_kyodo == objData.haigo_kyodo).Select(x => x.nm_haigo_kyodo).FirstOrDefault();
                        prop.setValue(name, this.Excel, SHEETNAME, i, 0, false);
                    }
                    else 
                    {
                        prop.setValue(objData, this.Excel, SHEETNAME, i);
                    }
                }
                // Bind genryo data
                var genryoData = this.GenryoData.Where(x => x.seq_shisaku == objData.seq_shisaku).FirstOrDefault();
                foreach (var prop in GenryoMapping)
                {
                    prop.setValue(genryoData, this.Excel, SHEETNAME, i);
                }
                // Bind
                if (i < 3)
                {
                    tr_cyuui cyuuiData = getCyuuiData(objData);
                    foreach (var prop in CyuuiMapping)
                    {
                        if (prop.property == "cyuui_nm_sample")
                        {
                            prop.setValue("サンプル: " + objData.nm_sample, this.Excel, SHEETNAME, i * 2, 0, false);
                        }
                        else if (prop.property == "cyuui_no_chui")
                        {
                            string name = "工程版: 0";
                            if (objData.no_chui != null)
                            {
                                name = "工程版:" + objData.no_chui;
                            }
                            prop.setValue(name, this.Excel, SHEETNAME, i * 2, 0, false);
                        }
                        else
                        {
                            if (cyuuiData != null)
                            {
                                prop.setValue(cyuuiData, this.Excel, SHEETNAME, i * 2);
                            }
                        }
                    }
                }
            }
        }
        // Bind data to shisaku list
        private void bindShisakuListData()
        {
            List<HaigoHyoCell> Mapping = new List<HaigoHyoCell>();
            var Cell = new HaigoHyoCell("quantity", "M10", HaigoHyoCell.NUMBER_TYPE, this.formatNumber);
            Mapping.Add(Cell);
            for (var i = 0; i < this.ShisakuData.Count; i++)
            {
                // Filter shisaku list base on seq_shisaku
                var shisakuList = this.ShisakuListData.Where(x => x.seq_shisaku == this.ShisakuData[i].seq_shisaku).ToList();

                int? sortSeq = -1;
                int index = 0;
                var curFormat = this.formatNumber;
                for (var j = 0; j < shisakuList.Count; j ++)
                {
                    var data = shisakuList[j];
                    if (sortSeq != data.sort_kotei)
                    {
                        sortSeq = data.sort_kotei;
                        switch (data.sort_kotei)
                        {
                            //工程(g)
                            case SUM_QTA_KOTEI_SORT_SEQ:
                                index = SUM_QTA_KOTEI_BEG_IND - 10;
                                curFormat = formatNumber;
                                break;
                            // 工程仕上重量(g)
                            case SUM_QTA_SORT_SEQ:
                                index = SUM_QTA_BEG_IND - 10;
                                curFormat = formatNumber;
                                break;
                            // 合計重量(g)
                            case INP_QTA_KOTEI_SORT_SEQ:
                                index = INP_QTA_KOTEI_BEG_IND - 10;
                                curFormat = formatNumber;
                                break;
                            default:
                                curFormat = this.formatNumber;
                                break;
                        }
                    }
                    // for each in mapping and set the value to the sheet
                    foreach (var prop in Mapping)
                    {
                        prop.format = curFormat;
                        prop.setValue(data, this.Excel, SHEETNAME, i, index);
                    }
                    index++;
                }
            }
        }
        // Bind data to excel file
        private void bindData()
        {
            this.bindShisakuhinData();
            this.bindLiteral();
            this.bindShisakuData();
            this.bindShisakuListData();
            this.Excel.SaveSheet(SHEETNAME);
            this.Excel.SaveWorkbook();
        }
        // Get excel file
        public void getExcel(string dirTemlates, HaigoHyoExcelCondition Conditions)
        {
            if (Conditions == null)
            {
                return;
            }
            // Set base param
            this.Conditions = Conditions;
            this.stream = new MemoryStream();
            // Contruct excel file
            //this.Excel = new XLWorkbook(dirTemlates);
            this.Excel = new ExcelFile(dirTemlates);
            // Contruct MAPPING list
            // ShisakuHin Mapping list
            this.ShisakuHinMapping = new List<HaigoHyoCell>();
            this.ShisakuHinMapping.Add(new HaigoHyoCell("no_shisaku", "B1"));        
            this.ShisakuHinMapping.Add(new HaigoHyoCell("no_irai", "F1"));           
            this.ShisakuHinMapping.Add(new HaigoHyoCell("nm_hin", "B2"));            
            this.ShisakuHinMapping.Add(new HaigoHyoCell("nm_kaisha", "B4"));         
            this.ShisakuHinMapping.Add(new HaigoHyoCell("nm_busho", "B5"));          
            this.ShisakuHinMapping.Add(new HaigoHyoCell("nm_user", "B6"));
            this.ShisakuHinMapping.Add(new HaigoHyoCell("sys_date", "B7"));
            this.ShisakuHinMapping.Add(new HaigoHyoCell("memo_shisaku", "AM8"));

            // Shisaku Mapping list
            this.ShisakuMapping = new List<HaigoHyoCell>();

            // Shisaku list Mapping list
            this.ShisakuListMapping = new List<HaigoHyoCell>();

            // Get data for binding
            this.getShisakuHinData();
            this.getShisakuData();
            this.getShisakuListData();
            this.getKoteiListLiteral();

            //for (uint i = 0; i < 200; i++)
            //{
            //    var testCell = new HaigoHyoCell() { Row = (int)i + 10, Col = 15, type = HaigoHyoCell.NUMBER_TYPE };
            //    testCell.ColumnNumberToName();
            //    testCell.test_format = UInt32Value.FromUInt32(i);
                
            //    testCell.setValue("1000.1", this.Excel, SHEETNAME, 0, 0, false);
            //}

            //this.Excel.SaveSheet(SHEETNAME);
            //this.Excel.SaveWorkbook();
            

            // Bind data to Excel
            this.bindData();
            this.stream = (MemoryStream)this.Excel.Stream;
            //this.Excel.SaveAs(this.stream);
        }
        // Get file stream after binding
        public MemoryStream getStream()
        {
            return this.stream;
        }
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
        /// get haigo_kyoda name
        /// </summary>
        private void getHaigoKyodoName()
        {
            this.haigoKyodoName = new List<HaigoKyodoName>();
            if (this.ShisakuData != null && this.ShisakuData.Count > 0)
            {
                List<string> processingShisaku = new List<string>();
                foreach (var shisa in this.ShisakuData)
                {
                    processingShisaku.Add(shisa.haigo_kyodo);
                }
                if (processingShisaku.Count > 0)
                {
                    using (ShohinKaihatsuEntities context = new ShohinKaihatsuEntities())
                    {
                        this.haigoKyodoName = (from i in context.ma_literal
                                               where i.cd_category == Conditions.cd_category_haigo_hyodo
                                                     && processingShisaku.Contains(i.cd_literal)
                                               select new HaigoKyodoName()
                                               {
                                                   haigo_kyodo = i.cd_literal,
                                                   nm_haigo_kyodo = i.nm_literal
                                               }).ToList();
                    }
                }
            }
        }
    }

    /// <summary>
    /// Excel request param
    /// </summary>
    public class HaigoHyoExcelCondition
    {
        public decimal cd_shain { get; set; }
        public decimal nen { get; set; }
        public decimal no_oi { get; set; }
        // user info
        public decimal id_user { get; set; }
        public string nm_user { get; set; }
        // cd_category
        public string cd_category_haigo_hyodo { get; set; }
    }

    /// <summary>
    /// extend of tr_shisakuhin
    /// </summary>
    public class tr_shisakuhin_EXT : sp_shohinkaihatsu_search_104_shisakuhin_Result
    {
        public string no_shisaku { get; set; }
        public string sys_date { get; set; }
    }

    /// <summary>
    /// name of haigo hyodo
    /// </summary>
    public class HaigoKyodoName
    {
        public string haigo_kyodo { get; set; }
        public string nm_haigo_kyodo { get; set; }
    }


    #endregion "OOP contructions - Base Excel objects"
}
