package jp.co.blueflag.shisaquick.srv.common;

import java.util.Calendar;


/**
 * 
 * 例外処理
 *  : 例外処理の判定を行う。
 * 
 * @author TT.k-katayama
 * @since  2009/03/26
 *
 */
public class ExceptionManager {

	//クラス名
	private String strClassName = null;
	//ログ出力用設定ファイル
	private String strLogSetingFileNm = null;
	//ログ管理	
	//private LogManager logManager;
	//システム例外
	private ExceptionSystem exceptionSystem = null;
	//一般例外
	private ExceptionUser exceptionUser = null;
	//警告例外
	private ExceptionWaning exceptionWaning = null;

	/**
	 * 例外処理の種類
	 *   一般Exception
	 *   警告Exception
	 *   システムException
	 *  
	 * @author TT.k-katayama
	 * @since  2009/03/26
	 */
	public enum ExceptionKind {
		一般Exception,
		警告Exception,
		システムException
	}	
	
	/**
	 * 例外処理クラス コンストラクタ
	 * @param strClassName : クラス名
	 *  
	 * @author TT.k-katayama
	 * @since  2009/03/26
	 */
	public ExceptionManager(String _strClassName) {
		//インスタンス変数の初期化
		strClassName = null;
		strLogSetingFileNm = null;
		exceptionSystem = null;
		exceptionUser = null;
		exceptionWaning = null;
		
		//① クラス名をメンバClassNameに格納する
		this.strClassName = _strClassName;
		
		//② log設定xmlファイル名を取得する
		this.strLogSetingFileNm = ConstManager.CONST_ROG4J_ERRORLOG_SETING; 
		
	}
	
	/**
	 * エクセプションスロー(1)
	 * @param ex : エクセプション
	 * @param logMsg : ログ出力
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 * 
	 * @author TT.k-katayama
	 * @since  2009/03/26
	 */
	public void ThrowException(
			Exception ex
			, String logMsg
			)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		ExceptionBase excBase = null; 
		
		//Exceptionの種類を判定しExceptionSystemに格納し、Exceptionのログを出力する。
		excBase = cnvException(ex, logMsg);
		
		//Exceptionをメンバに格納する
		if ( excBase.getClass().equals(ExceptionSystem.class) ) {
			//ExceptionSystem … なにもしない
			exceptionSystem = (ExceptionSystem) excBase;
			
		} else if ( excBase.getClass().equals(ExceptionUser.class) ) {
			//ExceptionUser … なにもしない
			exceptionUser = (ExceptionUser) excBase;
			
		} else if ( excBase.getClass().equals(ExceptionWaning.class) ) {
			//ExceptionWaning … なにもしない
			exceptionWaning = (ExceptionWaning) excBase;
			
		}
		
