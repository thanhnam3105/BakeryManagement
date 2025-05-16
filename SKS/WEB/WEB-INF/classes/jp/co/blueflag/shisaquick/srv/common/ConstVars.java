package jp.co.blueflag.shisaquick.srv.common;

/**
 * 
 * 定数保持
 *  : 静的定数の記述、保持
 *
 */
 public class ConstVars {

//	//DBの接続文字列　AppConTextへ移動
//	public static final String DB_CONECT = "";
	
//	//デバッグモード(0～3) Xmlリスト（設定）へ移動　CONST_DEBUG_LEVEL
//	public static final int DEBUG_MODE = 3;
 
	//XML　DB　Session
	
	//ＤＢセッション設定XML（試作用）
	public static final String CONST_XML_PATH_DB1 = "conf/hibernate.cfg.xml";
		
	//ＤＢセッション設定XML（製法支援連携）
	public static final String CONST_XML_PATH_DB2 = "conf/hibernate.cfg2.xml";
 
	//ＤＢセッション設定XML（予備）
	public static final String CONST_XML_PATH_DB3 = "conf/hibernate.cfg3.xml";
 
	//XML　Const　List
	
	//コンストXMLの格納位置+ファイル名(メッセージ用)
	public static final String CONST_XML_PATH_MSG = "conf/Const_Msg.xml";
		
	//コンストXMLの格納位置+ファイル名(設定用)
	public static final String CONST_XML_PATH_SETING = "conf/Const_Setting.xml";

	//コンストXMLの格納位置+ファイル名(エクセルテンプレート)
	public static final String CONST_XML_PATH_EXCEL_TEMPLATES = "conf/Const_Excel_Templates.xml";

	//APP　ConText
	
	//インプットチェックのDI APPConTextファイルパス
	public static final String APPCONTEXT_NM_INPUTCHECK = "conf/appContext_InputCheck.xml";

	//データチェックのDI APPConTextファイルパス
	public static final String APPCONTEXT_NM_DATACHECK = "conf/appContext_DataCheck.xml";

	//業務ロジックのDI APPConTextファイルパス
	public static final String APPCONTEXT_NM_LOGIC = "conf/appContext_Logic.xml";

	//管理クラスのDI APPConTextファイルパス
	public static final String APPCONTEXT_NM_MANAGER = "conf/appContext_Manager.xml";

	//データアクセスオブジェクトのDI　AppConTextファイルパス
	public static final String APPCONTEXT_NM_DAO = "conf/appContext_BD.xml";

	//その他　静的設定値
	
	//サーブレットXMLリクエストエンコード
	public static final String SRV_XML_REQ_ENCODE = "UTF-8";

	//サーブレットXMLレスポンスエンコード
	public static final String SRV_XML_RESP_ENCODE = "text/xml; charset=UTF-8";
	
	//log4jのエラーログ出力、設定ファイル名
	public static final String CONST_ROG4J_ERRORLOG_SETING = "conf/log4j_ErrorLog.xml";
	
	//リクエストカウント
	public static long ReqCount = 0;

}
