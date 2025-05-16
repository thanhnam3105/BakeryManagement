package jp.co.blueflag.shisaquick.jws.base;

import java.math.BigDecimal;

import jp.co.blueflag.shisaquick.jws.common.*;

/**
 *
 * 原料データ保持
 *  : 原料データに関する情報を管理する
 *
 */
public class MaterialData extends DataBase {

	private int intKaishacd;			//会社コード
	private int intBushocd;				//部署コード
	private String strGenryocd;			//原料コード
	private String strGenryonm;			//原料名
	private String strKaishanm;			//会社名
	private String strBushonm;			//部署名
	private BigDecimal dciSakusan;		//酢酸
	private BigDecimal dciShokuen;		//食塩
	private BigDecimal dciSousan;		//総酸
	private BigDecimal dciGanyu;		//油含有率
	private String strHyoji;			//表示案
	private String strTenka;			//添加物
	private String strMemo;				//メモ
	private String strEiyono1;			//栄養計算食品番号１
	private String strEiyono2;			//栄養計算食品番号２
	private String strEiyono3;			//栄養計算食品番号３
	private String strEiyono4;			//栄養計算食品番号４
	private String strEiyono5;			//栄養計算食品番号５
	private String strWariai1;			//割合１
	private String strWariai2;			//割合２
	private String strWariai3;			//割合３
	private String strWariai4;			//割合４
	private String strWariai5;			//割合５
	private String strNyurokuhi;		//入力日
	private BigDecimal dciNyurokucd;	//入力者コード
	private String strNyurokunm;		//入力者名
	private int intKakunin;				//分析情報確認フラグ
	private String strKakuninhi;		//確認日
	private BigDecimal dciKakunincd;	//確認者コード
	private String strKakuninnm;		//確認者名
	private int intHaisicd;				//廃止フラグ
	private String strkakuteicd;		//確定コード
	private String strKonyu;			//最終購入日
	private BigDecimal dciTanka;		//単価
	private BigDecimal dciBudomari;		//歩留
	private String strNisugata;			//荷姿
	private BigDecimal dciTorokuId;		//登録者ID
	private String strTorokuDt;			//登録日付
	private String strTorokuNm;			//登録者名
	private BigDecimal dciKosinId;		//更新者ID
	private String strKosinDt;			//更新日付
	private String strKosinNm;			//更新者名
	private int intListRowMax;			//表示最大検す
	private int intMaxRow;				//レコード件数

//add start --------------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.4
	private int intShiyoFlg;			//使用実績フラグ
	private int intMishiyoFlg;			//未使用フラグ
//add end --------------------------------------------------------------------------------------

//add start --------------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.31
	private BigDecimal ma_dciBudomari;		//歩留
//add end --------------------------------------------------------------------------------------

	private int intHankou;				//分析値差異の有無
	private XmlData xdtData;			//XMLデータ
// ADD start 20121002 QP@20505 No.24
	private BigDecimal dciMsg;		//ＭＳＧ
// ADD end 20121002 QP@20505 No.24


	/**
	 * コンストラクタ
	 * @param xdtSetXml : XMLデータ
	 */
	 public MaterialData() {
		 //スーパークラスのコンストラクタ呼び出し
		 super();

		 this.intKaishacd = -1;
		 this.intBushocd = -1;
		 this.strGenryocd = null;
		 this.strKaishanm = null;
		 this.strBushonm = null;
		 this.strGenryonm = null;
		 this.dciSakusan = null;
		 this.dciShokuen = null;
		 this.dciSousan = null;
		 this.dciGanyu = null;
		 this.strHyoji = null;
		 this.strTenka = null;
		 this.strMemo = null;
		 this.strEiyono1 = null;
		 this.strEiyono2 = null;
		 this.strEiyono3 = null;
		 this.strEiyono4 = null;
		 this.strEiyono5 = null;
		 this.strWariai1 = null;
		 this.strWariai2 = null;
		 this.strWariai3 = null;
		 this.strWariai4 = null;
		 this.strWariai5 = null;

		 this.intHaisicd = 0;
		 this.strkakuteicd = null;
		 this.strKakuninhi = null;
		 this.dciKakunincd = null;
		 this.strKakuninnm = null;

		 this.strNyurokuhi = null;
		 this.dciNyurokucd = null;
		 this.strNyurokunm = null;

		 this.intKakunin = 0;
		 this.strKonyu = null;
		 this.dciTanka = null;
		 this.dciBudomari = null;
		 this.strNisugata = null;

		 this.dciTorokuId = null;
		 this.strTorokuDt = null;
		 this.strTorokuNm = null;
		 this.dciKosinId = null;
		 this.strKosinDt = null;
		 this.strKosinNm = null;
//add start --------------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.4
		 this.intShiyoFlg = 0;
		 this.intMishiyoFlg = 0;
//add end --------------------------------------------------------------------------------------
//add start --------------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.31
		 this.ma_dciBudomari = null;
//add end --------------------------------------------------------------------------------------
		 this.intHankou = 0;
// ADD start 20121002 QP@20505 No.24
		 this.dciMsg = null;
// ADD end 20121002 QP@20505 No.24

	 }


