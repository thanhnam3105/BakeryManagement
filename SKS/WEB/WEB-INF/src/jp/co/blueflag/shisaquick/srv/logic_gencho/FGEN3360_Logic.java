package jp.co.blueflag.shisaquick.srv.logic_gencho;

import java.util.List;

import jp.co.blueflag.shisaquick.srv.base.LogicBase;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsKindBean;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsTableBean;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionManager.ExceptionKind;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;

/**
 * 資材情報更新処理
 *  機能ID：FGEN3360　
 *
 * @author TT.Shima
 * @since  2014/10/07
 */
public class FGEN3360_Logic extends LogicBase {

	/**
	 * 資材情報更新コンストラクタ
	 * : インスタンス生成
	 */
	public FGEN3360_Logic(){
		super();
	}

	/**
	 * 資材手配情報更新 管理操作
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

			// 更新処理
			updateSQL(reqData);

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
			this.em.ThrowException(e, "資材情報更新DB処理に失敗しました。");

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
	 * 更新処理
	 *  : 資材手配情報の更新を行う
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */

	//==========================================================================================
	//仮テープルに保存
	// SQL作成

	//=========================================================================================
	private void updateSQL(
			RequestResponsKindBean reqData)
					throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		StringBuffer strSQL;
		List<?> lstRecset;

