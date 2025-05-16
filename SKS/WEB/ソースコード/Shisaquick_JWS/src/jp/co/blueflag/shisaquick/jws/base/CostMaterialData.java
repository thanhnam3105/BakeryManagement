package jp.co.blueflag.shisaquick.jws.base;

import java.math.BigDecimal;
import jp.co.blueflag.shisaquick.jws.common.*;

/**
 * 
 * 原価原料データ保持
 *
 */
public class CostMaterialData extends DataBase {

	private BigDecimal dciShisakuUser;			//試作CD-社員CD
	private BigDecimal dciShisakuYear;			//試作CD-年
	private BigDecimal dciShisakuNum;			//試作CD-追番
	private int intShisakuSeq;				//試作SEQ
	private int intInsatu;					//印刷flg
	private String strZyutenSui;			//重点量水相
	private String strZyutenYu;			//重点量油相
	private String strGokei;					//合計量
	private String strGenryohi;				//原料費
	private String strGenryohiTan;		//原料費（1本）
	private String strHizyu;					//比重
	private String strYoryo;					//容量
	private String strIrisu;					//入数
	private String strYukoBudomari;		//有効歩留
	private String strLevel;					//レベル量
	private String strHizyuBudomari;		//比重歩留
	private String strZyutenAve;			//平均充填量
	private String strGenryohiCs;			//1C/S原料費
	private String strZairyohiCs;			//1C/S材料費
	private String strKeihiCs;				//1C/S経費
	private String strGenkakeiCs;			//1C/S原価計
	private String strGenkakeiTan;		//1個原価計
	private String strGenkakeiBai;		//1個売価
	private String strGenkakeiRi;			//1個粗利率
	private BigDecimal dciTorokuId;					//登録者ID
	private String strTorokuHi;				//登録日付
	private BigDecimal dciKosinId;					//更新者ID
	private String strKosinHi;				//更新日付
	private String strTorokuNm;				//登録者名
	private String strKosinNm;				//更新者名
//	private ExceptionBase ex;				//エラーハンドリング
		
