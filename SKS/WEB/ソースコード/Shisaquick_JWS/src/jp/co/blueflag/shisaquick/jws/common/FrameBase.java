package jp.co.blueflag.shisaquick.jws.common;

import java.awt.Component;
import java.awt.Container;
import java.awt.FocusTraversalPolicy;
import java.util.ArrayList;

import javax.swing.*;

/**
 * 
 * ��ʑ��� : ��ʂ̊��N���X
 *  �t�H�[�J�X�̐���ɂ��Ă�Swing�̋@�\��p���čs���@
 *  �i�t�H�[�J�X���[���ݒ�{�L�[�w��j
 *
 */
public class FrameBase extends JFrame  {

	private static final long serialVersionUID = 1L;	//�f�t�H���g�V���A��ID
	
	private ExceptionBase ex;						//�G���[����
	
	/**
	 * ��ʑ���R���X�g���N�^ : �C���X�^���X�𐶐�
	 * @param title : ��ʃ^�C�g��
	 */
	public FrameBase(String title) {
		//�X�[�p�[�N���X�̃R���X�g���N�^�Ăяo��
		super(title);
		
		this.ex = null;
	}

	/**
	 * �t�H�[�J�X�R���g���[��
	 *  : Enter�L�[���䏈���ƃt�H�[�J�X���̐ݒ���s��
	 * @param aryFocusComp : �t�H�[�J�X�ɐݒ肷���ʃR���|�[�l���g.
	 *  aryFocusComp[a][b] :  [a : ��ʍs], [b : ��ʗ�]
	 */
	public void setFocusControl(final JComponent[][] aryFocusComp) {
		JComponent setComp = null;
		JComponent enterComp = null;
		final ArrayList lstComp = new ArrayList();
		
		//�������z���1�����z��ɕϊ����A
		//ENTER�����ݒ�
		for ( int i = 0; i < aryFocusComp.length; i++ ) {
			for ( int j= 0; j < aryFocusComp[i].length; j++ ) {
				setComp =aryFocusComp[i][j];
				
				if ( i < aryFocusComp.length - 1 ) {
					enterComp = aryFocusComp[i + 1][0];
				} else {
					enterComp = aryFocusComp[0][0];
				}
				
				try {
					this.setEnterFocusControl(setComp, enterComp);
				} catch (ExceptionBase e) {
					e.getStackTrace();
				}
				
				lstComp.add(setComp);
			}
		}
		
		//�R���|�[�l���g�������Ă��Ȃ��ꍇ�A�����I��
		if ( lstComp.size() == 0 ) {
			return;
		}
		
		//�t�H�[�J�X���ݒ�
		this.setFocusTraversalPolicy(new FocusTraversalPolicy() {			
			//�����t�H�[�J�X�ꏊ
			public Component getFirstComponent(Container focusCycleRoot) {
				return (Component) lstComp.get(0);
			}
			//�ŏI�t�H�[�J�X�ꏊ
			public Component getLastComponent(Container focusCycleRoot) {
				return (Component) lstComp.get(lstComp.size()-1);
			}
			//���̃t�H�[�J�X�ړ��ꏊ
			public Component getComponentAfter(Container focusCycleRoot, Component aComponent) {
				int index = lstComp.indexOf(aComponent);
				return (Component) lstComp.get((index + 1) % lstComp.size());
			}
			//�O�̃t�H�[�J�X�ړ��ꏊ
			public Component getComponentBefore(Container focusCycleRoot, Component aComponent) {
				int index = lstComp.indexOf(aComponent);
				return (Component) lstComp.get((index - 1 + lstComp.size()) % lstComp.size());
			}
			//�f�t�H���gComponent
			public Component getDefaultComponent(Container focusCycleRoot) {
				return (Component) lstComp.get(0);
			}
			
		} );
		
	}

	/***
	 * Enter�������t�H�[�J�X�R���g���[��
	 *  : Enter�������t�H�[�J�X���䏈�����s��
	 * @param setBaseComp : �ΏۃR���|�[�l���g��Base�N���X(TableBase��)
	 * @param enterComp : Enter���̃t�H�[�J�X�ړ���R���|�[�l���g
	 */
	public void setEnterFocusControl(JComponent setBaseComp, JComponent enterComp) throws ExceptionBase {
		try { 
			//FocusControl�C���^�[�t�F�[�X�̃��\�b�h���g�p
			FocusControl focusCtrl = (FocusControl)setBaseComp;
			focusCtrl.setEnterFocusControl(enterComp);
		} catch ( Exception e ) {
			//�G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("FrameBase�̃t�H�[�J�X���䏈�������s���܂���");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;
		} finally {
			
		}
	}

}
