package jp.co.blueflag.shisaquick.jws.disp;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import jp.co.blueflag.shisaquick.jws.base.SubFrame;
import jp.co.blueflag.shisaquick.jws.common.ExceptionBase;
import jp.co.blueflag.shisaquick.jws.common.JwsConstManager;
import jp.co.blueflag.shisaquick.jws.data.DataCtrl;
import jp.co.blueflag.shisaquick.jws.panel.AnalysisPanel;

/**
 * 
 * �yA02-02�z ���앪�̓f�[�^�m�F�T�u���
 *  : ���앪�̓f�[�^�m�F�T�u��ʂ̑�����s��
 * 
 * @author TT.katayama
 * @since 2009/04/03
 */
public class AnalysinSubDisp extends SubFrame {
	private static final long serialVersionUID = 1L;
	
	private AnalysisPanel analysisPanel;

	private ExceptionBase ex;

	/**
	 * �R���X�g���N�^ 
	 * @param strOutput : ��ʃ^�C�g��
	 */
	public AnalysinSubDisp(String strOutput) throws ExceptionBase {
		//�P�D�X�[�p�[�N���X�̃R���X�g���N�^���`�i��ʃ^�C�g���ݒ�j
		super(JwsConstManager.JWS_TITLE + "�@"+strOutput);
		
		try {
			//�Q�D��ʂ̈ʒu�A�T�C�Y���w��
//mod start --------------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.4
//			this.setSize(900, 530);
//			this.setSize(990, 530);
//Java6�Ή�
			this.setSize(1002,599);
//mod end --------------------------------------------------------------------------------------
			this.setLocationRelativeTo(null);

			//�R�D���앪�̓f�[�^�m�F�p�l���C���X�^���X�𐶐�
			this.analysisPanel = new AnalysisPanel(strOutput);
			//TODO �I���{�^���̃C�x���g�ݒ�
			(this.analysisPanel.getButton())[4].addActionListener(getActionEvent());
			(this.analysisPanel.getButton())[4].setActionCommand("shuryo");
			this.getContentPane().add(this.analysisPanel);

//add start --------------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.5
	        //�t�H�[�J�X���ݒ�p�I�u�W�F�N�g�̊i�[
	        this.setFocusControl(this.analysisPanel.getSetFocusComponent());
//add end --------------------------------------------------------------------------------------
			
			//�S�D��ʂ�\����Ԃɂ���
			this.setVisible(false);
			
		} catch (ExceptionBase eb) {
			throw eb;
			
		} catch (Exception e) {
			//�G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("���앪�̓f�[�^�m�F��ʂ̃R���X�g���N�^�����s���܂����B");
			this.ex.setStrErrShori("AnalysinSubDisp");
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.toString());
			throw ex;
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
						ex.setStrErrmsg("���앪�̓f�[�^�m�F���(Frame)�@�A�N�V�����C�x���g���������s���܂���");
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
	
	//�p�l���Z�b�^�[&�Q�b�^�[
	public AnalysisPanel getAnalysisPanel() {
		return analysisPanel;
	}
	public void setAnalysisPanel(AnalysisPanel analysisPanel) {
		this.analysisPanel = analysisPanel;
	}
	
	
}