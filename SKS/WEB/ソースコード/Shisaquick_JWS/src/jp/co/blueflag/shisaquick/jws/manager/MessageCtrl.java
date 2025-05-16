package jp.co.blueflag.shisaquick.jws.manager;

import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.ArrayList;

import javax.swing.JScrollBar;

import jp.co.blueflag.shisaquick.jws.common.*;
import jp.co.blueflag.shisaquick.jws.data.DataCtrl;
import jp.co.blueflag.shisaquick.jws.base.*;

/**
 * 
 * ���b�Z�[�W����
 *  : �V�X�e�����ɂĈ������b�Z�[�W�̑�����s��
 *  
 *  @author TT.katayama
 *  @since 2009/04/22
 *
 */
public class MessageCtrl extends SubFrame {
	private static final long serialVersionUID = 1L;
	
	//OK�i�����j�{�^���N���b�N���̃R�}���h����
	private final String OK_CLICK = "okClick";
	
	private PanelBase msgPanel;			//���b�Z�[�W�z�u�p�l��
	private LabelBase msgLabel;			//���b�Z�[�W���x��
	
	private TabBase msgTab;				//���b�Z�[�W�^�u
	private ScrollBase[] tabScroll;		//�^�u�p�X�N���[���p�l��
	private TextAreaBase[] tabText;		//�^�u�p�e�L�X�g

	private ScrollBase msgScroll;			//���b�Z�[�W�X�N���[��
	private TextAreaBase msgText;		//���b�Z�[�W�e�L�X�g
	private ButtonBase okButton;			//�����{�^��

	//2012/02/22 TT H.SHIMA Java6�Ή� start
//	private int dispWidth = 400;			//��ʕ�
	private int dispWidth = 412;
//	private int dispHeight = 400;			//��ʍ���
	private int dispHeight = 412;
	//2012/02/22 TT H.SHIMA Java6�Ή� end
	private final int btnWidth = 80;		//�{�^����
	private final int btnHeight = 40;		//�{�^������
	
	private String DebugLevel = "";
	
	private ExceptionBase ex;
	
