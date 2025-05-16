package jp.co.blueflag.shisaquick.jws.table;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.DefaultCellEditor;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.text.JTextComponent;

import jp.co.blueflag.shisaquick.jws.base.TrialData;
import jp.co.blueflag.shisaquick.jws.celleditor.ComboBoxCellEditor;
import jp.co.blueflag.shisaquick.jws.celleditor.MiddleCellEditor;
import jp.co.blueflag.shisaquick.jws.celleditor.TextFieldCellEditor;
import jp.co.blueflag.shisaquick.jws.cellrenderer.ComboBoxCellRenderer;
import jp.co.blueflag.shisaquick.jws.cellrenderer.MiddleCellRenderer;
import jp.co.blueflag.shisaquick.jws.cellrenderer.TextFieldCellRenderer;
import jp.co.blueflag.shisaquick.jws.common.ComboBase;
import jp.co.blueflag.shisaquick.jws.common.ExceptionBase;
import jp.co.blueflag.shisaquick.jws.common.JwsConstManager;
import jp.co.blueflag.shisaquick.jws.common.LabelBase;
import jp.co.blueflag.shisaquick.jws.common.PanelBase;
import jp.co.blueflag.shisaquick.jws.common.ScrollBase;
import jp.co.blueflag.shisaquick.jws.common.TableBase;
import jp.co.blueflag.shisaquick.jws.common.TextboxBase;
import jp.co.blueflag.shisaquick.jws.data.DataCtrl;
import jp.co.blueflag.shisaquick.jws.textbox.NumelicTextbox;

/************************************************************************************
 * 
 * �����ǉ��e�[�u���N���X
 *  : �����ǉ��e�[�u���R���g���[����ݒ肷��
 * 
 * @author TT.katayama
 * @since 2009/04/05
 *
 ************************************************************************************/
public class PrototypeListTable{
	//---------------------------- �e�e�[�u�� ---------------------------------------
	//�����w�b�_�[�e�[�u��
	private TableBase leftHeaderTable;
	//�E���w�b�_�[�e�[�u��
	private TableBase headerTable;
	//�E���R���{�{�b�N�X�e�[�u��
	private TableBase comboTable;
	//�E�����C���e�[�u��
	private TableBase mainTable;
	
	//------------------------- �X�N���[���p�l�� -------------------------------------
	private ScrollBase[] scroll;	
	
	//------------------------ �G�f�B�^&�����_���z�� -----------------------------------
	//�E���w�b�_�[�e�[�u���p�z��
	private ArrayList headerTableCellEditor = new ArrayList();	
	private ArrayList headerTableCellRenderer = new ArrayList();
	//�E���R���{�{�b�N�X�e�[�u���p�z��
	private ArrayList comboTableCellEditor = new ArrayList();	
	private ArrayList comboTableCellRenderer = new ArrayList();
	//�E�����C���e�[�u���p�z��
	private ArrayList mainTableCellEditor = new ArrayList();	
	private ArrayList mainTableCellRenderer = new ArrayList();
	
	//---------------------------- �ő�H���� ----------------------------------------
	private int maxKotei = 0;
	
	//---------------------------- ��O�N���X -----------------------------------------
	private ExceptionBase ex = null;
	
	/**********************************************************************************
	 * 
	 * �R���X�g���N�^
	 * @param intRow : �s��
	 * @param intCol : ��
	 * @throws ExceptionBase 
	 * 
	 **********************************************************************************/
	public PrototypeListTable() throws ExceptionBase {
		
		try {
			//�z���f�[�^�擾
			ArrayList retuData = DataCtrl.getInstance().getTrialTblData().SearchHaigoData(0);
			//�ő�H�����擾
			this.maxKotei = DataCtrl.getInstance().getTrialTblData().getIntMaxKotei();
			
			//�e�[�u�� ������
			this.initTable();
			
			//�X�N���[���p�l���̏����ݒ�
			this.initScroll();
			
		} catch( Exception e ) {
			
			e.printStackTrace();
			//�G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("�����ǉ��e�[�u�����������������s���܂����B");
			this.ex.setStrErrShori("PrototypeListTable");
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;
			
		} finally {
		}
	}
	
