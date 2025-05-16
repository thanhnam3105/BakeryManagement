package jp.co.blueflag.shisaquick.srv.commonlogic;

import java.util.ArrayList;
import java.util.List;

import jp.co.blueflag.shisaquick.srv.base.LogicBase;
import jp.co.blueflag.shisaquick.srv.common.ConstManager;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;

/*****************************************************************************
 * 
 * 漢字変換候補一覧取得
 *  : DB（ma_yomigana）変換候補一覧取得
 *  
 * @author TT.ISONO
 * @since  2009/08/28
 * 
 *****************************************************************************/
public class ImmGetConvList  extends LogicBase{
	
	/**
	 * 漢字変換候補一覧取得コンストラクタ 
	 * : インスタンス生成
	 */
	public ImmGetConvList() {
		
		//基底クラスのコンストラクタ
		super();
		
	}
	
	/****************************************************************************
	 * 
	 * 変換候補一覧取得
	 * @param  strInput   : 変換文字
	 * @return  ArrayList : 候補一覧配列
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 * 
	 ****************************************************************************/
	public ArrayList ImmGetConvListChange(String strInput)
		throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		//返却用配列初期化
		ArrayList ret = new ArrayList();
		//DB検索結果
		List<?> lstSearchData = null;
		//SQL格納
		StringBuffer strSql;
		
		//候補一覧取得
		try{

			//候補数の最大値を取得
			double ListMax = toDouble(ConstManager.getConstValue(ConstManager.Category.設定,"IME_LIST_MAX"));

			//検索SQLを生成
			strSql = createSQL(strInput);
			
			//データベース検索、検索結果を取得する。
			createSearchDB();
			lstSearchData = searchDB.dbSearch(strSql.toString());
			
			//返却用配列に変換前の文字を追加
			ret.add(strInput);
			
			for (int i = 0; i < lstSearchData.size() ; i++) {

				//候補数の最大値を超えているか確認
				if (i < ListMax-1){
					//候補最大値以内の場合、追加
					Object[] items = (Object[]) lstSearchData.get(i);
					ret.add(items[0]);
					
				}
			
			}
			
		} catch(Exception e){
			this.em.ThrowException(e, "変換候補一覧の取得に失敗しました。");
			
		} finally{
			
			//DBセッション開放
			searchDB.Close();
			//ローカル変数の開放
			removeList(lstSearchData);
			
		}
		
		//返却
		return ret;
		
	}

	/**
	 * 漢字変換候補一覧 検索用SQL作成
	 * @param 変換対象文字列
	 * @return 作成SQL
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private StringBuffer createSQL(String strTg)
			throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		StringBuffer strSQL = new StringBuffer();
		
		//SQLを作成する。		
		try {

			strSQL.append("select ");
			strSQL.append("  nm_kana as nm_target ");
			strSQL.append(" ,dt_toroku ");
			strSQL.append(" from ");
			strSQL.append("  ma_yomigana ");
			strSQL.append(" where ");
			strSQL.append("  nm_kanji = '" + strTg + "' ");
			strSQL.append(" union all ");
			strSQL.append(" select ");
			strSQL.append("  nm_kanji as nm_target ");
			strSQL.append(" ,dt_toroku ");
			strSQL.append(" from ");
			strSQL.append("  ma_yomigana ");
			strSQL.append(" where ");
			strSQL.append("  nm_kana = '" + strTg + "' ");
			strSQL.append(" order by dt_toroku DESC ");
			
		} catch (Exception e) {
			this.em.ThrowException(e, "漢字変換候補一覧 検索用SQL作成が失敗しました。");
			
		} finally {
			//ローカル変数の削除
			
		}
		//作成したSQLを返却
		return strSQL;
		
	}

}
