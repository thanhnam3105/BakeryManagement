package jp.co.blueflag.shisaquick.srv.inputcheck;

import java.math.BigDecimal;

import jp.co.blueflag.shisaquick.srv.base.InputCheck;
import jp.co.blueflag.shisaquick.srv.base.RequestData;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;
import jp.co.blueflag.shisaquick.srv.common.ExceptionManager.ExceptionKind;

import org.springframework.util.StringUtils;

/**
 *
 * 【JW030】 試作データ画面リクエスト登録（新規） インプットチェック
 *
 */
public class ShisakuTourokuShinkiInputCheck extends InputCheck {

	/**
	 * コンストラクタ
	 *  : 試作データ画面 初期表示時インプットチェック用コンストラクタ
	 */
	public ShisakuTourokuShinkiInputCheck() {
		//基底クラスのコンストラクタ
		super();

	}

	/**
	 * インプットチェック管理
	 *  : 各データチェック処理を管理する。
	 * @param requestData : リクエストデータ
	 * @param userInfoData : ユーザー情報
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	public void execInputCheck(
			RequestData checkData
			,UserInfoData _userInfoData
			)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		//ユーザー情報退避
		super.userInfoData = _userInfoData;

		try {
			//USERINFOのインプットチェックを行う。
			super.userInfoCheck(checkData);

//2009/09/30 TT.A.ISONO DEL START [試作CD追番は登録時に採番するよう変更する。]
//試作データの排他制御は、登録時に行うため、SA420はカット

			//SA420のインプットチェックを行う。
			//exclusiveControlKeyCheck(checkData);

//2009/09/30 TT.A.ISONO DEL END   [試作CD追番は登録時に採番するよう変更する。]

			//SA490のインプットチェックを行う。
			insertValueCheck(checkData);

		} catch (Exception e) {
			this.em.ThrowException(e, "");

		} finally {

		}
	}

//	/**
//	 * 排他制御項目インプットチェック
//	 *  : SA420のインプットチェックを行う。
//	 * @param requestData : リクエストデータ
//	 * @throws ExceptionWaning
//	 * @throws ExceptionUser
//	 * @throws ExceptionSystem
//	 */
//	private void exclusiveControlKeyCheck(RequestData checkData) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
//
//		try {
//			//実行区分＝1（処理する）の場合
//			if (checkData.GetValueStr("SA420", 0, 0, "kubun_ziko").equals("1")) {
//				//ユーザIDの必須チェックを行う。
////			    super.hissuInputCheck(checkData.GetValueStr("SA420", 0, 0, "id_user"),"ユーザID");
//
//			    //排他区分の必須チェックを行う。
//			    super.hissuInputCheck(checkData.GetValueStr("SA420", 0, 0, "kubun_haita"),"排他区分");
//				//試作コードの必須チェックを行う。
//			    super.hissuInputCheck(checkData.GetValueStr("SA420", 0, 0, "cd_shain"),"試作CD-社員CD");
//			    super.hissuInputCheck(checkData.GetValueStr("SA420", 0, 0, "nen"),"試作CD-年");
//			    super.hissuInputCheck(checkData.GetValueStr("SA420", 0, 0, "no_oi"),"試作CD-追番");
//
//			}
//		} catch (Exception e) {
//			this.em.ThrowException(e, "");
//		} finally {
//
//		}
//	}

