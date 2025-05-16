package jp.co.blueflag.shisaquick.srv.base;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Calendar;
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

		return toString( obj,  "");
		
	}
	protected static String toString(Object obj,  String DefVal){

		return toString(obj, DefVal, true);
		
	}
	protected static String toString(Object obj,  String DefVal, boolean flg ){

		String ret = DefVal;
		
		try{
			ret = obj.toString();
			
		}catch(Exception e){
			
		}
		
		if (flg){
			
			if (ret.equals("")){
				ret = DefVal;
			}
			
		}
		
		return ret;
		
	}
	protected static String toString(Object obj,  String DefVal ,String reprceTg){

		String ret = "";
		try{
			ret = toString(obj, DefVal, true);

			ret = ret.replaceAll(reprceTg, "");
		
		}catch(Exception e){
			
		}
		return ret;

	}
	/**
	 * ダブル方を指定のフォーマットでストリングに変換します。
	 * @param target	：編集対象
	 * @param syosu		：小数指定（0～...）
	 * @param kirisute	：指定桁以下の処理（1：切捨て、2：切り上げ、3：四捨五入）
	 * @param kanma		：整数部のカンマ編集（true：実施、false：未実施）
	 * @param DefVal	：失敗時の返却値
	 * @return
	 */
	protected static String toString(
			
			double target
			, int syosu
			, int kirisute
			, boolean kanma 
			, String DefVal
			){
		
		String ret = DefVal;
		BigDecimal tg = null;
		String strZero = "0000000000";
		
		try{
			if (kirisute == 1){
				//切捨て
				tg = new BigDecimal(toRoundDown(target, syosu));

			}else if(kirisute == 2){
				//切り上げ
				tg = new BigDecimal(toRoundUp(target, syosu));
				
			}else if(kirisute == 3){
				//四捨五入
				tg = new BigDecimal(toRoundString(target, syosu));
		
			}
			
			if (syosu > 0){
				strZero = "." + getRight(strZero, syosu);	
				
			}else{
				strZero = "";

			}
			
			if (kanma){
				//カンマ編集
				DecimalFormat decimalformat = new DecimalFormat("###,##0" + strZero); 
				ret = decimalformat.format(tg); 
				
			}else{
				//カンマ編集なし
				DecimalFormat decimalformat = new DecimalFormat("#####0" + strZero); 
				ret = decimalformat.format(tg); 
				
			}
			
		}catch(Exception e){
			
		}
		return ret;
		
	}
	/**
	 * オブジェクトをBigDecimalに変換する。
	 * @param obj : 対象のオブジェクト
	 * @return : BigDecimal（変換不能な場合は、0）
	 */
	protected BigDecimal toDecimal(Object obj){

		return toDecimal(obj,"0");
		
	}
	protected BigDecimal toDecimal(Object obj, String defVal){
		
		BigDecimal ret = new BigDecimal(defVal);
		
		try{
			ret = new BigDecimal(toString(obj));
			
		}catch(Exception e){
			
		}
		return ret;
		
	}
	/**
	 * Objectを数値に変換する
	 * @param obj : 対象のオブジェクト
	 * @return double : (不可能な場合は0を返す)
	 */
	protected static double toDouble(Object obj){

		return toDouble(obj, 0);
		
	}
	protected static double toDouble(Object obj, double defVal){
		
		double ret = defVal;

		try {
			ret = Double.parseDouble(toString(obj,  "" , ","));

		} catch(NumberFormatException e) {

        }
		return ret;
		
	}
	/**
	 * Objectを数値に変換する(int)
	 * @param obj : 対象のオブジェクト
	 * @return double : (不可能な場合は0を返す)
	 */
	protected static int toInteger(Object obj){
		
		return toInteger(obj, 0);
		
	}
	protected static int toInteger(Object obj, int defVal){
		
		int ret = defVal;

		try {
			ret = Integer.parseInt(toString(obj));

		} catch(Exception e) {

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
	 * 切り上げ
	 * @param value : 対象の値
	 * @param round : 切り上げの桁数
	 * @return double : 切り上げの結果
	 */
	protected static double toRoundUp(double value, int round){
		
		double ret = 0;

		BigDecimal bd = new BigDecimal(String.valueOf(value));
		ret = bd.setScale(round, BigDecimal.ROUND_UP).doubleValue();
		
		return ret;
		
	}
	/**
	 * 切り捨て
	 * @param value : 対象の値
	 * @param round : 切り捨ての桁数
	 * @return double : 切り捨ての結果
	 */
	protected static double toRoundDown(double value, int round){
		
		double ret = 0;

		BigDecimal bd = new BigDecimal(String.valueOf(value));
		ret = bd.setScale(round, BigDecimal.ROUND_DOWN).doubleValue();
		
		return ret;
		
	}
	/**
	 * 四捨五入
	 * @param value : 対象の値
	 * @param round : 四捨五入の桁数
	 * @return double : 四捨五入の結果(文字列型)
	 */
	protected static String toRoundString(double value, int round){
		
		String ret = "";

		BigDecimal bd = new BigDecimal(String.valueOf(value));
		ret = bd.setScale(round, BigDecimal.ROUND_HALF_UP).toString();
		
		return ret;
		
	}
	/**
	 * 文字列の右から指定した文字数だけ切り出す
	 * @param tgStr　:　ターゲットの文字列
	 * @param ix　:　切り出す文字数
	 * @return　String　：　切り出された文字列（エラーの場合は””）
	 */
	protected static String getRight(String tgStr, int ix) {
		
		String ret = "";
		
		try{
			ret = tgStr.substring(tgStr.length()-ix,tgStr.length());
			
		}catch(Exception e){
			
		}
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

	/**
	 * リストを区切り文字付き文字列に変換する
	 * @param tgList : 変換対象のリスト
	 * @param sp : 区切り文字
	 * @return String 変換結果の文字列
	 */
	protected String ListToString(List<String> tgList, String sp){
		
		String ret = "";
		
		for (int ix = 0; ix < tgList.size(); ix++ ){
			ret += tgList.get(ix) + sp;
			
		}
		return ret;
		
	}
	/**
	 * 区切り文字付きの文字列をリストに変換する
	 * @param tgString : 変換対象の区切り文字付き文字列
	 * @param sp : 区切り文字
	 * @return ArrayList<String> 変換結果リスト
	 */
	protected ArrayList<String> StringToList(String tgString, String sp){
		
		ArrayList<String> ret = new ArrayList<String>();
		
		String[] strings = tgString.split(sp);
		//ファイル数分URLを生成
		for(int i = 0; i < strings.length; i++){
			//ダウンロードURLを格納
			ret.add(strings[i]);
		}
		strings = null;
		
		return ret;
		
	}
	
	/**
	 * 区切り文字付きの文字列を日付型に変換（ＹＹＹＹ／ＭＭ／ＤＤ）
	 * @param strdate : 変換対象の入力日付
	 * @return string : 変換後日付
	 */
	protected String cnvDateFormat(String strdate){
		
		String ret = null;
		String[] strTemps = null;
		
		try{
			
			//配列に分解
			if(strdate.split("/").length == 3){
				//①「/」で分解
				strTemps = strdate.split("/");
			}else if(strdate.split("-").length == 3){
				//②「-」で分解
				strTemps = strdate.split("-");
			}else if(strdate.split("\\.").length == 3){
				//③「.」で分解(「.」は正規表現で「何か１文字」の意味となるので、バックスラッシュを付与して対応)
				strTemps = strdate.split("\\.");
			}else if(strdate.length() == 8){
				//区切り文字無し8桁
				strdate = strdate.substring(0,4) + "/" +strdate.substring(4,6) + "/" + strdate.substring(6,8);
				strTemps = strdate.split("/");
			}else if(strdate.length() == 6){
				//区切り文字無し6桁
				strdate = strdate.substring(0,2) + "/" +strdate.substring(2,4) + "/" + strdate.substring(4,6);
				strTemps = strdate.split("/");
			}else{
				//上記以外の場合は元の値を返す
				return strdate;
			}
			
			//日付のフォーマットを指定
			MessageFormat mf = new MessageFormat("{0,number,0000}/{1,number,00}/{2,number,00}");
			
			//年が4桁以外の場合に、フォーマットを変更
			if(toString(strTemps[0]).length() != 4){
				
				//現在の年を取得
				Calendar cal1 = Calendar.getInstance();  //オブジェクトの生成
				String year = Integer.toString(cal1.get(Calendar.YEAR));       //現在の年を取得
				String f_year = year.substring(0,1); //年の一文字目を取得（フォーマット用）
				
				//年の一文字目に現在の年数を指定
				mf = new MessageFormat("{0,number," + f_year + "000}/{1,number,00}/{2,number,00}");
			}
			//年が0桁以外の場合に、引数をそのまま返却
			if(toString(strTemps[0]).length() == 0){
				
				return strdate;
				
			}
			
			Object[] dateobj = {toInteger(strTemps[0]), toInteger(strTemps[1]), toInteger(strTemps[2])};
			ret = mf.format(dateobj);
			
			//年頭が「０」の場合「２」に置き換え
			//ret = ret.replaceFirst("0", "2");
			
		}catch(Exception e){
			
			return strdate;
			
		}
		
		return ret;
	}
	

}