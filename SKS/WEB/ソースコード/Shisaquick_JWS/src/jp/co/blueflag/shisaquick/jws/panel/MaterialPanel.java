package jp.co.blueflag.shisaquick.jws.panel;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableColumn;
import javax.swing.text.Document;

import jp.co.blueflag.shisaquick.jws.base.BushoData;
import jp.co.blueflag.shisaquick.jws.base.KaishaData;
import jp.co.blueflag.shisaquick.jws.base.MaterialData;
import jp.co.blueflag.shisaquick.jws.base.XmlData;
import jp.co.blueflag.shisaquick.jws.button.LinkButton;
import jp.co.blueflag.shisaquick.jws.cellrenderer.MiddleCellRenderer;
import jp.co.blueflag.shisaquick.jws.common.ButtonBase;
import jp.co.blueflag.shisaquick.jws.common.ComboBase;
import jp.co.blueflag.shisaquick.jws.common.ExceptionBase;
import jp.co.blueflag.shisaquick.jws.common.JwsConstManager;
import jp.co.blueflag.shisaquick.jws.common.PanelBase;
import jp.co.blueflag.shisaquick.jws.common.TextboxBase;
import jp.co.blueflag.shisaquick.jws.data.DataCtrl;
import jp.co.blueflag.shisaquick.jws.label.DispTitleLabel;
import jp.co.blueflag.shisaquick.jws.label.HeaderLabel;
import jp.co.blueflag.shisaquick.jws.label.ItemLabel;
import jp.co.blueflag.shisaquick.jws.label.LevelLabel;
import jp.co.blueflag.shisaquick.jws.manager.XmlConnection;
import jp.co.blueflag.shisaquick.jws.table.MaterialTable;
import jp.co.blueflag.shisaquick.jws.textbox.HankakuTextbox;

/**
 * 
 * �yA05-05�z �����ꗗ�p�l������p�̃N���X
 * 
 * @author k-katayama
 * @since 2009/04/03
 */
public class MaterialPanel extends PanelBase {
	private static final long serialVersionUID = 1L;

	private KaishaData KaishaData = new KaishaData();
	private BushoData BushoData = new BushoData();
	
	private DispTitleLabel dispTitleLabel;			//��ʃ^�C�g�����x��
	private HeaderLabel headerLabel;				//�w�b�_�\�����x��
	private LevelLabel levelLabel;						//���x���\�����x��
	private ItemLabel[] itemLabel;						//���ڃ��x��[0:�R�[�h, 1:���, 2:���O, 3:�H��]
	private HankakuTextbox codeTextbox;			//�R�[�h�e�L�X�g�{�b�N�X
	private TextboxBase nameTextbox;				//���O�e�L�X�g�{�b�N�X
	private ComboBase kaishaComb;					//��ЃR���{�{�b�N�X
	private ComboBase kojoComb;					//�H��R���{�{�b�N�X
	private ButtonBase searchBtn;					//�����{�^��
	private ButtonBase endBtn;						//�I���{�^��
	private MaterialTable materialTable;				//�����ꗗ�e�[�u��
	private LinkButton[] linkBtn;						//�����N�{�^��
	private ItemLabel dataLabel;						//�f�[�^���\�����x��
	private LinkButton linkPrevBtn;					//�O���ڃ����N�{�^��
	private LinkButton linkNextBtn;					//�����ڃ����N�{�^��
//add start --------------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.4
	private JRadioButton[] radioButton;					//���W�I�{�^��
	private ButtonGroup copyCheck = new ButtonGroup();
//add end --------------------------------------------------------------------------------------

	private XmlData xmlJW610;						//�w�l�k�f�[�^�ێ�(JW610)
	private XmlData xmlJW620;						//�w�l�k�f�[�^�ێ�(JW620)
	private XmlData xmlJW630;						//�w�l�k�f�[�^�ێ�(JW630)
	private ExceptionBase ex;							//�G���[����
	
	//�����{�^���������̃C�x���g��
	private final String SEARCH_BTN_CLICK = "searchBtnClick";
	//��ЃR���{�{�b�N�X�I�����̃C�x���g��
	private final String KAISHA_COMB_SELECT = "kaishaCombSelect";
	//�����N�{�^���������̃C�x���g��
	private final String LINK_BTN_CLICK = "linkBtnClick";
	//�u�O�ցv�����N�{�^���������̃C�x���g��
	private final String LINK_PREV_BTN_CLICK = "linkPrevBtnClick";
	//�u���ցv�����N�{�^���������̃C�x���g��
	private final String LINK_NEXT_BTN_CLICK = "linkNextBtnClick";
//add start --------------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.4
	//�H��R���{�{�b�N�X�I�����̃C�x���g��
	private final String KOUJYOU_COMB_SELECT = "KoujyouCombSelect";
//add end --------------------------------------------------------------------------------------
	
