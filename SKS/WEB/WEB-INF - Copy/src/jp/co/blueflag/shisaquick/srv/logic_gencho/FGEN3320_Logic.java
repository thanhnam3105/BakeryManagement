package jp.co.blueflag.shisaquick.srv.logic_gencho;

import java.util.List;

import jp.co.blueflag.shisaquick.srv.base.LogicBaseJExcel;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsKindBean;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsTableBean;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionManager.ExceptionKind;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;

/**
 * 資材情報削除処理
 *  機能ID：FGEN3320　
 *
 * @author TT.Shima
 * @since  2014/10/07
 */
public class FGEN3320_Logic extends LogicBaseJExcel {

	/**
	 * 資材手配情報更新コンストラクタ
	 * : インスタンス生成
	 */
	public FGEN3320_Logic(){
		super();
	}

	/**
	 * 資材手配情報削除処理を行う
	 * @param reqData : リクエストデータ
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

			// 削除処理
			deleteSQL(reqData);

			// コミット
			execDB.Commit();

			//機能IDの設定
			String strKinoId = reqData.getID();
			resKind.setID(strKinoId);

			//テーブル名の設定
			String strTableNm = reqData.getTableID(0);
			resKind.addTableItem(strTableNm);

			// レスポンスデータの格納
			storageData(resKind.getTableItem(0));

		} catch (Exception e) {
			// ロールバック
			execDB.Rollback();
			this.em.ThrowException(e, "資材手配情報削除処理に失敗しました。");

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

	/**
	 * 削除処理
	 *  : 資材手配情報の削除を行う
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private void deleteSQL(
			RequestResponsKindBean reqData)
					throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		StringBuffer strSQL;
		List<?> lstRecset;
		String allkey = "";

		try {
			for(int i = 0;i < reqData.getCntRow(reqData.getTableID(0)); i++){

				//リクエストデータより試作コード取得
				String strReqShain           = toString(reqData.getFieldVale(0, i, "cd_shain"));
				String strReqNen             = toString(reqData.getFieldVale(0, i, "nen"));
				String strReqNoOi            = toString(reqData.getFieldVale(0, i, "no_oi"));
				String strReqShizai          = toString(reqData.getFieldVale(0, i, "seq_shizai"));
				String strReqEda             = toString(reqData.getFieldVale(0, i, "no_eda"));
				String strShohinCd           = toString(reqData.getFieldVale(0, i, "cd_shohin"));
				String strKbn                = toString(reqData.getFieldVale(0, i, "kbn"));
				int inteqShain = Integer.parseInt(strReqShain);
				int intReqNen = Integer.parseInt(strReqNen);
				int inteqNoOi =Integer.parseInt(strReqNoOi);
				int intReqShizai =Integer.parseInt(strReqShizai);
				int intReqEda =Integer.parseInt(strReqEda);


				//SELECT文作成
				strSQL = new StringBuffer();
				strSQL.append(" SELECT ");
				strSQL.append("   cd_shain ");
				strSQL.append(" FROM ");
				strSQL.append("    tr_shizai_tehai ");
				strSQL.append(" WHERE ");
				strSQL.append("        cd_shain = " + inteqShain);
				strSQL.append("    AND nen = " + intReqNen);
				strSQL.append("    AND no_oi = " + inteqNoOi);
				strSQL.append("    AND seq_shizai = " + intReqShizai);
				strSQL.append("    AND no_eda = " + intReqEda);

				//共通クラス　データベース管理を用い、SQLを実行
				lstRecset = this.searchDB.dbSearch(strSQL.toString());

				// データが存在しない
				if(lstRecset.isEmpty()){
					em.ThrowException(ExceptionKind.警告Exception, "W000500", "", "", "");
				}

				// SQL作成
				strSQL = new StringBuffer();
				strSQL.append(" DELETE ");
				strSQL.append(" FROM ");
				strSQL.append("   tr_shizai_tehai ");
				strSQL.append(" WHERE ");
				strSQL.append("        cd_shain = " + inteqShain);
				strSQL.append("    AND nen = " + intReqNen);
				strSQL.append("    AND no_oi = " + inteqNoOi);
				strSQL.append("    AND seq_shizai = " + intReqShizai);
				strSQL.append("    AND no_eda = " + intReqEda);

				//共通クラス　データベース管理を用い、SQLを実行
				this.execDB.execSQL(strSQL.toString());

				allkey = getAllKey(reqData);

				strSQL = new StringBuffer();
				strSQL.append(" SELECT ");
				strSQL.append("   cd_shain ");
				strSQL.append(" FROM ");
				strSQL.append("    tr_shizai_tehai_temp ");
				strSQL.append(" WHERE ");
				strSQL.append("        cd_tmp_group_key = '" + allkey + "'");
				strSQL.append("    AND cd_shain = " + inteqShain);
				strSQL.append("    AND nen = " + intReqNen);
				strSQL.append("    AND no_oi = " + inteqNoOi);
				strSQL.append("    AND seq_shizai = " + intReqShizai);
				strSQL.append("    AND no_eda = " + intReqEda);
				strSQL.append("    AND kbn_shizai = '" + strKbn + "'");

				//共通クラス　データベース管理を用い、SQLを実行
				lstRecset = this.searchDB.dbSearch_notError(strSQL.toString());

				// データが存在しない
				if(lstRecset.size() != 0){
					strSQL = new StringBuffer();
					strSQL.append(" DELETE ");
					strSQL.append(" FROM ");
					strSQL.append("   tr_shizai_tehai_temp ");
					strSQL.append(" WHERE ");
					strSQL.append("        cd_tmp_group_key = '" + allkey + "'");
					strSQL.append("    AND cd_shain = " + inteqShain);
					strSQL.append("    AND nen = " + intReqNen);
					strSQL.append("    AND no_oi = " + inteqNoOi);
					strSQL.append("    AND seq_shizai = " + intReqShizai);
					strSQL.append("    AND no_eda = " + intReqEda);
					strSQL.append("    AND kbn_shizai = '" + strKbn + "'");
					//共通クラス　データベース管理を用い、SQLを実行
					this.execDB.execSQL(strSQL.toString());
				}


				// クリア
				strSQL = null;
			}

		} catch (Exception e) {

			em.ThrowException(e, "資材手配情報削除DB処理に失敗しました。");

		} finally {

		}
	}

	/**
	 * テンポラリー用キー作成
	 * @param reqData
	 * @return
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private String getAllKey(RequestResponsKindBean reqData)
			throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		String allkey = "";
		for(int j = 0;j < reqData.getCntRow(reqData.getTableID(0)); j++){
			//リクエストデータより試作コード取得
			// 社員コード
			String strReqShain           = toString(reqData.getFieldVale(0, j, "cd_shain"));
			// 年
			String strReqNen             = toString(reqData.getFieldVale(0, j, "nen"));
			// 追番
			String strReqNoOi            = toString(reqData.getFieldVale(0, j, "no_oi"));
			// seq番号
			String strReqShizai          = toString(reqData.getFieldVale(0, j, "seq_shizai"));
			// 枝番
			String strReqEda             = toString(reqData.getFieldVale(0, j, "no_eda"));
			// 製品コード
			String strShohinCd           = toString(reqData.getFieldVale(0, j, "cd_shohin"));

			int intShainCd = Integer.parseInt(strReqShain);
			String shainCd = String.valueOf(intShainCd);

			int intNen = Integer.parseInt(strReqNen);
			String nen = String.valueOf(intNen);

			int intNoOi = Integer.parseInt(strReqNoOi);
			String noOi =String.valueOf(intNoOi);

			int intSeqShizai = Integer.parseInt(strReqShizai);
			String seqShizai = String.valueOf(intSeqShizai);

			int intNoEda = Integer.parseInt(strReqEda);
			String noEda = String.valueOf(intNoEda);

			String tempkey = shainCd + "_" + nen + "_" +  noOi
					 + "_" + seqShizai + "_" + noEda + "_" + strShohinCd + "_" ;
			allkey += tempkey;
		}
		return allkey;
	}

	/**
     * パラメーター格納
     *  : 資材手配依頼書出力画面へのレスポンスデータへ格納する。
     * @param lstGenkaHeader : 検索結果情報リスト
     * @throws ExceptionWaning
     * @throws ExceptionUser
     * @throws ExceptionSystem
     */
	private void storageData(RequestResponsTableBean resTable) throws ExceptionSystem,
			ExceptionUser, ExceptionWaning {

		try {
			//処理結果の格納
			resTable.addFieldVale(0, "flg_return", "true");
			resTable.addFieldVale(0, "msg_error", "");
			resTable.addFieldVale(0, "no_errmsg", "");
			resTable.addFieldVale(0, "nm_class", "");
			resTable.addFieldVale(0, "cd_error", "");
			resTable.addFieldVale(0, "msg_system", "");
			resTable.addFieldVale(0, "msg_cd", "0");

		} catch (Exception e) {
			this.em.ThrowException(e, "資材手配情報削除DB処理に失敗しました。");

		} finally {

		}
	}
}
