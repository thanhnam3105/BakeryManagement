package jp.co.blueflag.shisaquick.jws.table;

//------------------------------ ��{�@�\�@List�C���|�[�g  -----------------------------------
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
//----------------------------------- AWT�@�C���|�[�g  --------------------------------------
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
//---------------------------------- Swing�@�C���|�[�g  -------------------------------------
import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.text.Document;

//--------------------------------- �V�T�N�C�b�N�@�C���|�[�g  -----------------------------------
import jp.co.blueflag.shisaquick.jws.base.BushoData;
import jp.co.blueflag.shisaquick.jws.base.CostMaterialData;
import jp.co.blueflag.shisaquick.jws.base.KaishaData;
import jp.co.blueflag.shisaquick.jws.base.LiteralData;
import jp.co.blueflag.shisaquick.jws.base.ManufacturingData;
import jp.co.blueflag.shisaquick.jws.base.MaterialData;
import jp.co.blueflag.shisaquick.jws.base.MixedData;
import jp.co.blueflag.shisaquick.jws.base.PrototypeData;
import jp.co.blueflag.shisaquick.jws.base.PrototypeListData;
import jp.co.blueflag.shisaquick.jws.base.TrialData;
import jp.co.blueflag.shisaquick.jws.base.XmlData;
import jp.co.blueflag.shisaquick.jws.celleditor.ComboBoxCellEditor;
import jp.co.blueflag.shisaquick.jws.celleditor.MiddleCellEditor;
import jp.co.blueflag.shisaquick.jws.celleditor.TextAreaCellEditor;
import jp.co.blueflag.shisaquick.jws.celleditor.TextFieldCellEditor;
import jp.co.blueflag.shisaquick.jws.cellrenderer.CheckBoxCellRenderer;
import jp.co.blueflag.shisaquick.jws.cellrenderer.ComboBoxCellRenderer;
import jp.co.blueflag.shisaquick.jws.cellrenderer.MiddleCellRenderer;
import jp.co.blueflag.shisaquick.jws.cellrenderer.TextAreaCellRenderer;
import jp.co.blueflag.shisaquick.jws.cellrenderer.TextFieldCellRenderer;
import jp.co.blueflag.shisaquick.jws.common.*;
import jp.co.blueflag.shisaquick.jws.label.ItemLabel;
import jp.co.blueflag.shisaquick.jws.manager.MaterialMstData;
import jp.co.blueflag.shisaquick.jws.manager.XmlConnection;
import jp.co.blueflag.shisaquick.jws.panel.ManufacturingPanel;
import jp.co.blueflag.shisaquick.jws.textbox.HankakuTextbox;
import jp.co.blueflag.shisaquick.jws.textbox.NumelicTextbox;
import jp.co.blueflag.shisaquick.jws.data.*;
import jp.co.blueflag.shisaquick.jws.disp.AnalysinSubDisp;
import jp.co.blueflag.shisaquick.jws.disp.ColorSubDisp;
import jp.co.blueflag.shisaquick.jws.disp.MaterialSubDisp;
import jp.co.blueflag.shisaquick.jws.disp.MemoSubDisp;

/************************************************************************************
 *
 *   �z���\(����\�@)�e�[�u���N���X
 *   @author TT nishigawa
 *
 ************************************************************************************/
public class Trial1Table extends PanelBase{

	//------------------------------- �f�[�^  ------------------------------------------
	ArrayList aryTrialData;
	PrototypeData PrototypeData;
	ArrayList aryHaigou;
	ArrayList aryShisakuList;

	//------------------------------ �G���[�Ǘ�  ----------------------------------------
	private ExceptionBase ex;

	//-------------------------------- ���  ------------------------------------------
	private ColorSubDisp colorSubDisp = null;
	private AnalysinSubDisp analysinSubDisp = null;
	private MaterialSubDisp materialSubDisp_Copy = null;
	//�yQP@20505�zNo5 2012/10/12 TT H.SHIMA ADD Start
	private MemoSubDisp memoSubDisp = null;
	//�yQP@20505�zNo5 2012/10/12 TT H.SHIMA ADD End

	//----------------------------- �{�^���O���[�v ---------------------------------------
	//ButtonGroup KoteiCheck = new ButtonGroup();
	//ButtonGroup ShisakuCheck = new ButtonGroup();

	//-------------------------------- �w�b�_�[  ----------------------------------------
	private PanelBase HaigoHeader;
	private TableBase ListHeader;

	//-------------------------------- ����  ------------------------------------------
	private TableBase HaigoMeisai;
	private TableBase ListMeisai;

	//----------------------------- �X�N���[���o�[  ---------------------------------------
	private ScrollBase scrollTop;
	private ScrollBase scrollLeft;
	private ScrollBase scrollMain;

	//---------------------- ����w�b�_�[�e�[�u���Z���G�f�B�^�[  -------------------------------
	ArrayList aryHeaderShisakuCellEditor;

	//---------------------- ����w�b�_�[�e�[�u���Z�������_���[  -------------------------------
	ArrayList aryHeaderShisakuCellRenderer;

	//----------------------- �z�����׃e�[�u���Z���G�f�B�^�[  --------------------------------
	MiddleCellEditor HaigoMeisaiCellEditor0;		//1���
	MiddleCellEditor HaigoMeisaiCellEditor1;		//2���
	MiddleCellEditor HaigoMeisaiCellEditor2;		//3���
	MiddleCellEditor HaigoMeisaiCellEditor3;		//4���
	MiddleCellEditor HaigoMeisaiCellEditor4;		//5���
	MiddleCellEditor HaigoMeisaiCellEditor5;		//6���
	MiddleCellEditor HaigoMeisaiCellEditor6;		//7���
	MiddleCellEditor HaigoMeisaiCellEditor7;		//8���

	//---------------------- �z�����׃e�[�u���Z�������_���[  ---------------------------------
	MiddleCellRenderer HaigoMeisaiCellRenderer0;	//1���
	MiddleCellRenderer HaigoMeisaiCellRenderer1;	//2���
	MiddleCellRenderer HaigoMeisaiCellRenderer2;	//3���
	MiddleCellRenderer HaigoMeisaiCellRenderer3;	//4���
	MiddleCellRenderer HaigoMeisaiCellRenderer4;	//5���
	MiddleCellRenderer HaigoMeisaiCellRenderer5;	//6���
	MiddleCellRenderer HaigoMeisaiCellRenderer6;	//7���
	MiddleCellRenderer HaigoMeisaiCellRenderer7;	//8���

	//---------------------- ���얾�׃e�[�u���Z���G�f�B�^�[  ----------------------------------
	ArrayList aryListShisakuCellEditor;

	//---------------------- ���얾�׃e�[�u���Z�������_���[  ----------------------------------
	ArrayList aryListShisakuCellRenderer;

	//------------------------------- ���C��  --------------------------------------------
	private LineBorder line = new LineBorder(Color.BLACK, 1);

	//-------------------------------- �F  ---------------------------------------------
	private Color clBlue = JwsConstManager.SHISAKU_ITEM_COLOR;
	private Color Yellow = JwsConstManager.SHISAKU_ITEM_COLOR2;

	//------------------------------ �I��z��  ------------------------------------------
	ArrayList aryGenryoCheck = new ArrayList();
	int[] gCheck = new int[2]; //�����I���i[0]=�s�A[1]=��j
	int[] kCheck = new int[2]; //�H���I���i[0]=�s�A[1]=��j

	//------------------------------ �����t���O  -----------------------------------------
	boolean blnGenryoCopy = false;

//add start -------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.7
	//�ҏW��
	private int editCol = -1;
	//�Z���ҏW�t���O
	private boolean editFg = false;
	public boolean isEditFg() {
		return editFg;
	}
	public void setEditFg(boolean editFg) {
		this.editFg = editFg;
	}
//add end   -------------------------------------------------------------------------------

//2011/05/11 QP@10181_No.12 TT Nishigawa Add Start -----------------------
	private CheckboxBase allCheck;
//2011/05/11 QP@10181_No.12 TT Nishigawa Add End -------------------------


	/************************************************************************************
	 *
	 *   �R���X�g���N�^
	 *    : �z���\(����\�@)�e�[�u���𐶐�����
	 *   @author TT nishigawa
	 *
	 ************************************************************************************/
	public Trial1Table(){
		super();
		this.setLayout(null);
		this.setBackground(Color.WHITE);
		ex = new ExceptionBase();

		try{

			//����w�b�_�[�e�[�u���Z���G�f�B�^�[
			aryHeaderShisakuCellEditor = new ArrayList();

			//����w�b�_�[�e�[�u���Z�������_���[
			aryHeaderShisakuCellRenderer = new ArrayList();

			//���얾�׃e�[�u���Z���G�f�B�^�[
			aryListShisakuCellEditor = new ArrayList();

			//���얾�׃e�[�u���Z�������_���[
			aryListShisakuCellRenderer = new ArrayList();

			//�F�w���ʐ���
			colorSubDisp = new ColorSubDisp("�F�w����");

			//���̓f�[�^�m�F���
			analysinSubDisp = new AnalysinSubDisp("���앪�̓f�[�^�m�F���");

			//�yQP@20505�zNo5 2012/10/12 TT H.SHIMA ADD Start
			//�������͉��
			memoSubDisp = new MemoSubDisp("�������͉��");
			//�yQP@20505�zNo5 2012/10/12 TT H.SHIMA ADD End


			//�����ꗗ��ʁi�����R�s�[���j
			ArrayList aList = DataCtrl.getInstance().getUserMstData().getAryAuthData();

			//��ʃC���X�^���X����
			for (int i = 0; i < aList.size(); i++) {

				//�����擾
				String[] items = (String[]) aList.get(i);

				//�����ꗗ��ʂ̎g�p���������邩�`�F�b�N����
				if (items[0].toString().equals("150")) {

					//��ʐ���
					materialSubDisp_Copy = new MaterialSubDisp("�����ꗗ���");
					break;
				}
			}

			//�f�[�^�擾
			try{

				//����i�f�[�^�擾
				PrototypeData = DataCtrl.getInstance().getTrialTblData().SearchShisakuhinData();

				//�����f�[�^�擾�i�S���j
				aryTrialData = DataCtrl.getInstance().getTrialTblData().SearchShisakuRetuData(0);

				//�z���f�[�^�擾�i�S���j
				aryHaigou = DataCtrl.getInstance().getTrialTblData().SearchHaigoData(0);

				//���샊�X�g�f�[�^�擾�i�S���j
				aryShisakuList = DataCtrl.getInstance().getTrialTblData().getAryShisakuList();

			}catch(Exception e){

				e.printStackTrace();

			}

			//�z���w�b�_�[����
			HaigoHeader = (PanelBase)DispHaigoHeader();
			this.add(HaigoHeader);

			//�����w�b�_�[����
			ListHeader = (TableBase)DispListHeader();
			scrollTop = new ScrollBase(ListHeader) {
				private static final long serialVersionUID = 1L;

				//�w�b�_�[������
				public void setColumnHeaderView(Component view) {}
			};

			//�z�����א���
			HaigoMeisai = (TableBase)DispHaigoMeisai();
			scrollLeft = new ScrollBase(HaigoMeisai) {
				private static final long serialVersionUID = 1L;

				//�w�b�_�[������
				public void setColumnHeaderView(Component view) {}
			};

			//����񖾍א���
			ListMeisai = (TableBase)DispListMeisai();
			scrollMain = new ScrollBase(ListMeisai) {
				private static final long serialVersionUID = 1L;

				//�w�b�_�[������
				public void setColumnHeaderView(Component view) {}
			};

			//�X�N���[���o�[�ݒ�i����w�b�_�[�e�[�u���j
			scrollTop.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
			scrollTop.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
			scrollTop.setBounds(596, 0, 378, 100);
			scrollTop.setBorder(new LineBorder(Color.BLACK, 1));
			//2011/04/21 QP@10181_No.41 TT T.Satoh Add Start -------------------------
			scrollTop.getHorizontalScrollBar().setMaximum(ListHeader.getColumnCount() * 200);
			scrollTop.getVerticalScrollBar().setMaximum(1000);
			//2011/04/21 QP@10181_No.41 TT T.Satoh Add End ---------------------------

			//�X�N���[���o�[�ݒ�i�z�����׃e�[�u���j
			scrollLeft.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
			scrollLeft.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
			scrollLeft.setBounds(0, 99, 597, 409);
			scrollLeft.setBorder(new LineBorder(Color.BLACK, 1));
			//2011/04/21 QP@10181_No.41 TT T.Satoh Add Start -------------------------
			scrollLeft.getHorizontalScrollBar().setMaximum(1000);
			scrollLeft.getVerticalScrollBar().setMaximum(HaigoMeisai.getRowCount() * 100);
			//2011/04/21 QP@10181_No.41 TT T.Satoh Add End ---------------------------

			//�X�N���[���o�[�ݒ�i���얾�׃e�[�u���j
			scrollMain.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
			scrollMain.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
			scrollMain.setBounds(596, 99, 395, 426);
			scrollMain.setBorder(new LineBorder(Color.BLACK, 1));
			//2011/04/21 QP@10181_No.41 TT T.Satoh Add Start -------------------------
			scrollMain.getHorizontalScrollBar().setMaximum(ListMeisai.getColumnCount() * 200);
			scrollMain.getVerticalScrollBar().setMaximum(ListMeisai.getRowCount() * 100);
			//2011/04/21 QP@10181_No.41 TT T.Satoh Add End ---------------------------

			//�X�N���[���o�[�̓����𓯊�������
			final JScrollBar barTop = this.scrollTop.getHorizontalScrollBar();
			final JScrollBar barLeft = this.scrollLeft.getVerticalScrollBar();
			final JScrollBar barMain_yoko = this.scrollMain.getHorizontalScrollBar();
			final JScrollBar barMain_tate = this.scrollMain.getVerticalScrollBar();

			barMain_yoko.addAdjustmentListener(new AdjustmentListener() {
				public void adjustmentValueChanged(AdjustmentEvent e) {
					barTop.setValue(e.getValue());
				}
			});
			barTop.addAdjustmentListener(new AdjustmentListener() {
				public void adjustmentValueChanged(AdjustmentEvent e) {
					barMain_yoko.setValue(e.getValue());
				}
			});
			barMain_tate.addAdjustmentListener(new AdjustmentListener() {
				public void adjustmentValueChanged(AdjustmentEvent e) {
					barLeft.setValue(e.getValue());
				}
			});
			barLeft.addAdjustmentListener(new AdjustmentListener() {
				public void adjustmentValueChanged(AdjustmentEvent e) {
					barMain_tate.setValue(e.getValue());
				}
			});

			//�z�����ׁA���샊�X�g�e�[�u�����}�E�X�z�C�[���ł�����������
			HaigoMeisai.addMouseWheelListener(new MouseWheelListener (){
				public void mouseWheelMoved(MouseWheelEvent e) {

					//�X�N���[���o�[���擾
					JScrollBar JScrollBar = scrollMain.getVerticalScrollBar();
					int IncScroll = 0;
					int value = JScrollBar.getValue();

					//�����
					if(0<e.getWheelRotation()){
						IncScroll = JScrollBar.getUnitIncrement(+1);
						JScrollBar.setValue(value + IncScroll);

					//������
					}else{
						IncScroll = JScrollBar.getUnitIncrement(-1);
						JScrollBar.setValue(value - IncScroll);
					}
				}
			});

			//�t�H�[�J�X�ݒ�
			this.ListHeaderFocusControl();
			this.HaigoMeisaiFocusControl();
			this.ListMeisaiFocusControl();

			//�p�l���ɒǉ�
			this.add(scrollTop);
			this.add(scrollLeft);
			this.add(scrollMain);

			//������v�Z
			DispGenryohi();

		}catch(Exception e){
			e.printStackTrace();
		}
	}

	/************************************************************************************
	 *
	 *   �z���w�b�_�[����
	 *    : �z���w�b�_�[�����̏����������s��
	 *   @author TT nishigawa
	 *   @return JComponent : �z���w�b�_�[�R���|�[�l���g
	 *
	 ************************************************************************************/
	private JComponent DispHaigoHeader(){
		try{

			//�p�l�������ݒ�
			HaigoHeader = new PanelBase();
			HaigoHeader.setLayout(null);
			HaigoHeader.setBackground(Color.LIGHT_GRAY);
			HaigoHeader.setBorder(new LineBorder(Color.BLACK, 1));
			HaigoHeader.setBounds(0, 0, 597, 100);

			//�R���|�[�l���g�z�u
			//�H�����x��
			ItemLabel hlKotei = new ItemLabel();
			hlKotei.setBorder(line);
			hlKotei.setHorizontalAlignment(SwingConstants.CENTER);
			//2011/05/13 QP@10181_No.71 TT T.Satoh Change Start -------------------------
			//hlKotei.setText("�H��");
			hlKotei.setText("�H��");
			//2011/05/13 QP@10181_No.71 TT T.Satoh Change End ---------------------------
			hlKotei.setBounds(0, 0, 30, 35);
			HaigoHeader.add(hlKotei);

			//����CD���x��
			ItemLabel hlGenryoCd = new ItemLabel();
			hlGenryoCd.setBorder(line);
			hlGenryoCd.setHorizontalAlignment(SwingConstants.CENTER);
			//2011/05/13 QP@10181_No.71 TT T.Satoh Change Start -------------------------
			//hlGenryoCd.setText("����CD");
			hlGenryoCd.setText("����CD");
			//2011/05/13 QP@10181_No.71 TT T.Satoh Change End ---------------------------
			hlGenryoCd.setBounds(29, 0, 101, 35);
			HaigoHeader.add(hlGenryoCd);

			//���������x��
			ItemLabel hlGenryoNm = new ItemLabel();
			hlGenryoNm.setBorder(line);
			hlGenryoNm.setHorizontalAlignment(SwingConstants.CENTER);
			//2011/05/13 QP@10181_No.71 TT T.Satoh Change Start -------------------------
			//hlGenryoNm.setText("������");
			hlGenryoNm.setText("������");
			//2011/05/13 QP@10181_No.71 TT T.Satoh Change End ---------------------------
			hlGenryoNm.setBounds(129, 0, 285, 35);
			HaigoHeader.add(hlGenryoNm);

			//�P�����x��
			ItemLabel hlTanka = new ItemLabel();
			hlTanka.setBorder(line);
			hlTanka.setHorizontalAlignment(SwingConstants.CENTER);
			hlTanka.setText("<html>�P��<br>  (" + JwsConstManager.JWS_MARK_0005 + ")");
			hlTanka.setBounds(413, 0, 82, 35);
			HaigoHeader.add(hlTanka);

			//�������x��
			ItemLabel hlBudomari = new ItemLabel();
			hlBudomari.setBorder(line);
			hlBudomari.setHorizontalAlignment(SwingConstants.CENTER);
			hlBudomari.setText("<html>����<br>(" + JwsConstManager.JWS_MARK_0005 + ")");
			hlBudomari.setBounds(494, 0, 51, 35);
			HaigoHeader.add(hlBudomari);

			//���ܗL�����x��
			ItemLabel hlAbura = new ItemLabel();
			hlAbura.setBorder(line);
			hlAbura.setHorizontalAlignment(SwingConstants.CENTER);
			hlAbura.setText("���ܗL��");
			hlAbura.setBounds(544, 0, 53, 35);
			HaigoHeader.add(hlAbura);

			//���t���x��
			ItemLabel hlHiduke = new ItemLabel();
			hlHiduke.setBackground(Color.lightGray);
			hlHiduke.setHorizontalAlignment(SwingConstants.RIGHT);
			//2011/05/13 QP@10181_No.71 TT T.Satoh Change Start -------------------------
			//hlHiduke.setText("���t");
			hlHiduke.setText("���t");
			//2011/05/13 QP@10181_No.71 TT T.Satoh Change End ---------------------------
			hlHiduke.setBounds(503, 35, 91, 13);
			HaigoHeader.add(hlHiduke);

			//�����NO���x��
			ItemLabel hlSample = new ItemLabel();
			hlSample.setBackground(Color.lightGray);
			hlSample.setHorizontalAlignment(SwingConstants.RIGHT);
			//2011/05/13 QP@10181_No.71 TT T.Satoh Change Start -------------------------
			//hlSample.setText("�����NO");
			hlSample.setText("�����NO");
			//2011/05/13 QP@10181_No.71 TT T.Satoh Change End ---------------------------
			hlSample.setBounds(503, 49, 91, 13);
			HaigoHeader.add(hlSample);

			//�������x��
			ItemLabel hlMemo = new ItemLabel();
			hlMemo.setBackground(Color.lightGray);
			hlMemo.setHorizontalAlignment(SwingConstants.RIGHT);
			hlMemo.setText("����");
			hlMemo.setBounds(503, 65, 91, 13);
			HaigoHeader.add(hlMemo);

			//���FG���x��
			ItemLabel hlInsatu = new ItemLabel();
			hlInsatu.setBackground(Color.lightGray);
			hlInsatu.setHorizontalAlignment(SwingConstants.RIGHT);

//2011/05/13 QP@10181_No.12 TT Nishigawa Change Start -------------------------
			//hlInsatu.setText("���FG");
			hlInsatu.setText("�i�S�R�s�[���̗�w��j���FG");

			//ALL�`�F�b�N�{�b�N�X�ݒ�
			allCheck = new CheckboxBase();
			allCheck.setBackground(Color.lightGray);
			allCheck.setBounds(578, 86, 17, 12);
			//���[�h�ҏW
			if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
					JwsConstManager.JWS_COMPONENT_0021, DataCtrl.getInstance().getParamData().getStrMode())){
				allCheck.setEnabled(false);
			}
			else{
				allCheck.addActionListener(new selectAllCheck());
			}
			this.add(allCheck);
//2011/05/13 QP@10181_No.12 TT Nishigawa Change end   -------------------------

			hlInsatu.setBounds(376, 86, 200, 12);
			HaigoHeader.add(hlInsatu);

		}catch(Exception e){

		}finally{

		}

		return HaigoHeader;
	}

//2011/05/13 QP@10181_No.12 TT Nishigawa Change Start -------------------------
	/************************************************************************************
	 *
	 *   ALL�`�F�b�N�{�b�N�X�I���C�x���g�N���X
	 *   @author TT nishigawa
	 *
	 ************************************************************************************/
	class selectAllCheck implements ActionListener{

		public void actionPerformed(ActionEvent e){
			try{
				//--------------------- ����SEQ&���ӎ���No�擾 --------------------------
				CheckboxBase cb = (CheckboxBase)e.getSource();

				//ALL�`�F�b�N�{�b�N�X���`�F�b�N����Ă���ꍇ
				if(cb.isSelected()){
					//����񃋁[�v
					for(int i=0; i<ListHeader.getColumnCount(); i++){

						//�L�[���ڎ擾
						MiddleCellEditor mceSeq = (MiddleCellEditor)ListHeader.getCellEditor(0, i);
						DefaultCellEditor dceSeq = (DefaultCellEditor)mceSeq.getTableCellEditor(0);
						CheckboxBase chkSeq = (CheckboxBase)dceSeq.getComponent();
						int intSeq  = Integer.parseInt(chkSeq.getPk1());

		    			//�f�[�^�ݒ�
			    		DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuPrintFg(
			    				intSeq,
			    				1,
			    				DataCtrl.getInstance().getUserMstData().getDciUserid());

			    		//�\���l�ݒ�
			    		ListHeader.setValueAt("true", 5, i);

					}
				}
				//ALL�`�F�b�N�{�b�N�X���`�F�b�N����Ă��Ȃ��ꍇ
				else{
					//����񃋁[�v
					for(int i=0; i<ListHeader.getColumnCount(); i++){

						//�L�[���ڎ擾
						MiddleCellEditor mceSeq = (MiddleCellEditor)ListHeader.getCellEditor(0, i);
						DefaultCellEditor dceSeq = (DefaultCellEditor)mceSeq.getTableCellEditor(0);
						CheckboxBase chkSeq = (CheckboxBase)dceSeq.getComponent();
						int intSeq  = Integer.parseInt(chkSeq.getPk1());

		    			//�f�[�^�ݒ�
			    		DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuPrintFg(
			    				intSeq,
			    				0,
			    				DataCtrl.getInstance().getUserMstData().getDciUserid());

			    		//�\���l�ݒ�
			    		ListHeader.setValueAt("false", 5, i);

					}
				}
			}
			catch(Exception ex){
				ex.printStackTrace();

			}
			finally{

			}
		}
	}
//2011/05/13 QP@10181_No.12 TT Nishigawa Change end -------------------------


	/************************************************************************************
	 *
	 *   �����w�b�_�[����
	 *    : �����w�b�_�[�����̏����������s��
	 *   @author TT nishigawa
	 *   @return JComponent :�����w�b�_�[�R���|�[�l���g
	 *
	 ************************************************************************************/
	private JComponent DispListHeader(){
		try{
			//�e�[�u�������ݒ�
			int ListHeaderCol = aryTrialData.size();
			int ListHeaderRow = 6;
			ListHeader = new TableBase(ListHeaderRow,ListHeaderCol){

//add start -------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.7
				//�Z���I����
				public void columnSelectionChanged(ListSelectionEvent e){
					super.columnSelectionChanged(e);
					try{
						//�Čv�Z����
						if(!e.getValueIsAdjusting()){
							AutoCopyKeisan();
						}
					}catch(Exception ex){
						ex.printStackTrace();
					}finally{

					}
				}
//add end   -------------------------------------------------------------------------------


				//--------------------�@�����f�[�^�X�V�@----------------------------
				public void editingStopped( ChangeEvent e ){
					try{
						//�Z���ҏW�I������
						super.editingStopped( e );

						//�ҏW�s��ԍ��擾
						int row = getSelectedRow();
						int column = getSelectedColumn();
						if( row>=0 && column>=0 ){

							//�L�[���ڎ擾
							MiddleCellEditor mceSeq = (MiddleCellEditor)ListHeader.getCellEditor(0, column);
							DefaultCellEditor dceSeq = (DefaultCellEditor)mceSeq.getTableCellEditor(0);
							CheckboxBase chkSeq = (CheckboxBase)dceSeq.getComponent();
							int intSeq  = Integer.parseInt(chkSeq.getPk1());

							//�����I��
							if(row == 0){
								//�񃋁[�v
								for(int i=0; i<ListHeader.getColumnCount(); i++){
									//�I���͏������Ȃ�
									if(i == column){

									}
									//�I���łȂ��ꍇ
									else{
										//�I���`�F�b�N�{�b�N�X��������
										MiddleCellEditor mceChk = (MiddleCellEditor)ListHeader.getCellEditor(0, i);
										DefaultCellEditor dceChk = (DefaultCellEditor)mceChk.getTableCellEditor(0);
										CheckboxBase chkChk = (CheckboxBase)dceChk.getComponent();
										chkChk.setSelected(false);
									}
								}
							}

					    	//���ӎ���No
					    	if(row == 1){

					    		//���[�h�ҏW
								if(DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
										JwsConstManager.JWS_COMPONENT_0017, DataCtrl.getInstance().getParamData().getStrMode())){

									//�\���l�擾
									String insert = getValueAt( row, column ).toString();

									//�f�[�^�ݒ�
						    		DataCtrl.getInstance().getTrialTblData().UpdChuiJikouNo(
						    				intSeq,
						    				DataCtrl.getInstance().getTrialTblData().checkNullString(insert),
						    				DataCtrl.getInstance().getUserMstData().getDciUserid()
						    			);
								}
					    	}
					    	//������t
					    	if(row == 2){

					    		//���[�h�ҏW
					    		if(DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
										JwsConstManager.JWS_COMPONENT_0018, DataCtrl.getInstance().getParamData().getStrMode())){

					    			//�\���l�擾
					    			String insert = getValueAt( row, column ).toString();

					    			//�f�[�^�ݒ�
						    		DataCtrl.getInstance().getTrialTblData().UpdShisaukRetuDate(
						    				intSeq,
						    				DataCtrl.getInstance().getTrialTblData().checkNullString(insert),
						    				DataCtrl.getInstance().getUserMstData().getDciUserid()
						    			);
					    		}
					    	}
					    	//�T���v��No
					    	if(row == 3){

					    		//���[�h�ҏW
					    		if(DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
										JwsConstManager.JWS_COMPONENT_0019, DataCtrl.getInstance().getParamData().getStrMode())){

					    			//�\���l�擾
					    			String insert = getValueAt( row, column ).toString();

					    			//�f�[�^�ݒ�
						    		DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuSampleNo(
						    				intSeq,
						    				DataCtrl.getInstance().getTrialTblData().checkNullString(insert),
						    				DataCtrl.getInstance().getUserMstData().getDciUserid()
						    			);

//mod start -------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.2
						    		//�T���v��No����`�F�b�N
									String chk = DataCtrl.getInstance().getTrialTblData().checkDistSampleNo(intSeq);
									//����T���v��No���Ȃ��ꍇ
									if(chk==null){

									}
									//����T���v��No������ꍇ
									else{
										DataCtrl.getInstance().getMessageCtrl().PrintMessageString(JwsConstManager.JWS_ERROR_0048 + chk);
									}
//mod end --------------------------------------------------------------------------------

					    		}
					    	}
					    	//����
					    	if(row == 4){

					    		//���[�h�ҏW
					    		if(DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
										JwsConstManager.JWS_COMPONENT_0020, DataCtrl.getInstance().getParamData().getStrMode())){

					    			//�\���l�擾
					    			String insert = getValueAt( row, column ).toString();

					    			//�f�[�^�ݒ�
						    		DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuMemo(
						    				intSeq,
						    				DataCtrl.getInstance().getTrialTblData().checkNullString(insert),
						    				DataCtrl.getInstance().getUserMstData().getDciUserid()
						    			);
					    		}
					    	}
					    	//���Flg
					    	if(row == 5){

					    		//���[�h�ҏW
					    		if(DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
										JwsConstManager.JWS_COMPONENT_0021, DataCtrl.getInstance().getParamData().getStrMode())){

					    			//�\���l�擾
					    			String insert = (getValueAt( row, column ).toString()=="true")?"1":"0";

					    			//�f�[�^�ݒ�
						    		DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuPrintFg(
						    				intSeq,
						    				DataCtrl.getInstance().getTrialTblData().checkNullInt(insert),
						    				DataCtrl.getInstance().getUserMstData().getDciUserid());
					    		}
					    	}
						}
					}catch(ExceptionBase be){

					}catch(Exception ex){

					}finally{
						//�e�X�g�\��
						//DataCtrl.getInstance().getTrialTblData().dispTrial();
					}
				}
			};

//add start -------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.7
			ListHeader.addMouseListener(new java.awt.event.MouseAdapter(){
				public void mouseClicked(MouseEvent e) {
					//�Čv�Z����
					try{
						AutoCopyKeisan();
					}catch(Exception ex){
						ex.printStackTrace();
					}
				}
			});
