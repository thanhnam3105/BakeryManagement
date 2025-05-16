package jp.co.blueflag.shisaquick.jws.disp;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import jp.co.blueflag.shisaquick.jws.base.SubFrame;
import jp.co.blueflag.shisaquick.jws.common.ExceptionBase;
import jp.co.blueflag.shisaquick.jws.common.JwsConstManager;
import jp.co.blueflag.shisaquick.jws.panel.PrototypeListPanel;

/**
 * 
 * �yA02-06�z �����ǉ��T�u���
 *  : �����ǉ��T�u��ʂ̑�����s��
 * 
 * @author TT.katayama
 * @since 2009/04/03
 */
public class PrototypeListSubDisp extends SubFrame {
	private static final long serialVersionUID = 1L;
	
	private PrototypeListPanel prototypeListPanel;	//�����ǉ��p�l���N���X
	private ExceptionBase ex;								//�G���[����N���X
	private String strTitle;

	/**
	 * �R���X�g���N�^ 
	 * @param strOutput : ��ʃ^�C�g��
	 * @throws ExceptionBase 
	 */
	public PrototypeListSubDisp(String strOutput) throws ExceptionBase {
		//�P�D�X�[�p�[�N���X�̃R���X�g���N�^���`�i��ʃ^�C�g���ݒ�j
		super(JwsConstManager.JWS_TITLE + "�@"+strOutput);
		strTitle = strOutput;
		
		try {
			//��ʂ̈ʒu�A�T�C�Y���w��
			//2012/02/22 TT H.SHIMA Java6�Ή� start
//			this.setSize(620, 370);
			this.setSize(632,379);
			//2012/02/22 TT H.SHIMA Java6�Ή� end
			this.setLocationRelativeTo(null);
			
			//�����ǉ��p�l���C���X�^���X�𐶐�
			this.prototypeListPanel = new PrototypeListPanel(strTitle); 
			this.getContentPane().add(this.prototypeListPanel);
			
			//��ʂ�\����Ԃɂ���
			this.setVisible(false);
			

		} catch (Exception e) {
			//�G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("�����ǉ��T�u��ʂ̃R���X�g���N�^�����s���܂����B");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw this.ex;
			
		} finally {
			
		}
	}
	
	
	public void initPanel() throws ExceptionBase {
		try {
			//�����ǉ��p�l���C���X�^���X�𐶐�
			this.remove(prototypeListPanel);
			this.prototypeListPanel = new PrototypeListPanel(strTitle); 
			this.getContentPane().add(this.prototypeListPanel);
			
		}catch (ExceptionBase eb) {
			throw eb;
			
		} catch (Exception e) {
			//�G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("�����ǉ��T�u��ʂ̏��������������s���܂����B");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw this.ex;
			
		} finally {
			
		}
	}
	
	//�p�l���Q�b�^�[���Z�b�^�[
	public PrototypeListPanel getPrototypeListPanel() {
		return prototypeListPanel;
	}

	public void setPrototypeListPanel(PrototypeListPanel prototypeListPanel) {
		this.prototypeListPanel = prototypeListPanel;
	}
	
	
}