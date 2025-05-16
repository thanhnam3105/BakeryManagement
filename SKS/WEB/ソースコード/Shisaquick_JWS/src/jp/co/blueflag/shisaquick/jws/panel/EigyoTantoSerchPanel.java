package jp.co.blueflag.shisaquick.jws.panel;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableColumn;

import jp.co.blueflag.shisaquick.jws.base.*;
import jp.co.blueflag.shisaquick.jws.button.LinkButton;
import jp.co.blueflag.shisaquick.jws.cellrenderer.MiddleCellRenderer;
import jp.co.blueflag.shisaquick.jws.cellrenderer.TextFieldCellRenderer;
import jp.co.blueflag.shisaquick.jws.common.*;
import jp.co.blueflag.shisaquick.jws.data.DataCtrl;
import jp.co.blueflag.shisaquick.jws.label.*;
import jp.co.blueflag.shisaquick.jws.manager.*;
import jp.co.blueflag.shisaquick.jws.table.EigyoTantoSearchTable;
import jp.co.blueflag.shisaquick.jws.textbox.*;

/************************************************************************************
 * 
 * �yQP@00342�z
 * �c�ƒS�������T�u��� �p�l������p�̃N���X
 * 
 * @author TT.Nishigawa
 * @since 2011/2/15
 * 
 ************************************************************************************/
public class EigyoTantoSerchPanel extends PanelBase {
	private static final long serialVersionUID = 1L;

	private KaishaData KaishaData = new KaishaData();
	private BushoData BushoData = new BushoData();
	
	private DispTitleLabel dispTitleLabel;							//��ʃ^�C�g�����x��
	private HeaderLabel headerLabel;								//�w�b�_�\�����x��
	private LevelLabel levelLabel;										//���x���\�����x��
	private ItemLabel[] itemLabel;										//���ڃ��x��
	private TextboxBase TantoNmTextbox;							//�S���Җ��e�L�X�g�{�b�N�X
	private ComboBase kaishaComb;									//������ЃR���{�{�b�N�X
	private ComboBase bushoComb;									//���������R���{�{�b�N�X
	private ButtonBase[] button;										//�{�^��
	private EigyoTantoSearchTable EigyoTantoSearchTable;	//���앪�̓f�[�^�m�F�e�[�u��
	private LinkButton[] linkBtn;										//�����N�{�^��
	private ItemLabel dataLabel;										//�f�[�^���\�����x��
	private LinkButton linkPrevBtn;									//�O���ڃ����N�{�^��
	private LinkButton linkNextBtn;									//�����ڃ����N�{�^��
	
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
	
	private int intLinkMaxNum = 0;								//�����N�ő吔
	
	private XmlConnection xmlConnection;						//�w�l�k�ʐM
	private MessageCtrl messageCtrl;							//���b�Z�[�W����
	private ExceptionBase ex;										//�G���[����
	private XmlData xmlData;										//�w�l�k�f�[�^�ێ�
	
	private XmlData xmlRGEN2040;
	private XmlData xmlRGEN2060;
	private XmlData xmlJW900;
	
	int selectMaxNum = 0;
	int selectPage = 1;

	int selRow=0;

	private boolean isSelectKaishaCmb = false;	//��ЃR���{�{�b�N�X�I�������t���O
	//��ЃR���{�{�b�N�X�I�����̃C�x���g��
	private final String KAISHA_COMB_SELECT = "kaishaCombSelect";
	//�H��R���{�{�b�N�X�I�����̃C�x���g��
	private final String BUSHO_COMB_SELECT = "bushoCombSelect";
	//�����N�{�^���������̃C�x���g��
	private final String LINK_BTN_CLICK = "linkBtnClick";
	//�u�O�ցv�����N�{�^���������̃C�x���g��
	private final String LINK_PREV_BTN_CLICK = "linkPrevBtnClick";
	//�u���ցv�����N�{�^���������̃C�x���g��
	private final String LINK_NEXT_BTN_CLICK = "linkNextBtnClick";
	
	private ArrayList EigyoTantoAry = new ArrayList();
	
	//�����{�^�������׸�
	private boolean selectFg = false;

