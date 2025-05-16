package jp.co.blueflag.shisaquick.jws.panel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;

import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableColumn;

import jp.co.blueflag.shisaquick.jws.base.MixedData;
import jp.co.blueflag.shisaquick.jws.base.TrialData;
import jp.co.blueflag.shisaquick.jws.base.XmlData;
import jp.co.blueflag.shisaquick.jws.celleditor.MiddleCellEditor;
import jp.co.blueflag.shisaquick.jws.celleditor.TextAreaCellEditor;
import jp.co.blueflag.shisaquick.jws.celleditor.TextFieldCellEditor;
import jp.co.blueflag.shisaquick.jws.cellrenderer.MiddleCellRenderer;
import jp.co.blueflag.shisaquick.jws.cellrenderer.TextAreaCellRenderer;
import jp.co.blueflag.shisaquick.jws.cellrenderer.TextFieldCellRenderer;
import jp.co.blueflag.shisaquick.jws.common.ButtonBase;
import jp.co.blueflag.shisaquick.jws.common.CheckboxBase;
import jp.co.blueflag.shisaquick.jws.common.ExceptionBase;
import jp.co.blueflag.shisaquick.jws.common.JwsConstManager;
import jp.co.blueflag.shisaquick.jws.common.PanelBase;
import jp.co.blueflag.shisaquick.jws.common.TableBase;
import jp.co.blueflag.shisaquick.jws.common.TextboxBase;
import jp.co.blueflag.shisaquick.jws.data.DataCtrl;
import jp.co.blueflag.shisaquick.jws.label.ItemIndicationLabel;
import jp.co.blueflag.shisaquick.jws.label.ItemLabel;
import jp.co.blueflag.shisaquick.jws.manager.XmlConnection;

/**
 *
 * �����l(����\�A)�p�l���N���X
 *
 */
public class Trial2Panel extends PanelBase {
	private static final long serialVersionUID = 1L;

	//�ꊇ�`�F�b�N/�����@ ActionCommand�l
	private final String IKKATU_CHK = "ikatuChkClick";
	private final String BUNSEKI_CHK = "bunsekiChkClick";

	//QP@20505 2012/11/01 ADD Start
	private TextboxBase txtFreeSuibunKassei;	//��������
	private TextboxBase txtFreeAlchol;			//�A���R�[��
	private TextboxBase txtNendo;				//�S�x
	private TextboxBase txtOndo;				//���x
	private TextboxBase txtFree1;				//
	private TextboxBase txtFree2;				//
	private TextboxBase txtFree3;				//
	private TextboxBase txtFree4;				//
	private TextboxBase txtFree5;				//
	private TextboxBase txtFree6;				//
	//QP@20505 2012/11/01 ADD End
	
	//�`�F�b�N�{�b�N�X
	private CheckboxBase chkAuto;		//�����v�Z
// ADD start 20121017 QP@20505 No.11
	private ButtonBase btnBunsekiMstData;		//���̓}�X�^�̍ŐV��ԂɍX�V
// ADD end 20121017 QP@20505 No.11
	private CheckboxBase chkSo_sho;		//����
	private CheckboxBase chkSui;		//������
	private CheckboxBase chkTodo;		//���x
	private CheckboxBase chkOndo;		//���x
	private CheckboxBase chkPh;			//PH
	private CheckboxBase chkBun;		//����
	private CheckboxBase chkHiju;		//��d
	private CheckboxBase chkKasei;		//��������
	private CheckboxBase chkAruko;		//�A���R�[��
	private CheckboxBase chkFree1;		//�t���[�^�C�g���@
	private CheckboxBase chkFree2;		//�t���[�^�C�g���A
	private CheckboxBase chkFree3;		//�t���[�^�C�g���B
	private CheckboxBase chkAll;		//�ꊇ�`�F�b�Nor����
// ADD start 20120928 QP@20505 No.24
	private CheckboxBase chkFreeSuibunKassei;		//���������t���[
	private CheckboxBase chkFreeAlchol;				//�A���R�[�� �t���[
	private CheckboxBase chkJikkoSakusanNodo;		//�����|�_�Z�x
	private CheckboxBase chkSuisoMSG;		//������MSG
	private CheckboxBase chkFreeNendo;		//�S�x�t���[
	private CheckboxBase chkFreeOndo;				//���x �t���[
	private CheckboxBase chkFree4;		//�t���[�^�C�g���S
	private CheckboxBase chkFree5;		//�t���[�^�C�g���T
	private CheckboxBase chkFree6;		//�t���[�^�C�g���U
// ADD end 20120928 QP@20505 No.24

	//�e�[�u��
	private TableBase table;

	//2011/04/20 QP@10181_No.41 TT T.Satoh Add Start -------------------------
	//�X�N���[���o�[
	private JScrollPane scroll;
	//2011/04/20 QP@10181_No.41 TT T.Satoh Add End ---------------------------

	//2011/05/30�@�yQP@10181_No.42_49_72�z�H���p�^�[������@TT.NISHIGAWA�@start
	private CheckboxBase chkHiju_sui;		//������d
	//2011/05/30�@�yQP@10181_No.42_49_72�z�H���p�^�[������@TT.NISHIGAWA�@end

	private ExceptionBase ex;

	/**
	 *
	 * �����l(����\�A)�p�l���N���X�@�R���X�g���N�^
	 * @throws ExceptionBase
	 *
	 */
	public Trial2Panel() throws ExceptionBase {
		super();

		try {
			//��ʕ\��
			this.panelDisp();
		} catch (ExceptionBase e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			//�G���[�ݒ�
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			//2011/04/12 QP@10181_No.26 TT T.Satoh Change Start -------------------------
			//ex.setStrErrmsg("����\�A�p�l���N���X�@�R���X�g���N�^���������s���܂���");
			ex.setStrErrmsg("�����l�p�l���N���X�@�R���X�g���N�^���������s���܂���");
			//2011/04/12 QP@10181_No.26 TT T.Satoh Change End ---------------------------
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getStackTrace().toString());
			throw ex;
		} finally {
		}

	}

	/**
	 *
	 * �����l(����\�A)�p�l���N���X�@��ʕ\��
	 * @throws ExceptionBase
	 *
	 */
	private void panelDisp() throws ExceptionBase{

		this.setLayout(null);
		this.setBackground(Color.WHITE);

		//����f�[�^���擾
		Object[] aryTrialData = (DataCtrl.getInstance().getTrialTblData().SearchShisakuRetuData(0).toArray());

		//�R���|�[�l���g����������
		this.initComponent(aryTrialData);

		//�e�[�u������������
		this.initTable(aryTrialData);

		//2011/06/07 QP@10181_No.41 TT T.Satoh Add Start -------------------------
		//�����l�̉��X�N���[���o�[�̍ő�l���擾
		int hTokuseiBarMax = this.getScroll().getHorizontalScrollBar().getMaximum();

		//�����l�̉��X�N���[���o�[�̈ʒu��ݒ�
		this.getScroll().getHorizontalScrollBar().setValue(hTokuseiBarMax);
		//2011/06/07 QP@10181_No.41 TT T.Satoh Add End ---------------------------

	}


	/**
	 * �R���|�[�l���g�����������i�e�[�u���������j
	 * @param aryTrialData : ����f�[�^
	 * @throws ExceptionBase
	 */
	private void initComponent(Object[] aryTrialData) throws ExceptionBase {
		TrialData trialData = (TrialData)aryTrialData[0];

		//�v�Z���ڗp������ : (�v�Z)�@�ƕ\������
		String strKeisan = "(" + JwsConstManager.JWS_MARK_0005 + ")";

		try {
			//���͒l�������v�Z
			chkAuto = new CheckboxBase();
			chkAuto.setBackground(Color.WHITE);
			chkAuto.setBounds(11, 5, 25, 16);
			//����e�[�u��.�����v�ZFlg
			if ( trialData.getIntZidoKei() == 1 ) {
				chkAuto.setSelected(true);
			} else {
				chkAuto.setSelected(false);
			}
			//���[�h�ҏW
			if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
					JwsConstManager.JWS_COMPONENT_0055, DataCtrl.getInstance().getParamData().getStrMode())){
				chkAuto.setEnabled(false);
			}else{
				chkAuto.addFocusListener(new FocusCheck("�����v�Z"));
			}
			this.add(chkAuto);

				ItemIndicationLabel iiAuto = new ItemIndicationLabel();
				iiAuto.setBounds(35, 5, 150, 15);
				iiAuto.setText("���͒l�𖈉񎩓��v�Z");
				this.add(iiAuto);

//QP@20505 No.24 2012/09/11 TT H.SHIMA ADD Start
			// �H���p�^�[���擾
			String ptKotei = DataCtrl.getInstance().getTrialTblData().SearchShisakuhinData().getStrPt_kotei();
			String ptValue = "";
			if(ptKotei == null || ptKotei.length() == 0){
			}else{
				ptValue = DataCtrl.getInstance().getLiteralDataKoteiPtn().selectLiteralVal1(ptKotei);
			}
//QP@20505 No.24 2012/09/11 TT H.SHIMA ADD End

			//���ڃ��x���ݒ�iTOP�j
			ItemLabel hlTop = new ItemLabel();
			hlTop.setBorder(new LineBorder(Color.BLACK, 1));
			hlTop.setHorizontalAlignment(SwingConstants.CENTER);
			hlTop.setBounds(15, 25, 180, 18);
			this.add(hlTop);

//QP@20505 No.24 2012/09/11 TT H.SHIMA DEL Start
//			//���ڃ��x���ݒ�i�v�Z���ʕ\���O���[�v�j
//			ItemLabel hlKeisan = new ItemLabel();
//			hlKeisan.setBorder(new LineBorder(Color.BLACK, 1));
//			hlKeisan.setHorizontalAlignment(SwingConstants.CENTER);
//			hlKeisan.setBounds(15, 42, 15, 86);
//			hlKeisan.setText("<html>�v<br> �Z<br> ��<br> ��");
//			this.add(hlKeisan);
//QP@20505 No.24 2012/09/11 TT H.SHIMA DEL End

			//���ڃ��x���ݒ�i���_�j
			ItemLabel hlSosan = new ItemLabel();
			hlSosan.setBorder(new LineBorder(Color.BLACK, 1));
//QP@20505 No.24 2012/09/11 TT H.SHIMA MOD Start
//			hlSosan.setBounds(45, 42, 150, 18);
			hlSosan.setBounds(31, 42, 164, 18);
//QP@20505 No.24 2012/09/11 TT H.SHIMA MOD End
			hlSosan.setText(" ���_�i���j");
			this.add(hlSosan);

			//���ڃ��x���ݒ�i�H���j
			ItemLabel hlShoku = new ItemLabel();
			hlShoku.setBorder(new LineBorder(Color.BLACK, 1));
//QP@20505 No.24 2012/09/11 TT H.SHIMA MOD Start
//			hlShoku.setBounds(45, 59, 150, 18);
			hlShoku.setBounds(31, 59, 164, 18);
//QP@20505 No.24 2012/09/11 TT H.SHIMA MOD End
			hlShoku.setText(" �H���i���j");
			this.add(hlShoku);

			//���_�E�H��
			chkSo_sho = new CheckboxBase();
			chkSo_sho.setBorderPainted(true);
			chkSo_sho.setBorder(new LineBorder(Color.BLACK, 1));
			chkSo_sho.setBackground( JwsConstManager.SHISAKU_ITEM_COLOR );
			chkSo_sho.setHorizontalAlignment(JCheckBox.CENTER);
//QP@20505 No.24 2012/09/11 TT H.SHIMA MOD Start
//				chkSo_sho.setBounds(29, 42, 17, 35);
			chkSo_sho.setBounds(15, 42, 17, 35);
//QP@20505 No.24 2012/09/11 TT H.SHIMA MOD End
			//����e�[�u�� flg
			if ( trialData.getIntSosanFlg() == 1 ) {
				chkSo_sho.setSelected(true);
			} else if (trialData.getIntShokuenFlg() == 1) {
				chkSo_sho.setSelected(true);
			} else {
				chkSo_sho.setSelected(false);
			}
			//���[�h�ҏW
			if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
					JwsConstManager.JWS_COMPONENT_0056, DataCtrl.getInstance().getParamData().getStrMode())){
				chkSo_sho.setEnabled(false);
			}else{
				chkSo_sho.addFocusListener(new FocusCheck("���_�E�H��"));
			}
			this.add(chkSo_sho);

			//���ڃ��x���ݒ�i�������_�x�j
			ItemLabel hlSuiSando = new ItemLabel();
			hlSuiSando.setBorder(new LineBorder(Color.BLACK, 1));
//QP@20505 No.24 2012/09/11 TT H.SHIMA MOD Start
//			hlSuiSando.setBounds(45, 76, 150, 18);
			hlSuiSando.setBounds(31, 76, 164, 18);
//QP@20505 No.24 2012/09/11 TT H.SHIMA MOD End
			hlSuiSando.setText(" �������_�x�i���j");
			this.add(hlSuiSando);

			//���ڃ��x���ݒ�i�������H���j
			ItemLabel hlSuiShoku = new ItemLabel();
			hlSuiShoku.setBorder(new LineBorder(Color.BLACK, 1));
//QP@20505 No.24 2012/09/11 TT H.SHIMA MOD Start
//			hlSuiShoku.setBounds(45, 93, 150, 18);
			hlSuiShoku.setBounds(31, 93, 164, 18);
//QP@20505 No.24 2012/09/11 TT H.SHIMA MOD End
			hlSuiShoku.setText(" �������H���i���j");
			this.add(hlSuiShoku);

			//���ڃ��x���ݒ�i�������|�_�j
			ItemLabel hlSuiSaku = new ItemLabel();
			hlSuiSaku.setBorder(new LineBorder(Color.BLACK, 1));
//QP@20505 No.24 2012/09/11 TT H.SHIMA MOD Start
//			hlSuiSaku.setBounds(45, 110, 150, 18);
			hlSuiSaku.setBounds(31, 110, 164, 18);
//QP@20505 No.24 2012/09/11 TT H.SHIMA MOD End
			hlSuiSaku.setText(" �������|�_�i���j");
			this.add(hlSuiSaku);

			//�������_�x, �������H��, �������|�_
			chkSui = new CheckboxBase();
			chkSui.setBorderPainted(true);
			chkSui.setBorder(new LineBorder(Color.BLACK, 1));
			chkSui.setBackground(JwsConstManager.SHISAKU_ITEM_COLOR);
			chkSui.setHorizontalAlignment(JCheckBox.CENTER);
//QP@20505 No.24 2012/09/11 TT H.SHIMA MOD Start
//				chkSui.setBounds(29, 76, 17, 52);
			chkSui.setBounds(15, 76, 17, 52);
//QP@20505 No.24 2012/09/11 TT H.SHIMA MOD End
			//����e�[�u�� flg
			if ( trialData.getIntSuiSandoFlg() == 1 ) {
				chkSui.setSelected(true);
			} else if ( trialData.getIntSuiShokuenFlg() == 1 ) {
				chkSui.setSelected(true);
			} else if ( trialData.getIntSuiSakusanFlg() == 1 ) {
				chkSui.setSelected(true);
			} else {
				chkSui.setSelected(false);
			}
			//���[�h�ҏW
			if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
					JwsConstManager.JWS_COMPONENT_0057, DataCtrl.getInstance().getParamData().getStrMode())){
				chkSui.setEnabled(false);
			}else{
				chkSui.addFocusListener(new FocusCheck("�������_�x�E�H���E�|�_"));
			}
			this.add(chkSui);

// ADD start 20120927 QP@20505 No.24
			int y_count = 0;
			if(ptValue.equals("") || ptValue.equals(JwsConstManager.JWS_KOTEITYPE_3)){
				// --------  �H���p�^�[���u���̑��E���H�v�܂��́A���I���̏ꍇ  ----------------------------------------------------------------
// ADD start 20120927 QP@20505 No.24
				//���ڃ��x���ݒ�i����l���̓O���[�v�j
				ItemLabel hlSokutei = new ItemLabel();
	//QP@20505 No.24 2012/09/11 TT H.SHIMA DEL Start
	//			hlSokutei.setBorder(new LineBorder(Color.BLACK, 1));
	//			hlSokutei.setHorizontalAlignment(SwingConstants.CENTER);
	//			hlSokutei.setVerticalAlignment(SwingConstants.CENTER);
	//			hlSokutei.setBounds(15, 127, 15, 222);
	//			hlSokutei.setText("<html>��<br>��<br>�l<br>��<br>��");
	//			this.add(hlSokutei);
	//QP@20505 No.24 2012/09/11 TT H.SHIMA DEL End

				//���ڃ��x���ݒ�i���x�j
				ItemLabel hlTodo = new ItemLabel();
				hlTodo.setBorder(new LineBorder(Color.BLACK, 1));
	//QP@20505 No.24 2012/09/11 TT H.SHIMA MOD Start
	//			hlTodo.setBounds(45, 127, 150, 18);
				hlTodo.setBounds(31, 127, 164, 18);
	//QP@20505 No.24 2012/09/11 TT H.SHIMA MOD End
				hlTodo.setText(" ���x");
				this.add(hlTodo);

					//���x
					chkTodo = new CheckboxBase();
					chkTodo.setBorderPainted(true);
					chkTodo.setBorder(new LineBorder(Color.BLACK, 1));
					chkTodo.setBackground(JwsConstManager.SHISAKU_ITEM_COLOR);
					chkTodo.setHorizontalAlignment(JCheckBox.CENTER);
	//QP@20505 No.24 2012/09/11 TT H.SHIMA MOD Start
	//				chkTodo.setBounds(29, 127, 17, 18);
					chkTodo.setBounds(15, 127, 17, 18);
	//QP@20505 No.24 2012/09/11 TT H.SHIMA MOD End
					//����e�[�u�� flg
					if ( trialData.getIntToudoFlg() == 1 ) {
						chkTodo.setSelected(true);
					} else {
						chkTodo.setSelected(false);
					}
					//���[�h�ҏW
					if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
							JwsConstManager.JWS_COMPONENT_0058, DataCtrl.getInstance().getParamData().getStrMode())){
						chkTodo.setEnabled(false);
					}else{
						chkTodo.addFocusListener(new FocusCheck("���x"));
					}
					this.add(chkTodo);

				//���ڃ��x���ݒ�i�S�x�j
				ItemLabel hlNendo = new ItemLabel();
				hlNendo.setBorder(new LineBorder(Color.BLACK, 1));
	//QP@20505 No.24 2012/09/11 TT H.SHIMA MOD Start
	//			hlNendo.setBounds(45, 144, 150, 18);
				hlNendo.setBounds(31, 144, 164, 18);
	//QP@20505 No.24 2012/09/11 TT H.SHIMA MOD End
				hlNendo.setText(" �S�x");
				this.add(hlNendo);

				//���ڃ��x���ݒ�i���x�j
				ItemLabel hlOndo = new ItemLabel();
				hlOndo.setBorder(new LineBorder(Color.BLACK, 1));
	//QP@20505 No.24 2012/09/11 TT H.SHIMA MOD Start
	//			hlOndo.setBounds(45, 161, 150, 18);
				hlOndo.setBounds(31, 161, 164, 18);
	//QP@20505 No.24 2012/09/11 TT H.SHIMA MOD End
				hlOndo.setText(" ���x�i���j");
				this.add(hlOndo);

					//�S�x, ���x
					chkOndo = new CheckboxBase();
					chkOndo.setBorderPainted(true);
					chkOndo.setBorder(new LineBorder(Color.BLACK, 1));
					chkOndo.setBackground(JwsConstManager.SHISAKU_ITEM_COLOR);
					chkOndo.setHorizontalAlignment(JCheckBox.CENTER);
	//QP@20505 No.24 2012/09/11 TT H.SHIMA MOD Start
	//				chkOndo.setBounds(29, 144, 17, 35);
					chkOndo.setBounds(15, 144, 17, 35);
	//QP@20505 No.24 2012/09/11 TT H.SHIMA MOD End
					//����e�[�u�� flg
					if ( trialData.getIntNendoFlg() == 1 ) {
						chkOndo.setSelected(true);
					} else if ( trialData.getIntOndoFlg() == 1 ) {
						chkOndo.setSelected(true);
					} else {
						chkOndo.setSelected(false);
					}
					//���[�h�ҏW
					if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
							JwsConstManager.JWS_COMPONENT_0059, DataCtrl.getInstance().getParamData().getStrMode())){
						chkOndo.setEnabled(false);
					}else{
						chkOndo.addFocusListener(new FocusCheck("�S�x�E���x"));
					}
					this.add(chkOndo);

				//���ڃ��x���ݒ�ipH�j
				ItemLabel hlPh = new ItemLabel();
				hlPh.setBorder(new LineBorder(Color.BLACK, 1));
	//QP@20505 No.24 2012/09/11 TT H.SHIMA MOD Start
	//			hlPh.setBounds(45, 178, 150, 18);
				hlPh.setBounds(31, 178, 164, 18);
	//QP@20505 No.24 2012/09/11 TT H.SHIMA MOD End
				hlSokutei.setVerticalAlignment(SwingConstants.CENTER);
				hlPh.setText(" pH");
				this.add(hlPh);

					//PH
					chkPh = new CheckboxBase();
					chkPh.setBorderPainted(true);
					chkPh.setBorder(new LineBorder(Color.BLACK, 1));
					chkPh.setBackground(JwsConstManager.SHISAKU_ITEM_COLOR);
					chkPh.setHorizontalAlignment(JCheckBox.CENTER);
	//QP@20505 No.24 2012/09/11 TT H.SHIMA MOD Start
	//				chkPh.setBounds(29, 178, 17, 18);
					chkPh.setBounds(15, 178, 17, 18);
	//QP@20505 No.24 2012/09/11 TT H.SHIMA MOD End
					//����e�[�u�� flg
					if ( trialData.getIntPhFlg() == 1 ) {
						chkPh.setSelected(true);
					} else {
						chkPh.setSelected(false);
					}
					//���[�h�ҏW
					if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
							JwsConstManager.JWS_COMPONENT_0060, DataCtrl.getInstance().getParamData().getStrMode())){
						chkPh.setEnabled(false);
					}else{
						chkPh.addFocusListener(new FocusCheck("PH"));
					}
					this.add(chkPh);

				//���ڃ��x���ݒ�i���_�i���j�F���́j
				ItemLabel hlBun_sosan = new ItemLabel();
				hlBun_sosan.setBorder(new LineBorder(Color.BLACK, 1));
	//QP@20505 No.24 2012/09/11 TT H.SHIMA MOD Start
	//			hlBun_sosan.setBounds(45, 195, 150, 18);
				hlBun_sosan.setBounds(31, 195, 164, 18);
	//QP@20505 No.24 2012/09/11 TT H.SHIMA MOD End
				hlBun_sosan.setVerticalAlignment(SwingConstants.CENTER);
				hlBun_sosan.setText(" ���_�i���j�F����");
				this.add(hlBun_sosan);

				//���ڃ��x���ݒ�i�H���i���j�F���́j
				ItemLabel hlBun_shoku = new ItemLabel();
				hlBun_shoku.setBorder(new LineBorder(Color.BLACK, 1));
	//QP@20505 No.24 2012/09/11 TT H.SHIMA MOD Start
	//			hlBun_shoku.setBounds(45, 212, 150, 18);
				hlBun_shoku.setBounds(31, 212, 164, 18);
	//QP@20505 No.24 2012/09/11 TT H.SHIMA MOD End
				hlBun_shoku.setVerticalAlignment(SwingConstants.CENTER);
				hlBun_shoku.setText(" �H���i���j�F����");
				this.add(hlBun_shoku);

					//���_����, �H������
					chkBun = new CheckboxBase();
					chkBun.setBorderPainted(true);
					chkBun.setBorder(new LineBorder(Color.BLACK, 1));
					chkBun.setBackground(JwsConstManager.SHISAKU_ITEM_COLOR);
					chkBun.setHorizontalAlignment(JCheckBox.CENTER);
	//QP@20505 No.24 2012/09/11 TT H.SHIMA MOD Start
	//				chkBun.setBounds(29, 195, 17, 35);
					chkBun.setBounds(15, 195, 17, 35);
	//QP@20505 No.24 2012/09/11 TT H.SHIMA MOD End
					//����e�[�u�� flg
					if ( trialData.getIntSosanBunsekiFlg() == 1 ) {
						chkBun.setSelected(true);
					} else if ( trialData.getIntShokuenBunsekiFlg() == 1 ) {
						chkBun.setSelected(true);
					} else {
						chkBun.setSelected(false);
					}
					//���[�h�ҏW
					if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
							JwsConstManager.JWS_COMPONENT_0061, DataCtrl.getInstance().getParamData().getStrMode())){
						chkBun.setEnabled(false);
					}else{
						chkBun.addFocusListener(new FocusCheck("���_���́E�H������"));
					}
					this.add(chkBun);

				//���ڃ��x���ݒ�i��d�j
				ItemLabel hlHiju = new ItemLabel();
				hlHiju.setBorder(new LineBorder(Color.BLACK, 1));
	//QP@20505 No.24 2012/09/11 TT H.SHIMA MOD Start
	//			hlHiju.setBounds(45, 229, 150, 18);
				hlHiju.setBounds(31, 229, 164, 18);
	//QP@20505 No.24 2012/09/11 TT H.SHIMA MOD End
				hlHiju.setVerticalAlignment(SwingConstants.CENTER);
	//2011/05/30�@�yQP@10181_No.42_49_72�z�H���p�^�[������@TT.NISHIGAWA�@start
				hlHiju.setText(" ���i��d�i���Ȃ�P�j" + strKeisan);
	//2011/05/30�@�yQP@10181_No.42_49_72�z�H���p�^�[������@TT.NISHIGAWA�@end
				this.add(hlHiju);

					//��d
					chkHiju = new CheckboxBase();
					chkHiju.setBorderPainted(true);
					chkHiju.setBorder(new LineBorder(Color.BLACK, 1));
					chkHiju.setBackground(JwsConstManager.SHISAKU_ITEM_COLOR);
					chkHiju.setHorizontalAlignment(JCheckBox.CENTER);
	//QP@20505 No.24 2012/09/11 TT H.SHIMA MOD Start
	//				chkHiju.setBounds(29, 229, 17, 18);
					chkHiju.setBounds(15, 229, 17, 18);
	//QP@20505 No.24 2012/09/11 TT H.SHIMA MOD End
					//����e�[�u�� flg
					if ( trialData.getIntHizyuFlg() == 1 ) {
						chkHiju.setSelected(true);
					} else {
						chkHiju.setSelected(false);
					}
					//���[�h�ҏW
					if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
							JwsConstManager.JWS_COMPONENT_0062, DataCtrl.getInstance().getParamData().getStrMode())){
						chkHiju.setEnabled(false);
					}else{
						chkHiju.addFocusListener(new FocusCheck("��d"));
					}
					this.add(chkHiju);


	//2011/05/30�@�yQP@10181_No.42_49_72�z�H���p�^�[������@TT.NISHIGAWA�@start
