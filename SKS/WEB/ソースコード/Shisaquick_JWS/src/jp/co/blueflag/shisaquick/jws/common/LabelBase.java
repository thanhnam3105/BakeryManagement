package jp.co.blueflag.shisaquick.jws.common;

import javax.swing.*;

/**
 * 
 * ���x���̊�{�N���X
 *
 */
public class LabelBase extends JLabel {

	private static final long serialVersionUID = 1L;	//�f�t�H���g�V���A��ID
	
//	private ExceptionBase eb;						//�G���[����
	
	/**
	 * �f�t�H���g�R���X�g���N�^
	 */
	public LabelBase() {
		 //�X�[�p�[�N���X�̃R���X�g���N�^�Ăяo��
		 super();
	}

	/**
	 * �R���X�g���N�^(�e�L�X�g�w��)
	 * @param text : �e�L�X�g
	 */
	public LabelBase(String text) {
		 //�X�[�p�[�N���X�̃R���X�g���N�^�Ăяo��
		 super(text);

	}
}
