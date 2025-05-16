package jp.co.blueflag.shisaquick.jws.panel;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.DefaultCellEditor;
import javax.swing.JScrollPane;

import jp.co.blueflag.shisaquick.jws.base.ManufacturingData;
import jp.co.blueflag.shisaquick.jws.base.TrialData;
import jp.co.blueflag.shisaquick.jws.celleditor.MiddleCellEditor;
import jp.co.blueflag.shisaquick.jws.common.ButtonBase;
import jp.co.blueflag.shisaquick.jws.common.CheckboxBase;
import jp.co.blueflag.shisaquick.jws.common.ComboBase;
import jp.co.blueflag.shisaquick.jws.common.ExceptionBase;
import jp.co.blueflag.shisaquick.jws.common.JwsConstManager;
import jp.co.blueflag.shisaquick.jws.common.PanelBase;
import jp.co.blueflag.shisaquick.jws.common.ScrollBase;
import jp.co.blueflag.shisaquick.jws.common.TextAreaBase;
import jp.co.blueflag.shisaquick.jws.data.DataCtrl;
import jp.co.blueflag.shisaquick.jws.label.ItemLabel;
import jp.co.blueflag.shisaquick.jws.manager.MessageCtrl;
import jp.co.blueflag.shisaquick.jws.table.Trial1Table;

/************************************************************************************
 *
 * �yA05-08�z �����H���p�l������p�̃N���X
 *
 * @author TT.katayama
 * @since 2009/04/06
 *
 ************************************************************************************/
public class ManufacturingPanel extends PanelBase {
	private static final long serialVersionUID = 1L;

	private ComboBase combo;					//�R���{�{�b�N�X����
	//2011/04/19 QP@10181_No.31 TT T.Satoh Change Start -------------------------
	//private ItemLabel itemLabel;					//���x������
	private ItemLabel[] itemLabel;					//���x������
	//2011/04/19 QP@10181_No.31 TT T.Satoh Change End ---------------------------
	private CheckboxBase checkbox;			//�`�F�b�N�{�b�N�X����
	private TextAreaBase memoText;			//�����e�L�X�g
	private ScrollBase memoScroll;				//�����e�L�X�g�p�X�N���[��
	private ButtonBase[] button;					//�{�^��

	private MessageCtrl messageCtrl;			//���b�Z�[�W����
	private ExceptionBase ex;						//�G���[����

	private int ShisakuSeq = 0;
	private int TyuuiNo = 0;

	private boolean tyuuiFg = false;			//���ӎ����\���t���O
	private boolean memoFg = false;			//���상���\���t���O

