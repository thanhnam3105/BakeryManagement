package jp.co.blueflag.shisaquick.jws.panel;

import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.TableCellEditor;

import jp.co.blueflag.shisaquick.jws.base.BushoData;
import jp.co.blueflag.shisaquick.jws.base.CostMaterialData;
import jp.co.blueflag.shisaquick.jws.base.HansekiData;
import jp.co.blueflag.shisaquick.jws.base.KaishaData;
import jp.co.blueflag.shisaquick.jws.base.LiteralData;
import jp.co.blueflag.shisaquick.jws.base.ManufacturingData;
import jp.co.blueflag.shisaquick.jws.base.MixedData;
import jp.co.blueflag.shisaquick.jws.base.PrototypeData;
import jp.co.blueflag.shisaquick.jws.base.PrototypeListData;
import jp.co.blueflag.shisaquick.jws.base.ShisanData;
import jp.co.blueflag.shisaquick.jws.base.TrialData;
import jp.co.blueflag.shisaquick.jws.base.XmlData;
import jp.co.blueflag.shisaquick.jws.celleditor.MiddleCellEditor;
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
import jp.co.blueflag.shisaquick.jws.label.DispTitleLabel;
import jp.co.blueflag.shisaquick.jws.label.HaihunLabel;
import jp.co.blueflag.shisaquick.jws.label.HeaderLabel;
import jp.co.blueflag.shisaquick.jws.label.ItemIndicationLabel;
import jp.co.blueflag.shisaquick.jws.label.ItemLabel;
import jp.co.blueflag.shisaquick.jws.label.LevelLabel;
import jp.co.blueflag.shisaquick.jws.manager.DownloadPathData;
import jp.co.blueflag.shisaquick.jws.manager.UrlConnection;
import jp.co.blueflag.shisaquick.jws.manager.XmlConnection;
import jp.co.blueflag.shisaquick.jws.tab.TrialTab;
import jp.co.blueflag.shisaquick.jws.textbox.HankakuTextbox;
import jp.co.blueflag.shisaquick.jws.textbox.NumelicTextbox;

/************************************************************************************************
 *
 * ����f�[�^��ʃp�l���N���X
 * @author TT nishigawa
 *
 ***********************************************************************************************/
public class TrialDispPanel extends PanelBase {
	private static final long serialVersionUID = 1L;

	//�G���[����
	private ExceptionBase ex;

	//���ڃR���|�[�l���g
	private DispTitleLabel DispTitleLabel;
	private LevelLabel LevelLabel;
	private HaihunLabel HaihunLabel;
	private HeaderLabel HeaderLabel;

	//�f�[�^&�ʐM�I�u�W�F�N�g
	private XmlConnection xcon;
	private XmlData xmlJW010;
	private XmlData xmlJW020;
	private XmlData xmlJW030;
	private XmlData xmlJW040;
	private XmlData xmlJW050;
	private XmlData xmlJW060;
	private XmlData xmlJW070;
	private XmlData xmlJ010;
	private XmlData xmlJW120;		//����\�o��
	private XmlData xmlJW130;		//�T���v���������o��
	private XmlData xmlJW740;		//�h�{�v�Z���o��
	private XmlData xmlJWuser;
	private PrototypeData PrototypeData;
	private TrialData TrialData = null;
	private ArrayList aryTrialData = new ArrayList();

	//����&���@No
	private String seiho_user = null;
	private String seiho_shubetu = null;
	private String seiho_nen = null;
	private String seiho_num = null;
	private String seiho_Shisan = null;
	private String seiho_kakutei = null;

	//�R���|�[�l���g�ݒ�
	private String strTitle = "����f�[�^���";
	private int red = 0xff;
	private int green = 0xff;
	private int blue = 0xff;

	//��ʓ��R���|�[�l���g
	private TrialTab tb;
	private TextboxBase txtShisakuUser;
	private TextboxBase txtShisakuNen;
	private TextboxBase txtShisakuOi;
	private TextboxBase txtIrai;
	private ComboBase cmbShubetuNo;
	private TextboxBase txtHinnm;
	private TextboxBase txtSeihoUser;
	private TextboxBase txtSeihoShu;
	private TextboxBase txtSeihoNen;
	private TextboxBase txtSeihoOi;
	private ItemIndicationLabel ilTorokuHi;
	private ItemIndicationLabel ilKosinHi;
	private ItemIndicationLabel ilTorokuSha;
	private ItemIndicationLabel iiShisanHi;
	private ItemIndicationLabel ilKosinSha;
	private ItemIndicationLabel iiKakuteiHi;
	//�yQP@20505_No.38�z2012/10/17 TT H.SHIMA ADD Start
	private ItemIndicationLabel ilSecret;
	//�yQP@20505_No.38�z2012/10/17 TT H.SHIMA ADD End
	private CheckboxBase chkHaisi;
	private ButtonBase btnToroku;
	private ButtonBase btnShuryo;
	private ButtonBase btnTcopy;
	private ButtonBase btnZcopy;
	private ButtonBase btnCtrlZ;
	//�yQP@20505_No.38�z2012/10/17 TT H.SHIMA ADD Start
	private ButtonBase btnSecret;
	//�yQP@20505_No.38�z2012/10/17 TT H.SHIMA ADD End

//2011/05/30�@�yQP@10181_No.42_49_72�z�H���p�^�[������@TT.NISHIGAWA�@start
	private ComboBase cmbKoteiPtn;
//2011/05/30�@�yQP@10181_No.42_49_72�z�H���p�^�[������@TT.NISHIGAWA�@end
// ADD start 20121009 QP@20505 No.24
	JTabbedPane jtab;
// ADD end 201210093 QP@20505 No.24

	/*******************************************************************************************
	 *
	 * ����f�[�^��ʃp�l�� �R���X�g���N�^
	 * @throws ExceptionBase
	 * @author TT nishigawa
	 *
	 ******************************************************************************************/
	public TrialDispPanel() throws ExceptionBase {
		//�X�[�p�[�N���X�̃R���X�g���N�^�Ăяo��
		super();

		try {
			//���[�h�擾
			String mode = DataCtrl.getInstance().getParamData().getStrMode();

			//���[�U���擾
			this.conUserInfo();

			//�f�[�^�ݒ�iJW010�j
			this.conJW010();

			//�p�l����ʂ̐ݒ�
			this.panelDisp();

			//�f�[�^�̐ݒ�
			this.setPanelData();

			//�^�u�̏����I��
			if(mode.equals(JwsConstManager.JWS_MODE_0002)){
				tb.setSelectedIndex(2);
			}

			//2011/06/07 QP@10181_No.41 TT T.Satoh Add Start -------------------------
			//�z���\�̎��얾�׃e�[�u���̉��X�N���[���o�[�̍ő�l���擾
			int hHaigoBarMax = tb.getTrial1Panel().getTrial1().getScrollMain().getHorizontalScrollBar().getMaximum();

			//�z���\�̎��얾�׃e�[�u���̉��X�N���[���o�[�̈ʒu��ݒ�
			tb.getTrial1Panel().getTrial1().getScrollMain().getHorizontalScrollBar().setValue(hHaigoBarMax);
			//2011/06/07 QP@10181_No.41 TT T.Satoh Add End ---------------------------

			//�^�u�I���C�x���g�ǉ�
			tb.addChangeListener(new ChangeListener() {
				public void stateChanged(ChangeEvent e) {

					//�������Z��� �t�H�[�J�X����
			    	TableBase GenkaTable = tb.getTrial5Panel().getTable();
			    	TableCellEditor GenkaTableEditor = GenkaTable.getCellEditor();
					if(GenkaTableEditor != null){
						GenkaTable.getCellEditor().stopCellEditing();
					}
					GenkaTable.clearSelection();

					try{
// MOD start 20121009 QP@20505 No.24
//						JTabbedPane jtab = (JTabbedPane)e.getSource();
						jtab = (JTabbedPane)e.getSource();
// MOD end 20121009 QP@20505 No.24
					    int sindex = jtab.getSelectedIndex();

				    	//�����l(����\�A)��� �������Z�X�V����
				    	tb.getTrial2Panel().updGenkaShisan();
				    	//��{���(����\�B)��� �������Z�X�V����
				    	tb.getTrial3Panel().updGenkaShisan();
				    	//�������Z��� �������Z�X�V����
				    	tb.getTrial5Panel().updGenkaShisan();

					    if(sindex == 0){

					    	//����(kg)�v�Z���ʂ����.����(kg)�ɕ\������
					    	tb.getTrial1Panel().getTrial1().DispGenryohi();

					    	//2011/06/07 QP@10181_No.41 TT T.Satoh Add Start -------------------------
							//�z���\�̎��얾�׃e�[�u���̉��X�N���[���o�[�̍ő�l���擾
							int hHaigoBarMax = tb.getTrial1Panel().getTrial1().getScrollMain().getHorizontalScrollBar().getMaximum();

							//�z���\�̎��얾�׃e�[�u���̉��X�N���[���o�[�̈ʒu��ݒ�
							tb.getTrial1Panel().getTrial1().getScrollMain().getHorizontalScrollBar().setValue(hHaigoBarMax);
							//2011/06/07 QP@10181_No.41 TT T.Satoh Add End ---------------------------

					    }if(sindex == 1){

					    	//�e�[�u�����擾
							TableBase HaigoMeisai = tb.getTrial1Panel().getTrial1().getHaigoMeisai();
							TableBase ListMeisai = tb.getTrial1Panel().getTrial1().getListMeisai();
							TableBase ListHeader = tb.getTrial1Panel().getTrial1().getListHeader();

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
							HaigoMeisai.clearSelection();

							//���얾�ׂ̑I�������N���A
							TableCellEditor lmEditor = ListMeisai.getCellEditor();
							if(lmEditor != null){
								ListMeisai.getCellEditor().stopCellEditing();
							}
							ListMeisai.clearSelection();

							//--------------------------- �����l(����\�A)�p�l���ݒ� -----------------------------------
							//�����v�Z
							Trial2Panel tp2 = new Trial2Panel();
// ADD start 20121017 QP@20505 No.11
							CheckboxBase chkAuto = tp2.getChkAuto();
							chkAuto.addActionListener(getActionEvent());
							chkAuto.setActionCommand("autoClick");
							ButtonBase btnBunsekiMstData = tp2.getCmdBunsekiMstData();
							btnBunsekiMstData.addActionListener(getActionEvent());
							btnBunsekiMstData.setActionCommand("click_getBunsekiMstData");
// ADD end 20121017 QP@20505 No.11

							//�ĕ\��
							tb.setTrial2Panel(tp2);
							jtab.setComponentAt(1, tp2);

					    }if(sindex == 2){

//add start -------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.7
					    	tb.getTrial1Panel().getTrial1().AutoCopyKeisan();
//add end   -------------------------------------------------------------------------------

					    }if(sindex == 3){
					    	//�������Z(����\�D)��ʏ���

					    	//�e�[�u�����擾
							TableBase HaigoMeisai = tb.getTrial1Panel().getTrial1().getHaigoMeisai();
							TableBase ListMeisai = tb.getTrial1Panel().getTrial1().getListMeisai();
							TableBase ListHeader = tb.getTrial1Panel().getTrial1().getListHeader();

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
							HaigoMeisai.clearSelection();
							//���얾�ׂ̑I�������N���A
							TableCellEditor lmEditor = ListMeisai.getCellEditor();
							if(lmEditor != null){
								ListMeisai.getCellEditor().stopCellEditing();
							}
							ListMeisai.clearSelection();

							//--------------------------- �������Z(����\�D)�p�l���ݒ� -----------------------------------

							//�������Z(����\�D)�ĕ\��
							Trial5Panel tp5 = new Trial5Panel();

							//�������Z(����\�D)��ʂ̃R���{�{�b�N�X�l��ݒ�
							tp5.selectCmbShisanKakutei(tb.getTrial5Panel().getCmbShisanKakutei().getSelectedIndex());

							//�������Z�\����{�^���C�x���g���� �Đݒ�
							tp5.getBtnShisanHyo().addActionListener(getActionEvent());
							tp5.getBtnShisanHyo().setActionCommand("shisanHyo");

							//�������Z�o�^�{�^���C�x���g���� �Đݒ�
							tp5.getBtnShisanToroku().addActionListener(getActionEvent());
							tp5.getBtnShisanToroku().setActionCommand("shisanToroku");

							//�ĕ\��
							tb.setTrial5Panel(tp5);
							jtab.setComponentAt(3, tp5);

							//�H���`�F�b�N
							int intKoteiChk = DataCtrl.getInstance().getTrialTblData().CheckKotei();

//mod start -------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.33,34
//							if ( intKoteiChk == 0 || intKoteiChk == -1 || intKoteiChk > 2 ) {
//								//�s����
//
//								//�x�����b�Z�[�W�\��
//								tb.transferFocus();
//								String strMessage = JwsConstManager.JWS_ERROR_0023;
//								DataCtrl.getInstance().getMessageCtrl().PrintMessageString(strMessage);
//
//							}

							//�ĕ\�������ꍇ
							if(JwsConstManager.JWS_FLG_DISP){
								JwsConstManager.JWS_FLG_DISP = false;
							}
							//�ĕ\���łȂ��ꍇ
							else{
								if ( intKoteiChk == 0 || intKoteiChk == -1 || intKoteiChk > 2 ) {
									//�s����

									//�x�����b�Z�[�W�\��
									tb.transferFocus();
									String strMessage = JwsConstManager.JWS_ERROR_0023;
									DataCtrl.getInstance().getMessageCtrl().PrintMessageString(strMessage);

								}
							}
//mod end   -------------------------------------------------------------------------------

					    }

					}catch(Exception ex){

					}finally{

					}
				}
			});

		} catch (ExceptionBase e) {
			e.printStackTrace();
			throw e;

		} catch (Exception e) {
			//�G���[�ݒ�
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			ex.setStrErrmsg("����f�[�^��ʃp�l�� �R���X�g���N�^���������s���܂���");
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}
	}

	/*******************************************************************************************
	 *
	 * ����f�[�^��ʐݒ�
	 * @throws ExceptionBase
	 * @author TT nishigawa
	 *
	 ******************************************************************************************/
	public void setPanelData() throws ExceptionBase{
		try{
			//����i�f�[�^�擾
			PrototypeData = DataCtrl.getInstance().getTrialTblData().SearchShisakuhinData();

			//����i�f�[�^���ɐ��@���삪�o�^����Ă���ꍇ
			if(PrototypeData.getIntSeihoShisaku() > 0){

				//�����f�[�^����
				aryTrialData = DataCtrl.getInstance().getTrialTblData().SearchShisakuRetuData(PrototypeData.getIntSeihoShisaku());

				//�����f�[�^�����݂���ꍇ
				if(!aryTrialData.isEmpty()){

					//�����f�[�^�擾
					TrialData = new TrialData();
					TrialData = (TrialData)aryTrialData.get(0);

					//���@No�擾�i���@No-1�j
					String seiho = TrialData.getStrSeihoNo1();
					String[] seiho_sp = seiho.split("-");

					if ( seiho_sp.length == 4 ) {
						seiho_user = seiho_sp[0];
						seiho_shubetu = seiho_sp[1];
						seiho_nen = seiho_sp[2];
						seiho_num = seiho_sp[3];
					}
				}
			//����i�f�[�^���ɐ��@���삪�o�^����Ă��Ȃ��ꍇ
			}else{
				seiho_user = null;
				seiho_shubetu = null;
				seiho_nen = null;
				seiho_num = null;
			}

			//�^�u�ݒ�
			String strKohinHi = DataCtrl.getInstance().getTrialTblData().SearchShisakuhinData().getStrKosinhi();
			tb.setTrialPane();
			DataCtrl.getInstance().getTrialTblData().SearchShisakuhinData().setStrKosinhi(strKohinHi);

			//����R�[�h
			txtShisakuUser.setText(checkNull(PrototypeData.getDciShisakuUser()));
			txtShisakuNen.setText(checkNull(PrototypeData.getDciShisakuYear()));
			txtShisakuOi.setText(checkNull(PrototypeData.getDciShisakuNum()));

			//�˗��ԍ�
			txtIrai.setText(checkNull(PrototypeData.getStrIrai()));

			//���No
			setLiteralCmb(
					cmbShubetuNo
					,DataCtrl.getInstance().getLiteralDataShubetuNo()
					, ""
					, 1);
			cmbShubetuNo.setSelectedItem(PrototypeData.getStrShubetuNo());
			//txtShubetuNo.setText(checkNull(PrototypeData.getStrShubetuNo()));

			//�yQP@20505_No.38�z2012/10/17 TT H.SHIMA ADD Start
			if(PrototypeData.getStrSecret() != null){
				ilSecret.setText("ON");
				ilSecret.setBackground(JwsConstManager.SHISAKU_ITEM_COLOR3);
			}else{
				ilSecret.setText("OFF");
				ilSecret.setBackground(JwsConstManager.SHISAKU_ITEM_COLOR4);
			}
			//�yQP@20505_No.38�z2012/10/17 TT H.SHIMA ADD End

			//�i��
			txtHinnm.setText(checkNull(PrototypeData.getStrHinnm()));

			//���@NO
			txtSeihoUser.setText(checkNull(seiho_user));
			txtSeihoShu.setText(checkNull(seiho_shubetu));
			txtSeihoNen.setText(checkNull(seiho_nen));
			txtSeihoOi.setText(checkNull(seiho_num));

			//�o�^��
			ilTorokuHi.setText(checkNull(PrototypeData.getStrTorokuhi()));

			//�X�V��
			//UPD 2012.4.30�@�yH24�N�x�Ή��z�X�V���Ŏ���i�̍X�V�r������̂��߁A�T�[�o�T�C�h�̍X�V����ҏW
//			ilKosinHi.setText(checkNull(PrototypeData.getStrKosinhi()));
			String strKoshin = checkNull(PrototypeData.getStrKosinhi());
			if(strKoshin.length() >= 10 ){
				strKoshin = strKoshin.substring(0,10);
				strKoshin = strKoshin.replace('-', '/');
			}
			ilKosinHi.setText(strKoshin);

			//�o�^��
			ilTorokuSha.setText(checkNull(PrototypeData.getStrKosinNm()));

			//���Z��
			iiShisanHi.setText(checkNull(seiho_Shisan));

			//�X�V��
			ilKosinSha.setText(checkNull(PrototypeData.getStrTorokuNm()));

			//���Z�m���
			iiKakuteiHi.setText(checkNull(seiho_kakutei));

			//�p�~
			if(PrototypeData.getIntHaisi() > 0){
				chkHaisi.setSelected(true);
			}

			//--------------------------- ��{���(����\�B)�p�l���ݒ� -----------------------------------
			//�S����Ёi���[�h�ҏW�j
			if(DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
					JwsConstManager.JWS_COMPONENT_0102, DataCtrl.getInstance().getParamData().getStrMode())){
				tb.getTrial3Panel().getCmbKaisha().addFocusListener(new FocusCheck("�S�����"));
			}

			//�S���H��i���[�h�ҏW�j
			if(DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
					JwsConstManager.JWS_COMPONENT_0103, DataCtrl.getInstance().getParamData().getStrMode())){
				tb.getTrial3Panel().getCmbKojo().addFocusListener(new FocusCheck("�S���H��"));
			}

			//�����w��
			boolean edit_fg = true;
			if(DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
					JwsConstManager.JWS_COMPONENT_0101, DataCtrl.getInstance().getParamData().getStrMode())){
				//tb.getTrial3Panel().getCmbShosu().addFocusListener(new FocusCheck("�����w��"));

//mod start -------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.33,34
				//�����z��擾
				ArrayList aryRetu = DataCtrl.getInstance().getTrialTblData().SearchShisakuRetuData(0);
				//����񃋁[�v
				for(int i=0; i<aryRetu.size(); i++){
					//�����f�[�^�擾
					TrialData TrialData = (TrialData)aryRetu.get(i);
					int intShisakuSeq = TrialData.getIntShisakuSeq();
					//��L�[���ڎ擾
					boolean chk = DataCtrl.getInstance().getTrialTblData().checkShisakuIraiKakuteiFg(intShisakuSeq);
					//�ҏW�\�̏ꍇ�F��������
					if(chk){
						tb.getTrial3Panel().getCmbShosu().addFocusListener(new FocusCheck("�����w��"));
					}
					//�ҏW�s�̏ꍇ�F��������
					else{
						edit_fg = false;

						//�����w��ҏW�s��
						tb.getTrial3Panel().getCmbShosu().setEnabled(false);
						tb.getTrial3Panel().getCmbShosu().setBackground(JwsConstManager.JWS_DISABLE_COLOR);
						//�e�ʕҏW�s��
						tb.getTrial3Panel().getCmbYoryo().setEnabled(false);
						tb.getTrial3Panel().getCmbYoryo().setBackground(JwsConstManager.JWS_DISABLE_COLOR);
						tb.getTrial3Panel().getCmbYoryo().getEditor().getEditorComponent().setBackground(JwsConstManager.JWS_DISABLE_COLOR);
						//�e�ʒP�ʕҏW�s��
						tb.getTrial3Panel().getCmbtani().setEnabled(false);
						tb.getTrial3Panel().getCmbtani().setBackground(JwsConstManager.JWS_DISABLE_COLOR);
					}
				}
//add end   -------------------------------------------------------------------------------

			}

//2011/05/11 QP@10181_No.42_49_73 TT Nishigawa Add Start -------------------------
			//�H���p�^�[��
			if(edit_fg){

				//���[�h�ҏW
				if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
						JwsConstManager.JWS_COMPONENT_0153, DataCtrl.getInstance().getParamData().getStrMode())){
					cmbKoteiPtn.setBackground(Color.lightGray);
					cmbKoteiPtn.setEnabled(false);
				}
				else{
					//�H���p�^�[���ҏW��
					cmbKoteiPtn.setBackground(Color.white);
					cmbKoteiPtn.setEnabled(true);
				}
			}
			else{
				//�H���p�^�[���ҏW�s��
				cmbKoteiPtn.setBackground(Color.lightGray);
				cmbKoteiPtn.setEnabled(false);
			}
//2011/05/11 QP@10181_No.42_49_73 TT Nishigawa Add Start -------------------------


//2011/05/11 QP@10181_No.42_49_73 TT Nishigawa Add Start -------------------------
			//�e�ʒP��
			if(DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
					JwsConstManager.JWS_COMPONENT_0110, DataCtrl.getInstance().getParamData().getStrMode())){
				tb.getTrial3Panel().getCmbtani().addFocusListener(new FocusCheck("�e�ʒP��"));
			}
//2011/05/11 QP@10181_No.42_49_73 TT Nishigawa Add End --------------------------

			//--------------------------- �z���\(����\�@)�p�l�� ���[�o�� �ݒ� -----------------------------------
			//�T���v��������
			if(DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
					JwsConstManager.JWS_COMPONENT_0049, DataCtrl.getInstance().getParamData().getStrMode())){
				tb.getTrial1Panel().getBtnSample().addActionListener(this.getActionEvent());
				tb.getTrial1Panel().getBtnSample().setActionCommand("sampleSetumei");
			}

			//����\
			if(DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
					JwsConstManager.JWS_COMPONENT_0048, DataCtrl.getInstance().getParamData().getStrMode())){
				tb.getTrial1Panel().getBtnShisakuHyo().addActionListener(this.getActionEvent());
				tb.getTrial1Panel().getBtnShisakuHyo().setActionCommand("shisakuHyo");
			}

			//�h�{�v�Z��
			if(DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
					JwsConstManager.JWS_COMPONENT_0126, DataCtrl.getInstance().getParamData().getStrMode())){
				tb.getTrial1Panel().getBtnEiyoKeisan().addActionListener(this.getActionEvent());
				tb.getTrial1Panel().getBtnEiyoKeisan().setActionCommand("eiyoKeisan");
			}

			//--------------------------- ���Z���E���Z�m�� �ݒ� -----------------------------------

			//�ŏI���Z�m������Z�f�[�^���擾
			ShisanData shisanData = DataCtrl.getInstance().getShisanRirekiKanriData().SearchLastShisanData();

			//�ŏI���Z�m�肵�����Z���ƃT���v��No���A��ʍ��ڂ̎��Z���Ǝ��Z�m��ɐݒ�
			iiShisanHi.setText(shisanData.getStrShisanHi());
			if ( shisanData.getStrSampleNo() != null ) {
				iiKakuteiHi.setText(shisanData.getStrSampleNo());

			} else {
				iiKakuteiHi.setText("");

			}

//2011/05/11 QP@10181_No.42_49_73 TT Nishigawa Add Start -------------------------
			//�H���p�^�[��
			int listCount = cmbKoteiPtn.getActionListeners().length;
			for(int i=0; i<listCount; i++){
				cmbKoteiPtn.removeActionListener(cmbKoteiPtn.getActionListeners()[0]);
			}
			//cmbKoteiPtn.removeActionListener(cmbKoteiPtn.getActionListeners());

			//�H���p�^�[��
			setLiteralCmb(
					cmbKoteiPtn
					,DataCtrl.getInstance().getLiteralDataKoteiPtn()
					, PrototypeData.getStrPt_kotei()
					, 0);
			//�A�N�V�������X�i�[�ǉ�
			cmbKoteiPtn.addActionListener(getActionEvent());
			cmbKoteiPtn.setActionCommand("�H���p�^�[��");

			//setGenkaShisanMark();
//2011/05/11 QP@10181_No.42_49_73 TT Nishigawa Add Start -------------------------

		}catch(Exception e){
			e.printStackTrace();

			//�G���[�ݒ�
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			ex.setStrErrmsg("����f�[�^��ʐݒ菈�������s���܂���");
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getMessage());
			throw ex;
		}
	}

	/*******************************************************************************************
	 *
	 * ����f�[�^��ʃp�l���\��
	 * @throws ExceptionBase
	 * @author TT nishigawa
	 *
	 ******************************************************************************************/
	private void panelDisp() throws ExceptionBase{
		try{
			//------------------------------ �`�惂�[�h�ݒ�  -------------------------------------
			UIManager.setLookAndFeel(JwsConstManager.UI_CLASS_NAME);

			//-------------------------------- �p�l���ݒ�   --------------------------------------
			this.setLayout(null);
			this.setBackground(new Color(red,green,blue));

			//------------------------------ �^�C�g�����x���ݒ�  -----------------------------------
			DispTitleLabel = new DispTitleLabel();
			DispTitleLabel.setText(strTitle);
			this.add(DispTitleLabel);

			//------------------------------ JWS�o�[�W�����ݒ�  ----------------------------------
			ItemLabel VerLabel = new ItemLabel();
			VerLabel.setBounds(160, 5, 50, 15);
			VerLabel.setBackground(Color.white);
			VerLabel.setText(JwsConstManager.JWS_VERSION);
			this.add(VerLabel);

			//------------------------------- ���x�����x���ݒ�  -----------------------------------
			LevelLabel = new LevelLabel();
			LevelLabel.setBounds(963, 5, 50, 15);
			this.add(LevelLabel);

			//------------------------------- �w�b�_���x���ݒ�   -----------------------------------
			HeaderLabel = new HeaderLabel();
			HeaderLabel.setBounds(215, 5, 745, 15);
			this.add(HeaderLabel);

			//-------------------------- ���ڃ��x���ݒ�i����R�[�h�j  -------------------------------
			ItemLabel hlShisaku = new ItemLabel();
			hlShisaku.setBounds(5, 25, 60, 19);
			hlShisaku.setText("���캰��");
			this.add(hlShisaku);

			//����R�[�h�i���[�UID�j
			txtShisakuUser = new TextboxBase();
			txtShisakuUser.setBounds(65, 25, 80, 20);

			//���[�h�ҏW
			if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
					JwsConstManager.JWS_COMPONENT_0001, DataCtrl.getInstance().getParamData().getStrMode())){
				txtShisakuUser.setBounds(65, 25, 80, 19);
				txtShisakuUser.setBackground(Color.lightGray);
				txtShisakuUser.setEditable(false);
			}
			this.add(txtShisakuUser);


			//����R�[�h�i�n�C�t���j
			HaihunLabel = new HaihunLabel();
			HaihunLabel.setBounds(145, 25, 15, 19);
			this.add(HaihunLabel);

			//����R�[�h�i�N�j
			txtShisakuNen = new TextboxBase();
			txtShisakuNen.setBounds(160, 25, 30, 20);
			txtShisakuNen.setEditable(false);
			//���[�h�ҏW
			if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
					JwsConstManager.JWS_COMPONENT_0002, DataCtrl.getInstance().getParamData().getStrMode())){
				txtShisakuNen.setBounds(160, 25, 30, 19);
				txtShisakuNen.setBackground(Color.lightGray);
				txtShisakuNen.setEditable(false);
			}
			this.add(txtShisakuNen);

			//����R�[�h�i�n�C�t���j
			HaihunLabel = new HaihunLabel();
			HaihunLabel.setBounds(190, 25, 15, 20);
			this.add(HaihunLabel);

			//����R�[�h�i�ǔԁj
			txtShisakuOi = new TextboxBase();
			txtShisakuOi.setBounds(205, 25, 30, 20);
			txtShisakuOi.setEditable(false);
			//���[�h�ҏW
			if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
					JwsConstManager.JWS_COMPONENT_0003, DataCtrl.getInstance().getParamData().getStrMode())){
				txtShisakuOi.setBounds(205, 25, 30, 19);
				txtShisakuOi.setBackground(Color.lightGray);
				txtShisakuOi.setEditable(false);
			}
			this.add(txtShisakuOi);

			//-------------------------- ���ڃ��x���ݒ�i�˗��ԍ��j  -------------------------------
			ItemLabel hlIrai = new ItemLabel();
//mod start --------------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.1
			//hlIrai.setBounds(270, 25, 60, 19);
			//hlIrai.setText("�˗��ԍ�");
			hlIrai.setBounds(254, 25, 76, 19);
			hlIrai.setText("�˗��ԍ�IR@");
//mod start --------------------------------------------------------------------------------------
			this.add(hlIrai);

			//�˗��ԍ�
			txtIrai = new TextboxBase();
			txtIrai.setBounds(330, 25, 110, 20);
			//���[�h�ҏW
			if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
					JwsConstManager.JWS_COMPONENT_0004, DataCtrl.getInstance().getParamData().getStrMode())){
				txtIrai.setBounds(330, 25, 110, 19);
				txtIrai.setBackground(Color.lightGray);
				txtIrai.setEditable(false);
			}else{
				txtIrai.addFocusListener(new FocusCheck("�˗��ԍ�"));
			}
			this.add(txtIrai);

			//----------------------------------- �{�^��  ---------------------------------------
			//�o�^�{�^��
			btnToroku = new ButtonBase("�o�^");
			btnToroku.addActionListener(this.getActionEvent());
			btnToroku.setActionCommand("toroku");
			btnToroku.setBounds(445, 25, 80, 38);
			//���[�h�ҏW
			if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
					JwsConstManager.JWS_COMPONENT_0011, DataCtrl.getInstance().getParamData().getStrMode())){
				btnToroku.setEnabled(false);
			}
			this.add(btnToroku);

			//�I���{�^��
			btnShuryo = new ButtonBase("�I��");
			btnShuryo.addActionListener(this.getActionEvent());
			btnShuryo.setActionCommand("shuryo");
			btnShuryo.setBounds(525, 25, 80, 38);
			//���[�h�ҏW
			if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
					JwsConstManager.JWS_COMPONENT_0012, DataCtrl.getInstance().getParamData().getStrMode())){
				btnShuryo.setEnabled(false);
			}
			this.add(btnShuryo);

			//�����R�s�[�{�^��
			btnTcopy = new ButtonBase("������߰");
			btnTcopy.addActionListener(this.getActionEvent());
			btnTcopy.setActionCommand("copy_tokutyo");
			btnTcopy.setBounds(445, 63, 80, 38);
			//���[�h�ҏW
			if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
					JwsConstManager.JWS_COMPONENT_0013, DataCtrl.getInstance().getParamData().getStrMode())){
				btnTcopy.setEnabled(false);
			}
			this.add(btnTcopy);

			//�S�R�s�[�{�^��
			btnZcopy = new ButtonBase("�S��߰");
			btnZcopy.addActionListener(this.getActionEvent());
			btnZcopy.setActionCommand("copy_all");
			btnZcopy.setBounds(525, 63, 80, 38);
			//���[�h�ҏW
			if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
					JwsConstManager.JWS_COMPONENT_0014, DataCtrl.getInstance().getParamData().getStrMode())){
				btnZcopy.setEnabled(false);
			}
			this.add(btnZcopy);

			//--------------------------- ���ڃ��x���ݒ�i�o�^���j  --------------------------------
			ItemLabel hlTorokuHi = new ItemLabel();
			hlTorokuHi.setBounds(610, 25, 50, 15);
			hlTorokuHi.setText("�o�^��");
			this.add(hlTorokuHi);
			//�o�^��
			ilTorokuHi = new ItemIndicationLabel();
			//�yQP@20505_No.38�z2012/10/17 TT H.SHIMA MOD Start
//			ilTorokuHi.setBounds(660, 25, 145, 15);
			ilTorokuHi.setBounds(660, 25, 100, 15);
			//�yQP@20505_No.38�z2012/10/17 TT H.SHIMA MOD End
			this.add(ilTorokuHi);

			//-------------------------- ���ڃ��x���ݒ�i��ʔԍ��j  -------------------------------
			ItemLabel hlShubetu = new ItemLabel();
			//�yQP@20505_No.38�z2012/10/17 TT H.SHIMA MOD Start
//			hlShubetu.setBounds(807, 25, 60, 35);
			hlShubetu.setBounds(762, 25, 60, 35);
			//�yQP@20505_No.38�z2012/10/17 TT H.SHIMA MOD End
			hlShubetu.setBackground(JwsConstManager.SHISAKU_ITEM_COLOR2);
			hlShubetu.setText("��ʔԍ�");
			this.add(hlShubetu);
			//��ʔԍ�
			cmbShubetuNo = new ComboBase();

			//�yQP@20505_No.38�z2012/10/17 TT H.SHIMA MOD Start
//			cmbShubetuNo.setBounds(867, 25, 70, 35);
			cmbShubetuNo.setBounds(822, 25, 70, 35);
			//�yQP@20505_No.38�z2012/10/17 TT H.SHIMA MOD End
			//���[�h�ҏW
			if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
					JwsConstManager.JWS_COMPONENT_0015, DataCtrl.getInstance().getParamData().getStrMode())){
				//�yQP@20505_No.38�z2012/10/17 TT H.SHIMA MOD Start
//				cmbShubetuNo.setBounds(867, 25, 70, 35);
				cmbShubetuNo.setBounds(822, 25, 70, 35);
				//�yQP@20505_No.38�z2012/10/17 TT H.SHIMA MOD End
				cmbShubetuNo.setBackground(Color.lightGray);
				cmbShubetuNo.setEnabled(false);
			}else{
				cmbShubetuNo.addFocusListener(new FocusCheck("��ʔԍ�"));
			}
			this.add(cmbShubetuNo);

			//�yQP@20505_No.38�z2012/10/17 TT H.SHIMA ADD Start
			//--------------------------- �V�[�N���b�g���x���ݒ�   ---------------------------------
			ItemLabel hlSecret = new ItemLabel();
			hlSecret.setBounds(897, 25, 75, 15);
			hlSecret.setText("�V�[�N���b�g");
			this.add(hlSecret);
			ilSecret = new ItemIndicationLabel();
			ilSecret.setBounds(972, 25, 44, 15);
			this.add(ilSecret);
			btnSecret = new ButtonBase("�V�[�N���b�g�ݒ�");
			btnSecret.setMargin(new Insets(0, 0, 0, 0));
			btnSecret.addActionListener(this.getActionEvent());
			btnSecret.setActionCommand("secret");
			btnSecret.setBounds(897, 40, 119, 20);
			//���[�h�ҏW
			if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
					JwsConstManager.JWS_COMPONENT_0190, DataCtrl.getInstance().getParamData().getStrMode())){
				btnSecret.setEnabled(false);
			}
			this.add(btnSecret);
			//�yQP@20505_No.38�z2012/10/17 TT H.SHIMA ADD End

			//--------------------------- ���ڃ��x���ݒ�i�X�V���j  --------------------------------
			ItemLabel hlKosinHi = new ItemLabel();
			hlKosinHi.setBounds(610, 45, 50, 15);
			hlKosinHi.setText("�X�V��");
			this.add(hlKosinHi);
			//�X�V��
			ilKosinHi = new ItemIndicationLabel();
			//�yQP@20505_No.38�z2012/10/17 TT H.SHIMA MOD Start
//			ilKosinHi.setBounds(660, 45, 145, 15);
			ilKosinHi.setBounds(660, 45, 100, 15);
			//�yQP@20505_No.38�z2012/10/17 TT H.SHIMA MOD End
			this.add(ilKosinHi);

			//--------------------------- ���ڃ��x���ݒ�i�o�^�ҁj  --------------------------------
			ItemLabel hlTorokuSha = new ItemLabel();
			hlTorokuSha.setBounds(610, 65, 50, 15);
			hlTorokuSha.setText("�o�^��");
			this.add(hlTorokuSha);
			//�o�^��
			ilTorokuSha = new ItemIndicationLabel();
			ilTorokuSha.setBounds(660, 65, 145, 15);
			this.add(ilTorokuSha);

			//--------------------------- ���ڃ��x���ݒ�i���Z���j  --------------------------------
			ItemLabel hlShisanHi = new ItemLabel();
			hlShisanHi.setBounds(807, 65, 60, 15);
			hlShisanHi.setText("���Z��");
			this.add(hlShisanHi);
			//���Z��
			iiShisanHi = new ItemIndicationLabel();
			iiShisanHi.setBounds(867, 65, 145, 15);
			this.add(iiShisanHi);

			//--------------------------- ���ڃ��x���ݒ�i�X�V�ҁj  --------------------------------
			ItemLabel hlKosinSha = new ItemLabel();
			hlKosinSha.setBounds(610, 85, 50, 15);
			hlKosinSha.setText("�X�V��");
			this.add(hlKosinSha);
			//�X�V��
			ilKosinSha = new ItemIndicationLabel();
			ilKosinSha.setBounds(660, 85, 145, 15);
			this.add(ilKosinSha);

			//-------------------------- ���ڃ��x���ݒ�i���Z�m��j  -------------------------------
			ItemLabel hlKakuteiHi = new ItemLabel();
			hlKakuteiHi.setBounds(807, 85, 60, 15);
			hlKakuteiHi.setText("���Z�m��");
			this.add(hlKakuteiHi);
			//���Z�m��
			iiKakuteiHi = new ItemIndicationLabel();
			iiKakuteiHi.setBounds(867, 85, 145, 15);
			this.add(iiKakuteiHi);

			//---------------------------- ���ڃ��x���ݒ�i�i���j    --------------------------------
			ItemLabel hlHin = new ItemLabel();
			hlHin.setBounds(5, 48, 60, 30);
			//2011/05/13 QP@10181_No.71 TT T.Satoh Change Start -------------------------
			//hlHin.setText("�i��");
			hlHin.setText("�i��");
			//2011/05/13 QP@10181_No.71 TT T.Satoh Change End ---------------------------
			this.add(hlHin);
			//�i��
			txtHinnm = new TextboxBase();
			txtHinnm.setBounds(65, 48, 375, 31);
			//���[�h�ҏW
			if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
					JwsConstManager.JWS_COMPONENT_0005, DataCtrl.getInstance().getParamData().getStrMode())){
				txtHinnm.setBounds(65, 48, 375, 30);
				txtHinnm.setBackground(Color.lightGray);
				txtHinnm.setEditable(false);
			}else{
				txtHinnm.addFocusListener(new FocusCheck("�i��"));
			}
			this.add(txtHinnm);

			//--------------------------- ���ڃ��x���ݒ�i���@No�j --------------------------------
			ItemLabel hlSeiho = new ItemLabel();
			hlSeiho.setBounds(5, 82, 60, 19);
			hlSeiho.setText("���@No");
			this.add(hlSeiho);

			//���@No�i���[�UID�j
			txtSeihoUser = new TextboxBase();
			txtSeihoUser.setBounds(65, 82, 50, 20);
			//���[�h�ҏW
			if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
					JwsConstManager.JWS_COMPONENT_0006, DataCtrl.getInstance().getParamData().getStrMode())){
				txtSeihoUser.setBounds(65, 82, 50, 19);
				txtSeihoUser.setBackground(Color.lightGray);
				txtSeihoUser.setEditable(false);
			}
			this.add(txtSeihoUser);

			//�n�C�t��
			HaihunLabel = new HaihunLabel();
			HaihunLabel.setBounds(115, 82, 15, 19);
			this.add(HaihunLabel);

			//���@No�i���@��ʁj
			txtSeihoShu = new TextboxBase();
			txtSeihoShu.setBounds(130, 82, 30, 20);
			//���[�h�ҏW
			if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
					JwsConstManager.JWS_COMPONENT_0007, DataCtrl.getInstance().getParamData().getStrMode())){
				txtSeihoShu.setBounds(130, 82, 30, 19);
				txtSeihoShu.setBackground(Color.lightGray);
				txtSeihoShu.setEditable(false);
			}
			this.add(txtSeihoShu);

			//�n�C�t��
			HaihunLabel = new HaihunLabel();
			HaihunLabel.setBounds(160, 82, 15, 19);
			this.add(HaihunLabel);

			//���@No�i�N�j
			txtSeihoNen = new TextboxBase();
			txtSeihoNen.setBounds(175, 82, 30, 20);
			//���[�h�ҏW
			if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
					JwsConstManager.JWS_COMPONENT_0008, DataCtrl.getInstance().getParamData().getStrMode())){
				txtSeihoNen.setBounds(175, 82, 30, 19);
				txtSeihoNen.setBackground(Color.lightGray);
				txtSeihoNen.setEnabled(false);
			}
			this.add(txtSeihoNen);

			//�n�C�t��
			HaihunLabel = new HaihunLabel();
			HaihunLabel.setBounds(205, 82, 15, 19);
			this.add(HaihunLabel);

			//���@No�i�ǔԁj
			txtSeihoOi = new TextboxBase();
			txtSeihoOi.setBackground(Color.lightGray);
			txtSeihoOi.setBounds(220, 82, 40, 20);
			//���[�h�ҏW
			if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
					JwsConstManager.JWS_COMPONENT_0009, DataCtrl.getInstance().getParamData().getStrMode())){
				txtSeihoOi.setBounds(220, 82, 40, 19);
				txtSeihoOi.setBackground(Color.lightGray);
				txtSeihoOi.setEnabled(false);
			}
			this.add(txtSeihoOi);

			//---------------------------- ���ڃ��x���ݒ�i�p�~�j ---------------------------------
			ItemLabel hlhaisi = new ItemLabel();
			hlhaisi.setBounds(270, 82, 60, 19);
			hlhaisi.setText("�p�~");
			this.add(hlhaisi);
			//�p�~
			chkHaisi = new CheckboxBase();
			chkHaisi.setBackground(Color.WHITE);
			chkHaisi.setBounds(330, 82, 30, 20);
			//���[�h�ҏW
			if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
					JwsConstManager.JWS_COMPONENT_0010, DataCtrl.getInstance().getParamData().getStrMode())){
				chkHaisi.setEnabled(false);
			}else{
				chkHaisi.addFocusListener(new FocusCheck("�p�~"));
			}
			this.add(chkHaisi);

			//---------------------------- ������v�Z���l���x�� -------------------------------
			ItemLabel hlGenBikouText = new ItemLabel();
			hlGenBikouText.setBackground(Color.white);
			hlGenBikouText.setBounds(545, 109, 190, 15);
			hlGenBikouText.setText("�i" + JwsConstManager.JWS_MARK_0005 + "�j�͌�����̌v�Z�ɕK�v�ł�");
			this.add(hlGenBikouText);


			//------------------------------- F2���l���x���\�� --------------------------------
			ItemLabel hlBikou = new ItemLabel();
			hlBikou.setBackground(JwsConstManager.SHISAKU_F2_COLOR);
			hlBikou.setBorder(new LineBorder(Color.BLACK, 1));
			hlBikou.setBounds(750, 109, 15, 15);
			this.add(hlBikou);

			ItemLabel hlBikouText = new ItemLabel();
			hlBikouText.setBackground(Color.white);
			hlBikouText.setBounds(770, 109, 250, 15);
			hlBikouText.setText("�̍��ڂ�F2�{�^��������ɕҏW���ĉ�����");
			this.add(hlBikouText);

//2011/05/30�@�yQP@10181_No.42_49_72�z�H���p�^�[������@TT.NISHIGAWA�@start
			ItemLabel hlKoteiPtn = new ItemLabel();
			hlKoteiPtn.setBounds(270, 106, 110, 19);
			hlKoteiPtn.setText("�H���p�^�[��");
			this.add(hlKoteiPtn);
			//�H���p�^�[��
			cmbKoteiPtn = new ComboBase();
			cmbKoteiPtn.setBounds(380, 106, 150, 20);
			//���[�h�ҏW
			if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
					JwsConstManager.JWS_COMPONENT_0153, DataCtrl.getInstance().getParamData().getStrMode())){
				cmbKoteiPtn.setBackground(Color.lightGray);
				cmbKoteiPtn.setEnabled(false);
			}
			else{
				//�t�H�[�J�X���X�i�[�ݒ�
//				cmbKoteiPtn.addActionListener(getActionEvent());
//				cmbKoteiPtn.setActionCommand("�H���p�^�[��");
				//cmbKoteiPtn.addFocusListener(new FocusCheck("�H���p�^�[��"));
			}
			this.add(cmbKoteiPtn);
//2011/05/30�@�yQP@10181_No.42_49_72�z�H���p�^�[������@TT.NISHIGAWA�@end

			//--------------------------------- �^�u�\�� ---------------------------------------
			tb = new TrialTab();
//MOD 2013/6/25 ogawa�yQP@30151�zNo.9 start
//�C���O�̃\�[�X
//			tb.setBounds(5, 105, 1010, 610);
//�C����̃\�[�X
			tb.setBounds(5, 105, 1010, 629);
//MOD 2013/6/25 ogawa�yQP@30151�zNo.9 end
			this.add(tb);

		} catch (ExceptionBase e) {
			throw e;

		} catch (Exception e) {
			e.printStackTrace();
			//�G���[�ݒ�
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			ex.setStrErrmsg("����f�[�^��ʃp�l�� �R���X�g���N�^���������s���܂���");
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
						tb.getTrial1Panel().getTrial1().AutoCopyKeisan();
//add end   -------------------------------------------------------------------------------

					    //-------------------- �o�^�{�^�� �N���b�N���̏���  ------------------------------
						if ( event_name == "toroku") {

							if(cancelMsg()){
								//�o�^����
								toroku();
							}
						}
						//-------------------- �I���{�^�� �N���b�N���̏���  ------------------------------
						else if ( event_name == "shuryo") {

							//�I������
							shuryo();


						}
						//-------------- ����R�s�[�i�����R�s�[�j�{�^�� �N���b�N���̏���  ----------------------
						else if ( event_name == "copy_tokutyo") {

//2011/05/19�@�yQP@10181_No.11�z����R�s�[���[�h�����@TT.NISHIGAWA�@start
							//���݂̃��[�h�擾
							String modo_now = DataCtrl.getInstance().getParamData().getStrMode();
//2011/05/19�@�yQP@10181_No.11�z����R�s�[���[�h�����@TT.NISHIGAWA�@end

							int sel = tb.getSelectedIndex();

							//����R�s�[�ʐM
							conJW020();

							//�����R�s�[
							DataCtrl.getInstance().getTrialTblData().CopyShisakuhin();
							DataCtrl.getInstance().getParamData().setStrSisaku("0-0-0");

							//�ĕ\��
							setPanelData();
							tb.setSelectedIndex(sel);

							//���[�h��V�K�ɕύX
							DataCtrl.getInstance().getParamData().setStrMode(JwsConstManager.JWS_MODE_0002);

							//�R�s�[�{�^���g�p�s��
							btnTcopy.setEnabled(false);
							btnZcopy.setEnabled(false);

							//�p�~�`�F�b�N�{�b�N�X
							chkHaisi.setSelected(false);

							//�������Z��ʂ��N���A����
							tb.getTrial5Panel().clearGenkaShisan();
							//�������Z��ʂ̌����˗��`�F�b�N�{�b�N�X��ҏW�ݒ�
							setGenkaIrai_true();

							//���Z���E���Z�m����N���A����
							iiShisanHi.setText("");
							iiKakuteiHi.setText("");

//2010/05/12�@�V�T�N�C�b�N�i�����j�v�]�y�Č�No9�z�r�����̕\���@TT.NISHIGAWA�@START
							//�r�����N���A
							DataCtrl.getInstance().getUserMstData().setStrHaitaKaishanm("");
							DataCtrl.getInstance().getUserMstData().setStrHaitaBushonm("");
							DataCtrl.getInstance().getUserMstData().setStrHaitaShimei("");
							HeaderLabel.setText(HeaderLabel.getHeaderUserData());
//2010/05/12�@�V�T�N�C�b�N�i�����j�v�]�y�Č�No9�z�r�����̕\���@TT.NISHIGAWA�@END


//2011/05/11 QP@10181_No.42_49_72 TT Nishigawa Add Start -------------------------
							//�H���p�^�[���̕ҏW�s����
							cmbKoteiPtn.setBackground(Color.white);
							cmbKoteiPtn.setEnabled(true);
//2011/05/11 QP@10181_No.42_49_72 TT Nishigawa Add End --------------------------


//2011/05/19�@�yQP@10181_No.11�z����R�s�[���[�h�����@TT.NISHIGAWA�@start
							//�{�^�������O�̃��[�h������R�s�[���[�h�������ꍇ
							if(modo_now.equals(JwsConstManager.JWS_MODE_0004)){

								//�˗��ԍ���ҏW��
								txtIrai.setEditable(true);
								txtIrai.setBackground(Color.white);
								txtIrai.addFocusListener(new FocusCheck("�˗��ԍ�"));

								//�i����ҏW��
								txtHinnm.setEditable(true);
								txtHinnm.setBackground(Color.white);
								txtHinnm.addFocusListener(new FocusCheck("�i��"));

								//�p�~��ҏW��
								chkHaisi.setEnabled(true);
								chkHaisi.addFocusListener(new FocusCheck("�p�~"));

								//�o�^�{�^����ҏW��
								btnToroku.setEnabled(true);

								//��ʔԍ���ҏW��
								cmbShubetuNo.setEnabled(true);
								cmbShubetuNo.setBackground(Color.white);
								cmbShubetuNo.addFocusListener(new FocusCheck("��ʔԍ�"));

								//�H���p�^�[���Ƀ��X�i�[�ǉ�
//								cmbKoteiPtn.addActionListener(getActionEvent());
//								cmbKoteiPtn.setActionCommand("�H���p�^�[��");
								//cmbKoteiPtn.addFocusListener(new FocusCheck("�H���p�^�[��"));

								//�yQP@20505_No.38�z2012/10/17 TT H.SHIMA ADD Start
								//�V�[�N���b�g�{�^����ҏW��
								btnSecret.setEnabled(true);
								//�yQP@20505_No.38�z2012/10/17 TT H.SHIMA ADD End

								//�ĕ\��
								setPanelData();
								tb.setSelectedIndex(sel);

							}
//2011/05/19�@�yQP@10181_No.11�z����R�s�[���[�h�����@TT.NISHIGAWA�@end


						}
						//--------------- ����R�s�[�i�S�R�s�[�j�{�^�� �N���b�N���̏���  -----------------------
						else if ( event_name == "copy_all") {

//2011/05/19�@�yQP@10181_No.12�z�S�R�s�[��w��@TT.NISHIGAWA�@start
							//�I���T���v����ێ�
							String CopyRetu = "";

							//�����w�b�_�e�[�u���擾
							TableBase ListHeaderChk = tb.getTrial1Panel().getTrial1().getListHeader();

							//����񃋁[�v
							int msg_count = 1;
							for(int i=0; i<ListHeaderChk.getColumnCount(); i++){

								//�R���|�[�l���g�擾
								MiddleCellEditor mcShisaku = (MiddleCellEditor)ListHeaderChk.getCellEditor(0, i);
								DefaultCellEditor tcShisaku = (DefaultCellEditor)mcShisaku.getTableCellEditor(0);
								CheckboxBase chkShisaku = (CheckboxBase)tcShisaku.getComponent();
								int chk_seq = Integer.parseInt(chkShisaku.getPk1());
								TrialData trialData = (TrialData)DataCtrl.getInstance().getTrialTblData().SearchShisakuRetuData(chk_seq).get(0);

								//���Fg�Ƀ`�F�b�N�������Ă���ꍇ
								if(trialData.getIntInsatuFlg() == 1){
									//�T���v��No�ݒ�
									String sampleNo = (String)ListHeaderChk.getValueAt(3, i);
									if(sampleNo == null || sampleNo.length() == 0){
										sampleNo = "�T���v�����̖���";
									}

									//�T���v����15��ȏ�̏ꍇ�̓��[�v�A�E�g
									msg_count++;
									if(msg_count > 5){
										//i=ListHeaderChk.getColumnCount();
										msg_count = 1;
										CopyRetu = CopyRetu + " , " + "�y" + sampleNo + "�z" + "\n";
									}
									else{
										if(i == (ListHeaderChk.getColumnCount()-1)){
											if(msg_count > 2){
												CopyRetu = CopyRetu + " , " + "�y" + sampleNo + "�z";
											}
											else{
												CopyRetu = CopyRetu + "�y" + sampleNo + "�z";
											}
										}
										else{
											if(msg_count > 2){
												CopyRetu = CopyRetu + " , " + "�y" + sampleNo + "�z";
											}
											else{
												CopyRetu = CopyRetu + "�y" + sampleNo + "�z";
											}

										}
									}
								}
							}

							//�m�F�_�C�A���O�\���i�T���v���I�����Ȃ��ꍇ�j
							if(CopyRetu.length() == 0){
								//���b�Z�[�W�\��
								DataCtrl.getInstance().getMessageCtrl().PrintMessageString(JwsConstManager.JWS_ERROR_0025);
								//�������f
								return;
							}

							//�m�F�_�C�A���O�\���i�S�R�s�[���s�O�j
							JOptionPane jp = new JOptionPane();
							int option = jp.showConfirmDialog(
									jp.getRootPane(),
									JwsConstManager.JWS_ERROR_0026 + "\n" + CopyRetu
									, "�m�F���b�Z�[�W"
									,JOptionPane.YES_NO_OPTION
									,JOptionPane.PLAIN_MESSAGE
								);

							//�u�͂��v����
						    if (option == JOptionPane.YES_OPTION){
						    	//�������s

						    }
						    //�u�������v����
						    else if (option == JOptionPane.NO_OPTION){
						    	//�������f
						    	return;
						    }
//2011/05/19�@�yQP@10181_No.12�z�S�R�s�[��w��@TT.NISHIGAWA�@end


//2011/05/19�@�yQP@10181_No.11�z����R�s�[���[�h�����@TT.NISHIGAWA�@start
							//���݂̃��[�h�擾
							String modo_now = DataCtrl.getInstance().getParamData().getStrMode();
//2011/05/19�@�yQP@10181_No.11�z����R�s�[���[�h�����@TT.NISHIGAWA�@end


							//����R�s�[�ʐM
							conJW020();

							//�S�R�s�[
							DataCtrl.getInstance().getTrialTblData().CopyAllShisakuhin();
							DataCtrl.getInstance().getParamData().setStrSisaku("0-0-0");

							//�\���ύX
							setCopyData();

							//���[�h��V�K�ɕύX
							DataCtrl.getInstance().getParamData().setStrMode(JwsConstManager.JWS_MODE_0002);

							//�R�s�[�{�^���g�p�s��
							btnTcopy.setEnabled(false);
							btnZcopy.setEnabled(false);

							//�p�~�`�F�b�N�{�b�N�X
							chkHaisi.setSelected(false);

							//�������Z��ʂ��N���A����
							tb.getTrial5Panel().clearGenkaShisan();
							//�������Z��ʂ̌����˗��`�F�b�N�{�b�N�X��ҏW�ݒ�
							setGenkaIrai_true();

//2010/05/12�@�V�T�N�C�b�N�i�����j�v�]�y�Č�No9�z�r�����̕\���@TT.NISHIGAWA�@START
							//�r�����N���A
							DataCtrl.getInstance().getUserMstData().setStrHaitaKaishanm("");
							DataCtrl.getInstance().getUserMstData().setStrHaitaBushonm("");
							DataCtrl.getInstance().getUserMstData().setStrHaitaShimei("");
							HeaderLabel.setText(HeaderLabel.getHeaderUserData());
//2010/05/12�@�V�T�N�C�b�N�i�����j�v�]�y�Č�No9�z�r�����̕\���@TT.NISHIGAWA�@END


							//�z���f�[�^�A����f�[�^�̔z�񐮗�
							DataCtrl.getInstance().getTrialTblData().sortAryHaigo();
							DataCtrl.getInstance().getTrialTblData().sortAryShisaku();
							DataCtrl.getInstance().getTrialTblData().sortAryShisakuList();


//2011/05/19�@�yQP@10181_No.11�z����R�s�[���[�h�����@TT.NISHIGAWA�@start
							//�{�^�������O�̃��[�h������R�s�[���[�h�������ꍇ
							if(modo_now.equals(JwsConstManager.JWS_MODE_0004)){

								//�˗��ԍ���ҏW��
								txtIrai.setEditable(true);
								txtIrai.setBackground(Color.white);
								txtIrai.addFocusListener(new FocusCheck("�˗��ԍ�"));

								//�i����ҏW��
								txtHinnm.setEditable(true);
								txtHinnm.setBackground(Color.white);
								txtHinnm.addFocusListener(new FocusCheck("�i��"));

								//�p�~��ҏW��
								chkHaisi.setEnabled(true);
								chkHaisi.addFocusListener(new FocusCheck("�p�~"));

								//�o�^�{�^����ҏW��
								btnToroku.setEnabled(true);

								//��ʔԍ���ҏW��
								cmbShubetuNo.setEnabled(true);
								cmbShubetuNo.setBackground(Color.white);
								cmbShubetuNo.addFocusListener(new FocusCheck("��ʔԍ�"));

								//�H���p�^�[���Ƀ��X�i�[�ǉ�
//								cmbKoteiPtn.addActionListener(getActionEvent());
//								cmbKoteiPtn.setActionCommand("�H���p�^�[��");
								//cmbKoteiPtn.addFocusListener(new FocusCheck("�H���p�^�[��"));

								//�yQP@20505_No.38�z2012/10/17 TT H.SHIMA ADD Start
								//�V�[�N���b�g�{�^����ҏW��
								btnSecret.setEnabled(true);
								//�yQP@20505_No.38�z2012/10/17 TT H.SHIMA ADD End

								//2011/04/20 QP@10181_No.41 TT T.Satoh Add Start -------------------------
								//�z���\�̎��얾�׃e�[�u���̏c�X�N���[���o�[�̌��݈ʒu���擾
								int vHaigoBarVal = tb.getTrial1Panel().getTrial1().getScrollMain().getVerticalScrollBar().getValue();

								//�z���\�̎��얾�׃e�[�u���̉��X�N���[���o�[�̌��݈ʒu���擾
								int hHaigoBarVal = tb.getTrial1Panel().getTrial1().getScrollMain().getHorizontalScrollBar().getValue();

								//�����l�̉��X�N���[���o�[�̌��݈ʒu���擾
								int hTokuseiBarVal = tb.getTrial2Panel().getScroll().getHorizontalScrollBar().getValue();

								//�������Z�̉��X�N���[���o�[�̌��݈ʒu���擾
								int hGenkaBarVal = tb.getTrial5Panel().getScroll().getHorizontalScrollBar().getValue();
								//2011/04/20 QP@10181_No.41 TT T.Satoh Add End ---------------------------

								//�^�u�p�l���̍ĕ\��
						    	int sel = tb.getSelectedIndex();
						    	setPanelData();
						    	tb.setSelectedIndex(sel);

								//2011/04/20 QP@10181_No.41 TT T.Satoh Add Start -------------------------
								//�z���\�̎��얾�׃e�[�u���̏c�X�N���[���o�[�̈ʒu��ݒ�
								tb.getTrial1Panel().getTrial1().getScrollMain().getVerticalScrollBar().setValue(vHaigoBarVal);

								//�z���\�̎��얾�׃e�[�u���̉��X�N���[���o�[�̈ʒu��ݒ�
								tb.getTrial1Panel().getTrial1().getScrollMain().getHorizontalScrollBar().setValue(hHaigoBarVal);

								//�����l�̉��X�N���[���o�[�̈ʒu��ݒ�
								tb.getTrial2Panel().getScroll().getHorizontalScrollBar().setValue(hTokuseiBarVal);

								//�������Z�̉��X�N���[���o�[�̈ʒu��ݒ�
								tb.getTrial5Panel().getScroll().getHorizontalScrollBar().setValue(hGenkaBarVal);
								//2011/04/20 QP@10181_No.41 TT T.Satoh Add End ---------------------------
							}
//2011/05/19�@�yQP@10181_No.11�z����R�s�[���[�h�����@TT.NISHIGAWA�@end


//2011/05/19�@�yQP@10181_No.12�z�S�R�s�[��w��@TT.NISHIGAWA�@start
							//�����w�b�_�e�[�u���擾
							TableBase ListHeader = tb.getTrial1Panel().getTrial1().getListHeader();

							//����񃋁[�v
							for(int i=0; i<ListHeader.getColumnCount(); i++){

								//�R���|�[�l���g�擾
								MiddleCellEditor mcShisaku = (MiddleCellEditor)ListHeader.getCellEditor(0, i);
								DefaultCellEditor tcShisaku = (DefaultCellEditor)mcShisaku.getTableCellEditor(0);
								CheckboxBase chkShisaku = (CheckboxBase)tcShisaku.getComponent();
								int chk_seq = Integer.parseInt(chkShisaku.getPk1());
								TrialData trialData = (TrialData)DataCtrl.getInstance().getTrialTblData().SearchShisakuRetuData(chk_seq).get(0);

								//���Fg�Ƀ`�F�b�N�������Ă���ꍇ
								if(trialData.getIntInsatuFlg() == 1){

								}
								//���Fg�Ƀ`�F�b�N�������Ă��Ȃ��ꍇ
								else{
									//��폜
									tb.getTrial1Panel().HaigoDelShisakuCol(i);
									i --;
								}
							}

							//���FG�N���A����
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

							//ALL�`�F�b�N�t���O�N���A
							tb.getTrial1Panel().getTrial1().getAllCheck().setSelected(false);

							//�H���p�^�[���̕ҏW�s����
							cmbKoteiPtn.setBackground(Color.white);
							cmbKoteiPtn.setEnabled(true);

//2011/05/19�@�yQP@10181_No.12�z�S�R�s�[��w��@TT.NISHIGAWA�@end

							//�ĕ\��
					    	int sel = tb.getSelectedIndex();
					    	setPanelData();
					    	tb.setSelectedIndex(sel);

						}

						//�yQP@20505_No.38�z2012/10/17 TT H.SHIMA ADD Start
						//------------------------------- �V�[�N���b�g�ؑ� --------------------------------
						else if(event_name.equals("secret")){
							// �yKPX1500671�z ADD start
							//�f�[�^�ύX�t���O�n�m
					    	DataCtrl.getInstance().getTrialTblData().setHenkouFg(true);
							// �yKPX1500671�z ADD end

							if(PrototypeData.getStrSecret() != null){
								PrototypeData.setStrSecret(null);
								ilSecret.setText("OFF");
								ilSecret.setBackground(JwsConstManager.SHISAKU_ITEM_COLOR4);
							}else{
								PrototypeData.setStrSecret("1");
								ilSecret.setText("ON");
								ilSecret.setBackground(JwsConstManager.SHISAKU_ITEM_COLOR3);
							}
						}
						//�yQP@20505_No.38�z2012/10/17 TT H.SHIMA MOD End

						//------------------------------- �����v�Z --------------------------------------
						else if(event_name.equals("autoClick")){

							//�v�Z����
							AutoKeisan();

						}

// ADD start 20121017 QP@20505 No.11
						//------------------------------- ���̓}�X�^�̍ŐV��ԂɍX�V�{�^������ --------------------------------------
						else if(event_name.equals("click_getBunsekiMstData")){

							//�m�F�_�C�A���O�\���i�S�R�s�[���s�O�j
							JOptionPane jp = new JOptionPane();
							int option = jp.showConfirmDialog(
									jp.getRootPane(),
									JwsConstManager.JWS_ERROR_0029
									, "�m�F���b�Z�[�W"
									,JOptionPane.YES_NO_OPTION
									,JOptionPane.PLAIN_MESSAGE
								);

							//�u�͂��v����
						    if (option == JOptionPane.YES_OPTION){
						    	//�������s

						    }
						    //�u�������v����
						    else if (option == JOptionPane.NO_OPTION){
						    	//�������f
						    	return;
						    }

							//�������̓}�X�^�l�擾
							ArrayList aryBunsekiData = conJW220();

							//�\�����i�z�����ׁj
							TableBase dispHaigoMeisai = tb.getTrial1Panel().getTrial1().getHaigoMeisai();
							//�ő�H����
							int maxKotei = DataCtrl.getInstance().getTrialTblData().getIntMaxKotei();

							//���݂̔z���f�[�^�擾
							ArrayList koshinHaigo = DataCtrl.getInstance().getTrialTblData().SearchHaigoData(0);

							for(int k=0; k<dispHaigoMeisai.getRowCount()-maxKotei-9; k++){
								//�R���|�[�l���g�擾
								MiddleCellEditor selectMc = (MiddleCellEditor)dispHaigoMeisai.getCellEditor(k, 2);
								DefaultCellEditor selectDc = (DefaultCellEditor)selectMc.getTableCellEditor(k);

								//�����s�̏ꍇ�ɏ���
								if(selectDc.getComponent() instanceof CheckboxBase){

									for(int n=0; n<aryBunsekiData.size(); n++){
										//�}�X�^����擾�����������̓f�[�^
										MixedData md = (MixedData)aryBunsekiData.get(n);
										if ( dispHaigoMeisai.getValueAt(k, 3) == null || dispHaigoMeisai.getValueAt(k, 3).equals("")) {
											// ����CD�����͂���Ă��Ȃ��i�󔒁j�̏ꍇ

										} else {
											if (dispHaigoMeisai.getValueAt(k, 3).equals(md.getStrGenryoCd())){
												//���ܗL���@��ʂɕ\��
												dispHaigoMeisai.setValueAt(md.getDciGanyuritu(), k, 7);
											}
										}
									}
								}
							}

							int koteiCd = 0;
							int koteiSeq = 0;
							String genryoCd = "";
							for(int m=0; m<koshinHaigo.size(); m++){
								MixedData MixedData = (MixedData)koshinHaigo.get(m);

								genryoCd = MixedData.getStrGenryoCd();

								for(int n=0; n<aryBunsekiData.size(); n++){

									//�}�X�^����擾�����������̓f�[�^
									MixedData md = (MixedData)aryBunsekiData.get(n);

									if(genryoCd == null || genryoCd.length() < 0){
									}
									else if (genryoCd.equals(md.getStrGenryoCd())){
										koteiCd = MixedData.getIntKoteiCd();
										koteiSeq = MixedData.getIntKoteiSeq();

										//���ܗL��
										DataCtrl.getInstance().getTrialTblData().UpdHaigoGenryoYuganyuryo(
												koteiCd,
												koteiSeq,
												md.getDciGanyuritu(),
												DataCtrl.getInstance().getUserMstData().getDciUserid());
										//�|�_
										DataCtrl.getInstance().getTrialTblData().UpdHaigoGenryoSakusan(
												koteiCd,
												koteiSeq,
												md.getDciSakusan(),
												DataCtrl.getInstance().getUserMstData().getDciUserid());
										//�H��
										DataCtrl.getInstance().getTrialTblData().UpdHaigoGenryoSyokuen(
												koteiCd,
												koteiSeq,
												md.getDciShokuen(),
												DataCtrl.getInstance().getUserMstData().getDciUserid());
										//���_
										DataCtrl.getInstance().getTrialTblData().UpdHaigoGenryoSousan(
												koteiCd,
												koteiSeq,
												md.getDciSosan(),
												DataCtrl.getInstance().getUserMstData().getDciUserid());
										//�l�r�f
										DataCtrl.getInstance().getTrialTblData().UpdHaigoGenryoMsg(
												koteiCd,
												koteiSeq,
												md.getDciMsg(),
												DataCtrl.getInstance().getUserMstData().getDciUserid());
									}
								}
							}

							//�����v�Z�̃`�F�b�N�{�b�N�X���I�t�Ȃ�ꎞ�I�ɃI���i�}�X�^�l�ōX�V���邽�ߕK���v�Z������������j
							int flgChkOff = 0;
							CheckboxBase chkAuto = tb.getTrial2Panel().getChkAuto();
							if (! chkAuto.isSelected()){
								flgChkOff = 1;
								chkAuto.setSelected(true);
							}

							//�v�Z����
							AutoKeisan();

							if (flgChkOff == 1){
								chkAuto.setSelected(false);
							}
						}
// ADD end 20121017 QP@20505 No.11

						//------------------------------- �T���v���������o�� --------------------------------------
						else if(event_name.equals("sampleSetumei")) {

							//���Flg�`�F�b�N���s��
							if ( chkInsatuFlg("�T���v��������", 4) ) {

//mod start -------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.2
								//�T���v��No����`�F�b�N
								String chk = DataCtrl.getInstance().getTrialTblData().checkDistSampleNo_ALL();

								//����T���v��No���Ȃ��ꍇ
								if(chk==null){

									if(cancelMsg()){
										//�����ۑ�����
										JidouHozon(2);

										//�T���v�����������o�͂���
										conJW130();
									}

								}
								//����T���v��No������ꍇ
								else{
									DataCtrl.getInstance().getMessageCtrl().PrintMessageString(JwsConstManager.JWS_ERROR_0048 + chk);
								}
//mod end -------------------------------------------------------------------------------
							}

						}
						//------------------------------- ����\�o�� --------------------------------------
						else if(event_name.equals("shisakuHyo")) {

//mod start -------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.1
							//���Flg�`�F�b�N���s��
							//if ( chkInsatuFlg("����\", 10) ) {
							if ( chkInsatuFlg("����\", 20) ) {

	//mod start -------------------------------------------------------------------------------
	//QP@00412_�V�T�N�C�b�N���� No.2
								//�T���v��No����`�F�b�N
								String chk = DataCtrl.getInstance().getTrialTblData().checkDistSampleNo_ALL();
								//����T���v��No���Ȃ��ꍇ
								if(chk==null){

									if(cancelMsg()){
										//�����ۑ�����
										JidouHozon(1);

										//����\�o�͏���
										conJW120();
									}

								}
								//����T���v��No������ꍇ
								else{
									DataCtrl.getInstance().getMessageCtrl().PrintMessageString(JwsConstManager.JWS_ERROR_0048 + chk);
								}
	//mod end -------------------------------------------------------------------------------
							}
//mod end -------------------------------------------------------------------------------

						}
						//------------------------------- �h�{�v�Z���o�� --------------------------------------
						else if(event_name.equals("eiyoKeisan")) {

							//����񂪑I������Ă��邩
							if ( DataCtrl.getInstance().getManufacturingSubDisp().getManufacturingPanel().getShisakuSeq() != 0 ) {

//mod start -------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.2
								//�T���v��No����`�F�b�N
								String chk = DataCtrl.getInstance().getTrialTblData().checkDistSampleNo_ALL();
								//����T���v��No���Ȃ��ꍇ
								if(chk==null){

									int seq = DataCtrl.getInstance().getManufacturingSubDisp().getManufacturingPanel().getShisakuSeq();

									if(cancelMsg()){
										//�����ۑ�����
										JidouHozon(3);

										//�h�{�v�Z���o�͏���
										conJW740(seq);
									}

								}
								//����T���v��No������ꍇ
								else{
									DataCtrl.getInstance().getMessageCtrl().PrintMessageString(JwsConstManager.JWS_ERROR_0048 + chk);
								}
							} else {
								//���b�Z�[�W�\��
								DataCtrl.getInstance().getMessageCtrl().PrintMessageString("������I�����ĉ������B");

							}
//mod end -------------------------------------------------------------------------------

						}

						//------------------------------- �������Z�\ --------------------------------------
						else if(event_name.equals("shisanHyo")) {

//mod start -------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.2
							//�T���v��No����`�F�b�N
							String chk = DataCtrl.getInstance().getTrialTblData().checkDistSampleNo_ALL();
							//����T���v��No���Ȃ��ꍇ
							if(chk==null){

								//�H���`�F�b�N
								int intKoteiChk = DataCtrl.getInstance().getTrialTblData().CheckKotei();

								//�z���\(����\�@)��ʂ̍H���ɂāA�I������Ă���H���̃`�F�b�N���s��
								if ( intKoteiChk == 1 || intKoteiChk == 2 ) {
									//�����t or ���̑������t�ȊO�̏ꍇ

									//���Flg�`�F�b�N���s��
									if ( tb.getTrial5Panel().chkGenkaInsatuFlg("�������Z�\", 3) ) {

										if(cancelMsg()){
											//�����ۑ�����
											JidouHozon(4);

											//�������Z�\�o�͏���
											tb.getTrial5Panel().outputGenkaShisanHyo(intKoteiChk);

											//�������Z�}�[�N�ݒ菈��
											setGenkaShisanMark();
										}

									}

								} else if ( intKoteiChk == 0 ) {
									//���I����

									//�x�����b�Z�[�W�\��
									String strMessage = JwsConstManager.JWS_ERROR_0022;
									DataCtrl.getInstance().getMessageCtrl().PrintMessageString(strMessage);

								} else {
									//�����t or ���̑������t�ȊO�ł͂Ȃ��ꍇ

									//�x�����b�Z�[�W�\��
									String strMessage = JwsConstManager.JWS_ERROR_0023;
									DataCtrl.getInstance().getMessageCtrl().PrintMessageString(strMessage);

								}

							}
							//����T���v��No������ꍇ
							else{
								DataCtrl.getInstance().getMessageCtrl().PrintMessageString(JwsConstManager.JWS_ERROR_0048 + chk);
							}
//mod end -------------------------------------------------------------------------------

						}
						//------------------------------- �������Z�o�^ --------------------------------------
						else if(event_name.equals("shisanToroku")) {

							//�������Z�o�^����
							tb.getTrial5Panel().shisanToroku();


							//���Z���E���Z�m�� �ݒ�

							//�ŏI���Z�m������Z�f�[�^���擾
							ShisanData shisanData = DataCtrl.getInstance().getShisanRirekiKanriData().SearchLastShisanData();

							//�ŏI���Z�m�肵�����Z���ƃT���v��No���A��ʍ��ڂ̎��Z���Ǝ��Z�m��ɐݒ�
							iiShisanHi.setText(shisanData.getStrShisanHi());
							if ( shisanData.getStrSampleNo() != null ) {

								iiKakuteiHi.setText(shisanData.getStrSampleNo());

							} else {

								iiKakuteiHi.setText("");

							}

						}


						//-------------------------- �H���p�^�[��  -----------------------
						else if(event_name.equals("�H���p�^�[��")){

							//���݂̐ݒ�l�擾
							String kotei_ptn = DataCtrl.getInstance().getTrialTblData().SearchShisakuhinData().getStrPt_kotei();
							String kotei_ptn_value = "";
							if(kotei_ptn == null){
								kotei_ptn = "";
							}
							else{
								kotei_ptn_value = DataCtrl.getInstance().getLiteralDataKoteiPtn().selectLiteralVal1(kotei_ptn);
							}

							System.out.println("���ݒl�F" + kotei_ptn_value);

							//�I��l�擾
							String insert = null;
							int selectId = ((ComboBase)e.getSource()).getSelectedIndex();
							if(selectId > 0){
								insert = DataCtrl.getInstance().getLiteralDataKoteiPtn().selectLiteralCd(selectId-1);
							}
							if(insert == null){
								insert = "";
							}

							System.out.println("�I��l�F" + insert);

							//�m�F���b�Z�[�W�\���t���O
							boolean confirmMsg = false;

							//�ݒ�l�ƑI��l���������ꍇ
							if(insert.equals(kotei_ptn)){
								//�������Ȃ�

							}
							//�ݒ�l�ƑI��l���������Ȃ��ꍇ
							else{
// DEL start 20121026 QP@20505 No.24
//								//������d�̓��͊m�F
//								ArrayList aryShisakuRetu = DataCtrl.getInstance().getTrialTblData().SearchShisakuRetuData(0);
//								Iterator ite = aryShisakuRetu.iterator();
//
//								//���X�g���������[�v
//								while(ite.hasNext()){
//									//�����f�[�^�I�u�W�F�N�g�擾
//									TrialData trialData = (TrialData)ite.next();
//
//									//������d�ɓ��͂��Ȃ��ꍇ
//									if(trialData.getStrHiju_sui() == null || trialData.getStrHiju_sui().length()==0){
//
//									}
//									//������d�ɓ��͂�����ꍇ
//									else{
//										//�m�F���b�Z�[�W�\���t���O���I��
//										confirmMsg = true;
//									}
//								}
// DEL end 20121026 QP@20505 No.24
// ADD start 20121003 QP@20505 No.24
								//������d�̓��͊m�F
								boolean flgSuisoHiju = false;
								ArrayList aryShisakuRetu = DataCtrl.getInstance().getTrialTblData().SearchShisakuRetuData(0);
								Iterator ite = aryShisakuRetu.iterator();

								while(ite.hasNext()){
									//�����f�[�^�I�u�W�F�N�g�擾
									TrialData trialData = (TrialData)ite.next();

									if(trialData.getStrHiju_sui() == null || trialData.getStrHiju_sui().length()==0){
										//������d�ɓ��͂��Ȃ��ꍇ
									}else{
										flgSuisoHiju = true;	// ������d���b�Z�[�W�\���t���O
									}
								}
								ite = null;
								//���i��d�̓��͊m�F
								boolean flgSeihinHiju = false;
								ite = aryShisakuRetu.iterator();

								while(ite.hasNext()){
									//�����f�[�^�I�u�W�F�N�g�擾
									TrialData trialData = (TrialData)ite.next();

									if(trialData.getStrHizyu() == null || trialData.getStrHizyu().length()==0){
										//���i��d�ɓ��͂��Ȃ��ꍇ
									}else{
										flgSeihinHiju = true;	//���i��d���b�Z�[�W�\���t���O
									}
								}

								int flgValueClear = 0;
								//�e�ʒP�ʎ擾
								String yoryoTani = DataCtrl.getInstance().getTrialTblData().SearchShisakuhinData().getStrTani();
								//�e�ʒP�ʂ���Value1�擾
								String taniValue1 = "";
								if(yoryoTani == null || yoryoTani.length() == 0){
								}
								else{
									taniValue1 =  DataCtrl.getInstance().getLiteralDataTani().selectLiteralVal1(yoryoTani);
								}
								//�����l��ʍ��ڂ��؂�ւ�邩�ǂ�������
								if (insert.equals("001") || insert.equals("002")){
									if (kotei_ptn.equals("003") || kotei_ptn.equals("")){
										flgValueClear = 1; // 1�t or 2�t �ɂȂ�ꍇ
									}
								}else{
									if (kotei_ptn.equals("001") || kotei_ptn.equals("002")){
										flgValueClear = 2; // ���̑��E���H or �� �ɂȂ�ꍇ
									}
								}
								//�H���p�^�[���A�e�ʒP�� �̑I��l�ɂ���ĕ\�����b�Z�[�W���قȂ�
								confirmMsg = true;
								String strMessage = "";
								if (kotei_ptn.equals("001")){
									if (taniValue1.equals("1")){
										if (insert.equals("003") || insert.equals("")){
											// 1�t(ml) �� ���̑��E���H or ��
											strMessage = JwsConstManager.JWS_CONFIRM_0013;
										}else{
											// 1�t(ml) �� 2�t
											strMessage = JwsConstManager.JWS_CONFIRM_0012;
											if ( ! flgSeihinHiju){
												confirmMsg = false;                       // ���i��d���󔒂Ȃ烁�b�Z�[�W�\�����Ȃ�
											}
										}
									}else if (taniValue1.equals("2")){
										if (insert.equals("003") || insert.equals("")){
											// 1�t(g) �� ���̑��E���H or ��
											strMessage = JwsConstManager.JWS_CONFIRM_0014;
										}
									}else{
										if (insert.equals("003") || insert.equals("")){
											// 1�t(�P�ʋ�) �� ���̑��E���H or ��
											strMessage = JwsConstManager.JWS_CONFIRM_0014;
										}
									}
								}else if (kotei_ptn.equals("002")){
									if (taniValue1.equals("1")){
										if (insert.equals("003") || insert.equals("")){
											// 2�t(ml) �� ���̑��E���H or ��
											strMessage = JwsConstManager.JWS_CONFIRM_0015;
										}else{
											// 2�t(ml) �� 1�t
											strMessage = JwsConstManager.JWS_CONFIRM_0011;
											if ( ! flgSuisoHiju){
												confirmMsg = false;                        // ������d���󔒂Ȃ烁�b�Z�[�W�\�����Ȃ�
											}
										}
									}else if (taniValue1.equals("2")){
										if (insert.equals("003") || insert.equals("")){
											// 2�t(g) �� ���̑��E���H or ��
											strMessage = JwsConstManager.JWS_CONFIRM_0014;
										}
									}else{
										// 2�t(�P�ʋ�) �� ���̑��E���H or ��
										strMessage = JwsConstManager.JWS_CONFIRM_0014;
									}
								}else{
									if (insert.equals("001") || insert.equals("002")){
										// ���̑��E���H or �� (ml�Eg�E�P�ʋ�) �� 1�t or 2�t
										strMessage = JwsConstManager.JWS_CONFIRM_0016;
									}
								}

								if (strMessage.equals("")){
									// Yes_No�m�F���b�Z�[�W�\���Ȃ�

								}else{
									//Yes_No�m�F���b�Z�[�W�\��
									if (confirmMsg){
										JOptionPane jp = new JOptionPane();
										int option = JOptionPane.showConfirmDialog(
												jp.getRootPane(),
												strMessage
												, "�m�F���b�Z�[�W"
												,JOptionPane.YES_NO_OPTION
												,JOptionPane.PLAIN_MESSAGE
											);
									    if (option == JOptionPane.YES_OPTION){
									    	 //�u�͂��v�����A�������s
									    }else{ // �u�������v����
									    	//�R���{�{�b�N�X��I��O�̏�Ԃɖ߂�
									    	String selectNm = DataCtrl.getInstance().getLiteralDataKoteiPtn().selectLiteralNm(kotei_ptn);
									    	((ComboBase)e.getSource()).setSelectedItem(selectNm);
									    	//�������f
									    	return;
									    }
									}
								}
// ADD end 20121003 QP@20505 No.24
// DEL start 20121026 QP@20505 No.24
//								//�m�F���b�Z�[�W�\��
//								if(confirmMsg){
//
//									//�m�F�_�C�A���O�\��
//									JOptionPane jp = new JOptionPane();
//									int option = JOptionPane.showConfirmDialog(
//											jp.getRootPane(),
//											JwsConstManager.JWS_CONFIRM_0011
//											, "�m�F���b�Z�[�W"
//											,JOptionPane.YES_NO_OPTION
//											,JOptionPane.PLAIN_MESSAGE
//										);
//
//									//�u�͂��v����
//								    if (option == JOptionPane.YES_OPTION){
//
//								    	//�������s
//
//								    }
//								    //�u�������v����
//								    else {
//
//								    	//�R���{�{�b�N�X��I��O�̏�Ԃɖ߂�
//								    	String selectNm = DataCtrl.getInstance().getLiteralDataKoteiPtn().selectLiteralNm(kotei_ptn);
//								    	((ComboBase)e.getSource()).setSelectedItem(selectNm);
//
//								    	//�������f
//								    	return;
//
//								    }
//								}
// DEL end 20121026 QP@20505 No.24
								//�H���p�^�[���ύX���Ɋm�F���b�Z�[�W
								if(kotei_ptn_value.equals("")){
									//�O�I�����󔒂̏ꍇ�̓��b�Z�[�W�Ȃ�
								}
								else{
									DataCtrl.getInstance().getMessageCtrl().PrintMessageString(JwsConstManager.JWS_CONFIRM_0010);
								}

								//�H���p�^�[���ύX
								DataCtrl.getInstance().getTrialTblData().UpdKoteiPtn(
										DataCtrl.getInstance().getTrialTblData().checkNullString(insert),
										DataCtrl.getInstance().getUserMstData().getDciUserid()
									);
								int selecttab = tb.getSelectedIndex();
								if (selecttab == 1){
//							    	tb.setSelectedIndex(2);
//							    	tb.setSelectedIndex(1);
									//�����v�Z
									Trial2Panel tp2 = new Trial2Panel();
// ADD start 20121017 QP@20505
									CheckboxBase chkAuto = tp2.getChkAuto();
									chkAuto.addActionListener(getActionEvent());
									chkAuto.setActionCommand("autoClick");
									//�����v�Z
									ButtonBase btnBunsekiMstData = tp2.getCmdBunsekiMstData();
									btnBunsekiMstData.addActionListener(getActionEvent());
									btnBunsekiMstData.setActionCommand("click_getBunsekiMstData");
// ADD end 20121017 QP@20505
									//�ĕ\��
									tb.setTrial2Panel(tp2);
									jtab.setComponentAt(1, tp2);
								}
								//�H���p�^�[���擾
								String ptKotei = PrototypeData.getStrPt_kotei();

						    	//�z�����׃e�[�u���擾
								TableBase HaigoMeisai = tb.getTrial1Panel().getTrial1().getHaigoMeisai();

								//�z�����׃e�[�u���@�s���[�v
								for(int i=0; i<HaigoMeisai.getRowCount(); i++){

									//�R���|�[�l���g�擾
									MiddleCellEditor selectMc = (MiddleCellEditor)HaigoMeisai.getCellEditor(i, 3);
									DefaultCellEditor selectDc = (DefaultCellEditor)selectMc.getTableCellEditor(i);

									//�H���s�̏ꍇ
									if(selectDc.getComponent() instanceof ComboBase){

										//�L�[���ڎ擾
										MiddleCellEditor selectMcKey = (MiddleCellEditor)HaigoMeisai.getCellEditor(i, 0);
										DefaultCellEditor selectDcKey = (DefaultCellEditor)selectMcKey.getTableCellEditor(i);
										CheckboxBase cb = (CheckboxBase)selectDcKey.getComponent();
										int koteiCd = Integer.parseInt(cb.getPk1());

										//�f�[�^�}��
										DataCtrl.getInstance().getTrialTblData().UpdHaigoZokusei(
												koteiCd,
												DataCtrl.getInstance().getTrialTblData().checkNullString(""),
												DataCtrl.getInstance().getUserMstData().getDciUserid()
											);

										//�H�������R���{�{�b�N�X�擾
										ComboBase cbKotei = (ComboBase)selectDc.getComponent();
										MiddleCellRenderer selectMr =  (MiddleCellRenderer)HaigoMeisai.getCellRenderer(i, 3);
										ComboBoxCellRenderer selectCr =  (ComboBoxCellRenderer)selectMr.getTableCellRenderer(i);

										//�H���p�^�[�����u�󔒁v�̏ꍇ
										if(ptKotei == null || ptKotei.length() == 0){
											//�Z���G�f�B�^�ɋ�s�̂�
											cbKotei.removeAllItems();
											cbKotei.addItem("");
											selectCr.removeAllItems();
											selectCr.addItem("");
										}
										//�H���p�^�[�����u�󔒁v�łȂ��ꍇ
										else{
											//�H���p�^�[����Value1�擾
											String ptValue = DataCtrl.getInstance().getLiteralDataKoteiPtn().selectLiteralVal1(ptKotei);

											//�H���p�^�[�����u�������P�t�^�C�v�v�̏ꍇ
											if(ptValue.equals(JwsConstManager.JWS_KOTEITYPE_1)){
												//�I��l��ݒ�
												setLiteralCmb(cbKotei, DataCtrl.getInstance().getLiteralDataKotei_tyomi1(), "", 0);
												setLiteralCmb(selectCr, DataCtrl.getInstance().getLiteralDataKotei_tyomi1(), "", 0);
											}
											//�H���p�^�[�����u�������Q�t�^�C�v�v�̏ꍇ
											else if(ptValue.equals(JwsConstManager.JWS_KOTEITYPE_2)){
												//�I��l��ݒ�
												setLiteralCmb(cbKotei, DataCtrl.getInstance().getLiteralDataKotei_tyomi2(), "", 0);
												setLiteralCmb(selectCr, DataCtrl.getInstance().getLiteralDataKotei_tyomi2(), "", 0);
											}
											//�H���p�^�[�����u���̑��E���H�^�C�v�v�̏ꍇ
											else if(ptValue.equals(JwsConstManager.JWS_KOTEITYPE_3)){
												//�I��l��ݒ�
												setLiteralCmb(cbKotei, DataCtrl.getInstance().getLiteralDataKotei_sonota(), "", 0);
												setLiteralCmb(selectCr, DataCtrl.getInstance().getLiteralDataKotei_sonota(), "", 0);
											}
										}
										HaigoMeisai.setValueAt("", i, 3);
									}
								}

								//��{���̗e�ʒP�ʂ�ݒ�
								ComboBase cbTani = tb.getTrial3Panel().getCmbtani();
								tb.getTrial3Panel().setLiteralCmbYoryo(cbTani, DataCtrl.getInstance().getLiteralDataTani(), "", 0);

								//�H���p�^�[�����u�󔒁v�̏ꍇ
								if(ptKotei == null || ptKotei.length() == 0){

									//�f�[�^�ݒ�
									DataCtrl.getInstance().getTrialTblData().UpdYouryoTani(
											"",
											DataCtrl.getInstance().getUserMstData().getDciUserid()
										);

									//���v�d�オ��d�ʏ�����
									clearSiagariZyuryo(kotei_ptn_value);
								}
								//�H���p�^�[�����u�󔒁v�łȂ��ꍇ
								else{
									//�H���p�^�[����Value1�擾
									String ptValue = DataCtrl.getInstance().getLiteralDataKoteiPtn().selectLiteralVal1(ptKotei);

									//�H���p�^�[�����u�������P�t�^�C�v�v�̏ꍇ
									if(ptValue.equals(JwsConstManager.JWS_KOTEITYPE_1)){

										//�f�[�^�ݒ�
										DataCtrl.getInstance().getTrialTblData().UpdYouryoTani(
												"",
												DataCtrl.getInstance().getUserMstData().getDciUserid()
											);

										//���v�d�オ��d�ʏ�����
										clearSiagariZyuryo(kotei_ptn_value);
									}
									//�H���p�^�[�����u�������Q�t�^�C�v�v�̏ꍇ
									else if(ptValue.equals(JwsConstManager.JWS_KOTEITYPE_2)){

										cbTani.setSelectedIndex(1);

										//�f�[�^�ݒ�
										String selectNm = (String) cbTani.getSelectedItem();
										insert = DataCtrl.getInstance().getLiteralDataTani().selectLiteralCd(selectNm);
										DataCtrl.getInstance().getTrialTblData().UpdYouryoTani(
												DataCtrl.getInstance().getTrialTblData().checkNullString(insert),
												DataCtrl.getInstance().getUserMstData().getDciUserid()
											);

										//���v�d�オ��d�ʏ�����
										clearSiagariZyuryo(kotei_ptn_value);
									}
									//�H���p�^�[�����u���̑��E���H�^�C�v�v�̏ꍇ
									else if(ptValue.equals(JwsConstManager.JWS_KOTEITYPE_3)){

										cbTani.setSelectedIndex(1);

										//�f�[�^�ݒ�
										String selectNm = (String) cbTani.getSelectedItem();
										insert = DataCtrl.getInstance().getLiteralDataTani().selectLiteralCd(selectNm);
										DataCtrl.getInstance().getTrialTblData().UpdYouryoTani(
												DataCtrl.getInstance().getTrialTblData().checkNullString(insert),
												DataCtrl.getInstance().getUserMstData().getDciUserid()
											);
									}
								}

								//�����l�A�������Z�e�[�u���ݒ菈��
								setTp2_5TableHiju(flgValueClear);
							}
						}

					} catch (ExceptionBase eb) {

						//���b�Z�[�W�\��
						DataCtrl.getInstance().PrintMessage(eb);

					} catch (Exception ec) {

						ec.printStackTrace();

						//�G���[�ݒ�
						ex = new ExceptionBase();
						ex.setStrErrCd("");
						ex.setStrErrmsg("����f�[�^��� �{�^���������������s���܂���");
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
	 *   �I������
	 *   @author TT nishigawa
	 *
	 ************************************************************************************/
	public void shuryo() throws ExceptionBase{
		try{

			//�������[�h�擾
			String mode = DataCtrl.getInstance().getParamData().getStrMode();

			//�I�����s�t���O
			boolean blnEnd = true;

			//�f�[�^�ύX�`�F�b�N
			if(DataCtrl.getInstance().getTrialTblData().getHenkouFg()){

//2011/05/19�@�yQP@10181_No.11�z����R�s�[���[�h�����@TT.NISHIGAWA�@start
//				//�Q�ƃ��[�h�̏ꍇ or ���@�R�s�[���[�h�̏ꍇ
//				if(mode.equals(JwsConstManager.JWS_MODE_0000) || mode.equals(JwsConstManager.JWS_MODE_0003)){

				//�Q�ƃ��[�h�̏ꍇ or ���@�R�s�[���[�h�̏ꍇ or ����R�s�[���[�h�̏ꍇ
				if(mode.equals(JwsConstManager.JWS_MODE_0000)
						|| mode.equals(JwsConstManager.JWS_MODE_0003)
						|| mode.equals(JwsConstManager.JWS_MODE_0004) ){

//2011/05/19�@�yQP@10181_No.11�z����R�s�[���[�h�����@TT.NISHIGAWA�@end

					//�I�����sOK
			    	blnEnd = true;

				}
				//�ڍׁA�V�K���[�h�̏ꍇ
				else{

					//�_�C�A���O�R���|�[�l���g�ݒ�
					JOptionPane jp = new JOptionPane();

					//�m�F�_�C�A���O�\��
					int option = jp.showConfirmDialog(
							jp.getRootPane(),
							"���e���ύX����Ă��܂��B�ۑ����܂����H"
							, "�m�F���b�Z�[�W"
							,JOptionPane.YES_NO_OPTION
							,JOptionPane.PLAIN_MESSAGE
						);

					//�u�͂��v����
				    if (option == JOptionPane.YES_OPTION){

				    	//�I�����sNG
				    	blnEnd = false;

				    	//�o�^����
				    	toroku();

				    //�u�������v����
				    }else if (option == JOptionPane.NO_OPTION){

				    	//�I�����sOK
				    	blnEnd = true;

				    }

				}



			}

			//�I�����sOK
			if(blnEnd){

				//�ڍ� or ���@�R�s�[�̏ꍇ
				if(mode.equals(JwsConstManager.JWS_MODE_0001) || mode.equals(JwsConstManager.JWS_MODE_0003)){

					//�I������
					conJW060();

				}

				//��ʏI��
				System.exit(0);

			}
			//�I������NG
			else{

				//�������Ȃ�

			}

		}catch(ExceptionBase eb) {

			throw eb;

		}catch(Exception ec){

			ec.printStackTrace();

			//�G���[�ݒ�
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			ex.setStrErrmsg("�I�����������s���܂���");
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(ec.getMessage());

		}finally{


		}
	}


	/************************************************************************************
	 *
	 *   �o�^����
	 *   @author TT nishigawa
	 *
	 ************************************************************************************/
	public void toroku() throws ExceptionBase{
		try{
			String mode = DataCtrl.getInstance().getParamData().getStrMode();

			//2011/04/20 QP@10181_No.41 TT T.Satoh Add Start -------------------------
			//�z���\�̎��얾�׃e�[�u���̏c�X�N���[���o�[�̌��݈ʒu���擾
			int vHaigoBarVal = tb.getTrial1Panel().getTrial1().getScrollMain().getVerticalScrollBar().getValue();

			//�z���\�̎��얾�׃e�[�u���̉��X�N���[���o�[�̌��݈ʒu���擾
			int hHaigoBarVal = tb.getTrial1Panel().getTrial1().getScrollMain().getHorizontalScrollBar().getValue();

			//�����l�̉��X�N���[���o�[�̌��݈ʒu���擾
			int hTokuseiBarVal = tb.getTrial2Panel().getScroll().getHorizontalScrollBar().getValue();

			//�������Z�̉��X�N���[���o�[�̌��݈ʒu���擾
			int hGenkaBarVal = tb.getTrial5Panel().getScroll().getHorizontalScrollBar().getValue();
			//2011/04/20 QP@10181_No.41 TT T.Satoh Add End ---------------------------

			//�ڍ�
			if(mode.equals(JwsConstManager.JWS_MODE_0001)){

//mod start -------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.2
				//�T���v��No����`�F�b�N
				String chk = DataCtrl.getInstance().getTrialTblData().checkDistSampleNo_ALL();
				//����T���v��No���Ȃ��ꍇ
				if(chk==null){
					//�o�^�i�ҏW�j
					conJW040(0);
					//�f�[�^�ύX�t���O������
			    	DataCtrl.getInstance().getTrialTblData().setHenkouFg(false);
				}
				//����T���v��No������ꍇ
				else{
					DataCtrl.getInstance().getMessageCtrl().PrintMessageString(JwsConstManager.JWS_ERROR_0048 + chk);
				}
//mod end --------------------------------------------------------------------------------

			//�V�K
			}else if(mode.equals(JwsConstManager.JWS_MODE_0002)){

//mod start -------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.2
				//�T���v��No����`�F�b�N
				String chk = DataCtrl.getInstance().getTrialTblData().checkDistSampleNo_ALL();
				//����T���v��No���Ȃ��ꍇ
				if(chk==null){
					//�o�^�i�V�K�j
					conJW030(0);

					//�f�[�^�ݒ�
					txtShisakuUser.setText(checkNull(PrototypeData.getDciShisakuUser()));
					txtShisakuNen.setText(checkNull(PrototypeData.getDciShisakuYear()));
					txtShisakuOi.setText(checkNull(PrototypeData.getDciShisakuNum()));

					//�R�s�[�{�^���g�p��
					btnTcopy.setEnabled(true);
					btnZcopy.setEnabled(true);

	//2010/05/12�@�V�T�N�C�b�N�i�����j�v�]�y�Č�No9�z�r�����̕\���@TT.NISHIGAWA�@START
					//�r�����ݒ�
					DataCtrl.getInstance().getUserMstData().setStrHaitaKaishanm(
							DataCtrl.getInstance().getUserMstData().getStrKaishanm());
					DataCtrl.getInstance().getUserMstData().setStrHaitaBushonm(
							DataCtrl.getInstance().getUserMstData().getStrBushonm());
					DataCtrl.getInstance().getUserMstData().setStrHaitaShimei(
							DataCtrl.getInstance().getUserMstData().getStrUsernm());
					HeaderLabel.setText(HeaderLabel.getHeaderUserData());
	//2010/05/12�@�V�T�N�C�b�N�i�����j�v�]�y�Č�No9�z�r�����̕\���@TT.NISHIGAWA�@END

					//�f�[�^�ύX�t���O������
			    	DataCtrl.getInstance().getTrialTblData().setHenkouFg(false);
				}
				//����T���v��No������ꍇ
				else{
					DataCtrl.getInstance().getMessageCtrl().PrintMessageString(JwsConstManager.JWS_ERROR_0048 + chk);
				}
//mod end --------------------------------------------------------------------------------

			//���@�R�s�[
			}else if(mode.equals(JwsConstManager.JWS_MODE_0003)){

				//����SEQ�擾
				String strSeq = null;
				int col;

				TableBase ListHeader = tb.getTrial1Panel().getTrial1().getListHeader();

				for(int i=0; i<ListHeader.getColumnCount(); i++){
					MiddleCellEditor mcShisaku = (MiddleCellEditor)ListHeader.getCellEditor(0, i);
					DefaultCellEditor tcShisaku = (DefaultCellEditor)mcShisaku.getTableCellEditor(0);
					CheckboxBase chkShisaku = (CheckboxBase)tcShisaku.getComponent();
					if(chkShisaku.isSelected()){
						strSeq = chkShisaku.getPk1();
						col = i;
					}
				}
				//�o�^�i���@�R�s�[�j
				if(strSeq != null){

					conJW050(strSeq);

				}else{

					DataCtrl.getInstance().getMessageCtrl().PrintMessageString("������I�����ĉ������B");

				}
				//�f�[�^�ύX�t���O������
		    	DataCtrl.getInstance().getTrialTblData().setHenkouFg(false);
			}

			//2011/04/20 QP@10181_No.41 TT T.Satoh Add Start -------------------------
			//�z���\�̎��얾�׃e�[�u���̏c�X�N���[���o�[�̈ʒu��ݒ�
			tb.getTrial1Panel().getTrial1().getScrollMain().getVerticalScrollBar().setValue(vHaigoBarVal);

			//�z���\�̎��얾�׃e�[�u���̉��X�N���[���o�[�̈ʒu��ݒ�
			tb.getTrial1Panel().getTrial1().getScrollMain().getHorizontalScrollBar().setValue(hHaigoBarVal);

			//�����l�̉��X�N���[���o�[�̈ʒu��ݒ�
			tb.getTrial2Panel().getScroll().getHorizontalScrollBar().setValue(hTokuseiBarVal);

			//�������Z�̉��X�N���[���o�[�̈ʒu��ݒ�
			tb.getTrial5Panel().getScroll().getHorizontalScrollBar().setValue(hGenkaBarVal);
			//2011/04/20 QP@10181_No.41 TT T.Satoh Add End ---------------------------

		}catch(ExceptionBase eb) {

			throw eb;

		}catch(Exception ec){

			ec.printStackTrace();

			//�G���[�ݒ�
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			ex.setStrErrmsg("�o�^���������s���܂���");
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(ec.getMessage());

		}finally{


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

			//�R���|�[�l���g�擾
			CheckboxBase chkAuto = tb.getTrial2Panel().getChkAuto();
			TableBase table = tb.getTrial2Panel().getTable();
			TableBase ListMeisai = tb.getTrial1Panel().getTrial1().getListMeisai();
			TableBase HaigoMeisai = tb.getTrial1Panel().getTrial1().getHaigoMeisai();
// ADD start 20121029 QP@20505 No.11
			//�H���p�^�[���擾
			String ptKotei = PrototypeData.getStrPt_kotei();
			String ptValue = "";
			if(ptKotei == null || ptKotei.length() == 0){
			}else{
				ptValue = DataCtrl.getInstance().getLiteralDataKoteiPtn().selectLiteralVal1(ptKotei);
			}
// ADD end 20121029 QP@20505 No.11

			//�����v�Z���`�F�b�N����Ă���ꍇ�̂ݏ���
			if(chkAuto.isSelected()){

				//�z���f�[�^�擾
				ArrayList HaigoData = DataCtrl.getInstance().getTrialTblData().SearchHaigoData(0);

				//�ő�H�����擾
				int koteiNum = DataCtrl.getInstance().getTrialTblData().getIntMaxKotei();

				//����񐔕����[�v
				for(int i=0; i<ListMeisai.getColumnCount(); i++){
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
					MiddleCellEditor mceSeq = (MiddleCellEditor)table.getCellEditor(0, i);
					DefaultCellEditor dceSeq = (DefaultCellEditor)mceSeq.getTableCellEditor(0);
					TextboxBase chkSeq = (TextboxBase)dceSeq.getComponent();
					int intSeq  = Integer.parseInt(chkSeq.getPk1());

					//�z���\(����\�@)�s��
					//2011/06/13 QP@10181_No.29 TT T.Satoh Change Start -------------------------
					//int maxRow = ListMeisai.getRowCount()-7;
					int maxRow = ListMeisai.getRowCount()-8;
					//2011/06/13 QP@10181_No.29 TT T.Satoh Change End ---------------------------


					//------------------------ �v�Z�K�v���� -----------------------------------
					//�@���v�d��(g)
					BigDecimal goleiZyuryo = new BigDecimal("0.00");

// MOD start 20121009 QP@20505 No.24
//					Object objZyuryo = tb.getTrial1Panel().getTrial1().getListMeisai().getValueAt(maxRow-1, i);
					Object objZyuryo = tb.getTrial1Panel().getTrial1().getListMeisai().getValueAt(maxRow - koteiNum - 1, i);
// MOD end 20121009 QP@20505 No.24

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
					maxRow+=2;

					//------------------------------- ���_�v�Z���� -----------------------------
					//�B���_���v��/�@���v�d��
					BigDecimal sosan = new BigDecimal("0.00");
					if(goleiSosan.intValue() > 0 && goleiZyuryo.intValue() > 0){
						sosan = goleiSosan.divide(goleiZyuryo, 2, BigDecimal.ROUND_HALF_UP);
					}

					//�f�[�^�}��
					table.setValueAt(sosan, 1, i);
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
					table.setValueAt(shokuen, 2, i);
					DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuSyokuen(
		    				intSeq,
		    				DataCtrl.getInstance().getTrialTblData().checkNullDecimal(shokuen.toString()),
		    				DataCtrl.getInstance().getUserMstData().getDciUserid()
		    			);

					//�z���\(����\�@)�֑}��
					ListMeisai.setValueAt(shokuen, maxRow+=1, i);

// ADD start 20121002 QP@20505 No.24
					//------------------------------ �l�r�f�v�Z���� -------------------------------
					//�C�l�r�f���v��/�@���v�d��
					BigDecimal msg = new BigDecimal("0.00");
					if(goleiMsg.intValue() > 0 && goleiZyuryo.intValue() > 0){
						msg = goleiMsg.divide(goleiZyuryo, 2, BigDecimal.ROUND_HALF_UP);
					}

					//�f�[�^�}��
//					table.setValueAt(msg, 2, i);
					DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuMsg(
		    				intSeq,
		    				DataCtrl.getInstance().getTrialTblData().checkNullDecimal(msg.toString()),
		    				DataCtrl.getInstance().getUserMstData().getDciUserid()
		    			);
// ADD end 20121002 QP@20505 No.24

					//--------------------------- �������_�x�v�Z����-----------------------------
					//�D���_���v��/�i�@���v�ʁ[�A���ܗL���v�ʁj
					BigDecimal sui_sando = new BigDecimal("0.00");
					if(goleiSosan.intValue() > 0 && (goleiZyuryo.subtract(goleiGanyu)).intValue() > 0){
						sui_sando = goleiSosan.divide((goleiZyuryo.subtract(goleiGanyu)), 2, BigDecimal.ROUND_HALF_UP);
					}

					//�f�[�^�}��
					table.setValueAt(sui_sando, 3, i);
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
					table.setValueAt(sui_shokuen, 4, i);
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
					table.setValueAt(sui_sakusan, 5, i);
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
//					//�z���\�֑}��
					if(ptValue.equals(JwsConstManager.JWS_KOTEITYPE_1) || ptValue.equals(JwsConstManager.JWS_KOTEITYPE_2)){
						table.setValueAt(sui_msg, 7, i);
					}

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
					if(ptValue.equals(JwsConstManager.JWS_KOTEITYPE_1) || ptValue.equals(JwsConstManager.JWS_KOTEITYPE_2)){
						table.setValueAt(jsn, 6, i);
					}
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
	 *   �S�R�s�[
	 *    :  �S�R�s�[���s��
	 *   @author TT nishigawa
	 *
	 ************************************************************************************/
	public void setCopyData() throws ExceptionBase{
		try{
			//------------------------ ����f�[�^��ʏ�����  -------------------------------
			//����R�[�h
			txtShisakuUser.setText(checkNull(PrototypeData.getDciShisakuUser()));
			txtShisakuNen.setText(checkNull(PrototypeData.getDciShisakuYear()));
			txtShisakuOi.setText(checkNull(PrototypeData.getDciShisakuNum()));
			//���@No
			txtSeihoUser.setText(checkNull(null));
			txtSeihoShu.setText(checkNull(null));
			txtSeihoNen.setText(checkNull(null));
			txtSeihoOi.setText(checkNull(null));
			//�o�^��
			ilTorokuHi.setText(checkNull(PrototypeData.getStrTorokuhi()));
			//�X�V��

			//UPD 2012.4.30�@�yH24�N�x�Ή��z�X�V���Ŏ���i�̍X�V�r������̂��߁A�T�[�o�T�C�h�̍X�V����ҏW
//			ilKosinHi.setText(checkNull(PrototypeData.getStrKosinhi()));
			String strKoshin = checkNull(PrototypeData.getStrKosinhi());
			if(strKoshin.length() >= 10 ){
				strKoshin = strKoshin.substring(0,10);
				strKoshin = strKoshin.replace('-', '/');
			}
			ilKosinHi.setText(strKoshin);

			//�o�^��
			ilTorokuSha.setText(checkNull(PrototypeData.getStrKosinNm()));
			//�X�V��
			ilKosinSha.setText(checkNull(PrototypeData.getStrTorokuNm()));
			//���Z��
			iiShisanHi.setText(checkNull(seiho_Shisan));
			//���Z�m���
			iiKakuteiHi.setText(checkNull(seiho_kakutei));

			//--------------------------- �z���\(����\�@)������  --------------------------------
			//����w�b�_-���@&���Z�}�[�N
			TableBase ListHeader = tb.getTrial1Panel().getTrial1().getListHeader();
			for(int i=0; i<ListHeader.getColumnCount(); i++){
				MiddleCellEditor mc = (MiddleCellEditor)ListHeader.getCellEditor(0, i);
				DefaultCellEditor dc = (DefaultCellEditor)mc.getTableCellEditor(0);
				CheckboxBase cb = (CheckboxBase)dc.getComponent();
				cb.setText("");
			}
			tb.getTrial1Panel().getTrial1().setHeaderShisakuER();

			//--------------------------- ��{���(����\�B)������  --------------------------------
			//�����O���[�v
			tb.getTrial3Panel().getTxtGroup().setText(PrototypeData.getStrGroupNm());
			//�����`�[��
			tb.getTrial3Panel().getTxtTeam().setText(PrototypeData.getStrTeamNm());





//add start -------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.30,No.33,No.34
			//�ҏW�ۃt���O������
			DataCtrl.getInstance().getTrialTblData().setShisakuListHenshuOkFg_init();


			//�z���\(����\�@)������--------------------------------------------------------------------------------------------------
			//�z�����׃e�[�u���擾
			TableBase tbHaigoMeisai = tb.getTrial1Panel().getTrial1().getHaigoMeisai();

			//�z�����׍s�����[�v
			for(int i=0; i<tbHaigoMeisai.getRowCount(); i++){

				//2��ڂ̃I�u�W�F�N�g�擾
				MiddleCellEditor mcKotei = (MiddleCellEditor)tbHaigoMeisai.getCellEditor(i, 0);
				DefaultCellEditor tcKotei = (DefaultCellEditor)mcKotei.getTableCellEditor(i);

				//�H���s�������ꍇ
				if(((JComponent)tcKotei.getComponent()) instanceof CheckboxBase){

					//�H���������g�p�\--------------------------------------------------------------
					//�G�f�B�^�ݒ�
					MiddleCellEditor mceKoteiZokusei = (MiddleCellEditor)tbHaigoMeisai.getCellEditor(i, 3);
					DefaultCellEditor dceKoteiZokusei = (DefaultCellEditor)mceKoteiZokusei.getTableCellEditor(i);
					((ComboBase)dceKoteiZokusei.getComponent()).setEnabled(true);
					//�����_���ݒ�
					MiddleCellRenderer mcrKoteiZokusei =  (MiddleCellRenderer)tbHaigoMeisai.getCellRenderer(i, 3);
					ComboBoxCellRenderer tfcrmcrKoteiZokusei =  (ComboBoxCellRenderer)mcrKoteiZokusei.getTableCellRenderer(i);
					tfcrmcrKoteiZokusei.setColor(Color.white);

					//���샊�X�g�擾
					TableBase tbListMeisai = tb.getTrial1Panel().getTrial1().getListMeisai();
					for(int j=0; j<tbListMeisai.getColumnCount(); j++){
						//�z���ʂ��g�p�\--------------------------------------------------------------
						//�����_���ݒ�
						MiddleCellRenderer mcrRyo =  (MiddleCellRenderer)tbListMeisai.getCellRenderer(i, j);
						TextFieldCellRenderer tfcrRyo =  (TextFieldCellRenderer)mcrRyo.getTableCellRenderer(i);
						tfcrRyo.setColor(Color.white);
					}
				}
				//�H���s�ȊO
				else{

					//3��ڂ̃I�u�W�F�N�g�擾
					MiddleCellEditor mcGenryo = (MiddleCellEditor)tbHaigoMeisai.getCellEditor(i, 2);
					DefaultCellEditor tcGenryo = (DefaultCellEditor)mcGenryo.getTableCellEditor(i);

					//�����s�������ꍇ
					if(((JComponent)tcGenryo.getComponent()) instanceof CheckboxBase){

						//�H���L�[���ڎ擾
						CheckboxBase chkGenryo = (CheckboxBase)tcGenryo.getComponent();
						int koteiCd = Integer.parseInt(chkGenryo.getPk1());
						int koteiSeq = Integer.parseInt(chkGenryo.getPk2());

						//�Z���F�擾
						int Iro = Integer.parseInt(DataCtrl.getInstance().getTrialTblData().searchHaigouGenryoColor(koteiCd,koteiSeq));

						//�V�K����or�R�����g�sorNULL
						boolean sinki_chk = DataCtrl.getInstance().getTrialTblData().searchHaigouGenryoCdSinki(koteiCd, koteiSeq);

						//�����R�[�h���g�p�\-------------------------------------------------------------
						//�G�f�B�^�ݒ�
						MiddleCellEditor mceGenryoCd = (MiddleCellEditor)tbHaigoMeisai.getCellEditor(i, 3);
						DefaultCellEditor dceGenryoCd = (DefaultCellEditor)mceGenryoCd.getTableCellEditor(i);
						((HankakuTextbox)dceGenryoCd.getComponent()).setBackground(Color.white);
						((HankakuTextbox)dceGenryoCd.getComponent()).setEditable(true);
						//�����_���ݒ�
						MiddleCellRenderer mcrGenryoCd =  (MiddleCellRenderer)tbHaigoMeisai.getCellRenderer(i, 3);
						TextFieldCellRenderer tfcrGenryoCd =  (TextFieldCellRenderer)mcrGenryoCd.getTableCellRenderer(i);
						tfcrGenryoCd.setColor(Color.white);

						//���������g�p�\-------------------------------------------------------------
						//�V�K����or�R�����g�sorNULL�̏ꍇ�F�ҏW�\
						if(sinki_chk){
							//�G�f�B�^�ݒ�
							MiddleCellEditor mceGenryoNm = (MiddleCellEditor)tbHaigoMeisai.getCellEditor(i, 4);
							DefaultCellEditor dceGenryoNm = (DefaultCellEditor)mceGenryoNm.getTableCellEditor(i);
							((TextboxBase)dceGenryoNm.getComponent()).setBackground(Color.white);
							((TextboxBase)dceGenryoNm.getComponent()).setEditable(true);
							//�����_���ݒ�
							MiddleCellRenderer mcrGenryoNm =  (MiddleCellRenderer)tbHaigoMeisai.getCellRenderer(i, 4);
							TextFieldCellRenderer tfcrGenryoNm =  (TextFieldCellRenderer)mcrGenryoNm.getTableCellRenderer(i);
							tfcrGenryoNm.setColor(new Color(Iro));
						}
						//�V�K����or�R�����g�sorNULL�̏ꍇ�F�������Ȃ�
						else{

						}

						//�P�����g�p�\-------------------------------------------------------------
						//�V�K����or�R�����g�sorNULL�̏ꍇ�F�ҏW�\
						if(sinki_chk){
							//�G�f�B�^�ݒ�
							MiddleCellEditor mceTanka = (MiddleCellEditor)tbHaigoMeisai.getCellEditor(i, 5);
							DefaultCellEditor dceTanka = (DefaultCellEditor)mceTanka.getTableCellEditor(i);
							((TextboxBase)dceTanka.getComponent()).setBackground(Color.white);
							((TextboxBase)dceTanka.getComponent()).setEditable(true);
							//�����_���ݒ�
							MiddleCellRenderer mcrTanka =  (MiddleCellRenderer)tbHaigoMeisai.getCellRenderer(i, 5);
							TextFieldCellRenderer tfcrTanka =  (TextFieldCellRenderer)mcrTanka.getTableCellRenderer(i);
							tfcrTanka.setColor(new Color(Iro));
						}
						//�V�K����or�R�����g�sorNULL�̏ꍇ�F�������Ȃ�
						else{

						}

						//�������g�p�\-------------------------------------------------------------
						//�G�f�B�^�ݒ�
						MiddleCellEditor mceBudomari = (MiddleCellEditor)tbHaigoMeisai.getCellEditor(i, 6);
						DefaultCellEditor dceBudomari = (DefaultCellEditor)mceBudomari.getTableCellEditor(i);
						((TextboxBase)dceBudomari.getComponent()).setBackground(Color.white);
						((TextboxBase)dceBudomari.getComponent()).setEditable(true);
						//�����_���ݒ�
						MiddleCellRenderer mcrBudomari =  (MiddleCellRenderer)tbHaigoMeisai.getCellRenderer(i, 6);
						TextFieldCellRenderer tfcrBudomari =  (TextFieldCellRenderer)mcrBudomari.getTableCellRenderer(i);
						tfcrBudomari.setColor(new Color(Iro));

						//�������g�p�\-------------------------------------------------------------
						//�G�f�B�^�ݒ�
						MiddleCellEditor mceAbura = (MiddleCellEditor)tbHaigoMeisai.getCellEditor(i, 7);
						DefaultCellEditor dceAbura = (DefaultCellEditor)mceAbura.getTableCellEditor(i);
						((TextboxBase)dceAbura.getComponent()).setBackground(Color.white);
						((TextboxBase)dceAbura.getComponent()).setEditable(true);
						//�����_���ݒ�
						MiddleCellRenderer mcrAbura =  (MiddleCellRenderer)tbHaigoMeisai.getCellRenderer(i, 7);
						TextFieldCellRenderer tfcrAbura =  (TextFieldCellRenderer)mcrAbura.getTableCellRenderer(i);
						tfcrAbura.setColor(new Color(Iro));

						//���샊�X�g�擾
						TableBase tbListMeisai = tb.getTrial1Panel().getTrial1().getListMeisai();
						for(int j=0; j<tbListMeisai.getColumnCount(); j++){

							//����r�d�p�擾
							TableBase tbListRetu = tb.getTrial1Panel().getTrial1().getListHeader();
							MiddleCellEditor mceChk = (MiddleCellEditor)tbListRetu.getCellEditor(0, j);
							DefaultCellEditor dceChk = (DefaultCellEditor)mceChk.getTableCellEditor(0);
							int intShisakuSeq = Integer.parseInt( ((CheckboxBase)dceChk.getComponent()).getPk1() );

							//�F�擾
							int ryoIro = Integer.parseInt(DataCtrl.getInstance().getTrialTblData().searchShisakuListColor(intShisakuSeq, koteiCd, koteiSeq));

							//�z���ʂ��g�p�\---------------------------------------------------------
							//�G�f�B�^�ݒ�
							MiddleCellEditor mceRyo = (MiddleCellEditor)tbListMeisai.getCellEditor(i, j);
							DefaultCellEditor dceRyo = (DefaultCellEditor)mceRyo.getTableCellEditor(i);
							((NumelicTextbox)dceRyo.getComponent()).setBackground(Color.white);
							((NumelicTextbox)dceRyo.getComponent()).setEnabled(true);
							//�����_���ݒ�
							MiddleCellRenderer mcrRyo =  (MiddleCellRenderer)tbListMeisai.getCellRenderer(i, j);
							TextFieldCellRenderer tfcrRyo =  (TextFieldCellRenderer)mcrRyo.getTableCellRenderer(i);
							tfcrRyo.setColor(new Color(ryoIro));
						}
					}
					//�����s�ȊO
					else{
						//���샊�X�g�擾
						TableBase tbListMeisai = tb.getTrial1Panel().getTrial1().getListMeisai();
						//2011/06/13 QP@10181_No.29 TT T.Satoh Change Start -------------------------
						//int gokeiShiagariGyo = tbHaigoMeisai.getRowCount()-7;
						int gokeiShiagariGyo = tbHaigoMeisai.getRowCount()-8;
						//2011/06/13 QP@10181_No.29 TT T.Satoh Change End ---------------------------
						for(int j=0; j<tbListMeisai.getColumnCount(); j++){

							if( i == gokeiShiagariGyo ){
								//���v�d��d�ʂ��g�p�\---------------------------------------------------
								//�G�f�B�^�ݒ�
								MiddleCellEditor mceRyo = (MiddleCellEditor)tbListMeisai.getCellEditor(i, j);
								DefaultCellEditor dceRyo = (DefaultCellEditor)mceRyo.getTableCellEditor(i);
								((NumelicTextbox)dceRyo.getComponent()).setBackground(Color.white);
								((NumelicTextbox)dceRyo.getComponent()).setEnabled(true);
								//�����_���ݒ�
								MiddleCellRenderer mcrRyo =  (MiddleCellRenderer)tbListMeisai.getCellRenderer(i, j);
								TextFieldCellRenderer tfcrRyo =  (TextFieldCellRenderer)mcrRyo.getTableCellRenderer(i);
								tfcrRyo.setColor(Color.white);
							}
							else{
								//�z���ʂ��g�p�\---------------------------------------------------------
								//�����_���ݒ�
								MiddleCellRenderer mcrRyo =  (MiddleCellRenderer)tbListMeisai.getCellRenderer(i, j);
								TextFieldCellRenderer tfcrRyo =  (TextFieldCellRenderer)mcrRyo.getTableCellRenderer(i);
								tfcrRyo.setColor(Color.white);
							}
						}
					}
				}
			}

			//�t�H�[�J�X�����i��L�����𑦎����f����ׁj
			int row = 0;
			int col = 0;
			//�z������
			tbHaigoMeisai.clearSelection();
			row = tbHaigoMeisai.getRowCount()-1;
			col = tbHaigoMeisai.getColumnCount()-1;
			tbHaigoMeisai.setRowSelectionInterval(0, 0);
			tbHaigoMeisai.setColumnSelectionInterval(0, 0);
			tbHaigoMeisai.setRowSelectionInterval(row, row);
			tbHaigoMeisai.setColumnSelectionInterval(col, col);
			//���샊�X�g����
			TableBase tbListMeisai = tb.getTrial1Panel().getTrial1().getListMeisai();
			tbListMeisai.clearSelection();
			row = tbListMeisai.getRowCount()-1;
			col = tbListMeisai.getColumnCount()-1;
			tbListMeisai.setRowSelectionInterval(0, 0);
			tbListMeisai.setColumnSelectionInterval(0, 0);
			tbListMeisai.setRowSelectionInterval(row, row);
			tbListMeisai.setColumnSelectionInterval(col, col);
			//�N���A
			tbHaigoMeisai.clearSelection();
			tbListMeisai.clearSelection();


			//��{���(����\�B)������--------------------------------------------------------------------------------------------------
			//�����w��ҏW�s��
			tb.getTrial3Panel().getCmbShosu().setBackground(JwsConstManager.SHISAKU_ITEM_COLOR2);
			tb.getTrial3Panel().getCmbShosu().setEnabled(true);
			//�e�ʕҏW�s��
			tb.getTrial3Panel().getCmbYoryo().setBackground(JwsConstManager.SHISAKU_ITEM_COLOR2);
			tb.getTrial3Panel().getCmbYoryo().getEditor().getEditorComponent().setBackground(JwsConstManager.SHISAKU_ITEM_COLOR2);
			tb.getTrial3Panel().getCmbYoryo().setEnabled(true);
			//�e�ʒP�ʕҏW�s��
			tb.getTrial3Panel().getCmbtani().setBackground(Color.white);
			tb.getTrial3Panel().getCmbtani().setEnabled(true);


			//�������Z(����\�D)������--------------------------------------------------------------------------------------------------
			TableBase table5 = tb.getTrial5Panel().getTable();
			for(int i=0; i<table5.getColumnCount(); i++){
				//�[�U�ʐ������g�p�\--------------------------------------------------------
				//�G�f�B�^�ݒ�
				MiddleCellEditor mceSuiso = (MiddleCellEditor)table5.getCellEditor(3, i);
				DefaultCellEditor dceSuiso = (DefaultCellEditor)mceSuiso.getTableCellEditor(3);
				((NumelicTextbox)dceSuiso.getComponent()).setBackground(JwsConstManager.SHISAKU_ITEM_COLOR2);
				((NumelicTextbox)dceSuiso.getComponent()).setEditable(true);
				//�����_���ݒ�
				MiddleCellRenderer mcrSuiso =  (MiddleCellRenderer)table5.getCellRenderer(3, i);
				TextFieldCellRenderer tfcrSuiso =  (TextFieldCellRenderer)mcrSuiso.getTableCellRenderer(3);
				tfcrSuiso.setColor(JwsConstManager.SHISAKU_ITEM_COLOR2);

				//�[�U�ʖ������g�p�\--------------------------------------------------------
				//�G�f�B�^�ݒ�
				MiddleCellEditor mceYuso = (MiddleCellEditor)table5.getCellEditor(4, i);
				DefaultCellEditor dceYuso = (DefaultCellEditor)mceYuso.getTableCellEditor(4);
				((NumelicTextbox)dceYuso.getComponent()).setBackground(JwsConstManager.SHISAKU_ITEM_COLOR2);
				((NumelicTextbox)dceYuso.getComponent()).setEditable(true);
				//�����_���ݒ�
				MiddleCellRenderer mcrYuso =  (MiddleCellRenderer)table5.getCellRenderer(4, i);
				TextFieldCellRenderer tfcrYuso =  (TextFieldCellRenderer)mcrYuso.getTableCellRenderer(4);
				tfcrYuso.setColor(JwsConstManager.SHISAKU_ITEM_COLOR2);
			}
//add end   -------------------------------------------------------------------------------

		}catch(Exception e){
			e.printStackTrace();

			//�G���[�ݒ�
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			ex.setStrErrmsg("�S�R�s�[�����s���܂���");
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getMessage());
			throw ex;

		}finally{

		}
	}

	/************************************************************************************
	 *
	 *   �����R�s�[
	 *    :  �����R�s�[���s��
	 *   @author TT nishigawa
	 *
	 ************************************************************************************/
	public void Copy_Tokutyo() throws ExceptionBase{
		try{
			//����f�[�^������
			DataCtrl.getInstance().getTrialTblData().setTraialData(1);

			//����R�[�h������
			//����CD-�Ј�CD
			DataCtrl.getInstance().getTrialTblData().SearchShisakuhinData().setDciShisakuUser(null);
			//����CD-�N
			DataCtrl.getInstance().getTrialTblData().SearchShisakuhinData().setDciShisakuYear(null);
			//����CD-�ǔ�
			DataCtrl.getInstance().getTrialTblData().SearchShisakuhinData().setDciShisakuNum(null);


		}catch(Exception e){

			e.printStackTrace();

			//�G���[�ݒ�
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			ex.setStrErrmsg("�����R�s�[�����s���܂���");
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getMessage());
			throw ex;

		}finally{

		}
	}

	/************************************************************************************
	 *
	 *  ���@�R�s�[�����@XML�ʐM�����iJW050�j
	 *    :  ���@�R�s�[����XML�f�[�^�ʐM�iJW050�j���s��
	 *   @author TT nishigawa
	 *
	 ************************************************************************************/
	private void conJW050(String strSeq) throws ExceptionBase{
		try{
			//--------------------------- ���M�p�����[�^�i�[  ---------------------------------
			String strUser = DataCtrl.getInstance().getParamData().getDciUser().toString();
			String strShisaku_user = DataCtrl.getInstance().getParamData().getStrSisaku_user();
			String strShisaku_nen = DataCtrl.getInstance().getParamData().getStrSisaku_nen();
			String strShisaku_oi = DataCtrl.getInstance().getParamData().getStrSisaku_oi();
			String strShubetuNm = DataCtrl.getInstance().getLiteralDataShubetu().selectLiteralNm(
												DataCtrl.getInstance().getTrialTblData().SearchShisakuhinData().getStrShubetu());


			//--------------------------- ���MXML�f�[�^�쐬  ---------------------------------
			xmlJW050 = new XmlData();
			ArrayList arySetTag = new ArrayList();

			//------------------------------- Root�ǉ�  ------------------------------------
			xmlJW050.AddXmlTag("","JW050");
			arySetTag.clear();

			//------------------------- �@�\ID�ǉ��iUSERINFO�j  ------------------------------
			xmlJW050.AddXmlTag("JW050", "USERINFO");

			//----------------------------- �e�[�u���^�O�ǉ�  --------------------------------
			xmlJW050.AddXmlTag("USERINFO", "table");

			//------------------------------ ���R�[�h�ǉ�  -----------------------------------
			//�����敪
			arySetTag.add(new String[]{"kbn_shori", "3"});
			//���[�UID
			arySetTag.add(new String[]{"id_user",strUser});
			//XML�փ��R�[�h�ǉ�
			xmlJW050.AddXmlTag("table", "rec", arySetTag);
			//�z�񏉊���
			arySetTag.clear();

			//--------------------------- �@�\ID�ǉ��i���@No�o�^�j  --------------------------
			xmlJW050.AddXmlTag("JW050", "SA500");
			//�@�e�[�u���^�O�ǉ�
			xmlJW050.AddXmlTag("SA500", "table");
			//�@���R�[�h�ǉ�
			arySetTag.add(new String[]{"cd_shain", strShisaku_user});
			arySetTag.add(new String[]{"nen", strShisaku_nen});
			arySetTag.add(new String[]{"no_oi", strShisaku_oi});
			arySetTag.add(new String[]{"seq_shisaku", strSeq});
			arySetTag.add(new String[]{"no_shubetu", checkNull(DataCtrl.getInstance().getTrialTblData().SearchShisakuhinData().getStrShubetuNo())});
			arySetTag.add(new String[]{"cd_shubetu", checkNull(strShubetuNm)});

			//�R�����g�R�[�h
			int keta = DataCtrl.getInstance().getTrialTblData().getKaishaGenryo();
			String commentCd = commentSet(keta);
			arySetTag.add(new String[]{"cd_comment", commentCd});


			xmlJW050.AddXmlTag("table", "rec", arySetTag);
			//�z�񏉊���
			arySetTag.clear();

			//------------------------- T120 �z���e�[�u��(tr_haigo) -------------------------
			xmlJW050.AddXmlTag("SA500", "tr_haigo");

			//-------------------------------- ���R�[�h�ǉ�  ---------------------------------
			//�z���f�[�^�擾
			ArrayList addHaigo = DataCtrl.getInstance().getTrialTblData().SearchHaigoData(0);
			for(int i=0; i<addHaigo.size(); i++){
				MixedData MixedData = (MixedData)addHaigo.get(i);
				//����CD-�Ј�CD
				arySetTag.add(new String[]{"cd_shain",checkNull(MixedData.getDciShisakuUser())});
				//����CD-�N
				arySetTag.add(new String[]{"nen",checkNull(MixedData.getDciShisakuYear())});
				//����CD-�ǔ�
				arySetTag.add(new String[]{"no_oi",checkNull(MixedData.getDciShisakuNum())});
				//�H��CD
				arySetTag.add(new String[]{"cd_kotei",checkNull(MixedData.getIntKoteiCd())});
				//�H��SEQ
				arySetTag.add(new String[]{"seq_kotei",checkNull(MixedData.getIntKoteiSeq())});
				//�H����
				arySetTag.add(new String[]{"nm_kotei",checkNull(MixedData.getStrKouteiNm())});
				//�H������
				arySetTag.add(new String[]{"zoku_kotei",checkNull(MixedData.getStrKouteiZokusei())});
				//�H����
				arySetTag.add(new String[]{"sort_kotei",checkNull(MixedData.getIntKoteiNo())});
				//������
				arySetTag.add(new String[]{"sort_genryo",checkNull(MixedData.getIntGenryoNo())});
				//����CD
				arySetTag.add(new String[]{"cd_genryo",checkNull(MixedData.getStrGenryoCd())});
				//���CD
				arySetTag.add(new String[]{"cd_kaisha",checkNull(MixedData.getIntKaishaCd())});
				//����CD
				arySetTag.add(new String[]{"cd_busho",checkNull(MixedData.getIntBushoCd())});
				//��������
				arySetTag.add(new String[]{"nm_genryo",checkNull(MixedData.getStrGenryoNm())});
				//�P��
				arySetTag.add(new String[]{"tanka",checkNull(MixedData.getDciTanka())});
				//����
				arySetTag.add(new String[]{"budomari",checkNull(MixedData.getDciBudomari())});
				//���ܗL��
				arySetTag.add(new String[]{"ritu_abura",checkNull(MixedData.getDciGanyuritu())});
				//�|�_
				arySetTag.add(new String[]{"ritu_sakusan",checkNull(MixedData.getDciSakusan())});
				//�H��
				arySetTag.add(new String[]{"ritu_shokuen",checkNull(MixedData.getDciShokuen())});
				//���_
				arySetTag.add(new String[]{"ritu_sousan",checkNull(MixedData.getDciSosan())});
// ADD start 20121002 QP@20505 No.24
				//�l�r�f
				arySetTag.add(new String[]{"ritu_msg",checkNull(MixedData.getDciMsg())});
// ADD end 20121002 QP@20505 No.24
				//�F
				arySetTag.add(new String[]{"color",checkNull(MixedData.getStrIro())});
				//�o�^��ID
				arySetTag.add(new String[]{"id_toroku",checkNull(MixedData.getDciTorokuId())});
				//�o�^���t
				arySetTag.add(new String[]{"dt_toroku",checkNull(MixedData.getStrTorokuHi())});
				//�X�V��ID
				arySetTag.add(new String[]{"id_koshin",checkNull(MixedData.getDciKosinId())});
				//�X�V���t
				arySetTag.add(new String[]{"dt_koshin",checkNull(MixedData.getStrKosinHi())});

				//XML�փ��R�[�h�ǉ�
				xmlJW050.AddXmlTag("tr_haigo", "rec", arySetTag);
				//�z�񏉊���
				arySetTag.clear();
			}

			//�z�񏉊���
			arySetTag.clear();

			//--------------------------------- XML���M ------------------------------------
			System.out.println("JW050���MXML===============================================================");
			xmlJW050.dispXml();

			xcon = new XmlConnection(xmlJW050);
			xcon.setStrAddress(DataCtrl.getInstance().getJnlpConnect().getStrAddressAjax());
			xcon.XmlSend();

			//--------------------------------- XML��M ------------------------------------
			xmlJW050 = xcon.getXdocRes();

//			System.out.println();
//			System.out.println("JW050��MXML===============================================================");
//			xmlJW050.dispXml();
//			System.out.println();

			//------------------------------- Result�`�F�b�N ----------------------------------
			DataCtrl.getInstance().getResultData().setResultData(xmlJW050);
			if (DataCtrl.getInstance().getResultData().getStrReturnFlg().equals("true")) {

				//---------------------------- ���@�L���X�V ----------------------------------
				TableBase lh = tb.getTrial1Panel().getTrial1().getListHeader();
				for(int i=0; i<lh.getColumnCount(); i++){
					//�R���|�[�l���g�擾
					MiddleCellEditor selectMc = (MiddleCellEditor)lh.getCellEditor(0, i);
					DefaultCellEditor selectDc = (DefaultCellEditor)selectMc.getTableCellEditor(0);
					CheckboxBase cb = (CheckboxBase)selectDc.getComponent();
					String Mark = cb.getText();
					int sisakuSeq = Integer.parseInt(cb.getPk1());
					//�L���폜
					if(Mark != null && Mark.length() > 0){
						Mark = Mark.replace(JwsConstManager.JWS_MARK_0003.toCharArray()[0], ' ');
						Mark = Mark.trim();
					}
					if(sisakuSeq == Integer.parseInt(strSeq)){
						cb.setText(JwsConstManager.JWS_MARK_0003 + " " + Mark);
					}else{
						cb.setText(Mark);
					}
				}

				//-------------------------- ���@No�X�V ----------------------------------
				//�@�\ID�̐ݒ�
				String strKinoId = "SA500";

				//�S�̔z��擾
				ArrayList aryData = xmlJW050.GetAryTag(strKinoId);

				//�@�\�z��擾
				ArrayList kinoData = (ArrayList)aryData.get(0);

				//�e�[�u���z��擾
				ArrayList tableData = (ArrayList)kinoData.get(1);

				//���R�[�h�擾
				for(int i=1; i<tableData.size(); i++){
					//�@�P���R�[�h�擾
					ArrayList recData = ((ArrayList)((ArrayList)tableData.get(i)).get(0));
					for(int j=0; j<recData.size(); j++){
						//�@���ږ��擾
						String recNm = ((String[])recData.get(j))[1];
						//�@���ڒl�擾
						String recVal = ((String[])recData.get(j))[2];

						//�@���@No1
						if ( recNm == "no_seiho1" ) {
							//��ʕ\��
							txtSeihoUser.setText(recVal.split("-")[0]);
							txtSeihoShu.setText(recVal.split("-")[1]);
							txtSeihoNen.setText(recVal.split("-")[2]);
							txtSeihoOi.setText(recVal.split("-")[3]);

							//�f�[�^�}��
							DataCtrl.getInstance().getTrialTblData().UpdRetuSeiho1(
									Integer.parseInt(strSeq),
									recVal,
									DataCtrl.getInstance().getUserMstData().getDciUserid());
						}
						//�@���@No2
						if ( recNm == "no_seiho2" ) {
							//�f�[�^�}��
							DataCtrl.getInstance().getTrialTblData().UpdRetuSeiho2(
									Integer.parseInt(strSeq),
									recVal,
									DataCtrl.getInstance().getUserMstData().getDciUserid());
						}

						//�@���@No3
						if ( recNm == "no_seiho3" ) {
							//�f�[�^�}��
							DataCtrl.getInstance().getTrialTblData().UpdRetuSeiho3(
									Integer.parseInt(strSeq),
									recVal,
									DataCtrl.getInstance().getUserMstData().getDciUserid());
						}
						//�@���@No4
						if ( recNm == "no_seiho4" ) {
							//�f�[�^�}��
							DataCtrl.getInstance().getTrialTblData().UpdRetuSeiho4(
									Integer.parseInt(strSeq),
									recVal,
									DataCtrl.getInstance().getUserMstData().getDciUserid());
						}
						//�@���@No5
						if ( recNm == "no_seiho5" ) {
							//�f�[�^�}��
							DataCtrl.getInstance().getTrialTblData().UpdRetuSeiho5(
									Integer.parseInt(strSeq),
									recVal,
									DataCtrl.getInstance().getUserMstData().getDciUserid());
						}
					}
				}

				//�����H�����
				ManufacturingPanel mp = DataCtrl.getInstance().getManufacturingSubDisp().getManufacturingPanel();
				//���@No���I������Ă���ꍇ
				if(mp.getCombo().getSelectedIndex() == 2){
					mp.dispSeiho();
				}

				//�ĕ\��
				tb.getTrial1Panel().getTrial1().setHeaderShisakuER();
				DataCtrl.getInstance().getMessageCtrl().PrintMessageString("����ɐ��@�R�s�[�������������܂����B");

			}else{
				ExceptionBase ExceptionBase  = new ExceptionBase();
				throw ExceptionBase;
			}
		}catch(ExceptionBase eb){
			throw eb;

		}catch(Exception e){
			e.printStackTrace();

			//�G���[�ݒ�
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			ex.setStrErrmsg("���@�R�s�[�����s���܂���");
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getMessage());
			throw ex;

		}finally{

		}
	}

	/************************************************************************************
	 *
	 *  ���[�U���擾�����@XML�ʐM�����iJW020�j
	 *    :  ���[�U���擾
	 *   @author TT nishigawa
	 *
	 ************************************************************************************/
	private void conUserInfo() throws ExceptionBase{
		try{
			//--------------------------- ���M�p�����[�^�i�[  ---------------------------------
			String strUser = DataCtrl.getInstance().getParamData().getDciUser().toString();
			String strShisaku_user = DataCtrl.getInstance().getParamData().getStrSisaku_user();
			String strShisaku_nen = DataCtrl.getInstance().getParamData().getStrSisaku_nen();
			String strShisaku_oi = DataCtrl.getInstance().getParamData().getStrSisaku_oi();
			String strGamenId = DataCtrl.getInstance().getParamData().getStrMode();

			//--------------------------- ���MXML�f�[�^�쐬  ---------------------------------
			xmlJWuser = new XmlData();
			ArrayList arySetTag = new ArrayList();

			//------------------------------- Root�ǉ�  ------------------------------------
			xmlJWuser.AddXmlTag("","JW020");
			arySetTag.clear();

			//------------------------- �@�\ID�ǉ��iUSERINFO�j  ------------------------------
			xmlJWuser.AddXmlTag("JW020", "USERINFO");

			//----------------------------- �e�[�u���^�O�ǉ�  ---------------------------------
			xmlJWuser.AddXmlTag("USERINFO", "table");

			//------------------------------ ���R�[�h�ǉ�  -----------------------------------
			//�����敪
			arySetTag.add(new String[]{"kbn_shori", "3"});
			//���[�UID
			arySetTag.add(new String[]{"id_user",strUser});
			//XML�փ��R�[�h�ǉ�
			xmlJWuser.AddXmlTag("table", "rec", arySetTag);
			//�z�񏉊���
			arySetTag.clear();

			//--------------------------- �@�\ID�ǉ��i�r������j  -----------------------------
			xmlJWuser.AddXmlTag("JW020", "SA420");
			//�@�e�[�u���^�O�ǉ�
			xmlJWuser.AddXmlTag("SA420", "table");
			//�@���R�[�h�ǉ�
			arySetTag.add(new String[]{"kubun_ziko", "0"});
			arySetTag.add(new String[]{"kubun_haita", "0"});
			arySetTag.add(new String[]{"id_user", strUser});
			arySetTag.add(new String[]{"cd_shain", strShisaku_user});
			arySetTag.add(new String[]{"nen", strShisaku_nen});
			arySetTag.add(new String[]{"no_oi", strShisaku_oi});
			xmlJWuser.AddXmlTag("table", "rec", arySetTag);
			//�z�񏉊���
			arySetTag.clear();

			//--------------------------------- XML���M ------------------------------------
			//System.out.println("JW020���MXML===============================================================");
			//xmlJWuser.dispXml();
			xcon = new XmlConnection(xmlJWuser);
			xcon.setStrAddress(DataCtrl.getInstance().getJnlpConnect().getStrAddressAjax());
			xcon.XmlSend();

			//--------------------------------- XML��M ------------------------------------
			xmlJWuser = xcon.getXdocRes();
			//System.out.println();
			//System.out.println("JW020��MXML===============================================================");
			//xmlJW020.dispXml();
			//System.out.println();

			//Result�`�F�b�N
			DataCtrl.getInstance().getResultData().setResultData(xmlJWuser);
			if (DataCtrl.getInstance().getResultData().getStrReturnFlg().equals("true")) {

			}else{
				ExceptionBase ExceptionBase  = new ExceptionBase();
				throw ExceptionBase;
			}

			//---------------------------- ���[�U�}�X�^�f�[�^�ݒ�  -----------------------------
			DataCtrl.getInstance().getUserMstData().setUserData(xmlJWuser);

		}catch(ExceptionBase eb){
			throw eb;

		}catch(Exception e){
			e.printStackTrace();

			//�G���[�ݒ�
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			ex.setStrErrmsg("���[�U�f�[�^�擾�Ɏ��s���܂����B");
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getMessage());
			throw ex;

		}finally{

		}
	}


	/************************************************************************************
	 *
	 *  ����R�s�[�����i�r������j�@XML�ʐM�����iJW020�j
	 *    :  �r�����䏈��XML�f�[�^�ʐM�iJW020�j���s��
	 *   @author TT nishigawa
	 *
	 ************************************************************************************/
	private void conJW020() throws ExceptionBase{
		try{
			//--------------------------- ���M�p�����[�^�i�[  ---------------------------------
			String strUser = DataCtrl.getInstance().getParamData().getDciUser().toString();
			String strShisaku_user = DataCtrl.getInstance().getParamData().getStrSisaku_user();
			String strShisaku_nen = DataCtrl.getInstance().getParamData().getStrSisaku_nen();
			String strShisaku_oi = DataCtrl.getInstance().getParamData().getStrSisaku_oi();
			String strGamenId = DataCtrl.getInstance().getParamData().getStrMode();

			//--------------------------- ���MXML�f�[�^�쐬  ---------------------------------
			xmlJW020 = new XmlData();
			ArrayList arySetTag = new ArrayList();

			//------------------------------- Root�ǉ�  ------------------------------------
			xmlJW020.AddXmlTag("","JW020");
			arySetTag.clear();

			//------------------------- �@�\ID�ǉ��iUSERINFO�j  ------------------------------
			xmlJW020.AddXmlTag("JW020", "USERINFO");

			//----------------------------- �e�[�u���^�O�ǉ�  ---------------------------------
			xmlJW020.AddXmlTag("USERINFO", "table");

			//------------------------------ ���R�[�h�ǉ�  -----------------------------------
			//�����敪
			arySetTag.add(new String[]{"kbn_shori", "3"});
			//���[�UID
			arySetTag.add(new String[]{"id_user",strUser});
			//XML�փ��R�[�h�ǉ�
			xmlJW020.AddXmlTag("table", "rec", arySetTag);
			//�z�񏉊���
			arySetTag.clear();

			//--------------------------- �@�\ID�ǉ��i�r������j  -----------------------------
			xmlJW020.AddXmlTag("JW020", "SA420");
			//�@�e�[�u���^�O�ǉ�
			xmlJW020.AddXmlTag("SA420", "table");
			//�@���R�[�h�ǉ�
			arySetTag.add(new String[]{"kubun_ziko", "1"});
			arySetTag.add(new String[]{"kubun_haita", "0"});
			arySetTag.add(new String[]{"id_user", strUser});
			arySetTag.add(new String[]{"cd_shain", strShisaku_user});
			arySetTag.add(new String[]{"nen", strShisaku_nen});
			arySetTag.add(new String[]{"no_oi", strShisaku_oi});
			xmlJW020.AddXmlTag("table", "rec", arySetTag);
			//�z�񏉊���
			arySetTag.clear();

			//--------------------------------- XML���M ------------------------------------
			//System.out.println("JW020���MXML===============================================================");
			//xmlJW020.dispXml();
			xcon = new XmlConnection(xmlJW020);
			xcon.setStrAddress(DataCtrl.getInstance().getJnlpConnect().getStrAddressAjax());
			xcon.XmlSend();

			//--------------------------------- XML��M ------------------------------------
			xmlJW020 = xcon.getXdocRes();
			//System.out.println();
			//System.out.println("JW020��MXML===============================================================");
			//xmlJW020.dispXml();
			//System.out.println();

			//Result�`�F�b�N
			DataCtrl.getInstance().getResultData().setResultData(xmlJW020);
			if (DataCtrl.getInstance().getResultData().getStrReturnFlg().equals("true")) {

			}else{
				ExceptionBase ExceptionBase  = new ExceptionBase();
				throw ExceptionBase;
			}
		}catch(ExceptionBase eb){
			throw eb;

		}catch(Exception e){
			e.printStackTrace();

			//�G���[�ݒ�
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			ex.setStrErrmsg("����f�[�^�̔r������Ɏ��s���܂���");
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getMessage());
			throw ex;

		}finally{

		}
	}

	/************************************************************************************
	 *
	 *   �I�������@XML�ʐM�����iJW060�j
	 *    :  �I������XML�f�[�^�ʐM�iJW060�j���s��
	 *   @author TT nishigawa
	 *
	 ************************************************************************************/
	private void conJW060() throws ExceptionBase{
		try{
			//--------------------------- ���M�p�����[�^�i�[  ---------------------------------
			String strUser = DataCtrl.getInstance().getParamData().getDciUser().toString();
			String strShisaku_user = DataCtrl.getInstance().getParamData().getStrSisaku_user();
			String strShisaku_nen = DataCtrl.getInstance().getParamData().getStrSisaku_nen();
			String strShisaku_oi = DataCtrl.getInstance().getParamData().getStrSisaku_oi();
			String strGamenId = DataCtrl.getInstance().getParamData().getStrMode();

			//--------------------------- ���MXML�f�[�^�쐬  ---------------------------------
			xmlJW060 = new XmlData();
			ArrayList arySetTag = new ArrayList();

			//------------------------------- Root�ǉ�  ------------------------------------
			xmlJW060.AddXmlTag("","JW060");
			arySetTag.clear();

			//------------------------- �@�\ID�ǉ��iUSERINFO�j  ------------------------------
			xmlJW060.AddXmlTag("JW060", "USERINFO");

			//----------------------------- �e�[�u���^�O�ǉ�  ---------------------------------
			xmlJW060.AddXmlTag("USERINFO", "table");

			//------------------------------ ���R�[�h�ǉ�  -----------------------------------
			//�����敪
			arySetTag.add(new String[]{"kbn_shori", "3"});
			//���[�UID
			arySetTag.add(new String[]{"id_user",strUser});
			//XML�փ��R�[�h�ǉ�
			xmlJW060.AddXmlTag("table", "rec", arySetTag);
			//�z�񏉊���
			arySetTag.clear();

			//--------------------------- �@�\ID�ǉ��i�r������j  -----------------------------
			xmlJW060.AddXmlTag("JW060", "SA420");
			//�@�e�[�u���^�O�ǉ�
			xmlJW060.AddXmlTag("SA420", "table");

			//�@���R�[�h�ǉ�
			arySetTag.add(new String[]{"kubun_ziko", "1"});
			arySetTag.add(new String[]{"kubun_haita", "0"});
			arySetTag.add(new String[]{"id_user", strUser});
			arySetTag.add(new String[]{"cd_shain", strShisaku_user});
			arySetTag.add(new String[]{"nen", strShisaku_nen});
			arySetTag.add(new String[]{"no_oi", strShisaku_oi});
			xmlJW060.AddXmlTag("table", "rec", arySetTag);
			//�z�񏉊���
			arySetTag.clear();

			//�@XML���M
			//System.out.println("���MXML===============================================================");
			//xmlJW060.dispXml();

			xcon = new XmlConnection(xmlJW060);
			xcon.setStrAddress(DataCtrl.getInstance().getJnlpConnect().getStrAddressAjax());
			xcon.XmlSend();
			//�@XML��M
			xmlJW060 = xcon.getXdocRes();

			//System.out.println();
			//System.out.println("��MXML===============================================================");
			//xmlJW060.dispXml();
			//System.out.println();

			//Result�`�F�b�N
			DataCtrl.getInstance().getResultData().setResultData(xmlJW060);
			if (DataCtrl.getInstance().getResultData().getStrReturnFlg().equals("true")) {

			}else{
				ExceptionBase ExceptionBase  = new ExceptionBase();
				throw ExceptionBase;
			}

		}catch(ExceptionBase eb){
			throw eb;

		}catch(Exception e){
			e.printStackTrace();

			//�G���[�ݒ�
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			ex.setStrErrmsg("�I�����������s���܂���");
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getMessage());
			throw ex;

		}finally{

		}
	}

	/************************************************************************************
	 *
	 *   �V�K�o�^�����@XML�ʐM�����iJW030�j
	 *    :  �V�K�o�^����XML�f�[�^�ʐM�iJW030�j���s��
	 *   @param intChkMsg : �o�^�����b�Z�[�W�̎w��
	 *    [0:�o�^, 1:�����ۑ�(����\), 2:�����ۑ�(�T���v��������), 3:�����ۑ�(�h�{�v�Z��)]
	 *   @author TT nishigawa
	 *
	 ************************************************************************************/
	private void conJW030(int intChkMsg) throws ExceptionBase{
		try{


			//DataCtrl.getInstance().getMessageCtrl().PrintMessageString("����Ɏ���o�^�������������܂����B");

			//--------------------------- ���M�p�����[�^�i�[  ---------------------------------
			String strUser = DataCtrl.getInstance().getParamData().getDciUser().toString();
			String strShisaku_user = DataCtrl.getInstance().getParamData().getStrSisaku_user();
			String strShisaku_nen = DataCtrl.getInstance().getParamData().getStrSisaku_nen();
			String strShisaku_oi = DataCtrl.getInstance().getParamData().getStrSisaku_oi();
			String strGamenId = DataCtrl.getInstance().getParamData().getStrMode();



			String nen = new SimpleDateFormat("yy").format(new Date());


			//--------------------------- ���MXML�f�[�^�쐬  ---------------------------------
			xmlJW030 = new XmlData();
			ArrayList arySetTag = new ArrayList();

			//------------------------------- Root�ǉ�  ------------------------------------
			xmlJW030.AddXmlTag("","JW030");
			arySetTag.clear();

			//------------------------- �@�\ID�ǉ��iUSERINFO�j  ------------------------------
			xmlJW030.AddXmlTag("JW030", "USERINFO");

			//----------------------------- �e�[�u���^�O�ǉ�  ---------------------------------
			xmlJW030.AddXmlTag("USERINFO", "table");

			//------------------------------ ���R�[�h�ǉ�  -----------------------------------
			//�����敪
			arySetTag.add(new String[]{"kbn_shori", "3"});
			//���[�UID
			arySetTag.add(new String[]{"id_user",strUser});
			//XML�փ��R�[�h�ǉ�
			xmlJW030.AddXmlTag("table", "rec", arySetTag);
			//�z�񏉊���
			arySetTag.clear();

			//------------------------ �@�\ID�ǉ��i������o�^�j  ---------------------------
			xmlJW030.AddXmlTag("JW030", "SA490");

			//--------------------- T110 ����i�e�[�u��(tr_shisakuhin) ----------------------
			xmlJW030.AddXmlTag("SA490", "tr_shisakuhin");

			//-------------------------------- ���R�[�h�ǉ�  ---------------------------------

			//����i�f�[�^�擾
			PrototypeData addPrototypeData = DataCtrl.getInstance().getTrialTblData().SearchShisakuhinData();
			//����CD-�Ј�CD
			arySetTag.add(new String[]{"cd_shain", strUser});
			//����CD-�N
			arySetTag.add(new String[]{"nen", nen});
			//����CD-�ǔ�
			arySetTag.add(new String[]{"no_oi", checkNull(addPrototypeData.getDciShisakuNum())});
			//�˗��ԍ�
			arySetTag.add(new String[]{"no_irai", checkNull(addPrototypeData.getStrIrai())});
			//�i��
			arySetTag.add(new String[]{"nm_hin", checkNull(addPrototypeData.getStrHinnm())});

			//�w��H��-���CD
			if(addPrototypeData.getIntKaishacd() <= 0){ //��ЃR�[�h��0�ȉ��̏ꍇ�͋�
				arySetTag.add(new String[]{"cd_kaisha", ""});
			}else{
				arySetTag.add(new String[]{"cd_kaisha", checkNull(addPrototypeData.getIntKaishacd())});
			}
			//�w��H��-�H��CD
			if(addPrototypeData.getIntKojoco() <= 0){ //�H��R�[�h��0�ȉ��̏ꍇ�͋�
				arySetTag.add(new String[]{"cd_kojo", ""});
			}else{
				arySetTag.add(new String[]{"cd_kojo", checkNull(addPrototypeData.getIntKojoco())});
			}

			//���CD
			arySetTag.add(new String[]{"cd_shubetu", checkNull(addPrototypeData.getStrShubetu())});
			//���No
			arySetTag.add(new String[]{"no_shubetu", checkNull(addPrototypeData.getStrShubetuNo())});
			//�O���[�vCD
			arySetTag.add(new String[]{"cd_group", checkNull(addPrototypeData.getIntGroupcd())});
			//�`�[��CD
			arySetTag.add(new String[]{"cd_team", checkNull(addPrototypeData.getIntTeamcd())});
			//�ꊇ�\��CD
			arySetTag.add(new String[]{"cd_ikatu", checkNull(addPrototypeData.getStrIkatu())});
			//�W������CD
			arySetTag.add(new String[]{"cd_genre", checkNull(addPrototypeData.getStrZyanru())});
			//���[�UCD
			arySetTag.add(new String[]{"cd_user", checkNull(addPrototypeData.getStrUsercd())});
			//��������
			arySetTag.add(new String[]{"tokuchogenryo", checkNull(addPrototypeData.getStrTokutyo())});
			//�p�r
			arySetTag.add(new String[]{"youto", checkNull(addPrototypeData.getStrYoto())});
			//���i��CD
			arySetTag.add(new String[]{"cd_kakaku", checkNull(addPrototypeData.getStrKakaku())});
			//�S���c��CD
			arySetTag.add(new String[]{"cd_eigyo", checkNull(addPrototypeData.getStrTantoEigyo())});
			//�������@CD
			arySetTag.add(new String[]{"cd_hoho", checkNull(addPrototypeData.getStrSeizocd())});
			//�[�U���@CD
			arySetTag.add(new String[]{"cd_juten", checkNull(addPrototypeData.getStrZyutencd())});
			//�E�ە��@
			arySetTag.add(new String[]{"hoho_sakin", checkNull(addPrototypeData.getStrSakin())});
			//�e��E���
			arySetTag.add(new String[]{"youki", checkNull(addPrototypeData.getStrYokihozai())});
			//�e��
			//2011/04/13 QP@10181_No.26 TT T.Satoh Change Start -------------------------
			//seisuCheck(checkNull(addPrototypeData.getStrYoryo()), "����f�[�^��� ����\�B �e��", 0);
			seisuCheck(checkNull(addPrototypeData.getStrYoryo()), "����f�[�^��� ��{��� �e��", 0);
			//2011/04/13 QP@10181_No.26 TT T.Satoh Change End ---------------------------
			arySetTag.add(new String[]{"yoryo", checkNull(addPrototypeData.getStrYoryo())});

			//�e�ʒP��CD
			arySetTag.add(new String[]{"cd_tani", checkNull(addPrototypeData.getStrTani())});

			//���萔
			//2011/04/13 QP@10181_No.26 TT T.Satoh Change Start -------------------------
			//seisuCheck(checkNull(addPrototypeData.getStrIrisu()), "����f�[�^��� ����\�B ���萔", 0);
			seisuCheck(checkNull(addPrototypeData.getStrIrisu()), "����f�[�^��� ��{��� ���萔", 0);
			//2011/04/13 QP@10181_No.26 TT T.Satoh Change End ---------------------------
			arySetTag.add(new String[]{"su_iri", checkNull(addPrototypeData.getStrIrisu())});

			//�戵���xCD
			arySetTag.add(new String[]{"cd_ondo", checkNull(addPrototypeData.getStrOndo())});
			//�ܖ�����
			arySetTag.add(new String[]{"shomikikan", checkNull(addPrototypeData.getStrShomi())});

			//����
			//2011/04/13 QP@10181_No.26 TT T.Satoh Change Start -------------------------
			//ShosuCheck(checkNull(addPrototypeData.getStrGenka()), 2, "����f�[�^��� ����\�B ������]", 0);
			ShosuCheck(checkNull(addPrototypeData.getStrGenka()), 2, "����f�[�^��� ��{��� ������]", 0);
			//2011/04/13 QP@10181_No.26 TT T.Satoh Change End ---------------------------
			arySetTag.add(new String[]{"genka", checkNull(addPrototypeData.getStrGenka())});

			//����
			//2011/04/13 QP@10181_No.26 TT T.Satoh Change Start -------------------------
			//ShosuCheck(checkNull(addPrototypeData.getStrBaika()), 2, "����f�[�^��� ����\�B ������]", 0);
			ShosuCheck(checkNull(addPrototypeData.getStrBaika()), 2, "����f�[�^��� ��{��� ������]", 0);
			//2011/04/13 QP@10181_No.26 TT T.Satoh Change End ---------------------------
			arySetTag.add(new String[]{"baika", checkNull(addPrototypeData.getStrBaika())});

			//�z�蕨��
			arySetTag.add(new String[]{"buturyo", checkNull(addPrototypeData.getStrSotei())});
			//��������
			arySetTag.add(new String[]{"dt_hatubai", checkNull(addPrototypeData.getStrHatubai())});
			//�v�攄��
			arySetTag.add(new String[]{"uriage_k", checkNull(addPrototypeData.getStrKeikakuUri())});
			//�v�旘�v
			arySetTag.add(new String[]{"rieki_k", checkNull(addPrototypeData.getStrKeikakuRie())});
			//�̔��㔄��
			arySetTag.add(new String[]{"uriage_h", checkNull(addPrototypeData.getStrHanbaigoUri())});
			//�̔��㗘�v
			arySetTag.add(new String[]{"rieki_h", checkNull(addPrototypeData.getStrHanbaigoRie())});
			//�׎pCD
			arySetTag.add(new String[]{"cd_nisugata", checkNull(addPrototypeData.getStrNishugata())});
			//������
			arySetTag.add(new String[]{"memo", checkNull(addPrototypeData.getStrSogo())});
			//�����w��
			arySetTag.add(new String[]{"keta_shosu", checkNull(addPrototypeData.getStrShosu())});

			//�����w��l�P
			String cd = addPrototypeData.getStrShosu();
			int val1 = 0;
			if(cd != null && cd.length()>0){
				val1 = DataCtrl.getInstance().getLiteralDataShosu().selectLiteralVal1(Integer.parseInt(cd));
			}
			arySetTag.add(new String[]{"keta_shosu_val1", Integer.toString(val1)});

			//�p�~��
			arySetTag.add(new String[]{"kbn_haishi", checkNull(addPrototypeData.getIntHaisi())});
			//�r��
			arySetTag.add(new String[]{"kbn_haita", checkNull(addPrototypeData.getDciHaita())});
			//���@����
			arySetTag.add(new String[]{"seq_shisaku", checkNull(addPrototypeData.getIntSeihoShisaku())});
			//���상��
			arySetTag.add(new String[]{"memo_shisaku", checkNull(addPrototypeData.getStrShisakuMemo())});
			//���ӎ����\��
			arySetTag.add(new String[]{"flg_chui", checkNull(addPrototypeData.getIntChuiFg())});
			//�o�^��ID
			arySetTag.add(new String[]{"id_toroku", checkNull(DataCtrl.getInstance().getUserMstData().getDciUserid())});
			//�o�^���t
			arySetTag.add(new String[]{"dt_toroku", DataCtrl.getInstance().getTrialTblData().getSysDate()});
			//�X�V��ID
			arySetTag.add(new String[]{"id_koshin", checkNull(DataCtrl.getInstance().getUserMstData().getDciUserid())});

			//�X�V���t
			//UPD 2012.4.30�@�yH24�N�x�Ή��z�X�V���Ŏ���i�̍X�V�r��������s�����ߐV�K�o�^���w�����邽�ߓ��t�ɉ�������Ȃ��B
//			arySetTag.add(new String[]{"dt_koshin", DataCtrl.getInstance().getTrialTblData().getSysDate()});
			arySetTag.add(new String[]{"dt_koshin", checkNull(addPrototypeData.getStrKosinhi())});

//2011/05/11 QP@10181_No.42_49_72 TT Nishigawa Add Start -------------------------
			//�H���p�^�[��
			arySetTag.add(new String[]{"pt_kotei", checkNull(addPrototypeData.getStrPt_kotei())});
//2011/05/11 QP@10181_No.42_49_72 TT Nishigawa Add End --------------------------

			//�yQP@20505_No.38�z2012/10/17 TT H.SHIMA ADD Start
			arySetTag.add(new String[]{"flg_secret", checkNull(addPrototypeData.getStrSecret())});
			//�yQP@20505_No.38�z2012/10/17 TT H.SHIMA ADD End
//ADD 2013/06/19 ogawa �yQP@30151�zNo.9 start
			//�̐Ӊ��CD
			if(addPrototypeData.getIntHansekicd() <= 0){ //�̐Ӊ�ЃR�[�h��0�ȉ��̏ꍇ�͋�
				arySetTag.add(new String[]{"cd_hanseki", ""});
			}else{
				arySetTag.add(new String[]{"cd_hanseki", checkNull(addPrototypeData.getIntHansekicd())});
			}
//ADD 2013/06/19 ogawa �yQP@30151�zNo.9 end

			//XML�փ��R�[�h�ǉ�
			xmlJW030.AddXmlTag("tr_shisakuhin", "rec", arySetTag);
			//�z�񏉊���
			arySetTag.clear();

			//------------------------- T120 �z���e�[�u��(tr_haigo) -------------------------
			xmlJW030.AddXmlTag("SA490", "tr_haigo");

			//-------------------------------- ���R�[�h�ǉ�  ---------------------------------
			//�z���f�[�^�擾
			ArrayList addHaigo = DataCtrl.getInstance().getTrialTblData().SearchHaigoData(0);
			for(int i=0; i<addHaigo.size(); i++){
				MixedData MixedData = (MixedData)addHaigo.get(i);
				//����CD-�Ј�CD
				arySetTag.add(new String[]{"cd_shain",strUser});
				//����CD-�N
				arySetTag.add(new String[]{"nen",nen});
				//����CD-�ǔ�
				arySetTag.add(new String[]{"no_oi",checkNull(MixedData.getDciShisakuNum())});
				//�H��CD
				arySetTag.add(new String[]{"cd_kotei",checkNull(MixedData.getIntKoteiCd())});
				//�H��SEQ
				arySetTag.add(new String[]{"seq_kotei",checkNull(MixedData.getIntKoteiSeq())});
				//�H����
				arySetTag.add(new String[]{"nm_kotei",checkNull(MixedData.getStrKouteiNm())});
				//�H������
				arySetTag.add(new String[]{"zoku_kotei",checkNull(MixedData.getStrKouteiZokusei())});
				//�H����
				arySetTag.add(new String[]{"sort_kotei",checkNull(MixedData.getIntKoteiNo())});
				//������
				arySetTag.add(new String[]{"sort_genryo",checkNull(MixedData.getIntGenryoNo())});
				//����CD
				arySetTag.add(new String[]{"cd_genryo",checkNull(MixedData.getStrGenryoCd())});
				//���CD
				arySetTag.add(new String[]{"cd_kaisha",checkNull(MixedData.getIntKaishaCd())});
				//����CD
				arySetTag.add(new String[]{"cd_busho",checkNull(MixedData.getIntBushoCd())});
				//��������
				arySetTag.add(new String[]{"nm_genryo",checkNull(MixedData.getStrGenryoNm())});
				//�P��
				arySetTag.add(new String[]{"tanka",checkNull(MixedData.getDciTanka())});
				//����
				arySetTag.add(new String[]{"budomari",checkNull(MixedData.getDciBudomari())});
				//���ܗL��
				arySetTag.add(new String[]{"ritu_abura",checkNull(MixedData.getDciGanyuritu())});
				//�|�_
				arySetTag.add(new String[]{"ritu_sakusan",checkNull(MixedData.getDciSakusan())});
				//�H��
				arySetTag.add(new String[]{"ritu_shokuen",checkNull(MixedData.getDciShokuen())});
				//���_
				arySetTag.add(new String[]{"ritu_sousan",checkNull(MixedData.getDciSosan())});
// ADD start 20121002 QP@20505 No.24
				//�l�r�f
				arySetTag.add(new String[]{"ritu_msg",checkNull(MixedData.getDciMsg())});
// ADD end 20121002 QP@20505 No.24
				//�F
				arySetTag.add(new String[]{"color",checkNull(MixedData.getStrIro())});
				//�o�^��ID
				arySetTag.add(new String[]{"id_toroku",checkNull(DataCtrl.getInstance().getUserMstData().getDciUserid())});
				//�o�^���t
				arySetTag.add(new String[]{"dt_toroku",DataCtrl.getInstance().getTrialTblData().getSysDate()});
				//�X�V��ID
				arySetTag.add(new String[]{"id_koshin",checkNull(DataCtrl.getInstance().getUserMstData().getDciUserid())});

				//�X�V���t
				arySetTag.add(new String[]{"dt_koshin",DataCtrl.getInstance().getTrialTblData().getSysDate()});

				//XML�փ��R�[�h�ǉ�
				xmlJW030.AddXmlTag("tr_haigo", "rec", arySetTag);
				//�z�񏉊���
				arySetTag.clear();
			}

			//----------------------- T131 ����e�[�u��(tr_shisaku) -------------------------
			xmlJW030.AddXmlTag("SA490", "tr_shisaku");

			//-------------------------------- ���R�[�h�ǉ�  ---------------------------------
			//�����f�[�^�擾
			ArrayList addRetu = DataCtrl.getInstance().getTrialTblData().SearchShisakuRetuData(0);
			for(int i=0; i<addRetu.size(); i++){
				TrialData TrialData = (TrialData)addRetu.get(i);
				//����CD-�Ј�CD
				arySetTag.add(new String[]{"cd_shain",strUser});
				//����CD-�N
				arySetTag.add(new String[]{"nen",nen});
				//����CD-�ǔ�
				arySetTag.add(new String[]{"no_oi",checkNull(TrialData.getDciShisakuNum())});
				//����SEQ
				arySetTag.add(new String[]{"seq_shisaku",checkNull(TrialData.getIntShisakuSeq())});
				//����\����
				arySetTag.add(new String[]{"sort_shisaku",checkNull(TrialData.getIntHyojiNo())});
				//���ӎ���NO
				arySetTag.add(new String[]{"no_chui",checkNull(TrialData.getStrTyuiNo())});
				//�T���v��NO�i���́j
				arySetTag.add(new String[]{"nm_sample",checkNull(TrialData.getStrSampleNo())});
				//����
				arySetTag.add(new String[]{"memo",checkNull(TrialData.getStrMemo())});
				//���Flg
				arySetTag.add(new String[]{"flg_print",checkNull(TrialData.getIntInsatuFlg())});
				//�����v�ZFlg
				arySetTag.add(new String[]{"flg_auto",checkNull(TrialData.getIntZidoKei())});
				//�������ZNo
				arySetTag.add(new String[]{"no_shisan",checkNull(TrialData.getIntGenkaShisan())});
				//���@No-1
				arySetTag.add(new String[]{"no_seiho1",checkNull(TrialData.getStrSeihoNo1())});
				//���@No-2
				arySetTag.add(new String[]{"no_seiho2",checkNull(TrialData.getStrSeihoNo2())});
				//���@No-3
				arySetTag.add(new String[]{"no_seiho3",checkNull(TrialData.getStrSeihoNo3())});
				//���@No-4
				arySetTag.add(new String[]{"no_seiho4",checkNull(TrialData.getStrSeihoNo4())});
				//���@No-5
				arySetTag.add(new String[]{"no_seiho5",checkNull(TrialData.getStrSeihoNo5())});
				//���_
				arySetTag.add(new String[]{"ritu_sousan",checkNull(TrialData.getDciSosan())});
				//���_-�o��Flg
				arySetTag.add(new String[]{"flg_sousan",checkNull(TrialData.getIntSosanFlg())});
				//�H��
				arySetTag.add(new String[]{"ritu_shokuen",checkNull(TrialData.getDciShokuen())});
				//�H��-�o��Flg
				arySetTag.add(new String[]{"flg_shokuen",checkNull(TrialData.getIntShokuenFlg())});
// ADD start 20121002 QP@20505 No.24
				//�l�r�f
				arySetTag.add(new String[]{"ritu_msg",checkNull(TrialData.getDciMsg())});
// ADD end 20121002 QP@20505 No.24
				//�������_�x
				arySetTag.add(new String[]{"sando_suiso",checkNull(TrialData.getDciSuiSando())});
				//�������_�x-�o��Flg
				arySetTag.add(new String[]{"flg_sando_suiso",checkNull(TrialData.getIntSuiSandoFlg())});
				//�������H��
				arySetTag.add(new String[]{"shokuen_suiso",checkNull(TrialData.getDciSuiShokuen())});
				//�������H��-�o��Flg
				arySetTag.add(new String[]{"flg_shokuen_suiso",checkNull(TrialData.getIntSuiShokuenFlg())});
				//�������|�_
				arySetTag.add(new String[]{"sakusan_suiso",checkNull(TrialData.getDciSuiSakusan())});
				//�������|�_-�o��Flg
				arySetTag.add(new String[]{"flg_sakusan_suiso",checkNull(TrialData.getIntSuiSandoFlg())});
				//���x
				arySetTag.add(new String[]{"toudo",checkNull(TrialData.getStrToudo())});
				//���x-�o��Flg
				arySetTag.add(new String[]{"flg_toudo",checkNull(TrialData.getIntToudoFlg())});
				//�S�x
				arySetTag.add(new String[]{"nendo",checkNull(TrialData.getStrNendo())});
				//�S�x-�o��Flg
				arySetTag.add(new String[]{"flg_nendo",checkNull(TrialData.getIntNendoFlg())});
				//���x
				arySetTag.add(new String[]{"ondo",checkNull(TrialData.getStrOndo())});
				//���x-�o��Flg
				arySetTag.add(new String[]{"flg_ondo",checkNull(TrialData.getIntOndoFlg())});
				//PH
				arySetTag.add(new String[]{"ph",checkNull(TrialData.getStrPh())});
				//PH - �o��Flg
				arySetTag.add(new String[]{"flg_ph",checkNull(TrialData.getIntPhFlg())});
				//���_�F����
				arySetTag.add(new String[]{"ritu_sousan_bunseki",checkNull(TrialData.getStrSosanBunseki())});
				//���_�F����-�o��Flg
				arySetTag.add(new String[]{"flg_sousan_bunseki",checkNull(TrialData.getIntSosanBunsekiFlg())});
				//�H���F����
				arySetTag.add(new String[]{"ritu_shokuen_bunseki",checkNull(TrialData.getStrShokuenBunseki())});
				//�H���F����-�o��Flg
				arySetTag.add(new String[]{"flg_shokuen_bunseki",checkNull(TrialData.getIntShokuenBunsekiFlg())});
				//��d
				arySetTag.add(new String[]{"hiju",checkNull(TrialData.getStrHizyu())});
				//��d-�o��Flg
				arySetTag.add(new String[]{"flg_hiju",checkNull(TrialData.getIntHizyuFlg())});
				//��������
				arySetTag.add(new String[]{"suibun_kasei",checkNull(TrialData.getStrSuibun())});
				//��������-�o��Flg
				arySetTag.add(new String[]{"flg_suibun_kasei",checkNull(TrialData.getIntSuibunFlg())});
				//�A���R�[��
				arySetTag.add(new String[]{"alcohol",checkNull(TrialData.getStrArukoru())});
				//�A���R�[��-�o��Flg
				arySetTag.add(new String[]{"flg_alcohol",checkNull(TrialData.getIntArukoruFlg())});
				//�쐬����
				arySetTag.add(new String[]{"memo_sakusei",checkNull(TrialData.getStrSakuseiMemo())});
				//�쐬����-�o��Flg
				arySetTag.add(new String[]{"flg_memo",checkNull(TrialData.getIntSakuseiMemoFlg())});
				//�]��
				arySetTag.add(new String[]{"hyoka",checkNull(TrialData.getStrHyoka())});
				//�]��-�o��Flg
				arySetTag.add(new String[]{"flg_hyoka",checkNull(TrialData.getIntHyokaFlg())});
				//�t���[�@�^�C�g��
				arySetTag.add(new String[]{"free_title1",checkNull(TrialData.getStrFreeTitle1())});
				//�t���[�@���e
				arySetTag.add(new String[]{"free_value1",checkNull(TrialData.getStrFreeNaiyo1())});
				//�t���[�@-�o��Flg
				arySetTag.add(new String[]{"flg_free1",checkNull(TrialData.getIntFreeFlg())});
				//�t���[�A�^�C�g��
				arySetTag.add(new String[]{"free_title2",checkNull(TrialData.getStrFreeTitle2())});
				//�t���[�A���e
				arySetTag.add(new String[]{"free_value2",checkNull(TrialData.getStrFreeNaiyo2())});
				//�t���[�A-�o��Flg
				arySetTag.add(new String[]{"flg_free2",checkNull(TrialData.getIntFreeFl2())});
				//�t���[�B�^�C�g��
				arySetTag.add(new String[]{"free_title3",checkNull(TrialData.getStrFreeTitle3())});
				//�t���[�B���e
				arySetTag.add(new String[]{"free_value3",checkNull(TrialData.getStrFreeNaiyo3())});
				//�t���[�B-�o��Flg
				arySetTag.add(new String[]{"flg_free3",checkNull(TrialData.getIntFreeFl3())});
				//������t
				arySetTag.add(new String[]{"dt_shisaku",checkNull(TrialData.getStrShisakuHi())});
				//�d��d��
				arySetTag.add(new String[]{"juryo_shiagari_g",checkNull(TrialData.getDciShiagari())});
				//�o�^��ID
				arySetTag.add(new String[]{"id_toroku",checkNull(DataCtrl.getInstance().getUserMstData().getDciUserid())});
				//�o�^���t
				arySetTag.add(new String[]{"dt_toroku",DataCtrl.getInstance().getTrialTblData().getSysDate()});
				//�X�V��ID
				arySetTag.add(new String[]{"id_koshin",checkNull(DataCtrl.getInstance().getUserMstData().getDciUserid())});
				//�X�V���t
				arySetTag.add(new String[]{"dt_koshin",DataCtrl.getInstance().getTrialTblData().getSysDate()});
				//�����˗��t���O
				arySetTag.add(new String[]{"flg_shisanIrai",Integer.toString(TrialData.getFlg_shisanIrai())});
//add start -------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.7
				//�v�Z��
				arySetTag.add(new String[]{"siki_keisan",checkNull(TrialData.getStrKeisanSiki())});
//add end   -------------------------------------------------------------------------------
//2011/04/12 QP@10181_No.67 TT Nishigawa Change Start -------------------------
				//�L�����Z��FG
				arySetTag.add(new String[]{"flg_cancel",checkNull(TrialData.getFlg_cancel())});
//2011/04/12 QP@10181_No.67 TT Nishigawa Change Start -------------------------
//2011/05/11 QP@10181_No.42_49_72 TT Nishigawa Add Start -------------------------
				//������d
				arySetTag.add(new String[]{"hiju_sui",checkNull(TrialData.getStrHiju_sui())});
				//������d-�o��Flg
				arySetTag.add(new String[]{"flg_hiju_sui",checkNull(TrialData.getIntHiju_sui_fg())});
//2011/05/11 QP@10181_No.42_49_72 TT Nishigawa Add End --------------------------
// ADD start 20121003 QP@20505 No.24
				// --------------- �H���p�^�[�� ���̑��E���H -----------------
				//�t���[�������� �^�C�g��
				arySetTag.add(new String[]{"freetitle_suibun_kasei",checkNull(TrialData.getStrFreeTitleSuibunKasei())});
				//�t���[�������� ���e
				arySetTag.add(new String[]{"free_suibun_kasei",checkNull(TrialData.getStrFreeSuibunKasei())});
				//�t���[�������� - �o��Flg
				arySetTag.add(new String[]{"flg_freeSuibunKasei",checkNull(TrialData.getIntFreeSuibunKaseiFlg())});
				//�t���[�A���R�[�� �^�C�g��
				arySetTag.add(new String[]{"freetitle_alcohol",checkNull(TrialData.getStrFreeTitleAlchol())});
				//�t���[�A���R�[�� ���e
				arySetTag.add(new String[]{"free_alcohol",checkNull(TrialData.getStrFreeAlchol())});
				//�t���[�A���R�[�� - �o��Flg
				arySetTag.add(new String[]{"flg_freeAlchol",checkNull(TrialData.getIntFreeAlcholFlg())});
				// --------------- �H���p�^�[�� 1�t�E2�t -----------------
				//�����|�_�Z�x
				arySetTag.add(new String[]{"jikkoSakusanNodo",checkNull(TrialData.getDciJikkoSakusanNodo())});
				//�����|�_�Z�x - �o��flg
				arySetTag.add(new String[]{"flg_jikkoSakusanNodo",checkNull(TrialData.getIntJikkoSakusanNodoFlg())});
				//�������l�r�f
				arySetTag.add(new String[]{"msg_suiso",checkNull(TrialData.getDciSuisoMSG())});
				//�������l�r�f - �o��flg
				arySetTag.add(new String[]{"flg_msg_suiso",checkNull(TrialData.getIntSuisoMSGFlg())});
				//�t���[�S�x �^�C�g��
				arySetTag.add(new String[]{"freetitle_nendo",checkNull(TrialData.getStrFreeTitleNendo())});
				//�t���[�S�x ���e
				arySetTag.add(new String[]{"free_nendo",checkNull(TrialData.getStrFreeNendo())});
				//�t���[�S�x - �o��Flg
				arySetTag.add(new String[]{"flg_freeNendo",checkNull(TrialData.getIntFreeNendoFlg())});
				//�t���[���x �^�C�g��
				arySetTag.add(new String[]{"freetitle_ondo",checkNull(TrialData.getStrFreeTitleOndo())});
				//�t���[���x ���e
				arySetTag.add(new String[]{"free_ondo",checkNull(TrialData.getStrFreeOndo())});
				//�t���[���x - �o��Flg
				arySetTag.add(new String[]{"flg_freeOndo",checkNull(TrialData.getIntFreeOndoFlg())});
				//�t���[�C�^�C�g��
				arySetTag.add(new String[]{"free_title4",checkNull(TrialData.getStrFreeTitle4())});
				//�t���[�C���e
				arySetTag.add(new String[]{"free_value4",checkNull(TrialData.getStrFreeNaiyo4())});
				//�t���[�C-�o��Flg
				arySetTag.add(new String[]{"flg_free4",checkNull(TrialData.getIntFreeFlg4())});
				//�t���[�D�^�C�g��
				arySetTag.add(new String[]{"free_title5",checkNull(TrialData.getStrFreeTitle5())});
				//�t���[�D���e
				arySetTag.add(new String[]{"free_value5",checkNull(TrialData.getStrFreeNaiyo5())});
				//�t���[�D-�o��Flg
				arySetTag.add(new String[]{"flg_free5",checkNull(TrialData.getIntFreeFlg5())});
				//�t���[�E�^�C�g��
				arySetTag.add(new String[]{"free_title6",checkNull(TrialData.getStrFreeTitle6())});
				//�t���[�E���e
				arySetTag.add(new String[]{"free_value6",checkNull(TrialData.getStrFreeNaiyo6())});
				//�t���[�E-�o��Flg
				arySetTag.add(new String[]{"flg_free6",checkNull(TrialData.getIntFreeFlg6())});
// ADD end 20121003 QP@20505 No.24

				//XML�փ��R�[�h�ǉ�
				xmlJW030.AddXmlTag("tr_shisaku", "rec", arySetTag);
				//�z�񏉊���
				arySetTag.clear();
			}

			//------------------- T132 ���샊�X�g�e�[�u��(tr_shisaku_list) --------------------
			xmlJW030.AddXmlTag("SA490", "tr_shisaku_list");

			//-------------------------------- ���R�[�h�ǉ�  ---------------------------------
			//�z���f�[�^�擾
			ArrayList addList = DataCtrl.getInstance().getTrialTblData().getAryShisakuList();
			for(int i=0; i<addList.size(); i++){
				PrototypeListData PrototypeListData = (PrototypeListData)addList.get(i);
				//����CD-�Ј�CD
				arySetTag.add(new String[]{"cd_shain",strUser});
				//����CD-�N
				arySetTag.add(new String[]{"nen",nen});
				//����CD-�ǔ�
				arySetTag.add(new String[]{"no_oi",checkNull(PrototypeListData.getDciShisakuNum())});
				//����SEQ
				arySetTag.add(new String[]{"seq_shisaku",checkNull(PrototypeListData.getIntShisakuSeq())});
				//�H��CD
				arySetTag.add(new String[]{"cd_kotei",checkNull(PrototypeListData.getIntKoteiCd())});
				//�H��SEQ
				arySetTag.add(new String[]{"seq_kotei",checkNull(PrototypeListData.getIntKoteiSeq())});
				//��
				arySetTag.add(new String[]{"quantity",checkNull(PrototypeListData.getDciRyo())});
				//�F
				arySetTag.add(new String[]{"color",checkNull(PrototypeListData.getStrIro())});
				//�o�^��ID
				arySetTag.add(new String[]{"id_toroku",checkNull(DataCtrl.getInstance().getUserMstData().getDciUserid())});
				//�o�^���t
				arySetTag.add(new String[]{"dt_toroku",DataCtrl.getInstance().getTrialTblData().getSysDate()});
				//�X�V��ID
				arySetTag.add(new String[]{"id_koshin",checkNull(DataCtrl.getInstance().getUserMstData().getDciUserid())});
				//�X�V���t
				arySetTag.add(new String[]{"dt_koshin",DataCtrl.getInstance().getTrialTblData().getSysDate()});
// ADD start 20121009 QP@20505 No.24
				//�H���d��d��
				arySetTag.add(new String[]{"juryo_shiagari_seq",checkNull(PrototypeListData.getDciKouteiShiagari())});
// ADD end 20121009 QP@20505 No.24
				//XML�փ��R�[�h�ǉ�
				xmlJW030.AddXmlTag("tr_shisaku_list", "rec", arySetTag);
				//�z�񏉊���
				arySetTag.clear();
			}

			//---------------------- T133 �����H���e�[�u��(tr_cyuui) ------------------------
			xmlJW030.AddXmlTag("SA490", "tr_cyuui");

			//-------------------------------- ���R�[�h�ǉ�  ---------------------------------
			//�z���f�[�^�擾
			ArrayList addCyuui = DataCtrl.getInstance().getTrialTblData().SearchSeizoKouteiData(0);
			for(int i=0; i<addCyuui.size(); i++){
				ManufacturingData ManufacturingData = (ManufacturingData)addCyuui.get(i);
				//����CD-�Ј�CD
				arySetTag.add(new String[]{"cd_shain",strUser});
				//����CD-�N
				arySetTag.add(new String[]{"nen",nen});
				//����CD-�ǔ�
				arySetTag.add(new String[]{"no_oi",checkNull(ManufacturingData.getDciShisakuNum())});
				//���ӎ���NO
				arySetTag.add(new String[]{"no_chui",checkNull(ManufacturingData.getIntTyuiNo())});
				//���ӎ���
				arySetTag.add(new String[]{"chuijiko",checkNull(ManufacturingData.getStrTyuiNaiyo())});
				//�o�^��ID
				arySetTag.add(new String[]{"id_toroku",checkNull(DataCtrl.getInstance().getUserMstData().getDciUserid())});
				//�o�^���t
				arySetTag.add(new String[]{"dt_toroku",DataCtrl.getInstance().getTrialTblData().getSysDate()});
				//�X�V��ID
				arySetTag.add(new String[]{"id_koshin",checkNull(DataCtrl.getInstance().getUserMstData().getDciUserid())});
				//�X�V���t
				arySetTag.add(new String[]{"dt_koshin",DataCtrl.getInstance().getTrialTblData().getSysDate()});

				//XML�փ��R�[�h�ǉ�
				xmlJW030.AddXmlTag("tr_cyuui", "rec", arySetTag);
				//�z�񏉊���
				arySetTag.clear();
			}

			//---------------------- T141 ���������e�[�u��(tr_genryo) ------------------------
			xmlJW030.AddXmlTag("SA490", "tr_genryo");

			//-------------------------------- ���R�[�h�ǉ�  ---------------------------------
			//���������f�[�^�擾
			ArrayList addGenka = DataCtrl.getInstance().getTrialTblData().SearchGenkaGenryoData(0);
			for(int i=0; i<addGenka.size(); i++){
				CostMaterialData costMaterialData = (CostMaterialData)addGenka.get(i);

				//�@����CD-�Ј�CD
				arySetTag.add(new String[]{"cd_shain",strUser});
				//�@����CD-�N
				arySetTag.add(new String[]{"nen",nen});
				//�@����CD-�ǔ�
				arySetTag.add(new String[]{"no_oi",checkNull(costMaterialData.getDciShisakuNum())});
				//�@����SEQ
				arySetTag.add(new String[]{"seq_shisaku",checkNull(costMaterialData.getIntShisakuSeq())});
				//�@���flg
				arySetTag.add(new String[]{"flg_print",checkNull(costMaterialData.getIntinsatu())});
				//�@�d�_�ʐ���
				arySetTag.add(new String[]{"zyusui",checkNull(costMaterialData.getStrZyutenSui())});
				//�@�d�_�ʖ���
				arySetTag.add(new String[]{"zyuabura",checkNull(costMaterialData.getStrZyutenYu())});
				//�@���v��
				arySetTag.add(new String[]{"gokei",checkNull(costMaterialData.getStrGokei())});
				//�@������
				arySetTag.add(new String[]{"genryohi",checkNull(costMaterialData.getStrGenryohi())});
				//�@������i1�{�j
				arySetTag.add(new String[]{"genryohi1",checkNull(costMaterialData.getStrGenryohiTan())});
				//�@��d
				arySetTag.add(new String[]{"hiju",checkNull(costMaterialData.getStrHizyu())});
				//�@�e��
				arySetTag.add(new String[]{"yoryo",checkNull(costMaterialData.getStrYoryo())});
				//�@����
				arySetTag.add(new String[]{"irisu",checkNull(costMaterialData.getStrIrisu())});
				//�@�L������
				arySetTag.add(new String[]{"yukobudomari",checkNull(costMaterialData.getStrYukoBudomari())});
				//�@���x����
				arySetTag.add(new String[]{"reberu",checkNull(costMaterialData.getStrLevel())});
				//�@��d����
				arySetTag.add(new String[]{"hizyubudomari",checkNull(costMaterialData.getStrHizyuBudomari())});
				//�@���Ϗ[�U��
				arySetTag.add(new String[]{"heikinzyu",checkNull(costMaterialData.getStrZyutenAve())});
				//�@1C/S������
				arySetTag.add(new String[]{"cs_genryo",checkNull(costMaterialData.getStrGenryohiCs())});
				//�@1C/S�ޗ���
				arySetTag.add(new String[]{"cs_zairyohi",checkNull(costMaterialData.getStrZairyohiCs())});
				//�@1C/S�o��
				arySetTag.add(new String[]{"cs_keihi",checkNull(costMaterialData.getStrKeihiCs())});
				//�@1C/S�����v
				arySetTag.add(new String[]{"cs_genka",checkNull(costMaterialData.getStrGenkakeiCs())});
				//�@1�����v
				arySetTag.add(new String[]{"ko_genka",checkNull(costMaterialData.getStrGenkakeiTan())});
				//�@1����
				arySetTag.add(new String[]{"ko_baika",checkNull(costMaterialData.getStrGenkakeiBai())});
				//�@1�e����
				arySetTag.add(new String[]{"ko_riritu",checkNull(costMaterialData.getStrGenkakeiRi())});
//				//�@�o�^��ID
//				arySetTag.add(new String[]{"id_toroku",checkNull(costMaterialData.getDciTorokuId())});
//				//�@�o�^���t
//				arySetTag.add(new String[]{"dt_toroku",checkNull(costMaterialData.getStrTorokuHi())});
//				//�@�X�V��ID
//				arySetTag.add(new String[]{"id_koshin",checkNull(costMaterialData.getDciKosinId())});
//				//�@�X�V���t
//				arySetTag.add(new String[]{"dt_koshin",checkNull(costMaterialData.getStrKosinHi())});
				//�o�^��ID
				arySetTag.add(new String[]{"id_toroku",checkNull(DataCtrl.getInstance().getUserMstData().getDciUserid())});
				//�o�^���t
				arySetTag.add(new String[]{"dt_toroku",DataCtrl.getInstance().getTrialTblData().getSysDate()});
				//�X�V��ID
				arySetTag.add(new String[]{"id_koshin",checkNull(DataCtrl.getInstance().getUserMstData().getDciUserid())});
				//�X�V���t
				arySetTag.add(new String[]{"dt_koshin",DataCtrl.getInstance().getTrialTblData().getSysDate()});

				//XML�փ��R�[�h�ǉ�
				xmlJW030.AddXmlTag("tr_genryo", "rec", arySetTag);
				//�z�񏉊���
				arySetTag.clear();

			}


			//2009/09/30 TT.NISHIGAWA DEL START [�r������͓o�^���W�b�N���ɂĎ��s]
			//--------------------------- �@�\ID�ǉ��i�r������j  -----------------------------
//			xmlJW030.AddXmlTag("JW030", "SA420");
//			//�@�e�[�u���^�O�ǉ�
//			xmlJW030.AddXmlTag("SA420", "table");
//			//�@���R�[�h�ǉ�
//			arySetTag.add(new String[]{"kubun_ziko", "1"});
//			arySetTag.add(new String[]{"kubun_haita", "1"});
//			arySetTag.add(new String[]{"id_user", strUser});
//			arySetTag.add(new String[]{"cd_shain", strShisaku_user});
//			arySetTag.add(new String[]{"nen", strShisaku_nen});
//			arySetTag.add(new String[]{"no_oi", strShisaku_oi});
//			xmlJW030.AddXmlTag("table", "rec", arySetTag);
//			//�z�񏉊���
//			arySetTag.clear();
			//2009/09/30 TT.NISHIGAWA DEL END   [�r������͓o�^���W�b�N���ɂĎ��s]

			//�@XML���M
			//System.out.println("JW030���MXML===============================================================");
			//xmlJW030.dispXml();

			xcon = new XmlConnection(xmlJW030);
			xcon.setStrAddress(DataCtrl.getInstance().getJnlpConnect().getStrAddressAjax());
			xcon.XmlSend();
			//�@XML��M
			xmlJW030 = xcon.getXdocRes();

//			System.out.println();
//			System.out.println("JW030��MXML===============================================================");
//			xmlJW030.dispXml();
//			System.out.println();

			//Result�`�F�b�N
			DataCtrl.getInstance().getResultData().setResultData(xmlJW030);



			if (DataCtrl.getInstance().getResultData().getStrReturnFlg().equals("true")) {
				//�@�\ID�̐ݒ�
				String strKinoId = "SA490";

				//�S�̔z��擾
				ArrayList userData = xmlJW030.GetAryTag(strKinoId);

				//�@�\�z��擾
				ArrayList kinoData = (ArrayList)userData.get(0);

				//�e�[�u���z��擾
				ArrayList tableData = (ArrayList)kinoData.get(1);

				//���R�[�h�擾
				for(int i=1; i<tableData.size(); i++){
					//�@�P���R�[�h�擾
					ArrayList recData = ((ArrayList)((ArrayList)tableData.get(i)).get(0));
					for(int j=0; j<recData.size(); j++){
						//�@���ږ��擾
						String recNm = ((String[])recData.get(j))[1];
						//�@���ڒl�擾
						String recVal = ((String[])recData.get(j))[2];
						//�@����R�[�h
						if ( recNm == "new_code" ) {
							//�p�����[�^�f�[�^�֊i�[
							DataCtrl.getInstance().getParamData().setStrSisaku(recVal);

							//�e��e�[�u���f�[�^�֊i�[
							String shisakuCd = recVal.split("-")[0];
							String shisakuNen = recVal.split("-")[1];
							String shisakuOi = recVal.split("-")[2];
							this.setTableData(shisakuCd, shisakuNen, shisakuOi);

						}
					}
				}
			}






			if ( intChkMsg == 0 ) {
				//�o�^�{�^��������

				if (DataCtrl.getInstance().getResultData().getStrReturnFlg().equals("true")) {
					//���[�h�̕ύX
					DataCtrl.getInstance().getParamData().setStrMode(JwsConstManager.JWS_MODE_0001);
					DataCtrl.getInstance().getMessageCtrl().PrintMessageString("����Ɏ���o�^�������������܂����B");
				}else{
					ExceptionBase ExceptionBase  = new ExceptionBase();
					throw ExceptionBase;
				}

			} else {
				//�����ۑ�������

				if (DataCtrl.getInstance().getResultData().getStrReturnFlg().equals("true")) {
					//���[�h�̕ύX
					DataCtrl.getInstance().getParamData().setStrMode(JwsConstManager.JWS_MODE_0001);

				} else {

					//�G���[������

					//�G���[���b�Z�[�W�𒠕[�p�ɕύX
					String strErrMsg = new String(DataCtrl.getInstance().getResultData().getStrErrorMsg());

					//����\
					if ( intChkMsg == 1 ) {
						strErrMsg = strErrMsg.replaceFirst("�o�^", "����\ �����ۑ�");

					//�T���v��������
					} else if ( intChkMsg == 2 ) {
						strErrMsg = strErrMsg.replaceFirst("�o�^", "�T���v�������� �����ۑ�");

					//�h�{�v�Z��
					} else if ( intChkMsg == 3 ) {
						strErrMsg = strErrMsg.replaceFirst("�o�^", "�h�{�v�Z�� �����ۑ�");

					//�������Z�\
					} else if ( intChkMsg == 4 ) {
						strErrMsg = strErrMsg.replaceFirst("�o�^", "�������Z�\ �����ۑ�");

					}

					//Result�̃G���[���b�Z�[�W��ύX
					DataCtrl.getInstance().getResultData().setStrErrorMsg(strErrMsg);

					//Exception��throw����
					ExceptionBase ExceptionBase  = new ExceptionBase();
					throw ExceptionBase;

				}

			}

			//�������Z��ʁ@���Z�m��T���v��No�R���{�{�b�N�X �X�V
			tb.getTrial5Panel().updShisanSampleNo();

			//�������ZFG�ҏW�s�ݒ�
			DataCtrl.getInstance().getTrialTblData().setShisakuRetuFlg_initCtrl();
			setGenkaIrai_false();

//add start -------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.33,34
			//�o�^��̕ҏW�t���O�ݒ�{�ĕ\��
			dispHenshuOkFg();
//add end   -------------------------------------------------------------------------------


		}catch(ExceptionBase eb){
			throw eb;

		}catch(Exception e){

			e.printStackTrace();

			//�G���[�ݒ�
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			ex.setStrErrmsg("�V�K�o�^���������s���܂���");
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getMessage());
			throw ex;

		}finally{

		}
	}


	/************************************************************************************
	 *
	 *   ����R�[�h�����̔ԁ@XML�ʐM�����iJ010�j
	 *    :  ����R�[�h�����̔ԏ���XML�f�[�^�ʐM�iJ010�j���s��
	 *   @author TT nishigawa
	 *
	 ************************************************************************************/
	private void conJ010() throws ExceptionBase{
		try{
			//--------------------------- ���M�p�����[�^�i�[  ---------------------------------
			String strUser = DataCtrl.getInstance().getParamData().getDciUser().toString();
			String strShisaku_user = DataCtrl.getInstance().getParamData().getStrSisaku_user();
			String strShisaku_nen = DataCtrl.getInstance().getParamData().getStrSisaku_nen();
			String strShisaku_oi = DataCtrl.getInstance().getParamData().getStrSisaku_oi();
			String strGamenId = DataCtrl.getInstance().getParamData().getStrMode();

			//--------------------------- ���MXML�f�[�^�쐬  ---------------------------------
			xmlJ010 = new XmlData();
			ArrayList arySetTag = new ArrayList();

			//------------------------------- Root�ǉ�  ------------------------------------
			xmlJ010.AddXmlTag("","J010");
			arySetTag.clear();

			//------------------------- �@�\ID�ǉ��iUSERINFO�j  ------------------------------
			xmlJ010.AddXmlTag("J010", "USERINFO");

			//----------------------------- �e�[�u���^�O�ǉ�  ---------------------------------
			xmlJ010.AddXmlTag("USERINFO", "table");

			//------------------------------ ���R�[�h�ǉ�  -----------------------------------
			//�����敪
			arySetTag.add(new String[]{"kbn_shori", "3"});
			//���[�UID
			arySetTag.add(new String[]{"id_user",strUser});
			//XML�փ��R�[�h�ǉ�
			xmlJ010.AddXmlTag("table", "rec", arySetTag);
			//�z�񏉊���
			arySetTag.clear();

			//--------------------------- �@�\ID�ǉ��i�����̔ԁj  -----------------------------
			xmlJ010.AddXmlTag("J010", "SA410");
			//�@�e�[�u���^�O�ǉ�
			xmlJ010.AddXmlTag("SA410", "table");
			//�@���R�[�h�ǉ�
			arySetTag.add(new String[]{"kbn_shori", "cd_shisaku"});
			xmlJ010.AddXmlTag("table", "rec", arySetTag);
			//�z�񏉊���
			arySetTag.clear();

			//----------------------------------- XML���M  ----------------------------------
			//System.out.println("J010���MXML===============================================================");
			//xmlJ010.dispXml();

			xcon = new XmlConnection(xmlJ010);
			xcon.setStrAddress(DataCtrl.getInstance().getJnlpConnect().getStrAddressAjax());
			xcon.XmlSend();

			//----------------------------------- XML��M  ----------------------------------
			xmlJ010 = xcon.getXdocRes();

			//System.out.println();
			//System.out.println("J010��MXML===============================================================");
			//xmlJ010.dispXml();

			//--------------------------------- ����R�[�h�擾  -------------------------------
			//Result�`�F�b�N
			DataCtrl.getInstance().getResultData().setResultData(xmlJ010);
			if (DataCtrl.getInstance().getResultData().getStrReturnFlg().equals("true")) {
				//�@�\ID�̐ݒ�
				String strKinoId = "SA410";

				//�S�̔z��擾
				ArrayList userData = xmlJ010.GetAryTag(strKinoId);

				//�@�\�z��擾
				ArrayList kinoData = (ArrayList)userData.get(0);

				//�e�[�u���z��擾
				ArrayList tableData = (ArrayList)kinoData.get(1);

				//���R�[�h�擾
				for(int i=1; i<tableData.size(); i++){
					//�@�P���R�[�h�擾
					ArrayList recData = ((ArrayList)((ArrayList)tableData.get(i)).get(0));
					for(int j=0; j<recData.size(); j++){
						//�@���ږ��擾
						String recNm = ((String[])recData.get(j))[1];
						//�@���ڒl�擾
						String recVal = ((String[])recData.get(j))[2];
						//�@����R�[�h
						if ( recNm == "new_code" ) {
							//�p�����[�^�f�[�^�֊i�[
							DataCtrl.getInstance().getParamData().setStrSisaku(recVal);

							//�e��e�[�u���f�[�^�֊i�[
							String shisakuCd = recVal.split("-")[0];
							String shisakuNen = recVal.split("-")[1];
							String shisakuOi = recVal.split("-")[2];
							this.setTableData(shisakuCd, shisakuNen, shisakuOi);

						}
					}
				}
			}else{
				ExceptionBase ExceptionBase  = new ExceptionBase();
				throw ExceptionBase;
			}
		}catch(ExceptionBase eb){
			throw eb;

		}catch(Exception e){
			e.printStackTrace();

			//�G���[�ݒ�
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			ex.setStrErrmsg("����R�[�h�̎����̔ԏ��������s���܂���");
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getMessage());
			throw ex;

		}finally{

		}
	}

	/************************************************************************************
	 *
	 *   �e�f�[�^�֎�L�[��ݒ肷��
	 *   @author TT nishigawa
	 *
	 ************************************************************************************/
	public void setTableData(String shisakuCd, String shisakuNen, String shisakuOi) throws ExceptionBase{
		try{
			/**********************************************************
			 *�@T110�i�[
			 *********************************************************/
			//����CD-�Ј�CD
			DataCtrl.getInstance().getTrialTblData().SearchShisakuhinData().setDciShisakuUser(new BigDecimal(shisakuCd));
			//����CD-�N
			DataCtrl.getInstance().getTrialTblData().SearchShisakuhinData().setDciShisakuYear(new BigDecimal(shisakuNen));
			//����CD-�ǔ�
			DataCtrl.getInstance().getTrialTblData().SearchShisakuhinData().setDciShisakuNum(new BigDecimal(shisakuOi));

			/**********************************************************
			 *�@T120�i�[
			 *********************************************************/
			ArrayList aryHaigo = DataCtrl.getInstance().getTrialTblData().SearchHaigoData(0);
			for(int i=0; i<aryHaigo.size(); i++){
				//����CD-�Ј�CD
				((MixedData)aryHaigo.get(i)).setDciShisakuUser(new BigDecimal(shisakuCd));
				//����CD-�N
				((MixedData)aryHaigo.get(i)).setDciShisakuYear(new BigDecimal(shisakuNen));
				//����CD-�ǔ�
				((MixedData)aryHaigo.get(i)).setDciShisakuNum(new BigDecimal(shisakuOi));
			}

			/**********************************************************
			 *�@T131�i�[
			 *********************************************************/
			ArrayList aryRetu = DataCtrl.getInstance().getTrialTblData().SearchShisakuRetuData(0);
			for(int i=0; i<aryRetu.size(); i++){
				//����CD-�Ј�CD
				((TrialData)aryRetu.get(i)).setDciShisakuUser(new BigDecimal(shisakuCd));
				//����CD-�N
				((TrialData)aryRetu.get(i)).setDciShisakuYear(new BigDecimal(shisakuNen));
				//����CD-�ǔ�
				((TrialData)aryRetu.get(i)).setDciShisakuNum(new BigDecimal(shisakuOi));
			}


			/**********************************************************
			 *�@T132�i�[
			 *********************************************************/
			ArrayList aryList = DataCtrl.getInstance().getTrialTblData().getAryShisakuList();
			for(int i=0; i<aryList.size(); i++){
				//����CD-�Ј�CD
				((PrototypeListData)aryList.get(i)).setDciShisakuUser(new BigDecimal(shisakuCd));
				//����CD-�N
				((PrototypeListData)aryList.get(i)).setDciShisakuYear(new BigDecimal(shisakuNen));
				//����CD-�ǔ�
				((PrototypeListData)aryList.get(i)).setDciShisakuNum(new BigDecimal(shisakuOi));
			}

			/**********************************************************
			 *�@T133�i�[
			 *********************************************************/
			ArrayList arySeizo = DataCtrl.getInstance().getTrialTblData().SearchSeizoKouteiData(0);
			for(int i=0; i<arySeizo.size(); i++){
				//����CD-�Ј�CD
				((ManufacturingData)arySeizo.get(i)).setDciShisakuUser(new BigDecimal(shisakuCd));
				//����CD-�N
				((ManufacturingData)arySeizo.get(i)).setDciShisakuYear(new BigDecimal(shisakuNen));
				//����CD-�ǔ�
				((ManufacturingData)arySeizo.get(i)).setDciShisakuNum(new BigDecimal(shisakuOi));
			}

			/**********************************************************
			 *�@T140�i�[
			 *********************************************************/


			/**********************************************************
			 *�@T141�i�[
			 *********************************************************/
			//���������f�[�^�擾
			ArrayList aryGenka = DataCtrl.getInstance().getTrialTblData().SearchGenkaGenryoData(0);
			for(int i=0; i<aryGenka.size(); i++){
				CostMaterialData costMaterialData = (CostMaterialData)aryGenka.get(i);

				//����CD-�Ј�CD
				costMaterialData.setDciShisakuUser(new BigDecimal(shisakuCd));
				//����CD-�N
				costMaterialData.setDciShisakuYear(new BigDecimal(shisakuNen));
				//����CD-�ǔ�
				costMaterialData.setDciShisakuNum(new BigDecimal(shisakuOi));

			}

		}catch(Exception e){
			e.printStackTrace();

			ex = new ExceptionBase();
			ex.setStrErrCd("");
			ex.setStrErrmsg("�e�f�[�^�̃L�[�ݒ菈�������s���܂���");
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getMessage());
			throw ex;

		}finally{
			//�e�X�g�\��
			//DataCtrl.getInstance().getTrialTblData().dispPrototype();

		}
	}

	/************************************************************************************
	 *
	 *   �o�^�����p�@XML�ʐM�����iJW040�j
	 *    : ��������XML�f�[�^�ʐM�iJW040�j���s��
	 *   @param intChkMsg : �o�^�����b�Z�[�W�̎w��
	 *    [0:�o�^, 1:�����ۑ�(����\), 2:�����ۑ�(�T���v��������), 3:�����ۑ�(�h�{�v�Z��)]
	 *   @author TT nishigawa
	 *
	 ************************************************************************************/
	private void conJW040(int intChkMsg) throws ExceptionBase{
		try{

			//DataCtrl.getInstance().getMessageCtrl().PrintMessageString("����Ɏ���o�^�������������܂����B");

			//--------------------------- ���M�p�����[�^�i�[  ---------------------------------
			String strUser = DataCtrl.getInstance().getParamData().getDciUser().toString();
			String strShisaku_user = DataCtrl.getInstance().getParamData().getStrSisaku_user();
			String strShisaku_nen = DataCtrl.getInstance().getParamData().getStrSisaku_nen();
			String strShisaku_oi = DataCtrl.getInstance().getParamData().getStrSisaku_oi();
			String strGamenId = DataCtrl.getInstance().getParamData().getStrMode();

			//--------------------------- ���MXML�f�[�^�쐬  ---------------------------------
			xmlJW040 = new XmlData();
			ArrayList arySetTag = new ArrayList();

			//------------------------------- Root�ǉ�  ------------------------------------
			xmlJW040.AddXmlTag("","JW040");
			arySetTag.clear();

			//------------------------- �@�\ID�ǉ��iUSERINFO�j  ------------------------------
			xmlJW040.AddXmlTag("JW040", "USERINFO");

			//----------------------------- �e�[�u���^�O�ǉ�  ---------------------------------
			xmlJW040.AddXmlTag("USERINFO", "table");

			//------------------------------ ���R�[�h�ǉ�  -----------------------------------
			//�����敪
			arySetTag.add(new String[]{"kbn_shori", "3"});
			//���[�UID
			arySetTag.add(new String[]{"id_user",strUser});
			//XML�փ��R�[�h�ǉ�
			xmlJW040.AddXmlTag("table", "rec", arySetTag);
			//�z�񏉊���
			arySetTag.clear();

			//------------------------ �@�\ID�ǉ��i������o�^�j  ---------------------------
			xmlJW040.AddXmlTag("JW040", "SA490");

			//--------------------- T110 ����i�e�[�u��(tr_shisakuhin) ----------------------
			xmlJW040.AddXmlTag("SA490", "tr_shisakuhin");

			//-------------------------------- ���R�[�h�ǉ�  ---------------------------------
			//����i�f�[�^�擾
			PrototypeData addPrototypeData = DataCtrl.getInstance().getTrialTblData().SearchShisakuhinData();
			//����CD-�Ј�CD
			arySetTag.add(new String[]{"cd_shain", checkNull(addPrototypeData.getDciShisakuUser())});
			//����CD-�N
			arySetTag.add(new String[]{"nen", checkNull(addPrototypeData.getDciShisakuYear())});
			//����CD-�ǔ�
			arySetTag.add(new String[]{"no_oi", checkNull(addPrototypeData.getDciShisakuNum())});
			//�˗��ԍ�
			arySetTag.add(new String[]{"no_irai", checkNull(addPrototypeData.getStrIrai())});
			//�i��
			arySetTag.add(new String[]{"nm_hin", checkNull(addPrototypeData.getStrHinnm())});

			//�w��H��-���CD
			if(addPrototypeData.getIntKaishacd() <= 0){ //��ЃR�[�h��0�ȉ��̏ꍇ�͋�
				arySetTag.add(new String[]{"cd_kaisha", ""});
			}else{
				arySetTag.add(new String[]{"cd_kaisha", checkNull(addPrototypeData.getIntKaishacd())});
			}
			//�w��H��-�H��CD
			if(addPrototypeData.getIntKojoco() <= 0){ //�H��R�[�h��0�ȉ��̏ꍇ�͋�
				arySetTag.add(new String[]{"cd_kojo", ""});
			}else{
				arySetTag.add(new String[]{"cd_kojo", checkNull(addPrototypeData.getIntKojoco())});
			}

			//���CD
			arySetTag.add(new String[]{"cd_shubetu", checkNull(addPrototypeData.getStrShubetu())});
			//���No
			arySetTag.add(new String[]{"no_shubetu", checkNull(addPrototypeData.getStrShubetuNo())});
			//�O���[�vCD
			arySetTag.add(new String[]{"cd_group", checkNull(addPrototypeData.getIntGroupcd())});
			//�`�[��CD
			arySetTag.add(new String[]{"cd_team", checkNull(addPrototypeData.getIntTeamcd())});
			//�ꊇ�\��CD
			arySetTag.add(new String[]{"cd_ikatu", checkNull(addPrototypeData.getStrIkatu())});
			//�W������CD
			arySetTag.add(new String[]{"cd_genre", checkNull(addPrototypeData.getStrZyanru())});
			//���[�UCD
			arySetTag.add(new String[]{"cd_user", checkNull(addPrototypeData.getStrUsercd())});
			//��������
			arySetTag.add(new String[]{"tokuchogenryo", checkNull(addPrototypeData.getStrTokutyo())});
			//�p�r
			arySetTag.add(new String[]{"youto", checkNull(addPrototypeData.getStrYoto())});
			//���i��CD
			arySetTag.add(new String[]{"cd_kakaku", checkNull(addPrototypeData.getStrKakaku())});
			//�S���c��CD
			arySetTag.add(new String[]{"cd_eigyo", checkNull(addPrototypeData.getStrTantoEigyo())});
			//�������@CD
			arySetTag.add(new String[]{"cd_hoho", checkNull(addPrototypeData.getStrSeizocd())});
			//�[�U���@CD
			arySetTag.add(new String[]{"cd_juten", checkNull(addPrototypeData.getStrZyutencd())});
			//�E�ە��@
			arySetTag.add(new String[]{"hoho_sakin", checkNull(addPrototypeData.getStrSakin())});
			//�e��E���
			arySetTag.add(new String[]{"youki", checkNull(addPrototypeData.getStrYokihozai())});
			//�e��
			//2011/04/13 QP@10181_No.26 TT T.Satoh Change Start -------------------------
			//seisuCheck(checkNull(addPrototypeData.getStrYoryo()), "����f�[�^��� ����\�B �e��", 1);
			seisuCheck(checkNull(addPrototypeData.getStrYoryo()), "����f�[�^��� ��{��� �e��", 1);
			//2011/04/13 QP@10181_No.26 TT T.Satoh Change End ---------------------------
			arySetTag.add(new String[]{"yoryo", checkNull(addPrototypeData.getStrYoryo())});

			//�e�ʒP��CD
			arySetTag.add(new String[]{"cd_tani", checkNull(addPrototypeData.getStrTani())});

			//���萔
			//2011/04/13 QP@10181_No.26 TT T.Satoh Change Start -------------------------
			//seisuCheck(checkNull(addPrototypeData.getStrIrisu()), "����f�[�^��� ����\�B ���萔", 1);
			seisuCheck(checkNull(addPrototypeData.getStrIrisu()), "����f�[�^��� ��{��� ���萔", 1);
			//2011/04/13 QP@10181_No.26 TT T.Satoh Change End ---------------------------
			arySetTag.add(new String[]{"su_iri", checkNull(addPrototypeData.getStrIrisu())});

			//�戵���xCD
			arySetTag.add(new String[]{"cd_ondo", checkNull(addPrototypeData.getStrOndo())});
			//�ܖ�����
			arySetTag.add(new String[]{"shomikikan", checkNull(addPrototypeData.getStrShomi())});

			//����
			//2011/04/13 QP@10181_No.26 TT T.Satoh Change Start -------------------------
			//ShosuCheck(checkNull(addPrototypeData.getStrGenka()), 2, "����f�[�^��� ����\�B ������]", 1);
			ShosuCheck(checkNull(addPrototypeData.getStrGenka()), 2, "����f�[�^��� ��{��� ������]", 1);
			//2011/04/13 QP@10181_No.26 TT T.Satoh Change End ---------------------------
			arySetTag.add(new String[]{"genka", checkNull(addPrototypeData.getStrGenka())});

			//����
			//2011/04/13 QP@10181_No.26 TT T.Satoh Change Start -------------------------
			//ShosuCheck(checkNull(addPrototypeData.getStrBaika()), 2, "����f�[�^��� ����\�B ������]", 1);
			ShosuCheck(checkNull(addPrototypeData.getStrBaika()), 2, "����f�[�^��� ��{��� ������]", 1);
			//2011/04/13 QP@10181_No.26 TT T.Satoh Change End ---------------------------
			arySetTag.add(new String[]{"baika", checkNull(addPrototypeData.getStrBaika())});

			//�z�蕨��
			arySetTag.add(new String[]{"buturyo", checkNull(addPrototypeData.getStrSotei())});
			//��������
			arySetTag.add(new String[]{"dt_hatubai", checkNull(addPrototypeData.getStrHatubai())});
			//�v�攄��
			arySetTag.add(new String[]{"uriage_k", checkNull(addPrototypeData.getStrKeikakuUri())});
			//�v�旘�v
			arySetTag.add(new String[]{"rieki_k", checkNull(addPrototypeData.getStrKeikakuRie())});
			//�̔��㔄��
			arySetTag.add(new String[]{"uriage_h", checkNull(addPrototypeData.getStrHanbaigoUri())});
			//�̔��㗘�v
			arySetTag.add(new String[]{"rieki_h", checkNull(addPrototypeData.getStrHanbaigoRie())});
			//�׎pCD
			arySetTag.add(new String[]{"cd_nisugata", checkNull(addPrototypeData.getStrNishugata())});
			//������
			arySetTag.add(new String[]{"memo", checkNull(addPrototypeData.getStrSogo())});
			//�����w��
			arySetTag.add(new String[]{"keta_shosu", checkNull(addPrototypeData.getStrShosu())});

			//�����w��l�P
			String cd = addPrototypeData.getStrShosu();
			int val1 = 0;
			if(cd != null && cd.length()>0){
				val1 = DataCtrl.getInstance().getLiteralDataShosu().selectLiteralVal1(Integer.parseInt(cd));
			}
			arySetTag.add(new String[]{"keta_shosu_val1", Integer.toString(val1)});


			//�p�~��
			arySetTag.add(new String[]{"kbn_haishi", checkNull(addPrototypeData.getIntHaisi())});
			//�r��
			arySetTag.add(new String[]{"kbn_haita", checkNull(addPrototypeData.getDciHaita())});
			//���@����
			arySetTag.add(new String[]{"seq_shisaku", checkNull(addPrototypeData.getIntSeihoShisaku())});
			//���상��
			arySetTag.add(new String[]{"memo_shisaku", checkNull(addPrototypeData.getStrShisakuMemo())});
			//���ӎ����\��
			arySetTag.add(new String[]{"flg_chui", checkNull(addPrototypeData.getIntChuiFg())});
			//�o�^��ID
			arySetTag.add(new String[]{"id_toroku", checkNull(addPrototypeData.getDciTorokuid())});
			//�o�^���t
			arySetTag.add(new String[]{"dt_toroku", checkNull(addPrototypeData.getStrTorokuhi())});
			//�X�V��ID
			arySetTag.add(new String[]{"id_koshin", checkNull(DataCtrl.getInstance().getUserMstData().getDciUserid())});

			//�X�V���t
			//UPD 2012.4.30�@�yH24�N�x�Ή��z�X�V���Ŏ���i�̍X�V�r��������s�����ߍX�V
//			arySetTag.add(new String[]{"dt_koshin", DataCtrl.getInstance().getTrialTblData().getSysDate()});
			arySetTag.add(new String[]{"dt_koshin", checkNull(addPrototypeData.getStrKosinhi())});


//2011/05/11 QP@10181_No.42_49_72 TT Nishigawa Add Start -------------------------
			//�H���p�^�[��
			arySetTag.add(new String[]{"pt_kotei", checkNull(addPrototypeData.getStrPt_kotei())});
//2011/05/11 QP@10181_No.42_49_72 TT Nishigawa Add End --------------------------

			//�yQP@20505_No.38�z2012/10/17 TT H.SHIMA ADD Start
			arySetTag.add(new String[]{"flg_secret", checkNull(addPrototypeData.getStrSecret())});
			//�yQP@20505_No.38�z2012/10/17 TT H.SHIMA ADD End
//ADD 2013/06/19 ogawa �yQP@30151�zNo.9 start
			//�̐Ӊ��CD
			if(addPrototypeData.getIntHansekicd() <= 0){ //�̐Ӊ�ЃR�[�h��0�ȉ��̏ꍇ�͋�
				arySetTag.add(new String[]{"cd_hanseki", ""});
			}else{
				arySetTag.add(new String[]{"cd_hanseki", checkNull(addPrototypeData.getIntHansekicd())});
			}
//ADD 2013/06/19 ogawa �yQP@30151�zNo.9 start

			//XML�փ��R�[�h�ǉ�
			xmlJW040.AddXmlTag("tr_shisakuhin", "rec", arySetTag);
			//�z�񏉊���
			arySetTag.clear();

			//------------------------- T120 �z���e�[�u��(tr_haigo) -------------------------
			xmlJW040.AddXmlTag("SA490", "tr_haigo");

			//-------------------------------- ���R�[�h�ǉ�  ---------------------------------
			//�z���f�[�^�擾
			ArrayList addHaigo = DataCtrl.getInstance().getTrialTblData().SearchHaigoData(0);
			for(int i=0; i<addHaigo.size(); i++){
				MixedData MixedData = (MixedData)addHaigo.get(i);
				//����CD-�Ј�CD
				arySetTag.add(new String[]{"cd_shain",checkNull(MixedData.getDciShisakuUser())});
				//����CD-�N
				arySetTag.add(new String[]{"nen",checkNull(MixedData.getDciShisakuYear())});
				//����CD-�ǔ�
				arySetTag.add(new String[]{"no_oi",checkNull(MixedData.getDciShisakuNum())});
				//�H��CD
				arySetTag.add(new String[]{"cd_kotei",checkNull(MixedData.getIntKoteiCd())});
				//�H��SEQ
				arySetTag.add(new String[]{"seq_kotei",checkNull(MixedData.getIntKoteiSeq())});
				//�H����
				arySetTag.add(new String[]{"nm_kotei",checkNull(MixedData.getStrKouteiNm())});
				//�H������
				arySetTag.add(new String[]{"zoku_kotei",checkNull(MixedData.getStrKouteiZokusei())});
				//�H����
				arySetTag.add(new String[]{"sort_kotei",checkNull(MixedData.getIntKoteiNo())});
				//������
				arySetTag.add(new String[]{"sort_genryo",checkNull(MixedData.getIntGenryoNo())});
				//����CD
				arySetTag.add(new String[]{"cd_genryo",checkNull(MixedData.getStrGenryoCd())});
				//���CD
				arySetTag.add(new String[]{"cd_kaisha",checkNull(MixedData.getIntKaishaCd())});
				//����CD
				arySetTag.add(new String[]{"cd_busho",checkNull(MixedData.getIntBushoCd())});
				//��������
				arySetTag.add(new String[]{"nm_genryo",checkNull(MixedData.getStrGenryoNm())});
				//�P��
				arySetTag.add(new String[]{"tanka",checkNull(MixedData.getDciTanka())});
				//����
				arySetTag.add(new String[]{"budomari",checkNull(MixedData.getDciBudomari())});
				//���ܗL��
				arySetTag.add(new String[]{"ritu_abura",checkNull(MixedData.getDciGanyuritu())});
				//�|�_
				arySetTag.add(new String[]{"ritu_sakusan",checkNull(MixedData.getDciSakusan())});
				//�H��
				arySetTag.add(new String[]{"ritu_shokuen",checkNull(MixedData.getDciShokuen())});
// ADD start 20121002 QP@20505 No.24
				//�l�r�f
				arySetTag.add(new String[]{"ritu_msg",checkNull(MixedData.getDciMsg())});
// ADD end 20121002 QP@20505 No.24
				//���_
				arySetTag.add(new String[]{"ritu_sousan",checkNull(MixedData.getDciSosan())});
				//�F
				arySetTag.add(new String[]{"color",checkNull(MixedData.getStrIro())});
				//�o�^��ID
				arySetTag.add(new String[]{"id_toroku",checkNull(MixedData.getDciTorokuId())});
				//�o�^���t
				arySetTag.add(new String[]{"dt_toroku",checkNull(MixedData.getStrTorokuHi())});
				//�X�V��ID
				arySetTag.add(new String[]{"id_koshin",checkNull(DataCtrl.getInstance().getUserMstData().getDciUserid())});
				//�X�V���t
				arySetTag.add(new String[]{"dt_koshin",DataCtrl.getInstance().getTrialTblData().getSysDate()});

				//XML�փ��R�[�h�ǉ�
				xmlJW040.AddXmlTag("tr_haigo", "rec", arySetTag);
				//�z�񏉊���
				arySetTag.clear();
			}

			//----------------------- T131 ����e�[�u��(tr_shisaku) -------------------------
			xmlJW040.AddXmlTag("SA490", "tr_shisaku");

			//-------------------------------- ���R�[�h�ǉ�  ---------------------------------
			//�����f�[�^�擾
			ArrayList addRetu = DataCtrl.getInstance().getTrialTblData().SearchShisakuRetuData(0);
			for(int i=0; i<addRetu.size(); i++){
				TrialData TrialData = (TrialData)addRetu.get(i);
				//����CD-�Ј�CD
				arySetTag.add(new String[]{"cd_shain",checkNull(TrialData.getDciShisakuUser())});
				//����CD-�N
				arySetTag.add(new String[]{"nen",checkNull(TrialData.getDciShisakuYear())});
				//����CD-�ǔ�
				arySetTag.add(new String[]{"no_oi",checkNull(TrialData.getDciShisakuNum())});
				//����SEQ
				arySetTag.add(new String[]{"seq_shisaku",checkNull(TrialData.getIntShisakuSeq())});
				//����\����
				arySetTag.add(new String[]{"sort_shisaku",checkNull(TrialData.getIntHyojiNo())});
				//���ӎ���NO
				arySetTag.add(new String[]{"no_chui",checkNull(TrialData.getStrTyuiNo())});
				//�T���v��NO�i���́j
				arySetTag.add(new String[]{"nm_sample",checkNull(TrialData.getStrSampleNo())});
				//����
				arySetTag.add(new String[]{"memo",checkNull(TrialData.getStrMemo())});
				//���Flg
				arySetTag.add(new String[]{"flg_print",checkNull(TrialData.getIntInsatuFlg())});
				//�����v�ZFlg
				arySetTag.add(new String[]{"flg_auto",checkNull(TrialData.getIntZidoKei())});
				//�������ZNo
				arySetTag.add(new String[]{"no_shisan",checkNull(TrialData.getIntGenkaShisan())});
				//���@No-1
				arySetTag.add(new String[]{"no_seiho1",checkNull(TrialData.getStrSeihoNo1())});
				//���@No-2
				arySetTag.add(new String[]{"no_seiho2",checkNull(TrialData.getStrSeihoNo2())});
				//���@No-3
				arySetTag.add(new String[]{"no_seiho3",checkNull(TrialData.getStrSeihoNo3())});
				//���@No-4
				arySetTag.add(new String[]{"no_seiho4",checkNull(TrialData.getStrSeihoNo4())});
				//���@No-5
				arySetTag.add(new String[]{"no_seiho5",checkNull(TrialData.getStrSeihoNo5())});
				//���_
				arySetTag.add(new String[]{"ritu_sousan",checkNull(TrialData.getDciSosan())});
				//���_-�o��Flg
				arySetTag.add(new String[]{"flg_sousan",checkNull(TrialData.getIntSosanFlg())});
				//�H��
				arySetTag.add(new String[]{"ritu_shokuen",checkNull(TrialData.getDciShokuen())});
				//�H��-�o��Flg
				arySetTag.add(new String[]{"flg_shokuen",checkNull(TrialData.getIntShokuenFlg())});
// ADD start 20121002 QP@20505 No.24
				//�l�r�f
				arySetTag.add(new String[]{"ritu_msg",checkNull(TrialData.getDciMsg())});
// ADD end 20121002 QP@20505 No.24
				//�������_�x
				arySetTag.add(new String[]{"sando_suiso",checkNull(TrialData.getDciSuiSando())});
				//�������_�x-�o��Flg
				arySetTag.add(new String[]{"flg_sando_suiso",checkNull(TrialData.getIntSuiSandoFlg())});
				//�������H��
				arySetTag.add(new String[]{"shokuen_suiso",checkNull(TrialData.getDciSuiShokuen())});
				//�������H��-�o��Flg
				arySetTag.add(new String[]{"flg_shokuen_suiso",checkNull(TrialData.getIntSuiShokuenFlg())});
				//�������|�_
				arySetTag.add(new String[]{"sakusan_suiso",checkNull(TrialData.getDciSuiSakusan())});
				//�������|�_-�o��Flg
				arySetTag.add(new String[]{"flg_sakusan_suiso",checkNull(TrialData.getIntSuiSandoFlg())});
				//���x
				arySetTag.add(new String[]{"toudo",checkNull(TrialData.getStrToudo())});
				//���x-�o��Flg
				arySetTag.add(new String[]{"flg_toudo",checkNull(TrialData.getIntToudoFlg())});
				//�S�x
				arySetTag.add(new String[]{"nendo",checkNull(TrialData.getStrNendo())});
				//�S�x-�o��Flg
				arySetTag.add(new String[]{"flg_nendo",checkNull(TrialData.getIntNendoFlg())});
				//���x
				arySetTag.add(new String[]{"ondo",checkNull(TrialData.getStrOndo())});
				//���x-�o��Flg
				arySetTag.add(new String[]{"flg_ondo",checkNull(TrialData.getIntOndoFlg())});
				//PH
				arySetTag.add(new String[]{"ph",checkNull(TrialData.getStrPh())});
				//PH - �o��Flg
				arySetTag.add(new String[]{"flg_ph",checkNull(TrialData.getIntPhFlg())});
				//���_�F����
				arySetTag.add(new String[]{"ritu_sousan_bunseki",checkNull(TrialData.getStrSosanBunseki())});
				//���_�F����-�o��Flg
				arySetTag.add(new String[]{"flg_sousan_bunseki",checkNull(TrialData.getIntSosanBunsekiFlg())});
				//�H���F����
				arySetTag.add(new String[]{"ritu_shokuen_bunseki",checkNull(TrialData.getStrShokuenBunseki())});
				//�H���F����-�o��Flg
				arySetTag.add(new String[]{"flg_shokuen_bunseki",checkNull(TrialData.getIntShokuenBunsekiFlg())});
				//��d
				arySetTag.add(new String[]{"hiju",checkNull(TrialData.getStrHizyu())});
				//��d-�o��Flg
				arySetTag.add(new String[]{"flg_hiju",checkNull(TrialData.getIntHizyuFlg())});
				//��������
				arySetTag.add(new String[]{"suibun_kasei",checkNull(TrialData.getStrSuibun())});
				//��������-�o��Flg
				arySetTag.add(new String[]{"flg_suibun_kasei",checkNull(TrialData.getIntSuibunFlg())});
				//�A���R�[��
				arySetTag.add(new String[]{"alcohol",checkNull(TrialData.getStrArukoru())});
				//�A���R�[��-�o��Flg
				arySetTag.add(new String[]{"flg_alcohol",checkNull(TrialData.getIntArukoruFlg())});
				//�쐬����
				arySetTag.add(new String[]{"memo_sakusei",checkNull(TrialData.getStrSakuseiMemo())});
				//�쐬����-�o��Flg
				arySetTag.add(new String[]{"flg_memo",checkNull(TrialData.getIntSakuseiMemoFlg())});
				//�]��
				arySetTag.add(new String[]{"hyoka",checkNull(TrialData.getStrHyoka())});
				//�]��-�o��Flg
				arySetTag.add(new String[]{"flg_hyoka",checkNull(TrialData.getIntHyokaFlg())});
				//�t���[�@�^�C�g��
				arySetTag.add(new String[]{"free_title1",checkNull(TrialData.getStrFreeTitle1())});
				//�t���[�@���e
				arySetTag.add(new String[]{"free_value1",checkNull(TrialData.getStrFreeNaiyo1())});
				//�t���[�@-�o��Flg
				arySetTag.add(new String[]{"flg_free1",checkNull(TrialData.getIntFreeFlg())});
				//�t���[�A�^�C�g��
				arySetTag.add(new String[]{"free_title2",checkNull(TrialData.getStrFreeTitle2())});
				//�t���[�A���e
				arySetTag.add(new String[]{"free_value2",checkNull(TrialData.getStrFreeNaiyo2())});
				//�t���[�A-�o��Flg
				arySetTag.add(new String[]{"flg_free2",checkNull(TrialData.getIntFreeFl2())});
				//�t���[�B�^�C�g��
				arySetTag.add(new String[]{"free_title3",checkNull(TrialData.getStrFreeTitle3())});
				//�t���[�B���e
				arySetTag.add(new String[]{"free_value3",checkNull(TrialData.getStrFreeNaiyo3())});
				//�t���[�B-�o��Flg
				arySetTag.add(new String[]{"flg_free3",checkNull(TrialData.getIntFreeFl3())});
				//������t
				arySetTag.add(new String[]{"dt_shisaku",checkNull(TrialData.getStrShisakuHi())});
				//�d��d��
				arySetTag.add(new String[]{"juryo_shiagari_g",checkNull(TrialData.getDciShiagari())});
				//�o�^��ID
				arySetTag.add(new String[]{"id_toroku",checkNull(TrialData.getDciTorokuId())});
				//�o�^���t
				arySetTag.add(new String[]{"dt_toroku",checkNull(TrialData.getStrTorokuHi())});
				//�X�V��ID
				arySetTag.add(new String[]{"id_koshin",checkNull(DataCtrl.getInstance().getUserMstData().getDciUserid())});
				//�X�V���t
				arySetTag.add(new String[]{"dt_koshin",DataCtrl.getInstance().getTrialTblData().getSysDate()});
				//�����˗��t���O
				arySetTag.add(new String[]{"flg_shisanIrai",Integer.toString(TrialData.getFlg_shisanIrai())});
//add start -------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.7
				//�v�Z��
				arySetTag.add(new String[]{"siki_keisan",checkNull(TrialData.getStrKeisanSiki())});
//add end   -------------------------------------------------------------------------------
//2011/04/12 QP@10181_No.67 TT Nishigawa Change Start -------------------------
				//�L�����Z��FG
				arySetTag.add(new String[]{"flg_cancel",checkNull(TrialData.getFlg_cancel())});
//2011/04/12 QP@10181_No.67 TT Nishigawa Change Start -------------------------
//2011/05/11 QP@10181_No.42_49_72 TT Nishigawa Add Start -------------------------
				//������d
				arySetTag.add(new String[]{"hiju_sui",checkNull(TrialData.getStrHiju_sui())});
				//������d-�o��Flg
				arySetTag.add(new String[]{"flg_hiju_sui",checkNull(TrialData.getIntHiju_sui_fg())});
//2011/05/11 QP@10181_No.42_49_72 TT Nishigawa Add End --------------------------
// ADD start 20121003 QP@20505 No.24
				//�t���[�S�x �^�C�g��
				arySetTag.add(new String[]{"freetitle_nendo",checkNull(TrialData.getStrFreeTitleNendo())});
				//�t���[�S�x ���e
				arySetTag.add(new String[]{"free_nendo",checkNull(TrialData.getStrFreeNendo())});
				//�t���[�S�x -�o��Flg
				arySetTag.add(new String[]{"flg_freeNendo",checkNull(TrialData.getIntFreeNendoFlg())});
				//�t���[���x �^�C�g��
				arySetTag.add(new String[]{"freetitle_ondo",checkNull(TrialData.getStrFreeTitleOndo())});
				//�t���[���x ���e
				arySetTag.add(new String[]{"free_ondo",checkNull(TrialData.getStrFreeOndo())});
				//�t���[���x -�o��Flg
				arySetTag.add(new String[]{"flg_freeOndo",checkNull(TrialData.getIntFreeOndoFlg())});
				//�t���[�������� �^�C�g��
				arySetTag.add(new String[]{"freetitle_suibun_kasei",checkNull(TrialData.getStrFreeTitleSuibunKasei())});
				//�t���[�������� ���e
				arySetTag.add(new String[]{"free_suibun_kasei",checkNull(TrialData.getStrFreeSuibunKasei())});
				//�t���[�������� -�o��Flg
				arySetTag.add(new String[]{"flg_freeSuibunKasei",checkNull(TrialData.getIntFreeSuibunKaseiFlg())});
				//�t���[�A���R�[�� �^�C�g��
				arySetTag.add(new String[]{"freetitle_alcohol",checkNull(TrialData.getStrFreeTitleAlchol())});
				//�t���[�A���R�[�� ���e
				arySetTag.add(new String[]{"free_alcohol",checkNull(TrialData.getStrFreeAlchol())});
				//�t���[�A���R�[�� -�o��Flg
				arySetTag.add(new String[]{"flg_freeAlchol",checkNull(TrialData.getIntFreeAlcholFlg())});
				//�����|�_�Z�x
				arySetTag.add(new String[]{"jikkoSakusanNodo",checkNull(TrialData.getDciJikkoSakusanNodo())});
				//�����|�_�Z�x -�o��Flg
				arySetTag.add(new String[]{"flg_jikkoSakusanNodo",checkNull(TrialData.getIntJikkoSakusanNodoFlg())});
				//�������l�r�f
				arySetTag.add(new String[]{"msg_suiso",checkNull(TrialData.getDciSuisoMSG())});
				//�������l�r�f-�o��Flg
				arySetTag.add(new String[]{"flg_msg_suiso",checkNull(TrialData.getIntSuisoMSGFlg())});
				//�t���[�C�^�C�g��
				arySetTag.add(new String[]{"free_title4",checkNull(TrialData.getStrFreeTitle4())});
				//�t���[�C���e
				arySetTag.add(new String[]{"free_value4",checkNull(TrialData.getStrFreeNaiyo4())});
				//�t���[�C-�o��Flg
				arySetTag.add(new String[]{"flg_free4",checkNull(TrialData.getIntFreeFlg4())});
				//�t���[�D�^�C�g��
				arySetTag.add(new String[]{"free_title5",checkNull(TrialData.getStrFreeTitle5())});
				//�t���[�D���e
				arySetTag.add(new String[]{"free_value5",checkNull(TrialData.getStrFreeNaiyo5())});
				//�t���[�D-�o��Flg
				arySetTag.add(new String[]{"flg_free5",checkNull(TrialData.getIntFreeFlg5())});
				//�t���[�E�^�C�g��
				arySetTag.add(new String[]{"free_title6",checkNull(TrialData.getStrFreeTitle6())});
				//�t���[�E���e
				arySetTag.add(new String[]{"free_value6",checkNull(TrialData.getStrFreeNaiyo6())});
				//�t���[�E-�o��Flg
				arySetTag.add(new String[]{"flg_free6",checkNull(TrialData.getIntFreeFlg6())});
// ADD end 20121003 QP@20505 No.24

				//XML�փ��R�[�h�ǉ�
				xmlJW040.AddXmlTag("tr_shisaku", "rec", arySetTag);
				//�z�񏉊���
				arySetTag.clear();
			}

			//------------------- T132 ���샊�X�g�e�[�u��(tr_shisaku_list) --------------------
			xmlJW040.AddXmlTag("SA490", "tr_shisaku_list");

			//-------------------------------- ���R�[�h�ǉ�  ---------------------------------
			//�z���f�[�^�擾
			ArrayList addList = DataCtrl.getInstance().getTrialTblData().getAryShisakuList();
			for(int i=0; i<addList.size(); i++){
				PrototypeListData PrototypeListData = (PrototypeListData)addList.get(i);
				//����CD-�Ј�CD
				arySetTag.add(new String[]{"cd_shain",checkNull(PrototypeListData.getDciShisakuUser())});
				//����CD-�N
				arySetTag.add(new String[]{"nen",checkNull(PrototypeListData.getDciShisakuYear())});
				//����CD-�ǔ�
				arySetTag.add(new String[]{"no_oi",checkNull(PrototypeListData.getDciShisakuNum())});
				//����SEQ
				arySetTag.add(new String[]{"seq_shisaku",checkNull(PrototypeListData.getIntShisakuSeq())});
				//�H��CD
				arySetTag.add(new String[]{"cd_kotei",checkNull(PrototypeListData.getIntKoteiCd())});
				//�H��SEQ
				arySetTag.add(new String[]{"seq_kotei",checkNull(PrototypeListData.getIntKoteiSeq())});
				//��
				arySetTag.add(new String[]{"quantity",checkNull(PrototypeListData.getDciRyo())});
				//�F
				arySetTag.add(new String[]{"color",checkNull(PrototypeListData.getStrIro())});
				//�o�^��ID
				arySetTag.add(new String[]{"id_toroku",checkNull(PrototypeListData.getDciTorokuId())});
				//�o�^���t
				arySetTag.add(new String[]{"dt_toroku",checkNull(PrototypeListData.getStrTorokuHi())});
				//�X�V��ID
				arySetTag.add(new String[]{"id_koshin",checkNull(DataCtrl.getInstance().getUserMstData().getDciUserid())});
				//�X�V���t
				arySetTag.add(new String[]{"dt_koshin",DataCtrl.getInstance().getTrialTblData().getSysDate()});
// ADD start 20121009 QP@20505 No.24
				//�H���d��d��
				arySetTag.add(new String[]{"juryo_shiagari_seq",checkNull(PrototypeListData.getDciKouteiShiagari())});
// ADD end 20121009 QP@20505 No.24
				//XML�փ��R�[�h�ǉ�
				xmlJW040.AddXmlTag("tr_shisaku_list", "rec", arySetTag);
				//�z�񏉊���
				arySetTag.clear();
			}

			//---------------------- T133 �����H���e�[�u��(tr_cyuui) ------------------------
			xmlJW040.AddXmlTag("SA490", "tr_cyuui");

			//-------------------------------- ���R�[�h�ǉ�  ---------------------------------
			//�z���f�[�^�擾
			ArrayList addCyuui = DataCtrl.getInstance().getTrialTblData().SearchSeizoKouteiData(0);
			for(int i=0; i<addCyuui.size(); i++){
				ManufacturingData ManufacturingData = (ManufacturingData)addCyuui.get(i);
				//����CD-�Ј�CD
				arySetTag.add(new String[]{"cd_shain",checkNull(ManufacturingData.getDciShisakuUser())});
				//����CD-�N
				arySetTag.add(new String[]{"nen",checkNull(ManufacturingData.getDciShisakuYear())});
				//����CD-�ǔ�
				arySetTag.add(new String[]{"no_oi",checkNull(ManufacturingData.getDciShisakuNum())});
				//���ӎ���NO
				arySetTag.add(new String[]{"no_chui",checkNull(ManufacturingData.getIntTyuiNo())});
				//���ӎ���
				arySetTag.add(new String[]{"chuijiko",checkNull(ManufacturingData.getStrTyuiNaiyo())});
				//�o�^��ID
				arySetTag.add(new String[]{"id_toroku",checkNull(ManufacturingData.getDciTorokuId())});
				//�o�^���t
				arySetTag.add(new String[]{"dt_toroku",checkNull(ManufacturingData.getStrTorokuHi())});
				//�X�V��ID
				arySetTag.add(new String[]{"id_koshin",checkNull(DataCtrl.getInstance().getUserMstData().getDciUserid())});
				//�X�V���t
				arySetTag.add(new String[]{"dt_koshin",DataCtrl.getInstance().getTrialTblData().getSysDate()});

				//XML�փ��R�[�h�ǉ�
				xmlJW040.AddXmlTag("tr_cyuui", "rec", arySetTag);
				//�z�񏉊���
				arySetTag.clear();
			}

			//---------------------- T141 ���������e�[�u��(tr_genryo) ------------------------
			xmlJW040.AddXmlTag("SA490", "tr_genryo");

			//-------------------------------- ���R�[�h�ǉ�  ---------------------------------
			//���������f�[�^�擾
			ArrayList addGenka = DataCtrl.getInstance().getTrialTblData().SearchGenkaGenryoData(0);
			for(int i=0; i<addGenka.size(); i++){
				CostMaterialData costMaterialData = (CostMaterialData)addGenka.get(i);

				//�@����CD-�Ј�CD
				arySetTag.add(new String[]{"cd_shain",checkNull(costMaterialData.getDciShisakuUser())});
				//�@����CD-�N
				arySetTag.add(new String[]{"nen",checkNull(costMaterialData.getDciShisakuYear())});
				//�@����CD-�ǔ�
				arySetTag.add(new String[]{"no_oi",checkNull(costMaterialData.getDciShisakuNum())});
				//�@����SEQ
				arySetTag.add(new String[]{"seq_shisaku",checkNull(costMaterialData.getIntShisakuSeq())});
				//�@���flg
				arySetTag.add(new String[]{"flg_print",checkNull(costMaterialData.getIntinsatu())});
				//�@�d�_�ʐ���
				arySetTag.add(new String[]{"zyusui",checkNull(costMaterialData.getStrZyutenSui())});
				//�@�d�_�ʖ���
				arySetTag.add(new String[]{"zyuabura",checkNull(costMaterialData.getStrZyutenYu())});
				//�@���v��
				arySetTag.add(new String[]{"gokei",checkNull(costMaterialData.getStrGokei())});
				//�@������
				arySetTag.add(new String[]{"genryohi",checkNull(costMaterialData.getStrGenryohi())});
				//�@������i1�{�j
				arySetTag.add(new String[]{"genryohi1",checkNull(costMaterialData.getStrGenryohiTan())});
				//�@��d
				arySetTag.add(new String[]{"hiju",checkNull(costMaterialData.getStrHizyu())});
				//�@�e��
				arySetTag.add(new String[]{"yoryo",checkNull(costMaterialData.getStrYoryo())});
				//�@����
				arySetTag.add(new String[]{"irisu",checkNull(costMaterialData.getStrIrisu())});
				//�@�L������
				arySetTag.add(new String[]{"yukobudomari",checkNull(costMaterialData.getStrYukoBudomari())});
				//�@���x����
				arySetTag.add(new String[]{"reberu",checkNull(costMaterialData.getStrLevel())});
				//�@��d����
				arySetTag.add(new String[]{"hizyubudomari",checkNull(costMaterialData.getStrHizyuBudomari())});
				//�@���Ϗ[�U��
				arySetTag.add(new String[]{"heikinzyu",checkNull(costMaterialData.getStrZyutenAve())});
				//�@1C/S������
				arySetTag.add(new String[]{"cs_genryo",checkNull(costMaterialData.getStrGenryohiCs())});
				//�@1C/S�ޗ���
				arySetTag.add(new String[]{"cs_zairyohi",checkNull(costMaterialData.getStrZairyohiCs())});
				//�@1C/S�o��
				arySetTag.add(new String[]{"cs_keihi",checkNull(costMaterialData.getStrKeihiCs())});
				//�@1C/S�����v
				arySetTag.add(new String[]{"cs_genka",checkNull(costMaterialData.getStrGenkakeiCs())});
				//�@1�����v
				arySetTag.add(new String[]{"ko_genka",checkNull(costMaterialData.getStrGenkakeiTan())});
				//�@1����
				arySetTag.add(new String[]{"ko_baika",checkNull(costMaterialData.getStrGenkakeiBai())});
				//�@1�e����
				arySetTag.add(new String[]{"ko_riritu",checkNull(costMaterialData.getStrGenkakeiRi())});
//				//�@�o�^��ID
//				arySetTag.add(new String[]{"id_toroku",checkNull(costMaterialData.getDciTorokuId())});
//				//�@�o�^���t
//				arySetTag.add(new String[]{"dt_toroku",checkNull(costMaterialData.getStrTorokuHi())});
//				//�@�X�V��ID
//				arySetTag.add(new String[]{"id_koshin",checkNull(costMaterialData.getDciKosinId())});
//				//�@�X�V���t
//				arySetTag.add(new String[]{"dt_koshin",checkNull(costMaterialData.getStrKosinHi())});
				//�o�^��ID
				arySetTag.add(new String[]{"id_toroku",checkNull(costMaterialData.getDciTorokuId())});
				//�o�^���t
				arySetTag.add(new String[]{"dt_toroku",checkNull(costMaterialData.getStrTorokuHi())});
				//�X�V��ID
				arySetTag.add(new String[]{"id_koshin",checkNull(DataCtrl.getInstance().getUserMstData().getDciUserid())});
				//�X�V���t
				arySetTag.add(new String[]{"dt_koshin",DataCtrl.getInstance().getTrialTblData().getSysDate()});

				//XML�փ��R�[�h�ǉ�
				xmlJW040.AddXmlTag("tr_genryo", "rec", arySetTag);

				//�z�񏉊���
				arySetTag.clear();
			}

			//�@XML���M
//			System.out.println("JW040���MXML===============================================================");
//			xmlJW040.dispXml();

			xcon = new XmlConnection(xmlJW040);
			xcon.setStrAddress(DataCtrl.getInstance().getJnlpConnect().getStrAddressAjax());
			xcon.XmlSend();

			//�@XML��M
			xmlJW040 = xcon.getXdocRes();

//			System.out.println();
//			System.out.println("JW040��MXML===============================================================");
//			xmlJW040.dispXml();
//			System.out.println();

			//------------------------------- Result�f�[�^�`�F�b�N -----------------------------
			DataCtrl.getInstance().getResultData().setResultData(xmlJW040);

			if ( intChkMsg == 0 ) {
				//�o�^�{�^��������

				if (DataCtrl.getInstance().getResultData().getStrReturnFlg().equals("true")) {
					//���[�h�̕ύX
					DataCtrl.getInstance().getMessageCtrl().PrintMessageString("����Ɏ���o�^�������������܂����B");
				}else{
					ExceptionBase ExceptionBase  = new ExceptionBase();
					throw ExceptionBase;
				}

			} else {
				//�����ۑ�������

				if (DataCtrl.getInstance().getResultData().getStrReturnFlg().equals("true")) {
					//���[�h�̕ύX

				}else{
					//�G���[������

					//�G���[���b�Z�[�W�𒠕[�p�ɕύX
					String strErrMsg = new String(DataCtrl.getInstance().getResultData().getStrErrorMsg());

					//����\
					if ( intChkMsg == 1 ) {
						strErrMsg = strErrMsg.replaceFirst("�o�^", "����\ �����ۑ�");

					//�T���v��������
					} else if ( intChkMsg == 2 ) {
						strErrMsg = strErrMsg.replaceFirst("�o�^", "�T���v�������� �����ۑ�");

					//�h�{�v�Z��
					} else if ( intChkMsg == 3 ) {
						strErrMsg = strErrMsg.replaceFirst("�o�^", "�h�{�v�Z�� �����ۑ�");

					//�������Z�\
					} else if ( intChkMsg == 4 ) {
						strErrMsg = strErrMsg.replaceFirst("�o�^", "�������Z�\ �����ۑ�");

					}

					//Result�̃G���[���b�Z�[�W��ύX
					DataCtrl.getInstance().getResultData().setStrErrorMsg(strErrMsg);

					//Exception��throw����
					ExceptionBase ExceptionBase  = new ExceptionBase();
					throw ExceptionBase;

				}

			}

			//�������Z��ʁ@���Z�m��T���v��No�R���{�{�b�N�X �X�V
			tb.getTrial5Panel().updShisanSampleNo();

			//�������ZFG�ҏW�s�ݒ�
			DataCtrl.getInstance().getTrialTblData().setShisakuRetuFlg_initCtrl();
			setGenkaIrai_false();

//add start -------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.33,34
			//�o�^��̕ҏW�t���O�ݒ�{�ĕ\��
			dispHenshuOkFg();
//add end   -------------------------------------------------------------------------------

		}catch(ExceptionBase eb){
			throw eb;

		}catch(Exception e){
			e.printStackTrace();

			ex = new ExceptionBase();
			ex.setStrErrCd("");
			ex.setStrErrmsg("�ҏW�o�^���������s���܂���");
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getMessage());
			throw ex;

		}finally{

		}
	}

	/************************************************************************************
	 *
	 *   ���������p�@XML�ʐM�����iJW010�j
	 *    : ��������XML�f�[�^�ʐM�iJW010�j���s��
	 *   @author TT nishigawa
	 *
	 ************************************************************************************/
	private void conJW010() throws ExceptionBase{
		try{
			//----------------------------- ���M�p�����[�^�i�[  -------------------------------
			String strUser = DataCtrl.getInstance().getParamData().getDciUser().toString();
			String strShisaku_user = DataCtrl.getInstance().getParamData().getStrSisaku_user();
			String strShisaku_nen = DataCtrl.getInstance().getParamData().getStrSisaku_nen();
			String strShisaku_oi = DataCtrl.getInstance().getParamData().getStrSisaku_oi();

//�yQP@10181_No.11�z
//����R�s�[���[�h�ҏW�p�^�[���ǉ� start ----------------------------------------------------
			//String strGamenId = DataCtrl.getInstance().getParamData().getStrMode();

			//���ID������
			String strGamenId = "";
			//�N�����[�h������R�s�[�̏ꍇ
			if(DataCtrl.getInstance().getParamData().getStrMode().equals(JwsConstManager.JWS_MODE_0004)){
				//���ID�Ɂu�ڍׁv��ݒ�
				strGamenId = JwsConstManager.JWS_MODE_0001;
			}
			//�N�����[�h������R�s�[�łȂ��ꍇ
			else{
				//���݂̋N�����[�h��ݒ�
				strGamenId = DataCtrl.getInstance().getParamData().getStrMode();
			}
//����R�s�[���[�h�ҏW�p�^�[���ǉ� end ----------------------------------------------------


			//----------------------------- ���MXML�f�[�^�쐬  ------------------------------
			xmlJW010 = new XmlData();
			ArrayList arySetTag = new ArrayList();

			//--------------------------------- Root�ǉ�  ---------------------------------
			xmlJW010.AddXmlTag("","JW010");
			arySetTag.clear();

			//--------------------------- �@�\ID�ǉ��iUSEERINFO�j  --------------------------
			xmlJW010.AddXmlTag("JW010", "USERINFO");
			//�@�e�[�u���^�O�ǉ�
			xmlJW010.AddXmlTag("USERINFO", "table");
			//�@���R�[�h�ǉ�
			arySetTag.add(new String[]{"kbn_shori", "3"});
			arySetTag.add(new String[]{"id_user",strUser});
			xmlJW010.AddXmlTag("table", "rec", arySetTag);
			//�z�񏉊���
			arySetTag.clear();

			//------------------------- �@�\ID�ǉ��iJWS���e���������j  -------------------------
			this.addLiteralDataXml(strUser, strGamenId);

			//--------------------------- �@�\ID�ǉ��i��Ќ����j  ----------------------------
			xmlJW010.AddXmlTag("JW010", "SA140");
			//�@�e�[�u���^�O�ǉ�
			xmlJW010.AddXmlTag("SA140", "table");
			//�@���R�[�h�ǉ�
			arySetTag.add(new String[]{"id_user",strUser});
			arySetTag.add(new String[]{"id_gamen", strGamenId});

			xmlJW010.AddXmlTag("table", "rec", arySetTag);
			//�z�񏉊���
			arySetTag.clear();

			//--------------------------- �@�\ID�ǉ��i�r������j  -----------------------------
			xmlJW010.AddXmlTag("JW010", "SA420");
			//�@�e�[�u���^�O�ǉ�
			xmlJW010.AddXmlTag("SA420", "table");
			//���R�[�h�ǉ�

//2011/05/19�@�yQP@10181_No.11�z����R�s�[���[�h�����@TT.NISHIGAWA�@start
//			//�V�K���[�h�̏ꍇ or �Q�ƃ��[�h�̏ꍇ
//			if(DataCtrl.getInstance().getParamData().getStrMode().equals(JwsConstManager.JWS_MODE_0002)
//					|| DataCtrl.getInstance().getParamData().getStrMode().equals(JwsConstManager.JWS_MODE_0000) ){

			//�V�K���[�h�̏ꍇ or �Q�ƃ��[�h�̏ꍇ or ����R�s�[���[�h�̏ꍇ
			if(DataCtrl.getInstance().getParamData().getStrMode().equals(JwsConstManager.JWS_MODE_0002)
					|| DataCtrl.getInstance().getParamData().getStrMode().equals(JwsConstManager.JWS_MODE_0000)
					|| DataCtrl.getInstance().getParamData().getStrMode().equals(JwsConstManager.JWS_MODE_0004) ){

//2011/05/19�@�yQP@10181_No.11�z����R�s�[���[�h�����@TT.NISHIGAWA�@end

				arySetTag.add(new String[]{"kubun_ziko", "0"});
				arySetTag.add(new String[]{"kubun_haita", "1"});
				arySetTag.add(new String[]{"id_user", strUser});
				arySetTag.add(new String[]{"cd_shain", strShisaku_user});
				arySetTag.add(new String[]{"nen", strShisaku_nen});
				arySetTag.add(new String[]{"no_oi", strShisaku_oi});
				xmlJW010.AddXmlTag("table", "rec", arySetTag);
				//�z�񏉊���
				arySetTag.clear();
			}else{
				arySetTag.add(new String[]{"kubun_ziko", "1"});
				arySetTag.add(new String[]{"kubun_haita", "1"});
				arySetTag.add(new String[]{"id_user", strUser});
				arySetTag.add(new String[]{"cd_shain", strShisaku_user});
				arySetTag.add(new String[]{"nen", strShisaku_nen});
				arySetTag.add(new String[]{"no_oi", strShisaku_oi});
				xmlJW010.AddXmlTag("table", "rec", arySetTag);
				//�z�񏉊���
				arySetTag.clear();
			}

			//------------------------- �@�\ID�ǉ��i����f�[�^�����j  ---------------------------
			xmlJW010.AddXmlTag("JW010", "SA480");
			//�@�e�[�u���^�O�ǉ�
			xmlJW010.AddXmlTag("SA480", "table");
			//�@���R�[�h�ǉ�
			arySetTag.add(new String[]{"cd_shain", strShisaku_user});
			arySetTag.add(new String[]{"nen", strShisaku_nen});
			arySetTag.add(new String[]{"no_oi", strShisaku_oi});
			xmlJW010.AddXmlTag("table", "rec", arySetTag);
			//�z�񏉊���
			arySetTag.clear();

			//------------------------ �@�\ID�ǉ��i�����S����Ќ����j  -------------------------
			xmlJW010.AddXmlTag("JW010", "SA210");
			//�@�e�[�u���^�O�ǉ�
			xmlJW010.AddXmlTag("SA210", "table");
			//�@���R�[�h�ǉ�
			arySetTag.add(new String[]{"id_user", strUser});
			xmlJW010.AddXmlTag("table", "rec", arySetTag);
			//�z�񏉊���
			arySetTag.clear();


			//----------------------------------- XML���M  ----------------------------------
//			System.out.println("JW010���MXML===============================================================");
//			xmlJW010.dispXml();
			xcon = new XmlConnection(xmlJW010);
			xcon.setStrAddress(DataCtrl.getInstance().getJnlpConnect().getStrAddressAjax());
			xcon.XmlSend();

			//----------------------------------- XML��M  ----------------------------------
			xmlJW010 = xcon.getXdocRes();

//			System.out.println();
//			System.out.println("JW010��MXML===============================================================");
//			xmlJW010.dispXml();
//			System.out.println();

			//�e�X�gXML�f�[�^
			//xmlJW010 = new XmlData(new File("src/main/JW010.xml"));

			//------------------------------- Resuslt�f�[�^�ݒ� -------------------------------
			DataCtrl.getInstance().getResultData().setResultData(xmlJW010);

			//------------------------------- ���e�����f�[�^�ݒ�  ------------------------------
			this.setLiteralData();

//ADD 2013/06/21 ogawa �yQP@30151�zNo.9 start
			//-------------------------------- �̐Ӊ�Ѓf�[�^�ݒ�  --------------------------------
			DataCtrl.getInstance().getHansekiData().setHansekiData(xmlJW010);
//ADD 2013/06/21 ogawa �yQP@30151�zNo.9 end
			//-------------------------------- ��Ѓf�[�^�ݒ�  --------------------------------
			DataCtrl.getInstance().getKaishaData().setKaishaData(xmlJW010);

			//---------------------------- ���[�U�}�X�^�f�[�^�ݒ�  -----------------------------
			DataCtrl.getInstance().getUserMstData().setSeizoData(xmlJW010);

			//2010/02/25 NAKAMURA ADD START----------------------------------------------
			//---------------------------- �r�����[�U�f�[�^�ݒ�  -----------------------------
			DataCtrl.getInstance().getUserMstData().setHaitaUserData(xmlJW010);
			//2010/02/25 NAKAMURA ADD END------------------------------------------------

			//---------------------------------- �r���`�F�b�N ----------------------------------
			if(!DataCtrl.getInstance().getParamData().getStrMode().equals(JwsConstManager.JWS_MODE_0000)){
				//�@�\ID�̐ݒ�
				String strKinoId = "SA420";

				//�S�̔z��擾
				ArrayList aryData = xmlJW010.GetAryTag(strKinoId);

				//�@�\�z��擾
				ArrayList kinoData = (ArrayList)aryData.get(0);

				//�e�[�u���z��擾
				ArrayList tableData = (ArrayList)kinoData.get(1);

				//���R�[�h�擾
				for(int i=1; i<tableData.size(); i++){
					//�@�P���R�[�h�擾
					ArrayList recData = ((ArrayList)((ArrayList)tableData.get(i)).get(0));
					for(int j=0; j<recData.size(); j++){
						//�@���ږ��擾
						String recNm = ((String[])recData.get(j))[1];
						//�@���ڒl�擾
						String recVal = ((String[])recData.get(j))[2];
						//�@����R�[�h
						if ( recNm == "kekka_haita" ) {
							//�r���������s��
							if(recVal.equals("false")){

//2011/05/19�@�yQP@10181_No.11�z����R�s�[���[�h�����@TT.NISHIGAWA�@start
								//�\�����b�Z�[�W�ݒ�
								String sanshoMsg = "";

								//�J���ꂽ���̃��[�h���m�F�F�ڍׂ̏ꍇ
								if(DataCtrl.getInstance().getParamData().getStrMode().equals(JwsConstManager.JWS_MODE_0001)){
									//����R�s�[���[�h�ɐݒ�
									DataCtrl.getInstance().getParamData().setStrMode(JwsConstManager.JWS_MODE_0004);
									sanshoMsg = "���L���[�U���ҏW���ł��B����R�s�[���[�h�ŋN�����܂��B";
								}
								else{
									//�Q�ƃ��[�h�ɐݒ�
									DataCtrl.getInstance().getParamData().setStrMode(JwsConstManager.JWS_MODE_0000);
									sanshoMsg = "���L���[�U���ҏW���ł��B�Q�ƃ��[�h�ŋN�����܂��B";
								}

								//2010/05/19�@�V�T�N�C�b�N�i�����j�v�]�y�Č�No9�z�r�����̕\���@TT.NISHIGAWA�@START
								//DataCtrl.getInstance().getMessageCtrl().PrintMessageString("�����[�U���ҏW���ł��B�Q�ƃ��[�h�ŋN�����܂��B");
								sanshoMsg = sanshoMsg + "\n";
								sanshoMsg = sanshoMsg + "\n��ЁF" + DataCtrl.getInstance().getUserMstData().getStrHaitaKaishanm();
								sanshoMsg = sanshoMsg + "\n�����F" + DataCtrl.getInstance().getUserMstData().getStrHaitaBushonm();
								sanshoMsg = sanshoMsg + "\n�����F" + DataCtrl.getInstance().getUserMstData().getStrHaitaShimei();
								//���b�Z�[�W�\��
								DataCtrl.getInstance().getMessageCtrl().PrintMessageString(sanshoMsg);
								//2010/05/19�@�V�T�N�C�b�N�i�����j�v�]�y�Č�No9�z�r�����̕\���@TT.NISHIGAWA�@END
//2011/05/19�@�yQP@10181_No.11�z����R�s�[���[�h�����@TT.NISHIGAWA�@end
							}
						}
					}
				}
			}


			//---------------------------- ����e�[�u���f�[�^�ݒ�  -----------------------------
			//���[�h�擾
			String mode = DataCtrl.getInstance().getParamData().getStrMode();

			//�yQP@10181_No.11�z
			//����R�s�[���[�h�ҏW�p�^�[���ǉ� -----start

			//���[�h�F�Q��or�ڍ�or���@�R�s�[�̏ꍇ
			//if(mode.equals(JwsConstManager.JWS_MODE_0000) || mode.equals(JwsConstManager.JWS_MODE_0001) || mode.equals(JwsConstManager.JWS_MODE_0003)){
			//	DataCtrl.getInstance().getTrialTblData().setTraialData(xmlJW010);
			if(mode.equals(JwsConstManager.JWS_MODE_0000) || mode.equals(JwsConstManager.JWS_MODE_0001) || mode.equals(JwsConstManager.JWS_MODE_0003) || mode.equals(JwsConstManager.JWS_MODE_0004)){
				DataCtrl.getInstance().getTrialTblData().setTraialData(xmlJW010);

			//����R�s�[���[�h�ҏW�p�^�[���ǉ� -----end

			//���[�h�F�V�K�̏ꍇ
			}else{
				DataCtrl.getInstance().getTrialTblData().setTraialData(0);
			}

//add start -------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.4,8
			//�R���X�g�l�ݒ�
			DataCtrl.getInstance().getTrialTblData().setConstData(xmlJW010);
//add end   -------------------------------------------------------------------------------

//add start -------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.33,34
			//�ҏW�\�t���O�ݒ�i�����\�����j
			DataCtrl.getInstance().getTrialTblData().setShisakuListHenshuOkFg();
//add end   -------------------------------------------------------------------------------


		}catch(ExceptionBase ex){
			ex.printStackTrace();
			throw ex;

		}catch(Exception e){
			e.printStackTrace();

			//�G���[�ݒ�
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			ex.setStrErrmsg("�����\���̃f�[�^�擾�Ɏ��s���܂���");
			ex.setStrErrShori(this.getClass().toString());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getMessage());
			throw ex;

		}finally{

			//DataCtrl.getInstance().getTrialTblData().dispTrial();
		}

	}

	/************************************************************************************
	 *
	 * ���e�����f�[�^�𑗐M�pXML�Ɋi�[
	 * @param strUserId : ���[�UID
	 * @param strGamenId : ���ID
	 * @throws ExceptionBase
	 *
	 ************************************************************************************/
	private void addLiteralDataXml(String strUserId, String strGamenId) throws ExceptionBase {
		String[] id_user = new String[2];
		String[] id_gamen = new String[2];
		ArrayList arySetTag = new ArrayList();

		//�@�ySA600�`SA780 : JWS���e���������z �@�\ID�ǉ�
		for ( int i=0; i<19; i++ ) {

			String strKinoId = "SA" + (600+(i*10));
			xmlJW010.AddXmlTag("JW010", strKinoId);

			//�@�e�[�u���^�O�ǉ�
			xmlJW010.AddXmlTag(strKinoId, "table");

			//�@���R�[�h�ǉ�
			id_user = new String[]{"id_user", strUserId};
			id_gamen = new String[]{"id_gamen",strGamenId};
			arySetTag.add(id_user);
			arySetTag.add(id_gamen);
			xmlJW010.AddXmlTag("table", "rec", arySetTag);
			arySetTag.clear();
		}

		//�@�ySA850 : JWS���e���������z �@�\ID�ǉ�
		xmlJW010.AddXmlTag("JW010", "SA850");

		//�@�e�[�u���^�O�ǉ�
		xmlJW010.AddXmlTag("SA850", "table");

		//�@���R�[�h�ǉ�
		id_user = new String[]{"id_user", strUserId};
		id_gamen = new String[]{"id_gamen",strGamenId};
		arySetTag.add(id_user);
		arySetTag.add(id_gamen);
		xmlJW010.AddXmlTag("table", "rec", arySetTag);
		arySetTag.clear();

//2011/05/30�@�yQP@10181_No.42_49_72�z�H���p�^�[������@TT.NISHIGAWA�@start

		//�@�ySA900 : JWS���e���������i�H���p�^�[���j�z �@�\ID�ǉ�
		xmlJW010.AddXmlTag("JW010", "SA900");
		//�@�e�[�u���^�O�ǉ�
		xmlJW010.AddXmlTag("SA900", "table");
		//�@���R�[�h�ǉ�
		id_user = new String[]{"id_user", strUserId};
		id_gamen = new String[]{"id_gamen",strGamenId};
		arySetTag.add(id_user);
		arySetTag.add(id_gamen);
		xmlJW010.AddXmlTag("table", "rec", arySetTag);
		arySetTag.clear();


		//�@�ySA910 : JWS���e���������i���i��d�j�z �@�\ID�ǉ�
		xmlJW010.AddXmlTag("JW010", "SA910");
		//�@�e�[�u���^�O�ǉ�
		xmlJW010.AddXmlTag("SA910", "table");
		//�@���R�[�h�ǉ�
		id_user = new String[]{"id_user", strUserId};
		id_gamen = new String[]{"id_gamen",strGamenId};
		arySetTag.add(id_user);
		arySetTag.add(id_gamen);
		xmlJW010.AddXmlTag("table", "rec", arySetTag);
		arySetTag.clear();

		//�@�ySA920 : JWS���e���������i������d�j�z �@�\ID�ǉ�
		xmlJW010.AddXmlTag("JW010", "SA920");
		//�@�e�[�u���^�O�ǉ�
		xmlJW010.AddXmlTag("SA920", "table");
		//�@���R�[�h�ǉ�
		id_user = new String[]{"id_user", strUserId};
		id_gamen = new String[]{"id_gamen",strGamenId};
		arySetTag.add(id_user);
		arySetTag.add(id_gamen);
		xmlJW010.AddXmlTag("table", "rec", arySetTag);
		arySetTag.clear();

		//�@�ySA930 : JWS���e���������i�������P�t�^�C�v�@�H�������j�z �@�\ID�ǉ�
		xmlJW010.AddXmlTag("JW010", "SA930");
		//�@�e�[�u���^�O�ǉ�
		xmlJW010.AddXmlTag("SA930", "table");
		//�@���R�[�h�ǉ�
		id_user = new String[]{"id_user", strUserId};
		id_gamen = new String[]{"id_gamen",strGamenId};
		arySetTag.add(id_user);
		arySetTag.add(id_gamen);
		xmlJW010.AddXmlTag("table", "rec", arySetTag);
		arySetTag.clear();

		//�@�ySA940 : JWS���e���������i�������Q�t�^�C�v�@�H�������j�z �@�\ID�ǉ�
		xmlJW010.AddXmlTag("JW010", "SA940");
		//�@�e�[�u���^�O�ǉ�
		xmlJW010.AddXmlTag("SA940", "table");
		//�@���R�[�h�ǉ�
		id_user = new String[]{"id_user", strUserId};
		id_gamen = new String[]{"id_gamen",strGamenId};
		arySetTag.add(id_user);
		arySetTag.add(id_gamen);
		xmlJW010.AddXmlTag("table", "rec", arySetTag);
		arySetTag.clear();

		//�@�ySA950 : JWS���e���������i���̑��E���H�^�C�v�@�H�������j�z �@�\ID�ǉ�
		xmlJW010.AddXmlTag("JW010", "SA950");
		//�@�e�[�u���^�O�ǉ�
		xmlJW010.AddXmlTag("SA950", "table");
		//�@���R�[�h�ǉ�
		id_user = new String[]{"id_user", strUserId};
		id_gamen = new String[]{"id_gamen",strGamenId};
		arySetTag.add(id_user);
		arySetTag.add(id_gamen);
		xmlJW010.AddXmlTag("table", "rec", arySetTag);
		arySetTag.clear();
//2011/05/30�@�yQP@10181_No.42_49_72�z�H���p�^�[������@TT.NISHIGAWA�@end
// ADD start 20121003 QP@20505 No.24
		//�@�ySA960 : JWS���e���������i�����|�_�Z�x�j�z �@�\ID�ǉ�
		xmlJW010.AddXmlTag("JW010", "SA960");
		//�@�e�[�u���^�O�ǉ�
		xmlJW010.AddXmlTag("SA960", "table");
		//�@���R�[�h�ǉ�
		id_user = new String[]{"id_user", strUserId};
		id_gamen = new String[]{"id_gamen",strGamenId};
		arySetTag.add(id_user);
		arySetTag.add(id_gamen);
		xmlJW010.AddXmlTag("table", "rec", arySetTag);
		arySetTag.clear();
// ADD end 20121003 QP@20505 No.24

	}

	/************************************************************************************
	 *
	 * ���e�����f�[�^�ێ�����
	 *  : ���e�����f�[�^��DataCtrl���t�B�[���h�Ɋi�[����
	 * @throws ExceptionBase
	 *
	 ************************************************************************************/
	private void setLiteralData() throws ExceptionBase {
		//�@SA600 : ���e�����f�[�^(�H������)
		DataCtrl.getInstance().getLiteralDataZokusei().setLiteralData(xmlJW010);
		//�@SA610 : ���e�����f�[�^(�ꊇ�\��)
		DataCtrl.getInstance().getLiteralDataIkatu().setLiteralData(xmlJW010);
		//�@SA620 : ���e�����f�[�^(�W������)
		DataCtrl.getInstance().getLiteralDataZyanru().setLiteralData(xmlJW010);
		//�@SA630 : ���e�����f�[�^(���[�U)
		DataCtrl.getInstance().getLiteralDataUser().setLiteralData(xmlJW010);
		//�@SA640 : ���e�����f�[�^(��������)
		DataCtrl.getInstance().getLiteralDataTokutyo().setLiteralData(xmlJW010);
		//�@SA650 : ���e�����f�[�^(�p�r)
		DataCtrl.getInstance().getLiteralDataYoto().setLiteralData(xmlJW010);
		//�@SA660 : ���e�����f�[�^(���i��)
		DataCtrl.getInstance().getLiteralDataKakaku().setLiteralData(xmlJW010);
		//�@SA670 : ���e�����f�[�^(���)
		DataCtrl.getInstance().getLiteralDataShubetu().setLiteralData(xmlJW010);
		//�@SA680 : ���e�����f�[�^(�����w��)
		DataCtrl.getInstance().getLiteralDataShosu().setLiteralData(xmlJW010);
		//�@SA690 : ���e�����f�[�^(�S���c��)
		DataCtrl.getInstance().getLiteralDataTanto().setLiteralData(xmlJW010);
		//�@SA700 : ���e�����f�[�^(�������@)
		DataCtrl.getInstance().getLiteralDataSeizo().setLiteralData(xmlJW010);
		//�@SA710 : ���e�����f�[�^(�[�U���@)
		DataCtrl.getInstance().getLiteralDataZyuten().setLiteralData(xmlJW010);
		//�@SA720 : ���e�����f�[�^(�E�ە��@)
		DataCtrl.getInstance().getLiteralDataSakin().setLiteralData(xmlJW010);
		//�@SA730 : ���e�����f�[�^(�e����)
		DataCtrl.getInstance().getLiteralDataYoki().setLiteralData(xmlJW010);
		//�@SA740 : ���e�����f�[�^(�e��)
		DataCtrl.getInstance().getLiteralDataYoryo().setLiteralData(xmlJW010);
		//�@SA750 : ���e�����f�[�^(�P��)
		DataCtrl.getInstance().getLiteralDataTani().setLiteralData(xmlJW010);
		//�@SA760 : ���e�����f�[�^(�׎p)
		DataCtrl.getInstance().getLiteralDataNisugata().setLiteralData(xmlJW010);
		//�@SA770 : ���e�����f�[�^(�戵���x)
		DataCtrl.getInstance().getLiteralDataOndo().setLiteralData(xmlJW010);
		//�@SA780 : ���e�����f�[�^(�ܖ�����)
		DataCtrl.getInstance().getLiteralDataShomi().setLiteralData(xmlJW010);
		//�@SA850 : ���e�����f�[�^(�ܖ�����)
		DataCtrl.getInstance().getLiteralDataShubetuNo().setLiteralData(xmlJW010);

//2011/05/30�@�yQP@10181_No.42_49_72�z�H���p�^�[������@TT.NISHIGAWA�@start
		//�@SA900 : ���e�����f�[�^(�H���p�^�[��)
		DataCtrl.getInstance().getLiteralDataKoteiPtn().setLiteralData(xmlJW010);

		//�@SA910 : ���e�����f�[�^(���i��d)
		DataCtrl.getInstance().getLiteralDataSeihinHiju().setLiteralData(xmlJW010);

		//�@SA920 : ���e�����f�[�^(������d)
		DataCtrl.getInstance().getLiteralDataYusoHiju().setLiteralData(xmlJW010);

		//�@SA930 : ���e�����f�[�^(�������P�t�^�C�v)
		DataCtrl.getInstance().getLiteralDataKotei_tyomi1().setLiteralData(xmlJW010);

		//�@SA940 : ���e�����f�[�^(�������Q�t�^�C�v)
		DataCtrl.getInstance().getLiteralDataKotei_tyomi2().setLiteralData(xmlJW010);

		//�@SA940 : ���e�����f�[�^(���̑��E���H�^�C�v)
		DataCtrl.getInstance().getLiteralDataKotei_sonota().setLiteralData(xmlJW010);
//2011/05/30�@�yQP@10181_No.42_49_72�z�H���p�^�[������@TT.NISHIGAWA�@end
// ADD start 20121003 QP@20505 No.24
		//�@SA960 : ���e�����f�[�^(�����|�_�Z�x)
		DataCtrl.getInstance().getLiteralDataJikkoSakusanNodo().setLiteralData(xmlJW010);
// ADD end 20121003 QP@20505 No.24
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

	/************************************************************************************
	 *
	 *  �f�[�^�}���N���X�iFocusListener�j
	 *   @author TT nishigawa
	 *
	 ************************************************************************************/
	class FocusCheck implements FocusListener {
		String komoku = "";

		/****** �R���X�g���N�^ ******/
		public FocusCheck(String strKomoku){
			komoku = strKomoku;
		}

		/***** ���X�g�t�H�[�J�X *****/
		public void focusLost( FocusEvent e ){
			try{
				//�R���|�[�l���g�擾
				JComponent jc = (JComponent)e.getSource();

				if(jc != null){

					//-------------------------- �˗��ԍ�  ---------------------------
					if(komoku == "�˗��ԍ�"){
						String insert = checkNull(((JTextField)jc).getText());
						DataCtrl.getInstance().getTrialTblData().UpdIraiNo(
								DataCtrl.getInstance().getTrialTblData().checkNullString(insert),
								DataCtrl.getInstance().getUserMstData().getDciUserid()
							);
					}

					//---------------------------- �i��  -----------------------------
					if(komoku == "�i��"){
						String insert = checkNull(((JTextField)jc).getText());
						DataCtrl.getInstance().getTrialTblData().UpdHinmei(
								DataCtrl.getInstance().getTrialTblData().checkNullString(insert),
								DataCtrl.getInstance().getUserMstData().getDciUserid()
							);
					}

					//---------------------------- �p�~  -----------------------------
					if(komoku == "�p�~"){
						String insert = (((JCheckBox)jc).isSelected())? "1" : "0";
						DataCtrl.getInstance().getTrialTblData().UpdHaishi(
								DataCtrl.getInstance().getTrialTblData().checkNullInt(insert),
								DataCtrl.getInstance().getUserMstData().getDciUserid()
							);
					}

					//-------------------------- ��ʔԍ�  ---------------------------
					if(komoku == "��ʔԍ�"){
						String insert = checkNull(((JComboBox)jc).getSelectedItem());
						DataCtrl.getInstance().getTrialTblData().UpdSyubetuNo(
								DataCtrl.getInstance().getTrialTblData().checkNullString(insert),
								DataCtrl.getInstance().getUserMstData().getDciUserid()
							);
					}


//ADD 2013/06/19 ogawa �yQP@30151�zNo.9 start
					//------------------- ��{���(����\�B)�̐Ӊ�� ---------------------
					if(komoku == "�̐Ӊ��"){
						//---------------------- �̐Ӊ�Ѓf�[�^ ----------------------------
						String selHanseki = (String)tb.getTrial3Panel().getCmbHanseki().getSelectedItem();

						String ins=null;

						//�̐Ӊ�Ѓf�[�^�R�[�h����
						HansekiData hansekiData = DataCtrl.getInstance().getHansekiData();

						for ( int i=0; i<hansekiData.getArtKaishaCd().size(); i++ ) {
							//��ЃR�[�h
							String hansekiCd = hansekiData.getArtKaishaCd().get(i).toString();
							//��Ж�
							String hansekiNm = hansekiData.getAryKaishaNm().get(i).toString();

							//�I��̐Ӊ�ЃR�[�h�̌��o
							if ( hansekiNm.equals(checkNull(selHanseki))) {
								ins = hansekiCd;
							}
						}
						//�f�[�^�}��
						DataCtrl.getInstance().getTrialTblData().UpdHanseki(
								DataCtrl.getInstance().getTrialTblData().checkNullInt(ins),
								DataCtrl.getInstance().getUserMstData().getDciUserid()
							);
					}

//ADD 2013/06/19 ogawa �yQP@30151�zNo.9 end
					//------------------- ��{���(����\�B)�S�����or�H�� ---------------------
					if(komoku == "�S�����" || komoku == "�S���H��"){

						//---------------------- ��Ѓf�[�^ ----------------------------
						String selKaisha = (String)tb.getTrial3Panel().getCmbKaisha().getSelectedItem();

						String insert=null;

						//��Ѓf�[�^�R�[�h����
						KaishaData kaishaData = DataCtrl.getInstance().getKaishaData();

						for ( int i=0; i<kaishaData.getArtKaishaCd().size(); i++ ) {
							//��ЃR�[�h
							String kaishaCd = kaishaData.getArtKaishaCd().get(i).toString();
							//��Ж�
							String kaishaNm = kaishaData.getAryKaishaNm().get(i).toString();
							//������
							String keta_genryo = kaishaData.getAryKaishaGenryo().get(i).toString();

							//�I����ЃR�[�h�̌��o
							if ( kaishaNm.equals(checkNull(selKaisha))) {
								insert = kaishaCd;

								//����������
								genryoCdArai(keta_genryo);

							}
						}
						//�f�[�^�}��
						DataCtrl.getInstance().getTrialTblData().UpdTantoKaisha(
								DataCtrl.getInstance().getTrialTblData().checkNullInt(insert),
								DataCtrl.getInstance().getUserMstData().getDciUserid()
							);

						//---------------------- �����f�[�^ -----------------------------
						String selKojo = (String)tb.getTrial3Panel().getCmbKojo().getSelectedItem();
						String kojo_insert = null;
						BushoData bushoData = DataCtrl.getInstance().getBushoData();
						//�����f�[�^�R�[�h����
						for (int i=0; i<bushoData.getArtBushoCd().size(); i++ ) {
							//�����R�[�h
							String bushoCd = bushoData.getArtBushoCd().get(i).toString();
							//������
							String bushoNm = bushoData.getAryBushoNm().get(i).toString();

							//�I�𕔏��R�[�h�̌��o
							if ( bushoNm.equals(checkNull(selKojo))) {
								kojo_insert = bushoCd;
							}
						}
						//�f�[�^�}��
						DataCtrl.getInstance().getTrialTblData().UpdTantoKojo(
								DataCtrl.getInstance().getTrialTblData().checkNullInt(kojo_insert),
								DataCtrl.getInstance().getUserMstData().getDciUserid()
							);

						//����������
						if(selKaisha != null && selKaisha.length() > 0){

							if(selKojo != null && selKojo.length() > 0){

								genryoNmArai();

							}

						}


					}
					//------------------------ �����w�� -----------------------------
					if(komoku == "�����w��"){

						//��������
						TableBase tblListHeader  = tb.getTrial1Panel().getTrial1().getListHeader();
						TableBase tblListHaigo  = tb.getTrial1Panel().getTrial1().getHaigoMeisai();
						TableBase tblListMeisai = tb.getTrial1Panel().getTrial1().getListMeisai();
						ArrayList aryHaigo = DataCtrl.getInstance().getTrialTblData().SearchHaigoData(0);
						int max = DataCtrl.getInstance().getTrialTblData().getIntMaxKotei();


						//���ݐݒ肳��Ă��鏬�����e�����R�[�h�擾
						PrototypeData PrototypeData = DataCtrl.getInstance().getTrialTblData().SearchShisakuhinData();
						String shosucd = PrototypeData.getStrShosu();

						//���ݐݒ肳��Ă��鏬�������擾
						int moto_val1 = 0;
						if(shosucd != null){
							moto_val1 = DataCtrl.getInstance().getLiteralDataShosu().selectLiteralVal1(Integer.parseInt(shosucd));
						}


						//��{���(����\�B)�w��̏������e�����R�[�h�擾
						String insert_shosu = null;
						int selectId = ((ComboBase)jc).getSelectedIndex();
						if(selectId > 0){
							insert_shosu = DataCtrl.getInstance().getLiteralDataShosu().selectLiteralCd(selectId-1);
						}

						//��{���(����\�B)�w��̏��������擾
						int kosin_val1 = 0;
						if(insert_shosu != null){
							kosin_val1 = DataCtrl.getInstance().getLiteralDataShosu().selectLiteralVal1(Integer.parseInt(insert_shosu));
						}

						//���s���t���O
						boolean blnExe = true;

						//�w�菬�������ݐݒ肳��Ă��鏬���l��菬�����ꍇ
						if(kosin_val1 < moto_val1){

							//�_�C�A���O�R���|�[�l���g�ݒ�
							JOptionPane jp = new JOptionPane();

//mod start --------------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.19
							//�m�F�_�C�A���O�\��
							int option = jp.showConfirmDialog(
									jp.getRootPane(),
//									"����"+kosin_val1+"�����傫���������͐؂�̂Ă��܂��B��낵���ł����H"
									"����"+kosin_val1+"�����傫���������͎l�̌ܓ�����܂��B��낵���ł����H"
									, "�m�F���b�Z�[�W"
									,JOptionPane.YES_NO_OPTION
									,JOptionPane.PLAIN_MESSAGE
								);
//mod end --------------------------------------------------------------------------------------

							//�u�͂��v����
						    if (option == JOptionPane.YES_OPTION){

						    	//���s����
						    	blnExe = true;

						    //�u�������v����
						    }else if (option == JOptionPane.NO_OPTION){

						    	//���s���Ȃ�
						    	blnExe = false;
						    }
						}

						//-------------------------------- ���s����ꍇ --------------------------------------
						if(blnExe){

							//�����_�}��
							DataCtrl.getInstance().getTrialTblData().UpdSyousuShitei(
									DataCtrl.getInstance().getTrialTblData().checkNullString(insert_shosu),
									DataCtrl.getInstance().getUserMstData().getDciUserid()
								);

							//�z�����׍s�������[�v
							//2011/06/13 QP@10181_No.29 TT T.Satoh Change Start -------------------------
							//for(int l=0; l<tblListHaigo.getRowCount()-max-8; l++){
							for(int l=0; l<tblListHaigo.getRowCount()-max-9; l++){
							//2011/06/13 QP@10181_No.29 TT T.Satoh Change End ---------------------------

								//�R���|�[�l���g�擾
								MiddleCellEditor selectMc = (MiddleCellEditor)tblListHaigo.getCellEditor(l, 2);
								DefaultCellEditor selectDc = (DefaultCellEditor)selectMc.getTableCellEditor(l);

								//�����s�̏ꍇ�ɏ���
								if(selectDc.getComponent() instanceof CheckboxBase){

									//�L�[���ڎ擾
									CheckboxBase CheckboxBase = (CheckboxBase)selectDc.getComponent();
									int koteiCd = Integer.parseInt(CheckboxBase.getPk1());
									int koteiSeq = Integer.parseInt(CheckboxBase.getPk2());

									//����w�b�_�[�񐔕����[�v
									for(int m=0; m<tblListHeader.getColumnCount(); m++){

										//�R���|�[�l���g�擾
										MiddleCellEditor selectMch = (MiddleCellEditor)tblListHeader.getCellEditor(0, m);
										DefaultCellEditor selectDch = (DefaultCellEditor)selectMch.getTableCellEditor(0);

										//�L�[���ڎ擾
										CheckboxBase CheckboxBaseh = (CheckboxBase)selectDch.getComponent();
										int ShisakuCd = Integer.parseInt(CheckboxBaseh.getPk1());

										//���֏���
										String insert_arai = (String)tblListMeisai.getValueAt(l, m);

										//��������
						    			if(insert_arai != null && insert_arai.length() > 0){

//mod start --------------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.19
						    				//���֏���
//						    				insert_arai = tb.getTrial1Panel().getTrial1().ShosuArai(insert_arai);
						    				insert_arai = tb.getTrial1Panel().getTrial1().ShosuAraiHulfUp(insert_arai);
//mod end --------------------------------------------------------------------------------------

							    			//�e�[�u���}��
						    				tblListMeisai.setValueAt(insert_arai, l, m);
						    			}

						    			//�f�[�^�}��
										DataCtrl.getInstance().getTrialTblData().UpdShisakuListRyo(
												ShisakuCd,
												koteiCd,
												koteiSeq,
												DataCtrl.getInstance().getTrialTblData().checkNullDecimal(insert_arai)
											);

										//�H�����v�v�Z
										tb.getTrial1Panel().getTrial1().koteiSum(m);

										//�����v�Z
										AutoKeisan();
									}
								}
							}
						}
						//------------------------------- ���s���Ȃ��ꍇ --------------------------------------
						else{

							//����B��ʂ̏����I��l�����ɖ߂�

							//���e�����R�[�h�擪0���ߏ���
							int strLng = shosucd.length();
							for(int i=strLng; i<3; i++){
								shosucd = "0" + shosucd;
							}

							//���e�������擾
							String litNm = DataCtrl.getInstance().getLiteralDataShosu().selectLiteralNm(shosucd);

							//�R���{�{�b�N�X�I��
							((ComboBase)jc).setSelectedItem(litNm);

						}
					}

//2011/05/11 QP@10181_No.42_49_72 TT T.Nishigawa Add Start -------------------------
					if(komoku == "�e�ʒP��"){
						//�ύX��擾
						String insert = null;
						String selectNm = (String) ((ComboBase)jc).getSelectedItem();
						insert = DataCtrl.getInstance().getLiteralDataTani().selectLiteralCd(selectNm);

						//�ύX�O�擾
						String  insert_moto = PrototypeData.getStrTani();

						//�ύX�O�ƕύX�オ�����ꍇ
						if(insert.equals(insert_moto)){

							//�����Ȃ�

						}
						//�ύX�O�ƕύX�オ�قȂ�ꍇ
						else{

							//�ύX��f�[�^�ݒ�
							DataCtrl.getInstance().getTrialTblData().UpdYouryoTani(
									DataCtrl.getInstance().getTrialTblData().checkNullString(insert),
									DataCtrl.getInstance().getUserMstData().getDciUserid()
								);

							//�����l�A�������Z�e�[�u���ݒ菈��
							setTp2_5TableHiju(0);
						}
					}
//2011/05/11 QP@10181_No.42_49_72 TT T.Nishigawa Add End --------------------------


				}
			}catch(ExceptionBase eb){
				DataCtrl.getInstance().PrintMessage(eb);

			}catch(Exception ex){
				ex.printStackTrace();

			}finally{
				//�e�X�g�\��
				//DataCtrl.getInstance().getTrialTblData().dispPrototype();
				//DataCtrl.getInstance().getTrialTblData().dispHaigo();
				//DataCtrl.getInstance().getTrialTblData().dispProtoList();

			}
	    }

		public void focusGained( FocusEvent e ){
//add start -------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.7
			try{
				tb.getTrial1Panel().getTrial1().AutoCopyKeisan();

			}catch(ExceptionBase eb){
				DataCtrl.getInstance().PrintMessage(eb);

			}catch(Exception ex){
				ex.printStackTrace();

			}finally{

			}
//add end   -------------------------------------------------------------------------------
	    }
	}

//2011/05/11 QP@10181_No.42_49_72 TT T.Nishigawa Add Start -------------------------
	/************************************************************************************
	 *
	 *  �����l�A�������Z�e�[�u���ݒ菈��
	 *    :  ���i��d�A������d�A�����[�U�ʁA�����[�U�ʂ�ݒ�
	 *   @author TT nishigawa
	 *   @flgColumnChenge �H���p�^�[���ɂ��\�����ڕύX�t���O
	 *                   �i0:���ڕύX���̏��������Ȃ�  1:���̑��E���H��1�t�E2�t  2:1�t�E2�t�˂��̑��E���H�j
	 *
	 ************************************************************************************/
	public void setTp2_5TableHiju(int flgColumnChenge) throws ExceptionBase{
		try{

			//���ݑI�𒆂̃^�u�擾
			int sel = tb.getSelectedIndex();

			//�H���p�^�[���擾
			String ptKotei = PrototypeData.getStrPt_kotei();

			//�e�ʒP�ʎ擾
			String yoryoTani = DataCtrl.getInstance().getTrialTblData().SearchShisakuhinData().getStrTani();

			//�e�ʒP�ʂ���Value1�擾
			String taniValue1 = "";
			if(yoryoTani == null || yoryoTani.length() == 0){

			}
			else{
				taniValue1 =  DataCtrl.getInstance().getLiteralDataTani().selectLiteralVal1(yoryoTani);
			}

			//��ݒ�i�e�[�u���j
			TableBase tp1Table = tb.getTrial1Panel().getTrial1().getListHeader();
			//�����l�̐��i��d�A������d��ݒ�i�e�[�u���j
			TableBase tp2Table = tb.getTrial2Panel().getTable();
			//�������Z�e�[�u��
			TableBase tp5Table = tb.getTrial5Panel().getTable();

			for(int i=0; i<tp1Table.getColumnCount(); i++){

				//�L�[����
//				MiddleCellEditor mceSeq = (MiddleCellEditor)tp2Table.getCellEditor(0, i);
//				DefaultCellEditor dceSeq = (DefaultCellEditor)mceSeq.getTableCellEditor(0);
//				TextboxBase chkSeq = (TextboxBase)dceSeq.getComponent();
//				int intSeq  = Integer.parseInt(chkSeq.getPk1());
				MiddleCellEditor mceSeq = (MiddleCellEditor)tp1Table.getCellEditor(0, i);
				DefaultCellEditor dceSeq = (DefaultCellEditor)mceSeq.getTableCellEditor(0);
				CheckboxBase chkSeq = (CheckboxBase)dceSeq.getComponent();
				int intSeq  = Integer.parseInt(chkSeq.getPk1());

				//�H���p�^�[�����u�󔒁v�̏ꍇ
				if(ptKotei == null || ptKotei.length() == 0){

					//���i��d�@�ҏW�s�i�����l�F�󔒁j
					if(sel == 1){
						setSeihinHijuEnabled(i,false,"");
					}
					DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuHiju(
		    				intSeq,
		    				DataCtrl.getInstance().getTrialTblData().checkNullString(null),
		    				DataCtrl.getInstance().getUserMstData().getDciUserid()
		    			);

					//������d�@�ҏW�s�i�����l�F�󔒁j
					if(sel == 1){
						setSuisoHijuEnabled(i,false,"");
					}
					DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuSuiHiju(
		    				intSeq,
		    				DataCtrl.getInstance().getTrialTblData().checkNullString(null),
		    				DataCtrl.getInstance().getUserMstData().getDciUserid()
		    			);

					//�u�[�U�ʐ����v�͕ҏW�s��
					if(sel == 3){
						setSuisoJutenEnabled(i,false,"");
					}
					DataCtrl.getInstance().getTrialTblData().UpdGenkaValue(
		    				intSeq,
		    				DataCtrl.getInstance().getTrialTblData().checkNullString(null),
		    				DataCtrl.getInstance().getUserMstData().getDciUserid(),
		    				JwsConstManager.JWS_COMPONENT_0134);

					//�u�[�U�ʖ����v�͕ҏW�s��
					if(sel == 3){
						setYusoJutenEnabled(i,false,"");
					}
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
							if(sel == 1){
								setSeihinHijuEnabled(i,true,"");
							}
							DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuHiju(
				    				intSeq,
				    				DataCtrl.getInstance().getTrialTblData().checkNullString(null),
				    				DataCtrl.getInstance().getUserMstData().getDciUserid()
				    			);

							//������d�@�ҏW�s�i�����l�F�󔒁j
							if(sel == 1){
								setSuisoHijuEnabled(i,false,"");
							}
							DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuSuiHiju(
				    				intSeq,
				    				DataCtrl.getInstance().getTrialTblData().checkNullString(null),
				    				DataCtrl.getInstance().getUserMstData().getDciUserid()
				    			);

							//�u�[�U�ʐ����v�͕ҏW�s�i�[�U�ʌv�Z�j
							String keisan1 = DataCtrl.getInstance().getTrialTblData().KeisanZyutenType1(intSeq);
							if(sel == 3){
								setSuisoJutenEnabled(i,false,keisan1);
							}
							tp5Table.setValueAt( keisan1, 3, i);
							DataCtrl.getInstance().getTrialTblData().UpdGenkaValue(
				    				intSeq,
				    				DataCtrl.getInstance().getTrialTblData().checkNullString(keisan1),
				    				DataCtrl.getInstance().getUserMstData().getDciUserid(),
				    				JwsConstManager.JWS_COMPONENT_0134);

							//�u�[�U�ʖ����v�͕ҏW�s��
							if(sel == 3){
								setYusoJutenEnabled(i,false,"");
							}
							DataCtrl.getInstance().getTrialTblData().UpdGenkaValue(
				    				intSeq,
				    				DataCtrl.getInstance().getTrialTblData().checkNullString(null),
				    				DataCtrl.getInstance().getUserMstData().getDciUserid(),
				    				JwsConstManager.JWS_COMPONENT_0135);

						}
						//�e�ʂ��ug�v�̏ꍇ
						else if(taniValue1.equals("2")){

							//���i��d�@�ҏW�s�i�����l�F1�j
							if(sel == 1){
								setSeihinHijuEnabled(i,false,"1");
							}
							DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuHiju(
				    				intSeq,
				    				DataCtrl.getInstance().getTrialTblData().checkNullString("1"),
				    				DataCtrl.getInstance().getUserMstData().getDciUserid()
				    			);

							//������d�@�ҏW�s�i�����l�F�󔒁j
							if(sel == 1){
								setSuisoHijuEnabled(i,false,"");
							}
							DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuSuiHiju(
				    				intSeq,
				    				DataCtrl.getInstance().getTrialTblData().checkNullString(null),
				    				DataCtrl.getInstance().getUserMstData().getDciUserid()
				    			);

							//�u�[�U�ʐ����v�͕ҏW�s�i�[�U�ʌv�Z�j
							String keisan1 = DataCtrl.getInstance().getTrialTblData().KeisanZyutenType1(intSeq);
							if(sel == 3){
								setSuisoJutenEnabled(i,false,keisan1);
							}
							DataCtrl.getInstance().getTrialTblData().UpdGenkaValue(
				    				intSeq,
				    				DataCtrl.getInstance().getTrialTblData().checkNullString(keisan1),
				    				DataCtrl.getInstance().getUserMstData().getDciUserid(),
				    				JwsConstManager.JWS_COMPONENT_0134);

							//�u�[�U�ʖ����v�͕ҏW�s��
							if(sel == 3){
								setYusoJutenEnabled(i,false,"");
							}
							DataCtrl.getInstance().getTrialTblData().UpdGenkaValue(
				    				intSeq,
				    				DataCtrl.getInstance().getTrialTblData().checkNullString(null),
				    				DataCtrl.getInstance().getUserMstData().getDciUserid(),
				    				JwsConstManager.JWS_COMPONENT_0135);

						}
						//�e�ʂ��u�󔒁v�̏ꍇ�iml,g�ȊO�̏ꍇ�j
						else{

							//���i��d�@�ҏW�s�i�����l�F�󔒁j
							if(sel == 1){
								setSeihinHijuEnabled(i,false,"");
							}
							DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuHiju(
				    				intSeq,
				    				DataCtrl.getInstance().getTrialTblData().checkNullString(null),
				    				DataCtrl.getInstance().getUserMstData().getDciUserid()
				    			);

							//������d�@�ҏW�s�i�����l�F�󔒁j
							if(sel == 1){
								setSuisoHijuEnabled(i,false,"");
							}
							DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuSuiHiju(
				    				intSeq,
				    				DataCtrl.getInstance().getTrialTblData().checkNullString(null),
				    				DataCtrl.getInstance().getUserMstData().getDciUserid()
				    			);

							//�u�[�U�ʐ����v�͕ҏW�s��
							if(sel == 3){
								setSuisoJutenEnabled(i,false,"");
							}
							DataCtrl.getInstance().getTrialTblData().UpdGenkaValue(
				    				intSeq,
				    				DataCtrl.getInstance().getTrialTblData().checkNullString(null),
				    				DataCtrl.getInstance().getUserMstData().getDciUserid(),
				    				JwsConstManager.JWS_COMPONENT_0134);

							//�u�[�U�ʖ����v�͕ҏW�s��
							if(sel == 3){
								setYusoJutenEnabled(i,false,"");
							}
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
							if(sel == 1){
								setSeihinHijuEnabled(i,false,keisan);
							}
							DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuHiju(
				    				intSeq,
				    				DataCtrl.getInstance().getTrialTblData().checkNullString(keisan),
				    				DataCtrl.getInstance().getUserMstData().getDciUserid()
				    			);

							//������d�@�ҏW�i�󔒁j
							if(sel == 1){
								setSuisoHijuEnabled(i,true,"");
							}
							DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuSuiHiju(
				    				intSeq,
				    				DataCtrl.getInstance().getTrialTblData().checkNullString(null),
				    				DataCtrl.getInstance().getUserMstData().getDciUserid()
				    			);

							//�u�[�U�ʐ����v�͕ҏW�s�i�v�Z�j
							String keisan1 = DataCtrl.getInstance().getTrialTblData().KeisanSuisoZyuten(intSeq);
							if(sel == 3){
								setSuisoJutenEnabled(i,false,keisan1);
							}
							DataCtrl.getInstance().getTrialTblData().UpdGenkaValue(
				    				intSeq,
				    				DataCtrl.getInstance().getTrialTblData().checkNullString(keisan1),
				    				DataCtrl.getInstance().getUserMstData().getDciUserid(),
				    				JwsConstManager.JWS_COMPONENT_0134);

							//�u�[�U�ʖ����v�͕ҏW�s�i�v�Z�j
							String keisan2 = DataCtrl.getInstance().getTrialTblData().KeisanYusoZyuten(intSeq);
							if(sel == 3){
								setYusoJutenEnabled(i,false,keisan2);
							}
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
							if(sel == 1){
								setSeihinHijuEnabled(i,false,"1");
							}
							DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuHiju(
				    				intSeq,
				    				DataCtrl.getInstance().getTrialTblData().checkNullString("1"),
				    				DataCtrl.getInstance().getUserMstData().getDciUserid()
				    			);

							//������d�@�ҏW�s�i�����l�F�󔒁j
							if(sel == 1){
								setSuisoHijuEnabled(i,false,"");
							}
							DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuSuiHiju(
				    				intSeq,
				    				DataCtrl.getInstance().getTrialTblData().checkNullString(null),
				    				DataCtrl.getInstance().getUserMstData().getDciUserid()
				    			);

							//�u�[�U�ʐ����v�͕ҏW�s�i�[�U�ʐ����v�Z�j
							String keisan1 = DataCtrl.getInstance().getTrialTblData().KeisanSuisoZyuten(intSeq);
							if(sel == 3){
								setSuisoJutenEnabled(i,false,keisan1);
							}
							DataCtrl.getInstance().getTrialTblData().UpdGenkaValue(
				    				intSeq,
				    				DataCtrl.getInstance().getTrialTblData().checkNullString(keisan1),
				    				DataCtrl.getInstance().getUserMstData().getDciUserid(),
				    				JwsConstManager.JWS_COMPONENT_0134);

							//�u�[�U�ʖ����v�͕ҏW�s�i�[�U�ʖ����v�Z�j
							String keisan2 = DataCtrl.getInstance().getTrialTblData().KeisanYusoZyuten(intSeq);
							if(sel == 3){
								setYusoJutenEnabled(i,false,keisan2);
							}
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
							if(sel == 1){
								setSeihinHijuEnabled(i,false,"");
							}
							DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuHiju(
				    				intSeq,
				    				DataCtrl.getInstance().getTrialTblData().checkNullString(null),
				    				DataCtrl.getInstance().getUserMstData().getDciUserid()
				    			);

							//������d�@�ҏW�s�i�����l�F�󔒁j
							if(sel == 1){
								setSuisoHijuEnabled(i,false,"");
							}
							DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuSuiHiju(
				    				intSeq,
				    				DataCtrl.getInstance().getTrialTblData().checkNullString(null),
				    				DataCtrl.getInstance().getUserMstData().getDciUserid()
				    			);

							//�u�[�U�ʐ����v�͕ҏW�s��
							if(sel == 3){
								setSuisoJutenEnabled(i,false,"");
							}
							DataCtrl.getInstance().getTrialTblData().UpdGenkaValue(
				    				intSeq,
				    				DataCtrl.getInstance().getTrialTblData().checkNullString(null),
				    				DataCtrl.getInstance().getUserMstData().getDciUserid(),
				    				JwsConstManager.JWS_COMPONENT_0134);

							//�u�[�U�ʖ����v�͕ҏW�s��
							if(sel == 3){
								setYusoJutenEnabled(i,false,"");
							}
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
						if(sel == 3){
							setSuisoJutenEnabled(i,true,"");
						}
						DataCtrl.getInstance().getTrialTblData().UpdGenkaValue(
			    				intSeq,
			    				DataCtrl.getInstance().getTrialTblData().checkNullString(null),
			    				DataCtrl.getInstance().getUserMstData().getDciUserid(),
			    				JwsConstManager.JWS_COMPONENT_0134);

						//�u�[�U�ʖ����v�͕ҏW��
						if(sel == 3){
							setYusoJutenEnabled(i,true,"");
						}
						DataCtrl.getInstance().getTrialTblData().UpdGenkaValue(
			    				intSeq,
			    				DataCtrl.getInstance().getTrialTblData().checkNullString(null),
			    				DataCtrl.getInstance().getUserMstData().getDciUserid(),
			    				JwsConstManager.JWS_COMPONENT_0135);

						//�e�ʂ��ug�v�̏ꍇ
						if(taniValue1.equals("2")){

							//���i��d�@�ҏW�s�i�����l�F1�j
							if(sel == 1){
								setSeihinHijuEnabled(i,false,"1");
							}
							DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuHiju(
				    				intSeq,
				    				DataCtrl.getInstance().getTrialTblData().checkNullString("1"),
				    				DataCtrl.getInstance().getUserMstData().getDciUserid()
				    			);

							//������d�@�ҏW�s�i�����l�F�󔒁j
							if(sel == 1){
								setSuisoHijuEnabled(i,false,"");
							}
							DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuSuiHiju(
				    				intSeq,
				    				DataCtrl.getInstance().getTrialTblData().checkNullString(null),
				    				DataCtrl.getInstance().getUserMstData().getDciUserid()
				    			);

						}
						//�e�ʂ��u�󔒁v�̏ꍇ�ig�ȊO�̏ꍇ�j
						else{

							//���i��d�@�ҏW�s�i�����l�F�󔒁j
							if(sel == 1){
								// MOD start 20121003 QP@20505
//								setSeihinHijuEnabled(i,false,"1");
								setSeihinHijuEnabled(i,false,"");
								// MOD start 20121003 QP@20505
							}
							DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuHiju(
				    				intSeq,
				    				DataCtrl.getInstance().getTrialTblData().checkNullString(null),
				    				DataCtrl.getInstance().getUserMstData().getDciUserid()
				    			);

							//������d�@�ҏW�s�i�����l�F�󔒁j
							if(sel == 1){
								setSuisoHijuEnabled(i,false,"");
							}
							DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuSuiHiju(
				    				intSeq,
				    				DataCtrl.getInstance().getTrialTblData().checkNullString(null),
				    				DataCtrl.getInstance().getUserMstData().getDciUserid()
				    			);
						}
					}
// ADD start 20121003 QP@20505 No.24
					// �e�T���v�����Ƃɉ�ʍ��ڃN���A�i�f�t�H���g�l��ݒ�j
					if (flgColumnChenge == 1){
						// �H���p�^�[�� ���̑��E���Hor�� �� 1�t�E2�t �ɕύX���ꂽ�ꍇ
						// ���x
						DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuToudo(
								intSeq,
								DataCtrl.getInstance().getTrialTblData().checkNullString(null),
			    				DataCtrl.getInstance().getUserMstData().getDciUserid()
			    			);
						// �S�x
						DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuNendo(
								intSeq,
								DataCtrl.getInstance().getTrialTblData().checkNullString(null),
			    				DataCtrl.getInstance().getUserMstData().getDciUserid()
			    			);
						// ���x
						DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuOndo(
								intSeq,
								DataCtrl.getInstance().getTrialTblData().checkNullString(null),
			    				DataCtrl.getInstance().getUserMstData().getDciUserid()
			    			);
						// ���_�|����
						DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuSousanBunseki(
								intSeq,
								DataCtrl.getInstance().getTrialTblData().checkNullString(null),
			    				DataCtrl.getInstance().getUserMstData().getDciUserid()
			    			);
						// �H���|����
						DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuSyokuenBunseki(
								intSeq,
								DataCtrl.getInstance().getTrialTblData().checkNullString(null),
			    				DataCtrl.getInstance().getUserMstData().getDciUserid()
			    			);
						// ���������t���[���e
						DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuFreeSuibunKasei(
								intSeq,
								DataCtrl.getInstance().getTrialTblData().checkNullString(null),
			    				DataCtrl.getInstance().getUserMstData().getDciUserid()
			    			);
						// �A���R�[���t���[���e
						DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuFreeAlchol(
								intSeq,
								DataCtrl.getInstance().getTrialTblData().checkNullString(null),
			    				DataCtrl.getInstance().getUserMstData().getDciUserid()
			    			);
						// �t���[1
						DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuFreeNaiyou_1(
								intSeq,
								DataCtrl.getInstance().getTrialTblData().checkNullString(null),
			    				DataCtrl.getInstance().getUserMstData().getDciUserid()
			    			);
						// �t���[2
						DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuFreeNaiyou_2(
								intSeq,
								DataCtrl.getInstance().getTrialTblData().checkNullString(null),
			    				DataCtrl.getInstance().getUserMstData().getDciUserid()
			    			);
						// �t���[3
						DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuFreeNaiyou_3(
								intSeq,
								DataCtrl.getInstance().getTrialTblData().checkNullString(null),
			    				DataCtrl.getInstance().getUserMstData().getDciUserid()
			    			);

					}else if (flgColumnChenge == 2){
						// �H���p�^�[�� 1�t�E2�t �� ���̑��E���Hor�� �ɕύX���ꂽ�ꍇ
						// �S�x�t���[
						DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuFreeNendo(
								intSeq,
								DataCtrl.getInstance().getTrialTblData().checkNullString(null),
			    				DataCtrl.getInstance().getUserMstData().getDciUserid()
			    			);
						// ���x�t���[
						DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuFreeOndo(
								intSeq,
								DataCtrl.getInstance().getTrialTblData().checkNullString(null),
			    				DataCtrl.getInstance().getUserMstData().getDciUserid()
			    			);
						// �t���[1
						DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuFreeNaiyou_1(
								intSeq,
								DataCtrl.getInstance().getTrialTblData().checkNullString(null),
			    				DataCtrl.getInstance().getUserMstData().getDciUserid()
			    			);
						// �t���[2
						DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuFreeNaiyou_2(
								intSeq,
								DataCtrl.getInstance().getTrialTblData().checkNullString(null),
			    				DataCtrl.getInstance().getUserMstData().getDciUserid()
			    			);
						// �t���[3
						DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuFreeNaiyou_3(
								intSeq,
								DataCtrl.getInstance().getTrialTblData().checkNullString(null),
			    				DataCtrl.getInstance().getUserMstData().getDciUserid()
			    			);
						// �t���[4
						DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuFreeNaiyou_4(
								intSeq,
								DataCtrl.getInstance().getTrialTblData().checkNullString(null),
			    				DataCtrl.getInstance().getUserMstData().getDciUserid()
			    			);
						// �t���[5
						DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuFreeNaiyou_5(
								intSeq,
								DataCtrl.getInstance().getTrialTblData().checkNullString(null),
			    				DataCtrl.getInstance().getUserMstData().getDciUserid()
			    			);
						// �t���[6
						DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuFreeNaiyou_6(
								intSeq,
								DataCtrl.getInstance().getTrialTblData().checkNullString(null),
			    				DataCtrl.getInstance().getUserMstData().getDciUserid()
			    			);
					}
// ADD end 20121003 QP@20505 No.24
				}
// ADD start 20121003 QP@20505 No.24
				// ��ʍ��ڂ̃N���A�i�f�t�H���g�l��ݒ�j
				// ADD start 20130226 QP@20505 ������̏C��
				if (flgColumnChenge == 0){
					// �H���p�^�[�� �� �� ���̑��E���H �ɕύX���ꂽ�ꍇ
					// ���������t���[�^�C�g��
					DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuFreeTitle_SuibunKasei(
		    				"��������",
		    				DataCtrl.getInstance().getUserMstData().getDciUserid()
		    			);
					// �A���R�[���t���[�^�C�g��
					DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuFreeTitle_Alchol(
		    				"�A���R�[��",
		    				DataCtrl.getInstance().getUserMstData().getDciUserid()
		    			);
				}else
				// ADD end 20130226 QP@20505 ������̏C��
				if (flgColumnChenge == 1){
					// ���������t���[�^�C�g��
					DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuFreeTitle_SuibunKasei(
// MOD start 20130225 QP@20505 ������̏C��
//// MOD start 20121128 QP@20505 �ۑ�No.11
////		    				"��������",
//		    				"",
//// MOD end 20121128 QP@20505 �ۑ�No.11
		    				"��������",
// MOD end 20130225 QP@20505 ������̏C��
		    				DataCtrl.getInstance().getUserMstData().getDciUserid()
		    			);
					// �A���R�[���t���[�^�C�g��
					DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuFreeTitle_Alchol(
// MOD start 20130225 QP@20505 ������̏C��
//// MOD start 20121128 QP@20505 �ۑ�No.11
////		    				"�A���R�[��",
//		    				"",
//// MOD end 20121128 QP@20505 �ۑ�No.11
		    				"�A���R�[��",
// MOD end 20130225 QP@20505 ������̏C��
		    				DataCtrl.getInstance().getUserMstData().getDciUserid()
		    			);

					// ADD start 20130226 QP@20505 ������̏C��
					// �S�x�t���[�^�C�g��
					DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuFreeTitle_Nendo(
		    				"�S�x",
		    				DataCtrl.getInstance().getUserMstData().getDciUserid()
		    			);
					// ���x�t���[�^�C�g��
					DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuFreeTitle_Ondo(
		    				"���x�i���j",
		    				DataCtrl.getInstance().getUserMstData().getDciUserid()
		    			);
					// ADD end 20130226 QP@20505 ������̏C��

					// ���x�|�o��Flg
					DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuToudoFg(
		    				0,
		    				DataCtrl.getInstance().getUserMstData().getDciUserid()
		    			);
					// �S�x�E���x�|�o��Flg
					DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuNendoFg(
		    				0,
		    				DataCtrl.getInstance().getUserMstData().getDciUserid()
		    			);
					DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuOndoFg(
		    				0,
		    				DataCtrl.getInstance().getUserMstData().getDciUserid()
		    			);
					// ���_�|���́|�o��Flg
					DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuSousanBunsekiFg(
		    				0,
		    				DataCtrl.getInstance().getUserMstData().getDciUserid()
		    			);
					// �H���|���́|�o��Flg
					DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuSyokuenBunsekiFg(
		    				0,
		    				DataCtrl.getInstance().getUserMstData().getDciUserid()
		    			);
					// ���������t���[�|�o��Flg
					DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuFreeSuibunKaseiFg(
		    				0,
		    				DataCtrl.getInstance().getUserMstData().getDciUserid()
		    			);
					// �A���R�[���t���[�|�o��Flg
					DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuFreeAlcholFg(
		    				0,
		    				DataCtrl.getInstance().getUserMstData().getDciUserid()
		    			);
					// �t���[1�^�C�g��
					DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuFreeTitle_1(
							DataCtrl.getInstance().getTrialTblData().checkNullString(null),
		    				DataCtrl.getInstance().getUserMstData().getDciUserid()
		    			);
					// �t���[1�|�o��Flg
					DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuFreeFg_1(
		    				0,
		    				DataCtrl.getInstance().getUserMstData().getDciUserid()
		    			);
					// �t���[2�^�C�g��
					DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuFreeTitle_2(
							DataCtrl.getInstance().getTrialTblData().checkNullString(null),
		    				DataCtrl.getInstance().getUserMstData().getDciUserid()
		    			);
					// �t���[2�|�o��Flg
					DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuFreeFg_2(
		    				0,
		    				DataCtrl.getInstance().getUserMstData().getDciUserid()
		    			);
					// �t���[3�^�C�g��
					DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuFreeTitle_3(
							DataCtrl.getInstance().getTrialTblData().checkNullString(null),
		    				DataCtrl.getInstance().getUserMstData().getDciUserid()
		    			);
					// �t���[3�|�o��Flg
					DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuFreeFg_3(
		    				0,
		    				DataCtrl.getInstance().getUserMstData().getDciUserid()
		    			);
					if(sel == 1){
						tb.getTrial2Panel().afterOtherTypeDispClear();
					}

				}else if (flgColumnChenge == 2){

					// �S�x�t���[�^�C�g��
					DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuFreeTitle_Nendo(
// MOD start 20130225 QP@20505 ������̏C��
//// MOD start 20121128 QP@20505 �ۑ�No.11
////		    				"�S�x",
//		    				"",
//// MOD end 20121128 QP@20505 �ۑ�No.11
		    				"�S�x",
// MOD end 20130225 QP@20505 ������̏C��
		    				DataCtrl.getInstance().getUserMstData().getDciUserid()
		    			);
					// ���x�t���[�^�C�g��
					DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuFreeTitle_Ondo(
// MOD start 20130225 QP@20505 ������̏C��
//// MOD start 20121128 QP@20505 �ۑ�No.11
////		    				"���x",
//		    				"",
//// MOD end 20121128 QP@20505 �ۑ�No.11
		    				"���x�i���j",
// MOD end 20130225 QP@20505 ������̏C��
		    				DataCtrl.getInstance().getUserMstData().getDciUserid()
		    			);
					// �S�x�t���[�E���x�t���[�|�o��Flg
					DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuFreeNendoFg(
		    				0,
		    				DataCtrl.getInstance().getUserMstData().getDciUserid()
		    			);
					// ADD start 20130226 QP@20505 ������̏C��
					// ���������t���[�^�C�g��
					DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuFreeTitle_SuibunKasei(
		    				"��������",
		    				DataCtrl.getInstance().getUserMstData().getDciUserid()
		    			);
					// �A���R�[���t���[�^�C�g��
					DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuFreeTitle_Alchol(
		    				"�A���R�[��",
		    				DataCtrl.getInstance().getUserMstData().getDciUserid()
		    			);
					// ADD end 20130226 QP@20505 ������̏C��
					// �t���[1�^�C�g��
					DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuFreeTitle_1(
							DataCtrl.getInstance().getTrialTblData().checkNullString(null),
		    				DataCtrl.getInstance().getUserMstData().getDciUserid()
		    			);
					// �t���[1�|�o��Flg
					DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuFreeFg_1(
		    				0,
		    				DataCtrl.getInstance().getUserMstData().getDciUserid()
		    			);
					// �t���[2�^�C�g��
					DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuFreeTitle_2(
							DataCtrl.getInstance().getTrialTblData().checkNullString(null),
		    				DataCtrl.getInstance().getUserMstData().getDciUserid()
		    			);
					// �t���[2�|�o��Flg
					DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuFreeFg_2(
		    				0,
		    				DataCtrl.getInstance().getUserMstData().getDciUserid()
		    			);
					// �t���[3�^�C�g��
					DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuFreeTitle_3(
							DataCtrl.getInstance().getTrialTblData().checkNullString(null),
		    				DataCtrl.getInstance().getUserMstData().getDciUserid()
		    			);
					// �t���[3�|�o��Flg
					DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuFreeFg_3(
		    				0,
		    				DataCtrl.getInstance().getUserMstData().getDciUserid()
		    			);
					// �t���[4�^�C�g��
					DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuFreeTitle_4(
							DataCtrl.getInstance().getTrialTblData().checkNullString(null),
		    				DataCtrl.getInstance().getUserMstData().getDciUserid()
		    			);
					// �t���[4�|�o��Flg
					DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuFreeFg_4(
		    				0,
		    				DataCtrl.getInstance().getUserMstData().getDciUserid()
		    			);
					// �t���[5�^�C�g��
					DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuFreeTitle_5(
							DataCtrl.getInstance().getTrialTblData().checkNullString(null),
		    				DataCtrl.getInstance().getUserMstData().getDciUserid()
		    			);
					// �t���[5�|�o��Flg
					DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuFreeFg_5(
		    				0,
		    				DataCtrl.getInstance().getUserMstData().getDciUserid()
		    			);
					// �t���[6�^�C�g��
					DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuFreeTitle_6(
							DataCtrl.getInstance().getTrialTblData().checkNullString(null),
		    				DataCtrl.getInstance().getUserMstData().getDciUserid()
		    			);
					// �t���[6�|�o��Flg
					DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuFreeFg_6(
		    				0,
		    				DataCtrl.getInstance().getUserMstData().getDciUserid()
		    			);

					if(sel == 1){
						tb.getTrial2Panel().afterEkiTypeDispClear();
					}
				}
// ADD end 20121003 QP@20505 No.24

				//�D�̕\���l�ݒ�
				if(sel == 3){
					tb.getTrial5Panel().updDispValues(intSeq, i);
				}

			}
		}
		catch(Exception e){
			e.printStackTrace();

		}
	}
	private void setSeihinHijuEnabled(int col,boolean enabled,String val){
		try{
// ADD start 20121003 QP@20505 No.24
			//�H���p�^�[���擾
			String ptKotei = PrototypeData.getStrPt_kotei();
			String taniValue = "0";
			int numRow = 12;
			if(ptKotei == null || ptKotei.length() == 0){
				taniValue = "0";
			}
			else{
				taniValue = DataCtrl.getInstance().getLiteralDataKoteiPtn().selectLiteralVal1(ptKotei);
			}
			if (taniValue.equals(JwsConstManager.JWS_KOTEITYPE_1) || taniValue.equals(JwsConstManager.JWS_KOTEITYPE_2)){
				numRow = 9;
			}
// ADD end 20121003 QP@20505 No.24
			//�����l�̐��i��d�A������d��ݒ�i�e�[�u���j
			TableBase tp2Table = tb.getTrial2Panel().getTable();

			//���i��d�R���|�[�l���g�擾
// MOD start 20121003 QP@20505 No.24
//			MiddleCellEditor tp2TableMC_h = (MiddleCellEditor)tp2Table.getCellEditor(12, col);
//			DefaultCellEditor tp2TableDC_h = (DefaultCellEditor)tp2TableMC_h.getTableCellEditor(12);
			MiddleCellEditor tp2TableMC_h = (MiddleCellEditor)tp2Table.getCellEditor(numRow, col);
			DefaultCellEditor tp2TableDC_h = (DefaultCellEditor)tp2TableMC_h.getTableCellEditor(numRow);
// MOD end 20121003 QP@20505 No.24
			TextboxBase tp2TableTB_h = (TextboxBase)tp2TableDC_h.getComponent();

			//���i��d
			tp2TableTB_h.setEditable(enabled);
// MOD start 20121003 QP@20505 No.24
//			tp2Table.setValueAt( val, 12, col);
			tp2Table.setValueAt( val, numRow, col);
// MOD end 20121003 QP@20505 No.24

			//�w�i�F�ύX
// MOD start 20121003 QP@20505 No.24
//			MiddleCellRenderer selectMr = (MiddleCellRenderer)tp2Table.getCellRenderer(12, col);
//			TextFieldCellRenderer selectCer = (TextFieldCellRenderer)selectMr.getTableCellRenderer(12);
			MiddleCellRenderer selectMr = (MiddleCellRenderer)tp2Table.getCellRenderer(numRow, col);
			TextFieldCellRenderer selectCer = (TextFieldCellRenderer)selectMr.getTableCellRenderer(numRow);
// MOD end 20121003 QP@20505 No.24
			if(enabled){
				tp2TableTB_h.setBackground(Color.white);
				selectCer.setColor(Color.white);
			}
			else{
				tp2TableTB_h.setBackground(JwsConstManager.JWS_DISABLE_COLOR);
				selectCer.setColor(JwsConstManager.JWS_DISABLE_COLOR);
			}

		}catch(Exception e){

		}
	}
	private void setSuisoHijuEnabled(int col,boolean enabled,String val){
		try{
// ADD start 20121003 QP@20505 No.24
			//�H���p�^�[���擾
			String ptKotei = PrototypeData.getStrPt_kotei();
			String taniValue = "0";
			int numRow = 13;
			if(ptKotei == null || ptKotei.length() == 0){
				taniValue = "0";
			}
			else{
				taniValue = DataCtrl.getInstance().getLiteralDataKoteiPtn().selectLiteralVal1(ptKotei);
			}
			if (taniValue.equals(JwsConstManager.JWS_KOTEITYPE_1) || taniValue.equals(JwsConstManager.JWS_KOTEITYPE_2)){
				numRow = 10;
			}
// ADD end 20121003 QP@20505 No.24

			//�����l�̐��i��d�A������d��ݒ�i�e�[�u���j
			TableBase tp2Table = tb.getTrial2Panel().getTable();

			//������d�R���|�[�l���g�擾
// MOD start 20121003 QP@20505 No.24
//			MiddleCellEditor tp2TableMC_s = (MiddleCellEditor)tp2Table.getCellEditor(13, col);
//			DefaultCellEditor tp2TableDC_s = (DefaultCellEditor)tp2TableMC_s.getTableCellEditor(13);
			MiddleCellEditor tp2TableMC_s = (MiddleCellEditor)tp2Table.getCellEditor(numRow, col);
			DefaultCellEditor tp2TableDC_s = (DefaultCellEditor)tp2TableMC_s.getTableCellEditor(numRow);
// MOD end 20121003 QP@20505 No.24
			TextboxBase tp2TableTB_s = (TextboxBase)tp2TableDC_s.getComponent();

			//������d
			tp2TableTB_s.setEditable(enabled);
// MOD start 20121003 QP@20505 No.24
//			tp2Table.setValueAt( val, 13, col);
			tp2Table.setValueAt( val, numRow, col);
// MOD end 20121003 QP@20505 No.24

			//�w�i�F�ύX
// MOD start 20121003 QP@20505 No.24
//			MiddleCellRenderer selectMr = (MiddleCellRenderer)tp2Table.getCellRenderer(13, col);
//			TextFieldCellRenderer selectCer = (TextFieldCellRenderer)selectMr.getTableCellRenderer(13);
			MiddleCellRenderer selectMr = (MiddleCellRenderer)tp2Table.getCellRenderer(numRow, col);
			TextFieldCellRenderer selectCer = (TextFieldCellRenderer)selectMr.getTableCellRenderer(numRow);
// MOD end 20121003 QP@20505 No.24
			if(enabled){
				tp2TableTB_s.setBackground(Color.white);
				selectCer.setColor(Color.white);
			}
			else{
				tp2TableTB_s.setBackground(JwsConstManager.JWS_DISABLE_COLOR);
				selectCer.setColor(JwsConstManager.JWS_DISABLE_COLOR);
			}

		}catch(Exception e){

		}
	}
	private void setSuisoJutenEnabled(int col,boolean enabled,String val){
		try{
			//�������Z�e�[�u��
			TableBase tp5Table = tb.getTrial5Panel().getTable();

			//�����[�U�ʃR���|�[�l���g�擾
			MiddleCellEditor tp5TableMC_s = (MiddleCellEditor)tp5Table.getCellEditor(3, col);
			DefaultCellEditor tp5TableDC_s = (DefaultCellEditor)tp5TableMC_s.getTableCellEditor(3);
			TextboxBase tp5TableTB_s = (TextboxBase)tp5TableDC_s.getComponent();

			//�[�U�ʐ���
			tp5TableTB_s.setEditable(enabled);
			tp5Table.setValueAt( val, 3, col);

			//�w�i�F�ύX
			MiddleCellRenderer selectMr = (MiddleCellRenderer)tp5Table.getCellRenderer(3, col);
			TextFieldCellRenderer selectCer = (TextFieldCellRenderer)selectMr.getTableCellRenderer(3);
			if(enabled){
				tp5TableTB_s.setBackground(JwsConstManager.SHISAKU_ITEM_COLOR2);
				selectCer.setColor(JwsConstManager.SHISAKU_ITEM_COLOR2);
			}
			else{
				tp5TableTB_s.setBackground(JwsConstManager.JWS_DISABLE_COLOR);
				selectCer.setColor(JwsConstManager.JWS_DISABLE_COLOR);
			}

		}catch(Exception e){

		}
	}
	private void setYusoJutenEnabled(int col,boolean enabled,String val){
		try{
			//�������Z�e�[�u��
			TableBase tp5Table = tb.getTrial5Panel().getTable();

			//�����[�U�ʃR���|�[�l���g�擾
			MiddleCellEditor tp5TableMC_y = (MiddleCellEditor)tp5Table.getCellEditor(4, col);
			DefaultCellEditor tp5TableDC_y = (DefaultCellEditor)tp5TableMC_y.getTableCellEditor(4);
			TextboxBase tp5TableTB_y = (TextboxBase)tp5TableDC_y.getComponent();

			//�[�U�ʖ���
			tp5TableTB_y.setEditable(enabled);
			tp5Table.setValueAt( val, 4, col);

			//�w�i�F�ύX
			MiddleCellRenderer selectMr = (MiddleCellRenderer)tp5Table.getCellRenderer(4, col);
			TextFieldCellRenderer selectCer = (TextFieldCellRenderer)selectMr.getTableCellRenderer(4);
			if(enabled){
				tp5TableTB_y.setBackground(JwsConstManager.SHISAKU_ITEM_COLOR2);
				selectCer.setColor(JwsConstManager.SHISAKU_ITEM_COLOR2);
			}
			else{
				tp5TableTB_y.setBackground(JwsConstManager.JWS_DISABLE_COLOR);
				selectCer.setColor(JwsConstManager.JWS_DISABLE_COLOR);
			}

		}catch(Exception e){

		}
	}
//2011/05/11 QP@10181_No.42_49_72 TT T.Nishigawa Add End -------------------------

	/************************************************************************************
	 *
	 *  �������i�����R�[�h�j�􂢊���
	 *    :  ��Ўw��̌����ɂČ����R�[�h�̐��ւ��s��
	 *   @author TT nishigawa
	 *   @param �w�茅��
	 *
	 ************************************************************************************/
	public void genryoCdArai(String keta) throws ExceptionBase{
		try{

			//�w�茅��
			String setLen = JwsConstManager.JWS_KETA_GENRYO;

			//�z�����׃e�[�u���擾
			TableBase HaigoMeisai = tb.getTrial1Panel().getTrial1().getHaigoMeisai();

			//�z�����׃e�[�u���@�s���[�v
			for(int i=0; i<HaigoMeisai.getRowCount(); i++){

				//�R���|�[�l���g�擾
				MiddleCellEditor selectMc = (MiddleCellEditor)HaigoMeisai.getCellEditor(i, 3);
				DefaultCellEditor selectDc = (DefaultCellEditor)selectMc.getTableCellEditor(i);

				//�����R�[�h�̏ꍇ
				if(selectDc.getComponent() instanceof HankakuTextbox){


					//�L�[���ڎ擾
					MiddleCellEditor selectMcKey = (MiddleCellEditor)HaigoMeisai.getCellEditor(i, 2);
					DefaultCellEditor selectDcKey = (DefaultCellEditor)selectMcKey.getTableCellEditor(i);
					CheckboxBase cb = (CheckboxBase)selectDcKey.getComponent();
					int koteiCd = Integer.parseInt(cb.getPk1());
					int koteiSeq = Integer.parseInt(cb.getPk2());

					//�����R�[�h�擾
					String strCd = (String)HaigoMeisai.getValueAt(i, 3);

					//�ݒ茴���R�[�h������
					String setCd = null;

					//�R���|�[�l���g�擾
					HankakuTextbox Comp = (HankakuTextbox)selectDc.getComponent();


					//�w�茅����NULL�̏ꍇ
					if(keta == null){



					}
					//�w�茅����NULL�ȊO�̏ꍇ
					else{

						setLen = keta;

					}

					//���֑O�̌����R�[�h�����擾
					int keta_moto = 0;
					if(strCd != null){
						keta_moto = strCd.length();
					}

					//���֌�̌����R�[�h�������������Ȃ�ꍇ
					if(keta_moto > Integer.parseInt(setLen)){

						//�R�[�h��Null��ݒ�
						setCd = null;

					}
					//���֌�̌����R�[�h���������������A�傫���Ȃ�ꍇ
					else{

						//�R�[�h�ɐ��֑O�̒l��ݒ�
						setCd = strCd;

					}

					//�R�����g�s�̔���
					boolean comFlg = DataCtrl.getInstance().getTrialTblData().commentChk(strCd, Comp.getIntMaxLength());

					//�R�����g�s�̏ꍇ
					if(comFlg){

						//�R�����g�s�쐬
						setCd = commentSet(Integer.parseInt(setLen));

					}

					//�����ݒ�
					Comp.setIntMaxLength(Integer.parseInt(setLen));

					//�e�[�u���l�ɐ��֌�̃R�[�h��ݒ�
					HaigoMeisai.setValueAt(setCd, i, 3);

					//�z���f�[�^�F�����R�[�h�ɐ��֌�̃R�[�h��ݒ�
					DataCtrl.getInstance().getTrialTblData().UpdHaigoGenryoCd(
							koteiCd,
							koteiSeq,
							setCd,
							DataCtrl.getInstance().getUserMstData().getDciUserid()
						);


				}

			}

		}catch(Exception e){

			e.printStackTrace();

		}finally{

		}
	}

	/************************************************************************************
	 *
	 *  �R�����g�s�쐬
	 *    :  �w�蕶����A�w�茅���ɂăR�����g�s���쐬����i�w�茅���S�Ă�9��}���j
	 *   @author TT nishigawa
	 *
	 ************************************************************************************/
	public String commentSet(int intKeta){

		//�߂�l������
		String ret = "";

		try{

			//�w�茅�����[�v
			for(int i=0; i<intKeta; i++){

				//�����u9�v�����Z
				ret = ret + "9";

			}


		}catch(Exception e){

			e.printStackTrace();

		}finally{

		}

		//�ԋp
		return ret;
	}


	/************************************************************************************
	 *
	 *  �������A���/�H��i�����j�􂢊���
	 *    :  �������A���/�H��i�����j�􂢊������s��
	 *   @author TT nishigawa
	 *
	 ************************************************************************************/
	private void genryoNmArai() throws ExceptionBase{
		try{
			//------------------- �z���\(����\�@)���������� ------------------------
			TableBase HaigoMeisai = tb.getTrial1Panel().getTrial1().getHaigoMeisai();

			//�z���f�[�^�擾
			ArrayList retuData = DataCtrl.getInstance().getTrialTblData().SearchHaigoData(0);

			//�ő�H�����擾
			int maxKotei = DataCtrl.getInstance().getTrialTblData().getIntMaxKotei();

			//�􂢑ւ��ʐM����
			ArrayList aryHaigoArai = conJW070();

			//�\������
			TableBase dispHaigoMeisai = tb.getTrial1Panel().getTrial1().getHaigoMeisai();
			//2011/06/13 QP@10181_No.29 TT T.Satoh Change Start -------------------------
			//for(int k=0; k<dispHaigoMeisai.getRowCount()-maxKotei-8; k++){
			for(int k=0; k<dispHaigoMeisai.getRowCount()-maxKotei-9; k++){
			//2011/06/13 QP@10181_No.29 TT T.Satoh Change End ---------------------------

				//�R���|�[�l���g�擾
				MiddleCellEditor selectMc = (MiddleCellEditor)dispHaigoMeisai.getCellEditor(k, 2);
				DefaultCellEditor selectDc = (DefaultCellEditor)selectMc.getTableCellEditor(k);

				//�����s�̏ꍇ�ɏ���
				if(selectDc.getComponent() instanceof CheckboxBase){
					CheckboxBase CheckboxBase = (CheckboxBase)selectDc.getComponent();

					//�L�[���ڎ擾
					int koteiCd = Integer.parseInt(CheckboxBase.getPk1());
					int koteiSeq = Integer.parseInt(CheckboxBase.getPk2());

					//�ʐM�擾�f�[�^���[�v
					for(int l=0; l<aryHaigoArai.size(); l++){

						//�����f�[�^�擾
						MixedData md = (MixedData)aryHaigoArai.get(l);

						if(md.getIntGenryoNo() == k){

							//�������̎擾
							String insertNm = md.getStrGenryoNm();

							//2011/04/28 QP@10181_No.5 TT T.Satoh Add Start -------------------------
							//�����R�[�h�擾
							String insertCd = md.getStrGenryoCd();
							//2011/04/28 QP@10181_No.5 TT T.Satoh Add End ---------------------------

							if(insertNm != null && insertNm.length() > 0){

								//�\���p������
								String strDispGenryo = insertNm;

								//�R�����g�s�̔���
								int keta = DataCtrl.getInstance().getTrialTblData().getKaishaGenryo();
								boolean comFlg = DataCtrl.getInstance().getTrialTblData().commentChk(md.getStrGenryoCd(), keta);

								//�����R�[�h���R�����g�s�̏ꍇ
								if(insertNm != null && insertNm.length()>0){

									if(md.getStrGenryoCd() != null && comFlg){

										//�u�����v�L��������ꍇ
										if(strDispGenryo.substring(0, 1).equals(JwsConstManager.JWS_MARK_0001) ||
												strDispGenryo.substring(0, 1).equals(JwsConstManager.JWS_MARK_0002)){

											//���L���폜
											strDispGenryo = strDispGenryo.substring(1);
											if(strDispGenryo.length() == 0){
												strDispGenryo = null;
											}
										}
									}
								}

//mod start -------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.33,34
								//�ҏW�ۃ`�F�b�N
								boolean chk = DataCtrl.getInstance().getTrialTblData().checkListHenshuOkFg(0, koteiCd, koteiSeq);

								//�ҏW�\�̏ꍇ�F��������
								if(chk){
									//2011/04/28 QP@10181_No.5 TT T.Satoh Add Start -------------------------
									//�����R�[�h��NULL�ł͂Ȃ��ꍇ
									if (insertCd != null) {
										//�����R�[�h��1�����ڂ��uN�v�ł͂Ȃ��ꍇ
										if (!insertCd.substring(0, 1).equals("N")) {
									//2011/04/28 QP@10181_No.5 TT T.Satoh Add End ---------------------------
											//�\���l�ݒ�
											dispHaigoMeisai.setValueAt(strDispGenryo, k, 4);

											//������
											DataCtrl.getInstance().getTrialTblData().UpdHaigoGenryoMei(
													koteiCd,
													koteiSeq,
													strDispGenryo,
													DataCtrl.getInstance().getUserMstData().getDciUserid());
									//2011/04/28 QP@10181_No.5 TT T.Satoh Add Start -------------------------
										}
										else{
											//�V�K�����Ł��̏ꍇ
											if(strDispGenryo.substring(0, 1).equals(JwsConstManager.JWS_MARK_0001)){
												//�\���l�ݒ�
												dispHaigoMeisai.setValueAt(strDispGenryo, k, 4);

												//������
												DataCtrl.getInstance().getTrialTblData().UpdHaigoGenryoMei(
														koteiCd,
														koteiSeq,
														strDispGenryo,
														DataCtrl.getInstance().getUserMstData().getDciUserid());
											}
											else{
												//�ݒ�O�̌������擾
												String genryoNm_moto = DataCtrl.getInstance().getTrialTblData().SearchHaigoGenryoMei(koteiCd,koteiSeq);

												//�ݒ�O�̌�������NULL�łȂ��ꍇ
												if(genryoNm_moto != null){

													//�u���v�L��������ꍇ
													if(genryoNm_moto.substring(0, 1).equals(JwsConstManager.JWS_MARK_0001)){
														//���L���폜
														genryoNm_moto = genryoNm_moto.substring(1);
														if(genryoNm_moto.length() == 0){
															genryoNm_moto = null;
														}

														//�\���l�ݒ�
														dispHaigoMeisai.setValueAt(genryoNm_moto, k, 4);

														//������
														DataCtrl.getInstance().getTrialTblData().UpdHaigoGenryoMei(
																koteiCd,
																koteiSeq,
																genryoNm_moto,
																DataCtrl.getInstance().getUserMstData().getDciUserid());
													}
												}
											}
										}
									}
									//2011/04/28 QP@10181_No.5 TT T.Satoh Add End ---------------------------

									//���CD
									DataCtrl.getInstance().getTrialTblData().UpdHaigoKaishaCd(
											koteiCd,
											koteiSeq,
											md.getIntKaishaCd(),
											DataCtrl.getInstance().getUserMstData().getDciUserid());

									//����CD�i�H��CD�j
									DataCtrl.getInstance().getTrialTblData().UpdHaigoKojoCd(
											koteiCd,
											koteiSeq,
											md.getIntBushoCd(),
											DataCtrl.getInstance().getUserMstData().getDciUserid());

									//2011/04/26 QP@10181_No.73 TT T.Satoh Add Start -------------------------
									//�P��
//									//�\���l�ݒ�
//									dispHaigoMeisai.setValueAt(md.getDciTanka(), k, 5);
//									//�e�[�u���ݒ�
//									DataCtrl.getInstance().getTrialTblData().UpdHaigoGenryoTanka(
//											koteiCd,
//											koteiSeq,
//											md.getDciTanka(),
//											DataCtrl.getInstance().getUserMstData().getDciUserid());

									//�\���l�ݒ�
									dispHaigoMeisai.setValueAt(md.getDciTanka(), k, 5);

									//�e�[�u���ݒ�
									DataCtrl.getInstance().getTrialTblData().UpdHaigoGenryoTanka(
											koteiCd,
											koteiSeq,
											md.getDciTanka(),
											DataCtrl.getInstance().getUserMstData().getDciUserid());

									//�G�f�B�^�ݒ�
									TableBase tbHaigoMeisai = tb.getTrial1Panel().getTrial1().getHaigoMeisai();
									MiddleCellEditor mceTanka = (MiddleCellEditor)tbHaigoMeisai.getCellEditor(k, 5);
									DefaultCellEditor dceTanka = (DefaultCellEditor)mceTanka.getTableCellEditor(k);
									TextboxBase tbbTanka = (TextboxBase)dceTanka.getComponent();

									//�����_���ݒ�
									MiddleCellRenderer mcrTanka =  (MiddleCellRenderer)tbHaigoMeisai.getCellRenderer(k, 5);
									TextFieldCellRenderer tfcrTanka =  (TextFieldCellRenderer)mcrTanka.getTableCellRenderer(k);

									//�V�K����or�R�����g�sorNULL�̏ꍇ
									boolean sinki_chk = DataCtrl.getInstance().getTrialTblData().searchHaigouGenryoCdSinki(koteiCd, koteiSeq);
									if(sinki_chk){

										//�ҏW��
										tbbTanka.setBackground(Color.WHITE);
										tbbTanka.setEditable(true);
										tbbTanka.setEnabled(true);
										tfcrTanka.setColor(Color.WHITE);
									}
									//�V�K����or�R�����g�sorNULL�łȂ��ꍇ
									else{
										//��{���̉�Ђ��L���[�s�[�̏ꍇ
										if(DataCtrl.getInstance().getTrialTblData().SearchShisakuhinData().getIntKaishacd() ==
											Integer.parseInt(JwsConstManager.JWS_CD_KEWPIE)){

											//�����̉�Ђ��L���[�s�[�̏ꍇ
											if(md.getIntKaishaCd() == Integer.parseInt(JwsConstManager.JWS_CD_KEWPIE)){

												//�������̏ꍇ
												if(strDispGenryo != null && strDispGenryo.substring(0, 1).equals(JwsConstManager.JWS_MARK_0001)){

													//�ҏW��
													tbbTanka.setBackground(Color.WHITE);
													tbbTanka.setEditable(true);
													tbbTanka.setEnabled(true);
													tfcrTanka.setColor(Color.WHITE);
												}
												//�������łȂ��ꍇ
												else{

													//�ҏW�s��
													tbbTanka.setBackground(JwsConstManager.JWS_DISABLE_COLOR);
													tbbTanka.setEditable(false);
													tbbTanka.setEnabled(false);
													tfcrTanka.setColor(JwsConstManager.JWS_DISABLE_COLOR);
												}
											}
											//�����̉�Ђ��L���[�s�[�łȂ��ꍇ
											else{

												//�ҏW��
												tbbTanka.setBackground(Color.WHITE);
												tbbTanka.setEditable(true);
												tbbTanka.setEnabled(true);
												tfcrTanka.setColor(Color.WHITE);
											}
										}
										//��{���̉�Ђ��L���[�s�[�łȂ��ꍇ
										else{

											//�ҏW��
											tbbTanka.setBackground(Color.WHITE);
											tbbTanka.setEditable(true);
											tbbTanka.setEnabled(true);
											tfcrTanka.setColor(Color.WHITE);

										}
									}
									//2011/04/26 QP@10181_No.73 TT T.Satoh Add End -------------------------

									//����
									//�\���l�ݒ�
									dispHaigoMeisai.setValueAt(md.getDciBudomari(), k, 6);
									//�e�[�u���ݒ�
									DataCtrl.getInstance().getTrialTblData().UpdHaigoGenryoBudomari(
											koteiCd,
											koteiSeq,
											md.getDciBudomari(),
											DataCtrl.getInstance().getUserMstData().getDciUserid());

									//���ܗL��
									//�\���l�ݒ�
									dispHaigoMeisai.setValueAt(md.getDciGanyuritu(), k, 7);
									//�e�[�u���ݒ�
									DataCtrl.getInstance().getTrialTblData().UpdHaigoGenryoYuganyuryo(
											koteiCd,
											koteiSeq,
											md.getDciGanyuritu(),
											DataCtrl.getInstance().getUserMstData().getDciUserid());
								}
								//�ҏW�s�̏ꍇ�F�������Ȃ�
								else{

								}
								//�|�_
								DataCtrl.getInstance().getTrialTblData().UpdHaigoGenryoSakusan(
										koteiCd,
										koteiSeq,
										md.getDciSakusan(),
										DataCtrl.getInstance().getUserMstData().getDciUserid());

								//�H��
								DataCtrl.getInstance().getTrialTblData().UpdHaigoGenryoSyokuen(
										koteiCd,
										koteiSeq,
										md.getDciShokuen(),
										DataCtrl.getInstance().getUserMstData().getDciUserid());

								//���_
								DataCtrl.getInstance().getTrialTblData().UpdHaigoGenryoSousan(
										koteiCd,
										koteiSeq,
										md.getDciSosan(),
										DataCtrl.getInstance().getUserMstData().getDciUserid());

// ADD start 20121002 QP@20505 No.24
								//�l�r�f
								DataCtrl.getInstance().getTrialTblData().UpdHaigoGenryoMsg(
										koteiCd,
										koteiSeq,
										md.getDciMsg(),
										DataCtrl.getInstance().getUserMstData().getDciUserid());
// ADD end 20121002 QP@20505 No.24
//mod end   -------------------------------------------------------------------------------

//add start -------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.31

								//���H��Ɍ���������ꍇ
								if(md.getDciMaBudomari() == null){

								}
								//���H��Ɍ���������ꍇ
								else{
									//�}�X�^�����ݒ�
									DataCtrl.getInstance().getTrialTblData().UpdHaigoGenryoMaBudomari(
											koteiCd,
											koteiSeq,
											md.getDciMaBudomari());
								}



								boolean fg = DataCtrl.getInstance().getTrialTblData().searchHaigouGenryoMaBudomari(koteiCd, koteiSeq);

								//�G�f�B�^�ݒ�
								TableBase tbHaigoMeisai = tb.getTrial1Panel().getTrial1().getHaigoMeisai();
								MiddleCellEditor mceBudomari = (MiddleCellEditor)tbHaigoMeisai.getCellEditor(k, 6);
								DefaultCellEditor dceBudomari = (DefaultCellEditor)mceBudomari.getTableCellEditor(k);
								//2011/05/31 QP@10181_No.66 TT T.Satoh Change Start -------------------------
								//�˗��ς݂̏ꍇ
								if(!DataCtrl.getInstance().getTrialTblData().checkListHenshuOkFg(0, koteiCd, koteiSeq)){
									//�F��ς��Ȃ�
								}
								else if(fg){
								//if(fg){
								//2011/05/31 QP@10181_No.66 TT T.Satoh Change End ---------------------------
									((TextboxBase)dceBudomari.getComponent()).setFont(new Font("Default", Font.PLAIN, 12));
									((TextboxBase)dceBudomari.getComponent()).setForeground(Color.black);
								}
								else{
									((TextboxBase)dceBudomari.getComponent()).setFont(new Font("Default", Font.BOLD, 12));
									((TextboxBase)dceBudomari.getComponent()).setForeground(Color.red);
								}

								//�����_���ݒ�
								MiddleCellRenderer mcrBudomari =  (MiddleCellRenderer)tbHaigoMeisai.getCellRenderer(k, 6);
								TextFieldCellRenderer tfcrBudomari =  (TextFieldCellRenderer)mcrBudomari.getTableCellRenderer(k);
								if(fg){
									tfcrBudomari.setFont(new Font("Default", Font.PLAIN, 12));
									tfcrBudomari.setForeground(Color.black);
								}
								else{
									tfcrBudomari.setFont(new Font("Default", Font.BOLD, 12));
									tfcrBudomari.setForeground(Color.red);
								}
//add end   -------------------------------------------------------------------------------

							}
						}
					}
				}
			}
		}catch(ExceptionBase eb){
			throw eb;

		}catch(Exception e){
			e.printStackTrace();

			//�G���[�ݒ�
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			ex.setStrErrmsg("�������̉��/�H��i�����j���֏����Ɏ��s���܂���");
			ex.setStrErrShori(this.getClass().toString());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getMessage());
			throw ex;

		}finally{

		}

	}

	/************************************************************************************
	 *
	 *  �������A���/�H��i�����j�􂢊����@XML�ʐM�����iJW070�j
	 *    :  �������A���/�H��i�����j�􂢊�������XML�f�[�^�ʐM�iJW070�j���s��
	 *   @author TT nishigawa
	 *
	 ************************************************************************************/
	private ArrayList conJW070() throws ExceptionBase{
		ArrayList ret = new ArrayList();

		try{

			//--------------------------- ���M�p�����[�^�i�[  ---------------------------------
			String strUser = DataCtrl.getInstance().getParamData().getDciUser().toString();

			//�z���f�[�^�擾
			ArrayList retuData = DataCtrl.getInstance().getTrialTblData().SearchHaigoData(0);

			//--------------------------- ���MXML�f�[�^�쐬  ---------------------------------
			xmlJW070 = new XmlData();
			ArrayList arySetTag = new ArrayList();

			//------------------------------- Root�ǉ�  ------------------------------------
			xmlJW070.AddXmlTag("","JW070");
			arySetTag.clear();

			//------------------------- �@�\ID�ǉ��iUSERINFO�j  -----------------------------
			xmlJW070.AddXmlTag("JW070", "USERINFO");

			//----------------------------- �e�[�u���^�O�ǉ�  --------------------------------
			xmlJW070.AddXmlTag("USERINFO", "table");

			//------------------------------ ���R�[�h�ǉ�  -----------------------------------
			//�����敪
			arySetTag.add(new String[]{"kbn_shori", "3"});
			//���[�UID
			arySetTag.add(new String[]{"id_user",strUser});
			//XML�փ��R�[�h�ǉ�
			xmlJW070.AddXmlTag("table", "rec", arySetTag);
			//�z�񏉊���
			arySetTag.clear();

			//---------------------- �������A���/�H��i�����j�􂢊���  ----------------------
			xmlJW070.AddXmlTag("JW070", "SA810");
			//�@�e�[�u���^�O�ǉ�
			xmlJW070.AddXmlTag("SA810", "table");


			//-------------------------------- ���R�[�h�ǉ�  ---------------------------------
			//����i�f�[�^�擾
			PrototypeData selPrototypeData = DataCtrl.getInstance().getTrialTblData().SearchShisakuhinData();
			//�z���f�[�^�擾
			ArrayList addHaigo = DataCtrl.getInstance().getTrialTblData().SearchHaigoData(0);

			for(int i=0; i<addHaigo.size(); i++){
				MixedData MixedData = (MixedData)addHaigo.get(i);
				//���CD(�􂢊�����̉��CD)
				arySetTag.add(new String[]{"cd_new_kaisha",checkNull(selPrototypeData.getIntKaishacd())});
				//�H��CD(�􂢊�����̍H��CD)
				arySetTag.add(new String[]{"cd_new_busho",checkNull(selPrototypeData.getIntKojoco())});
				//������
				arySetTag.add(new String[]{"sort_genryo",checkNull(MixedData.getIntGenryoNo())});
				//����CD
				arySetTag.add(new String[]{"cd_genryo",checkNull(MixedData.getStrGenryoCd())});
				//���CD
				arySetTag.add(new String[]{"cd_kaisha",checkNull(MixedData.getIntKaishaCd())});
				//����CD�i�H��CD�j
				arySetTag.add(new String[]{"cd_busho",checkNull(MixedData.getIntBushoCd())});
				//��������
				String insertNm = "";

				//���������擾
				int keta = DataCtrl.getInstance().getTrialTblData().getKaishaGenryo();

				//�R�����g�s�̔���
				boolean comFlg = DataCtrl.getInstance().getTrialTblData().commentChk(
												MixedData.getStrGenryoCd(),
												keta
											);

				//�R�����g�s�̏ꍇ
				if(comFlg){

					insertNm = MixedData.getStrGenryoNm();

				//�����s�̏ꍇ
				}else{

					insertNm = tb.getTrial1Panel().getTrial1().delMark(MixedData.getStrGenryoNm());

				}
				arySetTag.add(new String[]{"nm_genryo",checkNull(insertNm)});
				//�P��
				arySetTag.add(new String[]{"tanka",checkNull(MixedData.getDciTanka())});
				//����
				arySetTag.add(new String[]{"budomari",checkNull(MixedData.getDciBudomari())});
				//���ܗL��
				arySetTag.add(new String[]{"ritu_abura",checkNull(MixedData.getDciGanyuritu())});
				//�|�_
				arySetTag.add(new String[]{"ritu_sakusan",checkNull(MixedData.getDciSakusan())});
				//�H��
				arySetTag.add(new String[]{"ritu_shokuen",checkNull(MixedData.getDciShokuen())});
				//���_
				arySetTag.add(new String[]{"ritu_sousan",checkNull(MixedData.getDciSosan())});
// ADD start 20121002 QP@20505 No.24
				//�l�r�f
				arySetTag.add(new String[]{"ritu_msg",checkNull(MixedData.getDciMsg())});
// ADD end 20121002 QP@20505 No.24

				//XML�փ��R�[�h�ǉ�
				xmlJW070.AddXmlTag("table", "rec", arySetTag);
				//�z�񏉊���
				arySetTag.clear();
			}

			//----------------------------------- XML���M  ----------------------------------
//			System.out.println("xmlJW070���MXML===============================================================");
//			xmlJW070.dispXml();
			xcon = new XmlConnection(xmlJW070);
			xcon.setStrAddress(DataCtrl.getInstance().getJnlpConnect().getStrAddressAjax());
			xcon.XmlSend();

			//----------------------------------- XML��M  ----------------------------------
			xmlJW070 = xcon.getXdocRes();
//			System.out.println();
//			System.out.println("xmlJW070��MXML===============================================================");
//			xmlJW070.dispXml();

			//---------------------------------- Result�`�F�b�N  -------------------------------
			DataCtrl.getInstance().getResultData().setResultData(xmlJW070);
			if (DataCtrl.getInstance().getResultData().getStrReturnFlg().equals("false")) {
				ExceptionBase ExceptionBase  = new ExceptionBase();
				throw ExceptionBase;

			}else{
				/**********************************************************
				 *�@T120�i�[
				 *********************************************************/
				//�S�̔z��擾
				ArrayList t120 = xmlJW070.GetAryTag("SA810");

				//�@�\�z��擾
				ArrayList kinoData = (ArrayList)t120.get(0);

				//�e�[�u���z��擾
				ArrayList tableT120 = (ArrayList)kinoData.get(1);

				//�@���R�[�h�擾
				for(int i=1; i<tableT120.size(); i++){

					//�@�P���R�[�h�擾
					ArrayList recData = ((ArrayList)((ArrayList)tableT120.get(i)).get(0));
					//�@�z���f�[�^������
					MixedData midtHaigou = new MixedData();

					//�@�f�[�^�֊i�[
					for(int j=0; j<recData.size(); j++){

						//�@���ږ��擾
						String recNm = ((String[])recData.get(j))[1];
						//�@���ڒl�擾
						String recVal = ((String[])recData.get(j))[2];

						/*****************�z���f�[�^�֒l�Z�b�g*********************/
						//  ������
						if(recNm == "sort_genryo"){
							midtHaigou.setIntGenryoNo(DataCtrl.getInstance().getTrialTblData().checkNullInt(recVal));

						//�@����CD
						}if(recNm == "cd_genryo"){
							midtHaigou.setStrGenryoCd(DataCtrl.getInstance().getTrialTblData().checkNullString(recVal));

						//�@���CD
						}if(recNm == "cd_kaisha"){
							midtHaigou.setIntKaishaCd(DataCtrl.getInstance().getTrialTblData().checkNullInt(recVal));

						//�@����CD�i�H��CD�j
						}if(recNm == "cd_busho"){
							midtHaigou.setIntBushoCd(DataCtrl.getInstance().getTrialTblData().checkNullInt(recVal));

						//�@��������
						}if(recNm == "nm_genryo"){
							midtHaigou.setStrGenryoNm(DataCtrl.getInstance().getTrialTblData().checkNullString(recVal));

						//�@�P��
						}if(recNm == "tanka"){
							midtHaigou.setDciTanka(DataCtrl.getInstance().getTrialTblData().checkNullDecimal(recVal));

						//�@����
						}if(recNm == "budomari"){
							midtHaigou.setDciBudomari(DataCtrl.getInstance().getTrialTblData().checkNullDecimal(recVal));

						//�@���ܗL��
						}if(recNm == "ritu_abura"){
							midtHaigou.setDciGanyuritu(DataCtrl.getInstance().getTrialTblData().checkNullDecimal(recVal));

						//�@�|�_
						}if(recNm == "ritu_sakusan"){
							midtHaigou.setDciSakusan(DataCtrl.getInstance().getTrialTblData().checkNullDecimal(recVal));

						//�@�H��
						}if(recNm == "ritu_shokuen"){
							midtHaigou.setDciShokuen(DataCtrl.getInstance().getTrialTblData().checkNullDecimal(recVal));

						//�@���_
						}if(recNm == "ritu_sousan"){
							midtHaigou.setDciSosan(DataCtrl.getInstance().getTrialTblData().checkNullDecimal(recVal));

						}

						if(recNm == "ma_budomari"){
							midtHaigou.setDciMaBudomari(DataCtrl.getInstance().getTrialTblData().checkNullDecimal(recVal));

						}
// ADD start 20121002 QP@20505 No.24
						//�@�l�r�f
						if(recNm == "ritu_msg"){
							midtHaigou.setDciMsg(DataCtrl.getInstance().getTrialTblData().checkNullDecimal(recVal));
						}
// ADD end 20121002 QP@20505 No.24
					}
					//�@�z���f�[�^�z��֒ǉ�
					ret.add(midtHaigou);
					recData.clear();
				}
			}

		}catch(ExceptionBase eb){
			throw eb;

		}catch(Exception e){
			e.printStackTrace();

			//�G���[�ݒ�
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			ex.setStrErrmsg(" �������̉��/�H��i�����j���֏����Ɏ��s���܂���");
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getStackTrace().toString());
			throw ex;

		}finally{

		}

		return ret;
	}

// ADD start 20121017 QP@20505 No.11
	/**
	 * �yJW220�z �������̓}�X�^�l�擾 ���MXML�f�[�^�쐬
	 * @author 2012/10/17 T.Hisahori
	 */
	private ArrayList conJW220() throws ExceptionBase{
		ArrayList ret = new ArrayList();
		int i;
		try{
			//--------------------------- ���M�p�����[�^�i�[  -----------------------------------
			String strUser = DataCtrl.getInstance().getParamData().getDciUser().toString();
			String strXmlId = "JW220";
			String strAjaxUrl = DataCtrl.getInstance().getJnlpConnect().getStrAddressAjax();

			//--------------------------- ���MXML�f�[�^�쐬  -----------------------------------
			XmlData xmlJW220 = new XmlData();
			ArrayList arySetTag = new ArrayList();

			//------------------------------- Root�ǉ�  --------------------------------------
			xmlJW220.AddXmlTag("",strXmlId);
			arySetTag.clear();

			//-------------------------- �@�\ID�ǉ��iUSERINFO�j  -------------------------------
			xmlJW220.AddXmlTag(strXmlId, "USERINFO");
			//�@�e�[�u���^�O�ǉ�
			xmlJW220.AddXmlTag("USERINFO", "table");
			//�@���R�[�h�ǉ�
			arySetTag.add(new String[]{"kbn_shori", "3"});
			arySetTag.add(new String[]{"id_user",strUser});
			xmlJW220.AddXmlTag("table", "rec", arySetTag);
			arySetTag.clear();

			//------------------------ �@�\ID�ǉ��i���͒l�ύX�m�F�j  ----------------------------
			xmlJW220.AddXmlTag(strXmlId, "SA591");
			//�@�e�[�u���^�O�ǉ�
			xmlJW220.AddXmlTag("SA591", "table");

			//�z���f�[�^
			ArrayList aryHaigoData = DataCtrl.getInstance().getTrialTblData().SearchHaigoData(0);
			for ( i=0; i<aryHaigoData.size(); i++  ) {
				MixedData mixedData = (MixedData)aryHaigoData.get(i);
				//���X�|���X�f�[�^�̐ݒ�
				arySetTag.add(new String[]{"cd_kaisha",checkNull(mixedData.getIntKaishaCd())});		//��ЃR�[�h(����)
				arySetTag.add(new String[]{"cd_genryo",checkNull(mixedData.getStrGenryoCd())});		//�����R�[�h(����)
				arySetTag.add(new String[]{"ritu_abura",checkNull(mixedData.getDciGanyuritu())});	//���ܗL��(����)
				arySetTag.add(new String[]{"ritu_sakusan",checkNull(mixedData.getDciSakusan())});	//�|�_(����)
				arySetTag.add(new String[]{"ritu_shokuen",checkNull(mixedData.getDciShokuen())});	//�H��(����)
				arySetTag.add(new String[]{"ritu_sousan",checkNull(mixedData.getDciSosan())});		//���_(����)
				arySetTag.add(new String[]{"ritu_msg",checkNull(mixedData.getDciMsg())});		//���_(����)
				xmlJW220.AddXmlTag("table", "rec", arySetTag);
				arySetTag.clear();
			}

			//---------------------------------- XML���M  ------------------------------------
			//xmlJW210.dispXml();

			XmlData xmlJW = xmlJW220;
			XmlConnection xmlConnection = new XmlConnection(xmlJW220);
			xmlConnection.setStrAddress(strAjaxUrl);
			xmlConnection.XmlSend();

			//---------------------------------- XML��M  ------------------------------------
			xmlJW220 = xmlConnection.getXdocRes();

			//--------------------------------- Result�f�[�^  ----------------------------------
			DataCtrl.getInstance().getResultData().setResultData(xmlJW220);
			if ( DataCtrl.getInstance().getResultData().isReturnFlgCheck() ) {
				//�������ʂ����݂����ꍇ
				/**********************************************************
				 *�@T120�i�[
				 *********************************************************/
				//�S�̔z��擾
				ArrayList t120 = xmlJW220.GetAryTag("SA591");

				//�@�\�z��擾
				ArrayList kinoData = (ArrayList)t120.get(0);

				//�e�[�u���z��擾
				ArrayList tableT120 = (ArrayList)kinoData.get(1);

				//�@���R�[�h�擾
				for(int p=1; p<tableT120.size(); p++){

					//�@�P���R�[�h�擾
					ArrayList recData = ((ArrayList)((ArrayList)tableT120.get(p)).get(0));
					//�@�z���f�[�^������
					MixedData midtHaigou = new MixedData();

					//�@�f�[�^�֊i�[
					for(int j=0; j<recData.size(); j++){

						//�@���ږ��擾
						String recNm = ((String[])recData.get(j))[1];
						//�@���ڒl�擾
						String recVal = ((String[])recData.get(j))[2];

						/*****************�z���f�[�^�֒l�Z�b�g*********************/
						//�@����CD
						 if(recNm == "cd_genryo"){
							midtHaigou.setStrGenryoCd(DataCtrl.getInstance().getTrialTblData().checkNullString(recVal));
						//�@���CD
						}if(recNm == "cd_kaisha"){
							midtHaigou.setIntKaishaCd(DataCtrl.getInstance().getTrialTblData().checkNullInt(recVal));
						//�@��������
						}if(recNm == "nm_genryo"){
							midtHaigou.setStrGenryoNm(DataCtrl.getInstance().getTrialTblData().checkNullString(recVal));
						//�@���ܗL��
						}if(recNm == "ritu_abura"){
							midtHaigou.setDciGanyuritu(DataCtrl.getInstance().getTrialTblData().checkNullDecimal(recVal));
						//�@�|�_
						}if(recNm == "ritu_sakusan"){
							midtHaigou.setDciSakusan(DataCtrl.getInstance().getTrialTblData().checkNullDecimal(recVal));
						//�@�H��
						}if(recNm == "ritu_shokuen"){
							midtHaigou.setDciShokuen(DataCtrl.getInstance().getTrialTblData().checkNullDecimal(recVal));
						//�@���_
						}if(recNm == "ritu_sousan"){
							midtHaigou.setDciSosan(DataCtrl.getInstance().getTrialTblData().checkNullDecimal(recVal));
						//�@�l�r�f
						}if(recNm == "ritu_msg"){
							midtHaigou.setDciMsg(DataCtrl.getInstance().getTrialTblData().checkNullDecimal(recVal));
						}
					}
					//�@�z���f�[�^�z��֒ǉ�
					ret.add(midtHaigou);
					recData.clear();
				}
			} else {
				//�������ʂ����݂��Ȃ��ꍇ
			}
		}catch(ExceptionBase eb){
			throw eb;
		}catch(Exception e){
			e.printStackTrace();
			//�G���[�ݒ�
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			ex.setStrErrmsg(" ���̓}�X�^�̍ŐV�f�[�^�擾�����Ɏ��s���܂���");
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getStackTrace().toString());
			throw ex;
		}finally{
		}
		return ret;
	}
// ADD end 20121017 QP@20505 No.11

	/************************************************************************************
	 *
	 * �����ۑ�����
	 *  : ���[�o�̓{�^�����������ꂽ���A�Ăяo�����B
	 *   �����ŁA�o�^�������s��
	 *   @param intChkMsg : �o�^�����b�Z�[�W�̎w��
	 *    [0:�o�^, 1:�����ۑ�(����\), 2:�����ۑ�(�T���v��������), 3:�����ۑ�(�h�{�v�Z��)]
	 * @throws ExceptionBase
	 *
	 ************************************************************************************/
	private void JidouHozon(int intChkMsg) throws ExceptionBase {

		try {

			//2011/04/20 QP@10181_No.41 TT T.Satoh Add Start -------------------------
			//�z���\�̎��얾�׃e�[�u���̏c�X�N���[���o�[�̌��݈ʒu���擾
			int vHaigoBarVal = tb.getTrial1Panel().getTrial1().getScrollMain().getVerticalScrollBar().getValue();

			//�z���\�̎��얾�׃e�[�u���̉��X�N���[���o�[�̌��݈ʒu���擾
			int hHaigoBarVal = tb.getTrial1Panel().getTrial1().getScrollMain().getHorizontalScrollBar().getValue();

			//�����l�̉��X�N���[���o�[�̌��݈ʒu���擾
			int hTokuseiBarVal = tb.getTrial2Panel().getScroll().getHorizontalScrollBar().getValue();

			//�������Z�̉��X�N���[���o�[�̌��݈ʒu���擾
			int hGenkaBarVal = tb.getTrial5Panel().getScroll().getHorizontalScrollBar().getValue();
			//2011/04/20 QP@10181_No.41 TT T.Satoh Add End ---------------------------

			//���[�h�̎擾
			String mode = DataCtrl.getInstance().getParamData().getStrMode();

			//�ڍ�
			if(mode.equals(JwsConstManager.JWS_MODE_0001)){
				//�o�^�i�ҏW�j
				conJW040(intChkMsg);

			//�V�K
			}else if(mode.equals(JwsConstManager.JWS_MODE_0002)){
				//����R�[�h�����̔�
				//conJ010();
				//�o�^�i�V�K�j
				conJW030(intChkMsg);
				//�f�[�^�ݒ�
				txtShisakuUser.setText(checkNull(PrototypeData.getDciShisakuUser()));
				txtShisakuNen.setText(checkNull(PrototypeData.getDciShisakuYear()));
				txtShisakuOi.setText(checkNull(PrototypeData.getDciShisakuNum()));

				//�R�s�[�{�^���g�p��
				btnTcopy.setEnabled(true);
				btnZcopy.setEnabled(true);
			}

			//2011/04/20 QP@10181_No.41 TT T.Satoh Add Start -------------------------
			//�z���\�̎��얾�׃e�[�u���̏c�X�N���[���o�[�̈ʒu��ݒ�
			tb.getTrial1Panel().getTrial1().getScrollMain().getVerticalScrollBar().setValue(vHaigoBarVal);

			//�z���\�̎��얾�׃e�[�u���̉��X�N���[���o�[�̈ʒu��ݒ�
			tb.getTrial1Panel().getTrial1().getScrollMain().getHorizontalScrollBar().setValue(hHaigoBarVal);

			//�����l�̉��X�N���[���o�[�̈ʒu��ݒ�
			tb.getTrial2Panel().getScroll().getHorizontalScrollBar().setValue(hTokuseiBarVal);

			//�������Z�̉��X�N���[���o�[�̈ʒu��ݒ�
			tb.getTrial5Panel().getScroll().getHorizontalScrollBar().setValue(hGenkaBarVal);
			//2011/04/20 QP@10181_No.41 TT T.Satoh Add End ---------------------------

		}catch(ExceptionBase eb){
			throw eb;

		}catch(Exception e){
			//�G���[�ݒ�
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			//2011/04/13 QP@10181_No.26 TT T.Satoh Change Start -------------------------
			//ex.setStrErrmsg("����\�@ ���FG�`�F�b�N���������s���܂���");
			ex.setStrErrmsg("�z���\ ���FG�`�F�b�N���������s���܂���");
			//2011/04/13 QP@10181_No.26 TT T.Satoh Change End ---------------------------
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getStackTrace().toString());
			throw ex;

		}finally{

		}

	}

	/************************************************************************************
	 *
	 * ���FG�`�F�b�N
	 * @param strName : ���[��
	 * @param intChkCount : �w�茏��
	 * @return true : �����, false : ����s��
	 * @throws ExceptionBase
	 *
	 ************************************************************************************/
	private boolean chkInsatuFlg(String strName, int intChkCount) throws ExceptionBase {

		boolean ret = true;
		int intCount = 0;

		try {

			//�����f�[�^���擾���A����e�[�u���f�[�^���擾����
			ArrayList aryShisakuRetu = DataCtrl.getInstance().getTrialTblData().SearchShisakuRetuData(0);
			for ( int i=0; i<aryShisakuRetu.size(); i++ ) {
				TrialData trialData = (TrialData)aryShisakuRetu.get(i);

				//���Flg���`�F�b�N����Ă���Ȃ�΁A�J�E���g��i�߂�B
				if ( trialData.getIntInsatuFlg() == 1 ) {
					intCount++;

				}

				//�J�E���g���w�茏���𒴂����ꍇ
				if ( intCount > intChkCount ) {
					//���b�Z�[�W��\�����A�����𒆒f����
					String strMessage = strName + "�ɏo�͂ł���̂�" + intChkCount + "��܂łł�";
					DataCtrl.getInstance().getMessageCtrl().PrintMessageString(strMessage);
					ret = false;
					break;

				}

			}

			//���FG���I������Ă��Ȃ��ꍇ
			if ( intCount == 0 ) {
				//���b�Z�[�W��\�����A�����𒆒f����
				String strMessage = "���FG��I�����ĉ������B";
				DataCtrl.getInstance().getMessageCtrl().PrintMessageString(strMessage);
				ret = false;

			}

		} catch (ExceptionBase e) {
			throw e;

		} catch (Exception e) {
			//�G���[�ݒ�
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			//2011/04/13 QP@10181_No.26 TT T.Satoh Change Start -------------------------
			//ex.setStrErrmsg("����\�@ ���FG�`�F�b�N���������s���܂���");
			ex.setStrErrmsg("�z���\ ���FG�`�F�b�N���������s���܂���");
			//2011/04/13 QP@10181_No.26 TT T.Satoh Change End ---------------------------
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getStackTrace().toString());
			throw ex;

		} finally {

		}
		return ret;

	}

	/************************************************************************************
	 *
	 *   ����\�o��
	 *   @author TT katayama
	 *   @throws ExceptionBase
	 *
	 ************************************************************************************/
	private void conJW120() throws ExceptionBase {

		try {

			//------------------------------ ���M�p�����[�^�i�[  ------------------------
			String strUser = DataCtrl.getInstance().getParamData().getDciUser().toString();

			//------------------------------ ���MXML�f�[�^�쐬  ------------------------
			xmlJW120 = new XmlData();
			ArrayList arySetTag = new ArrayList();

			//------------------------------ Root�ǉ�  ---------------------------------
			xmlJW120.AddXmlTag("","JW120");
			arySetTag.clear();

			//------------------------------ �@�\ID�ǉ��iUSEERINFO�j  -------------------
			xmlJW120.AddXmlTag("JW120", "USERINFO");
			//�@�e�[�u���^�O�ǉ�
			xmlJW120.AddXmlTag("USERINFO", "table");
			//�@���R�[�h�ǉ�
			arySetTag.add(new String[]{"kbn_shori", "3"});
			arySetTag.add(new String[]{"id_user",strUser});
			xmlJW120.AddXmlTag("table", "rec", arySetTag);
			//�z�񏉊���
			arySetTag.clear();

			//------------------------------ �@�\ID�ǉ��iSA460�j  ------------------------
			xmlJW120.AddXmlTag("JW120", "SA460");
			//�@�e�[�u���^�O�ǉ�
			xmlJW120.AddXmlTag("SA460", "table");

			//���샊�X�g�f�[�^���擾
			Object[] aryShisakuList = DataCtrl.getInstance().getTrialTblData().getAryShisakuList().toArray();

			//���ӎ���
			String strChuijiko = DataCtrl.getInstance().getManufacturingSubDisp().getManufacturingPanel().getMemoText().getText();
			//��ɕ\��
			boolean isTuneniChk = DataCtrl.getInstance().getManufacturingSubDisp().getManufacturingPanel().getCheckbox().isSelected();

			//�@���R�[�h�ǉ�
			for ( int i=0; i<aryShisakuList.length; i++ ) {

				//���샊�X�g�f�[�^���擾
				PrototypeListData prototypeListData = (PrototypeListData)aryShisakuList[i];

				//���Fg�̎擾
				int intPrintFg = ((TrialData)DataCtrl.getInstance().getTrialTblData().SearchShisakuRetuData(prototypeListData.getIntShisakuSeq()).get(0)).getIntInsatuFlg();

				//���F���Ƀ`�F�b�N������Ă���ꍇ�A�f�[�^��ǉ�����
				if ( intPrintFg == 1 ) {

					//�f�[�^�̒ǉ�
					arySetTag.add(new String[]{"cd_shain", DataCtrl.getInstance().getParamData().getStrSisaku_user()});
					arySetTag.add(new String[]{"nen", DataCtrl.getInstance().getParamData().getStrSisaku_nen()});
					arySetTag.add(new String[]{"no_oi", DataCtrl.getInstance().getParamData().getStrSisaku_oi()});
					arySetTag.add(new String[]{"seq_shisaku", Integer.toString(prototypeListData.getIntShisakuSeq())});
					arySetTag.add(new String[]{"cd_kotei", Integer.toString(prototypeListData.getIntKoteiCd())});
					arySetTag.add(new String[]{"seq_kotei", Integer.toString(prototypeListData.getIntKoteiSeq())});

					//���ӎ����u��ɕ\���v�Ƀ`�F�b�N�������Ă���ꍇ
					if ( isTuneniChk ) {
						arySetTag.add(new String[]{"chuijiko", strChuijiko});

					} else {
						arySetTag.add(new String[]{"chuijiko", ""});

					}
// ADD start 20121019 QP@20505 No.24
					// �����l���ڂ̐؂蕪���i�H���p�^�[���ɂ���ĕ\�����ڂ��؂�ւ�邽�߁j
					//���݂̐ݒ�l�擾
					String kotei_ptn = DataCtrl.getInstance().getTrialTblData().SearchShisakuhinData().getStrPt_kotei();
					if(kotei_ptn == null){
						kotei_ptn = "";
					}
					String flgPattern = "0";
					if (kotei_ptn.equals("001") || kotei_ptn.equals("002")) {
						//�H���p�^�[�����P�tor�Q�t�̏ꍇ
						flgPattern = "1";
					}
					arySetTag.add(new String[]{"pattern_kotei", flgPattern});
// ADD end 20121019 QP@20505 No.24

					xmlJW120.AddXmlTag("table", "rec", arySetTag);

					//�z�񏉊���
					arySetTag.clear();

				}

			}

			//----------------------------------- XML���M  ----------------------------------
//			System.out.println("JW120���MXML===============================================================");
//			xmlJW120.dispXml();
			xcon = new XmlConnection(xmlJW120);
			xcon.setStrAddress(DataCtrl.getInstance().getJnlpConnect().getStrAddressAjax());
			xcon.XmlSend();

			//----------------------------------- XML��M  ----------------------------------
			xmlJW120 = xcon.getXdocRes();
			//System.out.println();
			//System.out.println("JW120��MXML===============================================================");
			//xmlJW120.dispXml();
			//System.out.println();

			//---------------------------- Result�f�[�^�ݒ�(RESULT)  -----------------------------
			DataCtrl.getInstance().getResultData().setResultData(xmlJW120);

			// Result�f�[�^.�������ʂ�true�̏ꍇ�AExceptionBase��Throw����
			if ( !DataCtrl.getInstance().getResultData().isReturnFlgCheck() ) {
				throw new ExceptionBase();
			}

			//------------------------------ �f�[�^�ݒ�(SA460) --------------------------------
			//�_�E�����[�h�p�X�N���X
			DownloadPathData downloadPathData = new DownloadPathData();
			downloadPathData.setDownloadPathData(xmlJW120, "SA460");

			//URL�R�l�N�V�����N���X
			UrlConnection urlConnection = new UrlConnection();

			//�_�E�����[�h�p�X�𑗂�A�t�@�C���_�E�����[�h��ʂŊJ��
			urlConnection.urlFileDownLoad( downloadPathData.getStrDownloadPath());

		} catch (ExceptionBase e) {
			throw e;

		} catch (Exception e) {
			//�G���[�ݒ�
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			//2011/04/13 QP@10181_No.26 TT T.Satoh Change Start -------------------------
			//ex.setStrErrmsg("����\�@ ����\�o�͏��������s���܂���");
			ex.setStrErrmsg("�z���\ ����\�o�͏��������s���܂���");
			//2011/04/13 QP@10181_No.26 TT T.Satoh Change End ---------------------------
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getStackTrace().toString());
			throw ex;

		} finally {

		}

	}

	/************************************************************************************
	 *
	 *   �T���v���������o��
	 *   @author TT katayama
	 *   @throws ExceptionBase
	 *
	 ************************************************************************************/
	private void conJW130() throws ExceptionBase {

		try {

			//------------------------------ ���M�p�����[�^�i�[  ------------------------
			String strUser = DataCtrl.getInstance().getParamData().getDciUser().toString();

			//------------------------------ ���MXML�f�[�^�쐬  ------------------------
			xmlJW130 = new XmlData();
			ArrayList arySetTag = new ArrayList();

			//------------------------------ Root�ǉ�  ---------------------------------
			xmlJW130.AddXmlTag("","JW130");
			arySetTag.clear();

			//------------------------------ �@�\ID�ǉ��iUSEERINFO�j  -------------------
			xmlJW130.AddXmlTag("JW130", "USERINFO");
			//�@�e�[�u���^�O�ǉ�
			xmlJW130.AddXmlTag("USERINFO", "table");
			//�@���R�[�h�ǉ�
			arySetTag.add(new String[]{"kbn_shori", "3"});
			arySetTag.add(new String[]{"id_user",strUser});
			xmlJW130.AddXmlTag("table", "rec", arySetTag);
			//�z�񏉊���
			arySetTag.clear();

			//------------------------------ �@�\ID�ǉ��iSA450�j  ------------------------
			xmlJW130.AddXmlTag("JW130", "SA450");
			//�@�e�[�u���^�O�ǉ�
			xmlJW130.AddXmlTag("SA450", "table");
			//�@���R�[�h�ǉ� : ����e�[�u���̃f�[�^��ݒ�
			arySetTag.add(new String[]{"cd_shain", DataCtrl.getInstance().getParamData().getStrSisaku_user()});
			arySetTag.add(new String[]{"nen", DataCtrl.getInstance().getParamData().getStrSisaku_nen()});
			arySetTag.add(new String[]{"no_oi", DataCtrl.getInstance().getParamData().getStrSisaku_oi()});

			//�������f�[�^���擾
			int intShisakuSeqCount = 0;
			ArrayList aryTrialData = DataCtrl.getInstance().getTrialTblData().SearchShisakuRetuData(0);

			//����SEQ�i�[�ϐ�
			String[] aryShisakuSeq = new String[]{"","","",""};

			for ( int i=0; i<aryTrialData.size(); i++ ) {

				//����e�[�u���f�[�^�̎擾
				TrialData trialData = (TrialData)aryTrialData.get(i);

				//���Fg�ɂ�锻��
				if ( trialData.getIntInsatuFlg() == 1 ) {

					//����SEQ�擾
					String strShisakuSeq = Integer.toString(trialData.getIntShisakuSeq());

					//����SEQ���i�[
					aryShisakuSeq[intShisakuSeqCount] = strShisakuSeq;

					intShisakuSeqCount++;

				}

			}

			//����SEQ�ݒ�
			arySetTag.add(new String[]{"seq_shisaku1", aryShisakuSeq[0]});
			arySetTag.add(new String[]{"seq_shisaku2", aryShisakuSeq[1]});
			arySetTag.add(new String[]{"seq_shisaku3", aryShisakuSeq[2]});
			arySetTag.add(new String[]{"seq_shisaku4", aryShisakuSeq[3]});

			//table�ݒ�
			xmlJW130.AddXmlTag("table", "rec", arySetTag);

			//�z�񏉊���
			arySetTag.clear();

			//----------------------------------- XML���M  ----------------------------------
			System.out.println("JW130���MXML===============================================================");
			xmlJW130.dispXml();
			xcon = new XmlConnection(xmlJW130);
			xcon.setStrAddress(DataCtrl.getInstance().getJnlpConnect().getStrAddressAjax());
			xcon.XmlSend();

			//----------------------------------- XML��M  ----------------------------------
			xmlJW130 = xcon.getXdocRes();
//			System.out.println();
//			System.out.println("JW130��MXML===============================================================");
//			xmlJW130.dispXml();
//			System.out.println();

			//---------------------------- Result�f�[�^�ݒ�(RESULT)  -----------------------------
			DataCtrl.getInstance().getResultData().setResultData(xmlJW130);

			// Result�f�[�^.�������ʂ�true�̏ꍇ�AExceptionBase��Throw����
			if ( !DataCtrl.getInstance().getResultData().isReturnFlgCheck() ) {
				throw new ExceptionBase();
			}

			//------------------------------ �f�[�^�ݒ�(SA450) --------------------------------
			//�_�E�����[�h�p�X�N���X
			DownloadPathData downloadPathData = new DownloadPathData();
			downloadPathData.setDownloadPathData(xmlJW130, "SA450");

			//URL�R�l�N�V�����N���X
			UrlConnection urlConnection = new UrlConnection();

			//�_�E�����[�h�p�X�𑗂�A�t�@�C���_�E�����[�h��ʂŊJ��
			urlConnection.urlFileDownLoad( downloadPathData.getStrDownloadPath());

		} catch (ExceptionBase e) {
			throw e;

		} catch (Exception e) {
			//�G���[�ݒ�
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			//2011/04/13 QP@10181_No.26 TT T.Satoh Change Start -------------------------
			//ex.setStrErrmsg("����\�@ �T���v���������o�͏��������s���܂���");
			ex.setStrErrmsg("�z���\ �T���v���������o�͏��������s���܂���");
			//2011/04/13 QP@10181_No.26 TT T.Satoh Change End ---------------------------
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getStackTrace().toString());
			throw ex;

		} finally {


		}

	}

	/************************************************************************************
	 *
	 * �yJW740�z �h�{�v�Z���o�� ���MXML�f�[�^�쐬
	 * @throws ExceptionBase
	 *
	 ************************************************************************************/
	private void conJW740(int seq) throws ExceptionBase {

		try {

			//------------------------------ ���M�p�����[�^�i�[  ------------------------
			String strUser = DataCtrl.getInstance().getParamData().getDciUser().toString();
//			String strShisakuSeq = Integer.toString(DataCtrl.getInstance().getManufacturingSubDisp().getManufacturingPanel().getShisakuSeq());
			String strShisakuSeq = Integer.toString(seq);

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

	/**
	 * ���e�����f�[�^���R���{�{�b�N�X�֐ݒ�
	 * @param comb : �ݒ�ΏۃR���{�{�b�N�X
	 * @param literalData : �ݒ�Ώۃ��e�����f�[�^
	 * @param strLiteralCd : �\���Ώۃ��e�����R�[�h
	 * @param intType : �\���p���e�����R�[�h�̃^�C�v
	 *  (0:�R�[�h, 1:���l)
	 * @throws ExceptionBase
	 */
	private void setLiteralCmb(JComboBox comb, LiteralData literalData, String strLiteralCd, int intType) throws ExceptionBase {
		int i;
		String literalCd = "";
		String literalNm = "";
		int sortNo = 0;
		Object viewLiteralNm = "";

		try {
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
					if(strLiteralCd != null && strLiteralCd.length() > 0){
						comb.addItem(strLiteralCd);
						comb.setSelectedItem(strLiteralCd);
					}
				}
			}

		}catch(Exception e){
			//�G���[�ݒ�
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			ex.setStrErrmsg("���e�����f�[�^�R���{�{�b�N�X�ݒ菈�������s���܂���");
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getMessage());
			throw ex;
		}finally{
		}
	}

	/**
	 * �������Z�}�[�N�ݒ菈��
	 * @throws ExceptionBase
	 */
	private void setGenkaShisanMark() throws ExceptionBase {

		try {

			//���������f�[�^�̎擾
			ArrayList aryCostMaterial = DataCtrl.getInstance().getTrialTblData().SearchGenkaGenryoData(0);

			for ( int i=0; i<aryCostMaterial.size(); i++ ) {

				//���������f�[�^
				CostMaterialData costMaterialData = (CostMaterialData)aryCostMaterial.get(i);

				//����SEQ�̎擾
				int intSeq = costMaterialData.getIntShisakuSeq();

				//����e�[�u���̎擾
				TrialData trialData = (TrialData)(DataCtrl.getInstance().getTrialTblData().SearchShisakuRetuData(intSeq).get(0));

				//�������ZNo�̎擾
				int intGenkaShisanNo = trialData.getFlg_init();

				//---------------------------- �������Z�}�[�N�X�V ----------------------------------
				TableBase lh = tb.getTrial1Panel().getTrial1().getListHeader();
				for(int j=0; j<lh.getColumnCount(); j++){

					//�R���|�[�l���g�擾
					MiddleCellEditor selectMc = (MiddleCellEditor)lh.getCellEditor(0, j);
					DefaultCellEditor selectDc = (DefaultCellEditor)selectMc.getTableCellEditor(0);
					CheckboxBase cb = (CheckboxBase)selectDc.getComponent();
					String Mark = cb.getText();
					int sisakuSeq = Integer.parseInt(cb.getPk1());

					//�L���폜
					if(Mark != null && Mark.length() > 0){
						Mark = Mark.replace(JwsConstManager.JWS_MARK_0004.toCharArray()[0], ' ');
						Mark = Mark.trim();
					}

					if(sisakuSeq == costMaterialData.getIntShisakuSeq()){

						//�������ZNo��0���傫���ꍇ�A�}�[�N��ݒ肷��
						if ( intGenkaShisanNo > 0 ) {
							cb.setText(Mark + " " + JwsConstManager.JWS_MARK_0004);

						}

					}

				}

				//�ĕ\��
				tb.getTrial1Panel().getTrial1().setHeaderShisakuER();

			}

		}catch(ExceptionBase e) {
			throw e;

		}catch(Exception e){
			//�G���[�ݒ�
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			ex.setStrErrmsg("�������Z�}�[�N�ݒ菈�������s���܂���");
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getMessage());
			throw ex;
		}finally{

		}

	}

	/**
	 * ��d�`�F�b�N
	 * @throws ExceptionBase
	 */
	public boolean chkHiju() throws ExceptionBase{

		boolean ret = true;

		try{

			//����i���擾
			PrototypeData PrototypeData = DataCtrl.getInstance().getTrialTblData().SearchShisakuhinData();

			//�e�ʒP�ʂ��uml�v�̏ꍇ
			String tani = PrototypeData.getStrTani();
			if(tani != null && tani.equals(JwsConstManager.JWS_CD_TANI)){

				//�����f�[�^�z��擾
				ArrayList aryRetu = DataCtrl.getInstance().getTrialTblData().SearchShisakuRetuData(0);

				//��d�����̓`�F�b�N
				for(int i=0; i<aryRetu.size(); i++){

					//�����f�[�^�擾
					TrialData TrialData = (TrialData)aryRetu.get(i);

					//��d�擾
					String hiju = TrialData.getStrHizyu();

					//�����͂̏ꍇ
					if(hiju == null){

						ret = false;

					}
				}
			}
		}
		catch(Exception e){

			e.printStackTrace();

		}
		finally{

		}

		return ret;

	}


	/**
	 * �����`�F�b�N
	 * @param strData : �`�F�b�N�f�[�^
	 * @param komokuNm : ���ږ�
	 * @param flg : 0=�V�K,1=�ҏW
	 * @throws ExceptionBase
	 */
	public void seisuCheck(String strData, String komokuNm, int flg) throws ExceptionBase{

			//��d�̒l�����l�̏ꍇ
			try{

				//BigDecimal�^�ɕϊ��i���l�`�F�b�N�j
				BigDecimal chkDeci = new BigDecimal(strData);

				try{
					long chkInt = Long.parseLong(strData);

				}catch(Exception e){

					//�G���[���b�Z�[�W����
					String errMsg;

					//�V�K�o�^�̏ꍇ
					if(flg == 0){
						errMsg = "�y����f�[�^��ʁz �o�^�i�V�K�j���A���s���܂����B\n";
					}
					//�ҏW�o�^�̏ꍇ
					else{
						errMsg = "�y����f�[�^��ʁz �o�^�i�ҏW�j���A���s���܂����B\n";
					}

					errMsg += komokuNm+"�͐����œ��͂��ĉ������B";

					//�G���[�ݒ�
					ex = new ExceptionBase();
					ex.setStrErrCd("");
					ex.setStrErrmsg(errMsg);
					ex.setStrErrShori(this.getClass().getName());
					ex.setStrMsgNo("");
					ex.setStrSystemMsg("");

					throw ex;
				}

			}
			//�����`�F�b�N�G���[�̏ꍇ
			catch(ExceptionBase eb){

				throw eb;

			}
			//��d�̒l�����l�łȂ��ꍇ
			catch(Exception e){

				//�G���[���b�Z�[�W����
				String errMsg;

				//�V�K�o�^�̏ꍇ
				if(flg == 0){
					errMsg = "�y����f�[�^��ʁz �o�^�i�V�K�j���A���s���܂����B\n";
				}
				//�ҏW�o�^�̏ꍇ
				else{
					errMsg = "�y����f�[�^��ʁz �o�^�i�ҏW�j���A���s���܂����B\n";
				}

				errMsg += komokuNm+"�͐����œ��͂��ĉ������B";

				//�G���[�ݒ�
				ex = new ExceptionBase();
				ex.setStrErrCd("");
				ex.setStrErrmsg(errMsg);
				ex.setStrErrShori(this.getClass().getName());
				ex.setStrMsgNo("");
				ex.setStrSystemMsg("");

				throw ex;



			}

	}

	/**
	 * ���������`�F�b�N
	 * @param strData : �`�F�b�N�f�[�^
	 * @param iKetasu : �w�茅��
	 * @param komokuNm : ���ږ�
	 * @param flg : 0=�V�K,1=�ҏW
	 * @throws ExceptionBase
	 */
	public void ShosuCheck(String strData, int iKetasu, String komokuNm, int flg) throws ExceptionBase{

		//�l�����l�̏ꍇ
		try{

			//BigDecimal�^�ɕϊ��i���l�`�F�b�N�j
			BigDecimal chkDeci = new BigDecimal(strData);

			//�����_�u.�v�����݂���ꍇ�Ƀ`�F�b�N
			if(strData.indexOf(".") > 0){

				//�����`�F�b�N
				int iSize = strData.substring(strData.indexOf(".") + 1).length();

				if (iSize != 0 && iKetasu < iSize){

					//�G���[���b�Z�[�W����
					String errMsg;

					//�V�K�o�^�̏ꍇ
					if(flg == 0){
						errMsg = "�y����f�[�^��ʁz �o�^�i�V�K�j���A���s���܂����B\n";
					}
					//�ҏW�o�^�̏ꍇ
					else{
						errMsg = "�y����f�[�^��ʁz �o�^�i�ҏW�j���A���s���܂����B\n";
					}

					errMsg += komokuNm+"�͏�����"+iKetasu+"���ȓ��œ��͂��ĉ������B";

					//�G���[�ݒ�
					ex = new ExceptionBase();
					ex.setStrErrCd("");
					ex.setStrErrmsg(errMsg);
					ex.setStrErrShori(this.getClass().getName());
					ex.setStrMsgNo("");
					ex.setStrSystemMsg("");

					throw ex;
				}

			}

		}
		//�����`�F�b�N�G���[�̏ꍇ
		catch(ExceptionBase eb){

			throw eb;

		}
		//��d�̒l�����l�łȂ��ꍇ
		catch(Exception e){

			//�������Ȃ�

		}
	}

	/**
	 * �������Z(����\�D)�@�����˗��`�F�b�N�{�b�N�X�ҏW�s����
	 * @throws ExceptionBase
	 */
	private void setGenkaIrai_false() {

		//�������Z(����\�D)�e�[�u���擾
		TableBase GenkaTable = tb.getTrial5Panel().getTable();

		//�������Z(����\�D)�e�[�u���@�񃋁[�v
		for(int i=0; i<GenkaTable.getColumnCount(); i++){

			//�R���|�[�l���g�擾
			MiddleCellEditor selectMc = (MiddleCellEditor)GenkaTable.getCellEditor(2, i);
			DefaultCellEditor selectDc = (DefaultCellEditor)selectMc.getTableCellEditor(2);

			//�����R�[�h�̏ꍇ
			if(selectDc.getComponent() instanceof CheckboxBase){

				//�˗��`�F�b�N�{�b�N�X�擾
				CheckboxBase CheckIrai = (CheckboxBase)selectDc.getComponent();

				//�˗�������̈˗��`�F�b�N�{�b�N�X�͕ҏW�s��
				if(CheckIrai.isSelected()){
					CheckIrai.setEnabled(false);
				}
			}
		}
	}

	/**
	 * �������Z(����\�D)�@�����˗��`�F�b�N�{�b�N�X�ҏW����
	 * @throws ExceptionBase
	 */
	private void setGenkaIrai_true() {

		//�������Z(����\�D)�e�[�u���擾
		TableBase GenkaTable = tb.getTrial5Panel().getTable();

		//�������Z(����\�D)�e�[�u���@�񃋁[�v
		for(int i=0; i<GenkaTable.getColumnCount(); i++){

			//�R���|�[�l���g�擾
			MiddleCellEditor selectMc = (MiddleCellEditor)GenkaTable.getCellEditor(2, i);
			DefaultCellEditor selectDc = (DefaultCellEditor)selectMc.getTableCellEditor(2);

			//�����R�[�h�̏ꍇ
			if(selectDc.getComponent() instanceof CheckboxBase){

				//�˗��`�F�b�N�{�b�N�X�擾
				CheckboxBase CheckIrai = (CheckboxBase)selectDc.getComponent();

				//�˗��`�F�b�N�{�b�N�X�ҏW��
				CheckIrai.setEnabled(true);
				GenkaTable.setValueAt("false", 2, i);
			}
		}
	}


	public ButtonBase getBtnShuryo() {
		return btnShuryo;
	}

	public void setBtnShuryo(ButtonBase btnShuryo) {
		this.btnShuryo = btnShuryo;
	}

	//add start -------------------------------------------------------------------------------
	//QP@00412_�V�T�N�C�b�N���� No.33,34
	public void dispHenshuOkFg() throws ExceptionBase {

		JwsConstManager.JWS_FLG_DISP = true;

		//�l�����l�̏ꍇ
		try{
			//�f�[�^�ݒ�iJW010�j
			this.conJW010();

			//�ҏW�\�t���O�ݒ�i�o�^���j
			DataCtrl.getInstance().getTrialTblData().setShisakuListHenshuOkFg();

	    	//�ĕ\��
	    	int sel = tb.getSelectedIndex();
	    	setPanelData();
	    	tb.setSelectedIndex(sel);

	    	//�ύX�t���O������
	    	DataCtrl.getInstance().getTrialTblData().setHenkouFg(false);
		}catch(ExceptionBase e) {
			throw e;

		}catch(Exception e){
			//�G���[�ݒ�
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			ex.setStrErrmsg("�o�^���̍ĕ\�����������s���܂���");
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getMessage());
			throw ex;
		}finally{

		}
	}
	//add end   -------------------------------------------------------------------------------

//2011/05/11 QP@10181_No.42_49_72 TT T.Nishigawa Add Start -------------------------
	/**
	 * �H���p�^�[���ύX���̍��v�d��d�ʏ�����
	 * @throws ExceptionBase
	 */
	private void clearSiagariZyuryo(String kotei_ptn_value){
		try{
			//���v�d�オ��d�ʏ�����
			if(kotei_ptn_value.equals(JwsConstManager.JWS_KOTEITYPE_3)){

				//���샊�X�g�擾
				TableBase tbListMeisai = tb.getTrial1Panel().getTrial1().getListMeisai();
				TableBase tbListHeader = tb.getTrial1Panel().getTrial1().getListHeader();
				int gokeiShiagariGyo = tb.getTrial1Panel().getTrial1().getHaigoMeisai().getRowCount()-8;
				for(int i=0; i<tbListHeader.getColumnCount(); i++){

					//����SEQ�擾
					MiddleCellEditor selectMcKey = (MiddleCellEditor)tbListHeader.getCellEditor(0, i);
					DefaultCellEditor selectDcKey = (DefaultCellEditor)selectMcKey.getTableCellEditor(0);
					CheckboxBase cb = (CheckboxBase)selectDcKey.getComponent();
					int shisakuSeq = Integer.parseInt(cb.getPk1());

					//�\���l��������
					tbListMeisai.setValueAt("", gokeiShiagariGyo, i);

					//�f�[�^������
					TrialData trialData = (TrialData)DataCtrl.getInstance().getTrialTblData().SearchShisakuRetuData(shisakuSeq).get(0);
					trialData.setDciShiagari(null);
				}
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
//2011/05/11 QP@10181_No.42_49_72 TT T.Nishigawa Add End -------------------------

	/**
	 * �L�����Z���˗��m�F���b�Z�[�W
	 * @throws ExceptionBase
	 */
	private boolean cancelMsg(){
		boolean ret = false;
		try{
			//�L�����Z����ێ�
			String strCancelNo = "";

			//�����f�[�^�擾
			ArrayList addRetu = DataCtrl.getInstance().getTrialTblData().SearchShisakuRetuData(0);
			for(int i=0; i<addRetu.size(); i++){

				//����f�[�^�擾
				TrialData TrialData = (TrialData)addRetu.get(i);

				//�L�����Z��Fg
				String cancelFg = checkNull(TrialData.getFlg_cancel());

				//�L�����Z��Fg���P�̏ꍇ
				if(cancelFg.equals("1")){
					strCancelNo = strCancelNo + "\n" + TrialData.getStrSampleNo();
				}
			}

			//1�����Ȃ��ꍇ
			if(strCancelNo.equals("")){
				ret = true;
			}
			//1���ȏ゠��ꍇ
			else{
				//�_�C�A���O�R���|�[�l���g�ݒ�
				JOptionPane jp = new JOptionPane();
				//�m�F�_�C�A���O�\��
				int option = jp.showConfirmDialog(
						jp.getRootPane(),
						"���L�T���v��No�̌������Z�˗����������܂��B��낵���ł����H" + strCancelNo
						, "�m�F���b�Z�[�W"
						,JOptionPane.YES_NO_OPTION
						,JOptionPane.PLAIN_MESSAGE
					);

				//�u�͂��v����
			    if (option == JOptionPane.YES_OPTION){
			    	ret = true;

			    //�u�������v����
			    }else if (option == JOptionPane.NO_OPTION){
			    	ret = false;
			    }
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return ret;
	}

}