	/**
	 * 会社コード ゲッター
	 * @return intKaishacd : 会社コードの値を返す
	 */
	public int getIntKaishacd() {
		return intKaishacd;
	}
	/**
	 * 会社コード セッター
	 * @param _intKaishacd : 会社コードの値を格納する
	 */
	public void setIntKaishacd(int _intKaishacd) {
		this.intKaishacd = _intKaishacd;
	}

	/**
	 * 部署コード  ゲッター
	 * @return intBushocd : 部署コードの値を返す
	 */
	public int getIntBushocd() {
		return intBushocd;
	}
	/**
	 * 部署コード セッター
	 * @param _intBushocd : 部署コードの値を格納する
	 */
	public void setIntBushocd(int _intBushocd) {
		this.intBushocd = _intBushocd;
	}

	/**
	 * 原料コード  ゲッター
	 * @return strGenryocd : 原料コードの値を返す
	 */
	public String getStrGenryocd() {
		return strGenryocd;
	}
	/**
	 * 原料コード セッター
	 * @param _strGenryocd : 原料コードの値を格納する
	 */
	public void setStrGenryocd(String _strGenryocd) {
		this.strGenryocd = _strGenryocd;
	}

	/**
	 * 原料名  ゲッター
	 * @return strGenryonm : 原料名の値を返す
	 */
	public String getStrGenryonm() {
		return strGenryonm;
	}
	/**
	 * 原料名 セッター
	 * @param _strGenryonm : 原料名の値を格納する
	 */
	public void setStrGenryonm(String _strGenryonm) {
		this.strGenryonm = _strGenryonm;
	}

	/**
	 * 会社名 ゲッター
	 * @return strKaishanm : 会社名の値を返す
	 */
	public String getStrKaishanm() {
		return strKaishanm;
	}
	/**
	 * 会社名 セッター
	 * @param _strKaishanm : 会社名の値を格納する
	 */
	public void setStrKaishanm(String _strKaishanm) {
		this.strKaishanm = _strKaishanm;
	}

	/**
	 * 部署名 ゲッター
	 * @return strBushonm : 部署名の値を返す
	 */
	public String getStrBushonm() {
		return strBushonm;
	}
	/**
	 * 部署名 セッター
	 * @param _strBushonm : 部署名の値を格納する
	 */
	public void setStrBushonm(String _strBushonm) {
		this.strBushonm = _strBushonm;
	}

	/**
	 * 酢酸  ゲッター
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
	 * 食塩  ゲッター
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

// ADD start 20121002 QP@20505 No.24
	/**
	 * ＭＳＧ  ゲッター
	 * @return dciMsg : ＭＳＧの値を返す
	 */
	public BigDecimal getDciMsg() {
		return dciMsg;
	}
	/**
	 * ＭＳＧ セッター
	 * @param _dciShokuen : 食塩の値を格納する
	 */
	public void setDciMsg(BigDecimal _dciMsg) {
		this.dciMsg = _dciMsg;
	}
// ADD end 20121002 QP@20505 No.24

	/**
	 * 総酸  ゲッター
	 * @return dciSousan : 総酸の値を返す
	 */
	public BigDecimal getDciSousan() {
		return dciSousan;
	}
	/**
	 * 総酸 セッター
	 * @param _dciSousan : 総酸の値を格納する
	 */
	public void setDciSousan(BigDecimal _dciSousan) {
		this.dciSousan = _dciSousan;
	}

	/**
	 * 油含有率  ゲッター
	 * @return intGanyu : 油含有率の値を返す
	 */
	public BigDecimal getDciGanyu() {
		return dciGanyu;
	}
	/**
	 * 油含有率 セッター
	 * @param _dciGanyu : 油含有率の値を格納する
	 */
	public void setDciGanyu(BigDecimal _dciGanyu) {
		this.dciGanyu = _dciGanyu;
	}

