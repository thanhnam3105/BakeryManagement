package jp.co.blueflag.shisaquick.jws.panel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.DefaultCellEditor;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableColumn;

import jp.co.blueflag.shisaquick.jws.base.CostMaterialData;
import jp.co.blueflag.shisaquick.jws.base.ShisanData;
import jp.co.blueflag.shisaquick.jws.base.TrialData;
import jp.co.blueflag.shisaquick.jws.base.XmlData;
import jp.co.blueflag.shisaquick.jws.celleditor.MiddleCellEditor;
import jp.co.blueflag.shisaquick.jws.celleditor.TextFieldCellEditor;
import jp.co.blueflag.shisaquick.jws.cellrenderer.CheckBoxCellRenderer;
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
import jp.co.blueflag.shisaquick.jws.label.ItemIndicationLabel;
import jp.co.blueflag.shisaquick.jws.label.ItemLabel;
import jp.co.blueflag.shisaquick.jws.manager.DownloadPathData;
import jp.co.blueflag.shisaquick.jws.manager.UrlConnection;
import jp.co.blueflag.shisaquick.jws.manager.XmlConnection;
import jp.co.blueflag.shisaquick.jws.textbox.HankakuTextbox;
import jp.co.blueflag.shisaquick.jws.textbox.NumelicTextbox;

/*****************************************************************************************
 * 
 *   �������Z(����\�D)�p�l���N���X
 *   @author TT katayama
 *   
 *****************************************************************************************/
public class Trial5Panel extends PanelBase {
	
	private static final long serialVersionUID = 1L;

	//�萔�l
	private static final String CLICK_SHISAN_RIREKI = "click_shisanRireki";

	//�f�[�^&�ʐM�I�u�W�F�N�g
	private XmlConnection xcon;
	private XmlData xmlJW840;						//���Z�m��T���v��No����(�����\��)
	private XmlData xmlJW850;						//���Z�m�藚���Q��
	private XmlData xmlJW860;						//�������Z�o�^
	private XmlData xmlJ010;							//���Z����p�����̔�
	private XmlData xmlJW830;						//�������Z�\�o��

	//��ʓ��R���|�[�l���g
	private TableBase table;							//�e�[�u��
	private ComboBase cmbShisanKakutei;			//���Z�m��T���v��NO�R���{�{�b�N�X
	private ButtonBase btnShisanHyo;				//�������Z�\�o�̓{�^��
	private ButtonBase btnShisanToroku;			//�������Z�o�^�{�^��
	private ButtonBase btnShisanRireki;			//���Z�����Q�ƃ{�^��

	//2011/04/20 QP@10181_No.41 TT T.Satoh Add Start -------------------------
	//�X�N���[���o�[
	private JScrollPane scroll;
	//2011/04/20 QP@10181_No.41 TT T.Satoh Add End ---------------------------
	
	//�G���[����
	private ExceptionBase ex;
		
