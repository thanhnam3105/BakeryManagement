package jp.co.blueflag.shisaquick.jws.base;

import java.math.BigDecimal;

import jp.co.blueflag.shisaquick.jws.common.DataBase;

/******************************************************************
 * 
 * 試算データ保持
 * @author k-katayama
 * 
 ******************************************************************/
public class ShisanData extends DataBase {

	private BigDecimal dciShisakuUser;		//試作CD-社員CD
	private BigDecimal dciShisakuYear;		//試作CD-年
	private BigDecimal dciShisakuNum;		//試作CD-追番
	private int intShisakuSeq;					//試作SEQ
	private int intRirekiNo;						//履歴順
	private String strSampleNo;				//サンプルNO（名称）
	private String strShisanHi;					//試算日付
	private BigDecimal dciTorokuId;			//登録者ID
	private String strTorokuHi;					//登録日付

	/******************************************************************
	 * 
	 * コンストラクタ
	 * @param xdtSetXml : XMLデータ
	 * 
	 ******************************************************************/
	public ShisanData() {
		//スーパークラスのコンストラクタ
		super();

		//試算データ初期化処理
		this.initShisanData();
				
	}

	/******************************************************************
	 * 
	 * 試算履歴データ初期化処理
	 * 
	 ******************************************************************/
	private void initShisanData() {
		
		this.dciShisakuUser = new BigDecimal(0);
		this.dciShisakuYear = new BigDecimal(0);
		this.dciShisakuNum = new BigDecimal(0);
		this.intShisakuSeq = 0;
		this.intRirekiNo = 0;		
		this.strSampleNo = "";
		this.strShisanHi = "";
		this.dciTorokuId = new BigDecimal(0);
		this.strTorokuHi = "";
		
	}

	/**
	 * @return dciShisakuUser： 試作CD-社員CDを返す
	 */
	public BigDecimal getDciShisakuUser() {
		return dciShisakuUser;
	}

	/**
	 * @param dciShisakuUser： 試作CD-社員CDを取得する
	 */
	public void setDciShisakuUser(BigDecimal dciShisakuUser) {
		this.dciShisakuUser = dciShisakuUser;
	}

	/**
	 * @return dciShisakuYear： 試作CD-年を返す
	 */
	public BigDecimal getDciShisakuYear() {
		return dciShisakuYear;
	}

	/**
	 * @param dciShisakuYear： 試作CD-年を取得する
	 */
	public void setDciShisakuYear(BigDecimal dciShisakuYear) {
		this.dciShisakuYear = dciShisakuYear;
	}

	/**
	 * @return dciShisakuNum： 試作CD-追番を返す
	 */
	public BigDecimal getDciShisakuNum() {
		return dciShisakuNum;
	}

	/**
	 * @param dciShisakuNum： 試作CD-追番を取得する
	 */
	public void setDciShisakuNum(BigDecimal dciShisakuNum) {
		this.dciShisakuNum = dciShisakuNum;
	}

	/**
	 * @return intShisakuSeq： 試作SEQを返す
	 */
	public int getIntShisakuSeq() {
		return intShisakuSeq;
	}

	/**
	 * @param intShisakuSeq： 試作SEQを取得する
	 */
	public void setIntShisakuSeq(int intShisakuSeq) {
		this.intShisakuSeq = intShisakuSeq;
	}

	/**
	 * @return intRirekiNo： 履歴順を返す
	 */
	public int getIntRirekiNo() {
		return intRirekiNo;
	}

	/**
	 * @param intRirekiNo： 履歴順を取得する
	 */
	public void setIntRirekiNo(int intRirekiNo) {
		this.intRirekiNo = intRirekiNo;
	}

	/**
	 * @return strSampleNo： サンプルNO（名称）を返す
	 */
	public String getStrSampleNo() {
		return strSampleNo;
	}

	/**
	 * @param strSampleNo： サンプルNO（名称）を取得する
	 */
	public void setStrSampleNo(String strSampleNo) {
		this.strSampleNo = strSampleNo;
	}

	/**
	 * @return strShisanHi： 試算日付を返す
	 */
	public String getStrShisanHi() {
		return strShisanHi;
	}

	/**
	 * @param strShisanHi： 試算日付を取得する
	 */
	public void setStrShisanHi(String strShisanHi) {
		this.strShisanHi = strShisanHi;
	}

	/**
	 * @return dciTorokuId： 登録者IDを返す
	 */
	public BigDecimal getDciTorokuId() {
		return dciTorokuId;
	}

	/**
	 * @param dciTorokuId： 登録者IDを取得する
	 */
	public void setDciTorokuId(BigDecimal dciTorokuId) {
		this.dciTorokuId = dciTorokuId;
	}

	/**
//	 * @return strTorokuHi： 登録日付を返す
	 */
	public String getStrTorokuHi() {
		return strTorokuHi;
	}

	/**
	 * @param strTorokuHi： 登録日付を取得する
	 */
	public void setStrTorokuHi(String strTorokuHi) {
		this.strTorokuHi = strTorokuHi;
	}

}
