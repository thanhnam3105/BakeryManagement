package jp.co.blueflag.shisaquick.jws.panel;

import java.awt.Color;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import jp.co.blueflag.shisaquick.jws.base.BushoData;
import jp.co.blueflag.shisaquick.jws.base.CostMaterialData;
import jp.co.blueflag.shisaquick.jws.base.EigyoTantoData;
import jp.co.blueflag.shisaquick.jws.base.KaishaData;
import jp.co.blueflag.shisaquick.jws.base.LiteralData;
import jp.co.blueflag.shisaquick.jws.base.PrototypeData;
import jp.co.blueflag.shisaquick.jws.base.XmlData;
import jp.co.blueflag.shisaquick.jws.common.ButtonBase;
import jp.co.blueflag.shisaquick.jws.common.ComboBase;
import jp.co.blueflag.shisaquick.jws.common.ExceptionBase;
import jp.co.blueflag.shisaquick.jws.common.InputComboBase;
import jp.co.blueflag.shisaquick.jws.common.JwsConstManager;
import jp.co.blueflag.shisaquick.jws.common.PanelBase;
import jp.co.blueflag.shisaquick.jws.common.ScrollBase;
import jp.co.blueflag.shisaquick.jws.common.TableBase;
import jp.co.blueflag.shisaquick.jws.common.TextAreaBase;
import jp.co.blueflag.shisaquick.jws.common.TextboxBase;
import jp.co.blueflag.shisaquick.jws.data.DataCtrl;
import jp.co.blueflag.shisaquick.jws.disp.EigyoTantoSearchDisp;
import jp.co.blueflag.shisaquick.jws.label.ItemIndicationLabel;
import jp.co.blueflag.shisaquick.jws.label.ItemLabel;
import jp.co.blueflag.shisaquick.jws.manager.XmlConnection;
import jp.co.blueflag.shisaquick.jws.textbox.HankakuTextbox;
import jp.co.blueflag.shisaquick.jws.textbox.NumelicTextbox;

/**
 * 
 * ��{���(����\�B)�p�l���N���X
 * 
 */
public class Trial3Panel extends PanelBase {
	private static final long serialVersionUID = 1L;

	private Color Yellow = JwsConstManager.SHISAKU_ITEM_COLOR2;
	
	private ComboBase cmbKaisha;			//�S����ЃR���{�{�b�N�X
	private ComboBase cmbKojo;				//�S���H��R���{�{�b�N�X
	private ComboBase cmbShosu;			//�����w��R���{�{�b�N�X
	private TextboxBase txtGroup;
	private TextboxBase txtTeam;
	private TextboxBase txtIrisu;
	private InputComboBase cmbYoryo;
//mod start --------------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.26
//	private TextboxBase txtBaikibou;
	private NumelicTextbox txtBaikibou;
//mod end --------------------------------------------------------------------------------------
//add start -------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.33,34
	private ComboBase cmbtani;
//add end   -------------------------------------------------------------------------------
	
	//�yQP@00342�z
	TextboxBase txtEigyo;
	
	//��ЃR���{�{�b�N�X ActionCommand�l
	private final String kaishaCommand = "kaishaCmb_click";
	
	private int l_labelx = 15;						//�܂Ƃ߃��x��X�ʒu�i�J�n�_�j
	private int l_labelw = 20;						//�܂Ƃ߃��x����
	private int labelx = l_labelx + l_labelw - 1;	//���x��X�ʒu
	private int labely = 5;							//���x��Y�ʒu�i�J�n�_�j
	private int labelw = 170;						//���x����
	private int labelh = 20;							//���x������
	private int mg_hgt = labelh - 1;				//���x���Ԋu
	private int ctrlx  = labelx + labelw;			//�R���g���[��X�ʒu
	private int ctrlw  = 275;						//�R���g���[����
	private int ctrlh  = labelh + 1;					//�R���g���[������
	private int sogo_labelx = labelx + labelw + ctrlw + 10;	//�����������x��X�ʒu
	private int sogo_labely = labely;				//�����������x��Y�ʒu
	private int sogo_labelw = 500;				//�����������x����
	private int sogo_labelh = labelh;				//�����������x������
	private int sogo_ctrlh = 306;					//���������R���g���[������
	private int bikouw = 15;
	private int bikouh = 15;
	private int tani = 50;
		
	private PrototypeData PrototypeData;

	//�yQP@00342�z
	private EigyoTantoSearchDisp et = new EigyoTantoSearchDisp("�S���c�ƌ���");
	
	private XmlConnection xcon;
	private XmlData xmlJW620;
	private ExceptionBase ex;
	
	/**
	 * 
	 * ��{���(����\�B)�p�l���N���X�@�R���X�g���N�^
	 * @throws ExceptionBase 
	 * 
	 */
	public Trial3Panel() throws ExceptionBase {
		super();

		try {
			//����i�f�[�^�ێ��N���X�̐���
			PrototypeData = new PrototypeData();
			PrototypeData = DataCtrl.getInstance().getTrialTblData().SearchShisakuhinData();
	
			//�f�[�^�ݒ�iJW020�j
			this.conJW620(Integer.toString(PrototypeData.getIntKaishacd()));
			
			//�yQP@00342�z
			et.setVisible(false);
			
			//��ʕ\��
			this.panelDisp();
		} catch (ExceptionBase e) {
			throw e;
		} catch (Exception e) {
			//�G���[�ݒ�
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			//2011/04/12 QP@10181_No.26 TT T.Satoh Change Start -------------------------
			//ex.setStrErrmsg("����\�B�p�l���N���X�@�R���X�g���N�^���������s���܂���");
			ex.setStrErrmsg("��{���p�l���N���X�@�R���X�g���N�^���������s���܂���");
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
	 * ��{���(����\�B)�p�l���N���X�@��ʕ\��
	 * @throws ExceptionBase 
	 * 
	 */
	private void panelDisp() throws ExceptionBase{
		String strLiteralCd = "";

		//�v�Z���ڗp������ : (�v�Z)�@�ƕ\������
		String strKeisan = " (" + JwsConstManager.JWS_MARK_0005 + ")";
								
		try {
			this.setLayout(null);
			this.setBackground(Color.WHITE);
			
			//���ڃ��x���ݒ�i����\�O���[�v�j
			ItemLabel hlShisaku = new ItemLabel();
			hlShisaku.setBorder(new LineBorder(Color.BLACK, 1));
			hlShisaku.setHorizontalAlignment(SwingConstants.CENTER);
			hlShisaku.setBounds(l_labelx, labely, l_labelw, (labelh*8)-7);
			hlShisaku.setText("<html>��<br>��<br>�\");
			this.add(hlShisaku);
			
			//�����O���[�v
			ItemLabel hlGroup = new ItemLabel();
			hlGroup.setBorder(new LineBorder(Color.BLACK, 1));
			hlGroup.setBounds(labelx, labely, labelw, labelh);
			hlGroup.setText("<html><font color=\"red\">�K�{</font>�@�����O���[�v");
			this.add(hlGroup);
			
				//�����O���[�v�p�e�L�X�g�{�b�N�X
				txtGroup = new TextboxBase();
				txtGroup.setBackground(Color.WHITE);
				txtGroup.setBounds(ctrlx, labely, ctrlw, ctrlh);
				txtGroup.setText(PrototypeData.getStrGroupNm());
				//���[�h�ҏW
				if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
						JwsConstManager.JWS_COMPONENT_0092, DataCtrl.getInstance().getParamData().getStrMode())){
					txtGroup.setBackground(Color.lightGray);
					txtGroup.setBounds(ctrlx, labely, ctrlw-1, ctrlh);
					txtGroup.setEditable(false);
				}else{
					txtGroup.addFocusListener(new FocusCheck("�����O���[�v"));
				}
				this.add(txtGroup);
			
			//�����`�[��
			labely = labely + mg_hgt;
			ItemLabel hlTeam = new ItemLabel();
			hlTeam.setBorder(new LineBorder(Color.BLACK, 1));
			hlTeam.setBounds(labelx, labely, labelw, labelh);
			hlTeam.setText("<html><font color=\"red\">�K�{</font>�@�����`�[��");
			this.add(hlTeam);
	
				//�����`�[���p�e�L�X�g�{�b�N�X
				txtTeam = new TextboxBase();
				txtTeam.setBackground(Color.WHITE);
				txtTeam.setBounds(ctrlx, labely, ctrlw, ctrlh);
				txtTeam.setText(PrototypeData.getStrTeamNm());
				//���[�h�ҏW
				if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
						JwsConstManager.JWS_COMPONENT_0093, DataCtrl.getInstance().getParamData().getStrMode())){
					txtTeam.setBackground(Color.lightGray);
					txtTeam.setBounds(ctrlx, labely, ctrlw-1, ctrlh);
					txtTeam.setEditable(false);
				}else{
					txtTeam.addFocusListener(new FocusCheck("�����`�[��"));
				}
				this.add(txtTeam);
			
			//�ꊇ�\��
			labely = labely + mg_hgt;
			ItemLabel hlIkatu = new ItemLabel();
			hlIkatu.setBorder(new LineBorder(Color.BLACK, 1));
			hlIkatu.setBounds(labelx, labely, labelw, labelh);
			hlIkatu.setText("�@�@�@�ꊇ�\��");
			this.add(hlIkatu);
			
				//�ꊇ�\���p�R���{�{�b�N�X����
				ComboBase cmbIkatu = new ComboBase();
				cmbIkatu.setBackground(Color.WHITE);
				cmbIkatu.setBounds(ctrlx, labely, ctrlw, ctrlh);
				//�R���{�{�b�N�X�ɒl��ݒ�
				strLiteralCd = PrototypeData.getStrIkatu();
				setLiteralCmb(cmbIkatu, DataCtrl.getInstance().getLiteralDataIkatu(), strLiteralCd, 0);
				//���[�h�ҏW
				if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
						JwsConstManager.JWS_COMPONENT_0094, DataCtrl.getInstance().getParamData().getStrMode())){
					cmbIkatu.setBounds(ctrlx, labely, ctrlw-1, ctrlh);
					cmbIkatu.setEnabled(false);
				}else{
					cmbIkatu.addFocusListener(new FocusCheck("�ꊇ�\��"));
				}
				//�p�l���ɒǉ�
				this.add(cmbIkatu);
			
			//�W������
			labely = labely + mg_hgt;
			ItemLabel hlZyanru = new ItemLabel();
			hlZyanru.setBorder(new LineBorder(Color.BLACK, 1));
			hlZyanru.setBounds(labelx, labely, labelw, labelh);
			hlZyanru.setText("�@�@�@�W������");
			this.add(hlZyanru);
	
