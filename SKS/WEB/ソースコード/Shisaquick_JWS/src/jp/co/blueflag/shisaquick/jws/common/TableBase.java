package jp.co.blueflag.shisaquick.jws.common;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.*;
import javax.swing.event.TableColumnModelEvent;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

import jp.co.blueflag.shisaquick.jws.celleditor.MiddleCellEditor;
import jp.co.blueflag.shisaquick.jws.data.DataCtrl;

/**
 * 
 * �e�[�u������
 *  : �A�v�����e�[�u���̊�{������s��
 *
 */
public class TableBase extends JTable implements FocusControl {

	private static final long serialVersionUID = 1L;	//�f�t�H���g�V���A��ID
	
	//�G���[����
	private ExceptionBase ex;
	//���ړ��̓����o
	boolean directInput = true;
	
	/**
	 * �R���X�g���N�^
	 */
	public TableBase(){
		//�X�[�p�[�N���X�̃R���X�g���N�^���Ăяo��
		super();
		
		this.ex = null;
		
	}
	
	/**
	 * �R���X�g���N�^(�e�[�u�����f�� �w��)
	 * @param DefaultTableModel : �e�[�u�����f��
	 */
	public TableBase(DefaultTableModel DefaultTableModel) {
		super(DefaultTableModel);
		
		this.ex = null;
		
	}
	
	/**
	 * �R���X�g���N�^(TableModel �w��)
	 * @param tablemodel : �s��
	 */
	public TableBase(int intRow, int intCol) {
		super(intRow, intCol);
		
		this.ex = null;
		
	}
	
	/**
	 * ���ړ��͎w��
	 * @param tablemodel : �s��
	 */
	protected boolean processKeyBinding(KeyStroke ks, KeyEvent e, int condition, boolean pressed) {
		//���ړ��͂̏ꍇ
		if(directInput){
			 boolean retValue = super.processKeyBinding(ks, e, condition, pressed);
			    //if(!check.isSelected()) return retValue;
			 
			 try{
				 
				 if(KeyStroke.getKeyStroke('\t').equals(ks) || KeyStroke.getKeyStroke('\n').equals(ks)) {
				      
					 return retValue;
				    	
				 }
				    
				 if(getInputContext().isCompositionEnabled() && !isEditing() &&
						 !pressed && !ks.isOnKeyRelease()) {
				    	
					 int selectedRow = getSelectedRow();
					 int selectedColumn = getSelectedColumn();
					 if(selectedRow!=-1 && selectedColumn!=-1 && !editCellAt(selectedRow, selectedColumn)) {
						 return retValue;
					 }
				      
				 }
				 
			 }catch(Exception ex){
				 
				 
			 }
			 
			 return retValue;
			
		//���ړ��͂łȂ��ꍇ
		}else{
			return super.processKeyBinding(ks, e, condition, pressed);
		}
	}

	/**
	 * Enter�������t�H�[�J�X�R���g���[��
	 * @param enterComp : Enter���̃t�H�[�J�X�ړ���JComponent
	 */
	public void setEnterFocusControl(final JComponent enterComp) throws ExceptionBase {
		
		try {
			this.addKeyListener(new java.awt.event.KeyAdapter() {
				public void keyPressed(java.awt.event.KeyEvent e) {

					//�ŏI�s�E�ŏI��
					boolean isLastRowSelect = ( isRowSelected(getRowCount() - 1));
					boolean isLastColumnSelect = ( isColumnSelected(getColumnCount() - 1));
					
					//KEY �������̏���
					if ( e.getKeyCode() == KeyEvent.VK_ENTER  ) {			//ENTER�L�[
						//�ŏI�s�Ƀt�H�[�J�X��
						if ( isLastRowSelect ) {
							enterComp.requestFocus();	//�Ώۂ�JComponent�փt�H�[�J�X��ݒ�
							clearSelection();		//�e�[�u���𖢑I���ɐݒ�
						}
					}
				}
			});
		} catch ( Exception e ) {			
			//�G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("TableBase�̃t�H�[�J�X���䏈�������s���܂���");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;
		} finally {
			
		}
	}
	