		//⑤ 例外のスロー ※インスタンスのあるクラスをスロー
		ThrowException();		

	}
	/**
	 * Exceptionの種類を判定しExceptionSystemに格納する。
	 * Exceptionのログを出力する。
	 * @param ex：Exception
	 * @param logMsg：ログ出力メッセージ
	 * @return：ExceptionBase
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public ExceptionBase cnvException(
			Exception ex
			, String logMsg
			)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		ExceptionBase ret = null;

		ExceptionSystem excSystem = null;
		ExceptionUser excUser = null;
		ExceptionWaning excWaning = null;
		
		String strDateTime = "";		//現在の日付(YYY/MM/DD hh:mm:dd)
		String strErrorCD = "";			//システムエラーコード
		String strErrorMsg = "";		//システムエラーメッセージ
		String strLogOutMsg = "";		//ログ出力用メッセージ
		String strUserMsg = "";			//ユーザー用メッセージ
		
		//① Exceptionの種類を判別する
		if ( ex.getClass().equals(ExceptionSystem.class) ) {
			//ExceptionSystem … なにもしない
			excSystem = (ExceptionSystem) ex;
			
			//Log出力項目
			strLogOutMsg = 
				strDateTime 
			+ " [▲ Trace] " 
			+ " クラス名＝" 
			+ this.strClassName 
			+ " 発生クラス＝" 
			+ excSystem.getClassName(); 
			
			// 2) logManagerクラスのインスタンスを生成しLogを出力する
			//this.logManager = new LogManager();
			LogManager.setStrStingXmlNm(this.strLogSetingFileNm);
			LogManager.systemLog(strLogOutMsg);
			
		} else if ( ex.getClass().equals(ExceptionUser.class) ) {
			//ExceptionUser … なにもしない
			excUser = (ExceptionUser) ex;
			
		} else if ( ex.getClass().equals(ExceptionWaning.class) ) {
			//ExceptionWaning … なにもしない
			excWaning = (ExceptionWaning) ex;
			
		} else {
			//SystemException(本FramWork以外でThrowされたException)
			
			//② logを出力する
			// 1) メッセージの編集
			strDateTime = this.getDateTimeNow();
			strErrorCD = ex.getClass().getName();
			strErrorMsg = this.getSystemErrMessage(ex);
			//Log出力項目
			strLogOutMsg = 
				strDateTime 
			+ " [SYSTEM EXCEPTION] 【" 
			+ logMsg
			+ "】 クラス名＝" 
			+ this.strClassName 
			+ " エクセプションの種類＝" 
			+ strErrorCD 
			+ " メッセージ＝" 
			+ strErrorMsg; 
			
			// 2) logManagerクラスのインスタンスを生成しLogを出力する
			//this.logManager = new LogManager();
			LogManager.setStrStingXmlNm(this.strLogSetingFileNm);
			LogManager.systemLog(strLogOutMsg);
			
			//③ ユーザー用メッセージの生成
			try {
				strUserMsg = ConstManager.getConstValue(ConstManager.Category.メッセージ, strErrorCD );
			} catch (ExceptionWaning e) {
				//ExceptionWaningの場合は処理を続行する
			} finally {

			}
			
			//④ ExceptionをExceptionSystemに格納
			excSystem = new ExceptionSystem();
			//ユーザーメッセージ
			excSystem.setUserMsg(strUserMsg);
			//クラス名
			excSystem.setClassName(this.strClassName);
			//メッセージ番号
			excSystem.setMsgNumber("");
			//システムエラーコード
			excSystem.setSystemErrorCD(ex.getClass().getName());
			//システムエラーメッセージ
			excSystem.setSystemErrorMsg(strErrorMsg);
			
			//格納済みのExceptionを破棄する。
			ex = null;
			
		}
		
		//例外を戻り値に設定する。 
		if ( excSystem != null ) {
			ret = excSystem;
		} else if ( excUser != null ) {
			ret = excUser;
		} else if ( excWaning != null ) {
			ret = excWaning;
		}
		return ret;
		
	}
	/**
	 * 例外を上位クラスにスローする
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public void ThrowException() 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{
	
		//⑤ 例外のスロー ※インスタンスのあるクラスをスロー
		if ( this.exceptionSystem != null ) {
			throw this.exceptionSystem;
			
		} else if ( this.exceptionUser != null ) {
			throw this.exceptionUser;
			
		} else if ( this.exceptionWaning != null ) {
			throw this.exceptionWaning;
			
		}

	}
	/**
	 * エクセプションスロー(2)
	 * @param ExKind : 例外種類
	 * @param MsgNumber : メッセージ番号
	 * @param ChangMsg1 : 置き換え文字列①
	 * @param ChangMsg2 : 置き換え文字列②
	 * @param ChangMsg3 : 置き換え文字列③
	 * @param DebugMsg  : ログ出力メッセージ
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 * 
	 * @author TT.k-katayama
	 * @since  2009/03/26
	 */
	public void ThrowException(
			ExceptionKind ExKind
			, String MsgNumber
			, String ChangMsg1
			, String ChangMsg2
			, String ChangMsg3
			, String DebugMsg
		)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		//現在の日付
		String strDateTime = "";
		//ユーザーメッセージ
		String strUserMsg = "";
		//ログに出力するメッセージ		
		String strLogOutMsg = "";
		
		//① 例外の種類を判定し、Exceptionのインスタンスを生成
		if ( ExKind == ExceptionKind.一般Exception ) {
			this.exceptionUser = new ExceptionUser();
			
		} else if ( ExKind == ExceptionKind.警告Exception ) {
			this.exceptionWaning = new ExceptionWaning();
			
		} else if ( ExKind == ExceptionKind.システムException ) {
			this.exceptionSystem = new ExceptionSystem();
			
		}

		//② メッセージの編集
		try {
			strUserMsg = ConstManager.getConstValue(ConstManager.Category.メッセージ, MsgNumber);
			
		} catch (ExceptionWaning e) {
			// ※戻り値がExceptionWaningの場合は、処理続行	

		} finally {	
		
		}
				
		//置き換え文字列でメッセージを加工する
		strUserMsg = strUserMsg.replace("$1", ChangMsg1);
		strUserMsg = strUserMsg.replace("$2", ChangMsg2);
		strUserMsg = strUserMsg.replace("$3", ChangMsg3);
		
		//③ 例外の編集
		if ( ExKind == ExceptionKind.一般Exception ) {
			this.exceptionUser.setUserMsg(strUserMsg);				//ユーザーメッセージ
			this.exceptionUser.setClassName(this.strClassName);		//クラス名
			this.exceptionUser.setMsgNumber(MsgNumber);				//メッセージ番号
			this.exceptionUser.setSystemErrorCD("");				//システムエラーコード
			this.exceptionUser.setSystemErrorMsg("");				//システムエラーメッセージ

		} else if ( ExKind == ExceptionKind.警告Exception ) {
			this.exceptionWaning.setUserMsg(strUserMsg);			//ユーザーメッセージ
			this.exceptionWaning.setClassName(this.strClassName);	//クラス名
			this.exceptionWaning.setMsgNumber(MsgNumber);			//メッセージ番号
			this.exceptionWaning.setSystemErrorCD("");				//システムエラーコード
			this.exceptionWaning.setSystemErrorMsg("");				//システムエラーメッセージ

		} else if ( ExKind == ExceptionKind.システムException ) {
			this.exceptionSystem.setUserMsg(strUserMsg);			//ユーザーメッセージ
			this.exceptionSystem.setClassName(this.strClassName);	//クラス名
			this.exceptionSystem.setMsgNumber(MsgNumber);			//メッセージ番号
			this.exceptionSystem.setSystemErrorCD("");				//システムエラーコード
			this.exceptionSystem.setSystemErrorMsg("");				//システムエラーメッセージ

		}
		
		//④ logの出力
		// 1) メッセージの編集
		// YYYY/MM/DD hh:mm:ss クラス名 メッセージ番号 ユーザーメッセージ ログ出力メッセージ
		strDateTime = this.getDateTimeNow();	
		strLogOutMsg = 
		" 【" 
		+ DebugMsg
		+ "】 クラス名＝" 
		+ this.strClassName 
		+ " メッセージ番号＝" 
		+ MsgNumber 
		+ " メッセージ＝" 
		+ strUserMsg; 

		// 2) logMangerクラスのインスタンスを生成
		//this.logManager = new LogManager();
		LogManager.setStrStingXmlNm(this.strLogSetingFileNm);
		
		// 3) ログを出力する
		// 例外種類の判定
		if ( ExKind == ExceptionKind.一般Exception ) {
			LogManager.userErrorLog(
					strDateTime 
					+ " [USER EXCEPTION] " 
					+ strLogOutMsg);
			
		} else if ( ExKind == ExceptionKind.警告Exception ) {
			LogManager.warningErrorLog(
					strDateTime 
					+ " [WANING EXCEPTION] " 
					+ strLogOutMsg);

		} else if ( ExKind == ExceptionKind.システムException ) {
			LogManager.systemLog(
					strDateTime 
					+ " [SYSTEM EXCEPTION] " 
					+ strLogOutMsg);

		}

		//⑤ 例外のスロー ※インスタンスのあるクラスをスロー
		if ( this.exceptionSystem != null ) {
			throw this.exceptionSystem;

		} else if ( this.exceptionUser != null ) {
			throw this.exceptionUser;

		} else if ( this.exceptionWaning != null ) {
			throw this.exceptionWaning;

		}
	}

	/**
	 * エクセプションスロー(3)
	 * @param ExKind : 例外種類
	 * @param MsgNumber : メッセージ番号
	 * @param ChangMsg1 : 置き換え文字列①
	 * @param ChangMsg2 : 置き換え文字列②
	 * @param ChangMsg3 : 置き換え文字列③
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 * 
	 * @author TT.k-katayama
	 * @since  2009/03/26
	 */
	public void ThrowException(
			ExceptionKind ExKind
			, String MsgNumber
			, String ChangMsg1
			, String ChangMsg2
			, String ChangMsg3
		) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		this.ThrowException(ExKind, MsgNumber, ChangMsg1, ChangMsg2, ChangMsg3, "");

	}
	
	/**
	 * ログ出力
	 * @param strMsg : ログ出力メッセージ
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 * 
	 * @author TT.k-katayama
	 * @since  2009/03/26
	 */
	public void DebugLog(
			String strMsg
			)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		String strDateTime;			//現在の日付
		String strLogOutMsg = "";
		
		//① logを出力する
		
		// 1) メッセージの編集
		// YYY/MM/DD hh:mm:dd クラス名 ログ出力メッセージ 
		strDateTime = this.getDateTimeNow();	
		strLogOutMsg = strDateTime
		+ " [DEBUG LOG] 【" 
		+ strMsg
		+ "】 クラス名＝" 
		+ this.strClassName; 
		
		// 2) logMangerクラスのインスタンスを生成しLogを出力する
		//this.logManager = new LogManager();
		LogManager.setStrStingXmlNm(this.strLogSetingFileNm);
		LogManager.infoLog(strLogOutMsg);
		
	}
	
	/**
	 * 現在の日付・時間を取得
	 * @return [YYYY/MM/DD hh:mm:ss]を取得
	 * 
	 * @author TT.k-katayama
	 * @since  2009/03/26
	 */
	private String getDateTimeNow() {
		String strDateTime = "";

		//現在の年月日時間を取得
		Calendar cal = Calendar.getInstance();
		int y = cal.get(Calendar.YEAR);			//現在の年を取得
	    int mon = cal.get(Calendar.MONTH) + 1;	//現在の月を取得
	    int d = cal.get(Calendar.DATE);			//現在の日を取得
	    int h = cal.get(Calendar.HOUR_OF_DAY);	//現在の時を取得
	    int min = cal.get(Calendar.MINUTE);		//現在の分を取得
	    int sec = cal.get(Calendar.SECOND);		//現在の秒を取得
	    
	    //YYYY/MM/DD hh:mm:ss
	    strDateTime = y + "/" + mon + "/" + d + " " + h + ":" + min + ":" + sec;
		
		return strDateTime;
	}
	
	/**
	 * システムエラーメッセージの取得
	 * @param ex : Exception
	 * @return システムエラーメッセージ
	 * 
	 * @author TT.k-katayama
	 * @since  2009/03/26
	 */
	private String getSystemErrMessage(Exception ex) {
		String strErrMessage = "";

		//メッセージ
		strErrMessage += ex.getMessage();
		//メソッド名
		strErrMessage += " " + ex.getStackTrace()[0].getMethodName();
		//ファイル名
		strErrMessage += " " + ex.getStackTrace()[0].getFileName();
		//行番号
		strErrMessage += " " + ex.getStackTrace()[0].getLineNumber();
		
		return strErrMessage;

	}
	
}
