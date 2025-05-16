package jp.co.blueflag.shisaquick.jws.base;

import java.math.BigDecimal;

import jp.co.blueflag.shisaquick.jws.common.*;


/**
 * 
 * 試作リストデータ保持
 *  : 試作リストデータの管理を行う
 *
 */
public class PrototypeListData extends DataBase {

	private BigDecimal dciShisakuUser;	//試作CD-社員CD
	private BigDecimal dciShisakuYear;	//試作CD-年
	private BigDecimal dciShisakuNum;	//試作CD-追番
	private int intShisakuSeq;			//試作SEQ
	private int intKoteiCd;				//工程CD
	private int intKoteiSeq;			//工程SEQ
	private BigDecimal dciRyo;			//量
	private String strIro;				//色
	private BigDecimal dciTorokuId;		//登録者ID
	private String strTorokuHi;			//登録日付
	private BigDecimal dciKosinId;		//更新者ID
	private String strKosinHi;			//更新日付
	private String strTorokuNm;			//登録者名
	private String strKosinNm;			//更新者名
//	private ExceptionBase ex;			//エラーハンドリング
	
//add start -------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.33,34
	private int intHenshuOkFg;			//編集可能フラグ
//add end   -------------------------------------------------------------------------------
	
	// ADD start 20120914 QP@20505 No.1
	private BigDecimal dciKouteiShiagari;//工程仕上重量
	// ADD end 20120914 QP@20505 No.1
	
	/**
	 * コンストラクタ
	 * @param xdtSetXml : XMLデータ
	 */
	public PrototypeListData() {
		//スーパークラスのコンストラクタ
		super();
		
		this.dciShisakuUser = null;
		this.dciShisakuYear = null;
		this.dciShisakuNum = null;
		this.intShisakuSeq = 0;
		this.intKoteiCd = -1;
		this.intKoteiSeq = -1;
		this.dciRyo = null;
		this.strIro = "-1";
		this.dciTorokuId = null;
		this.strTorokuHi = null;
		this.dciKosinId = null;
		this.strKosinHi = null;
//add start -------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.33,34
		this.intHenshuOkFg = 0;
//add end   -------------------------------------------------------------------------------
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
	 * 試作SEQ ゲッター
	 * @return intShisakuSeq : 試作SEQの値を返す
	 */
	public int getIntShisakuSeq() {
		return intShisakuSeq;
	}
	/**
	 * 試作SEQ セッター
	 * @param _intShisakuSeq : 試作SEQの値を格納する
	 */
	public void setIntShisakuSeq(int _intShisakuSeq) {
		this.intShisakuSeq = _intShisakuSeq;
	}

	/**
	 * 工程CD ゲッター
	 * @return intKoteiCd : 工程CDの値を返す
	 */
	public int getIntKoteiCd() {
		return intKoteiCd;
	}
	/**
	 * 工程CD セッター
	 * @param _intKoteiCd : 工程CDの値を格納する
	 */
	public void setIntKoteiCd(int _intKoteiCd) {
		this.intKoteiCd = _intKoteiCd;
	}

	/**
	 * 工程SEQ ゲッター
	 * @return intKoteiSeq : 工程SEQの値を返す
	 */
	public int getIntKoteiSeq() {
		return intKoteiSeq;
	}
	/**
	 * 工程SEQ セッター
	 * @param _intKoteiSeq : 工程SEQの値を格納する
	 */
	public void setIntKoteiSeq(int _intKoteiSeq) {
		this.intKoteiSeq = _intKoteiSeq;
	}

	/**
	 * 量 ゲッター
	 * @return dciRyo : 量の値を返す
	 */
	public BigDecimal getDciRyo() {
		return dciRyo;
	}
	/**
	 * 量 セッター
	 * @param _dciRyo : 量の値を格納する
	 */
	public void setDciRyo(BigDecimal _dciRyo) {
		this.dciRyo = _dciRyo;
	}

	/**
	 * 色 ゲッター
	 * @return strIro : 色の値を返す
	 */
	public String getStrIro() {
		return strIro;
	}
	/**
	 * 色 セッター
	 * @param _strIro : 色の値を格納する
	 */
	public void setStrIro(String _strIro) {
		this.strIro = _strIro;
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

//add start -------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.33,34
	//編集可能フラグセッタ＆ゲッタ
	public int getIntHenshuOkFg() {
		return intHenshuOkFg;
	}
	public void setIntHanshuOkFg(int intHenshuOkFg) {
		this.intHenshuOkFg = intHenshuOkFg;
	}
//add end   -------------------------------------------------------------------------------

// ADD start 20120914 QP@20505 No.1
	/**
	 * 工程仕上重量 ゲッター
	 * @return dciKouteiShiagari : 量の値を返す
	 */
	public BigDecimal getDciKouteiShiagari() {
		return dciKouteiShiagari;
	}
	/**
	 * 工程仕上重量 セッター
	 * @param _dciKouteiShiagari : 量の値を格納する
	 */
	public void setDciKouteiShiagari(BigDecimal _dciKouteiShiagari) {
		this.dciKouteiShiagari = _dciKouteiShiagari;
	}
//ADD end 20120914 QP@20505 No.1

}