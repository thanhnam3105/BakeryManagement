
package jp.co.blueflag.shisaquick.srv.base;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import jp.co.blueflag.shisaquick.srv.base.ResponsData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;

/**
 * 
 * XML生成
 *  : レスポンスデータ保持より、機能IDごとの各データ保持クラスを抽出し、機能IDごとのXMLを生成する。
 *    上記生成XMLを返却用XML（XMLID単位）へ格納する。
 * @author TT.furuta
 * @since  2009/03/26
 */
public class CreateXML extends ObjectBase{
	
	/**
	 * コンストラクタ
	 *  : XML生成用コンストラクタ
	 */
	public CreateXML() {
		super();
	}
	
	/**
	 * XML情報生成
	 *  : 返却用XML情報を生成する。
	 * @param responseData : レスポンスデータ保持クラス
	 * @return レスポンスXMLデータ
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	public Document writeXMLInfo(ResponsData responseData) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		Document document = null;
		
        DocumentBuilderFactory fact = null;
        DocumentBuilder builder = null;
        Element elmXML = null;
		try{
	        
	        //インスタンスを取得する
	        fact = DocumentBuilderFactory.newInstance();
	        builder = fact.newDocumentBuilder();
	        	
	        //ビルダーからDOMを取得する
	        document = builder.newDocument();
	        
	        //①：レスポンスデータより、XMlIDを抽出し、エレメントルートを生成する。
	        elmXML = document.createElement(responseData.getID());
	        document.appendChild(elmXML);

	        //②：USERINFOXMLオブジェクト生成を呼出す。
//    		document = createUserInfoXML(document, elmXML);

	        for (int i=0;i < responseData.getItemList().size();i++){
	        	
	        	//機能Bean取得
	        	RequestResponsKindBean respKinoBean 
	        	= (RequestResponsKindBean)responseData.getItemList().get(i);
	        	
	            //③：機能IDごとのXMLルートを作成し、テーブルXMLオブジェクト生成を呼出す。
	            Element elmKino = document.createElement(respKinoBean.getID());

	        	elmKino = createTableXML(respKinoBean, document, elmKino);
	        	
	        	elmXML.appendChild(elmKino);
	        }
	        
		} catch (Exception e) {
			this.em.ThrowException(e, "");

		} finally {
			fact = null;
			builder = null;
			elmXML = null;
		}

		//④：生成されたXMLオブジェクトを返却する。
		return document;
	}
	
	/**
	 * テーブルXMLオブジェクト生成
	 *  : レスポンス用XMLを作成する。
	 * @param responseData : レスポンスデータ保持クラス
	 * @param strKinouID : 機能ID
	 * @return レスポンスXMLデータ
	 */
	private Element createTableXML(RequestResponsKindBean responseData, Document document, Element elmKino) {
	     
		
        for (int i=0;i < responseData.getItemList().size();i++){
        	
        	//①引数：レスポンスデータより、テーブルデータ取得
        	RequestResponsTableBean respTableBean = (RequestResponsTableBean)responseData.getItemList().get(i);
        	
            //②：①を用い、テーブルタグ生成
            Element elmTable = document.createElement(respTableBean.getID());

        	//③：レコードXMLオブジェクト生成を呼出す。
            elmTable = createRecXML(respTableBean, document, elmTable);
        	
            //④：③戻り値を設定し、返却する。
        	elmKino.appendChild(elmTable);
        }
        
 		return elmKino;
	}
	
	/**
	 * レコードXMLオブジェクト生成
	 * @param responseData : レスポンスデータ保持クラス
	 * @param document : ドキュメント
	 * @param elmTable : テーブルのエレメント
	 * @return レコードオブジェクトのエレメント
	 */
	private Element createRecXML(RequestResponsTableBean responseData, Document document, Element elmTable){
		
        for (int i=0;i < responseData.getItemList().size();i++){
        	
        	//①引数：レスポンスデータより、テーブルデータ取得
        	RequestResponsRowBean respRecBean = (RequestResponsRowBean)responseData.getItemList().get(i);
        	
            //②：①を用い、レコードタグ生成
            Element elmRec = document.createElement(respRecBean.getID());

        	//③：パラメーターXMLオブジェクト生成を呼出す。
            elmRec = createPrmXML(respRecBean, document, elmRec);
        	
            //④：③戻り値を設定し、返却する。
            elmTable.appendChild(elmRec);
        }

		return elmTable;
	}

	/**
	 * パラメータXMLオブジェクト生成
	 * @param responseData : レスポンスデータ保持クラス
	 * @param document : ドキュメント
	 * @param elmRec : レコードのエレメント
	 * @return パラメータオブジェクトのエレメント
	 */
	private Element createPrmXML(RequestResponsRowBean responseData, Document document, Element elmRec){
		
        for (int i=0;i < responseData.getItemList().size();i++){
        	
        	//①引数：レスポンスデータより、パラメーターデータ取得
        	RequestResponsFieldBean respPrmRow = (RequestResponsFieldBean)responseData.getItemList().get(i);
        
        	//②：①より取得したデータを、XML属性に追加する。
        	elmRec.setAttribute(respPrmRow.getName(), respPrmRow.getValue());
        }
        
        return elmRec;
	}

}
	