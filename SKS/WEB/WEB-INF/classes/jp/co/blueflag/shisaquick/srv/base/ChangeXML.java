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
 * XML�ϊ�
 *  : ���N�G�X�gXML���@�\ID�@�E�@�e�f�[�^�𒊏o���A�@�\ID�ɕR�t���e�f�[�^�ێ��N���X�ɐݒ肷��B
 *    �@�\ID���Ƃ̐ݒ�f�[�^�����N�G�X�g�f�[�^�ێ��N���X�Ɋi�[����B
 *    �@�\ID���Ƃ̐ݒ�f�[�^�����X�|���X�f�[�^�ێ��N���X�Ɋi�[����B
 * @author TT.furuta
 * @since  2009/03/24
 */
public class ChangeXML  extends ObjectBase{
	
	private HttpServletRequest req;		//HTTP���N�G�X�g�f�[�^
	private RequestData requestData;		//���N�G�X�g�f�[�^�ێ��N���X

	/**
	 * �R���X�g���N�^
	 *  : XML�ϊ��p�R���X�g���N�^
	 */	
	public ChangeXML(){
		super();
	}
	
	/**
	 * ���N�G�X�g�f�[�^����
	 *  : ���N�G�X�g�f�[�^�ێ��N���X�𐶐�����B
	 * @param requestData : ���N�G�X�g�f�[�^�ێ��N���X
	 * @param strKinouID : �@�\ID
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	public RequestData createRequestData(HttpServletRequest req) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		Document xmlObj = null;
		
		try{
			//�@���N�G�X�g���������oreq�ɑޔ�
			this.req = req;
			
			//�A���N�G�X�g����XML��޼ު�Ăɕϊ���
			xmlObj = readXMLInfo();
			
			//�BXML��޼ު�Ă����RequestData�𐶐�����
			requestData = new RequestData(xmlObj);
			
		} catch (Exception e) {
			
			em.ThrowException(e, "���N�G�X�g�f�[�^�����Ɏ��s���܂����B");
			
		} finally {
			xmlObj = null;
		}
		
		//�C��������RequestData�׽��ԋp����
		return requestData;	
	}
	
	/**
	 * XML��񒊏o
	 *  : ���N�G�X�gXML�̏��𒊏o����B
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

			//�@���N�G�X�g�����AXML��񕶎�����擾
		  	requestData = new BufferedReader(new InputStreamReader(req.getInputStream(),"UTF-8"));
		  	stringBuffer = new StringBuffer();
		  	String line;

	  		while ((line = requestData.readLine()) != null) {
	  			//���s�R�[�h�̈ʒu���`�F�b�N����
	  			if (line.substring(line.length()-1,line.length()).equals(">")) {
		  			stringBuffer.append(line);
	  			} else {
	                //���s�R�[�h�𕶎��ɒu��������
		  			stringBuffer.append(line + "\\n");
	  			}
//	  			stringBuffer.append(line);
	  		}

	  		//�A�F�@�ɂĎ擾��������p���AXML�I�u�W�F�N�g�i�K�w�^�j���`������B
			builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();

//			xmlObj	= builder.parse(new InputSource(new StringReader(stringBuffer.toString())));

			aaa = new StringReader(stringBuffer.toString());

			bbb = new InputSource(aaa);

			try{
				xmlObj	= builder.parse(bbb);
				
			}catch(SAXParseException ex){
				
			}
			
		} catch (Exception e) {
			em.ThrowException(e, "XML��񒊏o�Ɏ��s���܂����B");
			
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
