package jp.co.blueflag.shisaquick.srv.manager;

import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;

import jp.co.blueflag.shisaquick.srv.base.RequestData;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsKindBean;
import jp.co.blueflag.shisaquick.srv.base.ResponsData;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ConstManager;
import jp.co.blueflag.shisaquick.srv.common.ExceptionBase;
import jp.co.blueflag.shisaquick.srv.common.ExceptionManager;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;
import jp.co.blueflag.shisaquick.srv.common.ConstManager.Category;

/**
 * 
 * ���N�G�X�g�R���g���[��
 *  : ��M����XML�@�\ID���ƂɋƖ����W�b�N���Ǘ�����B
 * @author TT.furuta
 * @since  2009/03/25
 */
public class RequestContoroler {

	//���X�|���X�f�[�^
	private ResponsData responseData = null;
	//�@�\���N�G�X�g�f�[�^
	private RequestResponsKindBean reqKinoData = null;
	//�@�\���X�|���X�f�[�^
	private RequestResponsKindBean respKinoData = null;
	//���[�U�[���Ǘ�
	private UserInfoData userInfoData = null;
	
	/**
	 * �R���X�g���N�^
	 *  : ���N�G�X�g�R���g���[���p�R���X�g���N�^
	 */
	public RequestContoroler() {
		
		//�C���X�^���X�ϐ��̏�����
		responseData = null;
		reqKinoData = null;
		respKinoData = null;
		userInfoData = null;
	}
	
	/**
	 * ���C���R���g���[���[
	 *  : �e���W�b�N�Ǘ��R���g���[���[
	 * @param  requestData �F���N�G�X�g�f�[�^
	 * @param userInfoData : ���[�U�[���
	 * @param constManager : �ݒ���
	 * @return responseData�F���X�|���X�f�[�^
	 */
	public ResponsData mainContorol(
			RequestData requestData
			,UserInfoData _userInfoData
			) {

		//���[�U�[���ޔ�
		userInfoData = _userInfoData;
		
		ClassPathResource cls_rsr = null;
		XmlBeanFactory factory = null;
		DataCheckManager dcManager = null;
		LogicManager lgManager = null;
		
		try {
						
			//���X�|���X�f�[�^�̃C���X�^���X����
			responseData = new ResponsData(requestData.getID());
			
			//DI�ݒ�t�@�C���Ǐo
			cls_rsr = new ClassPathResource(ConstManager.APPCONTEXT_NM_MANAGER);
			factory = new XmlBeanFactory(cls_rsr);

			for (int i=0;i < requestData.GetKindCnt();i++){
			
				//�@�FrequestData���@�\���N�G�X�g�iRequestResponsBean�j�𒊏o
				reqKinoData = (RequestResponsKindBean) requestData.getItemList().get(i);
				
				//�@�\ID��USERINFO�ȊO�̏ꍇ�݈̂ȉ����s
				if (!reqKinoData.getID().equals("USERINFO")){
						
					//�A�F�f�[�^�`�F�b�N���s��
					dcManager = (DataCheckManager)factory.getBean("DI_DataCheckManager");
					
					try {
						//�@�\���N�G�X�g�iRequestResponsBean�j�������ɂ��āADataCheckMahager�׽DataCheckҿ�Ă��Ăяo���B
						dcManager.DataCheck(reqKinoData, userInfoData);
						
					} catch (ExceptionWaning e) {
						//�������Ȃ�
						e = null;
						
					}
					
					//�B�F�Ɩ����W�b�N�����s����
					lgManager = (LogicManager)factory.getBean("DI_LogicManager");
					
					//�@�\���N�G�X�g�iRequestResponsBean�j�������ɂ��āALogicManager�׽callLogic���\�b�g���Ăяo���B
					respKinoData = lgManager.callLogic(reqKinoData, userInfoData);
					
					//�߂�l�������oresponseData��AddKindҿ�ĂŒǉ�����
					responseData.AddKind(respKinoData);
				}
			}
			
		} catch (Exception e) {
			
			//�D�F�G�N�Z�v�V�����̏������s��
			responseData = exceptionProc(e);
			e = null;
			
		} finally {
			//�N���X�̔j��
			cls_rsr = null;
			factory = null;
			dcManager = null;
			lgManager = null;
			
		}
		//�C�F�������ʂ�ԋp����
		return responseData;

	}
	
	/**
	 * ����߼��ڽ��ݽ�ێ��ϊ�
	 *  : �ُ���̒��o�E�]�@���s���B
	 * @param ex : ��O�N���X
	 */
	private ResponsData exceptionProc(Exception ex) {
		
		//���R�R�ŃL���b�`�����Exception�͉��L�Q��̂�
		//�P�D�C���v�b�g�`�F�b�N/�Z�b�V�����`�F�b�N�Ŗ����I�ɔ����������G���[
		//�Q�DAjaxServlet�Ŕ��������A�\�����ʏ�Q

		ExceptionManager eManager = null;
		
		try{
			//Exception�̎�ނ𔻒�
			ExceptionBase e = null;
			if ( ex.getClass().equals(ExceptionSystem.class) ) {
				e = (ExceptionBase) ex;
	
			} else if ( ex.getClass().equals(ExceptionUser.class) ) {
				e = (ExceptionBase) ex;
	
			} else if ( ex.getClass().equals(ExceptionWaning.class) ) {
				e = (ExceptionBase) ex;
	
			} else {
				//�Q�D�\�����ʏ�Q���AExceptionSystem�ɕϊ�����
				eManager = new ExceptionManager(this.getClass().getName());
				e = eManager.cnvException(ex, "RequestContoroler�ŁA�V�X�e����O���������܂����B");
				
			}
			
			//�@�F��O�N���X����Ƀ��X�|���X�ێ��𐶐����A�����orespData�Ɋi�[����
			RequestResponsKindBean KindBean = new RequestResponsKindBean();
			//�@�\ID��ݒ肷��
			KindBean.setID(reqKinoData.getID());
			
			//��������ݒ肷��B
			
			//������
			KindBean.addFieldVale("table", "rec", "flg_return", "false");
			//���b�Z�[�W 
			KindBean.addFieldVale("table", "rec", "msg_error", e.getUserMsg());
			//�������� 
			KindBean.addFieldVale("table", "rec", "nm_class", e.getClassName());
			//���b�Z�[�W�ԍ� 
			KindBean.addFieldVale("table", "rec", "no_errmsg", e.getMsgNumber());
			//�G���[�R�[�h 
			KindBean.addFieldVale("table", "rec", "cd_error", e.getSystemErrorCD());
			//�V�X�e�����b�Z�[�W 
			KindBean.addFieldVale("table", "rec", "msg_system", e.getSystemErrorMsg());
			//�f�o�b�N���[�h
			KindBean.addFieldVale("table", "rec", "debuglevel", ConstManager.getConstValue(Category.�ݒ�, "CONST_DEBUG_LEVEL"));
			
			//�s�ǉ�
			responseData.AddKind(KindBean);

		} catch (Exception exc){
			//���s���ԋp���ʂ�������
			responseData = null;
			
		}finally{
			//�N���X�̔j��
			eManager = null;
			
		}
		return responseData;
	}

}
