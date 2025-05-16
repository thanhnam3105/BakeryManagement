package jp.co.blueflag.shisaquick.jws.base;

import java.math.BigDecimal;

import jp.co.blueflag.shisaquick.jws.common.*;

/**
 * 
 * 資材データ保持
 *
 */
public class ShizaiData extends DataBase {

	private BigDecimal dciShisakuUser;	//試作CD-社員CD
	private BigDecimal dciShisakuYear;	//試作CD-年
	private BigDecimal dciShisakuNum;	//試作CD-追番
	private int intShizaiSeq;		//資材SEQ
	private int intHyojiNo;			//表示順
	private String strShizaiCd;			//資材CD
	private String strShizaiNm;		//資材名称
	private BigDecimal dciTanka;			//単価
	private BigDecimal dciBudomari;	//歩留
	private BigDecimal dciTorokuId;			//登録者ID
	private String strTorokuHi;		//登録日付
	private BigDecimal dciKosinId;			//更新者ID
	private String strKosinHi;		//更新日付
	private String strTorokuNm;				//登録者名
	private String strKosinNm;				//更新者名
//	private ExceptionBase ex;				//エラーハンドリング
	
	/**
	 * コンストラクタ
	 * @param xdtSetXml : XMLデータ
	 */
	public ShizaiData() {
		//スーパークラスのコンストラクタ
		super();
		
		this.dciShisakuUser = null;
		this.dciShisakuYear = null;
		this.dciShisakuNum = null;
		this.intShizaiSeq = 0;
		this.intHyojiNo = 0;
		this.strShizaiCd = null;
		this.strShizaiNm = null;
		this.dciTanka = null;
		this.dciBudomari = null;
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
	 * @param  _dciShisakuUser : 試作CD-社員CDの値を格納する
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
	 * @param  _dciShisakuNum : 試作CD-追番の値を格納する
	 */
	public void setDciShisakuNum(BigDecimal _dciShisakuNum) {
		this.dciShisakuNum = _dciShisakuNum;
	}

	/**
	 * 資材SEQ ゲッター
	 * @return intShizaiSeq : 資材SEQの値を返す
	 */
	public int getIntShizaiSeq() {
		return intShizaiSeq;
	}
	/**
	 * 資材SEQ セッター
	 * @param intShizaiSeq : 資材SEQの値を格納する
	 */
	public void setIntShizaiSeq(int _intShizaiSeq) {
		this.intShizaiSeq = _intShizaiSeq;
	}

	/**
	 * 表示順 ゲッター
	 * @return intHyojiNo : 表示順の値を返す
	 */
	public int getIntHyojiNo() {
		return intHyojiNo;
	}
	/**
	 * 表示順 セッター
	 * @param  _intHyojiNo : 表示順の値を格納する
	 */
	public void setIntHyojiNo(int  _intHyojiNo) {
		this.intHyojiNo = _intHyojiNo;
	}

	/**
	 * 資材CD ゲッター
	 * @return strShizaiCd : 資材CDの値を返す
	 */
	public String getStrShizaiCd() {
		return strShizaiCd;
	}
	/**
	 * 資材CD セッター
	 * @param _strShizaiCd : 資材CDの値を格納する
	 */
	public void setStrShizaiCd(String _strShizaiCd) {
		this.strShizaiCd = _strShizaiCd;
	}

	/**
	 * 資材名称 ゲッター
	 * @return strShizaiNm : 資材名称の値を返す
	 */
	public String getStrShizaiNm() {
		return strShizaiNm;
	}
	/**
	 * 資材名称 セッター
	 * @param _strShizaiNm : 資材名称の値を格納する
	 */
	public void setStrShizaiNm(String _strShizaiNm) {
		this.strShizaiNm = _strShizaiNm;
	}

	/**
	 * 単価 ゲッター
	 * @return dciTanka : 単価の値を返す
	 */
	public BigDecimal getDciTanka() {
		return dciTanka;
	}
	/**
	 * 単価 セッター
	 * @param _dciTanka : 単価の値を格納する
	 */
	public void setDciTanka(BigDecimal _dciTanka) {
		this.dciTanka = _dciTanka;
	}

	/**
	 * 歩留 ゲッター
	 * @return dciBudomari : 歩留の値を返す
	 */
	public BigDecimal getDciBudomari() {
		return dciBudomari;
	}
	/**
	 * 歩留 セッター
	 * @param _dciBudomari : 歩留の値を格納する
	 */
	public void setDciBudomari(BigDecimal _dciBudomari) {
		this.dciBudomari = _dciBudomari;
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
