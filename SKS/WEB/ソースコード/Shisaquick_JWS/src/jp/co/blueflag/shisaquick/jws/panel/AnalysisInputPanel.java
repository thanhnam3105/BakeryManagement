package jp.co.blueflag.shisaquick.jws.panel;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JOptionPane;

import jp.co.blueflag.shisaquick.jws.base.KaishaData;
import jp.co.blueflag.shisaquick.jws.base.MaterialData;
import jp.co.blueflag.shisaquick.jws.base.XmlData;
import jp.co.blueflag.shisaquick.jws.common.ButtonBase;
import jp.co.blueflag.shisaquick.jws.common.CheckboxBase;
import jp.co.blueflag.shisaquick.jws.common.ComboBase;
import jp.co.blueflag.shisaquick.jws.common.ExceptionBase;
import jp.co.blueflag.shisaquick.jws.common.PanelBase;
import jp.co.blueflag.shisaquick.jws.common.ScrollBase;
import jp.co.blueflag.shisaquick.jws.common.TextAreaBase;
import jp.co.blueflag.shisaquick.jws.common.TextboxBase;
import jp.co.blueflag.shisaquick.jws.data.DataCtrl;
import jp.co.blueflag.shisaquick.jws.label.DispTitleLabel;
import jp.co.blueflag.shisaquick.jws.label.HeaderLabel;
import jp.co.blueflag.shisaquick.jws.label.ItemLabel;
import jp.co.blueflag.shisaquick.jws.label.LevelLabel;
import jp.co.blueflag.shisaquick.jws.manager.MessageCtrl;
import jp.co.blueflag.shisaquick.jws.manager.XmlConnection;
import jp.co.blueflag.shisaquick.jws.textbox.HankakuTextbox;
import jp.co.blueflag.shisaquick.jws.textbox.NumelicTextbox;

/**
 * 
 * �yA05-04�z ���͒l���̓p�l������p�̃N���X
 * 
 * @author k-katayama
 * @since 2009/04/03
 */
public class AnalysisInputPanel extends PanelBase {
	private static final long serialVersionUID = 1L;
	
	private KaishaData KaishaData = new KaishaData();
	
	private DispTitleLabel dispTitleLabel;				//��ʃ^�C�g�����x��
	private HeaderLabel headerLabel;					//�w�b�_�\�����x��
	private LevelLabel levelLabel;							//���x���\�����x��
	
	private ItemLabel[] itemLabelLeft;					//�������ڃ��x��
	private ItemLabel[] itemLabelOther;					//���̑����ڃ��x��
	
	private ComboBase kaishaComb;						// ��ЃR���{�{�b�N�X
	private HankakuTextbox genryoCdTextbox;		// �����R�[�h�e�L�X�g�{�b�N�X(���p)
	private TextboxBase genryoNmTextbox;			// �������e�L�X�g�{�b�N�X
	
	private TextboxBase[] numTextbox;				// �e�L�X�g�{�b�N�X(���l)
	
	private TextAreaBase hyojianTextarea;				// �\���ăe�L�X�g�G���A
	private ScrollBase hyojianScroll;						// �\���ăX�N���[���p�l��
	private TextAreaBase tenkabutuTextarea;			// �Y�����e�L�X�g�G���A
	private ScrollBase tenkabutuScroll;					// �Y�����X�N���[���p�l��
	
	private TextboxBase[] eiyouTextbox;			// �h�{�v�Z�H�i�ԍ������e�L�X�g�{�b�N�X
	
	private TextAreaBase memoTextarea;				// �����e�L�X�g�G���A
	private ScrollBase memoScroll;						// �����X�N���[���p�l��
	private HankakuTextbox nyuryokuhiTextbox;		// ���͓��e�L�X�g�{�b�N�X(���p)
	private TextboxBase nyuryokushaTextbox;		// ���͎҃e�L�X�g�{�b�N�X
	
	private CheckboxBase kakuninCheckbox;			// �m�F�`�F�b�N�{�b�N�X
	private HankakuTextbox kakuninhiTextbox;		// �m�F���e�L�X�g�{�b�N�X(���p)
	private TextboxBase kakuninTextbox;				// �m�F�҃e�L�X�g�{�b�N�X
	private CheckboxBase haishiCheckbox;			// �p�~�`�F�b�N�{�b�N�X
	private HankakuTextbox kakuteiCdTextbox;		// �m��R�[�h�e�L�X�g�{�b�N�X(���p)
	
	private ButtonBase[] button;							// �{�^��
	
	private XmlData xmlData;								//�w�l�k�f�[�^�ێ�
	private XmlConnection xmlConnection;				//�w�l�k�ʐM
	private MessageCtrl messageCtrl;					//���b�Z�[�W����
	private ExceptionBase ex;								//�G���[����
	private String strGamenId;
	
	private MaterialData selData = new MaterialData();
	boolean sinkiFg = false;
	
	//�m�F�A�X�V�A�o�^��ID
	BigDecimal torokuId;
	BigDecimal kakuninId;
	BigDecimal koshinId;
	String koshinNm;
	
	//XML�f�[�^
	private XmlData xmlJW610;
	private XmlData xmlJW820;
	private XmlData xmlJ010;
	
	//�m�F�`�F�b�N
	boolean kakuninFg = false;

//add start --------------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.13
	//�ҏW�O�������
	private String strGenryoNmOld = "";		//������
	private String strSakusanOld = "";			//�|�_
	private String strShokuenOld = "";			//�H��
	private String strSousanOld = "";			//���_
	private String strGanyuOld = "";			//���ܗL��
// ADD start 20121122 QP@20505 �ۑ�No.10
	private String strMsgOld = "";			//MSG
// ADD end 20121122 QP@20505 �ۑ�No.10
	private String strHyojiOld = "";			//�\����
	private String strTankaOld = "";			//�Y����
	private String strMemoOld = "";			//����
	private String[] strEiyonoOld = new String[5];		//�h�{�v�Z
	private String[] strWariaiOld = new String[5];		//����
	private String strKakuteiCdOld = "";		//�m��R�[�h
	private boolean blnCheckKakunin = false;	//�m�F�f�[�^�t���O
	//�m�F�f�[�^�t���O�@�Z�b�^���Q�b�^
	public boolean isBlnCheckKakunin() {
		return blnCheckKakunin;
	}
	public void setBlnCheckKakunin(boolean blnCheckKakunin) {
		this.blnCheckKakunin = blnCheckKakunin;
	}
	//�m�F�`�F�b�N�{�b�N�X�@�Z�b�^���Q�b�^
	public CheckboxBase getKakuninCheckbox() {
		return kakuninCheckbox;
	}
	public void setKakuninCheckbox(CheckboxBase kakuninCheckbox) {
		this.kakuninCheckbox = kakuninCheckbox;
	}
	
//add end --------------------------------------------------------------------------------------
		