	private boolean isSelectKaishaCmb = false;	//��ЃR���{�{�b�N�X�I�������t���O
	private int intLinkMaxNum = 0;					//�����N�ő吔

//add start --------------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.6
	private final String KOJO_ZENKOJO = "zenkojo";			//�S�H��̏ꍇ
	private final String KOJO_ZENKOJO_YOKO = "�S�H��_��";		//�S�H��̏ꍇ�̕���
	private final String KOJO_ZENKOJO_TATE = "�S�H��_�c";		//�󔒂̏ꍇ�̕���
//add end --------------------------------------------------------------------------------------
	
	
	/**
	 * �R���X�g���N�^
	 * @param strOutput : ��ʃ^�C�g��
	 * @throws ExceptionBase
	 */
	public MaterialPanel(String strOutput) throws ExceptionBase {
		//�X�[�p�[�N���X�̃R���X�g���N�^���Ăяo��
		super();

		try {
			//�P�D�p�l���̐ݒ�
			this.setPanel();
			
			//�Q�D�R���g���[���̔z�u
			
			//�R���g���[���z�u
			this.addControl(strOutput);	

			//�R�D�������������s
			this.init();
			
		} catch (ExceptionBase e) {
			throw e;
			
		} catch(Exception e) {
			//�G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("�����ꗗ�p�l���R���X�g���N�^�����s���܂����B");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.toString());
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
	 * �R���g���[���z�u
	 * @param strTitle : ��ʃ^�C�g��
	 * @throws ExceptionBase 
	 */
	private void addControl(String strTitle) throws ExceptionBase {
		int i;
		int x,y, width,height;
		
		int defLabelWidth = 60;
		int defTextWidth = 200;
		int defHeight = 18;
		int dispWidth = 900;
//add start --------------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.4
		int defRadioWidth = 60;
//add end --------------------------------------------------------------------------------------

		try {
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
			
			///
			/// ���ڃ��x���̐ݒ�[0:�R�[�h, 1:���O, 2:���, 3:�H��]
			///
//mod start --------------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.4
			//this.itemLabel = new ItemLabel[4];
			this.itemLabel = new ItemLabel[6];
			//0 : �R�[�h
			x = 5;
			y = 30;
			this.itemLabel[0] = new ItemLabel();
			this.itemLabel[0].setText("�R�[�h");
			this.itemLabel[0].setBounds(x,y,defLabelWidth,defHeight);
			//1 : ���O
			x = this.itemLabel[0].getX();
			y = this.itemLabel[0].getY() + defHeight + 5;
			this.itemLabel[1] = new ItemLabel();
			this.itemLabel[1].setText("���O");
			this.itemLabel[1].setBounds(x,y,defLabelWidth,defHeight);
			//2 : ���
			x = this.itemLabel[0].getX() + defLabelWidth + defTextWidth + 5;
			y = this.itemLabel[0].getY();
			this.itemLabel[2] = new ItemLabel();
			this.itemLabel[2].setText("���");
			this.itemLabel[2].setBounds(x,y,defLabelWidth,defHeight);
			//3 : �H��
			x = this.itemLabel[2].getX();
			y = this.itemLabel[2].getY() + defHeight + 5;
			this.itemLabel[3] = new ItemLabel();
			this.itemLabel[3].setText("�H��");
			this.itemLabel[3].setBounds(x,y,defLabelWidth,defHeight);
			//4 : �g�p����(���x���l�̓T�[�u���b�g��Const_Setting.xml���擾)
			x = this.itemLabel[2].getX() + defLabelWidth + defTextWidth + 5;
			y = this.itemLabel[2].getY();
			this.itemLabel[4] = new ItemLabel();
			this.itemLabel[4].setText(JwsConstManager.JWS_NM_SHIYO);
			this.itemLabel[4].setBounds(x,y,defLabelWidth,defHeight);
			//5 : �S��
			x = this.itemLabel[4].getX();
			y = this.itemLabel[4].getY() + defHeight + 5;
			this.itemLabel[5] = new ItemLabel();
			this.itemLabel[5].setText("�S��");
			this.itemLabel[5].setBounds(x,y,defLabelWidth,defHeight);
			
			//���ڃ��x�����p�l���ɒǉ�
			for ( i=0; i<this.itemLabel.length; i++ ) {
				this.add(this.itemLabel[i]);
			}
			
			///
			/// �R�[�h�e�L�X�g�{�b�N�X(���p)�̐ݒ�
			///
			x = this.itemLabel[0].getX() + defLabelWidth;
			y = this.itemLabel[0].getY();
			this.codeTextbox = new HankakuTextbox();
			this.codeTextbox.setBounds(x,y,defTextWidth,defHeight);
			this.add(this.codeTextbox);
			
			///
			/// ���O�e�L�X�g�{�b�N�X�̐ݒ�
			///
			x = this.itemLabel[1].getX() + defLabelWidth;
			y = this.itemLabel[1].getY();
			this.nameTextbox = new TextboxBase();
			this.nameTextbox.setBounds(x,y,defTextWidth,defHeight);
			this.add(this.nameTextbox);
			
			///
			/// ��ЃR���{�{�b�N�X�̐ݒ�
			///
			x = this.itemLabel[2].getX() + defLabelWidth;
			y = this.itemLabel[2].getY();
			this.kaishaComb = new ComboBase();
			this.kaishaComb.setBounds(x,y,defTextWidth,defHeight);
			this.add(this.kaishaComb);
			
			///
			/// �H��R���{�{�b�N�X�̐ݒ�
			///
			x = this.itemLabel[3].getX() + defLabelWidth;
			y = this.itemLabel[3].getY();
			this.kojoComb = new ComboBase();
			this.kojoComb.setBounds(x,y,defTextWidth,defHeight);
			this.add(this.kojoComb);

			///
			/// ���W�I�{�^���̐ݒ�[0:3����, 1:�S��]
			///
			this.radioButton = new JRadioButton[2];
			x = this.itemLabel[4].getX() + defLabelWidth;
			y = this.itemLabel[4].getY();
			width = defRadioWidth;
			height = defHeight;
			//3����
			this.radioButton[0] = new JRadioButton();
			this.radioButton[0].setBounds(x,y,width,height);
			this.radioButton[0].addActionListener(this.getActionEvent());
			this.radioButton[0].setActionCommand("sanKagetu");
			this.radioButton[0].setSelected(true);
			copyCheck.add(this.radioButton[0]);
			//�S��
			x = this.itemLabel[5].getX() + defLabelWidth;
			y = this.itemLabel[5].getY();
			this.radioButton[1] = new JRadioButton();
			this.radioButton[1].setBounds(x,y,width,height);
			this.radioButton[1].addActionListener(this.getActionEvent());
			this.radioButton[1].setActionCommand("zenKen");
			//this.radioButton[1].setSelected(true);
			copyCheck.add(this.radioButton[1]);
			//�p�l���ɒǉ�
			for ( i=0; i<this.radioButton.length; i++ ) {
				this.radioButton[i].setBackground(Color.WHITE);
				this.add(this.radioButton[i]);
			}
//mod end --------------------------------------------------------------------------------------

			///
			/// �����{�^���̐ݒ�
			///
			width = 80;
			height = 38;
//2010.09.09 sakai change --->
			//x = this.kaishaComb.getX() + defTextWidth + 5;
			//y = this.kaishaComb.getY();
			x = this.radioButton[0].getX() + defRadioWidth + 5;
			y = this.radioButton[0].getY();
//2010.09.09 sakai change <---
			this.searchBtn = new ButtonBase();
			this.searchBtn.setFont(new Font("Default", Font.PLAIN, 11));
			this.searchBtn.setBounds(x, y, width, height+2);
			this.searchBtn.setText("����");
			this.add(this.searchBtn);
			
			///
			/// �I���{�^���̐ݒ�
			///
			x = x + width + 5;
			this.endBtn = new ButtonBase();
			this.endBtn.setFont(new Font("Default", Font.PLAIN, 11));
			this.endBtn.setBounds(x, y, width, height+2);
			this.endBtn.setText("�I��");
			this.add(this.endBtn);
	
			///
			/// �e�[�u���z�u
			///
			this.addTable();
			
			///
			/// �f�[�^���\��
			///
			this.dataLabel = new ItemLabel();
			this.dataLabel.setBackground(Color.WHITE);
			this.dataLabel.setFont(new Font("Default", Font.PLAIN, 13));
			this.dataLabel.setText("");
			this.dataLabel.setHorizontalAlignment(JLabel.CENTER);
			this.dataLabel.setBounds(0, 380, 880, 20);
			this.add(this.dataLabel);
			
			///
			/// �����N�{�^���̐ݒ�
			///
			y  = 390;
			this.linkBtn = new LinkButton[12];
			this.linkBtn[0] = new LinkButton("�ŏ���");
			this.linkBtn[1] = new LinkButton("�Ō��");
			for ( i=2; i<this.linkBtn.length; i++ ) {
				String strText = "" + (i-1);
//				this.linkBtn[i] = new LinkButton(strText,120 + ((i-2)*35),y);
				this.linkBtn[i] = new LinkButton(strText,dispWidth/2 - 175 + ((i-2)*35),y);
// ADD start 20120706 hisahori
				this.linkBtn[i].setBounds(this.linkBtn[i].getLocation().x, this.linkBtn[i].getLocation().y, 60, this.linkBtn[i].getHeight());
// ADD end 20120706 hisahori
			}
			this.linkBtn[0].setBounds(150,y,80,this.linkBtn[2].getHeight());
			this.linkBtn[1].setBounds(685,y,80,this.linkBtn[2].getHeight());		
			//�����N�{�^�����p�l���ɒǉ�
			for ( i=0; i<this.linkBtn.length; i++ ) {
				this.add(this.linkBtn[i]);
			}
			
			///
			/// �u���ցv�E�u�O�ցv�����N�{�^���̐ݒ�
			///
			//�����N�{�^���Ƀe�L�X�g�ƕ\�����W��ݒ�
			this.linkPrevBtn = new LinkButton("<<", this.linkBtn[2].getX() - this.linkBtn[2].getWidth(), y);
			this.linkNextBtn = new LinkButton(">>", this.linkBtn[11].getX() + this.linkBtn[11].getWidth(), y);
			this.linkPrevBtn.setEnabled(false);
			this.linkNextBtn.setEnabled(false);
			//�����N�{�^�����p�l���ɒǉ�
			this.add(this.linkPrevBtn);
			this.add(this.linkNextBtn);
			
				
			//�A:�e�R���|�[�l���g�̃C�x���g��ݒ肷��
			
			//�����e�[�u���ɃL�[�C�x���g��ݒ肷��
			materialTable.getMainTable().addKeyListener(getKeyEvent());
	
			//�����{�^���ɃC�x���g��ݒ�
			this.searchBtn.addActionListener(this.getActionEvent());
			this.searchBtn.setActionCommand(this.SEARCH_BTN_CLICK);
			//��ЃR���{�{�b�N�X�ɃC�x���g��ݒ�
			this.kaishaComb.addActionListener(this.getActionEvent());
			this.kaishaComb.setActionCommand(this.KAISHA_COMB_SELECT);
//add start --------------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.4
			//�H��R���{�{�b�N�X�ɃC�x���g��ݒ�
			this.kojoComb.addActionListener(this.getActionEvent());
			this.kojoComb.setActionCommand(this.KOUJYOU_COMB_SELECT);
			
//add end --------------------------------------------------------------------------------------

			//�����N�{�^���ɃC�x���g��ݒ�
			for ( i=0; i<this.linkBtn.length; i++ ) {
				this.linkBtn[i].addActionListener(this.getActionEvent());
				this.linkBtn[i].setActionCommand(this.LINK_BTN_CLICK);
			}
			//�u���ցv�E�u�O�ցv�����N�{�^�����C�x���g��ݒ�
			this.linkPrevBtn.addActionListener(this.getActionEvent());
			this.linkPrevBtn.setActionCommand(this.LINK_PREV_BTN_CLICK);
			this.linkNextBtn.addActionListener(this.getActionEvent());
			this.linkNextBtn.setActionCommand(this.LINK_NEXT_BTN_CLICK);

		} catch(ExceptionBase eb){
			throw eb;
			
		}catch (Exception e) {
			//�G���[�ݒ�
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			ex.setStrErrmsg("�����ꗗ�p�l���̃R���g���[���z�u�����s���܂���");
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getMessage());
			throw ex;
		} finally {
		}
	}
	
	/**
	 * �����ꗗ�e�[�u���z�u����
	 * @throws ExceptionBase 
	 */
	private void addTable() throws ExceptionBase {
		
		try {
			//�e�[�u���̃C���X�^���X����
			this.materialTable = new MaterialTable();
			
			//�e�[�u���pScroll�p�l���̐ݒ�
			this.materialTable.getScroll().setBounds(5, 80, 880, 290);
			this.add(this.materialTable.getScroll());

		} catch(ExceptionBase eb){
			throw eb;
			
		}catch (Exception e) {
			//�G���[�ݒ�
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			ex.setStrErrmsg("�����ꗗ�p�l���̃e�[�u���z�u���������s���܂���");
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getMessage());
			throw ex;
			
		} finally {
			
		}
		
	}

	/**
	 * ����������
	 */
	private void init() throws ExceptionBase {
		//�@:�yJW620�z�����s���A�����R���{�{�b�N�X�R���g���[���ɐݒ肷��
		try {
			//��ЃR���{�{�b�N�X�̏����ݒ�
			this.isSelectKaishaCmb = false;
			this.conJW610();
			this.setKaishaCmb();
			//�����R���{�{�b�N�X�̏����ݒ�
			this.isSelectKaishaCmb = true;
			this.selectKaishaComb();
	
			//�����N�{�^���̏����ݒ�
			for(int i=0; i<this.linkBtn.length; i++) {
				this.linkBtn[i].setEnabled(false);
			}
			//�u���ցv�E�u�O�ցv�����N�{�^���̏����ݒ�
			this.linkNextBtn.setEnabled(false);
			this.linkPrevBtn.setEnabled(false);

		}catch(ExceptionBase eb){
			throw eb;
			
		}catch (Exception e) {
			//�G���[�ݒ�
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			ex.setStrErrmsg("�����ꗗ�p�l���̏��������������s���܂���");
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getMessage());
			throw ex;
			
		} finally {
			
		}
		
	}

