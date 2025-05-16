package jp.co.blueflag.shisaquick.jws.manager;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

import jp.co.blueflag.shisaquick.jws.base.XmlData;
import jp.co.blueflag.shisaquick.jws.common.DataBase;
import jp.co.blueflag.shisaquick.jws.common.ExceptionBase;
import jp.co.blueflag.shisaquick.jws.data.DataCtrl;

/**
 * 
 * Resultデータ保持クラス
 *  : Servletとの通信により発生したエラーメッセージを格納する
 * 
 * @author TT.katayama
 * @since 2009/04/22
 *
 */
public class ResultData extends DataBase {

	private XmlData xdtData;			//XMLデータ
	private ExceptionBase ex;			//エラーハンドリング

	private String strReturnFlg;			//成功可否
	private String strErrorMsg;				//エラーメッセージ
	private String strClassNm;				//処理名称(クラス名)
	private String strErrmsgNo;			//エラーメッセージ番号
	private String strErrorCd;				//エラーコード
	private String strSystemMsg;			//システムエラーメッセージ
	private String strDebuglevel;			//デバッグレベル

	/**
	 * コンストラクタ
	 */
	public ResultData() throws ExceptionBase{
		//スーパークラスのコンストラクタを呼び出す
		super();
		
		strReturnFlg = "";
		strErrorMsg = "";
		strClassNm = "";
		strErrmsgNo = "";
		strErrorCd = "";
		strSystemMsg = "";
		strDebuglevel = "0";
		
		try {
			//エラーハンドリングクラスの生成
			this.ex = new ExceptionBase();

			//Resultデータを初期化
			this.initResultData();
			
		} catch ( Exception e ) {	
			//エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("Resultデータ保持のコンストラクタが失敗しました");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;
			
		} finally {
			
		}
		
	}
	
