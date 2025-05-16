package jp.co.blueflag.shisaquick.jws.data;

import jp.co.blueflag.shisaquick.jws.base.*;
import jp.co.blueflag.shisaquick.jws.common.ExceptionBase;
import jp.co.blueflag.shisaquick.jws.common.JnlpConnect;
import jp.co.blueflag.shisaquick.jws.common.ModeCtrl;
import jp.co.blueflag.shisaquick.jws.disp.DownloadDisp;
import jp.co.blueflag.shisaquick.jws.disp.ManufacturingSubDisp;
import jp.co.blueflag.shisaquick.jws.disp.TrialMainDisp;
import jp.co.blueflag.shisaquick.jws.manager.*;

/**
 *
 * 共通データ管理クラス
 *
 */
public class DataCtrl {

	//このクラスに唯一のインスタンス
	private static DataCtrl instance = new DataCtrl();

	//各種データクラス
	//試作テーブルデータ保持
	private TrialTblData TrialTblData;
	//ユーザマスタデータ保持
	private UserMstData UserMstData;
	//クリップボードデータ保持
	private ClipboardData ClipboardData;
	//配列データ保持
	private ArrayData ArrayData;
	//パラメータデータ保持
	private ParamData ParamData;
	//会社データ保持
	private KaishaData kaishaData;
	//部署データ保持
	private BushoData bushoData;
	//リテラルデータ保持
// MOD start 20121003 QP@20505 No.24
//	private LiteralData[] literalData = new LiteralData[26];
	private LiteralData[] literalData = new LiteralData[27];
//	// MOD end 20121003 QP@20505 No.24

	//原料データ保持
	private MaterialMstData materialMstData;
	//Resultデータ保持
	private ResultData resultData;
	//メッセージコントロール
	private MessageCtrl messageCtrl;

	//モード編集クラス
	private ModeCtrl modeCtrl;

	//製造工程画面
	private ManufacturingSubDisp manufacturingSubDisp;

	//ダウンロード用待ち画面
	private  DownloadDisp DownloadDisp;

	//Jnlp接続クラス
	private JnlpConnect jnlpConnect;

	//試算履歴データ
	private ShisanRirekiKanriData shisanRirekiKanriData;


	private ExceptionBase ex;

	/**
	 * コンストラクタ(private)
	 */
	private DataCtrl(){
		try{
			TrialTblData = new TrialTblData();
			UserMstData = new UserMstData();
			ClipboardData = new ClipboardData();
			ArrayData = new ArrayData();
			ParamData = new ParamData();
			kaishaData = new KaishaData();
			bushoData = new BushoData();
			materialMstData = new MaterialMstData();
			resultData = new ResultData();
			modeCtrl = new ModeCtrl();
			manufacturingSubDisp = new ManufacturingSubDisp("製造工程画面");
			DownloadDisp = new DownloadDisp();
			shisanRirekiKanriData = new ShisanRirekiKanriData();

			messageCtrl = new MessageCtrl();
			jnlpConnect = new JnlpConnect();

			//リテラルデータクラスの生成
			literalData[0]  = new LiteralData("SA600");		//00 : 工程属性
			literalData[1]  = new LiteralData("SA610");		//01 : 一括表示
			literalData[2]  = new LiteralData("SA620");		//02 : ジャンル
			literalData[3]  = new LiteralData("SA630");		//03 : ユーザ
			literalData[4]  = new LiteralData("SA640");		//04 : 特徴原料
			literalData[5]  = new LiteralData("SA650");		//05 : 用途
			literalData[6]  = new LiteralData("SA660");		//06 : 価格帯
			literalData[7]  = new LiteralData("SA670");		//07 : 種別
			literalData[8]  = new LiteralData("SA680");		//08 : 少数指定
			literalData[9]  = new LiteralData("SA690");		//09 : 担当営業
			literalData[10] = new LiteralData("SA700");	//10 : 製造方法
			literalData[11] = new LiteralData("SA710");	//11 : 充填方法
			literalData[12] = new LiteralData("SA720");	//12 : 殺菌方法
			literalData[13] = new LiteralData("SA730");	//13 : 容器包材
			literalData[14] = new LiteralData("SA740");	//14 : 容量
			literalData[15] = new LiteralData("SA750");	//15 : 単位
			literalData[16] = new LiteralData("SA760");	//16 : 荷姿
			literalData[17] = new LiteralData("SA770");	//17 : 取扱温度
			literalData[18] = new LiteralData("SA780");	//18 : 賞味期間
			literalData[19] = new LiteralData("SA850");	//19 : 種別No
//2011/05/30　【QP@10181_No.42_49_72】工程パターン制御　TT.NISHIGAWA　start
			literalData[20] = new LiteralData("SA900");	//20 : 工程パターン
			literalData[21] = new LiteralData("SA910");	//21 : 製品比重
			literalData[22] = new LiteralData("SA920");	//22 : 油相比重
			literalData[23] = new LiteralData("SA930");	//23 : 調味料１液タイプ　工程属性
			literalData[24] = new LiteralData("SA940");	//24 : 調味料２液タイプ　工程属性
			literalData[25] = new LiteralData("SA950");	//25 : その他・加食タイプ　工程属性
//2011/05/30　【QP@10181_No.42_49_72】工程パターン制御　TT.NISHIGAWA　end
// ADD start 20121003 QP@20505 No.24
			literalData[26] = new LiteralData("SA960");	//26 : 実効酢酸濃度
// ADD end 20121003 QP@20505 No.24
		}catch(Exception e){
			//エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("共通データ管理のコンストラクタが失敗しました");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			this.messageCtrl.PrintErrMessage(ex);

		}finally{

		}

	}

