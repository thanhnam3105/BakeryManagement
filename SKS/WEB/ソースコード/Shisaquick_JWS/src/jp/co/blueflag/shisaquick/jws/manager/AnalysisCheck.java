package jp.co.blueflag.shisaquick.jws.manager;

import jp.co.blueflag.shisaquick.jws.base.*;
import jp.co.blueflag.shisaquick.jws.common.*;


/**
 * 
 * ���͒l�m�F�N���X
 *
 */
public class AnalysisCheck {

	private TrialTblData midtCheck;				//��r�p�̎���e�[�u���f�[�^
	private MaterialData mtmsCheck;			//��r�p�̌����}�X�^�f�[�^
	private MessageCtrl msdCheck;				//��r���e��\��
	
	/**
	 * ���͒l�m�F �R���X�g���N�^
	 */
	public AnalysisCheck() {
		this.midtCheck = null;
		this.mtmsCheck = null;
		this.msdCheck = null;
	}
	
	/**
	 * ���͒l��r
	 *  : ����f�[�^�ƌ����f�[�^�̕��͒l���m�F����
	 */
	public void CheckHikaku() {
		
	}
	
	/**
	 * ��r���ʕ\��
	 *  : ����e�[�u���f�[�^�ƌ����}�X�^�f�[�^�̔�r���ʂ�\��
	 */
	public void PrintHikaku() {
		
	}

	/**
	 * ����e�[�u���f�[�^ �Q�b�^�[
	 * @return midtCheck : ����e�[�u���f�[�^�̒l��Ԃ�
	 */
	public TrialTblData getMidtCheck() {
		return midtCheck;
	}
	/**
	 * ����e�[�u���f�[�^ �Z�b�^�[
	 * @param _midtCheck : ����e�[�u���f�[�^�̒l���i�[����
	 */
	public void setMidtCheck(TrialTblData _midtCheck) {
		this.midtCheck = _midtCheck;
	}

	/**
	 * �����f�[�^ �Q�b�^�[
	 * @return mtmsCheck : �����}�X�^�f�[�^�̒l��Ԃ�
	 */
	public MaterialData getMtmsCheck() {
		return mtmsCheck;
	}
	/**
	 * �����f�[�^ �Z�b�^�[
	 * @param _mtmsCheck : �����f�[�^�̒l���i�[����
	 */
	public void setMtmsCheck(MaterialData _mtmsCheck) {
		this.mtmsCheck = _mtmsCheck;
	}

	/**
	 * ��r���e �Q�b�^�[
	 * @return msdCheck : ��r���e�̒l��Ԃ�
	 */
	public MessageCtrl getMsdCheck() {
		return msdCheck;
	}
	/**
	 * ��r���e �Z�b�^�[
	 * @param _msdCheck : ��r���e�̒l���i�[����
	 */
	public void setMsdCheck(MessageCtrl _msdCheck) {
		this.msdCheck = _msdCheck;
	}
	
}
