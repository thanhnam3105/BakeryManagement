package jp.co.blueflag.shisaquick.jws.base;

import javax.swing.JFrame;

import jp.co.blueflag.shisaquick.jws.common.*;

/**
 * 
 * ���C����ʊ�ՃN���X
 *
 */
public class MainFrame extends FrameBase {

	private static final long serialVersionUID = 1L;

	/**
	 * ���C����ʊ�� �R���X�g���N�^ 
	 * @param title : �^�C�g��
	 */
	public MainFrame(String title) {
		//�X�[�p�[�N���X�̃R���X�g���N�^�Ăяo��
		super(title);

		//��ʂ����ƁA�v���O�������I������
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}


	/**
	 * �I������
	 *  : �v���O�������I������
	 */
	public void Exit() {
		System.exit(0);
	}

}
