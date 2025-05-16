package jp.co.blueflag.shisaquick.srv.logic;

import java.util.List;

import jp.co.blueflag.shisaquick.srv.base.LogicBase;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsKindBean;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsTableBean;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ConstManager;
import jp.co.blueflag.shisaquick.srv.common.ConstManager.Category;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;

/**
 *
 * 原料データ管理②DB処理
 *  : 原料データ削除処理のＤＢに対する業務ロジックの実装
 *
 * @author TT.k-katayama
 * @since  2009/04/14
 *
 */
public class GenryoDataKanri2Logic extends LogicBase {

	/**
	 * コンストラクタ
	 */
	public GenryoDataKanri2Logic() {
		//基底クラスのコンストラクタ
		super();

	}

	/**
	 * 原料データ2操作管理
	 * @param reqKind : 機能リクエストデータ
	 * @param userInfoData : ユーザー情報
	 * @return レスポンスデータ（機能）
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	public RequestResponsKindBean ExecLogic(
			RequestResponsKindBean reqData
			,UserInfoData _userInfoData
			)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		//ユーザー情報退避
		userInfoData = _userInfoData;

		RequestResponsKindBean resKind = null;

		try {
			//レスポンスデータ（機能）生成
			resKind = new RequestResponsKindBean();

			//①：リクエストデータより、処理区分を抽出し、各処理メソッドを呼出す。
			String strReqShoriKbn = reqData.getFieldVale(0, 0, "kbn_shori");
//			String strReqKakuteiCd = reqData.getFieldVale(0, 0, "cd_kakutei");

			String strGenryocd = "";

			if ( strReqShoriKbn.equals("0") ) {
				//0 : 登録処理
				strGenryocd = this.genryoKanri2InsertSQL(reqData,0);

			} else if ( strReqShoriKbn.equals("1") ) {
				//1 : 更新処理
				this.genryoKanri2UpdateSQL(reqData);

//				//確定コードが存在する場合は、原料コード=確定コードによる登録処理を行う。
//				if ( !(strReqKakuteiCd.equals(null) || strReqKakuteiCd.equals("")) ) {
//					this.genryoKanri2InsertSQL(reqData,1);
//				}
			} else {
			}

			//③：正常終了時、管理結果パラメーター格納メソッドを呼出し、レスポンスデータを形成する。

			//機能IDの設定
			resKind.setID(reqData.getID());
			//テーブル名の設定
			resKind.addTableItem(reqData.getTableID(0));

			this.storageGenryoDataKanri2(strGenryocd,resKind.getTableItem(0));

		} catch (Exception e) {
			this.em.ThrowException(e, "原料データ2操作管理処理が失敗しました。");
		} finally {
			if (execDB != null) {
				//セッションのクローズ
				execDB.Close();
				execDB = null;
			}
		}

		return resKind;
	}

	/**
	 * 原料分析情報マスタ登録用SQL作成
	 * @param reqData : リクエストデータ
	 * @param intKakuteiChk : 確定チェック [0:原料コードによる登録, 1:確定コードによる登録]
	 * @return 作成SQL
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private StringBuffer createInsertGenryoSQL(RequestResponsKindBean reqData, int intKakuteiChk) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		StringBuffer strSQL_M401 = new StringBuffer();

		try {

			// 機能リクエストデータの取得
			String strReqKaishaCd = reqData.getFieldVale(0, 0, "cd_kaisha");
			String strReqGenryoCd = reqData.getFieldVale(0, 0, "cd_genryo");
			String strReqSakusanRitu = reqData.getFieldVale(0, 0, "ritu_sakusan");
			String strReqShokuenRitu = reqData.getFieldVale(0, 0, "ritu_shokuen");
			String strReqSousanRitu = reqData.getFieldVale(0, 0, "ritu_sousan");
			String strReqAburaRitu = reqData.getFieldVale(0, 0, "ritu_abura");
			String strReqHyojian = reqData.getFieldVale(0, 0, "hyojian");
			String strReqTenkabutu = reqData.getFieldVale(0, 0, "tenkabutu");
			String strReqMemo = reqData.getFieldVale(0, 0, "memo");
			String strReqEiyoNo1 = reqData.getFieldVale(0, 0, "no_eiyo1");
			String strReqEiyoNo2 = reqData.getFieldVale(0, 0, "no_eiyo2");
			String strReqEiyoNo3 = reqData.getFieldVale(0, 0, "no_eiyo3");
			String strReqEiyoNo4 = reqData.getFieldVale(0, 0, "no_eiyo4");
			String strReqEiyoNo5 = reqData.getFieldVale(0, 0, "no_eiyo5");
			String strReqWariai1 = reqData.getFieldVale(0, 0, "wariai1");
			String strReqWariai2 = reqData.getFieldVale(0, 0, "wariai2");
			String strReqWariai3 = reqData.getFieldVale(0, 0, "wariai3");
			String strReqWariai4 = reqData.getFieldVale(0, 0, "wariai4");
			String strReqWariai5 = reqData.getFieldVale(0, 0, "wariai5");
			String strReqHaishiKbn = reqData.getFieldVale(0, 0, "kbn_haishi");
			String strReqKakuteiCd = reqData.getFieldVale(0, 0, "cd_kakutei");
			String strReqKakuninId = reqData.getFieldVale(0, 0, "id_kakunin");
			String strReqKakuninDt = reqData.getFieldVale(0, 0, "dt_kakunin");
			String strReqTorokuId = reqData.getFieldVale(0, 0, "id_toroku");
			String strReqTorokuDt = reqData.getFieldVale(0, 0, "dt_toroku");
// ADD start 20121005 QP@20505 No.24
			String strReqMsgRitu = "";
// MOD start 20121126 QP@20505 課題No.10
//			try{
//				strReqMsgRitu = reqData.getFieldVale(0, 0, "ritu_msg");
//			}catch(Exception exc){
//			}
			strReqMsgRitu = reqData.getFieldVale(0, 0, "ritu_msg");
// MOD end 20121126 QP@20505 課題No.10
// ADD end 20121005 QP@20505 No.24

			//パラメータ空白時の処理
			if ( strReqSakusanRitu.equals("") ) {
				strReqSakusanRitu = "NULL";
			}
			if ( strReqShokuenRitu.equals("") ) {
				strReqShokuenRitu = "NULL";
			}
			if ( strReqSousanRitu.equals("") ) {
				strReqSousanRitu = "NULL";
			}
			if ( strReqAburaRitu.equals("") ) {
				strReqAburaRitu = "NULL";
			}
// ADD start 20121005 QP@20505 No.24
// MOD start 20121126 QP@20505 課題No.10
//			if ( "".equals(strReqMsgRitu ) ) {
			if ( strReqMsgRitu.equals("") ) {
// MOD end 20121126 QP@20505 課題No.10
				strReqMsgRitu = "NULL";
			}
// ADD end 20121005 QP@20505 No.24
			if ( strReqHaishiKbn.equals("") ) {
				strReqHaishiKbn = "0";
			}
			if ( strReqKakuninId.equals("") ) {
				strReqKakuninId = "NULL";
			}

// MOD start 20190712 KPX@1900110
			//M401 原料分析情報マスタ　登録用SQL
//			strSQL_M401.append(" INSERT INTO ma_genryo VALUES ( ");
			strSQL_M401.append(" INSERT INTO ma_genryo");
			strSQL_M401.append(" ( ");
			strSQL_M401.append(" cd_kaisha ");
			strSQL_M401.append(" ,cd_genryo ");
			strSQL_M401.append(" ,ritu_sakusan ");
			strSQL_M401.append(" ,ritu_shokuen ");
			strSQL_M401.append(" ,ritu_sousan ");
			strSQL_M401.append(" ,ritu_abura ");
			strSQL_M401.append(" ,hyojian ");
			strSQL_M401.append(" ,tenkabutu ");
			strSQL_M401.append(" ,memo ");
			strSQL_M401.append(" ,no_eiyo1 ");
			strSQL_M401.append(" ,no_eiyo2 ");
			strSQL_M401.append(" ,no_eiyo3 ");
			strSQL_M401.append(" ,no_eiyo4 ");
			strSQL_M401.append(" ,no_eiyo5 ");
			strSQL_M401.append(" ,wariai1 ");
			strSQL_M401.append(" ,wariai2 ");
			strSQL_M401.append(" ,wariai3 ");
			strSQL_M401.append(" ,wariai4 ");
			strSQL_M401.append(" ,wariai5 ");
			strSQL_M401.append(" ,nisugata_hyoji ");
			strSQL_M401.append(" ,rank_ibutsu ");
			strSQL_M401.append(" ,shiyokahi_genryo ");
			strSQL_M401.append(" ,shiyokahi_riyu ");
			strSQL_M401.append(" ,shiyokahi_cd_genryo_daitai ");
			strSQL_M401.append(" ,shiyokahi_nm_genryo_daitai ");
			strSQL_M401.append(" ,trouble_joho ");
			strSQL_M401.append(" ,trouble_gaiyo ");
			strSQL_M401.append(" ,trouble_naiyo_shosai ");
			strSQL_M401.append(" ,sakkin_chomieki_yohi ");
			strSQL_M401.append(" ,sakkin_chomieki_joken ");
			strSQL_M401.append(" ,NB_genteigenryo ");
			strSQL_M401.append(" ,NB_joken ");
			strSQL_M401.append(" ,NB_riyu ");
			strSQL_M401.append(" ,kasseikoso ");
			strSQL_M401.append(" ,gosei_tenkabutu ");
			strSQL_M401.append(" ,biko ");
			strSQL_M401.append(" ,kbn_haishi ");
			strSQL_M401.append(" ,cd_kakutei ");
			strSQL_M401.append(" ,id_kakunin ");
			strSQL_M401.append(" ,dt_kakunin ");
			strSQL_M401.append(" ,id_toroku ");
			strSQL_M401.append(" ,dt_toroku ");
			strSQL_M401.append(" ,id_koshin ");
			strSQL_M401.append(" ,dt_koshin ");
			strSQL_M401.append(" ,ritu_msg ");
			strSQL_M401.append(" ) ");
			strSQL_M401.append(" VALUES ( ");
// MOD end 20190712 KPX@1900110

			strSQL_M401.append(" " + strReqKaishaCd);
			if ( intKakuteiChk == 0 ) {
				strSQL_M401.append(" ,'" + strReqGenryoCd + "'");
			} else {
				strSQL_M401.append(" ,'" + strReqKakuteiCd + "'");
			}
			strSQL_M401.append(" ," + strReqSakusanRitu);
			strSQL_M401.append(" ," + strReqShokuenRitu);
			strSQL_M401.append(" ," + strReqSousanRitu);
			strSQL_M401.append(" ," + strReqAburaRitu);
			strSQL_M401.append(" ,'" + strReqHyojian + "'");
			strSQL_M401.append(" ,'" + strReqTenkabutu + "'");
			strSQL_M401.append(" ,'" + strReqMemo + "'");
			strSQL_M401.append(" ,'" + strReqEiyoNo1 + "'");
			strSQL_M401.append(" ,'" + strReqEiyoNo2 + "'");
			strSQL_M401.append(" ,'" + strReqEiyoNo3 + "'");
			strSQL_M401.append(" ,'" + strReqEiyoNo4 + "'");
			strSQL_M401.append(" ,'" + strReqEiyoNo5 + "'");
			strSQL_M401.append(" ,'" + strReqWariai1 + "'");
			strSQL_M401.append(" ,'" + strReqWariai2 + "'");
			strSQL_M401.append(" ,'" + strReqWariai3 + "'");
			strSQL_M401.append(" ,'" + strReqWariai4 + "'");
			strSQL_M401.append(" ,'" + strReqWariai5 + "'");
// ADD start 20190712 KPX@1900110
			strSQL_M401.append(" ,NULL");
			strSQL_M401.append(" ,NULL");
			strSQL_M401.append(" ,NULL");
			strSQL_M401.append(" ,NULL");
			strSQL_M401.append(" ,NULL");
			strSQL_M401.append(" ,NULL");
			strSQL_M401.append(" ,NULL");
			strSQL_M401.append(" ,NULL");
			strSQL_M401.append(" ,NULL");
			strSQL_M401.append(" ,NULL");
			strSQL_M401.append(" ,NULL");
			strSQL_M401.append(" ,NULL");
			strSQL_M401.append(" ,NULL");
			strSQL_M401.append(" ,NULL");
			strSQL_M401.append(" ,NULL");
			strSQL_M401.append(" ,NULL");
			strSQL_M401.append(" ,NULL");
// ADD end 20190712 KPX@1900110
			strSQL_M401.append(" ," + strReqHaishiKbn);
			if ( intKakuteiChk == 0 ) {
				strSQL_M401.append(" ,'" + strReqKakuteiCd + "'");
			} else {
				strSQL_M401.append(" ,''");
			}
			strSQL_M401.append(" ," + strReqKakuninId);
			if ( !strReqKakuninDt.equals("") ) {
				strSQL_M401.append(" ,'" + strReqKakuninDt + "'");
			} else {
				strSQL_M401.append(" ,NULL");
			}
			strSQL_M401.append(" ," + strReqTorokuId);
			strSQL_M401.append(" ,'" + strReqTorokuDt + "'");
			strSQL_M401.append(" ," + strReqTorokuId);
			strSQL_M401.append(" ,'" + strReqTorokuDt + "'");
// ADD start 20121005 QP@20505 No.24
			strSQL_M401.append(" ," + strReqMsgRitu);
// ADD end 20121005 QP@20505 No.24
			strSQL_M401.append("  ) ");

		} catch (Exception e) {
			this.em.ThrowException(e, "原料データ2登録(M401)SQL作成処理が失敗しました。");
		} finally {
		}

		return strSQL_M401;
	}

	/**
	 * 原料マスタ登録用SQL作成
	 * @param reqData : リクエストデータ
	 * @param intKakuteiChk : 確定チェック [0:原料コードによる登録, 1:確定コードによる登録]
	 * @return 作成SQL
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private StringBuffer createInsertGenryokojoSQL(RequestResponsKindBean reqData, int intKakuteiChk) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		StringBuffer strSQL_M402 = new StringBuffer();

		try {

			// 機能リクエストデータの取得
			String strReqKaishaCd = reqData.getFieldVale(0, 0, "cd_kaisha");
			String strReqGenryoCd = reqData.getFieldVale(0, 0, "cd_genryo");
			String strReqGenryoNm = reqData.getFieldVale(0, 0, "nm_genryo");
			String strReqSakusanRitu = reqData.getFieldVale(0, 0, "ritu_sakusan");
			String strReqShokuenRitu = reqData.getFieldVale(0, 0, "ritu_shokuen");
			String strReqSousanRitu = reqData.getFieldVale(0, 0, "ritu_sousan");
			String strReqAburaRitu = reqData.getFieldVale(0, 0, "ritu_abura");
			String strReqHaishiKbn = reqData.getFieldVale(0, 0, "kbn_haishi");
			String strReqKakuninId = reqData.getFieldVale(0, 0, "id_kakunin");
			String strReqKakuteiCd = reqData.getFieldVale(0, 0, "cd_kakutei");
			String strReqTorokuId = reqData.getFieldVale(0, 0, "id_toroku");
			String strReqTorokuDt = reqData.getFieldVale(0, 0, "dt_toroku");
// ADD start 20121005 QP@20505 No.24
			String strReqMsgRitu = "";
// MOD start 20121126 QP@20505 課題No.10
//			try{
//				strReqMsgRitu = reqData.getFieldVale(0, 0, "ritu_msg");
//			}catch(Exception e){
//			}
			strReqMsgRitu = reqData.getFieldVale(0, 0, "ritu_msg");
// MOD end 20121126 QP@20505 課題No.10
// ADD end 20121005 QP@20505 No.24

			//パラメータ空白時の処理
			if ( strReqSakusanRitu.equals("") ) {
				strReqSakusanRitu = "NULL";
			}
			if ( strReqShokuenRitu.equals("") ) {
				strReqShokuenRitu = "NULL";
			}
			if ( strReqSousanRitu.equals("") ) {
				strReqSousanRitu = "NULL";
			}
			if ( strReqAburaRitu.equals("") ) {
				strReqAburaRitu = "NULL";
			}
// ADD start 20121005 QP@20505 No.24
// MOD start 20121126 QP@20505 課題No.10
//			if ( "".equals(strReqMsgRitu) ) {
			if ( strReqMsgRitu.equals("") ) {
// MOD end 20121126 QP@20505 課題No.10
				strReqMsgRitu = "NULL";
			}
// ADD end 20121005 QP@20505 No.24
			if ( strReqHaishiKbn.equals("") ) {
				strReqHaishiKbn = "0";
			}
			if ( strReqKakuninId.equals("") ) {
				strReqKakuninId = "NULL";
			}
// MOD start 20190712 KPX@1900110
			//M402 原料マスタ　登録用SQL
//			strSQL_M402.append("INSERT INTO ma_genryokojo VALUES ( ");
			strSQL_M402.append("INSERT INTO ma_genryokojo ");
			strSQL_M402.append(" ( ");
			strSQL_M402.append(" cd_kaisha ");
			strSQL_M402.append(" ,cd_genryo ");
			strSQL_M402.append(" ,cd_busho ");
			strSQL_M402.append(" ,nm_genryo ");
			strSQL_M402.append(" ,tanka ");
			strSQL_M402.append(" ,budomari ");
			strSQL_M402.append(" ,nisugata ");
			strSQL_M402.append(" ,kikakusho ");
			strSQL_M402.append(" ,dt_konyu ");
			strSQL_M402.append(" ,id_toroku ");
			strSQL_M402.append(" ,dt_toroku ");
			strSQL_M402.append(" ,id_koshin ");
			strSQL_M402.append(" ,dt_koshin ");
			strSQL_M402.append(" ,flg_shiyo ");
			strSQL_M402.append(" ,flg_mishiyo ");
			strSQL_M402.append(" ) ");
			strSQL_M402.append(" VALUES ( ");
// MOD end 20190712 KPX@1900110
			strSQL_M402.append("  " + strReqKaishaCd);
			if ( intKakuteiChk == 0 ) {
				strSQL_M402.append(" ,'" + strReqGenryoCd +  "'");
			} else {
				strSQL_M402.append(" ,'" + strReqKakuteiCd +  "'");
			}
			strSQL_M402.append(" ," + ConstManager.getConstValue(Category.設定, "SHINKIGENRYO_BUSHOCD"));
			strSQL_M402.append(" ,'" + strReqGenryoNm +  "'");
			strSQL_M402.append(" ,NULL");
			strSQL_M402.append(" ,NULL");
			strSQL_M402.append(" ,NULL");
			strSQL_M402.append(" ,NULL");
			strSQL_M402.append(" ,NULL");
			strSQL_M402.append(" ," + strReqTorokuId);
			strSQL_M402.append(" ,'" + strReqTorokuDt + "'");
			strSQL_M402.append(" ," + strReqTorokuId);
			strSQL_M402.append(" ,'" + strReqTorokuDt + "'");
//add start --------------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.4
			strSQL_M402.append(" ,0");			//使用実績
			strSQL_M402.append(" ,0");			//未使用
//add end --------------------------------------------------------------------------------------
			// ADD start 20121005 QP@20505 No.24
			// ADD end 20121005 QP@20505 No.24
			strSQL_M402.append(")");

		} catch (Exception e) {
			this.em.ThrowException(e, "原料データ2登録(M402)SQL作成処理が失敗しました。");
		} finally {
		}

		return strSQL_M402;
	}

	/**
	 * 原料データ2登録SQL作成
	 *  : 登録S用SQLを作成し、原料分析情報マスタにパラメータを登録する
	 *   ※ 対象テーブル : ma_genryo, ma_genryokojo
	 * @param reqData : 機能リクエストデータ
	 * @param intKakuteiChk : 確定チェック [0:原料コードによる登録, 1:確定コードによる登録]
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private String genryoKanri2InsertSQL(RequestResponsKindBean reqData, int intKakuteiChk)
			throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		StringBuffer strSQL_M401 = null;
		StringBuffer strSQL_M402 = null;
		String strRetNewCode = "";

		try {

//2009/09/29 TT.A.ISONO ADD START [新規原料CDは登録時に採番するよう変更する。]

//			//①：原料データ登録用SQL作成処理を呼び出し、SQLを取得する。
//			strSQL_M401 = createInsertGenryoSQL(reqData, intKakuteiChk);
//			strSQL_M402 = createInsertGenryokojoSQL(reqData, intKakuteiChk);
//
//			//②：データベース管理を用い、原料データの削除を行う。
//			super.createExecDB();								//DB更新の生成
//			this.execDB.BeginTran();							//トランザクション開始

			//新規原料のｺｰﾄﾞを採番する
			StringBuffer strSQL = new StringBuffer();
			StringBuffer strUpdSQL = new StringBuffer();
			List<?> lstSearchAry = null;

			//採番マスタ.キー1 = 原料コード
			//採番マスタ.キー2 = N
			strSQL.append(" SELECT MAX(M601.no_seq)+1 AS no_seq_max ");
			strSQL.append(" FROM ma_saiban M601 WITH(UPDLOCK, ROWLOCK)");
			strSQL.append(" WHERE M601.key1='原料コード' AND M601.key2='N' ");

			try{

				//②：作成したSQLを用いて、検索処理を行い、新規発行コードを取得する。
				super.createSearchDB();
				searchDB.BeginTran();
				lstSearchAry = this.searchDB.dbSearch(strSQL.toString());

				//新規発行コードに1を格納する
				if ( lstSearchAry.size() >= 0 ) {
					//新規発行コード　：　採番マスタ.採番の最大値+1
					strRetNewCode = lstSearchAry.get(0).toString();

				} else {
					//検索結果が存在しなかった場合
					strRetNewCode = "1";

				}

				//ユーザ情報 : ユーザID
				String strUserId = "";
				//ユーザ情報のユーザIDを取得
				strUserId = userInfoData.getId_user();

				//③：下記条件を用いて、採番マスタ（ma_saiban）.採番の自動採番用SQLを作成する。

				//採番マスタ.キー1 = 原料コード
				//採番マスタ.キー2 = N
				strUpdSQL.append(" UPDATE ma_saiban ");
				strUpdSQL.append("   SET no_seq =" + strRetNewCode);
				strUpdSQL.append("      ,id_koshin =" + strUserId);
				strUpdSQL.append("      ,dt_koshin = GETDATE() ");
				strUpdSQL.append(" WHERE key1='原料コード' AND key2='N' ");

				//④：作成したSQLを用いて、更新処理を行う。
				super.createExecDB();								//DB更新の生成
				execDB.setSession(searchDB.getSession());
				this.execDB.execSQL(strUpdSQL.toString());		//SQL実行

				//ｺｰﾄﾞの採番
				strRetNewCode = "000000" + strRetNewCode;
				strRetNewCode = "N" +
				strRetNewCode.substring(strRetNewCode.length()-5,strRetNewCode.length()
						);

				//リクエストデータに採番したｺｰﾄﾞを反映する
				reqData.setFieldVale(0, 0, "cd_genryo", strRetNewCode);

				//①：原料データ登録用SQL作成処理を呼び出し、SQLを取得する。
				strSQL_M401 = createInsertGenryoSQL(reqData, intKakuteiChk);
				strSQL_M402 = createInsertGenryokojoSQL(reqData, intKakuteiChk);

//2009/09/29 TT.A.ISONO ADD END   [新規原料CDは登録時に採番するよう変更する。]

//			try{
				//M401 原料分析情報マスタ　登録用SQL実行
				this.execDB.execSQL(strSQL_M401.toString());
				//M402 原料マスタ　登録用SQL実行
				this.execDB.execSQL(strSQL_M402.toString());

				//コミット　※トランザクションはsearchDBで開始（BeginTran）しているので、コミットもsearchDBで行います。
				this.searchDB.Commit();

			}catch(Exception e){
				//ロールバック　※トランザクションはsearchDBで開始（BeginTran）しているので、ロールバックもsearchDBで行います。
				this.searchDB.Rollback();

			}

		} catch (Exception e) {
			this.em.ThrowException(e, "原料データ2登録SQL作成処理が失敗しました。");

		} finally {
			//変数の削除
			strSQL_M401 = null;
			strSQL_M402 = null;
		}


		//採番コード返却
		return strRetNewCode;

	}

	/**
	 * 原料データ2更新SQL作成
	 *  : 更新用SQLを作成し、原料マスタを更新する
	 *   ※ 対象テーブル : ma_genryo, ma_genryokojo
	 * @param reqData : 機能リクエストデータ
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private void genryoKanri2UpdateSQL(RequestResponsKindBean reqData)
			throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		StringBuffer strSQL_M401 = new StringBuffer();
		StringBuffer strSQL_M402 = new StringBuffer();

		try {

			//①：リクエストデータより、原料データ更新を行う為のSQLを作成する。

			// 機能リクエストデータの取得
			String strReqKaishaCd = reqData.getFieldVale(0, 0, "cd_kaisha");
			String strReqGenryoCd = reqData.getFieldVale(0, 0, "cd_genryo");
			String strReqGenryoNm = reqData.getFieldVale(0, 0, "nm_genryo");
			String strReqSakusanRitu = reqData.getFieldVale(0, 0, "ritu_sakusan");
			String strReqShokuenRitu = reqData.getFieldVale(0, 0, "ritu_shokuen");
			String strReqSousanRitu = reqData.getFieldVale(0, 0, "ritu_sousan");
			String strReqAburaRitu = reqData.getFieldVale(0, 0, "ritu_abura");
			String strReqHyojian = reqData.getFieldVale(0, 0, "hyojian");
			String strReqTenkabutu = reqData.getFieldVale(0, 0, "tenkabutu");
			String strReqMemo = reqData.getFieldVale(0, 0, "memo");
			String strReqEiyoNo1 = reqData.getFieldVale(0, 0, "no_eiyo1");
			String strReqEiyoNo2 = reqData.getFieldVale(0, 0, "no_eiyo2");
			String strReqEiyoNo3 = reqData.getFieldVale(0, 0, "no_eiyo3");
			String strReqEiyoNo4 = reqData.getFieldVale(0, 0, "no_eiyo4");
			String strReqEiyoNo5 = reqData.getFieldVale(0, 0, "no_eiyo5");
			String strReqWariai1 = reqData.getFieldVale(0, 0, "wariai1");
			String strReqWariai2 = reqData.getFieldVale(0, 0, "wariai2");
			String strReqWariai3 = reqData.getFieldVale(0, 0, "wariai3");
			String strReqWariai4 = reqData.getFieldVale(0, 0, "wariai4");
			String strReqWariai5 = reqData.getFieldVale(0, 0, "wariai5");
			String strReqHaishiKbn = reqData.getFieldVale(0, 0, "kbn_haishi");
			String strReqKakuteiCd = reqData.getFieldVale(0, 0, "cd_kakutei");
			String strReqKakuninId = reqData.getFieldVale(0, 0, "id_kakunin");
			String strReqKakuninDt = reqData.getFieldVale(0, 0, "dt_kakunin");
//			String strReqKakuninKbn = reqData.getFieldVale(0, 0, "kbn_kakunin");
//			String strReqTorokuId = reqData.getFieldVale(0, 0, "id_toroku");
//			String strReqTorokuDt = reqData.getFieldVale(0, 0, "dt_toroku");
			String strFgHenkou = reqData.getFieldVale(0, 0, "fg_henkou");
			// ADD start 20121005 QP@20505 No.24
			String strReqMsgRitu = null;
// MOD start 20121126 QP@20505 課題No.10
//			//分析値入力画面からの更新の場合MSGは更新しない
//			try{
//				strReqMsgRitu = reqData.getFieldVale(0, 0, "ritu_msg");
//			}catch(Exception exc){
//			}
			strReqMsgRitu = reqData.getFieldVale(0, 0, "ritu_msg");
// MOD end 20121126 QP@20505 課題No.10
			// ADD end 20121005 QP@20505 No.24

			//パラメータ空白時の処理
			if ( strReqSakusanRitu.equals("") ) {
				strReqSakusanRitu = "NULL";
			}
			if ( strReqShokuenRitu.equals("") ) {
				strReqShokuenRitu = "NULL";
			}
			if ( strReqSousanRitu.equals("") ) {
				strReqSousanRitu = "NULL";
			}
			if ( strReqAburaRitu.equals("") ) {
				strReqAburaRitu = "NULL";
			}
// ADD start 20121005 QP@20505 No.24
// MOD start 20121126 QP@20505 課題No.10
			//分析値入力画面からの更新の場合MSGは更新しない
//			if( "".equals(strReqMsgRitu) ) {
			if( strReqMsgRitu.equals("") ) {
// MOD end 20121126 QP@20505 課題No.10
				strReqMsgRitu = "NULL";
			}
// ADD end 20121005 QP@20505 No.24
			if ( strReqHaishiKbn.equals("") ) {
				strReqHaishiKbn = "0";
			}
//			if ( strReqKakuteiCd.equals("") ) {
//				strReqKakuteiCd = "NULL";
//			}
			if ( strReqKakuninId.equals("") ) {
				strReqKakuninId = "NULL";
			}

			//SQL作成

			//M401 原料分析情報マスタ　更新用SQL
			strSQL_M401.append("UPDATE ma_genryo SET ");
			strSQL_M401.append("       ritu_sakusan = " + strReqSakusanRitu);
			strSQL_M401.append("      ,ritu_shokuen = " + strReqShokuenRitu);
			strSQL_M401.append("      ,ritu_sousan = " + strReqSousanRitu);
			strSQL_M401.append("      ,ritu_abura = " + strReqAburaRitu);
			strSQL_M401.append("      ,hyojian = '" + strReqHyojian + "'");
			strSQL_M401.append("      ,tenkabutu = '" + strReqTenkabutu + "'");
			strSQL_M401.append("      ,memo = '" + strReqMemo + "'");
			strSQL_M401.append("      ,no_eiyo1 = '" + strReqEiyoNo1 + "'");
			strSQL_M401.append("      ,no_eiyo2 = '" + strReqEiyoNo2 + "'");
			strSQL_M401.append("      ,no_eiyo3 = '" + strReqEiyoNo3 + "'");
			strSQL_M401.append("      ,no_eiyo4 = '" + strReqEiyoNo4 + "'");
			strSQL_M401.append("      ,no_eiyo5 = '" + strReqEiyoNo5 + "'");
			strSQL_M401.append("      ,wariai1 = '" + strReqWariai1 + "'");
			strSQL_M401.append("      ,wariai2 = '" + strReqWariai2 + "'");
			strSQL_M401.append("      ,wariai3 = '" + strReqWariai3 + "'");
			strSQL_M401.append("      ,wariai4 = '" + strReqWariai4 + "'");
			strSQL_M401.append("      ,wariai5 = '" + strReqWariai5 + "'");
			strSQL_M401.append("      ,kbn_haishi = " + strReqHaishiKbn);
			strSQL_M401.append("      ,cd_kakutei = '" + strReqKakuteiCd + "'");
//add start --------------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.13
//			strSQL_M401.append("      ,id_kakunin = " + strReqKakuninId);
			//確認情報　0:NULL格納, 1:ユーザ情報格納
			if ( !strReqKakuninDt.equals("") ) {
//				strSQL_M401.append("      ,dt_kakunin = '" + strReqKakuninDt + "'");
				strSQL_M401.append("      ,id_kakunin = " + userInfoData.getId_user());
				strSQL_M401.append("      ,dt_kakunin = GETDATE()");
				if(strFgHenkou.equals("true")){
					strSQL_M401.append("     ,id_koshin = " + userInfoData.getId_user());
					strSQL_M401.append("     ,dt_koshin = GETDATE()");
				}

			} else {
				strSQL_M401.append("      ,id_kakunin = NULL");
				strSQL_M401.append("      ,dt_kakunin = NULL");
				strSQL_M401.append("     ,id_koshin = " + userInfoData.getId_user());
				strSQL_M401.append("     ,dt_koshin = GETDATE()");
			}
// ADD start 20121005 QP@20505 No.24
// MOD start 20121126 QP@20505 課題No.10
//			//分析値入力画面からの更新の場合MSGは更新しない
//			if(strReqMsgRitu != null){
//				strSQL_M401.append("      ,ritu_msg = " + strReqMsgRitu);
//			}
			strSQL_M401.append("      ,ritu_msg = " + strReqMsgRitu);
// MOD end 20121126 QP@20505 課題No.10
// ADD end 20121005 QP@20505 No.24
//add end --------------------------------------------------------------------------------------
//			strSQL_M401.append("      ,id_koshin = " + strReqTorokuId);
//			strSQL_M401.append("      ,dt_koshin = '" + strReqTorokuDt + "'");
//			strSQL_M401.append("     ,id_koshin = " + userInfoData.getId_user());
//			strSQL_M401.append("     ,dt_koshin = GETDATE()");
			strSQL_M401.append(" WHERE cd_kaisha = " + strReqKaishaCd);
			strSQL_M401.append("  AND  cd_genryo = '" + strReqGenryoCd + "'");

			//M402 原料マスタ　更新用SQL
			strSQL_M402.append("UPDATE ma_genryokojo SET");
			strSQL_M402.append(" nm_genryo = '" + strReqGenryoNm + "'");
//			strSQL_M402.append(" ,id_koshin = " + strReqTorokuId);
//			strSQL_M402.append(" ,dt_koshin = '" + strReqTorokuDt + "'");
			strSQL_M402.append(" ,id_koshin = " + userInfoData.getId_user());
			strSQL_M402.append(" ,dt_koshin = GETDATE()");
			strSQL_M402.append(" WHERE cd_kaisha = " + strReqKaishaCd);
			strSQL_M402.append("  AND  cd_busho = " + ConstManager.getConstValue(Category.設定, "SHINKIGENRYO_BUSHOCD"));
			strSQL_M402.append("  AND  cd_genryo = '" + strReqGenryoCd + "'");

			//②：データベース管理を用い、原料データの削除を行う。
			super.createExecDB();								//DB更新の生成
			this.execDB.BeginTran();							//トランザクション開始

			try{
				//M401 原料分析情報マスタ　更新用SQL実行
				this.execDB.execSQL(strSQL_M401.toString());
				//M402 原料マスタ　更新用SQL実行
				this.execDB.execSQL(strSQL_M402.toString());

				this.execDB.Commit();							//コミット

			}catch(Exception e){
				this.execDB.Rollback();							//ロールバック
			}

		} catch (Exception e) {
			this.em.ThrowException(e, "原料データ2更新SQL作成処理が失敗しました。");
		} finally {
			//変数の削除
			strSQL_M401 = null;
			strSQL_M402 = null;
		}

	}

	/**
	 * 管理結果パラメーター格納
	 *  : 原料データ2の管理結果情報をレスポンスデータへ格納する。
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private void storageGenryoDataKanri2(String genryo, RequestResponsTableBean resTable)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		try {

			//処理結果の格納
			resTable.addFieldVale(0, "flg_return", "true");
			resTable.addFieldVale(0, "msg_error", "");
			resTable.addFieldVale(0, "no_errmsg", "");
			resTable.addFieldVale(0, "nm_class", "");
			resTable.addFieldVale(0, "cd_error", "");
			resTable.addFieldVale(0, "msg_system", "");

			resTable.addFieldVale(0, "cd_genryo", genryo);

		} catch (Exception e) {
			this.em.ThrowException(e, "管理結果パラメーター格納処理が失敗しました。");

		} finally {

		}

	}

}
