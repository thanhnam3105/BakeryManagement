package jp.co.blueflag.shisaquick.srv.common;

import org.apache.log4j.*;
import org.apache.log4j.xml.*;

/**
 * 
 * ログ出力クラス
 *  : デバッグ・エラー・システムエラー・トレース等のログ出力を行う。
 *    ログ出力設定に関しては、log4j設定XMLファイル内にて設定。
 *  
 * @author TT.k-katayama
 * @since  2009/03/25
 *
 */
public class LogManager {

	private static Logger logger = null;			//ログ出力クラス
	private static int intDebugLevel = -1;			//デバッグレベル
	private static String strStingXmlNm = "";		//log4j設定XMLファイル名
	
//	/**
//	 * ログ出力クラス コンストラクタ
//	 * @throws ExceptionWaning 
//	 * @throws ExceptionUser 
//	 * @throws ExceptionSystem 
//	 * 
//	 * @author TT.k-katayama
//	 * @since  2009/03/25
//	 */
//	public LogManager() 
//	throws ExceptionSystem, ExceptionUser, ExceptionWaning {
//		
//		//② コンストよりデバッグレベルを取得する
//		String strDebugLv = ConstManager.getConstValue(ConstManager.Category.設定, "CONST_DEBUG_LEVEL");
//		this.intDebugLevel = Integer.parseInt(strDebugLv);
//		
//	}
	
	/**
	 * ログ出力
	 *  : DebugLevel0のログを出力する
	 * @param strLogOutMsg	: ログ出力メッセージ
	 * 
	 * @author TT.k-katayama
	 * @since  2009/03/25
	 */
	public static void systemLog(String strLogOutMsg) {

		GetDebugLevel();
		
		//① log4j Loggerのインスタンスを生成する
		CreateLogger();
		
		//② デバッグログの出力を行う
		logger.debug(strLogOutMsg);
	}

	/**
	 * ログ出力
	 *  : DebugLevel1のログを出力する
	 * @param strLogOutMsg	: ログ出力メッセージ
	 * 
	 * @author TT.k-katayama
	 * @since  2009/03/25
	 */
	public static void userErrorLog(String strLogOutMsg) {

		GetDebugLevel();
		
		//① メンバintDebugLevelを判定する
		if ( intDebugLevel >= 1 ) {
			// メンバintDebugLevelが1以上の場合、処理続行
			
			//② log4j Loggerのインスタンスを生成する
			CreateLogger();
			
			//③ デバッグログの出力を行う
			logger.debug(strLogOutMsg);
		}
	}

	/**
	 * ログ出力
	 *  : DebugLevel2のログを出力する
	 * @param strLogOutMsg	: ログ出力メッセージ
	 * 
	 * @author TT.k-katayama
	 * @since  2009/03/25
	 */
	public static void warningErrorLog(String strLogOutMsg) {

		GetDebugLevel();
		
		//① メンバintDebugLevelを判定する
		if ( intDebugLevel >= 2 ) {
			// メンバintDebugLevelが2以上の場合、処理続行
			
			//② log4j Loggerのインスタンスを生成する
			CreateLogger();
			
			//③ デバッグログの出力を行う
			logger.debug(strLogOutMsg);
			
		}
	}

	/**
	 * ログ出力
	 *  : DebugLevel3のログを出力する
	 * @param strLogOutMsg	: ログ出力メッセージ
	 * 
	 * @author TT.k-katayama
	 * @since  2009/03/25
	 */
	public static void infoLog(String strLogOutMsg) {

		GetDebugLevel();
		
		//① メンバintDebugLevelを判定する
		if ( intDebugLevel >= 3 ) {
			// メンバintDebugLevelが3以上の場合、処理続行
			
			//② log4j Loggerのインスタンスを生成する
			CreateLogger();
			
			//③ デバッグログの出力を行う
			logger.debug(strLogOutMsg);
		}
	}
	
	/**
	 * log4j Loggerのインスタンスを生成する
	 * 
	 * @author TT.k-katayama
	 * @since  2009/03/26
	 */
	private static void CreateLogger() {

		// ログ出力クラスを生成
		logger = Logger.getLogger("");
		logger.setLevel(Level.DEBUG);

		// メンバstrStingXmlNmに設定されたlog4j設定ファイルを読み込む
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		DOMConfigurator.configure(loader.getResource(strStingXmlNm));
	}

	/**
	 * log4j設定XMLファイル名 ゲッター
	 * @return strStingXmlNm : log4j設定XMLファイル名の値を返す
	 */
	public static String getStrStingXmlNm() {
		return strStingXmlNm;
	}
	/**
	 * log4j設定XMLファイル名 セッター
	 * @param _strStingXmlNm : log4j設定XMLファイル名の値を格納する
	 */
	public static void setStrStingXmlNm(String _strStingXmlNm) {
		strStingXmlNm = _strStingXmlNm;
	}
	
	private static void GetDebugLevel() {

		//② コンストよりデバッグレベルを取得する
		String strDebugLv;
		
		try {
			if (intDebugLevel < 0 ){
				strDebugLv = ConstManager.getConstValue(ConstManager.Category.設定, "CONST_DEBUG_LEVEL");
				intDebugLevel = Integer.parseInt(strDebugLv);

			}

		} catch (Exception e) {
			intDebugLevel = 3;

		}

	}
	
}