	/**
	 * 0001.データ設定
	 *  : XMLデータより試作テーブルデータを生成する。
	 * @param xdtData : XMLデータ
	 * @throws ExceptionBase 
	 */
	public void setResultData(XmlData xdtSetXml) throws ExceptionBase{
		this.xdtData = xdtSetXml;
		
		try {
			//Resultデータを初期化
			this.initResultData();
			
			/**********************************************************
			 *　Resultデータ格納
			 *********************************************************/
			//機能IDの設定
			String strKinoId = "RESULT";
			
			//全体配列取得
			ArrayList userData = xdtData.GetAryTag(strKinoId);
			
			//機能配列取得
			ArrayList kinoData = (ArrayList)userData.get(0);

			//テーブル配列取得
			ArrayList tableData = (ArrayList)kinoData.get(1);

			//レコード取得
			for(int i=1; i<tableData.size(); i++){
				//　１レコード取得
				ArrayList recData = ((ArrayList)((ArrayList)tableData.get(i)).get(0));
				
				//原料データへ格納
				for(int j=0; j<recData.size(); j++){
						
					//　項目名取得
					String recNm = ((String[])recData.get(j))[1];
					//　項目値取得
					String recVal = ((String[])recData.get(j))[2];
	
					/*****************Resultデータへ値セット*********************/
					if ( recNm == "flg_return" ) {
						//成功可否
						this.strReturnFlg = recVal;
					}
					if ( recNm == "msg_error" ) {
						//エラーメッセージ
						this.strErrorMsg = recVal;
					}
					if ( recNm == "nm_class" ) {
						//処理名称(クラス名)
						this.strClassNm = recVal;
					}
					if ( recNm == "no_errmsg" ) {
						//エラーメッセージ番号
						this.strErrmsgNo = recVal;
					}
					if ( recNm == "cd_error" ) {
						//エラーコード
						this.strErrorCd = recVal;
					}
					if ( recNm == "msg_system" ) {
						//システムエラーメッセージ
						this.strSystemMsg = recVal;
					}
					if ( recNm == "debuglevel" ) {
						//デバッグレベル
						this.strDebuglevel = recVal;
					}
					
				}
			}
			
		} catch (ExceptionBase e ) {
			throw e;
			
		} catch (Exception e) {
			e.printStackTrace();
			//エラー設定
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			ex.setStrErrmsg("Resultデータのデータ設定に失敗しました");
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getMessage());
			throw ex;
			
		} finally {
			
		}
	}
	
	/**
	 * Resultデータ初期化処理
	 */
	public void initResultData() {
		this.strReturnFlg = "";
		this.strErrorMsg = "";
		this.strClassNm = "";
		this.strErrmsgNo = "";
		this.strErrorCd = "";
		this.strSystemMsg = "";
		this.strDebuglevel = "";
	}
	
	/**
	 * 処理結果チェックメソッド
	 * @return true:処理成功, false:処理失敗 
	 */
	public boolean isReturnFlgCheck(){
		boolean blnRetChk = true;
		
		try{
			if ( this.getStrReturnFlg().equals("false") ) {
				blnRetChk = false;
			}	
		}catch(Exception e){
			//エラー設定
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			ex.setStrErrmsg("Resultデータの処理結果チェックに失敗しました");
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getMessage());
			DataCtrl.getInstance().getMessageCtrl().PrintErrMessage(ex);
			
		}finally{
			
		}
		
		return blnRetChk;
	}
	
	/**
	 * データ表示
	 */
	public void dispData() throws ExceptionBase{
		
		try{
			System.out.println(" \n成功可否 : " + this.getStrReturnFlg());
			System.out.println(" エラーメッセージ : " + this.getStrErrorMsg());
			System.out.println(" 処理名称(クラス名) : " + this.getStrClassNm());
			System.out.println(" エラーメッセージ番号 : " + this.getStrErrmsgNo());
			System.out.println(" エラーコード : " + this.getStrErrorCd());
			System.out.println(" システムエラーメッセージ : " + this.getStrSystemMsg());
			System.out.println(" デバッグレベル : " + this.getStrDebuglevel());
			
		}catch(Exception e){
			//エラー設定
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			ex.setStrErrmsg("Resultデータのデータ表示に失敗しました");
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getMessage());
			throw ex;
			
		}finally{
			
		}
	}

	/**
	 * 成功可否 ゲッター
	 * @return strReturnFlg : 成功可否の値を返す
	 */
	public String getStrReturnFlg() {
		return strReturnFlg;
	}
	/**
	 * 成功可否 セッター
	 * @param _strReturnFlg : 成功可否の値を格納する
	 */
	public void setStrReturnFlg(String _strReturnFlg) {
		this.strReturnFlg = _strReturnFlg;
	}

	/**
	 * エラーメッセージ ゲッター
	 * @return strErrorMsg : エラーメッセージの値を返す
	 */
	public String getStrErrorMsg() {
		return strErrorMsg;
	}
	/**
	 * エラーメッセージ セッター
	 * @param _strErrorMsg : エラーメッセージの値を格納する
	 */
	public void setStrErrorMsg(String _strErrorMsg) {
		this.strErrorMsg = _strErrorMsg;
	}

	/**
	 * 処理名称(クラス名) ゲッター
	 * @return strClassNm : 処理名称(クラス名)の値を返す
	 */
	public String getStrClassNm() {
		return strClassNm;
	}
	/**
	 * 処理名称(クラス名) セッター
	 * @param _strClassNm : 処理名称(クラス名)の値を格納する
	 */
	public void setStrClassNm(String _strClassNm) {
		this.strClassNm = _strClassNm;
	}

	/**
	 * エラーメッセージ番号 ゲッター
	 * @return strErrmsgNo : エラーメッセージ番号の値を返す
	 */
	public String getStrErrmsgNo() {
		return strErrmsgNo;
	}
	/**
	 * エラーメッセージ番号 セッター
	 * @param _strErrmsgNo : エラーメッセージ番号の値を格納する
	 */
	public void setStrErrmsgNo(String _strErrmsgNo) {
		this.strErrmsgNo = _strErrmsgNo;
	}

	/**
	 * エラーコード ゲッター
	 * @return strErrorCd : エラーコードの値を返す
	 */
	public String getStrErrorCd() {
		return strErrorCd;
	}
	/**
	 * エラーコード セッター
	 * @param _strErrorCd : エラーコードの値を格納する
	 */
	public void setStrErrorCd(String _strErrorCd) {
		this.strErrorCd = _strErrorCd;
	}

	/**
	 * システムエラーメッセージ ゲッター
	 * @return strSystemMsg : システムエラーメッセージの値を返す
	 */
	public String getStrSystemMsg() {
		return strSystemMsg;
	}
	/**
	 * システムエラーメッセージ セッター
	 * @param _strSystemMsg : システムエラーメッセージの値を格納する
	 */
	public void setStrSystemMsg(String _strSystemMsg) {
		this.strSystemMsg = _strSystemMsg;
	}

	/**
	 * デバッグレベル ゲッター
	 * @return strDebuglevel : デバッグレベルの値を返す
	 */
	public String getStrDebuglevel() {
		return strDebuglevel;
	}
	/**
	 * デバッグレベル セッター
	 * @param _strDebuglevel : デバッグレベルの値を格納する
	 */
	public void setStrDebuglevel(String _strDebuglevel) {
		this.strDebuglevel = _strDebuglevel;
	}
	
}
