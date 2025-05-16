package jp.co.blueflag.shisaquick.jws.panel;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.DefaultCellEditor;
import javax.swing.JRadioButton;
import javax.swing.table.TableCellEditor;

import jp.co.blueflag.shisaquick.jws.celleditor.MiddleCellEditor;
import jp.co.blueflag.shisaquick.jws.common.ButtonBase;
import jp.co.blueflag.shisaquick.jws.common.ComboBase;
import jp.co.blueflag.shisaquick.jws.common.ExceptionBase;
import jp.co.blueflag.shisaquick.jws.common.PanelBase;
import jp.co.blueflag.shisaquick.jws.common.TableBase;
import jp.co.blueflag.shisaquick.jws.data.DataCtrl;
import jp.co.blueflag.shisaquick.jws.label.DispTitleLabel;
import jp.co.blueflag.shisaquick.jws.label.HeaderLabel;
import jp.co.blueflag.shisaquick.jws.label.ItemLabel;
import jp.co.blueflag.shisaquick.jws.label.LevelLabel;
import jp.co.blueflag.shisaquick.jws.manager.MessageCtrl;
import jp.co.blueflag.shisaquick.jws.table.PrototypeListTable;

/*************************************************************************************
 * 
 * �yA05-07�z �����ǉ��p�l������p�̃N���X
 * 
 * @author TT.katayama
 * @since 2009/04/03
 * 
 **********************************************************************************/
public class PrototypeListPanel extends PanelBase {
	private static final long serialVersionUID = 1L;

	private DispTitleLabel dispTitleLabel;				//��ʃ^�C�g�����x��
	private HeaderLabel headerLabel;					//���x���\�����x��
	private LevelLabel levelLabel;							//�w�b�_�\�����x��
	
	private ItemLabel[] itemLabel;							//���ڃ��x��
	private JRadioButton[] radioButton;					//���W�I�{�^��
	private PrototypeListTable prototypeListTable;	//�����e�[�u��
	private ButtonBase[] button;							//�{�^��
	private ButtonGroup copyCheck = new ButtonGroup();
	
	private MessageCtrl messageCtrl;					//���b�Z�[�W����
	private ExceptionBase ex;								//�G���[����
	
	/**********************************************************************************
	 * 
	 * �R���X�g���N�^
	 * 
	 **********************************************************************************/
	public PrototypeListPanel(String strOutput) throws ExceptionBase {
		//�X�[�p�[�N���X�̃R���X�g���N�^���Ăяo��
		super();

		try {
			//�P�D�p�l���̐ݒ�
			this.setPanel();
			
			//�Q�D�R���g���[���̔z�u
			this.addControl(strOutput);
			
		} catch(Exception e) {
			e.printStackTrace();
			//�G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("�����ǉ��p�l�����������s���܂����B");
			this.ex.setStrErrShori("PrototypeListPanel");
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw this.ex;
			
		} finally {
			
		}
		
	}

	/**********************************************************************************
	 * 
	 * �p�l���ݒ�
	 * 
	 **********************************************************************************/
	private void setPanel() {
		this.setLayout(null);
		this.setBackground(Color.WHITE);
	}