	/**
	 * コンストラクタ
	 * @param xdtSetXml : XMLデータ
	 */
	public CostMaterialData() {
		//スーパークラスのコンストラクタ
		super();
		
		this.dciShisakuUser = null;
		this.dciShisakuYear = null;
		this.dciShisakuNum = null;
		this.intShisakuSeq = 0;
		this.intInsatu = 0;
		this.strZyutenSui = null;
		this.strZyutenYu = null;
		this.strGokei = null;
		this.strGenryohi = null;
		this.strGenryohiTan = null;
		this.strHizyu = null;
		this.strYoryo = null;
		this.strIrisu = null;
		this.strYukoBudomari = null;
		this.strLevel = null;
		this.strHizyuBudomari = null;
		this.strZyutenAve = null;
		this.strGenryohiCs = null;
		this.strZairyohiCs = null;
		this.strKeihiCs = null;
		this.strGenkakeiCs = null;
		this.strGenkakeiTan = null;
		this.strGenkakeiBai = null;
		this.strGenkakeiRi = null;
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
	 * 印刷flg ゲッター
	 * @return intinsatu : 印刷flgの値を返す
	 */
	public int getIntinsatu() {
		return intInsatu;
	}
	/**
	 * 印刷flg セッター
	 * @param _intinsatu : 印刷flgの値を格納する
	 */
	public void setIntinsatu(int _intinsatu) {
		this.intInsatu = _intinsatu;
	}

	/**
	 * 重点量水相 ゲッター
	 * @return strZyutenSui : 重点量水相の値を返す
	 */
	public String getStrZyutenSui() {
		return strZyutenSui;
	}
	/**
	 * 重点量水相 セッター
	 * @param _strZyutenSui : 重点量水相の値を格納する
	 */
	public void setStrZyutenSui(String _strZyutenSui) {
		this.strZyutenSui = _strZyutenSui;
	}

	/**
	 * 重点量油相 ゲッター
	 * @return strZyutenYu : 重点量油相の値を返す
	 */
	public String getStrZyutenYu() {
		return strZyutenYu;
	}
	/**
	 * 重点量油相 セッター
	 * @param _strZyutenYu : 重点量油相の値を格納する
	 */
	public void setStrZyutenYu(String _strZyutenYu) {
		this.strZyutenYu = _strZyutenYu;
	}

	/**
	 * 合計量 ゲッター
	 * @return strGokei : 合計量の値を返す
	 */
	public String getStrGokei() {
		return strGokei;
	}
	/**
	 * 合計量 セッター
	 * @param _strGokei : 合計量の値を格納する
	 */
	public void setStrGokei(String _strGokei) {
		this.strGokei = _strGokei;
	}

	/**
	 * 原料費 ゲッター
	 * @return strGenryohi : 原料費の値を返す
	 */
	public String getStrGenryohi() {
		return strGenryohi;
	}
	/**
	 * 原料費 セッター
	 * @param _strGenryohi : 原料費の値を格納する
	 */
	public void setStrGenryohi(String _strGenryohi) {
		this.strGenryohi = _strGenryohi;
	}

	/**
	 * 原料費（1本） ゲッター
	 * @return strGenryohiTan : 原料費（1本）の値を返す
	 */
	public String getStrGenryohiTan() {
		return strGenryohiTan;
	}
	/**
	 * 原料費（1本） セッター
	 * @param _strGenryohiTan : 原料費（1本）の値を格納する
	 */
	public void setStrGenryohiTan(String _strGenryohiTan) {
		this.strGenryohiTan = _strGenryohiTan;
	}

	/**
	 * 比重 ゲッター
	 * @return strHizyu : 比重の値を返す
	 */
	public String getStrHizyu() {
		return strHizyu;
	}
	/**
	 * 比重 セッター
	 * @param _strHizyu : 比重の値を格納する
	 */
	public void setStrHizyu(String _strHizyu) {
		this.strHizyu = _strHizyu;
	}

	/**
	 * 容量 ゲッター
	 * @return strYoryo : 容量の値を返す
	 */
	public String getStrYoryo() {
		return strYoryo;
	}
	/**
	 * 容量 セッター
	 * @param _strYoryo : 容量の値を格納する
	 */
	public void setStrYoryo(String _strYoryo) {
		this.strYoryo = _strYoryo;
	}

	/**
	 * 入数 ゲッター
	 * @return strIrisu : 入数の値を返す
	 */
	public String getStrIrisu() {
		return strIrisu;
	}
	/**
	 * 入数 セッター
	 * @param _strIrisu : 入数の値を格納する
	 */
	public void setStrIrisu(String _strIrisu) {
		this.strIrisu = _strIrisu;
	}

	/**
	 * 有効歩留 ゲッター
	 * @return strYukoBudomari : 有効歩留の値を返す
	 */
	public String getStrYukoBudomari() {
		return strYukoBudomari;
	}
	/**
	 * 有効歩留 セッター
	 * @param _strYukoBudomari : 有効歩留の値を格納する
	 */
	public void setStrYukoBudomari(String _strYukoBudomari) {
		this.strYukoBudomari = _strYukoBudomari;
	}

	/**
	 * レベル量 ゲッター
	 * @return strLevel : レベル量の値を返す
	 */
	public String getStrLevel() {
		return strLevel;
	}
	/**
	 * レベル量 セッター
	 * @param _strLevel : レベル量の値を格納する
	 */
	public void setStrLevel(String _strLevel) {
		this.strLevel = _strLevel;
	}

	/**
	 * 比重歩留 ゲッター
	 * @return strHizyuBudomari : 比重歩留の値を返す
	 */
	public String getStrHizyuBudomari() {
		return strHizyuBudomari;
	}
	/**
	 * 比重歩留 セッター
	 * @param _strHizyuBudomari : 比重歩留の値を格納する
	 */
	public void setStrHizyuBudomari(String _strHizyuBudomari) {
		this.strHizyuBudomari = _strHizyuBudomari;
	}

	/**
	 * 平均充填量 ゲッター
	 * @return strZyutenAve : 平均充填量の値を返す
	 */
	public String getStrZyutenAve() {
		return strZyutenAve;
	}
	/**
	 * 平均充填量 セッター
	 * @param _strZyutenAve : 平均充填量の値を格納する
	 */
	public void setStrZyutenAve(String _strZyutenAve) {
		this.strZyutenAve = _strZyutenAve;
	}

	/**
	 * 1C/S原料費 ゲッター
	 * @return strGenryohiCs : 1C/S原料費の値を返す
	 */
	public String getStrGenryohiCs() {
		return strGenryohiCs;
	}
	/**
	 * 1C/S原料費 セッター
	 * @param _strGenryohiCs : 1C/S原料費の値を格納する
	 */
	public void setStrGenryohiCs(String _strGenryohiCs) {
		this.strGenryohiCs = _strGenryohiCs;
	}

	/**
	 * 1C/S材料費 ゲッター
	 * @return strZairyohiCs : 1C/S材料費の値を返す
	 */
	public String getStrZairyohiCs() {
		return strZairyohiCs;
	}
	/**
	 * 1C/S材料費 セッター
	 * @param _strZairyohiCs : 1C/S材料費の値を格納する
	 */
	public void setStrZairyohiCs(String _strZairyohiCs) {
		this.strZairyohiCs = _strZairyohiCs;
	}

	/**
	 * 1C/S経費 ゲッター
	 * @return strKeihiCs : 1C/S経費の値を返す
	 */
	public String getStrKeihiCs() {
		return strKeihiCs;
	}
	/**
	 * 1C/S経費 セッター
	 * @param _strKeihiCs : 1C/S経費の値を格納する
	 */
	public void setStrKeihiCs(String _strKeihiCs) {
		this.strKeihiCs = _strKeihiCs;
	}

	/**
	 * 1C/S原価計 ゲッター
	 * @return strGenkakeiCs : 1C/S原価計の値を返す
	 */
	public String getStrGenkakeiCs() {
		return strGenkakeiCs;
	}
	/**
	 * 1C/S原価計 セッター
	 * @param _strGenkakeiCs : 1C/S原価計の値を格納する
	 */
	public void setStrGenkakeiCs(String _strGenkakeiCs) {
		this.strGenkakeiCs = _strGenkakeiCs;
	}

	/**
	 * 1個原価計 ゲッター
	 * @return strGenkakeiTan : 1個原価計の値を返す
	 */
	public String getStrGenkakeiTan() {
		return strGenkakeiTan;
	}
	/**
	 * 1個原価計 セッター
	 * @param _strGenkakeiTan : 1個原価計の値を格納する
	 */
	public void setStrGenkakeiTan(String _strGenkakeiTan) {
		this.strGenkakeiTan = _strGenkakeiTan;
	}

	/**
	 * 1個売価 ゲッター
	 * @return strGenkakeiBai : 1個売価の値を返す
	 */
	public String getStrGenkakeiBai() {
		return strGenkakeiBai;
	}
	/**
	 * 1個売価 セッター
	 * @param _strGenkakeiBai : 1個売価の値を格納する
	 */
	public void setStrGenkakeiBai(String _strGenkakeiBai) {
		this.strGenkakeiBai = _strGenkakeiBai;
	}

	/**
	 * 1個粗利率 ゲッター
	 * @return strGenkakeiRi : 1個粗利率の値を返す
	 */
	public String getStrGenkakeiRi() {
		return strGenkakeiRi;
	}
	/**
	 * 1個粗利率 セッター
	 * @param _strGenkakeiRi : 1個粗利率の値を格納する
	 */
	public void setStrGenkakeiRi(String _strGenkakeiRi) {
		this.strGenkakeiRi = _strGenkakeiRi;
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