	/**
	 * �R���X�g���N�^
	 */
	public MessageCtrl() {
		//�X�[�p�[�N���X�̃R���X�g���N�^�Ăяo��
		super("���b�Z�[�W�{�b�N�X");
		
		try{
			final int MSG_COUNT = 3;
			
			///
			/// ��ʂ̈ʒu�A�T�C�Y���w��
			///
			this.setSize(dispWidth, dispHeight);
			this.setLocationRelativeTo(null);
					
			///
			/// ���b�Z�[�W�p�l���̐���
			///
			this.msgPanel = new PanelBase();
			this.msgPanel.setLayout(null);
			this.msgPanel.setBounds(0, 0, dispWidth, dispHeight);

			///
			/// �����{�^���̐���
			///
			this.okButton = new ButtonBase();
			this.msgPanel.setBounds(0, 0, dispWidth, dispHeight);
			//�ʒu�E�T�C�Y�̐ݒ�
			this.okButton.setBounds((dispWidth/2) - (btnWidth/2),dispHeight - (btnHeight * 2),btnWidth,btnHeight);
			//�\���e�L�X�g��ݒ�
			this.okButton.setText("����");	
			//�N���b�N�C�x���g��ݒ�
			this.okButton.addActionListener(this.getActionEvent());
			this.okButton.setActionCommand(this.OK_CLICK);
			//�p�l���ɒǉ�
			this.msgPanel.add(this.okButton);

			///
			/// ���x���̏�����
			///
			this.msgLabel = new LabelBase();
			this.msgLabel.setBounds(5,10,dispWidth - 15,20);
			this.msgLabel.setText("");
			this.msgPanel.add(this.msgLabel);
			
			///
			/// ���b�Z�[�W�^�u�̐���
			///
			this.msgTab = new TabBase();
			//�ʒu�E�T�C�Y�̐ݒ�
			//2012/02/22 TT H.SHIMA Java6�Ή� start
//			this.msgTab.setBounds(5,50,dispWidth - 15,this.okButton.getY() - 60);
			this.msgTab.setBounds(5,50,dispWidth - 27,this.okButton.getY() - 60);
			//2012/02/22 TT H.SHIMA Java6�Ή� end

			///
			/// �^�u�p �X�N���[���p�l���A�e�L�X�g�̐���
			///
			this.tabScroll = new ScrollBase[MSG_COUNT];
			this.tabText = new TextAreaBase[MSG_COUNT];
			for( int i=0; i<MSG_COUNT; i++ ) {
				//�e�L�X�g�̐���
				this.tabText[i] = new TextAreaBase();
				this.tabText[i].setLineWrap(true);
				this.tabText[i].setEditable(false);
				this.tabText[i].setText("");
				this.tabText[i].setBackground(Color.WHITE);
				//�X�N���[���p�l���̐���
				this.tabScroll[i] = new ScrollBase(this.tabText[i]);
			}
			
			///
			/// �X�N���[���p�l�����^�u�ɐݒ�
			///
			this.msgTab.addTab("�J�e�S��", this.tabScroll[0]);
			this.msgTab.addTab("�G���[���b�Z�[�W", this.tabScroll[1]);
			this.msgTab.addTab("�V�X�e���G���[���b�Z�[�W", this.tabScroll[2]);
			this.msgTab.setVisible(false);
			//�p�l���ɒǉ�
			this.msgPanel.add(this.msgTab);
			
			///
			/// ���b�Z�[�W�p �e�L�X�g�̐���
			///
			this.msgText = new TextAreaBase();
			this.msgText.setLineWrap(true);
			this.msgText.setEditable(false);
			this.msgText.setText("");
			
			///
			/// ���b�Z�[�W�p �X�N���[���p�l���̐���
			///
			this.msgScroll = new ScrollBase(this.msgText);
			this.msgScroll.setBounds(this.msgTab.getBounds());
			this.msgScroll.setVerticalScrollBarPolicy(ScrollBase.VERTICAL_SCROLLBAR_AS_NEEDED);
			this.msgScroll.setHorizontalScrollBarPolicy(ScrollBase.HORIZONTAL_SCROLLBAR_NEVER);
			this.msgScroll.setVisible(false);
			this.msgPanel.add(this.msgScroll);
			
			///
			/// �t���[���Ƀp�l����ݒ�
			///
			Container contentPane = this.getContentPane();
			contentPane.add (this.msgPanel);
			
			// �t���[�����\���ɐݒ�
			this.setVisible(false);
						
		}catch(Exception e){
			//�G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("���b�Z�[�W����̃R���X�g���N�^�����s���܂���");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			
			this.PrintErrMessage(ex);
		}

		
	}
	
	/**
	 * Result���b�Z�[�W�\��
	 *  : �ݒ肳��Ă��郁�b�Z�[�W���_�C�A���O�ɂĕ\������
	 */
	public void PrintMessage(ResultData resultData) {
		//��ʂ��\��
		this.setVisible(false);
		
		//��ʈʒu�A�T�C�Y�̍Đݒ�
		//2012/02/22 TT H.SHIMA Java6�Ή� start
//		this.dispWidth = 400;
		this.dispWidth = 412;
//		this.dispHeight = 400;
		this.dispHeight = 412;
		//2012/02/22 TT H.SHIMA Java6�Ή� end
		this.setSize(dispWidth, dispHeight);
		this.setLocationRelativeTo(null);

		//���b�Z�[�W���x���̐ݒ�
		this.msgLabel.setText("���s����");
		
		//�f�o�b�O���x���擾
		DebugLevel = resultData.getStrDebuglevel();
		
		//�{�^���̈ʒu�E�T�C�Y�̐ݒ�
		//2012/02/22 TT H.SHIMA Java6�Ή� start
//		this.okButton.setBounds((dispWidth/2) - (btnWidth/2),dispHeight - (btnHeight + 30),btnWidth,btnHeight);
		this.okButton.setBounds((dispWidth/2) - (btnWidth/2),dispHeight - (btnHeight + 42),btnWidth,btnHeight);
		//2012/02/22 TT H.SHIMA Java6�Ή� end
		
		//�G���[���b�Z�[�W
		StringBuffer strErrMsg = new StringBuffer();
		strErrMsg.append(resultData.getStrErrorMsg().replaceAll("\\\\n", "\n"));
		
		//�f�o�b�O���x�����u0�v�̏ꍇ
		if(DebugLevel.equals("0")){
			
			//���b�Z�[�W�\��
			PrintMessageString(strErrMsg.toString());
			
		}else{
			
			//��\��
			msgScroll.setVisible(false);
			msgTab.setVisible(false);
			
			msgTab.setSelectedIndex(1);
			
			//�e�e�L�X�g�Ƀ��b�Z�[�W��ݒ�
			StringBuffer strCategory = new StringBuffer();
			
			//�V�X�e���G���[���b�Z�[�W
			StringBuffer strSystemErr = new StringBuffer();
			strSystemErr.append(resultData.getStrSystemMsg().replaceAll("\\\\n", "\n"));
			
			strCategory.append("�������� : " + resultData.getStrClassNm());
			strCategory.append("\n�G���[�R�[�h : " + resultData.getStrErrorCd());
			strCategory.append("\n�G���[���b�Z�[�W�ԍ� : " + resultData.getStrErrmsgNo());
			this.tabText[0].setText(strCategory.toString());
			this.tabText[1].setText(strErrMsg.toString());
			this.tabText[2].setText(strSystemErr.toString());
			
			//�^�u�̈ʒu�E�T�C�Y�̐ݒ�
			//2012/02/22 TT H.SHIMA Java6�Ή� start
//			this.msgTab.setBounds(5,30,dispWidth - 15,this.okButton.getY() - 35);
			this.msgTab.setBounds(5,30,dispWidth - 27,this.okButton.getY() - 35);
			//2012/02/22 TT H.SHIMA Java6�Ή� end
			this.msgTab.setVisible(true);
			
		}
		
		//��ʂ�\��
		this.setVisible(true);
	}
	