	/************************************************************************************
	 * 
	 * �������Z(����\�D)�p�l���N���X�@�R���X�g���N�^
	 *   @throws ExceptionBase 
	 *   @author TT katayama
	 *   
	 ************************************************************************************/
	public Trial5Panel() throws ExceptionBase {
		
		super();
		
		try {
			//��ʕ\��
			this.panelDisp();
			
			//���Z�m��T���v��No��������
		
			//�R���{�{�b�N�X������������Ă��Ȃ��ꍇ�A���������������s��
			if ( DataCtrl.getInstance().getShisanRirekiKanriData().getAryShisanKakuteiData().size() == 0 ) {
				
				//��������
				this.con_JW840();	
				
			}
			
			//���Z�m��T���v��No�R���{�{�b�N�X��ݒ�
			this.setShisanKakuteiCmb();
			
		} catch (ExceptionBase e) {
			throw e;
			
		} catch (Exception e) {
			//�G���[�ݒ�
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			//2011/04/13 QP@10181_No.26 TT T.Satoh Change Start -------------------------
			//ex.setStrErrmsg("�������Z�D �R���X�g���N�^���������s���܂���");
			ex.setStrErrmsg("�������Z �R���X�g���N�^���������s���܂���");
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
	 * �������Z(����\�D)�p�l���N���X�@��ʕ\��
	 *   @throws ExceptionBase 
	 *   @author TT katayama
	 *   
	 ************************************************************************************/
	private void panelDisp() throws ExceptionBase{

		try {
			this.setLayout(null);
			this.setBackground(Color.WHITE);
			
			//����f�[�^���擾
			Object[] aryTrialData = (DataCtrl.getInstance().getTrialTblData().SearchShisakuRetuData(0).toArray());
			
			//�R���|�[�l���g����������
			this.initComponent(aryTrialData);
			
			//�e�[�u������������
			this.initTable(aryTrialData);
			
			//2011/06/07 QP@10181_No.41 TT T.Satoh Add Start -------------------------
			//�������Z�̉��X�N���[���o�[�̍ő�l���擾
			int hGenkaBarMax = this.getScroll().getHorizontalScrollBar().getMaximum();
			
			//�������Z�̉��X�N���[���o�[�̈ʒu��ݒ�
			this.getScroll().getHorizontalScrollBar().setValue(hGenkaBarMax);
			//2011/06/07 QP@10181_No.41 TT T.Satoh Add End ---------------------------

		} catch (ExceptionBase e) {
			throw e;
			
		} catch (Exception e) {
			//�G���[�ݒ�
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			//2011/04/13 QP@10181_No.26 TT T.Satoh Change Start -------------------------
			//ex.setStrErrmsg("�������Z�D ��ʕ\�����������s���܂���");
			ex.setStrErrmsg("�������Z ��ʕ\�����������s���܂���");
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
	 * �R���|�[�l���g�����������i�e�[�u���������j
	 *   @param aryTrialData : ����f�[�^
	 *   @throws ExceptionBase 
	 *   @author TT katayama
	 *   
	 ************************************************************************************/
	public void initComponent(Object[] aryTrialData) throws ExceptionBase {
		
		try {  
			
			int hll_x, hlr_x;				//�w�b�_�[X���W
			int hll_w, hlr_w;				//�w�b�_�[��
			int hlr_h = 16;				//�����w�b�_�[����
			
			hll_x = 15;						//�����w�b�_�[X���W
			hll_w = 80;						//�����w�b�_�[��
			hlr_x = hll_x + hll_w - 1;	//�E���w�b�_�[X���W
			hlr_w = 180;					//�E���w�b�_�[��
			
			//�v�Z���ڗp������ : (�v�Z)�@�ƕ\������
			String strKeisan = " (" + JwsConstManager.JWS_MARK_0005 + ")";
									
			//--------------------�@�@�@----------------------------
			
			//���ڃ��x���ݒ�i����:�@�j
			ItemLabel hl = new ItemLabel();
			hl.setBorder(new LineBorder(Color.BLACK, 1));
			hl.setHorizontalAlignment(SwingConstants.CENTER);
			hl.setBounds(hll_x, 25, hll_w, 116);
			//2011/05/26 QP@10181_No.26 TT T.Satoh Change Start -------------------------
			//hl.setText("�@");
			hl.setText("�z���\");
			//2011/05/26 QP@10181_No.26 TT T.Satoh Change End ---------------------------
			this.add(hl);
			
			//���ڃ��x���ݒ�i�T���v��NO�j
			ItemLabel hlSampleNo = new ItemLabel();
			hlSampleNo.setBorder(new LineBorder(Color.BLACK, 1));
			hlSampleNo.setHorizontalAlignment(SwingConstants.LEFT);
			hlSampleNo.setBounds(hlr_x, 25, hlr_w, hlr_h + 10);
			hlSampleNo.setText(" �T���v��NO");
			this.add(hlSampleNo);
			
			//���ڃ��x���ݒ�i���FG�j
			ItemLabel hlPrintFg = new ItemLabel();
			hlPrintFg.setBorder(new LineBorder(Color.BLACK, 1));
			hlPrintFg.setHorizontalAlignment(SwingConstants.LEFT);
			hlPrintFg.setBounds(hlr_x, 50, hlr_w, hlr_h);
			hlPrintFg.setText(" ���FG");
			this.add(hlPrintFg);
			
			//���ڃ��x���ݒ�i�������Z�˗�FG�j
			ItemLabel genkaIraiFg = new ItemLabel();
			genkaIraiFg.setBorder(new LineBorder(Color.BLACK, 1));
			genkaIraiFg.setHorizontalAlignment(SwingConstants.LEFT);
			genkaIraiFg.setBounds(hlr_x, 65, hlr_w, hlr_h);
			genkaIraiFg.setText(" �������Z�˗�");
			this.add(genkaIraiFg);
			
			//���ڃ��x���ݒ�i�j
			ItemLabel hlJutenSuiso = new ItemLabel();
			hlJutenSuiso.setBorder(new LineBorder(Color.BLACK, 1));
			hlJutenSuiso.setHorizontalAlignment(SwingConstants.LEFT);
			hlJutenSuiso.setBounds(hlr_x, 80, hlr_w, hlr_h);
			//2011/05/06 QP@10181_No.71 TT T.Satoh Change Start -------------------------
			//hlJutenSuiso.setText(" �[�U�ʐ���(g)" + strKeisan);
			hlJutenSuiso.setText(" �[�U�ʐ���(g)" + strKeisan);
			//2011/05/06 QP@10181_No.71 TT T.Satoh Change End ---------------------------
			this.add(hlJutenSuiso);
			
			//���ڃ��x���ݒ�i�[�U�ʖ���(g))
			ItemLabel hlJutenYuso = new ItemLabel();
			hlJutenYuso.setBorder(new LineBorder(Color.BLACK, 1));
			hlJutenYuso.setHorizontalAlignment(SwingConstants.LEFT);
			hlJutenYuso.setBounds(hlr_x, 95, hlr_w, hlr_h);
			//2011/05/06 QP@10181_No.71 TT T.Satoh Change Start -------------------------
			//hlJutenYuso.setText(" �[�U�ʖ���(g)" + strKeisan);
			hlJutenYuso.setText(" �[�U�ʖ���(g)" + strKeisan);
			//2011/05/06 QP@10181_No.71 TT T.Satoh Change End ---------------------------
			this.add(hlJutenYuso);
			
			//���ڃ��x���ݒ�i�󔒁j
			ItemLabel hlKuhaku = new ItemLabel();
			hlKuhaku.setBorder(new LineBorder(Color.BLACK, 1));
			hlKuhaku.setHorizontalAlignment(SwingConstants.LEFT);
			hlKuhaku.setBounds(hlr_x, 110, hlr_w, hlr_h);
			hlKuhaku.setText("");
			this.add(hlKuhaku);
			
			//�y�v�Z���ځz ���ڃ��x���ݒ�i���v��(�P�{�Fg)(�e�ʁ~��d)�j
			ItemLabel hlGoukei = new ItemLabel();
			hlGoukei.setBorder(new LineBorder(Color.BLACK, 1));
			hlGoukei.setHorizontalAlignment(SwingConstants.LEFT);
			hlGoukei.setBounds(hlr_x, 125, hlr_w, hlr_h);
			//2011/05/06 QP@10181_No.71 TT T.Satoh Change Start -------------------------
			//hlGoukei.setText(" ���v��(�P�{�Fg)(�e�ʁ~��d)");
			hlGoukei.setText(" ���v��(�P�{�Fg)(�e�ʁ~��d)");
			//2011/05/06 QP@10181_No.71 TT T.Satoh Change End ---------------------------
			this.add(hlGoukei); 
			
			//�y�v�Z���ځz ���ڃ��x���ݒ�i������(kg)�j
			ItemLabel hlGenryohiKg = new ItemLabel();
			hlGenryohiKg.setBorder(new LineBorder(Color.BLACK, 1));
			hlGenryohiKg.setHorizontalAlignment(SwingConstants.LEFT);
			hlGenryohiKg.setBounds(hlr_x, 140, hlr_w, hlr_h);
			hlGenryohiKg.setText(" ������(kg)");
			this.add(hlGenryohiKg);

			//-----------------------------------------------------

			//�y�v�Z���ځz ���ڃ��x���ݒ�i������(1�{��)�j
			ItemLabel hlGenryohi1Hon = new ItemLabel();
			hlGenryohi1Hon.setBorder(new LineBorder(Color.BLACK, 1));
			hlGenryohi1Hon.setHorizontalAlignment(SwingConstants.LEFT);
			hlGenryohi1Hon.setBounds(hlr_x, 155, hlr_w, hlr_h);
			hlGenryohi1Hon.setText(" ������(1�{��)");
			this.add(hlGenryohi1Hon);

			//--------------------�@�A�@----------------------------
			
			//���ڃ��x���ݒ�i����:�A�j
			hl = new ItemLabel();
			hl.setBorder(new LineBorder(Color.BLACK, 1));
			hl.setHorizontalAlignment(SwingConstants.CENTER);
			hl.setBounds(hll_x, 170, hll_w, hlr_h);
			//2011/05/26 QP@10181_No.26 TT T.Satoh Change Start -------------------------
			//hl.setText("�A");
			hl.setText("�����l");
			//2011/05/26 QP@10181_No.26 TT T.Satoh Change End ---------------------------
			this.add(hl);
			
			//���ڃ��x���ݒ�i��d�j
			ItemLabel hlHiju = new ItemLabel();
			hlHiju.setBorder(new LineBorder(Color.BLACK, 1));
			hlHiju.setHorizontalAlignment(SwingConstants.LEFT);
			hlHiju.setBounds(hlr_x, 170, hlr_w, hlr_h);
			//2011/05/06 QP@10181_No.71 TT T.Satoh Change Start -------------------------
			//hlHiju.setText(" ��d");
			hlHiju.setText(" ��d");
			//2011/05/06 QP@10181_No.71 TT T.Satoh Change End ---------------------------
			this.add(hlHiju);

			//--------------------�@�B�@----------------------------
			
			//���ڃ��x���ݒ�i����:�B�j
			hl = new ItemLabel();
			hl.setBorder(new LineBorder(Color.BLACK, 1));
			hl.setHorizontalAlignment(SwingConstants.CENTER);
			hl.setBounds(hll_x, 185, hll_w, 31);
			//2011/05/26 QP@10181_No.26 TT T.Satoh Change Start -------------------------
			//hl.setText("�B");
			hl.setText("��{���");
			//2011/05/26 QP@10181_No.26 TT T.Satoh Change End ---------------------------
			this.add(hl);
			
			//���ڃ��x���ݒ�i�e�ʁj
			ItemLabel hlYoryo = new ItemLabel();
			hlYoryo.setBorder(new LineBorder(Color.BLACK, 1));
			hlYoryo.setHorizontalAlignment(SwingConstants.LEFT);
			hlYoryo.setBounds(hlr_x, 185, hlr_w, hlr_h);
			hlYoryo.setText(" �e��");
			this.add(hlYoryo);
			
			//���ڃ��x���ݒ�i���萔�j
			ItemLabel hlIrisu = new ItemLabel();
			hlIrisu.setBorder(new LineBorder(Color.BLACK, 1));
			hlIrisu.setHorizontalAlignment(SwingConstants.LEFT);
			hlIrisu.setBounds(hlr_x, 200, hlr_w, hlr_h);
			hlIrisu.setText(" ���萔");
			this.add(hlIrisu);

			//-----------------------------------------------------

			//���ڃ��x���ݒ�i�L������(%)�j
			ItemLabel hlYukoBudomari = new ItemLabel();
			hlYukoBudomari.setBorder(new LineBorder(Color.BLACK, 1));
			hlYukoBudomari.setHorizontalAlignment(SwingConstants.LEFT);
			hlYukoBudomari.setBounds(hlr_x, 215, hlr_w, hlr_h);
			hlYukoBudomari.setText(" �L������(%)" + strKeisan);
			this.add(hlYukoBudomari);
			
			//���ڃ��x���ݒ�i�󔒁j
			hlKuhaku = new ItemLabel();
			hlKuhaku.setBorder(new LineBorder(Color.BLACK, 1));
			hlKuhaku.setHorizontalAlignment(SwingConstants.LEFT);
			hlKuhaku.setBounds(hlr_x, 230, hlr_w, hlr_h);
			hlKuhaku.setText("");
			this.add(hlKuhaku);
			
			//�y�v�Z���ځz ���ڃ��x���ݒ�i���x����(g���e�ʁ~����)�j
			ItemLabel hlLebelRyo1 = new ItemLabel();
			hlLebelRyo1.setBorder(new LineBorder(Color.BLACK, 1));
			hlLebelRyo1.setHorizontalAlignment(SwingConstants.LEFT);
			hlLebelRyo1.setBounds(hlr_x, 245, hlr_w, hlr_h);
			hlLebelRyo1.setText(" ���x����(g���e�ʁ~����)");
			this.add(hlLebelRyo1);

//mod start --------------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.24
//			//�y�v�Z���ځz ���ڃ��x���ݒ�i��d���Z��(g���x���ʁ~��d)�j
			//�y�v�Z���ځz ���ڃ��x���ݒ�i��d���Z��(g���Ϗ[�U�ʁ~��d)�j
			ItemLabel hlLebelRyo2 = new ItemLabel();
			hlLebelRyo2.setBorder(new LineBorder(Color.BLACK, 1));
			hlLebelRyo2.setHorizontalAlignment(SwingConstants.LEFT);
			hlLebelRyo2.setBounds(hlr_x, 260, hlr_w, hlr_h);
//			hlLebelRyo2.setText(" ��d���Z��(g���x���ʁ~��d)");
			hlLebelRyo2.setText(" ��d���Z��(g���Ϗ[�U�ʁ~��d)");
			this.add(hlLebelRyo2);
//mod end --------------------------------------------------------------------------------------
			
			//���ڃ��x���ݒ�i���Ϗ[�U��(g)�j
			ItemLabel hlLebelRyo3 = new ItemLabel();
			hlLebelRyo3.setBorder(new LineBorder(Color.BLACK, 1));
			hlLebelRyo3.setHorizontalAlignment(SwingConstants.LEFT);
			hlLebelRyo3.setBounds(hlr_x, 275, hlr_w, hlr_h);
			hlLebelRyo3.setText(" ���Ϗ[�U��(g)" + strKeisan);
			this.add(hlLebelRyo3);

			//--------------------�@1c/s������̌v�Z�@---------------

			//���ڃ��x���ݒ�i����:1c/s������̌v�Z�j
			ItemLabel hlKeisan1CS = new ItemLabel();
			hlKeisan1CS.setBorder(new LineBorder(Color.BLACK, 1));
			hlKeisan1CS.setFont(new Font("Default", Font.PLAIN, 9));
			hlKeisan1CS.setHorizontalAlignment(SwingConstants.CENTER);
			//2011/06/07 QP@10181_No.26 TT T.Satoh Change Start -------------------------
			//hlKeisan1CS.setBounds(hll_x, 290, hll_w, hlr_h);
			hlKeisan1CS.setBounds(hll_x, 290, hll_w, hlr_h * 4);
			//2011/06/07 QP@10181_No.26 TT T.Satoh Change End ---------------------------
			hlKeisan1CS.setText("1c/s������̌v�Z");
			this.add(hlKeisan1CS);
			
			//�y�v�Z���ځz ���ڃ��x���ݒ�i������j
			ItemLabel hlGenryohi = new ItemLabel();
			hlGenryohi.setBorder(new LineBorder(Color.BLACK, 1));
			hlGenryohi.setHorizontalAlignment(SwingConstants.LEFT);
			hlGenryohi.setBounds(hlr_x, 290, hlr_w, hlr_h);
			hlGenryohi.setText(" ������");
			this.add(hlGenryohi);

			//--------------------�@�C�@----------------------------

			//���ڃ��x���ݒ�i����:�C�j
			hl = new ItemLabel();
			hl.setBorder(new LineBorder(Color.BLACK, 1));
			hl.setHorizontalAlignment(SwingConstants.CENTER);
			hl.setBounds(hll_x, 305, hll_w, hlr_h);
			
//2011/05/26 QP@10181_No.26 TT T.Satoh Change Start -------------------------
			//hl.setText("�C");
			hl.setText("");
//2011/05/26 QP@10181_No.26 TT T.Satoh Change End ---------------------------

			this.add(hl);
			//���ڃ��x���ݒ�i�ޗ���j
			ItemLabel hlZairyohi = new ItemLabel();
			hlZairyohi.setBorder(new LineBorder(Color.BLACK, 1));
			hlZairyohi.setHorizontalAlignment(SwingConstants.LEFT);
			hlZairyohi.setBounds(hlr_x, 305, hlr_w, hlr_h);
			hlZairyohi.setText(" �ޗ���" + strKeisan);
			this.add(hlZairyohi);

			//-----------------------------------------------------

			//���ڃ��x���ݒ�i�Œ��j
			ItemLabel hlKeihi = new ItemLabel();
			hlKeihi.setBorder(new LineBorder(Color.BLACK, 1));
			hlKeihi.setHorizontalAlignment(SwingConstants.LEFT);
			hlKeihi.setBounds(hlr_x, 320, hlr_w, hlr_h);
			hlKeihi.setText(" �Œ��" + strKeisan);
			this.add(hlKeihi);
			
			//�y�v�Z���ځz ���ڃ��x���ݒ�i�����v/cs�j
			ItemLabel hlGenkakeiCs = new ItemLabel();
			hlGenkakeiCs.setBorder(new LineBorder(Color.BLACK, 1));
			hlGenkakeiCs.setHorizontalAlignment(SwingConstants.LEFT);
			hlGenkakeiCs.setBounds(hlr_x, 335, hlr_w, hlr_h);
			hlGenkakeiCs.setText(" �����v/cs");
			this.add(hlGenkakeiCs);

			//--------------------�@1������̌v�Z�@----------------

			//���ڃ��x���ݒ�i����:1������̌v�Z�j
			ItemLabel hlKeisan1Ko = new ItemLabel();
			hlKeisan1Ko.setBorder(new LineBorder(Color.BLACK, 1));
			hlKeisan1Ko.setFont(new Font("Default", Font.PLAIN, 9));
			hlKeisan1Ko.setHorizontalAlignment(SwingConstants.CENTER);
			hlKeisan1Ko.setBounds(hll_x, 350, hll_w, hlr_h);
			hlKeisan1Ko.setText("1������̌v�Z");
			this.add(hlKeisan1Ko);
			
			//�y�v�Z���ځz ���ڃ��x���ݒ�i�����v/�j
			ItemLabel hlGenkakeiKo = new ItemLabel();
			hlGenkakeiKo.setBorder(new LineBorder(Color.BLACK, 1));
			hlGenkakeiKo.setHorizontalAlignment(SwingConstants.LEFT);
			hlGenkakeiKo.setBounds(hlr_x, 350, hlr_w, hlr_h);
			hlGenkakeiKo.setText(" �����v/��");
			this.add(hlGenkakeiKo);

			//-----------------------------------------------------

			//���ڃ��x���ݒ�i�����j
			ItemLabel hlBaika = new ItemLabel();
			hlBaika.setBorder(new LineBorder(Color.BLACK, 1));
			hlBaika.setHorizontalAlignment(SwingConstants.LEFT);
			hlBaika.setBounds(hlr_x, 365, hlr_w, hlr_h);
			hlBaika.setText(" ����");
			this.add(hlBaika);
			
			//�y�v�Z���ځz ���ڃ��x���ݒ�i�e��(%)�j
			ItemLabel hlArari = new ItemLabel();
			hlArari.setBorder(new LineBorder(Color.BLACK, 1));
			hlArari.setHorizontalAlignment(SwingConstants.LEFT);
			hlArari.setBounds(hlr_x, 380, hlr_w, hlr_h);
			hlArari.setText(" �e��(%)");
			this.add(hlArari);
			
//2011/04/20 QP@10181_No.67 TT Nishigawa Change Start ---------------------------
			//�y�v�Z���ځz ���ڃ��x���ݒ�i�˗��L�����Z���j
			ItemLabel hlCancel = new ItemLabel();
			hlCancel.setBorder(new LineBorder(Color.BLACK, 1));
			hlCancel.setHorizontalAlignment(SwingConstants.LEFT);
			hlCancel.setBounds(hlr_x, 395, hlr_w, hlr_h);
			hlCancel.setText(" �˗��L�����Z��");
			this.add(hlCancel);
//2011/04/20 QP@10181_No.67 TT Nishigawa Change End ---------------------------
			

			//-----------------------------------------------------

			//���ڃ��x���ݒ�i���l�j
			ItemLabel hlBikou = new ItemLabel();
			hlBikou.setBackground(JwsConstManager.SHISAKU_ITEM_COLOR2);
			hlBikou.setBorder(new LineBorder(Color.BLACK, 1));
			hlBikou.setBounds(hlr_x, 435+15, 15, 16);
			this.add(hlBikou);
			
			ItemIndicationLabel ilBikou = new ItemIndicationLabel();
			ilBikou.setBounds(hlBikou.getX() + 16, hlBikou.getY(), 120, 16);
			ilBikou.setText(" �̍��ڂ͕ҏW�\");
			this.add(ilBikou);

			//--------------------�@���Z�m��T���v��NO�@--------------------------

			//���ڃ��x���ݒ�i�e��(%)�j
			ItemLabel lblKakuteiSampleNo = new ItemLabel();
			lblKakuteiSampleNo.setBackground(Color.white);
			lblKakuteiSampleNo.setBorder(new LineBorder(Color.BLACK, 0));
			lblKakuteiSampleNo.setHorizontalAlignment(SwingConstants.CENTER);
			lblKakuteiSampleNo.setBounds(273, 419+15, 120, hlr_h);
			lblKakuteiSampleNo.setText(" ���Z�m��T���v��No");
			this.add(lblKakuteiSampleNo);
			
			//�R���{�{�b�N�X�ݒ�i���Z�m��T���v��NO�j
			cmbShisanKakutei = new ComboBase();
			cmbShisanKakutei.setBounds(273, 435+15, 120, 20);
			//���[�h�ҏW
			if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
					JwsConstManager.JWS_COMPONENT_0131, DataCtrl.getInstance().getParamData().getStrMode())){
				cmbShisanKakutei.setEnabled(false);
			}
			this.add(cmbShisanKakutei);

			//--------------------�@�{�^���@--------------------------

			//�{�^���ݒ�i�������Z�o�^�j
			btnShisanToroku = new ButtonBase();
			btnShisanToroku.setBounds(cmbShisanKakutei.getX() + 120, 435+15, 80, 20);
			btnShisanToroku.setHorizontalAlignment(SwingConstants.CENTER);
			btnShisanToroku.setMargin(new Insets(0, 0, 0, 0));
			btnShisanToroku.setText("�������Z�o�^");
			//���[�h�ҏW
			if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
					JwsConstManager.JWS_COMPONENT_0150, DataCtrl.getInstance().getParamData().getStrMode())){
				btnShisanToroku.setEnabled(false);
			}
			this.add(btnShisanToroku);
			
			//�{�^���ݒ�(���Z�����Q��)
			btnShisanRireki = new ButtonBase();
			btnShisanRireki.setBounds(btnShisanToroku.getX() + 80, 435+15, 80, 20);
			btnShisanRireki.setHorizontalAlignment(SwingConstants.CENTER);
			btnShisanRireki.setMargin(new Insets(0, 0, 0, 0));
			btnShisanRireki.setText("���Z�����Q��");
			btnShisanRireki.addActionListener(this.getActionEvent());
			btnShisanRireki.setActionCommand(CLICK_SHISAN_RIREKI);
			//���[�h�ҏW
			if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
					JwsConstManager.JWS_COMPONENT_0132, DataCtrl.getInstance().getParamData().getStrMode())){
				btnShisanRireki.setEnabled(false);
			}
			this.add(btnShisanRireki);
			
			//�{�^���ݒ�i�������Z�\����j
			btnShisanHyo = new ButtonBase();
			btnShisanHyo.setBounds(1024 - 140, 435+15, 100, 20);
			btnShisanHyo.setHorizontalAlignment(SwingConstants.CENTER);
			btnShisanHyo.setMargin(new Insets(0, 0, 0, 0));
			btnShisanHyo.setText("�������Z�\���");
			//���[�h�ҏW
			if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
					JwsConstManager.JWS_COMPONENT_0133, DataCtrl.getInstance().getParamData().getStrMode())){
				btnShisanHyo.setEnabled(false);
			}
			this.add(btnShisanHyo);
			
			//------------------------------------------------------
			
		} catch (Exception e) {
			//�G���[�ݒ�
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			//2011/04/13 QP@10181_No.26 TT T.Satoh Change Start -------------------------
			//ex.setStrErrmsg("�������Z�D �R���|�[�l���g���������������s���܂���");
			ex.setStrErrmsg("�������Z �R���|�[�l���g���������������s���܂���");
			//2011/04/13 QP@10181_No.26 TT T.Satoh Change End ---------------------------
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getMessage());
			throw ex;
			
		} finally {
			
		}	
		
	}

