package jp.co.blueflag.shisaquick.srv.common;

/**
 * 
 * 定数管理
 *  : サーバーで使用する、メッセージ等の定数値を管理する。
 * @author TT.furuta
 * @since  2009/03/24
 */
public final class ConstManager extends ConstVars {
	
	//定数XML格納（メッセージ）
	private static XmlConstList objXmlConstList_MSG = null;
	//定数XML格納（設定）
	private static XmlConstList objXmlConstList_SETING = null;
	//定数XML格納（エクセルテンプレート）
	private static XmlConstList objXmlConstList_EXCEL_TEMPLATES = null;
	//ルートURL
	private static String RootURL = "";
	//ルートAPPパス
	private static String RootAppPath = "";
	
	/**
	 * コンストリストのカテゴリ 
	 */
	public static enum Category{
		メッセージ,
		設定,
		エクセルテンプレート
	}
	/**
	 * コンストの取得
	 *  : 指定されたカテゴリ/コード（キーワード）のコンスト値を返却する
	 * @param enmCategory : コンストのカテゴリ
	 * @param strCode : コンストのコード
	 * @return コンスト値
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	public static String getConstValue(Category enmCategory, String strCode) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		String ret ="";
		
		//Enumキーワード：メッセージ
		if (enmCategory == Category.メッセージ) {
			//インスタンスがない場合生成
			if (objXmlConstList_MSG == null){
				objXmlConstList_MSG = new XmlConstList(CONST_XML_PATH_MSG);
			}
			ret = objXmlConstList_MSG.GetConstValue(strCode);

		//Enumキーワード：設定
		} else if (enmCategory == Category.設定){
			//インスタンスがない場合生成
			if (objXmlConstList_SETING == null){
				objXmlConstList_SETING = new XmlConstList(CONST_XML_PATH_SETING);
			}
			ret = objXmlConstList_SETING.GetConstValue(strCode);

		//Enumキーワード：エクセルテンプレート
		} else if (enmCategory == Category.エクセルテンプレート){
			//インスタンスがない場合生成
			if (objXmlConstList_EXCEL_TEMPLATES == null){
				objXmlConstList_EXCEL_TEMPLATES = new XmlConstList(CONST_XML_PATH_EXCEL_TEMPLATES);
			}
			ret = objXmlConstList_EXCEL_TEMPLATES.GetConstValue(strCode);

		}
		
		return ret;
	}
	/**
	 * ルートURL取得
	 * @return : ルートURL
	 */
	public static String getRootURL(){
		
		return RootURL;
		
	}
	/**
	 * ルートURL設定
	 * @param _RootURL : ルートURL
	 */
	public static void setRootURL(String _RootURL){
		
		RootURL = _RootURL;
		
	}
	/**
	 * ルートAPPパス取得
	 * @return : ルートAPPパス
	 */
	public static String getRootAppPath(){
		
		return RootAppPath;
		
	}
	/**
	 * ルートAPPパス設定
	 * @param _RootURL : ルートAPPパス
	 */
	public static void setRootAppPath(String _RootAppPath){
		
		RootAppPath = _RootAppPath;
		
	}
	
}