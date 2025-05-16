package jp.co.blueflag.shisaquick.srv.base;

import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;

/**
 * 
 * リクエスト/レスポンスRows
 *  : リクエスト/レスポンスの行を管理する
 * @author TT.furuta
 * @since  2009/03/24
 */
public class RequestResponsRowBean extends RequestResponsBean {
	
	/**
	 * コンストラクタ
	 */
	public RequestResponsRowBean() {
		//基底のコンストラクタ
		super();
		
	}
	/**
	 * フィールドクラスを取得
	 * @param itemNo：フィールドインデックス
	 * @return フィールドRequestResponsFieldBean
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public RequestResponsFieldBean getFieldItem(int itemNo) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		RequestResponsFieldBean ret = null;
		
		try{
			//item 取得
			if(GetItem(itemNo) == null){
				ret=null;				
			}else{
				ret = (RequestResponsFieldBean) GetItem(itemNo);
			}
			
		}catch(Exception e){
			this.em.ThrowException(e, "/n フィールドの取得に失敗しました。itemNo=" + Integer.toString(itemNo));

		}finally{
			
		}
		return ret;
		
	}
	/**
	 * フィールドクラスを取得
	 * @param itemName：フィールド名
	 * @return フィールドRequestResponsFieldBean
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public RequestResponsFieldBean getFieldItem(String itemName)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		RequestResponsFieldBean ret = null;
		
		try{
			//item 取得
			if (getFieldItem(GetItemNo(itemName)) == null){

			}else{
				ret = (RequestResponsFieldBean) getFieldItem(GetItemNo(itemName));
				
			}
			
		}catch(Exception e){
			this.em.ThrowException(e, "/n フィールドの取得に失敗しました。itemNo=" + itemName);
			
		}finally{
			
		}
		return ret;

	}
	/**
	 * フィールドｵﾌﾞｼﾞｪｸﾄをセットする
	 * @param itemNo：フィールドインデックス
	 * @param itemField：フィールドｵﾌﾞｼﾞｪｸﾄ
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public void setFieldItem(int FieldNo, RequestResponsFieldBean itemField)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		try{
			//item をセットする
			SetItem(itemField, FieldNo);
			
		}catch(Exception e){
			this.em.ThrowException(e, "/n フィールドのセットに失敗しました。itemNo=" + Integer.toString(FieldNo));
			
		}finally{
			
		}
		
	}
	/**
	 * フィールドｵﾌﾞｼﾞｪｸﾄをセットする
	 * @param itemName：フィールド名
	 * @param itemField：フィールドｵﾌﾞｼﾞｪｸﾄ
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public void setFieldItem(String itemName, RequestResponsFieldBean itemField)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		try{
			//item をセットする
			SetItem(itemField, GetItemNo(itemName));
			
		}catch(Exception e){
			this.em.ThrowException(e, "/n フィールドのセットに失敗しました。itemNo=" + itemName);
			
		}finally{
			
		}
		
	}
	/**
	 * フィールドの追加
	 * @param itemField：フィールドｵﾌﾞｼﾞｪｸﾄ
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public void addFieldItem(RequestResponsFieldBean itemField)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try{
			//item を追加する
			AddItem(itemField);
			
		}catch(Exception e){
			this.em.ThrowException(e, "フィールドの追加に失敗しました。");
			
		}finally{
			
		}
		
	}
	/**
	 * フィールドの追加
	 * @param itemField：フィールドｵﾌﾞｼﾞｪｸﾄ
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public RequestResponsFieldBean addFieldItem(String FieldName)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		RequestResponsFieldBean ret = new RequestResponsFieldBean(FieldName,"");
		
		try{
			//item を追加する
			AddItem(ret);
			
		}catch(Exception e){
			this.em.ThrowException(e, "フィールドの追加に失敗しました。");
			
		}finally{
			
		}
		return ret;
		
	}
	/**
	 * フィールドの追加
	 * @param itemField：フィールドｵﾌﾞｼﾞｪｸﾄ
	 * @param ix：挿入位置インデックス
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public void addFieldItem(RequestResponsFieldBean itemField, int ix)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try{
			//item を追加する
			AddItem(itemField,ix);
			
		}catch(Exception e){
			this.em.ThrowException(e, "フィールドの追加に失敗しました。");
			
		}finally{
			
		}
		
	}
	/**
	 * フィールドの値を取得する
	 * @param itemNo：フィールドのインデックス
	 * @return　フィールドの値
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public String getFieldVale(int itemNo)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		String ret = "";

		try{
			//値を取得
			ret = getFieldItem(itemNo).getValue();
			
		}catch(Exception e){
			this.em.ThrowException(e, "フィールドの値、取得に失敗しました。itemNo" + Integer.toString(itemNo));
			
		}finally{
			
		}
		return ret;
		
	}
	/**
	 * フィールドの値を取得する
	 * @param itemName：フィールド名
	 * @return　フィールドの値
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public String getFieldVale(String itemName)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		String ret = "";

		try{
			//値を取得
			ret = getFieldItem(itemName).getValue();
			
		}catch(Exception e){
			this.em.ThrowException(e, "フィールドの値、取得に失敗しました。itemName=" + itemName);
			
		}finally{
			
		}
		return ret;
		
	}
	/**
	 * フィールドの値をセットする
	 * @param itemValue：フィールドの値
	 * @param itemNo：フィールドインデックス
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public void setFieldVale( int itemNo, String itemValue)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try{
			//値をセット
			
			getFieldItem(itemNo).setValue(itemValue);
			
		}catch(Exception e){
			this.em.ThrowException(e, "フィールドの値、セットに失敗しました。itemNo=" + Integer.toString(itemNo));
			
		}finally{
			
		}
		
	}
	/**
	 * フィールドの値をセットする
	 * @param itemName：フィールド名
	 * @param itemValue：フィールドの値
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public void setFieldVale(String itemName, String itemValue)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try{
			//値をセット
			if(getFieldItem(itemName) == null){
				addFieldItem(new RequestResponsFieldBean(itemName,itemValue));
			}else{
				getFieldItem(itemName).setValue(itemValue);
			}
			
		}catch(Exception e){
			this.em.ThrowException(e, "フィールドの値、セットに失敗しました。itemNo=" + itemName);
			
		}finally{
			
		}
		
	}
	/**
	 * フィールドの値を追加する
	 * @param itemValue：フィールドの値
	 * @param itemName：フィールド名
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public void addFieldVale(String itemName, String itemValue)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try{
			//値を追加
			addFieldItem(new RequestResponsFieldBean(itemName,itemValue));
			
		}catch(Exception e){
			this.em.ThrowException(e, "フィールドの値、追加に失敗しました。itemNo=" + itemName);
			
		}finally{
			
		}
		
	}
	
}