	/************************************************************************************
	 * 
	 * �R���X�g���N�^
	 * @param strOutput : ��ʃ^�C�g��
	 * @throws ExceptionBase
	 * 
	 ************************************************************************************/
	public EigyoTantoSerchPanel(String strOutput) throws ExceptionBase {
		//�X�[�p�[�N���X�̃R���X�g���N�^���Ăяo��
		super();

		try {
			//�p�l���̐ݒ�
			this.setPanel();
			
			//�R���g���[���z�u
			this.addControl(strOutput);
			
		}catch(ExceptionBase eb){
			
			throw eb;
			
		}catch(Exception e) {
			
			e.printStackTrace();
			
			//�G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("�c�ƒS�������T�u��ʃp�l���̃R���X�g���N�^�����s���܂����B");
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
			int dispWidth = 920;
			
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
			/// ���ڃ��x���̐ݒ�
			/// [0:�S���Җ�, 1:�������, 2:�����H��]
			///
			this.itemLabel = new ItemLabel[3];
			
			x = 5;
			y = 30;
			
			//0 : �S���Җ�
			this.itemLabel[0] = new ItemLabel();
			this.itemLabel[0].setText("�S���Җ�");
			this.itemLabel[0].setBounds(x,y,defLabelWidth,defHeight);

			//1 : �������
			x = this.itemLabel[0].getX();
			y = this.itemLabel[0].getY() + defHeight + 5;
			this.itemLabel[1] = new ItemLabel();
			this.itemLabel[1].setText("�������");
			this.itemLabel[1].setBounds(x,y,defLabelWidth,defHeight);
			
			//2 : ��������
			x = this.itemLabel[1].getX();
			y = this.itemLabel[1].getY() + this.itemLabel[1].getHeight() + 5;
			this.itemLabel[2] = new ItemLabel();
			this.itemLabel[2].setText("��������");
			this.itemLabel[2].setBounds(x,y,defLabelWidth,defHeight);
			
			//���ڃ��x�����p�l���ɒǉ�
			for ( i=0; i<this.itemLabel.length; i++ ) {
				this.add(this.itemLabel[i]);
			}
			
			///
			///�S���Җ��e�L�X�g�{�b�N�X
			///
			x = this.itemLabel[0].getX();
			y = this.itemLabel[0].getY();
			width = this.itemLabel[0].getWidth();
			this.TantoNmTextbox = new TextboxBase();
			this.TantoNmTextbox.setBounds(x+width, y, defTextWidth,defHeight);
			this.add(this.TantoNmTextbox);

			
			///
			///������ЃR���{�{�b�N�X
			///
			x = this.itemLabel[1].getX();
			y = this.itemLabel[1].getY();
			width = this.itemLabel[1].getWidth();
			this.kaishaComb = new ComboBase();
			this.kaishaComb.setBounds(x+width, y, defTextWidth,defHeight);
			this.add(this.kaishaComb);

			///
			///�����H��R���{�{�b�N�X
			///
			x = this.itemLabel[2].getX();
			y = this.itemLabel[2].getY();
			width = this.itemLabel[2].getWidth();
			this.bushoComb = new ComboBase();
			this.bushoComb.setBounds(x+width, y, defTextWidth,defHeight);
			this.add(this.bushoComb);
			
			///
			/// �{�^��
			/// [0:����, 1:�I��, 2:�I��]
			///
			this.button = new ButtonBase[3];	
			
			//0:����
			x = dispWidth - 200;
			width = 80;
			height = 38;
			y = this.itemLabel[1].getY() + defHeight-10;
			this.button[0] = new ButtonBase();
			this.button[0].setFont(new Font("Default", Font.PLAIN, 11));
			this.button[0].setBounds(x, y, width, height);
			this.button[0].setText("����");
			this.button[0].addActionListener(this.getActionEvent());
			this.button[0].setActionCommand("kensaku");
			
			//1:�I��
			y = this.itemLabel[1].getY() + defHeight-10;
			x = dispWidth - 100;
			this.button[1] = new ButtonBase();
			this.button[1].setFont(new Font("Default", Font.PLAIN, 11));
			this.button[1].setBounds(x, y, width, height);
			this.button[1].setText("�I��");
			
			//2:�I��
			width = 80;
			y = 450;
			x = dispWidth - 100;
			this.button[2] = new ButtonBase();
			this.button[2].setFont(new Font("Default", Font.PLAIN, 11));
			this.button[2].setBounds(x, y, width, height);
			this.button[2].setText("�I��");
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
			this.dataLabel.setBounds(0, 400, 800, 20);
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
			
			this.linkBtn[11].setBounds(this.linkBtn[9].getX() + this.linkBtn[9].getWidth() + this.linkBtn[9].getWidth()
					,this.linkBtn[9].getY(),80,this.linkBtn[9].getHeight());
			
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
			
			//�H��R���{�{�b�N�X�ɃC�x���g��ݒ�
			this.bushoComb.addActionListener(this.getActionEvent());
			this.bushoComb.setActionCommand(this.BUSHO_COMB_SELECT);
			
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
			this.ex.setStrErrmsg("�c�ƒS�������T�u��ʃp�l���̃R���g���[���z�u���������s���܂����B");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
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
				{ this.TantoNmTextbox },	//���O
				{ this.kaishaComb, this.bushoComb },				//��ЃR���{, �H��R���{
				this.button,										//�{�^��
				{ this.linkPrevBtn },								//�O�փ����N�{�^�� 
				this.linkBtn,										//�����N�{�^��
				{ this.linkNextBtn }								//���փ����N�{�^��
		};
		return comp;
	}
	
	
	/************************************************************************************
	 * 
	 * �c�ƒS�������T�u��ʃe�[�u������������
	 * @throws ExceptionBase 
	 * 
	 ************************************************************************************/
	private void addTable() throws ExceptionBase {
		try{
			int intRow = 0;			//�s��
			int intCol = 6;		//��
			Object[] columnNm = new Object[intCol];		//��
			int[] columnWidth = new int[intCol];				//��

			//�񖼂̐ݒ�
			columnNm[0]  = "No";
			columnNm[1]  = "�S���Җ�";
			columnNm[2]  = "�������";
			columnNm[3]  = "��������";
			columnNm[4]  = "�{������";
			columnNm[5]  = "�S����i";

			//�񕝂̐ݒ�
			columnWidth[0] = 20;		//�sNo
			columnWidth[1] = 200;	//�S���Җ�
			columnWidth[2] = 200;	//�������
			columnWidth[3] = 200;	//��������
			columnWidth[4] = 70;		//�{������
			columnWidth[5] = 200;	//�S����i
			
			//�e�[�u���̃C���X�^���X����
			this.EigyoTantoSearchTable = new EigyoTantoSearchTable(intRow, intCol, columnNm, columnWidth);
			this.EigyoTantoSearchTable.getScroll().setBounds(5, 120, 910, 275);
			this.add(this.EigyoTantoSearchTable.getScroll());
			
			//�}�E�X�C�x���g�ݒ�
			this.EigyoTantoSearchTable.getMainTable().addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					//�I���s�̍s�ԍ����擾���܂�
					int ii = EigyoTantoSearchTable.getMainTable().getSelectedRow();
					selRow = ii;
				}
			});
			
		}catch(ExceptionBase eb){
			throw eb;
			
		}catch(Exception e){
			//�G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("�c�ƒS�������T�u��ʃp�l���̃e�[�u�����������������s���܂����B");
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
			
			//�S���Җ��e�L�X�g�{�b�N�X
			TantoNmTextbox.setText(null);
			
			//------------------------------- �e�[�u�������� ---------------------------------
			dispClear();
			
			//------------------------ ��ЃR���{�{�b�N�X�̏����ݒ� ----------------------------
			this.isSelectKaishaCmb = false;
			conJW610();
			this.setKaishaCmb();
			
			//------------------------ �����R���{�{�b�N�X�̏����ݒ� ----------------------------
			this.isSelectKaishaCmb = true;
			this.selectKaishaComb();

			//---------------------------- �����N�{�^���̏����ݒ� ------------------------------
			for(int i=0; i<this.linkBtn.length-2; i++) {
				this.linkBtn[i].setEnabled(false);
			}
			//�u���ցv�E�u�O�ցv�����N�{�^���̏����ݒ�
			this.linkNextBtn.setEnabled(false);
			this.linkPrevBtn.setEnabled(false);
			
		}catch(ExceptionBase eb){
			throw eb;
			
		}catch(Exception e){
			//�G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("�c�ƒS�������T�u��ʃp�l���̃e�[�u�����������������s���܂����B");
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
			TableBase mt = EigyoTantoSearchTable.getMainTable();
			
			//�����_�������i������j
			MiddleCellRenderer md = new MiddleCellRenderer();
			
			//�����_�������i���l�j
			MiddleCellRenderer nmd = new MiddleCellRenderer();

			for(int i=0; i<EigyoTantoAry.size(); i++){
				
				//�f�[�^�}��
				EigyoTantoData EigyoTantoData = (EigyoTantoData)EigyoTantoAry.get(i);
				
				//�s�ǉ�
				mt.tableInsertRow(i);
				
				//�����_���ݒ�i������j
				TextboxBase comp = new TextboxBase();
				comp.setBackground(Color.WHITE);
				
				//�����_���ݒ�i���l�j
				NumelicTextbox ncomp = new NumelicTextbox();
				ncomp.setBackground(Color.WHITE);
								
				//���X�g�����̊i�[
				max_row = EigyoTantoData.getIntMaxRow();
				row_num = EigyoTantoData.getIntListRowMax();
				selectMaxNum = max_row;
				
				//�f�[�^�ݒ�
				//No
				mt.setValueAt(EigyoTantoData.getNo_gyo(), i, 0);
				//�S���Җ�
				mt.setValueAt(EigyoTantoData.getNm_user(), i, 1);
				//�������
				mt.setValueAt(EigyoTantoData.getNm_kaisha(), i, 2);
				//��������
				mt.setValueAt(EigyoTantoData.getNm_busho(), i, 3);
				//�{������
				mt.setValueAt(EigyoTantoData.getHonbukengen(), i, 4);
				//�S����i
				mt.setValueAt(EigyoTantoData.getNm_josi(), i, 5);
				

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

				//�sNo
				if(i == 0){
					//�����񃌃��_���ݒ�
					column.setCellRenderer(md);
				}
				//�S���Җ�
				else if(i == 1){
					//�����񃌃��_���ݒ�
					column.setCellRenderer(md);
				}
				//�������
				else if(i == 2){
					//�����񃌃��_���ݒ�
					column.setCellRenderer(md);			
				}
				//��������
				else if(i == 3){
					//�����񃌃��_���ݒ�
					column.setCellRenderer(md);			
				}
				//�{������
				else if(i == 4){
					//�����񃌃��_���ݒ�
					column.setCellRenderer(md);				
				}
				//�S����i
				else if(i == 5){
					//�����񃌃��_���ݒ�
					column.setCellRenderer(md);
				}
			}
			initLink(max_row, row_num);
			
		}catch(Exception e){
			//�G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("�c�ƒS�������T�u��ʃp�l���̃e�[�u���\�����������s���܂����B");
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

		//�����N�{�^���̏����ݒ�
		for(int i=0; i<this.linkBtn.length; i++) {
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
			if ( intMaxRow > (intListRowMax*10) ) {	
				//[����]�E[�O��]�����N�{�^�����g�p�ɐݒ�
				this.linkPrevBtn.setEnabled(true);
				this.linkNextBtn.setEnabled(true);
			}
		}
	}
	
	/************************************************************************************
	 * 
	 *   ���������p�@XML�ʐM�����iJW900�j
	 *    : ��������XML�f�[�^�ʐM�iJW900�j���s��
	 *   @author TT nishigawa
	 *   
	 ************************************************************************************/
	public void conJW900(int page) throws ExceptionBase{
		try{
			
			//----------------------------- ���M�p�����[�^�i�[  -------------------------------
			String strUser = DataCtrl.getInstance().getParamData().getDciUser().toString();
			String strKaishaCd = this.getSelectKaishaCd();
			String strBushoCd = this.getSelectKojoCd();
			
			//----------------------------- ���MXML�f�[�^�쐬  ------------------------------
			xmlJW900 = new XmlData();
			ArrayList arySetTag = new ArrayList();
			
			//--------------------------------- Root�ǉ�  ---------------------------------
			xmlJW900.AddXmlTag("","JW900");
			arySetTag.clear();
			
			//--------------------------- �@�\ID�ǉ��iUSEERINFO�j  --------------------------
			xmlJW900.AddXmlTag("JW900", "USERINFO");
			//�@�e�[�u���^�O�ǉ�
			xmlJW900.AddXmlTag("USERINFO", "table");
			//�@���R�[�h�ǉ�
			arySetTag.add(new String[]{"kbn_shori", "3"});
			arySetTag.add(new String[]{"id_user",strUser});
			xmlJW900.AddXmlTag("table", "rec", arySetTag);
			//�z�񏉊���
			arySetTag.clear();
			
			//------------------------ �@�\ID�ǉ��i�S���Ҍ����j  ----------------------------
			xmlJW900.AddXmlTag("JW900", "FGEN2110");
			//�@�e�[�u���^�O�ǉ�
			xmlJW900.AddXmlTag("FGEN2110", "table");
			
			//�@���R�[�h�ǉ�
			String[] id_user = new String[]{"id_user", ""};
			String[] nm_genryo = new String[]{"nm_user", checkNull(TantoNmTextbox.getText())};
			String[] cd_kaisha = new String[]{"cd_kaisha",strKaishaCd};
			String[] cd_busho = new String[]{"cd_busho", strBushoCd};
			String[] num_selectRow = new String[]{"no_page", Integer.toString(page)};
			String[] kbn_shori = new String[]{"kbn_shori", "1"};

			arySetTag.add(id_user);
			arySetTag.add(nm_genryo);
			arySetTag.add(cd_kaisha);
			arySetTag.add(cd_busho);
			arySetTag.add(num_selectRow);
			arySetTag.add(kbn_shori);
			
			xmlJW900.AddXmlTag("table", "rec", arySetTag);
			arySetTag.clear();
			
			//----------------------------------- XML���M  ----------------------------------
//			System.out.println("\nJW720���MXML===============================================================");
//			xmlJW900.dispXml();
			XmlConnection xcon = new XmlConnection(xmlJW900);
			xcon.setStrAddress(DataCtrl.getInstance().getJnlpConnect().getStrAddressAjax());
			xcon.XmlSend();
			
			//----------------------------------- XML��M  ----------------------------------
			xmlJW900 = xcon.getXdocRes();
//			System.out.println("\nJW720��MXML===============================================================");
//			xmlJW900.dispXml();
//			xmlJW720.dispXml();
			
			//�e�X�gXML�f�[�^
			//xmlJW720 = new XmlData(new File("src/main/JW720.xml"));
			
			//------------------------------- Result�f�[�^�`�F�b�N -----------------------------
			DataCtrl.getInstance().getResultData().setResultData(xmlJW900);
			if ( !DataCtrl.getInstance().getResultData().isReturnFlgCheck() ) {
				
				//��ʏ��N���A
				dispClear();
				
				//�f�[�^���\�����x���N���A
				this.dataLabel.setText("");
				
				throw new ExceptionBase();
				
			}
			else{
				XmlData xdtData = xmlJW900;
				
				EigyoTantoAry = new ArrayList();
				 
				 try{
					//�@XML�f�[�^�i�[
					String strKinoId = "FGEN2110";
					
					//�S�̔z��擾
					ArrayList mateData = xdtData.GetAryTag(strKinoId);
					
					//�@�\�z��擾
					ArrayList kinoData = (ArrayList)mateData.get(0);
					
					//�e�[�u���z��擾
					ArrayList tableData = (ArrayList)kinoData.get(1);

					//���R�[�h�擾
					for(int i=1; i<tableData.size(); i++){
					//�@�P���R�[�h�擾
					ArrayList recData = ((ArrayList)((ArrayList)tableData.get(i)).get(0));
					
						//�c�ƒS���҃f�[�^����
						EigyoTantoData EigyoTantoData = new EigyoTantoData();
						
						//�c�ƒS���҃f�[�^�֊i�[
						for(int j=0; j<recData.size(); j++){
							
							//�@���ږ��擾
							String recNm = ((String[])recData.get(j))[1];
							//�@���ڒl�擾
							String recVal = ((String[])recData.get(j))[2];
							
							/*****************�����f�[�^�֒l�Z�b�g*********************/
							// �sNo
							if(recNm == "no_row"){
								EigyoTantoData.setNo_gyo(recVal);
							}
							// ���[�U�[ID
							if(recNm == "id_user"){
								EigyoTantoData.setId_user(recVal);
							}
							// ���[�U�[����
							if(recNm == "nm_user"){
								EigyoTantoData.setNm_user(recVal);
							}
							// ���CD
							if(recNm == "cd_kaisha"){
								EigyoTantoData.setCd_kaisha(recVal);
							}
							// ��Ж�
							if(recNm == "nm_kaisha"){
								EigyoTantoData.setNm_kaisha(recVal);
							}
							// ����CD
							if(recNm == "cd_busho"){
								EigyoTantoData.setCd_busho(recVal);
							}
							// ��������
							if(recNm == "nm_busho"){
								EigyoTantoData.setNm_busho(recVal);
							}
							// �{������
							if(recNm == "kbn_honbu"){
								EigyoTantoData.setHonbukengen(recVal);
							}
							// ��iID
							if(recNm == "id_josi"){
								EigyoTantoData.setId_josi(recVal);
							}
							// ��i��
							if(recNm == "nm_josi"){
								EigyoTantoData.setNm_josi(recVal);
							}
							// ������
							if(recNm == "max_row"){
								EigyoTantoData.setIntMaxRow(checkNullInt(recVal));
							}
							// �œ��\������
							if(recNm == "list_max_row"){
								EigyoTantoData.setIntListRowMax(checkNullInt(recVal));
							}
						}
						//�@�c�ƒS���z��֒ǉ�
						this.EigyoTantoAry.add(EigyoTantoData);
					}
				}catch(Exception e){
					 
				}
				
				//�f�[�^���\���ݒ�
				//���X�g�\�������y�эő匏�����擾
				EigyoTantoData EigyoTantoData = (EigyoTantoData)EigyoTantoAry.get(0);
				int intListRowMax = EigyoTantoData.getIntListRowMax();
				int intMaxRow = EigyoTantoData.getIntMaxRow();
				
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
			ex.setStrErrmsg("�c�ƒS�������T�u��ʃp�l���̌��������ʐM���������s���܂���");
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getMessage());
			throw ex;
			
		}finally{
		}
	}
		
	/**
	 * �󕶎��`�F�b�N�iInt�j
	 */
	public int checkNullInt(String val){
		int ret = -1;
		try{
			//�l���󕶎��łȂ��ꍇ
			if(!val.equals("")){
				ret = Integer.parseInt(val);
			}
		}catch(Exception e){
			
		}finally{
			
		}
		return ret;
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
						
						//--------------------- �I�� �{�^�� �N���b�N���̏��� -------------------------
						if(event_name == "kensaku"){
						
							selectPage = 1;
							
							//���������iJW900�j
							conJW900(selectPage);
							dispTable();
							selectFg = true;

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
							
						
						}
						//---------------------- ��ЃR���{�{�b�N�X�I���� --------------------------
						else if ( event_name.equals(KAISHA_COMB_SELECT)) {
							selectKaishaComb();
							
						}
						//---------------------- �H��R���{�{�b�N�X�I���� --------------------------
						else if ( event_name.equals(BUSHO_COMB_SELECT)) {
							selectBushoComb();
							
						}
						//------------------------- �����N�{�^�������� -----------------------------
						else if ( event_name.equals(LINK_BTN_CLICK) ) {
							
							ButtonBase link = (ButtonBase)e.getSource();
							String strPage = link.getText();
							//�f�[�^�}��
							EigyoTantoData EigyoTantoData = (EigyoTantoData)EigyoTantoAry.get(0);
							int intRowNum = EigyoTantoData.getIntListRowMax();
							
							
							//�����{�^������
							if(selectFg){
								if(strPage.equals("�ŏ���")){
									selectPage = 1;
									conJW900(selectPage);
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
									conJW900(selectPage);
									dispTable();
									
									//�ŏI�y�[�W�܂Ői�߂�
									for ( int i=0; i<(selectMaxNum / (intRowNum*10)); i++ ) {
										clickLinkNextBtn(linkNextBtn);
										
									}
									
								}else{
									selectPage = Integer.parseInt(strPage);
									conJW900(selectPage);
									dispTable();
									
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
						ex.setStrErrmsg("�c�ƒS�������T�u��ʃp�l����ActionListener�C�x���g�����s���܂���");
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
			ex.setStrErrmsg("�c�ƒS�������T�u��ʃp�l���̉�ЃR���{�{�b�N�X�ݒ菈�������s���܂���");
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
			this.bushoComb.addItem("");
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
			ex.setStrErrmsg("�c�ƒS�������T�u��ʃp�l���̕����R���{�{�b�N�X�ݒ菈�������s���܂���");
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
			ex.setStrErrmsg("�c�ƒS�������T�u��ʃp�l���̑I����ЃR�[�h�擾���������s���܂���");
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
			
			//�@���MXML�f�[�^�쐬
			xmlRGEN2040 = new XmlData();
			ArrayList arySetTag = new ArrayList();
			
			//�@Root�ǉ�
			xmlRGEN2040.AddXmlTag("","RGEN2040");
			arySetTag.clear();
			
			//�@�@�\ID�ǉ�
			xmlRGEN2040.AddXmlTag("RGEN2040", "USERINFO");
			//�@�e�[�u���^�O�ǉ�
			xmlRGEN2040.AddXmlTag("USERINFO", "table");	
			//�@���R�[�h�ǉ�
			String[] kbn_shori = {"kbn_shori", "3"};
			String[] id_user = {"id_user",strUser};
			arySetTag.add(kbn_shori);
			arySetTag.add(id_user);
			xmlRGEN2040.AddXmlTag("table", "rec", arySetTag);
			arySetTag.clear();

			//�@�y���������z �@�\ID�ǉ�
			xmlRGEN2040.AddXmlTag("RGEN2040", "FGEN2090");
			//�@�e�[�u���^�O�ǉ�
			xmlRGEN2040.AddXmlTag("FGEN2090", "table");
			String[] nulldata = {"data",""};
			arySetTag.add(nulldata);
			xmlRGEN2040.AddXmlTag("table", "rec", arySetTag);
			arySetTag.clear();
			
//			xmlJW610.dispXml();
			//�@XML���M
			XmlConnection xmlConnection = new XmlConnection(xmlRGEN2040);
			xmlConnection.setStrAddress(DataCtrl.getInstance().getJnlpConnect().getStrAddressAjax());
			xmlConnection.XmlSend();
			//�@XML��M
			xmlRGEN2040 = xmlConnection.getXdocRes();
//			xmlJW610.dispXml();
			
			//�@�e�X�gXML�f�[�^
			//xmlJW610 = new XmlData(new File("src/main/JW610.xml"));
			
			// ��Ѓf�[�^
			KaishaData.setKaishaData_Eigyo(xmlRGEN2040);

			// Result�f�[�^
			DataCtrl.getInstance().getResultData().setResultData(xmlRGEN2040);
			if ( !DataCtrl.getInstance().getResultData().isReturnFlgCheck() ) {
				throw new ExceptionBase();
			}
			
		} catch (ExceptionBase e) {
			throw e;
			
		} catch (Exception e) {
			//�G���[�ݒ�
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			ex.setStrErrmsg("�c�ƒS�������T�u��ʃp�l���̉�ЃR���{�{�b�N�X�l�擾�ʐM���������s���܂���");
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
			ex.setStrErrmsg("�c�ƒS�������T�u��ʃp�l���̉�ЃR���{�{�b�N�X�I�������������s���܂���");
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getMessage());
			throw ex;
			
		} finally {
		}	
	}

	/************************************************************************************
	 * 
	 * �H��R���{�{�b�N�X�I��������
	 * @throws ExceptionBase 
	 * 
	 ************************************************************************************/
	private void selectBushoComb() throws ExceptionBase {
		try {
			if ( this.bushoComb.isValid() ) {		//���d������h��
				
			}
			
		} catch (Exception e) {
			//�G���[�ݒ�
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			ex.setStrErrmsg("�c�ƒS�������T�u��ʃp�l���̍H��R���{�{�b�N�X�I�������������s���܂���");
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getMessage());
			throw ex;
			
		} finally {
		}	
	}
	
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
			ex.setStrErrmsg("�c�ƒS�������T�u��ʃp�l���̑I���H��R�[�h�擾���������s���܂���");
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
	 * �yRGEN2060�z ��ЃR���{�{�b�N�X������ ���MXML�f�[�^�쐬
	 * @param strKaishaCd : ��ЃR�[�h
	 * 
	 ************************************************************************************/
	private void conJW620(String strKaishaCd) throws ExceptionBase{
		try{
			
			//�@���M�p�����[�^�i�[
			String strUser = DataCtrl.getInstance().getParamData().getDciUser().toString();
			
			//�@���MXML�f�[�^�쐬
			xmlRGEN2060 = new XmlData();
			ArrayList arySetTag = new ArrayList();
			
			//�@Root�ǉ�
			xmlRGEN2060.AddXmlTag("","RGEN2060");
			arySetTag.clear();
			
			//�@�@�\ID�ǉ�
			xmlRGEN2060.AddXmlTag("RGEN2060", "USERINFO");
			//�@�e�[�u���^�O�ǉ�
			xmlRGEN2060.AddXmlTag("USERINFO", "table");	
			//�@���R�[�h�ǉ�
			String[] kbn_shori = {"kbn_shori", "3"};
			String[] id_user = {"id_user",strUser};
			arySetTag.add(kbn_shori);
			arySetTag.add(id_user);
			xmlRGEN2060.AddXmlTag("table", "rec", arySetTag);
			arySetTag.clear();

			//�@�y���������z �@�\ID�ǉ�
			xmlRGEN2060.AddXmlTag("RGEN2060", "FGEN2070");

			//�@�e�[�u���^�O�ǉ�
			xmlRGEN2060.AddXmlTag("FGEN2070", "table");
			//�@���R�[�h�ǉ�
			String[] cd_kaisha = new String[]{"cd_kaisha",strKaishaCd};
						
			String[] eigyo_kengen = new String[]{"eigyo_kengen","0"};
						
			arySetTag.add(cd_kaisha);
						
			arySetTag.add(eigyo_kengen);
						
			xmlRGEN2060.AddXmlTag("table", "rec", arySetTag);
			arySetTag.clear();
			
//			xmlJW620.dispXml();
			//�@XML���M
			XmlConnection xmlConnection = new XmlConnection(xmlRGEN2060);
			xmlConnection.setStrAddress(DataCtrl.getInstance().getJnlpConnect().getStrAddressAjax());
			xmlConnection.XmlSend();
			//�@XML��M
			xmlRGEN2060 = xmlConnection.getXdocRes();
//			xmlJW620.dispXml();
			
			//�����f�[�^
			BushoData.setBushoData_Eigyo(xmlRGEN2060);

			// Result�f�[�^
			DataCtrl.getInstance().getResultData().setResultData(xmlRGEN2060);
			if ( !DataCtrl.getInstance().getResultData().isReturnFlgCheck() ) {
				throw new ExceptionBase();
				
			}
			
		}catch(ExceptionBase e){
			throw e;
			
		}catch(Exception e){
			//�G���[�ݒ�
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			ex.setStrErrmsg("�c�ƒS�������T�u��ʃp�l���̉�ЃR���{�{�b�N�X�����ʐM���������s���܂���");
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getMessage());
			throw ex;
			
		}finally{
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
			if ( EigyoTantoSearchTable.getMainTable().getRowCount() > 0 ) {
				for ( int i=EigyoTantoSearchTable.getMainTable().getRowCount()-1; i>-1; i-- ) {
					EigyoTantoSearchTable.getMainTable().tableDeleteRow(i);
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
			ex.setStrErrmsg("�c�ƒS�������T�u��ʃp�l���̉�ʏ��������������s���܂���");
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
			ex.setStrErrmsg("�c�ƒS�������T�u��ʃp�l����<<�����N�{�^�����������������s���܂���");
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
			ex.setStrErrmsg("�c�ƒS�������T�u��ʃp�l����>>�����N�{�^�����������������s���܂���");
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getMessage());
			throw ex;
			
		} finally {
			
		}
		
	}
	
	
	public ArrayList getEigyoTantoAry() {
		return EigyoTantoAry;
	}
	public void setEigyoTantoAry(ArrayList eigyoTantoAry) {
		EigyoTantoAry = eigyoTantoAry;
	}
	public EigyoTantoSearchTable getEigyoTantoSearchTable() {
		return EigyoTantoSearchTable;
	}
	public void setEigyoTantoSearchTable(EigyoTantoSearchTable eigyoTantoSearchTable) {
		EigyoTantoSearchTable = eigyoTantoSearchTable;
	}
	
}