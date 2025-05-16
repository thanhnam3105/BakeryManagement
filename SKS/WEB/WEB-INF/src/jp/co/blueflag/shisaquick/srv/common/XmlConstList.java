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
 * �萔XML�i�[
 *  : XML��ǂݍ��݃��X�g�����i�[����
 *  
 * @author TT.k-katayama
 * @since  2009/03/25
 *
 */
public class XmlConstList {

	private List ConstList = null;	//XmlConstListRow��LIST
	private XmlConstListRow xmlConstListRow = null;	//LIST�̍s
	private ExceptionManager em = null;				//Exception�Ǘ��N���X
	
	/**
	 * �R���X�g���N�^
	 *  : XML��ǂݍ��݃��X�g�Ɋi�[����
	 * @param strConstFname : �R���X�gXML�̃t�@�C�����iFULLPath)
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 *  
	 * @author TT.k-katayama
	 * @since  2009/03/25
	 */
	public XmlConstList(String strConstFname) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		//�@ �C���X�^���X�𐶐�����
		this.ConstList = new ArrayList();
		this.em = new ExceptionManager(this.getClass().getName());
		
		try {
			// XML�ǂݍ��� XmlConstListRow��List�Ɋi�[����
			this.LoadXml(strConstFname);

		} catch (Exception e) {
			this.em.ThrowException(e, "");

		} finally {
			
		}
		
	}
	
	/**
	 * �R���X�g�̎擾 : �w��̃R���X�g�l��ԋp
	 * @param strCode : �R���X�g�̃R�[�h
	 * @return �R���X�g�l
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
			// �w��̃R���X�g�l��List��茟�����l��ԋp
			for ( int i=0; i<this.ConstList.size(); i++ ) {
				this.xmlConstListRow = (XmlConstListRow) this.ConstList.get(i);
				if ( this.xmlConstListRow.getCode().equals(strCode) ) {
					strValue = this.xmlConstListRow.getValue();
				}
			}
			
			// �Y���������ꍇ�͗�O���X���[����
			if ( strValue == "" ) {
				this.em.ThrowException(ExceptionManager.ExceptionKind.�x��Exception, "W000100", strCode, "", "");
			}
		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {
			
		}
		
		return strValue;
	}
	
	/**
	 * XML�ǂݍ���
	 *  : XML�ǂݍ��݁AXmlConstListRow��List�Ɋi�[����
	 * @param strConstFname : �R���X�gXML�̃t�@�C�����iFULLPath)
	 * @throws Exception
	 *  
	 * @author TT.k-katayama
	 * @since  2009/03/26
	 */
	private void LoadXml(String strConstFname) throws Exception {
		//XML�t�@�C���̃t�@�C���p�X�擾
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		String xmlfile = loader.getResource(strConstFname).getPath();
		
		//XML��ǂݍ��݁AXmlConstListRow��List�Ɋi�[����
		// �h�L�������g�r���_�[�t�@�N�g���𐶐�
		DocumentBuilderFactory dbfactory = DocumentBuilderFactory.newInstance();
		// �h�L�������g�r���_�[�𐶐�
		DocumentBuilder builder = dbfactory.newDocumentBuilder();
		// �p�[�X�����s����Document�I�u�W�F�N�g���擾
		Document doc = builder.parse(xmlfile);
		// ���[�g�v�f���擾�i�^�O���FConstXML�j
		Element root = doc.getDocumentElement();

		// Rec�v�f�̃��X�g���擾
		NodeList list = root.getElementsByTagName("Rec");
		// Rec�v�f�̐��������[�v
		for (int i=0; i < list.getLength() ; i++) {

			// Rec�v�f���擾
			Element element = (Element)list.item(i);
			
			// code�����̒l���擾
			String code = element.getAttribute("code");
			// value�����̒l���擾
			String value = element.getAttribute("value");
			
			//XmlConstListRow�Ɋi�[
			this.xmlConstListRow = new XmlConstListRow(code,value);

			//XmlConstListRow��List�ɒǉ�
			this.ConstList.add(this.xmlConstListRow);

		}
	}
}