	/**********************************************************************************
	 * 
	 * �e�[�u�� ������
	 * 
	 **********************************************************************************/
	public void initTable() throws ExceptionBase{
		try{
			//-------------------- �����w�b�_�[�e�[�u���̏����� ------------------------------
			this.leftHeaderTable = new TableBase(3,1){
				private static final long serialVersionUID = 1L;
				
				//�Z���ҏW�s�ݒ�
				public boolean isCellEditable(int row, int column) {
				    return false;
				}
			};
			//�����w�b�_�[�e�[�u���P��Z���I��s��
			this.leftHeaderTable.setCellSelectionEnabled( false );
			//�����w�b�_�[�e�[�u���g��
			this.leftHeaderTable.setBorder(new LineBorder(Color.BLACK, 1));
			//�����w�b�_�[�e�[�u���w�i�F
			this.leftHeaderTable.setBackground(JwsConstManager.SHISAKU_ITEM_COLOR);
			//2010.09.09 sakai add --->
			//�t�H���g�T�C�Y�̐ݒ�
			this.leftHeaderTable.setFont(new Font("Default", Font.PLAIN, 13));
			//2010.09.09 sakai add <---
			//�����w�b�_�[�e�[�u���l
			this.leftHeaderTable.setValueAt("�v�Z��", 0, 0);
			this.leftHeaderTable.setValueAt("�T���v��No", 1, 0);
			this.leftHeaderTable.setValueAt("�H��", 2, 0);
			//�����w�b�_�[�e�[�u���c��
			this.leftHeaderTable.setRowHeight( 0 , 20 );
			this.leftHeaderTable.setRowHeight( 1 , 18 );
			this.leftHeaderTable.setRowHeight( 2 , 200 );
			
			
			//------------------ �i1�s�ځj�w�b�_�[�e�[�u���̏����� -----------------------------
			this.headerTable = new TableBase(1,0);
			//�E���w�b�_�[�e�[�u���P��Z���I��s��
			this.headerTable.setCellSelectionEnabled( false );
			//�E���w�b�_�[�e�[�u�������T�C�Y�ݒ�s��
			this.headerTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
			//�E���w�b�_�[�e�[�u���c��
			this.headerTable.setRowHeight(20);
			//��ǉ�
			this.addHeaderTableCol();
			
			
			//----------------- �i2�s�ځj�����I���e�[�u���̏����� ----------------------------
			this.comboTable = new TableBase(1,0);
			//�E���R���{�{�b�N�X�e�[�u���P��Z���I����
			this.comboTable.setCellSelectionEnabled( true );
			//�E���R���{�{�b�N�X�e�[�u�������T�C�Y�ݒ�s��
			this.comboTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
			//�E���R���{�{�b�N�X�e�[�u���c��
			this.comboTable.setRowHeight(20);
			//��ǉ�
			this.addComboTableCol();
			
			
			//------------------- �i3�s�ځj���C���e�[�u���̏����� -------------------------------
			this.mainTable = new TableBase(0,0);
			//�E�����C���e�[�u���P��Z���I����
			this.mainTable.setCellSelectionEnabled( true );
			//�E�����C���e�[�u�������T�C�Y�ݒ�s��
			this.mainTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
			//��ǉ�
			//2010.09.15 sakai change --->
			//this.addMainTableCol();
			this.addMainTableCol_all();
			//2010.09.15 sakai change <---
			
		}catch(ExceptionBase eb){
			throw eb;
			
		}catch(Exception e){
			//�G���[�ݒ�
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			ex.setStrErrmsg("�����v�Z�e�[�u���̏������Ɏ��s���܂����B");
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getMessage());
			throw ex;
			
		}finally{
			
		}
		
	}
	
	/**********************************************************************************
	 * 
	 * �E���e�[�u��(1�s��)�E�^�C�g���e�[�u����ǉ�
	 * 
	 **********************************************************************************/
	public void addHeaderTableCol() {
		try{
			//----------------------------- �����ݒ� -------------------------------------
			//�ő�񐔎擾
			int col = this.headerTable.getColumnCount();
			
			//-------------------------- �e�[�u����ǉ� ----------------------------------
			this.headerTable.tableInsertColumn(col);
			this.headerTable.setValueAt(Integer.toString(col+1), 0, col);
			
			//------------------------ �G�f�B�^&�����_������ -------------------------------
			//���ԃG�f�B�^&�����_������
			MiddleCellEditor mce = new MiddleCellEditor(this.headerTable);
			MiddleCellRenderer mcr = new MiddleCellRenderer();
			
			//�R���|�[�l���g����
			TextboxBase tb = new TextboxBase();
			tb.setBackground(JwsConstManager.SHISAKU_ITEM_COLOR);
			tb.setEditable(false);
			
			//�G�f�B�^&�����_������
			TextFieldCellEditor tce = new TextFieldCellEditor(tb);
			TextFieldCellRenderer tcr = new TextFieldCellRenderer(tb);
			//���ԃG�f�B�^&�����_���o�^
			mce.addEditorAt(0, tce);
			mcr.add(0, tcr);
			//�G�f�B�^&�����_���z��֒ǉ�
			headerTableCellEditor.add(mce);
			headerTableCellRenderer.add(mcr);
			
			//------------------- �e�[�u���֒��ԃG�f�B�^&�����_���ݒ� ------------------------
			DefaultTableColumnModel columnModel = (DefaultTableColumnModel)this.headerTable.getColumnModel();
			TableColumn column = null;
			for(int i=0; i<this.headerTable.getColumnCount(); i++){
				//�G�f�B�^&�����_���ݒ�
				column = this.headerTable.getColumnModel().getColumn(i);
				column.setCellEditor((MiddleCellEditor)headerTableCellEditor.get(i));
				column.setCellRenderer((MiddleCellRenderer)headerTableCellRenderer.get(i));
				//���ݒ�
				column.setMinWidth(200);
				column.setMaxWidth(200);
			}
			
		}catch(Exception e){
			e.printStackTrace();
			
		}finally{
			
		}
	}

	/**********************************************************************************
	 * 
	 * �E���e�[�u��(2�s��)�E�R���{�{�b�N�X�e�[�u����ǉ�
	 * 
	 **********************************************************************************/
	public void addComboTableCol() {
		try{
			//----------------------------- �����ݒ� -------------------------------------
			//�ő�񐔎擾
			int col = this.comboTable.getColumnCount();
			//�f�[�^�擾
			ArrayList retuData = DataCtrl.getInstance().getTrialTblData().SearchShisakuRetuData(0);
			String setSample = ((TrialData)retuData.get(0)).getStrSampleNo();
			
			//-------------------------- �e�[�u����ǉ� ----------------------------------
			//�����No
			this.comboTable.tableInsertColumn(col);
			//�v�Z��
			this.comboTable.tableInsertColumn(col+1);
			this.comboTable.setValueAt("+", 0, col+1);
			
			
			//------------------- �����No�I��p�@�G�f�B�^&�����_������ ---------------------
			//���ԃG�f�B�^&�����_������
			MiddleCellEditor mceSample = new MiddleCellEditor(this.comboTable);
			MiddleCellRenderer mcrSample = new MiddleCellRenderer();
			
			//�R���|�[�l���g����
			ComboBase cbSample = new ComboBase();
			//�f�[�^�ݒ�
			int selCount = 0;
			for(int j=1; j<=retuData.size(); j++){
				for(int i=0; i<retuData.size(); i++){
					TrialData selTrialData = (TrialData)retuData.get(i);
					//�\�����ɂĕ\��
					if(j == selTrialData.getIntHyojiNo()){
						//2010.09.09 sakai change --->
						//cbSample.addItem(selTrialData.getIntHyojiNo()+"�F"+checkNull(selTrialData.getStrSampleNo()));
						cbSample.addItem(checkNull(selTrialData.getStrSampleNo()));
						//2010.09.09 sakai change <---
						
						//�����ݒ�p�T���v��No�擾�i�\�������ŏ��̃T���v��No�j
						if(selCount == 0){
							setSample = checkNull(selTrialData.getStrSampleNo());
							selCount ++;
						}
					}
				}
			}
			//�T���v�����ݒ�
			this.comboTable.setValueAt(setSample, 0, col);
			
			//�G�f�B�^&�����_������
			ComboBoxCellEditor cceSample = new ComboBoxCellEditor(cbSample);
			ComboBoxCellRenderer ccrSample = new ComboBoxCellRenderer(cbSample);
			//���ԃG�f�B�^&�����_���o�^
			mceSample.addEditorAt(0, cceSample);
			mcrSample.add(0, ccrSample);
			//�G�f�B�^&�����_���z��֒ǉ�
			comboTableCellEditor.add(mceSample);
			comboTableCellRenderer.add(mcrSample);
			
			//-------------------- �v�Z���I��p�@�G�f�B�^&�����_������ ----------------------
			//���ԃG�f�B�^&�����_������
			MiddleCellEditor mceKeisan = new MiddleCellEditor(this.comboTable);
			MiddleCellRenderer mcrKeisan = new MiddleCellRenderer();
			
			//�R���|�[�l���g����
			ComboBase cbKeisan = new ComboBase();
			//2010.09.09 sakai add --->
			//�t�H���g�T�C�Y�̐ݒ�
			cbKeisan.setFont(new Font("Default", Font.PLAIN, 14));
			//2010.09.09 sakai add <---
			
			//�f�[�^�ݒ�
			cbKeisan.addItem("+");
			cbKeisan.addItem("-");
			
			//�G�f�B�^&�����_������
			ComboBoxCellEditor cceKeisan = new ComboBoxCellEditor(cbKeisan);
			ComboBoxCellRenderer ccrKeisan = new ComboBoxCellRenderer(cbKeisan);
			//���ԃG�f�B�^&�����_���o�^
			mceKeisan.addEditorAt(0, cceKeisan);
			mcrKeisan.add(0, ccrKeisan);
			//�G�f�B�^&�����_���z��֒ǉ�
			comboTableCellEditor.add(mceKeisan);
			comboTableCellRenderer.add(mcrKeisan);
			
			
			//------------------- �e�[�u���֒��ԃG�f�B�^&�����_���ݒ� ------------------------
			DefaultTableColumnModel columnModel = (DefaultTableColumnModel)this.comboTable.getColumnModel();
			TableColumn column = null;
			for(int i=0; i<this.comboTable.getColumnCount(); i++){
				//�G�f�B�^&�����_���ݒ�
				column = this.comboTable.getColumnModel().getColumn(i);
				column.setCellEditor((MiddleCellEditor)comboTableCellEditor.get(i));
				column.setCellRenderer((MiddleCellRenderer)comboTableCellRenderer.get(i));
				//���ݒ�
				int retuFlg = i % 2;
				if(retuFlg == 0){
					column.setMinWidth(140);
					column.setMaxWidth(140);
				}else{
					column.setMinWidth(60);
					column.setMaxWidth(60);
				}
			}
			
		}catch(ExceptionBase eb){
			
		}catch(Exception e){
			e.printStackTrace();
			
		}finally{
			
		}
		
	}
	
	/**********************************************************************************
	 * 
	 * �E���e�[�u��(3�s��) �E���C���e�[�u����ǉ��i�v�Z�j
	 * 
	 **********************************************************************************/
	public void addMainTableCol() throws ExceptionBase{
		try{
			//----------------------------- �����ݒ� -------------------------------------
			//�ő�񐔎擾
			int col = this.mainTable.getColumnCount();
			
			//-------------------------- �e�[�u����ǉ� ----------------------------------
			this.mainTable.tableInsertColumn(col);
			this.mainTable.tableInsertColumn(col+1);
			this.mainTable.tableInsertColumn(col+2);
			
			//-------------------------- �e�[�u���s�ǉ� ----------------------------------
			//�H���������[�v
			for(int i=0; i<maxKotei; i++){
				if(this.mainTable.getRowCount() < maxKotei){
					this.mainTable.tableInsertRow(i);
				}
				//�����l�ݒ�
				this.mainTable.setValueAt(Integer.toString(i+1), i, col);
				this.mainTable.setValueAt("�~", i, col+1);
				this.mainTable.setValueAt("0.0", i, col+2);
			}
			
			
			//------------------- �H�����@�G�f�B�^&�����_������ -----------------------------
			//���ԃG�f�B�^&�����_������
			MiddleCellEditor mceKotei = new MiddleCellEditor(this.mainTable);
			MiddleCellRenderer mcrKotei = new MiddleCellRenderer();
			
			//�s��������
			for(int i=0; i<maxKotei; i++){
				//�R���|�[�l���g����
				TextboxBase tb = new TextboxBase();
				tb.setBackground(Color.lightGray);
				tb.setEditable(false);
				
				//�G�f�B�^&�����_������
				TextFieldCellEditor tce = new TextFieldCellEditor(tb);
				TextFieldCellRenderer tcr = new TextFieldCellRenderer(tb);
				//���ԃG�f�B�^&�����_���o�^
				mceKotei.addEditorAt(i, tce);
				mcrKotei.add(i, tcr);
			}
			//�G�f�B�^&�����_���z��֒ǉ�
			mainTableCellEditor.add(mceKotei);
			mainTableCellRenderer.add(mcrKotei);
			
			
			//------------------- �v�Z���@�G�f�B�^&�����_������ -----------------------------
			//���ԃG�f�B�^&�����_������
			MiddleCellEditor mceKeisan = new MiddleCellEditor(this.mainTable);
			MiddleCellRenderer mcrKeisan = new MiddleCellRenderer();
			
			//�s��������
			for(int i=0; i<maxKotei; i++){
				//�R���|�[�l���g����
				ComboBase cbKeisan = new ComboBase();
				//2010.09.09 sakai add --->
				//�t�H���g�T�C�Y�̐ݒ�
				cbKeisan.setFont(new Font("Default", Font.PLAIN, 14));
				//2010.09.09 sakai add <---
				//�f�[�^�ݒ�
				cbKeisan.addItem("�~");
				cbKeisan.addItem("��");
				//�G�f�B�^&�����_������
				ComboBoxCellEditor cceKeisan = new ComboBoxCellEditor(cbKeisan);
				ComboBoxCellRenderer ccrKeisan = new ComboBoxCellRenderer(cbKeisan);
				//���ԃG�f�B�^&�����_���o�^
				mceKeisan.addEditorAt(i, cceKeisan);
				mcrKeisan.add(i, ccrKeisan);
			}
			//�G�f�B�^&�����_���z��֒ǉ�
			mainTableCellEditor.add(mceKeisan);
			mainTableCellRenderer.add(mcrKeisan);
			
			
			//------------------- �v�Z�l�@�G�f�B�^&�����_������ ------------------------------
			//���ԃG�f�B�^&�����_������
			MiddleCellEditor mceValue = new MiddleCellEditor(this.mainTable);
			MiddleCellRenderer mcrValue = new MiddleCellRenderer();
			
			//�s��������
			for(int i=0; i<maxKotei; i++){
				//�R���|�[�l���g����
				NumelicTextbox tb = new NumelicTextbox();
				
				//�G�f�B�^&�����_������
				TextFieldCellEditor tce = new TextFieldCellEditor(tb);
				TextFieldCellRenderer tcr = new TextFieldCellRenderer(tb);
				//���ԃG�f�B�^&�����_���o�^
				mceValue.addEditorAt(i, tce);
				mcrValue.add(i, tcr);
			}
			//�G�f�B�^&�����_���z��֒ǉ�
			mainTableCellEditor.add(mceValue);
			mainTableCellRenderer.add(mcrValue);
			
			
			//------------------- �e�[�u���֒��ԃG�f�B�^&�����_���ݒ� ------------------------
			DefaultTableColumnModel columnModel = (DefaultTableColumnModel)this.mainTable.getColumnModel();
			TableColumn column = null;
			for(int i=0; i<this.mainTable.getColumnCount(); i++){
				//�G�f�B�^&�����_���ݒ�
				column = this.mainTable.getColumnModel().getColumn(i);
				column.setCellEditor((MiddleCellEditor)mainTableCellEditor.get(i));
				column.setCellRenderer((MiddleCellRenderer)mainTableCellRenderer.get(i));
				//���ݒ�
				int retuFlg = i % 3;
				if(retuFlg == 0){
					column.setMinWidth(100);
					column.setMaxWidth(100);
				}else if(retuFlg == 1){
					column.setMinWidth(40);
					column.setMaxWidth(40);
				}else{
					column.setMinWidth(60);
					column.setMaxWidth(60);
				}
			}
			//2010.09.09 sakai add --->
			this.mainTable.setRowHeight(17);
			//2010.09.09 sakai add <---
			
		}catch(Exception e){
			e.printStackTrace();
			
			//�G���[�ݒ�
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			ex.setStrErrmsg("�v�Z�e�[�u���̗�ǉ��Ɏ��s���܂����B");
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getMessage());
			throw ex;
			
		}finally{
			
		}
	}
	
	/**********************************************************************************
	 * 
	 * �E���e�[�u��(3�s��) �E���C���e�[�u����ǉ��i�S�H���j
	 * 
	 **********************************************************************************/
	public void addMainTableCol_all() throws ExceptionBase{
		try{
			//----------------------------- �����ݒ� -------------------------------------
			//�ő�񐔎擾
			int col = this.mainTable.getColumnCount();
			//�ő�H�����擾
			int maxKotei = 1;
			
			//-------------------------- �e�[�u����ǉ� ----------------------------------
			this.mainTable.tableInsertColumn(col);
			this.mainTable.tableInsertColumn(col+1);
			this.mainTable.tableInsertColumn(col+2);
			
			//-------------------------- �e�[�u���s�ǉ� ----------------------------------
			if(this.mainTable.getRowCount() < maxKotei){
				this.mainTable.tableInsertRow(0);
			}
			this.mainTable.setValueAt("�S�H��", 0, col);
			this.mainTable.setValueAt("�~", 0, col+1);
			this.mainTable.setValueAt("0.0", 0, col+2);
			
			//------------------- �H�����@�G�f�B�^&�����_������ -----------------------------
			//���ԃG�f�B�^&�����_������
			MiddleCellEditor mceKotei = new MiddleCellEditor(this.mainTable);
			MiddleCellRenderer mcrKotei = new MiddleCellRenderer();
			
			//�R���|�[�l���g����
			TextboxBase tb = new TextboxBase();
			tb.setBackground(Color.lightGray);
			tb.setEditable(false);
				
			//�G�f�B�^&�����_������
			TextFieldCellEditor tce = new TextFieldCellEditor(tb);
			TextFieldCellRenderer tcr = new TextFieldCellRenderer(tb);

			//���ԃG�f�B�^&�����_���o�^
			mceKotei.addEditorAt(0, tce);
			mcrKotei.add(0, tcr);
			
			//�G�f�B�^&�����_���z��֒ǉ�
			mainTableCellEditor.add(mceKotei);
			mainTableCellRenderer.add(mcrKotei);
			
			
			//------------------- �v�Z���@�G�f�B�^&�����_������ -----------------------------
			//���ԃG�f�B�^&�����_������
			MiddleCellEditor mceKeisan = new MiddleCellEditor(this.mainTable);
			MiddleCellRenderer mcrKeisan = new MiddleCellRenderer();
			
			//�R���|�[�l���g����
			ComboBase cbKeisan = new ComboBase();
			
			//2010.09.09 sakai add --->
			//�t�H���g�T�C�Y�̐ݒ�
			cbKeisan.setFont(new Font("Default", Font.PLAIN, 14));
			//2010.09.09 sakai add <---

			//�f�[�^�ݒ�
			cbKeisan.addItem("�~");
			cbKeisan.addItem("��");
			
			//�G�f�B�^&�����_������
			ComboBoxCellEditor cceKeisan = new ComboBoxCellEditor(cbKeisan);
			ComboBoxCellRenderer ccrKeisan = new ComboBoxCellRenderer(cbKeisan);
			
			//���ԃG�f�B�^&�����_���o�^
			mceKeisan.addEditorAt(0, cceKeisan);
			mcrKeisan.add(0, ccrKeisan);
			
			//�G�f�B�^&�����_���z��֒ǉ�
			mainTableCellEditor.add(mceKeisan);
			mainTableCellRenderer.add(mcrKeisan);
			
			
			//------------------- �v�Z�l�@�G�f�B�^&�����_������ ------------------------------
			//���ԃG�f�B�^&�����_������
			MiddleCellEditor mceValue = new MiddleCellEditor(this.mainTable);
			MiddleCellRenderer mcrValue = new MiddleCellRenderer();
			
			//�R���|�[�l���g����
			NumelicTextbox tb2 = new NumelicTextbox();
			
			//�G�f�B�^&�����_������
			TextFieldCellEditor tce2 = new TextFieldCellEditor(tb2);
			TextFieldCellRenderer tcr2 = new TextFieldCellRenderer(tb2);
			
			//���ԃG�f�B�^&�����_���o�^
			mceValue.addEditorAt(0, tce2);
			mcrValue.add(0, tcr2);
			
			//�G�f�B�^&�����_���z��֒ǉ�
			mainTableCellEditor.add(mceValue);
			mainTableCellRenderer.add(mcrValue);
			
			
			//------------------- �e�[�u���֒��ԃG�f�B�^&�����_���ݒ� ------------------------
			DefaultTableColumnModel columnModel = (DefaultTableColumnModel)this.mainTable.getColumnModel();
			TableColumn column = null;
			for(int i=0; i<this.mainTable.getColumnCount(); i++){
				//�G�f�B�^&�����_���ݒ�
				column = this.mainTable.getColumnModel().getColumn(i);
				column.setCellEditor((MiddleCellEditor)mainTableCellEditor.get(i));
				column.setCellRenderer((MiddleCellRenderer)mainTableCellRenderer.get(i));
				//���ݒ�
				int retuFlg = i % 3;
				if(retuFlg == 0){
					column.setMinWidth(100);
					column.setMaxWidth(100);
				}else if(retuFlg == 1){
					column.setMinWidth(40);
					column.setMaxWidth(40);
				}else{
					column.setMinWidth(60);
					column.setMaxWidth(60);
				}
			}
			//2010.09.09 sakai add --->
			this.mainTable.setRowHeight(17);
			//2010.09.09 sakai add <---
			
		}catch(Exception e){
			e.printStackTrace();
			
			//�G���[�ݒ�
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			ex.setStrErrmsg("�v�Z�e�[�u���̏������Ɏ��s���܂����B");
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getMessage());
			throw ex;
			
		}finally{
			
		}
	}
	
	/**********************************************************************************
	 * 
	 * �E���e�[�u��(3�s��) �E���C���e�[�u��������
	 * 
	 **********************************************************************************/
	public void initMainTable() throws ExceptionBase{
		try{
			int col_count = this.mainTable.getColumnCount();
			int row_count = this.mainTable.getRowCount();
			//�e�[�u��������
			for(int i=0; i<col_count; i++){
				this.mainTable.tableDeleteColumn(0);
			}
			for(int i=0; i<row_count; i++){
				this.mainTable.tableDeleteRow(0);
			}
			
			//�����_��&�G�f�B�^�z�񏉊���
			mainTableCellEditor.clear();
			mainTableCellRenderer.clear();
			
		}catch(Exception e){
			e.printStackTrace();
			
			//�G���[�ݒ�
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			ex.setStrErrmsg("�v�Z�e�[�u���̏������Ɏ��s���܂����B");
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getMessage());
			throw ex;
			
		}finally{
			
		}
	}

	/**********************************************************************************
	 * 
	 * �X�N���[���p�l�� �����ݒ�
	 * 
	 **********************************************************************************/
	private void initScroll() {

		//�X�N���[���p�l���̃C���X�^���X����[0:�w�b�_�[�e�[�u��, 1:�R���{�{�b�N�X�e�[�u��, 2:���C���e�[�u��]
		this.scroll = new ScrollBase[3];
		//�w�b�_�[�e�[�u��
		this.scroll[0] = new ScrollBase( this.headerTable ) {
			private static final long serialVersionUID = 1L;
			//�w�b�_�[������
			public void setColumnHeaderView(Component view) {} 
		};
		//�R���{�{�b�N�X�e�[�u��
		this.scroll[1] = new ScrollBase( this.comboTable ) {
			private static final long serialVersionUID = 1L;
			//�w�b�_�[������
			public void setColumnHeaderView(Component view) {} 
		};
		//���C���e�[�u��
		this.scroll[2] = new ScrollBase( this.mainTable ) {
			private static final long serialVersionUID = 1L;
			//�w�b�_�[������
			public void setColumnHeaderView(Component view) {} 
		};

		
		//�X�N���[���p�l���̏����ݒ�
		//�w�b�_�[�e�[�u��
		this.scroll[0].setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
		this.scroll[0].setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		this.scroll[0].setBackground(Color.WHITE);
		this.scroll[0].setBorder(new LineBorder(Color.BLACK, 1));
		this.scroll[0].setBackground(Color.WHITE);
		//�R���{�{�b�N�X�e�[�u��
		this.scroll[1].setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
		this.scroll[1].setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		this.scroll[1].setBackground(Color.WHITE);
		this.scroll[1].setBorder(new LineBorder(Color.BLACK, 1));
		this.scroll[1].setBackground(Color.WHITE);
		//���C���e�[�u��
		this.scroll[2].setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		this.scroll[2].setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		this.scroll[2].setBackground(Color.WHITE);
		this.scroll[2].setBorder(new LineBorder(Color.BLACK, 1));		
		this.scroll[2].setBackground(Color.WHITE);

		
		//�X�N���[���o�[�̓����𓯊�������
		final JScrollBar bar1 = this.scroll[0].getHorizontalScrollBar();
		final JScrollBar bar2 = this.scroll[1].getHorizontalScrollBar();
		final JScrollBar bar3 = this.scroll[2].getHorizontalScrollBar();
		bar1.addAdjustmentListener(new AdjustmentListener() {
			public void adjustmentValueChanged(AdjustmentEvent e) {
				bar2.setValue(e.getValue());
				bar3.setValue(e.getValue());
			}
		});
		bar2.addAdjustmentListener(new AdjustmentListener() {
			public void adjustmentValueChanged(AdjustmentEvent e) {
				bar1.setValue(e.getValue());
				bar3.setValue(e.getValue());
			}
		});
		bar3.addAdjustmentListener(new AdjustmentListener() {
			public void adjustmentValueChanged(AdjustmentEvent e) {
				bar1.setValue(e.getValue());
				bar2.setValue(e.getValue());
			}
		});
		
	}

	/************************************************************************************
	 * 
	 * NULL�`�F�b�N�����i�I�u�W�F�N�g�j
	 * @throws ExceptionBase
	 * 
	 ************************************************************************************/
	private String checkNull(Object val){
		String ret = "";
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
		String ret = "";
		if(val >= 0){
			ret = Integer.toString(val);
		}
		return ret;
	}
	
	/**********************************************************************************
	 * 
	 * �ő�H�����@�Q�b�^�[
	 * @return Scroll�p�l�� 
	 * 
	 **********************************************************************************/
	public int getMaxKotei() {
		return maxKotei;
	}
	
	/**********************************************************************************
	 * 
	 * �X�N���[���p�l���@�Q�b�^�[
	 * @return Scroll�p�l�� 
	 * 
	 **********************************************************************************/
	public ScrollBase[] getScroll() {
		return this.scroll;
	}
	
	/**********************************************************************************
	 * 
	 * ���C���e�[�u�� �Q�b�^�[
	 * @return ���C���e�[�u��
	 * 
	 **********************************************************************************/
	public TableBase getMainTable() {
		return this.mainTable;
	}
	
	/**********************************************************************************
	 * 
	 * �R���{�{�b�N�X�e�[�u�� �Q�b�^�[
	 * @return �R���{�{�b�N�X�e�[�u��
	 * 
	 **********************************************************************************/
	public TableBase getComboTable() {
		return this.comboTable;
	}
	
	/**********************************************************************************
	 * 
	 * �w�b�_�[�e�[�u�� �Q�b�^�[
	 * @return �w�b�_�[�e�[�u��
	 * 
	 **********************************************************************************/
	public TableBase getHeaderTable() {
		return this.headerTable;
	}
	
	/**********************************************************************************
	 * 
	 * �����w�b�_�[�e�[�u�� �Q�b�^�[
	 * @return �����w�b�_�[�e�[�u��
	 * 
	 **********************************************************************************/
	public TableBase getLeftHeaderTable() {
		return this.leftHeaderTable;
	}
	
	
}