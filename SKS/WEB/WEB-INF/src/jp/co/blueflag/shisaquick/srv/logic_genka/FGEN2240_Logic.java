package jp.co.blueflag.shisaquick.srv.logic_genka;

import java.util.List;

import jp.co.blueflag.shisaquick.srv.base.LogicBaseJExcel;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsKindBean;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsTableBean;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;
import jp.co.blueflag.shisaquick.srv.common.ExceptionManager.ExceptionKind;

/**
 * �������Z�\�i�c�Ɓj�𐶐�����
 * @author TT.Kitazawa
 * @since 2015/03/03
 */
public class FGEN2240_Logic extends LogicBaseJExcel {

	/**
	 * �R���X�g���N�^
	 */
	public FGEN2240_Logic() {
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
			//inputCheck �Ń`�F�b�N��
			if (toString( reqData.getFieldVale(0, 0, "seq_shisaku")).equals("")) {
				em.ThrowException(ExceptionKind.���Exception, "E000207", "�����", "", "");
			}

			//DB����
			super.createSearchDB();
			lstRecset = getData(reqData);

			//�������Z�\�i�c�ƁjExcel�t�@�C������
			DownLoadPath = makeExcelFile(lstRecset,  reqData);



			//���X�|���X�f�[�^����
			ret = CreateRespons(DownLoadPath);

		} catch (Exception e) {
			em.ThrowException(e, "�������Z�\�i�c�Ɓj�̐����Ɏ��s���܂����B");

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
			ret.setID("FGEN2240");

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
	 * �������Z�\�i�c�Ɓj(EXCEL)�𐶐�����
	 * @param lstRecset : �����f�[�^���X�g
	 * @return�@String : �_�E�����[�h�p�̃p�X
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private String makeExcelFile(

			List<?> lstRecset
			, RequestResponsKindBean reqData
			)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		// �߂�l�F�_�E�����[�h�p�̃p�X
		String ret = "";
		// MAX�T���v����
		int intMax = 6;
		// �T���v���񂲂Ƃ̎���SEQ
		String[] strArraySeq = new String[intMax];
		// �T���v���񂲂Ƃ̎��Z����
		//  [0]:�����v�i�~�j�^�P�[�X, [1]:�����v�i�~�j�^��, [2]: �����v�i�~�j�^Kg, [3]:����, [4]:�e���i���j
		String[][] strArraySisan = new String[intMax][5];

		try {
			//EXCEL�e���v���[�g��ǂݍ���
			super.ExcelReadTemplate("�������Z�\�i�c�Ɓj");

			//�I���T���v�������擾�i�e�[�u��Bean�̌����j
			int reqCnt = ((RequestResponsTableBean)reqData.GetItem(0)).GetCnt();

			try{
				// �T���v���񂲂Ƃ̒l���擾
				for (int i = 0; i < reqCnt; i++) {
					//����SEQ���擾
					strArraySeq[i] = toString(reqData.getFieldVale(0, i, "seq_shisaku"));
					//���Z���ڂ��擾:��ʕ\���̒l�Ȃ̂�String toString�K�v�H
					// �����v�i�~�j�^�P�[�X
					strArraySisan[i][0] = toString(reqData.getFieldVale(0, i, "genkakei"));
					// �����v�i�~�j�^��
					strArraySisan[i][1] = toString(reqData.getFieldVale(0, i, "genkakeiKo"));
					// �����v�i�~�j�^Kg
					strArraySisan[i][2] = toString(reqData.getFieldVale(0, i, "kg_genkake"));
					// ����
					strArraySisan[i][3] = toString(reqData.getFieldVale(0, i, "baika"));
					// �e���i���j
					strArraySisan[i][4] = toString(reqData.getFieldVale(0, i, "arari"));
				}

			}catch(Exception e){

			}

			//�_�E�����[�h�p��EXCEL�𐶐�����
			for (int i = 0; i < lstRecset.size(); i++) {
				//�������ʂ�1�s�������o��
				Object[] items = (Object[]) lstRecset.get(i);

				if(i==0){
					//EXCEL�e���v���[�g��ǂݍ���
					// �t�@�C�����F����R�[�h + "�i�c�Ɓj" + �i�� + ���t
					ret = super.ExcelOutput_genka(
							makeShisakuNo(
							reqData.getFieldVale(0, 0, "cd_shain")
							,reqData.getFieldVale(0, 0, "nen")
							,reqData.getFieldVale(0, 0, "no_oi")
							),
							toString(items[0]));
				}

				try{
					//Excel�ɒl���Z�b�g����

					//�i��
					super.ExcelSetValue("�i��", toString(items[0]));
					//�˗��ԍ�
					super.ExcelSetValue("�˗��ԍ�", toString(items[1]));
					//���t
					super.ExcelSetValue("���t", toString(items[2]));
					//���Z����
					super.ExcelSetValue("���Z����", toString(items[8]));
					//�̗p�T���v��
					super.ExcelSetValue("�̗p�T���v��", toString(items[52]));
					//�}�Ԏ��
					super.ExcelSetValue("�}�Ԏ��", toString(items[39]));
					//������
					super.ExcelSetValue("������", toString(items[18]));
					//�c��
					super.ExcelSetValue("�c��", toString(items[19]));
					//���Y�Ǘ���
					super.ExcelSetValue("���Y�Ǘ���", toString(items[20]));
					//�H��
					super.ExcelSetValue("�H��", toString(items[21]));

					//�������Z����(�c�Ɨp)
					super.ExcelSetValue("�������Z����(�c�Ɨp)", toString(items[17]));
					//�̐Ӊ��
					super.ExcelSetValue("�̐Ӊ��", toString(items[7]));
					//�������
					super.ExcelSetValue("�������", toString(items[4]));
					//�H�ꖼ(������)
					super.ExcelSetValue("�����H��", toString(items[5]));
					//�H�ꖼ(������)
					super.ExcelSetValue("�S���c��", toString(items[10]));

					//�e��E���
					super.ExcelSetValue("�e��E���", toString(items[11]));
					//���e��
					super.ExcelSetValue("���e��", toString(items[12]));
					//���萔
					super.ExcelSetValue("���萔", toString(items[13]));
					//�׎p
					super.ExcelSetValue("�׎p", toString(items[14]));
					//�戵���x
					super.ExcelSetValue("�戵���x", toString(items[15]));
					//�ܖ�����
					super.ExcelSetValue("�ܖ�����", toString(items[16]));


					//����SEQ���Ƃɕ���i�ŏI���Z�p�j
					if ( toString(items[40]).equals(strArraySeq[0]) ){

						// �T���v����F�P��ڂ̃f�[�^�Z�b�g
						setExcelValue(items, "1", strArraySisan[0]);

					} else if ( toString(items[40]).equals(strArraySeq[1]) ){

						// �T���v����F�Q��ڂ̃f�[�^�Z�b�g
						setExcelValue(items, "2", strArraySisan[1]);

					} else if ( toString(items[40]).equals(strArraySeq[2]) ){

						// �T���v����F�R��ڂ̃f�[�^�Z�b�g
						setExcelValue(items, "3", strArraySisan[2]);

					} else if ( toString(items[40]).equals(strArraySeq[3]) ){

						// �T���v����F�S��ڂ̃f�[�^�Z�b�g
						setExcelValue(items, "4", strArraySisan[3]);

					} else if ( toString(items[40]).equals(strArraySeq[4]) ){

						// �T���v����F�T��ڂ̃f�[�^�Z�b�g
						setExcelValue(items, "5", strArraySisan[4]);

					} else if ( toString(items[40]).equals(strArraySeq[5]) ){

						// �T���v����F�U��ڂ̃f�[�^�Z�b�g
						setExcelValue(items, "6", strArraySisan[5]);

					}

				}catch(ExceptionWaning e){
					break;

				}finally{

				}

			}

			super.ExcelWrite();
			super.close();

		} catch (Exception e) {
			em.ThrowException(e, "");

		} finally {
			//�ϐ��̍폜
			strArraySeq = null;

		}
		return ret;

	}

	/**
	 * �t�@�C�����p�̎���ԍ��𐶐�����i�u�c�Ɓv�t���j
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
		//�c��
		ret += "�i�c�Ɓj";

		return ret;

	}

	/**
	 * �T���v����̃f�[�^��Excel�ɃZ�b�g����
	 * @param items  �F �������ʂ�1�s���f�[�^
	 * @param cellNo �F ��ԍ�
	 * @param argSisan�F ���Z���ڂ̒l
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private void setExcelValue(Object[] items, String cellNo, String[] argSisan)
			throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		String strSetNm = "";

		//�T���v��No
		strSetNm = "�T���v��No" + cellNo;
		super.ExcelSetValue(strSetNm, toString(items[22]));
		strSetNm = "�T���v��" + cellNo;
		super.ExcelSetValue(strSetNm, toString(items[22]));

		//��]����
		strSetNm = "��]����" + cellNo;
		super.ExcelSetValue(strSetNm, toString(items[23]));
		//����
		strSetNm = "��]����" + cellNo;
		super.ExcelSetValue(strSetNm, toString(items[24]));
		//�����P�ʂ����ꂼ�ꌴ���A���������ɐݒ�B
		strSetNm = "�����P��" + cellNo;
		super.ExcelSetValue(strSetNm, toString(items[25]));
		strSetNm = "�����P��" + cellNo;
		super.ExcelSetValue(strSetNm, toString(items[25]));
		//�̔�����  �̔����ԑI��(�ʔNor�X�|�b�g)+�̔�����+�̔����ԒP��
		strSetNm = "�̔�����" + cellNo;
		super.ExcelSetValue(strSetNm, toString(items[27]) + toString(items[26]) + toString(items[28]));
		//�z�蕨��
		strSetNm = "�z�蕨��" + cellNo;
		super.ExcelSetValue(strSetNm, toDouble(items[29]));
		strSetNm = "�z�蕨�ʒP��" + cellNo;
		super.ExcelSetValue(strSetNm, toString(items[30])+toString(items[31]));
		//�z�蕨�ʔ��l
		strSetNm = "�z�蕨�ʔ��l" + cellNo;
		super.ExcelSetValue(strSetNm, toString(items[32]));
		//��������
		strSetNm = "��������" + cellNo;
		super.ExcelSetValue(strSetNm, toString(items[33]));

		//�v�攄��
		strSetNm = "�v�攄��" + cellNo;
		super.ExcelSetValue(strSetNm, toString(items[34]));
		//�v�旘�v
		strSetNm = "�v�旘�v" + cellNo;
		super.ExcelSetValue(strSetNm, toString(items[35]));
		//�̔��㔄��
		strSetNm = "�̔��㔄��" + cellNo;
		super.ExcelSetValue(strSetNm, toString(items[36]));
		//�̔��㔄��
		strSetNm = "�̔��㗘�v" + cellNo;
		super.ExcelSetValue(strSetNm, toString(items[37]));

		//�������b�g
		strSetNm = "�������b�g" + cellNo;
		super.ExcelSetValue(strSetNm, toString(items[38]));

		//���Z���~�̃T���v����
		if(toString(items[43]).equals("1")){
			strSetNm = "���Z��" + cellNo;
			super.ExcelSetValue(strSetNm, "���Z���~");

		} else if(toString(items[42]).equals("1")){
			//�Œ荀�ڃ`�F�b�N����Ă���T���v����
			strSetNm = "���ڌŒ�`�F�b�N" + cellNo;
			super.ExcelSetValue(strSetNm, "�����ڌŒ�T���v��NO");

			//���Z���F�Œ荀�ڃ`�F�b�NON�̎��\��
			strSetNm = "���Z��" + cellNo;
			super.ExcelSetValue(strSetNm, toString(items[44]));
		}

		// ���Z�����󔒂̎��́y�v�Z���ځz��\�����Ȃ�
		//   ��ʂ̒l���擾���Ă���̂ŁA���̂܂ܕ\�����Ă���
		// �����v�i�~�j�^�P�[�X
		strSetNm = "�����v/�P�[�X" + cellNo;
		super.ExcelSetValue(strSetNm, argSisan[0]);
		// �����v�i�~�j�^��
		strSetNm = "�����v/��" + cellNo;
		super.ExcelSetValue(strSetNm, argSisan[1]);
		// �����v�i�~�j�^Kg
		strSetNm = "�����v/�j�f" + cellNo;
		super.ExcelSetValue(strSetNm, argSisan[2]);
		// ����
		strSetNm = "����" + cellNo;
		super.ExcelSetValue(strSetNm, argSisan[3]);
		// �e���i���j
		strSetNm = "�e��" + cellNo;
		super.ExcelSetValue(strSetNm, argSisan[4]);

/*
 * // ���Z�����󔒂̎��́y�v�Z���ځz��\�� �|�|�c�a�̒l�͎g�p���Ȃ�
			if((items[44] != null) && (!items[44].equals(""))) {
				//���Z���ځFnull�̎��͕\�����Ȃ�
				if ((items[45] != null) && (!items[45].equals(""))) {
					strSetNm = "�����v/�P�[�X" + cellNo;
					super.ExcelSetValue(strSetNm, toDouble(items[45]));
				}
				if ((items[46] != null) && (!items[46].equals(""))) {
					strSetNm = "�����v/��" + cellNo;
					super.ExcelSetValue(strSetNm, toDouble(items[46]));
				}
				if ((items[47] != null) && (!items[47].equals(""))) {
					strSetNm = "�����v/�j�f" + cellNo;
					super.ExcelSetValue(strSetNm, toDouble(items[47]));
				}
				// ���Z���ځitr_shisan_shisaku_kotei�j �̔����͒P�ʕt�ׁ̈A������
				if ((items[48] != null) && (!items[48].equals(""))) {
					strSetNm = "����" + cellNo;
					super.ExcelSetValue(strSetNm, toString(items[48]));
				}
				// ��ʁF�v�Z�O�̎��A-1 ���Z�b�g����Ă���i�����ł�null�j
				if ((items[49] != null) && (!items[49].equals(""))) {
					strSetNm = "�e��" + cellNo;
					super.ExcelSetValue(strSetNm, toDouble(items[49]));
					strSetNm = "�e����" + cellNo;
					super.ExcelSetValue(strSetNm, "��");
				}
			}
*/
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
			em.ThrowException(e, "�������Z�\�i�c�Ɓj�ADB�����Ɏ��s���܂����B");

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
			String strNo_eda = "";
			String strShisakuSeq = "";

			//����iCD�@�Ј��R�[�h	cd_shain
			strCd_shain = reqData.getFieldVale(0, 0, "cd_shain");
			//����iNo�@�N		nen
			strNen = reqData.getFieldVale(0, 0, "nen");
			//����iNo�@�ǔ�		no_oi
			strNo_oi = reqData.getFieldVale(0, 0, "no_oi");
			//����iNo�@�}��		no_eda
			strNo_eda = reqData.getFieldVale(0, 0, "no_eda");

			//�I���T���v�������擾�i�e�[�u��Bean�̌����j
			int reqCnt = ((RequestResponsTableBean)reqData.GetItem(0)).GetCnt();

			//SQL���̍쐬
			ret.append(" SELECT ");
			ret.append("   CASE WHEN T310.nm_edaShisaku IS NOT NULL ");
			ret.append("   THEN ");
			ret.append("   CASE RTRIM(T310.nm_edaShisaku) WHEN '' ");
			ret.append("   THEN T110.nm_hin ");
			ret.append("   ELSE T110.nm_hin + ' �y' + T310.nm_edaShisaku + '�z' ");
			ret.append("   END ");
			ret.append("   ELSE T110.nm_hin END AS hinmei ");						//0 �i��

			ret.append("  ,CONVERT(VARCHAR,T110.no_irai) AS no_irai ");				//1 �˗��ԍ�
			ret.append("  ,CONVERT(VARCHAR,YEAR(GETDATE())) + '�N' ");
			ret.append("   + CONVERT(VARCHAR,MONTH(GETDATE())) + '��' ");
			ret.append("   + CONVERT(VARCHAR,DAY(GETDATE())) + '��' AS dt_hizuke ");//2
			ret.append("  ,T310.cd_kojo AS cd_kojo ");								//3 �H��R�[�h
			ret.append("  ,M104.nm_kaisha  AS nm_kaisha ");							//4 ������Ж�
			ret.append("  ,M104.nm_busho  AS nm_busho ");							//5 �H�ꖼ(������)
			ret.append("  ,T310.cd_hanseki AS cd_hanseki ");						//6 �̐Ӊ�ЃR�[�h
			ret.append("  ,M104_hanseki.nm_kaisha  AS nm_hanseki ");				//7 �̐Ӊ�Ж�

//			ret.append("  ,T310.dt_kizitu ");										//8 ���Z����
			ret.append("  ,CONVERT(VARCHAR,YEAR(T310.dt_kizitu)) + '�N' ");
			ret.append("   + CONVERT(VARCHAR,MONTH(T310.dt_kizitu)) + '��' ");
			ret.append("   + CONVERT(VARCHAR,DAY(T310.dt_kizitu)) + '��' AS dt_kizitu ");//8 ���Z����

			ret.append("  ,M101.nm_user AS kenkyusho ");							//9
			ret.append("  ,M110.nm_user AS eigyo ");								//10 �S���c��

			ret.append("  ,T110.youki AS youki ");								//11 �e��E���
			ret.append("  ,T310.yoryo AS yoryo ");								//12 �e��
			ret.append("  ,T310.su_iri AS su_iri ");							//13 ����
			ret.append("  ,T310.cd_nisugata AS nm_nisugata ");					//14 �׎p
			ret.append("  ,M3023.nm_literal AS nm_ondo ");						//15 �戵���x
			// CHG 2019/09/17 BRC.ida start
			//ret.append("  ,T110.shomikikan AS shomikikan ");					//16 �ܖ�����
			ret.append(" ,CASE WHEN T110.shomikikan IS NULL THEN NULL ");
			ret.append("  ELSE T110.shomikikan + M302_TNI_SYUMI.nm_literal END AS shomikikan ");//16 �ܖ�����
			// CHG 2019/09/17 BRC.ida start			
			ret.append("  ,T312.memo_eigyo AS memo_eigyo ");					//17 �������Z����(�c�Ɨp)

			//�X�e�[�^�X�ύX�������A�������A�c�ƁA���Y�Ǘ����̂��̂��̍ŏI�X�V�S���Җ����擾
			ret.append("  ,M101_nm_kenkyu.nm_user AS nm_kenkyu ");				//18 �������@�ŏI�A�N�Z�X�S���Җ�
			ret.append("  ,M101_nm_eigyo.nm_user AS nm_eigyo ");				//19 �c�Ɓ@�ŏI�A�N�Z�X�S���Җ�
			ret.append("  ,M101_nm_seisan.nm_user AS nm_seisan ");				//20 ���Y�Ǘ����@�ŏI�A�N�Z�X�S���Җ�
			ret.append("  ,M101_nm_kojo.nm_user AS nm_kojo ");					//21 �H��@�ŏI�A�N�Z�X�S���Җ�

			ret.append("  ,T131.nm_sample AS nm_sample ");						//22 �T���v��No
			// �y��{���Ftr_shisan_kihonjoho�z
			ret.append("  ,T313_kihon_sub.genka AS kibogenka ");				//23 ��]����
			ret.append("  ,T313_kihon_sub.baika AS kibobaika ");				//24 ��]����
			ret.append("  ,M302_TNI_G.nm_literal AS cd_genka_tani ");			//25 �����P��

			ret.append("  ,T313_kihon_sub.kikan_hanbai_suti AS kikan_hanbai_suti ");//26 �̔�����
			ret.append("  ,M302_Hanbai_T.nm_literal AS kikan_hanbai_sen ");			//27 �̔����ԑI��(�ʔNor�X�|�b�g)
			ret.append("  ,M302_Hanbai_K.nm_literal AS kikan_hanbai_tani ");		//28 �̔����ԒP��

			ret.append("  ,T313_kihon_sub.buturyo_suti AS buturyo_s ");			//29 �z�蕨��
			ret.append("  ,M302_Buturyo_U.nm_literal AS buturyo_u ");			//30 �z�蕨��
			ret.append("  ,M302_Buturyo_K.nm_literal AS buturyo_k ");			//31 �z�蕨��
			ret.append("  ,T313_kihon_sub.buturyo AS buturyo_biko ");			//32 �z�蕨�ʔ��l
			ret.append("  ,T313_kihon_sub.dt_hatubai AS dt_hatubai ");			//33 ��������

			ret.append("  ,T313_kihon_sub.uriage_k AS uriage_k ");				//34 �v�攄��^�N
			ret.append("  ,T313_kihon_sub.rieki_k AS rieki_k ");				//35 �v�旘�v�^�N
			ret.append("  ,T313_kihon_sub.uriage_h AS uriage_h ");				//36
			ret.append("  ,T313_kihon_sub.rieki_h AS rieki_h ");				//37
			ret.append("  ,T313_kihon_sub.lot AS lot ");						//38 �������b�g

			ret.append("  ,M302_Eda_S.nm_��iteral AS nm_shurui ");				//39 �}�Ԏ��
			ret.append("  ,T131.seq_shisaku AS seq_shisaku ");					//40 ����SEQ
			ret.append("  ,T131.no_shisan AS no_shisan ");						//41
			ret.append("  ,T331.fg_koumokuchk ");								//42 �Œ荀�ڃ`�F�b�N
			ret.append("  ,T331.fg_chusi ");									//43 ���Z���~�t���O
			ret.append("  ,CONVERT(VARCHAR,T331.dt_shisan,111) dt_shisan ");	//44 ���Z��

			// �y���Z���ځFtr_shisan_shisaku_kotei�z
			ret.append("  ,T332.cs_genka ");									//45 �����v�i�~�j/�P�[�X
			ret.append("  ,T332.ko_genka ");									//46 �����v�i�~�j/��
			ret.append("  ,T332.kg_genka ");									//47 �����v�i�~�j/�s
			ret.append("  ,T332.baika ");										//48 ����
			ret.append("  ,T332.arari ");										//49 �e���i���j

			ret.append("  ,T131.sort_shisaku ");								//50 �\�[�g
			ret.append("  ,T310.saiyo_sample ");								//51 �̗p�T���v��(seq_shisaku)
			//���� --- �̗p�T���v��
			ret.append("  ,CASE WHEN T310.saiyo_sample < 0  ");
			ret.append("   THEN  '' ");
			ret.append("   ELSE ( ");
			ret.append("    SELECT nm_sample  ");
			ret.append("     FROM tr_shisaku  ");
			ret.append("    WHERE cd_shain = " + strCd_shain);
			ret.append("    AND nen = " + strNen );
			ret.append("    AND no_oi = " + strNo_oi );
			ret.append("    AND seq_shisaku = T310.saiyo_sample )  ");
			ret.append("   END AS nm_saiyo ");									//52 �̗p�T���v��

			//����i�iT110�j
			ret.append(" FROM tr_shisakuhin T110 ");

			//�T���v��No�iT131�j seq_shisaku
			ret.append(" INNER JOIN tr_shisaku T131 ");
			ret.append("  ON T110.cd_shain = T131.cd_shain ");
			ret.append("  AND T110.nen = T131.nen ");
			ret.append("  AND T110.no_oi = T131.no_oi ");
			//�戵���x���擾
			ret.append(" LEFT JOIN ma_literal M3023 ");
			ret.append("  ON M3023.cd_category = 'K_toriatukaiondo' ");
			ret.append("  AND M3023.cd_literal = T110.cd_ondo ");
			//�������F����˗�
			ret.append(" LEFT JOIN ma_user AS M101 ");
			ret.append("  ON id_user =" + strCd_shain);

			//���Z�@����i�iT310�j--- �׎p�E�e�ʁE���� �iseq_shisaku �Ȃ��j
			ret.append(" INNER JOIN tr_shisan_shisakuhin T310 ");
			ret.append("  ON T110.cd_shain = T310.cd_shain ");
			ret.append("  AND T110.nen = T310.nen ");
			ret.append("  AND T110.no_oi = T310.no_oi ");

			//���Z�@��{���iT313_kihon_sub�j
			ret.append(" LEFT JOIN tr_shisan_kihonjoho T313_kihon_sub ");
			ret.append("  ON T310.cd_shain = T313_kihon_sub.cd_shain ");
			ret.append("  AND T310.nen = T313_kihon_sub.nen ");
			ret.append("  AND T310.no_oi = T313_kihon_sub.no_oi ");
			ret.append("  AND T310.no_eda = T313_kihon_sub.no_eda ");
			ret.append("  AND T131.seq_shisaku = T313_kihon_sub.seq_shisaku ");

			//���Z�@����iT331�j
			ret.append(" LEFT JOIN tr_shisan_shisaku T331 ");
			ret.append("  ON T310.cd_shain = T331.cd_shain ");
			ret.append("  AND T310.nen = T331.nen ");
			ret.append("  AND T310.no_oi = T331.no_oi ");
			ret.append("  AND T310.no_eda = T331.no_eda ");
			ret.append("  AND T131.seq_shisaku = T331.seq_shisaku ");

			//���Z�@����iT332�j �Œ�`�F�b�N
			ret.append(" LEFT JOIN tr_shisan_shisaku_kotei T332 ");
			ret.append("  ON T310.cd_shain = T332.cd_shain ");
			ret.append("  AND T310.nen = T332.nen ");
			ret.append("  AND T310.no_oi = T332.no_oi ");
			ret.append("  AND T310.no_eda = T332.no_eda ");
			ret.append("  AND T131.seq_shisaku = T332.seq_shisaku ");

			//�S���c�ƎҖ�
			ret.append(" LEFT JOIN ma_user AS M110 ");
			ret.append("  ON T110.cd_eigyo = M110.id_user ");

			//������ЁE������
			ret.append(" LEFT JOIN ma_busho AS M104 ");
			ret.append("  ON T310.cd_kaisha = M104.cd_kaisha ");
			ret.append("  AND T310.cd_kojo = M104.cd_busho ");

			//�̐Ӊ�Ж�
			ret.append(" LEFT JOIN (  ");
			ret.append("  SELECT DISTINCT cd_kaisha  ");
			ret.append("   , nm_kaisha  ");
			ret.append("  FROM ma_busho ) AS M104_hanseki ");
			ret.append("  ON T310.cd_hanseki = M104_hanseki.cd_kaisha ");

			//�������Z�����i�c�ƘA���p�j
			ret.append(" LEFT JOIN tr_shisan_memo_eigyo AS T312 ");
			ret.append("  ON T310.cd_shain = T312.cd_shain ");
			ret.append("  AND T310.nen = T312.nen ");
			ret.append("  AND T310.no_oi = T312.no_oi ");

			//�̔�����
			ret.append("  LEFT JOIN ma_literal AS M302_Hanbai_T ON ");
			ret.append("   'hanbai_kikan_sentaku' = M302_Hanbai_T.cd_category ");
			ret.append("   AND T313_kihon_sub.kikan_hanbai_sen = M302_Hanbai_T.cd_literal ");
			ret.append("  LEFT JOIN ma_literal AS M302_Hanbai_K ON ");
			ret.append("   'hanbai_kikan_tani' = M302_Hanbai_K.cd_category ");
			ret.append("   AND T313_kihon_sub.kikan_hanbai_tani = M302_Hanbai_K.cd_literal ");

			//�����P��
			ret.append("  LEFT JOIN ma_literal AS M302_TNI_G ON ");
			ret.append("   'K_tani_genka' = M302_TNI_G.cd_category ");
			ret.append("   AND T313_kihon_sub.cd_genka_tani = M302_TNI_G.cd_literal ");

			// ADD 2019/09/17 BRC.ida start
			//�ܖ����ԒP��
			ret.append("  LEFT JOIN ma_literal AS M302_TNI_SYUMI ON ");
			ret.append("   M302_TNI_SYUMI.cd_category = '23' ");
			ret.append("   AND T110.shomikikan_tani = M302_TNI_SYUMI.cd_literal ");
			// ADD 2019/09/17 BRC.ida end

			// �z�蕨��(���i�P��)�A�z�蕨��(���ԒP��)
			ret.append("  LEFT JOIN ma_literal AS M302_Buturyo_U ON ");
			ret.append("   'sotei_buturyo_seihin' = M302_Buturyo_U.cd_category ");
			ret.append("   AND T313_kihon_sub.buturyo_seihin = M302_Buturyo_U.cd_literal ");
			ret.append("  LEFT JOIN ma_literal AS M302_Buturyo_K ON ");
			ret.append("   'sotei_buturyo_kikan' = M302_Buturyo_K.cd_category ");
			ret.append("   AND T313_kihon_sub.buturyo_kikan = M302_Buturyo_K.cd_literal ");

			//�}�Ԏ��
			ret.append("  LEFT JOIN ma_literal AS M302_Eda_S ON ");
			ret.append("   'shurui_eda' = M302_Eda_S.cd_category ");
			ret.append("   AND T310.shurui_eda = M302_Eda_S.cd_literal ");

			//�X�e�[�^�X�ύX�������A�������A�c�ƁA���Y�Ǘ����A�H��̂��̂��̍ŏI�X�V�S���Җ����擾
			ret.append(" LEFT JOIN ( ");
			ret.append(" SELECT ");
			ret.append("  TOP(1) T442.dt_henkou AS dt_henkou ");
			ret.append("  ,M101.nm_user AS nm_user ");
			ret.append("  ,T442.cd_shain AS cd_shain ");
			ret.append("  ,T442.nen AS nen ");
			ret.append("  ,T442.no_oi AS no_oi ");
			ret.append("  ,T442.no_eda AS no_eda ");
			ret.append(" FROM ");
			ret.append("  tr_shisan_status_rireki AS T442 ");
			ret.append("  INNER JOIN ma_user AS M101 ON T442.id_henkou = M101.id_user ");
			ret.append(" WHERE ");
			ret.append("  T442.cd_zikko_ca = 'wk_kenkyu' ");
			ret.append("  AND T442.cd_shain = " + strCd_shain);
			ret.append("  AND T442.nen = " + strNen);
			ret.append("  AND T442.no_oi = " + strNo_oi);
			ret.append("  AND T442.no_eda = " + strNo_eda);
			ret.append(" ORDER BY T442.dt_henkou DESC ");
			ret.append(" ) AS M101_nm_kenkyu ON ");
			ret.append("  T310.cd_shain = M101_nm_kenkyu.cd_shain ");
			ret.append("  AND T310.nen = M101_nm_kenkyu.nen ");
			ret.append("  AND T310.no_oi = M101_nm_kenkyu.no_oi ");
			ret.append("  AND T310.no_eda = M101_nm_kenkyu.no_eda ");

			ret.append(" LEFT JOIN ( ");
			ret.append(" SELECT ");
			ret.append("  TOP(1) T442.dt_henkou AS dt_henkou ");
			ret.append("  ,M101.nm_user AS nm_user ");
			ret.append("  ,T442.cd_shain AS cd_shain ");
			ret.append("  ,T442.nen AS nen ");
			ret.append("  ,T442.no_oi AS no_oi ");
			ret.append("  ,T442.no_eda AS no_eda ");
			ret.append(" FROM ");
			ret.append("  tr_shisan_status_rireki AS T442 ");
			ret.append("  INNER JOIN ma_user AS M101 ON T442.id_henkou = M101.id_user ");
			ret.append(" WHERE ");
			ret.append("  T442.cd_zikko_ca = 'wk_eigyo' ");
			ret.append("  AND T442.cd_shain = " + strCd_shain);
			ret.append("  AND T442.nen = " + strNen);
			ret.append("  AND T442.no_oi = " + strNo_oi);
			ret.append("  AND T442.no_eda = " + strNo_eda);
			ret.append(" ORDER BY T442.dt_henkou DESC ");
			ret.append(" ) AS M101_nm_eigyo ON ");
			ret.append("  T310.cd_shain = M101_nm_eigyo.cd_shain ");
			ret.append("  AND T310.nen = M101_nm_eigyo.nen ");
			ret.append("  AND T310.no_oi = M101_nm_eigyo.no_oi ");
			ret.append("  AND T310.no_eda = M101_nm_eigyo.no_eda ");

			ret.append(" LEFT JOIN ( ");
			ret.append(" SELECT ");
			ret.append("  TOP(1) T442.dt_henkou AS dt_henkou ");
			ret.append("  ,M101.nm_user AS nm_user ");
			ret.append("  ,T442.cd_shain AS cd_shain ");
			ret.append("  ,T442.nen AS nen ");
			ret.append("  ,T442.no_oi AS no_oi ");
			ret.append("  ,T442.no_eda AS no_eda ");
			ret.append(" FROM ");
			ret.append("  tr_shisan_status_rireki AS T442 ");
			ret.append("  INNER JOIN ma_user AS M101 ON T442.id_henkou = M101.id_user ");
			ret.append(" WHERE ");
			ret.append("  T442.cd_zikko_ca = 'wk_seisan' ");
			ret.append("  AND T442.cd_shain = " + strCd_shain);
			ret.append("  AND T442.nen = " + strNen);
			ret.append("  AND T442.no_oi = " + strNo_oi);
			ret.append("  AND T442.no_eda = " + strNo_eda);
			ret.append(" ORDER BY T442.dt_henkou DESC ");
			ret.append(" ) AS M101_nm_seisan ON ");
			ret.append("  T310.cd_shain = M101_nm_seisan.cd_shain ");
			ret.append("  AND T310.nen = M101_nm_seisan.nen ");
			ret.append("  AND T310.no_oi = M101_nm_seisan.no_oi ");
			ret.append("  AND T310.no_eda = M101_nm_seisan.no_eda ");

			ret.append(" LEFT JOIN ( ");
			ret.append(" SELECT ");
			ret.append("  TOP(1) T442.dt_henkou AS dt_henkou ");
			ret.append("  ,M101.nm_user AS nm_user ");
			ret.append("  ,T442.cd_shain AS cd_shain ");
			ret.append("  ,T442.nen AS nen ");
			ret.append("  ,T442.no_oi AS no_oi ");
			ret.append("  ,T442.no_eda AS no_eda ");
			ret.append(" FROM ");
			ret.append("  tr_shisan_status_rireki AS T442 ");
			ret.append("  INNER JOIN ma_user AS M101 ON T442.id_henkou = M101.id_user ");
			ret.append(" WHERE ");
			ret.append("  T442.cd_zikko_ca = 'wk_kojo' ");
			ret.append("  AND T442.cd_shain = " + strCd_shain);
			ret.append("  AND T442.nen = " + strNen);
			ret.append("  AND T442.no_oi = " + strNo_oi);
			ret.append("  AND T442.no_eda = " + strNo_eda);
			ret.append(" ORDER BY T442.dt_henkou DESC ");
			ret.append(" ) AS M101_nm_kojo ON ");
			ret.append("  T310.cd_shain = M101_nm_kojo.cd_shain ");
			ret.append("  AND T310.nen = M101_nm_kojo.nen ");
			ret.append("  AND T310.no_oi = M101_nm_kojo.no_oi ");
			ret.append("  AND T310.no_eda = M101_nm_kojo.no_eda ");


			//��������
			//�i�荞�݁i���Z�@����i�j
			ret.append(" WHERE ");
			ret.append("  T310.cd_shain = " + strCd_shain);
			ret.append("  AND T310.nen = " + strNen);
			ret.append("  AND T310.no_oi = " + strNo_oi);
			ret.append("  AND T310.no_eda = " + strNo_eda);

			//����SEQ
			ret.append("  AND T131.seq_shisaku IN ( ");
			for ( int i=0; i<reqCnt; i++ ) {
				//����SEQ�̎擾
				try{
					strShisakuSeq = toString(reqData.getFieldVale(0, i, "seq_shisaku"));
					if ( i != 0 ) {
						ret.append(" , ");
					}
					ret.append(strShisakuSeq);

				}catch(Exception e){
					strShisakuSeq = "";
				}
			}
			ret.append(" ) ");

			ret.append("  ORDER BY ");
			ret.append("   T131.sort_shisaku ");

		} catch (Exception e) {
			this.em.ThrowException(e, "�������Z�\�i�c�Ɓj�A����SQL�̐����Ɏ��s���܂����B");

		} finally {

		}
		return ret;

	}

}