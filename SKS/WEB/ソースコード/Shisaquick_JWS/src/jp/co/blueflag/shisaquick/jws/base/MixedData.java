package jp.co.blueflag.shisaquick.jws.base;

import java.math.BigDecimal;

import jp.co.blueflag.shisaquick.jws.common.*;

/**
 *
 * 配合データ保持
 *
 */
public class MixedData extends DataBase {

	private BigDecimal dciShisakuUser;	//試作CD-社員CD
	private BigDecimal dciShisakuYear;	//試作CD-年
	private BigDecimal dciShisakuNum;	//試作CD-追番
	private int intKoteiCd;				//工程CD
	private int intKoteiSeq;			//工程SEQ
	private String strKouteiNm;			//工程名
	private String strKouteiZokusei;	//工程属性
	private int intKoteiNo;				//工程順
	private int intGenryoNo;			//原料順
	private String strGenryoCd;			//原料CD
	private int intKaishaCd;			//会社CD
	private int intBushoCd;				//部署CD
	private String strGenryoNm;			//原料名称
	private BigDecimal dciTanka;		//単価
	private BigDecimal dciBudomari;		//歩留
	private BigDecimal dciGanyuritu;	//油含有率
	private BigDecimal dciSakusan;		//酢酸
	private BigDecimal dciShokuen;		//食塩
	private BigDecimal dciSosan;		//総酸
	private String strIro;				//色
	private BigDecimal dciTorokuId;		//登録者ID
	private String strTorokuHi;			//登録日付
	private BigDecimal dciKosinId;		//更新者ID
	private String strKosinHi;			//更新日付
	private String strTorokuNm;			//登録者名
	private String strKosinNm;			//更新者名

//add start -------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.31
	private BigDecimal dciMaBudomari;		//マスタ歩留
//add end   -------------------------------------------------------------------------------
// ADD start 20121002 QP@20505 No.24
	private BigDecimal dciMsg;		//ＭＳｇ
// ADD end 20121002 QP@20505 No.24

//	private ExceptionBase ex;			//エラーハンドリング

