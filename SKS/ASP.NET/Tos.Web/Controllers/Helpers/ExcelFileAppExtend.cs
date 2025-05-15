/** 最終更新日 : 2016-10-17 **/
using DocumentFormat.OpenXml;
using DocumentFormat.OpenXml.Packaging;
using DocumentFormat.OpenXml.Spreadsheet;
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Web;
using System.Text.RegularExpressions;


namespace Tos.Web.Controllers.Helpers
{
    public partial class ExcelFile
    {
        //アプリケーションでExcel出力の拡張機能を実装する場合はこちらに実装してください。

        /// <summary>
        /// 行をコピーする
        /// </summary>
        /// <param name="sheetName"></param>
        /// <param name="rowNumberFrom"></param>
        /// <param name="rowNumberTo"></param>
        //public void CopyRow(string sheetName, UInt32 rowNumberFrom, UInt32 rowNumberTo, bool isSave)
        //{
        //    WorkbookPart book = document.WorkbookPart;
        //    Sheet sheet = book.Workbook.Descendants<Sheet>().Where((s) => s.Name == sheetName).FirstOrDefault();

        //    if (sheet != null)
        //    {
        //        Worksheet ws = ((WorksheetPart)(book.GetPartById(sheet.Id))).Worksheet;
        //        SheetData sheetData = ws.GetFirstChild<SheetData>();

        //        Row row = (Row)GetRow(sheetData, rowNumberFrom).CloneNode(true);
        //        row.RowIndex = rowNumberTo;

        //        IEnumerable<Cell> cells = row.Elements<Cell>().AsEnumerable<Cell>();
        //        foreach (Cell cell in cells)
        //        {
        //            string column = GetColumnName(cell.CellReference.Value);
        //            cell.CellReference = column + rowNumberTo;
        //        }

        //        sheetData.Append(row);

        //        if (isSave)
        //            ws.Save();
        //    }
        //}