// MOD start 20120928 QP@20505 No.24
//					int y_count = 229 + 17;
					y_count = 229 + 17;
// MOD end 20120928 QP@20505 No.24

					//���ڃ��x���ݒ�i��d�j
					ItemLabel hlHiju_sui = new ItemLabel();
					hlHiju_sui.setBorder(new LineBorder(Color.BLACK, 1));
	//QP@20505 No.24 2012/09/11 TT H.SHIMA MOD Start
	//				hlHiju_sui.setBounds(45, y_count, 150, 18);
					hlHiju_sui.setBounds(31, y_count, 164, 18);
	//QP@20505 No.24 2012/09/11 TT H.SHIMA MOD End
					hlHiju_sui.setVerticalAlignment(SwingConstants.CENTER);
					hlHiju_sui.setText(" ������d");
					this.add(hlHiju_sui);

						//��d
						chkHiju_sui = new CheckboxBase();
						chkHiju_sui.setBorderPainted(true);
						chkHiju_sui.setBorder(new LineBorder(Color.BLACK, 1));
						chkHiju_sui.setBackground(JwsConstManager.SHISAKU_ITEM_COLOR);
						chkHiju_sui.setHorizontalAlignment(JCheckBox.CENTER);
	//QP@20505 No.24 2012/09/11 TT H.SHIMA MOD Start
	//					chkHiju_sui.setBounds(29, y_count, 17, 18);
						chkHiju_sui.setBounds(15, y_count, 17, 18);
	//QP@20505 No.24 2012/09/11 TT H.SHIMA MOD End
						//����e�[�u�� flg
						if ( trialData.getIntHiju_sui_fg() == 1 ) {
							chkHiju_sui.setSelected(true);
						} else {
							chkHiju_sui.setSelected(false);
						}
						//���[�h�ҏW
						if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
								JwsConstManager.JWS_COMPONENT_0154, DataCtrl.getInstance().getParamData().getStrMode())){
							chkHiju_sui.setEnabled(false);
						}else{
							chkHiju_sui.addFocusListener(new FocusCheck("������d"));
						}
						this.add(chkHiju_sui);
	//2011/05/30�@�yQP@10181_No.42_49_72�z�H���p�^�[������@TT.NISHIGAWA�@end

// MOD start 20121025 QP@20505 No.24
//				//���ڃ��x���ݒ�i���������j
//				y_count = y_count + 17;
//				ItemLabel hlKasei = new ItemLabel();
//				hlKasei.setBorder(new LineBorder(Color.BLACK, 1));
//				hlKasei.setBounds(31, y_count, 164, 18);
//				hlKasei.setVerticalAlignment(SwingConstants.CENTER);
//				hlKasei.setText(" ��������");
//				this.add(hlKasei);
				//�e�L�X�g�ݒ�i���������t���[�j
				y_count = y_count + 17;
				txtFreeSuibunKassei = new TextboxBase();
				txtFreeSuibunKassei.setBackground(Color.WHITE);
				txtFreeSuibunKassei.setBorder(new LineBorder(Color.BLACK, 1));
				txtFreeSuibunKassei.setBounds(31, y_count, 164, 18);
				//����e�[�u��
				if (trialData.getStrFreeTitleSuibunKasei() == null){
// MOD start 20130215 QP@20505
	// MOD start 20121128 QP@20505 �ۑ�No,11
	//				txtFreeSuibunKassei.setText("��������");
//					txtFreeSuibunKassei.setText("");
	// MOD end 20121128 QP@20505 �ۑ�No,11
				txtFreeSuibunKassei.setText("��������");
// MOD end 20130215 QP@20505
				}else{
					txtFreeSuibunKassei.setText(trialData.getStrFreeTitleSuibunKasei());
				}
				//���[�h�ҏW
				if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
						JwsConstManager.JWS_COMPONENT_0160, DataCtrl.getInstance().getParamData().getStrMode())){
					txtFreeSuibunKassei.setEditable(false);
				}else{
					txtFreeSuibunKassei.addFocusListener(new FocusCheck("���������t���[�^�C�g��"));
				}
				this.add(txtFreeSuibunKassei);
// MOD start 20121025 QP@20505 No.24

					//��������
// MOD start 20121025 QP@20505 No.24
//					chkKasei = new CheckboxBase();
//					chkKasei.setBorderPainted(true);
//					chkKasei.setBorder(new LineBorder(Color.BLACK, 1));
//					chkKasei.setBackground(JwsConstManager.SHISAKU_ITEM_COLOR);
//					chkKasei.setHorizontalAlignment(JCheckBox.CENTER);
//					chkKasei.setBounds(29, y_count, 17, 18);
//					//����e�[�u�� flg
//					if ( trialData.getIntSuibunFlg() == 1 ) {
//						chkKasei.setSelected(true);
//					} else {
//						chkKasei.setSelected(false);
//					}
//					//���[�h�ҏW
//					if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
//							JwsConstManager.JWS_COMPONENT_0063, DataCtrl.getInstance().getParamData().getStrMode())){
//						chkKasei.setEnabled(false);
//					}else{
//						chkKasei.addFocusListener(new FocusCheck("���������t���[�o��"));
//					}
//					this.add(chkKasei);
					chkFreeSuibunKassei = new CheckboxBase();
					chkFreeSuibunKassei.setBorderPainted(true);
					chkFreeSuibunKassei.setBorder(new LineBorder(Color.BLACK, 1));
					chkFreeSuibunKassei.setBackground(JwsConstManager.SHISAKU_ITEM_COLOR);
					chkFreeSuibunKassei.setHorizontalAlignment(JCheckBox.CENTER);
					chkFreeSuibunKassei.setBounds(15, y_count, 17, 18);
					if ( trialData.getIntFreeSuibunKaseiFlg() == 1 ) {
						chkFreeSuibunKassei.setSelected(true);
					} else {
						chkFreeSuibunKassei.setSelected(false);
					}
					//���[�h�ҏW
					if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
							JwsConstManager.JWS_COMPONENT_0063, DataCtrl.getInstance().getParamData().getStrMode())){
						chkFreeSuibunKassei.setEnabled(false);
					}else{
						chkFreeSuibunKassei.addFocusListener(new FocusCheck("���������t���[�o��"));
					}
					this.add(chkFreeSuibunKassei);
// MOD end 20121025 QP@20505 No.24


// MOD start 20121025 QP@20505 No.24
//				//���ڃ��x���ݒ�i�A���R�[���j
//				y_count = y_count + 17;
//				ItemLabel hlAruko = new ItemLabel();
//				hlAruko.setBorder(new LineBorder(Color.BLACK, 1));
//				hlAruko.setBounds(45, y_count, 150, 18);
//				hlAruko.setVerticalAlignment(SwingConstants.CENTER);
//				hlAruko.setText(" �A���R�[��");
//				this.add(hlAruko);
				//�e�L�X�g�ݒ�i�A���R�[�� �t���[�j
				y_count = y_count + 17;
				txtFreeAlchol = new TextboxBase();
				txtFreeAlchol.setBackground(Color.WHITE);
				txtFreeAlchol.setBorder(new LineBorder(Color.BLACK, 1));
				txtFreeAlchol.setBounds(31, y_count, 164, 18);
				//����e�[�u��
				if (trialData.getStrFreeTitleAlchol() == null){
// MOD start 20130215 QP@20505
	// MOD start 20121128 QP@20505 �ۑ�No,11
	//				txtFreeAlchol.setText("�A���R�[��");
//					txtFreeAlchol.setText("");
	// MOD end 20121128 QP@20505 �ۑ�No,11
					txtFreeAlchol.setText("�A���R�[��");
// MOD end 20130215 QP@20505
				}else{
					txtFreeAlchol.setText(trialData.getStrFreeTitleAlchol());
				}
				//���[�h�ҏW
				if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
						JwsConstManager.JWS_COMPONENT_0163, DataCtrl.getInstance().getParamData().getStrMode())){
					txtFreeAlchol.setEditable(false);
				}else{
					txtFreeAlchol.addFocusListener(new FocusCheck("�A���R�[���t���[�^�C�g��"));
				}
				this.add(txtFreeAlchol);
// MOD end 20121025 QP@20505 No.24

					//�A���R�[��
// MOD start 20121025 QP@20505 No.24
//					chkAruko = new CheckboxBase();
//					chkAruko.setBorderPainted(true);
//					chkAruko.setBorder(new LineBorder(Color.BLACK, 1));
//					chkAruko.setBackground(JwsConstManager.SHISAKU_ITEM_COLOR);
//					chkAruko.setHorizontalAlignment(JCheckBox.CENTER);
//					chkAruko.setBounds(29, y_count, 17, 18);
//					//����e�[�u�� flg
//					if ( trialData.getIntArukoruFlg() == 1 ) {
//						chkAruko.setSelected(true);
//					} else {
//						chkAruko.setSelected(false);
//					}
//					//���[�h�ҏW
//					if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
//							JwsConstManager.JWS_COMPONENT_0064, DataCtrl.getInstance().getParamData().getStrMode())){
//						chkAruko.setEnabled(false);
//					}else{
//						chkAruko.addFocusListener(new FocusCheck("�A���R�[��"));
//					}
//					this.add(chkAruko);
					chkFreeAlchol = new CheckboxBase();
					chkFreeAlchol.setBorderPainted(true);
					chkFreeAlchol.setBorder(new LineBorder(Color.BLACK, 1));
					chkFreeAlchol.setBackground(JwsConstManager.SHISAKU_ITEM_COLOR);
					chkFreeAlchol.setHorizontalAlignment(JCheckBox.CENTER);
					chkFreeAlchol.setBounds(15, y_count, 17, 18);
					//����e�[�u�� flg
					if ( trialData.getIntFreeAlcholFlg() == 1 ) {
						chkFreeAlchol.setSelected(true);
					} else {
						chkFreeAlchol.setSelected(false);
					}
					//���[�h�ҏW
					if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
							JwsConstManager.JWS_COMPONENT_0064, DataCtrl.getInstance().getParamData().getStrMode())){
						chkFreeAlchol.setEnabled(false);
					}else{
						chkFreeAlchol.addFocusListener(new FocusCheck("�A���R�[���t���[�o��"));
					}
					this.add(chkFreeAlchol);
// MOD start 20121025 QP@20505 No.24


				//�e�L�X�g�ݒ�i�t���[�^�C�g���@�j
				y_count = y_count + 17;
				txtFree1 = new TextboxBase();
				txtFree1.setBackground(Color.WHITE);
				txtFree1.setBorder(new LineBorder(Color.BLACK, 1));
	//QP@20505 No.24 2012/09/11 TT H.SHIMA MOD Start
	//			txtFree1.setBounds(45, y_count, 150, 18);
				txtFree1.setBounds(31, y_count, 164, 18);
	//QP@20505 No.24 2012/09/11 TT H.SHIMA MOD End
				//����e�[�u���@�t���[�^�C�g��
				txtFree1.setText(trialData.getStrFreeTitle1());
				//���[�h�ҏW
				if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
						JwsConstManager.JWS_COMPONENT_0069, DataCtrl.getInstance().getParamData().getStrMode())){
					txtFree1.setEditable(false);
				}else{
					txtFree1.addFocusListener(new FocusCheck("�t���[�^�C�g��1"));
				}
				this.add(txtFree1);

					//�t���[�^�C�g���@
					chkFree1 = new CheckboxBase();
					chkFree1.setBorderPainted(true);
					chkFree1.setBorder(new LineBorder(Color.BLACK, 1));
					chkFree1.setBackground(JwsConstManager.SHISAKU_ITEM_COLOR);
					chkFree1.setHorizontalAlignment(JCheckBox.CENTER);
	//QP@20505 No.24 2012/09/11 TT H.SHIMA MOD Start
	//				chkFree1.setBounds(29, y_count, 17, 18);
					chkFree1.setBounds(15, y_count, 17, 18);
	//QP@20505 No.24 2012/09/11 TT H.SHIMA MOD End
					//����e�[�u�� flg
					if ( trialData.getIntFreeFlg() == 1 ) {
						chkFree1.setSelected(true);
					} else {
						chkFree1.setSelected(false);
					}
					//���[�h�ҏW
					if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
							JwsConstManager.JWS_COMPONENT_0065, DataCtrl.getInstance().getParamData().getStrMode())){
						chkFree1.setEnabled(false);
					}else{
						chkFree1.addFocusListener(new FocusCheck("�t���[�^�C�g��1Fg"));
					}
					this.add(chkFree1);

				//�e�L�X�g�ݒ�i�t���[�^�C�g���A�j
				y_count = y_count + 17;
				txtFree2 = new TextboxBase();
				txtFree2.setBackground(Color.WHITE);
				txtFree2.setBorder(new LineBorder(Color.BLACK, 1));
	//QP@20505 No.24 2012/09/11 TT H.SHIMA MOD Start
	//			txtFree2.setBounds(45, y_count, 150, 18);
				txtFree2.setBounds(31, y_count, 164, 18);
	//QP@20505 No.24 2012/09/11 TT H.SHIMA MOD End
				//����e�[�u���@�t���[�^�C�g��
				txtFree2.setText(trialData.getStrFreeTitle2());
				//���[�h�ҏW
				if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
						JwsConstManager.JWS_COMPONENT_0070, DataCtrl.getInstance().getParamData().getStrMode())){
					txtFree2.setEditable(false);
				}else{
					txtFree2.addFocusListener(new FocusCheck("�t���[�^�C�g��2"));
				}
				this.add(txtFree2);

					//�t���[�^�C�g���A
					chkFree2 = new CheckboxBase();
					chkFree2.setBorderPainted(true);
					chkFree2.setBorder(new LineBorder(Color.BLACK, 1));
					chkFree2.setBackground(JwsConstManager.SHISAKU_ITEM_COLOR);
					chkFree2.setHorizontalAlignment(JCheckBox.CENTER);
	//QP@20505 No.24 2012/09/11 TT H.SHIMA MOD Start
	//				chkFree2.setBounds(29, y_count, 17, 18);
					chkFree2.setBounds(15, y_count, 17, 18);
	//QP@20505 No.24 2012/09/11 TT H.SHIMA MOD End
					//����e�[�u�� flg
					if ( trialData.getIntFreeFl2() == 1 ) {
						chkFree2.setSelected(true);
					} else {
						chkFree2.setSelected(false);
					}
					//���[�h�ҏW
					if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
							JwsConstManager.JWS_COMPONENT_0066, DataCtrl.getInstance().getParamData().getStrMode())){
						chkFree2.setEnabled(false);
					}else{
						chkFree2.addFocusListener(new FocusCheck("�t���[�^�C�g��2Fg"));
					}
					this.add(chkFree2);

				//�e�L�X�g�ݒ�i�t���[�^�C�g���B�j
				y_count = y_count + 17;
				txtFree3 = new TextboxBase();
				txtFree3.setBackground(Color.WHITE);
				txtFree3.setBorder(new LineBorder(Color.BLACK, 1));
	//QP@20505 No.24 2012/09/11 TT H.SHIMA MOD Start
	//			txtFree3.setBounds(45, y_count, 150, 18);
				txtFree3.setBounds(31, y_count, 164, 18);
	//QP@20505 No.24 2012/09/11 TT H.SHIMA MOD End
				//����e�[�u���@�t���[�^�C�g��
				txtFree3.setText(trialData.getStrFreeTitle3());
				//���[�h�ҏW
				if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
						JwsConstManager.JWS_COMPONENT_0071, DataCtrl.getInstance().getParamData().getStrMode())){
					txtFree3.setEditable(false);
				}else{
					txtFree3.addFocusListener(new FocusCheck("�t���[�^�C�g��3"));
				}
				this.add(txtFree3);

					//�t���[�^�C�g���B
					chkFree3 = new CheckboxBase();
					chkFree3.setBorderPainted(true);
					chkFree3.setBorder(new LineBorder(Color.BLACK, 1));
					chkFree3.setBackground(JwsConstManager.SHISAKU_ITEM_COLOR);
					chkFree3.setHorizontalAlignment(JCheckBox.CENTER);
	//QP@20505 No.24 2012/09/11 TT H.SHIMA MOD Start
	//				chkFree3.setBounds(29, y_count, 17, 18);
					chkFree3.setBounds(15, y_count, 17, 18);
	//QP@20505 No.24 2012/09/11 TT H.SHIMA MOD End
					//����e�[�u�� flg
					if ( trialData.getIntFreeFl3() == 1 ) {
						chkFree3.setSelected(true);
					} else {
						chkFree3.setSelected(false);
					}
					//���[�h�ҏW
					if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
							JwsConstManager.JWS_COMPONENT_0067, DataCtrl.getInstance().getParamData().getStrMode())){
						chkFree3.setEnabled(false);
					}else{
						chkFree3.addFocusListener(new FocusCheck("�t���[�^�C�g��3Fg"));
					}
					this.add(chkFree3);