//add end ---------------------------------------------------------------------------------

			ListHeader.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
			ListHeader.setRowHeight(13);
			ListHeader.setAutoResizeMode(ListHeader.AUTO_RESIZE_OFF);
			ListHeader.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			ListHeader.setCellSelectionEnabled(true);

			//�e�[�u���I��
			ListHeader.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
				public void valueChanged(ListSelectionEvent e){
					if(e.getValueIsAdjusting()){

						//����w�b�_�[�̑I�������N���A
						TableCellEditor lmEditor = ListMeisai.getCellEditor();
						if(lmEditor != null){
							ListMeisai.getCellEditor().stopCellEditing();
						}
						ListMeisai.clearSelection();

						//�z�����ׂ̑I�������N���A
						TableCellEditor hmEditor = HaigoMeisai.getCellEditor();
						if(hmEditor != null){
							HaigoMeisai.getCellEditor().stopCellEditing();
						}
						HaigoMeisai.clearSelection();
					}
				}
			});

			//�e�[�u������
			for(int i=0; i<ListHeaderCol; i++){
				//--------------------�@�����f�[�^�擾�@----------------------------
				TrialData TrialData = ((TrialData)aryTrialData.get(i)); //�����f�[�^

				//------------------------- �����I�� ----------------------------

				//------------------------- ���ӎ���No ---------------------------
				//�f�[�^�擾
				String tyuiNum = checkNull(TrialData.getStrTyuiNo());
				ListHeader.setRowHeight(1,21);
				//�l�ݒ�
				ListHeader.setValueAt(tyuiNum, 1, i);

				//------------------------- ���t ---------------------------------
				//�f�[�^�擾
				String Hiduke = TrialData.getStrShisakuHi();
				//�l�ݒ�
				ListHeader.setValueAt(Hiduke, 2, i);

				//------------------------- �����No ------------------------------
				//�f�[�^�擾
				String Sample = TrialData.getStrSampleNo();
				//�l�ݒ�
				ListHeader.setValueAt(Sample, 3, i);

				//------------------------- ���� -----------------------------------
				//�f�[�^�擾
				String memo = TrialData.getStrMemo();
				//�l�ݒ�
				ListHeader.setValueAt( memo, 4, i );

				//------------------------- ���FG --------------------------------
				//�f�[�^�擾
				int Insatu = TrialData.getIntInsatuFlg();
				//�l�ݒ�
				ListHeader.setValueAt((Insatu == 1) ? "true" : "false", 5, i);

				//------------------ Jtable�֒��ԃG�f�B�^�[�������_���[��o�^  ----------------------
				addRetuShisakuColER(i,Integer.toString(TrialData.getIntShisakuSeq()));
			}
			setHeaderShisakuER();

		}catch(Exception e){
			e.printStackTrace();
		}finally{

		}

		return ListHeader;
	}

	/************************************************************************************
	 *
	 *   �����R�[�h���͌���
	 *    : ���͂��ꂽ�����R�[�h�ɂČ����f�[�^������
	 *   @author TT nishigawa
	 *   @param kaishaCd : ��ЃR�[�h
	 *   @param bushoCd  : �����R�[�h
	 *   @param kaishaCd : �����R�[�h
	 *   @return MaterialData : ���������f�[�^
	 *
	 ************************************************************************************/
	public MaterialData conJW110(int kaishaCd,int bushoCd,String GenryoCd) throws ExceptionBase{
		MaterialMstData mmd = new MaterialMstData();
		MaterialData ret = null;
		try{
			//�V�K�����`�F�b�N
			if(GenryoCd.indexOf("N") != -1){
				bushoCd = 0;
			}

			XmlData xmlJW110 = new XmlData();

			//----------------------------- ���M�p�����[�^�i�[  -------------------------------
			String strUser = DataCtrl.getInstance().getParamData().getDciUser().toString();

			//----------------------------- ���MXML�f�[�^�쐬  ------------------------------
			xmlJW110 = new XmlData();
			ArrayList arySetTag = new ArrayList();

			//--------------------------------- Root�ǉ�  ---------------------------------
			xmlJW110.AddXmlTag("","JW110");
			arySetTag.clear();

			//--------------------------- �@�\ID�ǉ��iUSEERINFO�j  --------------------------
			xmlJW110.AddXmlTag("JW110", "USERINFO");
			//�@�e�[�u���^�O�ǉ�
			xmlJW110.AddXmlTag("USERINFO", "table");
			//�@���R�[�h�ǉ�
			arySetTag.add(new String[]{"kbn_shori", "3"});
			arySetTag.add(new String[]{"id_user",strUser});
			xmlJW110.AddXmlTag("table", "rec", arySetTag);
			//�z�񏉊���
			arySetTag.clear();

			//------------------------- �@�\ID�ǉ��i�����R�[�h�����j  --------------------------
			xmlJW110.AddXmlTag("JW110", "SA580");
			//�@�e�[�u���^�O�ǉ�
			xmlJW110.AddXmlTag("SA580", "table");
			//�@���R�[�h�ǉ�
			arySetTag.add(new String[]{"cd_kaisha", checkNull(kaishaCd)});
			arySetTag.add(new String[]{"cd_busho" , checkNull(bushoCd)});
			arySetTag.add(new String[]{"cd_genryo", checkNull(GenryoCd)});
			xmlJW110.AddXmlTag("table", "rec", arySetTag);
			//�z�񏉊���
			arySetTag.clear();

			//----------------------------------- XML���M  ----------------------------------
//			System.out.println("JW110���MXML===============================================================");
//			xmlJW110.dispXml();
			XmlConnection xcon = new XmlConnection(xmlJW110);
			xcon.setStrAddress(DataCtrl.getInstance().getJnlpConnect().getStrAddressAjax());
			xcon.XmlSend();

			//----------------------------------- XML��M  ----------------------------------
			xmlJW110 = xcon.getXdocRes();
			System.out.println();
//			System.out.println("JW110��MXML===============================================================");
//			xmlJW110.dispXml();

			//�e�X�gXML�f�[�^
			//xmlJW110 = new XmlData(new File("src/main/JW110.xml"));

			//--------------------------------- �e��f�[�^�ݒ�  -------------------------------
			//Result�f�[�^
			DataCtrl.getInstance().getResultData().setResultData(xmlJW110);
			if(DataCtrl.getInstance().getResultData().getStrReturnFlg().equals("true")){

				//�����f�[�^�ݒ�
				mmd.setMaterialData(xmlJW110,"SA580");

				//�����f�[�^�擾
				MaterialData selMmd = (MaterialData)mmd.getAryMateData().get(0);

				//�������ʂ����݂���ꍇ�@���@�p�~�����łȂ��ꍇ
				if(selMmd.getStrGenryocd() != null && selMmd.getIntHaisicd() == 0){

					//�ԋp�l�Ɍ����f�[�^�i�[
					ret = selMmd;

				}

			}else{

				//�ʐM�G���[
				ExceptionBase ExceptionBase = new ExceptionBase();
				throw ExceptionBase;

			}

		}catch(ExceptionBase be){
			be.printStackTrace();
			throw be;

		}catch(Exception e){
			e.printStackTrace();

		}finally{


		}
		return ret;
	}

	/************************************************************************************
	 *
	 *   �z�����א���
	 *    : �z�����א����̏����������s��
	 *   @author TT nishigawa
	 *   @return JComponent :�z�����׃R���|�[�l���g
	 *
	 ************************************************************************************/
	private JComponent DispHaigoMeisai(){
		try{
			int kotei_num = DataCtrl.getInstance().getTrialTblData().getIntMaxKotei();
			int row = DataCtrl.getInstance().getTrialTblData().getIntMaxKotei() + aryHaigou.size();
			// MOD start 20120914 QP@20505 No.1
			//2011/06/13 QP@10181_No.29 TT T.Satoh Change Start -------------------------
			//int keisan = kotei_num+8;
//			int keisan = kotei_num+9;
			int keisan = (kotei_num*2)+9;
			//2011/06/13 QP@10181_No.29 TT T.Satoh Change End ---------------------------
			// MOD end 20120914 QP@20505 No.1

			//�e�[�u�������ݒ�
			HaigoMeisai = new TableBase((row+keisan),8){

//add start -------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.7
				//�Z���I����
				public void columnSelectionChanged(ListSelectionEvent e){
					super.columnSelectionChanged(e);
					try{
						//�Čv�Z����
						if(!e.getValueIsAdjusting()){
							AutoCopyKeisan();
						}
					}catch(Exception ex){
						ex.printStackTrace();
					}finally{

					}
				}
//add end   -------------------------------------------------------------------------------

				//�Z���̕ҏW�I����
				public void editingStopped( ChangeEvent e ){

					//------------------------ �z���f�[�^�X�V ------------------------------
					try{

						super.editingStopped( e );

						//2011/05/12 QP@10181_No.73 TT T.Satoh Add Start -------------------------
						//��ЃR�[�h�ێ��p
						int hojiKaisyaCd = 0;
						//2011/05/12 QP@10181_No.73 TT T.Satoh Add End ---------------------------

						//�ҏW�s��ԍ��擾
						int row = getSelectedRow();
						int column = getSelectedColumn();
						if( row>=0 && column>=0 ){

							//---------------- �L�[���ڎ擾  -------------------------------
							int intKoteiCd  = -1;
							int intKoteiSeq = -1;
							int intGenryoFg;
							MiddleCellEditor mceKey = (MiddleCellEditor)HaigoMeisai.getCellEditor(row, 2);
							DefaultCellEditor dceKey = (DefaultCellEditor)mceKey.getTableCellEditor(row);

							//�����s�̏ꍇ
							if(dceKey.getComponent() instanceof CheckboxBase){

								//�L�[���ڎ擾
								CheckboxBase chkKey = (CheckboxBase)dceKey.getComponent();
								intKoteiCd  = Integer.parseInt(chkKey.getPk1());
								intKoteiSeq = Integer.parseInt(chkKey.getPk2());
								intGenryoFg = 0;

							//�H���s�̏ꍇ
							}else{

								//�R���|�[�l���g�擾
								MiddleCellEditor mceKeyKotei = (MiddleCellEditor)HaigoMeisai.getCellEditor(row, 0);
								DefaultCellEditor dceKeyKotei = (DefaultCellEditor)mceKeyKotei.getTableCellEditor(row);

								//�R���|�[�l���g���`�F�b�N�{�b�N�X�̏ꍇ
								if(dceKeyKotei.getComponent() instanceof CheckboxBase){

									//�L�[���ڎ擾
									CheckboxBase chkKey = (CheckboxBase)dceKeyKotei.getComponent();
									intKoteiCd  = Integer.parseInt(chkKey.getPk1());
									intKoteiSeq = Integer.parseInt(chkKey.getPk2());
									intGenryoFg = 1;

								//�v�Z�s�̏ꍇ
								}else{
									intGenryoFg = 2;
								}
							}

					    	//------------ ����CD or �H������  ----------------------------
							if(column == 3){

								//����CD
								if(intGenryoFg == 0){


//add start -------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.33,34
									//�H���L�[���ڎ擾
									int intShisakuSeq = 0;
									boolean hanshu_chk = DataCtrl.getInstance().getTrialTblData().checkListHenshuOkFg(intShisakuSeq, intKoteiCd, intKoteiSeq);

									//�ҏW�\�̏ꍇ�F��������
									if(hanshu_chk){

									}
									//�ҏW�s�̏ꍇ�F�������Ȃ�
									else{
										return;
									}
//add end   -------------------------------------------------------------------------------

									//���[�h�ҏW
						    		if(DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
											JwsConstManager.JWS_COMPONENT_0025, DataCtrl.getInstance().getParamData().getStrMode())){

						    			//�\���l�擾
						    			String insert = getValueAt( row, column ).toString();

										//���͌����擾
										int keta = DataCtrl.getInstance().getTrialTblData().getKaishaGenryo();

						    			//�O0���ߏ���(�����R�[�h�����͂���Ă���ꍇ ���� �����R�[�h���͎�)
						    			if(insert != null &&insert.length() > 0 && !blnGenryoCopy){

						    				//�V�K�����łȂ��ꍇ
						    				if(insert.matches("^[0-9.]+$")){

						    					//���͕������擾
						    					int m = insert.length();

						    					//0���߃��[�v
						    					for(int l=m; l < keta; l++){
						    						insert = "0"+insert;
								    			}
						    				}
						    			}
						    			setValueAt(insert,row,column);

						    			//���H�ꌴ���擾�p
						    			MaterialData md3 = new MaterialData();

						    			//������������
										ArrayList aryHaigo = DataCtrl.getInstance().getTrialTblData().SearchHaigoData(intKoteiCd);
										MixedData selMixed = new MixedData();
										for(int i=0;i<aryHaigo.size();i++){
											MixedData MixedData = (MixedData)aryHaigo.get(i);
											if(MixedData.getIntKoteiSeq() == intKoteiSeq){
												selMixed = MixedData;
											}
										}

										//�B��ʎw���ЁA�H��R�[�h�擾
					    				int protoKaisha = DataCtrl.getInstance().getTrialTblData().SearchShisakuhinData().getIntKaishacd();
					    				int protoBusho = DataCtrl.getInstance().getTrialTblData().SearchShisakuhinData().getIntKojoco();

						    			//�����R�[�h�R�s�[or���͂̔��f
						    			if(blnGenryoCopy){

						    				//�����R�[�h�R�s�[��

						    				//�R�s�[���ꂽ���������H��̏ꍇ
						    				if(selMixed.getIntKaishaCd() == protoKaisha
						    						&& selMixed.getIntBushoCd() == protoBusho){

						    					//��������
						    					md3 = null;

						    				//�R�s�[���ꂽ�������V�K�����̏ꍇ
						    				}else if(selMixed.getIntKaishaCd() == protoKaisha
						    						&& selMixed.getIntBushoCd() == 0){

						    					//��������
						    					md3 = null;

						    				//�R�s�[���ꂽ����������Ђ̏ꍇ
						    				}else if(selMixed.getIntKaishaCd() != protoKaisha){

						    					//��������
						    					md3 = null;

						    				}else{


//add start--------------------------------------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.8
						    					//���H�ꌴ������
							    				//md3 = conJW110(protoKaisha,protoBusho,insert);

						    					//�������̏ꍇ�͌������s��Ȃ�
							    				if(protoBusho == Integer.parseInt(JwsConstManager.JWS_CD_DAIHYO_KOJO)
							    						&& protoKaisha == Integer.parseInt(JwsConstManager.JWS_CD_DAIHYO_KAISHA) ){
							    					md3 = null;
							    				}
							    				else{
							    					md3 = conJW110(protoKaisha,protoBusho,insert);
							    				}
//add end---------------------------------------------------------------------------------------------------------------

						    				}

						    			}else{

						    				//�����R�[�h���͎�

						    				//��{���(����\�B)�w��S����Ђ�z���f�[�^�ɐݒ�
											DataCtrl.getInstance().getTrialTblData().UpdHaigoKaishaCd(
													intKoteiCd,
													intKoteiSeq,
													protoKaisha,
													DataCtrl.getInstance().getUserMstData().getDciUserid()
												);

											//�V�K�����̏ꍇ
											if(!insert.matches("^[0-9.]+$")){
												protoBusho = 0;
											}

											//��{���(����\�B)�w��S���H���z���f�[�^�ɐݒ�
											DataCtrl.getInstance().getTrialTblData().UpdHaigoKojoCd(
													intKoteiCd,
													intKoteiSeq,
													protoBusho,
													DataCtrl.getInstance().getUserMstData().getDciUserid()
												);
						    			}

										//�R�~�b�g�i�����R�[�h�j
										DataCtrl.getInstance().getTrialTblData().UpdHaigoGenryoCd(
												intKoteiCd,
												intKoteiSeq,
												DataCtrl.getInstance().getTrialTblData().checkNullString(insert),
												DataCtrl.getInstance().getUserMstData().getDciUserid()
											);

										//���͌����R�[�h����
										int kaishaCd = selMixed.getIntKaishaCd();
										int bushoCd = selMixed.getIntBushoCd();

										//�R�����g�s����
										boolean comFlg = DataCtrl.getInstance().getTrialTblData().commentChk(insert, keta);

										//�����R�[�h����łȂ��ꍇ AND �R�����g�s�łȂ��ꍇ�Ɍ���
										if(insert != null && insert.length() > 0 && !comFlg){

											//����
											MaterialData md = conJW110(kaishaCd,bushoCd,insert);

											//�������sFg
											boolean exe = true;

											//�������ʂ����������ꍇ
											if(md != null){

												//�����R�s�[�̏ꍇ
												if(blnGenryoCopy){

													//���H�ꌴ�������݂��Ȃ��ꍇ
													if(md3 == null){

														//��������

													//���H�ꌴ�������݂���ꍇ
													}else{

														//�m�F�_�C�A���O�\��
														int option = JOptionPane.showConfirmDialog(
																this,
																"���H��Ɍ��������݂��܂��B���H��̌������g�p���܂����H"
																, "�m�F���b�Z�[�W"
																,JOptionPane.YES_NO_OPTION
																,JOptionPane.PLAIN_MESSAGE
															);

														//�u�͂��v����
													    if (option == JOptionPane.YES_OPTION){

													    	//���H�ꌴ���ݒ�
													    	md = md3;

													    	//�z���f�[�^�̉�ЃR�[�h�ɇB�w���ЃR�[�h�ݒ�
													    	DataCtrl.getInstance().getTrialTblData().UpdHaigoKaishaCd(
																	intKoteiCd,
																	intKoteiSeq,
																	protoKaisha,
																	DataCtrl.getInstance().getUserMstData().getDciUserid()
																);

													    	//�z���f�[�^�̕����R�[�h�ɇB�w�蕔���R�[�h�ݒ�
													    	DataCtrl.getInstance().getTrialTblData().UpdHaigoKojoCd(
																	intKoteiCd,
																	intKoteiSeq,
																	protoBusho,
																	DataCtrl.getInstance().getUserMstData().getDciUserid()
																);

													    //�u�������v����
													    }else if (option == JOptionPane.NO_OPTION){

													    	//�������Ȃ�

													    }

													}

													//�R�s�[�t���O��������
													blnGenryoCopy = false;

												}

												//�������s
												if(exe){

													//�R�~�b�g�i�������́j
													//�������擾
													String GenryoNm = md.getStrGenryonm();

													//����i�f�[�^�擾
													PrototypeData selPrototype = DataCtrl.getInstance().getTrialTblData().SearchShisakuhinData();

													//����i-�w���ЁA�����R�[�h�擾
													int hinKaishaCd = selPrototype.getIntKaishacd();
													int hinBushoCd  = selPrototype.getIntKojoco();

													//��������-��ЃR�[�h�A�����R�[�h�擾
													int haigoKaishaCd = selMixed.getIntKaishaCd();
													int haigoBushoCd = selMixed.getIntBushoCd();

													//2011/05/12 QP@10181_No.73 TT T.Satoh Add Start -------------------------
													//��ЃR�[�h�ێ�
													hojiKaisyaCd = haigoKaishaCd;
													//2011/05/12 QP@10181_No.73 TT T.Satoh Add End ---------------------------

													//��ЁE�H��L���ݒ�
													if(hinKaishaCd == haigoKaishaCd){
														if(hinBushoCd != haigoBushoCd){
															if(haigoBushoCd > 0){

																//���{��������ݒ�
																GenryoNm = JwsConstManager.JWS_MARK_0002 + delMark(GenryoNm);

															}
														}
													}else{

															//���{��������ݒ�
															GenryoNm = JwsConstManager.JWS_MARK_0001 + delMark(GenryoNm);

													}

//add start--------------------------------------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.8
													if(hinBushoCd == Integer.parseInt(JwsConstManager.JWS_CD_DAIHYO_KOJO)
															&& hinKaishaCd == Integer.parseInt(JwsConstManager.JWS_CD_DAIHYO_KAISHA) ){
														//�z���f�[�^�̕����R�[�h�ɇB�w�蕔���R�[�h�ݒ�
												    	DataCtrl.getInstance().getTrialTblData().UpdHaigoKojoCd(
																intKoteiCd,
																intKoteiSeq,
																md.getIntBushocd(),
																DataCtrl.getInstance().getUserMstData().getDciUserid()
															);
													}
//add end---------------------------------------------------------------------------------------------------------------


													//�\���l�}��
													setValueAt( GenryoNm, row, 4 );

													//�f�[�^�}��
													DataCtrl.getInstance().getTrialTblData().UpdHaigoGenryoMei(
															intKoteiCd,
															intKoteiSeq,
															GenryoNm,
															DataCtrl.getInstance().getUserMstData().getDciUserid()
														);

													//�R�~�b�g�i�P���j
													setValueAt( md.getDciTanka(), row, 5 );
													DataCtrl.getInstance().getTrialTblData().UpdHaigoGenryoTanka(
															intKoteiCd,
															intKoteiSeq,
															md.getDciTanka(),
															DataCtrl.getInstance().getUserMstData().getDciUserid()
														);

													//�R�~�b�g�i�����j
													setValueAt( md.getDciBudomari(), row, 6 );
													DataCtrl.getInstance().getTrialTblData().UpdHaigoGenryoBudomari(
															intKoteiCd,
															intKoteiSeq,
															md.getDciBudomari(),
															DataCtrl.getInstance().getUserMstData().getDciUserid()
														);

//add start -------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.31
													DataCtrl.getInstance().getTrialTblData().UpdHaigoGenryoMaBudomari(
															intKoteiCd,
															intKoteiSeq,
															md.getMa_dciBudomari()
														);
//add end   -------------------------------------------------------------------------------


													//�R�~�b�g�i���ܗL���j
													setValueAt( md.getDciGanyu(), row, 7 );
													DataCtrl.getInstance().getTrialTblData().UpdHaigoGenryoYuganyuryo(
															intKoteiCd,
															intKoteiSeq,
															md.getDciGanyu(),
															DataCtrl.getInstance().getUserMstData().getDciUserid()
														);

													//�R�~�b�g�i�|�_�j
													DataCtrl.getInstance().getTrialTblData().UpdHaigoGenryoSakusan(
															intKoteiCd,
															intKoteiSeq,
															md.getDciSakusan(),
															DataCtrl.getInstance().getUserMstData().getDciUserid()
														);

													//�R�~�b�g�i�H���j
													DataCtrl.getInstance().getTrialTblData().UpdHaigoGenryoSyokuen(
															intKoteiCd,
															intKoteiSeq,
															md.getDciShokuen(),
															DataCtrl.getInstance().getUserMstData().getDciUserid()
														);

													//�R�~�b�g�i���_�j
													DataCtrl.getInstance().getTrialTblData().UpdHaigoGenryoSousan(
															intKoteiCd,
															intKoteiSeq,
															md.getDciSousan(),
															DataCtrl.getInstance().getUserMstData().getDciUserid()
														);
								// ADD start 20121002 QP@20505 No.24
													//�R�~�b�g�i�l�r�f�j
													DataCtrl.getInstance().getTrialTblData().UpdHaigoGenryoMsg(
															intKoteiCd,
															intKoteiSeq,
															md.getDciMsg(),
															DataCtrl.getInstance().getUserMstData().getDciUserid()
														);
								// ADD end 20121002 QP@20505 No.24
												}

											//�������ʂ��Ȃ��ꍇ
											}else{

												String GenryoNm = "";

												//����������łȂ��ꍇ
												if(selMixed.getStrGenryoNm() != null){

													//���{�������@�ݒ�
													if(delMark(selMixed.getStrGenryoNm()) == null){
														GenryoNm = JwsConstManager.JWS_MARK_0001;
													}
													else{
														GenryoNm = JwsConstManager.JWS_MARK_0001 + delMark(selMixed.getStrGenryoNm());
													}

												}else{

													//���@�ݒ�
													GenryoNm = JwsConstManager.JWS_MARK_0001;

												}

												//���@��\��
												setValueAt( GenryoNm, row, 4 );

												//�f�[�^�}��
												DataCtrl.getInstance().getTrialTblData().UpdHaigoGenryoMei(
														intKoteiCd,
														intKoteiSeq,
														GenryoNm,
														DataCtrl.getInstance().getUserMstData().getDciUserid()
													);

												//�����f�[�^�擾
												ArrayList aList = DataCtrl.getInstance().getUserMstData().getAryAuthData();

												//��ʂ̎g�p�����m�FFg
												boolean kengen = false;

												//��ʃC���X�^���X����
												for (int i = 0; i < aList.size(); i++) {

													//���ID�擾
													String[] items = (String[]) aList.get(i);

													//�����ꗗ��ʂ̎g�p���������邩�`�F�b�N����
													if (items[0].toString().equals("150")) {

														kengen = true;

														//��ЃR�[�h�`�F�b�N
														KaishaData KaishaData = materialSubDisp_Copy.getMaterialPanel().getKaishaData();
														ArrayList aryKaishaCd = KaishaData.getArtKaishaCd();
														ArrayList aryKaishaNm = KaishaData.getAryKaishaNm();
														boolean chk = false;
														for(int k=0; k<aryKaishaCd.size(); k++){
															String cd = (String)aryKaishaCd.get(k);

															//�����ꗗ�������ɓ���ЃR�[�h�����݂���ꍇ�̂�
															if(Integer.parseInt(cd) == kaishaCd){
																chk = true;

																//��Ж��擾
																String kaishaNm = (String)aryKaishaNm.get(k);

																//��ʏ�����
																materialSubDisp_Copy.getMaterialPanel().clearDisp(false);

																//�������ڐݒ�
																materialSubDisp_Copy.getMaterialPanel().getCodeTextbox().setText(insert);
																materialSubDisp_Copy.getMaterialPanel().getKaishaComb().setSelectedItem(kaishaNm);

																//��ʔ�\��
																materialSubDisp_Copy.setVisible(false);

																//��������
																try{
																	materialSubDisp_Copy.getMaterialPanel().clickSearchBtn("1", true);
																	materialSubDisp_Copy.setVisible(true);
																}catch(ExceptionBase eb){
																	//DataCtrl.getInstance().PrintMessage(eb);
																}
															}
														}
														//�������ň����Ȃ���ЃR�[�h�̏ꍇ
														if(!chk){
															DataCtrl.getInstance().getMessageCtrl().PrintMessageString("�����Ȃ���ЃR�[�h���ݒ肳��Ă��܂��B�����̍Đݒ�����ĉ������B");
														}
													}
												}
												//�����ꗗ�̉{���������Ȃ��ꍇ
												if(!kengen){
													DataCtrl.getInstance().getMessageCtrl().PrintMessageString("�����ꗗ��ʂ̉{������������܂���B");
												}
											}
										}

										//�����R�[�h���R�����g�s�̏ꍇ�ɏ�����
										else if(insert != null && insert.length() > 0 && comFlg){

											//--------------------------- �z���f�[�^������ -----------------------------

											//������
											setValueAt( null, row, 4 );
											DataCtrl.getInstance().getTrialTblData().UpdHaigoGenryoMei(
													intKoteiCd,
													intKoteiSeq,
													null,
													DataCtrl.getInstance().getUserMstData().getDciUserid()
												);

											//�R�~�b�g�i�P���j
											setValueAt( null, row, 5 );
											DataCtrl.getInstance().getTrialTblData().UpdHaigoGenryoTanka(
													intKoteiCd,
													intKoteiSeq,
													null,
													DataCtrl.getInstance().getUserMstData().getDciUserid()
												);

											//�R�~�b�g�i�����j
											setValueAt( null, row, 6 );
											DataCtrl.getInstance().getTrialTblData().UpdHaigoGenryoBudomari(
													intKoteiCd,
													intKoteiSeq,
													null,
													DataCtrl.getInstance().getUserMstData().getDciUserid()
												);

											//�R�~�b�g�i���ܗL���j
											setValueAt( null, row, 7 );
											DataCtrl.getInstance().getTrialTblData().UpdHaigoGenryoYuganyuryo(
													intKoteiCd,
													intKoteiSeq,
													null,
													DataCtrl.getInstance().getUserMstData().getDciUserid()
												);

											//�R�~�b�g�i�|�_�j
											DataCtrl.getInstance().getTrialTblData().UpdHaigoGenryoSakusan(
													intKoteiCd,
													intKoteiSeq,
													null,
													DataCtrl.getInstance().getUserMstData().getDciUserid()
												);

											//�R�~�b�g�i�H���j
											DataCtrl.getInstance().getTrialTblData().UpdHaigoGenryoSyokuen(
													intKoteiCd,
													intKoteiSeq,
													null,
													DataCtrl.getInstance().getUserMstData().getDciUserid()
												);

											//�R�~�b�g�i���_�j
											DataCtrl.getInstance().getTrialTblData().UpdHaigoGenryoSousan(
													intKoteiCd,
													intKoteiSeq,
													null,
													DataCtrl.getInstance().getUserMstData().getDciUserid()
												);
// ADD start 20121002 QP@20505 No.24
											//�R�~�b�g�i�l�r�f�j
											DataCtrl.getInstance().getTrialTblData().UpdHaigoGenryoMsg(
													intKoteiCd,
													intKoteiSeq,
													null,
													DataCtrl.getInstance().getUserMstData().getDciUserid()
												);
// ADD end 20121002 QP@20505 No.24

											//--------------------------- �ʃf�[�^������ -----------------------------
											for(int i=0; i<ListHeader.getColumnCount(); i++){

												//�L�[�R���|�[�l���g�擾
												MiddleCellEditor mcHeader = (MiddleCellEditor)ListHeader.getCellEditor(0, i);
												DefaultCellEditor dcHeader = (DefaultCellEditor)mcHeader.getTableCellEditor(0);
												CheckboxBase cbHeader = (CheckboxBase)dcHeader.getComponent();

												//����SEQ�擾
												int sisakuSeq = Integer.parseInt(cbHeader.getPk1());

												//�ʃf�[�^������
												ListMeisai.setValueAt(null, row, i);
												DataCtrl.getInstance().getTrialTblData().UpdShisakuListRyo(
														sisakuSeq,
														intKoteiCd,
														intKoteiSeq,
														null
													);

											}

										}

						    		}

						    		//�H�����v�v�Z
									for(int col=0; col<ListMeisai.getColumnCount(); col++){
										koteiSum(col);
									}

									//�����v�Z
									AutoKeisan();

						    		//������v�Z
									DispGenryohi();

//add start -------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.30
									//�V�K����or�R�����g�sorNULL
									boolean sinki_chk = DataCtrl.getInstance().getTrialTblData().searchHaigouGenryoCdSinki(intKoteiCd, intKoteiSeq);

									//���[�h�ҏW�ێ擾
									boolean modeChk = DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
											JwsConstManager.JWS_COMPONENT_0025, DataCtrl.getInstance().getParamData().getStrMode());

									//�������擾
									String genryoNm = (String)HaigoMeisai.getValueAt(row, column);

									//�V�K����or�R�����g�sorNULL�̏ꍇ�@���@���[�h�ҏW�\�ȏꍇ�@�F�@�ҏW�\
									if(sinki_chk && modeChk){
										int color = Integer.parseInt(DataCtrl.getInstance().getTrialTblData().searchHaigouGenryoColor(intKoteiCd, intKoteiSeq));

										//�R���|�[�l���g�擾�i�������j
										DefaultCellEditor dceGenryoNm = (DefaultCellEditor)HaigoMeisaiCellEditor4.getTableCellEditor(row);
										((TextboxBase)dceGenryoNm.getComponent()).setEnabled(true);
										((TextboxBase)dceGenryoNm.getComponent()).setEditable(true);
										TextFieldCellRenderer tfcrGenryoNm =  (TextFieldCellRenderer)HaigoMeisaiCellRenderer4.getTableCellRenderer(row);
										tfcrGenryoNm.setColor(new Color(color));

										//�R���|�[�l���g�擾�i�P���j
										DefaultCellEditor dceTanka = (DefaultCellEditor)HaigoMeisaiCellEditor5.getTableCellEditor(row);
										((TextboxBase)dceTanka.getComponent()).setEnabled(true);
										((TextboxBase)dceTanka.getComponent()).setEditable(true);
										TextFieldCellRenderer tfcrTanka =  (TextFieldCellRenderer)HaigoMeisaiCellRenderer5.getTableCellRenderer(row);
										tfcrTanka.setColor(new Color(color));

									}
									//2011/05/12 QP@10181_No.73 TT T.Satoh Add Start -------------------------
									//���[�h�ҏW�s�̏ꍇ
									else if (!modeChk) {
										//�R���|�[�l���g�擾�i�������j:�ҏW�s�\
										DefaultCellEditor dceGenryoNm = (DefaultCellEditor)HaigoMeisaiCellEditor4.getTableCellEditor(row);
										((TextboxBase)dceGenryoNm.getComponent()).setEnabled(false);

										//�R���|�[�l���g�擾�i�P���j:�ҏW�s�\
										DefaultCellEditor dceTanka = (DefaultCellEditor)HaigoMeisaiCellEditor5.getTableCellEditor(row);
										((TextboxBase)dceTanka.getComponent()).setEnabled(false);
									}
									//��{���̉�ЃR�[�h���L���[�s�[�ł͂Ȃ��ꍇ
									else if(DataCtrl.getInstance().getTrialTblData().SearchShisakuhinData().getIntKaishacd() != Integer.parseInt(JwsConstManager.JWS_CD_KEWPIE)){
										//�R���|�[�l���g�擾�i�������j:�ҏW�s�\
										DefaultCellEditor dceGenryoNm = (DefaultCellEditor)HaigoMeisaiCellEditor4.getTableCellEditor(row);
										((TextboxBase)dceGenryoNm.getComponent()).setEnabled(false);
										TextFieldCellRenderer tfcrGenryoNm =  (TextFieldCellRenderer)HaigoMeisaiCellRenderer4.getTableCellRenderer(row);
										tfcrGenryoNm.setColor(JwsConstManager.JWS_DISABLE_COLOR);

										//�R���|�[�l���g�擾�i�P���j:�ҏW�\
										DefaultCellEditor dceTanka = (DefaultCellEditor)HaigoMeisaiCellEditor5.getTableCellEditor(row);
										((TextboxBase)dceTanka.getComponent()).setEnabled(true);
										((TextboxBase)dceTanka.getComponent()).setEditable(true);
										TextFieldCellRenderer tfcrTanka =  (TextFieldCellRenderer)HaigoMeisaiCellRenderer5.getTableCellRenderer(row);
										tfcrTanka.setColor(Color.WHITE);
									}

									//�������̏ꍇ
									else if(genryoNm != null && genryoNm.substring(0, 1).equals(JwsConstManager.JWS_MARK_0001)){
										//�R���|�[�l���g�擾�i�������j:�ҏW�s�\
										DefaultCellEditor dceGenryoNm = (DefaultCellEditor)HaigoMeisaiCellEditor4.getTableCellEditor(row);
										((TextboxBase)dceGenryoNm.getComponent()).setEnabled(false);
										TextFieldCellRenderer tfcrGenryoNm =  (TextFieldCellRenderer)HaigoMeisaiCellRenderer4.getTableCellRenderer(row);
										tfcrGenryoNm.setColor(JwsConstManager.JWS_DISABLE_COLOR);

										//�R���|�[�l���g�擾�i�P���j:�ҏW�\
										DefaultCellEditor dceTanka = (DefaultCellEditor)HaigoMeisaiCellEditor5.getTableCellEditor(row);
										((TextboxBase)dceTanka.getComponent()).setEnabled(true);
										((TextboxBase)dceTanka.getComponent()).setEditable(true);
										TextFieldCellRenderer tfcrTanka =  (TextFieldCellRenderer)HaigoMeisaiCellRenderer5.getTableCellRenderer(row);
										tfcrTanka.setColor(Color.WHITE);
									}

									//�����̉�ЃR�[�h���L���[�s�[�ł͂Ȃ��ꍇ
									else if (hojiKaisyaCd != Integer.parseInt(JwsConstManager.JWS_CD_KEWPIE)) {
										//�R���|�[�l���g�擾�i�������j:�ҏW�s�\
										DefaultCellEditor dceGenryoNm = (DefaultCellEditor)HaigoMeisaiCellEditor4.getTableCellEditor(row);
										((TextboxBase)dceGenryoNm.getComponent()).setEnabled(false);
										TextFieldCellRenderer tfcrGenryoNm =  (TextFieldCellRenderer)HaigoMeisaiCellRenderer4.getTableCellRenderer(row);
										tfcrGenryoNm.setColor(JwsConstManager.JWS_DISABLE_COLOR);

										//�R���|�[�l���g�擾�i�P���j:�ҏW�\
										DefaultCellEditor dceTanka = (DefaultCellEditor)HaigoMeisaiCellEditor5.getTableCellEditor(row);
										((TextboxBase)dceTanka.getComponent()).setEnabled(true);
										((TextboxBase)dceTanka.getComponent()).setEditable(true);
										TextFieldCellRenderer tfcrTanka =  (TextFieldCellRenderer)HaigoMeisaiCellRenderer5.getTableCellRenderer(row);
										tfcrTanka.setColor(Color.WHITE);
									}
									//2011/05/12 QP@10181_No.73 TT T.Satoh Add End ---------------------------
									//���������̏ꍇ�F�ҏW�s��
									else{
										//�R���|�[�l���g�擾�i�������j
										DefaultCellEditor dceGenryoNm = (DefaultCellEditor)HaigoMeisaiCellEditor4.getTableCellEditor(row);
										((TextboxBase)dceGenryoNm.getComponent()).setEnabled(false);
										TextFieldCellRenderer tfcrGenryoNm =  (TextFieldCellRenderer)HaigoMeisaiCellRenderer4.getTableCellRenderer(row);
										tfcrGenryoNm.setColor(JwsConstManager.JWS_DISABLE_COLOR);

										//�R���|�[�l���g�擾�i�P���j
										DefaultCellEditor dceTanka = (DefaultCellEditor)HaigoMeisaiCellEditor5.getTableCellEditor(row);
										((TextboxBase)dceTanka.getComponent()).setEnabled(false);
										TextFieldCellRenderer tfcrTanka =  (TextFieldCellRenderer)HaigoMeisaiCellRenderer5.getTableCellRenderer(row);
										tfcrTanka.setColor(JwsConstManager.JWS_DISABLE_COLOR);
									}
//add end   -------------------------------------------------------------------------------

//add start -------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.31
									//�R���|�[�l���g�擾�i�����j
									boolean chkMaBudomari = DataCtrl.getInstance().getTrialTblData().searchHaigouGenryoMaBudomari(intKoteiCd, intKoteiSeq);
									if(chkMaBudomari){
										DefaultCellEditor dceBudomari = (DefaultCellEditor)HaigoMeisaiCellEditor6.getTableCellEditor(row);
										((TextboxBase)dceBudomari.getComponent()).setFont(new Font("Default", Font.PLAIN, 12));
										((TextboxBase)dceBudomari.getComponent()).setForeground(Color.black);
										TextFieldCellRenderer tfcrBudomari =  (TextFieldCellRenderer)HaigoMeisaiCellRenderer6.getTableCellRenderer(row);
										tfcrBudomari.setFont(new Font("Default", Font.PLAIN, 12));
										tfcrBudomari.setForeground(Color.black);
									}
									else{
										DefaultCellEditor dceBudomari = (DefaultCellEditor)HaigoMeisaiCellEditor6.getTableCellEditor(row);
										((TextboxBase)dceBudomari.getComponent()).setFont(new Font("Default", Font.BOLD, 12));
										((TextboxBase)dceBudomari.getComponent()).setForeground(Color.red);
										TextFieldCellRenderer tfcrBudomari =  (TextFieldCellRenderer)HaigoMeisaiCellRenderer6.getTableCellRenderer(row);
										tfcrBudomari.setFont(new Font("Default", Font.BOLD, 12));
										tfcrBudomari.setForeground(Color.red);
									}
//add end   -------------------------------------------------------------------------------

								//�H������
								}else if(intGenryoFg == 1){

									//���[�h�ҏW
						    		if(DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
											JwsConstManager.JWS_COMPONENT_0024, DataCtrl.getInstance().getParamData().getStrMode())){

						    			String insert = null;

						    			//�R���|�[�l���g�擾
										MiddleCellEditor mcZokusei = (MiddleCellEditor)HaigoMeisai.getCellEditor(row, column);
										DefaultCellEditor dceZokusei = (DefaultCellEditor)mcZokusei.getTableCellEditor(row);
										ComboBase cb = (ComboBase)dceZokusei.getComponent();
										int selectId = cb.getSelectedIndex();

										//�}���l�擾
										if(selectId > 0){

//2011/05/30�@�yQP@10181_No.42_49_72�z�H���p�^�[������@TT.NISHIGAWA�@start

											//insert = DataCtrl.getInstance().getLiteralDataZokusei().selectLiteralCd(selectId-1);

											//�H���p�^�[���擾
											String ptKotei = PrototypeData.getStrPt_kotei();

											//�H���p�^�[�����u�󔒁v�̏ꍇ
											if(ptKotei == null || ptKotei.length() == 0){
												insert = "";
											}
											//�H���p�^�[�����u�󔒁v�łȂ��ꍇ
											else{
												//�H���p�^�[����Value1�擾
												String ptValue = DataCtrl.getInstance().getLiteralDataKoteiPtn().selectLiteralVal1(ptKotei);

												//�H���p�^�[�����u�������P�t�^�C�v�v�̏ꍇ
												if(ptValue.equals(JwsConstManager.JWS_KOTEITYPE_1)){
													insert = DataCtrl.getInstance().getLiteralDataKotei_tyomi1().selectLiteralCd(selectId-1);
												}
												//�H���p�^�[�����u�������Q�t�^�C�v�v�̏ꍇ
												else if(ptValue.equals(JwsConstManager.JWS_KOTEITYPE_2)){
													insert = DataCtrl.getInstance().getLiteralDataKotei_tyomi2().selectLiteralCd(selectId-1);
												}
												//�H���p�^�[�����u���̑��E���H�^�C�v�v�̏ꍇ
												else if(ptValue.equals(JwsConstManager.JWS_KOTEITYPE_3)){
													insert = DataCtrl.getInstance().getLiteralDataKotei_sonota().selectLiteralCd(selectId-1);
												}
											}

//2011/05/30�@�yQP@10181_No.42_49_72�z�H���p�^�[������@TT.NISHIGAWA�@end

										}

										//�f�[�^�}��
										DataCtrl.getInstance().getTrialTblData().UpdHaigoZokusei(
												intKoteiCd,
												DataCtrl.getInstance().getTrialTblData().checkNullString(insert),
												DataCtrl.getInstance().getUserMstData().getDciUserid()
											);

										//�H���`�F�b�N
										int intKoteiChk = DataCtrl.getInstance().getTrialTblData().CheckKotei();

										//�H�������݂��Ă���ꍇ�A�x�����b�Z�[�W��\��
										if ( intKoteiChk == -1 ) {
											//�s����

											//�x�����b�Z�[�W�\��
											String strMessage = JwsConstManager.JWS_ERROR_0021;
											DataCtrl.getInstance().getMessageCtrl().PrintMessageString(strMessage);

										}

										//������v�Z
						    			DispGenryohi();

						    		}
								}

//2011/05/11 QP@10181_No.42_49_72 TT T.Nishigawa Add Start -------------------------

								//���i��d�A�[�U�ʁA�����[�U�ʁA�����[�U�ʁ@�v�Z����
								ZidouKeisan2();

//2011/05/11 QP@10181_No.42_49_72 TT T.Nishigawa Add End -------------------------

							}

					    	//------------- ������  or �H����  -----------------------------
							if(column == 4){

								//������
								if(intGenryoFg == 0){

									//���[�h�ҏW
						    		if(DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
											JwsConstManager.JWS_COMPONENT_0027, DataCtrl.getInstance().getParamData().getStrMode())){

						    			//�\���l�擾
						    			String insert = getValueAt( row, column ).toString();

										//�f�[�^�}��
										DataCtrl.getInstance().getTrialTblData().UpdHaigoGenryoMei(
												intKoteiCd,
												intKoteiSeq,
												DataCtrl.getInstance().getTrialTblData().checkNullString(insert),
												DataCtrl.getInstance().getUserMstData().getDciUserid()
											);
						    		}

								//�H����
								}else if(intGenryoFg == 1){
									//���[�h�ҏW
						    		if(DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
											JwsConstManager.JWS_COMPONENT_0026, DataCtrl.getInstance().getParamData().getStrMode())){

						    			String insert = getValueAt( row, column ).toString();
										DataCtrl.getInstance().getTrialTblData().UpdHaigoKouteimei(
												intKoteiCd,
												DataCtrl.getInstance().getTrialTblData().checkNullString(insert),
												DataCtrl.getInstance().getUserMstData().getDciUserid()
											);
						    		}
								}
							}

					    	//------------------ �P��  -----------------------------------
							if(column == 5){
								if(intGenryoFg == 0){
									//���[�h�ҏW
						    		if(DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
											JwsConstManager.JWS_COMPONENT_0028, DataCtrl.getInstance().getParamData().getStrMode())){

						    			//�e�[�u���ݒ�l�擾
						    			String insert = getValueAt( row, column ).toString();

						    			//���������􂢑ւ�
						    			insert = ShosuArai_haigo(insert);

						    			//�\���l�ݒ�
						    			setValueAt( insert, row, column );

										//�f�[�^�ݒ�
						    			DataCtrl.getInstance().getTrialTblData().UpdHaigoGenryoTanka(
												intKoteiCd,
												intKoteiSeq,
												DataCtrl.getInstance().getTrialTblData().checkNullDecimal(insert),
												DataCtrl.getInstance().getUserMstData().getDciUserid()
											);

						    			//������v�Z
						    			DispGenryohi();

						    		}
								}
							}

							//------------------ ����  -----------------------------------
							if(column == 6){
								if(intGenryoFg == 0){
									//���[�h�ҏW
						    		if(DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
											JwsConstManager.JWS_COMPONENT_0029, DataCtrl.getInstance().getParamData().getStrMode())){

						    			//�e�[�u���ݒ�l�擾
										String insert = getValueAt( row, column ).toString();

										//���������􂢑ւ�
						    			insert = ShosuArai_haigo(insert);

						    			//�\���l�ݒ�
						    			setValueAt( insert, row, column );

										//�f�[�^�ݒ�
										DataCtrl.getInstance().getTrialTblData().UpdHaigoGenryoBudomari(
												intKoteiCd,
												intKoteiSeq,
												DataCtrl.getInstance().getTrialTblData().checkNullDecimal(insert),
												DataCtrl.getInstance().getUserMstData().getDciUserid()
											);

										//������v�Z
						    			DispGenryohi();

//add start -------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.31
						    			//�������r
						    			boolean chk_3 = DataCtrl.getInstance().getTrialTblData().searchHaigouGenryoMaBudomari(intKoteiCd, intKoteiSeq);

										//�������}�X�^�Ɠ���̏ꍇ�F��������
										if(chk_3){
											DefaultCellEditor dceBudomari = (DefaultCellEditor)HaigoMeisaiCellEditor6.getTableCellEditor(row);
											((TextboxBase)dceBudomari.getComponent()).setFont(new Font("Default", Font.PLAIN, 12));
											((TextboxBase)dceBudomari.getComponent()).setForeground(Color.black);
											TextFieldCellRenderer tfcrBudomari =  (TextFieldCellRenderer)HaigoMeisaiCellRenderer6.getTableCellRenderer(row);
											tfcrBudomari.setFont(new Font("Default", Font.PLAIN, 12));
											tfcrBudomari.setForeground(Color.black);
										}
										//�������}�X�^�Ƒ���̏ꍇ�F�R���|�[�l���g�̑���s��
										else{
											DefaultCellEditor dceBudomari = (DefaultCellEditor)HaigoMeisaiCellEditor6.getTableCellEditor(row);
											((TextboxBase)dceBudomari.getComponent()).setFont(new Font("Default", Font.BOLD, 12));
											((TextboxBase)dceBudomari.getComponent()).setForeground(Color.red);
											TextFieldCellRenderer tfcrBudomari =  (TextFieldCellRenderer)HaigoMeisaiCellRenderer6.getTableCellRenderer(row);
											tfcrBudomari.setFont(new Font("Default", Font.BOLD, 12));
											tfcrBudomari.setForeground(Color.red);
										}
//add end   -------------------------------------------------------------------------------

						    		}
								}
							}

							//---------------- ���ܗL��  ----------------------------------
							if(column == 7){
								if(intGenryoFg == 0){
									//���[�h�ҏW
						    		if(DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
											JwsConstManager.JWS_COMPONENT_0030, DataCtrl.getInstance().getParamData().getStrMode())){

						    			//�e�[�u���ݒ�l�擾
						    			String insert = getValueAt( row, column ).toString();

						    			//���������􂢑ւ�
						    			insert = ShosuArai_haigo(insert);

						    			//�\���l�ݒ�
						    			setValueAt( insert, row, column );

										//�f�[�^�ݒ�
										DataCtrl.getInstance().getTrialTblData().UpdHaigoGenryoYuganyuryo(
												intKoteiCd,
												intKoteiSeq,
												DataCtrl.getInstance().getTrialTblData().checkNullDecimal(insert),
												DataCtrl.getInstance().getUserMstData().getDciUserid()
											);
						    		}
								}
							}

							//�����v�Z
							AutoKeisan();

						}
					}catch(ExceptionBase be){

					}catch(Exception ex){
						ex.printStackTrace();

					}finally{
						//�e�X�g�\��
						//DataCtrl.getInstance().getTrialTblData().dispHaigo();
					}
				}
			};