        /// <summary>
        /// Coppy Sheet
        /// </summary>
        /// /// <param name="spTemplateDoc">spTemplateDoc</param>
        /// /// <param name="sheetNameCopy">sheet name copy</param>
        /// /// <param name="sheetNameNew">sheet name new</param>
        /// 
        public static void CopySheet(SpreadsheetDocument spTemplateDoc, string sheetNameCopy, string sheetNameNew)
        {
            // テンプレートのSheet1のデータを取得
            WorkbookPart book = spTemplateDoc.WorkbookPart;

            //Get id in sheet
            string relId = book.Workbook.Descendants<Sheet>()
            .Where(s => s.Name.Value.Equals(sheetNameCopy))
            .First()
            .Id;

            // Sheet名に対するWorkSheetPartを取得する
            WorksheetPart tempSheetPart = (WorksheetPart)book.GetPartById(relId);

            // MemoryStreamにDocumentクローンを作成
            SpreadsheetDocument cloneDoc = SpreadsheetDocument.Create(new MemoryStream(), spTemplateDoc.DocumentType);
            // MemoryStreamのDocumentにテンプレートWorkbookのクローンを生成
            WorkbookPart cloneWorkbookPart = cloneDoc.AddWorkbookPart();
            // MemoryStreamのWorkbookにWorksheetPartのクローンを生成
            WorksheetPart cloneWorksheetPart = cloneWorkbookPart.AddPart<WorksheetPart>(tempSheetPart);
            // MemoryStreamのWorkbookにWorksheetPartのクローンを生成
            WorksheetPart clonedSheet = book.AddPart<WorksheetPart>(cloneWorksheetPart);

            // デフォルトではワークシートの作業グループが設定されているため、
            // WorksheetPartオブジェクトのTabSelectedプロパティをfalseに設定することで作業グループを解除します。
            tempSheetPart.Worksheet.SheetViews.GetFirstChild<SheetView>().TabSelected = BooleanValue.FromBoolean(false);
            clonedSheet.Worksheet.SheetViews.GetFirstChild<SheetView>().TabSelected = BooleanValue.FromBoolean(false);

            // Table definition parts are somewhat special and need unique ids...so let's make an id based on count
            int numTableDefParts = tempSheetPart.GetPartsCountOfType<TableDefinitionPart>();
            int tableId = 0;
            // Clean up table definition parts (tables need unique ids)
            if (numTableDefParts != 0)
            {
                foreach (TableDefinitionPart tableDefPart in tempSheetPart.TableDefinitionParts)
                {
                    tableId++;
                    tableDefPart.Table.Id = (uint)tableId;
                    tableDefPart.Table.DisplayName = "CopiedTable" + tableId;
                    tableDefPart.Table.Name = "CopiedTable" + tableId;
                    tableDefPart.Table.Save();
                }
            }
            // There should only be one sheet that has focus                          
            //CleanView(clonedSheet);

            // Workbook Sheetの取得
            Sheets tempSheets = spTemplateDoc.WorkbookPart.Workbook.GetFirstChild<Sheets>();
            //Sheets tempSheets = book.Workbook.GetFirstChild<Sheets>();
            // シート名生成
            uint cloneSheetId = 1;
            if (tempSheets.Elements<Sheet>().Count() > 0)
            {
                cloneSheetId = tempSheets.Elements<Sheet>().Select(s => s.SheetId.Value).Max() + 1;
            }
            // シート名は、メニュー番号などになる。
            // string cloneSheetName = "Sheet" + cloneSheetId;
            string cloneSheetName = sheetNameNew;
            Sheet copiedSheet = new Sheet();
            copiedSheet.Name = cloneSheetName;
            copiedSheet.Id = book.GetIdOfPart(clonedSheet);
            // SheetIdの設定
            // Memo: 必ずしもSheet1がSheetId「1」なわけではない
            copiedSheet.SheetId = (uint)tempSheets.Elements<Sheet>().Select(s => s.SheetId.Value).Max() + 1;
            //copiedSheet.SheetId = (uint)tempSheets.ChildElements.Count + 1;
            // シートの追加
            tempSheets.Append(copiedSheet);
            Worksheet ws = ((WorksheetPart)(book.GetPartById(copiedSheet.Id))).Worksheet;
            ws.Save();
        }

        public static WorksheetPart GetWorkSheetPart(WorkbookPart workbookPart, string sheetName)
        {
            //Get the relationship id of the sheetname
            string relId = workbookPart.Workbook.Descendants<Sheet>()
                .Where(s => s.Name.Value.Equals(sheetName))
                .First()
                .Id;

            return (WorksheetPart)workbookPart.GetPartById(relId);
        }

        static void CleanView(WorksheetPart worksheetPart)
        {
            //There can only be one sheet that has focus
            SheetViews views = worksheetPart.Worksheet.GetFirstChild<SheetViews>();

            if (views != null)
            {
                views.Remove();
                worksheetPart.Worksheet.Save();
            }
        }

        static void FixupTableParts(WorksheetPart worksheetPart, int numTableDefParts)
        {
            int tableId = numTableDefParts;
            //Every table needs a unique id and name
            foreach (TableDefinitionPart tableDefPart in worksheetPart.TableDefinitionParts)
            {
                tableId++;
                tableDefPart.Table.Id = (uint)tableId;
                tableDefPart.Table.DisplayName = "CopiedTable" + tableId;
                tableDefPart.Table.Name = "CopiedTable" + tableId;
                tableDefPart.Table.Save();
            }
        }

