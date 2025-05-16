package jp.co.blueflag.shisaquick.srv.base;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringReader;
import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXParseException;

import jp.co.blueflag.shisaquick.srv.base.RequestData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;

/**
 * 
 * XML変換
 *  : リクエストXMLより機能ID　・　各データを抽出し、機能IDに紐付く各データ保持クラスに設定する。
 *    機能IDごとの設定データをリクエストデータ保持クラスに格納する。
 *    機能IDごとの設定データをレスポンスデータ保持クラスに格納する。
 * @author TT.furuta
 * @since  2009/03/24
 */
public class ChangeXML  extends ObjectBase{
	
	private HttpServletRequest req;		//HTTPリクエストデータ
	private RequestData requestData;		//リクエストデータ保持クラス

	/**
	 * コンストラクタ
	 *  : XML変換用コンストラクタ
	 */	
	public ChangeXML(){
		super();
	}
	
	/**
	 * リクエストデータ生成
	 *  : リクエストデータ保持クラスを生成する。
	 * @param requestData : リクエストデータ保持クラス
	 * @param strKinouID : 機能ID
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	public RequestData createRequestData(HttpServletRequest req) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		Document xmlObj = null;
		
		try{
			//①リクエスト情報をメンバreqに退避
			this.req = req;
			
			//②リクエスト情報をXMLｵﾌﾞｼﾞｪｸﾄに変換す
			xmlObj = readXMLInfo();
			
			//③XMLｵﾌﾞｼﾞｪｸﾄを基にRequestDataを生成する
			requestData = new RequestData(xmlObj);
			
		} catch (Exception e) {
			
			em.ThrowException(e, "リクエストデータ生成に失敗しました。");
			
		} finally {
			xmlObj = null;
		}
		
		//④生成したRequestDataｸﾗｽを返却する
		return requestData;	
	}
	
	/**
	 * XML情報抽出
	 *  : リクエストXMLの情報を抽出する。
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private Document readXMLInfo() throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		Document xmlObj = null;
		
		BufferedReader requestData = null;
		StringBuffer stringBuffer = null;
		DocumentBuilder builder = null;
		StringReader aaa = null;
		InputSource bbb = null;
		
		try{

			//①リクエスト情報より、XML情報文字列を取得
		  	requestData = new BufferedReader(new InputStreamReader(req.getInputStream(),"UTF-8"));
		  	stringBuffer = new StringBuffer();
		  	String line;

	  		while ((line = requestData.readLine()) != null) {
	  			//改行コードの位置をチェックする
	  			if (line.substring(line.length()-1,line.length()).equals(">")) {
		  			stringBuffer.append(line);
	  			} else {
	                //改行コードを文字に置き換える
		  			stringBuffer.append(line + "\\n");
	  			}
//	  			stringBuffer.append(line);
	  		}

	  		//②：①にて取得した情報を用い、XMLオブジェクト（階層型）を形成する。
			builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();

//			xmlObj	= builder.parse(new InputSource(new StringReader(stringBuffer.toString())));

			aaa = new StringReader(stringBuffer.toString());

			bbb = new InputSource(aaa);

			try{
				xmlObj	= builder.parse(bbb);
				
			}catch(SAXParseException ex){
				
			}
			
		} catch (Exception e) {
			em.ThrowException(e, "XML情報抽出に失敗しました。");
			
		} finally {
			requestData = null;
			stringBuffer = null;
			builder = null;
			aaa = null;
			bbb = null;
		}

		return xmlObj;
	}
}
