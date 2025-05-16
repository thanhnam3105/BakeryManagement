package jp.co.blueflag.shisaquick.jws.panel;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JColorChooser;
import javax.swing.colorchooser.AbstractColorChooserPanel;

import jp.co.blueflag.shisaquick.jws.common.ButtonBase;
import jp.co.blueflag.shisaquick.jws.common.ExceptionBase;
import jp.co.blueflag.shisaquick.jws.common.PanelBase;
import jp.co.blueflag.shisaquick.jws.data.DataCtrl;
import jp.co.blueflag.shisaquick.jws.label.DispTitleLabel;

/**
 * 
 * �yA05-06�z �F�w��p�l������p�̃N���X
 * 
 * @author TT.katayama
 * @since 2009/04/05
 */
public class ColorPanel extends PanelBase {
	private static final long serialVersionUID = 1L;

	private DispTitleLabel dispTitleLabel;		//��ʃ^�C�g�����x��
//	private HeaderLabel headerLabel;				//�w�b�_�\�����x��
//	private LevelLabel levelLabel;					//���x���\�����x��

	private JColorChooser colorChooser;		//�F�I���N���X
//	private Color selectColor;						//�F���i�[�N���X
	private ButtonBase closeBtn;					//����{�^��
	private ButtonBase colorClearBtn;			//�F�����{�^��
	
//	private MessageCtrl messageCtrl;				//���b�Z�[�W����
	private ExceptionBase ex;						//�G���[����

	//�I��F�����{�^���R�}���h������
	private final static String BTN_COLOR_CANCEL = "colorCancelBtnClick";
	
	/**
	 * �R���X�g���N�^
	 * @param strOutput : ��ʃ^�C�g��
	 * @throws ExceptionBase 
	 */
	public ColorPanel(String strOutput) throws ExceptionBase {
		//�X�[�p�[�N���X�̃R���X�g���N�^���Ăяo��
		super();

		try {
			//�P�D�p�l���̐ݒ�
			this.setPanel();
			
			//�Q�D�R���g���[���̔z�u
			this.addControl(strOutput);
			
			//�R�D�������������s
			this.init();
	
		} catch(ExceptionBase e) {
			throw e;
			
		} catch(Exception e) {
			//�G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("�F�w��p�l���̃R���X�g���N�^�����s���܂����B");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw this.ex;
			
		} finally {
		}
		
	}

	/**
	 * �p�l���ݒ�
	 */
	private void setPanel() {
		this.setLayout(null);
		this.setBackground(Color.WHITE);
	}

	/**
	 * �R���g���[���z�u
	 * @param strTitle : ��ʃ^�C�g��
	 * @throws ExceptionBase 
	 */
	private void addControl(String strTitle) throws ExceptionBase {
		int x, y, width, height;
		int dispWidth = 290;
	
		try {
			///
			/// �^�C�g�����x���ݒ�
			///
			this.dispTitleLabel = new DispTitleLabel();
			this.dispTitleLabel.setText(strTitle);
			this.add(this.dispTitleLabel);
			
			///
			/// �J���[�I��
			///
			x = 5;
			y = 30;
			width = 273;
			height = 120;
			this.colorChooser = new JColorChooser();
			AbstractColorChooserPanel colorPanel = this.colorChooser.getChooserPanels()[0];
			colorPanel.setBounds(x,y,width,height);
			colorPanel.setBackground(Color.WHITE);
			this.add(colorPanel);
	
			///
			/// �L�����Z���{�^��
			///
			x = dispWidth - 110;
			y = colorPanel.getY() + height;
			width = 100;
			height = 38;
			this.closeBtn = new ButtonBase();
			this.closeBtn.setFont(new Font("Default", Font.PLAIN, 11));
			this.closeBtn.setBounds(x,y,width,height);
			this.closeBtn.setText("�L�����Z��");
			this.add(this.closeBtn);
			
			///
			/// �F�I�������{�^��
			///
			x = x - width;
			this.colorClearBtn = new ButtonBase();
			this.colorClearBtn.setFont(new Font("Default", Font.PLAIN, 11));
			this.colorClearBtn.setBounds(x,y,width,height);
			this.colorClearBtn.setText("�I��F����");
			this.add(this.colorClearBtn);
		
		} catch (Exception e) {
			//�G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("�F�w��p�l���̃R���g���[���z�u���������s���܂����B");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw this.ex;
		} finally {
		}
	}
  
	/**
	 * ����������
	 * @throws ExceptionBase 
	 */
	private void init() throws ExceptionBase {
		
		try {
			//�I��F�����{�^�� Action�R�}���h�ݒ�
			this.colorClearBtn.addActionListener(this.getActionEvent());
			this.colorClearBtn.setActionCommand(BTN_COLOR_CANCEL);
			
		} catch (ExceptionBase e) {
			throw e;
		} catch (Exception e) {
			//�G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("�F�w��p�l���̏��������������s���܂����B");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw this.ex;
		} finally {
		}		
	}

	/**
	 * �L�����Z���{�^���@�C�x���g�ݒ菈��
	 * @param listener : ActionListener
	 * @param actionCommand : �R�}���h����
	 */
	public void setEndEvent(ActionListener listener, String actionCommand) {
		this.closeBtn.addActionListener(listener);
		this.closeBtn.setActionCommand(actionCommand);
	}
	
	/**
	 * ActionListener�擾���\�b�h
	 *  : �{�^���������y�уR���{�{�b�N�X�I�����̃C�x���g��ݒ肷��
	 * @return ActionListener 
	 * @throws ExceptionBase
	 */
	private ActionListener getActionEvent() throws ExceptionBase {
		ActionListener listener = null;
		listener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					String event_name = e.getActionCommand();
					if (event_name.equals(BTN_COLOR_CANCEL)) {
						//�I��F����������
						colorChooser.setColor(new Color(-1));
						colorChooser.setColor(new Color(-2));
					}
				}catch(Exception ec){
					//�G���[�ݒ�
					ex = new ExceptionBase();
					ex.setStrErrCd("");
					ex.setStrErrmsg("�F�w��p�l���̃L�����Z���{�^�����������s���܂����B");
					ex.setStrErrShori(this.getClass().getName());
					ex.setStrMsgNo("");
					ex.setStrSystemMsg(ec.getMessage());
					DataCtrl.getInstance().getMessageCtrl().PrintErrMessage(ex);
					
				}finally{
					
				}
			}
		};
		return listener;
	}	

	/**
	 * JColorChooser�擾���\�b�h
	 *  : JColorChooser�N���X�ɁA�I��F��񂪊i�[����Ă���B
	 * @return JColorChooser
	 */
	public JColorChooser getColorChooser() {
		return colorChooser;
	}

	/**
	 * JColorChooser�ݒ胁�\�b�h
	 * @param colorChooser
	 */
	public void setColorChooser(JColorChooser colorChooser) {
		this.colorChooser = colorChooser;
	}
}