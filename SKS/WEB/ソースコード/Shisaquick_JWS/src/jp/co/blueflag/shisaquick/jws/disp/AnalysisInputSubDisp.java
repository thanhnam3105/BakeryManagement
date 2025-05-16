package jp.co.blueflag.shisaquick.jws.disp;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import jp.co.blueflag.shisaquick.jws.base.SubFrame;
import jp.co.blueflag.shisaquick.jws.common.ExceptionBase;
import jp.co.blueflag.shisaquick.jws.common.JwsConstManager;
import jp.co.blueflag.shisaquick.jws.data.DataCtrl;
import jp.co.blueflag.shisaquick.jws.panel.AnalysisInputPanel;

/**
 * 
 * �yA02-03�z ���͒l���͉��
 *  : ���͒l���͉�ʂ̑�����s��
 * 
 * @author TT.katayama
 * @since 2009/04/03
 */
public class AnalysisInputSubDisp extends SubFrame {
	private static final long serialVersionUID = 1L;
	
	private AnalysisInputPanel analysisInputPanel;

	private ExceptionBase ex;

	/**
	 * �R���X�g���N�^ 
	 * @param strOutput : ��ʃ^�C�g��
	 */
	public AnalysisInputSubDisp(String strOutput) throws ExceptionBase {
		//�P�D�X�[�p�[�N���X�̃R���X�g���N�^���`�i��ʃ^�C�g���ݒ�j
		super(JwsConstManager.JWS_TITLE + "�@"+strOutput);
		
		try {
			//�Q�D��ʂ̈ʒu�A�T�C�Y���w��
// MOD start 20121126 QP@20505 �ۑ�No.10 MSG�ǉ��̂���
//			//2012/02/22 TT H.SHIMA Java6�Ή� start
////			this.setSize(500, 550);
//			this.setSize(512, 559);
//			//2012/02/22 TT H.SHIMA Java6�Ή� end
			this.setSize(512, 585);
// MOD end 20121126 QP@20505 �ۑ�No.10 MSG�ǉ��̂���
			this.setLocationRelativeTo(null);

			//�R�D���͒l���̓p�l���C���X�^���X�𐶐�
			this.analysisInputPanel = new AnalysisInputPanel(strOutput); 
			this.getContentPane().add(this.analysisInputPanel);
			
			//�I���C�x���g�ǉ�
			analysisInputPanel.getButton()[1].addActionListener(getActionEvent());
			analysisInputPanel.getButton()[1].setActionCommand("shuryo");
			
			//�S�D��ʂ�\����Ԃɂ���
			this.setVisible(false);

		} catch (ExceptionBase eb) {
			throw eb;
			
		} catch (Exception e) {
			//�G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("���͒l���͉�ʂ̃R���X�g���N�^�����s���܂����B");
			this.ex.setStrErrShori("AnalysisInputSubDisp");
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw this.ex;
		} finally {
			
		}
	}
	
	/**
	 * ActionListener�擾���\�b�h
	 *  : �{�^���������y�уR���{�{�b�N�X�I�����̃C�x���g��ݒ肷��
	 * @return ActionListener 
	 * @throws ExceptionBase
	 */
	public ActionListener getActionEvent() throws ExceptionBase {
		ActionListener listener = null;
		try{
			listener = new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						String event_name = e.getActionCommand();
						if ( event_name.equals("shuryo")) {
							//�I���������s��
							Exit();
						}
					} catch (Exception ec) {
						//�G���[�ݒ�
						ex = new ExceptionBase();
						ex.setStrErrCd("");
						ex.setStrErrmsg("���앪�̓f�[�^�m�F���(Frame)�@�I�����������s���܂���");
						ex.setStrErrShori(this.getClass().getName());
						ex.setStrMsgNo("");
						ex.setStrSystemMsg(ec.getMessage());
						DataCtrl.getInstance().PrintMessage(ex);
					} finally {
					}
				}
			};

		} catch (Exception e) {
			//�G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("���앪�̓f�[�^�m�F���(Frame)�@�A�N�V�����C�x���g���������s���܂���");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;
		} finally {
			
		}
		
		return listener;
	}
	
	/**
	 * �p�l���Q�b�^�[
	 */
	public AnalysisInputPanel getAnalysisInputPanel() {
		return analysisInputPanel;
	}
	
}