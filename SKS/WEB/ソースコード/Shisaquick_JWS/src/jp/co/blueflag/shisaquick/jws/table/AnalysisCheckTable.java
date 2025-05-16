package jp.co.blueflag.shisaquick.jws.table;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JViewport;
import javax.swing.ListSelectionModel;
import javax.swing.border.LineBorder;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import jp.co.blueflag.shisaquick.jws.common.ExceptionBase;
import jp.co.blueflag.shisaquick.jws.common.JwsConstManager;
import jp.co.blueflag.shisaquick.jws.common.ScrollBase;
import jp.co.blueflag.shisaquick.jws.common.TableBase;

/**
 * 
 * ���앪�̓f�[�^�m�F�e�[�u���N���X
 *  : ���앪�̓f�[�^�m�F�e�[�u���R���g���[����ݒ肷��
 * 
 * @author TT.katayama
 * @since 2009/04/04
 *
 */
public class AnalysisCheckTable {

	private ExceptionBase ex = null;
	
	private TableBase headerTable;			//�w�b�_�[�e�[�u��
	private TableBase mainTable;				//���C���e�[�u��
	private ScrollBase scroll;					//�X�N���[���p�l��
	
	/**
	 * �R���X�g���N�^(�s�E�� �w��)
	 * @param intRow : �s��
	 * @param intCol : ��
	 * @param columnNm : �񖼊i�[�z��
	 * @param columnWidth : �񕝊i�[�z��
	 * @throws ExceptionBase 
	 */
	public AnalysisCheckTable( int intRow, int intCol, Object[] columnNm, int[] columnWidth ) throws ExceptionBase{
		
		//�e�[�u���w�b�_�[�F
		Color table_color = JwsConstManager.SHISAKU_ITEM_COLOR;
		
		try {
			//�e�[�u���̃C���X�^���X����
			this.mainTable = new TableBase( intRow, intCol ){
				private static final long serialVersionUID = 1L;
				/**
				 * �Z���ҏW�s��
				 */
				public boolean isCellEditable(int row, int column) {
				    return false;
				}
			};
			this.headerTable = new TableBase( 1, intCol ) {
				private static final long serialVersionUID = 1L;
				/**
				 * �Z���ҏW�s��
				 */
				public boolean isCellEditable(int row, int column) {
				    return false;
				}
			};
			
			
			this.mainTable.setCellSelectionEnabled( false );
			this.headerTable.setCellSelectionEnabled( false );
			this.headerTable.setEnabled(false);
			//�������T�C�Y��OFF�ɐݒ�
			this.mainTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
			this.headerTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
			//�s�I���̐ݒ�
			this.mainTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			this.mainTable.setRowSelectionAllowed(true);
			
			//�񖼂̐ݒ�
			this.setColumnName(columnNm);
			//�񕝂̐ݒ�
			this.setColumnWidth(this.mainTable, columnWidth);
			this.setColumnWidth(this.headerTable, columnWidth);
			
			this.headerTable.setRowHeight(35);
			
			//�w�b�_�[�e�[�u���̐F�ݒ�
			this.headerTable.setBackground(table_color);
			
			//�X�N���[���p�l���̃C���X�^���X����
			this.scroll = new ScrollBase( this.mainTable ) {
				private static final long serialVersionUID = 1L;
				//�w�b�_�[������
				public void setColumnHeaderView(Component view) {} 
			};
			
			//�X�N���[���p�l���̐ݒ�
			this.scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
			this.scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
			this.scroll.setBackground(Color.WHITE);
			this.scroll.setBorder(new LineBorder(Color.BLACK, 1));
			this.scroll.setBackground(Color.WHITE);
			
			//�r���[�|�[�g�̐ݒ�
			JViewport headerViewport = new JViewport();
			headerViewport.setView(this.headerTable);
			headerViewport.setPreferredSize(this.headerTable.getPreferredSize());
			this.scroll.setColumnHeader(headerViewport);
			
		} catch( Exception e ) {
			//�G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("���앪�̓f�[�^�m�F�e�[�u�����������������s���܂����B");
			this.ex.setStrErrShori("AnalysisCheckTable");
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.toString());
			throw ex;
		} finally {
		}
	}
	
	/**
	 * �񖼂̏�����
	 * @param columnNm : �񖼊i�[�z��
	 */
	private void setColumnName(Object[] columnNm) {
		for (int i=0; i<columnNm.length; i++ ) {
			this.headerTable.setValueAt(columnNm[i], 0, i);
		}
	}
	
	/**
	 * ��̉����̏�����
	 * @param table : �Ώۃe�[�u��
	 * @param columnWidth : �񕝊i�[�z��
	 */
	private void setColumnWidth(TableBase table, int[] columnWidth) {		
		TableColumnModel columnModel = table.getColumnModel();
		TableColumn column;
		
		//�񕝂�ݒ肵�Ă���
		for ( int i=0; i<table.getColumnCount(); i++ ) {
			column = columnModel.getColumn(i);
			column.setPreferredWidth(columnWidth[i]);
			table.setRowHeight(17);
			table.setFont(new Font("Default", Font.PLAIN, 13));
		}
	}
	
	/**
	 * �X�N���[���p�l���@�Q�b�^�[
	 * @return Scroll�p�l�� 
	 */
	public ScrollBase getScroll() {
		return this.scroll;
	}
	
	/**
	 * ���C���e�[�u�� �Q�b�^�[
	 * @return ���C���e�[�u��
	 */
	public TableBase getMainTable() {
		return this.mainTable;
	}
	
	/**
	 * �T�u�e�[�u�� �Q�b�^�[
	 * @return �T�u�e�[�u��
	 */
	public TableBase getHeaderTable() {
		return this.headerTable;
	}
	
}