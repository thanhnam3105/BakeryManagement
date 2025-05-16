package jp.co.blueflag.shisaquick.srv.base;

import java.util.ArrayList;

import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;

/**
 * 
 * リクエスト/レスポンスBean
 *  : リクエスト/レスポンスのデータ保持で、最小単位のデータ管理を行う
 * @author TT.furuta
 * @since  2009/03/24
 */
public class RequestResponsBean extends ObjectBase {

	//ID
	protected String strID;				
	//用途に応じ、RequestResponsBean又は、RequestResponsRowを格納
	protected ArrayList<Object> arrItemList;
	
	/**
	 * コンストラクタ
	 */
	public RequestResponsBean() {
		super();
		
		//インスタンス生成
		arrItemList = new ArrayList<Object>();
	}
	
	/**
	 * リストのアイテム数を取得
	 * @return ItemListの格納数
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	public int GetCnt() throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		int ret = 0;
		
		try {
			ret = arrItemList.size();
		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {
			
		}
		
		//メンバarrItemListのアイテム数を返す
		return ret;
	}
	
	/**
	 * リストのアイテム名を取得
	 * @param itemNo : アイテムNo
	 * @return 指定されたItemListのクラス名
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	public String GetItemClassName(int itemNo) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		String ret = "";
		try {
			ret = arrItemList.get(itemNo).getClass().getName();
		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {
			
		}
		//アイテムリストのクラス名を返す
		return ret;
	}
	
	/**
	 * 項目名取得
	 * @param itemNo : アイテムNo
	 * @return 項目名
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	public String GetItemName(int itemNo) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		String strReturnNm = null;
		
		try {
			//Item番号より取得した対象がRequestResponsRowの場合
			if (GetItemClassName(itemNo).equals("jp.co.blueflag.shisaquick.srv.base.RequestResponsRow")){
				//RequestResponsRowに格納されている項目名を取得
				strReturnNm = ((RequestResponsRow)arrItemList.get(itemNo)).getName();
			}
		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {
			
		}
		
		return strReturnNm;
	}
	
	/**
	 * 項目設定値取得
	 * @param itemNo : アイテムNo
	 * @return 項目設定値
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	public String GetItemValue(int itemNo) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		String strReturnVal = null;

		try {
			
			if (GetItemClassName(itemNo).equals("jp.co.blueflag.shisaquick.srv.base.RequestResponsBean")){
				//RequestResponsBean
				//アイテムリストよりID取得
				strReturnVal = ((RequestResponsBean)arrItemList.get(itemNo)).getID();
				
			}else if(GetItemClassName(itemNo).equals("jp.co.blueflag.shisaquick.srv.base.RequestResponsRow")){
				//RequestResponsRow
				//アイテムリストより項目値を取得
				strReturnVal = ((RequestResponsRow)arrItemList.get(itemNo)).getValue().toString();

			}else if(GetItemClassName(itemNo).equals("jp.co.blueflag.shisaquick.srv.base.RequestResponsKindBean")){
				//RequestResponsKindBean
				//アイテムリストよりID取得
				strReturnVal = ((RequestResponsKindBean)arrItemList.get(itemNo)).getID();

			}else if(GetItemClassName(itemNo).equals("jp.co.blueflag.shisaquick.srv.base.RequestResponsTableBean")){
				//RequestResponsTablBean
				//アイテムリストよりID取得
				strReturnVal = ((RequestResponsTableBean)arrItemList.get(itemNo)).getID();

			}else if(GetItemClassName(itemNo).equals("jp.co.blueflag.shisaquick.srv.base.RequestResponsRowBean")){
				//RequestResponsRowBean
				//アイテムリストよりID取得
				strReturnVal = ((RequestResponsRowBean)arrItemList.get(itemNo)).getID();

			}else if(GetItemClassName(itemNo).equals("jp.co.blueflag.shisaquick.srv.base.RequestResponsFieldBean")){
				//RequestResponsFieldBean
				//アイテムリストより項目値を取得
				strReturnVal = ((RequestResponsFieldBean)arrItemList.get(itemNo)).getValue().toString();

			}

		} catch (Exception e) {
			this.em.ThrowException(e, "項目の値取得に失敗しました。itemNo=" + Integer.toString(itemNo));

		} finally {
			
		}
		return strReturnVal;

	}

	/**
	 * 項目設定値取得
	 * @param itemName : アイテム名
	 * @return 項目設定値
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	public String GetItemValue(String itemName) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		return GetItemValue(GetItemNo(itemName));
		
	}
	
	/**
	 * アイテム取得
	 * @param itemNo : アイテムNo
	 * @return オブジェクト
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	public Object GetItem(int itemNo) throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		Object ret = null;
		
		try {
			ret = arrItemList.get(itemNo);
			
		} catch(IndexOutOfBoundsException ex){
			
		} catch (Exception e) {
			this.em.ThrowException(e, "");
			
		} finally {
			
		}
		//リストのアイテム番号を指定して　オブジェクトを返す
		return ret;
		
	}
	/**
	 * リストアイテムをセットする
	 * @param obj：リストアイテム
	 * @param itemNo：リストインデックス
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public void SetItem(Object obj, int itemNo) throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try {
			arrItemList.set(itemNo, obj);

		} catch (Exception e) {
			this.em.ThrowException(e, "");

		} finally {
			
		}

	}
	/**
	 * アイテムリストにｵﾌﾞｼﾞｪｸﾄを追加
	 * @param obj：ｵﾌﾞｼﾞｪｸﾄ
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public void AddItem(Object obj) throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try {
			arrItemList.add(obj);

		} catch (Exception e) {
			this.em.ThrowException(e, "ｵﾌﾞｼﾞｪｸﾄの追加に失敗しました。");

		} finally {
			
		}
		
	}
	/**
	 * アイテムリストにｵﾌﾞｼﾞｪｸﾄを追加
	 * @param obj：ｵﾌﾞｼﾞｪｸﾄ
	 * @param ix：挿入位置インデックス
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public void AddItem(Object obj,int ix) throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try {
			arrItemList.add(ix, obj);

		} catch (Exception e) {
			this.em.ThrowException(e, "ｵﾌﾞｼﾞｪｸﾄの追加に失敗しました。");

		} finally {
			
		}
		
	}
	
	/**
	 * アイテムNo
	 * @param itemName : ID/項目名
	 * @return アイテム番号
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	public int GetItemNo(String itemName) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		int iReturnNo = -1;
		String strId = "";

		try {
			//Itemリスト数ループ
			for (int i =0;i < arrItemList.size();i++){
				
				if (GetItemClassName(i).equals("jp.co.blueflag.shisaquick.srv.base.RequestResponsBean")){
					//RequestResponsBean
					//アイテムリストよりID取得
					strId = ((RequestResponsBean)arrItemList.get(i)).getID();
					
				}else if(GetItemClassName(i).equals("jp.co.blueflag.shisaquick.srv.base.RequestResponsRow")){
					//RequestResponsRow
					//アイテムリストより項目名を取得
					strId = ((RequestResponsRow)arrItemList.get(i)).getName();

				}else if(GetItemClassName(i).equals("jp.co.blueflag.shisaquick.srv.base.RequestResponsKindBean")){
					//RequestResponsKindBean
					//アイテムリストよりID取得
					strId = ((RequestResponsKindBean)arrItemList.get(i)).getID();

				}else if(GetItemClassName(i).equals("jp.co.blueflag.shisaquick.srv.base.RequestResponsTableBean")){
					//RequestResponsTablBean
					//アイテムリストよりID取得
					strId = ((RequestResponsTableBean)arrItemList.get(i)).getID();

				}else if(GetItemClassName(i).equals("jp.co.blueflag.shisaquick.srv.base.RequestResponsRowBean")){
					//RequestResponsRowBean
					//アイテムリストよりID取得
					strId = ((RequestResponsRowBean)arrItemList.get(i)).getID();

				}else if(GetItemClassName(i).equals("jp.co.blueflag.shisaquick.srv.base.RequestResponsFieldBean")){
					//RequestResponsFieldBean
					//アイテムリストより項目名を取得
					strId = ((RequestResponsFieldBean)arrItemList.get(i)).getName();

				}
				//アイテム番号とIDが一致した場合、対象アイテム番号設定
				if (itemName.equals(strId)){
					iReturnNo = i;
					break;
				}

			}
		} catch (Exception e) {

			this.em.ThrowException(e, "アイテム番号の取得に失敗しました。itemName=" + itemName);

		} finally {
			
		}
		return iReturnNo;

	}


	/**
	 * ID ゲッター
	 * @return ID : IDの値を返す
	 */
	public String getID() {
		
		return strID;
	}
	/**
	 * ID セッター
	 * @param _ID : IDの値を格納する
	 */
	public void setID(String _strID) {
		this.strID = _strID;
		
	}

