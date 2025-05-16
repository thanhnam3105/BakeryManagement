package jp.co.blueflag.shisaquick.jws.table;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JViewport;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import jp.co.blueflag.shisaquick.jws.base.BushoData;
import jp.co.blueflag.shisaquick.jws.base.MaterialData;
import jp.co.blueflag.shisaquick.jws.base.XmlData;
import jp.co.blueflag.shisaquick.jws.cellrenderer.MiddleCellRenderer;
import jp.co.blueflag.shisaquick.jws.cellrenderer.TextFieldCellRenderer;
import jp.co.blueflag.shisaquick.jws.common.ExceptionBase;
import jp.co.blueflag.shisaquick.jws.common.JwsConstManager;
import jp.co.blueflag.shisaquick.jws.common.ScrollBase;
import jp.co.blueflag.shisaquick.jws.common.TableBase;
import jp.co.blueflag.shisaquick.jws.common.TextboxBase;
import jp.co.blueflag.shisaquick.jws.data.DataCtrl;
import jp.co.blueflag.shisaquick.jws.manager.XmlConnection;
import jp.co.blueflag.shisaquick.jws.textbox.NumelicTextbox;

/**
 * 
 * �����ꗗ�e�[�u���N���X
 *  : �����ꗗ�e�[�u���R���g���[����ݒ肷��
 * 
 * @author TT.katayama
 * @since 2009/04/03
 *
 */
public class MaterialTable {

	private ExceptionBase ex = null;
	
	private TableBase headerTable;			//�w�b�_�[�e�[�u��
	private TableBase mainTable;				//���C���e�[�u��
	private ScrollBase scroll;					//�X�N���[���p�l��
	
	private BushoData BushoData = new BushoData();

	//�ޔ�p�t�B�[���h
	private int intTaihiKaishaCd = 0;
	private int intTaihiBushoCd = 0;
	private String strTaihiGenryoCd = "";
	private int intTaihiRowId = 0;
	
	private ArrayList lstKaishaCd = null;
	private ArrayList lstBushoCd = null;
	private XmlData xmlJW620;						//�w�l�k�f�[�^�ێ�(JW620)
	
	//�w�b�_�[��
	private static final int HEADER_HEIGHT = 48;
	//�w�b�_�[�t�H���g�T�C�Y
	private static final int HEADER_FONT_SIZE = 12;
	//�s����
	private static final int COLUMN_HEIGHT = 16;
	//�e�[�u���t�H���g�T�C�Y
	private static final int COLUMN_FONT_SIZE = 12;
		
	/**
	 * �R���X�g���N�^(�s�E�� �w��)
	 * @throws ExceptionBase 
	 */
	public MaterialTable() throws ExceptionBase{
		
		try {
			//�L���[�s�[�����f�[�^�擾
			conJW620(JwsConstManager.JWS_CD_KEWPIE);
			
			//�e�[�u���̃C���X�^���X����
			this.mainTable = new TableBase(0,1) {
				private static final long serialVersionUID = 1L;
				/**
				 * �Z���ҏW�s��
				 */
				public boolean isCellEditable(int row, int column) {
				    return false;
				    
				}
				
			};
			this.headerTable = new TableBase(1,10) {
				private static final long serialVersionUID = 1L;
				/**
				 * �Z���ҏW�s��
				 */
				public boolean isCellEditable(int row, int column) {
				    return false;
				    
				}
				
			};
			
			//
			this.mainTable.setCellSelectionEnabled( false );
			this.headerTable.setCellSelectionEnabled( false );
			//�������T�C�Y��OFF�ɐݒ�
			this.mainTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
			this.headerTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
			
			this.headerTable.setEnabled(false);

			//�s�I���̐ݒ�
			this.mainTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			this.mainTable.setRowSelectionAllowed(true);
			this.mainTable.setSelectionBackground(JwsConstManager.TABLE_SELECTED_COLOR);
			
			//�t�H�[�J�X�ݒ�
			this.mainTable.setTabFocusControl(null);
			
			//�w�b�_�[�e�[�u���̐F�ݒ�
			this.headerTable.setBackground(JwsConstManager.SHISAKU_ITEM_COLOR);
			
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
			headerViewport.setPreferredSize( new Dimension(this.headerTable.getPreferredSize().width, HEADER_HEIGHT));
			headerViewport.setSize(this.scroll.getWidth(), 32);
			this.scroll.setColumnHeader(headerViewport);
			
		} catch( Exception e ) {
			//�G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("�����ꗗ�e�[�u�����������������s���܂����B");
			this.ex.setStrErrShori("MaterialTable");
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;
			
		} finally {
			
		}
		
	}