	/**
	 * 表示案  ゲッター
	 * @return strHyoji : 表示案の値を返す
	 */
	public String getStrHyoji() {
		return strHyoji;
	}
	/**
	 * 表示案 セッター
	 * @param _strHyoji : 表示案の値を格納する
	 */
	public void setStrHyoji(String _strHyoji) {
		this.strHyoji = _strHyoji;
	}

	/**
	 * 添加物  ゲッター
	 * @return strTenka : 添加物の値を返す
	 */
	public String getStrTenka() {
		return strTenka;
	}
	/**
	 * 添加物 セッター
	 * @param _strTenka : 添加物の値を格納する
	 */
	public void setStrTenka(String _strTenka) {
		this.strTenka = _strTenka;
	}

	/**
	 * 栄養計算食品番号1  ゲッター
	 * @return strEiyono1 : 栄養計算食品番号1の値を返す
	 */
	public String getStrEiyono1() {
		return strEiyono1;
	}
	/**
	 * 栄養計算食品番号1 セッター
	 * @param _strEiyono1 : 栄養計算食品番号1の値を格納する
	 */
	public void setStrEiyono1(String _strEiyono1) {
		this.strEiyono1 = _strEiyono1;
	}

	/**
	 * 栄養計算食品番号2  ゲッター
	 * @return strEiyono2 : 栄養計算食品番号2の値を返す
	 */
	public String getStrEiyono2() {
		return strEiyono2;
	}
	/**
	 * 栄養計算食品番号2 セッター
	 * @param _strEiyono2 : 栄養計算食品番号3の値を格納する
	 */
	public void setStrEiyono2(String _strEiyono2) {
		this.strEiyono2 = _strEiyono2;
	}

	/**
	 * 栄養計算食品番号3  ゲッター
	 * @return strEiyono3 : 栄養計算食品番号3の値を返す
	 */
	public String getStrEiyono3() {
		return strEiyono3;
	}
	/**
	 * 栄養計算食品番号3 セッター
	 * @param _strEiyono3 : 栄養計算食品番号3の値を格納する
	 */
	public void setStrEiyono3(String _strEiyono3) {
		this.strEiyono3 = _strEiyono3;
	}

	/**
	 * 栄養計算食品番号4  ゲッター
	 * @return strEiyono4 : 栄養計算食品番号4の値を返す
	 */
	public String getStrEiyono4() {
		return strEiyono4;
	}
	/**
	 * 栄養計算食品番号4 セッター
	 * @param _strEiyono4 : 栄養計算食品番号4の値を格納する
	 */
	public void setStrEiyono4(String _strEiyono4) {
		this.strEiyono4 = _strEiyono4;
	}

	/**
	 * 栄養計算食品番号5  ゲッター
	 * @return strEiyono5 : 栄養計算食品番号5の値を返す
	 */
	public String getStrEiyono5() {
		return strEiyono5;
	}
	/**
	 * 栄養計算食品番号5 セッター
	 * @param _strEiyono5 : 栄養計算食品番号5の値を格納する
	 */
	public void setStrEiyono5(String _strEiyono5) {
		this.strEiyono5 = _strEiyono5;
	}

	/**
	 * 割合1  ゲッター
	 * @return strWariai1 : 割合1の値を返す
	 */
	public String getStrWariai1() {
		return strWariai1;
	}
	/**
	 * 割合1 セッター
	 * @param _strWariai1 : 割合1の値を格納する
	 */
	public void setStrWariai1(String _strWariai1) {
		this.strWariai1 = _strWariai1;
	}

	/**
	 * 割合2  ゲッター
	 * @return strWariai2 : 割合2の値を返す
	 */
	public String getStrWariai2() {
		return strWariai2;
	}
	/**
	 * 割合2 セッター
	 * @param _strWariai2 : 割合2の値を格納する
	 */
	public void setStrWariai2(String _strWariai2) {
		this.strWariai2 = _strWariai2;
	}

	/**
	 * 割合3  ゲッター
	 * @return strWariai3 : 割合3の値を返す
	 */
	public String getStrWariai3() {
		return strWariai3;
	}
	/**
	 * 割合3 セッター
	 * @param _strWariai3 : 割合3の値を格納する
	 */
	public void setStrWariai3(String _strWariai3) {
		this.strWariai3 = _strWariai3;
	}

