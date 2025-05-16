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
 * 資材情報取得DB処理
 *  機能ID：FGEN3350　
 *  
 * @author TT.Shima
 * @since  2014/09/25
 */
public class FGEN3350_Logic extends LogicBase {
	
	/**
	 * 資材情報取得コンストラクタ 
	 * : インスタンス生成
	 */
	public FGEN3350_Logic(){
		super();
	}
	
	/**
	 * 資材情報取得 : 資材情報を取得する。
	 * 
	 * @param reqData
	 *            : 機能リクエストデータ
	 * @param userInfoData
	 *            : ユーザー情報
	 * @return レスポンスデータ（機能）
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	public RequestResponsKindBean ExecLogic(RequestResponsKindBean reqData,
			UserInfoData _userInfoData) throws ExceptionSystem, ExceptionUser,
			ExceptionWaning {

		// ユーザー情報退避
		userInfoData = _userInfoData;
		List<?> lstRecset = null;
		StringBuffer strSqlWhere = new StringBuffer();
		StringBuffer strSql = new StringBuffer();

		RequestResponsKindBean resKind = null;

		try {
			// レスポンスデータ（機能）生成
			resKind = new RequestResponsKindBean();

			// SQL作成（Where句取得）
			strSqlWhere = createWhereSQL(reqData, strSqlWhere);

			// SQL作成（資材データ取得）
			strSql = createTehaiSQL(reqData, strSql, strSqlWhere);

			// DBインスタンス生成
			createSearchDB();

			// 検索実行（資材データ取得）
			lstRecset = searchDB.dbSearch(strSql.toString());

			// 検索結果がない場合
			if (lstRecset.size() == 0) {
				em.ThrowException(ExceptionKind.警告Exception, "W000401",
						strSql.toString(), "", "");
			}

			// 機能IDの設定
			resKind.setID(reqData.getID());

			// テーブル名の設定
			resKind.addTableItem(reqData.getTableID(0));

			// レスポンスデータの形成
			storageShizaiData(lstRecset, resKind.getTableItem(0));

		} catch (Exception e) {
			this.em.ThrowException(e, "資材情報取得DB処理に失敗しました。");
		} finally {
			// リストの破棄
			removeList(lstRecset);
			if (searchDB != null) {
				// セッションのクローズ
				searchDB.Close();
				searchDB = null;
			}
			// 変数の削除
			strSqlWhere = null;
			strSql = null;
		}
		return resKind;

	}
	
	/**
	 * 取得SQL作成
	 *  : 手配情報を取得するSQLを作成。
	 * 
	 * @param strSql
	 *            : 検索条件SQL
	 * @param arrCondition
	 *            ：検索条件項目
	 * @return strSql：検索条件SQL
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private StringBuffer createTehaiSQL(RequestResponsKindBean reqData,
			StringBuffer strSql, StringBuffer strWhere) throws ExceptionSystem,
			ExceptionUser, ExceptionWaning {
		
		StringBuffer strAllSql = new StringBuffer();
		
		try {
			// SQL文の作成
			strAllSql.append(" SELECT ");
			strAllSql.append("   sekkei1, ");
			strAllSql.append("   sekkei2, ");
			strAllSql.append("   sekkei3, ");
			strAllSql.append("   zaishitsu, ");
			strAllSql.append("   biko_tehai, ");
			strAllSql.append("   printcolor, ");
			strAllSql.append("   no_color, ");
			strAllSql.append("   henkounaiyoushosai, ");
			strAllSql.append("   nouki, ");
			strAllSql.append("   suryo, ");
			strAllSql.append("   old_sizaizaiko, ");
			strAllSql.append("   rakuhan ");
			strAllSql.append(" FROM ");
			strAllSql.append("   tr_shizai_tehai ");
			strAllSql.append(strWhere);
			
		} catch (Exception e) {
			this.em.ThrowException(e, "資材情報取得DB処理に失敗しました。");
		} finally {

		}
		return strAllSql;
	}
	
	/**
	 * Where句取得SQL作成
	 * 
	 * @param reqData
	 *            : リクエストデータ
	 * @param strSql
	 *            : 検索条件SQL
	 * @return strSql：検索条件SQL
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private StringBuffer createWhereSQL(RequestResponsKindBean reqData,
			StringBuffer strSql) throws ExceptionSystem, ExceptionUser,
			ExceptionWaning {

		StringBuffer strWhere = new StringBuffer();

		try {
			// 検索条件取得---------------------------------------------------------------------
			
			// 社員コード
			String cd_shain = toString(reqData.getFieldVale(0, 0, "cd_shain"));
			
			// 年
			String nen = toString(reqData.getFieldVale(0, 0, "nen"));
			
			// 追番
			String no_oi = toString(reqData.getFieldVale(0, 0, "no_oi"));
			
			// 行番号
			String seq_shizai = toString(reqData.getFieldVale(0, 0, "seq_shizai"));
			
			// 枝番
			String no_eda = toString(reqData.getFieldVale(0, 0, "no_eda"));
			
			// WHERE句SQL作成----------------------------------------------------------------
			strWhere.append(" WHERE ");
			strWhere.append("     cd_shain = '" + cd_shain + "' ");
			strWhere.append(" AND nen = '" + nen + "' ");
			strWhere.append(" AND no_oi = '" + no_oi + "' ");
			strWhere.append(" AND seq_shizai = '" + seq_shizai + "' ");
			strWhere.append(" AND no_eda = '" + no_eda + "' ");

			
		} catch (Exception e) {

			em.ThrowException(e, "資材情報取得DB処理に失敗しました。");

		} finally {

		}

		return strWhere;
	}
	
	/**
	 * 資材情報パラメーター格納 : 資材情報をレスポンスデータへ格納する。
	 * 
	 * @param lstRecset
	 *            : 検索結果情報リスト
	 * @param resTable
	 *            : レスポンス情報
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private void storageShizaiData(List<?> lstRecset,
			RequestResponsTableBean resTable) throws ExceptionSystem,
			ExceptionUser, ExceptionWaning {

		try {
			// 検索結果取得
			Object[] items = (Object[]) lstRecset.get(0);
			String sekkei1 = toString(items[0]);
			String sekkei2 = toString(items[1]);
			String sekkei3 = toString(items[2]);
			String zaishitsu = toString(items[3]);
			String biko_tehai = toString(items[4]);
			String printcolor = toString(items[5]);
			String no_color = toString(items[6]);
			String henkounaiyoushosai = toString(items[7]);
			String nouki = toString(items[8]);
			String suryo = toString(items[9]);
			String old_sizaizaiko = toString(items[10]);
			String rakuhan = toString(items[11]);
			
			if(0 < lstRecset.size()){
				// 処理結果の格納
				resTable.addFieldVale(0, "flg_return", "true");
				resTable.addFieldVale(0, "msg_error", "");
				resTable.addFieldVale(0, "no_errmsg", "");
				resTable.addFieldVale(0, "nm_class", "");
				resTable.addFieldVale(0, "cd_error", "");
				resTable.addFieldVale(0, "msg_system", "");

				// レスポンス生成
				resTable.addFieldVale(0, "sekkei1", sekkei1);
				resTable.addFieldVale(0, "sekkei2", sekkei2);
				resTable.addFieldVale(0, "sekkei3", sekkei3);
				resTable.addFieldVale(0, "zaishitsu", zaishitsu);
				resTable.addFieldVale(0, "biko_tehai", biko_tehai);
				resTable.addFieldVale(0, "printcolor", printcolor);
				resTable.addFieldVale(0, "no_color", no_color);
				resTable.addFieldVale(0, "henkounaiyoushosai", henkounaiyoushosai);
				resTable.addFieldVale(0, "nouki", nouki);
				resTable.addFieldVale(0, "suryo", suryo);
				resTable.addFieldVale(0, "old_sizaizaiko", old_sizaizaiko);
				resTable.addFieldVale(0, "rakuhan", rakuhan);
			} 

		} catch (Exception e) {
			this.em.ThrowException(e, "資材情報取得DB処理に失敗しました。");

		} finally {

		}

	}

}