	/**
	 * インスタンス取得メソッド
	 * @return
	 */
	public static DataCtrl getInstance() {
		return instance;
	}

	/**
	 * メッセージ表示処理
	 * @param printBexception
	 */
	public void PrintMessage(ExceptionBase printBexception){

			//デバッグレベル設定
			this.getMessageCtrl().setDebugLevel(this.getResultData().getStrDebuglevel());

			//Result結果がtrueの場合（成功）
			if ( this.getResultData().isReturnFlgCheck() ) {

				//ExceptionBaseデータをエラー表示
				this.getMessageCtrl().PrintErrMessage(printBexception);

			}
			//Result結果がfalseの場合（失敗）
			else {

				//Resultデータをエラー表示
				this.getMessageCtrl().PrintMessage(this.resultData);

				//Resultデータ初期化
				this.resultData.initResultData();

			}
	}

	/**
	 * 試作テーブルデータ保持クラス ゲッター
	 * @return 試作テーブルデータ保持クラス
	 */
	public TrialTblData getTrialTblData() {
		return this.TrialTblData;
	}

	/**
	 * ユーザマスタデータ保持クラス ゲッター
	 * @return ユーザマスタデータ保持クラス
	 */
	public UserMstData getUserMstData() {
		return this.UserMstData;
	}

	/**
	 * クリップボードデータ保持クラス ゲッター
	 * @return クリップボードデータ保持クラス
	 */
	public ClipboardData getClipboardData() {
		return this.ClipboardData;
	}

	/**
	 * 配列データ保持クラス ゲッター
	 * @return 配列データ保持クラス
	 */
	public ArrayData getArrayData() {
		return this.ArrayData;
	}

	/**
	 * パラメータデータ保持クラス ゲッター
	 * @return パラメータデータ保持クラス
	 */
	public ParamData getParamData() {
		return this.ParamData;
	}

	/**
	 * 会社データ保持クラス ゲッター
	 * @return 会社データ保持クラス
	 */
	public KaishaData getKaishaData() {
		return this.kaishaData;
	}

	/**
	 * 部署データ保持クラス ゲッター
	 * @return 部署データ保持クラス
	 */
	public BushoData getBushoData() {
		return this.bushoData;
	}

	/**
	 * 原料データ保持クラス ゲッター
	 * @return 原料データ保持クラス
	 */
	public MaterialMstData getMaterialMstData() {
		return this.materialMstData;
	}

