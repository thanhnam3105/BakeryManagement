package jp.co.blueflag.shisaquick.srv.logic;

import java.util.List;

import jp.co.blueflag.shisaquick.srv.base.LogicBaseJExcel;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsKindBean;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;
import jp.co.blueflag.shisaquick.srv.common.ExceptionManager.ExceptionKind;

/**
 * �������Z�\�𐶐�����
 * @author jinbo
 * @since  2009/05/21
 */
public class GenkaShisanHyoLogic extends LogicBaseJExcel {
	
	/**
	 * �R���X�g���N�^
	 */
	public GenkaShisanHyoLogic() {
		//�X�[�p�[�N���X�̃R���X�g���N�^�Ăяo��
		super();
		
	}
	/**
	 * �������Z�\�𐶐�����
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
		//�����f�[�^
		List<?> lstRecset = null;
		//�G�N�Z���t�@�C���p�X
		String DownLoadPath = "";
		
		try {
			//DB����
			super.createSearchDB();
			lstRecset = getData(reqData);

			//�H���l
			String strKoteiValue = toString( reqData.getFieldVale(0, 0, "kotei_value"));
			
			//�H���l�ɂ��A�u�������p�^�[���v���u���̑��������ȊO�p�^�[���v���𔻒肷��
			if ( strKoteiValue.equals("1") ) {
				//������Excel�t�@�C������
				DownLoadPath = makeExcelFile1(lstRecset, reqData);
				
			} else if ( strKoteiValue.equals("2") ) {
				//���̑�Excel�t�@�C������
				DownLoadPath = makeExcelFile2(lstRecset, reqData);
				
			}

			//���X�|���X�f�[�^����
			ret = CreateRespons(DownLoadPath);
			
		} catch (Exception e) {
			em.ThrowException(e, "�������Z�\�̐����Ɏ��s���܂����B");

		} finally {
			//���X�g�̔j��
			removeList(lstRecset);
			if (searchDB != null) {
				//DB�Z�b�V�����̃N���[�Y
				searchDB.Close();
				searchDB = null;
			}
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
			ret.setID("SA800");
			
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
	 * ������_�������Z�\(EXCEL)�𐶐�����
	 * @param lstRecset : �����f�[�^���X�g
	 * @return�@String : �_�E�����[�h�p�̃p�X
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private String makeExcelFile1(List<?> lstRecset, RequestResponsKindBean reqData) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		String ret = "";
		String strShisakuSeq1 = "";
		String strShisakuSeq2 = "";
		String strShisakuSeq3 = "";
		int intChuijiko = 0;
						
		try {
			//EXCEL�e���v���[�g��ǂݍ���
			super.ExcelReadTemplate("�������������Z�\");
			
			//����SEQ���擾
			strShisakuSeq1 = toString( reqData.getFieldVale(0, 0, "seq_shisaku1"));
			strShisakuSeq2 = toString( reqData.getFieldVale(0, 0, "seq_shisaku2"));
			strShisakuSeq3 = toString( reqData.getFieldVale(0, 0, "seq_shisaku3"));
			
			//�_�E�����[�h�p��EXCEL�𐶐�����
			for (int i = 0; i < lstRecset.size(); i++) {
				
				//�������ʂ�1�s�������o��
				Object[] items = (Object[]) lstRecset.get(i);

				//ADD 2012.4.30�@�yH24�N�x�Ή��zJEXCEL�Ή��̈גǉ�
				if(i==0){
					//EXCEL�e���v���[�g��ǂݍ���
					ret = super.ExcelOutput(
							makeShisakuNo(
							reqData.getFieldVale(0, 0, "cd_shain")
							,reqData.getFieldVale(0, 0, "nen")
							,reqData.getFieldVale(0, 0, "no_oi")
							));
				}

				try{
					//Excel�ɒl���Z�b�g����
					
					//�i��
					super.ExcelSetValue("�i��", toString(items[2]));
					//�˗��ԍ�
					super.ExcelSetValue("�˗��ԍ�", toString(items[3]));
					//���t
					super.ExcelSetValue("���t", toString(items[4]));
					//�H��R�[�h
//					super.ExcelSetValue("�H��R�[�h", toDouble(items[5]));				//DEL 2012.4.30�@�yH24�N�x�Ή��z�R�[�h�o�͂��疼�̏o�͂ɕύX
					//������
					super.ExcelSetValue("������", toString(items[6]));
					//�c��
					super.ExcelSetValue("�c��", toString(items[7]));					

					//����SEQ���Ƃɕ���
					if ( toString(items[36]).equals(strShisakuSeq1) ){
						//�T���v��No1
						super.ExcelSetValue("�T���v��No1", toString(items[8]));

						//ADD 2012.4.30�@�yH24�N�x�Ή��z���i��񗓂ɍH����1�ҏW
						super.ExcelSetValue("���i_�T���v��No1","�T���v��:" + toString(items[8]));
						if ( !toString(items[37]).isEmpty() ) {
							super.ExcelSetValue("���i_�H����No1","�H����:" + toString(items[37]));
							super.ExcelSetValue("���i_���ӎ���1",toString(items[31]));
						}
						
					} else if ( toString(items[36]).equals(strShisakuSeq2) ){
						//�T���v��No2
						super.ExcelSetValue("�T���v��No2", toString(items[8]));

						//ADD 2012.4.30�@�yH24�N�x�Ή��z���i��񗓂ɍH����2�ҏW
						super.ExcelSetValue("���i_�T���v��No2","�T���v��:" + toString(items[8]));
						if ( !toString(items[37]).isEmpty() ) {
							super.ExcelSetValue("���i_�H����No2","�H����:" + toString(items[37]));
							super.ExcelSetValue("���i_���ӎ���2",toString(items[31]));
						}

					} else if ( toString(items[36]).equals(strShisakuSeq3) ){
						//�T���v��No3
						super.ExcelSetValue("�T���v��No3", toString(items[8]));

						//ADD 2012.4.30�@�yH24�N�x�Ή��z���i��񗓂ɍH����3�ҏW
						super.ExcelSetValue("���i_�T���v��No3","�T���v��:" + toString(items[8]));
						if ( !toString(items[37]).isEmpty() ) {
							super.ExcelSetValue("���i_�H����No3","�H����:" + toString(items[37]));
							super.ExcelSetValue("���i_���ӎ���3",toString(items[31]));
						}

					}

					//�H�����u�E�ے����t�v�̏ꍇ
					if (toString(items[1]).equals("1")) {
						//***** [�E�ے����t] *****

						if ( toString(items[36]).equals(strShisakuSeq1) ) {
							//�V�K����
							super.ExcelSetValue("�H��1_�V�K����", toString(items[9]));
							//�i�R�[�h
							super.ExcelSetValue("�H��1_�i�R�[�h", toString(items[10]));
							//�i��
							super.ExcelSetValue("�H��1_�i��", toString(items[11]));
							//�P��
							super.ExcelSetValue("�H��1_�P��", toDouble(items[12]));
							//����
							super.ExcelSetValue("�H��1_����", (toDouble(items[13]) / 100));
							
						} 

						//����SEQ���Ƃɕ���
						if ( toString(items[36]).equals(strShisakuSeq1) ){
							//�z��1
							super.ExcelSetValue("�H��1_�z��1", toDouble(items[14]));
							
						} else if ( toString(items[36]).equals(strShisakuSeq2) ){
							//�z��2
							super.ExcelSetValue("�H��1_�z��2", toDouble(items[14]));
							
						} else if ( toString(items[36]).equals(strShisakuSeq3) ){
							//�z��3
							super.ExcelSetValue("�H��1_�z��3", toDouble(items[14]));
							
						}

					//�H�����u�����v�̏ꍇ
					} else if (toString(items[1]).equals("2")) {
						//***** [����] *****

						if ( toString(items[36]).equals(strShisakuSeq1) ){
							//�V�K����
							super.ExcelSetValue("�H��2_�V�K����", toString(items[9]));
							//�i�R�[�h
							super.ExcelSetValue("�H��2_�i�R�[�h", toString(items[10]));
							//�i��
							super.ExcelSetValue("�H��2_�i��", toString(items[11]));
							//�P��
							super.ExcelSetValue("�H��2_�P��", toDouble(items[12]));
							//����
							super.ExcelSetValue("�H��2_����", (toDouble(items[13]) / 100));
						
						}

						//����SEQ���Ƃɕ���
						if ( toString(items[36]).equals(strShisakuSeq1) ){
							//�z��1
							super.ExcelSetValue("�H��2_�z��1", toDouble(items[14]));
							
						} else if ( toString(items[36]).equals(strShisakuSeq2) ){
							//�z��2
							super.ExcelSetValue("�H��2_�z��2", toDouble(items[14]));
							
						} else if ( toString(items[36]).equals(strShisakuSeq3) ){
							//�z��3
							super.ExcelSetValue("�H��2_�z��3", toDouble(items[14]));
							
						}

					//�H�����u�����v�̏ꍇ
					} else if (toString(items[1]).equals("3")) {
						//***** [����] *****

						if ( toString(items[36]).equals(strShisakuSeq1) ){
							//�V�K����
							super.ExcelSetValue("�H��3_�V�K����", toString(items[9]));
							//�i�R�[�h
							super.ExcelSetValue("�H��3_�i�R�[�h", toString(items[10]));
							//�i��
							super.ExcelSetValue("�H��3_�i��", toString(items[11]));
							//�P��
							super.ExcelSetValue("�H��3_�P��", toDouble(items[12]));
							//����
							super.ExcelSetValue("�H��3_����", (toDouble(items[13]) / 100));
							
						}

						//����SEQ���Ƃɕ���
						if ( toString(items[36]).equals(strShisakuSeq1) ){
							//�z��1
							super.ExcelSetValue("�H��3_�z��1", toDouble(items[14]));
							
						} else if ( toString(items[36]).equals(strShisakuSeq2) ){
							//�z��2
							super.ExcelSetValue("�H��3_�z��2", toDouble(items[14]));
							
						} else if ( toString(items[36]).equals(strShisakuSeq3) ){
							//�z��3
							super.ExcelSetValue("�H��3_�z��3", toDouble(items[14]));
							
						}
						
					} 
					
					//�������
					if ( toString(items[36]).equals(strShisakuSeq1) ){
						//�[�U�ʐ���1
						super.ExcelSetValue("����1", toDouble(items[15]));
						//�[�U�ʖ���1
						super.ExcelSetValue("����1", toDouble(items[16]));
						//��d1
						super.ExcelSetValue("��d1", toDouble(items[17]));
						
					} else if ( toString(items[36]).equals(strShisakuSeq2) ){
						//�[�U�ʐ���2
						super.ExcelSetValue("����2", toDouble(items[15]));
						//�[�U�ʖ���2
						super.ExcelSetValue("����2", toDouble(items[16]));
						//��d2
						super.ExcelSetValue("��d2", toDouble(items[17]));
						
					} else if ( toString(items[36]).equals(strShisakuSeq3) ){
						//�[�U�ʐ���3
						super.ExcelSetValue("����3", toDouble(items[15]));
						//�[�U�ʖ���3
						super.ExcelSetValue("����3", toDouble(items[16]));
						//��d3
						super.ExcelSetValue("��d3", toDouble(items[17]));
						
					}

					//ADD 2012.4.30�@�yH24�N�x�Ή��z�H�ꖼ(������)�ǉ�
					super.ExcelSetValue("�H��", toString(items[41]));
					//�������@
					super.ExcelSetValue("�������@", toString(items[18]));
					//�[�U���@
					super.ExcelSetValue("�[�U���@", toString(items[19]));
					//�E�ە��@
					super.ExcelSetValue("�E�ە��@", toString(items[20]));
					//�戵���x
					super.ExcelSetValue("�戵���x", toString(items[21]));
					//�e��E���
					super.ExcelSetValue("�e��E���", toString(items[22]));
					//�׎p
					super.ExcelSetValue("�׎p", toString(items[23]));
					//���e��
					super.ExcelSetValue("���e��", toString(items[24]));
					//���萔
					super.ExcelSetValue("���萔", toString(items[25]));
					//�ܖ�����
					super.ExcelSetValue("�ܖ�����", toString(items[26]));

					//��������
					//ADD 2014/01/15 nishigawa �yQP@30154�z�ǉ��ۑ�No.11 start
					//super.ExcelSetValue("��������", toString(items[27]));
					super.ExcelSetValue("��������1", toString(items[27]));
					super.ExcelSetValue("��������2", toString(items[27]));
					super.ExcelSetValue("��������3", toString(items[27]));
					//ADD 2014/01/15 nishigawa �yQP@30154�z�ǉ��ۑ�No.11 end
					
					//�z�蕨��
					//ADD 2014/01/15 nishigawa �yQP@30154�z�ǉ��ۑ�No.11 start
					//super.ExcelSetValue("�z�蕨��", toString(items[28]));
					super.ExcelSetValue("�z�蕨��1", toString(items[28]));
					super.ExcelSetValue("�z�蕨��2", toString(items[28]));
					super.ExcelSetValue("�z�蕨��3", toString(items[28]));
					//ADD 2014/01/15 nishigawa �yQP@30154�z�ǉ��ۑ�No.11 end
					
					//����
					//ADD 2014/01/15 nishigawa �yQP@30154�z�ǉ��ۑ�No.11 start
					//super.ExcelSetValue("����", toString(items[29]));
					super.ExcelSetValue("����1", toString(items[29]));
					super.ExcelSetValue("����2", toString(items[29]));
					super.ExcelSetValue("����3", toString(items[29]));
					//ADD 2014/01/15 nishigawa �yQP@30154�z�ǉ��ۑ�No.11 end
					
					//��]����
					//ADD 2014/01/15 nishigawa �yQP@30154�z�ǉ��ۑ�No.11 start
					//super.ExcelSetValue("��]����", toString(items[30]));
					super.ExcelSetValue("��]����1", toString(items[30]));
					super.ExcelSetValue("��]����2", toString(items[30]));
					super.ExcelSetValue("��]����3", toString(items[30]));
					//ADD 2014/01/15 nishigawa �yQP@30154�z�ǉ��ۑ�No.11 end

					//DEL 2012.4.30�@�yH24�N�x�Ή��z���i���̕ύX
//					//�ŐV�̒��ӎ�����ݒ肷��
//					if ( !toString(items[37]).isEmpty() ) {
//						
//						if ( intChuijiko < Integer.parseInt(toString(items[37])) ){
//							intChuijiko = Integer.parseInt(toString(items[37]));
//							
//							//���i���
//							super.ExcelSetValue("���i���", toString(toString(items[31])));
//							
//						}
//						
//					}

//					//�ŏI���Z�p
//					if ( toString(items[36]).equals(strShisakuSeq1) ){
//						//���Ϗ[�U��1
//						super.ExcelSetValue("���Ϗ[�U��1", toDouble(items[32]));
//						//�L������1
//						super.ExcelSetValue("�L������1", (toDouble(items[33]) / 100));
//						//�Œ��1
//						super.ExcelSetValue("�Œ��1", toDouble(items[34]));
//						
//					} else if ( toString(items[36]).equals(strShisakuSeq2) ){
//						//���Ϗ[�U��2
//						super.ExcelSetValue("���Ϗ[�U��2", toDouble(items[32]));
//						//�L������2
//						super.ExcelSetValue("�L������2", (toDouble(items[33]) / 100));
//						//�Œ��2
//						super.ExcelSetValue("�Œ��2", toDouble(items[34]));
//						
//					} else if ( toString(items[36]).equals(strShisakuSeq3) ){
//						//���Ϗ[�U��3
//						super.ExcelSetValue("���Ϗ[�U��3", toDouble(items[32]));
//						//�L������3
//						super.ExcelSetValue("�L������3", (toDouble(items[33]) / 100));
//						//�Œ��3
//						super.ExcelSetValue("�Œ��3", toDouble(items[34]));
//						
//					}
					
				}catch(ExceptionWaning e){
					//�ő�s���𒴂���ƁAExceptionWaning��Throw�����̂Ń��[�v���I������B
					break;
					
				}finally{
					
				}
				
			}
			
			//DEL 2012.4.30�@�yH24�N�x�Ή��zJEXCEL�Ή��̈׈ړ�
//			//�G�N�Z���t�@�C�����_�E�����[�h�t�H���_�ɐ�������
////			ret = super.ExcelOutput();
//			ret = super.ExcelOutput(
//					makeShisakuNo(
//							reqData.getFieldVale(0, 0, "cd_shain")
//							,reqData.getFieldVale(0, 0, "nen")
//							,reqData.getFieldVale(0, 0, "no_oi")
//							)
//					);

			//ADD 2012.4.30�@�yH24�N�x�Ή��zJEXCEL�Ή��̈גǉ�
			super.ExcelWrite();
			super.close();
			
		} catch (Exception e) {
			em.ThrowException(e, "");

		} finally {
			//�ϐ��̍폜
			strShisakuSeq1 = null;
			strShisakuSeq2 = null;
			strShisakuSeq3 = null;
			intChuijiko = 0;

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
	 * ���̑�_�������Z�\(EXCEL)�𐶐�����
	 * @param lstRecset : �����f�[�^���X�g
	 * @return�@String : �_�E�����[�h�p�̃p�X
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private String makeExcelFile2(List<?> lstRecset, RequestResponsKindBean reqData) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		String ret = "";
		String strShisakuSeq1 = "";
		String strShisakuSeq2 = "";
		String strShisakuSeq3 = "";
		int intChuijiko = 0;
		
		try {
			//EXCEL�e���v���[�g��ǂݍ���
			super.ExcelReadTemplate("���̑��������Z�\");

			//����SEQ���擾
			strShisakuSeq1 = toString( reqData.getFieldVale(0, 0, "seq_shisaku1"));
			strShisakuSeq2 = toString( reqData.getFieldVale(0, 0, "seq_shisaku2"));
			strShisakuSeq3 = toString( reqData.getFieldVale(0, 0, "seq_shisaku3"));

			//�_�E�����[�h�p��EXCEL�𐶐�����
			for (int i = 0; i < lstRecset.size(); i++) {
				
				//�������ʂ�1�s�������o��
				Object[] items = (Object[]) lstRecset.get(i);

				//ADD 2012.4.30�@�yH24�N�x�Ή��zJEXCEL�Ή��̈גǉ�
				if(i==0){
					//EXCEL�e���v���[�g��ǂݍ���
					ret = super.ExcelOutput(
							makeShisakuNo(
							reqData.getFieldVale(0, 0, "cd_shain")
							,reqData.getFieldVale(0, 0, "nen")
							,reqData.getFieldVale(0, 0, "no_oi")
							));
				}

				try{
					//Excel�ɒl���Z�b�g����
					
					//�i��
					super.ExcelSetValue("�i��", toString(items[2]));
					//�˗��ԍ�
					super.ExcelSetValue("�˗��ԍ�", toString(items[3]));
					//���t
					super.ExcelSetValue("���t", toString(items[4]));
					//�H��R�[�h
//					super.ExcelSetValue("�H��R�[�h", toDouble(items[5]));					//DEL 2012.4.30�@�yH24�N�x�Ή��z�R�[�h�o�͂��疼�̏o�͂ɕύX
					//������
					super.ExcelSetValue("������", toString(items[6]));
					//�c��
					super.ExcelSetValue("�c��", toString(items[7]));
					
					//����SEQ���Ƃɕ���
					if ( toString(items[36]).equals(strShisakuSeq1) ){
						//�T���v��No1
						super.ExcelSetValue("�T���v��No1", toString(items[8]));

						//ADD 2012.4.30�@�yH24�N�x�Ή��z���i��񗓂ɍH����1�ҏW
						super.ExcelSetValue("���i_�T���v��No1","�T���v��:" + toString(items[8]));
						if ( !toString(items[37]).isEmpty() ) {
							super.ExcelSetValue("���i_�H����No1","�H����:" + toString(items[37]));
							super.ExcelSetValue("���i_���ӎ���1",toString(items[31]));
						}
						
					} else if ( toString(items[36]).equals(strShisakuSeq2) ){
						//�T���v��No2
						super.ExcelSetValue("�T���v��No2", toString(items[8]));

						//ADD 2012.4.30�@�yH24�N�x�Ή��z���i��񗓂ɍH����2�ҏW
						super.ExcelSetValue("���i_�T���v��No2","�T���v��:" + toString(items[8]));
						if ( !toString(items[37]).isEmpty() ) {
							super.ExcelSetValue("���i_�H����No2","�H����:" + toString(items[37]));
							super.ExcelSetValue("���i_���ӎ���2",toString(items[31]));
						}

					} else if ( toString(items[36]).equals(strShisakuSeq3) ){
						//�T���v��No3
						super.ExcelSetValue("�T���v��No3", toString(items[8]));

						//ADD 2012.4.30�@�yH24�N�x�Ή��z���i��񗓂ɍH����3�ҏW
						super.ExcelSetValue("���i_�T���v��No3","�T���v��:" + toString(items[8]));
						if ( !toString(items[37]).isEmpty() ) {
							super.ExcelSetValue("���i_�H����No3","�H����:" + toString(items[37]));
							super.ExcelSetValue("���i_���ӎ���3",toString(items[31]));
						}

					}

					//�H�����u�E�ے����t�v�̏ꍇ
					if (toString(items[1]).equals("1")) {
						//***** [�E�ے����t] *****

						if ( toString(items[36]).equals(strShisakuSeq1) ){
							//�V�K����
							super.ExcelSetValue("�H��1_�V�K����", toString(items[9]));
							//�i�R�[�h
							super.ExcelSetValue("�H��1_�i�R�[�h", toString(items[10]));
							//�i��
							super.ExcelSetValue("�H��1_�i��", toString(items[11]));
							//�P��
							super.ExcelSetValue("�H��1_�P��", toDouble(items[12]));
							//����
							super.ExcelSetValue("�H��1_����", (toDouble(items[13]) / 100));
							
						}

						//����SEQ���Ƃɕ���
						if ( toString(items[36]).equals(strShisakuSeq1) ){
							//�z��1
							super.ExcelSetValue("�H��1_�z��1", toDouble(items[14]));
							
						} else if ( toString(items[36]).equals(strShisakuSeq2) ){
							//�z��2
							super.ExcelSetValue("�H��1_�z��2", toDouble(items[14]));
							
						} else if ( toString(items[36]).equals(strShisakuSeq3) ){
							//�z��3
							super.ExcelSetValue("�H��1_�z��3", toDouble(items[14]));
							
						}

					//�H�����u�����v�̏ꍇ
					} else if (toString(items[1]).equals("2")) {
						//***** [����] *****

						if ( toString(items[36]).equals(strShisakuSeq1) ){
							//�V�K����
							super.ExcelSetValue("�H��2_�V�K����", toString(items[9]));
							//�i�R�[�h
							super.ExcelSetValue("�H��2_�i�R�[�h", toString(items[10]));
							//�i��
							super.ExcelSetValue("�H��2_�i��", toString(items[11]));
							//�P��
							super.ExcelSetValue("�H��2_�P��", toDouble(items[12]));
							//����
							super.ExcelSetValue("�H��2_����", (toDouble(items[13]) / 100));
							
						}

						//����SEQ���Ƃɕ���
						if ( toString(items[36]).equals(strShisakuSeq1) ){
							//�z��1
							super.ExcelSetValue("�H��2_�z��1", toDouble(items[14]));
							
						} else if ( toString(items[36]).equals(strShisakuSeq2) ){
							//�z��2
							super.ExcelSetValue("�H��2_�z��2", toDouble(items[14]));
							
						} else if ( toString(items[36]).equals(strShisakuSeq3) ){
							//�z��3
							super.ExcelSetValue("�H��2_�z��3", toDouble(items[14]));
							
						}

					}
					
					//�������
					if ( toString(items[36]).equals(strShisakuSeq1) ){
						//�d�オ�荇�v�d��1
						if ( !toString(items[35]).isEmpty() ) {
							super.ExcelSetValue("�d�オ�荇�v�d��1", toDouble(items[35]));
							
						}
						
					} else if ( toString(items[36]).equals(strShisakuSeq2) ){
						//�d�オ�荇�v�d��2
						if ( !toString(items[35]).isEmpty() ) {
							super.ExcelSetValue("�d�オ�荇�v�d��2", toDouble(items[35]));
							
						}
						
					} else if ( toString(items[36]).equals(strShisakuSeq3) ){
						//�d�オ�荇�v�d��3
						if ( !toString(items[35]).isEmpty() ) {
							super.ExcelSetValue("�d�オ�荇�v�d��3", toDouble(items[35]));
						
						}
						
					}

					//ADD 2012.4.30�@�yH24�N�x�Ή��z�H�ꖼ(������)�ǉ�
					super.ExcelSetValue("�H��", toString(items[41]));
					//�������@
					super.ExcelSetValue("�������@", toString(items[18]));
					//�[�U���@
					super.ExcelSetValue("�[�U���@", toString(items[19]));
					//�E�ە��@
					super.ExcelSetValue("�E�ە��@", toString(items[20]));
					//�戵���x
					super.ExcelSetValue("�戵���x", toString(items[21]));
					//�e��E���
					super.ExcelSetValue("�e��E���", toString(items[22]));
					//�׎p
					super.ExcelSetValue("�׎p", toString(items[23]));
					//���e��
					super.ExcelSetValue("���e��", toString(items[24]));
					//���萔
					super.ExcelSetValue("���萔", toString(items[25]));
					//�ܖ�����
					super.ExcelSetValue("�ܖ�����", toString(items[26]));
					
					//��������
					//ADD 2014/01/15 nishigawa �yQP@30154�z�ǉ��ۑ�No.11 start
					//super.ExcelSetValue("��������", toString(items[27]));
					super.ExcelSetValue("��������1", toString(items[27]));
					super.ExcelSetValue("��������2", toString(items[27]));
					super.ExcelSetValue("��������3", toString(items[27]));
					//ADD 2014/01/15 nishigawa �yQP@30154�z�ǉ��ۑ�No.11 end
					
					//�z�蕨��
					//ADD 2014/01/15 nishigawa �yQP@30154�z�ǉ��ۑ�No.11 start
					//super.ExcelSetValue("�z�蕨��", toString(items[28]));
					super.ExcelSetValue("�z�蕨��1", toString(items[28]));
					super.ExcelSetValue("�z�蕨��2", toString(items[28]));
					super.ExcelSetValue("�z�蕨��3", toString(items[28]));
					//ADD 2014/01/15 nishigawa �yQP@30154�z�ǉ��ۑ�No.11 end
					
					//����
					//ADD 2014/01/15 nishigawa �yQP@30154�z�ǉ��ۑ�No.11 start
					//super.ExcelSetValue("����", toString(items[29]));
					super.ExcelSetValue("����1", toString(items[29]));
					super.ExcelSetValue("����2", toString(items[29]));
					super.ExcelSetValue("����3", toString(items[29]));
					//ADD 2014/01/15 nishigawa �yQP@30154�z�ǉ��ۑ�No.11 end
					
					//��]����
					//ADD 2014/01/15 nishigawa �yQP@30154�z�ǉ��ۑ�No.11 start
					//super.ExcelSetValue("��]����", toString(items[30]));
					super.ExcelSetValue("��]����1", toString(items[30]));
					super.ExcelSetValue("��]����2", toString(items[30]));
					super.ExcelSetValue("��]����3", toString(items[30]));
					//ADD 2014/01/15 nishigawa �yQP@30154�z�ǉ��ۑ�No.11 end

					//DEL 2012.4.30�@�yH24�N�x�Ή��z���i���̕ύX
//					//�ŐV�̒��ӎ�����ݒ肷��
//					if ( !toString(items[37]).isEmpty() ) {
//						
//						if ( intChuijiko < Integer.parseInt(toString(items[37])) ){
//							intChuijiko = Integer.parseInt(toString(items[37]));
//							
//							//���i���
//							super.ExcelSetValue("���i���", toString(toString(items[31])));
//							
//						}
//						
//					}
					
//					//�ŏI���Z�p
//					if ( toString(items[36]).equals(strShisakuSeq1) ){
//						//���Ϗ[�U��1
//						super.ExcelSetValue("���Ϗ[�U��1", toDouble(items[32]));
//						//�L������1
//						super.ExcelSetValue("�L������1", (toDouble(items[33]) / 100));
//						//�Œ��1
//						super.ExcelSetValue("�Œ��1", toDouble(items[34]));
//						
//					} else if ( toString(items[36]).equals(strShisakuSeq2) ){
//						//���Ϗ[�U��2
//						super.ExcelSetValue("���Ϗ[�U��2", toDouble(items[32]));
//						//�L������2
//						super.ExcelSetValue("�L������2", (toDouble(items[33]) / 100));
//						//�Œ��2
//						super.ExcelSetValue("�Œ��2", toDouble(items[34]));
//						
//					} else if ( toString(items[36]).equals(strShisakuSeq3) ){
//						//���Ϗ[�U��3
//						super.ExcelSetValue("���Ϗ[�U��3", toDouble(items[32]));
//						//�L������3
//						super.ExcelSetValue("�L������3", (toDouble(items[33]) / 100));
//						//�Œ��3
//						super.ExcelSetValue("�Œ��3", toDouble(items[34]));
//						
//					}
					
				}catch(ExceptionWaning e){
					//�ő�s���𒴂���ƁAExceptionWaning��Throw�����̂Ń��[�v���I������B
					break;
					
				}finally{
					
				}
				
			}
			
			//DEL 2012.4.30�@�yH24�N�x�Ή��zJEXCEL�Ή��̈׈ړ�
//			//�G�N�Z���t�@�C�����_�E�����[�h�t�H���_�ɐ�������
////			ret = super.ExcelOutput();
//			ret = super.ExcelOutput(
//					makeShisakuNo(
//							reqData.getFieldVale(0, 0, "cd_shain")
//							,reqData.getFieldVale(0, 0, "nen")
//							,reqData.getFieldVale(0, 0, "no_oi")
//							)
//					);

			//ADD 2012.4.30�@�yH24�N�x�Ή��zJEXCEL�Ή��̈גǉ�
			super.ExcelWrite();
			super.close();
			
		} catch (Exception e) {
			em.ThrowException(e, "");

		} finally {
			//�ϐ��̍폜
			strShisakuSeq1 = null;
			strShisakuSeq2 = null;
			strShisakuSeq3 = null;
			intChuijiko = 0;
			
		}
		return ret;
		
	}
	/**
	 * �Ώۂ̌������Z�f�[�^����������
	 * @param KindBean : ���N�G�X�g�f�[�^�i�@�\�j
	 * @return List : �������ʂ̃��X�g
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning : �Y���f�[�^����
	 */
	private List<?> getData(RequestResponsKindBean KindBean) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		//��������
		List<?> ret = null;
		//SQL�@StringBuffer
		StringBuffer strSql = new StringBuffer();
		