	/**********************************************************************************
	 * 
	 * �R���g���[���z�u
	 * @param strTitle : ��ʃ^�C�g��
	 * @throws ExceptionBase 
	 * 
	 **********************************************************************************/
	public void addControl(String strTitle) throws ExceptionBase {
		int i, x, y, width, height;
		int dispWidth = 620;
		int defLabelWidth = 60;
		int defRadioWidth = 60;
		int defButtonWidth = 80;
		int defButtonHeight = 38;
		int defHeight = 15;
	
		//-------------------------- �^�C�g�����x���ݒ� -----------------------------------
		this.dispTitleLabel = new DispTitleLabel();
		this.dispTitleLabel.setText(strTitle);
		this.add(this.dispTitleLabel);
		
		//2010.09.09 sakai change --->
		////------------------- ���ڃ��x���̐ݒ�[0:�v�Z, 1:�S�H��] ----------------------------
		//------------------- ���ڃ��x���̐ݒ�[0:�S�H��, 1:�v�Z] ----------------------------
		//2010.09.09 sakai change <---
		this.itemLabel = new ItemLabel[2];
		x = 5;
		y = 30;
		width = defLabelWidth;
		height = defHeight;
		//2010.09.09 sakai change --->
		//this.itemLabel[0] = new ItemLabel();
		//this.itemLabel[0].setText("�v�Z");
		//this.itemLabel[0].setBounds(x,y,width,height);
		////�S�H��
		//x += defLabelWidth + defRadioWidth + 5;
		//this.itemLabel[1] = new ItemLabel();
		//this.itemLabel[1].setText("�S�H��");
		//this.itemLabel[1].setBounds(x,y,width,height);
		//�S�H��
		this.itemLabel[0] = new ItemLabel();
		this.itemLabel[0].setText("�S�H��");
		this.itemLabel[0].setBounds(x,y,width,height);
		//�v�Z
		x += defLabelWidth + defRadioWidth + 5;
		this.itemLabel[1] = new ItemLabel();
		this.itemLabel[1].setText("�v�Z");
		this.itemLabel[1].setBounds(x,y,width,height);
		//2010.09.09 sakai change <---
		//�p�l���ɒǉ�
		for ( i=0; i<this.itemLabel.length; i++ ) {
			this.add(this.itemLabel[i]);
		}
				
		//2010.09.09 sakai change --->
		////-------------------- ���W�I�{�^���̐ݒ�[0:�v�Z, 1:�S�H��] --------------------------
		//-------------------- ���W�I�{�^���̐ݒ�[0:�S�H��, 1:�v�Z] --------------------------
		//2010.09.09 sakai change <---
		this.radioButton = new JRadioButton[2];
		x = this.itemLabel[0].getX() + defLabelWidth;
		y = this.itemLabel[0].getY();
		width = defRadioWidth;
		height = defHeight;
		//2010.09.09 sakai change --->
		////�v�Z
		//this.radioButton[0] = new JRadioButton();
		//this.radioButton[0].setBounds(x,y,width,height);
		//this.radioButton[0].setSelected(true);
		//this.radioButton[0].addActionListener(this.getActionEvent());
		//this.radioButton[0].setActionCommand("keisan");
		//copyCheck.add(this.radioButton[0]);
		////�S�H��
		//x = this.itemLabel[1].getX() + defLabelWidth;
		//y = this.itemLabel[1].getY();
		//this.radioButton[1] = new JRadioButton();
		//this.radioButton[1].setBounds(x,y,width,height);
		//this.radioButton[1].addActionListener(this.getActionEvent());
		//this.radioButton[1].setActionCommand("allKotei");
		//copyCheck.add(this.radioButton[1]);
		//�S�H��
		this.radioButton[0] = new JRadioButton();
		this.radioButton[0].setBounds(x,y,width,height);
		this.radioButton[0].setSelected(true);
		this.radioButton[0].addActionListener(this.getActionEvent());
		this.radioButton[0].setActionCommand("allKotei");
		copyCheck.add(this.radioButton[0]);
		//�v�Z
		x = this.itemLabel[1].getX() + defLabelWidth;
		y = this.itemLabel[1].getY();
		this.radioButton[1] = new JRadioButton();
		this.radioButton[1].setBounds(x,y,width,height);
		this.radioButton[1].addActionListener(this.getActionEvent());
		this.radioButton[1].setActionCommand("keisan");
		copyCheck.add(this.radioButton[1]);
		//2010.09.09 sakai change <---
		//�p�l���ɒǉ�
		for ( i=0; i<this.radioButton.length; i++ ) {
			this.radioButton[i].setBackground(Color.WHITE);
			this.add(this.radioButton[i]);
		}
		
		//---------------------------- �e�[�u���̐ݒ� ------------------------------------
		this.addTable();
		
		//----------------- �{�^���̐ݒ� [0:��ǉ�, 1:�̗p, 2:�L�����Z��] ----------------------
		this.button = new ButtonBase[3];
		width = defButtonWidth;
		height = defButtonHeight;
		//��ǉ�
		x = dispWidth - width - width - 120;
		y = this.prototypeListTable.getLeftHeaderTable().getY() + this.prototypeListTable.getLeftHeaderTable().getHeight() + 30;
		this.button[0] = new ButtonBase();
		this.button[0].setFont(new Font("Default", Font.PLAIN, 11));
		this.button[0].setBounds(x, y, width, height);
		this.button[0].setText("��ǉ�");
		this.button[0].addActionListener(this.getActionEvent());
		this.button[0].setActionCommand("addRetu");
		//�̗p
		x = dispWidth - width - 120;
		this.button[1] = new ButtonBase();
		this.button[1].setFont(new Font("Default", Font.PLAIN, 11));
		this.button[1].setBounds(x, y, width, height);
		this.button[1].setText("�̗p");
		//�L�����Z��
		x = dispWidth - 120;
		this.button[2] = new ButtonBase();
		this.button[2].setFont(new Font("Default", Font.PLAIN, 11));
		this.button[2].setBounds(x, y, width + 20, height);
		this.button[2].setText("�L�����Z��");
		//�p�l���ɒǉ�
		for ( i=0; i<this.button.length; i++ ) {
			this.add(this.button[i]);
		}
	}
	