	/**
	 * �e�[�u���̃N���A
	 */
	public void clearTable() {
		//�s���ڂ̑S�폜
		this.deleteRowAll(this.headerTable);
		this.deleteRowAll(this.mainTable);
		
		//�񍀖ڂ̑S�폜
		this.deleteColumnAll(this.headerTable);
		this.deleteColumnAll(this.mainTable);

		//��ЃR�[�h�E�����R�[�h���X�g�̊J��
		this.lstKaishaCd = null;
		this.lstBushoCd = null;
		
	}
	
	/**
	 * �񍀖ڂ̐ݒ�(�S�H��ł͂Ȃ��ꍇ)
	 */
	public void initTableNotZenkojo() {
//add start --------------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.4
		//int intColumnCount = 13;				//��
		int intColumnCount = 14;				//��
		Object[] objColumnNm = new Object[intColumnCount];		//��
		int[] intColumnWidth = new int[intColumnCount];				//��

		////�񖼂̐ݒ�
		//objColumnNm[0] = "";
		//objColumnNm[1] = setHtmlHeaderCenter("����<br>�R�[�h");
		//objColumnNm[2] = "������";
		//objColumnNm[3] = "�H�ꖼ";
		//objColumnNm[4] = "�P��";
		//objColumnNm[5] = "�����P";
		//objColumnNm[6] = setHtmlHeaderCenter("�|�_<br>�i%�j");
		//objColumnNm[7] = setHtmlHeaderCenter("�H��<br>�i%�j");
		//objColumnNm[8] = setHtmlHeaderCenter("���_<br>�i%�j");
		//objColumnNm[9] = setHtmlHeaderCenter("���ܗL��<br>�i%�j");
		//objColumnNm[10] = "����";
		//objColumnNm[11] = "�p�~";
		//objColumnNm[12] = setHtmlHeaderCenter("�m��<br>�R�[�h");

		//�񕝂̐ݒ�
		//intColumnWidth[0] = 30;		//�A��
		//intColumnWidth[1] = 80;	//�����R�[�h
		//intColumnWidth[2] = 250;	//������
		//intColumnWidth[3] = 100;	//������
		//intColumnWidth[4] = 80;	//�P��
		//intColumnWidth[5] = 50;	//�����P
		//intColumnWidth[6] = 48;	//�|�_
		//intColumnWidth[7] = 48;	//�H��
		//intColumnWidth[8] = 48;	//���_
		//intColumnWidth[9] = 58;	//���ܗL��
		//intColumnWidth[10] = 200;	//����
		//intColumnWidth[11] = 55;	//�p�~
		//intColumnWidth[12] = 50;	//�m��R�[�h

		//�񖼂̐ݒ�
		objColumnNm[0] = setHtmlHeaderCenter("����<br>�R�[�h");
		objColumnNm[1] = "������";
		objColumnNm[2] = "�H�ꖼ";
		objColumnNm[3] = "�P��";
		// �g�p����
		// ���C�A�E�g�͂Q�����̂ݕ\���̂��߁A�擪�Q�����̂ݕ\��������悤�ɂ���
		String strShiyoJiseki = JwsConstManager.JWS_NM_SHIYO;
		String strOutShiyoJiseki = strShiyoJiseki.substring(0, 1);
		strOutShiyoJiseki += "<br>" + strShiyoJiseki.substring(1, 2);
		objColumnNm[4] = setHtmlHeaderCenter(strOutShiyoJiseki);
		objColumnNm[5] = setHtmlHeaderCenter("��<br>�g");
		objColumnNm[6] = "�����P";
		objColumnNm[7] = setHtmlHeaderCenter("�|�_<br>�i%�j");
		objColumnNm[8] = setHtmlHeaderCenter("�H��<br>�i%�j");
		objColumnNm[9] = setHtmlHeaderCenter("���_<br>�i%�j");
		objColumnNm[10] = setHtmlHeaderCenter("���ܗL��<br>�i%�j");
		objColumnNm[11] = "����";
		objColumnNm[12] = "�p�~";
		objColumnNm[13] = setHtmlHeaderCenter("�m��<br>�R�[�h");

		//�񕝂̐ݒ�
		intColumnWidth[0] = 80;	//�����R�[�h
		intColumnWidth[1] = 250;	//������
		intColumnWidth[2] = 100;	//������
		intColumnWidth[3] = 80;	//�P��
		intColumnWidth[4] = 20;		//�O����
		intColumnWidth[5] = 20;		//���g�p�t���O
		intColumnWidth[6] = 50;	//�����P
		intColumnWidth[7] = 48;	//�|�_
		intColumnWidth[8] = 48;	//�H��
		intColumnWidth[9] = 48;	//���_
		intColumnWidth[10] = 58;	//���ܗL��
		intColumnWidth[11] = 200;	//����
		intColumnWidth[12] = 55;	//�p�~
		intColumnWidth[13] = 50;	//�m��R�[�h
//add end --------------------------------------------------------------------------------------
		
		this.initTable(intColumnCount, intColumnWidth, objColumnNm);
		
		//��ЃR�[�h�E�����R�[�h�̏�����
		if ( this.lstKaishaCd == null ) {
			this.lstKaishaCd = new ArrayList();
		}
		if ( this.lstBushoCd == null ) {
			this.lstBushoCd = new ArrayList();
		}
		this.lstKaishaCd.clear();
		this.lstBushoCd.clear();
		
	}
	
