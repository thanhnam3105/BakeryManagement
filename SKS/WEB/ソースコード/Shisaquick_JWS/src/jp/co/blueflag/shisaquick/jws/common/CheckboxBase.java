package jp.co.blueflag.shisaquick.jws.common;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

import javax.swing.*;

/**
 * 
 * �`�F�b�N�{�b�N�X����
 *  : �A�v�����`�F�b�N�{�b�N�X�̊�{������s��
 *
 */
public class CheckboxBase extends JCheckBox implements FocusControl {

	private static final long serialVersionUID = 1L;	//�f�t�H���g�V���A��ID
	
	private   ExceptionBase ex;					//�G���[����
	private   String pk1;						//�L�[�P
	private   String pk2;						//�L�[�Q
	private   String pk3;						//�L�[�R
	private   String pk4;						//�L�[�S
	private   String pk5;						//�L�[�T
	
	/**
	 * �R���X�g���N�^
	 */
	public CheckboxBase(){
		//�X�[�p�[�N���X�̌Ăяo��
		super();
		
		this.ex = null;
		this.setFont(new Font("Default", Font.PLAIN, 12));
		this.setIcon(new MyCheckBoxIcon2());
		this.setHorizontalAlignment(JCheckBox.CENTER);
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
		}  catch ( Exception e ) {			
			//�G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("CheckboxBase�̃t�H�[�J�X���䏈�������s���܂���");
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
	/**
	 * �L�[�T�Q�b�^�[���Z�b�^�[
	 */
	public String getPk5() {
		return pk5;
	}
	public void setPk5(String pk5) {
		this.pk5 = pk5;
	}
	
	/**
	 * ��p�A�C�R���N���X
	 */
	public class MyCheckBoxIcon2 implements Icon {  
		
		private final Icon orgIcon = UIManager.getIcon("CheckBox.icon"); 
		  
		public void paintIcon(Component c, Graphics g, int x, int y) {  
			  
			orgIcon.paintIcon(c, g, x, y);    
			AbstractButton b = (AbstractButton)c;    
			ButtonModel model = b.getModel();    
			g.setColor(Color.WHITE);    
			g.fillRect(x+2,y+2,getIconWidth()-4,getIconHeight()-4);    
			if(model.isSelected()) {
				g.setColor(Color.BLACK);      
				g.drawLine(x+9, y+3, x+9, y+3);      
				g.drawLine(x+8, y+4, x+9, y+4);      
				g.drawLine(x+7, y+5, x+9, y+5);      
				g.drawLine(x+6, y+6, x+8, y+6);      
				g.drawLine(x+3, y+7, x+7, y+7);      
				g.drawLine(x+4, y+8, x+6, y+8);      
				g.drawLine(x+5, y+9, x+5, y+9);      
				g.drawLine(x+3, y+5, x+3, y+5);      
				g.drawLine(x+3, y+6, x+4, y+6);    
			}  
		}
		  
		public int getIconWidth() {    
			return orgIcon.getIconWidth();  
		}
		
		public int getIconHeight() {    
			return orgIcon.getIconHeight();  
		}
	}
	
}