	/**
	 * 登録項目チェック
	 *  : SA490のインプットチェックを行う。
	 * @param requestData : リクエストデータ
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private void insertValueCheck(RequestData checkData) throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		try {
			//データ件数チェック（配合量セル数チェック）
			super.DataCellCheck(checkData);

			//テーブル：T110試作品テーブルのインプットチェック（試作品テーブル項目チェック）を行う。
			this.shisakuhinInserValueCheck(checkData);
			//テーブル：T120配合テーブルのインプットチェック（配合テーブル項目チェック）を行う。
			this.haigoInsertValueCheck(checkData);
			//テーブル：T131試作テーブルのインプットチェック（試作テーブル項目チェック）を行う。
			this.shisakuInsertValueCheck(checkData);
			//テーブル：T132試作リストテーブルのインプットチェック（試作リストテーブル項目チェック）を行う。
			this.shisakuListInsertValueCheck(checkData);
			//テーブル：T133製造工程テーブルのインプットチェック（製造工程テーブル項目チェック）を行う。
			this.seizoKoteiInsertValueCheck(checkData);
//			//テーブル：T140原価資材テーブルのインプットチェック（原価資材テーブル項目チェック）を行う。
//			this.genkaShizaiInsertValueCheck(checkData);
			//テーブル：T141原価原料テーブルのインプットチェック（原価原料テーブル項目チェック）を行う。
			this.genkaGenryoInsertValueCheck(checkData);

		} catch (Exception e) {
			this.em.ThrowException(e, "");

		} finally {

		}

	}

	/**
	 * 試作品テーブル 登録項目チェック
	 *  : SA490のインプットチェックを行う。
	 * @param requestData : リクエストデータ
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private void shisakuhinInserValueCheck(RequestData checkData)
		throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		String strKinoNm = "SA490";
		String strTableNm = "tr_shisakuhin";

		try {

			for ( int i=0; i<checkData.GetRecCnt(strKinoNm, strTableNm); i++ ) {

				///
				///必須項目の入力チェック
				///
				//試作コード-社員CD
			    super.hissuInputCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "cd_shain"),"試作データ画面 試作CD-社員CD");
			    //試作コード-年
			    super.hissuInputCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "nen"),"試作データ画面 試作CD-年");

//2009/09/30 TT.A.ISONO DEL START [試作CD追番は登録時に採番するよう変更する。]

			    //試作コード-追番
//			    super.hissuInputCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "no_oi"),"試作データ画面 試作CD-追番");

//2009/09/30 TT.A.ISONO DEL END   [試作CD追番は登録時に採番するよう変更する。]

			    super.hissuInputCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "nm_hin"),"試作データ画面 品名");
				//super.hissuInputCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "no_shubetu"),"試作データ画面 種別番号");

			    ///
				///入力桁数チェック
			    ///
				super.sizeCheckLen(checkData.GetValueStr(strKinoNm, strTableNm, i, "no_irai"),"試作データ画面 依頼番号",8);
				super.sizeHalfFullLengthCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "nm_hin"),"試作データ画面 品名",100,50);

				//【QP@00342】
				//URL予約文字チェック
				super.checkStringURL(checkData.GetValueStr(strKinoNm, strTableNm, i, "nm_hin"),"試作データ画面 品名");

				super.sizeCheckLen(checkData.GetValueStr(strKinoNm, strTableNm, i, "no_shubetu"),"試作データ画面 種別番号",2);
//mod start --------------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.21
//				super.sizeCheckLen(checkData.GetValueStr(strKinoNm, strTableNm, i, "memo_shisaku"),"試作ﾒﾓ",400);
				String strShisakuMemo = checkData.GetValueStr(strKinoNm, strTableNm, i, "memo_shisaku");
				super.sizeCheckLen(strShisakuMemo.replaceAll("\n", ""),"試作ﾒﾓ",1000);
				super.sizeCheckLen(strShisakuMemo,"試作ﾒﾓ",2000);
//mod end --------------------------------------------------------------------------------------

				try {

					///
					///必須項目の入力チェック
					///
					super.hissuInputCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "cd_kaisha"),"指定工場-会社CD");
					super.hissuInputCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "cd_kojo"),"指定工場-工場CD");
					super.hissuInputCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "cd_shubetu"),"種別CD");
					super.hissuInputCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "cd_group"),"グループCD");
					super.hissuInputCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "cd_team"),"チームCD");
					super.hissuInputCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "yoryo"),"容量");

//mod start --------------------------------------------------------------------------------------
//QP@10181_シサクイック改良
//					super.hissuInputCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "cd_tani"),"容量単位CD");
					String strChkPrm = checkData.GetValueStr(strKinoNm, strTableNm, i, "cd_tani");
					// チェックパラメーターの必須入力チェックを行う。
					if (strChkPrm.equals(null) || strChkPrm.equals("")) {
						em.ThrowException(ExceptionKind.一般Exception, "E000412", "", "", "");
					}
//mod end ---------------------------------------------------------------------------------------

					super.hissuInputCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "su_iri"),"入り数");

				    ///
					///入力桁数チェック
				    ///
					super.sizeCheckLen(checkData.GetValueStr(strKinoNm, strTableNm, i, "tokuchogenryo"),"特徴原料",60);
					super.sizeCheckLen(checkData.GetValueStr(strKinoNm, strTableNm, i, "youto"),"用途",60);
					super.sizeCheckLen(checkData.GetValueStr(strKinoNm, strTableNm, i, "cd_eigyo"),"担当営業CD",60);
					super.sizeCheckLen(checkData.GetValueStr(strKinoNm, strTableNm, i, "hoho_sakin"),"殺菌方法",60);
					super.sizeCheckLen(checkData.GetValueStr(strKinoNm, strTableNm, i, "youki"),"容器包材",60);
					super.sizeCheckLen(checkData.GetValueStr(strKinoNm, strTableNm, i, "yoryo"),"容量",60);
					super.sizeCheckLen(checkData.GetValueStr(strKinoNm, strTableNm, i, "su_iri"),"入り数",60);
					super.sizeCheckLen(checkData.GetValueStr(strKinoNm, strTableNm, i, "shomikikan"),"賞味期間",60);
					super.sizeCheckLen(checkData.GetValueStr(strKinoNm, strTableNm, i, "genka"),"原価",60);
					super.sizeCheckLen(checkData.GetValueStr(strKinoNm, strTableNm, i, "baika"),"売価",60);
					super.sizeCheckLen(checkData.GetValueStr(strKinoNm, strTableNm, i, "buturyo"),"想定物量",60);
					super.sizeCheckLen(checkData.GetValueStr(strKinoNm, strTableNm, i, "dt_hatubai"),"販売時期",60);
					super.sizeCheckLen(checkData.GetValueStr(strKinoNm, strTableNm, i, "uriage_k"),"計画売上",60);
					super.sizeCheckLen(checkData.GetValueStr(strKinoNm, strTableNm, i, "rieki_k"),"計画利益",60);
					super.sizeCheckLen(checkData.GetValueStr(strKinoNm, strTableNm, i, "uriage_h"),"販売後売上",60);
					super.sizeCheckLen(checkData.GetValueStr(strKinoNm, strTableNm, i, "rieki_h"),"販売後利益",60);
					super.sizeCheckLen(checkData.GetValueStr(strKinoNm, strTableNm, i, "cd_nisugata"),"荷姿CD",60);
					super.sizeCheckLen(checkData.GetValueStr(strKinoNm, strTableNm, i, "memo"),"総合ﾒﾓ",500);

				} catch (ExceptionUser ex) {
					//返却メッセージの編集
					ex.setUserMsg("試作データ画面 基本情報 " + ex.getUserMsg());
					throw ex;

				} finally {

				}

			}

		} catch (Exception e) {
			this.em.ThrowException(e, "");

		} finally {
			strKinoNm = null;
			strTableNm = null;

		}
	}

	/**
	 * 配合テーブル 登録項目チェック
	 * @param requestData : リクエストデータ
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private void haigoInsertValueCheck(RequestData checkData)
		throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		String strKinoNm = "SA490";
		String strTableNm = "tr_haigo";
		String strAddMsg = "";

		try {

			for ( int i=0; i<checkData.GetRecCnt(strKinoNm, strTableNm); i++ ) {

				///
				/// 必須項目の入力チェック
				///
			    super.hissuInputCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "cd_shain"),"試作データ画面 試作CD-社員CD");		//試作コード-社員CD
			    super.hissuInputCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "nen"),"試作データ画面 試作CD-年");					//試作コード-年

//2009/09/30 TT.A.ISONO DEL START [試作CD追番は登録時に採番するよう変更する。]

//			    super.hissuInputCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "no_oi"),"試作データ画面 試作CD-追番");				//試作コード-追番

//2009/09/30 TT.A.ISONO DEL END   [試作CD追番は登録時に採番するよう変更する。]

			    super.hissuInputCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "cd_kotei"),"試作データ画面 配合表 工程CD");
			    super.hissuInputCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "seq_kotei"),"試作データ画面 配合表 工程SEQ");

				//  [試作表①]追加メッセージの編集
				strAddMsg = "試作データ画面 配合表 [工程：" + checkData.GetValueStr(strKinoNm, strTableNm, i, "sort_kotei") + "]";

				///
				/// 入力桁数チェック(工程名)
				///
//			    super.sizeCheckLen(checkData.GetValueStr(strKinoNm, strTableNm, i, "nm_kotei"),strAddMsg+"工程名",60);
			    super.sizeHalfFullLengthCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "nm_kotei"),strAddMsg+"工程名",60,30);

				//  [試作表①]追加メッセージの編集
			    String genryoCd = checkData.GetValueStr(strKinoNm, strTableNm, i, "cd_genryo");
				if(!(genryoCd != null && genryoCd.length() > 0)){
					genryoCd += "未入力";
				}
				strAddMsg = "試作データ画面 配合表 [工程：" + checkData.GetValueStr(strKinoNm, strTableNm, i, "sort_kotei");
				strAddMsg +=  ", 原料ｺｰﾄﾞ：" + genryoCd + "]";

				///
				/// 入力桁数チェック(原料名称)
				///
//			    super.sizeCheckLen(checkData.GetValueStr(strKinoNm, strTableNm, i, "nm_genryo"),strAddMsg+"原料名称",60);
			    super.sizeHalfFullLengthCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "nm_genryo"),strAddMsg+"原料名称",60,30);

				///
				/// 数値項目については、数字範囲チェック
				///
			    //※　下記の項目は値がNULL(空文字)ではない場合、チェックを行う。
				if ( !(checkData.GetValueStr(strKinoNm, strTableNm, i, "tanka").isEmpty()) ) {
					super.rangeNumCheck(
							new BigDecimal(Double.parseDouble(checkData.GetValueStr(strKinoNm, strTableNm, i, "tanka")))
							, new BigDecimal("0")
							, new BigDecimal("99999999.99")
							, strAddMsg+"単価");
				}
				if ( !(checkData.GetValueStr(strKinoNm, strTableNm, i, "budomari").isEmpty()) ) {
					super.rangeNumCheck(Double.parseDouble(checkData.GetValueStr(strKinoNm, strTableNm, i, "budomari")),0,999.99,strAddMsg+"歩留");
				}
				if ( !(checkData.GetValueStr(strKinoNm, strTableNm, i, "ritu_abura").isEmpty()) ) {
					super.rangeNumCheck(Double.parseDouble(checkData.GetValueStr(strKinoNm, strTableNm, i, "ritu_abura")),0,999.99,strAddMsg+"油含有率");
				}
				if ( !(checkData.GetValueStr(strKinoNm, strTableNm, i, "ritu_sakusan").isEmpty()) ) {
					super.rangeNumCheck(Double.parseDouble(checkData.GetValueStr(strKinoNm, strTableNm, i, "ritu_sakusan")),0,999.99,strAddMsg+"酢酸");
				}
				if ( !(checkData.GetValueStr(strKinoNm, strTableNm, i, "ritu_shokuen").isEmpty()) ) {
					super.rangeNumCheck(Double.parseDouble(checkData.GetValueStr(strKinoNm, strTableNm, i, "ritu_shokuen")),0,999.99,strAddMsg+"食塩");
				}
				if ( !(checkData.GetValueStr(strKinoNm, strTableNm, i, "ritu_sousan").isEmpty()) ) {
					super.rangeNumCheck(Double.parseDouble(checkData.GetValueStr(strKinoNm, strTableNm, i, "ritu_sousan")),0,999.99,strAddMsg+"総酸");
				}

				///
				/// 小数点以下が入力される項目については、小数桁数チェック
				///
				//※　下記の項目は値がNULL(空文字)ではない場合、チェックを行う。
				if ( !(checkData.GetValueStr(strKinoNm, strTableNm, i, "tanka").isEmpty()) ) {
					super.shousuRangeCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "tanka"),2,strAddMsg+"単価");
				}
				if ( !(checkData.GetValueStr(strKinoNm, strTableNm, i, "budomari").isEmpty()) ) {
					super.shousuRangeCheck(Double.parseDouble(checkData.GetValueStr(strKinoNm, strTableNm, i, "budomari")),3,2,strAddMsg+"歩留");
				}
				if ( !(checkData.GetValueStr(strKinoNm, strTableNm, i, "ritu_abura").isEmpty()) ) {
					super.shousuRangeCheck(Double.parseDouble(checkData.GetValueStr(strKinoNm, strTableNm, i, "ritu_abura")),3,2,strAddMsg+"油含有率");
				}
				if ( !(checkData.GetValueStr(strKinoNm, strTableNm, i, "ritu_sakusan").isEmpty()) ) {
					super.shousuRangeCheck(Double.parseDouble(checkData.GetValueStr(strKinoNm, strTableNm, i, "ritu_sakusan")),3,2,strAddMsg+"酢酸");
				}
				if ( !(checkData.GetValueStr(strKinoNm, strTableNm, i, "ritu_shokuen").isEmpty()) ) {
					super.shousuRangeCheck(Double.parseDouble(checkData.GetValueStr(strKinoNm, strTableNm, i, "ritu_shokuen")),3,2,strAddMsg+"食塩");
				}
				if ( !(checkData.GetValueStr(strKinoNm, strTableNm, i, "ritu_sousan").isEmpty()) ) {
					super.shousuRangeCheck(Double.parseDouble(checkData.GetValueStr(strKinoNm, strTableNm, i, "ritu_sousan")),3,2,strAddMsg+"総酸");
				}

			}

		} catch (Exception e) {
			this.em.ThrowException(e, "");

		} finally {
			strKinoNm = null;
			strTableNm = null;
			strAddMsg = null;

		}

	}

	/**
	 * 試作テーブル 登録項目チェック
	 * @param requestData : リクエストデータ
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private void shisakuInsertValueCheck(RequestData checkData) throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		String strKinoNm = "SA490";
		String strTableNm = "tr_shisaku";
		String strAddMsg = "";

		try {

			for ( int i=0; i<checkData.GetRecCnt(strKinoNm, strTableNm); i++ ) {

				///
				///必須項目の入力チェック
				///
			    super.hissuInputCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "cd_shain"),"試作データ画面 試作CD-社員CD");		//試作コード-社員CD
			    super.hissuInputCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "nen"),"試作データ画面 試作CD-年");					//試作コード-年

//2009/09/30 TT.A.ISONO DEL START [試作CD追番は登録時に採番するよう変更する。]

//			    super.hissuInputCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "no_oi"),"試作データ画面 試作CD-追番");				//試作コード-追番

//2009/09/30 TT.A.ISONO DEL END   [試作CD追番は登録時に採番するよう変更する。]

			    super.hissuInputCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "seq_shisaku"),"試作データ画面 .試作SEQ");
			    //if ( checkData.GetValueStr(strKinoNm, "tr_shisakuhin", 0, "cd_tani").equals(ConstManager.getConstValue(Category.設定, "ML_TANI_CD")) ) {
			    	//試作品テーブルの容量単位CDが「ml」に設定されている場合
			    	//super.hissuInputCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "hiju"),"試作テーブル 比重");
			    //}

			    //試作テーブル.サンプルNO(名称)の取得
				String nm_sample = checkData.GetValueStr(strKinoNm, strTableNm, i, "nm_sample");
				if ( nm_sample.equals("") ) {
					nm_sample = "名称無し";		//サンプルNOが空の場合、"名称無し"と格納する
				}
				//試作テーブル.試作表示順の取得
				String sort_shisaku = checkData.GetValueStr(strKinoNm, strTableNm, i, "sort_shisaku");

				//  [試作表①]追加メッセージの編集
				strAddMsg = "試作データ画面 配合表 [試作列：" + sort_shisaku;
				strAddMsg += ", サンプルNO(名称)：" + nm_sample + "]";

				///
				/// [試作表①]日付チェック
				///
			    if ( !checkData.GetValueStr(strKinoNm, strTableNm, i, "dt_shisaku").isEmpty() ) {
			    	super.dateCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "dt_shisaku"), strAddMsg + "試作日付");
			    }

				///
				/// [試作表①]入力桁数チェック[試作表①]
				///
//mod start --------------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.7
			    //super.sizeCheckLen(nm_sample,strAddMsg + "サンプルNO（名称）",20);
			    super.sizeCheckLen(nm_sample,strAddMsg + "サンプルNO（名称）",200);
//mod end ----------------------------------------------------------------------------------------

			    //【QP@20505】No5 2012/10/12 TT H.SHIMA MOD Start
//			    super.sizeCheckLen(checkData.GetValueStr(strKinoNm, strTableNm, i, "memo"),strAddMsg + "メモ",20);
			    super.sizeCheckLen(checkData.GetValueStr(strKinoNm, strTableNm, i, "memo"),strAddMsg + "メモ",50);
				//【QP@20505】No5 2012/10/12 TT H.SHIMA MOD End
			    //super.sizeCheckLen(checkData.GetValueStr(strKinoNm, strTableNm, i, "memo_shisaku"),strAddMsg + "試作メモ",400);

				//  [試作表②]追加メッセージの編集
				strAddMsg = "試作データ画面 特性値 [試作列：" + sort_shisaku;
				strAddMsg += ", サンプルNO(名称)：" + nm_sample + "]";

				///
				/// [試作表②]入力桁数チェック
				///
			    super.sizeCheckLen(checkData.GetValueStr(strKinoNm, strTableNm, i, "memo_sakusei"),strAddMsg + "作成メモ",150);
			    super.sizeHalfFullLengthCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "nendo"),strAddMsg + "粘度",20,10);
			    super.sizeCheckLen(checkData.GetValueStr(strKinoNm, strTableNm, i, "hyoka"),strAddMsg + "評価",200);
			    super.sizeCheckLen(checkData.GetValueStr(strKinoNm, strTableNm, i, "free_title1"),strAddMsg + "フリー①タイトル",20);
			    super.sizeCheckLen(checkData.GetValueStr(strKinoNm, strTableNm, i, "free_value1"),strAddMsg + "フリー①内容",20);
			    super.sizeCheckLen(checkData.GetValueStr(strKinoNm, strTableNm, i, "free_title2"),strAddMsg + "フリー②タイトル",20);
			    super.sizeCheckLen(checkData.GetValueStr(strKinoNm, strTableNm, i, "free_value2"),strAddMsg + "フリー②内容",20);
			    super.sizeCheckLen(checkData.GetValueStr(strKinoNm, strTableNm, i, "free_title3"),strAddMsg + "フリー③タイトル",20);
			    super.sizeCheckLen(checkData.GetValueStr(strKinoNm, strTableNm, i, "free_value3"),strAddMsg + "フリー③内容",20);
			    super.sizeHalfFullLengthCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "toudo"),strAddMsg + "糖度",20,10);
			    super.sizeHalfFullLengthCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "ondo"),strAddMsg + "温度",20,10);
			    super.sizeHalfFullLengthCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "ph"),strAddMsg + "PH",20,10);
			    super.sizeHalfFullLengthCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "ritu_sousan_bunseki"),strAddMsg + "総酸：分析",20,10);
			    super.sizeHalfFullLengthCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "ritu_shokuen_bunseki"),strAddMsg + "食塩：分析",20,10);
			    super.sizeHalfFullLengthCheck_hankaku(checkData.GetValueStr(strKinoNm, strTableNm, i, "hiju"),strAddMsg + "比重",20,10);
			    super.sizeHalfFullLengthCheck_hankaku(checkData.GetValueStr(strKinoNm, strTableNm, i, "hiju_sui"),strAddMsg + "水相比重",20,10);
			    super.sizeHalfFullLengthCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "suibun_kasei"),strAddMsg + "水分活性",20,10);
			    super.sizeHalfFullLengthCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "alcohol"),strAddMsg + "アルコール",20,10);
// ADD start 20121003 QP@20505 No.24
			    super.sizeCheckLen(checkData.GetValueStr(strKinoNm, strTableNm, i, "freetitle_suibun_kasei"),strAddMsg + "フリー水分活性タイトル",20);
			    super.sizeCheckLen(checkData.GetValueStr(strKinoNm, strTableNm, i, "free_suibun_kasei"),strAddMsg + "フリー水分活性",20);
			    super.sizeCheckLen(checkData.GetValueStr(strKinoNm, strTableNm, i, "freetitle_alcohol"),strAddMsg + "フリーアルコールタイトル",20);
			    super.sizeCheckLen(checkData.GetValueStr(strKinoNm, strTableNm, i, "free_alcohol"),strAddMsg + "フリーアルコール",20);
			    super.sizeCheckLen(checkData.GetValueStr(strKinoNm, strTableNm, i, "freetitle_nendo"),strAddMsg + "フリー粘度タイトル",20);
			    super.sizeCheckLen(checkData.GetValueStr(strKinoNm, strTableNm, i, "free_nendo"),strAddMsg + "フリー粘度",20);
			    super.sizeCheckLen(checkData.GetValueStr(strKinoNm, strTableNm, i, "freetitle_ondo"),strAddMsg + "フリー温度タイトル",20);
			    super.sizeCheckLen(checkData.GetValueStr(strKinoNm, strTableNm, i, "free_ondo"),strAddMsg + "フリー温度",20);
			    super.sizeCheckLen(checkData.GetValueStr(strKinoNm, strTableNm, i, "free_title4"),strAddMsg + "フリー④タイトル",20);
			    super.sizeCheckLen(checkData.GetValueStr(strKinoNm, strTableNm, i, "free_value4"),strAddMsg + "フリー④内容",20);
			    super.sizeCheckLen(checkData.GetValueStr(strKinoNm, strTableNm, i, "free_title5"),strAddMsg + "フリー⑤タイトル",20);
			    super.sizeCheckLen(checkData.GetValueStr(strKinoNm, strTableNm, i, "free_value5"),strAddMsg + "フリー⑤内容",20);
			    super.sizeCheckLen(checkData.GetValueStr(strKinoNm, strTableNm, i, "free_title6"),strAddMsg + "フリー⑥タイトル",20);
			    super.sizeCheckLen(checkData.GetValueStr(strKinoNm, strTableNm, i, "free_value6"),strAddMsg + "フリー⑥内容",20);
// ADD end 20121003 QP@20505 No.24
			    ///
				/// [試作表②]糖度～アルコールの値は、数値チェックを行う
				///
				if ( !(checkData.GetValueStr(strKinoNm, strTableNm, i, "toudo").isEmpty()) ) {
					this.NumericCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "toudo"),strAddMsg + "糖度");
				}
				if ( !(checkData.GetValueStr(strKinoNm, strTableNm, i, "ondo").isEmpty()) ) {
					this.NumericCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "ondo"),strAddMsg + "温度");
				}
				if ( !(checkData.GetValueStr(strKinoNm, strTableNm, i, "ph").isEmpty()) ) {
					this.NumericCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "ph"),strAddMsg + "PH");
				}
				if ( !(checkData.GetValueStr(strKinoNm, strTableNm, i, "ritu_sousan_bunseki").isEmpty()) ) {
					this.NumericCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "ritu_sousan_bunseki"),strAddMsg + "総酸：分析");
				}
				if ( !(checkData.GetValueStr(strKinoNm, strTableNm, i, "ritu_shokuen_bunseki").isEmpty()) ) {
					this.NumericCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "ritu_shokuen_bunseki"),strAddMsg + "食塩：分析");
				}
				if ( !(checkData.GetValueStr(strKinoNm, strTableNm, i, "hiju").isEmpty()) ) {
					this.NumericCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "hiju"),strAddMsg + "比重");
				}
				if ( !(checkData.GetValueStr(strKinoNm, strTableNm, i, "suibun_kasei").isEmpty()) ) {
					this.NumericCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "suibun_kasei"),strAddMsg + "水分活性");
				}
				if ( !(checkData.GetValueStr(strKinoNm, strTableNm, i, "alcohol").isEmpty()) ) {
					this.NumericCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "alcohol"),strAddMsg + "アルコール");
				}

				///
				/// [試作表②]数値項目については、数字範囲チェック
				///
			    //※　下記の項目は値がNULL(空文字)ではない場合、チェックを行う。
				if ( !(checkData.GetValueStr(strKinoNm, strTableNm, i, "ritu_sousan").isEmpty()) ) {
					super.rangeNumCheck(Double.parseDouble(checkData.GetValueStr(strKinoNm, strTableNm, i, "ritu_sousan")),0,999.99,strAddMsg + "総酸");
				}
				if ( !(checkData.GetValueStr(strKinoNm, strTableNm, i, "ritu_shokuen").isEmpty()) ) {
					super.rangeNumCheck(Double.parseDouble(checkData.GetValueStr(strKinoNm, strTableNm, i, "ritu_shokuen")),0,999.99,strAddMsg + "食塩");
				}
				if ( !(checkData.GetValueStr(strKinoNm, strTableNm, i, "sando_suiso").isEmpty()) ) {
					super.rangeNumCheck(Double.parseDouble(checkData.GetValueStr(strKinoNm, strTableNm, i, "sando_suiso")),0,999.99,strAddMsg + "水相中酸度");
				}
				if ( !(checkData.GetValueStr(strKinoNm, strTableNm, i, "shokuen_suiso").isEmpty()) ) {
					super.rangeNumCheck(Double.parseDouble(checkData.GetValueStr(strKinoNm, strTableNm, i, "shokuen_suiso")),0,999.99,strAddMsg + "水相中食塩");
				}
				if ( !(checkData.GetValueStr(strKinoNm, strTableNm, i, "sakusan_suiso").isEmpty()) ) {
					super.rangeNumCheck(Double.parseDouble(checkData.GetValueStr(strKinoNm, strTableNm, i, "sakusan_suiso")),0,999.99,strAddMsg + "水相中酢酸");
				}
				if ( !(checkData.GetValueStr(strKinoNm, strTableNm, i, "juryo_shiagari_g").isEmpty()) ) {
					//super.shousuRangeCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "juryo_shiagari_g"),10,4,strAddMsg + "合計仕上重量");
					super.rangeNumCheck(
								new BigDecimal(checkData.GetValueStr(strKinoNm, strTableNm, i, "juryo_shiagari_g"))
								,new BigDecimal("0")
								,new BigDecimal("9999999999.9999")
								,strAddMsg + "合計仕上重量");
				}

				///
				/// [試作表②]小数点以下が入力される項目については、小数桁数チェック
				///
			    //※　下記の項目は値がNULL(空文字)ではない場合、チェックを行う。
				if ( !(checkData.GetValueStr(strKinoNm, strTableNm, i, "ritu_sousan").isEmpty()) ) {
					super.shousuRangeCheck(Double.parseDouble(checkData.GetValueStr(strKinoNm, strTableNm, i, "ritu_sousan")),3,2,strAddMsg + "総酸");
				}
				if ( !(checkData.GetValueStr(strKinoNm, strTableNm, i, "ritu_shokuen").isEmpty()) ) {
					super.shousuRangeCheck(Double.parseDouble(checkData.GetValueStr(strKinoNm, strTableNm, i, "ritu_shokuen")),3,2,strAddMsg + "食塩");
				}
				if ( !(checkData.GetValueStr(strKinoNm, strTableNm, i, "sando_suiso").isEmpty()) ) {
					super.shousuRangeCheck(Double.parseDouble(checkData.GetValueStr(strKinoNm, strTableNm, i, "sando_suiso")),3,2,strAddMsg + "水相中酸度");
				}
				if ( !(checkData.GetValueStr(strKinoNm, strTableNm, i, "shokuen_suiso").isEmpty()) ) {
					super.shousuRangeCheck(Double.parseDouble(checkData.GetValueStr(strKinoNm, strTableNm, i, "shokuen_suiso")),3,2,strAddMsg + "水相中食塩");
				}
				if ( !(checkData.GetValueStr(strKinoNm, strTableNm, i, "sakusan_suiso").isEmpty()) ) {
					super.shousuRangeCheck(Double.parseDouble(checkData.GetValueStr(strKinoNm, strTableNm, i, "sakusan_suiso")),3,2,strAddMsg + "水相中酢酸");
				}
				if ( !(checkData.GetValueStr(strKinoNm, strTableNm, i, "juryo_shiagari_g").isEmpty()) ) {
					super.shousuRangeCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "juryo_shiagari_g"),10,4,strAddMsg + "合計仕上重量");
				}
				if ( !(checkData.GetValueStr(strKinoNm, strTableNm, i, "hiju").isEmpty()) ) {
					if(checkData.GetValueStr(strKinoNm, strTableNm, i, "hiju").indexOf(".") != -1){
						super.shousuRangeCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "hiju"), 3, strAddMsg + "比重");
					}
				}

			}

		} catch (Exception e) {
			this.em.ThrowException(e, "");

		} finally {
			strKinoNm = null;
			strTableNm = null;
			strAddMsg = null;

		}

	}

	/**
	 * 試作テーブル.試作表示順の取得
	 * @param strShisakuSeq : 試作SEQ
	 * @param checkData : リクエストデータ
	 * @return 試作テーブル.試作表示順
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private String getShisakuSort(String strShisakuSeq, RequestData checkData)
		throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		String strRetShisakuSort = "";
		String strKinoNm = "SA490";
		String strTableNm_sub = "tr_shisaku";

		try {

			//試作表示順の取得
			for ( int j=0; j<checkData.GetRecCnt(strKinoNm, strTableNm_sub); j++ ) {

				//試作リスト.試作SEQ = 試作.試作SEQ
				if ( strShisakuSeq.equals(checkData.GetValueStr(strKinoNm, strTableNm_sub, j, "seq_shisaku")) ) {

					strRetShisakuSort = checkData.GetValueStr(strKinoNm, strTableNm_sub, j, "sort_shisaku") + "(ｻﾝﾌﾟﾙNO：";

					String strSample = checkData.GetValueStr(strKinoNm, strTableNm_sub, j, "nm_sample");
					if(strSample != null && strSample.length() > 0){
						strRetShisakuSort = strRetShisakuSort  + checkData.GetValueStr(strKinoNm, strTableNm_sub, j, "nm_sample") + ")";
					}else{
						strRetShisakuSort = strRetShisakuSort + "名称無し)";
					}

					break;

				}

			}

		} catch (Exception e) {
			this.em.ThrowException(e, "");

		} finally {
			strKinoNm = null;
			strTableNm_sub = null;

		}
		return strRetShisakuSort;

	}

	/**
	 * 配合テーブル.工程順・原料順の取得
	 * @param strKoteiCd : 工程CD
	 * @param strKoteiSeq : 工程SEQ
	 * @param checkData : リクエストデータ
	 * @return 配合テーブル.工程順 + ":::" + 配合テーブル.原料順
	 *             文字列「:::」で区切る
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private String getKoteiSort(String strKoteiCd, String strKoteiSeq, RequestData checkData)
		throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		String strRetKoteiSort = "";
		String strKinoNm = "SA490";
		String strTableNm_sub = "tr_haigo";

		try {

			//工程順の取得
			for ( int j=0; j<checkData.GetRecCnt(strKinoNm, strTableNm_sub); j++ ) {

				//試作リスト.工程CD = 配合.工程CD & 試作リスト.工程SEQ = 配合.工程SEQ
				if ( strKoteiCd.equals(checkData.GetValueStr(strKinoNm, strTableNm_sub, j, "cd_kotei"))
						&& strKoteiSeq.equals(checkData.GetValueStr(strKinoNm, strTableNm_sub, j, "seq_kotei")) ) {

					strRetKoteiSort = checkData.GetValueStr(strKinoNm, strTableNm_sub, j, "sort_kotei");
					strRetKoteiSort += ":::";

					String genryoCd = checkData.GetValueStr(strKinoNm, strTableNm_sub, j, "cd_genryo");
					if(genryoCd != null && genryoCd.length() > 0){
						strRetKoteiSort += genryoCd;
					}else{
						strRetKoteiSort += "未入力";
					}

					break;

				}

			}

		} catch (Exception e) {
			this.em.ThrowException(e, "");

		} finally {
			strKinoNm = null;
			strTableNm_sub = null;

		}
		return strRetKoteiSort;

	}

	/**
	 * 試作リストテーブル 登録項目チェック
	 * @param requestData : リクエストデータ
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private void shisakuListInsertValueCheck(RequestData checkData)
		throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		String strKinoNm = "SA490";
		String strTableNm = "tr_shisaku_list";

		//ADD 2012.4.30　【H24年度対応】修正4　試作列の入力に対する原料の入力チェックを行う。
		String strSeq_shisaku = "";//ブレークキー:試作SEQ
		String strFlg_shisanIrai = "";//試作依頼フラグ(1で依頼済み)
		String strCd_genryo = "";//原料コード
		String strNm_genryo = "";//原料名
		int intRecIndex = 0;//取得レコードインデックス
		String strFlg_cancel = "";//試作依頼キャンセル
		// 20160913 ADD KPX@1600766 Start
		String nm_sample = "";// サンプル名
		// 20160913 ADD KPX@1600766 End

		try {
			// 20160915 MOD KPX@1600766 Start
	    	// 20160420 ADD KPX@1600766 Start
//			String errGenryo = ""; // エラー原料情報
	    	StringBuffer errGenryo = new StringBuffer(""); // エラー原料情報
	    	// 20160420 ADD KPX@1600766 End
	    	// 20160915 MOD KPX@1600766 End

			for ( int i=0; i<checkData.GetRecCnt(strKinoNm, strTableNm); i++ ) {

				///
				///必須項目の入力チェック
				///
			    super.hissuInputCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "cd_shain"),"試作データ画面 試作CD-社員CD");		//試作コード-社員CD
			    super.hissuInputCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "nen"),"試作データ画面 試作CD-年");					//試作コード-年

//2009/09/30 TT.A.ISONO DEL START [試作CD追番は登録時に採番するよう変更する。]

//			    super.hissuInputCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "no_oi"),"試作データ画面 試作CD-追番");				//試作コード-追番

//2009/09/30 TT.A.ISONO DEL END   [試作CD追番は登録時に採番するよう変更する。]

			    //試作リスト.試作SEQ
			    String strShisakuSeq = checkData.GetValueStr(strKinoNm, strTableNm, i, "seq_shisaku");
			    super.hissuInputCheck(strShisakuSeq,"試作データ画面 試作SEQ");
			    //試作リスト.工程CD
			    String strKouteiCd = checkData.GetValueStr(strKinoNm, strTableNm, i, "cd_kotei");
			    super.hissuInputCheck(strShisakuSeq,"試作データ画面 工程CD");
			    //試作リスト.工程SEQ
			    String strKouteiSeq = checkData.GetValueStr(strKinoNm, strTableNm, i, "seq_kotei");
			    super.hissuInputCheck(strShisakuSeq,"試作データ画面 工程SEQ");

				//ADD 2012.4.30　【H24年度対応】修正4　試作列の入力に対する原料の入力チェックを行う。
				//試作依頼済みフラグを試作テーブルより取得保存する。
				if( !strSeq_shisaku.equals(strShisakuSeq)){
					intRecIndex = getTableIndex(checkData,"tr_shisaku","seq_shisaku",strShisakuSeq);
					strFlg_shisanIrai = checkData.GetValueStr(strKinoNm, "tr_shisaku", intRecIndex, "flg_shisanIrai");
					strFlg_cancel = checkData.GetValueStr(strKinoNm, "tr_shisaku", intRecIndex, "flg_cancel");
				}

				// 20160913 ADD KPX@1600766 Start
				nm_sample = checkData.GetValueStr(strKinoNm, "tr_shisaku", intRecIndex, "nm_sample");
				// 20160913 ADD KPX@1600766 End

				//※　下記の項目は値がNULL(空文字)ではない場合、チェックを行う。
			    String strRyo = checkData.GetValueStr(strKinoNm, strTableNm, i, "quantity");
			    if ( !(strRyo.isEmpty()) ) {

				    try {

						///
						/// 数値項目については、数字範囲チェック
				    	/// [0～99999.xxxx]
						///
				    	int intKetaSu = 0;
				    	if ( !checkData.GetValueStr(strKinoNm, "tr_shisakuhin", 0, "keta_shosu_val1").isEmpty() ) {
						    intKetaSu = Integer.parseInt(checkData.GetValueStr(strKinoNm, "tr_shisakuhin", 0, "keta_shosu_val1"));

				    	}
					    String strKetaSu = "99999";
					    if ( intKetaSu > 0 ) {

					    	strKetaSu += ".";
						    for( int j=0; j<intKetaSu; j++ ) {
						    	strKetaSu += "9";

						    }

					    }
				    	super.rangeNumCheck(Double.parseDouble(strRyo),0,Double.parseDouble(strKetaSu),"量");

						///
						/// 小数点以下が入力される項目については、小数桁数チェック
						///
				    	if ( intKetaSu > 0 ) {
				    		super.shousuRangeCheck(strRyo,5,intKetaSu,"量");

				    	} else if ( strRyo.indexOf(".") != -1 ) {
				    		super.shousuRangeCheck(strRyo,5,intKetaSu,"量");

				    	}

						//ADD 2012.4.30　【H24年度対応】修正4　試作列の入力に対する原料の入力チェックを行う。
						//試算依頼済みで且つキャンセルフラグがoffの試作について、配合テーブルの原料チェックを行う。
						if( "1".equals(strFlg_shisanIrai) && !"1".equals(strFlg_cancel)){
							//工程CD,工程SEQより配合テーブルからレコードを取得
							intRecIndex = getTableIndex(checkData,"tr_haigo","cd_kotei",strKouteiCd,"seq_kotei",strKouteiSeq);
							//原料CDが入力されていない時入力エラー
							strCd_genryo = checkData.GetValueStr(strKinoNm, "tr_haigo", intRecIndex, "cd_genryo");
							if(strCd_genryo == null || strCd_genryo.isEmpty()){
								em.ThrowException(
										ExceptionKind.一般Exception,
										"E000222",
										"","","");
							}
							//原料名の一文字目に★(原料マスタ未登録)の場合入力エラー
							strNm_genryo = checkData.GetValueStr(strKinoNm, "tr_haigo", intRecIndex, "nm_genryo");
							if( !"".equals(strNm_genryo)
									 &&	"★".equals(strNm_genryo.substring(0,1))
									 && !"N".equals(strCd_genryo.substring(0,1))
									 && !"n".equals(strCd_genryo.substring(0,1))

								){
								// 20160420 MOD KPX@1600766 Start
//								em.ThrowException(
//										ExceptionKind.一般Exception,
//										"E000223",
//										"","","");
								// 20160915 MOD KPX@1600766 Start
								// エラー原料情報の設定
//								errGenryo += System.getProperty("line.separator");
								// 20160913 ADD KPX@1600766 Start
//								errGenryo += "サンプルNo：" + nm_sample + "　";
								// 20160913 ADD KPX@1600766 End
//								errGenryo += strCd_genryo + "：" + strNm_genryo;
								// 20160420 MOD KPX@1600766 End
								errGenryo.append(System.getProperty("line.separator"));
								errGenryo.append(nm_sample + "　" + strCd_genryo + "：" + strNm_genryo);
								// 20160915 MOD KPX@1600766 END
							}
						}

				    } catch ( ExceptionUser ex) {		//チェックに引っかかった場合
						//メッセージの設定
						String strMsg = "";
						strMsg = "試作データ 配合表";
						strMsg +=  " [試作列：" + getShisakuSort(strShisakuSeq, checkData);
						strMsg += ", 工程：" + getKoteiSort(strKouteiCd, strKouteiSeq, checkData).split(":::")[0];
						strMsg += "(原料ｺｰﾄﾞ：" + getKoteiSort(strKouteiCd, strKouteiSeq, checkData).split(":::")[1] +")";
						strMsg += "]" + ex.getUserMsg();
						ex.setUserMsg(strMsg);

						//エクセプションをthrowする
						throw ex;

				    } finally {

				    }

			    }

			    //QP@20505_No1 2012/10/29 ADD Start
			    //工程仕上重量
			    String juryo_shiagari_seq = strShisakuSeq = checkData.GetValueStr(strKinoNm, strTableNm, i, "juryo_shiagari_seq");
			    if( !(juryo_shiagari_seq.isEmpty()) ){
			    	super.rangeNumCheck(
			    			new BigDecimal(checkData.GetValueStr(strKinoNm, strTableNm, i, "juryo_shiagari_seq"))
			    			,new BigDecimal("0")
			    			,new BigDecimal("9999999999.9999")
			    			,"試作データ画面 工程仕上重量");
			    }
				//QP@20505_No1 2012/10/29 ADD End

			}

			// 20160420 ADD KPX@1600766 Start
			try{
				// 20160915 MOD KPX@1600766 Start
				// 原料エラーがある場合エラーメッセージ表示
				//if(!"".equals(errGenryo)) {
				if(!"".equals(errGenryo.toString())) {
				// 20160915 MOD KPX@1600766 End
					em.ThrowException(
						ExceptionKind.一般Exception,
						"E000223",
						"","","");
				}
			} catch (ExceptionUser ex) {
				// 20160915 MOD KPX@1600766 Start
				// エラーメッセージ+エラー原料情報を設定
//				ex.setUserMsg(ex.getUserMsg() + errGenryo);
				ex.setUserMsg(ex.getUserMsg() + errGenryo.toString());
				// 20160915 MOD KPX@1600766 End

				throw ex;
			}
			// 20160420 ADD KPX@1600766 End

		} catch (Exception e) {
			this.em.ThrowException(e, "");

		} finally {
			strKinoNm = null;
			strTableNm = null;

		}

	}

	/**
	 * 製造工程テーブル 登録項目チェック
	 * @param requestData : リクエストデータ
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private void seizoKoteiInsertValueCheck(RequestData checkData) throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		String strKinoNm = "SA490";
		String strTableNm = "tr_cyuui";

		try {

			for ( int i=0; i<checkData.GetRecCnt(strKinoNm, strTableNm); i++ ) {

				///
				///必須項目の入力チェック
				///
			    super.hissuInputCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "cd_shain"),"試作データ画面 試作CD-社員CD");		//試作コード-社員CD
			    super.hissuInputCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "nen"),"試作データ画面 試作CD-年");					//試作コード-年

//2009/09/30 TT.A.ISONO DEL START [試作CD追番は登録時に採番するよう変更する。]

//			    super.hissuInputCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "no_oi"),"試作データ画面 試作CD-追番");				//試作コード-追番

//2009/09/30 TT.A.ISONO DEL END   [試作CD追番は登録時に採番するよう変更する。]

			    //製造工程 試作SEQ
			    //String strShisakuSeq = checkData.GetValueStr(strKinoNm, strTableNm, i, "seq_shisaku");
			    //super.hissuInputCheck(strShisakuSeq,"試作データ画面 試作SEQ");
			    //製造工程 試作SEQ
			    String strChuiNo = checkData.GetValueStr(strKinoNm, strTableNm, i, "no_chui");
		    	super.hissuInputCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "no_chui"),"試作データ画面 注意事項No");

			    try {
					///
					/// 入力桁数チェック
					///
//mod start --------------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.21
//				    super.sizeCheckLen(checkData.GetValueStr(strKinoNm, strTableNm, i, "chuijiko"),"注意事項",400);
			    	String strSeizoKotei = checkData.GetValueStr(strKinoNm, strTableNm, i, "chuijiko");
				    super.sizeCheckLen(strSeizoKotei.replaceAll("\n", ""),"注意事項",1000);
				    super.sizeCheckLen(strSeizoKotei,"注意事項",2000);
//mod end --------------------------------------------------------------------------------------

			    } catch (ExceptionUser ex) {

			    	//試作表示順の取得
			    	//String sort_shisaku = getShisakuSort(strShisakuSeq, checkData);

					//返却メッセージの編集
					ex.setUserMsg("試作データ画面 配合表 [注意事項No：" + strChuiNo + "]" + ex.getUserMsg());
					throw ex;

				} finally {

				}

			}

		} catch (Exception e) {
			this.em.ThrowException(e, "");

		} finally {
			strKinoNm = null;
			strTableNm = null;

		}
	}

//	/**
//	 * 原価資材テーブル 登録項目チェック
//	 * @param requestData : リクエストデータ
//	 * @throws ExceptionWaning
//	 * @throws ExceptionUser
//	 * @throws ExceptionSystem
//	 */
//	private void genkaShizaiInsertValueCheck(RequestData checkData) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
//
//		try {
//			String strKinoNm = "SA490";
//			String strTableNm = "tr_shizai";
//			for ( int i=0; i<checkData.GetRecCnt(strKinoNm, strTableNm); i++ ) {
//				///
//				///必須項目の入力チェック
//				///
//			    super.hissuInputCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "cd_shain"),"試作CD-社員CD");		//試作コード-社員CD
//			    super.hissuInputCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "nen"),"試作CD-年");					//試作コード-年
//			    super.hissuInputCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "no_oi"),"試作CD-追番");				//試作コード-追番
//
//			}
//		} catch (Exception e) {
//			this.em.ThrowException(e, "");
//		} finally {
//
//		}
//	}