	/**
	 * Resultデータ保持クラス　ゲッター
	 * @return Resultデータ保持クラス
	 */
	public ResultData getResultData() {
		return this.resultData;
	}

	/**
	 * メッセージコントロールクラス ゲッター
	 * @return メッセージコントロールクラス
	 */
	public MessageCtrl getMessageCtrl() {
		return this.messageCtrl;
	}

	/**
	 * モード編集クラス ゲッター
	 * @return モード編集クラス
	 */
	public ModeCtrl getModeCtrl() {
		return this.modeCtrl;
	}

	/**
	 * 製造工程画面クラス ゲッター
	 * @return 製造工程画面クラス
	 */
	public ManufacturingSubDisp getManufacturingSubDisp() {
		return manufacturingSubDisp;
	}

	/**
	 * ダウンロード用待ち画面クラス ゲッター
	 * @return ダウンロード用待ち画面クラス
	 */
	public DownloadDisp getDownloadDisp() {
		return DownloadDisp;
	}

	/**
	 * 試算履歴データ保持クラス ゲッター
	 * @return 試算履歴データ保持クラス
	 */
	public ShisanRirekiKanriData getShisanRirekiKanriData() {
		return this.shisanRirekiKanriData;
	}

	/**
	 * Jnlp接続クラス ゲッター
	 * @return Jnlp接続クラス
	 */
	public JnlpConnect getJnlpConnect() {
		return this.jnlpConnect;
	}

	/**
	 *  リテラルデータ【SA600 : 工程属性】 ゲッター
	 *  @return リテラルデータ保持クラス
	 */
	public LiteralData getLiteralDataZokusei() { return this.literalData[0]; }

	/**
	 * リテラルデータ【SA610 : 一括表示】 ゲッター
	 * @return リテラルデータ保持クラス
	 */
	public LiteralData getLiteralDataIkatu() { return this.literalData[1]; }

	/**
	 * リテラルデータ【SA620 : ジャンル】 ゲッター
	 * @return リテラルデータ保持クラス
	 */
	public LiteralData getLiteralDataZyanru() { return this.literalData[2]; }

	/**
	 * リテラルデータ【SA630 : ユーザ】 ゲッター
	 * @return リテラルデータ保持クラス
	 */
	public LiteralData getLiteralDataUser() { return this.literalData[3]; }

	/**
	 * リテラルデータ【SA640 : 特徴原料】 ゲッター
	 * @return リテラルデータ保持クラス
	 */
	public LiteralData getLiteralDataTokutyo() { return this.literalData[4]; }

	/**
	 * リテラルデータ【SA650 : 用途】 ゲッター
	 * @return リテラルデータ保持クラス
	 */
	public LiteralData getLiteralDataYoto() { return this.literalData[5]; }

	/**
	 * リテラルデータ【SA660 : 価格帯】 ゲッター
	 * @return リテラルデータ保持クラス
	 */
	public LiteralData getLiteralDataKakaku() { return this.literalData[6]; }

	/**
	 * リテラルデータ【SA670 : 種別】 ゲッター
	 * @return リテラルデータ保持クラス
	 */
	public LiteralData getLiteralDataShubetu() { return this.literalData[7]; }

	/**
	 * リテラルデータ【SA680 : 少数指定】 ゲッター
	 * @return リテラルデータ保持クラス
	 */
	public LiteralData getLiteralDataShosu() { return this.literalData[8]; }

	/**
	 * リテラルデータ【SA690 : 担当営業】 ゲッター
	 * @return リテラルデータ保持クラス
	 */
	public LiteralData getLiteralDataTanto() { return this.literalData[9]; }

	/**
	 * リテラルデータ【SA700 : 製造方法】 ゲッター
	 * @return リテラルデータ保持クラス
	 */
	public LiteralData getLiteralDataSeizo() { return this.literalData[10]; }

	/**
	 * リテラルデータ【SA710 : 充填方法】 ゲッター
	 * @return リテラルデータ保持クラス
	 */
	public LiteralData getLiteralDataZyuten() { return this.literalData[11]; }

