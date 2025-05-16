package jp.co.blueflag.shisaquick.srv.jnlp;

import java.io.File;
import java.io.FileOutputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import jp.co.blueflag.shisaquick.srv.base.LogicBase;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsFieldBean;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsKindBean;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsRowBean;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsTableBean;
import jp.co.blueflag.shisaquick.srv.base.ResponsData;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ConstManager;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;

/**
 * 
 * JNLP�쐬
 *  : �����o�ϐ��̒l����ɁAJNLP�t�@�C�����쐬����
 *  
 * @author TT.k-katayama
 * @since  2009/03/28
 *
 */
public class JNLPCreateLogic extends LogicBase {

	private ResponsData resData = null;
	
	
	/**
	 * �R���X�g���N�^
	 * @param requestData : ���N�G�X�g�f�[�^
	 *  
	 * @author TT.k-katayama
	 * @since  2009/03/28
	 */
	public JNLPCreateLogic() {
		//���N���X�̃R���X�g���N�^
		super();

		//�C���X�^���X�ϐ��̏�����
		resData = null;
	}

	/**
	 * ���W�b�N����
	 * @param reqData : �@�\���N�G�X�g�f�[�^
	 * @param userInfoData : ���[�U�[���
	 * @return ���X�|���X�f�[�^�i�@�\�j
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	public RequestResponsKindBean ExecLogic(
			RequestResponsKindBean reqData
			,UserInfoData _userInfoData
			) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		//���[�U�[���ޔ�
		userInfoData = _userInfoData;

		try {
			resData = new ResponsData("");
			String strFileName = "";

			// �@�FJNLP�쐬���\�b�h���Ăяo���A�쐬���ꂽ�t�@�C���p�X���擾����B
			strFileName = this.CreateJNLP(reqData);
						
			// �A:RequestResponsBean�ɒl���i�[���A�ԋp����B
			
			// �@�\ID�̐ݒ�
			String strKinoId = reqData.getID();
			resData.CurrentKindID(strKinoId);

			// �e�[�u�����̐ݒ�
			String strTableNm = ((RequestResponsTableBean) reqData.GetItem(0)).getID();
			resData.CurrentTableName(strTableNm);
			
			// �p�����[�^�i�[�AResponsData�ɃZ�b�g
			this.storageGroupCmb(strFileName);
			
		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {
			if (searchDB != null) {
				//�Z�b�V�����̃N���[�Y
				searchDB.Close();
				searchDB = null;
			}
		}

		return (RequestResponsKindBean) resData.GetItem(0);
	}	
	
	/**
	 * JNLP�ǂݍ���
	 *  : ��ƂȂ�JNLP�t�@�C���̓ǂݍ��݂��s��
	 * @param strFileName : �ǂݍ��ݐ�t�@�C����
	 * @return Document�I�u�W�F�N�g
	 * 
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 *  
	 * @author TT.k-katayama
	 * @since  2009/03/28
	 */
	private Document ReadJNLP(String strFileName) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		Document doc = null;
		
		try {
			//�v���W�F�N�g�t�H���_�̃p�X���擾
			String strRealPath = ConstManager.getRootAppPath();
			//XML��ǂݍ���
			// �h�L�������g�r���_�[�t�@�N�g���𐶐�
			DocumentBuilderFactory dbfactory = DocumentBuilderFactory.newInstance();
			// �h�L�������g�r���_�[�𐶐�
			DocumentBuilder builder = dbfactory.newDocumentBuilder();
			// �p�[�X�����s����Document�I�u�W�F�N�g���擾
			doc = builder.parse(strRealPath + "/jws/" + strFileName);
			
		} catch (Exception e) {
			this.em.ThrowException(e, "JNLP�t�@�C���̓ǂݍ��݂Ɏ��s���܂����B");
		} finally {
		}
		
