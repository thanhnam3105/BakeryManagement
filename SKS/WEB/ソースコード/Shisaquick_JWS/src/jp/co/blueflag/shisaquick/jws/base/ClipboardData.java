package jp.co.blueflag.shisaquick.jws.base;

import java.util.ArrayList;

import jp.co.blueflag.shisaquick.jws.common.DataBase;

/**
 * 
 * �N���b�v�{�[�h�f�[�^�ێ�
 *  : �R�s�[�����f�[�^�Ǘ�����
 *
 */
public class ClipboardData extends DataBase {

	private String strClipboad;			//�����R�[�h�̃R�s�[���y�[�X�g�ɕK�v�ȃf�[�^�̊i�[
	private ArrayList aryClipboad;		//���얾�ׂ̃R�s�[���y�[�X�g�ɕK�v�ȃf�[�^�̊i�[
	
	/**
	 * �R���X�g���N�^
	 */
	public ClipboardData(){
		//�X�[�p�[�N���X�̃R���X�g���N�^�Ăяo��
		super();
		
		this.strClipboad = null;
		this.aryClipboad = new ArrayList();
	}

	/**
	 * �N���b�v�{�[�h�p���� �Q�b�^�[
	 * @return strClipboad : �N���b�v�{�[�h�p�����̒l��Ԃ�
	 */
	public String getStrClipboad() {
		return strClipboad;
	}
	/**
	 * �N���b�v�{�[�h�p���� �Z�b�^�[
	 * @param strClipboad : �N���b�v�{�[�h�p�����̒l���i�[����
	 */
	public void setStrClipboad(String _strClipboad) {
		this.strClipboad = _strClipboad;
	}
	
	/**
	 * �N���b�v�{�[�h�p�z�� �Q�b�^�[
	 * @return aryClipboad : �N���b�v�{�[�h�p�z��̒l��Ԃ�
	 */
	public ArrayList getAryClipboad() {
		return aryClipboad;
	}
	/**
	 * �N���b�v�{�[�h�p�z�� �Z�b�^�[
	 * @param aryClipboad : �N���b�v�{�[�h�p�z��̒l���i�[����
	 */
	public void setAryClipboad(ArrayList aryClipboad) {
		this.aryClipboad = aryClipboad;
	}
	
}
