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
public class FGEN3690_Logic extends LogicBase{

	/**
	 * 資材手配入力：資材テーブル更新
	 * : インスタンス生成
	 */
	public FGEN3690_Logic() {
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
			// 社員コード
			String strCd_shain   = reqData.getFieldVale("table", 0, "cd_shain");
			// 年
			String strNen        = reqData.getFieldVale("table", 0, "nen");
			// 追番
			String strNo_oi      = reqData.getFieldVale("table", 0, "no_oi");
			// seq
			String strSeq_shizai = reqData.getFieldVale("table", 0, "seq_shizai");
			// 枝番
			String strNo_eda     = reqData.getFieldVale("table", 0, "no_eda");

			// 製品コード
			String strCdShohin = reqData.getFieldVale("table", 0, "cd_shohin");

			// 更新データ
			// 納入日
			String strNounyuDay   = reqData.getFieldVale("table", 0, "nounyu_day");
			// 資材手配テーブル更新
			// 版代
			String strHanPay   = reqData.getFieldVale("table", 0, "han_pay");
			// 版代支払日
			String strHanPayDay    = reqData.getFieldVale("table", 0, "han_payday");
			// 版代支払日の形式
			StringBuffer strbuf = new StringBuffer();
			strbuf = getHidukeFormat(strHanPayDay, strbuf);

			// ファイル名
			String strInputName      = reqData.getFieldVale("table", 0, "inputName");
			// ファイルパス
			String strFileName      = reqData.getFieldVale("table", 0, "filename");

			StringBuffer strShisanShizai = new StringBuffer();


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
            strSql.append(" FROM  tr_shizai_tehai  ");
            strSql.append(" WHERE cd_shain = "+ lngCd_shain );
            strSql.append("   AND nen = "     + intNen );
            strSql.append("   AND no_oi = "   + intNo_oi );
            strSql.append("   AND seq_shizai = " + intSeq_shizai );
            strSql.append("   AND no_eda = "  + intNo_eda );
           //strSql.append("   AND cd_shohin = '"  + strCdShohin + "'");

            super.createSearchDB();

            List<?> lstRecset = searchDB.dbSearch(strSql.toString());

            // 更新データが存在しない時、エラー
            if(lstRecset.size() < 1) {
            	em.ThrowException(ExceptionKind.一般Exception,"E000301","資材手配テーブル","試作No.","");
            }

            strSql = new StringBuffer();

            // SQL文の作成
            strSql.append("UPDATE  tr_shizai_tehai SET  ");
            strSql.append("   han_pay = '" + strHanPay + "'");				// 版代

            strSql.append("   , dt_han_payday = CONVERT(DateTime, '" + strbuf.toString() + "',111) ");		// 版代支払日
            strSql.append("   ,nm_file_aoyaki    = '" + strInputName + "'" );	// ファイル名
            // ファイルがあればファイルパスをアップデートする
            if (!strInputName.equals("")) {
            	strSql.append("   ,file_path_aoyaki   = '" + strFileName + "'");	// ファイルパス
            } else {
            	strSql.append("   ,file_path_aoyaki   = ''");	// ファイルパス
            }
            strSql.append("   ,id_koshin  = ");
            strSql.append(   userInfoData.getId_user());
            strSql.append("   ,dt_koshin  = ");
            strSql.append("  GETDATE()  ");
            strSql.append(" WHERE cd_shain = "+ lngCd_shain );
            strSql.append("   AND nen = "     + intNen );
            strSql.append("   AND no_oi = "   + intNo_oi );
            strSql.append("   AND seq_shizai = " + intSeq_shizai );
            strSql.append("   AND no_eda = "  + intNo_eda );
            //strSql.append("   AND cd_shohin = '"  + strCdShohin + "'");

            execDB.execSQL(strSql.toString());

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
		return strSql;
	}

	private StringBuffer getHidukeFormat(String date, StringBuffer strbuf) {
		String strMM;
		String strDD;
		String[] strTemps;
		if (!date.equals("")) {


			if (date.indexOf("/") != -1) {

				strTemps = date.split("/");

				// 年取り出し
				strbuf.append(strTemps[0].toString());
				if (strTemps[1].toString().length() == 1) {
					strMM = strTemps[1].toString();
					strbuf.append("0" + strMM);

				} else {
					strbuf.append(strTemps[1].toString());
				}
				if (strTemps[2].toString().length() == 1) {

					strDD = strTemps[2].toString();
					strbuf.append("0" + strDD);
				} else {
					strbuf.append(strTemps[2].toString());
				}

			} else {
				strbuf.append(date);
			}
		} else {
			strbuf.append("");
		}

		return strbuf;
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
