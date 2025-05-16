package jp.co.blueflag.shisaquick.jws.panel;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableColumn;
import javax.swing.text.Document;

import jp.co.blueflag.shisaquick.jws.base.*;
import jp.co.blueflag.shisaquick.jws.button.LinkButton;
import jp.co.blueflag.shisaquick.jws.cellrenderer.MiddleCellRenderer;
import jp.co.blueflag.shisaquick.jws.cellrenderer.TextFieldCellRenderer;
import jp.co.blueflag.shisaquick.jws.common.*;
import jp.co.blueflag.shisaquick.jws.data.DataCtrl;
import jp.co.blueflag.shisaquick.jws.disp.AnalysisInputSubDisp;
import jp.co.blueflag.shisaquick.jws.label.*;
import jp.co.blueflag.shisaquick.jws.manager.*;
import jp.co.blueflag.shisaquick.jws.table.AnalysisCheckTable;
import jp.co.blueflag.shisaquick.jws.textbox.*;

/************************************************************************************
 * 
 * �yA05-04�z ���앪�̓f�[�^�m�F�p�l������p�̃N���X
 * 
 * @author TT.katayama
 * @since 2009/04/04
 * 
 ************************************************************************************/
public class AnalysisPanel extends PanelBase {
	private static final long serialVersionUID = 1L;

	private KaishaData KaishaData = new KaishaData();
	private BushoData BushoData = new BushoData();
	
	private DispTitleLabel dispTitleLabel;						//��ʃ^�C�g�����x��
	private HeaderLabel headerLabel;							//�w�b�_�\�����x��
	private LevelLabel levelLabel;									//���x���\�����x��
	private ItemLabel[] itemLabel;									//���ڃ��x��
	private CheckboxBase[] genryoCheckbox;					//�����`�F�b�N�{�b�N�X
	private HankakuTextbox genryoCdTextbox;				//�����R�[�h�e�L�X�g�{�b�N�X�i���p�j
	private TextboxBase genryoNmTextbox;					//�������̃e�L�X�g�{�b�N�X
	private ComboBase kaishaComb;								//��ЃR���{�{�b�N�X
	private ComboBase bushoComb;								//�����R���{�{�b�N�X
	private ButtonBase[] button;									//�{�^��
	private AnalysisCheckTable analysisCheckTable;		//���앪�̓f�[�^�m�F�e�[�u��
	private LinkButton[] linkBtn;									//�����N�{�^��
	private ItemLabel dataLabel;									//�f�[�^���\�����x��
	private LinkButton linkPrevBtn;								//�O���ڃ����N�{�^��
	private LinkButton linkNextBtn;								//�����ڃ����N�{�^��
//add start --------------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.4
	private JRadioButton[] shiyoFlgBtn;					//���W�I�{�^��
	private ButtonGroup copyCheck = new ButtonGroup();
	//��ЃR���{�{�b�N�X�@�Q�b�^���Z�b�^
	public ComboBase getKaishaComb() {
		return kaishaComb;
	}
	public void setKaishaComb(ComboBase kaishaComb) {
		this.kaishaComb = kaishaComb;
	}
	//�����R���{�{�b�N�X�@�Q�b�^���Z�b�^
	public ComboBase getBushoComb() {
		return bushoComb;
	}
	public void setBushoComb(ComboBase bushoComb) {
		this.bushoComb = bushoComb;
	}
	//��Ѓf�[�^�@�Q�b�^���Z�b�^
	public KaishaData getKaishaData() {
		return KaishaData;
	}
	public void setKaishaData(KaishaData kaishaData) {
		KaishaData = kaishaData;
	}
	//�����f�[�^�@�Q�b�^���Z�b�^
	public BushoData getBushoData() {
		return BushoData;
	}
	public void setBushoData(BushoData bushoData) {
		BushoData = bushoData;
	}
	//���W�I�{�^���@�Q�b�^���Z�b�^
	public JRadioButton[] getShiyoFlgBtn() {
		return shiyoFlgBtn;
	}
	public void setShiyoFlgBtn(JRadioButton[] shiyoFlgBtn) {
		this.shiyoFlgBtn = shiyoFlgBtn;
	}
//add end --------------------------------------------------------------------------------------
	
	private AnalysisInputSubDisp analysisInputSubDisp;	//���͒l���͉��
	
	private int intLinkMaxNum = 0;								//�����N�ő吔
	
	private XmlConnection xmlConnection;						//�w�l�k�ʐM
	private MessageCtrl messageCtrl;							//���b�Z�[�W����
	private ExceptionBase ex;										//�G���[����
	private XmlData xmlData;										//�w�l�k�f�[�^�ێ�
	
	private XmlData xmlJW610;
	private XmlData xmlJW620;
	private XmlData xmlJW710;
	private XmlData xmlJW720;
	private XmlData xmlJW730;
	private XmlData xmlJW740;
	
	int selectMaxNum = 0;
	int selectPage = 1;

	int selRow=0;

	private boolean isSelectKaishaCmb = false;	//��ЃR���{�{�b�N�X�I�������t���O
	//��ЃR���{�{�b�N�X�I�����̃C�x���g��
	private final String KAISHA_COMB_SELECT = "kaishaCombSelect";
//add start --------------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.4
	//�H��R���{�{�b�N�X�I�����̃C�x���g��
	private final String BUSHO_COMB_SELECT = "bushoCombSelect";
//add end --------------------------------------------------------------------------------------
	//�����N�{�^���������̃C�x���g��
	private final String LINK_BTN_CLICK = "linkBtnClick";
	//�u�O�ցv�����N�{�^���������̃C�x���g��
	private final String LINK_PREV_BTN_CLICK = "linkPrevBtnClick";
	//�u���ցv�����N�{�^���������̃C�x���g��
	private final String LINK_NEXT_BTN_CLICK = "linkNextBtnClick";
	
	private MaterialMstData bunsekiMaterialMst =new MaterialMstData();
	private MaterialMstData henkouMaterialMst =new MaterialMstData();
	
	//�����{�^�������׸�
	private boolean selectFg = false;

//add start --------------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.6
	private final String KOJO_ZENKOJO_TATE = "�S�H��_�c";		//�󔒂̏ꍇ�̕���
//add end --------------------------------------------------------------------------------------
	
	//2011/05/26 QP@10181_No.5 TT T.Satoh Add Start -------------------------
	//�C�x���g���擾�p
	private String event_name = "";
	//2011/05/26 QP@10181_No.5 TT T.Satoh Add End ---------------------------
	
	/************************************************************************************
	 * 
	 * �R���X�g���N�^
	 * @param strOutput : ��ʃ^�C�g��
	 * @throws ExceptionBase
	 * 
	 ************************************************************************************/
	public AnalysisPanel(String strOutput) throws ExceptionBase {
		//�X�[�p�[�N���X�̃R���X�g���N�^���Ăяo��
		super();

		try {
			//�p�l���̐ݒ�
			this.setPanel();
			
			//���͒l���͉�ʃN���X�̃C���X�^���X����
			ArrayList aList = DataCtrl.getInstance().getUserMstData().getAryAuthData();

			for (int i = 0; i < aList.size(); i++) {		
				String[] items = (String[]) aList.get(i);	
				
				//���앪�̓f�[�^�m�F��ʂ̎g�p���������邩�`�F�b�N����	
				if (items[0].toString().equals("130") || items[0].toString().equals("140")) {	
					//���͒l���͉�ʃN���X�̃C���X�^���X����
					this.analysisInputSubDisp = new AnalysisInputSubDisp("���͒l���͉��");
					this.analysisInputSubDisp.getAnalysisInputPanel().getButton()[0].addActionListener(this.getActionEvent());
					this.analysisInputSubDisp.getAnalysisInputPanel().getButton()[0].setActionCommand("toroku");
					break;
				}	
			}		
			
			//�R���g���[���z�u
			this.addControl(strOutput);
			
		}catch(ExceptionBase eb){
			
			throw eb;
			
		}catch(Exception e) {
			
			e.printStackTrace();
			
			//�G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("���앪�̓f�[�^�m�F�p�l���̃R���X�g���N�^�����s���܂����B");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;
			
		} finally {
		}
		
	}

	/************************************************************************************
	 * 
	 * �p�l���ݒ�
	 * 
	 ************************************************************************************/
	private void setPanel() {
		this.setLayout(null);
		this.setBackground(Color.WHITE);
	}
	
