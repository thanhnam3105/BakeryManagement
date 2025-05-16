package jp.co.blueflag.shisaquick.jws.disp;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import jp.co.blueflag.shisaquick.jws.base.SubFrame;
import jp.co.blueflag.shisaquick.jws.common.ExceptionBase;
import jp.co.blueflag.shisaquick.jws.common.JwsConstManager;
import jp.co.blueflag.shisaquick.jws.data.DataCtrl;
import jp.co.blueflag.shisaquick.jws.panel.AnalysisPanel;
import jp.co.blueflag.shisaquick.jws.panel.EigyoTantoSerchPanel;

/**
 * 
 * �c�ƒS�������T�u���
 *  : �c�ƒS�������̑�����s��
 * 
 * @author TT.Nishigawa
 * @since 2011/2/10
 */
public class EigyoTantoSearchDisp extends SubFrame {
	private static final long serialVersionUID = 1L;
	
	private EigyoTantoSerchPanel EigyoTantoSerchPanel;

	private ExceptionBase ex;

	/**
	 * �R���X�g���N�^ 
	 * @param strOutput : ��ʃ^�C�g��
	 */
	public EigyoTantoSearchDisp(String strOutput) throws ExceptionBase {
		//�P�D�X�[�p�[�N���X�̃R���X�g���N�^���`�i��ʃ^�C�g���ݒ�j
		super(JwsConstManager.JWS_TITLE + "�@"+strOutput);
		
		try {
			//�Q�D��ʂ̈ʒu�A�T�C�Y���w��
			//2012/02/22 TT H.SHIMA Java6�Ή� start
//			this.setSize(920, 530);
			this.setSize(936, 542);
			//2012/02/22 TT H.SHIMA Java6�Ή� end
			this.setLocationRelativeTo(null);

			//�R�D���앪�̓f�[�^�m�F�p�l���C���X�^���X�𐶐�
			this.EigyoTantoSerchPanel = new EigyoTantoSerchPanel(strOutput);
			//�I���{�^���̃C�x���g�ݒ�
			(this.EigyoTantoSerchPanel.getButton())[2].addActionListener(getActionEvent());
			(this.EigyoTantoSerchPanel.getButton())[2].setActionCommand("shuryo");
			this.getContentPane().add(this.EigyoTantoSerchPanel);

	        //�t�H�[�J�X���ݒ�p�I�u�W�F�N�g�̊i�[
	        this.setFocusControl(this.EigyoTantoSerchPanel.getSetFocusComponent());
			
			//�S�D��ʂ�\����Ԃɂ���
			this.setVisible(false);
			
		} catch (ExceptionBase eb) {
			throw eb;
			
		} catch (Exception e) {
			//�G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("�c�ƒS�������T�u��ʂ̃R���X�g���N�^�����s���܂����B");
			this.ex.setStrErrShori("EigyoTantoSerchPanel");
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
						ex.setStrErrmsg("�c�ƒS�������T�u��ʁ@�A�N�V�����C�x���g���������s���܂���");
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
			this.ex.setStrErrmsg("�c�ƒS�������T�u��ʁ@�A�N�V�����C�x���g���������s���܂���");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;
		} finally {
			
		}
		
		return listener;
	}
	
	//�p�l���Z�b�^�[&�Q�b�^�[
	public EigyoTantoSerchPanel getEigyoTantoSerchPanel() {
		return EigyoTantoSerchPanel;
	}
	public void setEigyoTantoSerchPanel(EigyoTantoSerchPanel EigyoTantoSerchPanel) {
		this.EigyoTantoSerchPanel = EigyoTantoSerchPanel;
	}
	
	
}