package jp.co.blueflag.shisaquick.jws.common;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;

import javax.swing.*;

/**
 * 
 * �R���{�{�b�N�X�i���͉j����
 *  : �A�v�����R���{�{�b�N�X�i���͉j�̊�{������s��
 *
 */
public class InputComboBase extends JComboBox implements FocusControl {

	private static final long serialVersionUID = 1L;	//�f�t�H���g�V���A��ID
	
	private ExceptionBase ex;						//�G���[����
	
	private String[] cmbData;						//�R���{�{�b�N�X�����p�z��
	private String strText;							//�e�L�X�g
	
	private Color Yellow = JwsConstManager.SHISAKU_ITEM_COLOR2;
	
	/**
	 * �R���X�g���N�^
	 */
	public InputComboBase(){
		//�X�[�p�[�N���X�̃R���X�g���N�^�Ăяo��
		super();
		
		this.cmbData = null;
		this.strText = "";
		this.ex = new ExceptionBase();
		this.setEditable(true);
		this.setBackground(Yellow);
		this.getEditor().getEditorComponent().setBackground(Yellow);
		this.setFont(new Font("Default", Font.PLAIN, 12));
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
			this.ex.setStrErrmsg("InputComboBase�̃t�H�[�J�X���䏈�������s���܂���");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;
		} finally {
			
		}
	}
	
	
	/**
	 * �R���{�{�b�N�X�����p�z�� �Q�b�^�[
	 * @return cmbData : �R���{�{�b�N�X�����p�z��̒l��Ԃ�
	 */
	public String[] getCmbData() {
		return cmbData;
	}
	/**
	 * �R���{�{�b�N�X�����p�z�� �Z�b�^�[
	 * @param cmbData : �R���{�{�b�N�X�����p�z��̒l���i�[����
	 */
	public void setCmbData(String[] _cmbData) {
		this.cmbData = _cmbData;
	}

	/**
	 * �e�L�X�g �Q�b�^�[
	 * @return strText : �e�L�X�g�̒l��Ԃ�
	 */
	public String getStrText() {
		return strText;
	}
	/**
	 * �e�L�X�g �Z�b�^�[
	 * @param strText : �e�L�X�g�̒l���i�[����
	 */
	public void setStrText(String _strText) {
		this.strText = _strText;
	}

}
