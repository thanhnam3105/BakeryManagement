package jp.co.blueflag.shisaquick.jws.base;

import java.math.BigDecimal;

import jp.co.blueflag.shisaquick.jws.common.*;

/**
 *
 * 試作データ保持
 *  : 試作データの管理を行う
 *
 */
public class TrialData extends DataBase {

	private BigDecimal dciShisakuUser;		//試作CD-社員CD
	private BigDecimal dciShisakuYear;		//試作CD-年
	private BigDecimal dciShisakuNum;		//試作CD-追番
	private int intShisakuSeq;					//試作SEQ
	private int intHyojiNo;						//試作表示順
	private String strTyuiNo;					//注意事項NO
	private String strSampleNo;				//サンプルNO（名称）
	private String strMemo;						//メモ
	private int intInsatuFlg;					//印刷Flg
	private int intZidoKei;						//自動計算Flg
	private int intGenkaShisan;				//原価試算No
	private String strSeihoNo1;				//製法No-1
	private String strSeihoNo2;				//製法No-2
	private String strSeihoNo3;				//製法No-3
	private String strSeihoNo4;				//製法No-4
	private String strSeihoNo5;				//製法No-5
	private BigDecimal dciSosan;				//総酸
	private int intSosanFlg;					//総酸-出力Flg
	private BigDecimal dciShokuen;			//食塩
	private int intShokuenFlg;					//食塩-出力Flg
	private BigDecimal dciSuiSando;			//水相中酸度
	private int intSuiSandoFlg;				//水相中酸度-出力Flg
	private BigDecimal dciSuiShokuen;		//水相中食塩
	private int intSuiShokuenFlg;				//水相中食塩-出力Flg
	private BigDecimal dciSuiSakusan;		//水相中酢酸
	private int intSuiSakusanFlg;				//水相中酢酸-出力Flg
	private String strToudo;						//糖度
	private int intToudoFlg;					//糖度-出力Flg
	private String strNendo;					//粘度
	private int intNendoFlg;					//粘度-出力Flg
	private String strOndo;						//温度
	private int intOndoFlg;						//温度-出力Flg
	private String strPh;							//PH
	private int intPhFlg;							//PH - 出力Flg
	private String strSosanBunseki;			//総酸：分析
	private int intSosanBunsekiFlg;			//総酸：分析-出力Flg
	private String strShokuenBunseki;		//食塩：分析
	private int intShokuenBunsekiFlg;		//食塩：分析-出力Flg
	private String strHizyu;						//比重
	private int intHizyuFlg;						//比重-出力Flg
	private String strSuibun;					//水分活性
	private int intSuibunFlg;					//水分活性-出力Flg
	private String strArukoru;					//アルコール
	private int intArukoruFlg;					//アルコール-出力Flg
	private String strSakuseiMemo;			//作成メモ
	private int intSakuseiMemoFlg;			//作成メモ-出力Flg
	private String strHyoka;					//評価
	private int intHyokaFlg;					//評価-出力Flg
	private String strFreeTitle1;				//フリー①タイトル
	private String strFreeNaiyo1;				//フリー①内容
	private int intFreeFlg;						//フリー①-出力Flg
	private String strFreeTitle2;				//フリー②タイトル
	private String strFreeNaiyo2;				//フリー②内容
	private int intFreeFl2;						//フリー②-出力Flg
	private String strFreeTitle3;				//フリー③タイトル
	private String strFreeNaiyo3;				//フリー③内容
	private int intFreeFl3;						//フリー③-出力Flg
	private String strShisakuHi;				//試作日付
	private BigDecimal dciShiagari;			//仕上重量
	private BigDecimal dciTorokuId;			//登録者ID
	private String strTorokuHi;					//登録日付
	private BigDecimal dciKosinId;			//更新者ID
	private String strkosinHi;					//更新日付
	private String strTorokuNm;				//登録者名
	private String strKosinNm;					//更新者名
// ADD start 20120928 QP@20505 No.24
	private BigDecimal dciMsg;					// ＭＳＧ
	private String strFreeTitleNendo;			//粘度フリータイトル
	private String strFreeNendo;				//粘度フリー内容
	private int intFreeNendoFlg;				//粘度フリー出力flg
	private String strFreeTitleOndo;			//温度フリータイトル
	private String strFreeOndo;					//温度フリー内容
	private int intFreeOndoFlg;					//温度フリー出力flg
	private String strFreeTitleSuibunKasei;		//水分活性フリータイトル
	private String strFreeSuibunKasei;			//水分活性フリー内容
	private int intFreeSuibunKaseiFlg;			//水分活性－出力flg
	private String strFreeTitleAlchol;			//アルコールフリータイトル
	private String strFreeAlchol;				//アルコールフリー内容
	private int intFreeAlcholFlg;				//アルコールフリー出力flg
	private BigDecimal dciJikkoSakusanNodo;			//実効酢酸濃度
	private int intJikkoSakusanNodoFlg;			//実効酢酸濃度－出力flg
	private BigDecimal dciSuisoMSG;					//水相中ＭＳＧ
	private int intSuisoMSGFlg;					//水相中ＭＳＧ－出力flg
	private String strFreeTitle4;				//フリー④タイトル
	private String strFreeNaiyo4;				//フリー④内容
	private int intFreeFlg4;					//フリー④-出力Flg
	private String strFreeTitle5;				//フリー⑤タイトル
	private String strFreeNaiyo5;				//フリー⑤内容
	private int intFreeFlg5;						//フリー⑤-出力Flg
	private String strFreeTitle6;				//フリー⑥タイトル
	private String strFreeNaiyo6;				//フリー⑥内容
	private int intFreeFlg6;						//フリー⑥-出力Flg
// ADD end 20120928 QP@20505 No.24

//2009/10/26 TT.Y.NISHIGAWA ADD START [原価試算：試算依頼flg]
	private int flg_shisanIrai;               	//原価試算依頼フラグ
	private int flg_init;                       	//既存依頼データフラグ[1:既に依頼されているデータ]
//2009/10/26 TT.Y.NISHIGAWA ADD END [原価試算：試算依頼flg]

//add start -------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.7
	private String strKeisanSiki;				//計算式
//add end   -------------------------------------------------------------------------------

//2011/04/20 QP@10181_No.67 TT Nishigawa Change Start -------------------------
	private int flg_cancel;						//依頼キャンセルフラグ
//2011/04/20 QP@10181_No.67 TT Nishigawa Change End ---------------------------

//2011/05/30　【QP@10181_No.42_49_72】工程パターン制御　TT.NISHIGAWA　start
	private String strHiju_sui;						//水相比重
	private int intHiju_sui_fg;						//水相比重　出力FG
//2011/05/30　【QP@10181_No.42_49_72】工程パターン制御　TT.NISHIGAWA　end