// ADD start 20120927 QP@20505 No.24
			}else{
				// --------  �H���p�^�[���u�P�t�v�u�Q�t�v�̏ꍇ  -----------------------------------------------------------------------------
				y_count = 110;

				//���ڃ��x���ݒ�i����l���̓O���[�v�j
				ItemLabel hlSokutei = new ItemLabel();

				//���ڃ��x���ݒ�i�����|�_�Z�x�j
				y_count = y_count + 17;
				ItemLabel hlJSN = new ItemLabel();
				hlJSN.setBorder(new LineBorder(Color.BLACK, 1));
				hlJSN.setBounds(31, y_count, 164, 18);
				hlJSN.setText(" �����|�_�Z�x�i���j");
				this.add(hlJSN);

					chkJikkoSakusanNodo = new CheckboxBase();
					chkJikkoSakusanNodo.setBorderPainted(true);
					chkJikkoSakusanNodo.setBorder(new LineBorder(Color.BLACK, 1));
					chkJikkoSakusanNodo.setBackground(JwsConstManager.SHISAKU_ITEM_COLOR);
					chkJikkoSakusanNodo.setHorizontalAlignment(JCheckBox.CENTER);
					chkJikkoSakusanNodo.setBounds(15, y_count, 17, 18);
					//����e�[�u�� flg
					if ( trialData.getIntJikkoSakusanNodoFlg() == 1 ) {
						chkJikkoSakusanNodo.setSelected(true);
					} else {
						chkJikkoSakusanNodo.setSelected(false);
					}
					//���[�h�ҏW
					if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
							JwsConstManager.JWS_COMPONENT_0167, DataCtrl.getInstance().getParamData().getStrMode())){
						chkJikkoSakusanNodo.setEnabled(false);
					}else{
						chkJikkoSakusanNodo.addFocusListener(new FocusCheck("�����|�_�Z�x"));
					}
					this.add(chkJikkoSakusanNodo);

				//���ڃ��x���ݒ�i�������l�r�f�j
				y_count = y_count + 17;
				ItemLabel hlSuiMSG = new ItemLabel();
				hlSuiMSG.setBorder(new LineBorder(Color.BLACK, 1));
				hlSuiMSG.setBounds(31, y_count, 164, 18);
				hlSuiMSG.setText(" �������l�r�f�i���j");
				this.add(hlSuiMSG);

					chkSuisoMSG = new CheckboxBase();
					chkSuisoMSG.setBorderPainted(true);
					chkSuisoMSG.setBorder(new LineBorder(Color.BLACK, 1));
					chkSuisoMSG.setBackground(JwsConstManager.SHISAKU_ITEM_COLOR);
					chkSuisoMSG.setHorizontalAlignment(JCheckBox.CENTER);
					chkSuisoMSG.setBounds(15, y_count, 17, 18);
					//����e�[�u�� flg
					if ( trialData.getIntSuisoMSGFlg() == 1 ) {
						chkSuisoMSG.setSelected(true);
					} else {
						chkSuisoMSG.setSelected(false);
					}
					//���[�h�ҏW
					if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
							JwsConstManager.JWS_COMPONENT_0169, DataCtrl.getInstance().getParamData().getStrMode())){
						chkSuisoMSG.setEnabled(false);
					}else{
						chkSuisoMSG.addFocusListener(new FocusCheck("�������l�r�f"));
					}
					this.add(chkSuisoMSG);

				//���ڃ��x���ݒ�i���g�j
				y_count = y_count + 17;
				ItemLabel hlPh = new ItemLabel();
				hlPh.setBorder(new LineBorder(Color.BLACK, 1));
				hlPh.setBounds(31, y_count, 164, 18);
				hlPh.setText(" pH");
				this.add(hlPh);

					chkPh = new CheckboxBase();
					chkPh.setBorderPainted(true);
					chkPh.setBorder(new LineBorder(Color.BLACK, 1));
					chkPh.setBackground(JwsConstManager.SHISAKU_ITEM_COLOR);
					chkPh.setHorizontalAlignment(JCheckBox.CENTER);
					chkPh.setBounds(15, y_count, 17, 18);
					//����e�[�u�� flg
					if ( trialData.getIntPhFlg() == 1 ) {
						chkPh.setSelected(true);
					} else {
						chkPh.setSelected(false);
					}
					//���[�h�ҏW
					if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
							JwsConstManager.JWS_COMPONENT_0060, DataCtrl.getInstance().getParamData().getStrMode())){
						chkPh.setEnabled(false);
					}else{
						chkPh.addFocusListener(new FocusCheck("PH"));
					}
					this.add(chkPh);

				//���ڃ��x���ݒ�i���i��d�j
				y_count = y_count + 17;
				ItemLabel hlHiju = new ItemLabel();
				hlHiju.setBorder(new LineBorder(Color.BLACK, 1));
				hlHiju.setBounds(31, y_count, 164, 18);
				hlHiju.setVerticalAlignment(SwingConstants.CENTER);
				hlHiju.setText(" ���i��d�i���Ȃ�P�j" + strKeisan);
				this.add(hlHiju);

					chkHiju = new CheckboxBase();
					chkHiju.setBorderPainted(true);
					chkHiju.setBorder(new LineBorder(Color.BLACK, 1));
					chkHiju.setBackground(JwsConstManager.SHISAKU_ITEM_COLOR);
					chkHiju.setHorizontalAlignment(JCheckBox.CENTER);
					chkHiju.setBounds(15, y_count, 17, 18);
					//����e�[�u�� flg
					if ( trialData.getIntHizyuFlg() == 1 ) {
						chkHiju.setSelected(true);
					} else {
						chkHiju.setSelected(false);
					}
					//���[�h�ҏW
					if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
							JwsConstManager.JWS_COMPONENT_0062, DataCtrl.getInstance().getParamData().getStrMode())){
						chkHiju.setEnabled(false);
					}else{
						chkHiju.addFocusListener(new FocusCheck("��d"));
					}
					this.add(chkHiju);

				//���ڃ��x���ݒ�i������d�j
				y_count = y_count + 17;
				ItemLabel hlHiju_sui = new ItemLabel();
				hlHiju_sui.setBorder(new LineBorder(Color.BLACK, 1));
				hlHiju_sui.setBounds(31, y_count, 164, 18);
				hlHiju_sui.setVerticalAlignment(SwingConstants.CENTER);
				hlHiju_sui.setText(" ������d");
				this.add(hlHiju_sui);

					chkHiju_sui = new CheckboxBase();
					chkHiju_sui.setBorderPainted(true);
					chkHiju_sui.setBorder(new LineBorder(Color.BLACK, 1));
					chkHiju_sui.setBackground(JwsConstManager.SHISAKU_ITEM_COLOR);
					chkHiju_sui.setHorizontalAlignment(JCheckBox.CENTER);
					chkHiju_sui.setBounds(15, y_count, 17, 18);
					//����e�[�u�� flg
					if ( trialData.getIntHiju_sui_fg() == 1 ) {
						chkHiju_sui.setSelected(true);
					} else {
						chkHiju_sui.setSelected(false);
					}
					//���[�h�ҏW
					if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
							JwsConstManager.JWS_COMPONENT_0154, DataCtrl.getInstance().getParamData().getStrMode())){
						chkHiju_sui.setEnabled(false);
					}else{
						chkHiju_sui.addFocusListener(new FocusCheck("������d"));
					}
					this.add(chkHiju_sui);

				//���ڃ��x���ݒ�i�S�x �t���[�^�C�g���j
				y_count = y_count + 17;
				txtNendo = new TextboxBase();
				txtNendo.setBackground(Color.WHITE);
				txtNendo.setBorder(new LineBorder(Color.BLACK, 1));
				txtNendo.setBounds(31, y_count, 164, 18);
				if (trialData.getStrFreeTitleNendo() == null){
// MOD start 20130215 QP@20505
	// MOD start 20121128 QP@20505 �ۑ�No.11
	//				txtNendo.setText("�S�x");
//					txtNendo.setText("");
	// MOD end 20121128 QP@20505 �ۑ�No.11
				txtNendo.setText("�S�x");
// MOD end 20130215 QP@20505
				}else{
					txtNendo.setText(trialData.getStrFreeTitleNendo());
				}
				//���[�h�ҏW
				if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
						JwsConstManager.JWS_COMPONENT_0170, DataCtrl.getInstance().getParamData().getStrMode())){
					txtNendo.setEditable(false);
				}else{
					txtNendo.addFocusListener(new FocusCheck("�S�x�t���[�^�C�g��"));
				}
				this.add(txtNendo);

				//���ڃ��x���ݒ�i���x �t���[�^�C�g���j
				y_count = y_count + 17;
				txtOndo = new TextboxBase();
				txtOndo.setBackground(Color.WHITE);
				txtOndo.setBorder(new LineBorder(Color.BLACK, 1));
				txtOndo.setBounds(31, y_count, 164, 18);
				if (trialData.getStrFreeTitleOndo() == null){
// MOD start 20130215 QP@20505
	// MOD start 20121128 QP@20505 �ۑ�No.11
	//				txtOndo.setText("���x�i���j");
//					txtOndo.setText("");
	// MOD end 20121128 QP@20505 �ۑ�No.11
				txtOndo.setText("���x�i���j");
// MOD end 20130215 QP@20505
				}else{
					txtOndo.setText(trialData.getStrFreeTitleOndo());
				}
				//���[�h�ҏW
				if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
						JwsConstManager.JWS_COMPONENT_0173, DataCtrl.getInstance().getParamData().getStrMode())){
					txtOndo.setEditable(false);
				}else{
					txtOndo.addFocusListener(new FocusCheck("���x�t���[�^�C�g��"));
				}
				this.add(txtOndo);

					chkFreeNendo = new CheckboxBase();
					chkFreeNendo.setBorderPainted(true);
					chkFreeNendo.setBorder(new LineBorder(Color.BLACK, 1));
					chkFreeNendo.setBackground(JwsConstManager.SHISAKU_ITEM_COLOR);
					chkFreeNendo.setHorizontalAlignment(JCheckBox.CENTER);
					chkFreeNendo.setBounds(15, y_count - 17, 17, 35);
					//����e�[�u�� flg
					if ( trialData.getIntFreeNendoFlg() == 1 ) {
						chkFreeNendo.setSelected(true);
					} else if ( trialData.getIntFreeOndoFlg() == 1 ) {
						chkFreeNendo.setSelected(true);
					} else {
						chkFreeNendo.setSelected(false);
					}
					//���[�h�ҏW
					if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
							JwsConstManager.JWS_COMPONENT_0172, DataCtrl.getInstance().getParamData().getStrMode())){
						chkFreeNendo.setEnabled(false);
					}else{
						chkFreeNendo.addFocusListener(new FocusCheck("�S�x�t���[�o��"));
					}
					this.add(chkFreeNendo);

				//�e�L�X�g�ݒ�i�t���[�^�C�g���@�j
				y_count = y_count + 17;
				txtFree1 = new TextboxBase();
				txtFree1.setBackground(Color.WHITE);
				txtFree1.setBorder(new LineBorder(Color.BLACK, 1));
				txtFree1.setBounds(31, y_count, 164, 18);
				//����e�[�u���@�t���[�^�C�g��
				txtFree1.setText(trialData.getStrFreeTitle1());
				//���[�h�ҏW
				if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
						JwsConstManager.JWS_COMPONENT_0069, DataCtrl.getInstance().getParamData().getStrMode())){
					txtFree1.setEditable(false);
				}else{
					txtFree1.addFocusListener(new FocusCheck("�t���[�^�C�g��1"));
				}
				this.add(txtFree1);

					//�t���[�^�C�g���@
					chkFree1 = new CheckboxBase();
					chkFree1.setBorderPainted(true);
					chkFree1.setBorder(new LineBorder(Color.BLACK, 1));
					chkFree1.setBackground(JwsConstManager.SHISAKU_ITEM_COLOR);
					chkFree1.setHorizontalAlignment(JCheckBox.CENTER);
					chkFree1.setBounds(15, y_count, 17, 18);
					//����e�[�u�� flg
					if ( trialData.getIntFreeFlg() == 1 ) {
						chkFree1.setSelected(true);
					} else {
						chkFree1.setSelected(false);
					}
					//���[�h�ҏW
					if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
							JwsConstManager.JWS_COMPONENT_0065, DataCtrl.getInstance().getParamData().getStrMode())){
						chkFree1.setEnabled(false);
					}else{
						chkFree1.addFocusListener(new FocusCheck("�t���[�^�C�g��1Fg"));
					}
					this.add(chkFree1);

				//�e�L�X�g�ݒ�i�t���[�^�C�g���A�j
				y_count = y_count + 17;
				txtFree2 = new TextboxBase();
				txtFree2.setBackground(Color.WHITE);
				txtFree2.setBorder(new LineBorder(Color.BLACK, 1));
				txtFree2.setBounds(31, y_count, 164, 18);
				//����e�[�u���@�t���[�^�C�g��
				txtFree2.setText(trialData.getStrFreeTitle2());
				//���[�h�ҏW
				if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
						JwsConstManager.JWS_COMPONENT_0070, DataCtrl.getInstance().getParamData().getStrMode())){
					txtFree2.setEditable(false);
				}else{
					txtFree2.addFocusListener(new FocusCheck("�t���[�^�C�g��2"));
				}
				this.add(txtFree2);

					//�t���[�^�C�g���A
					chkFree2 = new CheckboxBase();
					chkFree2.setBorderPainted(true);
					chkFree2.setBorder(new LineBorder(Color.BLACK, 1));
					chkFree2.setBackground(JwsConstManager.SHISAKU_ITEM_COLOR);
					chkFree2.setHorizontalAlignment(JCheckBox.CENTER);
					chkFree2.setBounds(15, y_count, 17, 18);
					//����e�[�u�� flg
					if ( trialData.getIntFreeFl2() == 1 ) {
						chkFree2.setSelected(true);
					} else {
						chkFree2.setSelected(false);
					}
					//���[�h�ҏW
					if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
							JwsConstManager.JWS_COMPONENT_0066, DataCtrl.getInstance().getParamData().getStrMode())){
						chkFree2.setEnabled(false);
					}else{
						chkFree2.addFocusListener(new FocusCheck("�t���[�^�C�g��2Fg"));
					}
					this.add(chkFree2);

				//�e�L�X�g�ݒ�i�t���[�^�C�g���B�j
				y_count = y_count + 17;
				txtFree3 = new TextboxBase();
				txtFree3.setBackground(Color.WHITE);
				txtFree3.setBorder(new LineBorder(Color.BLACK, 1));
				txtFree3.setBounds(31, y_count, 164, 18);
				//����e�[�u���@�t���[�^�C�g��
				txtFree3.setText(trialData.getStrFreeTitle3());
				//���[�h�ҏW
				if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
						JwsConstManager.JWS_COMPONENT_0071, DataCtrl.getInstance().getParamData().getStrMode())){
					txtFree3.setEditable(false);
				}else{
					txtFree3.addFocusListener(new FocusCheck("�t���[�^�C�g��3"));
				}
				this.add(txtFree3);

					//�t���[�^�C�g���B
					chkFree3 = new CheckboxBase();
					chkFree3.setBorderPainted(true);
					chkFree3.setBorder(new LineBorder(Color.BLACK, 1));
					chkFree3.setBackground(JwsConstManager.SHISAKU_ITEM_COLOR);
					chkFree3.setHorizontalAlignment(JCheckBox.CENTER);
					chkFree3.setBounds(15, y_count, 17, 18);
					//����e�[�u�� flg
					if ( trialData.getIntFreeFl3() == 1 ) {
						chkFree3.setSelected(true);
					} else {
						chkFree3.setSelected(false);
					}
					//���[�h�ҏW
					if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
							JwsConstManager.JWS_COMPONENT_0067, DataCtrl.getInstance().getParamData().getStrMode())){
						chkFree3.setEnabled(false);
					}else{
						chkFree3.addFocusListener(new FocusCheck("�t���[�^�C�g��3Fg"));
					}
					this.add(chkFree3);

				//�e�L�X�g�ݒ�i�t���[�^�C�g���C�j
				y_count = y_count + 17;
				txtFree4 = new TextboxBase();
				txtFree4.setBackground(Color.WHITE);
				txtFree4.setBorder(new LineBorder(Color.BLACK, 1));
				txtFree4.setBounds(31, y_count, 164, 18);
				//����e�[�u���@�t���[�^�C�g��
				txtFree4.setText(trialData.getStrFreeTitle4());
				//���[�h�ҏW
				if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
						JwsConstManager.JWS_COMPONENT_0176, DataCtrl.getInstance().getParamData().getStrMode())){
					txtFree4.setEditable(false);
				}else{
					txtFree4.addFocusListener(new FocusCheck("�t���[�^�C�g��4"));
				}
				this.add(txtFree4);

					//�t���[�^�C�g���C
					chkFree4 = new CheckboxBase();
					chkFree4.setBorderPainted(true);
					chkFree4.setBorder(new LineBorder(Color.BLACK, 1));
					chkFree4.setBackground(JwsConstManager.SHISAKU_ITEM_COLOR);
					chkFree4.setHorizontalAlignment(JCheckBox.CENTER);
					chkFree4.setBounds(15, y_count, 17, 18);
					//����e�[�u�� flg
					if ( trialData.getIntFreeFlg4() == 1 ) {
						chkFree4.setSelected(true);
					} else {
						chkFree4.setSelected(false);
					}
					//���[�h�ҏW
					if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
							JwsConstManager.JWS_COMPONENT_0178, DataCtrl.getInstance().getParamData().getStrMode())){
						chkFree4.setEnabled(false);
					}else{
						chkFree4.addFocusListener(new FocusCheck("�t���[�^�C�g��4Fg"));
					}
					this.add(chkFree4);

				//�e�L�X�g�ݒ�i�t���[�^�C�g���D�j
				y_count = y_count + 17;
				txtFree5 = new TextboxBase();
				txtFree5.setBackground(Color.WHITE);
				txtFree5.setBorder(new LineBorder(Color.BLACK, 1));
				txtFree5.setBounds(31, y_count, 164, 18);
				//����e�[�u���@�t���[�^�C�g��
				txtFree5.setText(trialData.getStrFreeTitle5());
				//���[�h�ҏW
				if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
						JwsConstManager.JWS_COMPONENT_0179, DataCtrl.getInstance().getParamData().getStrMode())){
					txtFree5.setEditable(false);
				}else{
					txtFree5.addFocusListener(new FocusCheck("�t���[�^�C�g��5"));
				}
				this.add(txtFree5);

					//�t���[�^�C�g���D
					chkFree5 = new CheckboxBase();
					chkFree5.setBorderPainted(true);
					chkFree5.setBorder(new LineBorder(Color.BLACK, 1));
					chkFree5.setBackground(JwsConstManager.SHISAKU_ITEM_COLOR);
					chkFree5.setHorizontalAlignment(JCheckBox.CENTER);
					chkFree5.setBounds(15, y_count, 17, 18);
					//����e�[�u�� flg
					if ( trialData.getIntFreeFlg5() == 1 ) {
						chkFree5.setSelected(true);
					} else {
						chkFree5.setSelected(false);
					}
					//���[�h�ҏW
					if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
							JwsConstManager.JWS_COMPONENT_0181, DataCtrl.getInstance().getParamData().getStrMode())){
						chkFree5.setEnabled(false);
					}else{
						chkFree5.addFocusListener(new FocusCheck("�t���[�^�C�g��5Fg"));
					}
					this.add(chkFree5);

				//�e�L�X�g�ݒ�i�t���[�^�C�g���E�j
				y_count = y_count + 17;
				txtFree6 = new TextboxBase();
				txtFree6.setBackground(Color.WHITE);
				txtFree6.setBorder(new LineBorder(Color.BLACK, 1));
				txtFree6.setBounds(31, y_count, 164, 18);
				//����e�[�u���@�t���[�^�C�g��
				txtFree6.setText(trialData.getStrFreeTitle6());
				//���[�h�ҏW
				if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
						JwsConstManager.JWS_COMPONENT_0182, DataCtrl.getInstance().getParamData().getStrMode())){
					txtFree6.setEditable(false);
				}else{
					txtFree6.addFocusListener(new FocusCheck("�t���[�^�C�g��6"));
				}
				this.add(txtFree6);

					//�t���[�^�C�g���E
					chkFree6 = new CheckboxBase();
					chkFree6.setBorderPainted(true);
					chkFree6.setBorder(new LineBorder(Color.BLACK, 1));
					chkFree6.setBackground(JwsConstManager.SHISAKU_ITEM_COLOR);
					chkFree6.setHorizontalAlignment(JCheckBox.CENTER);
					chkFree6.setBounds(15, y_count, 17, 18);
					//����e�[�u�� flg
					if ( trialData.getIntFreeFlg6() == 1 ) {
						chkFree6.setSelected(true);
					} else {
						chkFree6.setSelected(false);
					}
					//���[�h�ҏW
					if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
							JwsConstManager.JWS_COMPONENT_0184, DataCtrl.getInstance().getParamData().getStrMode())){
						chkFree6.setEnabled(false);
					}else{
						chkFree6.addFocusListener(new FocusCheck("�t���[�^�C�g��6Fg"));
					}
					this.add(chkFree6);
			}

			y_count = 331;
// ADD end 20120927 QP@20505 No.24

			//���ڃ��x���ݒ�i�쐬�����j
			y_count = y_count + 17;
			ItemLabel hlSakusei = new ItemLabel();
			hlSakusei.setBorder(new LineBorder(Color.BLACK, 1));
			hlSakusei.setBounds(15, y_count, 180, 91);
			hlSakusei.setVerticalAlignment(SwingConstants.CENTER);
			hlSakusei.setText(" �쐬����");
			this.add(hlSakusei);

			//���ڃ��x���ݒ�i�]���j
			y_count = y_count + 90;
			ItemLabel hlHyoka = new ItemLabel();
			hlHyoka.setBorder(new LineBorder(Color.BLACK, 1));
			hlHyoka.setBounds(15, y_count, 180, 91);
			hlHyoka.setVerticalAlignment(SwingConstants.CENTER);
			hlHyoka.setText(" �]��");
			this.add(hlHyoka);

			//�ꊇ�`�F�b�N
			chkAll = new CheckboxBase();
			chkAll.setBackground(Color.WHITE);
			chkAll.setBounds(11, 555, 25, 16);
			chkAll.setSelected(false);
			chkAll.addActionListener(this.getActionEvent());
			chkAll.setActionCommand(this.IKKATU_CHK);
			//���[�h�ҏW
			if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
					JwsConstManager.JWS_COMPONENT_0068, DataCtrl.getInstance().getParamData().getStrMode())){
				chkAll.setEnabled(false);
			}else{
				chkAll.addFocusListener(new FocusCheck("�ꊇ�`�F�b�N"));
			}
			this.add(chkAll);

				ItemIndicationLabel iiAll = new ItemIndicationLabel();
				iiAll.setBounds(35, 555, 150, 15);
				iiAll.setText("�ꊇ�`�F�b�N�^����");
				this.add(iiAll);

			//���͒l�̕ύX�m�F
			ButtonBase btnHankou = new ButtonBase("���͒l�̕ύX�m�F");
			btnHankou.setBounds(194, 555, 180, 20);
			btnHankou.addActionListener(this.getActionEvent());
			btnHankou.setActionCommand(this.BUNSEKI_CHK);
			//���[�h�ҏW
			if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
					JwsConstManager.JWS_COMPONENT_0091, DataCtrl.getInstance().getParamData().getStrMode())){
				btnHankou.setEnabled(false);
			}
			this.add(btnHankou);