		try {
			for(int i = 0;i < reqData.getCntRow(reqData.getTableID(0)); i++){

				//リクエストデータより試作コード取得
				// 社員コード
				String strReqShain      = reqData.getFieldVale(0, i, "cd_shain");
				int intShain = Integer.parseInt(strReqShain);
				// 年
				String strReqNen        = reqData.getFieldVale(0, i, "nen");
				int intNen = Integer.parseInt(strReqNen);
				// 追番
				String strReqNoOi       = reqData.getFieldVale(0, i, "no_oi");
				int intNoOi = Integer.parseInt(strReqNoOi);
				// 枝番
				String strReqNoEda      = reqData.getFieldVale(0, i, "no_eda");
				int intNoEda = Integer.parseInt(strReqNoEda);
				// seq番号
				String strSeqNo         = reqData.getFieldVale(0, i, "seq_no");
				int intSeqNo = Integer.parseInt(strSeqNo);
				// 内容
				String strNaiyo         = toString(reqData.getFieldVale(0, i,"naiyo"));
				// 製品
				String strCdShohin      = toString(reqData.getFieldVale(0, i, "cd_shohin"));
				String strCdShohinFormat = get0SupressCode(strCdShohin);

				// 製品名
				String strNmShohin      = toString(reqData.getFieldVale(0, i, "nm_shohin"));
				// 納入先
				String strNounyudaki    = toString(reqData.getFieldVale(0, i, "nounyusaki"));
				// 旧資材コード
				String strCdShizai      = toString(reqData.getFieldVale(0, i, "cd_shizai"));
				// 新資材コード
				String strCdShizaiNew   = toString(reqData.getFieldVale(0, i, "cd_shizai_new"));
				// 設計１
				String strSekkei1       = toString(reqData.getFieldVale(0, i, "sekkei1"));
				// 設計２
				String strSekkei2       = toString(reqData.getFieldVale(0, i, "sekkei2"));
				// 設計３
				String strSekkei3       = toString(reqData.getFieldVale(0, i, "sekkei3"));
				// 材質
				String strZaishitsu     = toString(reqData.getFieldVale(0, i, "zaishitsu"));
				// 印刷色
				String strPrintColor    = toString(reqData.getFieldVale(0, i, "printcolor"));
				// 色番号
				String strNoColor       = toString(reqData.getFieldVale(0, i, "no_color"));
				// 備考
				String strBiko          = toString(reqData.getFieldVale(0, i, "biko"));
				// 変更内容詳細
				String strHenkounaiyou  = toString(reqData.getFieldVale(0, i, "henkou"));
				// 納期
				String strNouki         = toString(reqData.getFieldVale(0, i, "nouki"));
				// 数量
				String strSuryo         = toString(reqData.getFieldVale(0, i, "suryo"));


				//SELECT文作成
				strSQL = new StringBuffer();
				strSQL.append(" SELECT ");
				strSQL.append("   cd_shain ");
				strSQL.append(" FROM ");
				strSQL.append("    tr_shizai_tehai ");
				strSQL.append(" WHERE ");
				strSQL.append("        cd_shain = " + intShain);
				strSQL.append("    AND nen = " + intNen);
				strSQL.append("    AND no_oi = " + intNoOi);
				strSQL.append("    AND no_eda = " + intNoEda);
				strSQL.append("    AND seq_shizai = " + intSeqNo);
				strSQL.append("    AND cd_shohin  = " + strCdShohinFormat);

				//共通クラス　データベース管理を用い、SQLを実行
				lstRecset = this.searchDB.dbSearch(strSQL.toString());

				// データが存在しない
				if(lstRecset.isEmpty()){
					em.ThrowException(ExceptionKind.警告Exception, "W000500", "", "", "");
				}

				// ステータスの更新(テンポラリテーブルの処理に実施する。チェックのみとする。）
				// SQL作成
				// 値の更新
				// SQL作成
//				strSQL = new StringBuffer();
//				strSQL.append(" UPDATE ");
//				strSQL.append("   tr_shizai_tehai ");
//				strSQL.append(" SET ");
////				strSQL.append("   flg_tehai_status = 2 ,");
//				strSQL.append("   naiyo   = '" + strNaiyo + "',");					// 内容（現状のままだと内容がかけない。）
//				strSQL.append("   nm_shohin   = '" + strNmShohin + "',");			// 製品名
//				strSQL.append("   nounyusaki   = '" + strNounyudaki + "',");		// 納入先
//				String strCdShizaiFormat = get0SupressShizaiCd(strCdShizai);
//				strSQL.append("   cd_shizai   = '" + strCdShizaiFormat + "',");			// 旧資材コード
//				String strCdShizaiNewFormat = get0SupressShizaiCd(strCdShizaiNew);
//				strSQL.append("   cd_shizai_new   = '" + strCdShizaiNewFormat + "',");	// 新資材コード
//				strSQL.append("   sekkei1 = '" + strSekkei1 + "', ");
//				strSQL.append("   sekkei2 = '" + strSekkei2 + "', ");
//				strSQL.append("   sekkei3 = '" + strSekkei3 + "', ");
//				strSQL.append("   zaishitsu = '" + strZaishitsu + "', ");
//				strSQL.append("   printcolor = '" + strPrintColor + "', ");
//				strSQL.append("   no_color = '" + strNoColor + "', ");
//				strSQL.append("   henkounaiyoushosai = '" + strHenkounaiyou + "', ");	// 変更内容詳細
//				strSQL.append("   nouki = '" + strNouki + "', ");						// 納期
//				strSQL.append("   suryo='" + strSuryo + "', ");						// 数量
//				strSQL.append("   id_koshin = '" + userInfoData.getId_user() + "' ");
//				strSQL.append(" WHERE ");
//				strSQL.append("        cd_shain = " + intShain);
//				strSQL.append("    AND nen = " + intNen);
//				strSQL.append("    AND no_oi = " + intNoOi);
//				strSQL.append("    AND no_eda = " + intNoEda);
//				strSQL.append("    AND seq_shizai = " + intSeqNo);
//				strSQL.append("    AND cd_shohin  = '" + strCdShohinFormat + "'");
//
//
//				//共通クラス　データベース管理を用い、SQLを実行
//				this.execDB.execSQL(strSQL.toString());



				// クリア
				strSQL = null;
			}

		} catch (Exception e) {

			em.ThrowException(e, "資材情報更新DB処理に失敗しました。");

		} finally {

		}
	}

	/**
	 * 0サプレスする
	 * @param str
	 * @return convertShizai
	 */
	private String get0SupressShizaiCd(String str) {
		int intCdShizai = 0;
		if (!str.equals("")) {
			 intCdShizai = Integer.parseInt(str);
		}
		return String.valueOf(intCdShizai);
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
			this.em.ThrowException(e, "資材情報更新DB処理に失敗しました。");

		} finally {

		}
	}
	/**
	 * 0サプレスする
	 * @param strCdShohin
	 * @return
	 */
	public String  get0SupressCode(String strCdShohin) {
		int intCdShohin = 0;
		if (!strCdShohin.equals("")) {
			 intCdShohin = Integer.parseInt(strCdShohin);
		}
		return String.valueOf(intCdShohin);
	}
}
