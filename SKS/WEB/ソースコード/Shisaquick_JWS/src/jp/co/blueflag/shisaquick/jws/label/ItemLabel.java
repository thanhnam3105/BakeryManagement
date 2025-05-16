package jp.co.blueflag.shisaquick.jws.label;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.KeyEvent;

import javax.swing.SwingConstants;

import jp.co.blueflag.shisaquick.jws.common.*;

/**
 * 
 * ���ڃ��x���ݒ�p�̃N���X
 * 
 */
public class ItemLabel extends LabelBase {
	
	private ExceptionBase ex;
	
	/**
	 * ���ڃ��x�� �R���X�g���N�^ 
	 */
	public ItemLabel() throws ExceptionBase{
		//�X�[�p�[�N���X�̃R���X�g���N�^�Ăяo��
		super();
		
		try {
			//���x���̃T�C�Y�ݒ�
			this.setFont(new Font("Default", Font.PLAIN, 12));
			this.setVerticalAlignment(SwingConstants.CENTER);
			this.setBackground( JwsConstManager.SHISAKU_ITEM_COLOR );
			this.setOpaque( true );
			
		} catch ( Exception e ) {	
			//�G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("���ڃ��x���̃R���X�g���N�^�����s���܂���");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;
			
		} finally {
			
		}
		
	}
	
}