	/**
	 * �񍀖ڂ̐ݒ�(�S�H��̏ꍇ)
	 */
	public void initTableZenkojo() {
		int i;
		String bushoNm = "";
		ArrayList objColumnNm = new ArrayList();			//��
		
		//�����}�X�^���̍��ڐ�
		int intBushoCount = BushoData.getAryBushoNm().size();
		
		//�񖼂̐ݒ�
		int j =0;
		objColumnNm.add("");
		objColumnNm.add(setHtmlHeaderCenter("����<br>�R�[�h"));
		objColumnNm.add("������");
		
		//���������̗񍀖�(�P��)��ݒ�
		for( i=0; i<intBushoCount; i++ ) {
			bushoNm = BushoData.getAryBushoNm().get(i).toString();
			objColumnNm.add(setHtmlHeaderTable(bushoNm.replaceAll("�H��", ""),60,48));
//			objColumnNm.add(setHtmlHeader("<span valign='top'>" + bushoNm.replaceAll("�H��", "") + ""));
		}
		//���������̗񍀖�(����)��ݒ�
		for( i=0; i<intBushoCount; i++ ) {
			bushoNm = BushoData.getAryBushoNm().get(i).toString();
			objColumnNm.add(setHtmlHeaderTable(bushoNm.replaceAll("�H��", "") + "����1",50,48));
		}
		objColumnNm.add(setHtmlHeaderCenter("�|�_<br>�i%�j"));
		objColumnNm.add(setHtmlHeaderCenter("�H��<br>�i%�j"));
		objColumnNm.add(setHtmlHeaderCenter("���_<br>�i%�j"));
		objColumnNm.add(setHtmlHeaderCenter("���ܗL��<br>�i%�j"));
		objColumnNm.add("����");
		objColumnNm.add("�p�~");
		objColumnNm.add(setHtmlHeaderCenter("�m��<br>�R�[�h"));

		//�񕝂̐ݒ�
		int[] intColumnWidth = new int[objColumnNm.size()];		//��
		intColumnWidth[0] = 30;		//�A��
		intColumnWidth[1] = 80;		//�����R�[�h
		intColumnWidth[2] = 250;		//������
		for( i=0; i<intBushoCount; i++ ) {
			intColumnWidth[3+i] = 80;							//�P��
			intColumnWidth[3+i+intBushoCount] = 50;		//����
		}
		j=3+intBushoCount+intBushoCount;
		intColumnWidth[j] = 48;		//�|�_
		j=4+intBushoCount+intBushoCount;
		intColumnWidth[j] = 48;		//�H��
		j=5+intBushoCount+intBushoCount;
		intColumnWidth[j] = 48;		//���_
		j=6+intBushoCount+intBushoCount;
		intColumnWidth[j] = 58;		//���ܗL��
		j=7+intBushoCount+intBushoCount;
		intColumnWidth[j] = 200;		//����
		j=8+intBushoCount+intBushoCount;
		intColumnWidth[j] = 55;		//�p�~
		j=9+intBushoCount+intBushoCount;
		intColumnWidth[j] = 50;		//�m��R�[�h
		
		//�e�[�u����������
		this.initTable(objColumnNm.size(), intColumnWidth, objColumnNm.toArray());
		
		this.strTaihiGenryoCd = null;
		this.intTaihiBushoCd = 0;
		this.intTaihiKaishaCd = 0;
		this.intTaihiRowId = 0;

		//��ЃR�[�h�E�����R�[�h�̏�����
		if ( this.lstKaishaCd == null ) {
			this.lstKaishaCd = new ArrayList();
		}
		if ( this.lstBushoCd == null ) {
			this.lstBushoCd = new ArrayList();
		}
		this.lstKaishaCd.clear();
		this.lstBushoCd.clear();
		
	}
	