//add start -------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.7
			HaigoMeisai.addMouseListener(new java.awt.event.MouseAdapter(){
				public void mouseClicked(MouseEvent e) {
					//�Čv�Z����
					try{
						AutoCopyKeisan();
					}catch(Exception ex){
						ex.printStackTrace();
					}
				}
			});
//add end ---------------------------------------------------------------------------------

			HaigoMeisai.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
			HaigoMeisai.setRowHeight(17);
			HaigoMeisai.setAutoResizeMode(HaigoMeisai.AUTO_RESIZE_OFF);
			//HaigoMeisai.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			HaigoMeisai.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
			//HaigoMeisai.setCellSelectionEnabled(true);
			HaigoMeisai.addKeyListener(new CopyGenryo());


			//�e�[�u���I��
			HaigoMeisai.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
				public void valueChanged(ListSelectionEvent e){
					if(e.getValueIsAdjusting()){

						//����w�b�_�[�̑I�������N���A
						TableCellEditor hmEditor = ListHeader.getCellEditor();
						if(hmEditor != null){
							ListHeader.getCellEditor().stopCellEditing();
						}
						ListHeader.clearSelection();

						//���X�g���ׂ̑I�������N���A
						TableCellEditor lmEditor = ListMeisai.getCellEditor();
						if(lmEditor != null){
							ListMeisai.getCellEditor().stopCellEditing();
						}
						ListMeisai.clearSelection();
					}
				}
			});

			//---------------------------------- ��ݒ� ---------------------------------------
			DefaultTableColumnModel columnModel = (DefaultTableColumnModel)HaigoMeisai.getColumnModel();
			TableColumn column = null;
			for (int i = 0 ; i < columnModel.getColumnCount() ; i++){
				column = columnModel.getColumn(i);

				//�H���I��g�T�C�Y�w��
				if(i == 0){
					column.setMinWidth(15);
					column.setMaxWidth(15);
				}
				//�H�����g�T�C�Y�w��
				else if(i == 1){
					column.setMinWidth(15);
					column.setMaxWidth(15);
				}
				//�����I��g�T�C�Y�w��
				else if(i == 2){
					column.setMinWidth(16);
					column.setMaxWidth(16);
				}
				//����CD�i�H�������j�g�T�C�Y�w��
				else if(i == 3){
					column.setMinWidth(84);
					column.setMaxWidth(84);
				}
				//�������g�T�C�Y�w��
				else if(i == 4){
					column.setMinWidth(285);
					column.setMaxWidth(285);
				}
				//�P���g�T�C�Y�w��
				else if(i == 5){
					column.setMinWidth(80);
					column.setMaxWidth(80);
				}
				//�����g�T�C�Y�w��
				else if(i == 6){
					column.setMinWidth(50);
					column.setMaxWidth(50);
				}
				//���ܗL���g�T�C�Y�w��
				else if(i == 7){
					column.setMinWidth(51);
					column.setMaxWidth(51);
				}
	        }

			//------------------------------ �z���f�[�^�\�� ---------------------------------------
			//�z�����׃e�[�u���@���ԃG�f�B�^�[�����i�񐔕��j
			HaigoMeisaiCellEditor0 = new MiddleCellEditor(HaigoMeisai);
			HaigoMeisaiCellEditor1 = new MiddleCellEditor(HaigoMeisai);
			HaigoMeisaiCellEditor2 = new MiddleCellEditor(HaigoMeisai);
			HaigoMeisaiCellEditor3 = new MiddleCellEditor(HaigoMeisai);
			HaigoMeisaiCellEditor4 = new MiddleCellEditor(HaigoMeisai);
			HaigoMeisaiCellEditor5 = new MiddleCellEditor(HaigoMeisai);
			HaigoMeisaiCellEditor6 = new MiddleCellEditor(HaigoMeisai);
			HaigoMeisaiCellEditor7 = new MiddleCellEditor(HaigoMeisai);

			//�z�����׃e�[�u���@���ԃ����_���[�����i�񐔕��j
			HaigoMeisaiCellRenderer0 = new MiddleCellRenderer();
			HaigoMeisaiCellRenderer1 = new MiddleCellRenderer();
			HaigoMeisaiCellRenderer2 = new MiddleCellRenderer();
			HaigoMeisaiCellRenderer3 = new MiddleCellRenderer();
			HaigoMeisaiCellRenderer4 = new MiddleCellRenderer();
			HaigoMeisaiCellRenderer5 = new MiddleCellRenderer();
			HaigoMeisaiCellRenderer6 = new MiddleCellRenderer();
			HaigoMeisaiCellRenderer7 = new MiddleCellRenderer();

			//�e�[�u������
			int Output = 0;
			int bkKoteiCd = 0;

			//�z���f�[�^�ݒ�
			for(int i=0;i<aryHaigou.size();i++){
				MixedData mixedData = (MixedData)aryHaigou.get(i);

				//��L�[�̎擾
				String pk_KoteiCd  = Integer.toString(mixedData.getIntKoteiCd());
				String pk_KoteiSeq = Integer.toString(mixedData.getIntKoteiSeq());

				//�H�����\��
				if(mixedData.getIntKoteiNo() != bkKoteiCd){
					HaigoMeisai.setRowHeight(Output, 17);

					//---------------------- �H���I��  ---------------------------


					//----------------------- �H����  ----------------------------
					//�f�[�^�擾
					String KoteiNo = Integer.toString(mixedData.getIntKoteiNo());
					HaigoMeisai.setValueAt(KoteiNo, Output, 1);
					bkKoteiCd = mixedData.getIntKoteiNo();

					//---------------------- �H������ ----------------------------
					String KoteiZoku = checkNull(mixedData.getStrKouteiZokusei());

//2011/05/30�@�yQP@10181_No.42_49_72�z�H���p�^�[������@TT.NISHIGAWA�@start
					//HaigoMeisai.setValueAt(DataCtrl.getInstance().getLiteralDataZokusei().selectLiteralNm(KoteiZoku), Output, 3);

					//�H���p�^�[���擾
					String ptKotei = PrototypeData.getStrPt_kotei();

					//�H���p�^�[�����u�󔒁v�̏ꍇ
					if(ptKotei == null || ptKotei.length() == 0){

					}
					//�H���p�^�[�����u�󔒁v�łȂ��ꍇ
					else{
						//�H���p�^�[����Value1�擾
						String ptValue = DataCtrl.getInstance().getLiteralDataKoteiPtn().selectLiteralVal1(ptKotei);

						//�H���p�^�[�����u�������P�t�^�C�v�v�̏ꍇ
						if(ptValue.equals(JwsConstManager.JWS_KOTEITYPE_1)){
							HaigoMeisai.setValueAt(DataCtrl.getInstance().getLiteralDataKotei_tyomi1().selectLiteralNm(KoteiZoku), Output, 3);
						}
						//�H���p�^�[�����u�������Q�t�^�C�v�v�̏ꍇ
						else if(ptValue.equals(JwsConstManager.JWS_KOTEITYPE_2)){
							HaigoMeisai.setValueAt(DataCtrl.getInstance().getLiteralDataKotei_tyomi2().selectLiteralNm(KoteiZoku), Output, 3);
						}
						//�H���p�^�[�����u���̑��E���H�^�C�v�v�̏ꍇ
						else if(ptValue.equals(JwsConstManager.JWS_KOTEITYPE_3)){
							HaigoMeisai.setValueAt(DataCtrl.getInstance().getLiteralDataKotei_sonota().selectLiteralNm(KoteiZoku), Output, 3);
						}
					}
//2011/05/30�@�yQP@10181_No.42_49_72�z�H���p�^�[������@TT.NISHIGAWA�@end

					//------------------------ �H����  ----------------------------
					String KoteiNm = mixedData.getStrKouteiNm();
					HaigoMeisai.setValueAt(KoteiNm, Output, 4);

					this.addHaigoMeisaiRowER(Output, 1, pk_KoteiCd, pk_KoteiSeq,-1);
					Output++;
				}
				//---------------------- �H�����i�����j  -----------------------
				String KoteiNo = Integer.toString(mixedData.getIntKoteiNo());
				HaigoMeisai.setValueAt(KoteiNo, Output, 1);

				//----------------------- �����I��  --------------------------


				//------------------------ ����CD  --------------------------
				String GenryoCd = mixedData.getStrGenryoCd();
				HaigoMeisai.setValueAt(GenryoCd, Output, 3);

				//------------------------ ������  ---------------------------
				String GenryoNm = mixedData.getStrGenryoNm();
				HaigoMeisai.setValueAt(GenryoNm, Output, 4);

				//------------------------- �P��  ----------------------------
				String Tanka;
				//�l��NULL�łȂ��ꍇ
				if(mixedData.getDciTanka() != null){
					Tanka = mixedData.getDciTanka().toString();
				//�l��NULL�̏ꍇ
				}else{
					Tanka = null;
				}
				HaigoMeisai.setValueAt(Tanka, Output, 5);

				//------------------------- ����  ----------------------------
				String Budomari;
				//�l��NULL�łȂ��ꍇ
				if(mixedData.getDciBudomari() != null){
					Budomari = mixedData.getDciBudomari().toString();
				//�l��NULL�̏ꍇ
				}else{
					Budomari = null;
				}
				HaigoMeisai.setValueAt(Budomari, Output, 6);

				//------------------------ ���ܗL��  -------------------------
				String Abura;
				//�l��NULL�łȂ��ꍇ
				if(mixedData.getDciGanyuritu() != null){
					Abura = mixedData.getDciGanyuritu().toString();
				//�l��NULL�̏ꍇ
				}else{
					Abura = null;
				}
				HaigoMeisai.setValueAt(Abura, Output, 7);

				this.addHaigoMeisaiRowER(Output, 0, pk_KoteiCd, pk_KoteiSeq,Integer.parseInt(mixedData.getStrIro()));
				Output++;
			}

			//---------------------- �v�Z�f�[�^�\�� ---------------------------
			//���ԃG�f�B�^�[�������_���[����
			MiddleCellEditor SHeaderCellEditor = new MiddleCellEditor(HaigoMeisai);
			MiddleCellRenderer SHeaderCellRenderer = new MiddleCellRenderer();

			//�R���|�[�l���g����
			TextboxBase tbKeisan = new TextboxBase();
			tbKeisan.setEditable(false);
			tbKeisan.setBackground(Color.WHITE);

			//�G�f�B�^�[�������_���[����
			TextFieldCellEditor editKaraRetu = new TextFieldCellEditor(tbKeisan);
			TextFieldCellRenderer rendKaraRetu = new TextFieldCellRenderer(tbKeisan);

			//���ԃG�f�B�^�[�������_���[�֓o�^
			for(int i = Output; i < HaigoMeisai.getRowCount(); i++){
				//1���
				HaigoMeisaiCellEditor0.addEditorAt(i, editKaraRetu);
				HaigoMeisaiCellRenderer0.add(i, rendKaraRetu);
				//2���
				HaigoMeisaiCellEditor1.addEditorAt(i, editKaraRetu);
				HaigoMeisaiCellRenderer1.add(i, rendKaraRetu);
				//3���
				HaigoMeisaiCellEditor2.addEditorAt(i, editKaraRetu);
				HaigoMeisaiCellRenderer2.add(i, rendKaraRetu);
				//4���
				HaigoMeisaiCellEditor3.addEditorAt(i, editKaraRetu);
				HaigoMeisaiCellRenderer3.add(i, rendKaraRetu);
				//5���
				HaigoMeisaiCellEditor4.addEditorAt(i, editKaraRetu);
				HaigoMeisaiCellRenderer4.add(i, rendKaraRetu);
				//6���
				HaigoMeisaiCellEditor5.addEditorAt(i, editKaraRetu);
				HaigoMeisaiCellRenderer5.add(i, rendKaraRetu);
				//7���
				HaigoMeisaiCellEditor6.addEditorAt(i, editKaraRetu);
				HaigoMeisaiCellRenderer6.add(i, rendKaraRetu);
				//8���
				HaigoMeisaiCellEditor7.addEditorAt(i, editKaraRetu);
				HaigoMeisaiCellRenderer7.add(i, rendKaraRetu);
			}

			//�e�H�����v
			for(int i=0; i<kotei_num; i++){
				//x�H���i���j����
				HaigoMeisai.setValueAt((i+1) + "�H���i���j", Output, 4);
				Output++;
			}

			//���v�d�ʁi���j����
			HaigoMeisai.setValueAt("���v�d�ʁi���j", Output, 4);
			Output++;

			// ADD start 20120914 QP@20505 No.1
			//�H���d��d�ʁi���j����
			for(int i=0;i<kotei_num;i++){
				HaigoMeisai.setValueAt((i+1) + "�H���d��d�ʁi���j", Output, 4);
				Output++;
			}
			// ADD end 20120914 QP@20505 No.1

			//���v�d��d�ʁi���j����
			HaigoMeisai.setValueAt("���v�d��d�ʁi���j�i"+JwsConstManager.JWS_MARK_0005 + "�j", Output, 4);
			Output++;

			//�����i�����j����
			//2011/06/13 QP@10181_No.29 TT T.Satoh Change Start -------------------------
			//HaigoMeisai.setValueAt("�����i�����j", Output, 4);
			HaigoMeisai.setValueAt("������i�����j", Output, 4);
			//2011/06/13 QP@10181_No.29 TT T.Satoh Change End ---------------------------
			Output++;

			//2011/06/13 QP@10181_No.29 TT T.Satoh Add Start -------------------------
			HaigoMeisai.setValueAt("������i�P�j", Output, 4);
			Output++;
			//2011/06/13 QP@10181_No.29 TT T.Satoh Add End ---------------------------

			//���_�i���j����
			HaigoMeisai.setValueAt("���_�i���j", Output, 4);
			Output++;

			//�H���i���j����
			HaigoMeisai.setValueAt("�H���i���j", Output, 4);
			Output++;

//			// ADD start 20121002 QP@20505 No.24
//			//�l�r�f�i���j����
//			HaigoMeisai.setValueAt("�l�r�f�i���j", Output, 4);
//			Output++;
//			// ADD end 20121002 QP@20505 No.24

			//���������_����
			HaigoMeisai.setValueAt("�������_�x", Output, 4);
			Output++;

			//�������H������
			HaigoMeisai.setValueAt("�������H��", Output, 4);
			Output++;

			//�������|�_����
			HaigoMeisai.setValueAt("�������|�_", Output, 4);
			//Output++;


			//------------------ Jtable�֒��ԃG�f�B�^�[�������_���[��o�^  ----------------------
			this.setHaigoMeisaiER();
		}catch(Exception e){
			e.printStackTrace();

		}finally{

		}

		return HaigoMeisai;
	}

	/************************************************************************************
	 *
	 *   ����񖾍א���
	 *    : ����񖾍א����̏����������s��
	 *   @author TT nishigawa
	 *   @return JComponent :����񖾍׃R���|�[�l���g
	 *
	 ************************************************************************************/
	private JComponent DispListMeisai(){
		try{
			//�����ݒ�
			int kotei_num = DataCtrl.getInstance().getTrialTblData().getIntMaxKotei();
			int row = DataCtrl.getInstance().getTrialTblData().getIntMaxKotei() + aryHaigou.size();

			// ADD start 20120914 QP@20505 No.1
			//2011/06/13 QP@10181_No.29 TT T.Satoh Change Start -------------------------
			//int keisan = kotei_num+8;
			//int keisan = kotei_num+9;
			//2011/06/13 QP@10181_No.29 TT T.Satoh Change End ---------------------------
			int keisan = (kotei_num * 2) + 9;
			// ADD end 20120914 QP@20505 No.1

			int col = aryTrialData.size();

			//�e�[�u�������ݒ�
			ListMeisai = new TableBase((row+keisan),col){


//add start -------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.7
				//�Z���I����
				public void columnSelectionChanged(ListSelectionEvent e){
					super.columnSelectionChanged(e);
					try{
						//�Čv�Z����
						if(!e.getValueIsAdjusting()){

							if(this.getSelectedColumn() > -1){
								AutoCopyKeisan();
								editCol = this.getSelectedColumn();
							}

							//�I���ӏ��̒���
							int row = HaigoMeisai.getSelectedRow();
							if(row > -1){
								HaigoMeisai.clearSelection();
								HaigoMeisai.setRowSelectionInterval(row, row);
							}
						}
					}catch(Exception ex){
						ex.printStackTrace();

					}finally{

					}
				}
//add end   -------------------------------------------------------------------------------


				//�Z���̕ҏW�I����
				public void editingStopped( ChangeEvent e ){
					//------------------------ �z���f�[�^�X�V ------------------------------
					try{
						super.editingStopped( e );
						//�ҏW�s��ԍ��擾
						int row = getSelectedRow();
						int column = getSelectedColumn();
						if( row>=0 && column>=0 ){
							//---------------- �L�[���ڎ擾  -------------------------------
							int intShisakuSeq = -1;
							int intKoteiCd    = -1;
							int intKoteiSeq   = -1;
							int intGenryoFg;

							//����SEQ�@�擾
							MiddleCellEditor mceHeaderKey = (MiddleCellEditor)ListHeader.getCellEditor(0, column);
							DefaultCellEditor dceHeaderKey = (DefaultCellEditor)mceHeaderKey.getTableCellEditor(0);
							CheckboxBase chkHeaderKey = (CheckboxBase)dceHeaderKey.getComponent();
							intShisakuSeq  = Integer.parseInt(chkHeaderKey.getPk1());

							//�H��CD & �H��SEQ�@�擾
							MiddleCellEditor mceHaigoKey = (MiddleCellEditor)HaigoMeisai.getCellEditor(row, 2);
							DefaultCellEditor dceHaigoKey = (DefaultCellEditor)mceHaigoKey.getTableCellEditor(row);
							//�H���s�łȂ��ꍇ
							if(dceHaigoKey.getComponent() instanceof CheckboxBase){
								CheckboxBase chkHaigoKey = (CheckboxBase)dceHaigoKey.getComponent();
								intKoteiCd  = Integer.parseInt(chkHaigoKey.getPk1());
								intKoteiSeq = Integer.parseInt(chkHaigoKey.getPk2());

								//-------------------- ��  -----------------------------------
								//���[�h�ҏW
					    		if(DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
										JwsConstManager.JWS_COMPONENT_0031, DataCtrl.getInstance().getParamData().getStrMode())){

					    			//�\���l�擾
					    			String insert = (String)getValueAt( row, column );

					    			//��������
					    			if(insert != null && insert.length() > 0){

					    				//���֏���
					    				insert = ShosuArai(insert);

						    			//�e�[�u���}��
						    			setValueAt(insert, row, column);
					    			}

//add start -------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.7
					    			//���͑O�̒l���擾
					    			String beforeInsert = DataCtrl.getInstance().getTrialTblData().searchShisakuListRyo(
											intShisakuSeq,
											intKoteiCd,
											intKoteiSeq);

					    			//���͑O�Ɠ��͌�̒l���r
					    			if(beforeInsert.equals(insert)){
					    				//�������ꍇ���ύX����
					    				//�������Ȃ�
					    			}
					    			else{
					    				//�������Ȃ��ꍇ���ύX�L��
					    				//�Z���ҏW�t���O��ON�i�Čv�Z�Ώہj
						    			editFg = true;
					    			}
//add end   -------------------------------------------------------------------------------

					    			//�f�[�^�}��
									DataCtrl.getInstance().getTrialTblData().UpdShisakuListRyo(
											intShisakuSeq,
											intKoteiCd,
											intKoteiSeq,
											DataCtrl.getInstance().getTrialTblData().checkNullDecimal(insert)
										);

									//�H�����v�v�Z
									koteiSum(column);

//2011/05/11 QP@10181_No.42_49_72 TT T.Nishigawa Add Start -------------------------

					    			//���i��d�A�[�U�ʁA�����[�U�ʁA�����[�U�ʁ@�v�Z����
									ZidouKeisan2();

//2011/05/11 QP@10181_No.42_49_72 TT T.Nishigawa Add End -------------------------

									//�����v�Z
									AutoKeisan();

									//������v�Z
					    			DispGenryohi();
					    		}
							}else{
								//------------------ �d��d��  --------------------------------
								// ADD start 20120914 QP@20505 No.1
								int koutei_Cnt = DataCtrl.getInstance().getTrialTblData().getIntMaxKotei();
								// ADD end 20120914 QP@20505 No.1
								//2011/06/13 QP@10181_No.29 TT T.Satoh Change Start -------------------------
					    		//if(row == this.getRowCount()-7){
						    	if(row == this.getRowCount()-8){
						    	//2011/06/13 QP@10181_No.29 TT T.Satoh Change End ---------------------------
					    			//���[�h�ҏW
						    		if(DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
											JwsConstManager.JWS_COMPONENT_0124, DataCtrl.getInstance().getParamData().getStrMode())){

						    			//�\���l�擾
						    			String insert = (String)getValueAt( row, column );

						    			//��������
						    			if(insert != null && insert.length() > 0){

						    				//���֏���
						    				insert = ShosuArai4(insert);

							    			//�e�[�u���}��
							    			setValueAt(insert, row, column);
						    			}

//add start -------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.7
						    			//���͑O�̒l���擾
						    			String beforeInsert =
						    				DataCtrl.getInstance().getTrialTblData().searchShisakuRetuSiagari(intShisakuSeq);

						    			//���͑O�Ɠ��͌�̒l���r
						    			if(beforeInsert.equals(insert)){
						    				//�������ꍇ���ύX����
						    				//�������Ȃ�
						    			}
						    			else{
						    				//�������Ȃ��ꍇ���ύX�L��
						    				//�Z���ҏW�t���O��ON�i�Čv�Z�Ώہj
							    			editFg = true;
						    			}
//add end   -------------------------------------------------------------------------------

						    			//�f�[�^�}��
										DataCtrl.getInstance().getTrialTblData().UpdShiagariRetuDate(
												intShisakuSeq,
												DataCtrl.getInstance().getTrialTblData().checkNullDecimal(insert),
												DataCtrl.getInstance().getUserMstData().getDciUserid()
											);

										//������v�Z
						    			DispGenryohi();

						    		}
					    		}
						    	// ADD start 20120914 QP@20505 No.1
						    	//�H���d��d��
						    	else if(row < this.getRowCount()-8 && row >= this.getRowCount()-8-koutei_Cnt){
						    		//�\���l�擾
					    			String insert = (String)getValueAt( row, column );
					    			int gokeiShiagari = this.getRowCount()-8;
					    			int kouteiNum = row - (gokeiShiagari-koutei_Cnt) + 1;
					    			int intKoteiCode = DataCtrl.getInstance().getTrialTblData().getSerchKoteiCode(intShisakuSeq ,kouteiNum);

				    				//���֏���
				    				insert = ShosuArai4(insert);

					    			//�e�[�u���}��
					    			setValueAt(insert, row, column);

					    			//�f�[�^�}��
									DataCtrl.getInstance().getTrialTblData().UpdKouteiShiagari(
											intShisakuSeq,
											intKoteiCode,
											DataCtrl.getInstance().getTrialTblData().checkNullDecimal(insert),
											DataCtrl.getInstance().getUserMstData().getDciUserid()
										);

						    		//���v�d��d�ʌv�Z
					    			ShiagariZyuryoKeisan(column);

					    			String shiagariGokeiIns = checkNull(getValueAt(gokeiShiagari, column));

					    			//�f�[�^�}��
									DataCtrl.getInstance().getTrialTblData().UpdShiagariRetuDate(
											intShisakuSeq,
											DataCtrl.getInstance().getTrialTblData().checkNullDecimal(shiagariGokeiIns),
											DataCtrl.getInstance().getUserMstData().getDciUserid()
										);
									// ADD start 20121005 QP@20505
									//������v�Z
					    			DispGenryohi();
									// ADD end 20121005 QP@20505
					    		}
						    	// ADD end 20120914 QP@20505 No.1
							}
						}
					}catch(ExceptionBase be){

					}catch(Exception ex){
						ex.printStackTrace();

					}finally{
						//�e�X�g�\��
						//DataCtrl.getInstance().getTrialTblData().dispProtoList();
						//DataCtrl.getInstance().getTrialTblData().dispTrial();
					}
				}
			};

			ListMeisai.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
			ListMeisai.setRowHeight(17);
			ListMeisai.setAutoResizeMode(ListMeisai.AUTO_RESIZE_OFF);
			ListMeisai.setCellSelectionEnabled(true);
			ListMeisai.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

			//�L�[�C�x���g�ݒ�i���[�h�`�F�b�N�j
			//���[�h�ҏW
			if(DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
					JwsConstManager.JWS_COMPONENT_0125, DataCtrl.getInstance().getParamData().getStrMode())){
				ListMeisai.addKeyListener(new CopyCell());
			}

			//�e�[�u���I��
			ListMeisai.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
				//���e�[�u���ւ̃t�H�[�J�X�ړ���
				public void valueChanged(ListSelectionEvent e){

					int intSelectRow = ListMeisai.getSelectedRow();
					int intSelectCol = ListMeisai.getSelectedColumn();

					if(intSelectRow > -1 && intSelectCol > -1){
						//�z���s�I��
						HaigoMeisai.setRowSelectionInterval(intSelectRow, (intSelectRow+ListMeisai.getSelectedRows().length-1));
					}else{
						//HaigoMeisai.clearSelection();
					}

					if(e.getValueIsAdjusting()){


						//����w�b�_�[�̑I�������N���A
						TableCellEditor shEditor = ListHeader.getCellEditor();
						if(shEditor != null){
							ListHeader.getCellEditor().stopCellEditing();
						}
						ListHeader.clearSelection();
						//�z�����ׂ̑I�������N���A
						TableCellEditor hmEditor = HaigoMeisai.getCellEditor();
						if(hmEditor != null){
							HaigoMeisai.getCellEditor().stopCellEditing();
						}
//						HaigoMeisai.clearSelection();
					}
				}
			});

			//�Z���G�f�B�^�[�������_���[����
			for(int i=0;i<col;i++){
				this.addListShisakuColumnER(i);
			}

			//�f�[�^����
			int ListCount = 0;
			for(int i=0;i<col;i++){
				//�e�H�����v�v�Z
				ArrayList koteiGokei = new ArrayList();
				BigDecimal koteiKeisan = null;
				TrialData TrialData = ((TrialData)aryTrialData.get(i)); //�����f�[�^�擾
				int Output = 0;
				int bkKoteiCd = 0;

				// ADD start 20120914 QP@20505 No.1
				ArrayList koteiShiagari = new ArrayList();
				// ADD end 20120914 QP@20505 No.1

				//--------------------- ���샊�X�g�f�[�^�\��  --------------------------------
				for(int j=0;j<aryHaigou.size();j++){

					MixedData mixedData = (MixedData)aryHaigou.get(j);
					PrototypeListData PrototypeListData = (PrototypeListData)aryShisakuList.get(ListCount);
					//��L�[�擾
					String pk_ShisakuSeq = Integer.toString(PrototypeListData.getIntShisakuSeq());
					String pk_KoteiCd = Integer.toString(PrototypeListData.getIntKoteiCd());
					String pk_KoteiSeq = Integer.toString(PrototypeListData.getIntKoteiSeq());


					//-------------------- �H�����\��  ----------------------------------
					if(mixedData.getIntKoteiNo() != bkKoteiCd){
						//�H���v�Z�z��֒ǉ�
						if(koteiKeisan != null){
							koteiGokei.add(koteiKeisan);
						}
						//�v�Z��񏉊���
						koteiKeisan = new BigDecimal("0");
						//�e�[�u���ݒ�
						ListMeisai.setRowHeight(Output, 17);
						this.addListShisakuRowER(i, Output, 1);
						bkKoteiCd = mixedData.getIntKoteiNo();
						Output++;

						// ADD start 20120914 QP@20505 No.1
						//�l��NULL�łȂ��ꍇ
						if(((PrototypeListData)aryShisakuList.get(ListCount)).getDciKouteiShiagari() != null){
							koteiShiagari.add(((PrototypeListData)aryShisakuList.get(ListCount)).getDciKouteiShiagari().toString());
						}else{
							koteiShiagari.add("");
						}
						// ADD end 20120914 QP@20505 No.1

					}
					//--------------------- �ʃf�[�^�\��  ----------------------------------
					String ryo;
					//�l��NULL�łȂ��ꍇ
					if(((PrototypeListData)aryShisakuList.get(ListCount)).getDciRyo() != null){
						ryo = ((PrototypeListData)aryShisakuList.get(ListCount)).getDciRyo().toString();
						//�H�����v���Z
						koteiKeisan = koteiKeisan.add(new BigDecimal(ryo));
					//�l��NULL�̏ꍇ
					}else{
						ryo = null;
					}
					ListMeisai.setValueAt(ryo, Output, i);
					//�G�f�B�^&�����_���ݒ�
					this.addListShisakuRowER(i, Output, 0);
					//�J�E���g
					ListCount++;
					Output++;
				}
				koteiGokei.add(koteiKeisan);

				//------------------------- �v�Z��\��  ----------------------------------
				//�H�����v
				BigDecimal allGokei = new BigDecimal("0");
				for(int k=0; k<kotei_num; k++){
					//�e�H�����v�l�擾
					BigDecimal koteiAns = (BigDecimal)koteiGokei.get(k);
					//koteiAns = koteiAns.multiply(new BigDecimal("1000"));
					//x�H���i���j����
					ListMeisai.setValueAt(koteiAns, Output, i);
					//�G�f�B�^&�����_���ݒ�
					this.addListShisakuRowER(i, Output, 1);
					Output++;
					//�H�����v���Z
					allGokei = allGokei.add(koteiAns);
				}

				//���v�d�ʁi���j����
				ListMeisai.setValueAt(allGokei, Output, i);
				this.addListShisakuRowER(i, Output, 1);
				Output++;

				//�H���d��d��
				for(int l=0; l<kotei_num; l++){
					//x�H���d��d�ʁi���j����
					ListMeisai.setValueAt(koteiShiagari.get(l), Output, i);
					//�G�f�B�^&�����_���ݒ�
					this.addListShisakuRowER(i, Output, 0);
					Output++;
				}


				//���v�d��d�ʁi���j����
				ListMeisai.setValueAt(TrialData.getDciShiagari(), Output, i);
				this.addListShisakuRowER(i, Output, 0);
				Output++;

				//�����i�����j����
				ListMeisai.setValueAt(null, Output, i);
				this.addListShisakuRowER(i, Output, 1);
				Output++;

				//2011/06/13 QP@10181_No.29 TT T.Satoh Add Start -------------------------
				//������i�P�j
				ListMeisai.setValueAt(null, Output, i);
				this.addListShisakuRowER(i, Output, 1);
				Output++;
				//2011/06/13 QP@10181_No.29 TT T.Satoh Add End ---------------------------

				//���_�i���j����
				ListMeisai.setValueAt(TrialData.getDciSosan(), Output, i);
				this.addListShisakuRowER(i, Output, 1);
				Output++;

				//�H���i���j����
				ListMeisai.setValueAt(TrialData.getDciShokuen(), Output, i);
				this.addListShisakuRowER(i, Output, 1);
				Output++;

				// ADD start 20121002 QP@20505 No.24
//				//�l�r�f�i���j����
//				ListMeisai.setValueAt(TrialData.getDciMsg(), Output, i);
//				this.addListShisakuRowER(i, Output, 1);
//				Output++;
				// ADD end 20121002 QP@20505 No.24

				//�������_�x����
				ListMeisai.setValueAt(TrialData.getDciSuiSando(), Output, i);
				this.addListShisakuRowER(i, Output, 1);
				Output++;

				//�������H������
				ListMeisai.setValueAt(TrialData.getDciSuiShokuen(), Output, i);
				this.addListShisakuRowER(i, Output, 1);
				Output++;

				//�������|�_����
				ListMeisai.setValueAt(TrialData.getDciSuiSakusan(), Output, i);
				this.addListShisakuRowER(i, Output, 1);
				//Output++;

			}
			//Jtable�֒��ԃG�f�B�^�[�������_���[��o�^
			this.setListShisakuER();

			//------------------------------ �F�ύX  ------------------------------------
			int Count = 0;
			for(int i=0;i<col;i++){
				//�����f�[�^�擾
				TrialData TrialData = ((TrialData)aryTrialData.get(i));
				int Output = 0;
				//�z���f�[�^�����[�v
				for(int j=0;j<aryHaigou.size();j++){
					MixedData mixedData = (MixedData)aryHaigou.get(j);
					PrototypeListData PrototypeListData = (PrototypeListData)aryShisakuList.get(Count);

					//�H���L�[���ڎ擾
					MiddleCellEditor mc = (MiddleCellEditor)HaigoMeisai.getCellEditor(Output, 2);
					DefaultCellEditor tc = (DefaultCellEditor)mc.getTableCellEditor(Output);
					if(tc.getComponent() instanceof CheckboxBase){

					}else{
						Output++;
					}

//					if(mixedData.getIntGenryoNo() == 1){
//						Output++;
//					}



					//�Z���F�ݒ�
					this.addListShisakuRowER_color(Output, i, Integer.parseInt(PrototypeListData.getStrIro()));

					//�J�E���g
					Count++;
					Output++;
				}
			}

		}catch(Exception e){

			e.printStackTrace();

		}finally{

		}

		return ListMeisai;
	}

	/************************************************************************************
	 *
	 *   ���얾�ׁ@�����w�����
	 *    : ���X�g�����ڂ̏��������w�菬�����̂ݕ\��
	 *   @author TT nishigawa
	 *
	 ************************************************************************************/
	public String ShosuArai(String strChk){
		String ret = null;

		try{
				if(strChk != null && strChk.length()>0){
					//�����l���擾
					String cd = DataCtrl.getInstance().getTrialTblData().SearchShisakuhinData().getStrShosu();
	    			int val1 = 0;
	    			if(cd != null && cd.length()>0){
	    				val1 = DataCtrl.getInstance().getLiteralDataShosu().selectLiteralVal1(Integer.parseInt(cd));
	    			}

	    			//�t�H�[�}�b�g�ݒ�
	    			String format = "0";
	    			for(int k=0; k<val1; k++){
	    				if(k == 0){
	    					format = format + ".";
	    				}
	    				format = format + "0";
	    			}
	    			DecimalFormat decimalFormat = new DecimalFormat( format );
	    			BigDecimal bigDecimal = new BigDecimal(strChk);
	    			ret = decimalFormat.format( bigDecimal.setScale( val1, BigDecimal.ROUND_DOWN ) );
				}


		}catch(Exception e){
			e.printStackTrace();

			ret = null;


		}finally{

		}

		return ret;
	}

	/************************************************************************************
	 *
	 *   ���얾�ׁ@�����w�����
	 *    : ���X�g�����ڂ̏��������w�菬�����̂ݕ\��
	 *   @author TT nishigawa
	 *
	 ************************************************************************************/
	public String ShosuArai4(String strChk){
		String ret = null;

		try{
				if(strChk != null && strChk.length()>0){
					//�����l���擾
					String cd = DataCtrl.getInstance().getTrialTblData().SearchShisakuhinData().getStrShosu();
	    			int val1 = 4;


	    			//�t�H�[�}�b�g�ݒ�
	    			String format = "0";
	    			for(int k=0; k<val1; k++){
	    				if(k == 0){
	    					format = format + ".";
	    				}
	    				format = format + "0";
	    			}
	    			DecimalFormat decimalFormat = new DecimalFormat( format );
	    			BigDecimal bigDecimal = new BigDecimal(strChk);
	    			ret = decimalFormat.format( bigDecimal.setScale( val1, BigDecimal.ROUND_DOWN ) );
				}


		}catch(Exception e){
			e.printStackTrace();

			ret = null;


		}finally{

		}

		return ret;
	}

