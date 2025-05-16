package jp.co.blueflag.shisaquick.srv.inputcheck_genka;

import jp.co.blueflag.shisaquick.srv.base.InputCheck;
import jp.co.blueflag.shisaquick.srv.base.RequestData;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;
import jp.co.blueflag.shisaquick.srv.common.ExceptionManager.ExceptionKind;

public class RGEN2150_inputcheck extends InputCheck {

	/**
	 * �yQP@00342�z�R���X�g���N�^
	 *  : �������Z�f�[�^�ꗗ��� �����{�^���������C���v�b�g�`�F�b�N�p�R���X�g���N�^
	 */
	public RGEN2150_inputcheck() {
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

			//FGEN2150�̃C���v�b�g�`�F�b�N���s���B
			shisakuListSearchCheck(checkData);
		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {
			
		}
	}

	/**
	 * �������Z�f�[�^�ꗗ���������C���v�b�g�`�F�b�N
	 *  : FGEN2150�̃C���v�b�g�`�F�b�N���s���B
	 * @param requestData : ���N�G�X�g�f�[�^
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private void shisakuListSearchCheck(RequestData checkData) throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		try {
			//����No�̎擾
			String strShisakuNo = checkData.GetValueStr("FGEN2150", 0, 0, "no_shisaku");
			
		    //����No�����͂���Ă���ꍇ
			if (strShisakuNo != null && !strShisakuNo.equals("")) {
				
				String[] strShisaku = strShisakuNo.split("-",-1);
				
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
				//�Ј�CD�ƔN�ƒǔԂ̏ꍇ
				if (strShisaku.length == 4){
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
					//�}�Ԃ��R���ȏ�̏ꍇ�̓G���[
					if ((strShisaku[3].toString().length() > 3)) {
						// ���͌����s�����X���[����B
						em.ThrowException(ExceptionKind.���Exception,"E000212","����CD-�}��","3","");
					}
				}
				//5�ȏ�̗v�f������ꍇ�̓G���[
				if (strShisaku.length >= 5){
					//���������s�����X���[
					em.ThrowException(ExceptionKind.���Exception,"E000010","����No","","");
				}
			}

			//���얼�̓��͌����`�F�b�N���s��
			super.sizeCheckLen(checkData.GetValueStr("FGEN2150", 0, 0, "nm_shisaku"), "���얼", 100);
			
			//���Z����From�̓��t�`�F�b�N���s��
			String from = toString(checkData.GetValueStr("FGEN2150", 0, 0, "hi_kizitu_from"));
			if(from.equals("")){
				
			}
			else{
				super.dateCheck(from, "���Z�����i�J�n���j");
			}
			
			//���Z����To�̓��t�`�F�b�N���s��
			String to = toString(checkData.GetValueStr("FGEN2150", 0, 0, "hi_kizitu_to"));
			if(to.equals("")){
				
			}
			else{
				super.dateCheck(to, "���Z�����i�I�����j");
			}
			
		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {
			
		}
	}
}
