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
 * 【QP@40404】
 *  資材手配入力：資材テーブル更新
 *  機能ID：FGEN3460
 *
 * @author E.Kitazawa
 * @since  2014/10/03
 */
public class FGEN3460_Logic extends LogicBase{

	/**
	 * 資材手配入力：資材テーブル更新
	 * : インスタンス生成
	 */
	public FGEN3460_Logic() {
		//基底クラスのコンストラクタ
		super();

	}

	//--------------------------------------------------------------------------------------------------------------
	//
	//                                                                     ExecLogic（処理開始）
	//
	//--------------------------------------------------------------------------------------------------------------

	/**
	 * 資材手配入力：資材テーブル更新
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

		StringBuffer strSql = new StringBuffer();

		RequestResponsKindBean resKind = null;

		try {

			//レスポンスデータ（機能）生成
			resKind = new RequestResponsKindBean();


			// トランザクション開始
			super.createExecDB();

			execDB.BeginTran();

			strSql = createSQL(reqData);

			// コミット
			execDB.Commit();

			//機能IDの設定
			String strKinoId = reqData.getID();

			resKind.setID(strKinoId);

            // テーブル名の設定
            String strTableNm = reqData.getTableID(0);

            resKind.addTableItem(strTableNm);

            // レスポンスデータの形成
            this.storageData(resKind.getTableItem(strTableNm));

		} catch (Exception e) {
			if (execDB != null) {
				// ロールバック
				execDB.Rollback();
			}

			this.em.ThrowException(e, "");

		} finally {
			if (searchDB != null) {
				//セッションのクローズ
				searchDB.Close();
				searchDB = null;
			}

			//変数の削除
			strSql = null;

		}
		return resKind;

	}

	/**
	 * データ取得SQL作成
	 * @param reqData：リクエストデータ
	 * @param strSql：検索条件SQL
	 * @return strSql：検索条件SQL
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private StringBuffer createSQL(
            RequestResponsKindBean reqData
			)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		// SQL格納用
		StringBuffer strSql = null;

		try {
			// 試作No.
			String strCd_shain   = reqData.getFieldVale("table", 0, "cd_shain");
			String strNen        = reqData.getFieldVale("table", 0, "nen");
			String strNo_oi      = reqData.getFieldVale("table", 0, "no_oi");
			String strSeq_shizai = reqData.getFieldVale("table", 0, "seq_shizai");
			String strNo_eda     = reqData.getFieldVale("table", 0, "no_eda");

			// 更新データ
			String strCd_shizai_new   = reqData.getFieldVale("table", 0, "cd_shizai_new");
			String strNm_shizai_new   = reqData.getFieldVale("table", 0, "nm_shizai_new");
			String strCd_seizokojo    = reqData.getFieldVale("table", 0, "cd_seizokojo");
			String strCd_shokuba      = reqData.getFieldVale("table", 0, "cd_shokuba");
			String strCd_line         = reqData.getFieldVale("table", 0, "cd_line");
			String strCd_seihin       = reqData.getFieldVale("table", 0, "cd_seihin");
			String strCd_taisyoshizai = reqData.getFieldVale("table", 0, "cd_taisyoshizai");
			String strCd_hattyusaki   = reqData.getFieldVale("table", 0, "cd_hattyusaki");
			String strChk_kanryo      = reqData.getFieldVale("table", 0, "chk_kanryo");
			//May Thu 2016/09/28 【KPX@1602367】 add start
			String strNm_file         = reqData.getFieldVale("table", 0, "nm_file_henshita");
			String strFile_path       = reqData.getFieldVale("table", 0, "file_path_henshita");
            //May Thu 2016/09/28 【KPX@1602367】 add end
			long lngCd_shain = 0;
			int intNen = 0;
			int intNo_oi = 0;
			int intSeq_shizai = 0;
			int intNo_eda = 0;

			// 検索キーに空白は許可しない
			if (strCd_shain.equals("") || strNen.equals("") || strNo_oi.equals("") || strSeq_shizai.equals("") || strNo_eda.equals("")) {
				em.ThrowException(ExceptionKind.一般Exception,"E000001","試作No","","");
			}

			// 数値 に変換
			lngCd_shain =  Long.parseLong(strCd_shain);
			intNen      =  Integer.parseInt(strNen);
			intNo_oi    =  Integer.parseInt(strNo_oi);
			intSeq_shizai =  Integer.parseInt(strSeq_shizai);
			intNo_eda   =  Integer.parseInt(strNo_eda);

            strSql = new StringBuffer();

            // SQL文の作成
            strSql.append(" SELECT cd_shain  ");
            strSql.append(" FROM  tr_shisan_shizai  ");
            strSql.append(" WHERE cd_shain = "+ lngCd_shain );
            strSql.append("   AND nen = "     + intNen );
            strSql.append("   AND no_oi = "   + intNo_oi );
            strSql.append("   AND seq_shizai = " + intSeq_shizai );
            strSql.append("   AND no_eda = "  + intNo_eda );

            super.createSearchDB();

            List<?> lstRecset = searchDB.dbSearch(strSql.toString());

            // 更新データが存在しない時、エラー
            if(lstRecset.size() < 1) {
            	em.ThrowException(ExceptionKind.一般Exception,"E000301","試算資材テーブル","試作No.","");
            }

            strSql = new StringBuffer();

            // SQL文の作成
            strSql.append("UPDATE  tr_shisan_shizai SET  ");
            strSql.append("    cd_shizai_new = '" + strCd_shizai_new + "'");
            strSql.append("   ,nm_shizai_new = '" + strNm_shizai_new + "'");
            strSql.append("   ,cd_seizokojo  = '" + strCd_seizokojo + "'");
            strSql.append("   ,cd_shokuba    = '" + strCd_shokuba + "'" );
            strSql.append("   ,cd_line   = '" + strCd_line + "'");
            strSql.append("   ,cd_seihin = '" + strCd_seihin + "'");
            strSql.append("   ,cd_taisyoshizai = '" + strCd_taisyoshizai + "'");
            strSql.append("   ,cd_hattyusaki   = '" + strCd_hattyusaki + "'");
            strSql.append("   ,chk_kanryo  = '" + strChk_kanryo + "'");
			//May Thu
			strSql.append("   ,nm_file_henshita  = '" + strNm_file + "'");
			strSql.append("   ,file_path_henshita  = '" + strFile_path + "'");
			//May Thu
            strSql.append("   ,id_koshin  = ");
            strSql.append(   userInfoData.getId_user());
            strSql.append("   ,dt_koshin  = ");
            strSql.append("  GETDATE()  ");
            strSql.append(" WHERE cd_shain = "+ lngCd_shain );
            strSql.append("   AND nen = "     + intNen );
            strSql.append("   AND no_oi = "   + intNo_oi );
            strSql.append("   AND seq_shizai = " + intSeq_shizai );
            strSql.append("   AND no_eda = "  + intNo_eda );

            execDB.execSQL(strSql.toString());

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
		return strSql;
	}

	/**
	 * パラメーター格納
	 *  : レスポンスデータへ格納する。
	 * @param lstGenkaHeader : 検索結果情報リスト
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private void storageData(
			RequestResponsTableBean resTable
			)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try {
            // 処理結果の格納
            resTable.addFieldVale(0, "flg_return", "true");
            resTable.addFieldVale(0, "msg_error", "");
            resTable.addFieldVale(0, "no_errmsg", "");
            resTable.addFieldVale(0, "nm_class", "");
            resTable.addFieldVale(0, "cd_error", "");
            resTable.addFieldVale(0, "msg_system", "");


		} catch (Exception e) {
			this.em.ThrowException(e, "");

		} finally {

		}

	}

}
