package jp.co.blueflag.shisaquick.srv.common;

import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;


/**
 * 
 * 定数XML格納
 *  : XMLを読み込みリスト化し格納する
 *  
 * @author TT.k-katayama
 * @since  2009/03/25
 *
 */
public class XmlConstList {

	private List ConstList = null;	//XmlConstListRowのLIST
	private XmlConstListRow xmlConstListRow = null;	//LISTの行
	private ExceptionManager em = null;				//Exception管理クラス
	
	/**
	 * コンストラクタ
	 *  : XMLを読み込みリストに格納する
	 * @param strConstFname : コンストXMLのファイル名（FULLPath)
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 *  
	 * @author TT.k-katayama
	 * @since  2009/03/25
	 */
	public XmlConstList(String strConstFname) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		//① インスタンスを生成する
		this.ConstList = new ArrayList();
		this.em = new ExceptionManager(this.getClass().getName());
		
		try {
			// XML読み込み XmlConstListRowのListに格納する
			this.LoadXml(strConstFname);

		} catch (Exception e) {
			this.em.ThrowException(e, "");

		} finally {
			
		}
		
	}
	
	/**
	 * コンストの取得 : 指定のコンスト値を返却
	 * @param strCode : コンストのコード
	 * @return コンスト値
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 *  
	 * @author TT.k-katayama
	 * @since  2009/03/25
	 */
	public String GetConstValue(String strCode) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		String strValue = "";
		
		try {
			// 指定のコンスト値をListより検索し値を返却
			for ( int i=0; i<this.ConstList.size(); i++ ) {
				this.xmlConstListRow = (XmlConstListRow) this.ConstList.get(i);
				if ( this.xmlConstListRow.getCode().equals(strCode) ) {
					strValue = this.xmlConstListRow.getValue();
				}
			}
			
			// 該当が無い場合は例外をスローする
			if ( strValue == "" ) {
				this.em.ThrowException(ExceptionManager.ExceptionKind.警告Exception, "W000100", strCode, "", "");
			}
		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {
			
		}
		
		return strValue;
	}
	
	/**
	 * XML読み込み
	 *  : XML読み込み、XmlConstListRowのListに格納する
	 * @param strConstFname : コンストXMLのファイル名（FULLPath)
	 * @throws Exception
	 *  
	 * @author TT.k-katayama
	 * @since  2009/03/26
	 */
	private void LoadXml(String strConstFname) throws Exception {
		//XMLファイルのファイルパス取得
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		String xmlfile = loader.getResource(strConstFname).getPath();
		
		//XMLを読み込み、XmlConstListRowのListに格納する
		// ドキュメントビルダーファクトリを生成
		DocumentBuilderFactory dbfactory = DocumentBuilderFactory.newInstance();
		// ドキュメントビルダーを生成
		DocumentBuilder builder = dbfactory.newDocumentBuilder();
		// パースを実行してDocumentオブジェクトを取得
		Document doc = builder.parse(xmlfile);
		// ルート要素を取得（タグ名：ConstXML）
		Element root = doc.getDocumentElement();

		// Rec要素のリストを取得
		NodeList list = root.getElementsByTagName("Rec");
		// Rec要素の数だけループ
		for (int i=0; i < list.getLength() ; i++) {

			// Rec要素を取得
			Element element = (Element)list.item(i);
			
			// code属性の値を取得
			String code = element.getAttribute("code");
			// value属性の値を取得
			String value = element.getAttribute("value");
			
			//XmlConstListRowに格納
			this.xmlConstListRow = new XmlConstListRow(code,value);

			//XmlConstListRowをListに追加
			this.ConstList.add(this.xmlConstListRow);

		}
	}
}
