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
    //created from 【SearchInputController(Ver1.7)】 Template
    public class _300_SeihoBunshoSakusei_ExcelController : ApiController
    {
        #region "Controllerで公開するAPI"

        /// <summary>
        /// Download Excel when click btn excel download
        /// </summary>
        /// <param name="Conditions">検索条件</param>
        /// <returns>Excel file</returns>
        [HttpPost]
        public HttpResponseMessage Post([FromBody] SeihoBunshoExcelCondition Conditions)
        {
            // 読込テンプレートファイルパス
            string templatepath = HttpContext.Current.Server.MapPath(Properties.Settings.Default.ExcelTemplateFolder);
            string dirTemlates = templatepath + "\\" + Properties.Resources.BunshoTemplateExcelName + Properties.Resources.ExcelExtension;
            // 保存Excelファイル名 (製法文書_[製法番号]_[yyyyMMddhhmmss].xlsx)
            string excelname = Properties.Resources.BunshoExcelName + Conditions.no_seiho + "_" + DateTime.Now.ToString("yyyyMMddHHmmss") + Properties.Resources.ExcelExtension;

            // Excelテンプレートを読み込み、必要な情報をマッピングしてクライアントへ返却
            MemoryStream stream = EditExcelFile(dirTemlates, Conditions);
            return FileUploadDownloadUtility.CreateFileResponse(stream, excelname);
        }

        /// <summary>
        /// Export file excel when shonin 2 
        /// </summary>
        /// <param name="Conditions">検索条件</param>
        /// <returns>Export status</returns>
        [HttpPost]
        public HttpResponseMessage GetStaticExcel([FromBody] SeihoBunshoExcelCondition Conditions)
        {
            // 読込テンプレートファイルパス
            string templatepath = HttpContext.Current.Server.MapPath(Properties.Settings.Default.ExcelTemplateFolder);
            string dirTemlates = templatepath + "\\" + Properties.Resources.BunshoTemplateExcelName + Properties.Resources.ExcelExtension;
            // 保存Excelファイル名 (製法文書_[製法番号].xlsx)
            string excelDir = HttpContext.Current.Server.MapPath(Properties.Settings.Default.sourcePath_no_ikkatsu);
            string fileName = Conditions.no_seiho;
            string excelPath = excelDir + "\\" + fileName + Properties.Resources.ExcelExtension;

            if (Conditions.isDeleteExcel)
            {
                if (File.Exists(excelPath))
                {
                    File.Delete(excelPath);
                }
            }
            else
            {
                SeihoBunshoExcel BunshoExcel = new SeihoBunshoExcel();
                BunshoExcel.getExcel(dirTemlates, Conditions);
                XLWorkbook staticExcel = new XLWorkbook(BunshoExcel.getStream());
                // Delete if exist
                FileUploadDownloadUtility.deleteTempFile(excelDir, fileName);
                // Save file
                staticExcel.SaveAs(excelPath);
            }


            return Request.CreateResponse(System.Net.HttpStatusCode.OK, "");
        }

        /// <summary>
        /// Excelファイルの作成（ClosedXML使用）
        /// </summary>
        /// <param name="dirTemlates">検索条件</param>
        /// <returns>作成されたExcelファイルのMemoryStream</returns>
        private MemoryStream EditExcelFile(string dirTemlates, SeihoBunshoExcelCondition Conditions)
        {
            SeihoBunshoExcel BunshoExcel = new SeihoBunshoExcel();
            BunshoExcel.getExcel(dirTemlates, Conditions);
            return BunshoExcel.getStream();
        }
        #endregion

    }

    #region "OOP contructions - Base Excel objects"

    /// <summary>
    /// Base contruct for excel-cell
    /// </summary>
    public class SeihoBunshoCell
    {
        // Data type
        public static string NUMBER_TYPE = "number";
        public static string DATE_TYPE = "date";
        public static string STRING_TYPE = "string";
        public static int FIT11 = 11;
        public static int FIT15 = 15;
        public static int FIT22 = 22;
        public static int FIT25 = 25;
        public static int FIT44 = 44;
        public static string INDEX_IDENTIFY = "index";
        public static string COMMA_STRING = "comma_string";
        public static string BOOLEAN = "index";
        // Cell base informations
        public string position { get; set; }
        public string property { get; set; }
        public string type { get; set; }
        public string format { get; set; }
        public object defaultNull { get; set; }
        public int? fitConetent { get; set; }
        public string toStringFormat { get; set; }
        public int? fixedLength { get; set; }
        public int? trailingZero { get; set; }
        private string pCol { get; set; }
        private decimal? pRow { get; set; }
        // Set value to cell
        public void setValue(object data, XLWorkbook Excel, string sheetName, decimal distance = 0, bool isAutoGetValue = true)
        {
            if (Excel == null || data == null || this.position == null || this.property == null)
            {
                return;
            }
            // For single data binding
            string bindPosition = position;
            // For array data binding
            if (distance != 0)
            {
                bindPosition = this.getNextPosition(distance);
            }
            object value = null;
            // Get target data
            // Auto index, only use for array data
            if (this.type == INDEX_IDENTIFY) 
            {
                value = distance + 1;
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
                // Auto break line
                if (this.fitConetent != null)
                {
                    strValue = getFixLengthString(strValue, this.fitConetent ?? 0);
                }
                else if (this.type == COMMA_STRING)
                {
                    decimal n;
                    if (decimal.TryParse(strValue, out n))
                    {
                        strValue = n.ToString("#,0");
                    }
                }
                UpdateValue(Excel, sheetName, bindPosition, strValue);
            }            
        }
        // update value to a cell in work sheet
        private void UpdateValue(XLWorkbook Excel, string sheetName, string position, object value)
        {
            var Cell = Excel.Worksheet(sheetName).Cell(position);
            if (this.type != NUMBER_TYPE)
            {
                // Update data type
                Cell.Style.NumberFormat.Format = "@";
            }
            if (format != null)
            {
                Cell.Style.NumberFormat.Format = format;
            }
            // Update value
            Cell.Value = value;
        }
        // Split position
        public void splitPosition()
        {
            if (position == null || position.Length == 0)
            {
                return;
            }
            // x postion and y position
            string x = "";
            string y = "";
            for (var i = 0; i < position.Length; i++)
            { 
                string character = position[i].ToString();
                // Check if value is number
                if (String.Compare(character, "0") >= 0 && String.Compare(character, "9") <= 0)
                {
                    y += character;
                }
                else
                {
                    x += character;
                }
            }
            this.pCol = x;
            this.pRow = decimal.Parse(y);
        }
        // Get position with y distance
        public string getNextPosition(decimal distance)
        {
            if (this.pRow == null || this.pCol == null)
            {
                return string.Empty;
            }
            return this.pCol + (this.pRow + distance).ToString();
        }

        // Auto break line every 25 characters
        public static string GetFix25(string value)
        {
            return getFixLengthString(value, 25);
        }
        // Auto break line every n characters
        private static string getFixLengthString(string value, int length)
        {
            // Remove auto breakline following BUG #15559
            return value;
            //if (value != null)
            //{
            //    string result = String.Empty;
            //    int breaklineIndex = 0;
            //    for (var i = 0; i < value.Length; i++)
            //    {
            //        if (value[i] == '\n')
            //        {
            //            breaklineIndex = 0;
            //        }
            //        else
            //        {
            //            breaklineIndex++;
            //        }
            //        if (breaklineIndex != 0 && breaklineIndex % (length + 1) == 0)
            //        {
            //            result += "\n";
            //            breaklineIndex = 1;
            //        }
            //        result += value[i];
            //    }
            //    return result;
            //}
            //else
            //{
            //    return null;
            //}
        }
    }

    /// <summary>
    /// Base contruct for excel-sheet
    /// </summary>
    public class SeihoBunshoSheet
    {
        // Excel file
        protected SeihoBunshoExcel File { get; set; }
        // Document
        protected SeihoBunshoExcelCondition Conditions { get; set; }
        // Sheet name
        public string sheetName { get; set; }
        // Header data 
        public SeihoBunshoHeaderData headerData { get; set; }
        // List of header property in the sheet
        public List<SeihoBunshoCell> headerProperty { get; set; }
        // List of single property in the sheet
        public List<SeihoBunshoCell> singleProperty { get; set; }
        // List of array property in the sheet
        public List<SeihoBunshoCell> arrayProperty { get; set; }
        // Render header data
        public void renderHeader()
        {
            // for each in mapping and set the value to the sheet
            foreach (var prop in headerProperty)
            {
                prop.setValue(this.headerData, this.File.Excel, this.sheetName);
            }
        }
        // Return true if the sheet is exported
        public static bool isExport(List<string> lst_tab, string sheetName)
        {
            if (lst_tab == null)
            {
                return false;
            }
            if (lst_tab.Contains(sheetName))
            {
                return true;
            }
            return false;
        }
        // Sheet base infor
        protected void contructSheet(SeihoBunshoExcel file, string sheetName)
        {
            // Set base param
            this.Conditions = file.Conditions;
            this.headerData = file.headerData;
            this.File = file;
            this.sheetName = sheetName;
            // Get contruct mapping lst
            this.headerProperty = new List<SeihoBunshoCell>();
            this.singleProperty = new List<SeihoBunshoCell>();
            this.arrayProperty = new List<SeihoBunshoCell>();
            // Add header mapping data
            this.headerProperty.Add(new SeihoBunshoCell() { position = "AN1", property = "dt_seiho_sakusei" });
            this.headerProperty.Add(new SeihoBunshoCell() { position = "F2", property = "no_seiho" });
            this.headerProperty.Add(new SeihoBunshoCell() { position = "Q2", property = "nm_seiho" });
        }
    }

    /// <summary>
    /// TAB 1 表紙 Excel sheet
    /// </summary>
    public class HyoshiSheet : SeihoBunshoSheet
    {
        // Sheet name in template
        public static string SHEET_NAME = "表紙";
        public static string TAB_NAME = "Hyoshi";
        // Limit binding array
        public static int ARRAY_LIMIT = 8;
        public ma_seiho_bunsho_hyoshi_EXT singleData { get; set; }
        public List<sp_shohinkaihatsu_search_804_Result> arrayData { get; set; }
        // Get data for bind TAB 1
        public void getData()
        {
            var HyoshiData = _300_SeihoBunshoSakusei_Hyoshi_TabController.getExcelData(this.Conditions);
            this.singleData = HyoshiData.singleData;
            this.arrayData = HyoshiData.arrayData;
        }
        // Render single data
        public void renderSingle()
        {
            // for each in mapping and set the value to the sheet
            foreach (var prop in this.singleProperty)
            {
                prop.setValue(this.singleData, this.File.Excel, this.sheetName);
            }
        }
        // Render array data
        public void renderArray()
        {
            // for each in mapping and set the value to the sheet
            foreach (var prop in this.arrayProperty)
            {
                prop.splitPosition();
                for (var i = 0; i < arrayData.Count; i++ )
                {
                    if (i >= ARRAY_LIMIT) 
                    {
                        break;
                    }
                    prop.setValue(this.arrayData[i], this.File.Excel, this.sheetName, i);
                }
            }
        }
        // Add sheet, get value, bind data to sheet
        public HyoshiSheet(SeihoBunshoExcel file)
        {
            // Contruct the sheet
            this.contructSheet(file, HyoshiSheet.SHEET_NAME);
            // Add single mapping property
            // 製品種類
            this.singleProperty.Add(new SeihoBunshoCell() { position = "I4", property = "nm_hin_syurui" });
            // 作成担当者
            this.singleProperty.Add(new SeihoBunshoCell() { position = "Z4", property = "dt_create", type = SeihoBunshoCell.DATE_TYPE });
            this.singleProperty.Add(new SeihoBunshoCell() { position = "AF4", property = "nm_create_shozoku_busho" });
            this.singleProperty.Add(new SeihoBunshoCell() { position = "AM4", property = "nm_create" });
            // 表示用製法
            this.singleProperty.Add(new SeihoBunshoCell() { position = "I5", property = "no_hyojiyo_seiho" });
            // 承認１
            this.singleProperty.Add(new SeihoBunshoCell() { position = "Z5", property = "dt_shonin1", type = SeihoBunshoCell.DATE_TYPE });
            this.singleProperty.Add(new SeihoBunshoCell() { position = "AF5", property = "nm_shonin1_shozoku_busho" });
            this.singleProperty.Add(new SeihoBunshoCell() { position = "AM5", property = "nm_shonin1" });
            // 承認２
            this.singleProperty.Add(new SeihoBunshoCell() { position = "Z6", property = "dt_shonin2", type = SeihoBunshoCell.DATE_TYPE });
            this.singleProperty.Add(new SeihoBunshoCell() { position = "AF6", property = "nm_shonin2_shozoku_busho" });
            this.singleProperty.Add(new SeihoBunshoCell() { position = "AM6", property = "nm_shonin2" });
            // 製造工場
            this.singleProperty.Add(new SeihoBunshoCell() { position = "I18", property = "nm_kaisha_kojyo" });
            // 得意先名
            this.singleProperty.Add(new SeihoBunshoCell() { position = "I22", property = "nm_tokisaki" });
            // ブランド名
            this.singleProperty.Add(new SeihoBunshoCell() { position = "I23", property = "nm_brand" });
            // 製法番号
            this.singleProperty.Add(new SeihoBunshoCell() { position = "Y23", property = "no_seiho_sanko" });
            // 製造開始予定日
            this.singleProperty.Add(new SeihoBunshoCell() { position = "I24", property = "dt_seizo_kaishi_yotei" });
            // 製法名
            this.singleProperty.Add(new SeihoBunshoCell() { position = "Y24", property = "nm_seiho_sanko" });
            // 所属グループ
            this.singleProperty.Add(new SeihoBunshoCell() { position = "I25", property = "nm_shozoku_group" });
            // 所属チーム
            this.singleProperty.Add(new SeihoBunshoCell() { position = "I26", property = "nm_shozoku_team" });
            // チームリーダ
            this.singleProperty.Add(new SeihoBunshoCell() { position = "I27", property = "nm_team_leader" });
            // 重要技術情報
            this.singleProperty.Add(new SeihoBunshoCell() { position = "C30", property = "nm_juyo_gijitsu_joho", fitConetent = SeihoBunshoCell.FIT25 });
            // 強調表示
            this.singleProperty.Add(new SeihoBunshoCell() { position = "Y30", property = "nm_kyocho_hyoji", fitConetent = SeihoBunshoCell.FIT25 });
            // Add array mapping property
            //製品リスト
            this.arrayProperty.Add(new SeihoBunshoCell() { position = "I8", property = "cd_hin", fixedLength = Conditions.su_code_standard });
            this.arrayProperty.Add(new SeihoBunshoCell() { position = "M8", property = "nm_seihin" });
            // Get sheet data
            this.getData();
        }        
        // Bind data to the sheet
        public void Bind()
        {
            // Bind header data
            this.renderHeader();
            // Bind data
            this.renderSingle();
            this.renderArray();
        }
    }

    /// <summary>
    /// TAB 1 製品リスト Excel sheet
    /// </summary>
    public class SeihinSheet : SeihoBunshoSheet
    {
        // Sheet name in template
        public static string SHEET_NAME = "製品リスト";
        public static string TAB_NAME = "Hyoshi";
        public static int BEG_DETAIL = 5;
        // Dataset
        public List<sp_shohinkaihatsu_search_804_Result> arrayData { get; set; }
        // Get data for bind TAB 1
        public void getData(List<sp_shohinkaihatsu_search_804_Result> arrayData)
        {
            this.arrayData = arrayData;
        }
        // Render array data
        public void renderArray()
        {
            // for each in mapping and set the value to the sheet
            foreach (var prop in this.arrayProperty)
            {
                prop.splitPosition();

            }
            for (var i = HyoshiSheet.ARRAY_LIMIT; i < arrayData.Count; i++)
            {
                this.File.copyTempRow(this.sheetName, "G", "M", i - HyoshiSheet.ARRAY_LIMIT + BEG_DETAIL);
                foreach (var prop in this.arrayProperty)
                {
                    prop.setValue(this.arrayData[i], this.File.Excel, this.sheetName, i - HyoshiSheet.ARRAY_LIMIT);
                }
            }
        }
        // Add sheet, get value, bind data to sheet
        public SeihinSheet(SeihoBunshoExcel file, List<sp_shohinkaihatsu_search_804_Result> arrayData)
        {
            // Contruct the sheet
            this.contructSheet(file, SeihinSheet.SHEET_NAME);
            // Add array mapping property
            //製品リスト
            this.arrayProperty.Add(new SeihoBunshoCell() { position = "G5", property = "index", type = SeihoBunshoCell.INDEX_IDENTIFY });
            this.arrayProperty.Add(new SeihoBunshoCell() { position = "I5", property = "cd_hin", fixedLength = Conditions.su_code_standard });
            this.arrayProperty.Add(new SeihoBunshoCell() { position = "M5", property = "nm_seihin" });
            // Get sheet data
            this.getData(arrayData);
        }
        // Bind data to the sheet
        public void Bind()
        {
            // Bind header data
            this.renderHeader();
            // Bind data
            this.renderArray();
        }
    }

    /// <summary>
    /// TAB 1 元になる製法 Excel sheet
    /// </summary>
    public class SeihoSankoSheet : SeihoBunshoSheet
    {
        // Sheet name in template
        public static string SHEET_NAME = "元になる製法";
        public static string TAB_NAME = "Hyoshi";
        // Sheet data
        public ma_seiho_bunsho_hyoshi_EXT singleData { get; set; }
        // Get data for bind TAB 1
        public void getData(ma_seiho_bunsho_hyoshi_EXT singleData)
        {
            this.singleData = singleData;
        }
        // Render single data
        public void renderSingle()
        {
            // for each in mapping and set the value to the sheet
            foreach (var prop in this.singleProperty)
            {
                prop.setValue(this.singleData, this.File.Excel, this.sheetName);
            }
        }
        // Add sheet, get value, bind data to sheet
        public SeihoSankoSheet(SeihoBunshoExcel file, ma_seiho_bunsho_hyoshi_EXT singleData)
        {
            // Contruct the sheet
            this.contructSheet(file, SeihoSankoSheet.SHEET_NAME);
            // Add single mapping property
            // 製法番号
            this.singleProperty.Add(new SeihoBunshoCell() { position = "I4", property = "no_seiho_sanko" });
            // 製法名
            this.singleProperty.Add(new SeihoBunshoCell() { position = "I5", property = "nm_seiho_sanko" });
            // 元になる製法からの変更点(改版の場合)
            this.singleProperty.Add(new SeihoBunshoCell() { position = "C8", property = "nm_zenbankara_henkoten", fitConetent = SeihoBunshoCell.FIT25 });
            // 製品特徴
            this.singleProperty.Add(new SeihoBunshoCell() { position = "Y8", property = "nm_seihin_tokucho", fitConetent = SeihoBunshoCell.FIT25 });
            // Get sheet data
            this.getData(singleData);
        }
        // Bind data to the sheet
        public void Bind()
        {
            // Bind header data
            this.renderHeader();
            // Bind data
            this.renderSingle();
        }
    }

    /// <summary>
    /// TAB 2 容器包装 Excel sheet
    /// </summary>
    public class YoukiHousouSheet : SeihoBunshoSheet
    {
        // Sheet name in template
        public static string SHEET_NAME = "容器包装";
        public static string TAB_NAME = "YoukiHousou";
        // Sheet data
        public ma_seiho_bunsho_yokihoso singleData { get; set; }
        public List<ma_seiho_bunsho_yokihoso_shizai_EXT> arrayData { get; set; }
        // Sheet layout
        public List<SeihoBunshoCell> arrayPropertyCol2 { get; set; }
        // Get data for bind TAB 1
        public void getData()
        {
            using (ShohinKaihatsuEntities context = new ShohinKaihatsuEntities())
            {
                this.singleData = context.ma_seiho_bunsho_yokihoso.Where(x => x.no_seiho == this.Conditions.no_seiho).FirstOrDefault();
                this.arrayData = new List<ma_seiho_bunsho_yokihoso_shizai_EXT>();
                var shizaiData = context.ma_seiho_bunsho_yokihoso_shizai.Where(x => x.no_seiho == this.Conditions.no_seiho).ToList();
                if (shizaiData != null)
                {
                    foreach (var shizai in shizaiData)
                    {
                        ma_seiho_bunsho_yokihoso_shizai_EXT newShizai = new ma_seiho_bunsho_yokihoso_shizai_EXT();
                        DataCopier.ReFill(shizai, newShizai);
                        newShizai.getShizaiName(this.singleData);
                        this.arrayData.Add(newShizai);
                    }
                }
                // reorder detail data
                if (this.singleData != null && this.arrayData != null && this.arrayData.Count > 0)
                {
                    var searchDetail = this.arrayData;
                    this.arrayData = new List<ma_seiho_bunsho_yokihoso_shizai_EXT>();
                    this.arrayData.Add(searchDetail.Where(x => x.cd_yoki_hoso_shizai == this.singleData.cd_yoki_hoso_shizai01).FirstOrDefault());
                    this.arrayData.Add(searchDetail.Where(x => x.cd_yoki_hoso_shizai == this.singleData.cd_yoki_hoso_shizai02).FirstOrDefault());
                    this.arrayData.Add(searchDetail.Where(x => x.cd_yoki_hoso_shizai == this.singleData.cd_yoki_hoso_shizai03).FirstOrDefault());
                    this.arrayData.Add(searchDetail.Where(x => x.cd_yoki_hoso_shizai == this.singleData.cd_yoki_hoso_shizai04).FirstOrDefault());
                    this.arrayData.Add(searchDetail.Where(x => x.cd_yoki_hoso_shizai == this.singleData.cd_yoki_hoso_shizai05).FirstOrDefault());
                    this.arrayData.Add(searchDetail.Where(x => x.cd_yoki_hoso_shizai == this.singleData.cd_yoki_hoso_shizai06).FirstOrDefault());
                    this.arrayData.Add(searchDetail.Where(x => x.cd_yoki_hoso_shizai == this.singleData.cd_yoki_hoso_shizai07).FirstOrDefault());
                    this.arrayData.Add(searchDetail.Where(x => x.cd_yoki_hoso_shizai == this.singleData.cd_yoki_hoso_shizai08).FirstOrDefault());
                    this.arrayData.Add(searchDetail.Where(x => x.cd_yoki_hoso_shizai == this.singleData.cd_yoki_hoso_shizai09).FirstOrDefault());
                    this.arrayData.Add(searchDetail.Where(x => x.cd_yoki_hoso_shizai == this.singleData.cd_yoki_hoso_shizai10).FirstOrDefault());
                }
            }
        }
        // Render single data
        public void renderSingle()
        {
            // for each in mapping and set the value to the sheet
            foreach (var prop in this.singleProperty)
            {
                prop.setValue(this.singleData, this.File.Excel, this.sheetName);
            }
        }
        // Render array data
        public void renderArray()
        {
            // for each in mapping and set the value to the sheet
            foreach (var prop in this.arrayProperty)
            {
                prop.splitPosition();
                for (var i = 0; i < arrayData.Count; i++)
                {
                    // bind #1 column
                    if (i % 2 == 0)
                    {
                        prop.setValue(this.arrayData[i], this.File.Excel, this.sheetName, (i / 2) * 31);
                    }
                }
            }
            // for each in mapping and set the value to the sheet
            foreach (var prop in this.arrayPropertyCol2)
            {
                prop.splitPosition();
                for (var i = 0; i < arrayData.Count; i++)
                {
                    // bind #2 column
                    if (i % 2 == 1)
                    {
                        prop.setValue(this.arrayData[i], this.File.Excel, this.sheetName, (i / 2) * 31);
                    }
                }
            }
        }
        // Add sheet, get value, bind data to sheet
        public YoukiHousouSheet(SeihoBunshoExcel file)
        {
            // Contruct the sheet
            this.contructSheet(file, YoukiHousouSheet.SHEET_NAME);
            this.arrayPropertyCol2 = new List<SeihoBunshoCell>();
            // Add single mapping property
            // 容器包装名
            this.singleProperty.Add(new SeihoBunshoCell() { position = "G4", property = "nm_yoki_hoso" });
            // Add array mapping property
            // 資材
            this.arrayProperty.Add(new SeihoBunshoCell() { position = "I7", property = "nm_yoki_hoso_shizai" });
            this.arrayPropertyCol2.Add(new SeihoBunshoCell() { position = "AA7", property = "nm_yoki_hoso_shizai" });
            // メーカー1
            this.arrayProperty.Add(new SeihoBunshoCell() { position = "I8", property = "nm_maker01" });
            this.arrayPropertyCol2.Add(new SeihoBunshoCell() { position = "AA8", property = "nm_maker01" });
            // メーカー2
            this.arrayProperty.Add(new SeihoBunshoCell() { position = "I9", property = "nm_maker02" });
            this.arrayPropertyCol2.Add(new SeihoBunshoCell() { position = "AA9", property = "nm_maker02" });
            // メーカー3
            this.arrayProperty.Add(new SeihoBunshoCell() { position = "I10", property = "nm_maker03" });
            this.arrayPropertyCol2.Add(new SeihoBunshoCell() { position = "AA10", property = "nm_maker03" });
            // メーカー4
            this.arrayProperty.Add(new SeihoBunshoCell() { position = "I11", property = "nm_maker04" });
            this.arrayPropertyCol2.Add(new SeihoBunshoCell() { position = "AA11", property = "nm_maker04" });
            // メーカー5
            this.arrayProperty.Add(new SeihoBunshoCell() { position = "I12", property = "nm_maker05" });
            this.arrayPropertyCol2.Add(new SeihoBunshoCell() { position = "AA12", property = "nm_maker05" });
            // 資材企画書番号1
            this.arrayProperty.Add(new SeihoBunshoCell() { position = "I13", property = "no_shizai_kikakusho01" });
            this.arrayPropertyCol2.Add(new SeihoBunshoCell() { position = "AA13", property = "no_shizai_kikakusho01" });
            // 資材企画書番号2
            this.arrayProperty.Add(new SeihoBunshoCell() { position = "I14", property = "no_shizai_kikakusho02" });
            this.arrayPropertyCol2.Add(new SeihoBunshoCell() { position = "AA14", property = "no_shizai_kikakusho02" });
            // 資材企画書番号3
            this.arrayProperty.Add(new SeihoBunshoCell() { position = "I15", property = "no_shizai_kikakusho03" });
            this.arrayPropertyCol2.Add(new SeihoBunshoCell() { position = "AA15", property = "no_shizai_kikakusho03" });
            // 資材企画書番号4
            this.arrayProperty.Add(new SeihoBunshoCell() { position = "I16", property = "no_shizai_kikakusho04" });
            this.arrayPropertyCol2.Add(new SeihoBunshoCell() { position = "AA16", property = "no_shizai_kikakusho04" });
            // 資材企画書番号5
            this.arrayProperty.Add(new SeihoBunshoCell() { position = "I17", property = "no_shizai_kikakusho05" });
            this.arrayPropertyCol2.Add(new SeihoBunshoCell() { position = "AA17", property = "no_shizai_kikakusho05" });
            // 材質1
            this.arrayProperty.Add(new SeihoBunshoCell() { position = "I18", property = "nm_zaishitsu01" });
            this.arrayPropertyCol2.Add(new SeihoBunshoCell() { position = "AA18", property = "nm_zaishitsu01" });
            // 材質2
            this.arrayProperty.Add(new SeihoBunshoCell() { position = "I19", property = "nm_zaishitsu02" });
            this.arrayPropertyCol2.Add(new SeihoBunshoCell() { position = "AA19", property = "nm_zaishitsu02" });
            // 材質3
            this.arrayProperty.Add(new SeihoBunshoCell() { position = "I20", property = "nm_zaishitsu03" });
            this.arrayPropertyCol2.Add(new SeihoBunshoCell() { position = "AA20", property = "nm_zaishitsu03" });
            // 材質4
            this.arrayProperty.Add(new SeihoBunshoCell() { position = "I21", property = "nm_zaishitsu04" });
            this.arrayPropertyCol2.Add(new SeihoBunshoCell() { position = "AA21", property = "nm_zaishitsu04" });
            // 材質5
            this.arrayProperty.Add(new SeihoBunshoCell() { position = "I22", property = "nm_zaishitsu05" });
            this.arrayPropertyCol2.Add(new SeihoBunshoCell() { position = "AA22", property = "nm_zaishitsu05" });
            // リサイクル表示1
            this.arrayProperty.Add(new SeihoBunshoCell() { position = "I23", property = "nm_recycle_hyoji01" });
            this.arrayPropertyCol2.Add(new SeihoBunshoCell() { position = "AA23", property = "nm_recycle_hyoji01" });
            // リサイクル表示2
            this.arrayProperty.Add(new SeihoBunshoCell() { position = "I24", property = "nm_recycle_hyoji02" });
            this.arrayPropertyCol2.Add(new SeihoBunshoCell() { position = "AA24", property = "nm_recycle_hyoji02" });
            // リサイクル表示3
            this.arrayProperty.Add(new SeihoBunshoCell() { position = "I25", property = "nm_recycle_hyoji03" });
            this.arrayPropertyCol2.Add(new SeihoBunshoCell() { position = "AA25", property = "nm_recycle_hyoji03" });
            // リサイクル表示4
            this.arrayProperty.Add(new SeihoBunshoCell() { position = "I26", property = "nm_recycle_hyoji04" });
            this.arrayPropertyCol2.Add(new SeihoBunshoCell() { position = "AA26", property = "nm_recycle_hyoji04" });
            // リサイクル表示5
            this.arrayProperty.Add(new SeihoBunshoCell() { position = "I27", property = "nm_recycle_hyoji05" });
            this.arrayPropertyCol2.Add(new SeihoBunshoCell() { position = "AA27", property = "nm_recycle_hyoji05" });
            // サイズ1
            this.arrayProperty.Add(new SeihoBunshoCell() { position = "I28", property = "nm_size01" });
            this.arrayPropertyCol2.Add(new SeihoBunshoCell() { position = "AA28", property = "nm_size01" });
            // サイズ2
            this.arrayProperty.Add(new SeihoBunshoCell() { position = "I29", property = "nm_size02" });
            this.arrayPropertyCol2.Add(new SeihoBunshoCell() { position = "AA29", property = "nm_size02" });
            // サイズ3
            this.arrayProperty.Add(new SeihoBunshoCell() { position = "I30", property = "nm_size03" });
            this.arrayPropertyCol2.Add(new SeihoBunshoCell() { position = "AA30", property = "nm_size03" });
            // サイズ4
            this.arrayProperty.Add(new SeihoBunshoCell() { position = "I31", property = "nm_size04" });
            this.arrayPropertyCol2.Add(new SeihoBunshoCell() { position = "AA31", property = "nm_size04" });
            // サイズ5
            this.arrayProperty.Add(new SeihoBunshoCell() { position = "I32", property = "nm_size05" });
            this.arrayPropertyCol2.Add(new SeihoBunshoCell() { position = "AA32", property = "nm_size05" });
            // Free title
            this.arrayProperty.Add(new SeihoBunshoCell() { position = "B33", property = "nm_free_title_komoku" });
            // Free type
            this.arrayProperty.Add(new SeihoBunshoCell() { position = "I33", property = "nm_free_komoku", fitConetent = SeihoBunshoCell.FIT22 });
            this.arrayPropertyCol2.Add(new SeihoBunshoCell() { position = "AA33", property = "nm_free_komoku", fitConetent = SeihoBunshoCell.FIT22 });

            // Get sheet data
            this.getData();
        }
        // Bind data to the sheet
        public void Bind()
        {
            // Bind header data
            this.renderHeader();
            // Bind data
            this.renderSingle();
            //// Bind data
            this.renderArray();
        }
    }

    /// <summary>
    /// TAB 3 原料・機械設備・製造方法・表示事項 Excel sheet
    /// </summary>
    public class GenryoSetsubiSheet : SeihoBunshoSheet
    {
        // Sheet name in template
        public static string SHEET_NAME = "原料・機械設備・製造方法・表示事項";
        public static string TAB_NAME = "GenryoSetsubi";
        // Sheet data
        public ma_seiho_bunsho_genryo_setsubi singleData { get; set; }
        // Get data for bind TAB 1
        public void getData()
        {
            using (ShohinKaihatsuEntities context = new ShohinKaihatsuEntities())
            {
                this.singleData = context.ma_seiho_bunsho_genryo_setsubi.Where(x => x.no_seiho == this.Conditions.no_seiho).FirstOrDefault();
            }
        }
        // Render single data
        public void renderSingle()
        {
            // for each in mapping and set the value to the sheet
            foreach (var prop in this.singleProperty)
            {
                prop.setValue(this.singleData, this.File.Excel, this.sheetName);
            }
        }
        // Add sheet, get value, bind data to sheet
        public GenryoSetsubiSheet(SeihoBunshoExcel file)
        {
            // Contruct the sheet
            this.contructSheet(file, GenryoSetsubiSheet.SHEET_NAME);
            // Add single mapping property
            // 製法番号
            this.singleProperty.Add(new SeihoBunshoCell() { position = "C5", property = "nm_genryo_free_komoku", fitConetent = SeihoBunshoCell.FIT25 });
            // 製法名
            this.singleProperty.Add(new SeihoBunshoCell() { position = "Y5", property = "nm_kikai_setsubi_free_komoku", fitConetent = SeihoBunshoCell.FIT25 });
            // 元になる製法からの変更点(改版の場合)
            this.singleProperty.Add(new SeihoBunshoCell() { position = "C64", property = "nm_seizo_hoho_free_komoku", fitConetent = SeihoBunshoCell.FIT25 });
            // 製品特徴
            this.singleProperty.Add(new SeihoBunshoCell() { position = "Y64", property = "nm_hyoji_jiko_free_komoku", fitConetent = SeihoBunshoCell.FIT25 });
            // Get sheet data
            this.getData();
        }
        // Bind data to the sheet
        public void Bind()
        {
            // Bind header data
            this.renderHeader();
            // Bind data
            this.renderSingle();
        }
    }

    /// <summary>
    /// TAB 4 原料・機械設備・製造方法・表示事項 Excel sheet
    /// </summary>
    public class HaigoSeizoChuiJikoSheet : SeihoBunshoSheet
    {
        // Sheet name in template
        public static string SHEET_NAME = "配合・製造上の注意事項";
        public static string TAB_NAME = "HaigoSeizoChuiJiko";
        // Const
        public static string UN_CALC = "計算不可";
        // Sheet data
        public ma_seiho_bunsho_haigo_chui_EXT singleData { get; set; }
        // Get data for bind TAB 1
        public void getData()
        {
            using (ShohinKaihatsuEntities context = new ShohinKaihatsuEntities())
            {
                this.singleData = new ma_seiho_bunsho_haigo_chui_EXT();
                var currentData = context.ma_seiho_bunsho_haigo_chui.Where(x => x.no_seiho == this.Conditions.no_seiho).FirstOrDefault();
                bool isGramDisplayMode = false;
                if (currentData == null)
                {
                    currentData = new ma_seiho_bunsho_haigo_chui();
                }
                if (currentData != null)
                {
                    if (!this.Conditions.isApplied)
                    {
                        var seihoData = context.ma_seiho.Where(x => x.no_seiho == Conditions.no_seiho).FirstOrDefault();
                        if (seihoData != null && seihoData.cd_shain != null)
                        {
                            var shisakuData = context.tr_shisakuhin.Where(x => x.cd_shain == seihoData.cd_shain && x.nen == seihoData.nen && x.no_oi == seihoData.no_oi).FirstOrDefault();
                            if (shisakuData != null)
                            {
                                var tab4Controller = new _300_SeihoBunshoSakusei_HaigoSeizoChuiJiko_TabController();
                                HaigoSeizoChuiJikoRequest tab4Conditions = new HaigoSeizoChuiJikoRequest()
                                {
                                    no_seiho = Conditions.no_seiho,
                                    cd_shain = seihoData.cd_shain,
                                    nen = seihoData.nen,
                                    no_oi = seihoData.no_oi,
                                    seq_shisaku = seihoData.seq_shisaku,
                                    pt_kotei = shisakuData.pt_kotei,
                                    cd_tani_g = Conditions.cd_tani_g,
                                    kbn_pt_kotei = Conditions.kbn_pt_kotei,
                                    kbn_chomi = Conditions.kbn_chomi,
                                    kbn_suisho = Conditions.kbn_suisho,
                                    kbn_yusho = Conditions.kbn_yusho
                                };
                                var calcInfo = tab4Controller.ChangeShisaku(tab4Conditions);
                                if (calcInfo.currentData != null)
                                {
                                    if (calcInfo.cd_tani == Conditions.cd_tani_g)
                                    {
                                        isGramDisplayMode = true;
                                    }
                                    currentData.su_haigo_suiso_g = calcInfo.currentData.su_haigo_suiso_g;
                                    currentData.su_haigo_suiso_ml = calcInfo.currentData.su_haigo_suiso_ml;
                                    currentData.su_haigo_suiso_hiju = calcInfo.currentData.su_haigo_suiso_hiju;
                                    currentData.su_haigo_yuso_g = calcInfo.currentData.su_haigo_yuso_g;
                                    currentData.su_haigo_yuso_ml = calcInfo.currentData.su_haigo_yuso_ml;
                                    currentData.su_haigo_yuso_hiju = calcInfo.currentData.su_haigo_yuso_hiju;
                                    currentData.su_haigo_gokei_g = calcInfo.currentData.su_haigo_gokei_g;
                                    currentData.su_haigo_gokei_ml = calcInfo.currentData.su_haigo_gokei_ml;
                                    currentData.su_haigo_gokei_hiju = calcInfo.currentData.su_haigo_gokei_hiju;
                                }
                            }
                        }
                    }
                    // If pt_kotei != 002 -> set default value to Blank
                    if (currentData.pt_kotei != Conditions.pt_kotei_chomieki_2)
                    {
                        foreach (var mapping in this.singleProperty)
                        {
                            mapping.defaultNull = String.Empty;
                        }
                    }
                    // Display － for null value
                    if (this.Conditions.isApplied || isGramDisplayMode)
                    {
                        foreach (var mapping in this.singleProperty)
                        {
                            switch (mapping.property)
                            {
                                case "su_haigo_suiso_ml":
                                case "su_haigo_yuso_ml":
                                case "su_haigo_gokei_ml":
                                case "su_haigo_suiso_hiju":
                                case "su_haigo_yuso_hiju":
                                case "su_haigo_gokei_hiju":
                                    mapping.defaultNull = "－";
                                    break;
                            }
                        }
                    }
                    // 
                    DataCopier.ReFill(currentData, this.singleData);
                    this.singleData.getNameKotei(this.Conditions.cd_category_kotei);
                }
            }
        }
        // Render single data
        public void renderSingle()
        {
            // for each in mapping and set the value to the sheet
            foreach (var prop in this.singleProperty)
            {
                prop.setValue(this.singleData, this.File.Excel, this.sheetName);
            }

            if (this.singleData.pt_kotei != Conditions.pt_kotei_chomieki_2)
            {
                var worksheet = this.File.Excel.Worksheet(this.sheetName);
                worksheet.Range("C7:V11").Clear();
            }
        }
        // Add sheet, get value, bind data to sheet
        public HaigoSeizoChuiJikoSheet(SeihoBunshoExcel file)
        {
            // Contruct the sheet
            this.contructSheet(file, HaigoSeizoChuiJikoSheet.SHEET_NAME);
            // Add single mapping property
            // 工程パターン
            this.singleProperty.Add(new SeihoBunshoCell() { position = "I5", property = "nm_kotei"});
            // ドレッシング充填量（１本あたり）
            this.singleProperty.Add(new SeihoBunshoCell() { position = "G9", property = "su_haigo_suiso_g", type = SeihoBunshoCell.NUMBER_TYPE, format = "#,0.0", defaultNull = HaigoSeizoChuiJikoSheet.UN_CALC });
            this.singleProperty.Add(new SeihoBunshoCell() { position = "L9", property = "su_haigo_suiso_ml", type = SeihoBunshoCell.NUMBER_TYPE, format = "#,0.0", defaultNull = HaigoSeizoChuiJikoSheet.UN_CALC });
            this.singleProperty.Add(new SeihoBunshoCell() { position = "Q9", property = "su_haigo_suiso_hiju", type = SeihoBunshoCell.NUMBER_TYPE, format = "#,0.000", defaultNull = HaigoSeizoChuiJikoSheet.UN_CALC });
            this.singleProperty.Add(new SeihoBunshoCell() { position = "G10", property = "su_haigo_yuso_g", type = SeihoBunshoCell.NUMBER_TYPE, format = "#,0.0", defaultNull = HaigoSeizoChuiJikoSheet.UN_CALC });
            this.singleProperty.Add(new SeihoBunshoCell() { position = "L10", property = "su_haigo_yuso_ml", type = SeihoBunshoCell.NUMBER_TYPE, format = "#,0.0", defaultNull = HaigoSeizoChuiJikoSheet.UN_CALC });
            this.singleProperty.Add(new SeihoBunshoCell() { position = "Q10", property = "su_haigo_yuso_hiju", type = SeihoBunshoCell.NUMBER_TYPE, format = "#,0.000", defaultNull = HaigoSeizoChuiJikoSheet.UN_CALC });
            this.singleProperty.Add(new SeihoBunshoCell() { position = "G11", property = "su_haigo_gokei_g", type = SeihoBunshoCell.NUMBER_TYPE, format = "#,0.0", defaultNull = HaigoSeizoChuiJikoSheet.UN_CALC });
            this.singleProperty.Add(new SeihoBunshoCell() { position = "L11", property = "su_haigo_gokei_ml", type = SeihoBunshoCell.NUMBER_TYPE, format = "#,0.0", defaultNull = HaigoSeizoChuiJikoSheet.UN_CALC });
            this.singleProperty.Add(new SeihoBunshoCell() { position = "Q11", property = "su_haigo_gokei_hiju", type = SeihoBunshoCell.NUMBER_TYPE, format = "#,0.000", defaultNull = HaigoSeizoChuiJikoSheet.UN_CALC });
            // 配合に対する特記事項
            this.singleProperty.Add(new SeihoBunshoCell() { position = "D14", property = "nm_haigo_free_tokki_jiko", fitConetent = SeihoBunshoCell.FIT25 });
            // 製造上の注意点
            this.singleProperty.Add(new SeihoBunshoCell() { position = "Y5", property = "nm_chuiten_free_komoku", fitConetent = SeihoBunshoCell.FIT25 });
            // Get sheet data
            this.getData();
        }
        // Bind data to the sheet
        public void Bind()
        {
            // Bind header data
            this.renderHeader();
            // Bind data
            this.renderSingle();
        }
    }

    /// <summary>
    /// TAB 5 製造工程図 Excel sheet
    /// </summary>
    public class SeizoKoteizuSheet : SeihoBunshoSheet
    {
        // Sheet name in template
        public static string SHEET_NAME = "製造工程図";
        public static string TAB_NAME = "SeizoKoteizu";
        public static string GENRYO_FREE = "999999";
        // Const
        public const short GENRYO = 1;
        public const short KIKAI = 2;
        public const short KOTEI = 3;
        public const short FURI_NYURYOKU = 4;
        public const short SHIKAKE_GENRYO = 5;
        public const short SHIKAKE_KIKAI = 6;
        public const short SHIKAKE_KOTEI = 7;
        public const short SHIKAKE_FURI_NYURYOKU = 8;
        public const int BEG_DETAIL_ROW = 6;
        public const string MARK_COL_BEG = "B";
        public const string MARK_COL_END = "C";
        public const string ARROW_COL = "D";
        public const string GENRYO_COL_BEG = "E";
        public const string GENRYO_COL_END = "AR";
        public const XLBorderStyleValues BORDER_STYLE = XLBorderStyleValues.Thin;         
        public const XLBorderStyleValues BORDER_NONE = XLBorderStyleValues.None;          
        public const string GRAY_COLOR = "#d3d3d3";     //lightgray
        public const string DOWN_ARROW = "↓";
        public const string LEFT_ARROW = "←";

        // Sheet data
        public SeizoKoteizuTabSearchRespone data { get; set; }
        // Get data for bind
        public void getData()
        {
            this.data = _300_SeihoBunshoSakusei_SeizoKoteizu_TabController.getExcelData(this.Conditions);
        }
        // Sheet name = '製造工程図' + cd_hin
        private string getSheetName(decimal cd_hin)
        {
            return SeizoKoteizuSheet.SHEET_NAME + "（" + cd_hin.ToString() + "）";
        }
        // Get list of sheet name
        public List<string> getSheetName()
        {
            List<string> result = new List<string>();
            if (this.data != null && this.data.headerData != null)
            { 
                foreach(var haigo in this.data.headerData)
                {
                    result.Add(getSheetName(haigo.cd_hin ?? 0));
                }
            }
            return result;
        }
        // cd_hin + \n + nm_hin
        private string getHaigoText(sp_shohinkaihatsu_search_300_seizokoteizu_header_Result haigoData)
        {
            return CommonController.getFullString((haigoData.cd_hin ?? 0).ToString(), this.Conditions.su_code_standard) + "\n" + haigoData.nm_hin ?? "";
        }
        // cd_input_genryo + "    " + nm_input_genryo
        private string getKoteiText(ma_seiho_bunsho_koteizu_b haigoData)
        {
            if (haigoData.kbn_nyuryoku == GENRYO || haigoData.kbn_nyuryoku == SHIKAKE_GENRYO)
            {
                var name = haigoData.cd_input_genryo;
                if (name != null)
                {
                    name = CommonController.getFullString((haigoData.cd_input_genryo).ToString(), this.Conditions.su_code_standard) + "　　" + haigoData.nm_input_genryo ?? "";
                }
                else 
                {
                    name = haigoData.nm_input_genryo;
                }
                return name;
            }
            else
            {
                return haigoData.nm_input_genryo ?? "";
            }
        }
        // Render single data
        public void renderSingle(sp_shohinkaihatsu_search_300_seizokoteizu_header_Result haigoData)
        {
            var singleData = haigoData;
            if (singleData == null) 
            {
                return;
            }
            // Get sheet name base on cd_hin
            string sheetName = this.getSheetName(haigoData.cd_hin ?? 0);
            this.sheetName = sheetName;
            // Bind header data
            this.renderHeader();
            // for each in mapping and set the value to the sheet
            foreach (var prop in this.singleProperty)
            {
                prop.setValue(this.getHaigoText(singleData), this.File.Excel, sheetName, 0, false);
            }
        }
        // Set cell style before bind data
        private void prepairCell(ma_seiho_bunsho_koteizu_b koteizuData, string sheetName, int distance)
        {
            string strDistance = (BEG_DETAIL_ROW + distance).ToString();
            string color = String.Empty;
            this.File.copyTempRow(sheetName, MARK_COL_BEG, GENRYO_COL_END, BEG_DETAIL_ROW + distance);

            color = koteizuData.id_color;
            // Set border style and background color
            switch (koteizuData.kbn_nyuryoku)
            { 
                // For genryo
                case GENRYO:
                case SHIKAKE_GENRYO:
                    // Border style
                    this.File.setBoderStyle(sheetName, MARK_COL_BEG + strDistance, MARK_COL_END + strDistance, BORDER_STYLE, BORDER_STYLE, BORDER_STYLE, BORDER_STYLE);
                    this.File.setBoderStyle(sheetName, ARROW_COL + strDistance, ARROW_COL + strDistance, BORDER_STYLE, BORDER_NONE, BORDER_STYLE, BORDER_STYLE);
                    this.File.setBoderStyle(sheetName, GENRYO_COL_BEG + strDistance, GENRYO_COL_END + strDistance, BORDER_STYLE, BORDER_STYLE, BORDER_STYLE, BORDER_NONE);
                    // Background color
                    // Check if genryo is mishiyo
                    if (this.data.headerData != null)
                    {
                        if (koteizuData.cd_input_genryo == null || koteizuData.cd_input_genryo.IndexOf(GENRYO_FREE) >= 0)
                        {
                            color = koteizuData.id_color;
                        }
                        else
                        {
                            var existHeader = this.data.headerData.Where(x => CommonController.getFullString((x.cd_hin ?? 0).ToString(), Conditions.su_code_standard) == koteizuData.cd_input_genryo).FirstOrDefault();
                            if (existHeader != null && existHeader.flg_mishiyo == 1)
                            {
                                color = GRAY_COLOR;
                            }
                            else
                            {
                                decimal? cd_input_genryo = null;
                                if (koteizuData.cd_input_genryo != "" && koteizuData.cd_input_genryo != null) 
                                {
                                    cd_input_genryo = decimal.Parse(koteizuData.cd_input_genryo);
                                }
                                var existGenryo = this.data.masterData.genryoData.Where(x => x.cd_haigo == koteizuData.cd_haigo && x.cd_hin == cd_input_genryo).FirstOrDefault();
                                if (existGenryo == null || existGenryo.flg_mishiyo == 1)
                                {
                                    color = GRAY_COLOR;
                                }
                                else
                                {
                                    color = koteizuData.id_color;
                                }
                            }
                        }
                    }
                    break;
                case KIKAI:
                case SHIKAKE_KIKAI:
                    this.File.setUnderLineStyle(sheetName, GENRYO_COL_BEG + strDistance, GENRYO_COL_END + strDistance);
                    break;
                default:
                    color = koteizuData.id_color;
                    break;
            }
            if (color != null)
            {
                // Set background color
                this.File.setBackgroundColor(sheetName, MARK_COL_BEG + strDistance, GENRYO_COL_END + strDistance, XLColor.FromHtml(color));
            }
            if (koteizuData.kbn_nyuryoku >= SHIKAKE_GENRYO)
            {
                this.File.setForeColor(sheetName, MARK_COL_BEG + strDistance, GENRYO_COL_END + strDistance, XLColor.Red);
            }
            koteizuData.id_color = color;
        }
        // Get arrow of genryo
        public string getArrow(ma_seiho_bunsho_koteizu_b koteizu)
        {
            if (koteizu.kbn_nyuryoku < SHIKAKE_GENRYO)
            {
                return DOWN_ARROW;
            }
            return LEFT_ARROW;
        }
        // Render array data
        public void renderArray()
        {
            var arrayData = this.data.detailData;
            List<GenryoIndex> genryoIndex = new List<GenryoIndex>();
            // for each in mapping and set the value to the sheet
            foreach (var prop in this.arrayProperty)
            {
                prop.splitPosition();
            }
            // 
            for (var i = 0; i < arrayData.Count; i++)
            {
                var koteizuData = arrayData[i];
                // Get sheet name base on cd_hin
                string sheetName = this.getSheetName(koteizuData.cd_haigo);
                GenryoIndex index = genryoIndex.Where(x => x.sheetName == sheetName).FirstOrDefault();
                if (index == null)
                {
                    index = new GenryoIndex() { sheetName = sheetName, index = 0 };
                    genryoIndex.Add(index);
                }
                this.prepairCell(koteizuData, sheetName, index.index);
                foreach (var prop in this.arrayProperty)
                {
                    if (prop.property == "nm_free_mark")
                    {
                        prop.setValue(koteizuData, this.File.Excel, sheetName, index.index);
                    }
                    else if (prop.property == "arrow")
                    {
                        prop.setValue(getArrow(koteizuData), this.File.Excel, sheetName, index.index, false);
                    }
                    else if (prop.property == "nm_haigo")
                    {
                        prop.setValue(getKoteiText(koteizuData), this.File.Excel, sheetName, index.index, false);
                    }
                }
                index.index++;
            }
            setHeaderBackgroundColor();
        }
        // Set header background color base on detail
        private void setHeaderBackgroundColor()
        {
            int flex = this.Conditions.defaultColors.Count;
            if (this.data.headerData != null)
            {
                for (var i = 0; i < this.data.headerData.Count; i++)
                {
                    var hData = this.data.headerData[i];
                    if (hData.level.Length == 2)
                    {
                        continue;
                    }
                    string color = this.Conditions.defaultColors[i % flex];
                    //if (this.data.detailData != null)
                    //{
                    //    string cd_hin = CommonController.getFullString((this.data.headerData[i].cd_hin ?? 0).ToString(), Conditions.su_code_standard);
                    //    var exitsDetail = this.data.detailData.Where(x => x.cd_input_genryo == cd_hin).FirstOrDefault();
                    //    if (exitsDetail != null)
                    //    {
                    //        color = exitsDetail.id_color;
                    //    }
                    //}
                    if (hData.flg_mishiyo == 1)
                    {
                        color = GRAY_COLOR;
                    }
                    this.File.setBackgroundColor(getSheetName(hData.cd_hin ?? 0), "B4", "AR4", XLColor.FromHtml(color));
                }
            }
        }
        // Add sheet, get value, bind data to sheet
        public SeizoKoteizuSheet(SeihoBunshoExcel file)
        {
            // Contruct the sheet
            this.contructSheet(file, SeizoKoteizuSheet.SHEET_NAME);
            // Add single mapping property
            // 工程パターン
            this.singleProperty.Add(new SeihoBunshoCell() { position = "B4", property = "nm_haigo" });
            // Add array mapping property
            // Free mark
            this.arrayProperty.Add(new SeihoBunshoCell() { position = "B6", property = "nm_free_mark" });
            // Arrow
            this.arrayProperty.Add(new SeihoBunshoCell() { position = "D6", property = "arrow" });
            // haigo text
            this.arrayProperty.Add(new SeihoBunshoCell() { position = "E6", property = "nm_haigo" });
            // Get sheet data
            this.getData();
        }
        // Bind data to the sheet
        public void Bind()
        {
            // Bind haigo data
            if (this.data != null && this.data.headerData != null)
            {
                foreach (var haigo in this.data.headerData)
                {
                    this.renderSingle(haigo);
                }
                // Generate empty sheet
                if (this.data.headerData.Count == 0)
                {
                    this.sheetName = SHEET_NAME;
                    this.renderHeader();
                }
            }
            // Bind koteizu data
            this.renderArray();
        }
    }

    /// <summary>
    /// TAB 6 製品特性値 Excel sheet
    /// </summary>
    public class SeihinKikakuanSheet : SeihoBunshoSheet
    {
        // Sheet name in template
        public static string SHEET_NAME = "製品特性値";
        public static string TAB_NAME = "SeihinKikakuan";
        public static string RONRICHI_CLASS = "ronrichi";
        public static string KENSHISAKU_CLASS = "kenshisakuhin";
        // Dataset
        public sp_shohinkaihatsu_search_300_seihinkikakuan_excel_Result singleData { get; set; }
        public List<ma_seiho_bunsho_tokuseichi> arrayData { get; set; }
        public tr_shisaku shisakuData { get; set; }
        // Get data for bind
        public void getData()
        {
            using(ShohinKaihatsuEntities context = new ShohinKaihatsuEntities())
            {
                this.singleData = context.sp_shohinkaihatsu_search_300_seihinkikakuan_excel(
                                                                                                Conditions.no_seiho, 
                                                                                                Conditions.kbn_shomikikan, 
                                                                                                Conditions.kbn_shomikikan_seizo_fukumu, 
                                                                                                Conditions.kbn_shomikikan_tani, 
                                                                                                Conditions.kbn_meisho, 
                                                                                                Conditions.kbn_yoryo_tani,
                                                                                                Conditions.toriatsukai_ondo
                                                                                           ).FirstOrDefault();
                this.arrayData = context.ma_seiho_bunsho_tokuseichi.Where(x => x.no_seiho == Conditions.no_seiho).ToList();
                this.shisakuData = context.tr_shisaku.Where(x => x.cd_shain == this.headerData.cd_shain && x.nen == this.headerData.nen && x.no_oi == this.headerData.no_oi && x.seq_shisaku == this.headerData.seq_shisaku).FirstOrDefault();
            }
        }
        // Render single data
        public void renderSingle()
        {
            // for each in mapping and set the value to the sheet
            foreach (var prop in this.singleProperty)
            {
                if (prop.property == "su_naiyoryo")
                {
                    string data = this.singleData.su_naiyoryo;
                    if (data == null)
                    {
                        data = string.Empty;
                    }
                    decimal n;
                    if (decimal.TryParse(data, out n))
                    {
                        data = n.ToString("#,0.###########");
                    }
                    data += this.singleData.nm_naiyoryo_tani;
                    prop.setValue(data, this.File.Excel, this.sheetName, 0, false);
                }
                else
                {
                    prop.setValue(this.singleData, this.File.Excel, this.sheetName);
                }
            }
        }

        // Get haigo kyoda name
        // cd_category: 111
        private string getHaigoKyoName(string haigoKyodo)
        {
            using (ShohinKaihatsuEntities context = new ShohinKaihatsuEntities())
            {
                string result = context.ma_literal.Where(x => x.cd_category == this.Conditions.kbn_haigo_kyodo && x.cd_literal == haigoKyodo).Select(x => x.nm_literal).FirstOrDefault();
                if (result != null)
                {
                    return result;
                }
                return string.Empty;
            }
        }
        // Render array data
        public void renderArray()
        {
            // For applied bunsho
            if (Conditions.isApplied)
            {
                // for each in mapping and set the value to the sheet
                foreach (var prop in this.arrayProperty)
                {
                    prop.splitPosition();
                    for (var i = 0; i < arrayData.Count; i++)
                    {
                        prop.setValue(this.arrayData[i], this.File.Excel, this.sheetName, this.arrayData[i].no_seq_seihintokusechi);
                    }
                }
            }
            // For not apply bunsho
            else
            {
                SeihoBunshoCell flexCellRonrichi = new SeihoBunshoCell() { position = "U31", property = "nm_seihintokusechi_ronrichi" };
                SeihoBunshoCell flexCellKenShisakuhin = new SeihoBunshoCell() { position = "Y31", property = "nm_seihintokusechi_kenshisakuhin" };
                foreach (var prop in this.arrayProperty)
                {
                    prop.splitPosition();
                }
                for (var i = 0; i < arrayData.Count; i++)
                {
                    var data = this.arrayData[i];
                    // Using shisakuhin mapping like display in the page
                    var mapping = Conditions.shisakuPropMapping.Where(x => x.key == data.kbn_seihintokuseichi).FirstOrDefault();
                    // for each in mapping and set the value to the sheet
                    foreach (var prop in this.arrayProperty)
                    {
                        switch (prop.property)
                        {
                            case "nm_seihintokusechi_ronrichi":
                                if (mapping != null && mapping.classed == RONRICHI_CLASS && this.shisakuData != null)
                                {
                                    flexCellRonrichi.property = mapping.property;
                                    flexCellRonrichi.splitPosition();
                                    if (mapping.property == "haigo_kyodo")
                                    {
                                        string text = this.getHaigoKyoName(this.shisakuData.haigo_kyodo);
                                        flexCellRonrichi.setValue(text, this.File.Excel, this.sheetName, this.arrayData[i].no_seq_seihintokusechi, false);
                                    }
                                    else
                                    {
                                        flexCellRonrichi.setValue(this.shisakuData, this.File.Excel, this.sheetName, this.arrayData[i].no_seq_seihintokusechi);
                                    }
                                }
                                break;
                            case "nm_seihintokusechi_kenshisakuhin":
                                if (mapping != null && mapping.classed == KENSHISAKU_CLASS && this.shisakuData != null)
                                {
                                    flexCellKenShisakuhin.property = mapping.property;
                                    flexCellKenShisakuhin.splitPosition();
                                    flexCellKenShisakuhin.setValue(this.shisakuData, this.File.Excel, this.sheetName, this.arrayData[i].no_seq_seihintokusechi);
                                }
                                break;
                            default:
                                prop.setValue(data, this.File.Excel, this.sheetName, this.arrayData[i].no_seq_seihintokusechi);
                                break;
                        }
                    }
                }
            }
        }
        // Display / hide 粘度
        private void processSokutei()
        {
            if (this.File.headerData.pt_kotei == this.Conditions.pt_kotei_sonohokaeki || this.headerData.cd_shain == null)
            {
                for (var i = 18; i < 28; i++)
                {
                    this.File.Excel.Worksheet(this.sheetName).Row(18).Delete();
                }
            }
        }
        // Add sheet, get value, bind data to sheet
        public SeihinKikakuanSheet(SeihoBunshoExcel file)
        {
            // Contruct the sheet
            this.contructSheet(file, SeihinKikakuanSheet.SHEET_NAME);
            // Add single mapping property
            // 試作Ｎｏ
            this.singleProperty.Add(new SeihoBunshoCell() { position = "F4", property = "no_shisaku" });
            this.singleProperty.Add(new SeihoBunshoCell() { position = "O4", property = "nm_shisaku" });
            // 内容量
            this.singleProperty.Add(new SeihoBunshoCell() { position = "F6", property = "su_naiyoryo" });
            // 賞味期間
            this.singleProperty.Add(new SeihoBunshoCell() { position = "H11", property = "nm_shomikikan" });
            this.singleProperty.Add(new SeihoBunshoCell() { position = "M11", property = "nm_shomikikan_seizo_fukumu" });
            this.singleProperty.Add(new SeihoBunshoCell() { position = "U11", property = "su_shomikikan_free_input", type = SeihoBunshoCell.COMMA_STRING });
            this.singleProperty.Add(new SeihoBunshoCell() { position = "X11", property = "nm_shomikikan_tani" });
            // 取扱温度
            this.singleProperty.Add(new SeihoBunshoCell() { position = "H13", property = "nm_toriatsukai_ondo" });
            // 名称（品名）
            this.singleProperty.Add(new SeihoBunshoCell() { position = "H16", property = "nm_meisho" });
            this.singleProperty.Add(new SeihoBunshoCell() { position = "M16", property = "nm_meisho_hinmei" });
            // 粘度
            this.singleProperty.Add(new SeihoBunshoCell() { position = "AE21", property = "su_nendo_sokuteichi", type = SeihoBunshoCell.COMMA_STRING });
            this.singleProperty.Add(new SeihoBunshoCell() { position = "AE23", property = "dt_nendo_sokuteichi" });
            // 製品特性値
            this.singleProperty.Add(new SeihoBunshoCell() { position = "AG31", property = "nm_seihintokusechi_title1" });
            this.singleProperty.Add(new SeihoBunshoCell() { position = "AK31", property = "nm_seihintokusechi_title2" });
            this.singleProperty.Add(new SeihoBunshoCell() { position = "AO31", property = "nm_seihintokusechi_title3" });
            // Add array mapping property
            // 製品特性値
            // 項目
            this.arrayProperty.Add(new SeihoBunshoCell() { position = "D31", property = "nm_seihintokusechi_combo_input" });
            // 理論値
            this.arrayProperty.Add(new SeihoBunshoCell() { position = "U31", property = "nm_seihintokusechi_ronrichi" });
            // 研試作品
            this.arrayProperty.Add(new SeihoBunshoCell() { position = "Y31", property = "nm_seihintokusechi_kenshisakuhin" });
            // TP品
            this.arrayProperty.Add(new SeihoBunshoCell() { position = "AC31", property = "nm_seihintokusechi_tp" });
            // FREE
            this.arrayProperty.Add(new SeihoBunshoCell() { position = "AG31", property = "nm_seihintokusechi_input1" });
            this.arrayProperty.Add(new SeihoBunshoCell() { position = "AK31", property = "nm_seihintokusechi_input2" });
            this.arrayProperty.Add(new SeihoBunshoCell() { position = "AO31", property = "nm_seihintokusechi_input3" });
            // Get sheet data
            this.getData();
        }
        // Bind data to the sheet
        public void Bind()
        {
            // Bind header data
            this.renderHeader();
            // Bind data
            this.renderSingle();
            this.renderArray();
            // Display / Hide 粘度
            this.processSokutei();
        }
    }

    /// <summary>
    /// TAB 6 製品規格案及び取扱基準 Excel sheet
    /// </summary>
    public class SeihinKikakuanOyobiSheet : SeihoBunshoSheet
    {
        // Sheet name in template
        public static string SHEET_NAME = "製品規格案及び取扱基準";
        public static string TAB_NAME = "SeihinKikakuan";
        // Sheet data
        public sp_shohinkaihatsu_search_300_seihinkikakuan_excel_Result singleData { get; set; }
        // Get data for bind
        public void getData(sp_shohinkaihatsu_search_300_seihinkikakuan_excel_Result singleData)
        {
            this.singleData = singleData;
        }
        // Render single data
        public void renderSingle()
        {
            // for each in mapping and set the value to the sheet
            foreach (var prop in this.singleProperty)
            {
                prop.setValue(this.singleData, this.File.Excel, this.sheetName);
            }
        }
        // Add sheet, get value, bind data to sheet
        public SeihinKikakuanOyobiSheet(SeihoBunshoExcel file, sp_shohinkaihatsu_search_300_seihinkikakuan_excel_Result singleData)
        {
            // Contruct the sheet
            this.contructSheet(file, SeihinKikakuanOyobiSheet.SHEET_NAME);
            // Add single mapping property
            // 特性値
            this.singleProperty.Add(new SeihoBunshoCell() { position = "C5", property = "nm_free_tokuseichi", fitConetent = SeihoBunshoCell.FIT25 });
            // 微生物
            this.singleProperty.Add(new SeihoBunshoCell() { position = "Y5", property = "nm_free_biseibutsu", fitConetent = SeihoBunshoCell.FIT25 });
            // Get sheet data
            this.getData(singleData);
        }
        // Bind data to the sheet
        public void Bind()
        {
            // Bind header data
            this.renderHeader();
            // Bind data
            this.renderSingle();
        }
    }

    /// <summary>
    /// TAB 7 関連特許 Excel sheet
    /// </summary>
    public class KanrenTokkyoSheet : SeihoBunshoSheet
    {
        // Sheet name in template
        public static string SHEET_NAME = "関連特許";
        public static string TAB_NAME = "SeihojyoKakuninJiko";
        // Sheet data
        public ma_seiho_bunsho_kakuninjiko singleData { get; set; }
        // Get data for bind
        public void getData()
        {
            using (ShohinKaihatsuEntities context = new ShohinKaihatsuEntities())
            {
                this.singleData = context.ma_seiho_bunsho_kakuninjiko.Where(x => x.no_seiho == this.Conditions.no_seiho).FirstOrDefault();
                if (this.singleData == null)
                {
                    this.singleData = Conditions.kakuninJikoDefaultValue;
                }
                if (this.singleData == null)
                {
                    this.singleData = new ma_seiho_bunsho_kakuninjiko();
                }
            }
        }
        // Render single data
        public void renderSingle()
        {
            // Prepair 関連特許 data
            string nm_free_kanren_tokkyo = SeihoBunshoCell.GetFix25(this.singleData.nm_free_kanren_tokkyo);
            string nm_free_kanren_tokkyo_2 = string.Empty;
            if (nm_free_kanren_tokkyo != null)
            {
                int lineCount = 0;
                for (var i = 0; i < nm_free_kanren_tokkyo.Length; i++)
                {
                    if (nm_free_kanren_tokkyo[i] == '\n')
                    {
                        lineCount++;
                    }
                    if (lineCount >= 65)
                    {
                        nm_free_kanren_tokkyo_2 = nm_free_kanren_tokkyo.Substring(i + 1, nm_free_kanren_tokkyo.Length - i - 1);
                        nm_free_kanren_tokkyo = nm_free_kanren_tokkyo.Substring(0, i);
                        break;
                    }
                }
            }
            // for each in mapping and set the value to the sheet
            foreach (var prop in this.singleProperty)
            {
                if (prop.property == "nm_free_kanren_tokkyo")
                {
                    prop.setValue(nm_free_kanren_tokkyo, this.File.Excel, this.sheetName, 0, false);
                }
                else if (prop.property == "nm_free_kanren_tokkyo_2")
                {
                    prop.setValue(nm_free_kanren_tokkyo_2, this.File.Excel, this.sheetName, 0, false);
                }
                else
                {
                    prop.setValue(this.singleData, this.File.Excel, this.sheetName);
                }
            }

        }
        // Add sheet, get value, bind data to sheet
        public KanrenTokkyoSheet(SeihoBunshoExcel file)
        {
            // Contruct the sheet
            this.contructSheet(file, KanrenTokkyoSheet.SHEET_NAME);
            // Add single mapping property
            // 関連特許
            this.singleProperty.Add(new SeihoBunshoCell() { position = "C5", property = "nm_free_kanren_tokkyo" });
            this.singleProperty.Add(new SeihoBunshoCell() { position = "W5", property = "nm_free_kanren_tokkyo_2" });
            // 他社関連特許
            this.singleProperty.Add(new SeihoBunshoCell() { position = "C64", property = "nm_free_tasha_kanren_tokkyo", fitConetent = SeihoBunshoCell.FIT25 });
            // 営業特許業種
            this.singleProperty.Add(new SeihoBunshoCell() { position = "AE64", property = "nm_eigyo_kyoka_gyoshu01" });
            this.singleProperty.Add(new SeihoBunshoCell() { position = "AE65", property = "nm_eigyo_kyoka_gyoshu02" });
            this.singleProperty.Add(new SeihoBunshoCell() { position = "AE66", property = "nm_eigyo_kyoka_gyoshu03" });
            this.singleProperty.Add(new SeihoBunshoCell() { position = "AE67", property = "nm_eigyo_kyoka_gyoshu04" });
            this.singleProperty.Add(new SeihoBunshoCell() { position = "AE68", property = "nm_eigyo_kyoka_gyoshu05" });
            // Get sheet data
            this.getData();
        }
        // Bind data to the sheet
        public void Bind()
        {
            // Bind header data
            this.renderHeader();
            // Bind data
            this.renderSingle();
        }
    }

    /// <summary>
    /// TAB 7 製法上の確認事項 Excel sheet
    /// </summary>
    public class SeihoKakuninjikoSheet : SeihoBunshoSheet
    {
        // Sheet name in template
        public static string SHEET_NAME = "製法上の確認事項";
        public static string TAB_NAME = "SeihojyoKakuninJiko";
        // Sheet data
        public ma_seiho_bunsho_kakuninjiko singleData { get; set; }
        // Get data for bind TAB 1
        public void getData(ma_seiho_bunsho_kakuninjiko singleData)
        {
            this.singleData = singleData;
            if (this.singleData == null)
            {
                this.singleData = Conditions.kakuninJikoDefaultValue;
            }
            if (this.singleData == null)
            {
                this.singleData = new ma_seiho_bunsho_kakuninjiko();
            }
        }
        // Render single data
        public void renderSingle()
        {
            // for each in mapping and set the value to the sheet
            foreach (var prop in this.singleProperty)
            {
                prop.setValue(this.singleData, this.File.Excel, this.sheetName);
            }
        }
        // Add sheet, get value, bind data to sheet
        public SeihoKakuninjikoSheet(SeihoBunshoExcel file, ma_seiho_bunsho_kakuninjiko singleData)
        {
            // Contruct the sheet
            this.contructSheet(file, SeihoKakuninjikoSheet.SHEET_NAME);
            // Add single mapping property
            // 使用基準のある食品添加物の使用
            this.singleProperty.Add(new SeihoBunshoCell() { position = "C5", property = "nm_shokuhin_tenkabutsu", fitConetent = SeihoBunshoCell.FIT25 });
            // フリー入力
            this.singleProperty.Add(new SeihoBunshoCell() { position = "Y5", property = "nm_free_sonota", fitConetent = SeihoBunshoCell.FIT25 });
            // 原料の指定
            this.singleProperty.Add(new SeihoBunshoCell() { position = "C64", property = "nm_free_genryo_shitei", fitConetent = SeihoBunshoCell.FIT25 });
            // 原料の制限
            this.singleProperty.Add(new SeihoBunshoCell() { position = "Y64", property = "nm_free_genryo_seigen", fitConetent = SeihoBunshoCell.FIT25 });
            // Get sheet data
            this.getData(singleData);
        }
        // Bind data to the sheet
        public void Bind()
        {
            // Bind header data
            this.renderHeader();
            // Bind data
            this.renderSingle();
        }
    }


    /// <summary>
    /// TAB 8 賞味期間設定表 Excel sheet
    /// </summary>
    public class ShomikigenSetteiHyoSheet : SeihoBunshoSheet
    {
        // Sheet name in template
        public static string SHEET_NAME = "賞味期間設定表";
        public static string TAB_NAME = "ShomikigenSetteiHyo";
        // Sheet data
        public ma_seiho_bunsho_shomikigen_EXT singleData { get; set; }
        // Get data for bind TAB 1
        public void getData(ma_seiho_bunsho_hyoshi_EXT masterData1, sp_shohinkaihatsu_search_300_seihinkikakuan_excel_Result masterData6)
        {
            using (ShohinKaihatsuEntities context = new ShohinKaihatsuEntities())
            {
                // Get main data
                var currentData = context.ma_seiho_bunsho_shomikigen.Where(x => x.no_seiho == this.Conditions.no_seiho).FirstOrDefault();
                if (currentData == null)
                {
                    currentData = new ma_seiho_bunsho_shomikigen();
                    currentData.nm_kaihatsutanto = Conditions.nm_login;
                    currentData.nm_kaihatsubusho = Conditions.nm_busho_login;
                }
                this.singleData = new ma_seiho_bunsho_shomikigen_EXT();
                DataCopier.ReFill(currentData, this.singleData);
                //this.singleData.nm_nengetsu_hyoji = "年月表示対応: ";
                // When not export TAB 6
                if (this.File.seihinKikakuanSheet == null)
                {
                    masterData6 = context.sp_shohinkaihatsu_search_300_seihinkikakuan_excel(
                                                                                                Conditions.no_seiho, 
                                                                                                Conditions.kbn_shomikikan, 
                                                                                                Conditions.kbn_shomikikan_seizo_fukumu, 
                                                                                                Conditions.kbn_shomikikan_tani, 
                                                                                                Conditions.kbn_meisho, 
                                                                                                Conditions.kbn_yoryo_tani,
                                                                                                Conditions.toriatsukai_ondo
                                                                                           ).FirstOrDefault();
                }
                // When not export TAB 1
                if (this.File.hyoshiSheet == null)
                {
                    var TAB1Data = _300_SeihoBunshoSakusei_Hyoshi_TabController.getExcelData(this.Conditions, false);
                    if (TAB1Data != null)
                    {
                        masterData1 = TAB1Data.singleData;
                    }
                }
                if (masterData6 != null)
                {
                    // Trigger to tab 6 data
                    this.singleData.nm_shomikikan = masterData6.nm_shomikikan;
                    this.singleData.nm_shomikikan_seizo_fukumu = masterData6.nm_shomikikan_seizo_fukumu;
                    this.singleData.nm_shomikikan_tani = masterData6.nm_shomikikan_tani;
                    short n;
                    if (short.TryParse(masterData6.su_shomikikan_free_input, out n))
                    {
                        this.singleData.su_shomikikan_free_input = n;
                    }
                }
                if (masterData1 != null)
                {
                    // Trigger to tab 1 data
                    this.singleData.nm_tokisaki = masterData1.nm_tokisaki;
                    this.singleData.nm_brand = masterData1.nm_brand;
                }
                if (currentData.cd_nengetsu_hyoji != null)
                {
                    string cd_nengetsu_hyoji = currentData.cd_nengetsu_hyoji.ToString();
                    string nm_nengetsu_hyoji = context.ma_literal.Where(x => x.cd_category == Conditions.kbn_hinmei && x.cd_literal == cd_nengetsu_hyoji).Select(x => x.nm_literal).FirstOrDefault();
                    this.singleData.nm_nengetsu_hyoji = (nm_nengetsu_hyoji ?? "");
                }
                this.singleData.nm_kaihatsutanto = (this.singleData.nm_kaihatsubusho ?? "") + "　　" + (this.singleData.nm_kaihatsutanto ?? "");
            }
        }
        // Render single data
        public void renderSingle()
        {
            // for each in mapping and set the value to the sheet
            foreach (var prop in this.singleProperty)
            {
                prop.setValue(this.singleData, this.File.Excel, this.sheetName);
            }
        }
        // Add sheet, get value, bind data to sheet
        public ShomikigenSetteiHyoSheet(SeihoBunshoExcel file, ma_seiho_bunsho_hyoshi_EXT masterData1, sp_shohinkaihatsu_search_300_seihinkikakuan_excel_Result masterData6)
        {
            // Contruct the sheet
            this.contructSheet(file, ShomikigenSetteiHyoSheet.SHEET_NAME);
            // Add single mapping property
            // 得意先名
            this.singleProperty.Add(new SeihoBunshoCell() { position = "I4", property = "nm_tokisaki" });
            // ブランド
            this.singleProperty.Add(new SeihoBunshoCell() { position = "I5", property = "nm_brand" });
            // 開発担当
            this.singleProperty.Add(new SeihoBunshoCell() { position = "I6", property = "nm_kaihatsutanto" });
            // 保存試験の間隔
            this.singleProperty.Add(new SeihoBunshoCell() { position = "I8", property = "nm_hozon_shiken_kankaku" });
            // 保存試験の期間
            this.singleProperty.Add(new SeihoBunshoCell() { position = "I9", property = "nm_hozon_shiken_kikan" });
            // 備考
            this.singleProperty.Add(new SeihoBunshoCell() { position = "I11", property = "biko", fitConetent = SeihoBunshoCell.FIT44 });
            // 理化学試験
            this.singleProperty.Add(new SeihoBunshoCell() { position = "H20", property = "nm_rikagaku_shiken_komoku", fitConetent = SeihoBunshoCell.FIT11 });
            this.singleProperty.Add(new SeihoBunshoCell() { position = "Q20", property = "nm_rikagaku_shiken_hinshitsu", fitConetent = SeihoBunshoCell.FIT11 });
            this.singleProperty.Add(new SeihoBunshoCell() { position = "Z20", property = "nm_rikagaku_shiken_kikan", fitConetent = SeihoBunshoCell.FIT11 });
            this.singleProperty.Add(new SeihoBunshoCell() { position = "AI20", property = "nm_rikagaku_shiken_hokoku", fitConetent = SeihoBunshoCell.FIT11 });
            // 微生物試験
            this.singleProperty.Add(new SeihoBunshoCell() { position = "H31", property = "nm_biseibutsu_shiken_komoku", fitConetent = SeihoBunshoCell.FIT11 });
            this.singleProperty.Add(new SeihoBunshoCell() { position = "Q31", property = "nm_biseibutsu_shiken_hinshitsu", fitConetent = SeihoBunshoCell.FIT11 });
            this.singleProperty.Add(new SeihoBunshoCell() { position = "Z31", property = "nm_biseibutsu_shiken_kikan", fitConetent = SeihoBunshoCell.FIT11 });
            this.singleProperty.Add(new SeihoBunshoCell() { position = "AI31", property = "nm_biseibutsu_shiken_hokoku", fitConetent = SeihoBunshoCell.FIT11 });
            // 官能評価
            this.singleProperty.Add(new SeihoBunshoCell() { position = "H42", property = "nm_kanno_hyoka_komoku", fitConetent = SeihoBunshoCell.FIT11 });
            this.singleProperty.Add(new SeihoBunshoCell() { position = "Q42", property = "nm_kanno_hyoka_hinshitsu", fitConetent = SeihoBunshoCell.FIT11 });
            this.singleProperty.Add(new SeihoBunshoCell() { position = "Z42", property = "nm_kanno_hyoka_kikan", fitConetent = SeihoBunshoCell.FIT11 });
            this.singleProperty.Add(new SeihoBunshoCell() { position = "AI42", property = "nm_kanno_hyoka_hokoku", fitConetent = SeihoBunshoCell.FIT11 });
            // 安全係数
            this.singleProperty.Add(new SeihoBunshoCell() { position = "I55", property = "no_anzen_keisu" });
            // 賞味期間
            this.singleProperty.Add(new SeihoBunshoCell() { position = "I57", property = "nm_shomikikan" });
            this.singleProperty.Add(new SeihoBunshoCell() { position = "N57", property = "nm_shomikikan_seizo_fukumu" });
            this.singleProperty.Add(new SeihoBunshoCell() { position = "V57", property = "su_shomikikan_free_input", type = SeihoBunshoCell.COMMA_STRING });
            this.singleProperty.Add(new SeihoBunshoCell() { position = "Y57", property = "nm_shomikikan_tani" });
            this.singleProperty.Add(new SeihoBunshoCell() { position = "I58", property = "nm_shomikigen_biko" });
            this.singleProperty.Add(new SeihoBunshoCell() { position = "I61", property = "nm_nengetsu_hyoji" });
            // Get sheet data
            this.getData(masterData1, masterData6);
        }
        // Bind data to the sheet
        public void Bind()
        {
            // Bind header data
            this.renderHeader();
            // Bind data
            this.renderSingle();
        }
    }

    /// <summary>
    /// TAB 9 伝送指示及び開発商品履歴表 Excel sheet
    /// </summary>
    public class RirekiHyoSheet : SeihoBunshoSheet
    {
        // Sheet name in template
        public static string SHEET_NAME = "伝送指示及び開発商品履歴表";
        public static string TAB_NAME = "RirekiHyo";
        public static int BEG_DETAIL = 7;
        public double ROW_HEIGHT = 147;
        public static string BREAK_LINES = "@@BREAK-LINES@@";
        // Get detail row height
        public void getRowHeight()
        {
            ROW_HEIGHT = this.File.Excel.Worksheet(this.sheetName).Row(BEG_DETAIL).Height;
        }
        // Dataset
        public List<sp_shohinkaihatsu_search_300_rirekihyo_Result> arrayData { get; set; }
        // Get data for bind TAB 1
        public void getData()
        {
            using (ShohinKaihatsuEntities context = new ShohinKaihatsuEntities())
            {
                this.arrayData = context.sp_shohinkaihatsu_search_300_rirekihyo(Conditions.no_seiho, Conditions.kbn_haigo).ToList();
            }
        }
        // Set default row height
        private void setRowHeight(int index)
        {
            this.File.Excel.Worksheet(this.sheetName).Row(BEG_DETAIL + index).Height = ROW_HEIGHT;
        }
        // Bind data to row
        private void bindToRow(string no_seiho, string nm_kaisha, string nm_busho, string nm_seihin, string no_seiho_rireki_naiyo, int index)
        {
            this.setRowHeight(index * 2);

            foreach (var prop in this.arrayProperty)
            {
                switch (prop.property)
                { 
                    case "no_seiho":
                        prop.setValue(no_seiho, this.File.Excel, this.sheetName, index * 2, false);
                        break;
                    case "nm_kaisha":
                        prop.setValue(nm_kaisha, this.File.Excel, this.sheetName, index * 2, false);
                        break;
                    case "nm_busho":
                        prop.setValue(nm_busho, this.File.Excel, this.sheetName, index * 2, false);
                        break;
                    case "nm_seihin":
                        prop.setValue(nm_seihin, this.File.Excel, this.sheetName, index * 2, false);
                        break;
                    case "no_seiho_rireki_naiyo":
                        prop.setValue(no_seiho_rireki_naiyo, this.File.Excel, this.sheetName, index * 2, false);
                        break;
                    case "index":
                        prop.setValue(index + 1, this.File.Excel, this.sheetName, index * 2, false);
                        break;
                }
            }
        }
        // Get breaked string
        private string getBreakedString(string value)
        {
            if (value == null || value.Length < BREAK_LINES.Length)
            {
                return value;
            }
            string temp = value.Substring(BREAK_LINES.Length, value.Length - BREAK_LINES.Length);
            temp = temp.Replace(BREAK_LINES, "\n");
            return temp;
        }
        // Get breaked string
        private string getBreakedString(string value, int fixedLength)
        {
            if (value == null || value.Length < BREAK_LINES.Length)
            {
                return value;
            }
            string temp = value.Substring(BREAK_LINES.Length, value.Length - BREAK_LINES.Length);
            String[] lst = temp.Split(new string[] { BREAK_LINES }, StringSplitOptions.None);
            temp = string.Empty;
            foreach (string i in lst)
            {
                temp += CommonController.getFullString(i, fixedLength) + "\n";
            }
            return temp;
        }
        // Render array data
        public void renderArray()
        {
            // Get detail row height before bind 
            this.getRowHeight();
            // for each in mapping and set the value to the sheet
            foreach (var prop in this.arrayProperty)
            {
                prop.splitPosition();

            }
            // Init row data
            string no_seiho = string.Empty;
            string last_no_seiho = string.Empty;
            string nm_kaisha = string.Empty;
            string nm_busho = string.Empty;
            string nm_seihin = string.Empty;
            string no_seiho_rireki_naiyo = string.Empty;
            int index = 0;
            // Bind data to file
            for (var i = 0; i < arrayData.Count; i++)
            {
                var curData = arrayData[i];
                // Prepair row data
                if (no_seiho != curData.no_seiho)
                {
                    if (no_seiho != string.Empty)
                    {
                        bindToRow(no_seiho, nm_kaisha, nm_busho, nm_seihin, no_seiho_rireki_naiyo, index);
                        last_no_seiho = no_seiho;
                        nm_kaisha = string.Empty;
                        nm_busho = string.Empty;
                        nm_seihin = string.Empty;
                        no_seiho_rireki_naiyo = string.Empty;
                        index++;
                    }
                    no_seiho = curData.no_seiho;
                    no_seiho_rireki_naiyo = curData.no_seiho_rireki_naiyo;
                    nm_seihin += (getBreakedString(curData.nm_seihin, curData.su_code_standard) ?? "");
                }
                int bushoCount = Regex.Matches(curData.nm_busho ?? "", BREAK_LINES).Count;
                nm_kaisha += curData.nm_kaisha + "\n";
                for (var j = 1; j < bushoCount; j++)
                {
                    nm_kaisha += "\n";
                }
                nm_busho += (getBreakedString(curData.nm_busho) ?? "") + "\n";
            }
            if (last_no_seiho != no_seiho)
            {
                bindToRow(no_seiho, nm_kaisha, nm_busho, nm_seihin, no_seiho_rireki_naiyo, index);
            }
        }
        // Add sheet, get value, bind data to sheet
        public RirekiHyoSheet(SeihoBunshoExcel file)
        {
            // Contruct the sheet
            this.contructSheet(file, RirekiHyoSheet.SHEET_NAME);
            // Add array mapping property
            //製品リスト
            this.arrayProperty.Add(new SeihoBunshoCell() { position = "B7", property = "index" });
            this.arrayProperty.Add(new SeihoBunshoCell() { position = "D7", property = "no_seiho" });
            this.arrayProperty.Add(new SeihoBunshoCell() { position = "L7", property = "nm_kaisha" });
            this.arrayProperty.Add(new SeihoBunshoCell() { position = "T7", property = "nm_busho" });
            this.arrayProperty.Add(new SeihoBunshoCell() { position = "AB7", property = "nm_seihin" });
            this.arrayProperty.Add(new SeihoBunshoCell() { position = "AF7", property = "no_seiho_rireki_naiyo", fitConetent = SeihoBunshoCell.FIT15 });
            // Get sheet data
            this.getData();
        }
        // Bind data to the sheet
        public void Bind()
        {
            // Bind header data
            this.renderHeader();
            // Bind data
            this.renderArray();
        }
    }

    /// <summary>
    /// TAB 10 配合作成表 Excel sheet
    /// </summary>
    public class HaigoHyoSheet : SeihoBunshoSheet
    {
        // Sheet name in template
        public static string SHEET_NAME = "配合作成表";
        public static string TAB_NAME = "HaigoHyo";
        public static int BEGIN_DETAIL = 6;
        // Dataset
        public List<SeihoBunshoCell> totalProperty { get; set; }
        public List<sp_shohinkaihatsu_search_300_haigohyo_header_Result> singleData { get; set; }
        public List<sp_shohinkaihatsu_search_300_haigohyo_detail_Result> arrayData { get; set; }
        public List<sp_shohinkaihatsu_search_300_haigohyo_excel_Result> totalData { get; set; }
        // Get data for bind TAB 1
        public void getData()
        {
            var data = _300_SeihoBunshoSakusei_HaigoHyo_TabController.getExcelData(this.Conditions);
            if (data != null)
            {
                this.singleData = data.lstHaigoHeader;
                this.arrayData = data.lstHaigoDetail;
                this.totalData = data.totalData;
            }
        }
        // Sheet name = '配合作成表' + cd_hin
        private string getSheetName(decimal cd_hin)
        {
            return HaigoHyoSheet.SHEET_NAME + "（" + cd_hin.ToString() + "）";
        }
        // Sheet name = '配合作成表（合計）'
        private string getTotalSheetName()
        {
            return HaigoHyoSheet.SHEET_NAME + "（合計）";
        }
        // Get list of sheet name
        public List<string> getSheetName()
        {
            List<string> result = new List<string>();
            if (this.singleData != null)
            {
                if (this.singleData.Count > 1 && this.totalData != null && this.totalData.Count > 0)
                {
                    result.Add(getTotalSheetName());
                }
                foreach (var haigo in this.singleData)
                {
                    result.Add(getSheetName(haigo.cd_haigo));
                }
            }
            return result;
        }
        // Render single data
        public void renderSingle(sp_shohinkaihatsu_search_300_haigohyo_header_Result haigoData)
        {
            var singleData = haigoData;
            if (singleData == null)
            {
                return;
            }
            // Get sheet name base on cd_hin
            string sheetName = this.getSheetName(haigoData.cd_haigo);
            this.sheetName = sheetName;
            // Bind header data
            this.renderHeader();
            // Remove [分割詳細]
            this.File.Excel.Worksheet(sheetName).Cell("AM5").Value = "";
            // Change literal to [内訳]
            this.File.Excel.Worksheet(sheetName).Cell("B3").Value = "内訳";
            // for each in mapping and set the value to the sheet
            foreach (var prop in this.singleProperty)
            {
                prop.setValue(haigoData, this.File.Excel, sheetName);
            }
        }
        // Render array data
        public void renderArray()
        {
            List<GenryoIndex> genryoIndex = new List<GenryoIndex>();
            // for each in mapping and set the value to the sheet
            foreach (var prop in this.arrayProperty)
            {
                prop.splitPosition();
            }
            for (var i = 0; i < arrayData.Count; i++)
            {
                var detailData = arrayData[i];
                // Get sheet name base on cd_hin
                string sheetName = this.getSheetName(detailData.cd_haigo);
                GenryoIndex index = genryoIndex.Where(x => x.sheetName == sheetName).FirstOrDefault();
                if (index == null)
                {
                    index = new GenryoIndex() { sheetName = sheetName, index = 0 };
                    var haigoHeader = this.singleData.Where(x => x.cd_haigo == detailData.cd_haigo).FirstOrDefault();
                    if (haigoHeader != null)
                    {
                        index.nm_tani_sum = haigoHeader.nm_tani_sum;
                        index.kbn_shiagari = haigoHeader.kbn_shiagari;
                        index.qty_haigo_kei = haigoHeader.qty_haigo_kei;
                    }
                    genryoIndex.Add(index);
                }
                index.totalWeight += (detailData.qty_haigo_org ?? 0);
                this.File.copyTempRow(sheetName, "B", "AR", BEGIN_DETAIL + index.index);
                // for each in mapping and set the value to the sheet
                foreach (var prop in this.arrayProperty)
                {
                    if (prop.property != "kbn_bunkatsu" || detailData.kbn_bunkatsu != 0)
                    {
                        prop.setValue(detailData, this.File.Excel, sheetName, index.index);
                    }
                }
                index.index++;
            }
            foreach (var sheet in genryoIndex)
            {
                string sheetName = sheet.sheetName;
                this.File.copyTempRow(sheetName, "B", "AR", BEGIN_DETAIL + sheet.index);
                // Set total row data
                // Set [合計]
                SeihoBunshoCell totalCell = new SeihoBunshoCell() { position = "I6", property = "totalRow" };
                totalCell.splitPosition();
                totalCell.setValue("合計", this.File.Excel, sheetName, sheet.index, false);
                // Set [配合重量]
                totalCell.position = "AC6";
                totalCell.splitPosition();
                totalCell.format = "#,##0.000000";
                totalCell.type = SeihoBunshoCell.NUMBER_TYPE;
                totalCell.setValue(sheet.totalWeight, this.File.Excel, sheetName, sheet.index, false);
                if (sheet.kbn_shiagari == true)
                {
                    totalCell.setValue(sheet.qty_haigo_kei ?? 0, this.File.Excel, sheetName, sheet.index + 1, false);
                }
                // Set [単位]
                totalCell.position = "AJ6";
                totalCell.splitPosition();
                totalCell.format = "";
                totalCell.type = SeihoBunshoCell.STRING_TYPE;
                totalCell.setValue(sheet.nm_tani_sum, this.File.Excel, sheetName, sheet.index, false);
                if (sheet.kbn_shiagari == true)
                {
                    totalCell.setValue(sheet.nm_tani_sum, this.File.Excel, sheetName, sheet.index + 1, false);
                }
            }
        }
        // Render for sheet 配合作成表（合計）
        public void renderTotal()
        {
            if (this.totalData == null)
            {
                return;
            }
            var sheetName = getTotalSheetName();
            this.sheetName = sheetName;
            this.renderHeader();
            double totalWeight = 0;
            string nm_tani_sum = totalData[0].nm_tani_sum;
            foreach (var prop in this.totalProperty)
            {
                prop.splitPosition();
            }
            for (var i = 0; i < totalData.Count; i++)
            {
                var tData = totalData[i];
                // prepair for total row
                totalWeight += (tData.qty_haigo ?? 0);
                this.File.copyTempRow(sheetName, "B", "AR", BEGIN_DETAIL + i);
                // for each in mapping and set the value to the sheet
                foreach (var prop in this.totalProperty)
                {
                    if (prop.property != "kbn_bunkatsu" || tData.kbn_bunkatsu != "0")
                    {
                        prop.setValue(tData, this.File.Excel, sheetName, i);
                    }
                }
            }
            // Set total row data
            // Set [合計]
            SeihoBunshoCell totalCell = new SeihoBunshoCell() { position = "I6", property = "totalRow" };
            totalCell.splitPosition();
            totalCell.setValue("合計", this.File.Excel, sheetName, totalData.Count, false);
            // Set [配合重量]
            totalCell.position = "AC6";
            totalCell.splitPosition();
            totalCell.format = "#,##0.000000";
            totalCell.type = SeihoBunshoCell.NUMBER_TYPE;
            totalCell.setValue(totalWeight, this.File.Excel, sheetName, totalData.Count, false);
            // Set [単位]
            totalCell.position = "AJ6";
            totalCell.splitPosition(); 
            totalCell.format = "";
            totalCell.type = SeihoBunshoCell.STRING_TYPE;
            totalCell.setValue(nm_tani_sum, this.File.Excel, sheetName, totalData.Count, false);
        }
        // Add sheet, get value, bind data to sheet
        public HaigoHyoSheet(SeihoBunshoExcel file)
        {
            // Contruct the sheet
            this.contructSheet(file, HaigoHyoSheet.SHEET_NAME);
            this.totalProperty = new List<SeihoBunshoCell>();
            // Add single mapping property
            this.singleProperty.Add(new SeihoBunshoCell() { position = "J3", property = "cd_haigo" });
            this.singleProperty.Add(new SeihoBunshoCell() { position = "Q3", property = "nm_haigo" });
            // Add array mapping property
            // 分割
            this.arrayProperty.Add(new SeihoBunshoCell() { position = "B6", property = "kbn_bunkatsu" });
            // 原料ｺｰﾄﾞ
            this.arrayProperty.Add(new SeihoBunshoCell() { position = "E6", property = "cd_hin" });
            // 原料名
            this.arrayProperty.Add(new SeihoBunshoCell() { position = "I6", property = "nm_hin" });
            // 配合重量
            this.arrayProperty.Add(new SeihoBunshoCell() { position = "AC6", property = "qty_haigo", type = SeihoBunshoCell.NUMBER_TYPE, format = "#,##0.000" });
            // 単位
            this.arrayProperty.Add(new SeihoBunshoCell() { position = "AJ6", property = "nm_tani" });
            // mishiyo
            this.arrayProperty.Add(new SeihoBunshoCell() { position = "AR6", property = "nm_mishiyo" });
            // Add total mapping property
            // 分割
            this.totalProperty.Add(new SeihoBunshoCell() { position = "B6", property = "kbn_bunkatsu" });
            // 原料ｺｰﾄﾞ
            this.totalProperty.Add(new SeihoBunshoCell() { position = "E6", property = "cd_hin", fixedLength = Conditions.su_code_standard });
            // 原料名
            this.totalProperty.Add(new SeihoBunshoCell() { position = "I6", property = "nm_hin" });
            // 配合重量
            this.totalProperty.Add(new SeihoBunshoCell() { position = "AC6", property = "qty_haigo", type = SeihoBunshoCell.NUMBER_TYPE, format = "#,##0.000000" });
            // 単位
            this.totalProperty.Add(new SeihoBunshoCell() { position = "AJ6", property = "nm_tani" });
            // 分割詳細
            this.totalProperty.Add(new SeihoBunshoCell() { position = "AM6", property = "kbn_bunkatsu_disp" });
            // mishiyo
            this.totalProperty.Add(new SeihoBunshoCell() { position = "AR6", property = "nm_mishiyo" });
            // Get sheet data
            this.getData();
        }
        // Bind data to the sheet
        public void Bind()
        {
            // Bind haigo header data
            if (this.singleData != null)
            {
                foreach (var haigo in this.singleData)
                {
                    this.renderSingle(haigo);
                }
                if (this.singleData.Count > 1 && this.totalData != null && this.totalData.Count > 0)
                {
                    this.renderTotal();
                }
                // Generate empty sheet
                if (this.singleData.Count == 0)
                {
                    this.sheetName = SHEET_NAME;
                    this.renderHeader();
                }
            }
            // Bind haigo detail data
            this.renderArray();
        }
    }

    /// <summary>
    /// Base contruct for excel
    /// </summary>
    public class SeihoBunshoExcel
    {
        // Main work book
        public XLWorkbook Excel { get; set; }
        private MemoryStream stream { get; set; }
        // Base excel param
        public SeihoBunshoExcelCondition Conditions { get; set; }
        // Excel header data
        public SeihoBunshoHeaderData headerData { get; set; }
        // TAB 1 表紙
        public HyoshiSheet hyoshiSheet { get; set; }
        // TAB 1 製品リスト
        public SeihinSheet seihinSheet { get; set; }
        // TAB 1 元になる製法
        public SeihoSankoSheet seihoSankoSheet { get; set; }
        // TAB 2 容器包装
        public YoukiHousouSheet youkiHousouSheet { get; set; }
        // TAB 3 原料・機械設備・製造方法・表示事項
        public GenryoSetsubiSheet genryoSetsubiSheet { get; set; }
        // TAB 4 配合・製造上の注意事項
        public HaigoSeizoChuiJikoSheet haigoSeizoChuiJikoSheet { get; set; }
        // TAB 5 製造工程図
        public SeizoKoteizuSheet seizoKoteizuSheet { get; set; }
        // TAB 6 製品特性値
        public SeihinKikakuanSheet seihinKikakuanSheet { get; set; }
        // TAB 6 製品規格案及び取扱基準
        public SeihinKikakuanOyobiSheet seihinKikakuanOyobiSheet { get; set; }
        // TAB 7 関連特許
        public KanrenTokkyoSheet kanrenTokkyoSheet { get; set; }
        // TAB 7 製法上の確認事項
        public SeihoKakuninjikoSheet seihoKakuninjikoSheet { get; set; }
        // TAB 8 賞味期間設定表
        public ShomikigenSetteiHyoSheet shomikigenSetteiHyoSheet { get; set; }
        // TAB 9 商品開発履歴表
        public RirekiHyoSheet rirekiHyoSheet { get; set; }
        // TAB 10 配合作成表
        public HaigoHyoSheet haigoHyoSheet { get; set; }
        // List of sheet need to copy, key: source sheet name, value: new sheet name
        private List<SheetMapping> copySheetName { get; set; }
        // List of sheet need to change name
        private List<SheetMapping> changeSheetName { get; set; }
        // List of sheet need to delete
        private List<string> deleteSheetName { get; set; }
        // Sheet order
        private List<string> sheetOrder { get; set; }
        // Temporary file name: Now.getTicks
        private string tempFileName { get; set; }
        // Temporary download file path
        private string tempDownload { get; set; }
        // Get header data
        public static SeihoBunshoHeaderData getHeaderData(SeihoBunshoExcelCondition Conditions)
        {
            using (ShohinKaihatsuEntities context = new ShohinKaihatsuEntities())
            {
                SeihoBunshoHeaderData result = new SeihoBunshoHeaderData();
                var seihoData = context.ma_seiho.Where(x => x.no_seiho == Conditions.no_seiho).FirstOrDefault();
                var tab1Data = context.ma_seiho_bunsho_hyoshi.Where(x => x.no_seiho == Conditions.no_seiho).FirstOrDefault();
                if (seihoData != null)
                {
                    // 製法作成日
                    result.dt_seiho_sakusei = seihoData.dt_seiho_sakusei == null ? "" : (seihoData.dt_seiho_sakusei ?? DateTime.Now).ToString("yyyy/MM/dd");
                    // 製法番号
                    result.no_seiho = seihoData.no_seiho;
                    // 製法名
                    result.nm_seiho = seihoData.nm_seiho;
                    // 試作Ｎｏ
                    result.cd_shain = seihoData.cd_shain;
                    result.no_oi = seihoData.no_oi;
                    result.nen = seihoData.nen;
                    result.seq_shisaku = seihoData.seq_shisaku;
                    result.nm_sample = seihoData.nm_sample;
                    // Check shonin level
                    if (tab1Data != null && tab1Data.cd_create != null)
                    {
                        var tab4Data = context.ma_seiho_bunsho_haigo_chui.Where(x => x.no_seiho == Conditions.no_seiho).FirstOrDefault();
                        if (tab4Data != null)
                        {
                            result.pt_kotei = tab4Data.pt_kotei;
                        }
                    }
                    // Shonin level = 0
                    else if (result.cd_shain != null)
                    {
                        var shisakuData = context.tr_shisakuhin.Where(x => x.cd_shain == result.cd_shain && x.nen == result.nen && x.no_oi == result.no_oi).FirstOrDefault();
                        if (shisakuData != null)
                        {
                            result.pt_kotei = shisakuData.pt_kotei;
                            result.su_naiyoryo = shisakuData.yoryo;
                            short n;
                            if (short.TryParse(shisakuData.shomikikan, out n))
                            {
                                result.su_shomikikan_free_input = n;
                            }
                            result.kbn_shomikikan_tani = shisakuData.shomikikan_tani;
                            result.cd_ondo = shisakuData.cd_ondo;
                        }
                    }
                }
                return result;
            }
        }
        // Get new physicaly file
        private string getNewFile(string dirTemlates)
        {
            XLWorkbook tempFile = new XLWorkbook(dirTemlates);
            this.tempDownload = HttpContext.Current.Server.MapPath(Properties.Settings.Default.DownloadTempFolder);
            this.tempFileName = DateTime.Now.Ticks.ToString();
            string filePath = this.tempDownload + "\\" + this.tempFileName + Properties.Resources.ExcelExtension;
            // All sheet deleted
            if (this.deleteSheetName.Count == tempFile.Worksheets.Count)
            {
                tempFile.Worksheets.Add("Sheet 1");
            }
            // Delete empty sheet
            foreach (var sheet in this.deleteSheetName)
            {
                tempFile.Worksheet(sheet).Delete();
            }
            tempFile.SaveAs(filePath);
            return filePath;
        }
        // Change sheet order
        private void ChangeLayOut(string dirTemlates)
        {
            XLWorkbook tempFile = new XLWorkbook(dirTemlates);
            // Change sheets name
            foreach (var sheet in this.changeSheetName)
            {
                tempFile.Worksheet(sheet.srcSheet).Name = sheet.destSheet;
            }
            // Change sheet order
            for (var i = 0; i < this.sheetOrder.Count; i++)
            {
                if (this.sheetOrder[i].IndexOf(SeizoKoteizuSheet.SHEET_NAME) == 0)
                {
                    tempFile.Worksheet(this.sheetOrder[i]).Position = i + 1;
                }
            }
            tempFile.SaveAs(dirTemlates);
        }
        // Set border style
        public void setBoderStyle(string sheetName, string beginAddr, string endAddr, XLBorderStyleValues top, XLBorderStyleValues right, XLBorderStyleValues bottom, XLBorderStyleValues left)
        {
            var range = this.Excel.Worksheet(sheetName).Range(beginAddr + ":" + endAddr);
            if (range != null)
            {
                range.Style.Border.TopBorder = top;
                range.Style.Border.RightBorder = right;
                range.Style.Border.BottomBorder = bottom;
                range.Style.Border.LeftBorder = left;
            }
        }
        // Set under line
        public void setUnderLineStyle(string sheetName, string beginAddr, string endAddr)
        {
            var range = this.Excel.Worksheet(sheetName).Range(beginAddr, endAddr);
            if (range != null)
            {
                range.Style.Font.Underline = XLFontUnderlineValues.Single;
            }
        }
        // Copy temp row to other row
        public void copyTempRow(string sheetName, string beginAddr, string endAddr, int distance)
        {
            var sheet = this.Excel.Worksheet(sheetName);
            string rowAddr = (beginAddr + distance) + ":" + (endAddr + distance);
            string nextRowAddr = (beginAddr + (distance + 1)) + ":" + (endAddr + (distance + 1));
            var tempRow = sheet.Range(rowAddr);
            // Copy to next row
            sheet.Range(nextRowAddr).Style = tempRow.Style;
            sheet.Cell(beginAddr + (distance + 1)).Value = tempRow;
            // Set next row height
            sheet.Row(distance + 1).Height = sheet.Row(distance).Height;
        }
        // Merge cell
        public void mergeCell(string sheetName, string beginAddr, string endAddr)
        {
            this.Excel.Worksheet(sheetName).Range(beginAddr + ":" + endAddr).Column(1).Merge();
        }
        // Set row color
        public void setBackgroundColor(string sheetName, string beginAddr, string endAddr, XLColor color)
        {
            this.Excel.Worksheet(sheetName).Range(beginAddr + ":" + endAddr).Style.Fill.BackgroundColor = color;
        }
        // Set fore color
        public void setForeColor(string sheetName, string beginAddr, string endAddr, XLColor color)
        {
            this.Excel.Worksheet(sheetName).Range(beginAddr + ":" + endAddr).Style.Font.FontColor = color;
        }
        // Get excel data, base on the data get excel copy list and delete list
        private void getExcelData()
        { 
            // Check if user choose exprot TAB 1 
            if (SeihoBunshoSheet.isExport(this.Conditions.lst_tab, HyoshiSheet.TAB_NAME))
            {
                // 表紙
                this.hyoshiSheet = new HyoshiSheet(this);
                this.sheetOrder.Add(HyoshiSheet.SHEET_NAME);
                // If TAB 1 has more than 8 rows in seihin data
                if (this.hyoshiSheet != null && this.hyoshiSheet.arrayData != null && this.hyoshiSheet.arrayData.Count > HyoshiSheet.ARRAY_LIMIT)
                {
                    // 製品リスト
                    this.seihinSheet = new SeihinSheet(this, this.hyoshiSheet.arrayData);
                    this.sheetOrder.Add(SeihinSheet.SHEET_NAME);
                }
                else
                {
                    // Delete the empty sheet
                    this.deleteSheetName.Add(SeihinSheet.SHEET_NAME);
                }
                // 元になる製法
                this.seihoSankoSheet = new SeihoSankoSheet(this, this.hyoshiSheet.singleData);
                this.sheetOrder.Add(SeihoSankoSheet.SHEET_NAME);
            }
            else
            {
                // Delete the empty sheet
                this.deleteSheetName.Add(HyoshiSheet.SHEET_NAME);
                this.deleteSheetName.Add(SeihinSheet.SHEET_NAME);
                this.deleteSheetName.Add(SeihoSankoSheet.SHEET_NAME);
            }
            // Check if user choose exprot TAB 2
            if (SeihoBunshoSheet.isExport(this.Conditions.lst_tab, YoukiHousouSheet.TAB_NAME))
            {
                // 容器包装
                this.youkiHousouSheet = new YoukiHousouSheet(this);
                this.sheetOrder.Add(YoukiHousouSheet.SHEET_NAME);
            }
            else
            {
                // Delete the empty sheet
                this.deleteSheetName.Add(YoukiHousouSheet.SHEET_NAME);
            }
            // Check if user choose exprot TAB 3
            if (SeihoBunshoSheet.isExport(this.Conditions.lst_tab, GenryoSetsubiSheet.TAB_NAME))
            {
                // 原料・機械設備・製造方法・表示事項
                this.genryoSetsubiSheet = new GenryoSetsubiSheet(this);
                this.sheetOrder.Add(GenryoSetsubiSheet.SHEET_NAME);
            }
            else
            {
                this.deleteSheetName.Add(GenryoSetsubiSheet.SHEET_NAME);
            }
            // Check if user choose exprot TAB 4
            if (SeihoBunshoSheet.isExport(this.Conditions.lst_tab, HaigoSeizoChuiJikoSheet.TAB_NAME))
            {
                // 配合・製造上の注意事項
                this.haigoSeizoChuiJikoSheet = new HaigoSeizoChuiJikoSheet(this);
                this.sheetOrder.Add(HaigoSeizoChuiJikoSheet.SHEET_NAME);
            }
            else
            {
                this.deleteSheetName.Add(HaigoSeizoChuiJikoSheet.SHEET_NAME);
            }
            // Check if user choose exprot TAB 5
            if (SeihoBunshoSheet.isExport(this.Conditions.lst_tab, SeizoKoteizuSheet.TAB_NAME))
            {
                // 製造工程図
                this.seizoKoteizuSheet = new SeizoKoteizuSheet(this);
                var lstSheet = this.seizoKoteizuSheet.getSheetName();
                for (var i = 0; i < lstSheet.Count; i++)
                {
                    string sheetName = lstSheet[i];
                    // Change sheet name
                    if (i == 0)
                    {
                        this.changeSheetName.Add(new SheetMapping() 
                        { 
                            srcSheet = SeizoKoteizuSheet.SHEET_NAME,
                            destSheet = sheetName
                        });
                    }
                    // Copy new sheet
                    else
                    {
                        this.copySheetName.Add(new SheetMapping()
                        {
                            srcSheet = SeizoKoteizuSheet.SHEET_NAME,
                            destSheet = sheetName
                        });
                    }
                    this.sheetOrder.Add(sheetName);
                }
                if (lstSheet.Count == 0)
                {
                    // Remove temp sheet name
                    //this.deleteSheetName.Add(SeizoKoteizuSheet.SHEET_NAME);
                }
            }
            else
            {
                // Remove temp sheet name
                this.deleteSheetName.Add(SeizoKoteizuSheet.SHEET_NAME);
            }
            // Check if user choose exprot TAB 6 
            if (SeihoBunshoSheet.isExport(this.Conditions.lst_tab, SeihinKikakuanSheet.TAB_NAME))
            {
                // 製品特性値
                this.seihinKikakuanSheet = new SeihinKikakuanSheet(this);
                // 製品規格案及び取扱基準
                this.seihinKikakuanOyobiSheet = new SeihinKikakuanOyobiSheet(this, this.seihinKikakuanSheet.singleData);
                // Add sheet order
                this.sheetOrder.Add(SeihinKikakuanOyobiSheet.SHEET_NAME);
                this.sheetOrder.Add(SeihinKikakuanSheet.SHEET_NAME);
            }
            else
            {
                // Delete the empty sheet
                this.deleteSheetName.Add(SeihinKikakuanSheet.SHEET_NAME);
                this.deleteSheetName.Add(SeihinKikakuanOyobiSheet.SHEET_NAME);
            }
            // Check if user choose exprot TAB 7 
            if (SeihoBunshoSheet.isExport(this.Conditions.lst_tab, SeihoKakuninjikoSheet.TAB_NAME))
            {
                // 関連特許
                this.kanrenTokkyoSheet = new KanrenTokkyoSheet(this);
                // 製法上の確認事項
                this.seihoKakuninjikoSheet = new SeihoKakuninjikoSheet(this, this.kanrenTokkyoSheet.singleData);
                // Add sheet order
                this.sheetOrder.Add(KanrenTokkyoSheet.SHEET_NAME);
                this.sheetOrder.Add(SeihoKakuninjikoSheet.SHEET_NAME);
            }
            else
            {
                // Delete the empty sheet
                this.deleteSheetName.Add(KanrenTokkyoSheet.SHEET_NAME);
                this.deleteSheetName.Add(SeihoKakuninjikoSheet.SHEET_NAME);
            }
            // Check if user choose exprot TAB 8 
            if (SeihoBunshoSheet.isExport(this.Conditions.lst_tab, ShomikigenSetteiHyoSheet.TAB_NAME))
            {
                // 賞味期間設定表
                this.shomikigenSetteiHyoSheet = new ShomikigenSetteiHyoSheet(this, this.hyoshiSheet == null ? null : this.hyoshiSheet.singleData, this.seihinKikakuanSheet == null ? null : this.seihinKikakuanSheet.singleData);
                // Add sheet order
                this.sheetOrder.Add(ShomikigenSetteiHyoSheet.SHEET_NAME);
            }
            else
            {
                // Delete the empty sheet
                this.deleteSheetName.Add(ShomikigenSetteiHyoSheet.SHEET_NAME);
            }
            // Check if user choose exprot TAB 9 
            if (SeihoBunshoSheet.isExport(this.Conditions.lst_tab, RirekiHyoSheet.TAB_NAME))
            {
                // 商品開発履歴表
                this.rirekiHyoSheet = new RirekiHyoSheet(this);
                // Add sheet order
                this.sheetOrder.Add(RirekiHyoSheet.SHEET_NAME);
            }
            else
            {
                // Delete the empty sheet
                this.deleteSheetName.Add(RirekiHyoSheet.SHEET_NAME);
            }
            // Check if user choose exprot TAB 10 
            if (SeihoBunshoSheet.isExport(this.Conditions.lst_tab, HaigoHyoSheet.TAB_NAME))
            {
                // 配合作成表
                this.haigoHyoSheet = new HaigoHyoSheet(this);
                var lstSheet = this.haigoHyoSheet.getSheetName();
                for (var i = 0; i < lstSheet.Count; i++)
                {
                    string sheetName = lstSheet[i];
                    // Change sheet name
                    if (i == 0)
                    {
                        this.changeSheetName.Add(new SheetMapping()
                        {
                            srcSheet = HaigoHyoSheet.SHEET_NAME,
                            destSheet = sheetName
                        });
                    }
                    // Copy new sheet
                    else
                    {
                        this.copySheetName.Add(new SheetMapping()
                        {
                            srcSheet = HaigoHyoSheet.SHEET_NAME,
                            destSheet = sheetName
                        });
                    }
                    //this.sheetOrder.Add(sheetName);
                }
                if (lstSheet.Count == 0)
                {
                    // Remove temp sheet name
                    //this.deleteSheetName.Add(HaigoHyoSheet.SHEET_NAME);
                }
            }
            else
            {
                // Delete the empty sheet
                this.deleteSheetName.Add(HaigoHyoSheet.SHEET_NAME);
            }
        }
        // Get excel file
        public void getExcel(string dirTemlates, SeihoBunshoExcelCondition Conditions)
        {
            if (Conditions == null)
            {
                return;
            }
            // Set base param
            this.Conditions = Conditions;
            this.copySheetName = new List<SheetMapping>();
            this.deleteSheetName = new List<string>();
            this.changeSheetName = new List<SheetMapping>();
            this.sheetOrder = new List<string>();
            this.stream = new MemoryStream();

            this.Conditions.isApplied = this.isApplied();
            // Get header seiho data
            this.headerData = getHeaderData(Conditions);
            // Contruct and get all sheet data, get sheets need to copy and sheets to delete
            this.getExcelData();
            // Copy to template to new temporary file
            string filePath = this.getNewFile(dirTemlates);
            try
            {
                // Change excel file layout: add new sheets, rename sheets
                ExcelFile.ChangeLayOut(filePath, this.copySheetName);
                this.ChangeLayOut(filePath);

                // prepair to bind data
                this.Excel = new XLWorkbook(filePath);
                // Bind TAB 1 data
                if (this.hyoshiSheet != null) { this.hyoshiSheet.Bind(); }
                if (this.seihinSheet != null) { this.seihinSheet.Bind(); }
                if (this.seihoSankoSheet != null) { this.seihoSankoSheet.Bind(); }
                // Bind TAB 2 data
                if (this.youkiHousouSheet != null) { this.youkiHousouSheet.Bind(); }
                // Bind TAB 3 data
                if (this.genryoSetsubiSheet != null) { this.genryoSetsubiSheet.Bind(); }
                // Bind TAB 4 data
                if (this.haigoSeizoChuiJikoSheet != null) { this.haigoSeizoChuiJikoSheet.Bind(); }
                // Bind TAB 5 data
                if (this.seizoKoteizuSheet != null) { this.seizoKoteizuSheet.Bind(); }
                // Bind TAB 6 data
                if (this.seihinKikakuanSheet != null) { this.seihinKikakuanSheet.Bind(); }
                if (this.seihinKikakuanOyobiSheet != null) { this.seihinKikakuanOyobiSheet.Bind(); }
                // Bind TAB 7 data
                if (this.kanrenTokkyoSheet != null) { this.kanrenTokkyoSheet.Bind(); }
                if (this.seihoKakuninjikoSheet != null) { this.seihoKakuninjikoSheet.Bind(); }
                // Bind TAB 8 data
                if (this.shomikigenSetteiHyoSheet != null) { this.shomikigenSetteiHyoSheet.Bind(); }
                // Bind TAB 9 data
                if (this.rirekiHyoSheet != null) { this.rirekiHyoSheet.Bind(); }
                // Bind TAB 10 data
                if (this.haigoHyoSheet != null) { this.haigoHyoSheet.Bind(); }

                this.Excel.Worksheet(1).SetTabActive();

                // Save to stream
                this.Excel.SaveAs(stream);
            }
            catch (Exception ex)
            {
                // Try delete temp file when change layout get error
                FileUploadDownloadUtility.deleteTempFile(this.tempDownload, this.tempFileName);
                throw ex;
            }
            // Delete temp file
            FileUploadDownloadUtility.deleteTempFile(this.tempDownload, this.tempFileName);
        }
        // Get file stream after binding
        public MemoryStream getStream()
        {
            return this.stream;
        }
        // Get cell with cell address
        public IXLCell getCell(string sheetName, string Addr)
        {
            return this.Excel.Worksheet(sheetName).Cell(Addr);
        }
        // Check if seiho is applied
        public bool isApplied()
        {
            using (ShohinKaihatsuEntities context = new ShohinKaihatsuEntities())
            {
                var tab1Data = context.ma_seiho_bunsho_hyoshi.Where(x => x.no_seiho == Conditions.no_seiho).FirstOrDefault();
                if (tab1Data != null && tab1Data.cd_create != null)
                {
                    return true;
                }
                return false;
            }
        }
    }

    /// <summary>
    /// Excel request param
    /// </summary>
    public class SeihoBunshoExcelCondition
    {
        // Base param
        public string no_seiho { get; set; }
        public List<string> lst_tab { get; set; }
        public string lst_haigo { get; set; }
        // Category const
        public string kbn_tokisaki { get; set; }
        public string kbn_brand { get; set; }
        public string cd_category_kotei { get; set; }
        public string kbn_shomikikan { get; set; }
        public string kbn_shomikikan_seizo_fukumu { get; set; }
        public string kbn_shomikikan_tani { get; set; }
        public string kbn_meisho { get; set; }
        public string toriatsukai_ondo { get; set; }
        public string kbn_yoryo_tani { get; set; }
        public string kbn_haigo_kyodo { get; set; }
        public string kbn_hinmei { get; set; }
        // cd_literal
        public string cd_literal_status { get; set; }
        // kbn_hin const
        public short? kbn_haigo { get; set; }
        public short? kbn_genryo { get; set; }
        public short? kbn_maeshori { get; set; }
        public short? kbn_shikakari { get; set; }
        public short? kbn_sagyo { get; set; }
        // kbn_tani
        public string kbn_tani_kg { get; set; }
        public string kbn_tani_l { get; set; }
        public string kbn_gam { get; set; }
        // Tab4 const
        public string cd_tani_g { get; set; }
        public string kbn_pt_kotei { get; set; }
        public string kbn_chomi { get; set; }
        public string kbn_suisho { get; set; }
        public string kbn_yusho { get; set; }
        // kbn_shikakari
        public short kbn_shikakari_kaihatsu { get; set; }
        public short kbn_shikakari_FP { get; set; }
        // loop count
        public int loop_count { get; set; }
        // pt_kotei
        public string pt_kotei_chomieki_2 { get; set; }
        public string pt_kotei_sonohokaeki { get; set; }
        public bool flg_mishiyo { get; set; }
        // User infor const
        public int su_code_standard { get; set; }
        // TAB 5 default color
        public List<string> defaultColors { get; set; }
        // TAB 6 Property mapping
        public List<ShisakuPropMapping> shisakuPropMapping { get; set; }
        // TAB 7 Default null value
        public ma_seiho_bunsho_kakuninjiko kakuninJikoDefaultValue { get; set; }
        // User infor
        public string loginCD { get; set; }
        public string nm_login { get; set; }
        public string nm_busho_login { get; set; }
        // If shonin 2 removal
        public bool isDeleteExcel { get; set; }
        public bool isApplied { get; set; }
    }

    /// <summary>
    /// Mapping sheet name with source and destinate 
    /// </summary>
    public class SheetMapping
    {
        public string srcSheet { get; set; }
        public string destSheet { get; set; }
    }

    /// <summary>
    /// Seiho bunsho header excel data
    /// </summary>
    public class SeihoBunshoHeaderData
    {
        public string dt_seiho_sakusei { get; set; }
        public string no_seiho { get; set; }
        public string nm_seiho { get; set; }
        public decimal? cd_shain { get; set; }
        public decimal? no_oi { get; set; }
        public decimal? nen { get; set; }
        public short? seq_shisaku { get; set; }
        public string nm_sample { get; set; }
        public string pt_kotei { get; set; }
        public string su_naiyoryo { get; set; }
        public short? su_shomikikan_free_input { get; set; }
        public string kbn_shomikikan_tani { get; set; }
        public string cd_ondo { get; set; }

    }

    /// <summary>
    /// Add nm_yoki_hosho
    /// </summary>
    public class ma_seiho_bunsho_yokihoso_shizai_EXT : ma_seiho_bunsho_yokihoso_shizai
    {
        public string nm_yoki_hoso_shizai { get; set; }
        public string nm_free_title_komoku { get; set; }
        public void getShizaiName(ma_seiho_bunsho_yokihoso headerData)
        {
            if (headerData != null)
            {
                // 資材
                if (headerData.cd_yoki_hoso_shizai01 == this.cd_yoki_hoso_shizai) { this.nm_yoki_hoso_shizai = headerData.nm_yoki_hoso_shizai01; }
                if (headerData.cd_yoki_hoso_shizai02 == this.cd_yoki_hoso_shizai) { this.nm_yoki_hoso_shizai = headerData.nm_yoki_hoso_shizai02; }
                if (headerData.cd_yoki_hoso_shizai03 == this.cd_yoki_hoso_shizai) { this.nm_yoki_hoso_shizai = headerData.nm_yoki_hoso_shizai03; }
                if (headerData.cd_yoki_hoso_shizai04 == this.cd_yoki_hoso_shizai) { this.nm_yoki_hoso_shizai = headerData.nm_yoki_hoso_shizai04; }
                if (headerData.cd_yoki_hoso_shizai05 == this.cd_yoki_hoso_shizai) { this.nm_yoki_hoso_shizai = headerData.nm_yoki_hoso_shizai05; }
                if (headerData.cd_yoki_hoso_shizai06 == this.cd_yoki_hoso_shizai) { this.nm_yoki_hoso_shizai = headerData.nm_yoki_hoso_shizai06; }
                if (headerData.cd_yoki_hoso_shizai07 == this.cd_yoki_hoso_shizai) { this.nm_yoki_hoso_shizai = headerData.nm_yoki_hoso_shizai07; }
                if (headerData.cd_yoki_hoso_shizai08 == this.cd_yoki_hoso_shizai) { this.nm_yoki_hoso_shizai = headerData.nm_yoki_hoso_shizai08; }
                if (headerData.cd_yoki_hoso_shizai09 == this.cd_yoki_hoso_shizai) { this.nm_yoki_hoso_shizai = headerData.nm_yoki_hoso_shizai09; }
                if (headerData.cd_yoki_hoso_shizai10 == this.cd_yoki_hoso_shizai) { this.nm_yoki_hoso_shizai = headerData.nm_yoki_hoso_shizai10; }
                // Free title
                this.nm_free_title_komoku = headerData.nm_free_title_komoku;
            }
        }
    }

    /// <summary>
    /// Add nm_kotei
    /// </summary>
    public class ma_seiho_bunsho_haigo_chui_EXT : ma_seiho_bunsho_haigo_chui
    {
        public string nm_kotei { get; set; }
        // Get name of pt_kotei
        public void getNameKotei(string cd_category)
        {
            using (ShohinKaihatsuEntities context = new ShohinKaihatsuEntities())
            {
                this.nm_kotei = (from i in context.ma_literal where i.cd_category == cd_category && i.cd_literal == this.pt_kotei select i.nm_literal).FirstOrDefault();
            }
        }
    }

    /// <summary>
    /// Add shomikigen literals from TAB 6 data
    /// </summary>
    public class ma_seiho_bunsho_shomikigen_EXT : ma_seiho_bunsho_shomikigen
    {
        public string nm_shomikikan { get; set; }
        public string nm_shomikikan_seizo_fukumu { get; set; }
        public string nm_shomikikan_tani { get; set; }
        public string nm_tokisaki { get; set; }
        public string nm_brand { get; set; }
        public short? su_shomikikan_free_input { get; set; }
        public string nm_nengetsu_hyoji { get; set; }
    }
    
    /// <summary>
    /// Last index of genryo when binding
    /// </summary>
    public class GenryoIndex
    {
        public string sheetName { get; set; }
        public int index { get; set; }
        public double totalWeight { get; set; }
        public string nm_tani_sum { get; set; }
        public bool? kbn_shiagari { get; set; }
        public double? qty_haigo_kei { get; set; }
    }

    /// <summary>
    /// Shisaku property mapping
    /// </summary>
    public class ShisakuPropMapping
    {
        public string key { get; set; }
        public string property { get; set; }
        public string classed { get; set; }
    }


    #endregion "OOP contructions - Base Excel objects"
}