	/**
	 * ���ʃG���[���b�Z�[�W�\��
	 *  : BaseException�N���X���G���[���e���擾���A���b�Z�[�W���_�C�A���O�ɂĕ\������
	 * @param printBexception : ���ʃG���[�N���X
	 */
	public void PrintErrMessage(ExceptionBase printBexception) {
		//��ʂ��\��
		this.setVisible(false);
		
		//��ʈʒu�A�T�C�Y�̍Đݒ�
		//2012/02/22 TT H.SHIMA Java6�Ή� start
//		this.dispWidth = 400;
		this.dispWidth = 412;
//		this.dispHeight = 400;
		this.dispHeight = 412;
		//2012/02/22 TT H.SHIMA Java6�Ή� end
		this.setSize(dispWidth, dispHeight);
		this.setLocationRelativeTo(null);

		//���b�Z�[�W���x���̐ݒ�
		this.msgLabel.setText("���s����");

		//�{�^���̈ʒu�E�T�C�Y�̐ݒ�
		//2012/02/22 TT H.SHIMA Java6�Ή� start
//		this.okButton.setBounds((dispWidth/2) - (btnWidth/2),dispHeight - (btnHeight + 30),btnWidth,btnHeight);
		this.okButton.setBounds((dispWidth/2) - (btnWidth/2),dispHeight - (btnHeight + 42),btnWidth,btnHeight);
		//2012/02/22 TT H.SHIMA Java6�Ή� end
		
		//�f�o�b�O���x�����u0�v�̏ꍇ
		if(DebugLevel.equals("0") || DebugLevel.equals("")){
			
			//���b�Z�[�W�\��
			PrintMessageString(printBexception.getStrErrmsg());
			
		}else{
			
			//��\��
			msgScroll.setVisible(false);
			msgTab.setVisible(false);
			
			msgTab.setSelectedIndex(1);
			
			//�e�e�L�X�g�Ƀ��b�Z�[�W��ݒ�
			StringBuffer strCategory = new StringBuffer();
			strCategory.append("�������� : " + printBexception.getStrErrShori());
			strCategory.append("\n�G���[�R�[�h : " + printBexception.getStrErrCd());
			strCategory.append("\n�G���[���b�Z�[�W�ԍ� : " + printBexception.getStrMsgNo());
			this.tabText[0].setText(strCategory.toString());
			this.tabText[1].setText(printBexception.getStrErrmsg());				//�G���[���b�Z�[�W
			this.tabText[2].setText(printBexception.getStrSystemMsg());		//�V�X�e���G���[���b�Z�[�W
			
			//�^�u�̈ʒu�E�T�C�Y�̐ݒ�
			//2012/02/22 TT H.SHIMA Java6�Ή� start
//			this.msgTab.setBounds(5,30,dispWidth - 15,this.okButton.getY() - 35);
			this.msgTab.setBounds(5,30,dispWidth - 27,this.okButton.getY() - 35);
			//2012/02/22 TT H.SHIMA Java6�Ή� end
			this.msgTab.setVisible(true);
			
		}
		
		//��ʂ�\��
		this.setVisible(true);
	}
	
