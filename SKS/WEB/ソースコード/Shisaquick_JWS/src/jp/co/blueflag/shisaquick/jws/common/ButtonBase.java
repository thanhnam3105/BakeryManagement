package jp.co.blueflag.shisaquick.jws.common;

import java.awt.Font;
import java.awt.event.KeyEvent;

import javax.swing.*;

/**
 * 
 * �{�^������
 *  : �A�v�����{�^���̊�{������s��
 *
 */
public class ButtonBase extends JButton implements FocusControl {

	private static final long serialVersionUID = 1L;	//�f�t�H���g�V���A��ID
	
	private ExceptionBase ex;						//�G���[����
	
	/**
	 * �f�t�H���g�R���X�g���N�^
	 */
	public ButtonBase(){
		//�X�[�p�[�N���X�̌Ăяo��
		super();
		
		this.setFont(new Font("Default", Font.PLAIN, 12));
		this.ex = null;
	}

	/**
	 * �R���X�g���N�^(�e�L�X�g�w��)
	 * @param text : �e�L�X�g
	 */
	public ButtonBase(String text){
		//�X�[�p�[�N���X�̌Ăяo��
		super(text);
		
		this.setFont(new Font("Default", Font.PLAIN, 11));
		this.ex = null;
	}
	
	/**
	 * Enter�������t�H�[�J�X�R���g���[��
	 * @param enterComp : Enter���̃t�H�[�J�X�ړ���JComponent
	 */
	public void setEnterFocusControl(final JComponent enterComp) throws ExceptionBase {
		try {
			this.addKeyListener(new java.awt.event.KeyAdapter() {
				public void keyPressed(java.awt.event.KeyEvent e) {
					//KEY �������̏���
					if ( e.getKeyCode() == KeyEvent.VK_ENTER ) {
						enterComp.requestFocus();
					}
				}
			});
		} catch ( Exception e ) {			
			//�G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("ButtonBase�̃t�H�[�J�X���䏈�������s���܂���");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;
		} finally {
			
		}
	}
	
}
