package jp.co.blueflag.shisaquick.srv.manager;

import org.springframework.beans.factory.CannotLoadBeanClassException;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;

import jp.co.blueflag.shisaquick.srv.base.LogicBase;
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
 * ���W�b�N�Ǘ�
 *  : �@�\ID���Ƃ́@�e�Ɩ����W�b�N���Ǘ�����B
 * @author TT.furuta
 * @since  2009/03/25
 */
public class LogicManager extends ObjectBase{
	
	//���X�|���X�f�[�^
	private RequestResponsKindBean responsData = null;
	//�Ώۃ��W�b�N�N���X
	private LogicBase targetLogic = null;
	//���[�U�[���Ǘ�
	private UserInfoData userInfoData = null;

	/**
	 * ���W�b�N�Ǘ��p�R���X�g���N�^ 
	 * : �C���X�^���X����
	 */
	public LogicManager() {
		//���N���X�̃R���X�g���N�^
		super();

		//�C���X�^���X�ϐ��̏�����
		responsData = null;
		targetLogic = null;
		userInfoData = null;
		
	}
	
	/**
	 * ���W�b�N�ďo
	 * �F�e�Ɩ����W�b�N���ďo���B
	 * @param requestData  : ���N�G�X�g�f�[�^
	 * @param userInfoData : ���[�U�[���
	 * @return responsData�F���X�|���X�f�[�^
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	public RequestResponsKindBean callLogic(
			RequestResponsKindBean requestData
			,UserInfoData _userInfoData
			) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		ClassPathResource cls_rsr = null;
		XmlBeanFactory factory = null;
		String strKinoID = null;

		//���[�U�[���ޔ�
		userInfoData = _userInfoData;
		
		try {
			//�@�F�@�\ID�擾
			strKinoID = requestData.getID();
			
			//�A�F�萔�Ǘ��iConstManager�j�׽���A�hAPPCONTEXT_NM_LOGIC�h�̒l���擾
			cls_rsr = new ClassPathResource(ConstManager.APPCONTEXT_NM_LOGIC);
			factory = new XmlBeanFactory(cls_rsr);

			//�B�F�A�擾�l�ŃR���e�i�𐶐�
			targetLogic = (LogicBase)factory.getBean(strKinoID);
						
			//�C�F���N�G�X�g�f�[�^�������ɂ��āA�����otargetLogic��execLogic���\�b�h���Ăяo��
			responsData = targetLogic.ExecLogic(requestData, userInfoData);
			
		//�C���X�^���X�����݂��Ȃ��ꍇException���X���[����
		} catch (CannotLoadBeanClassException e){
			//�x����O��Throw����B
			em.ThrowException(ExceptionKind.�x��Exception, "W000400", strKinoID, "", "");

		} catch (Exception e){
			em.ThrowException(e, "���W�b�N�Ǘ��Ɏ��s���܂����B");
			
		} finally {
			
			//�N���X�̔j��
			targetLogic = null;
			userInfoData = null;
			cls_rsr = null;
			factory = null;
			targetLogic = null;
			
		}
		return responsData;

	}

}
