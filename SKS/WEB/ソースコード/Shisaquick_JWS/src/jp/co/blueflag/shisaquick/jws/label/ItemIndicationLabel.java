package jp.co.blueflag.shisaquick.jws.label;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.KeyEvent;

import javax.swing.SwingConstants;

import jp.co.blueflag.shisaquick.jws.common.*;

/**
 * 
 * �����ڃ��x���ݒ�p�̃N���X
 * 
 */
public class ItemIndicationLabel extends LabelBase {
	
	private ExceptionBase ex;
	
	/**
	 * �����ڃ��x�� �R���X�g���N�^ 
	 */
	public ItemIndicationLabel() throws ExceptionBase{
		//�X�[�p�[�N���X�̃R���X�g���N�^�Ăяo��
		super();
		
		try {
			//���x���̃T�C�Y�ݒ�
			this.setFont(new Font("Default", Font.PLAIN, 12));
			this.setBackground(Color.WHITE);
			this.setOpaque( true );
			
		} catch ( Exception e ) {			
			//�G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("�����ڃ��x���̃R���X�g���N�^�����s���܂���");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;
			
		} finally {
			
		}
		
	}
	
}