				//�W�������p�R���{�{�b�N�X����
				ComboBase cmbZyanru = new ComboBase();
				cmbZyanru.setBounds(ctrlx, labely, ctrlw, ctrlh);
				//�R���{�{�b�N�X�ɒl��ݒ�
				strLiteralCd = PrototypeData.getStrZyanru();
				setLiteralCmb(cmbZyanru, DataCtrl.getInstance().getLiteralDataZyanru(), strLiteralCd, 0);
				//���[�h�ҏW
				if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
						JwsConstManager.JWS_COMPONENT_0095, DataCtrl.getInstance().getParamData().getStrMode())){
					cmbZyanru.setBounds(ctrlx, labely, ctrlw-1, ctrlh);
					cmbZyanru.setEnabled(false);
				}else{
					cmbZyanru.addFocusListener(new FocusCheck("�W������"));
				}
				//�p�l���ɒǉ�
				this.add(cmbZyanru);
	
			//���[�U
			labely = labely + mg_hgt;
			ItemLabel hlUser = new ItemLabel();
			hlUser.setBorder(new LineBorder(Color.BLACK, 1));
			hlUser.setBounds(labelx, labely, labelw, labelh);
			//2011/05/06 QP@10181_No.71 TT T.Satoh Change Start -------------------------
			//hlUser.setText("�@�@�@���[�U");
			hlUser.setText("<html><font color=\"red\">�K�{</font>�@���[�U");
			//2011/05/06 QP@10181_No.71 TT T.Satoh Change End ---------------------------
			this.add(hlUser);
	
				//���[�U�p�R���{�{�b�N�X����
				ComboBase cmbUser = new ComboBase();
				cmbUser.setBounds(ctrlx, labely, ctrlw, ctrlh);
				//�R���{�{�b�N�X�ɒl��ݒ�
				strLiteralCd = PrototypeData.getStrUsercd();
				setLiteralCmb(cmbUser, DataCtrl.getInstance().getLiteralDataUser(), strLiteralCd, 0);
				//���[�h�ҏW
				if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
						JwsConstManager.JWS_COMPONENT_0096, DataCtrl.getInstance().getParamData().getStrMode())){
					cmbUser.setBounds(ctrlx, labely, ctrlw-1, ctrlh);
					cmbUser.setEnabled(false);
				}else{
					cmbUser.addFocusListener(new FocusCheck("���[�U"));
				}
				//�p�l���ɒǉ�
				this.add(cmbUser);
			
			//��������
			labely = labely + mg_hgt;
			ItemLabel hlTokutyo = new ItemLabel();
			hlTokutyo.setBorder(new LineBorder(Color.BLACK, 1));
			hlTokutyo.setBounds(labelx, labely, labelw, labelh);
			hlTokutyo.setText("�@�@�@��������");
			this.add(hlTokutyo);
	
				//���������p�R���{�{�b�N�X����
				InputComboBase cmbTokutyo = new InputComboBase();
				cmbTokutyo.setBounds(ctrlx, labely, ctrlw, ctrlh);
				//�R���{�{�b�N�X�ɒl��ݒ�
				strLiteralCd = PrototypeData.getStrTokutyo();
				setLiteralCmb(cmbTokutyo, DataCtrl.getInstance().getLiteralDataTokutyo(), strLiteralCd, 1);
				//���[�h�ҏW
				if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
						JwsConstManager.JWS_COMPONENT_0097, DataCtrl.getInstance().getParamData().getStrMode())){
					cmbTokutyo.setBounds(ctrlx, labely, ctrlw-1, ctrlh);
					cmbTokutyo.setEditable(false);
					cmbTokutyo.setEnabled(false);
					
				}else{
					cmbTokutyo.getEditor().getEditorComponent().addFocusListener(new FocusCheck("��������"));
				}
				//�p�l���ɒǉ�
				this.add(cmbTokutyo);
			
			//�p�r
			labely = labely + mg_hgt;
			ItemLabel hlYoto = new ItemLabel();
			hlYoto.setBorder(new LineBorder(Color.BLACK, 1));
			hlYoto.setBounds(labelx, labely, labelw, labelh);
			hlYoto.setText("�@�@�@�p�r");
			this.add(hlYoto);
	
				//�p�r�p�R���{�{�b�N�X����
				InputComboBase cmbYoto = new InputComboBase();
				cmbYoto.setBounds(ctrlx, labely, ctrlw, ctrlh);
				//�R���{�{�b�N�X�ɒl��ݒ�
				strLiteralCd = PrototypeData.getStrYoto();
				setLiteralCmb(cmbYoto, DataCtrl.getInstance().getLiteralDataYoto(), strLiteralCd, 1);
				//���[�h�ҏW
				if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
						JwsConstManager.JWS_COMPONENT_0098, DataCtrl.getInstance().getParamData().getStrMode())){
					cmbYoto.setBounds(ctrlx, labely, ctrlw-1, ctrlh);
					cmbYoto.setEditable(false);
					cmbYoto.setEnabled(false);
				}else{
					cmbYoto.getEditor().getEditorComponent().addFocusListener(new FocusCheck("�p�r"));
				}
				//�p�l���ɒǉ�
				this.add(cmbYoto);
			
			//���i��
			labely = labely + mg_hgt;
			ItemLabel hlKakaku = new ItemLabel();
			hlKakaku.setBorder(new LineBorder(Color.BLACK, 1));
			hlKakaku.setBounds(labelx, labely, labelw, labelh);
			hlKakaku.setText("�@�@�@���i��");
			this.add(hlKakaku);
	
				//���i�їp�R���{�{�b�N�X����
				ComboBase cmbKakaku = new ComboBase();
				cmbKakaku.setBounds(ctrlx, labely, ctrlw, ctrlh);
				//�R���{�{�b�N�X�ɒl��ݒ�
				strLiteralCd = PrototypeData.getStrKakaku();
				setLiteralCmb(cmbKakaku, DataCtrl.getInstance().getLiteralDataKakaku(), strLiteralCd, 0);
				//���[�h�ҏW
				if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
						JwsConstManager.JWS_COMPONENT_0099, DataCtrl.getInstance().getParamData().getStrMode())){
					cmbKakaku.setBounds(ctrlx, labely, ctrlw-1, ctrlh);
					cmbKakaku.setEnabled(false);
				}else{
					cmbKakaku.addFocusListener(new FocusCheck("���i��"));
				}
				//�p�l���ɒǉ�
				this.add(cmbKakaku);
			
			//���ڃ��x���ݒ�i�I�v�V�����j
			labely = labely + mg_hgt;
			ItemLabel hlOption = new ItemLabel();
			hlOption.setBorder(new LineBorder(Color.BLACK, 1));
			hlOption.setHorizontalAlignment(SwingConstants.CENTER);
			hlOption.setBounds(l_labelx, labely, l_labelw, (labelh*2)-1);
			this.add(hlOption);
			
			//���
			ItemLabel hlShubetu = new ItemLabel();
			hlShubetu.setBorder(new LineBorder(Color.BLACK, 1));
			hlShubetu.setBounds(labelx, labely, labelw, labelh);
			hlShubetu.setText("<html><font color=\"red\">�K�{</font>�@���");
			this.add(hlShubetu);
			
				//��ʗp�R���{�{�b�N�X����
				ComboBase cmbShubetu = new ComboBase();
				cmbShubetu.setBounds(ctrlx, labely, ctrlw, ctrlh);
				//�R���{�{�b�N�X�ɒl��ݒ�
				strLiteralCd = PrototypeData.getStrShubetu();
				setLiteralCmb(cmbShubetu, DataCtrl.getInstance().getLiteralDataShubetu(), strLiteralCd, 0);		//�K�{
				//���[�h�ҏW
				if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
						JwsConstManager.JWS_COMPONENT_0100, DataCtrl.getInstance().getParamData().getStrMode())){
					cmbShubetu.setBounds(ctrlx, labely, ctrlw-1, ctrlh);
					cmbShubetu.setEnabled(false);
				}else{
					cmbShubetu.addFocusListener(new FocusCheck("���"));
				}
				//�p�l���ɒǉ�
				this.add(cmbShubetu);
			
			//�����w��
			labely = labely + mg_hgt;
			ItemLabel hlShosu = new ItemLabel();
			hlShosu.setBorder(new LineBorder(Color.BLACK, 1));
			hlShosu.setBounds(labelx, labely, labelw, labelh);
			hlShosu.setText("�@�@�@�����w��");
			this.add(hlShosu);
			
				//�����w��p�R���{�{�b�N�X����
				cmbShosu = new ComboBase();
				cmbShosu.setBounds(ctrlx, labely, ctrlw, ctrlh);
				//�R���{�{�b�N�X�ɒl��ݒ�
				strLiteralCd =PrototypeData.getStrShosu();
				if(strLiteralCd != null && strLiteralCd.length()>0){
					for(int i=strLiteralCd.length(); i<3; i++){
						strLiteralCd = "0" + strLiteralCd;
					}
				}
				setLiteralCmb(cmbShosu, DataCtrl.getInstance().getLiteralDataShosu(), strLiteralCd, 0);
				//���[�h�ҏW
				if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
						JwsConstManager.JWS_COMPONENT_0101, DataCtrl.getInstance().getParamData().getStrMode())){
					cmbShosu.setBounds(ctrlx, labely, ctrlw-1, ctrlh);
					cmbShosu.setEnabled(false);
				}
				//�p�l���ɒǉ�
				this.add(cmbShosu);
			
			//���ڃ��x���ݒ�i�������Z�\�j
			labely = labely + mg_hgt;
			ItemLabel hlGenkahyo = new ItemLabel();
			hlGenkahyo.setBorder(new LineBorder(Color.BLACK, 1));
			hlGenkahyo.setHorizontalAlignment(SwingConstants.CENTER);
			hlGenkahyo.setBounds(l_labelx, labely, l_labelw, (labelh*20)-19);
			hlGenkahyo.setText("<html>��<br>��<br>��<br>�Z<br>�\");
			this.add(hlGenkahyo);
			
			//�S�����
			ItemLabel hlKaisha = new ItemLabel();
			hlKaisha.setBorder(new LineBorder(Color.BLACK, 1));
			hlKaisha.setBounds(labelx, labely, labelw, labelh);
			hlKaisha.setText("<html><font color=\"red\">�K�{</font>�@�H��@�S�����");
			this.add(hlKaisha);
	
				//�S����Зp�R���{�{�b�N�X����
				cmbKaisha = new ComboBase();
				cmbKaisha.setBounds(ctrlx, labely, ctrlw, ctrlh);
				//�R���{�{�b�N�X�ɒl��ݒ�
				setKaishaCmb(cmbKaisha);
				//�C�x���g�N���X�̐ݒ�
				cmbKaisha.addActionListener(this.getActionEvent());
				cmbKaisha.setActionCommand(kaishaCommand);
				//���[�h�ҏW
				if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
						JwsConstManager.JWS_COMPONENT_0102, DataCtrl.getInstance().getParamData().getStrMode())){
					cmbKaisha.setBounds(ctrlx, labely, ctrlw-1, ctrlh);
					cmbKaisha.setEnabled(false);
				}
				//�p�l���ɒǉ�
				this.add(cmbKaisha);
			
			//�S���H��
			labely = labely + mg_hgt;
			ItemLabel hlKojo = new ItemLabel();
			hlKojo.setBorder(new LineBorder(Color.BLACK, 1));
			hlKojo.setBounds(labelx, labely, labelw, labelh);
			hlKojo.setText("<html><font color=\"red\">�K�{</font>�@�H��@�S���H��");
			this.add(hlKojo);
	
				//�S���H��p�R���{�{�b�N�X����
				cmbKojo = new ComboBase();
				cmbKojo.setBounds(ctrlx, labely, ctrlw, ctrlh);
				//�R���{�{�b�N�X�ɒl��ݒ�
				setBushoCmb(cmbKojo);
				//���[�h�ҏW
				if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
						JwsConstManager.JWS_COMPONENT_0103, DataCtrl.getInstance().getParamData().getStrMode())){
					cmbKojo.setBounds(ctrlx, labely, ctrlw-1, ctrlh);
					cmbKojo.setEnabled(false);
				}
				//�p�l���ɒǉ�
				this.add(cmbKojo);
			
			//�S���c��
			//�yQP@00342�z
			labely = labely + mg_hgt;
			ItemLabel hlEigyo = new ItemLabel();
			hlEigyo.setBorder(new LineBorder(Color.BLACK, 1));
			hlEigyo.setBounds(labelx, labely, labelw-50, labelh);
			//2011/05/06 QP@10181_No.71 TT T.Satoh Change Start -------------------------
			//hlEigyo.setText("�@�@�@�S���c��");
			hlEigyo.setText("<html><font color=\"red\">�K�{</font>�@�S���c��");
			//2011/05/06 QP@10181_No.71 TT T.Satoh Change End ---------------------------
			this.add(hlEigyo);
			
			//�S���c�ƌ����{�^��
			ButtonBase btnSearchEigyo = new ButtonBase();
			btnSearchEigyo.setBounds(labelx+119, labely, 51, labelh);
			btnSearchEigyo.setHorizontalAlignment(SwingConstants.CENTER);
			btnSearchEigyo.addActionListener(this.getActionEvent());
			btnSearchEigyo.setActionCommand("btnSerchEigyo");
			btnSearchEigyo.setMargin(new Insets(0, 0, 0, 0));
			btnSearchEigyo.setText("����");
			//���[�h�ҏW
			if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
					JwsConstManager.JWS_COMPONENT_0133, DataCtrl.getInstance().getParamData().getStrMode())){
				btnSearchEigyo.setEnabled(false);
			}
			this.add(btnSearchEigyo);
			
			//�S���c�Ɨp�e�L�X�g�{�b�N�X����
			txtEigyo = new TextboxBase();
			//txtEigyo.setBackground(Yellow);
			txtEigyo.setBounds(ctrlx, labely, ctrlw, ctrlh);
			txtEigyo.setText(PrototypeData.getStrNmEigyoTanto());
			//���[�h�ҏW
			if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
					JwsConstManager.JWS_COMPONENT_0104, DataCtrl.getInstance().getParamData().getStrMode())){
				txtEigyo.setBackground(Color.lightGray);
				txtEigyo.setBounds(ctrlx, labely, ctrlw-1, ctrlh-2);
				txtEigyo.setEditable(false);
			}else{
				txtEigyo.setEditable(false);
				//txtEigyo.addFocusListener(new FocusCheck("�S���c��"));
			}
			this.add(txtEigyo);
			
