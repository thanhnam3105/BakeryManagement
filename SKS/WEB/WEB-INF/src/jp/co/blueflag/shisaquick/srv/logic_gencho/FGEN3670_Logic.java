package jp.co.blueflag.shisaquick.srv.logic_gencho;

import java.util.List;

import jp.co.blueflag.shisaquick.srv.base.LogicBaseJExcel;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsKindBean;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionManager.ExceptionKind;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;

public class FGEN3670_Logic extends LogicBaseJExcel {

	/**
	 * 発注先マスタExcel表を生成する
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

		//レスポンスデータ（機能）
		RequestResponsKindBean ret = null;
		//検索データ
		List<?> lstRecset = null;
		//エクセルファイルパス
		String downLoadPath = "";

		try {
			// DB検索
			super.createSearchDB();
			lstRecset = getData(reqData);

			// Excelファイル生成
			downLoadPath = makeExcelFile1(lstRecset, reqData);

			//レスポンスデータ生成
			ret = CreateRespons(downLoadPath, reqData);
			ret.setID(reqData.getID());


		} catch (Exception e) {
			em.ThrowException(e, "発注先マスタExcel表の生成に失敗しました。");

		} finally {
			//リストの破棄
			removeList(lstRecset);
			if (searchDB != null) {
				//DBセッションのクローズ
				searchDB.Close();
				searchDB = null;
			}
		}
		return reqData;

	}


	/**
	 * 対象の発注先マスターデータを検索する
	 * @param KindBean : リクエストデータ（機能）
	 * @return List : 検索結果のリスト
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning : 該当データ無し
	 */
	private List<?> getData(RequestResponsKindBean KindBean) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		//検索結果
		List<?> ret = null;
		//SQL　StringBuffer
		StringBuffer strSql = new StringBuffer();

		try {


			strSql = MakeSQLBuf(KindBean);

			//SQLを実行
			ret = searchDB.dbSearch(strSql.toString());


			//検索結果がない場合
			if (ret.size() == 0){
				em.ThrowException(ExceptionKind.警告Exception,"W000401", strSql.toString(), "", "");

			}

		} catch (Exception e) {
			em.ThrowException(e, "資材手配データ、DB検索に失敗しました。");

		} finally {
			//変数の削除
			strSql = null;

		}
		return ret;

	}

	private StringBuffer MakeSQLBuf(RequestResponsKindBean kindBean) throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		//SQL文の作成
		String cdhattyu = toString(kindBean.getFieldVale(0, 0, "cdhattyu"));
		String nmhattyu = toString(kindBean.getFieldVale(0, 0, "nmhattyu"));
		StringBuffer ret = new StringBuffer();

		String strCategoryCd = "C_hattyuusaki";
		try {

			//SQL文の作成
			ret.append("SELECT");
			ret.append("  RIGHT('000000' + convert(varchar,cd_literal), 6)");//EXCELの表示の0パディング対応
			ret.append(" ,nm_literal");
			ret.append(" ,no_sort");
			ret.append(" ,cd_2nd_literal");
			ret.append(" ,nm_2nd_literal");
			ret.append(" ,no_2nd_sort");
			ret.append(" ,mail_address");
			ret.append(" ,flg_mishiyo");
			ret.append(" FROM ma_literal");
			ret.append(" WHERE cd_category = '");
			ret.append(strCategoryCd);
			ret.append("'");
			if (cdhattyu != "") {
				ret.append(" AND cd_literal = '");
				ret.append(cdhattyu);
				ret.append("'");
			}
			if (nmhattyu != "") {
				ret.append(" AND cd_2nd_literal = '");
				ret.append(cdhattyu);
				ret.append("'");
			}
			ret.append("ORDER BY");
			ret.append(" flg_mishiyo");
			ret.append(" ,cd_literal");
			ret.append(" ,cd_2nd_literal");

		} catch (Exception e) {
			this.em.ThrowException(e, "資材手配データ、検索SQLの生成に失敗しました。");

		} finally {

		}

		return ret;
	}

	/**
	 * 発注先マスターExcelを生成する
	 * @param lstRecset : 検索データリスト
	 * @return　String : ダウンロード用のパス
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private String makeExcelFile1(List<?> lstRecset, RequestResponsKindBean reqData) throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		String ret = "";

		try {

			//EXCELテンプレートを読み込む
			super.ExcelReadTemplate("発注マスタ");
			ret = super.ExcelOutput("");
			for (int i = 0; i < lstRecset.size(); i++) {

				//検索結果の1行分を取り出す
				Object[] items = (Object[]) lstRecset.get(i);

				String strHattyuusakiCd = toString(items[0]);
				String strHattyuusakiNm = toString(items[1]);
				String strHattyuusakiNmHyoujijun = toString(items[2]);
				String strTantoushaCd = toString(items[3]);
				String strTantousha = toString(items[4]);
				String strTantoushahyoujiNm = toString(items[5]);
				String strMailAddress = toString(items[6]);
				String strMisiyou = toString(items[7]);

				// Excelに値をセットする
				super.ExcelSetValue("発注先コード", strHattyuusakiCd);
				super.ExcelSetValue("発注先名", strHattyuusakiNm);
				super.ExcelSetValue("発注先表示順", strHattyuusakiNmHyoujijun);
				super.ExcelSetValue("担当者コード", strTantoushaCd);
				super.ExcelSetValue("担当者", strTantousha);
				super.ExcelSetValue("担当者表示順", strTantoushahyoujiNm);
				super.ExcelSetValue("メールアドレス", strMailAddress);
				super.ExcelSetValue("未使用", strMisiyou);
			}

		} catch (Exception e) {
			em.ThrowException(e, "");

		} finally {
			super.ExcelWrite();
			super.close();
		}
		return ret;
	}

	/**
	 * レスポンスデータを生成する
	 * @param DownLoadPath : ファイルパス生成ファイル格納先(ダウンロードパラメータ)
	 * @return RequestResponsKindBean : レスポンスデータ（機能）
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private RequestResponsKindBean CreateRespons(String DownLoadPath,  RequestResponsKindBean reqData) throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		try {

			//ファイルパス	生成ファイル格納先
			reqData.addFieldVale(0, 0, "URLValue", DownLoadPath);
			//処理結果①	成功可否
			reqData.addFieldVale(0, 0, "flg_return", "true");
			//処理結果②	メッセージ
			reqData.addFieldVale(0, 0, "msg_error", "");
			//処理結果③	処理名称
			reqData.addFieldVale(0, 0, "no_errmsg", "");
			//処理結果⑥	メッセージ番号
			reqData.addFieldVale(0, 0, "nm_class", "");
			//処理結果④	エラーコード
			reqData.addFieldVale(0, 0, "cd_error", "");
			//処理結果⑤	システムメッセージ
			reqData.addFieldVale(0, 0, "msg_system", "");
		} catch (Exception e) {
			em.ThrowException(e, "");

		} finally {

		}
		return reqData;
	}
}