// ADD start 20121016 QP@20505 No.11
			//���͒l�̕ύX�m�F
			btnBunsekiMstData = new ButtonBase("���̓}�X�^�̍ŐV���ɍX�V");
			btnBunsekiMstData.setBounds(394, 555, 180, 20);
			//���[�h�ҏW
			if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
					JwsConstManager.JWS_COMPONENT_0155, DataCtrl.getInstance().getParamData().getStrMode())){
				btnBunsekiMstData.setEnabled(false);
			}
			this.add(btnBunsekiMstData);
// ADD end 20121016 QP@20505 No.11

		} catch (Exception e) {
			//�G���[�ݒ�
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			//2011/04/12 QP@10181_No.26 TT T.Satoh Change Start -------------------------
			//ex.setStrErrmsg("����\�A �R���|�[�l���g���������������s���܂���");
			ex.setStrErrmsg("�����l �R���|�[�l���g���������������s���܂���");
			//2011/04/12 QP@10181_No.26 TT T.Satoh Change End ---------------------------
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getMessage());
			throw ex;
		} finally {
		}
	}

	/**
	 * �e�[�u������������
	 * @param aryTrialData : ����f�[�^
	 * @throws ExceptionBase
	 */
	private void initTable(Object[] aryTrialData) throws ExceptionBase {
		TrialData trialData;
		try {
			//�s�E��
			int intRowCount = 21;
			int intColumnCount = aryTrialData.length;

			//�e�[�u������
			//2011/04/20 QP@10181_No.41 TT T.Satoh Change Start -------------------------
			//JScrollPane scroll;
			//2011/04/20 QP@10181_No.41 TT T.Satoh Change End ---------------------------
			table = new TableBase(intRowCount,intColumnCount){
				//--------------------�@�����f�[�^�X�V�@----------------------------
				public void editingStopped( ChangeEvent e ){
					try{
						super.editingStopped( e );

		// ADD start 20120927 QP@20505 No.24 �H���p�^�[���ʕ\���`��
						//�H���p�^�[���擾
						String ptKotei = DataCtrl.getInstance().getTrialTblData().SearchShisakuhinData().getStrPt_kotei();
						String ptValue = "";
						if(ptKotei == null || ptKotei.length() <= 0){
						}else{
							//�H���p�^�[�����󔒂ł͂Ȃ��ꍇ�AValue1�擾
							ptValue = DataCtrl.getInstance().getLiteralDataKoteiPtn().selectLiteralVal1(ptKotei);
						}
		// ADD end 20120927 QP@20505 No.24 �H���p�^�[���ʕ\���`��

						//�ҏW�s��ԍ��擾
						int row = getSelectedRow();
						int column = getSelectedColumn();
						if( row>=0 && column>=0 ){
							//�L�[���ڎ擾
							MiddleCellEditor mceSeq = (MiddleCellEditor)this.getCellEditor(0, column);
							DefaultCellEditor dceSeq = (DefaultCellEditor)mceSeq.getTableCellEditor(0);
							TextboxBase chkSeq = (TextboxBase)dceSeq.getComponent();
							int intSeq  = Integer.parseInt(chkSeq.getPk1());
							//���_
					    	if(row == 1){
					    		//���[�h�ҏW
								if(DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
										JwsConstManager.JWS_COMPONENT_0072, DataCtrl.getInstance().getParamData().getStrMode())){
									String insert = getValueAt( row, column ).toString();
						    		DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuSousan(
						    				intSeq,
						    				DataCtrl.getInstance().getTrialTblData().checkNullDecimal(insert),
						    				DataCtrl.getInstance().getUserMstData().getDciUserid()
						    			);
								}
					    	}
					    	//�H��
					    	if(row == 2){
					    		//���[�h�ҏW
								if(DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
										JwsConstManager.JWS_COMPONENT_0073, DataCtrl.getInstance().getParamData().getStrMode())){
									String insert = getValueAt( row, column ).toString();
						    		DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuSyokuen(
						    				intSeq,
						    				DataCtrl.getInstance().getTrialTblData().checkNullDecimal(insert),
						    				DataCtrl.getInstance().getUserMstData().getDciUserid()
						    			);
								}
					    	}
					    	//�������_�x
					    	if(row == 3){
					    		//���[�h�ҏW
								if(DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
										JwsConstManager.JWS_COMPONENT_0074, DataCtrl.getInstance().getParamData().getStrMode())){
									String insert = getValueAt( row, column ).toString();
						    		DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuSuisoSando(
						    				intSeq,
						    				DataCtrl.getInstance().getTrialTblData().checkNullDecimal(insert),
						    				DataCtrl.getInstance().getUserMstData().getDciUserid()
						    			);
								}
					    	}
					    	//�������H��
					    	if(row == 4){
					    		//���[�h�ҏW
								if(DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
										JwsConstManager.JWS_COMPONENT_0075, DataCtrl.getInstance().getParamData().getStrMode())){
									String insert = getValueAt( row, column ).toString();
						    		DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuSuisoSyokuen(
						    				intSeq,
						    				DataCtrl.getInstance().getTrialTblData().checkNullDecimal(insert),
						    				DataCtrl.getInstance().getUserMstData().getDciUserid()
						    			);
								}
					    	}
					    	//�������|�_
					    	if(row == 5){
					    		//���[�h�ҏW
								if(DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
										JwsConstManager.JWS_COMPONENT_0076, DataCtrl.getInstance().getParamData().getStrMode())){
									String insert = getValueAt( row, column ).toString();
						    		DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuSuisoSakusan(
						    				intSeq,
						    				DataCtrl.getInstance().getTrialTblData().checkNullDecimal(insert),
						    				DataCtrl.getInstance().getUserMstData().getDciUserid()
						    			);
								}
					    	}

// ADD start 20120927 QP@20505 No.24
							if(ptValue.equals("") || ptValue.equals(JwsConstManager.JWS_KOTEITYPE_3)){
								// �H���p�^�[���u���̑��E���H�v�܂��́A���I���̏ꍇ
// ADD start 20120927 QP@20505 No.24
						    	//���x
						    	if(row == 6){
						    		//���[�h�ҏW
									if(DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
											JwsConstManager.JWS_COMPONENT_0077, DataCtrl.getInstance().getParamData().getStrMode())){
										String insert = getValueAt( row, column ).toString();
							    		DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuToudo(
							    				intSeq,
							    				DataCtrl.getInstance().getTrialTblData().checkNullString(insert),
							    				DataCtrl.getInstance().getUserMstData().getDciUserid()
							    			);
									}
						    	}
						    	//�S�x
						    	if(row == 7){
						    		//���[�h�ҏW
									if(DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
											JwsConstManager.JWS_COMPONENT_0078, DataCtrl.getInstance().getParamData().getStrMode())){
										String insert = getValueAt( row, column ).toString();
							    		DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuNendo(
							    				intSeq,
							    				DataCtrl.getInstance().getTrialTblData().checkNullString(insert),
							    				DataCtrl.getInstance().getUserMstData().getDciUserid()
							    			);
									}
						    	}
						    	//���x
						    	if(row == 8){
						    		//���[�h�ҏW
									if(DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
											JwsConstManager.JWS_COMPONENT_0079, DataCtrl.getInstance().getParamData().getStrMode())){
										String insert = getValueAt( row, column ).toString();
							    		DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuOndo(
							    				intSeq,
							    				DataCtrl.getInstance().getTrialTblData().checkNullString(insert),
							    				DataCtrl.getInstance().getUserMstData().getDciUserid()
							    			);
									}
						    	}
						    	//PH
						    	if(row == 9){
						    		//���[�h�ҏW
									if(DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
											JwsConstManager.JWS_COMPONENT_0080, DataCtrl.getInstance().getParamData().getStrMode())){
										String insert = getValueAt( row, column ).toString();
							    		DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuPh(
							    				intSeq,
							    				DataCtrl.getInstance().getTrialTblData().checkNullString(insert),
							    				DataCtrl.getInstance().getUserMstData().getDciUserid()
							    			);
									}
						    	}
						    	//���_����
						    	if(row == 10){
						    		//���[�h�ҏW
									if(DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
											JwsConstManager.JWS_COMPONENT_0081, DataCtrl.getInstance().getParamData().getStrMode())){
										String insert = getValueAt( row, column ).toString();
							    		DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuSousanBunseki(
							    				intSeq,
							    				DataCtrl.getInstance().getTrialTblData().checkNullString(insert),
							    				DataCtrl.getInstance().getUserMstData().getDciUserid()
							    			);
									}
						    	}
						    	//�H������
						    	if(row == 11){
						    		//���[�h�ҏW
									if(DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
											JwsConstManager.JWS_COMPONENT_0082, DataCtrl.getInstance().getParamData().getStrMode())){
										String insert = getValueAt( row, column ).toString();
							    		DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuSyokuenBunseki(
							    				intSeq,
							    				DataCtrl.getInstance().getTrialTblData().checkNullString(insert),
							    				DataCtrl.getInstance().getUserMstData().getDciUserid()
							    			);
									}
						    	}
						    	//��d
						    	if(row == 12){
						    		//���[�h�ҏW
									if(DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
											JwsConstManager.JWS_COMPONENT_0083, DataCtrl.getInstance().getParamData().getStrMode())){
										String insert = getValueAt( row, column ).toString();
							    		DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuHiju(
							    				intSeq,
							    				DataCtrl.getInstance().getTrialTblData().checkNullString(insert),
							    				DataCtrl.getInstance().getUserMstData().getDciUserid()
							    			);

							    		//���������f�[�^�@��d �X�V
							    		DataCtrl.getInstance().getTrialTblData().UpdGenkaValue(
							    				intSeq,
							    				DataCtrl.getInstance().getTrialTblData().checkNullString(insert),
							    				DataCtrl.getInstance().getUserMstData().getDciUserid(),
							    				JwsConstManager.JWS_COMPONENT_0139
							    			);

	//2011/05/30�@�yQP@10181_No.42_49_72�z�H���p�^�[������@TT.NISHIGAWA�@start
							    		//�[�U�ʂ��v�Z
										String keisan1 = DataCtrl.getInstance().getTrialTblData().KeisanZyutenType1(intSeq);
										DataCtrl.getInstance().getTrialTblData().UpdGenkaValue(
							    				intSeq,
							    				DataCtrl.getInstance().getTrialTblData().checkNullString(keisan1),
							    				DataCtrl.getInstance().getUserMstData().getDciUserid(),
							    				JwsConstManager.JWS_COMPONENT_0134);
	//2011/05/30�@�yQP@10181_No.42_49_72�z�H���p�^�[������@TT.NISHIGAWA�@End

									}
						    	}

	//2011/05/30�@�yQP@10181_No.42_49_72�z�H���p�^�[������@TT.NISHIGAWA�@start
						    	//������d
						    	if(row == 13){
						    		//���[�h�ҏW
									if(DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
											JwsConstManager.JWS_COMPONENT_0154, DataCtrl.getInstance().getParamData().getStrMode())){
										String insert = getValueAt( row, column ).toString();
							    		DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuSuiHiju(
							    				intSeq,
							    				DataCtrl.getInstance().getTrialTblData().checkNullString(insert),
							    				DataCtrl.getInstance().getUserMstData().getDciUserid()
							    			);

							    		//���i��d���v�Z
							    		String keisan = DataCtrl.getInstance().getTrialTblData().KeisanSeihinHiju(intSeq);
							    		DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuHiju(
							    				intSeq,
							    				DataCtrl.getInstance().getTrialTblData().checkNullString(keisan),
							    				DataCtrl.getInstance().getUserMstData().getDciUserid()
							    			);
							    		//�\���l�ݒ�
							    		this.setValueAt(keisan, 12, column);

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
						    	}
	//2011/05/30�@�yQP@10181_No.42_49_72�z�H���p�^�[������@TT.NISHIGAWA�@start

						    	//��������
						    	if(row == 14){
						    		//���[�h�ҏW
									if(DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
											JwsConstManager.JWS_COMPONENT_0084, DataCtrl.getInstance().getParamData().getStrMode())){
										String insert = getValueAt( row, column ).toString();
							    		DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuFreeSuibunKasei(
							    				intSeq,
							    				DataCtrl.getInstance().getTrialTblData().checkNullString(insert),
							    				DataCtrl.getInstance().getUserMstData().getDciUserid()
							    			);
									}
						    	}
						    	//�A���R�[��
						    	if(row == 15){
						    		//���[�h�ҏW
									if(DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
											JwsConstManager.JWS_COMPONENT_0085, DataCtrl.getInstance().getParamData().getStrMode())){
										String insert = getValueAt( row, column ).toString();
							    		DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuFreeAlchol(
							    				intSeq,
							    				DataCtrl.getInstance().getTrialTblData().checkNullString(insert),
							    				DataCtrl.getInstance().getUserMstData().getDciUserid()
							    			);
									}
						    	}
						    	//�t���[���e1
						    	if(row == 16){
						    		//���[�h�ҏW
									if(DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
											JwsConstManager.JWS_COMPONENT_0086, DataCtrl.getInstance().getParamData().getStrMode())){
										String insert = getValueAt( row, column ).toString();
							    		DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuFreeNaiyou_1(
							    				intSeq,
							    				DataCtrl.getInstance().getTrialTblData().checkNullString(insert),
							    				DataCtrl.getInstance().getUserMstData().getDciUserid()
							    			);
									}
						    	}
						    	//�t���[���e2
						    	if(row == 17){
						    		//���[�h�ҏW
									if(DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
											JwsConstManager.JWS_COMPONENT_0087, DataCtrl.getInstance().getParamData().getStrMode())){
										String insert = getValueAt( row, column ).toString();
							    		DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuFreeNaiyou_2(
							    				intSeq,
							    				DataCtrl.getInstance().getTrialTblData().checkNullString(insert),
							    				DataCtrl.getInstance().getUserMstData().getDciUserid()
							    			);
									}
						    	}
						    	//�t���[���e3
						    	if(row == 18){
						    		//���[�h�ҏW
									if(DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
											JwsConstManager.JWS_COMPONENT_0088, DataCtrl.getInstance().getParamData().getStrMode())){
										String insert = getValueAt( row, column ).toString();
							    		DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuFreeNaiyou_3(
							    				intSeq,
							    				DataCtrl.getInstance().getTrialTblData().checkNullString(insert),
							    				DataCtrl.getInstance().getUserMstData().getDciUserid()
							    			);
									}
						    	}
// ADD start 20120928 QP@20505 No.24
							}else{
								// ---------  �H���p�^�[�� �P�t�E�Q�t�̏ꍇ  --------------------------------------------------------------
						    	//�����|�_�Z�x
						    	if(row == 6){
						    		//���[�h�ҏW
									if(DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
											JwsConstManager.JWS_COMPONENT_0166, DataCtrl.getInstance().getParamData().getStrMode())){
										String insert = getValueAt( row, column ).toString();
							    		DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuJikkoSakusanNodo(
							    				intSeq,
							    				DataCtrl.getInstance().getTrialTblData().checkNullDecimal(insert),
							    				DataCtrl.getInstance().getUserMstData().getDciUserid()
							    			);
									}
						    	}
						    	//�������l�r�f
						    	if(row == 7){
						    		//���[�h�ҏW
									if(DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
											JwsConstManager.JWS_COMPONENT_0168, DataCtrl.getInstance().getParamData().getStrMode())){
										String insert = getValueAt( row, column ).toString();
							    		DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuSuisoMSG(
							    				intSeq,
							    				DataCtrl.getInstance().getTrialTblData().checkNullDecimal(insert),
							    				DataCtrl.getInstance().getUserMstData().getDciUserid()
							    			);
									}
						    	}
						    	//PH
						    	if(row == 8){
						    		//���[�h�ҏW
									if(DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
											JwsConstManager.JWS_COMPONENT_0080, DataCtrl.getInstance().getParamData().getStrMode())){
										String insert = getValueAt( row, column ).toString();
							    		DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuPh(
							    				intSeq,
							    				DataCtrl.getInstance().getTrialTblData().checkNullString(insert),
							    				DataCtrl.getInstance().getUserMstData().getDciUserid()
							    			);
							    		//QP@20505 2012/10/25 No24 Start
							    		//�����v�Z���`�F�b�N����Ă���ꍇ�̂ݏ���
							    		if(getChkAuto().isSelected()){
							    			//�����|�_�Z�x
							    			String jikkoSakusan = DataCtrl.getInstance().getTrialTblData().KeisanJikkoSakusanNodo(intSeq).toString();
							    			DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuJikkoSakusanNodo(
							    					intSeq,
							    					DataCtrl.getInstance().getTrialTblData().checkNullDecimal(jikkoSakusan),
							    					DataCtrl.getInstance().getUserMstData().getDciUserid()
							    				);
							    			//�\���l�ݒ�
							    			this.setValueAt(jikkoSakusan, 6, column);
							    			
							    		}
							    		//QP@20505 2012/10/25 No24 End
									}
						    	}
						    	//��d
						    	if(row == 9){
						    		//���[�h�ҏW
									if(DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
											JwsConstManager.JWS_COMPONENT_0083, DataCtrl.getInstance().getParamData().getStrMode())){
										String insert = getValueAt( row, column ).toString();
							    		DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuHiju(
							    				intSeq,
							    				DataCtrl.getInstance().getTrialTblData().checkNullString(insert),
							    				DataCtrl.getInstance().getUserMstData().getDciUserid()
							    			);

							    		//���������f�[�^�@��d �X�V
							    		DataCtrl.getInstance().getTrialTblData().UpdGenkaValue(
							    				intSeq,
							    				DataCtrl.getInstance().getTrialTblData().checkNullString(insert),
							    				DataCtrl.getInstance().getUserMstData().getDciUserid(),
							    				JwsConstManager.JWS_COMPONENT_0139
							    			);
							    		//�[�U�ʂ��v�Z
										String keisan1 = DataCtrl.getInstance().getTrialTblData().KeisanZyutenType1(intSeq);
										DataCtrl.getInstance().getTrialTblData().UpdGenkaValue(
							    				intSeq,
							    				DataCtrl.getInstance().getTrialTblData().checkNullString(keisan1),
							    				DataCtrl.getInstance().getUserMstData().getDciUserid(),
							    				JwsConstManager.JWS_COMPONENT_0134);
									}
						    	}
						    	//������d
						    	if(row == 10){
						    		//���[�h�ҏW
									if(DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
											JwsConstManager.JWS_COMPONENT_0154, DataCtrl.getInstance().getParamData().getStrMode())){
										String insert = getValueAt( row, column ).toString();
							    		DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuSuiHiju(
							    				intSeq,
							    				DataCtrl.getInstance().getTrialTblData().checkNullString(insert),
							    				DataCtrl.getInstance().getUserMstData().getDciUserid()
							    			);

							    		//���i��d���v�Z
							    		String keisan = DataCtrl.getInstance().getTrialTblData().KeisanSeihinHiju(intSeq);
							    		DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuHiju(
							    				intSeq,
							    				DataCtrl.getInstance().getTrialTblData().checkNullString(keisan),
							    				DataCtrl.getInstance().getUserMstData().getDciUserid()
							    			);
							    		//�\���l�ݒ�
				// MOD start 20121010 QP@20505 No.24
				//			    		this.setValueAt(keisan, 12, column);
							    		this.setValueAt(keisan, 9, column);
				// MOD start 20121010 QP@20505 No.24

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
						    	}
						    	//�S�x �t���[
						    	if(row == 11){
						    		//���[�h�ҏW
									if(DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
											JwsConstManager.JWS_COMPONENT_0171, DataCtrl.getInstance().getParamData().getStrMode())){
										String insert = getValueAt( row, column ).toString();
							    		DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuFreeNendo(
							    				intSeq,
							    				DataCtrl.getInstance().getTrialTblData().checkNullString(insert),
							    				DataCtrl.getInstance().getUserMstData().getDciUserid()
							    			);
									}
						    	}
						    	//���x �t���[
						    	if(row == 12){
						    		//���[�h�ҏW
									if(DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
											JwsConstManager.JWS_COMPONENT_0174, DataCtrl.getInstance().getParamData().getStrMode())){
										String insert = getValueAt( row, column ).toString();
							    		DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuFreeOndo(
							    				intSeq,
							    				DataCtrl.getInstance().getTrialTblData().checkNullString(insert),
							    				DataCtrl.getInstance().getUserMstData().getDciUserid()
							    			);
									}
						    	}
						    	//�t���[���e1
						    	if(row == 13){
						    		//���[�h�ҏW
									if(DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
											JwsConstManager.JWS_COMPONENT_0086, DataCtrl.getInstance().getParamData().getStrMode())){
										String insert = getValueAt( row, column ).toString();
							    		DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuFreeNaiyou_1(
							    				intSeq,
							    				DataCtrl.getInstance().getTrialTblData().checkNullString(insert),
							    				DataCtrl.getInstance().getUserMstData().getDciUserid()
							    			);
									}
						    	}
						    	//�t���[���e2
						    	if(row == 14){
						    		//���[�h�ҏW
									if(DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
											JwsConstManager.JWS_COMPONENT_0087, DataCtrl.getInstance().getParamData().getStrMode())){
										String insert = getValueAt( row, column ).toString();
							    		DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuFreeNaiyou_2(
							    				intSeq,
							    				DataCtrl.getInstance().getTrialTblData().checkNullString(insert),
							    				DataCtrl.getInstance().getUserMstData().getDciUserid()
							    			);
									}
						    	}
						    	//�t���[���e3
						    	if(row == 15){
						    		//���[�h�ҏW
									if(DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
											JwsConstManager.JWS_COMPONENT_0088, DataCtrl.getInstance().getParamData().getStrMode())){
										String insert = getValueAt( row, column ).toString();
							    		DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuFreeNaiyou_3(
							    				intSeq,
							    				DataCtrl.getInstance().getTrialTblData().checkNullString(insert),
							    				DataCtrl.getInstance().getUserMstData().getDciUserid()
							    			);
									}
						    	}
						    	//�t���[���e4
						    	if(row == 16){
						    		//���[�h�ҏW
									if(DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
											JwsConstManager.JWS_COMPONENT_0177, DataCtrl.getInstance().getParamData().getStrMode())){
										String insert = getValueAt( row, column ).toString();
							    		DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuFreeNaiyou_4(
							    				intSeq,
							    				DataCtrl.getInstance().getTrialTblData().checkNullString(insert),
							    				DataCtrl.getInstance().getUserMstData().getDciUserid()
							    			);
									}
						    	}
						    	//�t���[���e5
						    	if(row == 17){
						    		//���[�h�ҏW
									if(DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
											JwsConstManager.JWS_COMPONENT_0180, DataCtrl.getInstance().getParamData().getStrMode())){
										String insert = getValueAt( row, column ).toString();
							    		DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuFreeNaiyou_5(
							    				intSeq,
							    				DataCtrl.getInstance().getTrialTblData().checkNullString(insert),
							    				DataCtrl.getInstance().getUserMstData().getDciUserid()
							    			);
									}
						    	}
						    	//�t���[���e6
						    	if(row == 18){
						    		//���[�h�ҏW
									if(DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
											JwsConstManager.JWS_COMPONENT_0183, DataCtrl.getInstance().getParamData().getStrMode())){
										String insert = getValueAt( row, column ).toString();
							    		DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuFreeNaiyou_6(
							    				intSeq,
							    				DataCtrl.getInstance().getTrialTblData().checkNullString(insert),
							    				DataCtrl.getInstance().getUserMstData().getDciUserid()
							    			);
									}
						    	}
							}