	/**
	 * 割合4  ゲッター
	 * @return strWariai4 : 割合4の値を返す
	 */
	public String getStrWariai4() {
		return strWariai4;
	}
	/**
	 * 割合4 セッター
	 * @param _strWariai4 : 割合4の値を格納する
	 */
	public void setStrWariai4(String _strWariai4) {
		this.strWariai4 = _strWariai4;
	}

	/**
	 * 割合5  ゲッター
	 * @return strWariai5 : 割合5の値を返す
	 */
	public String getStrWariai5() {
		return strWariai5;
	}
	/**
	 * 割合5 セッター
	 * @param _strWariai5 : 割合5の値を格納する
	 */
	public void setStrWariai5(String _strWariai5) {
		this.strWariai5 = _strWariai5;
	}

	/**
	 * メモ  ゲッター
	 * @return strMemo : メモの値を返す
	 */
	public String getStrMemo() {
		return strMemo;
	}
	/**
	 * メモ セッター
	 * @param _strMemo : メモの値を格納する
	 */
	public void setStrMemo(String _strMemo) {
		this.strMemo = _strMemo;
	}

	/**
	 * 入力日  ゲッター
	 * @return strNyurokuhi : 入力日の値を返す
	 */
	public String getStrNyurokuhi() {
		return strNyurokuhi;
	}
	/**
	 * 入力日 セッター
	 * @param _strNyurokuhi : 入力日の値を格納する
	 */
	public void setStrNyurokuhi(String _strNyurokuhi) {
		this.strNyurokuhi = _strNyurokuhi;
	}

	/**
	 * 入力者コード ゲッター
	 * @return dciNyurokucd : 入力者コードの値を返す
	 */
	public BigDecimal getDciNyurokucd() {
		return dciNyurokucd;
	}
	/**
	 * 入力者コード セッター
	 * @param _dciNyurokucd : 入力者コードの値を格納する
	 */
	public void setDciNyurokucd(BigDecimal _dciNyurokucd) {
		this.dciNyurokucd = _dciNyurokucd;
	}

	/**
	 * 入力者名 ゲッター
	 * @return strNyurokunm : 入力者名の値を返す
	 */
	public String getStrNyurokunm() {
		return strNyurokunm;
	}
	/**
	 * 入力者名 セッター
	 * @param _strNyurokunm : 入力者名の値を格納する
	 */
	public void setStrNyurokunm(String _strNyurokunm) {
		this.strNyurokunm = _strNyurokunm;
	}

	/**
	 * 分析情報確認フラグ ゲッター
	 * @return intKakunin : 分析情報確認フラグの値を返す
	 */
	public int getIntKakunin() {
		return intKakunin;
	}
	/**
	 * 分析情報確認フラグ セッター
	 * @param _intKakunin : 分析情報確認フラグの値を格納する
	 */
	public void setIntKakunin(int _intKakunin) {
		this.intKakunin = _intKakunin;
	}

	/**
	 * 確認日 ゲッター
	 * @return strKakuninhi : 確認日の値を返す
	 */
	public String getStrKakuninhi() {
		return strKakuninhi;
	}
	/**
	 * 確認日 セッター
	 * @param _strKakuninhi : 確認日の値を格納する
	 */
	public void setStrKakuninhi(String _strKakuninhi) {
		this.strKakuninhi = _strKakuninhi;
	}

	/**
	 * 確認者コード ゲッター
	 * @return dciKakunincd : 確認者コードの値を返す
	 */
	public BigDecimal getDciKakunincd() {
		return dciKakunincd;
	}
	/**
	 * 確認者コード セッター
	 * @param _dciKakunincd : 確認者コードの値を格納する
	 */
	public void setDciKakunincd(BigDecimal _dciKakunincd) {
		this.dciKakunincd = _dciKakunincd;
	}

	/**
	 * 確認者名 ゲッター
	 * @return strKakuninnm : 確認者名の値を返す
	 */
	public String getStrKakuninnm() {
		return strKakuninnm;
	}
	/**
	 * 確認者名 セッター
	 * @param _strKakuninnm : 確認者名の値を格納する
	 */
	public void setStrKakuninnm(String _strKakuninnm) {
		this.strKakuninnm = _strKakuninnm;
	}

	/**
	 * 廃止フラグ ゲッター
	 * @return intHaisicd : 廃止フラグの値を返す
	 */
	public int getIntHaisicd() {
		return intHaisicd;
	}
	/**
	 * 廃止フラグ セッター
	 * @param _intHaisicd : 廃止フラグの値を格納する
	 */
	public void setIntHaisicd(int _intHaisicd) {
		this.intHaisicd = _intHaisicd;
	}

