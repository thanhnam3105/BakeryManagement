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
				
				String[] strShisaku = strShisakuNo.split("-");
				
				//����No�t�H�[�}�b�g�`�F�b�N���s���B
				arrayCheck(strShisakuNo, 3, "����No");

				if (!(strShisaku[0].toString().length() <= 10 && strShisaku[0].toString().length() >= 1 && 
						strShisaku[1].toString().length() <= 2 && strShisaku[1].toString().length() >= 1 && 
						strShisaku[2].toString().length() <= 3 && strShisaku[2].toString().length() >= 1)) {
					//�G���[���X���[����B
					em.ThrowException(ExceptionKind.���Exception, "E000206", "����No", "", "");
				}
			}
			
			//���@No�������ΏۂƂ���ꍇ
			if (checkData.GetValueStr("SA200", 0, 0, "kbn_joken1").equals("1")) {
				//���@No�̎擾
				String strSeihoNo = checkData.GetValueStr("SA200", 0, 0, "no_seiho");
				
			    //���@No�����͂���Ă���ꍇ
				if (strSeihoNo != null && !strSeihoNo.equals("")) {
					
					String[] strSeiho = strSeihoNo.split("-");
	
					//���@No�t�H�[�}�b�g�`�F�b�N���s���B
					arrayCheck(strSeihoNo, 4, "���@No");
				    
					if (!(strSeiho[0].toString().length() == 4 && 
							strSeiho[1].toString().length() == 3 && 
							strSeiho[2].toString().length() == 2 && 
							strSeiho[3].toString().length() == 4)) {
						//�G���[���X���[����B
						em.ThrowException(ExceptionKind.���Exception, "E000206", "���@No", "", "");
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