	/**********************************************************************************
	 * 
	 * �p�l���փe�[�u���z�u
	 * @throws ExceptionBase 
	 * 
	 **********************************************************************************/
	public void addTable() throws ExceptionBase { 
		
		//----------------------------- �����ݒ� ----------------------------------------
		//�e��ϐ��錾
		int i;
		int x,y, width,height;
		int hoseiValue = 17;
		//�e�[�u���̃C���X�^���X����
		this.prototypeListTable = new PrototypeListTable();
		
		
		//--------------------- �����w�b�_�[�e�[�u���̃T�C�Y ------------------------------
		x = 5;
		y = this.itemLabel[0].getY() + this.itemLabel[0].getHeight() + 5;
		width = 100;
		height = 230 - 17;
		this.prototypeListTable.getLeftHeaderTable().setBounds(x,y, width,height);

		
		//------------------------ �w�b�_�[�e�[�u���̃T�C�Y -------------------------------
		x = 104;
		y = this.itemLabel[0].getY() + this.itemLabel[0].getHeight() + 5;
		width = 500;
		height = 20;
		this.prototypeListTable.getScroll()[0].setBounds(x, y, width - hoseiValue, height);
		
		//--------------------- �R���{�{�b�N�X�e�[�u���̃T�C�Y -----------------------------
		y += height - 2;
		height = 20;
		this.prototypeListTable.getScroll()[1].setBounds(x, y, width - hoseiValue, height);
		
		//------------------------- ���C���e�[�u���̃T�C�Y --------------------------------
		y += height - 1;
		height = 193;
		this.prototypeListTable.getScroll()[2].setBounds(x, y, width, height);
		
		//----------------------------- �p�l���ɒǉ� -------------------------------------
		for ( i=0; i<this.prototypeListTable.getScroll().length; i++ ) {
			this.add(this.prototypeListTable.getScroll()[i]);
		}
		this.add(this.prototypeListTable.getLeftHeaderTable());
		
	}
	