	/**
	 * 確定コード ゲッター
	 * @return strkakuteicd : 確定コードの値を返す
	 */
	public String getStrkakuteicd() {
		return strkakuteicd;
	}
	/**
	 * 確定コード セッター
	 * @param _strkakuteicd : 確定コードの値を格納する
	 */
	public void setStrkakuteicd(String _strkakuteicd) {
		this.strkakuteicd = _strkakuteicd;
	}

	/**
	 * 最終購入日 ゲッター
	 * @return strKonyu : 最終購入日の値を返す
	 */
	public String getStrKonyu() {
		return strKonyu;
	}
	/**
	 * 最終購入日 セッター
	 * @param _strKonyu : 最終購入日の値を格納する
	 */
	public void setStrKonyu(String _strKonyu) {
		this.strKonyu = _strKonyu;
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
	 * 荷姿 ゲッター
	 * @return strNisugata : 荷姿の値を返す
	 */
	public String getStrNisugata() {
		return strNisugata;
	}
	/**
	 * 荷姿 セッター
	 * @param _strNisugata : 荷姿の値を格納する
	 */
	public void setStrNisugata(String _strNisugata) {
		this.strNisugata = _strNisugata;
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
	 * @return strTorokuDt : 登録日付の値を返す
	 */
	public String getStrTorokuDt() {
		return strTorokuDt;
	}
	/**
	 * 登録日付 セッター
	 * @param _strTorokuDt : 登録日付の値を格納する
	 */
	public void setStrTorokuDt(String _strTorokuDt) {
		this.strTorokuDt = _strTorokuDt;
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
	 * @return strKosinDt : 更新日付の値を返す
	 */
	public String getStrKosinDt() {
		return strKosinDt;
	}
	/**
	 * 更新日付 セッター
	 * @param _strKosinDt : 更新日付の値を格納する
	 */
	public void setStrKosinDt(String _strKosinDt) {
		this.strKosinDt = _strKosinDt;
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

	/**
	 * 表示最大件数 ゲッター
	 * @return intListRowMax : 表示最大件数の値を返す
	 */
	public int getIntListRowMax() {
		return intListRowMax;
	}
	/**
	 * 表示最大件数 セッター
	 * @param _intListRowMax : 表示最大件数の値を格納する
	 */
	public void setIntListRowMax(int _intListRowMax) {
		this.intListRowMax = _intListRowMax;
	}

	/**
	 * レコード件数 ゲッター
	 * @return intMaxRow : レコード件数の値を返す
	 */
	public int getIntMaxRow() {
		return intMaxRow;
	}
	/**
	 * レコード件数 セッター
	 * @param _intMaxRow : レコード件数の値を格納する
	 */
	public void setIntMaxRow(int _intMaxRow) {
		this.intMaxRow = _intMaxRow;
	}

	/**
	 * 分析値差異の有無 ゲッター
	 * @return intHankou : 分析値差異の有無の値を返す
	 */
	public int getIntHankou() {
		return intHankou;
	}
	/**
	 * 分析値差異の有無 セッター
	 * @param _intHankou : 分析値差異の有無の値を格納する
	 */
	public void setIntHankou(int _intHankou) {
		this.intHankou = _intHankou;
	}

	/**
	 * XMLデータ セッター
	 * @param _xdtData : XMLデータの値を格納する
	 */
	public void setXdtData(XmlData _xdtData) {
		this.xdtData = _xdtData;
	}
	/**
	 * XMLデータ ゲッター
	 * @return xdtData : XMLデータの値を返す
	 */
	public XmlData getXdtData() {
		return xdtData;
	}

//add start --------------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.4
	/**
	 * 使用実績フラグ ゲッター
	 * @return intShiyoFlg : 使用実績フラグの値を返す
	 */
	public int getIntShiyoFlg() {
		return intShiyoFlg;
	}
	/**
	 * 使用実績フラグ セッター
	 * @param _intShiyoFlg : 使用実績フラグの値を格納する
	 */
	public void setIntShiyoFlg(int _intShiyoFlg) {
		this.intShiyoFlg = _intShiyoFlg;
	}

	/**
	 *　未使用フラグ ゲッター
	 * @return intMishiyoFlg : 未使用フラグの値を返す
	 */
	public int getIntMishiyoFlg() {
		return intMishiyoFlg;
	}
	/**
	 * 未使用フラグ セッター
	 * @param _intMishiyoFlg : 未使用フラグの値を格納する
	 */
	public void setIntMishiyoFlg(int _intMishiyoFlg) {
		this.intMishiyoFlg = _intMishiyoFlg;
	}
//add end --------------------------------------------------------------------------------------

//add start --------------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.31
	public BigDecimal getMa_dciBudomari() {
		return ma_dciBudomari;
	}
	public void setMa_dciBudomari(BigDecimal ma_dciBudomari) {
		this.ma_dciBudomari = ma_dciBudomari;
	}
//add end --------------------------------------------------------------------------------------

	public void dispMateData(){
		//　会社コード
		System.out.println("会社コード：" + this.getIntKaishacd());
		//　部署コード
		System.out.println("部署コード：" + this.getIntBushocd());
		//　原料コード
		System.out.println("原料コード：" + this.getStrGenryocd());
		//　会社名
		System.out.println("会社名：" + this.getStrKaishanm());
		//　部署名
		System.out.println("部署名：" + this.getStrBushonm());
		//　原料名
		System.out.println("原料名：" + this.getStrGenryonm());
		//　酢酸
		System.out.println("酢酸：" + this.getDciSakusan());
		//　食塩
		System.out.println("食塩：" + this.getDciShokuen());
		//　総酸
		System.out.println("総酸：" + this.getDciSousan());
		//　油含有率
		System.out.println("油含有率：" + this.getDciGanyu());
		//　表示案
		System.out.println("表示案：" + this.getStrHyoji());
		//　添加物
		System.out.println("添加物：" + this.getStrTenka());
		//　栄養計算食品番号1
		System.out.println("栄養計算食品番号1：" + this.getStrEiyono1());
		//　栄養計算食品番号2
		System.out.println("栄養計算食品番号2：" + this.getStrEiyono2());
		//　栄養計算食品番号3
		System.out.println("栄養計算食品番号3：" + this.getStrEiyono3());
		//　栄養計算食品番号4
		System.out.println("栄養計算食品番号4：" + this.getStrEiyono4());
		//　栄養計算食品番号5
		System.out.println("栄養計算食品番号5：" + this.getStrEiyono5());
		//　割合1
		System.out.println("割合1：" + this.getStrWariai1());
		//　割合2
		System.out.println("割合2：" + this.getStrWariai2());
		//　割合3
		System.out.println("割合3：" + this.getStrWariai3());
		//　割合4
		System.out.println("割合4：" + this.getStrWariai4());
		//　割合5
		System.out.println("割合5：" + this.getStrWariai5());
		//　メモ
		System.out.println("メモ：" + this.getStrMemo());
		//　入力日
		System.out.println("入力日：" + this.getStrNyurokuhi());
		//　入力者ID
		System.out.println("入力者ID：" + this.getDciNyurokucd());
		//　入力者名
		System.out.println("入力者名：" + this.getStrNyurokunm());
		//　確認日
		System.out.println("確認日：" + this.getStrKakuninhi());
		//　確認者ID
		System.out.println("確認者ID：" + this.getDciKakunincd());
		//　確認者名
		System.out.println("確認者名：" + this.getStrKakuninnm());
		//　廃止区分
		System.out.println("廃止区分：" + this.getIntHaisicd());
		//　確定コード
		System.out.println("確定コード：" + this.getStrkakuteicd());
		//　最終購入日
		System.out.println("最終購入日：" + this.getStrKonyu());
		//　単価
		System.out.println("単価：" + this.getDciTanka());
		//　歩留
		System.out.println("歩留：" + this.getDciBudomari());
		//　荷姿
		System.out.println("荷姿：" + this.getStrNisugata());
		//　登録者ID
		System.out.println("登録者ID：" + this.getDciTorokuId());
		//　登録日付
		System.out.println("登録日付：" + this.getStrTorokuDt());
		//　登録者名
		System.out.println("登録者名：" + this.getStrTorokuNm());
		//　更新者ID
		System.out.println("更新者ID：" + this.getDciKosinId());
		//　更新日付
		System.out.println("更新日付：" + this.getStrKosinDt());
		//　更新者名
		System.out.println("更新者名：" + this.getStrKosinNm());
//add start --------------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.4
		//　使用実績フラグ
		System.out.println("使用実績フラグ：" + this.getIntShiyoFlg());
		//　未使用フラグ
		System.out.println("未使用フラグ：" + this.getIntMishiyoFlg());
//add end --------------------------------------------------------------------------------------
	}

}
