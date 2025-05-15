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
    //created from 【CsvUploadDownloadController(Ver2.0)】 Template
    public class SekkeiHinshutsuGetoExcelController : ApiController
    {
        /// <summary>
        /// 検索条件に一致するデータを抽出しExcelを作成します。（Downloadフォルダに実体ファイル作成後、パスを返却）
        /// </summary>
        /// <param name="param">検索条件</param>
        /// <returns>作成されたExcelファイルのパス</returns>
        public HttpResponseMessage Get([FromUri]ParamSekkeiHinshutsuGetoExcel param)
        {
            // 読込テンプレートファイルパス
            string templatepath = HttpContext.Current.Server.MapPath(Properties.Settings.Default.ExcelTemplateFolder);
            string dirTemlates = templatepath + "\\" + Properties.Resources.SekkeiHinshutsuGetoExcelTemplate + Properties.Resources.ExcelExtension;

            // 保存Excelファイル名
            string excelname = Properties.Resources.SekkeiHinshutsuGetoExceName + string.Format("{0}-{1}-{2}_", param.cd_shain, param.nen, param.no_oi) + DateTime.Now.ToString("yyyyMMddHHmmss") + Properties.Resources.ExcelExtension;

            using (ShohinKaihatsuEntities context = new ShohinKaihatsuEntities())
            {
                context.Configuration.ProxyCreationEnabled = false;

                // テンプレートを読み込み、必要な情報をマッピングしてクライアントへ返却
                ExcelFile excelFile = new ExcelFile(dirTemlates);
                // Excelテンプレートを読み込み、必要な情報をマッピングしてクライアントへ返却
                MemoryStream stream = EditExcelFileByClosedXML(context, excelFile, param);
                return FileUploadDownloadUtility.CreateFileResponse(stream, excelname);
            };
        }

        /// <summary>
        /// Excelファイルの作成（ClosedXML使用）
        /// </summary>
        /// <param name="context">ShohinKaihatsuEntities</param>
        /// <param name="excelFile">ExcelFile</param>
        /// <param name="param">ParamSekkeiHinshutsuGetoExcel</param>
        /// <returns>作成されたExcelファイルのMemoryStream</returns>
        private MemoryStream EditExcelFileByClosedXML(ShohinKaihatsuEntities context, ExcelFile excelFile, ParamSekkeiHinshutsuGetoExcel param)
        {
            decimal cd_shain = decimal.Parse(param.cd_shain);
            decimal nen = decimal.Parse(param.nen);
            decimal no_oi = decimal.Parse(param.no_oi);
            decimal stratNoRow = 5;
            UInt32 styleCell = excelFile.SetAlignmentStyle("全体", "B" + stratNoRow, "Left", "Center", true, false);
            //write data sheet 表紙
            tr_gate_header gateHeader = context.tr_gate_header.Where(w => w.cd_shain == cd_shain && w.nen == nen && w.no_oi == no_oi).FirstOrDefault();
            excelFile.UpdateValue("表紙", "A1", string.Format("{0}-{1}-{2}", param.cd_shain, param.nen, param.no_oi), 0, true, false);
            excelFile.UpdateValue("表紙", "C4", gateHeader.nm_sample_plan, 0, true, false);
            excelFile.UpdateValue("表紙", "I4", gateHeader.dt_shonin_tanto_plan == null ? "" : gateHeader.dt_shonin_tanto_plan.Value.ToString("yyyy/MM/dd"), 0, true, false);
            excelFile.UpdateValue("表紙", "J4", gateHeader.nm_shonin_tanto_plan, 0, true, false);
            excelFile.UpdateValue("表紙", "I5", gateHeader.dt_shonin_reader_plan == null ? "" : gateHeader.dt_shonin_reader_plan.Value.ToString("yyyy/MM/dd"), 0, true, false);
            excelFile.UpdateValue("表紙", "J5", gateHeader.nm_shonin_reader_plan, 0, true, false);
            excelFile.UpdateValue("表紙", "C6", gateHeader.nm_sample_confirm, 0, true, false);
            excelFile.UpdateValue("表紙", "I6", gateHeader.dt_shonin_tanto_confirm == null ? "" : gateHeader.dt_shonin_tanto_confirm.Value.ToString("yyyy/MM/dd"), 0, true, false);
            excelFile.UpdateValue("表紙", "J6", gateHeader.nm_shonin_tanto_confirm, 0, true, false);
            excelFile.UpdateValue("表紙", "I7", gateHeader.dt_shonin_reader_confirm == null ? "" : gateHeader.dt_shonin_reader_confirm.Value.ToString("yyyy/MM/dd"), 0, true, false);
            excelFile.UpdateValue("表紙", "J7", gateHeader.nm_shonin_reader_confirm, 0, true, false);
            excelFile.UpdateValue("表紙", "B9", gateHeader.nm_comment_tanto, 0, true, false);
            excelFile.UpdateValue("表紙", "B19", gateHeader.nm_comment_reader, 0, true, false);
            excelFile.SaveSheet("表紙");
            //write data sheet 全体
            List<sp_shohinkaihatsu_searchGate_400_Result> lstDataGateAll = context.sp_shohinkaihatsu_searchGate_400(cd_shain, nen, no_oi, param.lstGate[0].no_gate, param.lstGate[0].no_bunrui).ToList();
            for (int i = 0; i < lstDataGateAll.Count; i++)
            {
                excelFile.UpdateValue("全体", "A" + (stratNoRow + i), (i + 1).ToString(), 0, true, false);
                excelFile.UpdateValue("全体", "B" + (stratNoRow + i), lstDataGateAll[i].nm_bunrui, styleCell, true, false);
                excelFile.UpdateValue("全体", "C" + (stratNoRow + i), lstDataGateAll[i].nm_check_bunrui, styleCell, true, false);
                excelFile.UpdateValue("全体", "D" + (stratNoRow + i), lstDataGateAll[i].nm_check, styleCell, true, false);
                excelFile.UpdateValue("全体", "E" + (stratNoRow + i), lstDataGateAll[i].nm_check_note1, styleCell, true, false);
                excelFile.UpdateValue("全体", "F" + (stratNoRow + i), lstDataGateAll[i].flg_ma_attachment == 1 ? "あり" : "", 0, true, false);
                excelFile.UpdateValue("全体", "G" + (stratNoRow + i), lstDataGateAll[i].flg_check_plan ? "○" : "", 0, true, false);
                excelFile.UpdateValue("全体", "H" + (stratNoRow + i), lstDataGateAll[i].flg_check_review ? "○" : "", 0, true, false);
                excelFile.UpdateValue("全体", "I" + (stratNoRow + i), lstDataGateAll[i].nm_comment_plan, styleCell, true, false);
                excelFile.UpdateValue("全体", "J" + (stratNoRow + i), lstDataGateAll[i].nm_comment_confirm, styleCell, true, false);
                excelFile.UpdateValue("全体", "K" + (stratNoRow + i), lstDataGateAll[i].flg_tr_attachment == 1 ? "あり" : "", 0, true, false);
                excelFile.UpdateValue("全体", "L" + (stratNoRow + i), lstDataGateAll[i].nm_free, styleCell, true, false);
                // 行の高さの設定
                excelFile.SetRowHeight("全体", (UInt32)(stratNoRow + i), 91.5, false);
            }
            excelFile.SaveSheet("全体");
            //write data sheet 菌制御
            List<sp_shohinkaihatsu_searchGate_400_Result> lstDataGateBacterialControl = context.sp_shohinkaihatsu_searchGate_400(cd_shain, nen, no_oi, param.lstGate[1].no_gate, param.lstGate[1].no_bunrui).ToList();
            for (int i = 0; i < lstDataGateBacterialControl.Count; i++)
            {
                excelFile.UpdateValue("菌制御", "A" + (stratNoRow + i), (i + 1).ToString(), 0, true, false);
                excelFile.UpdateValue("菌制御", "B" + (stratNoRow + i), lstDataGateBacterialControl[i].nm_bunrui, styleCell, true, false);
                excelFile.UpdateValue("菌制御", "C" + (stratNoRow + i), lstDataGateBacterialControl[i].nm_check_bunrui, styleCell, true, false);
                excelFile.UpdateValue("菌制御", "D" + (stratNoRow + i), lstDataGateBacterialControl[i].nm_check, styleCell, true, false);
                excelFile.UpdateValue("菌制御", "E" + (stratNoRow + i), lstDataGateBacterialControl[i].nm_check_note1, styleCell, true, false);
                excelFile.UpdateValue("菌制御", "F" + (stratNoRow + i), lstDataGateBacterialControl[i].nm_check_note2, styleCell, true, false);
                excelFile.UpdateValue("菌制御", "G" + (stratNoRow + i), lstDataGateBacterialControl[i].flg_ma_attachment == 1 ? "あり" : "", styleCell, true, false);
                excelFile.UpdateValue("菌制御", "H" + (stratNoRow + i), lstDataGateBacterialControl[i].flg_check_plan ? "○" : "", 0, true, false);
                excelFile.UpdateValue("菌制御", "I" + (stratNoRow + i), lstDataGateBacterialControl[i].flg_check_review ? "○" : "", 0, true, false);
                excelFile.UpdateValue("菌制御", "J" + (stratNoRow + i), lstDataGateBacterialControl[i].nm_comment_plan, styleCell, true, false);
                excelFile.UpdateValue("菌制御", "K" + (stratNoRow + i), lstDataGateBacterialControl[i].nm_comment_confirm, styleCell, true, false);
                excelFile.UpdateValue("菌制御", "L" + (stratNoRow + i), lstDataGateBacterialControl[i].flg_tr_attachment == 1 ? "あり" : "", styleCell, true, false);
                excelFile.UpdateValue("菌制御", "M" + (stratNoRow + i), lstDataGateBacterialControl[i].nm_free, styleCell, true, false);
                // 行の高さの設定
                excelFile.SetRowHeight("菌制御", (UInt32)(stratNoRow + i), 91.5, false);
            }
            excelFile.SaveSheet("菌制御");
            //write data sheet 事前確認
            List<sp_shohinkaihatsu_searchGate_400_Result> lstDataGatePriorConfirmation = context.sp_shohinkaihatsu_searchGate_400(cd_shain, nen, no_oi, param.lstGate[2].no_gate, param.lstGate[2].no_bunrui).ToList();
            for (int i = 0; i < lstDataGatePriorConfirmation.Count; i++)
            {
                excelFile.UpdateValue("事前確認", "A" + (stratNoRow + i), (i + 1).ToString(), 0, true, false);
                excelFile.UpdateValue("事前確認", "B" + (stratNoRow + i), lstDataGatePriorConfirmation[i].nm_bunrui, styleCell, true, false);
                excelFile.UpdateValue("事前確認", "C" + (stratNoRow + i), lstDataGatePriorConfirmation[i].nm_check_bunrui, styleCell, true, false);
                excelFile.UpdateValue("事前確認", "D" + (stratNoRow + i), lstDataGatePriorConfirmation[i].nm_check, styleCell, true, false);
                excelFile.UpdateValue("事前確認", "E" + (stratNoRow + i), lstDataGatePriorConfirmation[i].nm_check_note1, styleCell, true, false);
                excelFile.UpdateValue("事前確認", "F" + (stratNoRow + i), lstDataGatePriorConfirmation[i].nm_check_note2, styleCell, true, false);
                excelFile.UpdateValue("事前確認", "G" + (stratNoRow + i), lstDataGatePriorConfirmation[i].flg_ma_attachment == 1 ? "あり" : "", 0, true, false);
                excelFile.UpdateValue("事前確認", "H" + (stratNoRow + i), lstDataGatePriorConfirmation[i].flg_check_plan ? "○" : "", 0, true, false);
                excelFile.UpdateValue("事前確認", "I" + (stratNoRow + i), lstDataGatePriorConfirmation[i].flg_check_review ? "○" : "", 0, true, false);
                excelFile.UpdateValue("事前確認", "J" + (stratNoRow + i), lstDataGatePriorConfirmation[i].nm_comment_plan, styleCell, true, false);
                excelFile.UpdateValue("事前確認", "K" + (stratNoRow + i), lstDataGatePriorConfirmation[i].nm_comment_confirm, styleCell, true, false);
                excelFile.UpdateValue("事前確認", "L" + (stratNoRow + i), lstDataGatePriorConfirmation[i].flg_tr_attachment == 1 ? "あり" : "", 0, true, false);
                excelFile.UpdateValue("事前確認", "M" + (stratNoRow + i), lstDataGatePriorConfirmation[i].nm_free, styleCell, true, false);
                // 行の高さの設定
                excelFile.SetRowHeight("事前確認", (UInt32)(stratNoRow + i), 91.5, false);
            }
            excelFile.SaveSheet("事前確認");
            //write data sheet 妥当性
            List<sp_shohinkaihatsu_searchGate_400_Result> lstDataGateValidity = context.sp_shohinkaihatsu_searchGate_400(cd_shain, nen, no_oi, param.lstGate[3].no_gate, param.lstGate[3].no_bunrui).ToList();
            for (int i = 0; i < lstDataGateValidity.Count; i++)
            {
                excelFile.UpdateValue("妥当性", "A" + (stratNoRow + i), (i + 1).ToString(), 0, true, false);
                excelFile.UpdateValue("妥当性", "B" + (stratNoRow + i), lstDataGateValidity[i].nm_bunrui, styleCell, true, false);
                excelFile.UpdateValue("妥当性", "C" + (stratNoRow + i), lstDataGateValidity[i].nm_check_bunrui, styleCell, true, false);
                excelFile.UpdateValue("妥当性", "D" + (stratNoRow + i), lstDataGateValidity[i].nm_check, styleCell, true, false);
                excelFile.UpdateValue("妥当性", "E" + (stratNoRow + i), lstDataGateValidity[i].nm_check_note1, styleCell, true, false);
                excelFile.UpdateValue("妥当性", "F" + (stratNoRow + i), lstDataGateValidity[i].flg_ma_attachment == 1 ? "あり" : "", 0, true, false);
                excelFile.UpdateValue("妥当性", "G" + (stratNoRow + i), lstDataGateValidity[i].flg_check_plan ? "○" : "", 0, true, false);
                excelFile.UpdateValue("妥当性", "H" + (stratNoRow + i), lstDataGateValidity[i].flg_check_review ? "○" : "", 0, true, false);
                excelFile.UpdateValue("妥当性", "I" + (stratNoRow + i), lstDataGateValidity[i].nm_comment_plan, styleCell, true, false);
                excelFile.UpdateValue("妥当性", "J" + (stratNoRow + i), lstDataGateValidity[i].nm_comment_confirm, styleCell, true, false);
                excelFile.UpdateValue("妥当性", "K" + (stratNoRow + i), lstDataGateValidity[i].flg_tr_attachment == 1 ? "あり" : "", 0, true, false);
                excelFile.UpdateValue("妥当性", "L" + (stratNoRow + i), lstDataGateValidity[i].nm_free, styleCell, true, false);
                // 行の高さの設定
                excelFile.SetRowHeight("妥当性", (UInt32)(stratNoRow + i), 91.5, false);
            }
            excelFile.SaveSheet("妥当性");
            excelFile.SaveWorkbook();
            return (MemoryStream)excelFile.Stream;
        }
    }

    #region "ControllerのAPIで利用するリクエスト・レスポンスクラスです"

    public class ParamSekkeiHinshutsuGetoExcel
    {
        public string cd_shain { get; set; }
        public string nen { get; set; }
        public string no_oi { get; set; }
        public List<Gate> lstGate { get; set; }
    }

    public class Gate
    {
        public int no_gate { get; set; }
        public int? no_bunrui { get; set; }
    }

    #endregion

}