	/**
	 * �w�蕶����\�����b�Z�[�W�{�b�N�X
	 *  : �w�蕶��������b�Z�[�W�{�b�N�X�ŕ\������
	 */
	public void PrintMessageString(String strMessage) {
		//��ʂ��\��
		this.setVisible(false);
		
		//��ʈʒu�A�T�C�Y�̍Đݒ�
		//2012/02/22 TT H.SHIMA Java6�Ή� start
//		this.dispWidth = 400;
		this.dispWidth = 412;
//		this.dispHeight = 200;
		this.dispHeight = 212;
		//2012/02/22 TT H.SHIMA Java6�Ή� start
		this.setSize(dispWidth, dispHeight);
		this.setLocationRelativeTo(null);

		//���b�Z�[�W���x���̐ݒ�
		this.msgLabel.setText("���s����");

		//�{�^���̈ʒu�E�T�C�Y�̐ݒ�
		//2012/02/22 TT H.SHIMA Java6�Ή� start
//		this.okButton.setBounds((dispWidth/2) - (btnWidth/2),dispHeight - (btnHeight + 30),btnWidth,btnHeight);
		this.okButton.setBounds((dispWidth/2) - (btnWidth/2),dispHeight - (btnHeight + 42),btnWidth,btnHeight);
		//2012/02/22 TT H.SHIMA Java6�Ή� end
		
		//��\��
		msgScroll.setVisible(false);
		msgTab.setVisible(false);
		
		//�e�L�X�g��\��
		this.msgText.setText(strMessage);
		this.msgText.setBackground(this.getBackground());
		
		//�X�N���[���̈ʒu�E�T�C�Y�̐ݒ�
		//2012/02/22 TT H.SHIMA Java6�Ή� start
//		this.msgScroll.setBounds(5,30,dispWidth - 15,this.okButton.getY() - 35);
		this.msgScroll.setBounds(5,30,dispWidth - 27,this.okButton.getY() - 35);
		//2012/02/22 TT H.SHIMA Java6�Ή� end
		this.msgScroll.setVisible(true);
				
		//��ʂ�\��
		this.setVisible(true);
		
	}
	