	/************************************************************************************
	 * 
	 * �R���g���[���z�u
	 * @param strTitle : ��ʃ^�C�g��
	 * @throws ExceptionBase 
	 * 
	 ************************************************************************************/
	private void addControl(String strTitle) throws ExceptionBase {
		try{
			int i;
			int x, y, width, height;
			int defLabelWidth = 60;
			int defTextWidth = 200;
			int defHeight = 18;
//add start --------------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.4
//			int dispWidth = 900;
			int dispWidth = 990;
			int defRadioWidth = 30;
//add end --------------------------------------------------------------------------------------
			
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
			this.levelLabel.setBounds(dispWidth - 65, 5, 50, 15);
			this.add(this.levelLabel);
			
			///
			/// �w�b�_���x���ݒ�
			///
			this.headerLabel = new HeaderLabel();
			this.headerLabel.setBounds(10, 5, dispWidth - 80, 15);
			this.add(this.headerLabel);

//mod start --------------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.4
			///
			/// ���ڃ��x���̐ݒ�
			/// [0:�V�K����, 1:��������, 2:�����R�[�h, 3:��������, 4:���, 5:�H��]
			/// [6, 7 : �f�[�^�`�F�b�N���̐���]
			/// [8, 9 :�g�p���уt���O, �S�����x��]
			///
//			this.itemLabel = new ItemLabel[8];
			this.itemLabel = new ItemLabel[10];
//mod end --------------------------------------------------------------------------------------
			//0 : �V�K����
			x = 5;
			y = 30;
			this.itemLabel[0] = new ItemLabel();
			this.itemLabel[0].setText("�V�K����");
			this.itemLabel[0].setBounds(x,y,defLabelWidth,defHeight);
			//1 : ��������
			x = this.itemLabel[0].getX() + defLabelWidth + 40;
			this.itemLabel[1] = new ItemLabel();
			this.itemLabel[1].setText("��������");
			this.itemLabel[1].setBounds(x,y,defLabelWidth,defHeight);
			
			//2 : �����R�[�h
			x = this.itemLabel[1].getX();
			y = this.itemLabel[1].getY() + defHeight + 10;
			this.itemLabel[2] = new ItemLabel();
			this.itemLabel[2].setText("�����R�[�h");
			this.itemLabel[2].setBounds(x,y,defLabelWidth,defHeight);
			//3 : ��������
			x = this.itemLabel[2].getX();
			y = this.itemLabel[2].getY() + defHeight + 5;
			this.itemLabel[3] = new ItemLabel();
			this.itemLabel[3].setText("��������");
			this.itemLabel[3].setBounds(x,y,defLabelWidth,defHeight);

			//4 : ���f�[�^�`�F�b�N�Ɗm�F�`�F�b�N�͕ʂɍs���ĉ�����
			width = 200;
			x = this.itemLabel[2].getX() + defLabelWidth + defTextWidth + 5;
			y = this.itemLabel[2].getY();
			this.itemLabel[4] = new ItemLabel();
//			this.itemLabel[4].setText("<html>���f�[�^�`�F�b�N�Ɗm�F�`�F�b�N��<br>&nbsp;&nbsp;&nbsp;&nbsp;�ʂɍs���ĉ�����</html>");
			this.itemLabel[4].setText("<html>���f�[�^�`�F�b�N�Ɗm�F�`�F�b�N��<br>&nbsp;&nbsp;�ʂɍs���ĉ�����</html>");
			this.itemLabel[4].setBackground(Color.WHITE);
			this.itemLabel[4].setBounds(x,y,width+56,defHeight + defHeight);
			//5 : ���f�[�^�ϊ�����Ɗm�F���ڂ͏����܂�
			x = this.itemLabel[4].getX();
			y = this.itemLabel[4].getY() + this.itemLabel[4].getHeight() + 5;
			this.itemLabel[5] = new ItemLabel();
			this.itemLabel[5].setText("���f�[�^�ϊ�����Ɗm�F���ڂ͏����܂�");
			this.itemLabel[5].setBackground(Color.WHITE);
			this.itemLabel[5].setBounds(x,y,width+56,defHeight);

//mod start --------------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.4
			//8 : �g�p���уt���O
			x = this.itemLabel[4].getX() + this.itemLabel[4].getWidth();
			y = this.itemLabel[4].getY();
			this.itemLabel[8] = new ItemLabel();
			this.itemLabel[8].setText(JwsConstManager.JWS_NM_SHIYO);
			this.itemLabel[8].setBounds(x,y,defLabelWidth,defHeight);
			//9 : �S���t���O
			x = this.itemLabel[8].getX();
			y = this.itemLabel[8].getY() + this.itemLabel[8].getHeight() + 5;
			this.itemLabel[9] = new ItemLabel();
			this.itemLabel[9].setText("�S��");
			this.itemLabel[9].setBounds(x,y,defLabelWidth,defHeight);
			
			//6 : ���
//			x = this.itemLabel[4].getX() + this.itemLabel[4].getWidth();
//			y = this.itemLabel[4].getY();
			x = this.itemLabel[8].getX() + this.itemLabel[8].getWidth() + defRadioWidth;
			y = this.itemLabel[8].getY();
//mod end --------------------------------------------------------------------------------------
			this.itemLabel[6] = new ItemLabel();
			this.itemLabel[6].setText("���");
			this.itemLabel[6].setBounds(x,y,defLabelWidth,defHeight);
			//7 : �H��
			x = this.itemLabel[6].getX();
			y = this.itemLabel[6].getY() + this.itemLabel[6].getHeight() + 5;
			this.itemLabel[7] = new ItemLabel();
			this.itemLabel[7].setText("�H��");
			this.itemLabel[7].setBounds(x,y,defLabelWidth,defHeight);
			
			//���ڃ��x�����p�l���ɒǉ�
			for ( i=0; i<this.itemLabel.length; i++ ) {
				this.add(this.itemLabel[i]);
			}
			
			///
			/// �����`�F�b�N�{�b�N�X
			/// [0:�V�K����, 1:��������]
			///
			this.genryoCheckbox = new CheckboxBase[2];
			//0:�V�K����
			x = this.itemLabel[0].getX();
			y = this.itemLabel[0].getY();
			width = this.itemLabel[0].getWidth();
			this.genryoCheckbox[0] = new CheckboxBase();
			this.genryoCheckbox[0].setBounds(x+width, y, 20,defHeight);
			//1:��������
			x = this.itemLabel[1].getX();
			y = this.itemLabel[1].getY();
			width = this.itemLabel[1].getWidth();
			this.genryoCheckbox[1] = new CheckboxBase();
			this.genryoCheckbox[1].setBounds(x+width, y, 20,defHeight);
			//�����`�F�b�N�{�b�N�X���p�l���ɒǉ�
			for ( i=0; i<this.genryoCheckbox.length; i++ ) {
				this.genryoCheckbox[i].setBackground(Color.white);
				this.add(this.genryoCheckbox[i]);
			}
			
			///
			///�����R�[�h�e�L�X�g�{�b�N�X�i���p�j
			///
			x = this.itemLabel[2].getX();
			y = this.itemLabel[2].getY();
			width = this.itemLabel[2].getWidth();
			this.genryoCdTextbox = new HankakuTextbox();
			this.genryoCdTextbox.setBounds(x+width, y, defTextWidth,defHeight);
			this.add(this.genryoCdTextbox);
			
			///
			///�������̃e�L�X�g�{�b�N�X
			///
			x = this.itemLabel[3].getX();
			y = this.itemLabel[3].getY();
			width = this.itemLabel[3].getWidth();
			this.genryoNmTextbox = new TextboxBase();
			this.genryoNmTextbox.setBounds(x+width, y, defTextWidth,defHeight);
			this.add(this.genryoNmTextbox);

//mod start --------------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.4
			///
			/// ���W�I�{�^���̐ݒ�[0:�g�p����, 1:�S��]
			/// 
			this.shiyoFlgBtn = new JRadioButton[2];
			x = this.itemLabel[8].getX() + defLabelWidth;
			y = this.itemLabel[8].getY();
			width = defRadioWidth;
			height = defHeight;
			//�g�p����
			this.shiyoFlgBtn[0] = new JRadioButton();
			this.shiyoFlgBtn[0].setBounds(x,y,width,height);
			this.shiyoFlgBtn[0].setSelected(true);
			this.shiyoFlgBtn[0].addActionListener(this.getActionEvent());
			this.shiyoFlgBtn[0].setActionCommand("sanKagetu");
			copyCheck.add(this.shiyoFlgBtn[0]);
			//�S��
			x = this.itemLabel[9].getX() + defLabelWidth;
			y = this.itemLabel[9].getY();
			this.shiyoFlgBtn[1] = new JRadioButton();
			this.shiyoFlgBtn[1].setBounds(x,y,width,height);
			this.shiyoFlgBtn[1].addActionListener(this.getActionEvent());
			this.shiyoFlgBtn[1].setActionCommand("zenKen");
			copyCheck.add(this.shiyoFlgBtn[1]);
			//�p�l���ɒǉ�
			for ( i=0; i<this.shiyoFlgBtn.length; i++ ) {
				this.shiyoFlgBtn[i].setBackground(Color.WHITE);
				this.add(this.shiyoFlgBtn[i]);
			}
			//�g�p���уt���O�E���g�p�t���O�̏����ݒ�
			this.shiyoFlgBtn[0].setEnabled(true);
			this.shiyoFlgBtn[1].setEnabled(true);
//mod end --------------------------------------------------------------------------------------
			
			///
			///��ЃR���{�{�b�N�X
			///
			x = this.itemLabel[6].getX();
			y = this.itemLabel[6].getY();
			width = this.itemLabel[6].getWidth();
			this.kaishaComb = new ComboBase();
			this.kaishaComb.setBounds(x+width, y, defTextWidth,defHeight);
			this.add(this.kaishaComb);

			///
			///�����R���{�{�b�N�X
			///
			x = this.itemLabel[7].getX();
			y = this.itemLabel[7].getY();
			width = this.itemLabel[7].getWidth();
			this.bushoComb = new ComboBase();
			this.bushoComb.setBounds(x+width, y, defTextWidth,defHeight);
			this.add(this.bushoComb);
			
			///
			/// �{�^��
			/// [0:����, 1:�ڍ�, 2:�V�K����, 3:�폜, 4:�I��]
			///
			this.button = new ButtonBase[5];		//TODO 6 -> 5
			//0:����
			x = 5;
			width = 80;
			height = 38;
			y = this.itemLabel[1].getY() + defHeight + 10;
			this.button[0] = new ButtonBase();
			this.button[0].setFont(new Font("Default", Font.PLAIN, 11));
			this.button[0].setBounds(x, y, width, height);
			this.button[0].setText("����");
			this.button[0].addActionListener(this.getActionEvent());
			this.button[0].setActionCommand("kensaku");
			
			//1:�ڍ�
			y = 450;
			this.button[1] = new ButtonBase();
			this.button[1].setFont(new Font("Default", Font.PLAIN, 11));
			this.button[1].setBounds(x, y, width, height);
			this.button[1].setText("�ڍ�");
			this.button[1].addActionListener(this.getActionEvent());
			this.button[1].setActionCommand("shosai_btn_click");
			//�����`�F�b�N
			ArrayList Auth = DataCtrl.getInstance().getUserMstData().getAryAuthData();
			boolean shosaiChk = false;
			for(int j=0;j<Auth.size();j++){
				String[] strDispAuth = (String[])Auth.get(j);
				if(strDispAuth[0].equals("140")){
					//�{�������̏ꍇ
					if(strDispAuth[1].equals("10")){
						shosaiChk = true;
					}
					//�ҏW�����̏ꍇ
					else if(strDispAuth[1].equals("20")){
						shosaiChk = true;
					}
					//�ҏW�i�S�āj�����̏ꍇ
					else if(strDispAuth[1].equals("40")){
						shosaiChk = true;
					}
				}
			}
			if(!shosaiChk){
				this.button[1].setEnabled(false);
			}
			
			
			//2:�V�K����
			x += width;
			this.button[2] = new ButtonBase();
			this.button[2].setFont(new Font("Default", Font.PLAIN, 11));
			this.button[2].setBounds(x, y, width, height);
			this.button[2].setText("�V�K����");
			this.button[2].addActionListener(this.getActionEvent());
			this.button[2].setActionCommand("shinkigenryo_btn_click");
			//�����`�F�b�N
			Auth = DataCtrl.getInstance().getUserMstData().getAryAuthData();
			boolean sinkiChk = false;
			for(int j=0;j<Auth.size();j++){
				String[] strDispAuth = (String[])Auth.get(j);
				if(strDispAuth[0].equals("130")){
					//�{�������̏ꍇ
					if(strDispAuth[1].equals("20")){
						sinkiChk = true;
					}
				}
			}
			if(!sinkiChk){
				this.button[2].setEnabled(false);
			}
			
			
			//3:�폜
			x += width;
			this.button[3] = new ButtonBase();
			this.button[3].setFont(new Font("Default", Font.PLAIN, 11));
			this.button[3].setBounds(x, y, width, height);
			this.button[3].setText("�폜");
			this.button[3].addActionListener(this.getActionEvent());
			this.button[3].setActionCommand("sakuzyo");
			//�����`�F�b�N
			Auth = DataCtrl.getInstance().getUserMstData().getAryAuthData();
			boolean delChk = false;
			for(int j=0;j<Auth.size();j++){
				String[] strDispAuth = (String[])Auth.get(j);
				if(strDispAuth[0].equals("160")){
					//�ҏW�����̏ꍇ
					if(strDispAuth[1].equals("20")){
						delChk = true;
					}
				}
			}
			if(!delChk){
				this.button[3].setEnabled(false);
			}
			
			//TODO 4:Excel�o��
//			width = 100;
//			x = dispWidth - 100 - width;
//			this.button[4] = new ButtonBase();
//			this.button[4].setFont(new Font("Default", Font.PLAIN, 11));
//			this.button[4].setBounds(x, y, width, height);
//			this.button[4].setText("�h�{�v�Z��");
//			this.button[4].addActionListener(this.getActionEvent());
//			this.button[4].setActionCommand("outputExcel");
			//5:�I��
			width = 80;
			x = dispWidth - 100;
			this.button[4] = new ButtonBase();
			this.button[4].setFont(new Font("Default", Font.PLAIN, 11));
			this.button[4].setBounds(x, y, width, height);
			this.button[4].setText("�I��");
			this.button[4].addActionListener(this.getActionEvent());
			this.button[4].setActionCommand("shuryo");
			//�{�^�����p�l���ɒǉ�
			for ( i=0; i<this.button.length; i++ ) {
				this.add(this.button[i]);
			}

			//�e�[�u���z�u
			this.addTable();
			
			
			///
			/// �f�[�^���\�����x���̐ݒ�
			///
			this.dataLabel = new ItemLabel();
			this.dataLabel.setBackground(Color.WHITE);
			this.dataLabel.setFont(new Font("Default", Font.PLAIN, 13));
			this.dataLabel.setText("");
			this.dataLabel.setHorizontalAlignment(JLabel.CENTER);
			this.dataLabel.setBounds(0, 400, 880, 20);
			this.add(this.dataLabel);
			
			
			///
			/// �����N�{�^���̐ݒ�
			///
			this.linkBtn = new LinkButton[12];
			for ( i=0; i<this.linkBtn.length-2; i++ ) {
				String strText = "" + (i + 1);
				//this.linkBtn[i] = new LinkButton(strText,120 + (i*35),410);
				this.linkBtn[i] = new LinkButton(strText,dispWidth/2 - 110 + ((i-2)*35),405);
			}
			this.linkBtn[10] = new LinkButton("�ŏ���");
			this.linkBtn[10].setBounds(150,this.linkBtn[0].getY(),80,this.linkBtn[0].getHeight());
			
			this.linkBtn[11] = new LinkButton("�Ō��");
//mod start --------------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.4
//			this.linkBtn[11].setBounds(685,this.linkBtn[9].getY(),80,this.linkBtn[9].getHeight());
			this.linkBtn[11].setBounds(this.linkBtn[9].getX() + this.linkBtn[9].getWidth() + this.linkBtn[9].getWidth()
					,this.linkBtn[9].getY(),80,this.linkBtn[9].getHeight());
//mod end --------------------------------------------------------------------------------------
			
			//�����N�{�^�����p�l���ɒǉ�
			for ( i=0; i<this.linkBtn.length; i++ ) {
				this.add(this.linkBtn[i]);
			}
			
			///
			/// �u���ցv�E�u�O�ցv�����N�{�^���̐ݒ�
			///
			//�����N�{�^���Ƀe�L�X�g�ƕ\�����W��ݒ�
			this.linkPrevBtn = new LinkButton("<<", this.linkBtn[0].getX() - this.linkBtn[0].getWidth(), this.linkBtn[0].getY());
			this.linkNextBtn = new LinkButton(">>", this.linkBtn[9].getX() + this.linkBtn[9].getWidth(), this.linkBtn[9].getY());
			this.linkPrevBtn.setEnabled(false);
			this.linkNextBtn.setEnabled(false);
			//�����N�{�^�����p�l���ɒǉ�
			this.add(this.linkPrevBtn);
			this.add(this.linkNextBtn);

			//��ЃR���{�{�b�N�X�ɃC�x���g��ݒ�
			this.kaishaComb.addActionListener(this.getActionEvent());
			this.kaishaComb.setActionCommand(this.KAISHA_COMB_SELECT);
//add start --------------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.4
			//�H��R���{�{�b�N�X�ɃC�x���g��ݒ�
			this.bushoComb.addActionListener(this.getActionEvent());
			this.bushoComb.setActionCommand(this.BUSHO_COMB_SELECT);
//add end --------------------------------------------------------------------------------------
			//�����N�{�^���ɃC�x���g��ݒ�
			for (i=0; i<this.linkBtn.length; i++ ){
				this.linkBtn[i].addActionListener(this.getActionEvent());
				this.linkBtn[i].setActionCommand(this.LINK_BTN_CLICK);
				
			}
			//�u���ցv�E�u�O�ցv�����N�{�^�����C�x���g��ݒ�
			this.linkPrevBtn.addActionListener(this.getActionEvent());
			this.linkPrevBtn.setActionCommand(this.LINK_PREV_BTN_CLICK);
			this.linkNextBtn.addActionListener(this.getActionEvent());
			this.linkNextBtn.setActionCommand(this.LINK_NEXT_BTN_CLICK);
			
		}catch(ExceptionBase eb){
			throw eb;
			
		}catch(Exception e){
			//�G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("���앪�̓f�[�^�m�F�p�l���̃R���g���[���z�u���������s���܂����B");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;
			
		}finally{
			
		}
		
	}

//mod start --------------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.5
	/**
	 * �t�H�[�J�X���ݒ�p�R���|�[�l���g�S�@�Q�b�^�[
	 * @return �t�H�[�J�X���ݒ�p�R���|�[�l���g�S 
	 */
	public JComponent[][] getSetFocusComponent() {
		JComponent[][] comp = new JComponent[][] {
				{ this.genryoNmTextbox, this.genryoCdTextbox },	//���O, �R�[�h
				this.shiyoFlgBtn,									//�g�p���сE�S�����W�I�{�^��
				{ this.kaishaComb, this.bushoComb },				//��ЃR���{, �H��R���{
				this.genryoCheckbox,								//�`�F�b�N�{�b�N�X
				this.button,										//�{�^��
				{ this.linkPrevBtn },								//�O�փ����N�{�^�� 
				this.linkBtn,										//�����N�{�^��
				{ this.linkNextBtn }								//���փ����N�{�^��
		};
		return comp;
	}
//mod end --------------------------------------------------------------------------------------
	