	/**
	 * コンストラクタ
	 * @param xdtSetXml : XMLデータ
	 */
	public MixedData(){
		//スーパークラスのコンストラクタ
		super();

		this.dciShisakuUser = null;
		this.dciShisakuYear = null;
		this.dciShisakuNum = null;
		this.intKoteiCd = 0;
		this.intKoteiSeq = 0;
		this.intKoteiNo = 0;
		this.intGenryoNo = 0;
		this.strGenryoCd = null;
		this.intKaishaCd = -1;
		this.intBushoCd = -1;
		this.strGenryoNm = null;
		this.dciTanka = null;
		this.dciBudomari = null;
		this.dciGanyuritu = null;
		this.dciSakusan = null;
		this.dciShokuen = null;
		this.dciSosan = null;
		this.strIro = "-1";
		this.dciTorokuId = null;
		this.strTorokuHi = null;
		this.dciKosinId = null;
		this.strKosinHi = null;
//add start -------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.31
		this.dciMaBudomari = null;
//add end   -------------------------------------------------------------------------------
// ADD start 20121002 QP@20505 No.24
		this.dciMsg = null;
// ADD end 20121002 QP@20505 No.24
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
	 * 工程順 ゲッター
	 * @return intKoteiNo : 工程順の値を返す
	 */
	public int getIntKoteiNo() {
		return intKoteiNo;
	}
	/**
	 * 工程順 セッター
	 * @param _intKoteiNo : 工程順の値を格納する
	 */
	public void setIntKoteiNo(int _intKoteiNo) {
		this.intKoteiNo = _intKoteiNo;
	}

	/**
	 * 原料順 ゲッター
	 * @return intGenryoNo : 原料順の値を返す
	 */
	public int getIntGenryoNo() {
		return intGenryoNo;
	}
	/**
	 * 原料順 セッター
	 * @param _intGenryoNo : 原料順の値を格納する
	 */
	public void setIntGenryoNo(int _intGenryoNo) {
		this.intGenryoNo = _intGenryoNo;
	}

	/**
	 * 原料CD ゲッター
	 * @return strGenryoCd : 原料CDの値を返す
	 */
	public String getStrGenryoCd() {
		return strGenryoCd;
	}
	/**
	 * 原料CD セッター
	 * @param _strGenryoCd : 原料CDの値を格納する
	 */
	public void setStrGenryoCd(String _strGenryoCd) {
		this.strGenryoCd = _strGenryoCd;
	}

	/**
	 * 会社CD ゲッター
	 * @return intKaishaCd : 会社CDの値を返す
	 */
	public int getIntKaishaCd() {
		return intKaishaCd;
	}
	/**
	 * 会社CD セッター
	 * @param _intKaishaCd : 会社CDの値を格納する
	 */
	public void setIntKaishaCd(int _intKaishaCd) {
		this.intKaishaCd = _intKaishaCd;
	}

	/**
	 * 部署CD ゲッター
	 * @return intBushoCd : 部署CDの値を返す
	 */
	public int getIntBushoCd() {
		return intBushoCd;
	}
	/**
	 * 部署CD セッター
	 * @param _intBushoCd : 部署CDの値を格納する
	 */
	public void setIntBushoCd(int _intBushoCd) {
		this.intBushoCd = _intBushoCd;
	}

	/**
	 * 原料名称 ゲッター
	 * @return strGenryoNm : 原料名称の値を返す
	 */
	public String getStrGenryoNm() {
		return strGenryoNm;
	}
	/**
	 * 原料名称 セッター
	 * @param _strGenryoNm : 原料名称の値を格納する
	 */
	public void setStrGenryoNm(String _strGenryoNm) {
		this.strGenryoNm = _strGenryoNm;
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
	 *歩留  ゲッター
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
	 * 油含有率 ゲッター
	 * @return dciGanyuritu : 油含有率の値を返す
	 */
	public BigDecimal getDciGanyuritu() {
		return dciGanyuritu;
	}
	/**
	 * 油含有率 セッター
	 * @param _dciGanyuritu : 油含有率の値を格納する
	 */
	public void setDciGanyuritu(BigDecimal _dciGanyuritu) {
		this.dciGanyuritu = _dciGanyuritu;
	}

	/**
	 * 酢酸 ゲッター
	 * @return dciSakusan : 酢酸の値を返す
	 */
	public BigDecimal getDciSakusan() {
		return dciSakusan;
	}
	/**
	 * 酢酸 セッター
	 * @param _dciSakusan : 酢酸の値を格納する
	 */
	public void setDciSakusan(BigDecimal _dciSakusan) {
		this.dciSakusan = _dciSakusan;
	}

	/**
	 * 食塩 ゲッター
	 * @return dciShokuen : 食塩の値を返す
	 */
	public BigDecimal getDciShokuen() {
		return dciShokuen;
	}
	/**
	 * 食塩 セッター
	 * @param _dciShokuen : 食塩の値を格納する
	 */
	public void setDciShokuen(BigDecimal _dciShokuen) {
		this.dciShokuen = _dciShokuen;
	}

	/**
	 * 総酸 ゲッター
	 * @return dciSosan : 総酸の値を返す
	 */
	public BigDecimal getDciSosan() {
		return dciSosan;
	}
	/**
	 * 総酸 セッター
	 * @param _dciSosan : 総酸の値を格納する
	 */
	public void setDciSosan(BigDecimal _dciSosan) {
		this.dciSosan = _dciSosan;
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
	 * @return strKosinHi : の値を返す
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
	 * 工程名 ゲッター
	 * @return strKouteiNm : 工程名の値を返す
	 */
	public String getStrKouteiNm() {
		return strKouteiNm;
	}
	/**
	 * 工程名 セッター
	 * @param _strKouteiNm : 工程名の値を格納する
	 */
	public void setStrKouteiNm(String _strKouteiNm) {
		this.strKouteiNm = _strKouteiNm;
	}

	/**
	 * 工程属性 ゲッター
	 * @return strKouteiZokusei : 工程属性の値を返す
	 */
	public String getStrKouteiZokusei() {
		return strKouteiZokusei;
	}
	/**
	 * 工程属性 セッター
	 * @param _strKouteiZokusei : 工程属性の値を格納する
	 */
	public void setStrKouteiZokusei(String _strKouteiZokusei) {
		this.strKouteiZokusei = _strKouteiZokusei;
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
//QP@00412_シサクイック改良 No.31
	/**
	 * マスタ歩留 ゲッター
	 * @return dciMaBudomari : マスタ歩留の値を返す
	 */
	public BigDecimal getDciMaBudomari() {
		return dciMaBudomari;
	}
	/**
	 * マスタ歩留 セッター
	 * @param dciMaBudomari : マスタ歩留の値を格納する
	 */
	public void setDciMaBudomari(BigDecimal dciMaBudomari) {
		this.dciMaBudomari = dciMaBudomari;
	}
//add end   -------------------------------------------------------------------------------
//ADD start 20121002 QP@20505 No.24
	/**
	 * 食塩 ゲッター
	 * @return dciShokuen : 食塩の値を返す
	 */
	public BigDecimal getDciMsg() {
		return dciMsg;
	}
	/**
	 * 食塩 セッター
	 * @param _dciShokuen : 食塩の値を格納する
	 */
	public void setDciMsg(BigDecimal _dciMsg) {
		this.dciMsg = _dciMsg;
	}
//ADD end 20121002 QP@20505 No.24
}