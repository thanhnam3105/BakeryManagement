package jp.co.blueflag.shisaquick.jws.base;

import java.math.BigDecimal;

import jp.co.blueflag.shisaquick.jws.common.DataBase;

/**
 * 
 * パラメータデータ保持クラス
 *
 */
public class ParamData extends DataBase {

	private BigDecimal dciUser;			//ユーザID
	private String strSisaku_user;	//試作CD_ユーザ
	private String strSisaku_nen;	//試作CD_年
	private String strSisaku_oi;	//試作CD_追番
	private String strMode;			//モード
	
	/**
	 * パラメータデータ　コンストラクタ
	 */
	public ParamData() {
		//スーパークラスのコンストラクタ呼び出し
		super();
		
		this.dciUser = new BigDecimal(0);
		this.strSisaku_user = "";
		this.strSisaku_nen = "";
		this.strSisaku_oi = "";
		this.strMode = "";
	}

	/**
	 * ユーザID ゲッター
	 * @return dciUser : ユーザIDの値を返す
	 */
	public BigDecimal getDciUser() {
		return dciUser;
	}
	/**
	 * ユーザID セッター
	 * @param _dciUser : ユーザIDの値を格納する
	 */
	public void setDciUser(BigDecimal _dciUser) {
		this.dciUser = _dciUser;
	}

	/**
	 * 試作CD_ユーザ ゲッター
	 * @return strSisaku_user : の値を返す
	 */
	public String getStrSisaku_user() {
		return strSisaku_user;
		
	}
	/**
	 * 試作CD_年 ゲッター
	 * @return getStrSisaku_nen : の値を返す
	 */
	public String getStrSisaku_nen() {
		return strSisaku_nen;
		
	}
	/**
	 * 試作CD_追番 ゲッター
	 * @return strSisaku : の値を返す
	 */
	public String getStrSisaku_oi() {
		return strSisaku_oi;
		
	}
	/**
	 * 試作CD セッター
	 * @param _strSisaku : 試作CDの値を格納する
	 */
	public void setStrSisaku(String _strSisaku) {
		if(_strSisaku.equals("new")){
			this.strSisaku_user = null;
			this.strSisaku_nen = null;
			this.strSisaku_oi = null;
		}else{
			String[] str1Ary = _strSisaku.split("-");
			this.strSisaku_user = str1Ary[0];
			this.strSisaku_nen = str1Ary[1];
			this.strSisaku_oi = str1Ary[2];
		}
	}

	/**
	 * モード ゲッター
	 * @return strMode : モードの値を返す
	 */
	public String getStrMode() {
		return strMode;
	}
	/**
	 * モード セッター
	 * @param _strMode : モードの値を格納する
	 */
	public void setStrMode(String _strMode) {
		this.strMode = _strMode;
	}
	
}
