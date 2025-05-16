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
 * XML�ʐM
 *  : XML��ݒ肵�A�w��T�[�o�ƒʐM���s��
 *
 */
public class XmlConnection {

	private String strAddress;		//�ʐM��A�h���X
	private XmlData xdtSend = new XmlData();		//���M�p��XML�f�[�^
	private XmlData xdtRes = new XmlData();			//��M�p��XML�f�[�^
	private ExceptionBase ex;
	
	/**
	 * XML�ʐM �R���X�g���N�^
	 *  : ���������s���A�����̏����ʐM���J�n����
	 * @param xdocSendparm :  XML�f�[�^
	 */
	public XmlConnection(XmlData xdocSendparm) throws ExceptionBase{
		try{
			this.xdtSend = xdocSendparm;
			this.strAddress = "";
		}catch(Exception e){
			//�G���[�ݒ�
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			ex.setStrErrmsg("XML�ʐM�̏������Ɏ��s���܂���");
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getMessage());
			throw ex;
		}
	}
	
	/**
	 * �f�[�^���M
	 *  : �w�肵���A�h���X��XML�f�[�^�𑗐M����
	 */
	public void XmlSend() throws ExceptionBase{
		try{
			//�@�ڑ����ݒ�
			URL url = new URL(this.strAddress);
			HttpURLConnection http = (HttpURLConnection)url.openConnection();
			http.setRequestProperty("Content-type", "text/xml");
			http.setRequestProperty("charset", "UTF-8");
			http.setRequestMethod("POST");
			http.setDoOutput(true);
			http.connect();
			
			//�@XML�f�[�^����
		  	DocumentBuilderFactory dbfactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docbuilder = dbfactory.newDocumentBuilder(); // DocumentBuilder�C���X�^���X
			Document document = docbuilder.newDocument();         // Document�̐���
			
			//�@�o�̓X�g���[�����擾���C������g���ăf�[�^�𑗐M����
			OutputStreamWriter osw = new OutputStreamWriter( http.getOutputStream(), "UTF-8" );
			StringWriter sw = new StringWriter();
			TransformerFactory tfactory = TransformerFactory.newInstance();
	        Transformer transformer = tfactory.newTransformer();
	        transformer.transform(new DOMSource(xdtSend.getDocument()), new StreamResult(sw));
			osw.write(sw.toString());
			osw.flush();
			osw.close();
			sw.close();
			
			//�@���̓X�g���[�����擾
			BufferedReader bis = new BufferedReader(new InputStreamReader(http.getInputStream(),"UTF-8"));
			StringBuffer stringBuffer = new StringBuffer();
			String line;
			
			//�@���N�G�X�g���擾���s
		  	if (null == bis){
		  		//�G���[�ݒ�
				ex = new ExceptionBase();
				ex.setStrErrCd("");
				ex.setStrErrmsg("XML�f�[�^�̎�M�Ɏ��s���܂���");
				ex.setStrErrShori(this.getClass().toString());
				ex.setStrMsgNo("");
				ex.setStrSystemMsg("XML�f�[�^�̎�M�Ɏ��s");
				throw ex;
		  	}
		  	
		  	// �X�g���[����ǂݍ���
		  	while ((line = bis.readLine()) != null) {
	  			stringBuffer.append(line);
	  		}
			DocumentBuilder builder = dbfactory.newDocumentBuilder();
			document = builder.parse(new InputSource(new StringReader(stringBuffer.toString())));;
			
			//�@�ԋp�pXML�f�[�^�i�[
			this.xdtRes.setDocument(document);
			
		}catch(ExceptionBase e){
			throw ex;
			
		}catch(Exception e){
			e.printStackTrace();
			
			//�G���[�ݒ�
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			ex.setStrErrmsg("XML�f�[�^�̑��M�Ɏ��s���܂���");
			ex.setStrErrShori(this.getClass().toString());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getMessage());
			throw ex;
			
		}finally{
			
		}
	}
	
	/**
	 * ��M�f�[�^�擾
	 *  : �ʐM�ɂ��擾����XML�f�[�^���擾����
	 * @return XML�f�[�^�ێ��N���X
	 */
	public XmlData getXdocRes() {
		return xdtRes;
	}
	
	/**
	 * �ʐM��A�h���X �Q�b�^�[
	 * @return strAddress : �ʐM��A�h���X�̒l��Ԃ�
	 */
	public String getStrAddress() {
		return strAddress;
	}
	/**
	 * �ʐM��A�h���X �Z�b�^�[
	 * @param _strAddress : �ʐM��A�h���X�̒l���i�[����
	 */
	public void setStrAddress(String _strAddress) {
		this.strAddress = _strAddress;
	}

	/**
	 * ���M�p��XML�f�[�^ �Q�b�^�[
	 * @return xdocSend : ���M�p��XML�f�[�^�̒l��Ԃ�
	 */
	public XmlData getXdocSend() {
		return xdtSend;
	}
	/**
	 * ���M�p��XML�f�[�^ �Z�b�^�[
	 * @param _xdocSend : ���M�p��XML�f�[�^�̒l���i�[����
	 */
	public void setXdocSend(XmlData _xdocSend) {
		this.xdtSend = _xdocSend;
	}
	
}