	/**
	 * コンストラクタ
	 * @param xdtSetXml : XMLデータ
	 */
	public TrialData() {
		//スーパークラスのコンストラクタ
		super();

		this.dciShisakuUser = null;
		this.dciShisakuYear = null;
		this.dciShisakuNum = null;
		this.intShisakuSeq = 0;
		this.intHyojiNo = 0;
		this.strTyuiNo = null;
		this.strSampleNo = null;
		this.strMemo = null;
		this.intInsatuFlg = 0;
		this.intZidoKei = 0;
		this.intGenkaShisan = 0;
		this.strSeihoNo1 = null;
		this.strSeihoNo2 = null;
		this.strSeihoNo3 = null;
		this.strSeihoNo4 = null;
		this.strSeihoNo5 = null;
		this.dciSosan = null;
		this.intSosanFlg = 0;
		this.dciShokuen = null;
		this.intShokuenFlg = 0;
		this.dciSuiSando = null;
		this.intSuiSandoFlg = 0;
		this.dciSuiShokuen = null;
		this.intSuiShokuenFlg = 0;
		this.dciSuiSakusan = null;
		this.intSuiSakusanFlg = 0;
		this.strToudo = null;
		this.intToudoFlg = 0;
		this.strNendo = null;
		this.intNendoFlg = 0;
		this.strOndo = null;
		this.intOndoFlg = 0;
		this.strPh = null;
		this.intPhFlg = 0;
		this.strSosanBunseki = null;
		this.intSosanBunsekiFlg = 0;
		this.strShokuenBunseki = null;
		this.intShokuenBunsekiFlg = 0;
		this.strHizyu = null;
		this.intHizyuFlg = 0;
		this.strSuibun = null;
		this.intSuibunFlg = 0;
		this.strArukoru = null;
		this.intArukoruFlg = 0;
		this.strSakuseiMemo = null;
		this.intSakuseiMemoFlg = 0;
		this.strHyoka = null;
		this.intHyokaFlg = 0;
		this.strFreeTitle1 = null;
		this.strFreeNaiyo1 = null;
		this.intFreeFlg = 0;
		this.strFreeTitle2 = null;
		this.strFreeNaiyo2 = null;
		this.intFreeFl2 = 0;
		this.strFreeTitle3 = null;
		this.strFreeNaiyo3 = null;
		this.intFreeFl3 = 0;
		this.strShisakuHi = null;
		this.dciShiagari = null;
		this.dciTorokuId = null;
		this.strTorokuHi = null;
		this.dciKosinId = null;
		this.strkosinHi = null;
//2009/10/26 TT.Y.NISHIGAWA ADD START [原価試算：試算依頼flg]
		this.flg_shisanIrai = 0;
		this.flg_init = 0;
//2009/10/26 TT.Y.NISHIGAWA ADD END [原価試算：試算依頼flg]
//2011/04/20 QP@10181_No.67 TT Nishigawa Change Start -------------------------
		this.flg_cancel = 0;
//2011/04/20 QP@10181_No.67 TT Nishigawa Change End --------------------------
//2011/05/30　【QP@10181_No.42_49_72】工程パターン制御　TT.NISHIGAWA　start
		this.strHiju_sui = null;
		this.intHiju_sui_fg = 0;
//2011/05/30　【QP@10181_No.42_49_72】工程パターン制御　TT.NISHIGAWA　end
// ADD start 20120928 QP@20505 No.24
		this.dciMsg = null;
		this.strFreeTitleNendo = null;
		this.strFreeNendo = null;
		this.intFreeNendoFlg = 0;
		this.strFreeTitleOndo = null;
		this.strFreeOndo = null;
		this.intFreeOndoFlg = 0;
		this.strFreeTitleSuibunKasei = null;
		this.strFreeSuibunKasei = null;
		this.intFreeSuibunKaseiFlg = 0;
		this.strFreeTitleAlchol = null;
		this.strFreeAlchol = null;
		this.intFreeAlcholFlg = 0;
		this.dciJikkoSakusanNodo = null;
		this.intJikkoSakusanNodoFlg = 0;
		this.dciSuisoMSG = null;
		this.intSuisoMSGFlg = 0;
		this.strFreeTitle4 = null;
		this.strFreeNaiyo4 = null;
		this.intFreeFlg4 = 0;
		this.strFreeTitle5 = null;
		this.strFreeNaiyo5 = null;
		this.intFreeFlg5 = 0;
		this.strFreeTitle6 = null;
		this.strFreeNaiyo6 = null;
		this.intFreeFlg6 = 0;
// ADD end 20120928 QP@20505 No.24
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
	 * @param intShisakuSeq : 試作SEQの値を格納する
	 */
	public void setIntShisakuSeq(int _intShisakuSeq) {
		this.intShisakuSeq = _intShisakuSeq;
	}

	/**
	 * 試作表示順 ゲッター
	 * @return intHyojiNo : 試作表示順の値を返す
	 */
	public int getIntHyojiNo() {
		return intHyojiNo;
	}
	/**
	 * 試作表示順 セッター
	 * @param intHyojiNo : 試作表示順の値を格納する
	 */
	public void setIntHyojiNo(int _intHyojiNo) {
		this.intHyojiNo = _intHyojiNo;
	}

	/**
	 * 注意事項NO ゲッター
	 * @return strTyuiNo : 注意事項NOの値を返す
	 */
	public String getStrTyuiNo() {
		return strTyuiNo;
	}
	/**
	 * 注意事項NO セッター
	 * @param _strTyuiNo : 注意事項NOの値を格納する
	 */
	public void setStrTyuiNo(String _strTyuiNo) {
		this.strTyuiNo = _strTyuiNo;
	}

	/**
	 * サンプルNO（名称） ゲッター
	 * @return strSampleNo : サンプルNO（名称）の値を返す
	 */
	public String getStrSampleNo() {
		return strSampleNo;
	}
	/**
	 * サンプルNO（名称） セッター
	 * @param _strSampleNo : サンプルNO（名称）の値を格納する
	 */
	public void setStrSampleNo(String _strSampleNo) {
		this.strSampleNo = _strSampleNo;
	}

	/**
	 * メモ ゲッター
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
	 * 印刷Flg ゲッター
	 * @return intInsatuFlg : 印刷Flgの値を返す
	 */
	public int getIntInsatuFlg() {
		return intInsatuFlg;
	}
	/**
	 * 印刷Flg セッター
	 * @param _intInsatuFlg : 印刷Flgの値を格納する
	 */
	public void setIntInsatuFlg(int _intInsatuFlg) {
		this.intInsatuFlg = _intInsatuFlg;
	}

	/**
	 * 自動計算Flg ゲッター
	 * @return intZidoKei : 自動計算Flgの値を返す
	 */
	public int getIntZidoKei() {
		return intZidoKei;
	}
	/**
	 * 自動計算Flg セッター
	 * @param intZidoKei : 自動計算Flgの値を格納する
	 */
	public void setIntZidoKei(int _intZidoKei) {
		this.intZidoKei = _intZidoKei;
	}

	/**
	 * 原価試算No ゲッター
	 * @return intGenkaShisan : 原価試算Noの値を返す
	 */
	public int getIntGenkaShisan() {
		return intGenkaShisan;
	}
	/**
	 * 原価試算No セッター
	 * @param _intGenkaShisan : 原価試算Noの値を格納する
	 */
	public void setIntGenkaShisan(int _intGenkaShisan) {
		this.intGenkaShisan = _intGenkaShisan;
	}

	/**
	 * 製法No-1 ゲッター
	 * @return strSeihoNo1 : 製法No-1の値を返す
	 */
	public String getStrSeihoNo1() {
		return strSeihoNo1;
	}
	/**
	 * 製法No-1 セッター
	 * @param _strSeihoNo1 : 製法No-1の値を格納する
	 */
	public void setStrSeihoNo1(String _strSeihoNo1) {
		this.strSeihoNo1 = _strSeihoNo1;
	}

	/**
	 * 製法No-2 ゲッター
	 * @return strSeihoNo2 : 製法No-2の値を返す
	 */
	public String getStrSeihoNo2() {
		return strSeihoNo2;
	}
	/**
	 * 製法No-2 セッター
	 * @param _strSeihoNo2 : 製法No-2の値を格納する
	 */
	public void setStrSeihoNo2(String _strSeihoNo2) {
		this.strSeihoNo2 = _strSeihoNo2;
	}

	/**
	 * 製法No-3 ゲッター
	 * @return strSeihoNo3 : 製法No-3の値を返す
	 */
	public String getStrSeihoNo3() {
		return strSeihoNo3;
	}
	/**
	 * 製法No-3 セッター
	 * @param _strSeihoNo3 : 製法No-3の値を格納する
	 */
	public void setStrSeihoNo3(String _strSeihoNo3) {
		this.strSeihoNo3 = _strSeihoNo3;
	}

	/**
	 * 製法No-4 ゲッター
	 * @return strSeihoNo4 : 製法No-4の値を返す
	 */
	public String getStrSeihoNo4() {
		return strSeihoNo4;
	}
	/**
	 * 製法No-4 セッター
	 * @param _strSeihoNo4 : 製法No-4の値を格納する
	 */
	public void setStrSeihoNo4(String _strSeihoNo4) {
		this.strSeihoNo4 = _strSeihoNo4;
	}

	/**
	 * 製法No-5 ゲッター
	 * @return strSeihoNo5 : 製法No-5の値を返す
	 */
	public String getStrSeihoNo5() {
		return strSeihoNo5;
	}
	/**
	 * 製法No-5 セッター
	 * @param _strSeihoNo5 : 製法No-5の値を格納する
	 */
	public void setStrSeihoNo5(String _strSeihoNo5) {
		this.strSeihoNo5 = _strSeihoNo5;
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
	 * 総酸-出力Flg ゲッター
	 * @return intSosanFlg : 総酸-出力Flgの値を返す
	 */
	public int getIntSosanFlg() {
		return intSosanFlg;
	}
	/**
	 * 総酸-出力Flg セッター
	 * @param _intSosanFlg : 総酸-出力Flgの値を格納する
	 */
	public void setIntSosanFlg(int _intSosanFlg) {
		this.intSosanFlg = _intSosanFlg;
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
// ADD start 20121002 QP@20505 No.24
	/**
	 * ＭＳＧ ゲッター
	 * @return dciMsg : ＭＳＧの値を返す
	 */
	public BigDecimal getDciMsg() {
		return dciMsg;
	}
	/**
	 * ＭＳＧ セッター
	 * @param _dciMsg : ＭＳＧの値を格納する
	 */
	public void setDciMsg(BigDecimal _dciMsg) {
		this.dciMsg = _dciMsg;
	}
// ADD end 20121002 QP@20505 No.24

	/**
	 * 食塩-出力Flg ゲッター
	 * @return intShokuenFlg : 食塩-出力Flgの値を返す
	 */
	public int getIntShokuenFlg() {
		return intShokuenFlg;
	}
	/**
	 * 食塩-出力Flg セッター
	 * @param _intShokuenFlg : 食塩-出力Flgの値を格納する
	 */
	public void setIntShokuenFlg(int _intShokuenFlg) {
		this.intShokuenFlg = _intShokuenFlg;
	}

	/**
	 * 水相中酸度 ゲッター
	 * @return dciSuiSando : 水相中酸度の値を返す
	 */
	public BigDecimal getDciSuiSando() {
		return dciSuiSando;
	}
	/**
	 * 水相中酸度 セッター
	 * @param _dciSuiSando : 水相中酸度の値を格納する
	 */
	public void setDciSuiSando(BigDecimal _dciSuiSando) {
		this.dciSuiSando = _dciSuiSando;
	}

	/**
	 * 水相中酸度-出力Flg ゲッター
	 * @return intSuiSandoFlg : 水相中酸度-出力Flgの値を返す
	 */
	public int getIntSuiSandoFlg() {
		return intSuiSandoFlg;
	}
	/**
	 * 水相中酸度-出力Flg セッター
	 * @param _intSuiSandoFlg : 水相中酸度-出力Flgの値を格納する
	 */
	public void setIntSuiSandoFlg(int _intSuiSandoFlg) {
		this.intSuiSandoFlg = _intSuiSandoFlg;
	}

	/**
	 * 水相中食塩 ゲッター
	 * @return dciSuiShokuen : 水相中食塩の値を返す
	 */
	public BigDecimal getDciSuiShokuen() {
		return dciSuiShokuen;
	}
	/**
	 * 水相中食塩 セッター
	 * @param _dciSuiShokuen : 水相中食塩の値を格納する
	 */
	public void setDciSuiShokuen(BigDecimal _dciSuiShokuen) {
		this.dciSuiShokuen = _dciSuiShokuen;
	}

	/**
	 * 水相中食塩-出力Flg ゲッター
	 * @return intSuiShokuenFlg : 水相中食塩-出力Flgの値を返す
	 */
	public int getIntSuiShokuenFlg() {
		return intSuiShokuenFlg;
	}
	/**
	 * 水相中食塩-出力Flg セッター
	 * @param _intSuiShokuenFlg : 水相中食塩-出力Flgの値を格納する
	 */
	public void setIntSuiShokuenFlg(int _intSuiShokuenFlg) {
		this.intSuiShokuenFlg = _intSuiShokuenFlg;
	}

	/**
	 * 水相中酢酸 ゲッター
	 * @return dciSuiSakusan : 水相中酢酸の値を返す
	 */
	public BigDecimal getDciSuiSakusan() {
		return dciSuiSakusan;
	}
	/**
	 * 水相中酢酸 セッター
	 * @param _dciSuiSakusan : 水相中酢酸の値を格納する
	 */
	public void setDciSuiSakusan(BigDecimal _dciSuiSakusan) {
		this.dciSuiSakusan = _dciSuiSakusan;
	}

	/**
	 * 水相中酢酸-出力Flg ゲッター
	 * @return intSuiSakusanFlg : 水相中酢酸-出力Flgの値を返す
	 */
	public int getIntSuiSakusanFlg() {
		return intSuiSakusanFlg;
	}
	/**
	 * 水相中酢酸-出力Flg セッター
	 * @param _intSuiSakusanFlg : 水相中酢酸-出力Flgの値を格納する
	 */
	public void setIntSuiSakusanFlg(int _intSuiSakusanFlg) {
		this.intSuiSakusanFlg = _intSuiSakusanFlg;
	}

	/**
	 * 糖度 ゲッター
	 * @return strToudo : 糖度の値を返す
	 */
	public String getStrToudo() {
		return strToudo;
	}
	/**
	 * 糖度 セッター
	 * @param _strToudo : 糖度の値を格納する
	 */
	public void setStrToudo(String _strToudo) {
		this.strToudo = _strToudo;
	}

	/**
	 * 糖度-出力Flg ゲッター
	 * @return intToudoFlg : 糖度-出力Flgの値を返す
	 */
	public int getIntToudoFlg() {
		return intToudoFlg;
	}
	/**
	 * 糖度-出力Flg セッター
	 * @param _intToudoFlg : 糖度-出力Flgの値を格納する
	 */
	public void setIntToudoFlg(int _intToudoFlg) {
		this.intToudoFlg = _intToudoFlg;
	}

	/**
	 * 粘度 ゲッター
	 * @return strNendo : 粘度の値を返す
	 */
	public String getStrNendo() {
		return strNendo;
	}
	/**
	 * 粘度 セッター
	 * @param _strNendo : 粘度の値を格納する
	 */
	public void setStrNendo(String _strNendo) {
		this.strNendo = _strNendo;
	}

	/**
	 * 粘度-出力Flg ゲッター
	 * @return intNendoFlg : 粘度-出力Flgの値を返す
	 */
	public int getIntNendoFlg() {
		return intNendoFlg;
	}
	/**
	 * 粘度-出力Flg セッター
	 * @param _intNendoFlg : 粘度-出力Flgの値を格納する
	 */
	public void setIntNendoFlg(int _intNendoFlg) {
		this.intNendoFlg = _intNendoFlg;
	}

	/**
	 * 温度 ゲッター
	 * @return strOndo : 温度の値を返す
	 */
	public String getStrOndo() {
		return strOndo;
	}
	/**
	 * 温度 セッター
	 * @param _strOndo : 温度の値を格納する
	 */
	public void setStrOndo(String _strOndo) {
		this.strOndo = _strOndo;
	}

	/**
	 * 温度-出力Flg ゲッター
	 * @return intOndoFlg : 温度-出力Flgの値を返す
	 */
	public int getIntOndoFlg() {
		return intOndoFlg;
	}
	/**
	 * 温度-出力Flg セッター
	 * @param _intOndoFlg : 温度-出力Flgの値を格納する
	 */
	public void setIntOndoFlg(int _intOndoFlg) {
		this.intOndoFlg = _intOndoFlg;
	}

	/**
	 * PH ゲッター
	 * @return strPh : PHの値を返す
	 */
	public String getStrPh() {
		return strPh;
	}
	/**
	 * PH セッター
	 * @param _strPh : PHの値を格納する
	 */
	public void setStrPh(String _strPh) {
		this.strPh = _strPh;
	}

	/**
	 * PH - 出力Flg ゲッター
	 * @return intPhFlg : PH - 出力Flgの値を返す
	 */
	public int getIntPhFlg() {
		return intPhFlg;
	}
	/**
	 * PH - 出力Flg セッター
	 * @param _intPhFlg : PH - 出力Flgの値を格納する
	 */
	public void setIntPhFlg(int _intPhFlg) {
		this.intPhFlg = _intPhFlg;
	}

	/**
	 * 総酸：分析 ゲッター
	 * @return strSosanBunseki : 総酸：分析の値を返す
	 */
	public String getStrSosanBunseki() {
		return strSosanBunseki;
	}
	/**
	 * 総酸：分析 セッター
	 * @param _strSosanBunseki : 総酸：分析の値を格納する
	 */
	public void setStrSosanBunseki(String _strSosanBunseki) {
		this.strSosanBunseki = _strSosanBunseki;
	}

	/**
	 * 総酸：分析-出力Flg ゲッター
	 * @return intSosanBunsekiFlg : 総酸：分析-出力Flgの値を返す
	 */
	public int getIntSosanBunsekiFlg() {
		return intSosanBunsekiFlg;
	}
	/**
	 * 総酸：分析-出力Flg セッター
	 * @param _intSosanBunsekiFlg : 総酸：分析-出力Flgの値を格納する
	 */
	public void setIntSosanBunsekiFlg(int _intSosanBunsekiFlg) {
		this.intSosanBunsekiFlg = _intSosanBunsekiFlg;
	}

	/**
	 * 食塩：分析 ゲッター
	 * @return strShokuenBunseki : 食塩：分析の値を返す
	 */
	public String getStrShokuenBunseki() {
		return strShokuenBunseki;
	}
	/**
	 * 食塩：分析 セッター
	 * @param _strShokuenBunseki : 食塩：分析の値を格納する
	 */
	public void setStrShokuenBunseki(String _strShokuenBunseki) {
		this.strShokuenBunseki = _strShokuenBunseki;
	}

	/**
	 * 食塩：分析-出力Flg ゲッター
	 * @return intShokuenBunsekiFlg : 食塩：分析-出力Flgの値を返す
	 */
	public int getIntShokuenBunsekiFlg() {
		return intShokuenBunsekiFlg;
	}
	/**
	 * 食塩：分析-出力Flg セッター
	 * @param _intShokuenBunsekiFlg : 食塩：分析-出力Flgの値を格納する
	 */
	public void setIntShokuenBunsekiFlg(int _intShokuenBunsekiFlg) {
		this.intShokuenBunsekiFlg = _intShokuenBunsekiFlg;
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
	 * 比重-出力Flg ゲッター
	 * @return intHizyuFlg : 比重-出力Flgの値を返す
	 */
	public int getIntHizyuFlg() {
		return intHizyuFlg;
	}
	/**
	 * 比重-出力Flg セッター
	 * @param _intHizyuFlg : 比重-出力Flgの値を格納する
	 */
	public void setIntHizyuFlg(int _intHizyuFlg) {
		this.intHizyuFlg = _intHizyuFlg;
	}

	/**
	 * 水分活性 ゲッター
	 * @return strSuibun : 水分活性の値を返す
	 */
	public String getStrSuibun() {
		return strSuibun;
	}
	/**
	 * 水分活性 セッター
	 * @param _strSuibun : 水分活性の値を格納する
	 */
	public void setStrSuibun(String _strSuibun) {
		this.strSuibun = _strSuibun;
	}

	/**
	 * 水分活性-出力Flg ゲッター
	 * @return intSuibunFlg : 水分活性-出力Flgの値を返す
	 */
	public int getIntSuibunFlg() {
		return intSuibunFlg;
	}
	/**
	 * 水分活性-出力Flg セッター
	 * @param _intSuibunFlg : 水分活性-出力Flgの値を格納する
	 */
	public void setIntSuibunFlg(int _intSuibunFlg) {
		this.intSuibunFlg = _intSuibunFlg;
	}

	/**
	 * アルコール ゲッター
	 * @return strArukoru : アルコールの値を返す
	 */
	public String getStrArukoru() {
		return strArukoru;
	}
	/**
	 * アルコール セッター
	 * @param _strArukoru : アルコールの値を格納する
	 */
	public void setStrArukoru(String _strArukoru) {
		this.strArukoru = _strArukoru;
	}

	/**
	 * アルコール-出力Flg ゲッター
	 * @return intArukoruFlg : アルコール-出力Flgの値を返す
	 */
	public int getIntArukoruFlg() {
		return intArukoruFlg;
	}
	/**
	 * アルコール-出力Flg セッター
	 * @param _intArukoruFlg : アルコール-出力Flgの値を格納する
	 */
	public void setIntArukoruFlg(int _intArukoruFlg) {
		this.intArukoruFlg = _intArukoruFlg;
	}

	/**
	 * 作成メモ ゲッター
	 * @return strSakuseiMemo : 作成メモの値を返す
	 */
	public String getStrSakuseiMemo() {
		return strSakuseiMemo;
	}
	/**
	 * 作成メモ セッター
	 * @param _strSakuseiMemo : 作成メモの値を格納する
	 */
	public void setStrSakuseiMemo(String _strSakuseiMemo) {
		this.strSakuseiMemo = _strSakuseiMemo;
	}

	/**
	 * 作成メモ-出力Flg ゲッター
	 * @return strSakuseiMemoFlg : 作成メモ-出力Flgの値を返す
	 */
	public int getIntSakuseiMemoFlg() {
		return intSakuseiMemoFlg;
	}
	/**
	 * 作成メモ-出力Flg セッター
	 * @param _strSakuseiMemoFlg : 作成メモ-出力Flgの値を格納する
	 */
	public void setIntSakuseiMemoFlg(int _intSakuseiMemoFlg) {
		this.intSakuseiMemoFlg = _intSakuseiMemoFlg;
	}

	/**
	 * 評価 ゲッター
	 * @return strHyoka : 評価の値を返す
	 */
	public String getStrHyoka() {
		return strHyoka;
	}
	/**
	 * 評価 セッター
	 * @param _strHyoka : 評価の値を格納する
	 */
	public void setStrHyoka(String _strHyoka) {
		this.strHyoka = _strHyoka;
	}

	/**
	 * 評価-出力Flg ゲッター
	 * @return intHyokaFlg : 評価-出力Flgの値を返す
	 */
	public int getIntHyokaFlg() {
		return intHyokaFlg;
	}
	/**
	 * 評価-出力Flg セッター
	 * @param _intHyokaFlg : 評価-出力Flgの値を格納する
	 */
	public void setIntHyokaFlg(int _intHyokaFlg) {
		this.intHyokaFlg = _intHyokaFlg;
	}

	/**
	 * フリー①タイトル ゲッター
	 * @return strFreeTitle1 : フリー①タイトルの値を返す
	 */
	public String getStrFreeTitle1() {
		return strFreeTitle1;
	}
	/**
	 * フリー①タイトル セッター
	 * @param _strFreeTitle1 : フリー①タイトルの値を格納する
	 */
	public void setStrFreeTitle1(String _strFreeTitle1) {
		this.strFreeTitle1 = _strFreeTitle1;
	}

	/**
	 * フリー①内容 ゲッター
	 * @return strFreeNaiyo1 : フリー①内容の値を返す
	 */
	public String getStrFreeNaiyo1() {
		return strFreeNaiyo1;
	}
	/**
	 * フリー①内容 セッター
	 * @param _strFreeNaiyo1 : フリー①内容の値を格納する
	 */
	public void setStrFreeNaiyo1(String _strFreeNaiyo1) {
		this.strFreeNaiyo1 = _strFreeNaiyo1;
	}

	/**
	 * フリー①-出力Flg ゲッター
	 * @return intFreeFlg : フリー①-出力Flgの値を返す
	 */
	public int getIntFreeFlg() {
		return intFreeFlg;
	}
	/**
	 * フリー①-出力Flg セッター
	 * @param _intFreeFlg : フリー①-出力Flgの値を格納する
	 */
	public void setIntFreeFlg(int _intFreeFlg) {
		this.intFreeFlg = _intFreeFlg;
	}

	/**
	 * フリー②タイトル ゲッター
	 * @return strFreeTitle2 : フリー②タイトルの値を返す
	 */
	public String getStrFreeTitle2() {
		return strFreeTitle2;
	}
	/**
	 * フリー②タイトル セッター
	 * @param strFreeTitle2 : フリー②タイトルの値を格納する
	 */
	public void setStrFreeTitle2(String _strFreeTitle2) {
		this.strFreeTitle2 = _strFreeTitle2;
	}

	/**
	 * フリー②内容 ゲッター
	 * @return strFreeNaiyo2 : フリー②内容の値を返す
	 */
	public String getStrFreeNaiyo2() {
		return strFreeNaiyo2;
	}
	/**
	 * フリー②内容 セッター
	 * @param _strFreeNaiyo2 : フリー②内容の値を格納する
	 */
	public void setStrFreeNaiyo2(String _strFreeNaiyo2) {
		this.strFreeNaiyo2 = _strFreeNaiyo2;
	}

	/**
	 * フリー②-出力Flg ゲッター
	 * @return intFreeFl2 : フリー②-出力Flgの値を返す
	 */
	public int getIntFreeFl2() {
		return intFreeFl2;
	}
	/**
	 * フリー②-出力Flg セッター
	 * @param _intFreeFl2 : フリー②-出力Flgの値を格納する
	 */
	public void setIntFreeFl2(int _intFreeFl2) {
		this.intFreeFl2 = _intFreeFl2;
	}

	/**
	 * フリー③タイトル ゲッター
	 * @return strFreeTitle3 : フリー③タイトルの値を返す
	 */
	public String getStrFreeTitle3() {
		return strFreeTitle3;
	}
	/**
	 * フリー③タイトル セッター
	 * @param _strFreeTitle3 : フリー③タイトルの値を格納する
	 */
	public void setStrFreeTitle3(String _strFreeTitle3) {
		this.strFreeTitle3 = _strFreeTitle3;
	}

	/**
	 * フリー③内容 ゲッター
	 * @return strFreeNaiyo3 : フリー③内容の値を返す
	 */
	public String getStrFreeNaiyo3() {
		return strFreeNaiyo3;
	}
	/**
	 * フリー③内容 セッター
	 * @param _strFreeNaiyo3 : フリー③内容の値を格納する
	 */
	public void setStrFreeNaiyo3(String _strFreeNaiyo3) {
		this.strFreeNaiyo3 = _strFreeNaiyo3;
	}

	/**
	 * フリー③-出力Flg ゲッター
	 * @return intFreeFl3 : フリー③-出力Flgの値を返す
	 */
	public int getIntFreeFl3() {
		return intFreeFl3;
	}
	/**
	 * フリー③-出力Flg セッター
	 * @param _intFreeFl3 : フリー③-出力Flgの値を格納する
	 */
	public void setIntFreeFl3(int _intFreeFl3) {
		this.intFreeFl3 = _intFreeFl3;
	}

	/**
	 * 試作日付 ゲッター
	 * @return strShisakuHi : 試作日付の値を返す
	 */
	public String getStrShisakuHi() {
		return strShisakuHi;
	}

	/**
	 * 試作日付 セッター
	 * @param strShisakuHi : 試作日付の値を格納する
	 */
	public void setStrShisakuHi(String strShisakuHi) {
		this.strShisakuHi = strShisakuHi;
	}

	/**
	 * 仕上重量 ゲッター
	 * @return dciShiagari : 仕上重量の値を返す
	 */
	public BigDecimal getDciShiagari() {
		return dciShiagari;
	}

	/**
	 * 仕上重量 セッター
	 * @param dciShiagari : 仕上重量の値を格納する
	 */
	public void setDciShiagari(BigDecimal dciShiagari) {
		this.dciShiagari = dciShiagari;
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
	 * @return strkosinHi : 更新日付の値を返す
	 */
	public String getStrkosinHi() {
		return strkosinHi;
	}
	/**
	 * 更新日付 セッター
	 * @param _strkosinHi : 更新日付の値を格納する
	 */
	public void setStrkosinHi(String _strkosinHi) {
		this.strkosinHi = _strkosinHi;
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

	/**
	 * 原価試算依頼フラグ ゲッター
	 * @return flg_shisanIrai : 原価試算依頼フラグの値を返す
	 */
	public int getFlg_shisanIrai() {
		return flg_shisanIrai;
	}

	/**
	 * 原価試算依頼フラグ セッター
	 * @param flg_shisanIrai : 原価試算依頼フラグの値を格納する
	 */
	public void setFlg_shisanIrai(int flg_shisanIrai) {
		this.flg_shisanIrai = flg_shisanIrai;
	}

	/**
	 * 既存依頼データフラグ ゲッター
	 * @return flg_init : 既存依頼データフラグの値を返す
	 */
	public int getFlg_init() {
		return flg_init;
	}

	/**
	 * 既存依頼データフラグ セッター
	 * @param flg_init : 既存依頼データフラグの値を格納する
	 */
	public void setFlg_init(int flg_init) {
		this.flg_init = flg_init;
	}

//add start -------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.7
	/**
	 * 計算式 ゲッター
	 * @return strKeisanSiki : 計算式の値を返す
	 */
	public String getStrKeisanSiki() {
		return strKeisanSiki;
	}

	/**
	 * 計算式 セッター
	 * @param strKeisanSiki : 計算式の値を格納する
	 */
	public void setStrKeisanSiki(String strKeisanSiki) {
		this.strKeisanSiki = strKeisanSiki;
	}
//add end   -------------------------------------------------------------------------------

//2011/04/20 QP@10181_No.67 TT Nishigawa Change Start -------------------------
	/**
	 * キャンセルフラグ ゲッター
	 * @return strKeisanSiki : キャンセルフラグの値を返す
	 */
	public int getFlg_cancel() {
		return flg_cancel;
	}

	/**
	 * キャンセルフラグ セッター
	 * @param strKeisanSiki : キャンセルフラグの値を格納する
	 */
	public void setFlg_cancel(int flg_cancel) {
		this.flg_cancel = flg_cancel;
	}
//2011/04/20 QP@10181_No.67 TT Nishigawa Change End ---------------------------

//2011/05/30　【QP@10181_No.42_49_72】工程パターン制御　TT.NISHIGAWA　start
	/**
	 * 水相比重 ゲッター
	 * @return strHiju_sui : 水相比重の値を返す
	 */
	public String getStrHiju_sui() {
		return strHiju_sui;
	}
	/**
	 * 水相比重 セッター
	 * @param strHiju_sui : 水相比重の値を格納する
	 */
	public void setStrHiju_sui(String strHiju_sui) {
		this.strHiju_sui = strHiju_sui;
	}
	/**
	 * 水相比重　出力FG ゲッター
	 * @return intHiju_sui_fg :  水相比重　出力FGの値を返す
	 */
	public int getIntHiju_sui_fg() {
		return intHiju_sui_fg;
	}
	/**
	 * 水相比重　出力FG セッター
	 * @param intHiju_sui_fg : 水相比重　出力FGの値を格納する
	 */
	public void setIntHiju_sui_fg(int intHiju_sui_fg) {
		this.intHiju_sui_fg = intHiju_sui_fg;
	}
//2011/05/30　【QP@10181_No.42_49_72】工程パターン制御　TT.NISHIGAWA　start

// ADD start 20120928 QP@20505 No.24
	// ----------  ↓粘度フリー  ------------
	/**
	 * 粘度フリータイトル ゲッター
	 * @return strSuibun : 水分活性の値を返す
	 */
	public String getStrFreeTitleNendo() {
		return strFreeTitleNendo;
	}
	/**
	 * 粘度フリータイトル セッター
	 * @param _strSuibun : 水分活性の値を格納する
	 */
	public void setStrFreeTitleNendo(String _strFreeTitleNendo) {
		this.strFreeTitleNendo = _strFreeTitleNendo;
	}
	/**
	 * 粘度フリー内容 ゲッター
	 * @return strSuibun : 水分活性の値を返す
	 */
	public String getStrFreeNendo() {
		return strFreeNendo;
	}
	/**
	 * 粘度フリー内容 セッター
	 * @param _strSuibun : 水分活性の値を格納する
	 */
	public void setStrFreeNendo(String _strFreeNendo) {
		this.strFreeNendo = _strFreeNendo;
	}
	/**
	 * 粘度フリー-出力Flg ゲッター
	 * @return intSuibunFlg : 水分活性-出力Flgの値を返す
	 */
	public int getIntFreeNendoFlg() {
		return intFreeNendoFlg;
	}
	/**
	 * 粘度フリー-出力Flg セッター
	 * @param _intSuibunFlg : 水分活性-出力Flgの値を格納する
	 */
	public void setIntFreeNendoFlg(int _intFreeNendoFlg) {
		this.intFreeNendoFlg = _intFreeNendoFlg;
	}
	// ----------  ↓温度フリー   ------------
	/**
	 * 温度フリータイトル ゲッター
	 * @return strSuibun : 水分活性の値を返す
	 */
	public String getStrFreeTitleOndo() {
		return strFreeTitleOndo;
	}
	/**
	 * 温度フリータイトル セッター
	 * @param _strSuibun : 水分活性の値を格納する
	 */
	public void setStrFreeTitleOndo(String _strFreeTitleOndo) {
		this.strFreeTitleOndo = _strFreeTitleOndo;
	}
	/**
	 * 温度フリー内容 ゲッター
	 * @return strSuibun : 水分活性の値を返す
	 */
	public String getStrFreeOndo() {
		return strFreeOndo;
	}
	/**
	 * 温度フリー内容 セッター
	 * @param _strSuibun : 水分活性の値を格納する
	 */
	public void setStrFreeOndo(String _strFreeOndo) {
		this.strFreeOndo = _strFreeOndo;
	}
	/**
	 * 温度フリー-出力Flg ゲッター
	 * @return intSuibunFlg : 水分活性-出力Flgの値を返す
	 */
	public int getIntFreeOndoFlg() {
		return intFreeOndoFlg;
	}
	/**
	 * 温度フリー-出力Flg セッター
	 * @param _intSuibunFlg : 水分活性-出力Flgの値を格納する
	 */
	public void setIntFreeOndoFlg(int _intFreeOndoFlg) {
		this.intFreeOndoFlg = _intFreeOndoFlg;
	}
	// ----------  ↓水分活性フリー   ------------
	/**
	 * 水分活性フリータイトル ゲッター
	 * @return strSuibun : 水分活性の値を返す
	 */
	public String getStrFreeTitleSuibunKasei() {
		return strFreeTitleSuibunKasei;
	}
	/**
	 * 水分活性フリータイトル セッター
	 * @param _strSuibun : 水分活性の値を格納する
	 */
	public void setStrFreeTitleSuibunKasei(String _strFreeTitleSuibunKasei) {
		this.strFreeTitleSuibunKasei = _strFreeTitleSuibunKasei;
	}
	/**
	 * 水分活性フリー内容 ゲッター
	 * @return strSuibun : 水分活性の値を返す
	 */
	public String getStrFreeSuibunKasei() {
		return strFreeSuibunKasei;
	}
	/**
	 * 水分活性フリー内容 セッター
	 * @param _strSuibun : 水分活性の値を格納する
	 */
	public void setStrFreeSuibunKasei(String _strFreeSuibunKasei) {
		this.strFreeSuibunKasei = _strFreeSuibunKasei;
	}
	/**
	 * 水分活性フリー-出力Flg ゲッター
	 * @return intSuibunFlg : 水分活性-出力Flgの値を返す
	 */
	public int getIntFreeSuibunKaseiFlg() {
		return intFreeSuibunKaseiFlg;
	}
	/**
	 * 水分活性フリー-出力Flg セッター
	 * @param _intSuibunFlg : 水分活性-出力Flgの値を格納する
	 */
	public void setIntFreeSuibunKaseiFlg(int _intFreebunKaseiFlg) {
		this.intFreeSuibunKaseiFlg = _intFreebunKaseiFlg;
	}
	// ----------  ↓アルコール フリー項目   ------------
	/**
	 * アルコール フリータイトル ゲッター
	 * @return strSuibun : 水分活性の値を返す
	 */
	public String getStrFreeTitleAlchol() {
		return strFreeTitleAlchol;
	}
	/**
	 * アルコール フリータイトル セッター
	 * @param _strSuibun : 水分活性の値を格納する
	 */
	public void setStrFreeTitleAlchol(String _strFreeTitleAlchol) {
		this.strFreeTitleAlchol = _strFreeTitleAlchol;
	}
	/**
	 * アルコール フリー内容 ゲッター
	 * @return strSuibun : 水分活性の値を返す
	 */
	public String getStrFreeAlchol() {
		return strFreeAlchol;
	}
	/**
	 * アルコール フリー内容 セッター
	 * @param _strSuibun : 水分活性の値を格納する
	 */
	public void setStrFreeAlchol(String _strFreeAlchol) {
		this.strFreeAlchol = _strFreeAlchol;
	}
	/**
	 * アルコール フリー-出力Flg ゲッター
	 * @return intSuibunFlg : 水分活性-出力Flgの値を返す
	 */
	public int getIntFreeAlcholFlg() {
		return intFreeAlcholFlg;
	}
	/**
	 * アルコール フリー-出力Flg セッター
	 * @param _intSuibunFlg : 水分活性-出力Flgの値を格納する
	 */
	public void setIntFreeAlcholFlg(int _intFreeAlcholFlg) {
		this.intFreeAlcholFlg = _intFreeAlcholFlg;
	}
	// -------------  ↓実効酢酸濃度  ---------------------
	/**
	 * 実効酢酸濃度 ゲッター
	 * @return dciJikkoSakusanNodo : 水相中食塩の値を返す
	 */
	public BigDecimal getDciJikkoSakusanNodo() {
		return dciJikkoSakusanNodo;
	}
	/**
	 * 実効酢酸濃度 セッター
	 * @param _dciJikkoSakusanNodo : 水相中食塩の値を格納する
	 */
	public void setDciJikkoSakusanNodo(BigDecimal _dciJikkoSakusanNodo) {
		this.dciJikkoSakusanNodo = _dciJikkoSakusanNodo;
	}
	/**
	 * 実効酢酸濃度-出力Flg ゲッター
	 * @return intSuibunFlg : 水分活性-出力Flgの値を返す
	 */
	public int getIntJikkoSakusanNodoFlg() {
		return intJikkoSakusanNodoFlg;
	}
	/**
	 * 実効酢酸濃度-出力Flg セッター
	 * @param _intSuibunFlg : 水分活性-出力Flgの値を格納する
	 */
	public void setIntJikkoSakusanNodoFlg(int _intJikkoSakusanNodoFlg) {
		this.intJikkoSakusanNodoFlg = _intJikkoSakusanNodoFlg;
	}
	// -------------  ↓水相中ＭＳＧ  ---------------------
	/**
	 * 水相中ＭＳＧ ゲッター
	 * @return strSuibun : 水分活性の値を返す
	 */
	public BigDecimal getDciSuisoMSG() {
		return dciSuisoMSG;
	}
	/**
	 * 水相中ＭＳＧ セッター
	 * @param _strSuibun : 水分活性の値を格納する
	 */
	public void setDciSuisoMSG(BigDecimal _dciSuisoMSG) {
		this.dciSuisoMSG = _dciSuisoMSG;
	}
	/**
	 * 水相中ＭＳＧ-出力Flg ゲッター
	 * @return intSuibunFlg : 水分活性-出力Flgの値を返す
	 */
	public int getIntSuisoMSGFlg() {
		return intSuisoMSGFlg;
	}
	/**
	 * 水相中ＭＳＧ-出力Flg セッター
	 * @param _intSuibunFlg : 水分活性-出力Flgの値を格納する
	 */
	public void setIntSuisoMSGFlg(int _intSuisoMSGFlg) {
		this.intSuisoMSGFlg = _intSuisoMSGFlg;
	}
	// -------------  ↓フリー④  ---------------------
	/**
	 * フリー④タイトル ゲッター
	 * @return strFreeTitle4 : フリー④タイトルの値を返す
	 */
	public String getStrFreeTitle4() {
		return strFreeTitle4;
	}
	/**
	 * フリー④タイトル セッター
	 * @param _strFreeTitle4 : フリー④タイトルの値を格納する
	 */
	public void setStrFreeTitle4(String _strFreeTitle4) {
		this.strFreeTitle4 = _strFreeTitle4;
	}
	/**
	 * フリー④内容 ゲッター
	 * @return strFreeNaiyo4 : フリー④内容の値を返す
	 */
	public String getStrFreeNaiyo4() {
		return strFreeNaiyo4;
	}
	/**
	 * フリー④内容 セッター
	 * @param _strFreeNaiyo41 : フリー④内容の値を格納する
	 */
	public void setStrFreeNaiyo4(String _strFreeNaiyo4) {
		this.strFreeNaiyo4 = _strFreeNaiyo4;
	}
	/**
	 * フリー④-出力Flg ゲッター
	 * @return intFreeFlg4 : フリー④-出力Flgの値を返す
	 */
	public int getIntFreeFlg4() {
		return intFreeFlg4;
	}
	/**
	 * フリー④-出力Flg セッター
	 * @param _intFreeFlg4 : フリー④-出力Flgの値を格納する
	 */
	public void setIntFreeFlg4(int _intFreeFlg4) {
		this.intFreeFlg4 = _intFreeFlg4;
	}
	// -------------  ↓フリー⑤  ---------------------
	/**
	 * フリー⑤タイトル ゲッター
	 * @return strFreeTitle5 : フリー⑤タイトルの値を返す
	 */
	public String getStrFreeTitle5() {
		return strFreeTitle5;
	}
	/**
	 * フリー⑤タイトル セッター
	 * @param strFreeTitle5 : フリー⑤タイトルの値を格納する
	 */
	public void setStrFreeTitle5(String _strFreeTitle5) {
		this.strFreeTitle5 = _strFreeTitle5;
	}
	/**
	 * フリー⑤内容 ゲッター
	 * @return strFreeNaiyo2 : フリー⑤内容の値を返す
	 */
	public String getStrFreeNaiyo5() {
		return strFreeNaiyo5;
	}
	/**
	 * フリー⑤内容 セッター
	 * @param _strFreeNaiyo5 : フリー⑤内容の値を格納する
	 */
	public void setStrFreeNaiyo5(String _strFreeNaiyo5) {
		this.strFreeNaiyo5 = _strFreeNaiyo5;
	}
	/**
	 * フリー⑤-出力Flg ゲッター
	 * @return intFreeFlg5 : フリー⑤-出力Flgの値を返す
	 */
	public int getIntFreeFlg5() {
		return intFreeFlg5;
	}
	/**
	 * フリー⑤-出力Flg セッター
	 * @param _intFreeFlg5 : フリー⑤-出力Flgの値を格納する
	 */
	public void setIntFreeFlg5(int _intFreeFlg5) {
		this.intFreeFlg5 = _intFreeFlg5;
	}
	// -------------  ↓フリー⑥  ---------------------
	/**
	 * フリー⑥タイトル ゲッター
	 * @return strFreeTitle6 : フリー⑥タイトルの値を返す
	 */
	public String getStrFreeTitle6() {
		return strFreeTitle6;
	}
	/**
	 * フリー⑥タイトル セッター
	 * @param _strFreeTitle6 : フリー⑥タイトルの値を格納する
	 */
	public void setStrFreeTitle6(String _strFreeTitle6) {
		this.strFreeTitle6 = _strFreeTitle6;
	}
	/**
	 * フリー⑥内容 ゲッター
	 * @return strFreeNaiyo6 : フリー⑥内容の値を返す
	 */
	public String getStrFreeNaiyo6() {
		return strFreeNaiyo6;
	}
	/**
	 * フリー⑥内容 セッター
	 * @param _strFreeNaiyo6 : フリー⑥内容の値を格納する
	 */
	public void setStrFreeNaiyo6(String _strFreeNaiyo6) {
		this.strFreeNaiyo6 = _strFreeNaiyo6;
	}
	/**
	 * フリー⑥-出力Flg ゲッター
	 * @return intFreeFlg6 : フリー⑥-出力Flgの値を返す
	 */
	public int getIntFreeFlg6() {
		return intFreeFlg6;
	}
	/**
	 * フリー⑥-出力Flg セッター
	 * @param _intFreeFlg6 : フリー⑥-出力Flgの値を格納する
	 */
	public void setIntFreeFlg6(int _intFreeFlg6) {
		this.intFreeFlg6 = _intFreeFlg6;
	}
// ADD end 20120928 QP@20505 No.24
}