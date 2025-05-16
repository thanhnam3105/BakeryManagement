package jp.co.blueflag.shisaquick.srv.logic_genka;

import jp.co.blueflag.shisaquick.srv.base.LogicBase;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsKindBean;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsTableBean;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ConstManager;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;
import java.util.List;

/**
 * 【QP@00342】
 * 担当者マスタメンテ（営業）　担当者検索（営業）
 *  : 現在ステータス情報を取得する。
 *  機能ID：FGEN2060
 *
 * @author Nishigawa
 * @since  2011/01/28
 */
public class FGEN2060_Logic extends LogicBase{

	/**
	 * 担当者マスタメンテ（営業）　担当者検索（営業）
	 * : インスタンス生成
	 */
	public FGEN2060_Logic() {
		//基底クラスのコンストラクタ
		super();

	}

	//--------------------------------------------------------------------------------------------------------------
	//
	//                                                                     ExecLogic（処理開始）
	//
	//--------------------------------------------------------------------------------------------------------------

	/**
	 * 担当者マスタメンテ（営業）　会社検索（営業部署のみ）
	 *  : 担当者検索（営業）情報を取得する。
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

		List<?> lstRecset = null;

		RequestResponsKindBean resKind = null;

		try {

			//レスポンスデータ（機能）生成
			resKind = new RequestResponsKindBean();

			//機能IDの設定
			String strKinoId = reqData.getID();
			resKind.setID(strKinoId);

			//レスポンスデータの形成
			this.genkaKihonSetting(resKind, reqData);

		} catch (Exception e) {
			this.em.ThrowException(e, "");

		} finally {
			//リストの破棄
			removeList(lstRecset);

			if (searchDB != null) {
				//セッションのクローズ
				searchDB.Close();
				searchDB = null;
			}

			//変数の削除

		}
		return resKind;

	}

	//--------------------------------------------------------------------------------------------------------------
	//
	//                                                         DataSetting（レスポンスデータの形成）
	//
	//--------------------------------------------------------------------------------------------------------------
	/**
	 * レスポンスデータの形成
	 * @param resKind : レスポンスデータ
	 * @param reqData : リクエストデータ
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 *
	 * @author TT.Nishigawa
	 * @since  2009/10/21
	 */
	private void genkaKihonSetting(

			 RequestResponsKindBean resKind
			,RequestResponsKindBean reqData
			)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		//レコード値格納リスト
		List<?> lstRecset = null;

		//レコード値格納リスト
		StringBuffer strSqlBuf = null;

