package jp.co.blueflag.shisaquick.srv.base;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

/**
 * 
 * リクエストデータ保持
 *  : XMLIDごとの各リクエストデータ保持クラスを包括
 *
 */
public class RequestData extends RequestResponsBean {
	
	/**
	 * コンストラクタ
	 * @param inXML  : XMLのオブジェクト
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	public RequestData(Document inXML) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {
	
		super();
		
		try{
			if (null != inXML){
				//XMLデータ設定呼出
				CreateXMLData(inXML);
			}
			
		} catch (Exception e) {
			em.ThrowException(e, "XMLデータ設定呼出に失敗しました。");

		} finally {
			
		}
		
	}

	/**
	 * XMLデータ設定
	 * @param inXML : XMLオブジェクト 
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private void CreateXMLData(Document inXML) throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		try {
			
			inXML.getDocumentElement().getNodeName();
			
			//XMLID設定
			super.setID(inXML.getDocumentElement().getNodeName());
			
			//子ノード数取得
			int iChildLength = inXML.getDocumentElement().getChildNodes().getLength();
		
			//子ノード数ループ
			for(int i=0;i<iChildLength;i++){
				//子ノードItem取得
				Node strChildItem = inXML.getDocumentElement().getChildNodes().item(i);
	
				//機能IDデータ設定呼出
				CreateKinoData(strChildItem);
			}
			
		} catch (Exception e){
			
			em.ThrowException(e, "XMLIDデータ設定に失敗しました。");
			
		} finally {
			
		}
	}
	
	/**
	 * 機能IDデータ設定
	 * @param strChildItem : 自ノードItem 
	 * @param reqRespBean  : XMLIDBean
	 * @return テーブル名
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private void CreateKinoData(Node strChildItem) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
			
		try {
			//改行以外の場合要素追加
	    	if(strChildItem.getNodeName() != "#text"){
	    		
	    		//リクエストレスポンスBean生成
	    		RequestResponsKindBean reqBean = new RequestResponsKindBean();
	    		//ID設定
	    		reqBean.setID(strChildItem.getNodeName());
	    		
	    		//属性追加
	    		super.getItemList().add(reqBean);
	    		
	    		for (int i=0; i < strChildItem.getChildNodes().getLength();i++){
	    			//子ノードItem取得
	    			Node strChildItem2 = strChildItem.getChildNodes().item(i);
					//テーブルデータ設定呼出
	    			CreateTableData(strChildItem2, reqBean);
	
	    		}
	    	}
		} catch (Exception e) {
			
			em.ThrowException(e, "機能IDデータ設定に失敗しました。");
			
		} finally {
			
		}
	}

	/**
	 * テーブルデータ設定
	 * @param strChildItem : 自ノードItem 
	 * @param reqRespBean  : テーブルインデックス
	 * @return テーブル名
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private void CreateTableData(Node strChildItem, RequestResponsKindBean requestResponsBean) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		try {
			//改行以外の場合要素追加
	    	if(strChildItem.getNodeName() != "#text"){
	    		
	    		//リクエストレスポンスBean生成
	    		RequestResponsTableBean reqBean = new RequestResponsTableBean();
	    		//ID設定
	    		reqBean.setID(strChildItem.getNodeName());
	    		
	    		//属性追加
	    		requestResponsBean.getItemList().add(reqBean);
	    		
	    		for (int i=0; i < strChildItem.getChildNodes().getLength();i++){
	    			//子ノードItem取得
	    			Node strChildItem2 = strChildItem.getChildNodes().item(i);
					//行データ設定呼出
	    			CreateRowData(strChildItem2, reqBean);
	
	    		}
	    	}
		} catch (Exception e) {
			
			em.ThrowException(e, "テーブルデータ設定に失敗しました。");
			
		} finally {
			
		}
	}

	/**
	 * 行データ設定
	 * @param strChildItem : 自ノードItem 
	 * @param reqRespBean  : テーブルインデックス
	 * @return テーブル名
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private void CreateRowData(Node strChildItem, RequestResponsTableBean requestResponsBean) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		try {
			
			//改行以外の場合要素追加
	    	if(strChildItem.getNodeName() != "#text"){
	    		
	    		//リクエストレスポンスBean生成
	    		RequestResponsRowBean reqBean = new RequestResponsRowBean();
	    		//ID設定
	    		reqBean.setID(strChildItem.getNodeName());
	    		
	    		//属性追加
	    		requestResponsBean.getItemList().add(reqBean);
	    		
	            for (int i=0; i < strChildItem.getAttributes().getLength(); i++) {
	                Node attr = strChildItem.getAttributes().item(i);  // 属性ノード

	                //改行コードに置き換える
	                attr.setNodeValue(attr.getNodeValue().replaceAll("\\\\n", "\n"));
	                
	                RequestResponsRow reqRow = new RequestResponsFieldBean(attr.getNodeName(), attr.getNodeValue());
	                	                
	                //項目設定
	                reqBean.getItemList().add(reqRow);
	            }
	
	    	}
		} catch (Exception e) {
			
			em.ThrowException(e, "行データ設定に失敗しました。");
			
		} finally {
			
		}
	}

	/**
	 * XmlID取得
	 * @return : XmlID
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	public String GetXMLID() {
		return super.getID();
	}
	
	/**
	 * 機能ID取得 
	 * @param KindItemNo : アイテム番号 
	 * @return 機能ID
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	public String GetKindID(int KindItemNo) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		String strKinoId = null;
		
		try {
		
			//要素が空以外の場合
			if (null != super.getItemList().get(KindItemNo)){
				
				//子ノード機能Bean取得	
				RequestResponsBean reqKinoBean = (RequestResponsBean) super.getItemList().get(KindItemNo);
				
				//機能ID取得
				strKinoId = reqKinoBean.getID();
			}

		} catch (Exception e) {
			em.ThrowException(e, "機能IDの取得に失敗しました。");
		} finally {
			
		}		
		return strKinoId;
	}
	
	/**
	 * 機能IDインデックス取得 
	 * @param KindId : 機能ID 
	 * @return 機能IDインデックス
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	public int GetKindIDIndex(String KindId) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		int iRtnKinoIdIndex = -1;
		
		try {
			//機能ID数の取得
			int iKinoIdCnt = GetKindCnt();
			
			//機能IDのインデックスを検索
			for (int i = 0; i < iKinoIdCnt; i++){
				//検索値の機能IDと同じ場合
				if (KindId == GetKindID(i)) {
					//機能IDインデックス取得
					iRtnKinoIdIndex = i;
					break;
				}
			}

		} catch (Exception e) {
			em.ThrowException(e, "機能IDインデックスの取得に失敗しました。");
		} finally {
			
		}		
		return iRtnKinoIdIndex;
	}

	/**
	 * テーブル名取得
	 * @param KindItemNo : 機能IDインデックス 
	 * @param TableItemNo : テーブルインデックス
	 * @return テーブル名
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	public String GetTableName(int KindItemNo, int TableItemNo) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		String strTableNm = null;
		
		try {
			//要素が空以外の場合
			if (null != super.getItemList().get(KindItemNo)){
				
				//子ノード機能Bean取得
				RequestResponsBean reqKinoBean = (RequestResponsBean) super.getItemList().get(KindItemNo);
				
				//要素が空以外の場合
				if (null != reqKinoBean.getItemList().get(TableItemNo)){
	
					//子ノードテーブルBean取得
					RequestResponsBean reqTableBean = (RequestResponsBean) reqKinoBean.getItemList().get(TableItemNo);
					
					//テーブル名取得
					strTableNm = reqTableBean.getID();
				}
			}
		} catch (Exception e) {
			
			em.ThrowException(e, "テーブル名の取得に失敗しました。");
			
		} finally {
			
		}		
		
		return strTableNm;
	}
	
	/**
	 * テーブル名取得
	 * @param KindId : 機能ID
	 * @param TableItemNo : テーブルインデックス
	 * @return テーブル名
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	public String GetTableName(String KindId, int TableItemNo) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		String strTableNm = null;
		
		try {
			//機能IDのインデックスを取得
			int iKindItemNo = GetKindIDIndex(KindId);
		
			//機能IDインデックスが正常に取得できた場合
			if (iKindItemNo != -1) {
				//テーブル名取得の取得
				strTableNm = GetTableName(iKindItemNo, TableItemNo);
			}

		} catch (Exception e) {
			
			em.ThrowException(e, "テーブル名の取得に失敗しました。");
			
		} finally {
			
		}		
		
		return strTableNm;
	}

	/**
	 * テーブルインデックス取得 
	 * @param KindItemNo : 機能IDインデックス
	 * @param TableNm : テーブル名
	 * @return テーブルインデックス
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	public int GetTableIndex(int KindItemNo, String TableNm) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		int iRtnTableIndex = -1;
		
		try {
			//テーブル数の取得
			int iTableCnt = GetTableCnt(KindItemNo);
			
			//テーブル名のインデックスを検索
			for (int i = 0; i < iTableCnt; i++){
				//検索値のテーブル名と同じ場合
				if (TableNm == GetTableName(KindItemNo, i)) {
					//テーブルインデックス取得
					iRtnTableIndex = i;
					break;
				}
			}

		} catch (Exception e) {
			em.ThrowException(e, "テーブルインデックスの取得に失敗しました。");
		} finally {
			
		}		
		return iRtnTableIndex;
	}

	/**
	 * テーブルインデックス取得 
	 * @param KindId : 機能ID
	 * @param TableNm : テーブル名
	 * @return テーブルインデックス
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	public int GetTableIndex(String KindId, String TableNm) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		int iRtnTableIndex = -1;
		
		try {
			//機能IDのインデックスを取得
			int iKindItemNo = GetKindIDIndex(KindId);
		
			//機能IDインデックスが正常に取得できた場合
			if (iKindItemNo != -1) {
				//テーブルインデックスの取得
				iRtnTableIndex = GetTableIndex(iKindItemNo, TableNm);
			}

		} catch (Exception e) {
			em.ThrowException(e, "テーブルインデックスの取得に失敗しました。");
		} finally {
			
		}		
		return iRtnTableIndex;
	}

	/**
	 * 機能IDの項目名取得
	 * @param KindItemNo : 機能IDインデックス
	 * @param TableItemNo : テーブルインデックス
	 * @param ValueNo : 項目インデックス
	 * @return 機能IDの項目名
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	public String GetValueName(int KindItemNo, int TableItemNo, int ValueNo) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		String strValueNm = null;
		
		try {
			//要素が空以外の場合
			if (null != super.getItemList().get(KindItemNo)){
				
				//子ノード機能Bean取得
				RequestResponsBean reqKinoBean = (RequestResponsBean) super.getItemList().get(KindItemNo);
				
				//要素が空以外の場合
				if (null != reqKinoBean.getItemList().get(TableItemNo)){
	
					//子ノードテーブルBean取得
					RequestResponsBean reqTableBean = (RequestResponsBean) reqKinoBean.getItemList().get(TableItemNo);
	
					//要素が空以外の場合
					if (null != reqTableBean.getItemList().get(0)){
	
						//子ノード行Bean取得
						RequestResponsBean reqRowBean = (RequestResponsBean) reqTableBean.getItemList().get(0);
	
						if (null != reqRowBean.getItemList().get(ValueNo)){
	
							//子ノード項目Bean取得
							RequestResponsRow reqRow = (RequestResponsRow) reqRowBean.getItemList().get(ValueNo);
	
							//項目名取得
							strValueNm = reqRow.getName();
						}
					}
				}
			}

		} catch (Exception e) {
			
			em.ThrowException(e, "機能IDの項目名取得に失敗しました。");
			
		} finally {
			
		}		

		return strValueNm;
	
	}
	
	/**
	 * 機能IDの項目名取得
	 * @param KindId : 機能ID
	 * @param TableItemNo : テーブルインデックス
	 * @param ValueNo : 項目インデックス
	 * @return 機能IDの項目名
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	public String GetValueName(String KindId, int TableItemNo, int ValueNo) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		String strValueNm = null;
		
		try {
			//機能IDのインデックスを取得
			int iKindItemNo = GetKindIDIndex(KindId);
		
			//機能IDインデックスが正常に取得できた場合
			if (iKindItemNo != -1) {
				//機能IDの項目名を取得
				strValueNm = GetValueName(iKindItemNo, TableItemNo, ValueNo);
			}

		} catch (Exception e) {
			
			em.ThrowException(e, "機能IDの項目名取得に失敗しました。");
			
		} finally {
			
		}		

		return strValueNm;
	
	}

	/**
	 * 機能IDの項目名取得
	 * @param KindId : 機能ID
	 * @param TableItemNo : テーブルインデックス
	 * @param ValueNo : 項目インデックス
	 * @return 機能IDの項目名
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	public String GetValueName(String KindId, String TableNm, int ValueNo) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		String strValueNm = null;
		
		try {
			//機能IDのインデックスを取得
			int iKindItemNo = GetKindIDIndex(KindId);
		
			//機能IDインデックスが正常に取得できた場合
			if (iKindItemNo != -1) {
				//テーブルのインデックスを取得
				int iTableItemNo = GetTableIndex(iKindItemNo, TableNm);
			
				//テーブルインデックスが正常に取得できた場合
				if (iTableItemNo != -1) {
					//機能IDの項目名を取得
					strValueNm = GetValueName(iKindItemNo, iTableItemNo, ValueNo);
				}
			}

		} catch (Exception e) {
			
			em.ThrowException(e, "機能IDの項目名取得に失敗しました。");
			
		} finally {
			
		}		

		return strValueNm;
	
	}

	/**
	 * 機能IDの項目名取得
	 * @param KindItemNo : 機能IDインデックス
	 * @param TableItemNo : テーブルインデックス
	 * @param ValueNo : 項目インデックス
	 * @return 機能IDの項目名
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	public String GetValueName(int KindItemNo, String TableNm, int ValueNo) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		String strValueNm = null;
		
		try {
			//テーブルのインデックスを取得
			int iTableItemNo = GetTableIndex(KindItemNo, TableNm);
		
			//テーブルインデックスが正常に取得できた場合
			if (iTableItemNo != -1) {
				//機能IDの項目名を取得
				strValueNm = GetValueName(KindItemNo, iTableItemNo, ValueNo);
			}

		} catch (Exception e) {
			
			em.ThrowException(e, "機能IDの項目名取得に失敗しました。");
			
		} finally {
			
		}		

		return strValueNm;
	
	}

	/**
	 * 機能IDの項目インデックス取得
	 * @param KindItemNo : 機能IDインデックス
	 * @param TableItemNo : テーブルインデックス
	 * @param ValueNm : 機能IDの項目名
	 * @return 項目インデックス
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	public int GetValueIndex(int KindItemNo, int TableItemNo, String ValueNm) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		int iRtnValueIndex = -1;
		
		try {
			//項目数の取得
			int iValueCnt = GetValueCnt(KindItemNo, TableItemNo);
			
			//項目名のインデックスを検索
			for (int i = 0; i < iValueCnt; i++){
				//検索値の項目名と同じ場合
				if (ValueNm == GetValueName(KindItemNo, TableItemNo, i)) {
					//項目インデックス取得
					iRtnValueIndex = i;
					break;
				}
			}

		} catch (Exception e) {
			
			em.ThrowException(e, "機能IDの項目インデックス取得に失敗しました。");
			
		} finally {
			
		}		

		return iRtnValueIndex;
	
	}

	/**
	 * 機能IDの項目インデックス取得
	 * @param KindId : 機能ID
	 * @param TableItemNo : テーブルインデックス
	 * @param ValueNm : 機能IDの項目名
	 * @return 項目インデックス
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	public int GetValueIndex(String KindId, int TableItemNo, String ValueNm) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		int iRtnValueIndex = -1;
		
		try {
			//機能IDのインデックスを取得
			int iKindItemNo = GetKindIDIndex(KindId);
		
			//機能IDインデックスが正常に取得できた場合
			if (iKindItemNo != -1) {
				//機能IDの項目インデックスを取得の取得
				iRtnValueIndex = GetValueIndex(iKindItemNo, TableItemNo, ValueNm);
			}

		} catch (Exception e) {
			
			em.ThrowException(e, "機能IDの項目インデックス取得に失敗しました。");
			
		} finally {
			
		}		

		return iRtnValueIndex;
	
	}

	/**
	 * 機能IDの項目インデックス取得
	 * @param KindId : 機能ID
	 * @param TableNm : テーブル名
	 * @param ValueNm : 機能IDの項目名
	 * @return 項目インデックス
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	public int GetValueIndex(String KindId, String TableNm, String ValueNm) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		int iRtnValueIndex = -1;
		
		try {
			//機能IDのインデックスを取得
			int iKindItemNo = GetKindIDIndex(KindId);
		
			//機能IDインデックスが正常に取得できた場合
			if (iKindItemNo != -1) {
				//テーブルのインデックスを取得
				int iTableItemNo = GetTableIndex(iKindItemNo, TableNm);
			
				//テーブルインデックスが正常に取得できた場合
				if (iTableItemNo != -1) {
					//機能IDの項目インデックスを取得の取得
					iRtnValueIndex = GetValueIndex(iKindItemNo, iTableItemNo, ValueNm);
				}
			}

		} catch (Exception e) {
			
			em.ThrowException(e, "機能IDの項目インデックス取得に失敗しました。");
			
		} finally {
			
		}		

		return iRtnValueIndex;
	
	}

	/**
	 * 機能IDの項目インデックス取得
	 * @param KindItemNo : 機能IDインデックス
	 * @param TableNm : テーブル名
	 * @param ValueNm : 機能IDの項目名
	 * @return 項目インデックス
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	public int GetValueIndex(int KindItemNo, String TableNm, String ValueNm) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		int iRtnValueIndex = -1;
		
		try {
			//テーブルのインデックスを取得
			int iTableItemNo = GetTableIndex(KindItemNo, TableNm);
		
			//テーブルインデックスが正常に取得できた場合
			if (iTableItemNo != -1) {
				//機能IDの項目インデックスを取得の取得
				iRtnValueIndex = GetValueIndex(KindItemNo, iTableItemNo, ValueNm);
			}

		} catch (Exception e) {
			
			em.ThrowException(e, "機能IDの項目インデックス取得に失敗しました。");
			
		} finally {
			
		}		

		return iRtnValueIndex;
	
	}

	/**
	 * 機能ID数取得
	 * @return 機能IDの数 
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	public int GetKindCnt() throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		int ret = 0;
		try {
			
			if (null != super.getItemList()){
				ret = super.getItemList().size();
			}
			 
		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {
			
		}
		//機能ID数返却
		return ret;
	}
	
	/**
	 * テーブル数取得
	 * @param KindItemNo : 機能IDインデックス
	 * @return テーブル数
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	public int GetTableCnt(int KindItemNo) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		int iRtnTableCount = 0;
		
		try {
			//要素が空以外の場合
			if (null != super.getItemList().get(KindItemNo)){
				//テーブル数設定
				iRtnTableCount = ((RequestResponsBean) super.getItemList().get(KindItemNo)).getItemList().size(); 
			}

		} catch (Exception e) {
			
			em.ThrowException(e, "テーブル数取得に失敗しました。");
			
		} finally {
			
		}		

		return iRtnTableCount;
	}
	
	/**
	 * テーブル数取得
	 * @param KindId : 機能ID
	 * @return テーブル数
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	public int GetTableCnt(String KindId) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		int iRtnTableCount = 0;
		
		try {
			//機能IDのインデックスを取得
			int iKindItemNo = GetKindIDIndex(KindId);
		
			//機能IDインデックスが正常に取得できた場合
			if (iKindItemNo != -1) {
				//テーブル数を取得
				iRtnTableCount = GetTableCnt(iKindItemNo);
			}

		} catch (Exception e) {
			
			em.ThrowException(e, "テーブル数取得に失敗しました。");
			
		} finally {
			
		}		

		return iRtnTableCount;
	}
	
  /**
    * レコード数の取得
    * @param KindItemo : 機能IDインデックス
    * @param TableItemNo : テーブルインデックス
    * @return レコード数
	* @throws ExceptionWaning 
	* @throws ExceptionUser 
	* @throws ExceptionSystem 
    */
	public int GetRecCnt(int KindItemNo, int TableItemNo) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		int iRtnRecCount = 0;
		try {
			//要素が空以外の場合
			if (null != super.getItemList().get(KindItemNo)){
				
				//子ノード機能Bean取得
				RequestResponsBean reqKinoBean = (RequestResponsBean) super.getItemList().get(KindItemNo);
	
				if (null != reqKinoBean.getItemList().get(TableItemNo)){
					//レコード数設定
					iRtnRecCount = ((RequestResponsBean) reqKinoBean.getItemList().get(TableItemNo)).getItemList().size(); 				
				}
			}

		} catch (Exception e) {
			
			em.ThrowException(e, "レコード数の取得に失敗しました。");
			
		} finally {
			
		}		