        public static void CopySheet2(SpreadsheetDocument spTemplateDoc, string sheetNameCopy, string sheetNameNew)
        {
            // テンプレートのSheet1のデータを取得
            WorkbookPart book = spTemplateDoc.WorkbookPart;

            //Get id in sheet
            string relId = book.Workbook.Descendants<Sheet>()
            .Where(s => s.Name.Value.Equals(sheetNameCopy))
            .First()
            .Id;

            // Sheet名に対するWorkSheetPartを取得する
            WorksheetPart tempSheetPart = (WorksheetPart)book.GetPartById(relId);
            // MemoryStreamにDocumentクローンを作成
            SpreadsheetDocument cloneDoc = SpreadsheetDocument.Create(new MemoryStream(), spTemplateDoc.DocumentType);
            // MemoryStreamのDocumentにテンプレートWorkbookのクローンを生成
            WorkbookPart cloneWorkbookPart = cloneDoc.AddWorkbookPart();
            // MemoryStreamのWorkbookにWorksheetPartのクローンを生成
            WorksheetPart cloneWorksheetPart = cloneWorkbookPart.AddPart<WorksheetPart>(tempSheetPart);
            // MemoryStreamのWorkbookにWorksheetPartのクローンを生成
            WorksheetPart clonedSheet = book.AddPart<WorksheetPart>(cloneWorksheetPart);
        }


        //public void RemoveSheet(SpreadsheetDocument document, string sheetToDelete)
        //{
        //    var workbookPart = document.WorkbookPart;

        //    string sheetId = workbookPart.Workbook.Descendants<Sheet>()
        //    .Where(s => s.Name.Value.Equals(sheetToDelete))
        //    .First()
        //    .Id;
        //    // Get the SheetToDelete from workbook.xml
        //    var theSheet = workbookPart.Workbook.Descendants<Sheet>()
        //                               .FirstOrDefault(s => s.Id == sheetId);

        //    if (theSheet == null)
        //    {
        //        return;
        //    }

        //    // Remove the sheet reference from the workbook.
        //    var worksheetPart = (WorksheetPart)(workbookPart.GetPartById(sheetId));
        //    theSheet.Remove();

        //    // Delete the worksheet part.
        //    //workbookPart.DeletePart(worksheetPart);

        //}


        public MemoryStream getStream()
        { 
            //this.document.WorkbookPart.Workbook.Save();
            return (MemoryStream)this.stream;
        }

        //public ExcelFile(SpreadsheetDocument template)
        //{
        //    this.stream = new MemoryStream();
        //    this.document = SpreadsheetDocument.Create(this.stream, template.DocumentType);
        //    WorkbookPart wbp = this.document.AddWorkbookPart();   // Add workbook part
        //    WorksheetPart wsp = wbp.AddNewPart<WorksheetPart>(); // Add worksheet part
        //    this.document.WorkbookPart.Workbook = new Workbook();
        //    this.document.WorkbookPart.Workbook.Save();
        //}
        public ExcelFile(MemoryStream stream)
        {
            this.stream = stream;
            this.document = SpreadsheetDocument.Open(this.stream, true);
        }

        /// <summary>
        /// 指定されたセル番地に値のセットを行います。
        /// </summary>
        /// <param name="sheetName">シート名</param>
        /// <param name="addressName">対象のセル番地</param>
        /// <param name="value">セットする値</param>
        /// <param name="styleIndex">指定された値を対象のセルのCellオブジェクトのStyleIndexプロパティに設定します。</param>
        /// <param name="isString">セットする値が文字列の場合はtrueを指定し、それ以外はfalseを指定する。</param>
        /// <returns>ture/false</returns>
        public bool UpdateValue(WorkbookPart book, string sheetName, string addressName, string value, UInt32Value styleIndex, bool isString, bool isSave = true)
        {
            bool updated = false;

            Sheet sheet = this.document.WorkbookPart.Workbook.Descendants<Sheet>().Where((s) => s.Name == sheetName).FirstOrDefault();

            if (sheet != null)
            {
                Worksheet ws = ((WorksheetPart)(book.GetPartById(sheet.Id))).Worksheet;
                Cell cell = InsertCellInWorksheet(ws, addressName);

                if (isString)
                {
                    // 既存の文字列のインデックスを取得し、
                    // 共有文字列テーブルに文字列を挿入し、
                    // 新しい項目のインデックスを取得します。
                    int stringIndex = InsertSharedStringItem(book, value);

                    cell.CellValue = new CellValue(stringIndex.ToString());
                    cell.DataType = new EnumValue<CellValues>(CellValues.SharedString);
                }
                else
                {
                    cell.CellValue = new CellValue(value);
                    cell.DataType = new EnumValue<CellValues>(CellValues.Number);
                }

                if (styleIndex > 0)
                    cell.StyleIndex = styleIndex;

                if (isSave)
                    ws.Save();

                updated = true;
            }

            return updated;
        }

