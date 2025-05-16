package jp.co.blueflag.shisaquick.jws.button;

import java.awt.Font;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import jp.co.blueflag.shisaquick.jws.common.ButtonBase;

/**
 * 
 * �����N�{�^��
 * 
 * @author TT.katayama
 * @since 2009/04/03
 *
 */
public class LinkButton extends ButtonBase {
	private static final long serialVersionUID = 1L;
	
	/**
	 * �R���X�g���N�^
	 * @param strText : �e�L�X�g
	 */
	public LinkButton(String strText) {
		//�X�[�p�[�N���X�̃R���X�g���N�^���Ăяo��
		super();
		
		//�{�^�������Z�b�g
		this.setText(strText);
		//Font��ݒ�
		this.setFont(new Font("Default", Font.PLAIN, 14));
		//�g��������
		this.setBorderPainted(false);
		//�w�i������
		this.setContentAreaFilled(false);
		
		//�t�H�[�J�X���䏈���̐ݒ�
		this.addFocusListener(this.getFocusEvent());
	}
	
	/**
	 * �R���X�g���N�^(���W�w��)
	 * @param strText : �e�L�X�g
	 * @param x : X���W
	 * @param y : Y���W
	 */
	public LinkButton(String strText, int x, int y) {
		//�X�[�p�[�N���X�̃R���X�g���N�^���Ăяo��
		super();
		
		//�{�^�������Z�b�g
		this.setText(strText);
		//�T�C�Y�ݒ�
		this.setBounds(x,y, 50,50);
		//Font��ݒ�
		this.setFont(new Font("Default", Font.PLAIN, 14));
		//�g��������
		this.setBorderPainted(false);
		//�w�i������
		this.setContentAreaFilled(false);
		
		//�t�H�[�J�X���䏈���̐ݒ�
		this.addFocusListener(this.getFocusEvent());
	}
	
	/**
	 * FocusListener�擾���\�b�h
	 * @return FocusListener
	 */
	private FocusListener getFocusEvent() {
		return new FocusListener() {
			/**
			 * �t�H�[�J�X��
			 */
			public void focusGained(FocusEvent e) {
				if ( !isEnabled() ) {
					transferFocus();
				}
			}
			/**
			 * ���X�g�t�H�[�J�X��
			 */
			public void focusLost(FocusEvent e) {}
		};
	}
}
