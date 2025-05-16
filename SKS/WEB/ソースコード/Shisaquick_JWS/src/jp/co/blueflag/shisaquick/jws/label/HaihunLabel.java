package jp.co.blueflag.shisaquick.jws.label;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.KeyEvent;

import javax.swing.SwingConstants;

import jp.co.blueflag.shisaquick.jws.common.*;

/**
 * 
 * �n�C�t���\�����x���ݒ�p�̃N���X
 * 
 */
public class HaihunLabel extends LabelBase {
	
	private ExceptionBase ex;
	private int width = 50;
	private int height = 15;
	
	/**
	 * �n�C�t���\�����x�� �R���X�g���N�^ 
	 */
	public HaihunLabel() throws ExceptionBase {
		//�X�[�p�[�N���X�̃R���X�g���N�^�Ăяo��
		super();
		
		try {
			//���x���̃T�C�Y�ݒ�
			this.setText("-");
			this.setHorizontalAlignment(this.CENTER);
			this.setFont(new Font("Default", Font.PLAIN, 12));
			this.setBackground(Color.WHITE);
			this.setOpaque( true );
			
		} catch ( Exception e ) {			
			//�G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("�n�C�t���\�����x���̃R���X�g���N�^�����s���܂���");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;
			
		} finally {
			
		}
	}
	
}