	/**
	 * ��ʃN���A����
	 */
	public void clearDisp(boolean kensakuFg) throws ExceptionBase{

		try{
			//�������s��
			if(kensakuFg){
				//�e�[�u��������
				materialTable.clearTable();
				
				//�����N�{�^���̏����ݒ�
				for(int i=0; i<this.linkBtn.length; i++) {
					this.linkBtn[i].setEnabled(false);
				}
				//�u���ցv�E�u�O�ցv�����N�{�^���̏����ݒ�
				this.linkNextBtn.setEnabled(false);
				this.linkPrevBtn.setEnabled(false);
				
				//�f�[�^���\��������
				this.dataLabel.setText("");
				
			//��ʏI����
			}else{
				//�e�[�u��������
				materialTable.clearTable();

				//�����N�{�^���̏����ݒ�
				for(int i=0; i<this.linkBtn.length; i++) {
					this.linkBtn[i].setEnabled(false);
				}
				//�u���ցv�E�u�O�ցv�����N�{�^���̏����ݒ�
				this.linkNextBtn.setEnabled(false);
				this.linkPrevBtn.setEnabled(false);
				
				//�e�L�X�g�{�b�N�X�̏�����
				this.codeTextbox.setText("");
				this.nameTextbox.setText("");
				this.kaishaComb.setSelectedIndex(0);
				this.kojoComb.setSelectedIndex(0);
				
				//�f�[�^���\��������
				this.dataLabel.setText("");
				
				
			}
			
			
			
			
		}catch(Exception e){
			//�G���[�ݒ�
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			ex.setStrErrmsg("�����ꗗ�p�l���̉�ʃN���A���������s���܂���");
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getMessage());
			throw ex;
			
		}finally{
			
		}
		
	}

	/**
	 * �t�H�[�J�X���ݒ�p�R���|�[�l���g�S�@�Q�b�^�[
	 * @return �t�H�[�J�X���ݒ�p�R���|�[�l���g�S 
	 */
	public JComponent[][] getSetFocusComponent() {
		JComponent[][] comp = new JComponent[][] {
//mod start --------------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.5
//				{ this.codeTextbox, this.kaishaComb },		//1:�R�[�h, 2:���
//				{ this.nameTextbox, this.kojoComb },			//3;���O, 4:�H��
				{ this.nameTextbox, this.codeTextbox },		//���O, �R�[�h
				{ this.kaishaComb, this.kojoComb },			//���, �H��
				this.radioButton,								//�g�p���сE�S�����W�I�{�^��
//mod end --------------------------------------------------------------------------------------
				{ this.searchBtn, this.endBtn },				//5:����, 6:�I��
				{ this.materialTable.getMainTable() },			//7:�������X�g
				{ this.linkPrevBtn },								//9:�O�փ����N�{�^�� 
				this.linkBtn,											//8�`:�����N�{�^��
				{ this.linkNextBtn }								//10:���փ����N�{�^��
		};
		return comp;
	}
	
	/**
	 * �I���{�^���������C�x���g�ݒ�
	 * @param actionListener
	 * @throws ExceptionBase 
	 */
	public void setEndEvent(ActionListener actionListener, String actionCommand) throws ExceptionBase {
		try {
			//�I���{�^���ɃC�x���g��ݒ�
			this.endBtn.addActionListener(actionListener);
			this.endBtn.setActionCommand(actionCommand);
			
		}catch (Exception e) {
			//�G���[�ݒ�
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			ex.setStrErrmsg("�����ꗗ�p�l���̏I���{�^���C�x���g�����s���܂���");
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getMessage());
			throw ex;
			
		} finally {
		}
	}
	
	/**
	 * �I���{�^������������
	 * @throws ExceptionBase 
	 * @throws ExceptionBase 
	 */
	public void clickEndBtn() throws ExceptionBase {
		try {
			//��ʃN���A����
			clearDisp(false);
			
		}catch(ExceptionBase eb){
			throw eb;
			
		}catch (Exception e) {
			//�G���[�ݒ�
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			ex.setStrErrmsg("�����ꗗ�p�l���̏I���{�^�����������������s���܂���");
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getMessage());
			throw ex;
		} finally {
		}
	}
	
	
	/**
	 * �����{�^������������
	 * @throws ExceptionBase 
	 */
	public void clickSearchBtn(String strPageNum, boolean isSearch) throws ExceptionBase {
		try {
	
			//�����������s��
			this.conJW630(strPageNum);
			
			//�������ʂ���̏ꍇ�A�e�[�u�����N���A
			if ( DataCtrl.getInstance().getMaterialMstData().getAryMateData().size() == 0
					|| ((MaterialData)DataCtrl.getInstance().getMaterialMstData().getAryMateData().get(0)).getIntKaishacd() == 0 ) {
				clearDisp(true);		//��ʃN���A����
				return;
			}

//mod start --------------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.6
			//�S�H��`�F�b�N
//			boolean isZenkojo = this.kojoComb.getSelectedItem().equals("�S�H��");
			boolean isZenkojo = this.getSelectKojoCd().equals(KOJO_ZENKOJO);
//mod end --------------------------------------------------------------------------------------
			
			//�e�[�u��������
			if ( isZenkojo ) {
				//�L���[�s�[���S�H��̏ꍇ
				this.materialTable.initTableZenkojo();
				
				//�����_������(�ʏ�)
				MiddleCellRenderer md = new MiddleCellRenderer();
				//�����_�������i���l�j
				MiddleCellRenderer mdn = new MiddleCellRenderer();
	
				//�e�[�u���ɍ��ڂ�ǉ�
				for ( int i=0; i<DataCtrl.getInstance().getMaterialMstData().getAryMateData().size(); i++ ) {
					materialTable.insertMainTableZenkojo(((MaterialData)DataCtrl.getInstance().getMaterialMstData().getAryMateData().get(i)),md,mdn);	
				}
				
				//�����_���ݒ�
				//�e�[�u���J�����擾
				DefaultTableColumnModel columnModel = (DefaultTableColumnModel)materialTable.getMainTable().getColumnModel();
				TableColumn column = null;
				for(int i = 0; i<materialTable.getMainTable().getColumnCount(); i++){
					//�e�[�u���J�����֐ݒ�
					column = materialTable.getMainTable().getColumnModel().getColumn(i);
					
					//�P��or������̏ꍇ�͉E��
					if(2 < i && i < materialTable.getMainTable().getColumnCount()-3){
						column.setCellRenderer(mdn);
						
					//�P��or������ȊO�͒ʏ�̂���
					}else{
						column.setCellRenderer(md);
					}
				}
				
			} else {
				//�S�H��ł͂Ȃ��ꍇ
				this.materialTable.initTableNotZenkojo();
				
				//�����_������(�ʏ�)
				MiddleCellRenderer md = new MiddleCellRenderer();
				//�����_�������i���l�j
				MiddleCellRenderer mdn = new MiddleCellRenderer();
//add start --------------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.4
				//�����_������(�ʏ�)�i���������j
				MiddleCellRenderer mdc = new MiddleCellRenderer();
//add end --------------------------------------------------------------------------------------
				
				//�e�[�u���ɍ��ڂ�ǉ�
				for ( int i=0; i<DataCtrl.getInstance().getMaterialMstData().getAryMateData().size(); i++ ) {
//mod start --------------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.4
					//materialTable.insertMainTableNotZenkojo(i, ((MaterialData)DataCtrl.getInstance().getMaterialMstData().getAryMateData().get(i)),md,mdn);	
					materialTable.insertMainTableNotZenkojo(i, ((MaterialData)DataCtrl.getInstance().getMaterialMstData().getAryMateData().get(i)),md,mdn,mdc);	
//mod start --------------------------------------------------------------------------------------
				}
				
				//�����_���ݒ�
				//�e�[�u���J�����擾
				DefaultTableColumnModel columnModel = (DefaultTableColumnModel)materialTable.getMainTable().getColumnModel();
				TableColumn column = null;
				for(int i = 0; i<materialTable.getMainTable().getColumnCount(); i++){
					//�e�[�u���J�����֐ݒ�
					column = materialTable.getMainTable().getColumnModel().getColumn(i);
					//�P��or������̏ꍇ�͉E��
//mod start --------------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.4
					//if(i >= 4 && i <= 9){
					if((i == 3) || (i >= 6 && i <= 10)){
//mod start --------------------------------------------------------------------------------------
						column.setCellRenderer(mdn);
//mod start --------------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.4
					//�O�����͒��������ɂ���
					}else if(i == 4 || i == 5){
						
						column.setCellRenderer(mdc);
//mod start --------------------------------------------------------------------------------------
					//�P��or������ȊO�͒ʏ�̂���
					}else{
						column.setCellRenderer(md);
					}
				}
			}
			
			//���X�g�\�������y�эő匏�����擾
			int intListRowMax = ((MaterialData)DataCtrl.getInstance().getMaterialMstData().getAryMateData().get(0)).getIntListRowMax();
			int intMaxRow = ((MaterialData)DataCtrl.getInstance().getMaterialMstData().getAryMateData().get(0)).getIntMaxRow();
			
			//�ő�l��0�łȂ��ꍇ
			if ( intMaxRow != 0 ) {
				if ( (intMaxRow%intListRowMax != 0) ) {
					intLinkMaxNum = (intMaxRow/intListRowMax)+1;
				} else {
					intLinkMaxNum = (intMaxRow/intListRowMax);
				}
			}
			
			//�f�[�^���̕\��
			//System.out.println(strPageNum);
			this.dataLabel.setText("<html>�f�[�^���@�F�@" + intMaxRow + " ���ł�(" + intListRowMax + "�����ɕ\�����Ă��܂�)�@�@�@<b>"+Integer.parseInt(strPageNum)+"�^"+intLinkMaxNum+" �y�[�W<b></html>");
			
			if ( isSearch ) {
				//�����N�{�^���̏����ݒ�
				for(int i=0; i<this.linkBtn.length; i++) {
					if ( i > 1 ) {
						this.linkBtn[i].setText("" + (i-1));
					}
					this.linkBtn[i].setEnabled(false);
				}
				this.linkPrevBtn.setEnabled(false);
				this.linkNextBtn.setEnabled(false);
				
				
				this.linkBtn[0].setEnabled(true);
				this.linkBtn[1].setEnabled(true);
				for( int i=0; i<intLinkMaxNum; i++ ) {
					if ( i < this.linkBtn.length-2 ) { 
						this.linkBtn[i+2].setEnabled(true);
					}
				}
				
				//�������ʂ̌������\���ő匏�����A�����Ă���ꍇ
				//if ( this.intLinkMaxNum > intListRowMax ) {
				if ( intMaxRow > (intListRowMax*10) ) {
					//[����]�E[�O��]�����N�{�^�����g�p�ɐݒ�
					this.linkPrevBtn.setEnabled(true);
					this.linkNextBtn.setEnabled(true);
				}
			}

		} catch (ExceptionBase e) {
			throw e;
			
		} catch (Exception e) {
			//�G���[�ݒ�
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			ex.setStrErrmsg("�����ꗗ�p�l���̌����{�^�����������������s���܂���");
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getMessage());
			throw ex;
			
		} finally {
		}		
	}
	
	/**
	 * ��ЃR���{�{�b�N�X�I��������
	 * @throws ExceptionBase 
	 */
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
			ex.setStrErrmsg("�����ꗗ�p�l���̉�ЃR���{�{�b�N�X�I�������������s���܂���");
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getMessage());
			throw ex;
			
		} finally {
		}	
	}