// ADD end 20120928 QP@20505 No.24

					    	//�쐬����
					    	if(row == 19){
					    		//���[�h�ҏW
								if(DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
										JwsConstManager.JWS_COMPONENT_0089, DataCtrl.getInstance().getParamData().getStrMode())){
									String insert = getValueAt( row, column ).toString();
						    		DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuSakuseiMemo(
						    				intSeq,
						    				DataCtrl.getInstance().getTrialTblData().checkNullString(insert),
						    				DataCtrl.getInstance().getUserMstData().getDciUserid()
						    			);
								}
					    	}
					    	//�]��
					    	if(row == 20){
					    		//���[�h�ҏW
								if(DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
										JwsConstManager.JWS_COMPONENT_0090, DataCtrl.getInstance().getParamData().getStrMode())){
									String insert = getValueAt( row, column ).toString();
						    		DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuHyouka(
						    				intSeq,
						    				DataCtrl.getInstance().getTrialTblData().checkNullString(insert),
						    				DataCtrl.getInstance().getUserMstData().getDciUserid()
						    			);
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
			table.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
			table.setAutoResizeMode(table.AUTO_RESIZE_OFF);
			table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			//table.setCellSelectionEnabled(true);

			//�e�[�u���T�C�Y�ݒ�
			table.setRowHeight(17);
			table.setRowHeight(19, 90);
			table.setRowHeight(20, 90);

			//�X�N���[���p�l���ݒ�
			scroll = new JScrollPane( table ) {
				private static final long serialVersionUID = 1L;
				//�w�b�_�[������
				public void setColumnHeaderView(Component view) {}
			};
			scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
			scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
			scroll.setBackground(Color.WHITE);
			scroll.setBorder(new LineBorder(Color.BLACK, 1));
			scroll.setBounds(194, 25, 800, 523 + 1);
			scroll.setBackground(Color.WHITE);
			//2011/04/21 QP@10181_No.41 TT T.Satoh Add Start -------------------------
			scroll.getHorizontalScrollBar().setMaximum(200*intColumnCount);
			scroll.getVerticalScrollBar().setMaximum(1000);
			//2011/04/21 QP@10181_No.41 TT T.Satoh Add End ---------------------------
			this.add(scroll,BorderLayout.CENTER);

// ADD start 20120927 QP@20505 No.24 �H���p�^�[���ʕ\���`��
			//�H���p�^�[���擾
			String ptKotei = DataCtrl.getInstance().getTrialTblData().SearchShisakuhinData().getStrPt_kotei();
			String ptValue = "";
			if(ptKotei == null || ptKotei.length() == 0){
			}else{
				//�H���p�^�[�����󔒂ł͂Ȃ��ꍇ�AValue1�擾
				ptValue = DataCtrl.getInstance().getLiteralDataKoteiPtn().selectLiteralVal1(ptKotei);
			}
// ADD end 20120927 QP@20505 No.24 �H���p�^�[���ʕ\���`��

			//------------------------ �e�[�u���f�[�^&�Z���G�f�B�^�E�����_���}��  ------------------------
			//�e�[�u���J�������f���擾
			DefaultTableColumnModel columnModel = (DefaultTableColumnModel)table.getColumnModel();
			TableColumn column = null;
			//�񐔕����[�v
			for(int j = 0;j < columnModel.getColumnCount();j++){
				//��̕��ݒ�
				column = columnModel.getColumn(j);
	            column.setPreferredWidth(150);

				//�ǉ��p���ԃG�f�B�^�������_������
				MiddleCellEditor MiddleCellEditor = new MiddleCellEditor(table);
				MiddleCellRenderer MiddleCellRenderer = new MiddleCellRenderer();

				//�����f�[�^�擾�i�\�����j
				int no=0;
				for(int i=0; i<columnModel.getColumnCount(); i++){
					TrialData chkHyji = (TrialData)aryTrialData[i];
					if((chkHyji.getIntHyojiNo()-1) == j){
						no=i;
					}
				}
				trialData = (TrialData)aryTrialData[no];

				//------------------------------- �w�b�_����  -----------------------------------
				//�R���|�[�l���g����
				TextboxBase headerTB = new TextboxBase();
				headerTB.setEditable(false);
				headerTB.setBackground(JwsConstManager.SHISAKU_ITEM_COLOR);
				headerTB.setPk1(Integer.toString(trialData.getIntShisakuSeq()));
				//�Z���G�f�B�^&�����_������
				TextFieldCellEditor headerTCE = new TextFieldCellEditor(headerTB);
				TextFieldCellRenderer headerTCR = new TextFieldCellRenderer(headerTB);
				//���ԃG�f�B�^&�����_���֓o�^
				MiddleCellEditor.addEditorAt(0, headerTCE);
				MiddleCellRenderer.add(0, headerTCR);
				//�f�[�^�ݒ�
				table.setValueAt( trialData.getStrSampleNo(), 0, j);

				//--------------------------------- ���_  ------------------------------------
				//�R���|�[�l���g����
				TextboxBase sosanTB = new TextboxBase();
				//���[�h�ҏW
				if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
						JwsConstManager.JWS_COMPONENT_0072, DataCtrl.getInstance().getParamData().getStrMode())){
					sosanTB.setBackground(Color.lightGray);
					sosanTB.setEditable(false);
				}
				//�Z���G�f�B�^&�����_������
				TextFieldCellEditor sosanTCE = new TextFieldCellEditor(sosanTB);
				TextFieldCellRenderer sosanTCR = new TextFieldCellRenderer(sosanTB);
				//���ԃG�f�B�^&�����_���֓o�^
				MiddleCellEditor.addEditorAt(1, sosanTCE);
				MiddleCellRenderer.add(1, sosanTCR);
				//�f�[�^�ݒ�
				table.setValueAt( trialData.getDciSosan(), 1, j);

				//--------------------------------- �H��  ------------------------------------
				//�R���|�[�l���g����
				TextboxBase shokuenTB = new TextboxBase();
				//���[�h�ҏW
				if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
						JwsConstManager.JWS_COMPONENT_0073, DataCtrl.getInstance().getParamData().getStrMode())){
					shokuenTB.setBackground(Color.lightGray);
					shokuenTB.setEditable(false);
				}
				//�Z���G�f�B�^&�����_������
				TextFieldCellEditor shokuenTCE = new TextFieldCellEditor(shokuenTB);
				TextFieldCellRenderer shokuenTCR = new TextFieldCellRenderer(shokuenTB);
				//���ԃG�f�B�^&�����_���֓o�^
				MiddleCellEditor.addEditorAt(2, shokuenTCE);
				MiddleCellRenderer.add(2, shokuenTCR);
				//�f�[�^�ݒ�
				table.setValueAt( trialData.getDciShokuen(), 2, j);

				//------------------------------- �������_�x   --------------------------------
				//�R���|�[�l���g����
				TextboxBase suisandoTB = new TextboxBase();
				//���[�h�ҏW
				if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
						JwsConstManager.JWS_COMPONENT_0074, DataCtrl.getInstance().getParamData().getStrMode())){
					suisandoTB.setBackground(Color.lightGray);
					suisandoTB.setEditable(false);
				}
				//�Z���G�f�B�^&�����_������
				TextFieldCellEditor suisandoTCE = new TextFieldCellEditor(suisandoTB);
				TextFieldCellRenderer suisandoTCR = new TextFieldCellRenderer(suisandoTB);
				//���ԃG�f�B�^&�����_���֓o�^
				MiddleCellEditor.addEditorAt(3, suisandoTCE);
				MiddleCellRenderer.add(3, suisandoTCR);
				//�f�[�^�ݒ�
				table.setValueAt( trialData.getDciSuiSando(), 3, j);

				//------------------------------- �������H��   --------------------------------
				//�R���|�[�l���g����
				TextboxBase suishokuenTB = new TextboxBase();
				//���[�h�ҏW
				if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
						JwsConstManager.JWS_COMPONENT_0075, DataCtrl.getInstance().getParamData().getStrMode())){
					suishokuenTB.setBackground(Color.lightGray);
					suishokuenTB.setEditable(false);
				}
				//�Z���G�f�B�^&�����_������
				TextFieldCellEditor suishokuenTCE = new TextFieldCellEditor(suishokuenTB);
				TextFieldCellRenderer suishokuenTCR = new TextFieldCellRenderer(suishokuenTB);
				//���ԃG�f�B�^&�����_���֓o�^
				MiddleCellEditor.addEditorAt(4, suishokuenTCE);
				MiddleCellRenderer.add(4, suishokuenTCR);
				//�f�[�^�ݒ�
				table.setValueAt( trialData.getDciSuiShokuen(), 4, j);

				//------------------------------- �������|�_   --------------------------------
				//�R���|�[�l���g����
				TextboxBase suisakusanTB = new TextboxBase();
				//���[�h�ҏW
				if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
						JwsConstManager.JWS_COMPONENT_0076, DataCtrl.getInstance().getParamData().getStrMode())){
					suisakusanTB.setBackground(Color.lightGray);
					suisakusanTB.setEditable(false);
				}
				//�Z���G�f�B�^&�����_������
				TextFieldCellEditor suisakusanTCE = new TextFieldCellEditor(suisakusanTB);
				TextFieldCellRenderer suisakusanTCR = new TextFieldCellRenderer(suisakusanTB);
				//���ԃG�f�B�^&�����_���֓o�^
				MiddleCellEditor.addEditorAt(5, suisakusanTCE);
				MiddleCellRenderer.add(5, suisakusanTCR);
				//�f�[�^�ݒ�
				table.setValueAt( trialData.getDciSuiSakusan(), 5, j);

// ADD start 20120927 QP@20505 No.24
				if(ptValue.equals("") || ptValue.equals(JwsConstManager.JWS_KOTEITYPE_3)){
					// �H���p�^�[���u���̑��E���H�v�܂��́A���I���̏ꍇ
//ADD start 20120927 QP@20505 No.24
					//---------------------------------- ���x  -----------------------------------
					//�R���|�[�l���g����
					TextboxBase todoTB = new TextboxBase();
					todoTB.setBackground(Color.WHITE);
					//���[�h�ҏW
					if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
							JwsConstManager.JWS_COMPONENT_0077, DataCtrl.getInstance().getParamData().getStrMode())){
						todoTB.setEditable(false);
					}
					//�Z���G�f�B�^&�����_������
					TextFieldCellEditor todoTCE = new TextFieldCellEditor(todoTB);
					TextFieldCellRenderer todoTCR = new TextFieldCellRenderer(todoTB);
					//���ԃG�f�B�^&�����_���֓o�^
					MiddleCellEditor.addEditorAt(6, todoTCE);
					MiddleCellRenderer.add(6, todoTCR);
					//�f�[�^�ݒ�
					table.setValueAt( trialData.getStrToudo(), 6, j);

					//---------------------------------- �S�x  -----------------------------------
					//�R���|�[�l���g����
					TextboxBase nendoTB = new TextboxBase();
					nendoTB.setBackground(Color.WHITE);
					//���[�h�ҏW
					if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
							JwsConstManager.JWS_COMPONENT_0078, DataCtrl.getInstance().getParamData().getStrMode())){
						nendoTB.setEditable(false);
					}
					//�Z���G�f�B�^&�����_������
					TextFieldCellEditor nendoTCE = new TextFieldCellEditor(nendoTB);
					TextFieldCellRenderer nendoTCR = new TextFieldCellRenderer(nendoTB);
					//���ԃG�f�B�^&�����_���֓o�^
					MiddleCellEditor.addEditorAt(7, nendoTCE);
					MiddleCellRenderer.add(7, nendoTCR);
					//�f�[�^�ݒ�
					table.setValueAt( trialData.getStrNendo(), 7, j);

					//---------------------------------- ���x  -----------------------------------
					//�R���|�[�l���g����
					TextboxBase ondoTB = new TextboxBase();
					ondoTB.setBackground(Color.WHITE);
					//���[�h�ҏW
					if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
							JwsConstManager.JWS_COMPONENT_0079, DataCtrl.getInstance().getParamData().getStrMode())){
						ondoTB.setEditable(false);
					}
					//�Z���G�f�B�^&�����_������
					TextFieldCellEditor ondoTCE = new TextFieldCellEditor(ondoTB);
					TextFieldCellRenderer ondoTCR = new TextFieldCellRenderer(ondoTB);
					//���ԃG�f�B�^&�����_���֓o�^
					MiddleCellEditor.addEditorAt(8, ondoTCE);
					MiddleCellRenderer.add(8, ondoTCR);
					//�f�[�^�ݒ�
					table.setValueAt( trialData.getStrOndo(), 8, j);

					//----------------------------------- PH ------------------------------------
					//�R���|�[�l���g����
					TextboxBase phTB = new TextboxBase();
					phTB.setBackground(Color.WHITE);
					//���[�h�ҏW
					if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
							JwsConstManager.JWS_COMPONENT_0080, DataCtrl.getInstance().getParamData().getStrMode())){
						phTB.setEditable(false);
					}
					//�Z���G�f�B�^&�����_������
					TextFieldCellEditor phTCE = new TextFieldCellEditor(phTB);
					TextFieldCellRenderer phTCR = new TextFieldCellRenderer(phTB);
					//���ԃG�f�B�^&�����_���֓o�^
					MiddleCellEditor.addEditorAt(9, phTCE);
					MiddleCellRenderer.add(9, phTCR);
					//�f�[�^�ݒ�
					table.setValueAt( trialData.getStrPh(), 9, j);

					//--------------------------------- ���_����   --------------------------------
					//�R���|�[�l���g����
					TextboxBase bunsosanTB = new TextboxBase();
					bunsosanTB.setBackground(Color.WHITE);
					//���[�h�ҏW
					if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
							JwsConstManager.JWS_COMPONENT_0081, DataCtrl.getInstance().getParamData().getStrMode())){
						bunsosanTB.setEditable(false);
					}
					//�Z���G�f�B�^&�����_������
					TextFieldCellEditor bunsosanTCE = new TextFieldCellEditor(bunsosanTB);
					TextFieldCellRenderer bunsosanTCR = new TextFieldCellRenderer(bunsosanTB);
					//���ԃG�f�B�^&�����_���֓o�^
					MiddleCellEditor.addEditorAt(10, bunsosanTCE);
					MiddleCellRenderer.add(10, bunsosanTCR);
					//�f�[�^�ݒ�
					table.setValueAt( trialData.getStrSosanBunseki(), 10, j);

					//--------------------------------- �H������   --------------------------------
					//�R���|�[�l���g����
					TextboxBase bunshokuenTB = new TextboxBase();
					bunshokuenTB.setBackground(Color.WHITE);
					//���[�h�ҏW
					if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
							JwsConstManager.JWS_COMPONENT_0082, DataCtrl.getInstance().getParamData().getStrMode())){
						bunshokuenTB.setEditable(false);
					}
					//�Z���G�f�B�^&�����_������
					TextFieldCellEditor bunshokuenTCE = new TextFieldCellEditor(bunshokuenTB);
					TextFieldCellRenderer bunshokuenTCR = new TextFieldCellRenderer(bunshokuenTB);
					//���ԃG�f�B�^&�����_���֓o�^
					MiddleCellEditor.addEditorAt(11, bunshokuenTCE);
					MiddleCellRenderer.add(11, bunshokuenTCR);
					//�f�[�^�ݒ�
					table.setValueAt( trialData.getStrShokuenBunseki(), 11, j);

					//----------------------------------- ��d   ----------------------------------
					//�R���|�[�l���g����
					TextboxBase hijuTB = new TextboxBase();
					hijuTB.setBackground(Color.WHITE);
					//���[�h�ҏW
					if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
							JwsConstManager.JWS_COMPONENT_0083, DataCtrl.getInstance().getParamData().getStrMode())){
						hijuTB.setEditable(false);
					}


	//2011/05/30�@�yQP@10181_No.42_49_72�z�H���p�^�[������@TT.NISHIGAWA�@start
					//----------------------------------- ������d   ----------------------------------
					//�R���|�[�l���g����
					TextboxBase hijuTB_sui = new TextboxBase();
					hijuTB_sui.setBackground(Color.WHITE);
					//���[�h�ҏW
					if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
							JwsConstManager.JWS_COMPONENT_0154, DataCtrl.getInstance().getParamData().getStrMode())){
						hijuTB_sui.setEditable(false);
					}

					//----------------------------------- ���i��d�E������d�ݒ�   ----------------------------------
					//�Q�ƃ��[�h�łȂ��ꍇ
					if(DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
							JwsConstManager.JWS_COMPONENT_0154, DataCtrl.getInstance().getParamData().getStrMode())){

						//�e�ʒP�ʎ擾
						String yoryoTani = DataCtrl.getInstance().getTrialTblData().SearchShisakuhinData().getStrTani();

						//�e�ʒP�ʂ���Value1�擾
						String taniValue1 = "";
						if(yoryoTani == null || yoryoTani.length() == 0){

						}
						else{
							taniValue1 =  DataCtrl.getInstance().getLiteralDataTani().selectLiteralVal1(yoryoTani);
						}
	// DEL start 20120928 QP@20505 No.24
	//					//�H���p�^�[���擾
	//					String ptKotei = DataCtrl.getInstance().getTrialTblData().SearchShisakuhinData().getStrPt_kotei();
	// DEL end 20120928 QP@20505 No.24
						//�H���p�^�[�����u�󔒁v�̏ꍇ-------------------------------------------------------------------------
	// MOD start 20120928 QP@20505 No.24
	//					if(ptKotei == null || ptKotei.length() == 0){
						if(ptValue.equals("")){
	// MOD end 20120928 QP@20505 No.24
							//���i��d�@�ҏW�s��
							hijuTB.setEditable(false);
							//������d�@�ҏW�s��
							hijuTB_sui.setEditable(false);

							hijuTB.setBackground(JwsConstManager.JWS_DISABLE_COLOR);
							hijuTB_sui.setBackground(JwsConstManager.JWS_DISABLE_COLOR);
						}
						//�H���p�^�[�����u�󔒁v�łȂ��ꍇ
						else{
// DEL start 20120928 QP@20505 No.24
	//						//�H���p�^�[����Value1�擾
	//						String ptValue = DataCtrl.getInstance().getLiteralDataKoteiPtn().selectLiteralVal1(ptKotei);
//							//�H���p�^�[�����u�������P�t�^�C�v�v�̏ꍇ-------------------------------------------------------------
//							if(ptValue.equals(JwsConstManager.JWS_KOTEITYPE_1)){
//
//								//�e�ʂ��uml�v�̏ꍇ
//								if(taniValue1.equals("1")){
//									//���i��d�@�ҏW��
//									hijuTB.setEditable(true);
//									//������d�@�ҏW�s��
//									hijuTB_sui.setEditable(false);
//
//									hijuTB.setBackground(Color.white);
//									hijuTB_sui.setBackground(JwsConstManager.JWS_DISABLE_COLOR);
//								}
//								//�e�ʂ��ug�v�̏ꍇ
//								else if(taniValue1.equals("2")){
//									//���i��d�@�ҏW�s��
//									hijuTB.setEditable(false);
//									//������d�@�ҏW�s��
//									hijuTB_sui.setEditable(false);
//
//									hijuTB.setBackground(JwsConstManager.JWS_DISABLE_COLOR);
//									hijuTB_sui.setBackground(JwsConstManager.JWS_DISABLE_COLOR);
//								}
//								//�e�ʂ��u�󔒁v�̏ꍇ
//								else{
//									//���i��d�@�ҏW�s��
//									hijuTB.setEditable(false);
//									//������d�@�ҏW�s��
//									hijuTB_sui.setEditable(false);
//
//									hijuTB.setBackground(JwsConstManager.JWS_DISABLE_COLOR);
//									hijuTB_sui.setBackground(JwsConstManager.JWS_DISABLE_COLOR);
//								}
//							}
//							//�H���p�^�[�����u�������Q�t�^�C�v�v�̏ꍇ-------------------------------------------------------------
//							else if(ptValue.equals(JwsConstManager.JWS_KOTEITYPE_2)){
//
//								//�e�ʂ��uml�v�̏ꍇ
//								if(taniValue1.equals("1")){
//									//���i��d�@�ҏW�s��
//									hijuTB.setEditable(false);
//									//������d�@�ҏW��
//									hijuTB_sui.setEditable(true);
//
//									hijuTB.setBackground(JwsConstManager.JWS_DISABLE_COLOR);
//									hijuTB_sui.setBackground(Color.white);
//								}
//								//QP@20505 No.2 2012/09/05 TT H.SHIMA ADD -Start
//								//�e�ʂ��ug�v�̏ꍇ
//								else if(taniValue1.equals("2")){
//									//���i��d�@�ҏW�s��
//									hijuTB.setEditable(false);
//									//������d�@�ҏW�s��
//									hijuTB_sui.setEditable(false);
//
//									hijuTB.setBackground(JwsConstManager.JWS_DISABLE_COLOR);
//									hijuTB_sui.setBackground(JwsConstManager.JWS_DISABLE_COLOR);
//								}
//								//QP@20505 No.2 2012/09/05 TT H.SHIMA ADD -End
//								//�e�ʂ��u�󔒁v�̏ꍇ�iml�ȊO�̏ꍇ�j
//								else{
//									//���i��d�@�ҏW�s��
//									hijuTB.setEditable(false);
//									//������d�@�ҏW�s��
//									hijuTB_sui.setEditable(false);
//
//									hijuTB.setBackground(JwsConstManager.JWS_DISABLE_COLOR);
//									hijuTB_sui.setBackground(JwsConstManager.JWS_DISABLE_COLOR);
//								}
//
//							}
// DEL end 20120928 QP@20505 No.24
							//�H���p�^�[�����u���̑��E���H�^�C�v�v�̏ꍇ-------------------------------------------------------------
// MOD start 20120928 QP@20505 No.24
//							else if(ptValue.equals(JwsConstManager.JWS_KOTEITYPE_3)){
							if(ptValue.equals(JwsConstManager.JWS_KOTEITYPE_3)){
// MOD end 20120928 QP@20505 No.24
								//�e�ʂ��ug�v�̏ꍇ
								if(taniValue1.equals("2")){
									//���i��d�@�ҏW�s��
									hijuTB.setEditable(false);
									//������d�@�ҏW�s��
									hijuTB_sui.setEditable(false);

									hijuTB.setBackground(JwsConstManager.JWS_DISABLE_COLOR);
									hijuTB_sui.setBackground(JwsConstManager.JWS_DISABLE_COLOR);
								}
								//�e�ʂ��u�󔒁v�̏ꍇ�ig�ȊO�̏ꍇ�j
								else{
									//���i��d�@�ҏW�s��
									hijuTB.setEditable(false);
									//������d�@�ҏW�s��
									hijuTB_sui.setEditable(false);

									hijuTB.setBackground(JwsConstManager.JWS_DISABLE_COLOR);
									hijuTB_sui.setBackground(JwsConstManager.JWS_DISABLE_COLOR);
								}
							}
						}
					}
	//2011/05/30�@�yQP@10181_No.42_49_72�z�H���p�^�[������@TT.NISHIGAWA�@end

					//�Z���G�f�B�^&�����_������
					TextFieldCellEditor hijuTCE = new TextFieldCellEditor(hijuTB);
					TextFieldCellRenderer hijuTCR = new TextFieldCellRenderer(hijuTB);
					//���ԃG�f�B�^&�����_���֓o�^
					MiddleCellEditor.addEditorAt(12, hijuTCE);
					MiddleCellRenderer.add(12, hijuTCR);
					//�f�[�^�ݒ�
					table.setValueAt( trialData.getStrHizyu(), 12, j);

					//�Z���G�f�B�^&�����_������
					TextFieldCellEditor hijuTCE_sui = new TextFieldCellEditor(hijuTB_sui);
					TextFieldCellRenderer hijuTCR_sui = new TextFieldCellRenderer(hijuTB_sui);
					//���ԃG�f�B�^&�����_���֓o�^
					MiddleCellEditor.addEditorAt(13, hijuTCE_sui);
					MiddleCellRenderer.add(13, hijuTCR_sui);

					//�f�[�^�ݒ�
					table.setValueAt( trialData.getStrHiju_sui(), 13, j);

					//--------------------------------- ��������   --------------------------------
					//�R���|�[�l���g����
					TextboxBase suikaseiTB = new TextboxBase();
					suikaseiTB.setBackground(Color.WHITE);
					//���[�h�ҏW
					if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
							JwsConstManager.JWS_COMPONENT_0084, DataCtrl.getInstance().getParamData().getStrMode())){
						suikaseiTB.setEditable(false);
					}
					//�Z���G�f�B�^&�����_������
					TextFieldCellEditor suikaseiTCE = new TextFieldCellEditor(suikaseiTB);
					TextFieldCellRenderer suikaseiTCR = new TextFieldCellRenderer(suikaseiTB);
					//���ԃG�f�B�^&�����_���֓o�^
					MiddleCellEditor.addEditorAt(14, suikaseiTCE);
					MiddleCellRenderer.add(14, suikaseiTCR);
					//�f�[�^�ݒ�
	// MOD start 20120928 QP@20505 No.24
	//				table.setValueAt( trialData.getStrSuibun(), 14, j);
					table.setValueAt( trialData.getStrFreeSuibunKasei(), 14, j);
	// MOD end 20120928 QP@20505 No.24

					//--------------------------------- �A���R�[��  ---------------------------------
					//�R���|�[�l���g����
					TextboxBase arukoTB = new TextboxBase();
					arukoTB.setBackground(Color.WHITE);
					//���[�h�ҏW
					if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
							JwsConstManager.JWS_COMPONENT_0085, DataCtrl.getInstance().getParamData().getStrMode())){
						arukoTB.setEditable(false);
					}
					//�Z���G�f�B�^&�����_������
					TextFieldCellEditor arukoTCE = new TextFieldCellEditor(arukoTB);
					TextFieldCellRenderer arukoTCR = new TextFieldCellRenderer(arukoTB);
					//���ԃG�f�B�^&�����_���֓o�^
					MiddleCellEditor.addEditorAt(15, arukoTCE);
					MiddleCellRenderer.add(15, arukoTCR);
					//�f�[�^�ݒ�
	// MOD start 20120928 QP@20505 No.24
	//				table.setValueAt( trialData.getStrArukoru(), 15, j);
					table.setValueAt( trialData.getStrFreeAlchol(), 15, j);
	// MOD end 20120928 QP@20505 No.24

					//--------------------------------- �t���[���e�@ ------------------------------
					//�R���|�[�l���g����
					TextboxBase free1TB = new TextboxBase();
					free1TB.setBackground(Color.WHITE);
					//���[�h�ҏW
					if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
							JwsConstManager.JWS_COMPONENT_0086, DataCtrl.getInstance().getParamData().getStrMode())){
						free1TB.setEditable(false);
					}
					//�Z���G�f�B�^&�����_������
					TextFieldCellEditor free1TCE = new TextFieldCellEditor(free1TB);
					TextFieldCellRenderer free1TCR = new TextFieldCellRenderer(free1TB);
					//���ԃG�f�B�^&�����_���֓o�^
					MiddleCellEditor.addEditorAt(16, free1TCE);
					MiddleCellRenderer.add(16, free1TCR);
					//�f�[�^�ݒ�
					table.setValueAt( trialData.getStrFreeNaiyo1(), 16, j);

					//--------------------------------- �t���[���e�A ------------------------------
					//�R���|�[�l���g����
					TextboxBase free2TB = new TextboxBase();
					free2TB.setBackground(Color.WHITE);
					//���[�h�ҏW
					if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
							JwsConstManager.JWS_COMPONENT_0087, DataCtrl.getInstance().getParamData().getStrMode())){
						free2TB.setEditable(false);
					}
					//�Z���G�f�B�^&�����_������
					TextFieldCellEditor free2TCE = new TextFieldCellEditor(free2TB);
					TextFieldCellRenderer free2TCR = new TextFieldCellRenderer(free2TB);
					//���ԃG�f�B�^&�����_���֓o�^
					MiddleCellEditor.addEditorAt(17, free2TCE);
					MiddleCellRenderer.add(17, free2TCR);
					//�f�[�^�ݒ�
					table.setValueAt( trialData.getStrFreeNaiyo2(), 17, j);

					//--------------------------------- �t���[���e�B ------------------------------
					//�R���|�[�l���g����
					TextboxBase free3TB = new TextboxBase();
					free3TB.setBackground(Color.WHITE);
					//���[�h�ҏW
					if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
							JwsConstManager.JWS_COMPONENT_0088, DataCtrl.getInstance().getParamData().getStrMode())){
						free3TB.setEditable(false);
					}
					//�Z���G�f�B�^&�����_������
					TextFieldCellEditor free3TCE = new TextFieldCellEditor(free3TB);
					TextFieldCellRenderer free3TCR = new TextFieldCellRenderer(free3TB);
					//���ԃG�f�B�^&�����_���֓o�^
					MiddleCellEditor.addEditorAt(18, free3TCE);
					MiddleCellRenderer.add(18, free3TCR);
					//�f�[�^�ݒ�
					table.setValueAt( trialData.getStrFreeNaiyo3(), 18, j);