	/**
	 * アイテムリスト ゲッター
	 * @return itemList : アイテムリストの値を返す
	 */
	public ArrayList<Object> getItemList() {
		return arrItemList;
	}
	/**
	 * アイテムリスト セッター
	 * @param _itemList : アイテムリストの値を格納する
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	public void setItemList(ArrayList<Object> _itemList) {
		arrItemList = _itemList;

	}
	/**
	 * データリストを開放する（Nullセット）
	 */
	public void RemoveList(){
		
		RemoveList(arrItemList);
		
	}

	/**
	 * RequestResponsBeanのアイテムリストをクリアする
	 * @param ItemList
	 */
	private void RemoveList(ArrayList<Object> ItemList){
		
		if (ItemList == null){
			return;
		}
		
		for(int ix = ItemList.size()-1; ix < -1; ix-- ){

			if(ItemList.get(ix).getClass().getName().equals("jp.co.blueflag.shisaquick.srv.base.RequestResponsRow")){
				//RequestResponsRow
			}else if(ItemList.get(ix).getClass().getName().equals("jp.co.blueflag.shisaquick.srv.base.RequestResponsFieldBean")){
				//RequestResponsFieldBean
			}else{
				RemoveList(((RequestResponsBean)ItemList.get(ix)).getItemList());
			}
			ItemList.remove(ix);
			
		}
		//リストクリア
		ItemList = null;
		
	}
}