//add start --------------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.4
	/**
	 * �H��R���{�{�b�N�X�I��������
	 * @throws ExceptionBase 
	 */
	private void selectKojoComb() throws ExceptionBase {
		try {
			if ( this.kojoComb.isValid() ) {		//���d������h��
				this.setShiyoFlg();
			}
		} catch (Exception e) {
			//�G���[�ݒ�
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			ex.setStrErrmsg("�����ꗗ�p�l���̍H��R���{�{�b�N�X�I�������������s���܂���");
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getMessage());
			throw ex;
			
		} finally {
		}	
	}
//add end --------------------------------------------------------------------------------------

	/**
	 * �����N�{�^������������
	 * @throws ExceptionBase 
	 */
	private void clickLinkBtn(Object objSource) throws ExceptionBase {
		
		try {

			//���X�g�\�������y�эő匏�����擾
			int intListRowMax = ((MaterialData)DataCtrl.getInstance().getMaterialMstData().getAryMateData().get(0)).getIntListRowMax();
			int intMaxRow = ((MaterialData)DataCtrl.getInstance().getMaterialMstData().getAryMateData().get(0)).getIntMaxRow();
			int intLinkCnt = intMaxRow / (intListRowMax*10);
			
			if ( objSource.equals(this.linkBtn[0]) ) {
				//�ŏ���
				this.clickSearchBtn("1", false);
				
				//�ŏ��܂Ŗ߂�
				for ( int i=0; i<intLinkCnt; i++ ) {
					this.clickLinkPrevBtn(objSource);
					
				}
				
			} else if ( objSource.equals(this.linkBtn[1]) ) {
				//�Ō��
				this.clickSearchBtn("" + intLinkMaxNum, false);
				
					//�Ō�܂Ői��
				for ( int i=0; i<intLinkCnt; i++ ) {
					this.clickLinkNextBtn(objSource);
					
				}
				
			} else {
				//���̑�
				for ( int i=0; i<this.linkBtn.length; i++ ) {
					
					if ( objSource.equals(this.linkBtn[i+2]) ) {
						
						if ( this.linkBtn[i+2].isEnabled() ) {
							//�Ώۃ����N�ԍ���
							this.clickSearchBtn(this.linkBtn[i+2].getText(), false);
							break;
						}
						
					}
					
				}
				
			}
		} catch (ExceptionBase e) {
			throw e;
		} catch (Exception e) {
			//�G���[�ݒ�
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			ex.setStrErrmsg("�����ꗗ�p�l���̃����N�{�^�����������������s���܂���");
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getMessage());
			throw ex;
			
		} finally {
		}
		
	}

	/**
	 * �u���ցv�����N�{�^������������
	 * @param source
	 * @throws ExceptionBase 
	 */
	private void clickLinkNextBtn(Object source) throws ExceptionBase {

		try {
			
			//�����N�ő�\���l���擾
			int chkValue = Integer.parseInt(this.linkBtn[11].getText());
			
			//�����N�ő�\���l���������ʍő�l�𒴂��Ă��Ȃ���
			if ( chkValue < this.intLinkMaxNum ) {

				//�����N�ŏ��\���l���擾
				int intValue = Integer.parseInt(this.linkBtn[2].getText());
				
				//�����N�\���l�𑝂₷
				for ( int i=0; i<10; i++ ) {
					
					//�����N�\���l�Đݒ�
					this.linkBtn[i + 2].setText(Integer.toString(intValue + 10 + i));
					
					//�����N�g�p�ۍĐݒ�
					if ( (intValue + 10 + i) <= intLinkMaxNum ) {
						this.linkBtn[i + 2].setEnabled(true);
						
					} else {
						this.linkBtn[i + 2].setEnabled(false);
						
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

	/**
	 * �u�O�ցv�����N�{�^������������
	 * @param source
	 * @throws ExceptionBase 
	 */
	private void clickLinkPrevBtn(Object source) throws ExceptionBase {

		try {

			//�����N�ŏ��\���l
			int chkValue = Integer.parseInt(this.linkBtn[2].getText());
			
			//�����N�ŏ��\���l�������l�ł͂Ȃ��ꍇ
			if ( chkValue != 1 ) {				
				
				//�����N�\���l���P�O�O�ɖ߂�
				for ( int i=0; i<10; i++ ) {
					
					//�����N�\���l�Đݒ�
					this.linkBtn[i + 2].setText(Integer.toString(chkValue - 10 + i));
					
					//�����N�g�p�ۍĐݒ�
					if ( (chkValue - 10 + i) <= intLinkMaxNum ) {
						this.linkBtn[i + 2].setEnabled(true);
						
					} else {
						this.linkBtn[i + 2].setEnabled(false);
						
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
			ex.setStrErrmsg("�����ꗗ�p�l���̉�ЃR���{�{�b�N�X�ݒ菈�������s���܂���");
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getMessage());
			throw ex;
		} finally {
		}
		
	}

	/**
	 * �����R���{�{�b�N�X�ݒ菈��
	 * @throws ExceptionBase 
	 */
	private void setKojoCmb() throws ExceptionBase {
		int i;
		try {
			
			String bushoNm = "";
			
			//�R���{�{�b�N�X�̑S���ڂ̍폜
			this.kojoComb.removeAllItems();
			
//			//�����ɂ��ݒ�
//			ArrayList Auth = DataCtrl.getInstance().getUserMstData().getAryAuthData();
//			for(int j=0; j<Auth.size(); j++){
//				String[] strAuth = (String[])Auth.get(j);
//				//���ID�`�F�b�N
//				if(strAuth[0].equals("150")){
//					//�f�[�^ID�`�F�b�N�i�S�Ă̏ꍇ�j
//					if(strAuth[2].equals("9")){
//						//�󍀖ڂ�ǉ�
//						this.kojoComb.addItem("");
//					}
//				}
//			}

//mod start --------------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.6
			//�󍀖ڂ�ǉ�
//			this.kojoComb.addItem("");
			this.kojoComb.addItem(KOJO_ZENKOJO_TATE);

			//��ЃR���{�{�b�N�X���u�L���[�s�[�v��I�����Ă���ꍇ�A�S�H������ڂɒǉ�
			if ( this.kaishaComb.getSelectedItem().equals("�L���[�s�[") ) {
//				this.kojoComb.addItem("�S�H��");
				this.kojoComb.addItem(KOJO_ZENKOJO_YOKO);
			}
//mod end --------------------------------------------------------------------------------------
			
			//�\�����ɉ����ăR���{�{�b�N�X�ɒl��ǉ�
			for ( i=0; i<BushoData.getArtBushoCd().size(); i++ ) {
				//��Ж�
				bushoNm = BushoData.getAryBushoNm().get(i).toString(); 
				
				//�R���{�{�b�N�X�֒ǉ�
				this.kojoComb.addItem(bushoNm);
			}
		} catch (Exception e) {
			//�G���[�ݒ�
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			ex.setStrErrmsg("�����ꗗ�p�l���̕����R���{�{�b�N�X�ݒ菈�������s���܂���");
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getMessage());
			throw ex;
			
		} finally {
			
		}
		
	}

	/**
	 * �I����ЃR�[�h�̎擾
	 * @return �I����ЃR�[�h 
	 * @throws ExceptionBase 
	 */
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
			ex.setStrErrmsg("�����ꗗ�p�l���̑I����ЃR�[�h�擾���������s���܂���");
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getMessage());
			throw ex;
		} finally {
		}
		
		return intRetKaishaCd;
	}

	/**
	 * �I���H��R�[�h�̎擾
	 * @return �I���H��R�[�h 
	 * @throws ExceptionBase 
	 */
	private String getSelectKojoCd() throws ExceptionBase {
		int i;
		String strRetKojoCd = "";
		String bushoCd = "";
		String bushoNm = "";
		try {
			//�H��R���{�{�b�N�X�̑I��l���擾
			String kojoCombItem = this.kojoComb.getSelectedItem().toString();
			

//mod start --------------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.6
//			if ( !kojoCombItem.equals("�S�H��") ) {
			if ( !kojoCombItem.equals(KOJO_ZENKOJO_YOKO) ) {
//mod end --------------------------------------------------------------------------------------
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
			} else {
//mod start --------------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.6
				//�H�ꂪ�S�H��̏ꍇ
//				strRetKojoCd = "zenkojo";
				strRetKojoCd = KOJO_ZENKOJO;
//mod end --------------------------------------------------------------------------------------
			}
		} catch (Exception e) {
			//�G���[�ݒ�
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			ex.setStrErrmsg("�����ꗗ�p�l���̑I����ЃR�[�h�擾���������s���܂���");
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getMessage());
			throw ex;
		} finally {
		}
		
		return strRetKojoCd;
	}
	
	/**
	 * �yJW610�z ��ЃR���{�{�b�N�X�l�擾 ���MXML�f�[�^�쐬
	 */
	private void conJW610() throws ExceptionBase{
		try{
			
			//�@���M�p�����[�^�i�[
			String strUser = DataCtrl.getInstance().getParamData().getDciUser().toString();
			String strGamenId = "150";
			
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
			// Result�f�[�^.�������ʂ�true�̏ꍇ�AExceptionBase��Throw����
			if ( !DataCtrl.getInstance().getResultData().isReturnFlgCheck() ) {
				clearDisp(false);		//��ʃN���A����
				throw new ExceptionBase();
			}
			
		} catch (ExceptionBase e) {
			throw e;
			
		} catch (Exception e) {
			//�G���[�ݒ�
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			ex.setStrErrmsg("�����ꗗ�p�l���̉�ЃR���{�{�b�N�X�l�擾�ʐM���������s���܂���");
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getMessage());
			throw ex;
			
		}finally{
		}
	}
		
	/**
	 * �yJW620�z ��ЃR���{�{�b�N�X������ ���MXML�f�[�^�쐬
	 * @param strKaishaCd : ��ЃR�[�h
	 */
	private void conJW620(String strKaishaCd) throws ExceptionBase{
		try{
			
			//�@���M�p�����[�^�i�[
			String strUser = DataCtrl.getInstance().getParamData().getDciUser().toString();
			String strGamenId = "150";
			
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
//			xmlConnection.setStrAddress("http://localhost:8080/Shisaquick_SRV/AjaxServlet");
			xmlConnection.setStrAddress(DataCtrl.getInstance().getJnlpConnect().getStrAddressAjax());
			xmlConnection.XmlSend();
			//�@XML��M
			xmlJW620 = xmlConnection.getXdocRes();
//			xmlJW620.dispXml();
			
			//�@�e�X�gXML�f�[�^
			//xmlJW620 = new XmlData(new File("src/main/JW620.xml"));

			//�����f�[�^
			BushoData.setBushoData(xmlJW620);

			// Result�f�[�^
			DataCtrl.getInstance().getResultData().setResultData(xmlJW620);
			// Result�f�[�^.�������ʂ�true�̏ꍇ�AExceptionBase��Throw����
			if ( !DataCtrl.getInstance().getResultData().isReturnFlgCheck() ) {
				throw new ExceptionBase();
			}
			
		}catch(ExceptionBase e){
			throw e;
		}catch(Exception e){
			//�G���[�ݒ�
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			ex.setStrErrmsg("�����ꗗ�p�l���̉�ЃR���{�{�b�N�X�����ʐM���������s���܂���");
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getMessage());
			throw ex;
		}finally{
		}
	}
	
	/**
	 * �yJW630�z �����ꗗ�������� ���MXML�f�[�^�쐬
	 * @param strKaishaCd : ��ЃR�[�h
	 */
	private void conJW630(String strSelectRow) throws ExceptionBase{

		try {
			//�@���M�p�����[�^�i�[
			String strUser = DataCtrl.getInstance().getParamData().getDciUser().toString();
			String strGenryoCd = this.codeTextbox.getText();
			String strGenryoNm = this.nameTextbox.getText();
			String strKaishaCd = this.getSelectKaishaCd();
			String strBushoCd = this.getSelectKojoCd();
//add start --------------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.4
			// �g�p���у��W�I�{�^�����I���t���O�ݒ�
			String strShiyoFlg = "";
			//���W�I�{�^�����I�� ���� �����R�[�h���S�H��A���I���ł͂Ȃ��ꍇ
			if (this.radioButton[0].isSelected() 
					&& !strBushoCd.equals(KOJO_ZENKOJO) 
					&& !strBushoCd.equals("") ) {
				strShiyoFlg = "1";		//�I������Ă���
			} else {
				strShiyoFlg = "0";		//�I������Ă��Ȃ�
			}
//add end --------------------------------------------------------------------------------------
			
			//�@���MXML�f�[�^�쐬
			xmlJW630 = new XmlData();
			ArrayList arySetTag = new ArrayList();

			//�@Root�ǉ�
			xmlJW630.AddXmlTag("","JW630");
			arySetTag.clear();
			
			//�@�@�\ID�ǉ�
			xmlJW630.AddXmlTag("JW630", "USERINFO");
				//�@�e�[�u���^�O�ǉ�
				xmlJW630.AddXmlTag("USERINFO", "table");	
				//�@���R�[�h�ǉ�
				String[] kbn_shori = {"kbn_shori", "3"};
				String[] id_user = {"id_user",strUser};
				arySetTag.add(kbn_shori);
				arySetTag.add(id_user);
				xmlJW630.AddXmlTag("table", "rec", arySetTag);
				arySetTag.clear();

			//�@�y���͌��������z �@�\ID�ǉ�
			xmlJW630.AddXmlTag("JW630", "SA510");

				//�@�e�[�u���^�O�ǉ�
				xmlJW630.AddXmlTag("SA510", "table");
				//�@���R�[�h�ǉ�
				String[] taisho_genryo1 = new String[]{"taisho_genryo1","true"};
				String[] taisho_genryo2 = new String[]{"taisho_genryo2","true"};
				String[] cd_genryo = new String[]{"cd_genryo",strGenryoCd};
				String[] nm_genryo = new String[]{"nm_genryo",strGenryoNm};
				String[] cd_kaisha;
				if(strKaishaCd.equals("0")){
					cd_kaisha = new String[]{"cd_kaisha",""};
				}else{
					cd_kaisha = new String[]{"cd_kaisha",strKaishaCd};
				}
				String[] cd_busho = new String[]{"cd_busho",strBushoCd};
				String[] num_selectRow = new String[]{"num_selectRow",strSelectRow};
//add start --------------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.4
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
				
				xmlJW630.AddXmlTag("table", "rec", arySetTag);
				arySetTag.clear();
	
			//�@XML���M
			XmlConnection xmlConnection = new XmlConnection(xmlJW630);
			
			//xmlJW630.dispXml();
			
			xmlConnection.setStrAddress(DataCtrl.getInstance().getJnlpConnect().getStrAddressAjax());
			xmlConnection.XmlSend();
			//�@XML��M
			xmlJW630 = xmlConnection.getXdocRes();

			//xmlJW630.dispXml();

			//�@�e�X�gXML�f�[�^
			//xmlJW630 = new XmlData(new File("src/main/JW630.xml"));

//			//�����f�[�^
//			DataCtrl.getInstance().getMaterialMstData().setMaterialData(xmlJW630,"SA510");
//			DataCtrl.getInstance().getMaterialMstData().DispMateData();

			// Result�f�[�^
			DataCtrl.getInstance().getResultData().setResultData(xmlJW630);
//			DataCtrl.getInstance().getResultData().dispData();
			
			// Result�f�[�^.�������ʂ�true�̏ꍇ�AExceptionBase��Throw����
			if ( !DataCtrl.getInstance().getResultData().isReturnFlgCheck() ) {	
				throw new ExceptionBase();
			} else {
				//�����f�[�^
				DataCtrl.getInstance().getMaterialMstData().setMaterialData(xmlJW630,"SA510");
			}

		}catch(ExceptionBase e){
			throw e;
		}catch(Exception e){
			//�G���[�ݒ�
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			ex.setStrErrmsg("�����ꗗ�p�l���̌����ꗗ�����ʐM���������s���܂���");
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getMessage());
			throw ex;
		}finally{
		}
	}
	
	/**
	 * KeyAdapter�擾���\�b�h
	 *  : �L�[�������C�x���g��ݒ肷��
	 * @return KeyAdapter�N���X
	 * @throws ExceptionBase 
	 */
	private KeyAdapter getKeyEvent() throws ExceptionBase {
		KeyAdapter keyAdapter = null;
		try {
			keyAdapter =  new KeyAdapter() {
				public void keyPressed(KeyEvent e) {
					try {
						//F2�L�[������
						if ( e.getKeyCode() == KeyEvent.VK_F1 ) {
							//�I������Ă���s�ԍ����擾
							int selectRow = materialTable.getMainTable().getSelectedRow();
							//�����e�[�u�����A�Ώۍs�̌����R�[�h���擾
							String genryoCd = "";
							String kaishaCd = "";
							String bushoCd = "";
							try {
								
								if ( selectRow >= 0 ) {
//mod start --------------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.4
//									genryoCd = materialTable.getMainTable().getValueAt(selectRow, 1).toString();
									if (!getSelectKojoCd().equals(KOJO_ZENKOJO)) {
										//�S�H��ł͂Ȃ��ꍇ
										genryoCd = materialTable.getMainTable().getValueAt(selectRow, 0).toString();										
									} else {
										//�S�H��̏ꍇ
										genryoCd = materialTable.getMainTable().getValueAt(selectRow, 1).toString();										
									}
//mod end --------------------------------------------------------------------------------------
									kaishaCd = materialTable.getTableKaishaCd(selectRow);
									bushoCd = materialTable.getTableBushoCd(selectRow);
								}
							} catch(NullPointerException ne) {
								//���X�g�̒l��NULL�̏ꍇ
								genryoCd = "";
							} finally {
							}
							//�擾���������R�[�h���N���b�v�{�[�h�i�[�N���X�ɐݒ�
							DataCtrl.getInstance().getClipboardData().setStrClipboad(genryoCd + "\n" + kaishaCd + "\n" + bushoCd);
						}
					} catch (Exception ec) {
						//�G���[�ݒ�
						ex = new ExceptionBase();
						ex.setStrErrCd("");
						ex.setStrErrmsg("�����ꗗ�p�l���̃L�[�������C�x���g���������s���܂���");
						ex.setStrErrShori(this.getClass().getName());
						ex.setStrMsgNo("");
						ex.setStrSystemMsg(ec.getMessage());
						DataCtrl.getInstance().PrintMessage(ex);
					} finally {
					}
				}			
			};
		} catch (Exception e) {
			//�G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("�����ꗗ�p�l���̃L�[�C�x���g���������s���܂���");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;
		} finally {
		}
		return keyAdapter;
	}
	
	
	/**
	 * ActionListener�擾���\�b�h
	 *  : �{�^���������y�уR���{�{�b�N�X�I�����̃C�x���g��ݒ肷��
	 * @return ActionListener 
	 * @throws ExceptionBase
	 */
	private ActionListener getActionEvent() throws ExceptionBase {
		ActionListener listener = null;
		try{
			listener = new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						String event_name = e.getActionCommand();
						if ( event_name.equals(SEARCH_BTN_CLICK)) {
							
							//�����{�^��������							
							//�N���b�v�{�[�h�̒l���N���A
							DataCtrl.getInstance().getClipboardData().setStrClipboad("");
							//��������
							clickSearchBtn("1", true);
							
						} else if ( event_name.equals(KAISHA_COMB_SELECT)) {
							
							//��ЃR���{�{�b�N�X�I����
							selectKaishaComb();
							
//add start --------------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.4
							radioButton[1].setSelected(true);
							radioButton[0].setEnabled(false);
//add end --------------------------------------------------------------------------------------
							
//add start --------------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.4
						} else if ( event_name.equals(KOUJYOU_COMB_SELECT)) {
							//�H��R���{�{�b�N�X�I����
							selectKojoComb();
//add end --------------------------------------------------------------------------------------
							
						} else if ( event_name.equals(LINK_BTN_CLICK) ) {
							
							//�����N�{�^��������
							clickLinkBtn( e.getSource() );
							
						} else if ( event_name.equals(LINK_PREV_BTN_CLICK) ) {
							
							//[�O��]�����N�{�^��������
							clickLinkPrevBtn( e.getSource() );
							
						} else if ( event_name.equals(LINK_NEXT_BTN_CLICK) ) {
							
							//[����]�����N�{�^��������
							clickLinkNextBtn( e.getSource() );
							
						}
					} catch (ExceptionBase eb) {
						
						DataCtrl.getInstance().PrintMessage(eb);
						
						//��ʃN���A����
						try{
							clearDisp(true);
							
						}catch(ExceptionBase exb){
							
							DataCtrl.getInstance().PrintMessage(exb);
							
						}catch(Exception ec){
							//�G���[�ݒ�
							ex = new ExceptionBase();
							ex.setStrErrCd("");
							ex.setStrErrmsg("�����ꗗ�p�l���̉�ʃN���A���������s���܂���");
							ex.setStrErrShori(this.getClass().getName());
							ex.setStrMsgNo("");
							ex.setStrSystemMsg(ec.getMessage());
							
							DataCtrl.getInstance().PrintMessage(eb);
							
						}finally{
							
						}
						
						
					} catch (Exception ec) {
						
						//�G���[�ݒ�
						ex = new ExceptionBase();
						ex.setStrErrCd("");
						ex.setStrErrmsg("�����ꗗ�p�l���̃A�N�V�����C�x���g���������s���܂���");
						ex.setStrErrShori(this.getClass().getName());
						ex.setStrMsgNo("");
						ex.setStrSystemMsg(ec.getMessage());
						DataCtrl.getInstance().PrintMessage(ex);
						
					} finally {
					}
				}

			};
			
		} catch (Exception e) {
			//�G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("�����ꗗ�p�l���̃A�N�V�����C�x���g���������s���܂���");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;
		} finally {
		}
		
		return listener;
	}
	
