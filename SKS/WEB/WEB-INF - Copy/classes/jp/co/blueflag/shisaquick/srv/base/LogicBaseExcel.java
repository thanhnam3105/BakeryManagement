package jp.co.blueflag.shisaquick.srv.base;

import java.util.Calendar;

import jp.co.blueflag.shisaquick.srv.common.ConstManager;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;
import jp.co.blueflag.shisaquick.srv.common.ConstManager.Category;

/**
 * ロジックベース（EXCEL出力用）
 * @author isono
 *
 */
public class LogicBaseExcel extends LogicBase {
	
	//EXCEL管理クラス
	protected ExcelObject excelObject = null;
	//帳票名
	private String printName;
	
	/**
	 * コンストラクタ
	 */
	public LogicBaseExcel(){
		super();
		
	}
	/**
	 * エクセルテンプレートを読み込む
	 * @param PrintName : 帳票名（Const_Excel_Templates.xmlのキーワード）
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	protected void ExcelReadTemplate(String PrintName) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try {
			//帳票名の退避
			printName = PrintName;
			//テンプレートファイルのPathを取得
			ClassLoader loader = Thread.currentThread().getContextClassLoader();
			String xmlfile = loader.getResource(
					ConstManager.getConstValue(Category.設定, "EXCEL_TEMPLATES_FOLDER")
					+	"/"
					+	ConstManager.getConstValue(Category.エクセルテンプレート, PrintName)
					).getPath();
			//エクセルオブジェクトのインスタンス生成
			excelObject = new ExcelObject(PrintName);
			//テンプレートの読み込み
			excelObject.CreateNewBook(xmlfile);
			
		} catch (Exception e) {
			em.ThrowException(e, "");

		} finally {

		}
		
	}
	/**
	 * ExcelのCellにデータをセットする
	 * @param LinkNm : エクセルのSettingシートのリンク項目名
	 * @param Value : 値
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	protected void ExcelSetValue(String LinkNm, String Value) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try {
			//Excelにデータをセットする
			excelObject.SetValue(LinkNm, Value);
			
		} catch (Exception e) {
			em.ThrowException(e, "");

		} finally {

		}
		
	}
	/**
	 * ExcelのCellにデータをセットする
	 * @param LinkNm : エクセルのSettingシートのリンク項目名
	 * @param Value : 値
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	protected void ExcelSetValue(String LinkNm, boolean Value) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try {
			//Excelにデータをセットする
			excelObject.SetValue(LinkNm, Value);
			
		} catch (Exception e) {
			em.ThrowException(e, "");

		} finally {

		}
		
	}
	/**
	 * ExcelのCellにデータをセットする
	 * @param LinkNm : エクセルのSettingシートのリンク項目名
	 * @param Value : 値
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	protected void ExcelSetValue(String LinkNm, Double Value) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try {
			//Excelにデータをセットする
			excelObject.SetValue(LinkNm, Value);
			
		} catch (Exception e) {
			em.ThrowException(e, "");

		} finally {

		}
		
	}
	/**
	 * Excelobjectをファイルに出力する
	 * @return　String : ダウンロードファイル名
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	protected String ExcelOutput() 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		String ret = "";
		
		try {
			//Excelobjectをファイルに出力する

			//ダウンロード用フォルダー
			String FPAth =
				  ConstManager.getRootAppPath()
				+ ConstManager.getConstValue(Category.設定, "DOWNLOAD_FOLDER"); 
			FPAth +=	"/"; 
			//ダウンロードファイル名
			ret = MakeFileName();
			//ファイルの生成
			excelObject.CreateExcelFile(FPAth + ret);
			
		} catch (Exception e) {
			em.ThrowException(e, "");

		} finally {

		}
		return ret;
		
	}
	/**
	 * 出力ファイル名を生成する
	 * @return : ファイル名
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private String MakeFileName() 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		String ret = "";
		
		try {
			//ファイル名の生成
			
			//帳票名
			ret +=	printName;
			//ユーザーID
			ret +=	"_";
			ret +=	userInfoData.getId_user();
			ret +=	"_";
			
			//現在の年月日時間を取得
			Calendar cal = Calendar.getInstance();

			//年
			ret += Integer.toString(cal.get(Calendar.YEAR));
			//月
			ret += Integer.toString(cal.get(Calendar.MONTH));
			//日
			ret += Integer.toString(cal.get(Calendar.DATE));
			//時
			ret +=	"_";
			ret += Integer.toString(cal.get(Calendar.HOUR_OF_DAY));
			//分
			ret += Integer.toString(cal.get(Calendar.MINUTE));
			//秒
			ret += Integer.toString(cal.get(Calendar.SECOND));

			ret +=	".xls";

		} catch (Exception e) {
			em.ThrowException(e, "");

		} finally {

		}
		return ret;
		
	}
	
}
