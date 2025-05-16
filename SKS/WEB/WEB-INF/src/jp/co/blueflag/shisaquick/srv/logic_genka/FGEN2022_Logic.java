package jp.co.blueflag.shisaquick.srv.logic_genka;

import java.util.List;

import jp.co.blueflag.shisaquick.srv.base.LogicBase;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsKindBean;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;

/**
 *
 * 原価試算項目固定値登録
 *  : 営業の確認完了済みデータ、原価試算画面の項目固定値チェックONで固定データが無い場合の対応。
 *    ※項目固定値チェックをOFF にすることで再計算データが取得できる
 *
 * @author TT.kitazawa
 * @since  2015/07/22
 *
 */

public class FGEN2022_Logic extends LogicBase {

	/**
	 * コンストラクタ
	 */
	public FGEN2022_Logic() {
		//基底クラスのコンストラクタ
		super();

	}

	//--------------------------------------------------------------------------------------------------------------
	//
	//              ExecLogic（処理開始）
	//
	//--------------------------------------------------------------------------------------------------------------

	/**
	 * 原価試算情報 管理操作
	 * @param reqData : 機能リクエストデータ
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

		//レスポンスデータクラス
		RequestResponsKindBean resKind = null;

		try {
			//レスポンスデータ（機能）生成
			resKind = new RequestResponsKindBean();

			// トランザクション開始
			super.createExecDB();
			execDB.BeginTran();

			//DBコネクション
			createSearchDB();

			StringBuffer strSQL = new StringBuffer();
			List<?> lstRecset = null;

			//リクエストデータより試作コード取得
			String strReqShainCd = reqData.getFieldVale("kihon", 0, "cd_shain");
			String strReqNen = reqData.getFieldVale("kihon", 0, "nen");
			String strReqOiNo = reqData.getFieldVale("kihon", 0, "no_oi");
			String strReqEda = reqData.getFieldVale("kihon", 0, "no_eda");
			String strRet = "0";

			//更新用SQL作成
			for ( int i=0; i<reqData.getCntRow("keisan"); i++ ) {
				lstRecset = null;

				String strReqSeq = reqData.getFieldVale("keisan", i, "seq_shisaku");
				// 項目固定チェック
				String strKoteiChk = reqData.getFieldVale("keisan", i, "fg_koumokuchk");

				// 項目固定チェック がOFFの場合は処理しない
				if (!strKoteiChk.equals("1")) {
					break;
				}

				//SELECT文作成
				strSQL = new StringBuffer();
				strSQL.append(" SELECT cd_shain FROM");
				strSQL.append("    tr_shisan_shisaku_kotei");
				strSQL.append(" WHERE");
				strSQL.append("    cd_shain=" + strReqShainCd);
				strSQL.append(" AND nen=" + strReqNen);
				strSQL.append(" AND no_oi=" + strReqOiNo);
				strSQL.append(" AND seq_shisaku=" + strReqSeq);
				strSQL.append(" AND no_eda=" + strReqEda);

				//共通クラス　データベース管理を用い、SQLを実行
				lstRecset = this.searchDB.dbSearch(strSQL.toString());

				if( lstRecset.isEmpty() == false ){
					break;
				}

				//項目固定チェックを一旦 ゼロにする
				strSQL = null;
				strSQL = new StringBuffer();
				strSQL.append(" UPDATE tr_shisan_shisaku ");
				strSQL.append(" SET fg_koumokuchk = 0 ");
				strSQL.append(" WHERE ");
				strSQL.append("     cd_shain=" + strReqShainCd);
				strSQL.append(" AND nen=" + strReqNen);
				strSQL.append(" AND no_oi=" + strReqOiNo);
				strSQL.append(" AND seq_shisaku=" + strReqSeq);
				strSQL.append(" AND no_eda=" + strReqEda);

				this.execDB.execSQL(strSQL.toString());
				// 処理結果
				strRet = "1";

			}
			// コミット
			execDB.Commit();

			//機能IDの設定
			String strKinoId = reqData.getID();
			resKind.setID(strKinoId);

			//テーブル名の設定
			String strTableNm = reqData.getTableID(0);
			resKind.addTableItem(strTableNm);

			resKind.getTableItem(strTableNm);

			//処理結果の格納
			resKind.getTableItem(strTableNm).addFieldVale(0, "flg_return", "true");
			resKind.getTableItem(strTableNm).addFieldVale(0, "msg_error", "");
			resKind.getTableItem(strTableNm).addFieldVale(0, "no_errmsg", "");
			resKind.getTableItem(strTableNm).addFieldVale(0, "nm_class", "");
			resKind.getTableItem(strTableNm).addFieldVale(0, "cd_error", "");
			resKind.getTableItem(strTableNm).addFieldVale(0, "msg_system", "");
			resKind.getTableItem(strTableNm).addFieldVale(0, "msg_cd", strRet);

		} catch (Exception e) {
			// ロールバック
			execDB.Rollback();
			this.em.ThrowException(e, "原価試算情報 管理操作処理が失敗しました。");

		} finally {
			//セッションのクローズ
			if (execDB != null) {
				execDB.Close();
				execDB = null;
			}
			if (searchDB != null) {
				searchDB.Close();
				searchDB = null;
			}
		}
		return resKind;
	}
}