//add start --------------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.4
	/**
	 * �g�p���уt���O�����ݒ�
	 * @throws ExceptionBase 
	 */
	public void setShiyoFlg() throws ExceptionBase {
		try {
			//�󔒂��S�H��_���̏ꍇ�́A�S���݂̂Ƃ���
			if ( this.kojoComb.getSelectedItem().equals(KOJO_ZENKOJO_TATE) 
					|| this.getSelectKojoCd().equals(KOJO_ZENKOJO)
					|| this.kojoComb.getSelectedItem().equals("�V�K�o�^����")  ) {
				this.radioButton[1].setSelected(true);
				this.radioButton[0].setEnabled(false);
			}
			else {
				this.radioButton[0].setEnabled(true);
				this.radioButton[0].setSelected(true);
			}
		} catch (Exception e) {
			//�G���[�ݒ�
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			ex.setStrErrmsg("�g�p���уt���O�����ݒ菈�������s���܂���");
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getMessage());
			throw ex;
			
		} finally {
		}	
	}
//add end --------------------------------------------------------------------------------------

	
	/**
	 * �����R�[�h�e�L�X�g�{�b�N�X�Z�b�^�[���Q�b�^�[
	 */
	public HankakuTextbox getCodeTextbox() {
		return codeTextbox;
	}
	public void setCodeTextbox(HankakuTextbox codeTextbox) {
		this.codeTextbox = codeTextbox;
	}
	
	/**
	 * ��БI���R���{�{�b�N�X�Z�b�^�[���Q�b�^�[
	 */
	public ComboBase getKaishaComb() {
		return kaishaComb;
	}
	public void setKaishaComb(ComboBase kaishaComb) {
		this.kaishaComb = kaishaComb;
	}
	
	/**
	 * �����I���R���{�{�b�N�X�Q�b�^�[
	 */
	public ComboBase getKojoComb() {
		return kojoComb;
	}
	
	/**
	 * �����f�[�^�Z�b�^�[���Q�b�^�[
	 */
	public BushoData getBushoData() {
		return BushoData;
	}
	public void setBushoData(BushoData bushoData) {
		BushoData = bushoData;
	}
	
	/**
	 * ��Ѓf�[�^�Z�b�^�[���Q�b�^�[
	 */
	public KaishaData getKaishaData() {
		return KaishaData;
	}
	public void setKaishaData(KaishaData kaishaData) {
		KaishaData = kaishaData;
	}
	
}