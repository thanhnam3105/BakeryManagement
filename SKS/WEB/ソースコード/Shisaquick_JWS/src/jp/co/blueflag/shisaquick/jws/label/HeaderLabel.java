package jp.co.blueflag.shisaquick.jws.label;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.KeyEvent;

import javax.swing.SwingConstants;

import jp.co.blueflag.shisaquick.jws.common.*;
import jp.co.blueflag.shisaquick.jws.data.DataCtrl;

/**
 * 
 * �w�b�_�[���ڃ��x���ݒ�p�̃N���X
 * 
 */
public class HeaderLabel extends LabelBase {
	
	private ExceptionBase ex;
	
	private String strkaisha = "������ЁF";
	private String strbusho = "���������F";
	private String strtanto = "�S���ҁF";
	//2010/02/25 NAKAMURA ADD START---------
	private String strhaitakaisha = "��ЁF";
	private String strhaitabusho = "";
	private String strhaitashimei = "";
	//2010/02/25 NAKAMURA ADD END-----------
	private String header = "";
	
	/**
	 * ��ʃ^�C�g�����x�� �R���X�g���N�^ 
	 */
	public HeaderLabel() throws ExceptionBase{
		//�X�[�p�[�N���X�̃R���X�g���N�^�Ăяo��
		super();
		
		try {
			//���[�U�f�[�^�̐ݒ�
			header = this.getHeaderUserData();

			//���x���̃T�C�Y�ݒ�
			this.setText(header);
			this.setHorizontalAlignment(this.RIGHT);
			this.setFont(new Font("Default", Font.PLAIN, 12));
			this.setBackground( Color.WHITE );
			this.setOpaque( true );
			
		}catch ( Exception e ) {			
			//�G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("�w�b�_�[���ڃ��x���̃R���X�g���N�^�����s���܂���");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;
			
		} finally {
			
		}
		
	}
	
//2010/05/12�@�V�T�N�C�b�N�i�����j�v�]�y�Č�No9�z�r�����̕\���@TT.NISHIGAWA�@START	
	public String getHeaderUserData() throws ExceptionBase{
		
		String ret = "";
		
		try {
			//�f�[�^������
			strkaisha = "������ЁF";
			strbusho = "���������F";
			strtanto = "�S���ҁF";
			strhaitakaisha = "��ЁF";
			strhaitabusho = "";
			strhaitashimei = "";
			
			//���[�U�f�[�^�̐ݒ�
			strkaisha = strkaisha + DataCtrl.getInstance().getUserMstData().getStrKaishanm();
			strbusho = strbusho + DataCtrl.getInstance().getUserMstData().getStrBushonm();
			strtanto = strtanto + DataCtrl.getInstance().getUserMstData().getStrUsernm();
			//2010/02/25 NAKAMURA UPDATE START--------------------------------------------
			//header = strkaisha + "�@" + strbusho + "�@" + strtanto;
			strhaitakaisha = strhaitakaisha + DataCtrl.getInstance().getUserMstData().getStrHaitaKaishanm();
			strhaitabusho = DataCtrl.getInstance().getUserMstData().getStrHaitaBushonm();
			strhaitashimei = DataCtrl.getInstance().getUserMstData().getStrHaitaShimei();
			if(strhaitabusho.equals("")){
				strhaitabusho = "�g�p���F---/";
			}else{
				strhaitabusho = "�g�p���F" + strhaitabusho + "/";
			}
			if(strhaitashimei.equals("")){
				strhaitashimei = "---";
			}
			
			ret = strhaitabusho + strhaitashimei+ "�@" + strkaisha + "�@" + strbusho + "�@" + strtanto;
			
			//header = strhaitakaisha + "�@" + strhaitabusho + "�@" + strhaitashimei + "�@" + strkaisha + "�@" + strbusho + "�@" + strtanto;
			//2010/02/25 NAKAMURA UPDATE END----------------------------------------------
			
			this.setText("");
			//this.setText(header);

		}catch ( Exception e ) {			
			//�G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("�w�b�_�[���ڃ��x���̃��[�U�ݒ肪���s���܂���");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;
			
		} finally {
			
		}
		
		return ret;
		
	}
//2010/05/12�@�V�T�N�C�b�N�i�����j�v�]�y�Č�No9�z�r�����̕\���@TT.NISHIGAWA�@END
	
}