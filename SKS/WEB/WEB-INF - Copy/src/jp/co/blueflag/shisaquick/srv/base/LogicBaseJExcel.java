package jp.co.blueflag.shisaquick.srv.base;

import java.text.SimpleDateFormat;
import java.util.Date;

import jp.co.blueflag.shisaquick.srv.common.ConstManager;
import jp.co.blueflag.shisaquick.srv.common.ConstManager.Category;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;

/**
 * ロジックベース（EXCEL出力用）
 * @author isono
 *
 */
public class LogicBaseJExcel extends LogicBase {

	//EXCEL管理クラス
	protected JExcelObject jexcelObject = null;
	//帳票名
	private String printName;

	/**
	 * コンストラクタ
	 */
	public LogicBaseJExcel(){
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
			jexcelObject = new JExcelObject(PrintName);
			//テンプレートの読み込み
			jexcelObject.CreateNewBook(xmlfile);

		} catch (Exception e) {
			em.ThrowException(e, "");
			this.close();
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
			jexcelObject.SetValue(LinkNm, Value);

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
			jexcelObject.SetValue(LinkNm, Value);

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
			jexcelObject.SetValue(LinkNm, Value);

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
			this.close();
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
			jexcelObject.CreateExcelFile(FPAth + ret);

		} catch (Exception e) {
			em.ThrowException(e, "");
			this.close();
		} finally {

		}
		return ret;

	}
	/**
	 * Excelobjectをファイルに出力する
	 * @param FNameOption : ファイル名オプション（ファイル名）
	 * @return　String : ダウンロードファイル名
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	protected String ExcelOutputRawName(String FNameOption)
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
			ret = FNameOption;

			//ファイルの生成
			jexcelObject.CreateExcelFile(FPAth + ret);

		} catch (Exception e) {
			em.ThrowException(e, "");
			this.close();
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
			this.close();
		} finally {

		}
		return ret;

	}


	/**
	 * 出力するExcelファイル名を作成しテンプレートのコピーおよびSettingシートを削除する。
	 * このapiの後でセルへのセットが可能となる。
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
			jexcelObject.CreateExcelFile(FPAth + ret);

		} catch (Exception e) {
			em.ThrowException(e, "");
			this.close();
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
	protected void ExcelWrite()
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try {
			//ファイルの出力
			jexcelObject.ExcelWrite();
		} catch (Exception e) {
			em.ThrowException(e, "");
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
	protected void close()
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try {
			//ファイルの出力
			jexcelObject.close();
		} catch (Exception e) {
			em.ThrowException(e, "");
		} finally {
			jexcelObject = null;
		}
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
			//現在の年月日時間を取得
//			Calendar cal = Calendar.getInstance();

			//年
//			ret += (Integer.toString(cal.get(Calendar.YEAR))).substring(2, 4);
			//月
//			ret += Integer.toString(cal.get(Calendar.MONTH) + 1);
			//日
//			ret += Integer.toString(cal.get(Calendar.DATE));

			Date date = new Date();

			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

			ret += sdf.format(date);

//			【H24年度対応】2012/06/01 TT H.SHIMA MOD End

			ret +=	".xls";

		} catch (Exception e) {
			em.ThrowException(e, "");
			this.close();
		} finally {

		}
		return ret;

	}

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
			this.close();
		} finally {

		}
		return ret;

	}
}
