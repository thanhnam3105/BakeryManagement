package jp.co.blueflag.shisaquick.jws.common;

import javax.swing.JComponent;

/**
 * 
 * �t�H�[�J�X�R���g���[���p�C���^�[�t�F�[�X
 *
 */
public interface FocusControl {

	/**
	 * Enter�������t�H�[�J�X�R���g���[��
	 * @param enterComp : Enter���̃t�H�[�J�X�ړ���JComponent
	 */
	public void setEnterFocusControl(final JComponent enterComp) throws ExceptionBase;
}
