package jp.co.blueflag.shisaquick.srv.logic_gencho;

import java.util.List;

import jp.co.blueflag.shisaquick.srv.base.LogicBase;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsKindBean;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsTableBean;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;
import jp.co.blueflag.shisaquick.srv.common.ExceptionManager.ExceptionKind;

/**
 * 【QP@40404】
 *  資材手配入力：資材手配テーブル登録
 *  機能ID：FGEN3470
 *
 * @author E.Kitazawa
 * @since  2014/09/24
 */
public class FGEN3470_Logic extends LogicBase{

	/**
	 * 資材手配入力：資材手配テーブル登録
	 * : インスタンス生成
	 */
	public FGEN3470_Logic() {
		//基底クラスのコンストラクタ
		super();

	}

	//--------------------------------------------------------------------------------------------------------------
	//
	//                                                                     ExecLogic（処理開始）
	//
	//--------------------------------------------------------------------------------------------------------------

	/**
	 * 資材手配入力：資材手配テーブル登録
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
			// 検索条件（試作No.）
			String strCd_shain   = reqData.getFieldVale("table", 0, "cd_shain");
			String strNen        = reqData.getFieldVale("table", 0, "nen");
			String strNo_oi      = reqData.getFieldVale("table", 0, "no_oi");
			String strSeq_shizai = reqData.getFieldVale("table", 0, "seq_shizai");
			String strNo_eda     = reqData.getFieldVale("table", 0, "no_eda");
			// 検索時の更新日時
			String dt_tehai_koshin = reqData.getFieldVale("table", 0, "dt_tehai_koshin");

			// 登録データ
			// 商品コード
			String strCd_shohin      = reqData.getFieldVale("table", 0, "cd_seihin");
			// 品名
			String strNm_shohin      = reqData.getFieldVale("table", 0, "nm_shohin");
			// 旧資材コード
			String strCd_shizai      = reqData.getFieldVale("table", 0, "cd_shizai");
			// 新資材コード
			String strCd_shizai_new  = reqData.getFieldVale("table", 0, "cd_shizai_new");
			// 手配ステータス（未入力）
			int intTehai_status   = 1;

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
			strSql.append(" SELECT dt_koshin  ");
			strSql.append(" FROM  tr_shizai_tehai  ");
			strSql.append(" WHERE cd_shain = "+ lngCd_shain );
			strSql.append("   AND nen = "     + intNen );
			strSql.append("   AND no_oi = "   + intNo_oi );
			strSql.append("   AND seq_shizai = " + intSeq_shizai );
			strSql.append("   AND no_eda = "  + intNo_eda );

			super.createSearchDB();

			List<?> lstRecset = searchDB.dbSearch(strSql.toString());

			strSql = new StringBuffer();
			// データが存在する時、新資材コードを更新
			if(lstRecset.size() > 0) {
				String strMsg = "資材手配テーブル(Seq_shizai=" + strSeq_shizai + ") ";

				// 検索した時から更新されている場合
				if (toString(lstRecset.get(0)).compareTo(dt_tehai_koshin) > 0) {
					// 更新日時が新しくなった場合、エラーとする
					em.ThrowException(ExceptionKind.一般Exception,"E000342", strMsg, "","");
				}
	            // SQL文の作成
	            strSql.append("UPDATE  tr_shizai_tehai SET  ");
				strSql.append("  cd_shohin = '" + strCd_shohin + "'");
				strSql.append("   ,nm_shohin = '" + strNm_shohin + "'");
				strSql.append("   ,cd_shizai = '" + strCd_shizai + "'");
				strSql.append("   ,cd_shizai_new = '" + strCd_shizai_new + "'");
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

			} else {
				// データが存在しない時、レコード追加

				strSql.append("INSERT INTO tr_shizai_tehai (  ");
				strSql.append("  cd_shain,  ");
				strSql.append("  nen,  ");
				strSql.append("  no_oi,  ");
				strSql.append("  seq_shizai,  ");
				strSql.append("  no_eda,  ");
				strSql.append("  cd_shohin,  ");
				strSql.append("  nm_shohin,  ");
				strSql.append("  cd_shizai,  ");
				strSql.append("  cd_shizai_new,  ");
				strSql.append("  flg_tehai_status,  ");
				strSql.append("  id_toroku,  ");
				strSql.append("  dt_toroku,  ");
				strSql.append("  id_koshin,  ");
				strSql.append("  dt_koshin  ");
				strSql.append(")  ");
				strSql.append("VALUES  ");
				strSql.append("(  ");
				strSql.append("  " + lngCd_shain);
				strSql.append("  ," + intNen);
				strSql.append("  ,"  + intNo_oi);
				strSql.append("  ,"  + intSeq_shizai);
				strSql.append("  ," + intNo_eda);
				strSql.append("  ,  '"  + strCd_shohin  + "'");
				strSql.append("  ,  '"  + strNm_shohin  + "'");
				strSql.append("  ,  '"  + strCd_shizai  + "'");
				strSql.append("  ,  '"  + strCd_shizai_new  + "'");
				strSql.append("  ," + intTehai_status);

				strSql.append("  ," + userInfoData.getId_user());
				strSql.append("  , GETDATE()  ");
				strSql.append("  ," + userInfoData.getId_user());
				strSql.append("  , GETDATE()   ");
				strSql.append(")  ");

				execDB.execSQL(strSql.toString());
			}

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
