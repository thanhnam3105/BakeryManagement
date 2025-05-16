package jp.co.blueflag.shisaquick.jws.common;

import java.awt.event.KeyEvent;

import javax.swing.JComponent;
import javax.swing.JScrollPane;

/**
 * 
 * �X�N���[������
 *  : �X�N���[���̊�{������s��
 *
 */
public class ScrollBase extends JScrollPane implements FocusControl {

	private static final long serialVersionUID = 1L;	//�f�t�H���g�V���A��ID
	
	private ExceptionBase ex;						//�G���[����
	
	/**
	 * �f�t�H���g�R���X�g���N�^
	 */
	public ScrollBase() {
		//�X�[�p�[�N���X�̃R���X�g���N�^�Ăяo��
		super();
		this.ex = null;
	}
	
	/**
	 * JComponent�ݒ� �R���X�g���N�^
	 * @param view : �\��������R���|�[�l���g
	 */
	public ScrollBase(JComponent view) {
		//�X�[�p�[�N���X�̃R���X�g���N�^�Ăяo��
		super(view);
		this.ex = null;
	}

	/**
	 * Enter�������t�H�[�J�X�R���g���[��
	 * @param enterComp : Enter���̃t�H�[�J�X�ړ���JComponent
	 */
	public void setEnterFocusControl(final JComponent enterComp) throws ExceptionBase {
		try {
			this.addKeyListener(new java.awt.event.KeyAdapter() {
				public void keyPressed(java.awt.event.KeyEvent e) {
					//KEY �������̏���
					if ( e.getKeyCode() == KeyEvent.VK_ENTER ) {
						enterComp.requestFocus();
					}
				}
			});
		} catch ( Exception e ) {			
			//�G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("ScrollBase�̃t�H�[�J�X���䏈�������s���܂���");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;
		} finally {
			
		}
	}
	
}