// ADD start 20120928 QP@20505 No.24
				}else{
					// ----------  �H���p�^�[�� �P�t�E�Q�t �̏ꍇ  ----------------------------------------------------------------------
					//---------------------------------- �����|�_�Z�x  -----------------------------------
					//�R���|�[�l���g����
					TextboxBase JsnTB = new TextboxBase();
					JsnTB.setBackground(Color.WHITE);
//					//���[�h�ҏW
//					if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
//							JwsConstManager.JWS_COMPONENT_0166, DataCtrl.getInstance().getParamData().getStrMode())){
//						JsnTB.setEditable(false);
//					}
					JsnTB.setBackground(JwsConstManager.JWS_DISABLE_COLOR);
					JsnTB.setEditable(false);

					//�Z���G�f�B�^&�����_������
					TextFieldCellEditor JsnTCE = new TextFieldCellEditor(JsnTB);
					TextFieldCellRenderer JsnTCR = new TextFieldCellRenderer(JsnTB);
					//���ԃG�f�B�^&�����_���֓o�^
					MiddleCellEditor.addEditorAt(6, JsnTCE);
					MiddleCellRenderer.add(6, JsnTCR);
					//�f�[�^�ݒ�
					table.setValueAt( trialData.getDciJikkoSakusanNodo(), 6, j);
					//---------------------------------- �������l�r�f  -----------------------------------
					//�R���|�[�l���g����
					TextboxBase suiMsgTB = new TextboxBase();
					suiMsgTB.setBackground(Color.WHITE);
//					//���[�h�ҏW
//					if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
//							JwsConstManager.JWS_COMPONENT_0168, DataCtrl.getInstance().getParamData().getStrMode())){
//						suiMsgTB.setEditable(false);
//					}
					suiMsgTB.setBackground(JwsConstManager.JWS_DISABLE_COLOR);
					suiMsgTB.setEditable(false);

					//�Z���G�f�B�^&�����_������
					TextFieldCellEditor suiMsgTCE = new TextFieldCellEditor(suiMsgTB);
					TextFieldCellRenderer suiMsgTCR = new TextFieldCellRenderer(suiMsgTB);
					//���ԃG�f�B�^&�����_���֓o�^
					MiddleCellEditor.addEditorAt(7, suiMsgTCE);
					MiddleCellRenderer.add(7, suiMsgTCR);
					//�f�[�^�ݒ�
					table.setValueAt( trialData.getDciSuisoMSG(), 7, j);
					//----------------------------------- PH ------------------------------------
					//�R���|�[�l���g����
					TextboxBase phTB = new TextboxBase();
					phTB.setBackground(Color.WHITE);
					//���[�h�ҏW
					if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
							JwsConstManager.JWS_COMPONENT_0080, DataCtrl.getInstance().getParamData().getStrMode())){
						phTB.setEditable(false);
					}
					//�Z���G�f�B�^&�����_������
					TextFieldCellEditor phTCE = new TextFieldCellEditor(phTB);
					TextFieldCellRenderer phTCR = new TextFieldCellRenderer(phTB);
					//���ԃG�f�B�^&�����_���֓o�^
					MiddleCellEditor.addEditorAt(8, phTCE);
					MiddleCellRenderer.add(8, phTCR);
					//�f�[�^�ݒ�
					table.setValueAt( trialData.getStrPh(), 8, j);
					//----------------------------------- ��d   ----------------------------------
					//�R���|�[�l���g����
					TextboxBase hijuTB = new TextboxBase();
					hijuTB.setBackground(Color.WHITE);
					//���[�h�ҏW
					if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
							JwsConstManager.JWS_COMPONENT_0083, DataCtrl.getInstance().getParamData().getStrMode())){
						hijuTB.setEditable(false);
					}
					//----------------------------------- ������d   ----------------------------------
					//�R���|�[�l���g����
					TextboxBase hijuTB_sui = new TextboxBase();
					hijuTB_sui.setBackground(Color.WHITE);
					//���[�h�ҏW
					if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
							JwsConstManager.JWS_COMPONENT_0154, DataCtrl.getInstance().getParamData().getStrMode())){
						hijuTB_sui.setEditable(false);
					}
					//----------------------------------- ���i��d�E������d�ݒ�   ----------------------------------
					//�Q�ƃ��[�h�łȂ��ꍇ
					if(DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
							JwsConstManager.JWS_COMPONENT_0154, DataCtrl.getInstance().getParamData().getStrMode())){

						//�e�ʒP�ʎ擾
						String yoryoTani = DataCtrl.getInstance().getTrialTblData().SearchShisakuhinData().getStrTani();
						//�e�ʒP�ʂ���Value1�擾
						String taniValue1 = "";
						if(yoryoTani == null || yoryoTani.length() == 0){
						}
						else{
							taniValue1 =  DataCtrl.getInstance().getLiteralDataTani().selectLiteralVal1(yoryoTani);
						}

						//�H���p�^�[�����u�������P�t�^�C�v�v�̏ꍇ-------------------------------------------------------------
						if(ptValue.equals(JwsConstManager.JWS_KOTEITYPE_1)){
							//�e�ʂ��uml�v�̏ꍇ
							if(taniValue1.equals("1")){
								//���i��d�@�ҏW��
								hijuTB.setEditable(true);
								//������d�@�ҏW�s��
								hijuTB_sui.setEditable(false);

								hijuTB.setBackground(Color.white);
								hijuTB_sui.setBackground(JwsConstManager.JWS_DISABLE_COLOR);
							}
							//�e�ʂ��ug�v�̏ꍇ
							else if(taniValue1.equals("2")){
								//���i��d�@�ҏW�s��
								hijuTB.setEditable(false);
								//������d�@�ҏW�s��
								hijuTB_sui.setEditable(false);

								hijuTB.setBackground(JwsConstManager.JWS_DISABLE_COLOR);
								hijuTB_sui.setBackground(JwsConstManager.JWS_DISABLE_COLOR);
							}
							//�e�ʂ��u�󔒁v�̏ꍇ
							else{
								//���i��d�@�ҏW�s��
								hijuTB.setEditable(false);
								//������d�@�ҏW�s��
								hijuTB_sui.setEditable(false);

								hijuTB.setBackground(JwsConstManager.JWS_DISABLE_COLOR);
								hijuTB_sui.setBackground(JwsConstManager.JWS_DISABLE_COLOR);
							}
						}
						//�H���p�^�[�����u�������Q�t�^�C�v�v�̏ꍇ-------------------------------------------------------------
						else if(ptValue.equals(JwsConstManager.JWS_KOTEITYPE_2)){
							//�e�ʂ��uml�v�̏ꍇ
							if(taniValue1.equals("1")){
								//���i��d�@�ҏW�s��
								hijuTB.setEditable(false);
								//������d�@�ҏW��
								hijuTB_sui.setEditable(true);

								hijuTB.setBackground(JwsConstManager.JWS_DISABLE_COLOR);
								hijuTB_sui.setBackground(Color.white);
							}
							//QP@20505 No.2 2012/09/05 TT H.SHIMA ADD -Start
							//�e�ʂ��ug�v�̏ꍇ
							else if(taniValue1.equals("2")){
								//���i��d�@�ҏW�s��
								hijuTB.setEditable(false);
								//������d�@�ҏW�s��
								hijuTB_sui.setEditable(false);

								hijuTB.setBackground(JwsConstManager.JWS_DISABLE_COLOR);
								hijuTB_sui.setBackground(JwsConstManager.JWS_DISABLE_COLOR);
							}
							//QP@20505 No.2 2012/09/05 TT H.SHIMA ADD -End
							//�e�ʂ��u�󔒁v�̏ꍇ�iml�ȊO�̏ꍇ�j
							else{
								//���i��d�@�ҏW�s��
								hijuTB.setEditable(false);
								//������d�@�ҏW�s��
								hijuTB_sui.setEditable(false);

								hijuTB.setBackground(JwsConstManager.JWS_DISABLE_COLOR);
								hijuTB_sui.setBackground(JwsConstManager.JWS_DISABLE_COLOR);
							}
						}
					}
					//�Z���G�f�B�^&�����_������
					TextFieldCellEditor hijuTCE = new TextFieldCellEditor(hijuTB);
					TextFieldCellRenderer hijuTCR = new TextFieldCellRenderer(hijuTB);
					//���ԃG�f�B�^&�����_���֓o�^
					MiddleCellEditor.addEditorAt(9, hijuTCE);
					MiddleCellRenderer.add(9, hijuTCR);
					//�f�[�^�ݒ�
					table.setValueAt( trialData.getStrHizyu(), 9, j);

					//�Z���G�f�B�^&�����_������
					TextFieldCellEditor hijuTCE_sui = new TextFieldCellEditor(hijuTB_sui);
					TextFieldCellRenderer hijuTCR_sui = new TextFieldCellRenderer(hijuTB_sui);
					//���ԃG�f�B�^&�����_���֓o�^
					MiddleCellEditor.addEditorAt(10, hijuTCE_sui);
					MiddleCellRenderer.add(10, hijuTCR_sui);

					//�f�[�^�ݒ�
					table.setValueAt( trialData.getStrHiju_sui(), 10, j);
					//---------------------------------- �S�x �t���[  -----------------------------------
					//�R���|�[�l���g����
					TextboxBase nendoTB = new TextboxBase();
					nendoTB.setBackground(Color.WHITE);
					//���[�h�ҏW
					if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
							JwsConstManager.JWS_COMPONENT_0171, DataCtrl.getInstance().getParamData().getStrMode())){
						nendoTB.setEditable(false);
					}
					//�Z���G�f�B�^&�����_������
					TextFieldCellEditor nendoTCE = new TextFieldCellEditor(nendoTB);
					TextFieldCellRenderer nendoTCR = new TextFieldCellRenderer(nendoTB);
					//���ԃG�f�B�^&�����_���֓o�^
					MiddleCellEditor.addEditorAt(11, nendoTCE);
					MiddleCellRenderer.add(11, nendoTCR);
					//�f�[�^�ݒ�
					table.setValueAt( trialData.getStrFreeNendo(), 11, j);
					//---------------------------------- ���x �t���[  -----------------------------------
					//�R���|�[�l���g����
					TextboxBase ondoTB = new TextboxBase();
					ondoTB.setBackground(Color.WHITE);
					//���[�h�ҏW
					if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
							JwsConstManager.JWS_COMPONENT_0174, DataCtrl.getInstance().getParamData().getStrMode())){
						ondoTB.setEditable(false);
					}
					//�Z���G�f�B�^&�����_������
					TextFieldCellEditor ondoTCE = new TextFieldCellEditor(ondoTB);
					TextFieldCellRenderer ondoTCR = new TextFieldCellRenderer(ondoTB);
					//���ԃG�f�B�^&�����_���֓o�^
					MiddleCellEditor.addEditorAt(12, ondoTCE);
					MiddleCellRenderer.add(12, ondoTCR);
					//�f�[�^�ݒ�
					table.setValueAt( trialData.getStrFreeOndo(), 12, j);
					//--------------------------------- �t���[���e�@ ------------------------------
					//�R���|�[�l���g����
					TextboxBase free1TB = new TextboxBase();
					free1TB.setBackground(Color.WHITE);
					//���[�h�ҏW
					if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
							JwsConstManager.JWS_COMPONENT_0086, DataCtrl.getInstance().getParamData().getStrMode())){
						free1TB.setEditable(false);
					}
					//�Z���G�f�B�^&�����_������
					TextFieldCellEditor free1TCE = new TextFieldCellEditor(free1TB);
					TextFieldCellRenderer free1TCR = new TextFieldCellRenderer(free1TB);
					//���ԃG�f�B�^&�����_���֓o�^
					MiddleCellEditor.addEditorAt(13, free1TCE);
					MiddleCellRenderer.add(13, free1TCR);
					//�f�[�^�ݒ�
					table.setValueAt( trialData.getStrFreeNaiyo1(), 13, j);
					//--------------------------------- �t���[���e�A ------------------------------
					//�R���|�[�l���g����
					TextboxBase free2TB = new TextboxBase();
					free2TB.setBackground(Color.WHITE);
					//���[�h�ҏW
					if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
							JwsConstManager.JWS_COMPONENT_0087, DataCtrl.getInstance().getParamData().getStrMode())){
						free2TB.setEditable(false);
					}
					//�Z���G�f�B�^&�����_������
					TextFieldCellEditor free2TCE = new TextFieldCellEditor(free2TB);
					TextFieldCellRenderer free2TCR = new TextFieldCellRenderer(free2TB);
					//���ԃG�f�B�^&�����_���֓o�^
					MiddleCellEditor.addEditorAt(14, free2TCE);
					MiddleCellRenderer.add(14, free2TCR);
					//�f�[�^�ݒ�
					table.setValueAt( trialData.getStrFreeNaiyo2(), 14, j);
					//--------------------------------- �t���[���e�B ------------------------------
					//�R���|�[�l���g����
					TextboxBase free3TB = new TextboxBase();
					free3TB.setBackground(Color.WHITE);
					//���[�h�ҏW
					if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
							JwsConstManager.JWS_COMPONENT_0088, DataCtrl.getInstance().getParamData().getStrMode())){
						free3TB.setEditable(false);
					}
					//�Z���G�f�B�^&�����_������
					TextFieldCellEditor free3TCE = new TextFieldCellEditor(free3TB);
					TextFieldCellRenderer free3TCR = new TextFieldCellRenderer(free3TB);
					//���ԃG�f�B�^&�����_���֓o�^
					MiddleCellEditor.addEditorAt(15, free3TCE);
					MiddleCellRenderer.add(15, free3TCR);
					//�f�[�^�ݒ�
					table.setValueAt( trialData.getStrFreeNaiyo3(), 15, j);
					//--------------------------------- �t���[���e�C ------------------------------
					//�R���|�[�l���g����
					TextboxBase free4TB = new TextboxBase();
					free4TB.setBackground(Color.WHITE);
					//���[�h�ҏW
					if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
							JwsConstManager.JWS_COMPONENT_0177, DataCtrl.getInstance().getParamData().getStrMode())){
						free4TB.setEditable(false);
					}
					//�Z���G�f�B�^&�����_������
					TextFieldCellEditor free4TCE = new TextFieldCellEditor(free4TB);
					TextFieldCellRenderer free4TCR = new TextFieldCellRenderer(free4TB);
					//���ԃG�f�B�^&�����_���֓o�^
					MiddleCellEditor.addEditorAt(16, free4TCE);
					MiddleCellRenderer.add(16, free4TCR);
					//�f�[�^�ݒ�
					table.setValueAt( trialData.getStrFreeNaiyo4(), 16, j);
					//--------------------------------- �t���[���e�D ------------------------------
					//�R���|�[�l���g����
					TextboxBase free5TB = new TextboxBase();
					free5TB.setBackground(Color.WHITE);
					//���[�h�ҏW
					if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
							JwsConstManager.JWS_COMPONENT_0180, DataCtrl.getInstance().getParamData().getStrMode())){
						free5TB.setEditable(false);
					}
					//�Z���G�f�B�^&�����_������
					TextFieldCellEditor free5TCE = new TextFieldCellEditor(free5TB);
					TextFieldCellRenderer free5TCR = new TextFieldCellRenderer(free5TB);
					//���ԃG�f�B�^&�����_���֓o�^
					MiddleCellEditor.addEditorAt(17, free5TCE);
					MiddleCellRenderer.add(17, free5TCR);
					//�f�[�^�ݒ�
					table.setValueAt( trialData.getStrFreeNaiyo5(), 17, j);
					//--------------------------------- �t���[���e�E ------------------------------
					//�R���|�[�l���g����
					TextboxBase free6TB = new TextboxBase();
					free6TB.setBackground(Color.WHITE);
					//���[�h�ҏW
					if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
							JwsConstManager.JWS_COMPONENT_0183, DataCtrl.getInstance().getParamData().getStrMode())){
						free6TB.setEditable(false);
					}
					//�Z���G�f�B�^&�����_������
					TextFieldCellEditor free6TCE = new TextFieldCellEditor(free6TB);
					TextFieldCellRenderer free6TCR = new TextFieldCellRenderer(free6TB);
					//���ԃG�f�B�^&�����_���֓o�^
					MiddleCellEditor.addEditorAt(18, free6TCE);
					MiddleCellRenderer.add(18, free6TCR);
					//�f�[�^�ݒ�
					table.setValueAt( trialData.getStrFreeNaiyo6(), 18, j);
				}
