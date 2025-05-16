package jp.co.blueflag.shisaquick.jws.disp;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import jp.co.blueflag.shisaquick.jws.base.SubFrame;
import jp.co.blueflag.shisaquick.jws.common.ExceptionBase;
import jp.co.blueflag.shisaquick.jws.common.JwsConstManager;
import jp.co.blueflag.shisaquick.jws.data.DataCtrl;
import jp.co.blueflag.shisaquick.jws.panel.ColorPanel;

/**
 * 
 * �yA02-05�z �F�w��T�u���
 *  : �F�w��T�u��ʂ̑�����s��
 * 
 * @author TT.katayama
 * @since 2009/04/05
 */
public class ColorSubDisp extends SubFrame {
	private static final long serialVersionUID = 1L;
	
	private ColorPanel colorPanel;			//�F�w��p�l���N���X
	private ExceptionBase ex;					//�G���[����N���X

	//�I���{�^���������̃C�x���g��
	private final String END_BTN_CLICK = "endBtnClick";

	/**
	 * �R���X�g���N�^ 
	 * @param strOutput : ��ʃ^�C�g��
	 * @throws ExceptionBase 
	 */
	public ColorSubDisp(String strOutput) throws ExceptionBase {
		//�P�D�X�[�p�[�N���X�̃R���X�g���N�^���`�i��ʃ^�C�g���ݒ�j
		super(JwsConstManager.JWS_TITLE + "�@"+strOutput);

		try {
			//�Q�D��ʂ̈ʒu�A�T�C�Y���w��
			//2012/02/22 TT H.SHIMA Java6�Ή� start
//			this.setSize(290, 220);
			this.setSize(302,229);
			//2012/02/22 TT H.SHIMA Java6�Ή� end
			this.setLocationRelativeTo(null);
			
			//�R�D�F�w��p�l���C���X�^���X�𐶐�
			this.colorPanel = new ColorPanel(strOutput); 
			this.colorPanel.setEndEvent(this.getActionEvent(), this.END_BTN_CLICK);
			this.getContentPane().add(this.colorPanel);
			
			//�S�D��ʂ��\����Ԃɂ���
			this.setVisible(false);

		} catch (ExceptionBase eb) {
			throw eb;
			
		} catch (Exception e) {
			//�G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("�F�w��T�u��ʂ̃R���X�g���N�^�����s���܂����B");
			this.ex.setStrErrShori("ColorSubDisp");
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
						if ( event_name.equals(END_BTN_CLICK)) {
//							colorPanel.getColorChooser().setColor(new Color(-1));
							//�I���������s��
							Exit();
						}
					} catch (Exception ec) {
						//�G���[�ݒ�
						ex = new ExceptionBase();
						ex.setStrErrCd("");
						ex.setStrErrmsg("�F�w��T�u���(Frame)�̏I�����������s���܂���");
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
			this.ex.setStrErrmsg("�F�w��T�u���(Frame)�@�A�N�V�����C�x���g���������s���܂���");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;
			
		} finally {
			
		}
		
		return listener;
	}
	
	public ColorPanel getColorPanel() {
		return colorPanel;
	}

	public void setColorPanel(ColorPanel colorPanel) {
		this.colorPanel = colorPanel;
	}
	
}