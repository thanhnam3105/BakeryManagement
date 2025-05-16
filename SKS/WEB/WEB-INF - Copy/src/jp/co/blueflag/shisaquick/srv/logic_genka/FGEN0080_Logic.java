package jp.co.blueflag.shisaquick.srv.logic_genka;

import java.util.List;

import jp.co.blueflag.shisaquick.srv.base.LogicBase;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsKindBean;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;

/**
 * 資材検索（コードより）
 * ：該当の会社、工場、資材コードに該当する資材情報を取得する。
 * @author isono
 * @create 2009/11/04
 */
public class FGEN0080_Logic extends LogicBase {

	/**
	 * コンストラクタ
	 */
	public FGEN0080_Logic() {
		//基底クラスのコンストラクタ
		super();

	}
	/**
	 * 原価試算 資材検索の実装
	 * ：該当の会社、工場、資材コードに該当する資材情報を取得する。
	 * @param reqData		: リクエストデータ
	 * @param userInfoData	: ユーザー情報
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
		//レスポンス
		RequestResponsKindBean ret = null;
		//アイテムオブジェクト
		Object[] items = null;
		
		try {
			//レスポンスインスタンス生成
			ret = new RequestResponsKindBean();
			ret.setID("FGEN0080");
			
			//DB検索
			items = searchShizai(reqData);
			//レスポンスのセット
			if (items == null){
				//処理結果①	flg_return
				ret.addFieldVale("table", "rec", "flg_return", "true");
				//処理結果②	msg_error
				ret.addFieldVale("table", "rec", "msg_error", "コードに該当する資材がありません。");
				//処理結果③	nm_class
				ret.addFieldVale("table", "rec", "nm_class", "");
				//処理結果⑥	no_errmsg
				ret.addFieldVale("table", "rec", "no_errmsg", "");
				//処理結果④	cd_error
				ret.addFieldVale("table", "rec", "cd_error", "");
				//処理結果⑤	msg_system
				ret.addFieldVale("table", "rec", "msg_system", "");
				//資材SEQ	seq_shizai
				ret.addFieldVale("table", "rec", "seq_shizai", "");
				//会社CD	cd_kaisya
				ret.addFieldVale("table", "rec", "cd_kaisya", "");
				//工場CD	cd_kojyo
				ret.addFieldVale("table", "rec", "cd_kojyo", "");
				//工場記号	kigo_kojyo
				ret.addFieldVale("table", "rec", "kigo_kojyo", "");
				//資材CD	cd_shizai
				ret.addFieldVale("table", "rec", "cd_shizai", "");
				//資材名	nm_shizai
				ret.addFieldVale("table", "rec", "nm_shizai", "");
				//単価	tanka
				ret.addFieldVale("table", "rec", "tanka", "");
				//歩留	budomari
				ret.addFieldVale("table", "rec", "budomari", "");
				
			}else{
				//処理結果①	flg_return
				ret.addFieldVale("table", "rec", "flg_return", "true");
				//処理結果②	msg_error
				ret.addFieldVale("table", "rec", "msg_error", "");
				//処理結果③	nm_class
				ret.addFieldVale("table", "rec", "nm_class", "");
				//処理結果⑥	no_errmsg
				ret.addFieldVale("table", "rec", "no_errmsg", "");
				//処理結果④	cd_error
				ret.addFieldVale("table", "rec", "cd_error", "");
				//処理結果⑤	msg_system
				ret.addFieldVale("table", "rec", "msg_system", "");
				//資材SEQ	seq_shizai
				ret.addFieldVale("table", "rec", "seq_shizai"
						, reqData.getFieldVale("table", "rec", "seq_shizai"));
				//会社CD	cd_kaisya
				ret.addFieldVale("table", "rec", "cd_kaisya", toString(items[1], ""));
				//工場CD	cd_kojyo
				ret.addFieldVale("table", "rec", "cd_kojyo", toString(items[2], ""));
				//工場記号	kigo_kojyo
				ret.addFieldVale("table", "rec", "kigo_kojyo", toString(items[6], ""));
				//資材CD	cd_shizai
				ret.addFieldVale("table", "rec", "cd_shizai", toString(items[0], ""));
				//資材名	nm_shizai
				ret.addFieldVale("table", "rec", "nm_shizai", toString(items[3], ""));
				//単価	tanka
				ret.addFieldVale("table", "rec", "tanka", toString(toDouble(items[4]), 2, 2 , true, ""));
				//歩留	budomari
				ret.addFieldVale("table", "rec", "budomari", toString(toDouble(items[5]), 2, 2 , true, ""));
				
			}
			
		} catch (Exception e) {
			this.em.ThrowException(e, "原価試算 資材検索に失敗しました。");

		} finally {

		}
		return ret;
		
	}
	/**
	 * 資材情報の検索
	 * @param reqData	：リクエストデータ
	 * @return Object[]	：検索結果
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private Object[] searchShizai(
			
			RequestResponsKindBean reqData
			)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		//SQLバッファ
		StringBuffer strSQL = new StringBuffer();
		//結果バッファ
		List<?> listResult = null;
		//資材情報
		Object[] ret = null;
		
		try{

			//DBコネクション
			createSearchDB();

			//SQL生成
			strSQL.append(" SELECT ");
			strSQL.append("  M901.cd_shizai ");	//0
			strSQL.append(" ,M901.cd_kaisha ");	//1
			strSQL.append(" ,M901.cd_busho  ");	//2
			strSQL.append(" ,M901.nm_shizai ");	//3
			strSQL.append(" ,M901.tanka ");		//4
			strSQL.append(" ,M901.budomari ");	//5
			strSQL.append(" ,M302.nm_literal ");//6
			strSQL.append(" FROM ");
			strSQL.append("           ma_shizai AS M901 ");
			strSQL.append(" LEFT JOIN ma_literal AS M302 ");
			strSQL.append(" ON  M302.cd_category = 'K_kigo_kojyo' ");
			strSQL.append(" AND M302.cd_literal  = CONVERT(varchar, M901.cd_kaisha) + '-' + CONVERT(varchar, M901.cd_busho) ");
			strSQL.append(" WHERE ");
			strSQL.append("     M901.cd_shizai = " 
					+ toString(reqData.getFieldVale("table", "rec", "cd_shizai"), "null") + " ");
			strSQL.append(" AND M901.cd_kaisha = " 
					+ toString(reqData.getFieldVale("table", "rec", "cd_kaisya"), "null") + " ");
			strSQL.append(" AND M901.cd_busho  = " 
					+ toString(reqData.getFieldVale("table", "rec", "cd_kojyo"), "null") + " ");
			//DB検索
			try{
				listResult = this.searchDB.dbSearch(strSQL.toString());

				//資材情報
				if ( listResult.size() >= 0 ) {
					
					for (int i = 0; i < listResult.size(); i++) {
					
						ret = (Object[]) listResult.get(i);
						
					}

				}

			}catch(ExceptionWaning e){
				
			}
			
		}catch(Exception e){
			//例外のスロー
			this.em.ThrowException(e, "原価試算　資材情報の検索 に失敗しました。\nSQL:"
					+ strSQL.toString());
			
		}finally{
			//DBコネクション開放
			if (searchDB != null) {
				searchDB.Close();
				searchDB = null;
				
			}
			//ローカル変数の開放
			strSQL = null;
			removeList(listResult);

		}
		return ret;
		
	}


	
	
	
	
	
	
	
	
}