	/**
	 * �R���X�g���N�^
	 * @param strOutput : ��ʃ^�C�g��
	 * @throws ExceptionBase
	 */
	public AnalysisInputPanel(String strOutput) throws ExceptionBase {
		//�X�[�p�[�N���X�̃R���X�g���N�^���Ăяo��
		super();

		try {
			//�P�D�p�l���̐ݒ�
			this.setPanel();
			
			//�Q�D�R���g���[���̔z�u
			this.addControlLabel(strOutput);
			this.addControl();
			
		}catch(ExceptionBase eb){
			
			throw eb;
			
		}catch(Exception e) {
			
			//�G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("���͒l���̓p�l���̃R���X�g���N�^�����s���܂����B");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;
			
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
	 * �R���g���[�����x���z�u
	 * @param strTitle : ��ʃ^�C�g��
	 */
	private void addControlLabel(String strTitle) throws ExceptionBase{
		try{
			int i;
			int x, y, width, height;
			
			int midWidth = 100;
			int defHeight = 18;
			int dispWidht = 500;
			
			///
			/// �^�C�g�����x���ݒ�
			///
			this.dispTitleLabel = new DispTitleLabel();
			this.dispTitleLabel.setText(strTitle);
			this.add(this.dispTitleLabel);
			
			///
			/// ���x�����x���ݒ�
			///
			this.levelLabel = new LevelLabel();
			this.levelLabel.setBounds(dispWidht - 65, 5, 50, 15);
			this.add(this.levelLabel);
			
			///
			/// �w�b�_���x���ݒ�
			///
			this.headerLabel = new HeaderLabel();
			this.headerLabel.setBounds(10, 22, dispWidht - 17, 15);
			this.add(this.headerLabel);
			
			///
			/// �������ڃ��x���̐ݒ�
			/// [0:���, 1:�����R�[�h, 2:������, 3:�|�_(%), 4:�H��(%), 5:���_(%) 6:���ܗL��(%),
			///  7:�\����, 8:�Y����, 9: �h�{�v�Z�H�i�ԍ�����
			///  10:����, 11:���͓�, 12:�m�F��, 13:�p�~�� ]
			///
// MOD start 20121122 QP@20505 �ۑ�No.10
//			this.itemLabelLeft = new ItemLabel[14];
			this.itemLabelLeft = new ItemLabel[15];
// MOD end 20121122 QP@20505 �ۑ�No.10
			String[] strSetText = new String[this.itemLabelLeft.length];
			strSetText[0] = "���";
			strSetText[1] = "�����R�[�h";
			strSetText[2] = "������";
			strSetText[3] = "�|�_(%)";
			strSetText[4] = "�H��(%)";
			strSetText[5] = "���_(%)";
			strSetText[6] = "���ܗL��(%)";
// ADD start 20121122 QP@20505 �ۑ�No.10
			strSetText[14] = "�l�r�f(%)";
// ADD end 20121122 QP@20505 �ۑ�No.10
			strSetText[7] = "�\����";
			strSetText[8] = "�Y����";
			strSetText[9] = "<html>�h�{�v�Z�H�i�ԍ�<br>����(%)</html>";
			strSetText[10] = "����";
			strSetText[11] = "���͓�";
			strSetText[12] = "�m�F��";
			strSetText[13] = "�p�~��";
			
			//�ݒ菈��
			x = 5;
			y = 40;
			width = midWidth;
// MOD start 20121122 QP@20505 �ۑ�No.10 ���[�v�̓r���ɍ���MSG��ǉ����邽��
//			for ( i=0; i<this.itemLabelLeft.length; i++ ) {
			for ( i=0; i<this.itemLabelLeft.length - 1; i++ ) {
// MOD end 20121122 QP@20505 �ۑ�No.10
				height = defHeight;

				//���W����
				if ( i == 9 ) {
					//�h�{�v�Z�H�i�ԍ������̏ꍇ
					y += 25;
				}
				//�T�C�Y����
				if ( i > 6 && i < 12 ) {
					//�\���� �` ���͓��̏ꍇ
					height += height;
				}
	 			
				//������
				this.itemLabelLeft[i] = new ItemLabel();
				this.itemLabelLeft[i].setText(strSetText[i]);
				this.itemLabelLeft[i].setBounds(x,y,width,height);
				//�����ڃ��x���̍��W�ݒ�
				x = this.itemLabelLeft[i].getX();
				y = this.itemLabelLeft[i].getY() + height + 5;
				//�㑵���ɐݒ�
				this.itemLabelLeft[i].setVerticalAlignment(ItemLabel.TOP);
				//�p�l���ɒǉ�
				this.add(this.itemLabelLeft[i]);

// ADD start 20121122 QP@20505 �ۑ�No.10 ���[�v�̓r��(���ܗL���̑O�j�ɍ���MSG��ǉ����邽��
				if ( i == 5) {
					//������
					this.itemLabelLeft[14] = new ItemLabel();
					this.itemLabelLeft[14].setText(strSetText[14]);
					this.itemLabelLeft[14].setBounds(x,y,width,height);
					//�����ڃ��x���̍��W�ݒ�
					x = this.itemLabelLeft[14].getX();
					y = this.itemLabelLeft[14].getY() + height + 5;
					//�㑵���ɐݒ�
					this.itemLabelLeft[14].setVerticalAlignment(ItemLabel.TOP);
					//�p�l���ɒǉ�
					this.add(this.itemLabelLeft[14]);
				}
// ADD end 20121122 QP@20505 �ۑ�No.10
			}
			
			///
			/// ���̑����ڃ��x���̐ݒ�
			/// [0�`4: �h�{�v�Z�H�i�ԍ����� ��ԍ�]
			/// [5,���͎�, 6:�m�F(�`�F�b�N�{�b�N�X), 7:�m�F��, 8:�p�~(�`�F�b�N�{�b�N�X), 9:�m��R�[�h]
			///
// MOD start 20121122 QP@20505 �ۑ�No.10
			this.itemLabelOther = new ItemLabel[10];
//			this.itemLabelOther = new ItemLabel[11];
// MOD end 20121122 QP@20505 �ۑ�No.10
			height = defHeight;
			
			// 0�`4:�h�{�v�Z�H�i�ԍ����� ��ԍ�
			x = this.itemLabelLeft[9].getX() + this.itemLabelLeft[9].getWidth() + 10;
			y = this.itemLabelLeft[9].getY() - 20;
			width = midWidth - 30;
			for ( i = 0; i < 5; i++ ) {
				this.itemLabelOther[i] = new ItemLabel();
				this.itemLabelOther[i].setText(""+ (i+1));
				this.itemLabelOther[i].setBounds(x,y,width,height);
				this.itemLabelOther[i].setBackground(Color.WHITE);
				x = this.itemLabelOther[i].getX() + this.itemLabelOther[i].getWidth();
			}
//// ADD start 20121122 QP@20505 �ۑ�No.10
//			// 14�F �l�r�f
//			i = 5;
//			this.itemLabelOther[10] = new ItemLabel();
//			this.itemLabelOther[10].setText(""+ (i+1));
//			this.itemLabelOther[10].setBounds(x,y,width,height);
//			this.itemLabelOther[10].setBackground(Color.WHITE);
//			x = this.itemLabelOther[10].getX() + this.itemLabelOther[10].getWidth();
//// ADD end 20121122 QP@20505 �ۑ�No.10
			
			//���͎�
			x = dispWidht / 2;
			y = this.itemLabelLeft[11].getY();		//���͓�
			width = midWidth;
			this.itemLabelOther[5] = new ItemLabel();
			this.itemLabelOther[5].setText("���͎�");
			this.itemLabelOther[5].setBounds(x-40,y,width,height);
			
			//�m�F(�`�F�b�N�{�b�N�X)
			x = this.itemLabelLeft[11].getX() + this.itemLabelLeft[11].getWidth() + 10;
			y = this.itemLabelLeft[11].getY() + 17;	//���͓�
			width = 50;
			this.itemLabelOther[6] = new ItemLabel();
			this.itemLabelOther[6].setText("�m�F");
			this.itemLabelOther[6].setBounds(x,y+2,width,height-2);
			this.itemLabelOther[6].setBackground(Color.WHITE);
			
			//�m�F��
			x = dispWidht / 2;
			y = this.itemLabelLeft[12].getY();		//�m�F��
			width = midWidth;
			this.itemLabelOther[7] = new ItemLabel();
			this.itemLabelOther[7].setText("�m�F��");
			this.itemLabelOther[7].setBounds(x-40,y,width,height);
			
			//�p�~(�`�F�b�N�{�b�N�X)
			x = this.itemLabelLeft[13].getX() + this.itemLabelLeft[13].getWidth() + 10;
			y = this.itemLabelLeft[13].getY() + 2;	//�p�~��
			width = 50;
			this.itemLabelOther[8] = new ItemLabel();
			this.itemLabelOther[8].setText("�p�~");
			this.itemLabelOther[8].setBounds(x,y,width,height-2);
			this.itemLabelOther[8].setBackground(Color.WHITE);
			
			//�m��R�[�h
			x = dispWidht / 2;
			y = this.itemLabelLeft[13].getY();		//�p�~��
			width = midWidth;
			this.itemLabelOther[9] = new ItemLabel();
			this.itemLabelOther[9].setText("�m��R�[�h");
			this.itemLabelOther[9].setBounds(x-40,y,width,height);
			
			//���̑����ڃ��x�����p�l���ɒǉ�
			for ( i=0; i<this.itemLabelOther.length; i++ ) {
				if ( i != 6 && i != 8 ) {
					//�㑵���ɐݒ�
					this.itemLabelOther[i].setVerticalAlignment(ItemLabel.TOP);
				}
				//�ǉ�
				this.add(this.itemLabelOther[i]);
			}
		}catch(ExceptionBase eb){
			throw eb;
			
		}catch(Exception e){
			//�G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("���͒l���̓p�l���̃R���g���[�����x���z�u���������s���܂���");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;
			
		}finally{
			
		}
		
		
	}
	
	/**
	 * �R���g���[���z�u
	 */
	private void addControl() throws ExceptionBase{
		try{
			int i, j;
			int x, y, width, height;
			
			int midWidth = 100;
			int largeWidth = 380;
			int dispWidht = 500;
			
			///
			/// ��ЃR���{�{�b�N�X�̐ݒ�
			///
			this.kaishaComb = new ComboBase();
			//���W�E�T�C�Y�ݒ�
			j = 0;
			x = this.itemLabelLeft[j].getX() + this.itemLabelLeft[j].getWidth();
			y = this.itemLabelLeft[j].getY();
			width = midWidth;
			height = this.itemLabelLeft[j].getHeight();
			this.kaishaComb.setBounds(x,y,width+100,height);
			//�p�l���ɒǉ�
			this.add(this.kaishaComb);
			
			/// 
			/// �����R�[�h�e�L�X�g�{�b�N�X(���p)�̐ݒ�
			///
			this.genryoCdTextbox = new HankakuTextbox();
			//���W�E�T�C�Y�ݒ�
			j = 1;
			x = this.itemLabelLeft[j].getX() + this.itemLabelLeft[j].getWidth();
			y = this.itemLabelLeft[j].getY();
			width = midWidth;
			height = this.itemLabelLeft[j].getHeight();
			this.genryoCdTextbox.setBounds(x,y,width,height);
			//�p�l���ɒǉ�
			this.add(this.genryoCdTextbox);
			
			///
			/// �������e�L�X�g�{�b�N�X�̐ݒ�
			///
			this.genryoNmTextbox = new TextboxBase();
			//���W�E�T�C�Y�ݒ�
			j = 2;
			x = this.itemLabelLeft[j].getX() + this.itemLabelLeft[j].getWidth();
			y = this.itemLabelLeft[j].getY();
			width = largeWidth;
			height = this.itemLabelLeft[j].getHeight();
			this.genryoNmTextbox.setBounds(x,y,width,height);
			//�p�l���ɒǉ�
			this.add(this.genryoNmTextbox);
			
			///
			/// �e�L�X�g�{�b�N�X(���l)�̐ݒ�
			/// [0:�|�_(%), 1:�H��(%), 2:���_(%), 3:���ܗL��(%)]
			///
// MOD start 20121122 QP@20505 �ۑ�No.10
//			this.numTextbox = new NumelicTextbox[4];
			this.numTextbox = new NumelicTextbox[5];
// MOD end 20121122 QP@20505 �ۑ�No.10
			//������
			j = 3;
			for ( i = 0; i < this.numTextbox.length; i++ ) {
				this.numTextbox[i] = new NumelicTextbox();
				
// ADD start 20121122 QP@20505 �ۑ�No.10 ����MSG(%)�����[�v�̓r���ɒǉ����邽��
				if (i == 3) {
					// �l�r�f(%)
					int m = 14;
					
					//���W�E�T�C�Y�ݒ�
					x = this.itemLabelLeft[m].getX() + this.itemLabelLeft[m].getWidth();
					y = this.itemLabelLeft[m].getY();
					width = midWidth;
					height = this.itemLabelLeft[m].getHeight();
					this.numTextbox[i].setBounds(x,y,width,height);
					//�p�l���ɒǉ�
					this.add(this.numTextbox[i]);
					
					j -= 1;
				} else {
// ADD end 20121122 QP@20505 �ۑ�No.10
					//���W�E�T�C�Y�ݒ�
					x = this.itemLabelLeft[j + i].getX() + this.itemLabelLeft[j + i].getWidth();
					y = this.itemLabelLeft[j + i].getY();
					width = midWidth;
					height = this.itemLabelLeft[j + i].getHeight();
					this.numTextbox[i].setBounds(x,y,width,height);
					//�p�l���ɒǉ�
					this.add(this.numTextbox[i]);
// ADD start 20121122 QP@20505 �ۑ�No.10
				}
// ADD end 20121122 QP@20505 �ۑ�No.10
			}


			///
			/// �\���ăe�L�X�g�G���A�̐ݒ�
			///
			this.hyojianTextarea = new TextAreaBase();
			hyojianTextarea.setTABFocusControl();
			//���W�E�T�C�Y�ݒ�
			j = 7;
			x = this.itemLabelLeft[j].getX() + this.itemLabelLeft[j].getWidth();
			y = this.itemLabelLeft[j].getY();
			width = largeWidth;
			height = this.itemLabelLeft[j].getHeight();
//			this.hyojianTextarea.setBounds(x,y,width,height);
			//�e�L�X�g�܂�Ԃ��ɐݒ�
			this.hyojianTextarea.setLineWrap(true);
			//�X�N���[���p�l������
			this.hyojianScroll = new ScrollBase(this.hyojianTextarea);
			this.hyojianScroll.setBounds(x,y,width,height);
			//�p�l���ɒǉ�
			this.add(this.hyojianScroll);
			
			///
			/// �Y�����e�L�X�g�G���A�̐ݒ�
			///
			this.tenkabutuTextarea = new TextAreaBase();
			tenkabutuTextarea.setTABFocusControl();
			//���W�E�T�C�Y�ݒ�
			j = 8;
			x = this.itemLabelLeft[j].getX() + this.itemLabelLeft[j].getWidth();
			y = this.itemLabelLeft[j].getY();
			width = largeWidth;
			height = this.itemLabelLeft[j].getHeight();
//			this.tenkabutuTextarea.setBounds(x,y,width,height);
			//�e�L�X�g�܂�Ԃ��ɐݒ�
			this.tenkabutuTextarea.setLineWrap(true);
			//�X�N���[���p�l������
			this.tenkabutuScroll = new ScrollBase(this.tenkabutuTextarea);
			this.tenkabutuScroll.setBounds(x,y,width,height);
			//�p�l���ɒǉ�
			this.add(this.tenkabutuScroll);
					
			///
			/// �h�{�v�Z�H�i�ԍ�����(%)�e�L�X�g�{�b�N�X�̐ݒ�
			/// [0�`9:�e�L�X�g�{�b�N�X(���l)]
			///
			this.eiyouTextbox = new TextboxBase[10];
			//�T�C�Y�ݒ�
			width = this.itemLabelOther[0].getWidth();
			height = this.itemLabelLeft[0].getHeight();
			//������
			j = 9;
			for ( i = 0; i < this.eiyouTextbox.length; i++ ) {
				this.eiyouTextbox[i] = new TextboxBase();
				
				//X���W
				if ( i == 0 || i == 5 ) {
					x = this.itemLabelLeft[j].getX() + this.itemLabelLeft[j].getWidth();
				} else {
					x += width;
				}
				//X���W
				if ( i < 5 ) {
					y = this.itemLabelLeft[j].getY();
				} else {
					y = this.itemLabelLeft[j].getY() + this.itemLabelLeft[0].getHeight();
				}
				//���W�E�T�C�Y�ݒ�
				this.eiyouTextbox[i].setBounds(x,y,width,height);
				//�p�l���ɒǉ�
				this.add(this.eiyouTextbox[i]);
			}
			
			///
			/// �����e�L�X�g�G���A�̐ݒ�
			///
			this.memoTextarea = new TextAreaBase();
			memoTextarea.setTABFocusControl();
			//���W�E�T�C�Y�ݒ�
			j = 10;
			x = this.itemLabelLeft[j].getX() + this.itemLabelLeft[j].getWidth();
			y = this.itemLabelLeft[j].getY();
			width = largeWidth;
			height = this.itemLabelLeft[j].getHeight();
//			this.memoTextarea.setBounds(x,y,width,height);
			//�e�L�X�g�܂�Ԃ��ɐݒ�
			this.memoTextarea.setLineWrap(true);
			//�X�N���[���p�l������
			this.memoScroll = new ScrollBase(this.memoTextarea);
			this.memoScroll.setBounds(x,y,width,height);
			//�p�l���ɒǉ�
			this.add(this.memoScroll);

			///
			/// ���͓��e�L�X�g�{�b�N�X(���p)�̐ݒ�
			///
			this.nyuryokuhiTextbox = new HankakuTextbox();
			//���W�E�T�C�Y�ݒ�
			j = 11;
			x = this.itemLabelLeft[j].getX() + this.itemLabelLeft[j].getWidth();
			y = this.itemLabelLeft[j].getY();
			width = midWidth;
			height = this.itemLabelLeft[0].getHeight();
			this.nyuryokuhiTextbox.setBounds(x,y,width,height);
			//�p�l���ɒǉ�
			this.add(this.nyuryokuhiTextbox);

			///
			/// ���͎҃e�L�X�g�{�b�N�X�̐ݒ�
			///
			this.nyuryokushaTextbox = new TextboxBase();
			//���W�E�T�C�Y�ݒ�
			j = 5;
			x = this.itemLabelOther[j].getX() + this.itemLabelOther[j].getWidth();
			y = this.itemLabelOther[j].getY();
			width = midWidth;
			height = this.itemLabelOther[j].getHeight();
			this.nyuryokushaTextbox.setBounds(x,y,width+74,height);
			//�p�l���ɒǉ�
			this.add(this.nyuryokushaTextbox);

			///
			/// �m�F�`�F�b�N�{�b�N�X�̐ݒ�
			///
			this.kakuninCheckbox = new CheckboxBase();
			//���W�E�T�C�Y�ݒ�
			j = 6;
			x = this.itemLabelOther[j].getX() + this.itemLabelOther[j].getWidth();
			y = this.itemLabelOther[j].getY() + 2;
			width = 20;
			height = this.itemLabelOther[j].getHeight();
			this.kakuninCheckbox.setBounds(x,y,width,height);
			//�w�i : ��
			this.kakuninCheckbox.setBackground(Color.WHITE);
			this.kakuninCheckbox.addActionListener(this.getActionEvent());
			this.kakuninCheckbox.setActionCommand("kakunin");
			//�p�l���ɒǉ�
			this.add(this.kakuninCheckbox);

			///
			/// �m�F���e�L�X�g�{�b�N�X(���p)�̐ݒ�
			///
			this.kakuninhiTextbox = new HankakuTextbox();
			//���W�E�T�C�Y�ݒ�
			j = 12;
			x = this.itemLabelLeft[j].getX() + this.itemLabelLeft[j].getWidth();
			y = this.itemLabelLeft[j].getY();
			width = midWidth;
			height = this.itemLabelLeft[j].getHeight();
			this.kakuninhiTextbox.setBounds(x,y,width,height);
			//�p�l���ɒǉ�
			this.add(this.kakuninhiTextbox);

			///
			/// �m�F�҃e�L�X�g�{�b�N�X�̐ݒ�
			///
			this.kakuninTextbox = new TextboxBase();
			//���W�E�T�C�Y�ݒ�
			j = 7;
			x = this.itemLabelOther[j].getX() + this.itemLabelOther[j].getWidth();
			y = this.itemLabelOther[j].getY();
			width = midWidth;
			height = this.itemLabelOther[j].getHeight();
			this.kakuninTextbox.setBounds(x,y,width+74,height);
			//�p�l���ɒǉ�
			this.add(this.kakuninTextbox);
			
			///
			/// �p�~�`�F�b�N�{�b�N�X�̐ݒ�
			///
			this.haishiCheckbox = new CheckboxBase();
			//���W�E�T�C�Y�ݒ�
			j = 8;
			x = this.itemLabelOther[j].getX() + this.itemLabelOther[j].getWidth();
			y = this.itemLabelOther[j].getY() + 2;
			width = 20;
			height = this.itemLabelOther[j].getHeight();
			this.haishiCheckbox.setBounds(x,y,width,height);
			//�w�i : ��
			this.haishiCheckbox.setBackground(Color.WHITE);
			//�p�l���ɒǉ�
			this.add(this.haishiCheckbox);

			///
			/// �m��R�[�h�e�L�X�g�{�b�N�X(���p)�̐ݒ�
			///
			this.kakuteiCdTextbox = new HankakuTextbox();
			//���W�E�T�C�Y�ݒ�
			j = 9;
			x = this.itemLabelOther[j].getX() + this.itemLabelOther[j].getWidth();
			y = this.itemLabelOther[j].getY();
			width = midWidth;
			height = this.itemLabelOther[j].getHeight();
			this.kakuteiCdTextbox.setBounds(x,y,width+74,height);
			//�p�l���ɒǉ�
			this.add(this.kakuteiCdTextbox);

			///
			/// �{�^��
			/// [0:�o�^, 1:�I��]
			///
			this.button = new ButtonBase[2];
			//0:�o�^
			width = 80;
			height = 38;
			x = dispWidht - 100 - width;
// MOD start 20121122 QP@20505 �ۑ�No.10
//			y = 480;
			y = 500;
// MOD end 20121122 QP@20505 �ۑ�No.10
			this.button[0] = new ButtonBase();
			this.button[0].setFont(new Font("Default", Font.PLAIN, 11));
			this.button[0].setBounds(x, y, width, height);
			this.button[0].setText("�o�^");
			//1:�I��
			x = dispWidht - 100;
			this.button[1] = new ButtonBase();
			this.button[1].setFont(new Font("Default", Font.PLAIN, 11));
			this.button[1].setBounds(x, y, width, height);
			this.button[1].setText("�I��");
			//�{�^�����p�l���ɒǉ�
			for ( i=0; i<this.button.length; i++ ) {
				this.add(this.button[i]);
			}
			
		}catch(Exception e){
			//�G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("���͒l���̓p�l���̃R���g���[���z�u���������s���܂���");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;
			
		}finally{
			
		}
		
		
	}

	/**
	 * ����������
	 * @param mode : 0=�V�K or 1=�ڍ�
	 * @param md : �I�������f�[�^
	 */
	public void init(int mode,MaterialData md) throws ExceptionBase{
		try{
			//--------------------------- �ҏW�w��i�V�K�j ----------------------------
			if(mode == 0){
				strGamenId = "130";
				sinkiFg = true;
				
				
				//��ЃR���{�{�b�N�X�̏����ݒ�
				conJW610();
				this.setKaishaCmb();
				
				//��ЃR���{�{�b�N�X
				try{
					//����Ђ�I��
					kaishaComb.setSelectedItem(DataCtrl.getInstance().getUserMstData().getStrKaishanm());
					
				}catch(Exception e){
					//�R���{�{�b�N�X���̍ŏ��̂��̂�I��
					kaishaComb.setSelectedIndex(0);
					
				}
				
				// �����R�[�h�e�L�X�g�{�b�N�X(���p)
				genryoCdTextbox.setText(null);
				// �������e�L�X�g�{�b�N�X
				genryoNmTextbox.setText(null);
				
				// �e�L�X�g�{�b�N�X(���l)
				// [0:�|�_(%), 1:�H��(%), 2:���_(%), 3:�l�r�f(%) 4:���ܗL��(%) ]
				numTextbox[0].setText(null);
				numTextbox[1].setText(null);
				numTextbox[2].setText(null);
				numTextbox[3].setText(null);
// ADD start 20121122 QP@20505 �ۑ�No.10
				numTextbox[4].setText(null);
// ADD end 20121122 QP@20505 �ۑ�No.10
				
				// �\���ăe�L�X�g�G���A
				hyojianTextarea.setText(null);
				// �Y�����e�L�X�g�G���A
				tenkabutuTextarea.setText(null);
				
				// �h�{�v�Z�H�i�ԍ������e�L�X�g�{�b�N�X
				eiyouTextbox[0].setText(null);
				eiyouTextbox[1].setText(null);
				eiyouTextbox[2].setText(null);
				eiyouTextbox[3].setText(null);
				eiyouTextbox[4].setText(null);
				eiyouTextbox[5].setText(null);
				eiyouTextbox[6].setText(null);
				eiyouTextbox[7].setText(null);
				eiyouTextbox[8].setText(null);
				eiyouTextbox[9].setText(null);
				
				// �����e�L�X�g�G���A
				memoTextarea.setText(null);
				// ���͓��e�L�X�g�{�b�N�X(���p)
				nyuryokuhiTextbox.setText(null);
				// ���͎҃e�L�X�g�{�b�N�X
				nyuryokushaTextbox.setText(null);
				
				// �m�F�`�F�b�N�{�b�N�X
				kakuninCheckbox.setSelected(false);
				// �m�F���e�L�X�g�{�b�N�X(���p)
				kakuninhiTextbox.setText(null);
				// �m�F�҃e�L�X�g�{�b�N�X
				kakuninTextbox.setText(null);
				// �p�~�`�F�b�N�{�b�N�X
				haishiCheckbox.setEnabled(false);
				// �m��R�[�h�e�L�X�g�{�b�N�X(���p)
				kakuteiCdTextbox.setText(null);
				
				//ID�i�[
				torokuId = DataCtrl.getInstance().getUserMstData().getDciUserid();
				kakuninId = null;
				
				//��ЃR���{�{�b�N�X
				kaishaComb.setEnabled(true);
				// �����R�[�h�e�L�X�g�{�b�N�X(���p)
				genryoCdTextbox.setEnabled(false);
				// �������e�L�X�g�{�b�N�X
				genryoNmTextbox.setEnabled(true);
				
				// �e�L�X�g�{�b�N�X(���l)
				numTextbox[0].setEnabled(true);
				numTextbox[1].setEnabled(true);
				numTextbox[2].setEnabled(true);
				numTextbox[3].setEnabled(true);
// ADD start 20121122 QP@20505 �ۑ�No.10
				numTextbox[4].setEnabled(true);
// ADD end 20121122 QP@20505 �ۑ�No.10
				
				// �\���ăe�L�X�g�G���A
				hyojianTextarea.setEnabled(false);
				// �Y�����e�L�X�g�G���A
				tenkabutuTextarea.setEnabled(false);
				
				// �h�{�v�Z�H�i�ԍ������e�L�X�g�{�b�N�X
				eiyouTextbox[0].setEnabled(false);
				eiyouTextbox[1].setEnabled(false);
				eiyouTextbox[2].setEnabled(false);
				eiyouTextbox[3].setEnabled(false);
				eiyouTextbox[4].setEnabled(false);
				eiyouTextbox[5].setEnabled(false);
				eiyouTextbox[6].setEnabled(false);
				eiyouTextbox[7].setEnabled(false);
				eiyouTextbox[8].setEnabled(false);
				eiyouTextbox[9].setEnabled(false);
				
				// �����e�L�X�g�G���A
				memoTextarea.setEnabled(true);
				// ���͓��e�L�X�g�{�b�N�X(���p)
				nyuryokuhiTextbox.setText(getSysDate());
				nyuryokuhiTextbox.setEnabled(false);
				// ���͎҃e�L�X�g�{�b�N�X
				nyuryokushaTextbox.setText(DataCtrl.getInstance().getUserMstData().getStrUsernm());
				nyuryokushaTextbox.setEnabled(false);
				
				// �m�F�`�F�b�N�{�b�N�X
				kakuninCheckbox.setEnabled(false);
				// �m�F���e�L�X�g�{�b�N�X(���p)
				kakuninhiTextbox.setEnabled(false);
				// �m�F�҃e�L�X�g�{�b�N�X
				kakuninTextbox.setEnabled(false);
				// �p�~�`�F�b�N�{�b�N�X
				haishiCheckbox.setEnabled(false);
				haishiCheckbox.setSelected(false);
				// �m��R�[�h�e�L�X�g�{�b�N�X(���p)
				kakuteiCdTextbox.setEnabled(false);
				// �{�^��
				button[0].setEnabled(true);
				button[1].setEnabled(true);
			
			//--------------------------- �ҏW�w��i�ڍׁj ----------------------------
			}else{
				strGamenId = "140";
				sinkiFg = false;
				//ID�i�[
				torokuId = md.getDciTorokuId();
				kakuninId = md.getDciKakunincd();
				
				koshinId = md.getDciKosinId();
				koshinNm = md.getStrKosinNm();
				
				
				//��ЃR���{�{�b�N�X�̏����ݒ�
				conJW610();
				this.setKaishaCmb();
				
				//-------------------------- �f�[�^�ݒ� -------------------------------
				//��ЃR���{�{�b�N�X
				kaishaComb.setSelectedItem(getSelectKaishaNm(md.getIntKaishacd()));
				// �����R�[�h�e�L�X�g�{�b�N�X(���p)
				genryoCdTextbox.setText(md.getStrGenryocd());
				// �������e�L�X�g�{�b�N�X
				genryoNmTextbox.setText(md.getStrGenryonm());
				
				// �e�L�X�g�{�b�N�X(���l)
				// [0:�|�_(%), 1:�H��(%), 2:���_(%), 3:MSG 4:���ܗL��(%)]
				if(md.getDciSakusan() != null){
					numTextbox[0].setText(md.getDciSakusan().toString());
				}else{
					numTextbox[0].setText(null);
				}
				if(md.getDciShokuen() != null){
					numTextbox[1].setText(md.getDciShokuen().toString());
				}else{
					numTextbox[1].setText(null);
				}
				if(md.getDciSousan() != null){
					numTextbox[2].setText(md.getDciSousan().toString());
				}else{
					numTextbox[2].setText(null);
				}
// MOD start 20121122 QP@20505 �ۑ�No.10
//				if(md.getDciGanyu() != null){
//					numTextbox[3].setText(md.getDciGanyu().toString());
//				}else{
//					numTextbox[3].setText(null);
//				}
				if(md.getDciMsg() != null){
					numTextbox[3].setText(md.getDciMsg().toString());
				}else{
					numTextbox[3].setText(null);
				}
				if(md.getDciGanyu() != null){
					numTextbox[4].setText(md.getDciGanyu().toString());
				}else{
					numTextbox[4].setText(null);
				}
// MOD end 20121122 QP@20505 �ۑ�No.10
				
				
				// �\���ăe�L�X�g�G���A
				hyojianTextarea.setText(md.getStrHyoji());
				// �Y�����e�L�X�g�G���A
				tenkabutuTextarea.setText(md.getStrTenka());
				
				// �h�{�v�Z�H�i�ԍ������e�L�X�g�{�b�N�X
				eiyouTextbox[0].setText(md.getStrEiyono1());
				eiyouTextbox[1].setText(md.getStrEiyono2());
				eiyouTextbox[2].setText(md.getStrEiyono3());
				eiyouTextbox[3].setText(md.getStrEiyono4());
				eiyouTextbox[4].setText(md.getStrEiyono5());
				eiyouTextbox[5].setText(md.getStrWariai1());
				eiyouTextbox[6].setText(md.getStrWariai2());
				eiyouTextbox[7].setText(md.getStrWariai3());
				eiyouTextbox[8].setText(md.getStrWariai4());
				eiyouTextbox[9].setText(md.getStrWariai5());
				
				// �����e�L�X�g�G���A
				memoTextarea.setText(md.getStrMemo());
				// ���͓��e�L�X�g�{�b�N�X(���p)
				nyuryokuhiTextbox.setText(md.getStrKosinDt());
				// ���͎҃e�L�X�g�{�b�N�X
				nyuryokushaTextbox.setText(md.getStrKosinNm());
				
				// �m�F�`�F�b�N�{�b�N�X
				kakuninCheckbox.setSelected((md.getDciKakunincd()!=null)?true:false);
				
				// �m�F���ꂽ�f�[�^���ǂ���
				if(md.getDciKakunincd()!=null){
					kakuninFg = true;
				}else{
					kakuninFg = false;
				}
				
				// �m�F���e�L�X�g�{�b�N�X(���p)
				kakuninhiTextbox.setText(md.getStrKakuninhi());
				// �m�F�҃e�L�X�g�{�b�N�X
				kakuninTextbox.setText(md.getStrKakuninnm());
				// �p�~�`�F�b�N�{�b�N�X
				haishiCheckbox.setSelected((md.getIntHaisicd()==1)?true:false);
				// �m��R�[�h�e�L�X�g�{�b�N�X(���p)
				kakuteiCdTextbox.setText(md.getStrkakuteicd());
				
				//�����`�F�b�N
				ArrayList Auth = DataCtrl.getInstance().getUserMstData().getAryAuthData();
				boolean delChk = false;
				for(int j=0;j<Auth.size();j++){
					String[] strDispAuth = (String[])Auth.get(j);
					if(strDispAuth[0].equals("140")){
						//�{�������̏ꍇ
						if(strDispAuth[1].equals("10")){
							//��ЃR���{�{�b�N�X
							kaishaComb.setEnabled(false);
							// �����R�[�h�e�L�X�g�{�b�N�X(���p)
							genryoCdTextbox.setEnabled(false);
							// �������e�L�X�g�{�b�N�X
							genryoNmTextbox.setEnabled(false);	
							
							// �e�L�X�g�{�b�N�X(���l)
							numTextbox[0].setEnabled(false);
							numTextbox[1].setEnabled(false);
							numTextbox[2].setEnabled(false);
							numTextbox[3].setEnabled(false);
// ADD start 20121122 QP@20505 �ۑ�No.10
							numTextbox[4].setEnabled(false);
// ADD end 20121122 QP@20505 �ۑ�No.10
							
							// �\���ăe�L�X�g�G���A
							hyojianTextarea.setEnabled(false);
							// �Y�����e�L�X�g�G���A
							tenkabutuTextarea.setEnabled(false);
							
							// �h�{�v�Z�H�i�ԍ������e�L�X�g�{�b�N�X
							eiyouTextbox[0].setEnabled(false);
							eiyouTextbox[1].setEnabled(false);
							eiyouTextbox[2].setEnabled(false);
							eiyouTextbox[3].setEnabled(false);
							eiyouTextbox[4].setEnabled(false);
							eiyouTextbox[5].setEnabled(false);
							eiyouTextbox[6].setEnabled(false);
							eiyouTextbox[7].setEnabled(false);
							eiyouTextbox[8].setEnabled(false);
							eiyouTextbox[9].setEnabled(false);
							
							// �����e�L�X�g�G���A
							memoTextarea.setEnabled(false);
							// ���͓��e�L�X�g�{�b�N�X(���p)
							nyuryokuhiTextbox.setEnabled(false);
							// ���͎҃e�L�X�g�{�b�N�X
							nyuryokushaTextbox.setEnabled(false);
							
							// �m�F�`�F�b�N�{�b�N�X
							kakuninCheckbox.setEnabled(false);
							// �m�F���e�L�X�g�{�b�N�X(���p)
							kakuninhiTextbox.setEnabled(false);
							// �m�F�҃e�L�X�g�{�b�N�X
							kakuninTextbox.setEnabled(false);
							// �p�~�`�F�b�N�{�b�N�X
							haishiCheckbox.setEnabled(false);
							// �m��R�[�h�e�L�X�g�{�b�N�X(���p)
							kakuteiCdTextbox.setEnabled(false);
							// �{�^��
							button[0].setEnabled(false);
							button[1].setEnabled(true);
							
						}
						
						//�ҏW�i�V�K�j�����̏ꍇ
						else if(strDispAuth[1].equals("20")){
							//�V�K����
							if(md.getStrGenryocd().indexOf("N") != -1){
								//��ЃR���{�{�b�N�X
								kaishaComb.setEnabled(false);
								// �����R�[�h�e�L�X�g�{�b�N�X(���p)
								genryoCdTextbox.setEnabled(false);
								// �������e�L�X�g�{�b�N�X
								genryoNmTextbox.setEnabled(true);	
								
								// �e�L�X�g�{�b�N�X(���l)
								numTextbox[0].setEnabled(true);
								numTextbox[1].setEnabled(true);
								numTextbox[2].setEnabled(true);
								numTextbox[3].setEnabled(true);
// ADD start 20121122 QP@20505 �ۑ�No.10
								numTextbox[4].setEnabled(true);
// ADD end 20121122 QP@20505 �ۑ�No.10
								
								// �\���ăe�L�X�g�G���A
								hyojianTextarea.setEnabled(true);
								// �Y�����e�L�X�g�G���A
								tenkabutuTextarea.setEnabled(true);
								
								// �h�{�v�Z�H�i�ԍ������e�L�X�g�{�b�N�X
								eiyouTextbox[0].setEnabled(true);
								eiyouTextbox[1].setEnabled(true);
								eiyouTextbox[2].setEnabled(true);
								eiyouTextbox[3].setEnabled(true);
								eiyouTextbox[4].setEnabled(true);
								eiyouTextbox[5].setEnabled(true);
								eiyouTextbox[6].setEnabled(true);
								eiyouTextbox[7].setEnabled(true);
								eiyouTextbox[8].setEnabled(true);
								eiyouTextbox[9].setEnabled(true);
								
								// �����e�L�X�g�G���A
								memoTextarea.setEnabled(true);
								// ���͓��e�L�X�g�{�b�N�X(���p)
								nyuryokuhiTextbox.setEnabled(false);
								// ���͎҃e�L�X�g�{�b�N�X
								nyuryokushaTextbox.setEnabled(false);
								
								// �m�F�`�F�b�N�{�b�N�X
								kakuninCheckbox.setEnabled(true);
								// �m�F���e�L�X�g�{�b�N�X(���p)
								kakuninhiTextbox.setEnabled(false);
								// �m�F�҃e�L�X�g�{�b�N�X
								kakuninTextbox.setEnabled(false);
								// �p�~�`�F�b�N�{�b�N�X
								haishiCheckbox.setEnabled(true);
								// �m��R�[�h�e�L�X�g�{�b�N�X(���p)
								kakuteiCdTextbox.setEnabled(true);
								// �{�^��
								button[0].setEnabled(true);
								button[1].setEnabled(true);
								
							//��������
							}else{
								//��ЃR���{�{�b�N�X
								kaishaComb.setEnabled(false);
								// �����R�[�h�e�L�X�g�{�b�N�X(���p)
								genryoCdTextbox.setEnabled(false);
								// �������e�L�X�g�{�b�N�X
								genryoNmTextbox.setEnabled(false);	
								
								// �e�L�X�g�{�b�N�X(���l)
								numTextbox[0].setEnabled(false);
								numTextbox[1].setEnabled(false);
								numTextbox[2].setEnabled(false);
								numTextbox[3].setEnabled(false);
// ADD start 20121122 QP@20505 �ۑ�No.10
								numTextbox[4].setEnabled(false);
// ADD end 20121122 QP@20505 �ۑ�No.10
								
								// �\���ăe�L�X�g�G���A
								hyojianTextarea.setEnabled(false);
								// �Y�����e�L�X�g�G���A
								tenkabutuTextarea.setEnabled(false);
								
								// �h�{�v�Z�H�i�ԍ������e�L�X�g�{�b�N�X
								eiyouTextbox[0].setEnabled(false);
								eiyouTextbox[1].setEnabled(false);
								eiyouTextbox[2].setEnabled(false);
								eiyouTextbox[3].setEnabled(false);
								eiyouTextbox[4].setEnabled(false);
								eiyouTextbox[5].setEnabled(false);
								eiyouTextbox[6].setEnabled(false);
								eiyouTextbox[7].setEnabled(false);
								eiyouTextbox[8].setEnabled(false);
								eiyouTextbox[9].setEnabled(false);
								
								// �����e�L�X�g�G���A
								memoTextarea.setEnabled(false);
								// ���͓��e�L�X�g�{�b�N�X(���p)
								nyuryokuhiTextbox.setEnabled(false);
								// ���͎҃e�L�X�g�{�b�N�X
								nyuryokushaTextbox.setEnabled(false);
								
								// �m�F�`�F�b�N�{�b�N�X
								kakuninCheckbox.setEnabled(false);
								// �m�F���e�L�X�g�{�b�N�X(���p)
								kakuninhiTextbox.setEnabled(false);
								// �m�F�҃e�L�X�g�{�b�N�X
								kakuninTextbox.setEnabled(false);
								// �p�~�`�F�b�N�{�b�N�X
								haishiCheckbox.setEnabled(false);
								// �m��R�[�h�e�L�X�g�{�b�N�X(���p)
								kakuteiCdTextbox.setEnabled(false);
								// �{�^��
								button[0].setEnabled(false);
								button[1].setEnabled(true);
							}
						}
						//�ҏW�i�S�āj�����̏ꍇ
						else if(strDispAuth[1].equals("40")){
							//�V�K����
							if(md.getStrGenryocd().indexOf("N") != -1){
								//��ЃR���{�{�b�N�X
								kaishaComb.setEnabled(false);
								// �����R�[�h�e�L�X�g�{�b�N�X(���p)
								genryoCdTextbox.setEnabled(false);
								// �������e�L�X�g�{�b�N�X
								genryoNmTextbox.setEnabled(true);	
								
								// �e�L�X�g�{�b�N�X(���l)
								numTextbox[0].setEnabled(true);
								numTextbox[1].setEnabled(true);
								numTextbox[2].setEnabled(true);
								numTextbox[3].setEnabled(true);
// ADD start 20121122 QP@20505 �ۑ�No.10
								numTextbox[4].setEnabled(true);
// ADD end 20121122 QP@20505 �ۑ�No.10
								
								// �\���ăe�L�X�g�G���A
								hyojianTextarea.setEnabled(true);
								// �Y�����e�L�X�g�G���A
								tenkabutuTextarea.setEnabled(true);
								
								// �h�{�v�Z�H�i�ԍ������e�L�X�g�{�b�N�X
								eiyouTextbox[0].setEnabled(true);
								eiyouTextbox[1].setEnabled(true);
								eiyouTextbox[2].setEnabled(true);
								eiyouTextbox[3].setEnabled(true);
								eiyouTextbox[4].setEnabled(true);
								eiyouTextbox[5].setEnabled(true);
								eiyouTextbox[6].setEnabled(true);
								eiyouTextbox[7].setEnabled(true);
								eiyouTextbox[8].setEnabled(true);
								eiyouTextbox[9].setEnabled(true);
								
								// �����e�L�X�g�G���A
								memoTextarea.setEnabled(true);
								// ���͓��e�L�X�g�{�b�N�X(���p)
								nyuryokuhiTextbox.setEnabled(false);
								// ���͎҃e�L�X�g�{�b�N�X
								nyuryokushaTextbox.setEnabled(false);
								
								// �m�F�`�F�b�N�{�b�N�X
								kakuninCheckbox.setEnabled(true);
								// �m�F���e�L�X�g�{�b�N�X(���p)
								kakuninhiTextbox.setEnabled(false);
								// �m�F�҃e�L�X�g�{�b�N�X
								kakuninTextbox.setEnabled(false);
								// �p�~�`�F�b�N�{�b�N�X
								haishiCheckbox.setEnabled(true);
								// �m��R�[�h�e�L�X�g�{�b�N�X(���p)
								kakuteiCdTextbox.setEnabled(true);
								// �{�^��
								button[0].setEnabled(true);
								button[1].setEnabled(true);
							}else{
								//��ЃR���{�{�b�N�X
								kaishaComb.setEnabled(false);
								// �����R�[�h�e�L�X�g�{�b�N�X(���p)
								genryoCdTextbox.setEnabled(false);
								// �������e�L�X�g�{�b�N�X
								genryoNmTextbox.setEnabled(false);	
								
								// �e�L�X�g�{�b�N�X(���l)
								numTextbox[0].setEnabled(true);
								numTextbox[1].setEnabled(true);
								numTextbox[2].setEnabled(true);
								numTextbox[3].setEnabled(true);
// ADD start 20121122 QP@20505 �ۑ�No.10
								numTextbox[4].setEnabled(true);
// ADD end 20121122 QP@20505 �ۑ�No.10
								
								// �\���ăe�L�X�g�G���A
								hyojianTextarea.setEnabled(true);
								// �Y�����e�L�X�g�G���A
								tenkabutuTextarea.setEnabled(true);
								
								// �h�{�v�Z�H�i�ԍ������e�L�X�g�{�b�N�X
								eiyouTextbox[0].setEnabled(true);
								eiyouTextbox[1].setEnabled(true);
								eiyouTextbox[2].setEnabled(true);
								eiyouTextbox[3].setEnabled(true);
								eiyouTextbox[4].setEnabled(true);
								eiyouTextbox[5].setEnabled(true);
								eiyouTextbox[6].setEnabled(true);
								eiyouTextbox[7].setEnabled(true);
								eiyouTextbox[8].setEnabled(true);
								eiyouTextbox[9].setEnabled(true);
								
								// �����e�L�X�g�G���A
								memoTextarea.setEnabled(true);
								// ���͓��e�L�X�g�{�b�N�X(���p)
								nyuryokuhiTextbox.setEnabled(false);
								// ���͎҃e�L�X�g�{�b�N�X
								nyuryokushaTextbox.setEnabled(false);
								
								// �m�F�`�F�b�N�{�b�N�X
								kakuninCheckbox.setEnabled(true);
								// �m�F���e�L�X�g�{�b�N�X(���p)
								kakuninhiTextbox.setEnabled(false);
								// �m�F�҃e�L�X�g�{�b�N�X
								kakuninTextbox.setEnabled(false);
								// �p�~�`�F�b�N�{�b�N�X
								haishiCheckbox.setEnabled(false);
								// �m��R�[�h�e�L�X�g�{�b�N�X(���p)
								kakuteiCdTextbox.setEnabled(false);
								// �{�^��
								button[0].setEnabled(true);
								button[1].setEnabled(true);
							}
						}
					}
				}
				
				// �{�^���i�p�~�̏ꍇ�͑���s�j
				if(md.getIntHaisicd() == 1){
					button[0].setEnabled(false);
				}
				
			}

//add start --------------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.13
			//���͏���ޔ�
			bunsekiDataTaihi();
//add end --------------------------------------------------------------------------------------
			
		}catch(ExceptionBase eb){
			throw eb;
			
		}catch(Exception e){
			//�G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("���͒l���̓p�l���̏��������������s���܂���");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;
			
		}finally{
			
		}
	}
	
	/**********************************************************************************
	 * 
	 * ActionListener�C�x���g
	 * @return
	 * 
	 **********************************************************************************/
	private ActionListener getActionEvent() {
		return (
			new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try{
						//�C�x���g���擾
						String event_name = e.getActionCommand();
						
						//--------------- �m�F �`�F�b�N�{�b�N�X �N���b�N���̏��� -----------------------
						if ( event_name == "kakunin") {
							if(kakuninCheckbox.isSelected()){
								// �m�F���e�L�X�g�{�b�N�X(���p)
								kakuninhiTextbox.setText(getSysDate());
								// �m�F�҃e�L�X�g�{�b�N�X
								kakuninTextbox.setText(DataCtrl.getInstance().getUserMstData().getStrUsernm());
								//ID�i�[
								kakuninId = DataCtrl.getInstance().getUserMstData().getDciUserid();
								
							}else{
								// �m�F���e�L�X�g�{�b�N�X(���p)
								kakuninhiTextbox.setText(null);
								// �m�F�҃e�L�X�g�{�b�N�X
								kakuninTextbox.setText(null);
								//ID�i�[
								kakuninId = null;
							}
						}
						
					}catch(Exception ec){
						//�G���[�ݒ�
						ex = new ExceptionBase();
						ex.setStrErrCd("");
						ex.setStrErrmsg("���͒l���̓p�l����ActionListener�C�x���g�����s���܂���");
						ex.setStrErrShori(this.getClass().getName());
						ex.setStrMsgNo("");
						ex.setStrSystemMsg(ec.getMessage());
						DataCtrl.getInstance().PrintMessage(ex);
						
					}
				}
			}
		);
	}
	
	/************************************************************************************
	 * 
	 * �o�^����
	 * @throws ExceptionBase 
	 * 
	 ************************************************************************************/
	public void toroku_bunseki() throws ExceptionBase{
		try{
			//�o�^�iJW820�j
			if(sinkiFg){
				//�����̔ԁiJ010�j
//				String newCd = conJ010();
//				if(newCd != null){
//					//0���ߏ���
//					for(int i=newCd.length(); i<5; i++){
//						newCd = "0"+newCd;
//					}
//					newCd = "N" + newCd;			
//				}

				//�V�K
				String genryo_cd = conJW820("new","0");
				genryoCdTextbox.setText(genryo_cd);
				
				//�o�^�{�^���ҏW�s��
				this.button[0].setEnabled(false);

			}else{
//del start --------------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.13
//				//�m�F����Ă���f�[�^�̏ꍇ
//				if(kakuninFg){
//					
//					//�m�F���e��������
//					kakuninCheckbox.setSelected(false);
//					kakuninhiTextbox.setText(null);
//					kakuninTextbox.setText(null);
//					
//					//�m�F�`�F�b�N�t���O��false
//					kakuninFg = false;
//				
//				}
//				//�m�F����Ă��Ȃ��f�[�^�̏ꍇ
//				else{
//					
//					//�m�F�`�F�b�N�{�b�N�X���`�F�b�N����Ă���ꍇ
//					if(kakuninCheckbox.isSelected()){
//						
//						//�m�F�`�F�b�N�t���O��true
//						kakuninFg = true;
//						
//					}
//					//�m�F�`�F�b�N�{�b�N�X���`�F�b�N����Ă��Ȃ��ꍇ
//					else{
//						
//						//�������Ȃ�
//						
//					}
//					
//				}
//del end --------------------------------------------------------------------------------------
				
				//�ҏW
				conJW820(genryoCdTextbox.getText(),"1");
				
				if (kakuninCheckbox.isSelected()){
					// �m�F���e�L�X�g�{�b�N�X(���p)
					kakuninhiTextbox.setText(DataCtrl.getInstance().getTrialTblData().getSysDate());
					// �m�F�҃e�L�X�g�{�b�N�X
					kakuninTextbox.setText(DataCtrl.getInstance().getUserMstData().getStrUsernm());
				}
				else{
					// ���͓��e�L�X�g�{�b�N�X(���p)
					nyuryokuhiTextbox.setText(DataCtrl.getInstance().getTrialTblData().getSysDate());
					// ���͎҃e�L�X�g�{�b�N�X
					nyuryokushaTextbox.setText(DataCtrl.getInstance().getUserMstData().getStrUsernm());
				}

				
			}

		}catch(ExceptionBase eb){
			throw eb;
		}catch(Exception ec){
			//�G���[�ݒ�
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			ex.setStrErrmsg("���͒l���̓p�l���̓o�^���������s���܂���");
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(ec.getMessage());
			throw ex;
		}
		
	}
	
	/************************************************************************************
	 * 
	 * �I����Ж��̎擾
	 * @throws ExceptionBase 
	 * 
	 ************************************************************************************/
	private String getSelectKaishaNm(int cd) throws ExceptionBase {
		int i;
		String ret = null;
		String kaishaCd = "";
		String kaishaNm = "";
		try {
			//�\�����ɉ����ăR���{�{�b�N�X�ɒl��ǉ�
			for ( i=0; i<KaishaData.getArtKaishaCd().size(); i++ ) {
				//��ЃR�[�h
				kaishaCd = KaishaData.getArtKaishaCd().get(i).toString();
				//��Ж�
				kaishaNm = KaishaData.getAryKaishaNm().get(i).toString(); 
	
				//�I����ЃR�[�h�̌��o
				if ( kaishaCd.equals(Integer.toString(cd)) ) {
					ret = kaishaNm;
				}
			}
		}catch (Exception e) {
			//�G���[�ݒ�
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			ex.setStrErrmsg("���͒l���̓p�l���̉�Ж��擾���������s���܂���");
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getMessage());
			throw ex;
			
		} finally {
		}
		
		return ret;
	}
	