		return iRtnRecCount;
	}
	
	/**
	 * レコード数の取得
	 * @param KindId : 機能ID
	 * @param TableItemNo : テーブルインデックス
	 * @return レコード数
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	public int GetRecCnt(String KindId, int TableItemNo) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		int iRtnRecCount = 0;
		
		try {
			//機能IDのインデックスを取得
			int iKindItemNo = GetKindIDIndex(KindId);
		
			//機能IDインデックスが正常に取得できた場合
			if (iKindItemNo != -1) {
				//レコード数を取得
				iRtnRecCount = GetRecCnt(iKindItemNo, TableItemNo);
			}

		} catch (Exception e) {
			
			em.ThrowException(e, "レコード数の取得に失敗しました。");
			
		} finally {
			
		}		

		return iRtnRecCount;
	}
	
	/**
	 * レコード数の取得
	 * @param KindId : 機能ID
	 * @param TableNm : テーブル名
	 * @return レコード数
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	public int GetRecCnt(String KindId, String TableNm) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		int iRtnRecCount = 0;
		
		try {
			//機能IDのインデックスを取得
			int iKindItemNo = GetKindIDIndex(KindId);
		
			//機能IDインデックスが正常に取得できた場合
			if (iKindItemNo != -1) {
				//テーブルのインデックスを取得
				int iTableItemNo = GetTableIndex(iKindItemNo, TableNm);
			
				//テーブルインデックスが正常に取得できた場合
				if (iTableItemNo != -1) {
					//レコード数を取得
					iRtnRecCount = GetRecCnt(iKindItemNo, iTableItemNo);
				}
			}

		} catch (Exception e) {
			
			em.ThrowException(e, "レコード数の取得に失敗しました。");
			
		} finally {
			
		}		

		return iRtnRecCount;
	}
	
	/**
	 * レコード数の取得
	 * @param KindItemNo : 機能IDインデックス
	 * @param TableNm : テーブル名
	 * @return レコード数
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	public int GetRecCnt(int KindItemNo, String TableNm) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		int iRtnRecCount = 0;
		
		try {
			//テーブルのインデックスを取得
			int iTableItemNo = GetTableIndex(KindItemNo, TableNm);
		
			//テーブルインデックスが正常に取得できた場合
			if (iTableItemNo != -1) {
				//レコード数を取得
				iRtnRecCount = GetRecCnt(KindItemNo, iTableItemNo);
			}

		} catch (Exception e) {
			
			em.ThrowException(e, "レコード数の取得に失敗しました。");
			
		} finally {
			
		}		

		return iRtnRecCount;
	}
	
	/**
	 * 項目数
     * @param KindItemo : 機能IDインデックス
     * @param TableItemNo : テーブルインデックス
	 * @return 項目数
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	public int GetValueCnt(int KindItemNo, int TableItemNo) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		int iRtnValCount = 0;
		
		try {
			//要素が空以外の場合
			if (null != super.getItemList().get(KindItemNo)){
				
				//子ノード機能Bean取得
				RequestResponsBean reqKinoBean = (RequestResponsBean) super.getItemList().get(KindItemNo);
	
				if (null != reqKinoBean.getItemList().get(TableItemNo)){
					
					//子ノードテーブルBean取得
					RequestResponsBean reqTableBean = (RequestResponsBean) reqKinoBean.getItemList().get(TableItemNo);
					
					if (null != reqTableBean.getItemList().get(0)){
						
						//項目数設定
						iRtnValCount = ((RequestResponsBean) reqTableBean.getItemList().get(0)).getItemList().size();
					}
				}
			}

		} catch (Exception e) {
			
			em.ThrowException(e, "項目数の取得に失敗しました。");
			
		} finally {
			
		}		

		return iRtnValCount;
	}
	
	/**
	 * 項目数
     * @param KindItemo : 機能IDインデックス
     * @param TableItemNo : テーブルインデックス
	 * @return 項目数
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	public int GetValueCnt(String KindId, int TableItemNo) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		int iRtnValCount = 0;
		
		try {
			//機能IDのインデックスを取得
			int iKindItemNo = GetKindIDIndex(KindId);
		
			//機能IDインデックスが正常に取得できた場合
			if (iKindItemNo != -1) {
				//項目数を取得
				iRtnValCount = GetValueCnt(iKindItemNo, TableItemNo);
			}

		} catch (Exception e) {
			
			em.ThrowException(e, "項目数の取得に失敗しました。");
			
		} finally {
			
		}		

		return iRtnValCount;
	}
	
	/**
	 * 項目数
     * @param KindId : 機能ID
     * @param TableNm : テーブル名
	 * @return 項目数
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	public int GetValueCnt(String KindId, String TableNm) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		int iRtnValCount = 0;
		
		try {
			//機能IDのインデックスを取得
			int iKindItemNo = GetKindIDIndex(KindId);
		
			//機能IDインデックスが正常に取得できた場合
			if (iKindItemNo != -1) {
				//テーブルのインデックスを取得
				int iTableItemNo = GetTableIndex(iKindItemNo, TableNm);
			
				//テーブルインデックスが正常に取得できた場合
				if (iTableItemNo != -1) {
					//項目数を取得
					iRtnValCount = GetValueCnt(iKindItemNo, iTableItemNo);
				}
			}

		} catch (Exception e) {
			
			em.ThrowException(e, "項目数の取得に失敗しました。");
			
		} finally {
			
		}		

		return iRtnValCount;
	}
	
	/**
	 * 項目数
     * @param KindItemo : 機能IDインデックス
     * @param TableNm : テーブル名
	 * @return 項目数
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	public int GetValueCnt(int KindItemNo, String TableNm) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		int iRtnValCount = 0;
		
		try {
			//テーブルのインデックスを取得
			int iTableItemNo = GetTableIndex(KindItemNo, TableNm);
		
			//テーブルインデックスが正常に取得できた場合
			if (iTableItemNo != -1) {
				//項目数を取得
				iRtnValCount = GetValueCnt(KindItemNo, iTableItemNo);
			}

		} catch (Exception e) {
			
			em.ThrowException(e, "項目数の取得に失敗しました。");
			
		} finally {
			
		}		

		return iRtnValCount;
	}
	
	/**
	 * 機能IDの項目値
     * @param KindItemo : 機能IDインデックス
     * @param TableItemNo : テーブルインデックス
     * @param RecItemNo : レコードインデックス
     * @param ValueItemNo : 項目インデックス
	 * @return 機能IDの項目値
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	public String GetValue(int KindItemNo, int TableItemNo, int RecItemNo, int ValueNo) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		String strValue = null;
		
		try {
			//要素が空以外の場合
			if (null != super.getItemList().get(KindItemNo)){
				
				//子ノード機能Bean取得
				RequestResponsBean reqKinoBean = (RequestResponsBean) super.getItemList().get(KindItemNo);
				
				//要素が空以外の場合
				if (null != reqKinoBean.getItemList().get(TableItemNo)){
	
					//子ノードテーブルBean取得
					RequestResponsBean reqTableBean = (RequestResponsBean) reqKinoBean.getItemList().get(TableItemNo);
	
					//要素が空以外の場合
					if (null != reqTableBean.getItemList().get(RecItemNo)){
	
						//子ノード行Bean取得
						RequestResponsBean reqRowBean = (RequestResponsBean) reqTableBean.getItemList().get(RecItemNo);
	
						if (null != reqRowBean.getItemList().get(ValueNo)){
	
							//子ノード項目Bean取得
							RequestResponsRow reqRow = (RequestResponsRow) reqRowBean.getItemList().get(ValueNo);
	
							//項目値取得
							strValue = reqRow.getValue();
						}
					}
				}
			}

		} catch (Exception e) {
			
			em.ThrowException(e, "機能IDの項目値の取得に失敗しました。");
			
		} finally {
			
		}		

		return strValue;
	}
	
	/**
	 * 機能IDの項目値
     * @param KindId : 機能ID
     * @param TableItemNo : テーブルインデックス
     * @param RecItemNo : レコードインデックス
     * @param ValueItemNo : 項目インデックス
	 * @return 機能IDの項目値
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	public String GetValue(String KindId, int TableItemNo, int RecItemNo, int ValueItemNo) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		String strValue = null;
		
		try {
			//機能IDのインデックスを取得
			int iKindItemNo = GetKindIDIndex(KindId);
		
			//機能IDインデックスが正常に取得できた場合
			if (iKindItemNo != -1) {
				//機能IDの項目値を取得
				strValue = GetValue(iKindItemNo, TableItemNo, RecItemNo, ValueItemNo);
			}

		} catch (Exception e) {
			
			em.ThrowException(e, "機能IDの項目値の取得に失敗しました。");
			
		} finally {
			
		}		

		return strValue;
	}

	/**
	 * 機能IDの項目値
     * @param KindId : 機能ID
     * @param TableNm : テーブル名
     * @param RecItemNo : レコードインデックス
     * @param ValueItemNo : 項目インデックス
	 * @return 機能IDの項目値
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	public String GetValue(String KindId, String TableNm, int RecItemNo, int ValueItemNo) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		String strValue = null;
		
		try {
			//機能IDのインデックスを取得
			int iKindItemNo = GetKindIDIndex(KindId);
		
			//機能IDインデックスが正常に取得できた場合
			if (iKindItemNo != -1) {
				//テーブルのインデックスを取得
				int iTableItemNo = GetTableIndex(iKindItemNo, TableNm);
			
				//テーブルインデックスが正常に取得できた場合
				if (iTableItemNo != -1) {
					//機能IDの項目値を取得
					strValue = GetValue(iKindItemNo, iTableItemNo, RecItemNo, ValueItemNo);
				}
			}

		} catch (Exception e) {
			
			em.ThrowException(e, "機能IDの項目値の取得に失敗しました。");
			
		} finally {
			
		}		

		return strValue;
	}

	/**
	 * 機能IDの項目値
     * @param KindId : 機能ID
     * @param TableNm : テーブル名
     * @param RecItemNo : レコードインデックス
     * @param ValueNm : 項目名
	 * @return 機能IDの項目値
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	public String GetValue(String KindId, String TableNm, int RecItemNo, String ValueNm) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		String strValue = null;
		
		try {
			//機能IDのインデックスを取得
			int iKindItemNo = GetKindIDIndex(KindId);
		
			//機能IDインデックスが正常に取得できた場合
			if (iKindItemNo != -1) {
				//テーブルのインデックスを取得
				int iTableItemNo = GetTableIndex(iKindItemNo, TableNm);
			
				//テーブルインデックスが正常に取得できた場合
				if (iTableItemNo != -1) {
					//項目のインデックスを取得
					int iValueItemNo = GetValueIndex(iKindItemNo, iTableItemNo, ValueNm);
				
					//項目インデックスが正常に取得できた場合
					if (iValueItemNo != -1) {
						//機能IDの項目値を取得
						strValue = GetValue(iKindItemNo, iTableItemNo, RecItemNo, iValueItemNo);
					}
				}
			}

		} catch (Exception e) {
			
			em.ThrowException(e, "機能IDの項目値の取得に失敗しました。");
			
		} finally {
			
		}		

		return strValue;
	}

	/**
	 * 機能IDの項目値
     * @param KindItemNo : 機能IDインデックス
     * @param TableNm : テーブル名
     * @param RecItemNo : レコードインデックス
     * @param ValueNm : 項目名
	 * @return 機能IDの項目値
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	public String GetValue(int KindItemNo, String TableNm, int RecItemNo, String ValueNm) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		String strValue = null;
		
		try {
			//テーブルのインデックスを取得
			int iTableItemNo = GetTableIndex(KindItemNo, TableNm);
		
			//テーブルインデックスが正常に取得できた場合
			if (iTableItemNo != -1) {
				//項目のインデックスを取得
				int iValueItemNo = GetValueIndex(KindItemNo, iTableItemNo, ValueNm);
			
				//項目インデックスが正常に取得できた場合
				if (iValueItemNo != -1) {
					//機能IDの項目値を取得
					strValue = GetValue(KindItemNo, iTableItemNo, RecItemNo, iValueItemNo);
				}
			}

		} catch (Exception e) {
			
			em.ThrowException(e, "機能IDの項目値の取得に失敗しました。");
			
		} finally {
			
		}		

		return strValue;
	}

	/**
	 * 機能IDの項目値
     * @param KindItemNo : 機能IDインデックス
     * @param TableItemNo : テーブルインデックス
     * @param RecItemNo : レコードインデックス
     * @param ValueNm : 項目名
	 * @return 機能IDの項目値
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	public String GetValue(int KindItemNo, int TableItemNo, int RecItemNo, String ValueNm) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		String strValue = null;
		
		try {
			//項目のインデックスを取得
			int iValueItemNo = GetValueIndex(KindItemNo, TableItemNo, ValueNm);
		
			//項目インデックスが正常に取得できた場合
			if (iValueItemNo != -1) {
				//機能IDの項目値を取得
				strValue = GetValue(KindItemNo, TableItemNo, RecItemNo, iValueItemNo);
			}

		} catch (Exception e) {
			
			em.ThrowException(e, "機能IDの項目値の取得に失敗しました。");
			
		} finally {
			
		}		

		return strValue;
	}

	/**
	 * 機能IDの項目値
     * @param KindId : 機能ID
     * @param TableItemNo : テーブルインデックス
     * @param RecItemNo : レコードインデックス
     * @param ValueNm : 項目名
	 * @return 機能IDの項目値
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	public String GetValue(String KindId, int TableItemNo, int RecItemNo, String ValueNm) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		String strValue = null;
		
		try {
			//機能IDのインデックスを取得
			int iKindItemNo = GetKindIDIndex(KindId);
		
			//機能IDインデックスが正常に取得できた場合
			if (iKindItemNo != -1) {
				//項目のインデックスを取得
				int iValueItemNo = GetValueIndex(iKindItemNo, TableItemNo, ValueNm);
			
				//項目インデックスが正常に取得できた場合
				if (iValueItemNo != -1) {
					//機能IDの項目値を取得
					strValue = GetValue(iKindItemNo, TableItemNo, RecItemNo, iValueItemNo);
				}
			}

		} catch (Exception e) {
			
			em.ThrowException(e, "機能IDの項目値の取得に失敗しました。");
			
		} finally {
			
		}		

		return strValue;
	}

	/**
	 * 機能IDの項目値
     * @param KindId : 機能IDインデックス
     * @param TableNm : テーブル名
     * @param RecItemNo : レコードインデックス
     * @param ValueItemNo : 項目インデックス
	 * @return 機能IDの項目値
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	public String GetValue(int KindItemNo, String TableNm, int RecItemNo, int ValueItemNo) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		String strValue = null;
		
		try {
			//テーブルのインデックスを取得
			int iTableItemNo = GetTableIndex(KindItemNo, TableNm);
		
			//テーブルインデックスが正常に取得できた場合
			if (iTableItemNo != -1) {
				//機能IDの項目値を取得
				strValue = GetValue(KindItemNo, iTableItemNo, RecItemNo, ValueItemNo);
			}

		} catch (Exception e) {
			
			em.ThrowException(e, "機能IDの項目値の取得に失敗しました。");
			
		} finally {
			
		}		

		return strValue;
	}

	/**
	 * 機能IDの項目値(int)
     * @param KindItemNo : 機能IDインデックス
     * @param TableItemNo : テーブルインデックス
     * @param RecItemNo : レコードインデックス
     * @param ValueItemNo : 項目インデックス
	 * @return 機能IDの項目値(int)
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	public int GetValueInt(int KindItemNo, int TableItemNo, int RecItemNo, int ValueNo) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		int iRtnValue = 0;
		
		try {
			//取得項目値がNULL以外の場合
			if (null != GetValue(KindItemNo, TableItemNo, RecItemNo, ValueNo)){
				
				//項目値をint型にて設定
				iRtnValue = Integer.parseInt(GetValue(KindItemNo, TableItemNo, RecItemNo, ValueNo));
			}
		} catch (Exception e) {
			
			em.ThrowException(e, "機能IDの項目値(int)に失敗しました。");
			
		} finally {
			
		}		
		
		return iRtnValue;
	}

	/**
	 * 機能IDの項目値(int)
     * @param KindId : 機能ID
     * @param TableItemNo : テーブルインデックス
     * @param RecItemNo : レコードインデックス
     * @param ValueItemNo : 項目インデックス
	 * @return 機能IDの項目値(int)
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	public int GetValueInt(String KindId, int TableItemNo, int RecItemNo, int ValueItemNo) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		int iRtnValue = 0;
		
		try {
			//機能IDのインデックスを取得
			int iKindItemNo = GetKindIDIndex(KindId);
		
			//機能IDインデックスが正常に取得できた場合
			if (iKindItemNo != -1) {
				//機能IDの項目値(int)を取得
				iRtnValue = GetValueInt(iKindItemNo, TableItemNo, RecItemNo, ValueItemNo);
			}
		} catch (Exception e) {
			
			em.ThrowException(e, "機能IDの項目値(int)に失敗しました。");
			
		} finally {
			
		}		
		
		return iRtnValue;
	}

	/**
	 * 機能IDの項目値(int)
     * @param KindId : 機能ID
     * @param TableNm : テーブル名
     * @param RecItemNo : レコードインデックス
     * @param ValueItemNo : 項目インデックス
	 * @return 機能IDの項目値(int)
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	public int GetValueInt(String KindId, String TableNm, int RecItemNo, int ValueItemNo) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		int iRtnValue = 0;
		
		try {
			//機能IDのインデックスを取得
			int iKindItemNo = GetKindIDIndex(KindId);
		
			//機能IDインデックスが正常に取得できた場合
			if (iKindItemNo != -1) {
				//テーブルのインデックスを取得
				int iTableItemNo = GetTableIndex(iKindItemNo, TableNm);
			
				//テーブルインデックスが正常に取得できた場合
				if (iTableItemNo != -1) {
					//機能IDの項目値(int)を取得
					iRtnValue = GetValueInt(iKindItemNo, iTableItemNo, RecItemNo, ValueItemNo);
				}
			}
		} catch (Exception e) {
			
			em.ThrowException(e, "機能IDの項目値(int)に失敗しました。");
			
		} finally {
			
		}		
		
		return iRtnValue;
	}

	/**
	 * 機能IDの項目値(int)
     * @param KindId : 機能ID
     * @param TableNm : テーブル名
     * @param RecItemNo : レコードインデックス
     * @param ValueNm : 項目名
	 * @return 機能IDの項目値(int)
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	public int GetValueInt(String KindId, String TableNm, int RecItemNo, String ValueNm) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		int iRtnValue = 0;
		
		try {
			//機能IDのインデックスを取得
			int iKindItemNo = GetKindIDIndex(KindId);
		
			//機能IDインデックスが正常に取得できた場合
			if (iKindItemNo != -1) {
				//テーブルのインデックスを取得
				int iTableItemNo = GetTableIndex(iKindItemNo, TableNm);
			
				//テーブルインデックスが正常に取得できた場合
				if (iTableItemNo != -1) {
					//項目のインデックスを取得
					int iValueItemNo = GetValueIndex(iKindItemNo, iTableItemNo, ValueNm);
				
					//項目インデックスが正常に取得できた場合
					if (iValueItemNo != -1) {
						//機能IDの項目値(int)を取得
						iRtnValue = GetValueInt(iKindItemNo, iTableItemNo, RecItemNo, iValueItemNo);
					}
				}
			}
		} catch (Exception e) {
			
			em.ThrowException(e, "機能IDの項目値(int)に失敗しました。");
			
		} finally {
			
		}		
		
		return iRtnValue;
	}

	/**
	 * 機能IDの項目値(int)
     * @param KindItemNo : 機能IDインデックス
     * @param TableNm : テーブル名
     * @param RecItemNo : レコードインデックス
     * @param ValueNm : 項目名
	 * @return 機能IDの項目値(int)
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	public int GetValueInt(int KindItemNo, String TableNm, int RecItemNo, String ValueNm) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		int iRtnValue = 0;
		
		try {
			//テーブルのインデックスを取得
			int iTableItemNo = GetTableIndex(KindItemNo, TableNm);
		
			//テーブルインデックスが正常に取得できた場合
			if (iTableItemNo != -1) {
				//項目のインデックスを取得
				int iValueItemNo = GetValueIndex(KindItemNo, iTableItemNo, ValueNm);
			
				//項目インデックスが正常に取得できた場合
				if (iValueItemNo != -1) {
					//機能IDの項目値(int)を取得
					iRtnValue = GetValueInt(KindItemNo, iTableItemNo, RecItemNo, iValueItemNo);
				}
			}
		} catch (Exception e) {
			
			em.ThrowException(e, "機能IDの項目値(int)に失敗しました。");
			
		} finally {
			
		}		
		
		return iRtnValue;
	}

	/**
	 * 機能IDの項目値(int)
     * @param KindItemNo : 機能IDインデックス
     * @param TableItemNo : テーブルインデックス
     * @param RecItemNo : レコードインデックス
     * @param ValueNm : 項目名
	 * @return 機能IDの項目値(int)
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	public int GetValueInt(int KindItemNo, int TableItemNo, int RecItemNo, String ValueNm) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		int iRtnValue = 0;
		
		try {
			//項目のインデックスを取得
			int iValueItemNo = GetValueIndex(KindItemNo, TableItemNo, ValueNm);
		
			//項目インデックスが正常に取得できた場合
			if (iValueItemNo != -1) {
				//機能IDの項目値(int)を取得
				iRtnValue = GetValueInt(KindItemNo, TableItemNo, RecItemNo, iValueItemNo);
			}
		} catch (Exception e) {
			
			em.ThrowException(e, "機能IDの項目値(int)に失敗しました。");
			
		} finally {
			
		}		
		
		return iRtnValue;
	}

	/**
	 * 機能IDの項目値(int)
     * @param String : 機能ID
     * @param TableItemNo : テーブルインデックス
     * @param RecItemNo : レコードインデックス
     * @param ValueNm : 項目名
	 * @return 機能IDの項目値(int)
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	public int GetValueInt(String KindId, int TableItemNo, int RecItemNo, String ValueNm) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		int iRtnValue = 0;
		
		try {
			//機能IDのインデックスを取得
			int iKindItemNo = GetKindIDIndex(KindId);
		
			//機能IDインデックスが正常に取得できた場合
			if (iKindItemNo != -1) {
				//項目のインデックスを取得
				int iValueItemNo = GetValueIndex(iKindItemNo, TableItemNo, ValueNm);
			
				//項目インデックスが正常に取得できた場合
				if (iValueItemNo != -1) {
					//機能IDの項目値(int)を取得
					iRtnValue = GetValueInt(iKindItemNo, TableItemNo, RecItemNo, iValueItemNo);
				}
			}
		} catch (Exception e) {
			
			em.ThrowException(e, "機能IDの項目値(int)に失敗しました。");
			
		} finally {
			
		}		
		
		return iRtnValue;
	}

	/**
	 * 機能IDの項目値(int)
     * @param KindItemNo : 機能IDインデックス
     * @param TableNm : テーブル
     * @param RecItemNo : レコードインデックス
     * @param ValueItemNo : 項目インデックス
	 * @return 機能IDの項目値(int)
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	public int GetValueInt(int KindItemNo, String TableNm, int RecItemNo, int ValueItemNo) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		int iRtnValue = 0;
		
		try {
			//テーブルのインデックスを取得
			int iTableItemNo = GetTableIndex(KindItemNo, TableNm);
		
			//テーブルインデックスが正常に取得できた場合
			if (iTableItemNo != -1) {
					//機能IDの項目値(int)を取得
					iRtnValue = GetValueInt(KindItemNo, iTableItemNo, RecItemNo, ValueItemNo);
			}
		} catch (Exception e) {
			
			em.ThrowException(e, "機能IDの項目値(int)に失敗しました。");
			
		} finally {
			
		}		
		
		return iRtnValue;
	}

	/**
	 * 機能IDの項目値(Decimal)
     * @param KindItemo : 機能IDインデックス
     * @param TableItemNo : テーブルインデックス
     * @param RecItemNo : レコードインデックス
     * @param ValueItemNo : 項目インデックス
	 * @return 機能IDの項目値(Decimal)
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	public BigDecimal GetValueDec(int KindItemNo, int TableItemNo, int RecItemNo, int ValueNo) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
	
		BigDecimal dciValue = null;
	
		try {
			//取得項目値がNULL以外の場合
			if (null != GetValue(KindItemNo, TableItemNo, RecItemNo, ValueNo)){
				
				//項目値をBigDecimal型にて設定
				dciValue = new BigDecimal(GetValue(KindItemNo, TableItemNo, RecItemNo, ValueNo));
			}
		} catch (Exception e) {
			
			em.ThrowException(e, "機能IDの項目値(Decimal)に失敗しました。");
			
		} finally {
			
		}		

		return dciValue;
	}

	/**
	 * 機能IDの項目値(Decimal)
     * @param KindId : 機能ID
     * @param TableItemNo : テーブルインデックス
     * @param RecItemNo : レコードインデックス
     * @param ValueItemNo : 項目インデックス
	 * @return 機能IDの項目値(Decimal)
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	public BigDecimal GetValueDec(String KindId, int TableItemNo, int RecItemNo, int ValueItemNo) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		BigDecimal dciValue = null;
		
		try {
			//機能IDのインデックスを取得
			int iKindItemNo = GetKindIDIndex(KindId);
		
			//機能IDインデックスが正常に取得できた場合
			if (iKindItemNo != -1) {
				//機能IDの項目値(Decimal)を取得
				dciValue = GetValueDec(iKindItemNo, TableItemNo, RecItemNo, ValueItemNo);
			}
		} catch (Exception e) {
			
			em.ThrowException(e, "機能IDの項目値(Decimal)に失敗しました。");
			
		} finally {
			
		}		
		
		return dciValue;
	}

	/**
	 * 機能IDの項目値(Decimal)
     * @param KindId : 機能ID
     * @param TableNm : テーブル名
     * @param RecItemNo : レコードインデックス
     * @param ValueItemNo : 項目インデックス
	 * @return 機能IDの項目値(Decimal)
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	public BigDecimal GetValueDec(String KindId, String TableNm, int RecItemNo, int ValueItemNo) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		BigDecimal dciValue = null;
		
		try {
			//機能IDのインデックスを取得
			int iKindItemNo = GetKindIDIndex(KindId);
		
			//機能IDインデックスが正常に取得できた場合
			if (iKindItemNo != -1) {
				//テーブルのインデックスを取得
				int iTableItemNo = GetTableIndex(iKindItemNo, TableNm);
			
				//テーブルインデックスが正常に取得できた場合
				if (iTableItemNo != -1) {
					//機能IDの項目値(Decimal)を取得
					dciValue = GetValueDec(iKindItemNo, iTableItemNo, RecItemNo, ValueItemNo);
				}
			}
		} catch (Exception e) {
			
			em.ThrowException(e, "機能IDの項目値(Decimal)に失敗しました。");
			
		} finally {
			
		}		
		
		return dciValue;
	}

	/**
	 * 機能IDの項目値(Decimal)
     * @param KindId : 機能ID
     * @param TableNm : テーブル名
     * @param RecItemNo : レコードインデックス
     * @param ValueNm : 項目名
	 * @return 機能IDの項目値(Decimal)
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	public BigDecimal GetValueDec(String KindId, String TableNm, int RecItemNo, String ValueNm) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		BigDecimal dciValue = null;
		
		try {
			//機能IDのインデックスを取得
			int iKindItemNo = GetKindIDIndex(KindId);
		
			//機能IDインデックスが正常に取得できた場合
			if (iKindItemNo != -1) {
				//テーブルのインデックスを取得
				int iTableItemNo = GetTableIndex(iKindItemNo, TableNm);
			
				//テーブルインデックスが正常に取得できた場合
				if (iTableItemNo != -1) {
					//項目のインデックスを取得
					int iValueItemNo = GetValueIndex(iKindItemNo, iTableItemNo, ValueNm);
				
					//項目インデックスが正常に取得できた場合
					if (iValueItemNo != -1) {
						//機能IDの項目値(Decimal)を取得
						dciValue = GetValueDec(iKindItemNo, iTableItemNo, RecItemNo, iValueItemNo);
					}
				}
			}
		} catch (Exception e) {
			
			em.ThrowException(e, "機能IDの項目値(Decimal)に失敗しました。");
			
		} finally {
			
		}		
		
		return dciValue;
	}

	/**
	 * 機能IDの項目値(Decimal)
     * @param KindItemNo : 機能IDインデックス
     * @param TableNm : テーブル名
     * @param RecItemNo : レコードインデックス
     * @param ValueNm : 項目名
	 * @return 機能IDの項目値(Decimal)
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	public BigDecimal GetValueDec(int KindItemNo, String TableNm, int RecItemNo, String ValueNm) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		BigDecimal dciValue = null;
		
		try {
			//テーブルのインデックスを取得
			int iTableItemNo = GetTableIndex(KindItemNo, TableNm);
		
			//テーブルインデックスが正常に取得できた場合
			if (iTableItemNo != -1) {
				//項目のインデックスを取得
				int iValueItemNo = GetValueIndex(KindItemNo, iTableItemNo, ValueNm);
			
				//項目インデックスが正常に取得できた場合
				if (iValueItemNo != -1) {
					//機能IDの項目値(Decimal)を取得
					dciValue = GetValueDec(KindItemNo, iTableItemNo, RecItemNo, iValueItemNo);
				}
			}
		} catch (Exception e) {
			
			em.ThrowException(e, "機能IDの項目値(Decimal)に失敗しました。");
			
		} finally {
			
		}		
		
		return dciValue;
	}

	/**
	 * 機能IDの項目値(Decimal)
     * @param KindItemNo : 機能IDインデックス
     * @param TableItemNo : テーブルインデックス
     * @param RecItemNo : レコードインデックス
     * @param ValueNm : 項目名
	 * @return 機能IDの項目値(Decimal)
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	public BigDecimal GetValueDec(int KindItemNo, int TableItemNo, int RecItemNo, String ValueNm) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		BigDecimal dciValue = null;
		
		try {
			//項目のインデックスを取得
			int iValueItemNo = GetValueIndex(KindItemNo, TableItemNo, ValueNm);
		
			//項目インデックスが正常に取得できた場合
			if (iValueItemNo != -1) {
				//機能IDの項目値(Decimal)を取得
				dciValue = GetValueDec(KindItemNo, TableItemNo, RecItemNo, iValueItemNo);
			}
		} catch (Exception e) {
			
			em.ThrowException(e, "機能IDの項目値(Decimal)に失敗しました。");
			
		} finally {
			
		}		
		
		return dciValue;
	}

	/**
	 * 機能IDの項目値(Decimal)
     * @param String : 機能ID
     * @param TableItemNo : テーブルインデックス
     * @param RecItemNo : レコードインデックス
     * @param ValueNm : 項目名
	 * @return 機能IDの項目値(Decimal)
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	public BigDecimal GetValueDec(String KindId, int TableItemNo, int RecItemNo, String ValueNm) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		BigDecimal dciValue = null;
		
		try {
			//機能IDのインデックスを取得
			int iKindItemNo = GetKindIDIndex(KindId);
		
			//機能IDインデックスが正常に取得できた場合
			if (iKindItemNo != -1) {
				//項目のインデックスを取得
				int iValueItemNo = GetValueIndex(iKindItemNo, TableItemNo, ValueNm);
			
				//項目インデックスが正常に取得できた場合
				if (iValueItemNo != -1) {
					//機能IDの項目値(Decimal)を取得
					dciValue = GetValueDec(iKindItemNo, TableItemNo, RecItemNo, iValueItemNo);
				}
			}
		} catch (Exception e) {
			
			em.ThrowException(e, "機能IDの項目値(Decimal)に失敗しました。");
			
		} finally {
			
		}		
		
		return dciValue;
	}

	/**
	 * 機能IDの項目値(Decimal)
     * @param KindItemNo : 機能IDインデックス
     * @param TableNm : テーブル
     * @param RecItemNo : レコードインデックス
     * @param ValueItemNo : 項目インデックス
	 * @return 機能IDの項目値(Decimal)
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	public BigDecimal GetValueDec(int KindItemNo, String TableNm, int RecItemNo, int ValueItemNo) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		BigDecimal dciValue = null;
		
		try {
			//テーブルのインデックスを取得
			int iTableItemNo = GetTableIndex(KindItemNo, TableNm);
		
			//テーブルインデックスが正常に取得できた場合
			if (iTableItemNo != -1) {
					//機能IDの項目値(Decimal)を取得
					dciValue = GetValueDec(KindItemNo, iTableItemNo, RecItemNo, ValueItemNo);
			}
		} catch (Exception e) {
			
			em.ThrowException(e, "機能IDの項目値(Decimal)に失敗しました。");
			
		} finally {
			
		}		
		
		return dciValue;
	}

	/**
	 * 機能IDの項目値(Double)
     * @param KindItemo : 機能IDインデックス
     * @param TableItemNo : テーブルインデックス
     * @param RecItemNo : レコードインデックス
     * @param ValueItemNo : 項目インデックス
	 * @return 機能IDの項目値(Double)
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	public double GetValueDub(int KindItemNo, int TableItemNo, int RecItemNo, int ValueNo) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		double dblValue = 0;
		
		try {
			//取得項目値がNULL以外の場合
			if (null != GetValue(KindItemNo, TableItemNo, RecItemNo, ValueNo)){
				
				//項目値をdouble型にて設定
				dblValue = Double.parseDouble(GetValue(KindItemNo, TableItemNo, RecItemNo, ValueNo));
			}
		} catch (Exception e) {
			
			em.ThrowException(e, "機能IDの項目値(Double)に失敗しました。");
			
		} finally {
			
		}		
		
		return dblValue;

	}

	/**
	 * 機能IDの項目値(Double)
     * @param KindId : 機能ID
     * @param TableItemNo : テーブルインデックス
     * @param RecItemNo : レコードインデックス
     * @param ValueItemNo : 項目インデックス
	 * @return 機能IDの項目値(Double)
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	public double GetValueDub(String KindId, int TableItemNo, int RecItemNo, int ValueItemNo) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		double dblValue = 0;
		
		try {
			//機能IDのインデックスを取得
			int iKindItemNo = GetKindIDIndex(KindId);
		
			//機能IDインデックスが正常に取得できた場合
			if (iKindItemNo != -1) {
				//機能IDの項目値(Double)を取得
				dblValue = GetValueDub(iKindItemNo, TableItemNo, RecItemNo, ValueItemNo);
			}
		} catch (Exception e) {
			
			em.ThrowException(e, "機能IDの項目値(Double)に失敗しました。");
			
		} finally {
			
		}		
		
		return dblValue;
	}

	/**
	 * 機能IDの項目値(Double)
     * @param KindId : 機能ID
     * @param TableNm : テーブル名
     * @param RecItemNo : レコードインデックス
     * @param ValueItemNo : 項目インデックス
	 * @return 機能IDの項目値(Double)
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	public double GetValueDub(String KindId, String TableNm, int RecItemNo, int ValueItemNo) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		double dblValue = 0;
		
		try {
			//機能IDのインデックスを取得
			int iKindItemNo = GetKindIDIndex(KindId);
		
			//機能IDインデックスが正常に取得できた場合
			if (iKindItemNo != -1) {
				//テーブルのインデックスを取得
				int iTableItemNo = GetTableIndex(iKindItemNo, TableNm);
			
				//テーブルインデックスが正常に取得できた場合
				if (iTableItemNo != -1) {
					//機能IDの項目値(Double)を取得
					dblValue = GetValueDub(iKindItemNo, iTableItemNo, RecItemNo, ValueItemNo);
				}
			}
		} catch (Exception e) {
			
			em.ThrowException(e, "機能IDの項目値(Double)に失敗しました。");
			
		} finally {
			
		}		
		
		return dblValue;
	}

	/**
	 * 機能IDの項目値(Double)
     * @param KindId : 機能ID
     * @param TableNm : テーブル名
     * @param RecItemNo : レコードインデックス
     * @param ValueNm : 項目名
	 * @return 機能IDの項目値(Double)
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	public double GetValueDub(String KindId, String TableNm, int RecItemNo, String ValueNm) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		double dblValue = 0;
		
		try {
			//機能IDのインデックスを取得
			int iKindItemNo = GetKindIDIndex(KindId);
		
			//機能IDインデックスが正常に取得できた場合
			if (iKindItemNo != -1) {
				//テーブルのインデックスを取得
				int iTableItemNo = GetTableIndex(iKindItemNo, TableNm);
			
				//テーブルインデックスが正常に取得できた場合
				if (iTableItemNo != -1) {
					//項目のインデックスを取得
					int iValueItemNo = GetValueIndex(iKindItemNo, iTableItemNo, ValueNm);
				
					//項目インデックスが正常に取得できた場合
					if (iValueItemNo != -1) {
						//機能IDの項目値(Double)を取得
						dblValue = GetValueDub(iKindItemNo, iTableItemNo, RecItemNo, iValueItemNo);
					}
				}
			}
		} catch (Exception e) {
			
			em.ThrowException(e, "機能IDの項目値(Double)に失敗しました。");
			
		} finally {
			
		}		
		
		return dblValue;
	}

	/**
	 * 機能IDの項目値(Double)
     * @param KindItemNo : 機能IDインデックス
     * @param TableNm : テーブル名
     * @param RecItemNo : レコードインデックス
     * @param ValueNm : 項目名
	 * @return 機能IDの項目値(Double)
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	public double GetValueDub(int KindItemNo, String TableNm, int RecItemNo, String ValueNm) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		double dblValue = 0;
		
		try {
			//テーブルのインデックスを取得
			int iTableItemNo = GetTableIndex(KindItemNo, TableNm);
		
			//テーブルインデックスが正常に取得できた場合
			if (iTableItemNo != -1) {
				//項目のインデックスを取得
				int iValueItemNo = GetValueIndex(KindItemNo, iTableItemNo, ValueNm);
			
				//項目インデックスが正常に取得できた場合
				if (iValueItemNo != -1) {
					//機能IDの項目値(Double)を取得
					dblValue = GetValueDub(KindItemNo, iTableItemNo, RecItemNo, iValueItemNo);
				}
			}
		} catch (Exception e) {
			
			em.ThrowException(e, "機能IDの項目値(Double)に失敗しました。");
			
		} finally {
			
		}		
		
		return dblValue;
	}

	/**
	 * 機能IDの項目値(Double)
     * @param KindItemNo : 機能IDインデックス
     * @param TableItemNo : テーブルインデックス
     * @param RecItemNo : レコードインデックス
     * @param ValueNm : 項目名
	 * @return 機能IDの項目値(Double)
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	public double GetValueDub(int KindItemNo, int TableItemNo, int RecItemNo, String ValueNm) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		double dblValue = 0;
		
		try {
			//項目のインデックスを取得
			int iValueItemNo = GetValueIndex(KindItemNo, TableItemNo, ValueNm);
		
			//項目インデックスが正常に取得できた場合
			if (iValueItemNo != -1) {
				//機能IDの項目値(Double)を取得
				dblValue = GetValueDub(KindItemNo, TableItemNo, RecItemNo, iValueItemNo);
			}
		} catch (Exception e) {
			
			em.ThrowException(e, "機能IDの項目値(Double)に失敗しました。");
			
		} finally {
			
		}		
		
		return dblValue;
	}

	/**
	 * 機能IDの項目値(Double)
     * @param String : 機能ID
     * @param TableItemNo : テーブルインデックス
     * @param RecItemNo : レコードインデックス
     * @param ValueNm : 項目名
	 * @return 機能IDの項目値(Double)
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	public double GetValueDub(String KindId, int TableItemNo, int RecItemNo, String ValueNm) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		double dblValue = 0;
		
		try {
			//機能IDのインデックスを取得
			int iKindItemNo = GetKindIDIndex(KindId);
		
			//機能IDインデックスが正常に取得できた場合
			if (iKindItemNo != -1) {
				//項目のインデックスを取得
				int iValueItemNo = GetValueIndex(iKindItemNo, TableItemNo, ValueNm);
			
				//項目インデックスが正常に取得できた場合
				if (iValueItemNo != -1) {
					//機能IDの項目値(Double)を取得
					dblValue = GetValueDub(iKindItemNo, TableItemNo, RecItemNo, iValueItemNo);
				}
			}
		} catch (Exception e) {
			
			em.ThrowException(e, "機能IDの項目値(Double)に失敗しました。");
			
		} finally {
			
		}		
		
		return dblValue;
	}

	/**
	 * 機能IDの項目値(Double)
     * @param KindItemNo : 機能IDインデックス
     * @param TableNm : テーブル
     * @param RecItemNo : レコードインデックス
     * @param ValueItemNo : 項目インデックス
	 * @return 機能IDの項目値(Double)
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	public double GetValueDub(int KindItemNo, String TableNm, int RecItemNo, int ValueItemNo) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		double dblValue = 0;
		
		try {
			//テーブルのインデックスを取得
			int iTableItemNo = GetTableIndex(KindItemNo, TableNm);
		
			//テーブルインデックスが正常に取得できた場合
			if (iTableItemNo != -1) {
					//機能IDの項目値(Double)を取得
					dblValue = GetValueDub(KindItemNo, iTableItemNo, RecItemNo, ValueItemNo);
			}
		} catch (Exception e) {
			
			em.ThrowException(e, "機能IDの項目値(Double)に失敗しました。");
			
		} finally {
			
		}		
		
		return dblValue;
	}

	/**
	 * 機能IDの項目値(String)
     * @param KindItemo : 機能IDインデックス
     * @param TableItemNo : テーブルインデックス
     * @param RecItemNo : レコードインデックス
     * @param ValueItemNo : 項目インデックス
	 * @return 機能IDの項目値(String)
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	public String GetValueStr(int KindItemNo, int TableItemNo, int RecItemNo, int ValueNo) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		String ret = "";
		
		try {
			ret = GetValue(KindItemNo, TableItemNo, RecItemNo, ValueNo);
		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {
			
		}
		
		//項目値をStringにて返却
		return ret;
	}

	/**
	 * 機能IDの項目値(String)
     * @param KindId : 機能ID
     * @param TableItemNo : テーブルインデックス
     * @param RecItemNo : レコードインデックス
     * @param ValueItemNo : 項目インデックス
	 * @return 機能IDの項目値(String)
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	public String GetValueStr(String KindId, int TableItemNo, int RecItemNo, int ValueItemNo) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		String ret = "";
		
		try {
			//機能IDのインデックスを取得
			int iKindItemNo = GetKindIDIndex(KindId);
		
			//機能IDインデックスが正常に取得できた場合
			if (iKindItemNo != -1) {
				//機能IDの項目値(String)を取得
				ret = GetValueStr(iKindItemNo, TableItemNo, RecItemNo, ValueItemNo);
			}
		} catch (Exception e) {
			
			em.ThrowException(e, "機能IDの項目値(String)に失敗しました。");
			
		} finally {
			
		}		
		
		return ret;
	}

	/**
	 * 機能IDの項目値(String)
     * @param KindId : 機能ID
     * @param TableNm : テーブル名
     * @param RecItemNo : レコードインデックス
     * @param ValueItemNo : 項目インデックス
	 * @return 機能IDの項目値(String)
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	public String GetValueStr(String KindId, String TableNm, int RecItemNo, int ValueItemNo) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		String ret = "";
		
		try {
			//機能IDのインデックスを取得
			int iKindItemNo = GetKindIDIndex(KindId);
		
			//機能IDインデックスが正常に取得できた場合
			if (iKindItemNo != -1) {
				//テーブルのインデックスを取得
				int iTableItemNo = GetTableIndex(iKindItemNo, TableNm);
			
				//テーブルインデックスが正常に取得できた場合
				if (iTableItemNo != -1) {
					//機能IDの項目値(String)を取得
					ret = GetValueStr(iKindItemNo, iTableItemNo, RecItemNo, ValueItemNo);
				}
			}
		} catch (Exception e) {
			
			em.ThrowException(e, "機能IDの項目値(String)に失敗しました。");
			
		} finally {
			
		}		
		
		return ret;
	}

	/**
	 * 機能IDの項目値(String)
     * @param KindId : 機能ID
     * @param TableNm : テーブル名
     * @param RecItemNo : レコードインデックス
     * @param ValueNm : 項目名
	 * @return 機能IDの項目値(String)
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	public String GetValueStr(String KindId, String TableNm, int RecItemNo, String ValueNm) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		String ret = "";
		
		try {
			//機能IDのインデックスを取得
			int iKindItemNo = GetKindIDIndex(KindId);
		
			//機能IDインデックスが正常に取得できた場合
			if (iKindItemNo != -1) {
				//テーブルのインデックスを取得
				int iTableItemNo = GetTableIndex(iKindItemNo, TableNm);
			
				//テーブルインデックスが正常に取得できた場合
				if (iTableItemNo != -1) {
					//項目のインデックスを取得
					int iValueItemNo = GetValueIndex(iKindItemNo, iTableItemNo, ValueNm);
				
					//項目インデックスが正常に取得できた場合
					if (iValueItemNo != -1) {
						//機能IDの項目値(String)を取得
						ret = GetValueStr(iKindItemNo, iTableItemNo, RecItemNo, iValueItemNo);
					}
				}
			}
		} catch (Exception e) {
			
			em.ThrowException(e, "機能IDの項目値(String)に失敗しました。");
			
		} finally {
			
		}		
		
		return ret;
	}

	/**
	 * 機能IDの項目値(String)
     * @param KindItemNo : 機能IDインデックス
     * @param TableNm : テーブル名
     * @param RecItemNo : レコードインデックス
     * @param ValueNm : 項目名
	 * @return 機能IDの項目値(String)
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	public String GetValueStr(int KindItemNo, String TableNm, int RecItemNo, String ValueNm) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		String ret = "";
		
		try {
			//テーブルのインデックスを取得
			int iTableItemNo = GetTableIndex(KindItemNo, TableNm);
		
			//テーブルインデックスが正常に取得できた場合
			if (iTableItemNo != -1) {
				//項目のインデックスを取得
				int iValueItemNo = GetValueIndex(KindItemNo, iTableItemNo, ValueNm);
			
				//項目インデックスが正常に取得できた場合
				if (iValueItemNo != -1) {
					//機能IDの項目値(String)を取得
					ret = GetValueStr(KindItemNo, iTableItemNo, RecItemNo, iValueItemNo);
				}
			}
		} catch (Exception e) {
			
			em.ThrowException(e, "機能IDの項目値(String)に失敗しました。");
			
		} finally {
			
		}		
		
		return ret;
	}

	/**
	 * 機能IDの項目値(String)
     * @param KindItemNo : 機能IDインデックス
     * @param TableItemNo : テーブルインデックス
     * @param RecItemNo : レコードインデックス
     * @param ValueNm : 項目名
	 * @return 機能IDの項目値(String)
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	public String GetValueStr(int KindItemNo, int TableItemNo, int RecItemNo, String ValueNm) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		String ret = "";
		
		try {
			//項目のインデックスを取得
			int iValueItemNo = GetValueIndex(KindItemNo, TableItemNo, ValueNm);
		
			//項目インデックスが正常に取得できた場合
			if (iValueItemNo != -1) {
				//機能IDの項目値(String)を取得
				ret = GetValueStr(KindItemNo, TableItemNo, RecItemNo, iValueItemNo);
			}
		} catch (Exception e) {
			
			em.ThrowException(e, "機能IDの項目値(String)に失敗しました。");
			
		} finally {
			
		}		
		
		return ret;
	}

	/**
	 * 機能IDの項目値(String)
     * @param String : 機能ID
     * @param TableItemNo : テーブルインデックス
     * @param RecItemNo : レコードインデックス
     * @param ValueNm : 項目名
	 * @return 機能IDの項目値(String)
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	public String GetValueStr(String KindId, int TableItemNo, int RecItemNo, String ValueNm) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		String ret = "";
		
		try {
			//機能IDのインデックスを取得
			int iKindItemNo = GetKindIDIndex(KindId);
		
			//機能IDインデックスが正常に取得できた場合
			if (iKindItemNo != -1) {
				//項目のインデックスを取得
				int iValueItemNo = GetValueIndex(iKindItemNo, TableItemNo, ValueNm);
			
				//項目インデックスが正常に取得できた場合
				if (iValueItemNo != -1) {
					//機能IDの項目値(String)を取得
					ret = GetValueStr(iKindItemNo, TableItemNo, RecItemNo, iValueItemNo);
				}
			}
		} catch (Exception e) {
			
			em.ThrowException(e, "機能IDの項目値(String)に失敗しました。");
			
		} finally {
			
		}		
		
		return ret;
	}

	/**
	 * 機能IDの項目値(String)
     * @param KindItemNo : 機能IDインデックス
     * @param TableNm : テーブル
     * @param RecItemNo : レコードインデックス
     * @param ValueItemNo : 項目インデックス
	 * @return 機能IDの項目値(String)
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	public String GetValueStr(int KindItemNo, String TableNm, int RecItemNo, int ValueItemNo) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		String ret = "";
		
		try {
			//テーブルのインデックスを取得
			int iTableItemNo = GetTableIndex(KindItemNo, TableNm);
		
			//テーブルインデックスが正常に取得できた場合
			if (iTableItemNo != -1) {
					//機能IDの項目値(String)を取得
					ret = GetValueStr(KindItemNo, iTableItemNo, RecItemNo, ValueItemNo);
			}
		} catch (Exception e) {
			
			em.ThrowException(e, "機能IDの項目値(String)に失敗しました。");
			
		} finally {
			
		}		
		
		return ret;
	}

	/**
	 * 機能IDの項目値(Date)
     * @param KindItemo : 機能IDインデックス
     * @param TableItemNo : テーブルインデックス
     * @param RecItemNo : レコードインデックス
     * @param ValueItemNo : 項目インデックス
	 * @return 機能IDの項目値(Date)
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	public Date GetValueDate(int KindItemNo, int TableItemNo, int RecItemNo, int ValueNo) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		Date dateValue = null;
		
		try {
			//取得項目値がNULL以外の場合
			if (null != GetValue(KindItemNo, TableItemNo, RecItemNo, ValueNo)){
	
				//項目値をdate型にて設定
				DateFormat foramt = new SimpleDateFormat("yyyy/MM/dd"); 
				dateValue = foramt.parse(GetValue(KindItemNo, TableItemNo, RecItemNo, ValueNo));
			}
		} catch (Exception e) {
			em.ThrowException(e, "日付型への変換に失敗しました。");
		} 

		return dateValue;
	}
	
	/**
	 * 機能IDの項目値(Date)
     * @param KindId : 機能ID
     * @param TableItemNo : テーブルインデックス
     * @param RecItemNo : レコードインデックス
     * @param ValueItemNo : 項目インデックス
	 * @return 機能IDの項目値(Date)
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	public Date GetValueDate(String KindId, int TableItemNo, int RecItemNo, int ValueItemNo) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		Date dateValue = null;
		
		try {
			//機能IDのインデックスを取得
			int iKindItemNo = GetKindIDIndex(KindId);
		
			//機能IDインデックスが正常に取得できた場合
			if (iKindItemNo != -1) {
				//機能IDの項目値(Date)を取得
				dateValue = GetValueDate(iKindItemNo, TableItemNo, RecItemNo, ValueItemNo);
			}
		} catch (Exception e) {
			
			em.ThrowException(e, "機能IDの項目値(Date)に失敗しました。");
			
		} finally {
			
		}		
		
		return dateValue;
	}

	/**
	 * 機能IDの項目値(Date)
     * @param KindId : 機能ID
     * @param TableNm : テーブル名
     * @param RecItemNo : レコードインデックス
     * @param ValueItemNo : 項目インデックス
	 * @return 機能IDの項目値(Date)
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	public Date GetValueDate(String KindId, String TableNm, int RecItemNo, int ValueItemNo) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		Date dateValue = null;
		
		try {
			//機能IDのインデックスを取得
			int iKindItemNo = GetKindIDIndex(KindId);
		
			//機能IDインデックスが正常に取得できた場合
			if (iKindItemNo != -1) {
				//テーブルのインデックスを取得
				int iTableItemNo = GetTableIndex(iKindItemNo, TableNm);
			
				//テーブルインデックスが正常に取得できた場合
				if (iTableItemNo != -1) {
					//機能IDの項目値(Date)を取得
					dateValue = GetValueDate(iKindItemNo, iTableItemNo, RecItemNo, ValueItemNo);
				}
			}
		} catch (Exception e) {
			
			em.ThrowException(e, "機能IDの項目値(Date)に失敗しました。");
			
		} finally {
			
		}		
		
		return dateValue;
	}

	/**
	 * 機能IDの項目値(Date)
     * @param KindId : 機能ID
     * @param TableNm : テーブル名
     * @param RecItemNo : レコードインデックス
     * @param ValueNm : 項目名
	 * @return 機能IDの項目値(Date)
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	public Date GetValueDate(String KindId, String TableNm, int RecItemNo, String ValueNm) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		Date dateValue = null;
		
		try {
			//機能IDのインデックスを取得
			int iKindItemNo = GetKindIDIndex(KindId);
		
			//機能IDインデックスが正常に取得できた場合
			if (iKindItemNo != -1) {
				//テーブルのインデックスを取得
				int iTableItemNo = GetTableIndex(iKindItemNo, TableNm);
			
				//テーブルインデックスが正常に取得できた場合
				if (iTableItemNo != -1) {
					//項目のインデックスを取得
					int iValueItemNo = GetValueIndex(iKindItemNo, iTableItemNo, ValueNm);
				
					//項目インデックスが正常に取得できた場合
					if (iValueItemNo != -1) {
						//機能IDの項目値(Date)を取得
						dateValue = GetValueDate(iKindItemNo, iTableItemNo, RecItemNo, iValueItemNo);
					}
				}
			}
		} catch (Exception e) {
			
			em.ThrowException(e, "機能IDの項目値(Date)に失敗しました。");
			
		} finally {
			
		}		
		
		return dateValue;
	}

	/**
	 * 機能IDの項目値(Date)
     * @param KindItemNo : 機能IDインデックス
     * @param TableNm : テーブル名
     * @param RecItemNo : レコードインデックス
     * @param ValueNm : 項目名
	 * @return 機能IDの項目値(Date)
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	public Date GetValueDate(int KindItemNo, String TableNm, int RecItemNo, String ValueNm) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		Date dateValue = null;
		
		try {
			//テーブルのインデックスを取得
			int iTableItemNo = GetTableIndex(KindItemNo, TableNm);
		
			//テーブルインデックスが正常に取得できた場合
			if (iTableItemNo != -1) {
				//項目のインデックスを取得
				int iValueItemNo = GetValueIndex(KindItemNo, iTableItemNo, ValueNm);
			
				//項目インデックスが正常に取得できた場合
				if (iValueItemNo != -1) {
					//機能IDの項目値(Date)を取得
					dateValue = GetValueDate(KindItemNo, iTableItemNo, RecItemNo, iValueItemNo);
				}
			}
		} catch (Exception e) {
			
			em.ThrowException(e, "機能IDの項目値(Date)に失敗しました。");
			
		} finally {
			
		}		
		
		return dateValue;
	}

	/**
	 * 機能IDの項目値(Date)
     * @param KindItemNo : 機能IDインデックス
     * @param TableItemNo : テーブルインデックス
     * @param RecItemNo : レコードインデックス
     * @param ValueNm : 項目名
	 * @return 機能IDの項目値(Date)
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	public Date GetValueDate(int KindItemNo, int TableItemNo, int RecItemNo, String ValueNm) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		Date dateValue = null;
		
		try {
			//項目のインデックスを取得
			int iValueItemNo = GetValueIndex(KindItemNo, TableItemNo, ValueNm);
		
			//項目インデックスが正常に取得できた場合
			if (iValueItemNo != -1) {
				//機能IDの項目値(Date)を取得
				dateValue = GetValueDate(KindItemNo, TableItemNo, RecItemNo, iValueItemNo);
			}
		} catch (Exception e) {
			
			em.ThrowException(e, "機能IDの項目値(Date)に失敗しました。");
			
		} finally {
			
		}		
		
		return dateValue;
	}

	/**
	 * 機能IDの項目値(Date)
     * @param String : 機能ID
     * @param TableItemNo : テーブルインデックス
     * @param RecItemNo : レコードインデックス
     * @param ValueNm : 項目名
	 * @return 機能IDの項目値(Date)
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	public Date GetValueDate(String KindId, int TableItemNo, int RecItemNo, String ValueNm) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		Date dateValue = null;
		
		try {
			//機能IDのインデックスを取得
			int iKindItemNo = GetKindIDIndex(KindId);
		
			//機能IDインデックスが正常に取得できた場合
			if (iKindItemNo != -1) {
				//項目のインデックスを取得
				int iValueItemNo = GetValueIndex(iKindItemNo, TableItemNo, ValueNm);
			
				//項目インデックスが正常に取得できた場合
				if (iValueItemNo != -1) {
					//機能IDの項目値(Date)を取得
					dateValue = GetValueDate(iKindItemNo, TableItemNo, RecItemNo, iValueItemNo);
				}
			}
		} catch (Exception e) {
			
			em.ThrowException(e, "機能IDの項目値(Date)に失敗しました。");
			
		} finally {
			
		}		
		
		return dateValue;
	}

	/**
	 * 機能IDの項目値(Date)
     * @param KindItemNo : 機能IDインデックス
     * @param TableNm : テーブル
     * @param RecItemNo : レコードインデックス
     * @param ValueItemNo : 項目インデックス
	 * @return 機能IDの項目値(Date)
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	public Date GetValueDate(int KindItemNo, String TableNm, int RecItemNo, int ValueItemNo) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		Date dateValue = null;
		
		try {
			//テーブルのインデックスを取得
			int iTableItemNo = GetTableIndex(KindItemNo, TableNm);
		
			//テーブルインデックスが正常に取得できた場合
			if (iTableItemNo != -1) {
					//機能IDの項目値(Date)を取得
					dateValue = GetValueDate(KindItemNo, iTableItemNo, RecItemNo, ValueItemNo);
			}
		} catch (Exception e) {
			
			em.ThrowException(e, "機能IDの項目値(Date)に失敗しました。");
			
		} finally {
			
		}		
		
		return dateValue;
	}

}
