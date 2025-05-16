package jp.co.blueflag.shisaquick.jws.disp;

import java.awt.Color;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import jp.co.blueflag.shisaquick.jws.base.SubFrame;
import jp.co.blueflag.shisaquick.jws.common.ExceptionBase;
import jp.co.blueflag.shisaquick.jws.common.JwsConstManager;
import jp.co.blueflag.shisaquick.jws.common.PanelBase;
import jp.co.blueflag.shisaquick.jws.label.DispTitleLabel;
import jp.co.blueflag.shisaquick.jws.panel.ColorPanel;


/**
 * 
 * �N�����_�E�����[�h�p�҂����
 * 
 * @author TT.nishigawa
 * @since 2009/06/24
 */
public class DownloadDisp  extends SubFrame {

	private static final long serialVersionUID = 1L;
	
	private PanelBase pb;						//��ʃp�l��
	private DispTitleLabel dispTitleLabel;	//��ʃ^�C�g�����x��
	private JLabel gifLabel;						//�摜�\���p���x��
	private ExceptionBase ex;					//�G���[����N���X

	/**
	 * �R���X�g���N�^ 
	 * @throws ExceptionBase 
	 */
	public DownloadDisp() throws ExceptionBase {
		//�X�[�p�[�N���X�̃R���X�g���N�^���`�i��ʃ^�C�g���ݒ�j
		super(JwsConstManager.JWS_TITLE + "�@�A�v���P�[�V�����̃_�E�����[�h");
		
		try {
			//��ʂ̈ʒu�A�T�C�Y���w��
			//2012/02/22 TT H.SHIMA Java6�Ή� start
//			this.setSize(1000, 720);
			this.setSize(1012,729);
			//2012/02/22 TT H.SHIMA Java6�Ή� end
			this.setLocationRelativeTo(null);
			this.setBackground(Color.WHITE);
			
			//�p�l���C���X�^���X�𐶐�
			this.pb = new PanelBase();
			pb.setLayout(null);
			pb.setBackground(Color.WHITE);
			
			//�R���g���[���z�u
			setControl();
			this.getContentPane().add(this.pb);
			
			//��ʂ��\����Ԃɂ���
			this.setVisible(false);

		} catch (Exception e) {
			
			//�G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("�N�����_�E�����[�h�p�҂���ʂ̃R���X�g���N�^�����s���܂����B");
			this.ex.setStrErrShori(this.getClass().toString());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			
			throw this.ex;
			
		} finally {
		}
		
	}
	
	/**
	 * �R���g���[���z�u
	 * @throws ExceptionBase 
	 */
	public void setControl() throws ExceptionBase{
		
		try {
			
			//�R���g���[���ݒ�
			
			//��ʃ^�C�g�����x���ݒ�
			this.dispTitleLabel = new DispTitleLabel();
			this.dispTitleLabel.setText("����f�[�^���擾���ł��B���΂炭���҂����������B");
			this.dispTitleLabel.setBounds(0, 290, 1000, 50);
			this.dispTitleLabel.setHorizontalAlignment(JLabel.CENTER);
			
			//�摜�\�����x���ݒ�
			ClassLoader classLoader = this.getClass().getClassLoader();
			URL resUrl = classLoader.getResource("images/bar.gif");
			ImageIcon icon = new ImageIcon( resUrl );
			this.gifLabel = new JLabel(icon);
			this.gifLabel.setBounds(0, 380, 1000, 50);
			this.gifLabel.setHorizontalAlignment(JLabel.CENTER);
			
			//�p�l���֒ǉ�
			this.pb.add(this.dispTitleLabel);
			this.pb.add(this.gifLabel);

		} catch (ExceptionBase eb) {
			
			throw eb;
			
		} catch (Exception e) {
			
			//�G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("�N�����_�E�����[�h�p�҂���ʂ̃R���X�g���N�^�����s���܂����B");
			this.ex.setStrErrShori(this.getClass().toString());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw this.ex;
			
		} finally {
		}
		
	}
	
}
