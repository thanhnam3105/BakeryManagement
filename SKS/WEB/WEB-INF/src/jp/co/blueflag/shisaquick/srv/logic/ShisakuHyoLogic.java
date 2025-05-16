package jp.co.blueflag.shisaquick.srv.logic;

import java.util.ArrayList;
import java.util.List;

//�yQP@20505�zNo3 2012/10/09 TT H.SHIMA DEL
//import jp.co.blueflag.shisaquick.srv.base.LogicBaseExcel;
//�yQP@20505�zNo3 2012/10/09 TT H.SHIMA DEL
import jp.co.blueflag.shisaquick.srv.base.LogicBaseJExcel;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsKindBean;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;
import jp.co.blueflag.shisaquick.srv.common.ExceptionManager.ExceptionKind;

/**
 * 
 * �ySA460�z ����\�𐶐�����
 * 
 * @author k-katayama
 * @since 2009/05/20
 *
 */
public class ShisakuHyoLogic extends LogicBaseJExcel {

	/**
	 * �R���X�g���N�^
	 */
	public ShisakuHyoLogic() {
		//�X�[�p�[�N���X�̃R���X�g���N�^�Ăяo��
		super();
		
	}

	/**
	 * ����\�𐶐�����
	 * @param reqData : ���N�G�X�g�f�[�^
	 * @param userInfoData : ���[�U�[���
	 * @return ���X�|���X�f�[�^�i�@�\�j
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	public RequestResponsKindBean ExecLogic(
			RequestResponsKindBean reqData
			,UserInfoData _userInfoData
			) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		//���[�U�[���ޔ�
		userInfoData = _userInfoData;

		//���X�|���X�f�[�^�i�@�\�j
		RequestResponsKindBean ret = null;
		//��������
		List<?> lstRecset1 = null;		//�z���f�[�^�p�����f�[�^
		List<?> lstRecset2 = null;		//����f�[�^�p�����f�[�^
		String strChuijiko = "";			//�����H��/���ӎ����f�[�^
		//�G�N�Z���t�@�C���p�X
		String DownLoadPath = "";
		
		try {
			
			//�o�͑Ώۃf�[�^�����݂��Ȃ���,Exception��throw����
			if ( reqData.getCntRow(0) == 0 ) {
				this.em.ThrowException(ExceptionKind.�x��Exception,"W000403", "", "", "");
				
			}
			
			//DB����
			super.createSearchDB();
			lstRecset1 = getHaigoData(reqData);
			lstRecset2 = getShisakuData(reqData);
			strChuijiko = getChuijikoData(reqData, lstRecset2);

			//Excel�t�@�C������ : ����\�i���o�[�W�����j
			DownLoadPath = makeExcelFile1(
					lstRecset1, 
					lstRecset2, 
					strChuijiko,
					reqData		
			);

			//Excel�t�@�C������ : ����\�i�c�o�[�W�����j
			DownLoadPath = DownLoadPath + ":::" + makeExcelFile2(
					lstRecset1, 
					lstRecset2, 
					strChuijiko,
					reqData
					);
			
			//���X�|���X�f�[�^����
			ret = CreateRespons(DownLoadPath);
			
		} catch (Exception e) {
			em.ThrowException(e, "����\�̐����Ɏ��s���܂����B");

		} finally {
			//���X�g�̔j��
			removeList(lstRecset1);
			removeList(lstRecset2);
			if (searchDB != null) {
				//DB�Z�b�V�����̃N���[�Y
				searchDB.Close();
				searchDB = null;
			}
			
			//�ϐ��̍폜
			DownLoadPath = null;
			strChuijiko = null;

		}
		return ret;

	}
	
	/**
	 * ���X�|���X�f�[�^�𐶐�����
	 * @param DownLoadPath : �t�@�C���p�X�����t�@�C���i�[��(�_�E�����[�h�p�����[�^)
	 * @return RequestResponsKindBean : ���X�|���X�f�[�^�i�@�\�j
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private RequestResponsKindBean CreateRespons(String DownLoadPath) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		RequestResponsKindBean ret = null;
		
		try {
			//���X�|���X�𐶐�����
			ret = new RequestResponsKindBean();
			//�@�\ID��ݒu����
			ret.setID("SA460");
			
			//�t�@�C���p�X	�����t�@�C���i�[��
			ret.addFieldVale(0, 0, "URLValue", DownLoadPath);
			//�������ʇ@	������
			ret.addFieldVale(0, 0, "flg_return", "true");
			//�������ʇA	���b�Z�[�W
			ret.addFieldVale(0, 0, "msg_error", "");
			//�������ʇB	��������
			ret.addFieldVale(0, 0, "no_errmsg", "");
			//�������ʇE	���b�Z�[�W�ԍ�
			ret.addFieldVale(0, 0, "nm_class", "");
			//�������ʇC	�G���[�R�[�h
			ret.addFieldVale(0, 0, "cd_error", "");
			//�������ʇD	�V�X�e�����b�Z�[�W
			ret.addFieldVale(0, 0, "msg_system", "");
			
		} catch (Exception e) {
			em.ThrowException(e, "");

		} finally {

		}
		return ret;
		
	}
	
	/**
	 * ����\(���o�[�W����)�𐶐�����
	 * @param lstHaigoRecset : �z���f�[�^�����f�[�^���X�g
	 * @param lstShisakuRecset : ����f�[�^�����f�[�^���X�g
	 * @param strChuijiko 
	 * @return�@String : �_�E�����[�h�p�̃p�X
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private String makeExcelFile1(
			List<?> lstHaigoRecset, 
			List<?> lstShisakuRecset, 
			String strChuijiko,
			RequestResponsKindBean reqData		
	) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		String ret = "";
		Object[] items = null;
		
		//�l�i�[�p
		int intShisakuRetuSu = 0;				//�����
		int intShosuKeta = 0;						//��������
		Double dblRyo = 0.0;						//�z����
		Double dblGoukeiJuryo = 0.0;			//�z���� ���v
		
		//�H�� ���v��
		int intKoteiCount = 0;
		Object[] aryKoteiGoukeiRyo = null;
		List<Double> lstKoteiGoukei = new ArrayList<Double>();

		//���v�d�オ��d��
		List<Object> lstShiagariJuryo = new ArrayList<Object>();				
						
		try {
// ADD start 20121019 QP@20505 No.24
			String ptSokuteichi = ""; // ����l���ڂ̕\���p�^�[��
			ptSokuteichi = reqData.getFieldVale(0, 0, "pattern_kotei");
// ADD end 20121019 QP@20505 No.24
			
			//EXCEL�e���v���[�g��ǂݍ���
			super.ExcelReadTemplate("����\(��)");
			
			
			//�_�E�����[�h�p��EXCEL�𐶐�����
			
			// �@ : ��ʊ�{�����_�E�����[�h�pEXCEL�ɐݒ�
			
			// �������ʎ擾
			items = (Object[]) lstHaigoRecset.get(0);
			
			//�yQP@20505�zNo3 2012/10/09 TT H.SHIMA ADD Start JEXCEL�Ή��̈�
			//�G�N�Z���t�@�C�����_�E�����[�h�t�H���_�ɐ�������
			ret = super.ExcelOutput(
			makeShisakuNo(
					reqData.getFieldVale(0, 0, "cd_shain")
					,reqData.getFieldVale(0, 0, "nen")
					,reqData.getFieldVale(0, 0, "no_oi")
					));
			//�yQP@20505�zNo3 2012/10/09 TT H.SHIMA ADD End
			
			// Excel�ɒl���Z�b�g		
			
			//���ʍ���
			ExcelSetValueShisakuhin(items);		
			//��������
			super.ExcelSetValue("��������", toString(items[7]));
			
			
			//����񐔂̎擾
// 2010.11.04 Mod Arai Start  QP@00412_�V�T�N�C�b�N���� ��.1------------------
			intShisakuRetuSu = Integer.parseInt(items[34].toString());
//			intShisakuRetuSu = Integer.parseInt(items[24].toString());
// 2010.11.04 Mod Arai End ---------------------------------------------------
						
			//����񐔕��I�u�W�F�N�g��p�ӂ���
			aryKoteiGoukeiRyo = new Object[intShisakuRetuSu];

			//�H�����v�ʊi�[�p�z��A�P���i�[�p�z��̏�����
			for ( int i=0; i<intShisakuRetuSu; i++ ) {
				aryKoteiGoukeiRyo[i] = new ArrayList<Double>();
				
			}			

			// �A : �z���f�[�^���_�E�����[�h�pEXCEL�ɐݒ�
			for ( int i = 0; i < lstHaigoRecset.size(); i++ ) {
				
				// �������ʎ擾
				items = (Object[]) lstHaigoRecset.get(i);
				
				try{

					//��������
					if ( !toString(items[9]).isEmpty() ) {
						intShosuKeta = Integer.parseInt(toString(items[9]));
						
					} 

					// Excel�ɒl���Z�b�g����
					
					//���ʍ���
					this.ExcelSetValueHaigo(items);		
					
					//��(�񐔕�)
					for ( int j=0; j<intShisakuRetuSu; j++ ) {
//mod start --------------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.1
//						super.ExcelSetValue("��" + (j+1), toRoundString(toDouble(items[j + 14]), intShosuKeta) );
						super.ExcelSetValue("��" + (j+1), ChkDblVal(toRoundString(toDouble(items[j + 14]), intShosuKeta)) );
//mod end --------------------------------------------------------------------------------------
						
					}

					//�H���s�̏ꍇ
					if ( toString(items[10]).equals("---") ) {

						//�H�����v�ʃ��X�g�̏�����
						for ( int j=0; j<aryKoteiGoukeiRyo.length; j++ ) {
							lstKoteiGoukei = (ArrayList<Double>)aryKoteiGoukeiRyo[j];
							lstKoteiGoukei.add(0.0);
							aryKoteiGoukeiRyo[j] = lstKoteiGoukei;
							
						}
						//�J�E���g��i�߂�
						intKoteiCount++;
						
					} else {
						
						//�e���ڂ̍H�����v�ʋy�сA�P�����Z�o����
						for ( int j=0; j<aryKoteiGoukeiRyo.length; j++ ) {

							//�z����1�`10��ϐ��Ɋi�[����
							dblRyo = toDouble(items[j + 14]);

							//�e�H���̍��v��(�ő�10��)�����߂�
							lstKoteiGoukei = (ArrayList<Double>)aryKoteiGoukeiRyo[j];
							//���v�l�Z�o
							SetKoteiGoukeiRyo(lstKoteiGoukei, intKoteiCount, dblRyo);		
							aryKoteiGoukeiRyo[j] = lstKoteiGoukei;

						}			
												
					}
					
				} catch (ExceptionWaning e) {
					// �ő�s���𒴂���ƁAExceptionWaning��Throw�����̂Ń��[�v���I������B
					break;
					
				} finally {
					items = null;
					
				}
				
			}
						
			//�B : ���ږ���\��
			items = (Object[]) lstShisakuRecset.get(0);
// ADD start 20121019 QP@20505 No.24
			if (ptSokuteichi.equals("1")){
				// �H���p�[�^�����P�tor�Q�t�̏ꍇ
				ExcelSetValueTitle_p1and2(items);
			} else {
// ADD end 20121019 QP@20505 No.24
				ExcelSetValueTitle(items);
// ADD start 20121019 QP@20505 No.24
			}
// ADD end 20121019 QP@20505 No.24
			
			//�C : ����f�[�^���_�E�����[�h�pEXCEL�ɐݒ�
			for ( int i = 0; i < lstShisakuRecset.size(); i++ ) {
				
				//�������ʎ擾
				items = (Object[]) lstShisakuRecset.get(i);
				
				try{
					//Excel�ɒl���Z�b�g����
					
					//���ʍ���
// ADD start 20121019 QP@20505 No.24
					if (ptSokuteichi.equals("1")){
						// �H���p�[�^�����P�tor�Q�t�̏ꍇ
						this.ExcelSetValueShisaku_p1and2(items, i+1);
					} else {
// ADD end 20121019 QP@20505 No.24
						this.ExcelSetValueShisaku(items, i+1);
// ADD start 20121019 QP@20505 No.24
					}
// ADD end 20121019 QP@20505 No.24
					//�R�����g���e
					super.ExcelSetValue("�R�����g���eNO", toString(items[2]));
					super.ExcelSetValue("�R�����g���eNO", "");
					super.ExcelSetValue("�R�����g���e", "�y�쐬�����z\n" + toString(items[4]) + "\n\n�y�]���z\n" + toString(items[5]));
					super.ExcelSetValue("�R�����g���e", "");

					//���v�d�オ��d�ʂ�ݒ肷��
					lstShiagariJuryo.add(items[19]);
					
				} catch (ExceptionWaning e) {
					//�ő�s���𒴂���ƁAExceptionWaning��Throw�����̂Ń��[�v���I������B
					break;
					
				} finally {
					items = null;			
					
				}
				
			}
			
			//�yQP@20505�zNo3 2012/10/09 TT H.SHIMA DEL Start
			//�D : �����H��/���ӎ�����ݒ�
//			super.ExcelSetValue("�����H��/���ӎ���", strChuijiko);
			//�yQP@20505�zNo3 2012/10/09 TT H.SHIMA DEL End
			
			//�e���ڂ̍H�����v�ʋy�сA�P�����Z�o����
			for ( int j=0; j<intShisakuRetuSu; j++ ) {
				
				//�E : �H�����v�ʁE���v�d�ʂ�ݒ�
				lstKoteiGoukei = (ArrayList<Double>)aryKoteiGoukeiRyo[j];
				dblGoukeiJuryo = ExcelSetValueKotei(lstKoteiGoukei, intShosuKeta, j);
				
				//�F : �d�グ�d�ʖ��́A���v�d�ʂ�Excel�ɐݒ�
				
				try {
						
//mod start --------------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.1
//					//�d�オ��d�ʂ̑��݃`�F�b�N
//					if ( toString(lstShiagariJuryo.get(j)).isEmpty() ) {
//						//�d�オ��d�ʂ̑��݂��Ȃ��ꍇ�A���v�d�ʂ�\��
//						super.ExcelSetValue("���v�d��", toString(toRoundString(dblGoukeiJuryo,intShosuKeta)));
//						
//					} else {
//						//�d�オ��d�ʂ̑��݂���ꍇ�A�d�グ�d�ʂ�\��
//						super.ExcelSetValue("���v�d��", toRoundString(toDouble(lstShiagariJuryo.get(j)),intShosuKeta) );
//						
//					}
					//���v�d�ʂ̕\��
					super.ExcelSetValue("���v�d��", ChkDblVal(toRoundString(dblGoukeiJuryo,intShosuKeta)) );
					//�d���d�ʂ̕\��
					super.ExcelSetValue("���v�d��d��", ChkDblVal(toRoundString(toDouble(lstShiagariJuryo.get(j)),intShosuKeta)) );
//mod end --------------------------------------------------------------------------------------
										
				} catch (ExceptionWaning e) {
					//�ő�s���𒴂���ƁAExceptionWaning��Throw�����
					//�������I������
					
				} finally {
					
				}
				
			}
			
			//�yQP@20505�zNo3 2012/10/09 TT H.SHIMA DEL Start JEXCEL�Ή��̈�
			//�G�N�Z���t�@�C�����_�E�����[�h�t�H���_�ɐ�������
//			ret = super.ExcelOutput();
//			ret = super.ExcelOutput(
//					makeShisakuNo(
//							reqData.getFieldVale(0, 0, "cd_shain")
//							,reqData.getFieldVale(0, 0, "nen")
//							,reqData.getFieldVale(0, 0, "no_oi")
//							)
//					);
			//�yQP@20505�zNo3 2012/10/09 TT H.SHIMA DEL End
			//�yQP@20505�zNo3 2012/10/09 TT H.SHIMA ADD Start JEXCEL�Ή��̈�
			super.ExcelWrite();
			super.close();
			//�yQP@20505�zNo3 2012/10/09 TT H.SHIMA ADD End
			
		} catch (Exception e) {
			em.ThrowException(e, "");

		} finally {
			//���X�g�̔j��
			removeList(lstShiagariJuryo);
			removeList(lstKoteiGoukei);

			//�ϐ��̍폜
			items = null;
			dblRyo = null;			
			intKoteiCount = 0;
			lstKoteiGoukei = null;
			dblGoukeiJuryo = null;
			lstShiagariJuryo = null;
			aryKoteiGoukeiRyo = null;

		}
		return ret;
		
	}
	/**
	 * �t�@�C�����p�̎���ԍ��𐶐�����
	 * @param strCd_shain�@�F�@����No�Ј�
	 * @param strNen�@�F�@����No�N
	 * @param strNo_oi�@�F�@����No�ǂ���
	 * @return�@String�@�F�@����No
	 */
	private String makeShisakuNo(String strCd_shain, String strNen, String strNo_oi){
		
		String ret = "";

		ret += getRight(("0000000000" + strCd_shain),10);
		ret += "-";
		ret += getRight(("0000000000" + strNen),2);
		ret += "-";
		ret += getRight(("0000000000" + strNo_oi),3);
		
		return ret;
		
	}
	/**
	 * ����\(�c�o�[�W����)�𐶐�����
	 * @param lstHaigoRecset : �z���f�[�^�����f�[�^���X�g
	 * @param lstShisakuRecset : ����f�[�^�����f�[�^���X�g
	 * @param strChuijiko 
	 * @return�@String : �_�E�����[�h�p�̃p�X
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private String makeExcelFile2(
			List<?> lstHaigoRecset, 
			List<?> lstShisakuRecset, 
			String strChuijiko,
			RequestResponsKindBean reqData
			) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		String ret = "";
		Object[] items = null;
		
		//�l�i�[�p
		int intShisakuRetuSu = 0;				//�����
		int intShosuKeta = 0;						//��������
		Double dblRyo = 0.0;						//�z����
		Double dblGoukeiJuryo = 0.0;			//�z���� ���v
		
		//�H�� ���v��
		int intKoteiCount = 0;
		Object[] aryKoteiGoukeiRyo = null;
		List<Double> lstKoteiGoukei = new ArrayList<Double>();

		//���v�d�オ��d��
		List<Object> lstShiagariJuryo = new ArrayList<Object>();				
						
		try {
			
			//EXCEL�e���v���[�g��ǂݍ���
			super.ExcelReadTemplate("����\(�c)");
			
			
			//�_�E�����[�h�p��EXCEL�𐶐�����
			
// ADD start 20121019 QP@20505 No.24
			String ptSokuteichi = ""; // ����l���ڂ̕\���p�^�[��
			ptSokuteichi = reqData.getFieldVale(0, 0, "pattern_kotei");
// ADD end 20121019 QP@20505 No.24
			
			// �@ : ��ʊ�{�����_�E�����[�h�pEXCEL�ɐݒ�
			
			// �������ʎ擾
			items = (Object[]) lstHaigoRecset.get(0);

			//�yQP@20505�zNo3 2012/10/09 TT H.SHIMA DEL JEXCEL�Ή��̈�
			ret = super.ExcelOutput(
					makeShisakuNo(
							reqData.getFieldVale(0, 0, "cd_shain")
							,reqData.getFieldVale(0, 0, "nen")
							,reqData.getFieldVale(0, 0, "no_oi")
							)
					);
			//�yQP@20505�zNo3 2012/10/09 TT H.SHIMA DEL
			
			// Excel�ɒl���Z�b�g		
			
			//���ʍ���
			ExcelSetValueShisakuhin(items);	
						
			//����񐔂̎擾
// 2010.11.04 Mod Arai Start  QP@00412_�V�T�N�C�b�N���� ��.1------------------
			intShisakuRetuSu = Integer.parseInt(items[34].toString());
//			intShisakuRetuSu = Integer.parseInt(items[24].toString());
// 2010.11.04 Mod Arai End ---------------------------------------------------
						
			//����񐔕��I�u�W�F�N�g��p�ӂ���
			aryKoteiGoukeiRyo = new Object[intShisakuRetuSu];

			//�H�����v�ʊi�[�p�z��A�P���i�[�p�z��̏�����
			for ( int i=0; i<intShisakuRetuSu; i++ ) {
				aryKoteiGoukeiRyo[i] = new ArrayList<Double>();
				
			}
			

			// �A : �z���f�[�^���_�E�����[�h�pEXCEL�ɐݒ�
			for ( int i = 0; i < lstHaigoRecset.size(); i++ ) {
				
				// �������ʎ擾
				items = (Object[]) lstHaigoRecset.get(i);
				
				try{

					//��������
					if ( !toString(items[9]).isEmpty() ) {
						intShosuKeta = Integer.parseInt(toString(items[9]));
						
					} 

					// Excel�ɒl���Z�b�g����
					
					//���ʍ���
					this.ExcelSetValueHaigo(items);
					
					//��(�񐔕�)
					for ( int j=0; j<intShisakuRetuSu; j++ ) {
//mod start --------------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.1
//						super.ExcelSetValue("��" + (j+1), toRoundString(toDouble(items[j + 14]), intShosuKeta) );
						super.ExcelSetValue("��" + (j+1), ChkDblVal(toRoundString(toDouble(items[j + 14]), intShosuKeta)) );
//mod end --------------------------------------------------------------------------------------
						
					}

					//�H���s�̏ꍇ
					if ( toString(items[10]).equals("---") ) {

						//�H�����v�ʃ��X�g�̏�����
						for ( int j=0; j<aryKoteiGoukeiRyo.length; j++ ) {
							lstKoteiGoukei = (ArrayList<Double>)aryKoteiGoukeiRyo[j];
							lstKoteiGoukei.add(0.0);
							aryKoteiGoukeiRyo[j] = lstKoteiGoukei;
							
						}
						//�J�E���g��i�߂�
						intKoteiCount++;
						
					} else {
						
						//�e���ڂ̍H�����v�ʋy�сA�P�����Z�o����
						for ( int j=0; j<aryKoteiGoukeiRyo.length; j++ ) {

							//�z����1�`10��ϐ��Ɋi�[����
							dblRyo = toDouble(items[j + 14]);

							//�e�H���̍��v��(�ő�10��)�����߂�
							lstKoteiGoukei = (ArrayList<Double>)aryKoteiGoukeiRyo[j];
							//���v�l�Z�o
							SetKoteiGoukeiRyo(lstKoteiGoukei, intKoteiCount, dblRyo);		
							aryKoteiGoukeiRyo[j] = lstKoteiGoukei;

						}			
												
					}
					
				} catch (ExceptionWaning e) {
					// �ő�s���𒴂���ƁAExceptionWaning��Throw�����̂Ń��[�v���I������B
					break;
					
				} finally {
					items = null;
					
				}
				
			}

			//�B : ���ږ���\��
			items = (Object[]) lstShisakuRecset.get(0);
// ADD start 20121019 QP@20505 No.24
			if (ptSokuteichi.equals("1")){
				// �H���p�[�^�����P�tor�Q�t�̏ꍇ
				ExcelSetValueTitle_p1and2(items);
			} else {
// ADD end 20121019 QP@20505 No.24
				ExcelSetValueTitle(items);
// ADD start 20121019 QP@20505 No.24
			}
// ADD end 20121019 QP@20505 No.24
							
			//�C : ����f�[�^���_�E�����[�h�pEXCEL�ɐݒ�
			for ( int i = 0; i < lstShisakuRecset.size(); i++ ) {
				
				//�������ʎ擾
				items = (Object[]) lstShisakuRecset.get(i);
				
				try{
					//Excel�ɒl���Z�b�g����
					
					//���ʍ���
// ADD start 20121019 QP@20505 No.24
					if (ptSokuteichi.equals("1")){
						// �H���p�[�^�����P�tor�Q�t�̏ꍇ
						this.ExcelSetValueShisaku_p1and2(items, i+1);
					} else {
// ADD end 20121019 QP@20505 No.24
						this.ExcelSetValueShisaku(items, i+1);
// ADD start 20121019 QP@20505 No.24
					}
// ADD end 20121019 QP@20505 No.24
					//���v�d�オ��d�ʂ�ݒ肷��
					lstShiagariJuryo.add(items[19]);
				} catch (ExceptionWaning e) {
					//�ő�s���𒴂���ƁAExceptionWaning��Throw�����̂Ń��[�v���I������B
					break;
					
				} finally {
					items = null;			
					
				}
				
			}

			//�yQP@20505�zNo3 2012/10/09 TT H.SHIMA DEL Start
			//�D : �����H��/���ӎ�����ݒ�
//			super.ExcelSetValue("�����H��/���ӎ���", strChuijiko);
			//�yQP@20505�zNo3 2012/10/09 TT H.SHIMA DEL End

			//�e���ڂ̍H�����v�ʋy�сA�P�����Z�o����
			for ( int j=0; j<intShisakuRetuSu; j++ ) {
				
				//�E : �H�����v�ʁE���v�d�ʂ�ݒ�
				lstKoteiGoukei = (ArrayList<Double>)aryKoteiGoukeiRyo[j];
				dblGoukeiJuryo = ExcelSetValueKotei(lstKoteiGoukei, intShosuKeta, j);
				
				//�F : �d�グ�d�ʖ��́A���v�d�ʂ�Excel�ɐݒ�
				
				try {

//mod start --------------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.1
//					//�d�オ��d�ʂ̑��݃`�F�b�N
//					if ( toString(lstShiagariJuryo.get(j)).isEmpty() ) {
//						//�d�オ��d�ʂ̑��݂��Ȃ��ꍇ�A���v�d�ʂ�\��
//						super.ExcelSetValue("���v�d��", toString(toRoundString(dblGoukeiJuryo,intShosuKeta)));
//						
//					} else {
//						//�d�オ��d�ʂ̑��݂���ꍇ�A�d�グ�d�ʂ�\��
//						super.ExcelSetValue("���v�d��", toRoundString(toDouble(lstShiagariJuryo.get(j)),intShosuKeta) );
//						
//					}
					//���v�d�ʂ̕\��
					super.ExcelSetValue( "���v�d��", ChkDblVal(toRoundString(dblGoukeiJuryo,intShosuKeta)) );
					//�d���d�ʂ̕\��
					super.ExcelSetValue( "���v�d��d��", ChkDblVal(toRoundString(toDouble(lstShiagariJuryo.get(j)),intShosuKeta)) );
//mod end --------------------------------------------------------------------------------------
					
				} catch (ExceptionWaning e) {
					//�ő�s���𒴂���ƁAExceptionWaning��Throw�����
					//�������I������
					
				} finally {
					
				}
				
			}
					
			//�yQP@20505�zNo3 2012/10/09 TT H.SHIMA DEL Start JEXCEL�Ή��̈�
			//�G�N�Z���t�@�C�����_�E�����[�h�t�H���_�ɐ�������
//			ret = super.ExcelOutput();
//			ret = super.ExcelOutput(
//					makeShisakuNo(
//							reqData.getFieldVale(0, 0, "cd_shain")
//							,reqData.getFieldVale(0, 0, "nen")
//							,reqData.getFieldVale(0, 0, "no_oi")
//							)
//					);
			//�yQP@20505�zNo3 2012/10/09 TT H.SHIMA DEL End
			//�yQP@20505�zNo3 2012/10/09 TT H.SHIMA ADD Start JEXCEL�Ή��̈�
			super.ExcelWrite();
			super.close();
			//�yQP@20505�zNo3 2012/10/09 TT H.SHIMA ADD End
			
		} catch (Exception e) {
			em.ThrowException(e, "");

		} finally {
			//���X�g�̔j��
			removeList(lstShiagariJuryo);
			removeList(lstKoteiGoukei);

			//�ϐ��̍폜
			items = null;
			dblRyo = null;			
			intKoteiCount = 0;
			lstKoteiGoukei = null;
			dblGoukeiJuryo = null;
			lstShiagariJuryo = null;
			aryKoteiGoukeiRyo = null;

		}
		return ret;
		
	}
	
	/**
	 * ����i�e�[�u���f�[�^��Excel�ɐݒ�
	 *  : ���ʍ��ڂ̒l��ݒ肷��
	 * @param lstShisakuRecset
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private void ExcelSetValueShisakuhin(Object[] items)
		throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		try {
			//Excel�ɒl��ݒ�
			super.ExcelSetValue("����R�[�h", toString(items[0]));
			super.ExcelSetValue("�˗��ԍ�", toString(items[1]));
			super.ExcelSetValue("�i��", toString(items[2]));
			super.ExcelSetValue("����", toString(items[3]));
			super.ExcelSetValue("�I���H��", toString(items[4]));
			super.ExcelSetValue("�����", toString(items[5]));
			super.ExcelSetValue("���s��", toString(items[6]));
			super.ExcelSetValue("���상��", toString(items[8]));

		} catch(Exception e) {
			em.ThrowException(e, "");
			 
		} finally {
			
		}
		
	}
	
	/**
	 * �z���f�[�^��Excel�ɐݒ�
	 *  : ���ʍ��ڂ̒l��ݒ肷��
	 * @param lstShisakuRecset
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private void ExcelSetValueHaigo(Object[] items)
		throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		try {
			//Excel�ɒl��ݒ�
			super.ExcelSetValue("�����R�[�h", toString(items[10]));
			super.ExcelSetValue("������", toString(items[11]));
			
//mod start --------------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.1
//			super.ExcelSetValue("�P��", toRoundString(toDouble(items[12]), 2) );
//			super.ExcelSetValue("����", toRoundString(toDouble(items[13]), 2) );
			//�P���E�����̐ݒ�(�H���s�̏ꍇ�́A�󔒂�ݒ肷��)
			if ( toString(items[10]).equals("---") ) {
				super.ExcelSetValue("�P��", "" );
				super.ExcelSetValue("����", "" );
			} else {
				super.ExcelSetValue("�P��", toRoundString(toDouble(items[12]), 2) );		
				super.ExcelSetValue("����", toRoundString(toDouble(items[13]), 2) );		
			}
//mod end --------------------------------------------------------------------------------------

		} catch(Exception e) {
			em.ThrowException(e, "");
			 
		} finally {
			
		}
		
	}
	
	/**
	 * ����f�[�^��Excel�ɐݒ�
	 *  : ���ʍ��ڂ̒l��ݒ肷��
	 * @param items : ����f�[�^
	 * @param columnNo : ��ԍ�
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
		private void ExcelSetValueShisaku(Object[] items, int columnNo)
		throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		String strLinkNmVal = "�l" + columnNo;
		String strLinkNmTitle = "�^�C�g��";
		
		try {
			//Excel�ɒl��ݒ�
			//�yQP@20505�zNo3 2012/10/09 TT H.SHIMA ADD Start
			if(columnNo <= 3 ){
				super.ExcelSetValue("���i_�T���v��No" + columnNo, "�T���v��:" + toString(items[2]));
				super.ExcelSetValue("���i_�H����No" + columnNo, "�H����:" + toString(items[18]));
				super.ExcelSetValue("���i_���ӎ���" + columnNo, "" + toString(items[50]));
			}
			//�yQP@20505�zNo3 2012/10/09 TT H.SHIMA ADD End
			
			super.ExcelSetValue("���t", toString(items[1]));
			super.ExcelSetValue("NO", toString(items[2]));
			super.ExcelSetValue("����", toString(items[3]));
			
			//�t���O��1�̏ꍇ�A�\��
			if ( toString(items[28]).equals("1") ) {
				//���_
				super.ExcelSetValue(strLinkNmVal, toString(items[6]));
				
			}
			if ( toString(items[29]).equals("1") ) {
				//�H��
				super.ExcelSetValue(strLinkNmVal, toString(items[7]));
				
			}
			if ( toString(items[30]).equals("1") ) {
				//�������_�x
				super.ExcelSetValue(strLinkNmVal, toString(items[8]));
				
			}
			if ( toString(items[31]).equals("1") ) {
				//�������H��
				super.ExcelSetValue(strLinkNmVal, toString(items[9]));
				
			}
			if ( toString(items[32]).equals("1") ) {
				//�������|�_
				super.ExcelSetValue(strLinkNmVal, toString(items[10]));
				
			}
			if ( toString(items[33]).equals("1") ) {
				//���x
				super.ExcelSetValue(strLinkNmVal, toString(items[11]));
				
			}
			if ( toString(items[34]).equals("1") ) {
				//�S�x
				super.ExcelSetValue(strLinkNmVal, toString(items[12]));
				
			}
			if ( toString(items[35]).equals("1") ) {
				//���x
				super.ExcelSetValue(strLinkNmVal, toString(items[13]));
				
			}
			if ( toString(items[36]).equals("1") ) {
				//pH
				super.ExcelSetValue(strLinkNmVal, toString(items[14]));
				
			}
			if ( toString(items[37]).equals("1") ) {
				//���_�i���́j
				super.ExcelSetValue(strLinkNmVal, toString(items[15]));
				
			}
			if ( toString(items[38]).equals("1") ) {
				//�H���i���́j
				super.ExcelSetValue(strLinkNmVal, toString(items[16]));
				
			}
			if ( toString(items[39]).equals("1") ) {
				//��d
				super.ExcelSetValue(strLinkNmVal, toString(items[17]));
				
			}
			
			//������d
			if ( toString(items[48]).equals("1") ) {
				//������d
				super.ExcelSetValue(strLinkNmVal, toString(items[49]));
				
			}
// MDO start 20121019 QP@20505 No.24
//			if ( toString(items[40]).equals("1") ) {
//				// �t���[��������
//				super.ExcelSetValue(strLinkNmVal, toString(items[20]));
//				
//			}
//			if ( toString(items[41]).equals("1") ) {
//				//�t���[�A���R�[��
//				super.ExcelSetValue(strLinkNmVal, toString(items[21]));
//				
//			}
			if ( toString(items[55]).equals("1") ) {
				//�t���[���������^�C�g��&�t���[��������
				if ( columnNo == 1 ) {
					super.ExcelSetValue(strLinkNmTitle, toString(items[51]));
				}
				super.ExcelSetValue(strLinkNmVal, toString(items[52]));
			}
			if ( toString(items[56]).equals("1") ) {
				//�t���[���������^�C�g��&�t���[��������
				if ( columnNo == 1 ) {
					super.ExcelSetValue(strLinkNmTitle, toString(items[53]));
				}
				super.ExcelSetValue(strLinkNmVal, toString(items[54]));
			}
// MOD end 20121019 QP@20505 No.24

			if ( toString(items[42]).equals("1") ) {
				//�t���[�^�C�g��&�t���[���e �@
				if ( columnNo == 1 ) {
					super.ExcelSetValue(strLinkNmTitle, toString(items[22]));
					
				}
				super.ExcelSetValue(strLinkNmVal, toString(items[23]));
				
			}
			if ( toString(items[43]).equals("1") ) {
				//�t���[�^�C�g��&�t���[���e �A
				if ( columnNo == 1 ) {
					super.ExcelSetValue(strLinkNmTitle, toString(items[24]));
				
				}
				super.ExcelSetValue(strLinkNmVal, toString(items[25]));
				
			}
			if ( toString(items[44]).equals("1") ) {
				//�t���[�^�C�g��&�t���[���e �B
				if ( columnNo == 1 ) {
					super.ExcelSetValue(strLinkNmTitle, toString(items[26]));
					
				}
				super.ExcelSetValue(strLinkNmVal, toString(items[27]));
				
			}
			
			//����(kg)
			super.ExcelSetValue("����Kg", toDouble(items[45]));
			
//2011/05/11 QP@10181_No.21 TT T.Nishigawa Add Start --------------------------
			//����(��)
			super.ExcelSetValue("������", toDouble(items[47]));
//2011/05/11 QP@10181_No.21 TT T.Nishigawa Add End --------------------------

		} catch(Exception e) {
			em.ThrowException(e, "");
			 
		} finally {
			strLinkNmVal = null;
			strLinkNmTitle = null;
			
		}
		
	}

// ADD start 20121019 QP@20505 No.24
	/**
	 * ����f�[�^��Excel�ɐݒ�
	 *  : ���ʍ��ڂ̒l��ݒ肷��
	 * @param items : ����f�[�^
	 * @param columnNo : ��ԍ�
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private void ExcelSetValueShisaku_p1and2(Object[] items, int columnNo)
		throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		String strLinkNmVal = "�l" + columnNo;
		String strLinkNmTitle = "�^�C�g��";
		
		try {
			//Excel�ɒl��ݒ�
	//�yQP@20505�zNo3 2012/10/09 TT H.SHIMA ADD Start
			if(columnNo <= 3 ){
				super.ExcelSetValue("���i_�T���v��No" + columnNo, "�T���v��:" + toString(items[2]));
				super.ExcelSetValue("���i_�H����No" + columnNo, "�H����:" + toString(items[18]));
				super.ExcelSetValue("���i_���ӎ���" + columnNo, "" + toString(items[50]));
			}
	//�yQP@20505�zNo3 2012/10/09 TT H.SHIMA ADD End
			super.ExcelSetValue("���t", toString(items[1]));
			super.ExcelSetValue("NO", toString(items[2]));
			super.ExcelSetValue("����", toString(items[3]));
			
			//�t���O��1�̏ꍇ�A�\��
			if ( toString(items[28]).equals("1") ) {
				//���_
				super.ExcelSetValue(strLinkNmVal, toString(items[6]));
			}
			if ( toString(items[29]).equals("1") ) {
				//�H��
				super.ExcelSetValue(strLinkNmVal, toString(items[7]));
			}
			if ( toString(items[30]).equals("1") ) {
				//�������_�x
				super.ExcelSetValue(strLinkNmVal, toString(items[8]));
			}
			if ( toString(items[31]).equals("1") ) {
				//�������H��
				super.ExcelSetValue(strLinkNmVal, toString(items[9]));
			}
			if ( toString(items[32]).equals("1") ) {
				//�������|�_
				super.ExcelSetValue(strLinkNmVal, toString(items[10]));
			}
			if ( toString(items[69]).equals("1") ) {
				//�����|�_�Z�x
				super.ExcelSetValue(strLinkNmVal, toString(items[57]));
			}
			if ( toString(items[70]).equals("1") ) {
				//������MSG
				super.ExcelSetValue(strLinkNmVal, toString(items[58]));
			}
			if ( toString(items[36]).equals("1") ) {
				//pH
				super.ExcelSetValue(strLinkNmVal, toString(items[14]));
			}
			if ( toString(items[39]).equals("1") ) {
				//���i��d
				super.ExcelSetValue(strLinkNmVal, toString(items[17]));
			}
			if ( toString(items[48]).equals("1") ) {
				//������d
				super.ExcelSetValue(strLinkNmVal, toString(items[49]));
			}
			if ( toString(items[71]).equals("1") ) {
				//�t���[�S�x�^�C�g��&�t���[�S�x
				if ( columnNo == 1 ) {
					super.ExcelSetValue(strLinkNmTitle, toString(items[59]));
				}
				super.ExcelSetValue(strLinkNmVal, toString(items[60]));
			}
			if ( toString(items[71]).equals("1") ) { // �S�x�ƃt���O�`�F�b�N�{�b�N�X����
				//�t���[���x�^�C�g��&�t���[���x
				if ( columnNo == 1 ) {
					super.ExcelSetValue(strLinkNmTitle, toString(items[61]));
				}
				super.ExcelSetValue(strLinkNmVal, toString(items[62]));
			}
			if ( toString(items[42]).equals("1") ) {
				//�t���[�^�C�g��&�t���[���e �@
				if ( columnNo == 1 ) {
					super.ExcelSetValue(strLinkNmTitle, toString(items[22]));
				}
				super.ExcelSetValue(strLinkNmVal, toString(items[23]));
			}
			if ( toString(items[43]).equals("1") ) {
				//�t���[�^�C�g��&�t���[���e �A
				if ( columnNo == 1 ) {
					super.ExcelSetValue(strLinkNmTitle, toString(items[24]));
				}
				super.ExcelSetValue(strLinkNmVal, toString(items[25]));
			}
			if ( toString(items[44]).equals("1") ) {
				//�t���[�^�C�g��&�t���[���e �B
				if ( columnNo == 1 ) {
					super.ExcelSetValue(strLinkNmTitle, toString(items[26]));
				}
				super.ExcelSetValue(strLinkNmVal, toString(items[27]));
			}
			if ( toString(items[73]).equals("1") ) {
				//�t���[�^�C�g��&�t���[���e �C
				if ( columnNo == 1 ) {
					super.ExcelSetValue(strLinkNmTitle, toString(items[63]));
				}
				super.ExcelSetValue(strLinkNmVal, toString(items[64]));
			}
			if ( toString(items[74]).equals("1") ) {
				//�t���[�^�C�g��&�t���[���e �D
				if ( columnNo == 1 ) {
					super.ExcelSetValue(strLinkNmTitle, toString(items[65]));
				}
				super.ExcelSetValue(strLinkNmVal, toString(items[66]));
			}
			if ( toString(items[75]).equals("1") ) {
				//�t���[�^�C�g��&�t���[���e �E
				if ( columnNo == 1 ) {
					super.ExcelSetValue(strLinkNmTitle, toString(items[67]));
				}
				super.ExcelSetValue(strLinkNmVal, toString(items[68]));
			}
			//����(kg)
			super.ExcelSetValue("����Kg", toDouble(items[45]));
			//����(��)
			super.ExcelSetValue("������", toDouble(items[47]));
		} catch(Exception e) {
			em.ThrowException(e, "");
		} finally {
			strLinkNmVal = null;
			strLinkNmTitle = null;
		}
	}
// ADD end 20121019 QP@20505 No.24

	/**
	 * �H�����v�ʂ�Excel�ɐݒ�
	 * @param lstKoteiGoukei : �H�����v�ʃ��X�g
	 * @param intShosuKeta : ��������
	 * @param intColumn : ��
	 * @return ���v�d��
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private Double ExcelSetValueKotei(List<Double> lstKoteiGoukei, int intShosuKeta, int intColumn)
		throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		Double ret = 0.0;
		
		try {

			for(int i=0; i<lstKoteiGoukei.size(); i++) {
				
				//���v�d�ʂ����߂�
				ret += lstKoteiGoukei.get(i);
				
				//�H�����v�ʃ^�C�g����Excel�ɒl���Z�b�g				
				if ( intColumn == 0 ) {
					if ( i==0 ) {
						super.ExcelSetValue("�H���^�C�g��", "�P�H��(g)");
						
					} else {
						super.ExcelSetValue("�H���^�C�g��", Integer.toString(i+1).toUpperCase() + "�H��(g)");
						
					}
					
				}

				//�H�����v�ʂ�Excel�ɒl���Z�b�g
				if ( lstKoteiGoukei.get(i) != 0.0 ) {
					super.ExcelSetValue("�H�����v��" + (intColumn+1), toRoundString(toDouble(lstKoteiGoukei.get(i)),intShosuKeta) );
					
				} else {
					super.ExcelSetValue("�H�����v��" + (intColumn+1), "");
					
				}
								
			}

		} catch (ExceptionWaning e) {
			//�ő�s���𒴂���ƁAExceptionWaning��Throw�����
			//�������I������
			
		} catch(Exception e) {
			this.em.ThrowException(e, "�H�����v�ʏo�͏����Ɏ��s���܂����B");
			
		} finally {
			
		}
		return ret;
		
	}
	
	/**
	 * ���ږ���Excel�ɐݒ�
	 *  : ���v�d�ʁ`��d�܂ł̍��ږ����ꗗ�ɕ\��
	 * @param items : ����f�[�^
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private void ExcelSetValueTitle(Object[] items)
		throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		String strLinkNmTitle = "�^�C�g��";
		
		try {

			//Excel�ɕ\��
			super.ExcelSetValue("���v�d�ʃ^�C�g��", "���v�d��(g)");
//add start --------------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.1
			super.ExcelSetValue("���v�d��d�ʃ^�C�g��", "���v�d��d��(g)");
//add end --------------------------------------------------------------------------------------	
			super.ExcelSetValue("����Kg�^�C�g��", "������(Kg)");
			
//2011/05/11 QP@10181_No.21 TT T.Nishigawa Add Start --------------------------
			super.ExcelSetValue("�����^�C�g��", "������(1��)");
//2011/05/11 QP@10181_No.21 TT T.Nishigawa Add End --------------------------
			
			//�t���O��1�̏ꍇ�A�\��
			if ( toString(items[28]).equals("1") ) {
				super.ExcelSetValue(strLinkNmTitle, "���_");
				
			}
			if ( toString(items[29]).equals("1") ) {
				super.ExcelSetValue(strLinkNmTitle, "�H��");
				
			}
			if ( toString(items[30]).equals("1") ) {
				super.ExcelSetValue(strLinkNmTitle, "�������_�x");
				
			}
			if ( toString(items[31]).equals("1") ) {
				super.ExcelSetValue(strLinkNmTitle, "�������H��");
				
			}
			if ( toString(items[32]).equals("1") ) {
				super.ExcelSetValue(strLinkNmTitle, "�������|�_");
				
			}
			if ( toString(items[33]).equals("1") ) {
				super.ExcelSetValue(strLinkNmTitle, "���x");
				
			}
			if ( toString(items[34]).equals("1") ) {
				super.ExcelSetValue(strLinkNmTitle, "�S�x");
				
			}
			if ( toString(items[35]).equals("1") ) {
				super.ExcelSetValue(strLinkNmTitle, "���x");
				
			}
			if ( toString(items[36]).equals("1") ) {
				super.ExcelSetValue(strLinkNmTitle, "pH");
				
			}
			if ( toString(items[37]).equals("1") ) {
				super.ExcelSetValue(strLinkNmTitle, "���_�i���́j");
				
			}
			if ( toString(items[38]).equals("1") ) {
				super.ExcelSetValue(strLinkNmTitle, "�H���i���́j");
				
			}
			if ( toString(items[39]).equals("1") ) {
				super.ExcelSetValue(strLinkNmTitle, "���i��d");
				
			}
			if ( toString(items[48]).equals("1") ) {
				super.ExcelSetValue(strLinkNmTitle, "������d");
				
			}
// DEL start 20121019 QP@20505 No.24
//				if ( toString(items[40]).equals("1") ) {
//					super.ExcelSetValue(strLinkNmTitle, "��������");
//					
//				}
//				if ( toString(items[41]).equals("1") ) {
//					super.ExcelSetValue(strLinkNmTitle, "�A���R�[��");
//					
//				}
// DEL end 20121019 QP@20505 No.24

		} catch(Exception e) {
			this.em.ThrowException(e, "���ږ��ݒ菈���Ɏ��s���܂����B");
			
		} finally {
			
		}
	}
	
// ADD start 20121019 QP@20505 No.24
	/**
	 * ���ږ���Excel�ɐݒ�i�H���p�^�[���P�tor�Q�t�̏ꍇ�j
	 *  : ���v�d�ʁ`��d�܂ł̍��ږ����ꗗ�ɕ\��
	 * @param items : ����f�[�^
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private void ExcelSetValueTitle_p1and2(Object[] items)
		throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		String strLinkNmTitle = "�^�C�g��";
		
		try {
			//Excel�ɕ\��
			super.ExcelSetValue("���v�d�ʃ^�C�g��", "���v�d��(g)");
			super.ExcelSetValue("���v�d��d�ʃ^�C�g��", "���v�d��d��(g)");
			super.ExcelSetValue("����Kg�^�C�g��", "������(Kg)");
			super.ExcelSetValue("�����^�C�g��", "������(1��)");

			//�t���O��1�̏ꍇ�A�\��
			if ( toString(items[28]).equals("1") ) {
				super.ExcelSetValue(strLinkNmTitle, "���_");
			}
			if ( toString(items[29]).equals("1") ) {
				super.ExcelSetValue(strLinkNmTitle, "�H��");
			}
			if ( toString(items[30]).equals("1") ) {
				super.ExcelSetValue(strLinkNmTitle, "�������_�x");
			}
			if ( toString(items[31]).equals("1") ) {
				super.ExcelSetValue(strLinkNmTitle, "�������H��");
			}
			if ( toString(items[32]).equals("1") ) {
				super.ExcelSetValue(strLinkNmTitle, "�������|�_");
			}
			if ( toString(items[69]).equals("1") ) {
				super.ExcelSetValue(strLinkNmTitle, "�����|�_�Z�x");
			}
			if ( toString(items[70]).equals("1") ) {
				super.ExcelSetValue(strLinkNmTitle, "�������l�r�f");
			}
			if ( toString(items[36]).equals("1") ) {
				super.ExcelSetValue(strLinkNmTitle, "pH");
			}
			if ( toString(items[39]).equals("1") ) {
				super.ExcelSetValue(strLinkNmTitle, "���i��d");
			}
			if ( toString(items[48]).equals("1") ) {
				super.ExcelSetValue(strLinkNmTitle, "������d");
			}
		} catch(Exception e) {
			this.em.ThrowException(e, "���ږ��ݒ菈���Ɏ��s���܂����B");
		} finally {
		}
	}
// ADD end 20121019 QP@20505 No.24
	
	/**
	 * �Ώۂ̔z���f�[�^����������
	 * @param KindBean : ���N�G�X�g�f�[�^�i�@�\�j
	 * @return List : �������ʂ̃��X�g
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning : �Y���f�[�^����
	 */
	private List<?> getHaigoData(RequestResponsKindBean KindBean) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		//��������
		List<?> lstHaigoData = null;
		List<?> lstShisakuListData = null;
		//�o�̓f�[�^
		List<?> ret = null;
		//SQL�@StringBuffer
		StringBuffer strSql = new StringBuffer();
		
