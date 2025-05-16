package jp.co.blueflag.shisaquick.jws.common;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;

import javax.swing.*;
import javax.swing.border.LineBorder;

/**
 * 
 * �e�L�X�g�{�b�N�X����
 *  �F �A�v�����e�L�X�g�{�b�N�X�̊�{������s��
 */
public class TextboxBase extends JTextField implements FocusControl {

	private static final long serialVersionUID = 1L;	//�f�t�H���g�V���A��ID
	
	private ExceptionBase ex;						//�G���[����
	private   String pk1;						//�L�[�P
	private   String pk2;						//�L�[�Q
	private   String pk3;						//�L�[�R
	private   String pk4;						//�L�[�S
	
	/**
	 * �R���X�g���N�^
	 */
	public TextboxBase(){
		//�X�[�p�[�N���X�̃R���X�g���N�^�̌Ăяo��
		super();
		
		this.ex = new ExceptionBase();
		this.setFont(new Font("Default", Font.PLAIN, 12));
		this.setDisabledTextColor(Color.BLACK);
	}
	
	/**
	 * �R���X�g���N�^
	 */
	public TextboxBase(String s){
		//�X�[�p�[�N���X�̃R���X�g���N�^�̌Ăяo��
		super(s);
		
		this.ex = new ExceptionBase();
		this.setFont(new Font("Default", Font.PLAIN, 11));
		this.setDisabledTextColor(Color.BLACK);
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
			this.ex.setStrErrmsg("TextboxBase�̃t�H�[�J�X���䏈�������s���܂���");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;
			
		} finally {
			
		}
	}
	
	/**
	 * �L�[�P�Q�b�^�[���Z�b�^�[
	 */
	public String getPk1() {
		return pk1;
	}
	public void setPk1(String pk1) {
		this.pk1 = pk1;
	}
	/**
	 * �L�[�Q�Q�b�^�[���Z�b�^�[
	 */
	public String getPk2() {
		return pk2;
	}
	public void setPk2(String pk2) {
		this.pk2 = pk2;
	}
	/**
	 * �L�[�R�Q�b�^�[���Z�b�^�[
	 */
	public String getPk3() {
		return pk3;
	}
	public void setPk3(String pk3) {
		this.pk3 = pk3;
	}
	/**
	 * �L�[�S�Q�b�^�[���Z�b�^�[
	 */
	public String getPk4() {
		return pk4;
	}
	public void setPk4(String pk4) {
		this.pk4 = pk4;
	}
	
}
