package jp.co.blueflag.shisaquick.srv.logic;

import java.math.BigDecimal;
import java.util.List;

import jp.co.blueflag.shisaquick.srv.base.LogicBaseExcel;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsKindBean;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;
import jp.co.blueflag.shisaquick.srv.common.ExceptionManager.ExceptionKind;

/**
 * �T���v���������𐶐�����
 * @author isono
 * @since  2009/04/09
 */
public class SampleSetumeishoLogic extends LogicBaseExcel {

	/**
	 * �R���X�g���N�^
	 */
	public SampleSetumeishoLogic() {
		//�X�[�p�[�N���X�̃R���X�g���N�^�Ăяo��
		super();

	}
	/**
	 * �T���v���������𐶐�����
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
		List<?> item = null;
		//�����f�[�^
		List<?> lstRecset1 = null;
		List<?> lstRecset2 = null;
		List<?> lstRecset3 = null;
		List<?> lstRecset4 = null;
		//�G�N�Z���t�@�C���p�X
		String DownLoadPath = "";
		//SQL�@StringBuffer
		StringBuffer strSql = new StringBuffer();

		try {

			super.createSearchDB();

			//���쏇�ɂĎ���SEQ���������A���N�G�X�ƃf�[�^�Ɋi�[
			//SQL���̍쐬
			strSql = MakeSQLSeq(reqData,_userInfoData);
			//SQL�����s
			item = searchDB.dbSearch(strSql.toString());
			//�������ʂ��Ȃ��ꍇ
			if (item.size() == 0){
				em.ThrowException(ExceptionKind.�x��Exception,"W000401", strSql.toString(), "", "");

			}

			for( int i = 0; i < item.size(); i++ ){

				//�������ʂ̊i�[
				Object[] items = (Object[]) item.get(i);

				//����SEQ�擾
				String seq = toString(items[0]);
				//���N�G�X�g�f�[�^�Ɏ���SEQ�i�[
				reqData.setFieldVale(0, 0, "seq_shisaku" + (i+1), seq);

			}

			//DB����(��������)
			lstRecset1 = getData1(reqData);
			lstRecset2 = getData2(reqData);
			lstRecset3 = getData3(reqData);
			lstRecset4 = getData4(reqData);

			//Excel�t�@�C������(���H��)
			DownLoadPath = makeExcelFile1(lstRecset1,lstRecset2,lstRecset3,lstRecset4,reqData);

			//Excel�t�@�C������(��������)
			DownLoadPath = DownLoadPath + ":::" + makeExcelFile2(
					lstRecset1,
					lstRecset2,
					lstRecset3,
					lstRecset4,
					reqData
					);

			//���X�|���X�f�[�^����
			ret = CreateRespons(DownLoadPath);

		} catch (Exception e) {
			em.ThrowException(e, "�T���v���������̐����Ɏ��s���܂����B");

		} finally {
			//���X�g�̔j��
			removeList(lstRecset1);
			removeList(lstRecset2);
			removeList(lstRecset3);
			removeList(lstRecset4);

			if (searchDB != null) {
				//DB�Z�b�V�����̃N���[�Y
				searchDB.Close();
				searchDB = null;

			}

		}
		return ret;

	}

	/**
	 * ���N�G�X�g�f�[�^���A����SEQ����SQL�𐶐�����
	 * @param reqData : ���N�G�X�g�f�[�^
	 * @param userInfoData : ���[�U�[���
	 * @return String : sql
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private StringBuffer MakeSQLSeq(
			RequestResponsKindBean reqData
			,UserInfoData _userInfoData
	)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		StringBuffer ret = new StringBuffer();

		try {

			//SQL������
			ret.append(" select TOP 3 seq_shisaku,sort_shisaku  ");
			ret.append(" from tr_shisaku ");
			ret.append(" where  ");
			ret.append("   cd_shain = " + reqData.getFieldVale(0, 0, "cd_shain") );
			ret.append("   AND nen = " +  reqData.getFieldVale(0, 0, "nen") );
			ret.append("   AND no_oi = " + reqData.getFieldVale(0, 0, "no_oi") );
			ret.append("   AND flg_print = 1 ");
			ret.append(" order by sort_shisaku ");

		} catch (Exception e) {
			this.em.ThrowException(e, "�T���v���������A����SQL�̐����Ɏ��s���܂����B");

		} finally {

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
			ret.setID("SA450");

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
	 * �T���v���������𐶐�����
	 * @param lstRecset1 : �����f�[�^���X�g(seq_shisaku1)
	 * @param lstRecset2 : �����f�[�^���X�g(seq_shisaku2)
	 * @param lstRecset3 : �����f�[�^���X�g(seq_shisaku3)
	 * @return�@String : �_�E�����[�h�p�̃p�X
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private String makeExcelFile1(
			List<?> lstRecset1,
			List<?> lstRecset2,
			List<?> lstRecset3,
			List<?> lstRecset4,
			RequestResponsKindBean reqData
			)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		String ret = "";

		try {
			//EXCEL�e���v���[�g��ǂݍ���
//			super.ExcelReadTemplate("�T���v���������i�������Łj");
			//MOD 2013/06/18 ogawa start        Setting�V�[�g�̍폜���\���ɕύX
			//�C���O�̃\�[�X
			//super.ExcelReadTemplate("�T���v��������_���Z");
			//�C����̃\�[�X
			super.ExcelReadTemplate("�T���v��������_���Z", false);
			//MOD 2013/06/18 ogawa  end

			//�T���v����ڂ̏o��
			if(lstRecset1 != null){

				//�_�E�����[�h�p��EXCEL�𐶐�����
				for (int i = 0; i < lstRecset1.size(); i++) {

					//�������ʂ�1�s�������o��
					Object[] items = (Object[]) lstRecset1.get(i);

					try{

						//Excel�ɒl���Z�b�g����
						ExcelSetValueMain(1, items);

					}catch(ExceptionWaning e){
						//�ő�s���𒴂���ƁAExceptionWaning��Throw�����̂Ń��[�v���I������B
						break;

					}finally{

					}

				}

			}

			//�T���v���Q�ڂ̏o��
			if(lstRecset2 != null){

				//�_�E�����[�h�p��EXCEL�𐶐�����
				for (int i = 0; i < lstRecset2.size(); i++) {

					//�������ʂ�1�s�������o��
					Object[] items = (Object[]) lstRecset2.get(i);

					try{

						//Excel�ɒl���Z�b�g����
						ExcelSetValueMain(2, items);

					}catch(ExceptionWaning e){
						//�ő�s���𒴂���ƁAExceptionWaning��Throw�����̂Ń��[�v���I������B
						break;

					}finally{

					}

				}

			}

			//�T���v���R�ڂ̏o��
			if(lstRecset3 != null){

				//�_�E�����[�h�p��EXCEL�𐶐�����
				for (int i = 0; i < lstRecset3.size(); i++) {

					//�������ʂ�1�s�������o��
					Object[] items = (Object[]) lstRecset3.get(i);

					try{

						//Excel�ɒl���Z�b�g����
						ExcelSetValueMain(3, items);

					}catch(ExceptionWaning e){
						//�ő�s���𒴂���ƁAExceptionWaning��Throw�����̂Ń��[�v���I������B
						break;

					}finally{

					}

				}

			}

			//�T���v���S�ڂ̏o��
			if(lstRecset4 != null){

				//�_�E�����[�h�p��EXCEL�𐶐�����
				for (int i = 0; i < lstRecset4.size(); i++) {

					//�������ʂ�1�s�������o��
					Object[] items = (Object[]) lstRecset4.get(i);

					try{

						//Excel�ɒl���Z�b�g����
						ExcelSetValueMain(4, items);

					}catch(ExceptionWaning e){
						//�ő�s���𒴂���ƁAExceptionWaning��Throw�����̂Ń��[�v���I������B
						break;

					}finally{

					}

				}

			}

			//�G�N�Z���t�@�C�����_�E�����[�h�t�H���_�ɐ�������
//			ret = super.ExcelOutput();
			ret = super.ExcelOutput(
					makeShisakuNo(
							reqData.getFieldVale(0, 0, "cd_shain")
							,reqData.getFieldVale(0, 0, "nen")
							,reqData.getFieldVale(0, 0, "no_oi")
							)
					);

		} catch (Exception e) {
			em.ThrowException(e, "");

		} finally {

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
	 * �T���v��������(���H)�𐶐�����
	 * @param lstRecset1 : �����f�[�^���X�g(seq_shisaku1)
	 * @param lstRecset2 : �����f�[�^���X�g(seq_shisaku2)
	 * @param lstRecset3 : �����f�[�^���X�g(seq_shisaku3)
	 * @return�@String : �_�E�����[�h�p�̃p�X
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private String makeExcelFile2(
			List<?> lstRecset1,
			List<?> lstRecset2,
			List<?> lstRecset3,
			List<?> lstRecset4,
			RequestResponsKindBean reqData
			)
		throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		String ret = "";

		try {
			//EXCEL�e���v���[�g��ǂݍ���
//			super.ExcelReadTemplate("�T���v��������(���H��)");
			//MOD 2013/06/18 ogawa start        Setting�V�[�g�̍폜���\���ɕύX
			//�C���O�̃\�[�X
			//super.ExcelReadTemplate("�T���v��������");
			//�C����̃\�[�X
			super.ExcelReadTemplate("�T���v��������", false);
			//MOD 2013/06/18 ogawa  end

			//�T���v����ڂ̏o��
			if(lstRecset1 != null){

				//�_�E�����[�h�p��EXCEL�𐶐�����
				for (int i = 0; i < lstRecset1.size(); i++) {

					//�������ʂ�1�s�������o��
					Object[] items = (Object[]) lstRecset1.get(i);

					try{

						//Excel�ɒl���Z�b�g����
						ExcelSetValueMain(1, items);

					}catch(ExceptionWaning e){
						//�ő�s���𒴂���ƁAExceptionWaning��Throw�����̂Ń��[�v���I������B
						break;

					}finally{

					}

				}

			}

			//�T���v���Q�ڂ̏o��
			if(lstRecset2 != null){

				//�_�E�����[�h�p��EXCEL�𐶐�����
				for (int i = 0; i < lstRecset2.size(); i++) {

					//�������ʂ�1�s�������o��
					Object[] items = (Object[]) lstRecset2.get(i);

					try{

						//Excel�ɒl���Z�b�g����
						ExcelSetValueMain(2, items);

					}catch(ExceptionWaning e){
						//�ő�s���𒴂���ƁAExceptionWaning��Throw�����̂Ń��[�v���I������B
						break;

					}finally{

					}

				}

			}

			//�T���v���R�ڂ̏o��
			if(lstRecset3 != null){

				//�_�E�����[�h�p��EXCEL�𐶐�����
				for (int i = 0; i < lstRecset3.size(); i++) {

					//�������ʂ�1�s�������o��
					Object[] items = (Object[]) lstRecset3.get(i);

					try{

						//Excel�ɒl���Z�b�g����
						ExcelSetValueMain(3, items);

					}catch(ExceptionWaning e){
						//�ő�s���𒴂���ƁAExceptionWaning��Throw�����̂Ń��[�v���I������B
						break;

					}finally{

					}

				}

			}

			//�yQP@10181_No.21�z start
			//�T���v���S�ڂ̏o��
			if(lstRecset4 != null){

				//�_�E�����[�h�p��EXCEL�𐶐�����
				for (int i = 0; i < lstRecset4.size(); i++) {

					//�������ʂ�1�s�������o��
					Object[] items = (Object[]) lstRecset4.get(i);

					try{

						//Excel�ɒl���Z�b�g����
						ExcelSetValueMain(4, items);

					}catch(ExceptionWaning e){
						//�ő�s���𒴂���ƁAExceptionWaning��Throw�����̂Ń��[�v���I������B
						break;

					}finally{

					}

				}

			}
			//�yQP@10181_No.21�z end

			//�G�N�Z���t�@�C�����_�E�����[�h�t�H���_�ɐ�������
//			ret = super.ExcelOutput();
			ret = super.ExcelOutput(
					makeShisakuNo(
							reqData.getFieldVale(0, 0, "cd_shain")
							,reqData.getFieldVale(0, 0, "nen")
							,reqData.getFieldVale(0, 0, "no_oi")
							)
					);

		} catch (Exception e) {
			em.ThrowException(e, "");

		} finally {

		}
		return ret;

	}

	/**
	 * ����Excel�o�͍��ڂ�ݒ肷��
	 * @param intGenryoIdx : �����ԍ�
	 * @param items : ��������
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private void ExcelSetValueMain(int intGenryoIdx, Object[] items) throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		int intKetasu = 0;

		try {

			//�����������擾����
			if ( !toString(items[17]).isEmpty() ) {
				intKetasu = Integer.parseInt(items[17].toString());

			}

			//Excel�ɒl���Z�b�g����
			//�i��
			super.ExcelSetValue("�i��" + intGenryoIdx, toString(items[16]));
			//�T���v���ԍ�
			super.ExcelSetValue("�r�d�p" + intGenryoIdx, toString(items[20]));
			//�i�R�[�h
			super.ExcelSetValue("�i�R�[�h" + intGenryoIdx, toString(items[4]));
			//������
			super.ExcelSetValue("������" + intGenryoIdx, toString(items[5]));
			//�z��
			super.ExcelSetValue("�z��" + intGenryoIdx, toString(SetShosuKeta(items[6],intKetasu)));
			//����
			super.ExcelSetValue("����" + intGenryoIdx, toDouble(SetShosuKeta(items[7],2)));
			//�\���ď��
			super.ExcelSetValue("�\���ď��" + intGenryoIdx, toString(items[8]));
			//�Y�������
			super.ExcelSetValue("�Y�������" + intGenryoIdx, toString(items[9]));
			//�h�{�v�Z�p�@�H�i�ԍ�
			super.ExcelSetValue("�h�{�v�Z�p�@�H�i�ԍ�" + intGenryoIdx, SetEiyoNo(items));
			//����
			super.ExcelSetValue("����" + intGenryoIdx, toString(items[15]));
			//���v�d��
			super.ExcelSetValue("���v�d�ʃ^�C�g��" + intGenryoIdx, "���v�d��(g)");
			super.ExcelSetValue("���v�d��" + intGenryoIdx, toString(SetShosuKeta(items[19],intKetasu)));

			//�yQP@10181_No.21�z start
			//�ۑ����@
			super.ExcelSetValue("�ۑ����@" + intGenryoIdx, toString(items[21]));
			//���Y�H��
			super.ExcelSetValue("���Y�H��" + intGenryoIdx, toString(items[22]));
			//�ܖ�����
			super.ExcelSetValue("�ܖ�����" + intGenryoIdx, toString(items[23]));
			//�e��
			super.ExcelSetValue("�e��" + intGenryoIdx, toString(items[24]));
			//�e��
			super.ExcelSetValue("�e��" + intGenryoIdx, toString(items[25]));
			//���@�ԍ�
			super.ExcelSetValue("���@�ԍ�" + intGenryoIdx, toString(items[26]));
			//�ꊇ�\���̖���
			super.ExcelSetValue("�ꊇ�\���̖���" + intGenryoIdx, toString(items[27]));
			//����i�R�[�h
			super.ExcelSetValue("����i�R�[�h" + intGenryoIdx, toString(items[28]));
			//�쐬��
			super.ExcelSetValue("�쐬��" + intGenryoIdx, toString(items[31]));
			//�O���[�v
			super.ExcelSetValue("�O���[�v" + intGenryoIdx, toString(items[29]));
			//���O
			super.ExcelSetValue("���O" + intGenryoIdx, toString(items[30]));
			//�T���v�������� �O���[�v
			super.ExcelSetValue("�O���[�v", toString(items[29]));
			//�T���v�������� �O���[�v
			super.ExcelSetValue("���O", toString(items[30]));
			//�T���v�������� �쐬��
			super.ExcelSetValue("�쐬��", toString(items[31]));
			//�yQP@10181_No.21�z end

		} catch (Exception e) {
			em.ThrowException(e, "");

		} finally {
			intKetasu = 0;

		}

	}

	/**
	 * ����i�f�[�^(�T���v���P��)����������
	 * @param KindBean : ���N�G�X�g�f�[�^�i�@�\�j
	 * @return List : �������ʂ̃��X�g
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning : �Y���f�[�^����
	 */
	private List<?> getData1(RequestResponsKindBean reqData)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		//��������
		List<?> ret = null;

