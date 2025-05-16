package jp.co.blueflag.shisaquick.srv.base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import jp.co.blueflag.shisaquick.srv.base.ExcelSetting.enBindDirection;
import jp.co.blueflag.shisaquick.srv.base.ExcelSetting.enBindKind;
import jp.co.blueflag.shisaquick.srv.common.ExceptionManager;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;
import jp.co.blueflag.shisaquick.srv.common.ExceptionManager.ExceptionKind;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

/**
 * Excelオブジェクト
 *  ; テンプレートExcelファイルを元に、編集用のExcelファイルを生成し、Excelファイルを管理する。
 * @author isono
 * @since  2009/04/14
 */
public class ExcelObject extends ObjectBase{

	//Excelワークブックオブジェクト
	private HSSFWorkbook workbook = null;
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
	public ExcelObject(String strPrintName) {
		super();
		//②帳票名をメンバPrintNameに退避する。
		PrintName = strPrintName;

	}
	/**
	 * エクセルブックを生成する
	 *  : テンプレートを読込み新しいブックを生成する
	 * @param TemplateNm : テンプレートのファイル名(FULLパス)
	 * @param del : シート削除 true:削除　false:非表示
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	//MOD 2013/06/18 ogawa start        Settingシートの削除を非表示に変更
	//修正前のソース
	//public void CreateNewBook(String TemplateNm)
	//修正後のソース
	public void CreateNewBook(String TemplateNm, boolean del)
	//MOD 2013/06/18 ogawa  end
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		try {

			//①	テンプレートExcelファイルの読込み
			//テンプレートのファイル名を基にBookｵﾌﾞｼﾞｪｸﾄを生成する
			//Bookオブジェクトをメンバworkbookに格納する
			ReadTemplate(TemplateNm);

			//②	メンバworkbookより「Setting」シートを抽出する
			//メンバ「excelSetting」に格納する
			ReadSettingSheet();

			//③	メンバworkbookより「Setting」シートを削除する
			//MOD 2013/06/18 ogawa start        Settingシートの削除を非表示に変更
			//修正前のソース
			//RemovSetting();
			//修正後のソース
			RemovSetting(del);
			//MOD 2013/06/18 ogawa  end

		} catch (Exception e) {
			this.em.ThrowException(e, "テンプレートを読込みに失敗しました。");

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

		HSSFCell cell = null;

		try {
			//代入対象の設定情報を取得
			SearchSttingRow(LinkNm);
			//リストの場合、最大行/列を超えたかチェックする（ExceptionWaning : 最大行/列を超えた場合）
			ChekMaxRow();
			//対象のセルを取得
			cell = SearchCell();
			//対象のセルに値を代入する
			cell.setCellValue(Value);
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

		HSSFCell cell = null;

		try {
			//代入対象の設定情報を取得
			SearchSttingRow(LinkNm);
			//リストの場合、最大行/列を超えたかチェックする（ExceptionWaning : 最大行/列を超えた場合）
			ChekMaxRow();
			//対象のセルを取得
			cell = SearchCell();
			//対象のセルに値を代入する
			cell.setCellValue(Value);
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

		HSSFCell cell = null;

		try {
			//代入対象の設定情報を取得
			SearchSttingRow(LinkNm);
			//リストの場合、最大行/列を超えたかチェックする（ExceptionWaning : 最大行/列を超えた場合）
			ChekMaxRow();
			//対象のセルを取得
			cell = SearchCell();
			//対象のセルに値を代入する
			cell.setCellValue(Value);
			//代入後の処理（代入方法がリストの場合次のセルに座標を移動する）
			MoveNextCell();

		} catch (Exception e) {
			this.em.ThrowException(e, "値の代入に失敗しました。");

		} finally {

		}

	}
	/**
	 * エクセルオブジェクトより指定のファイル名でファイルを生成する
	 * @param OutFileNm : ファイル名(FULLパス)
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public void CreateExcelFile(String OutFileNm)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		FileOutputStream out = null;

		try {
			//ファイルストリームを生成する
			out = new FileOutputStream(OutFileNm);

			//Excelブックを出力する
            workbook.write(out);

        //FileNotFoundExceptionが発生した場合
		} catch(FileNotFoundException e) {
			this.em.ThrowException(ExceptionKind.一般Exception
        			, "E000402"
        			, ""
        			, ""
        			, "");

		} catch (Exception e) {
			this.em.ThrowException(e, "Excelファイルの出力に失敗しました。");

		} finally {
            try {
    			//ファイルストリームのクローズ  2009/07/24 nullの場合、例外が発生する為、nullチェックを追加
            	if (out != null){
            		out.close();
            	}
            }catch(Exception e){
    			this.em.ThrowException(e, "");

            }

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

		} finally {

		}

	}
	/**
	 * 代入対象のセルオブジェクトを抽出する
	 * @return HSSFCell : 代入対象のセルオブジェクト
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private HSSFCell SearchCell()
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		HSSFCell ret = null;
		HSSFSheet sheet = null;
		HSSFRow row = null;

		try {
			//セルの特定
			//シートの取得
			sheet = workbook.getSheet(CurrentSettingRow.getSheet());
			//行の取得
			row = sheet.getRow(CurrentSettingRow.getRow());
			if (row == null){
				row = sheet.createRow(CurrentSettingRow.getRow());
			}
			//セルの取得
			ret = row.getCell(CurrentSettingRow.getLine());
			if (ret == null){
				ret = row.createCell(CurrentSettingRow.getLine());
			}

		} catch (Exception e) {
			this.em.ThrowException(e, "代入対象のセルオブジェクトを抽出に失敗しました。");

		} finally {

		}

		return ret;
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

        FileInputStream in = null;
        workbook = null;

		try {
			//エクセルテンプレートを読み込む
			in = new FileInputStream(TemplateNm);
            POIFSFileSystem fs = new POIFSFileSystem(in);
            workbook = new HSSFWorkbook(fs);

		} catch (Exception e) {
			this.em.ThrowException(e, "テンプレートエクセルファイルの読み込みに失敗しました。");

		} finally {
			try{
				//インプットストリームのクローズ
				in.close();

			}catch(Exception e){
				this.em.ThrowException(e, "");

			}

		}

    }
//MOD 2013/06/18 ogawa start        Settingシートの削除を非表示に変更
//修正前のソース
	/**
	 * Settingシートを削除する。
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
//	private void RemovSetting()
//		    throws ExceptionSystem, ExceptionUser, ExceptionWaning{
//
//				try {
//					//Settingシートを削除する
//					workbook.removeSheetAt(workbook.getSheetIndex("Setting"));
//				} catch (Exception e) {
//					this.em.ThrowException(e, "Settingシートの削除に失敗しました。");
//
//				} finally {
//
//				}
//		    }
//修正後のソース
	/**
	 * Settingシートを削除する。
	 * @param del : シート削除 true:削除　false:非表示
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private void RemovSetting(boolean del)
		    throws ExceptionSystem, ExceptionUser, ExceptionWaning{

				try {
					if(del){
					//Settingシートを削除する
						workbook.removeSheetAt(workbook.getSheetIndex("Setting"));
					}else{
					//Settingシートを非表示にする
						workbook.setSheetHidden(workbook.getSheetIndex("Setting"), true);
					}
				} catch (Exception e) {
					this.em.ThrowException(e, "Settingシートの削除に失敗しました。");

				} finally {

				}
		    }

	//MOD 2013/06/18 ogawa  end
	/**
	 * Settingシートより設定をメンバ「excelSetting」に退避する。
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private void ReadSettingSheet()
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		HSSFSheet sheet = null;
		HSSFRow row = null;

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
				row = sheet.getRow(i);
				//対象行の確認
				if(row.getCell(1).getRichStringCellValue().getString() == ""){
					break;
				}

				//代入するシート
				strSheet = row.getCell(1).getRichStringCellValue().getString();
				//代入する列
				intLine = (int) row.getCell(2).getNumericCellValue();
				//代入する行
				intRow =  (int) row.getCell(3).getNumericCellValue();
				//代入方法
				if(row.getCell(4).getRichStringCellValue().getString().equals("代入")){
					enmKind = ExcelSetting.enBindKind.代入;

				}else if(row.getCell(4).getRichStringCellValue().getString().equals("リスト")){
					enmKind = ExcelSetting.enBindKind.リスト;

				}else{
					enmKind = null;

				}
				//代入方向
				if(row.getCell(5).getRichStringCellValue().getString().equals("縦")){
					enmDirection = ExcelSetting.enBindDirection.縦;

				}else if(row.getCell(5).getRichStringCellValue().getString().equals("横")){
					enmDirection = ExcelSetting.enBindDirection.横;

				}else{
					enmDirection = null;

				}
				//リスト行数
				if(enmKind == ExcelSetting.enBindKind.リスト){

					if (enmDirection == ExcelSetting.enBindDirection.縦){
						maxRow = (int) row.getCell(6).getNumericCellValue()
						+ intRow -1;

					}else if(enmDirection == ExcelSetting.enBindDirection.横){
						maxRow = (int) row.getCell(6).getNumericCellValue()
						+ intLine -1;

					}

				}else{
					maxRow = 0;

				}
				//リンク項目名
				LinkNm = row.getCell(7).getRichStringCellValue().getString();

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

		} finally {

		}

	}

}