	/************************************************************************************
	 * 
	 * �e�[�u������������
	 *   @param aryTrialData : ����f�[�^
	 *   @throws ExceptionBase 
	 *   @author TT katayama
	 *   
	 ************************************************************************************/
	private void initTable(Object[] aryTrialData) throws ExceptionBase {
		
		//�擾�Ώۃf�[�^ : ����e�[�u��
		TrialData trialData = null;
		
		//�擾�Ώۃf�[�^ : ���������e�[�u��
		CostMaterialData costMaterialData = null;
		//�擾�Ώۃf�[�^ : ��������
		ArrayList aryCostMaterialData = null;
			
		try {
			//�s�E��
			
//2011/04/20 QP@10181_No.67 TT Nishigawa Change Start -------------------------
//			int intRowCount = 24;
			int intRowCount = 25;
//2011/04/20 QP@10181_No.67 TT Nishigawa Change End ---------------------------
			
			int intColumnCount = aryTrialData.length;
			
			//�e�[�u������
			//2011/04/20 QP@10181_No.41 TT T.Satoh Change Start -------------------------
			//JScrollPane scroll;
			//2011/04/20 QP@10181_No.41 TT T.Satoh Change End ---------------------------
			table = new TableBase(intRowCount,intColumnCount){

				private static final long serialVersionUID = 1L;
				
				//--------------------�@���������f�[�^�X�V�@----------------------------
				public void editingStopped( ChangeEvent e ){
					try{
						super.editingStopped( e );
						
						//�ҏW�s��ԍ��擾
						int row = getSelectedRow();
						int column = getSelectedColumn();
						if( row>=0 && column>=0 ){
							
							//�L�[���ڎ擾
							MiddleCellEditor mceSeq = (MiddleCellEditor)this.getCellEditor(0, column);
							DefaultCellEditor dceSeq = (DefaultCellEditor)mceSeq.getTableCellEditor(0);
							TextboxBase chkSeq = (TextboxBase)dceSeq.getComponent();
							int intSeq  = Integer.parseInt(chkSeq.getPk1());

							//--------------------�@���[�h�ҏW�@----------------------------
					    	if(row == 1){
								//���FG
								if(DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
										JwsConstManager.JWS_COMPONENT_0149, DataCtrl.getInstance().getParamData().getStrMode())){

									//���������f�[�^�̍X�V
					    			String insert = (getValueAt( row, column ).toString()=="true")?"1":"0";
						    		DataCtrl.getInstance().getTrialTblData().UpdGenkaPrintFg(
						    				intSeq,
						    				DataCtrl.getInstance().getTrialTblData().checkNullInt(insert),
						    				DataCtrl.getInstance().getUserMstData().getDciUserid());
						    		
						    		//setValueAt(insert, row, column);
						    								    		
								}
								
					    	} 
					    	
					    	else if(row == 2){
								//�������Z�˗�FG
								if(DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
										JwsConstManager.JWS_COMPONENT_0151, DataCtrl.getInstance().getParamData().getStrMode())){

									//���������f�[�^�̍X�V
					    			String insert = (getValueAt( row, column ).toString()=="true")?"1":"0";
					    			
						    		DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuGenkaIraiFg(
						    				intSeq,
						    				Integer.parseInt(insert),
						    				DataCtrl.getInstance().getUserMstData().getDciUserid());
						    		
						    		//setValueAt(insert, row, column);
						    								    		
								}
								
					    	} 
					    	
					    	else if (row == 3) {
								//�[�U�ʐ���
								if(DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
										JwsConstManager.JWS_COMPONENT_0134, DataCtrl.getInstance().getParamData().getStrMode())){

									//���������f�[�^�̍X�V
					    			String insert = getValueAt( row, column ).toString();
						    		DataCtrl.getInstance().getTrialTblData().UpdGenkaValue(
						    				intSeq,
						    				DataCtrl.getInstance().getTrialTblData().checkNullString(insert),
						    				DataCtrl.getInstance().getUserMstData().getDciUserid(),
						    				JwsConstManager.JWS_COMPONENT_0134);

						    		//��ʕ\���l�̍X�V
						    		updDispValues(intSeq, column);
						    		
								}
								
					    	}
					    	
					    	else if (row == 4) {
								//�[�U�ʖ���
								if(DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
										JwsConstManager.JWS_COMPONENT_0135, DataCtrl.getInstance().getParamData().getStrMode())){

									//���������f�[�^�̍X�V
					    			String insert = getValueAt( row, column ).toString();
						    		DataCtrl.getInstance().getTrialTblData().UpdGenkaValue(
						    				intSeq,
						    				DataCtrl.getInstance().getTrialTblData().checkNullString(insert),
						    				DataCtrl.getInstance().getUserMstData().getDciUserid(),
						    				JwsConstManager.JWS_COMPONENT_0135);

						    		//��ʕ\���l�̍X�V
						    		updDispValues(intSeq, column);
						    		
								}
								
					    	}
					    	
					    	else if (row == 12) {
								//�L������
								if(DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
										JwsConstManager.JWS_COMPONENT_0127, DataCtrl.getInstance().getParamData().getStrMode())){

									//JTable�����̕ҏW�l���擾
					    			String insert = getValueAt( row, column ).toString();
					    			
					    			
					    			// QP@10181_No.30 -----------------------------------------------start
						    		//���g�̗񐔂��擾
						    		int intColomCnt = getColumnCount();
						    		
						    		//����SEQ�̎擾
						    		MiddleCellEditor mceShisakuSeq = (MiddleCellEditor)this.getCellEditor(0, column);
									DefaultCellEditor dceShisakuSeq = (DefaultCellEditor)mceShisakuSeq.getTableCellEditor(0);
									TextboxBase chkShisakuSeq = (TextboxBase)dceShisakuSeq.getComponent();
									int intShisakuSeq  = Integer.parseInt(chkShisakuSeq.getPk1());
						    		
						    		//�f�[�^���̗L�������擾
						    		ArrayList genkaData = DataCtrl.getInstance().getTrialTblData().SearchGenkaGenryoData(intShisakuSeq);
						    		CostMaterialData costMaterialData = (CostMaterialData)genkaData.get(0);
						    		String strYukoBudomari = costMaterialData.getStrYukoBudomari();
						    		
						    		//�L�������i���͒l�j�擾
						    		double dblInsert = -1;
						    		
						    		//�L�������i���͒l�j��NULL�̏ꍇ
						    		if(insert == null){
						    			
						    		}
						    		//�L�������i���͒l�j��NULL�łȂ��ꍇ
						    		else{
						    			if(insert.length() == 0){
						    				
						    			}
						    			else{
							    			try{
						    					dblInsert = Double.parseDouble(insert);
						    				}catch(Exception ee){
						    					dblInsert = 0.00;
						    				}
						    			}
						    		}
						    		
						    		
						    		//�L�������i���ݒl�j�擾
						    		double dblBudomari = -1;
						    		
						    		//�L�������i���ݒl�j��NULL�̏ꍇ
						    		if(strYukoBudomari == null){
						    			
						    		}
						    		//�L�������i���ݒl�j��NULL�łȂ��ꍇ
						    		else{
						    			if(strYukoBudomari.length() == 0){
						    				
						    			}
						    			else{
						    				dblBudomari = Double.parseDouble(strYukoBudomari);
						    			}
						    		}
						    		
						    		//�񐔂��P���傫���ꍇ ���@���͒l�����݂̐ݒ�l�Ɠ������Ȃ��ꍇ
							    	if( intColomCnt > 1 && dblInsert != dblBudomari ){
						    			
						    			//�_�C�A���O�R���|�[�l���g�ݒ�
										JOptionPane jp = new JOptionPane();
										
										//�m�F�_�C�A���O�\��
										int option = jp.showConfirmDialog(
												jp.getRootPane(),
												"�L�������𑼂̗�ɂ����f���܂����H"
												, "�m�F���b�Z�[�W"
												,JOptionPane.YES_NO_OPTION
												,JOptionPane.PLAIN_MESSAGE
											);
										
										//�u�͂��v����
									    if (option == JOptionPane.YES_OPTION){
									    	
									    	//�񐔕����[�v
									    	for( int i = 0; i < intColomCnt; i++ ){
									    		
									    		//����SEQ�̎擾
									    		MiddleCellEditor mceLoopSeq = (MiddleCellEditor)this.getCellEditor(0, i);
												DefaultCellEditor dceLoopSeq = (DefaultCellEditor)mceLoopSeq.getTableCellEditor(0);
												TextboxBase chkLoopSeq = (TextboxBase)dceLoopSeq.getComponent();
												int intLoopSeq  = Integer.parseInt(chkLoopSeq.getPk1());
									    		
									    		//���������f�[�^�̍X�V
									    		DataCtrl.getInstance().getTrialTblData().UpdGenkaValue(
									    				intLoopSeq,
									    				DataCtrl.getInstance().getTrialTblData().checkNullString(insert),
									    				DataCtrl.getInstance().getUserMstData().getDciUserid(),
									    				JwsConstManager.JWS_COMPONENT_0127);

									    		//��ʕ\���l�̍X�V
									    		updDispValues(intLoopSeq, i);
									    		
									    	}
									    	
									    }
									    //�u�������v����
									    else if (option == JOptionPane.NO_OPTION){
									    	
									    	
									    }
						    		}
							    	// QP@10181_No.30 -----------------------------------------------end
							    	
					    			
					    			//���������f�[�^�̍X�V
						    		DataCtrl.getInstance().getTrialTblData().UpdGenkaValue(
						    				intSeq,
						    				DataCtrl.getInstance().getTrialTblData().checkNullString(insert),
						    				DataCtrl.getInstance().getUserMstData().getDciUserid(),
						    				JwsConstManager.JWS_COMPONENT_0127);

						    		//��ʕ\���l�̍X�V
						    		updDispValues(intSeq, column);
						    		
								}
								
					    	}
					    	
					    	else if (row == 16) {
								//���Ϗ[�U��
								if(DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
										JwsConstManager.JWS_COMPONENT_0128, DataCtrl.getInstance().getParamData().getStrMode())){

									
									//JTable�����̕ҏW�l���擾
					    			String insert = getValueAt( row, column ).toString();
					    			
					    			
					    			// QP@10181_No.30 -----------------------------------------------start
						    		//���g�̗񐔂��擾
						    		int intColomCnt = getColumnCount();
						    		
						    		//����SEQ�̎擾
						    		MiddleCellEditor mceShisakuSeq = (MiddleCellEditor)this.getCellEditor(0, column);
									DefaultCellEditor dceShisakuSeq = (DefaultCellEditor)mceShisakuSeq.getTableCellEditor(0);
									TextboxBase chkShisakuSeq = (TextboxBase)dceShisakuSeq.getComponent();
									int intShisakuSeq  = Integer.parseInt(chkShisakuSeq.getPk1());
						    		
						    		//�f�[�^���̗L�������擾
						    		ArrayList genkaData = DataCtrl.getInstance().getTrialTblData().SearchGenkaGenryoData(intShisakuSeq);
						    		CostMaterialData costMaterialData = (CostMaterialData)genkaData.get(0);
						    		String strZyutenAve = costMaterialData.getStrZyutenAve();
						    		
						    		//���Ϗ[�U�ʁi���͒l�j�̎擾
						    		double dblInsert = -1;
						    		
						    		//���Ϗ[�U�ʁi���͒l�j��NULL�̏ꍇ
						    		if(insert == null){
						    			
						    		}
						    		//���Ϗ[�U�ʁi���͒l�j��NULL�łȂ��ꍇ
						    		else{
						    			if(insert.length() == 0){
						    				
						    			}
						    			else{
						    				try{
						    					dblInsert = Double.parseDouble(insert);
						    				}catch(Exception ee){
						    					dblInsert = 0.00;
						    				}
						    			}
						    		}
						    		
						    		//���Ϗ[�U�ʁi���ݒl�j�̎擾
						    		double dblZyutenAve = -1;
						    		
						    		//���Ϗ[�U�ʁi���ݒl�j��NULL�̏ꍇ
						    		if(strZyutenAve == null){
						    			
						    		}
						    		//���Ϗ[�U�ʁi���ݒl�j��NULL�łȂ��ꍇ
						    		else{
						    			if(strZyutenAve.length() == 0){
						    				
						    			}
						    			else{
						    				dblZyutenAve = Double.parseDouble(strZyutenAve);
						    			}
						    		}
						    		
						    		//�񐔂��P���傫���ꍇ ���@���͒l�����݂̐ݒ�l�Ɠ������Ȃ��ꍇ
							    	if( intColomCnt > 1 && dblInsert != dblZyutenAve ){
						    			
						    			//�_�C�A���O�R���|�[�l���g�ݒ�
										JOptionPane jp = new JOptionPane();
										
										//�m�F�_�C�A���O�\��
										int option = jp.showConfirmDialog(
												jp.getRootPane(),
												"���Ϗ[�U�ʂ𑼂̗�ɂ����f���܂����H"
												, "�m�F���b�Z�[�W"
												,JOptionPane.YES_NO_OPTION
												,JOptionPane.PLAIN_MESSAGE
											);
										
										//�u�͂��v����
									    if (option == JOptionPane.YES_OPTION){
									    	
									    	//�񐔕����[�v
									    	for( int i = 0; i < intColomCnt; i++ ){
									    		
									    		//����SEQ�̎擾
									    		MiddleCellEditor mceLoopSeq = (MiddleCellEditor)this.getCellEditor(0, i);
												DefaultCellEditor dceLoopSeq = (DefaultCellEditor)mceLoopSeq.getTableCellEditor(0);
												TextboxBase chkLoopSeq = (TextboxBase)dceLoopSeq.getComponent();
												int intLoopSeq  = Integer.parseInt(chkLoopSeq.getPk1());
									    		
												//���������f�[�^�̍X�V
								    			DataCtrl.getInstance().getTrialTblData().UpdGenkaValue(
								    					intLoopSeq,
									    				DataCtrl.getInstance().getTrialTblData().checkNullString(insert),
									    				DataCtrl.getInstance().getUserMstData().getDciUserid(),
									    				JwsConstManager.JWS_COMPONENT_0128);

									    		//��ʕ\���l�̍X�V
									    		updDispValues(intLoopSeq, i);
									    		
									    	}
									    	
									    }
									    //�u�������v����
									    else if (option == JOptionPane.NO_OPTION){
									    	
									    	
									    }
						    		}
							    	// QP@10181_No.30 -----------------------------------------------end
					    			
						    		
					    			//���������f�[�^�̍X�V
					    			DataCtrl.getInstance().getTrialTblData().UpdGenkaValue(
						    				intSeq,
						    				DataCtrl.getInstance().getTrialTblData().checkNullString(insert),
						    				DataCtrl.getInstance().getUserMstData().getDciUserid(),
						    				JwsConstManager.JWS_COMPONENT_0128);

						    		//��ʕ\���l�̍X�V
						    		updDispValues(intSeq, column);
						    		
								}
								
					    	}
					    	
					    	else if (row == 18) {
								//�ޗ���
								if(DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
										JwsConstManager.JWS_COMPONENT_0145, DataCtrl.getInstance().getParamData().getStrMode())){

									//���������f�[�^�̍X�V
					    			String insert = getValueAt( row, column ).toString();
						    		DataCtrl.getInstance().getTrialTblData().UpdGenkaValue(
						    				intSeq,
						    				DataCtrl.getInstance().getTrialTblData().checkNullString(insert),
						    				DataCtrl.getInstance().getUserMstData().getDciUserid(),
						    				JwsConstManager.JWS_COMPONENT_0145);

						    		//��ʕ\���l�̍X�V
						    		updDispValues(intSeq, column);
						    		
								}
								
					    	}
					    	
					    	else if (row == 19) {
								//�Œ��
								if(DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
										JwsConstManager.JWS_COMPONENT_0129, DataCtrl.getInstance().getParamData().getStrMode())){

									//���������f�[�^�̍X�V
					    			String insert = getValueAt( row, column ).toString();
						    		DataCtrl.getInstance().getTrialTblData().UpdGenkaValue(
						    				intSeq,
						    				DataCtrl.getInstance().getTrialTblData().checkNullString(insert),
						    				DataCtrl.getInstance().getUserMstData().getDciUserid(),
						    				JwsConstManager.JWS_COMPONENT_0129);

						    		//��ʕ\���l�̍X�V
						    		updDispValues(intSeq, column);
						    		
								}
																
					    	}
					    	
//2011/04/12 QP@10181_No.67 TT Nishigawa Change Start -------------------------
					    	else if(row == 24){
								//�L�����Z��FG
								if(DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
										JwsConstManager.JWS_COMPONENT_0151, DataCtrl.getInstance().getParamData().getStrMode())){

									//���������f�[�^�̍X�V
					    			String insert = (getValueAt( row, column ).toString()=="true")?"1":"0";
						    		DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuCancelIraiFg(
						    				intSeq,
						    				Integer.parseInt(insert),
						    				DataCtrl.getInstance().getUserMstData().getDciUserid());
								}
					    	} 
