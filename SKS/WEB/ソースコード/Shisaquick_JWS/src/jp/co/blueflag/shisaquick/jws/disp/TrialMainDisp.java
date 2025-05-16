package jp.co.blueflag.shisaquick.jws.disp;

import java.awt.Container;

import javax.swing.JFrame;

import jp.co.blueflag.shisaquick.jws.base.*;
import jp.co.blueflag.shisaquick.jws.common.*;
import jp.co.blueflag.shisaquick.jws.panel.TrialDispPanel;

/**
 * 
 * ����f�[�^��ʃN���X
 * 
 */
public class TrialMainDisp extends MainFrame {
	
	private ExceptionBase ex;
	private TrialDispPanel tp;
	
	/**
	 * ����f�[�^��� �R���X�g���N�^ 
	 * @param title : �^�C�g��
	 * @throws ExceptionBase 
	 */
	public TrialMainDisp(String title) throws ExceptionBase {
		//�X�[�p�[�N���X�̃R���X�g���N�^�Ăяo��
		super(JwsConstManager.JWS_TITLE + "�@"+title);
		
		try {
			
			//��ʃT�C�Y�A�ʒu�̐ݒ�
			//2012/02/22 TT H.SHIMA Java6�Ή� start
			//this.setSize(1024, 750);
			this.setSize(1036,759);
			//2012/02/22 TT H.SHIMA Java6�Ή� end
			//this.setLocationRelativeTo(null);
			
			//�p�l���̐ݒ�
			tp = new TrialDispPanel();
			Container contentPane = this.getContentPane();
			contentPane.add (tp);
			
			
			
		} catch (ExceptionBase e) {
			throw e;
			
		} catch (Exception e) {
			//�G���[�ݒ�
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			ex.setStrErrmsg("����f�[�^��ʁiFrame�j�̃R���X�g���N�^�����s���܂���");
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getMessage());
			throw ex;
			
		} finally {
			
		}
	}
	
	public TrialDispPanel getTp() {
		return tp;
	}

	public void setTp(TrialDispPanel tp) {
		this.tp = tp;
	}
	
	
}