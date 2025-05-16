package jp.co.blueflag.shisaquick.jws.manager;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import jp.co.blueflag.shisaquick.jws.base.*;
import jp.co.blueflag.shisaquick.jws.common.*;

/**
 * 
 * XML通信
 *  : XMLを設定し、指定サーバと通信を行う
 *
 */
public class XmlConnection {

	private String strAddress;		//通信先アドレス
	private XmlData xdtSend = new XmlData();		//送信用のXMLデータ
	private XmlData xdtRes = new XmlData();			//受信用のXMLデータ
	private ExceptionBase ex;
	
	/**
	 * XML通信 コンストラクタ
	 *  : 初期化を行い、引数の情報より通信を開始する
	 * @param xdocSendparm :  XMLデータ
	 */
	public XmlConnection(XmlData xdocSendparm) throws ExceptionBase{
		try{
			this.xdtSend = xdocSendparm;
			this.strAddress = "";
		}catch(Exception e){
			//エラー設定
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			ex.setStrErrmsg("XML通信の初期化に失敗しました");
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getMessage());
			throw ex;
		}
	}
	
	/**
	 * データ送信
	 *  : 指定したアドレスへXMLデータを送信する
	 */
	public void XmlSend() throws ExceptionBase{
		try{
			//　接続情報設定
			URL url = new URL(this.strAddress);
			HttpURLConnection http = (HttpURLConnection)url.openConnection();
			http.setRequestProperty("Content-type", "text/xml");
			http.setRequestProperty("charset", "UTF-8");
			http.setRequestMethod("POST");
			http.setDoOutput(true);
			http.connect();
			
			//　XMLデータ生成
		  	DocumentBuilderFactory dbfactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docbuilder = dbfactory.newDocumentBuilder(); // DocumentBuilderインスタンス
			Document document = docbuilder.newDocument();         // Documentの生成
			
			//　出力ストリームを取得し，それを使ってデータを送信する
			OutputStreamWriter osw = new OutputStreamWriter( http.getOutputStream(), "UTF-8" );
			StringWriter sw = new StringWriter();
			TransformerFactory tfactory = TransformerFactory.newInstance();
	        Transformer transformer = tfactory.newTransformer();
	        transformer.transform(new DOMSource(xdtSend.getDocument()), new StreamResult(sw));
			osw.write(sw.toString());
			osw.flush();
			osw.close();
			sw.close();
			
			//　入力ストリームを取得
			BufferedReader bis = new BufferedReader(new InputStreamReader(http.getInputStream(),"UTF-8"));
			StringBuffer stringBuffer = new StringBuffer();
			String line;
			
			//　リクエスト情報取得失敗
		  	if (null == bis){
		  		//エラー設定
				ex = new ExceptionBase();
				ex.setStrErrCd("");
				ex.setStrErrmsg("XMLデータの受信に失敗しました");
				ex.setStrErrShori(this.getClass().toString());
				ex.setStrMsgNo("");
				ex.setStrSystemMsg("XMLデータの受信に失敗");
				throw ex;
		  	}
		  	
		  	// ストリームを読み込み
		  	while ((line = bis.readLine()) != null) {
	  			stringBuffer.append(line);
	  		}
			DocumentBuilder builder = dbfactory.newDocumentBuilder();
			document = builder.parse(new InputSource(new StringReader(stringBuffer.toString())));;
			
			//　返却用XMLデータ格納
			this.xdtRes.setDocument(document);
			
		}catch(ExceptionBase e){
			throw ex;
			
		}catch(Exception e){
			e.printStackTrace();
			
			//エラー設定
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			ex.setStrErrmsg("XMLデータの送信に失敗しました");
			ex.setStrErrShori(this.getClass().toString());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getMessage());
			throw ex;
			
		}finally{
			
		}
	}
	
	/**
	 * 受信データ取得
	 *  : 通信により取得したXMLデータを取得する
	 * @return XMLデータ保持クラス
	 */
	public XmlData getXdocRes() {
		return xdtRes;
	}
	
	/**
	 * 通信先アドレス ゲッター
	 * @return strAddress : 通信先アドレスの値を返す
	 */
	public String getStrAddress() {
		return strAddress;
	}
	/**
	 * 通信先アドレス セッター
	 * @param _strAddress : 通信先アドレスの値を格納する
	 */
	public void setStrAddress(String _strAddress) {
		this.strAddress = _strAddress;
	}

	/**
	 * 送信用のXMLデータ ゲッター
	 * @return xdocSend : 送信用のXMLデータの値を返す
	 */
	public XmlData getXdocSend() {
		return xdtSend;
	}
	/**
	 * 送信用のXMLデータ セッター
	 * @param _xdocSend : 送信用のXMLデータの値を格納する
	 */
	public void setXdocSend(XmlData _xdocSend) {
		this.xdtSend = _xdocSend;
	}
	
}
