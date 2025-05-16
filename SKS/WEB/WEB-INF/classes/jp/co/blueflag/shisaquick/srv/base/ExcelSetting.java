package jp.co.blueflag.shisaquick.srv.base;

import java.util.ArrayList;

import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;

/**
 * Excel設定情報管理クラス
 * @author isono
 * @since  2009/04/14
 */
public class ExcelSetting extends ObjectBase {

	//Excel設定リスト
	protected ArrayList<SettingRow> ListSetting;
	//帳票名	（Log出力時に使用する、帳票名）
	String PrintName = "";
	
	/**
	 * Excel設定情報（代入方法）
	 * @author isono
	 * @since  2009/04/14
	 */
	public static enum enBindKind{
		代入,
		リスト
	}
	/**
	 * Excel設定情報（代入方向）
	 * @author isono
	 * @since  2009/04/14
	 */
	public static enum enBindDirection{
		縦,
		横
	}
	/**
	 * Excel設定情報クラス
	 * @author isono
	 * @since  2009/04/14
	 */
	public class SettingRow extends ObjectBase{

		//代入するシート
		String strSheet = "";
		//代入する列
		int intLine = 0;
		//代入する行
		int intRow = 0;
		//代入方法
		enBindKind ExcelBindKind = null;	
		//代入方向
		enBindDirection ExcelBindDirection = null;	
		//リスト行数
		int maxRow = 0;
		//リンク項目名		
		String LinkNm = "";	

		/**
		 * コンストラクタ
		 */
		public SettingRow(){
			super();
			
		}
		/**
		 * 代入するシート ゲッター
		 * @return String：代入するシート名
		 */
		public String getSheet() {
			return strSheet;
		}
		/**
		 * 代入する列 ゲッター
		 * @return int：代入する列
		 */
		public int getLine() {
			return intLine;
		}
		/**
		 * 代入する行 ゲッター
		 * @return int：代入する行
		 */
		public int getRow() {
			return intRow;
		}
		/**
		 * 代入方法 ゲッター
		 * @return enBindKind：代入方法
		 */
		public enBindKind getExcelBindKind() {
			return ExcelBindKind;
		}
		/**
		 * 代入方向 ゲッター
		 * @return enBindDirection：代入方向
		 */
		public enBindDirection getExcelBindDirection() {
			return ExcelBindDirection;
		}
		/**
		 * リスト最大行数 ゲッター
		 * @return int：リスト最大行数
		 */
		public int getMaxRow() {
			return maxRow;
		}
		/**
		 * リンク項目名 ゲッター
		 * @return String：リンク項目名
		 */
		public String getLinkNm() {
			return LinkNm;
		}
		/**
		 * 代入するシート名 セッター
		 * @param String : 代入するシート名
		 */
		public void setSheet(String _strSheet) {
			strSheet = _strSheet;
		}
		/**
		 * 代入する列 セッター
		 * @param int : 代入する列
		 */
		public void setLine(int _intLine) {
			intLine = _intLine;
		}
		/**
		 * 代入する行 セッター
		 * @param int : 代入する行
		 */
		public void setRow(int _intRow) {
			intRow = _intRow;
		}
		/**
		 * 代入方法 セッター
		 * @param enBindKind : 代入方法
		 */
		public void setExcelBindKind(enBindKind _ExcelBindKind) {
			ExcelBindKind = _ExcelBindKind;
		}
		/**
		 * 代入方向 セッター
		 * @param enBindDirection : 代入方向
		 */
		public void setExcelBindDirection(enBindDirection _ExcelBindDirection) {
			ExcelBindDirection = _ExcelBindDirection;
		}
		/**
		 * リスト行数 セッター
		 * @param int : リスト行数
		 */
		public void setMaxRow(int _maxRow) {
			maxRow = _maxRow;
		}
		/**
		 * リンク項目名 セッター
		 * @param _cd_team : リンク項目名
		 */
		public void setLinkNm(String _LinkNm) {
			LinkNm = _LinkNm;
		}

	}

	/**
	 * コンストラクタ
	 */
	public ExcelSetting(
			String strPrintName
	){

		//②帳票名をメンバPrintNameに退避する。
		PrintName = strPrintName;
		//③インスタンス生成
		ListSetting = new ArrayList<SettingRow>();
		
	}
	/**
	 * Excel設定を追加する。
	 * @param strSheet：代入するシート
	 * @param intLine：代入する列
	 * @param intRow：代入する行
	 * @param enmKind：代入方法
	 * @param enmDirection：リスト方向
	 * @param maxRow：リスト行数
	 * @param LinkNm：リンク項目名
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public void AddSetting(
			String strSheet, 
			int intLine, 
			int intRow, 
			enBindKind enmKind, 
			enBindDirection enmDirection, 
			int maxRow, 
			String LinkNm) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try{

			SettingRow setting = new SettingRow();
			
			//代入するシート
			setting.setSheet(strSheet);
			//代入する列
			setting.setLine(intLine);
			//代入する行
			setting.setRow(intRow);
			//代入方法
			setting.setExcelBindKind(enmKind);
			//代入方向
			setting.setExcelBindDirection(enmDirection);
			//リスト行数
			setting.setMaxRow(maxRow);
			//リンク項目名
			setting.setLinkNm(LinkNm);
			
			//リストに追加する
			ListSetting.add(setting);

		}catch(Exception e){
			em.ThrowException(e, "Excel設定の追加に失敗しました。");
			
		}finally{
			
		}
		
	}
	/**
	 * 設定の取得
	 * @param SttingIndex : 設定のインデックス
	 * @return SettingRow : 設定
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public SettingRow GetSetting(int SttingIndex) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		SettingRow ret = null;

		try{
			//①	リストより設定インデックスに該当する「SettingRow」を返却する
			ret = ListSetting.get(SttingIndex);

		}catch(Exception e){
			em.ThrowException(e, "設定の取得に失敗しました。");
			
		}finally{
			
		}
		return ret;
		
	}
	/**
	 * 設定の取得
	 * @param LinkNm : リンク項目名
	 * @return SettingRow : 設定
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public SettingRow GetSetting(String LinkNm) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		SettingRow ret = null;

		try{
			int ix = -1;
			//①	リンク項目名を引数にして、GetLinkNmNoを呼び出しインデックスを取得する。
			ix = GetLinkNmNo(LinkNm);
			//②	インデックスを引数にして、GetSettingを呼び出す。
			ret = ListSetting.get(ix);

		}catch(Exception e){
			em.ThrowException(e, "設定の取得に失敗しました。");
			
		}finally{
			
		}
		return ret;
		
	}
	/**
	 * リンク項目名に該当する、設定のインデックスを取得する。
	 * @param LinkNm : リンク項目名
	 * @return int : 設定のインデックス
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private int GetLinkNmNo(String LinkNm) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		int ret = -1;

		try{
			for (int i = 0; i < ListSetting.size(); i++){

//				if(ListSetting.get(i).getLinkNm() == LinkNm){
				if(ListSetting.get(i).getLinkNm().equals(LinkNm)){
					ret = i;
					break;

				}
				
			}
			
		}catch(Exception e){
			em.ThrowException(e, "リンク項目名に該当する、設定のインデックスの取得に失敗しました。");
			
		}finally{
			
		}
		return ret;
		
	}
	
}