	/**
	 * リテラルデータ【SA720 : 殺菌方法】 ゲッター
	 * @return リテラルデータ保持クラス
	 */
	public LiteralData getLiteralDataSakin() { return this.literalData[12]; }

	/**
	 * リテラルデータ【SA730 : 容器包材】 ゲッター
	 * @return リテラルデータ保持クラス
	 */
	public LiteralData getLiteralDataYoki() { return this.literalData[13]; }

	/**
	 * リテラルデータ【SA740 : 容量】 ゲッター
	 * @return リテラルデータ保持クラス
	 */
	public LiteralData getLiteralDataYoryo() { return this.literalData[14]; }

	/**
	 * リテラルデータ【SA750 : 単位】 ゲッター
	 * @return リテラルデータ保持クラス
	 */
	public LiteralData getLiteralDataTani() { return this.literalData[15]; }

	/**
	 * リテラルデータ【SA760 : 荷姿】 ゲッター
	 * @return リテラルデータ保持クラス
	 */
	public LiteralData getLiteralDataNisugata() { return this.literalData[16]; }

	/**
	 * リテラルデータ【SA770 : 取扱温度】 ゲッター
	 * @return リテラルデータ保持クラス
	 */
	public LiteralData getLiteralDataOndo() { return this.literalData[17]; }

	/**
	 * リテラルデータ【SA780 : 賞味期間】 ゲッター
	 * @return リテラルデータ保持クラス
	 */
	public LiteralData getLiteralDataShomi() { return this.literalData[18]; }

	/**
	 * リテラルデータ【SA850 : 種別No】 ゲッター
	 * @return リテラルデータ保持クラス
	 */
	public LiteralData getLiteralDataShubetuNo() { return this.literalData[19]; }


//2011/05/30　【QP@10181_No.42_49_72】工程パターン制御　TT.NISHIGAWA　start
	/**
	 * リテラルデータ【SA900 : 工程パターン】 ゲッター
	 * @return リテラルデータ保持クラス
	 */
	public LiteralData getLiteralDataKoteiPtn() { return this.literalData[20]; }
	/**
	 * リテラルデータ【SA910 : 製品比重】 ゲッター
	 * @return リテラルデータ保持クラス
	 */
	public LiteralData getLiteralDataSeihinHiju() { return this.literalData[21]; }
	/**
	 * リテラルデータ【SA920 : 油相比重】 ゲッター
	 * @return リテラルデータ保持クラス
	 */
	public LiteralData getLiteralDataYusoHiju() { return this.literalData[22]; }

	/**
	 * リテラルデータ【SA930 :  調味料１液タイプ　工程属性】 ゲッター
	 * @return リテラルデータ保持クラス
	 */
	public LiteralData getLiteralDataKotei_tyomi1() { return this.literalData[23]; }
	/**
	 * リテラルデータ【SA940 : 調味料２液タイプ　工程属性】 ゲッター
	 * @return リテラルデータ保持クラス
	 */
	public LiteralData getLiteralDataKotei_tyomi2() { return this.literalData[24]; }
	/**
	 * リテラルデータ【SA950 : その他・加食タイプ　工程属性】 ゲッター
	 * @return リテラルデータ保持クラス
	 */
	public LiteralData getLiteralDataKotei_sonota() { return this.literalData[25]; }

//2011/05/30　【QP@10181_No.42_49_72】工程パターン制御　TT.NISHIGAWA　end

// ADD start 20121003 QP@20505 No.24
	public LiteralData getLiteralDataJikkoSakusanNodo() { return this.literalData[26]; }
	/**
	 * リテラルデータ【SA960 : 実効酢酸濃度】 ゲッター
	 * @return リテラルデータ保持クラス
	 */
// ADD end 20121003 QP@20505 No.24

	/**
	 * 試作データセッター
	 */
	public void setTrialTblData(TrialTblData trialTblData) {
		TrialTblData = null;
		Runtime.getRuntime().gc();
		TrialTblData = trialTblData;
	}

}