		try {
			//SQL���̍쐬
			strSql = MakeSQLBuf(KindBean);
			
			//SQL�����s
			ret = searchDB.dbSearch(strSql.toString());
			
			//�������ʂ��Ȃ��ꍇ
			if (ret.size() == 0){
				em.ThrowException(ExceptionKind.�x��Exception,"W000401", strSql.toString(), "", "");
				
			}
			
		} catch (Exception e) {
			em.ThrowException(e, "�������Z�\�ADB�����Ɏ��s���܂����B");

		} finally {
			//�ϐ��̍폜
			strSql = null;
			
		}
		return ret;
		
	}
	/**
	 * ���N�G�X�g�f�[�^���A����SQL�𐶐�����
	 * @param reqData : ���N�G�X�g�f�[�^�i�@�\�j
	 * @return StringBuffer : ����SQL
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private StringBuffer MakeSQLBuf(RequestResponsKindBean reqData) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{
	
		StringBuffer ret = new StringBuffer();

		try {

			String strCd_shain = "";
			String strNen = "";
			String strNo_oi = "";
			String strShisakuSeq = "";
			
			//����iCD�@�Ј��R�[�h
			strCd_shain = reqData.getFieldVale(0, 0, "cd_shain");
			//����iNo�@�N
			strNen = reqData.getFieldVale(0, 0, "nen");
			//����iNo�@�ǔ�
			strNo_oi = reqData.getFieldVale(0, 0, "no_oi");
	
			//SQL���̍쐬	
			ret.append(" SELECT ");
			ret.append("   ISNULL(CONVERT(VARCHAR,M302K.value1),'') AS kotei1 ");
			ret.append("  ,M302K.value2 AS kotei2 ");
			ret.append("  ,T110.nm_hin AS hinmei ");
			ret.append("  ,CONVERT(VARCHAR,T110.no_irai) AS no_irai ");
			ret.append(" ,CONVERT(VARCHAR,YEAR(GETDATE())) + '�N' ");
			ret.append("   + CONVERT(VARCHAR,MONTH(GETDATE())) + '��' ");
			ret.append("   + CONVERT(VARCHAR,DAY(GETDATE())) + '��' AS dt_hizuke ");
			ret.append("  ,T110.cd_kojo AS cd_kojo ");
			ret.append("  ,M101.nm_user AS kenkyusho ");	//6.��������
			ret.append("  ,T110.cd_eigyo AS eigyo ");		//7.�c�ƃR�[�h
			ret.append("  ,T131.nm_sample AS nm_sample ");
			ret.append("  ,CASE ISNULL(LEFT(T120.cd_genryo,1),'') WHEN '' THEN ''  ");
			ret.append("    WHEN 'N' THEN '��' ELSE '' END AS new_genryo ");
			ret.append("  ,T120.cd_genryo AS cd_genryo ");
			ret.append("  ,T120.nm_genryo AS nm_genryo ");
			ret.append("  ,T120.tanka AS tanka ");
			ret.append("  ,T120.budomari AS budomari ");
			ret.append("  ,T132.quantity AS haigoryo ");
			ret.append("  ,T141.zyusui AS zyuten_suiso ");
			ret.append("  ,T141.zyuabura AS zyuten_yuso ");
			ret.append("  ,T131.hiju AS hiju ");
			ret.append("  ,M3021.nm_literal AS nm_seizohoho ");
			ret.append("  ,M3022.nm_literal AS nm_juten ");
			ret.append("  ,T110.hoho_sakin AS hoho_sakin ");
			ret.append("  ,M3023.nm_literal AS nm_ondo ");
			ret.append("  ,T110.youki AS youki ");
			ret.append("  ,T110.cd_nisugata AS nm_nisugata ");
			ret.append("  ,T110.yoryo AS yoryo ");
			ret.append("  ,T110.su_iri AS su_iri ");
			ret.append("  ,T110.shomikikan AS shomikikan ");
			ret.append("  ,T110.dt_hatubai AS dt_hatubai ");
			ret.append("  ,T110.buturyo AS buturyo ");
			ret.append("  ,T110.baika AS baika ");
			ret.append("  ,T110.genka AS kibogenka ");
			ret.append("  ,T133.chuijiko AS chuijiko ");				//31���ӎ���
			ret.append("  ,T141.heikinzyu AS heikinzyu ");
			ret.append("  ,T141.yukobudomari AS yukobudomari ");
			ret.append("  ,T141.cs_keihi AS koteihi ");
			ret.append("  ,T131.juryo_shiagari_g AS shiagarijuryo ");
			ret.append("  ,T131.seq_shisaku AS seq_shisaku ");
			ret.append("  ,T131.no_chui AS no_chui ");
			ret.append("  ,T120.sort_kotei AS sort_kotei ");
			ret.append("  ,T120.sort_genryo AS sort_genryo ");
			ret.append("  ,T131.no_shisan AS no_shisan ");

			//ADD 2012.4.30�@�yH24�N�x�Ή��z�H�ꖼ
			ret.append("  ,M104.nm_busho  AS nm_busho ");			//41�H�ꖼ(������)
			
			ret.append(" FROM tr_shisakuhin T110 ");
			ret.append(" INNER JOIN tr_haigo T120 ");
			ret.append("  ON T110.cd_shain = T120.cd_shain ");
			ret.append("  AND T110.nen = T120.nen ");
			ret.append("  AND T110.no_oi = T120.no_oi ");
			ret.append(" INNER JOIN tr_shisaku T131 ");
			ret.append("  ON T110.cd_shain = T131.cd_shain ");
			ret.append("  AND T110.nen = T131.nen ");
			ret.append("  AND T110.no_oi = T131.no_oi ");
			ret.append(" INNER JOIN tr_shisaku_list T132 ");
			ret.append("  ON T120.cd_shain = T132.cd_shain ");
			ret.append("  AND T120.nen = T132.nen ");
			ret.append("  AND T120.no_oi = T132.no_oi ");
			ret.append("  AND T120.cd_kotei = T132.cd_kotei ");
			ret.append("  AND T120.seq_kotei = T132.seq_kotei ");
			ret.append("  AND T131.seq_shisaku = T132.seq_shisaku ");
			//�������@���擾
			ret.append(" LEFT JOIN ma_literal M3021 ");
			ret.append("  ON M3021.cd_category = 'K_seizohoho' ");
			ret.append("  AND M3021.cd_literal = T110.cd_hoho ");
			//�[�U���@���擾
			ret.append(" LEFT JOIN ma_literal M3022 ");
			ret.append("  ON M3022.cd_category = 'K_jyutenhoho' ");
			ret.append("  AND M3022.cd_literal = T110.cd_juten ");
			//�戵���x���擾
			ret.append(" LEFT JOIN ma_literal M3023 ");
			ret.append("  ON M3023.cd_category = 'K_toriatukaiondo' ");
			ret.append("  AND M3023.cd_literal = T110.cd_ondo ");
			//�׎p���擾
			ret.append(" LEFT JOIN ma_user AS M101 ");
			ret.append("  ON id_user =" + strCd_shain);
			ret.append(" LEFT JOIN ma_literal AS M302K ");
			ret.append("  ON M302K.cd_category = 'K_kote' ");
			ret.append("  AND M302K.cd_literal = T120.zoku_kotei ");
			ret.append(" LEFT JOIN tr_genryo AS T141 ");
			ret.append("  ON T141.cd_shain = T131.cd_shain ");
			ret.append("  AND T141.nen = T131.nen ");
			ret.append("  AND T141.no_oi = T131.no_oi ");
			ret.append("  AND T141.seq_shisaku = T131.seq_shisaku ");
			ret.append(" LEFT JOIN tr_cyuui AS T133 ");
			ret.append("  ON T133.cd_shain = T131.cd_shain ");
			ret.append("  AND T133.nen = T131.nen ");
			ret.append("  AND T133.no_oi = T131.no_oi ");
			ret.append("  AND T133.no_chui = T131.no_chui ");

			//ADD 2012.4.30�@�yH24�N�x�Ή��z�H�ꖼ
			ret.append(" LEFT JOIN ma_busho AS M104 ");
			ret.append("  ON T110.cd_kaisha = M104.cd_kaisha ");
			ret.append("  AND T110.cd_kojo = M104.cd_busho ");

			//����SEQ1�`3�̔z���ʂ̎擾
			for ( int i=0; i<3; i++ ) {
				
				//����SEQ�̎擾
				strShisakuSeq = toString(reqData.getFieldVale(0, 0, "seq_shisaku" + (i+1)));

				//����SEQ����ł͂Ȃ��ꍇ�A�z���ʂ��擾����
				if ( !strShisakuSeq.isEmpty() ) {
					
					ret.append(" LEFT JOIN ( ");
					ret.append("  SELECT  ");
					ret.append("     T132s.cd_shain AS cd_shain ");
					ret.append("    ,T132s.nen AS nen ");
					ret.append("    ,T132s.no_oi AS no_oi ");
					ret.append("    ,T132s.cd_kotei AS cd_kotei ");
					ret.append("    ,T132s.seq_kotei AS seq_kotei ");
					ret.append("    ,T132s.quantity AS quantity ");
					ret.append("   FROM tr_shisaku_list T132s ");
					ret.append("   WHERE T132s.seq_shisaku = " + strShisakuSeq);
					ret.append(" ) T132_" + (i+1) );
					ret.append(" ON T120.cd_shain = T132_" + (i+1) + ".cd_shain ");
					ret.append(" AND T120.nen = T132_" + (i+1) + ".nen ");
					ret.append(" AND T120.no_oi = T132_" + (i+1) + ".no_oi ");
					ret.append(" AND T120.cd_kotei = T132_" + (i+1) + ".cd_kotei ");
					ret.append(" AND T120.seq_kotei = T132_" + (i+1) + ".seq_kotei ");
					
				}
				
			}
			
			//��������
			ret.append(" WHERE T110.cd_shain = " + strCd_shain);
			ret.append("  AND T110.nen = " + strNen);
			ret.append("  AND T110.no_oi = " + strNo_oi);
			
			//����SEQ
			ret.append("  AND T131.seq_shisaku IN ( ");
			for ( int i=0; i<3; i++ ) {
				
				//����SEQ�̎擾
				strShisakuSeq = toString(reqData.getFieldVale(0, 0, "seq_shisaku" + (i+1)));

				//����SEQ����ł͂Ȃ��ꍇ�A��������ǉ�����
				if ( !strShisakuSeq.isEmpty() ) {
			
					if ( i != 0 ) {
						ret.append(" , ");
						
					}
					ret.append(strShisakuSeq);	
					
				}
				
			}
			ret.append(" ) ");

			//����SEQ����擾�����A�ő�3�̔z���ʂ��A�S�āu0 or NULL�v�ł͂Ȃ������`�F�b�N
			ret.append("  AND NOT ( ");
			for ( int i=0; i<3; i++ ) {
				
				//����SEQ�̎擾
				strShisakuSeq = toString(reqData.getFieldVale(0, 0, "seq_shisaku" + (i+1)));

				//����SEQ����ł͂Ȃ��ꍇ�A��������ǉ�����
				if ( !strShisakuSeq.isEmpty() ) {
			
					if ( i != 0 ) {
						ret.append(" AND ");
						
					}
					ret.append(" ISNULL(T132_" + (i+1) + ".quantity,0) = 0 ");	
					
				}
				
			}
			ret.append(" ) ");			
			
			ret.append("  AND M302K.value1 IN ( 1,2 ) ");
			ret.append("  AND M302K.value2 IN ( 1,2,3 ) ");
			ret.append("  ORDER BY ");
			ret.append("   T120.sort_kotei ");
			ret.append("   ,T120.sort_genryo ");
			ret.append("   ,T131.no_shisan ");

		} catch (Exception e) {
			this.em.ThrowException(e, "�������Z�\�A����SQL�̐����Ɏ��s���܂����B");			
			
		} finally {
			
		}
		return ret;
		
	}
	
	
}