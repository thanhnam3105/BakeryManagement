package jp.co.blueflag.shisaquick.srv.base;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.TimeZone;

import jp.co.blueflag.shisaquick.srv.base.ExcelSetting.enBindDirection;
import jp.co.blueflag.shisaquick.srv.base.ExcelSetting.enBindKind;
import jp.co.blueflag.shisaquick.srv.common.ExceptionManager;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;
import jp.co.blueflag.shisaquick.srv.common.ExceptionManager.ExceptionKind;
import jxl.BooleanCell;
import jxl.Cell;
import jxl.CellType;
import jxl.DateCell;
import jxl.NumberCell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;

/**
 * JExcelオブジェクト
 *  ; テンプレートExcelファイルを元に、編集用のExcelファイルを生成し、Excelファイルを管理する。
 * @author isono
 * @since  2009/04/14
 */
public class JExcelObject extends ObjectBase{

	//Excelテンプレートワークブックオブジェクト	
	protected jxl.Workbook workbook = null;
	//出力Excelワークブックオブジェクト
	protected jxl.write.WritableWorkbook writebook = null;

	
	private int counter = 0;
	//設定情報
	private ExcelSetting excelSetting = null;	
	//帳票名	（Log出力時に使用する、帳票名）
	private String PrintName = "";
	//カレントの設定情報
	private ExcelSetting.SettingRow CurrentSettingRow = null;
	