        public void SaveWorkbook()
        {
            this.document.WorkbookPart.Workbook.Save();
        }

        /// <summary>
        /// Copy / Delete in multiple sheets
        /// </summary>
        /// <param name="filename"></param>
        /// <param name="sheetName"></param>
        /// <param name="deleteSheetName"></param>
        public static void ChangeLayOut(string filename, List<SheetMapping> copySheetName)
        {
            //Open workbook
            using (SpreadsheetDocument mySpreadsheet = SpreadsheetDocument.Open(filename, true))
            {
                WorkbookPart workbookPart = mySpreadsheet.WorkbookPart;
                //Copy sheets
                foreach (var sheet in copySheetName)
                {
                    //Get the source sheet to be copied
                    //sheet.Key is source sheet name
                    WorksheetPart sourceSheetPart = GetWorkSheetPart(workbookPart, sheet.srcSheet);

                    //Take advantage of AddPart for deep cloning
                    SpreadsheetDocument tempSheet = SpreadsheetDocument.Create(new MemoryStream(), mySpreadsheet.DocumentType);

                    WorkbookPart tempWorkbookPart = tempSheet.AddWorkbookPart();

                    WorksheetPart tempWorksheetPart = tempWorkbookPart.AddPart<WorksheetPart>(sourceSheetPart);

                    //Add cloned sheet and all associated parts to workbook

                    WorksheetPart clonedSheet = workbookPart.AddPart<WorksheetPart>(tempWorksheetPart);

                    //Table definition parts are somewhat special and need unique ids...so let's make an id based on count

                    int numTableDefParts = sourceSheetPart.GetPartsCountOfType<TableDefinitionPart>();

                    //tableId = numTableDefParts;

                    //Clean up table definition parts (tables need unique ids)

                    if (numTableDefParts != 0)

                        FixupTableParts(clonedSheet, numTableDefParts);

                    //There should only be one sheet that has focus

                    //CleanView(clonedSheet);
                    //Add new sheet to main workbook part

                    Sheets sheets = workbookPart.Workbook.GetFirstChild<Sheets>();

                    Sheet copiedSheet = new Sheet();

                    //sheet.Value is new sheet name
                    copiedSheet.Name = sheet.destSheet;

                    copiedSheet.Id = workbookPart.GetIdOfPart(clonedSheet);

                    copiedSheet.SheetId = (uint)sheets.Elements<Sheet>().Select(s => s.SheetId.Value).Max() + 1;

                    sheets.Append(copiedSheet);
                }
                if (copySheetName.Count > 0)
                {
                    //Save Changes
                    workbookPart.Workbook.Save();
                }
            }

        }

        /// <summary>
        /// Get all sheets
        /// </summary>
        /// <param name="filename"></param>
        /// <param name="sheetName"></param>
        /// <param name="deleteSheetName"></param>
        public List<Sheet> GetAllSheets()
        {
            List<Sheet> sheets = this.document.WorkbookPart.Workbook.Descendants<Sheet>().ToList();

            return sheets;
        }

