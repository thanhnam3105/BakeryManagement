package jp.co.blueflag.shisaquick.srv.base;

import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;

/**
 * 
 * リクエスト/レスポンスKaind (機能ID)
 *  : リクエスト/レスポンスのデータ保持で、最小単位のデータ管理を行う
 * @author TT.isono
 * @since  2009/03/24
 */
public class RequestResponsKindBean  extends RequestResponsBean {

	/**
	 * コンストラクタ
	 */
	public RequestResponsKindBean() {
		//基底ｸﾗｽのコンストラクタ
		super();
		
	}
	/**
	 * テーブルクラスを取得
	 * @param itemNo：テーブルインデックス
	 * @return テーブルRequestResponsFieldBean
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public RequestResponsTableBean getTableItem(int itemNo)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		RequestResponsTableBean ret = null;
		
		try{
			//item 取得
			ret = (RequestResponsTableBean) GetItem(itemNo);
			
		}catch(Exception e){
			this.em.ThrowException(e, "/n テーブルの取得に失敗しました。itemNo=" + Integer.toString(itemNo));
		}finally{
			
		}
		return ret;
	}
	/**
	 * テーブルクラスを取得
	 * @param itemName：テーブル名
	 * @return テーブルRequestResponsTableBean
	 * @Throws ExceptionSystem
	 * @Throws ExceptionUser
	 * @Throws ExceptionWaning
	 */
	public RequestResponsTableBean getTableItem(String itemName)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		RequestResponsTableBean ret = null;
		