//2011/04/12 QP@10181_No.67 TT Nishigawa Change End -------------------------
					    	
						}
					}catch(ExceptionBase be){
						DataCtrl.getInstance().PrintMessage(be);
						
					}catch(Exception ex){
						//�G���[�ݒ�
						ExceptionBase e1 = new ExceptionBase();
						ex.printStackTrace();
						e1.setStrErrCd("");
						//2011/04/12 QP@10181_No.26 TT T.Satoh Change Start -------------------------
						//e1.setStrErrmsg("����\�D �C�x���g���������s���܂���");
						e1.setStrErrmsg("�������Z �C�x���g���������s���܂���");
						//2011/04/12 QP@10181_No.26 TT T.Satoh Change End ---------------------------
						e1.setStrErrShori(this.getClass().getName());
						e1.setStrMsgNo("");
						e1.setStrSystemMsg(ex.getMessage());
						DataCtrl.getInstance().PrintMessage(e1);
												
					}finally{
						//�e�X�g�\��
//						DataCtrl.getInstance().getTrialTblData().dispCostMaterialData();
//						DataCtrl.getInstance().getTrialTblData().dispTrial();
						
					}
					
				}
				
			};
			table.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
			table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
			table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			table.setCellSelectionEnabled(true);
			
			//�e�[�u���T�C�Y�ݒ�
			table.setRowHeight(15);
			table.setRowHeight(0,25);
			
			//�X�N���[���p�l���ݒ�
			scroll = new JScrollPane( table ) {
				private static final long serialVersionUID = 1L;
				
				//�w�b�_�[������
				public void setColumnHeaderView(Component view) {} 
				
			};
			scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
			scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
			scroll.setBorder(new LineBorder(Color.BLACK, 1));
			scroll.setBounds(273, 25, 721, 403);
			scroll.setBackground(Color.WHITE);
			//2011/04/21 QP@10181_No.41 TT T.Satoh Add Start -------------------------
			scroll.getHorizontalScrollBar().setMaximum(200*intColumnCount);
			scroll.getVerticalScrollBar().setMaximum(1000);
			//2011/04/21 QP@10181_No.41 TT T.Satoh Add End ---------------------------
			this.add(scroll,BorderLayout.CENTER);
			
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

				//------------------------ �e�[�u���f�[�^�̎擾  ------------------------

				//�����f�[�^�擾�i�\�����j
				int no=0;
				for(int i=0; i<columnModel.getColumnCount(); i++){
					TrialData chkHyji = (TrialData)aryTrialData[i];
					if((chkHyji.getIntHyojiNo()-1) == j){
						no=i;
					}
				}
				trialData = (TrialData)aryTrialData[no];
				
				//���������e�[�u���f�[�^�̍X�V�E�ǉ� 
				// (�f�[�^�����݂��Ȃ��ꍇ�́A�V�K�f�[�^�𐶐�)
				DataCtrl.getInstance().getTrialTblData().AddGenkaGenryoData(trialData.getIntShisakuSeq());
				
				//�����������擾
				aryCostMaterialData = DataCtrl.getInstance().getTrialTblData().SearchGenkaGenryoData(trialData.getIntShisakuSeq());
				costMaterialData = (CostMaterialData)aryCostMaterialData.get(0);
				 
				//--------------------------------- �T���v��NO --------------------------
				//�R���|�[�l���g����
				TextboxBase sampleNoTB = new TextboxBase();
				sampleNoTB.setEditable(false);
				sampleNoTB.setBackground(JwsConstManager.SHISAKU_ITEM_COLOR);
				sampleNoTB.setPk1(Integer.toString(trialData.getIntShisakuSeq()));
				//�Z���G�f�B�^&�����_������
				TextFieldCellEditor sampleNoTCE = new TextFieldCellEditor(sampleNoTB);
				TextFieldCellRenderer sampleNoTCR = new TextFieldCellRenderer(sampleNoTB);
				//���ԃG�f�B�^&�����_���֓o�^
				MiddleCellEditor.addEditorAt(0, sampleNoTCE);
				MiddleCellRenderer.add(0, sampleNoTCR);
				//�f�[�^�ݒ�
				table.setValueAt( trialData.getStrSampleNo(), 0, j);

				//--------------------------------- ���FG -------------------------------
				//�R���|�[�l���g����
				CheckboxBase insatuFg = new CheckboxBase();
				insatuFg.setHorizontalAlignment(CheckboxBase.CENTER);
				//�f�[�^�ݒ�
				if(costMaterialData.getIntinsatu() == 1){
					//insatuFg.setSelected(true);
					//�f�[�^�ݒ�
					table.setValueAt( "true", 1, j);
					
				}
				//���[�h�ҏW
				if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
						JwsConstManager.JWS_COMPONENT_0149, DataCtrl.getInstance().getParamData().getStrMode())){
					insatuFg.setEnabled(false);
					
				}
				insatuFg.setBackground(JwsConstManager.SHISAKU_ITEM_COLOR2);
				//�G�f�B�^�[�������_���[����
				DefaultCellEditor insatuFlgTCE = new DefaultCellEditor(insatuFg);
				CheckBoxCellRenderer insatuFlgTCR = new CheckBoxCellRenderer(insatuFg);
				//���ԃG�f�B�^�[�������_���[�֓o�^
				MiddleCellEditor.addEditorAt(1, insatuFlgTCE);
				MiddleCellRenderer.add(1, insatuFlgTCR);
				
				
				//------------------------------- �������Z�˗�Fg ------------------------
				//�R���|�[�l���g����
				CheckboxBase genkaIraiFg = new CheckboxBase();
				genkaIraiFg.setHorizontalAlignment(CheckboxBase.CENTER);
				//�f�[�^�ݒ�
				if(trialData.getFlg_shisanIrai() == 1){
					//insatuFg.setSelected(true);
					//�f�[�^�ݒ�
					table.setValueAt( "true", 2, j);
					
					//���Ɉ˗�����Ă���f�[�^�̏ꍇ
					if(trialData.getFlg_init() == 1){
						//�ҏW�s��
						genkaIraiFg.setEnabled(false);
					}
					
				}
				//���[�h�ҏW
				if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
						JwsConstManager.JWS_COMPONENT_0151, DataCtrl.getInstance().getParamData().getStrMode())){
					genkaIraiFg.setEnabled(false);
					
				}
				genkaIraiFg.setBackground(JwsConstManager.SHISAKU_ITEM_COLOR2);
				//�G�f�B�^�[�������_���[����
				DefaultCellEditor genkaIraiFgTCE = new DefaultCellEditor(genkaIraiFg);
				CheckBoxCellRenderer genkaIraiFgTCR = new CheckBoxCellRenderer(genkaIraiFg);
				//���ԃG�f�B�^�[�������_���[�֓o�^
				MiddleCellEditor.addEditorAt(2, genkaIraiFgTCE);
				MiddleCellRenderer.add(2, genkaIraiFgTCR);
				
				
				//--------------------------------- �[�U�ʐ���(g) ------------------------
				//�R���|�[�l���g����
				NumelicTextbox jutenSuisoTB = new NumelicTextbox();
				jutenSuisoTB.setEnabled(true);
				//���[�h�ҏW
				if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
						JwsConstManager.JWS_COMPONENT_0134, DataCtrl.getInstance().getParamData().getStrMode())){
					jutenSuisoTB.setEditable(false);
					
				}
				jutenSuisoTB.setBackground(JwsConstManager.SHISAKU_ITEM_COLOR2);
				
//add start -------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.33,34
				int intShisakuSeq = trialData.getIntShisakuSeq();
				//��L�[���ڎ擾
				boolean chk = DataCtrl.getInstance().getTrialTblData().checkShisakuIraiKakuteiFg(intShisakuSeq);
				//�ҏW�\�̏ꍇ�F��������
				if(chk){
					
				}
				//�ҏW�\�̏ꍇ�F��������
				else{
					jutenSuisoTB.setBackground(JwsConstManager.JWS_DISABLE_COLOR);
					jutenSuisoTB.setEditable(false);
				}
//add end   -------------------------------------------------------------------------------
				
				
				
				//--------------------------------- �[�U�ʖ���(g) ------------------------
				//�R���|�[�l���g����
				NumelicTextbox jutenYusoTB = new NumelicTextbox();
				jutenYusoTB.setEnabled(true);
				//���[�h�ҏW
				if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
						JwsConstManager.JWS_COMPONENT_0135, DataCtrl.getInstance().getParamData().getStrMode())){
					jutenYusoTB.setEditable(false);
					
				}
				jutenYusoTB.setBackground(JwsConstManager.SHISAKU_ITEM_COLOR2);
				
//add start -------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.33,34
				intShisakuSeq = trialData.getIntShisakuSeq();
				//��L�[���ڎ擾
				chk = DataCtrl.getInstance().getTrialTblData().checkShisakuIraiKakuteiFg(intShisakuSeq);
				//�ҏW�\�̏ꍇ�F��������
				if(chk){
					
				}
				//�ҏW�\�̏ꍇ�F��������
				else{
					jutenYusoTB.setBackground(JwsConstManager.JWS_DISABLE_COLOR);
					jutenYusoTB.setEditable(false);
				}
//add end   -------------------------------------------------------------------------------
				
//2011/05/11 QP@10181_No.42_49_72 TT T.Nishigawa Add Start -------------------------
				
				//�Q�ƃ��[�h�̏ꍇ�i�[�U�ʐ����j
				if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
						JwsConstManager.JWS_COMPONENT_0134, DataCtrl.getInstance().getParamData().getStrMode())){
					jutenSuisoTB.setEditable(false);
				}
				//�Q�ƃ��[�h�̏ꍇ�i�[�U�ʖ����j
				else if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
						JwsConstManager.JWS_COMPONENT_0135, DataCtrl.getInstance().getParamData().getStrMode())){
					jutenYusoTB.setEditable(false);
				}
				//�Q�ƃ��[�h�łȂ��ꍇ
				else{
					
					//��L�[���ڎ擾
					intShisakuSeq = trialData.getIntShisakuSeq();
					chk = DataCtrl.getInstance().getTrialTblData().checkShisakuIraiKakuteiFg(intShisakuSeq);
					
					//�ҏW�\�̏ꍇ�F��������
					if(chk){
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
							
							//�u�[�U�ʐ����v�͕ҏW�s��
							jutenSuisoTB.setEditable(false);
							//�u�[�U�ʖ����v�͕ҏW�s��
							jutenYusoTB.setEditable(false);
							
							jutenSuisoTB.setBackground(JwsConstManager.JWS_DISABLE_COLOR);
							jutenYusoTB.setBackground(JwsConstManager.JWS_DISABLE_COLOR);
							
						}
						//�H���p�^�[�����u�󔒁v�łȂ��ꍇ
						else{
							
							//�H���p�^�[����Value1�擾
							String ptValue = DataCtrl.getInstance().getLiteralDataKoteiPtn().selectLiteralVal1(ptKotei);
							//�H���p�^�[�����u�������P�t�^�C�v�v�̏ꍇ-------------------------------------------------------------
							if(ptValue.equals(JwsConstManager.JWS_KOTEITYPE_1)){
								
								//�u�[�U�ʐ����v�͕ҏW�s�i�[�U�ʌv�Z�j
								jutenSuisoTB.setEditable(false);
								//�u�[�U�ʖ����v�͕ҏW�s��
								jutenYusoTB.setEditable(false);
								
								jutenSuisoTB.setBackground(JwsConstManager.JWS_DISABLE_COLOR);
								jutenYusoTB.setBackground(JwsConstManager.JWS_DISABLE_COLOR);
							}
							//�H���p�^�[�����u�������Q�t�^�C�v�v�̏ꍇ-------------------------------------------------------------
							else if(ptValue.equals(JwsConstManager.JWS_KOTEITYPE_2)){
								
								//�u�[�U�ʐ����v�͕ҏW�s�i�v�Z�j
								jutenSuisoTB.setEditable(false);
								//�u�[�U�ʖ����v�͕ҏW�s�i�v�Z�j
								jutenYusoTB.setEditable(false);
								
								jutenSuisoTB.setBackground(JwsConstManager.JWS_DISABLE_COLOR);
								jutenYusoTB.setBackground(JwsConstManager.JWS_DISABLE_COLOR);
							}
							//�H���p�^�[�����u���̑��E���H�^�C�v�v�̏ꍇ-------------------------------------------------------------
							else if(ptValue.equals(JwsConstManager.JWS_KOTEITYPE_3)){
								
								//�u�[�U�ʐ����v�͕ҏW��
								jutenSuisoTB.setEditable(true);
								//�u�[�U�ʖ����v�͕ҏW��
								jutenYusoTB.setEditable(true);
								
								jutenSuisoTB.setBackground(JwsConstManager.SHISAKU_ITEM_COLOR2);
								jutenYusoTB.setBackground(JwsConstManager.SHISAKU_ITEM_COLOR2);
							}
						}
					}
				}
				
