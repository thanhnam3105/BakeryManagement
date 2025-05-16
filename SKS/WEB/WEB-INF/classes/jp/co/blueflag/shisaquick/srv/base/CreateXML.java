
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
 * XML����
 *  : ���X�|���X�f�[�^�ێ����A�@�\ID���Ƃ̊e�f�[�^�ێ��N���X�𒊏o���A�@�\ID���Ƃ�XML�𐶐�����B
 *    ��L����XML��ԋp�pXML�iXMLID�P�ʁj�֊i�[����B
 * @author TT.furuta
 * @since  2009/03/26
 */
public class CreateXML extends ObjectBase{
	
	/**
	 * �R���X�g���N�^
	 *  : XML�����p�R���X�g���N�^
	 */
	public CreateXML() {
		super();
	}
	
	/**
	 * XML��񐶐�
	 *  : �ԋp�pXML���𐶐�����B
	 * @param responseData : ���X�|���X�f�[�^�ێ��N���X
	 * @return ���X�|���XXML�f�[�^
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
	        
	        //�C���X�^���X���擾����
	        fact = DocumentBuilderFactory.newInstance();
	        builder = fact.newDocumentBuilder();
	        	
	        //�r���_�[����DOM���擾����
	        document = builder.newDocument();
	        
	        //�@�F���X�|���X�f�[�^���AXMlID�𒊏o���A�G�������g���[�g�𐶐�����B
	        elmXML = document.createElement(responseData.getID());
	        document.appendChild(elmXML);

	        //�A�FUSERINFOXML�I�u�W�F�N�g�������ďo���B
//    		document = createUserInfoXML(document, elmXML);

	        for (int i=0;i < responseData.getItemList().size();i++){
	        	
	        	//�@�\Bean�擾
	        	RequestResponsKindBean respKinoBean 
	        	= (RequestResponsKindBean)responseData.getItemList().get(i);
	        	
	            //�B�F�@�\ID���Ƃ�XML���[�g���쐬���A�e�[�u��XML�I�u�W�F�N�g�������ďo���B
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

		//�C�F�������ꂽXML�I�u�W�F�N�g��ԋp����B
		return document;
	}
	
	/**
	 * �e�[�u��XML�I�u�W�F�N�g����
	 *  : ���X�|���X�pXML���쐬����B
	 * @param responseData : ���X�|���X�f�[�^�ێ��N���X
	 * @param strKinouID : �@�\ID
	 * @return ���X�|���XXML�f�[�^
	 */
	private Element createTableXML(RequestResponsKindBean responseData, Document document, Element elmKino) {
	     
		
        for (int i=0;i < responseData.getItemList().size();i++){
        	
        	//�@�����F���X�|���X�f�[�^���A�e�[�u���f�[�^�擾
        	RequestResponsTableBean respTableBean = (RequestResponsTableBean)responseData.getItemList().get(i);
        	
            //�A�F�@��p���A�e�[�u���^�O����
            Element elmTable = document.createElement(respTableBean.getID());

        	//�B�F���R�[�hXML�I�u�W�F�N�g�������ďo���B
            elmTable = createRecXML(respTableBean, document, elmTable);
        	
            //�C�F�B�߂�l��ݒ肵�A�ԋp����B
        	elmKino.appendChild(elmTable);
        }
        
 		return elmKino;
	}
	
	/**
	 * ���R�[�hXML�I�u�W�F�N�g����
	 * @param responseData : ���X�|���X�f�[�^�ێ��N���X
	 * @param document : �h�L�������g
	 * @param elmTable : �e�[�u���̃G�������g
	 * @return ���R�[�h�I�u�W�F�N�g�̃G�������g
	 */
	private Element createRecXML(RequestResponsTableBean responseData, Document document, Element elmTable){
		
        for (int i=0;i < responseData.getItemList().size();i++){
        	
        	//�@�����F���X�|���X�f�[�^���A�e�[�u���f�[�^�擾
        	RequestResponsRowBean respRecBean = (RequestResponsRowBean)responseData.getItemList().get(i);
        	
            //�A�F�@��p���A���R�[�h�^�O����
            Element elmRec = document.createElement(respRecBean.getID());

        	//�B�F�p�����[�^�[XML�I�u�W�F�N�g�������ďo���B
            elmRec = createPrmXML(respRecBean, document, elmRec);
        	
            //�C�F�B�߂�l��ݒ肵�A�ԋp����B
            elmTable.appendChild(elmRec);
        }

		return elmTable;
	}

	/**
	 * �p�����[�^XML�I�u�W�F�N�g����
	 * @param responseData : ���X�|���X�f�[�^�ێ��N���X
	 * @param document : �h�L�������g
	 * @param elmRec : ���R�[�h�̃G�������g
	 * @return �p�����[�^�I�u�W�F�N�g�̃G�������g
	 */
	private Element createPrmXML(RequestResponsRowBean responseData, Document document, Element elmRec){
		
        for (int i=0;i < responseData.getItemList().size();i++){
        	
        	//�@�����F���X�|���X�f�[�^���A�p�����[�^�[�f�[�^�擾
        	RequestResponsFieldBean respPrmRow = (RequestResponsFieldBean)responseData.getItemList().get(i);
        
        	//�A�F�@���擾�����f�[�^���AXML�����ɒǉ�����B
        	elmRec.setAttribute(respPrmRow.getName(), respPrmRow.getValue());
        }
        
        return elmRec;
	}

}
	