package jp.co.blueflag.shisaquick.srv.base;

import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;

/**
 * 
 * リクエスト/レスポンスTable
 *  : リクエスト/レスポンスのデータ保持で、最小単位のデータ管理を行う
 * @author TT.isono
 * @since  2009/03/24
 */
public class RequestResponsTableBean extends RequestResponsBean{
	
	/**
	 * コンストラクタ
	 */
	public RequestResponsTableBean() {
		//基底ｸﾗｽのコンストラクタ
		super();
		
	}
	/**
	 * ロークラスを取得
	 * @param itemNo：ローインデックス
	 * @return ローRequestResponsFieldBean
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public RequestResponsRowBean getRowItem(int itemNo) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		RequestResponsRowBean ret = null;
		
		try{
			//item 取得
			if (GetItem(itemNo) == null){
				
			}else{
				ret = (RequestResponsRowBean) GetItem(itemNo);
			}
			
		}catch(Exception e){
			this.em.ThrowException(e, "/n ローの取得に失敗しました。itemNo=" + Integer.toString(itemNo));
		}finally{
			
		}
		return ret;
	}
	/**
	 * ロークラスを取得
	 * @param itemName：ロー名
	 * @return ローRequestResponsRowBean
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public RequestResponsRowBean getRowItem(String itemName) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		RequestResponsRowBean ret = null;
		
		try{
			//item 取得
			if (GetItemNo(itemName) == -1){
				
			}else{
				ret = (RequestResponsRowBean) getRowItem(GetItemNo(itemName));
			}
			
		}catch(Exception e){
			this.em.ThrowException(e, "/n ローの取得に失敗しました。itemNo=" + itemName);
			
		}finally{
			
		}
		return ret;

	}
	/**
	 * ローｵﾌﾞｼﾞｪｸﾄをセットする
	 * @param itemNo：ローインデックス
	 * @param itemRow：ローｵﾌﾞｼﾞｪｸﾄ
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public void setRowItem( int itemNo, RequestResponsRowBean itemRow)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		try{
			//item をセットする
			SetItem(itemRow, itemNo);
			
		}catch(Exception e){
			this.em.ThrowException(e, "/n ローのセットに失敗しました。itemNo=" + Integer.toString(itemNo));
			
		}finally{
			
		}
		
	}
	/**
	 * ローｵﾌﾞｼﾞｪｸﾄをセットする
	 * @param itemRow：ローｵﾌﾞｼﾞｪｸﾄ
	 * @param itemName：ロー名
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public void setRowItem(String itemName, RequestResponsRowBean itemRow)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		try{
			//item をセットする
			SetItem(itemRow, GetItemNo(itemName));
			
		}catch(Exception e){
			this.em.ThrowException(e, "/n ローのセットに失敗しました。itemNo=" + itemName);
			
		}finally{
			
		}
		
	}
	/**
	 * ローの追加
	 * @param itemRow：ローｵﾌﾞｼﾞｪｸﾄ
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public void addRowItem(RequestResponsRowBean itemRow)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try{
			//item を追加する
			AddItem(itemRow);
			
		}catch(Exception e){
			this.em.ThrowException(e, "ローの追加に失敗しました。");
			
		}finally{
			
		}
		
	}
	/**
	 * ローの追加
	 * @param RowID：ローID
	 * @param itemRow：ローｵﾌﾞｼﾞｪｸﾄ
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public RequestResponsRowBean addRowItem(String RowID)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		RequestResponsRowBean ret = new RequestResponsRowBean();
		
		try{
			//item を追加する
			AddItem(ret);
			ret.setID(RowID);
			
		}catch(Exception e){
			this.em.ThrowException(e, "ローの追加に失敗しました。");
			
		}finally{
			
		}
		return ret;
		
	}
	/**
	 * ローの追加
	 * @param itemRow：ローｵﾌﾞｼﾞｪｸﾄ
	 * @param ix：挿入位置インデックス
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public void addRowItem(RequestResponsRowBean itemRow,int ix)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try{
			//item を追加する
			AddItem(itemRow,ix);
			
		}catch(Exception e){
			this.em.ThrowException(e, "ローの追加に失敗しました。");
			
		}finally{
			
		}
		
	}
	/**
	 * ローのIDを取得する
	 * @param itemNo：ローのインデックス
	 * @return　ローの値
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public String getRowID(int itemNo)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		String ret = "";

		try{
			//値を取得
			ret = getRowItem(itemNo).getID();
			
		}catch(Exception e){
			this.em.ThrowException(e, "ローの値、取得に失敗しました。itemNo" + Integer.toString(itemNo));
			
		}finally{
			
		}
		return ret;
		
	}

	//-----------------------------------------------------------------------------

	/**
	 * フィールドクラスを取得
	 * @param RowNo：ローインデックス
	 * @param FieldNo：フィールドインデックス
	 * @return フィールドRequestResponsFieldBean
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public RequestResponsFieldBean getFieldItem(int RowNo, int FieldNo)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		RequestResponsFieldBean ret = null;
		
		try{
			//item 取得
			ret = getRowItem(RowNo).getFieldItem(FieldNo);
			
		}catch(Exception e){
			this.em.ThrowException(e, "/n フィールドの取得に失敗しました。"
					+ "RowNo="
					+ Integer.toString(RowNo)
					+ "FieldNo="
					+ Integer.toString(FieldNo)
					);
		}finally{
			
		}
		return ret;
	}
	/**
	 * フィールドクラスを取得
	 * @param RowID：ロー名
	 * @param FieldNo：フィールドインデックス
	 * @return フィールドRequestResponsFieldBean
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public RequestResponsFieldBean getFieldItem(String RowID, int FieldNo)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		RequestResponsFieldBean ret = null;
		
		try{
			//item 取得
			ret = getRowItem(RowID).getFieldItem(FieldNo);
			
		}catch(Exception e){
			this.em.ThrowException(e, "/n フィールドの取得に失敗しました。"
					+ "RowID="
					+ RowID
					+ "FieldNo="
					+ Integer.toString(FieldNo)
					);
		}finally{
			
		}
		return ret;
	}
	/**
	 * フィールドクラスを取得
	 * @param RowNo：ローインデックス
	 * @param FieldName：フィール名
	 * @return フィールドRequestResponsFieldBean
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public RequestResponsFieldBean getFieldItem(int RowNo, String FieldName)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		RequestResponsFieldBean ret = null;
		
		try{
			//item 取得
			ret = getRowItem(RowNo).getFieldItem(FieldName);
			
		}catch(Exception e){
			this.em.ThrowException(e, "/n フィールドの取得に失敗しました。"
					+ "RowNo="
					+ Integer.toString(RowNo)
					+ "FieldNo="
					+ FieldName
					);
		}finally{
			
		}
		return ret;
	}
	/**
	 * フィールドクラスを取得
	 * @param RowID：ローインデックス
	 * @param FieldName：フィールドインデックス
	 * @return フィールドRequestResponsFieldBean
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public RequestResponsFieldBean getFieldItem(String RowID, String FieldName)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		RequestResponsFieldBean ret = null;
		
		try{
			//item 取得
			ret = getRowItem(RowID).getFieldItem(FieldName);
			
		}catch(Exception e){
			this.em.ThrowException(e, "/n フィールドの取得に失敗しました。"
					+ "RowID="
					+ RowID
					+ "FieldName="
					+ FieldName
					);
		}finally{
			
		}
		return ret;
	}
	/**
	 * フィールドｵﾌﾞｼﾞｪｸﾄをセットする
	 * @param RowNo：ローインデックス
	 * @param FieldNo：フィールドインデックス
	 * @param itemField：フィールドｵﾌﾞｼﾞｪｸﾄ
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public void setFieldItem(int RowNo, int FieldNo ,RequestResponsFieldBean itemField)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		try{
			//item をセットする
			getRowItem(RowNo).setFieldItem(FieldNo, itemField);
			
		}catch(Exception e){
			this.em.ThrowException(e, "/n フィールドのセットに失敗しました。"					
					+ "RowNo="
					+ Integer.toString(RowNo)
					+ "itemNo="
					+ Integer.toString(FieldNo)
					);
			
		}finally{
			
		}
		
	}
	/**
	 * フィールドｵﾌﾞｼﾞｪｸﾄをセットする
	 * @param RowID：ローID
	 * @param FieldNo：フィールドインデックス
	 * @param itemField：フィールドｵﾌﾞｼﾞｪｸﾄ
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public void setFieldItem(String RowID, int FieldNo, RequestResponsFieldBean itemField)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		try{
			//item をセットする
			getRowItem(RowID).setFieldItem(FieldNo, itemField);
			
		}catch(Exception e){
			this.em.ThrowException(e, "/n フィールドのセットに失敗しました。"					
					+ "RowNo="
					+ RowID
					+ "itemNo="
					+ Integer.toString(FieldNo)
					);
			
		}finally{
			
		}
		
	}
	/**
	 * フィールドｵﾌﾞｼﾞｪｸﾄをセットする
	 * @param RowNo：ローNo
	 * @param FieldName：フィールド名
	 * @param itemField：フィールドｵﾌﾞｼﾞｪｸﾄ
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public void setFieldItem(int RowNo, String FieldName, RequestResponsFieldBean itemField)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		try{
			//item をセットする
			getRowItem(RowNo).setFieldItem(FieldName, itemField);
			
		}catch(Exception e){
			this.em.ThrowException(e, "/n フィールドのセットに失敗しました。"					
					+ "RowNo="
					+ Integer.toString(RowNo)
					+ "itemName="
					+ FieldName
					);
			
		}finally{
			
		}
		
	}
	/**
	 * フィールドｵﾌﾞｼﾞｪｸﾄをセットする
	 * @param RowID：ローID
	 * @param FieldName：フィールド名
	 * @param itemField：フィールドｵﾌﾞｼﾞｪｸﾄ
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public void setFieldItem(String RowID, String FieldName, RequestResponsFieldBean itemField)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		try{
			//item をセットする
			getRowItem(RowID).setFieldItem(FieldName, itemField);
			
		}catch(Exception e){
			this.em.ThrowException(e, "/n フィールドのセットに失敗しました。"					
					+ "RowID="
					+ RowID
					+ "itemName="
					+ FieldName
					);
			
		}finally{
			
		}
		
	}
	/**
	 * フィールドｵﾌﾞｼﾞｪｸﾄの追加
	 * @param RowNo：ローインデックス
	 * @param itemField：フィールドｵﾌﾞｼﾞｪｸﾄ
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public void addFieldItem(int RowNo, RequestResponsFieldBean itemField)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try{
			RequestResponsRowBean RowBean = null;
			//item を追加する
			RowBean = getRowItem(RowNo);
			if(RowBean == null){
				RowBean = addRowItem("rec");
				
			}
			RowBean.addFieldItem(itemField);
			
		}catch(Exception e){
			this.em.ThrowException(e, "フィールドの追加に失敗しました。");
			
		}finally{
			
		}
		
	}
	/**
	 * フィールドｵﾌﾞｼﾞｪｸﾄの追加
	 * @param RowID：ローID
	 * @param itemField：フィールドｵﾌﾞｼﾞｪｸﾄ
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public void addFieldItem(String RowID, RequestResponsFieldBean itemField)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try{
			RequestResponsRowBean RowBean = null;
			//item を追加する
			RowBean = getRowItem(RowID);
			if(RowBean == null){
				RowBean = addRowItem("rec");
				
			}
			RowBean.addFieldItem(itemField);
			
		}catch(Exception e){
			this.em.ThrowException(e, "フィールドの追加に失敗しました。");
			
		}finally{
			
		}
		
	}
	/**
	 * フィールドｵﾌﾞｼﾞｪｸﾄの追加
	 * @param RowNo：ローインデックス
	 * @param itemField：フィールドｵﾌﾞｼﾞｪｸﾄ
	 * @param ix：挿入位置インデックス
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public void addFieldItem(int RowNo, RequestResponsFieldBean itemField,int ix)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try{
			RequestResponsRowBean RowBean = null;
			//item を追加する
			RowBean = getRowItem(RowNo);
			if(RowBean == null){
				RowBean = addRowItem("rec");
				
			}
			RowBean.addFieldItem(itemField, ix);
			
		}catch(Exception e){
			this.em.ThrowException(e, "フィールドの追加に失敗しました。");
			
		}finally{
			
		}
		
	}
	/**
	 * フィールドｵﾌﾞｼﾞｪｸﾄの追加
	 * @param RowID：ローID
	 * @param itemField：フィールドｵﾌﾞｼﾞｪｸﾄ
	 * @param ix：挿入位置インデックス
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public void addFieldItem(String RowID, RequestResponsFieldBean itemField,int ix)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try{
			RequestResponsRowBean RowBean = null;
			//item を追加する
			RowBean = getRowItem(RowID);
			if(RowBean == null){
				RowBean = addRowItem("rec");
				
			}
			RowBean.addFieldItem(itemField, ix);
			
		}catch(Exception e){
			this.em.ThrowException(e, "フィールドの追加に失敗しました。");
			
		}finally{
			
		}
		
	}
	/**
	 * フィールドの値を取得する
	 * @param RowNo：ローインデックス
	 * @param FieldNo：フィールドのインデックス
	 * @return　フィールドの値
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public String getFieldVale(int RowNo , int FieldNo) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		String ret = "";

		try{
			//値を取得
			ret = getFieldItem(RowNo, FieldNo).getValue();
			
		}catch(Exception e){
			this.em.ThrowException(e, "フィールドの値、取得に失敗しました。itemNo"
					+ Integer.toString(FieldNo));
			
		}finally{
			
		}
		return ret;
		
	}
	/**
	 * フィールドの値を取得する
	 * @param RowID：ローID
	 * @param FiledNo：フィールドのインデックス
	 * @return　フィールドの値
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public String getFieldVale(String RowID , int FiledNo)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		String ret = "";

		try{
			//値を取得
			ret = getFieldItem(RowID, FiledNo).getValue();
			
		}catch(Exception e){
			this.em.ThrowException(e, "フィールドの値、取得に失敗しました。itemNo"
					+ Integer.toString(FiledNo));
			
		}finally{
			
		}
		return ret;
		
	}
	/**
	 * フィールドの値を取得する
	 * @param RowNo：ローインデックス
	 * @param FiledName：フィールド名
	 * @return　フィールドの値
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public String getFieldVale(int RowNo, String FiledName) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		String ret = "";

		try{
			//値を取得
			ret = getFieldItem(RowNo, FiledName).getValue();
			
		}catch(Exception e){
			this.em.ThrowException(e, "フィールドの値、取得に失敗しました。itemName=" + FiledName);
			
		}finally{
			
		}
		return ret;
		
	}
	/**
	 * フィールドの値を取得する
	 * @param RowID：ローID
	 * @param FieldName：フィールド名
	 * @return　フィールドの値
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public String getFieldVale(String RowID, String FieldName)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		String ret = "";

		try{
			//値を取得
			ret = getFieldItem(RowID, FieldName).getValue();
			
		}catch(Exception e){
			this.em.ThrowException(e, "フィールドの値、取得に失敗しました。itemName=" + FieldName);
			
		}finally{
			
		}
		return ret;
		
	}
	/**
	 * フィールドの値をセットする
	 * @param RowNo：ローインデックス
	 * @param FieldNo：フィールドインデックス
	 * @param FieldValue：フィールドの値
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public void setFieldVale(int RowNo, int FieldNo, String FieldValue)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try{
			//値をセット
			getFieldItem(RowNo, FieldNo).setValue(FieldValue);
			
		}catch(Exception e){
			this.em.ThrowException(e, "フィールドの値、セットに失敗しました。itemNo="
					+ Integer.toString(FieldNo));
			
		}finally{
			
		}
		
	}
	/**
	 * フィールドの値をセットする
	 * @param RowID：ローID
	 * @param FieldNo：フィールドインデックス
	 * @param FieldValue：フィールドの値
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public void setFieldVale(String RowID, int FieldNo, String FieldValue)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try{
			//値をセット
			getFieldItem(RowID, FieldNo).setValue(FieldValue);
			
		}catch(Exception e){
			this.em.ThrowException(e, "フィールドの値、セットに失敗しました。itemNo=" 
					+ Integer.toString(FieldNo));
			
		}finally{
			
		}
		
	}
	/**
	 * フィールドの値をセットする
	 * @param RowNo：ローインデックス
	 * @param FieldName：フィールド名
	 * @param FieldValue：フィールドの値
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public void setFieldVale(int RowNo, String FieldName, String FieldValue)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try{
			//値をセット
			getFieldItem(RowNo, FieldName).setValue(FieldValue);
			
		}catch(Exception e){
			this.em.ThrowException(e, "フィールドの値、セットに失敗しました。itemNo=" + FieldName);
			
		}finally{
			
		}
		
	}
	/**
	 * フィールドの値をセットする
	 * @param RowID：ローID
	 * @param FieldName：フィールド名
	 * @param FieldValue：フィールドの値
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public void setFieldVale(String RowID, String FieldName, String FieldValue)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try{
			//値をセット
			getFieldItem(RowID, FieldName).setValue(FieldValue);
			
		}catch(Exception e){
			this.em.ThrowException(e, "フィールドの値、セットに失敗しました。itemNo=" + FieldName);
			
		}finally{
			
		}
		
	}
	/**
	 * フィールドの値を追加する
	 * @param RowNo：ローインデックス
	 * @param FieldValue：フィールドの値
	 * @param FieldName：フィールド名
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public void addFieldVale(int RowNo, String FieldName, String FieldValue)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try{
			RequestResponsRowBean RowBean = null; 
			
			RowBean = getRowItem(RowNo);
			if(RowBean == null){
				RowBean = addRowItem("rec");
			}
			RowBean.addFieldItem(new RequestResponsFieldBean(FieldName,FieldValue));
			
		}catch(Exception e){
			this.em.ThrowException(e, "フィールドの値、追加に失敗しました。itemNo=" + FieldName);
			
		}finally{
			
		}
		
	}
	/**
	 * フィールドの値を追加する
	 * @param RowID：ローID
	 * @param FieldName：フィールド名
	 * @param FieldValue：フィールドの値
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public void addFieldVale(String RowID, String FieldName, String FieldValue)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try{
			RequestResponsRowBean RowBean = null; 
			
			RowBean = getRowItem(RowID);
			if(RowBean == null){
				RowBean = addRowItem(RowID);
			}
			RowBean.addFieldItem(new RequestResponsFieldBean(FieldName,FieldValue));
			
		}catch(Exception e){
			this.em.ThrowException(e, "フィールドの値、追加に失敗しました。itemNo=" + FieldName);
			
		}finally{
			
		}
		
	}
	
}