	/************************************************************************************
	 * 
	 *   ActionListener�C�x���g
	 *    : �����R�s�[��ʂł̃{�^���������̏������L���b�`����
	 *   @author TT nishigawa
	 *   
	 ************************************************************************************/
	private ActionListener getActionEvent(){
		return (
				new ActionListener() {
					public void actionPerformed(ActionEvent e){
						try {
							//�C�x���g���擾
							String event_name = e.getActionCommand();
							
							//------------------------- ��ǉ� ------------------------------------
							if ( event_name == "addRetu") {
								
								//�I�����e�[�u���𖢑I���ɐݒ�
								TableBase mainTable = prototypeListTable.getMainTable();
								TableCellEditor tce = mainTable.getCellEditor();
								if(tce != null){
									mainTable.getCellEditor().stopCellEditing();
								}
								
								//�H�����`�F�b�N
								int dataRows = DataCtrl.getInstance().getTrialTblData().getIntMaxKotei();
								int tableRows = prototypeListTable.getMaxKotei();
								boolean chkAddRetu = true;
								if(dataRows != tableRows){
									chkAddRetu = false;
								}
								
								//����񐔃`�F�b�N
								int dataCols = DataCtrl.getInstance().getTrialTblData().SearchShisakuRetuData(0).size();
								MiddleCellEditor mce = (MiddleCellEditor)prototypeListTable.getComboTable().getCellEditor(0, 0);
								DefaultCellEditor dce = (DefaultCellEditor)mce.getTableCellEditor(0);
								ComboBase cb = (ComboBase)dce.getComponent();
								int tableCols = cb.getItemCount();
								if(dataCols != tableCols){
									chkAddRetu = false;
								}
								
								//��ǉ�����
								//�����20�񖢖��̏ꍇ�ɒǉ�
								int maxcol = prototypeListTable.getHeaderTable().getColumnCount();
								
//add start --------------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.7
								int chkCol = 0;
								if(radioButton[0].isSelected()){
									chkCol = 10;
								}
								else{
									chkCol = 20;
								}
//add end ----------------------------------------------------------------------------------------
								
								if(maxcol < chkCol){
									if(chkAddRetu){
										//�v�Z�I����
										//2010.09.09 sakai change --->
										//if(radioButton[0].isSelected()){
										if(radioButton[1].isSelected()){
										//2010.09.09 sakai change --->
											prototypeListTable.addHeaderTableCol();
											prototypeListTable.addComboTableCol();
											prototypeListTable.addMainTableCol();
										//�S�H���I����
										}else{
											prototypeListTable.addHeaderTableCol();
											prototypeListTable.addComboTableCol();
											prototypeListTable.addMainTableCol_all();
										}
									}else{
										//�G���[�ݒ�
										ex = new ExceptionBase();
										ex.setStrErrCd("");
										ex.setStrErrmsg("����\�̏�񂪕ύX����Ă��܂��B�u�����R�s�[�v�{�^�����������A�ēx�ݒ肵�ĉ������B");
										ex.setStrErrShori(this.getClass().getName());
										ex.setStrMsgNo("");
										ex.setStrSystemMsg("");
										//���b�Z�[�W�\��
										DataCtrl.getInstance().PrintMessage(ex);
									}
								}
								//�����20��ȏ�̏ꍇ�̓��b�Z�[�W�\��
								else{
									DataCtrl.getInstance().getMessageCtrl().PrintMessageString("��ǉ���" + chkCol + "��܂łł��B");
								}
								
							}
							
							//-------------------------- �v�Z -------------------------------------
							if ( event_name == "keisan") {
								//�e�[�u��������
								prototypeListTable.initMainTable();
								//��ǉ�
								int col_count = prototypeListTable.getHeaderTable().getColumnCount();
								for(int i=0; i<col_count; i++){
									prototypeListTable.addMainTableCol();
								}
							}
							
							//------------------------- �S�H�� ------------------------------------
							if ( event_name == "allKotei") {
								//�e�[�u��������
								prototypeListTable.initMainTable();
								//��ǉ�
								int col_count = prototypeListTable.getHeaderTable().getColumnCount();
								for(int i=0; i<col_count; i++){
									prototypeListTable.addMainTableCol_all();
								}
							}
							
						}catch(ExceptionBase be){
							//���b�Z�[�W�\��
							DataCtrl.getInstance().PrintMessage(be);
							
						}catch (Exception ec) {
							ec.printStackTrace();
							//�G���[�ݒ�
							ex = new ExceptionBase();
							ex.setStrErrCd("");
							ex.setStrErrmsg("�����R�s�[��ʂł̃{�^���������������s���܂����B");
							ex.setStrErrShori(this.getClass().getName());
							ex.setStrMsgNo("");
							ex.setStrSystemMsg(ec.getMessage());
							//���b�Z�[�W�\��
							DataCtrl.getInstance().PrintMessage(ex);
							
						} finally {
							
						}
					}
				}
			);
	}
	
	/**********************************************************************************
	 * 
	 *  �{�^���z��Z�b�^�[&�Q�b�^�[
	 *  @return
	 * 
	 **********************************************************************************/
	public ButtonBase[] getButton() {
		return button;
	}
	public void setButton(ButtonBase[] button) {
		this.button = button;
	}
	
	/**********************************************************************************
	 * 
	 *  ���W�I�{�^���z��Z�b�^�[&�Q�b�^�[
	 *  @return
	 * 
	 **********************************************************************************/
	public JRadioButton[] getRadioButton() {
		return radioButton;
	}
	public void setRadioButton(JRadioButton[] radioButton) {
		this.radioButton = radioButton;
	}
	
	/**********************************************************************************
	 * 
	 *  �e�[�u���Z�b�^�[&�Q�b�^�[
	 *  @return
	 * 
	 **********************************************************************************/
	public PrototypeListTable getPrototypeListTable() {
		return prototypeListTable;
	}
	public void setPrototypeListTable(PrototypeListTable prototypeListTable) {
		this.prototypeListTable = prototypeListTable;
	}
}