	/**
	 * HTML�w�b�_�[���ڐݒ�
	 * @param strParam : ���ڒl
	 * @return �ݒ�㍀�ڒl
	 */
	private String setHtmlHeader(String strParam) {
		
		return "<html>" + strParam + "</html>";
		
	}

	/**
	 * HTML�w�b�_�[���ڐݒ�(��������)
	 * @param strParam : ���ڒl
	 * @return �ݒ�㍀�ڒl
	 */
	private String setHtmlHeaderCenter(String strParam) {
		
		return this.setHtmlHeader("<center>" + strParam + "</center>");
		
	}

	/**
	 * HTML�w�b�_�[���ڐݒ�(�e�[�u��)
	 * @param strParam : ���ڒl
	 * @return �ݒ�㍀�ڒl
	 */
	private String setHtmlHeaderTable(String strParam, int intWidth, int intHeight) {
		
		return this.setHtmlHeader("<table width='" + intWidth + "' height='" + intHeight + "'><tr><td>" + strParam + "</td></tr></table>");
		
	}

//mod start --------------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.4
	/**
	 * �����ꗗ �s�ǉ�����(�S�H��ł͂Ȃ���)
	 * @param intRowId : �s�ԍ�
	 * @param materialData : �����f�[�^�ێ��N���X
	 * @param listener : �C�x���g�ݒ�N���X
	 */
	//public void insertMainTableNotZenkojo(int intRowId, MaterialData materialData, MiddleCellRenderer md,MiddleCellRenderer mdn) {
	public void insertMainTableNotZenkojo(int intRowId, MaterialData materialData, MiddleCellRenderer md,MiddleCellRenderer mdn,MiddleCellRenderer mdc) {
		int j=0;
		int intGenryocdNstartFlg = 0;

		this.mainTable.tableInsertRow(intRowId);
		//this.mainTable.setValueAt("" + (intRowId+1), intRowId, j++);	//�A��
		
		this.mainTable.setValueAt(materialData.getStrGenryocd(), intRowId, j++);		//�����R�[�h
		//�����R�[�h�擪�P����N�`�F�b�N
		if(!materialData.getStrGenryocd().equals("")) {
			if(materialData.getStrGenryocd().charAt(0) == 'N'){
				intGenryocdNstartFlg = 1;
			}
		}
		
		this.mainTable.setValueAt(materialData.getStrGenryonm(), intRowId, j++);		//������
		this.mainTable.setValueAt(materialData.getStrBushonm(), intRowId, j++);		//�H�ꖼ
		this.mainTable.setValueAt(materialData.getDciTanka(), intRowId, j++);			//�P��
		//�g�p���уt���O
		String strSankazetuNm = "";
		if(intGenryocdNstartFlg == 1 ) {
			//�擪N�����̏ꍇ
			strSankazetuNm = "-";
			
		} else if (materialData.getIntShiyoFlg() == 1) {
			//�g�p���уt���O=1
			strSankazetuNm = "��";
			
		} else {
			//�g�p���уt���O=0
			strSankazetuNm = "�~";
			
		}
		this.mainTable.setValueAt(strSankazetuNm, intRowId, j++);
		
		//���g�p�t���O
		String strMishiyouNm = "";
		if(intGenryocdNstartFlg == 1 ) {
			//�擪N�����̏ꍇ
			strMishiyouNm = "";
			
		} else if (materialData.getIntMishiyoFlg() == 1) {
			//�g�p���уt���O=1
			strMishiyouNm = "��";
			
		} else {
			//�g�p���уt���O=0
			strMishiyouNm = "";
			
		}
		this.mainTable.setValueAt(strMishiyouNm, intRowId, j++);
//add end --------------------------------------------------------------------------------------

		this.mainTable.setValueAt(materialData.getDciBudomari(), intRowId, j++);		//����
		this.mainTable.setValueAt(materialData.getDciSakusan(), intRowId, j++);		//�|�_
		this.mainTable.setValueAt(materialData.getDciShokuen(), intRowId, j++);		//�H��
		this.mainTable.setValueAt(materialData.getDciSousan(), intRowId, j++);			//���_
		this.mainTable.setValueAt(materialData.getDciGanyu(), intRowId, j++);			//���ܗL��
		this.mainTable.setValueAt(materialData.getStrMemo(), intRowId, j++);			//����
		String strHaishiNm = (materialData.getIntHaisicd() == 1)?"�p�~":"�g�p�\";
		this.mainTable.setValueAt(strHaishiNm, intRowId, j++);								//�p�~
		this.mainTable.setValueAt(materialData.getStrkakuteicd(), intRowId, j++);		//�m��R�[�h
		
		this.lstKaishaCd.add(new Integer(materialData.getIntKaishacd()));				//��ЃR�[�h
		this.lstBushoCd.add(new Integer(materialData.getIntBushocd()));;					//�����R�[�h
		
		//�����_���ݒ�i�ʏ�j
		TextboxBase comp = new TextboxBase();
		comp.setFont(new Font("Default", Font.PLAIN, COLUMN_FONT_SIZE));
		if(materialData.getIntHaisicd() == 1){
			comp.setBackground(Color.LIGHT_GRAY);
			
		}else{
			comp.setBackground(Color.WHITE);
			
		}
		md.add(intRowId, new TextFieldCellRenderer(comp));
		
		//�����_���ݒ�i���l�j
		NumelicTextbox ncomp = new NumelicTextbox();
		ncomp.setFont(new Font("Default", Font.PLAIN, COLUMN_FONT_SIZE));
		if(materialData.getIntHaisicd() == 1){
			ncomp.setBackground(Color.LIGHT_GRAY);
			
		}else{
			ncomp.setBackground(Color.WHITE);
			
		}
		mdn.add(intRowId, new TextFieldCellRenderer(ncomp));

//mod start --------------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.4
		////�����_���ݒ�i�ʏ�j
		TextboxBase compc = new TextboxBase();
		compc.setFont(new Font("Default", Font.PLAIN, 14));
		compc.setHorizontalAlignment(SwingConstants.CENTER);
		if(materialData.getIntHaisicd() == 1){
			compc.setBackground(Color.LIGHT_GRAY);	
		}else{
			compc.setBackground(Color.WHITE);
		}
		mdc.add(intRowId, new TextFieldCellRenderer(compc));
//add end --------------------------------------------------------------------------------------
	}
		