        /// <summary>
        /// 指定されたセル番地に値のセットを行います。
        /// </summary>
        /// <param name="sheetName">シート名</param>
        /// <param name="addressName">対象のセル番地</param>
        /// <param name="value">セットする値</param>
        /// <param name="styleIndex">指定された値を対象のセルのCellオブジェクトのStyleIndexプロパティに設定します。</param>
        /// <param name="isString">セットする値が文字列の場合はtrueを指定し、それ以外はfalseを指定する。</param>
        /// <param name="numberingFormat">0.00</param>
        /// <returns>ture/false</returns>
        public bool UpdateValue(string sheetName, string addressName, string value, UInt32Value styleIndex, bool isString, bool isSave = true, string numberingFormat = null, string align = null)
        {
            bool updated = false;

            WorkbookPart book = document.WorkbookPart;
            Sheet sheet = book.Workbook.Descendants<Sheet>().Where((s) => s.Name == sheetName).FirstOrDefault();

            if (sheet != null)
            {
                Worksheet ws = ((WorksheetPart)(book.GetPartById(sheet.Id))).Worksheet;
                Cell cell = InsertCellInWorksheet(ws, addressName);

                if (isString)
                {
                    // 既存の文字列のインデックスを取得し、
                    // 共有文字列テーブルに文字列を挿入し、
                    // 新しい項目のインデックスを取得します。
                    int stringIndex = InsertSharedStringItem(book, value);

                    cell.CellValue = new CellValue(stringIndex.ToString());
                    cell.DataType = new EnumValue<CellValues>(CellValues.SharedString);
                }
                else
                {
                    cell.CellValue = new CellValue(value);
                    cell.DataType = new EnumValue<CellValues>(CellValues.Number);
                }

                if (styleIndex > 0)
                    cell.StyleIndex = styleIndex;

                if (numberingFormat != null || align != null)
                {
                    CellFormat cellFormat = cell.StyleIndex != null ? GetCellFormat(cell.StyleIndex).CloneNode(true) as CellFormat : new CellFormat();
                    cell.StyleIndex = AddCellFormat(cellFormat, numberingFormat, align);
                }

                if (isSave)
                    ws.Save();

                updated = true;
            }

            return updated;
        }

        /// <summary>
        /// Add new numbering format to stylesheet
        /// </summary>
        /// <param name="cellFormat"></param>
        /// <param name="format"></param>
        /// <returns></returns>
        private UInt32 AddCellFormat(CellFormat cellFormat, string strNumberingFormat, string strAlignFormat)
        {
            Stylesheet styleSheet = document.WorkbookPart.WorkbookStylesPart.Stylesheet;

            if (strNumberingFormat != null)
            {
                // Get new FormatID
                UInt32 maxFormatID = styleSheet.NumberingFormats.Elements<NumberingFormat>().Select(x => x.NumberFormatId.Value).Max();
                maxFormatID++;
                // Create new numbering format
                NumberingFormat numberingFormat = new NumberingFormat();
                numberingFormat.NumberFormatId = maxFormatID;
                numberingFormat.FormatCode = StringValue.FromString(strNumberingFormat);
                styleSheet.NumberingFormats.Append(numberingFormat);
                // Add new format to cell format
                cellFormat.NumberFormatId = maxFormatID;
            }
            if (strAlignFormat != null)
            {
                Alignment al = new Alignment();
                if (cellFormat.Elements<Alignment>().Count() > 0)
                {
                    al = (Alignment)cellFormat.Elements<Alignment>().First().CloneNode(true);
                    cellFormat.RemoveChild<Alignment>(cellFormat.Elements<Alignment>().First());
                }
                HorizontalAlignmentValues horizontalVal;
                if (Enum.TryParse(strAlignFormat, out horizontalVal))
                {
                    al.Horizontal = horizontalVal;
                }
                else
                {
                    al.Horizontal = HorizontalAlignmentValues.General;
                }
                cellFormat.Append(al);
            }
            styleSheet.CellFormats.Append(cellFormat);
            // Save style sheet
            styleSheet.Save();
            // Return style index
            return (UInt32)(styleSheet.CellFormats.Elements<CellFormat>().Count() - 1);
        }
    }


}
