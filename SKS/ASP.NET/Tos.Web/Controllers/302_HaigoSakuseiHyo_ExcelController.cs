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
using Tos.Web.DataFP;

using System.Runtime.InteropServices;
using System.Dynamic;
using System.Text.RegularExpressions;
using System.Data.SqlClient;

namespace Tos.Web.Controllers
{
    //created from 【SearchInputController(Ver1.7)】 Template
    public class _302_HaigoSakuseiHyo_ExcelController : ApiController
    {
        #region "Controllerで公開するAPI"

        /// <summary>
        /// Download Excel when click btn excel download
        /// </summary>
        /// <param name="Conditions">検索条件</param>
        /// <returns>Excel file</returns>
        [HttpPost]
        public HttpResponseMessage Post([FromBody] HaigoSakuseiExcelCondition Conditions)
        {
            // 読込テンプレートファイルパス
            string templatepath = HttpContext.Current.Server.MapPath(Properties.Settings.Default.ExcelTemplateFolder);
            string dirTemlates = templatepath + "\\" + Properties.Resources.HaigoSakuseiTemplateExcelName + Properties.Resources.ExcelExtension;
            // 保存Excelファイル名 (製法文書_[製法番号]_[yyyyMMddhhmmss].xlsx)
            string excelname = String.Format(Properties.Resources.HaigoSakuseiExcelName, Conditions.cd_haigo) + DateTime.Now.ToString("yyyyMMddHHmmss") + Properties.Resources.ExcelExtension;

            // Excelテンプレートを読み込み、必要な情報をマッピングしてクライアントへ返却
            MemoryStream stream = EditExcelFile(dirTemlates, Conditions);
            return FileUploadDownloadUtility.CreateFileResponse(stream, excelname);
        }

        /// <summary>
        /// Excelファイルの作成（ClosedXML使用）
        /// </summary>
        /// <param name="dirTemlates">検索条件</param>
        /// <returns>作成されたExcelファイルのMemoryStream</returns>
        private MemoryStream EditExcelFile(string dirTemlates, HaigoSakuseiExcelCondition Conditions)
        {
            HaigoSakuseiExcel haigoSakuseiExcel = new HaigoSakuseiExcel();
            haigoSakuseiExcel.getExcel(dirTemlates, Conditions);
            return haigoSakuseiExcel.getStream();
        }
        #endregion

    }

    #region "OOP contructions - Base Excel objects"

    /// <summary>
    /// Excel cell
    /// </summary>
    public class HaigoSakuseiCell
    {
        // Data type
        public static string NUMBER_TYPE = "number";
        public static string DATE_TYPE = "date";
        public static string STRING_TYPE = "string";
        public static string INDEX_IDENTIFY = "index";
        public static string COMMA_STRING = "comma_string";
        public static string BOOLEAN = "index";
        // Cell base informations
        public string position { get; set; }
        public string property { get; set; }
        public string type { get; set; }
        public string format { get; set; }
        public object defaultNull { get; set; }
        public string toStringFormat { get; set; }
        public int? fixedLength { get; set; }
        public int? trailingZero { get; set; }
        private string pCol { get; set; }
        private decimal? pRow { get; set; }
        public bool zeroToNull { get; set; }
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
        public HaigoSakuseiCell()
        {
            this.splitPosition();
        }
    }
    /// <summary>
    /// Base contruct for excel
    /// </summary>
    public class HaigoSakuseiExcel
    {
        // Const
        public static string DATE_FRM = "yyyy/MM/dd";
        public static int KOTEI_HEADER_IND = 10;
        public static int MEISAI_IND = 11;
        public static int SUB_TOTAL_IND = 12;
        public static int TOTAL_IND = 13;
        public static int TMP_ROW_COUNT = 4;
        public static string TMP_BEG_COL = "AX";
        public static string TMP_END_COL = "CN";
        public static string DETAIL_BEG_COL = "B";
        public static string DETAIL_END_COL = "AR";
        public static int DETAIL_BEG_ROW = 10;
        public static string M_KIRIKAE_FP = "2";
        public static string M_KIRIKAE_HJ = "1";
        // Main work book
        public XLWorkbook Excel { get; set; }
        public string sheetName { get; set; }
        private MemoryStream stream { get; set; }
        private HaigoSakuseiHeaderData headerData { get; set; }
        private List<HaigoSakuseiMeisaiData> detailData { get; set; }
        private List<HaigoSakuseiCell> headerMapping { get; set; }
        private List<HaigoSakuseiCell> detailMapping { get; set; }
        private List<HaigoSakuseiCell> subTotalMapping { get; set; }
        private List<HaigoSakuseiCell> totalMapping { get; set; }
        private List<HaigoSakuseiCell> koteiMapping { get; set; }
        private int detailIndex { get; set; }
        private int lastMeisaiIndex { get; set; }
        private decimal totalAll { get; set; }

