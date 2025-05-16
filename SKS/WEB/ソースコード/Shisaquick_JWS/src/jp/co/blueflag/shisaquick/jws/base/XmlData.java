package jp.co.blueflag.shisaquick.jws.base;

import jp.co.blueflag.shisaquick.jws.common.*;

import java.io.File;
import java.util.*;			//ArrayListクラス

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Node;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;


/**
 * 
 * XMLデータ保持クラス
 * 
 */
public class XmlData extends DataBase {
	
	private DocumentBuilderFactory dbfactory;
	private DocumentBuilder docbuilder;
	private Document document;
	
	private ExceptionBase ex;			//エラーハンドリング

	/**
	 * コンストラクタ
	 *  : XMLデータを生成する
	 */
	public XmlData() throws ExceptionBase{
		//スーパークラスのコンストラクタ呼び出し
		super();
		
		try {
			dbfactory = DocumentBuilderFactory.newInstance();
			docbuilder = dbfactory.newDocumentBuilder(); // DocumentBuilderインスタンス
			document = docbuilder.newDocument();         // Documentの生成
			
			
		}catch(Exception e){
			//エラー設定
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			ex.setStrErrmsg("XMLデータの初期化に失敗しました");
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getMessage());
			throw ex;
			
		}finally{
			
		}
	}
	
	/**
	 * コンストラクタ（テスト用）
	 *  : XMLデータを生成する
	 */
	public XmlData(File fileXml) throws ExceptionBase{
		//スーパークラスのコンストラクタ呼び出し
		super();
		
		try {
			dbfactory = DocumentBuilderFactory.newInstance();
			docbuilder = dbfactory.newDocumentBuilder(); // DocumentBuilderインスタンス
			document = docbuilder.newDocument();         // Documentの生成
			
			//テストXMLファイル格納
			DocumentBuilder builder = dbfactory.newDocumentBuilder();
			document = builder.parse(fileXml);
			
		}catch(Exception e){
			//エラー設定
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			ex.setStrErrmsg("XMLデータの初期化に失敗しました");
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getMessage());
			throw ex;
			
		}finally{
			
		}
	}
	
	
	/**
	 * XMLタグ追加（属性無し）
	 *  : XMLに単一タグを追加する 
	 *  @param strSetTagName : 追加指定タグ名
	 *  @param strTagval : 追加するタグ名
	 */
	public void AddXmlTag( String strSetTagName,String strSetAddTagNm) throws ExceptionBase{
		
		try {
	        
	        String strTagName = strSetTagName;		//指定タグ名
	        String strAddTagNm = strSetAddTagNm;	//追加タグ名
	        
	        /******************************************************
	          ノード要素を生成する
	        *******************************************************/
	        Element elAdd = document.createElement(strAddTagNm);
	        
	        /******************************************************
	          ノードを追加
	        *******************************************************/
	        if(strSetTagName == ""){
	        	// RootノードをDocumentに追加
		        document.appendChild(elAdd);
		        
	        }else{
	        	//　指定ノード名にてノードを取得
		        NodeList list = document.getElementsByTagName(strTagName);;
		        // 要素の数だけループ
		        for (int i=0; i < list.getLength() ; i++) {
		        	// 要素を取得
		        	Element element = (Element)list.item(i);
		        	element.appendChild(elAdd);
		        }
	        }

	     }catch (Exception e) {
	    	//エラー設定
				ex = new ExceptionBase();
				ex.setStrErrCd("");
				ex.setStrErrmsg("XMLタグ追加（属性無し）に失敗しました");
				ex.setStrErrShori(this.getClass().getName());
				ex.setStrMsgNo("");
				ex.setStrSystemMsg(e.getMessage());
				throw ex;
				
	     }finally{
				
		 }
	}
	
	
	/**
	 * XMLタグ追加（属性付タグ）
	 *  : XMLに単一タグを追加する 
	 *  @param strSetTagName : 追加指定タグ名
	 *  @param strSetAddTagNm : 追加するタグ名
	 *  @param arySetAddTag : 属性情報
	 */
	public void AddXmlTag( String strSetTagName,String strSetAddTagNm,ArrayList arySetAddTag) throws ExceptionBase{
		
		try {
	        
	        String strTagName = strSetTagName;		//指定タグ名
	        String strAddTagNm = strSetAddTagNm;	//追加タグ名
	        ArrayList aryAddTag = arySetAddTag;		//属性名
	        
	        /******************************************************
	          ノード要素を生成する
	        *******************************************************/
	        Element elAdd = document.createElement(strAddTagNm);
	        for(int i = 0; i < aryAddTag.size(); i++){
	        	String[] strAry = (String[])aryAddTag.get(i);
	        	elAdd.setAttribute(strAry[0], strAry[1]);
	        }
	        
	        /******************************************************
	          ノードを追加
	        *******************************************************/
	        if(strSetTagName == ""){
	        	// RootノードをDocumentに追加
		        document.appendChild(elAdd);
		        
	        }else{
	        	//　指定ノード名にてノードを取得
		        NodeList list = document.getElementsByTagName(strTagName);
		        // 要素の数だけループ
		        for (int i=0; i < list.getLength() ; i++) {
		        	// 要素を取得
		        	Element element = (Element)list.item(i);
		        	element.appendChild(elAdd);
		        }
	        }

	     }catch (Exception e) {
	    	//エラー設定
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			ex.setStrErrmsg("XMLタグ追加（属性付タグ）に失敗しました");
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getMessage());
			throw ex;
				
	     }finally{
				
		 }
	}
	
	/**
	 * XMLデータ取得（配列）
	 *  : 指定されたタグを配列で返却
	 * @param strTagid : 指定タグID
	 */
	public ArrayList GetAryTag( String strTagid ) throws ExceptionBase{
		ArrayList aryTag = new ArrayList();
		
		try{
			String strTagName = strTagid;
			
			/******************************************************
	          指定タグを追加
	        *******************************************************/
			NodeList list = document.getElementsByTagName(strTagName);
			
	        // 要素の数だけループ
	        for (int i=0; i < list.getLength() ; i++) {
	        	// 要素を取得
	        	Element element = (Element)list.item(i);
	        	aryTag.add(GetAryTagE(element));
	        }
	        
        }catch(ExceptionBase e){
        	throw e;
		}catch(Exception e){
        	//エラー設定
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			ex.setStrErrmsg("XMLデータ取得（配列）に失敗しました");
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getMessage());
			throw ex;
        	
        }finally{
        	
        }
        
        //　配列を返却
		return aryTag;
	}
	
	/**
	 * XMLデータ取得（element）
	 *  : 指定されたノードを配列で返却
	 *  ：　自身のノードの再帰処理を行う
	 * @param element : 指定ノード
	 */
	private ArrayList GetAryTagE( Element element ) throws ExceptionBase{
		
		try{
			/******************************************************
	          指定タグを追加
	        *******************************************************/
			//　タグ名の取得
	        String strTagNm = element.getTagName();
	        
	        //　タグ情報格納配列
	        ArrayList aryTag = new ArrayList();
	        ArrayList aryZokusei = new ArrayList();
	        
	        //　属性名テスト出力
//	        System.out.println("");
//	        System.out.println(element.getTagName());
	        
	        // 属性の取得
        	NamedNodeMap attrs = element.getAttributes();
        	//　属性が存在する場合
            if (attrs.getLength() > 0) {
                    for (int index=0;index<attrs.getLength();index++) {
                    	// 属性ノード
                    	Node attr = attrs.item(index);
                           
                        //　属性格納
                        String[] staryTag = new String[3];
                        staryTag[0] = strTagNm;
                        staryTag[1] = attr.getNodeName();
                        staryTag[2] = attr.getNodeValue();
                          
                        //　属性配列へ追加
                        aryZokusei.add(staryTag);
                            
                        //　属性値テスト出力
//                      System.out.print("属性名 = " + attr.getNodeName()); // 属性の名前
//                      System.out.println(", 値 = " + attr.getNodeValue()); // 属性の値
                    }
                    
            //　属性が存在しない場合
            }else{
            	//　属性格納
            	String[] staryTag = new String[3];
            	staryTag[0] = strTagNm;
                staryTag[1] = "";
                staryTag[2] = "";
                
                //　属性配列へ追加
                aryZokusei.add(staryTag);
                
            }
            
            //属性情報配列を追加
            aryTag.add(aryZokusei);
            
            // 指定ノードの子ノードを全て取得
	        NodeList childList = element.getChildNodes();
	        
	        for (int j=0; j < childList.getLength() ; j++) {
	        	if(childList.item(j).getNodeName() != "#text"){
	        		// 子ノードの一要素を取得
		        	Element celement = (Element)childList.item(j);
		        	// 再帰処理
		        	aryTag.add(this.GetAryTagE(celement));
	        	}
	        }
	        
	        //　配列を返却
	        return aryTag;
	        
        }catch(Exception e){
        	e.printStackTrace();

        	//エラー設定
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			ex.setStrErrmsg("XMLタグ追加（属性付タグ）に失敗しました");
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getMessage());
			throw ex;
        	
        }finally{
        	
        }
	}
	
	/**
	 * XMLデータ ゲッター
	 * @return document : XMLデータの値を返す
	 */
	public Document getDocument() {
		return document;
	}
	/**
	 * XMLデータ セッター
	 * @param _document : XMLデータの値を格納する
	 */
	public void setDocument(Document _document) {
		this.document = _document;
	}
	
	/**
	 * XML表示（テスト用）
	 */
	public void dispXml(){
		try{
			/*
	         * DOMオブジェクトを文字列として出力
	         */ 
			
	        TransformerFactory tfactory = TransformerFactory.newInstance(); 
	        Transformer transformer = tfactory.newTransformer(); 
	        transformer.setOutputProperty(OutputKeys.ENCODING, "Shift_JIS");
	        transformer.transform(new DOMSource(document), new StreamResult(System.out)); 
		}catch(TransformerException e){
			e.printStackTrace();
		}
	}
	
	
}
