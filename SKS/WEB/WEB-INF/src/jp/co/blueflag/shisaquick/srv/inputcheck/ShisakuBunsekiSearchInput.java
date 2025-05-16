package jp.co.blueflag.shisaquick.srv.inputcheck;

import jp.co.blueflag.shisaquick.srv.base.InputCheck;
import jp.co.blueflag.shisaquick.srv.base.RequestData;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;

/**
 * 
 * �yJW720�z ����\���̓f�[�^�m�F��ʃ��N�G�X�g���� �C���v�b�g�`�F�b�N
 *
 */
public class ShisakuBunsekiSearchInput extends InputCheck {

	/**
	 * �R���X�g���N�^
	 *  : ����f�[�^��� �����\�����C���v�b�g�`�F�b�N�p�R���X�g���N�^
	 */
	public ShisakuBunsekiSearchInput() {
		//���N���X�̃R���X�g���N�^
		super();

	}

	/**
	 * �C���v�b�g�`�F�b�N�Ǘ�
	 *  : �e�f�[�^�`�F�b�N�������Ǘ�����B
	 * @param requestData : ���N�G�X�g�f�[�^
	 * @param userInfoData : ���[�U�[���
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	public void execInputCheck(
			RequestData checkData
			,UserInfoData _userInfoData
			) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		//���[�U�[���ޔ�
		super.userInfoData = _userInfoData;

		try {
			//USERINFO�̃C���v�b�g�`�F�b�N���s���B
			super.userInfoCheck(checkData);

			//SA590�̃C���v�b�g�`�F�b�N���s���B
//			henkouSearchKeyCheck(checkData);

			//SA510�̃C���v�b�g�`�F�b�N���s���B
//			genryoSearchKeyCheck(checkData);
			//SA860�̃C���v�b�g�`�F�b�N���s���B
			genryoSearchKeyCheck(checkData);

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {
			
		}
	}

	/**
	 * ���͒l�ύX���������l�`�F�b�N
	 *  : SA590�̃C���v�b�g�`�F�b�N���s���B
	 * @param requestData : ���N�G�X�g�f�[�^
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private void henkouSearchKeyCheck(RequestData checkData) throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		try {
			for( int i=0; i<checkData.GetRecCnt("SA590", 0); i++ ){
				//��ЃR�[�h�̕K�{���̓`�F�b�N
			    super.hissuInputCheck(checkData.GetValueStr("SA590", 0, i, "cd_kaisha"),"��ЃR�[�h");
				//�����R�[�h�̕K�{���̓`�F�b�N
			    super.hissuInputCheck(checkData.GetValueStr("SA590", 0, i, "cd_busho"),"�����R�[�h");
				//�����R�[�h�̕K�{���̓`�F�b�N
			    super.hissuInputCheck(checkData.GetValueStr("SA590", 0, i, "cd_genryo"),"�����R�[�h");
				//�������̕K�{���̓`�F�b�N
			    super.hissuInputCheck(checkData.GetValueStr("SA590", 0, i, "nm_genryo"),"������");
			    //�������̓��͌����`�F�b�N
			    super.sizeHalfFullLengthCheck(checkData.GetValueStr("SA590", 0, i, "nm_genryo"),"������",60,30);
			    //�P���̕K�{���̓`�F�b�N
			    super.hissuInputCheck(checkData.GetValueStr("SA590", 0, i, "tanka"),"�P��");
				//�����̕K�{���̓`�F�b�N
			    super.hissuInputCheck(checkData.GetValueStr("SA590", 0, i, "budomari"),"����");
				//���ܗL���̕K�{���̓`�F�b�N	
			    super.hissuInputCheck(checkData.GetValueStr("SA590", 0, i, "ritu_abura"),"���ܗL��");
				//�|�_�̕K�{���̓`�F�b�N
			    super.hissuInputCheck(checkData.GetValueStr("SA590", 0, i, "ritu_sakusan"),"�|�_");
				//�H���̕K�{���̓`�F�b�N
			    super.hissuInputCheck(checkData.GetValueStr("SA590", 0, i, "ritu_shokuen"),"�H��");
				//���_�̕K�{���̓`�F�b�N
			    super.hissuInputCheck(checkData.GetValueStr("SA590", 0, i, "ritu_sousan"),"���_");
			}
			
		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {
			
		}
	}

	/**
	 * ���͌������������l�`�F�b�N
	 *  : SA860�̃C���v�b�g�`�F�b�N���s���B
	 * @param requestData : ���N�G�X�g�f�[�^
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private void genryoSearchKeyCheck(RequestData checkData) throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		try {
			//��ЃR�[�h�̕K�{���̓`�F�b�N
		    super.hissuInputCheck(checkData.GetValueStr("SA860", 0, 0, "cd_kaisha"),"��ЃR�[�h");
			//�����R�[�h�̕K�{���̓`�F�b�N
//		    super.hissuInputCheck(checkData.GetValueStr("SA510", 0, 0, "cd_busho"),"�����R�[�h");
			//�����R�[�h�̓��͌����`�F�b�N
		    super.sizeCheckLen(checkData.GetValueStr("SA860", 0, 0, "cd_genryo"),"�����R�[�h",11);
			//�������̓��͌����`�F�b�N
		    super.sizeHalfFullLengthCheck(checkData.GetValueStr("SA860", 0, 0, "nm_genryo"),"��������",30,60);
			//�I���y�[�W�ԍ��̕K�{���̓`�F�b�N
		    super.hissuInputCheck(checkData.GetValueStr("SA860", 0, 0, "num_selectRow"),"�I���y�[�W�ԍ�");
			
		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {
			
		}
	}

}
