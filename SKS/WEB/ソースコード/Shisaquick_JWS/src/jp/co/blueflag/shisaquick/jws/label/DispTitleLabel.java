package jp.co.blueflag.shisaquick.jws.label;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.SwingConstants;

import jp.co.blueflag.shisaquick.jws.common.*;

/**
 * 
 * ��ʃ^�C�g�����x���ݒ�p�̃N���X
 * 
 */
public class DispTitleLabel extends LabelBase{
	
	private ExceptionBase ex;
	
	private int width = 150;
	private int height = 15;
	
	/**
	 * ��ʃ^�C�g�����x�� �R���X�g���N�^ 
	 */
	public DispTitleLabel() throws ExceptionBase{
		//�X�[�p�[�N���X�̃R���X�g���N�^�Ăяo��
		super();
		
		try{
			
			//���x���̃T�C�Y�ݒ�
			this.setFont(new Font("Default", Font.PLAIN, 12));
			this.setBounds(5, 5, width, height);
			this.setForeground( Color.white );
			this.setBackground( JwsConstManager.SHISAKU_TITLE_COLOR);
			this.setOpaque( true );
			
		}catch(Exception e){
			//�G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("��ʃ^�C�g�����x���̃R���X�g���N�^�����s���܂���");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;
			
		}finally{
			
		}
		
	}
	
}