	/**
	 * ���͌����m�F�f�[�^�\�����b�Z�[�W�{�b�N�X
	 *  : ���͌����m�F�f�[�^�����b�Z�[�W�{�b�N�X�ŕ\������
	 * @throws ExceptionBase 
	 */
	public void PrintMessageGenryoCheck() throws ExceptionBase {
		
		try {
			//��ʂ��\��
			this.setVisible(false);
			
			//��ʈʒu�A�T�C�Y�̍Đݒ�
			//2012/02/22 TT H.SHIMA Java6�Ή� start
//			this.dispWidth = 400;
			this.dispWidth = 412;
//			this.dispHeight = 400;
			this.dispHeight = 412;
			//2012/02/22 TT H.SHIMA Java6�Ή� end
			this.setSize(dispWidth, dispHeight);
			this.setLocationRelativeTo(null);
	
			//���b�Z�[�W���x���̐ݒ�
			this.msgLabel.setText("���͌����m�F�f�[�^�m�F");
	
			//�{�^���̈ʒu�E�T�C�Y�̐ݒ�
			//2012/02/22 TT H.SHIMA Java6�Ή� start
//			this.okButton.setBounds((dispWidth/2) - (btnWidth/2),dispHeight - (btnHeight + 30),btnWidth,btnHeight);
			this.okButton.setBounds((dispWidth/2) - (btnWidth/2),dispHeight - (btnHeight + 42),btnWidth,btnHeight);
			//2012/02/22 TT H.SHIMA Java6�Ή� end
			
			//��\��
			msgScroll.setVisible(false);
			msgTab.setVisible(false);
			
			//�ύX�f�[�^�L��
			boolean hrnkouFg = false;
			
			//���͌����ύX�m�F���� �\���p������
			StringBuffer strMessage = new StringBuffer();
			
			//�����f�[�^
			ArrayList aryMateChkData = DataCtrl.getInstance().getMaterialMstData().getAryMateChkData();
			
			//�z���f�[�^
			ArrayList aryHaigoData = new ArrayList(DataCtrl.getInstance().getTrialTblData().SearchHaigoData(0));
			
			
			//�ύX����Ă��錴���f�[�^�̒l���擾
			for ( int i=0; i<aryMateChkData.size(); i++ ) {
				
				MaterialData mateData = (MaterialData)aryMateChkData.get(i);
				
				//�z���f�[�^�̃L�[���ڂƈ�v����l��ݒ�
				for ( int j=0; j<aryHaigoData.size(); j++ ) {
				
					MixedData mixData = (MixedData)aryHaigoData.get(j); 
					
					String strNotGenryoMsg = "�����͑��݂��܂���"; 
					
					//�R�����g�s�̏ꍇ�͏������Ȃ�
					if(ChkNull(mixData.getStrGenryoCd()).equals("999999")){
						
						
					}else{
						
						//����̷����ڂ̏ꍇ
						if ( mateData.getStrGenryocd().equals(mixData.getStrGenryoCd()) 
								&& mateData.getIntKaishacd() == mixData.getIntKaishaCd()
								&& mateData.getIntBushocd() == mixData.getIntBushoCd()) {
							
							//���͒l�̓��e�m�F
							//���͒l���e�ɕύX���Ȃ��ꍇ
							if(mateData.getStrGenryonm().equals(mixData.getStrGenryoNm())
									&& ChkNull(mateData.getDciGanyu()).equals(ChkNull(mixData.getDciGanyuritu()))
									&& ChkNull(mateData.getDciSakusan()).equals(ChkNull(mixData.getDciSakusan()))
									&& ChkNull(mateData.getDciShokuen()).equals(ChkNull(mixData.getDciShokuen()))
									&& ChkNull(mateData.getDciSousan()).equals(ChkNull(mixData.getDciSosan()))
									//ADD start 20121031 QP@20505
									&& ChkNull(mateData.getDciMsg()).equals(ChkNull(mixData.getDciMsg()))
									//ADD end 20121031 QP@20505
								){
								
								//�������Ȃ�								
								
							//���͒l���e�ɕύX������ꍇ
							}else{
								
								hrnkouFg = true;
								
								//���s�i2���ڈȏ�́j
								if ( strMessage.length() != 0 ) strMessage.append("\n");
								
								//���������݂���ꍇ
								if ( !mateData.getStrGenryonm().equals(strNotGenryoMsg) ) {
									
									strMessage.append("---------------------------------------------------------");
									strMessage.append("\n�H�����F" + mixData.getIntKoteiNo());
									strMessage.append("\n�����R�[�h�F" + ChkNull(mateData.getStrGenryocd()) );
									strMessage.append("\n��Ж��F" + ChkNull(mateData.getStrKaishanm()) );
									strMessage.append("\n�������F" + ChkNull(mateData.getStrBushonm()) );
									strMessage.append("\n---------------------------------------------------------");
									strMessage.append("\n���������F" + ChkTouroku(mixData.getStrGenryoNm()) + "�@���@" + ChkTouroku(mateData.getStrGenryonm()) );
									strMessage.append("\n�����ܗL���F" + ChkTouroku(mixData.getDciGanyuritu()) + "�@���@" + ChkTouroku(mateData.getDciGanyu()) );
									strMessage.append("\n���|�_�F" + ChkTouroku(mixData.getDciSakusan()) + "�@���@" + ChkTouroku(mateData.getDciSakusan()) );
									strMessage.append("\n���H���F" + ChkTouroku(mixData.getDciShokuen()) + "�@���@" + ChkTouroku(mateData.getDciShokuen()) );
									strMessage.append("\n�����_�F" + ChkTouroku(mixData.getDciSosan()) + "�@���@" + ChkTouroku(mateData.getDciSousan()) );
									//ADD start 20121031 QP@20505
									strMessage.append("\n��MSG�F" + ChkTouroku(mixData.getDciMsg()) + "�@���@" + ChkTouroku(mateData.getDciMsg()) );
									//ADD end 20121031 QP@20505
									
									strMessage.append("\n---------------------------------------------------------");
									
								//���������݂��Ȃ��ꍇ
								} else {
									
									strMessage.append("---------------------------------------------------------");
									strMessage.append("\n�H�����F" + mixData.getIntKoteiNo());
									strMessage.append("\n�����R�[�h�F" + ChkNull(mateData.getStrGenryocd()) );
									strMessage.append("\n��Ж��F" + ChkNull(mateData.getStrKaishanm()) );
									strMessage.append("\n�������F" + ChkNull(mateData.getStrBushonm()) );
									strMessage.append("\n---------------------------------------------------------");
									strMessage.append("\n���������F" + ChkTouroku(mixData.getStrGenryoNm()) + "�@���@" + ChkTouroku(strNotGenryoMsg));
									strMessage.append("\n�����ܗL���F" + ChkTouroku(mixData.getDciGanyuritu()) + "�@���@" + ChkTouroku(strNotGenryoMsg));
									strMessage.append("\n���|�_�F" + ChkTouroku(mixData.getDciSakusan()) + "�@���@" + ChkTouroku(strNotGenryoMsg));
									strMessage.append("\n���H���F" + ChkTouroku(mixData.getDciShokuen()) + "�@���@" + ChkTouroku(strNotGenryoMsg));
									strMessage.append("\n�����_�F" + ChkTouroku(mixData.getDciSosan()) + "�@���@" + ChkTouroku(strNotGenryoMsg));
									//ADD start 20121031 QP@20505
									strMessage.append("\n��MSG�F" + ChkTouroku(mixData.getDciMsg()) + "�@���@" + ChkTouroku(mateData.getDciMsg()) );
									//ADD end 20121031 QP@20505
									
									strMessage.append("\n---------------------------------------------------------");
									
								}
								
								//�������s��ꂽ�f�[�^������������(���d��r��j�~���邽��)
								aryHaigoData.set(j, new MixedData());
								
							}
						}
					}
				}
			}
			
			
			//�ύX����Ă��錴���f�[�^������ꍇ
			if(hrnkouFg){
				
				
			//�ύX����Ă��錴���f�[�^���Ȃ��ꍇ
			}else{
				
				strMessage.append("�ύX����Ă��錴���͂���܂���");
				
			}
			
			//���b�Z�[�W��ݒ�
			this.msgText.setText(strMessage.toString());
			this.msgText.setBackground(Color.WHITE);
			
			//�X�N���[���̈ʒu�E�T�C�Y�̐ݒ�
			//2012/02/22 TT H.SHIMA Java6�Ή� start
//			this.msgScroll.setBounds(5,30,dispWidth - 15,this.okButton.getY() - 35);
			this.msgScroll.setBounds(5,30,dispWidth - 27,this.okButton.getY() - 35);
			//2012/02/22 TT H.SHIMA Java6�Ή� end
			this.msgScroll.setVisible(true);
			
			//��ʂ�\��
			this.setVisible(true);
			
		} catch (ExceptionBase e) {
			throw e;
			
		} catch (Exception e) {
			
			e.printStackTrace();
			//�G���[�ݒ�
			ExceptionBase ex = new ExceptionBase();
			ex.setStrErrCd("");
			ex.setStrErrmsg("���͌����m�F�f�[�^�\�����b�Z�[�W�{�b�N�X���������s���܂���");
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getMessage());
			throw ex;
			
		} finally {
			
		}
		
	}

	/**
	 * ���Z�����Q�ƃf�[�^�\�����b�Z�[�W�{�b�N�X
	 *  : ���Z�����Q�ƃf�[�^�����b�Z�[�W�{�b�N�X�ŕ\������
	 * @throws ExceptionBase 
	 */
	public void PrintMessageShisanRirekiSansyo() throws ExceptionBase {
		try {
			//��ʂ��\��
			this.setVisible(false);
			
			//��ʈʒu�A�T�C�Y�̍Đݒ�
			//2012/02/22 TT H.SHIMA Java6�Ή� start
//			this.dispWidth = 400;
			this.dispWidth = 412;
//			this.dispHeight = 400;
			this.dispHeight = 412;
			//2012/02/22 TT H.SHIMA Java6�Ή� end
			this.setSize(dispWidth, dispHeight);
			this.setLocationRelativeTo(null);
	
			//���b�Z�[�W���x���̐ݒ�
			this.msgLabel.setText("���Z�����Q��");
	
			//�{�^���̈ʒu�E�T�C�Y�̐ݒ�
			//2012/02/22 TT H.SHIMA Java6�Ή� start
//			this.okButton.setBounds((dispWidth/2) - (btnWidth/2),dispHeight - (btnHeight + 30),btnWidth,btnHeight);
			this.okButton.setBounds((dispWidth/2) - (btnWidth/2),dispHeight - (btnHeight + 42),btnWidth,btnHeight);
			//2012/02/22 TT H.SHIMA Java6�Ή� end
			
			//��\��
			msgScroll.setVisible(false);
			msgTab.setVisible(false);
			
			//�\���p������
			StringBuffer strMessage = new StringBuffer();
			
			//�f�[�^�̕\��
			strMessage.append("---------------------------------------------------------");
			strMessage.append("\n���Z��     \t �F �T���v��NO");
			strMessage.append("\n---------------------------------------------------------");
			
			//�Q�ƃf�[�^��\������
			ArrayList aryShisanRireki = DataCtrl.getInstance().getShisanRirekiKanriData().getAryShisanRirekiData();
			for ( int i=0; i<aryShisanRireki.size(); i++ ) {
				ShisanData shisanRirekiData = (ShisanData)aryShisanRireki.get(i);  

				strMessage.append("\n");
				String strShisanHi = shisanRirekiData.getStrShisanHi();
				String strSampleNo = shisanRirekiData.getStrSampleNo();
				strMessage.append(strShisanHi + "\t �F " + ChkTouroku(strSampleNo));
				
			}
			
			strMessage.append("\n---------------------------------------------------------");

			this.msgText.setText(strMessage.toString());
			this.msgText.setBackground(Color.WHITE);
			
			//�X�N���[���̈ʒu�E�T�C�Y�̐ݒ�
			//2012/02/22 TT H.SHIMA Java6�Ή� start
//			this.msgScroll.setBounds(5,30,dispWidth - 15,this.okButton.getY() - 35);
			this.msgScroll.setBounds(5,30,dispWidth - 27,this.okButton.getY() - 35);
			//2012/02/22 TT H.SHIMA Java6�Ή� end
			this.msgScroll.setVisible(true);
			
			//��ʂ�\��
			this.setVisible(true);
			
		} catch (Exception e) {
			//�G���[�ݒ�
			ExceptionBase ex = new ExceptionBase();
			ex.setStrErrCd("");
			ex.setStrErrmsg("���Z�����Q�ƃf�[�^�\�����b�Z�[�W�{�b�N�X���������s���܂���");
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getMessage());
			throw ex;
			
		} finally {
			
		}
	}
		
	/**
	 * Action�C�x���g�擾
	 * @return ActionListener
	 */
	private ActionListener getActionEvent() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//OK(����)�{�^���N���b�N��
				if ( e.getActionCommand().equals(OK_CLICK) ) {
					msgText.setBackground(Color.WHITE);
					Exit();
				}
			}
		};
	}
	
	/**
	 * Null�`�F�b�N
	 *  : Null�̏ꍇ�́A���Ԃ�
	 * @param strValue : �`�F�b�N����l
	 * @return �`�F�b�N��̒l
	 */
	private String ChkNull(Object strValue) {
		String ret = "";
		
		if ( strValue != null ) {
			ret = strValue.toString();
			
		}	
		return ret;
		
	}

	/**
	 * ���o�^�`�F�b�N(String)
	 *  : Null�̏ꍇ�́A���Ԃ�
	 * @param strValue : �`�F�b�N����l
	 * @return �`�F�b�N��̒l
	 */
	private String ChkTouroku(String strValue) {
		String ret = "���o�^";
		
		if ( strValue != null ) {
			ret = strValue;
			
		}	
		return ret;
		
	}
	
	/**
	 * ���o�^�`�F�b�N(BigDecimal)
	 *  : Null�̏ꍇ�́A���Ԃ�
	 * @param strValue : �`�F�b�N����l
	 * @return �`�F�b�N��̒l
	 */
	private String ChkTouroku(BigDecimal dciValue) {
		String ret = "���o�^";
		
		if ( dciValue != null ) {
			ret = dciValue.toString();
			
		}	
		return ret;
		
	}

	/**
	 * �f�o�b�O���x���Q�b�^�[���Z�b�^�[
	 */
	public String getDebugLevel() {
		return DebugLevel;
	}

	public void setDebugLevel(String debugLevel) {
		DebugLevel = debugLevel;
	}
	
	
}
