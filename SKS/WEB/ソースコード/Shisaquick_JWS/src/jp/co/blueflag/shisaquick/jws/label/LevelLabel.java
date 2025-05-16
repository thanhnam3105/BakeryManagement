package jp.co.blueflag.shisaquick.jws.label;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.KeyEvent;

import javax.swing.SwingConstants;

import jp.co.blueflag.shisaquick.jws.common.*;

/**
 * 
 * ���x�����ڃ��x���ݒ�p�̃N���X
 * 
 */
public class LevelLabel extends LabelBase {
	
	private ExceptionBase ex;
	private int red = 0xEE;
	private int green = 0x00;
	private int blue = 0x00;
	
	/**
	 *  ���x�����ڃ��x�� �R���X�g���N�^ 
	 */
	public LevelLabel() throws ExceptionBase{
		//�X�[�p�[�N���X�̃R���X�g���N�^�Ăяo��
		super();
		
		try {
			//���x���̃T�C�Y�ݒ�
			this.setText("���x���P");
			this.setFont(new Font("Default", Font.PLAIN, 12));
			this.setForeground( Color.white );
			this.setBackground(JwsConstManager.SHISAKU_LEVEL_COLOR);
			this.setOpaque( true );
			
		} catch ( Exception e ) {			
			//�G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("���x�����ڃ��x���̃R���X�g���N�^�����s���܂���");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;
			
		} finally {
			
		}
		
	}
	
}