// ADD end 20120928 QP@20505 No.24

				//----------------------------------- �쐬����  -------------------------------
				//�Z���G�f�B�^&�����_������
				TextAreaCellEditor memoTCE = new TextAreaCellEditor(table);
				memoTCE.getTextArea().setBackground(Color.WHITE);
				//���[�h�ҏW
				if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
						JwsConstManager.JWS_COMPONENT_0089, DataCtrl.getInstance().getParamData().getStrMode())){
					memoTCE.getTextArea().setEditable(false);
				}
				TextAreaCellRenderer memoTCR = new TextAreaCellRenderer();
				memoTCR.setColor(JwsConstManager.SHISAKU_F2_COLOR);
				//DefaultCellRenderer memoTCR = new DefaultCellRenderer(memoTCE.getTextArea());
				//���ԃG�f�B�^&�����_���֓o�^
				MiddleCellEditor.addEditorAt(19, memoTCE);
				MiddleCellRenderer.add(19, memoTCR);
				//�f�[�^�ݒ�
				table.setValueAt( trialData.getStrSakuseiMemo(), 19, j);

				//------------------------------------- �]��  --------------------------------
				//�Z���G�f�B�^&�����_������
				TextAreaCellEditor hyokaTCE = new TextAreaCellEditor(table);
				hyokaTCE.getTextArea().setBackground(Color.WHITE);
				//���[�h�ҏW
				if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
						JwsConstManager.JWS_COMPONENT_0090, DataCtrl.getInstance().getParamData().getStrMode())){
					hyokaTCE.getTextArea().setEditable(false);
				}
				TextAreaCellRenderer hyokaTCR = new TextAreaCellRenderer();
				hyokaTCR.setColor(JwsConstManager.SHISAKU_F2_COLOR);
				//DefaultCellRenderer hyokaTCR = new DefaultCellRenderer(hyokaTCE.getTextArea());
				//���ԃG�f�B�^&�����_���֓o�^
				MiddleCellEditor.addEditorAt(20, hyokaTCE);
				MiddleCellRenderer.add(20, hyokaTCR);
				//�f�[�^�ݒ�
				table.setValueAt( trialData.getStrHyoka(), 20, j);

				//------------------------------- �e�[�u���J�����֐ݒ�  ---------------------------
				column.setCellEditor(MiddleCellEditor);
				column.setCellRenderer(MiddleCellRenderer);
			}
		} catch (Exception e) {
			//�G���[�ݒ�
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			//2011/04/12 QP@10181_No.26 TT T.Satoh Change Start -------------------------
			//ex.setStrErrmsg("����\�A �e�[�u���������������������s���܂���");
			ex.setStrErrmsg("�����l �e�[�u���������������������s���܂���");
			//2011/04/12 QP@10181_No.26 TT T.Satoh Change End ---------------------------
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getMessage());
			throw ex;
		} finally {
		}
	}

	/**
	 * �yJW210�z ���͒l�}�X�^�ύX�m�F ���MXML�f�[�^�쐬
	 */
	private void conJW210() throws ExceptionBase{
		int i;
		try{
			//--------------------------- ���M�p�����[�^�i�[  -----------------------------------
			String strUser = DataCtrl.getInstance().getParamData().getDciUser().toString();
			String strXmlId = "JW210";
			String strAjaxUrl = DataCtrl.getInstance().getJnlpConnect().getStrAddressAjax();

			//--------------------------- ���MXML�f�[�^�쐬  -----------------------------------
			XmlData xmlJW210 = new XmlData();
			ArrayList arySetTag = new ArrayList();

			//------------------------------- Root�ǉ�  --------------------------------------
			xmlJW210.AddXmlTag("",strXmlId);
			arySetTag.clear();

			//-------------------------- �@�\ID�ǉ��iUSERINFO�j  -------------------------------
			xmlJW210.AddXmlTag(strXmlId, "USERINFO");
			//�@�e�[�u���^�O�ǉ�
			xmlJW210.AddXmlTag("USERINFO", "table");
			//�@���R�[�h�ǉ�
			arySetTag.add(new String[]{"kbn_shori", "3"});
			arySetTag.add(new String[]{"id_user",strUser});
			xmlJW210.AddXmlTag("table", "rec", arySetTag);
			arySetTag.clear();

			//------------------------ �@�\ID�ǉ��i���͒l�ύX�m�F�j  ----------------------------
			xmlJW210.AddXmlTag(strXmlId, "SA590");
			//�@�e�[�u���^�O�ǉ�
			xmlJW210.AddXmlTag("SA590", "table");

			//�z���f�[�^
			ArrayList aryHaigoData = DataCtrl.getInstance().getTrialTblData().SearchHaigoData(0);
			for ( i=0; i<aryHaigoData.size(); i++  ) {
				MixedData mixedData = (MixedData)aryHaigoData.get(i);
				//���X�|���X�f�[�^�̐ݒ�
				arySetTag.add(new String[]{"cd_kaisha",checkNull(mixedData.getIntKaishaCd())});		//��ЃR�[�h(����)
				arySetTag.add(new String[]{"cd_busho",checkNull(mixedData.getIntBushoCd())});		//�����R�[�h(����)
				arySetTag.add(new String[]{"cd_genryo",checkNull(mixedData.getStrGenryoCd())});		//�����R�[�h(����)
				arySetTag.add(new String[]{"nm_genryo",checkNull(mixedData.getStrGenryoNm())});		//������(����)
				arySetTag.add(new String[]{"tanka",checkNull(mixedData.getDciTanka())});			//�P��(����)
				arySetTag.add(new String[]{"budomari",checkNull(mixedData.getDciBudomari())});		//����(����)
				arySetTag.add(new String[]{"ritu_abura",checkNull(mixedData.getDciGanyuritu())});	//���ܗL��(����)
				arySetTag.add(new String[]{"ritu_sakusan",checkNull(mixedData.getDciSakusan())});	//�|�_(����)
				arySetTag.add(new String[]{"ritu_shokuen",checkNull(mixedData.getDciShokuen())});	//�H��(����)
				arySetTag.add(new String[]{"ritu_sousan",checkNull(mixedData.getDciSosan())});		//���_(����)
				//ADD start 20121031 QP@20505
				arySetTag.add(new String[]{"ritu_msg",checkNull(mixedData.getDciMsg())});			//MSG
				//ADD end 20121031 QP@20505
				
				xmlJW210.AddXmlTag("table", "rec", arySetTag);
				arySetTag.clear();
			}

			//---------------------------------- XML���M  ------------------------------------
			//xmlJW210.dispXml();

			XmlData xmlJW = xmlJW210;
			XmlConnection xmlConnection = new XmlConnection(xmlJW210);
			xmlConnection.setStrAddress(strAjaxUrl);
			xmlConnection.XmlSend();

			//---------------------------------- XML��M  ------------------------------------
			xmlJW210 = xmlConnection.getXdocRes();

			//xmlJW210.dispXml();
			//�@�e�X�gXML�f�[�^
			//xmlJW210 = new XmlData(new File("src/main/JW210.xml"));
			//xmlJW.dispXml();

			// ���͌����m�F�f�[�^�Ɋi�[
			DataCtrl.getInstance().getMaterialMstData().setMaterialChkData(xmlJW210);

			//--------------------------------- Result�f�[�^  ----------------------------------
			DataCtrl.getInstance().getResultData().setResultData(xmlJW210);
			if ( DataCtrl.getInstance().getResultData().isReturnFlgCheck() ) {
				//�������ʂ����݂����ꍇ
				DataCtrl.getInstance().getMessageCtrl().PrintMessageGenryoCheck();
			} else {
				//�������ʂ����݂��Ȃ��ꍇ
				throw new Exception();
			}

		} catch (ExceptionBase e) {
			throw e;
		} catch (Exception e) {
			//�G���[�ݒ�
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			ex.setStrErrmsg("  ���MXML�f�[�^�쐬���������s���܂���");
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getMessage());
			throw ex;
		}finally{
		}
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
					//---------------------------- �����v�Z  -----------------------------
					if(komoku == "�����v�Z"){
						String insert = (((JCheckBox)jc).isSelected())? "1" : "0";
						//�����v�Z
						DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuJidouKeisanFg(
								DataCtrl.getInstance().getTrialTblData().checkNullInt(insert),
								DataCtrl.getInstance().getUserMstData().getDciUserid()
							);
					}

					//---------------------------- ���_�E�H��  ----------------------------
					if(komoku == "���_�E�H��"){
						String insert = (((JCheckBox)jc).isSelected())? "1" : "0";
						//���_
						DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuSousanFg(
								DataCtrl.getInstance().getTrialTblData().checkNullInt(insert),
								DataCtrl.getInstance().getUserMstData().getDciUserid()
							);
						//�H��
						DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuSyokuenFg(
								DataCtrl.getInstance().getTrialTblData().checkNullInt(insert),
								DataCtrl.getInstance().getUserMstData().getDciUserid()
							);
					}

					//----------------------- �������_�x�E�H���E�|�_ -----------------------
					if(komoku == "�������_�x�E�H���E�|�_"){
						String insert = (((JCheckBox)jc).isSelected())? "1" : "0";
						//�������_�x
						DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuSuisoSandoFg(
								DataCtrl.getInstance().getTrialTblData().checkNullInt(insert),
								DataCtrl.getInstance().getUserMstData().getDciUserid()
							);
						//�������H��
						DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuSuisoSyokuenFg(
								DataCtrl.getInstance().getTrialTblData().checkNullInt(insert),
								DataCtrl.getInstance().getUserMstData().getDciUserid()
							);
						//�������|�_
						DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuSuisoSakusanFg(
								DataCtrl.getInstance().getTrialTblData().checkNullInt(insert),
								DataCtrl.getInstance().getUserMstData().getDciUserid()
							);
					}

					//------------------------------- ���x ------------------------------
					if(komoku == "���x"){
						String insert = (((JCheckBox)jc).isSelected())? "1" : "0";
						//���x
						DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuToudoFg(
								DataCtrl.getInstance().getTrialTblData().checkNullInt(insert),
								DataCtrl.getInstance().getUserMstData().getDciUserid()
							);
					}

					//---------------------------- �S�x�E���x  ----------------------------
					if(komoku == "�S�x�E���x"){
						String insert = (((JCheckBox)jc).isSelected())? "1" : "0";
						//�S�x
						DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuNendoFg(
								DataCtrl.getInstance().getTrialTblData().checkNullInt(insert),
								DataCtrl.getInstance().getUserMstData().getDciUserid()
							);
						//���x
						DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuOndoFg(
								DataCtrl.getInstance().getTrialTblData().checkNullInt(insert),
								DataCtrl.getInstance().getUserMstData().getDciUserid()
							);
					}

					//-------------------------------- PH  -----------------------------
					if(komoku == "PH"){
						String insert = (((JCheckBox)jc).isSelected())? "1" : "0";
						//PH
						DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuPhFg(
								DataCtrl.getInstance().getTrialTblData().checkNullInt(insert),
								DataCtrl.getInstance().getUserMstData().getDciUserid()
							);
					}

					//------------------------- ���_���́E�H������  ------------------------
					if(komoku == "���_���́E�H������"){
						String insert = (((JCheckBox)jc).isSelected())? "1" : "0";
						//���_����
						DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuSousanBunsekiFg(
								DataCtrl.getInstance().getTrialTblData().checkNullInt(insert),
								DataCtrl.getInstance().getUserMstData().getDciUserid()
							);
						//�H������
						DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuSyokuenBunsekiFg(
								DataCtrl.getInstance().getTrialTblData().checkNullInt(insert),
								DataCtrl.getInstance().getUserMstData().getDciUserid()
							);
					}

					//------------------------------- ��d ------------------------------
					if(komoku == "��d"){
						String insert = (((JCheckBox)jc).isSelected())? "1" : "0";
						//��d
						DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuHijuFg(
								DataCtrl.getInstance().getTrialTblData().checkNullInt(insert),
								DataCtrl.getInstance().getUserMstData().getDciUserid()
							);
					}

//2011/05/11 QP@10181_No.42_49_72 TT Nishigawa Add Start -------------------------
					//------------------------------- ��d ---------------------------
					if(komoku == "������d"){
						String insert = (((JCheckBox)jc).isSelected())? "1" : "0";
						//��d
						DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuSuiHijuFg(
								DataCtrl.getInstance().getTrialTblData().checkNullInt(insert),
								DataCtrl.getInstance().getUserMstData().getDciUserid()
							);
					}
//2011/05/11 QP@10181_No.42_49_72 TT Nishigawa Add End -------------------------

					//----------------------------- �������� �t���[ �^�C�g��  ------------------------
					if(komoku == "���������t���["){
						String insert = (((TextboxBase)jc).getText());
						//��������
						DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuSuibunKaseiFg(
								DataCtrl.getInstance().getTrialTblData().checkNullInt(insert),
								DataCtrl.getInstance().getUserMstData().getDciUserid()
							);
					}
					//----------------------------- �A���R�[�� �t���[ �^�C�g�� -----------------------------
					if(komoku == "�A���R�[���t���["){
						String insert = (((TextboxBase)jc).getText());
						//�A���R�[��
						DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuArukoruFg(
								DataCtrl.getInstance().getTrialTblData().checkNullInt(insert),
								DataCtrl.getInstance().getUserMstData().getDciUserid()
							);
					}
					//---------------------------- �t���[�^�C�g��1 --------------------------
					if(komoku == "�t���[�^�C�g��1"){
						String insert = (((TextboxBase)jc).getText());
						//�t���[�^�C�g��1
						DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuFreeTitle_1(
								DataCtrl.getInstance().getTrialTblData().checkNullString(insert),
								DataCtrl.getInstance().getUserMstData().getDciUserid()
							);
					}
					//--------------------------- �t���[�^�C�g��1Fg  ------------------------
					if(komoku == "�t���[�^�C�g��1Fg"){
						String insert = (((JCheckBox)jc).isSelected())? "1" : "0";
						//�t���[�^�C�g��1Fg
						DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuFreeFg_1(
								DataCtrl.getInstance().getTrialTblData().checkNullInt(insert),
								DataCtrl.getInstance().getUserMstData().getDciUserid()
							);
					}
					//---------------------------- �t���[�^�C�g��2  -------------------------
					if(komoku == "�t���[�^�C�g��2"){
						String insert = (((TextboxBase)jc).getText());
						//�t���[�^�C�g��2
						DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuFreeTitle_2(
								DataCtrl.getInstance().getTrialTblData().checkNullString(insert),
								DataCtrl.getInstance().getUserMstData().getDciUserid()
							);
					}
					//--------------------------- �t���[2Fg  ------------------------
					if(komoku == "�t���[�^�C�g��2Fg"){
						String insert = (((JCheckBox)jc).isSelected())? "1" : "0";
						//�t���[�^�C�g��2Fg
						DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuFreeFg_2(
								DataCtrl.getInstance().getTrialTblData().checkNullInt(insert),
								DataCtrl.getInstance().getUserMstData().getDciUserid()
							);
					}
					//---------------------------- �t���[�^�C�g��3  -------------------------
					if(komoku == "�t���[�^�C�g��3"){
						String insert = (((TextboxBase)jc).getText());
						//�t���[�^�C�g��3
						DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuFreeTitle_3(
								DataCtrl.getInstance().getTrialTblData().checkNullString(insert),
								DataCtrl.getInstance().getUserMstData().getDciUserid()
							);
					}
					//--------------------------- �t���[�^�C�g��3Fg  ------------------------
					if(komoku == "�t���[�^�C�g��3Fg"){
						String insert = (((JCheckBox)jc).isSelected())? "1" : "0";
						//�t���[�^�C�g��3Fg
						DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuFreeFg_3(
								DataCtrl.getInstance().getTrialTblData().checkNullInt(insert),
								DataCtrl.getInstance().getUserMstData().getDciUserid()
							);
					}
// ADD start 20121001 QP@20505 No.1
					//----------------------------- ���������t���[�^�C�g��  ------------------------
					if(komoku == "���������t���[�^�C�g��"){
						String insert = (((TextboxBase)jc).getText());
						DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuFreeTitle_SuibunKasei(
								DataCtrl.getInstance().getTrialTblData().checkNullString(insert),
								DataCtrl.getInstance().getUserMstData().getDciUserid()
							);
					}
					//----------------------------- ���������t���[Flg  ------------------------
					if(komoku == "���������t���[�o��"){
						String insert = (((JCheckBox)jc).isSelected())? "1" : "0";
						DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuFreeSuibunKaseiFg(
								DataCtrl.getInstance().getTrialTblData().checkNullInt(insert),
								DataCtrl.getInstance().getUserMstData().getDciUserid()
							);
					}
					//----------------------------- �A���R�[���t���[�^�C�g�� -----------------------------
					if(komoku == "�A���R�[���t���[�^�C�g��"){
						String insert = (((TextboxBase)jc).getText());
						DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuFreeTitle_Alchol(
								DataCtrl.getInstance().getTrialTblData().checkNullString(insert),
								DataCtrl.getInstance().getUserMstData().getDciUserid()
							);
					}
					//----------------------------- �A���R�[���t���[Flg -----------------------------
					if(komoku == "�A���R�[���t���[�o��"){
						String insert = (((JCheckBox)jc).isSelected())? "1" : "0";
						DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuFreeAlcholFg(
								DataCtrl.getInstance().getTrialTblData().checkNullInt(insert),
								DataCtrl.getInstance().getUserMstData().getDciUserid()
							);
					}
					//----------------------------- �����|�_�Z�x ------------------------
					if(komoku == "�����|�_�Z�x"){
						String insert = (((JCheckBox)jc).isSelected())? "1" : "0";
						DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuJikkoSakusanNodoFg(
								DataCtrl.getInstance().getTrialTblData().checkNullInt(insert),
								DataCtrl.getInstance().getUserMstData().getDciUserid()
							);
					}
					//----------------------------- �������l�r�f ------------------------
					if(komoku == "�������l�r�f"){
						String insert = (((JCheckBox)jc).isSelected())? "1" : "0";
						DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuSuisoMSGFg(
								DataCtrl.getInstance().getTrialTblData().checkNullInt(insert),
								DataCtrl.getInstance().getUserMstData().getDciUserid()
							);
					}
					//----------------------------- �S�x�t���[�^�C�g�� ------------------------
					if(komoku == "�S�x�t���[�^�C�g��"){
						String insert = (((TextboxBase)jc).getText());
						DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuFreeTitle_Nendo(
								DataCtrl.getInstance().getTrialTblData().checkNullString(insert),
								DataCtrl.getInstance().getUserMstData().getDciUserid()
							);
					}
					//----------------------------- �S�x�t���[ Flg ------------------------
					if(komoku == "�S�x�t���[�o��"){
						String insert = (((JCheckBox)jc).isSelected())? "1" : "0";
						DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuFreeNendoFg(
								DataCtrl.getInstance().getTrialTblData().checkNullInt(insert),
								DataCtrl.getInstance().getUserMstData().getDciUserid()
							);
						DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuFreeOndoFg(
								DataCtrl.getInstance().getTrialTblData().checkNullInt(insert),
								DataCtrl.getInstance().getUserMstData().getDciUserid()
							);
					}
					//----------------------------- ���x�t���[�^�C�g�� ------------------------
					if(komoku == "���x�t���[�^�C�g��"){
						String insert = (((TextboxBase)jc).getText());
						DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuFreeTitle_Ondo(
								DataCtrl.getInstance().getTrialTblData().checkNullString(insert),
								DataCtrl.getInstance().getUserMstData().getDciUserid()
							);
					}
//					//----------------------------- ���x�t���[ Flg ------------------------
//					if(komoku == "���x�t���[�o��"){
//						String insert = (((JCheckBox)jc).isSelected())? "1" : "0";
//						DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuFreeOndoFg(
//								DataCtrl.getInstance().getTrialTblData().checkNullInt(insert),
//								DataCtrl.getInstance().getUserMstData().getDciUserid()
//							);
//					}
					//---------------------------- �t���[�^�C�g��4 --------------------------
					if(komoku == "�t���[�^�C�g��4"){
						String insert = (((TextboxBase)jc).getText());
						//�t���[�^�C�g��4
						DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuFreeTitle_4(
								DataCtrl.getInstance().getTrialTblData().checkNullString(insert),
								DataCtrl.getInstance().getUserMstData().getDciUserid()
							);
					}
					//--------------------------- �t���[�^�C�g��4Fg  ------------------------
					if(komoku == "�t���[�^�C�g��4Fg"){
						String insert = (((JCheckBox)jc).isSelected())? "1" : "0";
						//�t���[�^�C�g��4Fg
						DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuFreeFg_4(
								DataCtrl.getInstance().getTrialTblData().checkNullInt(insert),
								DataCtrl.getInstance().getUserMstData().getDciUserid()
							);
					}
					//---------------------------- �t���[�^�C�g��5  -------------------------
					if(komoku == "�t���[�^�C�g��5"){
						String insert = (((TextboxBase)jc).getText());
						//�t���[�^�C�g��5
						DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuFreeTitle_5(
								DataCtrl.getInstance().getTrialTblData().checkNullString(insert),
								DataCtrl.getInstance().getUserMstData().getDciUserid()
							);
					}
					//--------------------------- �t���[�^�C�g��5Fg  ------------------------
					if(komoku == "�t���[�^�C�g��5Fg"){
						String insert = (((JCheckBox)jc).isSelected())? "1" : "0";
						//�t���[�^�C�g��5Fg
						DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuFreeFg_5(
								DataCtrl.getInstance().getTrialTblData().checkNullInt(insert),
								DataCtrl.getInstance().getUserMstData().getDciUserid()
							);
					}
					//---------------------------- �t���[�^�C�g��6  -------------------------
					if(komoku == "�t���[�^�C�g��6"){
						String insert = (((TextboxBase)jc).getText());
						//�t���[�^�C�g��6
						DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuFreeTitle_6(
								DataCtrl.getInstance().getTrialTblData().checkNullString(insert),
								DataCtrl.getInstance().getUserMstData().getDciUserid()
							);
					}
					//--------------------------- �t���[�^�C�g��6Fg  ------------------------
					if(komoku == "�t���[�^�C�g��6Fg"){
						String insert = (((JCheckBox)jc).isSelected())? "1" : "0";
						//�t���[�^�C�g��6Fg
						DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuFreeFg_6(
								DataCtrl.getInstance().getTrialTblData().checkNullInt(insert),
								DataCtrl.getInstance().getUserMstData().getDciUserid()
							);
					}