//add start --------------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.19
	/************************************************************************************
	 *
	 *   ���얾�ׁ@�����w�����
	 *    : ���X�g�����ڂ̏��������w�菬�����l�̌ܓ����ĕ\��
	 *   @author TT k-katayama
	 *
	 ************************************************************************************/
	public String ShosuAraiHulfUp(String strChk){
		String ret = null;

		try{
				if(strChk != null && strChk.length()>0){
					//�����l���擾
					String cd = DataCtrl.getInstance().getTrialTblData().SearchShisakuhinData().getStrShosu();
	    			int val1 = 0;
	    			if(cd != null && cd.length()>0){
	    				val1 = DataCtrl.getInstance().getLiteralDataShosu().selectLiteralVal1(Integer.parseInt(cd));
	    			}

	    			//�t�H�[�}�b�g�ݒ�
	    			String format = "0";
	    			for(int k=0; k<val1; k++){
	    				if(k == 0){
	    					format = format + ".";
	    				}
	    				format = format + "0";
	    			}
	    			DecimalFormat decimalFormat = new DecimalFormat( format );
	    			BigDecimal bigDecimal = new BigDecimal(strChk);
	    			ret = decimalFormat.format( bigDecimal.setScale( val1, BigDecimal.ROUND_HALF_UP ) );
				}


		}catch(Exception e){
			e.printStackTrace();

			ret = null;


		}finally{

		}

		return ret;
	}
//add end --------------------------------------------------------------------------------------

//add start -------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.7
	/************************************************************************************
	 *
	 *   ���얾�ׁ@�����w�����_�����w��
	 *    : ���X�g�����ڂ̏��������w�菬�����l�̌ܓ����ĕ\��
	 *   @author TT k-katayama
	 *
	 ************************************************************************************/
	public String ShosuAraiHulfUp_keta(String strChk,String keta){
		String ret = null;

		try{
				if(strChk != null && strChk.length()>0){
					//�����l���擾
	    			int val1 = 0;
	    			if(keta != null && keta.length()>0){
	    				val1 = Integer.parseInt(keta);
	    			}

	    			//�t�H�[�}�b�g�ݒ�
	    			String format = "0";
	    			for(int k=0; k<val1; k++){
	    				if(k == 0){
	    					format = format + ".";
	    				}
	    				format = format + "0";
	    			}
	    			DecimalFormat decimalFormat = new DecimalFormat( format );
	    			BigDecimal bigDecimal = new BigDecimal(strChk);
	    			ret = decimalFormat.format( bigDecimal.setScale( val1, BigDecimal.ROUND_HALF_UP ) );
				}


		}catch(Exception e){
			e.printStackTrace();

			ret = null;


		}finally{

		}

		return ret;
	}
//add end   -------------------------------------------------------------------------------

	/************************************************************************************
	 *
	 *   �z�����ׁ@�����w�����
	 *    : ���X�g�����ڂ̏��������w�菬�����̂ݕ\��
	 *   @author TT nishigawa
	 *
	 ************************************************************************************/
	public String ShosuArai_haigo(String strChk){
		String ret = null;

		try{
				if(strChk != null && strChk.length()>0){
					//�����l���擾
	    			int val1 = 2;

	    			//�t�H�[�}�b�g�ݒ�
	    			String format = "0";
	    			for(int k=0; k<val1; k++){
	    				if(k == 0){
	    					format = format + ".";
	    				}
	    				format = format + "0";
	    			}
	    			DecimalFormat decimalFormat = new DecimalFormat( format );
	    			BigDecimal bigDecimal = new BigDecimal(strChk);
	    			ret = decimalFormat.format( bigDecimal.setScale( val1, BigDecimal.ROUND_DOWN ) );
				}


		}catch(Exception e){
			e.printStackTrace();

			ret = null;


		}finally{

		}

		return ret;
	}


	/************************************************************************************
	 *
	 *   ������i�����j�v�Z
	 *   @author TT nishigawa
	 *
	 ************************************************************************************/
	public void DispGenryohi() throws ExceptionBase{
		try{

			//------------------------------- ������i�����j�v�Z --------------------------------

			//���������f�[�^�擾�i�S���j
			ArrayList aryGenka = DataCtrl.getInstance().getTrialTblData().SearchGenkaGenryoData(0);

			//�����iKg�j�v�Z
			for(int i=0; i<aryGenka.size(); i++){

				//���������f�[�^�擾�i1���j
				CostMaterialData CostMaterialData = (CostMaterialData)aryGenka.get(i);

				//�����iKg�j�v�Z���s
				DataCtrl.getInstance().getTrialTblData().UpdGenkaGenryoMath(CostMaterialData);

			}


			//------------------------------- ������i�����j�\�� --------------------------------

			//������@�s�ԍ��擾
			//2011/06/13 QP@10181_No.29 TT T.Satoh Change Start -------------------------
			//int row = ListMeisai.getRowCount()-6;
			int row = ListMeisai.getRowCount()-7;
			//2011/06/13 QP@10181_No.29 TT T.Satoh Change End ---------------------------

			//����񐔕����[�v
			for(int j=0; j<ListHeader.getColumnCount(); j++){

				//����SEQ�擾
				MiddleCellEditor mceSeq = (MiddleCellEditor)ListHeader.getCellEditor(0, j);
				DefaultCellEditor dceSeq = (DefaultCellEditor)mceSeq.getTableCellEditor(0);
				CheckboxBase chkSeq = (CheckboxBase)dceSeq.getComponent();
				int shisakuSeq  = Integer.parseInt(chkSeq.getPk1());

				//���������f�[�^�擾�i�v�Z�σf�[�^�j
    			ArrayList aryKeisanGenka = DataCtrl.getInstance().getTrialTblData().SearchGenkaGenryoData(shisakuSeq);

    			//������i�����j�擾
    			CostMaterialData keisanCostMaterialData = (CostMaterialData)aryKeisanGenka.get(0);
    			String genryohi = keisanCostMaterialData.getStrGenryohi();

    			//2011/06/13 QP@10181_No.29 TT T.Satoh Add Start -------------------------
    			//������i�P�j�擾[������(1�{��)�Ɠ����v�Z�Ȃ̂ŗ��p]
    			String genryohitan = keisanCostMaterialData.getStrGenryohiTan();
    			//2011/06/13 QP@10181_No.29 TT T.Satoh Add End ---------------------------

				//�\���l�ݒ�
    			ListMeisai.setValueAt(genryohi, row, j);
    			//2011/06/13 QP@10181_No.29 TT T.Satoh Add Start -------------------------
    			ListMeisai.setValueAt(genryohitan, row+1, j);
    			//2011/06/13 QP@10181_No.29 TT T.Satoh Add End ---------------------------

			}

		}catch(ExceptionBase eb){

			throw eb;

		}catch(Exception e){

			e.printStackTrace();

			//�G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			//2011/04/22 QP@10181_No.26 TT T.Satoh Change Start -------------------------
			//this.ex.setStrErrmsg("����@������i��g�j�\���Ɏ��s���܂���");
			this.ex.setStrErrmsg("�z���\������i��g�j�\���Ɏ��s���܂���");
			//2011/04/22 QP@10181_No.26 TT T.Satoh Change End ---------------------------
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		}finally{

		}
	}

	/************************************************************************************
	 *
	 *   �e��H�����v�v�Z
	 *    : �e��̍H�����v�l���v�Z���A�e�[�u���֐ݒ肷��
	 *   @author TT nishigawa
	 *   @param sol : �v�Z�s
	 *
	 ************************************************************************************/
	public void koteiSum(int col){
		try{
			//-------------------------------- �������� ----------------------------------
			//�e�H�����v�v�Z
			ArrayList koteiGokei = new ArrayList();
			BigDecimal koteiKeisan = null;
			//�z���f�[�^�擾
			ArrayList retuData = DataCtrl.getInstance().getTrialTblData().SearchHaigoData(0);
			//�ő�H�����擾
			int maxKotei = DataCtrl.getInstance().getTrialTblData().getIntMaxKotei();
			//�v�Z�Ώۗ񐔎擾
			// MOD start 20120914 QP@20505 No.1
			//2011/06/13 QP@10181_No.29 TT T.Satoh Change Start -------------------------
			//int keisanRow = ListMeisai.getRowCount()-maxKotei-8;
//			int keisanRow = ListMeisai.getRowCount()-maxKotei-9;
			//2011/06/13 QP@10181_No.29 TT T.Satoh Change End ---------------------------
			int keisanRow = ListMeisai.getRowCount() - ( maxKotei * 2 ) - 9;
			// ADD end 20120914 QP@20505 No.1

			//���v�}���J�n�s
			int Output = 0;

			//-------------------------------- �v�Z���� ----------------------------------
			//���얾�ׂ̍s�����[�v
			for(int i=0; i<keisanRow; i++){
				MiddleCellEditor mceHaigo = (MiddleCellEditor)HaigoMeisai.getCellEditor(i, 2);
				DefaultCellEditor dceHaigo = (DefaultCellEditor)mceHaigo.getTableCellEditor(i);

				//�����s
				if(dceHaigo.getComponent() instanceof CheckboxBase){
					//�ʃf�[�^�擾
					String ryo = (String)ListMeisai.getValueAt(i, col);
					//NULL�łȂ��ꍇ�ɏ���
					if(ryo != null && ryo.length() > 0){
						koteiKeisan = koteiKeisan.add(new BigDecimal(ryo));
					}
				//�H���s
				}else{
					//�H���v�Z�z��֒ǉ�
					if(koteiKeisan != null){
						koteiGokei.add(koteiKeisan);
					}
					//�v�Z��񏉊���
					koteiKeisan = new BigDecimal("0");
				}

				Output++;
			}
			koteiGokei.add(koteiKeisan);

			//----------------------------- �e�H�����v�\�� -------------------------------
			BigDecimal allGokei = new BigDecimal("0");
			for(int k=0; k<maxKotei; k++){
				//�e�H�����v�l�擾
				BigDecimal koteiAns = (BigDecimal)koteiGokei.get(k);
				//koteiAns = koteiAns.multiply(new BigDecimal("1000"));
				//x�H���i���j����
				ListMeisai.setValueAt(koteiAns, Output, col);
				Output++;
				//�H�����v���Z
				allGokei = allGokei.add(koteiAns);
			}
			//���v�d�ʁi���j����
			ListMeisai.setValueAt(allGokei, Output, col);
			Output++;

		}catch(Exception e){
			e.printStackTrace();

		}finally{

		}
	}

	/************************************************************************************
	 *
	 *   ����w�b�_�e�[�u���@�����G�f�B�^�������_����ǉ�
	 *    : ����w�b�_�e�[�u���փZ���G�f�B�^�������_����ǉ�����
	 *   @author TT nishigawa
	 *   @param row : �ǉ��s
	 *   @param koteiCd  : ����SEQ
	 *
	 ************************************************************************************/
	public void addRetuShisakuColER(int col,String sisakuSeq){
		try{
			//�����f�[�^�擾
			ArrayList aryRetu =
				DataCtrl.getInstance().getTrialTblData().SearchShisakuRetuData(Integer.parseInt(sisakuSeq));
			TrialData TrialData = (TrialData)aryRetu.get(0);

			//---------------------- �ǉ��p���ԃG�f�B�^�������_������  --------------------------
			MiddleCellEditor MiddleCellEditor = new MiddleCellEditor(ListHeader);
			MiddleCellRenderer MiddleCellRenderer = new MiddleCellRenderer();
			int seq = TrialData.getIntShisakuSeq(); //SEQ

			//----------------------------------- 1�s��  ----------------------------------
			//���@�}�[�N�ݒ�
			String mark = "";
			int seihoseq = PrototypeData.getIntSeihoShisaku();
			if(seq == seihoseq){
				mark = JwsConstManager.JWS_MARK_0003+" ";
			}

			//�������Z�}�[�N�ݒ�
			int genka = TrialData.getFlg_init();
			if(genka > 0){
				mark = mark + JwsConstManager.JWS_MARK_0004;
			}

			//�R���|�[�l���g����
			CheckboxBase listSelect = new CheckboxBase();
			listSelect.setHorizontalAlignment(listSelect.LEFT);
			listSelect.setText(mark);
			listSelect.setBackground(clBlue);

			//���[�h�ҏW
			if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
					JwsConstManager.JWS_COMPONENT_0016, DataCtrl.getInstance().getParamData().getStrMode())){
				listSelect.setEnabled(false);
			}

			//�`�F�b�N���̃C�x���g�ǉ�
			listSelect.addActionListener(new CheckShisaku());
			listSelect.setActionCommand("shisakuCheck");

			//�R���|�[�l���g�փL�[���ڒǉ�
			listSelect.setPk1(sisakuSeq);

			//�{�^���O���[�v�֒ǉ�
			//ShisakuCheck.add(listSelect);

			//�G�f�B�^�[�������_���[����
			DefaultCellEditor editor0 = new DefaultCellEditor(listSelect);
			CheckBoxCellRenderer renderer0 = new CheckBoxCellRenderer(listSelect);

			//���ԃG�f�B�^�������_���֓o�^
			MiddleCellEditor.addEditorAt(0, editor0);
			MiddleCellRenderer.add(0, renderer0);

			//----------------------------------- 2�s��  ----------------------------------
			ArrayList SeizoKotei =
				DataCtrl.getInstance().getTrialTblData().SearchSeizoKouteiData(0); //�����H���f�[�^
			int tyuiNum = 0;
			if(TrialData.getStrTyuiNo() != null){
				tyuiNum = Integer.parseInt(TrialData.getStrTyuiNo());
			}
			String setTyuiNum = "";

			//�R���|�[�l���g����
			ComboBase comboSelect = new ComboBase();
			comboSelect.setPk1(sisakuSeq);
			comboSelect.addItem("");

			//���ӎ���No�ǉ�
			for(int j=0;j<SeizoKotei.size();j++){
				ManufacturingData ManufacturingData = (ManufacturingData)SeizoKotei.get(j);
				String seizoNum = Integer.toString(ManufacturingData.getIntTyuiNo());
				comboSelect.addItem(seizoNum);
			}

			//���ӎ���No�I��
			if(tyuiNum > 0){
				setTyuiNum = Integer.toString(tyuiNum);
				comboSelect.setSelectedItem(setTyuiNum);
			}

			//���[�h�ҏW
			if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
					JwsConstManager.JWS_COMPONENT_0017, DataCtrl.getInstance().getParamData().getStrMode())){
				comboSelect.setEnabled(false);

			}else{
				comboSelect.addActionListener(new selectTyuui());

			}

			//�G�f�B�^�[�������_���[����
			ComboBoxCellEditor editor1 = new ComboBoxCellEditor(comboSelect);
			ComboBoxCellRenderer renderer1 = new ComboBoxCellRenderer(comboSelect);

			//���ԃG�f�B�^�������_���֓o�^
			MiddleCellEditor.addEditorAt(1, editor1);
			MiddleCellRenderer.add(1, renderer1);

			//----------------------------------- 3�s��  ----------------------------------
			//�R���|�[�l���g����
			HankakuTextbox tbHiduke = new HankakuTextbox();
			tbHiduke.setBackground(Yellow);

			//���[�h�ҏW
			if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
					JwsConstManager.JWS_COMPONENT_0018, DataCtrl.getInstance().getParamData().getStrMode())){
				tbHiduke.setEditable(false);
			}

			//�G�f�B�^�[�������_���[����
			TextFieldCellEditor editor2 = new TextFieldCellEditor(tbHiduke);
			TextFieldCellRenderer renderer2 = new TextFieldCellRenderer(tbHiduke);

			//���ԃG�f�B�^�[�������_���[�֓o�^
			MiddleCellEditor.addEditorAt(2, editor2);
			MiddleCellRenderer.add(2, renderer2);

			//----------------------------------- 4�s��  ----------------------------------
			//�R���|�[�l���g����
			TextboxBase tbSample = new TextboxBase();
			tbSample.setBackground(Yellow);

			//���[�h�ҏW
			if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
					JwsConstManager.JWS_COMPONENT_0019, DataCtrl.getInstance().getParamData().getStrMode())){
				tbSample.setEditable(false);
			}

			//�G�f�B�^�[�������_���[����
			TextFieldCellEditor editor3 = new TextFieldCellEditor(tbSample);
			TextFieldCellRenderer renderer3 = new TextFieldCellRenderer(tbSample);

			//���ԃG�f�B�^�[�������_���[�֓o�^
			MiddleCellEditor.addEditorAt(3, editor3);
			MiddleCellRenderer.add(3, renderer3);

			//----------------------------------- 5�s��  ----------------------------------
			//�G�f�B�^�[�������_���[����
			TextAreaCellEditor editor4 = new TextAreaCellEditor(ListHeader);
			editor4.getTextArea().setBackground(Yellow);

			//�yQP@20505�zNo5 2012/10/12 TT H.SHIMA ADD Start
			// ���X�i�[�̐ݒ�
			editor4.getTextArea().addMouseListener(new memoPanelDisp(ListHeader));
			//�yQP@20505�zNo5 2012/10/12 TT H.SHIMA ADD Start
			
			//���[�h�ҏW
			if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
					JwsConstManager.JWS_COMPONENT_0020, DataCtrl.getInstance().getParamData().getStrMode())){
				editor4.getTextArea().setEditable(false);
			}
			TextAreaCellRenderer renderer4 = new TextAreaCellRenderer();
			renderer4.setColor(JwsConstManager.SHISAKU_F2_COLOR);
			//DefaultCellRenderer renderer4 = new DefaultCellRenderer(editor4.getTextArea());

			//���ԃG�f�B�^�[�������_���[�֓o�^
			MiddleCellEditor.addEditorAt(4, editor4);
			MiddleCellRenderer.add(4, renderer4);

			//----------------------------------- 6�s��  ----------------------------------
			int Insatu = TrialData.getIntInsatuFlg();

			//�R���|�[�l���g����
			CheckboxBase InsatuFg = new CheckboxBase();
			InsatuFg.setBackground(Yellow);
			InsatuFg.setHorizontalAlignment(listSelect.CENTER);
			if(Insatu == 1){
				InsatuFg.setSelected(true);
			}

			//���[�h�ҏW
			if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
					JwsConstManager.JWS_COMPONENT_0021, DataCtrl.getInstance().getParamData().getStrMode())){
				InsatuFg.setEnabled(false);
			}

			//�G�f�B�^�[�������_���[����
			DefaultCellEditor editor5 = new DefaultCellEditor(InsatuFg);
			CheckBoxCellRenderer renderer5 = new CheckBoxCellRenderer(InsatuFg);

			//���ԃG�f�B�^�[�������_���[�֓o�^
			MiddleCellEditor.addEditorAt(5, editor5);
			MiddleCellRenderer.add(5, renderer5);

			//����w�b�_�[�G�f�B�^�������_���z��֒ǉ�
			aryHeaderShisakuCellEditor.add(col,MiddleCellEditor);
			aryHeaderShisakuCellRenderer.add(col, MiddleCellRenderer);

		}catch(Exception e){
			e.printStackTrace();
		}finally{

		}
	}

	/************************************************************************************
	 *
	 *   ����w�b�_�[�e�[�u���@�G�f�B�^�������_����폜
	 *    : ����w�b�_�[�e�[�u�����Z���G�f�B�^�������_������폜����
	 *   @author TT nishigawa
	 *   @param col : �폜��
	 *
	 ************************************************************************************/
	public void removeHeaderShisakuColER(int col){
		try{
			//�G�f�B�^�������_���폜
			aryHeaderShisakuCellEditor.remove(col);
			aryHeaderShisakuCellRenderer.remove(col);
		}catch(Exception e){
			e.printStackTrace();
		}finally{

		}
	}

	/************************************************************************************
	 *
	 *   ����w�b�_�[�e�[�u���@�G�f�B�^�������_�������
	 *    : ����w�b�_�[�e�[�u�����Z���G�f�B�^�������_�������ւ���
	 *   @author TT nishigawa
	 *   @param  col_moto : ���֌���
	 *   @param  col_saki : ���֐��
	 *
	 ************************************************************************************/
	public void changeHeaderShisakuColER(int col_moto, int col_saki){
		try{
			//���֌��G�f�B�^�������_����擾
			MiddleCellEditor mc_moto = (MiddleCellEditor)aryHeaderShisakuCellEditor.get(col_moto);
			MiddleCellRenderer mr_moto = (MiddleCellRenderer)aryHeaderShisakuCellRenderer.get(col_moto);

			//���֐�G�f�B�^�������_����擾
			MiddleCellEditor mc_saki = (MiddleCellEditor)aryHeaderShisakuCellEditor.get(col_saki);
			MiddleCellRenderer mr_saki = (MiddleCellRenderer)aryHeaderShisakuCellRenderer.get(col_saki);

			//�G�f�B�^�����
			aryHeaderShisakuCellEditor.set(col_moto, mc_saki);
			aryHeaderShisakuCellEditor.set(col_saki, mc_moto);

			//�����_�������
			aryHeaderShisakuCellRenderer.set(col_moto, mr_saki);
			aryHeaderShisakuCellRenderer.set(col_saki, mr_moto);

		}catch(Exception e){
			e.printStackTrace();
		}finally{

		}
	}

	/************************************************************************************
	 *
	 *   ����w�b�_�[�e�[�u���@�G�f�B�^�������_���ݒ�
	 *    : ����w�b�_�[�e�[�u���փZ���G�f�B�^�������_����ݒ肷��
	 *   @author TT nishigawa
	 *
	 ************************************************************************************/
	public void setHeaderShisakuER(){
		try{
			//�e�[�u���J�����擾
			DefaultTableColumnModel columnModel = (DefaultTableColumnModel)ListHeader.getColumnModel();
			TableColumn column = null;

			for(int i = 0; i<ListHeader.getColumnCount(); i++){

				//�G�f�B�^�������_���z����擾
				MiddleCellEditor MiddleCellEditor = (MiddleCellEditor)aryHeaderShisakuCellEditor.get(i);
				MiddleCellRenderer MiddleCellRenderer = (MiddleCellRenderer)aryHeaderShisakuCellRenderer.get(i);

				//�e�[�u���J�����֐ݒ�
				column = ListHeader.getColumnModel().getColumn(i);
				column.setCellEditor(MiddleCellEditor);
				column.setCellRenderer(MiddleCellRenderer);
			}

			//���ӎ���No�s�̍����ݒ�
			ListHeader.setRowHeight(1,21);

			//�����No�s�̍����ݒ�
			ListHeader.setRowHeight(3, 17);

			//�����s�̍����ݒ�
			ListHeader.setRowHeight(4, 22);

		}catch(Exception e){
			e.printStackTrace();
		}finally{

		}
	}

	/************************************************************************************
	 *
	 *   �z�����׃e�[�u���@�����G�f�B�^�������_���s�ǉ�
	 *    : �z�����׃e�[�u���֌����s�̃Z���G�f�B�^�������_����ǉ�����
	 *   @author TT nishigawa
	 *   @param row : �ǉ��s
	 *   @param flg : 0=�����s or 1=�H���s�@or 2=�v�Z�s
	 *   @param koteiCd  : �H��CD
	 *   @param koteiSeq : �H��SEQ
	 *
	 ************************************************************************************/
	public void addHaigoMeisaiRowER(int row,int flg,String koteiCd,String koteiSeq,int color){
		try{
			//--------------- �H���I���G�f�B�^�[�������_���[����  -------------------------
			JComponent KoteiSel;
			TableCellEditor editKoteiSel;
			TableCellRenderer rendKoteiSel;

			//�����s
			if(flg == 0){
				KoteiSel = new TextboxBase();
				((TextboxBase)KoteiSel).setEditable(false);
				KoteiSel.setBackground(Color.WHITE);
				editKoteiSel = new TextFieldCellEditor((TextboxBase)KoteiSel);
				rendKoteiSel = new TextFieldCellRenderer((TextboxBase)KoteiSel);

			//�H���s
			}else if(flg == 1){

				//�H�����擾
				ArrayList aryHaigoData =
					DataCtrl.getInstance().getTrialTblData().SearchHaigoData(Integer.parseInt(koteiCd));
				MixedData MixedData = new MixedData();
				String koteiNo = "";
				String koteiNm = "";
				String koteiZoku = "";

				//����e�[�u���f�[�^�ێ��N���X���f�[�^����
				for(int i=0; i<aryHaigoData.size(); i++){
					MixedData = (MixedData)aryHaigoData.get(i);

					//�����Ɠ��H��CD�̍H�����A�H�����A�H���������擾
					if(MixedData.getIntKoteiSeq() == Integer.parseInt(koteiSeq)){
						koteiNo = Integer.toString(MixedData.getIntKoteiNo());
						koteiNm = MixedData.getStrKouteiNm();
						koteiZoku = MixedData.getStrKouteiZokusei();
					}
				}

				//�R���|�[�l���g����
				KoteiSel = new CheckboxBase();
				KoteiSel.setPreferredSize(new Dimension(13, 13));
				KoteiSel.setBackground(Color.WHITE);
				KoteiSel.setBorder(line);

				//�`�F�b�N���̃C�x���g�ǉ�
				((CheckboxBase)KoteiSel).addActionListener(new CheckKotei());
				((CheckboxBase)KoteiSel).setActionCommand("koteiCheck");

				//�R���|�[�l���g�ɃL�[��ݒ�
				((CheckboxBase)KoteiSel).setPk1(koteiCd);
				((CheckboxBase)KoteiSel).setPk2(koteiSeq);
				((CheckboxBase)KoteiSel).setPk3(koteiNo);
				((CheckboxBase)KoteiSel).setPk4(koteiNm);
				((CheckboxBase)KoteiSel).setPk5(koteiZoku);

				//���[�h�ҏW
				if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
						JwsConstManager.JWS_COMPONENT_0022, DataCtrl.getInstance().getParamData().getStrMode())){
					((CheckboxBase)KoteiSel).setEnabled(false);
				}

				//�{�^���O���[�v�֒ǉ�
				//KoteiCheck.add((CheckboxBase)KoteiSel);
				editKoteiSel = new DefaultCellEditor((CheckboxBase)KoteiSel);
				rendKoteiSel = new CheckBoxCellRenderer((CheckboxBase)KoteiSel);

			}else{

				//�R���|�[�l���g����
				KoteiSel = new TextboxBase();
				((TextboxBase)KoteiSel).setEditable(false);
				KoteiSel.setBackground(Color.WHITE);

				//�G�f�B�^�[�������_���[����
				editKoteiSel = new TextFieldCellEditor((TextboxBase)KoteiSel);
				rendKoteiSel = new TextFieldCellRenderer((TextboxBase)KoteiSel);
			}

			//���ԃZ���G�f�B�^�[�������_���[�֓o�^
			HaigoMeisaiCellEditor0.addEditorAt(row, editKoteiSel);
			HaigoMeisaiCellRenderer0.add(row, rendKoteiSel);

			//----------------- �H�����G�f�B�^�[�������_���[����  ---------------------------
			TextboxBase KoteiNo = new TextboxBase();
			KoteiNo.setEditable(false);
			KoteiNo.setBackground(Color.WHITE);
			TextFieldCellEditor editKoteiNo = new TextFieldCellEditor(KoteiNo);
			TextFieldCellRenderer rendKoteiNo = new TextFieldCellRenderer(KoteiNo);

			//���ԃZ���G�f�B�^�[�������_���[�֓o�^
			HaigoMeisaiCellEditor1.addEditorAt(row, editKoteiNo);
			HaigoMeisaiCellRenderer1.add(row, rendKoteiNo);

			//----------------- �����I���G�f�B�^�[�������_���[����  -------------------------
			JComponent GenryoSel;
			TableCellEditor editGenryoSel;
			TableCellRenderer rendGenryoSel;

			//�����s
			if(flg == 0){
				GenryoSel = new CheckboxBase();
				GenryoSel.setBackground(Color.WHITE);

				//�`�F�b�N���̃C�x���g�ǉ�
				((CheckboxBase)GenryoSel).addActionListener(new CheckGenryo());
				((CheckboxBase)GenryoSel).setActionCommand("genryoCheck");

				//�R���|�[�l���g�ɃL�[��ݒ�
				((CheckboxBase)GenryoSel).setPk1(koteiCd);
				((CheckboxBase)GenryoSel).setPk2(koteiSeq);

				//���[�h�ҏW
				if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
						JwsConstManager.JWS_COMPONENT_0023, DataCtrl.getInstance().getParamData().getStrMode())){
					((CheckboxBase)GenryoSel).setEnabled(false);
				}
				editGenryoSel = new DefaultCellEditor((CheckboxBase)GenryoSel);
				rendGenryoSel = new CheckBoxCellRenderer((CheckboxBase)GenryoSel);

			//�H���s
			}else{
				GenryoSel = new TextboxBase();
				GenryoSel.setBackground(Color.WHITE);
				((TextboxBase)GenryoSel).setEditable(false);
				GenryoSel.setBackground(Color.WHITE);
				editGenryoSel = new TextFieldCellEditor((TextboxBase)GenryoSel);
				rendGenryoSel = new TextFieldCellRenderer((TextboxBase)GenryoSel);
			}

			//���ԃZ���G�f�B�^�[�������_���[�֓o�^
			HaigoMeisaiCellEditor2.addEditorAt(row, editGenryoSel);
			HaigoMeisaiCellRenderer2.add(row, rendGenryoSel);

			//------------------- ����CD�G�f�B�^�[�������_���[����  -------------------------
			JComponent GenryoCD;
			TableCellEditor editGenryoCD;
			TableCellRenderer rendGenryoCD;

			//�����s
			if(flg == 0){
				GenryoCD = new HankakuTextbox(){

					/**
					 * �e�L�X�g�{�b�N�X�̃t�H���g���f���̍쐬
					 * @return ���p�����pDocument
					 */
				    protected Document createDefaultModel() {
				    	 //���������t���c���������������w��
					     return new HankakuDocumentGenryo();
				    }
				};
				GenryoCD.setBackground(Color.WHITE);

				//�����擾
				int keta = getKeta();

				//���w��
				((HankakuTextbox)GenryoCD).setIntMaxLength(keta);

				//���[�h�ҏW
				if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
						JwsConstManager.JWS_COMPONENT_0025, DataCtrl.getInstance().getParamData().getStrMode())){
					((HankakuTextbox)GenryoCD).setEditable(false);

				}else{
					GenryoCD.addMouseListener(new analysisDisp());
					//GenryoCD.addKeyListener(new CopyGenryo());
				}

//add start -------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.33,34
				//�H���L�[���ڎ擾
				int intShisakuSeq = 0;
				int intKoteiCd = Integer.parseInt(koteiCd);
				int intKoteiSeq = Integer.parseInt(koteiSeq);
				boolean chk = DataCtrl.getInstance().getTrialTblData().checkListHenshuOkFg(intShisakuSeq, intKoteiCd, intKoteiSeq);

				//�ҏW�\�̏ꍇ�F��������
				if(chk){
					//���[�h�ҏW
					if(DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
							JwsConstManager.JWS_COMPONENT_0025, DataCtrl.getInstance().getParamData().getStrMode())){
						((HankakuTextbox)GenryoCD).setEditable(true);
					}
				}
				//�ҏW�s�̏ꍇ�F�R���|�[�l���g�̑���s��
				else{
					((HankakuTextbox)GenryoCD).setBackground(JwsConstManager.JWS_DISABLE_COLOR);
					((HankakuTextbox)GenryoCD).setEditable(false);
				}
//add end   -------------------------------------------------------------------------------

				editGenryoCD = new TextFieldCellEditor((HankakuTextbox)GenryoCD);
				rendGenryoCD = new TextFieldCellRenderer((HankakuTextbox)GenryoCD);

			//�H���s
			}else if(flg == 1){

				GenryoCD = new ComboBase();
				GenryoCD.setFont(new Font("Default", Font.PLAIN, 11));
				GenryoCD.setBackground(Color.WHITE);

//2011/05/30�@�yQP@10181_No.42_49_72�z�H���p�^�[������@TT.NISHIGAWA�@start
				//setLiteralCmb((ComboBase)GenryoCD, DataCtrl.getInstance().getLiteralDataZokusei(), "", 0);

				//�H���p�^�[���擾
				String ptKotei = PrototypeData.getStrPt_kotei();

				//�H���p�^�[�����u�󔒁v�̏ꍇ
				if(ptKotei == null || ptKotei.length() == 0){
					//�I��l�ɋ�s�̂�
					((ComboBase) GenryoCD).removeAllItems();
					((ComboBase) GenryoCD).addItem("");
				}
				//�H���p�^�[�����u�󔒁v�łȂ��ꍇ
				else{
					//�H���p�^�[����Value1�擾
					String ptValue = DataCtrl.getInstance().getLiteralDataKoteiPtn().selectLiteralVal1(ptKotei);

					//�H���p�^�[�����u�������P�t�^�C�v�v�̏ꍇ
					if(ptValue.equals(JwsConstManager.JWS_KOTEITYPE_1)){
						//�I��l��ݒ�
						setLiteralCmb((ComboBase)GenryoCD, DataCtrl.getInstance().getLiteralDataKotei_tyomi1(), "", 0);

					}
					//�H���p�^�[�����u�������Q�t�^�C�v�v�̏ꍇ
					else if(ptValue.equals(JwsConstManager.JWS_KOTEITYPE_2)){
						//�I��l��ݒ�
						setLiteralCmb((ComboBase)GenryoCD, DataCtrl.getInstance().getLiteralDataKotei_tyomi2(), "", 0);

					}
					//�H���p�^�[�����u���̑��E���H�^�C�v�v�̏ꍇ
					else if(ptValue.equals(JwsConstManager.JWS_KOTEITYPE_3)){
						//�I��l��ݒ�
						setLiteralCmb((ComboBase)GenryoCD, DataCtrl.getInstance().getLiteralDataKotei_sonota(), "", 0);

					}
				}


//2011/05/30�@�yQP@10181_No.42_49_72�z�H���p�^�[������@TT.NISHIGAWA�@end

				//���[�h�ҏW
				if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
						JwsConstManager.JWS_COMPONENT_0024, DataCtrl.getInstance().getParamData().getStrMode())){
					((ComboBase)GenryoCD).setEnabled(false);
				}

//add start -------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.33,34
				//�H���L�[���ڎ擾
				int intShisakuSeq = 0;
				int intKoteiCd = Integer.parseInt(koteiCd);
				int intKoteiSeq = 0;
				boolean chk = DataCtrl.getInstance().getTrialTblData().checkListHenshuOkFg(intShisakuSeq, intKoteiCd, intKoteiSeq);

				//�ҏW�\�̏ꍇ�F��������
				if(chk){
					//���[�h�ҏW
					if(DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
							JwsConstManager.JWS_COMPONENT_0024, DataCtrl.getInstance().getParamData().getStrMode())){
						((ComboBase)GenryoCD).setEnabled(true);
					}
				}
				//�ҏW�s�̏ꍇ�F�R���|�[�l���g�̑���s��
				else{
					((ComboBase)GenryoCD).setEnabled(false);
				}
//add end   -------------------------------------------------------------------------------

				editGenryoCD = new ComboBoxCellEditor((ComboBase)GenryoCD);
				rendGenryoCD = new ComboBoxCellRenderer((ComboBase)GenryoCD);

			//�v�Z�s
			}else{

				//�R���|�[�l���g����
				GenryoCD = new TextboxBase();
				((TextboxBase)GenryoCD).setEditable(false);
				GenryoCD.setBackground(Color.WHITE);

				//�G�f�B�^�[�������_���[����
				editGenryoCD = new TextFieldCellEditor((TextboxBase)GenryoCD);
				rendGenryoCD = new TextFieldCellRenderer((TextboxBase)GenryoCD);
			}
			//���ԃZ���G�f�B�^�[�������_���[�֓o�^
			HaigoMeisaiCellEditor3.addEditorAt(row, editGenryoCD);
			HaigoMeisaiCellRenderer3.add(row, rendGenryoCD);

			//------------------ �������G�f�B�^�[�������_���[����  -------------------------
			JComponent GenryoNm;
			TextFieldCellEditor editGenryoNm;
			TextFieldCellRenderer rendGenryoNm;

//add start -------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.33,34
			//�H���L�[���ڎ擾
			int intShisakuSeq = 0;
			int intKoteiCd = Integer.parseInt(koteiCd);
			int intKoteiSeq = Integer.parseInt(koteiSeq);
			boolean chk = DataCtrl.getInstance().getTrialTblData().checkListHenshuOkFg(intShisakuSeq, intKoteiCd, intKoteiSeq);
//add end   -------------------------------------------------------------------------------

//add start -------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.30
			boolean chk_2 = DataCtrl.getInstance().getTrialTblData().searchHaigouGenryoCdSinki(intKoteiCd, intKoteiSeq);
//add end   -------------------------------------------------------------------------------
			//2011/06/02 QP@10181_No.73 TT T.Satoh Add Start -------------------------
			//��ЃR�[�h���L���s�[������
			boolean kaishaCd_QP = DataCtrl.getInstance().getTrialTblData().searchHaigouKaishaCd(intKoteiCd, intKoteiSeq);
			//2011/06/02 QP@10181_No.73 TT T.Satoh Add End ---------------------------

			//�����s
			if(flg == 0){
				GenryoNm = new TextboxBase();

				//���[�h�ҏW
				if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
						JwsConstManager.JWS_COMPONENT_0027, DataCtrl.getInstance().getParamData().getStrMode())){
					((TextboxBase)GenryoNm).setEditable(false);

				}else{

					//�����s�̏ꍇ�F�F�ύX�C�x���g�ǉ�
					((TextboxBase)GenryoNm).addMouseListener(new colorChange(HaigoMeisai,0));
				}

//add start -------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.30,No.33,No.34
				//�ҏW�\�̏ꍇ�F��������
				if(chk && chk_2){

				}
				//�ҏW�s�̏ꍇ�F�R���|�[�l���g�̑���s��
				else{
					((TextboxBase)GenryoNm).setBackground(JwsConstManager.JWS_DISABLE_COLOR);
					((TextboxBase)GenryoNm).setEditable(false);
				}
//add end   -------------------------------------------------------------------------------

				editGenryoNm = new TextFieldCellEditor((TextboxBase)GenryoNm);
				rendGenryoNm = new TextFieldCellRenderer((TextboxBase)GenryoNm);

			//�H���s
			}else if(flg == 1){
				GenryoNm = new TextboxBase();

				//���[�h�ҏW
				if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
						JwsConstManager.JWS_COMPONENT_0026, DataCtrl.getInstance().getParamData().getStrMode())){
					((TextboxBase)GenryoNm).setEditable(false);
				}
				editGenryoNm = new TextFieldCellEditor((TextboxBase)GenryoNm);
				rendGenryoNm = new TextFieldCellRenderer((TextboxBase)GenryoNm);

			//�v�Z�s
			}else{
				//�R���|�[�l���g����
				GenryoNm = new TextboxBase();
				((TextboxBase)GenryoNm).setEditable(false);
				GenryoNm.setBackground(Color.WHITE);

				//�G�f�B�^�[�������_���[����
				editGenryoNm = new TextFieldCellEditor((TextboxBase)GenryoNm);
				rendGenryoNm = new TextFieldCellRenderer((TextboxBase)GenryoNm);
			}

