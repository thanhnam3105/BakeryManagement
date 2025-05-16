package jp.co.blueflag.shisaquick.srv.base;

import java.math.BigDecimal;
import java.util.List;

import jp.co.blueflag.shisaquick.srv.common.ExceptionManager;

/**
 * オブジェクトのベース
 * @author isono
 *
 */
public class ObjectBase {

	//Exception管理クラス
	protected ExceptionManager em;
	
	public ObjectBase(){
		//クラス名を引数として、ExceptionManagerのインスタンスを生成する。
		this.em = new ExceptionManager(this.getClass().getName());

	}
	
	/**
	 * ObjectのStringを返す。
	 * @param obj : 対象のオブジェクト
	 * @return : String（nullの場合は””を返す）
	 */
	protected static String toString(Object obj ){

		String ret = "";
		
		if (obj != null){
			ret = obj.toString();
			
		}
		return ret;
		
	}
	/**
	 * Objectを数値に変換する
	 * @param obj : 対象のオブジェクト
	 * @return double : (不可能な場合は0を返す)
	 */
	protected static double toDouble(Object obj){
		
		double ret = 0;

		try {
			ret = Double.parseDouble(toString(obj));

		} catch(NumberFormatException e) {

        }
		
		return ret;
		
	}
	/**
	 * 四捨五入
	 * @param value : 対象の値
	 * @param round : 四捨五入の桁数
	 * @return double : 四捨五入の結果
	 */
	protected static double toRound(double value, int round){
		
		double ret = 0;

		BigDecimal bd = new BigDecimal(String.valueOf(value));
		ret = bd.setScale(round, BigDecimal.ROUND_HALF_UP).doubleValue();
		
		return ret;
		
	}
	/**
	 * リストObjectの破棄
	 * @param listObj : 破棄対象のリスト
	 */
	protected static void removeList(Object Obj){

		try{
			for(int ix = ((List<?>)Obj).size()-1; ix > -1; ix--){
				removeList(((List<?>)Obj).get(ix));
				((List<?>)Obj).remove(ix);
				
			}
			
		}catch(Exception e){

		} finally {
			Obj = null;
		}
		
	}

}