		try {
			//�@ :�@�z���f�[�^�̌���
			
			//SQL���̍쐬
			strSql = MakeHaigoSQLBuf(KindBean);
			
			//SQL�����s
			lstHaigoData = searchDB.dbSearch(strSql.toString());
			
			//�������ʂ��Ȃ��ꍇ
			if (lstHaigoData.size() == 0){
				em.ThrowException(ExceptionKind.�x��Exception,"W000401", strSql.toString(), "", "");
				
			}

			//�A :�@���샊�X�g�f�[�^�̌���
			
			//SQL���̍쐬
			strSql = MakeShisakuListSQLBuf(KindBean);
			
			//SQL�����s
			lstShisakuListData = searchDB.dbSearch(strSql.toString());
			
			//�������ʂ��Ȃ��ꍇ
			if (lstShisakuListData.size() == 0){
				em.ThrowException(ExceptionKind.�x��Exception,"W000401", strSql.toString(), "", "");
				
			}

			//�B �z���f�[�^�Ǝ��샊�X�g�f�[�^�����킹�āA�o�͗p���X�g���쐬����
			ret = CreateHaigoShisakuListData(lstHaigoData, lstShisakuListData);
			
		} catch (Exception e) {
			em.ThrowException(e, "����\�o�́A�z���f�[�^�����Ɏ��s���܂����B");

		} finally {
			//���X�g�̔j��
			removeList(lstHaigoData);
			removeList(lstShisakuListData);
			
			//�ϐ��̍폜
			strSql = null;

		}
		return ret;
		
	}
	/**
	 * �Ώۂ̎���f�[�^����������
	 * @param KindBean : ���N�G�X�g�f�[�^�i�@�\�j
	 * @return List : �������ʂ̃��X�g
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning : �Y���f�[�^����
	 */
	private List<?> getShisakuData(RequestResponsKindBean KindBean) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		//��������
		List<?> ret = null;
		//SQL�@StringBuffer
		StringBuffer strSql = new StringBuffer();
		
		try {
			//�@ ����f�[�^��������

			//SQL���̍쐬
			strSql = MakeShisakuSQLBuf(KindBean);
			
			//SQL�����s
			ret = searchDB.dbSearch(strSql.toString());
			
			//�������ʂ��Ȃ��ꍇ
			if (ret.size() == 0){
				em.ThrowException(ExceptionKind.�x��Exception,"W000401", strSql.toString(), "", "");
				
			}
			
		} catch (Exception e) {
			em.ThrowException(e, "����\�o�́A����f�[�^�����Ɏ��s���܂����B");

		} finally {
			//�ϐ��̍폜
			strSql = null;

		}
		return ret;
		
	}

	/**
	 * ���ӎ����擾����
	 * @param KindBean : ���N�G�X�g�f�[�^�i�@�\�j
	 * @param lstShisakuData : ����f�[�^�����f�[�^
	 * @return�@��������
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning : �Y���f�[�^����
	 */
	private String getChuijikoData(RequestResponsKindBean reqData, List<?> lstShisakuData)
		throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		//���ӎ���
		String ret = null;		
		//��������
		List<?> lstRecset = null;
		//SQL�@StringBuffer
		StringBuffer strSql = new StringBuffer();
						
		try {
			
			//�@ : �����H���f�[�^�����pSQL���擾����
			strSql = MakeSeizokoteiSQLBuf(reqData);
			
			//�B : �擾���������pSQL�����s����
			lstRecset = searchDB.dbSearch(strSql.toString());
			
			//�D : �������ʂ�����ꍇ�A�ŐV�̒��ӎ������擾����i���ӎ���NO�̍ő�l�𔻒�j
			if (lstRecset.size() != 0){
				
				int intMaxChuiNo = 0;

				for ( int j=0; j<lstRecset.size(); j++ ) {
					//�����H�������f�[�^�̎擾
					Object[] items = (Object[])lstRecset.get(j);
					
					//���ӎ���NO�̍ő�l�𔻒肵�A�ő�l�̒��ӎ������擾����
					if ( !toString(items[0]).isEmpty() ) {
					
						if ( intMaxChuiNo < Integer.parseInt(toString(items[0]))  ) {
							//���ӎ���NO��ݒ�
							intMaxChuiNo = Integer.parseInt(toString(items[0]));		
							//���ӎ�����ݒ�
							ret = toString(items[1]);											
							
						}
					
					}
					
				}
				
			}
								
		} catch (Exception e) {
			em.ThrowException(e, "���ӎ����擾�����Ɏ��s���܂����B");

		} finally {
			//���X�g�̔j��
			removeList(lstRecset);
			
			//�ϐ��̍폜
			strSql = null;

		}
		return ret;
		
	}	
	
	/**
	 * ���N�G�X�g�f�[�^���A�z���f�[�^����SQL�𐶐�����
	 *  : ����i�e�[�u���A�z���e�[�u�����������A�z���f�[�^�𐶐�����B
	 * @param reqData : ���N�G�X�g�f�[�^�i�@�\�j
	 * @return StringBuffer : �z���f�[�^����SQL
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private StringBuffer MakeHaigoSQLBuf(RequestResponsKindBean reqData) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{
	
		StringBuffer ret = new StringBuffer();
		String strCd_shain = "";
		String strNen = "";
		String strNo_oi = "";
		String strCd_kotei = "";
		String strSeq_kotei = "";

		try {

			//���N�G�X�g�p�����[�^���A����R�[�h���擾����
			strCd_shain = reqData.getFieldVale(0, 0, "cd_shain");
			strNen = reqData.getFieldVale(0, 0, "nen");
			strNo_oi = reqData.getFieldVale(0, 0, "no_oi");
	
			//SQL���̍쐬
			ret.append(" SELECT DISTINCT ");
			ret.append("  RIGHT('0000000000'+CONVERT(varchar,T110.cd_shain),10)  ");
			ret.append("   +'-'+ RIGHT('00'+CONVERT(varchar,T110.nen),2)  ");
			ret.append("   +'-'+ RIGHT('000'+CONVERT(varchar,T110.no_oi),3) AS ����R�[�h  ");
			ret.append("  ,ISNULL(CONVERT(VARCHAR,T110.no_irai),'') AS �˗��ԍ� ");
			ret.append("  ,T110.nm_hin AS �i�� ");
			ret.append("  ,ISNULL(T110.memo,'') AS ���� ");
			ret.append("  ,M104.nm_kaisha AS ���� ");
			ret.append("  ,M104.nm_busho AS �I���H�� ");
			ret.append("  ,M101_shain.nm_user AS ����� ");
			ret.append("  ,CONVERT(VARCHAR,GETDATE(),111) + ' ' ");
			ret.append("    + CONVERT(VARCHAR,DATEPART(hour,GETDATE())) + ':' ");
			ret.append("    + CONVERT(VARCHAR,DATEPART(minute,GETDATE()))  AS ���s�� ");
			ret.append("  ,T120.cd_kotei AS cd_kotei ");
			ret.append("  ,T120.seq_kotei AS seq_kotei ");
			ret.append("  ,ISNULL(T120.nm_kotei,'') AS �H���� ");
			ret.append("  ,ISNULL(T120.zoku_kotei,'') AS �H������ ");
			ret.append("  ,ISNULL(T120.cd_genryo,'') AS �����R�[�h ");
			ret.append("  ,ISNULL(T120.nm_genryo,'') AS ������ ");
			ret.append("  ,ISNULL(CONVERT(VARCHAR,T120.tanka),'') AS �P�� ");
			ret.append("  ,ISNULL(CONVERT(VARCHAR,T120.budomari),'') AS ���� ");
			ret.append("  ,ISNULL(CONVERT(VARCHAR,T120.ritu_abura),'') AS ���ܗL�� ");
			ret.append("  ,ISNULL(CONVERT(VARCHAR,T120.ritu_sakusan),'') AS �|�_ ");
			ret.append("  ,ISNULL(CONVERT(VARCHAR,T120.ritu_shokuen),'') AS �H�� ");
			ret.append("  ,ISNULL(CONVERT(VARCHAR,T120.ritu_sousan),'') AS ���_ ");
			ret.append("  ,ISNULL(M302.value1,0) AS �������� ");
			ret.append("  ,ISNULL(T110.memo_shisaku,'') AS ���상�� ");
			ret.append("  ,ISNULL(T120.color,'') AS color ");
			ret.append("  ,T120.sort_kotei AS sort_kotei ");
			ret.append("  ,T120.sort_genryo AS sort_genryo ");
			ret.append(" FROM tr_shisakuhin T110 ");
			ret.append("  LEFT JOIN tr_haigo T120 ");
			ret.append("   ON  T120.cd_shain = T110.cd_shain ");
			ret.append("   AND T120.nen = T110.nen ");
			ret.append("   AND T120.no_oi = T110.no_oi ");
			ret.append("  LEFT JOIN ma_busho M104 ");
			ret.append("   ON  M104.cd_kaisha = T110.cd_kaisha ");
			ret.append("   AND M104.cd_busho = T110.cd_kojo ");
			ret.append("  LEFT JOIN ma_user M101_shain ");
			ret.append("   ON  M101_shain.id_user = T110.cd_shain ");
			ret.append("  LEFT JOIN ma_literal M302 ");
			ret.append("   ON  M302.cd_category = 'K_shosu' ");
			ret.append("   AND  M302.cd_literal = T110.keta_shosu ");
			ret.append(" WHERE  ");
			
			//����R�[�h�����������ɐݒ�
			ret.append("  T110.cd_shain=" + strCd_shain);
			ret.append("  AND T110.nen=" + strNen);
			ret.append("  AND T110.no_oi=" + strNo_oi);
			
			//�H��CD�E�H��SEQ�����������ɐݒ�
			ret.append(" AND ( ");
			for ( int i=0; i<reqData.getCntRow(0); i++ ) {
				
				//���N�G�X�g�p�����[�^���A�H��CD�E�H��SEQ���擾����
				strCd_kotei = reqData.getFieldVale(0, i, "cd_kotei");
				strSeq_kotei = reqData.getFieldVale(0, i, "seq_kotei");
				
				//������ǉ�
				if ( i != 0 ) {
					ret.append("  OR ");
				}
				ret.append("   (T120.cd_kotei=" + strCd_kotei);
				ret.append("    AND T120.seq_kotei=" + strSeq_kotei + ")");
				
			}
			ret.append(" ) ");

			//�擾��
			ret.append(" ORDER BY T120.sort_kotei, T120.sort_genryo ");

			
		} catch (Exception e) {
			this.em.ThrowException(e, "����\�A�z���f�[�^����SQL�̐����Ɏ��s���܂����B");
			
		} finally {
			//�ϐ��̍폜
			strCd_shain = null;
			strNen = null;
			strNo_oi = null;
			strCd_kotei = null;
			strSeq_kotei = null;
	
		}
		return ret;
		
	}
		
	/**
	 * ���N�G�X�g�f�[�^���A���샊�X�g�f�[�^����SQL�𐶐�����
	 * @param reqData : ���N�G�X�g�f�[�^�i�@�\�j
	 * @return StringBuffer : ����f�[�^����SQL
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private StringBuffer MakeShisakuListSQLBuf(RequestResponsKindBean reqData) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{
	
		StringBuffer ret = new StringBuffer();
		String strCd_shain = "";
		String strNen = "";
		String strNo_oi = "";
		String strSeq_shisaku = "";
		String strCd_kotei = "";
		String strSeq_kotei = "";

		try {

			//���N�G�X�g�p�����[�^���A����R�[�h���擾����
			strCd_shain = reqData.getFieldVale(0, 0, "cd_shain");
			strNen = reqData.getFieldVale(0, 0, "nen");
			strNo_oi = reqData.getFieldVale(0, 0, "no_oi");
	
			//SQL���̍쐬
			ret.append(" SELECT T132.seq_shisaku AS seq_shisaku ");
			ret.append("    ,T132.cd_kotei AS cd_kotei ");
			ret.append("    ,T132.seq_kotei AS seq_kotei ");
			ret.append("    ,ISNULL(CONVERT(VARCHAR,T132.quantity),'') AS �� ");
			ret.append("    ,ISNULL(T132.color,'') AS color ");
			ret.append("  FROM tr_shisakuhin T110 ");
			ret.append("    LEFT JOIN tr_haigo T120 ");
			ret.append("     ON  T120.cd_shain = T110.cd_shain ");
			ret.append("     AND T120.nen = T110.nen ");
			ret.append("     AND T120.no_oi = T110.no_oi ");
			ret.append("    LEFT JOIN tr_shisaku T131 ");
			ret.append("     ON  T131.cd_shain = T110.cd_shain ");
			ret.append("     AND T131.nen = T110.nen ");
			ret.append("     AND T131.no_oi = T110.no_oi ");
			ret.append("    LEFT JOIN tr_shisaku_list T132 ");
			ret.append("     ON  T132.cd_shain = T110.cd_shain ");
			ret.append("     AND T132.nen = T110.nen ");
			ret.append("     AND T132.no_oi = T110.no_oi ");
			ret.append("     AND T132.seq_shisaku = T131.seq_shisaku ");
			ret.append("     AND T132.cd_kotei = T120.cd_kotei ");
			ret.append("     AND T132.seq_kotei = T120.seq_kotei ");
			ret.append(" WHERE  ");

			//����R�[�h�����������ɐݒ�
			ret.append("  T110.cd_shain=" + strCd_shain);
			ret.append("  AND T110.nen=" + strNen);
			ret.append("  AND T110.no_oi=" + strNo_oi);

			//����SEQ
			ret.append("  AND T132.seq_shisaku IN( ");
			for ( int i=0; i<reqData.getCntRow(0); i++ ) {
				
				//���N�G�X�g�p�����[�^���A����SEQ���擾����
				strSeq_shisaku = reqData.getFieldVale(0, i, "seq_shisaku");
				
				//SQL���ɒǉ�
				if ( i != 0 ) {
					ret.append(",");
					
				}
				ret.append(strSeq_shisaku);
				
			}
			ret.append("  ) ");
			
			//�H��CD�E�H��SEQ�����������ɐݒ�
			ret.append(" AND ( ");
			for ( int i=0; i<reqData.getCntRow(0); i++ ) {
				
				//���N�G�X�g�p�����[�^���A�H��CD�E�H��SEQ���擾����
				strCd_kotei = reqData.getFieldVale(0, i, "cd_kotei");
				strSeq_kotei = reqData.getFieldVale(0, i, "seq_kotei");
				
				//������ǉ�
				if ( i != 0 ) {
					ret.append("  OR ");
					
				}
				ret.append("   (T132.cd_kotei=" + strCd_kotei);
				ret.append("    AND T132.seq_kotei=" + strSeq_kotei + ")");
				
			}
			ret.append(" ) ");
			
			//�擾��
			ret.append(" ORDER BY T131.sort_shisaku, T120.sort_kotei, T120.sort_genryo ");
			
		} catch (Exception e) {
			this.em.ThrowException(e, "����\�A���샊�X�g�f�[�^����SQL�̐����Ɏ��s���܂����B");
			
		} finally {
			//�ϐ��̍폜
			strCd_shain = null;
			strNen = null;
			strNo_oi = null;
			strSeq_shisaku = null;
			strCd_kotei = null;
			strSeq_kotei = null;
	
		}
		return ret;
		
	}
	
	/**
	 * ���N�G�X�g�f�[�^���A����f�[�^����SQL�𐶐�����
	 * @param reqData : ���N�G�X�g�f�[�^�i�@�\�j
	 * @return StringBuffer : ����f�[�^����SQL
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private StringBuffer MakeShisakuSQLBuf(RequestResponsKindBean reqData) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{
	
		StringBuffer ret = new StringBuffer();
		String strCd_shain = "";
		String strNen = "";
		String strNo_oi = "";
		String strSeq_shisaku = "";

		try {
			//���N�G�X�g�p�����[�^���A����R�[�h���擾����
			strCd_shain = reqData.getFieldVale(0, 0, "cd_shain");
			strNen = reqData.getFieldVale(0, 0, "nen");
			strNo_oi = reqData.getFieldVale(0, 0, "no_oi");
			
			//SQL���̍쐬
			ret.append(" SELECT  ");
			ret.append("    T131.seq_shisaku AS seq_shisaku "); // 0
			ret.append("   ,ISNULL(CONVERT(VARCHAR,T131.dt_shisaku,111),'') AS ����� "); // 1
			ret.append("   ,ISNULL(T131.nm_sample,'') AS �T���v��NO "); // 2
			ret.append("   ,ISNULL(T131.memo,'') AS ���� "); // 3
//			ret.append("   ,CASE WHEN T131.flg_memo=0 ");
//			ret.append("     THEN ISNULL(T131.memo_sakusei,'') ");
//			ret.append("     ELSE '' END AS �쐬���� ");
			ret.append("   ,ISNULL(T131.memo_sakusei,'') AS �쐬���� "); // 4
//			ret.append("   ,CASE WHEN T131.flg_hyoka=0 ");
//			ret.append("     THEN ISNULL(T131.hyoka,'') ");
//			ret.append("     ELSE '' END AS �]�� ");
			ret.append("   ,ISNULL(T131.hyoka,'') AS �]�� "); // 5
			ret.append("   ,CASE WHEN T131.flg_sousan=1 ");
			ret.append("     THEN ISNULL(CONVERT(VARCHAR,T131.ritu_sousan),'') ");
			ret.append("     ELSE '' END AS ���_ "); // 6
			ret.append("   ,CASE WHEN T131.flg_shokuen=1 ");
			ret.append("     THEN ISNULL(CONVERT(VARCHAR,T131.ritu_shokuen),'') ");
			ret.append("     ELSE '' END AS �H�� "); // 7
			ret.append("   ,CASE WHEN T131.flg_sando_suiso=1 ");
			ret.append("     THEN ISNULL(CONVERT(VARCHAR,T131.sando_suiso),'') ");
			ret.append("     ELSE '' END AS �������_�x "); // 8
			ret.append("   ,CASE WHEN T131.flg_shokuen_suiso=1 ");
			ret.append("     THEN ISNULL(CONVERT(VARCHAR,T131.shokuen_suiso),'') ");
			ret.append("     ELSE '' END AS �������H�� "); // 9
			ret.append("   ,CASE WHEN T131.flg_sakusan_suiso=1 ");
			ret.append("     THEN ISNULL(CONVERT(VARCHAR,T131.sakusan_suiso),'') ");
			ret.append("     ELSE '' END AS �������|�_ "); // 10
			ret.append("   ,CASE WHEN T131.flg_toudo=1 ");
			ret.append("     THEN ISNULL(T131.toudo,'') ");
			ret.append("     ELSE '' END AS ���x "); // 11
			ret.append("   ,CASE WHEN T131.flg_nendo=1 ");
			ret.append("     THEN ISNULL(T131.nendo,'') ");
			ret.append("     ELSE '' END AS �S�x "); // 12
			ret.append("   ,CASE WHEN T131.flg_ondo=1 ");
			ret.append("     THEN ISNULL(T131.ondo,'') ");
			ret.append("     ELSE '' END AS ���x "); // 13
			ret.append("   ,CASE WHEN T131.flg_ph=1 ");
			ret.append("     THEN ISNULL(T131.ph,'') ");
			ret.append("     ELSE '' END AS PH "); // 14
			ret.append("   ,CASE WHEN T131.flg_sousan_bunseki=1 ");
			ret.append("     THEN ISNULL(T131.ritu_sousan_bunseki,'') ");
			ret.append("     ELSE '' END AS ���_���� "); // 15
			ret.append("   ,CASE WHEN T131.flg_shokuen_bunseki=1 ");
			ret.append("     THEN ISNULL(T131.ritu_shokuen_bunseki,'') ");
			ret.append("     ELSE '' END AS �H������ "); // 16
			ret.append("   ,CASE WHEN T131.flg_hiju=1 ");
			ret.append("     THEN ISNULL(T131.hiju,'') ");
			ret.append("     ELSE '' END AS ��d "); // 17
			ret.append("   ,ISNULL(T131.no_chui,'') AS ���ӎ���No "); // 18
			ret.append("   ,ISNULL(CONVERT(VARCHAR,T131.juryo_shiagari_g),'') AS ���v�d�オ��d�� "); // 19
			//�������� �` �t���[�B
			ret.append("  ,CASE WHEN T131.flg_suibun_kasei=1 THEN ISNULL(T131.suibun_kasei,'') ELSE '' END AS �������� "); // 20
			ret.append("  ,CASE WHEN T131.flg_alcohol=1 THEN ISNULL(T131.alcohol,'') ELSE '' END AS �A���R�[�� "); // 21
			ret.append("  ,CASE WHEN T131.flg_free1=1 THEN ISNULL(T131.free_title1,'') ELSE '' END AS �t���[�^�C�g��1 "); // 22
			ret.append("  ,CASE WHEN T131.flg_free1=1 THEN ISNULL(T131.free_value1,'') ELSE '' END AS �t���[���e1 "); // 23
			ret.append("  ,CASE WHEN T131.flg_free2=1 THEN ISNULL(T131.free_title2,'') ELSE '' END AS �t���[�^�C�g��2 "); // 24
			ret.append("  ,CASE WHEN T131.flg_free2=1 THEN ISNULL(T131.free_value2,'') ELSE '' END AS �t���[���e2 "); // 25
			ret.append("  ,CASE WHEN T131.flg_free3=1 THEN ISNULL(T131.free_title3,'') ELSE '' END AS �t���[�^�C�g��3 "); // 26
			ret.append("  ,CASE WHEN T131.flg_free3=1 THEN ISNULL(T131.free_value3,'') ELSE '' END AS �t���[���e3 "); // 27
			//�t���O�擾 ���_�`�A���R�[��
			ret.append("  ,ISNULL(T131.flg_sousan,0) AS flg_sousan "); // 28
			ret.append("  ,ISNULL(T131.flg_shokuen,0) AS flg_shokuen "); // 29
			ret.append("  ,ISNULL(T131.flg_sando_suiso,0) AS flg_sando_suiso "); // 30
			ret.append("  ,ISNULL(T131.flg_shokuen_suiso,0) AS flg_shokuen_suiso "); // 31
			ret.append("  ,ISNULL(T131.flg_sakusan_suiso,0) AS flg_sakusan_suiso "); // 32
			ret.append("  ,ISNULL(T131.flg_toudo,0) AS flg_toudo "); // 33
			ret.append("  ,ISNULL(T131.flg_nendo,0) AS flg_nendo "); // 34
			ret.append("  ,ISNULL(T131.flg_ondo,0) AS flg_ondo "); // 35
			ret.append("  ,ISNULL(T131.flg_ph,0) AS flg_ph "); // 36
			ret.append("  ,ISNULL(T131.flg_sousan_bunseki,0) AS flg_sousan_bunseki "); // 37
			ret.append("  ,ISNULL(T131.flg_shokuen_bunseki,0) AS flg_shokuen_bunseki "); // 38
			ret.append("  ,ISNULL(T131.flg_hiju,0) AS flg_hiju "); // 39
			ret.append("  ,ISNULL(T131.flg_suibun_kasei,0) AS flg_suibun_kasei "); // 40
			ret.append("  ,ISNULL(T131.flg_alcohol,0) AS flg_alcohol "); // 41

			ret.append("  ,ISNULL(T131.flg_free1,0) AS flg_free1 "); // 42
			ret.append("  ,ISNULL(T131.flg_free2,0) AS flg_free2 "); // 43
			ret.append("  ,ISNULL(T131.flg_free3,0) AS flg_free3 "); // 44
			//�����ikg�j : ���������e�[�u��.������
			ret.append("  ,T141.genryohi AS genka "); // 45
			//���쏇
			ret.append("   ,T131.sort_shisaku AS sort_shisaku "); // 46
	//2011/05/11 QP@10181_No.21 TT T.Nishigawa Add Start --------------------------
			//�����i�j : ���������e�[�u��.������i1�{�j
			ret.append("  ,T141.genryohi1 AS genka1 "); // 47
			
			ret.append("  ,ISNULL(T131.flg_hiju_sui,0) AS flg_hiju_sui "); // 48
			ret.append("  ,CASE WHEN T131.flg_hiju_sui=1 ");
			ret.append("     THEN ISNULL(T131.hiju_sui,'') ");
			ret.append("     ELSE '' END AS ������d "); // 49
	//2011/05/11 QP@10181_No.21 TT T.Nishigawa Add End --------------------------

			//�yQP@20505�zNo3 2012/10/09 TT H.SHIMA ADD Start
			ret.append(",    ISNULL(T133.chuijiko, '') AS ���ӎ��� "); // 50
			//�yQP@20505�zNo3 2012/10/09 TT H.SHIMA ADD End
//ADD start 20121019 QP@20505 No.24
			ret.append("  ,CASE WHEN T131.flg_freeSuibunKasei=1 THEN ISNULL(T131.freetitle_suibun_kasei,'') ELSE '' END AS �t���[���������^�C�g�� "); // 51
			ret.append("  ,CASE WHEN T131.flg_freeSuibunKasei=1 THEN ISNULL(T131.free_suibun_kasei,'') ELSE '' END AS �t���[�������� "); // 52
			ret.append("  ,CASE WHEN T131.flg_freeAlchol=1 THEN ISNULL(T131.freetitle_alcohol,'') ELSE '' END AS �t���[�A���R�[���^�C�g�� "); // 53
			ret.append("  ,CASE WHEN T131.flg_freeAlchol=1 THEN ISNULL(T131.free_alcohol,'') ELSE '' END AS �t���[�A���R�[�� "); // 54
			// DEL start 20130226 QP@20505 ������̏C��
//			ret.append("  ,ISNULL(T131.flg_suibun_kasei,0) AS flg_freeSuibunKasei ");
//			ret.append("  ,ISNULL(T131.flg_alcohol,0) AS flg_freeAlchol ");
			// DEL end 20130226 QP@20505 ������̏C��
			// ADD start 20130226 QP@20505 ������̏C��
			ret.append("  ,ISNULL(T131.flg_freeSuibunKasei,0) AS flg_freeSuibunKasei "); // 55
			ret.append("  ,ISNULL(T131.flg_freeAlchol,0) AS flg_freeAlchol "); // 56
			// ADD end 20130226 QP@20505 ������̏C��
			ret.append("   ,CASE WHEN T131.flg_jikkoSakusanNodo=1 ");
			ret.append("     THEN ISNULL(CONVERT(VARCHAR,T131.jikkoSakusanNodo),'') ");
			ret.append("     ELSE '' END AS �����|�_�Z�x "); // 57
			ret.append("   ,CASE WHEN T131.flg_msg_suiso=1 ");
			ret.append("     THEN ISNULL(CONVERT(VARCHAR,T131.msg_suiso),'') ");
			ret.append("     ELSE '' END AS �������l�r�f "); // 58			
			ret.append("  ,CASE WHEN T131.flg_freeNendo=1 THEN ISNULL(T131.freetitle_nendo,'') ELSE '' END AS �t���[�S�x�^�C�g�� "); // 59
			ret.append("  ,CASE WHEN T131.flg_freeNendo=1 THEN ISNULL(T131.free_nendo,'') ELSE '' END AS �t���[�S�x "); // 60
			ret.append("  ,CASE WHEN T131.flg_freeNendo=1 THEN ISNULL(T131.freetitle_ondo,'') ELSE '' END AS �t���[���x�^�C�g�� "); // 61 ���t���O�͔S�x�Ƌ���
			ret.append("  ,CASE WHEN T131.flg_freeNendo=1 THEN ISNULL(T131.free_ondo,'') ELSE '' END AS �t���[���x "); // 62
			ret.append("  ,CASE WHEN T131.flg_free4=1 THEN ISNULL(T131.free_title4,'') ELSE '' END AS �t���[�^�C�g��4 "); // 63
			ret.append("  ,CASE WHEN T131.flg_free4=1 THEN ISNULL(T131.free_value4,'') ELSE '' END AS �t���[���e4 "); // 64
			ret.append("  ,CASE WHEN T131.flg_free5=1 THEN ISNULL(T131.free_title5,'') ELSE '' END AS �t���[�^�C�g��5 "); // 65
			ret.append("  ,CASE WHEN T131.flg_free5=1 THEN ISNULL(T131.free_value5,'') ELSE '' END AS �t���[���e5 "); // 66
			ret.append("  ,CASE WHEN T131.flg_free6=1 THEN ISNULL(T131.free_title6,'') ELSE '' END AS �t���[�^�C�g��6 "); // 67
			ret.append("  ,CASE WHEN T131.flg_free6=1 THEN ISNULL(T131.free_value6,'') ELSE '' END AS �t���[���e6 "); // 68
			
			ret.append("  ,ISNULL(T131.flg_jikkoSakusanNodo,0) AS flg_jikkoSakusanNodo "); // 69
			ret.append("  ,ISNULL(T131.flg_msg_suiso,0) AS flg_msg_suiso "); // 70
			ret.append("  ,ISNULL(T131.flg_freeNendo,0) AS flg_freeNendo "); // 71
			ret.append("  ,ISNULL(T131.flg_freeOndo,0) AS flg_freeOndo "); // 72
			ret.append("  ,ISNULL(T131.flg_free4,0) AS flg_free4 "); // 73
			ret.append("  ,ISNULL(T131.flg_free5,0) AS flg_free5 "); // 74
			ret.append("  ,ISNULL(T131.flg_free6,0) AS flg_free6 "); // 75
//ADD end 20121019 QP@20505 No.24
			ret.append(" FROM tr_shisakuhin T110 ");
			ret.append("  LEFT JOIN tr_shisaku T131 ");
			ret.append("   ON  T131.cd_shain = T110.cd_shain ");
			ret.append("   AND T131.nen = T110.nen ");
			ret.append("   AND T131.no_oi = T110.no_oi ");			
			
			//���������e�[�u��
			ret.append(" LEFT JOIN tr_genryo T141 ");
			ret.append("  ON  T141.cd_shain = T131.cd_shain ");
			ret.append("  AND T141.nen = T131.nen ");
			ret.append("  AND T141.no_oi = T131.no_oi ");
			ret.append("  AND T141.seq_shisaku = T131.seq_shisaku ");
			
			//�yQP@20505�zNo3 2012/10/09 TT H.SHIMA ADD Start
			ret.append(" LEFT JOIN tr_cyuui T133 ");
			ret.append("  ON T133.cd_shain = T131.cd_shain ");
			ret.append("  AND T133.nen = T131.nen");
			ret.append("  AND T133.no_oi = T131.no_oi");
			ret.append("  AND T133.no_chui = T131.no_chui");
			//�yQP@20505�zNo3 2012/10/09 TT H.SHIMA ADD End
			
			ret.append(" WHERE  ");

			//����R�[�h�����������ɐݒ�
			ret.append("  T110.cd_shain=" + strCd_shain);
			ret.append("  AND T110.nen=" + strNen);
			ret.append("  AND T110.no_oi=" + strNo_oi);

			//����SEQ
			ret.append("  AND T131.seq_shisaku IN( ");
			for ( int i=0; i<reqData.getCntRow(0); i++ ) {
				
				//���N�G�X�g�p�����[�^���A����SEQ���擾����
				strSeq_shisaku = reqData.getFieldVale(0, i, "seq_shisaku");
				
				//SQL���ɒǉ�
				if ( i != 0 ) {
					ret.append(",");
					
				}
				ret.append(strSeq_shisaku);
				
			}
			ret.append("  ) ");
			
			//�擾��
			ret.append(" ORDER BY T131.sort_shisaku ");
			
		} catch (Exception e) {
			this.em.ThrowException(e, "����\�A����f�[�^����SQL�̐����Ɏ��s���܂����B");
			
		} finally {
			//�ϐ��̍폜
			strCd_shain = null;
			strNen = null;
			strNo_oi = null;
			strSeq_shisaku = null;
	
		}
		return ret;
		
	}

	/**
	 * ���N�G�X�g�f�[�^���A�����H���f�[�^����SQL�𐶐�����
	 * @param reqData : ���N�G�X�g�f�[�^�i�@�\�j
	 * @return StringBuffer : �����H���f�[�^����SQL
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private StringBuffer MakeSeizokoteiSQLBuf(RequestResponsKindBean reqData)
		throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		StringBuffer ret = new StringBuffer();
		String strCd_shain = "";
		String strNen = "";
		String strNo_oi = "";
		String strSeq_shisaku = "";

		try {

			//���N�G�X�g�p�����[�^���A����R�[�h���擾����
			strCd_shain = reqData.getFieldVale(0, 0, "cd_shain");
			strNen = reqData.getFieldVale(0, 0, "nen");
			strNo_oi = reqData.getFieldVale(0, 0, "no_oi");
	
			//SQL���̍쐬
			ret.append(" SELECT ");
			ret.append("   ISNULL(CONVERT(VARCHAR,T133.no_chui),'') AS ����NO ");
			ret.append("  ,ISNULL(T133.chuijiko,'') AS ���ӎ��� ");
			ret.append(" FROM tr_shisaku T131 ");
			ret.append(" LEFT JOIN tr_cyuui T133 ");
			ret.append("   ON  T133.cd_shain = T131.cd_shain ");
			ret.append("   AND T133.nen = T131.nen ");
			ret.append("   AND T133.no_oi = T131.no_oi ");
			ret.append("   AND T133.no_chui = T131.no_chui ");
			ret.append(" WHERE  ");

			//����R�[�h�����������ɐݒ�
			ret.append("  T131.cd_shain=" + strCd_shain);
			ret.append("  AND T131.nen=" + strNen);
			ret.append("  AND T131.no_oi=" + strNo_oi);

			//����SEQ
			ret.append("  AND T131.seq_shisaku IN( ");
			for ( int i=0; i<reqData.getCntRow(0); i++ ) {
				
				//���N�G�X�g�p�����[�^���A����SEQ���擾����
				strSeq_shisaku = reqData.getFieldVale(0, i, "seq_shisaku");
				
				//SQL���ɒǉ�
				if ( i != 0 ) {
					ret.append(",");
					
				}
				ret.append(strSeq_shisaku);
				
			}
			ret.append("  ) ");
			
			//�擾��
			ret.append(" ORDER BY T131.sort_shisaku ");
			
		} catch (Exception e) {
			this.em.ThrowException(e, "�����H���f�[�^����QL�̐����Ɏ��s���܂����B");
			
		} finally {
			//�ϐ��̍폜
			strCd_shain = null;
			strNen = null;
			strNo_oi = null;
			strSeq_shisaku = null;
	
		}
		return ret;

	}
		
	
	/**
	 * �z���E���샊�X�g�f�[�^�̍쐬
	 * @param lstHaigoData : �z���f�[�^
	 * @param lstShisakuListData : ���샊�X�g�f�[�^
	 * @return �o�̓f�[�^
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private List<?> CreateHaigoShisakuListData(List<?> lstHaigoData, List<?> lstShisakuListData) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		List<Object[]> ret = new ArrayList<Object[]>();
		
		//�ݒ�p�I�u�W�F�N�g
		Object[] add_items = null;
		Object[] haigo_items = null;
		Object[] shisakuList_items = null;
		//�H��CD�E�H��SEQ
		String strCd_kotei = null;
		String strSeq_kotei = null;
		
		//�ޔ�p�H��CD
		String strCd_kotei_taihi = "";
		//�H��CD�ݒ�p�J�E���g
		int intKoteiCount = 0;		
		//���샊�X�g�ݒ�p�J�E���g
		int intShisakuListCount = 0;
		
		//�v�f��
// 2010.11.04 Mod Arai Start  QP@00412_�V�T�N�C�b�N���� ��.1------------------
		int intRecCount = 35;
//		int intRecCount = 25;
// 2010.11.04 Mod Arai End ---------------------------------------------------
		//�����
		int intShisakuRetuCount = 0;
		
		try {
			
			//�z���e�[�u���f�[�^�Ǝ��샊�X�g�e�[�u���f�[�^�����킹��
			
			//�z���f�[�^���X�g�̐ݒ�
			for (int i = 0; i < lstHaigoData.size(); i++ ) {
				
				//�z���f�[�^ �������ʎ擾
				haigo_items = (Object[]) lstHaigoData.get(i);
				
				//�ǉ��p�I�u�W�F�N�g����
				add_items = new Object[intRecCount];
				
				//��{���̐ݒ�
				add_items[0] = haigo_items[0].toString();			//����R�[�h
				add_items[1] = haigo_items[1].toString();			//�˗��ԍ�
				add_items[2] = haigo_items[2].toString();			//�i��
				add_items[3] = haigo_items[4].toString();			//��Ж�
				add_items[4] = haigo_items[5].toString();			//�H�ꖼ
				add_items[5] = haigo_items[6].toString();			//�����
				add_items[6] = haigo_items[7].toString();			//���s��
				add_items[7] = haigo_items[3].toString();			//��������
				add_items[8] = haigo_items[21].toString();		//���상��
				
				//�z���f�[�^�������ʂ̐ݒ�
				
				// �H��CD�E�H��SEQ�̎擾
				strCd_kotei = haigo_items[8].toString();			//�H��CD
				strSeq_kotei = haigo_items[9].toString();			//�H��SEQ
				
				// �H���s�̐ݒ�
				if ( !strCd_kotei.equals(strCd_kotei_taihi) ) {
					//�H��CD��ޔ�
					strCd_kotei_taihi = strCd_kotei; 
										
					//�H��CD�ݒ�p�J�E���g��i�߂�
					intKoteiCount++;
					
					//�H��CD�Ƒޔ�p�H��CD���قȂ�ꍇ�A�u�H������ �H�����v��ݒ�
					add_items[10] = "---";
					add_items[11] = intKoteiCount + "�H����" + haigo_items[10].toString() + "��";
					for ( int j = 12; j < intRecCount; j++ ) {
						//�z���f�[�^
						add_items[j] = "";
						
					}

					//���X�g��s�ǉ�
					ret.add(add_items);	
					
				}
				
				//�ǉ��p�I�u�W�F�N�g�Đ���
				add_items = new Object[intRecCount];
				
				//��{���̐ݒ�
				add_items[0] = haigo_items[0].toString();			//����R�[�h
				add_items[1] = haigo_items[1].toString();			//�˗��ԍ�
				add_items[2] = haigo_items[2].toString();			//�i��
				add_items[3] = haigo_items[3].toString();			//��Ж�
				add_items[4] = haigo_items[4].toString();			//�H�ꖼ
				add_items[5] = haigo_items[5].toString();			//�����
				add_items[6] = haigo_items[6].toString();			//���s��
				add_items[7] = haigo_items[7].toString();			//��������
				add_items[8] = haigo_items[21].toString();		//���상��
				add_items[9] = haigo_items[20].toString();		//��������
				
				//�z���f�[�^�̐ݒ�
				add_items[10] = haigo_items[12].toString();		//�����R�[�h 
				add_items[11] = haigo_items[13].toString();		//������
				add_items[12] = haigo_items[14].toString();		//�P��
				add_items[13] = haigo_items[15].toString();		//����
				
				//���샊�X�g���ʂ��擾(10���ڂ̂�)
				intShisakuListCount = 0;
// 2010.11.04 Mod Arai Start  QP@00412_�V�T�N�C�b�N���� ��.1------------------
				for (int j = 0; intShisakuListCount < 20; j++ ) {
//				for (int j = 0; intShisakuListCount < 10; j++ ) {
// 2010.11.04 Mod Arai End ---------------------------------------------------
					
					//���샊�X�g.�ʂ�ݒ�
					if ( j < lstShisakuListData.size() ) {
						
						//���샊�X�g�f�[�^�@�������ʎ擾
						shisakuList_items = (Object[]) lstShisakuListData.get(j);
						
						//�z���f�[�^�Ǝ��샊�X�g�f�[�^�̍H��CD�E�H��SEQ��������
						if ( strCd_kotei.equals(shisakuList_items[1].toString()) && strSeq_kotei.equals(shisakuList_items[2].toString()) ) {
						
							//�ʂ�ݒ�
							add_items[intShisakuListCount + 14] = shisakuList_items[3].toString();

							//�J�E���g��i�߂�
							intShisakuListCount++;
							
							//����񐔂��J�E���g(�����P�s�ڂ́A�J�E���g���s��)
							if ( ret.size() < 2 ) {
								intShisakuRetuCount++;
							
							}
							
						}
												
					} else {
						
						//�ʂ�ݒ�
						add_items[intShisakuListCount + 14] = "";
						
						//�J�E���g��i�߂�
						intShisakuListCount++;
						
					}
															
				}
					
				//���X�g��s�ǉ�
				ret.add(add_items);	
				
			}

			//�S�Ă̔z���f�[�^�Ɏ�����ݒ�
			for ( int i=0; i<ret.size(); i++ ) {
				
				//�z���f�[�^�擾
				Object[] items = (Object[])ret.get(i);
				
				//������ݒ�
				items[intRecCount-1] = intShisakuRetuCount;
				ret.set(i, items);
				
			}			
			
		} catch(Exception e) {
			this.em.ThrowException(e, "����\�A�z���E���샊�X�g�f�[�^�̍쐬�����Ɏ��s���܂����B");
			
		} finally {
			//�ϐ��̍폜
			add_items = null;
			haigo_items = null;
			shisakuList_items = null;
			strCd_kotei = null;
			strSeq_kotei = null;
			strCd_kotei_taihi = null;
			intKoteiCount = 0;
			intShisakuListCount = 0;
			intRecCount = 0;
			
		}
		return ret;
		
	}
	
	/**
	 * �H�����v�ʎZ�o����
	 * @param lstKoteiGoukei : �H�����v�ʃ��X�g
	 * @param dblRyo : �z����
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private void SetKoteiGoukeiRyo(List<Double> lstKoteiGoukei, int intIndex, Double dblRyo)
		throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		try {
			//�H�����v�ʎZ�o
			lstKoteiGoukei.set(intIndex-1, lstKoteiGoukei.get(intIndex-1) + dblRyo);
			
		} catch(Exception e) {
			this.em.ThrowException(e, "�H�����v�ʎZ�o�����Ɏ��s���܂����B");
			
		} finally {
			
		}
		
	}

//add start --------------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.1
	/**
	 * Double�l�ϊ�����
	 * @param chkVal : �Ώۂ̒l
	 * @return 0��NULL = ��
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private String ChkDblVal(String chkVal)
		throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		String strRet = "";
		
		try {
			//Double�ɕϊ�����0�̏ꍇ�A�󔒂�Ԃ�
			if (toDouble(chkVal) == 0) {
				strRet = "";				
			} else {
				strRet = toString(chkVal);				
			}

		} catch(Exception e) {
			this.em.ThrowException(e, "Double�l�ϊ������Ɏ��s���܂����B");
		} finally {			
		}
		
		return strRet;
	}
//add end --------------------------------------------------------------------------------------
	
}