	//TODO
	/**
	 * 原価原料テーブル 登録項目チェック
	 * @param requestData : リクエストデータ
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private void genkaGenryoInsertValueCheck(RequestData checkData) throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		String strKinoNm = "SA490";
		String strTableNm = "tr_genryo";
		String strAddMsg = "";

		try {

			for ( int i=0; i<checkData.GetRecCnt(strKinoNm, strTableNm); i++ ) {

				///
				///必須項目の入力チェック
				///
			    super.hissuInputCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "cd_shain"),"試作CD-社員CD");		//試作コード-社員CD
			    super.hissuInputCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "nen"),"試作CD-年");					//試作コード-年

//2009/09/30 TT.A.ISONO DEL START [試作CD追番は登録時に採番するよう変更する。]

//			    super.hissuInputCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "no_oi"),"試作CD-追番");				//試作コード-追番

//2009/09/30 TT.A.ISONO DEL END   [試作CD追番は登録時に採番するよう変更する。]

			    super.hissuInputCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "seq_shisaku"),"試作SEQ");			//試作SEQ

			    //試作テーブル.サンプルNO(名称)の取得
				String nm_sample = checkData.GetValueStr(strKinoNm, "tr_shisaku", i, "nm_sample");
				if ( nm_sample.equals("") ) {
					nm_sample = "名称無し";		//サンプルNOが空の場合、"名称無し"と格納する

				}
				//試作テーブル.試作表示順の取得
				String sort_shisaku = checkData.GetValueStr(strKinoNm, "tr_shisaku", i, "sort_shisaku");

				//  [試作表①]追加メッセージの編集
				strAddMsg = "試作データ画面 原価試算<原料>⑤ [試作列：" + sort_shisaku;
				strAddMsg += ", サンプルNO(名称)：" + nm_sample + "]";

				///
				///入力桁数チェック
				///
			    super.sizeHalfFullLengthCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "zyusui"),strAddMsg + "充填量水相",20,10);
			    super.sizeHalfFullLengthCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "zyuabura"),strAddMsg + "充填量油相",20,10);
			    super.sizeHalfFullLengthCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "gokei"),strAddMsg + "合計量",20,10);
			    super.sizeHalfFullLengthCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "genryohi"),strAddMsg + "原料費(kg)",20,10);
			    super.sizeHalfFullLengthCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "genryohi1"),strAddMsg + "原料費（1本当）",20,10);
			    super.sizeHalfFullLengthCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "hiju"),strAddMsg + "比重",20,10);
			    super.sizeHalfFullLengthCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "yoryo"),strAddMsg + "容量",20,10);
			    super.sizeHalfFullLengthCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "irisu"),strAddMsg + "入り数",20,10);
			    super.sizeHalfFullLengthCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "yukobudomari"),strAddMsg + "有効歩留",20,10);
			    super.sizeHalfFullLengthCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "reberu"),strAddMsg + "レベル量",20,10);
			    super.sizeHalfFullLengthCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "hizyubudomari"),strAddMsg + "比重加算量",20,10);
			    super.sizeHalfFullLengthCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "heikinzyu"),strAddMsg + "平均充填量",20,10);
			    super.sizeHalfFullLengthCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "cs_genryo"),strAddMsg + "原料費/CS",20,10);
			    super.sizeHalfFullLengthCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "cs_zairyohi"),strAddMsg + "材料費",20,10);
			    super.sizeHalfFullLengthCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "cs_keihi"),strAddMsg + "固定費",20,10);
			    super.sizeHalfFullLengthCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "cs_genka"),strAddMsg + "原価計/CS",20,10);
			    super.sizeHalfFullLengthCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "ko_genka"),strAddMsg + "原価計/個",20,10);
			    super.sizeHalfFullLengthCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "ko_baika"),strAddMsg + "売価",20,10);
			    super.sizeHalfFullLengthCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "ko_riritu"),strAddMsg + "粗利",20,10);

				///
				/// 小数点以下が入力される項目については、小数桁数チェック
				///
				if ( !(checkData.GetValueStr(strKinoNm, strTableNm, i, "zyusui").isEmpty()) ) {
					super.shousuRangeCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "zyusui"),2,strAddMsg+"充填量水相");
				}
				if ( !(checkData.GetValueStr(strKinoNm, strTableNm, i, "zyuabura").isEmpty()) ) {
					super.shousuRangeCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "zyuabura"),2,strAddMsg+"充填量油相");
				}
				if ( !(checkData.GetValueStr(strKinoNm, strTableNm, i, "gokei").isEmpty()) ) {
					super.shousuRangeCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "gokei"),3,strAddMsg+"合計量");
				}
				if ( !(checkData.GetValueStr(strKinoNm, strTableNm, i, "genryohi").isEmpty()) ) {
					super.shousuRangeCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "genryohi"),2,strAddMsg+"原料費(kg)");
				}
				if ( !(checkData.GetValueStr(strKinoNm, strTableNm, i, "genryohi1").isEmpty()) ) {
					super.shousuRangeCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "genryohi1"),2,strAddMsg+"原料費（1本当）");
				}
				if ( !(checkData.GetValueStr(strKinoNm, strTableNm, i, "hiju").isEmpty()) ) {
					super.shousuRangeCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "hiju"),3,strAddMsg+"比重");
				}
				if ( !(checkData.GetValueStr(strKinoNm, strTableNm, i, "yukobudomari").isEmpty()) ) {
					super.shousuRangeCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "yukobudomari"),2,strAddMsg+"有効歩留");
				}
				if ( !(checkData.GetValueStr(strKinoNm, strTableNm, i, "reberu").isEmpty()) ) {
					super.shousuRangeCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "reberu"),2,strAddMsg+"レベル量");
				}
				if ( !(checkData.GetValueStr(strKinoNm, strTableNm, i, "hizyubudomari").isEmpty()) ) {
					super.shousuRangeCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "hizyubudomari"),2,strAddMsg+"比重加算量");
				}
				if ( !(checkData.GetValueStr(strKinoNm, strTableNm, i, "heikinzyu").isEmpty()) ) {
					super.shousuRangeCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "heikinzyu"),2,strAddMsg+"平均充填量");
				}
				if ( !(checkData.GetValueStr(strKinoNm, strTableNm, i, "cs_genryo").isEmpty()) ) {
					super.shousuRangeCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "cs_genryo"),2,strAddMsg+"原料費/CS");
				}
				if ( !(checkData.GetValueStr(strKinoNm, strTableNm, i, "cs_zairyohi").isEmpty()) ) {
					super.shousuRangeCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "cs_zairyohi"),2,strAddMsg+"材料費");
				}
				if ( !(checkData.GetValueStr(strKinoNm, strTableNm, i, "cs_keihi").isEmpty()) ) {
					super.shousuRangeCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "cs_keihi"),2,strAddMsg+"固定費");
				}
				if ( !(checkData.GetValueStr(strKinoNm, strTableNm, i, "cs_genka").isEmpty()) ) {
					super.shousuRangeCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "cs_genka"),2,strAddMsg+"原価計/CS");
				}
				if ( !(checkData.GetValueStr(strKinoNm, strTableNm, i, "ko_genka").isEmpty()) ) {
					super.shousuRangeCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "ko_genka"),2,strAddMsg+"原価計/個");
				}
				if ( !(checkData.GetValueStr(strKinoNm, strTableNm, i, "ko_baika").isEmpty()) ) {
					super.shousuRangeCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "ko_baika"),2,strAddMsg+"売価");
				}
				if ( !(checkData.GetValueStr(strKinoNm, strTableNm, i, "ko_riritu").isEmpty()) ) {
					super.shousuRangeCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "ko_riritu"),2,strAddMsg+"粗利");
				}

			}

		} catch (Exception e) {
			this.em.ThrowException(e, "");

		} finally {
			//変数の削除
			strKinoNm = null;
			strTableNm = null;
			strAddMsg = null;

		}

	}

	/**
	 * 数値チェック
	 * @param strChkParam : チェックパラメータ
	 * @param strChkName : チェック名称
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private void NumericCheck(String strChkParam, String strChkName)
		throws ExceptionSystem, ExceptionUser, ExceptionWaning {

try {

			try {
		    	// チェックパラメーターの数値チェックを行う。
				Double.parseDouble(strChkParam);
				//Integer.parseInt(strChkParam);

			} catch (NumberFormatException ne) {
				//数値ではない場合、ExceptionをThrowする
		    	em.ThrowException(
		    			ExceptionKind.一般Exception,
		    			"E000012",
		    			strChkName, "","");

			}

		} catch (Exception e) {
			em.ThrowException(e, "インプットチェックに失敗しました。");

		} finally {

		}
	}

	//ADD 2012.4.30　【H24年度対応】修正4　試作列の入力に対する原料の入力チェックを行う。
	/**
	 * 指定テーブルを指定キーの指定データでサーチし、indexをリターンする。
	 * @param checkData : リクエストデータ
	 * @param strTableNm_sub : テーブル名
	 * @param strSearchKey : サーチ項目...
	 * @param strSearchData : サーチデータ(null以外)...
	 * @return テーブルのindex
	 * @throws ExceptionSystem
	 * @throws ExceptionUser	見つからない場合発生。
	 * @throws ExceptionWaning
	 */
	private int getTableIndex(RequestData checkData,String strTableNm_sub,String... strKeyData)
		throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		String strKinoNm = "SA490";

		try {

	    	//データサーチ
			int intParaCounter = 0;
			for ( int j=0; j<checkData.GetRecCnt(strKinoNm, strTableNm_sub); j++ ) {

				for ( intParaCounter=0; intParaCounter<strKeyData.length; intParaCounter=intParaCounter+2){
					if( strKeyData[intParaCounter+1] == null){
						break;
					}else if ( !strKeyData[intParaCounter+1].equals(checkData.GetValueStr(strKinoNm, strTableNm_sub, j, strKeyData[intParaCounter]))){
						break;
					}
				}
				if( intParaCounter >= strKeyData.length){
					return j;
				}

			}
			//インターフェース上見つからないことはないはず
			em.ThrowException(
	    			ExceptionKind.一般Exception,
	    			"E000224",
	    			strTableNm_sub,StringUtils.arrayToCommaDelimitedString(strKeyData),"");

		} catch (Exception e) {
			em.ThrowException(e, "");

		}
		return 0;
	}

}
