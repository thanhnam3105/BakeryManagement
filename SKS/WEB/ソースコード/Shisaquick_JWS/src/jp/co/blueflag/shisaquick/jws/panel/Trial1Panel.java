package jp.co.blueflag.shisaquick.jws.panel;

//------------------------------ ��{�@�\�@List�C���|�[�g  -----------------------------------
import java.math.BigDecimal;
import java.util.ArrayList;
//----------------------------------- AWT�@�C���|�[�g  --------------------------------------
import java.awt.Color;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
//---------------------------------- Swing�@�C���|�[�g  -------------------------------------
import javax.swing.DefaultCellEditor;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.table.TableCellEditor;
//--------------------------------- �V�T�N�C�b�N�@�C���|�[�g  -----------------------------------
import jp.co.blueflag.shisaquick.jws.base.BushoData;
import jp.co.blueflag.shisaquick.jws.base.CostMaterialData;
import jp.co.blueflag.shisaquick.jws.base.ManufacturingData;
import jp.co.blueflag.shisaquick.jws.base.MixedData;
import jp.co.blueflag.shisaquick.jws.base.PrototypeData;
import jp.co.blueflag.shisaquick.jws.base.PrototypeListData;
import jp.co.blueflag.shisaquick.jws.base.TrialData;
import jp.co.blueflag.shisaquick.jws.base.XmlData;
import jp.co.blueflag.shisaquick.jws.celleditor.MiddleCellEditor;
import jp.co.blueflag.shisaquick.jws.celleditor.TextFieldCellEditor;
import jp.co.blueflag.shisaquick.jws.cellrenderer.ComboBoxCellRenderer;
import jp.co.blueflag.shisaquick.jws.cellrenderer.MiddleCellRenderer;
import jp.co.blueflag.shisaquick.jws.cellrenderer.TextFieldCellRenderer;
import jp.co.blueflag.shisaquick.jws.common.ButtonBase;
import jp.co.blueflag.shisaquick.jws.common.CheckboxBase;
import jp.co.blueflag.shisaquick.jws.common.ComboBase;
import jp.co.blueflag.shisaquick.jws.common.ExceptionBase;
import jp.co.blueflag.shisaquick.jws.common.JwsConstManager;
import jp.co.blueflag.shisaquick.jws.common.PanelBase;
import jp.co.blueflag.shisaquick.jws.common.TableBase;
import jp.co.blueflag.shisaquick.jws.common.TextboxBase;
import jp.co.blueflag.shisaquick.jws.data.DataCtrl;
import jp.co.blueflag.shisaquick.jws.disp.*;
import jp.co.blueflag.shisaquick.jws.label.ItemLabel;
import jp.co.blueflag.shisaquick.jws.manager.XmlConnection;
import jp.co.blueflag.shisaquick.jws.table.PrototypeListTable;
import jp.co.blueflag.shisaquick.jws.table.Trial1Table;

/*****************************************************************************************
 *
 *   �z���\(����\�@)�N���X
 *   @author TT nishigawa
 *
 *****************************************************************************************/
public class Trial1Panel extends PanelBase {

	//--------------------------------- ��ʃ����o  ----------------------------------------
	private MaterialSubDisp materialSubDisp = null;
	private PrototypeListSubDisp prototypeListSubDisp = null;

	//-------------------------------- �e�[�u�������o  ---------------------------------------
	private TableBase table = new TableBase();
	private Trial1Table Trial1;

	private ButtonBase btnSample;			//�T���v���������{�^��
	private ButtonBase btnShisakuHyo;		//����\�{�^��
	private ButtonBase btnEiyoKeisan;		//�h�{�v�Z���{�^��

	//-------------------------------- �f�[�^&�ʐM�I�u�W�F�N�g  -------------------------------
	private XmlConnection xcon;
	private XmlData xmlJW120;				//����\�o��
	private XmlData xmlJW130;				//�T���v���������o��

	//--------------------------------- �G���[�Ǘ�  ----------------------------------------
	private ExceptionBase ex;


	/************************************************************************************
	 *
	 *   �z���\(����\�@)�R���X�g���N�^
	 *    : �z���\(����\�@)��ʂ̏���������
	 *   @author TT nishigawa
	 *
	 ************************************************************************************/
	public Trial1Panel() throws ExceptionBase {
		super();
		this.setLayout(null);
		this.setBackground(Color.WHITE);

		try {
			//��ʃC���X�^���X����
			ArrayList aList = DataCtrl.getInstance().getUserMstData().getAryAuthData();
			for (int i = 0; i < aList.size(); i++) {
				String[] items = (String[]) aList.get(i);

				//�����ꗗ��ʂ̎g�p���������邩�`�F�b�N����
				if (items[0].toString().equals("150")) {
					materialSubDisp = new MaterialSubDisp("�����ꗗ���");
					break;
				}
			}

			prototypeListSubDisp = new PrototypeListSubDisp("�����ǉ����");

		} catch (ExceptionBase e) {
			throw e;

		} catch (Exception e) {
			//�G���[�ݒ�
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			//2011/04/12 QP@10181_No.26 TT T.Satoh Change Start -------------------------
			//ex.setStrErrmsg("����f�[�^��� ����\�@ ���������������s���܂���");
			ex.setStrErrmsg("����f�[�^��� �z���\ ���������������s���܂���");
			//2011/04/12 QP@10181_No.26 TT T.Satoh Change End ---------------------------
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}
	}

	/************************************************************************************
	 *
	 *   ��ʏ���������
	 *    : �z���\(����\�@)��ʂ̏������������s��
	 *   @author TT nishigawa
	 *
	 ************************************************************************************/
	public void init() throws ExceptionBase{
		try{
			this.removeAll();

			//�����H���{�^��
			ButtonBase btnSeizoKotei = new ButtonBase();
			btnSeizoKotei.setBounds(980, 10, 17, 99);
			btnSeizoKotei.addActionListener(this.getActionEvent());
			btnSeizoKotei.setHorizontalAlignment(SwingConstants.CENTER);
			btnSeizoKotei.setMargin(new Insets(0, 0, 0, 0));
			btnSeizoKotei.setText("<html>�����H��");
			btnSeizoKotei.setActionCommand("btnSeizoKotei");

			//���[�h�ҏW
			if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
					JwsConstManager.JWS_COMPONENT_0032, DataCtrl.getInstance().getParamData().getStrMode())){
				btnSeizoKotei.setEnabled(false);
			}
			this.add(btnSeizoKotei);

			//�z���\(����\�@)�e�[�u��
			Trial1 = new Trial1Table();
			Trial1.setBounds(5,10,1010,526);
			this.add(Trial1);

			Runtime.getRuntime().gc();

			//�e�[�u������{�^���i��i�j
			//�����ꗗ
			ButtonBase btnGenryoList = new ButtonBase("�����ꗗ");
			btnGenryoList.addActionListener(this.getActionEvent());
			btnGenryoList.setActionCommand("btnGenryoList");
			btnGenryoList.setMargin(new Insets(0, 0, 0, 0));
			btnGenryoList.setBounds(5, 540, 80, 20);
			//�����`�F�b�N
			ArrayList Auth = DataCtrl.getInstance().getUserMstData().getAryAuthData();
			boolean mateChk = false;
			for(int i=0;i<Auth.size();i++){
				String[] strDispAuth = (String[])Auth.get(i);
				if(strDispAuth[0].equals("150")){
					//�{�������̏ꍇ
					if(strDispAuth[1].equals("10")){
						mateChk = true;
					}
				}
			}
			//���[�h�ҏW
			if(mateChk){
				if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
						JwsConstManager.JWS_COMPONENT_0033, DataCtrl.getInstance().getParamData().getStrMode())){
					btnGenryoList.setEnabled(false);
				}
			}else{
				btnGenryoList.setEnabled(false);
			}
			this.add(btnGenryoList);

			//�H���}��
			ButtonBase btnKoteiIns = new ButtonBase("�H���}��");
			btnKoteiIns.addActionListener(this.getActionEvent());
			btnKoteiIns.setActionCommand("btnKoteiIns");
			btnKoteiIns.setMargin(new Insets(0, 0, 0, 0));
			btnKoteiIns.setBounds(100, 540, 80, 20);
			//���[�h�ҏW
			if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
					JwsConstManager.JWS_COMPONENT_0035, DataCtrl.getInstance().getParamData().getStrMode())){
				btnKoteiIns.setEnabled(false);
			}
			this.add(btnKoteiIns);

			//�H���ړ�(��)
			ButtonBase btnKoteiMove_Up = new ButtonBase("�H���ړ�(��)");
			btnKoteiMove_Up.setMargin(new Insets(0, 0, 0, 0));
			btnKoteiMove_Up.addActionListener(this.getActionEvent());
			btnKoteiMove_Up.setActionCommand("btnKoteiMove_Up");
			btnKoteiMove_Up.setBounds(180, 540, 80, 20);
			//���[�h�ҏW
			if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
					JwsConstManager.JWS_COMPONENT_0036, DataCtrl.getInstance().getParamData().getStrMode())){
				btnKoteiMove_Up.setEnabled(false);
			}
			this.add(btnKoteiMove_Up);

			//�H���ړ�(��)
			ButtonBase btnKoteiMove_Down = new ButtonBase("�H���ړ�(��)");
			btnKoteiMove_Down.setMargin(new Insets(0, 0, 0, 0));
			btnKoteiMove_Down.addActionListener(this.getActionEvent());
			btnKoteiMove_Down.setActionCommand("btnKoteiMove_Down");
			btnKoteiMove_Down.setBounds(260, 540, 80, 20);
			//���[�h�ҏW
			if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
					JwsConstManager.JWS_COMPONENT_0037, DataCtrl.getInstance().getParamData().getStrMode())){
				btnKoteiMove_Down.setEnabled(false);
			}
			this.add(btnKoteiMove_Down);

			//�H���폜
			ButtonBase btnKoteiDel = new ButtonBase("�H���폜");
			btnKoteiDel.setMargin(new Insets(0, 0, 0, 0));
			btnKoteiDel.addActionListener(this.getActionEvent());
			btnKoteiDel.setActionCommand("btnKoteiDel");
			btnKoteiDel.setBounds(340, 540, 80, 20);
			//���[�h�ҏW
			if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
					JwsConstManager.JWS_COMPONENT_0038, DataCtrl.getInstance().getParamData().getStrMode())){
				btnKoteiDel.setEnabled(false);
			}
			this.add(btnKoteiDel);

			//�����ǉ�
			ButtonBase btnShisakuIns = new ButtonBase("�����ǉ�");
			btnShisakuIns.setMargin(new Insets(0, 0, 0, 0));
			btnShisakuIns.addActionListener(this.getActionEvent());
			btnShisakuIns.setActionCommand("btnShisakuIns");
			btnShisakuIns.setBounds(435, 540, 80, 20);
			//���[�h�ҏW
			if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
					JwsConstManager.JWS_COMPONENT_0043, DataCtrl.getInstance().getParamData().getStrMode())){
				btnShisakuIns.setEnabled(false);
			}
			this.add(btnShisakuIns);

			//�����폜
			ButtonBase btnShisakuDel = new ButtonBase("�����폜");
			btnShisakuDel.setMargin(new Insets(0, 0, 0, 0));
			btnShisakuDel.addActionListener(this.getActionEvent());
			btnShisakuDel.setActionCommand("btnShisakuDel");
			btnShisakuDel.setBounds(515, 540, 80, 20);
			//���[�h�ҏW
			if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
					JwsConstManager.JWS_COMPONENT_0044, DataCtrl.getInstance().getParamData().getStrMode())){
				btnShisakuDel.setEnabled(false);
			}
			this.add(btnShisakuDel);

			//������߰
			ButtonBase btnShisakuCopy = new ButtonBase("������߰");
			btnShisakuCopy.setMargin(new Insets(0, 0, 0, 0));
			btnShisakuCopy.addActionListener(this.getActionEvent());
			btnShisakuCopy.setActionCommand("btnShisakuCopy");
			btnShisakuCopy.setBounds(595, 540, 80, 20);
			//���[�h�ҏW
			if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
					JwsConstManager.JWS_COMPONENT_0045, DataCtrl.getInstance().getParamData().getStrMode())){
				btnShisakuCopy.setEnabled(false);
			}
			this.add(btnShisakuCopy);

			//����ِ�����
			btnSample = new ButtonBase("����ِ�����");
			btnSample.setMargin(new Insets(0, 0, 0, 0));
			btnSample.setBounds(690, 540, 80, 20);
//			btnSample.addActionListener(this.getActionEvent());
//			btnSample.setActionCommand("sampleSetumei");
			//���[�h�ҏW
			if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
					JwsConstManager.JWS_COMPONENT_0049, DataCtrl.getInstance().getParamData().getStrMode())){
				btnSample.setEnabled(false);
			}
			this.add(btnSample);

			//�e�[�u������{�^���i���i�j
			ButtonBase btnGenryoBunseki = new ButtonBase("��������");
			btnGenryoBunseki.setBounds(5, 560, 80, 20);
			btnGenryoBunseki.addActionListener(this.getActionEvent());
			btnGenryoBunseki.setActionCommand("btnGenryoBunseki");
			//�����`�F�b�N
			boolean bunsekiChk = false;
			for(int i=0;i<Auth.size();i++){
				String[] strDispAuth = (String[])Auth.get(i);
				if(strDispAuth[0].equals("160")){
					//�{�������̏ꍇ
					if(strDispAuth[1].equals("10")){
						bunsekiChk = true;
					}else if(strDispAuth[1].equals("20")){
						bunsekiChk = true;
					}
				}
			}
			if(bunsekiChk){
				//���[�h�ҏW
				if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
						JwsConstManager.JWS_COMPONENT_0034, DataCtrl.getInstance().getParamData().getStrMode())){
					btnGenryoBunseki.setEnabled(false);
				}
			}else{
				btnGenryoBunseki.setEnabled(false);
			}
			this.add(btnGenryoBunseki);

			//�����}��
			ButtonBase btnGenryoIns = new ButtonBase("�����}��");
			btnGenryoIns.setMargin(new Insets(0, 0, 0, 0));
			btnGenryoIns.addActionListener(this.getActionEvent());
			btnGenryoIns.setActionCommand("btnGenryoIns");
			btnGenryoIns.setBounds(100, 560, 80, 20);
			//���[�h�ҏW
			if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
					JwsConstManager.JWS_COMPONENT_0039, DataCtrl.getInstance().getParamData().getStrMode())){
				btnGenryoIns.setEnabled(false);
			}
			this.add(btnGenryoIns);

			//�����ړ�(��)
			ButtonBase btnGenryoMove_Up = new ButtonBase("�����ړ�(��)");
			btnGenryoMove_Up.setMargin(new Insets(0, 0, 0, 0));
			btnGenryoMove_Up.addActionListener(this.getActionEvent());
			btnGenryoMove_Up.setActionCommand("btnGenryoMove_Up");
			btnGenryoMove_Up.setBounds(180, 560, 80, 20);
			//���[�h�ҏW
			if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
					JwsConstManager.JWS_COMPONENT_0040, DataCtrl.getInstance().getParamData().getStrMode())){
				btnGenryoMove_Up.setEnabled(false);
			}
			this.add(btnGenryoMove_Up);

			//�����ړ�(��)
			ButtonBase btnGenryoMove_Down = new ButtonBase("�����ړ�(��)");
			btnGenryoMove_Down.setMargin(new Insets(0, 0, 0, 0));
			btnGenryoMove_Down.addActionListener(this.getActionEvent());
			btnGenryoMove_Down.setActionCommand("btnGenryoMove_Down");
			btnGenryoMove_Down.setBounds(260, 560, 80, 20);
			//���[�h�ҏW
			if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
					JwsConstManager.JWS_COMPONENT_0041, DataCtrl.getInstance().getParamData().getStrMode())){
				btnGenryoMove_Down.setEnabled(false);
			}
			this.add(btnGenryoMove_Down);

			//�����폜
			ButtonBase btnGenryoDel = new ButtonBase("�����폜");
			btnGenryoDel.setMargin(new Insets(0, 0, 0, 0));
			btnGenryoDel.addActionListener(this.getActionEvent());
			btnGenryoDel.setActionCommand("btnGenryoDel");
			btnGenryoDel.setBounds(340, 560, 80, 20);
			//���[�h�ҏW
			if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
					JwsConstManager.JWS_COMPONENT_0042, DataCtrl.getInstance().getParamData().getStrMode())){
				btnGenryoDel.setEnabled(false);
			}
			this.add(btnGenryoDel);

			//�����ړ�(��)
			ButtonBase btnShisakuMove_Left = new ButtonBase("�����ړ�(��)");
			btnShisakuMove_Left.setMargin(new Insets(0, 0, 0, 0));
			btnShisakuMove_Left.addActionListener(this.getActionEvent());
			btnShisakuMove_Left.setActionCommand("btnShisakuMove_Left");
			btnShisakuMove_Left.setBounds(435, 560, 80, 20);
			//���[�h�ҏW
			if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
					JwsConstManager.JWS_COMPONENT_0046, DataCtrl.getInstance().getParamData().getStrMode())){
				btnShisakuMove_Left.setEnabled(false);
			}
			this.add(btnShisakuMove_Left);

			//�����ړ�(��)
			ButtonBase btnShisakuMove_Right = new ButtonBase("�����ړ�(��)");
			btnShisakuMove_Right.setMargin(new Insets(0, 0, 0, 0));
			btnShisakuMove_Right.addActionListener(this.getActionEvent());
			btnShisakuMove_Right.setActionCommand("btnShisakuMove_Right");
			btnShisakuMove_Right.setBounds(515, 560, 80, 20);
			//���[�h�ҏW
			if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
					JwsConstManager.JWS_COMPONENT_0047, DataCtrl.getInstance().getParamData().getStrMode())){
				btnShisakuMove_Right.setEnabled(false);
			}
			this.add(btnShisakuMove_Right);

			//����\�o��
			btnShisakuHyo = new ButtonBase("����\�o��");
			btnShisakuHyo.setMargin(new Insets(0, 0, 0, 0));
			btnShisakuHyo.setBounds(595, 560, 80, 20);