//2011/05/11 QP@10181_No.42_49_72 TT T.Nishigawa Add End   -------------------------
				
				//�Z���G�f�B�^&�����_������
				TextFieldCellEditor jutenSuisoTCE = new TextFieldCellEditor(jutenSuisoTB);
				TextFieldCellRenderer jutenSuisoTCR = new TextFieldCellRenderer(jutenSuisoTB);
				//���ԃG�f�B�^&�����_���֓o�^
				MiddleCellEditor.addEditorAt(3, jutenSuisoTCE);
				MiddleCellRenderer.add(3, jutenSuisoTCR);
				//�f�[�^�ݒ�
				table.setValueAt( costMaterialData.getStrZyutenSui(), 3, j);
				
				//�Z���G�f�B�^&�����_������
				TextFieldCellEditor jutenYusoTCE = new TextFieldCellEditor(jutenYusoTB);
				TextFieldCellRenderer jutenYusoTCR = new TextFieldCellRenderer(jutenYusoTB);
				//���ԃG�f�B�^&�����_���֓o�^
				MiddleCellEditor.addEditorAt(4, jutenYusoTCE);
				MiddleCellRenderer.add(4, jutenYusoTCR);
				//�f�[�^�ݒ�
				table.setValueAt( costMaterialData.getStrZyutenYu(), 4, j);
				
				
				//--------------------------------- �� ---------------------------------
				//�R���|�[�l���g����
				NumelicTextbox kuhakuTB = new NumelicTextbox();
				kuhakuTB.setEnabled(false);
				//�Z���G�f�B�^&�����_������
				TextFieldCellEditor kuhakuTCE = new TextFieldCellEditor(kuhakuTB);
				TextFieldCellRenderer kuhakuTCR = new TextFieldCellRenderer(kuhakuTB);
				//���ԃG�f�B�^&�����_���֓o�^
				MiddleCellEditor.addEditorAt(5, kuhakuTCE);
				MiddleCellRenderer.add(5, kuhakuTCR);
				
				//--------------------------------- ���v��(�P�{�Fg)(�e�ʁ~��d) ------------
				//�R���|�[�l���g����
				NumelicTextbox goukeiryoTB = new NumelicTextbox();
				goukeiryoTB.setEnabled(false);
				goukeiryoTB.setBackground(Color.WHITE);
				//�Z���G�f�B�^&�����_������
				TextFieldCellEditor goukeiryoTCE = new TextFieldCellEditor(goukeiryoTB);
				TextFieldCellRenderer goukeiryoTCR = new TextFieldCellRenderer(goukeiryoTB);
				//���ԃG�f�B�^&�����_���֓o�^
				MiddleCellEditor.addEditorAt(6, goukeiryoTCE);
				MiddleCellRenderer.add(6, goukeiryoTCR);
				//�f�[�^�ݒ�
				table.setValueAt( costMaterialData.getStrGokei(), 6, j);
				
				//--------------------------------- ������(kg) ---------------------------
				//�R���|�[�l���g����
				NumelicTextbox genryohiKgTB = new NumelicTextbox();
				genryohiKgTB.setEnabled(false);
				genryohiKgTB.setBackground(Color.WHITE);
				//�Z���G�f�B�^&�����_������
				TextFieldCellEditor genryohiKgTCE = new TextFieldCellEditor(genryohiKgTB);
				TextFieldCellRenderer genryohiKgTCR = new TextFieldCellRenderer(genryohiKgTB);
				//���ԃG�f�B�^&�����_���֓o�^
				MiddleCellEditor.addEditorAt(7, genryohiKgTCE);
				MiddleCellRenderer.add(7, genryohiKgTCR);
				//�f�[�^�ݒ�
				table.setValueAt( costMaterialData.getStrGenryohi(), 7, j);
				
				//--------------------------------- ������(1�{��) ------------------------
				//�R���|�[�l���g����
				NumelicTextbox genryohi1HonTB = new NumelicTextbox();
				genryohi1HonTB.setEnabled(false);
				genryohi1HonTB.setBackground(Color.WHITE);
				//�Z���G�f�B�^&�����_������
				TextFieldCellEditor genryohi1HonTCE = new TextFieldCellEditor(genryohi1HonTB);
				TextFieldCellRenderer genryohi1HonTCR = new TextFieldCellRenderer(genryohi1HonTB);
				//���ԃG�f�B�^&�����_���֓o�^
				MiddleCellEditor.addEditorAt(8, genryohi1HonTCE);
				MiddleCellRenderer.add(8, genryohi1HonTCR);
				//�f�[�^�ݒ�
				table.setValueAt( costMaterialData.getStrGenryohiTan(), 8, j);
				
				//--------------------------------- ��d ---------------------------------
				//�R���|�[�l���g����
				NumelicTextbox hijuTB = new NumelicTextbox();
				hijuTB.setEnabled(false);
				hijuTB.setBackground(Color.WHITE);
				//�Z���G�f�B�^&�����_������
				TextFieldCellEditor hijuTCE = new TextFieldCellEditor(hijuTB);
				TextFieldCellRenderer hijuTCR = new TextFieldCellRenderer(hijuTB);
				//���ԃG�f�B�^&�����_���֓o�^
				MiddleCellEditor.addEditorAt(9, hijuTCE);
				MiddleCellRenderer.add(9, hijuTCR);
				//�f�[�^�ݒ�
				table.setValueAt( costMaterialData.getStrHizyu(), 9, j);
				
				//--------------------------------- �e�� ---------------------------------
				//�R���|�[�l���g����
				NumelicTextbox yoryoTB = new NumelicTextbox();
				yoryoTB.setEnabled(false);
				yoryoTB.setBackground(Color.WHITE);
				//�Z���G�f�B�^&�����_������
				TextFieldCellEditor yoryoTCE = new TextFieldCellEditor(yoryoTB);
				TextFieldCellRenderer yoryoTCR = new TextFieldCellRenderer(yoryoTB);
				//���ԃG�f�B�^&�����_���֓o�^
				MiddleCellEditor.addEditorAt(10, yoryoTCE);
				MiddleCellRenderer.add(10, yoryoTCR);
				//�f�[�^�ݒ�
				table.setValueAt( costMaterialData.getStrYoryo(), 10, j);
				
				//--------------------------------- ���萔 -------------------------------
				//�R���|�[�l���g����
				NumelicTextbox irisuTB = new NumelicTextbox();
				irisuTB.setEnabled(false);
				irisuTB.setBackground(Color.WHITE);
				//�Z���G�f�B�^&�����_������
				TextFieldCellEditor irisuTCE = new TextFieldCellEditor(irisuTB);
				TextFieldCellRenderer irisuTCR = new TextFieldCellRenderer(irisuTB);
				//���ԃG�f�B�^&�����_���֓o�^
				MiddleCellEditor.addEditorAt(11, irisuTCE);
				MiddleCellRenderer.add(11, irisuTCR);
				//�f�[�^�ݒ�
				table.setValueAt( costMaterialData.getStrIrisu(), 11, j);
				
				//--------------------------------- �L������(%) --------------------------
				//�R���|�[�l���g����
				NumelicTextbox yukoBudomariTB = new NumelicTextbox();
				yukoBudomariTB.setEnabled(true);
				//���[�h�ҏW
				if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
						JwsConstManager.JWS_COMPONENT_0127, DataCtrl.getInstance().getParamData().getStrMode())){
					yukoBudomariTB.setEditable(false);
					
				}
				yukoBudomariTB.setBackground(JwsConstManager.SHISAKU_ITEM_COLOR2);
				//�Z���G�f�B�^&�����_������
				TextFieldCellEditor yukoBudomariTCE = new TextFieldCellEditor(yukoBudomariTB);
				TextFieldCellRenderer yukoBudomariTCR = new TextFieldCellRenderer(yukoBudomariTB);
				//���ԃG�f�B�^&�����_���֓o�^
				MiddleCellEditor.addEditorAt(12, yukoBudomariTCE);
				MiddleCellRenderer.add(12, yukoBudomariTCR);
				//�f�[�^�ݒ�
				table.setValueAt( costMaterialData.getStrYukoBudomari(), 12, j);
				
				//--------------------------------- �� ---------------------------------
				//���ԃG�f�B�^&�����_���֓o�^
				MiddleCellEditor.addEditorAt(13, kuhakuTCE);
				MiddleCellRenderer.add(13, kuhakuTCR);
				
				//--------------------------------- ���x����(g���e�ʁ~����) --------------
				//�R���|�[�l���g����
				NumelicTextbox lebelRyo1TB = new NumelicTextbox();
				lebelRyo1TB.setEnabled(false);
				lebelRyo1TB.setBackground(Color.WHITE);
				//�Z���G�f�B�^&�����_������
				TextFieldCellEditor lebelRyo1TCE = new TextFieldCellEditor(lebelRyo1TB);
				TextFieldCellRenderer lebelRyo1TCR = new TextFieldCellRenderer(lebelRyo1TB);
				//���ԃG�f�B�^&�����_���֓o�^
				MiddleCellEditor.addEditorAt(14, lebelRyo1TCE);
				MiddleCellRenderer.add(14, lebelRyo1TCR);
				//�f�[�^�ݒ�
				table.setValueAt( costMaterialData.getStrLevel(), 14, j);
				
//mod start --------------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.24
//				//--------------------------------- ��d���Z��(g���x���ʁ~��d) ------------
				//--------------------------------- ��d���Z��(g���Ϗ[�U�ʁ~��d) ------------
//mod end --------------------------------------------------------------------------------------
				//�R���|�[�l���g����
				NumelicTextbox lebelRyo2TB = new NumelicTextbox();
				lebelRyo2TB.setEnabled(false);
				lebelRyo2TB.setBackground(Color.WHITE);
				//�Z���G�f�B�^&�����_������
				TextFieldCellEditor lebelRyo2TCE = new TextFieldCellEditor(lebelRyo2TB);
				TextFieldCellRenderer lebelRyo2TCR = new TextFieldCellRenderer(lebelRyo2TB);
				//���ԃG�f�B�^&�����_���֓o�^
				MiddleCellEditor.addEditorAt(15, lebelRyo2TCE);
				MiddleCellRenderer.add(15, lebelRyo2TCR);
				//�f�[�^�ݒ�
				table.setValueAt( costMaterialData.getStrHizyuBudomari(), 15, j);
				
				//--------------------------------- ���Ϗ[�U��(g) ------------------------
				//�R���|�[�l���g����
				NumelicTextbox heikinJutenryoTB = new NumelicTextbox();
				heikinJutenryoTB.setEnabled(true);
				//���[�h�ҏW
				if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
						JwsConstManager.JWS_COMPONENT_0128, DataCtrl.getInstance().getParamData().getStrMode())){
					heikinJutenryoTB.setEditable(false);
					
				}
				heikinJutenryoTB.setBackground(JwsConstManager.SHISAKU_ITEM_COLOR2);
				//�Z���G�f�B�^&�����_������
				TextFieldCellEditor heikinJutenryoTCE = new TextFieldCellEditor(heikinJutenryoTB);
				TextFieldCellRenderer heikinJutenryoTCR = new TextFieldCellRenderer(heikinJutenryoTB);
				//���ԃG�f�B�^&�����_���֓o�^
				MiddleCellEditor.addEditorAt(16, heikinJutenryoTCE);
				MiddleCellRenderer.add(16, heikinJutenryoTCR);
				//�f�[�^�ݒ�
				table.setValueAt( costMaterialData.getStrZyutenAve(), 16, j);
				
				//--------------------------------- ������ (1c/s������) -------------------------------
				//�R���|�[�l���g����
				NumelicTextbox genryohiTB = new NumelicTextbox();
				genryohiTB.setEnabled(false);
				genryohiTB.setBackground(Color.WHITE);
				//�Z���G�f�B�^&�����_������
				TextFieldCellEditor genryohiTCE = new TextFieldCellEditor(genryohiTB);
				TextFieldCellRenderer genryohiTCR = new TextFieldCellRenderer(genryohiTB);
				//���ԃG�f�B�^&�����_���֓o�^
				MiddleCellEditor.addEditorAt(17, genryohiTCE);
				MiddleCellRenderer.add(17, genryohiTCR);
				//�f�[�^�ݒ�
				table.setValueAt( costMaterialData.getStrGenryohiCs(), 17, j);
				
				//--------------------------------- �ޗ��� -------------------------------
				//�R���|�[�l���g����
				NumelicTextbox zairyohiTB = new NumelicTextbox();
				zairyohiTB.setEnabled(true);
				//���[�h�ҏW
				if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
						JwsConstManager.JWS_COMPONENT_0145, DataCtrl.getInstance().getParamData().getStrMode())){
					zairyohiTB.setEditable(false);
					
				}
				zairyohiTB.setBackground(JwsConstManager.SHISAKU_ITEM_COLOR2);
				//�Z���G�f�B�^&�����_������
				TextFieldCellEditor zairyohiTCE = new TextFieldCellEditor(zairyohiTB);
				TextFieldCellRenderer zairyohiTCR = new TextFieldCellRenderer(zairyohiTB);
				//���ԃG�f�B�^&�����_���֓o�^
				MiddleCellEditor.addEditorAt(18, zairyohiTCE);
				MiddleCellRenderer.add(18, zairyohiTCR);
				//�f�[�^�ݒ�
				table.setValueAt( costMaterialData.getStrZairyohiCs(), 18, j);
				
				//--------------------------------- �Œ�� ---------------------------------
				//�R���|�[�l���g����
				NumelicTextbox keihiTB = new NumelicTextbox();
				keihiTB.setEnabled(true);
				//���[�h�ҏW
				if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
						JwsConstManager.JWS_COMPONENT_0129, DataCtrl.getInstance().getParamData().getStrMode())){
					keihiTB.setEditable(false);
					
				}
				keihiTB.setBackground(JwsConstManager.SHISAKU_ITEM_COLOR2);
				//�Z���G�f�B�^&�����_������
				TextFieldCellEditor keihiTCE = new TextFieldCellEditor(keihiTB);
				TextFieldCellRenderer keihiTCR = new TextFieldCellRenderer(keihiTB);
				//���ԃG�f�B�^&�����_���֓o�^
				MiddleCellEditor.addEditorAt(19, keihiTCE);
				MiddleCellRenderer.add(19, keihiTCR);
				//�f�[�^�ݒ�
				table.setValueAt( costMaterialData.getStrKeihiCs(), 19, j);
				
				//--------------------------------- �����v/cs ----------------------------
				//�R���|�[�l���g����
				NumelicTextbox genkakeiCsTB = new NumelicTextbox();
				genkakeiCsTB.setEnabled(false);
				genkakeiCsTB.setBackground(Color.WHITE);
				//�Z���G�f�B�^&�����_������
				TextFieldCellEditor genkakeiCsTCE = new TextFieldCellEditor(genkakeiCsTB);
				TextFieldCellRenderer genkakeiCsTCR = new TextFieldCellRenderer(genkakeiCsTB);
				//���ԃG�f�B�^&�����_���֓o�^
				MiddleCellEditor.addEditorAt(20, genkakeiCsTCE);
				MiddleCellRenderer.add(20, genkakeiCsTCR);
				//�f�[�^�ݒ�
				table.setValueAt( costMaterialData.getStrGenkakeiCs(), 20, j);
				
				//--------------------------------- �����v/�� ----------------------------
				//�R���|�[�l���g����
				NumelicTextbox genkakeiKoTB = new NumelicTextbox();
				genkakeiKoTB.setEnabled(false);
				genkakeiKoTB.setBackground(Color.WHITE);
				//�Z���G�f�B�^&�����_������
				TextFieldCellEditor genkakeiKoTCE = new TextFieldCellEditor(genkakeiKoTB);
				TextFieldCellRenderer genkakeiKoTCR = new TextFieldCellRenderer(genkakeiKoTB);
				//���ԃG�f�B�^&�����_���֓o�^
				MiddleCellEditor.addEditorAt(21, genkakeiKoTCE);
				MiddleCellRenderer.add(21, genkakeiKoTCR);
				//�f�[�^�ݒ�
				table.setValueAt( costMaterialData.getStrGenkakeiTan(), 21, j);
				
				//--------------------------------- ���� ---------------------------------
				//�R���|�[�l���g����
				NumelicTextbox baikaTB = new NumelicTextbox();
				baikaTB.setEnabled(false);
				baikaTB.setBackground(Color.WHITE);
				//�Z���G�f�B�^&�����_������
				TextFieldCellEditor baikaTCE = new TextFieldCellEditor(baikaTB);
				TextFieldCellRenderer baikaTCR = new TextFieldCellRenderer(baikaTB);
				//���ԃG�f�B�^&�����_���֓o�^
				MiddleCellEditor.addEditorAt(22, baikaTCE);
				MiddleCellRenderer.add(22, baikaTCR);
				//�f�[�^�ݒ�
				table.setValueAt( costMaterialData.getStrGenkakeiBai(), 22, j);
				
				//--------------------------------- �e��(%) ------------------------------
				//�R���|�[�l���g����
				HankakuTextbox arariTB = new HankakuTextbox();
				arariTB.setHorizontalAlignment(SwingConstants.RIGHT);
				arariTB.setEnabled(false);
				arariTB.setBackground(Color.WHITE);
				//�Z���G�f�B�^&�����_������
				TextFieldCellEditor arariTCE = new TextFieldCellEditor(arariTB);
				TextFieldCellRenderer arariTCR = new TextFieldCellRenderer(arariTB);
				//���ԃG�f�B�^&�����_���֓o�^
				MiddleCellEditor.addEditorAt(23, arariTCE);
				MiddleCellRenderer.add(23, arariTCR);
				//�f�[�^�ݒ�
				table.setValueAt( costMaterialData.getStrGenkakeiRi(), 23, j);
				