		return doc;
	}
	
	/**
	 * JNLP�쐬
	 *  : JNLP�t�@�C�����쐬����
	 * @param reqData : �@�\���N�G�X�g�f�[�^
	 * @return �쐬���ꂽJNLP�t�@�C���p�X
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 *  
	 * @author TT.k-katayama
	 * @since  2009/03/28
	 */
	public String CreateJNLP(RequestResponsKindBean reqData) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		String strRetJnlpPath = "";

		//�@ : �@�\���N�G�X�g�f�[�^����͂��AJNLP�t�@�C�����쐬����
		try {
			
			// 1)JNLP�ǂݍ��݃��\�b�h���g�p���A�e���v���[�g�pJNLP�t�@�C����ǂݍ��ށitemplate.jnlp�j
			Document document = this.ReadJNLP("template.jnlp");
			
			// ���[�g�v�f���擾
			Element rootElement = document.getDocumentElement();
			// application-desc�v�f�̃��X�g���擾
			NodeList rootList = rootElement.getElementsByTagName("application-desc");
			// argument�v�f���擾
			Element argElement = (Element)rootList.item(0);
	        
			// 2)�@�\���N�G�X�g�f�[�^�̒l���擾����B(���@�\ID : SA550I)

			String strUserId = null;
			String strTrialCD = null;
			String strMode = null;

			// ���[�UID
			strUserId = reqData.getFieldVale(0, 0, "id_user");
			// ����R�[�h
			strTrialCD = reqData.getFieldVale(0, 0, "no_shisaku");
			// ���[�h
			strMode = reqData.getFieldVale(0, 0, "mode");
			
			// 3)JNLP�t�@�C�����̃f�[�^�ɉ�͂����@�\���N�G�X�g�f�[�^�̒l���i�[����B
			argElement.getElementsByTagName("argument").item(0).getFirstChild().setNodeValue(strUserId);
			argElement.getElementsByTagName("argument").item(1).getFirstChild().setNodeValue(strTrialCD);
			argElement.getElementsByTagName("argument").item(2).getFirstChild().setNodeValue(strMode);
			
			// 4)jws�t�H���_���ɐV�KJNLP�t�@�C�����쐬����B(���� : new_ + ���[�UID + .jnlp)
			
			// �v���W�F�N�g�t�H���_�p�X
			String strRealPath = ConstManager.getRootAppPath();
			// �V�KJNLP�t�@�C����
			String strNewJnlpName = "new_" + strUserId + ".jnlp";
			
			// �V�KJNLP�t�@�C���쐬
			TransformerFactory transFactory = TransformerFactory.newInstance();
			Transformer transformer = transFactory.newTransformer();
			DOMSource source = new DOMSource(document);
			File newXML = new File(strRealPath + "/jws/" + strNewJnlpName);
			FileOutputStream os = new FileOutputStream(newXML);
			StreamResult result = new StreamResult(os); 
			transformer.transform(source, result);
			
			// �쐬����JNLP�t�@�C���p�X��ݒ�
			strRetJnlpPath = strNewJnlpName; 
			
		} catch (Exception e) {
			this.em.ThrowException(e, "JNLP���쐬�ł��܂���ł����B");
		} finally {
		}
		
		//�A : �쐬�����t�@�C���̃p�X��strJNLPpath�Ƃ��ĕԋp����
		return strRetJnlpPath;
	}

	/**
	 * �R���{�p�F�O���[�v�p�����[�^�[�i�[
	 *  : �O���[�v�R���{�{�b�N�X�������X�|���X�f�[�^�֊i�[����B
	 * @param strFileName : �������ʏ�񃊃X�g
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	public void storageGroupCmb(String strFileName) throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		RequestResponsRowBean resBean = null;

		try {
			
			resBean = new RequestResponsRowBean();
			resBean.setID("rec");
			
			//�������ʂ̊i�[
			resBean.getItemList().add(new RequestResponsFieldBean("flg_return", "true"));
			resBean.getItemList().add(new RequestResponsFieldBean("msg_error", ""));
			resBean.getItemList().add(new RequestResponsFieldBean("no_errmsg", ""));
			resBean.getItemList().add(new RequestResponsFieldBean("nm_class", ""));
			resBean.getItemList().add(new RequestResponsFieldBean("cd_error", ""));
			resBean.getItemList().add(new RequestResponsFieldBean("msg_system", ""));

			//JNLP�t�@�C���p�X�i�[
			resBean.getItemList().add(new RequestResponsFieldBean("jnlp_path", strFileName));

			//���N�G�X�gBeen�Ɏ擾���ʂ�ǉ�
			resData.addRow(resBean);
			
		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {
			//�ϐ��̍폜
			resBean = null;
		}
	}
	
}
