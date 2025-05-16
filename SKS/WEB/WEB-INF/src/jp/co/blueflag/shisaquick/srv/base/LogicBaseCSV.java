package jp.co.blueflag.shisaquick.srv.base;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import jp.co.blueflag.shisaquick.srv.common.ConstManager;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;
import jp.co.blueflag.shisaquick.srv.common.ConstManager.Category;

/**
 * ロジックベース（CSV出力用）
 * @author isono
 *
 */
public class LogicBaseCSV extends LogicBase {
	
	//CSV管理クラス
	protected FileCreateCSV fileCreateCSV = null;
	
	/**
	 * コンストラクタ
	 */
	public LogicBaseCSV(){
		super();
		
	}
	/**
	 * CSVファイルをダウンロードフォルダに出力する。
	 * @param outFileName : ファイル名
	 * @param dataList : 出力するデータ(DBの検索結果)
	 * @return : ダウンロードファイル名
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	protected String CSVOutput(
			String outFileName
			, List<?> dataList) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		String ret = "";
		
		try {
			//CSVをファイルに出力する

			//ダウンロード用フォルダー
			String FPAth =
				  ConstManager.getRootAppPath()
				+ ConstManager.getConstValue(Category.設定, "DOWNLOAD_FOLDER"); 
			FPAth +=	"/"; 

			//ダウンロードファイル名
			ret = MakeFileName(outFileName);

			//ファイルの生成
			fileCreateCSV = new FileCreateCSV();
			fileCreateCSV.csvFileCreater(FPAth + ret, dataList);
			
		} catch (Exception e) {
			em.ThrowException(e, "");

		} finally {

		}
		return ret;
		
	}
	/**
	 * 出力ファイル名を生成する
	 * @param printName : 出力ファイル名
	 * @return : ファイル名（拡張後）
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private String MakeFileName(String printName) 
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
			
//			【H24年度対応】2012/06/01 TT H.SHIMA MOD Start
			
//			//現在の年月日時間を取得
//			Calendar cal = Calendar.getInstance();
//
//			//年
//			ret += Integer.toString(cal.get(Calendar.YEAR));
//			//月
//			ret += Integer.toString(cal.get(Calendar.MONTH));
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
			
			ret +=	".csv";

		} catch (Exception e) {
			em.ThrowException(e, "");

		} finally {

		}
		return ret;
		
	}
	
}
