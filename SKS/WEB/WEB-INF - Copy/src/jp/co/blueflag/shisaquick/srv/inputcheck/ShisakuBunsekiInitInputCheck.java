package jp.co.blueflag.shisaquick.srv.inputcheck;

import jp.co.blueflag.shisaquick.srv.base.InputCheck;
import jp.co.blueflag.shisaquick.srv.base.RequestData;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;

/**
 * 
 * �yJW710�z ����\���̓f�[�^�m�F��ʃ��N�G�X�g�������� �C���v�b�g�`�F�b�N
 *
 */
public class ShisakuBunsekiInitInputCheck extends InputCheck {

	/**
	 * �R���X�g���N�^
	 *  : ����f�[�^��� �����\�����C���v�b�g�`�F�b�N�p�R���X�g���N�^
	 */
	public ShisakuBunsekiInitInputCheck() {
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

			//SA570�̃C���v�b�g�`�F�b�N���s���B
			shisakulistSearchKeyCheck(checkData);

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
	 * ���͒l�ύX���������l�`�F�b�N
	 *  : SA570�̃C���v�b�g�`�F�b�N���s���B
	 * @param requestData : ���N�G�X�g�f�[�^
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private void shisakulistSearchKeyCheck(RequestData checkData) throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		try {
			for( int i=0; i<checkData.GetRecCnt("SA570", 0); i++ ){
				//��ЃR�[�h�̕K�{���̓`�F�b�N
			    super.hissuInputCheck(checkData.GetValueStr("SA570", 0, i, "cd_kaisha"),"��ЃR�[�h");
				//�����R�[�h�̕K�{���̓`�F�b�N
			    super.hissuInputCheck(checkData.GetValueStr("SA570", 0, i, "cd_busho"),"�����R�[�h");
				//�����R�[�h�̕K�{���̓`�F�b�N
			    super.hissuInputCheck(checkData.GetValueStr("SA570", 0, i, "cd_genryo"),"�����R�[�h");
				//�I���y�[�W�ԍ��̕K�{���̓`�F�b�N
			    super.hissuInputCheck(checkData.GetValueStr("SA570", 0, i, "num_selectRow"),"�I���y�[�W�ԍ�");
			}
			
		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {
			
		}
	}

}