	/**
	 * �R���X�g���N�^
	 */
	public ManufacturingPanel() throws ExceptionBase {
		//�X�[�p�[�N���X�̃R���X�g���N�^���Ăяo��
		super();

		try {
			//�p�l���̐ݒ�
			this.setPanel();

		} catch(Exception e) {
			e.printStackTrace();
			//�G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("�����H���p�l���̃R���X�g���N�^�����s���܂����B");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw this.ex;
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
		this.setBackground(JwsConstManager.SHISAKU_ITEM_COLOR);
	}

	/************************************************************************************
	 *
	 * ����������
	 *
	 ************************************************************************************/
	public void init() {
		ShisakuSeq = 0;
		TyuuiNo = 0;
		memoText.setText(null);

		//���ӎ���or���상���I��
		int sentaku = DataCtrl.getInstance().getTrialTblData().SearchShisakuhinData().getIntChuiFg();

		//�I�𖳂�
		if(sentaku == 0){

			tyuuiFg = false;
			memoFg = false;
			combo.setSelectedIndex(0);
			checkbox.setSelected(false);

		//���ӎ����\��
		}else if(sentaku == 1){

			tyuuiFg = true;
			memoFg = false;
			combo.setSelectedIndex(0);
			checkbox.setSelected(true);

		//���상���\��
		}else if(sentaku == 2){

			tyuuiFg = false;
			memoFg = true;
			combo.setSelectedIndex(1);
			checkbox.setSelected(true);

		}
	}

	/************************************************************************************
	 *
	 * �R���g���[���z�u
	 * @throws ExceptionBase
	 *
	 ************************************************************************************/
	public void addControl() throws ExceptionBase{
		try{
			//������
			this.removeAll();

			int x, y, width, height;
			int dispWidth = 320;

			///
			/// �R���{�{�b�N�X
			///
			x = 5;
			y = 5;
			width = dispWidth - 20;
			height = 20;
			this.combo = new ComboBase();
			this.combo.addItem("�����H��/���ӎ���");
			this.combo.addItem("���상��");
			this.combo.addItem("���@No");
			this.combo.addActionListener(new selectCombo());
			this.combo.setBounds(x,y,width,height);
			//���[�h�ҏW
			if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
					JwsConstManager.JWS_COMPONENT_0050, DataCtrl.getInstance().getParamData().getStrMode())){
				combo.setBackground(Color.lightGray);
				combo.setEditable(false);
			}
			this.add(this.combo);

			///
			/// �����e�L�X�g�G���A
			///
			this.memoText = new TextAreaBase();
			//���W�E�T�C�Y�ݒ�
			x = 0;
			y += height + 5;
			width = dispWidth - 8;
			height = 420;
			//�e�L�X�g�܂�Ԃ��ɐݒ�
			this.memoText.setLineWrap(true);
			//���[�h�ҏW
			if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
					JwsConstManager.JWS_COMPONENT_0051, DataCtrl.getInstance().getParamData().getStrMode())){
				memoText.setBackground(Color.lightGray);
				memoText.setEditable(false);
			}
			//�X�N���[���p�l������
			this.memoScroll = new ScrollBase(this.memoText);
			this.memoScroll.setBounds(x,y,width,height);
			this.memoScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
			//�p�l���ɒǉ�
			this.add(this.memoScroll);

			///
			/// ���x��(��ɕ\��)
			///
			x = dispWidth - 80;
			y += height + 5;
			width = 50;
			height = 15;
			//2011/04/19 QP@10181_No.31 TT T.Satoh Change Start -------------------------
			//this.itemLabel = new ItemLabel();
			this.itemLabel = new ItemLabel[2];
			this.itemLabel[0] = new ItemLabel();
			//this.itemLabel.setText("��ɕ\��");
			this.itemLabel[0].setText("��ɕ\��");
			//this.itemLabel.setBounds(x,y,width,height);
			this.itemLabel[0].setBounds(x,y,width,height);
			//this.add(this.itemLabel);
			this.add(this.itemLabel[0]);
			//2011/04/19 QP@10181_No.31 TT T.Satoh Change End ---------------------------

			///
			/// ��ɕ\���`�F�b�N�{�b�N�X
			///
			x += width;
			width = 50;
			this.checkbox = new CheckboxBase();
			this.checkbox.setBounds(x,y,20,height);
			this.checkbox.setBackground(JwsConstManager.SHISAKU_ITEM_COLOR);
			this.checkbox.addActionListener(new hyojiCheck());
			this.checkbox.setActionCommand("hyoji");
			//���[�h�ҏW
			if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
					JwsConstManager.JWS_COMPONENT_0052, DataCtrl.getInstance().getParamData().getStrMode())){
				checkbox.setEnabled(false);
			}
			this.add(this.checkbox);

			//2011/04/19 QP@10181_No.31 TT T.Satoh Add Start -------------------------
			///
			/// ���x��(�H���ŁF)
			///
			x = 0;
			y += height + 5;
			width = 120;
			height = 15;
			this.itemLabel[1] = new ItemLabel();
			if (TyuuiNo == 0) {
				this.itemLabel[1].setText("<html>�H���ŁF<font color=\"red\"><b>���I���ł�</font>");
			} else {
				this.itemLabel[1].setText("�H���ŁF" + TyuuiNo);
			}
			this.itemLabel[1].setBounds(x,y,width,height);
			this.add(this.itemLabel[1]);
			//2011/04/19 QP@10181_No.31 TT T.Satoh Add End ---------------------------

			///
			/// �{�^��
			/// [0:�V�K, 1:�X�V]
			///
			this.button = new ButtonBase[2];
			//�V�K
			width = 80;
			x = dispWidth - width - 100;
			//2011/04/19 QP@10181_No.31 TT T.Satoh Change Start -------------------------
			//y += height + 5;
			//2011/04/19 QP@10181_No.31 TT T.Satoh Change End ---------------------------
			height = 38;
			this.button[0] = new ButtonBase();
			this.button[0].setFont(new Font("Default", Font.PLAIN, 11));
			this.button[0].setBounds(x,y,width,height);
			this.button[0].setText("�V�K");
			//���[�h�ҏW
			if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
					JwsConstManager.JWS_COMPONENT_0053, DataCtrl.getInstance().getParamData().getStrMode())){
				button[0].setEnabled(false);
			}
			//�X�V
			x = dispWidth - 100;
			this.button[1] = new ButtonBase();
			this.button[1].setFont(new Font("Default", Font.PLAIN, 11));
			this.button[1].setBounds(x,y,width,height);
			this.button[1].setText("�X�V");
			//���[�h�ҏW
			if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
					JwsConstManager.JWS_COMPONENT_0054, DataCtrl.getInstance().getParamData().getStrMode())){
				button[1].setEnabled(false);
			}
			//�{�^�����p�l���ɒǉ�
			for ( int i=0; i<this.button.length; i++ ) {
				this.add(this.button[i]);
			}

		}catch(ExceptionBase eb){
			throw eb;

		}catch(Exception ec){
			//�G���[�ݒ�
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			ex.setStrErrmsg("�����H���p�l���̃R���g���[���z�u���������s���܂���");
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(ec.getMessage());
			throw ex;

		}finally{

		}
	}

	/************************************************************************************
	 *
	 * �����H��/���ӎ����\��
	 *
	 ************************************************************************************/
	public void dispSeizo() throws ExceptionBase{
		try{
			//�����H��/���ӎ����f�[�^�擾
			String seizo = "";
			if(TyuuiNo > 0){
				ArrayList tyuuiData = DataCtrl.getInstance().getTrialTblData().SearchSeizoKouteiData(TyuuiNo);
				ManufacturingData mfData = (ManufacturingData)tyuuiData.get(0);
				seizo = mfData.getStrTyuiNaiyo();
			}
			//�\��
			memoText.setText(seizo);

		}catch(ExceptionBase eb){
			throw eb;

		}catch(Exception ec){
			//�G���[�ݒ�
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			ex.setStrErrmsg("�����H���p�l���̐����H��/���ӎ����\�����������s���܂���");
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(ec.getMessage());
			throw ex;

		}finally{

		}
	}

	/************************************************************************************
	 *
	 * ���상���\��
	 *
	 ************************************************************************************/
	public void dispMemo() throws ExceptionBase{
		try{
			String sisakuMemo = DataCtrl.getInstance().getTrialTblData().SearchShisakuhinData().getStrShisakuMemo();

			//�\��
			memoText.setText(sisakuMemo);

		}catch(Exception ec){
			ec.printStackTrace();
			//�G���[�ݒ�
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			ex.setStrErrmsg("�����H���p�l���̎��상���\�������s���܂���");
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(ec.getMessage());
			throw ex;

		}finally{

		}
	}

	/************************************************************************************
	 *
	 * ���@No�\��
	 *
	 ************************************************************************************/
	public void dispSeiho() throws ExceptionBase{
		try{
			String strOut = "";
			if(ShisakuSeq > 0){
				//���@No�擾
				ArrayList seihoData = DataCtrl.getInstance().getTrialTblData().SearchShisakuRetuData(ShisakuSeq);
				TrialData trData = (TrialData)((DataCtrl.getInstance().getTrialTblData().SearchShisakuRetuData(ShisakuSeq)).get(0));
				String seiho1 = trData.getStrSeihoNo1();
				String seiho2 = trData.getStrSeihoNo2();
				String seiho3 = trData.getStrSeihoNo3();
				String seiho4 = trData.getStrSeihoNo4();
				String seiho5 = trData.getStrSeihoNo5();
				//�\���f�[�^�쐬
				StringBuffer strMessage = new StringBuffer();
				strMessage.append((seiho1 == null)?"":seiho1);
				strMessage.append("\n"+((seiho2 == null)?"":seiho2));
				strMessage.append("\n"+((seiho3 == null)?"":seiho3));
				strMessage.append("\n"+((seiho4 == null)?"":seiho4));
				strMessage.append("\n"+((seiho5 == null)?"":seiho5));
				strOut = strMessage.toString();
			}
			//�\��
			memoText.setText(strOut);

		}catch(ExceptionBase eb){
			throw eb;

		}catch(Exception ec){
			ec.printStackTrace();
			//�G���[�ݒ�
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			ex.setStrErrmsg("�����H���p�l���̐��@No�\�����������s���܂���");
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(ec.getMessage());
			throw ex;

		}finally{

		}
	}

	/************************************************************************************
	 *
	 *   ��ɕ\���`�F�b�N�C�x���g�N���X : ��ɕ\���`�F�b�N�{�b�N�X�������̏���
	 *   @author TT nishigawa
	 *
	 ************************************************************************************/
	class hyojiCheck implements ActionListener{
		public void actionPerformed(ActionEvent e){
			try{
				String event_name = e.getActionCommand();

				// �yKPX1500671�zADD start
				// �f�[�^�ύX�t���OON
				DataCtrl.getInstance().getTrialTblData().setHenkouFg(true);
				// �yKPX1500671�zADD end
				
				//��ɕ\���`�F�b�N�{�b�N�X����
				if ( event_name == "hyoji") {

					//�R���{�{�b�N�X�I���C���f�b�N�X�擾
					int selectCombo = combo.getSelectedIndex();

					//�����H��/���ӎ����@�I��
					if(selectCombo == 0){

						//�`�F�b�N
						if(checkbox.isSelected()){
							//�����H��/���ӎ����@�\��FG��ON
							tyuuiFg = true;

							//���상���@�\��FG��OFF
							memoFg = false;

							//�f�[�^�}��
							DataCtrl.getInstance().getTrialTblData().SearchShisakuhinData().setIntChuiFg(1);

						//�`�F�b�N������
						}else{
							//�����H��/���ӎ����@�\��FG��OFF
							tyuuiFg = false;

							//�f�[�^�}��
							DataCtrl.getInstance().getTrialTblData().SearchShisakuhinData().setIntChuiFg(0);
						}

					//���상���@�I��
					}else if(selectCombo == 1){

						//�`�F�b�N
						if(checkbox.isSelected()){
							//�����H��/���ӎ����@�\��FG��OFF
							tyuuiFg = false;

							//���상���@�\��FG��ON
							memoFg = true;

							//�f�[�^�}��
							DataCtrl.getInstance().getTrialTblData().SearchShisakuhinData().setIntChuiFg(2);

						//�`�F�b�N������
						}else{
							//���상���@�\��FG��OFF
							memoFg = false;

							//�f�[�^�}��
							DataCtrl.getInstance().getTrialTblData().SearchShisakuhinData().setIntChuiFg(0);
						}


					//���@No�@�I��
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
	 *   �����R���{�I���C�x���g�N���X
	 *   @author TT nishigawa
	 *
	 ************************************************************************************/
	class selectCombo implements ActionListener{
		public void actionPerformed(ActionEvent e){
			try{
				//-------------------------- �����H���\������  ----------------------------
				//�����H���̑I���R���{�{�b�N�X�̑I��l���擾
				ComboBase cb = (ComboBase)e.getSource();
				int selectCombo = cb.getSelectedIndex();

				//�����H��/���ӎ����̏ꍇ
				if(selectCombo == 0){

					//�����H��/���ӎ����\��
					dispSeizo();

					//��ɕ\���`�F�b�N�{�b�N�X�ݒ�
					checkbox.setEnabled(true);
					if(tyuuiFg){
						checkbox.setSelected(true);
					}else{
						checkbox.setSelected(false);
					}

					//�e�L�X�g�G���A�̎g�p��
					memoText.setBackground(Color.WHITE);
					memoText.setEnabled(true);

					//���[�h�ҏW
					if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
							JwsConstManager.JWS_COMPONENT_0051, DataCtrl.getInstance().getParamData().getStrMode())){
						memoText.setBackground(Color.lightGray);
						memoText.setEnabled(false);
					}

					//2011/04/19 QP@10181_No.31 TT T.Satoh Add Start -------------------------
					//�H���ŕ\��
					itemLabel[1].setVisible(true);
					//2011/04/19 QP@10181_No.31 TT T.Satoh Add End ---------------------------

					//�V�K�{�^���̎g�p��
					button[0].setEnabled(true);

					//���[�h�ҏW
					if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
							JwsConstManager.JWS_COMPONENT_0053, DataCtrl.getInstance().getParamData().getStrMode())){
						button[0].setEnabled(false);
					}

					//�X�V�{�^���̎g�p��
					button[1].setEnabled(true);

					//���[�h�ҏW
					if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
							JwsConstManager.JWS_COMPONENT_0054, DataCtrl.getInstance().getParamData().getStrMode())){
						button[1].setEnabled(false);
					}
				}

				//���상���̏ꍇ
				if(selectCombo == 1){

					//���상���\��
					dispMemo();

					//��ɕ\���`�F�b�N�{�b�N�X�ݒ�
					checkbox.setEnabled(true);
					if(memoFg){
						checkbox.setSelected(true);
					}else{
						checkbox.setSelected(false);
					}

					//�e�L�X�g�G���A�̎g�p��
					memoText.setBackground(Color.WHITE);
					memoText.setEnabled(true);

					//���[�h�ҏW
					if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
							JwsConstManager.JWS_COMPONENT_0051, DataCtrl.getInstance().getParamData().getStrMode())){
						memoText.setBackground(Color.lightGray);
						memoText.setEnabled(false);
					}

					//2011/04/19 QP@10181_No.31 TT T.Satoh Add Start -------------------------
					//�H���Ŕ�\��
					itemLabel[1].setVisible(false);
					//2011/04/19 QP@10181_No.31 TT T.Satoh Add End ---------------------------

					//�V�K�{�^���̎g�p��
					button[0].setEnabled(false);

					//�X�V�{�^���̎g�p��
					button[1].setEnabled(true);

					//���[�h�ҏW
					if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
							JwsConstManager.JWS_COMPONENT_0054, DataCtrl.getInstance().getParamData().getStrMode())){
						button[1].setEnabled(false);
					}
				}

				//���@No�̏ꍇ
				if(selectCombo == 2){
					//���@No�\��
					dispSeiho();

					//��ɕ\���`�F�b�N�{�b�N�X�ݒ�
					checkbox.setSelected(false);
					checkbox.setEnabled(false);

					//�����e�L�X�g�G���A
					memoText.setBackground(Color.lightGray);

					memoText.setEnabled(false);

					//2012/02/22 TT H.SHIMA Java6�Ή� start
					memoText.setBackground(Color.white);
					memoText.setDisabledTextColor(Color.BLACK);
					//2012/02/22 TT H.SHIMA Java6�Ή� end

					//2011/04/19 QP@10181_No.31 TT T.Satoh Add Start -------------------------
					//�H���Ŕ�\��
					itemLabel[1].setVisible(false);
					//2011/04/19 QP@10181_No.31 TT T.Satoh Add End ---------------------------

					//�{�^���̎g�p��
					button[0].setEnabled(false);
					button[1].setEnabled(false);
				}

			}catch(Exception ec){
				ec.printStackTrace();
				//�G���[�ݒ�
				ex = new ExceptionBase();
				ex.setStrErrCd("");
				ex.setStrErrmsg("�����H���p�l���̐����R���{�I�����������s���܂���");
				ex.setStrErrShori(this.getClass().getName());
				ex.setStrMsgNo("");
				ex.setStrSystemMsg(ec.getMessage());
				//���b�Z�[�W�\��
				DataCtrl.getInstance().PrintMessage(ex);

			}finally{

			}
		}
	}

	/************************************************************************************
	 *
	 * ����SEQ�Z�b�^�[&�Q�b�^�[
	 *
	 ************************************************************************************/
	public int getShisakuSeq() {
		return ShisakuSeq;
	}
	public void setShisakuSeq(int shisakuSeq) {
		ShisakuSeq = shisakuSeq;
	}

	/************************************************************************************
	 *
	 * ���ӎ���No�Z�b�^�[&�Q�b�^�[
	 *
	 ************************************************************************************/
	public int getTyuuiNo() {
		return TyuuiNo;
	}
	public void setTyuuiNo(int tyuuiNo) {
		TyuuiNo = tyuuiNo;
	}

	/************************************************************************************
	 *
	 * ��ɕ\���`�F�b�N�{�b�N�X�Z�b�^�[&�Q�b�^�[
	 *
	 ************************************************************************************/
	public CheckboxBase getCheckbox() {
		return checkbox;
	}
	public void setCheckbox(CheckboxBase checkbox) {
		this.checkbox = checkbox;
	}

	/************************************************************************************
	 *
	 * �I���R���{�{�b�N�X�{�b�N�X�Z�b�^�[&�Q�b�^�[
	 *
	 ************************************************************************************/
	public ComboBase getCombo() {
		return combo;
	}
	public void setCombo(ComboBase combo) {
		this.combo = combo;
	}

	//2011/04/19 QP@10181_No.31 TT T.Satoh Add Start -------------------------
	/************************************************************************************
	 *
	 * ���x���z��Z�b�^�[&�Q�b�^�[
	 *
	 ************************************************************************************/
	public ItemLabel[] getLabel() {
		return itemLabel;
	}
	public void setLabel(ItemLabel[] itemLabel) {
		this.itemLabel = itemLabel;
	}
	//2011/04/19 QP@10181_No.31 TT T.Satoh Add End ---------------------------

	/************************************************************************************
	 *
	 * �{�^���z��Z�b�^�[&�Q�b�^�[
	 *
	 ************************************************************************************/
	public ButtonBase[] getButton() {
		return button;
	}
	public void setButton(ButtonBase[] button) {
		this.button = button;
	}

	/************************************************************************************
	 *
	 * �e�L�X�g�G���A�Z�b�^�[&�Q�b�^�[
	 *
	 ************************************************************************************/
	public TextAreaBase getMemoText() {
		return memoText;
	}

	public void setMemoText(TextAreaBase memoText) {
		this.memoText = memoText;
	}

	/************************************************************************************
	 *
	 * �����H��/���ӎ����@�\��FG�Q�b�^�[
	 *
	 ************************************************************************************/
	public boolean isTyuuiFg() {
		return tyuuiFg;
	}

	/************************************************************************************
	 *
	 * ���상���@�\��FG�Q�b�^�[
	 *
	 ************************************************************************************/
	public boolean isMemoFg() {
		return memoFg;
	}


}