package jp.co.blueflag.shisaquick.jws.disp;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import jp.co.blueflag.shisaquick.jws.base.SubFrame;
import jp.co.blueflag.shisaquick.jws.common.ExceptionBase;
import jp.co.blueflag.shisaquick.jws.common.JwsConstManager;
import jp.co.blueflag.shisaquick.jws.data.DataCtrl;
import jp.co.blueflag.shisaquick.jws.panel.MaterialPanel;

/**
 * 
 * �yA02-03�z �����ꗗ�T�u���
 *  : �����ꗗ�T�u��ʂ̑�����s��
 * 
 * @author k-katayama
 * @since 2009/04/03
 */
public class MaterialSubDisp extends SubFrame {
	private static final long serialVersionUID = 1L;
	
	private MaterialPanel materialPanel;			//�����ꗗ�p�l���N���X

	private ExceptionBase ex;						//�G���[����N���X

	//�I���{�^���������̃C�x���g��
	private final String END_BTN_CLICK = "endBtnClick";

	/**
	 * �R���X�g���N�^ 
	 * @param strOutput : ��ʃ^�C�g��
	 * @throws ExceptionBase 
	 */
	public MaterialSubDisp(String strOutput) throws ExceptionBase {
		//�P�D�X�[�p�[�N���X�̃R���X�g���N�^���`�i��ʃ^�C�g���ݒ�j
		super(JwsConstManager.JWS_TITLE + "�@"+strOutput);

		try {
			//�Q�D��ʂ̈ʒu�A�T�C�Y���w��
			//2012/02/22 TT H.SHIMA Java6�Ή� start
//			this.setSize(900, 460);
			this.setSize(912, 469);
			//2012/02/22 TT H.SHIMA Java6�Ή� end
			this.setLocationRelativeTo(null);
		
			//�R�D�����ꗗ�p�l���C���X�^���X�𐶐�
			this.materialPanel = new MaterialPanel(strOutput);
			this.materialPanel.setEndEvent(this.getActionEvent(), this.END_BTN_CLICK);
			this.getContentPane().add(this.materialPanel);

			//�S�D��ʂ�\����Ԃɂ���
			this.setVisible(false);

	        //�t�H�[�J�X���ݒ�p�I�u�W�F�N�g�̊i�[
	        this.setFocusControl(this.materialPanel.getSetFocusComponent());
	        			
			//Window�C�x���g��ݒ�
			this.addWindowListener(this.getWindowEvent());
			
		} catch (ExceptionBase e) {
			throw e;
			
		} catch (Exception e) {
			//�G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("�����ꗗ�T�u��ʂ̃R���X�g���N�^�����s���܂����B");
			this.ex.setStrErrShori(this.getClass().getName());
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
						if ( event_name.equals(END_BTN_CLICK)) {
							//�I���{�^��������
							materialPanel.clickEndBtn();
							
							//�I���������s��
							Exit();
						}
					} catch (ExceptionBase eb) {
						DataCtrl.getInstance().PrintMessage(eb);
						
					} catch (Exception ec) {
						//�G���[�ݒ�
						ex = new ExceptionBase();
						ex.setStrErrCd("");
						ex.setStrErrmsg("�����ꗗ�T�u���(Frame)�̏I�����������s���܂���");
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
			this.ex.setStrErrmsg("�����ꗗ�T�u���(Frame)�@�A�N�V�����C�x���g���������s���܂���");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;
			
		} finally {
		}
		
		return listener;
	}
	
	/**
	 * Window�C�x���g�擾
	 * @return WindowListener
	 * @throws ExceptionBase
	 */
	public WindowListener getWindowEvent() throws ExceptionBase {
		WindowListener listener = null;
		try {
			listener = new WindowListener() {
				/**
				 * ��ʏI���C�x���g
				 */
				public void windowClosing(WindowEvent e) {
					try {
						materialPanel.clickEndBtn();
					} catch (ExceptionBase eb) {
						DataCtrl.getInstance().PrintMessage(eb);
					} catch (Exception ec) {
						//�G���[�ݒ�
						ex = new ExceptionBase();
						ex.setStrErrCd("");
						ex.setStrErrmsg("�����ꗗ�T�u���(Frame)�@��ʏI���C�x���g�iWindow�C�x���g�j���������s���܂���");
						ex.setStrErrShori(this.getClass().getName());
						ex.setStrMsgNo("");
						ex.setStrSystemMsg(ec.getMessage());
						DataCtrl.getInstance().PrintMessage(ex);
					} finally {
					}
				}	
				
				public void windowClosed(WindowEvent e) {}
				public void windowActivated(WindowEvent e) {}
				public void windowDeactivated(WindowEvent e) {}
				public void windowDeiconified(WindowEvent e) {}
				public void windowIconified(WindowEvent e) {}
				public void windowOpened(WindowEvent e) {}
			};

		} catch (Exception e) {
			//�G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("�����ꗗ�T�u���(Frame)�@Window�C�x���g���������s���܂���");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;
			
		} finally {
		}
		return listener;
	}
	
	//�p�l���Z�b�^�[&�Q�b�^�[
	public MaterialPanel getMaterialPanel() {
		return materialPanel;
	}
	public void setMaterialPanel(MaterialPanel materialPanel) {
		this.materialPanel = materialPanel;
	}
	
}