	/************************************************************************************
	 * 
	 * ����\���̓e�[�u������������
	 * @throws ExceptionBase 
	 * 
	 ************************************************************************************/
	private void addTable() throws ExceptionBase {
		try{
//mod start --------------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.4
			int intRow = 0;			//�s��
//			int intCol = 21;				//��
//			int intCol = 22;		//��
			int intCol = 23;		//��
			Object[] columnNm = new Object[intCol];		//��
			int[] columnWidth = new int[intCol];				//��

			//�񖼂̐ݒ�
/*			columnNm[0]  = "";
			columnNm[1]  = "����CD";
			columnNm[2]  = "������";
			columnNm[3]  = "�|�_(%)";
			columnNm[4]  = "�H��(%)";
			columnNm[5]  = "���_(%)";
			columnNm[6]  = "����(%)";
			columnNm[7]  = "�\����";
			columnNm[8]  = "�Y����";
			columnNm[9]  = "����";
			columnNm[10] = "<html>�h�{�v�Z<br>�H�i�ԍ�1</html>";
			columnNm[11] = "����1(%)";
			columnNm[12] = "<html>�h�{�v�Z<br>�H�i�ԍ�2</html>";
			columnNm[13] = "����2(%)";
			columnNm[14] = "<html>�h�{�v�Z<br>�H�i�ԍ�3</html>";
			columnNm[15] = "����3(%)";
			columnNm[16] = "<html>�h�{�v�Z<br>�H�i�ԍ�4</html>";
			columnNm[17] = "����4(%)";
			columnNm[18] = "<html>�h�{�v�Z<br>�H�i�ԍ�5</html>";
			columnNm[19] = "����5(%)";
			columnNm[20] = "�ŏI�g�p��";*/
			columnNm[0]  = "����CD";
			columnNm[1]  = "������";
			
			// �g�p���уt���O(2�����\��)
			String strShiyoFlg = JwsConstManager.JWS_NM_SHIYO;
			strShiyoFlg = "<html>" + strShiyoFlg.substring(0,1) + "<br>" + strShiyoFlg.substring(1,2) + "</html>";
			columnNm[2]  = strShiyoFlg;					//�g�p����		�ǉ�
			columnNm[3]  = "<html>��<br>�g</html>";		//���g�p		�ǉ�
			
			columnNm[4]  = "�|�_(%)";
			columnNm[5]  = "�H��(%)";
			columnNm[6]  = "���_(%)";
			//�yQP@20505_No11�z2012/10/19 TT H.SHIMA MOD Start
//			columnNm[7]  = "����(%)";
//			columnNm[8]  = "�\����";
//			columnNm[9]  = "�Y����";
//			columnNm[10]  = "����";
//			columnNm[11] = "<html>�h�{�v�Z<br>�H�i�ԍ�1</html>";
//			columnNm[12] = "����1(%)";
//			columnNm[13] = "<html>�h�{�v�Z<br>�H�i�ԍ�2</html>";
//			columnNm[14] = "����2(%)";
//			columnNm[15] = "<html>�h�{�v�Z<br>�H�i�ԍ�3</html>";
//			columnNm[16] = "����3(%)";
//			columnNm[17] = "<html>�h�{�v�Z<br>�H�i�ԍ�4</html>";
//			columnNm[18] = "����4(%)";
//			columnNm[19] = "<html>�h�{�v�Z<br>�H�i�ԍ�5</html>";
//			columnNm[20] = "����5(%)";
//			columnNm[21] = "�ŏI�g�p��";
			columnNm[7]  = "MSG(%)";
			columnNm[8]  = "����(%)";
			columnNm[9]  = "�\����";
			columnNm[10]  = "�Y����";
			columnNm[11]  = "����";
			columnNm[12] = "<html>�h�{�v�Z<br>�H�i�ԍ�1</html>";
			columnNm[13] = "����1(%)";
			columnNm[14] = "<html>�h�{�v�Z<br>�H�i�ԍ�2</html>";
			columnNm[15] = "����2(%)";
			columnNm[16] = "<html>�h�{�v�Z<br>�H�i�ԍ�3</html>";
			columnNm[17] = "����3(%)";
			columnNm[18] = "<html>�h�{�v�Z<br>�H�i�ԍ�4</html>";
			columnNm[19] = "����4(%)";
			columnNm[20] = "<html>�h�{�v�Z<br>�H�i�ԍ�5</html>";
			columnNm[21] = "����5(%)";
			columnNm[22] = "�ŏI�g�p��";
			//�yQP@20505_No11�z2012/10/19 TT H.SHIMA MOD End

			//�񕝂̐ݒ�
/*			columnWidth[0]  = 30;		//�A��
			columnWidth[1]  = 100;	//����CD
			columnWidth[2]  = 250;	//������
			columnWidth[3]  = 60;		//�|�_(%)
			columnWidth[4]  = 60;		//�H��(%)
			columnWidth[5]  = 60;		//���_(%)
			columnWidth[6]  = 60;		//����(%)
			columnWidth[7]  = 200;	//�\����
			columnWidth[8]  = 200;	//�Y����
			columnWidth[9]  = 200;	//����
			columnWidth[10] = 65;	//�h�{�v�Z�H�i�ԍ�1
			columnWidth[11] = 65;	//����1(%)
			columnWidth[12] = 65;	//�h�{�v�Z�H�i�ԍ�2
			columnWidth[13] = 65;	//����2(%)
			columnWidth[14] = 65;	//�h�{�v�Z�H�i�ԍ�3
			columnWidth[15] = 65;	//����3(%)
			columnWidth[16] = 65;	//�h�{�v�Z�H�i�ԍ�4
			columnWidth[17] = 65;	//����4(%)
			columnWidth[18] = 65;	//�h�{�v�Z�H�i�ԍ�5
			columnWidth[19] = 65;	//����5(%)
			columnWidth[20] = 120;	//�ŏI�w����*/
			columnWidth[0] = 100;	//����CD
			columnWidth[1] = 250;	//������
			columnWidth[2] = 20;		//�g�p����
			columnWidth[3] = 20;		//���g�p
			columnWidth[4] = 60;		//�|�_(%)
			columnWidth[5] = 60;		//�H��(%)
			columnWidth[6] = 60;		//���_(%)
			//�yQP@20505_No11�z2012/10/19 TT H.SHIMA MOD Start
//			columnWidth[7] = 60;		//����(%)
//			columnWidth[8] = 200;	//�\����
//			columnWidth[9] = 200;	//�Y����
//			columnWidth[10] = 200;	//����
//			columnWidth[11] = 65;		//�h�{�v�Z�H�i�ԍ�1
//			columnWidth[12] = 65;		//����1(%)
//			columnWidth[13] = 65;		//�h�{�v�Z�H�i�ԍ�2
//			columnWidth[14] = 65;		//����2(%)
//			columnWidth[15] = 65;		//�h�{�v�Z�H�i�ԍ�3
//			columnWidth[16] = 65;		//����3(%)
//			columnWidth[17] = 65;		//�h�{�v�Z�H�i�ԍ�4
//			columnWidth[18] = 65;		//����4(%)
//			columnWidth[19] = 65;		//�h�{�v�Z�H�i�ԍ�5
//			columnWidth[20] = 65;		//����5(%)
//			columnWidth[21] = 120;	//�ŏI�w����
			columnWidth[7] = 60;		//MSG
			columnWidth[8] = 60;		//����(%)
			columnWidth[9] = 200;	//�\����
			columnWidth[10] = 200;	//�Y����
			columnWidth[11] = 200;	//����
			columnWidth[12] = 65;		//�h�{�v�Z�H�i�ԍ�1
			columnWidth[13] = 65;		//����1(%)
			columnWidth[14] = 65;		//�h�{�v�Z�H�i�ԍ�2
			columnWidth[15] = 65;		//����2(%)
			columnWidth[16] = 65;		//�h�{�v�Z�H�i�ԍ�3
			columnWidth[17] = 65;		//����3(%)
			columnWidth[18] = 65;		//�h�{�v�Z�H�i�ԍ�4
			columnWidth[19] = 65;		//����4(%)
			columnWidth[20] = 65;		//�h�{�v�Z�H�i�ԍ�5
			columnWidth[21] = 65;		//����5(%)
			columnWidth[22] = 120;	//�ŏI�w����
			//�yQP@20505_No11�z2012/10/19 TT H.SHIMA MOD End
			
			//�e�[�u���̃C���X�^���X����
			this.analysisCheckTable = new AnalysisCheckTable(intRow, intCol, columnNm, columnWidth);
			//�e�[�u���pScroll�p�l���̐ݒ�
//			this.analysisCheckTable.getScroll().setBounds(5, 120, 880, 275);
			this.analysisCheckTable.getScroll().setBounds(5, 120, 970, 275);
			this.add(this.analysisCheckTable.getScroll());
//mod end --------------------------------------------------------------------------------------
			
			//�}�E�X�C�x���g�ݒ�
			this.analysisCheckTable.getMainTable().addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					//�I���s�̍s�ԍ����擾���܂�
					int ii = analysisCheckTable.getMainTable().getSelectedRow();
					MaterialData mt = (MaterialData)(bunsekiMaterialMst.getAryMateData().get(ii));
					bunsekiMaterialMst.setSelMate(mt);
					selRow = ii;
				}
			});
			
		}catch(ExceptionBase eb){
			throw eb;
			
		}catch(Exception e){
			//�G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("���앪�̓f�[�^�m�F�p�l���̃e�[�u�����������������s���܂����B");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;
			
		}finally{
			
		}
	}
	
	/************************************************************************************
	 * 
	 * ����������
	 * 
	 ************************************************************************************/
	public void init() throws ExceptionBase{
		try{
			selectFg = false;
			selectPage = 1;
			
			//------------------------------ �������ڂ̏����� -------------------------------
			//�����`�F�b�N�{�b�N�X
			genryoCheckbox[0].setSelected(false);
			genryoCheckbox[1].setSelected(false);
			//�����R�[�h�e�L�X�g�{�b�N�X�i���p�j
			genryoCdTextbox.setText(null);
			//�������̃e�L�X�g�{�b�N�X
			genryoNmTextbox.setText(null);
			
			//------------------------------- �e�[�u�������� ---------------------------------
			dispClear();
			
			//------------------------ ��ЃR���{�{�b�N�X�̏����ݒ� ----------------------------
			this.isSelectKaishaCmb = false;
			conJW610();
			this.setKaishaCmb();
			
			//------------------------ �����R���{�{�b�N�X�̏����ݒ� ----------------------------
			this.isSelectKaishaCmb = true;
			this.selectKaishaComb();

//add start --------------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.4
			//------------------------ �g�p���сE���g�p�̏����ݒ� ----------------------------
			//�g�p���уt���O�E���g�p�t���O�̏����ݒ�
			//this.shiyoFlgBtn[1].setSelected(true);
//			if (this.bushoComb.getSelectedItem().equals(KOJO_ZENKOJO_TATE)) {
//				this.shiyoFlgBtn[0].setEnabled(false);	
//			} else {
//				this.shiyoFlgBtn[0].setEnabled(true);
//			}
//add end --------------------------------------------------------------------------------------
			
			//---------------------------- �����N�{�^���̏����ݒ� ------------------------------
			for(int i=0; i<this.linkBtn.length-2; i++) {
				this.linkBtn[i].setEnabled(false);
			}
			//�u���ցv�E�u�O�ցv�����N�{�^���̏����ݒ�
			this.linkNextBtn.setEnabled(false);
			this.linkPrevBtn.setEnabled(false);
			
			//------------------------ ����\���̓f�[�^�����iJW710�j --------------------------
			conJW710(1);
			//�����N������
			for ( int i=0; i<10; i++) {
				linkBtn[i].setText("" + (i+1));
				//�����N�g�p�ۍĐݒ�
				if ( (i+1) <= intLinkMaxNum ) {
					linkBtn[i].setEnabled(true);
					
				} else {
					linkBtn[i].setEnabled(false);
					
				}
			}
			
			//-------------------------------- �e�[�u���\�� ----------------------------------
			dispTable();
			
		}catch(ExceptionBase eb){
			throw eb;
			
		}catch(Exception e){
			//�G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("���앪�̓f�[�^�m�F�p�l���̃e�[�u�����������������s���܂����B");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;
			
		}finally{
			
		}
	}
	
	/************************************************************************************
	 * 
	 *   �e�[�u���\��
	 *   @author TT nishigawa
	 *   
	 ************************************************************************************/
	public void dispTable() throws ExceptionBase{
		try{
			//�e�[�u��������
			dispClear();
			//�����ݒ�
			int max_row=0;
			int row_num=0;
			TableBase mt = analysisCheckTable.getMainTable();
			ArrayList aryMateData = bunsekiMaterialMst.getAryMateData();
			ArrayList aryHenkouData = henkouMaterialMst.getAryMateData();
			//2011/06/01 QP@10181_No.5 TT T.Satoh Add Start -------------------------
			//���בւ����z���f�[�^�e�[�u���i�[�p�z��p��
			ArrayList arySortHaigoData = new ArrayList();
			
			//�{�^��������
			if (event_name.equals("kensaku")
					|| event_name.equals("shosai_btn_click")
					|| event_name.equals("shinkigenryo_btn_click")
					|| event_name.equals(KAISHA_COMB_SELECT)
					|| event_name.equals(LINK_BTN_CLICK)
					|| event_name.equals("sakuzyo")
					|| event_name.equals("toroku")
					|| event_name.equals(LINK_PREV_BTN_CLICK)
					|| event_name.equals(LINK_NEXT_BTN_CLICK)) {
				
			}
			//�{�^���������ȊO�̏ꍇ(�����\��)
			else {
				//�z���\��ʂ̔z���f�[�^�z��擾
				ArrayList aryHaigoData = DataCtrl.getInstance().getTrialTblData().SearchHaigoData(0);
				
				//�����}�X�^����擾�����z������[�v
				for(int i=0; i<aryMateData.size(); i++){
					//�����}�X�^����1�s�擾
					MaterialData genryoMasterDt = (MaterialData)aryMateData.get(i);
					
					//�z���\��ʂ̔z���f�[�^�z������[�v
					for(int j=0; j<aryHaigoData.size(); j++){
						//�z���\��ʂ̔z���f�[�^��1�s�擾
						MixedData mxHaigoDt = (MixedData)aryHaigoData.get(j);
						
						//�����R�[�h����v����ꍇ
						if (genryoMasterDt.getStrGenryocd().equals(mxHaigoDt.getStrGenryoCd())) {
							//�z���\��ʂ̔z���f�[�^��1�s�ǉ�
							arySortHaigoData.add(mxHaigoDt);
							
							//�z���\��ʂ̔z���f�[�^�z��̃��[�v�𔲂���
							break;
						}
						//�����R�[�h��NULL�̏ꍇ
						else if (genryoMasterDt.getStrGenryocd().equals("NULL")
								&& mxHaigoDt.getStrGenryoCd() == null) {
							//�z���\��ʂ̔z���f�[�^��1�s�ǉ�
							arySortHaigoData.add(mxHaigoDt);
							
							//�z���\��ʂ̔z���f�[�^�z��̃��[�v�𔲂���
							break;
						}
					}
				}
			}
			//2011/06/01 QP@10181_No.5 TT T.Satoh Add End ---------------------------
			
			//�����_�������i������j
			MiddleCellRenderer md = new MiddleCellRenderer();
			
			//�����_�������i���l�j
			MiddleCellRenderer nmd = new MiddleCellRenderer();

			for(int i=0; i<aryMateData.size(); i++){
				
				//�f�[�^�}��
				MaterialData selMaterialData = (MaterialData)aryMateData.get(i);
				
				//2011/05/26 QP@10181_No.5 TT T.Satoh Add Start -------------------------
				MixedData mxSortHaigoData = null;
				
				//�{�^��������
				if (event_name.equals("kensaku")
						|| event_name.equals("shosai_btn_click")
						|| event_name.equals("shinkigenryo_btn_click")
						|| event_name.equals(KAISHA_COMB_SELECT)
						|| event_name.equals(LINK_BTN_CLICK)
						|| event_name.equals("sakuzyo")
						|| event_name.equals("toroku")
						|| event_name.equals(LINK_PREV_BTN_CLICK)
						|| event_name.equals(LINK_NEXT_BTN_CLICK)) {
					
				}
				//�{�^���������ȊO�̏ꍇ(�����\��)
				else {
					//���בւ����z���\��ʂ̔z���f�[�^��1�s�擾
					mxSortHaigoData = (MixedData)arySortHaigoData.get(i);
				}
				//2011/05/26 QP@10181_No.5 TT T.Satoh Add End ---------------------------
				
//				//�R�����g�s�͌������ύX
//				if(checkNull(selMaterialData.getStrGenryocd()).equals("999999")){
//					selMaterialData.setStrGenryonm("�R�����g�s�ł�");
//					//selMaterialData.setIntHaisicd(1);
//				}
				
				//�s�ǉ�
				mt.tableInsertRow(i);
				
				//�����_���ݒ�i������j
				TextboxBase comp = new TextboxBase();
				comp.setBackground(Color.WHITE);
				
				//�����_���ݒ�i���l�j
				NumelicTextbox ncomp = new NumelicTextbox();
				ncomp.setBackground(Color.WHITE);
								
				//�f�[�^�`�F�b�N
//				for(int j=0; j<aryHenkouData.size(); j++){
//					MaterialData selHenkouData = (MaterialData)aryHenkouData.get(j);
//					//�ύX����Ă���f�[�^������ꍇ
//					if((selMaterialData.getIntKaishacd() == selHenkouData.getIntKaishacd())
//							&& (selMaterialData.getIntBushocd() == selHenkouData.getIntBushocd())
//							&& (selMaterialData.getStrGenryocd().equals(selHenkouData.getStrGenryocd()))){
//						//comp.setBackground(Color.ORANGE);
//					}
//				}
				
				//���X�g�����̊i�[
				max_row = selMaterialData.getIntMaxRow();
				row_num = selMaterialData.getIntListRowMax();
				selectMaxNum = max_row;

//mod start --------------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.4
/*				//No
				mt.setValueAt(Integer.toString(i+1), i, 0);
				//����CD
				mt.setValueAt(selMaterialData.getStrGenryocd(), i, 1);
				//������
				mt.setValueAt(selMaterialData.getStrGenryonm(), i, 2);
				//�|�_(%)
				mt.setValueAt(selMaterialData.getDciSakusan(), i, 3);
				//�H��(%)
				mt.setValueAt(selMaterialData.getDciShokuen(), i, 4);
				//���_(%)
				mt.setValueAt(selMaterialData.getDciSousan(), i, 5);
				//����(%)
				mt.setValueAt(selMaterialData.getDciGanyu(), i, 6);
				//�\����
				mt.setValueAt(selMaterialData.getStrHyoji(), i, 7);
				//�Y����
				mt.setValueAt(selMaterialData.getStrTenka(), i, 8);
				//����
				mt.setValueAt(selMaterialData.getStrMemo(), i, 9);
				//�h�{�v�Z�H�i�ԍ�1
				mt.setValueAt(selMaterialData.getStrEiyono1(), i, 10);
				//����1(%)
				mt.setValueAt(selMaterialData.getStrWariai1(), i, 11);
				//�h�{�v�Z�H�i�ԍ�2
				mt.setValueAt(selMaterialData.getStrEiyono2(), i, 12);
				//����2(%)
				mt.setValueAt(selMaterialData.getStrWariai2(), i, 13);
				//�h�{�v�Z�H�i�ԍ�3
				mt.setValueAt(selMaterialData.getStrEiyono3(), i, 14);
				//����3(%)
				mt.setValueAt(selMaterialData.getStrWariai3(), i, 15);
				//�h�{�v�Z�H�i�ԍ�4
				mt.setValueAt(selMaterialData.getStrEiyono4(), i, 16);
				//����4(%)
				mt.setValueAt(selMaterialData.getStrWariai4(), i, 17);
				//�h�{�v�Z�H�i�ԍ�5
				mt.setValueAt(selMaterialData.getStrEiyono5(), i, 18);
				//����5(%)
				mt.setValueAt(selMaterialData.getStrWariai5(), i, 19);
				//�ŏI�w����
				mt.setValueAt(selMaterialData.getStrKonyu(), i, 20);*/
				//2011/06/01 QP@10181_No.5 TT T.Satoh Change Start -------------------------
				//����CD
				//mt.setValueAt(selMaterialData.getStrGenryocd(), i, 0);
				//������
				//mt.setValueAt(selMaterialData.getStrGenryonm(), i, 1);
				
				//����CD��NULL�̏ꍇ
				if (selMaterialData.getStrGenryocd().equals("NULL")) {
					//����CD�ɋ󔒂�\��
					mt.setValueAt("", i, 0);
				}
				//����CD������ꍇ
				else {
					//����CD�\��
					mt.setValueAt(selMaterialData.getStrGenryocd(), i, 0);
				}
				
				//�{�^��������
				if (event_name.equals("kensaku")
						|| event_name.equals("shosai_btn_click")
						|| event_name.equals("shinkigenryo_btn_click")
						|| event_name.equals(KAISHA_COMB_SELECT)
						|| event_name.equals(LINK_BTN_CLICK)
						|| event_name.equals("sakuzyo")
						|| event_name.equals("toroku")
						|| event_name.equals(LINK_PREV_BTN_CLICK)
						|| event_name.equals(LINK_NEXT_BTN_CLICK)) {
					//�������\��
					mt.setValueAt(selMaterialData.getStrGenryonm(), i, 1);
				}
				//�{�^���������ȊO�̏ꍇ(�����\��)
				else {
					//�������\��
					mt.setValueAt(mxSortHaigoData.getStrGenryoNm(), i, 1);
				}
				//2011/06/01 QP@10181_No.5 TT T.Satoh Change End ---------------------------
				
				// �����R�[�h�擪N�����`�F�b�N�@�iN�̏ꍇ�Atrue�j
				boolean isGenryoCdN = false;
				if (selMaterialData.getStrGenryocd().substring(0,1).equals("N")) {
					isGenryoCdN = true;
				} else {
					isGenryoCdN = false;
				}
				
				//�g�p����
				String strShiyoFlg = "";
				if (isGenryoCdN) {
					//�����R�[�h�擪N�����̏ꍇ
					strShiyoFlg = "  -";
				} else if (selMaterialData.getIntShiyoFlg() == 1) {
					//�g�p���уt���O��1
					strShiyoFlg = " ��";
				} else {
					//�g�p���уt���O��1
					strShiyoFlg = "  X";				
				}
				mt.setValueAt(strShiyoFlg, i, 2);
				
				//���g�p
				String strMiShiyoFlg = "";
				if (isGenryoCdN) {
					//�����R�[�h�擪N�����̏ꍇ
					strMiShiyoFlg = "";
				} else if (selMaterialData.getIntMishiyoFlg() == 1) {
					//���g�p�t���O��1
					strMiShiyoFlg = " ��";
				} else {
					//���g�p�t���O��1
					strMiShiyoFlg = "";	
				}
				mt.setValueAt(strMiShiyoFlg, i, 3);
				
				//�|�_(%)
				mt.setValueAt(selMaterialData.getDciSakusan(), i, 4);
				//�H��(%)
				mt.setValueAt(selMaterialData.getDciShokuen(), i, 5);
				//���_(%)
				mt.setValueAt(selMaterialData.getDciSousan(), i, 6);
				//�yQP@20505_No11�z2012/10/19 TT H.SHIMA ADD Start
				mt.setValueAt(selMaterialData.getDciMsg(), i, 7);
				//�yQP@20505_No11�z2012/10/19 TT H.SHIMA ADD End
				//�yQP@20505_No11�z2012/10/19 TT H.SHIMA MOD Start
				//����(%)
				//2011/06/01 QP@10181_No.5 TT T.Satoh Change Start -------------------------
				//mt.setValueAt(selMaterialData.getDciGanyu(), i, 7);
				
				//�{�^��������
				if (event_name.equals("kensaku")
						|| event_name.equals("shosai_btn_click")
						|| event_name.equals("shinkigenryo_btn_click")
						|| event_name.equals(KAISHA_COMB_SELECT)
						|| event_name.equals(LINK_BTN_CLICK)
						|| event_name.equals("sakuzyo")
						|| event_name.equals("toroku")
						|| event_name.equals(LINK_PREV_BTN_CLICK)
						|| event_name.equals(LINK_NEXT_BTN_CLICK)) {
					//����(%)�\��
//					mt.setValueAt(selMaterialData.getDciGanyu(), i, 7);
					mt.setValueAt(selMaterialData.getDciGanyu(), i, 8);
				}
				//�{�^���������ȊO�̏ꍇ(�����\��)
				else {
					//����(%)�\��
//					mt.setValueAt(mxSortHaigoData.getDciGanyuritu(), i, 7);
					mt.setValueAt(mxSortHaigoData.getDciGanyuritu(), i, 8);
				}
				//2011/06/01 QP@10181_No.5 TT T.Satoh Change End ---------------------------
//				//�\����
//				mt.setValueAt(selMaterialData.getStrHyoji(), i, 8);
//				//�Y����
//				mt.setValueAt(selMaterialData.getStrTenka(), i, 9);
//				//����
//				mt.setValueAt(selMaterialData.getStrMemo(), i, 10);
//				//�h�{�v�Z�H�i�ԍ�1
//				mt.setValueAt(selMaterialData.getStrEiyono1(), i, 11);
//				//����1(%)
//				mt.setValueAt(selMaterialData.getStrWariai1(), i, 12);
//				//�h�{�v�Z�H�i�ԍ�2
//				mt.setValueAt(selMaterialData.getStrEiyono2(), i, 13);
//				//����2(%)
//				mt.setValueAt(selMaterialData.getStrWariai2(), i, 14);
//				//�h�{�v�Z�H�i�ԍ�3
//				mt.setValueAt(selMaterialData.getStrEiyono3(), i, 15);
//				//����3(%)
//				mt.setValueAt(selMaterialData.getStrWariai3(), i, 16);
//				//�h�{�v�Z�H�i�ԍ�4
//				mt.setValueAt(selMaterialData.getStrEiyono4(), i, 17);
//				//����4(%)
//				mt.setValueAt(selMaterialData.getStrWariai4(), i, 18);
//				//�h�{�v�Z�H�i�ԍ�5
//				mt.setValueAt(selMaterialData.getStrEiyono5(), i, 19);
//				//����5(%)
//				mt.setValueAt(selMaterialData.getStrWariai5(), i, 20);
//				//�ŏI�w����
//				mt.setValueAt(selMaterialData.getStrKonyu(), i, 21);
				//�\����
				mt.setValueAt(selMaterialData.getStrHyoji(), i, 9);
				//�Y����
				mt.setValueAt(selMaterialData.getStrTenka(), i, 10);
				//����
				mt.setValueAt(selMaterialData.getStrMemo(), i, 11);
				//�h�{�v�Z�H�i�ԍ�1
				mt.setValueAt(selMaterialData.getStrEiyono1(), i, 12);
				//����1(%)
				mt.setValueAt(selMaterialData.getStrWariai1(), i, 13);
				//�h�{�v�Z�H�i�ԍ�2
				mt.setValueAt(selMaterialData.getStrEiyono2(), i, 14);
				//����2(%)
				mt.setValueAt(selMaterialData.getStrWariai2(), i, 15);
				//�h�{�v�Z�H�i�ԍ�3
				mt.setValueAt(selMaterialData.getStrEiyono3(), i, 16);
				//����3(%)
				mt.setValueAt(selMaterialData.getStrWariai3(), i, 17);
				//�h�{�v�Z�H�i�ԍ�4
				mt.setValueAt(selMaterialData.getStrEiyono4(), i, 18);
				//����4(%)
				mt.setValueAt(selMaterialData.getStrWariai4(), i, 19);
				//�h�{�v�Z�H�i�ԍ�5
				mt.setValueAt(selMaterialData.getStrEiyono5(), i, 20);
				//����5(%)
				mt.setValueAt(selMaterialData.getStrWariai5(), i, 21);
				//�ŏI�w����
				mt.setValueAt(selMaterialData.getStrKonyu(), i, 22);
				//�yQP@20505_No11�z2012/10/19 TT H.SHIMA MOD End
//mod end --------------------------------------------------------------------------------------
				
				if(selMaterialData.getIntHaisicd() == 1){
					comp.setBackground(Color.LIGHT_GRAY);
					ncomp.setBackground(Color.LIGHT_GRAY);
				}
				
				//�����񃌃��_���ݒ�
				TextFieldCellRenderer rendComp = new TextFieldCellRenderer(comp);
				md.add(i, rendComp);
				
				//���l�����_���ݒ�
				TextFieldCellRenderer nrendComp = new TextFieldCellRenderer(ncomp);
				nmd.add(i, nrendComp);
			}
			
			//�����_���ݒ�
			//�e�[�u���J�����擾
			DefaultTableColumnModel columnModel = (DefaultTableColumnModel)mt.getColumnModel();
			TableColumn column = null;
			for(int i = 0; i<mt.getColumnCount(); i++){
				
				//�e�[�u���J�����֐ݒ�
				column = mt.getColumnModel().getColumn(i);

//mod start --------------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.4
/*				//No
				if(i == 0){
					//�����񃌃��_���ݒ�
					column.setCellRenderer(md);
				}
				//����CD
				else if(i == 1){
					//�����񃌃��_���ݒ�
					column.setCellRenderer(md);
				}
				//������
				else if(i == 2){
					//�����񃌃��_���ݒ�
					column.setCellRenderer(md);
				}
				//�|�_(%)
				else if(i == 3){
					//���l�����_���ݒ�
					column.setCellRenderer(nmd);				
				}
				//�H��(%)
				else if(i == 4){
					//���l�����_���ݒ�
					column.setCellRenderer(nmd);
				}
				//���_(%)
				else if(i == 5){
					//���l�����_���ݒ�
					column.setCellRenderer(nmd);
				}				
				//����(%)
				else if(i == 6){
					//���l�����_���ݒ�
					column.setCellRenderer(nmd);
				}			
				//�\����
				else if(i == 7){
					//�����񃌃��_���ݒ�
					column.setCellRenderer(md);
				}		
				//�Y����
				else if(i == 8){
					//�����񃌃��_���ݒ�
					column.setCellRenderer(md);
				}	
				//����
				else if(i == 9){
					//�����񃌃��_���ݒ�
					column.setCellRenderer(md);
				}				
				//�h�{�v�Z�H�i�ԍ�1
				else if(i == 10){
					//�����񃌃��_���ݒ�
					column.setCellRenderer(md);
				}				
				//����1(%)
				else if(i == 11){
					//���l�����_���ݒ�
					column.setCellRenderer(nmd);
				}				
				//�h�{�v�Z�H�i�ԍ�2
				else if(i == 12){
					//�����񃌃��_���ݒ�
					column.setCellRenderer(md);
				}		
				//����2(%)
				else if(i == 13){
					//���l�����_���ݒ�
					column.setCellRenderer(nmd);
				}			
				//�h�{�v�Z�H�i�ԍ�3
				else if(i == 14){
					//�����񃌃��_���ݒ�
					column.setCellRenderer(md);
				}				
				//����3(%)
				else if(i == 15){
					//���l�����_���ݒ�
					column.setCellRenderer(nmd);
				}				
				//�h�{�v�Z�H�i�ԍ�4
				else if(i == 16){
					//�����񃌃��_���ݒ�
					column.setCellRenderer(md);
				}				
				//����4(%)
				else if(i == 17){
					//���l�����_���ݒ�
					column.setCellRenderer(nmd);
				}	
				//�h�{�v�Z�H�i�ԍ�5
				else if(i == 18){
					//�����񃌃��_���ݒ�
					column.setCellRenderer(md);
				}			
				//����5(%)
				else if(i == 19){
					//���l�����_���ݒ�
					column.setCellRenderer(nmd);
				}			
				//�ŏI�w����
				else if(i == 20){
					//�����񃌃��_���ݒ�
					column.setCellRenderer(md);
				}*/
				//����CD
				if(i == 0){
					//�����񃌃��_���ݒ�
					column.setCellRenderer(md);
				}
				//������
				else if(i == 1){
					//�����񃌃��_���ݒ�
					column.setCellRenderer(md);
				}
				//�g�p����
				else if(i == 2){
					//�����񃌃��_���ݒ�
					column.setCellRenderer(md);			
				}
				//���g�p
				else if(i == 3){
					//�����񃌃��_���ݒ�
					column.setCellRenderer(md);			
				}
				//�|�_(%)
				else if(i == 4){
					//���l�����_���ݒ�
					column.setCellRenderer(nmd);				
				}
				//�H��(%)
				else if(i == 5){
					//���l�����_���ݒ�
					column.setCellRenderer(nmd);
				}
				//���_(%)
				else if(i == 6){
					//���l�����_���ݒ�
					column.setCellRenderer(nmd);
				}				
				//�yQP@20505_No11�z2012/10/19 TT H.SHIMA MOD Start
//				//����(%)
//				else if(i == 7){
//					//���l�����_���ݒ�
//					column.setCellRenderer(nmd);
//				}			
//				//�\����
//				else if(i == 8){
//					//�����񃌃��_���ݒ�
//					column.setCellRenderer(md);
//				}		
//				//�Y����
//				else if(i == 9){
//					//�����񃌃��_���ݒ�
//					column.setCellRenderer(md);
//				}	
//				//����
//				else if(i == 10){
//					//�����񃌃��_���ݒ�
//					column.setCellRenderer(md);
//				}				
//				//�h�{�v�Z�H�i�ԍ�1
//				else if(i == 11){
//					//�����񃌃��_���ݒ�
//					column.setCellRenderer(md);
//				}				
//				//����1(%)
//				else if(i == 12){
//					//���l�����_���ݒ�
//					column.setCellRenderer(nmd);
//				}				
//				//�h�{�v�Z�H�i�ԍ�2
//				else if(i == 13){
//					//�����񃌃��_���ݒ�
//					column.setCellRenderer(md);
//				}		
//				//����2(%)
//				else if(i == 14){
//					//���l�����_���ݒ�
//					column.setCellRenderer(nmd);
//				}			
//				//�h�{�v�Z�H�i�ԍ�3
//				else if(i == 15){
//					//�����񃌃��_���ݒ�
//					column.setCellRenderer(md);
//				}				
//				//����3(%)
//				else if(i == 16){
//					//���l�����_���ݒ�
//					column.setCellRenderer(nmd);
//				}				
//				//�h�{�v�Z�H�i�ԍ�4
//				else if(i == 17){
//					//�����񃌃��_���ݒ�
//					column.setCellRenderer(md);
//				}				
//				//����4(%)
//				else if(i == 18){
//					//���l�����_���ݒ�
//					column.setCellRenderer(nmd);
//				}	
//				//�h�{�v�Z�H�i�ԍ�5
//				else if(i == 19){
//					//�����񃌃��_���ݒ�
//					column.setCellRenderer(md);
//				}			
//				//����5(%)
//				else if(i == 20){
//					//���l�����_���ݒ�
//					column.setCellRenderer(nmd);
//				}			
//				//�ŏI�w����
//				else if(i == 21){
//					//�����񃌃��_���ݒ�
//					column.setCellRenderer(md);
//				}
				//MSG(%)
				else if(i == 7){
					//���l�����_���ݒ�
					column.setCellRenderer(nmd);
				}			
				//����(%)
				else if(i == 8){
					//���l�����_���ݒ�
					column.setCellRenderer(nmd);
				}			
				//�\����
				else if(i == 9){
					//�����񃌃��_���ݒ�
					column.setCellRenderer(md);
				}		
				//�Y����
				else if(i == 10){
					//�����񃌃��_���ݒ�
					column.setCellRenderer(md);
				}	
				//����
				else if(i == 11){
					//�����񃌃��_���ݒ�
					column.setCellRenderer(md);
				}				
				//�h�{�v�Z�H�i�ԍ�1
				else if(i == 12){
					//�����񃌃��_���ݒ�
					column.setCellRenderer(md);
				}				
				//����1(%)
				else if(i == 13){
					//���l�����_���ݒ�
					column.setCellRenderer(nmd);
				}				
				//�h�{�v�Z�H�i�ԍ�2
				else if(i == 14){
					//�����񃌃��_���ݒ�
					column.setCellRenderer(md);
				}		
				//����2(%)
				else if(i == 15){
					//���l�����_���ݒ�
					column.setCellRenderer(nmd);
				}			
				//�h�{�v�Z�H�i�ԍ�3
				else if(i == 16){
					//�����񃌃��_���ݒ�
					column.setCellRenderer(md);
				}				
				//����3(%)
				else if(i == 17){
					//���l�����_���ݒ�
					column.setCellRenderer(nmd);
				}				
				//�h�{�v�Z�H�i�ԍ�4
				else if(i == 18){
					//�����񃌃��_���ݒ�
					column.setCellRenderer(md);
				}				
				//����4(%)
				else if(i == 19){
					//���l�����_���ݒ�
					column.setCellRenderer(nmd);
				}	
				//�h�{�v�Z�H�i�ԍ�5
				else if(i == 20){
					//�����񃌃��_���ݒ�
					column.setCellRenderer(md);
				}			
				//����5(%)
				else if(i == 21){
					//���l�����_���ݒ�
					column.setCellRenderer(nmd);
				}			
				//�ŏI�w����
				else if(i == 22){
					//�����񃌃��_���ݒ�
					column.setCellRenderer(md);
				}
				//�yQP@20505_No11�z2012/10/19 TT H.SHIMA MOD End
//mod end --------------------------------------------------------------------------------------
				//��L�ȊO
				else{
					//�����񃌃��_���ݒ�
					column.setCellRenderer(md);
				}
			}
			
			//�����N�{�^���ݒ�
//			int index=0;
//			for(int i=max_row; i>0; i-=row_num){
//				linkBtn[index].setEnabled(true);
//				index++;
//			}
//			intLinkMaxNum = index;
			initLink(max_row, row_num);
			
		}catch(Exception e){
			//�G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("���앪�̓f�[�^�m�F�p�l���̃e�[�u���\�����������s���܂����B");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;
		}
		
	}
	
	/************************************************************************************
	 * 
	 *   �����N�{�^������������
	 *   
	 *   @author TT katayama
	 *   
	 ************************************************************************************/
	private void initLink(int intMaxRow, int intListRowMax) {
		//TODO

		//�����N�{�^���̏����ݒ�
		for(int i=0; i<this.linkBtn.length; i++) {
//			if ( i < 10 ) {
//				this.linkBtn[i].setText("" + (i+1));	
//			}
			this.linkBtn[i].setEnabled(false);
		}
		this.linkPrevBtn.setEnabled(false);
		this.linkNextBtn.setEnabled(false);
		
		//�ő�l��0�łȂ��ꍇ
		if ( intMaxRow != 0 ) {
			if ( (intMaxRow%intListRowMax != 0) ) {
				intLinkMaxNum = (intMaxRow/intListRowMax)+1;
			} else {
				intLinkMaxNum = (intMaxRow/intListRowMax);
			}

			for( int i=0; i<intLinkMaxNum; i++ ) {
				if ( i < 10 ) {
					if ( Integer.parseInt(this.linkBtn[i].getText()) <= intLinkMaxNum ) {
						this.linkBtn[i].setEnabled(true);
						
					}
				}
			}
			this.linkBtn[10].setEnabled(true);
			this.linkBtn[11].setEnabled(true);
			
			//�������ʂ̌������\���ő匏�����A�����Ă���ꍇ
			//if ( this.intLinkMaxNum > intListRowMax ) {
			if ( intMaxRow > (intListRowMax*10) ) {	
				//[����]�E[�O��]�����N�{�^�����g�p�ɐݒ�
				this.linkPrevBtn.setEnabled(true);
				this.linkNextBtn.setEnabled(true);
			}
		}
	}
	
	/************************************************************************************
	 * 
	 *   ���������p�@XML�ʐM�����iJW710�j
	 *    : ��������XML�f�[�^�ʐM�iJW710�j���s��
	 *   @author TT nishigawa
	 *   
	 ************************************************************************************/
	private void conJW710(int page) throws ExceptionBase{
		try{
			//----------------------------- ���M�p�����[�^�i�[  -------------------------------
			String strUser = DataCtrl.getInstance().getParamData().getDciUser().toString();
			//�z���f�[�^
			ArrayList aryHaigoData = DataCtrl.getInstance().getTrialTblData().SearchHaigoData(0);
			
			//----------------------------- ���MXML�f�[�^�쐬  ------------------------------
			xmlJW710 = new XmlData();
			ArrayList arySetTag = new ArrayList();
			
			//--------------------------------- Root�ǉ�  ---------------------------------
			xmlJW710.AddXmlTag("","JW710");
			arySetTag.clear();
			
			//--------------------------- �@�\ID�ǉ��iUSEERINFO�j  --------------------------
			xmlJW710.AddXmlTag("JW710", "USERINFO");
			//�@�e�[�u���^�O�ǉ�
			xmlJW710.AddXmlTag("USERINFO", "table");
			//�@���R�[�h�ǉ�
			arySetTag.add(new String[]{"kbn_shori", "3"});
			arySetTag.add(new String[]{"id_user",strUser});
			xmlJW710.AddXmlTag("table", "rec", arySetTag);
			//�z�񏉊���
			arySetTag.clear();
			
			//------------------------ �@�\ID�ǉ��i���͒l�ύX�m�F�j  ----------------------------
//			xmlJW710.AddXmlTag("JW710", "SA590");
//			//�@�e�[�u���^�O�ǉ�
//			xmlJW710.AddXmlTag("SA590", "table");
//			
//			for (int i=0; i<aryHaigoData.size(); i++  ) {
//				MixedData mixedData = (MixedData)aryHaigoData.get(i);
//				//���X�|���X�f�[�^�̐ݒ�
//				arySetTag.add(new String[]{"cd_kaisha",checkNull(mixedData.getIntKaishaCd())});		//��ЃR�[�h(����)
//				arySetTag.add(new String[]{"cd_busho",checkNull(mixedData.getIntBushoCd())});		//�����R�[�h(����)
//				arySetTag.add(new String[]{"cd_genryo",checkNull(mixedData.getStrGenryoCd())});		//�����R�[�h(����)
//				arySetTag.add(new String[]{"nm_genryo",checkNull(mixedData.getStrGenryoNm())});		//������(����)
//				arySetTag.add(new String[]{"tanka",checkNull(mixedData.getDciTanka())});			//�P��(����)
//				arySetTag.add(new String[]{"budomari",checkNull(mixedData.getDciBudomari())});		//����(����)
//				arySetTag.add(new String[]{"ritu_abura",checkNull(mixedData.getDciGanyuritu())});	//���ܗL��(����)
//				arySetTag.add(new String[]{"ritu_sakusan",checkNull(mixedData.getDciSakusan())});	//�|�_(����)
//				arySetTag.add(new String[]{"ritu_shokuen",checkNull(mixedData.getDciShokuen())});	//�H��(����)
//				arySetTag.add(new String[]{"ritu_sousan",checkNull(mixedData.getDciSosan())});		//���_(����)
//				xmlJW710.AddXmlTag("table", "rec", arySetTag);
//				arySetTag.clear();
//			}
			
			//------------------------ �@�\ID�ǉ��i����\���������j  ----------------------------
			xmlJW710.AddXmlTag("JW710", "SA570");
			//�@�e�[�u���^�O�ǉ�
			xmlJW710.AddXmlTag("SA570", "table");
			
			//�z���f�[�^���[�v
			for (int i=0; i<aryHaigoData.size(); i++  ) {
				
				//�z���f�[�^�擾
				MixedData mixedData = (MixedData)aryHaigoData.get(i);
				
				//���������擾
				int keta = DataCtrl.getInstance().getTrialTblData().getKaishaGenryo();
				
				//�R�����g�s����
				boolean comFlg = DataCtrl.getInstance().getTrialTblData().commentChk(mixedData.getStrGenryoCd(), keta);
				
				//�R�����g�s�̏ꍇ
				if( comFlg ){
					
				}else{
					
					//���X�|���X�f�[�^�̐ݒ�
					arySetTag.add(new String[]{"cd_kaisha",checkNull(mixedData.getIntKaishaCd())});		//��ЃR�[�h(����)
					
					//mod start --------------------------------------------------------------------------------------
					//QP@00412_�V�T�N�C�b�N���� No.4
					//arySetTag.add(new String[]{"cd_busho",checkNull(mixedData.getIntBushoCd())});		//�����R�[�h(����)
					PrototypeData pt = DataCtrl.getInstance().getTrialTblData().SearchShisakuhinData();
					arySetTag.add(new String[]{"cd_busho",Integer.toString(pt.getIntKojoco())});		//�����R�[�h(����)
					//mod end --------------------------------------------------------------------------------------
					
					arySetTag.add(new String[]{"cd_genryo",checkNull(mixedData.getStrGenryoCd())});		//�����R�[�h(����)
					arySetTag.add(new String[]{"num_selectRow",Integer.toString(page)});		//�I���y�[�W�ԍ�(����)
					arySetTag.add(new String[]{"gyo_no",Integer.toString(mixedData.getIntGenryoNo())});		//�I���y�[�W�ԍ�(����)
					xmlJW710.AddXmlTag("table", "rec", arySetTag);
					arySetTag.clear();
					
				}
				
			}
			
			//----------------------------------- XML���M  ----------------------------------
//			System.out.println("\nJW710���MXML===============================================================");
//			xmlJW710.dispXml();
			XmlConnection xcon = new XmlConnection(xmlJW710);
			xcon.setStrAddress(DataCtrl.getInstance().getJnlpConnect().getStrAddressAjax());
			xcon.XmlSend();
			
			//----------------------------------- XML��M  ----------------------------------
			xmlJW710 = xcon.getXdocRes();
//			System.out.println("\nJW710��MXML===============================================================");
//			xmlJW710.dispXml();
			
			//�e�X�gXML�f�[�^
			//xmlJW710 = new XmlData(new File("src/main/JW710.xml"));
			
			//------------------------------- Result�f�[�^�`�F�b�N -----------------------------
			DataCtrl.getInstance().getResultData().setResultData(xmlJW710);
			if ( !DataCtrl.getInstance().getResultData().isReturnFlgCheck() ) {
				
				//��ʏ��N���A
				dispClear();
				
				//�f�[�^���\�����x���N���A
				this.dataLabel.setText("");
				
				throw new ExceptionBase();
				
			}else{
				//--------------------------------- �ύX���i�[ --------------------------------
//				henkouMaterialMst = new MaterialMstData();
//				henkouMaterialMst.setMaterialData(xmlJW710, "SA590");
				//henkouMaterialMst.DispMateData();
				
				//------------------------------- �������̓f�[�^�i�[ -----------------------------
				bunsekiMaterialMst.setMaterialData(xmlJW710,"SA570");
				//bunsekiMaterialMst.DispMateData();
				
				//�f�[�^���\���ݒ�
				//���X�g�\�������y�эő匏�����擾
				int intListRowMax = ((MaterialData)bunsekiMaterialMst.getAryMateData().get(0)).getIntListRowMax();
				int intMaxRow = ((MaterialData)bunsekiMaterialMst.getAryMateData().get(0)).getIntMaxRow();
				
				//�ő�l��0�łȂ��ꍇ
				if ( intMaxRow != 0 ) {
					if ( (intMaxRow%intListRowMax != 0) ) {
						intLinkMaxNum = (intMaxRow/intListRowMax)+1;
					} else {
						intLinkMaxNum = (intMaxRow/intListRowMax);
					}
				}
				
				//�f�[�^���̕\��
				this.dataLabel.setText("<html>�f�[�^���@�F�@" + intMaxRow + " ���ł�(" + intListRowMax + "�����ɕ\�����Ă��܂�)�@�@�@<b>"+selectPage+"�^"+intLinkMaxNum+" �y�[�W<b></html>");
			}

		}catch(ExceptionBase ex){
			throw ex;
			
		}catch(Exception e){
			//�G���[�ݒ�
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			ex.setStrErrmsg("���앪�̓f�[�^�m�F�p�l���̏��������ʐM���������s���܂����B");
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getMessage());
			throw ex;
			
		}finally{
		}
	}
	
	/************************************************************************************
	 * 
	 *   ���������p�@XML�ʐM�����iJW720�j
	 *    : ��������XML�f�[�^�ʐM�iJW720�j���s��
	 *   @author TT nishigawa
	 *   
	 ************************************************************************************/
	public void conJW720(int page) throws ExceptionBase{
		try{
			
			//----------------------------- ���M�p�����[�^�i�[  -------------------------------
			String strUser = DataCtrl.getInstance().getParamData().getDciUser().toString();
			String strKaishaCd = this.getSelectKaishaCd();
			String strBushoCd = this.getSelectKojoCd();
			
			//----------------------------- ���MXML�f�[�^�쐬  ------------------------------
			xmlJW720 = new XmlData();
			ArrayList arySetTag = new ArrayList();
			
			//--------------------------------- Root�ǉ�  ---------------------------------
			xmlJW720.AddXmlTag("","JW720");
			arySetTag.clear();
			
			//--------------------------- �@�\ID�ǉ��iUSEERINFO�j  --------------------------
			xmlJW720.AddXmlTag("JW720", "USERINFO");
			//�@�e�[�u���^�O�ǉ�
			xmlJW720.AddXmlTag("USERINFO", "table");
			//�@���R�[�h�ǉ�
			arySetTag.add(new String[]{"kbn_shori", "3"});
			arySetTag.add(new String[]{"id_user",strUser});
			xmlJW720.AddXmlTag("table", "rec", arySetTag);
			//�z�񏉊���
			arySetTag.clear();
			
			//------------------------ �@�\ID�ǉ��i���͒l�ύX�m�F�j  ----------------------------
//			xmlJW720.AddXmlTag("JW720", "SA590");
//			//�@�e�[�u���^�O�ǉ�
//			xmlJW720.AddXmlTag("SA590", "table");
//			
//			//�z���f�[�^
//			ArrayList aryHaigoData = DataCtrl.getInstance().getTrialTblData().SearchHaigoData(0);
//			for (int i=0; i<aryHaigoData.size(); i++  ) {
//				MixedData mixedData = (MixedData)aryHaigoData.get(i);
//				//���X�|���X�f�[�^�̐ݒ�
//				arySetTag.add(new String[]{"cd_kaisha",checkNull(mixedData.getIntKaishaCd())});		//��ЃR�[�h(����)
//				arySetTag.add(new String[]{"cd_busho",checkNull(mixedData.getIntBushoCd())});		//�����R�[�h(����)
//				arySetTag.add(new String[]{"cd_genryo",checkNull(mixedData.getStrGenryoCd())});		//�����R�[�h(����)
//				arySetTag.add(new String[]{"nm_genryo",checkNull(mixedData.getStrGenryoNm())});		//������(����)
//				arySetTag.add(new String[]{"tanka",checkNull(mixedData.getDciTanka())});			//�P��(����)
//				arySetTag.add(new String[]{"budomari",checkNull(mixedData.getDciBudomari())});		//����(����)
//				arySetTag.add(new String[]{"ritu_abura",checkNull(mixedData.getDciGanyuritu())});	//���ܗL��(����)
//				arySetTag.add(new String[]{"ritu_sakusan",checkNull(mixedData.getDciSakusan())});	//�|�_(����)
//				arySetTag.add(new String[]{"ritu_shokuen",checkNull(mixedData.getDciShokuen())});	//�H��(����)
//				arySetTag.add(new String[]{"ritu_sousan",checkNull(mixedData.getDciSosan())});		//���_(����)
//				xmlJW720.AddXmlTag("table", "rec", arySetTag);
//				arySetTag.clear();
//			}

			//------------------------ �@�\ID�ǉ��i���͌��������j  ----------------------------
			xmlJW720.AddXmlTag("JW720", "SA860");
			//�@�e�[�u���^�O�ǉ�
			xmlJW720.AddXmlTag("SA860", "table");
			
			//�@���R�[�h�ǉ�
			String[] taisho_genryo1 = new String[]{"taisho_genryo1", (genryoCheckbox[0].isSelected()?"true":"") };
			String[] taisho_genryo2 = new String[]{"taisho_genryo2", (genryoCheckbox[1].isSelected()?"true":"") };
			String[] cd_genryo = new String[]{"cd_genryo", checkNull(genryoCdTextbox.getText())};
			String[] nm_genryo = new String[]{"nm_genryo", checkNull(genryoNmTextbox.getText())};
			String[] cd_kaisha;
			if(strKaishaCd.equals("0")){
				cd_kaisha = new String[]{"cd_kaisha",""};
			}else{
				cd_kaisha = new String[]{"cd_kaisha",strKaishaCd};
			}
			String[] cd_busho = new String[]{"cd_busho", strBushoCd};
			String[] num_selectRow = new String[]{"num_selectRow", Integer.toString(page)};

//add start --------------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.4
			// �g�p���у{�^���I���@���@�H��R���{�{�b�N�X���󔒂̏ꍇ�A�g�p���уt���O��"1"��ݒ�
			String strShiyoFlg = "";
			if (this.shiyoFlgBtn[0].isSelected() 
					&& !this.getSelectKojoCd().equals(KOJO_ZENKOJO_TATE)) {
				strShiyoFlg = "1";
			} else {
				strShiyoFlg = "0";
			}
			String[] flg_shiyo = new String[]{"flg_shiyo", strShiyoFlg};
//add end --------------------------------------------------------------------------------------

			arySetTag.add(taisho_genryo1);
			arySetTag.add(taisho_genryo2);
			arySetTag.add(cd_genryo);
			arySetTag.add(nm_genryo);
			arySetTag.add(cd_kaisha);
			arySetTag.add(cd_busho);
			arySetTag.add(num_selectRow);
//add start --------------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.4
			arySetTag.add(flg_shiyo);
//add end --------------------------------------------------------------------------------------
			
			xmlJW720.AddXmlTag("table", "rec", arySetTag);
			arySetTag.clear();
			
			//----------------------------------- XML���M  ----------------------------------
//			System.out.println("\nJW720���MXML===============================================================");
//			xmlJW720.dispXml();
			XmlConnection xcon = new XmlConnection(xmlJW720);
			xcon.setStrAddress(DataCtrl.getInstance().getJnlpConnect().getStrAddressAjax());
			xcon.XmlSend();
			
			//----------------------------------- XML��M  ----------------------------------
			xmlJW720 = xcon.getXdocRes();
//			System.out.println("\nJW720��MXML===============================================================");
//			xmlJW720.dispXml();
			
			//�e�X�gXML�f�[�^
			//xmlJW720 = new XmlData(new File("src/main/JW720.xml"));
			
			//------------------------------- Result�f�[�^�`�F�b�N -----------------------------
			DataCtrl.getInstance().getResultData().setResultData(xmlJW720);
			if ( !DataCtrl.getInstance().getResultData().isReturnFlgCheck() ) {
				
				//��ʏ��N���A
				dispClear();
				
				//�f�[�^���\�����x���N���A
				this.dataLabel.setText("");
				
				throw new ExceptionBase();
				
			}else{
				//--------------------------------- �ύX���i�[ --------------------------------
//				henkouMaterialMst.setMaterialData(xmlJW720, "SA590");
				//henkouMaterialMst.DispMateData();
				
				//------------------------------- �������̓f�[�^�i�[ -----------------------------
				bunsekiMaterialMst.setMaterialData(xmlJW720,"SA860");
				//bunsekiMaterialMst.DispMateData();
				
				//�f�[�^���\���ݒ�
				//���X�g�\�������y�эő匏�����擾
				int intListRowMax = ((MaterialData)bunsekiMaterialMst.getAryMateData().get(0)).getIntListRowMax();
				int intMaxRow = ((MaterialData)bunsekiMaterialMst.getAryMateData().get(0)).getIntMaxRow();
				
				//�ő�l��0�łȂ��ꍇ
				if ( intMaxRow != 0 ) {
					if ( (intMaxRow%intListRowMax != 0) ) {
						intLinkMaxNum = (intMaxRow/intListRowMax)+1;
					} else {
						intLinkMaxNum = (intMaxRow/intListRowMax);
					}
				}
				//�f�[�^���̕\��
				this.dataLabel.setText("<html>�f�[�^���@�F�@" + intMaxRow + " ���ł�(" + intListRowMax + "�����ɕ\�����Ă��܂�)�@�@�@<b>"+selectPage+"�^"+intLinkMaxNum+" �y�[�W<b></html>");
			}
			
			
		}catch(ExceptionBase ex){
			throw ex;
			
		}catch(Exception e){
			//�G���[�ݒ�
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			ex.setStrErrmsg("���앪�̓f�[�^�m�F�p�l���̌��������ʐM���������s���܂���");
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getMessage());
			throw ex;
			
		}finally{
		}
	}
	
	/************************************************************************************
	 * 
	 *   �폜�����p�@XML�ʐM�����iJW730�j
	 *    : �폜����XML�f�[�^�ʐM�iJW730�j���s��
	 *   @author TT nishigawa
	 *   
	 ************************************************************************************/
	private void conJW730() throws ExceptionBase{
		try{
			MaterialData selmt = bunsekiMaterialMst.getSelMate();
			
			//���������擾
			int keta = DataCtrl.getInstance().getTrialTblData().getKaishaGenryo();
			
			//�R�����g�s����
			boolean comFlg = DataCtrl.getInstance().getTrialTblData().commentChk(selmt.getStrGenryocd(), keta);
			
			//�����R�[�h��NULL ���� �R�����g�s�łȂ��ꍇ
			if(selmt != null && !comFlg){
				//----------------------------- ���M�p�����[�^�i�[  -------------------------------
				String strUser = DataCtrl.getInstance().getParamData().getDciUser().toString();
				
				//----------------------------- ���MXML�f�[�^�쐬  ------------------------------
				xmlJW730 = new XmlData();
				ArrayList arySetTag = new ArrayList();
				
				//--------------------------------- Root�ǉ�  ---------------------------------
				xmlJW730.AddXmlTag("","JW730");
				arySetTag.clear();
				
				//--------------------------- �@�\ID�ǉ��iUSEERINFO�j  --------------------------
				xmlJW730.AddXmlTag("JW730", "USERINFO");
				//�@�e�[�u���^�O�ǉ�
				xmlJW730.AddXmlTag("USERINFO", "table");
				//�@���R�[�h�ǉ�
				arySetTag.add(new String[]{"kbn_shori", "3"});
				arySetTag.add(new String[]{"id_user",strUser});
				xmlJW730.AddXmlTag("table", "rec", arySetTag);
				//�z�񏉊���
				arySetTag.clear();
				
				//--------------------------- �@�\ID�ǉ��i�����f�[�^�Ǘ��j  --------------------------
				xmlJW730.AddXmlTag("JW730", "SA370");
				//�@�e�[�u���^�O�ǉ�
				xmlJW730.AddXmlTag("SA370", "table");
				//�@���R�[�h�ǉ�
				arySetTag.add(new String[]{"cd_kaisha", checkNull(selmt.getIntKaishacd())});
				arySetTag.add(new String[]{"cd_genryo",checkNull(selmt.getStrGenryocd())});
				arySetTag.add(new String[]{"flg_haishi",checkNull(selmt.getIntHaisicd())});
				xmlJW730.AddXmlTag("table", "rec", arySetTag);
				//�z�񏉊���
				arySetTag.clear();
				
				//----------------------------------- XML���M  ----------------------------------
//				System.out.println("\nJW730���MXML===============================================================");
//				xmlJW730.dispXml();
				XmlConnection xcon = new XmlConnection(xmlJW730);
				xcon.setStrAddress(DataCtrl.getInstance().getJnlpConnect().getStrAddressAjax());
				xcon.XmlSend();
				
				//----------------------------------- XML��M  ----------------------------------
				xmlJW730 = xcon.getXdocRes();
//				System.out.println("\nJW730��MXML===============================================================");
//				xmlJW730.dispXml();
				
				//------------------------------- Result�f�[�^�`�F�b�N -----------------------------
				DataCtrl.getInstance().getResultData().setResultData(xmlJW730);
				if ( !DataCtrl.getInstance().getResultData().isReturnFlgCheck() ) {
					throw new ExceptionBase();
				}else{
					//�e�[�u�����폜
					analysisCheckTable.getMainTable().tableDeleteRow(selRow);
					bunsekiMaterialMst.getAryMateData().remove(selRow);
					bunsekiMaterialMst.setSelMate(null);
					
					DataCtrl.getInstance().getMessageCtrl().PrintMessageString("����ɍ폜�������������܂����B");
				}
			}
		}catch(ExceptionBase ex){
			throw ex;
			
		}catch(Exception e){
			//�G���[�ݒ�
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			ex.setStrErrmsg("���앪�̓f�[�^�m�F�p�l���̍폜�ʐM���������s���܂���");
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getMessage());
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
						//2011/05/26 QP@10181_No.5 TT T.Satoh Change Start -------------------------
						//String event_name = e.getActionCommand();
						event_name = e.getActionCommand();
						//2011/05/26 QP@10181_No.5 TT T.Satoh Change End ---------------------------
						
						//--------------------- �ڍ� �{�^�� �N���b�N���̏��� -------------------------
						if ( event_name == "shosai_btn_click") {
							if(analysisCheckTable.getMainTable().getSelectedRow() >= 0){
								MaterialData selmt = bunsekiMaterialMst.getSelMate();
								MaterialData md = bunsekiMaterialMst.getSelMate();
								analysisInputSubDisp.getAnalysisInputPanel().init(1,md);
								
//add start --------------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.13
								//�m�F�`�F�b�N�i�m�F����Ă���f�[�^���ۂ��j
								if (analysisInputSubDisp.getAnalysisInputPanel().getKakuninCheckbox().isSelected()) {
									analysisInputSubDisp.getAnalysisInputPanel().setBlnCheckKakunin(true);
								}
								else{
									analysisInputSubDisp.getAnalysisInputPanel().setBlnCheckKakunin(false);
								}
//add end ----------------------------------------------------------------------------------------
								
								analysisInputSubDisp.setVisible(true);
								
							}else{
								DataCtrl.getInstance().getMessageCtrl().PrintMessageString("������I�����ĉ������B");
							}
							
						//------------------ �V�K���� �{�^�� �N���b�N���̏��� ------------------------
						} else if ( event_name == "shinkigenryo_btn_click") {
							analysisInputSubDisp.getAnalysisInputPanel().init(0,null);
							
//add start --------------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.13
							//�m�F�`�F�b�N�i�m�F����Ă���f�[�^���ۂ��j
							if (analysisInputSubDisp.getAnalysisInputPanel().getKakuninCheckbox().isSelected()) {
								analysisInputSubDisp.getAnalysisInputPanel().setBlnCheckKakunin(true);
							}
							else{
								analysisInputSubDisp.getAnalysisInputPanel().setBlnCheckKakunin(false);
							}
//add end ----------------------------------------------------------------------------------------
							
							analysisInputSubDisp.setVisible(true);
							
						//--------------------- ���� �{�^�� �N���b�N���̏��� -------------------------
						} else if(event_name == "kensaku"){
							selectPage = 1;
							
							//���������iJW720�j
							conJW720(selectPage);
							dispTable();
							selectFg = true;

							//�����N������
							for ( int i=0; i<10; i++) {
								linkBtn[i].setText("" + (i+1));
// ADD start 20120706 hisahori
								linkBtn[i].setBounds(linkBtn[i].getLocation().x,linkBtn[i].getLocation().y,60,linkBtn[i].getHeight());
// ADD start 20120706 hisahori
								//�����N�g�p�ۍĐݒ�
								if ( (i+1) <= intLinkMaxNum ) {
									linkBtn[i].setEnabled(true);
									
								} else {
									linkBtn[i].setEnabled(false);
									
								}
								
							}
							
						//---------------------- ��ЃR���{�{�b�N�X�I���� --------------------------
						} else if ( event_name.equals(KAISHA_COMB_SELECT)) {
							selectKaishaComb();
							
//add start --------------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.4
							shiyoFlgBtn[1].setSelected(true);
							shiyoFlgBtn[0].setEnabled(false);
//add end --------------------------------------------------------------------------------------

//add start --------------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.4
						//---------------------- �H��R���{�{�b�N�X�I���� --------------------------
						} else if ( event_name.equals(BUSHO_COMB_SELECT)) {
							selectBushoComb();
//add end --------------------------------------------------------------------------------------
							
						//------------------------- �����N�{�^�������� -----------------------------
						} else if ( event_name.equals(LINK_BTN_CLICK) ) {
							ButtonBase link = (ButtonBase)e.getSource();
							String strPage = link.getText();
							//�f�[�^�}��
							ArrayList aryMateData = bunsekiMaterialMst.getAryMateData();
							MaterialData selMaterialData = (MaterialData)aryMateData.get(0);
							int intRowNum = selMaterialData.getIntListRowMax();
							
							
							//�����{�^������
							if(selectFg){
								if(strPage.equals("�ŏ���")){
									selectPage = 1;
									conJW720(selectPage);
									dispTable();
									
									//�����N������
									for ( int i=0; i<10; i++) {
										linkBtn[i].setText("" + (i+1));
										//�����N�g�p�ۍĐݒ�
										if ( (i+1) <= intLinkMaxNum ) {
											linkBtn[i].setEnabled(true);
											
										} else {
											linkBtn[i].setEnabled(false);
											
										}
										
									}

								}else if(strPage.equals("�Ō��")){
									selectPage = intLinkMaxNum;
									conJW720(selectPage);
									dispTable();
									
									//�ŏI�y�[�W�܂Ői�߂�
									for ( int i=0; i<(selectMaxNum / (intRowNum*10)); i++ ) {
										clickLinkNextBtn(linkNextBtn);
										
									}
									
								}else{
									selectPage = Integer.parseInt(strPage);
									conJW720(selectPage);
									dispTable();
//									intLinkMaxNum = Integer.parseInt(strPage);
								}
							//�����\��
							}else{
								if(strPage.equals("�ŏ���")){
									selectPage = 1;
									conJW710(selectPage);
									dispTable();

									//�����N������
									for ( int i=0; i<10; i++) {
										linkBtn[i].setText("" + (i+1));
										//�����N�g�p�ۍĐݒ�
										if ( (i+1) <= intLinkMaxNum ) {
											linkBtn[i].setEnabled(true);
											
										} else {
											linkBtn[i].setEnabled(false);
											
										}
									}

								}else if(strPage.equals("�Ō��")){
									selectPage = intLinkMaxNum;
									conJW710(selectPage);
									dispTable();

									//�ŏI�y�[�W�܂Ői�߂�
									for ( int i=0; i<(selectMaxNum / (intRowNum*10)); i++ ) {
										clickLinkNextBtn(linkNextBtn);
										
									}
									
								}else{
									selectPage = Integer.parseInt(strPage);
									conJW710(selectPage);
									dispTable();
//									intLinkMaxNum = Integer.parseInt(strPage);
									
								}
								
							}
						
						//----------------------------- �폜���� ---------------------------------
						}else if(event_name.equals("sakuzyo")){
							//�����폜
							if(analysisCheckTable.getMainTable().getSelectedRow() >= 0){
								
								//�_�C�A���O�R���|�[�l���g�ݒ�
								JOptionPane jp = new JOptionPane();
								
								//�m�F�_�C�A���O�\��
								int option = jp.showConfirmDialog(
										jp.getRootPane(),
										"�����̍폜���s���܂��B��낵���ł����H"
										, "�m�F���b�Z�[�W"
										,JOptionPane.YES_NO_OPTION
										,JOptionPane.PLAIN_MESSAGE
									);
								
								//�u�͂��v����
							    if (option == JOptionPane.YES_OPTION){
							    	
							    	//�폜�������s
							    	conJW730();
									
									//��������
									int row = analysisCheckTable.getMainTable().getSelectedRow();
// ADD start 20120706 hisahori
									int rowcount = analysisCheckTable.getMainTable().getRowCount();
									if(intLinkMaxNum <= 1 && rowcount <= 0){
										//�s�J�E���g���O���Ȃ�Č������Ȃ�
									}else{
										 if(intLinkMaxNum >= 1 && rowcount <= 0){
												selectPage = selectPage - 1;
										 }
// ADD end 20120706 hisahori
										//�iJW720�j
										if(selectFg){
											conJW720(selectPage);
											dispTable();
											if(row > -1){
												analysisCheckTable.getMainTable().setRowSelectionInterval(row, row);
												MaterialData mt = (MaterialData)(bunsekiMaterialMst.getAryMateData().get(row));
												bunsekiMaterialMst.setSelMate(mt);
											}
											
										//�iJW710�j
										}else{
											conJW710(selectPage);
											dispTable();
											if(row > -1){
												analysisCheckTable.getMainTable().setRowSelectionInterval(row, row);
												MaterialData mt = (MaterialData)(bunsekiMaterialMst.getAryMateData().get(row));
												bunsekiMaterialMst.setSelMate(mt);
											}
										}
// ADD start 20120706 hisahori
									}
// ADD end 20120706 hisahori							    	
							    //�u�������v����
							    }else if (option == JOptionPane.NO_OPTION){
							    	
							    	//�������Ȃ�
							    	
							    }
								
							}else{
								DataCtrl.getInstance().getMessageCtrl().PrintMessageString("������I�����ĉ������B");
							}
							
						//TODO
//						//----------------------------- �h�{�v�Z���o�͏��� ---------------------------------
//						}else if(event_name.equals("outputExcel")){
//							if ( DataCtrl.getInstance().getManufacturingSubDisp().getManufacturingPanel().getShisakuSeq() != 0 ) {
//								//�h�{�v�Z���o�͏���
//								conJW740();	
//								
//							} else {
//								//���b�Z�[�W�\��
//								DataCtrl.getInstance().getMessageCtrl().PrintMessageString("����񂪑I������Ă��܂���B");
//								
//							}
//							
						//------------------------- �o�^ �{�^�� �N���b�N���̏��� --------------------------
						}
						else if ( event_name == "toroku") {
							analysisInputSubDisp.getAnalysisInputPanel().toroku_bunseki();
							
							
							
							//��������
							int row = analysisCheckTable.getMainTable().getSelectedRow();
							//�����{�^������������Ă���ꍇ�iJW720�j
							if(selectFg){
								conJW720(selectPage);
								dispTable();
								if(row > -1){
									analysisCheckTable.getMainTable().setRowSelectionInterval(row, row);
									MaterialData mt = (MaterialData)(bunsekiMaterialMst.getAryMateData().get(row));
									bunsekiMaterialMst.setSelMate(mt);
								}
								
							//�����\�������̏ꍇ�iJW710�j
							}else{
								conJW710(selectPage);
								dispTable();
								if(row > -1){
									analysisCheckTable.getMainTable().setRowSelectionInterval(row, row);
									MaterialData mt = (MaterialData)(bunsekiMaterialMst.getAryMateData().get(row));
									bunsekiMaterialMst.setSelMate(mt);
								}
							}
						}
						//------------------------- <<�i�O�ցj�����N�{�^�������� -----------------------------
						else if ( event_name.equals(LINK_PREV_BTN_CLICK) ) {
							clickLinkPrevBtn(e.getSource());
							
						}
						//------------------------- >>�i���ցj�����N�{�^�������� -----------------------------
						else if ( event_name.equals(LINK_NEXT_BTN_CLICK) ) {
							clickLinkNextBtn(e.getSource());
							
						}
						
						
					}catch(ExceptionBase eb){
						DataCtrl.getInstance().PrintMessage(eb);
						
					}catch(Exception ec){
						//�G���[�ݒ�
						ex = new ExceptionBase();
						ex.setStrErrCd("");
						ex.setStrErrmsg("���앪�̓f�[�^�m�F�p�l����ActionListener�C�x���g�����s���܂���");
						ex.setStrErrShori(this.getClass().getName());
						ex.setStrMsgNo("");
						ex.setStrSystemMsg(ec.getMessage());
						DataCtrl.getInstance().PrintMessage(ex);
						
					}finally{
						
					}
				}

			}
		);
	}
	
	/************************************************************************************
	 * 
	 * ��ЃR���{�{�b�N�X�ݒ菈��
	 * @throws ExceptionBase 
	 * 
	 ************************************************************************************/
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
		} catch (Exception e) {
			//�G���[�ݒ�
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			ex.setStrErrmsg("���앪�̓f�[�^�m�F�p�l���̉�ЃR���{�{�b�N�X�ݒ菈�������s���܂���");
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getMessage());
			throw ex;
			
		} finally {
		}
		
	}

	/************************************************************************************
	 * 
	 * �����R���{�{�b�N�X�ݒ菈��
	 * @throws ExceptionBase 
	 * 
	 ************************************************************************************/
	private void setKojoCmb() throws ExceptionBase {
		int i;
		try {
			String bushoNm = "";
			
			//�R���{�{�b�N�X�̑S���ڂ̍폜
			this.bushoComb.removeAllItems();
			
			//�\�����ɉ����ăR���{�{�b�N�X�ɒl��ǉ�
//mod start --------------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.6
//			this.bushoComb.addItem("");
			this.bushoComb.addItem(KOJO_ZENKOJO_TATE);
//mod end --------------------------------------------------------------------------------------
			for ( i=0; i<BushoData.getArtBushoCd().size(); i++ ) {
				//��Ж�
				bushoNm = BushoData.getAryBushoNm().get(i).toString(); 
				
				//�R���{�{�b�N�X�֒ǉ�
				this.bushoComb.addItem(bushoNm);
			}
		} catch (Exception e) {
			//�G���[�ݒ�
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			ex.setStrErrmsg("���앪�̓f�[�^�m�F�p�l���̕����R���{�{�b�N�X�ݒ菈�������s���܂���");
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getMessage());
			throw ex;
			
		} finally {
		}
		
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
			ex.setStrErrmsg("���앪�̓f�[�^�m�F�p�l���̑I����ЃR�[�h�擾���������s���܂���");
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
	 * �yJW610�z ��ЃR���{�{�b�N�X�l�擾 ���MXML�f�[�^�쐬
	 * 
	 ************************************************************************************/
	private void conJW610() throws ExceptionBase{
		try{
			
			//�@���M�p�����[�^�i�[
			String strUser = DataCtrl.getInstance().getParamData().getDciUser().toString();
			String strGamenId = "160";
			
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
			
//			xmlJW610.dispXml();
			//�@XML���M
			XmlConnection xmlConnection = new XmlConnection(xmlJW610);
			xmlConnection.setStrAddress(DataCtrl.getInstance().getJnlpConnect().getStrAddressAjax());
			xmlConnection.XmlSend();
			//�@XML��M
			xmlJW610 = xmlConnection.getXdocRes();
//			xmlJW610.dispXml();
			
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
			ex.setStrErrmsg("���앪�̓f�[�^�m�F�p�l���̉�ЃR���{�{�b�N�X�l�擾�ʐM���������s���܂���");
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getMessage());
			throw ex;
			
		}finally{
		}
	}
	
	/************************************************************************************
	 * 
	 * ��ЃR���{�{�b�N�X�I��������
	 * @throws ExceptionBase 
	 * 
	 ************************************************************************************/
	private void selectKaishaComb() throws ExceptionBase {
		try {
			if ( this.isSelectKaishaCmb ) {		//���d������h��
				
				this.conJW620(this.getSelectKaishaCd());
				
				this.setKojoCmb();
			}
		} catch (ExceptionBase e) {
			throw e;
			
		} catch (Exception e) {
			//�G���[�ݒ�
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			ex.setStrErrmsg("���앪�̓f�[�^�m�F�p�l���̉�ЃR���{�{�b�N�X�I�������������s���܂���");
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getMessage());
			throw ex;
			
		} finally {
		}	
	}

//add start --------------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.4
	/************************************************************************************
	 * 
	 * �H��R���{�{�b�N�X�I��������
	 * @throws ExceptionBase 
	 * 
	 ************************************************************************************/
	private void selectBushoComb() throws ExceptionBase {
		try {
			if ( this.bushoComb.isValid() ) {		//���d������h��
				//�󔒂̏ꍇ�́A�S���݂̂Ƃ���
				if ( this.bushoComb.getSelectedItem().equals(this.KOJO_ZENKOJO_TATE) 
						|| bushoComb.getSelectedItem().equals("�V�K�o�^����") ) {
					this.shiyoFlgBtn[1].setSelected(true);
					this.shiyoFlgBtn[0].setEnabled(false);
				}
				else {
					this.shiyoFlgBtn[0].setEnabled(true);
					this.shiyoFlgBtn[0].setSelected(true);
				}
			}
			
		} catch (Exception e) {
			//�G���[�ݒ�
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			ex.setStrErrmsg("���앪�̓f�[�^�m�F�p�l���̍H��R���{�{�b�N�X�I�������������s���܂���");
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getMessage());
			throw ex;
			
		} finally {
		}	
	}
