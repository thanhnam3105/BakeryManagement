package jp.co.blueflag.shisaquick.srv.datacheck;

import jp.co.blueflag.shisaquick.srv.base.DataCheck;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsKindBean;
import jp.co.blueflag.shisaquick.srv.base.SearchBaseDao;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.base.BaseDao.DBCategory;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;
import jp.co.blueflag.shisaquick.srv.common.ExceptionManager.ExceptionKind;

import java.util.List;

/**
 * QP@10181 No.67
 * 【SA490】 登録（編集）DBチェック
 * @author TT.Nishigawa
 * @since  2011/05/21
 */
public class SA490DataCheck extends DataCheck{

	/**
	 *  登録（編集）処理DBチェック処理用コンストラクタ
	 */
	public SA490DataCheck() {
		//基底クラスのコンストラクタ
		super();

	}

	/**
	 * 登録（製法コピー）処理DBチェック
	 * @param reqData : リクエストデータ
	 * @param userInfoData : ユーザー情報
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	public void execDataCheck(
			RequestResponsKindBean reqData
			,UserInfoData _userInfoData
			)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		//ユーザー情報退避
		userInfoData = _userInfoData;

		//サンプル列
		String errSampleNo = "";

		try {

			//試作列データループ
			for(int i = 0; i < reqData.getCntRow("tr_shisaku"); i++){

				//キャンセル依頼があった場合
				if (toString(reqData.getFieldVale("tr_shisaku", i, "flg_cancel")).equals("1")){

					//試作CD-社員ID
					String cd_shain = toString(reqData.getFieldVale("tr_shisaku", i, "cd_shain"));
					//試作CD-年
					String nen = toString(reqData.getFieldVale("tr_shisaku", i, "nen"));
					//試作CD-追番
					String no_oi = toString(reqData.getFieldVale("tr_shisaku", i, "no_oi"));
					//試作SEQ
					String seq_shisaku = toString(reqData.getFieldVale("tr_shisaku", i, "seq_shisaku"));

					//排他チェック
					checkHaita(reqData, cd_shain, nen, no_oi);

					//キャンセル時の営業ステータスのチェック
					checkStatus(reqData, cd_shain, nen, no_oi);

					//キャンセル時の試算日の入力チェック
					String retNo = checkShisanHi(reqData, cd_shain, nen, no_oi, seq_shisaku);
					if(retNo.length() > 0){
						errSampleNo =  errSampleNo + retNo + "\n";
					}

// 20160616  KPX@1502111_No.5 ADD start
					//サンプルNO（名称）
					String nm_sample = toString(reqData.getFieldVale("tr_shisaku", i, "nm_sample"));
					//自家原料：配合リンクの登録チェック
					chekHaigoLink(reqData, cd_shain, nen, no_oi, seq_shisaku, nm_sample);
// 20160616  KPX@1502111_No.5 ADD end

				}
			}

			//キャンセル対象で試算日の入力があった場合
			if(errSampleNo.length() > 0){
				em.ThrowException(ExceptionKind.一般Exception,"E000406", errSampleNo, "", "");
			}

		}
		catch (Exception e) {
			this.em.ThrowException(e, "");

		}
		finally {
			if (searchBD != null) {
				//セッションのクローズ
				searchBD.Close();
				searchBD = null;
			}
		}

	}

	/**
	 * キャンセル時の営業ステータスのチェック
	 * @param reqData : リクエストデータ
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private void checkHaita(
			RequestResponsKindBean reqData
			,String cd_shain
			,String nen
			,String no_oi
			)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		StringBuffer strSql1 = new StringBuffer();
		List<?> lstRecset = null;

		try {

			//DBセッションの設定
			super.createSearchDB();

			//ステータス取得用SQL作成
			strSql1.append(" select");
			strSql1.append("	RIGHT('0000000000' + CONVERT(varchar,cd_shain),10) AS cd_shain");
			strSql1.append("	,RIGHT('00' + CONVERT(varchar,nen),2) AS nen");
			strSql1.append("	,RIGHT('000' + CONVERT(varchar,no_oi),3) AS no_oi");
			strSql1.append("	,RIGHT('000' + CONVERT(varchar,no_eda),3) AS no_eda");
			strSql1.append("	,haita_id_user");
			strSql1.append(" from");
			strSql1.append("	tr_shisan_shisakuhin");
			strSql1.append(" where");
			strSql1.append("	cd_shain = " + cd_shain);
			strSql1.append("	and nen = " + nen);
			strSql1.append("	and no_oi = " + no_oi);
			strSql1.append("	and haita_id_user IS NOT NULL");

			//SQLを実行
			lstRecset = searchBD.dbSearch(strSql1.toString());

			//排他中でない場合
			if (lstRecset.size() == 0){

			}
			//排他中の場合
			else{
				em.ThrowException(ExceptionKind.一般Exception,"E000407", "", "", "");
			}

		}
		catch (Exception e) {
			this.em.ThrowException(e, "");

		}
		finally {
			//リストの破棄
			removeList(lstRecset);

			//変数の削除
			strSql1 = null;
		}

	}

	/**
	 * キャンセル時の営業ステータスのチェック
	 * @param reqData : リクエストデータ
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private void checkStatus(
			RequestResponsKindBean reqData
			,String cd_shain
			,String nen
			,String no_oi
			)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		StringBuffer strSql1 = new StringBuffer();
		List<?> lstRecset = null;

		try {

			//DBセッションの設定
			super.createSearchDB();

			//ステータス取得用SQL作成
			strSql1.append(" select");
			strSql1.append("	RIGHT('0000000000' + CONVERT(varchar,cd_shain),10) AS cd_shain");
			strSql1.append("	,RIGHT('00' + CONVERT(varchar,nen),2) AS nen");
			strSql1.append("	,RIGHT('000' + CONVERT(varchar,no_oi),3) AS no_oi");
			strSql1.append("	,RIGHT('000' + CONVERT(varchar,no_eda),3) AS no_eda");
			strSql1.append(" from");
			strSql1.append("	tr_shisan_status");
			strSql1.append(" where");
			strSql1.append("	cd_shain = " + cd_shain);
			strSql1.append("	and nen = " + nen);
			strSql1.append("	and no_oi = " + no_oi);
			strSql1.append("	and st_eigyo >= 2");

			//SQLを実行
			lstRecset = searchBD.dbSearch(strSql1.toString());

			//営業ステータスが２以上のものがない場合
			if (lstRecset.size() == 0){

			}
			//営業ステータスが２以上のものがある場合
			else{
				String errNo = "";

				for (int i = 0; i < lstRecset.size(); i++) {

					Object[] items = (Object[]) lstRecset.get(i);

					//結果をレスポンスデータに格納
					String ShisakuNo = toString(items[0]) + "-" + toString(items[1]) + "-" + toString(items[2]) + "-" + toString(items[3]);
					errNo = errNo + ShisakuNo + "\n";
				}

				em.ThrowException(ExceptionKind.一般Exception,"E000405", errNo, "", "");
			}

		}
		catch (Exception e) {
			this.em.ThrowException(e, "");

		}
		finally {
			//リストの破棄
			removeList(lstRecset);

			//変数の削除
			strSql1 = null;
		}

	}

	/**
	 * キャンセル時の試算日の入力チェック
	 * @param reqData : リクエストデータ
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private String checkShisanHi(
			RequestResponsKindBean reqData
			,String cd_shain
			,String nen
			,String no_oi
			,String seq_shisaku
			)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		StringBuffer strSql1 = new StringBuffer();
		List<?> lstRecset = null;

		String ret = "";

		try {
			//DBセッションの設定
			super.createSearchDB();

			//試算日取得用SQL作成
			strSql1.append(" select distinct");
			strSql1.append(" 	T2.nm_sample");
			strSql1.append(" from");
			strSql1.append(" 	tr_shisan_shisaku AS T1");
			strSql1.append(" 	inner join tr_shisaku AS T2");
			strSql1.append(" 		ON T1.cd_shain = T2.cd_shain");
			strSql1.append(" 		and T1.nen = T2.nen");
			strSql1.append(" 		and T1.no_oi = T2.no_oi");
			strSql1.append(" 		and T1.seq_shisaku = T2.seq_shisaku");
			strSql1.append(" where");
			strSql1.append(" 	T1.cd_shain = " + cd_shain);
			strSql1.append(" 	and T1.nen = " + nen);
			strSql1.append(" 	and T1.no_oi = " + no_oi);
			strSql1.append(" 	and T1.seq_shisaku = " + seq_shisaku);
			strSql1.append(" 	and T1.dt_shisan IS NOT NULL");


			//SQLを実行
			lstRecset = searchBD.dbSearch(strSql1.toString());

			//試算日が入力されているサンプル列がない場合
			if (lstRecset.size() == 0){

			}
			//試算日が入力されているサンプル列がある場合
			else{

					//結果をレスポンスデータに格納
					ret = (String)lstRecset.get(0);
			}
		}
		catch (Exception e) {
			this.em.ThrowException(e, "");

		}
		finally {
			//リストの破棄
			removeList(lstRecset);

			//変数の削除
			strSql1 = null;
		}

		//返却
		return ret;
	}

// 20160616  KPX@1502111_No.5 ADD start
	/**
	 * キャンセル時の配合リンク登録チェック
	 * @param reqData : リクエストデータ
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private void chekHaigoLink(
			RequestResponsKindBean reqData
			,String cd_shain
			,String nen
			,String no_oi
			,String seq_shisaku
			,String nm_sample
			)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		StringBuffer strSql1 = new StringBuffer();
		List<?> lstRecset = null;

		try {

			//DBセッションの設定
			super.createSearchDB();

			//ステータス取得用SQL作成
			strSql1.append(" select");
			strSql1.append("	cd_kaisha");
			strSql1.append("	,cd_genryo");
			strSql1.append("	,cd_shain");
			strSql1.append("	,nen");
			strSql1.append("	,no_oi");
			strSql1.append("	,seq_shisaku");
			strSql1.append(" from");
			strSql1.append("	tr_haigo_link");
			strSql1.append(" where");
			strSql1.append("	cd_shain = " + cd_shain);
			strSql1.append("	and nen = " + nen);
			strSql1.append("	and no_oi = " + no_oi);
			strSql1.append("	and seq_shisaku = " + seq_shisaku);

			//SQLを実行
			lstRecset = searchBD.dbSearch(strSql1.toString());

			//自家原料：配合リンクに登録されている場合
			if (lstRecset.size() > 0){

//				Object[] items = (Object[]) lstRecset.get(0);

				//結果をレスポンスデータに格納
//				String strGenryo = "\n  会社CD：" + toString(items[0]) + "\n  原料CD：" + toString(items[1])  + "\n  サンプルNo：" + nm_sample;

				em.ThrowException(ExceptionKind.一般Exception,"E000413", "", "", "");
			}

		}
		catch (Exception e) {
			this.em.ThrowException(e, "");

		}
		finally {
			//リストの破棄
			removeList(lstRecset);

			//変数の削除
			strSql1 = null;
		}

	}
// 20160616  KPX@1502111_No.5 ADD end

}