        // Base excel param
        public HaigoSakuseiExcelCondition Conditions { get; set; }
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
        public void copyTempRow(int tmpIndex)
        {
            var sheet = this.Excel.Worksheet(this.sheetName);
            int rowIndex = DETAIL_BEG_ROW + this.detailIndex;
            string rowAddr = (TMP_BEG_COL + tmpIndex) + ":" + (TMP_END_COL + tmpIndex);
            string nextRowAddr = (DETAIL_BEG_COL + rowIndex) + ":" + (DETAIL_END_COL + rowIndex);
            var tempRow = sheet.Range(rowAddr);
            // Copy to next row
            sheet.Range(nextRowAddr).Style = tempRow.Style;
            sheet.Cell(DETAIL_BEG_COL + rowIndex).Value = tempRow;
            // Set next row height
            sheet.Row(rowIndex).Height = sheet.Row(tmpIndex).Height;
        }
        // Merge cell
        public void mergeCell(string beginAddr, string endAddr)
        {
            this.Excel.Worksheet(this.sheetName).Range(beginAddr + ":" + endAddr).Column(1).Merge();
        }
        // Set row color
        public void setBackgroundColor(string beginAddr, string endAddr, XLColor color)
        {
            this.Excel.Worksheet(this.sheetName).Range(beginAddr + ":" + endAddr).Style.Fill.BackgroundColor = color;
        }
        // Set fore color
        public void setForeColor(string beginAddr, string endAddr, XLColor color)
        {
            this.Excel.Worksheet(this.sheetName).Range(beginAddr + ":" + endAddr).Style.Font.FontColor = color;
        }
        // Set row height
        public void setRowHeight(double height)
        {
            this.Excel.Worksheet(this.sheetName).Row(this.detailIndex + DETAIL_BEG_ROW).Height = height;
        }
        // Remove template rows
        public void removeTmp()
        {
            var begAddr = TMP_BEG_COL + (DETAIL_BEG_ROW);
            var endAddr = TMP_END_COL + (DETAIL_BEG_ROW + TMP_ROW_COUNT - 1);
            this.Excel.Worksheet(this.sheetName).Range(begAddr + ":" + endAddr).Delete(XLShiftDeletedCells.ShiftCellsUp);
        }
        // Get seiho data
        private void getSeihoData()
        {
            using (ShohinKaihatsuEntities context = new ShohinKaihatsuEntities())
            {
                context.Configuration.ProxyCreationEnabled = false;
                // Get seiho data
                var seihoData = context.ma_seiho.Where(x => x.no_seiho == Conditions.no_seiho).FirstOrDefault();
                if (seihoData != null)
                {
                    this.headerData.no_seiho = seihoData.no_seiho;
                    this.headerData.nm_seiho = seihoData.nm_seiho;
                    this.headerData.nm_seiho_sakusei_1 = seihoData.nm_seiho_sakusei_1;
                }
                if (Conditions.M_kirikae == M_KIRIKAE_HJ)
                {
                    this.headerData.nm_title = "表示用配合作成表";
                }
            }
        }
        // Check valid haigo data
        private void getHaigoMeiMeta()
        {
            using (FOODPROCSEntities context = new FOODPROCSEntities(Conditions.cd_kaisha, Conditions.cd_kojyo))
            {
                context.Configuration.ProxyCreationEnabled = false;
                // Check valid haigo data
                var query = " SELECT "
                            + "   m.cd_haigo	"
                            + " FROM ma_haigo_mei m	"
                            + " WHERE	"
                            + "   m.cd_haigo + '-' + CAST(m.no_han AS varchar) IN ('@cd_haigo-@no_han')	"
                            + "   AND m.cd_haigo + '-' + CAST(m.no_han AS varchar) NOT IN	"
                            + "   ( "
                            + "       SELECT r.cd_haigo + '-' + CAST(r.no_han AS VARCHAR) "
                            + "       FROM ma_haigo_recipe r "
                            + "       WHERE r.flg_sakujyo = 0	"
                            + "           AND r.qty_haigo_h = m.qty_haigo_h"
                            + "   )	"
                            + "   AND m.flg_sakujyo = 0	"
                            + "   AND m.qty_kihon = m.qty_haigo_h";

                List<HaigoMeiMetaData> result = context.Database.SqlQuery<HaigoMeiMetaData>(query, new SqlParameter("@cd_haigo", Conditions.cd_haigo)
                                                                                                 , new SqlParameter("@no_han", Conditions.no_han)).ToList();
                headerData.exist_haigo = false;
                if (result == null || result.Count == 0)
                {
                    headerData.exist_haigo = true;
                    headerData.cd_haigo = Conditions.cd_haigo;
                }
            }
        }
        // Main query of detail data
        private void getDetailData()
        {
            using (FOODPROCSEntities context = new FOODPROCSEntities(Conditions.cd_kaisha, Conditions.cd_kojyo))
            {
                context.Configuration.ProxyCreationEnabled = false;
                string suffix = String.Empty;
                if (Conditions.M_kirikae == M_KIRIKAE_HJ) {
                    suffix = "_hyoji";
                }
                // Get haigo meisai data
                var query = " SELECT "
                            + "     t.nm_shozoku "
                            + "     , m.no_seiho "
                            + "     , m.no_han "
                            + "     , m.dt_from "
                            + "     , m.dt_to  "
                            + "     , m.cd_haigo "
                            + "     , m.nm_haigo  "
                            + "     , m.dt_toroku "
                            + "     , m.biko	"
                            + "     , m.dt_henko  "
                            + "     , m.ritsu_kihon				AS keisu "
                            + "     , GETDATE()					AS dt_output "
                            + "     , ''						AS id_kenkyujyo  "
                            + "     , m.cd_tanto_hinkan  "
                            + "     , t.nm_tanto  "
                            + "     , r.no_kotei "
                            + "     , r.cd_hin "
                            + "     , r.nm_hin  "
                            + "     , CASE "
                            + "         WHEN m.kbn_vw='04' THEN "
                            + "             CASE "
                            + "                 WHEN IsNull(h.cd_tani_hin, '04') = '04' THEN r.qty_haigo "
                            + "                 ELSE r.qty_haigo*IsNull(h.hijyu,1) "
                            + "             END	"
                            + "         ELSE "
                            + "             CASE "
                            + "                 WHEN IsNull(h.cd_tani_hin, '04') = '04' THEN r.qty_haigo/IsNull(h.hijyu,1)	"
                            + "                 ELSE r.qty_haigo "
                            + "             END	"
                            + "     END AS qty_haigo	"
                            + "     , CASE "
                            + "         WHEN r.kbn_hin='9' THEN NULL "
                            + "         WHEN mk.cd_tani_shiyo = 3 THEN r.qty_haigo * 1000	"
                            + "         ELSE r.qty_haigo	"
                            + "     END AS qty_hm "
                            + "     , CASE r.kbn_hin "
                            + "         WHEN 9 THEN tn.nm_tani "
                            + "         ELSE	"
                            + "             CASE IsNull(h.cd_tani_hin, '04') "
                            + "                 WHEN '04' THEN "
                            + "                     CASE mk.cd_tani_shiyo "
                            + "                         WHEN '03' THEN 'g' "
                            + "                         ELSE 'kg' "
                            + "                     END	"
                            + "                 ELSE "
                            + "                     CASE mk.cd_tani_shiyo "
                            + "                         WHEN '03' THEN 'ml' "
                            + "                         ELSE 'L' "
                            + "                     END "
                            + "             END	"
                            + "     END AS nm_tani	"
                            + "     , CASE "
                            + "         WHEN m.kbn_vw='04' THEN 'kg' "
                            + "         ELSE 'L' "
                            + "     END AS nm_tani_sum "
                            + "     , r.qty_nisugata AS litter "
                            + "     , CASE r.kbn_hin "
                            + "         WHEN 9 THEN r.hijyu "
                            + "         ELSE h.hijyu "
                            + "     END AS hijyu "
                            + "     , CASE h.kbn_hin "
                            + "         WHEN 4 THEN '仕掛品' "
                            + "         ELSE h.no_kikaku "
                            + "     END AS no_kikaku "
                            + "     , r.kbn_hin	"
                            + "     , r.no_tonyu	"
                            + "     , CASE r.kbn_bunkatsu "
                            + "         WHEN 0 THEN '' "
                            + "         ELSE CAST(r.kbn_bunkatsu AS varchar) "
                            + "     END AS kbn_bunkatsu   "
                            + "     , ''									AS nm_seiho_sakusei1	"
                            + "     , CAST(m.kbn_shiagari AS tinyint)		AS kbn_shiagari	"
                            + "     , m.qty_haigo_kei	"
                            + " FROM ma_haigo_mei" + suffix + "  m	"
                            + " INNER JOIN ma_haigo_recipe" + suffix + " r "
                            + " ON m.cd_haigo		=	r.cd_haigo	"
                            + " AND m.no_han		=	r.no_han	";
                if (Conditions.M_kirikae == M_KIRIKAE_FP)
                {
                    query +=  " AND m.qty_haigo_h	=	r.qty_haigo_h ";
                }
                query +=      " LEFT JOIN ma_tanto t	"
                            + " ON RIGHT('0000000000' + LTRIM(RTRIM(m.cd_tanto_hinkan)), 10) = RIGHT('0000000000' + LTRIM(RTRIM(t.cd_tanto)),10) "
                            + " AND t.flg_mishiyo	=	0	"
                            + " AND t.flg_sakujyo	=	0	"
                            + " LEFT JOIN ma_mark mk	"
                            + " ON mk.cd_mark		=	r.cd_mark "
                            + " LEFT JOIN ma_tani tn	"
                            + " ON mk.cd_tani_shiyo	=	tn.cd_tani	"
                            + " LEFT JOIN SS_vw_hin" + suffix + " h	"
                            + " ON cast(h.cd_hin AS varchar)	=	(CASE WHEN isnumeric(r.cd_hin)=1 then cast(cast(r.cd_hin as bigint) as varchar) ELSE r.cd_hin END) "
                            + " AND h.kbn_hin_toroku = r.kbn_hin "
                            + " WHERE "
                            + "     (m.cd_haigo + '-' + CAST(m.no_han AS varchar)) in (@cd_haigo)	"
                            + "     AND m.flg_sakujyo	=	0 "
                            + "     AND r.flg_sakujyo	=	0 "
                            + "     AND m.qty_kihon		=	m.qty_haigo_h	"
                            + " ORDER BY "
                            + "     m.cd_haigo, m.no_han, r.no_kotei, r.no_tonyu	";
                this.detailData = context.Database.SqlQuery<HaigoSakuseiMeisaiData>(query, new SqlParameter("@cd_haigo", Conditions.cd_haigo + "-" + Conditions.no_han)).ToList();
                if (this.detailData != null && this.detailData.Count > 0)
                { 
                    var dData = this.detailData[0];
                    this.headerData.nm_tanto = dData.nm_tanto;
                    this.headerData.nm_shozoku = dData.nm_shozoku;
                    this.headerData.nm_haigo = dData.nm_haigo;
                    this.headerData.no_han = dData.no_han;
                    this.headerData.dt_range = dData.dt_from.ToString(DATE_FRM) + "～" + dData.dt_to.ToString(DATE_FRM);
                    this.headerData.cd_tanto_hinkan = dData.cd_tanto_hinkan;
                    this.headerData.nm_tanto_hinkan = dData.nm_tanto;
                    this.headerData.dt_toroku = dData.dt_toroku.ToString(DATE_FRM);
                    this.headerData.dt_henko = dData.dt_henko.ToString(DATE_FRM);
                    this.headerData.dt_output = dData.dt_output.ToString(DATE_FRM);
                    this.headerData.keisu = dData.keisu;
                    this.headerData.biko = dData.biko;
                    this.headerData.kbn_shiagari = dData.kbn_shiagari;
                }
            }
        }
        // Get excel data
        private void getExcelData()
        {
            this.headerData = new HaigoSakuseiHeaderData();
            this.getSeihoData();
            this.getHaigoMeiMeta();
            this.getDetailData();
            
        }
        // Get mapping between properties and cell address
        private void getMappingAddr()
        {
            // Contruct
            this.headerMapping = new List<HaigoSakuseiCell>();
            this.detailMapping = new List<HaigoSakuseiCell>();
            this.subTotalMapping = new List<HaigoSakuseiCell>();
            this.totalMapping = new List<HaigoSakuseiCell>();
            this.koteiMapping = new List<HaigoSakuseiCell>();
            headerMapping.Add(new HaigoSakuseiCell() { position = "W1", property = "nm_title", defaultNull = String.Empty });
            headerMapping.Add(new HaigoSakuseiCell() { position = "AR1", property = "nm_shozoku", defaultNull = String.Empty });
            // 開発担当者
            headerMapping.Add(new HaigoSakuseiCell() { position = "AL2", property = "nm_seiho_sakusei_1" });
            // 製法番号
            headerMapping.Add(new HaigoSakuseiCell() { position = "G3", property = "no_seiho" });
            // 第
            headerMapping.Add(new HaigoSakuseiCell() { position = "Q3", property = "no_han" });
            // 版
            headerMapping.Add(new HaigoSakuseiCell() { position = "T3", property = "dt_range" });
            // 品管ID
            headerMapping.Add(new HaigoSakuseiCell() { position = "AL3", property = "cd_tanto_hinkan" });
            headerMapping.Add(new HaigoSakuseiCell() { position = "AL4", property = "nm_tanto_hinkan" });
            // メモ
            headerMapping.Add(new HaigoSakuseiCell() { position = "C4", property = "cd_haigo" });
            headerMapping.Add(new HaigoSakuseiCell() { position = "G4", property = "nm_haigo" });
            headerMapping.Add(new HaigoSakuseiCell() { position = "E5", property = "biko" });
            // 登録日
            headerMapping.Add(new HaigoSakuseiCell() { position = "AG5", property = "dt_toroku" });
            // 更新日
            headerMapping.Add(new HaigoSakuseiCell() { position = "AG6", property = "dt_henko" });
            // 出力日
            headerMapping.Add(new HaigoSakuseiCell() { position = "AG7", property = "dt_output" });
            // 基本倍率
            headerMapping.Add(new HaigoSakuseiCell() { position = "AI8", property = "keisu" });
            // kbn_bunkatsu
            detailMapping.Add(new HaigoSakuseiCell() { position = "B" + DETAIL_BEG_ROW, property = "kbn_bunkatsu" });
            // no_tonyu
            detailMapping.Add(new HaigoSakuseiCell() { position = "D" + DETAIL_BEG_ROW, property = "no_tonyu" });
            // cd_hin
            detailMapping.Add(new HaigoSakuseiCell() { position = "F" + DETAIL_BEG_ROW, property = "cd_hin" });
            // nm_hin
            detailMapping.Add(new HaigoSakuseiCell() { position = "J" + DETAIL_BEG_ROW, property = "nm_hin" });
            // qty_haigo
            detailMapping.Add(new HaigoSakuseiCell() { position = "AD" + DETAIL_BEG_ROW, property = "qty_hm", format = "#,0.000", type = HaigoSakuseiCell.NUMBER_TYPE, defaultNull = string.Empty });
            // nm_tani
            detailMapping.Add(new HaigoSakuseiCell() { position = "AJ" + DETAIL_BEG_ROW, property = "nm_tani" });
            // no_kikaku
            detailMapping.Add(new HaigoSakuseiCell() { position = "AL" + DETAIL_BEG_ROW, property = "no_kikaku" });
            // Sub total base on kotei
            subTotalMapping.Add(new HaigoSakuseiCell() { position = "J" + DETAIL_BEG_ROW, property = "label" });
            subTotalMapping.Add(new HaigoSakuseiCell() { position = "L" + DETAIL_BEG_ROW, property = "no_kotei", type = HaigoSakuseiCell.NUMBER_TYPE });
            subTotalMapping.Add(new HaigoSakuseiCell() { position = "AD" + DETAIL_BEG_ROW, property = "total", format = "#,0.000000" });
            subTotalMapping.Add(new HaigoSakuseiCell() { position = "AJ" + DETAIL_BEG_ROW, property = "tani" });
            // Total of the file
            totalMapping.Add(new HaigoSakuseiCell() { position = "J" + DETAIL_BEG_ROW, property = "label" });
            totalMapping.Add(new HaigoSakuseiCell() { position = "AD" + DETAIL_BEG_ROW, property = "total", format = "#,0.000000" });
            totalMapping.Add(new HaigoSakuseiCell() { position = "AJ" + DETAIL_BEG_ROW, property = "tani" });
            // No kotei
            koteiMapping.Add(new HaigoSakuseiCell() { position = "B" + DETAIL_BEG_ROW, property = "label" });
            koteiMapping.Add(new HaigoSakuseiCell() { position = "E" + DETAIL_BEG_ROW, property = "no_kotei" });
            // Contruct config
            //foreach (var prop in this.headerMapping)
            //{
            //    prop.splitPosition();
            //}
            foreach (var prop in this.detailMapping)
            {
                prop.splitPosition();
            }
            foreach (var prop in this.subTotalMapping)
            {
                prop.splitPosition();
            }
            foreach (var prop in this.totalMapping)
            {
                prop.splitPosition();
            }
            foreach (var prop in this.koteiMapping)
            {
                prop.splitPosition();
            }
        }
        // Bind header data
        private void bindHeader()
        {
            foreach (var prop in this.headerMapping)
            {
                prop.setValue(this.headerData, this.Excel, this.sheetName);
            }            
        }
        // Get obj total data
        private HaigoSakuseiTotalData getTotalData(string label = null, int? no_kotei = null, decimal? sum = null, string tani = null)
        {
            HaigoSakuseiTotalData tData = new HaigoSakuseiTotalData();
            if (sum != null)
            {
                sum = this.truncateNumber(sum ?? 0);
            }
            tData.label = label;
            tData.no_kotei = no_kotei;
            tData.total = sum;
            tData.tani = tani;
            return tData;
        }
        // Bind sub total is sum of qty on each kotei
        private void addSubTotal(string label, int no_kotei, decimal sum, string tani)
        { 
            // Draw sub total row
            this.copyTempRow(SUB_TOTAL_IND);
            this.totalAll += sum;
            // Bind data
            var tData = getTotalData(label, no_kotei, sum, tani);
            foreach (var prop in this.subTotalMapping)
            {
                prop.setValue(tData, this.Excel, this.sheetName, this.detailIndex);
            }
            this.detailIndex += 2;
        }
        // Add total row
        private void addTotal(string label, decimal? sum, string tani, bool isSmallPadding = false)
        {
            // Draw total row
            this.copyTempRow(TOTAL_IND);
            // Bind data
            var tData = getTotalData(label, null, sum, tani);
            foreach (var prop in this.totalMapping)
            {
                prop.setValue(tData, this.Excel, this.sheetName, this.detailIndex);
            }
            this.detailIndex++;
            if (isSmallPadding) 
            {
                this.setRowHeight(7);
            }
        }
        // New kotei header
        private void addKotei(string label, int no_kotei)
        {
            var tData = getTotalData(label, no_kotei);
            foreach (var prop in this.koteiMapping)
            {
                prop.setValue(tData, this.Excel, this.sheetName, this.detailIndex);
            }
            this.detailIndex++;
        }
        // prepair template rows before binding
        private void prepairTmpRow()
        {
            // Move tamplate rows to other side of the sheet
            var sheet = this.Excel.Worksheet(this.sheetName);
            int tmpRowIndex = DETAIL_BEG_ROW;
            int targetRowIndex = tmpRowIndex;
            string tmpRowAddr = (DETAIL_BEG_COL + tmpRowIndex) + ":" + (DETAIL_END_COL + (tmpRowIndex + TMP_ROW_COUNT));
            string targetRowAddr = (TMP_BEG_COL + targetRowIndex) + ":" + (TMP_END_COL + (targetRowIndex + TMP_ROW_COUNT));
            var tempRow = sheet.Range(tmpRowAddr);
            sheet.Range(targetRowAddr).Style = tempRow.Style;
            sheet.Cell(TMP_BEG_COL + targetRowIndex).Value = tempRow;
            // Clear old tmp
            // Note: Delete range in closed xml cause error to template file
            tempRow.Merge();
            tempRow.Value = "";
            // Clear border
            tempRow.Style.Border.TopBorder = XLBorderStyleValues.None;
            tempRow.Style.Border.RightBorder = XLBorderStyleValues.None;
            tempRow.Style.Border.BottomBorder = XLBorderStyleValues.None;
            tempRow.Style.Border.LeftBorder = XLBorderStyleValues.None;
            // Unmerge
            tempRow.Unmerge();
            // Clear fore color
            //tempRow.Style.Font.FontColor = XLColor.NoColor;
        }
        // Bind detail data
        private void bindDetail()
        {
            this.detailIndex = 0;
            int curKotei = -1;
            string sumTani = String.Empty;
            decimal subTotal = 0;
            double? qty_haigo_kei = 0;
            foreach (var dData in this.detailData)
            {
                if (dData.no_kotei != curKotei)
                {
                    if (curKotei != -1)
                    { 
                        // Add sub total
                        this.addSubTotal("工程", curKotei, subTotal, sumTani);
                    }
                    // Add new kotei
                    this.addKotei("工程：", dData.no_kotei);
                    // Reset summary data
                    curKotei = dData.no_kotei;
                    sumTani = dData.nm_tani_sum;
                    qty_haigo_kei = dData.qty_haigo_kei;
                    subTotal = 0;
                }
                this.copyTempRow(MEISAI_IND);
                foreach (var prop in this.detailMapping)
                {
                    prop.setValue(dData, this.Excel, this.sheetName, this.detailIndex);
                }
                subTotal += dData.qty_haigo == null ? 0 : (decimal)dData.qty_haigo;
                this.detailIndex++;
            }
            this.addSubTotal("工程", curKotei, subTotal, sumTani);
            this.addTotal("配合総計", this.totalAll, sumTani, true);
            // Skip a row
            this.detailIndex++;
            if (this.headerData.kbn_shiagari == 1)
            {
                // Final total data
                this.addTotal("仕上重量", (decimal)qty_haigo_kei, sumTani);
            }
            else
            {
                // Final total data
                this.addTotal("仕上重量", null, null);
            }
            // Remove tmp rows
            this.removeTmp();
        }
        // Binding flow
        private void bindData()
        {
            this.prepairTmpRow();
            if (this.headerData.exist_haigo)
            {
                this.getMappingAddr();
                this.bindHeader();
                this.bindDetail();
            }
            else 
            {
                this.removeTmp();
            }
            // Save to stream
            this.Excel.SaveAs(this.stream);
        }
        // Get excel file
        public void getExcel(string dirTemlates, HaigoSakuseiExcelCondition Conditions)
        {
            if (Conditions == null)
            {
                return;
            }
            this.stream = new MemoryStream();
            // Contruct new excel file
            this.Excel = new XLWorkbook(dirTemlates);
            // Set sheet name
            this.sheetName = "配合作成表(工場)";
            // Set base param
            this.Conditions = Conditions;
            // Contruct and get all sheet data
            this.getExcelData();
            // Set data to excel file
            this.bindData();
        }
        // Get file stream after binding
        public MemoryStream getStream()
        {
            return this.stream;
        }
        private decimal truncateNumber(decimal value)
        {
            if (value == 0)
            {
                return value;
            }
            decimal pow = 1000000;
            return ((Decimal)(Decimal.ToInt64(value * pow)) / pow);
        }
    }

