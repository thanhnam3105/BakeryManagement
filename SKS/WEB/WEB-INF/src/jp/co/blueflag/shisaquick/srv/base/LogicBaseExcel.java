package jp.co.blueflag.shisaquick.srv.base;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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
	 * @param del : シート削除 true:削除　false:非表示
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	//EXCELテンプレートを読み込む
	//MOD 2013/06/18 ogawa start        Settingシートの削除を非表示に変更
	//修正前のソース
	//protected void ExcelReadTemplate(String PrintName)
	//修正後のソース
	protected void ExcelReadTemplate(String PrintName, boolean del)
	//MOD 2013/06/18 ogawa  end
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
			//MOD 2013/06/18 ogawa start        Settingシートの削除を非表示に変更
			//修正前のソース
			//excelObject.CreateNewBook(xmlfile);
			//修正後のソース
			excelObject.CreateNewBook(xmlfile, del);
			//MOD 2013/06/18 ogawa  end

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
			ret = ExcelOutput("");

		} catch (Exception e) {
			em.ThrowException(e, "");

		} finally {

		}
		return ret;

	}
	/**
	 * Excelobjectをファイルに出力する
	 * @param FNameOption : ファイル名オプション（ファイル名_ファイル名オプション_年月日_時分秒）
	 * @return　String : ダウンロードファイル名
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	protected String ExcelOutput(String FNameOption)
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
			ret = MakeFileName(FNameOption);

			//ファイルの生成
			excelObject.CreateExcelFile(FPAth + ret);

		} catch (Exception e) {
			em.ThrowException(e, "");

		} finally {

		}
		return ret;

	}
	/**
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	/**
	 * 出力ファイル名を生成する
	 * @param FNameOption : ファイル名オプション
	 * @return : ファイル名
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private String MakeFileName(String FNameOption)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		String ret = "";

		try {
			//ファイル名の生成

			//帳票名
			ret +=	printName;

			//ファイル名オプション
			if (!FNameOption.equals("")){
				//ファイル名が指定されている場合、ファイル名にファイル名オプションを付加する。
				ret +=	"_";
				ret +=	FNameOption;

			}

			//ユーザーID
			ret +=	"_";
			ret +=	userInfoData.getId_user();
			ret +=	"_";

//			【H24年度対応】2012/06/01 TT H.SHIMA MOD Start

//			//現在の年月日時間を取得
//			Calendar cal = Calendar.getInstance();
//
//			//年
//			ret += Integer.toString(cal.get(Calendar.YEAR));
//			//月
//			ret += Integer.toString(cal.get(Calendar.MONTH) + 1);
//			//日
//			ret += Integer.toString(cal.get(Calendar.DATE));
//			//時
//			ret +=	"_";
//			ret += Integer.toString(cal.get(Calendar.HOUR_OF_DAY));
//			//分
//			ret += Integer.toString(cal.get(Calendar.MINUTE));
//			//秒
//			ret += Integer.toString(cal.get(Calendar.SECOND));

			Date date = new Date();

			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_kkmmss");

			ret += sdf.format(date);

//			【H24年度対応】2012/06/01 TT H.SHIMA MOD End

			ret +=	".xls";

		} catch (Exception e) {
			em.ThrowException(e, "");

		} finally {

		}
		return ret;

	}


	/**
	 * Excelobjectをファイルに出力する
	 * @param FNameOption : ファイル名オプション（ファイル名_ファイル名オプション_年月日_時分秒）
	 * @return　String : ダウンロードファイル名
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	protected String ExcelOutput_genka(String FNameOption , String strNm)
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
			ret = MakeFileName_genka(FNameOption,strNm);

			//ファイルの生成
			excelObject.CreateExcelFile(FPAth + ret);

		} catch (Exception e) {
			em.ThrowException(e, "");

		} finally {

		}
		return ret;

	}
	/**
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	/**
	 * 出力ファイル名を生成する
	 * @param FNameOption : ファイル名オプション
	 * @return : ファイル名
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private String MakeFileName_genka(String FNameOption , String strNm)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		String ret = "";

		try {
			//ファイル名の生成

			//禁止文字の置換
			strNm = chnageErrChar(strNm);

			//帳票名
			ret +=FNameOption + "_" + strNm + "_";

//			【H24年度対応】2012/06/01 TT H.SHIMA MOD Start

//			//現在の年月日時間を取得
//			Calendar cal = Calendar.getInstance();
//
//			//年
//			ret += (Integer.toString(cal.get(Calendar.YEAR))).substring(2, 4);
//			//月
//			ret += Integer.toString(cal.get(Calendar.MONTH) + 1);
//			//日
//			ret += Integer.toString(cal.get(Calendar.DATE));

			Date date = new Date();

			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

			ret += sdf.format(date);

//			【H24年度対応】2012/06/01 TT H.SHIMA MOD End

			ret +=	".xls";

		} catch (Exception e) {
			em.ThrowException(e, "");

		} finally {

		}
		return ret;

	}




	//***** ADD 【H24年度対応】 2012/04/16 hagiwara S **********
	/**
	 * Excelobjectをファイルに出力する
	 * @param FName : ファイル名
	 * @return　String : ダウンロードファイル名
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	protected String ExcelOutput_genka(String FName)
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
			ret = MakeFileName_genka(FName);

			//ファイルの生成
			excelObject.CreateExcelFile(FPAth + ret);

		} catch (Exception e) {
			em.ThrowException(e, "");

		} finally {

		}
		return ret;

	}
	/**
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	/**
	 * 出力ファイル名を生成する
	 * @param FName : ファイル名
	 * @return : ファイル名
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private String MakeFileName_genka(String FName)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		String ret = "";

		try {
			//ファイル名の生成

			//禁止文字の置換
			FName = chnageErrChar(FName);

			//帳票名
			ret += FName + "_";

//			【H24年度対応】2012/06/01 TT H.SHIMA MOD Start

//			//現在の年月日時間を取得
//			Calendar cal = Calendar.getInstance();
//
//			//年
//			ret += (Integer.toString(cal.get(Calendar.YEAR))).substring(2, 4);
//			//月
//			ret += Integer.toString(cal.get(Calendar.MONTH) + 1);
//			//日
//			ret += Integer.toString(cal.get(Calendar.DATE));

			Date date = new Date();

			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

			ret += sdf.format(date);

//			【H24年度対応】2012/06/01 TT H.SHIMA MOD End

			ret +=	".xls";

		} catch (Exception e) {
			em.ThrowException(e, "");

		} finally {

		}
		return ret;

	}
	//***** ADD 【H24年度対応】 2012/04/16 hagiwara E **********



	/**
	 * 禁止文字の置換
	 * @param FNameOption : ファイル名オプション
	 * @return : ファイル名
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private String chnageErrChar(String strNm)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		String ret = strNm;

		try {
			//禁止文字の置換
			// \ / : * ? " > < |
			ret = ret.replaceAll("\\\\","■");
			ret = ret.replaceAll("/","■");
			ret = ret.replaceAll(":","■");
			ret = ret.replaceAll("\\*","■");
			ret = ret.replaceAll("\\?","■");
			ret = ret.replaceAll("\"","■");
			ret = ret.replaceAll(">","■");
			ret = ret.replaceAll("<","■");
			ret = ret.replaceAll("\\|","■");

		} catch (Exception e) {
			em.ThrowException(e, "");

		} finally {

		}
		return ret;

	}

}
