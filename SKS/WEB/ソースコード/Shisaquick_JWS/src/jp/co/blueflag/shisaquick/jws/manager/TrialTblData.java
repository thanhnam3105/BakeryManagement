package jp.co.blueflag.shisaquick.jws.manager;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

import jp.co.blueflag.shisaquick.jws.base.*;
import jp.co.blueflag.shisaquick.jws.common.DataBase;
import jp.co.blueflag.shisaquick.jws.common.ExceptionBase;
import jp.co.blueflag.shisaquick.jws.common.JwsConstManager;
import jp.co.blueflag.shisaquick.jws.data.DataCtrl;
import jp.hishidama.eval.ExpRuleFactory;
import jp.hishidama.eval.Expression;
import jp.hishidama.eval.Rule;

/**
 * 
 * 試作テーブルデータ保持
 * 
 */
public class TrialTblData extends DataBase {

	private PrototypeData ptdtShaisakuHin; // 試作品データ保持
	private MixedData midtHaigou; // 配合データ保持
	private TrialData tldtShisakuRetu; // 試作列データ保持
	private PrototypeListData pldtShisakuList; // 試作リストデータ保持
	private ManufacturingData mfdtSeizo; // 製造工程データ保持
	private ShizaiData shdtShizai; // 資材データ保持
	private CostMaterialData cmdtGenka; // 原価原料データ保持
	private ArrayList aryHaigou; // 配合データ配列
	private ArrayList aryShisakuRetu; // 試作列データ配列
	private ArrayList aryShisakuList; // 試作リストデータ配列
	private ArrayList arySeizo; // 製造工程データ配列
	private ArrayList aryShizai; // 資材データ配列
	private ArrayList aryGenka; // 原価原料データ配列
	private int intSelectKotei; // 選択工程
	private ArrayList arySelectGenka; // 選択原料
	private int intMaxKotei; // 最大工程順
	private ArrayList aryMaxGenka; // 最大原料順
	private int intSelectRetu; // 選択試作列
	private ArrayList arySelectTyui; // 選択注意事項No
	private int intMaxRetu; // 最大試作列順
	private int[] intOutput; // 常に表示保持
	private ArrayList aryCopyKeisan; // 試作列コピー計算配列
	// private OutputTrialList otltShisakuHyo; //試作表データ保持
	// private OutputSample osltSample; //サンプル説明書データ保持
	// private XmlData xdtData; //XMLデータ
	private ExceptionBase ex; // エラーハンドリング
	private boolean HenkouFg; // データ変更フラグ

	/**
	 * 0000.コンストラクタ : スーパークラスのコンストラクタを定義。初期化を行う。
	 * 
	 * @param xdtData
	 *            : XMLデータ
	 */
	public TrialTblData() throws ExceptionBase {
		super();

		try {
			// 選択原料初期化
			arySelectGenka = new ArrayList();
			arySelectGenka.add(new ArrayList());
			arySelectGenka.add(new ArrayList());

			// データ変更フラグ初期化
			HenkouFg = false;

			// エラークラス
			this.ex = new ExceptionBase();

		} catch (Exception e) {
			// エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("試作テーブルデータ保持のコンストラクタが失敗しました");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/**
	 * 0001_1.データ設定（新規） : 試作テーブルデータを生成する。
	 * 
	 * @param flg
	 *            　：　0=全新規,1=特徴コピー
	 * @throws ExceptionBase
	 */
	public void setTraialData(int flg) throws ExceptionBase {

		try {
			aryHaigou = new ArrayList();
			aryShisakuRetu = new ArrayList();
			aryShisakuList = new ArrayList();
			arySeizo = new ArrayList();
			aryShizai = new ArrayList();
			aryGenka = new ArrayList();

			// 【KPX1500671】データ変更フラグの設定タイミング変更 DEL start
			// データ変更
			// HenkouFg = true;
			// 【KPX1500671】 DEL end

			/**********************************************************
			 * 　T110格納
			 *********************************************************/
			/***************** 試作品データへ値セット ********************/
			// 全新規の場合
			if (flg == 0) {
				ptdtShaisakuHin = new PrototypeData();

				// 　指定工場-会社CD
				ptdtShaisakuHin.setIntKaishacd(Integer.parseInt(DataCtrl
						.getInstance().getKaishaData().getArtKaishaCd().get(0)
						.toString()));
				// 　指定工場-工場CD
				ptdtShaisakuHin.setIntKojoco(DataCtrl.getInstance()
						.getUserMstData().getIntBushocd());
				// ADD 2013/06/19 ogawa 【QP@30151】No.9 start
				// 　販責会社CD
				ptdtShaisakuHin.setIntHansekicd(Integer.parseInt(DataCtrl
						.getInstance().getHansekiData().getArtKaishaCd().get(0)
						.toString()));
				// ADD 2013/06/19 ogawa 【QP@30151】No.9 end

				// 特徴コピーの場合
			} else {

				// キー項目
				ptdtShaisakuHin.setDciShisakuUser(null);
				ptdtShaisakuHin.setDciShisakuYear(null);
				ptdtShaisakuHin.setDciShisakuNum(null);

				// 廃止
				ptdtShaisakuHin.setIntHaisi(0);

				// 2011/05/11 QP@10181_No.42_49_72 TT Nishigawa Add Start
				// -------------------------
				// 工程パターン
				ptdtShaisakuHin.setStrPt_kotei(null);
				// 2011/05/11 QP@10181_No.42_49_72 TT Nishigawa Add End
				// --------------------------

			}

			// 　グループCD
			ptdtShaisakuHin.setIntGroupcd(DataCtrl.getInstance()
					.getUserMstData().getIntGroupcd());

			// 　チームCD
			ptdtShaisakuHin.setIntTeamcd(DataCtrl.getInstance()
					.getUserMstData().getIntTeamcd());

			// 　グループ名
			ptdtShaisakuHin.setStrGroupNm(DataCtrl.getInstance()
					.getUserMstData().getStrGroupnm());

			// 　チーム名
			ptdtShaisakuHin.setStrTeamNm(DataCtrl.getInstance()
					.getUserMstData().getStrTeamnm());

			// 　製法試作
			ptdtShaisakuHin.setIntSeihoShisaku(0);

			// 　登録者ID
			ptdtShaisakuHin.setDciTorokuid(DataCtrl.getInstance()
					.getUserMstData().getDciUserid());

			// 　登録日付
			ptdtShaisakuHin.setStrTorokuhi(getSysDate());

			// 　更新者ID
			ptdtShaisakuHin.setDciKosinid(DataCtrl.getInstance()
					.getUserMstData().getDciUserid());

			// 　更新日付
			// UPD 2012.4.30　【H24年度対応】更新日で試作品の更新排他制御を行うため更新日無しでデータを作成。
			// ptdtShaisakuHin.setStrKosinhi(getSysDate());
			ptdtShaisakuHin.setStrKosinhi("");

			// 　登録者名
			ptdtShaisakuHin.setStrTorokuNm(DataCtrl.getInstance()
					.getUserMstData().getStrUsernm());

			// 　更新者名
			ptdtShaisakuHin.setStrKosinNm(DataCtrl.getInstance()
					.getUserMstData().getStrUsernm());

			// 2010/02/25 NAKAMURA ADD START--------------
			// 排他会社名
			ptdtShaisakuHin.setStrHaitaKaishaNm(DataCtrl.getInstance()
					.getUserMstData().getStrHaitaKaishanm());
			// 排他部署名
			ptdtShaisakuHin.setStrHaitaBushoNm(DataCtrl.getInstance()
					.getUserMstData().getStrHaitaBushonm());
			// 排他氏名
			ptdtShaisakuHin.setStrHaitaShimei(DataCtrl.getInstance()
					.getUserMstData().getStrHaitaShimei());
			// 2010/02/25 NAKAMURA ADD END----------------

			// 【QP@20505_No.21】2012/10/17 TT H.SHIMA ADD Start
			ptdtShaisakuHin.setStrSecret(null);
			// 【QP@20505_No.21】2012/10/17 TT H.SHIMA ADD End

			/**********************************************************
			 * 　T120格納
			 *********************************************************/
			MixedData addMidtHaigou = new MixedData();

			/***************** 配合データへ値セット *********************/
			// 　工程CD
			addMidtHaigou.setIntKoteiCd(1);

			// 　工程SEQ
			addMidtHaigou.setIntKoteiSeq(1);

			// 　工程順
			addMidtHaigou.setIntKoteiNo(1);

			// 　原料順
			addMidtHaigou.setIntGenryoNo(1);

			// 　会社CD
			addMidtHaigou.setIntKaishaCd(ptdtShaisakuHin.getIntKaishacd());

			// 　部署CD
			addMidtHaigou.setIntBushoCd(ptdtShaisakuHin.getIntKojoco());

			// 　登録者ID
			addMidtHaigou.setDciTorokuId(DataCtrl.getInstance()
					.getUserMstData().getDciUserid());

			// 　登録日付
			addMidtHaigou.setStrTorokuHi(getSysDate());

			// 　更新者ID
			addMidtHaigou.setDciKosinId(DataCtrl.getInstance().getUserMstData()
					.getDciUserid());

			// 　更新日付
			addMidtHaigou.setStrKosinHi(getSysDate());

			// 　登録者名
			addMidtHaigou.setStrTorokuNm(DataCtrl.getInstance()
					.getUserMstData().getStrUsernm());

			// 　更新者名
			addMidtHaigou.setStrKosinNm(DataCtrl.getInstance().getUserMstData()
					.getStrUsernm());

			// 　配合データ配列へ追加
			aryHaigou.add(addMidtHaigou);

			/**********************************************************
			 * 　T131格納
			 *********************************************************/
			TrialData addTldtShisakuRetu = new TrialData();

			/***************** 試作列データへ値セット *******************/
			// 　試作SEQ
			addTldtShisakuRetu.setIntShisakuSeq(1);

			// 　試作表示順
			addTldtShisakuRetu.setIntHyojiNo(1);

			// 　登録者ID
			addTldtShisakuRetu.setDciTorokuId(DataCtrl.getInstance()
					.getUserMstData().getDciUserid());

			// 　登録日付
			addTldtShisakuRetu.setStrTorokuHi(getSysDate());

			// 　更新者ID
			addTldtShisakuRetu.setDciKosinId(DataCtrl.getInstance()
					.getUserMstData().getDciUserid());

			// 　更新日付
			addTldtShisakuRetu.setStrkosinHi(getSysDate());

			// 　登録者名
			addTldtShisakuRetu.setStrTorokuNm(DataCtrl.getInstance()
					.getUserMstData().getStrUsernm());

			// 　更新者名
			addTldtShisakuRetu.setStrKosinNm(DataCtrl.getInstance()
					.getUserMstData().getStrUsernm());

			// 　試作列データ配列へ追加
			aryShisakuRetu.add(addTldtShisakuRetu);

			/**********************************************************
			 * 　T132格納
			 *********************************************************/
			PrototypeListData addPldtShisakuList = new PrototypeListData();

			/***************** 試作リストデータへ値セット *****************/
			// 　試作SEQ
			addPldtShisakuList.setIntShisakuSeq(1);

			// 　工程CD
			addPldtShisakuList.setIntKoteiCd(1);

			// 　工程SEQ
			addPldtShisakuList.setIntKoteiSeq(1);

			// 　登録者ID
			addPldtShisakuList.setDciTorokuId(DataCtrl.getInstance()
					.getUserMstData().getDciUserid());

			// 　登録日付
			addPldtShisakuList.setStrTorokuHi(getSysDate());

			// 　更新者ID
			addPldtShisakuList.setDciKosinId(DataCtrl.getInstance()
					.getUserMstData().getDciUserid());

			// 　更新日付
			addPldtShisakuList.setStrKosinHi(getSysDate());

			// 　登録者名
			addPldtShisakuList.setStrTorokuNm(DataCtrl.getInstance()
					.getUserMstData().getStrUsernm());

			// 　更新者名
			addPldtShisakuList.setStrKosinNm(DataCtrl.getInstance()
					.getUserMstData().getStrUsernm());

			// 　試作リストデータ配列へ追加
			aryShisakuList.add(addPldtShisakuList);

			/**********************************************************
			 * 　T133格納
			 *********************************************************/
			arySeizo = new ArrayList();

			/**********************************************************
			 * 　T140格納
			 *********************************************************/
			ShizaiData addShdtShizai = new ShizaiData();

			/***************** 資材データへ値セット *********************/
			// 　資材SEQ
			addShdtShizai.setIntShizaiSeq(1);

			// 　表示順
			addShdtShizai.setIntHyojiNo(1);

			// 　登録者ID
			addShdtShizai.setDciTorokuId(DataCtrl.getInstance()
					.getUserMstData().getDciUserid());

			// 　登録日付
			addShdtShizai.setStrTorokuHi(getSysDate());

			// 　更新者ID
			addShdtShizai.setDciKosinId(DataCtrl.getInstance().getUserMstData()
					.getDciUserid());

			// 　更新日付
			addShdtShizai.setStrKosinHi(getSysDate());

			// 　登録者名
			addShdtShizai.setStrTorokuNm(DataCtrl.getInstance()
					.getUserMstData().getStrUsernm());

			// 　更新者名
			addShdtShizai.setStrKosinNm(DataCtrl.getInstance().getUserMstData()
					.getStrUsernm());

			// 資材データ配列へ追加
			aryShizai.add(addShdtShizai);

			/**********************************************************
			 * 　T141格納
			 *********************************************************/
			CostMaterialData addCmdtGenka = new CostMaterialData();

			/***************** 原価原料データへ値セット ******************/
			// 　試作SEQ
			addCmdtGenka.setIntShisakuSeq(1);

			// 　登録者ID
			addCmdtGenka.setDciTorokuId(DataCtrl.getInstance().getUserMstData()
					.getDciUserid());

			// 　登録日付
			addCmdtGenka.setStrTorokuHi(getSysDate());

			// 　更新者ID
			addCmdtGenka.setDciKosinId(DataCtrl.getInstance().getUserMstData()
					.getDciUserid());

			// 　更新日付
			addCmdtGenka.setStrKosinHi(getSysDate());

			// 　登録者名
			addCmdtGenka.setStrTorokuNm(DataCtrl.getInstance().getUserMstData()
					.getStrUsernm());

			// 　更新者名
			addCmdtGenka.setStrKosinNm(DataCtrl.getInstance().getUserMstData()
					.getStrUsernm());

			// 原価原料データ配列へ追加
			aryGenka.add(addCmdtGenka);

		} catch (Exception e) {
			// エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("試作テーブルデータ保持のデータ設定（新規）が失敗しました");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}
	}

	/**
	 * 0001.データ設定（詳細or製法コピー） : XMLデータより試作テーブルデータを生成する。
	 * 
	 * @param xdtData
	 *            : XMLデータ
	 * @throws ExceptionBase
	 */
	public void setTraialData(XmlData xdtSetXml) throws ExceptionBase {

		try {
			ptdtShaisakuHin = new PrototypeData();
			aryHaigou = new ArrayList();
			aryShisakuRetu = new ArrayList();
			aryShisakuList = new ArrayList();
			arySeizo = new ArrayList();
			aryShizai = new ArrayList();
			aryGenka = new ArrayList();

			XmlData xdtData = xdtSetXml;

			/**********************************************************
			 * 　T110格納
			 *********************************************************/
			// 　ID格納
			String strKinoId = "tr_shisakuhin";

			// 　全体配列取得
			ArrayList t110 = xdtData.GetAryTag(strKinoId);

			// 　テーブル配列取得
			ArrayList tableT110 = (ArrayList) t110.get(0);

			// 　レコード取得
			for (int i = 1; i < tableT110.size(); i++) {
				// 　１レコード取得
				ArrayList recData = ((ArrayList) ((ArrayList) tableT110.get(i))
						.get(0));

				// 　データ格納
				for (int j = 0; j < recData.size(); j++) {

					// 　項目名取得
					String recNm = ((String[]) recData.get(j))[1];
					// 　項目値取得
					String recVal = ((String[]) recData.get(j))[2];

					/***************** 試作品データへ値セット *********************/
					// 　試作CD-社員CD
					if (recNm == "cd_shain") {
						ptdtShaisakuHin
								.setDciShisakuUser(checkNullDecimal(recVal));

						// 　試作CD-年
					}
					if (recNm == "nen") {
						ptdtShaisakuHin
								.setDciShisakuYear(checkNullDecimal(recVal));

						// 　試作CD-追番
					}
					if (recNm == "no_oi") {
						ptdtShaisakuHin
								.setDciShisakuNum(checkNullDecimal(recVal));

						// 　依頼番号
					}
					if (recNm == "no_irai") {
						ptdtShaisakuHin.setStrIrai(checkNullString(recVal));

						// 　品名
					}
					if (recNm == "nm_hin") {
						ptdtShaisakuHin.setStrHinnm(checkNullString(recVal));
						// ADD 2013/06/19 ogawa 【QP@30151】No.9 start
						// 　販責会社CD
					}
					if (recNm == "cd_hanseki") {
						ptdtShaisakuHin.setIntHansekicd(checkNullInt(recVal));
						// ADD 2013/06/19 ogawa 【QP@30151】No.9 start

						// 　指定工場-会社CD
					}
					if (recNm == "cd_kaisha") {
						ptdtShaisakuHin.setIntKaishacd(checkNullInt(recVal));

						// 　指定工場-工場CD
					}
					if (recNm == "cd_kojo") {
						ptdtShaisakuHin.setIntKojoco(checkNullInt(recVal));

						// 　種別CD
					}
					if (recNm == "cd_shubetu") {
						ptdtShaisakuHin.setStrShubetu(checkNullString(recVal));

						// 　種別No
					}
					if (recNm == "no_shubetu") {
						ptdtShaisakuHin
								.setStrShubetuNo(checkNullString(recVal));

						// 　グループCD
					}
					if (recNm == "cd_group") {
						ptdtShaisakuHin.setIntGroupcd(checkNullInt(recVal));

						// 　チームCD
					}
					if (recNm == "cd_team") {
						ptdtShaisakuHin.setIntTeamcd(checkNullInt(recVal));

						// 　グループ名
					}
					if (recNm == "nm_group") {
						ptdtShaisakuHin.setStrGroupNm(checkNullString(recVal));

						// 　チーム名
					}
					if (recNm == "nm_team") {
						ptdtShaisakuHin.setStrTeamNm(checkNullString(recVal));

						// 　一括表示CD
					}
					if (recNm == "cd_ikatu") {
						ptdtShaisakuHin.setStrIkatu(checkNullString(recVal));

						// 　ジャンルCD
					}
					if (recNm == "cd_genre") {
						ptdtShaisakuHin.setStrZyanru(checkNullString(recVal));

						// 　ユーザCD
					}
					if (recNm == "cd_user") {
						ptdtShaisakuHin.setStrUsercd(checkNullString(recVal));

						// 　特徴原料
					}
					if (recNm == "tokuchogenryo") {
						ptdtShaisakuHin.setStrTokutyo(checkNullString(recVal));

						// 　用途
					}
					if (recNm == "youto") {
						ptdtShaisakuHin.setStrYoto(checkNullString(recVal));

						// 　価格帯CD
					}
					if (recNm == "cd_kakaku") {
						ptdtShaisakuHin.setStrKakaku(checkNullString(recVal));

						// 　担当営業CD
					}
					if (recNm == "cd_eigyo") {
						ptdtShaisakuHin
								.setStrTantoEigyo(checkNullString(recVal));

						// 　製造方法CD
					}
					if (recNm == "cd_hoho") {
						ptdtShaisakuHin.setStrSeizocd(checkNullString(recVal));

						// 　充填方法CD
					}
					if (recNm == "cd_juten") {
						ptdtShaisakuHin.setStrZyutencd(checkNullString(recVal));

						// 　殺菌方法
					}
					if (recNm == "hoho_sakin") {
						ptdtShaisakuHin.setStrSakin(checkNullString(recVal));

						// 　容器・包材
					}
					if (recNm == "youki") {
						ptdtShaisakuHin
								.setStrYokihozai(checkNullString(recVal));

						// 　容量
					}
					if (recNm == "yoryo") {
						ptdtShaisakuHin.setStrYoryo(checkNullString(recVal));

						// 　容量単位CD
					}
					if (recNm == "cd_tani") {
						ptdtShaisakuHin.setStrTani(checkNullString(recVal));

						// 　入り数
					}
					if (recNm == "su_iri") {
						ptdtShaisakuHin.setStrIrisu(checkNullString(recVal));

						// 　取扱温度CD
					}
					if (recNm == "cd_ondo") {
						ptdtShaisakuHin.setStrOndo(checkNullString(recVal));

						// 　賞味期間
					}
					if (recNm == "shomikikan") {
						ptdtShaisakuHin.setStrShomi(checkNullString(recVal));

						// 　原価
					}
					if (recNm == "genka") {
						ptdtShaisakuHin.setStrGenka(checkNullString(recVal));

						// 　売価
					}
					if (recNm == "baika") {
						ptdtShaisakuHin.setStrBaika(checkNullString(recVal));

						// 　想定物量
					}
					if (recNm == "buturyo") {
						ptdtShaisakuHin.setStrSotei(checkNullString(recVal));

						// 　発売時期
					}
					if (recNm == "dt_hatubai") {
						ptdtShaisakuHin.setStrHatubai(checkNullString(recVal));

						// 　計画売上
					}
					if (recNm == "uriage_k") {
						ptdtShaisakuHin
								.setStrKeikakuUri(checkNullString(recVal));

						// 　計画利益
					}
					if (recNm == "rieki_k") {
						ptdtShaisakuHin
								.setStrKeikakuRie(checkNullString(recVal));

						// 　販売後売上
					}
					if (recNm == "uriage_h") {
						ptdtShaisakuHin
								.setStrHanbaigoUri(checkNullString(recVal));

						// 　販売後利益
					}
					if (recNm == "rieki_h") {
						ptdtShaisakuHin
								.setStrHanbaigoRie(checkNullString(recVal));

						// 　荷姿CD
					}
					if (recNm == "cd_nisugata") {
						ptdtShaisakuHin
								.setStrNishugata(checkNullString(recVal));

						// 　総合ﾒﾓ
					}
					if (recNm == "memo") {
						ptdtShaisakuHin.setStrSogo(checkNullString(recVal));

						// 　小数指定
					}
					if (recNm == "keta_shosu") {
						ptdtShaisakuHin.setStrShosu(checkNullString(recVal));

						// 　廃止区
					}
					if (recNm == "kbn_haishi") {
						ptdtShaisakuHin.setIntHaisi(checkNullInt(recVal));

						// 　排他
					}
					if (recNm == "kbn_haita") {
						ptdtShaisakuHin.setDciHaita(checkNullDecimal(recVal));

						// 　製法試作
					}
					if (recNm == "seq_shisaku") {
						ptdtShaisakuHin
								.setIntSeihoShisaku(checkNullInt(recVal));

						// 　試作メモ
					}
					if (recNm == "memo_shisaku") {
						ptdtShaisakuHin
								.setStrShisakuMemo(checkNullString(recVal));

						// 　注意事項表示
					}
					if (recNm == "flg_chui") {
						// 値が空文字でない場合
						if (!recVal.equals("")) {
							ptdtShaisakuHin.setIntChuiFg(Integer
									.parseInt(recVal));
						}

						// 　登録者ID
					}
					if (recNm == "id_toroku") {
						ptdtShaisakuHin
								.setDciTorokuid(checkNullDecimal(recVal));

						// 　登録日付
					}
					if (recNm == "dt_toroku") {
						ptdtShaisakuHin.setStrTorokuhi(checkNullString(recVal));

						// 　更新者ID
					}
					if (recNm == "id_koshin") {
						ptdtShaisakuHin.setDciKosinid(checkNullDecimal(recVal));

						// 　更新日付
					}
					if (recNm == "dt_koshin") {
						ptdtShaisakuHin.setStrKosinhi(checkNullString(recVal));

						// 　登録者名
					}
					if (recNm == "nm_toroku") {
						ptdtShaisakuHin.setStrTorokuNm(checkNullString(recVal));

						// 　更新者名
					}
					if (recNm == "nm_koshin") {
						ptdtShaisakuHin.setStrKosinNm(checkNullString(recVal));

						// 2010/02/25 NAKAMURA ADD
						// START----------------------------
						// 　排他会社名
					}
					if (recNm == "nm_kaisha_haita") {
						ptdtShaisakuHin
								.setStrHaitaKaishaNm(checkNullString(recVal));
						// 　排他部署名
					}
					if (recNm == "nm_busho_haita") {
						ptdtShaisakuHin
								.setStrHaitaBushoNm(checkNullString(recVal));
						// 　排他氏名
					}
					if (recNm == "nm_user_haita") {
						ptdtShaisakuHin
								.setStrHaitaShimei(checkNullString(recVal));
					}
					// 2010/02/25 NAKAMURA ADD END------------------------------

					// 【QP@00342】
					if (recNm == "nm_eigyo_tanto") {
						ptdtShaisakuHin
								.setStrNmEigyoTanto(checkNullString(recVal));
					}

					// 2011/05/30　【QP@10181_No.42_49_72】工程パターン制御　TT.NISHIGAWA　start
					if (recNm == "pt_kotei") {
						ptdtShaisakuHin.setStrPt_kotei(checkNullString(recVal));
					}
					// 2011/05/30　【QP@10181_No.42_49_72】工程パターン制御　TT.NISHIGAWA　end

					// 【QP@20505_No.38】2012/10/17 TT H.SHIMA ADD Start
					if (recNm == "flg_secret") {
						ptdtShaisakuHin.setStrSecret(checkNullString(recVal));
					}
					// 【QP@20505_No.38】2012/10/17 TT H.SHIMA ADD End

				}
				recData.clear();
			}
			tableT110.clear();
			t110.clear();

			/**********************************************************
			 * 　T120格納
			 *********************************************************/
			// 　ID格納
			strKinoId = "tr_haigo";

			// 　全体配列取得
			ArrayList t120 = xdtData.GetAryTag(strKinoId);

			// 　テーブル配列取得
			ArrayList tableT120 = (ArrayList) t120.get(0);

			// 　レコード取得
			for (int i = 1; i < tableT120.size(); i++) {

				// 　１レコード取得
				ArrayList recData = ((ArrayList) ((ArrayList) tableT120.get(i))
						.get(0));
				// 　配合データ初期化
				midtHaigou = new MixedData();

				// 　データへ格納
				for (int j = 0; j < recData.size(); j++) {

					// 　項目名取得
					String recNm = ((String[]) recData.get(j))[1];
					// 　項目値取得
					String recVal = ((String[]) recData.get(j))[2];

					/***************** 配合データへ値セット *********************/
					// 　試作CD-社員CD
					if (recNm == "cd_shain") {
						midtHaigou.setDciShisakuUser(checkNullDecimal(recVal));

						// 　試作CD-年
					}
					if (recNm == "nen") {
						midtHaigou.setDciShisakuYear(checkNullDecimal(recVal));

						// 　試作CD-追番
					}
					if (recNm == "no_oi") {
						midtHaigou.setDciShisakuNum(checkNullDecimal(recVal));

						// 　工程CD
					}
					if (recNm == "cd_kotei") {
						midtHaigou.setIntKoteiCd(checkNullInt(recVal));

						// 　工程SEQ
					}
					if (recNm == "seq_kotei") {
						midtHaigou.setIntKoteiSeq(checkNullInt(recVal));

						// 　工程名
					}
					if (recNm == "nm_kotei") {
						midtHaigou.setStrKouteiNm(checkNullString(recVal));

						// 　工程属性
					}
					if (recNm == "zoku_kotei") {
						midtHaigou.setStrKouteiZokusei(checkNullString(recVal));

						// 　工程順
					}
					if (recNm == "sort_kotei") {
						midtHaigou.setIntKoteiNo(checkNullInt(recVal));

						// 　原料順
					}
					if (recNm == "sort_genryo") {
						midtHaigou.setIntGenryoNo(checkNullInt(recVal));

						// 　原料CD
					}
					if (recNm == "cd_genryo") {
						midtHaigou.setStrGenryoCd(checkNullString(recVal));

						// 　会社CD
					}
					if (recNm == "cd_kaisha") {
						midtHaigou.setIntKaishaCd(checkNullInt(recVal));

						// 　部署CD
					}
					if (recNm == "cd_busho") {
						midtHaigou.setIntBushoCd(checkNullInt(recVal));

						// 　原料名称
					}
					if (recNm == "nm_genryo") {
						midtHaigou.setStrGenryoNm(checkNullString(recVal));

						// 　単価
					}
					if (recNm == "tanka") {
						midtHaigou.setDciTanka(checkNullDecimal(recVal));

						// 　歩留
					}
					if (recNm == "budomari") {
						midtHaigou.setDciBudomari(checkNullDecimal(recVal));

						// 　油含有率
					}
					if (recNm == "ritu_abura") {
						midtHaigou.setDciGanyuritu(checkNullDecimal(recVal));

						// 　酢酸
					}
					if (recNm == "ritu_sakusan") {
						midtHaigou.setDciSakusan(checkNullDecimal(recVal));

						// 　食塩
					}
					if (recNm == "ritu_shokuen") {
						midtHaigou.setDciShokuen(checkNullDecimal(recVal));
						// ADD start 20121002 QP@20505 No.24
						// 　ＭＳＧ
					}
					if (recNm == "ritu_msg") {
						midtHaigou.setDciMsg(checkNullDecimal(recVal));
						// ADD end 20121002 QP@20505 No.24
						// 　総酸
					}
					if (recNm == "ritu_sousan") {
						midtHaigou.setDciSosan(checkNullDecimal(recVal));

						// 　色
					}
					if (recNm == "color") {
						midtHaigou.setStrIro(checkNullString(recVal));

						// 　登録者ID
					}
					if (recNm == "id_toroku") {
						midtHaigou.setDciTorokuId(checkNullDecimal(recVal));

						// 　登録日付
					}
					if (recNm == "dt_toroku") {
						midtHaigou.setStrTorokuHi(checkNullString(recVal));

						// 　更新者ID
					}
					if (recNm == "id_koshin") {
						midtHaigou.setDciKosinId(checkNullDecimal(recVal));

						// 　更新日付
					}
					if (recNm == "dt_koshin") {
						midtHaigou.setStrKosinHi(checkNullString(recVal));

						// 　登録者名
					}
					if (recNm == "nm_toroku") {
						midtHaigou.setStrTorokuNm(checkNullString(recVal));

						// 　更新者名
					}
					if (recNm == "nm_koshin") {
						midtHaigou.setStrKosinNm(checkNullString(recVal));

					}

					// add start
					// -------------------------------------------------------------------------------
					// QP@00412_シサクイック改良 No.31
					// 　更新者名
					if (recNm == "ma_budomari") {
						midtHaigou.setDciMaBudomari(checkNullDecimal(recVal));
					}
					// add end
					// -------------------------------------------------------------------------------

				}
				// 　配合データ配列へ追加
				aryHaigou.add(midtHaigou);
				recData.clear();
			}
			tableT120.clear();
			t120.clear();

			/**********************************************************
			 * 　T131格納
			 *********************************************************/
			// 　ID格納
			strKinoId = "tr_shisaku";

			// 　全体配列取得
			ArrayList t131 = xdtData.GetAryTag(strKinoId);

			// 　テーブル配列取得
			ArrayList tableT131 = (ArrayList) t131.get(0);

			// 　レコード取得
			for (int i = 1; i < tableT131.size(); i++) {

				// 　１レコード取得
				ArrayList recData = ((ArrayList) ((ArrayList) tableT131.get(i))
						.get(0));
				// 　試作列データ初期化
				tldtShisakuRetu = new TrialData();

				// 　データへ格納
				for (int j = 0; j < recData.size(); j++) {

					// 　項目名取得
					String recNm = ((String[]) recData.get(j))[1];
					// 　項目値取得
					String recVal = ((String[]) recData.get(j))[2];

					/***************** 試作列データへ値セット *********************/
					// 　試作CD-社員CD
					if (recNm == "cd_shain") {
						tldtShisakuRetu
								.setDciShisakuUser(checkNullDecimal(recVal));

						// 　試作CD-年
					}
					if (recNm == "nen") {
						tldtShisakuRetu
								.setDciShisakuYear(checkNullDecimal(recVal));

						// 　試作CD-追番
					}
					if (recNm == "no_oi") {
						tldtShisakuRetu
								.setDciShisakuNum(checkNullDecimal(recVal));

						// 　試作SEQ
					}
					if (recNm == "seq_shisaku") {
						tldtShisakuRetu.setIntShisakuSeq(checkNullInt(recVal));

						// 　試作表示順
					}
					if (recNm == "sort_shisaku") {
						tldtShisakuRetu.setIntHyojiNo(checkNullInt(recVal));

						// 　注意事項NO
					}
					if (recNm == "no_chui") {
						tldtShisakuRetu.setStrTyuiNo(checkNullString(recVal));

						// 　サンプルNO（名称）
					}
					if (recNm == "nm_sample") {
						tldtShisakuRetu.setStrSampleNo(checkNullString(recVal));

						// 　メモ
					}
					if (recNm == "memo") {
						tldtShisakuRetu.setStrMemo(checkNullString(recVal));

						// 　印刷Flg
					}
					if (recNm == "flg_print") {
						tldtShisakuRetu.setIntInsatuFlg(checkNullInt(recVal));

						// 　自動計算Flg
					}
					if (recNm == "flg_auto") {
						tldtShisakuRetu.setIntZidoKei(checkNullInt(recVal));

						// 　原価試算No
					}
					if (recNm == "no_shisan") {
						tldtShisakuRetu.setIntGenkaShisan(checkNullInt(recVal));

						// 　製法No-1
					}
					if (recNm == "no_seiho1") {
						tldtShisakuRetu.setStrSeihoNo1(checkNullString(recVal));

						// 　製法No-2
					}
					if (recNm == "no_seiho2") {
						tldtShisakuRetu.setStrSeihoNo2(checkNullString(recVal));

						// 　製法No-3
					}
					if (recNm == "no_seiho3") {
						tldtShisakuRetu.setStrSeihoNo3(checkNullString(recVal));

						// 　製法No-4
					}
					if (recNm == "no_seiho4") {
						tldtShisakuRetu.setStrSeihoNo4(checkNullString(recVal));

						// 　製法No-5
					}
					if (recNm == "seiho_no5") {
						tldtShisakuRetu.setStrSeihoNo5(checkNullString(recVal));

						// 　総酸
					}
					if (recNm == "ritu_sousan") {
						tldtShisakuRetu.setDciSosan(checkNullDecimal(recVal));

						// 　総酸-出力Flg
					}
					if (recNm == "flg_sousan") {
						tldtShisakuRetu.setIntSosanFlg(checkNullInt(recVal));

						// 　食塩
					}
					if (recNm == "ritu_shokuen") {
						tldtShisakuRetu.setDciShokuen(checkNullDecimal(recVal));
						// ADD start 20121002 QP@20505 No.24
						// 　ＭＳＧ
					}
					if (recNm == "ritu_msg") {
						tldtShisakuRetu.setDciMsg(checkNullDecimal(recVal));
						// ADD end 20121002 QP@20505 No.24
						// 　食塩-出力Flg
					}
					if (recNm == "flg_shokuen") {
						tldtShisakuRetu.setIntShokuenFlg(checkNullInt(recVal));

						// 　水相中酸度
					}
					if (recNm == "sando_suiso") {
						tldtShisakuRetu
								.setDciSuiSando(checkNullDecimal(recVal));

						// 　水相中酸度-出力Flg
					}
					if (recNm == "flg_sando_suiso") {
						tldtShisakuRetu.setIntSuiSandoFlg(checkNullInt(recVal));

						// 　水相中食塩
					}
					if (recNm == "shokuen_suiso") {
						tldtShisakuRetu
								.setDciSuiShokuen(checkNullDecimal(recVal));

						// 　水相中食塩-出力Flg
					}
					if (recNm == "flg_shokuen_suiso") {
						tldtShisakuRetu
								.setIntSuiShokuenFlg(checkNullInt(recVal));

						// 　水相中酢酸
					}
					if (recNm == "sakusan_suiso") {
						tldtShisakuRetu
								.setDciSuiSakusan(checkNullDecimal(recVal));

						// 　水相中酢酸-出力Flg
					}
					if (recNm == "flg_sakusan_suiso") {
						tldtShisakuRetu
								.setIntSuiSakusanFlg(checkNullInt(recVal));

						// 　糖度
					}
					if (recNm == "toudo") {
						tldtShisakuRetu.setStrToudo(checkNullString(recVal));

						// 　糖度-出力Flg
					}
					if (recNm == "flg_toudo") {
						tldtShisakuRetu.setIntToudoFlg(checkNullInt(recVal));

						// 　粘度
					}
					if (recNm == "nendo") {
						tldtShisakuRetu.setStrNendo(checkNullString(recVal));

						// 　粘度-出力Flg
					}
					if (recNm == "flg_nendo") {
						tldtShisakuRetu.setIntNendoFlg(checkNullInt(recVal));

						// 　温度
					}
					if (recNm == "ondo") {
						tldtShisakuRetu.setStrOndo(checkNullString(recVal));

						// 　温度-出力Flg
					}
					if (recNm == "flg_ondo") {
						tldtShisakuRetu.setIntOndoFlg(checkNullInt(recVal));

						// 　PH
					}
					if (recNm == "ph") {
						tldtShisakuRetu.setStrPh(checkNullString(recVal));

						// 　PH - 出力Flg
					}
					if (recNm == "flg_ph") {
						tldtShisakuRetu.setIntPhFlg(checkNullInt(recVal));

						// 　総酸：分析
					}
					if (recNm == "ritu_sousan_bunseki") {
						tldtShisakuRetu
								.setStrSosanBunseki(checkNullString(recVal));

						// 　総酸：分析-出力Flg
					}
					if (recNm == "flg_sousan_bunseki") {
						tldtShisakuRetu
								.setIntSosanBunsekiFlg(checkNullInt(recVal));

						// 　食塩：分析
					}
					if (recNm == "ritu_shokuen_bunseki") {
						tldtShisakuRetu
								.setStrShokuenBunseki(checkNullString(recVal));

						// 　食塩：分析-出力Flg
					}
					if (recNm == "flg_shokuen_bunseki") {
						tldtShisakuRetu
								.setIntShokuenBunsekiFlg(checkNullInt(recVal));

						// 　比重
					}
					if (recNm == "hiju") {
						tldtShisakuRetu.setStrHizyu(checkNullString(recVal));

						// 　比重-出力Flg
					}
					if (recNm == "flg_hiju") {
						tldtShisakuRetu.setIntHizyuFlg(checkNullInt(recVal));

						// 　水分活性
					}
					if (recNm == "suibun_kasei") {
						tldtShisakuRetu.setStrSuibun(checkNullString(recVal));

						// 　水分活性-出力Flg
					}
					if (recNm == "flg_suibun_kasei") {
						tldtShisakuRetu.setIntSuibunFlg(checkNullInt(recVal));

						// 　アルコール
					}
					if (recNm == "alcohol") {
						tldtShisakuRetu.setStrArukoru(checkNullString(recVal));

						// 　アルコール-出力Flg
					}
					if (recNm == "flg_alcohol") {
						tldtShisakuRetu.setIntArukoruFlg(checkNullInt(recVal));

						// 　作成メモ
					}
					if (recNm == "memo_sakusei") {
						tldtShisakuRetu
								.setStrSakuseiMemo(checkNullString(recVal));

						// 　作成メモ-出力Flg
					}
					if (recNm == "flg_memo") {
						tldtShisakuRetu
								.setIntSakuseiMemoFlg(checkNullInt(recVal));

						// 　評価
					}
					if (recNm == "hyoka") {
						tldtShisakuRetu.setStrHyoka(checkNullString(recVal));

						// 　評価-出力Flg
					}
					if (recNm == "flg_hyoka") {
						tldtShisakuRetu.setIntHyokaFlg(checkNullInt(recVal));

						// 　フリー①タイトル
					}
					if (recNm == "free_title1") {
						tldtShisakuRetu
								.setStrFreeTitle1(checkNullString(recVal));

						// 　フリー①内容
					}
					if (recNm == "free_value1") {
						tldtShisakuRetu
								.setStrFreeNaiyo1(checkNullString(recVal));

						// 　フリー①-出力Flg
					}
					if (recNm == "flg_free1") {
						tldtShisakuRetu.setIntFreeFlg(checkNullInt(recVal));

						// 　フリー②タイトル
					}
					if (recNm == "free_title2") {
						tldtShisakuRetu
								.setStrFreeTitle2(checkNullString(recVal));

						// 　フリー②内容
					}
					if (recNm == "free_value2") {
						tldtShisakuRetu
								.setStrFreeNaiyo2(checkNullString(recVal));

						// 　フリー②-出力Flg
					}
					if (recNm == "flg_free2") {
						tldtShisakuRetu.setIntFreeFl2(checkNullInt(recVal));

						// 　フリー③タイトル
					}
					if (recNm == "free_title3") {
						tldtShisakuRetu
								.setStrFreeTitle3(checkNullString(recVal));

						// 　フリー③内容
					}
					if (recNm == "free_value3") {
						tldtShisakuRetu
								.setStrFreeNaiyo3(checkNullString(recVal));

						// 　フリー③-出力Flg
					}
					if (recNm == "flg_free3") {
						tldtShisakuRetu.setIntFreeFl3(checkNullInt(recVal));

						// 　試作日付
					}
					if (recNm == "dt_shisaku") {
						tldtShisakuRetu
								.setStrShisakuHi(checkNullString(recVal));

						// 仕上重量
					}
					if (recNm == "juryo_shiagari_g") {
						tldtShisakuRetu
								.setDciShiagari(checkNullDecimal(recVal));

						// 　登録者ID
					}
					if (recNm == "id_toroku") {
						tldtShisakuRetu.setDciTorokuId(new BigDecimal(recVal));

						// 　登録日付
					}
					if (recNm == "dt_toroku") {
						tldtShisakuRetu.setStrTorokuHi(checkNullString(recVal));

						// 　更新者ID
					}
					if (recNm == "id_koshin") {
						tldtShisakuRetu.setDciKosinId(checkNullDecimal(recVal));

						// 　更新日付
					}
					if (recNm == "dt_koshin") {
						tldtShisakuRetu.setStrkosinHi(checkNullString(recVal));

						// 　登録者名
					}
					if (recNm == "nm_toroku") {
						tldtShisakuRetu.setStrTorokuNm(checkNullString(recVal));

						// 　更新者名
					}
					if (recNm == "nm_koshin") {
						tldtShisakuRetu.setStrKosinNm(checkNullString(recVal));

					}
					// 2009/10/26 TT.Y.NISHIGAWA ADD START [原価試算：試算依頼flg]
					// 　試算依頼flg
					if (recNm == "flg_shisanIrai") {
						tldtShisakuRetu.setFlg_shisanIrai(Integer
								.parseInt(recVal));
						// 　試算依頼flg
						if (Integer.parseInt(recVal) == 1) {
							tldtShisakuRetu.setFlg_init(1);
						}
					}
					// 2009/10/26 TT.Y.NISHIGAWA ADD END [原価試算：試算依頼flg]
					// add start
					// -------------------------------------------------------------------------------
					// QP@00412_シサクイック改良 No.7
					if (recNm == "siki_keisan") {
						tldtShisakuRetu.setStrKeisanSiki(recVal);
					}
					// add end
					// -------------------------------------------------------------------------------
					// 2011/05/30　【QP@10181_No.42_49_72】工程パターン制御　TT.NISHIGAWA　start
					// 水相比重
					if (recNm == "hiju_sui") {
						tldtShisakuRetu.setStrHiju_sui(checkNullString(recVal));
					}
					// 水相比重　出力FG
					if (recNm == "flg_hiju_sui") {
						tldtShisakuRetu.setIntHiju_sui_fg(checkNullInt(recVal));
					}
					// 2011/05/30　【QP@10181_No.42_49_72】工程パターン制御　TT.NISHIGAWA　end
					// ADD start 20121009 QP@20505 No.24
					// 　水分活性 フリー タイトル
					if (recNm == "freetitle_suibun_kasei") {
						tldtShisakuRetu
								.setStrFreeTitleSuibunKasei(checkNullString(recVal));

						// 　水分活性 フリー 内容
					}
					if (recNm == "free_suibun_kasei") {
						tldtShisakuRetu
								.setStrFreeSuibunKasei(checkNullString(recVal));

						// 　水分活性 フリー -出力Flg
					}
					if (recNm == "flg_freeSuibunKasei") {
						tldtShisakuRetu
								.setIntFreeSuibunKaseiFlg(checkNullInt(recVal));

						// 　アルコール フリー タイトル
					}
					if (recNm == "freetitle_alcohol") {
						tldtShisakuRetu
								.setStrFreeTitleAlchol(checkNullString(recVal));

						// 　アルコール フリー 内容
					}
					if (recNm == "free_alcohol") {
						tldtShisakuRetu
								.setStrFreeAlchol(checkNullString(recVal));

						// 　アルコール フリー -出力Flg
					}
					if (recNm == "flg_freeAlchol") {
						tldtShisakuRetu
								.setIntFreeAlcholFlg(checkNullInt(recVal));

						// 　実効酢酸濃度
					}
					if (recNm == "jikkoSakusanNodo") {
						tldtShisakuRetu
								.setDciJikkoSakusanNodo(checkNullDecimal(recVal));

						// 　実効酢酸濃度-出力Flg
					}
					if (recNm == "flg_jikkoSakusanNodo") {
						tldtShisakuRetu
								.setIntJikkoSakusanNodoFlg(checkNullInt(recVal));

						// 　水相中ＭＳＧ
					}
					if (recNm == "msg_suiso") {
						tldtShisakuRetu
								.setDciSuisoMSG(checkNullDecimal(recVal));

						// 　水相中ＭＳＧ-出力Flg
					}
					if (recNm == "flg_msg_suiso") {
						tldtShisakuRetu.setIntSuisoMSGFlg(checkNullInt(recVal));

						// 　フリー粘度 タイトル
					}
					if (recNm == "freetitle_nendo") {
						tldtShisakuRetu
								.setStrFreeTitleNendo(checkNullString(recVal));

						// 　フリー粘度
					}
					if (recNm == "free_nendo") {
						tldtShisakuRetu
								.setStrFreeNendo(checkNullString(recVal));

						// 　フリー粘度-出力Flg
					}
					if (recNm == "flg_freeNendo") {
						tldtShisakuRetu
								.setIntFreeNendoFlg(checkNullInt(recVal));

						// 　フリー温度 タイトル
					}
					if (recNm == "freetitle_ondo") {
						tldtShisakuRetu
								.setStrFreeTitleOndo(checkNullString(recVal));

						// 　フリー温度
					}
					if (recNm == "free_ondo") {
						tldtShisakuRetu.setStrFreeOndo(checkNullString(recVal));

						// //　フリー温度-出力Flg
						// }if(recNm == "flg_freeOndo"){
						// tldtShisakuRetu.setIntFreeFlg4(checkNullInt(recVal));

						// 　フリー④タイトル
					}
					if (recNm == "free_title4") {
						tldtShisakuRetu
								.setStrFreeTitle4(checkNullString(recVal));

						// 　フリー④内容
					}
					if (recNm == "free_value4") {
						tldtShisakuRetu
								.setStrFreeNaiyo4(checkNullString(recVal));

						// 　フリー④-出力Flg
					}
					if (recNm == "flg_free4") {
						tldtShisakuRetu.setIntFreeFlg4(checkNullInt(recVal));

						// 　フリー⑤タイトル
					}
					if (recNm == "free_title5") {
						tldtShisakuRetu
								.setStrFreeTitle5(checkNullString(recVal));

						// 　フリー⑤内容
					}
					if (recNm == "free_value5") {
						tldtShisakuRetu
								.setStrFreeNaiyo5(checkNullString(recVal));

						// 　フリー⑤-出力Flg
					}
					if (recNm == "flg_free5") {
						tldtShisakuRetu.setIntFreeFlg5(checkNullInt(recVal));

						// 　フリー⑥タイトル
					}
					if (recNm == "free_title6") {
						tldtShisakuRetu
								.setStrFreeTitle6(checkNullString(recVal));

						// 　フリー⑥内容
					}
					if (recNm == "free_value6") {
						tldtShisakuRetu
								.setStrFreeNaiyo6(checkNullString(recVal));

						// 　フリー⑥-出力Flg
					}
					if (recNm == "flg_free6") {
						tldtShisakuRetu.setIntFreeFlg6(checkNullInt(recVal));

					}
					// ADD end 20121009 QP@20505 No.24
				}
				// 　試作列データ配列へ追加
				aryShisakuRetu.add(tldtShisakuRetu);
				recData.clear();
			}
			tableT131.clear();
			t131.clear();

			/**********************************************************
			 * 　T132格納
			 *********************************************************/
			// 　ID格納
			strKinoId = "tr_shisaku_list";

			// 　全体配列取得
			ArrayList t132 = xdtData.GetAryTag(strKinoId);

			// 　テーブル配列取得
			ArrayList tableT132 = (ArrayList) t132.get(0);

			// 　レコード取得
			for (int i = 1; i < tableT132.size(); i++) {

				// 　１レコード取得
				ArrayList recData = ((ArrayList) ((ArrayList) tableT132.get(i))
						.get(0));
				// 　試作リストデータ初期化
				pldtShisakuList = new PrototypeListData();

				// 　データへ格納
				for (int j = 0; j < recData.size(); j++) {

					// 　項目名取得
					String recNm = ((String[]) recData.get(j))[1];
					// 　項目値取得
					String recVal = ((String[]) recData.get(j))[2];

					/***************** 試作リストデータへ値セット *********************/
					// 　試作CD-社員CD
					if (recNm == "cd_shain") {
						pldtShisakuList
								.setDciShisakuUser(checkNullDecimal(recVal));

						// 　試作CD-年
					}
					if (recNm == "nen") {
						pldtShisakuList
								.setDciShisakuYear(checkNullDecimal(recVal));

						// 　試作CD-追番
					}
					if (recNm == "no_oi") {
						pldtShisakuList
								.setDciShisakuNum(checkNullDecimal(recVal));

						// 　試作SEQ
					}
					if (recNm == "seq_shisaku") {
						pldtShisakuList.setIntShisakuSeq(checkNullInt(recVal));

						// 　工程CD
					}
					if (recNm == "cd_kotei") {
						pldtShisakuList.setIntKoteiCd(checkNullInt(recVal));

						// 　工程SEQ
					}
					if (recNm == "seq_kotei") {
						pldtShisakuList.setIntKoteiSeq(checkNullInt(recVal));

						// 　量
					}
					if (recNm == "quantity") {
						pldtShisakuList.setDciRyo(checkNullDecimal(recVal));

						// 　色
					}
					if (recNm == "color") {
						pldtShisakuList.setStrIro(checkNullString(recVal));

						// ADD start 20120914 QP@20505 No.1
						// 　工程仕上重量
					}
					if (recNm == "juryo_shiagari_seq") {
						pldtShisakuList
								.setDciKouteiShiagari(checkNullDecimal(recVal));
						// ADD end 20120914 QP@20505 No.1

						// 　登録者ID
					}
					if (recNm == "id_toroku") {
						pldtShisakuList
								.setDciTorokuId(checkNullDecimal(recVal));

						// 　登録日付
					}
					if (recNm == "dt_toroku") {
						pldtShisakuList.setStrTorokuHi(checkNullString(recVal));

						// 　更新者ID
					}
					if (recNm == "id_koshin") {
						pldtShisakuList.setDciKosinId(checkNullDecimal(recVal));

						// 　更新日付
					}
					if (recNm == "dt_koshin") {
						pldtShisakuList.setStrKosinHi(checkNullString(recVal));

						// 　登録者名
					}
					if (recNm == "id_koshin") {
						pldtShisakuList.setStrTorokuNm(checkNullString(recVal));

						// 　更新者名
					}
					if (recNm == "dt_koshin") {
						pldtShisakuList.setStrKosinNm(checkNullString(recVal));

					}
					// // ADD start 20120928 QP@20505 No.24
					// //　粘度フリータイトル
					// if(recNm == "freetitle_nendo"){
					// tldtShisakuRetu.setStrFreeTitleNendo(checkNullString(recVal));
					//
					// //　粘度フリー内容
					// }if(recNm == "free_nendo"){
					// tldtShisakuRetu.setStrFreeNendo(checkNullString(recVal));
					//
					// //　粘度フリー-出力Flg
					// }if(recNm == "flg_freeNendo"){
					// tldtShisakuRetu.setIntFreeNendoFlg(checkNullInt(recVal));
					//
					// //　温度フリータイトル
					// }if(recNm == "freetitle_ondo"){
					// tldtShisakuRetu.setStrFreeTitleOndo(checkNullString(recVal));
					//
					// //　温度フリー内容
					// }if(recNm == "free_ondo"){
					// tldtShisakuRetu.setStrFreeOndo(checkNullString(recVal));
					//
					// //　温度フリー-出力Flg
					// }if(recNm == "flg_freeOndo"){
					// tldtShisakuRetu.setIntFreeOndoFlg(checkNullInt(recVal));
					//
					// //　水分活性フリータイトル
					// }if(recNm == "freetitle_suibun_kasei"){
					// tldtShisakuRetu.setStrFreeTitleSuibunKasei(checkNullString(recVal));
					//
					// //　水分活性フリー内容
					// }if(recNm == "free_suibun_kasei"){
					// tldtShisakuRetu.setStrFreeSuibunKasei(checkNullString(recVal));
					//
					// //　水分活性フリー-出力Flg
					// }if(recNm == "flg_freeSuibunKasei"){
					// tldtShisakuRetu.setIntFreeSuibunKaseiFlg(checkNullInt(recVal));
					//
					// //　アルコール フリータイトル
					// }if(recNm == "freetitle_alcohol"){
					// tldtShisakuRetu.setStrFreeTitleAlchol(checkNullString(recVal));
					//
					// //　アルコール フリー内容
					// }if(recNm == "free_alcohol"){
					// tldtShisakuRetu.setStrFreeAlchol(checkNullString(recVal));
					//
					// //　アルコール フリー-出力Flg
					// }if(recNm == "flg_freeAlchol"){
					// tldtShisakuRetu.setIntFreeAlcholFlg(checkNullInt(recVal));
					//
					// //　実行酢酸濃度
					// }if(recNm == "jikkoSakusanNodo"){
					// tldtShisakuRetu.setDciJikkoSakusanNodo(checkNullDecimal(recVal));
					//
					// //　実行酢酸濃度-出力Flg
					// }if(recNm == "flg_jikkoSakusanNodo"){
					// tldtShisakuRetu.setIntJikkoSakusanNodoFlg(checkNullInt(recVal));
					//
					// //　水相中ＭＳＧ
					// }if(recNm == "msg_suiso"){
					// tldtShisakuRetu.setDciSuisoMSG(checkNullDecimal(recVal));
					//
					// //　水相中ＭＳＧ-出力Flg
					// }if(recNm == "flg_msg_suiso"){
					// tldtShisakuRetu.setIntSuisoMSGFlg(checkNullInt(recVal));
					//
					// //　フリー④タイトル
					// }if(recNm == "free_title4"){
					// tldtShisakuRetu.setStrFreeTitle4(checkNullString(recVal));
					//
					// //　フリー④内容
					// }if(recNm == "free_value4"){
					// tldtShisakuRetu.setStrFreeNaiyo4(checkNullString(recVal));
					//
					// //　フリー④-出力Flg
					// }if(recNm == "flg_free4"){
					// tldtShisakuRetu.setIntFreeFlg4(checkNullInt(recVal));
					//
					// //　フリー⑤タイトル
					// }if(recNm == "free_title5"){
					// tldtShisakuRetu.setStrFreeTitle5(checkNullString(recVal));
					//
					// //　フリー⑤内容
					// }if(recNm == "free_value5"){
					// tldtShisakuRetu.setStrFreeNaiyo5(checkNullString(recVal));
					//
					// //　フリー⑤-出力Flg
					// }if(recNm == "flg_free5"){
					// tldtShisakuRetu.setIntFreeFlg5(checkNullInt(recVal));
					//
					// //　フリー⑥タイトル
					// }if(recNm == "free_title6"){
					// tldtShisakuRetu.setStrFreeTitle6(checkNullString(recVal));
					//
					// //　フリー⑥内容
					// }if(recNm == "free_value6"){
					// tldtShisakuRetu.setStrFreeNaiyo6(checkNullString(recVal));
					//
					// //　フリー⑥-出力Flg
					// }if(recNm == "flg_free6"){
					// tldtShisakuRetu.setIntFreeFlg6(checkNullInt(recVal));
					//
					// }
					// // ADD end 20120928 QP@20505 No.24
				}
				// 　試作リストデータ配列へ追加
				aryShisakuList.add(pldtShisakuList);
				recData.clear();
			}
			tableT132.clear();
			t132.clear();

			/**********************************************************
			 * 　T133格納
			 *********************************************************/
			// 　ID格納
			strKinoId = "tr_cyuui";

			// 　全体配列取得
			ArrayList t133 = xdtData.GetAryTag(strKinoId);

			// 　テーブル配列取得
			ArrayList tableT133 = (ArrayList) t133.get(0);

			// 　レコード取得
			for (int i = 1; i < tableT133.size(); i++) {

				// 　１レコード取得
				ArrayList recData = ((ArrayList) ((ArrayList) tableT133.get(i))
						.get(0));
				// 　製造工程データ初期化
				mfdtSeizo = new ManufacturingData();

				// 　データへ格納
				for (int j = 0; j < recData.size(); j++) {

					// 　項目名取得
					String recNm = ((String[]) recData.get(j))[1];
					// 　項目値取得
					String recVal = ((String[]) recData.get(j))[2];

					/***************** 製造工程データへ値セット *********************/
					// 　試作CD-社員CD
					if (recNm == "cd_shain") {
						mfdtSeizo.setDciShisakuUser(checkNullDecimal(recVal));

						// 　試作CD-年
					}
					if (recNm == "nen") {
						mfdtSeizo.setDciShisakuYear(checkNullDecimal(recVal));

						// 　試作CD-追番
					}
					if (recNm == "no_oi") {
						mfdtSeizo.setDciShisakuNum(checkNullDecimal(recVal));

						// 　注意事項NO
					}
					if (recNm == "no_chui") {
						mfdtSeizo.setIntTyuiNo(checkNullInt(recVal));

						// 　注意事項
					}
					if (recNm == "chuijiko") {
						mfdtSeizo.setStrTyuiNaiyo(checkNullString(recVal));

						// 　登録者ID
					}
					if (recNm == "id_toroku") {
						mfdtSeizo.setDciTorokuId(checkNullDecimal(recVal));

						// 　登録日付
					}
					if (recNm == "dt_toroku") {
						mfdtSeizo.setStrTorokuHi(checkNullString(recVal));

						// 　更新者ID
					}
					if (recNm == "id_koshin") {
						mfdtSeizo.setDciKosinId(checkNullDecimal(recVal));

						// 　更新日付
					}
					if (recNm == "dt_koshin") {
						mfdtSeizo.setStrKosinHi(checkNullString(recVal));

						// 　登録者名
					}
					if (recNm == "nm_toroku") {
						mfdtSeizo.setStrTorokuNm(checkNullString(recVal));

						// 　更新者名
					}
					if (recNm == "nm_koshin") {
						mfdtSeizo.setStrKosinNm(checkNullString(recVal));

					}

				}
				// 製造工程データ配列へ追加
				arySeizo.add(mfdtSeizo);
				recData.clear();
			}
			tableT133.clear();
			t133.clear();

			/**********************************************************
			 * 　T140格納
			 *********************************************************/
			// 　ID格納
			strKinoId = "tr_shizai";

			// 　全体配列取得
			ArrayList t140 = xdtData.GetAryTag(strKinoId);

			// 　テーブル配列取得
			ArrayList tableT140 = (ArrayList) t140.get(0);

			// 　レコード取得
			for (int i = 1; i < tableT140.size(); i++) {
				// 　１レコード取得
				ArrayList recData = ((ArrayList) ((ArrayList) tableT140.get(i))
						.get(0));
				// 　資材データ初期化
				shdtShizai = new ShizaiData();

				// 　データへ格納
				for (int j = 0; j < recData.size(); j++) {

					// 　項目名取得
					String recNm = ((String[]) recData.get(j))[1];
					// 　項目値取得
					String recVal = ((String[]) recData.get(j))[2];

					/***************** 資材データへ値セット *********************/
					// 　試作CD-社員CD
					if (recNm == "cd_shain") {
						shdtShizai.setDciShisakuUser(checkNullDecimal(recVal));

						// 　試作CD-年
					}
					if (recNm == "nen") {
						shdtShizai.setDciShisakuYear(checkNullDecimal(recVal));

						// 　試作CD-追番
					}
					if (recNm == "no_oi") {
						shdtShizai.setDciShisakuNum(checkNullDecimal(recVal));

						// 　資材SEQ
					}
					if (recNm == "seq_shizai") {
						shdtShizai.setIntShizaiSeq(checkNullInt(recVal));

						// 　表示順
					}
					if (recNm == "no_sort") {
						shdtShizai.setIntHyojiNo(checkNullInt(recVal));

						// 　資材CD
					}
					if (recNm == "cd_shizai") {
						shdtShizai.setStrShizaiCd(checkNullString(recVal));

						// 　資材名称
					}
					if (recNm == "nm_shizai") {
						shdtShizai.setStrShizaiNm(checkNullString(recVal));

						// 　単価
					}
					if (recNm == "tanka") {
						shdtShizai.setDciTanka(checkNullDecimal(recVal));

						// 　歩留
					}
					if (recNm == "budomari") {
						shdtShizai.setDciBudomari(checkNullDecimal(recVal));

						// 　登録者ID
					}
					if (recNm == "id_toroku") {
						shdtShizai.setDciTorokuId(checkNullDecimal(recVal));

						// 　登録日付
					}
					if (recNm == "dt_toroku") {
						shdtShizai.setStrTorokuHi(checkNullString(recVal));

						// 　更新者ID
					}
					if (recNm == "id_koshin") {
						shdtShizai.setDciKosinId(checkNullDecimal(recVal));

						// 　更新日付
					}
					if (recNm == "dt_koshin") {
						shdtShizai.setStrKosinHi(checkNullString(recVal));

						// 　登録者名
					}
					if (recNm == "nm_toroku") {
						shdtShizai.setStrTorokuNm(checkNullString(recVal));

						// 　更新者名
					}
					if (recNm == "nm_koshin") {
						shdtShizai.setStrKosinNm(checkNullString(recVal));

					}

				}
				// 資材データ配列へ追加
				aryShizai.add(shdtShizai);
				recData.clear();
			}
			tableT140.clear();
			t140.clear();

			/**********************************************************
			 * 　T141格納
			 *********************************************************/
			// 　ID格納
			strKinoId = "tr_genryo";

			// 　全体配列取得
			ArrayList t141 = xdtData.GetAryTag(strKinoId);

			// 　テーブル配列取得
			ArrayList tableT141 = (ArrayList) t141.get(0);

			// 　レコード取得
			for (int i = 1; i < tableT141.size(); i++) {
				// 　１レコード取得
				ArrayList recData = ((ArrayList) ((ArrayList) tableT141.get(i))
						.get(0));
				// 　原価原料データ初期化
				cmdtGenka = new CostMaterialData();

				// 　データへ格納
				for (int j = 0; j < recData.size(); j++) {

					// 　項目名取得
					String recNm = ((String[]) recData.get(j))[1];
					// 　項目値取得
					String recVal = ((String[]) recData.get(j))[2];

					/***************** 原価原料データへ値セット *********************/
					// 　試作CD-社員CD
					if (recNm == "cd_shain") {
						cmdtGenka.setDciShisakuUser(checkNullDecimal(recVal));

						// 　試作CD-年
					}
					if (recNm == "nen") {
						cmdtGenka.setDciShisakuYear(checkNullDecimal(recVal));

						// 　試作CD-追番
					}
					if (recNm == "no_oi") {
						cmdtGenka.setDciShisakuNum(checkNullDecimal(recVal));

						// 　試作SEQ
					}
					if (recNm == "seq_shisaku") {
						cmdtGenka.setIntShisakuSeq(checkNullInt(recVal));

						// 　印刷flg
					}
					if (recNm == "flg_print") {
						cmdtGenka.setIntinsatu(checkNullInt(recVal));

						// 　重点量水相
					}
					if (recNm == "zyusui") {
						cmdtGenka.setStrZyutenSui(checkNullString(recVal));

						// 　重点量油相
					}
					if (recNm == "zyuabura") {
						cmdtGenka.setStrZyutenYu(checkNullString(recVal));

						// 　合計量
					}
					if (recNm == "gokei") {
						cmdtGenka.setStrGokei(checkNullString(recVal));

						// 　原料費
					}
					if (recNm == "genryohi") {
						cmdtGenka.setStrGenryohi(checkNullString(recVal));

						// 　原料費（1本）
					}
					if (recNm == "genryohi1") {
						cmdtGenka.setStrGenryohiTan(checkNullString(recVal));

						// 　比重
					}
					if (recNm == "hizyu") {
						cmdtGenka.setStrHizyu(checkNullString(recVal));

						// 　容量
					}
					if (recNm == "yoryo") {
						cmdtGenka.setStrYoryo(checkNullString(recVal));

						// 　入数
					}
					if (recNm == "irisu") {
						cmdtGenka.setStrIrisu(checkNullString(recVal));

						// 　有効歩留
					}
					if (recNm == "yukobudomari") {
						cmdtGenka.setStrYukoBudomari(checkNullString(recVal));

						// 　レベル量
					}
					if (recNm == "reberu") {
						cmdtGenka.setStrLevel(checkNullString(recVal));

						// 　比重歩留
					}
					if (recNm == "hizyubudomari") {
						cmdtGenka.setStrHizyuBudomari(checkNullString(recVal));

						// 　平均充填量
					}
					if (recNm == "heikinzyu") {
						cmdtGenka.setStrZyutenAve(checkNullString(recVal));

						// 　1C/S原料費
					}
					if (recNm == "cs_genryo") {
						cmdtGenka.setStrGenryohiCs(checkNullString(recVal));

						// 　1C/S材料費
					}
					if (recNm == "cs_zairyohi") {
						cmdtGenka.setStrZairyohiCs(checkNullString(recVal));

						// 　1C/S経費
					}
					if (recNm == "cs_keihi") {
						cmdtGenka.setStrKeihiCs(checkNullString(recVal));

						// 　1C/S原価計
					}
					if (recNm == "cs_genka") {
						cmdtGenka.setStrGenkakeiCs(checkNullString(recVal));

						// 　1個原価計
					}
					if (recNm == "ko_genka") {
						cmdtGenka.setStrGenkakeiTan(checkNullString(recVal));

						// 　1個売価
					}
					if (recNm == "ko_baika") {
						cmdtGenka.setStrGenkakeiBai(checkNullString(recVal));

						// 　1個粗利率
					}
					if (recNm == "ko_riritu") {
						cmdtGenka.setStrGenkakeiRi(checkNullString(recVal));

						// 　登録者ID
					}
					if (recNm == "id_toroku") {
						cmdtGenka.setDciTorokuId(checkNullDecimal(recVal));

						// 　登録日付
					}
					if (recNm == "dt_toroku") {
						cmdtGenka.setStrTorokuHi(checkNullString(recVal));

						// 　更新者ID
					}
					if (recNm == "id_koshin") {
						cmdtGenka.setDciKosinId(checkNullDecimal(recVal));

						// 　更新日付
					}
					if (recNm == "dt_koshin") {
						cmdtGenka.setStrKosinHi(checkNullString(recVal));

						// 　登録者名
					}
					if (recNm == "nm_toroku") {
						cmdtGenka.setStrTorokuNm(checkNullString(recVal));

						// 　更新者名
					}
					if (recNm == "nm_koshin") {
						cmdtGenka.setStrKosinNm(checkNullString(recVal));

					}

				}
				// 原価原料データ配列へ追加
				aryGenka.add(cmdtGenka);
				recData.clear();
			}
			tableT141.clear();
			t141.clear();

		} catch (Exception e) {
			e.printStackTrace();
			// エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("試作テーブルデータ保持のデータ設定（詳細or製法コピー）が失敗しました");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}
	}

	/**
	 * 0002.試作品コピー（特徴コピー） : 試作品テーブルデータのコピーを行う（試作品データのみコピー）
	 * 
	 * @throws ExceptionBase
	 */
	public void CopyShisakuhin() throws ExceptionBase {

		try {
			// 特徴コピー
			setTraialData(1);

		} catch (Exception e) {

			// エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("試作テーブルデータ保持の試作品コピー（特徴コピー）が失敗しました");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}
	}

	/**
	 * 0003.試作品コピー（全コピー） : 試作品テーブルデータのコピーを行う
	 * 
	 * @throws ExceptionBase
	 */
	public void CopyAllShisakuhin() throws ExceptionBase {

		try {

			// データ変更
			HenkouFg = true;

			/**********************************************************
			 * 　T110格納
			 *********************************************************/
			// 　試作CD-社員CD
			ptdtShaisakuHin.setDciShisakuUser(null);
			// 　試作CD-年
			ptdtShaisakuHin.setDciShisakuYear(null);
			// 　試作CD-追番
			ptdtShaisakuHin.setDciShisakuNum(null);
			// 　グループCD
			ptdtShaisakuHin.setIntGroupcd(DataCtrl.getInstance()
					.getUserMstData().getIntGroupcd());
			// 　チームCD
			ptdtShaisakuHin.setIntTeamcd(DataCtrl.getInstance()
					.getUserMstData().getIntTeamcd());
			// 　グループ名
			ptdtShaisakuHin.setStrGroupNm(DataCtrl.getInstance()
					.getUserMstData().getStrGroupnm());
			// 　チーム名
			ptdtShaisakuHin.setStrTeamNm(DataCtrl.getInstance()
					.getUserMstData().getStrTeamnm());
			// 　廃止
			ptdtShaisakuHin.setIntHaisi(0);
			// 　製法試作
			ptdtShaisakuHin.setIntSeihoShisaku(0);
			// 　登録者ID
			ptdtShaisakuHin.setDciTorokuid(DataCtrl.getInstance()
					.getUserMstData().getDciUserid());
			// 　登録日付
			ptdtShaisakuHin.setStrTorokuhi(getSysDate());
			// 　更新者ID
			ptdtShaisakuHin.setDciKosinid(DataCtrl.getInstance()
					.getUserMstData().getDciUserid());
			// 　更新日付
			// UPD 2012.4.30　【H24年度対応】更新日で試作品の更新排他制御を行うため更新日無しでデータを作成。
			// ptdtShaisakuHin.setStrKosinhi(getSysDate());
			ptdtShaisakuHin.setStrKosinhi("");
			// 　登録者名
			ptdtShaisakuHin.setStrTorokuNm(DataCtrl.getInstance()
					.getUserMstData().getStrUsernm());
			// 　更新者名
			ptdtShaisakuHin.setStrKosinNm(DataCtrl.getInstance()
					.getUserMstData().getStrUsernm());

			// 2010/02/25 NAKAMURA ADD
			// START---------------------------------------------------
			// 排他会社名
			ptdtShaisakuHin.setStrHaitaKaishaNm(DataCtrl.getInstance()
					.getUserMstData().getStrHaitaKaishanm());
			// 排他部署名
			ptdtShaisakuHin.setStrHaitaBushoNm(DataCtrl.getInstance()
					.getUserMstData().getStrHaitaBushonm());
			// 排他氏名
			ptdtShaisakuHin.setStrHaitaShimei(DataCtrl.getInstance()
					.getUserMstData().getStrHaitaShimei());
			// 2010/02/25 NAKAMURA ADD
			// END-----------------------------------------------------
			// 【QP@20505_No.38】2012/10/17 TT H.SHIMA ADD Start
			ptdtShaisakuHin.setStrSecret(null);
			// 【QP@20505_No.38】2012/10/17 TT H.SHIMA ADD End

			/**********************************************************
			 * 　T120格納
			 *********************************************************/
			ArrayList aryHaigo = DataCtrl.getInstance().getTrialTblData()
					.SearchHaigoData(0);
			for (int i = 0; i < aryHaigo.size(); i++) {
				// 　試作CD-社員CD
				((MixedData) aryHaigo.get(i)).setDciShisakuUser(null);
				// 　試作CD-年
				((MixedData) aryHaigo.get(i)).setDciShisakuYear(null);
				// 　試作CD-追番
				((MixedData) aryHaigo.get(i)).setDciShisakuNum(null);
				// 　登録者ID
				((MixedData) aryHaigo.get(i)).setDciTorokuId(DataCtrl
						.getInstance().getUserMstData().getDciUserid());
				// 　登録日付
				((MixedData) aryHaigo.get(i)).setStrTorokuHi(getSysDate());
				// 　更新者ID
				((MixedData) aryHaigo.get(i)).setDciKosinId(DataCtrl
						.getInstance().getUserMstData().getDciUserid());
				// 　更新日付
				((MixedData) aryHaigo.get(i)).setStrKosinHi(getSysDate());
				// 　登録者名
				((MixedData) aryHaigo.get(i)).setStrTorokuNm(DataCtrl
						.getInstance().getUserMstData().getStrUsernm());
				// 　更新者名
				((MixedData) aryHaigo.get(i)).setStrKosinNm(DataCtrl
						.getInstance().getUserMstData().getStrUsernm());
			}

			/**********************************************************
			 * 　T131格納
			 *********************************************************/
			ArrayList aryRetu = DataCtrl.getInstance().getTrialTblData()
					.SearchShisakuRetuData(0);
			for (int i = 0; i < aryRetu.size(); i++) {
				// 　試作CD-社員CD
				((TrialData) aryRetu.get(i)).setDciShisakuUser(null);
				// 　試作CD-年
				((TrialData) aryRetu.get(i)).setDciShisakuYear(null);
				// 　試作CD-追番
				((TrialData) aryRetu.get(i)).setDciShisakuNum(null);
				// 　原価試算
				((TrialData) aryRetu.get(i)).setIntGenkaShisan(0);
				// 　製法No-1
				((TrialData) aryRetu.get(i)).setStrSeihoNo1(null);
				// 　製法No-2
				((TrialData) aryRetu.get(i)).setStrSeihoNo2(null);
				// 　製法No-3
				((TrialData) aryRetu.get(i)).setStrSeihoNo3(null);
				// 　製法No-4
				((TrialData) aryRetu.get(i)).setStrSeihoNo4(null);
				// 　製法No-5
				((TrialData) aryRetu.get(i)).setStrSeihoNo5(null);
				// 　登録者ID
				((TrialData) aryRetu.get(i)).setDciTorokuId(DataCtrl
						.getInstance().getUserMstData().getDciUserid());
				// 　登録日付
				((TrialData) aryRetu.get(i)).setStrTorokuHi(getSysDate());
				// 　更新者ID
				((TrialData) aryRetu.get(i)).setDciKosinId(DataCtrl
						.getInstance().getUserMstData().getDciUserid());
				// 　更新日付
				((TrialData) aryRetu.get(i)).setStrkosinHi(getSysDate());
				// 　登録者名
				((TrialData) aryRetu.get(i)).setStrTorokuNm(DataCtrl
						.getInstance().getUserMstData().getStrUsernm());
				// 　更新者名
				((TrialData) aryRetu.get(i)).setStrKosinNm(DataCtrl
						.getInstance().getUserMstData().getStrUsernm());
				// 　原価依頼フラグ
				((TrialData) aryRetu.get(i)).setFlg_shisanIrai(0);
				// 　既存依頼データフラグ
				((TrialData) aryRetu.get(i)).setFlg_init(0);

			}

			/**********************************************************
			 * 　T132格納
			 *********************************************************/
			ArrayList aryList = DataCtrl.getInstance().getTrialTblData()
					.getAryShisakuList();
			for (int i = 0; i < aryList.size(); i++) {
				// 　試作CD-社員CD
				((PrototypeListData) aryList.get(i)).setDciShisakuUser(null);
				// 　試作CD-年
				((PrototypeListData) aryList.get(i)).setDciShisakuYear(null);
				// 　試作CD-追番
				((PrototypeListData) aryList.get(i)).setDciShisakuNum(null);
				// 　登録者ID
				((PrototypeListData) aryList.get(i)).setDciTorokuId(DataCtrl
						.getInstance().getUserMstData().getDciUserid());
				// 　登録日付
				((PrototypeListData) aryList.get(i))
						.setStrTorokuHi(getSysDate());
				// 　更新者ID
				((PrototypeListData) aryList.get(i)).setDciKosinId(DataCtrl
						.getInstance().getUserMstData().getDciUserid());
				// 　更新日付
				((PrototypeListData) aryList.get(i))
						.setStrKosinHi(getSysDate());
				// 　登録者名
				((PrototypeListData) aryList.get(i)).setStrTorokuNm(DataCtrl
						.getInstance().getUserMstData().getStrUsernm());
				// 　更新者名
				((PrototypeListData) aryList.get(i)).setStrKosinNm(DataCtrl
						.getInstance().getUserMstData().getStrUsernm());
			}

			/**********************************************************
			 * 　T133格納
			 *********************************************************/
			ArrayList arySeizo = DataCtrl.getInstance().getTrialTblData()
					.SearchSeizoKouteiData(0);
			for (int i = 0; i < arySeizo.size(); i++) {
				// 　試作CD-社員CD
				((ManufacturingData) arySeizo.get(i)).setDciShisakuUser(null);
				// 　試作CD-年
				((ManufacturingData) arySeizo.get(i)).setDciShisakuYear(null);
				// 　試作CD-追番
				((ManufacturingData) arySeizo.get(i)).setDciShisakuNum(null);
				// 　登録者ID
				((ManufacturingData) arySeizo.get(i)).setDciTorokuId(DataCtrl
						.getInstance().getUserMstData().getDciUserid());
				// 　登録日付
				((ManufacturingData) arySeizo.get(i))
						.setStrTorokuHi(getSysDate());
				// 　更新者ID
				((ManufacturingData) arySeizo.get(i)).setDciKosinId(DataCtrl
						.getInstance().getUserMstData().getDciUserid());
				// 　更新日付
				((ManufacturingData) arySeizo.get(i))
						.setStrKosinHi(getSysDate());
				// 　登録者名
				((ManufacturingData) arySeizo.get(i)).setStrTorokuNm(DataCtrl
						.getInstance().getUserMstData().getStrUsernm());
				// 　更新者名
				((ManufacturingData) arySeizo.get(i)).setStrKosinNm(DataCtrl
						.getInstance().getUserMstData().getStrUsernm());
			}

			/**********************************************************
			 * 　T140格納
			 *********************************************************/

			/**********************************************************
			 * 　T141格納
			 *********************************************************/
			// 原価原料データ取得
			ArrayList aryGenka = DataCtrl.getInstance().getTrialTblData()
					.SearchGenkaGenryoData(0);
			for (int i = 0; i < aryGenka.size(); i++) {
				CostMaterialData costMaterialData = (CostMaterialData) aryGenka
						.get(i);

				// 試作CD-社員CD
				costMaterialData.setDciShisakuUser(null);
				// 試作CD-年
				costMaterialData.setDciShisakuYear(null);
				// 試作CD-追番
				costMaterialData.setDciShisakuNum(null);
				// 　登録者ID
				costMaterialData.setDciTorokuId(DataCtrl.getInstance()
						.getUserMstData().getDciUserid());
				// 　登録日付
				costMaterialData.setStrTorokuHi(getSysDate());
				// 　更新者ID
				costMaterialData.setDciKosinId(DataCtrl.getInstance()
						.getUserMstData().getDciUserid());
				// 　更新日付
				costMaterialData.setStrKosinHi(getSysDate());
				// 　登録者名
				costMaterialData.setStrTorokuNm(DataCtrl.getInstance()
						.getUserMstData().getStrUsernm());
				// 　更新者名
				costMaterialData.setStrKosinNm(DataCtrl.getInstance()
						.getUserMstData().getStrUsernm());
			}

		} catch (Exception e) {

			// エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("試作テーブルデータ保持の試作品コピー（全コピー）が失敗しました");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/**
	 * 0004.最新、過去の製法コピーの管理をする
	 * 
	 * @param intShisakuSeq
	 *            : 試作SEQ
	 * @throws ExceptionBase
	 */
	public void CopySeihou(int intShisakuSeq) throws ExceptionBase {

		try {

			// 仕様未確定

		} catch (Exception e) {

			ex.setStrErrmsg("");
			ex.setStrErrShori("");
			ex.setStrMsgNo("");
			ex.setStrErrCd("");
			ex.setStrSystemMsg("");

			throw ex;

		} finally {

		}

	}

	/**
	 * 0005.試作品データ検索 : 試作品データを検索する
	 * 
	 * @return 試作品データ
	 */
	public PrototypeData SearchShisakuhinData() {
		return ptdtShaisakuHin;
	}

	/**
	 * 0006.試作品試作コード更新 : 試作品データに対して試作コードを設定する
	 * 
	 * @param dciShainCd
	 *            : 試作CD-社員CD
	 * @param dciNen
	 *            : 試作CD-年
	 * @param dciTuiban
	 *            : 試作CD-追番
	 * @param dciUserId
	 *            : ユーザID
	 * @throws ExceptionBase
	 */
	public void UpdShisakuCd(BigDecimal dciShainCd, BigDecimal dciNen,
			BigDecimal dciTuiban, BigDecimal dciUserId) throws ExceptionBase {

		try {

			// 試作コードの設定
			ptdtShaisakuHin.setDciShisakuUser(dciShainCd);
			ptdtShaisakuHin.setDciShisakuYear(dciNen);
			ptdtShaisakuHin.setDciShisakuNum(dciTuiban);

			// 更新者情報の設定
			ptdtShaisakuHin.setDciKosinid(dciUserId);
			// DEL 2012.4.30　【H24年度対応】更新日で試作品の更新排他制御を行うため更新
			// ptdtShaisakuHin.setStrKosinhi(getSysDate());

		} catch (Exception e) {

			// エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("試作テーブルデータ保持の試作品試作コード更新が失敗しました");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/**
	 * 0007.試作品依頼番号更新 : 試作品データに対して依頼番号を設定する
	 * 
	 * @param strIraiNo
	 *            ; 依頼番号
	 * @param dciUserId
	 *            : ユーザID
	 * @throws ExceptionBase
	 */
	public void UpdIraiNo(String strIraiNo, BigDecimal dciUserId)
			throws ExceptionBase {

		try {

			// 【KPX1500671】データ変更フラグの設定タイミング変更：MOD start
			// データ変更
			// HenkouFg = true;

			// データ変更チェック
			chkHenkouData(ptdtShaisakuHin.getStrIrai(), strIraiNo);
			// 【KPX1500671】MOD end

			// 依頼番号設定
			ptdtShaisakuHin.setStrIrai(strIraiNo);

			// 更新者情報の設定
			ptdtShaisakuHin.setDciKosinid(dciUserId);
			// DEL 2012.4.30　【H24年度対応】更新日で試作品の更新排他制御を行うため更新
			// ptdtShaisakuHin.setStrKosinhi(getSysDate());

		} catch (Exception e) {

			// エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("試作テーブルデータ保持の試作品依頼番号更新が失敗しました");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/**
	 * 0008.試作品種別番号更新 : 試作品データに対して種別番号を設定する
	 * 
	 * @param intSyubetuNo
	 *            : 種別番号
	 * @param dciUserId
	 *            : ユーザID
	 * @throws ExceptionBase
	 */
	public void UpdSyubetuNo(String strSyubetuNo, BigDecimal dciUserId)
			throws ExceptionBase {

		try {

			// 【KPX1500671】データ変更フラグの設定タイミング変更：MOD start
			// データ変更
			// HenkouFg = true;

			// データ変更チェック
			chkHenkouData(ptdtShaisakuHin.getStrShubetuNo(), strSyubetuNo);
			// 【KPX1500671】MOD end

			// 種別番号設定待ち　DB上
			ptdtShaisakuHin.setStrShubetuNo(strSyubetuNo);

			// 更新者情報の設定
			ptdtShaisakuHin.setDciKosinid(dciUserId);
			// DEL 2012.4.30　【H24年度対応】更新日で試作品の更新排他制御を行うため更新
			// ptdtShaisakuHin.setStrKosinhi(getSysDate());

		} catch (Exception e) {

			// エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("試作テーブルデータ保持の試作品種別番号更新が失敗しました");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/**
	 * 0009.試作品廃止区更新 : 試作品データに対して廃止区を設定する
	 * 
	 * @param intHaishi
	 *            : 廃止区
	 * @param dciUserId
	 *            : ユーザID
	 * @throws ExceptionBase
	 */
	public void UpdHaishi(int intHaishi, BigDecimal dciUserId)
			throws ExceptionBase {

		try {

			// 【KPX1500671】データ変更フラグの設定タイミング変更：MOD start
			// データ変更
			// HenkouFg = true;

			// データ変更チェック
			chkHenkouData(ptdtShaisakuHin.getIntHaisi(), intHaishi);
			// 【KPX1500671】MOD end

			// 廃止区設定
			ptdtShaisakuHin.setIntHaisi(intHaishi);

			// 更新者情報の設定
			ptdtShaisakuHin.setDciKosinid(dciUserId);
			// DEL 2012.4.30　【H24年度対応】更新日で試作品の更新排他制御を行うため更新
			// ptdtShaisakuHin.setStrKosinhi(getSysDate());

		} catch (Exception e) {

			// エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("試作テーブルデータ保持の試作品廃止区更新が失敗しました");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/**
	 * 0010.試作品品名更新 : 試作品データに対して品名を設定する
	 * 
	 * @param strHinmei
	 *            : 品名
	 * @param dciUserId
	 *            : ユーザID
	 * @throws ExceptionBase
	 */
	public void UpdHinmei(String strHinmei, BigDecimal dciUserId)
			throws ExceptionBase {

		try {

			// 【KPX1500671】データ変更フラグの設定タイミング変更：MOD start
			// データ変更
			// HenkouFg = true;

			// データ変更チェック
			chkHenkouData(ptdtShaisakuHin.getStrHinnm(), strHinmei);
			// 【KPX1500671】MOD end

			// 品名設定
			ptdtShaisakuHin.setStrHinnm(strHinmei);

			// 更新者情報の設定
			ptdtShaisakuHin.setDciKosinid(dciUserId);
			// DEL 2012.4.30　【H24年度対応】更新日で試作品の更新排他制御を行うため更新
			// ptdtShaisakuHin.setStrKosinhi(getSysDate());

		} catch (Exception e) {

			// エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("試作テーブルデータ保持の試作品品名更新が失敗しました");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/**
	 * 0011.試作品チームCD更新 : 試作品データに対してチームCDを設定する
	 * 
	 * @param intTeamCd
	 *            : チームCD
	 * @param dciUserId
	 *            : ユーザID
	 * @throws ExceptionBase
	 */
	public void UpdTeamCd(int intTeamCd, BigDecimal dciUserId)
			throws ExceptionBase {

		try {

			// 【KPX1500671】データ変更フラグの設定タイミング変更：MOD start
			// データ変更
			// HenkouFg = true;
			// 【KPX1500671】MOD end

			// チームCD設定
			ptdtShaisakuHin.setIntTeamcd(intTeamCd);

			// 更新者情報の設定
			ptdtShaisakuHin.setDciKosinid(dciUserId);
			// DEL 2012.4.30　【H24年度対応】更新日で試作品の更新排他制御を行うため更新
			// ptdtShaisakuHin.setStrKosinhi(getSysDate());

		} catch (Exception e) {

			// エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("試作テーブルデータ保持の試作品チームCD更新が失敗しました");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/**
	 * 0012.試作品グループCD更新 : 試作品データに対してグループCDを設定する
	 * 
	 * @param intGroupCd
	 *            : グループCD
	 * @param dciUserId
	 *            : ユーザID
	 * @throws ExceptionBase
	 */
	public void UpdGroupCd(int intGroupCd, BigDecimal dciUserId)
			throws ExceptionBase {

		try {

			// 【KPX1500671】データ変更フラグの設定タイミング変更：MOD start
			// データ変更
			// HenkouFg = true;
			// 【KPX1500671】MOD end

			// グループCD設定
			ptdtShaisakuHin.setIntGroupcd(intGroupCd);

			// 更新者情報の設定
			ptdtShaisakuHin.setDciKosinid(dciUserId);
			// DEL 2012.4.30　【H24年度対応】更新日で試作品の更新排他制御を行うため更新
			// ptdtShaisakuHin.setStrKosinhi(getSysDate());

		} catch (Exception e) {

			// エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("試作テーブルデータ保持の試作品グループCD更新が失敗しました");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/**
	 * 0013.試作品一括表示更新 : 試作品データに対して一括表示を設定する
	 * 
	 * @param strIkkatu
	 *            : 一括表示
	 * @param iUserId
	 *            : ユーザID
	 * @throws ExceptionBase
	 */
	public void UpdIkkatsuHyouji(String strIkkatu, BigDecimal dciUserId)
			throws ExceptionBase {

		try {

			// 【KPX1500671】データ変更フラグの設定タイミング変更：MOD start
			// データ変更
			// HenkouFg = true;

			// データ変更チェック
			chkHenkouData(ptdtShaisakuHin.getStrIkatu(), strIkkatu);
			// 【KPX1500671】MOD end

			// 一括表示設定
			ptdtShaisakuHin.setStrIkatu(strIkkatu);

			// 更新者情報の設定
			ptdtShaisakuHin.setDciKosinid(dciUserId);
			// DEL 2012.4.30　【H24年度対応】更新日で試作品の更新排他制御を行うため更新
			// ptdtShaisakuHin.setStrKosinhi(getSysDate());

		} catch (Exception e) {

			// エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("試作テーブルデータ保持の試作品一括表示更新が失敗しました");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/**
	 * 0014.試作品ジャンル更新 : 試作品データに対してジャンルを設定する
	 * 
	 * @param intJanru
	 *            : ジャンル
	 * @param dciUserId
	 *            : ユーザID
	 * @throws ExceptionBase
	 */
	public void UpdJanru(String strJanru, BigDecimal dciUserId)
			throws ExceptionBase {

		try {

			// 【KPX1500671】データ変更フラグの設定タイミング変更：MOD start
			// データ変更
			// HenkouFg = true;

			// データ変更チェック
			chkHenkouData(ptdtShaisakuHin.getStrZyanru(), strJanru);
			// 【KPX1500671】MOD end

			// ジャンル設定
			ptdtShaisakuHin.setStrZyanru(strJanru);

			// 更新者情報の設定
			ptdtShaisakuHin.setDciKosinid(dciUserId);
			// DEL 2012.4.30　【H24年度対応】更新日で試作品の更新排他制御を行うため更新
			// ptdtShaisakuHin.setStrKosinhi(getSysDate());

		} catch (Exception e) {

			// エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("試作テーブルデータ保持の試作品ジャンル更新が失敗しました");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/**
	 * 0015.試作品ユーザ更新 : 試作品データに対してユーザを設定する
	 * 
	 * @param strUserCd
	 *            : ユーザCD
	 * @param dciUserId
	 *            : ユーザID
	 * @throws ExceptionBase
	 */
	public void UpdUser(String strUserCd, BigDecimal dciUserId)
			throws ExceptionBase {

		try {

			// 【KPX1500671】データ変更フラグの設定タイミング変更：MOD start
			// データ変更
			// HenkouFg = true;

			// データ変更チェック
			chkHenkouData(ptdtShaisakuHin.getStrUsercd(), strUserCd);
			// 【KPX1500671】MOD end

			// ユーザーCD設定
			ptdtShaisakuHin.setStrUsercd(strUserCd);

			// 更新者情報の設定
			ptdtShaisakuHin.setDciKosinid(dciUserId);
			// DEL 2012.4.30　【H24年度対応】更新日で試作品の更新排他制御を行うため更新
			// ptdtShaisakuHin.setStrKosinhi(getSysDate());

		} catch (Exception e) {

			// エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("試作テーブルデータ保持の試作品ユーザ更新が失敗しました");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/**
	 * 0016.試作品特徴原料更新 : 試作品データに対して特徴原料を設定する
	 * 
	 * @param strTokutyoGenryo
	 *            : 特徴原料
	 * @param dciUserId
	 *            : ユーザID
	 * @throws ExceptionBase
	 */
	public void UpdGenryo(String strTokutyoGenryo, BigDecimal dciUserId)
			throws ExceptionBase {

		try {

			// 【KPX1500671】データ変更フラグの設定タイミング変更：MOD start
			// データ変更
			// HenkouFg = true;

			// データ変更チェック
			chkHenkouData(ptdtShaisakuHin.getStrTokutyo(), strTokutyoGenryo);
			// 【KPX1500671】MOD end

			// 特徴原料設定
			ptdtShaisakuHin.setStrTokutyo(strTokutyoGenryo);

			// 更新者情報の設定
			ptdtShaisakuHin.setDciKosinid(dciUserId);
			// DEL 2012.4.30　【H24年度対応】更新日で試作品の更新排他制御を行うため更新
			// ptdtShaisakuHin.setStrKosinhi(getSysDate());

		} catch (Exception e) {

			// エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("試作テーブルデータ保持の試作品特徴原料更新が失敗しました");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/**
	 * 0017.試作品用途更新 : 試作品データに対して用途を設定する
	 * 
	 * @param strYouto
	 *            : 用途
	 * @param dciUserId
	 *            : ユーザID
	 * @throws ExceptionBase
	 */
	public void UpdYouto(String strYouto, BigDecimal dciUserId)
			throws ExceptionBase {

		try {

			// 【KPX1500671】データ変更フラグの設定タイミング変更：MOD start
			// データ変更
			// HenkouFg = true;

			// データ変更チェック
			chkHenkouData(ptdtShaisakuHin.getStrYoto(), strYouto);
			// 【KPX1500671】MOD end

			// 用途設定
			ptdtShaisakuHin.setStrYoto(strYouto);

			// 更新者情報の設定
			ptdtShaisakuHin.setDciKosinid(dciUserId);
			// DEL 2012.4.30　【H24年度対応】更新日で試作品の更新排他制御を行うため更新
			// ptdtShaisakuHin.setStrKosinhi(getSysDate());

		} catch (Exception e) {

			// エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("試作テーブルデータ保持の試作品用途更新が失敗しました");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/**
	 * 0018.試作品価格帯更新 : 試作品データに対して価格帯を設定する
	 * 
	 * @param strKakakutai
	 *            : 価格帯
	 * @param dciUserId
	 *            : ユーザID
	 * @throws ExceptionBase
	 */
	public void UpdKakakutai(String strKakakutai, BigDecimal dciUserId)
			throws ExceptionBase {

		try {

			// 【KPX1500671】データ変更フラグの設定タイミング変更：MOD start
			// データ変更
			// HenkouFg = true;

			// データ変更チェック
			chkHenkouData(ptdtShaisakuHin.getStrKakaku(), strKakakutai);
			// 【KPX1500671】MOD end

			// 価格帯設定
			ptdtShaisakuHin.setStrKakaku(strKakakutai);

			// 更新者情報の設定
			ptdtShaisakuHin.setDciKosinid(dciUserId);
			// DEL 2012.4.30　【H24年度対応】更新日で試作品の更新排他制御を行うため更新
			// ptdtShaisakuHin.setStrKosinhi(getSysDate());

		} catch (Exception e) {

			// エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("試作テーブルデータ保持の試作品価格帯更新が失敗しました");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/**
	 * 0019.試作品種別番号更新 : 試作品データに対して種別を設定する
	 * 
	 * @param strSyubetu
	 *            : 種別
	 * @param dciUserId
	 *            : ユーザID
	 * @throws ExceptionBase
	 */
	public void UpdHinsyubetu(String strSyubetu, BigDecimal dciUserId)
			throws ExceptionBase {

		try {

			// 【KPX1500671】データ変更フラグの設定タイミング変更：MOD start
			// データ変更
			// HenkouFg = true;

			// データ変更チェック
			chkHenkouData(ptdtShaisakuHin.getStrShubetu(), strSyubetu);
			// 【KPX1500671】MOD end

			// 種別設定
			ptdtShaisakuHin.setStrShubetu(strSyubetu);

			// 更新者情報の設定
			ptdtShaisakuHin.setDciKosinid(dciUserId);
			// DEL 2012.4.30　【H24年度対応】更新日で試作品の更新排他制御を行うため更新
			// ptdtShaisakuHin.setStrKosinhi(getSysDate());

		} catch (Exception e) {

			// エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("試作テーブルデータ保持の試作品種別番号更新が失敗しました");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/**
	 * 0020.試作品少数指定更新 : 試作品データに対して少数指定を設定する
	 * 
	 * @param strSyosuShitei
	 *            : 小数指定
	 * @param dciUserId
	 *            : ユーザID
	 * @throws ExceptionBase
	 */
	public void UpdSyousuShitei(String strSyosuShitei, BigDecimal dciUserId)
			throws ExceptionBase {

		try {

			// 【KPX1500671】データ変更フラグの設定タイミング変更：MOD start
			// データ変更
			// HenkouFg = true;

			// データ変更チェック
			String strBefSyosu = ptdtShaisakuHin.getStrShosu();
			if (null != strBefSyosu) {
				// 前ゼロ排除
				strBefSyosu = String.valueOf(Integer.parseInt(strBefSyosu));
			}
			String strAftSyosu = strSyosuShitei;
			if (null != strAftSyosu) {
				// 前ゼロ排除
				strAftSyosu = String.valueOf(Integer.parseInt(strAftSyosu));
			}
			chkHenkouData(strBefSyosu, strAftSyosu);
			// 【KPX1500671】MOD end

			// 小数指定設定
			ptdtShaisakuHin.setStrShosu(strSyosuShitei);

			// 更新者情報の設定
			ptdtShaisakuHin.setDciKosinid(dciUserId);
			// DEL 2012.4.30　【H24年度対応】更新日で試作品の更新排他制御を行うため更新
			// ptdtShaisakuHin.setStrKosinhi(getSysDate());

		} catch (Exception e) {

			// エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("試作テーブルデータ保持の試作品少数指定更新が失敗しました");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	// ADD 2013/06/19 ogawa 【QP@30151】No.9 start
	/**
	 * 0021.試作品販責会社更新 : 試作品データに対して販責会社を設定する
	 * 
	 * @param intTantoKaishaCd
	 *            : 販責会社
	 * @param dciUserId
	 *            : ユーザID
	 * @throws ExceptionBase
	 */
	public void UpdHanseki(int intHansekiCd, BigDecimal dciUserId)
			throws ExceptionBase {

		try {

			// 【KPX1500671】データ変更フラグの設定タイミング変更：MOD start
			// データ変更
			// HenkouFg = true;

			// データ変更チェック
			chkHenkouData(ptdtShaisakuHin.getIntHansekicd(), intHansekiCd);
			// 【KPX1500671】MOD end

			// 販責会社設定
			ptdtShaisakuHin.setIntHansekicd(intHansekiCd);
			// 更新者情報の設定
			ptdtShaisakuHin.setDciKosinid(dciUserId);

		} catch (Exception e) {

			// エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("試作テーブルデータ保持の試作品販責会社更新が失敗しました");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	// ADD 2013/06/19 ogawa 【QP@30151】No.9 end

	/**
	 * 0021.試作品担当会社更新 : 試作品データに対して担当会社を設定する
	 * 
	 * @param intTantoKaishaCd
	 *            : 担当会社
	 * @param dciUserId
	 *            : ユーザID
	 * @throws ExceptionBase
	 */
	public void UpdTantoKaisha(int intTantoKaishaCd, BigDecimal dciUserId)
			throws ExceptionBase {

		try {

			// 【KPX1500671】データ変更フラグの設定タイミング変更：MOD start
			// データ変更
			// HenkouFg = true;

			// データ変更チェック
			chkHenkouData(ptdtShaisakuHin.getIntKaishacd(), intTantoKaishaCd);
			// 【KPX1500671】MOD end

			// 担当会社設定
			ptdtShaisakuHin.setIntKaishacd(intTantoKaishaCd);

			// 更新者情報の設定
			ptdtShaisakuHin.setDciKosinid(dciUserId);
			// DEL 2012.4.30　【H24年度対応】更新日で試作品の更新排他制御を行うため更新
			// ptdtShaisakuHin.setStrKosinhi(getSysDate());

		} catch (Exception e) {

			// エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("試作テーブルデータ保持の試作品担当会社更新が失敗しました");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/**
	 * 0022.試作品担当工場更新 : 試作品データに対して担当工場を設定する
	 * 
	 * @param intTantoKojoCd
	 *            : 担当工場
	 * @param dciUserId
	 *            : ユーザID
	 * @throws ExceptionBase
	 */
	public void UpdTantoKojo(int intTantoKojoCd, BigDecimal dciUserId)
			throws ExceptionBase {

		try {

			// 【KPX1500671】データ変更フラグの設定タイミング変更：MOD start
			// データ変更
			// HenkouFg = true;

			// データ変更チェック
			chkHenkouData(ptdtShaisakuHin.getIntKojoco(), intTantoKojoCd);
			// 【KPX1500671】MOD end

			// 担当工場設定
			ptdtShaisakuHin.setIntKojoco(intTantoKojoCd);

			// 更新者情報の設定
			ptdtShaisakuHin.setDciKosinid(dciUserId);
			// DEL 2012.4.30　【H24年度対応】更新日で試作品の更新排他制御を行うため更新
			// ptdtShaisakuHin.setStrKosinhi(getSysDate());

		} catch (Exception e) {

			// エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("試作テーブルデータ保持の試作品担当工場更新が失敗しました");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/**
	 * 0023.試作品担当営業更新 : 試作品データに対して担当営業を設定する
	 * 
	 * @param strTantoEigyoCd
	 *            : 担当営業
	 * @param dciUserId
	 *            : ユーザID
	 */
	public void UpdTantoEigyo(String strTantoEigyoCd, BigDecimal dciUserId)
			throws ExceptionBase {

		try {

			// 【KPX1500671】データ変更フラグの設定タイミング変更：MOD start
			// データ変更
			// HenkouFg = true;

			// データ変更チェック
			String strBefTantoEigyoCd = ptdtShaisakuHin.getStrTantoEigyo();
			if (null != strBefTantoEigyoCd) {
				// 前ゼロ排除
				strBefTantoEigyoCd = String.valueOf(Integer.parseInt(strBefTantoEigyoCd));
			}
			String strAftTantoEigyoCd = strTantoEigyoCd;
			if (null != strAftTantoEigyoCd) {
				// 前ゼロ排除
				strAftTantoEigyoCd = String.valueOf(Integer.parseInt(strAftTantoEigyoCd));
			}
			chkHenkouData(strBefTantoEigyoCd, strAftTantoEigyoCd);
			// 【KPX1500671】MOD end

			// 担当営業設定
			ptdtShaisakuHin.setStrTantoEigyo(strTantoEigyoCd);

			// 更新者情報の設定
			ptdtShaisakuHin.setDciKosinid(dciUserId);
			// DEL 2012.4.30　【H24年度対応】更新日で試作品の更新排他制御を行うため更新
			// ptdtShaisakuHin.setStrKosinhi(getSysDate());

		} catch (Exception e) {

			// エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("試作テーブルデータ保持の試作品担当営業更新が失敗しました");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/**
	 * 0024.試作品製造方法更新 : 試作品データに対して製造方法を設定する
	 * 
	 * @param strSeizoHouho
	 *            : 製造方法
	 * @param dciUserId
	 *            : ユーザID
	 */
	public void UpdSeizoHouho(String strSeizoHouho, BigDecimal dciUserId)
			throws ExceptionBase {

		try {

			// 【KPX1500671】データ変更フラグの設定タイミング変更：MOD start
			// データ変更
			// HenkouFg = true;

			// データ変更チェック
			chkHenkouData(ptdtShaisakuHin.getStrSeizocd(), strSeizoHouho);
			// 【KPX1500671】MOD end

			// 製造方法設定
			ptdtShaisakuHin.setStrSeizocd(strSeizoHouho);

			// 更新者情報の設定
			ptdtShaisakuHin.setDciKosinid(dciUserId);
			// DEL 2012.4.30　【H24年度対応】更新日で試作品の更新排他制御を行うため更新
			// ptdtShaisakuHin.setStrKosinhi(getSysDate());

		} catch (Exception e) {

			// エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("試作テーブルデータ保持の試作品製造方法更新が失敗しました");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/**
	 * 0025.試作品充填方法更新 : 試作品データに対して充填方法を設定する
	 * 
	 * @param strJutenHouho
	 *            : 充填方法
	 * @param dciUserId
	 *            : ユーザID
	 */
	public void UpdJutenHouho(String strJutenHouho, BigDecimal dciUserId)
			throws ExceptionBase {

		try {

			// 【KPX1500671】データ変更フラグの設定タイミング変更：MOD start
			// データ変更
			// HenkouFg = true;

			// データ変更チェック
			chkHenkouData(ptdtShaisakuHin.getStrZyutencd(), strJutenHouho);
			// 【KPX1500671】MOD end

			// 充填方法設定
			ptdtShaisakuHin.setStrZyutencd(strJutenHouho);

			// 更新者情報の設定
			ptdtShaisakuHin.setDciKosinid(dciUserId);
			// DEL 2012.4.30　【H24年度対応】更新日で試作品の更新排他制御を行うため更新
			// ptdtShaisakuHin.setStrKosinhi(getSysDate());

		} catch (Exception e) {

			// エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("試作テーブルデータ保持の試作品充填方法更新が失敗しました");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/**
	 * 0026.試作品殺菌方法更新 : 試作品データに対して殺菌方法を設定する
	 * 
	 * @param strSakkinHouho
	 *            : 殺菌方法
	 * @param dciUserId
	 *            : ユーザID
	 */
	public void UpdSakkinHouho(String strSakkinHouho, BigDecimal dciUserId)
			throws ExceptionBase {

		try {

			// 【KPX1500671】データ変更フラグの設定タイミング変更：MOD start
			// データ変更
			// HenkouFg = true;

			// データ変更チェック
			chkHenkouData(ptdtShaisakuHin.getStrSakin(), strSakkinHouho);
			// 【KPX1500671】MOD end

			// 殺菌方法設定
			ptdtShaisakuHin.setStrSakin(strSakkinHouho);

			// 更新者情報の設定
			ptdtShaisakuHin.setDciKosinid(dciUserId);
			ptdtShaisakuHin.setStrTorokuhi(getSysDate());

		} catch (Exception e) {

			// エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("試作テーブルデータ保持の試作品殺菌方法更新が失敗しました");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/**
	 * 0027.試作品容器・包材更新 : 試作品データに対して容器・包材を設定する
	 * 
	 * @param strYoukiHouzai
	 *            : 容器・包材
	 * @param dciUserId
	 *            : ユーザID
	 */
	public void UpdYoukiHouzai(String strYoukiHouzai, BigDecimal dciUserId)
			throws ExceptionBase {

		try {

			// 【KPX1500671】データ変更フラグの設定タイミング変更：MOD start
			// データ変更
			// HenkouFg = true;

			// データ変更チェック
			chkHenkouData(ptdtShaisakuHin.getStrYokihozai(), strYoukiHouzai);
			// 【KPX1500671】MOD end

			// 包材設定
			ptdtShaisakuHin.setStrYokihozai(strYoukiHouzai);

			// 更新者情報の設定
			ptdtShaisakuHin.setDciKosinid(dciUserId);
			// DEL 2012.4.30　【H24年度対応】更新日で試作品の更新排他制御を行うため更新
			// ptdtShaisakuHin.setStrKosinhi(getSysDate());

		} catch (Exception e) {

			// エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("試作テーブルデータ保持の試作品容器・包材更新が失敗しました");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/**
	 * 0028.試作品容量更新 : 試作品データに対して容量を設定する
	 * 
	 * @param strYouryo
	 *            : 容量
	 * @param dciUserId
	 *            : ユーザID
	 */
	public void UpdYouryo(String strYouryo, BigDecimal dciUserId)
			throws ExceptionBase {

		try {

			// 【KPX1500671】データ変更フラグの設定タイミング変更：MOD start
			// データ変更
			// HenkouFg = true;

			// データ変更チェック
			chkHenkouData(ptdtShaisakuHin.getStrYoryo(), strYouryo);
			// 【KPX1500671】MOD end

			// 容量設定
			ptdtShaisakuHin.setStrYoryo(strYouryo);

			// 更新者情報の設定
			ptdtShaisakuHin.setDciKosinid(dciUserId);
			// DEL 2012.4.30　【H24年度対応】更新日で試作品の更新排他制御を行うため更新
			// ptdtShaisakuHin.setStrKosinhi(getSysDate());

		} catch (Exception e) {

			// エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("試作テーブルデータ保持の試作品容量更新が失敗しました");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/**
	 * 0029.試作品容量単位更新 : 試作品データに対して容量単位を設定する
	 * 
	 * @param strYouryoTani
	 *            : 容量単位
	 * @param dciUserId
	 *            : ユーザID
	 */
	public void UpdYouryoTani(String strYouryoTani, BigDecimal dciUserId)
			throws ExceptionBase {

		try {

			// 【KPX1500671】データ変更フラグの設定タイミング変更：MOD start
			// データ変更
			// HenkouFg = true;

			// データ変更チェック
			chkHenkouData(ptdtShaisakuHin.getStrTani(), strYouryoTani);
			// 【KPX1500671】MOD end

			// 容量単位設定
			ptdtShaisakuHin.setStrTani(strYouryoTani);

			// 更新者情報の設定
			ptdtShaisakuHin.setDciKosinid(dciUserId);
			// DEL 2012.4.30　【H24年度対応】更新日で試作品の更新排他制御を行うため更新
			// ptdtShaisakuHin.setStrKosinhi(getSysDate());

		} catch (Exception e) {

			// エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("試作テーブルデータ保持の試作品容量単位更新が失敗しました");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/**
	 * 0030.試作品入り数更新 : 試作品データに対して入り数を設定する
	 * 
	 * @param strIriSu
	 *            : 入り数
	 * @param dciUserId
	 *            : ユーザID
	 */
	public void UpdIriSu(String strIriSu, BigDecimal dciUserId)
			throws ExceptionBase {

		try {

			// 【KPX1500671】データ変更フラグの設定タイミング変更：MOD start
			// データ変更
			// HenkouFg = true;

			// データ変更チェック
			chkHenkouData(ptdtShaisakuHin.getStrIrisu(), strIriSu);
			// 【KPX1500671】MOD end

			// 入り数設定
			ptdtShaisakuHin.setStrIrisu(strIriSu);

			// 更新者情報の設定
			ptdtShaisakuHin.setDciKosinid(dciUserId);
			// DEL 2012.4.30　【H24年度対応】更新日で試作品の更新排他制御を行うため更新
			// ptdtShaisakuHin.setStrKosinhi(getSysDate());

		} catch (Exception e) {

			// エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("試作テーブルデータ保持の試作品入り数更新が失敗しました");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/**
	 * 0031.試作品荷姿更新 : 試作品データに対して荷姿を設定する
	 * 
	 * @param strNisugata
	 *            : 荷姿
	 * @param dciUserId
	 *            : ユーザID
	 */
	public void UpdNisugata(String strNisugata, BigDecimal dciUserId)
			throws ExceptionBase {

		try {

			// 【KPX1500671】データ変更フラグの設定タイミング変更：MOD start
			// データ変更
			// HenkouFg = true;

			// データ変更チェック
			chkHenkouData(ptdtShaisakuHin.getStrNishugata(), strNisugata);
			// 【KPX1500671】MOD end

			// 荷姿設定
			ptdtShaisakuHin.setStrNishugata(strNisugata);

			// 更新者情報の設定
			ptdtShaisakuHin.setDciKosinid(dciUserId);
			// DEL 2012.4.30　【H24年度対応】更新日で試作品の更新排他制御を行うため更新
			// ptdtShaisakuHin.setStrKosinhi(getSysDate());

		} catch (Exception e) {

			// エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("試作テーブルデータ保持の試作品荷姿更新が失敗しました");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/**
	 * 0032.試作品取扱温度更新 : 試作品データに対して取扱温度を設定する
	 * 
	 * @param strOndo
	 *            : 取扱温度
	 * @param dciUserId
	 *            : ユーザID
	 */
	public void UpdOndo(String strOndo, BigDecimal dciUserId)
			throws ExceptionBase {

		try {

			// 【KPX1500671】データ変更フラグの設定タイミング変更：MOD start
			// データ変更
			// HenkouFg = true;

			// データ変更チェック
			chkHenkouData(ptdtShaisakuHin.getStrOndo(), strOndo);
			// 【KPX1500671】MOD end

			// 取扱温度設定
			ptdtShaisakuHin.setStrOndo(strOndo);

			// 更新者情報の設定
			ptdtShaisakuHin.setDciKosinid(dciUserId);
			// DEL 2012.4.30　【H24年度対応】更新日で試作品の更新排他制御を行うため更新
			// ptdtShaisakuHin.setStrKosinhi(getSysDate());

		} catch (Exception e) {

			// エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("試作テーブルデータ保持の試作品取扱温度更新が失敗しました");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/**
	 * 0033.試作品賞味期間更新 : 試作品データに対して賞味期間を設定する
	 * 
	 * @param strSyoumikigen
	 *            : 賞味期限
	 * @param dciUserId
	 *            : ユーザID
	 */
	public void UpdSyoumikigen(String strSyoumikigen, BigDecimal dciUserId)
			throws ExceptionBase {

		try {

			// 【KPX1500671】データ変更フラグの設定タイミング変更：MOD start
			// データ変更
			// HenkouFg = true;

			// データ変更チェック
			chkHenkouData(ptdtShaisakuHin.getStrShomi(), strSyoumikigen);
			// 【KPX1500671】MOD end

			// 賞味期間設定
			ptdtShaisakuHin.setStrShomi(strSyoumikigen);

			// 更新者情報の設定
			ptdtShaisakuHin.setDciKosinid(dciUserId);
			// DEL 2012.4.30　【H24年度対応】更新日で試作品の更新排他制御を行うため更新
			// ptdtShaisakuHin.setStrKosinhi(getSysDate());

		} catch (Exception e) {

			// エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("試作テーブルデータ保持の試作品賞味期間更新が失敗しました");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/**
	 * 0034.試作品原価希望更新 : 試作品データに対して原価希望を設定する
	 * 
	 * @param strGenkaKibo
	 *            : 原価希望
	 * @param dciUserId
	 *            : ユーザID
	 */
	public void UpdGenkaKibo(String strGenkaKibo, BigDecimal dciUserId)
			throws ExceptionBase {

		try {

			// 【KPX1500671】データ変更フラグの設定タイミング変更：MOD start
			// データ変更
			// HenkouFg = true;

			// データ変更チェック
			chkHenkouData(ptdtShaisakuHin.getStrGenka(), strGenkaKibo);
			// 【KPX1500671】MOD end

			// 原価希望設定
			ptdtShaisakuHin.setStrGenka(strGenkaKibo);

			// 更新者情報の設定
			ptdtShaisakuHin.setDciKosinid(dciUserId);
			// DEL 2012.4.30　【H24年度対応】更新日で試作品の更新排他制御を行うため更新
			// ptdtShaisakuHin.setStrKosinhi(getSysDate());

		} catch (Exception e) {

			// エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("試作テーブルデータ保持の試作品原価希望更新が失敗しました");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/**
	 * 0035.試作品売価希望更新 : 試作品データに対して売価希望を設定する
	 * 
	 * @param strBaikaKibo
	 *            : 売価希望
	 * @param dciUserId
	 *            : ユーザID
	 */
	public void UpdBaikaKibo(String strBaikaKibo, BigDecimal dciUserId)
			throws ExceptionBase {

		try {

			// 【KPX1500671】データ変更フラグの設定タイミング変更：MOD start
			// データ変更
			// HenkouFg = true;

			// データ変更チェック
			chkHenkouData(ptdtShaisakuHin.getStrBaika(), strBaikaKibo);
			// 【KPX1500671】MOD end

			// 売価希望設定
			ptdtShaisakuHin.setStrBaika(strBaikaKibo);

			// 更新者情報の設定
			ptdtShaisakuHin.setDciKosinid(dciUserId);
			// DEL 2012.4.30　【H24年度対応】更新日で試作品の更新排他制御を行うため更新
			// ptdtShaisakuHin.setStrKosinhi(getSysDate());

		} catch (Exception e) {

			// エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("試作テーブルデータ保持の試作品売価希望更新が失敗しました");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/**
	 * 0036.試作品想定物量更新 : 試作品データに対して想定物量を設定する
	 * 
	 * @param strSouteiButuryo
	 *            : 想定物量
	 * @param iUserId
	 *            : ユーザID
	 */
	public void UpdSouteiButuryo(String strSouteiButuryo, BigDecimal dciUserId)
			throws ExceptionBase {

		try {

			// 【KPX1500671】データ変更フラグの設定タイミング変更：MOD start
			// データ変更
			// HenkouFg = true;

			// データ変更チェック
			chkHenkouData(ptdtShaisakuHin.getStrSotei(), strSouteiButuryo);
			// 【KPX1500671】MOD end

			// 想定物量設定
			ptdtShaisakuHin.setStrSotei(strSouteiButuryo);

			// 更新者情報の設定
			ptdtShaisakuHin.setDciKosinid(dciUserId);
			// DEL 2012.4.30　【H24年度対応】更新日で試作品の更新排他制御を行うため更新
			// ptdtShaisakuHin.setStrKosinhi(getSysDate());

		} catch (Exception e) {

			// エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("試作テーブルデータ保持の試作品想定物量更新が失敗しました");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/**
	 * 0037.試作品販売時期更新 : 試作品データに対して販売時期を設定する
	 * 
	 * @param strHanbaiJiki
	 *            : 販売時期
	 * @param dciUserId
	 *            : ユーザID
	 */
	public void UpdHanbaiJiki(String strHanbaiJiki, BigDecimal dciUserId)
			throws ExceptionBase {

		try {

			// 【KPX1500671】データ変更フラグの設定タイミング変更：MOD start
			// データ変更
			// HenkouFg = true;

			// データ変更チェック
			chkHenkouData(ptdtShaisakuHin.getStrHatubai(), strHanbaiJiki);
			// 【KPX1500671】MOD end

			// 販売時期設定
			ptdtShaisakuHin.setStrHatubai(strHanbaiJiki);

			// 更新者情報の設定
			ptdtShaisakuHin.setDciKosinid(dciUserId);
			// DEL 2012.4.30　【H24年度対応】更新日で試作品の更新排他制御を行うため更新
			// ptdtShaisakuHin.setStrKosinhi(getSysDate());

		} catch (Exception e) {

			// エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("試作テーブルデータ保持の試作品販売時期更新が失敗しました");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/**
	 * 0038.試作品計画売上更新 : 試作品データに対して計画売上を設定する
	 * 
	 * @param strKeikakuUriage
	 *            : 計画売上
	 * @param dciUserId
	 *            : ユーザID
	 */
	public void UpdKeikakuUriage(String strKeikakuUriage, BigDecimal dciUserId)
			throws ExceptionBase {

		try {

			// 【KPX1500671】データ変更フラグの設定タイミング変更：MOD start
			// データ変更
			// HenkouFg = true;

			// データ変更チェック
			chkHenkouData(ptdtShaisakuHin.getStrKeikakuUri(), strKeikakuUriage);
			// 【KPX1500671】MOD end

			// 計画売上設定
			ptdtShaisakuHin.setStrKeikakuUri(strKeikakuUriage);

			// 更新者情報の設定
			ptdtShaisakuHin.setDciKosinid(dciUserId);
			// DEL 2012.4.30　【H24年度対応】更新日で試作品の更新排他制御を行うため更新
			// ptdtShaisakuHin.setStrKosinhi(getSysDate());

		} catch (Exception e) {

			// エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("試作テーブルデータ保持の試作品計画売上更新が失敗しました");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/**
	 * 0039.試作品計画利益更新 : 試作品データに対して計画利益を設定する
	 * 
	 * @param strKeikakuRieki
	 *            : 計画利益
	 * @param dciUserId
	 *            : ユーザID
	 */
	public void UpdKeikakuRieki(String strKeikakuRieki, BigDecimal dciUserId)
			throws ExceptionBase {

		try {

			// 【KPX1500671】データ変更フラグの設定タイミング変更：MOD start
			// データ変更
			// HenkouFg = true;

			// データ変更チェック
			chkHenkouData(ptdtShaisakuHin.getStrKeikakuRie(), strKeikakuRieki);
			// 【KPX1500671】MOD end

			// 計画利益設定
			ptdtShaisakuHin.setStrKeikakuRie(strKeikakuRieki);

			// 更新者情報の設定
			ptdtShaisakuHin.setDciKosinid(dciUserId);
			// DEL 2012.4.30　【H24年度対応】更新日で試作品の更新排他制御を行うため更新
			// ptdtShaisakuHin.setStrKosinhi(getSysDate());

		} catch (Exception e) {

			// エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("試作テーブルデータ保持の試作品計画利益更新が失敗しました");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/**
	 * 0040.試作品販売後売上更新 : 試作品データに対して販売後売上を設定する
	 * 
	 * @param strHanbaigoUriage
	 *            : 販売後売上
	 * @param dciUserId
	 *            : ユーザID
	 */
	public void UpdHanbaigoUriage(String strHanbaigoUriage, BigDecimal dciUserId)
			throws ExceptionBase {

		try {

			// 【KPX1500671】データ変更フラグの設定タイミング変更：MOD start
			// データ変更
			// HenkouFg = true;

			// データ変更チェック
			chkHenkouData(ptdtShaisakuHin.getStrHanbaigoUri(),
					strHanbaigoUriage);
			// 【KPX1500671】MOD end

			// 販売後売上設定
			ptdtShaisakuHin.setStrHanbaigoUri(strHanbaigoUriage);

			// 更新者情報の設定
			ptdtShaisakuHin.setDciKosinid(dciUserId);
			// DEL 2012.4.30　【H24年度対応】更新日で試作品の更新排他制御を行うため更新
			// ptdtShaisakuHin.setStrKosinhi(getSysDate());

		} catch (Exception e) {

			// エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("試作テーブルデータ保持の試作品販売後売上更新が失敗しました");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/**
	 * 0041.試作品販売後利益更新 : 試作品データに対して販売後利益を設定する
	 * 
	 * @param strHanbaigoRieki
	 *            : 販売後利益
	 * @param dciUserId
	 *            : ユーザID
	 */
	public void UpdHanbaigoRieki(String strHanbaigoRieki, BigDecimal dciUserId)
			throws ExceptionBase {

		try {

			// 【KPX1500671】データ変更フラグの設定タイミング変更：MOD start
			// データ変更
			// HenkouFg = true;

			// データ変更チェック
			chkHenkouData(ptdtShaisakuHin.getStrHanbaigoRie(), strHanbaigoRieki);
			// 【KPX1500671】MOD end

			// 販売後利益設定
			ptdtShaisakuHin.setStrHanbaigoRie(strHanbaigoRieki);

			// 更新者情報の設定
			ptdtShaisakuHin.setDciKosinid(dciUserId);
			// DEL 2012.4.30　【H24年度対応】更新日で試作品の更新排他制御を行うため更新
			// ptdtShaisakuHin.setStrKosinhi(getSysDate());

		} catch (Exception e) {

			// エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("試作テーブルデータ保持の試作品販売後利益更新が失敗しました");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/**
	 * 0042.試作品総合メモ更新 : 試作品データに対して総合メモを設定する
	 * 
	 * @param strSogoMemo
	 *            : 総合メモ
	 * @param dciUserId
	 *            : ユーザID
	 */
	public void UpdSogoMemo(String strSogoMemo, BigDecimal dciUserId)
			throws ExceptionBase {

		try {

			// 【KPX1500671】データ変更フラグの設定タイミング変更：MOD start
			// データ変更
			// HenkouFg = true;

			// データ変更チェック
			chkHenkouData(ptdtShaisakuHin.getStrSogo(), strSogoMemo);
			// 【KPX1500671】MOD end

			// 総合メモ設定
			ptdtShaisakuHin.setStrSogo(strSogoMemo);

			// 更新者情報の設定
			ptdtShaisakuHin.setDciKosinid(dciUserId);
			// DEL 2012.4.30　【H24年度対応】更新日で試作品の更新排他制御を行うため更新
			// ptdtShaisakuHin.setStrKosinhi(getSysDate());

		} catch (Exception e) {

			// エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("試作テーブルデータ保持の試作品総合メモ更新が失敗しました");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/**
	 * 0043.配合データ検索 : 配合データの検索
	 * 
	 * @param intKouteiCd
	 *            : 工程CD
	 * @return 配列（配合データ）
	 */
	public ArrayList SearchHaigoData(int intKouteiCd) throws ExceptionBase {

		// 新規リストインスタンス生成
		ArrayList arr = new ArrayList();

		try {

			// 引数：工程CDが、0の場合
			if (intKouteiCd == 0) {
				// 最大工程順取得
				intMaxKotei = 0;
				for (int i = 0; i < aryHaigou.size(); i++) {
					MixedData mixedData = (MixedData) aryHaigou.get(i);
					if (intMaxKotei < mixedData.getIntKoteiNo()) {
						intMaxKotei = mixedData.getIntKoteiNo();
					}
				}

				arr = aryHaigou;
			} else {

				Iterator ite = aryHaigou.iterator();
				// リスト件数分ループ
				while (ite.hasNext()) {
					// 配合データオブジェクト取得
					MixedData mixData = (MixedData) ite.next();

					// 引数：工程CDと配合データオブジェクト：工程CDが一致した場合
					if (intKouteiCd == mixData.getIntKoteiCd()) {
						// 返却リストに配合データオブジェクト追加
						arr.add(mixData);
					}
				}
			}

		} catch (Exception e) {

			// エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("試作テーブルデータ保持の配合データ検索が失敗しました");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

		return arr;
	}

	/**
	 * 0044.配合工程選択 : 「選択工程」　を設定する
	 * 
	 * @param intKouteiCd
	 *            : 工程CD
	 */
	public void SelectHaigoKoutei(int intKouteiCd) throws ExceptionBase {

		try {

			// 選択工程CDと、引数:工程CDが一致した場合
			if (intSelectKotei == intKouteiCd) {

				// 選択工程CD初期化
				intSelectKotei = 0;

				// 選択工程CDと、引数:工程CDが一致しない場合
			} else {

				// 引数：工程CDを選択工程CDへ設定
				intSelectKotei = intKouteiCd;

			}

		} catch (Exception e) {

			// エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("試作テーブルデータ保持の配合工程選択が失敗しました");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/**
	 * 0045.配合工程データ追加 : 配合データへの工程追加処理を行う
	 * 
	 * @return MixedData : 追加配合データ
	 */
	public MixedData AddHaigoKoutei() throws ExceptionBase {

		try {

			// データ変更
			HenkouFg = true;

			// 最大工程ＣＤ取得
			int max_koteiCd = 0;
			for (int i = 0; i < aryHaigou.size(); i++) {
				MixedData MixedData = (MixedData) aryHaigou.get(i);
				if (max_koteiCd < MixedData.getIntKoteiCd()) {
					max_koteiCd = MixedData.getIntKoteiCd();
				}
			}
			// 最大工程ＣＤへ+1加算
			max_koteiCd++;
			// 配合データ追加
			MixedData addMixedData = new MixedData();
			// 試作コード-社員ＣＤ
			addMixedData.setDciShisakuUser(checkNullDecimal(DataCtrl
					.getInstance().getParamData().getStrSisaku_user()));
			// 試作コード-年
			addMixedData.setDciShisakuYear(checkNullDecimal(DataCtrl
					.getInstance().getParamData().getStrSisaku_nen()));
			// 試作コード-追番
			addMixedData.setDciShisakuNum(checkNullDecimal(DataCtrl
					.getInstance().getParamData().getStrSisaku_oi()));
			// 工程ＣＤ
			addMixedData.setIntKoteiCd(max_koteiCd);
			// 工程ＳＥＱ
			addMixedData.setIntKoteiSeq(1);
			// 会社CD
			addMixedData.setIntKaishaCd(ptdtShaisakuHin.getIntKaishacd());
			// 部署CD
			addMixedData.setIntBushoCd(ptdtShaisakuHin.getIntKojoco());
			// 登録者ＩＤ
			addMixedData.setDciTorokuId(DataCtrl.getInstance().getUserMstData()
					.getDciUserid());
			// 登録者名
			addMixedData.setStrTorokuNm(DataCtrl.getInstance().getUserMstData()
					.getStrUsernm());
			// 登録日付
			addMixedData.setStrTorokuHi(getSysDate());

			// 配合データへ追加
			aryHaigou.add(addMixedData);

			// 試作リストデータ追加
			for (int i = 0; i < aryShisakuRetu.size(); i++) {
				TrialData TrialData = (TrialData) aryShisakuRetu.get(i);
				// 試作リストデータ生成
				PrototypeListData addPrototypeListData = new PrototypeListData();
				// 試作ＣＤ-社員ＣＤ
				addPrototypeListData
						.setDciShisakuUser(checkNullDecimal(DataCtrl
								.getInstance().getParamData()
								.getStrSisaku_user()));
				// 試作ＣＤ-年
				addPrototypeListData
						.setDciShisakuYear(checkNullDecimal(DataCtrl
								.getInstance().getParamData()
								.getStrSisaku_nen()));
				// 試作ＣＤ-追番
				addPrototypeListData.setDciShisakuNum(checkNullDecimal(DataCtrl
						.getInstance().getParamData().getStrSisaku_oi()));
				// 試作ＳＥＱ
				addPrototypeListData.setIntShisakuSeq(TrialData
						.getIntShisakuSeq());
				// 工程ＣＤ
				addPrototypeListData.setIntKoteiCd(max_koteiCd);
				// 工程ＳＥＱ
				addPrototypeListData.setIntKoteiSeq(1);
				// 登録者ＩＤ
				addPrototypeListData.setDciTorokuId(DataCtrl.getInstance()
						.getUserMstData().getDciUserid());
				// 登録者名
				addPrototypeListData.setStrTorokuNm(DataCtrl.getInstance()
						.getUserMstData().getStrUsernm());
				// 登録日付
				addPrototypeListData.setStrTorokuHi(getSysDate());

				// 試作リストデータへ追加
				aryShisakuList.add(addPrototypeListData);
			}

			return addMixedData;

		} catch (Exception e) {
			// エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("試作テーブルデータ保持の配合工程データ追加が失敗しました");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/**
	 * 0046.配合工程順移動 : 「選択工程」の工程順の入れ替えを行う
	 * 
	 * @param strHoukouShitei
	 *            : 方向指定(上or下)
	 * @param dciUserId
	 *            : ユーザID
	 */
	public void MoveHaigoKoutei(String strHoukouShitei, BigDecimal dciUserId)
			throws ExceptionBase {

		try {

			// データ変更
			HenkouFg = true;

			// 方向指定判定
			if (strHoukouShitei.equals("上")) {

				// 選択工程順が1以外の場合のみ処理する。
				if (intSelectKotei != 1) {

					Iterator ite = aryHaigou.iterator();

					// リスト件数分ループ
					while (ite.hasNext()) {
						// 配合データオブジェクト取得
						MixedData mixData = (MixedData) ite.next();

						// 選択工程順と取得工程順が一致
						if (intSelectKotei == mixData.getIntKoteiNo()) {

							// 選択工程順を-1
							mixData.setIntKoteiCd(mixData.getIntKoteiNo() - 1);
							// 更新者情報の設定
							mixData.setDciKosinId(dciUserId);
							mixData.setStrKosinHi(getSysDate());

							// 選択工程順 - 1と取得工程順が一致
						} else if ((intSelectKotei - 1) == mixData
								.getIntKoteiNo()) {

							// 選択工程工程順を+1
							mixData.setIntKoteiCd(mixData.getIntKoteiNo() + 1);
							// 更新者情報の設定
							mixData.setDciKosinId(dciUserId);
							mixData.setStrKosinHi(getSysDate());

						}
					}
					// 選択工程順を-1
					intSelectKotei = intSelectKotei - 1;
				}
			} else if (strHoukouShitei.equals("下")) {

				// 選択工程順が1以外の場合のみ処理する。
				if (intSelectKotei != 1) {

					Iterator ite = aryHaigou.iterator();

					// リスト件数分ループ
					while (ite.hasNext()) {
						// 配合データオブジェクト取得
						MixedData mixData = (MixedData) ite.next();

						// 選択工程順と取得工程順が一致
						if (intSelectKotei == mixData.getIntKoteiNo()) {

							// 選択工程順を+1
							mixData.setIntKoteiCd(mixData.getIntKoteiNo() + 1);
							// 更新者情報の設定
							mixData.setDciKosinId(dciUserId);
							mixData.setStrKosinHi(getSysDate());

							// 選択工程順 - 1と取得工程順が一致
						} else if ((intSelectKotei + 1) == mixData
								.getIntKoteiNo()) {

							// 選択工程工程順を-1
							mixData.setIntKoteiCd(mixData.getIntKoteiNo() - 1);
							// 更新者情報の設定
							mixData.setDciKosinId(dciUserId);
							mixData.setStrKosinHi(getSysDate());

						}
					}

					// 選択工程順を+1
					intSelectKotei = intSelectKotei + 1;
				}
			}

		} catch (Exception e) {

			// エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("試作テーブルデータ保持の配合工程順移動が失敗しました");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/**
	 * 0047.配合工程削除 : 配合、試作リストデータを削除する
	 * 
	 * @param KoteiCd
	 *            : 削除用工程ＣＤ
	 */
	public void DelHaigoKoutei(int KoteiCd) throws ExceptionBase {
		try {

			// データ変更
			HenkouFg = true;

			// 配合データ削除
			Iterator iteHaigo = aryHaigou.iterator();
			// リスト件数分ループ
			while (iteHaigo.hasNext()) {
				// 配合データオブジェクト取得
				MixedData mixData = (MixedData) iteHaigo.next();
				// 工程CDが一致
				if (KoteiCd == mixData.getIntKoteiCd()) {
					iteHaigo.remove();
				}
			}

			// 試作リストデータ削除
			Iterator iteShisaku = aryShisakuList.iterator();
			// リスト件数分ループ
			while (iteShisaku.hasNext()) {
				// 配合データオブジェクト取得
				PrototypeListData PrototypeListData = (PrototypeListData) iteShisaku
						.next();
				// 工程CDが一致
				if (KoteiCd == PrototypeListData.getIntKoteiCd()) {
					iteShisaku.remove();
				}
			}

		} catch (Exception e) {

			// エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("試作テーブルデータ保持の配合工程削除が失敗しました");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/**
	 * 0048.配合工程データ更新（工程名） : 指定工程データに対して工程名を設定する
	 * 
	 * @param intKouteiCd
	 *            : 工程CD
	 * @param strKouteiMei
	 *            : 工程名
	 * @param dciUserId
	 *            : ユーザID
	 */
	public void UpdHaigoKouteimei(int intKouteiCd, String strKouteiMei,
			BigDecimal dciUserId) throws ExceptionBase {

		try {

			// 【KPX1500671】データ変更フラグの設定タイミング変更：MOD start
			// データ変更
			// HenkouFg = true;

			Iterator ite = aryHaigou.iterator();

			// リスト件数分ループ
			while (ite.hasNext()) {

				// 配合データオブジェクト取得
				MixedData mixData = (MixedData) ite.next();

				// 工程CDが一致
				if (intKouteiCd == mixData.getIntKoteiCd()) {
					// データ変更チェック
					chkHenkouData(mixData.getStrKouteiNm(), strKouteiMei);
					// 【KPX1500671】MOD end

					// 工程名設定
					mixData.setStrKouteiNm(strKouteiMei);
					// 更新者情報の設定
					mixData.setDciKosinId(dciUserId);
					mixData.setStrKosinHi(getSysDate());
				}
			}

		} catch (Exception e) {

			// エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("試作テーブルデータ保持の配合工程データ更新（工程名）が失敗しました");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/**
	 * 0049.配合工程データ更新（工程属性） : 指定工程データに対して工程属性を設定する
	 * 
	 * @param intKouteiCd
	 *            : 工程CD
	 * @param intKouteiZokusei
	 *            : 工程属性
	 * @param dciUserId
	 *            : ユーザID
	 */
	public void UpdHaigoZokusei(int intKouteiCd, String strKouteiZokusei,
			BigDecimal dciUserId) throws ExceptionBase {

		try {

			// 【KPX1500671】データ変更フラグの設定タイミング変更：MOD start
			// データ変更
			// HenkouFg = true;

			Iterator ite = aryHaigou.iterator();

			// リスト件数分ループ
			while (ite.hasNext()) {

				// 配合データオブジェクト取得
				MixedData mixData = (MixedData) ite.next();

				// 工程CDが一致
				if (intKouteiCd == mixData.getIntKoteiCd()) {
					// データ変更チェック
					chkHenkouData(mixData.getStrKouteiZokusei(),
							strKouteiZokusei);
					// 【KPX1500671】MOD end

					// 工程属性設定
					mixData.setStrKouteiZokusei(strKouteiZokusei);
					// 更新者情報の設定
					mixData.setDciKosinId(dciUserId);
					mixData.setStrKosinHi(getSysDate());
				}
			}

		} catch (Exception e) {

			// エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("試作テーブルデータ保持の配合工程データ更新（工程属性）が失敗しました");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/**
	 * 0050.配合原料選択 : 「選択原料」　を設定する
	 * 
	 * @param intKouteiCd
	 *            : 工程CD
	 * @param intKouteiSeq
	 *            : 工程SEQ
	 */
	public void SelectHaigoGenryo(int intKouteiCd, int intKouteiSeq)
			throws ExceptionBase {

		try {

			// 工程CD・工程SEQ取得
			ArrayList arrKouteiCd = (ArrayList) arySelectGenka.get(0);
			ArrayList arrKouteiSeq = (ArrayList) arySelectGenka.get(1);

			// 削除FLG
			boolean blDelFlg = false;

			// 選択原料判定
			for (int i = 0; i < arrKouteiCd.size(); i++) {
				// 引数工程CD・工程SEQと一致する場合
				if (arrKouteiCd.get(i).equals(Integer.toString(intKouteiCd))
						&& arrKouteiSeq.get(i).equals(
								Integer.toString(intKouteiSeq))) {

					// 選択原料削除
					arrKouteiCd.remove(i);
					arrKouteiSeq.remove(i);

					arySelectGenka.set(0, arrKouteiCd);
					arySelectGenka.set(1, arrKouteiSeq);

					blDelFlg = true;
				}
			}

			// 削除対象がない場合
			if (!blDelFlg) {
				// 選択原料設定
				arrKouteiCd.add(Integer.toString(intKouteiCd));
				arrKouteiSeq.add(Integer.toString(intKouteiSeq));
				arySelectGenka.set(0, arrKouteiCd);
				arySelectGenka.set(1, arrKouteiSeq);
			}

		} catch (Exception e) {
			// エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("試作テーブルデータ保持の配合原料選択が失敗しました");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/**
	 * 0051.配合原料データ追加 : 配合データへの原料追加処理を行う
	 * 
	 * @param koteiCd
	 *            : 工程CD
	 * @param koteiSeq
	 *            : 工程SEQ
	 * @return MixedData : 追加配合データ
	 */
	public MixedData AddHaigoGenryo(String koteiCd) throws ExceptionBase {

		try {

			// データ変更
			HenkouFg = true;

			// --------------------------- 配合データ追加 ---------------------------
			// 最大工程SEQ,原料順取得
			int intMaxKoteiSeq = 0;
			int koteiNo = 0;
			String koteiNm = "";
			String koteiZoku = "";

			for (int i = 0; i < aryHaigou.size(); i++) {
				MixedData selHaigou = (MixedData) aryHaigou.get(i);
				// データ保持内の配合データ：工程CD　と　引数：工程CDが等しい場合
				if (selHaigou.getIntKoteiCd() == Integer.parseInt(koteiCd)) {
					// 最大工程SEQ取得
					if (intMaxKoteiSeq < selHaigou.getIntKoteiSeq()) {
						intMaxKoteiSeq = selHaigou.getIntKoteiSeq();
					}
					// 工程順
					koteiNo = selHaigou.getIntKoteiNo();
					// 工程名
					koteiNm = selHaigou.getStrKouteiNm();
					// 工程属性
					koteiZoku = selHaigou.getStrKouteiZokusei();
				}
			}
			intMaxKoteiSeq++;

			// 追加用配合データ
			MixedData addMixedData = new MixedData();
			// 試作-社員CD
			addMixedData.setDciShisakuUser(checkNullDecimal(DataCtrl
					.getInstance().getParamData().getStrSisaku_user()));
			// 試作-年
			addMixedData.setDciShisakuYear(checkNullDecimal(DataCtrl
					.getInstance().getParamData().getStrSisaku_nen()));
			// 試作-追番
			addMixedData.setDciShisakuNum(checkNullDecimal(DataCtrl
					.getInstance().getParamData().getStrSisaku_oi()));
			// 工程CD
			addMixedData.setIntKoteiCd(Integer.parseInt(koteiCd));
			// 工程SEQ
			addMixedData.setIntKoteiSeq(intMaxKoteiSeq);
			// 工程名
			addMixedData.setStrKouteiNm(koteiNm);
			// 工程属性
			addMixedData.setStrKouteiZokusei(koteiZoku);
			// 工程順
			addMixedData.setIntKoteiNo(koteiNo);
			// 会社CD
			addMixedData.setIntKaishaCd(ptdtShaisakuHin.getIntKaishacd());
			// 部署CD
			addMixedData.setIntBushoCd(ptdtShaisakuHin.getIntKojoco());
			// 登録者ID
			addMixedData.setDciTorokuId(DataCtrl.getInstance().getUserMstData()
					.getDciUserid());
			// 登録者名
			addMixedData.setStrTorokuNm(DataCtrl.getInstance().getUserMstData()
					.getStrUsernm());
			// 登録日
			addMixedData.setStrTorokuHi(getSysDate());

			// 配合データ追加
			aryHaigou.add(addMixedData);

			// -------------------------- 試作リストデータ追加 ---------------------------
			int count = aryShisakuRetu.size();
			for (int i = 0; i < count; i++) {
				TrialData selTrialData = (TrialData) aryShisakuRetu.get(i);
				PrototypeListData addPrototypeListData = new PrototypeListData();
				// 試作-社員CD
				addPrototypeListData
						.setDciShisakuUser(checkNullDecimal(DataCtrl
								.getInstance().getParamData()
								.getStrSisaku_user()));
				// 試作-年
				addPrototypeListData
						.setDciShisakuYear(checkNullDecimal(DataCtrl
								.getInstance().getParamData()
								.getStrSisaku_nen()));
				// 試作-追番
				addPrototypeListData.setDciShisakuNum(checkNullDecimal(DataCtrl
						.getInstance().getParamData().getStrSisaku_oi()));
				// 試作SEQ
				addPrototypeListData.setIntShisakuSeq(selTrialData
						.getIntShisakuSeq());
				// 工程CD
				addPrototypeListData.setIntKoteiCd(Integer.parseInt(koteiCd));
				// 工程SEQ
				addPrototypeListData.setIntKoteiSeq(intMaxKoteiSeq);
				// 登録者ID
				addPrototypeListData.setDciTorokuId(DataCtrl.getInstance()
						.getUserMstData().getDciUserid());
				// 登録者名
				addPrototypeListData.setStrTorokuNm(DataCtrl.getInstance()
						.getUserMstData().getStrUsernm());
				// 登録日付
				addPrototypeListData.setStrTorokuHi(getSysDate());

				// 試作リストデータ追加
				aryShisakuList.add(addPrototypeListData);
			}

			return addMixedData;

		} catch (Exception e) {
			// エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("試作テーブルデータ保持の配合原料データ追加が失敗しました");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}
	}

	/**
	 * 0051_1.配合原料データ追加 : 0051.配合原料データ追加メソッドをオーバロード : 原料行がない場合の配合データへの原料追加処理を行う
	 * 
	 * @param koteiCd
	 *            : 工程CD
	 * @param koteiSeq
	 *            : 工程SEQ
	 * @param koteiNo
	 *            : 工程順
	 * @param koteiNm
	 *            : 工程名
	 * @param koteiZoku
	 *            : 工程属性
	 * @return MixedData : 追加配合データ
	 */
	public MixedData AddHaigoGenryo(String koteiCd, String koteiSeq,
			int koteiNo, String koteiNm, String koteiZoku) throws ExceptionBase {

		try {

			// データ変更
			HenkouFg = true;

			// --------------------------- 配合データ追加 ---------------------------
			// 追加用配合データ
			MixedData addMixedData = new MixedData();
			// 試作-社員CD
			addMixedData.setDciShisakuUser(checkNullDecimal(DataCtrl
					.getInstance().getParamData().getStrSisaku_user()));
			// 試作-年
			addMixedData.setDciShisakuYear(checkNullDecimal(DataCtrl
					.getInstance().getParamData().getStrSisaku_nen()));
			// 試作-追番
			addMixedData.setDciShisakuNum(checkNullDecimal(DataCtrl
					.getInstance().getParamData().getStrSisaku_oi()));
			// 工程CD
			addMixedData.setIntKoteiCd(Integer.parseInt(koteiCd));
			// 工程SEQ
			addMixedData.setIntKoteiSeq(Integer.parseInt(koteiSeq));
			// 工程名
			addMixedData.setStrKouteiNm(koteiNm);
			// 工程属性
			addMixedData.setStrKouteiZokusei(koteiZoku);
			// 工程順
			addMixedData.setIntKoteiNo(koteiNo);
			// 会社CD
			addMixedData.setIntKaishaCd(ptdtShaisakuHin.getIntKaishacd());
			// 部署CD
			addMixedData.setIntBushoCd(ptdtShaisakuHin.getIntKojoco());
			// 登録者ID
			addMixedData.setDciTorokuId(DataCtrl.getInstance().getUserMstData()
					.getDciUserid());
			// 登録者名
			addMixedData.setStrTorokuNm(DataCtrl.getInstance().getUserMstData()
					.getStrUsernm());
			// 登録日
			addMixedData.setStrTorokuHi(getSysDate());

			// 配合データ追加
			aryHaigou.add(addMixedData);

			// -------------------------- 試作リストデータ追加 ---------------------------
			int count = aryShisakuRetu.size();
			for (int i = 0; i < count; i++) {

				TrialData selTrialData = (TrialData) aryShisakuRetu.get(i);
				PrototypeListData addPrototypeListData = new PrototypeListData();
				// 試作-社員CD
				addPrototypeListData
						.setDciShisakuUser(checkNullDecimal(DataCtrl
								.getInstance().getParamData()
								.getStrSisaku_user()));
				// 試作-年
				addPrototypeListData
						.setDciShisakuYear(checkNullDecimal(DataCtrl
								.getInstance().getParamData()
								.getStrSisaku_nen()));
				// 試作-追番
				addPrototypeListData.setDciShisakuNum(checkNullDecimal(DataCtrl
						.getInstance().getParamData().getStrSisaku_oi()));
				// 試作SEQ
				addPrototypeListData.setIntShisakuSeq(selTrialData
						.getIntShisakuSeq());
				// 工程CD
				addPrototypeListData.setIntKoteiCd(Integer.parseInt(koteiCd));
				// 工程SEQ
				addPrototypeListData.setIntKoteiSeq(Integer.parseInt(koteiSeq));
				// 登録者ID
				addPrototypeListData.setDciTorokuId(DataCtrl.getInstance()
						.getUserMstData().getDciUserid());
				// 登録者名
				addPrototypeListData.setStrTorokuNm(DataCtrl.getInstance()
						.getUserMstData().getStrUsernm());
				// 登録日付
				addPrototypeListData.setStrTorokuHi(getSysDate());

				// 試作リストデータ追加
				aryShisakuList.add(addPrototypeListData);
			}

			return addMixedData;

		} catch (Exception e) {
			// エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("試作テーブルデータ保持の配合原料データ追加（原料行がない場合）が失敗しました");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/**
	 * 0051_2.原料順設定 : 原料順を設定する
	 * 
	 * @param KoteiCd
	 *            : 移動元工程ＣＤ
	 * @param KoteiSeq
	 *            : 移動元工程ＳＥＱ
	 * @param GenryoNo
	 *            : 原料順
	 */
	public void NoHaigoGenryo(String KoteiCd, String KoteiSeq, int GenryoNo)
			throws ExceptionBase {
		try {
			// 原料順設定
			for (int i = 0; i < aryHaigou.size(); i++) {

				MixedData selHaigou = (MixedData) aryHaigou.get(i);

				if (selHaigou.getIntKoteiCd() == Integer.parseInt(KoteiCd)
						&& selHaigou.getIntKoteiSeq() == Integer
								.parseInt(KoteiSeq)) {

					selHaigou.setIntGenryoNo(GenryoNo);

				}
			}
		} catch (Exception e) {
			// エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("試作テーブルデータ保持の原料順設定が失敗しました");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;
		}

	}

	/**
	 * 0051_3.原料順設定 : 工程＆原料順を設定する
	 * 
	 * @param KoteiCd
	 *            : 移動元工程ＣＤ
	 * @param KoteiSeq
	 *            : 移動元工程ＳＥＱ
	 * @param KoteiNo
	 *            : 原料順
	 * @param GenryoNo
	 *            : 原料順
	 */
	public void NoHaigoGenryo(String KoteiCd, String KoteiSeq, int KoteiNo,
			int GenryoNo) throws ExceptionBase {
		try {

			// 工程＆原料順設定
			for (int i = 0; i < aryHaigou.size(); i++) {

				MixedData selHaigou = (MixedData) aryHaigou.get(i);

				if (selHaigou.getIntKoteiCd() == Integer.parseInt(KoteiCd)
						&& selHaigou.getIntKoteiSeq() == Integer
								.parseInt(KoteiSeq)) {

					// 工程順設定
					selHaigou.setIntKoteiNo(KoteiNo);

					// 原料順設定
					selHaigou.setIntGenryoNo(GenryoNo);

				}
			}
		} catch (Exception e) {
			// エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("試作テーブルデータ保持の原料順設定（工程＆原料順）が失敗しました");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;
		}
	}

	/**
	 * 0052.配合原料データ移動 : 「選択原料」の原料順の入れ替えを行う
	 * 
	 * @param KoteiCd_moto
	 *            : 移動元工程ＣＤ
	 * @param KoteiSeq_moto
	 *            : 移動元工程ＳＥＱ
	 * @param KoteiCd_saki
	 *            : 移動先工程ＣＤ
	 * @param KoteiSeq_saki
	 *            : 移動先工程ＳＥＱ
	 * @param strHoukouShitei
	 *            : 移動数
	 * @return MixedData : 移動配合データ
	 */
	public MixedData MoveHaigoGenryo(String KoteiCd_moto, String KoteiSeq_moto,
			String KoteiCd_saki, String KoteiSeq_saki, int HoukouShitei)
			throws ExceptionBase {

		try {

			// データ変更
			HenkouFg = true;

			// 原料順取得用
			MixedData Haigou_saki = new MixedData();

			// ----------------------- 他工程内への原料順移動処理
			// -------------------------------
			if (!KoteiCd_moto.equals(KoteiCd_saki)) {

				// 移動元配合情報取得
				MixedData Haigou_moto = new MixedData();
				for (int i = 0; i < aryHaigou.size(); i++) {

					MixedData selHaigou = (MixedData) aryHaigou.get(i);

					if (selHaigou.getIntKoteiCd() == Integer
							.parseInt(KoteiCd_moto)
							&& selHaigou.getIntKoteiSeq() == Integer
									.parseInt(KoteiSeq_moto)) {
						Haigou_moto = selHaigou;
					}
				}

				// 移動先工程へ移動元原料を挿入
				Haigou_saki = AddHaigoGenryo(KoteiCd_saki);

				// 原料CD
				Haigou_saki.setStrGenryoCd(Haigou_moto.getStrGenryoCd());
				// 会社CD
				Haigou_saki.setIntKaishaCd(Haigou_moto.getIntKaishaCd());
				// 部署CD
				Haigou_saki.setIntBushoCd(Haigou_moto.getIntBushoCd());
				// 原料名称
				Haigou_saki.setStrGenryoNm(Haigou_moto.getStrGenryoNm());
				// 単価
				Haigou_saki.setDciTanka(Haigou_moto.getDciTanka());
				// 歩留
				Haigou_saki.setDciBudomari(Haigou_moto.getDciBudomari());
				// 油含有率
				Haigou_saki.setDciGanyuritu(Haigou_moto.getDciGanyuritu());
				// 酢酸
				Haigou_saki.setDciSakusan(Haigou_moto.getDciSakusan());
				// 食塩
				Haigou_saki.setDciShokuen(Haigou_moto.getDciShokuen());
				// ADD start 20121002 QP@20505 No.24
				// ＭＳＧ
				Haigou_saki.setDciMsg(Haigou_moto.getDciMsg());
				// ADD end 20121002 QP@20505 No.24
				// 総酸
				Haigou_saki.setDciSosan(Haigou_moto.getDciSosan());
				// 色
				Haigou_saki.setStrIro(Haigou_moto.getStrIro());
				// 登録者ID
				Haigou_saki.setDciTorokuId(Haigou_moto.getDciTorokuId());
				// 登録者名
				Haigou_saki.setStrTorokuNm(Haigou_moto.getStrTorokuNm());
				// 登録日付
				Haigou_saki.setStrTorokuHi(Haigou_moto.getStrTorokuHi());
				// 更新者ID
				Haigou_saki.setDciKosinId(DataCtrl.getInstance()
						.getUserMstData().getDciUserid());
				// 更新者名
				Haigou_saki.setStrKosinNm(DataCtrl.getInstance()
						.getUserMstData().getStrUsernm());
				// 更新日付
				Haigou_saki.setStrKosinHi(getSysDate());

				// マスタ歩留まり
				Haigou_saki.setDciMaBudomari(Haigou_moto.getDciMaBudomari());

				// //挿入先データ更新
				// for(int i=0; i<aryHaigou.size(); i++){
				//
				// MixedData selHaigou = (MixedData)aryHaigou.get(i);
				//
				// if(Haigou_saki.getIntKoteiCd() == selHaigou.getIntKoteiCd()
				// && Haigou_saki.getIntKoteiSeq() ==
				// selHaigou.getIntKoteiSeq()){
				//
				// //データ更新
				// aryHaigou.set(i, Haigou_saki);
				// }
				// }

				// 試作リストデータ更新
				for (int j = 0; j < aryShisakuList.size(); j++) {

					// 試作リストデータ取得
					PrototypeListData pld = (PrototypeListData) aryShisakuList
							.get(j);

					// 移動元データ取得
					if (pld.getIntKoteiCd() == Integer.parseInt(KoteiCd_moto)
							&& pld.getIntKoteiSeq() == Integer
									.parseInt(KoteiSeq_moto)) {

						// 移動元データ退避
						PrototypeListData pld_moto = pld;

						// 移動先データへ更新
						for (int k = 0; k < aryShisakuList.size(); k++) {

							PrototypeListData pldUpd = (PrototypeListData) aryShisakuList
									.get(k);

							// 移動先データ取得
							if (pldUpd.getIntKoteiCd() == Haigou_saki
									.getIntKoteiCd()
									&& pldUpd.getIntKoteiSeq() == Haigou_saki
											.getIntKoteiSeq()
									&& pldUpd.getIntShisakuSeq() == pld_moto
											.getIntShisakuSeq()) {

								// 移動先データへ移動元データ更新
								PrototypeListData pld_saki = pldUpd;

								// 量
								pld_saki.setDciRyo(pld_moto.getDciRyo());
								// 色
								pld_saki.setStrIro(pld_moto.getStrIro());
								// 更新者ID
								pld_saki.setDciKosinId(DataCtrl.getInstance()
										.getUserMstData().getDciUserid());
								// 更新者名
								pld_saki.setStrKosinNm(DataCtrl.getInstance()
										.getUserMstData().getStrUsernm());
								// 更新日
								pld_saki.setStrKosinHi(getSysDate());

							}
						}
					}
				}

				// 移動元原料を移動元工程より削除
				DelHaigoGenryo(KoteiCd_moto, KoteiSeq_moto);

				// ----------------------- 同工程内への原料順移動処理
				// -------------------------------
			} else {

				for (int i = 0; i < aryHaigou.size(); i++) {

					MixedData selHaigou = (MixedData) aryHaigou.get(i);

					if (selHaigou.getIntKoteiCd() == Integer
							.parseInt(KoteiCd_moto)
							&& selHaigou.getIntKoteiSeq() == Integer
									.parseInt(KoteiSeq_moto)) {

						Haigou_saki = selHaigou;
					}
				}
			}

			return Haigou_saki;

		} catch (Exception e) {

			// エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("試作テーブルデータ保持の配合原料データ移動が失敗しました");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}
	}

	/**
	 * 0053.配合原料削除 : 配合データへの原料削除処理を行う
	 * 
	 * @param koteiCd
	 *            : 工程CD
	 * @param koteiSeq
	 *            : 工程SEQ
	 * 
	 */
	public void DelHaigoGenryo(String koteiCd, String koteiSeq)
			throws ExceptionBase {

		try {

			// データ変更
			HenkouFg = true;

			// 配合データ削除
			Iterator iteHaigo = aryHaigou.iterator();

			// リスト件数分ループ
			while (iteHaigo.hasNext()) {

				// 配合データオブジェクト取得
				MixedData mixData = (MixedData) iteHaigo.next();

				// 工程CDが一致
				if (mixData.getIntKoteiCd() == Integer.parseInt(koteiCd)
						&& mixData.getIntKoteiSeq() == Integer
								.parseInt(koteiSeq)) {

					iteHaigo.remove();

				}
			}

			// 試作リストデータ削除
			Iterator iteShisaku = aryShisakuList.iterator();

			// リスト件数分ループ
			while (iteShisaku.hasNext()) {

				// 配合データオブジェクト取得
				PrototypeListData PrototypeListData = (PrototypeListData) iteShisaku
						.next();

				// 工程CDが一致
				if (PrototypeListData.getIntKoteiCd() == Integer
						.parseInt(koteiCd)
						&& PrototypeListData.getIntKoteiSeq() == Integer
								.parseInt(koteiSeq)) {

					iteShisaku.remove();

				}
			}
		} catch (Exception e) {

			// エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("試作テーブルデータ保持の配合原料削除が失敗しました");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/**
	 * 0054.配合原料CD更新 : 指定原料データに対して原料CDを設定する
	 * 
	 * @param intKouteiCd
	 *            : 工程CD
	 * @param intKouteiSeq
	 *            : 工程SEQ
	 * @param strGenryoCd
	 *            : 原料CD
	 * @param dciUserId
	 *            : ユーザID
	 */
	public void UpdHaigoGenryoCd(int intKouteiCd, int intKouteiSeq,
			String strGenryoCd, BigDecimal dciUserId) throws ExceptionBase {

		try {

			// 【KPX1500671】データ変更フラグの設定タイミング変更：MOD start
			// データ変更
			// HenkouFg = true;

			Iterator ite = aryHaigou.iterator();

			// リスト件数分ループ
			while (ite.hasNext()) {

				// 配合データオブジェクト取得
				MixedData mixData = (MixedData) ite.next();

				// 工程CD・工程SEQが一致
				if (intKouteiCd == mixData.getIntKoteiCd()
						&& intKouteiSeq == mixData.getIntKoteiSeq()) {

					// データ変更チェック
					chkHenkouData(mixData.getStrGenryoCd(), strGenryoCd);
					// 【KPX1500671】MOD end

					// 原料CD設定
					mixData.setStrGenryoCd(strGenryoCd);

					// 更新者情報の設定
					mixData.setDciKosinId(dciUserId);
					mixData.setStrKosinHi(getSysDate());

				}
			}

		} catch (Exception e) {

			// エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("試作テーブルデータ保持の配合原料CD更新が失敗しました");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/**
	 * 0054_1.配合原料会社CD更新 : 指定原料データに対して会社CDを設定する
	 * 
	 * @param intKouteiCd
	 *            : 工程CD
	 * @param intKouteiSeq
	 *            : 工程SEQ
	 * @param intKaishaCd
	 *            : 会社CD
	 * @param dciUserId
	 *            : ユーザID
	 */
	public void UpdHaigoKaishaCd(int intKouteiCd, int intKouteiSeq,
			int intKaishaCd, BigDecimal dciUserId) throws ExceptionBase {

		try {

			// 【KPX1500671】データ変更フラグの設定タイミング変更：MOD start
			// データ変更
			// HenkouFg = true;

			Iterator ite = aryHaigou.iterator();

			// リスト件数分ループ
			while (ite.hasNext()) {

				// 配合データオブジェクト取得
				MixedData mixData = (MixedData) ite.next();

				// 工程CD・工程SEQが一致
				if (intKouteiCd == mixData.getIntKoteiCd()
						&& intKouteiSeq == mixData.getIntKoteiSeq()) {

					// データ変更チェック
					chkHenkouData(mixData.getIntKaishaCd(), intKaishaCd);
					// 【KPX1500671】MOD end

					// 会社CD設定
					mixData.setIntKaishaCd(intKaishaCd);

					// 更新者情報の設定
					mixData.setDciKosinId(dciUserId);
					mixData.setStrKosinHi(getSysDate());

				}
			}

		} catch (Exception e) {

			// エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("試作テーブルデータ保持の配合原料会社CD更新が失敗しました");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/**
	 * 0054_2.配合原料工場CD更新 : 指定原料データに対して工場CDを設定する
	 * 
	 * @param intKouteiCd
	 *            : 工程CD
	 * @param intKouteiSeq
	 *            : 工程SEQ
	 * @param intKojoCd
	 *            : 工場CD
	 * @param dciUserId
	 *            : ユーザID
	 */
	public void UpdHaigoKojoCd(int intKouteiCd, int intKouteiSeq,
			int intKojoCd, BigDecimal dciUserId) throws ExceptionBase {

		try {

			// 【KPX1500671】データ変更フラグの設定タイミング変更：MOD start
			// データ変更
			// HenkouFg = true;

			Iterator ite = aryHaigou.iterator();

			// リスト件数分ループ
			while (ite.hasNext()) {

				// 配合データオブジェクト取得
				MixedData mixData = (MixedData) ite.next();

				// 工程CD・工程SEQが一致
				if (intKouteiCd == mixData.getIntKoteiCd()
						&& intKouteiSeq == mixData.getIntKoteiSeq()) {

					// データ変更チェック
					chkHenkouData(mixData.getIntBushoCd(), intKojoCd);
					// 【KPX1500671】MOD end

					// 工場CD設定
					mixData.setIntBushoCd(intKojoCd);

					// 更新者情報の設定
					mixData.setDciKosinId(dciUserId);
					mixData.setStrKosinHi(getSysDate());

				}
			}

		} catch (Exception e) {

			// エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("試作テーブルデータ保持の配合原料工場CD更新が失敗しました");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/**
	 * 0055.配合原料名称更新 : 指定原料データに対して原料名称を設定する
	 * 
	 * @param intKouteiCd
	 *            : 工程CD
	 * @param intKouteiSeq
	 *            : 工程SEQ
	 * @param strGenryoMei
	 *            : 原料名称
	 * @param dciUserId
	 *            : ユーザID
	 */
	public void UpdHaigoGenryoMei(int intKouteiCd, int intKouteiSeq,
			String strGenryoMei, BigDecimal dciUserId) throws ExceptionBase {

		try {

			// 【KPX1500671】データ変更フラグの設定タイミング変更：MOD start
			// データ変更
			// HenkouFg = true;

			Iterator ite = aryHaigou.iterator();

			// リスト件数分ループ
			while (ite.hasNext()) {

				// 配合データオブジェクト取得
				MixedData mixData = (MixedData) ite.next();

				// 工程CD・工程SEQが一致
				if (intKouteiCd == mixData.getIntKoteiCd()
						&& intKouteiSeq == mixData.getIntKoteiSeq()) {

					// データ変更チェック
					chkHenkouData(mixData.getStrGenryoNm(), strGenryoMei);
					// 【KPX1500671】MOD end

					// 原料名称設定
					mixData.setStrGenryoNm(strGenryoMei);

					// 更新者情報の設定
					mixData.setDciKosinId(dciUserId);
					mixData.setStrKosinHi(getSysDate());

				}
			}

		} catch (Exception e) {

			// エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("試作テーブルデータ保持の配合原料名称更新が失敗しました");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/**
	 * 0056.配合原料単価更新 : 指定原料データに対して原料単価を設定する
	 * 
	 * @param intKouteiCd
	 *            : 工程CD
	 * @param intKouteiSeq
	 *            : 工程SEQ
	 * @param dciGenryoTanka
	 *            : 原料単価
	 * @param dciUserId
	 *            : ユーザID
	 */
	public void UpdHaigoGenryoTanka(int intKouteiCd, int intKouteiSeq,
			BigDecimal dciGenryoTanka, BigDecimal dciUserId)
			throws ExceptionBase {

		try {

			// 【KPX1500671】データ変更フラグの設定タイミング変更：MOD start
			// データ変更
			// HenkouFg = true;

			Iterator ite = aryHaigou.iterator();

			// リスト件数分ループ
			while (ite.hasNext()) {

				// 配合データオブジェクト取得
				MixedData mixData = (MixedData) ite.next();

				// 工程CD・工程SEQが一致
				if (intKouteiCd == mixData.getIntKoteiCd()
						&& intKouteiSeq == mixData.getIntKoteiSeq()) {

					// データ変更チェック
					chkHenkouData(mixData.getDciTanka(), dciGenryoTanka);
					// 【KPX1500671】MOD end

					// 単価設定
					mixData.setDciTanka(dciGenryoTanka);

					// 更新者情報の設定
					mixData.setDciKosinId(dciUserId);
					mixData.setStrKosinHi(getSysDate());

				}
			}

		} catch (Exception e) {

			// エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("試作テーブルデータ保持の配合原料単価更新が失敗しました");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/**
	 * 0057.配合原料歩留更新 : 指定原料データに対して原料歩留を設定する
	 * 
	 * @param intKouteiCd
	 *            : 工程CD
	 * @param intKouteiSeq
	 *            : 工程SEQ
	 * @param dciBudomari
	 *            : 原料歩留
	 * @param dciUserId
	 *            : ユーザID
	 */
	public void UpdHaigoGenryoBudomari(int intKouteiCd, int intKouteiSeq,
			BigDecimal dciBudomari, BigDecimal dciUserId) throws ExceptionBase {

		try {

			// 【KPX1500671】データ変更フラグの設定タイミング変更：MOD start
			// データ変更
			// HenkouFg = true;

			Iterator ite = aryHaigou.iterator();

			// リスト件数分ループ
			while (ite.hasNext()) {

				// 配合データオブジェクト取得
				MixedData mixData = (MixedData) ite.next();

				// 工程CD・工程SEQが一致
				if (intKouteiCd == mixData.getIntKoteiCd()
						&& intKouteiSeq == mixData.getIntKoteiSeq()) {

					// データ変更チェック
					chkHenkouData(mixData.getDciBudomari(), dciBudomari);
					// 【KPX1500671】MOD end

					// 歩留設定
					mixData.setDciBudomari(dciBudomari);

					// 更新者情報の設定
					mixData.setDciKosinId(dciUserId);
					mixData.setStrKosinHi(getSysDate());

				}
			}

		} catch (Exception e) {

			// エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("試作テーブルデータ保持の配合原料歩留更新が失敗しました");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/**
	 * 0058.配合原料油含有率更新 : 指定原料データに対して原料油含有率を設定する
	 * 
	 * @param intKouteiCd
	 *            : 工程CD
	 * @param intKouteiSeq
	 *            : 工程SEQ
	 * @param dciYuganyuryo
	 *            : 原料油含有量
	 * @param dciUserId
	 *            : ユーザID
	 */
	public void UpdHaigoGenryoYuganyuryo(int intKouteiCd, int intKouteiSeq,
			BigDecimal dciYuganyuryo, BigDecimal dciUserId)
			throws ExceptionBase {

		try {

			// 【KPX1500671】データ変更フラグの設定タイミング変更：MOD start
			// データ変更
			// HenkouFg = true;

			Iterator ite = aryHaigou.iterator();

			// リスト件数分ループ
			while (ite.hasNext()) {

				// 配合データオブジェクト取得
				MixedData mixData = (MixedData) ite.next();

				// 工程CD・工程SEQが一致
				if (intKouteiCd == mixData.getIntKoteiCd()
						&& intKouteiSeq == mixData.getIntKoteiSeq()) {

					// データ変更チェック
					chkHenkouData(mixData.getDciGanyuritu(), dciYuganyuryo);
					// 【KPX1500671】MOD end

					// 油含有量設定
					mixData.setDciGanyuritu(dciYuganyuryo);

					// 更新者情報の設定
					mixData.setDciKosinId(dciUserId);
					mixData.setStrKosinHi(getSysDate());

				}
			}

		} catch (Exception e) {

			// エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("試作テーブルデータ保持の配合原料油含有率更新が失敗しました");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/**
	 * 0059.配合原料酢酸更新 : 指定原料データに対して原料酢酸を設定する
	 * 
	 * @param intKouteiCd
	 *            : 工程CD
	 * @param intKouteiSeq
	 *            : 工程SEQ
	 * @param dciSakusan
	 *            : 原料酢酸
	 * @param dciUserId
	 *            : ユーザID
	 */
	public void UpdHaigoGenryoSakusan(int intKouteiCd, int intKouteiSeq,
			BigDecimal dciSakusan, BigDecimal dciUserId) throws ExceptionBase {

		try {

			// 【KPX1500671】データ変更フラグの設定タイミング変更：MOD start
			// データ変更
			// HenkouFg = true;
			// 【KPX1500671】MOD end

			Iterator ite = aryHaigou.iterator();

			// リスト件数分ループ
			while (ite.hasNext()) {

				// 配合データオブジェクト取得
				MixedData mixData = (MixedData) ite.next();

				// 工程CD・工程SEQが一致
				if (intKouteiCd == mixData.getIntKoteiCd()
						&& intKouteiSeq == mixData.getIntKoteiSeq()) {

					// 酢酸設定
					mixData.setDciSakusan(dciSakusan);

					// 更新者情報の設定
					mixData.setDciKosinId(dciUserId);
					mixData.setStrKosinHi(getSysDate());

				}
			}

		} catch (Exception e) {

			// エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("試作テーブルデータ保持の配合原料酢酸更新が失敗しました");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/**
	 * 0060.配合原料食塩更新 : 指定原料データに対して原料食塩を設定する
	 * 
	 * @param intKouteiCd
	 *            : 工程CD
	 * @param intKouteiSeq
	 *            : 工程SEQ
	 * @param dciSyokuen
	 *            : 原料食塩
	 * @param dciUserId
	 *            : ユーザID
	 */
	public void UpdHaigoGenryoSyokuen(int intKouteiCd, int intKouteiSeq,
			BigDecimal dciSyokuen, BigDecimal dciUserId) throws ExceptionBase {

		try {

			// 【KPX1500671】データ変更フラグの設定タイミング変更：MOD start
			// データ変更
			// HenkouFg = true;
			// 【KPX1500671】MOD end

			Iterator ite = aryHaigou.iterator();

			// リスト件数分ループ
			while (ite.hasNext()) {

				// 配合データオブジェクト取得
				MixedData mixData = (MixedData) ite.next();

				// 工程CD・工程SEQが一致
				if (intKouteiCd == mixData.getIntKoteiCd()
						&& intKouteiSeq == mixData.getIntKoteiSeq()) {

					// 食塩設定
					mixData.setDciShokuen(dciSyokuen);

					// 更新者情報の設定
					mixData.setDciKosinId(dciUserId);
					mixData.setStrKosinHi(getSysDate());

				}
			}

		} catch (Exception e) {

			// エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("試作テーブルデータ保持の配合原料食塩更新が失敗しました");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	// ADD start 20121002 QP@20505 No.24
	/**
	 * 0060.配合原料ＭＳＧ更新 : 指定原料データに対して原料ＭＳＧを設定する
	 * 
	 * @param intKouteiCd
	 *            : 工程CD
	 * @param intKouteiSeq
	 *            : 工程SEQ
	 * @param dciMsg
	 *            : 原料ＭＳＧ
	 * @param dciUserId
	 *            : ユーザID
	 */
	public void UpdHaigoGenryoMsg(int intKouteiCd, int intKouteiSeq,
			BigDecimal dciMsg, BigDecimal dciUserId) throws ExceptionBase {
		try {
			// 【KPX1500671】データ変更フラグの設定タイミング変更：MOD start
			// データ変更
			// HenkouFg = true;
			// 【KPX1500671】MOD end

			Iterator ite = aryHaigou.iterator();

			// リスト件数分ループ
			while (ite.hasNext()) {

				// 配合データオブジェクト取得
				MixedData mixData = (MixedData) ite.next();

				// 工程CD・工程SEQが一致
				if (intKouteiCd == mixData.getIntKoteiCd()
						&& intKouteiSeq == mixData.getIntKoteiSeq()) {

					// ＭＳＧ設定
					mixData.setDciMsg(dciMsg);

					// 更新者情報の設定
					mixData.setDciKosinId(dciUserId);
					mixData.setStrKosinHi(getSysDate());
				}
			}
		} catch (Exception e) {
			// エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("試作テーブルデータ保持の配合原料食塩更新が失敗しました");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;
		} finally {
		}
	}

	// ADD end 20121002 QP@20505 No.24
	/**
	 * 0061.配合原料総酸更新 : 指定原料データに対して原料総酸を設定する
	 * 
	 * @param intKouteiCd
	 *            : 工程CD
	 * @param intKouteiSeq
	 *            : 工程SEQ
	 * @param dciSousan
	 *            : 原料総酸
	 * @param dciUserId
	 *            : ユーザID
	 */
	public void UpdHaigoGenryoSousan(int intKouteiCd, int intKouteiSeq,
			BigDecimal dciSousan, BigDecimal dciUserId) throws ExceptionBase {

		try {

			// 【KPX1500671】データ変更フラグの設定タイミング変更：MOD start
			// データ変更
			// HenkouFg = true;
			// 【KPX1500671】MOD end

			Iterator ite = aryHaigou.iterator();

			// リスト件数分ループ
			while (ite.hasNext()) {

				// 配合データオブジェクト取得
				MixedData mixData = (MixedData) ite.next();

				// 工程CD・工程SEQが一致
				if (intKouteiCd == mixData.getIntKoteiCd()
						&& intKouteiSeq == mixData.getIntKoteiSeq()) {

					// 総酸設定
					mixData.setDciSosan(dciSousan);

					// 更新者情報の設定
					mixData.setDciKosinId(dciUserId);
					mixData.setStrKosinHi(getSysDate());

				}
			}

		} catch (Exception e) {

			// エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("試作テーブルデータ保持の配合原料総酸更新が失敗しました");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/**
	 * 0062.配合原料色更新 : 指定原料データに対して原料色を設定する
	 * 
	 * @param intKouteiCd
	 *            : 工程CD
	 * @param intKouteiSeq
	 *            : 工程SEQ
	 * @param strColor
	 *            : 原料色
	 * @param dciUserId
	 *            : ユーザID
	 */
	public void UpdHaigouGenryoColor(int intKouteiCd, int intKouteiSeq,
			String strColor, BigDecimal dciUserId) throws ExceptionBase {

		try {

			// 【KPX1500671】データ変更フラグの設定タイミング変更：MOD start
			// データ変更
			// HenkouFg = true;

			Iterator ite = aryHaigou.iterator();

			// リスト件数分ループ
			while (ite.hasNext()) {

				// 配合データオブジェクト取得
				MixedData mixData = (MixedData) ite.next();

				// 工程CD・工程SEQが一致
				if (intKouteiCd == mixData.getIntKoteiCd()
						&& intKouteiSeq == mixData.getIntKoteiSeq()) {

					// データ変更チェック
					chkHenkouData(mixData.getStrIro(), strColor);
					// 【KPX1500671】MOD end

					// 原料色設定
					mixData.setStrIro(strColor);

					// 更新者情報の設定
					mixData.setDciKosinId(dciUserId);
					mixData.setStrKosinHi(getSysDate());

				}
			}

		} catch (Exception e) {

			// エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("試作テーブルデータ保持の配合原料色更新が失敗しました");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/**
	 * 0063.試作列データ検索 : 試作列データの検索
	 * 
	 * @param intShisakuSeq
	 *            : 試作SEQ
	 * @return 配列（試作列データ）
	 */
	public ArrayList SearchShisakuRetuData(int intShisakuSeq)
			throws ExceptionBase {

		// リストインスタンス生成
		ArrayList arr = new ArrayList();

		try {

			// 引数：試作SEQが、0の場合
			if (intShisakuSeq == 0) {
				arr = aryShisakuRetu;
			} else {

				Iterator ite = aryShisakuRetu.iterator();

				// リスト件数分ループ
				while (ite.hasNext()) {
					// 試作列データオブジェクト取得
					TrialData trialData = (TrialData) ite.next();

					// 引数：試作SEQと試作列データオブジェクト：試作SEQが一致した場合
					if (intShisakuSeq == trialData.getIntShisakuSeq()) {
						// 返却リストに試作列データオブジェクト追加
						arr.add(trialData);
					}
				}
			}

		} catch (Exception e) {

			// エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("試作テーブルデータ保持の試作列データ検索が失敗しました");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

		return arr;
	}

	/**
	 * 0064.製造工程データ検索 : 製造工程データの検索
	 * 
	 * @param intShisakuSeq
	 *            : 試作SEQ
	 * @param intChuiJikouNo
	 *            : 注意事項No
	 * @return 配列（試作列データ）
	 */
	public ArrayList SearchSeizoKouteiData(int intChuiJikouNo)
			throws ExceptionBase {

		// 新規リストインスタンス生成
		ArrayList arr = new ArrayList();

		try {

			// 引数：注意事項Noが、0の場合
			if (intChuiJikouNo == 0) {
				arr = arySeizo;

			} else {

				Iterator ite = arySeizo.iterator();

				// リスト件数分ループ
				while (ite.hasNext()) {
					// 製造工程データオブジェクト取得
					ManufacturingData manufacturingData = (ManufacturingData) ite
							.next();

					// 引数：試作SEQ・注意事項Noと製造工程オブジェクト：試作SEQ・注意事項Noが一致した場合
					if (intChuiJikouNo == manufacturingData.getIntTyuiNo()) {
						// 返却リストに製造工程オブジェクト追加
						arr.add(manufacturingData);
					}
				}
			}

		} catch (Exception e) {

			// エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("試作テーブルデータ保持の製造工程データ検索が失敗しました");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

		return arr;
	}

	/**
	 * 0065.試作列選択 : 「選択試作列」を設定する
	 * 
	 * @param intShisakuSeq
	 *            : 試作SEQ
	 */
	public void SelectShisakuRetu(int intShisakuSeq) throws ExceptionBase {

		try {

			// 選択試作列と、引数:試作が一致した場合
			if (intSelectRetu == intShisakuSeq) {

				// 選択試作列初期化
				intSelectRetu = 0;

				// 選択試作列と、引数:試作SEQが一致しない場合
			} else {

				// 引数：試作SEQを選択試作列へ設定
				intSelectRetu = intShisakuSeq;

			}

		} catch (Exception e) {

			// エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("試作テーブルデータ保持の試作列選択が失敗しました");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/**
	 * 0066.試作列注意事項No更新 : 指定試作列データに対して注意事項Noを設定する
	 * 
	 * @param intShisakuSeq
	 *            : 試作SEQ
	 * @param intChuiJikouNo
	 *            : 注意事項No
	 * @param dciUserId
	 *            : ユーザID
	 */
	public void UpdChuiJikouNo(int intShisakuSeq, String strChuiJikouNo,
			BigDecimal dciUserId) throws ExceptionBase {

		try {

			// 【KPX1500671】データ変更フラグの設定タイミング変更：MOD start
			// データ変更
			// HenkouFg = true;

			Iterator ite = aryShisakuRetu.iterator();

			// リスト件数分ループ
			while (ite.hasNext()) {

				// 試作列オブジェクト取得
				TrialData trialData = (TrialData) ite.next();

				// 試作SEQが一致
				if (intShisakuSeq == trialData.getIntShisakuSeq()) {

					// データ変更チェック
					chkHenkouData(trialData.getStrTyuiNo(), strChuiJikouNo);
					// 【KPX1500671】MOD end

					// 注意事項No設定
					trialData.setStrTyuiNo(strChuiJikouNo);

					// 更新者情報の設定
					trialData.setDciKosinId(dciUserId);
					trialData.setStrkosinHi(getSysDate());

				}
			}

		} catch (Exception e) {

			// エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("試作テーブルデータ保持の試作列注意事項No更新が失敗しました");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/**
	 * 0067.試作列データ追加 : 試作列データへの試作列追加処理を行う
	 * 
	 * @return TrialData : 追加試作列データ
	 */
	public TrialData AddShisakuRetu() throws ExceptionBase {

		try {
			// ADD start 20121002 QP@20505 No.24
			String ptValue = "0";
			// 工程パターンが「空白」の場合
			String ptKotei = SearchShisakuhinData().getStrPt_kotei();
			if (ptKotei == null || ptKotei.length() == 0) {
				ptValue = "0";
			} else {
				// 工程パターンのValue1取得
				ptValue = DataCtrl.getInstance().getLiteralDataKoteiPtn()
						.selectLiteralVal1(ptKotei);
			}
			// ADD end 20121002 QP@20505 No.24

			// データ変更
			HenkouFg = true;

			// 最大試作SEQ取得
			int maxShisakuSeq = 0;
			for (int i = 0; i < aryShisakuRetu.size(); i++) {

				TrialData TrialData = (TrialData) aryShisakuRetu.get(i);

				if (maxShisakuSeq < TrialData.getIntShisakuSeq()) {
					maxShisakuSeq = TrialData.getIntShisakuSeq();
				}

			}

			// 最大試作SEQへ加算
			maxShisakuSeq++;

			// 追加用の試作列データ生成
			TrialData addTrialData = new TrialData();
			// 試作コード-社員CD
			addTrialData.setDciShisakuUser(checkNullDecimal(DataCtrl
					.getInstance().getParamData().getStrSisaku_user()));
			// 試作コード-年
			addTrialData.setDciShisakuYear(checkNullDecimal(DataCtrl
					.getInstance().getParamData().getStrSisaku_nen()));
			// 試作コード-追番
			addTrialData.setDciShisakuNum(checkNullDecimal(DataCtrl
					.getInstance().getParamData().getStrSisaku_oi()));
			// 試作SEQ
			addTrialData.setIntShisakuSeq(maxShisakuSeq);
			// 登録者ID
			addTrialData.setDciTorokuId(DataCtrl.getInstance().getUserMstData()
					.getDciUserid());
			// 登録者名
			addTrialData.setStrTorokuNm(DataCtrl.getInstance().getUserMstData()
					.getStrUsernm());
			// 登録日付
			addTrialData.setStrTorokuHi(getSysDate());

			// 出力Flgの設定
			if (aryShisakuRetu.size() != 0) {

				// １列前の試作データを取得
				TrialData trialBeforeData = (TrialData) DataCtrl.getInstance()
						.getTrialTblData()
						.SearchShisakuRetuData(maxShisakuSeq - 1).get(0);
				// 総酸
				addTrialData.setIntSosanFlg(trialBeforeData.getIntSosanFlg());
				// 食塩
				addTrialData.setIntShokuenFlg(trialBeforeData
						.getIntShokuenFlg());
				// 水相中酸度
				addTrialData.setIntSuiSandoFlg(trialBeforeData
						.getIntSuiSandoFlg());
				// 水相中食塩
				addTrialData.setIntSuiShokuenFlg(trialBeforeData
						.getIntSuiShokuenFlg());
				// 水相中酢酸
				addTrialData.setIntSuiSakusanFlg(trialBeforeData
						.getIntSuiSakusanFlg());
				// ADD start 20121002 QP@20505 No.24
				if (ptValue.equals("0") || ptValue.equals("3")) {
					// ADD end 20121002 QP@20505 No.24
					// 糖度
					addTrialData.setIntToudoFlg(trialBeforeData
							.getIntToudoFlg());
					// 粘度
					addTrialData.setIntNendoFlg(trialBeforeData
							.getIntNendoFlg());
					// 温度
					addTrialData.setIntOndoFlg(trialBeforeData.getIntOndoFlg());
					// pH
					addTrialData.setIntPhFlg(trialBeforeData.getIntPhFlg());
					// 総酸（分析）
					addTrialData.setIntSosanBunsekiFlg(trialBeforeData
							.getIntSosanBunsekiFlg());
					// 食塩（分析）
					addTrialData.setIntShokuenBunsekiFlg(trialBeforeData
							.getIntShokuenBunsekiFlg());
					// 比重
					addTrialData.setIntHizyuFlg(trialBeforeData
							.getIntHizyuFlg());
					// 水相比重
					addTrialData.setIntHiju_sui_fg(trialBeforeData
							.getIntHiju_sui_fg());
					// MOD start 20121002 QP@20505 No.24
					// //水分活性
					// addTrialData.setIntSuibunFlg(trialBeforeData.getIntSuibunFlg());
					// //アルコール
					// addTrialData.setIntArukoruFlg(trialBeforeData.getIntArukoruFlg());
					// 水分活性フリー
					addTrialData.setIntFreeSuibunKaseiFlg(trialBeforeData
							.getIntFreeSuibunKaseiFlg());
					// アルコールフリー
					addTrialData.setIntFreeAlcholFlg(trialBeforeData
							.getIntFreeAlcholFlg());
					// MOD end 20121002 QP@20505 No.24
					// 作成メモ
					addTrialData.setIntSakuseiMemoFlg(trialBeforeData
							.getIntSakuseiMemoFlg());
					// 評価
					addTrialData.setIntHyokaFlg(trialBeforeData
							.getIntHyokaFlg());
					// フリー①
					addTrialData.setIntFreeFlg(trialBeforeData.getIntFreeFlg());
					// フリー②
					addTrialData.setIntFreeFl2(trialBeforeData.getIntFreeFl2());
					// フリー③
					addTrialData.setIntFreeFl3(trialBeforeData.getIntFreeFl3());

					// ADD start 20121002 QP@20505 No.24
					// 水分活性フリータイトル
					addTrialData.setStrFreeTitleSuibunKasei(trialBeforeData
							.getStrFreeTitleSuibunKasei());
					// アルコールフリータイトル
					addTrialData.setStrFreeTitleAlchol(trialBeforeData
							.getStrFreeTitleAlchol());
					// フリー①タイトル
					addTrialData.setStrFreeTitle1(trialBeforeData
							.getStrFreeTitle1());
					// フリー②タイトル
					addTrialData.setStrFreeTitle2(trialBeforeData
							.getStrFreeTitle2());
					// フリー③タイトル
					addTrialData.setStrFreeTitle3(trialBeforeData
							.getStrFreeTitle3());
					// フリー④タイトル
					addTrialData.setStrFreeTitle4(trialBeforeData
							.getStrFreeTitle4());
					// フリー⑤タイトル
					addTrialData.setStrFreeTitle5(trialBeforeData
							.getStrFreeTitle5());
					// フリー⑥タイトル
					addTrialData.setStrFreeTitle6(trialBeforeData
							.getStrFreeTitle6());
				} else {
					// 工程パターン １液・２液 の場合
					// 実効酢酸濃度
					addTrialData.setIntJikkoSakusanNodoFlg(trialBeforeData
							.getIntJikkoSakusanNodoFlg());
					// 水相中ＭＳＧ
					addTrialData.setIntSuisoMSGFlg(trialBeforeData
							.getIntSuisoMSGFlg());
					// pH
					addTrialData.setIntPhFlg(trialBeforeData.getIntPhFlg());
					// 比重
					addTrialData.setIntHizyuFlg(trialBeforeData
							.getIntHizyuFlg());
					// 水相比重
					addTrialData.setIntHiju_sui_fg(trialBeforeData
							.getIntHiju_sui_fg());
					// 粘度フリー
					addTrialData.setIntFreeNendoFlg(trialBeforeData
							.getIntFreeNendoFlg());
					// 温度フリー
					addTrialData.setIntFreeOndoFlg(trialBeforeData
							.getIntFreeOndoFlg());
					// フリー①
					addTrialData.setIntFreeFlg(trialBeforeData.getIntFreeFlg());
					// フリー②
					addTrialData.setIntFreeFl2(trialBeforeData.getIntFreeFl2());
					// フリー③
					addTrialData.setIntFreeFl3(trialBeforeData.getIntFreeFl3());
					// フリー④
					addTrialData.setIntFreeFlg4(trialBeforeData
							.getIntFreeFlg4());
					// フリー⑤
					addTrialData.setIntFreeFlg5(trialBeforeData
							.getIntFreeFlg5());
					// フリー⑥
					addTrialData.setIntFreeFlg6(trialBeforeData
							.getIntFreeFlg6());

					// 粘度フリータイトル
					addTrialData.setStrFreeTitleNendo(trialBeforeData
							.getStrFreeTitleNendo());
					// 温度フリータイトル
					addTrialData.setStrFreeTitleOndo(trialBeforeData
							.getStrFreeTitleOndo());
					// フリー①タイトル
					addTrialData.setStrFreeTitle1(trialBeforeData
							.getStrFreeTitle1());
					// フリー②タイトル
					addTrialData.setStrFreeTitle2(trialBeforeData
							.getStrFreeTitle2());
					// フリー③タイトル
					addTrialData.setStrFreeTitle3(trialBeforeData
							.getStrFreeTitle3());
					// フリー④タイトル
					addTrialData.setStrFreeTitle4(trialBeforeData
							.getStrFreeTitle4());
					// フリー⑤タイトル
					addTrialData.setStrFreeTitle5(trialBeforeData
							.getStrFreeTitle5());
					// フリー⑥タイトル
					addTrialData.setStrFreeTitle6(trialBeforeData
							.getStrFreeTitle6());
				}
				// ADD end 20121002 QP@20505 No.24

			}

			// 試作列データ配列へ追加
			aryShisakuRetu.add(addTrialData);

			// 試作リストデータ配列へ追加
			for (int i = 0; i < aryHaigou.size(); i++) {
				MixedData MixedData = (MixedData) aryHaigou.get(i);
				// 試作リストデータ生成
				PrototypeListData addListData = new PrototypeListData();

				// 試作コード-社員CD
				addListData.setDciShisakuUser(checkNullDecimal(DataCtrl
						.getInstance().getParamData().getStrSisaku_user()));
				// 試作コード-年
				addListData.setDciShisakuYear(checkNullDecimal(DataCtrl
						.getInstance().getParamData().getStrSisaku_nen()));
				// 試作コード-追番
				addListData.setDciShisakuNum(checkNullDecimal(DataCtrl
						.getInstance().getParamData().getStrSisaku_oi()));
				// 試作SEQ
				addListData.setIntShisakuSeq(maxShisakuSeq);
				// 工程CD
				addListData.setIntKoteiCd(MixedData.getIntKoteiCd());
				// 工程SEQ
				addListData.setIntKoteiSeq(MixedData.getIntKoteiSeq());
				// 登録者ID
				addListData.setDciTorokuId(DataCtrl.getInstance()
						.getUserMstData().getDciUserid());
				// 登録者名
				addListData.setStrTorokuNm(DataCtrl.getInstance()
						.getUserMstData().getStrUsernm());
				// 登録日付
				addListData.setStrTorokuHi(getSysDate());

				// 試作リストデータ配列へ追加
				aryShisakuList.add(addListData);
			}

			return addTrialData;

		} catch (Exception e) {
			// エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("試作テーブルデータ保持の試作列データ追加が失敗しました");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}
	}

	/**
	 * 0068.試作列データ削除 : 「選択試作列」の試作列データを削除する
	 * 
	 * @param delSeq
	 *            : 削除試作SEQ
	 */
	public void DelShsiakuRetu(int delSeq) throws ExceptionBase {
		try {

			// データ変更
			HenkouFg = true;

			// 試作品データ削除（製法試作）
			if (ptdtShaisakuHin.getIntSeihoShisaku() == delSeq) {
				ptdtShaisakuHin.setIntSeihoShisaku(0);
			}

			// 試作列データ削除
			Iterator iteRetu = aryShisakuRetu.iterator();
			while (iteRetu.hasNext()) {
				// 試作列オブジェクト取得
				TrialData trialData = (TrialData) iteRetu.next();
				// 試作SEQが一致
				if (delSeq == trialData.getIntShisakuSeq()) {
					iteRetu.remove();
				}
			}

			// 試作リストデータ削除
			Iterator iteList = aryShisakuList.iterator();
			while (iteList.hasNext()) {
				// 試作列オブジェクト取得
				PrototypeListData PrototypeListData = (PrototypeListData) iteList
						.next();
				// 試作SEQが一致
				if (delSeq == PrototypeListData.getIntShisakuSeq()) {
					iteList.remove();
				}
			}

			// 原価原料データ削除
			Iterator iteGenka = aryGenka.iterator();
			while (iteGenka.hasNext()) {
				// 試作列オブジェクト取得
				CostMaterialData costMaterialData = (CostMaterialData) iteGenka
						.next();
				// 試作SEQが一致
				if (delSeq == costMaterialData.getIntShisakuSeq()) {
					iteGenka.remove();
				}
			}

		} catch (Exception e) {

			// エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("試作テーブルデータ保持の試作列データ削除が失敗しました");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/**
	 * 0068_1.試作列順設定 : 試作列順の設定を行う
	 * 
	 * @param seq
	 *            : 試作SEQ
	 * @param no
	 *            : 試作順
	 */
	public void SetRetuNo(int seq, int no) throws ExceptionBase {
		try {
			// 試作列順設定
			for (int i = 0; i < aryShisakuRetu.size(); i++) {
				TrialData TrialData = (TrialData) aryShisakuRetu.get(i);
				if (TrialData.getIntShisakuSeq() == seq) {
					TrialData.setIntHyojiNo(no);
				}
			}
		} catch (Exception e) {

			// エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("試作テーブルデータ保持の試作列順設定が失敗しました");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}
	}

	/**
	 * 0069.試作列順移動 : 「選択試作列」の試作列順の入れ替えを行う
	 * 
	 * @param strHoukouShitei
	 *            : 方向指定(上or下)
	 * @param iUserId
	 *            : ユーザID
	 */
	public void MoveShisakuRetu(String strHoukouShitei, int iUserId)
			throws ExceptionBase {

		try {

			// 未実装

		} catch (Exception e) {

			// エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("試作テーブルデータ保持の試作列順移動が失敗しました");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/**
	 * 0070.試作列日付更新 : 指定試作列データに対して試作列日付を設定する
	 * 
	 * @param intShisakuSeq
	 *            : 試作SEQ
	 * @param strDate
	 *            : 日付
	 * @param iUserId
	 *            : ユーザID
	 */
	public void UpdShisaukRetuDate(int intShisakuSeq, String strDate,
			BigDecimal dciUserId) throws ExceptionBase {

		try {

			// 【KPX1500671】データ変更フラグの設定タイミング変更：MOD start
			// データ変更
			// HenkouFg = true;

			Iterator ite = aryShisakuRetu.iterator();

			// リスト件数分ループ
			while (ite.hasNext()) {

				// 試作列オブジェクト取得
				TrialData trialData = (TrialData) ite.next();

				// 試作SEQが一致
				if (intShisakuSeq == trialData.getIntShisakuSeq()) {

					// データ変更チェック
					chkHenkouData(trialData.getStrShisakuHi(), strDate);
					// 【KPX1500671】MOD end

					// 試作日付設定
					trialData.setStrShisakuHi(strDate);

					// 更新者情報の設定
					trialData.setDciKosinId(dciUserId);
					trialData.setStrkosinHi(getSysDate());

				}
			}

		} catch (Exception e) {

			// エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("試作テーブルデータ保持の試作列日付更新が失敗しました");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/**
	 * 試作列工程仕上重量更新 : 指定試作列データに対して工程仕上重量を設定する
	 * 
	 * @param intShisakuSeq
	 *            : 試作SEQ
	 * @param dciShiagari
	 *            : 仕上重量
	 * @param iUserId
	 *            : ユーザID
	 */
	public void UpdKouteiShiagari(int intShisakuSeq, int intKoteiCode,
			BigDecimal dciKouteiShiagari, BigDecimal dciUserId)
			throws ExceptionBase {

		try {

			// 【KPX1500671】データ変更フラグの設定タイミング変更：MOD start
			// データ変更
			// HenkouFg = true;

			Iterator ite = aryShisakuList.iterator();

			// リスト件数分ループ
			while (ite.hasNext()) {

				// 試作列オブジェクト取得
				PrototypeListData prototypeListData = (PrototypeListData) ite
						.next();

				// 試作SEQが一致
				if (intShisakuSeq == prototypeListData.getIntShisakuSeq()
						&& intKoteiCode == prototypeListData.getIntKoteiCd()) {

					// データ変更チェック
					chkHenkouData(prototypeListData.getDciKouteiShiagari(),
							dciKouteiShiagari);
					// 【KPX1500671】MOD end

					// 工程仕上重量設定
					prototypeListData.setDciKouteiShiagari(dciKouteiShiagari);

					// 更新者情報の設定
					prototypeListData.setDciKosinId(dciUserId);
					prototypeListData.setStrKosinHi(getSysDate());

				}
			}

		} catch (Exception e) {

			// エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("試作テーブルデータ保持の試作列工程仕上重量更新が失敗しました");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}
	}

	/**
	 * 0070_2.試作列仕上重量更新 : 指定試作列データに対して仕上重量を設定する
	 * 
	 * @param intShisakuSeq
	 *            : 試作SEQ
	 * @param dciShiagari
	 *            : 仕上重量
	 * @param iUserId
	 *            : ユーザID
	 */
	public void UpdShiagariRetuDate(int intShisakuSeq, BigDecimal dciShiagari,
			BigDecimal dciUserId) throws ExceptionBase {

		try {

			// 【KPX1500671】データ変更フラグの設定タイミング変更：MOD start
			// データ変更
			// HenkouFg = true;

			Iterator ite = aryShisakuRetu.iterator();

			// リスト件数分ループ
			while (ite.hasNext()) {

				// 試作列オブジェクト取得
				TrialData trialData = (TrialData) ite.next();

				// 試作SEQが一致
				if (intShisakuSeq == trialData.getIntShisakuSeq()) {

					// データ変更チェック
					chkHenkouData(trialData.getDciShiagari(), dciShiagari);
					// 【KPX1500671】MOD end

					// 仕上重量設定
					trialData.setDciShiagari(dciShiagari);

					// 更新者情報の設定
					trialData.setDciKosinId(dciUserId);
					trialData.setStrkosinHi(getSysDate());

				}
			}

		} catch (Exception e) {

			// エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("試作テーブルデータ保持の試作列仕上重量更新が失敗しました");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}
	}

	/**
	 * 0070_3.試作列製法No1更新 : 指定試作列データに対して製法No-1を設定する
	 * 
	 * @param intShisakuSeq
	 *            : 試作SEQ
	 * @param strSeihoNo1
	 *            : 製法No-1
	 * @param iUserId
	 *            : ユーザID
	 */
	public void UpdRetuSeiho1(int intShisakuSeq, String strSeihoNo1,
			BigDecimal dciUserId) throws ExceptionBase {

		try {

			// データ変更
			 HenkouFg = true;

			Iterator ite = aryShisakuRetu.iterator();

			// リスト件数分ループ
			while (ite.hasNext()) {

				// 試作列オブジェクト取得
				TrialData trialData = (TrialData) ite.next();

				// 試作SEQが一致
				if (intShisakuSeq == trialData.getIntShisakuSeq()) {

					// 製法No-1設定
					trialData.setStrSeihoNo1(strSeihoNo1);

					// 更新者情報の設定
					trialData.setDciKosinId(dciUserId);
					trialData.setStrkosinHi(getSysDate());

				}
			}

		} catch (Exception e) {

			// エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("試作テーブルデータ保持の試作列製法No1更新が失敗しました");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}
	}

	/**
	 * 0070_4.試作列製法No2更新 : 指定試作列データに対して製法No-2を設定する
	 * 
	 * @param intShisakuSeq
	 *            : 試作SEQ
	 * @param strSeihoNo2
	 *            : 製法No-2
	 * @param iUserId
	 *            : ユーザID
	 */
	public void UpdRetuSeiho2(int intShisakuSeq, String strSeihoNo2,
			BigDecimal dciUserId) throws ExceptionBase {

		try {

			// データ変更
			 HenkouFg = true;

			Iterator ite = aryShisakuRetu.iterator();

			// リスト件数分ループ
			while (ite.hasNext()) {

				// 試作列オブジェクト取得
				TrialData trialData = (TrialData) ite.next();

				// 試作SEQが一致
				if (intShisakuSeq == trialData.getIntShisakuSeq()) {

					// 製法No-2設定
					trialData.setStrSeihoNo2(strSeihoNo2);

					// 更新者情報の設定
					trialData.setDciKosinId(dciUserId);
					trialData.setStrkosinHi(getSysDate());

				}
			}

		} catch (Exception e) {

			// エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("試作テーブルデータ保持の試作列製法No2更新が失敗しました");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/**
	 * 0070_5.試作列製法No3更新 : 指定試作列データに対して製法No-3を設定する
	 * 
	 * @param intShisakuSeq
	 *            : 試作SEQ
	 * @param strSeihoNo3
	 *            : 製法No-3
	 * @param iUserId
	 *            : ユーザID
	 */
	public void UpdRetuSeiho3(int intShisakuSeq, String strSeihoNo3,
			BigDecimal dciUserId) throws ExceptionBase {

		try {

			// データ変更
			 HenkouFg = true;

			Iterator ite = aryShisakuRetu.iterator();

			// リスト件数分ループ
			while (ite.hasNext()) {

				// 試作列オブジェクト取得
				TrialData trialData = (TrialData) ite.next();

				// 試作SEQが一致
				if (intShisakuSeq == trialData.getIntShisakuSeq()) {

					// 製法No-3設定
					trialData.setStrSeihoNo3(strSeihoNo3);

					// 更新者情報の設定
					trialData.setDciKosinId(dciUserId);
					trialData.setStrkosinHi(getSysDate());

				}
			}

		} catch (Exception e) {

			// エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("試作テーブルデータ保持の.試作列製法No3更新が失敗しました");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/**
	 * 0070_6.試作列製法No4更新 : 指定試作列データに対して製法No-4を設定する
	 * 
	 * @param intShisakuSeq
	 *            : 試作SEQ
	 * @param strSeihoNo4
	 *            : 製法No-4
	 * @param iUserId
	 *            : ユーザID
	 */
	public void UpdRetuSeiho4(int intShisakuSeq, String strSeihoNo4,
			BigDecimal dciUserId) throws ExceptionBase {

		try {

			// データ変更
			 HenkouFg = true;

			Iterator ite = aryShisakuRetu.iterator();

			// リスト件数分ループ
			while (ite.hasNext()) {

				// 試作列オブジェクト取得
				TrialData trialData = (TrialData) ite.next();

				// 試作SEQが一致
				if (intShisakuSeq == trialData.getIntShisakuSeq()) {

					// 製法No-4設定
					trialData.setStrSeihoNo4(strSeihoNo4);

					// 更新者情報の設定
					trialData.setDciKosinId(dciUserId);
					trialData.setStrkosinHi(getSysDate());

				}
			}

		} catch (Exception e) {

			// エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("試作テーブルデータ保持の.試作列製法No4更新が失敗しました");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/**
	 * 0070_6.試作列製法No5更新 : 指定試作列データに対して製法No-5を設定する
	 * 
	 * @param intShisakuSeq
	 *            : 試作SEQ
	 * @param strSeihoNo5
	 *            : 製法No-5
	 * @param iUserId
	 *            : ユーザID
	 */
	public void UpdRetuSeiho5(int intShisakuSeq, String strSeihoNo5,
			BigDecimal dciUserId) throws ExceptionBase {

		try {

			// データ変更
			 HenkouFg = true;

			Iterator ite = aryShisakuRetu.iterator();

			// リスト件数分ループ
			while (ite.hasNext()) {

				// 試作列オブジェクト取得
				TrialData trialData = (TrialData) ite.next();

				// 試作SEQが一致
				if (intShisakuSeq == trialData.getIntShisakuSeq()) {

					// 製法No-5設定
					trialData.setStrSeihoNo5(strSeihoNo5);

					// 更新者情報の設定
					trialData.setDciKosinId(dciUserId);
					trialData.setStrkosinHi(getSysDate());

				}
			}

		} catch (Exception e) {

			// エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("試作テーブルデータ保持の試作列製法No5更新が失敗しました");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/**
	 * 0071.試作列サンプルNo更新 : 指定試作列データに対して試作列サンプルNoを設定する
	 * 
	 * @param intShisakuSeq
	 *            : 試作SEQ
	 * @param intSampleNo
	 *            : サンプルNo
	 * @param dciUserId
	 *            : ユーザID
	 */
	public void UpdShisakuRetuSampleNo(int intShisakuSeq, String strSampleNo,
			BigDecimal dciUserId) throws ExceptionBase {

		try {

			// 【KPX1500671】データ変更フラグの設定タイミング変更：MOD start
			// データ変更
			// HenkouFg = true;

			Iterator ite = aryShisakuRetu.iterator();

			// リスト件数分ループ
			while (ite.hasNext()) {

				// 試作列オブジェクト取得
				TrialData trialData = (TrialData) ite.next();

				// 試作SEQが一致
				if (intShisakuSeq == trialData.getIntShisakuSeq()) {

					// データ変更チェック
					chkHenkouData(trialData.getStrSampleNo(), strSampleNo);
					// 【KPX1500671】MOD end

					// サンプルNo設定
					trialData.setStrSampleNo(strSampleNo);

					// 更新者情報の設定
					trialData.setDciKosinId(dciUserId);
					trialData.setStrkosinHi(getSysDate());

				}
			}

		} catch (Exception e) {

			// エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("試作テーブルデータ保持の試作列サンプルNo更新が失敗しました");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/**
	 * 0072.試作列メモ更新 : 指定試作列データに対してメモを設定する
	 * 
	 * @param intShisakuSeq
	 *            : 試作SEQ
	 * @param strMemo
	 *            : メモ
	 * @param dciUserId
	 *            : ユーザID
	 */
	public void UpdShisakuRetuMemo(int intShisakuSeq, String strMemo,
			BigDecimal dciUserId) throws ExceptionBase {

		try {

			// 【KPX1500671】データ変更フラグの設定タイミング変更：MOD start
			// データ変更
			// HenkouFg = true;

			Iterator ite = aryShisakuRetu.iterator();

			// リスト件数分ループ
			while (ite.hasNext()) {

				// 試作列オブジェクト取得
				TrialData trialData = (TrialData) ite.next();

				// 試作SEQが一致
				if (intShisakuSeq == trialData.getIntShisakuSeq()) {

					// データ変更チェック
					chkHenkouData(trialData.getStrMemo(), strMemo);
					// 【KPX1500671】MOD end

					// メモ設定
					trialData.setStrMemo(strMemo);

					// 更新者情報の設定
					trialData.setDciKosinId(dciUserId);
					trialData.setStrkosinHi(getSysDate());

				}
			}

		} catch (Exception e) {

			// エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("試作テーブルデータ保持の試作列メモ更新が失敗しました");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/**
	 * 0073.試作列印刷FG更新 : 指定試作列データに対して印刷FGを設定する
	 * 
	 * @param intShisakuSeq
	 *            : 試作SEQ
	 * @param intPrintFg
	 *            : 印刷FG
	 * @param dciUserId
	 *            : ユーザID
	 */
	public void UpdShisakuRetuPrintFg(int intShisakuSeq, int intPrintFg,
			BigDecimal dciUserId) throws ExceptionBase {

		try {

			// 【KPX1500671】データ変更フラグの設定タイミング変更：MOD start
			// データ変更
			// HenkouFg = true;

			Iterator ite = aryShisakuRetu.iterator();

			// リスト件数分ループ
			while (ite.hasNext()) {

				// 試作列オブジェクト取得
				TrialData trialData = (TrialData) ite.next();

				// 試作SEQが一致
				if (intShisakuSeq == trialData.getIntShisakuSeq()) {

					// データ変更チェック
					chkHenkouData(trialData.getIntInsatuFlg(), intPrintFg);
					// 【KPX1500671】MOD end

					// 印刷FLG設定
					trialData.setIntInsatuFlg(intPrintFg);

					// 更新者情報の設定
					trialData.setDciKosinId(dciUserId);
					trialData.setStrkosinHi(getSysDate());

				}
			}

		} catch (Exception e) {

			// エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("試作テーブルデータ保持の試作列印刷FG更新が失敗しました");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/**
	 * 0074.試作列自動計算FG更新 : 指定試作列データに対して自動計算FGを設定する
	 * 
	 * @param intJidouKeisanFg
	 *            : 自動計算印刷FG
	 * @param dciUserId
	 *            : ユーザID
	 */
	public void UpdShisakuRetuJidouKeisanFg(int intJidouKeisanFg,
			BigDecimal dciUserId) throws ExceptionBase {

		try {

			// 【KPX1500671】データ変更フラグの設定タイミング変更：MOD start
			// データ変更
			// HenkouFg = true;

			Iterator ite = aryShisakuRetu.iterator();

			// リスト件数分ループ
			while (ite.hasNext()) {

				// 試作列オブジェクト取得
				TrialData trialData = (TrialData) ite.next();

				// データ変更チェック
				chkHenkouData(trialData.getIntZidoKei(), intJidouKeisanFg);
				// 【KPX1500671】MOD end

				// 印刷FLG設定
				trialData.setIntZidoKei(intJidouKeisanFg);

				// 更新者情報の設定
				trialData.setDciKosinId(dciUserId);
				trialData.setStrkosinHi(getSysDate());

			}

		} catch (Exception e) {

			// エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("試作テーブルデータ保持の試作列自動計算FG更新が失敗しました");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/**
	 * 0075.試作列総酸出力FG更新 : 指定試作列データに対して総酸出力FGを設定する
	 * 
	 * @param intSousanFg
	 *            : 総酸出力FG
	 * @param dciUserId
	 *            : ユーザID
	 */
	public void UpdShisakuRetuSousanFg(int intSousanFg, BigDecimal dciUserId)
			throws ExceptionBase {

		try {

			// 【KPX1500671】データ変更フラグの設定タイミング変更：MOD start
			// データ変更
			// HenkouFg = true;

			Iterator ite = aryShisakuRetu.iterator();

			// リスト件数分ループ
			while (ite.hasNext()) {

				// 試作列オブジェクト取得
				TrialData trialData = (TrialData) ite.next();

				// データ変更チェック
				chkHenkouData(trialData.getIntSosanFlg(), intSousanFg);
				// 【KPX1500671】MOD end

				// 総酸出力FLG設定
				trialData.setIntSosanFlg(intSousanFg);

				// 更新者情報の設定
				trialData.setDciKosinId(dciUserId);
				trialData.setStrkosinHi(getSysDate());

			}

		} catch (Exception e) {

			// エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("試作テーブルデータ保持の試作列総酸出力FG更新が失敗しました");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/**
	 * 0076.試作列食塩出力FG更新 : 指定試作列データに対して食塩出力FGを設定する
	 * 
	 * @param intShisakuSeq
	 *            : 試作SEQ
	 * @param intSyokuenFg
	 *            : 食塩出力FG
	 * @param dciUserId
	 *            : ユーザID
	 */
	public void UpdShisakuRetuSyokuenFg(int intSyokuenFg, BigDecimal dciUserId)
			throws ExceptionBase {

		try {

			// 【KPX1500671】データ変更フラグの設定タイミング変更：MOD start
			// データ変更
			// HenkouFg = true;

			Iterator ite = aryShisakuRetu.iterator();

			// リスト件数分ループ
			while (ite.hasNext()) {

				// 試作列オブジェクト取得
				TrialData trialData = (TrialData) ite.next();

				// データ変更チェック
				chkHenkouData(trialData.getIntShokuenFlg(), intSyokuenFg);
				// 【KPX1500671】MOD end

				// 食塩出力FLG設定
				trialData.setIntShokuenFlg(intSyokuenFg);

				// 更新者情報の設定
				trialData.setDciKosinId(dciUserId);
				trialData.setStrkosinHi(getSysDate());

			}

		} catch (Exception e) {

			// エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("試作テーブルデータ保持の試作列食塩出力FG更新が失敗しました");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/**
	 * 0077.試作列水相中酸度出力FG更新 : 指定試作列データに対して水相中酸度出力FGを設定する
	 * 
	 * @param intSuisoSandoFg
	 *            : 水相中酸度出力FG
	 * @param dciUserId
	 *            : ユーザID
	 */
	public void UpdShisakuRetuSuisoSandoFg(int intSuisoSandoFg,
			BigDecimal dciUserId) throws ExceptionBase {

		try {

			// 【KPX1500671】データ変更フラグの設定タイミング変更：MOD start
			// データ変更
			// HenkouFg = true;

			Iterator ite = aryShisakuRetu.iterator();

			// リスト件数分ループ
			while (ite.hasNext()) {

				// 試作列オブジェクト取得
				TrialData trialData = (TrialData) ite.next();

				// データ変更チェック
				chkHenkouData(trialData.getIntSuiSandoFlg(), intSuisoSandoFg);
				// 【KPX1500671】MOD end

				// 水相中酸度出力FLG設定
				trialData.setIntSuiSandoFlg(intSuisoSandoFg);

				// 更新者情報の設定
				trialData.setDciKosinId(dciUserId);
				trialData.setStrkosinHi(getSysDate());

			}

		} catch (Exception e) {

			// エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("試作テーブルデータ保持の試作列水相中酸度出力FG更新が失敗しました");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/**
	 * 0078.試作列水相中食塩出力FG更新 : 指定試作列データに対して水相中食塩出力FGを設定する
	 * 
	 * @param intSuisoSyokuenFg
	 *            : 水相中食塩出力FG
	 * @param dciUserId
	 *            : ユーザID
	 */
	public void UpdShisakuRetuSuisoSyokuenFg(int intSuisoSyokuenFg,
			BigDecimal dciUserId) throws ExceptionBase {

		try {

			// 【KPX1500671】データ変更フラグの設定タイミング変更：MOD start
			// データ変更
			// HenkouFg = true;

			Iterator ite = aryShisakuRetu.iterator();

			// リスト件数分ループ
			while (ite.hasNext()) {

				// 試作列オブジェクト取得
				TrialData trialData = (TrialData) ite.next();

				// データ変更チェック
				chkHenkouData(trialData.getIntSuiShokuenFlg(),
						intSuisoSyokuenFg);
				// 【KPX1500671】MOD end

				// 水相中食塩出力FLG設定
				trialData.setIntSuiShokuenFlg(intSuisoSyokuenFg);

				// 更新者情報の設定
				trialData.setDciKosinId(dciUserId);
				trialData.setStrkosinHi(getSysDate());

			}

		} catch (Exception e) {

			// エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("試作テーブルデータ保持の試作列水相中食塩出力FG更新が失敗しました");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/**
	 * 0079.試作列水相中酢酸出力FG更新 : 指定試作列データに対して水相中酢酸出力FGを設定する
	 * 
	 * @param intSuisoSakusanFg
	 *            : 水相中酢酸出力FG
	 * @param dciUserId
	 *            : ユーザID
	 */
	public void UpdShisakuRetuSuisoSakusanFg(int intSuisoSakusanFg,
			BigDecimal dciUserId) throws ExceptionBase {

		try {

			// 【KPX1500671】データ変更フラグの設定タイミング変更：MOD start
			// データ変更
			// HenkouFg = true;

			Iterator ite = aryShisakuRetu.iterator();

			// リスト件数分ループ
			while (ite.hasNext()) {

				// 試作列オブジェクト取得
				TrialData trialData = (TrialData) ite.next();

				// データ変更チェック
				chkHenkouData(trialData.getIntSuiSakusanFlg(),
						intSuisoSakusanFg);
				// 【KPX1500671】MOD end

				// 水相中酢酸出力FLG設定
				trialData.setIntSuiSakusanFlg(intSuisoSakusanFg);

				// 更新者情報の設定
				trialData.setDciKosinId(dciUserId);
				trialData.setStrkosinHi(getSysDate());

			}

		} catch (Exception e) {

			// エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("試作テーブルデータ保持の試作列水相中酢酸出力FG更新が失敗しました");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/**
	 * 0080.試作列糖度出力FG更新 : 指定試作列データに対して糖度出力FGを設定する
	 * 
	 * @param intToudoFg
	 *            : 糖度出力FG
	 * @param dciUserId
	 *            : ユーザID
	 */
	public void UpdShisakuRetuToudoFg(int intToudoFg, BigDecimal dciUserId)
			throws ExceptionBase {

		try {

			// 【KPX1500671】データ変更フラグの設定タイミング変更：MOD start
			// データ変更
			// HenkouFg = true;

			Iterator ite = aryShisakuRetu.iterator();

			// リスト件数分ループ
			while (ite.hasNext()) {

				// 試作列オブジェクト取得
				TrialData trialData = (TrialData) ite.next();

				// データ変更チェック
				chkHenkouData(trialData.getIntToudoFlg(), intToudoFg);
				// 【KPX1500671】MOD end

				// 糖度出力FLG設定
				trialData.setIntToudoFlg(intToudoFg);

				// 更新者情報の設定
				trialData.setDciKosinId(dciUserId);
				trialData.setStrkosinHi(getSysDate());

			}

		} catch (Exception e) {

			// エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("試作テーブルデータ保持の試作列糖度出力FG更新が失敗しました");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/**
	 * 0081.試作列粘度出力FG更新 : 指定試作列データに対して粘度出力FGを設定する
	 * 
	 * @param intNendoFg
	 *            : 粘度出力FG
	 * @param dciUserId
	 *            : ユーザID
	 */
	public void UpdShisakuRetuNendoFg(int intNendoFg, BigDecimal dciUserId)
			throws ExceptionBase {

		try {

			// 【KPX1500671】データ変更フラグの設定タイミング変更：MOD start
			// データ変更
			// HenkouFg = true;

			Iterator ite = aryShisakuRetu.iterator();

			// リスト件数分ループ
			while (ite.hasNext()) {

				// 試作列オブジェクト取得
				TrialData trialData = (TrialData) ite.next();

				// データ変更チェック
				chkHenkouData(trialData.getIntNendoFlg(), intNendoFg);
				// 【KPX1500671】MOD end

				// 粘度出力FLG設定
				trialData.setIntNendoFlg(intNendoFg);

				// 更新者情報の設定
				trialData.setDciKosinId(dciUserId);
				trialData.setStrkosinHi(getSysDate());

			}

		} catch (Exception e) {

			// エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("試作テーブルデータ保持の試作列粘度出力FG更新が失敗しました");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/**
	 * 0082.試作列温度出力FG更新 : 指定試作列データに対して温度出力FGを設定する
	 * 
	 * @param intOndoFg
	 *            : 温度出力FG
	 * @param dciUserId
	 *            : ユーザID
	 */
	public void UpdShisakuRetuOndoFg(int intOndoFg, BigDecimal dciUserId)
			throws ExceptionBase {

		try {

			// 【KPX1500671】データ変更フラグの設定タイミング変更：MOD start
			// データ変更
			// HenkouFg = true;

			Iterator ite = aryShisakuRetu.iterator();

			// リスト件数分ループ
			while (ite.hasNext()) {

				// 試作列オブジェクト取得
				TrialData trialData = (TrialData) ite.next();

				// データ変更チェック
				chkHenkouData(trialData.getIntOndoFlg(), intOndoFg);
				// 【KPX1500671】MOD end

				// 温度出力FLG設定
				trialData.setIntOndoFlg(intOndoFg);

				// 更新者情報の設定
				trialData.setDciKosinId(dciUserId);
				trialData.setStrkosinHi(getSysDate());

			}

		} catch (Exception e) {

			// エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("試作テーブルデータ保持の試作列温度出力FG更新が失敗しました");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/**
	 * 0083.試作列pH出力FG更新 : 指定試作列データに対してpH出力FGを設定する
	 * 
	 * @param intPhFg
	 *            : pH出力FG
	 * @param dciUserId
	 *            : ユーザID
	 */
	public void UpdShisakuRetuPhFg(int intPhFg, BigDecimal dciUserId)
			throws ExceptionBase {

		try {

			// 【KPX1500671】データ変更フラグの設定タイミング変更：MOD start
			// データ変更
			// HenkouFg = true;

			Iterator ite = aryShisakuRetu.iterator();

			// リスト件数分ループ
			while (ite.hasNext()) {

				// 試作列オブジェクト取得
				TrialData trialData = (TrialData) ite.next();

				// データ変更チェック
				chkHenkouData(trialData.getIntPhFlg(), intPhFg);
				// 【KPX1500671】MOD end

				// pH出力FLG設定
				trialData.setIntPhFlg(intPhFg);

				// 更新者情報の設定
				trialData.setDciKosinId(dciUserId);
				trialData.setStrkosinHi(getSysDate());

			}

		} catch (Exception e) {

			// エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("試作テーブルデータ保持の試作列pH出力FG更新が失敗しました");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/**
	 * 0084.試作列総酸-分析出力FG更新 : 指定試作列データに対して総酸-分析出力FGを設定する
	 * 
	 * @param intSousanBunsekiFg
	 *            : 総酸-分析出力FG
	 * @param dciUserId
	 *            : ユーザID
	 */
	public void UpdShisakuRetuSousanBunsekiFg(int intSousanBunsekiFg,
			BigDecimal dciUserId) throws ExceptionBase {

		try {

			// 【KPX1500671】データ変更フラグの設定タイミング変更：MOD start
			// データ変更
			// HenkouFg = true;

			Iterator ite = aryShisakuRetu.iterator();

			// リスト件数分ループ
			while (ite.hasNext()) {

				// 試作列オブジェクト取得
				TrialData trialData = (TrialData) ite.next();

				// データ変更チェック
				chkHenkouData(trialData.getIntSosanBunsekiFlg(),
						intSousanBunsekiFg);
				// 【KPX1500671】MOD end

				// 総酸-分析出力FLG設定
				trialData.setIntSosanBunsekiFlg(intSousanBunsekiFg);

				// 更新者情報の設定
				trialData.setDciKosinId(dciUserId);
				trialData.setStrkosinHi(getSysDate());

			}

		} catch (Exception e) {

			// エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("試作テーブルデータ保持の試作列総酸-分析出力FG更新が失敗しました");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/**
	 * 0085.試作列食塩-分析出力FG更新 : 指定試作列データに対して食塩-分析出力FGを設定する
	 * 
	 * @param intSyokuenBunsekiFg
	 *            : 食塩-分析出力FG
	 * @param dciUserId
	 *            : ユーザID
	 */
	public void UpdShisakuRetuSyokuenBunsekiFg(int intSyokuenBunsekiFg,
			BigDecimal dciUserId) throws ExceptionBase {

		try {

			// 【KPX1500671】データ変更フラグの設定タイミング変更：MOD start
			// データ変更
			// HenkouFg = true;

			Iterator ite = aryShisakuRetu.iterator();

			// リスト件数分ループ
			while (ite.hasNext()) {

				// 試作列オブジェクト取得
				TrialData trialData = (TrialData) ite.next();

				// データ変更チェック
				chkHenkouData(trialData.getIntShokuenBunsekiFlg(),
						intSyokuenBunsekiFg);
				// 【KPX1500671】MOD end

				// 食塩-分析出力FLG設定
				trialData.setIntShokuenBunsekiFlg(intSyokuenBunsekiFg);

				// 更新者情報の設定
				trialData.setDciKosinId(dciUserId);
				trialData.setStrkosinHi(getSysDate());

			}

		} catch (Exception e) {

			// エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("試作テーブルデータ保持の試作列食塩-分析出力FG更新が失敗しました");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/**
	 * 0086.試作列比重出力FG更新 : 指定試作列データに対して比重出力FGを設定する
	 * 
	 * @param intHijuFg
	 *            : 比重出力FG
	 * @param dciUserId
	 *            : ユーザID
	 */
	public void UpdShisakuRetuHijuFg(int intHijuFg, BigDecimal dciUserId)
			throws ExceptionBase {

		try {

			// 【KPX1500671】データ変更フラグの設定タイミング変更：MOD start
			// データ変更
			// HenkouFg = true;

			Iterator ite = aryShisakuRetu.iterator();

			// リスト件数分ループ
			while (ite.hasNext()) {

				// 試作列オブジェクト取得
				TrialData trialData = (TrialData) ite.next();

				// データ変更チェック
				chkHenkouData(trialData.getIntHizyuFlg(), intHijuFg);
				// 【KPX1500671】MOD end

				// 比重出力FLG設定
				trialData.setIntHizyuFlg(intHijuFg);

				// 更新者情報の設定
				trialData.setDciKosinId(dciUserId);
				trialData.setStrkosinHi(getSysDate());

			}

		} catch (Exception e) {

			// エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("試作テーブルデータ保持の試作列比重出力FG更新が失敗しました");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/**
	 * 0087.試作列水分活性出力FG更新 : 指定試作列データに対して水分活性出力FGを設定する
	 * 
	 * @param intSuibunKaseiFg
	 *            : 水分活性出力FG
	 * @param dciUserId
	 *            : ユーザID
	 */
	public void UpdShisakuRetuSuibunKaseiFg(int intSuibunKaseiFg,
			BigDecimal dciUserId) throws ExceptionBase {

		try {

			// 【KPX1500671】データ変更フラグの設定タイミング変更：MOD start
			// データ変更
			// HenkouFg = true;

			Iterator ite = aryShisakuRetu.iterator();

			// リスト件数分ループ
			while (ite.hasNext()) {

				// 試作列オブジェクト取得
				TrialData trialData = (TrialData) ite.next();

				// データ変更チェック
				chkHenkouData(trialData.getIntSuibunFlg(), intSuibunKaseiFg);
				// 【KPX1500671】MOD end

				// 水分活性出力FLG設定
				trialData.setIntSuibunFlg(intSuibunKaseiFg);

				// 更新者情報の設定
				trialData.setDciKosinId(dciUserId);
				trialData.setStrkosinHi(getSysDate());

			}

		} catch (Exception e) {

			// エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("試作テーブルデータ保持の試作列水分活性出力FG更新が失敗しました");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/**
	 * 0088.試作列アルコール出力FG更新 : 指定試作列データに対してアルコール出力FGを設定する
	 * 
	 * @param intArukoruFg
	 *            : アルコール出力FG
	 * @param dciUserId
	 *            : ユーザID
	 */
	public void UpdShisakuRetuArukoruFg(int intArukoruFg, BigDecimal dciUserId)
			throws ExceptionBase {

		try {

			// 【KPX1500671】データ変更フラグの設定タイミング変更：MOD start
			// データ変更
			// HenkouFg = true;

			Iterator ite = aryShisakuRetu.iterator();

			// リスト件数分ループ
			while (ite.hasNext()) {

				// 試作列オブジェクト取得
				TrialData trialData = (TrialData) ite.next();

				// データ変更チェック
				chkHenkouData(trialData.getIntArukoruFlg(), intArukoruFg);
				// 【KPX1500671】MOD end

				// アルコール出力FLG設定
				trialData.setIntArukoruFlg(intArukoruFg);

				// 更新者情報の設定
				trialData.setDciKosinId(dciUserId);
				trialData.setStrkosinHi(getSysDate());

			}

		} catch (Exception e) {

			// エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("試作テーブルデータ保持の試作列アルコール出力FG更新が失敗しました");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/**
	 * 0089.試作列フリー①出力FG更新 : 指定試作列データに対してフリー①出力FGを設定する
	 * 
	 * @param intFreeFg_1
	 *            : フリー①出力FG
	 * @param dciUserId
	 *            : ユーザID
	 */
	public void UpdShisakuRetuFreeFg_1(int intFreeFg_1, BigDecimal dciUserId)
			throws ExceptionBase {

		try {

			// 【KPX1500671】データ変更フラグの設定タイミング変更：MOD start
			// データ変更
			// HenkouFg = true;

			Iterator ite = aryShisakuRetu.iterator();

			// リスト件数分ループ
			while (ite.hasNext()) {

				// 試作列オブジェクト取得
				TrialData trialData = (TrialData) ite.next();

				// データ変更チェック
				chkHenkouData(trialData.getIntFreeFlg(), intFreeFg_1);
				// 【KPX1500671】MOD end

				// フリー①出力FLG設定
				trialData.setIntFreeFlg(intFreeFg_1);

				// 更新者情報の設定
				trialData.setDciKosinId(dciUserId);
				trialData.setStrkosinHi(getSysDate());

			}

		} catch (Exception e) {

			// エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("試作テーブルデータ保持の試作列フリー①出力FG更新が失敗しました");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/**
	 * 0090.試作列フリー②出力FG更新 : 指定試作列データに対してフリー②出力FGを設定する
	 * 
	 * @param intFreeFg_2
	 *            : フリー②出力FG
	 * @param dciUserId
	 *            : ユーザID
	 */
	public void UpdShisakuRetuFreeFg_2(int intFreeFg_2, BigDecimal dciUserId)
			throws ExceptionBase {

		try {

			// 【KPX1500671】データ変更フラグの設定タイミング変更：MOD start
			// データ変更
			// HenkouFg = true;

			Iterator ite = aryShisakuRetu.iterator();

			// リスト件数分ループ
			while (ite.hasNext()) {

				// 試作列オブジェクト取得
				TrialData trialData = (TrialData) ite.next();

				// データ変更チェック
				chkHenkouData(trialData.getIntFreeFl2(), intFreeFg_2);
				// 【KPX1500671】MOD end

				// フリー②出力FLG設定
				trialData.setIntFreeFl2(intFreeFg_2);

				// 更新者情報の設定
				trialData.setDciKosinId(dciUserId);
				trialData.setStrkosinHi(getSysDate());

			}

		} catch (Exception e) {

			// エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("試作テーブルデータ保持の試作列フリー②出力FG更新が失敗しました");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/**
	 * 0091.試作列フリー③出力FG更新 : 指定試作列データに対してフリー③出力FGを設定する
	 * 
	 * @param intFreeFg_3
	 *            : フリー③出力FG
	 * @param dciUserId
	 *            : ユーザID
	 */
	public void UpdShisakuRetuFreeFg_3(int intFreeFg_3, BigDecimal dciUserId)
			throws ExceptionBase {

		try {

			// 【KPX1500671】データ変更フラグの設定タイミング変更：MOD start
			// データ変更
			// HenkouFg = true;

			Iterator ite = aryShisakuRetu.iterator();

			// リスト件数分ループ
			while (ite.hasNext()) {

				// 試作列オブジェクト取得
				TrialData trialData = (TrialData) ite.next();

				// データ変更チェック
				chkHenkouData(trialData.getIntFreeFl3(), intFreeFg_3);
				// 【KPX1500671】MOD end

				// フリー③出力FLG設定
				trialData.setIntFreeFl3(intFreeFg_3);

				// 更新者情報の設定
				trialData.setDciKosinId(dciUserId);
				trialData.setStrkosinHi(getSysDate());

			}

		} catch (Exception e) {

			// エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("試作テーブルデータ保持の試作列フリー③出力FG更新が失敗しました");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	// ADD start 20121001 QP@20505 No.24
	/**
	 * 試作列 水分活性フリー出力FG更新 : 指定試作列データに対して水分活性フリー出力FGを設定する
	 * 
	 * @param intSuibunKaseiFg
	 *            : 水分活性フリー出力FG
	 * @param dciUserId
	 *            : ユーザID
	 */
	public void UpdShisakuRetuFreeSuibunKaseiFg(int intFreeSuibunKaseiFg,
			BigDecimal dciUserId) throws ExceptionBase {
		try {
			// 【KPX1500671】データ変更フラグの設定タイミング変更：MOD start
			// データ変更
			// HenkouFg = true;

			Iterator ite = aryShisakuRetu.iterator();

			// リスト件数分ループ
			while (ite.hasNext()) {

				// 試作列オブジェクト取得
				TrialData trialData = (TrialData) ite.next();

				// データ変更チェック
				chkHenkouData(trialData.getIntFreeSuibunKaseiFlg(),
						intFreeSuibunKaseiFg);
				// 【KPX1500671】MOD end

				// 水分活性フリー出力FLG設定
				trialData.setIntFreeSuibunKaseiFlg(intFreeSuibunKaseiFg);

				// 更新者情報の設定
				trialData.setDciKosinId(dciUserId);
				trialData.setStrkosinHi(getSysDate());
			}
		} catch (Exception e) {
			// エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("試作テーブルデータ保持の試作列水分活性フリー出力FG更新が失敗しました");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;
		} finally {
		}
	}

	/**
	 * 試作列 アルコールフリー出力FG更新 : 指定試作列データに対してアルコールフリー出力FGを設定する
	 * 
	 * @param intSuibunKaseiFg
	 *            : アルコールフリー出力FG
	 * @param dciUserId
	 *            : ユーザID
	 */
	public void UpdShisakuRetuFreeAlcholFg(int intFreeAlcholFg,
			BigDecimal dciUserId) throws ExceptionBase {
		try {
			// 【KPX1500671】データ変更フラグの設定タイミング変更：MOD start
			// データ変更
			// HenkouFg = true;

			Iterator ite = aryShisakuRetu.iterator();

			// リスト件数分ループ
			while (ite.hasNext()) {

				// 試作列オブジェクト取得
				TrialData trialData = (TrialData) ite.next();

				// データ変更チェック
				chkHenkouData(trialData.getIntFreeAlcholFlg(), intFreeAlcholFg);
				// 【KPX1500671】MOD end

				// アルコールフリー出力FLG設定
				trialData.setIntFreeAlcholFlg(intFreeAlcholFg);

				// 更新者情報の設定
				trialData.setDciKosinId(dciUserId);
				trialData.setStrkosinHi(getSysDate());
			}
		} catch (Exception e) {
			// エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("試作テーブルデータ保持の試作列アルコールフリー出力FG更新が失敗しました");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;
		} finally {
		}
	}

	/**
	 * 試作列 実効酢酸濃度 出力FG更新 : 指定試作列データに対して実効酢酸濃度 出力FGを設定する
	 * 
	 * @param intSuibunKaseiFg
	 *            : 実効酢酸濃度 出力FG
	 * @param dciUserId
	 *            : ユーザID
	 */
	public void UpdShisakuRetuJikkoSakusanNodoFg(int intJSNFg,
			BigDecimal dciUserId) throws ExceptionBase {
		try {
			// 【KPX1500671】データ変更フラグの設定タイミング変更：MOD start
			// データ変更
			// HenkouFg = true;

			Iterator ite = aryShisakuRetu.iterator();

			// リスト件数分ループ
			while (ite.hasNext()) {

				// 試作列オブジェクト取得
				TrialData trialData = (TrialData) ite.next();

				// データ変更チェック
				chkHenkouData(trialData.getIntJikkoSakusanNodoFlg(), intJSNFg);
				// 【KPX1500671】MOD end

				// 実効酢酸濃度 出力FLG設定
				trialData.setIntJikkoSakusanNodoFlg(intJSNFg);

				// 更新者情報の設定
				trialData.setDciKosinId(dciUserId);
				trialData.setStrkosinHi(getSysDate());
			}
		} catch (Exception e) {
			// エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("試作テーブルデータ保持の試作列 実効酢酸濃度 出力FG更新が失敗しました");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;
		} finally {
		}
	}

	/**
	 * 試作列 水相中ＭＳＧ 出力FG更新 : 指定試作列データに対して水相中ＭＳＧ 出力FGを設定する
	 * 
	 * @param intSuibunKaseiFg
	 *            : 水相中ＭＳＧ 出力FG
	 * @param dciUserId
	 *            : ユーザID
	 */
	public void UpdShisakuRetuSuisoMSGFg(int intSuisoMSGFg, BigDecimal dciUserId)
			throws ExceptionBase {
		try {
			// 【KPX1500671】データ変更フラグの設定タイミング変更：MOD start
			// データ変更
			// HenkouFg = true;

			Iterator ite = aryShisakuRetu.iterator();

			// リスト件数分ループ
			while (ite.hasNext()) {

				// 試作列オブジェクト取得
				TrialData trialData = (TrialData) ite.next();

				// データ変更チェック
				chkHenkouData(trialData.getIntSuisoMSGFlg(), intSuisoMSGFg);
				// 【KPX1500671】MOD end

				// 水相中ＭＳＧ 出力FLG設定
				trialData.setIntSuisoMSGFlg(intSuisoMSGFg);

				// 更新者情報の設定
				trialData.setDciKosinId(dciUserId);
				trialData.setStrkosinHi(getSysDate());
			}
		} catch (Exception e) {
			// エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("試作テーブルデータ保持の試作列 水相中ＭＳＧ 出力FG更新が失敗しました");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;
		} finally {
		}
	}

	/**
	 * 試作列 粘度フリー出力FG更新 : 指定試作列データに対して粘度フリー出力FGを設定する
	 * 
	 * @param intSuibunKaseiFg
	 *            : 粘度フリー出力FG
	 * @param dciUserId
	 *            : ユーザID
	 */
	public void UpdShisakuRetuFreeNendoFg(int intFreeNendoFg,
			BigDecimal dciUserId) throws ExceptionBase {
		try {
			// 【KPX1500671】データ変更フラグの設定タイミング変更：MOD start
			// データ変更
			// HenkouFg = true;

			Iterator ite = aryShisakuRetu.iterator();

			// リスト件数分ループ
			while (ite.hasNext()) {

				// 試作列オブジェクト取得
				TrialData trialData = (TrialData) ite.next();

				// データ変更チェック
				chkHenkouData(trialData.getIntFreeNendoFlg(), intFreeNendoFg);
				// 【KPX1500671】MOD end

				// 粘度フリー出力FLG設定
				trialData.setIntFreeNendoFlg(intFreeNendoFg);

				// 更新者情報の設定
				trialData.setDciKosinId(dciUserId);
				trialData.setStrkosinHi(getSysDate());
			}
		} catch (Exception e) {
			// エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("試作テーブルデータ保持の試作列 粘度フリー 出力FG更新が失敗しました");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;
		} finally {
		}
	}

	/**
	 * 試作列 温度フリー出力FG更新 : 指定試作列データに対して温度フリー出力FGを設定する
	 * 
	 * @param intSuibunKaseiFg
	 *            : 温度フリー出力FG
	 * @param dciUserId
	 *            : ユーザID
	 */
	public void UpdShisakuRetuFreeOndoFg(int intFreeOndoFg, BigDecimal dciUserId)
			throws ExceptionBase {
		try {
			// 【KPX1500671】データ変更フラグの設定タイミング変更：MOD start
			// データ変更
			// HenkouFg = true;

			Iterator ite = aryShisakuRetu.iterator();

			// リスト件数分ループ
			while (ite.hasNext()) {

				// 試作列オブジェクト取得
				TrialData trialData = (TrialData) ite.next();

				// データ変更チェック
				chkHenkouData(trialData.getIntFreeNendoFlg(), intFreeOndoFg);
				// 【KPX1500671】MOD end

				// 温度フリー出力FLG設定
				trialData.setIntFreeNendoFlg(intFreeOndoFg);

				// 更新者情報の設定
				trialData.setDciKosinId(dciUserId);
				trialData.setStrkosinHi(getSysDate());
			}
		} catch (Exception e) {
			// エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("試作テーブルデータ保持の試作列 温度フリー 出力FG更新が失敗しました");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;
		} finally {
		}
	}

	/**
	 * 試作列 フリー④出力FG更新 : 指定試作列データに対してフリー④出力FGを設定する
	 * 
	 * @param intFreeFg_1
	 *            : フリー④出力FG
	 * @param dciUserId
	 *            : ユーザID
	 */
	public void UpdShisakuRetuFreeFg_4(int intFreeFg, BigDecimal dciUserId)
			throws ExceptionBase {
		try {
			// 【KPX1500671】データ変更フラグの設定タイミング変更：MOD start
			// データ変更
			// HenkouFg = true;

			Iterator ite = aryShisakuRetu.iterator();

			// リスト件数分ループ
			while (ite.hasNext()) {

				// 試作列オブジェクト取得
				TrialData trialData = (TrialData) ite.next();

				// データ変更チェック
				chkHenkouData(trialData.getIntFreeFlg4(), intFreeFg);
				// 【KPX1500671】MOD end

				// フリー④出力FLG設定
				trialData.setIntFreeFlg4(intFreeFg);

				// 更新者情報の設定
				trialData.setDciKosinId(dciUserId);
				trialData.setStrkosinHi(getSysDate());
			}
		} catch (Exception e) {
			// エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("試作テーブルデータ保持の試作列フリ④出力FG更新が失敗しました");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;
		} finally {
		}
	}

	/**
	 * 試作列 フリー⑤出力FG更新 : 指定試作列データに対してフリー⑤出力FGを設定する
	 * 
	 * @param intFreeFg_1
	 *            : フリー⑤出力FG
	 * @param dciUserId
	 *            : ユーザID
	 */
	public void UpdShisakuRetuFreeFg_5(int intFreeFg, BigDecimal dciUserId)
			throws ExceptionBase {
		try {
			// 【KPX1500671】データ変更フラグの設定タイミング変更：MOD start
			// データ変更
			// HenkouFg = true;

			Iterator ite = aryShisakuRetu.iterator();

			// リスト件数分ループ
			while (ite.hasNext()) {

				// 試作列オブジェクト取得
				TrialData trialData = (TrialData) ite.next();

				// データ変更チェック
				chkHenkouData(trialData.getIntFreeFlg5(), intFreeFg);
				// 【KPX1500671】MOD end

				// フリー⑤出力FLG設定
				trialData.setIntFreeFlg5(intFreeFg);

				// 更新者情報の設定
				trialData.setDciKosinId(dciUserId);
				trialData.setStrkosinHi(getSysDate());
			}
		} catch (Exception e) {
			// エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("試作テーブルデータ保持の試作列フリー⑤出力FG更新が失敗しました");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;
		} finally {
		}
	}

	/**
	 * 試作列 フリー⑥出力FG更新 : 指定試作列データに対してフリー⑥出力FGを設定する
	 * 
	 * @param intFreeFg_1
	 *            : フリー⑥出力FG
	 * @param dciUserId
	 *            : ユーザID
	 */
	public void UpdShisakuRetuFreeFg_6(int intFreeFg, BigDecimal dciUserId)
			throws ExceptionBase {
		try {
			// 【KPX1500671】データ変更フラグの設定タイミング変更：MOD start
			// データ変更
			// HenkouFg = true;

			Iterator ite = aryShisakuRetu.iterator();

			// リスト件数分ループ
			while (ite.hasNext()) {

				// 試作列オブジェクト取得
				TrialData trialData = (TrialData) ite.next();

				// データ変更チェック
				chkHenkouData(trialData.getIntFreeFlg6(), intFreeFg);
				// 【KPX1500671】MOD end

				// フリー⑥出力FLG設定
				trialData.setIntFreeFlg6(intFreeFg);

				// 更新者情報の設定
				trialData.setDciKosinId(dciUserId);
				trialData.setStrkosinHi(getSysDate());
			}
		} catch (Exception e) {
			// エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("試作テーブルデータ保持の試作列フリー⑥出力FG更新が失敗しました");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;
		} finally {
		}
	}

	// ADD end 20121001 QP@20505 No.24

	/**
	 * 0092.試作列原価試算No更新 : 指定試作列データに対して原価試算Noを設定する
	 * 
	 * @param intShisakuSeq
	 *            : 試作SEQ
	 * @param intGenkashisanNo
	 *            : 原価試算No
	 * @param dciUserId
	 *            : ユーザID
	 */
	public void UpdShisakuRetuGenkashisanNo(int intShisakuSeq,
			int intGenkashisanNo, BigDecimal dciUserId) throws ExceptionBase {

		try {

			// データ変更
			HenkouFg = true;

			Iterator ite = aryShisakuRetu.iterator();

			// リスト件数分ループ
			while (ite.hasNext()) {

				// 試作列オブジェクト取得
				TrialData trialData = (TrialData) ite.next();

				// 試作SEQが一致
				if (intShisakuSeq == trialData.getIntShisakuSeq()) {

					// 原価試算No設定
					trialData.setIntGenkaShisan(intGenkashisanNo);

					// 更新者情報の設定
					trialData.setDciKosinId(dciUserId);
					trialData.setStrkosinHi(getSysDate());

				}
			}

		} catch (Exception e) {

			// エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("試作テーブルデータ保持の試作列原価試算No更新が失敗しました");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/**
	 * 0093.試作列総酸更新 : 指定試作列データに対して総酸を設定する
	 * 
	 * @param intShisakuSeq
	 *            : 試作SEQ
	 * @param dciSousan
	 *            : 総酸
	 * @param dciUserId
	 *            : ユーザID
	 */
	public void UpdShisakuRetuSousan(int intShisakuSeq, BigDecimal dciSousan,
			BigDecimal dciUserId) throws ExceptionBase {

		try {

			// 【KPX1500671】データ変更フラグの設定タイミング変更：DEL start
			// データ変更
			// HenkouFg = true;
			// 【KPX1500671】DEL end

			Iterator ite = aryShisakuRetu.iterator();

			// リスト件数分ループ
			while (ite.hasNext()) {

				// 試作列オブジェクト取得
				TrialData trialData = (TrialData) ite.next();

				// 試作SEQが一致
				if (intShisakuSeq == trialData.getIntShisakuSeq()) {

					// 総酸設定
					trialData.setDciSosan(dciSousan);

					// 更新者情報の設定
					trialData.setDciKosinId(dciUserId);
					trialData.setStrkosinHi(getSysDate());

				}
			}

		} catch (Exception e) {

			// エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("試作テーブルデータ保持の試作列総酸更新が失敗しました");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/**
	 * 0094.試作列食塩更新 : 指定試作列データに対して食塩を設定する
	 * 
	 * @param intShisakuSeq
	 *            : 試作SEQ
	 * @param dciSyokuen
	 *            : 食塩
	 * @param dciUserId
	 *            : ユーザID
	 */
	public void UpdShisakuRetuSyokuen(int intShisakuSeq, BigDecimal dciSyokuen,
			BigDecimal dciUserId) throws ExceptionBase {

		try {

			// 【KPX1500671】データ変更フラグの設定タイミング変更：DEL start
			// データ変更
			// HenkouFg = true;
			// 【KPX1500671】DEL end

			Iterator ite = aryShisakuRetu.iterator();

			// リスト件数分ループ
			while (ite.hasNext()) {

				// 試作列オブジェクト取得
				TrialData trialData = (TrialData) ite.next();

				// 試作SEQが一致
				if (intShisakuSeq == trialData.getIntShisakuSeq()) {

					// 食塩設定
					trialData.setDciShokuen(dciSyokuen);

					// 更新者情報の設定
					trialData.setDciKosinId(dciUserId);
					trialData.setStrkosinHi(getSysDate());

				}
			}

		} catch (Exception e) {

			// エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("試作テーブルデータ保持の試作列食塩更新が失敗しました");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	// ADD start 20121002 QP@20505 No.24
	/**
	 * 0094.試作列ＭＳＧ更新 : 指定試作列データに対してＭＳＧを設定する
	 * 
	 * @param intShisakuSeq
	 *            : 試作SEQ
	 * @param dciMsg
	 *            : ＭＳＧ
	 * @param dciUserId
	 *            : ユーザID
	 */
	public void UpdShisakuRetuMsg(int intShisakuSeq, BigDecimal dciMsg,
			BigDecimal dciUserId) throws ExceptionBase {
		try {
			// 【KPX1500671】データ変更フラグの設定タイミング変更：DEL start
			// データ変更
			// HenkouFg = true;
			// 【KPX1500671】DEL end

			Iterator ite = aryShisakuRetu.iterator();

			// リスト件数分ループ
			while (ite.hasNext()) {

				// 試作列オブジェクト取得
				TrialData trialData = (TrialData) ite.next();

				// 試作SEQが一致
				if (intShisakuSeq == trialData.getIntShisakuSeq()) {

					// ＭＳＧ設定
					trialData.setDciMsg(dciMsg);

					// 更新者情報の設定
					trialData.setDciKosinId(dciUserId);
					trialData.setStrkosinHi(getSysDate());
				}
			}
		} catch (Exception e) {
			// エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("試作テーブルデータ保持の試作列ＭＳＧ更新が失敗しました");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;
		} finally {
		}
	}

	// ADD end 20121002 QP@20505 No.24

	/**
	 * 0095.試作列水相中酸度更新 : 指定試作列データに対して水相中酸度を設定する
	 * 
	 * @param intShisakuSeq
	 *            : 試作SEQ
	 * @param dciSuisoSando
	 *            : 水相中酸度
	 * @param dciUserId
	 *            : ユーザID
	 */
	public void UpdShisakuRetuSuisoSando(int intShisakuSeq,
			BigDecimal dciSuisoSando, BigDecimal dciUserId)
			throws ExceptionBase {

		try {

			// 【KPX1500671】データ変更フラグの設定タイミング変更：DEL start
			// データ変更
			// HenkouFg = true;
			// 【KPX1500671】DEL end

			Iterator ite = aryShisakuRetu.iterator();

			// リスト件数分ループ
			while (ite.hasNext()) {

				// 試作列オブジェクト取得
				TrialData trialData = (TrialData) ite.next();

				// 試作SEQが一致
				if (intShisakuSeq == trialData.getIntShisakuSeq()) {

					// 水相中酸度設定
					trialData.setDciSuiSando(dciSuisoSando);

					// 更新者情報の設定
					trialData.setDciKosinId(dciUserId);
					trialData.setStrkosinHi(getSysDate());
				}
			}

		} catch (Exception e) {

			// エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("試作テーブルデータ保持の試作列水相中酸度更新が失敗しました");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/**
	 * 0096.試作列水相中食塩更新 : 指定試作列データに対して水相中食塩を設定する
	 * 
	 * @param intShisakuSeq
	 *            : 試作SEQ
	 * @param dciSuisoSyokuen
	 *            : 水相中食塩
	 * @param dciUserId
	 *            : ユーザID
	 */
	public void UpdShisakuRetuSuisoSyokuen(int intShisakuSeq,
			BigDecimal dciSuisoSyokuen, BigDecimal dciUserId)
			throws ExceptionBase {

		try {

			// 【KPX1500671】データ変更フラグの設定タイミング変更：DEL start
			// データ変更
			// HenkouFg = true;
			// 【KPX1500671】DEL end

			Iterator ite = aryShisakuRetu.iterator();

			// リスト件数分ループ
			while (ite.hasNext()) {

				// 試作列オブジェクト取得
				TrialData trialData = (TrialData) ite.next();

				// 試作SEQが一致
				if (intShisakuSeq == trialData.getIntShisakuSeq()) {

					// 水相中食塩設定
					trialData.setDciSuiShokuen(dciSuisoSyokuen);

					// 更新者情報の設定
					trialData.setDciKosinId(dciUserId);
					trialData.setStrkosinHi(getSysDate());
				}
			}

		} catch (Exception e) {

			// エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("試作テーブルデータ保持の試作列水相中食塩更新が失敗しました");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/**
	 * 0097.試作列水相中酢酸更新 : 指定試作列データに対して水相中酢酸を設定する
	 * 
	 * @param intShisakuSeq
	 *            : 試作SEQ
	 * @param dciSuisoSakusan
	 *            : 水相中酢酸
	 * @param dciUserId
	 *            : ユーザID
	 */
	public void UpdShisakuRetuSuisoSakusan(int intShisakuSeq,
			BigDecimal dciSuisoSakusan, BigDecimal dciUserId)
			throws ExceptionBase {

		try {

			// 【KPX1500671】データ変更フラグの設定タイミング変更：DEL start
			// データ変更
			// HenkouFg = true;
			// 【KPX1500671】DEL end

			Iterator ite = aryShisakuRetu.iterator();

			// リスト件数分ループ
			while (ite.hasNext()) {

				// 試作列オブジェクト取得
				TrialData trialData = (TrialData) ite.next();

				// 試作SEQが一致
				if (intShisakuSeq == trialData.getIntShisakuSeq()) {

					// 水相中酢酸設定
					trialData.setDciSuiSakusan(dciSuisoSakusan);

					// 更新者情報の設定
					trialData.setDciKosinId(dciUserId);
					trialData.setStrkosinHi(getSysDate());

				}
			}

		} catch (Exception e) {

			// エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("試作テーブルデータ保持の試作列水相中酢酸更新が失敗しました");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/**
	 * 0098.試作列糖度更新 : 指定試作列データに対して糖度を設定する
	 * 
	 * @param intShisakuSeq
	 *            : 試作SEQ
	 * @param intToudo
	 *            : 糖度
	 * @param dciUserId
	 *            : ユーザID
	 */
	public void UpdShisakuRetuToudo(int intShisakuSeq, String strToudo,
			BigDecimal dciUserId) throws ExceptionBase {

		try {

			// 【KPX1500671】データ変更フラグの設定タイミング変更：MOD start
			// データ変更
			// HenkouFg = true;

			Iterator ite = aryShisakuRetu.iterator();

			// リスト件数分ループ
			while (ite.hasNext()) {

				// 試作列オブジェクト取得
				TrialData trialData = (TrialData) ite.next();

				// 試作SEQが一致
				if (intShisakuSeq == trialData.getIntShisakuSeq()) {

					// データ変更チェック
					chkHenkouData(trialData.getStrToudo(), strToudo);
					// 【KPX1500671】MOD end

					// 糖度設定
					trialData.setStrToudo(strToudo);

					// 更新者情報の設定
					trialData.setDciKosinId(dciUserId);
					trialData.setStrkosinHi(getSysDate());

				}
			}

		} catch (Exception e) {

			// エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("試作テーブルデータ保持の試作列糖度更新が失敗しました");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/**
	 * 0099.試作列粘度更新 : 指定試作列データに対して粘度を設定する
	 * 
	 * @param intShisakuSeq
	 *            : 試作SEQ
	 * @param intNendo
	 *            : 粘度
	 * @param dciUserId
	 *            : ユーザID
	 */
	public void UpdShisakuRetuNendo(int intShisakuSeq, String strNendo,
			BigDecimal dciUserId) throws ExceptionBase {

		try {

			// 【KPX1500671】データ変更フラグの設定タイミング変更：MOD start
			// データ変更
			// HenkouFg = true;

			Iterator ite = aryShisakuRetu.iterator();

			// リスト件数分ループ
			while (ite.hasNext()) {

				// 試作列オブジェクト取得
				TrialData trialData = (TrialData) ite.next();

				// 試作SEQが一致
				if (intShisakuSeq == trialData.getIntShisakuSeq()) {

					// データ変更チェック
					chkHenkouData(trialData.getStrNendo(), strNendo);
					// 【KPX1500671】MOD end

					// 粘度設定
					trialData.setStrNendo(strNendo);

					// 更新者情報の設定
					trialData.setDciKosinId(dciUserId);
					trialData.setStrkosinHi(getSysDate());

				}
			}

		} catch (Exception e) {

			// エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("試作テーブルデータ保持の試作列粘度更新が失敗しました");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/**
	 * 0100.試作列温度更新 : 指定試作列データに対して温度を設定する
	 * 
	 * @param intShisakuSeq
	 *            : 試作SEQ
	 * @param intOndo
	 *            : 温度
	 * @param dciUserId
	 *            : ユーザID
	 */
	public void UpdShisakuRetuOndo(int intShisakuSeq, String strOndo,
			BigDecimal dciUserId) throws ExceptionBase {

		try {

			// 【KPX1500671】データ変更フラグの設定タイミング変更：MOD start
			// データ変更
			// HenkouFg = true;

			Iterator ite = aryShisakuRetu.iterator();

			// リスト件数分ループ
			while (ite.hasNext()) {

				// 試作列オブジェクト取得
				TrialData trialData = (TrialData) ite.next();

				// 試作SEQが一致
				if (intShisakuSeq == trialData.getIntShisakuSeq()) {

					// データ変更チェック
					chkHenkouData(trialData.getStrOndo(), strOndo);
					// 【KPX1500671】MOD end

					// 温度設定
					trialData.setStrOndo(strOndo);

					// 更新者情報の設定
					trialData.setDciKosinId(dciUserId);
					trialData.setStrkosinHi(getSysDate());

				}
			}

		} catch (Exception e) {

			// エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("試作テーブルデータ保持の試作列温度更新が失敗しました");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/**
	 * 0101.試作列pH更新 : 指定試作列データに対してpHを設定する
	 * 
	 * @param intShisakuSeq
	 *            : 試作SEQ
	 * @param intPh
	 *            : pH
	 * @param dciUserId
	 *            : ユーザID
	 */
	public void UpdShisakuRetuPh(int intShisakuSeq, String strPh,
			BigDecimal dciUserId) throws ExceptionBase {

		try {

			// 【KPX1500671】データ変更フラグの設定タイミング変更：MOD start
			// データ変更
			// HenkouFg = true;

			Iterator ite = aryShisakuRetu.iterator();

			// リスト件数分ループ
			while (ite.hasNext()) {

				// 試作列オブジェクト取得
				TrialData trialData = (TrialData) ite.next();

				// 試作SEQが一致
				if (intShisakuSeq == trialData.getIntShisakuSeq()) {

					// データ変更チェック
					chkHenkouData(trialData.getStrPh(), strPh);
					// 【KPX1500671】MOD end

					// pH設定
					trialData.setStrPh(strPh);

					// 更新者情報の設定
					trialData.setDciKosinId(dciUserId);
					trialData.setStrkosinHi(getSysDate());

				}
			}

		} catch (Exception e) {

			// エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("試作テーブルデータ保持の試作列pH更新が失敗しました");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/**
	 * 0102.試作列総酸-分析更新 : 指定試作列データに対して総酸-分析を設定する
	 * 
	 * @param intShisakuSeq
	 *            : 試作SEQ
	 * @param intSousanBunseki
	 *            : 総酸-分析
	 * @param dciUserId
	 *            : ユーザID
	 */
	public void UpdShisakuRetuSousanBunseki(int intShisakuSeq,
			String strSousanBunseki, BigDecimal dciUserId) throws ExceptionBase {

		try {

			// 【KPX1500671】データ変更フラグの設定タイミング変更：MOD start
			// データ変更
			// HenkouFg = true;

			Iterator ite = aryShisakuRetu.iterator();

			// リスト件数分ループ
			while (ite.hasNext()) {

				// 試作列オブジェクト取得
				TrialData trialData = (TrialData) ite.next();

				// 試作SEQが一致
				if (intShisakuSeq == trialData.getIntShisakuSeq()) {

					// データ変更チェック
					chkHenkouData(trialData.getStrSosanBunseki(),
							strSousanBunseki);
					// 【KPX1500671】MOD end

					// 総酸-分析設定
					trialData.setStrSosanBunseki(strSousanBunseki);

					// 更新者情報の設定
					trialData.setDciKosinId(dciUserId);
					trialData.setStrkosinHi(getSysDate());

				}
			}

		} catch (Exception e) {

			ex.setStrErrmsg("");
			ex.setStrErrShori("");
			ex.setStrMsgNo("");
			ex.setStrErrCd("");
			ex.setStrSystemMsg("");

			throw ex;

		} finally {

		}

	}

	/**
	 * 0103.試作列食塩-分析更新 : 指定試作列データに対して総酸-食塩を設定する
	 * 
	 * @param intShisakuSeq
	 *            : 試作SEQ
	 * @param intSyokuenBunseki
	 *            : 食塩-分析
	 * @param dciUserId
	 *            : ユーザID
	 */
	public void UpdShisakuRetuSyokuenBunseki(int intShisakuSeq,
			String strSyokuenBunseki, BigDecimal dciUserId)
			throws ExceptionBase {

		try {

			// 【KPX1500671】データ変更フラグの設定タイミング変更：MOD start
			// データ変更
			// HenkouFg = true;

			Iterator ite = aryShisakuRetu.iterator();

			// リスト件数分ループ
			while (ite.hasNext()) {

				// 試作列オブジェクト取得
				TrialData trialData = (TrialData) ite.next();

				// 試作SEQが一致
				if (intShisakuSeq == trialData.getIntShisakuSeq()) {

					// データ変更チェック
					chkHenkouData(trialData.getStrShokuenBunseki(),
							strSyokuenBunseki);
					// 【KPX1500671】MOD end

					// 食塩-分析設定
					trialData.setStrShokuenBunseki(strSyokuenBunseki);

					// 更新者情報の設定
					trialData.setDciKosinId(dciUserId);
					trialData.setStrkosinHi(getSysDate());

				}
			}

		} catch (Exception e) {

			// エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("試作テーブルデータ保持の試作列食塩-分析更新が失敗しました");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/**
	 * 0104.試作列比重更新 : 指定試作列データに対して比重を設定する
	 * 
	 * @param intShisakuSeq
	 *            : 試作SEQ
	 * @param intHiju
	 *            : 比重
	 * @param dciUserId
	 *            : ユーザID
	 */
	public void UpdShisakuRetuHiju(int intShisakuSeq, String strHiju,
			BigDecimal dciUserId) throws ExceptionBase {

		try {

			// 【KPX1500671】データ変更フラグの設定タイミング変更：MOD start
			// データ変更
			// HenkouFg = true;

			Iterator ite = aryShisakuRetu.iterator();

			// リスト件数分ループ
			while (ite.hasNext()) {

				// 試作列オブジェクト取得
				TrialData trialData = (TrialData) ite.next();

				// 試作SEQが一致
				if (intShisakuSeq == trialData.getIntShisakuSeq()) {

					// 工程パターン取得
					String ptKotei = DataCtrl.getInstance().getTrialTblData()
							.SearchShisakuhinData().getStrPt_kotei();
					// 工程パターンのValue1取得
					String ptValue = null;
					if (ptKotei == null || ptKotei.length() == 0) {
					} else {
						// 工程パターンのValue1取得
						ptValue = DataCtrl.getInstance()
								.getLiteralDataKoteiPtn()
								.selectLiteralVal1(ptKotei);
					}
					String yoryoTani = DataCtrl.getInstance().getTrialTblData()
							.SearchShisakuhinData().getStrTani();
					String taniValue1 = "";
					if (yoryoTani == null || yoryoTani.length() == 0) {
					} else {
						taniValue1 = DataCtrl.getInstance()
								.getLiteralDataTani()
								.selectLiteralVal1(yoryoTani);
					}
					// 1液 かつ 容量が「ml」の場合
					if (JwsConstManager.JWS_KOTEITYPE_1.equals(ptValue)
							&& "1".equals(taniValue1)) {
						// データ変更チェック
						chkHenkouData(trialData.getStrHizyu(), strHiju);
					}
					// 【KPX1500671】MOD end

					// 比重設定
					trialData.setStrHizyu(strHiju);

					// 更新者情報の設定
					trialData.setDciKosinId(dciUserId);
					trialData.setStrkosinHi(getSysDate());

				}
			}

		} catch (Exception e) {

			// エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("試作テーブルデータ保持の試作列比重更新が失敗しました");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/**
	 * 0105.試作列水分活性更新 : 指定試作列データに対して水分活性を設定する
	 * 
	 * @param intShisakuSeq
	 *            : 試作SEQ
	 * @param intSuibunKasei
	 *            : 水分活性
	 * @param dciUserId
	 *            : ユーザID
	 */
	public void UpdShisakuRetuSuibunKasei(int intShisakuSeq,
			String strSuibunKasei, BigDecimal dciUserId) throws ExceptionBase {

		try {

			// データ変更
			HenkouFg = true;

			Iterator ite = aryShisakuRetu.iterator();

			// リスト件数分ループ
			while (ite.hasNext()) {

				// 試作列オブジェクト取得
				TrialData trialData = (TrialData) ite.next();

				// 試作SEQが一致
				if (intShisakuSeq == trialData.getIntShisakuSeq()) {

					// 水分活性設定
					trialData.setStrSuibun(strSuibunKasei);

					// 更新者情報の設定
					trialData.setDciKosinId(dciUserId);
					trialData.setStrkosinHi(getSysDate());

				}
			}

		} catch (Exception e) {

			// エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("試作テーブルデータ保持の試作列水分活性更新が失敗しました");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/**
	 * 0106.試作列アルコール更新 : 指定試作列データに対してアルコールを設定する
	 * 
	 * @param intShisakuSeq
	 *            : 試作SEQ
	 * @param intArukoru
	 *            : アルコール
	 * @param dciUserId
	 *            : ユーザID
	 */
	public void UpdShisakuRetuArukoru(int intShisakuSeq, String strArukoru,
			BigDecimal dciUserId) throws ExceptionBase {

		try {

			// 【KPX1500671】データ変更フラグの設定タイミング変更：DEL start
			// データ変更
			// HenkouFg = true;
			// 【KPX1500671】DEL end

			Iterator ite = aryShisakuRetu.iterator();

			// リスト件数分ループ
			while (ite.hasNext()) {

				// 試作列オブジェクト取得
				TrialData trialData = (TrialData) ite.next();

				// 試作SEQが一致
				if (intShisakuSeq == trialData.getIntShisakuSeq()) {

					// アルコール設定
					trialData.setStrArukoru(strArukoru);

					// 更新者情報の設定
					trialData.setDciKosinId(dciUserId);
					trialData.setStrkosinHi(getSysDate());

				}
			}

		} catch (Exception e) {

			// エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("試作テーブルデータ保持の試作列アルコール更新が失敗しました");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/**
	 * 0107.試作列フリータイトル①更新 : 全試作列データに対してフリータイトル①を設定する
	 * 
	 * @param strFreeTitle_1
	 *            : フリータイトル①
	 * @param dciUserId
	 *            : ユーザID
	 */
	public void UpdShisakuRetuFreeTitle_1(String strFreeTitle_1,
			BigDecimal dciUserId) throws ExceptionBase {

		try {

			// 【KPX1500671】データ変更フラグの設定タイミング変更：MOD start
			// データ変更
			// HenkouFg = true;

			Iterator ite = aryShisakuRetu.iterator();

			// リスト件数分ループ
			while (ite.hasNext()) {

				// 試作列オブジェクト取得
				TrialData trialData = (TrialData) ite.next();

				// データ変更チェック
				chkHenkouData(trialData.getStrFreeTitle1(), strFreeTitle_1);
				// 【KPX1500671】MOD end

				// フリータイトル①設定
				trialData.setStrFreeTitle1(strFreeTitle_1);

				// 更新者情報の設定
				trialData.setDciKosinId(dciUserId);
				trialData.setStrkosinHi(getSysDate());

			}

		} catch (Exception e) {

			// エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("試作テーブルデータ保持の試作列フリータイトル①更新が失敗しました");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/**
	 * 0108.試作列フリー内容①更新 : 指定試作列データに対してフリー内容①を設定する
	 * 
	 * @param strFreeNaiyou_1
	 *            : フリー内容①
	 * @param dciUserId
	 *            : ユーザID
	 */
	public void UpdShisakuRetuFreeNaiyou_1(int intShisakuSeq,
			String strFreeNaiyou_1, BigDecimal dciUserId) throws ExceptionBase {

		try {

			// 【KPX1500671】データ変更フラグの設定タイミング変更：MOD start
			// データ変更
			// HenkouFg = true;

			Iterator ite = aryShisakuRetu.iterator();

			// リスト件数分ループ
			while (ite.hasNext()) {

				// 試作列オブジェクト取得
				TrialData trialData = (TrialData) ite.next();

				// 試作SEQが一致
				if (intShisakuSeq == trialData.getIntShisakuSeq()) {

					// データ変更チェック
					chkHenkouData(trialData.getStrFreeNaiyo1(), strFreeNaiyou_1);
					// 【KPX1500671】MOD end

					// フリー内容①設定
					trialData.setStrFreeNaiyo1(strFreeNaiyou_1);

					// 更新者情報の設定
					trialData.setDciKosinId(dciUserId);
					trialData.setStrkosinHi(getSysDate());

				}
			}

		} catch (Exception e) {

			// エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("試作テーブルデータ保持の試作列フリー内容①更新が失敗しました");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/**
	 * 0109.試作列フリータイトル②更新 : 全試作列データに対してフリータイトル②を設定する
	 * 
	 * @param strFreeTitle_2
	 *            : フリータイトル②
	 * @param dciUserId
	 *            : ユーザID
	 */
	public void UpdShisakuRetuFreeTitle_2(String strFreeTitle_2,
			BigDecimal dciUserId) throws ExceptionBase {

		try {

			// 【KPX1500671】データ変更フラグの設定タイミング変更：MOD start
			// データ変更
			// HenkouFg = true;

			Iterator ite = aryShisakuRetu.iterator();

			// リスト件数分ループ
			while (ite.hasNext()) {

				// 試作列オブジェクト取得
				TrialData trialData = (TrialData) ite.next();

				// データ変更チェック
				chkHenkouData(trialData.getStrFreeTitle2(), strFreeTitle_2);
				// 【KPX1500671】MOD end

				// フリータイトル②設定
				trialData.setStrFreeTitle2(strFreeTitle_2);

				// 更新者情報の設定
				trialData.setDciKosinId(dciUserId);
				trialData.setStrkosinHi(getSysDate());

			}

		} catch (Exception e) {

			// エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("試作テーブルデータ保持の試作列フリータイトル②更新が失敗しました");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/**
	 * 0110.試作列フリー内容②更新 : 指定試作列データに対してフリー内容②を設定する
	 * 
	 * @param strFreeNaiyou_2
	 *            : フリー内容②
	 * @param dciUserId
	 *            : ユーザID
	 */
	public void UpdShisakuRetuFreeNaiyou_2(int intShisakuSeq,
			String strFreeNaiyou_2, BigDecimal dciUserId) throws ExceptionBase {

		try {

			// 【KPX1500671】データ変更フラグの設定タイミング変更：MOD start
			// データ変更
			// HenkouFg = true;

			Iterator ite = aryShisakuRetu.iterator();

			// リスト件数分ループ
			while (ite.hasNext()) {

				// 試作列オブジェクト取得
				TrialData trialData = (TrialData) ite.next();

				// 試作SEQが一致
				if (intShisakuSeq == trialData.getIntShisakuSeq()) {

					// データ変更チェック
					chkHenkouData(trialData.getStrFreeNaiyo2(), strFreeNaiyou_2);
					// 【KPX1500671】MOD end

					// フリー内容②設定
					trialData.setStrFreeNaiyo2(strFreeNaiyou_2);

					// 更新者情報の設定
					trialData.setDciKosinId(dciUserId);
					trialData.setStrkosinHi(getSysDate());

				}
			}

		} catch (Exception e) {

			// エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("試作テーブルデータ保持の試作列フリー内容②更新が失敗しました");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/**
	 * 0111.試作列フリータイトル③更新 : 全試作列データに対してフリータイトル③を設定する
	 * 
	 * @param strFreeTitle_3
	 *            : フリータイトル③
	 * @param dciUserId
	 *            : ユーザID
	 */
	public void UpdShisakuRetuFreeTitle_3(String strFreeTitle_3,
			BigDecimal dciUserId) throws ExceptionBase {

		try {

			// 【KPX1500671】データ変更フラグの設定タイミング変更：MOD start
			// データ変更
			// HenkouFg = true;

			Iterator ite = aryShisakuRetu.iterator();

			// リスト件数分ループ
			while (ite.hasNext()) {

				// 試作列オブジェクト取得
				TrialData trialData = (TrialData) ite.next();

				// データ変更チェック
				chkHenkouData(trialData.getStrFreeTitle3(), strFreeTitle_3);
				// 【KPX1500671】MOD end

				// フリータイトル③設定
				trialData.setStrFreeTitle3(strFreeTitle_3);

				// 更新者情報の設定
				trialData.setDciKosinId(dciUserId);
				trialData.setStrkosinHi(getSysDate());

			}

		} catch (Exception e) {

			// エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("試作テーブルデータ保持の試作列フリータイトル③更新が失敗しました");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/**
	 * 0112.試作列フリー内容③更新 : 指定試作列データに対してフリー内容③を設定する
	 * 
	 * @param strFreeNaiyou_3
	 *            : フリー内容③
	 * @param dciUserId
	 *            : ユーザID
	 */
	public void UpdShisakuRetuFreeNaiyou_3(int intShisakuSeq,
			String strFreeNaiyou_3, BigDecimal dciUserId) throws ExceptionBase {

		try {

			// 【KPX1500671】データ変更フラグの設定タイミング変更：MOD start
			// データ変更
			// HenkouFg = true;

			Iterator ite = aryShisakuRetu.iterator();

			// リスト件数分ループ
			while (ite.hasNext()) {

				// 試作列オブジェクト取得
				TrialData trialData = (TrialData) ite.next();

				// 試作SEQが一致
				if (intShisakuSeq == trialData.getIntShisakuSeq()) {

					// データ変更チェック
					chkHenkouData(trialData.getStrFreeNaiyo3(), strFreeNaiyou_3);
					// 【KPX1500671】MOD end

					// フリー内容③設定
					trialData.setStrFreeNaiyo3(strFreeNaiyou_3);

					// 更新者情報の設定
					trialData.setDciKosinId(dciUserId);
					trialData.setStrkosinHi(getSysDate());

				}
			}

		} catch (Exception e) {

			// エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("試作テーブルデータ保持の試作列フリー内容③更新が失敗しました");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	// ADD start 20120928 QP@20505 No.24
	/**
	 * 試作列 水分活性リータイトル更新 : 全試作列データに対して水分活性フリータイトル内容の値を設定する
	 * 
	 * @param strFreeTitle_Ondo
	 *            : 水分活性フリータイトル
	 * @param dciUserId
	 *            : ユーザID
	 */
	public void UpdShisakuRetuFreeTitle_SuibunKasei(
			String strFreeTitle_SuibunKasei, BigDecimal dciUserId)
			throws ExceptionBase {
		try {
			// 【KPX1500671】データ変更フラグの設定タイミング変更：MOD start
			// データ変更
			// HenkouFg = true;

			Iterator ite = aryShisakuRetu.iterator();

			// リスト件数分ループ
			while (ite.hasNext()) {

				// 試作列オブジェクト取得
				TrialData trialData = (TrialData) ite.next();

				// データ変更チェック
				chkHenkouData(trialData.getStrFreeTitleSuibunKasei(),
						strFreeTitle_SuibunKasei);
				// 【KPX1500671】MOD end

				// 水分活性フリータイトル設定
				trialData.setStrFreeTitleSuibunKasei(strFreeTitle_SuibunKasei);

				// 更新者情報の設定
				trialData.setDciKosinId(dciUserId);
				trialData.setStrkosinHi(getSysDate());
			}
		} catch (Exception e) {
			// エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("試作テーブルデータ保持の試作列 水分活性フリータイトル 更新が失敗しました");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;
		} finally {
		}
	}

	/**
	 * 試作列 水分活性フリー内容 更新 : 指定試作列データに対して水分活性フリー内容を設定する
	 * 
	 * @param strFreeOndo
	 *            : 水分活性フリー内容
	 * @param dciUserId
	 *            : ユーザID
	 */
	public void UpdShisakuRetuFreeSuibunKasei(int intShisakuSeq,
			String strFreeSuibunKasei, BigDecimal dciUserId)
			throws ExceptionBase {
		try {
			// 【KPX1500671】データ変更フラグの設定タイミング変更：MOD start
			// データ変更
			// HenkouFg = true;

			Iterator ite = aryShisakuRetu.iterator();

			// リスト件数分ループ
			while (ite.hasNext()) {

				// 試作列オブジェクト取得
				TrialData trialData = (TrialData) ite.next();

				// 試作SEQが一致
				if (intShisakuSeq == trialData.getIntShisakuSeq()) {

					// データ変更チェック
					chkHenkouData(trialData.getStrFreeSuibunKasei(),
							strFreeSuibunKasei);
					// 【KPX1500671】MOD end

					// 水分活性フリー内容 設定
					trialData.setStrFreeSuibunKasei(strFreeSuibunKasei);

					// 更新者情報の設定
					trialData.setDciKosinId(dciUserId);
					trialData.setStrkosinHi(getSysDate());
				}
			}
		} catch (Exception e) {
			// エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("試作テーブルデータ保持の試作列 水分活性フリー内容 更新が失敗しました");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;
		} finally {
		}
	}

	/**
	 * 試作列 アルコールリータイトル更新 : 全試作列データに対してアルコールフリータイトル内容の値を設定する
	 * 
	 * @param strFreeTitle_Ondo
	 *            : アルコールフリータイトル
	 * @param dciUserId
	 *            : ユーザID
	 */
	public void UpdShisakuRetuFreeTitle_Alchol(String strFreeTitle_Alchol,
			BigDecimal dciUserId) throws ExceptionBase {
		try {
			// 【KPX1500671】データ変更フラグの設定タイミング変更：MOD start
			// データ変更
			// HenkouFg = true;

			Iterator ite = aryShisakuRetu.iterator();

			// リスト件数分ループ
			while (ite.hasNext()) {

				// 試作列オブジェクト取得
				TrialData trialData = (TrialData) ite.next();

				// データ変更チェック
				chkHenkouData(trialData.getStrFreeTitleAlchol(),
						strFreeTitle_Alchol);
				// 【KPX1500671】MOD end

				// アルコールフリータイトル設定
				trialData.setStrFreeTitleAlchol(strFreeTitle_Alchol);

				// 更新者情報の設定
				trialData.setDciKosinId(dciUserId);
				trialData.setStrkosinHi(getSysDate());
			}
		} catch (Exception e) {
			// エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("試作テーブルデータ保持の試作列 アルコールフリータイトル 更新が失敗しました");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;
		} finally {
		}
	}

	/**
	 * 試作列 アルコールフリー内容 更新 : 指定試作列データに対してアルコールフリー内容を設定する
	 * 
	 * @param strFreeOndo
	 *            : アルコールフリー内容
	 * @param dciUserId
	 *            : ユーザID
	 */
	public void UpdShisakuRetuFreeAlchol(int intShisakuSeq,
			String strFreeAlchol, BigDecimal dciUserId) throws ExceptionBase {
		try {
			// 【KPX1500671】データ変更フラグの設定タイミング変更：MOD start
			// データ変更
			// HenkouFg = true;

			Iterator ite = aryShisakuRetu.iterator();

			// リスト件数分ループ
			while (ite.hasNext()) {

				// 試作列オブジェクト取得
				TrialData trialData = (TrialData) ite.next();

				// 試作SEQが一致
				if (intShisakuSeq == trialData.getIntShisakuSeq()) {

					// データ変更チェック
					chkHenkouData(trialData.getStrFreeAlchol(), strFreeAlchol);
					// 【KPX1500671】MOD end

					// アルコールフリー内容 設定
					trialData.setStrFreeAlchol(strFreeAlchol);

					// 更新者情報の設定
					trialData.setDciKosinId(dciUserId);
					trialData.setStrkosinHi(getSysDate());
				}
			}
		} catch (Exception e) {
			// エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("試作テーブルデータ保持の試作列 アルコールフリー内容 更新が失敗しました");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;
		} finally {
		}
	}

	/**
	 * 試作列 実効酢酸濃度 更新 : 指定試作列データに対して実効酢酸濃度を設定する
	 * 
	 * @param strFreeOndo
	 *            : 実効酢酸濃度
	 * @param dciUserId
	 *            : ユーザID
	 */
	public void UpdShisakuRetuJikkoSakusanNodo(int intShisakuSeq,
			BigDecimal dciJsn, BigDecimal dciUserId) throws ExceptionBase {
		try {
			// 【KPX1500671】データ変更フラグの設定タイミング変更：DEL start
			// データ変更
			// HenkouFg = true;
			// 【KPX1500671】DEL end

			Iterator ite = aryShisakuRetu.iterator();

			// リスト件数分ループ
			while (ite.hasNext()) {

				// 試作列オブジェクト取得
				TrialData trialData = (TrialData) ite.next();

				// 試作SEQが一致
				if (intShisakuSeq == trialData.getIntShisakuSeq()) {

					// 実効酢酸濃度（％）設定
					trialData.setDciJikkoSakusanNodo(dciJsn);

					// 更新者情報の設定
					trialData.setDciKosinId(dciUserId);
					trialData.setStrkosinHi(getSysDate());
				}
			}
		} catch (Exception e) {
			// エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("試作テーブルデータ保持の試作列 実効酢酸濃度 更新が失敗しました");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;
		} finally {
		}
	}

	/**
	 * 試作列 水相中ＭＳＧ 更新 : 指定試作列データに対して水相中ＭＳＧを設定する
	 * 
	 * @param strFreeOndo
	 *            : 水相中ＭＳＧ
	 * @param dciUserId
	 *            : ユーザID
	 */
	public void UpdShisakuRetuSuisoMSG(int intShisakuSeq,
			BigDecimal dciSuisoMsg, BigDecimal dciUserId) throws ExceptionBase {
		try {
			// 【KPX1500671】データ変更フラグの設定タイミング変更：DEL start
			// データ変更
			// HenkouFg = true;
			// 【KPX1500671】DEL end

			Iterator ite = aryShisakuRetu.iterator();

			// リスト件数分ループ
			while (ite.hasNext()) {

				// 試作列オブジェクト取得
				TrialData trialData = (TrialData) ite.next();

				// 試作SEQが一致
				if (intShisakuSeq == trialData.getIntShisakuSeq()) {

					// 水相中ＭＳＧ設定
					trialData.setDciSuisoMSG(dciSuisoMsg);

					// 更新者情報の設定
					trialData.setDciKosinId(dciUserId);
					trialData.setStrkosinHi(getSysDate());
				}
			}
		} catch (Exception e) {
			// エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("試作テーブルデータ保持の試作列 水相中ＭＳＧ 更新が失敗しました");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;
		} finally {
		}
	}

	/**
	 * 試作列 粘度フリータイトル更新 : 全試作列データに対して粘度フリータイトル内容の値を設定する
	 * 
	 * @param strFreeTitle_Ondo
	 *            : 粘度フリータイトル
	 * @param dciUserId
	 *            : ユーザID
	 */
	public void UpdShisakuRetuFreeTitle_Nendo(String strFreeTitle_Nendo,
			BigDecimal dciUserId) throws ExceptionBase {
		try {
			// 【KPX1500671】データ変更フラグの設定タイミング変更：MOD start
			// データ変更
			// HenkouFg = true;

			Iterator ite = aryShisakuRetu.iterator();

			// リスト件数分ループ
			while (ite.hasNext()) {

				// 試作列オブジェクト取得
				TrialData trialData = (TrialData) ite.next();

				// データ変更チェック
				chkHenkouData(trialData.getStrFreeTitleNendo(),
						strFreeTitle_Nendo);
				// 【KPX1500671】MOD end

				// 温度フリータイトル設定
				trialData.setStrFreeTitleNendo(strFreeTitle_Nendo);

				// 更新者情報の設定
				trialData.setDciKosinId(dciUserId);
				trialData.setStrkosinHi(getSysDate());
			}
		} catch (Exception e) {
			// エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("試作テーブルデータ保持の試作列 粘度フリータイトル 更新が失敗しました");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;
		} finally {
		}
	}

	/**
	 * 試作列 粘度フリー内容 更新 : 指定試作列データに対して粘度フリー内容を設定する
	 * 
	 * @param strFreeOndo
	 *            : 粘度フリー内容
	 * @param dciUserId
	 *            : ユーザID
	 */
	public void UpdShisakuRetuFreeNendo(int intShisakuSeq, String strFreeNendo,
			BigDecimal dciUserId) throws ExceptionBase {
		try {
			// 【KPX1500671】データ変更フラグの設定タイミング変更：MOD start
			// データ変更
			// HenkouFg = true;

			Iterator ite = aryShisakuRetu.iterator();

			// リスト件数分ループ
			while (ite.hasNext()) {

				// 試作列オブジェクト取得
				TrialData trialData = (TrialData) ite.next();

				// 試作SEQが一致
				if (intShisakuSeq == trialData.getIntShisakuSeq()) {

					// データ変更チェック
					chkHenkouData(trialData.getStrFreeNendo(), strFreeNendo);
					// 【KPX1500671】MOD end

					// 粘度フリー内容設定
					trialData.setStrFreeNendo(strFreeNendo);

					// 更新者情報の設定
					trialData.setDciKosinId(dciUserId);
					trialData.setStrkosinHi(getSysDate());
				}
			}
		} catch (Exception e) {
			// エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("試作テーブルデータ保持の試作列 粘度フリー項目 更新が失敗しました");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;
		} finally {
		}
	}

	/**
	 * 試作列 温度フリータイトル更新 : 全試作列データに対して温度フリータイトル内容の値を設定する
	 * 
	 * @param strFreeTitle_Ondo
	 *            : 温度フリータイトル
	 * @param dciUserId
	 *            : ユーザID
	 */
	public void UpdShisakuRetuFreeTitle_Ondo(String strFreeTitle_Ondo,
			BigDecimal dciUserId) throws ExceptionBase {
		try {
			// 【KPX1500671】データ変更フラグの設定タイミング変更：MOD start
			// データ変更
			// HenkouFg = true;

			Iterator ite = aryShisakuRetu.iterator();

			// リスト件数分ループ
			while (ite.hasNext()) {

				// 試作列オブジェクト取得
				TrialData trialData = (TrialData) ite.next();

				// データ変更チェック
				chkHenkouData(trialData.getStrFreeTitleOndo(),
						strFreeTitle_Ondo);
				// 【KPX1500671】MOD end

				// 温度フリータイトル設定
				trialData.setStrFreeTitleOndo(strFreeTitle_Ondo);

				// 更新者情報の設定
				trialData.setDciKosinId(dciUserId);
				trialData.setStrkosinHi(getSysDate());
			}
		} catch (Exception e) {
			// エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("試作テーブルデータ保持の試作列 温度フリータイトル 更新が失敗しました");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;
		} finally {
		}
	}

	/**
	 * 試作列 温度フリー内容 更新 : 指定試作列データに対して温度フリー内容を設定する
	 * 
	 * @param strFreeOndo
	 *            : 温度フリー内容
	 * @param dciUserId
	 *            : ユーザID
	 */
	public void UpdShisakuRetuFreeOndo(int intShisakuSeq, String strFreeOndo,
			BigDecimal dciUserId) throws ExceptionBase {
		try {
			// 【KPX1500671】データ変更フラグの設定タイミング変更：MOD start
			// データ変更
			// HenkouFg = true;

			Iterator ite = aryShisakuRetu.iterator();

			// リスト件数分ループ
			while (ite.hasNext()) {

				// 試作列オブジェクト取得
				TrialData trialData = (TrialData) ite.next();

				// 試作SEQが一致
				if (intShisakuSeq == trialData.getIntShisakuSeq()) {

					// データ変更チェック
					chkHenkouData(trialData.getStrFreeOndo(), strFreeOndo);
					// 【KPX1500671】MOD end

					// 温度フリー内容設定
					trialData.setStrFreeOndo(strFreeOndo);

					// 更新者情報の設定
					trialData.setDciKosinId(dciUserId);
					trialData.setStrkosinHi(getSysDate());
				}
			}
		} catch (Exception e) {
			// エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("試作テーブルデータ保持の試作列 温度フリー項目 更新が失敗しました");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;
		} finally {
		}
	}

	/**
	 * 試作列フリータイトル④更新 : 全試作列データに対してフリータイトル④を設定する
	 * 
	 * @param strFreeTitle_4
	 *            : フリータイトル④
	 * @param dciUserId
	 *            : ユーザID
	 */
	public void UpdShisakuRetuFreeTitle_4(String strFreeTitle_4,
			BigDecimal dciUserId) throws ExceptionBase {
		try {
			// 【KPX1500671】データ変更フラグの設定タイミング変更：MOD start
			// データ変更
			// HenkouFg = true;

			Iterator ite = aryShisakuRetu.iterator();

			// リスト件数分ループ
			while (ite.hasNext()) {

				// 試作列オブジェクト取得
				TrialData trialData = (TrialData) ite.next();

				// データ変更チェック
				chkHenkouData(trialData.getStrFreeTitle4(), strFreeTitle_4);
				// 【KPX1500671】MOD end

				// フリータイトル④設定
				trialData.setStrFreeTitle4(strFreeTitle_4);

				// 更新者情報の設定
				trialData.setDciKosinId(dciUserId);
				trialData.setStrkosinHi(getSysDate());
			}
		} catch (Exception e) {
			// エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("試作テーブルデータ保持の試作列フリータイトル④更新が失敗しました");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;
		} finally {
		}
	}

	/**
	 * 試作列フリー内容④更新 : 指定試作列データに対してフリー内容④を設定する
	 * 
	 * @param strFreeNaiyou_4
	 *            : フリー内容④
	 * @param dciUserId
	 *            : ユーザID
	 */
	public void UpdShisakuRetuFreeNaiyou_4(int intShisakuSeq,
			String strFreeNaiyou_4, BigDecimal dciUserId) throws ExceptionBase {
		try {
			// 【KPX1500671】データ変更フラグの設定タイミング変更：MOD start
			// データ変更
			// HenkouFg = true;

			Iterator ite = aryShisakuRetu.iterator();

			// リスト件数分ループ
			while (ite.hasNext()) {

				// 試作列オブジェクト取得
				TrialData trialData = (TrialData) ite.next();

				// 試作SEQが一致
				if (intShisakuSeq == trialData.getIntShisakuSeq()) {

					// データ変更チェック
					chkHenkouData(trialData.getStrFreeNaiyo4(), strFreeNaiyou_4);
					// 【KPX1500671】MOD end

					// フリー内容④設定
					trialData.setStrFreeNaiyo4(strFreeNaiyou_4);

					// 更新者情報の設定
					trialData.setDciKosinId(dciUserId);
					trialData.setStrkosinHi(getSysDate());
				}
			}
		} catch (Exception e) {
			// エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("試作テーブルデータ保持の試作列フリー内容④更新が失敗しました");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;
		} finally {
		}
	}

	/**
	 * 試作列フリータイトル⑤更新 : 全試作列データに対してフリータイトル⑤を設定する
	 * 
	 * @param strFreeTitle_5
	 *            : フリータイトル⑤
	 * @param dciUserId
	 *            : ユーザID
	 */
	public void UpdShisakuRetuFreeTitle_5(String strFreeTitle_5,
			BigDecimal dciUserId) throws ExceptionBase {
		try {
			// 【KPX1500671】データ変更フラグの設定タイミング変更：MOD start
			// データ変更
			// HenkouFg = true;

			Iterator ite = aryShisakuRetu.iterator();

			// リスト件数分ループ
			while (ite.hasNext()) {

				// 試作列オブジェクト取得
				TrialData trialData = (TrialData) ite.next();

				// データ変更チェック
				chkHenkouData(trialData.getStrFreeTitle5(), strFreeTitle_5);
				// 【KPX1500671】MOD end

				// フリータイトル⑤設定
				trialData.setStrFreeTitle5(strFreeTitle_5);

				// 更新者情報の設定
				trialData.setDciKosinId(dciUserId);
				trialData.setStrkosinHi(getSysDate());
			}
		} catch (Exception e) {
			// エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("試作テーブルデータ保持の試作列フリータイトル⑤更新が失敗しました");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;
		} finally {
		}
	}

	/**
	 * 試作列フリー内容⑤更新 : 指定試作列データに対してフリー内容⑤を設定する
	 * 
	 * @param strFreeNaiyou_5
	 *            : フリー内容⑤
	 * @param dciUserId
	 *            : ユーザID
	 */
	public void UpdShisakuRetuFreeNaiyou_5(int intShisakuSeq,
			String strFreeNaiyou_5, BigDecimal dciUserId) throws ExceptionBase {
		try {
			// 【KPX1500671】データ変更フラグの設定タイミング変更：MOD start
			// データ変更
			// HenkouFg = true;

			Iterator ite = aryShisakuRetu.iterator();

			// リスト件数分ループ
			while (ite.hasNext()) {

				// 試作列オブジェクト取得
				TrialData trialData = (TrialData) ite.next();

				// 試作SEQが一致
				if (intShisakuSeq == trialData.getIntShisakuSeq()) {

					// データ変更チェック
					chkHenkouData(trialData.getStrFreeNaiyo5(), strFreeNaiyou_5);
					// 【KPX1500671】MOD end

					// フリー内容⑤設定
					trialData.setStrFreeNaiyo5(strFreeNaiyou_5);

					// 更新者情報の設定
					trialData.setDciKosinId(dciUserId);
					trialData.setStrkosinHi(getSysDate());
				}
			}
		} catch (Exception e) {
			// エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("試作テーブルデータ保持の試作列フリー内容⑤更新が失敗しました");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;
		} finally {
		}
	}

	/**
	 * 試作列フリータイトル⑥更新 : 全試作列データに対してフリータイトル⑥設定する
	 * 
	 * @param strFreeTitle_6
	 *            : フリータイトル⑥
	 * @param dciUserId
	 *            : ユーザID
	 */
	public void UpdShisakuRetuFreeTitle_6(String strFreeTitle_6,
			BigDecimal dciUserId) throws ExceptionBase {
		try {
			// 【KPX1500671】データ変更フラグの設定タイミング変更：MOD start
			// データ変更
			// HenkouFg = true;

			Iterator ite = aryShisakuRetu.iterator();

			// リスト件数分ループ
			while (ite.hasNext()) {

				// 試作列オブジェクト取得
				TrialData trialData = (TrialData) ite.next();

				// データ変更チェック
				chkHenkouData(trialData.getStrFreeTitle6(), strFreeTitle_6);
				// 【KPX1500671】MOD end

				// フリータイトル⑥設定
				trialData.setStrFreeTitle6(strFreeTitle_6);

				// 更新者情報の設定
				trialData.setDciKosinId(dciUserId);
				trialData.setStrkosinHi(getSysDate());
			}
		} catch (Exception e) {
			// エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("試作テーブルデータ保持の試作列フリータイトル⑥更新が失敗しました");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;
		} finally {
		}
	}

	/**
	 * 試作列フリー内容⑥更新 : 指定試作列データに対してフリー内容⑥を設定する
	 * 
	 * @param strFreeNaiyou_6
	 *            : フリー内容⑥
	 * @param dciUserId
	 *            : ユーザID
	 */
	public void UpdShisakuRetuFreeNaiyou_6(int intShisakuSeq,
			String strFreeNaiyou_6, BigDecimal dciUserId) throws ExceptionBase {
		try {
			// 【KPX1500671】データ変更フラグの設定タイミング変更：MOD start
			// データ変更
			// HenkouFg = true;

			Iterator ite = aryShisakuRetu.iterator();

			// リスト件数分ループ
			while (ite.hasNext()) {

				// 試作列オブジェクト取得
				TrialData trialData = (TrialData) ite.next();

				// 試作SEQが一致
				if (intShisakuSeq == trialData.getIntShisakuSeq()) {

					// データ変更チェック
					chkHenkouData(trialData.getStrFreeNaiyo6(), strFreeNaiyou_6);
					// 【KPX1500671】MOD end

					// フリー内容⑥設定
					trialData.setStrFreeNaiyo6(strFreeNaiyou_6);

					// 更新者情報の設定
					trialData.setDciKosinId(dciUserId);
					trialData.setStrkosinHi(getSysDate());
				}
			}
		} catch (Exception e) {
			// エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("試作テーブルデータ保持の試作列フリー内容⑥更新が失敗しました");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;
		} finally {
		}
	}

	// ADD end 20120928 QP@20505 No.24

	/**
	 * 0113.試作列作成メモ更新 : 指定試作列データに対して作成メモを設定する
	 * 
	 * @param strSakuseiMemo
	 *            : 作成メモ
	 * @param dciUserId
	 *            : ユーザID
	 */
	public void UpdShisakuRetuSakuseiMemo(int intShisakuSeq,
			String strSakuseiMemo, BigDecimal dciUserId) throws ExceptionBase {

		try {

			// 【KPX1500671】データ変更フラグの設定タイミング変更：MOD start
			// データ変更
			// HenkouFg = true;

			Iterator ite = aryShisakuRetu.iterator();

			// リスト件数分ループ
			while (ite.hasNext()) {

				// 試作列オブジェクト取得
				TrialData trialData = (TrialData) ite.next();

				// 試作SEQが一致
				if (intShisakuSeq == trialData.getIntShisakuSeq()) {

					// データ変更チェック
					chkHenkouData(trialData.getStrSakuseiMemo(), strSakuseiMemo);
					// 【KPX1500671】MOD end

					// 作成メモ設定
					trialData.setStrSakuseiMemo(strSakuseiMemo);

					// 更新者情報の設定
					trialData.setDciKosinId(dciUserId);
					trialData.setStrkosinHi(getSysDate());

				}
			}

		} catch (Exception e) {

			// エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("試作テーブルデータ保持の試作列作成メモ更新が失敗しました");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/**
	 * 0114.試作列評価更新 : 指定試作列データに対して評価を設定する
	 * 
	 * @param strHyouka
	 *            : 評価
	 * @param dciUserId
	 *            : ユーザID
	 */
	public void UpdShisakuRetuHyouka(int intShisakuSeq, String strHyouka,
			BigDecimal dciUserId) throws ExceptionBase {

		try {

			// 【KPX1500671】データ変更フラグの設定タイミング変更：MOD start
			// データ変更
			// HenkouFg = true;

			Iterator ite = aryShisakuRetu.iterator();

			// リスト件数分ループ
			while (ite.hasNext()) {

				// 試作列オブジェクト取得
				TrialData trialData = (TrialData) ite.next();

				// 試作SEQが一致
				if (intShisakuSeq == trialData.getIntShisakuSeq()) {

					// データ変更チェック
					chkHenkouData(trialData.getStrHyoka(), strHyouka);
					// 【KPX1500671】MOD end

					// 評価設定
					trialData.setStrHyoka(strHyouka);

					// 更新者情報の設定
					trialData.setDciKosinId(dciUserId);
					trialData.setStrkosinHi(getSysDate());

				}
			}

		} catch (Exception e) {

			// エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("試作テーブルデータ保持の試作列評価更新が失敗しました");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/**
	 * 0115.試作リスト量更新 : 試作リストデータに対して量を設定する
	 * 
	 * @param intShisakuSeq
	 *            : 試作SEQ
	 * @param intKouteiCd
	 *            : 工程CD
	 * @param intKouteiSeq
	 *            : 工程SEQ
	 * @param dciRyou
	 *            : 量
	 */
	public void UpdShisakuListRyo(int intShisakuSeq, int intKouteiCd,
			int intKouteiSeq, BigDecimal dciRyou) throws ExceptionBase {

		try {

			// 【KPX1500671】データ変更フラグの設定タイミング変更：MOD start
			// データ変更
			// HenkouFg = true;

			Iterator ite = aryShisakuList.iterator();

			// リスト件数分ループ
			while (ite.hasNext()) {

				// 試作リストオブジェクト取得
				PrototypeListData prototypeListData = (PrototypeListData) ite
						.next();

				// 試作SEQ・工程CD・工程SEQが一致した場合
				if (intShisakuSeq == prototypeListData.getIntShisakuSeq()
						&& intKouteiCd == prototypeListData.getIntKoteiCd()
						&& intKouteiSeq == prototypeListData.getIntKoteiSeq()) {

					// データ変更チェック
					chkHenkouData(prototypeListData.getDciRyo(), dciRyou);
					// 【KPX1500671】MOD end

					// 量設定
					prototypeListData.setDciRyo(dciRyou);

					// 更新者情報の設定
					prototypeListData.setDciKosinId(DataCtrl.getInstance()
							.getUserMstData().getDciUserid());
					prototypeListData.setStrKosinHi(getSysDate());

				}
			}

		} catch (Exception e) {

			// エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("試作テーブルデータ保持の試作リスト量更新が失敗しました");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/**
	 * 0116.試作リスト色更新 : 試作リストデータに対して色を設定する
	 * 
	 * @param intShisakuSeq
	 *            : 試作SEQ
	 * @param intKouteiCd
	 *            : 工程CD
	 * @param intKouteiSeq
	 *            : 工程SEQ
	 * @param strColor
	 *            : 色
	 * @param dcdciUserId
	 *            : ユーザID
	 */
	public void UpdShisakuListColor(int intShisakuSeq, int intKouteiCd,
			int intKouteiSeq, String strColor, BigDecimal dciUserId)
			throws ExceptionBase {

		try {

			// データ変更
			HenkouFg = true;

			Iterator ite = aryShisakuList.iterator();

			// リスト件数分ループ
			while (ite.hasNext()) {

				// 試作リストオブジェクト取得
				PrototypeListData prototypeListData = (PrototypeListData) ite
						.next();

				// 試作SEQ・工程CD・工程SEQが一致した場合
				if (intShisakuSeq == prototypeListData.getIntShisakuSeq()
						&& intKouteiCd == prototypeListData.getIntKoteiCd()
						&& intKouteiSeq == prototypeListData.getIntKoteiSeq()) {

					// 色設定
					prototypeListData.setStrIro(strColor);

					// 更新者情報の設定
					prototypeListData.setDciKosinId(dciUserId);
					prototypeListData.setStrKosinHi(getSysDate());

				}
			}

		} catch (Exception e) {

			// エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("試作テーブルデータ保持の試作リスト色更新が失敗しました");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/**
	 * 0118.製造工程データ追加 : 製造工程データに対して注意事項を設定する
	 * 
	 * @param intShisakuSeq
	 *            : 試作SEQ
	 * @param intChuiJikouNo
	 *            : 注意事項No
	 * @param strChuiJikou
	 *            : 注意事項
	 * @param dciUserId
	 *            : ユーザID
	 * @return ManufacturingData : 追加製造工程データ
	 */
	public ManufacturingData AddSeizoKouteiData(int intShsakuSeq,
			int intChuiJikouNo, String strChuiJikou, BigDecimal dciUserId)
			throws ExceptionBase {

		try {

			// データ変更
			HenkouFg = true;

			Iterator ite = arySeizo.iterator();

			// 注意事項No最大値 & Key項目
			int iMaxTyuiNo = 0;
			BigDecimal dciShisakuShainCd = ptdtShaisakuHin.getDciShisakuUser();
			BigDecimal dciShisakuYear = ptdtShaisakuHin.getDciShisakuYear();
			BigDecimal dciShisakuOiBan = ptdtShaisakuHin.getDciShisakuNum();

			ManufacturingData newManufacturingData = new ManufacturingData();

			// リスト件数分ループ
			while (ite.hasNext()) {

				// 製造工程オブジェクト取得
				ManufacturingData manufacturingData = (ManufacturingData) ite
						.next();

				// 注意事項Noの最大値設定
				if (iMaxTyuiNo < manufacturingData.getIntTyuiNo()) {

					// 注意事項No最大値の取得、KEY項目の設定を行う。
					iMaxTyuiNo = manufacturingData.getIntTyuiNo();
					dciShisakuShainCd = manufacturingData.getDciShisakuUser();
					dciShisakuYear = manufacturingData.getDciShisakuYear();
					dciShisakuOiBan = manufacturingData.getDciShisakuNum();

				}
			}

			// 新規製造工程データ設定
			newManufacturingData.setDciShisakuUser(dciShisakuShainCd);
			newManufacturingData.setDciShisakuYear(dciShisakuYear);
			newManufacturingData.setDciShisakuNum(dciShisakuOiBan);
			newManufacturingData.setIntTyuiNo(iMaxTyuiNo + 1);
			newManufacturingData.setStrTyuiNaiyo(strChuiJikou);

			// 更新者情報の設定
			newManufacturingData.setDciTorokuId(dciUserId);
			newManufacturingData.setStrTorokuHi(getSysDate());
			newManufacturingData.setDciKosinId(dciUserId);
			newManufacturingData.setStrKosinHi(getSysDate());

			// 製造工程リストに追加
			arySeizo.add(newManufacturingData);

			return newManufacturingData;

		} catch (Exception e) {

			// エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("試作テーブルデータ保持の製造工程データ追加が失敗しました");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/**
	 * 0119.製造工程データ更新 : 製造工程データに対して注意事項を設定する
	 * 
	 * @param intShisakuSeq
	 *            : 試作SEQ
	 * @param intChuiJikouNo
	 *            : 注意事項No
	 * @param strChuiJikou
	 *            : 注意事項
	 * @param dciUserId
	 *            : ユーザID
	 */
	public void UpdSeizoKouteiData(int intChuiJikouNo, String strChuiJikou,
			BigDecimal dciUserId) throws ExceptionBase {

		try {

			// 【KPX1500671】データ変更フラグの設定タイミング変更：MOD start
			// データ変更
			// HenkouFg = true;

			Iterator ite = arySeizo.iterator();

			// リスト件数分ループ
			while (ite.hasNext()) {

				// 製造工程オブジェクト取得
				ManufacturingData manufacturingData = (ManufacturingData) ite
						.next();

				// 注意事項が一致した場合
				if (intChuiJikouNo == manufacturingData.getIntTyuiNo()) {

					// データ変更チェック
					chkHenkouData(manufacturingData.getStrTyuiNaiyo(),
							strChuiJikou);
					// 【KPX1500671】MOD end

					// 注意事項Noを取得する。
					manufacturingData.setStrTyuiNaiyo(strChuiJikou);

					// 更新者情報の設定
					manufacturingData.setDciKosinId(dciUserId);
					manufacturingData.setStrKosinHi(getSysDate());

				}
			}

		} catch (Exception e) {

			// エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("試作テーブルデータ保持の製造工程データ更新が失敗しました");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/**
	 * 0129.試作表出力 : 試作表出力に必要なデータを生成し返却
	 * 
	 * @return 試作表データ
	 */
	public PrototypeListData OutShisakuList() throws ExceptionBase {

		try {

		} catch (Exception e) {

			// エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("試作テーブルデータ保持の試作表出力が失敗しました");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

		return null;
	}

	// /**
	// * 0130.サンプル表出力 : サンプル説明書出力に必要なデータを生成し返却
	// * @return サンプル説明書データ
	// */
	// public OutputSample OutSampleList() {
	// return null;
	// }

	/**
	 * 0131.試作列コピー計算試作設定 : 試作列コピー計算配列へ試作計算情報を登録する
	 * 
	 * @param intKeisanRetusu
	 *            : 計算列数
	 * @param intShisakuSeq
	 *            : 試作SEQ
	 * @param strKeisanFugo
	 *            : 計算符号
	 */
	public void SetShisaku_ShisakuRetuCopyKeisan(int intKeisanRetusu,
			int intShisakuSeq, String strKeisanFugo) throws ExceptionBase {

		try {

		} catch (Exception e) {

			// エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("試作テーブルデータ保持の試作列コピー計算試作設定が失敗しました");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/**
	 * 0132.試作列コピー計算工程設定 : 試作列コピー計算配列へ工程計算情報を登録する
	 * 
	 * @param intKeisanRetusu
	 *            : 計算列数
	 * @param intKouteiRetusu
	 *            : 工程列数
	 * @param intKouteiCd
	 *            : 工程CD
	 * @param intKouteiSeq
	 *            : 工程SEQ
	 * @param strKeisanFugo
	 *            : 計算符号
	 */
	public void SetKoutei_ShisakuRetuCopyKeisan(int intKeisanRetusu,
			int intKouteiRetusu, int intKouteiCd, int intKouteiSeq,
			String strKeisanFugo) throws ExceptionBase {

		try {

		} catch (Exception e) {

			// エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("試作テーブルデータ保持の試作列コピー計算工程設定が失敗しました");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/**
	 * 0133.試作列コピー計算実行 : 試作列コピー計算を行う
	 */
	public void RunShisakuRetuCopyKeisan() throws ExceptionBase {

		try {

		} catch (Exception e) {

			// エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("試作テーブルデータ保持の試作列コピー計算実行が失敗しました");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/**
	 * 0134.原価原料データ検索 : 原価原料データの検索
	 * 
	 * @param intShisakuSeq
	 *            : 試作SEQ
	 * @return 配列（原価原料データ）
	 */
	public ArrayList SearchGenkaGenryoData(int intShisakuSeq)
			throws ExceptionBase {

		// 新規リストインスタンス生成
		ArrayList arr = new ArrayList();

		try {

			// 引数：試作SEQが、0の場合
			if (intShisakuSeq == 0) {
				arr = aryGenka;
			} else {

				Iterator ite = aryGenka.iterator();

				// リスト件数分ループ
				while (ite.hasNext()) {
					// 試作列データオブジェクト取得
					CostMaterialData costMaterialData = (CostMaterialData) ite
							.next();

					// 引数：試作SEQと試作列データオブジェクト：試作SEQが一致した場合
					if (intShisakuSeq == costMaterialData.getIntShisakuSeq()) {
						// 返却リストに試作列データオブジェクト追加
						arr.add(costMaterialData);
					}
				}
			}

		} catch (Exception e) {

			// エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("原価原料テーブルデータ保持の原価原料データ検索が失敗しました");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

		return arr;
	}

	/**
	 * 0135.原価原料データ追加 : 原価原料データの追加処理
	 * 
	 * @param intShisakuSeq
	 *            : 試作SEQ
	 * @return 配列（原価原料データ）
	 */
	public void AddGenkaGenryoData(int intShisakuSeq) throws ExceptionBase {
		CostMaterialData costMaterialData = null;
		int intNewFlg = 0; // 新規データFlg

		try {

			// 試作テーブルデータを取得
			TrialData trialData = (TrialData) this.SearchShisakuRetuData(
					intShisakuSeq).get(0);

			// 原価原料テーブルデータ生成
			if (this.SearchGenkaGenryoData(intShisakuSeq).size() == 0) {
				// 追加
				costMaterialData = new CostMaterialData();
				intNewFlg = 0;

			} else {
				// 更新
				costMaterialData = (CostMaterialData) this
						.SearchGenkaGenryoData(intShisakuSeq).get(0);
				intNewFlg = 1;

			}

			// 新規データ設定
			if (intNewFlg == 0) {
				// 試作CD
				costMaterialData.setDciShisakuUser(this.ptdtShaisakuHin
						.getDciShisakuUser());
				costMaterialData.setDciShisakuYear(this.ptdtShaisakuHin
						.getDciShisakuYear());
				costMaterialData.setDciShisakuNum(this.ptdtShaisakuHin
						.getDciShisakuNum());
				// 試作SEQ
				costMaterialData.setIntShisakuSeq(intShisakuSeq);
				// 登録者ID・登録日付
				costMaterialData.setDciTorokuId(this.ptdtShaisakuHin
						.getDciShisakuUser());
				costMaterialData.setStrTorokuHi(this.getSysDate());

			}

			// 更新データ設定
			// 比重
			costMaterialData.setStrHizyu(trialData.getStrHizyu());
			// 容量
			costMaterialData.setStrYoryo(this.ptdtShaisakuHin.getStrYoryo());
			// 入り数
			costMaterialData.setStrIrisu(this.ptdtShaisakuHin.getStrIrisu());
			// 売価
			costMaterialData.setStrGenkakeiBai(this.ptdtShaisakuHin
					.getStrBaika());
			// 更新者ID・更新日付
			costMaterialData.setDciKosinId(this.ptdtShaisakuHin
					.getDciShisakuUser());
			costMaterialData.setStrKosinHi(this.getSysDate());

			// 計算処理
			UpdGenkaGenryoMath(costMaterialData);

			if (intNewFlg == 0) {
				// 新規 原価原料テーブルデータを追加
				this.aryGenka.add(costMaterialData);

			} else {
				// 更新 原価原料テーブルデータを設定
				for (int i = 0; i < this.aryGenka.size(); i++) {
					CostMaterialData csm = (CostMaterialData) this.aryGenka
							.get(i);

					if (csm.getIntShisakuSeq() == costMaterialData
							.getIntShisakuSeq()) {
						csm = costMaterialData;

					}

				}

			}

			// 2011/05/30　【QP@10181_No.42_49_72】工程パターン制御　TT.NISHIGAWA　start
			// 充填量を計算
			String keisan = DataCtrl.getInstance().getTrialTblData()
					.KeisanZyutenType1(costMaterialData.getIntShisakuSeq());
			UpdGenkaValue(costMaterialData.getIntShisakuSeq(), DataCtrl
					.getInstance().getTrialTblData().checkNullString(keisan),
					DataCtrl.getInstance().getUserMstData().getDciUserid(),
					JwsConstManager.JWS_COMPONENT_0134);
			// costMaterialData.setStrZyutenSui(keisan);

			// 水相充填量を計算
			String keisan1 = DataCtrl.getInstance().getTrialTblData()
					.KeisanSuisoZyuten(costMaterialData.getIntShisakuSeq());
			UpdGenkaValue(costMaterialData.getIntShisakuSeq(), DataCtrl
					.getInstance().getTrialTblData().checkNullString(keisan1),
					DataCtrl.getInstance().getUserMstData().getDciUserid(),
					JwsConstManager.JWS_COMPONENT_0134);
			// costMaterialData.setStrZyutenSui(keisan1);

			// 油相充填量を計算
			String keisan2 = DataCtrl.getInstance().getTrialTblData()
					.KeisanYusoZyuten(costMaterialData.getIntShisakuSeq());
			UpdGenkaValue(costMaterialData.getIntShisakuSeq(), DataCtrl
					.getInstance().getTrialTblData().checkNullString(keisan2),
					DataCtrl.getInstance().getUserMstData().getDciUserid(),
					JwsConstManager.JWS_COMPONENT_0135);
			// costMaterialData.setStrZyutenYu(keisan2);
			// 2011/05/30　【QP@10181_No.42_49_72】工程パターン制御　TT.NISHIGAWA　end

		} catch (Exception e) {
			e.printStackTrace();
			// エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("原価原料テーブルデータ保持の原価原料データ追加が失敗しました");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {
			costMaterialData = null;

		}

	}

	/**
	 * 0136.原価原料 印刷FG更新 : 指定試作列データに対して印刷FGを設定する
	 * 
	 * @param intShisakuSeq
	 *            : 試作SEQ
	 * @param Intinsatu
	 *            : 印刷FG
	 * @param dciUserId
	 *            : ユーザID
	 */
	public void UpdGenkaPrintFg(int intShisakuSeq, int Intinsatu,
			BigDecimal dciUserId) throws ExceptionBase {

		try {

			// 【KPX1500671】データ変更フラグの設定タイミング変更：MOD start
			// データ変更
			// HenkouFg = true;

			Iterator ite = this.aryGenka.iterator();

			// リスト件数分ループ
			while (ite.hasNext()) {

				// 試作列オブジェクト取得
				CostMaterialData costMaterialData = (CostMaterialData) ite
						.next();

				// 試作SEQが一致
				if (intShisakuSeq == costMaterialData.getIntShisakuSeq()) {

					// データ変更チェック
					chkHenkouData(costMaterialData.getIntinsatu(), Intinsatu);
					// 【KPX1500671】MOD end

					// 印刷FLG設定
					costMaterialData.setIntinsatu(Intinsatu);

					// 更新者情報の設定
					costMaterialData.setDciKosinId(dciUserId);
					costMaterialData.setStrKosinHi(getSysDate());

				}

			}

		} catch (Exception e) {

			// エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("原価原料テーブルデータ保持の印刷FG更新が失敗しました");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/**
	 * 0137.原価原料 画面値更新 : 指定試作列データに対して画面値を設定する
	 * 
	 * @param intShisakuSeq
	 *            : 試作SEQ
	 * @param strValue
	 *            : 設定値
	 * @param dciUserId
	 *            : ユーザID
	 * @param strName
	 *            : 項目名
	 */
	public void UpdGenkaValue(int intShisakuSeq, String strValue,
			BigDecimal dciUserId, String strName) throws ExceptionBase {

		try {

			// 【KPX1500671】データ変更フラグの設定タイミング変更：MOD start
			// データ変更
			// HenkouFg = true;

			Iterator ite = this.aryGenka.iterator();

			// 値がNullの場合、空白を設定する
			if (strName == null) {
				strName = "";
			}

			while (ite.hasNext()) {

				// 原価原料テーブルデータの取得
				CostMaterialData costMaterialData = (CostMaterialData) ite
						.next();

				// 原価原料テーブルデータに画面より送られてきた値を設定する
				if (intShisakuSeq == costMaterialData.getIntShisakuSeq()) {

					// 工程パターン取得
					String ptKotei = DataCtrl.getInstance().getTrialTblData()
							.SearchShisakuhinData().getStrPt_kotei();
					// 工程パターンのValue1取得
					String ptValue = null;

					if (ptKotei != null && ptKotei.length() > 0) {
						ptValue = DataCtrl.getInstance()
								.getLiteralDataKoteiPtn()
								.selectLiteralVal1(ptKotei);
					}

					// 充填量水相
					if (strName.equals(JwsConstManager.JWS_COMPONENT_0134)) {

						// その他・加食の場合
						if (JwsConstManager.JWS_KOTEITYPE_3.equals(ptValue)) {
							// データ変更チェック（数値比較）
							chkHenkouDecData(
									costMaterialData.getStrZyutenSui(),
									strValue);
						}

						costMaterialData.setStrZyutenSui(strValue);

						// 充填量油相
					} else if (strName
							.equals(JwsConstManager.JWS_COMPONENT_0135)) {

						// その他・加食の場合
						if (JwsConstManager.JWS_KOTEITYPE_3.equals(ptValue)) {
							// データ変更チェック（数値比較）
							chkHenkouDecData(costMaterialData.getStrZyutenYu(),
									strValue);
						}

						costMaterialData.setStrZyutenYu(strValue);

						// //合計量
						// } else if (
						// strName.equals(JwsConstManager.JWS_COMPONENT_0136) )
						// {
						// costMaterialData.setStrGokei(strValue);
						//
						// //原料費(kg)
						// } else if (
						// strName.equals(JwsConstManager.JWS_COMPONENT_0137) )
						// {
						// costMaterialData.setStrGenryohi(strValue);
						//
						// //原料費(１本当)
						// } else if (
						// strName.equals(JwsConstManager.JWS_COMPONENT_0138) )
						// {
						// costMaterialData.setStrGenryohiTan(strValue);

						// 比重
					} else if (strName
							.equals(JwsConstManager.JWS_COMPONENT_0139)) {

						costMaterialData.setStrHizyu(strValue);

						// 容量
					} else if (strName
							.equals(JwsConstManager.JWS_COMPONENT_0140)) {

						costMaterialData.setStrYoryo(strValue);

						// 入数
					} else if (strName
							.equals(JwsConstManager.JWS_COMPONENT_0141)) {

						costMaterialData.setStrIrisu(strValue);

						// 有効歩留
					} else if (strName
							.equals(JwsConstManager.JWS_COMPONENT_0127)) {
						// データ変更チェック（数値比較）
						chkHenkouDecData(costMaterialData.getStrYukoBudomari(),
								strValue);

						costMaterialData.setStrYukoBudomari(strValue);

						// //レベル量
						// } else if (
						// strName.equals(JwsConstManager.JWS_COMPONENT_0142) )
						// {
						// costMaterialData.setStrLevel(strValue);
						//
						// //比重加算量
						// } else if (
						// strName.equals(JwsConstManager.JWS_COMPONENT_0143) )
						// {
						// costMaterialData.setStrHizyuBudomari(strValue);

						// 平均充填量
					} else if (strName
							.equals(JwsConstManager.JWS_COMPONENT_0128)) {
						// データ変更チェック（数値比較）
						chkHenkouDecData(costMaterialData.getStrZyutenAve(),
								strValue);

						costMaterialData.setStrZyutenAve(strValue);

						// //原料費/cs
						// } else if (
						// strName.equals(JwsConstManager.JWS_COMPONENT_0144) )
						// {
						// costMaterialData.setStrGenryohiCs(strValue);

						// 材料費
					} else if (strName
							.equals(JwsConstManager.JWS_COMPONENT_0145)) {
						// データ変更チェック（数値比較）
						chkHenkouDecData(costMaterialData.getStrZairyohiCs(),
								strValue);

						costMaterialData.setStrZairyohiCs(strValue);

						// 固定費
					} else if (strName
							.equals(JwsConstManager.JWS_COMPONENT_0129)) {
						// データ変更チェック（数値比較）
						chkHenkouDecData(costMaterialData.getStrKeihiCs(),
								strValue);

						costMaterialData.setStrKeihiCs(strValue);

						// //原価計/cs
						// } else if (
						// strName.equals(JwsConstManager.JWS_COMPONENT_0146) )
						// {
						// costMaterialData.setStrGenkakeiCs(strValue);
						//
						// //原価計/個
						// } else if (
						// strName.equals(JwsConstManager.JWS_COMPONENT_0147) )
						// {
						// costMaterialData.setStrGenkakeiTan(strValue);

						// 売価
					} else if (strName
							.equals(JwsConstManager.JWS_COMPONENT_0130)) {

						costMaterialData.setStrGenkakeiBai(strValue);

						// //粗利
						// } else if (
						// strName.equals(JwsConstManager.JWS_COMPONENT_0148) )
						// {
						// costMaterialData.setStrGenkakeiRi(strValue);

					}
					// 【KPX1500671】MOD end

					// 計算処理
					UpdGenkaGenryoMath(costMaterialData);

					// 更新者情報の設定
					costMaterialData.setDciKosinId(dciUserId);
					costMaterialData.setStrKosinHi(getSysDate());

				}

			}

		} catch (Exception e) {
			// エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("原価原料テーブルデータ保持の値更新が失敗しました");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/**
	 * 0138.原価原料テーブル計算処理
	 * 
	 * @param costMaterialData
	 *            原価原料データ
	 * @throws ExceptionBase
	 */
	public void UpdGenkaGenryoMath(CostMaterialData costMaterialData)
			throws ExceptionBase {

		try {

			// 容量
			double dblYouryo = checkNumericDouble(this.ptdtShaisakuHin
					.getStrYoryo());
			if (this.ptdtShaisakuHin.getStrYoryo() != null) {
				costMaterialData.setStrYoryo(SetShosuKeta(
						Double.toString(dblYouryo), 0));

			} else {
				costMaterialData.setStrYoryo(null);

			}

			// 入数
			double dblIrisu = checkNumericDouble(this.ptdtShaisakuHin
					.getStrIrisu());
			if (this.ptdtShaisakuHin.getStrIrisu() != null) {
				costMaterialData.setStrIrisu(SetShosuKeta(
						Double.toString(dblIrisu), 0));

			} else {
				costMaterialData.setStrIrisu(null);

			}

			// 比重
			TrialData trialData = (TrialData) (this
					.SearchShisakuRetuData(costMaterialData.getIntShisakuSeq())
					.get(0));
			double dblHizyu = checkNumericDouble(trialData.getStrHizyu());
			if (trialData.getStrHizyu() != null) {
				costMaterialData.setStrHizyu(SetShosuKeta(
						Double.toString(dblHizyu), 3));

			} else {
				costMaterialData.setStrHizyu(null);

			}

			// 材料費
			double dblZairyohi = checkNumericDouble(costMaterialData
					.getStrZairyohiCs());
			if (costMaterialData.getStrZairyohiCs() != null) {
				costMaterialData.setStrZairyohiCs(SetShosuKeta(
						Double.toString(dblZairyohi), 2));

			} else {
				costMaterialData.setStrZairyohiCs(null);

			}

			// 固定費
			double dblKeihi = checkNumericDouble(costMaterialData
					.getStrKeihiCs());
			if (costMaterialData.getStrKeihiCs() != null) {
				costMaterialData.setStrKeihiCs(SetShosuKeta(
						Double.toString(dblKeihi), 2));

			} else {
				costMaterialData.setStrKeihiCs(null);

			}

			// 売価
			double dblBaika = checkNumericDouble(this.ptdtShaisakuHin
					.getStrBaika());
			if (this.ptdtShaisakuHin.getStrBaika() != null) {
				costMaterialData.setStrGenkakeiBai(SetShosuKeta(
						Double.toString(dblBaika), 2));

			} else {
				costMaterialData.setStrGenkakeiBai(null);

			}

			// 充填量水相
			double dblZyutenryoSuiso = checkNumericDouble(costMaterialData
					.getStrZyutenSui());
			if (costMaterialData.getStrZyutenSui() != null) {
				costMaterialData.setStrZyutenSui(SetShosuKeta(
						Double.toString(dblZyutenryoSuiso), 2));

			} else {
				// mod start
				// --------------------------------------------------------------------------------------
				// QP@00412_シサクイック改良 No.25
				// costMaterialData.setStrZyutenSui(null);
				costMaterialData.setStrZyutenSui(SetShosuKeta("0", 2));
				// mod end
				// --------------------------------------------------------------------------------------

			}

			// 充填量油相
			double dblZyutenryoYuso = checkNumericDouble(costMaterialData
					.getStrZyutenYu());
			if (costMaterialData.getStrZyutenYu() != null) {
				costMaterialData.setStrZyutenYu(SetShosuKeta(
						Double.toString(dblZyutenryoYuso), 2));

			} else {
				// mod start
				// --------------------------------------------------------------------------------------
				// QP@00412_シサクイック改良 No.25
				// costMaterialData.setStrZyutenYu(null);
				costMaterialData.setStrZyutenYu(SetShosuKeta("0", 2));
				// mod end
				// --------------------------------------------------------------------------------------

			}

			// 平均充填量
			double dblZyutenryoAve = checkNumericDouble(costMaterialData
					.getStrZyutenAve());
			if (costMaterialData.getStrZyutenAve() != null) {
				costMaterialData.setStrZyutenAve(SetShosuKeta(
						Double.toString(dblZyutenryoAve), 2));

			} else {
				costMaterialData.setStrZyutenAve(null);

			}

			// 有効歩留
			double dblYukoBudomari = checkNumericDouble(costMaterialData
					.getStrYukoBudomari());
			if (costMaterialData.getStrYukoBudomari() != null) {
				costMaterialData.setStrYukoBudomari(SetShosuKeta(
						Double.toString(dblYukoBudomari), 2));

			} else {
				costMaterialData.setStrYukoBudomari(null);

			}

			// 合計(１本:g)
			// : 容量x比重
			double dblGoukei = dblYouryo * dblHizyu;
			costMaterialData.setStrGokei(SetShosuKeta(
					Double.toString(dblGoukei), 3));

			// レベル量(g)
			// : 容量 x 入数
			double dblLevelRyo = dblYouryo * dblIrisu;
			costMaterialData.setStrLevel(SetShosuKeta(
					Double.toString(dblLevelRyo), 2));

			// mod start
			// --------------------------------------------------------------------------------------
			// QP@00412_シサクイック改良 No.24
			// 比重加算量(g)
			// // : レベル量x比重
			// costMaterialData.setStrHizyuBudomari(SetShosuKeta(Double.toString(dblLevelRyo*dblHizyu),2));
			// : 平均充填量x比重
			costMaterialData.setStrHizyuBudomari(SetShosuKeta2(
					Double.toString(dblZyutenryoAve * dblHizyu), 2));
			// mod end
			// --------------------------------------------------------------------------------------

			// 工程チェック
			if (CheckKotei() == 1 || CheckKotei() == 2) {

				// 原料費/ケース
				double dblGenryohiCs = 0.0;
				dblGenryohiCs = KeisanGenryoHi(costMaterialData);
				costMaterialData.setStrGenryohiCs(SetShosuKeta(
						Double.toString(dblGenryohiCs), 2));

				// 原料費（ｋg)
				// MOD 2013/8/2 okano【QP@30151】No.34 start
				// : 「原料費/ケース」÷「レベル量（ｇ）」*１０００
				// double dblGenryohiKg = dblGenryohiCs / dblLevelRyo * 1000;
				// : 「原料費/ケース」÷(「レベル量（ｇ）」*１０００ * 比重)
				double dblGenryohiKg = dblGenryohiCs / (dblLevelRyo * dblHizyu)
						* 1000;
				// MOD 2013/8/2 okano【QP@30151】No.34 end
				costMaterialData.setStrGenryohi(SetShosuKeta(
						Double.toString(dblGenryohiKg), 2));

				// 原料費（１本当)
				// : 「原料費/ケース」/入数
				double dblGenryohiTan = dblGenryohiCs / dblIrisu;
				costMaterialData.setStrGenryohiTan(SetShosuKeta(
						Double.toString(dblGenryohiTan), 2));

				// 原価計/ケース
				// : 「原料費/ケース」＋「材料費/ケース」（入力項目）＋「固定費/ケース」（入力項目）
				double dblGenkakeiCs = dblGenryohiCs + dblZairyohi + dblKeihi;
				costMaterialData.setStrGenkakeiCs(SetShosuKeta(
						Double.toString(dblGenkakeiCs), 2));

				// 原価計/個
				// : 「原価計/ケース」/「入り数」
				double dblGenkakeiTan = dblGenkakeiCs / dblIrisu;
				costMaterialData.setStrGenkakeiTan(SetShosuKeta(
						Double.toString(dblGenkakeiTan), 2));

				// 粗利（％）
				// : 1-（「原価計/個」/[売価」) ... %表示に変更
				double dblSori = (1 - (dblGenkakeiTan / dblBaika)) * 100;
				costMaterialData.setStrGenkakeiRi(SetShosuKeta(
						Double.toString(dblSori), 2));

				// System.out.println(dblSori);

			} else {

				// 工程が不正な場合、原料費に関わる項目にNULLを格納する

				// 原料費/ケース
				costMaterialData.setStrGenryohiCs(null);
				// 原料費（ｋg)
				costMaterialData.setStrGenryohi(null);
				// 原料費（１本当)
				costMaterialData.setStrGenryohiTan(null);
				// 原価計/ケース
				costMaterialData.setStrGenkakeiCs(null);
				// 原価計/個
				costMaterialData.setStrGenkakeiTan(null);
				// 粗利（％）
				costMaterialData.setStrGenkakeiRi(null);

			}

		} catch (Exception e) {

			// エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("原価原料テーブル計算処理が失敗しました");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/**
	 * 0139.原料費(kg)の計算処理 : 原料費を計算し、原価原料データに格納する
	 * 
	 * @return 原料費/cs
	 * @throws ExceptionBase
	 */
	public double KeisanGenryoHi(CostMaterialData costMaterialData)
			throws ExceptionBase {

		// 計算結果 : 原料費/cs
		double ret = 0.0;

		try {

			// 試作SEQ
			int intShisakuSeq = costMaterialData.getIntShisakuSeq();

			// 試作列データの取得
			TrialData trialData = (TrialData) this.SearchShisakuRetuData(
					intShisakuSeq).get(0);
			;

			// 配合データの取得
			ArrayList aryHaigoData = this.SearchHaigoData(0);

			// 水相合計重量・単価、油相合計重量・単価
			double dblSuisoGokeiryo = 0.0;
			double dblSuisoTanka = 0.0;
			double dblYusoGokeiryo = 0.0;
			double dblYusoTanka = 0.0;

			// 充填量水相
			double dblZyutenryoSuiso = checkNumericDouble(costMaterialData
					.getStrZyutenSui());
			// 充填量油相
			double dblZyutenryoYuso = checkNumericDouble(costMaterialData
					.getStrZyutenYu());
			// 平均充填量
			double dblZyutenryoAve = checkNumericDouble(costMaterialData
					.getStrZyutenAve());
			// 容量
			double dblYoryo = checkNumericDouble(this.ptdtShaisakuHin
					.getStrYoryo());
			// 入数
			double dblIrisu = checkNumericDouble(this.ptdtShaisakuHin
					.getStrIrisu());
			// レベル量
			double dblLevelryo = dblYoryo * dblIrisu;
			// 有効歩留
			double dblYukoBudomari = checkNumericDouble(costMaterialData
					.getStrYukoBudomari()) / 100;
			// 仕上がり合計重量
			double dblShiagariJuryo = dblSuisoGokeiryo;
			if (trialData.getDciShiagari() != null) {
				dblShiagariJuryo = checkNumericDouble(trialData
						.getDciShiagari());

			}

			// リテラル値
			String strLiteralVal1 = "";
			String strLiteralVal2 = "";

			// リスト件数分ループ
			for (int i = 0; i < aryHaigoData.size(); i++) {

				// 配合データの取得
				MixedData haigoData = (MixedData) aryHaigoData.get(i);

				// 工程属性の取得
				String strZokusei = haigoData.getStrKouteiZokusei();

				// 工程が選ばれていない場合
				if (strZokusei != null) {

					// リテラル値の取得
					strLiteralVal1 = DataCtrl.getInstance()
							.getLiteralDataZokusei()
							.selectLiteralVal1(strZokusei);
					strLiteralVal2 = DataCtrl.getInstance()
							.getLiteralDataZokusei()
							.selectLiteralVal2(strZokusei);

					// ----工程合計重量を求める----------------------

					// 配合データより値の取得

					// 工程CD
					int intKoteiCd = checkNullInt(""
							+ haigoData.getIntKoteiCd());
					// 工程SEQ
					int intKoteiSeq = checkNullInt(""
							+ haigoData.getIntKoteiSeq());
					// 単価
					double dblTanka = checkNumericDouble(haigoData
							.getDciTanka());
					// 歩留
					double dblBudomari = checkNumericDouble(haigoData
							.getDciBudomari()) / 100;

					// ----試作リストデータより、配合量を取得処理を行う----------------------

					// 配合量
					double dblRyo = 0.0;

					Iterator ite = this.getAryShisakuList().iterator();

					// リスト件数分ループ
					while (ite.hasNext()) {
						// 試作リストデータオブジェクト取得
						PrototypeListData shisakuRetu = (PrototypeListData) ite
								.next();

						// 試作SEQ、工程CD、工程SEQが一致した場合
						if (intShisakuSeq == shisakuRetu.getIntShisakuSeq()
								&& intKoteiCd == shisakuRetu.getIntKoteiCd()
								&& intKoteiSeq == shisakuRetu.getIntKoteiSeq()) {

							// 配合量を取得
							dblRyo = checkNumericDouble(shisakuRetu.getDciRyo());

							// ループを抜ける
							break;

						}

					}

					// ----リテラル値2による分岐処理----------------------
					if (strLiteralVal2.equals("1")
							|| strLiteralVal2.equals("2")) {

						// 水相の計算処理を行う [1:殺菌調味液, 2:水相 or 1:ソース, 2:別充填具材]
						// ※ 「その他調味液以外パターン」の場合は、
						// 　　ソースと別充填具材の合計重量と金額計を求める

						// 合計重量を求める
						dblSuisoGokeiryo += dblRyo;

						// 合計単価を求める
						// :　配合量*単価/歩留
						dblSuisoTanka += checkErrNumericDouble(dblRyo
								* dblTanka / dblBudomari);

					} else if (strLiteralVal2.equals("3")) {

						// 油相の計算処理を行う [3:油相]

						// 合計重量を求める
						dblYusoGokeiryo += dblRyo;

						// 合計単価を求める
						// :　配合量*単価/歩留
						dblYusoTanka += checkErrNumericDouble(dblRyo * dblTanka
								/ dblBudomari);

					}

				}
			}

			// --------------原料費/ケースを求める--------------

			// 調味液パターン
			if (strLiteralVal1.equals("1")) {

				// ① (水相合計単価 / 水相合計重量 * 充填量水相(g) /1000)
				double dblAnsSuiso = checkErrNumericDouble(dblSuisoTanka
						/ dblSuisoGokeiryo * dblZyutenryoSuiso / 1000);

				// ② (水相合計単価 / 水相合計重量 * 充填量油相(g) /1000)
				double dblAnsYuso = checkErrNumericDouble(dblYusoTanka
						/ dblYusoGokeiryo * dblZyutenryoYuso / 1000);

				// ③ 平均充填量 / レベル量 / 有効歩留　* 入数
				double dblAnsVal = checkErrNumericDouble(dblZyutenryoAve
						/ dblLevelryo / dblYukoBudomari * dblIrisu);

				// 原料費/ケース結果 = [ (① + ②) * ③ ]
				ret = (dblAnsSuiso + dblAnsYuso) * dblAnsVal;

				// その他調味液以外パターン
			} else if (strLiteralVal1.equals("2")) {

				// 合計単価/ 仕上がり重量合計 * 平均充填量 / 1000 / 有効歩留(%)
				ret = checkErrNumericDouble(dblSuisoTanka / dblShiagariJuryo
						* dblZyutenryoAve / 1000 / dblYukoBudomari);

			}

		} catch (ExceptionBase eb) {
			throw eb;

		} catch (Exception e) {
			// エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("原料費(kg)の計算処理が失敗しました");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}
		return ret;

	}

	/**
	 * 0140.工程チェック : 配合表(試作表①)画面の著未液が複数混在しているかのチェックを行う
	 * 
	 * @return チェック結果 [-1:混在している、0:選択されていない、 1:調味液選択、 2:その他調味液以外選択]
	 * @throws ExceptionBase
	 */
	public int CheckKotei() throws ExceptionBase {

		int ret = 0;

		try {

			// 配合データリストの取得
			ArrayList aryHaigoData = this.SearchHaigoData(0);

			// 工程が二つ以上存在する場合、混在チェックを行う
			if (aryHaigoData.size() > 0) {

				// 工程属性
				String strKoteiZoku = ((MixedData) aryHaigoData.get(0))
						.getStrKouteiZokusei();

				if (strKoteiZoku != null) {

					String strValue1 = DataCtrl.getInstance()
							.getLiteralDataZokusei()
							.selectLiteralVal1(strKoteiZoku);
					ret = checkNullInt(strValue1);

					for (int i = 1; i < aryHaigoData.size(); i++) {

						// 配合データの取得
						MixedData haigoData = (MixedData) aryHaigoData.get(i);

						String strChkZokusei = haigoData.getStrKouteiZokusei();
						if (strChkZokusei != null) {

							String strValue1Chk = DataCtrl.getInstance()
									.getLiteralDataZokusei()
									.selectLiteralVal1(strChkZokusei);

							// 配合表(試作表①)画面の工程が混在しているかをチェック
							if (!strValue1.equals(strValue1Chk)) {
								// 混在している場合
								ret = -1;

								// 処理を中断する
								break;

							} else {
								// 混在していない場合、リテラル値1を格納
								if (strValue1.equals("1")
										|| strValue1.equals("2")) {
									// 調味液、その他調味液以外の場合

									ret = checkNullInt(strValue1);

								} else {
									// 調味液、その他調味液以外ではない場合

									ret = 3;

								}

							}

						} else {
							ret = 0;

							// 処理を中断する
							break;

						}

					}

				}

			} else {

			}

		} catch (Exception e) {
			// エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("工程チェック処理が失敗しました");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}
		return ret;

	}

	/**
	 * システム日付 ゲッター
	 * 
	 * @return システム日付の値を返す
	 */
	public String getSysDate() {
		return new SimpleDateFormat("yyyy/MM/dd").format(new Date());
	}

	/**
	 * @return intSelectKotei
	 */
	public int getIntSelectKotei() {
		return intSelectKotei;
	}

	/**
	 * @param intSelectKotei
	 *            セットする intMaxKotei
	 */
	public void setIntSelectKotei(int intSelectKotei) {
		this.intSelectKotei = intSelectKotei;
	}

	/**
	 * @return intMaxKotei
	 */
	public int getIntMaxKotei() {
		return intMaxKotei;
	}

	/**
	 * @param intMaxKotei
	 *            セットする intMaxKotei
	 */
	public void setIntMaxKotei(int intMaxKotei) {
		this.intMaxKotei = intMaxKotei;
	}

	/**
	 * @return aryShisakuList
	 */
	public ArrayList getAryShisakuList() {
		return aryShisakuList;
	}

	/**
	 * @param aryShisakuList
	 *            セットする aryShisakuList
	 */
	public void setAryShisakuList(ArrayList aryShisakuList) {
		this.aryShisakuList = aryShisakuList;
	}

	/**
	 * 試作品データ表示
	 */
	public void dispPrototype() {

		System.out.println("");
		System.out
				.println("=========================　T110　========================");

		// 試作品データ表示

		System.out.println("");
		System.out.println("============　" + 1 + "件目　============");

		System.out.println("試作CD-社員CD：" + ptdtShaisakuHin.getDciShisakuUser());
		System.out.println("試作CD-年：" + ptdtShaisakuHin.getDciShisakuYear());
		System.out.println("試作CD-追番：" + ptdtShaisakuHin.getDciShisakuNum());
		System.out.println("依頼番号：" + ptdtShaisakuHin.getStrIrai());
		System.out.println("品名：" + ptdtShaisakuHin.getStrHinnm());
		// ADD 2013/06/19 ogawa 【QP@30151】No.9 start
		System.out.println("販責会社CD：" + ptdtShaisakuHin.getIntHansekicd());
		// ADD 2013/06/19 ogawa 【QP@30151】No.9 start
		System.out.println("指定工場-会社CD：" + ptdtShaisakuHin.getIntKaishacd());
		System.out.println("指定工場-工場CD：" + ptdtShaisakuHin.getIntKojoco());
		System.out.println("種別CD：" + ptdtShaisakuHin.getStrShubetu());
		System.out.println("種別No：" + ptdtShaisakuHin.getStrShubetuNo());
		System.out.println("グループCD：" + ptdtShaisakuHin.getIntGroupcd());
		System.out.println("チームCD：" + ptdtShaisakuHin.getIntTeamcd());
		System.out.println("一括表示CD：" + ptdtShaisakuHin.getStrIkatu());
		System.out.println("ジャンルCD：" + ptdtShaisakuHin.getStrZyanru());
		System.out.println("ユーザCD：" + ptdtShaisakuHin.getStrUsercd());
		System.out.println("特徴原料：" + ptdtShaisakuHin.getStrTokutyo());
		System.out.println("用途：" + ptdtShaisakuHin.getStrYoto());
		System.out.println("価格帯CD：" + ptdtShaisakuHin.getStrKakaku());
		System.out.println("担当営業CD：" + ptdtShaisakuHin.getStrTantoEigyo());
		System.out.println("製造方法CD：" + ptdtShaisakuHin.getStrSeizocd());
		System.out.println("充填方法CD：" + ptdtShaisakuHin.getStrZyutencd());
		System.out.println("殺菌方法：" + ptdtShaisakuHin.getStrSakin());
		System.out.println("容器・包材：" + ptdtShaisakuHin.getStrYokihozai());
		System.out.println("容量：" + ptdtShaisakuHin.getStrYoryo());
		System.out.println("容量単位CD：" + ptdtShaisakuHin.getStrTani());
		System.out.println("入り数：" + ptdtShaisakuHin.getStrIrisu());
		System.out.println("取扱温度CD：" + ptdtShaisakuHin.getStrOndo());
		System.out.println("賞味期間：" + ptdtShaisakuHin.getStrShomi());
		System.out.println("原価：" + ptdtShaisakuHin.getStrGenka());
		System.out.println("売価：" + ptdtShaisakuHin.getStrBaika());
		System.out.println("想定物量：" + ptdtShaisakuHin.getStrSotei());
		System.out.println("発売時期：" + ptdtShaisakuHin.getStrHatubai());
		System.out.println("計画売上：" + ptdtShaisakuHin.getStrKeikakuUri());
		System.out.println("計画利益：" + ptdtShaisakuHin.getStrKeikakuRie());
		System.out.println("販売後売上：" + ptdtShaisakuHin.getStrHanbaigoUri());
		System.out.println("販売後利益：" + ptdtShaisakuHin.getStrHanbaigoRie());
		System.out.println("荷姿：" + ptdtShaisakuHin.getStrNishugata());
		System.out.println("総合ﾒﾓ：" + ptdtShaisakuHin.getStrSogo());
		System.out.println("小数指定：" + ptdtShaisakuHin.getStrShosu());
		System.out.println("廃止区：" + ptdtShaisakuHin.getIntHaisi());
		System.out.println("排他：" + ptdtShaisakuHin.getDciHaita());
		System.out.println("製法試作：" + ptdtShaisakuHin.getIntSeihoShisaku());
		System.out.println("試作メモ：" + ptdtShaisakuHin.getStrShisakuMemo());
		System.out.println("注意事項表示：" + ptdtShaisakuHin.getIntChuiFg());
		System.out.println("登録者ID：" + ptdtShaisakuHin.getDciTorokuid());
		System.out.println("登録日付：" + ptdtShaisakuHin.getStrTorokuhi());
		System.out.println("更新者ID：" + ptdtShaisakuHin.getDciKosinId());
		System.out.println("更新日付：" + ptdtShaisakuHin.getStrKosinhi());
		System.out.println("工程パターン：" + ptdtShaisakuHin.getStrPt_kotei());
		System.out.println("シークレット：" + ptdtShaisakuHin.getStrSecret());
	}

	/**
	 * 配合データ表示
	 */
	public void dispHaigo() {

		System.out.println("");
		System.out
				.println("=========================　T120　========================");

		// 配合データ配列表示
		for (int i = 0; i < this.aryHaigou.size(); i++) {

			MixedData MixedData = (MixedData) this.aryHaigou.get(i);

			System.out.println("");
			System.out.println("============　" + (i + 1) + "件目　============");

			System.out.println("試作CD-社員CD：" + MixedData.getDciShisakuUser());
			System.out.println("試作CD-年：" + MixedData.getDciShisakuYear());
			System.out.println("試作CD-追番：" + MixedData.getDciShisakuNum());
			System.out.println("工程CD：" + MixedData.getIntKoteiCd());
			System.out.println("工程SEQ：" + MixedData.getIntKoteiSeq());
			System.out.println("工程名：" + MixedData.getStrKouteiNm());
			System.out.println("工程属性：" + MixedData.getStrKouteiZokusei());
			System.out.println("工程順：" + MixedData.getIntKoteiNo());
			System.out.println("原料順：" + MixedData.getIntGenryoNo());
			System.out.println("原料CD：" + MixedData.getStrGenryoCd());
			System.out.println("会社CD：" + MixedData.getIntKaishaCd());
			System.out.println("部署CD：" + MixedData.getIntBushoCd());
			System.out.println("原料名称：" + MixedData.getStrGenryoNm());
			System.out.println("単価：" + MixedData.getDciTanka());
			System.out.println("歩留：" + MixedData.getDciBudomari());
			System.out.println("油含有率：" + MixedData.getDciGanyuritu());
			System.out.println("酢酸：" + MixedData.getDciSakusan());
			System.out.println("食塩：" + MixedData.getDciShokuen());
			System.out.println("総酸：" + MixedData.getDciSosan());
			System.out.println("色：" + MixedData.getStrIro());
			System.out.println("登録者ID：" + MixedData.getDciTorokuId());
			System.out.println("登録日付：" + MixedData.getStrTorokuHi());
			System.out.println("更新者ID：" + MixedData.getDciKosinId());
			System.out.println("更新日付：" + MixedData.getStrKosinHi());
			System.out.println("マスタ歩留：" + MixedData.getDciMaBudomari());

		}
	}

	/**
	 * 試作列データ表示
	 */
	public void dispTrial() {

		System.out.println("");
		System.out
				.println("=========================　T131　========================");

		// 試作列データ配列表示
		for (int i = 0; i < this.aryShisakuRetu.size(); i++) {

			TrialData TrialData = (TrialData) this.aryShisakuRetu.get(i);

			System.out.println("");
			System.out.println("============　" + (i + 1) + "件目　============");

			System.out.println("試作CD-社員CD：" + TrialData.getDciShisakuUser());
			System.out.println("試作CD-年：" + TrialData.getDciShisakuYear());
			System.out.println("試作CD-追番：" + TrialData.getDciShisakuNum());
			System.out.println("試作SEQ：" + TrialData.getIntShisakuSeq());
			System.out.println("試作表示順：" + TrialData.getIntHyojiNo());
			System.out.println("注意事項NO：" + TrialData.getStrTyuiNo());
			System.out.println("サンプルNO（名称）：" + TrialData.getStrSampleNo());
			System.out.println("メモ：" + TrialData.getStrMemo());
			System.out.println("試作メモ：" + TrialData.getStrSakuseiMemo());
			System.out.println("印刷Flg：" + TrialData.getIntInsatuFlg());
			System.out.println("自動計算Flg：" + TrialData.getIntZidoKei());
			System.out.println("原価試算No：" + TrialData.getIntGenkaShisan());
			System.out.println("製法No-1：" + TrialData.getStrSeihoNo1());
			System.out.println("製法No-2：" + TrialData.getStrSeihoNo2());
			System.out.println("製法No-3：" + TrialData.getStrSeihoNo3());
			System.out.println("製法No-4：" + TrialData.getStrSeihoNo4());
			System.out.println("製法No-5：" + TrialData.getStrSeihoNo5());
			System.out.println("総酸：" + TrialData.getDciSosan());
			System.out.println("総酸-出力Flg：" + TrialData.getIntSosanFlg());
			System.out.println("食塩：" + TrialData.getDciShokuen());
			System.out.println("食塩-出力Flg：" + TrialData.getIntShokuenFlg());
			System.out.println("水相中酸度：" + TrialData.getDciSuiSando());
			System.out.println("水相中酸度-出力Flg：" + TrialData.getIntSuiSandoFlg());
			System.out.println("水相中食塩：" + TrialData.getDciSuiShokuen());
			System.out
					.println("水相中食塩-出力Flg：" + TrialData.getIntSuiShokuenFlg());
			System.out.println("水相中酢酸：" + TrialData.getDciSuiSakusan());
			System.out
					.println("水相中酢酸-出力Flg：" + TrialData.getIntSuiSakusanFlg());
			System.out.println("糖度：" + TrialData.getStrToudo());
			System.out.println("糖度-出力Flg：" + TrialData.getIntToudoFlg());
			System.out.println("粘度：" + TrialData.getStrNendo());
			System.out.println("粘度-出力Flg：" + TrialData.getIntNendoFlg());
			System.out.println("温度：" + TrialData.getStrOndo());
			System.out.println("温度-出力Flg：" + TrialData.getIntOndoFlg());
			System.out.println("PH：" + TrialData.getStrPh());
			System.out.println("PH - 出力Flg：" + TrialData.getIntPhFlg());
			System.out.println("総酸：分析：" + TrialData.getStrSosanBunseki());
			System.out.println("総酸：分析-出力Flg："
					+ TrialData.getIntSosanBunsekiFlg());
			System.out.println("食塩：分析：" + TrialData.getStrShokuenBunseki());
			System.out.println("食塩：分析-出力Flg："
					+ TrialData.getIntShokuenBunsekiFlg());
			System.out.println("比重：" + TrialData.getStrHizyu());
			System.out.println("比重-出力Flg：" + TrialData.getIntHizyuFlg());
			System.out.println("水分活性：" + TrialData.getStrSuibun());
			System.out.println("水分活性-出力Flg：" + TrialData.getIntSuibunFlg());
			System.out.println("アルコール：" + TrialData.getStrArukoru());
			System.out.println("アルコール-出力Flg：" + TrialData.getIntArukoruFlg());
			System.out.println("作成メモ：" + TrialData.getStrSakuseiMemo());
			System.out
					.println("作成メモ-出力Flg：" + TrialData.getIntSakuseiMemoFlg());
			System.out.println("評価：" + TrialData.getStrHyoka());
			System.out.println("評価-出力Flg：" + TrialData.getIntHyokaFlg());
			System.out.println("フリー①タイトル：" + TrialData.getStrFreeTitle1());
			System.out.println("フリー①内容：" + TrialData.getStrFreeNaiyo1());
			System.out.println("フリー①-出力Flg：" + TrialData.getIntFreeFlg());
			System.out.println("フリー②タイトル：" + TrialData.getStrFreeTitle2());
			System.out.println("フリー②内容：" + TrialData.getStrFreeNaiyo2());
			System.out.println("フリー②-出力Flg：" + TrialData.getIntFreeFl2());
			System.out.println("フリー③タイトル：" + TrialData.getStrFreeTitle3());
			System.out.println("フリー③内容：" + TrialData.getStrFreeNaiyo3());
			System.out.println("フリー③-出力Flg：" + TrialData.getIntFreeFl3());
			System.out.println("試作日付：" + TrialData.getStrShisakuHi());
			System.out.println("仕上重量：" + TrialData.getDciShiagari());
			System.out.println("登録者ID：" + TrialData.getDciTorokuId());
			System.out.println("登録日付：" + TrialData.getStrTorokuHi());
			System.out.println("更新者ID：" + TrialData.getDciKosinId());
			System.out.println("更新日付：" + TrialData.getStrkosinHi());
			System.out.println("原価試算依頼：" + TrialData.getFlg_shisanIrai());
			System.out.println("既存依頼データフラグ：" + TrialData.getFlg_init());
			System.out.println("計算式：" + TrialData.getStrKeisanSiki());
			System.out.println("キャンセルFG：" + TrialData.getFlg_cancel());
			System.out.println("水相比重：" + TrialData.getStrHiju_sui());
			System.out.println("水相比重　出力FG：" + TrialData.getIntHiju_sui_fg());

		}
	}

	/**
	 * 試作リストデータ表示
	 */
	public void dispProtoList() {

		System.out.println("");
		System.out
				.println("=========================　T132　========================");

		// 試作リストデータ配列表示
		for (int i = 0; i < this.aryShisakuList.size(); i++) {

			PrototypeListData PrototypeListData = (PrototypeListData) this.aryShisakuList
					.get(i);

			System.out.println("");
			System.out.println("============　" + (i + 1) + "件目　============");

			System.out.println("試作CD-社員CD："
					+ PrototypeListData.getDciShisakuUser());
			System.out.println("試作CD-年："
					+ PrototypeListData.getDciShisakuYear());
			System.out.println("試作CD-追番："
					+ PrototypeListData.getDciShisakuNum());
			System.out.println("試作SEQ：" + PrototypeListData.getIntShisakuSeq());
			System.out.println("工程CD：" + PrototypeListData.getIntKoteiCd());
			System.out.println("工程SEQ：" + PrototypeListData.getIntKoteiSeq());
			System.out.println("量：" + PrototypeListData.getDciRyo());
			System.out.println("色：" + PrototypeListData.getStrIro());
			System.out.println("登録者ID：" + PrototypeListData.getDciTorokuId());
			System.out.println("登録日付：" + PrototypeListData.getStrTorokuHi());
			System.out.println("更新者ID：" + PrototypeListData.getDciKosinId());
			System.out.println("更新日付：" + PrototypeListData.getStrKosinHi());
			// ADD start 20120914 QP@20505 No.1
			System.out.println("工程仕上重量："
					+ PrototypeListData.getDciKouteiShiagari());
			// ADD end 20120914 QP@20505 No.1

			System.out.println("【QP@00412】編集可能フラグ："
					+ PrototypeListData.getIntHenshuOkFg());
		}

	}

	/**
	 * 製造工程データ表示
	 */
	public void dispManufacturingData() {

		System.out.println("");
		System.out
				.println("=========================　T133　========================");

		// 製造工程データ配列表示
		for (int i = 0; i < this.arySeizo.size(); i++) {

			ManufacturingData ManufacturingData = (ManufacturingData) this.arySeizo
					.get(i);

			System.out.println("");
			System.out.println("============　" + (i + 1) + "件目　============");

			System.out.println("試作CD-社員CD："
					+ ManufacturingData.getDciShisakuUser());
			System.out.println("試作CD-年："
					+ ManufacturingData.getDciShisakuYear());
			System.out.println("試作CD-追番："
					+ ManufacturingData.getDciShisakuNum());
			System.out.println("注意事項NO：" + ManufacturingData.getIntTyuiNo());
			System.out.println("注意事項：" + ManufacturingData.getStrTyuiNaiyo());
			System.out.println("登録者ID：" + ManufacturingData.getDciTorokuId());
			System.out.println("登録日付：" + ManufacturingData.getStrTorokuHi());
			System.out.println("更新者ID：" + ManufacturingData.getDciKosinId());
			System.out.println("更新日付：" + ManufacturingData.getStrKosinHi());
		}
	}

	/**
	 * 資材データ表示
	 */
	public void dispShizaiData() {

		System.out.println("");
		System.out
				.println("=========================　T140　========================");

		// 資材データ配列表示
		for (int i = 0; i < this.aryShizai.size(); i++) {

			ShizaiData ShizaiData = (ShizaiData) this.aryShizai.get(i);

			System.out.println("");
			System.out.println("============　" + (i + 1) + "件目　============");

			System.out.println("試作CD-社員CD：" + ShizaiData.getDciShisakuUser());
			System.out.println("試作CD-年：" + ShizaiData.getDciShisakuYear());
			System.out.println("試作CD-追番：" + ShizaiData.getDciShisakuNum());
			System.out.println("資材SEQ：" + ShizaiData.getIntShizaiSeq());
			System.out.println("表示順：" + ShizaiData.getIntHyojiNo());
			System.out.println("資材CD：" + ShizaiData.getStrShizaiCd());
			System.out.println("資材名称：" + ShizaiData.getStrShizaiNm());
			System.out.println("単価：" + ShizaiData.getDciTanka());
			System.out.println("歩留：" + ShizaiData.getDciBudomari());
			System.out.println("登録者ID：" + ShizaiData.getDciTorokuId());
			System.out.println("登録日付：" + ShizaiData.getStrTorokuHi());
			System.out.println("更新者ID：" + ShizaiData.getDciKosinId());
			System.out.println("更新日付：" + ShizaiData.getStrKosinHi());
		}
	}

	/**
	 * 原価原料データ表示
	 */
	public void dispCostMaterialData() {

		System.out.println("");
		System.out
				.println("=========================　T141　========================");

		// 原価原料データ配列表示
		for (int i = 0; i < this.aryGenka.size(); i++) {

			CostMaterialData CostMaterialData = (CostMaterialData) this.aryGenka
					.get(i);

			System.out.println("");
			System.out.println("============　" + (i + 1) + "件目　============");

			System.out.println("試作CD-社員CD："
					+ CostMaterialData.getDciShisakuUser());
			System.out
					.println("試作CD-年：" + CostMaterialData.getDciShisakuYear());
			System.out
					.println("試作CD-追番：" + CostMaterialData.getDciShisakuNum());

			System.out.println("試作SEQ：" + CostMaterialData.getIntShisakuSeq());
			System.out.println("印刷flg：" + CostMaterialData.getIntinsatu());
			System.out.println("重点量水相：" + CostMaterialData.getStrZyutenSui());
			System.out.println("重点量油相：" + CostMaterialData.getStrZyutenYu());
			System.out.println("合計量：" + CostMaterialData.getStrGokei());
			System.out.println("原料費：" + CostMaterialData.getStrGenryohi());
			System.out.println("原料費（1本）："
					+ CostMaterialData.getStrGenryohiTan());
			System.out.println("比重：" + CostMaterialData.getStrHizyu());
			System.out.println("容量：" + CostMaterialData.getStrYoryo());
			System.out.println("入数：" + CostMaterialData.getStrIrisu());
			System.out.println("有効歩留：" + CostMaterialData.getStrYukoBudomari());
			System.out.println("レベル量：" + CostMaterialData.getStrLevel());
			System.out
					.println("比重歩留：" + CostMaterialData.getStrHizyuBudomari());
			System.out.println("平均充填量：" + CostMaterialData.getStrZyutenAve());
			System.out
					.println("1C/S原料費：" + CostMaterialData.getStrGenryohiCs());
			System.out
					.println("1C/S材料費：" + CostMaterialData.getStrZairyohiCs());
			System.out.println("1C/S経費：" + CostMaterialData.getStrKeihiCs());
			System.out
					.println("1C/S原価計：" + CostMaterialData.getStrGenkakeiCs());
			System.out.println("1個原価計：" + CostMaterialData.getStrGenkakeiTan());
			System.out.println("1個売価：" + CostMaterialData.getStrGenkakeiBai());
			System.out.println("1個粗利率：" + CostMaterialData.getStrGenkakeiRi());
			System.out.println("登録者ID：" + CostMaterialData.getDciTorokuId());
			System.out.println("登録日付：" + CostMaterialData.getStrTorokuHi());
			System.out.println("更新者ID：" + CostMaterialData.getDciKosinId());
			System.out.println("更新日付：" + CostMaterialData.getStrKosinHi());
		}
	}

	/**
	 * 空文字チェック（String）
	 */
	public String checkNullString(String val) {
		String ret = null;
		try {
			// 値が空文字でない場合
			if (!val.equals("")) {
				ret = val;
			}
		} catch (Exception e) {

		} finally {

		}
		return ret;
	}

	/**
	 * 空文字チェック（String）
	 */
	public String toString(Object val, String def) {

		String ret = def;

		try {

			ret = val.toString();

		} catch (Exception e) {

		} finally {

		}
		return ret;
	}

	public String toString(Object obj, String def, String reprceTg) {

		String ret = def;
		try {

			ret = toString(obj, def);

			ret = ret.replaceAll(reprceTg, "");

		} catch (Exception e) {

		}
		return ret;

	}

	/**
	 * 空文字チェック（Decimal）
	 */
	public BigDecimal checkNullDecimal(String val) {
		BigDecimal ret = null;
		try {
			// 値が空文字でない場合
			if (!val.equals("")) {
				ret = new BigDecimal(val);
			}
		} catch (Exception e) {

		} finally {

		}
		return ret;
	}

	/**
	 * 空文字チェック（Int）
	 */
	public int checkNullInt(String val) {
		int ret = -1;
		try {
			// 値が空文字でない場合
			if (!val.equals("")) {
				ret = Integer.parseInt(val);
			}
		} catch (Exception e) {

		} finally {

		}
		return ret;
	}

	/**
	 * 数値チェック（Double）
	 */
	public double checkNumericDouble(String val) {
		double ret = 0.0;
		try {
			// 値が空文字でない場合
			if (!val.equals("")) {
				ret = Double.parseDouble(val);
			}
		} catch (Exception e) {

		} finally {

		}
		return ret;
	}

	/**
	 * 数値チェック（Double）
	 */
	public double checkNumericDouble(Object val) {
		double ret = 0.0;
		try {
			// 値が空文字でない場合
			if (val != null) {
				ret = Double.parseDouble(val.toString());
			}
		} catch (Exception e) {

		} finally {

		}
		return ret;
	}

	/**
	 * エラー数値チェック(Double) : 対象値がNaN又はInfiniteの場合の判定処理
	 * 
	 * @param val
	 *            : 対象値
	 * @return double型の値
	 */
	private double checkErrNumericDouble(double val) {
		double ret = 0.0;

		try {

			// 値がNaN, Infiniteの場合
			if (Double.isNaN(val) || Double.isInfinite(val)) {
				ret = 0.0;

			} else {
				ret = val;

			}

		} catch (Exception e) {

		} finally {

		}
		return ret;
	}

	/**
	 * 小数桁数合わせ処理(桁は切り上げ)
	 * 
	 * @param dblValue
	 *            : 対象値
	 * @param intKetasu
	 *            : 指定小数桁数
	 * @return 桁合わせ編集後文字列
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private String SetShosuKeta(String strValue, int intKetasu)
			throws ExceptionBase {

		String ret = "0";

		try {

			// 対象値が空文字では無い場合
			if (checkNullString(strValue) != "") {

				// 値をDecimalに設定
				BigDecimal dciValue = new BigDecimal(strValue);

				// 値を指定小数桁数に合わせる
				ret = dciValue.setScale(intKetasu, BigDecimal.ROUND_UP)
						.toString();

			} else {

				// 値をDecimalに設定
				BigDecimal dciValue = new BigDecimal(ret);

				// 値を指定小数桁数に合わせる
				ret = dciValue.setScale(intKetasu, BigDecimal.ROUND_UP)
						.toString();

			}

		} catch (Exception e) {

		} finally {

		}
		return ret;

	}

	/**
	 * 小数桁数合わせ処理(桁は切り上げ)
	 * 
	 * @param dblValue
	 *            : 対象値
	 * @param intKetasu
	 *            : 指定小数桁数
	 * @return 桁合わせ編集後文字列
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private String SetShosuKeta2(String strValue, int intKetasu)
			throws ExceptionBase {

		String ret = "0";

		try {

			// 対象値が空文字では無い場合
			if (checkNullString(strValue) != "") {

				// 値をDecimalに設定
				BigDecimal dciValue = new BigDecimal(strValue);

				// 値を指定小数桁数に合わせる
				ret = dciValue.setScale(intKetasu, BigDecimal.ROUND_DOWN)
						.toString();

			} else {

				// 値をDecimalに設定
				BigDecimal dciValue = new BigDecimal(ret);

				// 値を指定小数桁数に合わせる
				ret = dciValue.setScale(intKetasu, BigDecimal.ROUND_DOWN)
						.toString();

			}

		} catch (Exception e) {

		} finally {

		}
		return ret;

	}

	/**
	 * 小数桁数合わせ処理( 四捨五入 )
	 * 
	 * @param dblValue
	 *            : 対象値
	 * @param intKetasu
	 *            : 指定小数桁数
	 * @return 桁合わせ編集後文字列
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private String SetShosuKeta3(String strValue, int intKetasu)
			throws ExceptionBase {

		String ret = "0";

		try {

			// 対象値が空文字では無い場合
			if (checkNullString(strValue) != "") {

				// 値をDecimalに設定
				BigDecimal dciValue = new BigDecimal(strValue);

				// 値を指定小数桁数に合わせる
				ret = dciValue.setScale(intKetasu, BigDecimal.ROUND_HALF_UP)
						.toString();

			} else {

				// 値をDecimalに設定
				BigDecimal dciValue = new BigDecimal(ret);

				// 値を指定小数桁数に合わせる
				ret = dciValue.setScale(intKetasu, BigDecimal.ROUND_HALF_UP)
						.toString();

			}

		} catch (Exception e) {

		} finally {

		}
		return ret;

	}

	/************************************************************************************
	 * 
	 * コメント行判定 : 指定文字列、指定桁数にてコメント行を判定する（指定桁数全てが9だった場合はコメント行）
	 * 
	 * @author TT nishigawa
	 * 
	 ************************************************************************************/
	public boolean commentChk(String strVal, int intKeta) {

		// 戻り値初期化
		boolean ret = true;

		try {

			// 指定文字列がNULLの場合
			if (strVal == null) {

				ret = false;

			}
			// 指定文字列がNULLでない場合
			else {

				// 指定文字列の桁数が指定桁数未満の場合
				if (strVal.length() < intKeta) {

					ret = false;

				}
				// 指定文字列の桁数が指定桁数未満でない場合
				else {

					// 指定文字列内のALL9チェック
					for (int i = 0; i < strVal.length(); i++) {

						// 1文字毎に取得
						String subVal = strVal.substring(i, i + 1);

						// 文字が「9」の場合
						if (subVal.equals("9")) {

							// 何もしない

						}
						// 文字が「9」以外の場合
						else {

							ret = false;

						}

					}

				}

			}

		} catch (Exception e) {

			e.printStackTrace();

		} finally {

		}

		// 返却
		return ret;
	}

	/************************************************************************************
	 * 
	 * 指定会社　原料桁数取得 : 試作③画面で指定した会社の原料桁を取得
	 * 
	 * @author TT nishigawa
	 * 
	 ************************************************************************************/
	public int getKaishaGenryo() {

		int ret = Integer.parseInt(JwsConstManager.JWS_KETA_GENRYO);

		try {

			// 指定会社コード取得
			int kaishaCd = DataCtrl.getInstance().getTrialTblData()
					.SearchShisakuhinData().getIntKaishacd();

			// 会社データコード検索
			KaishaData kaishaData = DataCtrl.getInstance().getKaishaData();

			for (int i = 0; i < kaishaData.getArtKaishaCd().size(); i++) {

				// 会社コード
				String getkaishaCd = kaishaData.getArtKaishaCd().get(i)
						.toString();

				// 原料桁
				String getketa_genryo = kaishaData.getAryKaishaGenryo().get(i)
						.toString();

				// 指定会社コードと等しい場合
				if (Integer.parseInt(getkaishaCd) == kaishaCd) {

					// 原料桁設定
					ret = Integer.parseInt(getketa_genryo);

				}
			}

		} catch (Exception e) {

			e.printStackTrace();

		} finally {

		}

		return ret;
	}

	/**
	 * 試作列 原価試算FG更新 : 指定試作列データに対して原価試算FGを設定する
	 * 
	 * @param intShisakuSeq
	 *            : 試作SEQ
	 * @param genkaIraiFg
	 *            : 原価試算依頼FG
	 * @param dciUserId
	 *            : ユーザID
	 */
	public void UpdShisakuRetuGenkaIraiFg(int intShisakuSeq, int genkaIraiFg,
			BigDecimal dciUserId) throws ExceptionBase {

		try {

			// 【KPX1500671】データ変更フラグの設定タイミング変更：MOD start
			// データ変更
			// HenkouFg = true;

			Iterator ite = aryShisakuRetu.iterator();

			// リスト件数分ループ
			while (ite.hasNext()) {

				// 試作列オブジェクト取得
				TrialData trialData = (TrialData) ite.next();

				// 試作SEQが一致
				if (intShisakuSeq == trialData.getIntShisakuSeq()) {

					// データ変更チェック
					chkHenkouData(trialData.getFlg_shisanIrai(), genkaIraiFg);
					// 【KPX1500671】MOD end

					// サンプルNo設定
					trialData.setFlg_shisanIrai(genkaIraiFg);

					// 更新者情報の設定
					trialData.setDciKosinId(dciUserId);
					trialData.setStrkosinHi(getSysDate());

				}
			}

		} catch (Exception e) {

			// エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("試作テーブルデータ保持の試作列サンプルNo更新が失敗しました");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	// 2011/04/12 QP@10181_No.67 TT Nishigawa Change Start
	// -------------------------
	/**
	 * 試作列 キャンセルFG更新 : 指定試作列データに対してキャンセルFGを設定する
	 * 
	 * @param intShisakuSeq
	 *            : 試作SEQ
	 * @param genkaCancelFg
	 *            : キャンセルFG
	 * @param dciUserId
	 *            : ユーザID
	 */
	public void UpdShisakuRetuCancelIraiFg(int intShisakuSeq,
			int genkaCancelFg, BigDecimal dciUserId) throws ExceptionBase {

		try {

			// 【KPX1500671】データ変更フラグの設定タイミング変更：MOD start
			// データ変更
			// HenkouFg = true;

			Iterator ite = aryShisakuRetu.iterator();

			// リスト件数分ループ
			while (ite.hasNext()) {

				// 試作列オブジェクト取得
				TrialData trialData = (TrialData) ite.next();

				// 試作SEQが一致
				if (intShisakuSeq == trialData.getIntShisakuSeq()) {

					// データ変更チェック
					chkHenkouData(trialData.getFlg_cancel(), genkaCancelFg);
					// 【KPX1500671】MOD end

					// キャンセルFG設定
					trialData.setFlg_cancel(genkaCancelFg);

					// 更新者情報の設定
					trialData.setDciKosinId(dciUserId);
					trialData.setStrkosinHi(getSysDate());

				}
			}

		} catch (Exception e) {

			// エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("試作テーブルデータ保持の試作列サンプルNo更新が失敗しました");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}
	}

	// 2011/04/12 QP@10181_No.67 TT Nishigawa Change End
	// -------------------------

	/**
	 * 試作列 原価試算FG編集不可指定 : 登録時に、既存依頼データフラグをonにする
	 */
	public void setShisakuRetuFlg_initCtrl() throws ExceptionBase {

		try {

			Iterator ite = aryShisakuRetu.iterator();

			// リスト件数分ループ
			while (ite.hasNext()) {

				// 試作列オブジェクト取得
				TrialData trialData = (TrialData) ite.next();

				// 原価試算依頼されているデータの場合
				if (trialData.getFlg_shisanIrai() == 1) {

					// 既存依頼データフラグをon
					trialData.setFlg_init(1);

				}
			}

		} catch (Exception e) {

			// エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("試作テーブルデータ保持の試作列サンプルNo更新が失敗しました");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/**
	 * データ変更フラグ ゲッター
	 * 
	 * @return HenkouFg : データ変更フラグ（false:データ変更無し、true:データ変更有り）
	 */
	public boolean getHenkouFg() {
		return HenkouFg;
	}

	/**
	 * データ変更フラグ セッター
	 * 
	 * @param henkouFg
	 *            : データ変更フラグ（false:データ変更無し、true:データ変更有り）
	 */
	public void setHenkouFg(boolean henkouFg) {
		HenkouFg = henkouFg;
	}

	/**
	 * 新規原料チェック
	 * 
	 * @param strCd
	 *            : 原料コード
	 */
	public boolean chkNgenryo(String strCd) {

		boolean rec = true;

		// 原料コードが空白の場合
		if (strCd == null || strCd.length() == 0) {

			rec = false;

		}
		// 原料コードが空白でない場合
		else {

			// 原料コードの一文字目を取得
			String strFirst = strCd.substring(0, 1);

			// 一文字目がNの場合
			if (strFirst.equals("N")) {

				// 何もしない

			}
			// 一文字目がNでない場合
			else {

				rec = false;

			}
		}

		return rec;

	}

	// add start
	// -------------------------------------------------------------------------------
	// QP@00412_シサクイック改良 No.33,34
	/**
	 * JWS試作リストデータ.編集可能フラグ設定　※１
	 */
	public void setShisakuListHenshuOkFg() throws ExceptionBase {

		try {

			// １．初期表示時と登録時に試作リストデータに下記２～５の条件でフラグを設定

			// 試作列件数分ループ
			for (int i = 0; i < aryShisakuRetu.size(); i++) {
				// 試作列データ取得
				TrialData TrialData = (TrialData) aryShisakuRetu.get(i);

				// ２．依頼確定フラグ＝１のデータ
				if (TrialData.getFlg_init() == 1) {

					// 試作リスト件数分ループ
					Iterator ite = aryShisakuList.iterator();
					while (ite.hasNext()) {

						// 試作リストオブジェクト取得
						PrototypeListData prototypeListData = (PrototypeListData) ite
								.next();

						// 試作SEQが一致した場合
						if (TrialData.getIntShisakuSeq() == prototypeListData
								.getIntShisakuSeq()) {

							// ３．自身の配合量に入力のないデータ（空白 or 0）
							if (prototypeListData.getDciRyo() == null
									|| (prototypeListData
											.getDciRyo()
											.compareTo(new BigDecimal("0.0000")) == 0)) {
								prototypeListData.setIntHanshuOkFg(0);
							}
							// ４．自身の配合量に入力のあるデータ
							else {
								prototypeListData.setIntHanshuOkFg(1);
							}
						}
					}
				}
			}

		} catch (Exception e) {

			// エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("試作テーブルデータ保持のJWS試作リストデータ.編集可能フラグ設定が失敗しました");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}
	}

	/**
	 * JWS試作リストデータ.編集可能フラグ設定　※１
	 */
	public void setShisakuListHenshuOkFg_init() throws ExceptionBase {

		try {
			// 試作リスト件数分ループ
			Iterator ite = aryShisakuList.iterator();
			while (ite.hasNext()) {
				// 試作リストオブジェクト取得
				PrototypeListData prototypeListData = (PrototypeListData) ite
						.next();
				prototypeListData.setIntHanshuOkFg(0);
			}
		} catch (Exception e) {

			// エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("試作テーブルデータ保持のJWS試作リストデータ.編集可能フラグ設定が失敗しました");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}
	}

	/**
	 * JWS試作リストデータ.編集可否チェック　※２ 　　：　各引数が「0」の場合は全件検索
	 * 
	 * @param : 試作SEQ
	 * @param : 工程CD
	 * @param : 工程SEQ
	 */
	public boolean checkListHenshuOkFg(int intShisakuSeq, int intKoteiCd,
			int intKoteiSeq) throws ExceptionBase {

		boolean ret = true;

		try {

			// １．引数を基に試作リストデータ取得
			Iterator ite = aryShisakuList.iterator();
			while (ite.hasNext()) {

				// 編集フラグチェック対象
				boolean blnChkFgShisakuSeq = false;
				boolean blnChkFgKoteiCd = false;
				boolean blnChkFgKoteiSeq = false;

				// 試作リストオブジェクト取得
				PrototypeListData prototypeListData = (PrototypeListData) ite
						.next();

				// 試作SEQ検索（0の場合は全件検索）
				if (intShisakuSeq == 0) {
					blnChkFgShisakuSeq = true;
				} else {
					if (intShisakuSeq == prototypeListData.getIntShisakuSeq()) {
						blnChkFgShisakuSeq = true;
					}
				}

				// 工程CD検索（0の場合は全件検索）
				if (intKoteiCd == 0) {
					blnChkFgKoteiCd = true;
				} else {
					if (intKoteiCd == prototypeListData.getIntKoteiCd()) {
						blnChkFgKoteiCd = true;
					}
				}

				// 工程SEQ検索（0の場合は全件検索）
				if (intKoteiSeq == 0) {
					blnChkFgKoteiSeq = true;
				} else {
					if (intKoteiSeq == prototypeListData.getIntKoteiSeq()) {
						blnChkFgKoteiSeq = true;
					}
				}

				// 編集可能フラグチェック
				if (blnChkFgShisakuSeq && blnChkFgKoteiCd && blnChkFgKoteiSeq) {
					int intHenshuOkFg = prototypeListData.getIntHenshuOkFg();
					if (intHenshuOkFg == 1) {
						ret = false;
					}
				}
			}
		} catch (Exception e) {

			// エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("試作テーブルデータ保持のJWS試作リストデータ.編集可能フラグ設定が失敗しました");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

		return ret;
	}

	/**
	 * 試作列編集可否チェック　※５
	 * 
	 * @param : 試作SEQ
	 */
	public boolean checkShisakuIraiKakuteiFg(int intShisakuSeq)
			throws ExceptionBase {

		boolean ret = true;

		try {
			Iterator ite = aryShisakuRetu.iterator();

			// リスト件数分ループ
			while (ite.hasNext()) {
				// 試作列データオブジェクト取得
				TrialData trialData = (TrialData) ite.next();

				// 引数：試作SEQと試作列データオブジェクト：試作SEQが一致した場合（0の場合は全件）
				if (intShisakuSeq == trialData.getIntShisakuSeq()
						|| intShisakuSeq == 0) {
					// 依頼確定フラグをチェック
					if (trialData.getFlg_init() == 1) {
						ret = false;
					}
				}
			}

		} catch (Exception e) {

			// エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("試作テーブルデータ保持のJWS試作リストデータ.編集可能フラグ設定が失敗しました");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

		return ret;
	}

	/**
	 * 配合データ色検索 : 配合リストより配合データの色を検索
	 * 
	 * @param intKouteiCd
	 *            : 工程ＣＤ
	 * @param intKouteiSeq
	 *            : 工程ＳＥＱ
	 * @throws ExceptionBase
	 */
	public String searchHaigouGenryoColor(int intKouteiCd, int intKouteiSeq)
			throws ExceptionBase {

		String ret = "-1";

		try {
			Iterator ite = aryHaigou.iterator();
			// リスト件数分ループ
			while (ite.hasNext()) {

				// 配合データオブジェクト取得
				MixedData mixData = (MixedData) ite.next();

				// 工程CD・工程SEQが一致
				if (intKouteiCd == mixData.getIntKoteiCd()
						&& intKouteiSeq == mixData.getIntKoteiSeq()) {

					// 原料色設定
					ret = mixData.getStrIro();

				}
			}

		} catch (Exception e) {

			// エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("試作テーブルデータ保持の配合原料色検索が失敗しました");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

		return ret;
	}

	/**
	 * 試作リストデータ色検索 : 試作リストより配合量データの色を検索
	 * 
	 * @param intShisakuSeq
	 *            : 試作ＳＥＱ
	 * @param intKouteiCd
	 *            : 工程ＣＤ
	 * @param intKouteiSeq
	 *            : 工程ＳＥＱ
	 * @throws ExceptionBase
	 */
	public String searchShisakuListColor(int intShisakuSeq, int intKouteiCd,
			int intKouteiSeq) throws ExceptionBase {

		String ret = "-1";

		try {
			Iterator ite = aryShisakuList.iterator();
			// リスト件数分ループ
			while (ite.hasNext()) {

				// 配合データオブジェクト取得
				PrototypeListData PrototypeListData = (PrototypeListData) ite
						.next();

				// 工程CD・工程SEQが一致
				if (intShisakuSeq == PrototypeListData.getIntShisakuSeq()
						&& intKouteiCd == PrototypeListData.getIntKoteiCd()
						&& intKouteiSeq == PrototypeListData.getIntKoteiSeq()) {

					// 原料色設定
					ret = PrototypeListData.getStrIro();

				}
			}

		} catch (Exception e) {

			// エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("試作テーブルデータ保持の配合原料色検索が失敗しました");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

		return ret;

	}

	// add end
	// -------------------------------------------------------------------------------

	// add start
	// -------------------------------------------------------------------------------
	// QP@00412_シサクイック改良 No.8
	/**
	 * データ設定（新規） : XMLデータ（試作品ＲＥＣ）よりコンスト値を設定する。
	 * 
	 * @param xdtData
	 *            : XMLデータ
	 * @throws ExceptionBase
	 */
	public void setConstData(XmlData xdtSetXml) throws ExceptionBase {

		try {
			XmlData xdtData = xdtSetXml;

			/**********************************************************
			 * 　T110格納
			 *********************************************************/
			// 　ID格納
			String strKinoId = "tr_shisakuhin";

			// 　全体配列取得
			ArrayList t110 = xdtData.GetAryTag(strKinoId);

			// 　テーブル配列取得
			ArrayList tableT110 = (ArrayList) t110.get(0);

			// 　レコード取得
			for (int i = 1; i < tableT110.size(); i++) {
				// 　１レコード取得
				ArrayList recData = ((ArrayList) ((ArrayList) tableT110.get(i))
						.get(0));

				// 　データ格納
				for (int j = 0; j < recData.size(); j++) {

					// 　項目名取得
					String recNm = ((String[]) recData.get(j))[1];
					// 　項目値取得
					String recVal = ((String[]) recData.get(j))[2];

					/***************** JWSコンストマネージャデータへ値セット *********************/
					// 代表工場セット
					if (recNm == "cd_daihyo_kojo") {
						JwsConstManager.JWS_CD_DAIHYO_KOJO = checkNullString(recVal);
					}
					// 代表会社セット
					if (recNm == "cd_daihyo_kaisha") {
						JwsConstManager.JWS_CD_DAIHYO_KAISHA = checkNullString(recVal);
					}
					// add start
					// -------------------------------------------------------------------------------
					// QP@00412_シサクイック改良 No.4
					// 代表工場セット
					if (recNm == "nm_shiyo") {
						JwsConstManager.JWS_NM_SHIYO = checkNullString(recVal);
					}
					// add end
					// -------------------------------------------------------------------------------
				}
				recData.clear();
			}
			tableT110.clear();
			t110.clear();

		} catch (Exception e) {
			e.printStackTrace();
			// エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("試作テーブルデータ保持のデータ設定（詳細or製法コピー）が失敗しました");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}
	}

	// add end
	// -------------------------------------------------------------------------------

	// add start
	// -------------------------------------------------------------------------------
	// QP@00412_シサクイック改良 No.30
	/**
	 * 新規原料orコメント行or空白行チェック : 配合リストより配合データの原料コードより検索
	 * 
	 * @param intKouteiCd
	 *            : 工程ＣＤ
	 * @param intKouteiSeq
	 *            : 工程ＳＥＱ
	 * @return boolean : 新規原料orコメント行or空白行:ture 既存:false
	 * @throws ExceptionBase
	 */
	public boolean searchHaigouGenryoCdSinki(int intKouteiCd, int intKouteiSeq)
			throws ExceptionBase {

		boolean ret = false;

		try {
			Iterator ite = aryHaigou.iterator();
			// リスト件数分ループ
			while (ite.hasNext()) {

				// 配合データオブジェクト取得
				MixedData mixData = (MixedData) ite.next();

				// 工程CD・工程SEQが一致
				if (intKouteiCd == mixData.getIntKoteiCd()
						&& intKouteiSeq == mixData.getIntKoteiSeq()) {

					// 新規原料orコメント行or空白行の場合
					if (mixData.getStrGenryoCd() == null) {
						ret = true;
					} else if (mixData.getStrGenryoCd().indexOf("N") != -1) {
						ret = true;
					} else if (mixData.getStrGenryoCd().indexOf("n") != -1) {
						ret = true;
					} else if (commentChk(mixData.getStrGenryoCd())) {
						ret = true;
					}

				}
			}

		} catch (Exception e) {

			// エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("試作テーブルデータ保持の配合原料色検索が失敗しました");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

		return ret;
	}

	// 2011/06/02 QP@10181_No.73 TT T.Satoh Add Start -------------------------
	/**
	 * 会社コードチェック : 配合リストより配合データの会社コードより検索
	 * 
	 * @param intKouteiCd
	 *            : 工程ＣＤ
	 * @param intKouteiSeq
	 *            : 工程ＳＥＱ
	 * @return boolean : キューピー:ture キューピー以外:false
	 * @throws ExceptionBase
	 */
	public boolean searchHaigouKaishaCd(int intKouteiCd, int intKouteiSeq)
			throws ExceptionBase {

		boolean ret = false;

		try {

			// 基本情報の会社コードがキユーピーの場合
			if (DataCtrl.getInstance().getTrialTblData().SearchShisakuhinData()
					.getIntKaishacd() == Integer
					.parseInt(JwsConstManager.JWS_CD_KEWPIE)) {

			} else {
				return false;
			}

			Iterator ite = aryHaigou.iterator();
			// リスト件数分ループ
			while (ite.hasNext()) {

				// 配合データオブジェクト取得
				MixedData mixData = (MixedData) ite.next();

				// 工程CD・工程SEQが一致
				if (intKouteiCd == mixData.getIntKoteiCd()
						&& intKouteiSeq == mixData.getIntKoteiSeq()) {

					// 会社コードがキューピーの場合
					if (mixData.getIntKaishaCd() == Integer
							.parseInt(JwsConstManager.JWS_CD_KEWPIE)) {

						// ★原料の場合
						if (mixData.getStrGenryoNm() != null
								&& mixData.getStrGenryoNm().substring(0, 1)
										.equals(JwsConstManager.JWS_MARK_0001)) {
							ret = false;
						} else {
							ret = true;
						}
					}
				}
			}

		} catch (Exception e) {

			// エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("試作テーブルデータ保持の配合原料検索が失敗しました");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

		return ret;
	}

	// 2011/06/02 QP@10181_No.73 TT T.Satoh Add End ---------------------------

	/************************************************************************************
	 * 
	 * コメント行判定 : コメント行を判定する（全てが9だった場合はコメント行）
	 * 
	 * @author TT nishigawa
	 * 
	 ************************************************************************************/
	public boolean commentChk(String strVal) {

		// 戻り値初期化
		boolean ret = true;

		try {
			// 指定文字列がNULLの場合
			if (strVal == null) {
				ret = false;
			}
			// 指定文字列がNULLでない場合
			else {
				// 指定文字列内のALL9チェック
				for (int i = 0; i < strVal.length(); i++) {

					// 1文字毎に取得
					String subVal = strVal.substring(i, i + 1);

					// 文字が「9」の場合
					if (subVal.equals("9")) {

						// 何もしない

					}
					// 文字が「9」以外の場合
					else {
						ret = false;
					}
				}
			}
		} catch (Exception e) {

			e.printStackTrace();

		} finally {

		}

		return ret;
	}

	// add end
	// -------------------------------------------------------------------------------

	// add start
	// -------------------------------------------------------------------------------
	// QP@00412_シサクイック改良 No.31
	/**
	 * マスタ歩留比較チェック
	 * 
	 * @param intKouteiCd
	 *            : 工程ＣＤ
	 * @param intKouteiSeq
	 *            : 工程ＳＥＱ
	 * @return boolean : 同一:ture 相違:false
	 * @throws ExceptionBase
	 */
	public boolean searchHaigouGenryoMaBudomari(int intKouteiCd,
			int intKouteiSeq) throws ExceptionBase {

		boolean ret = false;

		try {
			Iterator ite = aryHaigou.iterator();
			// リスト件数分ループ
			while (ite.hasNext()) {

				// 配合データオブジェクト取得
				MixedData mixData = (MixedData) ite.next();

				// 工程CD・工程SEQが一致
				if (intKouteiCd == mixData.getIntKoteiCd()
						&& intKouteiSeq == mixData.getIntKoteiSeq()) {

					// 空白行とコメント行と★原料はtrueを返却
					if (mixData.getStrGenryoCd() == null) {
						ret = true;
					} else if (commentChk(mixData.getStrGenryoCd())) {
						ret = true;
					}

					else if (mixData.getStrGenryoNm() != null
							&& mixData.getStrGenryoNm().indexOf(
									JwsConstManager.JWS_MARK_0001) != -1) {
						ret = true;
					}

					// 新規原料、既存原料の場合
					else {
						// nullチェック
						if (mixData.getDciBudomari() == null
								|| mixData.getDciMaBudomari() == null) {
							// 配合データとマスタの歩留が同一の場合
							if (mixData.getDciBudomari() == null
									&& mixData.getDciMaBudomari() == null) {
								ret = true;
							}
						} else {
							// 配合データとマスタの歩留が同一の場合
							if (mixData.getDciBudomari().compareTo(
									mixData.getDciMaBudomari()) == 0) {
								ret = true;
							}
						}
					}
				}
			}

		} catch (Exception e) {

			e.printStackTrace();

			// エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("試作テーブルデータ保持の配合原料色検索が失敗しました");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

		return ret;
	}

	/**
	 * 配合原料マスタ歩留更新 : 指定原料データに対してマスタ歩留を設定する
	 * 
	 * @param intKouteiCd
	 *            : 工程CD
	 * @param intKouteiSeq
	 *            : 工程SEQ
	 * @param dciBudomari
	 *            : 原料歩留
	 */
	public void UpdHaigoGenryoMaBudomari(int intKouteiCd, int intKouteiSeq,
			BigDecimal dciBudomari) throws ExceptionBase {

		try {

			Iterator ite = aryHaigou.iterator();

			// リスト件数分ループ
			while (ite.hasNext()) {

				// 配合データオブジェクト取得
				MixedData mixData = (MixedData) ite.next();

				// 工程CD・工程SEQが一致
				if (intKouteiCd == mixData.getIntKoteiCd()
						&& intKouteiSeq == mixData.getIntKoteiSeq()) {

					// 歩留設定
					mixData.setDciMaBudomari(dciBudomari);

				}
			}

		} catch (Exception e) {

			// エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("試作テーブルデータ保持の配合原料歩留更新が失敗しました");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	// add end
	// -------------------------------------------------------------------------------

	// add start
	// -------------------------------------------------------------------------------
	// QP@00412_シサクイック改良 No.7
	/**
	 * コピー対象計算式変換 　第一引数の計算式を第二引数の検索開始位置より解析し、 　計算可能な計算式に変換する
	 * 
	 * @param strkeisanSiki
	 *            : 計算式
	 * @param index
	 *            : 検索開始位置
	 * @return 変換後計算式
	 */
	public String changeKeisanLogic(String strkeisanSiki, int index) {

		// 返却文字列
		String ret = strkeisanSiki;

		// 検索開始位置
		int intStartIndex = index;

		// 現在の文字位置
		int intSerchIndex = index;

		// 括弧開始位置
		int intKakkoStartIndex = index;

		// 最終文字位置
		int intLastIndex = ret.length();

		// 括弧カウント
		int intKakkoCount = 0;

		// 「括弧開き」指定
		String strKakkoHiraki = JwsConstManager.JWS_COPY_0003;

		// 「括弧閉じ」指定
		String strKakkoTozi = JwsConstManager.JWS_COPY_0004;

		// 試作SEQ設定文字列
		String strSeqMark = JwsConstManager.JWS_COPY_0005;

		// 処理終了
		boolean blnEnd = false;

		try {

			// 検索開始位置より最終文字までループ
			for (int i = intStartIndex; i < intLastIndex; i++) {

				// 現在の文字位置を設定
				intSerchIndex = i;

				// 文字を取得
				String str = ret.substring(i, i + 1);

				// 「括弧開き」があった場合
				if (str.equals(strKakkoHiraki)) {

					// 括弧開始位置を設定
					if (intKakkoCount == 0) {
						intKakkoStartIndex = i;
					}

					// 連続する「括弧開き」があった場合に、括弧カウント+1
					intKakkoCount += 1;

				} else {

					// 「括弧開き」でない場合にループアウト
					if (intKakkoCount > 0) {
						i = intLastIndex;
					}
				}
			}

			// 「括弧開き」が一つ以上存在した場合
			if (intKakkoCount > 0) {

				// 現在の文字位置より最終文字までループ
				for (int i = intSerchIndex + 1; i < intLastIndex; i++) {

					// 現在の文字位置を設定
					intSerchIndex = i;

					// 文字を取得
					String str = ret.substring(i, i + 1);

					// 「括弧開き」があった場合
					if (str.equals(strKakkoHiraki)) {

						// 括弧カウント+1
						intKakkoCount += 1;

					}
					// 「括弧閉じ」があった場合
					else if (str.equals(strKakkoTozi)) {

						// 括弧カウント-1
						intKakkoCount -= 1;

					}
					// 上記以外の文字
					else {

						// 何もしない

					}

					// 括弧カウントが0の場合
					if (intKakkoCount == 0) {

						// ループアウト
						i = intLastIndex;

					}
				}
			}
			// 計算式内に括弧が存在しない場合
			else {

				// 処理終了
				blnEnd = true;

			}

			// 括弧カウントが0の場合
			if (intKakkoCount == 0) {

				// 処理終了の場合（括弧が一つもない場合）
				if (blnEnd) {

					return ret;

				} else {

					// 検索開始位置から現在の文字位置までの部分計算式を取得
					String strBubunKeisan = ret.substring(intKakkoStartIndex,
							intSerchIndex + 1);

					// 試作列配列の取得
					ArrayList aryRetu = DataCtrl.getInstance()
							.getTrialTblData().SearchShisakuRetuData(0);

					// 試作配列ループ
					for (int i = 0; i < aryRetu.size(); i++) {

						// 試作列データ取得
						TrialData TrialData = (TrialData) aryRetu.get(i);

						// 試作列名取得（括弧付加）
						String strRetuName = strKakkoHiraki
								+ TrialData.getStrKeisanSiki() + strKakkoTozi;

						// 部分計算式とサンプルNoが等しい場合
						if (strBubunKeisan.equals(strRetuName)) {

							// 試作SEQ取得
							int intSeq = TrialData.getIntShisakuSeq();

							// 括弧ごと試作SEQと置き換える
							String ret1 = ret.substring(0, intKakkoStartIndex);
							String ret2 = ret.substring(intSerchIndex + 1,
									intLastIndex);
							ret = ret1 + strSeqMark + intSeq + ret2;

						}

					}

					// 再帰処理により再検索
					ret = changeKeisanLogic(ret, intStartIndex + 1);

				}
			} else {

				// 括弧が妥当でない
				// System.out.println("括弧の関係が妥当でない");

			}

		} catch (Exception e) {

			// e.printStackTrace();
			return ret;

		}

		return ret;
	}

	/**
	 * 変換計算式より試作SEQを配合量に置換
	 * 
	 * @param strkeisanSiki
	 *            : 計算式
	 * @param intKoteiCd
	 *            : 工程CD
	 * @param intKoteiSeq
	 *            : 工程SEQ
	 * @return 配合設定済み計算式
	 */
	public String getKeisanShisakuSeq(String strKeisanSiki, int intKoteiCd,
			int intKoteiSeq) {

		String ret = strKeisanSiki;

		try {

			// 試作SEQ設定文字列
			String strSeqMark = JwsConstManager.JWS_COPY_0005;

			// 試作列配列の取得
			ArrayList aryRetu = DataCtrl.getInstance().getTrialTblData()
					.SearchShisakuRetuData(0);

			// 試作配列ループ
			for (int i = 0; i < aryRetu.size(); i++) {

				// 試作列データ取得
				TrialData TrialData = (TrialData) aryRetu.get(i);

				// 試作SEQ取得
				int intSeq = TrialData.getIntShisakuSeq();

				// 変換用試作SEQ生成
				String changeSeq = strSeqMark + intSeq;

				// 試作SEQが計算式内に存在する場合
				if (ret.indexOf(changeSeq) >= 0) {

					// 試作リスト配列取得
					ArrayList aryShisakuList = DataCtrl.getInstance()
							.getTrialTblData().getAryShisakuList();

					// 試作リスト配列ループ
					for (int j = 0; j < aryShisakuList.size(); j++) {

						// 試作リストデータ取得
						PrototypeListData PrototypeListData = (PrototypeListData) aryShisakuList
								.get(j);

						// 工程CD取得
						int intPltKoteiCd = PrototypeListData.getIntKoteiCd();

						// 工程SEQ取得
						int intPltKoteiSeq = PrototypeListData.getIntKoteiSeq();

						// 試作SEQ取得
						int intPltShisakuSeq = PrototypeListData
								.getIntShisakuSeq();

						// 対象の配合である場合
						if (intPltKoteiCd == intKoteiCd
								&& intPltKoteiSeq == intKoteiSeq
								&& intPltShisakuSeq == intSeq) {

							// 配合量の取得
							String strRyo = toString(
									PrototypeListData.getDciRyo(), "");

							// 配合量が空白の場合
							if (strRyo.equals("")) {
								ret = "";
							}
							// 配合量が入力されている場合
							else {
								// 計算式内の試作SEQを配合量に置換
								ret = ret.replaceAll(changeSeq, strRyo);
							}
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}

	/**
	 * 変換計算式より試作SEQを合計仕上重量に置換
	 * 
	 * @param strkeisanSiki
	 *            : 計算式
	 * @return 配合設定済み計算式
	 */
	public String getKeisanShisakuSeqSiagari(String strKeisanSiki) {

		String ret = strKeisanSiki;

		try {

			// 試作SEQ設定文字列
			String strSeqMark = JwsConstManager.JWS_COPY_0005;

			// 試作列配列の取得
			ArrayList aryRetu = DataCtrl.getInstance().getTrialTblData()
					.SearchShisakuRetuData(0);

			// 最大試作SEQ取得
			int maxSeq = 0;
			for (int j = 0; j < aryRetu.size(); j++) {
				// 試作列データ取得
				TrialData TrialData = (TrialData) aryRetu.get(j);
				// 試作SEQ取得
				int intSeq = TrialData.getIntShisakuSeq();
				// 最大試作SEQ設定
				if (intSeq > maxSeq) {
					maxSeq = intSeq;
				}
			}

			// 試作SEQが大きい方から小さい方へループ
			for (int k = maxSeq; k >= 0; k--) {
				// 試作配列ループ
				for (int i = 0; i < aryRetu.size(); i++) {

					// 試作列データ取得
					TrialData TrialData = (TrialData) aryRetu.get(i);

					// 試作SEQ取得
					int intSeq = TrialData.getIntShisakuSeq();

					// ループSEQと等しい場合
					if (intSeq == k) {
						// 変換用試作SEQ生成
						String changeSeq = strSeqMark + intSeq;

						// 試作SEQが計算式内に存在する場合
						if (ret.indexOf(changeSeq) >= 0) {

							// 合計仕上重量の取得
							String strRyo = toString(
									TrialData.getDciShiagari(), "");

							// 合計仕上重量が空白の場合
							if (strRyo.equals("")) {
								ret = "";
							}
							// 合計仕上重量が入力されている場合
							else {
								// 計算式内の試作SEQを合計仕上重量に置換
								ret = ret.replaceAll(changeSeq, strRyo);
							}
						}
					}
				}
			}

			// //試作配列ループ
			// for( int i = 0; i < aryRetu.size(); i++ ){
			//
			// //試作列データ取得
			// TrialData TrialData = (TrialData)aryRetu.get(i);
			//
			// //試作SEQ取得
			// int intSeq = TrialData.getIntShisakuSeq();
			//
			// //変換用試作SEQ生成
			// String changeSeq = strSeqMark + intSeq;
			//
			// //試作SEQが計算式内に存在する場合
			// if( ret.indexOf(changeSeq) >= 0){
			//
			// //合計仕上重量の取得
			// String strRyo = toString(TrialData.getDciShiagari(),"");
			//
			// //合計仕上重量が空白の場合
			// if(strRyo.equals("")){
			// ret = "";
			// }
			// //合計仕上重量が入力されている場合
			// else{
			// //計算式内の試作SEQを合計仕上重量に置換
			// ret = ret.replaceAll( changeSeq , strRyo );
			// }
			// }
			// }
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}

	// QP@20505 2012/10/26 No1 Add Start
	/**
	 * 変換計算式より試作SEQを工程仕上重量に置換
	 * 
	 * @param strkeisanSiki
	 *            : 計算式
	 * @return 配合設定済み計算式
	 */
	public String getKeisanShisakuSeqKoteiSiagari(String strKeisanSiki,
			int koteiban) {

		String ret = strKeisanSiki;

		try {

			// 試作SEQ設定文字列
			String strSeqMark = JwsConstManager.JWS_COPY_0005;

			// 試作列配列の取得
			ArrayList aryRetu = DataCtrl.getInstance().getTrialTblData()
					.SearchShisakuRetuData(0);

			// 最大試作SEQ取得
			int maxSeq = 0;
			for (int j = 0; j < aryRetu.size(); j++) {
				// 試作列データ取得
				TrialData TrialData = (TrialData) aryRetu.get(j);
				// 試作SEQ取得
				int intSeq = TrialData.getIntShisakuSeq();
				// 最大試作SEQ設定
				if (intSeq > maxSeq) {
					maxSeq = intSeq;
				}
			}

			// 試作SEQが大きい方から小さい方へループ
			for (int k = maxSeq; k >= 0; k--) {
				// 試作配列ループ
				for (int i = 0; i < aryRetu.size(); i++) {

					// 試作列データ取得
					TrialData TrialData = (TrialData) aryRetu.get(i);

					// 試作SEQ取得
					int intSeq = TrialData.getIntShisakuSeq();

					// ループSEQと等しい場合
					if (intSeq == k) {
						// 変換用試作SEQ生成
						String changeSeq = strSeqMark + intSeq;

						// 試作SEQが計算式内に存在する場合
						if (ret.indexOf(changeSeq) >= 0) {
							// int firstKoteiSeq = 1;
							int intKoteiCode = DataCtrl.getInstance()
									.getTrialTblData()
									.getSerchKoteiCode(intSeq, koteiban);
							// ADD 20130408 start
							// 原料行削除後、試作列コピーすると、工程SEQが取得できない不具合の修正
							// PrototypeListData pd =
							// DataCtrl.getInstance().getTrialTblData().searchShisakuListData(
							// intSeq,
							// intKoteiCode,
							// firstKoteiSeq);
							PrototypeListData pd = DataCtrl
									.getInstance()
									.getTrialTblData()
									.searchShisakuListData_Copy(intSeq,
											intKoteiCode);
							// ADD 20130408 end
							String strRyo = DataCtrl.getInstance()
									.getTrialTblData()
									.toString((pd.getDciKouteiShiagari()), "");

							// 合計仕上重量が空白の場合
							if (strRyo.equals("")) {
								ret = "";
							}
							// 合計仕上重量が入力されている場合
							else {
								// 計算式内の試作SEQを合計仕上重量に置換
								ret = ret.replaceAll(changeSeq, strRyo);
							}
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}

	// QP@20505 2012/10/26 No1 Add End

	/**
	 * 　計算実行
	 * 
	 * @param strkeisanSiki
	 *            : 計算式
	 * @return 計算結果
	 */
	public String execKeisan(String strkeisanSiki) {

		String ret = "";

		// 計算式が空白の場合
		if (toString(strkeisanSiki, "", " ").equals("")) {

			// 何もしない

		}
		// 計算式が空白でない場合
		else {
			try {

				// 計算クラスを設定
				Rule rule = ExpRuleFactory.getDefaultRule();

				// 計算式を解析
				Expression exp = rule.parse(strkeisanSiki);

				// 計算実施
				double result = exp.evalDouble();

				// 結果を返却変数へ
				ret = Double.toString(result);

				// 計算結果が数値かどうかを確認
				BigDecimal bigDecimal = new BigDecimal(ret);

			} catch (Exception e) {

				// 返却値に空白を設定
				ret = "";

			}
		}

		return ret;

	}

	/**
	 * 試作列データ　計算式検索 : 試作列データの計算式検索
	 * 
	 * @param intShisakuSeq
	 *            : 試作SEQ
	 * @return 文字列（計算式）
	 */
	public String SearchShisakuKeisanSiki(int intShisakuSeq)
			throws ExceptionBase {

		String ret = "";

		try {
			Iterator ite = aryShisakuRetu.iterator();

			// リスト件数分ループ
			while (ite.hasNext()) {
				// 試作列データオブジェクト取得
				TrialData trialData = (TrialData) ite.next();

				// 引数：試作SEQと試作列データオブジェクト：試作SEQが一致した場合
				if (intShisakuSeq == trialData.getIntShisakuSeq()) {
					ret = trialData.getStrKeisanSiki();
				}
			}
		} catch (Exception e) {

			// エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("試作テーブルデータ保持の計算式検索が失敗しました");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}
		return ret;
	}

	/**
	 * 試作列データ　計算式更新
	 * 
	 * @param intShisakuSeq
	 *            : 試作SEQ
	 * @param strKeisanSiki
	 *            : 計算式
	 */
	public void UpdShisakuKeisanSiki(int intShisakuSeq, String strKeisanSiki)
			throws ExceptionBase {

		try {
			Iterator ite = aryShisakuRetu.iterator();

			// リスト件数分ループ
			while (ite.hasNext()) {
				// 試作列データオブジェクト取得
				TrialData trialData = (TrialData) ite.next();

				// 引数：試作SEQと試作列データオブジェクト：試作SEQが一致した場合
				if (intShisakuSeq == trialData.getIntShisakuSeq()) {
					trialData.setStrKeisanSiki(strKeisanSiki);
				}
			}
		} catch (Exception e) {

			// エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("試作テーブルデータ保持の計算式検索が失敗しました");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}
	}

	/**
	 * 試作リストデータ配合量検索 : 試作リストより配合量を検索
	 * 
	 * @param intShisakuSeq
	 *            : 試作ＳＥＱ
	 * @param intKouteiCd
	 *            : 工程ＣＤ
	 * @param intKouteiSeq
	 *            : 工程ＳＥＱ
	 * @throws ExceptionBase
	 */
	public String searchShisakuListRyo(int intShisakuSeq, int intKouteiCd,
			int intKouteiSeq) throws ExceptionBase {

		String ret = "";

		try {
			Iterator ite = aryShisakuList.iterator();
			// リスト件数分ループ
			while (ite.hasNext()) {

				// 試作リストデータオブジェクト取得
				PrototypeListData PrototypeListData = (PrototypeListData) ite
						.next();

				// 試作SEQ・工程CD・工程SEQが一致
				if (intShisakuSeq == PrototypeListData.getIntShisakuSeq()
						&& intKouteiCd == PrototypeListData.getIntKoteiCd()
						&& intKouteiSeq == PrototypeListData.getIntKoteiSeq()) {

					// 配合量設定
					ret = PrototypeListData.getDciRyo().toString();

				}
			}

		} catch (Exception e) {

		} finally {

		}
		return ret;
	}

	/**
	 * 試作列データ仕上重量検索 : 試作列リストより試作列データの仕上重量を検索
	 * 
	 * @param intShisakuSeq
	 *            : 試作SEQ
	 * @throws ExceptionBase
	 */
	public String searchShisakuRetuSiagari(int intShisakuSeq)
			throws ExceptionBase {

		String ret = "";

		try {
			Iterator ite = aryShisakuRetu.iterator();
			// リスト件数分ループ
			while (ite.hasNext()) {

				// 試作列オブジェクト取得
				TrialData trialData = (TrialData) ite.next();

				// 試作SEQが一致
				if (intShisakuSeq == trialData.getIntShisakuSeq()) {

					// 仕上重量設定
					ret = trialData.getDciShiagari().toString();

				}
			}

		} catch (Exception e) {

		} finally {

		}

		return ret;
	}

	// add end
	// -------------------------------------------------------------------------------

	// add start
	// -------------------------------------------------------------------------------
	// QP@00412_シサクイック改良 No.2
	/**
	 * サンプルNo同一名称チェック : 指定された試作SEQのサンプル名称と同一のものがあるのかをチェック
	 * 
	 * @param intShisakuSeq
	 *            : 試作SEQ
	 * @return ret : 同一サンプルNo
	 * @throws ExceptionBase
	 */
	public String checkDistSampleNo(int intShisakuSeq) throws ExceptionBase {

		String ret = null;

		try {
			String checkSampleNo = "";

			// リスト件数分ループ_サンプルNo取得用
			Iterator chk_ite = aryShisakuRetu.iterator();
			while (chk_ite.hasNext()) {
				// 試作列オブジェクト取得
				TrialData trialData = (TrialData) chk_ite.next();
				// 試作SEQが一致
				if (intShisakuSeq == trialData.getIntShisakuSeq()) {
					// サンプルNo取得
					checkSampleNo = trialData.getStrSampleNo();
					if (checkSampleNo == null) {
						checkSampleNo = "";
					}
					break;
				}
			}

			// リスト件数分ループ_サンプルNo比較用
			Iterator dis_ite = aryShisakuRetu.iterator();
			while (dis_ite.hasNext()) {
				// 試作列オブジェクト取得
				TrialData trialData = (TrialData) dis_ite.next();
				// 試作SEQが一致しない
				if (intShisakuSeq != trialData.getIntShisakuSeq()) {
					// サンプルNo取得
					String distSampleNo = trialData.getStrSampleNo();
					if (distSampleNo == null) {
						distSampleNo = "";
					}
					// サンプルNo比較：同一サンプルNoが存在する場合
					if (checkSampleNo.equals(distSampleNo)) {
						ret = distSampleNo;
						if (ret.equals("")) {
							ret = "（空白）";
						}
						return ret;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();

		} finally {

		}

		return ret;
	}

	/**
	 * サンプルNo同一名称チェック（全件） : 指定された試作SEQのサンプル名称と同一のものがあるのかをチェック（全件）
	 * 
	 * @param 無し
	 * @return ret : 同一サンプルNo
	 * @throws ExceptionBase
	 */
	public String checkDistSampleNo_ALL() throws ExceptionBase {

		String ret = null;

		try {
			Iterator ite = aryShisakuRetu.iterator();
			// リスト件数分ループ_サンプルNo取得用
			while (ite.hasNext()) {
				// 試作列オブジェクト取得
				TrialData trialData = (TrialData) ite.next();
				// サンプルNo同一名称チェック
				String chk = null;
				chk = this.checkDistSampleNo(trialData.getIntShisakuSeq());
				if (chk == null) {

				} else {
					if (ret == null) {
						ret = "";
						ret = ret + chk;
					} else {
						ret = ret + "\n" + chk;
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();

		} finally {

		}

		return ret;
	}

	/**
	 * 試作列データサンプルNo検索 : 試作列リストより試作列データのサンプルNoを検索
	 * 
	 * @param intShisakuSeq
	 *            : 試作SEQ
	 * @throws ExceptionBase
	 */
	public String searchShisakuRetuSampleNo(int intShisakuSeq)
			throws ExceptionBase {

		String ret = "";

		try {
			Iterator ite = aryShisakuRetu.iterator();
			// リスト件数分ループ
			while (ite.hasNext()) {

				// 試作列オブジェクト取得
				TrialData trialData = (TrialData) ite.next();

				// 試作SEQが一致
				if (intShisakuSeq == trialData.getIntShisakuSeq()) {

					// サンプルNo
					ret = trialData.getStrSampleNo();
				}
			}

		} catch (Exception e) {

		} finally {

		}

		return ret;
	}

	// add end
	// -------------------------------------------------------------------------------

	// 2011/05/11 QP@10181_No.42_49_72 TT T.Nishigawa Add Start
	// -------------------------
	/*************************************************************************************************
	 * 
	 * 試作列水相比重出力FG更新 : 指定試作列データに対して水相比重出力FGを設定する
	 * 
	 * @param intHijuFg
	 *            : 水相比重出力FG
	 * @param dciUserId
	 *            : ユーザID
	 * 
	 *************************************************************************************************/
	public void UpdShisakuRetuSuiHijuFg(int intHijuFg, BigDecimal dciUserId)
			throws ExceptionBase {

		try {

			// 【KPX1500671】データ変更フラグの設定タイミング変更：MOD start
			// データ変更
			// HenkouFg = true;

			Iterator ite = aryShisakuRetu.iterator();

			// リスト件数分ループ
			while (ite.hasNext()) {

				// 試作列オブジェクト取得
				TrialData trialData = (TrialData) ite.next();

				// データ変更チェック
				chkHenkouData(trialData.getIntHiju_sui_fg(), intHijuFg);
				// 【KPX1500671】MOD end

				// 水相比重出力FLG設定
				trialData.setIntHiju_sui_fg(intHijuFg);

				// 更新者情報の設定
				trialData.setDciKosinId(dciUserId);
				trialData.setStrkosinHi(getSysDate());

			}

		} catch (Exception e) {

			// エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("試作テーブルデータ保持の試作列比重出力FG更新が失敗しました");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/*************************************************************************************************
	 * 
	 * 試作列水相比重更新 : 指定試作列データに対して水相比重を設定する
	 * 
	 * @param intShisakuSeq
	 *            : 試作SEQ
	 * @param intHiju
	 *            : 水相比重
	 * @param dciUserId
	 *            : ユーザID
	 * 
	 *************************************************************************************************/
	public void UpdShisakuRetuSuiHiju(int intShisakuSeq, String strHiju,
			BigDecimal dciUserId) throws ExceptionBase {

		try {

			// 【KPX1500671】データ変更フラグの設定タイミング変更：MOD start
			// データ変更
			// HenkouFg = true;

			Iterator ite = aryShisakuRetu.iterator();

			// リスト件数分ループ
			while (ite.hasNext()) {

				// 試作列オブジェクト取得
				TrialData trialData = (TrialData) ite.next();

				// 試作SEQが一致
				if (intShisakuSeq == trialData.getIntShisakuSeq()) {

					// 工程パターン取得
					String ptKotei = DataCtrl.getInstance().getTrialTblData()
							.SearchShisakuhinData().getStrPt_kotei();
					String ptValue = null;
					// 工程パターンのValue1取得
					if (ptKotei != null && ptKotei.length() > 0) {
						ptValue = DataCtrl.getInstance()
								.getLiteralDataKoteiPtn()
								.selectLiteralVal1(ptKotei);
					}

					String yoryoTani = DataCtrl.getInstance().getTrialTblData()
							.SearchShisakuhinData().getStrTani();
					// 容量単位からValue1取得
					String taniValue1 = "";
					if (yoryoTani == null || yoryoTani.length() == 0) {
					} else {
						taniValue1 = DataCtrl.getInstance()
								.getLiteralDataTani()
								.selectLiteralVal1(yoryoTani);
					}

					// 2液タイプ かつ 単位「ml」
					if (JwsConstManager.JWS_KOTEITYPE_2.equals(ptValue)
							&& "1".equals(taniValue1)) {

						// データ変更チェック
						chkHenkouData(trialData.getStrHiju_sui(), strHiju);
					}
					// 【KPX1500671】MOD end

					// 水相比重設定
					trialData.setStrHiju_sui(strHiju);

					// 更新者情報の設定
					trialData.setDciKosinId(dciUserId);
					trialData.setStrkosinHi(getSysDate());

				}
			}

		} catch (Exception e) {

			// エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("試作テーブルデータ保持の試作列比重更新が失敗しました");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/*************************************************************************************************
	 * 
	 * 試作品工程パターン更新 : 試作品データに対して工程パターンを設定する
	 * 
	 * @param strKoteiPtn
	 *            : 工程パターン
	 * @param dciUserId
	 *            : ユーザID
	 * @throws ExceptionBase
	 * 
	 *************************************************************************************************/
	public void UpdKoteiPtn(String strKoteiPtn, BigDecimal dciUserId)
			throws ExceptionBase {

		try {

			// 【KPX1500671】データ変更フラグの設定タイミング変更：MOD start
			// データ変更
			// HenkouFg = true;

			// データ変更チェック
			chkHenkouData(ptdtShaisakuHin.getStrPt_kotei(), strKoteiPtn);
			// 【KPX1500671】MOD end

			// 試作品工程パターン更新
			ptdtShaisakuHin.setStrPt_kotei(strKoteiPtn);

			// 更新者情報の設定
			ptdtShaisakuHin.setDciKosinid(dciUserId);
			// UPD 2012.4.30　【H24年度対応】更新日で試作品の更新排他制御を行うため不用意の設定を削除
			// ptdtShaisakuHin.setStrKosinhi(getSysDate());

		} catch (Exception e) {

			// エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("試作テーブルデータ保持の試作品試作品工程パターン更新が失敗しました");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/*************************************************************************************************
	 * 
	 * 製品比重の計算処理
	 * 
	 * @return 製品比重
	 * @throws ExceptionBase
	 * 
	 *************************************************************************************************/
	public String KeisanSeihinHiju(int intShisakuSeq) throws ExceptionBase {

		// 計算結果 : 製品比重
		String ret = "";

		try {

			// 試作列データの取得
			TrialData trialData = (TrialData) this.SearchShisakuRetuData(
					intShisakuSeq).get(0);

			// 既に依頼されているデータの計算は行わない
			if (trialData.getFlg_init() == 1) {
				return trialData.getStrHizyu();
			}

			// 工程パターンが「空白」の場合
			String ptKotei = SearchShisakuhinData().getStrPt_kotei();
			if (ptKotei == null || ptKotei.length() == 0) {
				return trialData.getStrHizyu();
			}
			// 工程パターンが「空白」でない場合
			else {

				// 工程パターンのValue1取得
				String ptValue = DataCtrl.getInstance()
						.getLiteralDataKoteiPtn().selectLiteralVal1(ptKotei);

				// 容量単位からValue1取得
				String yoryoTani = DataCtrl.getInstance().getTrialTblData()
						.SearchShisakuhinData().getStrTani();
				String taniValue1 = "";
				if (yoryoTani == null || yoryoTani.length() == 0) {
					return trialData.getStrHizyu();
				} else {
					taniValue1 = DataCtrl.getInstance().getLiteralDataTani()
							.selectLiteralVal1(yoryoTani);
				}

				// 工程パターンが「調味料１液タイプ」の場合-------------------------------------------------------------
				if (ptValue.equals(JwsConstManager.JWS_KOTEITYPE_1)) {
					return trialData.getStrHizyu();
				}
				// 工程パターンが「調味料２液タイプ」の場合-------------------------------------------------------------
				else if (ptValue.equals(JwsConstManager.JWS_KOTEITYPE_2)) {
					// 容量が「ml」の場合
					if (taniValue1.equals("1")) {
						// 処理続行
					}
					// 容量が「空白」の場合（ml以外の場合）
					else {
						return trialData.getStrHizyu();
					}
				}
				// 工程パターンが「その他・加食タイプ」の場合-------------------------------------------------------------
				else if (ptValue.equals(JwsConstManager.JWS_KOTEITYPE_3)) {
					return trialData.getStrHizyu();
				}
			}

			// 配合データの取得
			ArrayList aryHaigoData = this.SearchHaigoData(0);

			// 殺菌調味液重量、ドレ水相合計重量、ドレ油相合計重量、水相比重、油相比重、製品比重
			double dblSakinGokeiryo = 0.0;
			double dblSuisoGokeiryo = 0.0;
			double dblYusoGokeiryo = 0.0;
			double dblSuisoHiju = checkNumericDouble(trialData.getStrHiju_sui());
			double dblYusoHiju = checkNumericDouble(DataCtrl.getInstance()
					.getLiteralDataYusoHiju().getAryBiko().get(0));
			double dblSeihinHiju = 0.0;

			// リテラル値
			String strLiteralVal1 = "";
			String strLiteralVal2 = "";

			// リスト件数分ループ
			for (int i = 0; i < aryHaigoData.size(); i++) {

				// 配合データの取得
				MixedData haigoData = (MixedData) aryHaigoData.get(i);

				// 工程属性の取得
				String strZokusei = haigoData.getStrKouteiZokusei();

				// 工程が選ばれている場合
				if (strZokusei != null) {

					// リテラル値の取得
					strLiteralVal1 = DataCtrl.getInstance()
							.getLiteralDataZokusei()
							.selectLiteralVal1(strZokusei);
					strLiteralVal2 = DataCtrl.getInstance()
							.getLiteralDataZokusei()
							.selectLiteralVal2(strZokusei);

					// ----工程合計重量を求める----------------------

					// 配合データより値の取得

					// 工程CD
					int intKoteiCd = checkNullInt(""
							+ haigoData.getIntKoteiCd());
					// 工程SEQ
					int intKoteiSeq = checkNullInt(""
							+ haigoData.getIntKoteiSeq());

					// ----試作リストデータより、配合量を取得処理を行う----------------------

					// 配合量
					double dblRyo = 0.0;

					Iterator ite = this.getAryShisakuList().iterator();

					// リスト件数分ループ
					while (ite.hasNext()) {
						// 試作リストデータオブジェクト取得
						PrototypeListData shisakuRetu = (PrototypeListData) ite
								.next();

						// 試作SEQ、工程CD、工程SEQが一致した場合
						if (intShisakuSeq == shisakuRetu.getIntShisakuSeq()
								&& intKoteiCd == shisakuRetu.getIntKoteiCd()
								&& intKoteiSeq == shisakuRetu.getIntKoteiSeq()) {

							// 配合量を取得
							dblRyo = checkNumericDouble(shisakuRetu.getDciRyo());

							// ループを抜ける
							break;

						}

					}

					// ----リテラル値2による分岐処理----------------------
					// 1:殺菌調味液
					if (strLiteralVal2.equals("1")) {

						// 殺菌調味液の合計重量を求める
						dblSakinGokeiryo += dblRyo;

					}
					// 2:水相
					else if (strLiteralVal2.equals("2")) {

						// 水相の合計重量を求める
						dblSuisoGokeiryo += dblRyo;

					}
					// 3:油相
					else if (strLiteralVal2.equals("3")) {

						// 油相の合計重量を求める
						dblYusoGokeiryo += dblRyo;

					}

				}
			}

			// --------------製品比重を求める--------------

			// ( 殺菌調味液重量 ＋ ドレ水相合計重量 ＋ ドレ油相合計重量 ) ÷ ( ( ( 殺菌調味液重量 ＋ ドレ水相合計重量 ) ÷
			// 水相比重 ) ＋ ( ドレ油相合計重量 ÷ 油相比重 ) )
			dblSeihinHiju = (dblSakinGokeiryo + dblSuisoGokeiryo + dblYusoGokeiryo)
					/ (((dblSakinGokeiryo + dblSuisoGokeiryo) / dblSuisoHiju) + (dblYusoGokeiryo / dblYusoHiju));

			// 少数桁数を調整
			ret = SetShosuKeta(Double.toString(dblSeihinHiju), 3);

		} catch (ExceptionBase eb) {
			throw eb;

		} catch (Exception e) {
			// エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("製品比重の計算処理が失敗しました");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}
		return ret;

	}

	/*************************************************************************************************
	 * 
	 * 調味料1液タイプ　充填量の計算処理
	 * 
	 * @return 充填量
	 * @throws ExceptionBase
	 * 
	 *************************************************************************************************/
	public String KeisanZyutenType1(int intShisakuSeq) throws ExceptionBase {

		// 計算結果 : 充填量
		String ret = "";

		try {
			// 原価データ取得
			CostMaterialData costMaterialData = (CostMaterialData) this
					.SearchGenkaGenryoData(intShisakuSeq).get(0);

			// 試作列データの取得
			TrialData trialData = (TrialData) this.SearchShisakuRetuData(
					intShisakuSeq).get(0);

			// 既に依頼されているデータの計算は行わない
			if (trialData.getFlg_init() == 1) {
				return costMaterialData.getStrZyutenSui();
			}

			// 工程パターンが「空白」の場合
			String ptKotei = SearchShisakuhinData().getStrPt_kotei();
			if (ptKotei == null || ptKotei.length() == 0) {
				return costMaterialData.getStrZyutenSui();
			}
			// 工程パターンが「空白」でない場合
			else {

				// 工程パターンのValue1取得
				String ptValue = DataCtrl.getInstance()
						.getLiteralDataKoteiPtn().selectLiteralVal1(ptKotei);

				// 容量単位からValue1取得
				String yoryoTani = DataCtrl.getInstance().getTrialTblData()
						.SearchShisakuhinData().getStrTani();
				String taniValue1 = "";
				if (yoryoTani == null || yoryoTani.length() == 0) {
					return costMaterialData.getStrZyutenSui();
				} else {
					taniValue1 = DataCtrl.getInstance().getLiteralDataTani()
							.selectLiteralVal1(yoryoTani);
				}

				// 工程パターンが「調味料１液タイプ」の場合-------------------------------------------------------------
				if (ptValue.equals(JwsConstManager.JWS_KOTEITYPE_1)) {
					// 処理続行
				}
				// 工程パターンが「調味料２液タイプ」の場合-------------------------------------------------------------
				else if (ptValue.equals(JwsConstManager.JWS_KOTEITYPE_2)) {
					return costMaterialData.getStrZyutenSui();
				}
				// 工程パターンが「その他・加食タイプ」の場合-------------------------------------------------------------
				else if (ptValue.equals(JwsConstManager.JWS_KOTEITYPE_3)) {
					return costMaterialData.getStrZyutenSui();
				}
			}

			// 充填量、製品容量、製品比重
			double dblZyutenryo = 0.0;
			double dblSeihinYoryo = checkNumericDouble(SearchShisakuhinData()
					.getStrYoryo());
			double dblSeihinHiju = checkNumericDouble(trialData.getStrHizyu());

			// --------------充填量を求める--------------

			// 製品容量×製品比重
			dblZyutenryo = dblSeihinYoryo * dblSeihinHiju;

			// MOD start 20121121 QP@20505 切り上げを四捨五入に変更
			// 少数桁数を調整
			// ret = SetShosuKeta(Double.toString(dblZyutenryo),2);
			ret = SetShosuKeta3(Double.toString(dblZyutenryo), 2);
			// MOD end 20121121 QP@20505 切り上げを四捨五入に変更

		} catch (ExceptionBase eb) {
			throw eb;

		} catch (Exception e) {
			e.printStackTrace();

			// エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("調味料1液タイプ　充填量の計算処理が失敗しました");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}
		return ret;

	}

	/*************************************************************************************************
	 * 
	 * 調味料2液タイプ　水相充填量の計算処理
	 * 
	 * @return 水相充填量
	 * @throws ExceptionBase
	 * 
	 *************************************************************************************************/
	public String KeisanSuisoZyuten(int intShisakuSeq) throws ExceptionBase {

		// 計算結果 : 水相充填量
		String ret = "";

		try {
			// 原価データ取得
			CostMaterialData costMaterialData = (CostMaterialData) this
					.SearchGenkaGenryoData(intShisakuSeq).get(0);

			// 試作列データの取得
			TrialData trialData = (TrialData) this.SearchShisakuRetuData(
					intShisakuSeq).get(0);

			// QP@20505 No.2 2012/09/05 TT H.SHIMA ADD -Start
			String taniValue1 = "";
			// QP@20505 No.2 2012/09/05 TT H.SHIMA ADD -End

			// 既に依頼されているデータの計算は行わない
			if (trialData.getFlg_init() == 1) {
				return costMaterialData.getStrZyutenSui();
			}

			// 工程パターンが「空白」の場合
			String ptKotei = SearchShisakuhinData().getStrPt_kotei();
			if (ptKotei == null || ptKotei.length() == 0) {
				return costMaterialData.getStrZyutenSui();
			}
			// 工程パターンが「空白」でない場合
			else {

				// 工程パターンのValue1取得
				String ptValue = DataCtrl.getInstance()
						.getLiteralDataKoteiPtn().selectLiteralVal1(ptKotei);

				// 容量単位からValue1取得
				String yoryoTani = DataCtrl.getInstance().getTrialTblData()
						.SearchShisakuhinData().getStrTani();

				// QP@20505 No.2 2012/09/05 TT H.SHIMA DEL -Start
				// String taniValue1 = "";
				// QP@20505 No.2 2012/09/05 TT H.SHIMA DEL -End

				if (yoryoTani == null || yoryoTani.length() == 0) {
					return costMaterialData.getStrZyutenSui();
				} else {
					taniValue1 = DataCtrl.getInstance().getLiteralDataTani()
							.selectLiteralVal1(yoryoTani);
				}

				// 工程パターンが「調味料１液タイプ」の場合-------------------------------------------------------------
				if (ptValue.equals(JwsConstManager.JWS_KOTEITYPE_1)) {
					return costMaterialData.getStrZyutenSui();
				}
				// 工程パターンが「調味料２液タイプ」の場合-------------------------------------------------------------
				else if (ptValue.equals(JwsConstManager.JWS_KOTEITYPE_2)) {

					// QP@20505 No.2 2012/09/05 TT H.SHIMA MOD -Start
					// 容量が「ml」「g」の場合
					// if(taniValue1.equals("1")){
					if (taniValue1.equals("1") || taniValue1.equals("2")) {
						// 処理続行
					}
					// QP@20505 No.2 2012/09/05 TT H.SHIMA MOD -End

					// 容量が「空白」の場合（ml以外の場合）
					else {
						return costMaterialData.getStrZyutenSui();
					}
				}
				// 工程パターンが「その他・加食タイプ」の場合-------------------------------------------------------------
				else if (ptValue.equals(JwsConstManager.JWS_KOTEITYPE_3)) {
					return costMaterialData.getStrZyutenSui();
				}
			}

			// 配合データの取得
			ArrayList aryHaigoData = this.SearchHaigoData(0);

			// 殺菌調味液重量、ドレ水相合計重量、ドレ油相合計重量、水相比重、油相比重、製品容量、水相充填量
			double dblSakinGokeiryo = 0.0;
			double dblSuisoGokeiryo = 0.0;
			double dblYusoGokeiryo = 0.0;
			double dblSuisoHiju = checkNumericDouble(trialData.getStrHiju_sui());
			double dblYusoHiju = checkNumericDouble(DataCtrl.getInstance()
					.getLiteralDataYusoHiju().getAryBiko().get(0));
			double dblSeihinYoryo = checkNumericDouble(SearchShisakuhinData()
					.getStrYoryo());
			double dblSuisoZyuten = 0.0;

			// リテラル値
			String strLiteralVal1 = "";
			String strLiteralVal2 = "";

			// リスト件数分ループ
			for (int i = 0; i < aryHaigoData.size(); i++) {

				// 配合データの取得
				MixedData haigoData = (MixedData) aryHaigoData.get(i);

				// 工程属性の取得
				String strZokusei = haigoData.getStrKouteiZokusei();

				// 工程が選ばれている場合
				if (strZokusei != null) {

					// リテラル値の取得
					strLiteralVal1 = DataCtrl.getInstance()
							.getLiteralDataZokusei()
							.selectLiteralVal1(strZokusei);
					strLiteralVal2 = DataCtrl.getInstance()
							.getLiteralDataZokusei()
							.selectLiteralVal2(strZokusei);

					// ----工程合計重量を求める----------------------

					// 配合データより値の取得

					// 工程CD
					int intKoteiCd = checkNullInt(""
							+ haigoData.getIntKoteiCd());
					// 工程SEQ
					int intKoteiSeq = checkNullInt(""
							+ haigoData.getIntKoteiSeq());

					// ----試作リストデータより、配合量を取得処理を行う----------------------

					// 配合量
					double dblRyo = 0.0;

					Iterator ite = this.getAryShisakuList().iterator();

					// リスト件数分ループ
					while (ite.hasNext()) {
						// 試作リストデータオブジェクト取得
						PrototypeListData shisakuRetu = (PrototypeListData) ite
								.next();

						// 試作SEQ、工程CD、工程SEQが一致した場合
						if (intShisakuSeq == shisakuRetu.getIntShisakuSeq()
								&& intKoteiCd == shisakuRetu.getIntKoteiCd()
								&& intKoteiSeq == shisakuRetu.getIntKoteiSeq()) {

							// 配合量を取得
							dblRyo = checkNumericDouble(shisakuRetu.getDciRyo());

							// ループを抜ける
							break;

						}

					}

					// ----リテラル値2による分岐処理----------------------
					// 1:殺菌調味液
					if (strLiteralVal2.equals("1")) {

						// 殺菌調味液の合計重量を求める
						dblSakinGokeiryo += dblRyo;

					}
					// 2:水相
					else if (strLiteralVal2.equals("2")) {

						// 水相の合計重量を求める
						dblSuisoGokeiryo += dblRyo;

					}
					// 3:油相
					else if (strLiteralVal2.equals("3")) {

						// 油相の合計重量を求める
						dblYusoGokeiryo += dblRyo;

					}

				}
			}

			// --------------水相充填量を求める--------------
			// QP@20505 No.2 2012/09/05 TT H.SHIMA MOD -Start
			// 容量が「ml」の場合
			if (taniValue1.equals("1")) {

				// ( 殺菌調味液重量 ＋ ドレ水相合計重量 ) × ( 製品容量 ÷ ( ( ( 殺菌調味液重量 ＋ ドレ水相合計重量 )
				// ÷ 水相比重 ) ＋ ( ドレ油相合計重量 ÷ 油相比重 ) ) )
				dblSuisoZyuten = (dblSakinGokeiryo + dblSuisoGokeiryo)
						* (dblSeihinYoryo / (((dblSakinGokeiryo + dblSuisoGokeiryo) / dblSuisoHiju) + (dblYusoGokeiryo / dblYusoHiju)));

			}
			// 容量が「g」の場合
			else if (taniValue1.equals("2")) {

				// ( 殺菌調味液重量 ＋ ドレ水相合計重量 ) × ( 製品容量 ÷ ( ( 殺菌調味液重量 ＋ ドレ水相合計重量 ) ＋
				// ( ドレ油相合計重量 ) )
				dblSuisoZyuten = (dblSakinGokeiryo + dblSuisoGokeiryo)
						* (dblSeihinYoryo / ((dblSakinGokeiryo + dblSuisoGokeiryo) + dblYusoGokeiryo));

			}
			// それ以外の場合
			else {

				throw new Exception();

			}
			// QP@20505 No.2 2012/09/05 TT H.SHIMA MOD -End

			// MOD start 20121121 QP@20505 切り上げを四捨五入に変更
			// 少数桁数を調整
			// ret = SetShosuKeta(Double.toString(dblSuisoZyuten),2);
			ret = SetShosuKeta3(Double.toString(dblSuisoZyuten), 2);
			// MOD end 20121121 QP@20505 切り上げを四捨五入に変更

		} catch (ExceptionBase eb) {
			throw eb;

		} catch (Exception e) {
			// エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("調味料2液タイプ　水相充填量の計算処理が失敗しました");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}
		return ret;

	}

	/*************************************************************************************************
	 * 
	 * 調味料2液タイプ　油相充填量の計算処理
	 * 
	 * @return 油相充填量
	 * @throws ExceptionBase
	 * 
	 *************************************************************************************************/
	public String KeisanYusoZyuten(int intShisakuSeq) throws ExceptionBase {

		// 計算結果 : 油相充填量
		String ret = "";

		try {
			// 原価データ取得
			CostMaterialData costMaterialData = (CostMaterialData) this
					.SearchGenkaGenryoData(intShisakuSeq).get(0);

			// 試作列データの取得
			TrialData trialData = (TrialData) this.SearchShisakuRetuData(
					intShisakuSeq).get(0);

			// QP@20505 No.2 2012/09/05 TT H.SHIMA ADD -Start
			String taniValue1 = "";
			// QP@20505 No.2 2012/09/05 TT H.SHIMA ADD -End

			// 既に依頼されているデータの計算は行わない
			if (trialData.getFlg_init() == 1) {
				return costMaterialData.getStrZyutenYu();
			}

			// 工程パターンが「空白」の場合
			String ptKotei = SearchShisakuhinData().getStrPt_kotei();
			if (ptKotei == null || ptKotei.length() == 0) {
				return costMaterialData.getStrZyutenYu();
			}
			// 工程パターンが「空白」でない場合
			else {

				// 工程パターンのValue1取得
				String ptValue = DataCtrl.getInstance()
						.getLiteralDataKoteiPtn().selectLiteralVal1(ptKotei);

				// 容量単位からValue1取得
				String yoryoTani = DataCtrl.getInstance().getTrialTblData()
						.SearchShisakuhinData().getStrTani();

				// QP@20505 No.2 2012/09/05 TT H.SHIMA DEL -Start
				// String taniValue1 = "";
				// QP@20505 No.2 2012/09/05 TT H.SHIMA DEL -End

				if (yoryoTani == null || yoryoTani.length() == 0) {
					return costMaterialData.getStrZyutenYu();
				} else {
					taniValue1 = DataCtrl.getInstance().getLiteralDataTani()
							.selectLiteralVal1(yoryoTani);
				}

				// 工程パターンが「調味料１液タイプ」の場合-------------------------------------------------------------
				if (ptValue.equals(JwsConstManager.JWS_KOTEITYPE_1)) {
					return costMaterialData.getStrZyutenYu();
				}
				// 工程パターンが「調味料２液タイプ」の場合-------------------------------------------------------------
				else if (ptValue.equals(JwsConstManager.JWS_KOTEITYPE_2)) {

					// QP@20505 No.2 2012/09/05 TT H.SHIMA MOD -Start
					// 容量が「ml」の場合
					// if(taniValue1.equals("1")){
					if (taniValue1.equals("1") || taniValue1.equals("2")) {
						// 処理続行
					}
					// QP@20505 No.2 2012/09/05 TT H.SHIMA MOD -End

					// 容量が「空白」の場合（ml以外の場合）
					else {
						return costMaterialData.getStrZyutenYu();
					}
				}
				// 工程パターンが「その他・加食タイプ」の場合-------------------------------------------------------------
				else if (ptValue.equals(JwsConstManager.JWS_KOTEITYPE_3)) {
					return costMaterialData.getStrZyutenYu();
				}
			}

			// 配合データの取得
			ArrayList aryHaigoData = this.SearchHaigoData(0);

			// 殺菌調味液重量、ドレ水相合計重量、ドレ油相合計重量、水相比重、油相比重、製品容量、油相充填量
			double dblSakinGokeiryo = 0.0;
			double dblSuisoGokeiryo = 0.0;
			double dblYusoGokeiryo = 0.0;
			double dblSuisoHiju = checkNumericDouble(trialData.getStrHiju_sui());
			double dblYusoHiju = checkNumericDouble(DataCtrl.getInstance()
					.getLiteralDataYusoHiju().getAryBiko().get(0));
			double dblSeihinYoryo = checkNumericDouble(SearchShisakuhinData()
					.getStrYoryo());
			double dblYusoZyuten = 0.0;

			// リテラル値
			String strLiteralVal1 = "";
			String strLiteralVal2 = "";

			// リスト件数分ループ
			for (int i = 0; i < aryHaigoData.size(); i++) {

				// 配合データの取得
				MixedData haigoData = (MixedData) aryHaigoData.get(i);

				// 工程属性の取得
				String strZokusei = haigoData.getStrKouteiZokusei();

				// 工程が選ばれている場合
				if (strZokusei != null) {

					// リテラル値の取得
					strLiteralVal1 = DataCtrl.getInstance()
							.getLiteralDataZokusei()
							.selectLiteralVal1(strZokusei);
					strLiteralVal2 = DataCtrl.getInstance()
							.getLiteralDataZokusei()
							.selectLiteralVal2(strZokusei);

					// ----工程合計重量を求める----------------------

					// 配合データより値の取得

					// 工程CD
					int intKoteiCd = checkNullInt(""
							+ haigoData.getIntKoteiCd());
					// 工程SEQ
					int intKoteiSeq = checkNullInt(""
							+ haigoData.getIntKoteiSeq());

					// ----試作リストデータより、配合量を取得処理を行う----------------------

					// 配合量
					double dblRyo = 0.0;

					Iterator ite = this.getAryShisakuList().iterator();

					// リスト件数分ループ
					while (ite.hasNext()) {
						// 試作リストデータオブジェクト取得
						PrototypeListData shisakuRetu = (PrototypeListData) ite
								.next();

						// 試作SEQ、工程CD、工程SEQが一致した場合
						if (intShisakuSeq == shisakuRetu.getIntShisakuSeq()
								&& intKoteiCd == shisakuRetu.getIntKoteiCd()
								&& intKoteiSeq == shisakuRetu.getIntKoteiSeq()) {

							// 配合量を取得
							dblRyo = checkNumericDouble(shisakuRetu.getDciRyo());

							// ループを抜ける
							break;

						}

					}

					// ----リテラル値2による分岐処理----------------------
					// 1:殺菌調味液
					if (strLiteralVal2.equals("1")) {

						// 殺菌調味液の合計重量を求める
						dblSakinGokeiryo += dblRyo;

					}
					// 2:水相
					else if (strLiteralVal2.equals("2")) {

						// 水相の合計重量を求める
						dblSuisoGokeiryo += dblRyo;

					}
					// 3:油相
					else if (strLiteralVal2.equals("3")) {

						// 油相の合計重量を求める
						dblYusoGokeiryo += dblRyo;

					}

				}
			}

			// --------------油相充填量を求める--------------
			// QP@20505 No.2 2012/09/05 TT H.SHIMA MOD -Start
			// 容量が「ml」の場合
			if (taniValue1.equals("1")) {

				// ドレ油相合計重量 × ( 製品容量 ÷ ( ( ( 殺菌調味液重量 ＋ ドレ水相合計重量 ) ÷ 水相比重 ) ＋ (
				// ドレ油相合計重量 ÷ 油相比重 ) ) )
				dblYusoZyuten = dblYusoGokeiryo
						* (dblSeihinYoryo / (((dblSakinGokeiryo + dblSuisoGokeiryo) / dblSuisoHiju) + (dblYusoGokeiryo / dblYusoHiju)));

			}
			// 容量が「g」の場合
			else if (taniValue1.equals("2")) {

				// ドレ油相合計重量 × ( 製品容量 ÷ ( ( 殺菌調味液重量 ＋ ドレ水相合計重量 ) ＋ ( ドレ油相合計重量 ) )
				dblYusoZyuten = dblYusoGokeiryo
						* (dblSeihinYoryo / ((dblSakinGokeiryo + dblSuisoGokeiryo) + dblYusoGokeiryo));

			}
			// それ以外の場合
			else {

				throw new Exception();

			}
			// QP@20505 No.2 2012/09/05 TT H.SHIMA MOD -End

			// MOD start 20121121 QP@20505 切り上げを四捨五入に変更
			// 少数桁数を調整
			// ret = SetShosuKeta(Double.toString(dblYusoZyuten),2);
			ret = SetShosuKeta3(Double.toString(dblYusoZyuten), 2);
			// MOD end 20121121 QP@20505 切り上げを四捨五入に変更

		} catch (ExceptionBase eb) {
			throw eb;

		} catch (Exception e) {
			// エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("調味料2液タイプ　油相充填量の計算処理が失敗しました");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}
		return ret;

	}

	// 2011/05/11 QP@10181_No.42_49_72 TT T.Nishigawa Add End
	// -------------------------

	/*************************************************************************************************
	 * 
	 * 配合データ配列　原料順並び替え
	 * 
	 * @return なし
	 * @throws ExceptionBase
	 * 
	 *************************************************************************************************/
	public void sortAryHaigo() throws ExceptionBase {
		try {
			ArrayList aryHaigoCopy = new ArrayList();

			// 最大原料順取得
			int m_genryo = 0;
			for (int i = 0; i < aryHaigou.size(); i++) {

				// 配合データの取得
				MixedData haigoData = (MixedData) aryHaigou.get(i);

				// 原料順設定
				if (m_genryo < haigoData.getIntGenryoNo()) {
					m_genryo = haigoData.getIntGenryoNo();
				}
			}

			// 配列並び替え
			for (int i = 0; i <= m_genryo; i++) {

				// 配合データ配列ループ
				for (int j = 0; j < aryHaigou.size(); j++) {

					// 配合データの取得
					MixedData haigoData = (MixedData) aryHaigou.get(j);

					// 配合データ追加
					if (i == haigoData.getIntGenryoNo()) {
						aryHaigoCopy.add(haigoData);
					}
				}
			}

			// コピーデータ設定
			aryHaigou = null;
			aryHaigou = aryHaigoCopy;

		} catch (Exception e) {

			e.printStackTrace();

			// エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("配合データ配列　原料順並び替え処理が失敗しました");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;
		}
	}

	/*************************************************************************************************
	 * 
	 * 試作データ配列　試作順並び替え
	 * 
	 * @return なし
	 * @throws ExceptionBase
	 * 
	 *************************************************************************************************/
	public void sortAryShisaku() throws ExceptionBase {
		try {
			ArrayList aryShisakuCopy = new ArrayList();

			// 最大試作順取得
			int m_shisaku = 0;
			for (int i = 0; i < aryShisakuRetu.size(); i++) {

				// 試作データの取得
				TrialData shisakuData = (TrialData) aryShisakuRetu.get(i);

				// 試作順設定
				if (m_shisaku < shisakuData.getIntHyojiNo()) {
					m_shisaku = shisakuData.getIntHyojiNo();
				}
			}

			// 配列並び替え
			for (int i = 0; i <= m_shisaku; i++) {

				// 試作データ配列ループ
				for (int j = 0; j < aryShisakuRetu.size(); j++) {

					// 試作データの取得
					TrialData shisakuData = (TrialData) aryShisakuRetu.get(j);

					// 試作データ追加
					if (i == shisakuData.getIntHyojiNo()) {
						aryShisakuCopy.add(shisakuData);
					}
				}
			}

			// コピーデータ設定
			aryShisakuRetu = null;
			aryShisakuRetu = aryShisakuCopy;

		} catch (Exception e) {

			e.printStackTrace();

			// エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("試作データ配列　試作順並び替え処理が失敗しました");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;
		}
	}

	/*************************************************************************************************
	 * 
	 * 試作リストデータ配列　試作リスト順並び替え
	 * 
	 * @return なし
	 * @throws ExceptionBase
	 * 
	 *************************************************************************************************/
	public void sortAryShisakuList() throws ExceptionBase {
		try {
			ArrayList aryShisakuListCopy = new ArrayList();

			// 最大原料順取得
			int m_genryo = 0;
			for (int i = 0; i < aryHaigou.size(); i++) {

				// 配合データの取得
				MixedData haigoData = (MixedData) aryHaigou.get(i);

				// 原料順設定
				if (m_genryo < haigoData.getIntGenryoNo()) {
					m_genryo = haigoData.getIntGenryoNo();
				}
			}

			// 最大試作順取得
			int m_shisaku = 0;
			for (int i = 0; i < aryShisakuRetu.size(); i++) {

				// 試作データの取得
				TrialData shisakuData = (TrialData) aryShisakuRetu.get(i);

				// 試作順設定
				if (m_shisaku < shisakuData.getIntHyojiNo()) {
					m_shisaku = shisakuData.getIntHyojiNo();
				}
			}

			// 配列並び替え
			for (int i = 0; i <= m_shisaku; i++) {

				// 試作データ配列ループ
				for (int j = 0; j < aryShisakuRetu.size(); j++) {

					// 試作データの取得
					TrialData shisakuData = (TrialData) aryShisakuRetu.get(j);

					// 試作データ追加
					if (i == shisakuData.getIntHyojiNo()) {

						// 試作SEQ取得
						int shisaku_seq = shisakuData.getIntShisakuSeq();

						// 配列並び替え
						for (int k = 0; k <= m_genryo; k++) {

							// 配合データ配列ループ
							for (int l = 0; l < aryHaigou.size(); l++) {

								// 配合データの取得
								MixedData haigoData = (MixedData) aryHaigou
										.get(l);

								// 配合データ追加
								if (k == haigoData.getIntGenryoNo()) {

									// 工程CD取得
									int kotei_cd = haigoData.getIntKoteiCd();

									// 工程SEQ取得
									int kotei_seq = haigoData.getIntKoteiSeq();

									// 試作リスト取得
									PrototypeListData PrototypeListData = this
											.searchShisakuListData(shisaku_seq,
													kotei_cd, kotei_seq);
									aryShisakuListCopy.add(PrototypeListData);
								}
							}
						}
					}
				}
			}

			// コピーデータ設定
			aryShisakuList = null;
			aryShisakuList = aryShisakuListCopy;

		} catch (Exception e) {

			e.printStackTrace();

			// エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("試作データ配列　試作順並び替え処理が失敗しました");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;
		}
	}

	/**
	 * 試作リストデータ配合量検索 : 試作リストより配合量を検索（全コピー処理で使用）
	 * 
	 * @param intShisakuSeq
	 *            : 試作ＳＥＱ
	 * @param intKouteiCd
	 *            : 工程ＣＤ
	 * @param intKouteiSeq
	 *            : 工程ＳＥＱ
	 * @throws ExceptionBase
	 */
	public PrototypeListData searchShisakuListData(int intShisakuSeq,
			int intKouteiCd, int intKouteiSeq) throws ExceptionBase {

		PrototypeListData ret = null;

		try {
			Iterator ite = aryShisakuList.iterator();
			// リスト件数分ループ
			while (ite.hasNext()) {

				// 試作リストデータオブジェクト取得
				PrototypeListData PrototypeListData = (PrototypeListData) ite
						.next();

				// 試作SEQ・工程CD・工程SEQが一致
				if (intShisakuSeq == PrototypeListData.getIntShisakuSeq()
						&& intKouteiCd == PrototypeListData.getIntKoteiCd()
						&& intKouteiSeq == PrototypeListData.getIntKoteiSeq()) {

					// 配合量設定
					ret = PrototypeListData;

				}
			}

		} catch (Exception e) {

		} finally {

		}
		return ret;
	}

	// ADD 20130408 start 原料行削除後、試作列コピーすると、工程SEQが取得できない不具合の修正
	/**
	 * 試作リストデータ配合量検索（試作列コピー版） : 試作リストより配合量を検索（試作列コピー処理で使用する場合に、工程SEQを特定しない）
	 * 
	 * @param intShisakuSeq
	 *            : 試作ＳＥＱ
	 * @param intKouteiCd
	 *            : 工程ＣＤ
	 * @param intKouteiSeq
	 *            : 工程ＳＥＱ
	 * @throws ExceptionBase
	 */
	public PrototypeListData searchShisakuListData_Copy(int intShisakuSeq,
			int intKouteiCd) throws ExceptionBase {
		PrototypeListData ret = null;
		try {
			Iterator ite = aryShisakuList.iterator();
			// リスト件数分ループ
			while (ite.hasNext()) {
				// 試作リストデータオブジェクト取得
				PrototypeListData PrototypeListData = (PrototypeListData) ite
						.next();

				// 試作SEQ・工程CDが一致
				if (intShisakuSeq == PrototypeListData.getIntShisakuSeq()
						&& intKouteiCd == PrototypeListData.getIntKoteiCd()) {
					// 配合量設定
					ret = PrototypeListData;
				}
			}
		} catch (Exception e) {
		} finally {
		}
		return ret;
	}

	// ADD 20130408 end

	/**
	 * 0055.配合原料名称取得 : 指定原料データの原料名称を取得する
	 * 
	 * @param intKouteiCd
	 *            : 工程CD
	 * @param intKouteiSeq
	 *            : 工程SEQ
	 * @return 原料名
	 */
	public String SearchHaigoGenryoMei(int intKouteiCd, int intKouteiSeq)
			throws ExceptionBase {

		String ret = "";

		try {

			Iterator ite = aryHaigou.iterator();

			// リスト件数分ループ
			while (ite.hasNext()) {

				// 配合データオブジェクト取得
				MixedData mixData = (MixedData) ite.next();

				// 工程CD・工程SEQが一致
				if (intKouteiCd == mixData.getIntKoteiCd()
						&& intKouteiSeq == mixData.getIntKoteiSeq()) {

					// 原料名称設定
					ret = mixData.getStrGenryoNm();
				}
			}

		} catch (Exception e) {

			// エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("試作テーブルデータ保持の配合原料名称更新が失敗しました");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

		return ret;

	}

	/*************************************************************************************************
	 * 
	 * 試作リストデータ 工程コード取得
	 * 
	 * @param intShisakuSeq
	 *            ：試作SEQ
	 * @param intKoteiRow
	 *            ：工程番号
	 * @return 工程コード
	 * @throws ExceptionBase
	 * 
	 *************************************************************************************************/
	public int getSerchKoteiCode(int intShisakuSeq, int intKoteiRow)
			throws ExceptionBase {

		int ret = 0;

		try {
			ArrayList koteiCodeList = new ArrayList();
			ArrayList uniqueKoteiCodeList = new ArrayList();

			Iterator ite = aryShisakuList.iterator();

			// リスト件数分ループ
			while (ite.hasNext()) {
				PrototypeListData prototypeListData = (PrototypeListData) ite
						.next();

				// 試作SEQが一致
				if (intShisakuSeq == prototypeListData.getIntShisakuSeq()) {

					// 工程コード設定
					koteiCodeList.add(new Integer(prototypeListData
							.getIntKoteiCd()));
				}
			}

			// 工程コードを一意に
			Set set = new LinkedHashSet();
			set.addAll(koteiCodeList);
			uniqueKoteiCodeList.addAll(set);

			// 工程番号に一致する工程コードをretに設定
			ret = Integer.parseInt(String.valueOf(uniqueKoteiCodeList
					.get(intKoteiRow - 1)));

		} catch (Exception e) {

			// エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("配合表工程コードの取得が失敗しました");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

		return ret;
	}

	/*************************************************************************************************
	 * 
	 * 実効酢酸濃度の計算処理
	 * 
	 * @return 実効酢酸濃度
	 * @throws ExceptionBase
	 * 
	 *************************************************************************************************/
	public BigDecimal KeisanJikkoSakusanNodo(int _intShisakuSeq)
			throws ExceptionBase {

		// 計算結果 : 実効酢酸濃度
		BigDecimal ret = new BigDecimal("0.00");
		// 試作列(Start:1)
		int intShisakuSeq = _intShisakuSeq;

		try {
			// 試作列データの取得
			TrialData trialData = (TrialData) this.SearchShisakuRetuData(
					intShisakuSeq).get(0);

			// 既に依頼されているデータの計算は行わない
			if (trialData.getFlg_init() == 1) {
				return ret;
			}

			// 工程パターンが「空白」の場合
			String ptKotei = SearchShisakuhinData().getStrPt_kotei();
			if (ptKotei == null || ptKotei.length() == 0) {
				return ret;
			} else
			// 工程パターンが「その他・加食タイプ」の場合
			if (ptKotei.equals(JwsConstManager.JWS_KOTEITYPE_3)) {
				return ret;
			}

			// 配合データ取得
			ArrayList HaigoData = this.SearchHaigoData(0);

			// ------------------------ 計算必要項目
			// -----------------------------------
			// ①合計重量(g)
			BigDecimal goleiZyuryo = new BigDecimal("0.00");
			// ②油含有合計量
			BigDecimal goleiGanyu = new BigDecimal("0.00");
			// ③酢酸合計量
			BigDecimal goleiSakusan = new BigDecimal("0.00");
			// ⑥ＭＳＧ合計量
			BigDecimal goleiMsg = new BigDecimal("0.00");

			Iterator ite = this.getAryShisakuList().iterator();

			int i = 0;
			// リスト件数分ループ
			while (ite.hasNext()) {
				// 試作リストデータオブジェクト取得
				PrototypeListData shisakuRetu = (PrototypeListData) ite.next();

				// 試作SEQ、工程CD、工程SEQが一致した場合
				if (intShisakuSeq == shisakuRetu.getIntShisakuSeq()) {

					// 配合量を取得
					BigDecimal ryoVal = shisakuRetu.getDciRyo();
					// 配合量
					BigDecimal bdRyo = new BigDecimal("0.000");
					if (ryoVal != null) {
						bdRyo = new BigDecimal(ryoVal.toString());
					}

					// ①合計量
					if (shisakuRetu.getDciRyo() != null) {
						goleiZyuryo = goleiZyuryo.add(shisakuRetu.getDciRyo());
					}
					// 配合データの取得
					MixedData haigoData = (MixedData) HaigoData.get(i);

					// ②油含有合計量加算
					if (haigoData.getDciGanyuritu() != null) {
						goleiGanyu = goleiGanyu.add(bdRyo.multiply(haigoData
								.getDciGanyuritu().multiply(
										new BigDecimal("0.01"))));
					}
					// ③酢酸合計量加算
					if (haigoData.getDciSakusan() != null) {
						goleiSakusan = goleiSakusan.add(bdRyo
								.multiply(haigoData.getDciSakusan()));
					}
					// ⑥ＭＳＧ合計量加算
					if (haigoData.getDciMsg() != null) {
						goleiMsg = goleiMsg.add(bdRyo.multiply(haigoData
								.getDciMsg()));
					}

					// カウントアップ
					i++;
				}
			}

			// --------------------------
			// 水相中酢酸計算処理------------------------------
			// ③酢酸合計量/（①合計量ー②油含有合計量）
			BigDecimal sui_sakusan = new BigDecimal("0.00");
			if (goleiSakusan.intValue() > 0
					&& (goleiZyuryo.subtract(goleiGanyu)).intValue() > 0) {
				sui_sakusan = goleiSakusan.divide(
						(goleiZyuryo.subtract(goleiGanyu)), 2,
						BigDecimal.ROUND_HALF_UP);
			}

			// ⑥ＭＳＧ合計量/（①合計量－②油含有合計量）
			BigDecimal sui_msg = new BigDecimal("0.00");
			if (goleiMsg.intValue() > 0
					&& (goleiZyuryo.subtract(goleiGanyu)).intValue() > 0) {
				sui_msg = goleiMsg.divide((goleiZyuryo.subtract(goleiGanyu)),
						2, BigDecimal.ROUND_HALF_UP);
			}

			// 水相中酢酸－水相中ＭＳＧ×(0.5791×ｐＨ－1.9104)/ 187.13×60
			double dblJsnKoteiValue1 = checkNumericDouble(DataCtrl
					.getInstance().getLiteralDataJikkoSakusanNodo()
					.getAryBiko().get(0));
			double dblJsnKoteiValue2 = checkNumericDouble(DataCtrl
					.getInstance().getLiteralDataJikkoSakusanNodo()
					.getAryBiko().get(1));
			double dblJsnKoteiValue3 = checkNumericDouble(DataCtrl
					.getInstance().getLiteralDataJikkoSakusanNodo()
					.getAryBiko().get(2));
			double dblJsnKoteiValue4 = checkNumericDouble(DataCtrl
					.getInstance().getLiteralDataJikkoSakusanNodo()
					.getAryBiko().get(3));
			String strPh = "";
			BigDecimal ph = new BigDecimal("0.00");
			BigDecimal dci_sui_sakusan = new BigDecimal(sui_sakusan.toString());
			BigDecimal kotei1 = new BigDecimal(
					String.valueOf(dblJsnKoteiValue1)); // 0.5791
			BigDecimal kotei2 = new BigDecimal(
					String.valueOf(dblJsnKoteiValue2)); // 1.9104
			BigDecimal kotei3 = new BigDecimal(
					String.valueOf(dblJsnKoteiValue3)); // 187.13
			BigDecimal kotei4 = new BigDecimal(
					String.valueOf(dblJsnKoteiValue4)); // 60
			ArrayList aryTrialData = aryShisakuRetu;
			int cntList = aryTrialData.size();
			TrialData TrialData = new TrialData();
			for (int k = 0; k < cntList; k++) {
				TrialData = ((TrialData) aryTrialData.get(k)); // 試作列データ
				if (TrialData.getIntShisakuSeq() == intShisakuSeq) {
					strPh = TrialData.getStrPh();
				}
			}
			if (strPh == null) {
			} else {
				if (strPh.length() > 0) {
					// 2013/04/01 MOD Start
					// ph = new BigDecimal(strPh);
					ph = new BigDecimal(strPh.replaceAll(" ", "").replaceAll(
							"　", ""));
					// 2013/04/01 MOD End
				}
			}
			// 計算
			BigDecimal jsn = new BigDecimal("0.00");
			jsn = ph.multiply(kotei1); // 0.5791×ph
			jsn = jsn.subtract(kotei2); // jsn－1.9104
			if (sui_msg.doubleValue() > 0 && jsn.doubleValue() > 0) {
				jsn = jsn.multiply(sui_msg); // jsn×水相中MSG
				jsn = jsn.divide(kotei3, 4, BigDecimal.ROUND_HALF_UP); // jsn÷187.13
				jsn = jsn.multiply(kotei4); // jsn×60
				jsn = dci_sui_sakusan.subtract(jsn); // 水相中酢酸－jsn
				if (jsn.doubleValue() > 0) {
					jsn = jsn.setScale(2, BigDecimal.ROUND_HALF_UP);
				} else {
					// 最後の減算でマイナス値になる場合
					jsn = new BigDecimal("0.00");
				}
			} else {
				// 割り算する前に0値があった場合
				jsn = new BigDecimal("0.00");
			}

			ret = jsn;

		} catch (ExceptionBase eb) {
			throw eb;

		} catch (Exception e) {
			// エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("実効酢酸濃度の計算処理が失敗しました");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}
		return ret;

	}

	// データ変更チェック：ADD start
	/*******************************************************************************************
	 * 
	 * データ変更チェック ：データが変更されている時、変更フラグをtrue にする
	 * 
	 * @param getData
	 *            　：　変更前データ
	 * @param setData
	 *            　：　変更後データ
	 * @author TT kitazawa
	 * 
	 ******************************************************************************************/
	private void chkHenkouData(int getData, int setData) {

		if (getData != setData) {
			// データ変更
			HenkouFg = true;
		}
	}

	private void chkHenkouData(String getData, String setData) {

		if (getData == null) {
			if (setData == null) {
				//
			} else {
				// データ変更
				HenkouFg = true;
			}

		} else if (!getData.equals(setData)) {
			// データ変更
			HenkouFg = true;
		}
	}

	private void chkHenkouData(BigDecimal getData, BigDecimal setData) {

		if (getData == null) {
			if (setData == null) {
				//
			} else {
				// データ変更
				HenkouFg = true;
			}

		} else {

			// 空文字チェック
			if (setData == null) {
				// データ変更
				HenkouFg = true;

			} else {
				if (getData.compareTo(setData) != 0) {
					// データ変更
					HenkouFg = true;
				}
			}
		}
	}

	// Stringデータを数値比較
	private void chkHenkouDecData(String getData, String setData) {

		if (getData == null) {
			if (setData == null) {
				//
			} else {
				// データ変更
				HenkouFg = true;
			}

		} else {
			// 空文字チェック
			BigDecimal getDecData = checkNullDecimal(getData);
			BigDecimal setDecData = checkNullDecimal(setData);

			// 数値文字列以外の時、nullが返る為
			if (getDecData == null || setDecData == null) {
				// データ変更
				HenkouFg = true;

			} else {
				if (getDecData.compareTo(setDecData) != 0) {
					// データ変更
					HenkouFg = true;
				}
			}
		}
	}

	// データ変更チェック：ADD end

}