    public class HaigoMeiMetaData
    {
        public string cd_haigo { get; set; }
    }

    /// <summary>
    /// Excel header data
    /// </summary>
    public class HaigoSakuseiHeaderData
    {
        public string nm_title { get; set; }
        public string no_seiho { get; set; }
        public string nm_seiho { get; set; }
        public string nm_tanto { get; set; }
        public string nm_shozoku { get; set; }
        public string nm_seiho_sakusei_1 { get; set; }
        public bool exist_haigo { get; set; }
        public string cd_haigo { get; set; }
        public string nm_haigo { get; set; }
        public int no_han { get; set; }
        public string dt_range { get; set; }
        public string cd_tanto_hinkan { get; set; }
        public string nm_tanto_hinkan { get; set; }
        public string dt_toroku { get; set; }
        public string dt_henko { get; set; }
        public string dt_output { get; set; }
        public double? keisu { get; set; }
        public string biko { get; set; }
        public byte? kbn_shiagari { get; set; }
    }

    /// <summary>
    /// Excel detail data
    /// </summary>
    public class HaigoSakuseiMeisaiData
    {
        public string nm_shozoku { get; set; }
        public string no_seiho { get; set; }
        public int no_han { get; set; }
        public System.DateTime dt_from { get; set; }
        public System.DateTime dt_to { get; set; }
        public string cd_haigo { get; set; }
        public string nm_haigo { get; set; }
        public System.DateTime dt_toroku { get; set; }
        public string biko { get; set; }
        public System.DateTime dt_henko { get; set; }
        public Nullable<double> keisu { get; set; }
        public System.DateTime dt_output { get; set; }
        public string id_kenkyujyo { get; set; }
        public string cd_tanto_hinkan { get; set; }
        public string nm_tanto { get; set; }
        public int no_kotei { get; set; }
        public string cd_hin { get; set; }
        public string nm_hin { get; set; }
        public Nullable<double> qty_haigo { get; set; }
        public Nullable<double> qty_hm { get; set; }
        public string nm_tani { get; set; }
        public string nm_tani_sum { get; set; }
        public Nullable<double> litter { get; set; }
        public Nullable<double> hijyu { get; set; }
        public string no_kikaku { get; set; }
        public string kbn_hin { get; set; }
        public int no_tonyu { get; set; }
        public string kbn_bunkatsu { get; set; }
        public string nm_seiho_sakusei1 { get; set; }
        public Nullable<byte> kbn_shiagari { get; set; }
        public Nullable<double> qty_haigo_kei { get; set; }
    }

    /// <summary>
    /// Excel request param
    /// </summary>
    public class HaigoSakuseiExcelCondition
    {
        // Base param
        public string no_seiho { get; set; }
        public string cd_haigo { get; set; }
        public string no_han { get; set; }
        public int cd_kaisha { get; set; }
        public int cd_kojyo { get; set; }
        public string M_kirikae { get; set; }
    }

    /// <summary>
    /// Total row data
    /// </summary>
    public class HaigoSakuseiTotalData
    {
        public string label { get; set; }
        public int? no_kotei { get; set; }
        public decimal? total { get; set; }
        public string tani { get; set; }
    }

    #endregion "OOP contructions - Base Excel objects"
}