//mod start -------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.30,No.33,No.34
			//�F�ݒ�
			//rendGenryoNm.setColor(new Color(color));

			//�ҏW�\�̏ꍇ�F��������
			if(chk && chk_2){
				rendGenryoNm.setColor(new Color(color));
			}
			//�ҏW�s�̏ꍇ�F�F�ύX�s��
			else{

			}
//mod end   -------------------------------------------------------------------------------


			//���ԃZ���G�f�B�^�[�������_���[�֓o�^
			HaigoMeisaiCellEditor4.addEditorAt(row, editGenryoNm);
			HaigoMeisaiCellRenderer4.add(row, rendGenryoNm);

			//-------------------- �P���G�f�B�^�[�������_���[����  --------------------------
			JComponent Tanka;

			//�����s
			if(flg == 0){

				Tanka = new NumelicTextbox();

				//���[�h�ҏW
				if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
						JwsConstManager.JWS_COMPONENT_0028, DataCtrl.getInstance().getParamData().getStrMode())){
					((NumelicTextbox)Tanka).setEditable(false);
				}else{
					Tanka.addMouseListener(new colorChange(HaigoMeisai,0));
				}

//add start -------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No30,.33,34
				//�ҏW�\�̏ꍇ�F��������
				if(chk && chk_2){
					((TextboxBase)Tanka).setEditable(true);
				}
				//�ҏW�s�̏ꍇ�F�R���|�[�l���g�̑���s��
				else{
					((TextboxBase)Tanka).setBackground(JwsConstManager.JWS_DISABLE_COLOR);
					((TextboxBase)Tanka).setEditable(false);
				}
//add end   -------------------------------------------------------------------------------

				//2011/05/10 QP@10181_No.73 TT T.Satoh Add Start -------------------------
				//�z���ʂ����͂���Ă��Ȃ��ꍇ
				if(chk){

					//���CD���L���[�s�[�̏ꍇ
					if (kaishaCd_QP) {

					}
					//���CD���L���[�s�[�łȂ��ꍇ
					else{
						//�P����ҏW�\�ɂ���
						((TextboxBase)Tanka).setBackground(Color.WHITE);
						((TextboxBase)Tanka).setEditable(true);
					}
				}

				//���[�h�ҏW
				if(DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
						JwsConstManager.JWS_COMPONENT_0028, DataCtrl.getInstance().getParamData().getStrMode())){
				}
				else{
					((NumelicTextbox)Tanka).setEditable(false);
				}
				//2011/05/17 QP@10181_No.73 TT T.Satoh Add End ---------------------------

			//�H���s
			}else{
				//TextField�i���͕s�j�R���|�[�l���g����
				Tanka = new TextboxBase();
				((TextboxBase)Tanka).setEditable(false);
				Tanka.setBackground(Color.WHITE);
			}

			TextFieldCellEditor editTanka = new TextFieldCellEditor((TextboxBase)Tanka);
			TextFieldCellRenderer rendTanka = new TextFieldCellRenderer((TextboxBase)Tanka);

//mod start -------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.30,No.33,No.34
			//�F�ݒ�
			//rendTanka.setColor(new Color(color));

			//�ҏW�\�̏ꍇ�F��������
			if(chk && chk_2){
				rendTanka.setColor(new Color(color));
			}
			//�ҏW�s�̏ꍇ�F�F�ύX�s��
			else{

			}
//mod end   -------------------------------------------------------------------------------

			//���ԃZ���G�f�B�^�[�������_���[�֓o�^
			HaigoMeisaiCellEditor5.addEditorAt(row, editTanka);
			HaigoMeisaiCellRenderer5.add(row, rendTanka);

			//--------------------- �����G�f�B�^�[�������_���[����  --------------------------
			JComponent Budomari;
			//�����s
			if(flg == 0){
				Budomari = new NumelicTextbox();
				//���[�h�ҏW
				if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
						JwsConstManager.JWS_COMPONENT_0029, DataCtrl.getInstance().getParamData().getStrMode())){
					((NumelicTextbox)Budomari).setEditable(false);
				}else{
					Budomari.addMouseListener(new colorChange(HaigoMeisai,0));
				}
//add start -------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.33,No.34
				//�ҏW�\�̏ꍇ�F��������
				if(chk){
					((NumelicTextbox)Budomari).setEditable(true);
				}
				//�ҏW�s�̏ꍇ�F�R���|�[�l���g�̑���s��
				else{
					((NumelicTextbox)Budomari).setBackground(JwsConstManager.JWS_DISABLE_COLOR);
					((NumelicTextbox)Budomari).setEditable(false);
				}
//add end   -------------------------------------------------------------------------------

//add start -------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.31
				boolean chk_3 = DataCtrl.getInstance().getTrialTblData().searchHaigouGenryoMaBudomari(intKoteiCd, intKoteiSeq);
				//�������}�X�^�Ɠ���̏ꍇ�F��������
				if(chk_3){

				}
				//�������}�X�^�Ƒ���̏ꍇ�F�R���|�[�l���g�̑���s��
				else{
					((NumelicTextbox)Budomari).setFont(new Font("Default", Font.BOLD, 12));
					((NumelicTextbox)Budomari).setForeground(Color.red);
				}
//add end   -------------------------------------------------------------------------------

				//2011/05/17 QP@10181_No.73 TT T.Satoh Add Start -------------------------
				//���[�h�ҏW
				if (!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
						JwsConstManager.JWS_COMPONENT_0029, DataCtrl.getInstance().getParamData().getStrMode())) {
					//��������͕s�\�ɂ���
					((NumelicTextbox)Budomari).setEditable(false);
				}
				//2011/05/17 QP@10181_No.73 TT T.Satoh Add End ---------------------------

			//�H���s
			}else{
				//TextField�i���͕s�j�R���|�[�l���g����
				Budomari = new TextboxBase();
				((TextboxBase)Budomari).setEditable(false);
				Budomari.setBackground(Color.WHITE);
			}
			TextFieldCellEditor editBudomari = new TextFieldCellEditor((TextboxBase)Budomari);
			TextFieldCellRenderer rendBudomari = new TextFieldCellRenderer((TextboxBase)Budomari);

//mod start -------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.33,No.34
			//�F�ݒ�
			//rendBudomari.setColor(new Color(color));

			//�ҏW�\�̏ꍇ�F��������
			if(chk){
				rendBudomari.setColor(new Color(color));
			}
			//�ҏW�s�̏ꍇ�F�F�ύX�s��
			else{

			}
//mod end   -------------------------------------------------------------------------------

			//���ԃZ���G�f�B�^�[�������_���[�֓o�^
			HaigoMeisaiCellEditor6.addEditorAt(row, editBudomari);
			HaigoMeisaiCellRenderer6.add(row, rendBudomari);

			//------------------- ���ܗL���G�f�B�^�[�������_���[����  -------------------------
			JComponent Abura;
			//�����s
			if(flg == 0){
				Abura = new NumelicTextbox();
				//���[�h�ҏW
				if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
						JwsConstManager.JWS_COMPONENT_0030, DataCtrl.getInstance().getParamData().getStrMode())){
					((NumelicTextbox)Abura).setEditable(false);
				}else{
					Abura.addMouseListener(new colorChange(HaigoMeisai,0));
				}

//add start -------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.33,No.34
				//�ҏW�\�̏ꍇ�F��������
				if(chk){
					((NumelicTextbox)Abura).setEditable(true);
				}
				//�ҏW�s�̏ꍇ�F�R���|�[�l���g�̑���s��
				else{
					((NumelicTextbox)Abura).setBackground(JwsConstManager.JWS_DISABLE_COLOR);
					((NumelicTextbox)Abura).setEditable(false);
				}
//add end   -------------------------------------------------------------------------------

				//2011/05/17 QP@10181_No.73 TT T.Satoh Add Start -------------------------
				//���[�h�ҏW
				if (!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
						JwsConstManager.JWS_COMPONENT_0030, DataCtrl.getInstance().getParamData().getStrMode())) {
					//���ܗL������͕s�\�ɂ���
					((NumelicTextbox)Abura).setEditable(false);
				}
				//2011/05/17 QP@10181_No.73 TT T.Satoh Add End ---------------------------

			//�H���s
			}else{
				//TextField�i���͕s�j�R���|�[�l���g����
				Abura = new TextboxBase();
				((TextboxBase)Abura).setEditable(false);
				Abura.setBackground(Color.WHITE);
			}
			TextFieldCellEditor editAbura = new TextFieldCellEditor((TextboxBase)Abura);
			TextFieldCellRenderer rendAbura = new TextFieldCellRenderer((TextboxBase)Abura);
//mod start -------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.33,No.34
			//�F�ݒ�
			//rendAbura.setColor(new Color(color));

			//�ҏW�\�̏ꍇ�F��������
			if(chk){
				rendAbura.setColor(new Color(color));
			}
			//�ҏW�s�̏ꍇ�F�F�ύX�s��
			else{

			}
//mod end   -------------------------------------------------------------------------------

			//���ԃZ���G�f�B�^�[�������_���[�֓o�^
			HaigoMeisaiCellEditor7.addEditorAt(row, editAbura);
			HaigoMeisaiCellRenderer7.add(row, rendAbura);

		}catch(ExceptionBase eb){
			eb.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}finally{

		}
	}


	/************************************************************************************
	 *
	 *   �z�����׃e�[�u���@�����G�f�B�^�������_���s�ړ�
	 *    : �z�����׃e�[�u���֌����s�̃Z���G�f�B�^�������_���̈ړ����s��
	 *   @author TT nishigawa
	 *   @param row_moto : �ړ����@�s�ԍ�
	 *   @param row_saki : �ړ���@�s�ԍ�
	 *
	 ************************************************************************************/
	public int getKeta(){

		//����������
		String keta = JwsConstManager.JWS_KETA_GENRYO;

		try{

			//�w���Ў擾
			int selKaisha = DataCtrl.getInstance().getTrialTblData().SearchShisakuhinData().getIntKaishacd();

			//��Ѓf�[�^�S���擾
			KaishaData kaishaData = DataCtrl.getInstance().getKaishaData();

			for ( int i=0; i<kaishaData.getArtKaishaCd().size(); i++ ) {

				//��ЃR�[�h�擾
				int kaishaCd = Integer.parseInt(kaishaData.getArtKaishaCd().get(i).toString());

				//������
				String keta_genryo = kaishaData.getAryKaishaGenryo().get(i).toString();

				//�I����ЃR�[�h�̌��o
				if ( kaishaCd == selKaisha ) {

					//�����擾
					keta = keta_genryo;

				}
			}

		}catch(Exception e){

			e.printStackTrace();

		}finally{

		}

		return Integer.parseInt(keta);

	}

	/************************************************************************************
	 *
	 *   �z�����׃e�[�u���@�����G�f�B�^�������_���s�ړ�
	 *    : �z�����׃e�[�u���֌����s�̃Z���G�f�B�^�������_���̈ړ����s��
	 *   @author TT nishigawa
	 *   @param row_moto : �ړ����@�s�ԍ�
	 *   @param row_saki : �ړ���@�s�ԍ�
	 *
	 ************************************************************************************/
	public void moveHaigoMeisaiRowER(int row_moto,int row_saki){
		try{
			//-------------------- ���֌��G�f�B�^�������_���擾  ----------------------------------
			//1���
			MiddleCellEditor mc_0_moto = (MiddleCellEditor)HaigoMeisai.getCellEditor(row_moto,0);
			TableCellEditor dc_0_moto = mc_0_moto.getTableCellEditor(row_moto);
			MiddleCellRenderer mr_0_moto = (MiddleCellRenderer)HaigoMeisai.getCellRenderer(row_moto, 0);
			TableCellRenderer cc_0_moto = mr_0_moto.getTableCellRenderer(row_moto);
			mc_0_moto.removeEditorAt(row_moto); //���ԃG�f�B�^���폜
			mr_0_moto.remove(row_moto); 		//���ԃ����_�����폜
			//2���
			MiddleCellEditor mc_1_moto = (MiddleCellEditor)HaigoMeisai.getCellEditor(row_moto,1);
			TableCellEditor dc_1_moto = mc_1_moto.getTableCellEditor(row_moto);
			MiddleCellRenderer mr_1_moto = (MiddleCellRenderer)HaigoMeisai.getCellRenderer(row_moto, 1);
			TableCellRenderer cc_1_moto = mr_1_moto.getTableCellRenderer(row_moto);
			mc_1_moto.removeEditorAt(row_moto); //���ԃG�f�B�^���폜
			mr_1_moto.remove(row_moto); 		//���ԃ����_�����폜
			//3���
			MiddleCellEditor mc_2_moto = (MiddleCellEditor)HaigoMeisai.getCellEditor(row_moto,2);
			TableCellEditor dc_2_moto = mc_2_moto.getTableCellEditor(row_moto);
			MiddleCellRenderer mr_2_moto = (MiddleCellRenderer)HaigoMeisai.getCellRenderer(row_moto, 2);
			TableCellRenderer cc_2_moto = mr_2_moto.getTableCellRenderer(row_moto);
			mc_2_moto.removeEditorAt(row_moto); //���ԃG�f�B�^���폜
			mr_2_moto.remove(row_moto); 		//���ԃ����_�����폜
			//4���
			MiddleCellEditor mc_3_moto = (MiddleCellEditor)HaigoMeisai.getCellEditor(row_moto,3);
			TableCellEditor dc_3_moto = mc_3_moto.getTableCellEditor(row_moto);
			MiddleCellRenderer mr_3_moto = (MiddleCellRenderer)HaigoMeisai.getCellRenderer(row_moto, 3);
			TableCellRenderer cc_3_moto = mr_3_moto.getTableCellRenderer(row_moto);
			mc_3_moto.removeEditorAt(row_moto); //���ԃG�f�B�^���폜
			mr_3_moto.remove(row_moto); 		//���ԃ����_�����폜
			//5���
			MiddleCellEditor mc_4_moto = (MiddleCellEditor)HaigoMeisai.getCellEditor(row_moto,4);
			TableCellEditor dc_4_moto = mc_4_moto.getTableCellEditor(row_moto);
			MiddleCellRenderer mr_4_moto = (MiddleCellRenderer)HaigoMeisai.getCellRenderer(row_moto, 4);
			TableCellRenderer cc_4_moto = mr_4_moto.getTableCellRenderer(row_moto);
			mc_4_moto.removeEditorAt(row_moto); //���ԃG�f�B�^���폜
			mr_4_moto.remove(row_moto); 		//���ԃ����_�����폜
			//6���
			MiddleCellEditor mc_5_moto = (MiddleCellEditor)HaigoMeisai.getCellEditor(row_moto,5);
			TableCellEditor dc_5_moto = mc_5_moto.getTableCellEditor(row_moto);
			MiddleCellRenderer mr_5_moto = (MiddleCellRenderer)HaigoMeisai.getCellRenderer(row_moto, 5);
			TableCellRenderer cc_5_moto = mr_5_moto.getTableCellRenderer(row_moto);
			mc_5_moto.removeEditorAt(row_moto); //���ԃG�f�B�^���폜
			mr_5_moto.remove(row_moto); 		//���ԃ����_�����폜
			//7���
			MiddleCellEditor mc_6_moto = (MiddleCellEditor)HaigoMeisai.getCellEditor(row_moto,6);
			TableCellEditor dc_6_moto = mc_6_moto.getTableCellEditor(row_moto);
			MiddleCellRenderer mr_6_moto = (MiddleCellRenderer)HaigoMeisai.getCellRenderer(row_moto, 6);
			TableCellRenderer cc_6_moto = mr_6_moto.getTableCellRenderer(row_moto);
			mc_6_moto.removeEditorAt(row_moto); //���ԃG�f�B�^���폜
			mr_6_moto.remove(row_moto); 		//���ԃ����_�����폜
			//8���
			MiddleCellEditor mc_7_moto = (MiddleCellEditor)HaigoMeisai.getCellEditor(row_moto,7);
			TableCellEditor dc_7_moto = mc_7_moto.getTableCellEditor(row_moto);
			MiddleCellRenderer mr_7_moto = (MiddleCellRenderer)HaigoMeisai.getCellRenderer(row_moto, 7);
			TableCellRenderer cc_7_moto = mr_7_moto.getTableCellRenderer(row_moto);
			mc_7_moto.removeEditorAt(row_moto); //���ԃG�f�B�^���폜
			mr_7_moto.remove(row_moto); 		//���ԃ����_�����폜

			//------------------------- �G�f�B�^�������_���ړ�  ----------------------------------
			//1���
			mc_0_moto.addEditorAt(row_saki, dc_0_moto);
			mr_0_moto.add(row_saki, cc_0_moto);
			//2���
			mc_1_moto.addEditorAt(row_saki, dc_1_moto);
			mr_1_moto.add(row_saki, cc_1_moto);
			//3���
			mc_2_moto.addEditorAt(row_saki, dc_2_moto);
			mr_2_moto.add(row_saki, cc_2_moto);
			//4���
			mc_3_moto.addEditorAt(row_saki, dc_3_moto);
			mr_3_moto.add(row_saki, cc_3_moto);
			//5���
			mc_4_moto.addEditorAt(row_saki, dc_4_moto);
			mr_4_moto.add(row_saki, cc_4_moto);
			//6���
			mc_5_moto.addEditorAt(row_saki, dc_5_moto);
			mr_5_moto.add(row_saki, cc_5_moto);
			//7���
			mc_6_moto.addEditorAt(row_saki, dc_6_moto);
			mr_6_moto.add(row_saki, cc_6_moto);
			//8���
			mc_7_moto.addEditorAt(row_saki, dc_7_moto);
			mr_7_moto.add(row_saki, cc_7_moto);
		}catch(Exception e){
			e.printStackTrace();
		}finally{

		}
	}

	/************************************************************************************
	 *
	 *   �z�����׃e�[�u���@�����G�f�B�^�������_���s����
	 *    : �z�����׃e�[�u���֌����s�̃Z���G�f�B�^�������_������ւ���
	 *   @author TT nishigawa
	 *   @param row_moto : ���֌��@�s�ԍ�
	 *   @param row_saki : ���֐�@�s�ԍ�
	 *
	 ************************************************************************************/
	public void changeHaigoMeisaiRowER(int row_moto,int row_saki){
		try{
			//-------------------- ���֌��G�f�B�^�������_���擾  ----------------------------------
			//1���
			MiddleCellEditor mc_0_moto = (MiddleCellEditor)HaigoMeisai.getCellEditor(row_moto,0);
			TableCellEditor dc_0_moto = mc_0_moto.getTableCellEditor(row_moto);
			MiddleCellRenderer mr_0_moto = (MiddleCellRenderer)HaigoMeisai.getCellRenderer(row_moto, 0);
			TableCellRenderer cc_0_moto = mr_0_moto.getTableCellRenderer(row_moto);
			//2���
			MiddleCellEditor mc_1_moto = (MiddleCellEditor)HaigoMeisai.getCellEditor(row_moto,1);
			TableCellEditor dc_1_moto = mc_1_moto.getTableCellEditor(row_moto);
			MiddleCellRenderer mr_1_moto = (MiddleCellRenderer)HaigoMeisai.getCellRenderer(row_moto, 1);
			TableCellRenderer cc_1_moto = mr_1_moto.getTableCellRenderer(row_moto);
			//3���
			MiddleCellEditor mc_2_moto = (MiddleCellEditor)HaigoMeisai.getCellEditor(row_moto,2);
			TableCellEditor dc_2_moto = mc_2_moto.getTableCellEditor(row_moto);
			MiddleCellRenderer mr_2_moto = (MiddleCellRenderer)HaigoMeisai.getCellRenderer(row_moto, 2);
			TableCellRenderer cc_2_moto = mr_2_moto.getTableCellRenderer(row_moto);
			//4���
			MiddleCellEditor mc_3_moto = (MiddleCellEditor)HaigoMeisai.getCellEditor(row_moto,3);
			TableCellEditor dc_3_moto = mc_3_moto.getTableCellEditor(row_moto);
			MiddleCellRenderer mr_3_moto = (MiddleCellRenderer)HaigoMeisai.getCellRenderer(row_moto, 3);
			TableCellRenderer cc_3_moto = mr_3_moto.getTableCellRenderer(row_moto);
			//5���
			MiddleCellEditor mc_4_moto = (MiddleCellEditor)HaigoMeisai.getCellEditor(row_moto,4);
			TableCellEditor dc_4_moto = mc_4_moto.getTableCellEditor(row_moto);
			MiddleCellRenderer mr_4_moto = (MiddleCellRenderer)HaigoMeisai.getCellRenderer(row_moto, 4);
			TableCellRenderer cc_4_moto = mr_4_moto.getTableCellRenderer(row_moto);
			//6���
			MiddleCellEditor mc_5_moto = (MiddleCellEditor)HaigoMeisai.getCellEditor(row_moto,5);
			TableCellEditor dc_5_moto = mc_5_moto.getTableCellEditor(row_moto);
			MiddleCellRenderer mr_5_moto = (MiddleCellRenderer)HaigoMeisai.getCellRenderer(row_moto, 5);
			TableCellRenderer cc_5_moto = mr_5_moto.getTableCellRenderer(row_moto);
			//7���
			MiddleCellEditor mc_6_moto = (MiddleCellEditor)HaigoMeisai.getCellEditor(row_moto,6);
			TableCellEditor dc_6_moto = mc_6_moto.getTableCellEditor(row_moto);
			MiddleCellRenderer mr_6_moto = (MiddleCellRenderer)HaigoMeisai.getCellRenderer(row_moto, 6);
			TableCellRenderer cc_6_moto = mr_6_moto.getTableCellRenderer(row_moto);
			//8���
			MiddleCellEditor mc_7_moto = (MiddleCellEditor)HaigoMeisai.getCellEditor(row_moto,7);
			TableCellEditor dc_7_moto = mc_7_moto.getTableCellEditor(row_moto);
			MiddleCellRenderer mr_7_moto = (MiddleCellRenderer)HaigoMeisai.getCellRenderer(row_moto, 7);
			TableCellRenderer cc_7_moto = mr_7_moto.getTableCellRenderer(row_moto);

			//-------------------- ���֐�G�f�B�^�������_���擾  -----------------------------------
			//1���
			MiddleCellEditor mc_0_saki = (MiddleCellEditor)HaigoMeisai.getCellEditor(row_saki,0);
			TableCellEditor dc_0_saki = mc_0_saki.getTableCellEditor(row_saki);
			MiddleCellRenderer mr_0_saki = (MiddleCellRenderer)HaigoMeisai.getCellRenderer(row_saki, 0);
			TableCellRenderer cc_0_saki = mr_0_moto.getTableCellRenderer(row_saki);
			//2���
			MiddleCellEditor mc_1_saki = (MiddleCellEditor)HaigoMeisai.getCellEditor(row_saki,1);
			TableCellEditor dc_1_saki = mc_1_saki.getTableCellEditor(row_saki);
			MiddleCellRenderer mr_1_saki = (MiddleCellRenderer)HaigoMeisai.getCellRenderer(row_saki, 1);
			TableCellRenderer cc_1_saki = mr_1_moto.getTableCellRenderer(row_saki);
			//3���
			MiddleCellEditor mc_2_saki = (MiddleCellEditor)HaigoMeisai.getCellEditor(row_saki,2);
			TableCellEditor dc_2_saki = mc_2_saki.getTableCellEditor(row_saki);
			MiddleCellRenderer mr_2_saki = (MiddleCellRenderer)HaigoMeisai.getCellRenderer(row_saki, 2);
			TableCellRenderer cc_2_saki = mr_2_moto.getTableCellRenderer(row_saki);
			//4���
			MiddleCellEditor mc_3_saki = (MiddleCellEditor)HaigoMeisai.getCellEditor(row_saki,3);
			TableCellEditor dc_3_saki = mc_3_saki.getTableCellEditor(row_saki);
			MiddleCellRenderer mr_3_saki = (MiddleCellRenderer)HaigoMeisai.getCellRenderer(row_saki, 3);
			TableCellRenderer cc_3_saki = mr_3_moto.getTableCellRenderer(row_saki);
			//5���
			MiddleCellEditor mc_4_saki = (MiddleCellEditor)HaigoMeisai.getCellEditor(row_saki,4);
			TableCellEditor dc_4_saki = mc_4_saki.getTableCellEditor(row_saki);
			MiddleCellRenderer mr_4_saki = (MiddleCellRenderer)HaigoMeisai.getCellRenderer(row_saki, 4);
			TableCellRenderer cc_4_saki = mr_4_moto.getTableCellRenderer(row_saki);
			//6���
			MiddleCellEditor mc_5_saki = (MiddleCellEditor)HaigoMeisai.getCellEditor(row_saki,5);
			TableCellEditor dc_5_saki = mc_5_saki.getTableCellEditor(row_saki);
			MiddleCellRenderer mr_5_saki = (MiddleCellRenderer)HaigoMeisai.getCellRenderer(row_saki, 5);
			TableCellRenderer cc_5_saki = mr_5_moto.getTableCellRenderer(row_saki);
			//7���
			MiddleCellEditor mc_6_saki = (MiddleCellEditor)HaigoMeisai.getCellEditor(row_saki,6);
			TableCellEditor dc_6_saki = mc_6_saki.getTableCellEditor(row_saki);
			MiddleCellRenderer mr_6_saki = (MiddleCellRenderer)HaigoMeisai.getCellRenderer(row_saki, 6);
			TableCellRenderer cc_6_saki = mr_6_moto.getTableCellRenderer(row_saki);
			//8���
			MiddleCellEditor mc_7_saki = (MiddleCellEditor)HaigoMeisai.getCellEditor(row_saki,7);
			TableCellEditor dc_7_saki = mc_7_saki.getTableCellEditor(row_saki);
			MiddleCellRenderer mr_7_saki = (MiddleCellRenderer)HaigoMeisai.getCellRenderer(row_saki, 7);
			TableCellRenderer cc_7_saki = mr_7_moto.getTableCellRenderer(row_saki);

			//----------------------- �G�f�B�^�������_������  --------------------------------------
			//1���
			HaigoMeisaiCellEditor0.setEditorAt(row_saki, dc_0_moto);
			HaigoMeisaiCellEditor0.setEditorAt(row_moto, dc_0_saki);
			HaigoMeisaiCellRenderer0.set(row_saki, cc_0_moto);
			HaigoMeisaiCellRenderer0.set(row_moto, cc_0_saki);
			//2���
			HaigoMeisaiCellEditor1.setEditorAt(row_saki, dc_1_moto);
			HaigoMeisaiCellEditor1.setEditorAt(row_moto, dc_1_saki);
			HaigoMeisaiCellRenderer1.set(row_saki, cc_1_moto);
			HaigoMeisaiCellRenderer1.set(row_moto, cc_1_saki);
			//3���
			HaigoMeisaiCellEditor2.setEditorAt(row_saki, dc_2_moto);
			HaigoMeisaiCellEditor2.setEditorAt(row_moto, dc_2_saki);
			HaigoMeisaiCellRenderer2.set(row_saki, cc_2_moto);
			HaigoMeisaiCellRenderer2.set(row_moto, cc_2_saki);
			//4���
			HaigoMeisaiCellEditor3.setEditorAt(row_saki, dc_3_moto);
			HaigoMeisaiCellEditor3.setEditorAt(row_moto, dc_3_saki);
			HaigoMeisaiCellRenderer3.set(row_saki, cc_3_moto);
			HaigoMeisaiCellRenderer3.set(row_moto, cc_3_saki);
			//5���
			HaigoMeisaiCellEditor4.setEditorAt(row_saki, dc_4_moto);
			HaigoMeisaiCellEditor4.setEditorAt(row_moto, dc_4_saki);
			HaigoMeisaiCellRenderer4.set(row_saki, cc_4_moto);
			HaigoMeisaiCellRenderer4.set(row_moto, cc_4_saki);
			//6���
			HaigoMeisaiCellEditor5.setEditorAt(row_saki, dc_5_moto);
			HaigoMeisaiCellEditor5.setEditorAt(row_moto, dc_5_saki);
			HaigoMeisaiCellRenderer5.set(row_saki, cc_5_moto);
			HaigoMeisaiCellRenderer5.set(row_moto, cc_5_saki);
			//7���
			HaigoMeisaiCellEditor6.setEditorAt(row_saki, dc_6_moto);
			HaigoMeisaiCellEditor6.setEditorAt(row_moto, dc_6_saki);
			HaigoMeisaiCellRenderer6.set(row_saki, cc_6_moto);
			HaigoMeisaiCellRenderer6.set(row_moto, cc_6_saki);
			//8���
			HaigoMeisaiCellEditor7.setEditorAt(row_saki, dc_7_moto);
			HaigoMeisaiCellEditor7.setEditorAt(row_moto, dc_7_saki);
			HaigoMeisaiCellRenderer7.set(row_saki, cc_7_moto);
			HaigoMeisaiCellRenderer7.set(row_moto, cc_7_saki);
		}catch(Exception e){
			e.printStackTrace();
		}finally{

		}
	}

	/************************************************************************************
	 *
	 *   �z�����׃e�[�u���@�����G�f�B�^�������_���s�폜
	 *    : �z�����׃e�[�u���֌����s�̃Z���G�f�B�^�������_�����폜����
	 *   @author TT nishigawa
	 *   @param row : �폜�s
	 *
	 ************************************************************************************/
	public void delHaigoMeisaiRowER(int row){
		try{
			//--------------- �폜�s�G�f�B�^�[�������_���[�폜 -------------------------
			//1���
			HaigoMeisaiCellEditor0.removeEditorAt(row);
			HaigoMeisaiCellRenderer0.remove(row);
			//2���
			HaigoMeisaiCellEditor1.removeEditorAt(row);
			HaigoMeisaiCellRenderer1.remove(row);
			//3���
			HaigoMeisaiCellEditor2.removeEditorAt(row);
			HaigoMeisaiCellRenderer2.remove(row);
			//4���
			HaigoMeisaiCellEditor3.removeEditorAt(row);
			HaigoMeisaiCellRenderer3.remove(row);
			//5���
			HaigoMeisaiCellEditor4.removeEditorAt(row);
			HaigoMeisaiCellRenderer4.remove(row);
			//6���
			HaigoMeisaiCellEditor5.removeEditorAt(row);
			HaigoMeisaiCellRenderer5.remove(row);
			//7���
			HaigoMeisaiCellEditor6.removeEditorAt(row);
			HaigoMeisaiCellRenderer6.remove(row);
			//8���
			HaigoMeisaiCellEditor7.removeEditorAt(row);
			HaigoMeisaiCellRenderer7.remove(row);
		}catch(Exception e){
			e.printStackTrace();
		}finally{

		}
	}

	/************************************************************************************
	 *
	 *   �z�����׃e�[�u���@�G�f�B�^�������_���ݒ�
	 *    : �z�����׃e�[�u���փZ���G�f�B�^�������_����ݒ肷��
	 *   @author TT nishigawa
	 *
	 ************************************************************************************/
	public void setHaigoMeisaiER(){
		try{
			//------------------ Jtable�֒��ԃG�f�B�^�[�������_���[��o�^  ----------------------
			DefaultTableColumnModel columnModel = (DefaultTableColumnModel)HaigoMeisai.getColumnModel();
			TableColumn column = null;

			//�H���I��
			column = HaigoMeisai.getColumnModel().getColumn(0);
			column.setCellEditor(HaigoMeisaiCellEditor0);
			column.setCellRenderer(HaigoMeisaiCellRenderer0);

			//�H����
			column = HaigoMeisai.getColumnModel().getColumn(1);
			column.setCellEditor(HaigoMeisaiCellEditor1);
			column.setCellRenderer(HaigoMeisaiCellRenderer1);

			//�����I��
			column = HaigoMeisai.getColumnModel().getColumn(2);
			column.setCellEditor(HaigoMeisaiCellEditor2);
			column.setCellRenderer(HaigoMeisaiCellRenderer2);

			//����CD
			column = HaigoMeisai.getColumnModel().getColumn(3);
			column.setCellEditor(HaigoMeisaiCellEditor3);
			column.setCellRenderer(HaigoMeisaiCellRenderer3);

			//������
			column = HaigoMeisai.getColumnModel().getColumn(4);
			column.setCellEditor(HaigoMeisaiCellEditor4);
			column.setCellRenderer(HaigoMeisaiCellRenderer4);

			//�P��
			column = HaigoMeisai.getColumnModel().getColumn(5);
			column.setCellEditor(HaigoMeisaiCellEditor5);
			column.setCellRenderer(HaigoMeisaiCellRenderer5);

			//����
			column = HaigoMeisai.getColumnModel().getColumn(6);
			column.setCellEditor(HaigoMeisaiCellEditor6);
			column.setCellRenderer(HaigoMeisaiCellRenderer6);

			//���ܗL��
			column = HaigoMeisai.getColumnModel().getColumn(7);
			column.setCellEditor(HaigoMeisaiCellEditor7);
			column.setCellRenderer(HaigoMeisaiCellRenderer7);

		}catch(Exception e){

			e.printStackTrace();

		}finally{

		}
	}

	/************************************************************************************
	 *
	 *   ���얾�׃e�[�u���@�ʃG�f�B�^�������_���F�ݒ�
	 *    : ���얾�׃e�[�u���֗ʍs�̃Z���G�f�B�^�������_����ǉ�����
	 *   @author TT nishigawa
	 *   @param row : �ǉ��s
	 *   @param flg : �����sor��s
	 *   @param pk_KoteiCd  : �H��CD
	 *   @param pk_KoteiSeq : �H��SEQ
	 *
	 ************************************************************************************/
	public void addListShisakuRowER_color(int row, int col, int color){
		try{
				//�����_���擾
				MiddleCellRenderer MiddleCellRenderer = (MiddleCellRenderer)aryListShisakuCellRenderer.get(col);
				TextFieldCellRenderer dr = (TextFieldCellRenderer)MiddleCellRenderer.getTableCellRenderer(row);

//add start -------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.33,34

				//�F�ύX
				//dr.setColor(new Color(color));

				//����SEQ�擾
				int intShisakuSeq = 0;
				MiddleCellEditor deleteMc = (MiddleCellEditor)ListHeader.getCellEditor(0, col);
				DefaultCellEditor deleteDc = (DefaultCellEditor)deleteMc.getTableCellEditor(0);
				CheckboxBase CheckboxBase = (CheckboxBase)deleteDc.getComponent();
				intShisakuSeq = Integer.parseInt(CheckboxBase.getPk1());

				//��L�[���ڎ擾
				boolean chk = DataCtrl.getInstance().getTrialTblData().checkShisakuIraiKakuteiFg(intShisakuSeq);

				//�ҏW�\�̏ꍇ�F��������
				if(chk){
					//�F�ύX
					dr.setColor(new Color(color));
				}
				//�ҏW�s�̏ꍇ�F��������
				else{

				}
//add end   -------------------------------------------------------------------------------


		}catch(Exception e){
			e.printStackTrace();

		}finally{
			//�e�X�g�\��
			//System.out.println(row+" , "+col+" , "+color);
		}
	}

	/************************************************************************************
	 *
	 *   ���얾�׃e�[�u���@�ʃG�f�B�^�������_���s�ǉ�
	 *    : ���얾�׃e�[�u���֗ʍs�̃Z���G�f�B�^�������_����ǉ�����
	 *   @author TT nishigawa
	 *   @param row : �ǉ��s
	 *   @param flg : �����sor��s
	 *   @param pk_KoteiCd  : �H��CD
	 *   @param pk_KoteiSeq : �H��SEQ
	 *
	 ************************************************************************************/
	public void addListShisakuRowER(int col, int row, int flg){
		try{
			//�ʃG�f�B�^�������_���s�ǉ�
//			for(int i=0; i<ListMeisai.getColumnCount(); i++){

				//�G�f�B�^�������_���z���蒆�ԃG�f�B�^�[�������_���[�擾
				MiddleCellEditor MiddleCellEditor = (MiddleCellEditor)aryListShisakuCellEditor.get(col);
				MiddleCellRenderer MiddleCellRenderer = (MiddleCellRenderer)aryListShisakuCellRenderer.get(col);

				//�R���|�[�l���g����
				TextboxBase comp;
				if(flg == 0){

					//TextField�i���l�j�R���|�[�l���g����
					comp = new NumelicTextbox();

					//���[�h�ҏW
					if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
							JwsConstManager.JWS_COMPONENT_0031, DataCtrl.getInstance().getParamData().getStrMode())){
						((NumelicTextbox)comp).setEditable(false);
					}else{
						//2012/10/22 QP@20505_No.1 TT S.SHIMA MOD Start
						//2011/06/13 QP@10181_No.29 TT T.Satoh Change Start -------------------------
						//if(row < ListMeisai.getRowCount()-8-1){
						//if(row < ListMeisai.getRowCount()-9-1){
						if(row < ListMeisai.getRowCount()-10-1){
						//2011/06/13 QP@10181_No.29 TT T.Satoh Change End ---------------------------
						//2012/10/22 QP@20505_No.1 TT S.SHIMA MOD End
							comp.addMouseListener(new colorChange(ListMeisai,1));
						}
					}

				}else{

					//TextField�i���͕s�j�R���|�[�l���g����
					comp = new NumelicTextbox();
					comp.setEditable(false);
					comp.setBackground(Color.WHITE);
				}

//add start -------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.33,34

				//�F�ύX
				//rendComp.setColor(new Color(-1));

				//����SEQ�擾
				int intShisakuSeq = 0;
				MiddleCellEditor deleteMc = (MiddleCellEditor)ListHeader.getCellEditor(0, col);
				DefaultCellEditor deleteDc = (DefaultCellEditor)deleteMc.getTableCellEditor(0);
				CheckboxBase CheckboxBase = (CheckboxBase)deleteDc.getComponent();
				intShisakuSeq = Integer.parseInt(CheckboxBase.getPk1());

				//��L�[���ڎ擾
				boolean chk = DataCtrl.getInstance().getTrialTblData().checkShisakuIraiKakuteiFg(intShisakuSeq);

				//�ҏW�\�̏ꍇ�F��������
				if(chk){
					comp.setBackground(Color.WHITE);
				}
				//�ҏW�s�̏ꍇ�F�R���|�[�l���g�̑���s��
				else{
					comp.setEnabled(false);
					comp.setBackground(JwsConstManager.JWS_DISABLE_COLOR);
				}
