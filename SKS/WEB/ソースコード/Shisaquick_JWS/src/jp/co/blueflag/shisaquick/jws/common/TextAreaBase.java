package jp.co.blueflag.shisaquick.jws.common;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;

import javax.swing.*;

/**
 * 
 * �e�L�X�g�G���A��{�N���X
 *
 */
public class TextAreaBase extends JTextArea implements FocusControl {

	private static final long serialVersionUID = 1L;	//�f�t�H���g�V���A��ID
	
	private ExceptionBase ex;						//�G���[����
	
	/**
	 * �e�L�X�g�G���A�@�R���X�g���N�^
	 */
	public TextAreaBase() {
		//�X�[�p�[�N���X�̃R���X�g���N�^�Ăяo��
		super();
		
		this.setFont(new Font("Default", Font.PLAIN, 12));
		this.ex = new ExceptionBase();
	}
	
	/**
	 * TAB�������t�H�[�J�X�R���g���[��
	 */
	public void setTABFocusControl() throws ExceptionBase {
		this.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyPressed(java.awt.event.KeyEvent e) {
				//KEY �������̏���
				if ( e.getKeyCode() == KeyEvent.VK_TAB ) {
					transferFocus();
					e.consume();
				}
			}
		});
	}
	
	/**
	 * �ҏW�ێ��̐F�w��
	 */
	public void setEnabled(boolean b) {
		if(b){
			this.setBackground(Color.WHITE);
		}else{
			this.setBackground(Color.LIGHT_GRAY);
		}
		super.setEnabled(b);
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
			this.ex.setStrErrmsg("TextAreaBase�̃t�H�[�J�X���䏈�������s���܂���");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;
		} finally {
			
		}
	}
	
}