		try{
			//item 取得
			ret = (RequestResponsTableBean) getTableItem(GetItemNo(itemName));
			
		}catch(Exception e){
			this.em.ThrowException(e, "/n テーブルの取得に失敗しました。itemNo=" + itemName);
			
		}finally{
			
		}
		return ret;

	}
	/**
	 * テーブルｵﾌﾞｼﾞｪｸﾄをセットする
	 * @param TableNo：テーブルインデックス
	 * @param itemTable：テーブルｵﾌﾞｼﾞｪｸﾄ
	 * @Throws ExceptionSystem
	 * @Throws ExceptionUser
	 * @Throws ExceptionWaning
	 */
	public void setTableItem( int TableNo, RequestResponsTableBean itemTable)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		try{
			//item をセットする
			SetItem(itemTable, TableNo);
			
		}catch(Exception e){
			this.em.ThrowException(e, "/n テーブルのセットに失敗しました。itemNo="
					+ Integer.toString(TableNo));
			
		}finally{
			
		}
		
	}
	/**
	 * テーブルｵﾌﾞｼﾞｪｸﾄをセットする
	 * @param TableName：テーブル名
	 * @param itemTable：テーブルｵﾌﾞｼﾞｪｸﾄ
	 * @Throws ExceptionSystem
	 * @Throws ExceptionUser
	 * @Throws ExceptionWaning
	 */
	public void setTableItem(String TableName, RequestResponsTableBean itemTable)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		try{
			//item をセットする
			SetItem(itemTable, GetItemNo(TableName));
			
		}catch(Exception e){
			this.em.ThrowException(e, "/n テーブルのセットに失敗しました。itemNo=" + TableName);
			
		}finally{
			
		}
		
	}
	/**
	 * テーブルの追加
	 * @param itemTable：テーブルｵﾌﾞｼﾞｪｸﾄ
	 * @Throws ExceptionSystem
	 * @Throws ExceptionUser
	 * @Throws ExceptionWaning
	 */
	public void addTableItem(RequestResponsTableBean itemTable)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try{
			//item を追加する
			AddItem(itemTable);
			
		}catch(Exception e){
			this.em.ThrowException(e, "テーブルの追加に失敗しました。");
			
		}finally{
			
		}
		
	}
	/**
	 * テーブルの追加
	 * @param TableID：テーブルID
	 * @return テーブルｵﾌﾞｼﾞｪｸﾄ
	 * @Throws ExceptionSystem
	 * @Throws ExceptionUser
	 * @Throws ExceptionWaning
	 */
	public RequestResponsTableBean addTableItem(String TableID)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		RequestResponsTableBean ret = new RequestResponsTableBean();
		
		try{
			//item を追加する
			AddItem(ret);
			ret.setID(TableID);
			
		}catch(Exception e){
			this.em.ThrowException(e, "テーブルの追加に失敗しました。");
			
		}finally{
			
		}
		return ret;
		
	}
	/**
	 * テーブルの追加
	 * @param itemTable：テーブルｵﾌﾞｼﾞｪｸﾄ
	 * @param ix：挿入位置インデックス
	 * @Throws ExceptionSystem
	 * @Throws ExceptionUser
	 * @Throws ExceptionWaning
	 */
	public void addTableItem(RequestResponsTableBean itemTable,int ix)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try{
			//item を追加する
			AddItem(itemTable,ix);
			
		}catch(Exception e){
			this.em.ThrowException(e, "テーブルの追加に失敗しました。");
			
		}finally{
			
		}
		
	}
	/**
	 * テーブルのIDを取得する
	 * @param itemNo：テーブルのインデックス
	 * @return　テーブルの値
	 * @Throws ExceptionSystem
	 * @Throws ExceptionUser
	 * @Throws ExceptionWaning
	 */
	public String getTableID(int itemNo)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		String ret = "";

		try{
			//値を取得
			ret = getTableItem(itemNo).getID();
			
		}catch(Exception e){
			this.em.ThrowException(e, "テーブルの値、取得に失敗しました。itemNo" + Integer.toString(itemNo));
			
		}finally{
			
		}
		return ret;
		
	}

	//-----------------------------------------------------------------------------

	/**
	 * ロークラスを取得
	 * @param TableNo：テーブルインデックス
	 * @param RowNo：ローインデックス
	 * @return ローRequestResponsFieldBean
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public RequestResponsRowBean getRowItem(int TableNo, int RowNo)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		RequestResponsRowBean ret = null;
		
		try{
			//item 取得
			ret = getTableItem(TableNo).getRowItem(RowNo);
			
		}catch(Exception e){
			this.em.ThrowException(e, "/n ローの取得に失敗しました。itemNo=" 
					+ Integer.toString(RowNo));
		}finally{
			
		}
		return ret;
	}
	/**
	 * ロークラスを取得
	 * @param TableID：テーブルID
	 * @param RowNo：ローインデックス
	 * @return ローRequestResponsFieldBean
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public RequestResponsRowBean getRowItem(String TableID, int RowNo)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		RequestResponsRowBean ret = null;
		
		try{
			//item 取得
			ret = getTableItem(TableID).getRowItem(RowNo);
			
		}catch(Exception e){
			this.em.ThrowException(e, "/n ローの取得に失敗しました。itemNo=" 
					+ Integer.toString(RowNo));
		}finally{
			
		}
		return ret;
	}
	/**
	 * ロークラスを取得
	 * @param TableNo：テーブルインデックス
	 * @param RowName：ロー名
	 * @return ローRequestResponsRowBean
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public RequestResponsRowBean getRowItem(int TableNo, String RowName)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		RequestResponsRowBean ret = null;
		
		try{
			//item 取得
			ret = getTableItem(TableNo).getRowItem(RowName);
			
		}catch(Exception e){
			this.em.ThrowException(e, "/n ローの取得に失敗しました。itemNo=" + RowName);
			
		}finally{
			
		}
		return ret;

	}
	/**
	 * ロークラスを取得
	 * @param TableID：テーブルID
	 * @param RowName：ロー名
	 * @return ローRequestResponsRowBean
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public RequestResponsRowBean getRowItem(String TableID, String RowName)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		RequestResponsRowBean ret = null;
		
		try{
			//item 取得
			ret = getTableItem(TableID).getRowItem(RowName);
			
		}catch(Exception e){
			this.em.ThrowException(e, "/n ローの取得に失敗しました。itemNo=" + RowName);
			
		}finally{
			
		}
		return ret;

	}
	/**
	 * ローｵﾌﾞｼﾞｪｸﾄをセットする
	 * @param TableNo：テーブルインデックス
	 * @param RowNo：ローインデックス
	 * @param itemRow：ローｵﾌﾞｼﾞｪｸﾄ
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public void setRowItem(int TableNo, int RowNo, RequestResponsRowBean itemRow)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		try{
			try{
				//item をセットする
				getTableItem(TableNo).setRowItem(RowNo, itemRow);
				
			}catch(Exception e){
				addRowItem(TableNo, itemRow);
			}
			
		}catch(Exception e){
			this.em.ThrowException(e, "/n ローのセットに失敗しました。itemNo=" + Integer.toString(RowNo));
			
		}finally{
			
		}
		
	}
	/**
	 * ローｵﾌﾞｼﾞｪｸﾄをセットする
	 * @param TableID：テーブルインデックス
	 * @param RowNo：ローインデックス
	 * @param itemRow：ローｵﾌﾞｼﾞｪｸﾄ
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public void setRowItem(String TableID, int RowNo, RequestResponsRowBean itemRow)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		try{
			try{
				//item をセットする
				getTableItem(TableID).setRowItem(RowNo, itemRow);
				
			}catch(Exception e){
				addRowItem(TableID, itemRow);
			}
			
		}catch(Exception e){
			this.em.ThrowException(e, "/n ローのセットに失敗しました。itemNo="
					+ Integer.toString(RowNo));
			
		}finally{
			
		}
		
	}
	/**
	 * ローｵﾌﾞｼﾞｪｸﾄをセットする
	 * @param TableNo：テーブルインデックス
	 * @param RowID：ロー名
	 * @param itemRow：ローｵﾌﾞｼﾞｪｸﾄ
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public void setRowItem(int TableNo, String RowID, RequestResponsRowBean itemRow)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		try{
			try{
				//item をセットする
				getTableItem(TableNo).setRowItem(RowID, itemRow);
				
			}catch(Exception e){
				addRowItem(TableNo, itemRow);
			}
			
		}catch(Exception e){
			this.em.ThrowException(e, "/n ローのセットに失敗しました。itemNo=" + RowID);
			
		}finally{
			
		}
		
	}
	/**
	 * ローｵﾌﾞｼﾞｪｸﾄをセットする
	 * @param TableID：テーブルID
	 * @param RowID：ロー名
	 * @param itemRow：ローｵﾌﾞｼﾞｪｸﾄ
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public void setRowItem(String TableID, String RowID, RequestResponsRowBean itemRow)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		try{
			try{
				//item をセットする
				getTableItem(TableID).setRowItem(RowID, itemRow);
				
			}catch(Exception e){
				addRowItem(TableID, itemRow);
			}
			
		}catch(Exception e){
			this.em.ThrowException(e, "/n ローのセットに失敗しました。itemNo=" + RowID);
			
		}finally{
			
		}
		
	}
	/**
	 * ローの追加
	 * @param TableNo：テーブルインデックス
	 * @param itemRow：ローｵﾌﾞｼﾞｪｸﾄ
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public void addRowItem(int TableNo, RequestResponsRowBean itemRow)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try{
			//item を追加する
			RequestResponsTableBean TableBean = null;
			
			TableBean = getTableItem(TableNo);
			if (TableBean == null){
				TableBean = addTableItem("table");
			}
			TableBean.addRowItem(itemRow);
			
		}catch(Exception e){
			this.em.ThrowException(e, "ローの追加に失敗しました。");
			
		}finally{
			
		}
		
	}
	/**
	 * ローの追加
	 * @param TableID：テーブルID
	 * @param itemRow：ローｵﾌﾞｼﾞｪｸﾄ
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public void addRowItem(String TableID, RequestResponsRowBean itemRow)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try{
			//item を追加する
			RequestResponsTableBean TableBean = null;
			
			TableBean = getTableItem(TableID);
			if (TableBean == null){
				TableBean = addTableItem(TableID);
			}
			TableBean.addRowItem(itemRow);
			
		}catch(Exception e){
			this.em.ThrowException(e, "ローの追加に失敗しました。");
			
		}finally{
			
		}
		
	}
	/**
	 * ローの追加
	 * @param TableNo：テーブルインデックス
	 * @param RowID：ローID
	 * @param itemRow：ローｵﾌﾞｼﾞｪｸﾄ
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public RequestResponsRowBean addRowItem(int TableNo, String RowID)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		RequestResponsRowBean RowBean = null; 
		
		try{
			//item を追加する
			RequestResponsTableBean TableBean = null;
			
			TableBean = getTableItem(TableNo);
			if (TableBean == null){
				TableBean = addTableItem("table");
			}
			RowBean = TableBean.addRowItem(RowID);
			
		}catch(Exception e){
			this.em.ThrowException(e, "ローの追加に失敗しました。");
			
		}finally{
			
		}
		return RowBean;
		
	}
	/**
	 * ローの追加
	 * @param TableID：テーブルID
	 * @param RowID：ローID
	 * @param itemRow：ローｵﾌﾞｼﾞｪｸﾄ
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public RequestResponsRowBean addRowItem(String TableID, String RowID)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		RequestResponsRowBean RowBean = null; 
		
		try{
			//item を追加する
			RequestResponsTableBean TableBean = null;
			
			TableBean = getTableItem(TableID);
			if (TableBean == null){
				TableBean = addTableItem(TableID);
			}
			RowBean = TableBean.addRowItem(RowID);
			
		}catch(Exception e){
			this.em.ThrowException(e, "ローの追加に失敗しました。");
			
		}finally{
			
		}
		return RowBean;
		
	}
	/**
	 * ローの追加
	 * @param TableNo：テーブルインデックス
	 * @param itemRow：ローｵﾌﾞｼﾞｪｸﾄ
	 * @param ix：挿入位置インデックス
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public void addRowItem(int TableNo, RequestResponsRowBean itemRow,int ix)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try{
			//item を追加する
			RequestResponsTableBean TableBean = null;
			
			TableBean = getTableItem(TableNo);
			if (TableBean == null){
				TableBean = addTableItem("rec");
			}
			TableBean.addRowItem(itemRow, ix);
			
		}catch(Exception e){
			this.em.ThrowException(e, "ローの追加に失敗しました。");
			
		}finally{
			
		}
		
	}
	/**
	 * ローの追加
	 * @param TableID：テーブルID
	 * @param itemRow：ローｵﾌﾞｼﾞｪｸﾄ
	 * @param ix：挿入位置インデックス
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public void addRowItem(String TableID, RequestResponsRowBean itemRow,int ix)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try{
			//item を追加する
			RequestResponsTableBean TableBean = null;
			
			TableBean = getTableItem(TableID);
			if (TableBean == null){
				TableBean = addTableItem(TableID);
			}
			TableBean.addRowItem(itemRow, ix);
			
		}catch(Exception e){
			this.em.ThrowException(e, "ローの追加に失敗しました。");
			
		}finally{
			
		}
		
	}
	/**
	 * ローのIDを取得する
	 * @param TableNo：テーブルのインデックス
	 * @param RowNo：ローのインデックス
	 * @return　ローの値
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public String getRowID(int TableNo, int RowNo)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		String ret = "";

		try{
			//値を取得
			ret = getTableItem(TableNo).getRowID(RowNo);
			
		}catch(Exception e){
			this.em.ThrowException(e, "ローの値、取得に失敗しました。itemNo"
					+ Integer.toString(RowNo));
			
		}finally{
			
		}
		return ret;
		
	}
	/**
	 * ローのIDを取得する
	 * @param TableID：テーブルID
	 * @param RowNo：ローのインデックス
	 * @return　ローの値
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public String getRowID(String TableID, int RowNo)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		String ret = "";

		try{
			//値を取得
			ret = getTableItem(TableID).getRowID(RowNo);
			
		}catch(Exception e){
			this.em.ThrowException(e, "ローの値、取得に失敗しました。itemNo"
					+ Integer.toString(RowNo));
			
		}finally{
			
		}
		return ret;
		
	}

	//-----------------------------------------------------------------------------

	/**
	 * フィールドクラスを取得
	 * @param TableNo：テーブルインデックス
	 * @param RowNo：ローインデックス
	 * @param FieldNo：フィールドインデックス
	 * @return フィールドRequestResponsFieldBean
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public RequestResponsFieldBean getFieldItem(int TableNo, int RowNo, int FieldNo)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		RequestResponsFieldBean ret = null;
		
		try{
			//item 取得
			ret = getRowItem(TableNo, RowNo).getFieldItem(FieldNo);
			
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
	 * @param TableID：テーブルID
	 * @param RowNo：ローインデックス
	 * @param FieldNo：フィールドインデックス
	 * @return フィールドRequestResponsFieldBean
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public RequestResponsFieldBean getFieldItem(String TableID, int RowNo, int FieldNo)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		RequestResponsFieldBean ret = null;
		
		try{
			//item 取得
			ret = getRowItem(TableID, RowNo).getFieldItem(FieldNo);
			
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
	 * @param TableNo：テーブルインデックス
	 * @param RowID：ローID
	 * @param FieldNo：フィールドインデックス
	 * @return フィールドRequestResponsFieldBean
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public RequestResponsFieldBean getFieldItem(int TableNo, String RowID, int FieldNo)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		RequestResponsFieldBean ret = null;
		
		try{
			//item 取得
			ret = getRowItem(TableNo, RowID).getFieldItem(FieldNo);
			
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
	 * @param TableID：テーブルID
	 * @param RowID：ローID
	 * @param FieldNo：フィールドインデックス
	 * @return フィールドRequestResponsFieldBean
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public RequestResponsFieldBean getFieldItem(String TableID, String RowID, int FieldNo)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		RequestResponsFieldBean ret = null;
		
		try{
			//item 取得
			ret = getRowItem(TableID, RowID).getFieldItem(FieldNo);
			
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
	 * @param TableNo：テーブルインデックス
	 * @param RowNo：ローインデックス
	 * @param FieldName：フィール名
	 * @return フィールドRequestResponsFieldBean
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public RequestResponsFieldBean getFieldItem(int TableNo, int RowNo, String FieldName)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		RequestResponsFieldBean ret = null;
		
		try{
			//item 取得
			ret = getRowItem(TableNo, RowNo).getFieldItem(FieldName);
			
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
	 * @param TableID：テーブルID
	 * @param RowNo：ローインデックス
	 * @param FieldName：フィール名
	 * @return フィールドRequestResponsFieldBean
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public RequestResponsFieldBean getFieldItem(String TableID, int RowNo, String FieldName)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		RequestResponsFieldBean ret = null;
		
		try{
			//item 取得
			ret = getRowItem(TableID, RowNo).getFieldItem(FieldName);
			
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
	 * @param TableNo：テーブルインデックス
	 * @param RowID：ローインデックス
	 * @param FieldName：フィールドインデックス
	 * @return フィールドRequestResponsFieldBean
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public RequestResponsFieldBean getFieldItem(int TableNo, String RowID, String FieldName)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		RequestResponsFieldBean ret = null;
		
		try{
			//item 取得
			ret = getRowItem(TableNo, RowID).getFieldItem(FieldName);
			
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
	 * フィールドクラスを取得
	 * @param TableID：テーブルID
	 * @param RowID：ローインデックス
	 * @param FieldName：フィールドインデックス
	 * @return フィールドRequestResponsFieldBean
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public RequestResponsFieldBean getFieldItem(String TableID, String RowID, String FieldName)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		RequestResponsFieldBean ret = null;
		
		try{
			//item 取得
			ret = getRowItem(TableID, RowID).getFieldItem(FieldName);
			
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
	 * @param TableNo：テーブルインデックス
	 * @param RowNo：ローインデックス
	 * @param FieldNo：フィールドインデックス
	 * @param itemField：フィールドｵﾌﾞｼﾞｪｸﾄ
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public void setFieldItem(int TableNo, int RowNo, int FieldNo ,RequestResponsFieldBean itemField)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		try{
			try{
				//item をセットする
				getRowItem(TableNo, RowNo).setFieldItem(FieldNo, itemField);
				
			}catch(Exception e){
				addFieldItem(TableNo, RowNo, itemField);
				
			}
			
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
	 * @param TableID：テーブルインデックス
	 * @param RowNo：ローインデックス
	 * @param FieldNo：フィールドインデックス
	 * @param itemField：フィールドｵﾌﾞｼﾞｪｸﾄ
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public void setFieldItem(String TableID, int RowNo, int FieldNo ,RequestResponsFieldBean itemField)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		try{
			try{
				//item をセットする
				getRowItem(TableID, RowNo).setFieldItem(FieldNo, itemField);
				
			}catch(Exception e){
				addFieldItem(TableID, RowNo, itemField);
				
			}
			
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
	 * @param TableNo：テーブルインデックス
	 * @param RowID：ローID
	 * @param FieldNo：フィールドインデックス
	 * @param itemField：フィールドｵﾌﾞｼﾞｪｸﾄ
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public void setFieldItem(int TableNo, String RowID, int FieldNo ,RequestResponsFieldBean itemField)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		try{
			try{
				//item をセットする
				getRowItem(TableNo, RowID).setFieldItem(FieldNo, itemField);
				
			}catch(Exception e){
				addFieldItem(TableNo, RowID, itemField);
				
			}
			
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
	 * @param TableID：テーブルID
	 * @param RowID：ローID
	 * @param FieldNo：フィールドインデックス
	 * @param itemField：フィールドｵﾌﾞｼﾞｪｸﾄ
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public void setFieldItem(String TableID, String RowID, int FieldNo ,RequestResponsFieldBean itemField)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		try{
			try{
				//item をセットする
				getRowItem(TableID, RowID).setFieldItem(FieldNo, itemField);
				
			}catch(Exception e){
				addFieldItem(TableID, RowID, itemField);
				
			}
			
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
	 * @param TableNo：テーブルインデックス
	 * @param RowNo：ローインデックス
	 * @param FieldName：フィールド名
	 * @param itemField：フィールドｵﾌﾞｼﾞｪｸﾄ
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public void setFieldItem(int TableNo, int RowNo, String FieldName, RequestResponsFieldBean itemField)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		try{
			try{
				//item をセットする
				getRowItem(TableNo, RowNo).setFieldItem(FieldName,itemField);
				
			}catch(Exception e){
				addFieldItem(TableNo, RowNo, itemField);
				
			}
			
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
	 * @param TableID：テーブルインデックス
	 * @param RowNo：ローインデックス
	 * @param FieldName：フィールド名
	 * @param itemField：フィールドｵﾌﾞｼﾞｪｸﾄ
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public void setFieldItem(String TableID, int RowNo, String FieldName, RequestResponsFieldBean itemField)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		try{
			try{
				//item をセットする
				getRowItem(TableID, RowNo).setFieldItem(FieldName, itemField);
				
			}catch(Exception e){
				addFieldItem(TableID, RowNo, itemField);
				
			}
			
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
	 * @param TableNo：テーブルインデックス
	 * @param RowID：ローID
	 * @param FieldName：フィールド名
	 * @param itemField：フィールドｵﾌﾞｼﾞｪｸﾄ
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public void setFieldItem(int TableNo, String RowID, String FieldName, RequestResponsFieldBean itemField)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		try{
			try{
				//item をセットする
				getRowItem(TableNo, RowID).setFieldItem(FieldName, itemField);
				
			}catch(Exception e){
				addFieldItem(TableNo, RowID, itemField);
				
			}
			
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
	 * フィールドｵﾌﾞｼﾞｪｸﾄをセットする
	 * @param TableID：テーブルインデックス
	 * @param RowID：ローID
	 * @param FieldName：フィールド名
	 * @param itemField：フィールドｵﾌﾞｼﾞｪｸﾄ
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public void setFieldItem(String TableID, String RowID, String FieldName, RequestResponsFieldBean itemField)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		try{
			try{
				//item をセットする
				getRowItem(TableID, RowID).setFieldItem(FieldName, itemField);
				
			}catch(Exception e){
				addFieldItem(TableID, RowID, itemField);
				
			}
			
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
	 * @param TableNo：テーブルインデックス
	 * @param RowNo：ローインデックス
	 * @param itemField：フィールドｵﾌﾞｼﾞｪｸﾄ
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public void addFieldItem(int TableNo, int RowNo, RequestResponsFieldBean itemField)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try{
			RequestResponsTableBean TableBean = null;
			RequestResponsRowBean RowBean = null;

			//item を追加する
			TableBean = getTableItem(TableNo);
			if (TableBean == null){
				TableBean = addTableItem("table");
			}
			RowBean = TableBean.getRowItem(RowNo);
			if (RowBean == null){
				RowBean = TableBean.addRowItem("rec");
			}
			
			RowBean.addFieldItem(itemField);
			
		}catch(Exception e){
			this.em.ThrowException(e, "フィールドの追加に失敗しました。");
			
		}finally{
			
		}
		
	}
	/**
	 * フィールドｵﾌﾞｼﾞｪｸﾄの追加
	 * @param TableID：テーブルID
	 * @param RowNo：ローインデックス
	 * @param itemField：フィールドｵﾌﾞｼﾞｪｸﾄ
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public void addFieldItem(String TableID, int RowNo, RequestResponsFieldBean itemField)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try{
			RequestResponsTableBean TableBean = null;
			RequestResponsRowBean RowBean = null;

			//item を追加する
			TableBean = getTableItem(TableID);
			if (TableBean == null){
				TableBean = addTableItem(TableID);
			}
			RowBean = TableBean.getRowItem(RowNo);
			if (RowBean == null){
				RowBean = TableBean.addRowItem("rec");
			}
			
			RowBean.addFieldItem(itemField);
			
		}catch(Exception e){
			this.em.ThrowException(e, "フィールドの追加に失敗しました。");
			
		}finally{
			
		}
		
	}
	/**
	 * フィールドｵﾌﾞｼﾞｪｸﾄの追加
	 * @param TableNo：テーブルインデックス
	 * @param RowID：ローID
	 * @param itemField：フィールドｵﾌﾞｼﾞｪｸﾄ
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public void addFieldItem(int TableNo, String RowID, RequestResponsFieldBean itemField)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try{
			RequestResponsTableBean TableBean = null;
			RequestResponsRowBean RowBean = null;

			//item を追加する
			TableBean = getTableItem(TableNo);
			if (TableBean == null){
				TableBean = addTableItem("table");
			}
			RowBean = TableBean.getRowItem(RowID);
			if (RowBean == null){
				RowBean = TableBean.addRowItem(RowID);
			}
			
			RowBean.addFieldItem(itemField);
			
		}catch(Exception e){
			this.em.ThrowException(e, "フィールドの追加に失敗しました。");
			
		}finally{
			
		}
		
	}
	/**
	 * フィールドｵﾌﾞｼﾞｪｸﾄの追加
	 * @param TableID：テーブルID
	 * @param RowID：ローID
	 * @param itemField：フィールドｵﾌﾞｼﾞｪｸﾄ
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public void addFieldItem(String TableID, String RowID, RequestResponsFieldBean itemField)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try{
			RequestResponsTableBean TableBean = null;
			RequestResponsRowBean RowBean = null;

			//item を追加する
			TableBean = getTableItem(TableID);
			if (TableBean == null){
				TableBean = addTableItem(TableID);
			}
			RowBean = TableBean.getRowItem(RowID);
			if (RowBean == null){
				RowBean = TableBean.addRowItem(RowID);
			}
			
			RowBean.addFieldItem(itemField);
			
		}catch(Exception e){
			this.em.ThrowException(e, "フィールドの追加に失敗しました。");
			
		}finally{
			
		}
		
	}
	/**
	 * フィールドｵﾌﾞｼﾞｪｸﾄの追加
	 * @param TableNo：テーブルインデックス
	 * @param RowNo：ローインデックス
	 * @param itemField：フィールドｵﾌﾞｼﾞｪｸﾄ
	 * @param ix：挿入位置インデックス
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public void addFieldItem(int TableNo, int RowNo, RequestResponsFieldBean itemField,int ix)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try{
			RequestResponsTableBean TableBean = null;
			RequestResponsRowBean RowBean = null;

			//item を追加する
			TableBean = getTableItem(TableNo);
			if (TableBean == null){
				TableBean = addTableItem("table");
			}
			RowBean = TableBean.getRowItem(RowNo);
			if (RowBean == null){
				RowBean = TableBean.addRowItem("rec");
			}
			
			RowBean.addFieldItem(itemField, ix);
			
		}catch(Exception e){
			this.em.ThrowException(e, "フィールドの追加に失敗しました。");
			
		}finally{
			
		}
		
	}
	/**
	 * フィールドｵﾌﾞｼﾞｪｸﾄの追加
	 * @param TableID：テーブルID
	 * @param RowNo：ローインデックス
	 * @param itemField：フィールドｵﾌﾞｼﾞｪｸﾄ
	 * @param ix：挿入位置インデックス
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public void addFieldItem(String TableID, int RowNo, RequestResponsFieldBean itemField,int ix)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try{
			RequestResponsTableBean TableBean = null;
			RequestResponsRowBean RowBean = null;

			//item を追加する
			TableBean = getTableItem(TableID);
			if (TableBean == null){
				TableBean = addTableItem(TableID);
			}
			RowBean = TableBean.getRowItem(RowNo);
			if (RowBean == null){
				RowBean = TableBean.addRowItem("rec");
			}
			RowBean.addFieldItem(itemField, ix);
			
		}catch(Exception e){
			this.em.ThrowException(e, "フィールドの追加に失敗しました。");
			
		}finally{
			
		}
		
	}
	/**
	 * フィールドｵﾌﾞｼﾞｪｸﾄの追加
	 * @param TableNo：テーブルインデックス
	 * @param RowID：ローID
	 * @param itemField：フィールドｵﾌﾞｼﾞｪｸﾄ
	 * @param ix：挿入位置インデックス
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public void addFieldItem(int TableNo, String RowID, RequestResponsFieldBean itemField,int ix)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try{
			RequestResponsTableBean TableBean = null;
			RequestResponsRowBean RowBean = null;

			//item を追加する
			TableBean = getTableItem(TableNo);
			if (TableBean == null){
				TableBean = addTableItem("table");
			}
			RowBean = TableBean.getRowItem(RowID);
			if (RowBean == null){
				RowBean = TableBean.addRowItem(RowID);
			}
			
			RowBean.addFieldItem(itemField, ix);
			
		}catch(Exception e){
			this.em.ThrowException(e, "フィールドの追加に失敗しました。");
			
		}finally{
			
		}
		
	}
	/**
	 * フィールドの値を取得する
	 * @param TableNo：テーブルインデックス
	 * @param RowNo：ローインデックス
	 * @param itemNo：フィールドのインデックス
	 * @return　フィールドの値
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public String getFieldVale(int TableNo, int RowNo , int itemNo)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		String ret = "";

		try{
			//値を取得
			ret = getFieldItem(TableNo, RowNo, itemNo).getValue();
			
		}catch(Exception e){
			this.em.ThrowException(e, "フィールドの値、取得に失敗しました。itemNo" + Integer.toString(itemNo));
			
		}finally{
			
		}
		return ret;
		
	}
	/**
	 * フィールドの値を取得する
	 * @param TableID：テーブルID
	 * @param RowNo：ローインデックス
	 * @param itemNo：フィールドのインデックス
	 * @return　フィールドの値
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public String getFieldVale(String TableID, int RowNo , int itemNo) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		String ret = "";

		try{
			//値を取得
			ret = getFieldItem(TableID, RowNo, itemNo).getValue();
			
		}catch(Exception e){
			this.em.ThrowException(e, "フィールドの値、取得に失敗しました。itemNo" 
					+ Integer.toString(itemNo));
			
		}finally{
			
		}
		return ret;
		
	}
	/**
	 * フィールドの値を取得する
	 * @param TableNo：テーブルインデックス
	 * @param RowID：ローID
	 * @param itemNo：フィールドのインデックス
	 * @return　フィールドの値
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public String getFieldVale(int TableNo, String RowID , int itemNo)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		String ret = "";

		try{
			//値を取得
			ret = getFieldItem(TableNo, RowID, itemNo).getValue();
			
		}catch(Exception e){
			this.em.ThrowException(e, "フィールドの値、取得に失敗しました。itemNo" 
					+ Integer.toString(itemNo));
			
		}finally{
			
		}
		return ret;
		
	}
	/**
	 * フィールドの値を取得する
	 * @param TableID：テーブルID
	 * @param RowID：ローID
	 * @param itemNo：フィールドのインデックス
	 * @return　フィールドの値
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public String getFieldVale(String TableID, String RowID , int itemNo)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		String ret = "";

		try{
			//値を取得
			ret = getFieldItem(TableID, RowID, itemNo).getValue();
			
		}catch(Exception e){
			this.em.ThrowException(e, "フィールドの値、取得に失敗しました。itemNo" 
					+ Integer.toString(itemNo));
			
		}finally{
			
		}
		return ret;
		
	}
	/**
	 * フィールドの値を取得する
	 * @param TableNo：テーブルインデックス
	 * @param RowNo：ローインデックス
	 * @param itemName：フィールド名
	 * @return　フィールドの値
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public String getFieldVale(int TableNo, int RowNo, String itemName)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		String ret = "";

		try{
			//値を取得
			ret = getFieldItem(TableNo, RowNo, itemName).getValue();
			
		}catch(Exception e){
			this.em.ThrowException(e, "フィールドの値、取得に失敗しました。itemName=" 
					+ itemName);
			
		}finally{
			
		}
		return ret;
		
	}
	/**
	 * フィールドの値を取得する
	 * @param TableID：テーブID
	 * @param RowNo：ローインデックス
	 * @param itemName：フィールド名
	 * @return　フィールドの値
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public String getFieldVale(String TableID, int RowNo, String itemName)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		String ret = "";

		try{
			//値を取得
			ret = getFieldItem(TableID, RowNo, itemName).getValue();
			
		}catch(Exception e){
			this.em.ThrowException(e, "フィールドの値、取得に失敗しました。itemName=" + itemName);
			
		}finally{
			
		}
		return ret;
		
	}
	/**
	 * フィールドの値を取得する
	 * @param TableNo：テーブルインデックス
	 * @param RowID：ローID
	 * @param itemName：フィールド名
	 * @return　フィールドの値
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public String getFieldVale(int TableNo, String RowID, String itemName)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		String ret = "";

		try{
			//値を取得
			ret = getFieldItem(TableNo, RowID, itemName).getValue();
			
		}catch(Exception e){
			this.em.ThrowException(e, "フィールドの値、取得に失敗しました。itemName=" + itemName);
			
		}finally{
			
		}
		return ret;
		
	}
	/**
	 * フィールドの値を取得する
	 * @param TableID：テーブルインデックス
	 * @param RowID：ローID
	 * @param itemName：フィールド名
	 * @return　フィールドの値
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public String getFieldVale(String TableID, String RowID, String itemName)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		String ret = "";

		try{
			//値を取得
			ret = getFieldItem(TableID, RowID, itemName).getValue();
			
		}catch(Exception e){
			this.em.ThrowException(e, "フィールドの値、取得に失敗しました。itemName=" + itemName);
			
		}finally{
			
		}
		return ret;
		
	}
	/**
	 * フィールドの値をセットする
	 * @param TableNo：テーブルインデックス
	 * @param RowNo：ローインデックス
	 * @param FieldNo：フィールドインデックス
	 * @param itemValue：フィールドの値
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public void setFieldVale(int TableNo, int RowNo, int FieldNo, String itemValue)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try{
			//値をセット
			getFieldItem(TableNo, RowNo, FieldNo).setValue(itemValue);
			
		}catch(Exception e){
			this.em.ThrowException(e, "フィールドの値、セットに失敗しました。itemNo="
					+ Integer.toString(FieldNo));
			
		}finally{
			
		}
		
	}
	/**
	 * フィールドの値をセットする
	 * @param TableID：テーブルID
	 * @param RowNo：ローインデックス
	 * @param FieldNo：フィールドインデックス
	 * @param itemValue：フィールドの値
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public void setFieldVale(String TableID, int RowNo, int FieldNo, String itemValue)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try{
			//値をセット
			getFieldItem(TableID, RowNo, FieldNo).setValue(itemValue);
			
		}catch(Exception e){
			this.em.ThrowException(e, "フィールドの値、セットに失敗しました。itemNo="
					+ Integer.toString(FieldNo));
			
		}finally{
			
		}
		
	}
	/**
	 * フィールドの値をセットする
	 * @param TableNo：テーブルインデックス
	 * @param RowID：ローID
	 * @param FieldNo：フィールドインデックス
	 * @param itemValue：フィールドの値
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public void setFieldVale(int TableNo, String RowID, int FieldNo, String itemValue)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try{
			//値をセット
			getFieldItem(TableNo, RowID, FieldNo).setValue(itemValue);
			
		}catch(Exception e){
			this.em.ThrowException(e, "フィールドの値、セットに失敗しました。itemNo="
					+ Integer.toString(FieldNo));
			
		}finally{
			
		}
		
	}
	/**
	 * フィールドの値をセットする
	 * @param TableID：テーブルID
	 * @param RowID：ローID
	 * @param FieldNo：フィールドインデックス
	 * @param itemValue：フィールドの値
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public void setFieldVale(String TableID, String RowID, int FieldNo, String itemValue)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try{
			//値をセット
			getFieldItem(TableID, RowID, FieldNo).setValue(itemValue);
			
		}catch(Exception e){
			this.em.ThrowException(e, "フィールドの値、セットに失敗しました。itemNo=" 
					+ Integer.toString(FieldNo));
			
		}finally{
			
		}
		
	}
	/**
	 * フィールドの値をセットする
	 * @param TableNo：テーブルインデックス
	 * @param RowNo：ローインデックス
	 * @param FieldName：フィールド名
	 * @param itemValue：フィールドの値
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public void setFieldVale(int TableNo, int RowNo, String FieldName, String itemValue)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try{
			try{
				//値をセット
				getFieldItem(TableNo, RowNo, FieldName).setValue(itemValue);
				
			}catch(Exception e){
				addFieldVale(TableNo, RowNo, FieldName, itemValue);
			}
			
		}catch(Exception e){
			this.em.ThrowException(e, "フィールドの値、セットに失敗しました。itemNo=" + FieldName);
			
		}finally{
			
		}
		
	}
	/**
	 * フィールドの値をセットする
	 * @param TableID：テーブルID
	 * @param RowNo：ローインデックス
	 * @param FieldName：フィールド名
	 * @param itemValue：フィールドの値
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public void setFieldVale(String TableID, int RowNo, String FieldName, String itemValue)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try{
			try{
				//値をセット
				getFieldItem(TableID, RowNo, FieldName).setValue(itemValue);
				
			}catch(Exception e){
				addFieldVale(TableID, RowNo, FieldName, itemValue);
			}
			
		}catch(Exception e){
			this.em.ThrowException(e, "フィールドの値、セットに失敗しました。itemNo=" + FieldName);
			
		}finally{
			
		}
		
	}
	/**
	 * フィールドの値をセットする
	 * @param TableNo：テーブルインデックス
	 * @param RowID：ローID
	 * @param FieldName：フィールド名
	 * @param itemValue：フィールドの値
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public void setFieldVale(int TableNo, String RowID, String FieldName, String itemValue)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try{
			//値をセット
			try{
				getTableItem(TableNo).getRowItem(RowID).getFieldItem(FieldName).setValue(itemValue);
				
			}catch(Exception e){
				addFieldVale(TableNo, RowID, FieldName, itemValue);		
			}
			
		}catch(Exception e){
			this.em.ThrowException(e, "フィールドの値、セットに失敗しました。itemNo=" + FieldName);
			
		}finally{
			
		}
		
	}
	/**
	 * フィールドの値をセットする
	 * @param TableID：テーブルID
	 * @param RowID：ローID
	 * @param FieldName：フィールド名
	 * @param itemValue：フィールドの値
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public void setFieldVale(String TableID, String RowID, String FieldName, String itemValue)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try{
			//値をセット
			try{
				getTableItem(TableID).getRowItem(RowID).getFieldItem(FieldName).setValue(itemValue);
				
			}catch(Exception e){
				addFieldVale(TableID, RowID, FieldName, itemValue);		
			}
			
		}catch(Exception e){
			this.em.ThrowException(e, "フィールドの値、セットに失敗しました。itemNo=" + FieldName);
			
		}finally{
			
		}
		
	}
	/**
	 * フィールドの値を追加する
	 * @param TableNo：テーブルインデックス
	 * @param RowNo：ローインデックス
	 * @param FieldName：フィールド名
	 * @param itemValue：フィールドの値
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public void addFieldVale(int TableNo, int RowNo, String FieldName, String itemValue)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try{
			RequestResponsTableBean TableBean = null;
			RequestResponsRowBean RowBean = null;
			RequestResponsFieldBean FieldBean = null;

			//値をセット
			TableBean = getTableItem(TableNo);
			if (TableBean == null){
				TableBean = addTableItem("table");
			}
			RowBean = TableBean.getRowItem(RowNo);
			if (RowBean == null){
				RowBean = TableBean.addRowItem("rec");
			}
			FieldBean = RowBean.getFieldItem(FieldName);
			if (FieldBean == null){
				FieldBean = RowBean.addFieldItem(FieldName);
			}
			
			FieldBean.setValue(itemValue);
			
		}catch(Exception e){
			this.em.ThrowException(e, "フィールドの値、追加に失敗しました。itemNo=" + FieldName);
			
		}finally{
			
		}
		
	}
	/**
	 * フィールドの値を追加する
	 * @param TableID：テーブルID
	 * @param RowNo：ローインデックス
	 * @param FieldName：フィールド名
	 * @param itemValue：フィールドの値
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public void addFieldVale(String TableID, int RowNo, String FieldName, String itemValue)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try{
			RequestResponsTableBean TableBean = null;
			RequestResponsRowBean RowBean = null;
			RequestResponsFieldBean FieldBean = null;

			//値をセット
			TableBean = getTableItem(TableID);
			if (TableBean == null){
				TableBean = addTableItem(TableID);
			}
			RowBean = TableBean.getRowItem(RowNo);
			if (RowBean == null){
				RowBean = TableBean.addRowItem("rec");
			}
			FieldBean = RowBean.getFieldItem(FieldName);
			if (FieldBean == null){
				FieldBean = RowBean.addFieldItem(FieldName);
			}
			FieldBean.setValue(itemValue);
			
		}catch(Exception e){
			this.em.ThrowException(e, "フィールドの値、追加に失敗しました。itemNo=" + FieldName);
			
		}finally{
			
		}
		
	}
	/**
	 * フィールドの値を追加する
	 * @param TableNo：テーブルインデックス
	 * @param RowID：ローID
	 * @param FieldName：フィールド名
	 * @param itemValue：フィールドの値
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public void addFieldVale(int TableNo, String RowID, String FieldName, String itemValue)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try{
			//値を追加
			RequestResponsTableBean TableBean = null;
			RequestResponsRowBean RowBean = null;
			RequestResponsFieldBean FieldBean = null;

			//値をセット
			TableBean = getTableItem(TableNo);
			if (TableBean == null){
				TableBean = addTableItem("table");
			}
			RowBean = TableBean.getRowItem(RowID);
			if (RowBean == null){
				RowBean = TableBean.addRowItem(RowID);
			}
			FieldBean = RowBean.getFieldItem(FieldName);
			if (FieldBean == null){
				FieldBean = RowBean.addFieldItem(FieldName);
			}
			FieldBean.setValue(itemValue);
			
		}catch(Exception e){
			this.em.ThrowException(e, "フィールドの値、追加に失敗しました。itemNo=" + FieldName);
			
		}finally{
			
		}
		
	}
	/**
	 * フィールドの値を追加する
	 * @param TableID：テーブルID
	 * @param RowID：ローID
	 * @param FieldName：フィールド名
	 * @param itemValue：フィールドの値
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public void addFieldVale(String TableID, String RowID, String FieldName, String itemValue)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try{
			//値を追加
			RequestResponsTableBean TableBean = null;
			RequestResponsRowBean RowBean = null;
			RequestResponsFieldBean FieldBean = null;

			//値をセット
			TableBean = getTableItem(TableID);
			if (TableBean == null){
				TableBean = addTableItem(TableID);
			}
			RowBean = TableBean.getRowItem(RowID);
			if (RowBean == null){
				RowBean = TableBean.addRowItem(RowID);
			}
			FieldBean = RowBean.getFieldItem(FieldName);
			if (FieldBean == null){
				FieldBean = RowBean.addFieldItem(FieldName);
			}
			FieldBean.setValue(itemValue);
			
		}catch(Exception e){
			this.em.ThrowException(e, "フィールドの値、追加に失敗しました。itemNo=" + FieldName);
			
		}finally{
			
		}
		
	}
	public int getCntTable() 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		int ret = -1;
		
		try{
			ret = GetCnt();
			
		}catch(Exception e){
			this.em.ThrowException(e, "");
			
		}finally{

		}
		return ret;
	
	}
	public int getCntRow(int TableNo) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		int ret = -1;
		
		try{
			ret = getTableItem(TableNo).GetCnt();
			
		}catch(Exception e){
			this.em.ThrowException(e, "");
			
		}finally{

		}
		return ret;
	
	}
	public int getCntRow(String TableID) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		int ret = -1;
		
		try{
			ret = getTableItem(TableID).GetCnt();
			
		}catch(Exception e){
			this.em.ThrowException(e, "");
			
		}finally{

		}
		return ret;
	
	}
	public int getCntField(int TableNo, int RowNo) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		int ret = -1;
		
		try{
			ret = getRowItem(TableNo, RowNo).GetCnt();
			
		}catch(Exception e){
			this.em.ThrowException(e, "");
			
		}finally{

		}
		return ret;
	
	}
	public int getCntField(String TableID, int RowNo) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		int ret = -1;
		
		try{
			ret = getRowItem(TableID, RowNo).GetCnt();
			
		}catch(Exception e){
			this.em.ThrowException(e, "");
			
		}finally{

		}
		return ret;
	
	}
	public int getCntField(int TableNo, String RowID) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		int ret = -1;
		
		try{
			ret = getRowItem(TableNo, RowID).GetCnt();
			
		}catch(Exception e){
			this.em.ThrowException(e, "");
			
		}finally{

		}
		return ret;
	
	}
	public int getCntField(String TableID, String RowID) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		int ret = -1;
		
		try{
			ret = getRowItem(TableID, RowID).GetCnt();
			
		}catch(Exception e){
			this.em.ThrowException(e, "");
			
		}finally{

		}
		return ret;
	
	}
	
	
}
