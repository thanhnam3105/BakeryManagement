package jp.co.blueflag.shisaquick.jws.base;

import java.math.BigDecimal;

import jp.co.blueflag.shisaquick.jws.common.*;

/**
 * 
 * 製造工程データ保持
 *  : 製造工程データの管理を行う
 *
 */
public class ManufacturingData extends DataBase {

	private BigDecimal dciShisakuUser;	//試作CD-社員CD
	private BigDecimal dciShisakuYear;	//試作CD-年
	private BigDecimal dciShisakuNum;	//試作CD-追番
	private int intTyuiNo;				//注意事項NO
	private String strTyuiNaiyo;		//注意事項
	private BigDecimal dciTorokuId;		//登録者ID
	private String strTorokuHi;			//登録日付
	private BigDecimal dciKosinId;		//更新者ID
	private String strKosinHi;			//更新日付
	private String strTorokuNm;			//登録者名
	private String strKosinNm;			//更新者名
//	private ExceptionBase ex;			//エラーハンドリング
	
	/**
	 * コンストラクタ
	 * @param xdtSetXml : XMLデータ
	 */
	public ManufacturingData() {
		//スーパークラスのコンストラクタ
		super();
		
		this.dciShisakuUser = null;
		this.dciShisakuYear = null;
		this.dciShisakuNum = null;
		this.intTyuiNo = -1;
		this.strTyuiNaiyo = null;
		this.dciTorokuId = null;
		this.strTorokuHi = null;
		this.dciKosinId = null;
		this.strKosinHi = null;
	}
	
	/**
	 * 試作CD-社員CD ゲッター
	 * @return dciShisakuUser : 試作CD-社員CDの値を返す
	 */
	public BigDecimal getDciShisakuUser() {
		return dciShisakuUser;
	}
	/**
	 * 試作CD-社員CD セッター
	 * @param _dciShisakuUser : 試作CD-社員CDの値を格納する
	 */
	public void setDciShisakuUser(BigDecimal _dciShisakuUser) {
		this.dciShisakuUser = _dciShisakuUser;
	}

	/**
	 * 試作CD-年 ゲッター
	 * @return dciShisakuYear : 試作CD-年の値を返す
	 */
	public BigDecimal getDciShisakuYear() {
		return dciShisakuYear;
	}
	/**
	 * 試作CD-年 セッター
	 * @param _dciShisakuYear : 試作CD-年の値を格納する
	 */
	public void setDciShisakuYear(BigDecimal _dciShisakuYear) {
		this.dciShisakuYear = _dciShisakuYear;
	}

	/**
	 * 試作CD-追番 ゲッター
	 * @return dciShisakuNum : 試作CD-追番の値を返す
	 */
	public BigDecimal getDciShisakuNum() {
		return dciShisakuNum;
	}
	/**
	 * 試作CD-追番 セッター
	 * @param _dciShisakuNum : 試作CD-追番の値を格納する
	 */
	public void setDciShisakuNum(BigDecimal _dciShisakuNum) {
		this.dciShisakuNum = _dciShisakuNum;
	}

	/**
	 * 注意事項NO ゲッター
	 * @return intTyuiNo : 注意事項NOの値を返す
	 */
	public int getIntTyuiNo() {
		return intTyuiNo;
	}
	/**
	 * 注意事項NO セッター
	 * @param _intTyuiNo : 注意事項NOの値を格納する
	 */
	public void setIntTyuiNo(int _intTyuiNo) {
		this.intTyuiNo = _intTyuiNo;
	}

	/**
	 * 注意事項 ゲッター
	 * @return intTyuiNaiyo : 注意事項の値を返す
	 */
	public String getStrTyuiNaiyo() {
		return strTyuiNaiyo;
	}
	/**
	 * 注意事項 セッター
	 * @param _strTyuiNaiyo : 注意事項の値を格納する
	 */
	public void setStrTyuiNaiyo(String _strTyuiNaiyo) {
		this.strTyuiNaiyo = _strTyuiNaiyo;
	}

	/**
	 * 登録者ID ゲッター
	 * @return dciTorokuId : 登録者IDの値を返す
	 */
	public BigDecimal getDciTorokuId() {
		return dciTorokuId;
	}
	/**
	 * 登録者ID セッター
	 * @param _dciTorokuId : 登録者IDの値を格納する
	 */
	public void setDciTorokuId(BigDecimal _dciTorokuId) {
		this.dciTorokuId = _dciTorokuId;
	}

	/**
	 * 登録日付 ゲッター
	 * @return strTorokuHi : 登録日付の値を返す
	 */
	public String getStrTorokuHi() {
		return strTorokuHi;
	}
	/**
	 * 登録日付 セッター
	 * @param _strTorokuHi : 登録日付の値を格納する
	 */
	public void setStrTorokuHi(String _strTorokuHi) {
		this.strTorokuHi = _strTorokuHi;
	}

	/**
	 * 更新者ID ゲッター
	 * @return dciKosinId : 更新者IDの値を返す
	 */
	public BigDecimal getDciKosinId() {
		return dciKosinId;
	}
	/**
	 * 更新者ID セッター
	 * @param _dciKosinId : 更新者IDの値を格納する
	 */
	public void setDciKosinId(BigDecimal _dciKosinId) {
		this.dciKosinId = _dciKosinId;
	}

	/**
	 * 更新日付 ゲッター
	 * @return strKosinHi : 更新日付の値を返す
	 */
	public String getStrKosinHi() {
		return strKosinHi;
	}
	/**
	 * 更新日付 セッター
	 * @param _strKosinHi : 更新日付の値を格納する
	 */
	public void setStrKosinHi(String _strKosinHi) {
		this.strKosinHi = _strKosinHi;
	}
	
	/**
	 * 登録者名 ゲッター
	 * @return strTorokuNm : 登録者名の値を返す
	 */
	public String getStrTorokuNm() {
		return strTorokuNm;
	}
	/**
	 * 登録者名 セッター
	 * @param _strTorokuNm : 登録者名の値を格納する
	 */
	public void setStrTorokuNm(String _strTorokuNm) {
		this.strTorokuNm = _strTorokuNm;
	}
	
	/**
	 * 更新者名 ゲッター
	 * @return strKosinNm : 更新者名の値を返す
	 */
	public String getStrKosinNm() {
		return strKosinNm;
	}
	/**
	 * 更新者名 セッター
	 * @param _strKosinNm : 更新者名の値を格納する
	 */
	public void setStrKosinNm(String _strKosinNm) {
		this.strKosinNm = _strKosinNm;
	}

}