		try {
			//テーブル名
			String strTblNm = "table";

			//データ取得SQL作成
			strSqlBuf = this.createGenkaKihonSQL(reqData);

			//共通クラス　データベース検索を用いてSQL実行
			super.createSearchDB();
			lstRecset = searchDB.dbSearch(strSqlBuf.toString());

			//レスポンスデータにテーブル追加
			resKind.addTableItem(strTblNm);

			//追加したテーブルへレコード格納
			this.storageGenkaKihon(lstRecset, resKind.getTableItem(strTblNm));

		} catch (Exception e) {
			this.em.ThrowException(e, "担当者マスタメンテ（営業）　担当者検索（営業）処理が失敗しました。");

		} finally {
			//リストの破棄
			removeList(lstRecset);

			if (searchDB != null) {
				//セッションの解放
				this.searchDB.Close();
				searchDB = null;

			}

			//変数の削除
			strSqlBuf = null;

		}

	}


	//--------------------------------------------------------------------------------------------------------------
	//
	//                                                                  createSQL（SQL文生成）
	//
	//--------------------------------------------------------------------------------------------------------------
	/**
	 * データ取得SQL作成
	 * @param reqData：リクエストデータ
	 * @param strSql：検索条件SQL
	 * @return strSql：検索条件SQL
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private StringBuffer createGenkaKihonSQL(
			RequestResponsKindBean reqData
			)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		//SQL格納用
		StringBuffer strSql = new StringBuffer();

		// ADD 2013/9/25 okano【QP@30151】No.28 start
		List<?> lstRecset = null;
		// ADD 2013/9/25 okano【QP@30151】No.28 end

		try {
			//リクエストからデータ抽出
			String id_user = reqData.getFieldVale(0, 0, "id_user");

			// MOD 2013/9/25 okano【QP@30151】No.28 start
			//SQL文の作成
//			strSql.append(" SELECT   ");
//			strSql.append(" 	   M1.password  ");
//			strSql.append("       ,M1.cd_kengen  ");
//			strSql.append("       ,M1.nm_user  ");
//			strSql.append("       ,M1.cd_kaisha  ");
//			strSql.append("       ,M1.cd_busho  ");
//			strSql.append("       ,M1.id_josi  ");
//			strSql.append(" 	  ,M2.nm_user AS nm_josi  ");
//			strSql.append(" FROM   ");
//			strSql.append(" 	   ma_user AS M1  ");
//			strSql.append(" 	   LEFT JOIN ma_user AS M2  ");
//			strSql.append(" 	   ON M1.id_josi = M2.id_user  ");
//			strSql.append(" WHERE  ");
//			strSql.append(" 	   M1.id_user = " + id_user);

			strSql.append("SELECT COUNT(*) FROM ma_user WHERE id_user = ");
			strSql.append(id_user);

			createSearchDB();
			lstRecset = searchDB.dbSearch(strSql.toString());

			strSql = new StringBuffer();
			if (Integer.parseInt(lstRecset.get(0).toString()) == 0){
				strSql.append("SELECT");
				strSql.append("  password");
				strSql.append(" ,cd_kengen");
				strSql.append(" ,nm_user");
				strSql.append(" ,cd_kaisha");
				strSql.append(" ,cd_busho");
				strSql.append(" ,NULL");
				strSql.append(" ,NULL");
				strSql.append(" FROM ma_user_new");
				strSql.append(" WHERE id_user = ");
				strSql.append(id_user);
			} else {
				strSql.append(" SELECT   ");
				strSql.append(" 	   M1.password  ");
				strSql.append("       ,M1.cd_kengen  ");
				strSql.append("       ,M1.nm_user  ");
				strSql.append("       ,M1.cd_kaisha  ");
				strSql.append("       ,M1.cd_busho  ");
				// MOD 2015/03/03 TT.Kitazawa【QP@40812】No.19 start
//				strSql.append("       ,M1.id_josi  ");
//				strSql.append(" 	  ,M2.nm_user AS nm_josi  ");
				strSql.append("       ,MEMBER.id_member  ");
				strSql.append("       ,M2.nm_user AS nm_member ");
				strSql.append(" FROM   ");
				strSql.append(" 	   ma_user AS M1  ");

				strSql.append("        LEFT JOIN ma_member AS MEMBER  ");
				strSql.append("        ON M1.id_user = MEMBER.id_user  ");
				strSql.append(" 	   LEFT JOIN ma_user AS M2  ");
//				strSql.append(" 	   ON M1.id_josi = M2.id_user  ");
				strSql.append("        ON MEMBER.id_member = M2.id_user ");

				strSql.append(" WHERE  ");
				strSql.append(" 	   M1.id_user = " + id_user);
				strSql.append(" ORDER BY  MEMBER.no_sort ");
				// MOD 2015/03/03 TT.Kitazawa【QP@40812】No.19 end
			}
			// MOD 2013/9/25 okano【QP@30151】No.28 end
		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {
			// ADD 2013/9/25 okano【QP@30151】No.28 start
			removeList(lstRecset);
			if (searchDB != null) {
				//セッションの解放
				this.searchDB.Close();
				searchDB = null;

			}
			// ADD 2013/9/25 okano【QP@30151】No.28 end
		}
		return strSql;
	}

	//--------------------------------------------------------------------------------------------------------------
	//
	//                                                                  storageData（パラメーター格納）
	//
	//--------------------------------------------------------------------------------------------------------------
	/**
	 * パラメーター格納
	 *  : レスポンスデータへ格納する。
	 * @param lstGenkaHeader : 検索結果情報リスト
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private void storageGenkaKihon(

			  List<?> lstGenkaHeader
			, RequestResponsTableBean resTable
			)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try {
			//営業（一般）権限コード取得
			String strEigyoIppan =
				ConstManager.getConstValue(ConstManager.Category.設定,"EIGYO_KENGEN_IPPAN");
//【QP@10713】2011/10/28 TT H.SHIMA -ADD Start
			//営業（本部）権限コード取得
			String strEigyoHonbu =
				ConstManager.getConstValue(ConstManager.Category.設定,"EIGYO_KENGEN_HONBU");
			//営業（管理者システム）権限コード取得
			String strEigyoSystem =
				ConstManager.getConstValue(ConstManager.Category.設定,"EIGYO_KENGEN_SYSTEM");
			//営業（仮）権限コード取得
			String strEigyoKari =
				ConstManager.getConstValue(ConstManager.Category.設定,"EIGYO_KENGEN_KARI");
			// ADD 2013/9/25 okano【QP@30151】No.28 start
			//営業（仮）権限コード取得
			String strEigyoPass =
				ConstManager.getConstValue(ConstManager.Category.設定,"EIGYO_KENGEN_PASS");
			// ADD 2013/9/25 okano【QP@30151】No.28 end
//【QP@10713】2011/10/28 TT H.SHIMA -ADD End

			// ADD 2015/03/03 TT.Kitazawa【QP@40812】start（Javaエラー対応）
			if (lstGenkaHeader.size() == 0) {
				//処理結果の格納
				resTable.addFieldVale(0, "flg_return", "true");
				resTable.addFieldVale(0, "msg_error", "");
				resTable.addFieldVale(0, "no_errmsg", "");
				resTable.addFieldVale(0, "nm_class", "");
				resTable.addFieldVale(0, "cd_error", "");
				resTable.addFieldVale(0, "msg_system", "");
			}
			// ADD 2015/03/03 TT.Kitazawa【QP@40812】end

			for (int i = 0; i < lstGenkaHeader.size(); i++) {

				//処理結果の格納
				resTable.addFieldVale(i, "flg_return", "true");
				resTable.addFieldVale(i, "msg_error", "");
				resTable.addFieldVale(i, "no_errmsg", "");
				resTable.addFieldVale(i, "nm_class", "");
				resTable.addFieldVale(i, "cd_error", "");
				resTable.addFieldVale(i, "msg_system", "");

				Object[] items = (Object[]) lstGenkaHeader.get(i);

				//結果をレスポンスデータに格納
				resTable.addFieldVale(i, "password", toString(items[0],""));
				resTable.addFieldVale(i, "cd_kengen", toString(items[1],""));
				resTable.addFieldVale(i, "nm_user", toString(items[2],""));
				resTable.addFieldVale(i, "cd_kaisha", toString(items[3],""));
				resTable.addFieldVale(i, "cd_busho", toString(items[4],""));
				// MOD 2015/03/03 TT.Kitazawa【QP@40812】No.19 start
//				resTable.addFieldVale(i, "id_josi", toString(items[5],""));
//				resTable.addFieldVale(i, "nm_josi", toString(items[6],""));
				resTable.addFieldVale(i, "id_member", toString(items[5],""));
				resTable.addFieldVale(i, "nm_member", toString(items[6],""));
				// MOD 2015/03/03 TT.Kitazawa【QP@40812】No.19 end

				//営業（一般）権限の場合
				if(toString(items[1],"").equals(strEigyoIppan)){
//【QP@10713】2011/10/28 TT H.SHIMA -MOD Start
					//resTable.addFieldVale(i, "kengen_ippan", "1");
					resTable.addFieldVale(i, "eigyo_kengen", "1");
				}
				//営業（一般）権限以外の場合
				else if(toString(items[1],"").equals(strEigyoHonbu) || toString(items[1],"").equals(strEigyoSystem)){
					resTable.addFieldVale(i, "eigyo_kengen", "2");
				}
				// ADD 2013/9/25 okano【QP@30151】No.28 start
				else if(toString(items[1],"").equals(strEigyoPass)){
					resTable.addFieldVale(i, "eigyo_kengen", "3");
				}
				// ADD 2013/9/25 okano【QP@30151】No.28 end
				else{
					//resTable.addFieldVale(i, "kengen_ippan", "0");
					resTable.addFieldVale(i, "eigyo_kengen", "0");
//【QP@10713】2011/10/28 TT H.SHIMA -MOD Start
				}
			}

		} catch (Exception e) {
			this.em.ThrowException(e, "");

		} finally {

		}

	}

}