//			btnShisakuHyo.addActionListener(this.getActionEvent());
//			btnShisakuHyo.setActionCommand("shisakuHyo");
			//���[�h�ҏW
			if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
					JwsConstManager.JWS_COMPONENT_0048, DataCtrl.getInstance().getParamData().getStrMode())){
				btnShisakuHyo.setEnabled(false);
			}
			this.add(btnShisakuHyo);

			//�h�{�v�Z���o��
			btnEiyoKeisan = new ButtonBase("�h�{�v�Z��");
			btnEiyoKeisan.setMargin(new Insets(0, 0, 0, 0));
			btnEiyoKeisan.setBounds(690, 560, 80, 20);
			//���[�h�ҏW
			if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
					JwsConstManager.JWS_COMPONENT_0126, DataCtrl.getInstance().getParamData().getStrMode())){
				btnEiyoKeisan.setEnabled(false);
			}
			this.add(btnEiyoKeisan);


			//�����H����ʐݒ�
			DataCtrl.getInstance().getManufacturingSubDisp().getManufacturingPanel().addControl();
			DataCtrl.getInstance().getManufacturingSubDisp().getManufacturingPanel().init();

			//----------------------------- �����H���g�C�x���g�ǉ� -------------------------------
			//�����H���p�l���擾
			ManufacturingPanel pb = DataCtrl.getInstance().getManufacturingSubDisp().getManufacturingPanel();
			//�����H���V�K�{�^��
			pb.getButton()[0].addActionListener(this.getActionEvent());
			pb.getButton()[0].setActionCommand("sinki");

			//�����H���X�V�{�^��
			pb.getButton()[1].addActionListener(this.getActionEvent());
			pb.getButton()[1].setActionCommand("kosin");

		} catch (ExceptionBase e) {
			throw e;

		} catch (Exception e) {
			//�G���[�ݒ�
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			//2011/04/12 QP@10181_No.26 TT T.Satoh Change Start -------------------------
			//ex.setStrErrmsg("����f�[�^��� ����\�@ ���������������s���܂���");
			ex.setStrErrmsg("����f�[�^��� �z���\ ���������������s���܂���");
			//2011/04/12 QP@10181_No.26 TT T.Satoh Change End ---------------------------
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}
	}

	/************************************************************************************
	 *
	 *   ActionListener�C�x���g
	 *    : �z���\(����\�@)��ʂł̃{�^���������̏������L���b�`����
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

//add start -------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.7
						Trial1.AutoCopyKeisan();
//add end   -------------------------------------------------------------------------------

						//------------------------ �I���H���������ݒ�  ------------------------------
						TableBase selectHaigoMeisai = Trial1.getHaigoMeisai();
						ArrayList aryGenryoCheck = new ArrayList();
						//�H���I��������
						Trial1.setKCheck(new int[]{-1,-1});
						for(int i=0; i<selectHaigoMeisai.getRowCount(); i++){
							//�H���I��ݒ�
							MiddleCellEditor mcKotei = (MiddleCellEditor)selectHaigoMeisai.getCellEditor(i, 0);
							DefaultCellEditor tcKotei = (DefaultCellEditor)mcKotei.getTableCellEditor(i);
							if(((JComponent)tcKotei.getComponent()) instanceof CheckboxBase){
								CheckboxBase chkKotei = (CheckboxBase)tcKotei.getComponent();
								if(chkKotei.isSelected()){
									//�s�E��ԍ��擾
									int[] kcheck = new int[2];
									kcheck[0] = i;    //�s�ԍ�
									kcheck[1] = 0; //��ԍ�
									//�I���s��ݒ�
									Trial1.setKCheck(kcheck);
								}
							}
							//�����I��ݒ�
							MiddleCellEditor mcGenryo = (MiddleCellEditor)selectHaigoMeisai.getCellEditor(i, 2);
							DefaultCellEditor tcGenryo = (DefaultCellEditor)mcGenryo.getTableCellEditor(i);
							if(((JComponent)tcGenryo.getComponent()) instanceof CheckboxBase){
								CheckboxBase chkGenryo = (CheckboxBase)tcGenryo.getComponent();
								if(chkGenryo.isSelected()){
									//�s�E��ԍ��擾
									int[] gcheck = new int[2];
									gcheck[0] = i;    //�s�ԍ�
									gcheck[1] = 2; //��ԍ�
									//�I���s��ݒ�
									aryGenryoCheck.add(gcheck);
								}
							}
						}
						Trial1.setAryGenryoCheck(aryGenryoCheck);

						//-------------------------- �I�������ݒ�  --------------------------------
						TableBase ListHeader = Trial1.getListHeader();
						int selectCol = -1;
						for(int i=0; i<ListHeader.getColumnCount(); i++){
							//�R���|�[�l���g�擾
							MiddleCellEditor mcShisaku = (MiddleCellEditor)ListHeader.getCellEditor(0, i);
							DefaultCellEditor tcShisaku = (DefaultCellEditor)mcShisaku.getTableCellEditor(0);
							CheckboxBase chkShisaku = (CheckboxBase)tcShisaku.getComponent();
							if(chkShisaku.isSelected()){
								//��ԍ��擾
								selectCol = i;
							}
						}
						
						// �yKPX1500671�z ADD start
						// �����ꗗ�E�������́E�����H���{�^���C�x���g�͕ύX�t���O�ύX�����O����
						if ( (event_name != "btnGenryoBunseki") &&  (event_name != "btnGenryoList") && (event_name != "btnSeizoKotei") 
								&& (event_name != "sinki") && (event_name != "kosin")) {
							//�f�[�^�ύX�t���O�n�m
							DataCtrl.getInstance().getTrialTblData().setHenkouFg(true);
						}
						// �yKPX1500671�z ADD end

					    //----------- ���앪�̓f�[�^�m�F�T�u��� �{�^�� �N���b�N���̏���  ---------------------
						if ( event_name == "btnGenryoBunseki") {
//mod start --------------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.5
//							Trial1.getAnalysinSubDisp().setVisible(true);
//							Trial1.getAnalysinSubDisp().getAnalysisPanel().init();

							//���앪�̓f�[�^�m�F�T�u��ʁ@�ĕ\��
							AnalysinSubDisp as = new AnalysinSubDisp("���앪�̓f�[�^�m�F���");
							Trial1.getAnalysinSubDisp().dispose();
							Trial1.setAnalysinSubDisp(as);
							Trial1.getAnalysinSubDisp().setVisible(true);
							Trial1.getAnalysinSubDisp().getAnalysisPanel().init();

							//����i�f�[�^ ��Ё������R�[�h�擾
							PrototypeData pd = DataCtrl.getInstance().getTrialTblData().SearchShisakuhinData();
							String strSelKaisha = Integer.toString(pd.getIntKaishacd());
							String strSelBusho = Integer.toString(pd.getIntKojoco());

							//��Ё������R���{�{�b�N�X�擾
							AnalysisPanel ap = Trial1.getAnalysinSubDisp().getAnalysisPanel();
							ComboBase cmbKaisha = ap.getKaishaComb();
							ComboBase cmbKojo = ap.getBushoComb();

							//���앪�̓f�[�^�m�F�T�u��ʓ��̉�Ѓf�[�^�擾
							ArrayList aryKaishaCd = ap.getKaishaData().getArtKaishaCd();
							ArrayList aryKaishaNm = ap.getKaishaData().getAryKaishaNm();

							//��Ѓf�[�^�������[�v
							for(int ki=0; ki<aryKaishaCd.size(); ki++){

								//��ЃR�[�h�A��Ж��擾
								String strkaishaCd = (String)aryKaishaCd.get(ki);
								String strkaishaNm = (String)aryKaishaNm.get(ki);

								//����̉�ЃR�[�h�����݂���ꍇ
								if(strSelKaisha.equals(strkaishaCd)){

									//��ЃR���{�{�b�N�X�I��
									cmbKaisha.setSelectedItem(strkaishaNm);

									//�����f�[�^�擾
									BushoData bd = ap.getBushoData();

									//�����ꗗ��ʓ��̕����f�[�^�擾
									ArrayList aryBushoCd = ap.getBushoData().getArtBushoCd();
									ArrayList aryBushoNm = ap.getBushoData().getAryBushoNm();

									//�����f�[�^�������[�v
									for(int kj=0; kj<aryBushoCd.size(); kj++){

										//�����R�[�h�A�������擾
										String strBushoCd = (String)aryBushoCd.get(kj);
										String strBushoNm = (String)aryBushoNm.get(kj);

										//����̕����R�[�h�����݂���ꍇ
										if(strSelBusho.equals(strBushoCd)){

											//�����R���{�{�b�N�X�I��
											cmbKojo.setSelectedItem(strBushoNm);

											//���[�v�����I��
											break;
										}

									}

									//���[�v�����I��
									break;
								}
							}

							if(strSelKaisha.equals(JwsConstManager.JWS_CD_DAIHYO_KAISHA)
									&& strSelBusho.equals(JwsConstManager.JWS_CD_DAIHYO_KOJO)){
								ap.getShiyoFlgBtn()[0].setEnabled(false);
								ap.getShiyoFlgBtn()[1].setEnabled(true);
								ap.getShiyoFlgBtn()[1].setSelected(true);
							}
							else{
								ap.getShiyoFlgBtn()[0].setEnabled(true);
								ap.getShiyoFlgBtn()[1].setEnabled(true);
								ap.getShiyoFlgBtn()[0].setSelected(true);
							}
//mod end --------------------------------------------------------------------------------------
						}

						//--------------- �����ꗗ�T�u��� �{�^�� �N���b�N���̏���  -------------------------
						else if ( event_name == "btnGenryoList") {

//mod start --------------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.5
							materialSubDisp.dispose();
							materialSubDisp = new MaterialSubDisp("�����ꗗ���");
//mod end --------------------------------------------------------------------------------------

							//����i�f�[�^�擾
							PrototypeData pd = DataCtrl.getInstance().getTrialTblData().SearchShisakuhinData();

							//��Ё������R�[�h�擾
							String strSelKaisha = Integer.toString(pd.getIntKaishacd());
							String strSelBusho = Integer.toString(pd.getIntKojoco());

							//�����ꗗ�p�l���擾
							MaterialPanel mp = materialSubDisp.getMaterialPanel();

							//��Ё������R���{�{�b�N�X�擾
							ComboBase cmbKaisha = mp.getKaishaComb();
							ComboBase cmbKojo = mp.getKojoComb();


							//�����ꗗ��ʓ��̉�Ѓf�[�^�擾
							ArrayList aryKaishaCd = mp.getKaishaData().getArtKaishaCd();
							ArrayList aryKaishaNm = mp.getKaishaData().getAryKaishaNm();

							//��Ѓf�[�^�������[�v
							for(int ki=0; ki<aryKaishaCd.size(); ki++){

								//��ЃR�[�h�A��Ж��擾
								String strkaishaCd = (String)aryKaishaCd.get(ki);
								String strkaishaNm = (String)aryKaishaNm.get(ki);

								//����̉�ЃR�[�h�����݂���ꍇ
								if(strSelKaisha.equals(strkaishaCd)){

									//��ЃR���{�{�b�N�X�I��
									cmbKaisha.setSelectedItem(strkaishaNm);

									//�����f�[�^�擾
									BushoData bd = mp.getBushoData();

									//�����ꗗ��ʓ��̕����f�[�^�擾
									ArrayList aryBushoCd = mp.getBushoData().getArtBushoCd();
									ArrayList aryBushoNm = mp.getBushoData().getAryBushoNm();

									//�����f�[�^�������[�v
									for(int kj=0; kj<aryBushoCd.size(); kj++){

										//�����R�[�h�A�������擾
										String strBushoCd = (String)aryBushoCd.get(kj);
										String strBushoNm = (String)aryBushoNm.get(kj);

										//����̕����R�[�h�����݂���ꍇ
										if(strSelBusho.equals(strBushoCd)){

											//�����R���{�{�b�N�X�I��
											cmbKojo.setSelectedItem(strBushoNm);

											//���[�v�����I��
											break;
										}

									}

									//���[�v�����I��
									break;
								}
							}

//add start --------------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.4
							//�H��I�����������s��
							mp.setShiyoFlg();
//add end --------------------------------------------------------------------------------------

							//��ʕ\��
							materialSubDisp.setVisible(true);
						}
						//------------------ �����R�s�[�{�^�� �N���b�N���̏���  ---------------------------
						else if ( event_name == "btnShisakuCopy") {

//mod start -------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.2
							//�T���v��No����`�F�b�N
							String chk = DataCtrl.getInstance().getTrialTblData().checkDistSampleNo_ALL();
							//����T���v��No���Ȃ��ꍇ
							if(chk==null){
								//������
								prototypeListSubDisp.initPanel();
								//�̗p�{�^���C�x���g�ǉ�
								(prototypeListSubDisp.getPrototypeListPanel().getButton())[1].addActionListener(this);
								(prototypeListSubDisp.getPrototypeListPanel().getButton())[1].setActionCommand("saiyou");
								//�L�����Z���{�^���C�x���g�ǉ�
								(prototypeListSubDisp.getPrototypeListPanel().getButton())[2].addActionListener(this);
								(prototypeListSubDisp.getPrototypeListPanel().getButton())[2].setActionCommand("cansel");
								//��ʕ\��
								prototypeListSubDisp.setVisible(true);
							}
							//����T���v��No������ꍇ
							else{
								DataCtrl.getInstance().getMessageCtrl().PrintMessageString(JwsConstManager.JWS_ERROR_0048 + chk);
							}
//mod end --------------------------------------------------------------------------------

						}
						//--------------- �����H���T�u��ʗp�{�^�� �N���b�N���̏���  -----------------------
						else if ( event_name == "btnSeizoKotei") {

							//�����H��/���ӎ����p�l���擾
							ManufacturingPanel mp = DataCtrl.getInstance().getManufacturingSubDisp().getManufacturingPanel();

							//�u��ɕ\���v���ڂ��u�����H��/���ӎ����v�̏ꍇ
							if(mp.isTyuuiFg()){

								//�I���R���{�{�b�N�X��ݒ�
								mp.getCombo().setSelectedIndex(0);

								//�e�L�X�g�G���A�ݒ�
								mp.dispSeizo();

								//��ɕ\���`�F�b�N�{�b�N�X�ݒ�
								mp.getCheckbox().setSelected(true);

							}
							//�u��ɕ\���v���ڂ��u���상���v�̏ꍇ
							else if(mp.isMemoFg()){

								//�I���R���{�{�b�N�X��ݒ�
								mp.getCombo().setSelectedIndex(1);

								//�e�L�X�g�G���A�ݒ�
								mp.dispMemo();

								//��ɕ\���`�F�b�N�{�b�N�X�ݒ�
								mp.getCheckbox().setSelected(true);

							}
							//�u��ɕ\���v���ڂ��Z�b�g����Ă��Ȃ��ꍇ
							else{

								//�I���R���{�{�b�N�X��ݒ�
								mp.getCombo().setSelectedIndex(0);

								//�e�L�X�g�G���A�ݒ�
								mp.dispSeizo();

								//��ɕ\���`�F�b�N�{�b�N�X�ݒ�
								mp.getCheckbox().setSelected(false);

							}

							//��ʕ\��
							DataCtrl.getInstance().getManufacturingSubDisp().setVisible(true);
						}
						//-------------------- �H���}���{�^�� �N���b�N���̏���  ---------------------------
						else if ( event_name == "btnKoteiIns") {
							//�I���s�ԍ��擾
							int[] kCheck = Trial1.getKCheck();
							int row = kCheck[0];
							//�H���s�}��
							if(row >= 0){
//mod start --------------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.1
								//�����s�{�H���s�̍ő�s�`�F�b�N
								if (CheckMaxKoteiGenryoRow(2)) {
									//�H���s�}��
									HaigoInsKoteiRow(row);

									//������v�Z
									Trial1.DispGenryohi();

								}
//mod end --------------------------------------------------------------------------------------

							}
						}
						//------------------- �H���ړ��i���j�{�^�� �N���b�N���̏���  -------------------------
						else if ( event_name == "btnKoteiMove_Up") {
							//�I���s�ԍ��擾
							int[] kCheck = Trial1.getKCheck();
							int row = kCheck[0];
							//�H���s�ړ��i���j
							if(row >= 0){
								HaigoMoveKoteiRow(row,1);

								//������v�Z
								Trial1.DispGenryohi();
							}
						}
						//------------------- �H���ړ��i���j�{�^�� �N���b�N���̏���  -------------------------
						else if ( event_name == "btnKoteiMove_Down") {
							//�I���s�ԍ��擾
							int[] kCheck = Trial1.getKCheck();
							int row = kCheck[0];
							//�H���s�ړ��i���j
							if(row >= 0){
								HaigoMoveKoteiRow(row,0);

								//������v�Z
								Trial1.DispGenryohi();
							}
						}
						//---------------------- �H���폜�{�^�� �N���b�N���̏���  -------------------------
						else if ( event_name == "btnKoteiDel") {
							//�I���s�ԍ��擾
							int[] kCheck = Trial1.getKCheck();
							int row = kCheck[0];
							//�H���s�폜
							if(row >= 0){

//mod start -------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.33,34
								//�e�[�u���擾
								TableBase HaigoMeisai = Trial1.getHaigoMeisai();

								//�R���|�|�l���g�擾
								MiddleCellEditor mc = (MiddleCellEditor)HaigoMeisai.getCellEditor(row, 0);
								DefaultCellEditor tc = (DefaultCellEditor)mc.getTableCellEditor(row);
								CheckboxBase CheckboxBase = (CheckboxBase)tc.getComponent();

								//�H���L�[���ڎ擾
								int intShisakuSeq = 0;
								int intKoteiCd = Integer.parseInt(CheckboxBase.getPk1());
								int intKoteiSeq = 0;
								boolean chk = DataCtrl.getInstance().getTrialTblData().checkListHenshuOkFg(intShisakuSeq, intKoteiCd, intKoteiSeq);

								//�ҏW�\�̏ꍇ�F��������
								if(chk){

								}
								//�ҏW�s�̏ꍇ�F�G���[���b�Z�[�W��\��
								else{
									DataCtrl.getInstance().getMessageCtrl().PrintMessageString(JwsConstManager.JWS_ERROR_0040);
									return;
								}
//mod end   -------------------------------------------------------------------------------

								//�_�C�A���O�R���|�[�l���g�ݒ�
								JOptionPane jp = new JOptionPane();

								//�m�F�_�C�A���O�\��
								int option = jp.showConfirmDialog(
										jp.getRootPane(),
										"�H���s�̍폜���s���܂��B��낵���ł����H"
										, "�m�F���b�Z�[�W"
										,JOptionPane.YES_NO_OPTION
										,JOptionPane.PLAIN_MESSAGE
									);

								//�u�͂��v����
							    if (option == JOptionPane.YES_OPTION){

							    	//�H���폜
							    	HaigoDelKoteiRow(row);

									//������v�Z
									Trial1.DispGenryohi();

	//2011/05/11 QP@10181_No.42_49_72 TT T.Nishigawa Add Start -------------------------

									//���i��d�A�[�U�ʁA�����[�U�ʁA�����[�U�ʁ@�v�Z����
									Trial1.ZidouKeisan2();

	//2011/05/11 QP@10181_No.42_49_72 TT T.Nishigawa Add End -------------------------

							    //�u�������v����
							    }else if (option == JOptionPane.NO_OPTION){

							    	//�������Ȃ�

							    }

							}
						}

						//---------------------- �����}���{�^�� �N���b�N���̏���  -------------------------
						else if ( event_name == "btnGenryoIns") {

							ArrayList AryGenryoCheck = Trial1.getAryGenryoCheck();
							int count = AryGenryoCheck.size();
							String pk_KoteiCd;
							String pk_KoteiSeq;
							int maxRow = 0;

							//�I������Ă��錴��������ꍇ
							if(count > 0){
								//�e�[�u���擾
								TableBase HaigoMeisai = Trial1.getHaigoMeisai();
								TableBase ListMeisai = Trial1.getListMeisai();
								//�ő�s���擾
								for(int i=0;i<count;i++){
									int[] sel = (int[])AryGenryoCheck.get(i);
									if(maxRow < sel[0]){
										maxRow = sel[0];
									}
								}
								//�H���L�[���ڎ擾
								MiddleCellEditor mc = (MiddleCellEditor)HaigoMeisai.getCellEditor(maxRow, 2);
								DefaultCellEditor tc = (DefaultCellEditor)mc.getTableCellEditor(maxRow);
								CheckboxBase CheckboxBase = (CheckboxBase)tc.getComponent();
								pk_KoteiCd = CheckboxBase.getPk1();
								pk_KoteiSeq = CheckboxBase.getPk2();

//mod start --------------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.1
								//�����s�{�H���s�̍ő�s�`�F�b�N
								if (CheckMaxKoteiGenryoRow(1)) {
									//�����s�}��
									HaigoInsertRow(maxRow,pk_KoteiCd,pk_KoteiSeq);

									//������v�Z
									Trial1.DispGenryohi();

								}
//mod end --------------------------------------------------------------------------------------

							}
						}

						//--------------------- �����ړ��i���j�{�^�� �N���b�N���̏���  -----------------------
						else if ( event_name == "btnGenryoMove_Up") {
							HaigoMoveRow(-1);

							//������v�Z
							Trial1.DispGenryohi();

//2011/05/11 QP@10181_No.42_49_72 TT T.Nishigawa Add Start -------------------------

							//���i��d�A�[�U�ʁA�����[�U�ʁA�����[�U�ʁ@�v�Z����
							Trial1.ZidouKeisan2();

//2011/05/11 QP@10181_No.42_49_72 TT T.Nishigawa Add End -------------------------
						}

						//--------------------- �����ړ��i���j�{�^�� �N���b�N���̏���  -----------------------
						else if ( event_name == "btnGenryoMove_Down") {
							HaigoMoveRow(1);

							//������v�Z
							Trial1.DispGenryohi();

//2011/05/11 QP@10181_No.42_49_72 TT T.Nishigawa Add Start -------------------------

							//���i��d�A�[�U�ʁA�����[�U�ʁA�����[�U�ʁ@�v�Z����
							Trial1.ZidouKeisan2();

//2011/05/11 QP@10181_No.42_49_72 TT T.Nishigawa Add End -------------------------
						}

						//----------------------- �����폜�{�^�� �N���b�N���̏���  ------------------------
						else if ( event_name == "btnGenryoDel") {

							//�폜�p�f�[�^�擾
							ArrayList AryGenryoCheck = Trial1.getAryGenryoCheck();
							int count = AryGenryoCheck.size();
							String pk_KoteiCd;
							String pk_KoteiSeq;

							//�����I������Ă���ꍇ
							if(count > 0){


								//�I�����z��i�s�ԍ��̏��������̏��j
								ArrayList sort = new ArrayList();
								for(int i=0; i<count; i++){

									//�I���s�ԍ��擾
									int row = ((int[])AryGenryoCheck.get(i))[0];

									//�\�[�g�i2��ڈȍ~�j
									if(sort.size() > 0){

										//sort�z�����1�����ɔ�r
										int index = sort.size();

										for(int j=0; j<sort.size(); j++){
											int tag = Integer.parseInt((String)sort.get(j));

											//�s�ԍ��̐����������ꍇ
											if(row < tag){
												index = j;
												j = sort.size();
											}
										}
										sort.add(index, Integer.toString(row));
									//�\�[�g�i����j
									}else{
										sort.add(Integer.toString(row));
									}
								}

//mod start -------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.33,34
								//�e�[�u���擾
								TableBase chkHaigoMeisai = Trial1.getHaigoMeisai();

								//�I������Ă��錴�����폜
								for(int i=0; i<sort.size(); i++){

									//�H���L�[���ڎ擾
									int row = Integer.parseInt((String)sort.get(i));
									MiddleCellEditor mc = (MiddleCellEditor)chkHaigoMeisai.getCellEditor(row, 2);
									DefaultCellEditor tc = (DefaultCellEditor)mc.getTableCellEditor(row);
									CheckboxBase CheckboxBase = (CheckboxBase)tc.getComponent();
									int intShisakuSeq = 0;
									int intKoteiCd = Integer.parseInt(CheckboxBase.getPk1());
									int intKoteiSeq = Integer.parseInt(CheckboxBase.getPk2());

									boolean chk = DataCtrl.getInstance().getTrialTblData().checkListHenshuOkFg(intShisakuSeq, intKoteiCd, intKoteiSeq);

									//�ҏW�\�̏ꍇ�F��������
									if(chk){

									}
									//�ҏW�s�̏ꍇ�F�G���[���b�Z�[�W��\��
									else{
										DataCtrl.getInstance().getMessageCtrl().PrintMessageString(JwsConstManager.JWS_ERROR_0042);
										return;
									}
								}
//mod end   -------------------------------------------------------------------------------

								//�_�C�A���O�R���|�[�l���g�ݒ�
								JOptionPane jp = new JOptionPane();

								//�m�F�_�C�A���O�\��
								int option = jp.showConfirmDialog(
										jp.getRootPane(),
										"�����s�̍폜���s���܂��B��낵���ł����H"
										, "�m�F���b�Z�[�W"
										,JOptionPane.YES_NO_OPTION
										,JOptionPane.PLAIN_MESSAGE
									);

								//�u�͂��v����
							    if (option == JOptionPane.YES_OPTION){

							    	//�e�[�u���擾
									TableBase HaigoMeisai = Trial1.getHaigoMeisai();
									TableBase ListMeisai = Trial1.getListMeisai();

									//�I������Ă��錴�����폜
									for(int i=0; i<sort.size(); i++){

										//�H���L�[���ڎ擾
										int row = Integer.parseInt((String)sort.get(i));
										MiddleCellEditor mc = (MiddleCellEditor)HaigoMeisai.getCellEditor(row, 2);
										DefaultCellEditor tc = (DefaultCellEditor)mc.getTableCellEditor(row);
										CheckboxBase CheckboxBase = (CheckboxBase)tc.getComponent();
										pk_KoteiCd = CheckboxBase.getPk1();
										pk_KoteiSeq = CheckboxBase.getPk2();

										//�����s�폜
										HaigoDeleteRow(row,pk_KoteiCd,pk_KoteiSeq);

										//�\�[�g�z��Đݒ�
										for(int j=0; j<sort.size(); j++){
											if(i < j){
												int set = Integer.parseInt((String)sort.get(j));
												sort.set(j, Integer.toString(set-1));
											}
										}
									}

									//�I��z���������
									AryGenryoCheck.clear();

									//��H���֌����s��}��
									for(int i=0; i<HaigoMeisai.getRowCount(); i++){

										//�R���|�[�l���g�擾
										MiddleCellEditor mc_check = (MiddleCellEditor)HaigoMeisai.getCellEditor(i, 0);
										DefaultCellEditor tc_check = (DefaultCellEditor)mc_check.getTableCellEditor(i);

										//�H���s�̏ꍇ
										if(((JComponent)tc_check.getComponent()) instanceof CheckboxBase){

											//�L�[���ڂ��擾
											CheckboxBase chkSelKotei = (CheckboxBase)tc_check.getComponent();
											String selKoteiCd = chkSelKotei.getPk1();

											//�z���f�[�^����
											ArrayList aryChkKotei =
												DataCtrl.getInstance().getTrialTblData().SearchHaigoData(Integer.parseInt(selKoteiCd));

											//�H���f�[�^���Ɍ������Ȃ��ꍇ
											if(aryChkKotei.size() == 0){

												//�󌴗��s�}��
												HaigoInsertRow(i, selKoteiCd, "1");

											}
										}
									}

									//�z���f�[�^�������ݒ�
									for(int i=0; i<HaigoMeisai.getRowCount(); i++){

										//�R���|�[�l���g�擾�i�����I���`�F�b�N�{�b�N�X�j
										MiddleCellEditor selectMc = (MiddleCellEditor)HaigoMeisai.getCellEditor(i, 2);
										DefaultCellEditor selectDc = (DefaultCellEditor)selectMc.getTableCellEditor(i);

										//�����I���`�F�b�N�{�b�N�X�̏ꍇ
										if(((JComponent)selectDc.getComponent()) instanceof CheckboxBase){

											//�R���|�[�l���g���ΏۍH��CD�A�H��SEQ�擾
											CheckboxBase selectCb = (CheckboxBase)selectDc.getComponent();
											String count_KoteiCd = selectCb.getPk1();
											String count_KoteiSeq = selectCb.getPk2();

											//�������ݒ�
											DataCtrl.getInstance().getTrialTblData().NoHaigoGenryo(
													count_KoteiCd, count_KoteiSeq, i);
										}
									}

									//������v�Z
									Trial1.DispGenryohi();

		//2011/05/11 QP@10181_No.42_49_72 TT T.Nishigawa Add Start -------------------------

									//���i��d�A�[�U�ʁA�����[�U�ʁA�����[�U�ʁ@�v�Z����
									Trial1.ZidouKeisan2();

		//2011/05/11 QP@10181_No.42_49_72 TT T.Nishigawa Add End -------------------------

							    //�u�������v����
							    }else if (option == JOptionPane.NO_OPTION){

							    	//�������Ȃ�

							    }
							}
						}

						//---------------------- �����ǉ��{�^�� �N���b�N���̏���  -----------------------
						else if ( event_name == "btnShisakuIns") {
							if(selectCol >= 0){
								HaigoInsShisakuCol(selectCol);
							}
						}

						//---------------------- �����폜�{�^�� �N���b�N���̏���  -----------------------
						else if ( event_name == "btnShisakuDel") {
							if(selectCol >= 0){


//add start -------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.33,34
								//����SEQ�擾
								int intShisakuSeq = 0;
								MiddleCellEditor deleteMc = (MiddleCellEditor)ListHeader.getCellEditor(0, selectCol);
								DefaultCellEditor deleteDc = (DefaultCellEditor)deleteMc.getTableCellEditor(0);
								CheckboxBase CheckboxBase = (CheckboxBase)deleteDc.getComponent();
								intShisakuSeq = Integer.parseInt(CheckboxBase.getPk1());

								//��L�[���ڎ擾
								boolean chk = DataCtrl.getInstance().getTrialTblData().checkShisakuIraiKakuteiFg(intShisakuSeq);

								//�ҏW�\�̏ꍇ�F��������
								if(chk){

								}
								//�ҏW�s�̏ꍇ�F��������
								else{
									DataCtrl.getInstance().getMessageCtrl().PrintMessageString(JwsConstManager.JWS_ERROR_0043);
									return;
								}
//add end   -------------------------------------------------------------------------------

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

	//add start -------------------------------------------------------------------------------
	//QP@00412_�V�T�N�C�b�N���� No.7
							    	//��폜���̊m�F���b�Z�[�W
							    	if(Trial1.AutoCopyKeisanCheck(selectCol)){

							    	}
							    	else{
							    		//�m�F�_�C�A���O�\��
										int option2 = jp.showConfirmDialog(
												jp.getRootPane(),
												JwsConstManager.JWS_ERROR_0047
												, "�m�F���b�Z�[�W"
												,JOptionPane.YES_NO_OPTION
												,JOptionPane.PLAIN_MESSAGE
											);

										//�u�͂��v����
									    if (option2 == JOptionPane.YES_OPTION){
									    	//�������s
									    }
									    //�u�������v����
									    else{
									    	//�����I��
									    	return;
									    }
							    	}
	//add end   -------------------------------------------------------------------------------

							    	//�����폜
							    	HaigoDelShisakuCol(selectCol);

							    //�u�������v����
							    }else if (option == JOptionPane.NO_OPTION){

							    	//�������Ȃ�

							    }
							}
						}

						//-------------------- �����ړ��i���j�{�^�� �N���b�N���̏���  ----------------------
						else if ( event_name == "btnShisakuMove_Left") {
							if(selectCol >= 0){
								moveRetuShisakuCol(selectCol, 0);
							}
						}

						//-------------------- �����ړ��i���j�{�^�� �N���b�N���̏���  ----------------------
						else if ( event_name == "btnShisakuMove_Right") {
							if(selectCol >= 0){
								moveRetuShisakuCol(selectCol, 1);
							}
						}

						//--------------------  �����H���V�K �{�^�� �N���b�N���̏���  -----------------------
						if ( event_name == "sinki") {

							//�����H���p�l���擾
							ManufacturingPanel pb = DataCtrl.getInstance().getManufacturingSubDisp().getManufacturingPanel();

							//2011/06/02 QP@10181_No.31 TT T.Satoh Add Start -------------------------
							//�_�C�A���O�R���|�[�l���g�ݒ�
							JOptionPane jp = new JOptionPane();

							//�I���\�ȃ{�^������ݒ�
							Object[] options = { "OK" };

							//�I������Ă��鍀�ڂ��Ȃ��ꍇ
							if(pb.getMemoText().getText().equals("")){
								//�m�F�_�C�A���O�\��
								int option = jp.showOptionDialog(
										jp.getRootPane(),
										"�u�����H��/���ӎ����v�������͂ł��B"
										, "�m�F���b�Z�[�W"
										,JOptionPane.DEFAULT_OPTION
										,JOptionPane.PLAIN_MESSAGE
										,null
										,options
										,options[0]);
							}
							//2011/06/02 QP@10181_No.31 TT T.Satoh Add End ---------------------------

							//�I������Ă��鍀�ڂ�����ꍇ
							if( !pb.getMemoText().getText().equals("")){

								//�����H���f�[�^�ǉ�
								ManufacturingData md =
									DataCtrl.getInstance().getTrialTblData().AddSeizoKouteiData(
										pb.getShisakuSeq(),
										pb.getTyuuiNo(),
										DataCtrl.getInstance().getTrialTblData().checkNullString(pb.getMemoText().getText()),
										DataCtrl.getInstance().getUserMstData().getDciUserid()
									);

								//2011/05/31 QP@10181_No.31 TT T.Satoh Add Start -------------------------
								//�m�F�_�C�A���O�\��
								int option = jp.showOptionDialog(
										jp.getRootPane(),
										"�V�K�쐬����܂����B"
										, "�m�F���b�Z�[�W"
										,JOptionPane.DEFAULT_OPTION
										,JOptionPane.PLAIN_MESSAGE
										,null
										,options
										,options[0]);

								//�H���ŕ\��
								pb.getLabel()[1].setText("�H���ŁF" + md.getIntTyuiNo());
								//2011/05/31 QP@10181_No.31 TT T.Satoh Add End -------------------------

								//�I���f�[�^�ݒ�
								pb.setTyuuiNo(md.getIntTyuiNo());

								//�R���{�{�b�N�X�ǉ�
								for(int i=0; i<Trial1.getListHeader().getColumnCount(); i++){

									//�R���|�[�l���g�擾
									MiddleCellEditor selectMc = (MiddleCellEditor)Trial1.getListHeader().getCellEditor(1, i);
									DefaultCellEditor selectDc = (DefaultCellEditor)selectMc.getTableCellEditor(1);
									ComboBase cb = (ComboBase)selectDc.getComponent();

									//�I��index�擾
									int selectIndex = cb.getSelectedIndex();

									//�Z���G�f�B�^�I��l�ύX
									cb.addItem(Integer.toString(md.getIntTyuiNo()));
									//2011/05/31 QP@10181_No.31 TT T.Satoh Change Start -------------------------
									//cb.setSelectedItem(Integer.toString(md.getIntTyuiNo()));
									//2011/05/31 QP@10181_No.31 TT T.Satoh Change End ---------------------------

									//�Z�������_���I��l�ύX
									MiddleCellRenderer selectMr = (MiddleCellRenderer)Trial1.getListHeader().getCellRenderer(1, i);
									ComboBoxCellRenderer selectCer = (ComboBoxCellRenderer)selectMr.getTableCellRenderer(1);
									selectCer.addItem(Integer.toString(md.getIntTyuiNo()));
									selectCer.setSelectedIndex(selectIndex);
								}
							}

							//�e�X�g�\��
							//DataCtrl.getInstance().getTrialTblData().dispManufacturingData();
						}
						//--------------------- �����H���X�V �{�^�� �N���b�N���̏���  -----------------------
						if ( event_name == "kosin") {

					    	//2011/05/26 QP@10181_No.31 TT T.Satoh Add Start -------------------------
							//�m�F�_�C�A���O�̃{�^�������C�x���g�擾�p
							int option = 0;

							//�_�C�A���O�R���|�[�l���g�ݒ�
							JOptionPane jp = new JOptionPane();

							//�I���\�ȃ{�^������ݒ�
							Object[] options = { "OK" };
							//2011/05/26 QP@10181_No.31 TT T.Satoh Add End ---------------------------

							//�����H���p�l���擾
							ManufacturingPanel pb = DataCtrl.getInstance().getManufacturingSubDisp().getManufacturingPanel();
							int select = pb.getCombo().getSelectedIndex();

							//�����H��&���ӎ����X�V
							if(select == 0){

								//�I������Ă��鍀�ڂ�����ꍇ
								if( pb.getTyuuiNo()>0 ){

									//2011/05/27 QP@10181_No.31 TT T.Satoh Add Start -------------------------
									//�m�F�_�C�A���O�\��
									option = jp.showOptionDialog(
											jp.getRootPane(),
											"�X�V����܂����B"
											, "�m�F���b�Z�[�W"
											,JOptionPane.DEFAULT_OPTION
											,JOptionPane.PLAIN_MESSAGE
											,null
											,options
											,options[0]);
									//2011/05/27 QP@10181_No.31 TT T.Satoh Add End ---------------------------

									//�����H���f�[�^�X�V
									DataCtrl.getInstance().getTrialTblData().UpdSeizoKouteiData(
											pb.getTyuuiNo(),
											DataCtrl.getInstance().getTrialTblData().checkNullString(pb.getMemoText().getText()),
											DataCtrl.getInstance().getUserMstData().getDciUserid()
										);
								}
								//2011/05/27 QP@10181_No.31 TT T.Satoh Add Start -------------------------
								//�I������Ă��鍀�ڂ��Ȃ��ꍇ
								else {
									//�m�F�_�C�A���O�\��
									option = jp.showOptionDialog(
											jp.getRootPane(),
											"�H���ł�I�����ĉ������B"
											, "�m�F���b�Z�[�W"
											,JOptionPane.DEFAULT_OPTION
											,JOptionPane.PLAIN_MESSAGE
											,null
											,options
											,options[0]);
								}
								//2011/05/27 QP@10181_No.31 TT T.Satoh Add End ---------------------------
							}else if(select == 1){

								//2011/05/27 QP@10181_No.31 TT T.Satoh Add Start -------------------------
								//�m�F�_�C�A���O�\��
								option = jp.showOptionDialog(
										jp.getRootPane(),
										"�X�V����܂����B"
										, "�m�F���b�Z�[�W"
										,JOptionPane.DEFAULT_OPTION
										,JOptionPane.PLAIN_MESSAGE
										,null
										,options
										,options[0]);
								//2011/05/27 QP@10181_No.31 TT T.Satoh Add End ---------------------------

								// �yKPX1500671�z ADD start
								//���상���X�V
								String strBefMemo = DataCtrl.getInstance().getTrialTblData().SearchShisakuhinData().getStrShisakuMemo();
								String strAftMemo = pb.getMemoText().getText();
								if(null != strBefMemo && !strBefMemo.equals(strAftMemo)){
									//�f�[�^�ύX�t���O�n�m
									DataCtrl.getInstance().getTrialTblData().setHenkouFg(true);
								}
								// �yKPX1500671�z ADD end
								
								DataCtrl.getInstance().getTrialTblData().SearchShisakuhinData().setStrShisakuMemo(pb.getMemoText().getText());

							}
						}
						//----------------- ����R�s�[��ʁ@�̗p�{�^���N���b�N���̏��� -----------------------
						if ( event_name == "saiyou") {
							ShisakuSaiyou(selectCol);

							//������v�Z
							Trial1.DispGenryohi();
						}
						//-------------- ����R�s�[��ʁ@�L�����Z���{�^���N���b�N���̏��� ---------------------
						if(event_name == "cansel"){
							prototypeListSubDisp.setVisible(false);
						}

					} catch (ExceptionBase eb) {
						//���b�Z�[�W�\��
						DataCtrl.getInstance().PrintMessage(eb);

					} catch (Exception ec) {
						//�G���[�ݒ�
						ex = new ExceptionBase();
						ex.setStrErrCd("");
						//2011/04/12 QP@10181_No.26 TT T.Satoh Change Start -------------------------
						//ex.setStrErrmsg("����f�[�^��� ����\�@ �{�^���������������s���܂���");
						ex.setStrErrmsg("����f�[�^��� �z���\ �{�^���������������s���܂���");
						//2011/04/12 QP@10181_No.26 TT T.Satoh Change End ---------------------------
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

	/************************************************************************************
	 *
	 *   ����R�s�[��ʁ@�̗p����
	 *   @author TT nishigawa
	 *   @throws ExceptionBase
	 *
	 ************************************************************************************/
	public void ShisakuSaiyou(int col) throws ExceptionBase{
		try{

//add start -------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.35
			//�����n�̌v�Z���i�[�p�isampleNo�j
			String strSampleNo = "";
			//�����n�̌v�Z���i�[�p�i�v�Z���j
			String strSampleKeisan = "";
			//�v�Z���Ɏg�p���������P�񕪂̌v�Z���isampleNo�j
			String strKeisanShiki = "";
			//�v�Z���Ɏg�p���������P�񕪂̌v�Z���i�v�Z���j
			String strKeisanShiki_keisan = "";
//add end   -------------------------------------------------------------------------------

			//------------------------------ �������� --------------------------------------
			//����R�s�[�p�l���擾
			PrototypeListPanel pp = prototypeListSubDisp.getPrototypeListPanel();

			//����R�s�[�e�[�u���擾
			PrototypeListTable pt = pp.getPrototypeListTable();

			//�I������
			TableBase mainTable = pt.getMainTable();
			TableCellEditor tce = mainTable.getCellEditor();
			if(tce != null){
				mainTable.getCellEditor().stopCellEditing();
			}

			//�����t���O
			boolean chkAddRetu = true;

			//---------------------------- �H�����`�F�b�N ------------------------------------
			int dataRows = DataCtrl.getInstance().getTrialTblData().getIntMaxKotei();
			int tableRows = pt.getMaxKotei();
			if(dataRows != tableRows){
				chkAddRetu = false;
			}

			//--------------------------- ����񐔃`�F�b�N -----------------------------------
			int dataCols = DataCtrl.getInstance().getTrialTblData().SearchShisakuRetuData(0).size();
			MiddleCellEditor mce = (MiddleCellEditor)pt.getComboTable().getCellEditor(0, 0);
			DefaultCellEditor dce = (DefaultCellEditor)mce.getTableCellEditor(0);
			ComboBase cb = (ComboBase)dce.getComponent();
			int tableCols = cb.getItemCount();
			if(dataCols != tableCols){
				chkAddRetu = false;
			}

			//�����10�񖢖��̏ꍇ�ɒǉ�
			if(pp.getRadioButton()[0].isSelected()){
				int maxcol = pt.getHeaderTable().getColumnCount();
				int chkCol = 10;
				if(maxcol > chkCol){
					DataCtrl.getInstance().getMessageCtrl().PrintMessageString("��ǉ���" + chkCol + "��܂łł��B");
					return;
				}
			}

			//-------------------------- �����I���`�F�b�N ----------------------------------
			if(col >= 0){

				//�����v�Z
				if(chkAddRetu){

					//�e�[�u����ǉ�
					HaigoInsShisakuCol(col);

					//�Ώۗ�
					int insCol = col+1;

					//�v�Z��
					int keisanCol = pt.getHeaderTable().getColumnCount();

					//�H����
					int koteiNo = -1;

					//�����w��擾
					//���e�����f�[�^�擾
					ArrayList aryLiteralCd = DataCtrl.getInstance().getLiteralDataShosu().getAryLiteralCd();
					ArrayList aryLiteralVal = DataCtrl.getInstance().getLiteralDataShosu().getAryValue1();

					//�I���f�[�^�擾
					String SelShosu = DataCtrl.getInstance().getTrialTblData().SearchShisakuhinData().getStrShosu();
					int shosu = 0;

					//�I�����e�����R�[�h�̖��̎擾
					for(int k=0; k<aryLiteralCd.size(); k++){

						//���e�����R�[�h�ƒl�P�@�擾
						String strLiteralCd = (String)aryLiteralCd.get(k);
						String strLiteralVal = (String)aryLiteralVal.get(k);

						//�I�����Ă��鏬���w��̃��e�����l�P�@�擾
						if(SelShosu != null && SelShosu.length() > 0){
							if(Integer.parseInt(SelShosu) == Integer.parseInt(strLiteralCd)){
								try{
									//�����w��擾
									shosu = Integer.parseInt(strLiteralVal);

								}catch(Exception e){
									//��O����0��}��
									shosu = 0;

								}
							}
						}
					}

					//�z���s���[�v
					TableBase HaigoMeisai = Trial1.getHaigoMeisai();
					for(int i=0; i<HaigoMeisai.getRowCount(); i++){

						//�R���|�[�l���g�擾
						MiddleCellEditor mceHaigo = (MiddleCellEditor)HaigoMeisai.getCellEditor(i, 2);
						DefaultCellEditor dceHaigo = (DefaultCellEditor)mceHaigo.getTableCellEditor(i);

						//�����s�F�v�Z
						if(dceHaigo.getComponent() instanceof CheckboxBase){


							//�R�����g�s�̏ꍇ
							String Cd = (String)HaigoMeisai.getValueAt(i, 3);
							int keta = DataCtrl.getInstance().getTrialTblData().getKaishaGenryo();
							if(DataCtrl.getInstance().getTrialTblData().commentChk(Cd, keta)){

							}
							//�R�����g�s�łȂ��ꍇ
							else{

								//�R���|�[�l���g�擾
								CheckboxBase cbHaigo = (CheckboxBase)dceHaigo.getComponent();

								//�L�[���ڎ擾
								int koteiCd = Integer.parseInt(cbHaigo.getPk1());
								int koteiSeq = Integer.parseInt(cbHaigo.getPk2());

								//�v�Z�񃋁[�v
								TableBase ListHeader = Trial1.getListHeader();
								int combCount = 0;
								int mainCount = 0;
								String Ans = null;
								boolean nullFg = false;

								//�񐔕����[�v
								for(int j=0; j<keisanCol; j++){

									//����R�s�[��ʁ@����I���R���{�{�b�N�X�擾
									MiddleCellEditor mceKeisan = (MiddleCellEditor)pt.getComboTable().getCellEditor(0, combCount);
									DefaultCellEditor dceKeisan = (DefaultCellEditor)mceKeisan.getTableCellEditor(0);
									ComboBase cbKeisan = (ComboBase)dceKeisan.getComponent();

//add start -------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.35
									//�I�����쏇�擾�i�I�𕶎����u�F�i�R�����j�v�ɂĐ؂���j
									//int ShisakuNo = Integer.parseInt(cbKeisan.getSelectedItem().toString().split("�F")[0]);
									int ShisakuNo = cbKeisan.getSelectedIndex() + 1;

									//�v�Z�����̎g�p������SampleNo�̎擾
									try{
										strKeisanShiki = cbKeisan.getSelectedItem().toString();
									}catch(Exception e){
										strKeisanShiki = "";
									}
									if(JwsConstManager.JWS_COPY_0002){
										//�R�s�[��v�Z�t���O��True�̏ꍇSample�������ʂł�����
										strKeisanShiki = JwsConstManager.JWS_COPY_0003 + strKeisanShiki + JwsConstManager.JWS_COPY_0004;
									}
//add end   -------------------------------------------------------------------------------

									//����SEQ�擾�i����No��菈�������肵�A���̗�̎���SEQ���擾�j
									if(ShisakuNo > insCol){
										ShisakuNo += 1;
									}
									MiddleCellEditor mceHeader = (MiddleCellEditor)ListHeader.getCellEditor(0, ShisakuNo-1);
									DefaultCellEditor dceHeader = (DefaultCellEditor)mceHeader.getTableCellEditor(0);
									CheckboxBase cbHeader = (CheckboxBase)dceHeader.getComponent();

									//�L�[���ڎ擾�i����SEQ�j
									int shisakuSeq = Integer.parseInt(cbHeader.getPk1());

//add start -------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.7
									if(JwsConstManager.JWS_COPY_0002){
										String keisan = DataCtrl.getInstance().getTrialTblData().SearchShisakuKeisanSiki(shisakuSeq);
										//�v�Z�����Ȃ��ꍇ
										if(keisan == null || keisan.length() == 0){
											//����SEQ�����ʂł�����
											strKeisanShiki_keisan = JwsConstManager.JWS_COPY_0003 + JwsConstManager.JWS_COPY_0005
																			+ Integer.toString(shisakuSeq) + JwsConstManager.JWS_COPY_0004;
										}else{
											//�v�Z�������ʂł�����
											strKeisanShiki_keisan = JwsConstManager.JWS_COPY_0003 + keisan + JwsConstManager.JWS_COPY_0004;
										}
									}
//add end   -------------------------------------------------------------------------------

									//���X�g�l�擾
									TableBase ListMeisai = Trial1.getListMeisai();
									String listValue = (String)ListMeisai.getValueAt(i, ShisakuNo-1);

									//Null�`�F�b�N
									String strSiki = "";
									String strAtai = "";
									String strRetuSiki ="";

//add start -------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.7
									String strSiki_keisan = "";
//add end   -------------------------------------------------------------------------------

									//���l�f�[�^�łȂ��ꍇ�́u0�v��}��
									try{

										new BigDecimal(listValue);

									}catch(Exception e){

										listValue = "0";

									}

									//BigDecimal�^�ɕϊ�
									BigDecimal deciValue = new BigDecimal(listValue);

									//�H���v�Z���擾
									MiddleCellEditor mceSiki = (MiddleCellEditor)pt.getMainTable().getCellEditor(koteiNo, mainCount+1);
									DefaultCellEditor dceSiki = (DefaultCellEditor)mceSiki.getTableCellEditor(koteiNo);
									ComboBase cbSiki = (ComboBase)dceSiki.getComponent();
									strSiki = (String)cbSiki.getSelectedItem();

									//�H���v�Z�l�擾
									strAtai = (String)pt.getMainTable().getValueAt(koteiNo, mainCount+2);

									//�v�Z
									if(strAtai != null && strAtai.length()>0){
										//�v�Z�l��BigDecimal�^�ɕϊ�
										BigDecimal deciAtai = new BigDecimal(strAtai);

										//�|���Z
										if(strSiki.equals("�~")){
											deciValue = deciValue.multiply(deciAtai);

//add start -------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.35
											strSiki = "*";
											strSiki_keisan = "*";
//add end   -------------------------------------------------------------------------------

										//����Z
										}else if(strSiki.equals("��")){
											deciValue = deciValue.divide(deciAtai, BigDecimal.ROUND_HALF_UP);

//add start -------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.35
											strSiki = "/";
											strSiki_keisan = "/";
//add end   -------------------------------------------------------------------------------

										}

//add start -------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.35
										//�I���v�Z���Z�q�ƑI��������SampleNo�A��
										strKeisanShiki = strKeisanShiki + strSiki + deciAtai;
										//�I���v�Z���Z�q�ƑI�������̌v�Z���A��
										strKeisanShiki_keisan = strKeisanShiki_keisan + strSiki_keisan + deciAtai;
//add end   -------------------------------------------------------------------------------

									}else{
										Ans = null;
										nullFg = true;
									}

									//����
									if(j == 0){
										//������f�[�^�֊i�[
										//deciValue.setScale(scale);
										Ans = deciValue.toString();
										//�e�X�g�\��
										//System.out.println(koteiCd + "," + koteiSeq+"," + shisakuSeq+","+listValue+strSiki+strAtai+"="+Ans);

									//2��ڈȍ~
									}else{
										//�v�Z�Ώۂ�BigDecimal�^�ɕϊ�
										if(Ans != null && Ans.length() > 0){
											BigDecimal keisanValue = new BigDecimal(Ans);

											//�����v�Z���擾
											MiddleCellEditor mceRetuSiki = (MiddleCellEditor)pt.getComboTable().getCellEditor(0, combCount-1);
											DefaultCellEditor dceRetuSiki = (DefaultCellEditor)mceRetuSiki.getTableCellEditor(0);
											ComboBase cbRetuSiki = (ComboBase)dceRetuSiki.getComponent();
											strRetuSiki = (String)cbRetuSiki.getSelectedItem();

											//�����Z
											if(strRetuSiki.equals("+")){
												keisanValue = keisanValue.add(deciValue);
											//�����Z
											}else if(strRetuSiki.equals("-")){
												keisanValue = keisanValue.subtract(deciValue);
											}

//add start -------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.35
											//�I���v�Z���Z�q�ƌv�Z���̘A���i�T���v��No�j
											strKeisanShiki = strSampleNo + strRetuSiki + strKeisanShiki;
											//�I���v�Z���Z�q�ƌv�Z���̘A���i�v�Z���j
											strKeisanShiki_keisan = strSampleKeisan + strRetuSiki + strKeisanShiki_keisan;
//add end   -------------------------------------------------------------------------------

											//�e�X�g�\��
											//System.out.println(koteiCd + "," + koteiSeq+"," + shisakuSeq+","+Ans+strRetuSiki+"("+listValue+strSiki+strAtai+"="+deciValue.toString()+")="+keisanValue.toString());

											//������f�[�^�֊i�[
											Ans = keisanValue.toString();
										}
									}

									//�R���{�e�[�u����J�E���g
									combCount += 2;
									//���C���e�[�u����J�E���g
									mainCount+=3;

//add start -------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.35
									//�v�Z���̊i�[�i�T���v��No�j
									strSampleNo = strKeisanShiki;
									//�v�Z���̊i�[�i�v�Z���j
									strSampleKeisan = strKeisanShiki_keisan;
//add end   -------------------------------------------------------------------------------

								}

								//�v�Z���ʂ��e�[�u���\��
								TableBase ListMeisai = Trial1.getListMeisai();
								ListMeisai.setValueAt(Trial1.ShosuArai(Ans), i, insCol);
								//ListMeisai.setValueAt(Ans, i, insCol);

								//�v�Z���ʂ��f�[�^�}��
								//����SEQ�擾
								MiddleCellEditor mceSeq = (MiddleCellEditor)ListHeader.getCellEditor(0, insCol);
								DefaultCellEditor dceSeq = (DefaultCellEditor)mceSeq.getTableCellEditor(0);
								CheckboxBase cbSeq = (CheckboxBase)dceSeq.getComponent();
								int listSeq = Integer.parseInt(cbSeq.getPk1());
								//�f�[�^�}��
								DataCtrl.getInstance().getTrialTblData().UpdShisakuListRyo(
										listSeq,
										koteiCd,
										koteiSeq,
										DataCtrl.getInstance().getTrialTblData().checkNullDecimal(Trial1.ShosuArai(Ans))
									);
							}

						//�H���s
						}else{
							//�v�Z���I������Ă���ꍇ
							if(pp.getRadioButton()[1].isSelected()){
								koteiNo++;
							//�S�H�����I������Ă���ꍇ
							}else{
								koteiNo = 0;
							}
						}
					}

//add start -------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.35
					//�S�H�����I������Ă���ꍇ
					if(pp.getRadioButton()[0].isSelected()){

						//�쐬����������SampleNo�̐ݒ� ---------------------------
						//�\���l�ݒ�
						TableBase ListHeader = Trial1.getListHeader();

						//�L�[���ڎ擾
						MiddleCellEditor mceSeq = (MiddleCellEditor)ListHeader.getCellEditor(0, insCol);
						DefaultCellEditor dceSeq = (DefaultCellEditor)mceSeq.getTableCellEditor(0);
						CheckboxBase chkSeq = (CheckboxBase)dceSeq.getComponent();
						int intSeq  = Integer.parseInt(chkSeq.getPk1());

						//�l�̐ݒ�
						ListHeader.setValueAt(strSampleNo, 3, insCol);

						//�f�[�^�}��
						DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuSampleNo(
								intSeq,
								strSampleNo,
								DataCtrl.getInstance().getUserMstData().getDciUserid()
							);

						//�쐬��������f�[�^�Ɍv�Z����ݒ� ---------------------------
						//�f�[�^�}��
						DataCtrl.getInstance().getTrialTblData().UpdShisakuKeisanSiki(
								intSeq,
								strSampleKeisan
							);

	//add start ----------------------------------------------------------------------------
	//QP@00412_�V�T�N�C�b�N���� No.23
						//���얾�׃e�[�u���擾
						TableBase listMeisai = this.getTrial1().getListMeisai();

						//�ő�H�����擾
						int maxKotei = DataCtrl.getInstance().getTrialTblData().getIntMaxKotei();

						//���v�d��d�ʍs
						//2011/06/13 QP@10181_No.29 TT T.Satoh Change Start -------------------------
						//int keisanRow = listMeisai.getRowCount()-7;
						int keisanRow = listMeisai.getRowCount()-8;
						//2011/06/13 QP@10181_No.29 TT T.Satoh Change End ---------------------------


						//�z���f�[�^�z��擾
						ArrayList aryShisaku = DataCtrl.getInstance().getTrialTblData().SearchShisakuRetuData(intSeq);

						//�T���v��NO�擾
						String sampleKeisan = ((TrialData)aryShisaku.get(0)).getStrKeisanSiki();

						//�v�Z���ϊ�
						String keisanSiki = DataCtrl.getInstance().getTrialTblData().changeKeisanLogic( sampleKeisan , 0 );
						String keisan = DataCtrl.getInstance().getTrialTblData().getKeisanShisakuSeqSiagari( keisanSiki);


						//QP@20505 2012/10/26 No1 Add start
						int koteiShiagariRow = keisanRow - maxKotei;

						for(int i = 0; i < maxKotei; i++){
							//2013/04/01 MOD Start
//							int intKoteiCode = DataCtrl.getInstance().getTrialTblData().getSerchKoteiCode(insCol, i + 1);
							int intKoteiCode = DataCtrl.getInstance().getTrialTblData().getSerchKoteiCode(intSeq, i + 1);
							//2013/04/01 MOD End

							String koteiKeisanSiki = DataCtrl.getInstance().getTrialTblData().changeKeisanLogic( sampleKeisan , 0 );

							//�v�Z���擾
							String keisana = DataCtrl.getInstance().getTrialTblData().getKeisanShisakuSeqKoteiSiagari( koteiKeisanSiki ,i + 1);

							//�v�Z���s
							String strKekka = DataCtrl.getInstance().getTrialTblData().execKeisan(keisana);

							//�������ցi�H���d��d�ʁj
							if(strKekka != null && strKekka.length() > 0){
								//���֏���
								strKekka = this.getTrial1().ShosuAraiHulfUp_keta(strKekka,"4");
							}

							//�l�̐ݒ�
							listMeisai.setValueAt(strKekka, koteiShiagariRow + i, insCol);

							DataCtrl.getInstance().getTrialTblData().UpdKouteiShiagari(
									intSeq,
									intKoteiCode,
									DataCtrl.getInstance().getTrialTblData().checkNullDecimal(strKekka),
									DataCtrl.getInstance().getUserMstData().getDciUserid()
								);

						}
						//QP@20505 2012/10/26 No1 Add End

						//�v�Z���s
						String strKekka = DataCtrl.getInstance().getTrialTblData().execKeisan(keisan);

						//�������ցi���v�d��d�ʁj
						if(strKekka != null && strKekka.length() > 0){
							//���֏���
							strKekka = this.getTrial1().ShosuAraiHulfUp_keta(strKekka,"4");
						}

						//�l�̐ݒ�
						listMeisai.setValueAt(strKekka, keisanRow, insCol);

						//�f�[�^�}��
						DataCtrl.getInstance().getTrialTblData().UpdShiagariRetuDate(
								intSeq,
								DataCtrl.getInstance().getTrialTblData().checkNullDecimal(strKekka),
								DataCtrl.getInstance().getUserMstData().getDciUserid()
							);

	//add end   ---------------------------------------------------------------------------

					}
//add end   -------------------------------------------------------------------------------

					//�H�����v�v�Z
					Trial1.koteiSum(col+1);

					//�����v�Z
					Trial1.AutoKeisan();

					//��\������
					prototypeListSubDisp.setVisible(false);

				}else{
					//�G���[�ݒ�
					ex = new ExceptionBase();
					ex.setStrErrCd("");
					ex.setStrErrmsg("����\�̏�񂪕ύX����Ă��܂��B�u�����R�s�[�v�{�^�����������A�ēx�ݒ肵�ĉ������B");
					ex.setStrErrShori(this.getClass().getName());
					ex.setStrMsgNo("");
					ex.setStrSystemMsg("");
					throw ex;

				}

			}else{
				//�G���[�ݒ�
				ex = new ExceptionBase();
				ex.setStrErrCd("");
				//2011/04/12 QP@10181_No.26 TT T.Satoh Change Start -------------------------
				//ex.setStrErrmsg("����񂪑I������Ă��܂���B����\�@��莎����I�����ĉ������B");
				ex.setStrErrmsg("����񂪑I������Ă��܂���B�z���\��莎����I�����ĉ������B");
				//2011/04/12 QP@10181_No.26 TT T.Satoh Change End ---------------------------
				ex.setStrErrShori(this.getClass().getName());
				ex.setStrMsgNo("");
				ex.setStrSystemMsg("");
				throw ex;

			}

		}catch(ExceptionBase eb){
			throw eb;

		}catch(ArithmeticException ae){
			//�ǉ�����폜
			HaigoDelShisakuCol(col+1);

			//�G���[�ݒ�
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			ex.setStrErrmsg("0���Z�͂ł��܂���B");
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(ae.getMessage());
			throw ex;

		}catch(Exception e){

			e.printStackTrace();

			//�ǉ�����폜
			HaigoDelShisakuCol(col+1);

			//�G���[�ݒ�
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			ex.setStrErrmsg("����R�s�[�@�̗p���������s���܂����B");
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg("");
			throw ex;

		}finally{
			//�e�X�g�\��
			//DataCtrl.getInstance().getTrialTblData().dispTrial();
			//DataCtrl.getInstance().getTrialTblData().dispProtoList();

		}
	}

	/************************************************************************************
	 *
	 *   �����s�}��
	 *    : �z�����ׁA���샊�X�g�e�[�u���֍s��}������
	 *   @author TT nishigawa
	 *   @parm   maxRow      : �ǉ��s
	 *   @parm   pk_KoteiCd  : �H��CD
	 *   @parm   pk_KoteiSeq : �H��SEQ
	 *   @throws ExceptionBase
	 *
	 ************************************************************************************/
	public void HaigoInsertRow(int maxRow,String pk_KoteiCd,String pk_KoteiSeq) throws ExceptionBase{
		try{
			//�e�[�u���擾
			TableBase HaigoMeisai = Trial1.getHaigoMeisai();
			TableBase ListMeisai = Trial1.getListMeisai();

			//�}���s
			int moveRow = maxRow+1;

			//�z���f�[�^�����샊�X�g�f�[�^�ǉ�
			ArrayList chkHaigo =
				DataCtrl.getInstance().getTrialTblData().SearchHaigoData(Integer.parseInt(pk_KoteiCd));
			MixedData addMixedData = new MixedData();


			if(chkHaigo.size() == 0){

				//�R���|�[�l���g�擾�i�����I���`�F�b�N�{�b�N�X�j
				MiddleCellEditor selectMc = (MiddleCellEditor)HaigoMeisai.getCellEditor(maxRow, 0);
				DefaultCellEditor selectDc = (DefaultCellEditor)selectMc.getTableCellEditor(maxRow);
				CheckboxBase CheckboxBase = (CheckboxBase)selectDc.getComponent();

				addMixedData = DataCtrl.getInstance().getTrialTblData().AddHaigoGenryo(
						pk_KoteiCd,
						pk_KoteiSeq,
						Integer.parseInt(CheckboxBase.getPk3()),
						CheckboxBase.getPk4(),
						CheckboxBase.getPk5()
					);

			}else{

				addMixedData = DataCtrl.getInstance().getTrialTblData().AddHaigoGenryo(pk_KoteiCd);
			}

			pk_KoteiCd = Integer.toString(addMixedData.getIntKoteiCd());
			pk_KoteiSeq = Integer.toString(addMixedData.getIntKoteiSeq());

			//�z�����ׂ̑I�������N���A
			HaigoMeisai.clearSelection();
			TableCellEditor hmEditor = HaigoMeisai.getCellEditor();
			if(hmEditor != null){
				HaigoMeisai.getCellEditor().stopCellEditing();
			}

			//�z�����ׂ֍s�ǉ�
			HaigoMeisai.tableInsertRow(moveRow);
			Trial1.addHaigoMeisaiRowER(moveRow, 0, pk_KoteiCd, pk_KoteiSeq,Integer.parseInt(addMixedData.getStrIro()));
			Trial1.setHaigoMeisaiER();

			//�z���e�[�u���֍H�����\��
			HaigoMeisai.setValueAt(Integer.toString(addMixedData.getIntKoteiNo()), moveRow, 1);

			//���X�g���ׂ̑I�������N���A
			ListMeisai.clearSelection();
			TableCellEditor lmEditor = ListMeisai.getCellEditor();
			if(lmEditor != null){
				ListMeisai.getCellEditor().stopCellEditing();
			}

			//���X�g���ׂ֍s�ǉ�
			ListMeisai.tableInsertRow(moveRow);
			for(int i=0; i<ListMeisai.getColumnCount(); i++){
				Trial1.addListShisakuRowER(i, moveRow, 0);
			}


			Trial1.setListShisakuER();

			//�z���f�[�^�������ݒ�
			for(int i=0; i<HaigoMeisai.getRowCount(); i++){

				//�R���|�[�l���g�擾�i�����I���`�F�b�N�{�b�N�X�j
				MiddleCellEditor selectMc = (MiddleCellEditor)HaigoMeisai.getCellEditor(i, 2);
				DefaultCellEditor selectDc = (DefaultCellEditor)selectMc.getTableCellEditor(i);

				//�����I���`�F�b�N�{�b�N�X�̏ꍇ
				if(((JComponent)selectDc.getComponent()) instanceof CheckboxBase){

					//�R���|�[�l���g���ΏۍH��CD�A�H��SEQ�擾
					CheckboxBase selectCb = (CheckboxBase)selectDc.getComponent();
					String count_KoteiCd = selectCb.getPk1();
					String count_KoteiSeq = selectCb.getPk2();

					//�������ݒ�
					DataCtrl.getInstance().getTrialTblData().NoHaigoGenryo(
							count_KoteiCd, count_KoteiSeq, i);
				}
			}

			//�H�����v�v�Z
			for(int col=0; col<Trial1.getListMeisai().getColumnCount(); col++){
				Trial1.koteiSum(col);
			}
			//�����v�Z
			Trial1.AutoKeisan();

		} catch (ExceptionBase e) {
			throw e;

		} catch (Exception e) {
			//�G���[�ݒ�
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			//2011/04/12 QP@10181_No.26 TT T.Satoh Change Start -------------------------
			//ex.setStrErrmsg("����f�[�^��� ����\�@ �����s�}�����������s���܂���");
			ex.setStrErrmsg("����f�[�^��� �z���\ �����s�}�����������s���܂���");
			//2011/04/12 QP@10181_No.26 TT T.Satoh Change End ---------------------------
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {
			//�e�X�g�\��
			//DataCtrl.getInstance().getTrialTblData().dispHaigo();
			//DataCtrl.getInstance().getTrialTblData().dispProtoList();

		}
	}

	/************************************************************************************
	 *
	 *   �����s�ړ�
	 *    : �z�����ׁA���샊�X�g�e�[�u���̍s���ړ�����
	 *   @author TT nishigawa
	 *   @param move : �ړ����i1 or -1�j
	 *   @throws ExceptionBase
	 *
	 ************************************************************************************/
	public void HaigoMoveRow(int move) throws ExceptionBase{
		try{
			//�z���f�[�^�擾
			ArrayList retuData = DataCtrl.getInstance().getTrialTblData().SearchHaigoData(0);
			//�ő�H�����擾
			int maxKotei = DataCtrl.getInstance().getTrialTblData().getIntMaxKotei();

			ArrayList AryGenryoCheck = Trial1.getAryGenryoCheck();
			int count = AryGenryoCheck.size();
			//2011/06/13 QP@10181_No.29 TT T.Satoh Change Start -------------------------
			//int keisanRow = maxKotei+8;
			int keisanRow = maxKotei+9;
			//2011/06/13 QP@10181_No.29 TT T.Satoh Change End ---------------------------
			String pk_KoteiCd_moto = "";
			String pk_KoteiSeq_moto = "";
			String pk_KoteiCd_saki = "";
			String pk_KoteiSeq_saki = "";

			//�I������Ă��錴��������ꍇ
			if(count > 0){
				//�e�[�u���擾
				TableBase HaigoMeisai = Trial1.getHaigoMeisai();
				TableBase ListMeisai = Trial1.getListMeisai();
				//�I�����z��i�s�ԍ��̏��������̏��j
				ArrayList sort = new ArrayList();
				for(int i=0; i<count; i++){
					//�I���s�ԍ��擾
					int row = ((int[])AryGenryoCheck.get(i))[0];
					//�\�[�g�i2��ڈȍ~�j
					if(sort.size() > 0){
						//sort�z�����1�����ɔ�r
						int index = sort.size();
						for(int j=0; j<sort.size(); j++){
							int tag = Integer.parseInt((String)sort.get(j));
							//�s�ԍ��̐����������ꍇ
							if(row < tag){
								index = j;
								j = sort.size();
							}
						}
						sort.add(index, Integer.toString(row));
					//�\�[�g�i����j
					}else{
						sort.add(Integer.toString(row));
					}
				}

				//�z���A���샊�X�g�s�ړ�
				int move_count;
				if(move < 0){

					//�s�ԍ��̏��������̂��ړ�
					move_count = 0;

				}else{

					//�s�ԍ��̑傫�����̂��ړ�
					move_count = sort.size()-1;
				}

				for(int i=0; i<sort.size();i++){

					int row = Integer.parseInt((String)sort.get(move_count));
					int row_move = row+move;
					int row_bef = -1;

					//�ړ���̍s�ԍ��擾
					if(move < 0){

						if(move_count > 0){

							row_bef = Integer.parseInt((String)sort.get(move_count-1));
						}

					}else{

						if(move_count < sort.size()-1){
							row_bef = Integer.parseInt((String)sort.get(move_count+1));
						}

					}

					//�z�����ׂ̑I�������N���A
					HaigoMeisai.clearSelection();
					TableCellEditor hmEditor = HaigoMeisai.getCellEditor();
					if(hmEditor != null){
						HaigoMeisai.getCellEditor().stopCellEditing();
					}

					//���X�g���ׂ̑I�������N���A
					ListMeisai.clearSelection();
					TableCellEditor lmEditor = ListMeisai.getCellEditor();
					if(lmEditor != null){
						ListMeisai.getCellEditor().stopCellEditing();
					}

					//�ړ��͈͂̌��E�l��ݒ�
					int move_limit;

					if(move < 0){

						//�擪�s�̐ݒ�
						move_limit = 1;
					}else{

						//�ŏI�s�̐ݒ�
						move_limit = HaigoMeisai.getRowCount()-keisanRow-1;
					}
					//�ړ��悪�擪�E�ŏI�s�łȂ��ꍇ�A���ړ��悪�I���s�ԍ��ł͂Ȃ��ꍇ
					if(row != move_limit && row_bef != row_move){

						//�L�[���ڎ擾�ړ���
						MiddleCellEditor mc_moto = (MiddleCellEditor)HaigoMeisai.getCellEditor(row, 2);
						DefaultCellEditor tc_moto = (DefaultCellEditor)mc_moto.getTableCellEditor(row);
						CheckboxBase cb_moto = (CheckboxBase)tc_moto.getComponent();
						pk_KoteiCd_moto  = cb_moto.getPk1();
						pk_KoteiSeq_moto = cb_moto.getPk2();

						//�H�����ύX�m�F
						MiddleCellEditor mc = (MiddleCellEditor)HaigoMeisai.getCellEditor(row_move, 0);
						DefaultCellEditor tc = (DefaultCellEditor)mc.getTableCellEditor(row_move);

						boolean kotei_move_flg = false; //�H���ύXFlg
						if(((JComponent)tc.getComponent()) instanceof CheckboxBase){
							kotei_move_flg = true;

							//�L�[���ڎ擾�ړ���i���H�����̏ꍇ�j
							MiddleCellEditor mc_saki = (MiddleCellEditor)HaigoMeisai.getCellEditor(row_move+move, 2);
							DefaultCellEditor tc_saki = (DefaultCellEditor)mc_saki.getTableCellEditor(row_move+move);
							CheckboxBase cb_saki = (CheckboxBase)tc_saki.getComponent();
							pk_KoteiCd_saki  = cb_saki.getPk1();
							pk_KoteiSeq_saki = cb_saki.getPk2();

						}else{

							//�L�[���ڎ擾�ړ���i���H�����̏ꍇ�j
							MiddleCellEditor mc_saki = (MiddleCellEditor)HaigoMeisai.getCellEditor(row_move, 2);
							DefaultCellEditor tc_saki = (DefaultCellEditor)mc_saki.getTableCellEditor(row_move);
							CheckboxBase cb_saki = (CheckboxBase)tc_saki.getComponent();
							pk_KoteiCd_saki  = cb_saki.getPk1();
							pk_KoteiSeq_saki = cb_saki.getPk2();

						}

//add start -------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.33,34
						//�H�����ׂ�ꍇ
						if(kotei_move_flg){
							//�����L�[�擾
							int intShisakuSeq = 0;
							int intKoteiCd  = Integer.parseInt(cb_moto.getPk1());
							int intKoteiSeq = Integer.parseInt(cb_moto.getPk2());
							boolean chk = DataCtrl.getInstance().getTrialTblData().checkListHenshuOkFg(intShisakuSeq, intKoteiCd, intKoteiSeq);

							//�ҏW�\�̏ꍇ�F��������
							if(chk){

							}
							//�ҏW�s�̏ꍇ�F��������
							else{
								DataCtrl.getInstance().getMessageCtrl().PrintMessageString(JwsConstManager.JWS_ERROR_0041);
								break;
							}
						}
//add end   -------------------------------------------------------------------------------

						//�f�[�^�ړ�
						MixedData Haigou_saki = DataCtrl.getInstance().getTrialTblData().MoveHaigoGenryo(pk_KoteiCd_moto, pk_KoteiSeq_moto,
								pk_KoteiCd_saki, pk_KoteiSeq_saki, move);

						//�z���f�[�^�ړ�(���)
						HaigoMeisai.tableMoveRow(row,row_move);

						//���샊�X�g�f�[�^�ړ�(���)
						ListMeisai.tableMoveRow(row,row_move);

						//�H�����ύX
						if(kotei_move_flg){

							int koteiNo = Integer.parseInt((String)HaigoMeisai.getValueAt(row_move, 1))+move;
							HaigoMeisai.setValueAt(Integer.toString(koteiNo), row_move, 1);

							//�H���s�̍�������
							HaigoMeisai.setRowHeight(row, 17);
							HaigoMeisai.setRowHeight(row_move, 17);
							ListMeisai.setRowHeight(row, 17);
							ListMeisai.setRowHeight(row_move, 17);

						}

						//�z���G�f�B�^�������_���ړ�
						Trial1.changeHaigoMeisaiRowER(row, row_move);
						Trial1.setHaigoMeisaiER();
						MiddleCellEditor mc_haigo = (MiddleCellEditor)HaigoMeisai.getCellEditor(row_move, 2);
						DefaultCellEditor dc_haigo = (DefaultCellEditor)mc_haigo.getTableCellEditor(row_move);
						CheckboxBase cb_haigo = (CheckboxBase)dc_haigo.getComponent();
						cb_haigo.setPk1(Integer.toString(Haigou_saki.getIntKoteiCd()));
						cb_haigo.setPk2(Integer.toString(Haigou_saki.getIntKoteiSeq()));

						//���샊�X�g�G�f�B�^�������_���ړ�
						Trial1.changeListShisakuRowER(row, row_move);
						Trial1.setListShisakuER();

						//�����s���H���s���ׂ����ꍇ
						if(kotei_move_flg){

							//�R���|�[�l���g�擾
							MiddleCellEditor mc_check = (MiddleCellEditor)HaigoMeisai.getCellEditor(row-move, 2);
							DefaultCellEditor tc_check = (DefaultCellEditor)mc_check.getTableCellEditor(row-move);

							//�H�����Ɍ����s���Ȃ��ꍇ
							if(((JComponent)tc_check.getComponent()) instanceof CheckboxBase){

								//�ړ������s�̍s����z��ɐݒ�
								sort.set(move_count,Integer.toString(row_move));

							}else{

								//�}������s���̎擾
								int ins_move;
								if(move < 0){
									ins_move = row;
								}else{
									ins_move = row-move;
								}

								//�����s�}��
								HaigoInsertRow(ins_move, pk_KoteiCd_moto, "1");


								//�\�[�g�z��Đݒ�
								for(int j=0; j<sort.size(); j++){
									if(move < 0){

										//�ړ������s�̍s����z��ɐݒ�
										if(move_count == j){
											int setRow = Integer.parseInt((String)sort.get(j));
											sort.set(j,Integer.toString(row_move));
										}

										//�ړ������s���傫���s����z��ɐݒ�
										if(move_count < j){
											int setRow = Integer.parseInt((String)sort.get(j));
											sort.set(j,Integer.toString(setRow-move));
										}

									}else{

										//�ړ������s�̍s����z��ɐݒ�
										if(move_count == j){
											int setRow = Integer.parseInt((String)sort.get(j));
											sort.set(j,Integer.toString(row_move+move));
										}

										//�ړ������s���傫���s����z��ɐݒ�
										if(move_count < j){
											int setRow = Integer.parseInt((String)sort.get(j));
											sort.set(j,Integer.toString(setRow+move));
										}
									}
								}
							}
						}else{
							//�ړ������s�̍s����z��ɐݒ�
							sort.set(move_count,Integer.toString(row_move));
						}
					}
					//���[�v�C���f�b�N�X�F�J�E���g
					if(move < 0){
						move_count++; //����
					}else{
						move_count--; //�~��
					}
				}

				//�I�������z����Đݒ�
				for(int i=0; i<sort.size(); i++){
					AryGenryoCheck.set(i, new int[]{Integer.parseInt((String)sort.get(i)), 2});
				}

				//�z���f�[�^�������ݒ�
				for(int i=0; i<HaigoMeisai.getRowCount(); i++){

					//�R���|�[�l���g�擾�i�����I���`�F�b�N�{�b�N�X�j
					MiddleCellEditor selectMc = (MiddleCellEditor)HaigoMeisai.getCellEditor(i, 2);
					DefaultCellEditor selectDc = (DefaultCellEditor)selectMc.getTableCellEditor(i);

					//�����I���`�F�b�N�{�b�N�X�̏ꍇ
					if(((JComponent)selectDc.getComponent()) instanceof CheckboxBase){

						//�R���|�[�l���g���ΏۍH��CD�A�H��SEQ�擾
						CheckboxBase selectCb = (CheckboxBase)selectDc.getComponent();
						String count_KoteiCd = selectCb.getPk1();
						String count_KoteiSeq = selectCb.getPk2();

						//�������ݒ�
						DataCtrl.getInstance().getTrialTblData().NoHaigoGenryo(
								count_KoteiCd, count_KoteiSeq, i);
					}
				}

				//�H�����v�v�Z
				for(int col=0; col<Trial1.getListMeisai().getColumnCount(); col++){
					Trial1.koteiSum(col);
				}

				//�e�X�g�\��
				//DataCtrl.getInstance().getTrialTblData().dispHaigo();
			}

		} catch (ExceptionBase e) {

			throw e;

		} catch (Exception e) {

			//�G���[�ݒ�
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			//2011/04/12 QP@10181_No.26 TT T.Satoh Change Start -------------------------
			//ex.setStrErrmsg("����f�[�^��� ����\�@ �����s�ړ����������s���܂���");
			ex.setStrErrmsg("����f�[�^��� �z���\ �����s�ړ����������s���܂���");
			//2011/04/12 QP@10181_No.26 TT T.Satoh Change End ---------------------------
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

			//DataCtrl.getInstance().getTrialTblData().dispProtoList();

		}

	}

	/************************************************************************************
	 *
	 *   �����s�폜
	 *    : �z�����ׁA���샊�X�g�e�[�u���̍s���폜����
	 *   @author TT nishigawa
	 *   @param row : �폜�s�ԍ�
	 *   @param pk_KoteiCd : �H��CD
	 *   @param pk_KoteiSeq : �H��SEQ
	 *   @throws ExceptionBase
	 *
	 ************************************************************************************/
	public void HaigoDeleteRow(int row, String pk_KoteiCd, String pk_KoteiSeq) throws ExceptionBase{
		try{
			//------------------------- �\�����ڍ폜  ------------------------------------
			//�e�[�u���擾
			TableBase HaigoMeisai = Trial1.getHaigoMeisai();
			TableBase ListMeisai = Trial1.getListMeisai();

			//�e�[�u���\���l�폜
			HaigoMeisai.tableDeleteRow(row);
			ListMeisai.tableDeleteRow(row);

			//�G�f�B�^�������_���폜
			Trial1.delHaigoMeisaiRowER(row);
			Trial1.setHaigoMeisaiER();
			Trial1.delListShisakuRowER(row);
			Trial1.setListShisakuER();

			//---------------------------- �f�[�^�폜  -------------------------------------
			DataCtrl.getInstance().getTrialTblData().DelHaigoGenryo(pk_KoteiCd, pk_KoteiSeq);

			//�H�����v�v�Z
			for(int col=0; col<Trial1.getListMeisai().getColumnCount(); col++){
				Trial1.koteiSum(col);
			}
			//�����v�Z
			Trial1.AutoKeisan();

		}catch (ExceptionBase e) {
			throw e;

		}catch (Exception e) {
			//�G���[�ݒ�
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			//2011/04/12 QP@10181_No.26 TT T.Satoh Change Start -------------------------
			//ex.setStrErrmsg("����f�[�^��� ����\�@ �����s�폜���������s���܂���");
			ex.setStrErrmsg("����f�[�^��� �z���\ �����s�폜���������s���܂���");
			//2011/04/12 QP@10181_No.26 TT T.Satoh Change End ---------------------------
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {
			//�e�X�g�\��
//			DataCtrl.getInstance().getTrialTblData().dispHaigo();
//			DataCtrl.getInstance().getTrialTblData().dispProtoList();
		}
	}

	/************************************************************************************
	 *
	 *   �H���s�}��
	 *    : �z�����ׁA���샊�X�g�e�[�u���̍H���s�i�R�t�������s�j��}������
	 *   @author TT nishigawa
	 *   @param row : �}���s�ԍ�
	 *   @throws ExceptionBase
	 *
	 ************************************************************************************/
	public void HaigoInsKoteiRow(int row) throws ExceptionBase{
		try{
			//---------------------------- ��������  --------------------------------------
			//�e�[�u���擾
			TableBase HaigoMeisai = Trial1.getHaigoMeisai();
			TableBase ListMeisai = Trial1.getListMeisai();

			//�z���f�[�^�擾
			ArrayList retuData = DataCtrl.getInstance().getTrialTblData().SearchHaigoData(0);

			//�R���|�|�l���g�擾
			MiddleCellEditor mc = (MiddleCellEditor)HaigoMeisai.getCellEditor(row, 0);
			DefaultCellEditor tc = (DefaultCellEditor)mc.getTableCellEditor(row);
			CheckboxBase CheckboxBase = (CheckboxBase)tc.getComponent();
			//�H���L�[���ڎ擾
			String pk_KoteiCd = CheckboxBase.getPk1();
			String pk_KoteiSeq = CheckboxBase.getPk2();

			//�z���f�[�^���擾
			ArrayList ins_kotei =
				DataCtrl.getInstance().getTrialTblData().SearchHaigoData(Integer.parseInt(pk_KoteiCd));

			//�}���s�ݒ�
			int insert_row = row+ins_kotei.size()+1;

			//--------------------------- �f�[�^�}��  ---------------------------------------
			//�H���ǉ�
			MixedData addMixedData = DataCtrl.getInstance().getTrialTblData().AddHaigoKoutei();
			String addPk_koteiCd = Integer.toString(addMixedData.getIntKoteiCd());
			String addPk_koteiSeq = Integer.toString(addMixedData.getIntKoteiSeq());

			//--------------------------- �\���l�}�� --------------------------------------
			//�z�����ׂ̑I�������N���A
			HaigoMeisai.clearSelection();
			TableCellEditor hmEditor = HaigoMeisai.getCellEditor();
			if(hmEditor != null){
				HaigoMeisai.getCellEditor().stopCellEditing();
			}
			//���X�g���ׂ̑I�������N���A
			ListMeisai.clearSelection();
			TableCellEditor lmEditor = ListMeisai.getCellEditor();
			if(lmEditor != null){
				ListMeisai.getCellEditor().stopCellEditing();
			}

			//�z�����׃e�[�u���֍H���s�}��
			HaigoMeisai.tableInsertRow(insert_row);
			Trial1.addHaigoMeisaiRowER(insert_row, 1, addPk_koteiCd, addPk_koteiSeq, -1);
			Trial1.setHaigoMeisaiER();
			//���얾�׃e�[�u���֍H���s�}��
			ListMeisai.tableInsertRow(insert_row);

			for(int i=0; i<ListMeisai.getColumnCount(); i++){
				Trial1.addListShisakuRowER(i, insert_row, 1);
			}

			Trial1.setListShisakuER();
			//�s�̍�����ݒ�
			HaigoMeisai.setRowHeight(insert_row, 17);
			ListMeisai.setRowHeight(insert_row, 17);

			//�z�����׃e�[�u���֌����s�}��
			HaigoMeisai.tableInsertRow(insert_row+1);
			Trial1.addHaigoMeisaiRowER(insert_row+1, 0, addPk_koteiCd, addPk_koteiSeq,Integer.parseInt(addMixedData.getStrIro()));
			Trial1.setHaigoMeisaiER();
			//���얾�׃e�[�u���֌����s�}��
			ListMeisai.tableInsertRow(insert_row+1);

			for(int i=0; i<ListMeisai.getColumnCount(); i++){
				Trial1.addListShisakuRowER(i, insert_row+1, 0);
			}

			Trial1.setListShisakuER();


			//------------------------------ �v�Z��ǉ� --------------------------------------
			//�ő�H�����擾
			int maxKotei = DataCtrl.getInstance().getTrialTblData().getIntMaxKotei();

			//�v�Z�Ώۗ񐔎擾
			// MOD start 20120914 QP@20505 No.1
			//2011/06/13 QP@10181_No.29 TT T.Satoh Change Start -------------------------
			//int keisanRow = ListMeisai.getRowCount()-8;
			//int keisanRow = ListMeisai.getRowCount()-9;
			//2011/06/13 QP@10181_No.29 TT T.Satoh Change End ---------------------------
			int keisanRow = ListMeisai.getRowCount()-maxKotei-9;
			// MOD end 20120914 QP@20505 No.1

			//�z�����׃e�[�u���֌v�Z�s�}��
			HaigoMeisai.tableInsertRow(keisanRow);
			Trial1.addHaigoMeisaiRowER(keisanRow, 2, addPk_koteiCd, addPk_koteiSeq, -1);
			Trial1.setHaigoMeisaiER();
			//���얾�׃e�[�u���֌v�Z�s�}��
			ListMeisai.tableInsertRow(keisanRow);

			for(int i=0; i<ListMeisai.getColumnCount(); i++){
				Trial1.addListShisakuRowER(i, keisanRow, 1);
			}

			Trial1.setListShisakuER();

			//���ڕ\��
			Trial1.getHaigoMeisai().setValueAt((maxKotei+1)+"�H���i���j", keisanRow, 4);

			// ADD start 20120914 QP@20505 No.1
			//�H���d���
			int koteiSiagariRow = ListMeisai.getRowCount()-8;
			int gokeiJuuryoRow = koteiSiagariRow - maxKotei;
			int koteiShiagariIns = gokeiJuuryoRow + Integer.parseInt(CheckboxBase.getPk3());

			//�z�����׃e�[�u���֍H���d��s�}��
			HaigoMeisai.tableInsertRow(koteiSiagariRow);
			Trial1.addHaigoMeisaiRowER(koteiSiagariRow, 2, addPk_koteiCd, addPk_koteiSeq, -1);
			Trial1.setHaigoMeisaiER();
			//���얾�׃e�[�u���֍H���d��s�}��
			ListMeisai.tableInsertRow(koteiShiagariIns);

			for(int i=0; i<ListMeisai.getColumnCount(); i++){
				Trial1.addListShisakuRowER(i, koteiSiagariRow, 0);
			}

			Trial1.setListShisakuER();

			//���ڕ\��
			Trial1.getHaigoMeisai().setValueAt((maxKotei+1)+"�H���d��d�ʁi���j", koteiSiagariRow, 4);
			// ADD end 20120914 QP@20505 No.1

			//---------------------- �H�����������Đݒ�  -----------------------------------
			int koteiNo = 0;

			// MOD start 20120914 QP@20505 No.1
			//2011/06/13 QP@10181_No.29 TT T.Satoh Change Start -------------------------
			//for(int i=0; i<HaigoMeisai.getRowCount()-maxKotei-8-1; i++){
			//for(int i=0; i<HaigoMeisai.getRowCount()-maxKotei-9-1; i++){
			//2011/06/13 QP@10181_No.29 TT T.Satoh Change End ---------------------------
			for(int i=0; i<HaigoMeisai.getRowCount()-(maxKotei*2)-10-1; i++){
			// MOD end 20120914 QP@20505 No.1

				MiddleCellEditor mcKoteiNo = (MiddleCellEditor)HaigoMeisai.getCellEditor(i, 0);
				DefaultCellEditor tcKoteiNo = (DefaultCellEditor)mcKoteiNo.getTableCellEditor(i);
				if(((JComponent)tcKoteiNo.getComponent()) instanceof CheckboxBase){
					koteiNo++;
					//�R���|�[�l���g�ɃL�[���ڂ�ݒ�
					CheckboxBase setKotei = (CheckboxBase)tcKoteiNo.getComponent();
					setKotei.setPk3(Integer.toString(koteiNo));
					setKotei.setPk4("");
					setKotei.setPk5("");
				}
				//�e�[�u�����\���l�ݒ�
				HaigoMeisai.setValueAt(Integer.toString(koteiNo), i, 1);

				//�z���f�[�^�H�����������ݒ�
				//�R���|�[�l���g�擾�i�����I���`�F�b�N�{�b�N�X�j
				MiddleCellEditor selectMc = (MiddleCellEditor)HaigoMeisai.getCellEditor(i, 2);
				DefaultCellEditor selectDc = (DefaultCellEditor)selectMc.getTableCellEditor(i);

				//�����I���`�F�b�N�{�b�N�X�̏ꍇ
				if(((JComponent)selectDc.getComponent()) instanceof CheckboxBase){

					//�R���|�[�l���g���ΏۍH��CD�A�H��SEQ�擾
					CheckboxBase selectCb = (CheckboxBase)selectDc.getComponent();
					String count_KoteiCd = selectCb.getPk1();
					String count_KoteiSeq = selectCb.getPk2();

					//�������ݒ�
					DataCtrl.getInstance().getTrialTblData().NoHaigoGenryo(
							count_KoteiCd, count_KoteiSeq, koteiNo, i);
				}
			}
			//�ő�H�����ݒ�
			DataCtrl.getInstance().getTrialTblData().setIntMaxKotei(koteiNo);

			//�H�����v�v�Z
			for(int col=0; col<Trial1.getListMeisai().getColumnCount(); col++){
				Trial1.koteiSum(col);
			}
			//�����v�Z
			Trial1.AutoKeisan();

			//�e�X�g�\��
			//DataCtrl.getInstance().getTrialTblData().dispHaigo();
			//DataCtrl.getInstance().getTrialTblData().dispProtoList();

		} catch (ExceptionBase e) {
			throw e;

		} catch (Exception e) {
			//�G���[�ݒ�
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			//2011/04/12 QP@10181_No.26 TT T.Satoh Change Start -------------------------
			//ex.setStrErrmsg("����f�[�^��� ����\�@ �H���s�}�����������s���܂���");
			ex.setStrErrmsg("����f�[�^��� �z���\ �H���s�}�����������s���܂���");
			//2011/04/12 QP@10181_No.26 TT T.Satoh Change End ---------------------------
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/************************************************************************************
	 *
	 *   �H���s�폜
	 *    : �z�����ׁA���샊�X�g�e�[�u���̍H���s�i�R�t�������s�j���폜����
	 *   @author TT nishigawa
	 *   @param row : �폜�s�ԍ�
	 *   @throws ExceptionBase
	 *
	 ************************************************************************************/
	public void HaigoDelKoteiRow(int row) throws ExceptionBase{
		try{
			//---------------------------- ��������  --------------------------------------
			//�e�[�u���擾
			TableBase HaigoMeisai = Trial1.getHaigoMeisai();
			TableBase ListMeisai = Trial1.getListMeisai();

			//�z���f�[�^�擾
			ArrayList retuData = DataCtrl.getInstance().getTrialTblData().SearchHaigoData(0);
			int maxKotei = DataCtrl.getInstance().getTrialTblData().getIntMaxKotei()-1;

			// ADD start 20120914 QP@20505 No.1
			int beforeKotei = DataCtrl.getInstance().getTrialTblData().getIntMaxKotei();
			// ADD end 20120914 QP@20505 No.1

			//�R���|�|�l���g�擾
			MiddleCellEditor mc = (MiddleCellEditor)HaigoMeisai.getCellEditor(row, 0);
			DefaultCellEditor tc = (DefaultCellEditor)mc.getTableCellEditor(row);
			CheckboxBase CheckboxBase = (CheckboxBase)tc.getComponent();
			//�H���L�[���ڎ擾
			String pk_KoteiCd = CheckboxBase.getPk1();
			String pk_KoteiSeq = CheckboxBase.getPk2();

			// ADD start 20120914 QP@20505 No.1
			String pk_KoteiSelect = CheckboxBase.getPk3();
			// ADD end 20120914 QP@20505 No.1

			//�z���f�[�^���擾
			ArrayList del_kotei =
				DataCtrl.getInstance().getTrialTblData().SearchHaigoData(Integer.parseInt(pk_KoteiCd));

			//�폜�s�ݒ�
			int delete_start = row;
			int delete_end = delete_start+del_kotei.size();

			//--------------------------- �\���l�폜  -------------------------------------
			//�z�����ׂ̑I�������N���A
			HaigoMeisai.clearSelection();
			TableCellEditor hmEditor = HaigoMeisai.getCellEditor();
			if(hmEditor != null){
				HaigoMeisai.getCellEditor().stopCellEditing();
			}
			//���X�g���ׂ̑I�������N���A
			ListMeisai.clearSelection();
			TableCellEditor lmEditor = ListMeisai.getCellEditor();
			if(lmEditor != null){
				ListMeisai.getCellEditor().stopCellEditing();
			}

			//�����s��1�s���ɍ폜
			for(int i=delete_start; i<=delete_end; i++){
				//�e�[�u���\���l�폜
				HaigoMeisai.tableDeleteRow(row);
				ListMeisai.tableDeleteRow(row);
				//�G�f�B�^�������_���폜
				Trial1.delHaigoMeisaiRowER(row);
				Trial1.delListShisakuRowER(row);
				Trial1.setHaigoMeisaiER();
				Trial1.setListShisakuER();
			}

			//---------------------------- �f�[�^�폜  -------------------------------------
			DataCtrl.getInstance().getTrialTblData().DelHaigoKoutei(Integer.parseInt(pk_KoteiCd));
			//�H���s�f�[�^��0���̏ꍇ
			if(DataCtrl.getInstance().getTrialTblData().SearchHaigoData(0).size() == 0){
				//�H���f�[�^�ǉ�
				MixedData addMixedData = DataCtrl.getInstance().getTrialTblData().AddHaigoKoutei();
				String addPk_koteiCd = Integer.toString(addMixedData.getIntKoteiCd());
				String addPk_koteiSeq = Integer.toString(addMixedData.getIntKoteiSeq());
				int insert_row = 0;
				maxKotei = 1;

				//�z�����׃e�[�u���֍H���s�}��
				HaigoMeisai.tableInsertRow(insert_row);
				Trial1.addHaigoMeisaiRowER(insert_row, 1, addPk_koteiCd, "1", -1);
				Trial1.setHaigoMeisaiER();
				//���얾�׃e�[�u���֍H���s�}��
				ListMeisai.tableInsertRow(insert_row);

				for(int i=0; i<ListMeisai.getColumnCount(); i++){
					Trial1.addListShisakuRowER(i, insert_row, 1);
				}

				Trial1.setListShisakuER();
				//�s�̍�����ݒ�
				HaigoMeisai.setRowHeight(insert_row, 17);
				ListMeisai.setRowHeight(insert_row, 17);

				//�z�����׃e�[�u���֌����s�}��
				HaigoMeisai.tableInsertRow(insert_row+1);
				Trial1.addHaigoMeisaiRowER(insert_row+1, 0, addPk_koteiCd, addPk_koteiSeq,Integer.parseInt(addMixedData.getStrIro()));
				Trial1.setHaigoMeisaiER();
				//���얾�׃e�[�u���֌����s�}��
				ListMeisai.tableInsertRow(insert_row+1);

				for(int i=0; i<ListMeisai.getColumnCount(); i++){
					Trial1.addListShisakuRowER(i, insert_row+1, 0);
				}

				Trial1.setListShisakuER();
			}else{
				//---------------------------- �v�Z��폜 ------------------------------------
				// MOD start 20120914 QP@20505 No.1
				//�v�Z�Ώۗ񐔎擾
				//2011/06/13 QP@10181_No.29 TT T.Satoh Change Start -------------------------
				//int keisanRow = ListMeisai.getRowCount()-8-1;
//				int keisanRow = ListMeisai.getRowCount()-9-1;
				//2011/06/13 QP@10181_No.29 TT T.Satoh Change End ---------------------------
				int keisanRow = ListMeisai.getRowCount()-beforeKotei-9-1;
				int siagariRow = ListMeisai.getRowCount()-9-1-beforeKotei + Integer.parseInt(pk_KoteiSelect);
				// MOD end 20120914 QP@20505 No.1

				//�e�[�u���\���l�폜
				HaigoMeisai.tableDeleteRow(keisanRow);
				ListMeisai.tableDeleteRow(keisanRow);
				//�G�f�B�^�������_���폜
				Trial1.delHaigoMeisaiRowER(keisanRow);
				Trial1.delListShisakuRowER(keisanRow);
				Trial1.setHaigoMeisaiER();
				Trial1.setListShisakuER();

				// ADD start 20120914 QP@20505 No.1
				//�e�[�u���\���l�폜
				HaigoMeisai.tableDeleteRow(siagariRow);
				ListMeisai.tableDeleteRow(siagariRow);
				//�G�f�B�^�������_���폜
				Trial1.delHaigoMeisaiRowER(siagariRow);
				Trial1.delListShisakuRowER(siagariRow);
				Trial1.setHaigoMeisaiER();
				Trial1.setListShisakuER();

				for(int i = 0;i <= ListMeisai.getRowCount()-9 - siagariRow;i++){
					Trial1.getHaigoMeisai().setValueAt(i+ Integer.parseInt(pk_KoteiSelect) + "�H���d��d�ʁi���j", siagariRow + i, 4);
				}
				// ADD end 20120914 QP@20505 No.1

			}

			//------------------------ �H�����������Đݒ�  --------------------------------
			int koteiNo = 0;
			//System.out.println(maxKotei);

			// MOD start 20120914 QP@20505 No.1
			//2011/06/13 QP@10181_No.29 TT T.Satoh Change Start -------------------------
			//for(int i=0; i<HaigoMeisai.getRowCount()-maxKotei-8; i++){
			//for(int i=0; i<HaigoMeisai.getRowCount()-maxKotei-9; i++){
			//2011/06/13 QP@10181_No.29 TT T.Satoh Change End ---------------------------
			for(int i=0; i<HaigoMeisai.getRowCount()-(maxKotei*2)-9; i++){
			// MOD end 20120914 QP@20505 No.1

				MiddleCellEditor mcKoteiNo = (MiddleCellEditor)HaigoMeisai.getCellEditor(i, 0);
				DefaultCellEditor tcKoteiNo = (DefaultCellEditor)mcKoteiNo.getTableCellEditor(i);
				if(((JComponent)tcKoteiNo.getComponent()) instanceof CheckboxBase){
					koteiNo++;
					//�R���|�[�l���g�ɃL�[���ڂ�ݒ�
					CheckboxBase setKotei = (CheckboxBase)tcKoteiNo.getComponent();
					setKotei.setPk3(Integer.toString(koteiNo));
					setKotei.setPk4("");
					setKotei.setPk5("");
				}
				//�e�[�u�����\���l�ݒ�
				HaigoMeisai.setValueAt(Integer.toString(koteiNo), i, 1);

				//�z���f�[�^�H�����������ݒ�
				//�R���|�[�l���g�擾�i�����I���`�F�b�N�{�b�N�X�j
				MiddleCellEditor selectMc = (MiddleCellEditor)HaigoMeisai.getCellEditor(i, 2);
				DefaultCellEditor selectDc = (DefaultCellEditor)selectMc.getTableCellEditor(i);

				//�����I���`�F�b�N�{�b�N�X�̏ꍇ
				if(((JComponent)selectDc.getComponent()) instanceof CheckboxBase){

					//�R���|�[�l���g���ΏۍH��CD�A�H��SEQ�擾
					CheckboxBase selectCb = (CheckboxBase)selectDc.getComponent();
					String count_KoteiCd = selectCb.getPk1();
					String count_KoteiSeq = selectCb.getPk2();

					//�������ݒ�
					DataCtrl.getInstance().getTrialTblData().NoHaigoGenryo(
							count_KoteiCd, count_KoteiSeq, koteiNo, i);
				}
			}
			//�ő�H�����ݒ�
			DataCtrl.getInstance().getTrialTblData().setIntMaxKotei(koteiNo);

			//�H�����v�v�Z
			for(int col=0; col<Trial1.getListMeisai().getColumnCount(); col++){
				Trial1.koteiSum(col);
			}
			//�����v�Z
			Trial1.AutoKeisan();

			//�e�X�g�\��
//			DataCtrl.getInstance().getTrialTblData().dispHaigo();
//			DataCtrl.getInstance().getTrialTblData().dispProtoList();

		} catch (ExceptionBase e) {
			throw e;

		} catch (Exception e) {
			//�G���[�ݒ�
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			//2011/04/12 QP@10181_No.26 TT T.Satoh Change Start -------------------------
			//ex.setStrErrmsg("����f�[�^��� ����\�@ �H���s�폜���������s���܂���");
			ex.setStrErrmsg("����f�[�^��� �z���\ �H���s�폜���������s���܂���");
			//2011/04/12 QP@10181_No.26 TT T.Satoh Change End ---------------------------
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/************************************************************************************
	 *
	 *   �H���s�ړ�
	 *    : �z�����ׁA���샊�X�g�e�[�u���̍H���s�i�R�t�������s�j���ړ�����
	 *   @author TT nishigawa
	 *   @param row  : �ړ����̍H���s�ԍ�
	 *   @param hoko : �ړ������i0=�� or 1=��j
	 *   @throws ExceptionBase
	 *
	 ************************************************************************************/
	public void HaigoMoveKoteiRow(int row,int hoko) throws ExceptionBase{
		try{
			//-------------------------- �ړ����s�ݒ�  ------------------------------------
			//�e�[�u���擾
			TableBase HaigoMeisai = Trial1.getHaigoMeisai();
			TableBase ListMeisai = Trial1.getListMeisai();

			//�R���|�|�l���g�擾
			MiddleCellEditor mc = (MiddleCellEditor)HaigoMeisai.getCellEditor(row, 0);
			DefaultCellEditor tc = (DefaultCellEditor)mc.getTableCellEditor(row);
			CheckboxBase CheckboxBase = (CheckboxBase)tc.getComponent();
			//�H���L�[���ڎ擾
			String pk_KoteiCd = CheckboxBase.getPk1();
			String pk_KoteiSeq = CheckboxBase.getPk2();
			// ADD start 20120914 QP@20505 No.1
			String pk_KoteiSelect = CheckboxBase.getPk3();
			// ADD end 20120914 QP@20505 No.1

			//�z���f�[�^���擾
			ArrayList arymoto_kotei =
				DataCtrl.getInstance().getTrialTblData().SearchHaigoData(Integer.parseInt(pk_KoteiCd));

			//�ړ����s�ݒ�
			int move_moto = row;
			int move_size = arymoto_kotei.size();

			//--------------------------- �ړ���s�ݒ�  -----------------------------------
			String koteiCd_saki = "";
			int move_saki = 0;
			int move_saki_size = 0;

			// ADD start 20120914 QP@20505 No.1
			int koteiShiagari_saki = 0;
			// ADD end 20120914 QP@20505 No.1

			boolean move_flg = true;
			//---------------- ��ړ��̏ꍇ  ----------------
			if(hoko == 1){
				move_moto = move_moto + move_size;
				//�擪�s�łȂ��ꍇ�ɏ�����ړ��������J�n
				if(row > 0){
					//�ړ���s�̌���
					for(int i=row-1; i>=0; i--){
						MiddleCellEditor MiddleCellEditor = (MiddleCellEditor)HaigoMeisai.getCellEditor(i, 0);
						DefaultCellEditor DefaultCellEditor = (DefaultCellEditor)MiddleCellEditor.getTableCellEditor(i);
						//1��ڂɍH���I���`�F�b�N�{�b�N�X������ꍇ�A�ŏI�s�łȂ�
						if(((JComponent)DefaultCellEditor.getComponent()) instanceof CheckboxBase){
							CheckboxBase up_chkBox = (CheckboxBase)DefaultCellEditor.getComponent();
							koteiCd_saki = up_chkBox.getPk1();
							move_saki = i;

							// ADD start 20120914 QP@20505 No.1
							koteiShiagari_saki = Integer.parseInt(up_chkBox.getPk3());
							// ADD end 20120914 QP@20505 No.1

							i = 0; //���[�v�A�E�g
						}
					}
					//�ړ���H���̔z���f�[�^���擾
					ArrayList saki_koteiData =
						DataCtrl.getInstance().getTrialTblData().SearchHaigoData(Integer.parseInt(koteiCd_saki));
				}else{
					move_flg = false;
				}
			//---------------- ���ړ��̏ꍇ  ----------------
			}else{
				move_moto = row;
				//�ړ���s�̌�������
				for(int i=row+1; i<HaigoMeisai.getRowCount(); i++){
					MiddleCellEditor MiddleCellEditor = (MiddleCellEditor)HaigoMeisai.getCellEditor(i, 0);
					DefaultCellEditor DefaultCellEditor = (DefaultCellEditor)MiddleCellEditor.getTableCellEditor(i);
					//1��ڂɍH���I���`�F�b�N�{�b�N�X������ꍇ�A�ŏI�s�łȂ�
					if(((JComponent)DefaultCellEditor.getComponent()) instanceof CheckboxBase){
						CheckboxBase down_chkBox = (CheckboxBase)DefaultCellEditor.getComponent();
						koteiCd_saki = down_chkBox.getPk1();
						move_saki = i;

						// ADD start 20120914 QP@20505 No.1
						koteiShiagari_saki = Integer.parseInt(down_chkBox.getPk3());
						// ADD end 20120914 QP@20505 No.1

						i = HaigoMeisai.getRowCount(); //���[�v�A�E�g
					}
				}
				//�ŏI�s�łȂ��ꍇ�ɉ������ړ��������J�n
				if(move_saki > 0){
					//�ړ���H���̔z���f�[�^���擾
					ArrayList saki_koteiData =
						DataCtrl.getInstance().getTrialTblData().SearchHaigoData(Integer.parseInt(koteiCd_saki));
					//�ړ���s�̐ݒ�
					move_saki = move_saki + saki_koteiData.size();
				}else{
					move_flg = false;
				}
			}
			//--------------------------- �\���l�ړ�  -------------------------------------
			if(move_flg){
				//�z�����ׂ̑I�������N���A
				HaigoMeisai.clearSelection();
				TableCellEditor hmEditor = HaigoMeisai.getCellEditor();
				if(hmEditor != null){
					HaigoMeisai.getCellEditor().stopCellEditing();
				}
				//���X�g���ׂ̑I�������N���A
				ListMeisai.clearSelection();
				TableCellEditor lmEditor = ListMeisai.getCellEditor();
				if(lmEditor != null){
					ListMeisai.getCellEditor().stopCellEditing();
				}
				//�f�[�^�ړ�(���)
				for(int i=0; i<=move_size; i++){
					//�z���f�[�^�ړ�(���)
					HaigoMeisai.tableMoveRow(move_moto,move_saki);
					Trial1.moveHaigoMeisaiRowER(move_moto, move_saki);
					Trial1.setHaigoMeisaiER();
					//���샊�X�g�f�[�^�ړ�(���)
					ListMeisai.tableMoveRow(move_moto,move_saki);
					Trial1.moveListShisakuRowER(move_moto, move_saki);
					Trial1.setListShisakuER();
				}

				// ADD start 20120914 QP@20505 No.1
				int maxKotei = DataCtrl.getInstance().getTrialTblData().getIntMaxKotei();
				int ListRow = ListMeisai.getRowCount();
				int shiagari_moto = ListRow - 9 - maxKotei + Integer.parseInt(pk_KoteiSelect);
				int shiagari_saki = ListRow - 9 - maxKotei + koteiShiagari_saki;
				ListMeisai.tableMoveRow(shiagari_moto, shiagari_saki);
				Trial1.moveListShisakuRowER(shiagari_moto, shiagari_saki);
				Trial1.setListShisakuER();
				// ADD end 20120914 QP@20505 No.1

				//�H���s�̍����ݒ�
				for(int i=0; i<HaigoMeisai.getRowCount(); i++){
					MiddleCellEditor MiddleCellEditor = (MiddleCellEditor)HaigoMeisai.getCellEditor(i, 0);
					DefaultCellEditor DefaultCellEditor = (DefaultCellEditor)MiddleCellEditor.getTableCellEditor(i);
					//1��ڂɍH���I���`�F�b�N�{�b�N�X������ꍇ
					if(((JComponent)DefaultCellEditor.getComponent()) instanceof CheckboxBase){
						HaigoMeisai.setRowHeight(i, 17);
						ListMeisai.setRowHeight(i, 17);
					}else{
						HaigoMeisai.setRowHeight(i, 17);
						ListMeisai.setRowHeight(i, 17);
					}
				}
			}
			//----------------------- �H�����������Đݒ�  ----------------------------------
			//�z���f�[�^�擾
			ArrayList retuData = DataCtrl.getInstance().getTrialTblData().SearchHaigoData(0);
			//�ő�H�����擾
			int maxKotei = DataCtrl.getInstance().getTrialTblData().getIntMaxKotei();
			int koteiNo = 0;

			// MOD start 20120914 QP@20505 No.1
			//2011/06/13 QP@10181_No.29 TT T.Satoh Change Start -------------------------
			//for(int i=0; i<HaigoMeisai.getRowCount()-maxKotei-8; i++){
//			for(int i=0; i<HaigoMeisai.getRowCount()-maxKotei-9; i++){
			//2011/06/13 QP@10181_No.29 TT T.Satoh Change End ---------------------------
			for(int i = 0;i < HaigoMeisai.getRowCount() - (maxKotei * 2) - 9;i++){
			// ADD end 20120914 QP@20505 No.1

				MiddleCellEditor mcKoteiNo = (MiddleCellEditor)HaigoMeisai.getCellEditor(i, 0);
				DefaultCellEditor tcKoteiNo = (DefaultCellEditor)mcKoteiNo.getTableCellEditor(i);
				if(((JComponent)tcKoteiNo.getComponent()) instanceof CheckboxBase){
					koteiNo++;
					//�R���|�[�l���g�ɃL�[���ڂ�ݒ�
					CheckboxBase setKotei = (CheckboxBase)tcKoteiNo.getComponent();
					setKotei.setPk3(Integer.toString(koteiNo));
					setKotei.setPk4("");
					setKotei.setPk5("");
				}
				//�e�[�u�����\���l�ݒ�
				HaigoMeisai.setValueAt(Integer.toString(koteiNo), i, 1);

				//�z���f�[�^�H�����������ݒ�
				//�R���|�[�l���g�擾�i�����I���`�F�b�N�{�b�N�X�j
				MiddleCellEditor selectMc = (MiddleCellEditor)HaigoMeisai.getCellEditor(i, 2);
				DefaultCellEditor selectDc = (DefaultCellEditor)selectMc.getTableCellEditor(i);

				//�����I���`�F�b�N�{�b�N�X�̏ꍇ
				if(((JComponent)selectDc.getComponent()) instanceof CheckboxBase){
					//�R���|�[�l���g���ΏۍH��CD�A�H��SEQ�擾
					CheckboxBase selectCb = (CheckboxBase)selectDc.getComponent();
					String count_KoteiCd = selectCb.getPk1();
					String count_KoteiSeq = selectCb.getPk2();
					//�������ݒ�
					DataCtrl.getInstance().getTrialTblData().NoHaigoGenryo(
							count_KoteiCd, count_KoteiSeq, koteiNo, i);
				}
			}

			//�H�����v�v�Z
			for(int col=0; col<Trial1.getListMeisai().getColumnCount(); col++){
				Trial1.koteiSum(col);
			}


			//�e�X�g�\��
//			DataCtrl.getInstance().getTrialTblData().dispHaigo();
//			DataCtrl.getInstance().getTrialTblData().dispProtoList();

		} catch (ExceptionBase e) {
			throw e;

		} catch (Exception e) {
			//�G���[�ݒ�
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			//2011/04/12 QP@10181_No.26 TT T.Satoh Change Start -------------------------
			//ex.setStrErrmsg("����\�@�p�l���N���X�@�H���s�ړ����������s���܂���");
			ex.setStrErrmsg("�z���\�p�l���N���X�@�H���s�ړ����������s���܂���");
			//2011/04/12 QP@10181_No.26 TT T.Satoh Change End ---------------------------
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getStackTrace().toString());
			throw ex;

		} finally {
			//�e�X�g�\��
			//DataCtrl.getInstance().getTrialTblData().dispTrial();
			//DataCtrl.getInstance().getTrialTblData().dispProtoList();

		}

	}

	/************************************************************************************
	 *
	 *   �����}��
	 *    : �����e�[�u���̎�����}������
	 *   @author TT nishigawa
	 *   @param  col  : �ǉ���ԍ�
	 *   @throws ExceptionBase
	 *
	 ************************************************************************************/
	public void HaigoInsShisakuCol(int col) throws ExceptionBase{
		try{
			//---------------------------- �e�[�u���擾  ------------------------------------
			TableBase ListHeader = Trial1.getListHeader();
			TableBase ListMeisai = Trial1.getListMeisai();
			TableBase HaigoMeisai = Trial1.getHaigoMeisai();

			//----------------------------- �f�[�^�}��  -------------------------------------
			TrialData TrialData = DataCtrl.getInstance().getTrialTblData().AddShisakuRetu();

			//���������f�[�^�̑}��
			DataCtrl.getInstance().getTrialTblData().AddGenkaGenryoData(TrialData.getIntShisakuSeq());

//2011/05/12 QP@10181_No.30 TT Nishigawa Change Start -------------------------

			setTp2_5TableHiju_tp1(TrialData.getIntShisakuSeq());

			//�T���v����w�b�_�e�[�u���擾
			TableBase tbListHeader = this.getTrial1().getListHeader();

			//����SEQ�̎擾_�擪��
    		MiddleCellEditor mceShisakuSeq_sen = (MiddleCellEditor)tbListHeader.getCellEditor(0, 0);
			DefaultCellEditor dceShisakuSeq_sen = (DefaultCellEditor)mceShisakuSeq_sen.getTableCellEditor(0);
			CheckboxBase chkShisakuSeq_sen = (CheckboxBase)dceShisakuSeq_sen.getComponent();
			int intShisakuSeq_sen = Integer.parseInt(chkShisakuSeq_sen.getPk1());

			//�擪��̗L�������A���Ϗ[�U�ʎ擾
    		ArrayList genkaData_sen = DataCtrl.getInstance().getTrialTblData().SearchGenkaGenryoData(intShisakuSeq_sen);
    		CostMaterialData costMaterialData_sen = (CostMaterialData)genkaData_sen.get(0);
    		String strBudomari_sen = costMaterialData_sen.getStrYukoBudomari();
    		String strZyutenAve_sen = costMaterialData_sen.getStrZyutenAve();

    		//�ǉ���̗L�������A���Ϗ[�U�ʂɐ擪��̂��̂�ݒ�
    		ArrayList genkaData_ins = DataCtrl.getInstance().getTrialTblData().SearchGenkaGenryoData(TrialData.getIntShisakuSeq());
    		CostMaterialData costMaterialData_ins = (CostMaterialData)genkaData_ins.get(0);
    		costMaterialData_ins.setStrYukoBudomari(strBudomari_sen);
    		costMaterialData_ins.setStrZyutenAve(strZyutenAve_sen);

//2011/05/12 QP@10181_No.30 TT Nishigawa Change end   -------------------------


			//---------------------------- ��ʓ��}��  -------------------------------------
			//����w�b�_�[�̑I�������N���A
			ListHeader.clearSelection();
			TableCellEditor hmEditor = ListHeader.getCellEditor();
			if(hmEditor != null){
				ListHeader.getCellEditor().stopCellEditing();
			}
			//���X�g���ׂ̑I�������N���A
			ListMeisai.clearSelection();
			TableCellEditor lmEditor = ListMeisai.getCellEditor();
			if(lmEditor != null){
				ListMeisai.getCellEditor().stopCellEditing();
			}
			col++;
			ListHeader.tableInsertColumn(col);
			ListMeisai.tableInsertColumn(col);

			//------------------------- �G�f�B�^�������_���}��  ---------------------------------
			Trial1.addRetuShisakuColER(col, Integer.toString(TrialData.getIntShisakuSeq()));
			Trial1.addListShisakuColER(col);
			Trial1.setHeaderShisakuER();
			Trial1.setListShisakuER();

			//-------------------------- �H���s�̍����ݒ�  ----------------------------------
			for(int i=0; i<HaigoMeisai.getRowCount(); i++){
				MiddleCellEditor MiddleCellEditor = (MiddleCellEditor)HaigoMeisai.getCellEditor(i, 0);
				DefaultCellEditor DefaultCellEditor = (DefaultCellEditor)MiddleCellEditor.getTableCellEditor(i);
				//1��ڂɍH���I���`�F�b�N�{�b�N�X������ꍇ
				if(((JComponent)DefaultCellEditor.getComponent()) instanceof CheckboxBase){
					ListMeisai.setRowHeight(i, 17);
				}else{
					ListMeisai.setRowHeight(i, 17);
				}
			}

			//---------------------------- ���쏇�̐ݒ�  -----------------------------------
			setShisakuColNo();

			//�H�����v�v�Z
			Trial1.koteiSum(col);
			//�����v�Z
			Trial1.AutoKeisan();

		} catch (ExceptionBase e) {
			throw e;

		} catch (Exception e) {
			e.printStackTrace();

			//�G���[�ݒ�
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			//2011/04/12 QP@10181_No.26 TT T.Satoh Change Start -------------------------
			//ex.setStrErrmsg("����\�@�p�l���N���X�@�����}�����������s���܂���");
			ex.setStrErrmsg("�z���\�p�l���N���X�@�����}�����������s���܂���");
			//2011/04/12 QP@10181_No.26 TT T.Satoh Change End ---------------------------
			ex.setStrErrShori(this.getClass().getName() + ":HaigoInsShisakuCol");
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getStackTrace().toString());
			throw ex;

		} finally {
			//�e�X�g�\��
			//DataCtrl.getInstance().getTrialTblData().dispTrial();
			//DataCtrl.getInstance().getTrialTblData().dispProtoList();

		}

	}

	/************************************************************************************
	 *
	 *   �����폜
	 *    : �����e�[�u���̎������폜����
	 *   @author TT nishigawa
	 *   @param  col  : �폜��ԍ�
	 *   @throws ExceptionBase
	 *
	 ************************************************************************************/
	public void HaigoDelShisakuCol(int col) throws ExceptionBase{
		try{
			//---------------------------- �e�[�u���擾  ------------------------------------
			TableBase ListHeader = Trial1.getListHeader();
			TableBase ListMeisai = Trial1.getListMeisai();
			TableBase HaigoMeisai = Trial1.getHaigoMeisai();

			//----------------------------- �f�[�^�폜  -------------------------------------
			//����SEQ�擾
			int delSeq = 0;
			MiddleCellEditor deleteMc = (MiddleCellEditor)ListHeader.getCellEditor(0, col);
			DefaultCellEditor deleteDc = (DefaultCellEditor)deleteMc.getTableCellEditor(0);
			CheckboxBase CheckboxBase = (CheckboxBase)deleteDc.getComponent();
			delSeq = Integer.parseInt(CheckboxBase.getPk1());
			DataCtrl.getInstance().getTrialTblData().DelShsiakuRetu(delSeq);

			//---------------------------- ��ʓ��폜  -------------------------------------
			//����w�b�_�[�̑I�������N���A
			ListHeader.clearSelection();
			TableCellEditor hmEditor = ListHeader.getCellEditor();
			if(hmEditor != null){
				ListHeader.getCellEditor().stopCellEditing();
			}
			//���X�g���ׂ̑I�������N���A
			ListMeisai.clearSelection();
			TableCellEditor lmEditor = ListMeisai.getCellEditor();
			if(lmEditor != null){
				ListMeisai.getCellEditor().stopCellEditing();
			}
			ListHeader.tableDeleteColumn(col);
			ListMeisai.tableDeleteColumn(col);

			//------------------------- �G�f�B�^�������_���폜  ---------------------------------
			Trial1.removeHeaderShisakuColER(col);
			Trial1.removeListShisakuColER(col);
			Trial1.setHeaderShisakuER();
			Trial1.setListShisakuER();

			//---------------------- ����񂪑S�č폜���ꂽ�ꍇ  ------------------------------
			if(ListHeader.getColumnCount() == 0){
				//�V�K�Ɏ�����}��
				TrialData TrialData = DataCtrl.getInstance().getTrialTblData().AddShisakuRetu();
				//��ʓ��}��
				ListHeader.tableInsertColumn(col);
				ListMeisai.tableInsertColumn(col);
				//�G�f�B�^�������_���}��
				Trial1.addRetuShisakuColER(col, Integer.toString(TrialData.getIntShisakuSeq()));
				Trial1.addListShisakuColER(col);
				Trial1.setHeaderShisakuER();
				Trial1.setListShisakuER();
			}

			//---------------------------- ���쏇�̐ݒ�  -----------------------------------
			setShisakuColNo();

			//-------------------------- �H���s�̍����ݒ�  ----------------------------------
			for(int i=0; i<HaigoMeisai.getRowCount(); i++){
				MiddleCellEditor MiddleCellEditor = (MiddleCellEditor)HaigoMeisai.getCellEditor(i, 0);
				DefaultCellEditor DefaultCellEditor = (DefaultCellEditor)MiddleCellEditor.getTableCellEditor(i);
				//1��ڂɍH���I���`�F�b�N�{�b�N�X������ꍇ
				if(((JComponent)DefaultCellEditor.getComponent()) instanceof CheckboxBase){
					ListMeisai.setRowHeight(i, 17);
				}else{
					ListMeisai.setRowHeight(i, 17);
				}
			}

			//-------------------------- ���ӎ���No�폜  ------------------------------------
			//�����H���p�l���擾
			ManufacturingPanel pb = DataCtrl.getInstance().getManufacturingSubDisp().getManufacturingPanel();
			//�폜SEQ�ƌ��ݕ\������Ă��鐻���H�����̎���SEQ���������ꍇ
			if(delSeq == pb.getShisakuSeq()){
				//�����H���g������
				pb.init();
			}

		} catch (ExceptionBase e) {
			throw e;

		} catch (Exception e) {
			//�G���[�ݒ�
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			//2011/04/12 QP@10181_No.26 TT T.Satoh Change Start -------------------------
			//ex.setStrErrmsg("����\�@�p�l���N���X�@�����폜���������s���܂���");
			ex.setStrErrmsg("�z���\�p�l���N���X�@�����폜���������s���܂���");
			//2011/04/12 QP@10181_No.26 TT T.Satoh Change End ---------------------------
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getStackTrace().toString());
			throw ex;

		} finally {
			//�e�X�g�\��
			//DataCtrl.getInstance().getTrialTblData().dispTrial();
			//DataCtrl.getInstance().getTrialTblData().dispProtoList();
			//DataCtrl.getInstance().getTrialTblData().dispManufacturingData();

		}

	}

	/************************************************************************************
	 *
	 *   �����ړ�
	 *    : �����e�[�u���̎������ړ�����
	 *   @author TT nishigawa
	 *   @param  col_moto : �ړ�����ԍ�
	 *   @param  hoko     : �ړ������i0=�� or 1=�E�j
	 *   @throws ExceptionBase
	 *
	 ************************************************************************************/
	public void moveRetuShisakuCol(int col, int hoko) throws ExceptionBase{
		try{
			//---------------------------- �e�[�u���擾  ------------------------------------
			TableBase ListHeader = Trial1.getListHeader();
			TableBase ListMeisai = Trial1.getListMeisai();
			TableBase HaigoMeisai = Trial1.getHaigoMeisai();

			//---------------------------- ��ʓ��ړ�  -------------------------------------
			//����w�b�_�[�̑I�������N���A
			ListHeader.clearSelection();
			TableCellEditor hmEditor = ListHeader.getCellEditor();
			if(hmEditor != null){
				ListHeader.getCellEditor().stopCellEditing();
			}
			//���X�g���ׂ̑I�������N���A
			ListMeisai.clearSelection();
			TableCellEditor lmEditor = ListMeisai.getCellEditor();
			if(lmEditor != null){
				ListMeisai.getCellEditor().stopCellEditing();
			}
			//���ړ�
			if(hoko == 0){
				if(col > 0){
					ListHeader.tableMoveColumn( col, col-1 );
					ListMeisai.tableMoveColumn( col, col-1 );
				}
			//�E�ړ�
			}else{
				if(col < ListHeader.getColumnCount()-1){
					ListHeader.tableMoveColumn( col, col+1 );
					ListMeisai.tableMoveColumn( col, col+1 );
				}
			}

			//------------------------- �G�f�B�^�������_���ړ�  ---------------------------------
			//���ړ�
			if(hoko == 0){
				if(col > 0){
					Trial1.changeHeaderShisakuColER(col, col-1);
					Trial1.changeListShisakuColER(col, col-1);
					Trial1.setHeaderShisakuER();
					Trial1.setListShisakuER();
				}
			//�E�ړ�
			}else{
				if(col < ListHeader.getColumnCount()-1){
					Trial1.changeHeaderShisakuColER(col, col+1);
					Trial1.changeListShisakuColER(col, col+1);
					Trial1.setHeaderShisakuER();
					Trial1.setListShisakuER();
				}
			}

			//---------------------------- ���쏇�̐ݒ�  -----------------------------------
			setShisakuColNo();

			//-------------------------- �H���s�̍����ݒ�  ----------------------------------
			for(int i=0; i<HaigoMeisai.getRowCount(); i++){
				MiddleCellEditor MiddleCellEditor = (MiddleCellEditor)HaigoMeisai.getCellEditor(i, 0);
				DefaultCellEditor DefaultCellEditor = (DefaultCellEditor)MiddleCellEditor.getTableCellEditor(i);
				//1��ڂɍH���I���`�F�b�N�{�b�N�X������ꍇ
				if(((JComponent)DefaultCellEditor.getComponent()) instanceof CheckboxBase){
					ListMeisai.setRowHeight(i, 17);
				}else{
					ListMeisai.setRowHeight(i, 17);
				}
			}

			//�e�X�g�\��
//			DataCtrl.getInstance().getTrialTblData().dispTrial();
//			DataCtrl.getInstance().getTrialTblData().dispProtoList();
//			DataCtrl.getInstance().getTrialTblData().dispManufacturingData();

		} catch (ExceptionBase e) {
			throw e;

		} catch (Exception e) {
			//�G���[�ݒ�
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			//2011/04/12 QP@10181_No.26 TT T.Satoh Change Start -------------------------
			//ex.setStrErrmsg("����\�@�p�l���N���X�@�����ړ����������s���܂���");
			ex.setStrErrmsg("�z���\�p�l���N���X�@�����ړ����������s���܂���");
			//2011/04/12 QP@10181_No.26 TT T.Satoh Change End ---------------------------
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getStackTrace().toString());
			throw ex;

		} finally {

		}
	}

	/************************************************************************************
	 *
	 *   ����񏇐ݒ�
	 *    : �����̕\������ݒ肷��
	 *   @author TT nishigawa
	 *   @throws ExceptionBase
	 *
	 ************************************************************************************/
	public void setShisakuColNo() throws ExceptionBase{
		try{
			//---------------------------- �e�[�u���擾  ------------------------------------
			TableBase ListHeader = Trial1.getListHeader();

			//---------------------------- ���쏇�̐ݒ�  -----------------------------------
			int shisaku_no = 1;
			for(int i=0; i<ListHeader.getColumnCount(); i++){
				//�R���|�[�l���g�擾
				MiddleCellEditor MiddleCellEditor = (MiddleCellEditor)ListHeader.getCellEditor(0, i);
				DefaultCellEditor DefaultCellEditor = (DefaultCellEditor)MiddleCellEditor.getTableCellEditor(0);
				CheckboxBase CheckboxBase = (CheckboxBase)DefaultCellEditor.getComponent();
				//����SEQ�擾
				int noSeq = Integer.parseInt(CheckboxBase.getPk1());
				//���쏇�ݒ�
				DataCtrl.getInstance().getTrialTblData().SetRetuNo(noSeq, shisaku_no);
				//����No�J�E���g
				shisaku_no++;
			}

		} catch (ExceptionBase e) {
			throw e;

		} catch (Exception e) {
			//�G���[�ݒ�
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			//2011/04/12 QP@10181_No.26 TT T.Satoh Change Start -------------------------
			//ex.setStrErrmsg("����\�@�p�l���N���X�@����񏇐ݒ菈�������s���܂���");
			ex.setStrErrmsg("�z���\�p�l���N���X�@����񏇐ݒ菈�������s���܂���");
			//2011/04/12 QP@10181_No.26 TT T.Satoh Change End ---------------------------
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getStackTrace().toString());
			throw ex;

		}finally{

		}

	}

//mod start --------------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.1
	/************************************************************************************
	 *
	 *   �ő�s���`�F�b�N
	 *    : �ő�s���i�H���s�{�����s�j�̃`�F�b�N���s��
	 *   @param row : �ǉ��s���i�H���ǉ�=2�s, �����ǉ�=1�s�j
	 *   @author TT k-katayama
	 *   @throws ExceptionBase
	 *
	 ************************************************************************************/
	private boolean CheckMaxKoteiGenryoRow(int insRow) throws ExceptionBase {
		boolean blnRet = true;

		try{
			int maxRow = 0;
			TableBase HaigoMeisai = Trial1.getHaigoMeisai();

			//�ő�s�̎擾
			for(int i=0; i<HaigoMeisai.getRowCount(); i++){
				//�H���I��ݒ�
				MiddleCellEditor mcKotei = (MiddleCellEditor)HaigoMeisai.getCellEditor(i, 0);
				DefaultCellEditor tcKotei = (DefaultCellEditor)mcKotei.getTableCellEditor(i);
				if(((JComponent)tcKotei.getComponent()) instanceof CheckboxBase){
					maxRow = i;

				}

				//�����I��ݒ�
				MiddleCellEditor mcGenryo = (MiddleCellEditor)HaigoMeisai.getCellEditor(i, 2);
				DefaultCellEditor tcGenryo = (DefaultCellEditor)mcGenryo.getTableCellEditor(i);
				if(((JComponent)tcGenryo.getComponent()) instanceof CheckboxBase){
					maxRow = i;
				}
			}

			//�ő�s��150�𒴂���ꍇ�A�G���[�������s��
			if (maxRow + insRow + 1 > 150) {
				//�G���[�ݒ�
				String strMessage = JwsConstManager.JWS_ERROR_0024;
				DataCtrl.getInstance().getMessageCtrl().PrintMessageString(strMessage);
				blnRet = false;
			}

		} catch (Exception e) {
			//�G���[�ݒ�
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			//2011/04/12 QP@10181_No.26 TT T.Satoh Change Start -------------------------
			//ex.setStrErrmsg("����\�@�p�l���N���X�@�ő�s���`�F�b�N���������s���܂���");
			ex.setStrErrmsg("�z���\�p�l���N���X�@�ő�s���`�F�b�N���������s���܂���");
			//2011/04/12 QP@10181_No.26 TT T.Satoh Change End ---------------------------
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getStackTrace().toString());
			throw ex;

		}finally{

		}

		return blnRet;
	}