	/**
	 * コンストラクタ
	 * @param strPrintName : 帳票名	（Log出力時に使用する、帳票名）
	 */
	public JExcelObject(String strPrintName) {
		super();
		//②帳票名をメンバPrintNameに退避する。
		PrintName = strPrintName;

	}
	/**
	 * エクセルブックを生成する
	 *  : テンプレートを読込み新しいブックを生成する
	 * @param TemplateNm : テンプレートのファイル名(FULLパス)
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	public void CreateNewBook(String TemplateNm) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		try {

			//①	テンプレートExcelファイルの読込み	
			//テンプレートのファイル名を基にBookｵﾌﾞｼﾞｪｸﾄを生成する	
			//Bookオブジェクトをメンバworkbookに格納する
			ReadTemplate(TemplateNm);
			
			//②	メンバworkbookより「Setting」シートを抽出する	
			//メンバ「excelSetting」に格納する
			ReadSettingSheet();
			
			/*************** 2012/04/09 ************
			 * 下記の理由よりSettingシートは非表示として削除しない。
			 * ・WritableSheetでないとsheetを削除できない。
			 * ・WritableWorkbookでSheetを削除するとexcelが壊れる。
			 */
//			//③	メンバworkbookより「Setting」シートを削除する
//			RemovSetting();
			
		} catch (Exception e) {
			this.em.ThrowException(e, "テンプレートを読込みに失敗しました。");
			this.close();

		} finally {
		}
	}
	/**
	 * エクセルオブジェクトより指定のファイル名でファイルを生成し
	 * テンプレートをコピーしてSettingのシートを削除する。
	 * @param OutFileNm : ファイル名(FULLパス)
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public void CreateExcelFile(String OutFileNm) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try {
			// System.gc()「ガベージコレクション」の実行をOFFに設定 
			WorkbookSettings settings = new WorkbookSettings(); 
			settings.setGCDisabled(true);
			//作成&テンプレートコピー
			writebook = jxl.Workbook.createWorkbook(new File(OutFileNm), workbook,settings);
			
        //FileNotFoundExceptionが発生した場合
		} catch(FileNotFoundException e) {
			this.em.ThrowException(ExceptionKind.一般Exception
        			, "E000402"
        			, ""
        			, ""
        			, "");
						
		} catch (Exception e) {
			this.em.ThrowException(e, "Excelファイルの出力に失敗しました。");
			this.close();

		} finally {
		}
		
	}
	
	/**
	 * 値をリンク項目名に該当するCellに代入する。
	 * @param LinkNm : リンク項目名
	 * @param Value : 代入する値
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public void SetValue(String LinkNm, String Value) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		counter ++;
		try {
			//代入対象の設定情報を取得
			SearchSttingRow(LinkNm);
			//リストの場合、最大行/列を超えたかチェックする（ExceptionWaning : 最大行/列を超えた場合）
			ChekMaxRow();
			//文字のセル作成
			jxl.write.Label label = new jxl.write.Label(CurrentSettingRow.getLine(),CurrentSettingRow.getRow(),Value);
			//対象のセルを取得
			//シートの取得
			jxl.write.WritableSheet sheet = writebook.getSheet(CurrentSettingRow.getSheet());
			jxl.Cell cell = sheet.getWritableCell(CurrentSettingRow.getLine(), CurrentSettingRow.getRow());// <- 書込み先が空の場合はBlankが返る
			if(cell != null){
				// 元のセルのフォーマット等を新しいセルに設定する
				if (cell.getCellFeatures() != null) {
					label.setCellFeatures(new jxl.write.WritableCellFeatures(cell.getCellFeatures()));
				}
				if (cell.getCellFormat() != null) {
					label.setCellFormat(cell.getCellFormat());
				}
				
			}
			
			// セルを上書き
			sheet.addCell(label);
			//代入後の処理（代入方法がリストの場合次のセルに座標を移動する）
			MoveNextCell();
			
		} catch (Exception e) {
			this.em.ThrowException(e, "値の代入に失敗しました。");

		} finally {
		}
		
	}
	/**
	 * 値をリンク項目名に該当するCellに設定する
	 * @param LinkNm : リンク項目名
	 * @param Value : 代入する値
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public void SetValue(String LinkNm, double Value) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		counter ++;

		try {
			//代入対象の設定情報を取得
			SearchSttingRow(LinkNm);
			//リストの場合、最大行/列を超えたかチェックする（ExceptionWaning : 最大行/列を超えた場合）
			ChekMaxRow();
			//文字のセル作成
			jxl.write.Number number = new jxl.write.Number(CurrentSettingRow.getLine(),CurrentSettingRow.getRow(),Value);
			//対象のセルを取得
			//シートの取得
			jxl.write.WritableSheet sheet = writebook.getSheet(CurrentSettingRow.getSheet());
			jxl.Cell cell = sheet.getWritableCell(CurrentSettingRow.getLine(), CurrentSettingRow.getRow());// <- 書込み先が空の場合はBlankが返る
			if(cell != null){
				// 元のセルのフォーマット等を新しいセルに設定する
				if (cell.getCellFeatures() != null) {
					number.setCellFeatures(new jxl.write.WritableCellFeatures(cell.getCellFeatures()));
				}
				if (cell.getCellFormat() != null) {
					number.setCellFormat(cell.getCellFormat());
				}
			}
			// セルを上書き
			sheet.addCell(number);
			//代入後の処理（代入方法がリストの場合次のセルに座標を移動する）
			MoveNextCell();
			
		} catch (Exception e) {
			this.em.ThrowException(e, "値の代入に失敗しました。");

		} finally {
		}
		
	}
	/**
	 * 値をリンク項目名に該当するCellに設定する
	 * @param LinkNm : リンク項目名
	 * @param Value : 代入する値
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning : 最大行/列を超えた場合
	 */
	public void SetValue(String LinkNm, boolean Value) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try {
			//代入対象の設定情報を取得
			SearchSttingRow(LinkNm);
			//リストの場合、最大行/列を超えたかチェックする（ExceptionWaning : 最大行/列を超えた場合）
			ChekMaxRow();
			//文字のセル作成
			jxl.write.Boolean bool = new jxl.write.Boolean(CurrentSettingRow.getLine(),CurrentSettingRow.getRow(),Value);
			//対象のセルを取得
			//シートの取得
			jxl.write.WritableSheet sheet = writebook.getSheet(CurrentSettingRow.getSheet());
			jxl.Cell cell = sheet.getWritableCell(CurrentSettingRow.getLine(), CurrentSettingRow.getRow());// <- 書込み先が空の場合はBlankが返る
			if(cell != null){
				// 元のセルのフォーマット等を新しいセルに設定する
				if (cell.getCellFeatures() != null) {
					bool.setCellFeatures(new jxl.write.WritableCellFeatures(cell.getCellFeatures()));
				}
				if (cell.getCellFormat() != null) {
					bool.setCellFormat(cell.getCellFormat());
				}
			}
			// セルを上書き
			sheet.addCell(bool);
			//代入後の処理（代入方法がリストの場合次のセルに座標を移動する）
			MoveNextCell();
			
		} catch (Exception e) {
			this.em.ThrowException(e, "値の代入に失敗しました。");

		} finally {
		}
		
	}
	/**
	 * 最大行を超えたかチェックする
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning : 最大行/列を超えた場合
	 */
	private void ChekMaxRow() 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		try {
			//リストか判定する
			if(CurrentSettingRow.getExcelBindKind() 
					== ExcelSetting.enBindKind.リスト){
				//リストの場合
				
				//リストの方向を判定する
				if (CurrentSettingRow.getExcelBindDirection() 
						== ExcelSetting.enBindDirection.縦){
					//縦方向の場合
					
					//最大行を超えたか判定
					if (CurrentSettingRow.getMaxRow() 
							< CurrentSettingRow.getRow()){
						//超えた場合
						
						//Exceptionをスローする
						em.ThrowException(ExceptionManager.ExceptionKind.警告Exception,
								"W000402", 
								PrintName, 
								Integer.toString(CurrentSettingRow.getMaxRow()), 
								"");
					
					}else{
						//超えない場合
						
						//何もしない

					}
					
				}else if(CurrentSettingRow.getExcelBindDirection()
						== ExcelSetting.enBindDirection.横){
					//横方向の場合
					
					//最大列を超えたか判定
					if (CurrentSettingRow.getMaxRow() 
							< CurrentSettingRow.getLine()){
						//超えた場合
						
						//Exceptionをスローする
						em.ThrowException(ExceptionManager.ExceptionKind.警告Exception,
								"W000402", 
								PrintName, 
								Integer.toString(CurrentSettingRow.getMaxRow()), 
								"");

					}else{
						//超えない場合
						
						//何もしない

					}
					
				}
				
			}			

		} catch (Exception e) {
			this.em.ThrowException(e, "リストの最大行数チェックに失敗しました。");
			this.close();

		} finally {
		}
		
	}
	/**
	 * 代入方法がリストの場合、次のセルに座標を移動する
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private void MoveNextCell()
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		try {
			//代入方法がリストか判定する
			if (CurrentSettingRow.getExcelBindKind() == ExcelSetting.enBindKind.リスト){
				//リストの場合
				
				//リスト方向を判定する
				if (CurrentSettingRow.getExcelBindDirection() 
						== ExcelSetting.enBindDirection.縦){
					//縦の場合
					CurrentSettingRow.setRow(CurrentSettingRow.getRow() + 1);
					
				}else{
					//横の場合
					CurrentSettingRow.setLine(CurrentSettingRow.getLine() + 1);
					
				}
				
			}
			
		} catch (Exception e) {
			this.em.ThrowException(e, "次のセルに移動が失敗しました。");
			this.close();

		} finally {
		}
	}
	/**
	 * 設定リストより設定情報を特定しメンバCurrentSettingRowに格納
	 * @param LinkNm : リンク項目名
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private void SearchSttingRow(String LinkNm) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try {
			//設定情報をメンバCurrentSettingRowに格納
			CurrentSettingRow = excelSetting.GetSetting(LinkNm);
			
		} catch (Exception e) {
			this.em.ThrowException(e, "設定情報の特定に失敗しました。");
			this.close();

		} finally {
		}
		
	}
	/**
	 * テンプレートエクセルファイルをメンバ「workbook」に読み込む。
	 * @param TemplateNm : ファイル名（FULLパス）
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private void ReadTemplate(String TemplateNm) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		workbook = null;

		try {
			//エクセルテンプレートを読み込む
			workbook = Workbook.getWorkbook(new File(TemplateNm));
		} catch (Exception e) {
			this.em.ThrowException(e, "テンプレートエクセルファイルの読み込みに失敗しました。");
			this.close();

		} finally {
		}
        
    }
	/**
	 * Settingシートより設定をメンバ「excelSetting」に退避する。
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private void ReadSettingSheet() 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		Sheet sheet = null;
		Cell[] rows = null;

		//代入するシート
		String strSheet = ""; 
		//代入する列
		int intLine = -1; 
		//代入する行
		int intRow = -1;  
		//代入方法
		enBindKind enmKind = null; 
		//代入方向
		enBindDirection enmDirection = null; 
		//リスト行数
		int maxRow = -1; 
		//リンク項目名
		String LinkNm = ""; 

		try {

			//excelSettingインスタンス生成
			excelSetting = new ExcelSetting(PrintName);
			
			//Settingシートを抽出する
			sheet = workbook.getSheet("Setting");

			//Settingシートの情報でexcelSettingを生成する
			for(int i = 2; i < 65536; i++){
				//設定行の抽出
				rows = sheet.getRow(i);
				//対象行の確認
				if(rows[1].getContents() == ""){
					break;
				}
				
				//代入するシート
				strSheet = rows[1].getContents(); 
				//代入する列
				intLine = Integer.parseInt(rows[2].getContents()); 
				//代入する行
				intRow =  Integer.parseInt(rows[3].getContents());  
				//代入方法
				if("代入".equals(rows[4].getContents())){
					enmKind = ExcelSetting.enBindKind.代入;
					
				}else if("リスト".equals(rows[4].getContents())){
					enmKind = ExcelSetting.enBindKind.リスト;
					
				}else{
					enmKind = null;
					
				}
				//代入方向
				if("縦".equals(rows[5].getContents())){
					enmDirection = ExcelSetting.enBindDirection.縦;
					
				}else if("横".equals(rows[5].getContents())){
					enmDirection = ExcelSetting.enBindDirection.横;
					
				}else{
					enmDirection = null;
					
				}
				//リスト行数
				if(enmKind == ExcelSetting.enBindKind.リスト){
					
					if (enmDirection == ExcelSetting.enBindDirection.縦){
						maxRow = Integer.parseInt(rows[6].getContents())
						+ intRow -1; 
						
					}else if(enmDirection == ExcelSetting.enBindDirection.横){
						maxRow = Integer.parseInt(rows[6].getContents())
						+ intLine -1; 
						
					}
					
				}else{
					maxRow = 0; 

				}
				//リンク項目名
				LinkNm = rows[7].getContents(); 

				//excelSettingを生成
				excelSetting.AddSetting(
						strSheet, 
						intLine, 
						intRow, 
						enmKind, 
						enmDirection, 
						maxRow, 
						LinkNm);
				
			}
			
		} catch (Exception e) {
			this.em.ThrowException(e, "Settingシートより抽出した設定情報の格納に失敗しました。");
			this.close();

		} finally {
		}

	}
	/**
	 * Excelobjectをファイルに出力する
	 * @param FNameOption : ファイル名オプション（ファイル名_ファイル名オプション_年月日_時分秒）
	 * @return　String : ダウンロードファイル名
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	protected void ExcelWrite() 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try {
			writebook.write();
		} catch (Exception e) {
			em.ThrowException(e, "");
		} finally {
			this.close();
		}
	}
	/**
	 * 使用オブジェクトの開放
	 * @param FNameOption : ファイル名オプション（ファイル名_ファイル名オプション_年月日_時分秒）
	 * @return　String : ダウンロードファイル名
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	protected void close() 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try{
			if(writebook != null){
				writebook.close();
			}
			writebook = null;
			if(workbook != null){
				workbook.close();
			}
		} catch (Exception e) {
			
		} finally {
			writebook = null;
			workbook = null;
				
		}
	}
	public Object getValue(Cell cell) {
		  if (cell == null) {
		    return null;
		  }
		  CellType type = cell.getType();
		  if(CellType.DATE == type || CellType.DATE_FORMULA == type) {
		      Date date = ((DateCell) cell).getDate();
		      // Excelから読み取った値に対して、デフォルトタイムゾーンとGMTとの差分を評価する必要が無い為。
		      date.setTime(date.getTime() - TimeZone.getDefault().getRawOffset());
		      return date;
		  } else if(CellType.NUMBER == type || CellType.NUMBER_FORMULA == type) {
		      return new BigDecimal(((NumberCell) cell).getValue());
		  } else if(CellType.BOOLEAN == type || CellType.BOOLEAN_FORMULA == type) {
		      return Boolean.valueOf(((BooleanCell) cell).getValue());
		  } else if(CellType.LABEL == type || CellType.STRING_FORMULA == type) {
		      String s = cell.getContents();
		    if ("".equals(s)) {
		      s = null;
		    }
		    return s;
		  } else {
		      return cell.getContents();
		  }
		}
	
}