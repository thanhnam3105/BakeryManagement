package jp.co.blueflag.shisaquick.jws.disp;

import jp.co.blueflag.shisaquick.jws.base.SubFrame;
import jp.co.blueflag.shisaquick.jws.common.ExceptionBase;
import jp.co.blueflag.shisaquick.jws.common.JwsConstManager;
import jp.co.blueflag.shisaquick.jws.panel.ManufacturingPanel;
import jp.co.blueflag.shisaquick.jws.panel.PrototypeListPanel;

/**
 * 
 * �yA02-07�z �����H���T�u���
 *  : �����H���T�u��ʂ̑�����s��
 * 
 * @author TT.katayama
 * @since 2009/04/03
 */
public class ManufacturingSubDisp extends SubFrame {
	private static final long serialVersionUID = 1L;
	
	private ManufacturingPanel manufacturingPanel;	//�����H���p�l���N���X
	private ExceptionBase ex;								//�G���[����N���X

	/**
	 * �R���X�g���N�^ 
	 * @param strOutput : ��ʃ^�C�g��
	 * @throws ExceptionBase 
	 */
	public ManufacturingSubDisp(String strOutput) throws ExceptionBase {
		//�P�D�X�[�p�[�N���X�̃R���X�g���N�^���`�i��ʃ^�C�g���ݒ�j
		super(JwsConstManager.JWS_TITLE + "�@"+strOutput);

		try {
			//�Q�D��ʂ̈ʒu�A�T�C�Y���w��
			//2012/02/22 TT H.SHIMA Java6�Ή� start
//			this.setSize(320, 550);
			this.setSize(332, 559);
			//2012/02/22 TT H.SHIMA Java6�Ή� end
			this.setLocationRelativeTo(null);
			
			//�R�D�����H���p�l���C���X�^���X�𐶐�
			this.manufacturingPanel = new ManufacturingPanel(); 
			this.getContentPane().add(this.manufacturingPanel);
			
			//�S�D��ʂ�\����Ԃɂ���
			this.setVisible(false);

		} catch (ExceptionBase eb) {
			throw eb;
			
		} catch (Exception e) {
			//�G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("�����H����ʂ̃R���X�g���N�^�����s���܂����B");
			this.ex.setStrErrShori("ManufacturingSubDisp");
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw this.ex;
			
		} finally {
			
		}
	}
	
	/**
	 * �����H���p�l���Z�b�^�[&�Q�b�^�[
	 */
	public ManufacturingPanel getManufacturingPanel() {
		return manufacturingPanel;
	}
	public void setManufacturingPanel(ManufacturingPanel manufacturingPanel) {
		this.manufacturingPanel = manufacturingPanel;
	}
	
}