	/**
	 * �����ꗗ �s�ǉ�����(�S�H�ꎞ)
	 * @param intRowId : �s�ԍ�
	 * @param materialData : �����f�[�^�ێ��N���X
	 * @param listener : �C�x���g�ݒ�N���X
	 */
	public void insertMainTableZenkojo(MaterialData materialData, MiddleCellRenderer md,MiddleCellRenderer mdn) {
		
		//�����}�X�^���̍��ڐ�
		int intBushoCount = BushoData.getAryBushoNm().size();


		//�������ʂƕێ����Ă�����ЃR�[�h�E�����R�[�h���r���A����łȂ��̂Ȃ�΁A�s��ǉ�
		if ( materialData.getIntKaishacd() != this.intTaihiKaishaCd || materialData.getIntBushocd() != this.intTaihiBushoCd
				|| !materialData.getStrGenryocd().equals(this.strTaihiGenryoCd) ) {
			//�e�[�u���s���ݒ�
			intTaihiRowId = this.mainTable.getRowCount();
			//�e�[�u���s�ǉ�
			this.mainTable.tableInsertRow(intTaihiRowId);
			//�l�̐ݒ�
			this.mainTable.setValueAt("" + (intTaihiRowId+1), intTaihiRowId, 0);							//�A��
			this.mainTable.setValueAt(materialData.getStrGenryocd(), intTaihiRowId, 1);			//�����R�[�h
			this.mainTable.setValueAt(materialData.getStrGenryonm(), intTaihiRowId, 2);			//������
			this.mainTable.setValueAt(materialData.getDciSakusan(), intTaihiRowId, 3 + intBushoCount + intBushoCount);		//�|�_
			this.mainTable.setValueAt(materialData.getDciShokuen(), intTaihiRowId, 4 + intBushoCount + intBushoCount);		//�H��
			this.mainTable.setValueAt(materialData.getDciSousan(), intTaihiRowId, 5 + intBushoCount + intBushoCount);		//���_
			this.mainTable.setValueAt(materialData.getDciGanyu(), intTaihiRowId, 6 + intBushoCount + intBushoCount);			//���ܗL��
			this.mainTable.setValueAt(materialData.getStrMemo(), intTaihiRowId, 7 + intBushoCount + intBushoCount);			//����
			String strHaishiNm = (materialData.getIntHaisicd() == 1)?"�p�~":"�g�p�\";
			this.mainTable.setValueAt(strHaishiNm, intTaihiRowId, 8 + intBushoCount + intBushoCount);							//�p�~
			this.mainTable.setValueAt(materialData.getStrkakuteicd(), intTaihiRowId, 9 + intBushoCount + intBushoCount);		//�m��R�[�h
			
			//�R�[�h��ޔ�
			this.intTaihiKaishaCd = materialData.getIntKaishacd();
			this.intTaihiBushoCd = materialData.getIntBushocd();
			this.strTaihiGenryoCd = materialData.getStrGenryocd();

			this.lstKaishaCd.add(new Integer(materialData.getIntKaishacd()));				//��ЃR�[�h
			this.lstBushoCd.add(new Integer(materialData.getIntBushocd()));;					//�����R�[�h
			
			//�����_���ݒ�
			TextboxBase comp = new TextboxBase();
			comp.setFont(new Font("Default", Font.PLAIN, COLUMN_FONT_SIZE));
			if(materialData.getIntHaisicd() == 1){
				comp.setBackground(Color.LIGHT_GRAY);
			}else{
				comp.setBackground(Color.WHITE);
			}
			TextFieldCellRenderer rendComp = new TextFieldCellRenderer(comp);
			md.add(intTaihiRowId, rendComp);
			
			//�����_���ݒ�i���l�j
			NumelicTextbox ncomp = new NumelicTextbox();
			ncomp.setFont(new Font("Default", Font.PLAIN, COLUMN_FONT_SIZE));
			if(materialData.getIntHaisicd() == 1){
				ncomp.setBackground(Color.LIGHT_GRAY);
				
			}else{
				ncomp.setBackground(Color.WHITE);
				
			}
			mdn.add(intTaihiRowId, new TextFieldCellRenderer(ncomp));
			
		}

		//�S�������̒P���y�ѕ�����ݒ�
		for ( int i=0; i<intBushoCount; i++ ) {
			String cmbBushoNm = BushoData.getAryBushoNm().get(i).toString(); 
			if ( cmbBushoNm.equals(materialData.getStrBushonm()) ) {
				this.mainTable.setValueAt(materialData.getDciTanka(), intTaihiRowId, i + 3 );							//�P��
				this.mainTable.setValueAt(materialData.getDciBudomari(), intTaihiRowId, i + 3 + intBushoCount );	//����

				break;
			}
		}

	}