	/************************************************************************************
	 * 
	 * �I����ЃR�[�h�̎擾
	 * @return �I����ЃR�[�h 
	 * @throws ExceptionBase 
	 * 
	 ************************************************************************************/
	private String getSelectKaishaCd() throws ExceptionBase {
		int i;
		String intRetKaishaCd = "";
		String kaishaCd = "";
		String kaishaNm = "";
		try {
			//�\�����ɉ����ăR���{�{�b�N�X�ɒl��ǉ�
			for ( i=0; i<KaishaData.getArtKaishaCd().size(); i++ ) {
				//��ЃR�[�h
				kaishaCd = KaishaData.getArtKaishaCd().get(i).toString();
				//��Ж�
				kaishaNm = KaishaData.getAryKaishaNm().get(i).toString(); 
	
				//�I����ЃR�[�h�̌��o
				if ( kaishaNm.equals(this.kaishaComb.getSelectedItem().toString()) ) {
					intRetKaishaCd = kaishaCd;
				}
			}
		} catch (Exception e) {
			//�G���[�ݒ�
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			ex.setStrErrmsg("���͒l���̓p�l���̑I����ЃR�[�h�擾���������s���܂���");
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getMessage());
			throw ex;
			
		} finally {
		}
		
		return intRetKaishaCd;
	}
	
	/************************************************************************************
	 * 
	 *   ����R�[�h�����̔ԁ@XML�ʐM�����iJ010�j
	 *    :  ����R�[�h�����̔ԏ���XML�f�[�^�ʐM�iJ010�j���s��
	 *   @author TT nishigawa
	 *   
	 ************************************************************************************/
	private String conJ010() throws ExceptionBase{
		String ret = null;
		try{
			//--------------------------- ���M�p�����[�^�i�[  ---------------------------------
			String strUser = DataCtrl.getInstance().getParamData().getDciUser().toString();
			String strShisaku_user = DataCtrl.getInstance().getParamData().getStrSisaku_user();
			String strShisaku_nen = DataCtrl.getInstance().getParamData().getStrSisaku_nen();
			String strShisaku_oi = DataCtrl.getInstance().getParamData().getStrSisaku_oi();
			
			//--------------------------- ���MXML�f�[�^�쐬  ---------------------------------
			xmlJ010 = new XmlData();
			ArrayList arySetTag = new ArrayList();
			
			//------------------------------- Root�ǉ�  ------------------------------------
			xmlJ010.AddXmlTag("","J010");
			arySetTag.clear();
			
			//------------------------- �@�\ID�ǉ��iUSERINFO�j  ------------------------------
			xmlJ010.AddXmlTag("J010", "USERINFO");
			
			//----------------------------- �e�[�u���^�O�ǉ�  ---------------------------------
			xmlJ010.AddXmlTag("USERINFO", "table");
			
			//------------------------------ ���R�[�h�ǉ�  -----------------------------------
			//�����敪
			arySetTag.add(new String[]{"kbn_shori", "3"});
			//���[�UID
			arySetTag.add(new String[]{"id_user",strUser});
			//XML�փ��R�[�h�ǉ�
			xmlJ010.AddXmlTag("table", "rec", arySetTag);
			//�z�񏉊���
			arySetTag.clear();
			
			//--------------------------- �@�\ID�ǉ��i�����̔ԁj  -----------------------------
			xmlJ010.AddXmlTag("J010", "SA410");
			//�@�e�[�u���^�O�ǉ�
			xmlJ010.AddXmlTag("SA410", "table");
			//�@���R�[�h�ǉ�
			arySetTag.add(new String[]{"kbn_shori", "cd_genryo"});
			xmlJ010.AddXmlTag("table", "rec", arySetTag);
			//�z�񏉊���
			arySetTag.clear();
			
			//----------------------------------- XML���M  ----------------------------------
			//System.out.println("J010���MXML===============================================================");
			//xmlJ010.dispXml();
			XmlConnection xcon = new XmlConnection(xmlJ010);
			xcon.setStrAddress(DataCtrl.getInstance().getJnlpConnect().getStrAddressAjax());
			xcon.XmlSend();
			
			//----------------------------------- XML��M  ----------------------------------
			xmlJ010 = xcon.getXdocRes();
			//System.out.println();
			//System.out.println("J010��MXML===============================================================");
			//xmlJ010.dispXml();
			
			//�@�e�X�gXML�f�[�^
			//xmlJ010 = new XmlData(new File("src/main/J010.xml"));
			
			//--------------------------------- �����R�[�h�擾  -------------------------------
			//Result�`�F�b�N
			DataCtrl.getInstance().getResultData().setResultData(xmlJ010);
			if (DataCtrl.getInstance().getResultData().getStrReturnFlg().equals("true")) {
				//�@�\ID�̐ݒ�
				String strKinoId = "SA410";
				
				//�S�̔z��擾
				ArrayList userData = xmlJ010.GetAryTag(strKinoId);
				
				//�@�\�z��擾
				ArrayList kinoData = (ArrayList)userData.get(0);

				//�e�[�u���z��擾
				ArrayList tableData = (ArrayList)kinoData.get(1);

				//���R�[�h�擾
				for(int i=1; i<tableData.size(); i++){
					//�@�P���R�[�h�擾
					ArrayList recData = ((ArrayList)((ArrayList)tableData.get(i)).get(0));
					for(int j=0; j<recData.size(); j++){
						//�@���ږ��擾
						String recNm = ((String[])recData.get(j))[1];
						//�@���ڒl�擾
						String recVal = ((String[])recData.get(j))[2];
						//�@����R�[�h
						if ( recNm == "new_code" ) {
							ret = recVal;
						}
					}
				}
			}else{
				ExceptionBase ExceptionBase  = new ExceptionBase();
				throw ExceptionBase;
			}
		}catch(ExceptionBase eb){
			throw eb;
			
		}catch(Exception e){
			//�G���[�ݒ�
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			ex.setStrErrmsg("���͒l���̓p�l���̎���R�[�h�����̔ԒʐM���������s���܂���");
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getMessage());
			throw ex;
			
		}finally{
			
		}
		
		return ret;
	}
	
	/************************************************************************************
	 * 
	 *   �o�^�����p�@XML�ʐM�����iJW820�j
	 *    : �o�^����XML�f�[�^�ʐM�iJW820�j���s��
	 *   @author TT nishigawa
	 *   
	 ************************************************************************************/
	private String conJW820(String genryoCd,String kbn) throws ExceptionBase{
		
		String genryo_cd = "";
		
		try{
			//----------------------------- ���M�p�����[�^�i�[  -----------------------------
			String strUser = DataCtrl.getInstance().getParamData().getDciUser().toString();
			
			//----------------------------- ���MXML�f�[�^�쐬  -----------------------------
			xmlJW820 = new XmlData();
			ArrayList arySetTag = new ArrayList();
			
			//--------------------------------- Root�ǉ�  ----------------------------------
			xmlJW820.AddXmlTag("","JW820");
			
			//--------------------------- �@�\ID�ǉ��iUSEERINFO�j  --------------------------
			xmlJW820.AddXmlTag("JW820", "USERINFO");
			//�@�e�[�u���^�O�ǉ�
			xmlJW820.AddXmlTag("USERINFO", "table");
			//�@���R�[�h�ǉ�
			arySetTag.add(new String[]{"kbn_shori", "3"});
			arySetTag.add(new String[]{"id_user",strUser});
			xmlJW820.AddXmlTag("table", "rec", arySetTag);
			//�z�񏉊���
			arySetTag.clear();
			
			//--------------------------- �@�\ID�ǉ��i���͌����o�^�j  -------------------------
			xmlJW820.AddXmlTag("JW820", "SA380");
			//�@�e�[�u���^�O�ǉ�
			xmlJW820.AddXmlTag("SA380", "table");
			
			arySetTag.add(new String[]{"cd_kaisha",checkNull(getSelectKaishaCd())});					//��ЃR�[�h
			arySetTag.add(new String[]{"cd_genryo",checkNull(genryoCd)});				//�����R�[�h
			arySetTag.add(new String[]{"nm_genryo",checkNull(genryoNmTextbox.getText())});				//������
			arySetTag.add(new String[]{"ritu_sakusan",checkNull(numTextbox[0].getText())});				//�|�_
			arySetTag.add(new String[]{"ritu_shokuen",checkNull(numTextbox[1].getText())});				//�H��
			arySetTag.add(new String[]{"ritu_sousan",checkNull(numTextbox[2].getText())});				//���_
// MOD start 20121122 QP@20505 �ۑ�No.10
//			arySetTag.add(new String[]{"ritu_abura",checkNull(numTextbox[3].getText())});				//���ܗL��
			arySetTag.add(new String[]{"ritu_msg",checkNull(numTextbox[3].getText())});				//�l�r�f
			arySetTag.add(new String[]{"ritu_abura",checkNull(numTextbox[4].getText())});				//���ܗL��
// MDO end 20121122 QP@20505 �ۑ�No.10
			arySetTag.add(new String[]{"hyojian",checkNull(hyojianTextarea.getText())});					//�\����
			arySetTag.add(new String[]{"tenkabutu",checkNull(tenkabutuTextarea.getText())});				//�Y����
			arySetTag.add(new String[]{"memo",checkNull(memoTextarea.getText())});						//����
			arySetTag.add(new String[]{"no_eiyo1",checkNull(eiyouTextbox[0].getText())});					//�h�{�v�Z1
			arySetTag.add(new String[]{"no_eiyo2",checkNull(eiyouTextbox[1].getText())});					//�h�{�v�Z2
			arySetTag.add(new String[]{"no_eiyo3",checkNull(eiyouTextbox[2].getText())});					//�h�{�v�Z3
			arySetTag.add(new String[]{"no_eiyo4",checkNull(eiyouTextbox[3].getText())});					//�h�{�v�Z4
			arySetTag.add(new String[]{"no_eiyo5",checkNull(eiyouTextbox[4].getText())});					//�h�{�v�Z5
			arySetTag.add(new String[]{"wariai1",checkNull(eiyouTextbox[5].getText())});					//����1
			arySetTag.add(new String[]{"wariai2",checkNull(eiyouTextbox[6].getText())});					//����2
			arySetTag.add(new String[]{"wariai3",checkNull(eiyouTextbox[7].getText())});					//����3
			arySetTag.add(new String[]{"wariai4",checkNull(eiyouTextbox[8].getText())});					//����4
			arySetTag.add(new String[]{"wariai5",checkNull(eiyouTextbox[9].getText())});					//����5
			arySetTag.add(new String[]{"kbn_haishi",checkNull( (haishiCheckbox.isSelected())?1:0 )});	//�p�~
			arySetTag.add(new String[]{"cd_kakutei",checkNull(kakuteiCdTextbox.getText())});			//�m��R�[�h

//mod start --------------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.13
//			//�m�F�`�F�b�N�{�b�N�X���`�F�b�N����Ă���ꍇ
//			if(kakuninCheckbox.isSelected()){
//				
//				arySetTag.add(new String[]{"id_kakunin",checkNull(kakuninId)});								//�m�F��ID
//				arySetTag.add(new String[]{"dt_kakunin",checkNull(kakuninhiTextbox.getText())});		//�m�F��
//			}
//			//�m�F�`�F�b�N�{�b�N�X���`�F�b�N����Ă��Ȃ��ꍇ
//			else{
//				
//				arySetTag.add(new String[]{"id_kakunin",""});		//�m�F��ID
//				arySetTag.add(new String[]{"dt_kakunin",""});		//�m�F��
//			}
			
			//�m�F���ޔ�
			boolean blnKakuninCheck = kakuninCheckbox.isSelected();
			String strKakuninDt = kakuninhiTextbox.getText();
			String strKakuninId = kakuninTextbox.getText();
			
			int fg = koshinJohoCheck();
			int henkou_fg = this.bunsekiDataHensyuCheck();
			
			if (henkou_fg == 1) {
				arySetTag.add(new String[]{"fg_henkou","true"});
			}
			else{
				arySetTag.add(new String[]{"fg_henkou","false"});
			}

			if (fg == 1) {
				//�m�F���Ƀ��[�U�����i�[(�����ł͔��肷�邽�߂�1���i�[)
				arySetTag.add(new String[]{"id_kakunin","1"});		//�m�F��ID
				arySetTag.add(new String[]{"dt_kakunin","1"});		//�m�F��

				arySetTag.add(new String[]{"id_toroku",checkNull(koshinId)});									//�o�^��ID
				arySetTag.add(new String[]{"dt_toroku",checkNull(koshinNm)});		//�o�^��
				
				//�m�F�`�F�b�N�t���O��true
				kakuninFg = true;
				
			} else if(fg == 0) {
				//�m�F����NULL���i�[
				arySetTag.add(new String[]{"id_kakunin",""});		//�m�F��ID
				arySetTag.add(new String[]{"dt_kakunin",""});		//�m�F��
				
				arySetTag.add(new String[]{"id_toroku",checkNull(torokuId)});	//�o�^��ID
				arySetTag.add(new String[]{"dt_toroku",checkNull(nyuryokuhiTextbox.getText())});	//�o�^��

				//�m�F���e��������
				kakuninCheckbox.setSelected(false);
				kakuninhiTextbox.setText(null);
				kakuninTextbox.setText(null);

				//�m�F�`�F�b�N�t���O��false
				kakuninFg = false;
			} else{
				return genryoCd;
			}
//mod end --------------------------------------------------------------------------------------
			
			//arySetTag.add(new String[]{"id_toroku",checkNull(torokuId)});									//�o�^��ID
			//arySetTag.add(new String[]{"dt_toroku",checkNull(nyuryokuhiTextbox.getText())});		//�o�^��
			arySetTag.add(new String[]{"kbn_shori",checkNull(kbn)});										//�����敪
			
			xmlJW820.AddXmlTag("table", "rec", arySetTag);
			arySetTag.clear();
			
			//----------------------------------- XML���M  ----------------------------------
			System.out.println("JW820���MXML===============================================================");
			xmlJW820.dispXml();
			XmlConnection xcon = new XmlConnection(xmlJW820);
			xcon.setStrAddress(DataCtrl.getInstance().getJnlpConnect().getStrAddressAjax());
			xcon.XmlSend();
			
			//----------------------------------- XML��M  ----------------------------------
			xmlJW820 = xcon.getXdocRes();
			//System.out.println();
			//System.out.println("JW820��MXML===============================================================");
			//xmlJW820.dispXml();
			
			//�e�X�gXML�f�[�^
			//xmlJW820 = new XmlData(new File("src/main/JW820.xml"));
			
			//------------------------------- Result�f�[�^�`�F�b�N -----------------------------
			DataCtrl.getInstance().getResultData().setResultData(xmlJW820);
			if ( !DataCtrl.getInstance().getResultData().isReturnFlgCheck() ) {
//add start --------------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.13
				//�m�F���e��������
				kakuninCheckbox.setSelected(blnKakuninCheck);
				kakuninhiTextbox.setText(strKakuninDt);
				kakuninTextbox.setText(strKakuninId);
//add end --------------------------------------------------------------------------------------				
				throw new ExceptionBase();
			}else{
				if(haishiCheckbox.isSelected()){
					button[0].setEnabled(false);
				}
				
				
				//�̔Ԍ����R�[�h�擾
				//�@�\ID�̐ݒ�
				String strKinoId = "SA380";
				
				//�S�̔z��擾
				ArrayList userData = xmlJW820.GetAryTag(strKinoId);
				
				//�@�\�z��擾
				ArrayList kinoData = (ArrayList)userData.get(0);

				//�e�[�u���z��擾
				ArrayList tableData = (ArrayList)kinoData.get(1);

				//���R�[�h�擾
				for(int i=1; i<tableData.size(); i++){
					//�@�P���R�[�h�擾
					ArrayList recData = ((ArrayList)((ArrayList)tableData.get(i)).get(0));
					for(int j=0; j<recData.size(); j++){
						//�@���ږ��擾
						String recNm = ((String[])recData.get(j))[1];
						//�@���ڒl�擾
						String recVal = ((String[])recData.get(j))[2];
						//�@�����R�[�h
						if ( recNm == "cd_genryo" ) {
							
							genryo_cd = recVal;
							
						}
					}
				}
				
				DataCtrl.getInstance().getMessageCtrl().PrintMessageString("����ɓo�^�������������܂����B");

//add start --------------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.13
				//�o�^�������͏���ޔ�
				this.bunsekiDataTaihi();
				
				if (fg == 1) {
					blnCheckKakunin = true;
					if (henkou_fg == 1) {
						// ���͓��e�L�X�g�{�b�N�X(���p)
						nyuryokuhiTextbox.setText(DataCtrl.getInstance().getTrialTblData().getSysDate());
						// ���͎҃e�L�X�g�{�b�N�X
						nyuryokushaTextbox.setText(DataCtrl.getInstance().getUserMstData().getStrUsernm());
					}
				}
				else if(fg == 0){
					blnCheckKakunin = false;
				}
//add end --------------------------------------------------------------------------------------
			}

		}catch(ExceptionBase ex){
			throw ex;
			
		}catch(Exception e){
			//�G���[�ݒ�
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			ex.setStrErrmsg("���͒l���̓p�l���̓o�^�ʐM���������s���܂���");
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getMessage());
			throw ex;
			
		}finally{
		}
		
		return genryo_cd;
		
	}
	
	/**
	 * �yJW610�z ��ЃR���{�{�b�N�X�l�擾 ���MXML�f�[�^�쐬
	 */
	private void conJW610() throws ExceptionBase{
		try{
			
			//�@���M�p�����[�^�i�[
			String strUser = DataCtrl.getInstance().getParamData().getDciUser().toString();
			
			//�@���MXML�f�[�^�쐬
			xmlJW610 = new XmlData();
			ArrayList arySetTag = new ArrayList();
			
			//�@Root�ǉ�
			xmlJW610.AddXmlTag("","JW610");
			arySetTag.clear();
			
			//�@�@�\ID�ǉ�
			xmlJW610.AddXmlTag("JW610", "USERINFO");
			//�@�e�[�u���^�O�ǉ�
			xmlJW610.AddXmlTag("USERINFO", "table");	
			//�@���R�[�h�ǉ�
			String[] kbn_shori = {"kbn_shori", "3"};
			String[] id_user = {"id_user",strUser};
			arySetTag.add(kbn_shori);
			arySetTag.add(id_user);
			xmlJW610.AddXmlTag("table", "rec", arySetTag);
			arySetTag.clear();

			//�@�y���������z �@�\ID�ǉ�
			xmlJW610.AddXmlTag("JW610", "SA140");

			//�@�e�[�u���^�O�ǉ�
			xmlJW610.AddXmlTag("SA140", "table");
			//�@���R�[�h�ǉ�
			id_user = new String[]{"id_user",strUser};
			String[] id_gamen = new String[]{"id_gamen", strGamenId};
			arySetTag.add(id_user);
			arySetTag.add(id_gamen);
			xmlJW610.AddXmlTag("table", "rec", arySetTag);
			arySetTag.clear();
			
			xmlJW610.dispXml();
			//�@XML���M
			XmlConnection xmlConnection = new XmlConnection(xmlJW610);
			xmlConnection.setStrAddress(DataCtrl.getInstance().getJnlpConnect().getStrAddressAjax());
			xmlConnection.XmlSend();
			//�@XML��M
			xmlJW610 = xmlConnection.getXdocRes();
			xmlJW610.dispXml();
			
			//�@�e�X�gXML�f�[�^
			//xmlJW610 = new XmlData(new File("src/main/JW610.xml"));


			// ��Ѓf�[�^
			KaishaData.setKaishaData(xmlJW610);

			// Result�f�[�^
			DataCtrl.getInstance().getResultData().setResultData(xmlJW610);
			if ( !DataCtrl.getInstance().getResultData().isReturnFlgCheck() ) {
				throw new ExceptionBase();
			}
			
		} catch (ExceptionBase e) {
			throw e;
			
		} catch (Exception e) {
			//�G���[�ݒ�
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			ex.setStrErrmsg("���͒l���̓p�l���̉�ЃR���{�{�b�N�X�l�擾�ʐM���������s���܂���");
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getMessage());
			throw ex;
			
		}finally{
		}
	}
	
	/**
	 * ��ЃR���{�{�b�N�X�ݒ菈��
	 * @throws ExceptionBase 
	 */
	private void setKaishaCmb() throws ExceptionBase {
		int i;
		
		try {
			String kaishaNm = "";
			
			//�R���{�{�b�N�X�̑S���ڂ̍폜
			this.kaishaComb.removeAllItems();
	
			//�\�����ɉ����ăR���{�{�b�N�X�ɒl��ǉ�
			for ( i=0; i<KaishaData.getArtKaishaCd().size(); i++ ) {
				//��Ж�
				kaishaNm = KaishaData.getAryKaishaNm().get(i).toString(); 
				
				//�R���{�{�b�N�X�֒ǉ�
				this.kaishaComb.addItem(kaishaNm);
			}
		}catch (Exception e) {
			//�G���[�ݒ�
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			ex.setStrErrmsg("���͒l���̓p�l���̉�ЃR���{�{�b�N�X�ݒ菈�������s���܂���");
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getMessage());
			throw ex;
			
		} finally {
		}
		
	}

