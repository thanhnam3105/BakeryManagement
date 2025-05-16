package jp.co.blueflag.shisaquick.srv.manager;

import org.springframework.beans.factory.CannotLoadBeanClassException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;

import jp.co.blueflag.shisaquick.srv.base.InputCheck;
import jp.co.blueflag.shisaquick.srv.base.ObjectBase;
import jp.co.blueflag.shisaquick.srv.base.RequestData;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ConstManager;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;
import jp.co.blueflag.shisaquick.srv.common.ExceptionManager.ExceptionKind;

/**
 * 
 * �C���v�b�g�`�F�b�N�Ǘ�
 *  : XMLID���Ƃ́@�e�C���v�b�g�`�F�b�N���Ǘ�����B
 * @author TT.furuta
 * @since  2009/03/25
 */
public class InputCheckManager extends ObjectBase{

	//�C���v�b�g�`�F�b�N�N���X
	private InputCheck inputCheck = null;
	//���[�U�[���Ǘ�
	private UserInfoData userInfoData = null;
	
	/**
	 * �R���X�g���N�^
	 *  : �C���v�b�g�`�F�b�N�Ǘ��p�R���X�g���N�^
	 */
	public InputCheckManager() {
		//���N���X�̃R���X�g���N�^
		super();
		
		//�C���X�^���X�ϐ��̏�����
		inputCheck = null;
		userInfoData = null;
		
	}
	
	/**
	 * �C���v�b�g�`�F�b�N�Ǘ�
	 *  : XMLID���Ƃ́@�e�C���v�b�g�`�F�b�N�������Ǘ�����B
	 * @param reqData : ���N�G�X�g�f�[�^
	 * @param userInfoData : ���[�U�[���
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	public void inputCheckmanager(
			RequestData reqData
			,UserInfoData _userInfoData
			) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		//���[�U�[���ޔ�
		userInfoData = _userInfoData;
		
		ClassPathResource cls_rsr = null;
		XmlBeanFactory factory = null;
		String strXmlID = null;
		
		try {
			
			//�@�FXMLID�擾
			strXmlID = reqData.getID();
			
			//�A�萔�Ǘ��iConstManager�j�׽���A�hAPPCONTEXT_NM_INPUTCHECK�h�̒l���擾
			cls_rsr = new ClassPathResource(ConstManager.APPCONTEXT_NM_INPUTCHECK);
			factory = new XmlBeanFactory(cls_rsr);

			//�B�F�A�擾�l�ŃR���e�i�𐶐�
			inputCheck = (InputCheck)factory.getBean(strXmlID);
						
			//�C�F���N�G�X�g�f�[�^�ێ��������ɂ��āA�����oIpCheck��execInputCheck���\�b�h���Ăяo��
			inputCheck.execInputCheck(reqData, userInfoData);
		
		//�C���X�^���X�����݂��Ȃ��ꍇException���X���[����
		} catch (CannotLoadBeanClassException e){
			//�x����O��Throw����B
			em.ThrowException(ExceptionKind.�x��Exception, "W000200", strXmlID, "", "");

		}catch(NoSuchBeanDefinitionException e){
			//�x����O��Throw����B
			em.ThrowException(ExceptionKind.�x��Exception, "W000200", strXmlID, "", "");
			
		} catch (Exception e){
		
			em.ThrowException(e, "�C���v�b�g�`�F�b�N�Ǘ��Ɏ��s���܂����B");
			
		} finally {

			//�N���X�̔j��
			inputCheck = null;
			userInfoData = null;
			cls_rsr = null;
			factory = null;
			inputCheck = null;
			
		}
		
	}

}