//2011/05/27 QP@10181_No.67 TT Nishigawa Change Start -------------------------
				
				//--------------------------------- �˗��L�����Z�� ------------------------
				//�R���|�[�l���g����
				CheckboxBase cancelFg = new CheckboxBase();
				cancelFg.setHorizontalAlignment(CheckboxBase.CENTER);
				//���[�h�ҏW
				if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
						JwsConstManager.JWS_COMPONENT_0151, DataCtrl.getInstance().getParamData().getStrMode())){
					//�ҏW�s��
					cancelFg.setEnabled(false);
				}
				else{
					//���Ɉ˗�����Ă���f�[�^�̏ꍇ
					if(trialData.getFlg_init() == 1){
						//�ҏW��
						cancelFg.setBackground(JwsConstManager.SHISAKU_ITEM_COLOR2);
					}
					//�˗�����Ă��Ȃ��ꍇ
					else{
						//�ҏW�s��
						cancelFg.setEnabled(false);
					}
				}
				//�G�f�B�^�[�������_���[����
				DefaultCellEditor cancelFlgTCE = new DefaultCellEditor(cancelFg);
				CheckBoxCellRenderer cancelFlgTCR = new CheckBoxCellRenderer(cancelFg);
				//���ԃG�f�B�^�[�������_���[�֓o�^
				MiddleCellEditor.addEditorAt(24, cancelFlgTCE);
				MiddleCellRenderer.add(24, cancelFlgTCR);
				