// ADD end 20121001 QP@20505 No.1

					//---------------------------- �ꊇ�`�F�b�N  ----------------------------
					if(komoku == "�ꊇ�`�F�b�N"){
						String insert = (((JCheckBox)jc).isSelected())? "1" : "0";
						//�����v�Z
						DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuJidouKeisanFg(
								DataCtrl.getInstance().getTrialTblData().checkNullInt(insert),
								DataCtrl.getInstance().getUserMstData().getDciUserid()
							);
						//���_
						DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuSousanFg(
								DataCtrl.getInstance().getTrialTblData().checkNullInt(insert),
								DataCtrl.getInstance().getUserMstData().getDciUserid()
							);
						//�H��
						DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuSyokuenFg(
								DataCtrl.getInstance().getTrialTblData().checkNullInt(insert),
								DataCtrl.getInstance().getUserMstData().getDciUserid()
							);
						//�������_�x
						DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuSuisoSandoFg(
								DataCtrl.getInstance().getTrialTblData().checkNullInt(insert),
								DataCtrl.getInstance().getUserMstData().getDciUserid()
							);
						//�������H��
						DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuSuisoSyokuenFg(
								DataCtrl.getInstance().getTrialTblData().checkNullInt(insert),
								DataCtrl.getInstance().getUserMstData().getDciUserid()
							);
						//�������|�_
						DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuSuisoSakusanFg(
								DataCtrl.getInstance().getTrialTblData().checkNullInt(insert),
								DataCtrl.getInstance().getUserMstData().getDciUserid()
							);
// ADD start 20121002 QP@20505 No.24
						//�H���p�^�[���擾
						String ptKotei = DataCtrl.getInstance().getTrialTblData().SearchShisakuhinData().getStrPt_kotei();
						String ptValue = "";
						if(ptKotei == null || ptKotei.length() == 0){
						}else{
							//�H���p�^�[�����󔒂ł͂Ȃ��ꍇ�AValue1�擾
							ptValue = DataCtrl.getInstance().getLiteralDataKoteiPtn().selectLiteralVal1(ptKotei);
						}
						if(ptValue.equals("") || ptValue.equals(JwsConstManager.JWS_KOTEITYPE_3)){
							// �H���p�^�[���u���̑��E���H�v�܂��́A���I���̏ꍇ
// ADD end 20121002 QP@20505 No.24
							//���x
							DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuToudoFg(
									DataCtrl.getInstance().getTrialTblData().checkNullInt(insert),
									DataCtrl.getInstance().getUserMstData().getDciUserid()
								);
							//�S�x
							DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuNendoFg(
									DataCtrl.getInstance().getTrialTblData().checkNullInt(insert),
									DataCtrl.getInstance().getUserMstData().getDciUserid()
								);
							//���x
							DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuOndoFg(
									DataCtrl.getInstance().getTrialTblData().checkNullInt(insert),
									DataCtrl.getInstance().getUserMstData().getDciUserid()
								);
							//PH
							DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuPhFg(
									DataCtrl.getInstance().getTrialTblData().checkNullInt(insert),
									DataCtrl.getInstance().getUserMstData().getDciUserid()
								);
							//���_����
							DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuSousanBunsekiFg(
									DataCtrl.getInstance().getTrialTblData().checkNullInt(insert),
									DataCtrl.getInstance().getUserMstData().getDciUserid()
								);
							//�H������
							DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuSyokuenBunsekiFg(
									DataCtrl.getInstance().getTrialTblData().checkNullInt(insert),
									DataCtrl.getInstance().getUserMstData().getDciUserid()
								);
							//��d
							DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuHijuFg(
									DataCtrl.getInstance().getTrialTblData().checkNullInt(insert),
									DataCtrl.getInstance().getUserMstData().getDciUserid()
								);

	//2011/05/30�@�yQP@10181_No.42_49_72�z�H���p�^�[������@TT.NISHIGAWA�@start
							//������d
							DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuSuiHijuFg(
									DataCtrl.getInstance().getTrialTblData().checkNullInt(insert),
									DataCtrl.getInstance().getUserMstData().getDciUserid()
								);
	//2011/05/30�@�yQP@10181_No.42_49_72�z�H���p�^�[������@TT.NISHIGAWA�@end

	//2012/11/01 QP@20505 MOD Start
							//��������
//							DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuSuibunKaseiFg(
							DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuFreeSuibunKaseiFg(
									DataCtrl.getInstance().getTrialTblData().checkNullInt(insert),
									DataCtrl.getInstance().getUserMstData().getDciUserid()
								);
							//�A���R�[��
//							DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuArukoruFg(
							DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuFreeAlcholFg(
									DataCtrl.getInstance().getTrialTblData().checkNullInt(insert),
									DataCtrl.getInstance().getUserMstData().getDciUserid()
								);
	//2012/11/01 QP@20505 MOD Start
							//�t���[�^�C�g��1Fg
							DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuFreeFg_1(
									DataCtrl.getInstance().getTrialTblData().checkNullInt(insert),
									DataCtrl.getInstance().getUserMstData().getDciUserid()
								);
							//�t���[�^�C�g��2Fg
							DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuFreeFg_2(
									DataCtrl.getInstance().getTrialTblData().checkNullInt(insert),
									DataCtrl.getInstance().getUserMstData().getDciUserid()
								);
							//�t���[�^�C�g��3Fg
							DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuFreeFg_3(
									DataCtrl.getInstance().getTrialTblData().checkNullInt(insert),
									DataCtrl.getInstance().getUserMstData().getDciUserid()
								);
// ADD start 20121002 QP@20505 No.24
						}else{
							// �H���p�^�[��1�t�E2�t�̏ꍇ
							//�����|�_�Z�x
							DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuJikkoSakusanNodoFg(
									DataCtrl.getInstance().getTrialTblData().checkNullInt(insert),
									DataCtrl.getInstance().getUserMstData().getDciUserid()
								);
							//�������l�r�f
							DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuSuisoMSGFg(
									DataCtrl.getInstance().getTrialTblData().checkNullInt(insert),
									DataCtrl.getInstance().getUserMstData().getDciUserid()
								);
							//PH
							DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuPhFg(
									DataCtrl.getInstance().getTrialTblData().checkNullInt(insert),
									DataCtrl.getInstance().getUserMstData().getDciUserid()
								);
							//��d
							DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuHijuFg(
									DataCtrl.getInstance().getTrialTblData().checkNullInt(insert),
									DataCtrl.getInstance().getUserMstData().getDciUserid()
								);
							//������d
							DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuSuiHijuFg(
									DataCtrl.getInstance().getTrialTblData().checkNullInt(insert),
									DataCtrl.getInstance().getUserMstData().getDciUserid()
								);
							//�S�x�t���[
							DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuFreeNendoFg(
									DataCtrl.getInstance().getTrialTblData().checkNullInt(insert),
									DataCtrl.getInstance().getUserMstData().getDciUserid()
								);
							//���x�t���[
							DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuFreeOndoFg(
									DataCtrl.getInstance().getTrialTblData().checkNullInt(insert),
									DataCtrl.getInstance().getUserMstData().getDciUserid()
								);
							//�t���[�^�C�g��1Fg
							DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuFreeFg_1(
									DataCtrl.getInstance().getTrialTblData().checkNullInt(insert),
									DataCtrl.getInstance().getUserMstData().getDciUserid()
								);
							//�t���[�^�C�g��2Fg
							DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuFreeFg_2(
									DataCtrl.getInstance().getTrialTblData().checkNullInt(insert),
									DataCtrl.getInstance().getUserMstData().getDciUserid()
								);
							//�t���[�^�C�g��3Fg
							DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuFreeFg_3(
									DataCtrl.getInstance().getTrialTblData().checkNullInt(insert),
									DataCtrl.getInstance().getUserMstData().getDciUserid()
								);
							//�t���[�^�C�g��4Fg
							DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuFreeFg_4(
									DataCtrl.getInstance().getTrialTblData().checkNullInt(insert),
									DataCtrl.getInstance().getUserMstData().getDciUserid()
								);
							//�t���[�^�C�g��5Fg
							DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuFreeFg_5(
									DataCtrl.getInstance().getTrialTblData().checkNullInt(insert),
									DataCtrl.getInstance().getUserMstData().getDciUserid()
								);
							//�t���[�^�C�g��6Fg
							DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuFreeFg_6(
									DataCtrl.getInstance().getTrialTblData().checkNullInt(insert),
									DataCtrl.getInstance().getUserMstData().getDciUserid()
								);
						}
// ADD end 20121002 QP@20505 No.24
					}
				}
			}catch(ExceptionBase eb){

			}catch(Exception ex){

			}finally{
				//�e�X�g�\��
				//DataCtrl.getInstance().getTrialTblData().dispTrial();
			}
	    }
		public void focusGained( FocusEvent e ){
	    }
	}


	/**
	 * �`�F�b�N�؂�ւ�
	 * @param blnChk : �`�F�b�N�l
	 * @param flgKoteiP : �H���p�^�[���ʂ̐؂�ւ��t���O�i1:1�t�E2�t�A0:���̑��E���H�j
	 */
	private void setCheckboxSelectedAll(boolean blnChk, int flgKoteiP) {
//		chkAuto.setSelected(blnChk);
		chkSo_sho.setSelected(blnChk);
		chkSui.setSelected(blnChk);
// ADD start 20121002 QP@20505 No.24
		if (flgKoteiP == 0){
// ADD end 20121002 QP@20505 No.24
			chkTodo.setSelected(blnChk);
			chkOndo.setSelected(blnChk);
			chkPh.setSelected(blnChk);
			chkBun.setSelected(blnChk);
			chkHiju.setSelected(blnChk);
	// MOD start 20121002 QP@20505 No.24 ���ږ��t���[�ɕύX
	//		chkKasei.setSelected(blnChk);
	//		chkAruko.setSelected(blnChk);
			chkFreeSuibunKassei.setSelected(blnChk);
			chkFreeAlchol.setSelected(blnChk);
	// MOD start 20121002 QP@20505 No.24
			chkFree1.setSelected(blnChk);
			chkFree2.setSelected(blnChk);
			chkFree3.setSelected(blnChk);
	//2011/05/11 QP@10181_No.42_49_72 TT Nishigawa Add Start -------------------------
			chkHiju_sui.setSelected(blnChk);
	//2011/05/11 QP@10181_No.42_49_72 TT Nishigawa Add End --------------------------
// ADD start 20121002 QP@20505 No.24
		}else{
			chkJikkoSakusanNodo.setSelected(blnChk);
			chkSuisoMSG.setSelected(blnChk);
			chkFreeNendo.setSelected(blnChk);
			//chkFreeOndo.setSelected(blnChk);
			chkHiju.setSelected(blnChk);
			chkHiju_sui.setSelected(blnChk);
			chkPh.setSelected(blnChk);
			chkFree1.setSelected(blnChk);
			chkFree2.setSelected(blnChk);
			chkFree3.setSelected(blnChk);
			chkFree4.setSelected(blnChk);
			chkFree5.setSelected(blnChk);
			chkFree6.setSelected(blnChk);
		}
// ADD end 20121002 QP@20505 No.24
	}

	/**
	 * �C�x���g�����̎擾
	 * @return ActionListener�N���X
	 */
	private ActionListener getActionEvent() {
		return new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				String eventNm = e.getActionCommand();

				try {
// ADD start 20121002 QP@20505 No.24
					//�H���p�^�[���擾
					int flgKoteiP = 0;
					String ptKotei = DataCtrl.getInstance().getTrialTblData().SearchShisakuhinData().getStrPt_kotei();
					String ptValue = "";
					if(ptKotei == null || ptKotei.length() == 0){
					}else{
						//�H���p�^�[�����󔒂ł͂Ȃ��ꍇ�AValue1�擾
						ptValue = DataCtrl.getInstance().getLiteralDataKoteiPtn().selectLiteralVal1(ptKotei);
					}
					if(ptValue.equals("") || ptValue.equals(JwsConstManager.JWS_KOTEITYPE_3)){
						// �H���p�^�[���u���̑��E���H�v�܂��́A���I���̏ꍇ
						flgKoteiP = 0;
					}else{
						flgKoteiP = 1;
					}
// ADD end 20121002 QP@20505 No.24
					//---------- �ꊇ�`�F�b�N/�����`�F�b�N�{�b�N�X ActionEvent -----------------------
					if ( eventNm.equals(IKKATU_CHK) ) {
						if ( chkAll.isSelected() ) {
							//�S�`�F�b�N
							setCheckboxSelectedAll(true, flgKoteiP);
						} else {
							//�S����
							setCheckboxSelectedAll(false, flgKoteiP);
						}

					//----------------------- ���͒l�}�X�^�ύX�m�F ------------------------------
					} else if ( eventNm.equals(BUNSEKI_CHK) ) {
						//���͒l�}�X�^�ύX�m�F
						conJW210();

					}

				} catch (ExceptionBase eb) {

					DataCtrl.getInstance().PrintMessage(eb);

				} catch (Exception ec) {

					ec.printStackTrace();
					//�G���[�ݒ�
					ex = new ExceptionBase();
					ex.setStrErrCd("");
					//2011/04/12 QP@10181_No.26 TT T.Satoh Change Start -------------------------
					//ex.setStrErrmsg("����\�A �C�x���g���������s���܂���");
					ex.setStrErrmsg("�����l �C�x���g���������s���܂���");
					//2011/04/12 QP@10181_No.26 TT T.Satoh Change End ---------------------------
					ex.setStrErrShori(this.getClass().getName());
					ex.setStrMsgNo("");
					ex.setStrSystemMsg(ec.getMessage());
					DataCtrl.getInstance().PrintMessage(ex);

				} finally {

					//DataCtrl.getInstance().getTrialTblData().dispTrial();

				}
			}
		};
	}

	/************************************************************************************
	 *
	 * �������Z�X�V����
	 * @throws ExceptionBase
	 *
	 ************************************************************************************/
	public void updGenkaShisan() throws ExceptionBase {

		try {

			//����i�f�[�^�@�X�V
			table.editingStopped(new ChangeEvent(new Object()));

		} catch (Exception ec) {
			//�G���[�ݒ�
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			//2011/04/12 QP@10181_No.26 TT T.Satoh Change Start -------------------------
			//ex.setStrErrmsg("����\�A �������Z�X�V���������s���܂���");
			ex.setStrErrmsg("�����l �������Z�X�V���������s���܂���");
			//2011/04/12 QP@10181_No.26 TT T.Satoh Change End ---------------------------
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(ec.getMessage());
			throw ex;

		} finally {

		}

	}
	
	//QP@20505 2012/11/01 ADD Start
	/**
	 *  ��ʃN���A����
	 *    �󔒁A���̑����H�^�C�v��1�t2�t�^�C�v�ւƍH���p�^�[���ؑ֎��̉�ʏ���������
	 */
	public void afterEkiTypeDispClear(){

// MOD start 20130215 QP@20505
	// MOD start 20121128 QP@20505 �ۑ�No.11
	//	txtFreeSuibunKassei.setText("��������");//��������
//		txtFreeSuibunKassei.setText("");//��������
	// MOD end 20121128 QP@20505 �ۑ�No.11
		txtFreeSuibunKassei.setText("��������");
// MOD end 20130215 QP@20505
		this.add(txtFreeSuibunKassei);
// MOD start 20130215 QP@20505
	// MOD start 20121128 QP@20505 �ۑ�No.11
	//	txtFreeAlchol.setText("�A���R�[��");	//�A���R�[��
//		txtFreeAlchol.setText("");	//�A���R�[��
	// MOD end 20121128 QP@20505 �ۑ�No.11
		txtFreeAlchol.setText("�A���R�[��");
// MOD end 20130215 QP@20505
		this.add(txtFreeAlchol);
		txtFree1.setText("");					//�t���[�^�C�g���P
		this.add(txtFree1);
		txtFree2.setText("");					//�t���[�^�C�g���Q
		this.add(txtFree2);
		txtFree3.setText("");					//�t���[�^�C�g���R
		this.add(txtFree3);
		
		chkFreeSuibunKassei.setSelected(false);	//�t���[���������`�F�b�N
		chkFreeAlchol.setSelected(false);		//�t���[�A���R�[���`�F�b�N
		chkFree1.setSelected(false);			//�t���[�P�`�F�b�N
		chkFree2.setSelected(false);			//�t���[�Q�`�F�b�N
		chkFree3.setSelected(false);			//�t���[�R�`�F�b�N
		
		for(int i = 0; i < table.getColumnCount(); i++){
			table.setValueAt("" ,16 ,i );		//�t���[�P�l
			table.setValueAt("" ,17 ,i );		//�t���[�Q�l
			table.setValueAt("" ,18 ,i );		//�t���[�R�l
		}
		
	}
	
	/**
	 *  ��ʃN���A����
	 *    1�t2�t�^�C�v���󔒁A���̑����H�^�C�v�ւƍH���p�^�[���ؑ֎��̉�ʏ���������
	 */
	public void afterOtherTypeDispClear(){
// MOD start 20130215 QP@20505
	// MOD start 20121128 QP@20505 �ۑ�No.11
	//	txtNendo.setText("�S�x");
//		txtNendo.setText("");
	// MOD end 20121128 QP@20505 �ۑ�No.11
		txtNendo.setText("�S�x");
// MOD end 20130215 QP@20505
		this.add(txtNendo);
// MOD start 20130215 QP@20505
	// MOD start 20121128 QP@20505 �ۑ�No.11
	//	txtOndo.setText("���x�i���j");
//		txtOndo.setText("");
	// MOD end 20121128 QP@20505 �ۑ�No.11
		txtOndo.setText("���x�i���j");
// MOD end 20130215 QP@20505
		this.add(txtOndo);
		
		txtFree1.setText("");					//�t���[�^�C�g���P
		this.add(txtFree1);
		txtFree2.setText("");					//�t���[�^�C�g���Q
		this.add(txtFree2);
		txtFree3.setText("");					//�t���[�^�C�g���R
		this.add(txtFree3);
		txtFree4.setText("");					//�t���[�^�C�g���S
		this.add(txtFree4);
		txtFree5.setText("");					//�t���[�^�C�g���T
		this.add(txtFree5);
		txtFree6.setText("");					//�t���[�^�C�g���U
		this.add(txtFree6);
		
		chkFreeNendo.setSelected(false);		//�t���[�S�x���x�`�F�b�N
		chkFree1.setSelected(false);			//�t���[�P�`�F�b�N
		chkFree2.setSelected(false);			//�t���[�Q�`�F�b�N
		chkFree3.setSelected(false);			//�t���[�R�`�F�b�N
		chkFree4.setSelected(false);			//�t���[�S�`�F�b�N
		chkFree5.setSelected(false);			//�t���[�T�`�F�b�N
		chkFree6.setSelected(false);			//�t���[�U�`�F�b�N
		
		for(int i = 0; i < table.getColumnCount(); i++){
			table.setValueAt("" ,13 ,i );		//�t���[�P�l
			table.setValueAt("" ,14 ,i );		//�t���[�Q�l
			table.setValueAt("" ,15 ,i );		//�t���[�R�l
			table.setValueAt("" ,16 ,i );		//�t���[�S�l
			table.setValueAt("" ,17 ,i );		//�t���[�T�l
			table.setValueAt("" ,18 ,i );		//�t���[�U�l
		}
	}
	//QP@20505 2012/11/01 ADD End

	/**
	 * NULL�`�F�b�N�����i�I�u�W�F�N�g�j
	 * @throws ExceptionBase
	 */
	private String checkNull(Object val){
		String ret = "NULL";
		if(val != null){
			ret = val.toString();
		}
		return ret;
	}

	/**
	 * NULL�`�F�b�N�����i���l�j
	 * @throws ExceptionBase
	 */
	private String checkNull(int val){
		String ret = "NULL";
		if(val >= 0){
			ret = Integer.toString(val);
		}
		return ret;
	}

	/**
	 * @return chkAuto
	 */
	public CheckboxBase getChkAuto() {
		return chkAuto;
	}
	
// ADD start 20121017 QP@20505 No.11
	/**
	 * @return chkAuto
	 */
	public ButtonBase getCmdBunsekiMstData() {
		return btnBunsekiMstData;
	}
// ADD end 20121017 QP@20505 No.11

	/**
	 * @return table
	 */
	public TableBase getTable() {
		return table;
	}

	//2011/04/20 QP@10181_No.41 TT T.Satoh Add Start -------------------------
	/************************************************************************************
	 *
	 *   scroll�Q�b�^�[
	 *   @author TT satoh
	 *   @return scroll :�@�����l�̃X�N���[���o�[
	 *
	 ************************************************************************************/
	public JScrollPane getScroll() {
		return scroll;
	}

	/************************************************************************************
	 *
	 *   scroll�Z�b�^�[
	 *   @author TT satoh
	 *   @param _scroll : �����l�̃X�N���[���o�[
	 *
	 ************************************************************************************/
	public void setScroll(JScrollPane _scroll) {
		scroll = _scroll;
	}
	//2011/04/20 QP@10181_No.41 TT T.Satoh Add End ---------------------------

}