//mod end --------------------------------------------------------------------------------------

	/************************************************************************************
	 *
	 *   �z���\(����\�@)�e�[�u���@�Q�b�^&�Z�b�^
	 *   @author TT nishigawa
	 *   @throws ExceptionBase
	 *
	 ************************************************************************************/
	public Trial1Table getTrial1() {
		return Trial1;
	}
	public void setTrial1(Trial1Table trial1) {
		Trial1 = trial1;
	}

	/************************************************************************************
	 *
	 *   �T���v���������{�^���@�Q�b�^
	 *   @author TT katayama
	 *
	 ************************************************************************************/
	public ButtonBase getBtnSample() {
		return this.btnSample;
	}

	/************************************************************************************
	 *
	 *   ����\�o�̓{�^���@�Q�b�^
	 *   @author TT katayama
	 *
	 ************************************************************************************/
	public ButtonBase getBtnShisakuHyo() {
		return this.btnShisakuHyo;
	}

	/************************************************************************************
	 *
	 *   �h�{�v�Z���{�^���@�Q�b�^
	 *   @author TT katayama
	 *
	 ************************************************************************************/
	public ButtonBase getBtnEiyoKeisan() {
		return this.btnEiyoKeisan;
	}

	/************************************************************************************
	 *
	 *  �����l�A�������Z�e�[�u���ݒ菈��
	 *    :  ���i��d�A������d�A�����[�U�ʁA�����[�U�ʂ�ݒ�
	 *   @author TT nishigawa
	 *   @param �Ȃ�
	 *
	 ************************************************************************************/
	public void setTp2_5TableHiju_tp1(int intSeq){
		try{
			//�H���p�^�[���擾
			String ptKotei = DataCtrl.getInstance().getTrialTblData().SearchShisakuhinData().getStrPt_kotei();

			//�e�ʒP�ʎ擾
			String yoryoTani = DataCtrl.getInstance().getTrialTblData().SearchShisakuhinData().getStrTani();

			//�e�ʒP�ʂ���Value1�擾
			String taniValue1 = "";
			if(yoryoTani == null || yoryoTani.length() == 0){

			}
			else{
				taniValue1 =  DataCtrl.getInstance().getLiteralDataTani().selectLiteralVal1(yoryoTani);
			}

				//�H���p�^�[�����u�󔒁v�̏ꍇ
				if(ptKotei == null || ptKotei.length() == 0){

					//���i��d�@�ҏW�s�i�����l�F�󔒁j
					DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuHiju(
		    				intSeq,
		    				DataCtrl.getInstance().getTrialTblData().checkNullString(null),
		    				DataCtrl.getInstance().getUserMstData().getDciUserid()
		    			);

					//������d�@�ҏW�s�i�����l�F�󔒁j
					DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuSuiHiju(
		    				intSeq,
		    				DataCtrl.getInstance().getTrialTblData().checkNullString(null),
		    				DataCtrl.getInstance().getUserMstData().getDciUserid()
		    			);

					//�u�[�U�ʐ����v�͕ҏW�s��
					DataCtrl.getInstance().getTrialTblData().UpdGenkaValue(
		    				intSeq,
		    				DataCtrl.getInstance().getTrialTblData().checkNullString(null),
		    				DataCtrl.getInstance().getUserMstData().getDciUserid(),
		    				JwsConstManager.JWS_COMPONENT_0134);

					//�u�[�U�ʖ����v�͕ҏW�s��
					DataCtrl.getInstance().getTrialTblData().UpdGenkaValue(
		    				intSeq,
		    				DataCtrl.getInstance().getTrialTblData().checkNullString(null),
		    				DataCtrl.getInstance().getUserMstData().getDciUserid(),
		    				JwsConstManager.JWS_COMPONENT_0135);

				}
				//�H���p�^�[�����u�󔒁v�łȂ��ꍇ
				else{

					//�H���p�^�[����Value1�擾
					String ptValue = DataCtrl.getInstance().getLiteralDataKoteiPtn().selectLiteralVal1(ptKotei);

					//�H���p�^�[�����u�������P�t�^�C�v�v�̏ꍇ-------------------------------------------------------------
					if(ptValue.equals(JwsConstManager.JWS_KOTEITYPE_1)){

						//�e�ʂ��uml�v�̏ꍇ
						if(taniValue1.equals("1")){

							//���i��d�@�ҏW�i�����l�F�󔒁j
							DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuHiju(
				    				intSeq,
				    				DataCtrl.getInstance().getTrialTblData().checkNullString(null),
				    				DataCtrl.getInstance().getUserMstData().getDciUserid()
				    			);

							//������d�@�ҏW�s�i�����l�F�󔒁j
							DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuSuiHiju(
				    				intSeq,
				    				DataCtrl.getInstance().getTrialTblData().checkNullString(null),
				    				DataCtrl.getInstance().getUserMstData().getDciUserid()
				    			);

							//�u�[�U�ʐ����v�͕ҏW�s�i�[�U�ʌv�Z�j
							String keisan1 = DataCtrl.getInstance().getTrialTblData().KeisanZyutenType1(intSeq);
							DataCtrl.getInstance().getTrialTblData().UpdGenkaValue(
				    				intSeq,
				    				DataCtrl.getInstance().getTrialTblData().checkNullString(keisan1),
				    				DataCtrl.getInstance().getUserMstData().getDciUserid(),
				    				JwsConstManager.JWS_COMPONENT_0134);

							//�u�[�U�ʖ����v�͕ҏW�s��
							DataCtrl.getInstance().getTrialTblData().UpdGenkaValue(
				    				intSeq,
				    				DataCtrl.getInstance().getTrialTblData().checkNullString(null),
				    				DataCtrl.getInstance().getUserMstData().getDciUserid(),
				    				JwsConstManager.JWS_COMPONENT_0135);

						}
						//�e�ʂ��ug�v�̏ꍇ
						else if(taniValue1.equals("2")){

							//���i��d�@�ҏW�s�i�����l�F1�j
							DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuHiju(
				    				intSeq,
				    				DataCtrl.getInstance().getTrialTblData().checkNullString("1"),
				    				DataCtrl.getInstance().getUserMstData().getDciUserid()
				    			);

							//������d�@�ҏW�s�i�����l�F�󔒁j
							DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuSuiHiju(
				    				intSeq,
				    				DataCtrl.getInstance().getTrialTblData().checkNullString(null),
				    				DataCtrl.getInstance().getUserMstData().getDciUserid()
				    			);

							//�u�[�U�ʐ����v�͕ҏW�s�i�[�U�ʌv�Z�j
							String keisan1 = DataCtrl.getInstance().getTrialTblData().KeisanZyutenType1(intSeq);
							DataCtrl.getInstance().getTrialTblData().UpdGenkaValue(
				    				intSeq,
				    				DataCtrl.getInstance().getTrialTblData().checkNullString(keisan1),
				    				DataCtrl.getInstance().getUserMstData().getDciUserid(),
				    				JwsConstManager.JWS_COMPONENT_0134);

							//�u�[�U�ʖ����v�͕ҏW�s��
							DataCtrl.getInstance().getTrialTblData().UpdGenkaValue(
				    				intSeq,
				    				DataCtrl.getInstance().getTrialTblData().checkNullString(null),
				    				DataCtrl.getInstance().getUserMstData().getDciUserid(),
				    				JwsConstManager.JWS_COMPONENT_0135);

						}
						//�e�ʂ��u�󔒁v�̏ꍇ�iml,g�ȊO�̏ꍇ�j
						else{

							//���i��d�@�ҏW�s�i�����l�F�󔒁j
							DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuHiju(
				    				intSeq,
				    				DataCtrl.getInstance().getTrialTblData().checkNullString(null),
				    				DataCtrl.getInstance().getUserMstData().getDciUserid()
				    			);

							//������d�@�ҏW�s�i�����l�F�󔒁j
							DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuSuiHiju(
				    				intSeq,
				    				DataCtrl.getInstance().getTrialTblData().checkNullString(null),
				    				DataCtrl.getInstance().getUserMstData().getDciUserid()
				    			);

							//�u�[�U�ʐ����v�͕ҏW�s��
							DataCtrl.getInstance().getTrialTblData().UpdGenkaValue(
				    				intSeq,
				    				DataCtrl.getInstance().getTrialTblData().checkNullString(null),
				    				DataCtrl.getInstance().getUserMstData().getDciUserid(),
				    				JwsConstManager.JWS_COMPONENT_0134);

							//�u�[�U�ʖ����v�͕ҏW�s��
							DataCtrl.getInstance().getTrialTblData().UpdGenkaValue(
				    				intSeq,
				    				DataCtrl.getInstance().getTrialTblData().checkNullString(null),
				    				DataCtrl.getInstance().getUserMstData().getDciUserid(),
				    				JwsConstManager.JWS_COMPONENT_0135);

						}
					}
					//�H���p�^�[�����u�������Q�t�^�C�v�v�̏ꍇ-------------------------------------------------------------
					else if(ptValue.equals(JwsConstManager.JWS_KOTEITYPE_2)){

						//�e�ʂ��uml�v�̏ꍇ
						if(taniValue1.equals("1")){

							//���i��d�@�ҏW�s�i���i��d�@�ҏW�s�i���L�����v�Z�j�j
							String keisan = DataCtrl.getInstance().getTrialTblData().KeisanSeihinHiju(intSeq);
							DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuHiju(
				    				intSeq,
				    				DataCtrl.getInstance().getTrialTblData().checkNullString(keisan),
				    				DataCtrl.getInstance().getUserMstData().getDciUserid()
				    			);

							//������d�@�ҏW�i�󔒁j
							DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuSuiHiju(
				    				intSeq,
				    				DataCtrl.getInstance().getTrialTblData().checkNullString(null),
				    				DataCtrl.getInstance().getUserMstData().getDciUserid()
				    			);

							//�u�[�U�ʐ����v�͕ҏW�s�i�v�Z�j
							String keisan1 = DataCtrl.getInstance().getTrialTblData().KeisanSuisoZyuten(intSeq);
							DataCtrl.getInstance().getTrialTblData().UpdGenkaValue(
				    				intSeq,
				    				DataCtrl.getInstance().getTrialTblData().checkNullString(keisan1),
				    				DataCtrl.getInstance().getUserMstData().getDciUserid(),
				    				JwsConstManager.JWS_COMPONENT_0134);

							//�u�[�U�ʖ����v�͕ҏW�s�i�v�Z�j
							String keisan2 = DataCtrl.getInstance().getTrialTblData().KeisanYusoZyuten(intSeq);
							DataCtrl.getInstance().getTrialTblData().UpdGenkaValue(
				    				intSeq,
				    				DataCtrl.getInstance().getTrialTblData().checkNullString(keisan2),
				    				DataCtrl.getInstance().getUserMstData().getDciUserid(),
				    				JwsConstManager.JWS_COMPONENT_0135);

						}

						//QP@20505 No.2 2012/09/05 TT H.SHIMA ADD -Start
						//�e�ʂ��ug�v�̏ꍇ
						else if(taniValue1.equals("2")){

							//���i��d�@�ҏW�s�i�����l�F1�j
							DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuHiju(
				    				intSeq,
				    				DataCtrl.getInstance().getTrialTblData().checkNullString("1"),
				    				DataCtrl.getInstance().getUserMstData().getDciUserid()
				    			);

							//������d�@�ҏW�s�i�����l�F�󔒁j
							DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuSuiHiju(
				    				intSeq,
				    				DataCtrl.getInstance().getTrialTblData().checkNullString(null),
				    				DataCtrl.getInstance().getUserMstData().getDciUserid()
				    			);

							//�u�[�U�ʐ����v�͕ҏW�s�i�[�U�ʐ����v�Z�j
							String keisan1 = DataCtrl.getInstance().getTrialTblData().KeisanSuisoZyuten(intSeq);
							DataCtrl.getInstance().getTrialTblData().UpdGenkaValue(
				    				intSeq,
				    				DataCtrl.getInstance().getTrialTblData().checkNullString(keisan1),
				    				DataCtrl.getInstance().getUserMstData().getDciUserid(),
				    				JwsConstManager.JWS_COMPONENT_0134);

							//�u�[�U�ʖ����v�͕ҏW�s�i�[�U�ʖ����v�Z�j
							String keisan2 = DataCtrl.getInstance().getTrialTblData().KeisanYusoZyuten(intSeq);
							DataCtrl.getInstance().getTrialTblData().UpdGenkaValue(
				    				intSeq,
				    				DataCtrl.getInstance().getTrialTblData().checkNullString(keisan2),
				    				DataCtrl.getInstance().getUserMstData().getDciUserid(),
				    				JwsConstManager.JWS_COMPONENT_0135);

						}
						//QP@20505 No.2 2012/09/05 TT H.SHIMA ADD -End

						//�e�ʂ��u�󔒁v�̏ꍇ�iml�ȊO�̏ꍇ�j
						else{

							//���i��d�@�ҏW�s�i�����l�F�󔒁j
							DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuHiju(
				    				intSeq,
				    				DataCtrl.getInstance().getTrialTblData().checkNullString(null),
				    				DataCtrl.getInstance().getUserMstData().getDciUserid()
				    			);

							//������d�@�ҏW�s�i�����l�F�󔒁j
							DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuSuiHiju(
				    				intSeq,
				    				DataCtrl.getInstance().getTrialTblData().checkNullString(null),
				    				DataCtrl.getInstance().getUserMstData().getDciUserid()
				    			);

							//�u�[�U�ʐ����v�͕ҏW�s��
							DataCtrl.getInstance().getTrialTblData().UpdGenkaValue(
				    				intSeq,
				    				DataCtrl.getInstance().getTrialTblData().checkNullString(null),
				    				DataCtrl.getInstance().getUserMstData().getDciUserid(),
				    				JwsConstManager.JWS_COMPONENT_0134);

							//�u�[�U�ʖ����v�͕ҏW�s��
							DataCtrl.getInstance().getTrialTblData().UpdGenkaValue(
				    				intSeq,
				    				DataCtrl.getInstance().getTrialTblData().checkNullString(null),
				    				DataCtrl.getInstance().getUserMstData().getDciUserid(),
				    				JwsConstManager.JWS_COMPONENT_0135);

						}

					}

					//�H���p�^�[�����u���̑��E���H�^�C�v�v�̏ꍇ-------------------------------------------------------------
					else if(ptValue.equals(JwsConstManager.JWS_KOTEITYPE_3)){

						//�u�[�U�ʐ����v�͕ҏW��
						DataCtrl.getInstance().getTrialTblData().UpdGenkaValue(
			    				intSeq,
			    				DataCtrl.getInstance().getTrialTblData().checkNullString(null),
			    				DataCtrl.getInstance().getUserMstData().getDciUserid(),
			    				JwsConstManager.JWS_COMPONENT_0134);

						//�u�[�U�ʖ����v�͕ҏW��
						DataCtrl.getInstance().getTrialTblData().UpdGenkaValue(
			    				intSeq,
			    				DataCtrl.getInstance().getTrialTblData().checkNullString(null),
			    				DataCtrl.getInstance().getUserMstData().getDciUserid(),
			    				JwsConstManager.JWS_COMPONENT_0135);

						//�e�ʂ��ug�v�̏ꍇ
						if(taniValue1.equals("2")){

							//���i��d�@�ҏW�s�i�����l�F1�j
							DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuHiju(
				    				intSeq,
				    				DataCtrl.getInstance().getTrialTblData().checkNullString("1"),
				    				DataCtrl.getInstance().getUserMstData().getDciUserid()
				    			);

							//������d�@�ҏW�s�i�����l�F�󔒁j
							DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuSuiHiju(
				    				intSeq,
				    				DataCtrl.getInstance().getTrialTblData().checkNullString(null),
				    				DataCtrl.getInstance().getUserMstData().getDciUserid()
				    			);

						}
						//�e�ʂ��u�󔒁v�̏ꍇ�ig�ȊO�̏ꍇ�j
						else{

							//���i��d�@�ҏW�s�i�����l�F�󔒁j
							DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuHiju(
				    				intSeq,
				    				DataCtrl.getInstance().getTrialTblData().checkNullString(null),
				    				DataCtrl.getInstance().getUserMstData().getDciUserid()
				    			);

							//������d�@�ҏW�s�i�����l�F�󔒁j
							DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuSuiHiju(
				    				intSeq,
				    				DataCtrl.getInstance().getTrialTblData().checkNullString(null),
				    				DataCtrl.getInstance().getUserMstData().getDciUserid()
				    			);
						}
					}
				}
		}
		catch(Exception e){
			e.printStackTrace();

		}
	}
}
