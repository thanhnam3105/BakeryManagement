package jp.co.blueflag.shisaquick.srv.manager;

import org.springframework.beans.factory.CannotLoadBeanClassException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;

import jp.co.blueflag.shisaquick.srv.base.DataCheck;
import jp.co.blueflag.shisaquick.srv.base.ObjectBase;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsKindBean;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ConstManager;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;
import jp.co.blueflag.shisaquick.srv.common.ExceptionManager.ExceptionKind;


/**
 * 
 * �f�[�^�`�F�b�N�Ǘ�
 *  : �@�\ID���Ƃ́@�e�f�[�^�`�F�b�N���Ǘ�����B
 *
 */
public class DataCheckManager extends ObjectBase{

	//�f�[�^�`�F�b�N
	private DataCheck targetDataCheck = null;
	//���[�U�[���Ǘ�
	private UserInfoData userInfoData = null;
	
	/**
	 * �f�[�^�`�F�b�N�Ǘ��p�R���X�g���N�^ : �C���X�^���X�𐶐�����
	 */
	public DataCheckManager() {
		//���N���X�̃R���X�g���N�^
		super();

		//�C���X�^���X�ϐ��̏�����
		targetDataCheck = null;
		userInfoData = null;
		
	}
	
	/**
	 * �f�[�^�`�F�b�N�Ǘ�
	 *  : �@�\ID���Ƃ́@�e�f�[�^�`�F�b�N�������Ǘ�����B
	 * @param requestData : �@�\ID���N�G�X�g�f�[�^
	 * @param userInfoData : ���[�U�[���
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	public void DataCheck(
			RequestResponsKindBean requestData
			,UserInfoData _userInfoData
		)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		//���[�U�[���ޔ�
		userInfoData = _userInfoData;
	
		ClassPathResource cls_rsr = null;
		XmlBeanFactory factory = null;
		String strKinoID = null;
		
		try {
			
			//�@�F�@�\ID�̎擾
			strKinoID = requestData.getID();
			
			//�A�萔�Ǘ��iConstManager�j�׽���A�hAPPCONTEXT_NM_DATACHECK�h�̒l���擾
			cls_rsr = new ClassPathResource(ConstManager.APPCONTEXT_NM_DATACHECK);
			factory = new XmlBeanFactory(cls_rsr);

			//�B�F�A�擾�l�ŃR���e�i�𐶐�
			targetDataCheck = (DataCheck)factory.getBean(strKinoID);
			
			//�C�F���N�G�X�g�f�[�^�ێ��������ɂ��āA�����otargetDataCheck��execDataCheck���\�b�h���Ăяo��
			targetDataCheck.execDataCheck(requestData, userInfoData);

		//�R���e�i�����݂��Ȃ������ꍇ
		} catch (NoSuchBeanDefinitionException e){
			//�������Ȃ�
			
		//�C���X�^���X�����݂��Ȃ��ꍇException���X���[����
		} catch (CannotLoadBeanClassException e){
			
			//�x����O��Throw����B
			em.ThrowException(ExceptionKind.�x��Exception, "W000300", strKinoID, "", "");

			
		} catch (Exception e){
		
			em.ThrowException(e, "�f�[�^�`�F�b�N�Ǘ��Ɏ��s���܂����B");
			
		} finally {
			//�N���X�̔j��
			targetDataCheck = null;
			userInfoData = null;
			cls_rsr = null;
			factory = null;
			targetDataCheck = null;
			
		}

	}

}