//add end   -------------------------------------------------------------------------------

				//�G�f�B�^�[�������_���[����
				TextFieldCellEditor editComp = new TextFieldCellEditor(comp);
				TextFieldCellRenderer rendComp = new TextFieldCellRenderer(comp);

				//���ԃG�f�B�^�[�������_���[�֓o�^
				MiddleCellEditor.addEditorAt(row, editComp);
				MiddleCellRenderer.add(row, rendComp);
				//�G�f�B�^�������_���z��֐ݒ�
				aryListShisakuCellEditor.set(col, MiddleCellEditor);
				aryListShisakuCellRenderer.set(col, MiddleCellRenderer);
//			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{

		}
	}

	/************************************************************************************
	 *
	 *   ���얾�׃e�[�u���@�G�f�B�^�������_����폜
	 *    : ���얾�׃e�[�u���̃Z���G�f�B�^�������_������폜����
	 *   @author TT nishigawa
	 *   @param  col : �폜��
	 *
	 ************************************************************************************/
	public void removeListShisakuColER(int col){
		try{
			//�G�f�B�^�������_����폜
			aryListShisakuCellEditor.remove(col);
			aryListShisakuCellRenderer.remove(col);
		}catch(Exception e){
			e.printStackTrace();
		}finally{

		}
	}

	/************************************************************************************
	 *
	 *   ���얾�׃e�[�u���@�G�f�B�^�������_�������
	 *    : ���얾�׃e�[�u���̃Z���G�f�B�^�������_�������ւ���
	 *   @author TT nishigawa
	 *   @param  col_moto : ���֌���
	 *   @param  col_saki : ���֐��
	 *
	 ************************************************************************************/
	public void changeListShisakuColER(int col_moto, int col_saki){
		try{
			//���֌��G�f�B�^�������_����擾
			MiddleCellEditor mc_moto = (MiddleCellEditor)aryListShisakuCellEditor.get(col_moto);
			MiddleCellRenderer mr_moto = (MiddleCellRenderer)aryListShisakuCellRenderer.get(col_moto);

			//���֐�G�f�B�^�������_����擾
			MiddleCellEditor mc_saki = (MiddleCellEditor)aryListShisakuCellEditor.get(col_saki);
			MiddleCellRenderer mr_saki = (MiddleCellRenderer)aryListShisakuCellRenderer.get(col_saki);

			//�G�f�B�^�����
			aryListShisakuCellEditor.set(col_moto, mc_saki);
			aryListShisakuCellEditor.set(col_saki, mc_moto);

			//�����_�������
			aryListShisakuCellRenderer.set(col_moto, mr_saki);
			aryListShisakuCellRenderer.set(col_saki, mr_moto);

		}catch(Exception e){
			e.printStackTrace();
		}finally{

		}
	}

	/************************************************************************************
	 *
	 *   ���얾�׃e�[�u���@�G�f�B�^�������_����ǉ�
	 *    : ���얾�׃e�[�u���֗�̃Z���G�f�B�^�������_����ǉ�����
	 *   @author TT nishigawa
	 *   @param col : �ǉ���
	 *
	 ************************************************************************************/
	public void addListShisakuColER(int col){
		try{
			//���ԃG�f�B�^�[�������_���[����
			MiddleCellEditor MiddleCellEditor = new MiddleCellEditor(ListMeisai);
			MiddleCellRenderer MiddleCellRenderer = new MiddleCellRenderer();

			//�ʃG�f�B�^�������_����ǉ�
			for(int i=0; i<HaigoMeisai.getRowCount(); i++){

				//�H���s���ǂ����̔��f���s��
				int flg = 0;

				MiddleCellEditor chkKotei_mc = (MiddleCellEditor)HaigoMeisai.getCellEditor(i, 2);
				DefaultCellEditor chkKotei_tc = (DefaultCellEditor)chkKotei_mc.getTableCellEditor(i);

				if(((JComponent)chkKotei_tc.getComponent()) instanceof CheckboxBase){
					//�����s
					flg = 0;
				}else{
					// ADD start 20120914 QP@20505 No.1
					int kotei_Cnt = DataCtrl.getInstance().getTrialTblData().getIntMaxKotei();
					// ADD end 20120914 QP@20505 No.1

					//�H���s
					flg = 1;

					//���v�d��d�ʍs�̏ꍇ
					//2011/06/13 QP@10181_No.29 TT T.Satoh Change Start -------------------------
					//if(i == HaigoMeisai.getRowCount() - 7){
					if(i == HaigoMeisai.getRowCount() - 8){
					//2011/06/13 QP@10181_No.29 TT T.Satoh Change End ---------------------------
						flg = 0;
					}
					// ADD start 20120914 QP@20505 No.1
					//�H���d��d�ʍs�̏ꍇ
					else if(i < HaigoMeisai.getRowCount()-8 && i >= HaigoMeisai.getRowCount()-8-kotei_Cnt){
						flg = 0;
					}
					// ADD end 20120914 QP@20505 No.1
				}

				//�R���|�[�l���g����
				TextboxBase comp;

				//�����s�̏ꍇ
				if(flg == 0){

					//TextField�i���l�j�R���|�[�l���g����
					comp = new NumelicTextbox();
					comp.addMouseListener(new colorChange(ListMeisai,1));

				}
				//�H���s�̏ꍇ
				else{

					//TextField�i���͕s�j�R���|�[�l���g����
					comp = new NumelicTextbox();
					comp.setEditable(false);
					comp.setBackground(Color.WHITE);

				}
				//�G�f�B�^�[�������_���[����
				TextFieldCellEditor editComp = new TextFieldCellEditor(comp);
				TextFieldCellRenderer rendComp = new TextFieldCellRenderer(comp);
				//���ԃG�f�B�^�[�������_���[�֓o�^
				MiddleCellEditor.addEditorAt(i, editComp);
				MiddleCellRenderer.add(i, rendComp);
			}

			//�G�f�B�^�������_���z��֒ǉ�
			aryListShisakuCellEditor.add(col, MiddleCellEditor);
			aryListShisakuCellRenderer.add(col, MiddleCellRenderer);

		}catch(Exception e){
			e.printStackTrace();
		}finally{

		}
	}

	/************************************************************************************
	 *
	 *   ���얾�׃e�[�u���@�ʃG�f�B�^�������_���s�ړ�
	 *    : ���얾�׃e�[�u���֗ʍs�̃Z���G�f�B�^�������_�����ړ�����
	 *   @author TT nishigawa
	 *   @param row_moto : �ړ����@�s�ԍ�
	 *   @param row_saki : �ړ���@�s�ԍ�
	 *
	 ************************************************************************************/
	public void moveListShisakuRowER(int row_moto,int row_saki){
		//-------------------- �G�f�B�^�������_���ړ��ݒ�  ----------------------------------
		try{
			//�񐔃��[�v
			for(int i=0; i<ListMeisai.getColumnCount(); i++){
				//�G�f�B�^�������_���z���蒆�ԃG�f�B�^�[�������_���[�擾
				MiddleCellEditor MiddleCellEditor = (MiddleCellEditor)aryListShisakuCellEditor.get(i);
				MiddleCellRenderer MiddleCellRenderer = (MiddleCellRenderer)aryListShisakuCellRenderer.get(i);

				//�ړ����G�f�B�^�������_���擾
				MiddleCellEditor mc_moto = (MiddleCellEditor)ListMeisai.getCellEditor(row_moto,i);
				TableCellEditor dc_moto = mc_moto.getTableCellEditor(row_moto);
				MiddleCellRenderer mr_moto = (MiddleCellRenderer)ListMeisai.getCellRenderer(row_moto, i);
				TableCellRenderer cc_moto = mr_moto.getTableCellRenderer(row_moto);

				//���ԃG�f�B�^�[�������_���[���ړ������폜
				MiddleCellEditor.removeEditorAt(row_moto);
				MiddleCellRenderer.remove(row_moto);

				//���ԃG�f�B�^�[�������_���[�֓o�^
				MiddleCellEditor.addEditorAt(row_saki, dc_moto);
				MiddleCellRenderer.add(row_saki, cc_moto);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{

		}
	}

	/************************************************************************************
	 *
	 *   ���얾�׃e�[�u���@�ʃG�f�B�^�������_���s����
	 *    : ���얾�׃e�[�u���֗ʍs�̃Z���G�f�B�^�������_������ւ���
	 *   @author TT nishigawa
	 *   @param row_moto : ���֌��@�s�ԍ�
	 *   @param row_saki : ���֐�@�s�ԍ�
	 *
	 ************************************************************************************/
	public void changeListShisakuRowER(int row_moto,int row_saki){
		//-------------------- �G�f�B�^�������_�����֐ݒ�  ----------------------------------
		try{
			//�񐔃��[�v
			for(int i=0; i<ListMeisai.getColumnCount(); i++){
				//�G�f�B�^�������_���z���蒆�ԃG�f�B�^�[�������_���[�擾
				MiddleCellEditor MiddleCellEditor = (MiddleCellEditor)aryListShisakuCellEditor.get(i);
				MiddleCellRenderer MiddleCellRenderer = (MiddleCellRenderer)aryListShisakuCellRenderer.get(i);

				//���֌��G�f�B�^�������_���擾
				MiddleCellEditor mc_moto = (MiddleCellEditor)ListMeisai.getCellEditor(row_moto,i);
				TableCellEditor dc_moto = mc_moto.getTableCellEditor(row_moto);
				MiddleCellRenderer mr_moto = (MiddleCellRenderer)ListMeisai.getCellRenderer(row_moto, i);
				TableCellRenderer cc_moto = mr_moto.getTableCellRenderer(row_moto);

				//���֐�G�f�B�^�������_���擾
				MiddleCellEditor mc_saki = (MiddleCellEditor)ListMeisai.getCellEditor(row_saki,i);
				TableCellEditor dc_saki = mc_saki.getTableCellEditor(row_saki);
				MiddleCellRenderer mr_saki = (MiddleCellRenderer)ListMeisai.getCellRenderer(row_saki, i);
				TableCellRenderer cc_saki = mr_saki.getTableCellRenderer(row_saki);

				//���ԃG�f�B�^�[�������_���[�֓o�^
				MiddleCellEditor.setEditorAt(row_moto, dc_saki);
				MiddleCellEditor.setEditorAt(row_saki, dc_moto);
				MiddleCellRenderer.set(row_moto, cc_saki);
				MiddleCellRenderer.set(row_saki, cc_moto);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{

		}
	}

	/************************************************************************************
	 *
	 *   ���얾�׃e�[�u���@�ʃG�f�B�^�������_���s�폜
	 *    : ���얾�׃e�[�u���s�̃Z���G�f�B�^�������_�����폜����
	 *   @author TT nishigawa
	 *   @param row : �ړ����@�s�ԍ�
	 *
	 ************************************************************************************/
	public void delListShisakuRowER(int row){
		//-------------------- �G�f�B�^�������_���폜�ݒ�  ----------------------------------
		try{
			//�񐔃��[�v
			for(int i=0; i<ListMeisai.getColumnCount(); i++){
				//�G�f�B�^�������_���z���蒆�ԃG�f�B�^�[�������_���[�擾
				MiddleCellEditor MiddleCellEditor = (MiddleCellEditor)aryListShisakuCellEditor.get(i);
				MiddleCellRenderer MiddleCellRenderer = (MiddleCellRenderer)aryListShisakuCellRenderer.get(i);

				//�G�f�B�^�������_���폜
				MiddleCellEditor.removeEditorAt(row);
				MiddleCellRenderer.remove(row);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{

		}
	}

	/************************************************************************************
	 *
	 *   ���얾�׃e�[�u���@�ʃG�f�B�^�������_����ǉ�
	 *    : ���얾�׃e�[�u���֗ʗ�̃Z���G�f�B�^�������_����ǉ�����
	 *   @author TT nishigawa
	 *   @param Column : �ǉ���
	 *
	 ************************************************************************************/
	public void addListShisakuColumnER(int Column){
		try{
			//�G�f�B�^�������_���z��֒ǉ�
			aryListShisakuCellEditor.add(Column, new MiddleCellEditor(ListMeisai));
			aryListShisakuCellRenderer.add(Column, new MiddleCellRenderer());
		}catch(Exception e){
			e.printStackTrace();
		}finally{

		}
	}

	/************************************************************************************
	 *
	 *   ���얾�׃e�[�u���@�G�f�B�^�������_���ݒ�
	 *    : ���얾�׃e�[�u���փZ���G�f�B�^�������_����ݒ肷��
	 *   @author TT nishigawa
	 *
	 ************************************************************************************/
	public void setListShisakuER(){
		try{
			//�e�[�u���J�����擾
			DefaultTableColumnModel columnModel = (DefaultTableColumnModel)ListMeisai.getColumnModel();
			TableColumn column = null;
			for(int i = 0; i<ListMeisai.getColumnCount(); i++){
				//�G�f�B�^�������_���z����擾
				MiddleCellEditor MiddleCellEditor = (MiddleCellEditor)aryListShisakuCellEditor.get(i);
				MiddleCellRenderer MiddleCellRenderer = (MiddleCellRenderer)aryListShisakuCellRenderer.get(i);
				//�e�[�u���J�����֐ݒ�
				column = ListMeisai.getColumnModel().getColumn(i);
				column.setCellEditor(MiddleCellEditor);
				column.setCellRenderer(MiddleCellRenderer);
			}
		}catch(Exception e){
			e.printStackTrace();

		}finally{

		}
	}

	/************************************************************************************
	 *
	 *   �����`�F�b�N�C�x���g�N���X : �����`�F�b�N�{�b�N�X�������̏����i�����I���j
	 *   @author TT nishigawa
	 *
	 ************************************************************************************/
	class CheckGenryo implements ActionListener{
		public void actionPerformed(ActionEvent e){
			try{
				String event_name = e.getActionCommand();
				//�����`�F�b�N�{�b�N�X����
				if ( event_name == "genryoCheck") {
					//�R���|�[�l���g�擾
					CheckboxBase CheckboxBase = (CheckboxBase)e.getSource();
					String pk_KoteiCd = CheckboxBase.getPk1();
					String pk_KoteiSeq = CheckboxBase.getPk2();

					//�e�X�g�\��
//					System.out.println("�H��CD�F" + pk_KoteiCd);
//					System.out.println("�H��SEQ�F" + pk_KoteiSeq);

					//�I�������f�[�^�ǉ�
					DataCtrl.getInstance().getTrialTblData().SelectHaigoGenryo(
							Integer.parseInt(pk_KoteiCd), Integer.parseInt(pk_KoteiSeq));
				}
			}catch(ExceptionBase eb){


			}catch(Exception ex){
				ex.printStackTrace();

			}finally{

			}
		}
	}

	/************************************************************************************
	 *
	 *   �H���`�F�b�N�C�x���g�N���X : �H���`�F�b�N�{�b�N�X�������̏����i�H���I���j
	 *   @author TT nishigawa
	 *
	 ************************************************************************************/
	class CheckKotei implements ActionListener{
		public void actionPerformed(ActionEvent e){
			try{
				String event_name = e.getActionCommand();
				//�H���`�F�b�N�{�b�N�X����
				if ( event_name == "koteiCheck") {

					//�R���|�[�l���g�擾
					CheckboxBase CheckboxBase = (CheckboxBase)e.getSource();
					String pk_KoteiCd = CheckboxBase.getPk1();
					String pk_KoteiSeq = CheckboxBase.getPk2();
					String pk_KoteiNo = CheckboxBase.getPk3();
					String pk_KoteiNm = CheckboxBase.getPk4();
					String pk_KoteiZoku = CheckboxBase.getPk5();

					//�e�X�g�\��
//					System.out.println("�H��CD�F" + pk_KoteiCd);
//					System.out.println("�H��SEQ�F" + pk_KoteiSeq);
//					System.out.println("�H�����F" + pk_KoteiNo);
//					System.out.println("�H�����F" + pk_KoteiNm);
//					System.out.println("�H�������F" + pk_KoteiZoku);


					//�`�F�b�N�{�b�N�X���I�����ꂽ�ꍇ
					if(CheckboxBase.isSelected()){

						//���`�F�b�N�{�b�N�X������
						for(int i=0; i<HaigoMeisai.getRowCount(); i++){

							//�R���|�[�l���g�擾
							MiddleCellEditor mc = (MiddleCellEditor)HaigoMeisai.getCellEditor(i, 0);
							DefaultCellEditor tc = (DefaultCellEditor)mc.getTableCellEditor(i);

							//�H���`�F�b�N�{�b�N�X�̏ꍇ
							if(tc.getComponent() instanceof CheckboxBase){

								//�`�F�b�N�{�b�N�X�R���|�[�l���g�擾
								CheckboxBase getCheck = (CheckboxBase)tc.getComponent();

								//�H��CD�擾
								String get_KoteiCd = getCheck.getPk1();

								//�`�F�b�N���ꂽ���̂Ɠ���̍H��CD�̏ꍇ
								if(pk_KoteiCd.equals(get_KoteiCd)){

								}
								//�`�F�b�N���ꂽ���̂ƈقȂ鎎��SEQ�̏ꍇ
								else{

									//�`�F�b�N������
									HaigoMeisai.setValueAt(null, i, 0);

								}

							}
						}

					//�`�F�b�N�{�b�N�X���I���������ꂽ�ꍇ
					}else{


					}

				}

			}catch(Exception ex){
				ex.printStackTrace();
			}finally{

			}
		}
	}

	/************************************************************************************
	 *
	 *   ����I���`�F�b�N�C�x���g�N���X : ����I���`�F�b�N�{�b�N�X�������̏���
	 *   @author TT nishigawa
	 *
	 ************************************************************************************/
	class CheckShisaku implements ActionListener{
		public void actionPerformed(ActionEvent e){
			try{
				String event_name = e.getActionCommand();
				//�H���`�F�b�N�{�b�N�X����
				if ( event_name == "shisakuCheck") {

					//�R���|�[�l���g�擾
					CheckboxBase CheckboxBase = (CheckboxBase)e.getSource();

					//�L�[���ڎ擾
					String tyuuiNo = (String)ListHeader.getValueAt(1, ListHeader.getSelectedColumn());
					String pk_ShisakuSeq = CheckboxBase.getPk1();

					//�`�F�b�N�{�b�N�X���I�����ꂽ�ꍇ
					if(CheckboxBase.isSelected()){

						//���`�F�b�N�{�b�N�X������
						for(int i=0; i<ListHeader.getColumnCount(); i++){

							//�R���|�[�l���g�擾
							MiddleCellEditor mc = (MiddleCellEditor)ListHeader.getCellEditor(0, i);
							DefaultCellEditor tc = (DefaultCellEditor)mc.getTableCellEditor(0);
							CheckboxBase getCheck = (CheckboxBase)tc.getComponent();

							//����SEQ�擾
							String selectSeq = getCheck.getPk1();

							//�`�F�b�N���ꂽ���̂Ɠ���̎���SEQ�̏ꍇ
							if(pk_ShisakuSeq.equals(selectSeq)){

							}
							//�`�F�b�N���ꂽ���̂ƈقȂ鎎��SEQ�̏ꍇ
							else{

								//�`�F�b�N������
								ListHeader.setValueAt(null, 0, i);

							}

						}

					//�`�F�b�N�{�b�N�X���I���������ꂽ�ꍇ
					}else{


					}

					//-------------------------- �����H���\������  ----------------------------
					//�����H���p�l���擾
					ManufacturingPanel pb = DataCtrl.getInstance().getManufacturingSubDisp().getManufacturingPanel();
					CheckboxBase seizoCb = pb.getCheckbox();


					//�`�F�b�N�{�b�N�X���I�����ꂽ�ꍇ
					if(CheckboxBase.isSelected()){

						//����SEQ�Z�b�g
						pb.setShisakuSeq(Integer.parseInt(pk_ShisakuSeq));

						//���ӎ���No��null�łȂ��ꍇ
						if(tyuuiNo != null){

							//���ӎ���No���I������Ă���ꍇ
							if(!tyuuiNo.equals("")){
								//2011/04/19 QP@10181_No.31 TT T.Satoh Add Start -------------------------
								pb.getLabel()[1].setText("�H���ŁF" + tyuuiNo);
								//2011/04/19 QP@10181_No.31 TT T.Satoh Add End ---------------------------
								pb.setTyuuiNo(Integer.parseInt(tyuuiNo));

							//���ӎ���No���I������Ă��Ȃ��ꍇ
							}else{

								//2011/05/27 QP@10181_No.31 TT T.Satoh Add Start -------------------------
								//��ʕ\��
								pb.getLabel()[1].setText("<html>�H���ŁF<font color=\"red\"><b>���I���ł�</font>");
								//2011/05/27 QP@10181_No.31 TT T.Satoh Add End ---------------------------
								pb.setTyuuiNo(0);

							}

						//���ӎ���No��null�̏ꍇ
						}else{

							pb.setTyuuiNo(0);

						}

					//�`�F�b�N�{�b�N�X���I���������ꂽ�ꍇ
					}else{

						//����SEQ�A���ӎ���No�Z�b�g
						pb.setShisakuSeq(0);
						pb.setTyuuiNo(0);

						//2011/05/27 QP@10181_No.31 TT T.Satoh Add Start -------------------------
						//��ʕ\��
						pb.getLabel()[1].setText("<html>�H���ŁF<font color=\"red\"><b>���I���ł�</font>");
						//2011/05/27 QP@10181_No.31 TT T.Satoh Add End ---------------------------
					}


					//�����H���̑I���R���{�{�b�N�X�̑I��l���擾
					int selectCombo = pb.getCombo().getSelectedIndex();

					//�����H��/���ӎ����̏ꍇ
					if(selectCombo == 0){
						pb.dispSeizo();
					}

					//���상���̏ꍇ
					if(selectCombo == 1){
						pb.dispMemo();
					}

					//���@No�̏ꍇ
					if(selectCombo == 2){
						pb.dispSeiho();
					}
				}
			}catch(Exception ex){
				ex.printStackTrace();
			}finally{

			}
		}
	}

	/************************************************************************************
	 *
	 *   ���ӎ���No�I���C�x���g�N���X : ���ӎ���No�I�����̏���
	 *   @author TT nishigawa
	 *
	 ************************************************************************************/
	class selectTyuui implements ActionListener{
		public void actionPerformed(ActionEvent e){
			try{
				//--------------------- ����SEQ&���ӎ���No�擾 --------------------------
				ComboBase cb = (ComboBase)e.getSource();
				String strNo = (String)cb.getSelectedItem();
				int intSEQ = Integer.parseInt(cb.getPk1());

				//-------------------------- �I����ԃ`�F�b�N  -----------------------------
				//�����H���p�l���擾
				ManufacturingPanel pb = DataCtrl.getInstance().getManufacturingSubDisp().getManufacturingPanel();

				//���삪�I����Ԃɂ���ꍇ�ɏ���
				if(pb.getShisakuSeq() == intSEQ){

					//���ӎ���No�ݒ�
					if(strNo != null && strNo.length() > 0){
						//2011/04/19 QP@10181_No.31 TT T.Satoh Add Start -------------------------
						pb.getLabel()[1].setText("�H���ŁF" + strNo);
						//2011/04/19 QP@10181_No.31 TT T.Satoh Add End ---------------------------
						pb.setTyuuiNo(Integer.parseInt(strNo));
					}else{
						//2011/05/27 QP@10181_No.31 TT T.Satoh Add Start -------------------------
						pb.getLabel()[1].setText("<html>�H���ŁF<font color=\"red\"><b>���I���ł�</font>");
						//2011/05/27 QP@10181_No.31 TT T.Satoh Add End ---------------------------
						pb.setTyuuiNo(0);
					}

					//-------------------- �����H���\������  ----------------------------
					//�����H���̑I���R���{�{�b�N�X�̑I��l���擾
					int selectCombo = pb.getCombo().getSelectedIndex();

					//�����H��/���ӎ����̏ꍇ
					if(selectCombo == 0){
						pb.dispSeizo();
					}

				}
			}catch(Exception ex){
				ex.printStackTrace();

			}finally{

			}
		}
	}

	/************************************************************************************
	 *
	 *   �����w�b�_�[�e�[�u���@�t�H�[�J�X����
	 *    : �����w�b�_�[�e�[�u���̃t�H�[�J�X�������s��
	 *   @author TT nishigawa
	 *
	 ************************************************************************************/
	public void ListHeaderFocusControl() throws ExceptionBase {
		ExceptionBase ex = new ExceptionBase();
		try {
			ListHeader.addKeyListener(new KeyAdapter() {
				public void keyPressed(KeyEvent e) {

					//�ŏI�s�E�ŏI��
					boolean isLastRowSelect = ( ListHeader.isRowSelected(ListHeader.getRowCount() - 1));
					boolean isLastColumnSelect = ( ListHeader.isColumnSelected(ListHeader.getColumnCount() - 1));
					int RowSelect = ListHeader.getSelectedRow();
					int ColumnSelect = ListHeader.getSelectedColumn();

					//�������ړ�
					if ( e.getKeyCode() == KeyEvent.VK_ENTER ) {			//ENTER�L�[
						if (e.getModifiers() == KeyEvent.SHIFT_MASK) {
							//�ŏ��s�Ƀt�H�[�J�X��
							if ( RowSelect == 0 && ColumnSelect > 0 ) {

								//�I�����e�[�u���𖢑I���ɐݒ�
								TableCellEditor tce = ListHeader.getCellEditor();
								if(tce != null){
									ListHeader.getCellEditor().stopCellEditing();
								}
								ListHeader.clearSelection();

								//�I���e�[�u���փt�H�[�J�X
								int row = ListMeisai.getRowCount()-1;
								ListMeisai.requestFocus();
								ListMeisai.setRowSelectionInterval(row, row);
								ListMeisai.setColumnSelectionInterval(ColumnSelect-1, ColumnSelect-1);

								//�z���s�I��
								HaigoMeisai.setRowSelectionInterval(row, row);

								e.consume();
							}
							//�ŏ��s�E��Ƀt�H�[�J�X��
							else if(RowSelect == 0 && ColumnSelect == 0){

								//�I�����e�[�u���𖢑I���ɐݒ�
								TableCellEditor tce = ListHeader.getCellEditor();
								if(tce != null){
									ListHeader.getCellEditor().stopCellEditing();
								}
								ListHeader.clearSelection();

								//�I���e�[�u���փt�H�[�J�X
								int row = HaigoMeisai.getRowCount()-1;
								int col = HaigoMeisai.getColumnCount()-1;
								HaigoMeisai.requestFocus();
								HaigoMeisai.setRowSelectionInterval(row, row);
								HaigoMeisai.setColumnSelectionInterval(col, col);

								e.consume();
							}
						}else{
							//�ŏI�s�Ƀt�H�[�J�X��
							if ( isLastRowSelect ) {

								//�I�����e�[�u���𖢑I���ɐݒ�
								TableCellEditor tce = ListHeader.getCellEditor();
								if(tce != null){
									ListHeader.getCellEditor().stopCellEditing();
								}
								ListHeader.clearSelection();

								//�I���e�[�u���փt�H�[�J�X
								ListMeisai.requestFocus();
								ListMeisai.setRowSelectionInterval(0, 0);
								ListMeisai.setColumnSelectionInterval(ColumnSelect, ColumnSelect);

								//�z���s�I��
								HaigoMeisai.setRowSelectionInterval(0, 0);

								e.consume();
							}
						}
					}
					//�������ړ�
					if ( e.getKeyCode() == KeyEvent.VK_DOWN ) {			//ENTER�L�[

						//�ŏI�s�Ƀt�H�[�J�X��
						if ( isLastRowSelect ) {

							//�I�����e�[�u���𖢑I���ɐݒ�
							TableCellEditor tce = ListHeader.getCellEditor();
							if(tce != null){
								ListHeader.getCellEditor().stopCellEditing();
							}
							ListHeader.clearSelection();

							//�I���e�[�u���փt�H�[�J�X
							ListMeisai.requestFocus();
							ListMeisai.setRowSelectionInterval(0, 0);
							ListMeisai.setColumnSelectionInterval(ColumnSelect, ColumnSelect);

							//�z���s�I��
							HaigoMeisai.setRowSelectionInterval(0, 0);

							e.consume();
						}
					}

					//�E�����ړ�
					else if ( e.getKeyCode() == KeyEvent.VK_TAB) {		//TAB�L�[
						if (e.getModifiers() == KeyEvent.SHIFT_MASK) {

						}else{
							//�ŏI��Ƀt�H�[�J�X��
							if ( isLastRowSelect && isLastColumnSelect ) {
								//�I�����e�[�u���𖢑I���ɐݒ�
								TableCellEditor tce = ListHeader.getCellEditor();
								if(tce != null){
									ListHeader.getCellEditor().stopCellEditing();
								}
								ListHeader.clearSelection();
								//�I���e�[�u���փt�H�[�J�X
								HaigoMeisai.requestFocus();
								HaigoMeisai.setRowSelectionInterval(0, 0);
								HaigoMeisai.setColumnSelectionInterval(0, 0);
								e.consume();
							}
						}
					}
				}
			});
		} catch ( Exception e ) {
			e.printStackTrace();
			//�G���[�ݒ�
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("�����w�b�_�[�̃t�H�[�J�X���䏈�������s���܂���");
			this.ex.setStrErrShori("Trial1Table:ListHeaderFocusControl");
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;
		} finally {

		}
	}

	/************************************************************************************
	 *
	 *   �z�����׃e�[�u���@�t�H�[�J�X����
	 *    : �z�����׃e�[�u���̃t�H�[�J�X�������s��
	 *   @author TT nishigawa
	 *
	 ************************************************************************************/
	public void HaigoMeisaiFocusControl() throws ExceptionBase {
		ExceptionBase ex = new ExceptionBase();
		try {
			HaigoMeisai.addKeyListener(new KeyAdapter() {
				public void keyPressed(KeyEvent e) {

					//�ŏI�s�E�ŏI��
					boolean isLastRowSelect = ( HaigoMeisai.isRowSelected(HaigoMeisai.getRowCount() - 1));
					boolean isLastColumnSelect = ( HaigoMeisai.isColumnSelected(HaigoMeisai.getColumnCount() - 1));
					int RowSelect = HaigoMeisai.getSelectedRow();
					int ColumnSelect = HaigoMeisai.getSelectedColumn();
					//�������ړ�
					if ( e.getKeyCode() == KeyEvent.VK_ENTER) {			//ENTER�L�[
						if (e.getModifiers() == KeyEvent.SHIFT_MASK) {
							//�ŏI�s�Ƀt�H�[�J�X��
							if ( RowSelect == 0 && ColumnSelect == 0 ) {
								e.consume();
							}
						}else{
							//�ŏI�s�Ƀt�H�[�J�X��
							if ( isLastRowSelect && isLastColumnSelect ) {
								//�I�����e�[�u���𖢑I���ɐݒ�
								TableCellEditor tce = HaigoMeisai.getCellEditor();
								if(tce != null){
									HaigoMeisai.getCellEditor().stopCellEditing();
								}
								HaigoMeisai.clearSelection();

								//�I���e�[�u���փt�H�[�J�X
								ListHeader.requestFocus();
								ListHeader.setRowSelectionInterval(0, 0);
								ListHeader.setColumnSelectionInterval(0, 0);
								e.consume();
							}
						}
					}
					//�E�����ړ�
					else if ( e.getKeyCode() == KeyEvent.VK_TAB ) {		//TAB�L�[
						if (e.getModifiers() == KeyEvent.SHIFT_MASK) {
							//�ŏ���Ƀt�H�[�J�X��
							if ( ColumnSelect == 0 && RowSelect > 0 ) {
								//�I�����e�[�u���𖢑I���ɐݒ�
								TableCellEditor tce = HaigoMeisai.getCellEditor();
								if(tce != null){
									HaigoMeisai.getCellEditor().stopCellEditing();
								}
								HaigoMeisai.clearSelection();
								//�I���e�[�u���փt�H�[�J�X
								int col = ListMeisai.getColumnCount()-1;
								ListMeisai.requestFocus();
								ListMeisai.setRowSelectionInterval(RowSelect-1, RowSelect-1);
								ListMeisai.setColumnSelectionInterval(col, col);
								e.consume();
							}
							//�ŏ���E�s�Ƀt�H�[�J�X��
							else if ( ColumnSelect == 0 && RowSelect == 0 ) {
								e.consume();
							}

						}else{
							//�ŏI��Ƀt�H�[�J�X��
							if ( isLastColumnSelect ) {
								//�I�����e�[�u���𖢑I���ɐݒ�
								TableCellEditor tce = HaigoMeisai.getCellEditor();
								if(tce != null){
									HaigoMeisai.getCellEditor().stopCellEditing();
								}
								//HaigoMeisai.clearSelection();
								//�I���e�[�u���փt�H�[�J�X
								ListMeisai.requestFocus();
								ListMeisai.setRowSelectionInterval(RowSelect, RowSelect);
								ListMeisai.setColumnSelectionInterval(0, 0);
								e.consume();
							}
						}
					}
					//�E�����ړ�
					else if ( e.getKeyCode() == KeyEvent.VK_RIGHT ) {

						//�ŏI��Ƀt�H�[�J�X��
						if ( isLastColumnSelect ) {
							//�I�����e�[�u���𖢑I���ɐݒ�
							TableCellEditor tce = HaigoMeisai.getCellEditor();
							if(tce != null){
								HaigoMeisai.getCellEditor().stopCellEditing();
							}
							//HaigoMeisai.clearSelection();

							//�I���e�[�u���փt�H�[�J�X
							ListMeisai.requestFocus();
							ListMeisai.setRowSelectionInterval(RowSelect, RowSelect);
							ListMeisai.setColumnSelectionInterval(0, 0);
							e.consume();
						}
					}
				}
			});
		} catch ( Exception e ) {
			e.printStackTrace();
			//�G���[�ݒ�
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("HaigoMeisai�̃t�H�[�J�X���䏈�������s���܂���");
			this.ex.setStrErrShori("Trial1Table:HaigoMeisaiFocusControl");
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;
		} finally {

		}
	}

	/************************************************************************************
	 *
	 *   ���얾�׃e�[�u���@�t�H�[�J�X����
	 *    : ���얾�׃e�[�u���̃t�H�[�J�X�������s��
	 *   @author TT nishigawa
	 *
	 ************************************************************************************/
	public void ListMeisaiFocusControl() throws ExceptionBase {
		ExceptionBase ex = new ExceptionBase();
		try {
			ListMeisai.addKeyListener(new KeyAdapter() {
				public void keyPressed(java.awt.event.KeyEvent e) {

					//�ŏI�s�E�ŏI��
					boolean isLastRowSelect = ( ListMeisai.isRowSelected(ListMeisai.getRowCount() - 1));
					boolean isLastColumnSelect = ( ListMeisai.isColumnSelected(ListMeisai.getColumnCount() - 1));
					int RowSelect = ListMeisai.getSelectedRow();
					int ColumnSelect = ListMeisai.getSelectedColumn();

					//�������ړ�(Enter)
					if ( e.getKeyCode() == KeyEvent.VK_ENTER) {
						if (e.getModifiers() == KeyEvent.SHIFT_MASK) {
							//�ŏ��s�Ƀt�H�[�J�X��
							if ( RowSelect == 0 ) {

								//�z���s�I������
								HaigoMeisai.clearSelection();

								//�I�����e�[�u���𖢑I���ɐݒ�
								TableCellEditor tce = ListMeisai.getCellEditor();
								if(tce != null){
									ListMeisai.getCellEditor().stopCellEditing();
								}
								ListMeisai.clearSelection();

								//�I���e�[�u���փt�H�[�J�X
								int row = ListHeader.getRowCount()-1;
								ListHeader.requestFocus();
								ListHeader.setRowSelectionInterval(row, row);
								ListHeader.setColumnSelectionInterval(ColumnSelect, ColumnSelect);
								e.consume();
							}
						}else{

							//�ŏI�s�Ƀt�H�[�J�X��
							if ( isLastRowSelect && !isLastColumnSelect) {

								//�z���s�I������
								HaigoMeisai.clearSelection();

								//�I�����e�[�u���𖢑I���ɐݒ�
								TableCellEditor tce = ListMeisai.getCellEditor();
								if(tce != null){
									ListMeisai.getCellEditor().stopCellEditing();
								}
								ListMeisai.clearSelection();

								//�I���e�[�u���փt�H�[�J�X
								ListHeader.requestFocus();
								ListHeader.setRowSelectionInterval(0, 0);
								ListHeader.setColumnSelectionInterval(ColumnSelect+1, ColumnSelect+1);
								e.consume();
							}
							//�ŏI�s�E��Ƀt�H�[�J�X��
							else if ( isLastRowSelect && isLastColumnSelect ) {
								e.consume();
							}
						}
					}

					//������ړ�
					if ( e.getKeyCode() == KeyEvent.VK_UP) {

						//�ŏ��s�Ƀt�H�[�J�X��
						if ( RowSelect == 0 ) {

							//�z���s�I������
							HaigoMeisai.clearSelection();

							//�I�����e�[�u���𖢑I���ɐݒ�
							TableCellEditor tce = ListMeisai.getCellEditor();
							if(tce != null){
								ListMeisai.getCellEditor().stopCellEditing();
							}
							ListMeisai.clearSelection();

							//�I���e�[�u���փt�H�[�J�X
							int row = ListHeader.getRowCount()-1;
							ListHeader.requestFocus();
							ListHeader.setRowSelectionInterval(row, row);
							ListHeader.setColumnSelectionInterval(ColumnSelect, ColumnSelect);
							e.consume();
						}
					}

					//�E�����ړ��iTab�j
					else if ( e.getKeyCode() == KeyEvent.VK_TAB ) {
						if (e.getModifiers() == KeyEvent.SHIFT_MASK) {

							//�ŏ���Ƀt�H�[�J�X��
							if ( ColumnSelect == 0 ) {

								//�I�����e�[�u���𖢑I���ɐݒ�
								TableCellEditor tce = ListMeisai.getCellEditor();
								if(tce != null){
									ListMeisai.getCellEditor().stopCellEditing();
								}
								ListMeisai.clearSelection();

								//�I���e�[�u���փt�H�[�J�X
								HaigoMeisai.requestFocus();
								HaigoMeisai.setRowSelectionInterval(RowSelect, RowSelect);
								int col = HaigoMeisai.getColumnCount() - 1;
								HaigoMeisai.setColumnSelectionInterval(col, col);
								e.consume();
							}
	                    } else {

	                    	//�ŏI��Ƀt�H�[�J�X��
							if ( isLastColumnSelect && !isLastRowSelect ) {

								//�I�����e�[�u���𖢑I���ɐݒ�
								TableCellEditor tce = ListMeisai.getCellEditor();
								if(tce != null){
									ListMeisai.getCellEditor().stopCellEditing();
								}
								ListMeisai.clearSelection();

								//�I���e�[�u���փt�H�[�J�X
								HaigoMeisai.requestFocus();
								HaigoMeisai.setRowSelectionInterval(RowSelect+1, RowSelect+1);
								HaigoMeisai.setColumnSelectionInterval(0, 0);

								//�I�����e�[�u���𖢑I���ɐݒ�
								ListMeisai.clearSelection();
								e.consume();
							}
							//�ŏI�s�E��Ƀt�H�[�J�X��
							else if ( isLastRowSelect && isLastColumnSelect ) {
								e.consume();
							}
	                    }
					}

					//�������ړ�(left)
					else if ( e.getKeyCode() == KeyEvent.VK_LEFT) {

						//�ŏI��Ƀt�H�[�J�X��
						if ( ColumnSelect == 0 ) {

							//�I�����e�[�u���𖢑I���ɐݒ�
							TableCellEditor tce = ListMeisai.getCellEditor();
							if(tce != null){
								ListMeisai.getCellEditor().stopCellEditing();
							}
							ListMeisai.clearSelection();

							//�I���e�[�u���փt�H�[�J�X
							HaigoMeisai.requestFocus();
							HaigoMeisai.setRowSelectionInterval(RowSelect, RowSelect);
							int col = HaigoMeisai.getColumnCount() - 1;
							HaigoMeisai.setColumnSelectionInterval(col, col);
							e.consume();
						}
					}
				}
			});
		} catch ( Exception e ) {

			e.printStackTrace();

			//�G���[�ݒ�
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("HaigoMeisai�̃t�H�[�J�X���䏈�������s���܂���");
			this.ex.setStrErrShori("Trial1Table:HaigoMeisaiFocusControl");
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}
	}

	/************************************************************************************
	 *
	 *   ���e�����f�[�^���R���{�{�b�N�X�֐ݒ�
	 *   @author TT katayama
	 *   @param comb : �ݒ�ΏۃR���{�{�b�N�X
	 *   @param literalData : �ݒ�Ώۃ��e�����f�[�^
	 *   @param strLiteralCd : �\���Ώۃ��e�����R�[�h
	 *   @param intType : �\���p���e�����R�[�h�̃^�C�v(0:�R�[�h, 1:���l)
	 *
	 ************************************************************************************/
	private void setLiteralCmb(JComboBox comb, LiteralData literalData, String strLiteralCd, int intType) {
		try{
			int i;
			String literalCd = "";
			String literalNm = "";
			Object viewLiteralNm = "";

			//�R���{�{�b�N�X�̑S���ڂ̍폜
			comb.removeAllItems();

			//�^�C�v��0��1�̏ꍇ�A�󍀖ڂ�ǉ�����
			comb.addItem("");

			//�\�����ɉ����ăR���{�{�b�N�X�ɒl��ǉ�
			for ( i=0; i<literalData.getAryLiteralCd().size(); i++ ) {

				//���e�����R�[�h
				literalCd = literalData.getAryLiteralCd().get(i).toString();

				//���e������
				literalNm = literalData.getAryLiteralNm().get(i).toString();

				//�R���{�{�b�N�X�֒ǉ�
				comb.addItem(literalNm);

				//�\�����ڂ̌��o
				if ( intType == 0 ) {

					//�R�[�h��
					if ( literalCd.equals(strLiteralCd) ) {
						viewLiteralNm = literalNm;
					}

				} else {

					//���l��
					if ( literalNm.equals(strLiteralCd) ) {
						viewLiteralNm = strLiteralCd;
					}

				}
			}
			//�\�����ڂ̐ݒ�
			if ( viewLiteralNm != "" ) {

				//�\�����ڂ����݂���ꍇ
				comb.setSelectedItem(viewLiteralNm);

			} else {
				if ( intType == 0 ) {

					//�R�[�h�̏ꍇ�A�󍀖ڂ�\��
					comb.setSelectedIndex(0);
				} else {

					//���l�̏ꍇ�A���l�����ڂɒǉ����A�\������
					comb.addItem(strLiteralCd);
					comb.setSelectedItem(strLiteralCd);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{

		}
	}

	/************************************************************************************
	 *
	 *   �F�w��T�u��ʁ@�N��&�F�ύX�C�x���g�N���X
	 *   @author TT nishigawa
	 *
	 ************************************************************************************/
	class colorChange extends MouseAdapter{

		//*********************************************************************************
		//  �����o�i�[
		//*********************************************************************************
		TableBase TableBase;
		colorChangeEv ccev;
		int tableFlg = 0; //�z������ or ���얾��(0=�z������,1=���얾��)

		//*********************************************************************************
		//  �R���X�g���N�^
		//*********************************************************************************
		public colorChange(TableBase tb,int tbFg){
			TableBase = tb;
			tableFlg = tbFg;
			//�`�F���W���X�i�[
			ccev = new colorChangeEv(TableBase);
		}

		//*********************************************************************************
		//  �}�E�X�N���b�N
		//*********************************************************************************
		public void mouseClicked(final MouseEvent me) {

			//�_�u���N���b�N
			if(me.getClickCount()==2) {

				//���X�i�[���폜
				colorSubDisp.getColorPanel().getColorChooser().getSelectionModel().removeChangeListener(ccev);

				//���X�i�[��o�^
				colorSubDisp.getColorPanel().getColorChooser().getSelectionModel().addChangeListener(ccev);

				//�F�w��T�u��� �{�^�� �N���b�N���̏���
				colorSubDisp.setVisible(true);
			}
		}

		//*********************************************************************************
		//  �F�I���N���X
		//*********************************************************************************
		class colorChangeEv implements ChangeListener{

			//------------------------------------------------------------------------------
			//  �����o�i�[
			//------------------------------------------------------------------------------
			TableBase TableBase;

			//------------------------------------------------------------------------------
			//  �R���X�g���N�^
			//------------------------------------------------------------------------------
			public colorChangeEv(TableBase tb){
				TableBase = tb;
			}

			//------------------------------------------------------------------------------
			//  �F�I����
			//------------------------------------------------------------------------------
			public void stateChanged(ChangeEvent e) {
				try{
					//�F�����擾
					Color selectColor = colorSubDisp.getColorPanel().getColorChooser().getColor();

					System.out.println("�F�w��F"+selectColor.getRGB());

					//�e�[�u�����擾
					int row = TableBase.getSelectedRow();
					int col = TableBase.getSelectedColumn();

					//�L�[���ڎ擾
					int intShisakuSeq = -1;
					int intKoteiCd    = -1;
					int intKoteiSeq   = -1;

					//�H��CD & �H��SEQ�@�擾
					MiddleCellEditor mceHaigoKey = (MiddleCellEditor)HaigoMeisai.getCellEditor(row, 2);
					DefaultCellEditor dceHaigoKey = (DefaultCellEditor)mceHaigoKey.getTableCellEditor(row);
					CheckboxBase chkHaigoKey = (CheckboxBase)dceHaigoKey.getComponent();
					intKoteiCd  = Integer.parseInt(chkHaigoKey.getPk1());
					intKoteiSeq = Integer.parseInt(chkHaigoKey.getPk2());

					//----------------------------- �z�����ׂ̏ꍇ -------------------------------
					if(tableFlg == 0){
						for(int i=4; i<TableBase.getColumnCount(); i++){

							//�����_���擾
							MiddleCellRenderer mr = (MiddleCellRenderer)TableBase.getCellRenderer(row, i);
							TextFieldCellRenderer dr = (TextFieldCellRenderer)mr.getTableCellRenderer(row);

							//�F�ύX
							dr.setColor(new Color(selectColor.getRGB()));
						}

						//�f�[�^�}��
						DataCtrl.getInstance().getTrialTblData().UpdHaigouGenryoColor(
								intKoteiCd,
								intKoteiSeq,
								DataCtrl.getInstance().getTrialTblData().checkNullString(Integer.toString(selectColor.getRGB())),
								DataCtrl.getInstance().getUserMstData().getDciUserid()
							);

					//----------------------------- ���얾�ׂ̏ꍇ -------------------------------
					}else{

						//�����_���擾
						MiddleCellRenderer mr = (MiddleCellRenderer)TableBase.getCellRenderer(row, col);
						TextFieldCellRenderer dr = (TextFieldCellRenderer)mr.getTableCellRenderer(row);

						//�F�ύX
						dr.setColor(new Color(selectColor.getRGB()));

						//����SEQ�@�擾
						MiddleCellEditor mceHeaderKey = (MiddleCellEditor)ListHeader.getCellEditor(0, col);
						DefaultCellEditor dceHeaderKey = (DefaultCellEditor)mceHeaderKey.getTableCellEditor(0);
						CheckboxBase chkHeaderKey = (CheckboxBase)dceHeaderKey.getComponent();
						intShisakuSeq  = Integer.parseInt(chkHeaderKey.getPk1());

						//�f�[�^�}��
						DataCtrl.getInstance().getTrialTblData().UpdShisakuListColor(
								intShisakuSeq,
								intKoteiCd,
								intKoteiSeq,
								DataCtrl.getInstance().getTrialTblData().checkNullString(Integer.toString(selectColor.getRGB())),
								DataCtrl.getInstance().getUserMstData().getDciUserid()
							);

					}

					//�I�������N���A
//					TableCellEditor hmEditor = TableBase.getCellEditor();
//					if(hmEditor != null){
//						hmEditor.stopCellEditing();
//					}
					TableBase.clearSelection();

					//�I�����̍Đݒ�
					TableBase.requestFocus();
					TableBase.setRowSelectionInterval(row, row);
					TableBase.setColumnSelectionInterval(col, col);

					//��ʂ��\��
					colorSubDisp.setVisible(false);

					//���X�i�[���폜
					//colorSubDisp.getColorPanel().getColorChooser().getSelectionModel().removeChangeListener(this);

					colorSubDisp = new ColorSubDisp("�F�w����");

					//�e�X�g�\��
					//System.out.println(row);

				}catch(Exception ex){

				}finally{
					//�e�X�g�\��
					//DataCtrl.getInstance().getTrialTblData().dispHaigo();
					//DataCtrl.getInstance().getTrialTblData().dispProtoList();
				}

			}
		}
	}

	/************************************************************************************
	 *
	 *   ���앪�̓f�[�^�m�F�T�u��ʁ@�N���C�x���g�N���X
	 *   @author TT nishigawa
	 *
	 ************************************************************************************/
	class analysisDisp extends MouseAdapter{
		public void mouseClicked(final MouseEvent me) {
			if(me.getClickCount()==2) {
				try{
					//���앪�̓f�[�^�m�F�T�u��� �{�^�� �N���b�N���̏���
					analysinSubDisp.setVisible(true);
					analysinSubDisp.getAnalysisPanel().init();

				}catch(ExceptionBase eb){

					DataCtrl.getInstance().getMessageCtrl().PrintErrMessage(eb);

				}catch(Exception e){

					e.printStackTrace();

				}

			}
		}
	}

	/************************************************************************************
	 *
	 *   �����R�[�h�R�s�[�N���X
	 *   @author TT nishigawa
	 *
	 ************************************************************************************/
	class CopyGenryo extends KeyAdapter {
		//�L�[������
		public void keyPressed( KeyEvent e ){
			try{

				int keyCode = e.getKeyCode();

				//�N���b�v�{�[�h�f�[�^�擾
				if(keyCode == KeyEvent.VK_F3){

					//�R�s�[�f�[�^�擾
					String cpData = DataCtrl.getInstance().getClipboardData().getStrClipboad();

					if(cpData != null && cpData.length() > 0){

						String genryoCd = cpData.split("\n")[0];
						String kaishaCd = cpData.split("\n")[1];
						String kojoCd = cpData.split("\n")[2];

						//�z���f�[�^�擾
						ArrayList ary = DataCtrl.getInstance().getTrialTblData().SearchHaigoData(0);
						int haigoNum = DataCtrl.getInstance().getTrialTblData().getIntMaxKotei();

						//�I���s�擾
						int selRow = HaigoMeisai.getSelectedRow();
						int selCol = HaigoMeisai.getSelectedColumn();

						//�ҏW�\�s�@���@�����R�[�h�̂ݏ���
						//2011/06/13 QP@10181_No.29 TT T.Satoh Change Start -------------------------
						//if(selRow < HaigoMeisai.getRowCount()-haigoNum-8 && selCol == 3){
						if(selRow < HaigoMeisai.getRowCount()-haigoNum-9 && selCol == 3){
						//2011/06/13 QP@10181_No.29 TT T.Satoh Change End ---------------------------

							//�L�[�R���|�[�l���g�擾
							MiddleCellEditor mce2 = (MiddleCellEditor)HaigoMeisai.getCellEditor(selRow, 2);
							DefaultCellEditor tfce2 = (DefaultCellEditor)mce2.getTableCellEditor(selRow);

							//�����s�̏ꍇ
							if(tfce2.getComponent() instanceof CheckboxBase){

								//�����R�[�h�R�s�[�t���O��on
								blnGenryoCopy = true;

								//��L�[�擾
								CheckboxBase cb = (CheckboxBase)tfce2.getComponent();
								int koteiCd  = Integer.parseInt(cb.getPk1());
								int koteiSeq = Integer.parseInt(cb.getPk2());

//add start -------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.33,34
								//�H���L�[���ڎ擾
								int intShisakuSeq = 0;
								boolean hanshu_chk = DataCtrl.getInstance().getTrialTblData().checkListHenshuOkFg(intShisakuSeq, koteiCd, koteiSeq);

								//�ҏW�\�̏ꍇ�F��������
								if(hanshu_chk){

								}
								//�ҏW�s�̏ꍇ�F�������Ȃ�
								else{
									blnGenryoCopy = false;
									return;
								}
//add end   -------------------------------------------------------------------------------


								//------------------------------- �\���l�ύX -----------------------------------
								HaigoMeisai.setValueAt(genryoCd, selRow, 3);

								//------------------------------- �f�[�^�}�� ------------------------------------
								//��Б}��
								DataCtrl.getInstance().getTrialTblData().UpdHaigoKaishaCd(
										koteiCd,
										koteiSeq,
										DataCtrl.getInstance().getTrialTblData().checkNullInt(kaishaCd),
										DataCtrl.getInstance().getUserMstData().getDciUserid()
									);
								//�H��}��
								DataCtrl.getInstance().getTrialTblData().UpdHaigoKojoCd(
										koteiCd,
										koteiSeq,
										DataCtrl.getInstance().getTrialTblData().checkNullInt(kojoCd),
										DataCtrl.getInstance().getUserMstData().getDciUserid()
									);
							}
						}
					}
				}
			}catch(Exception ex){
				ex.printStackTrace();
			}finally{
				//�e�X�g�\��
				//DataCtrl.getInstance().getTrialTblData().dispHaigo();

			}

		}
	}

	/************************************************************************************
	 *
	 *   �e�[�u���R�s�[�N���X
	 *   @author TT nishigawa
	 *
	 ************************************************************************************/
	class CopyCell extends KeyAdapter {
		public void keyPressed( KeyEvent e ){
			try{

				int keyCode = e.getKeyCode();

				//�z���f�[�^�擾
				ArrayList retuData = DataCtrl.getInstance().getTrialTblData().SearchHaigoData(0);

				//�ő�H�����擾
				int maxKotei = DataCtrl.getInstance().getTrialTblData().getIntMaxKotei();
				//2011/06/13 QP@10181_No.29 TT T.Satoh Change Start -------------------------
				//int maxRow = ListMeisai.getRowCount()-maxKotei-8;
				int maxRow = ListMeisai.getRowCount()-maxKotei-9;
				//2011/06/13 QP@10181_No.29 TT T.Satoh Change End ---------------------------

				//-------------------------- �e�[�u���Z���f�[�^�R�s�[  -------------------------------
				if(keyCode == KeyEvent.VK_F1){
					//�I���Z���s��擾
					int[] rows = ListMeisai.getSelectedRows();
					int[] columns = ListMeisai.getSelectedColumns();

					//�R�s�[�z�񏉊���
					ArrayList aryCopy = new ArrayList();

					//�I���s���[�v
					for(int i=0; i<rows.length; i++){

						//�s�z�񏉊���
						ArrayList aryRows = new ArrayList();

						//�I��񃋁[�v
						for(int j=0; j<columns.length; j++){

							//�s��ԍ��擾
							int row = rows[i];
							int col = columns[j];

							//�l�擾
							if(row < maxRow){
								Object value = ListMeisai.getValueAt(row, col);

								//�s�z��֒ǉ�
								aryRows.add(value);
							}
						}

						//�R�s�[�z��֒ǉ�
						aryCopy.add(aryRows);
					}

					//�N���b�v�{�[�h�֕ۑ�
					DataCtrl.getInstance().getClipboardData().setAryClipboad(aryCopy);

					//�����I��
					e.consume();
				}

				//------------------------ �e�[�u���Z���f�[�^�y�[�X�g  -------------------------------
				if(keyCode == KeyEvent.VK_F3){

//add start -------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.7
					//�m�F���b�Z�[�W
					boolean kakuninFg = false;
					//�Čv�Z
					boolean exeCopyFg = false;
					//�߰�Đ掎��SEQ�ۑ��p
					ArrayList aryPstSeq = new ArrayList();
//add end   -------------------------------------------------------------------------------

					//�N���b�v�{�[�h���擾
					ArrayList aryCopy = DataCtrl.getInstance().getClipboardData().getAryClipboad();

					//�I���Z���s��擾
					int row = ListMeisai.getSelectedRows()[0];
					int col = ListMeisai.getSelectedColumns()[0];

					//�ő�e�[�u���s��擾
					int row_max = ListMeisai.getRowCount();
					int col_max = ListMeisai.getColumnCount();

					int sel_max_row = -1;
					int sel_max_col = -1;

					//�e�[�u���փf�[�^�ݒ�
					for(int i=0; i<aryCopy.size(); i++){

						//��f�[�^�擾
						ArrayList selCols = (ArrayList)aryCopy.get(i);
						for(int j=0; j<selCols.size(); j++){

							//�e�[�u���s����w��
							int set_row = row + i;
							int set_col = col + j;

								//�l�̎擾
								String value = null;
								if(selCols.get(j) != null){
									value = selCols.get(j).toString();
								}

								//�l�ݒ�
								if(set_row < row_max && set_col < col_max){

//mod start -------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.33,34
								//����SEQ�擾
								int intShisakuSeq = 0;
								MiddleCellEditor deleteMc = (MiddleCellEditor)ListHeader.getCellEditor(0, set_col);
								DefaultCellEditor deleteDc = (DefaultCellEditor)deleteMc.getTableCellEditor(0);
								CheckboxBase CheckboxBase = (CheckboxBase)deleteDc.getComponent();
								intShisakuSeq = Integer.parseInt(CheckboxBase.getPk1());

								//��L�[���ڎ擾
								boolean chk = DataCtrl.getInstance().getTrialTblData().checkShisakuIraiKakuteiFg(intShisakuSeq);

								//�ҏW�\�̏ꍇ�F��������
								if(chk){

	//add start -------------------------------------------------------------------------------
	//QP@00412_�V�T�N�C�b�N���� No.7
									if(kakuninFg){

									}
									else{

										//�Čv�Z�̉e���͈͊m�F
						    			if(AutoCopyKeisanCheck(ListMeisai.getSelectedColumn())){

						    			}
						    			else{
						    				//�_�C�A���O�R���|�[�l���g�ݒ�
											JOptionPane jp = new JOptionPane();

							    			//�m�F�_�C�A���O�\��
											int option = jp.showConfirmDialog(
													jp.getRootPane(),
													JwsConstManager.JWS_ERROR_0046
													, "�m�F���b�Z�[�W"
													,JOptionPane.YES_NO_OPTION
													,JOptionPane.PLAIN_MESSAGE
												);

											//�u�͂��v����
										    if (option == JOptionPane.YES_OPTION){
										    	exeCopyFg = true;

										    //�u�������v����
										    }else if (option == JOptionPane.NO_OPTION){
										    	exeCopyFg = false;
										    }
										    kakuninFg = true;
						    			}
									}
	//add end   -------------------------------------------------------------------------------



									//�L�[���ڕ\���i�z���f�[�^�j
									int pk_koteiCd = 0;
									int pk_koteiSeq = 0;
									MiddleCellEditor mc_haigo = (MiddleCellEditor)HaigoMeisai.getCellEditor(set_row, 2);
									DefaultCellEditor tc_haigo = (DefaultCellEditor)mc_haigo.getTableCellEditor(set_row);
									if(((JComponent)tc_haigo.getComponent()) instanceof CheckboxBase){
										CheckboxBase chk_haigo = (CheckboxBase)tc_haigo.getComponent();
										pk_koteiCd = Integer.parseInt(chk_haigo.getPk1());
										pk_koteiSeq = Integer.parseInt(chk_haigo.getPk2());
									}

									//�L�[���ڎ擾�i�����f�[�^�j
									int pk_sisakuSeq = 0;
									MiddleCellEditor mc_shisaku = (MiddleCellEditor)ListHeader.getCellEditor(0, set_col);
									DefaultCellEditor tc_shisaku = (DefaultCellEditor)mc_shisaku.getTableCellEditor(0);
									CheckboxBase chk_shisaku = (CheckboxBase)tc_shisaku.getComponent();
									pk_sisakuSeq = Integer.parseInt(chk_shisaku.getPk1());

    //add start -------------------------------------------------------------------------------
    //QP@00412_�V�T�N�C�b�N���� No.7
									//�߰�Đ�̎���SEQ�ۑ�
									aryPstSeq.add(Integer.toString(pk_sisakuSeq));
    //add end   -------------------------------------------------------------------------------

									//�H���sor�v�Z�s�̏ꍇ�̓y�[�X�g���Ȃ�
									if(pk_koteiCd > 0){

										//�e�[�u���\���l�ݒ�
										ListMeisai.setValueAt(value, set_row, set_col);

										//���샊�X�g�f�[�^�ݒ�
										if(value == null || value.length() == 0){
											DataCtrl.getInstance().getTrialTblData().UpdShisakuListRyo(
													pk_sisakuSeq, pk_koteiCd, pk_koteiSeq, null);
										}else{
											DataCtrl.getInstance().getTrialTblData().UpdShisakuListRyo(
													pk_sisakuSeq, pk_koteiCd, pk_koteiSeq, new BigDecimal(value));
										}

	//add start -------------------------------------------------------------------------------
	//QP@00412_�V�T�N�C�b�N���� No.7
										if(exeCopyFg){
											//�R�s�[��v�Z�t���O���ɐݒ肳��Ă���ꍇ�@���@���s�\�ȏꍇ
								    		if( JwsConstManager.JWS_COPY_0002 ){

								    			//�u���ʊJ���v�w��
					    						String strKakkoHiraki = JwsConstManager.JWS_COPY_0003;

					    						//�u���ʕ��v�w��
					    						String strKakkoTozi = JwsConstManager.JWS_COPY_0004;

								    			//�z���f�[�^�z��擾
								    			ArrayList aryShisaku = DataCtrl.getInstance().getTrialTblData().SearchShisakuRetuData(pk_sisakuSeq);

								    			//�T���v��NO�擾
//								    			String SampleNo = ((TrialData)aryShisaku.get(0)).getStrSampleNo();
//								    			String chkSampleNo = strKakkoHiraki + SampleNo + strKakkoTozi;

								    			//�T���v��SEQ�擾
								    			String SampleSeq = JwsConstManager.JWS_COPY_0005 + Integer.toString((((TrialData)aryShisaku.get(0)).getIntShisakuSeq()));

								    			//�T���v��NO�擾
								    			String SampleKeisan = ((TrialData)aryShisaku.get(0)).getStrKeisanSiki();
								    			if(SampleKeisan == null || SampleKeisan.length() ==0){
								    				SampleKeisan = SampleSeq;
								    			}

								    			//String SampleSeq = ((TrialData)aryShisaku.get(0))
								    			String chkSampleNo = strKakkoHiraki + SampleKeisan + strKakkoTozi;

								    			//����񃋁[�v
								    			for(int k=0; k<ListHeader.getColumnCount(); k++){

								    				//���g�̗�łȂ��ꍇ
								    				if( k != set_col ){

								    					//����SEQ�擾
							    						MiddleCellEditor mceHeaderKey1 = (MiddleCellEditor)ListHeader.getCellEditor(0, k);
														DefaultCellEditor dceHeaderKey1 = (DefaultCellEditor)mceHeaderKey1.getTableCellEditor(0);
														CheckboxBase chkHeaderKey1 = (CheckboxBase)dceHeaderKey1.getComponent();
														int roopShisakuSeq  = Integer.parseInt(chkHeaderKey1.getPk1());

//								    					//�T���v��No�擾
//								    					String roopSampleNo =
//								    						DataCtrl.getInstance().getTrialTblData().toString(ListHeader.getValueAt(3, k), "");
								    					//�v�Z���擾
								    					String roopSampleNo =
								    						DataCtrl.getInstance().getTrialTblData().SearchShisakuKeisanSiki(roopShisakuSeq);

								    					//����̃T���v��No���܂܂��ꍇ
								    					if(roopSampleNo.indexOf(chkSampleNo) >= 0){

															//�������Z�˗�����Ă���f�[�^�͏������Ȃ�
															TrialData chkTrialData = (TrialData)DataCtrl.getInstance().getTrialTblData().SearchShisakuRetuData(roopShisakuSeq).get(0);
															if(chkTrialData.getFlg_init() == 1){

															}else{

																//�R�s�[��v�Z���sFg
																boolean blnExec = true;

																//�߰�Đ�̎��삩�ǂ������f
//																for(int l=0; l<aryPstSeq.size(); l++){
//
//																	//����SEQ�擾
//																	String seq = (String) aryPstSeq.get(l);
//
//																	//���s�s�i�߰�Đ��D��j
//																	if(seq.equals(Integer.toString(roopShisakuSeq))){
//																		blnExec = false;
//																	}
//
//																}

																//���s�\�ȏꍇ�i�߰�Đ�̎����łȂ��ꍇ�j
																if(blnExec){

																	String strKekka = "";

																	if(value == null || value.length() == 0){

																	}
																	else{
																		//���g�̃T���v��No���ݒ肳��Ă��镔����z���ʂƂ���������
																		//String repSampleNo = "\\\\" + strKakkoHiraki + SampleNo + "\\\\" + strKakkoTozi;
																		String repSampleNo = strKakkoHiraki + SampleKeisan + strKakkoTozi;
																		//roopSampleNo = roopSampleNo.replaceAll( repSampleNo , value);
																		roopSampleNo = roopSampleNo.replaceAll( "\\Q" + chkSampleNo + "\\E" , value);

																		//�v�Z���ϊ�
																		String keisanSiki = DataCtrl.getInstance().getTrialTblData().changeKeisanLogic( roopSampleNo , 0 );

																		//�z���ʐݒ�
																		String keisan =
																			DataCtrl.getInstance().getTrialTblData().getKeisanShisakuSeq( keisanSiki , pk_koteiCd , pk_koteiSeq );

											    						//�v�Z���s
																		strKekka = DataCtrl.getInstance().getTrialTblData().execKeisan(keisan);

																		//��������
														    			if(strKekka != null && strKekka.length() > 0){

														    				//���֏���
														    				strKekka = ShosuAraiHulfUp(strKekka);

														    			}
																	}

													    			//�e�[�u���}��
																	ListMeisai.setValueAt(strKekka, set_row, k);

													    			//�f�[�^�}��
																	DataCtrl.getInstance().getTrialTblData().UpdShisakuListRyo(
																			roopShisakuSeq,
																			pk_koteiCd,
																			pk_koteiSeq,
																			DataCtrl.getInstance().getTrialTblData().checkNullDecimal(strKekka)
																		);

																	//�H�����v�̎Z�o
																	koteiSum(k);

																}
									    					}
														}
								    				}
								    			}
								    		}
										}
	//add end   -------------------------------------------------------------------------------
									}
								}
								//�ҏW�s�̏ꍇ�F��������
								else{

								}
//mod end   -------------------------------------------------------------------------------

								//�ŏI�y�[�X�g�l�̎擾
								if(sel_max_row < set_row){
									if(set_row < maxRow){
										sel_max_row = set_row;
									}else{
										sel_max_row = maxRow-1;
									}
								}
								if(sel_max_col < set_col){
									sel_max_col = set_col;
								}
							}
						}
					}

					//�I��F�̐ݒ�
					if(sel_max_row > -1 && sel_max_col > -1){
						ListMeisai.changeSelection(sel_max_row, sel_max_col, false, true);
					}

					//�H�����v�v�Z
					if(aryCopy.size() > 0){
						ArrayList selCols = (ArrayList)aryCopy.get(0);
						for(int j=0; j<selCols.size(); j++){

							//�e�[�u���s����w��
							int set_col = col + j;
							if(set_col < ListMeisai.getColumnCount()){
								koteiSum(set_col);
							}
						}
						//�����v�Z
						AutoKeisan();
					}

					//������v�Z
					DispGenryohi();

//2011/05/11 QP@10181_No.42_49_72 TT T.Nishigawa Add Start -------------------------

					//���i��d�A�[�U�ʁA�����[�U�ʁA�����[�U�ʁ@�v�Z����
					ZidouKeisan2();

//2011/05/11 QP@10181_No.42_49_72 TT T.Nishigawa Add End -------------------------

					//�����I��
					e.consume();
				}
			}catch(Exception ex){
				ex.printStackTrace();

			}finally{
				//DataCtrl.getInstance().getTrialTblData().dispProtoList();

			}
		}
	}

	/************************************************************************************
	 *
	 *   �����v�Z
	 *    :  �����v�Z���s��
	 *   @author TT nishigawa
	 *
	 ************************************************************************************/
	public void AutoKeisan() throws ExceptionBase{
		try{
			ArrayList aryRetu = DataCtrl.getInstance().getTrialTblData().SearchShisakuRetuData(0);
			TrialData td = (TrialData)aryRetu.get(0);

			//�����v�Z���`�F�b�N����Ă���ꍇ�̂ݏ���
			if(td.getIntZidoKei() == 1){

				//�z���f�[�^�擾
				ArrayList HaigoData = DataCtrl.getInstance().getTrialTblData().SearchHaigoData(0);

				//�ő�H�����擾
				int koteiNum = DataCtrl.getInstance().getTrialTblData().getIntMaxKotei();

				//����񐔕����[�v
				for(int i=0; i<ListHeader.getColumnCount(); i++){

					//�����w��擾
					//���e�����f�[�^�擾
					ArrayList aryLiteralCd = DataCtrl.getInstance().getLiteralDataShosu().getAryLiteralCd();
					ArrayList aryLiteralNm = DataCtrl.getInstance().getLiteralDataShosu().getAryLiteralNm();

					//�I���f�[�^�擾
					String SelShosu = DataCtrl.getInstance().getTrialTblData().SearchShisakuhinData().getStrShosu();
					int shosu = 0;

					//�I�����e�����R�[�h�̖��̎擾
					for(int k=0; k<aryLiteralCd.size(); k++){

						//�R�[�h�Ɩ��̎擾
						String strLiteralCd = (String)aryLiteralCd.get(k);
						String strLiteralNm = (String)aryLiteralNm.get(k);
						if(SelShosu != null && SelShosu.length() > 0){
							if(SelShosu.equals(strLiteralCd)){
								try{

									//�����w��擾
									shosu = Integer.parseInt(strLiteralNm);

								}catch(Exception e){

									//��O����0��}��
									shosu = 0;
								}

							}

						}
					}

					//����SEQ�擾
					MiddleCellEditor mceSeq = (MiddleCellEditor)ListHeader.getCellEditor(0, i);
					DefaultCellEditor dceSeq = (DefaultCellEditor)mceSeq.getTableCellEditor(0);
					CheckboxBase chkSeq = (CheckboxBase)dceSeq.getComponent();
					int intSeq  = Integer.parseInt(chkSeq.getPk1());

					// MOD start 20120914 QP@20505 No.1
					//�z���\(����\�@)�s��
					//2011/06/13 QP@10181_No.29 TT T.Satoh Change Start -------------------------
					//int maxRow = ListMeisai.getRowCount()-7;
//					int maxRow = ListMeisai.getRowCount()-8;
					//2011/06/13 QP@10181_No.29 TT T.Satoh Change End ---------------------------
					int kotei_num = DataCtrl.getInstance().getTrialTblData().getIntMaxKotei();
					int maxRow = ListMeisai.getRowCount()-8-kotei_num;
					// MOD end 20120914 QP@20505 No.1

					//------------------------ �v�Z�K�v���� -----------------------------------
					//�@���v�d��(g)
					BigDecimal goleiZyuryo = new BigDecimal("0.00");
					Object objZyuryo = ListMeisai.getValueAt(maxRow-1, i);
					if(objZyuryo != null){
						if(objZyuryo.toString().length()>0){
							goleiZyuryo = new BigDecimal(objZyuryo.toString());
							//goleiZyuryo = goleiZyuryo.divide(new BigDecimal("1000"), BigDecimal.ROUND_HALF_UP);
						}
					}
					//�A���ܗL���v��
					BigDecimal goleiGanyu = new BigDecimal("0.00");

					//�B�|�_���v��
					BigDecimal goleiSakusan = new BigDecimal("0.00");

					//�C�H�����v��
					BigDecimal goleiShokuen = new BigDecimal("0.00");

					//�D���_���v��
					BigDecimal goleiSosan = new BigDecimal("0.00");

// ADD start 20121002 QP@20505 No.24
					//�l�r�f���v��
					BigDecimal goleiMsg = new BigDecimal("0.00");
// ADD end 20121002 QP@20505 No.24

					//----------------------- �z���`�H���ʎ擾 ---------------------------------
					//2011/06/13 QP@10181_No.29 TT T.Satoh Change Start -------------------------
					//for(int j=0; j<HaigoMeisai.getRowCount()-koteiNum-8;j++){
					for(int j=0; j<HaigoMeisai.getRowCount()-koteiNum-9;j++){
					//2011/06/13 QP@10181_No.29 TT T.Satoh Change End ---------------------------

						//�L�[�R���|�[�l���g�擾
						MiddleCellEditor mceHaigo = (MiddleCellEditor)HaigoMeisai.getCellEditor(j, 2);
						DefaultCellEditor dceHaigo = (DefaultCellEditor)mceHaigo.getTableCellEditor(j);

						//�����s�̏ꍇ
						if(dceHaigo.getComponent() instanceof CheckboxBase){

							//�L�[���ڎ擾
							CheckboxBase cbKotei = (CheckboxBase)dceHaigo.getComponent();
							int koteiCd = Integer.parseInt(cbKotei.getPk1());
							int koteiSeq = Integer.parseInt(cbKotei.getPk2());

							//�z���ʎ擾
							String ryoVal = (String)ListMeisai.getValueAt(j, i);
							BigDecimal bdRyo = new BigDecimal("0.0000");
							if(ryoVal != null && ryoVal.length() > 0){
								bdRyo = new BigDecimal(ryoVal);
							}

							//�z���`�H���ʂ��v�Z�{���Z
							for(int k=0; k<HaigoData.size(); k++){
								
								//�z���f�[�^�擾
								MixedData selMixedData = (MixedData)HaigoData.get(k);
								if(selMixedData.getIntKoteiCd() == koteiCd && selMixedData.getIntKoteiSeq() == koteiSeq){

									//�A���ܗL���v�ʉ��Z
									if(selMixedData.getDciGanyuritu() != null){
										goleiGanyu = goleiGanyu.add(bdRyo.multiply(selMixedData.getDciGanyuritu().multiply(new BigDecimal("0.01"))));
									}
									//�B�|�_���v�ʉ��Z
									if(selMixedData.getDciSakusan() != null){
										goleiSakusan = goleiSakusan.add(bdRyo.multiply(selMixedData.getDciSakusan()));
									}
									//�C�H�����v�ʉ��Z
									if(selMixedData.getDciShokuen() != null){
										goleiShokuen = goleiShokuen.add(bdRyo.multiply(selMixedData.getDciShokuen()));
									}
									//�D���_���v�ʉ��Z
									if(selMixedData.getDciSosan() != null){
										goleiSosan = goleiSosan.add(bdRyo.multiply(selMixedData.getDciSosan()));
									}
					// ADD start 20121002 QP@20505 No.24
									//�l�r�f���v�ʉ��Z
									if(selMixedData.getDciMsg() != null){
										goleiMsg = goleiMsg.add(bdRyo.multiply(selMixedData.getDciMsg()));
									}
					// ADD end 20121002 QP@20505 No.24
								}
							}
						}
					}

					//---------------------------------- �����s -------------------------------
					//2011/05/17 QP@10181_No.71 TT T.Satoh Add Start -------------------------
					//maxRow+=1;
// MOD start 20121017 QP@20505 No.1
					//maxRow+=2;
					maxRow+=2 + koteiNum;
// MOD start 20121017 QP@20505 No.1
					//2011/05/17 QP@10181_No.71 TT T.Satoh Add End --------------------------

					//------------------------------- ���_�v�Z���� -----------------------------
					//�B���_���v��/�@���v�d��
					BigDecimal sosan = new BigDecimal("0.00");
					if(goleiSosan.intValue() > 0 && goleiZyuryo.intValue() > 0){
						sosan = goleiSosan.divide(goleiZyuryo, 2, BigDecimal.ROUND_HALF_UP);
					}


					//�f�[�^�}��
					//table.setValueAt(sosan, 1, i);
					DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuSousan(
		    				intSeq,
		    				DataCtrl.getInstance().getTrialTblData().checkNullDecimal(sosan.toString()),
		    				DataCtrl.getInstance().getUserMstData().getDciUserid()
		    			);

					//�z���\(����\�@)�֑}��
					ListMeisai.setValueAt(sosan, maxRow+=1, i);


					//------------------------------ �H���v�Z���� -------------------------------
					//�C�H�����v��/�@���v�d��
					BigDecimal shokuen = new BigDecimal("0.00");
					if(goleiShokuen.intValue() > 0 && goleiZyuryo.intValue() > 0){
						shokuen = goleiShokuen.divide(goleiZyuryo, 2, BigDecimal.ROUND_HALF_UP);
					}

					//�f�[�^�}��
					//table.setValueAt(shokuen, 2, i);
					DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuSyokuen(
		    				intSeq,
		    				DataCtrl.getInstance().getTrialTblData().checkNullDecimal(shokuen.toString()),
		    				DataCtrl.getInstance().getUserMstData().getDciUserid()
		    			);

					//�z���\(����\�@)�֑}��
					ListMeisai.setValueAt(shokuen, maxRow+=1, i);

// ADD start 20121002 QP@20505 No.24
					//------------------------------ �l�r�f�v�Z���� -------------------------------
					//�l�r�f���v��/�@���v�d��
					BigDecimal msg = new BigDecimal("0.00");
					if(goleiMsg.intValue() > 0 && goleiZyuryo.intValue() > 0){
						msg = goleiMsg.divide(goleiZyuryo, 2, BigDecimal.ROUND_HALF_UP);
					}
					//�f�[�^�}��
					//table.setValueAt(shokuen, 2, i);
					DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuMsg(
		    				intSeq,
		    				DataCtrl.getInstance().getTrialTblData().checkNullDecimal(msg.toString()),
		    				DataCtrl.getInstance().getUserMstData().getDciUserid()
		    			);

//					//�z���\(����\�@)�֑}��
//					ListMeisai.setValueAt(msg, maxRow+=1, i);
// ADD end 20121002 QP@20505 No.24

					//--------------------------- �������_�x�v�Z����-----------------------------
					//�D���_���v��/�i�@���v�ʁ[�A���ܗL���v�ʁj
					BigDecimal sui_sando = new BigDecimal("0.00");
					if(goleiSosan.intValue() > 0 && (goleiZyuryo.subtract(goleiGanyu)).intValue() > 0){
						sui_sando = goleiSosan.divide((goleiZyuryo.subtract(goleiGanyu)), 2, BigDecimal.ROUND_HALF_UP);
					}

					//�f�[�^�}��
					//table.setValueAt(sui_sando, 3, i);
					DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuSuisoSando(
		    				intSeq,
		    				DataCtrl.getInstance().getTrialTblData().checkNullDecimal(sui_sando.toString()),
		    				DataCtrl.getInstance().getUserMstData().getDciUserid()
		    			);

					//�z���\(����\�@)�֑}��
					ListMeisai.setValueAt(sui_sando, maxRow+=1, i);


					//--------------------------- �������H���v�Z����-----------------------------
					//�C�H�����v��/�i�@���v�ʁ[�A���ܗL���v�ʁj
					BigDecimal sui_shokuen = new BigDecimal("0.00");
					if(goleiShokuen.intValue() > 0 && (goleiZyuryo.subtract(goleiGanyu)).intValue() > 0){
						sui_shokuen = goleiShokuen.divide((goleiZyuryo.subtract(goleiGanyu)), 2, BigDecimal.ROUND_HALF_UP);
					}

					//�f�[�^�}��
					//table.setValueAt(sui_shokuen, 4, i);
					DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuSuisoSyokuen(
		    				intSeq,
		    				DataCtrl.getInstance().getTrialTblData().checkNullDecimal(sui_shokuen.toString()),
		    				DataCtrl.getInstance().getUserMstData().getDciUserid()
		    			);

					//�z���\(����\�@)�֑}��
					ListMeisai.setValueAt(sui_shokuen, maxRow+=1, i);


					//-------------------------- �������|�_�v�Z����------------------------------
					//�B�|�_���v��/�i�@���v�ʁ[�A���ܗL���v�ʁj
					BigDecimal sui_sakusan = new BigDecimal("0.00");
					if(goleiSakusan.intValue() > 0 && (goleiZyuryo.subtract(goleiGanyu)).intValue() > 0){
						sui_sakusan = goleiSakusan.divide((goleiZyuryo.subtract(goleiGanyu)), 2, BigDecimal.ROUND_HALF_UP);
					}

					//�f�[�^�}��
					//table.setValueAt(sui_sakusan, 5, i);
					DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuSuisoSakusan(
		    				intSeq,
		    				DataCtrl.getInstance().getTrialTblData().checkNullDecimal(sui_sakusan.toString()),
		    				DataCtrl.getInstance().getUserMstData().getDciUserid()
		    			);

					//�z���\(����\�@)�֑}��
					ListMeisai.setValueAt(sui_sakusan, maxRow+=1, i);

// ADD start 20121003 QP@20505 No.24
					//--------------------------- �������l�r�f�v�Z����-----------------------------
					//�l�r�f���v��/�i�@���v�ʁ[�A���ܗL���v�ʁj
					BigDecimal sui_msg = new BigDecimal("0.00");
					if(goleiMsg.intValue() > 0 && (goleiZyuryo.subtract(goleiGanyu)).intValue() > 0){
						sui_msg = goleiMsg.divide((goleiZyuryo.subtract(goleiGanyu)), 2, BigDecimal.ROUND_HALF_UP);
					}

					//�f�[�^�}��
					DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuSuisoMSG(
		    				intSeq,
		    				DataCtrl.getInstance().getTrialTblData().checkNullDecimal(sui_msg.toString()),
		    				DataCtrl.getInstance().getUserMstData().getDciUserid()
		    			);
					//�z���\�֑}��
//					ListMeisai.setValueAt(sui_msg, maxRow+=1, i);

					//----------------------------- �����|�_�Z�x�i���j-----------------------------
					//�������|�_�|�������l�r�f�~(0.5791�~���g�|1.9104)/ 187.13�~60
					double dblJsnKoteiValue1 = checkNumericDouble(DataCtrl.getInstance().getLiteralDataJikkoSakusanNodo().getAryBiko().get(0));
					double dblJsnKoteiValue2 = checkNumericDouble(DataCtrl.getInstance().getLiteralDataJikkoSakusanNodo().getAryBiko().get(1));
					double dblJsnKoteiValue3 = checkNumericDouble(DataCtrl.getInstance().getLiteralDataJikkoSakusanNodo().getAryBiko().get(2));
					double dblJsnKoteiValue4 = checkNumericDouble(DataCtrl.getInstance().getLiteralDataJikkoSakusanNodo().getAryBiko().get(3));
					String strPh = "";
					BigDecimal ph = new BigDecimal("0.00");
					BigDecimal dci_sui_sakusan = new BigDecimal(sui_sakusan.toString());
					BigDecimal kotei1 = new BigDecimal(String.valueOf(dblJsnKoteiValue1)); // 0.5791
					BigDecimal kotei2 = new BigDecimal(String.valueOf(dblJsnKoteiValue2)); // 1.9104
					BigDecimal kotei3 = new BigDecimal(String.valueOf(dblJsnKoteiValue3)); // 187.13
					BigDecimal kotei4 = new BigDecimal(String.valueOf(dblJsnKoteiValue4)); // 60
					ArrayList aryTrialData = DataCtrl.getInstance().getTrialTblData().SearchShisakuRetuData(0);
					int cntList = aryTrialData.size();
					TrialData TrialData = new TrialData();
					for (int k=0; k<cntList; k++){
						TrialData = ((TrialData)aryTrialData.get(k)); //�����f�[�^
						if (TrialData.getIntShisakuSeq() == intSeq){
							strPh = TrialData.getStrPh();
						}
					}
					if (strPh == null){
					}else{
						if (strPh.length() > 0){
							//2013/04/01 MOD Start
//							ph = new BigDecimal(strPh);
							ph = new BigDecimal(strPh.replaceAll(" ","").replaceAll("�@",""));
							//2013/04/01 MOD End
						}
					}
					// �v�Z
					BigDecimal jsn = new BigDecimal("0.00");
					jsn = ph.multiply(kotei1); // 0.5791�~ph
					jsn = jsn.subtract(kotei2); // jsn�|1.9104
					if (sui_msg.doubleValue() > 0 && jsn.doubleValue() > 0){
						jsn = jsn.multiply(sui_msg); // jsn�~������MSG
						jsn = jsn.divide(kotei3, 4, BigDecimal.ROUND_HALF_UP); // jsn��187.13
						jsn = jsn.multiply(kotei4); // jsn�~60
						jsn = dci_sui_sakusan.subtract(jsn); // �������|�_�|jsn
						if (jsn.doubleValue() > 0){
							jsn = jsn.setScale(2, BigDecimal.ROUND_HALF_UP);
						}else{
							// �Ō�̌��Z�Ń}�C�i�X�l�ɂȂ�ꍇ
							jsn = new BigDecimal("0.00");
						}
					}else{
						// ����Z����O��0�l���������ꍇ
						jsn = new BigDecimal("0.00");
					}

					//�f�[�^�}��
					DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuJikkoSakusanNodo(
		    				intSeq,
		    				DataCtrl.getInstance().getTrialTblData().checkNullDecimal(jsn.toString()),
		    				DataCtrl.getInstance().getUserMstData().getDciUserid()
		    			);
// ADD end 20121003 QP@20505 No.24
				}
			}

		}catch(Exception e){
			e.printStackTrace();
			//�G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("����f�[�^��ʃp�l���̎����v�Z���������s���܂���");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		}finally{

		}
	}

	/************************************************************************************
	 *
	 *   �d��d�ʌv�Z
	 *    :  �d��d�ʂ̌v�Z���s��
	 *   @author TT shima
	 *
	 ************************************************************************************/
	public void ShiagariZyuryoKeisan(int column){
		try{
			
			//���[�h�ҏW
    		if(DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
					JwsConstManager.JWS_COMPONENT_0055, DataCtrl.getInstance().getParamData().getStrMode())){
    			
				int gokeiShiagari = ListMeisai.getRowCount()-8;
				int koteiNum = DataCtrl.getInstance().getTrialTblData().getIntMaxKotei();
				int koteiSum = gokeiShiagari - koteiNum;
				
				BigDecimal shiagariGokei = null;
				
				for(int j = 0; j < koteiNum ;j++){
					
					//�ʃf�[�^�擾
					String ryo = (String)ListMeisai.getValueAt(koteiSum+j, column);
					
					if(ryo != null && ryo.length() > 0){
					
						if(shiagariGokei instanceof BigDecimal){
						}else{
							shiagariGokei = new BigDecimal("0");
						}
						
						//�H�����v���Z
						shiagariGokei = shiagariGokei.add(new BigDecimal(ryo));
					}
				}
				
				//���v���o��
				ListMeisai.setValueAt(shiagariGokei, gokeiShiagari, column);
			
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}


	/************************************************************************************
	 *
	 * ���L���폜
	 * @throws ExceptionBase
	 *
	 ************************************************************************************/
	public String delMark(String strVal){

		String ret = strVal;

		try{

			if(ret != null && ret.length() > 0){

				//���L���폜
				if(ret.substring(0, 1).equals(JwsConstManager.JWS_MARK_0001) ||
						ret.substring(0, 1).equals(JwsConstManager.JWS_MARK_0002)){
					ret = ret.substring(1);
					if(ret.length() == 0){
						ret = null;
					}
				}
			}

		}catch(Exception e){

			e.printStackTrace();

		}finally{

		}

		return ret;
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

// ADD start 20121003 QP@20505 No.24
	/************************************************************************************
	 * ���l�`�F�b�N�iDouble�j
	 ************************************************************************************/
	public double checkNumericDouble(Object val){
		double ret = 0.0;
		//�l���󕶎��łȂ��ꍇ
		if(val != null){
			ret = Double.parseDouble(val.toString());
		}
		return ret;
	}
// ADD end 20121003 QP@20505 No.24

	/************************************************************************************
	 *
	 *   kCheck�Q�b�^�[
	 *   @author TT nishigawa
	 *   @return int[] : �I���H���z��
	 *
	 ************************************************************************************/
	public int[] getKCheck() {
		return kCheck;
	}

	/************************************************************************************
	 *
	 *   kCheck�Z�b�^�[
	 *   @author TT nishigawa
	 *   @param check : �I���H���z��
	 *
	 ************************************************************************************/
	public void setKCheck(int[] check) {
		kCheck = check;
	}

	/************************************************************************************
	 *
	 *   aryGenryoCheck�Q�b�^�[
	 *   @author TT nishigawa
	 *   @return ArrayList : �����I��z��
	 *
	 ************************************************************************************/
	public ArrayList getAryGenryoCheck() {
		return aryGenryoCheck;
	}

	/************************************************************************************
	 *
	 *   aryGenryoCheck�Z�b�^�[
	 *   @author TT nishigawa
	 *   @param aryGenryoCheck : �����I��z��
	 *
	 ************************************************************************************/
	public void setAryGenryoCheck(ArrayList aryGenryoCheck) {
		this.aryGenryoCheck = aryGenryoCheck;
	}

	/************************************************************************************
	 *
	 *   ListHeader�Q�b�^�[
	 *   @author TT nishigawa
	 *   @return TableBase : �����w�b�_�[�e�[�u��
	 *
	 ************************************************************************************/
	public TableBase getListHeader() {
		return ListHeader;
	}

	/************************************************************************************
	 *
	 *   ListHeader�Z�b�^�[
	 *   @author TT nishigawa
	 *   @param listHeader : �����w�b�_�[�e�[�u��
	 *
	 ************************************************************************************/
	public void setListHeader(TableBase listHeader) {
		ListHeader = listHeader;
	}

	/************************************************************************************
	 *
	 *   HaigoMeisai�Q�b�^�[
	 *   @author TT nishigawa
	 *   @return TableBase : �z�����׃e�[�u��
	 *
	 ************************************************************************************/
	public TableBase getHaigoMeisai() {
		return HaigoMeisai;
	}

	/************************************************************************************
	 *
	 *   HaigoMeisai�Z�b�^�[
	 *   @author TT nishigawa
	 *   @param haigoMeisai : �z�����׃e�[�u��
	 *
	 ************************************************************************************/
	public void setHaigoMeisai(TableBase haigoMeisai) {
		HaigoMeisai = haigoMeisai;
	}

	/************************************************************************************
	 *
	 *   AnalysinSubDisp�Q�b�^�[
	 *   @author TT nishigawa
	 *   @return AnalysinSubDisp : �������͒l�m�F���
	 *
	 ************************************************************************************/
	public AnalysinSubDisp getAnalysinSubDisp() {
		return analysinSubDisp;
	}

	/************************************************************************************
	 *
	 *   AnalysinSubDisp�Z�b�^�[
	 *   @author TT nishigawa
	 *   @param AnalysinSubDisp : �������͒l�m�F���
	 *
	 ************************************************************************************/
	public void setAnalysinSubDisp(AnalysinSubDisp analysinSubDisp) {
		this.analysinSubDisp = analysinSubDisp;
	}

	/************************************************************************************
	 *
	 *   ListMeisai�Q�b�^�[
	 *   @author TT nishigawa
	 *   @return TableBase : ���얾�׃e�[�u��
	 *
	 ************************************************************************************/
	public TableBase getListMeisai() {
		return ListMeisai;
	}

	/************************************************************************************
	 *
	 *   ListMeisai�Z�b�^�[
	 *   @author TT nishigawa
	 *   @param listMeisai : ���얾�׃e�[�u��
	 *
	 ************************************************************************************/
	public void setListMeisai(TableBase listMeisai) {
		ListMeisai = listMeisai;
	}

	//2011/04/20 QP@10181_No.41 TT T.Satoh Add Start -------------------------
	/************************************************************************************
	 *
	 *   scrollMain�Q�b�^�[
	 *   @author TT satoh
	 *   @return scrollMain :�@���얾�׃e�[�u���̃X�N���[���o�[
	 *
	 ************************************************************************************/
	public ScrollBase getScrollMain() {
		return scrollMain;
	}

	/************************************************************************************
	 *
	 *   scrollMain�Z�b�^�[
	 *   @author TT satoh
	 *   @param _scrollMain : ���얾�׃e�[�u���̃X�N���[���o�[
	 *
	 ************************************************************************************/
	public void setScrollMain(ScrollBase _scrollMain) {
		scrollMain = _scrollMain;
	}
	//2011/04/20 QP@10181_No.41 TT T.Satoh Add End ---------------------------


//add start -------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.7
	/************************************************************************************
	 *
	 *   �R�s�[�����Z���̍Čv�Z
	 *   @author TT nishigawa
	 *
	 ************************************************************************************/
	public void AutoCopyKeisan() throws ExceptionBase{
		try{
			if(editFg && editCol > -1){
				//�R�s�[��v�Z�t���O���ɐݒ肳��Ă���ꍇ
	    		if( JwsConstManager.JWS_COPY_0002){

	    			//�Čv�Z�̉e���͈͊m�F
	    			if(AutoCopyKeisanCheck(editCol)){

	    			}
	    			else{
//	    				//�_�C�A���O�R���|�[�l���g�ݒ�
						JOptionPane jp = new JOptionPane();

		    			//�m�F�_�C�A���O�\��
						int option = jp.showConfirmDialog(
								jp.getRootPane(),
								JwsConstManager.JWS_ERROR_0045
								, "�m�F���b�Z�[�W"
								,JOptionPane.YES_NO_OPTION
								,JOptionPane.PLAIN_MESSAGE
							);

						//�u�͂��v����
					    if (option == JOptionPane.YES_OPTION){

					    //�u�������v����
					    }else if (option == JOptionPane.NO_OPTION){
					    	//�Z���ҏW�t���O��������
			    			editFg = false;
					    	return;
					    }
	    			}

	    			//�ő�H�����擾
//	    			int maxKotei = DataCtrl.getInstance().getTrialTblData().getIntMaxKotei();
	    			//���v�d��d��
	    			//2011/06/13 QP@10181_No.29 TT T.Satoh Change Start -------------------------
	    			//int keisanRow = ListMeisai.getRowCount()-7;
	    			int keisanRow = ListMeisai.getRowCount()-8;
	    			//2011/06/13 QP@10181_No.29 TT T.Satoh Change End ---------------------------

					for(int j=0 ;j<ListMeisai.getRowCount(); j++){

						//�s�E��@�擾
		    			int row = j;
						int column = editCol;

						//---------------- �L�[���ڎ擾  -------------------------------
						int intShisakuSeq = -1;
						int intKoteiCd    = -1;
						int intKoteiSeq   = -1;

						//����SEQ�@�擾
						MiddleCellEditor mceHeaderKey = (MiddleCellEditor)ListHeader.getCellEditor(0, column);
						DefaultCellEditor dceHeaderKey = (DefaultCellEditor)mceHeaderKey.getTableCellEditor(0);
						CheckboxBase chkHeaderKey = (CheckboxBase)dceHeaderKey.getComponent();
						intShisakuSeq  = Integer.parseInt(chkHeaderKey.getPk1());

						//�H��CD & �H��SEQ�@�擾
						MiddleCellEditor mceHaigoKey = (MiddleCellEditor)HaigoMeisai.getCellEditor(row, 2);
						DefaultCellEditor dceHaigoKey = (DefaultCellEditor)mceHaigoKey.getTableCellEditor(row);

						//�H���s�łȂ��ꍇ or ���v�d��d�ʍs
						if(dceHaigoKey.getComponent() instanceof CheckboxBase || j == keisanRow){

							//�u���ʊJ���v�w��
    						String strKakkoHiraki = JwsConstManager.JWS_COPY_0003;

    						//�u���ʕ��v�w��
    						String strKakkoTozi = JwsConstManager.JWS_COPY_0004;

			    			//�z���f�[�^�z��擾
			    			ArrayList aryShisaku = DataCtrl.getInstance().getTrialTblData().SearchShisakuRetuData(intShisakuSeq);

			    			//�T���v��SEQ�擾
			    			String SampleSeq = JwsConstManager.JWS_COPY_0005 + Integer.toString((((TrialData)aryShisaku.get(0)).getIntShisakuSeq()));

			    			//�T���v��NO�擾
			    			String SampleKeisan = ((TrialData)aryShisaku.get(0)).getStrKeisanSiki();
			    			if(SampleKeisan == null || SampleKeisan.length() ==0){
			    				SampleKeisan = SampleSeq;
			    			}

			    			//String SampleSeq = ((TrialData)aryShisaku.get(0))
			    			String chkSampleNo = strKakkoHiraki + SampleKeisan + strKakkoTozi;




			    			//�\���l�擾
			    			String insert = "";
			    			try{
		    					insert = ListMeisai.getValueAt( row, column ).toString();
		    				}catch(Exception e){

		    				}

			    			//����񃋁[�v
			    			for(int i=0; i<ListHeader.getColumnCount(); i++){

			    				//���g�̗�łȂ��ꍇ
			    				if( i != column ){

			    					//����SEQ�擾
		    						MiddleCellEditor mceHeaderKey1 = (MiddleCellEditor)ListHeader.getCellEditor(0, i);
									DefaultCellEditor dceHeaderKey1 = (DefaultCellEditor)mceHeaderKey1.getTableCellEditor(0);
									CheckboxBase chkHeaderKey1 = (CheckboxBase)dceHeaderKey1.getComponent();
									int roopShisakuSeq  = Integer.parseInt(chkHeaderKey1.getPk1());

			    					//�v�Z���擾
			    					String roopSampleKeisanSiki =
			    						DataCtrl.getInstance().getTrialTblData().SearchShisakuKeisanSiki(roopShisakuSeq);

			    					//�v�Z����NULL�̏ꍇ
			    					if(roopSampleKeisanSiki == null){

			    					}
			    					//�v�Z����NULL�łȂ��ꍇ
			    					else{
			    						//����̃T���v��No���܂܂��ꍇ
				    					if(roopSampleKeisanSiki.indexOf(chkSampleNo) >= 0){

											//�������Z�˗�����Ă���f�[�^�͏������Ȃ�
											TrialData chkTrialData = (TrialData)DataCtrl.getInstance().getTrialTblData().SearchShisakuRetuData(roopShisakuSeq).get(0);
											if(chkTrialData.getFlg_init() == 1){

											}
											else{
												//���g�̃T���v��No���ݒ肳��Ă��镔����z���ʂƂ���������
												String repSampleNo = strKakkoHiraki + SampleKeisan + strKakkoTozi;
												//String repSampleNo = JwsConstManager.JWS_COPY_0005 + SampleNo;
												roopSampleKeisanSiki = roopSampleKeisanSiki.replaceAll( "\\Q" + chkSampleNo + "\\E" , insert);

												//�v�Z���ϊ�
												String keisanSiki = DataCtrl.getInstance().getTrialTblData().changeKeisanLogic( roopSampleKeisanSiki , 0 );

//												//String keisanSiki = roopSampleNo;
//												//���g�̃T���v��No���ݒ肳��Ă��镔����z���ʂƂ���������
//												String repSampleNo = SampleSeq;
//												roopSampleKeisanSiki = roopSampleKeisanSiki.replaceAll( repSampleNo , insert);
//												//�v�Z���ϊ�
//												String keisanSiki = roopSampleKeisanSiki;

												//���v�d��d�ʌv�Z
												if(j == keisanRow){
													String keisan =
														DataCtrl.getInstance().getTrialTblData().getKeisanShisakuSeqSiagari( keisanSiki);

													//�v�Z���s
													String strKekka = DataCtrl.getInstance().getTrialTblData().execKeisan(keisan);

													//�������ցi���v�d��d�ʁj
									    			if(strKekka != null && strKekka.length() > 0){

									    				//���֏���
									    				strKekka = ShosuAraiHulfUp_keta(strKekka,"4");

									    			}

													//�e�[�u���}��
													ListMeisai.setValueAt(strKekka, row, i);

													//�f�[�^�}��
													DataCtrl.getInstance().getTrialTblData().UpdShiagariRetuDate(
															roopShisakuSeq,
															DataCtrl.getInstance().getTrialTblData().checkNullDecimal(strKekka),
															DataCtrl.getInstance().getUserMstData().getDciUserid()
														);

												}
												//�z���ʌv�Z
												else{

													//�L�[���ڎ擾
													CheckboxBase chkHaigoKey = (CheckboxBase)dceHaigoKey.getComponent();
													intKoteiCd  = Integer.parseInt(chkHaigoKey.getPk1());
													intKoteiSeq = Integer.parseInt(chkHaigoKey.getPk2());

													//�z���ʐݒ�
													String keisan =
														DataCtrl.getInstance().getTrialTblData().getKeisanShisakuSeq( keisanSiki , intKoteiCd , intKoteiSeq );

						    						//�v�Z���s
													String strKekka = DataCtrl.getInstance().getTrialTblData().execKeisan(keisan);

													//��������
									    			if(strKekka != null && strKekka.length() > 0){

									    				//���֏���
									    				strKekka = ShosuAraiHulfUp(strKekka);

									    			}

									    			//�e�[�u���}��
													ListMeisai.setValueAt(strKekka, row, i);

									    			//�f�[�^�}��
													DataCtrl.getInstance().getTrialTblData().UpdShisakuListRyo(
															roopShisakuSeq,
															intKoteiCd,
															intKoteiSeq,
															DataCtrl.getInstance().getTrialTblData().checkNullDecimal(strKekka)
														);

						    						//�H�����v�v�Z
													koteiSum(i);
												}
											}
				    					}
			    					}

			    				}
			    			}
						}
					}
					//�����v�Z
					AutoKeisan();

					//������v�Z
	    			DispGenryohi();

	    			//�Z���ҏW�t���O��������
	    			editFg = false;
	    		}
			}
		}catch(ExceptionBase be){

		}catch(Exception ex){
			ex.printStackTrace();

		}finally{
			//DataCtrl.getInstance().getTrialTblData().dispTrial();
		}
	}

	/************************************************************************************
	 *
	 *   ���g�̗񂪑���̌v�Z�ň����Ă��邩�̊m�F
	 *   @param int�@�F�@��w��
	 *   @return boolean�@�F�@true �e�����Ȃ��@false �e������
	 *   @author TT nishigawa
	 *
	 ************************************************************************************/
	public boolean AutoCopyKeisanCheck(int col) throws ExceptionBase{

		boolean ret = true;

		try{
				//�R�s�[��v�Z�t���O���ɐݒ肳��Ă���ꍇ
	    		if( JwsConstManager.JWS_COPY_0002){

	    			//�ő�H�����擾
	    			int maxKotei = DataCtrl.getInstance().getTrialTblData().getIntMaxKotei();
	    			//���v�d��d��
	    			//2011/06/13 QP@10181_No.29 TT T.Satoh Change Start -------------------------
	    			//int keisanRow = ListMeisai.getRowCount()-maxKotei-6;
	    			int keisanRow = ListMeisai.getRowCount()-maxKotei-7;
	    			//2011/06/13 QP@10181_No.29 TT T.Satoh Change End ---------------------------

					for(int j=0 ;j<ListMeisai.getRowCount(); j++){

						//�s�E��@�擾
		    			int row = j;
						int column = col;

						//---------------- �L�[���ڎ擾  -------------------------------
						int intShisakuSeq = -1;

						//����SEQ�@�擾
						MiddleCellEditor mceHeaderKey = (MiddleCellEditor)ListHeader.getCellEditor(0, column);
						DefaultCellEditor dceHeaderKey = (DefaultCellEditor)mceHeaderKey.getTableCellEditor(0);
						CheckboxBase chkHeaderKey = (CheckboxBase)dceHeaderKey.getComponent();
						intShisakuSeq  = Integer.parseInt(chkHeaderKey.getPk1());

						//�H��CD & �H��SEQ�@�擾
						MiddleCellEditor mceHaigoKey = (MiddleCellEditor)HaigoMeisai.getCellEditor(row, 2);
						DefaultCellEditor dceHaigoKey = (DefaultCellEditor)mceHaigoKey.getTableCellEditor(row);

						//�H���s�łȂ��ꍇ or ���v�d��d�ʍs
						if(dceHaigoKey.getComponent() instanceof CheckboxBase || j == keisanRow){

							//�u���ʊJ���v�w��
    						String strKakkoHiraki = JwsConstManager.JWS_COPY_0003;

    						//�u���ʕ��v�w��
    						String strKakkoTozi = JwsConstManager.JWS_COPY_0004;

			    			//�z���f�[�^�z��擾
			    			ArrayList aryShisaku = DataCtrl.getInstance().getTrialTblData().SearchShisakuRetuData(intShisakuSeq);

			    			//�T���v��SEQ�擾
			    			String SampleSeq = JwsConstManager.JWS_COPY_0005 + Integer.toString((((TrialData)aryShisaku.get(0)).getIntShisakuSeq()));

			    			//�T���v��NO�擾
			    			String SampleKeisan = ((TrialData)aryShisaku.get(0)).getStrKeisanSiki();
			    			if(SampleKeisan == null || SampleKeisan.length() ==0){
			    				SampleKeisan = SampleSeq;
			    			}

			    			//String SampleSeq = ((TrialData)aryShisaku.get(0))
			    			String chkSampleNo = strKakkoHiraki + SampleKeisan + strKakkoTozi;

			    			//����񃋁[�v
			    			for(int i=0; i<ListHeader.getColumnCount(); i++){

			    				//���g�̗�łȂ��ꍇ
			    				if( i != column ){

			    					//����SEQ�擾
		    						MiddleCellEditor mceHeaderKey1 = (MiddleCellEditor)ListHeader.getCellEditor(0, i);
									DefaultCellEditor dceHeaderKey1 = (DefaultCellEditor)mceHeaderKey1.getTableCellEditor(0);
									CheckboxBase chkHeaderKey1 = (CheckboxBase)dceHeaderKey1.getComponent();
									int roopShisakuSeq  = Integer.parseInt(chkHeaderKey1.getPk1());

			    					//�v�Z���擾
			    					String roopSampleKeisanSiki =
			    						DataCtrl.getInstance().getTrialTblData().SearchShisakuKeisanSiki(roopShisakuSeq);

			    					//�v�Z����NULL�̏ꍇ
			    					if(roopSampleKeisanSiki == null){

			    					}
			    					//�v�Z����NULL�łȂ��ꍇ
			    					else{
			    						//����̃T���v��No���܂܂��ꍇ
				    					if(roopSampleKeisanSiki.indexOf(chkSampleNo) >= 0){

											//�������Z�˗�����Ă���f�[�^�͏������Ȃ�
											TrialData chkTrialData = (TrialData)DataCtrl.getInstance().getTrialTblData().SearchShisakuRetuData(roopShisakuSeq).get(0);
											if(chkTrialData.getFlg_init() == 1){

											}
											else{
												ret = false;
												break;
											}
				    					}
			    					}
			    				}
			    			}
						}
					}
			}
		}catch(ExceptionBase be){

		}catch(Exception ex){
			ex.printStackTrace();

		}finally{
			//DataCtrl.getInstance().getTrialTblData().dispTrial();
		}

		return ret;
	}
//add end   -------------------------------------------------------------------------------

//2011/04/26 QP@10181_No.73 TT T.Satoh Add Start -------------------------
	/************************************************************************************
	 *
	 *   �w�肵���s�̒P���̕ҏW�s��ݒ肷��
	 *   @param int�@�F�@�s�w��
	 *   @param boolean�@�F�@true �ҏW�\�@false �ҏW�s�\
	 *   @author TT T.Satoh
	 *
	 ************************************************************************************/
	public void changeTankaHenshuOK(int row, boolean henshu) {
		DefaultCellEditor dceTanka = (DefaultCellEditor)HaigoMeisaiCellEditor5.getTableCellEditor(row);

		if (henshu) {
			((TextboxBase)dceTanka.getComponent()).setEnabled(true);
			((TextboxBase)dceTanka.getComponent()).setEditable(true);
		} else {
			((TextboxBase)dceTanka.getComponent()).setEnabled(false);
			((TextboxBase)dceTanka.getComponent()).setEditable(false);
		}

		TextFieldCellRenderer tfcrTanka =  (TextFieldCellRenderer)HaigoMeisaiCellRenderer5.getTableCellRenderer(row);

		if (henshu) {
			tfcrTanka.setColor(Color.WHITE);
		} else {
			tfcrTanka.setColor(JwsConstManager.JWS_DISABLE_COLOR);
		}
	}
//2011/04/26 QP@10181_No.73 TT T.Satoh Add End ---------------------------

//2011/04/26 QP@10181_No.12 TT Nishigawa Add Start ---------------------------
	public CheckboxBase getAllCheck() {
		return allCheck;
	}
	public void setAllCheck(CheckboxBase allCheck) {
		this.allCheck = allCheck;
	}
//2011/04/26 QP@10181_No.12 TT Nishigawa Add End ---------------------------


//2011/05/11 QP@10181_No.42_49_72 TT T.Nishigawa Add Start -------------------------
	/************************************************************************************
	 *
	 *   ���i��d�A�[�U�ʁA�����[�U�ʁA�����[�U�ʁ@�v�Z����
	 *   @param �Ȃ�
	 *   @author TT T.Nishigawa
	 *
	 ************************************************************************************/
	public void ZidouKeisan2(){

		try{

			//�H���p�^�[���擾
			String ptKotei = DataCtrl.getInstance().getTrialTblData().SearchShisakuhinData().getStrPt_kotei();

			//���i��d�A�[�U�ʁA�����[�U�ʁA�����[�U�ʁ@�v�Z����
			for(int i=0; i<ListHeader.getColumnCount();i++){

				//�R���|�[�l���g�擾
				MiddleCellEditor mc2 = (MiddleCellEditor)ListHeader.getCellEditor(0, i);
				DefaultCellEditor tc2 = (DefaultCellEditor)mc2.getTableCellEditor(0);
				CheckboxBase getCheck = (CheckboxBase)tc2.getComponent();
				int intSeq = Integer.parseInt(getCheck.getPk1());

	    		//�H���p�^�[�����u�󔒁v�̏ꍇ
	    		if(ptKotei == null || ptKotei.length() == 0){

	    		}
	    		//�H���p�^�[�����u�󔒁v�łȂ��ꍇ
	    		else{

	    			//�H���p�^�[����Value1�擾
					String ptValue = DataCtrl.getInstance().getLiteralDataKoteiPtn().selectLiteralVal1(ptKotei);

					//�H���p�^�[�����u�������P�t�^�C�v�v�̏ꍇ-------------------------------------------------------------
					if(ptValue.equals(JwsConstManager.JWS_KOTEITYPE_1)){

						//�[�U�ʂ��v�Z
						String keisan1 = DataCtrl.getInstance().getTrialTblData().KeisanZyutenType1(intSeq);
						DataCtrl.getInstance().getTrialTblData().UpdGenkaValue(
			    				intSeq,
			    				DataCtrl.getInstance().getTrialTblData().checkNullString(keisan1),
			    				DataCtrl.getInstance().getUserMstData().getDciUserid(),
			    				JwsConstManager.JWS_COMPONENT_0134);

					}
					//�H���p�^�[�����u�������Q�t�^�C�v�v�̏ꍇ-------------------------------------------------------------
					else if(ptValue.equals(JwsConstManager.JWS_KOTEITYPE_2)){

						//���i��d���v�Z
			    		String keisan = DataCtrl.getInstance().getTrialTblData().KeisanSeihinHiju(intSeq);
			    		DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuHiju(
			    				intSeq,
			    				DataCtrl.getInstance().getTrialTblData().checkNullString(keisan),
			    				DataCtrl.getInstance().getUserMstData().getDciUserid()
			    			);

						//�����[�U�ʂ��v�Z
						String keisan1 = DataCtrl.getInstance().getTrialTblData().KeisanSuisoZyuten(intSeq);
						DataCtrl.getInstance().getTrialTblData().UpdGenkaValue(
			    				intSeq,
			    				DataCtrl.getInstance().getTrialTblData().checkNullString(keisan1),
			    				DataCtrl.getInstance().getUserMstData().getDciUserid(),
			    				JwsConstManager.JWS_COMPONENT_0134);

						//�����[�U�ʂ��v�Z
						String keisan2 = DataCtrl.getInstance().getTrialTblData().KeisanYusoZyuten(intSeq);
						DataCtrl.getInstance().getTrialTblData().UpdGenkaValue(
			    				intSeq,
			    				DataCtrl.getInstance().getTrialTblData().checkNullString(keisan2),
			    				DataCtrl.getInstance().getUserMstData().getDciUserid(),
			    				JwsConstManager.JWS_COMPONENT_0135);
					}
					//�H���p�^�[�����u���̑��E���H�^�C�v�v�̏ꍇ-------------------------------------------------------------
					else if(ptValue.equals(JwsConstManager.JWS_KOTEITYPE_3)){

					}
	    		}
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
//2011/05/11 QP@10181_No.42_49_72 TT T.Nishigawa Add End -------------------------

	
	/*******************************************************************************
	 *
	 *   �������͉��
	 *   @author shima.hs
	 *
	 *******************************************************************************/
	class memoPanelDisp extends MouseAdapter {
		
		//*************************************
		//  �����o�i�[
		//*************************************
		TableBase TableBase;
		String string = "";
		memoChangeEvent mce;
		
		//*************************************
		//  �R���X�g���N�^
		//*************************************
		public memoPanelDisp(TableBase tb){
			TableBase = tb;
			mce = new memoChangeEvent(TableBase);
		}
		
		//*************************************
		//  �}�E�X�N���b�N
		//*************************************
		public void mouseClicked(final MouseEvent me) {
			
			int column = TableBase.getSelectedColumn();
			int row = 4;
			
			//�_�u���N���b�N
			if(me.getClickCount()==2){
				try{
					if(DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
							JwsConstManager.JWS_COMPONENT_0020,DataCtrl.getInstance().getParamData().getStrMode())){
						
						//�ҏW���̏������m�肳����
						TableBase.editingStopped(null);
						
						//������ʂ̃e�L�X�g�Ɋ����̓e�L�X�g��ݒ�
						String insert=checkNull(TableBase.getValueAt(row,column));
						memoSubDisp.getMemoInputPanel().getMemoTextBox().setText(insert);
						
						memoSubDisp.getMemoInputPanel().setColumn(column);
						
						//���X�i�[���폜
						memoSubDisp.getMemoInputPanel().getOKButton().removeActionListener(mce);
						//���X�i�[��ǉ�
						memoSubDisp.getMemoInputPanel().getOKButton().addActionListener(mce);
						
						//������ʕ\��
						memoSubDisp.setVisible(true);
					}
					//�ҏW�s�̏ꍇ
					else{
					}
				}catch(ExceptionBase e){
					e.printStackTrace();
				}
			}
		}
		
		//*************************************
		//  �������f�C�x���g���X�i�[
		//*************************************
		class memoChangeEvent implements ActionListener{
			
			//�����o
			TableBase TableBase;
			
			//�R���X�g���N�^
			public memoChangeEvent(TableBase tb){
				TableBase = tb;
			}
			
			public void actionPerformed(ActionEvent actionevent) {
				
				//������ʂ̃e�L�X�g�G���A���̕�����擾
				String resultStr = memoSubDisp.getMemoInputPanel().getMemoTextBox().getText().toString();
				
				//�e�[�u�����
				int col = memoSubDisp.getMemoInputPanel().getColumn();
				int row = 4;
				
				//�L�[���ڎ擾
				MiddleCellEditor mceSeq = (MiddleCellEditor)ListHeader.getCellEditor(0, col);
				DefaultCellEditor dceSeq = (DefaultCellEditor)mceSeq.getTableCellEditor(0);
				CheckboxBase chkSeq = (CheckboxBase)dceSeq.getComponent();
				int intSeq  = Integer.parseInt(chkSeq.getPk1());
				
				
				//�I���̃������ɐݒ�
				TableBase.setValueAt(resultStr ,row ,col );
				
				//�f�[�^�ݒ�
	    		try{
					DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuMemo(
							intSeq,
							DataCtrl.getInstance().getTrialTblData().checkNullString(resultStr),
							DataCtrl.getInstance().getUserMstData().getDciUserid()
						);
				}catch(ExceptionBase e){
					e.printStackTrace();
				}
				
				//��ʂ��\��
				memoSubDisp.setVisible(false);
			}
			
		}
	}
}