//add end --------------------------------------------------------------------------------------
	
	/************************************************************************************
	 * 
	 * �I���H��R�[�h�̎擾
	 * @return �I���H��R�[�h 
	 * @throws ExceptionBase 
	 * 
	 ************************************************************************************/
	private String getSelectKojoCd() throws ExceptionBase {
		int i;
		String strRetKojoCd = "";
		String bushoCd = "";
		String bushoNm = "";
		try {
			//�H��R���{�{�b�N�X�̑I��l���擾
			String kojoCombItem = this.bushoComb.getSelectedItem().toString();
			
			//�H�ꂪ�S�H��ł͂Ȃ��ꍇ�A�I������Ă��镔�����ɂЕ����R�[�h���擾
			for ( i=0; i<BushoData.getArtBushoCd().size(); i++ ) {
				//��ЃR�[�h
				bushoCd = BushoData.getArtBushoCd().get(i).toString();
				//��Ж�
				bushoNm = BushoData.getAryBushoNm().get(i).toString(); 
		
				//�I����ЃR�[�h�̌��o
				if ( bushoNm.equals(kojoCombItem) ) {
					strRetKojoCd = bushoCd;
				}
			}
		} catch (Exception e) {
			//�G���[�ݒ�
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			ex.setStrErrmsg("���앪�̓f�[�^�m�F�p�l���̑I���H��R�[�h�擾���������s���܂���");
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getMessage());
			throw ex;
			
		} finally {
		}
		return strRetKojoCd;
	}
		
	/************************************************************************************
	 * 
	 * �yJW620�z ��ЃR���{�{�b�N�X������ ���MXML�f�[�^�쐬
	 * @param strKaishaCd : ��ЃR�[�h
	 * 
	 ************************************************************************************/
	private void conJW620(String strKaishaCd) throws ExceptionBase{
		try{
			
			//�@���M�p�����[�^�i�[
			String strUser = DataCtrl.getInstance().getParamData().getDciUser().toString();
			String strGamenId = "160";
			
			//�@���MXML�f�[�^�쐬
			xmlJW620 = new XmlData();
			ArrayList arySetTag = new ArrayList();
			
			//�@Root�ǉ�
			xmlJW620.AddXmlTag("","JW620");
			arySetTag.clear();
			
			//�@�@�\ID�ǉ�
			xmlJW620.AddXmlTag("JW620", "USERINFO");
			//�@�e�[�u���^�O�ǉ�
			xmlJW620.AddXmlTag("USERINFO", "table");	
			//�@���R�[�h�ǉ�
			String[] kbn_shori = {"kbn_shori", "3"};
			String[] id_user = {"id_user",strUser};
			arySetTag.add(kbn_shori);
			arySetTag.add(id_user);
			xmlJW620.AddXmlTag("table", "rec", arySetTag);
			arySetTag.clear();

			//�@�y���������z �@�\ID�ǉ�
			xmlJW620.AddXmlTag("JW620", "SA290");

			//�@�e�[�u���^�O�ǉ�
			xmlJW620.AddXmlTag("SA290", "table");
			//�@���R�[�h�ǉ�
			String[] cd_kaisha = new String[]{"cd_kaisha",strKaishaCd};
			id_user = new String[]{"id_user",strUser};
			String[] id_gamen = new String[]{"id_gamen", strGamenId};
			arySetTag.add(cd_kaisha);
			arySetTag.add(id_user);
			arySetTag.add(id_gamen);
			xmlJW620.AddXmlTag("table", "rec", arySetTag);
			arySetTag.clear();
			
//			xmlJW620.dispXml();
			//�@XML���M
			XmlConnection xmlConnection = new XmlConnection(xmlJW620);
			xmlConnection.setStrAddress(DataCtrl.getInstance().getJnlpConnect().getStrAddressAjax());
			xmlConnection.XmlSend();
			//�@XML��M
			xmlJW620 = xmlConnection.getXdocRes();
//			xmlJW620.dispXml();
			
			//�����f�[�^
			BushoData.setBushoData(xmlJW620);

			// Result�f�[�^
			DataCtrl.getInstance().getResultData().setResultData(xmlJW620);
			if ( !DataCtrl.getInstance().getResultData().isReturnFlgCheck() ) {
				throw new ExceptionBase();
				
			}
			
		}catch(ExceptionBase e){
			throw e;
			
		}catch(Exception e){
			//�G���[�ݒ�
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			ex.setStrErrmsg("���앪�̓f�[�^�m�F�p�l���̉�ЃR���{�{�b�N�X�����ʐM���������s���܂���");
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getMessage());
			throw ex;
			
		}finally{
		}
	}

	/************************************************************************************
	 * 
	 * �yJW740�z �h�{�v�Z���o�� ���MXML�f�[�^�쐬
	 * @throws ExceptionBase 
	 * 
	 ************************************************************************************/
	private void conJW740() throws ExceptionBase {
		
		try {
			
			//------------------------------ ���M�p�����[�^�i�[  ------------------------
			String strUser = DataCtrl.getInstance().getParamData().getDciUser().toString();
			String strShisakuSeq = Integer.toString(DataCtrl.getInstance().getManufacturingSubDisp().getManufacturingPanel().getShisakuSeq());

			//------------------------------ ���MXML�f�[�^�쐬  ------------------------
			xmlJW740 = new XmlData();
			ArrayList arySetTag = new ArrayList();

			//------------------------------ Root�ǉ�  ---------------------------------
			xmlJW740.AddXmlTag("","JW740");
			arySetTag.clear();

			//------------------------------ �@�\ID�ǉ��iUSEERINFO�j  -------------------
			xmlJW740.AddXmlTag("JW740", "USERINFO");
			//�@�e�[�u���^�O�ǉ�
			xmlJW740.AddXmlTag("USERINFO", "table");
			//�@���R�[�h�ǉ�
			arySetTag.add(new String[]{"kbn_shori", "3"});
			arySetTag.add(new String[]{"id_user",strUser});
			xmlJW740.AddXmlTag("table", "rec", arySetTag);
			//�z�񏉊���
			arySetTag.clear();

			//------------------------------ �@�\ID�ǉ��iSA430�j  ------------------------
			xmlJW740.AddXmlTag("JW740", "SA430");
			//�@�e�[�u���^�O�ǉ�
			xmlJW740.AddXmlTag("SA430", "table");
			//�@���R�[�h�ǉ�
			arySetTag.add(new String[]{"cd_shain", DataCtrl.getInstance().getParamData().getStrSisaku_user()});
			arySetTag.add(new String[]{"nen", DataCtrl.getInstance().getParamData().getStrSisaku_nen()});
			arySetTag.add(new String[]{"no_oi", DataCtrl.getInstance().getParamData().getStrSisaku_oi()});	
			arySetTag.add(new String[]{"seq_shisaku", strShisakuSeq});	
			xmlJW740.AddXmlTag("table", "rec", arySetTag);
			//�z�񏉊���
			arySetTag.clear();

			//------------------------------ �@�\ID�ǉ��iSA440�j  ------------------------
			xmlJW740.AddXmlTag("JW740", "SA440");
			//�@�e�[�u���^�O�ǉ�
			xmlJW740.AddXmlTag("SA440", "table");
			//�@���R�[�h�ǉ� : ����e�[�u���̃f�[�^��ݒ�
			arySetTag.add(new String[]{"cd_shain", DataCtrl.getInstance().getParamData().getStrSisaku_user()});
			arySetTag.add(new String[]{"nen", DataCtrl.getInstance().getParamData().getStrSisaku_nen()});
			arySetTag.add(new String[]{"no_oi", DataCtrl.getInstance().getParamData().getStrSisaku_oi()});	
			arySetTag.add(new String[]{"seq_shisaku", strShisakuSeq});	
			xmlJW740.AddXmlTag("table", "rec", arySetTag);
			//�z�񏉊���
			arySetTag.clear();

			//----------------------------------- XML���M  ----------------------------------
//			System.out.println("JW740���MXML===============================================================");
//			xmlJW740.dispXml();
			XmlConnection xmlConnection = new XmlConnection(xmlJW740);
			xmlConnection.setStrAddress(DataCtrl.getInstance().getJnlpConnect().getStrAddressAjax());
			xmlConnection.XmlSend();
			
			//----------------------------------- XML��M  ----------------------------------
			xmlJW740 = xmlConnection.getXdocRes();
//			System.out.println();
//			System.out.println("JW740��MXML===============================================================");
//			xmlJW740.dispXml();

			//---------------------------- Result�f�[�^�ݒ�(RESULT)  -----------------------------
			DataCtrl.getInstance().getResultData().setResultData(xmlJW740);
			
			// Result�f�[�^.�������ʂ�true�̏ꍇ�AExceptionBase��Throw����
			if ( !DataCtrl.getInstance().getResultData().isReturnFlgCheck() ) {	
				throw new ExceptionBase();
				
			}

			//------------------------------ �f�[�^�ݒ�(SA430/SA440) --------------------------------
			
			//�_�E�����[�h�p�X�N���X�擾(SA430)
			DownloadPathData downloadPathData1 = new DownloadPathData();
			downloadPathData1.setDownloadPathData(xmlJW740, "SA430");

			//�_�E�����[�h�p�X�N���X�擾(SA440)
			DownloadPathData downloadPathData2 = new DownloadPathData();
			downloadPathData2.setDownloadPathData(xmlJW740, "SA440");
			
			//URL�R�l�N�V�����N���X
			UrlConnection urlConnection = new UrlConnection();
			
			//�_�E�����[�h�p�X�𑗂�A�t�@�C���_�E�����[�h��ʂŊJ��
			urlConnection.urlFileDownLoad( downloadPathData1.getStrDownloadPath() + ":::" + downloadPathData2.getStrDownloadPath());
						
		} catch (ExceptionBase e) {
			throw e;
			
		} catch (Exception e) {
			//�G���[�ݒ�
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			ex.setStrErrmsg("���앪�� �h�{�v�Z���o�͏��������s���܂���");
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getStackTrace()[0].toString());
			throw ex;
			
		} finally {
			
		}
		
	}
	
	/************************************************************************************
	 * 
	 * NULL�`�F�b�N�����i�I�u�W�F�N�g�j
	 * @throws ExceptionBase
	 * 
	 ************************************************************************************/
	private String checkNull(Object val){
		String ret = "NULL";
		if(val != null){
			ret = val.toString();
		}
		return ret;
	}
	
	/************************************************************************************
	 * 
	 * NULL�`�F�b�N�����i���l�j
	 * @throws ExceptionBase
	 * 
	 ************************************************************************************/
	private String checkNull(int val){
		String ret = "NULL";
		if(val >= 0){
			ret = Integer.toString(val);
		}
		return ret;
	}
	
	/************************************************************************************
	 * 
	 * ��ʏ���������
	 * 
	 ************************************************************************************/
	public void dispClear() throws ExceptionBase{
		try{
			//�e�[�u���N���A����
			if ( analysisCheckTable.getMainTable().getRowCount() > 0 ) {
				for ( int i=analysisCheckTable.getMainTable().getRowCount()-1; i>-1; i-- ) {
					analysisCheckTable.getMainTable().tableDeleteRow(i);
				}
			}
			//�����N�{�^��
			for(int i=0; i<linkBtn.length-2; i++){
				linkBtn[i].setEnabled(false);
			}
			
		}catch(Exception e){
			//�G���[�ݒ�
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			ex.setStrErrmsg("���앪�̓f�[�^�m�F�p�l���̉�ʏ��������������s���܂���");
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getMessage());
			throw ex;
			
		}finally{
			
		}
	}
	
	/************************************************************************************
	 * 
	 * �{�^���Q�b�^�[
	 * 
	 ************************************************************************************/
	public ButtonBase[] getButton() {
		return button;
	}

	/************************************************************************************
	 * 
	 * �u���ցv�����N�{�^������������
	 * @param source
	 * @throws ExceptionBase
	 *  
	 ************************************************************************************/
	private void clickLinkNextBtn(Object source) throws ExceptionBase {

		try {
			
			//�����N�ő�\���l���擾
			int chkValue = Integer.parseInt(this.linkBtn[9].getText());
			
			//�����N�ő�\���l���������ʍő�l�𒴂��Ă��Ȃ���
			if ( chkValue < this.intLinkMaxNum ) {

				//�����N�ŏ��\���l���擾
				int intValue = Integer.parseInt(this.linkBtn[0].getText());
				
				//�����N�\���l�𑝂₷
				for ( int i=0; i<10; i++ ) {
					
					//�����N�\���l�Đݒ�
					this.linkBtn[i].setText(Integer.toString(intValue + 10 + i));
					
					//�����N�g�p�ۍĐݒ�
					if ( (intValue + 10 + i) <= intLinkMaxNum ) {
						this.linkBtn[i].setEnabled(true);
						
					} else {
						this.linkBtn[i].setEnabled(false);
						
					}
					
				}
				
			}
			
		} catch (Exception e) {
			//�G���[�ݒ�
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			ex.setStrErrmsg("�����ꗗ�p�l����<<�����N�{�^�����������������s���܂���");
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getMessage());
			throw ex;
			
		} finally {
			
		}
		
	}

	/************************************************************************************
	 * 
	 * �u�O�ցv�����N�{�^������������
	 * @param source
	 * @throws ExceptionBase
	 *  
	 ************************************************************************************/
	private void clickLinkPrevBtn(Object source) throws ExceptionBase {

		try {

			//�����N�ŏ��\���l
			int chkValue = Integer.parseInt(this.linkBtn[0].getText());
			
			//�����N�ŏ��\���l�������l�ł͂Ȃ��ꍇ
			if ( chkValue != 1 ) {				
				
				//�����N�\���l���P�O�O�ɖ߂�
				for ( int i=0; i<10; i++ ) {
					
					//�����N�\���l�Đݒ�
					this.linkBtn[i].setText(Integer.toString(chkValue - 10 + i));
					
					//�����N�g�p�ۍĐݒ�
					if ( (chkValue - 10 + i) <= intLinkMaxNum ) {
						this.linkBtn[i].setEnabled(true);
						
					} else {
						this.linkBtn[i].setEnabled(false);
						
					}
					
				}
				
			}
			
		} catch (Exception e) {
			//�G���[�ݒ�
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			ex.setStrErrmsg("�����ꗗ�p�l����>>�����N�{�^�����������������s���܂���");
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getMessage());
			throw ex;
			
		} finally {
			
		}
		
	}
}