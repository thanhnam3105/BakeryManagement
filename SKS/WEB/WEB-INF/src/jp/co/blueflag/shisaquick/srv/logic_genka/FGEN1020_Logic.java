package jp.co.blueflag.shisaquick.srv.logic_genka;

import java.util.List;

import jp.co.blueflag.shisaquick.srv.base.LogicBase;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsKindBean;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;

/**
 * 工場コンボボックス検索
 * @author isono
 * @create 2009/11/04
 */
public class FGEN1020_Logic extends LogicBase {

	/**
	 * コンストラクタ
	 */
	public FGEN1020_Logic() {
		//基底クラスのコンストラクタ
		super();

	}

	public RequestResponsKindBean ExecLogic(
			RequestResponsKindBean reqData
			,UserInfoData _userInfoData
			)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		//ユーザー情報退避
		userInfoData = _userInfoData;
		//検索バッファ
		StringBuffer strSql = new StringBuffer();
		List<?> lstRecset = null;
		//レスポンスデータクラス
		RequestResponsKindBean resKind = null;

		try {
			//レスポンスデータ（機能）生成
			resKind = new RequestResponsKindBean();
			resKind.setID("FGEN1020");

			String kaishaCd = "";
			String DataId = "";
			String id_gamen="";

			//リクエストデータの取得
			kaishaCd = reqData.getFieldVale(0, 0, "cd_kaisya");
			DataId = userInfoData.getID_data("170");
			id_gamen = reqData.getFieldVale(0, 0, "id_gamen");


			// ------------------------ SQL文の作成 ---------------------------
			strSql.append("SELECT cd_kaisha, cd_busho, nm_busho, ISNULL(keta_genryo,6) AS keta_genryo");
			strSql.append(" FROM ma_busho");
			strSql.append(" WHERE cd_kaisha = ");

			//WHERE区の生成
			//「SQ110：原価試算画面」からリクエストがあった場合
			if(id_gamen.equals("SQ110")){

				//MOD 2015/03/03 TT.Kitazawa【QP@40812】No.12 start
				//  ※DataId=4 の時、他会社を選択できない事
				if ( DataId.equals("4") ) {
					//自工場分
					strSql.append(userInfoData.getCd_kaisha());
					strSql.append(" AND cd_busho = ");
					strSql.append(userInfoData.getCd_busho());
				// 画面（170）権限の DataId=4 以外の場合の対応
//				} else if ( DataId.equals("9") ) {
				} else {
					//全て
					strSql.append(kaishaCd);

					//【QP@00342】工場フラグが１のものだけ表示 + 研究所
					strSql.append(" AND	flg_kojo = 1 ");
					// 2015/07/10 キユーピーのみ + 研究所を表示 add start
					if("1".equals(kaishaCd)){
						strSql.append(" OR (cd_kaisha = 1 AND cd_busho=1) ");
					}
					// 2015/07/10 キユーピーのみ + 研究所を表示 add end
				}
				//MOD 2015/03/03 TT.Kitazawa【QP@40812】No.12 end

			}
			//「SQ111：類似品検索画面」からリクエストがあった場合
			else if(id_gamen.equals("SQ111")){
				//全て
				strSql.append(kaishaCd);

				//【QP@00342】工場フラグが１のものだけ表示 + 研究所
				strSql.append(" AND	flg_kojo = 1 ");
				// 2015/07/10 キユーピーのみ + 研究所を表示 add start
				if("1".equals(kaishaCd)){
					strSql.append(" OR (cd_kaisha = 1 AND cd_busho=1) ");
				}
				// 2015/07/10 キユーピーのみ + 研究所を表示 add end
			}
			strSql.append(" ORDER BY cd_kaisha,cd_busho");


			// -------------------------- SQLを実行 ------------------------------
			super.createSearchDB();
			try{
				lstRecset = searchDB.dbSearch(strSql.toString());

			}catch(ExceptionWaning e){

			}

			// レスポンスデータの形成
			storageKoujouCmb(lstRecset, resKind);

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {
			//リストの破棄
			removeList(lstRecset);
			//セッションのクローズ
			if (searchDB != null) {
				searchDB.Close();
				searchDB = null;
			}
			//変数の削除
			strSql = null;
		}
		return resKind;
	}

	private void storageKoujouCmb(

			List<?> lstGroupCmb
			, RequestResponsKindBean resData
			)
	throws ExceptionSystem,	ExceptionUser, ExceptionWaning {

		try {

			if (lstGroupCmb == null){

				//処理結果①	flg_return
				resData.addFieldVale("kojyo", "rec" + "0", "flg_return", "true");
				//処理結果②	msg_error
				resData.addFieldVale("kojyo", "rec" + "0", "msg_error", "該当する工場がありません");
				//処理結果③	nm_class
				resData.addFieldVale("kojyo", "rec" + "0", "nm_class", "");
				//処理結果⑥	no_errmsg
				resData.addFieldVale("kojyo", "rec" + "0", "no_errmsg", "");
				//処理結果④	cd_error
				resData.addFieldVale("kojyo", "rec" + "0", "cd_error", "");
				//処理結果⑤	msg_system
				resData.addFieldVale("kojyo", "rec" + "0", "msg_system", "");
				//工場CD	cd_kojyo
				resData.addFieldVale("kojyo", "rec" + "0", "cd_kojyo", "");
				//工場名	nm_kojyo
				resData.addFieldVale("kojyo", "rec" + "0", "nm_kojyo", "");

			}else{

				for (int i = 0; i < lstGroupCmb.size(); i++) {

					//処理結果の格納
					Object[] items = (Object[]) lstGroupCmb.get(i);

					//処理結果①	flg_return
					resData.addFieldVale("kojyo", "rec" + toString(i), "flg_return", "true");
					//処理結果②	msg_error
					resData.addFieldVale("kojyo", "rec" + toString(i), "msg_error", "");
					//処理結果③	nm_class
					resData.addFieldVale("kojyo", "rec" + toString(i), "nm_class", "");
					//処理結果⑥	no_errmsg
					resData.addFieldVale("kojyo", "rec" + toString(i), "no_errmsg", "");
					//処理結果④	cd_error
					resData.addFieldVale("kojyo", "rec" + toString(i), "cd_error", "");
					//処理結果⑤	msg_system
					resData.addFieldVale("kojyo", "rec" + toString(i), "msg_system", "");
					//工場CD	cd_kojyo
					resData.addFieldVale("kojyo", "rec" + toString(i), "cd_kojyo", toString(items[1]));
					//工場名	nm_kojyo
					resData.addFieldVale("kojyo", "rec" + toString(i), "nm_kojyo", toString(items[2]));
					//桁数	keta_genryo
					resData.addFieldVale("kojyo", "rec" + toString(i), "cd_keta", toString(items[3]));

				}

			}

		} catch (Exception e) {
			this.em.ThrowException(e, "");

		} finally {

		}

	}

}