//			labely = labely + mg_hgt;
//			ItemLabel hlEigyo = new ItemLabel();
//			hlEigyo.setBorder(new LineBorder(Color.BLACK, 1));
//			hlEigyo.setBounds(labelx, labely, labelw, labelh);
//			hlEigyo.setText("�@�@�@�S���c��");
//			this.add(hlEigyo);
//			
//				//�S���c�Ɨp�R���{�{�b�N�X����
//				InputComboBase cmbEigyo = new InputComboBase();
//				cmbEigyo.setBounds(ctrlx, labely, ctrlw, ctrlh);
//				//�R���{�{�b�N�X�ɒl��ݒ�
//				strLiteralCd = PrototypeData.getStrTantoEigyo();
//				setLiteralCmb(cmbEigyo, DataCtrl.getInstance().getLiteralDataTanto(), strLiteralCd, 1);
//				//���[�h�ҏW
//				if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
//						JwsConstManager.JWS_COMPONENT_0104, DataCtrl.getInstance().getParamData().getStrMode())){
//					cmbEigyo.setBounds(ctrlx, labely, ctrlw-1, ctrlh);
//					cmbEigyo.setEditable(false);
//					cmbEigyo.setEnabled(false);
//				}else{
//					cmbEigyo.getEditor().getEditorComponent().addFocusListener(new FocusCheck("�S���c��"));
//				}
//				//�p�l���ɒǉ�
//				this.add(cmbEigyo);
			
			
			//�������@
			labely = labely + mg_hgt;
			ItemLabel hlSeizo = new ItemLabel();
			hlSeizo.setBorder(new LineBorder(Color.BLACK, 1));
			hlSeizo.setBounds(labelx, labely, labelw, labelh);
			//2011/05/06 QP@10181_No.71 TT T.Satoh Change Start -------------------------
			//hlSeizo.setText("�@�@�@�������@");
			hlSeizo.setText("<html><font color=\"red\">�K�{</font>�@�������@");
			//2011/05/06 QP@10181_No.71 TT T.Satoh Change End ---------------------------
			this.add(hlSeizo);
			
				//�������@�p�R���{�{�b�N�X����
				ComboBase cmbSeizo = new ComboBase();
				cmbSeizo.setBounds(ctrlx, labely, ctrlw, ctrlh);
				//�R���{�{�b�N�X�ɒl��ݒ�
				strLiteralCd = PrototypeData.getStrSeizocd();
				setLiteralCmb(cmbSeizo, DataCtrl.getInstance().getLiteralDataSeizo(), strLiteralCd, 0);
				//���[�h�ҏW
				if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
						JwsConstManager.JWS_COMPONENT_0105, DataCtrl.getInstance().getParamData().getStrMode())){
					cmbSeizo.setBounds(ctrlx, labely, ctrlw-1, ctrlh);
					cmbSeizo.setEnabled(false);
				}else{
					cmbSeizo.addFocusListener(new FocusCheck("�������@"));
				}
				//�p�l���ɒǉ�
				this.add(cmbSeizo);
			
			//�[�U���@
			labely = labely + mg_hgt;
			ItemLabel hlZyuten = new ItemLabel();
			hlZyuten.setBorder(new LineBorder(Color.BLACK, 1));
			hlZyuten.setBounds(labelx, labely, labelw, labelh);
			//2011/05/06 QP@10181_No.71 TT T.Satoh Change Start -------------------------
			//hlZyuten.setText("�@�@�@�[�U���@");
			hlZyuten.setText("<html><font color=\"red\">�K�{</font>�@�[�U���@");
			//2011/05/06 QP@10181_No.71 TT T.Satoh Change End ---------------------------
			this.add(hlZyuten);
			
				//�[�U���@�p�R���{�{�b�N�X����
				ComboBase cmbZyuten = new ComboBase();
				cmbZyuten.setBounds(ctrlx, labely, ctrlw, ctrlh);
				//�R���{�{�b�N�X�ɒl��ݒ�
				strLiteralCd = PrototypeData.getStrZyutencd();
				setLiteralCmb(cmbZyuten, DataCtrl.getInstance().getLiteralDataZyuten(), strLiteralCd, 0);
				//���[�h�ҏW
				if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
						JwsConstManager.JWS_COMPONENT_0106, DataCtrl.getInstance().getParamData().getStrMode())){
					cmbZyuten.setBounds(ctrlx, labely, ctrlw-1, ctrlh);
					cmbZyuten.setEnabled(false);
				}else{
					cmbZyuten.addFocusListener(new FocusCheck("�[�U���@"));
				}
				//�p�l���ɒǉ�
				this.add(cmbZyuten);
			
			//�E�ە��@
			labely = labely + mg_hgt;
			ItemLabel hlSakin = new ItemLabel();
			hlSakin.setBorder(new LineBorder(Color.BLACK, 1));
			hlSakin.setBounds(labelx, labely, labelw, labelh);
			//2011/05/06 QP@10181_No.71 TT T.Satoh Change Start -------------------------
			//hlSakin.setText("�@�@�@�E�ە��@");
			hlSakin.setText("<html><font color=\"red\">�K�{</font>�@�E�ە��@");
			//2011/05/06 QP@10181_No.71 TT T.Satoh Change End ---------------------------
			this.add(hlSakin);
			
				//�E�ە��@�p�R���{�{�b�N�X����
				InputComboBase cmbSakin = new InputComboBase();
				cmbSakin.setBounds(ctrlx, labely, ctrlw, ctrlh);
				//�R���{�{�b�N�X�ɒl��ݒ�
				strLiteralCd = PrototypeData.getStrSakin();
				setLiteralCmb(cmbSakin, DataCtrl.getInstance().getLiteralDataSakin(), strLiteralCd, 1);
				//���[�h�ҏW
				if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
						JwsConstManager.JWS_COMPONENT_0107, DataCtrl.getInstance().getParamData().getStrMode())){
					cmbSakin.setBounds(ctrlx, labely, ctrlw-1, ctrlh);
					cmbSakin.setEditable(false);
					cmbSakin.setEnabled(false);
				}else{
					cmbSakin.getEditor().getEditorComponent().addFocusListener(new FocusCheck("�E�ە��@"));
				}		
				//�p�l���ɒǉ�
				this.add(cmbSakin);
			
			//�e��E���
			labely = labely + mg_hgt;
			ItemLabel hlYoki = new ItemLabel();
			hlYoki.setBorder(new LineBorder(Color.BLACK, 1));
			hlYoki.setBounds(labelx, labely, labelw, labelh);
			//2011/05/06 QP@10181_No.71 TT T.Satoh Change Start -------------------------
			//hlYoki.setText("�@�@�@�e��E���");
			hlYoki.setText("<html><font color=\"red\">�K�{</font>�@�e��E���");
			//2011/05/06 QP@10181_No.71 TT T.Satoh Change End ---------------------------
			this.add(hlYoki);
			
				//�e��E��ޗp�R���{�{�b�N�X����
				InputComboBase cmbYoki = new InputComboBase();
				cmbYoki.setBounds(ctrlx, labely, ctrlw, ctrlh);
				//�R���{�{�b�N�X�ɒl��ݒ�
				strLiteralCd = PrototypeData.getStrYokihozai();
				setLiteralCmb(cmbYoki, DataCtrl.getInstance().getLiteralDataYoki(), strLiteralCd, 1);
				//���[�h�ҏW
				if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
						JwsConstManager.JWS_COMPONENT_0108, DataCtrl.getInstance().getParamData().getStrMode())){
					cmbYoki.setBounds(ctrlx, labely, ctrlw-1, ctrlh);
					cmbYoki.setEditable(false);
					cmbYoki.setEnabled(false);
				}else{
					cmbYoki.getEditor().getEditorComponent().addFocusListener(new FocusCheck("�e����"));
				}
				//�p�l���ɒǉ�
				this.add(cmbYoki);
			
			//�e��
			labely = labely + mg_hgt;
			ItemLabel hlYoryo = new ItemLabel();
			hlYoryo.setBorder(new LineBorder(Color.BLACK, 1));
			hlYoryo.setBounds(labelx, labely, labelw, labelh);
			hlYoryo.setText("<html><font color=\"red\">�K�{</font>�@�e�ʁi���l���́j" + strKeisan);
			this.add(hlYoryo);
			
				//�e�ʗp�R���{�{�b�N�X����
				cmbYoryo = new InputComboBase();
				cmbYoryo.setBounds(ctrlx, labely, ctrlw-tani+2, ctrlh);
				//�R���{�{�b�N�X�ɒl��ݒ�
				strLiteralCd = PrototypeData.getStrYoryo();
				setLiteralCmb(cmbYoryo, DataCtrl.getInstance().getLiteralDataYoryo(), strLiteralCd, 1);		//�K�{
				//���[�h�ҏW
				if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
						JwsConstManager.JWS_COMPONENT_0109, DataCtrl.getInstance().getParamData().getStrMode())){
					cmbYoryo.setEditable(false);
					cmbYoryo.setEnabled(false);
				}else{
					cmbYoryo.getEditor().getEditorComponent().addFocusListener(new FocusCheck("�e��"));
				}
				//�p�l���ɒǉ�
				this.add(cmbYoryo);
				
				//�P�ʗp�R���{�{�b�N�X����
				cmbtani = new ComboBase();
				cmbtani.setBounds(ctrlx+ctrlw-tani, labely, tani, ctrlh);
				//�R���{�{�b�N�X�ɒl��ݒ�
				strLiteralCd = PrototypeData.getStrTani();
				setLiteralCmbYoryo(cmbtani, DataCtrl.getInstance().getLiteralDataTani(), strLiteralCd, 0);			//�K�{
				//cmbtani.addFocusListener(new FocusCheck("�e�ʒP��"));
				//���[�h�ҏW
				if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
						JwsConstManager.JWS_COMPONENT_0110, DataCtrl.getInstance().getParamData().getStrMode())){
					cmbtani.setEnabled(false);
				}else{
					//cmbtani.addFocusListener(new FocusCheck("�e�ʒP��"));
				}
				//�p�l���ɒǉ�
				this.add(cmbtani);
			
			//���萔
			labely = labely + mg_hgt;
			ItemLabel hlIrisu = new ItemLabel();
			hlIrisu.setBorder(new LineBorder(Color.BLACK, 1));
			hlIrisu.setBounds(labelx, labely, labelw, labelh);
			hlIrisu.setText("<html><font color=\"red\">�K�{</font>�@���萔" + strKeisan);
			this.add(hlIrisu);
	
				//���萔�p�e�L�X�g�{�b�N�X����
				txtIrisu = new TextboxBase();
				txtIrisu.setBackground(Yellow);
				txtIrisu.setBounds(ctrlx, labely, ctrlw, ctrlh);
				txtIrisu.setText(PrototypeData.getStrIrisu());
				//���[�h�ҏW
				if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
						JwsConstManager.JWS_COMPONENT_0111, DataCtrl.getInstance().getParamData().getStrMode())){
					txtIrisu.setBackground(Color.lightGray);
					txtIrisu.setBounds(ctrlx, labely, ctrlw-1, ctrlh);
					txtIrisu.setEditable(false);
				}else{
					txtIrisu.addFocusListener(new FocusCheck("���萔"));
				}
				this.add(txtIrisu);
	
			//�׎p
			labely = labely + mg_hgt;
			ItemLabel hlNisugata = new ItemLabel();
			hlNisugata.setBorder(new LineBorder(Color.BLACK, 1));
			hlNisugata.setBounds(labelx, labely, labelw, labelh);
			hlNisugata.setText("�@�@�@�׎p");
			this.add(hlNisugata);
			
				//�׎p�p�R���{�{�b�N�X����
				InputComboBase cmbNisugata = new InputComboBase();
				cmbNisugata.setBounds(ctrlx, labely, ctrlw, ctrlh);
				//�R���{�{�b�N�X�ɒl��ݒ�
				strLiteralCd = PrototypeData.getStrNishugata();
				setLiteralCmb(cmbNisugata, DataCtrl.getInstance().getLiteralDataNisugata(), strLiteralCd, 1);
				//���[�h�ҏW
				if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
						JwsConstManager.JWS_COMPONENT_0112, DataCtrl.getInstance().getParamData().getStrMode())){
					cmbNisugata.setBounds(ctrlx, labely, ctrlw-1, ctrlh);
					cmbNisugata.setEditable(false);
					cmbNisugata.setEnabled(false);
				}else{
					cmbNisugata.getEditor().getEditorComponent().addFocusListener(new FocusCheck("�׎p"));
				}
				//�p�l���ɒǉ�
				this.add(cmbNisugata);
			
			//�戵���x
			labely = labely + mg_hgt;
			ItemLabel hlTori = new ItemLabel();
			hlTori.setBorder(new LineBorder(Color.BLACK, 1));
			hlTori.setBounds(labelx, labely, labelw, labelh);
			//2011/05/06 QP@10181_No.71 TT T.Satoh Change Start -------------------------
			//hlTori.setText("�@�@�@�戵���x");
			hlTori.setText("<html><font color=\"red\">�K�{</font>�@�戵���x");
			//2011/05/06 QP@10181_No.71 TT T.Satoh Change End ---------------------------
			this.add(hlTori);
			
				//�戵���x�p�R���{�{�b�N�X����
				ComboBase cmbTori = new ComboBase();
				cmbTori.setBounds(ctrlx, labely, ctrlw, ctrlh);
				//�R���{�{�b�N�X�ɒl��ݒ�
				strLiteralCd = PrototypeData.getStrOndo();
				setLiteralCmb(cmbTori, DataCtrl.getInstance().getLiteralDataOndo(), strLiteralCd, 0);
				//���[�h�ҏW
				if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
						JwsConstManager.JWS_COMPONENT_0113, DataCtrl.getInstance().getParamData().getStrMode())){
					cmbTori.setBounds(ctrlx, labely, ctrlw-1, ctrlh);
					cmbTori.setEnabled(false);
				}else{
					cmbTori.addFocusListener(new FocusCheck("�戵���x"));
				}
				//�p�l���ɒǉ�
				this.add(cmbTori);
			
			//�ܖ�����
			labely = labely + mg_hgt;
			ItemLabel hlShomi = new ItemLabel();
			hlShomi.setBorder(new LineBorder(Color.BLACK, 1));
			hlShomi.setBounds(labelx, labely, labelw, labelh);
			//2011/05/06 QP@10181_No.71 TT T.Satoh Change Start -------------------------
			//hlShomi.setText("�@�@�@�ܖ�����");
			hlShomi.setText("<html><font color=\"red\">�K�{</font>�@�ܖ�����");
			//2011/05/06 QP@10181_No.71 TT T.Satoh Change End ---------------------------
			this.add(hlShomi);
			
				//�ܖ����ԗp�R���{�{�b�N�X����
				InputComboBase cmbShomi = new InputComboBase();
				cmbShomi.setBounds(ctrlx, labely, ctrlw, ctrlh);
				//�R���{�{�b�N�X�ɒl��ݒ�
				strLiteralCd = PrototypeData.getStrShomi();
				setLiteralCmb(cmbShomi, DataCtrl.getInstance().getLiteralDataShomi(), strLiteralCd, 1);
				//���[�h�ҏW
				if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
						JwsConstManager.JWS_COMPONENT_0114, DataCtrl.getInstance().getParamData().getStrMode())){
					cmbShomi.setBounds(ctrlx, labely, ctrlw-1, ctrlh);
					cmbShomi.setEditable(false);
					cmbShomi.setEnabled(false);
				}else{
					cmbShomi.getEditor().getEditorComponent().addFocusListener(new FocusCheck("�ܖ�����"));
				}
				//�p�l���ɒǉ�
				this.add(cmbShomi);
			
			//������]
			labely = labely + mg_hgt;
			ItemLabel hlGenkibou = new ItemLabel();
			hlGenkibou.setBorder(new LineBorder(Color.BLACK, 1));
			hlGenkibou.setBounds(labelx, labely, labelw, labelh);
			hlGenkibou.setText("�@�@�@������]");
			this.add(hlGenkibou);
			
				//������]�p�e�L�X�g�{�b�N�X����
				TextboxBase txtGenkibou = new TextboxBase();
				txtGenkibou.setBackground(Yellow);
				txtGenkibou.setBounds(ctrlx, labely, ctrlw, ctrlh);
				txtGenkibou.setText(PrototypeData.getStrGenka());
				//���[�h�ҏW
				if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
						JwsConstManager.JWS_COMPONENT_0115, DataCtrl.getInstance().getParamData().getStrMode())){
					txtGenkibou.setBackground(Color.lightGray);
					txtGenkibou.setBounds(ctrlx, labely, ctrlw-1, ctrlh);
					txtGenkibou.setEditable(false);
				}else{
					txtGenkibou.addFocusListener(new FocusCheck("������]"));
				}
				this.add(txtGenkibou);
			
			//������]
			labely = labely + mg_hgt;
			ItemLabel hlBaikibou = new ItemLabel();
			hlBaikibou.setBorder(new LineBorder(Color.BLACK, 1));
			hlBaikibou.setBounds(labelx, labely, labelw, labelh);
			hlBaikibou.setText("�@�@�@������]" + strKeisan);
			this.add(hlBaikibou);
			
				//������]�p�e�L�X�g�{�b�N�X����
//mod start --------------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.26
//				txtBaikibou = new TextboxBase();
				txtBaikibou = new NumelicTextbox();								//���l�̂ݓ��͉�
//				txtBaikibou.setBackground(Yellow);
				txtBaikibou.setHorizontalAlignment(SwingConstants.LEFT);		//���񂹂ɐݒ�
//mod end --------------------------------------------------------------------------------------
				txtBaikibou.setBounds(ctrlx, labely, ctrlw, ctrlh);
				txtBaikibou.setText(PrototypeData.getStrBaika());
				//���[�h�ҏW
				if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
						JwsConstManager.JWS_COMPONENT_0116, DataCtrl.getInstance().getParamData().getStrMode())){
					txtBaikibou.setBackground(Color.lightGray);
					txtBaikibou.setBounds(ctrlx, labely, ctrlw-1, ctrlh);
					txtBaikibou.setEditable(false);
				}else{
					txtBaikibou.addFocusListener(new FocusCheck("������]"));
				}
				this.add(txtBaikibou);
			
			//�z�蕨��
			labely = labely + mg_hgt;
			ItemLabel hlSotei = new ItemLabel();
			hlSotei.setBorder(new LineBorder(Color.BLACK, 1));
			hlSotei.setBounds(labelx, labely, labelw, labelh);
			hlSotei.setText("�@�@�@�z�蕨��");
			this.add(hlSotei);
	
				//�z�蕨�ʗp�e�L�X�g�{�b�N�X����
				TextboxBase txtSotei = new TextboxBase();
				txtSotei.setBackground(Yellow);
				txtSotei.setBounds(ctrlx, labely, ctrlw, ctrlh);
				txtSotei.setText(PrototypeData.getStrSotei());
				//���[�h�ҏW
				if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
						JwsConstManager.JWS_COMPONENT_0117, DataCtrl.getInstance().getParamData().getStrMode())){
					txtSotei.setBackground(Color.lightGray);
					txtSotei.setBounds(ctrlx, labely, ctrlw-1, ctrlh);
					txtSotei.setEditable(false);
				}else{
					txtSotei.addFocusListener(new FocusCheck("�z�蕨��"));
				}
				this.add(txtSotei);
			
			//�̔�����
			labely = labely + mg_hgt;
			ItemLabel hlHanbai = new ItemLabel();
			hlHanbai.setBorder(new LineBorder(Color.BLACK, 1));
			hlHanbai.setBounds(labelx, labely, labelw, labelh);
			hlHanbai.setText("�@�@�@�̔�����");
			this.add(hlHanbai);
	
				//�̔������p�e�L�X�g�{�b�N�X����
				TextboxBase txtHanbai = new TextboxBase();
				txtHanbai.setBackground(Yellow);
				txtHanbai.setBounds(ctrlx, labely, ctrlw, ctrlh);
				txtHanbai.setText(PrototypeData.getStrHatubai());
				//���[�h�ҏW
				if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
						JwsConstManager.JWS_COMPONENT_0118, DataCtrl.getInstance().getParamData().getStrMode())){
					txtHanbai.setBackground(Color.lightGray);
					txtHanbai.setBounds(ctrlx, labely, ctrlw-1, ctrlh);
					txtHanbai.setEditable(false);
				}else{
					txtHanbai.addFocusListener(new FocusCheck("�̔�����"));
				}
				this.add(txtHanbai);
			
			//�v�攄��
			labely = labely + mg_hgt;
			ItemLabel hlKeikakuUri = new ItemLabel();
			hlKeikakuUri.setBorder(new LineBorder(Color.BLACK, 1));
			hlKeikakuUri.setBounds(labelx, labely, labelw, labelh);
			hlKeikakuUri.setText("�@�@�@�v�攄��");
			this.add(hlKeikakuUri);
	
				//�v�攄��p�e�L�X�g�{�b�N�X����
				TextboxBase txtKeikakuUri = new TextboxBase();
				txtKeikakuUri.setBackground(Yellow);
				txtKeikakuUri.setBounds(ctrlx, labely, ctrlw, ctrlh);
				txtKeikakuUri.setText(PrototypeData.getStrKeikakuUri());
				//���[�h�ҏW
				if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
						JwsConstManager.JWS_COMPONENT_0119, DataCtrl.getInstance().getParamData().getStrMode())){
					txtKeikakuUri.setBackground(Color.lightGray);
					txtKeikakuUri.setBounds(ctrlx, labely, ctrlw-1, ctrlh);
					txtKeikakuUri.setEditable(false);
				}else{
					txtKeikakuUri.addFocusListener(new FocusCheck("�v�攄��"));
				}
				this.add(txtKeikakuUri);
			
			//�v�旘�v
			labely = labely + mg_hgt;
			ItemLabel hlKeikakuRie = new ItemLabel();
			hlKeikakuRie.setBorder(new LineBorder(Color.BLACK, 1));
			hlKeikakuRie.setBounds(labelx, labely, labelw, labelh);
			hlKeikakuRie.setText("�@�@�@�v�旘�v");
			this.add(hlKeikakuRie);
	
				//�v�旘�v�p�e�L�X�g�{�b�N�X����
				TextboxBase txtKeikakuRie = new TextboxBase();
				txtKeikakuRie.setBackground(Yellow);
				txtKeikakuRie.setBounds(ctrlx, labely, ctrlw, ctrlh);
				txtKeikakuRie.setText(PrototypeData.getStrKeikakuRie());
				//���[�h�ҏW
				if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
						JwsConstManager.JWS_COMPONENT_0120, DataCtrl.getInstance().getParamData().getStrMode())){
					txtKeikakuRie.setBackground(Color.lightGray);
					txtKeikakuRie.setBounds(ctrlx, labely, ctrlw-1, ctrlh);
					txtKeikakuRie.setEditable(false);
				}else{
					txtKeikakuRie.addFocusListener(new FocusCheck("�v�旘�v"));
				}
				this.add(txtKeikakuRie);
			
			//�̔��㔄��
			labely = labely + mg_hgt;
			ItemLabel hlHanbaiUri = new ItemLabel();
			hlHanbaiUri.setBorder(new LineBorder(Color.BLACK, 1));
			hlHanbaiUri.setBounds(labelx, labely, labelw, labelh);
			hlHanbaiUri.setText("�@�@�@�̔��㔄��");
			this.add(hlHanbaiUri);
	
				//�̔��㔄��p�e�L�X�g�{�b�N�X����
				TextboxBase txtHanbaiUri = new TextboxBase();
				txtHanbaiUri.setBackground(Yellow);
				txtHanbaiUri.setBounds(ctrlx, labely, ctrlw, ctrlh);
				txtHanbaiUri.setText(PrototypeData.getStrHanbaigoUri());
				//���[�h�ҏW
				if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
						JwsConstManager.JWS_COMPONENT_0121, DataCtrl.getInstance().getParamData().getStrMode())){
					txtHanbaiUri.setBackground(Color.lightGray);
					txtHanbaiUri.setBounds(ctrlx, labely, ctrlw-1, ctrlh);
					txtHanbaiUri.setEditable(false);
				}else{
					txtHanbaiUri.addFocusListener(new FocusCheck("�̔��㔄��"));
				}
				this.add(txtHanbaiUri);
			
			//�̔��㗘�v
			labely = labely + mg_hgt;
			ItemLabel hlHanbaiRie = new ItemLabel();
			hlHanbaiRie.setBorder(new LineBorder(Color.BLACK, 1));
			hlHanbaiRie.setBounds(labelx, labely, labelw, labelh);
			hlHanbaiRie.setText("�@�@�@�̔��㗘�v");
			this.add(hlHanbaiRie);
	
				//�̔��㗘�v�p�e�L�X�g�{�b�N�X����
				TextboxBase txtHanbaiRie = new TextboxBase();
				txtHanbaiRie.setBackground(Yellow);
				txtHanbaiRie.setBounds(ctrlx, labely, ctrlw, ctrlh);
				txtHanbaiRie.setText(PrototypeData.getStrHanbaigoRie());
				//���[�h�ҏW
				if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
						JwsConstManager.JWS_COMPONENT_0122, DataCtrl.getInstance().getParamData().getStrMode())){
					txtHanbaiRie.setBackground(Color.lightGray);
					txtHanbaiRie.setBounds(ctrlx, labely, ctrlw-1, ctrlh);
					txtHanbaiRie.setEditable(false);
				}else{
					txtHanbaiRie.addFocusListener(new FocusCheck("�̔��㗘�v"));
				}
				this.add(txtHanbaiRie);
			
			//���ڃ��x���ݒ�i���������j
			ItemLabel hlSogo = new ItemLabel();
			hlSogo.setBorder(new LineBorder(Color.BLACK, 1));
			hlSogo.setBounds(sogo_labelx, sogo_labely, sogo_labelw, sogo_labelh);
			hlSogo.setText("��������");
			this.add(hlSogo);
			
				//���������p�e�L�X�g�G���A����
				sogo_labely = sogo_labely + mg_hgt;
				TextAreaBase txaSogo = new TextAreaBase();
				txaSogo.setTABFocusControl();
				txaSogo.setText(PrototypeData.getStrSogo());
				//���[�h�ҏW
				if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
						JwsConstManager.JWS_COMPONENT_0123, DataCtrl.getInstance().getParamData().getStrMode())){
					txaSogo.setBackground(Color.lightGray);
					txaSogo.setEditable(false);
				}else{
					txaSogo.addFocusListener(new FocusCheck("��������"));
				}
				ScrollBase scSogo = new ScrollBase(txaSogo);
				scSogo.setBounds(sogo_labelx, sogo_labely, sogo_labelw+1, sogo_ctrlh);
				this.add(scSogo);
				
			//���p���́i�e�ʁj
				sogo_labely = sogo_labely + sogo_ctrlh;
				ItemIndicationLabel ilHankaku1 = new ItemIndicationLabel();
				ilHankaku1.setBounds(sogo_labelx, sogo_labely, 150, bikouh);
				ilHankaku1.setText("���p����");
				this.add(ilHankaku1);
			
			//���p���́i���萔�j
				sogo_labely = sogo_labely + mg_hgt;
				ItemIndicationLabel ilHankaku2 = new ItemIndicationLabel();
				ilHankaku2.setBounds(sogo_labelx, sogo_labely, 150, bikouh);
				ilHankaku2.setText("���p����");
				this.add(ilHankaku2);
				
			//���ڃ��x���ݒ�i���l�j
			sogo_labely = sogo_labely + mg_hgt;
			ItemLabel hlBikou = new ItemLabel();
			hlBikou.setBackground(Yellow);
			hlBikou.setBorder(new LineBorder(Color.BLACK, 1));
			hlBikou.setBounds(sogo_labelx, sogo_labely, bikouw, bikouh);
			this.add(hlBikou);
				
				ItemIndicationLabel ilBikou = new ItemIndicationLabel();
				ilBikou.setBounds(sogo_labelx+bikouw-1, sogo_labely, 150, bikouh);
				ilBikou.setText(" �̍��ڂ͕ҏW�\");
				this.add(ilBikou);
		
		} catch (ExceptionBase e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			
			//�G���[�ݒ�
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			//2011/04/12 QP@10181_No.26 TT T.Satoh Change Start -------------------------
			//ex.setStrErrmsg("����f�[�^��� ����\�B ���������������s���܂���");
			ex.setStrErrmsg("����f�[�^��� ��{��� ���������������s���܂���");
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
					
					//�����O���[�v
					if(komoku == "�����O���[�v"){
						String insert = checkNull(((JTextField)jc).getText());
						DataCtrl.getInstance().getTrialTblData().UpdGroupCd(
								DataCtrl.getInstance().getTrialTblData().checkNullInt(insert),
								DataCtrl.getInstance().getUserMstData().getDciUserid()
							);
					}
					
					//�����`�[��
					if(komoku == "�����`�[��"){
						String insert = checkNull(((JTextField)jc).getText());
						DataCtrl.getInstance().getTrialTblData().UpdTeamCd(
								DataCtrl.getInstance().getTrialTblData().checkNullInt(insert),
								DataCtrl.getInstance().getUserMstData().getDciUserid()
							);
					}
					
					//�ꊇ�\��
					if(komoku == "�ꊇ�\��"){
						String insert = null;
						int selectId = ((ComboBase)jc).getSelectedIndex();
						//�}���l�擾
						if(selectId > 0){
							insert = DataCtrl.getInstance().getLiteralDataIkatu().selectLiteralCd(selectId-1);
						}
						DataCtrl.getInstance().getTrialTblData().UpdIkkatsuHyouji(
								DataCtrl.getInstance().getTrialTblData().checkNullString(insert),
								DataCtrl.getInstance().getUserMstData().getDciUserid()
							);
					}
					
					//�W������
					if(komoku == "�W������"){
						String insert = null;
						int selectId = ((ComboBase)jc).getSelectedIndex();
						//�}���l�擾
						if(selectId > 0){
							insert = DataCtrl.getInstance().getLiteralDataZyanru().selectLiteralCd(selectId-1);
						}
						DataCtrl.getInstance().getTrialTblData().UpdJanru(
								DataCtrl.getInstance().getTrialTblData().checkNullString(insert),
								DataCtrl.getInstance().getUserMstData().getDciUserid()
							);
					}
					
					//���[�U
					if(komoku == "���[�U"){
						String insert = null;
						int selectId = ((ComboBase)jc).getSelectedIndex();
						//�}���l�擾
						if(selectId > 0){
							insert = DataCtrl.getInstance().getLiteralDataUser().selectLiteralCd(selectId-1);
						}
						DataCtrl.getInstance().getTrialTblData().UpdUser(
								DataCtrl.getInstance().getTrialTblData().checkNullString(insert),
								DataCtrl.getInstance().getUserMstData().getDciUserid()
							);
					}
					
					//��������
					if(komoku == "��������"){
						String insert = ((JTextField)jc).getText();
						DataCtrl.getInstance().getTrialTblData().UpdGenryo(
								DataCtrl.getInstance().getTrialTblData().checkNullString(insert),
								DataCtrl.getInstance().getUserMstData().getDciUserid()
							);
					}
					
					//�p�r
					if(komoku == "�p�r"){
						String insert = ((JTextField)jc).getText();
						DataCtrl.getInstance().getTrialTblData().UpdYouto(
								DataCtrl.getInstance().getTrialTblData().checkNullString(insert),
								DataCtrl.getInstance().getUserMstData().getDciUserid()
							);
					}
					
					//���i��
					if(komoku == "���i��"){
						String insert = null;
						int selectId = ((ComboBase)jc).getSelectedIndex();
						if(selectId > 0){
							insert = DataCtrl.getInstance().getLiteralDataKakaku().selectLiteralCd(selectId-1);
						}
						DataCtrl.getInstance().getTrialTblData().UpdKakakutai(
								DataCtrl.getInstance().getTrialTblData().checkNullString(insert),
								DataCtrl.getInstance().getUserMstData().getDciUserid()
							);
					}
					
					//���
					if(komoku == "���"){
						String insert = null;
						int selectId = ((ComboBase)jc).getSelectedIndex();
						if(selectId > 0){
							insert = DataCtrl.getInstance().getLiteralDataShubetu().selectLiteralCd(selectId-1);
						}
						DataCtrl.getInstance().getTrialTblData().UpdHinsyubetu(
								DataCtrl.getInstance().getTrialTblData().checkNullString(insert),
								DataCtrl.getInstance().getUserMstData().getDciUserid()
							);
					}
					
					//�����w��
					//����f�[�^��ʓ��ɂĒ�`
					
					//�S�����
					//����f�[�^��ʓ��ɂĒ�`
					
					//�S���H��
					//����f�[�^��ʓ��ɂĒ�`
					
					//�yQP@00342�z
//					//�S���c��
//					if(komoku == "�S���c��"){
//						String insert = ((JTextField)jc).getText();
//						DataCtrl.getInstance().getTrialTblData().UpdTantoEigyo(
//								DataCtrl.getInstance().getTrialTblData().checkNullString(insert),
//								DataCtrl.getInstance().getUserMstData().getDciUserid()
//							);
//					}
					
					//�������@
					if(komoku == "�������@"){
						String insert = null;
						int selectId = ((ComboBase)jc).getSelectedIndex();
						if(selectId > 0){
							insert = DataCtrl.getInstance().getLiteralDataSeizo().selectLiteralCd(selectId-1);
						}
						DataCtrl.getInstance().getTrialTblData().UpdSeizoHouho(
								DataCtrl.getInstance().getTrialTblData().checkNullString(insert),
								DataCtrl.getInstance().getUserMstData().getDciUserid()
							);
					}
					
					//�[�U���@
					if(komoku == "�[�U���@"){
						String insert = null;
						int selectId = ((ComboBase)jc).getSelectedIndex();
						if(selectId > 0){
							insert = DataCtrl.getInstance().getLiteralDataZyuten().selectLiteralCd(selectId-1);
						}
						DataCtrl.getInstance().getTrialTblData().UpdJutenHouho(
								DataCtrl.getInstance().getTrialTblData().checkNullString(insert),
								DataCtrl.getInstance().getUserMstData().getDciUserid()
							);
					}
					
					//�E�ە��@
					if(komoku == "�E�ە��@"){
						String insert = ((JTextField)jc).getText();
						DataCtrl.getInstance().getTrialTblData().UpdSakkinHouho(
								DataCtrl.getInstance().getTrialTblData().checkNullString(insert),
								DataCtrl.getInstance().getUserMstData().getDciUserid()
							);
					}
					
					//�e����
					if(komoku == "�e����"){
						String insert = ((JTextField)jc).getText();
						DataCtrl.getInstance().getTrialTblData().UpdYoukiHouzai(
								DataCtrl.getInstance().getTrialTblData().checkNullString(insert),
								DataCtrl.getInstance().getUserMstData().getDciUserid()
							);
					}
					
					//�e��
					if(komoku == "�e��"){
						String insert = ((JTextField)jc).getText();
						DataCtrl.getInstance().getTrialTblData().UpdYouryo(
								DataCtrl.getInstance().getTrialTblData().checkNullString(insert),
								DataCtrl.getInstance().getUserMstData().getDciUserid()
							);
						
						

			    		//���������f�[�^�@�e�� �X�V
						ArrayList aryCostMaterial = DataCtrl.getInstance().getTrialTblData().SearchGenkaGenryoData(0);
						for ( int i=0; i<aryCostMaterial.size(); i++ ) {
							CostMaterialData costMaterial = (CostMaterialData)aryCostMaterial.get(i);

				    		DataCtrl.getInstance().getTrialTblData().UpdGenkaValue(
				    				costMaterial.getIntShisakuSeq(),
				    				DataCtrl.getInstance().getTrialTblData().checkNullString(insert),
				    				DataCtrl.getInstance().getUserMstData().getDciUserid(),
				    				JwsConstManager.JWS_COMPONENT_0140
				    			);
				    		
//2011/05/30�@�yQP@10181_No.42_49_72�z�H���p�^�[������@TT.NISHIGAWA�@start
				    		//�[�U�ʂ��v�Z
							String keisan = DataCtrl.getInstance().getTrialTblData().KeisanZyutenType1(costMaterial.getIntShisakuSeq());
							DataCtrl.getInstance().getTrialTblData().UpdGenkaValue(
									costMaterial.getIntShisakuSeq(),
				    				DataCtrl.getInstance().getTrialTblData().checkNullString(keisan),
				    				DataCtrl.getInstance().getUserMstData().getDciUserid(),
				    				JwsConstManager.JWS_COMPONENT_0134);
				    		
				    		//�����[�U�ʂ��v�Z
							String keisan1 = DataCtrl.getInstance().getTrialTblData().KeisanSuisoZyuten(costMaterial.getIntShisakuSeq());
							DataCtrl.getInstance().getTrialTblData().UpdGenkaValue(
									costMaterial.getIntShisakuSeq(),
				    				DataCtrl.getInstance().getTrialTblData().checkNullString(keisan1),
				    				DataCtrl.getInstance().getUserMstData().getDciUserid(),
				    				JwsConstManager.JWS_COMPONENT_0134);
							
							//�����[�U�ʂ��v�Z
							String keisan2 = DataCtrl.getInstance().getTrialTblData().KeisanYusoZyuten(costMaterial.getIntShisakuSeq());
							DataCtrl.getInstance().getTrialTblData().UpdGenkaValue(
									costMaterial.getIntShisakuSeq(),
				    				DataCtrl.getInstance().getTrialTblData().checkNullString(keisan2),
				    				DataCtrl.getInstance().getUserMstData().getDciUserid(),
				    				JwsConstManager.JWS_COMPONENT_0135);
//2011/05/30�@�yQP@10181_No.42_49_72�z�H���p�^�[������@TT.NISHIGAWA�@end
							
						}
					}
					
					//�e�ʒP��
					if(komoku == "�e�ʒP��"){
						
//2011/05/30�@�yQP@10181_No.42_49_72�z�H���p�^�[������@TT.NISHIGAWA�@start
//						String insert = null;
//						int selectId = ((ComboBase)jc).getSelectedIndex();
//						if(selectId > 0){
//							insert = DataCtrl.getInstance().getLiteralDataTani().selectLiteralCd(selectId-1);
//						}
//						DataCtrl.getInstance().getTrialTblData().UpdYouryoTani(
//								DataCtrl.getInstance().getTrialTblData().checkNullString(insert),
//								DataCtrl.getInstance().getUserMstData().getDciUserid()
//							);
//2011/05/30�@�yQP@10181_No.42_49_72�z�H���p�^�[������@TT.NISHIGAWA�@end
						
					}
					
					//���萔
					if(komoku == "���萔"){
						String insert = checkNull(((JTextField)jc).getText());
						DataCtrl.getInstance().getTrialTblData().UpdIriSu(
								DataCtrl.getInstance().getTrialTblData().checkNullString(insert),
								DataCtrl.getInstance().getUserMstData().getDciUserid()
							);

			    		//���������f�[�^�@���� �X�V
						ArrayList aryCostMaterial = DataCtrl.getInstance().getTrialTblData().SearchGenkaGenryoData(0);
						for ( int i=0; i<aryCostMaterial.size(); i++ ) {
							CostMaterialData costMaterial = (CostMaterialData)aryCostMaterial.get(i);
							
				    		DataCtrl.getInstance().getTrialTblData().UpdGenkaValue(
				    				costMaterial.getIntShisakuSeq(),
				    				DataCtrl.getInstance().getTrialTblData().checkNullString(insert),
				    				DataCtrl.getInstance().getUserMstData().getDciUserid(),
				    				JwsConstManager.JWS_COMPONENT_0141
				    			);
						}
						
					}
					
					//�׎p
					if(komoku == "�׎p"){
						String insert = ((JTextField)jc).getText();
						DataCtrl.getInstance().getTrialTblData().UpdNisugata(
								DataCtrl.getInstance().getTrialTblData().checkNullString(insert),
								DataCtrl.getInstance().getUserMstData().getDciUserid()
							);
					}
					
					//�戵���x
					if(komoku == "�戵���x"){
						String insert = null;
						int selectId = ((ComboBase)jc).getSelectedIndex();
						if(selectId > 0){
							insert = DataCtrl.getInstance().getLiteralDataOndo().selectLiteralCd(selectId-1);
						}
						DataCtrl.getInstance().getTrialTblData().UpdOndo(
								DataCtrl.getInstance().getTrialTblData().checkNullString(insert),
								DataCtrl.getInstance().getUserMstData().getDciUserid()
							);
					}
					
					//�ܖ�����
					if(komoku == "�ܖ�����"){
						String insert = ((JTextField)jc).getText();
						DataCtrl.getInstance().getTrialTblData().UpdSyoumikigen(
								DataCtrl.getInstance().getTrialTblData().checkNullString(insert),
								DataCtrl.getInstance().getUserMstData().getDciUserid()
							);
					}
					
					//������]
					if(komoku == "������]"){
						String insert = checkNull(((JTextField)jc).getText());
						DataCtrl.getInstance().getTrialTblData().UpdGenkaKibo(
								DataCtrl.getInstance().getTrialTblData().checkNullString(insert),
								DataCtrl.getInstance().getUserMstData().getDciUserid()
							);
					}
					
					//������]
					if(komoku == "������]"){
						String insert = checkNull(((JTextField)jc).getText());
						DataCtrl.getInstance().getTrialTblData().UpdBaikaKibo(
								DataCtrl.getInstance().getTrialTblData().checkNullString(insert),
								DataCtrl.getInstance().getUserMstData().getDciUserid()
							);

			    		//���������f�[�^�@���� �X�V
						ArrayList aryCostMaterial = DataCtrl.getInstance().getTrialTblData().SearchGenkaGenryoData(0);
						for ( int i=0; i<aryCostMaterial.size(); i++ ) {
							CostMaterialData costMaterial = (CostMaterialData)aryCostMaterial.get(i);

				    		DataCtrl.getInstance().getTrialTblData().UpdGenkaValue(
				    				costMaterial.getIntShisakuSeq(),
				    				DataCtrl.getInstance().getTrialTblData().checkNullString(insert),
				    				DataCtrl.getInstance().getUserMstData().getDciUserid(),
				    				JwsConstManager.JWS_COMPONENT_0130
				    			);
						}
						
					}
					
					//�z�蕨��
					if(komoku == "�z�蕨��"){
						String insert = checkNull(((JTextField)jc).getText());
						DataCtrl.getInstance().getTrialTblData().UpdSouteiButuryo(
								DataCtrl.getInstance().getTrialTblData().checkNullString(insert),
								DataCtrl.getInstance().getUserMstData().getDciUserid()
							);
					}
					
					//�̔�����
					if(komoku == "�̔�����"){
						String insert = checkNull(((JTextField)jc).getText());
						DataCtrl.getInstance().getTrialTblData().UpdHanbaiJiki(
								DataCtrl.getInstance().getTrialTblData().checkNullString(insert),
								DataCtrl.getInstance().getUserMstData().getDciUserid()
							);
					}
					
					//�v�攄��
					if(komoku == "�v�攄��"){
						String insert = checkNull(((JTextField)jc).getText());
						DataCtrl.getInstance().getTrialTblData().UpdKeikakuUriage(
								DataCtrl.getInstance().getTrialTblData().checkNullString(insert),
								DataCtrl.getInstance().getUserMstData().getDciUserid()
							);
					}
					
					//�v�旘�v
					if(komoku == "�v�旘�v"){
						String insert = checkNull(((JTextField)jc).getText());
						DataCtrl.getInstance().getTrialTblData().UpdKeikakuRieki(
								DataCtrl.getInstance().getTrialTblData().checkNullString(insert),
								DataCtrl.getInstance().getUserMstData().getDciUserid()
							);
					}
					
					//�̔��㔄��
					if(komoku == "�̔��㔄��"){
						String insert = checkNull(((JTextField)jc).getText());
						DataCtrl.getInstance().getTrialTblData().UpdHanbaigoUriage(
								DataCtrl.getInstance().getTrialTblData().checkNullString(insert),
								DataCtrl.getInstance().getUserMstData().getDciUserid()
							);
					}
					
					//�̔��㗘�v
					if(komoku == "�̔��㗘�v"){
						String insert = checkNull(((JTextField)jc).getText());
						DataCtrl.getInstance().getTrialTblData().UpdHanbaigoRieki(
								DataCtrl.getInstance().getTrialTblData().checkNullString(insert),
								DataCtrl.getInstance().getUserMstData().getDciUserid()
							);
					}
					
					//��������
					if(komoku == "��������"){
						String insert = checkNull(((JTextArea)jc).getText());
						DataCtrl.getInstance().getTrialTblData().UpdSogoMemo(
								DataCtrl.getInstance().getTrialTblData().checkNullString(insert),
								DataCtrl.getInstance().getUserMstData().getDciUserid()
							);
					}
				}
			}catch(ExceptionBase eb){
				
			}catch(Exception ex){
				ex.printStackTrace();
				
			}finally{
				//�e�X�g�\��
				//DataCtrl.getInstance().getTrialTblData().dispPrototype();
			}
	    }
		public void focusGained( FocusEvent e ){
	    }
	}

	/**
	 * �yJW620�z ���MXML�f�[�^�쐬
	 * @param strKaishaCd : ��ЃR�[�h
	 */
	private void conJW620(String strKaishaCd) throws ExceptionBase{
		try{
			
			//�@���M�p�����[�^�i�[
			String strUser = DataCtrl.getInstance().getParamData().getDciUser().toString();
			String strGamenId = DataCtrl.getInstance().getParamData().getStrMode();
//			if(strGamenId.equals(JwsConstManager.JWS_MODE_0000)){
//				strGamenId = JwsConstManager.JWS_MODE_0001;
//			}
			
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
//			xmlJW620.dispXml();
			xcon = new XmlConnection(xmlJW620);
			xcon.setStrAddress(DataCtrl.getInstance().getJnlpConnect().getStrAddressAjax());
			xcon.XmlSend();
			//�@XML��M
			xmlJW620 = xcon.getXdocRes();
//			xmlJW620.dispXml();
			
			//�@�e�X�gXML�f�[�^
			//xmlJW620 = new XmlData(new File("src/main/JW620.xml"));
			
			//�����f�[�^
			DataCtrl.getInstance().getBushoData().setBushoData(xmlJW620);
			//DataCtrl.getInstance().getBushoData().dispBushoData();

		}catch(ExceptionBase ex){
			throw ex;
		}catch(Exception e){
			
			//�G���[�ݒ�
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			ex.setStrErrmsg("���MXML�f�[�^�쐬���������s���܂���");
			ex.setStrErrShori("Trial3Panel:conJW620");
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getMessage());
			throw ex;
		}finally{
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
	
//2011/05/30�@�yQP@10181_No.42_49_72�z�H���p�^�[������@TT.NISHIGAWA�@start
	/**
	 * ���e�����f�[�^���R���{�{�b�N�X�֐ݒ�i�e�ʒP�ʃR���{��p�j
	 * @param comb : �ݒ�ΏۃR���{�{�b�N�X
	 * @param literalData : �ݒ�Ώۃ��e�����f�[�^
	 * @param strLiteralCd : �\���Ώۃ��e�����R�[�h
	 * @param intType : �\���p���e�����R�[�h�̃^�C�v
	 *  (0:�R�[�h, 1:���l)
	 * @throws ExceptionBase 
	 */
	public void setLiteralCmbYoryo(JComboBox comb, LiteralData literalData, String strLiteralCd, int intType) throws ExceptionBase {
		int i;		
		String literalCd = "";
		String literalNm = "";
		String literalValue1 = "";
		Object viewLiteralNm = "";

		String ptKotei = DataCtrl.getInstance().getTrialTblData().SearchShisakuhinData().getStrPt_kotei();
		
		try {
			//�R���{�{�b�N�X�̑S���ڂ̍폜
			comb.removeAllItems();
	
			//�^�C�v��0��1�̏ꍇ�A�󍀖ڂ�ǉ�����
			comb.addItem("");
			
			
			//�H���p�^�[�����u�󔒁v�̏ꍇ
			if(ptKotei == null || ptKotei.length() == 0){
				
			}
			//�H���p�^�[�����u�󔒁v�łȂ��ꍇ
			else{
				//�H���p�^�[����Value1�擾
				String ptValue = DataCtrl.getInstance().getLiteralDataKoteiPtn().selectLiteralVal1(ptKotei);
				
				//�\�����ɉ����ăR���{�{�b�N�X�ɒl��ǉ�
				for ( i=0; i<literalData.getAryLiteralCd().size(); i++ ) {
					
					//Value1
					literalValue1 = literalData.getAryValue1().get(i).toString();
					if(literalValue1.equals("0")){
						
					}
					else{
						//�H���p�^�[�����u�������P�t�^�C�v�v�̏ꍇ
						if(ptValue.equals(JwsConstManager.JWS_KOTEITYPE_1)){
							
							if( literalValue1.equals("1") || literalValue1.equals("2") ){
								
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
								}
							}
						}
						//�H���p�^�[�����u�������Q�t�^�C�v�v�̏ꍇ
						else if(ptValue.equals(JwsConstManager.JWS_KOTEITYPE_2)){
							
							//QP@20505 No.2 2012/09/05 TT H.SHIMA MOD -Start
							//if( literalValue1.equals("1") ){
							if( literalValue1.equals("1") || literalValue1.equals("2") ){
							//QP@20505 No.2 2012/09/05 TT H.SHIMA MOD -End
								
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
								}
							}
						}
						//�H���p�^�[�����u���̑��E���H�^�C�v�v�̏ꍇ
						else if(ptValue.equals(JwsConstManager.JWS_KOTEITYPE_3)){
							
							if( literalValue1.equals("2") ){
								
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
								}
							}
						}
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
//2011/05/30�@�yQP@10181_No.42_49_72�z�H���p�^�[������@TT.NISHIGAWA�@end

	/**
	 * ��Ѓf�[�^���R���{�{�b�N�X�֐ݒ�
	 * @param comb : �ݒ�ΏۃR���{�{�b�N�X
	 * @throws ExceptionBase 
	 */
	private void setKaishaCmb(JComboBox comb) throws ExceptionBase {
		int i;
		
		try {
			//��Ѓf�[�^
			KaishaData kaishaData = DataCtrl.getInstance().getKaishaData();
			//����i.��ЃR�[�h�̎擾
			String protKaishaCd = Integer.toString(PrototypeData.getIntKaishacd());
			
			String kaishaCd = "";
			String kaishaNm = "";
			Object viewKaishaNm = "";
			
			//�R���{�{�b�N�X�̑S���ڂ̍폜
			comb.removeAllItems();
			
			//�\�����ɉ����ăR���{�{�b�N�X�ɒl��ǉ�
			for ( i=0; i<kaishaData.getArtKaishaCd().size(); i++ ) {
				//��ЃR�[�h
				kaishaCd = kaishaData.getArtKaishaCd().get(i).toString();
				//��Ж�
				kaishaNm = kaishaData.getAryKaishaNm().get(i).toString(); 
				
				//�R���{�{�b�N�X�֒ǉ�
				comb.addItem(kaishaNm);
				
				//�\�����ڂ̌��o
				if ( kaishaCd.equals(protKaishaCd) ) {
					viewKaishaNm = kaishaNm; 
				}
			}
			
			//�Ώۍ��ڂ̕\��
			if ( viewKaishaNm != "" ) {
				comb.setSelectedItem(viewKaishaNm);
			} else {
				comb.setSelectedIndex(0);
			}
		
		}catch(Exception e){
			
			//�G���[�ݒ�
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			ex.setStrErrmsg("���MXML�f�[�^�쐬���������s���܂���");
			ex.setStrErrShori("Trial3Panel:conJW620");
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getMessage());
			throw ex;
		}finally{
		}
	}


	/**
	 * �����f�[�^���R���{�{�b�N�X�֐ݒ�
	 * @param comb : �ݒ�ΏۃR���{�{�b�N�X
	 * @param strBushoCd : �\���ΏۃR�[�h�R�[�h
	 * @throws ExceptionBase 
	 */
	private void setBushoCmb(JComboBox comb) throws ExceptionBase {
		int i;
		
		try {
			//�����f�[�^
			BushoData bushoData = DataCtrl.getInstance().getBushoData();
			//����i.�H��R�[�h�̎擾
			String protBushoCd = Integer.toString(PrototypeData.getIntKojoco());
			
			String bushoCd = "";
			String bushoNm = "";
			Object viewBushoNm = "";
			
			//�R���{�{�b�N�X�̑S���ڂ̍폜
			comb.removeAllItems();
			
			//�\�����ɉ����ăR���{�{�b�N�X�ɒl��ǉ�
			for ( i=0; i<bushoData.getArtBushoCd().size(); i++ ) {
				//��ЃR�[�h
				bushoCd = bushoData.getArtBushoCd().get(i).toString();
				//��Ж�
				bushoNm = bushoData.getAryBushoNm().get(i).toString(); 
				
				//�R���{�{�b�N�X�֒ǉ�
				comb.addItem(bushoNm);
				
				//�\�����ڂ̌��o
				if ( bushoCd.equals(protBushoCd) ) {
					viewBushoNm = bushoNm; 
				}
			}
			
			//�Ώۍ��ڂ̕\��
			if ( viewBushoNm != "" ) {
				comb.setSelectedItem(viewBushoNm);
			} else {
				comb.setSelectedIndex(0);
			}

		}catch(Exception e){
			e.printStackTrace();
			//�G���[�ݒ�
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			ex.setStrErrmsg("���MXML�f�[�^�쐬���������s���܂���");
			ex.setStrErrShori("Trial3Panel:conJW620");
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getMessage());
			throw ex;
		}finally{
		}
	}
	
	/**
	 * �I����ЃR�[�h�̎擾
	 * @return �I����ЃR�[�h 
	 * @throws ExceptionBase 
	 */
	private String getSelectKaishaCd() throws ExceptionBase {
		int i;
		String intRetKaishaCd = "";
		String kaishaCd = "";
		String kaishaNm = "";
		
		try {
			//��Ѓf�[�^
			KaishaData kaishaData = DataCtrl.getInstance().getKaishaData();
			
			//�\�����ɉ����ăR���{�{�b�N�X�ɒl��ǉ�
			for ( i=0; i<kaishaData.getArtKaishaCd().size(); i++ ) {
				//��ЃR�[�h
				kaishaCd = kaishaData.getArtKaishaCd().get(i).toString();
				//��Ж�
				kaishaNm = kaishaData.getAryKaishaNm().get(i).toString(); 
	
				//�I����ЃR�[�h�̌��o
				if ( kaishaNm.equals(cmbKaisha.getSelectedItem().toString()) ) {
					intRetKaishaCd = kaishaCd;
				}
			}

		}catch(Exception e){
			
			//�G���[�ݒ�
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			ex.setStrErrmsg("���MXML�f�[�^�쐬���������s���܂���");
			ex.setStrErrShori("Trial3Panel:conJW620");
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getMessage());
			throw ex;
		}finally{
		}
		
		return intRetKaishaCd;
	}
	
	/**
	 * �C�x���g�����̎擾
	 * @return ActionListener�N���X
	 */
	private ActionListener getActionEvent() {
		return new ActionListener() {
			//�C�x���g����
			public void actionPerformed(ActionEvent e) {
				String eventNm = e.getActionCommand();
				
				try {
					//��ЃR���{�{�b�N�X ActionEvent
					if ( eventNm == kaishaCommand ) {
						//��ЃR�[�h�̎擾
						String selKaishaCd = getSelectKaishaCd();
						//JW620 : �����f�[�^�̍Ď擾
						try {
							conJW620(selKaishaCd);
						} catch (ExceptionBase ex) {
							ex.printStackTrace();
						}
						//�H��R���{�{�b�N�X�̍Đݒ�
						setBushoCmb(cmbKojo);
					}
					
					//�yQP@00342�z
					//�c�ƒS������ ActionEvent
					if ( eventNm == "btnSerchEigyo" ) {
						
						//���앪�̓f�[�^�m�F�T�u��ʁ@�ĕ\��
						et.setVisible(true);
						et.getEigyoTantoSerchPanel().init();
						
						//�A�N�V�����C�x���g�ǉ�
						(et.getEigyoTantoSerchPanel().getButton())[1].addActionListener(getActionEvent());
						(et.getEigyoTantoSerchPanel().getButton())[1].setActionCommand("sentaku_btn_click");
					}
					//�yQP@00342�z
					//�c�ƒS������ �T�u��ʓ��̑I���{�^���N���b�N
					if ( eventNm == "sentaku_btn_click") {
						
						//�c�ƒS�������e�[�u���擾
						TableBase tb = et.getEigyoTantoSerchPanel().getEigyoTantoSearchTable().getMainTable();
						
						//�I������Ă���ꍇ
						if(tb.getSelectedRow() >= 0){
							
							//�I���s�擾
							int selected = tb.getSelectedRow();
							
							//�I������Ă���S���c�ƃf�[�^�擾
							EigyoTantoData EigyoTantoData = (EigyoTantoData)et.getEigyoTantoSerchPanel().getEigyoTantoAry().get(selected);
							
							//�S���c�Ƒ}��
							DataCtrl.getInstance().getTrialTblData().UpdTantoEigyo(
									DataCtrl.getInstance().getTrialTblData().checkNullString(EigyoTantoData.getId_user()),
									DataCtrl.getInstance().getUserMstData().getDciUserid()
								);
							
							//�S���c�Ɩ��\��
							txtEigyo.setText(EigyoTantoData.getNm_user());
							
							//�S���c�ƌ�����ʂ��\��
							et.setVisible(false);
							
						}
					}

				} catch (ExceptionBase eb) {
					DataCtrl.getInstance().PrintMessage(eb);
				} catch (Exception ec) {
					//�G���[�ݒ�
					ex = new ExceptionBase();
					ex.setStrErrCd("");
					//2011/04/12 QP@10181_No.26 TT T.Satoh Change Start -------------------------
					//ex.setStrErrmsg("����\�B �C�x���g���������s���܂���");
					ex.setStrErrmsg("��{��� �C�x���g���������s���܂���");
					//2011/04/12 QP@10181_No.26 TT T.Satoh Change End ---------------------------
					ex.setStrErrShori(this.getClass().getName());
					ex.setStrMsgNo("");
					ex.setStrSystemMsg(ec.getMessage());
					DataCtrl.getInstance().PrintMessage(ex);
				} finally {
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

		String strYoryo = "";
		String strIrisu = "";
		String strBaika = "";
		
		try {

			//����i�f�[�^�@�X�V
			//�e��
			strYoryo = this.cmbYoryo.getEditor().getItem().toString();
			DataCtrl.getInstance().getTrialTblData().UpdYouryo(
					DataCtrl.getInstance().getTrialTblData().checkNullString(strYoryo),
					DataCtrl.getInstance().getUserMstData().getDciUserid()
				);

			//���萔
			strIrisu = this.txtIrisu.getText();
			DataCtrl.getInstance().getTrialTblData().UpdIriSu(
					DataCtrl.getInstance().getTrialTblData().checkNullString(strIrisu),
					DataCtrl.getInstance().getUserMstData().getDciUserid()
				);
			
			//����
			strBaika = this.txtBaikibou.getText();
			DataCtrl.getInstance().getTrialTblData().UpdBaikaKibo(
					DataCtrl.getInstance().getTrialTblData().checkNullString(strBaika),
					DataCtrl.getInstance().getUserMstData().getDciUserid()
				);

    		//���������f�[�^�@�X�V
			ArrayList aryCostMaterial = DataCtrl.getInstance().getTrialTblData().SearchGenkaGenryoData(0);
			
			for ( int i=0; i<aryCostMaterial.size(); i++ ) {
				CostMaterialData costMaterial = (CostMaterialData)aryCostMaterial.get(i);
			
				//�e��
	    		DataCtrl.getInstance().getTrialTblData().UpdGenkaValue(
	    				costMaterial.getIntShisakuSeq(),
	    				DataCtrl.getInstance().getTrialTblData().checkNullString(strYoryo),
	    				DataCtrl.getInstance().getUserMstData().getDciUserid(),
	    				JwsConstManager.JWS_COMPONENT_0140
	    			);
				
				//���萔
	    		DataCtrl.getInstance().getTrialTblData().UpdGenkaValue(
	    				costMaterial.getIntShisakuSeq(),
	    				DataCtrl.getInstance().getTrialTblData().checkNullString(strIrisu),
	    				DataCtrl.getInstance().getUserMstData().getDciUserid(),
	    				JwsConstManager.JWS_COMPONENT_0141
	    			);
	    		
	    		//����
	    		DataCtrl.getInstance().getTrialTblData().UpdGenkaValue(
	    				costMaterial.getIntShisakuSeq(),
	    				DataCtrl.getInstance().getTrialTblData().checkNullString(strBaika),
	    				DataCtrl.getInstance().getUserMstData().getDciUserid(),
	    				JwsConstManager.JWS_COMPONENT_0130
	    			);
	    		
			}
			
		} catch (Exception ec) {
			//�G���[�ݒ�
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			//2011/04/12 QP@10181_No.26 TT T.Satoh Change Start -------------------------
			//ex.setStrErrmsg("����\�B �������Z�X�V���������s���܂���");
			ex.setStrErrmsg("��{��� �������Z�X�V���������s���܂���");
			//2011/04/12 QP@10181_No.26 TT T.Satoh Change End ---------------------------
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(ec.getMessage());
			throw ex;
			
		} finally {
			//�ϐ��̍폜
			strYoryo = null;
			strIrisu = null;
			strBaika = null;
			
		}
		
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
//	private String checkNull(int val){
//		String ret = "";
//		if(val >= 0){
//			ret = Integer.toString(val);
//		}
//		return ret;
//	}
	
	/************************************************************************************
	 * 
	 * �e�L�X�g�O���[�v�Q�b�^�[&�Z�b�^�[
	 * @throws ExceptionBase
	 * 
	 ************************************************************************************/
	public TextboxBase getTxtGroup() {
		return txtGroup;
	}
	public TextboxBase getTxtTeam() {
		return txtTeam;
	}
	
	/************************************************************************************
	 * 
	 * �S����ЃR���{�{�b�N�X�Q�b�^�[
	 * @throws ExceptionBase
	 * 
	 ************************************************************************************/
	public ComboBase getCmbKaisha() {
		return cmbKaisha;
	}
	
	/************************************************************************************
	 * 
	 * �S���H��R���{�{�b�N�X�Q�b�^�[
	 * @throws ExceptionBase
	 * 
	 ************************************************************************************/
	public ComboBase getCmbKojo() {
		return cmbKojo;
	}
	
	/************************************************************************************
	 * 
	 * �����w��R���{�{�b�N�X�Q�b�^�[
	 * @throws ExceptionBase
	 * 
	 ************************************************************************************/
	public ComboBase getCmbShosu() {
		return cmbShosu;
	}
	
	/************************************************************************************
	 * 
	 * �����w��R���{�{�b�N�X�Z�b�^�[
	 * @throws ExceptionBase
	 * 
	 ************************************************************************************/
	public void setCmbShosu(ComboBase cmbShosu) {
		this.cmbShosu = cmbShosu;
	}

//add start -------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.33,34
	public InputComboBase getCmbYoryo() {
		return cmbYoryo;
	}
	public void setCmbYoryo(InputComboBase cmbYoryo) {
		this.cmbYoryo = cmbYoryo;
	}
	public ComboBase getCmbtani() {
		return cmbtani;
	}
	public void setCmbtani(ComboBase cmbtani) {
		this.cmbtani = cmbtani;
	}
//add end   -------------------------------------------------------------------------------

	
	
}
