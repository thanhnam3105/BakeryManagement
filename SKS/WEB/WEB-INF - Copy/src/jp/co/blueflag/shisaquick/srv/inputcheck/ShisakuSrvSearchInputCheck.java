package jp.co.blueflag.shisaquick.srv.inputcheck;

import jp.co.blueflag.shisaquick.srv.base.InputCheck;
import jp.co.blueflag.shisaquick.srv.base.RequestData;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;
import jp.co.blueflag.shisaquick.srv.common.ExceptionManager.ExceptionKind;

public class ShisakuSrvSearchInputCheck extends InputCheck {

	/**
	 * �R���X�g���N�^
	 *  : ����f�[�^�ꗗ��� �����{�^���������C���v�b�g�`�F�b�N�p�R���X�g���N�^
	 */
	public ShisakuSrvSearchInputCheck() {
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

			//SA200�̃C���v�b�g�`�F�b�N���s���B
			shisakuListSearchCheck(checkData);
		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {
			
		}
	}

	/**
	 * ����f�[�^�ꗗ���������C���v�b�g�`�F�b�N
	 *  : SA200�̃C���v�b�g�`�F�b�N���s���B
	 * @param requestData : ���N�G�X�g�f�[�^
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private void shisakuListSearchCheck(RequestData checkData) throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		try {
			//����No�̎擾
			String strShisakuNo = checkData.GetValueStr("SA200", 0, 0, "no_shisaku");
			
		    //����No�����͂���Ă���ꍇ
			if (strShisakuNo != null && !strShisakuNo.equals("")) {
				
				String[] strShisaku = strShisakuNo.split("-",-1);
				
				//2009/08/04 DEL �ۑ臂295�̑Ή�
//				//����No�t�H�[�}�b�g�`�F�b�N���s���B
//				arrayCheck(strShisakuNo, 3, "����No");

				//2009/08/04 UPD START �ۑ臂295�̑Ή�
//				if (!(strShisaku[0].toString().length() <= 10 && strShisaku[0].toString().length() >= 1 && 
//						strShisaku[1].toString().length() <= 2 && strShisaku[1].toString().length() >= 1 && 
//						strShisaku[2].toString().length() <= 3 && strShisaku[2].toString().length() >= 1)) {
//					//�G���[���X���[����B
//					em.ThrowException(ExceptionKind.���Exception, "E000206", "����No", "", "");
//				}
				//�Ј�CD�݂̂̏ꍇ
				if (strShisaku.length == 1){
					//�Ј��b�c��10���ȏ�̏ꍇ�̓G���[
					if ((strShisaku[0].toString().length() > 10)) {
						// ���͌����s�����X���[����B
						em.ThrowException(ExceptionKind.���Exception,"E000212","����CD-�Ј�CD","10","");
					}
				}
				//�Ј�CD�ƔN�݂̂̏ꍇ
				if (strShisaku.length == 2){
					//�Ј��b�c��10���ȏ�̏ꍇ�̓G���[
					if ((strShisaku[0].toString().length() > 10)) {
						// ���͌����s�����X���[����B
						em.ThrowException(ExceptionKind.���Exception,"E000212","����CD-�Ј�CD","10","");
					}
					//�N��2���ȏ�̏ꍇ�̓G���[
					if ((strShisaku[1].toString().length() > 2)) {
						// ���͌����s�����X���[����B
						em.ThrowException(ExceptionKind.���Exception,"E000212","����CD-�N","2","");
					}
				}
				//�Ј�CD�ƔN�ƒǔԂ̏ꍇ
				if (strShisaku.length == 3){
					//�Ј��b�c��10���ȏ�̏ꍇ�̓G���[
					if ((strShisaku[0].toString().length() > 10)) {
						// ���͌����s�����X���[����B
						em.ThrowException(ExceptionKind.���Exception,"E000212","����CD-�Ј�CD","10","");
					}
					//�N���Q���ȏ�̏ꍇ�̓G���[
					if ((strShisaku[1].toString().length() > 2)) {
						// ���͌����s�����X���[����B
						em.ThrowException(ExceptionKind.���Exception,"E000212","����CD-�N","2","");
					}
					//�ǔԂ��R���ȏ�̏ꍇ�̓G���[
					if ((strShisaku[2].toString().length() > 3)) {
						// ���͌����s�����X���[����B
						em.ThrowException(ExceptionKind.���Exception,"E000212","����CD-�ǔ�","3","");
					}
				}
				//4�ȏ�̗v�f������ꍇ�̓G���[
				if (strShisaku.length >= 4){
					//���������s�����X���[
					em.ThrowException(ExceptionKind.���Exception,"E000010","����No","","");
				}
				//2009/08/04 UPD END
			}
			
			//���@No�������ΏۂƂ���ꍇ
			if (checkData.GetValueStr("SA200", 0, 0, "kbn_joken1").equals("1")) {
				//���@No�̎擾
				String strSeihoNo = checkData.GetValueStr("SA200", 0, 0, "no_seiho");
				
			    //���@No�����͂���Ă���ꍇ
				if (strSeihoNo != null && !strSeihoNo.equals("")) {
					
					String[] strSeiho = strSeihoNo.split("-",-1);
	
					//���@No�t�H�[�}�b�g�`�F�b�N���s���B
//					arrayCheck(strSeihoNo, 4, "���@No");
				    
//					if (!(strSeiho[0].toString().length() == 4 && 
//							strSeiho[1].toString().length() == 3 && 
//							strSeiho[2].toString().length() == 2 && 
//							strSeiho[3].toString().length() == 4)) {
//						//�G���[���X���[����B
//						em.ThrowException(ExceptionKind.���Exception, "E000206", "���@No", "", "");
//					}
					//��ЃR�[�h�݂̂̏ꍇ
					if (strSeiho.length == 1){
						//��ЃR�[�h��4���ȏ��
						if ((strSeiho[0].toString().length() > 4)) {
							// ���͌����s�����X���[����B
							em.ThrowException(ExceptionKind.���Exception,"E000212","���@No-���CD","4","");
						}
					}
					//��ЃR�[�h�Ǝ�ʂ̏ꍇ
					if (strSeiho.length == 2){
						//��ЃR�[�h��4���ȏ�̏ꍇ
						if ((strSeiho[0].toString().length() > 4)) {
							// ���͌����s�����X���[����B
							em.ThrowException(ExceptionKind.���Exception,"E000212","���@No-���CD","4","");
						}
						//��ʂ�3���ȏ�̏ꍇ
						if ((strSeiho[1].toString().length() > 3)) {
							// ���͌����s�����X���[����B
							em.ThrowException(ExceptionKind.���Exception,"E000212","���@No-���","3","");
						}
					}
					//��ЃR�[�h�Ǝ�ʂƔN�̏ꍇ
					if (strSeiho.length == 3){
						//��ЃR�[�h��4���ȏ�̏ꍇ
						if ((strSeiho[0].toString().length() > 4)) {
							// ���͌����s�����X���[����B
							em.ThrowException(ExceptionKind.���Exception,"E000212","���@No-���CD","4","");
						}
						//��ʂ�3���ȏ�̏ꍇ
						if ((strSeiho[1].toString().length() > 3)) {
							// ���͌����s�����X���[����B
							em.ThrowException(ExceptionKind.���Exception,"E000212","���@No-���","3","");
						}
						//�N��2���ȏ�̏ꍇ
						if ((strSeiho[2].toString().length() > 2)) {
							// ���͌����s�����X���[����B
							em.ThrowException(ExceptionKind.���Exception,"E000212","���@No-�N","2","");
						}
					}
					//��ЃR�[�h�Ǝ�ʂƔN�ƒǔԂ̏ꍇ
					if (strSeiho.length == 4){
						//��ЃR�[�h��4���ȏ�̏ꍇ
						if ((strSeiho[0].toString().length() > 4)) {
							// ���͌����s�����X���[����B
							em.ThrowException(ExceptionKind.���Exception,"E000212","���@No-���CD","4","");
						}
						//��ʂ�3���ȏ�̏ꍇ
						if ((strSeiho[1].toString().length() > 3)) {
							// ���͌����s�����X���[����B
							em.ThrowException(ExceptionKind.���Exception,"E000212","���@No-���","3","");
						}
						//�N��2���ȏ�̏ꍇ
						if ((strSeiho[2].toString().length() > 2)) {
							// ���͌����s�����X���[����B
							em.ThrowException(ExceptionKind.���Exception,"E000212","���@No-�N","2","");
						}
						//�ǔԂ�4���ȏ�̏ꍇ
						if ((strSeiho[3].toString().length() > 4)) {
							// ���͌����s�����X���[����B
							em.ThrowException(ExceptionKind.���Exception,"E000212","���@No-�ǔ�","4","");
						}
					}
					//5�ȏ�̗v�f������ꍇ�̓G���[
					if (strSeiho.length >= 5){
						//���������s�����X���[
						em.ThrowException(ExceptionKind.���Exception,"E000010","���@No","","");
					}
				}
			}

			//���얼�̓��͌����`�F�b�N���s��
			super.sizeCheckLen(checkData.GetValueStr("SA200", 0, 0, "nm_shisaku"), "���얼", 100);
			//���i�̗p�r�̓��͌����`�F�b�N���s��
			super.sizeCheckLen(checkData.GetValueStr("SA200", 0, 0, "cd_youto"), "���i�̗p�r", 60);
			//���������̓��͌����`�F�b�N���s��
			super.sizeCheckLen(checkData.GetValueStr("SA200", 0, 0, "cd_genryo"), "��������", 60);
			//�L�[���[�h�̓��͌����`�F�b�N���s��
			super.sizeCheckLen(checkData.GetValueStr("SA200", 0, 0, "keyword"), "�L�[���[�h", checkData.GetValueStr("SA200", 0, 0, "keyword").length());
			
		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {
			
		}
	}
}