//add start --------------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.13
	/**
	 * ���͏��ҏW�ޔ�
	 * @throws ExceptionBase
	 */
	private void bunsekiDataTaihi() throws ExceptionBase {
		
		try {
			//��ʏ��
			strGenryoNmOld = genryoNmTextbox.getText();						//������
			strSakusanOld = numTextbox[0].getText();						//�|�_
			strShokuenOld = numTextbox[1].getText();						//�H��
			strSousanOld = numTextbox[2].getText();							//���_
// MOD start 20121122 QP@20505 �ۑ�No.10
//			strGanyuOld = numTextbox[3].getText();							//���ܗL��
			strMsgOld = numTextbox[3].getText();							//MSG
			strGanyuOld = numTextbox[4].getText();							//���ܗL��
// MOD end 20121122 QP@20505 �ۑ�No.10
			strHyojiOld = hyojianTextarea.getText();						//�\����
			strTankaOld = tenkabutuTextarea.getText();						//�Y����
			strMemoOld = memoTextarea.getText();							//����
			strEiyonoOld[0] = eiyouTextbox[0].getText();					//�h�{�v�Z1
			strEiyonoOld[1] = eiyouTextbox[1].getText();					//�h�{�v�Z2
			strEiyonoOld[2] = eiyouTextbox[2].getText();					//�h�{�v�Z3
			strEiyonoOld[3] = eiyouTextbox[3].getText();					//�h�{�v�Z4
			strEiyonoOld[4] = eiyouTextbox[4].getText();					//�h�{�v�Z5
			strWariaiOld[0] = eiyouTextbox[5].getText();					//����1
			strWariaiOld[1] = eiyouTextbox[6].getText();					//����2
			strWariaiOld[2] = eiyouTextbox[7].getText();					//����3
			strWariaiOld[3] = eiyouTextbox[8].getText();					//����4
			strWariaiOld[4] = eiyouTextbox[9].getText();					//����5
			strKakuteiCdOld = kakuteiCdTextbox.getText();					//�m��R�[�h

		}catch(Exception ec){	
			//�G���[�ݒ�
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			ex.setStrErrmsg("���͏��ҏW�`�F�b�N���������s���܂���");
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(ec.getMessage());
			
		}finally{
			
		}
		
	}
	
	/**
	 * ���͏��ҏW�`�F�b�N
	 * @return 1:�ҏW��, 0:���ҏW��
	 * @throws ExceptionBase
	 */
	private int bunsekiDataHensyuCheck() throws ExceptionBase {
		
		try {
			//���݂̉�ʏ��
			String strGenryoNmNew = genryoNmTextbox.getText();					//������
			String strSakusanNew = numTextbox[0].getText();						//�|�_
			String strShokuenNew = numTextbox[1].getText();						//�H��
			String strSousanNew = numTextbox[2].getText();						//���_
// MDO start 20121122 QP@20505 �ۑ�No.10
//			String strGanyuNew = numTextbox[3].getText();						//���ܗL��
			String strMsgNew = numTextbox[3].getText();						//�l�r�f
			String strGanyuNew = numTextbox[4].getText();						//���ܗL��
// MOD end 20121122 QP@20505 �ۑ�No.10
			String strHyojiNew = hyojianTextarea.getText();						//�\����
			String strTankaNew = tenkabutuTextarea.getText();					//�Y����
			String strMemoNew = memoTextarea.getText();							//����
			String strEiyono1New = eiyouTextbox[0].getText();					//�h�{�v�Z1
			String strEiyono2New = eiyouTextbox[1].getText();					//�h�{�v�Z2
			String strEiyono3New = eiyouTextbox[2].getText();					//�h�{�v�Z3
			String strEiyono4New = eiyouTextbox[3].getText();					//�h�{�v�Z4
			String strEiyono5New = eiyouTextbox[4].getText();					//�h�{�v�Z5
			String strWariai1New = eiyouTextbox[5].getText();					//����1
			String strWariai2New = eiyouTextbox[6].getText();					//����2
			String strWariai3New = eiyouTextbox[7].getText();					//����3
			String strWariai4New = eiyouTextbox[8].getText();					//����4
			String strWariai5New = eiyouTextbox[9].getText();					//����5
			String strKakuteiCdNew = kakuteiCdTextbox.getText();				//�m��R�[�h

			//�ҏW�`�F�b�N : �ҏW������ꍇ�A1��ԋp���ď����I��
			if (!strGenryoNmNew.equals(strGenryoNmOld)) {
				return 1;
			} else if (!strSakusanNew.equals(strSakusanOld)) {
				return 1;
			} else if (!strShokuenNew.equals(strShokuenOld)) {
				return 1;
			} else if (!strSousanNew.equals(strSousanOld)) {
				return 1;
			} else if (!strGanyuNew.equals(strGanyuOld)) {
				return 1;
// ADD start 20121122 QP@20505 �ۑ�No.10
			} else if (!strMsgNew.equals(strMsgOld)) {
				return 1;
// ADD end 20121122 QP@20505 �ۑ�No.10
			} else if (!strHyojiNew.equals(strHyojiOld)) {
				return 1;
			} else if (!strTankaNew.equals(strTankaOld)) {
				return 1;
			} else if (!strMemoNew.equals(strMemoOld)) {
				return 1;
			} else if (!strEiyono1New.equals(strEiyonoOld[0])) {
				return 1;
			} else if (!strEiyono2New.equals(strEiyonoOld[1])) {
				return 1;
			} else if (!strEiyono3New.equals(strEiyonoOld[2])) {
				return 1;
			} else if (!strEiyono4New.equals(strEiyonoOld[3])) {
				return 1;
			} else if (!strEiyono5New.equals(strEiyonoOld[4])) {
				return 1;
			} else if (!strWariai1New.equals(strWariaiOld[0])) {
				return 1;
			} else if (!strWariai2New.equals(strWariaiOld[1])) {
				return 1;
			} else if (!strWariai3New.equals(strWariaiOld[2])) {
				return 1;
			} else if (!strWariai4New.equals(strWariaiOld[3])) {
				return 1;
			} else if (!strWariai5New.equals(strWariaiOld[4])) {
				return 1;
			} else if (!strKakuteiCdNew.equals(strKakuteiCdOld)) {
				return 1;
			}
			
		}catch(Exception ec){	
			//�G���[�ݒ�
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			ex.setStrErrmsg("���͏��ҏW�`�F�b�N���������s���܂���");
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(ec.getMessage());
			
		}finally{
			
		}
		
		return 0;
		
	}
	
	/**
	 * �X�V���m�F
	 * @return 0:NULL�i�[, 1:�m�F���Ƀ��[�U�����i�[
	 * @throws ExceptionBase
	 */
	private int koshinJohoCheck() throws ExceptionBase {
		int retFlg = 0;

		try {
			//�m�F�`�F�b�N
			if (blnCheckKakunin) {
				
				if(kakuninCheckbox.isSelected()){
					//���̓f�[�^�ҏW�`�F�b�N
					if (this.bunsekiDataHensyuCheck() == 1) {
						//���̓f�[�^���ҏW����Ă���ꍇ
						
						//�_�C�A���O�R���|�[�l���g�ݒ�
						JOptionPane jp = new JOptionPane();
										
						//�m�F�_�C�A���O�\��
						int option = JOptionPane.showConfirmDialog(
								jp.getRootPane(),
								"�m�F�ς݃f�[�^�̂��ߊm�F�҂��N���A���ĕۑ����܂�����낵���ł��傤���H"
								, "�m�F���b�Z�[�W"
								,JOptionPane.YES_NO_OPTION
								,JOptionPane.PLAIN_MESSAGE
							);
						
						//YES�ENO����
					    if (option == JOptionPane.YES_OPTION){
					    	//�u�͂��v����
					    	retFlg = 0;				    	
					    } else {
					    	//�u�������v����
					    	retFlg = 2;				    	
					    }
					} else {
						//���̓f�[�^�����ҏW�̏ꍇ
				    	retFlg = 1;
					}
				}
				else{
					retFlg = 0;	
				}
			} else {
				if(kakuninCheckbox.isSelected()){
					retFlg = 1;
				}
				else{
					retFlg = 0;	
				}
			}
	
		}catch(Exception ec){	
			//�G���[�ݒ�
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			ex.setStrErrmsg("�X�V���m�F���������s���܂���");
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(ec.getMessage());
			
		}finally{
			
		}
		
		return retFlg;
		
	}
//add end --------------------------------------------------------------------------------------
	
	/**
	 * �{�^�� �Q�b�^�[
	 */
	public ButtonBase[] getButton() {
		return button;
	}
	
	/**
	 * �V�X�e�����t �Q�b�^�[
	 * @return �V�X�e�����t�̒l��Ԃ�
	 */
	public String getSysDate(){
		return new SimpleDateFormat("yyyy/MM/dd").format(new Date());
	}
	
	/**
	 * NULL�`�F�b�N�����i�I�u�W�F�N�g�j
	 * @throws ExceptionBase
	 */
	private String checkNull(Object val){
		String ret = "";
		if(val != null){
			ret = val.toString();
		}
		return ret;
	}
	
	/**
	 * NULL�`�F�b�N�����i���l�j
	 * @throws ExceptionBase
	 */
	private String checkNull(int val){
		String ret = "";
		if(val >= 0){
			ret = Integer.toString(val);
		}
		return ret;
	}

}