//2011/04/27 QP@10181_No.67 TT Nishigawa Change End   -------------------------
				
				//------------------------------- �e�[�u���J�����֐ݒ�  ---------------------------
				column.setCellEditor(MiddleCellEditor);
				column.setCellRenderer(MiddleCellRenderer);
				
			}

		} catch (ExceptionBase e) {
			throw e;
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
			//�G���[�ݒ�
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			//2011/04/13 QP@10181_No.26 TT T.Satoh Change Start -------------------------
			//ex.setStrErrmsg("�������Z�D �e�[�u���������������������s���܂���");
			ex.setStrErrmsg("�������Z �e�[�u�����������������s���܂���");
			//2011/04/13 QP@10181_No.26 TT T.Satoh Change End ---------------------------
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getMessage());
			throw ex;
			
		} finally {
			//�ϐ��̍폜
			trialData = null;
			costMaterialData = null;
			aryCostMaterialData = null;
			
		}		
		
	}
	
	/************************************************************************************
	 * 
	 *  ���Z�m��T���v��No�R���{�{�b�N�X�ݒ�
	 *   @author TT katayama
	 *   @throws ExceptionBase 
	 *   
	 ************************************************************************************/
	private void setShisanKakuteiCmb() throws ExceptionBase {

		try {
			
			//���Z�m��f�[�^���擾
			ArrayList aryShisanKakutei = DataCtrl.getInstance().getShisanRirekiKanriData().getAryShisanKakuteiData();
			
			//�R���{�{�b�N�X������
			cmbShisanKakutei.removeAllItems();
			
			//�R���{�{�b�N�X�ɃT���v��No��ݒ�
			for ( int i =0; i<aryShisanKakutei.size(); i++ ) {				
				ShisanData shisanData = (ShisanData)aryShisanKakutei.get(i);
				
				if ( shisanData.getStrSampleNo() != null ) {
					cmbShisanKakutei.addItem(shisanData.getStrSampleNo());
					
				} else {
					cmbShisanKakutei.addItem("");
					
				}
				
			}
			
		} catch (Exception e) {
			//�G���[�ݒ�
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			//2011/04/13 QP@10181_No.26 TT T.Satoh Change Start -------------------------
			//ex.setStrErrmsg("�������Z�D ���Z�m��T���v��No�R���{�{�b�N�X�ݒ菈�������s���܂���");
			ex.setStrErrmsg("�������Z ���Z�m��T���v��No�R���{�{�b�N�X�ݒ菈�������s���܂���");
			//2011/04/13 QP@10181_No.26 TT T.Satoh Change End ---------------------------
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getMessage());
			throw ex;
			
		} finally {
			
		}
		
	}

	/************************************************************************************
	 * 
	 *  ���Z�m��T���v��No�R���{�{�b�N�X�ݒ�
	 *   @author TT katayama
	 *   @throws ExceptionBase 
	 *   
	 ************************************************************************************/
	public void updDispValues(int intSeq, int column) throws ExceptionBase {

		try {
			
			//���Z�f�[�^�̎擾
			CostMaterialData genkaData = (CostMaterialData)DataCtrl.getInstance().getTrialTblData().SearchGenkaGenryoData(intSeq).get(0);

			//3 : �[�U�ʐ���
			table.setValueAt(genkaData.getStrZyutenSui(), 3, column);

			//4 : �[�U�ʖ���
			table.setValueAt(genkaData.getStrZyutenYu(), 4, column);

			//6 : ���v��
			table.setValueAt(genkaData.getStrGokei(), 6, column);
									
			//7 : ������(kg)
			table.setValueAt(genkaData.getStrGenryohi(), 7, column);
			
			//8 : ������(�P�{��)
			table.setValueAt(genkaData.getStrGenryohiTan(), 8, column);

			//9 : ��d
			table.setValueAt(genkaData.getStrHizyu(), 9, column);

			//10 : �e��
			table.setValueAt(genkaData.getStrYoryo(), 10, column);

			//11 : ����
			table.setValueAt(genkaData.getStrIrisu(), 11, column);

			//12 : �L������
			table.setValueAt(genkaData.getStrYukoBudomari(), 12, column);
			
			//14 : ���x����
			table.setValueAt(genkaData.getStrLevel(), 14, column);
			
			//15 : ��d���Z��
			table.setValueAt(genkaData.getStrHizyuBudomari(), 15, column);
			
			//16 : ���Ϗ[�U��
			table.setValueAt(genkaData.getStrZyutenAve(), 16, column);
			
			//17 : ������/cs
			table.setValueAt(genkaData.getStrGenryohiCs(), 17, column);
			
			//18 : �ޗ���
			table.setValueAt(genkaData.getStrZairyohiCs(), 18, column);

			//19 : �Œ��
			table.setValueAt(genkaData.getStrKeihiCs(), 19, column);
						
			//20 : �����v/cs
			table.setValueAt(genkaData.getStrGenkakeiCs(), 20, column);
			
			//21 : �����n/��
			table.setValueAt(genkaData.getStrGenkakeiTan(), 21, column);

			//22 : ����
			table.setValueAt(genkaData.getStrGenkakeiBai(), 22, column);
			
			//23 : �e��/��
			table.setValueAt(genkaData.getStrGenkakeiRi(), 23, column);			
			
		} catch (Exception e) {
			e.printStackTrace();
			
			//�G���[�ݒ�
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			//2011/04/13 QP@10181_No.26 TT T.Satoh Change Start -------------------------
			//ex.setStrErrmsg("�������Z�D ���Z�m��T���v��No�R���{�{�b�N�X�ݒ菈�������s���܂���");
			ex.setStrErrmsg("�������Z ���Z�m��T���v��No�R���{�{�b�N�X�ݒ菈�������s���܂���");
			//2011/04/13 QP@10181_No.26 TT T.Satoh Change End ---------------------------
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getMessage());
			throw ex;
			
		} finally {
			
		}
		
	}

	/************************************************************************************
	 * 
	 *  �C�x���g�����̎擾
	 *   @return ActionListener�N���X
	 *   @author TT katayama
	 *   
	 ************************************************************************************/
	private ActionListener getActionEvent() {
		
		return new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				
				String eventNm = e.getActionCommand();
				
				try {
					//���Z�����Q�ƃ{�^������������
					if ( eventNm.equals(CLICK_SHISAN_RIREKI) ) {					
						
						//JW850 : ���Z�����Q��
						con_JW850();
						
					}
					
				} catch (ExceptionBase eb) {
					DataCtrl.getInstance().PrintMessage(eb);
					
				} catch (Exception ec) {
					//�G���[�ݒ�
					ex = new ExceptionBase();
					ex.setStrErrCd("");
					//2011/04/13 QP@10181_No.26 TT T.Satoh Change Start -------------------------
					//ex.setStrErrmsg("�������Z�D �C�x���g���������s���܂���");
					ex.setStrErrmsg("�������Z �C�x���g���������s���܂���");
					//2011/04/13 QP@10181_No.26 TT T.Satoh Change End ---------------------------
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
	 *  JW840 ���Z�m��T���v��No��������
	 *   @author TT katayama
	 *   @throws ExceptionBase 
	 *   
	 ************************************************************************************/
	private void con_JW840() throws ExceptionBase {
		
		try {

			//------------------------------ ���M�p�����[�^�i�[  ------------------------
			String strUser = DataCtrl.getInstance().getParamData().getDciUser().toString();

			//------------------------------ ���MXML�f�[�^�쐬  ------------------------
			xmlJW840 = new XmlData();
			ArrayList arySetTag = new ArrayList();

			//------------------------------ Root�ǉ�  ---------------------------------
			xmlJW840.AddXmlTag("","JW840");
			arySetTag.clear();

			//------------------------------ �@�\ID�ǉ��iUSEERINFO�j  -------------------
			xmlJW840.AddXmlTag("JW840", "USERINFO");
			//�@�e�[�u���^�O�ǉ�
			xmlJW840.AddXmlTag("USERINFO", "table");
			//�@���R�[�h�ǉ�
			arySetTag.add(new String[]{"kbn_shori", "3"});
			arySetTag.add(new String[]{"id_user",strUser});
			xmlJW840.AddXmlTag("table", "rec", arySetTag);
			//�z�񏉊���
			arySetTag.clear();

			//------------------------------ �@�\ID�ǉ��iSA830�j  -------------------
			xmlJW840.AddXmlTag("JW840", "SA830");
			//�@�e�[�u���^�O�ǉ�
			xmlJW840.AddXmlTag("SA830", "table");
			//�@���R�[�h�ǉ�
			arySetTag.add(new String[]{"cd_shain", DataCtrl.getInstance().getParamData().getStrSisaku_user()});
			arySetTag.add(new String[]{"nen", DataCtrl.getInstance().getParamData().getStrSisaku_nen()});
			arySetTag.add(new String[]{"no_oi", DataCtrl.getInstance().getParamData().getStrSisaku_oi()});
			xmlJW840.AddXmlTag("table", "rec", arySetTag);
			//�z�񏉊���
			arySetTag.clear();

			//----------------------------------- XML���M  ----------------------------------
//			System.out.println("JW840���MXML===============================================================");
//			xmlJW840.dispXml();
			xcon = new XmlConnection(xmlJW840);
			xcon.setStrAddress(DataCtrl.getInstance().getJnlpConnect().getStrAddressAjax());
			xcon.XmlSend();
			
			//----------------------------------- XML��M  ----------------------------------
			xmlJW840 = xcon.getXdocRes();

//			System.out.println("JW840��MXML===============================================================");
//			xmlJW840.dispXml();
//			System.out.println();

			//---------------------------- Result�f�[�^�ݒ�(RESULT)  -----------------------------
			DataCtrl.getInstance().getResultData().setResultData(xmlJW840);
			
			// Result�f�[�^.�������ʂ�true�̏ꍇ�AExceptionBase��Throw����
			if ( !DataCtrl.getInstance().getResultData().isReturnFlgCheck() ) {
				//���������s�����ꍇ
//				throw new ExceptionBase();
				
			} else {
				//���������������ꍇ

				//------------------------------ �f�[�^�ݒ�(SA830) --------------------------------
				DataCtrl.getInstance().getShisanRirekiKanriData().setShisanKakuteiNoData(this.xmlJW840);
				
			}

			
		} catch (ExceptionBase e) {
			throw e;
			
		} catch (Exception e) {
			//�G���[�ݒ�
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			//2011/04/13 QP@10181_No.26 TT T.Satoh Change Start -------------------------
			//ex.setStrErrmsg("�������Z�D �����\�����������s���܂���");
			ex.setStrErrmsg("�������Z �����\�����������s���܂���");
			//2011/04/13 QP@10181_No.26 TT T.Satoh Change End ---------------------------
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getMessage());
			throw ex;
			
		} finally {
			
		}
		
	}

	/************************************************************************************
	 * 
	 *  JW850 ���Z�����Q�ƃ{�^�� ����������
	 *   @author TT katayama
	 *   @throws ExceptionBase 
	 *   
	 ************************************************************************************/
	private void con_JW850() throws ExceptionBase {
		
		try {

			//------------------------------ ���M�p�����[�^�i�[  ------------------------
			String strUser = DataCtrl.getInstance().getParamData().getDciUser().toString();

			//------------------------------ ���MXML�f�[�^�쐬  ------------------------
			xmlJW850 = new XmlData();
			ArrayList arySetTag = new ArrayList();

			//------------------------------ Root�ǉ�  ---------------------------------
			xmlJW850.AddXmlTag("","JW850");
			arySetTag.clear();

			//------------------------------ �@�\ID�ǉ��iUSEERINFO�j  -------------------
			xmlJW850.AddXmlTag("JW850", "USERINFO");
			//�@�e�[�u���^�O�ǉ�
			xmlJW850.AddXmlTag("USERINFO", "table");
			//�@���R�[�h�ǉ�
			arySetTag.add(new String[]{"kbn_shori", "3"});
			arySetTag.add(new String[]{"id_user",strUser});
			xmlJW850.AddXmlTag("table", "rec", arySetTag);
			//�z�񏉊���
			arySetTag.clear();

			//------------------------------ �@�\ID�ǉ��iSA840�j  -------------------
			xmlJW850.AddXmlTag("JW850", "SA840");
			//�@�e�[�u���^�O�ǉ�
			xmlJW850.AddXmlTag("SA840", "table");
			//�@���R�[�h�ǉ�
			arySetTag.add(new String[]{"cd_shain", DataCtrl.getInstance().getParamData().getStrSisaku_user()});
			arySetTag.add(new String[]{"nen", DataCtrl.getInstance().getParamData().getStrSisaku_nen()});
			arySetTag.add(new String[]{"no_oi", DataCtrl.getInstance().getParamData().getStrSisaku_oi()});;		
			xmlJW850.AddXmlTag("table", "rec", arySetTag);
			//�z�񏉊���
			arySetTag.clear();

			//----------------------------------- XML���M  ----------------------------------
//			System.out.println("\nJW850���MXML===============================================================");
//			xmlJW850.dispXml();
			xcon = new XmlConnection(xmlJW850);
			xcon.setStrAddress(DataCtrl.getInstance().getJnlpConnect().getStrAddressAjax());
			xcon.XmlSend();
			
			//----------------------------------- XML��M  ----------------------------------
			xmlJW850 = xcon.getXdocRes();
//			System.out.println("\nJW850��MXML===============================================================");
//			xmlJW850.dispXml();

			//---------------------------- Result�f�[�^�ݒ�(RESULT)  -----------------------------
			DataCtrl.getInstance().getResultData().setResultData(xmlJW850);
			
			// Result�f�[�^.�������ʂ�true�̏ꍇ�AExceptionBase��Throw����
			if ( !DataCtrl.getInstance().getResultData().isReturnFlgCheck() ) {	
				throw new ExceptionBase();
			} else {
				//�������ʂ����݂����ꍇ

				//------------------------------ �f�[�^�ݒ�(SA840) --------------------------------
				DataCtrl.getInstance().getShisanRirekiKanriData().setShisanRirekiData(this.xmlJW850);
			
				//���Z�����m�F���b�Z�[�W��\��
				DataCtrl.getInstance().getMessageCtrl().PrintMessageShisanRirekiSansyo();
				
			}
				
			
		} catch (ExceptionBase e) {
			throw e;
			
		} catch (Exception e) {
			//�G���[�ݒ�
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			//2011/04/13 QP@10181_No.26 TT T.Satoh Change Start -------------------------
			//ex.setStrErrmsg("�������Z�D ���Z�����Q�ƃ{�^�����������������s���܂���");
			ex.setStrErrmsg("�������Z ���Z�����Q�ƃ{�^�����������������s���܂���");
			//2011/04/13 QP@10181_No.26 TT T.Satoh Change End ---------------------------
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getMessage());
			throw ex;
			
		} finally {
			
		}
		
	}

	/************************************************************************************
	 * 
	 *   ���Z���������̔ԁ@XML�ʐM�����iJ010�j
	 *    :  ���Z���������̔ԏ���XML�f�[�^�ʐM�iJ010�j���s��
	 *   @return �V�K���s�R�[�h
	 *   @author TT katayama
	 *   @throws ExceptionBase 
	 *   
	 ************************************************************************************/
	private String conJ010() throws ExceptionBase{
		
		String ret = "";
		
		try{
			
			//--------------------------- ���M�p�����[�^�i�[  ---------------------------------
			String strUser = DataCtrl.getInstance().getParamData().getDciUser().toString();
			
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
			arySetTag.add(new String[]{"kbn_shori", "shisan_rireki"});
			xmlJ010.AddXmlTag("table", "rec", arySetTag);
			//�z�񏉊���
			arySetTag.clear();
			
			//----------------------------------- XML���M  ----------------------------------
			//System.out.println("\nJ010���MXML===============================================================");
			//xmlJ010.dispXml();
			xcon = new XmlConnection(xmlJ010);
			xcon.setStrAddress(DataCtrl.getInstance().getJnlpConnect().getStrAddressAjax());
			xcon.XmlSend();
			
			//----------------------------------- XML��M  ----------------------------------
			xmlJ010 = xcon.getXdocRes();
			//System.out.println("\nJ010��MXML===============================================================");
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
							ret = recVal;
							
						}
						
					}
					
				}
				
			}else{
				ExceptionBase ex  = new ExceptionBase();
				throw ex;
				
			}
			
		}catch(ExceptionBase eb){
			throw eb;
			
		}catch(Exception e){
			//�G���[�ݒ�
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			//2011/04/13 QP@10181_No.26 TT T.Satoh Change Start -------------------------
			//ex.setStrErrmsg("�������Z�D ���Z���������̔ԏ��������s���܂���");
			ex.setStrErrmsg("�������Z ���Z���������̔ԏ��������s���܂���");
			//2011/04/13 QP@10181_No.26 TT T.Satoh Change End ---------------------------
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getMessage());
			throw ex;
			
		}finally{
			
		}
		return ret;
		
	}

	/************************************************************************************
	 * 
	 *  JW860 �������Z�o�^�{�^�� ����������
	 *   @param strRirekiNo : ������
	 *   @author TT katayama
	 *   @throws ExceptionBase 
	 *   
	 ************************************************************************************/
	private void con_JW860(String strRirekiNo) throws ExceptionBase {
			
		try {
						
			//------------------------------ ���M�p�����[�^�i�[  ------------------------
			
			//���[�UID
			String strUser = DataCtrl.getInstance().getParamData().getDciUser().toString();
			
			//���.���Z�m��T���v��No.�R���{�{�b�N�X��莎��SEQ�ƃT���v��No���擾����
			ShisanData shisanKakutei = DataCtrl.getInstance().getShisanRirekiKanriData().SearchShisanKakuteiData(this.cmbShisanKakutei.getSelectedIndex());
			String strShisakuSeq = Integer.toString(shisanKakutei.getIntShisakuSeq());
			
			//�T���v��NO
			String strSampleNo = "";
			if ( shisanKakutei.getStrSampleNo() != null ) {
				strSampleNo = shisanKakutei.getStrSampleNo();
				
			}
						
			//------------------------------ ���MXML�f�[�^�쐬  ------------------------
			xmlJW860 = new XmlData();
			ArrayList arySetTag = new ArrayList();

			//------------------------------ Root�ǉ�  ---------------------------------
			xmlJW860.AddXmlTag("","JW860");
			arySetTag.clear();

			//------------------------------ �@�\ID�ǉ��iUSEERINFO�j  -------------------
			xmlJW860.AddXmlTag("JW860", "USERINFO");
			//�@�e�[�u���^�O�ǉ�
			xmlJW860.AddXmlTag("USERINFO", "table");
			//�@���R�[�h�ǉ�
			arySetTag.add(new String[]{"kbn_shori", "3"});
			arySetTag.add(new String[]{"id_user",strUser});
			xmlJW860.AddXmlTag("table", "rec", arySetTag);
			//�z�񏉊���
			arySetTag.clear();

			//------------------------------ �@�\ID�ǉ��iSA820�j  -------------------
			//���Z�m�藚��o�^����
			xmlJW860.AddXmlTag("JW860", "SA820");
			//�@�e�[�u���^�O�ǉ�
			xmlJW860.AddXmlTag("SA820", "table");
			//�@���R�[�h�ǉ�
			arySetTag.add(new String[]{"cd_shain", DataCtrl.getInstance().getParamData().getStrSisaku_user()});
			arySetTag.add(new String[]{"nen", DataCtrl.getInstance().getParamData().getStrSisaku_nen()});
			arySetTag.add(new String[]{"no_oi", DataCtrl.getInstance().getParamData().getStrSisaku_oi()});
			arySetTag.add(new String[]{"seq_shisaku", strShisakuSeq});			//����SEQ
			arySetTag.add(new String[]{"nm_sample", strSampleNo});				//�T���v��No
			arySetTag.add(new String[]{"sort_rireki", strRirekiNo});				//������
			
			xmlJW860.AddXmlTag("table", "rec", arySetTag);
			//�z�񏉊���
			arySetTag.clear();
			
			//----------------------------------- XML���M  ----------------------------------
//			System.out.println("\nJW860���MXML===============================================================");
//			xmlJW860.dispXml();
			xcon = new XmlConnection(xmlJW860);
			xcon.setStrAddress(DataCtrl.getInstance().getJnlpConnect().getStrAddressAjax());
			xcon.XmlSend();
			
			//----------------------------------- XML��M  ----------------------------------
			xmlJW860 = xcon.getXdocRes();
//			System.out.println("\nJW860��MXML===============================================================");
//			xmlJW860.dispXml();

			//---------------------------- Result�f�[�^�ݒ�(RESULT)  -----------------------------
			DataCtrl.getInstance().getResultData().setResultData(xmlJW860);
			
			// Result�f�[�^.�������ʂ�true�̏ꍇ�AExceptionBase��Throw����
			if ( !DataCtrl.getInstance().getResultData().isReturnFlgCheck() ) {	
				throw new ExceptionBase();
				
			} else {
				//�G���[�ł͂Ȃ��ꍇ
				
				//�m�F���b�Z�[�W��\������
				DataCtrl.getInstance().getMessageCtrl().PrintMessageString("����Ɍ������Z�o�^�������������܂����B");
				
				//�ŏI���Z�m��f�[�^��ݒ肷��
				DataCtrl.getInstance().getShisanRirekiKanriData().SetLastShisanData(Integer.parseInt(strShisakuSeq));
				
			}

		} catch (ExceptionBase e) {
			throw e;
			
		} catch (Exception e) {
			//�G���[�ݒ�
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			//2011/04/13 QP@10181_No.26 TT T.Satoh Change Start -------------------------
			//ex.setStrErrmsg("�������Z�D �������Z�o�^�{�^�����������������s���܂���");
			ex.setStrErrmsg("�������Z �������Z�o�^�{�^�����������������s���܂���");
			//2011/04/13 QP@10181_No.26 TT T.Satoh Change End ---------------------------
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getMessage());
			throw ex;
			
		} finally {
			
		}
				
	}

	/************************************************************************************
	 * 
	 *  JW830 �������Z�\����{�^�� ����������
	 *   @param intKoteiVal : �I������Ă���H���̃��e�����l1(�s���ł͂Ȃ��l)
	 *   @author TT katayama
	 *   @throws ExceptionBase 
	 *   
	 ************************************************************************************/
	private void con_JW830(int intKoteiVal) throws ExceptionBase {
		
		//����SEQ�J�E���g
		int intShisakuSeqCnt = 0;
		//����SEQ �i�[�p�z��
		String[] aryShisakuSeq = null;
		//�d�オ�荇�v�d�� �i�[�p�z��
		String[] aryShiagariJuryo = null;
		//����e�[�u���f�[�^���X�g
		ArrayList aryCostMaterialData = null;
		
		try {
			
			int i;
			
			//------------------------------ ���M�p�����[�^�i�[  ------------------------
			
			//���[�UID
			String strUser = DataCtrl.getInstance().getParamData().getDciUser().toString();
			
			//���Fg�ɂ�鎎��f�[�^�ݒ�(�ő�3����)
			
			//����SEQ�J�E���g ������
			intShisakuSeqCnt = 0;
			//����SEQ �i�[�p�z�� ����
			aryShisakuSeq = new String[3];
			//�d�オ�荇�v�d�� �i�[�p�z�� ����
			aryShiagariJuryo = new String[3];
			//����e�[�u���f�[�^���X�g �擾
			aryCostMaterialData = DataCtrl.getInstance().getTrialTblData().SearchGenkaGenryoData(0);

			//�z��̏�����
			aryShisakuSeq[0] = "";
			aryShisakuSeq[1] = "";
			aryShisakuSeq[2] = "";
			aryShiagariJuryo[0] = "";
			aryShiagariJuryo[1] = "";
			aryShiagariJuryo[2] = "";
			
			//����f�[�^��ݒ肷��
			for ( i=0; i<aryCostMaterialData.size(); i++ ) {
				
				//���������e�[�u���f�[�^�擾
				CostMaterialData costMaterialData = (CostMaterialData)aryCostMaterialData.get(i);
				
				//���Fg�`�F�b�N
				if ( costMaterialData.getIntinsatu() == 1 ) {
					
					//����SEQ�J�E���g��i�߂�
					intShisakuSeqCnt++;
					if ( intShisakuSeqCnt > 3 ) {
						//�ݒ�ł��鎎��SEQ�͂R�܂�
						break;
					}
					
					
					//����SEQ��ݒ�
					aryShisakuSeq[intShisakuSeqCnt-1] = Integer.toString(costMaterialData.getIntShisakuSeq());
					
					//����e�[�u�����A�d�オ�荇�v�d�ʂ�ݒ�
//					TrialData trialData = (TrialData)DataCtrl.getInstance().getTrialTblData().SearchShisakuRetuData(costMaterialData.getIntShisakuSeq()).get(0);
//					if ( trialData.getDciShiagari() != null ) {
//						aryShiagariJuryo[intShisakuSeqCnt-1] = trialData.getDciShiagari().toString();
//						
//					}
				}
				
			}
			
			
			
				//�\�[�gSEQ�z��
			    String[] setArySeq = new String[3];
			    setArySeq[0] = "";
			    setArySeq[1] = "";
			    setArySeq[2] = "";
			    
			    //�����f�[�^�擾
				ArrayList aryTrialData = DataCtrl.getInstance().getTrialTblData().SearchShisakuRetuData(0);
				
				//�i�[�J�E���g
				int m=0;
				
				//����񐔕����[�v
				for(int l=1; l <= aryTrialData.size(); l++){
					
					//�������񐔕����[�v
					for(int n=0; n < aryShisakuSeq.length; n++){
						
						//�������SEQ��NULL�łȂ��ꍇ
						if(aryShisakuSeq[n] != null && aryShisakuSeq[n].length() > 0){
							
							//�������SEQ�擾
							int intSeq = Integer.parseInt(aryShisakuSeq[n]);
							
							//��������f�[�^�擾
							TrialData trialData1 = (TrialData)DataCtrl.getInstance().getTrialTblData().SearchShisakuRetuData(intSeq).get(0);
							
							//�������\�[�g���擾
							int intSort = trialData1.getIntHyojiNo();
							
							//�񐔃J�E���g�ƈ������\�[�g���������ꍇ
							if(l == intSort ){
								
								//�\�[�gSEQ�z��֊i�[
								setArySeq[m] = Integer.toString(intSeq);
								
								if ( trialData1.getDciShiagari() != null ) {
									aryShiagariJuryo[m] = trialData1.getDciShiagari().toString();
									
								}
								
								//�i�[�J�E���g+1
								m++;
							}
							
						}else{
							
							
						}
					}
				}
				
				//�e�X�g�\��
//				for(int o=0; o<setArySeq.length; o++){
//					System.out.println("����SEQ"+setArySeq[o]);
//					System.out.println("�d�オ��"+aryShiagariJuryo[o]);
//				}
				
			
			
			
			
			
			
			
			
			//------------------------------ ���MXML�f�[�^�쐬  ------------------------
			xmlJW830 = new XmlData();
			ArrayList arySetTag = new ArrayList();

			//------------------------------ Root�ǉ�  ---------------------------------
			xmlJW830.AddXmlTag("","JW830");
			arySetTag.clear();

			//------------------------------ �@�\ID�ǉ��iUSEERINFO�j  -------------------
			xmlJW830.AddXmlTag("JW830", "USERINFO");
			//�@�e�[�u���^�O�ǉ�
			xmlJW830.AddXmlTag("USERINFO", "table");
			//�@���R�[�h�ǉ�
			arySetTag.add(new String[]{"kbn_shori", "3"});
			arySetTag.add(new String[]{"id_user",strUser});
			xmlJW830.AddXmlTag("table", "rec", arySetTag);
			//�z�񏉊���
			arySetTag.clear();

			//------------------------------ �@�\ID�ǉ��iSA800�j  -------------------
			xmlJW830.AddXmlTag("JW830", "SA800");
			//�@�e�[�u���^�O�ǉ�
			xmlJW830.AddXmlTag("SA800", "table");
			//�@���R�[�h�ǉ�
			arySetTag.add(new String[]{"cd_shain", DataCtrl.getInstance().getParamData().getStrSisaku_user()});
			arySetTag.add(new String[]{"nen", DataCtrl.getInstance().getParamData().getStrSisaku_nen()});
			arySetTag.add(new String[]{"no_oi", DataCtrl.getInstance().getParamData().getStrSisaku_oi()});
			//����SEQ
			for ( i=0; i<3; i++ ) {
				
				System.out.println(setArySeq[i]);
				
				arySetTag.add(new String[]{"seq_shisaku" + (i+1), setArySeq[i]});
				arySetTag.add(new String[]{"juryo_shiagari" + (i+1), aryShiagariJuryo[i]});
				
			}
			//�H���l
			arySetTag.add(new String[]{"kotei_value", Integer.toString(intKoteiVal)});
			
			xmlJW830.AddXmlTag("table", "rec", arySetTag);
			//�z�񏉊���
			arySetTag.clear();

			//------------------------------ �@�\ID�ǉ��iSA870�j  -------------------
			xmlJW830.AddXmlTag("JW830", "SA870");
			//�@�e�[�u���^�O�ǉ�
			xmlJW830.AddXmlTag("SA870", "table");
			//�@���R�[�h�ǉ�
			arySetTag.add(new String[]{"cd_shain", DataCtrl.getInstance().getParamData().getStrSisaku_user()});
			arySetTag.add(new String[]{"nen", DataCtrl.getInstance().getParamData().getStrSisaku_nen()});
			arySetTag.add(new String[]{"no_oi", DataCtrl.getInstance().getParamData().getStrSisaku_oi()});
			//����SEQ
			for ( i=0; i<3; i++ ) {
				arySetTag.add(new String[]{"seq_shisaku" + (i+1), setArySeq[i]});
				
			}
			
			xmlJW830.AddXmlTag("table", "rec", arySetTag);
			//�z�񏉊���
			arySetTag.clear();

			//----------------------------------- XML���M  ----------------------------------
//			System.out.println("\nJW830���MXML===============================================================");
//			xmlJW830.dispXml();
			xcon = new XmlConnection(xmlJW830);
			xcon.setStrAddress(DataCtrl.getInstance().getJnlpConnect().getStrAddressAjax());
			xcon.XmlSend();
			
			//----------------------------------- XML��M  ----------------------------------
			xmlJW830 = xcon.getXdocRes();
//			System.out.println("\nJW830��MXML===============================================================");
//			xmlJW830.dispXml();

			//---------------------------- Result�f�[�^�ݒ�(RESULT)  -----------------------------
			DataCtrl.getInstance().getResultData().setResultData(xmlJW830);
			
			// Result�f�[�^.�������ʂ�true�̏ꍇ�AExceptionBase��Throw����
			if ( !DataCtrl.getInstance().getResultData().isReturnFlgCheck() ) {	
				throw new ExceptionBase();
			}

			//------------------------------ �f�[�^�ݒ�(SA800) --------------------------------
			//�_�E�����[�h�p�X�N���X
			DownloadPathData downloadPathData = new DownloadPathData();
			downloadPathData.setDownloadPathData(xmlJW830, "SA800");
			
			//URL�R�l�N�V�����N���X
			UrlConnection urlConnection = new UrlConnection();
			
			//�_�E�����[�h�p�X�𑗂�A�t�@�C���_�E�����[�h��ʂŊJ��
			urlConnection.urlFileDownLoad( downloadPathData.getStrDownloadPath());
			
			//------------------------------ �f�[�^�ݒ�(�������ZNo) --------------------------------
			for ( i=0; i<3; i++ ) {
				
				//����SEQ���i�[����Ă���ꍇ�A�������ZNo��ݒ肷��
				if ( !aryShisakuSeq[i].toString().equals("") ) {
					
					//����SEQ���擾
					int intShisakuSeq =Integer.parseInt(aryShisakuSeq[i]); 
					
					//�������ZNo��ݒ肷��(1�`3)
					this.setGenkaShisanNo( intShisakuSeq, i + 1);
					
				}			
				
			}

		} catch (ExceptionBase e) {
			throw e;
			
		} catch (Exception e) {
			//�G���[�ݒ�
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			//2011/04/13 QP@10181_No.26 TT T.Satoh Change Start -------------------------
			//ex.setStrErrmsg("�������Z�D �������Z�\����{�^�����������������s���܂���");
			ex.setStrErrmsg("�������Z �������Z�\����{�^�����������������s���܂���");
			//2011/04/13 QP@10181_No.26 TT T.Satoh Change End ---------------------------
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getMessage());
			throw ex;
			
		} finally {
			//�ϐ��̍폜
			intShisakuSeqCnt = 0;
			aryShisakuSeq = null;
			aryCostMaterialData = null;
			
		}
		
	}

	/************************************************************************************
	 * 
	 *  ���Z�m��T���v��No�R���{�{�b�N�X�@�X�V����
	 *   @author TT katayama
	 *   @throws ExceptionBase 
	 *   
	 ************************************************************************************/
	public void updShisanSampleNo() throws ExceptionBase {
		
		try {
			
			//��������
			this.con_JW840();
			
			//�R���{�{�b�N�X����������
			this.setShisanKakuteiCmb();
			
			//�g�p�ɐݒ�
			this.cmbShisanKakutei.setEnabled(true);
			this.btnShisanToroku.setEnabled(true);
			this.btnShisanRireki.setEnabled(true);
			
		} catch (ExceptionBase e) {
			throw e;
			
		} catch (Exception e) {
			//�G���[�ݒ�
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			//2011/04/13 QP@10181_No.26 TT T.Satoh Change Start -------------------------
			//ex.setStrErrmsg("�������Z�D ���Z�m��T���v��No�R���{�{�b�N�X�X�V���������s���܂���");
			ex.setStrErrmsg("�������Z ���Z�m��T���v��No�R���{�{�b�N�X�X�V���������s���܂���");
			//2011/04/13 QP@10181_No.26 TT T.Satoh Change End ---------------------------
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getMessage());
			throw ex;
			
		} finally {
			
		}
		
	}

	/************************************************************************************
	 * 
	 * ���Z�m��T���v��No�R���{�{�b�N�X�I������
	 * @throws ExceptionBase 
	 * 
	 ************************************************************************************/
	public void selectCmbShisanKakutei(int intSelectIndex) throws ExceptionBase {
		
		try {
			this.cmbShisanKakutei.setSelectedIndex(intSelectIndex);
		
		} catch (Exception e) {
			//�G���[�ݒ�
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			//2011/04/13 QP@10181_No.26 TT T.Satoh Change Start -------------------------
			//ex.setStrErrmsg("�������Z�D ���Z�m��T���v��No�R���{�{�b�N�X�I�����������s���܂���");
			ex.setStrErrmsg("�������Z ���Z�m��T���v��No�R���{�{�b�N�X�I�����������s���܂���");
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
	 *  �������Z��ʃN���A����
	 *   : ���Z�m��T���v��No�R���{�{�b�N�X�A�������Z�o�^�{�^���A
	 *   ���Z�����Q�ƃ{�^�����g�p�s�ɐݒ肷��
	 *   @author TT katayama
	 *   @throws ExceptionBase 
	 *   
	 ************************************************************************************/
	public void clearGenkaShisan() throws ExceptionBase {

		try {

			//���Z�m��T���v��No�R���{�{�b�N�X�̒l���N���A
			this.cmbShisanKakutei.removeAllItems();
			
			//���Z�m��T���v��No�R���{�{�b�N�X���g�p�s�ɐݒ�
			this.cmbShisanKakutei.setEnabled(false);

			//�������Z�o�^�{�^�����g�p�s�ɐݒ�
			this.btnShisanToroku.setEnabled(false);
			
			//���Z�����Q�ƃ{�^�����g�p�s�ɐݒ�
			this.btnShisanRireki.setEnabled(false);
			
		} catch (Exception e) {
			//�G���[�ݒ�
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			//2011/04/13 QP@10181_No.26 TT T.Satoh Change Start -------------------------
			//ex.setStrErrmsg("�������Z�D ���Z�m��T���v��No�R���{�{�b�N�X�X�V���������s���܂���");
			ex.setStrErrmsg("�������Z ���Z�m��T���v��No�R���{�{�b�N�X�X�V���������s���܂���");
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
	 *  �������Z�o�^����
	 *   @author TT katayama
	 *   @throws ExceptionBase 
	 *   
	 ************************************************************************************/
	public void shisanToroku() throws ExceptionBase {
		
		try {
		
			//J010 �����̔ԏ��� [���Z������ �擾]
			String strRirekiNo = conJ010();
			
			//JW860 : �������Z�o�^
			con_JW860(strRirekiNo);
		
		} catch (ExceptionBase e) {
			throw e;
			
		} catch (Exception e) {
			//�G���[�ݒ�
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			//2011/04/13 QP@10181_No.26 TT T.Satoh Change Start -------------------------
			//ex.setStrErrmsg("�������Z�D �������Z�o�^���������s���܂���");
			ex.setStrErrmsg("�������Z �������Z�o�^���������s���܂���");
			//2011/04/13 QP@10181_No.26 TT T.Satoh Change End ---------------------------
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getMessage());
			throw ex;
			
		} finally {
			
		}
		
	}
	
	/************************************************************************************
	 * 
	 * �������Z�\�@���FG�`�F�b�N
	 * @param strName : ���[�� 
	 * @param intChkCount : �w�茏��
	 * @return true : �����, false : ����s��
	 * @throws ExceptionBase 
	 * 
	 ************************************************************************************/
	public boolean chkGenkaInsatuFlg(String strName, int intChkCount) throws ExceptionBase {

		boolean ret = true;
		int intCount = 0;
		
		try {
			
			//�����f�[�^���擾���A����e�[�u���f�[�^���擾����
			ArrayList aryShisakuRetu = DataCtrl.getInstance().getTrialTblData().SearchGenkaGenryoData(0);
			for ( int i=0; i<aryShisakuRetu.size(); i++ ) {
				CostMaterialData costMaterialData = (CostMaterialData)aryShisakuRetu.get(i);
				
				//���Flg���`�F�b�N����Ă���Ȃ�΁A�J�E���g��i�߂�B
				if ( costMaterialData.getIntinsatu() == 1 ) {
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
			//ex.setStrErrmsg("�������Z�D ���FG�`�F�b�N���������s���܂���");
			ex.setStrErrmsg("�������Z ���FG�`�F�b�N���������s���܂���");
			//2011/04/13 QP@10181_No.26 TT T.Satoh Change End ---------------------------
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getStackTrace().toString());
			throw ex;
			
		} finally {
			
		}
		return ret;
	
	}
	
	//2011/05/02 QP@10181_No.73 TT T.Satoh Add Start -------------------------
	/************************************************************************************
	 * 
	 * �������Z�\�@�������Z�˗�FG�`�F�b�N
	 * @return true : �˗��ς�, false : �˗����Ă��Ȃ�
	 * @throws ExceptionBase 
	 * 
	 ************************************************************************************/
	public boolean chkGenkaIraiFlg() throws ExceptionBase {
		
		boolean ret = true;
		int intCount = 0;
		
		//�擾�Ώۃf�[�^ : ����e�[�u��
		TrialData trialData = null;
		
		try {
			//����f�[�^���擾
			Object[] aryTrialData = (DataCtrl.getInstance().getTrialTblData().SearchShisakuRetuData(0).toArray());
			
			//�e�[�u���J�������f���擾
			DefaultTableColumnModel columnModel = (DefaultTableColumnModel)table.getColumnModel();
			
			//�񐔕����[�v
			for(int j = 0;j < columnModel.getColumnCount();j++){
				//�����f�[�^�擾�i�\�����j
				int no=0;
				for(int i=0; i<columnModel.getColumnCount(); i++){
					TrialData chkHyji = (TrialData)aryTrialData[i];
					if((chkHyji.getIntHyojiNo()-1) == j){
						no=i;
					}
				}
				trialData = (TrialData)aryTrialData[no];
				
				//�����f�[�^���擾
				ArrayList aryShisakuRetu = DataCtrl.getInstance().getTrialTblData().SearchGenkaGenryoData(0);
				
				//����e�[�u���f�[�^���擾
				for ( int i=0; i<aryShisakuRetu.size(); i++ ) {
					//�������Z�˗�Flg���`�F�b�N����Ă���Ȃ�΁A�J�E���g��i�߂�B
					if ( trialData.getFlg_shisanIrai() == 1 ) {
						intCount++;
					}
				}
				
				//�������Z�˗�FG���I������Ă��Ȃ��ꍇ
				if ( intCount == 0 ) {
					ret = false;
				}
			}
		} catch (ExceptionBase e) {
			throw e;
		} catch (Exception e) {
			//�G���[�ݒ�
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			ex.setStrErrmsg("�������Z �������Z�˗�FG�`�F�b�N���������s���܂���");
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getStackTrace().toString());
			throw ex;
		} finally {
		}
		return ret;
	}
	//2011/05/02 QP@10181_No.73 TT T.Satoh Add End ---------------------------
	
	/************************************************************************************
	 * 
	 * �������Z�\�@�o�͏���
	 * @param intKoteiVal : �I������Ă���H���̃��e�����l1(�s���ł͂Ȃ��l)
	 * @throws ExceptionBase 
	 * 
	 ************************************************************************************/
	public void outputGenkaShisanHyo(int intKoteiVal) throws ExceptionBase {

		try {

			//�������Z�\�o��
			con_JW830(intKoteiVal);
			
		} catch (ExceptionBase e) {
			throw e;
			
		} catch (Exception e) {
			//�G���[�ݒ�
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			//2011/04/13 QP@10181_No.26 TT T.Satoh Change Start -------------------------
			//ex.setStrErrmsg("�������Z�D �������Z�\�o�͏��������s���܂���");
			ex.setStrErrmsg("�������Z �������Z�\�o�͏��������s���܂���");
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
	 * �������ZNo�ݒ菈��
	 * @param intShisakuSeq : ����SEQ 
	 * @param intGenkaShisan : �������ZNo
	 * @throws ExceptionBase 
	 * 
	 ************************************************************************************/
	private void setGenkaShisanNo(int intShisakuSeq, int intGenkaShisan) throws ExceptionBase {
		
		try {
			
			//����e�[�u���f�[�^���擾
			TrialData trialData = (TrialData)(DataCtrl.getInstance().getTrialTblData().SearchShisakuRetuData(intShisakuSeq).get(0));
			
			trialData.setIntGenkaShisan(intGenkaShisan);
			
		} catch (Exception e) {
			//�G���[�ݒ�
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			//2011/04/13 QP@10181_No.26 TT T.Satoh Change Start -------------------------
			//ex.setStrErrmsg("�������Z�D �������ZNo�ݒ菈�������s���܂���");
			ex.setStrErrmsg("�������Z �������ZNo�ݒ菈�������s���܂���");
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
			//2011/04/13 QP@10181_No.26 TT T.Satoh Change Start -------------------------
			//ex.setStrErrmsg("�������Z�D �������Z�X�V���������s���܂���");
			ex.setStrErrmsg("�������Z �������Z�X�V���������s���܂���");
			//2011/04/13 QP@10181_No.26 TT T.Satoh Change End ---------------------------
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(ec.getMessage());
			throw ex;
			
		} finally {
			
		}
		
	}
		
	/************************************************************************************
	 * 
	 * ���Z�m��T���v��No�R���{�{�b�N�X�@�Q�b�^
	 * @return ���Z�m��T���v��No�R���{�{�b�N�X
	 * 
	 ************************************************************************************/
	public ComboBase getCmbShisanKakutei() {
		return this.cmbShisanKakutei;
		
	}

	/************************************************************************************
	 * 
	 * �������Z�\����{�^���@�Q�b�^
	 * @return �������Z�\����{�^��
	 * 
	 ************************************************************************************/
	public ButtonBase getBtnShisanHyo() {
		return this.btnShisanHyo;
		
	}
	
	/************************************************************************************
	 * 
	 * �������Z�o�^�{�^���@�Q�b�^
	 * @return �������Z�o�^�{�^��
	 * 
	 ************************************************************************************/
	public ButtonBase getBtnShisanToroku() {
		return this.btnShisanToroku;
		
	}
	
	public TableBase getTable() {
		return table;
	}

	public void setTable(TableBase table) {
		this.table = table;
	}
	
	//2011/04/20 QP@10181_No.41 TT T.Satoh Add Start -------------------------
	/************************************************************************************
	 * 
	 *   scroll�Q�b�^�[
	 *   @author TT satoh
	 *   @return scroll :�@�������Z�̃X�N���[���o�[
	 *   
	 ************************************************************************************/
	public JScrollPane getScroll() {
		return scroll;
	}
	
	/************************************************************************************
	 * 
	 *   scroll�Z�b�^�[
	 *   @author TT satoh
	 *   @param _scroll : �������Z�̃X�N���[���o�[
	 *   
	 ************************************************************************************/
	public void setScroll(JScrollPane _scroll) {
		scroll = _scroll;
	}
	//2011/04/20 QP@10181_No.41 TT T.Satoh Add End ---------------------------
	
}