	/**
	 * TAB�������t�H�[�J�X�R���g���[��
	 * @param enterComp : TAB���̃t�H�[�J�X�ړ���JComponent
	 */
	public void setTabFocusControl(final JComponent enterComp) throws ExceptionBase {
		
		try {
			this.addKeyListener(new java.awt.event.KeyAdapter() {
				public void keyPressed(java.awt.event.KeyEvent e) {
	
					//�ŏI�s�E�ŏI��
					boolean isLastRowSelect = ( isRowSelected(getRowCount() - 1));
					boolean isLastColumnSelect = ( isColumnSelected(getColumnCount() - 1));
					
					if ( e.getKeyCode() == KeyEvent.VK_TAB ) {		//TAB�L�[
						//�ŏI�s�E�ŏI��Ƀt�H�[�J�X��
						if ( isLastRowSelect && isLastColumnSelect ) {
							transferFocus();		//�V�X�e���ɐݒ肳��Ă��鎟�̃t�B�[���h�Ƀt�H�[�J�X��ݒ�
							clearSelection();		//�e�[�u���𖢑I���ɐݒ�
						}
					}
				}
			});
		} catch ( Exception e ) {			
			//�G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("TableBase�̃t�H�[�J�X���䏈�������s���܂���");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;
		} finally {
			
		}
	}
	
	//�e�[�u�����상�\�b�h�i�s�폜�j
	public void tableDeleteRow(int row){
		DefaultTableModel model = (DefaultTableModel)this.getModel();
		model.removeRow( row );
	}
	//�e�[�u�����상�\�b�h�i�s�ǉ��j
	public void tableInsertRow(int row){
		this.clearSelection();
		DefaultTableModel model = (DefaultTableModel)this.getModel();
		model.insertRow( row, new Vector() );
	}
	//�e�[�u�����상�\�b�h�i�s�ړ��j
	public void tableMoveRow(int oldRow,int newRow){
		DefaultTableModel model = (DefaultTableModel)this.getModel();
		model.moveRow( oldRow, oldRow, newRow );
	}
	//�e�[�u�����상�\�b�h�i��폜�j
	public void tableDeleteColumn(int columnIndex){
		DefaultTableModel model = (DefaultTableModel)this.getModel();
		/* �w�b�_���i�[ */
		Vector columnIdentifiers = new Vector();
		for (int i = 0, n = this.getColumnCount(); i < n; i++) {
			columnIdentifiers.add(columnModel.getColumn(i).getHeaderValue());
		}
		/* �J�������폜 */
		columnIdentifiers.remove(columnIndex);
		TableColumnModel cm = getColumnModel();
		this.removeColumn(cm.getColumn(columnIndex));
		/* dataVector �z��̍쐬 */
		Object[][] dataVector = new Object[this.getRowCount()][this.getColumnCount()];
		for (int i = 0, n = dataVector.length; i < n; i++) {
			for (int j = 0, m = dataVector[0].length; j < m; j++) {
				dataVector[i][j] = this.getValueAt(i, j);
			}
		}
		/* �V�����f�[�^���Z�b�g */
		model.setDataVector(dataVector, columnIdentifiers.toArray());
	}
	//�e�[�u�����상�\�b�h�i��ǉ��j
	public void tableInsertColumn(int columnIndex){
		int rowCount = this.getRowCount();
		int colCount = this.getColumnCount();
		/* ��ǉ����A�w��ʒu�Ɉړ� */
		DefaultTableModel model = (DefaultTableModel)this.getModel();
		model.addColumn(new Vector());
		tableMoveColumn(colCount,columnIndex);
	}
	
	//�e�[�u�����상�\�b�h�i��ړ��j
	public void tableMoveColumn(int oldCol,int newCol){
		DefaultTableModel model = (DefaultTableModel)this.getModel();
		/* �w�b�_���i�[ */
		Vector columnIdentifiers = new Vector();
		for (int i = 0, n = this.getColumnCount(); i < n; i++) {
			columnIdentifiers.add(columnModel.getColumn(i).getHeaderValue());
		}
		/* �J�������ړ� */
		columnIdentifiers.set(oldCol, columnIdentifiers.get(newCol));
		columnIdentifiers.set(newCol, columnIdentifiers.get(oldCol));
		this.moveColumn( oldCol, newCol );
		/* dataVector �z��̍쐬 */
		Object[][] dataVector = new Object[this.getRowCount()][this.getColumnCount()];
		for (int i = 0, n = dataVector.length; i < n; i++) {
			for (int j = 0, m = dataVector[0].length; j < m; j++) {
				dataVector[i][j] = this.getValueAt(i, j);
			}
		}
		/* �V�����f�[�^���Z�b�g */
		model.setDataVector(dataVector, columnIdentifiers.toArray());
	}
	
	/**
	 * ���ړ��̓����o�@�Q�b�^�[
	 * @return
	 */
	public boolean isDirectInput() {
		return directInput;
	}
	/**
	 * ���ړ��̓����o�@�Z�b�^�[
	 * @return
	 */
	public void setDirectInput(boolean directInput) {
		this.directInput = directInput;
	}
	
	
	  
}