		String strCd_shain = "";
		String strNen = "";
		String strNo_oi = "";
		String strSeq_shisaku = "";

		try {

			//����iCD�@�Ј��R�[�h
			strCd_shain = reqData.getFieldVale(0, 0, "cd_shain");
			//����iNo�@�N
			strNen = reqData.getFieldVale(0, 0, "nen");
			//����iNo�@�ǔ�
			strNo_oi = reqData.getFieldVale(0, 0, "no_oi");
			//����No�i�T���v���ԍ��j
			strSeq_shisaku = reqData.getFieldVale(0, 0, "seq_shisaku1");

			if (strSeq_shisaku != ""){
				ret = getData(strCd_shain, strNen, strNo_oi,strSeq_shisaku);

			}

		} catch (Exception e) {
			em.ThrowException(e, "�T���v���������T���v���P�ADB�����Ɏ��s���܂����B");

		} finally {

		}
		return ret;

	}
	/**
	 * ����i�f�[�^(�T���v���Q��)����������
	 * @param KindBean : ���N�G�X�g�f�[�^�i�@�\�j
	 * @return List : �������ʂ̃��X�g
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning : �Y���f�[�^����
	 */
	private List<?> getData2(RequestResponsKindBean reqData)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		//��������
		List<?> ret = null;

		String strCd_shain = "";
		String strNen = "";
		String strNo_oi = "";
		String strSeq_shisaku = "";

		try {

			//����iCD�@�Ј��R�[�h
			strCd_shain = reqData.getFieldVale(0, 0, "cd_shain");
			//����iNo�@�N
			strNen = reqData.getFieldVale(0, 0, "nen");
			//����iNo�@�ǔ�
			strNo_oi = reqData.getFieldVale(0, 0, "no_oi");
			//����No�i�T���v���ԍ��j
			strSeq_shisaku = reqData.getFieldVale(0, 0, "seq_shisaku2");

			if (strSeq_shisaku != ""){
				ret = getData(strCd_shain, strNen, strNo_oi,strSeq_shisaku);

			}

		} catch (Exception e) {
			em.ThrowException(e, "�T���v���������T���v���Q�ADB�����Ɏ��s���܂����B");

		} finally {

		}
		return ret;

	}
	/**
	 * ����i�f�[�^(�T���v���R��)����������
	 * @param KindBean : ���N�G�X�g�f�[�^�i�@�\�j
	 * @return List : �������ʂ̃��X�g
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning : �Y���f�[�^����
	 */
	private List<?> getData3(RequestResponsKindBean reqData)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		//��������
		List<?> ret = null;

		String strCd_shain = "";
		String strNen = "";
		String strNo_oi = "";
		String strSeq_shisaku = "";

		try {

			//����iCD�@�Ј��R�[�h
			strCd_shain = reqData.getFieldVale(0, 0, "cd_shain");
			//����iNo�@�N
			strNen = reqData.getFieldVale(0, 0, "nen");
			//����iNo�@�ǔ�
			strNo_oi = reqData.getFieldVale(0, 0, "no_oi");
			//����No�i�T���v���ԍ��j
			strSeq_shisaku = toString(reqData.getFieldVale(0, 0, "seq_shisaku3"));

			if (strSeq_shisaku != ""){
				ret = getData(strCd_shain, strNen, strNo_oi,strSeq_shisaku);

			}

		} catch (Exception e) {
			em.ThrowException(e, "�T���v���������T���v���R�ADB�����Ɏ��s���܂����B");

		} finally {

		}
		return ret;

	}
	/**
	 * ����i�f�[�^(�T���v���S��)����������
	 * @param KindBean : ���N�G�X�g�f�[�^�i�@�\�j
	 * @return List : �������ʂ̃��X�g
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning : �Y���f�[�^����
	 */
	private List<?> getData4(RequestResponsKindBean reqData)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		//��������
		List<?> ret = null;

		String strCd_shain = "";
		String strNen = "";
		String strNo_oi = "";
		String strSeq_shisaku = "";

		try {

			//����iCD�@�Ј��R�[�h
			strCd_shain = reqData.getFieldVale(0, 0, "cd_shain");
			//����iNo�@�N
			strNen = reqData.getFieldVale(0, 0, "nen");
			//����iNo�@�ǔ�
			strNo_oi = reqData.getFieldVale(0, 0, "no_oi");
			//����No�i�T���v���ԍ��j
			strSeq_shisaku = toString(reqData.getFieldVale(0, 0, "seq_shisaku4"));

			if (strSeq_shisaku != ""){
				ret = getData(strCd_shain, strNen, strNo_oi,strSeq_shisaku);

			}

		} catch (Exception e) {
			em.ThrowException(e, "�T���v���������T���v���S�ADB�����Ɏ��s���܂����B");

		} finally {

		}
		return ret;

	}
	/**
	 * �Ώۂ̎���i�f�[�^����������
	 * @param strCd_shain : ����Ј�cd
	 * @param strNen : ����N
	 * @param strNo_oi : �ǔ�
	 * @return list : ��������
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private List<?> getData(String strCd_shain, String strNen,String strNo_oi,String strSeq_shisaku)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		//��������
		List<?> ret = null;
		//SQL�@StringBuffer
		StringBuffer strSql = new StringBuffer();

		try {
			//SQL���̍쐬
			strSql = MakeSQLBuf(strCd_shain,strNen,strNo_oi,strSeq_shisaku);

			//SQL�����s
			ret = searchDB.dbSearch(strSql.toString());

			//�������ʂ��Ȃ��ꍇ
			if (ret.size() == 0){
				em.ThrowException(ExceptionKind.�x��Exception,"W000401", strSql.toString(), "", "");

			}

		} catch (Exception e) {
			em.ThrowException(e, "�T���v���������ADB�����Ɏ��s���܂����B");

		} finally {
			//�ϐ��̍폜
			strSql = null;

		}
		return ret;

	}
	/**
	 * ���N�G�X�g�f�[�^���A����f�[�^����SQL�𐶐�����
	 * @param strCd_shain : ����Ј�cd
	 * @param strNen : ����N
	 * @param strNo_oi : �ǔ�
	 * @param strSeq_shisaku : ����No�i�T���v���ԍ��j
	 * @return String : sql
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private StringBuffer MakeSQLBuf(String strCd_shain,	String strNen,String strNo_oi, String strSeq_shisaku)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		StringBuffer ret = new StringBuffer();

		try {

			//SQL���̍쐬
			ret.append(" SELECT ");
			ret.append("  A.cd_shain AS ����ԍ�_�Ј�CD ");
			ret.append(" , A.nen AS ����ԍ�_�N ");
			ret.append(" , A.no_oi AS ����ԍ�_�ǔ� ");
			ret.append(" , A.seq_shisaku AS ����SEQ ");
			ret.append(" , D.cd_genryo AS �i�R�[�h ");
			ret.append(" , D.nm_genryo AS ������ ");
			ret.append(" , CONVERT(VARCHAR,C.quantity) AS �z�� ");
			ret.append(" , CONVERT(VARCHAR,D.budomari) AS ���� ");
			ret.append(" , E.hyojian AS �\���ď�� ");
			ret.append(" , E.tenkabutu AS �Y������� ");
			ret.append(" , E.no_eiyo1 AS �h�{�v�Z�p�H�i�ԍ�1 ");
			ret.append(" , E.no_eiyo2 AS �h�{�v�Z�p�H�i�ԍ�2 ");
			ret.append(" , E.no_eiyo3 AS �h�{�v�Z�p�H�i�ԍ�3 ");
			ret.append(" , E.no_eiyo4 AS �h�{�v�Z�p�H�i�ԍ�4 ");
			ret.append(" , E.no_eiyo5 AS �h�{�v�Z�p�H�i�ԍ�5 ");
			ret.append(" , E.memo AS ���� ");
			ret.append(" , B.nm_hin AS �i�� ");
			ret.append(" , ISNULL(G.value1,0) AS �������� ");
			ret.append(" , A.juryo_shiagari_g AS �d�オ��� ");
			ret.append(" , F.sum_quantity AS ���v�d�� ");
			ret.append(" , RIGHT('0000000000'+CONVERT(varchar,A.cd_shain),10)  ");
			//ret.append(" , RIGHT('00000'+CONVERT(varchar,A.cd_shain),5)  ");
			ret.append("   +'-'+ RIGHT('00'+CONVERT(varchar,A.nen),2)  ");
			ret.append("   +'-'+ RIGHT('000'+CONVERT(varchar,A.no_oi),3) ");
			ret.append("   +'  '+  A.nm_sample AS �T���v��No ");

			//�yQP@10181_No.21�z start
			ret.append(" ,G2.nm_literal AS �ۑ����@ ");
			ret.append(" ,B1.nm_busho AS ���Y�H�� ");
			ret.append(" ,B.shomikikan AS �ܖ����� ");
			ret.append(" ,B.youki AS �e�� ");
			ret.append(" ,B.yoryo+G3.nm_literal AS �e�� ");
			ret.append(" ,TS.no_seiho1 AS ���@�ԍ� ");
			ret.append(" ,G4.nm_literal AS �ꊇ�\���̖��� ");
			ret.append(" ,RIGHT('0000000000'+CONVERT(varchar ");
			ret.append(" 		,A.cd_shain) ");
			ret.append(" 	,10) +'-'+ RIGHT('00'+CONVERT(varchar ");
			ret.append(" 		,A.nen) ");
			ret.append(" 	,2) +'-'+ RIGHT('000'+CONVERT(varchar ");
			ret.append(" 		,A.no_oi) ");
			ret.append(" 	,3) AS ����i�R�[�h ");
			ret.append(" ,MG.nm_team AS �O���[�v ");
			ret.append(" ,MU.nm_user AS ���O ");
			ret.append(" ,B.dt_toroku AS �쐬�� ");
			//�yQP@10181_No.21�z end

			ret.append(" FROM  ");
			ret.append(" tr_shisaku AS A ");
			ret.append(" LEFT JOIN tr_shisakuhin AS B ");
			ret.append(" ON A.cd_shain = B.cd_shain ");
			ret.append(" AND A.nen = B.nen ");
			ret.append(" AND A.no_oi = B.no_oi ");
			ret.append(" LEFT JOIN tr_shisaku_list AS C ");
			ret.append(" ON A.cd_shain = C.cd_shain ");
			ret.append(" AND A.nen = C.nen ");
			ret.append(" AND A.no_oi = C.no_oi ");
			ret.append(" AND A.seq_shisaku = C.seq_shisaku ");
			ret.append(" LEFT JOIN tr_haigo AS D ");
			ret.append(" ON C.cd_shain = D.cd_shain ");
			ret.append(" AND C.nen = D.nen ");
			ret.append(" AND C.no_oi = D.no_oi ");
			ret.append(" AND C.cd_kotei = D.cd_kotei ");
			ret.append(" AND C.seq_kotei = D.seq_kotei ");
			ret.append(" LEFT JOIN ma_genryo AS E ");
			ret.append(" ON D.cd_kaisha = E.cd_kaisha ");
			ret.append(" AND D.cd_genryo = E.cd_genryo ");
			//���v�d��
			ret.append(" LEFT JOIN (  ");
			ret.append("  SELECT ");
			ret.append("    SUM(Fs.quantity) AS sum_quantity  ");
			ret.append("    ,Fs.cd_shain AS cd_shain ");
			ret.append("    ,Fs.nen AS nen ");
			ret.append("    ,Fs.no_oi AS no_oi ");
			ret.append("    ,Fs.seq_shisaku AS seq_shisaku ");
			ret.append("  FROM tr_shisaku_list Fs ");
			ret.append("  GROUP BY Fs.cd_shain, Fs.nen, Fs.no_oi, Fs.seq_shisaku ) F ");
			ret.append("  ON A.cd_shain = F.cd_shain ");
			ret.append("  AND A.nen = F.nen ");
			ret.append("  AND A.no_oi = F.no_oi ");
			ret.append("  AND A.seq_shisaku = F.seq_shisaku ");
			//��������(���e�����l)
			ret.append("  LEFT JOIN ma_literal G ");
			ret.append("   ON  G.cd_category = 'K_shosu' ");
			ret.append("   AND  G.cd_literal = B.keta_shosu ");

			//�yQP@10181_No.21�z start
			ret.append("  LEFT JOIN ma_literal G2 ");
			ret.append("  ON G2.cd_category = 'K_toriatukaiondo' ");
			ret.append("  AND G2.cd_literal = B.cd_ondo ");
			ret.append("  LEFT JOIN ma_busho B1 ");
			ret.append("  ON B1.cd_kaisha = B.cd_kaisha ");
			ret.append("  AND B1.cd_busho = B.cd_kojo ");
			ret.append("  LEFT JOIN ma_literal G3 ");
			ret.append("  ON G3.cd_category = 'K_tani' ");
			ret.append("  AND G3.cd_literal = B.cd_tani ");
			ret.append("  LEFT JOIN tr_shisaku AS TS ");
			ret.append("  ON B.cd_shain = TS.cd_shain ");
			ret.append("  AND B.nen = TS.nen ");
			ret.append("  AND B.no_oi = TS.no_oi ");
			ret.append("  AND B.seq_shisaku = TS.seq_shisaku ");
			ret.append("  LEFT JOIN ma_literal G4 ");
			ret.append("  ON G4.cd_category = 'K_ikatuhyouzi' ");
			ret.append("  AND G4.cd_literal = B.cd_ikatu ");
			ret.append("  LEFT JOIN ma_team MG ");
			ret.append("  ON MG.cd_group = B.cd_group ");
			ret.append("  AND MG.cd_team = B.cd_team ");
			ret.append("  LEFT JOIN ma_user MU ");
			ret.append("  ON MU.id_user = B.cd_shain ");
			//�yQP@10181_No.21�z end

			ret.append(" WHERE  ");
			ret.append("     A.cd_shain = " + strCd_shain + " ");
			ret.append(" AND A.nen = " + strNen + " ");
			ret.append(" AND A.no_oi = " + strNo_oi + " ");
			ret.append(" AND A.seq_shisaku = " + strSeq_shisaku + " ");
			ret.append(" AND ISNULL(C.quantity,0) > 0 ");
			//�\����
			ret.append(" ORDER BY D.sort_kotei, D.sort_genryo ");

		} catch (Exception e) {
			this.em.ThrowException(e, "�T���v���������A����SQL�̐����Ɏ��s���܂����B");

		} finally {

		}
		return ret;

	}

	/**
	 * Excel�o�͗p�̉h�{�v�Z�H�i�ԍ��̐ݒ�
	 * @param items : ��������
	 * @return Excel�ɐݒ肷��h�{�v�Z�H�i�ԍ��̕�����
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private String SetEiyoNo(Object[] items) throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		String ret = "";

		try{

			//�T��J��Ԃ�
			for ( int i=0; i<5; i++ ) {

				//�h�{�v�Z�H�i�ԍ���NULL�܂��́A�󕶎��ł͂Ȃ��ꍇ
				if ( !toString(items[i+10]).isEmpty() ) {

					//�����񂪋�ł͂Ȃ��ꍇ�A�J���}��}��
					if ( !ret.isEmpty() ) {
						ret += ",";

					}

					//�h�{�v�Z�H�i�ԍ��𕶎���ɒǉ�
					ret += toString(items[i+10]);

				}

			}

		} catch (Exception e) {
			this.em.ThrowException(e, "Excel�o�͗p�h�{�v�Z�H�i�ԍ��ݒ菈���Ɏ��s���܂����B");

		} finally {

		}
		return ret;

	}

	/**
	 * �����������킹����
	 * @param Value : �Ώےl
	 * @param intKetasu : �w�菬������
	 * @return  �����킹�ҏW�㕶����
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	protected Object SetShosuKeta(Object Value, int intKetasu)
		throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		Object ret = null;

		try {

			//�Ώےl��NULL�܂��͋󕶎��ł͖����ꍇ
			if ( !toString(Value).isEmpty() ) {

				//�l��Decimal�ɐݒ�
				BigDecimal dciValue = new BigDecimal(toDouble(Value));

				//�l���w�菬�������ɍ��킹��
				ret = dciValue.setScale(intKetasu, BigDecimal.ROUND_HALF_EVEN).toString();

			}

		} catch(Exception e) {
			this.em.ThrowException(e, "");

		} finally {

		}
		return ret;

	}

}