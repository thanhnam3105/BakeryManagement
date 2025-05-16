package jp.co.blueflag.shisaquick.jws.base;

import jp.co.blueflag.shisaquick.jws.common.*;

import java.io.File;
import java.util.*;			//ArrayList�N���X

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
 * XML�f�[�^�ێ��N���X
 * 
 */
public class XmlData extends DataBase {
	
	private DocumentBuilderFactory dbfactory;
	private DocumentBuilder docbuilder;
	private Document document;
	
	private ExceptionBase ex;			//�G���[�n���h�����O

	/**
	 * �R���X�g���N�^
	 *  : XML�f�[�^�𐶐�����
	 */
	public XmlData() throws ExceptionBase{
		//�X�[�p�[�N���X�̃R���X�g���N�^�Ăяo��
		super();
		
		try {
			dbfactory = DocumentBuilderFactory.newInstance();
			docbuilder = dbfactory.newDocumentBuilder(); // DocumentBuilder�C���X�^���X
			document = docbuilder.newDocument();         // Document�̐���
			
			
		}catch(Exception e){
			//�G���[�ݒ�
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			ex.setStrErrmsg("XML�f�[�^�̏������Ɏ��s���܂���");
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getMessage());
			throw ex;
			
		}finally{
			
		}
	}
	
	/**
	 * �R���X�g���N�^�i�e�X�g�p�j
	 *  : XML�f�[�^�𐶐�����
	 */
	public XmlData(File fileXml) throws ExceptionBase{
		//�X�[�p�[�N���X�̃R���X�g���N�^�Ăяo��
		super();
		
		try {
			dbfactory = DocumentBuilderFactory.newInstance();
			docbuilder = dbfactory.newDocumentBuilder(); // DocumentBuilder�C���X�^���X
			document = docbuilder.newDocument();         // Document�̐���
			
			//�e�X�gXML�t�@�C���i�[
			DocumentBuilder builder = dbfactory.newDocumentBuilder();
			document = builder.parse(fileXml);
			
		}catch(Exception e){
			//�G���[�ݒ�
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			ex.setStrErrmsg("XML�f�[�^�̏������Ɏ��s���܂���");
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getMessage());
			throw ex;
			
		}finally{
			
		}
	}
	
	
	/**
	 * XML�^�O�ǉ��i���������j
	 *  : XML�ɒP��^�O��ǉ����� 
	 *  @param strSetTagName : �ǉ��w��^�O��
	 *  @param strTagval : �ǉ�����^�O��
	 */
	public void AddXmlTag( String strSetTagName,String strSetAddTagNm) throws ExceptionBase{
		
		try {
	        
	        String strTagName = strSetTagName;		//�w��^�O��
	        String strAddTagNm = strSetAddTagNm;	//�ǉ��^�O��
	        
	        /******************************************************
	          �m�[�h�v�f�𐶐�����
	        *******************************************************/
	        Element elAdd = document.createElement(strAddTagNm);
	        
	        /******************************************************
	          �m�[�h��ǉ�
	        *******************************************************/
	        if(strSetTagName == ""){
	        	// Root�m�[�h��Document�ɒǉ�
		        document.appendChild(elAdd);
		        
	        }else{
	        	//�@�w��m�[�h���ɂăm�[�h���擾
		        NodeList list = document.getElementsByTagName(strTagName);;
		        // �v�f�̐��������[�v
		        for (int i=0; i < list.getLength() ; i++) {
		        	// �v�f���擾
		        	Element element = (Element)list.item(i);
		        	element.appendChild(elAdd);
		        }
	        }

	     }catch (Exception e) {
	    	//�G���[�ݒ�
				ex = new ExceptionBase();
				ex.setStrErrCd("");
				ex.setStrErrmsg("XML�^�O�ǉ��i���������j�Ɏ��s���܂���");
				ex.setStrErrShori(this.getClass().getName());
				ex.setStrMsgNo("");
				ex.setStrSystemMsg(e.getMessage());
				throw ex;
				
	     }finally{
				
		 }
	}
	
	
	/**
	 * XML�^�O�ǉ��i�����t�^�O�j
	 *  : XML�ɒP��^�O��ǉ����� 
	 *  @param strSetTagName : �ǉ��w��^�O��
	 *  @param strSetAddTagNm : �ǉ�����^�O��
	 *  @param arySetAddTag : �������
	 */
	public void AddXmlTag( String strSetTagName,String strSetAddTagNm,ArrayList arySetAddTag) throws ExceptionBase{
		
		try {
	        
	        String strTagName = strSetTagName;		//�w��^�O��
	        String strAddTagNm = strSetAddTagNm;	//�ǉ��^�O��
	        ArrayList aryAddTag = arySetAddTag;		//������
	        
	        /******************************************************
	          �m�[�h�v�f�𐶐�����
	        *******************************************************/
	        Element elAdd = document.createElement(strAddTagNm);
	        for(int i = 0; i < aryAddTag.size(); i++){
	        	String[] strAry = (String[])aryAddTag.get(i);
	        	elAdd.setAttribute(strAry[0], strAry[1]);
	        }
	        
	        /******************************************************
	          �m�[�h��ǉ�
	        *******************************************************/
	        if(strSetTagName == ""){
	        	// Root�m�[�h��Document�ɒǉ�
		        document.appendChild(elAdd);
		        
	        }else{
	        	//�@�w��m�[�h���ɂăm�[�h���擾
		        NodeList list = document.getElementsByTagName(strTagName);
		        // �v�f�̐��������[�v
		        for (int i=0; i < list.getLength() ; i++) {
		        	// �v�f���擾
		        	Element element = (Element)list.item(i);
		        	element.appendChild(elAdd);
		        }
	        }

	     }catch (Exception e) {
	    	//�G���[�ݒ�
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			ex.setStrErrmsg("XML�^�O�ǉ��i�����t�^�O�j�Ɏ��s���܂���");
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getMessage());
			throw ex;
				
	     }finally{
				
		 }
	}
	
	/**
	 * XML�f�[�^�擾�i�z��j
	 *  : �w�肳�ꂽ�^�O��z��ŕԋp
	 * @param strTagid : �w��^�OID
	 */
	public ArrayList GetAryTag( String strTagid ) throws ExceptionBase{
		ArrayList aryTag = new ArrayList();
		
		try{
			String strTagName = strTagid;
			
			/******************************************************
	          �w��^�O��ǉ�
	        *******************************************************/
			NodeList list = document.getElementsByTagName(strTagName);
			
	        // �v�f�̐��������[�v
	        for (int i=0; i < list.getLength() ; i++) {
	        	// �v�f���擾
	        	Element element = (Element)list.item(i);
	        	aryTag.add(GetAryTagE(element));
	        }
	        
        }catch(ExceptionBase e){
        	throw e;
		}catch(Exception e){
        	//�G���[�ݒ�
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			ex.setStrErrmsg("XML�f�[�^�擾�i�z��j�Ɏ��s���܂���");
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getMessage());
			throw ex;
        	
        }finally{
        	
        }
        
        //�@�z���ԋp
		return aryTag;
	}
	
	/**
	 * XML�f�[�^�擾�ielement�j
	 *  : �w�肳�ꂽ�m�[�h��z��ŕԋp
	 *  �F�@���g�̃m�[�h�̍ċA�������s��
	 * @param element : �w��m�[�h
	 */
	private ArrayList GetAryTagE( Element element ) throws ExceptionBase{
		
		try{
			/******************************************************
	          �w��^�O��ǉ�
	        *******************************************************/
			//�@�^�O���̎擾
	        String strTagNm = element.getTagName();
	        
	        //�@�^�O���i�[�z��
	        ArrayList aryTag = new ArrayList();
	        ArrayList aryZokusei = new ArrayList();
	        
	        //�@�������e�X�g�o��
//	        System.out.println("");
//	        System.out.println(element.getTagName());
	        
	        // �����̎擾
        	NamedNodeMap attrs = element.getAttributes();
        	//�@���������݂���ꍇ
            if (attrs.getLength() > 0) {
                    for (int index=0;index<attrs.getLength();index++) {
                    	// �����m�[�h
                    	Node attr = attrs.item(index);
                           
                        //�@�����i�[
                        String[] staryTag = new String[3];
                        staryTag[0] = strTagNm;
                        staryTag[1] = attr.getNodeName();
                        staryTag[2] = attr.getNodeValue();
                          
                        //�@�����z��֒ǉ�
                        aryZokusei.add(staryTag);
                            
                        //�@�����l�e�X�g�o��
//                      System.out.print("������ = " + attr.getNodeName()); // �����̖��O
//                      System.out.println(", �l = " + attr.getNodeValue()); // �����̒l
                    }
                    
            //�@���������݂��Ȃ��ꍇ
            }else{
            	//�@�����i�[
            	String[] staryTag = new String[3];
            	staryTag[0] = strTagNm;
                staryTag[1] = "";
                staryTag[2] = "";
                
                //�@�����z��֒ǉ�
                aryZokusei.add(staryTag);
                
            }
            
            //�������z���ǉ�
            aryTag.add(aryZokusei);
            
            // �w��m�[�h�̎q�m�[�h��S�Ď擾
	        NodeList childList = element.getChildNodes();
	        
	        for (int j=0; j < childList.getLength() ; j++) {
	        	if(childList.item(j).getNodeName() != "#text"){
	        		// �q�m�[�h�̈�v�f���擾
		        	Element celement = (Element)childList.item(j);
		        	// �ċA����
		        	aryTag.add(this.GetAryTagE(celement));
	        	}
	        }
	        
	        //�@�z���ԋp
	        return aryTag;
	        
        }catch(Exception e){
        	e.printStackTrace();

        	//�G���[�ݒ�
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			ex.setStrErrmsg("XML�^�O�ǉ��i�����t�^�O�j�Ɏ��s���܂���");
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getMessage());
			throw ex;
        	
        }finally{
        	
        }
	}
	
	/**
	 * XML�f�[�^ �Q�b�^�[
	 * @return document : XML�f�[�^�̒l��Ԃ�
	 */
	public Document getDocument() {
		return document;
	}
	/**
	 * XML�f�[�^ �Z�b�^�[
	 * @param _document : XML�f�[�^�̒l���i�[����
	 */
	public void setDocument(Document _document) {
		this.document = _document;
	}
	
	/**
	 * XML�\���i�e�X�g�p�j
	 */
	public void dispXml(){
		try{
			/*
	         * DOM�I�u�W�F�N�g�𕶎���Ƃ��ďo��
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