	/**
	 * �S�s���ڍ폜����
	 */
	public void deleteRowAll(TableBase table) {
		if ( table.getRowCount() > 0 ) {
			for ( int i=table.getRowCount()-1; i>-1; i-- ) {
				table.tableDeleteRow(i);
			}
		}
	}

	/**
	 * �S�񍀖ڍ폜����
	 * @param table
	 */
	private void deleteColumnAll(TableBase table) {
		if ( table.getColumnCount() > 0 ) {
			for( int i=table.getColumnCount()-1; i>-1; i--  ) {
				table.tableDeleteColumn(i);
			}
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
	 * ��̃T�C�Y�̏�����
	 * @param table : �Ώۃe�[�u��
	 * @param columnWidth : �񕝊i�[�z��
	 * @param rowHeight : �񍂂��i�[�z��
	 */
	private void setColumnSize(TableBase table, int[] columnWidth, int rowHeight) {		
		TableColumnModel columnModel = table.getColumnModel();
		TableColumn column;
		
		//�񕝂�ݒ肵�Ă���
		for ( int i=0; i<table.getColumnCount(); i++ ) {
			//���������ݒ�p�Z�������_���[
			DefaultTableCellRenderer render = new DefaultTableCellRenderer();
			render.setHorizontalAlignment(JLabel.CENTER);
			
			//�J�����̐ݒ�
			column = columnModel.getColumn(i);
			column.setCellRenderer(render);
			column.setPreferredWidth(columnWidth[i]);
			table.setRowHeight(rowHeight);
			table.setFont(new Font("Default", Font.PLAIN, HEADER_FONT_SIZE));		//�w�b�_�[�̃t�H���g
			
		}
		
	}
	
	/**
	 * �e�[�u������������
	 * @param intColumnCount
	 * @param table
	 */
	private void initTable(int intColumnCount, int[] intColumnWidth, Object[] objColumnNm) {
		
		//�e�[�u���̃N���A
		this.clearTable();

		//���ǉ�
		for ( int i=0; i<intColumnCount; i++ ) {
			this.headerTable.tableInsertColumn(i);
			this.mainTable.tableInsertColumn(i);
		}
		
		//�w�b�_�[�s��ǉ�
		this.headerTable.tableInsertRow(0);

		//�񖼂̐ݒ�
		this.setColumnName(objColumnNm);
		//�񕝂̐ݒ�
		this.setColumnSize(this.mainTable, intColumnWidth, COLUMN_HEIGHT);
		this.setColumnSize(this.headerTable, intColumnWidth, HEADER_HEIGHT);
		
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
			e.printStackTrace();
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
	 * �e�[�u�����̎w���ЃR�[�h���擾����
	 * @param index
	 * @return �w���ЃR�[�h
	 */
	public String getTableKaishaCd(int index) {
		return this.lstKaishaCd.get(index).toString();
	}
	
	/**
	 * �e�[�u�����̎w�蕔���R�[�h���擾����
	 * @param index
	 * @return �w�蕔���R�[�h
	 */
	public String getTableBushoCd(int index) {
		return this.lstBushoCd.get(index).toString();
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