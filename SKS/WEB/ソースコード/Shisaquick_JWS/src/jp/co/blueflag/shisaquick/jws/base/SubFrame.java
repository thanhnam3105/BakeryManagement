package jp.co.blueflag.shisaquick.jws.base;

import javax.swing.JFrame;

import jp.co.blueflag.shisaquick.jws.common.*;

/**
 * 
 * �T�u��ʊ��N���X
 *
 */
public class SubFrame extends FrameBase {

	private static final long serialVersionUID = 1L;

	/**
	 * �T�u��ʊ��@�R���X�g���N�^
	 * @param title : �^�C�g��
	 */
	public SubFrame(String title) {
		//�X�[�p�[�N���X�̃R���X�g���N�^�Ăяo��
		super(title);
		
	}

	/**
	 * �I������
	 *  : ��ʂ��\���ɐݒ肷��
	 */
	public void Exit() {
		this.setVisible(false);
	}
}
