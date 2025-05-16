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
import jp.co.blueflag.shisaquick.srv.logic.LiteralDataSearchLogic;

/**
 * �������Z�\�i�������Z�p�j�𐶐�����
 * @author isono
 * @since  2009/11/06
 */
public class FGEN0050_Logic extends LogicBaseJExcel {

	/**
	 * �R���X�g���N�^
	 */
	public FGEN0050_Logic() {
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
		List<?> lstRecset_shizai = null;
		//�G�N�Z���t�@�C���p�X
		String DownLoadPath = "";

		try {
			//DB����
			super.createSearchDB();
			lstRecset = getData(reqData);
			lstRecset_shizai = getData_shizai(reqData);

			// 20160524  KPX@1600766 ADD start
			// ���[�U�̕����F�������̎��A������Ђ̒P���J�������擾
			int flgTanka = getTankaHyouji_kengen(reqData, userInfoData);
			// 20160524  KPX@1600766 ADD end

			//�H���l
			String strKoteiValue = getSyurui(reqData);

			// 20160524  KPX@1600766 MOD start
			//�P���J�������ɂ��P���E�������i��\�����ځj��0�ɕύX
			//�H���l�ɂ��A�u�������p�^�[���v���u���̑��������ȊO�p�^�[���v���𔻒肷��
			if ( strKoteiValue.equals("1") ) {
				//������Excel�t�@�C������
//				DownLoadPath = makeExcelFile1(lstRecset, lstRecset_shizai, reqData);
				DownLoadPath = makeExcelFile1(lstRecset, lstRecset_shizai, reqData, flgTanka);

			} else if ( strKoteiValue.equals("2") ) {
				//���̑�Excel�t�@�C������
//				DownLoadPath = makeExcelFile2(lstRecset, lstRecset_shizai, reqData);
				DownLoadPath = makeExcelFile2(lstRecset, lstRecset_shizai, reqData, flgTanka);

			}
			// 20160524  KPX@1600766 MOD end

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
			ret.setID("FGEN0050");

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
	// 20160524  KPX@1600766 MOD start
	/**
	 * ������_�������Z�\(EXCEL)�𐶐�����
	 * @param lstRecset : �����f�[�^���X�g
	 * @param lstRecset_Shizai : ���ރf�[�^���X�g
	 * @param flgTanka: �P���J������ �i9�F�P���J���i�S�āj 1�F�P���J������   0�F�P���J���s�j
	 * @return�@String : �_�E�����[�h�p�̃p�X
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private String makeExcelFile1(

			List<?> lstRecset
			, List<?> lstRecset_Shizai
			, RequestResponsKindBean reqData
			, int flgTanka
	// 20160524  KPX@1600766 MOD end
			)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		String ret = "";
		String strShisakuSeq1 = "";
		String strShisakuSeq2 = "";
		String strShisakuSeq3 = "";
		int intChuijiko = 0;

		//�yQP@00342�z
		String strSuiZyuten1 = "";
		String strSuiZyuten2 = "";
		String strSuiZyuten3 = "";
		String strYuZyuten1 = "";
		String strYuZyuten2 = "";
		String strYuZyuten3 = "";

		try {
			//EXCEL�e���v���[�g��ǂݍ���
			super.ExcelReadTemplate("�������������Z�\");

			// ADD 2015/06/17 E.kitazawa�yQP@40812�z  --- start
			//�I���T���v�������擾�i�e�[�u��Bean�̌����j
			int reqCnt = ((RequestResponsTableBean)reqData.GetItem(0)).GetCnt();
			// ADD 2015/06/17 E.kitazawa�yQP@40812�z  --- end

			//����SEQ���擾
			try{
				strShisakuSeq1 = toString( reqData.getFieldVale(0, 0, "seq_shisaku"));

				//�yQP@00342�z
				strSuiZyuten1 = toString( reqData.getFieldVale(0, 0, "zyuten_sui"));
				strYuZyuten1 = toString( reqData.getFieldVale(0, 0, "zyuten_yu"));

			}catch(Exception e){

			}
			try{
				// MOD 2015/06/17 E.kitazawa�yQP@40812�z  --- start
				// ���N�G�X�g�f�[�^�����݂���Ƃ��̂ݒl���擾����
				if (reqCnt > 1) {
					strShisakuSeq2 = toString( reqData.getFieldVale(0, 1, "seq_shisaku"));

					//�yQP@00342�z
					strSuiZyuten2 = toString( reqData.getFieldVale(0, 1, "zyuten_sui"));
					strYuZyuten2 = toString( reqData.getFieldVale(0, 1, "zyuten_yu"));

				}

			}catch(Exception e){

			}
			try{
				if (reqCnt > 2) {
					strShisakuSeq3 = toString( reqData.getFieldVale(0, 2, "seq_shisaku"));

					//�yQP@00342�z
					strSuiZyuten3 = toString( reqData.getFieldVale(0, 2, "zyuten_sui"));
					strYuZyuten3 = toString( reqData.getFieldVale(0, 2, "zyuten_yu"));
				}
				// MOD 2015/06/17 E.kitazawa�yQP@40812�z  --- end

			}catch(Exception e){

			}
			//�_�E�����[�h�p��EXCEL�𐶐�����
			for (int i = 0; i < lstRecset.size(); i++) {


				//�������ʂ�1�s�������o��
				Object[] items = (Object[]) lstRecset.get(i);

				// 20160524  KPX@1600766 ADD start
				//�P���J�������ɂ�� 0�ݒ�
				items = setObj(items, flgTanka);
				// 20160524  KPX@1600766 ADD end


				//ADD 2012.4.30�@�yH24�N�x�Ή��zJEXCEL�Ή��̈גǉ�
				if(i==0){
					//EXCEL�e���v���[�g��ǂݍ���
					ret = super.ExcelOutput_genka(
							makeShisakuNo(
							reqData.getFieldVale(0, 0, "cd_shain")
							,reqData.getFieldVale(0, 0, "nen")
							,reqData.getFieldVale(0, 0, "no_oi")
							),
							this.replaceSymbol(toString(items[2])));
				}

				try{
					//Excel�ɒl���Z�b�g����

					//�i��
					super.ExcelSetValue("�i��", toString(items[2]));
					//�˗��ԍ�
					super.ExcelSetValue("�˗��ԍ�", toString(items[3]));
					//���t
					super.ExcelSetValue("���t", toString(items[4]));
					//�H��R�[�h	�i���Y�{���j5
					//super.ExcelSetValue("�H��R�[�h", toDouble(items[5]));		//DEL 2012.4.30�@�yH24�N�x�Ή��z�R�[�h�o�͂��疼�̏o�͂ɕύX
					//ADD&UPD 2012.4.30�@�yH24�N�x�Ή��z��������ŏI�A�N�Z�X�Ђ�S���҂Ƃ��ĕҏW�B�������A�c�ƁA���Y�Ǘ���
					//������
					super.ExcelSetValue("������", toString(items[50]));
					//�c��
					super.ExcelSetValue("�c��", toString(items[51]));
					//���Y�Ǘ���
					super.ExcelSetValue("���Y�Ǘ���", toString(items[52]));

					//ADD 2012.4.30�@�yH24�N�x�Ή��z�������Z�����A�������Z����(�c�Ɨp)
					super.ExcelSetValue("�������Z����", toString(items[44]));
					super.ExcelSetValue("�������Z����(�c�Ɨp)", toString(items[45]));

					//����SEQ���Ƃɕ���
					if ( toString(items[38]).equals(strShisakuSeq1) ){
						//�T���v��No1
						super.ExcelSetValue("�T���v��No1", toString(items[8]));

						//ADD 2012.4.30�@�yH24�N�x�Ή��z���i��񗓂ɍH����1�ҏW
						super.ExcelSetValue("���i_�T���v��No1","�T���v��:" + toString(items[8]));
						if ( !toString(items[39]).isEmpty() ) {
							super.ExcelSetValue("���i_�H����No1","�H����:" + toString(items[39]));
							super.ExcelSetValue("���i_���ӎ���1",toString(items[33]));
						}

					} else if ( toString(items[38]).equals(strShisakuSeq2) ){
						//�T���v��No2
						super.ExcelSetValue("�T���v��No2", toString(items[8]));

						//ADD 2012.4.30�@�yH24�N�x�Ή��z���i��񗓂ɍH����2�ҏW
						super.ExcelSetValue("���i_�T���v��No2","�T���v��:" + toString(items[8]));
						if ( !toString(items[39]).isEmpty() ) {
							super.ExcelSetValue("���i_�H����No2","�H����:" + toString(items[39]));
							super.ExcelSetValue("���i_���ӎ���2",toString(items[33]));
						}

					} else if ( toString(items[38]).equals(strShisakuSeq3) ){
						//�T���v��No3
						super.ExcelSetValue("�T���v��No3", toString(items[8]));

						//ADD 2012.4.30�@�yH24�N�x�Ή��z���i��񗓂ɍH����3�ҏW
						super.ExcelSetValue("���i_�T���v��No3","�T���v��:" + toString(items[8]));
						if ( !toString(items[39]).isEmpty() ) {
							super.ExcelSetValue("���i_�H����No3","�H����:" + toString(items[39]));
							super.ExcelSetValue("���i_���ӎ���3",toString(items[33]));
						}

					}

					//�H�����u�E�ے����t�v�̏ꍇ
					if (toString(items[1]).equals("1")) {
						//***** [�E�ے����t] *****

						if ( toString(items[38]).equals(strShisakuSeq1) ) {
							//�V�K�����i���Y�{���j9
							super.ExcelSetValue("�H��1_�V�K����", toString(items[9]));
							//�i�R�[�h
							super.ExcelSetValue("�H��1_�i�R�[�h", toString(items[10]));
							//�i���i���Y�{���j11
							super.ExcelSetValue("�H��1_�i��", toString(items[11]));
							//�P���i���Y�{���j12
							super.ExcelSetValue("�H��1_�P��", toDouble(items[12]));
							//�����i���Y�{���j13
							super.ExcelSetValue("�H��1_����", (toDouble(items[13]) / 100));

						}

						//����SEQ���Ƃɕ���
						if ( toString(items[38]).equals(strShisakuSeq1) ){
							//�z��1
							super.ExcelSetValue("�H��1_�z��1", toDouble(items[14]));

						} else if ( toString(items[38]).equals(strShisakuSeq2) ){
							//�z��2
							super.ExcelSetValue("�H��1_�z��2", toDouble(items[14]));

						} else if ( toString(items[38]).equals(strShisakuSeq3) ){
							//�z��3
							super.ExcelSetValue("�H��1_�z��3", toDouble(items[14]));

						}

					//�H�����u�����v�̏ꍇ
					} else if (toString(items[1]).equals("2")) {
						//***** [����] *****

						if ( toString(items[38]).equals(strShisakuSeq1) ){
							//�V�K�����i���Y�{���j
							super.ExcelSetValue("�H��2_�V�K����", toString(items[9]));
							//�i�R�[�h
							super.ExcelSetValue("�H��2_�i�R�[�h", toString(items[10]));
							//�i���i���Y�{���j
							super.ExcelSetValue("�H��2_�i��", toString(items[11]));
							//�P���i���Y�{���j
							super.ExcelSetValue("�H��2_�P��", toDouble(items[12]));
							//�����i���Y�{���j
							super.ExcelSetValue("�H��2_����", (toDouble(items[13]) / 100));

						}

						//����SEQ���Ƃɕ���
						if ( toString(items[38]).equals(strShisakuSeq1) ){
							//�z��1
							super.ExcelSetValue("�H��2_�z��1", toDouble(items[14]));

						} else if ( toString(items[38]).equals(strShisakuSeq2) ){
							//�z��2
							super.ExcelSetValue("�H��2_�z��2", toDouble(items[14]));

						} else if ( toString(items[38]).equals(strShisakuSeq3) ){
							//�z��3
							super.ExcelSetValue("�H��2_�z��3", toDouble(items[14]));

						}

					//�H�����u�����v�̏ꍇ
					} else if (toString(items[1]).equals("3")) {
						//***** [����] *****

						if ( toString(items[38]).equals(strShisakuSeq1) ){
							//�V�K�����i���Y�{���j
							super.ExcelSetValue("�H��3_�V�K����", toString(items[9]));
							//�i�R�[�h
							super.ExcelSetValue("�H��3_�i�R�[�h", toString(items[10]));
							//�i���i���Y�{���j
							super.ExcelSetValue("�H��3_�i��", toString(items[11]));
							//�P���i���Y�{���j
							super.ExcelSetValue("�H��3_�P��", toDouble(items[12]));
							//�����i���Y�{���j
							super.ExcelSetValue("�H��3_����", (toDouble(items[13]) / 100));

						}

						//����SEQ���Ƃɕ���
						if ( toString(items[38]).equals(strShisakuSeq1) ){
							//�z��1
							super.ExcelSetValue("�H��3_�z��1", toDouble(items[14]));

						} else if ( toString(items[38]).equals(strShisakuSeq2) ){
							//�z��2
							super.ExcelSetValue("�H��3_�z��2", toDouble(items[14]));

						} else if ( toString(items[38]).equals(strShisakuSeq3) ){
							//�z��3
							super.ExcelSetValue("�H��3_�z��3", toDouble(items[14]));

						}

					}

					//�������
					if ( toString(items[38]).equals(strShisakuSeq1) ){
						//�[�U�ʐ���1
						super.ExcelSetValue("����1", toDouble(strSuiZyuten1));
						//�[�U�ʖ���1
						super.ExcelSetValue("����1", toDouble(strYuZyuten1));
						//��d1
						super.ExcelSetValue("��d1", toDouble(items[17]));

					} else if ( toString(items[38]).equals(strShisakuSeq2) ){
						//�[�U�ʐ���2
						super.ExcelSetValue("����2", toDouble(strSuiZyuten2));
						//�[�U�ʖ���2
						super.ExcelSetValue("����2", toDouble(strYuZyuten2));
						//��d2
						super.ExcelSetValue("��d2", toDouble(items[17]));

					} else if ( toString(items[38]).equals(strShisakuSeq3) ){
						//�[�U�ʐ���3
						super.ExcelSetValue("����3", toDouble(strSuiZyuten3));
						//�[�U�ʖ���3
						super.ExcelSetValue("����3", toDouble(strYuZyuten3));
						//��d3
						super.ExcelSetValue("��d3", toDouble(items[17]));

					}

					//ADD 2012.4.30�@�yH24�N�x�Ή��z�H�ꖼ(������)�ǉ�
					super.ExcelSetValue("�H��", toString(items[43]));
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
					//�׎p�i���Y�{���j23
					super.ExcelSetValue("�׎p", toString(items[23]));
					//���e��
					super.ExcelSetValue("���e��", toString(items[24]));
					//���萔�i���Y�{���j25
					super.ExcelSetValue("���萔", toString(items[25]));
					//�ܖ�����
					super.ExcelSetValue("�ܖ�����", toString(items[26]));
					// MOD 2013/11/14 okano�yQP@30154�zstart
//						//�������b�g
//						super.ExcelSetValue("�������b�g", toString(items[53]));
//						//���������i���Y�{���j27
//						super.ExcelSetValue("��������", toString(items[27]));
//						//�z�蕨�ʁi���Y�{���j28
//						// MOD 2013/9/6 okano�yQP@30151�zNo.30 start
//	//						super.ExcelSetValue("�z�蕨��", toString(items[28]));
//						super.ExcelSetValue("�z�蕨��", toDouble(items[28]));
//						super.ExcelSetValue("�z�蕨�ʒP��", toString(items[29])+toString(items[30]));
//						// MOD 2013/9/6 okano�yQP@30151�zNo.30 end
//						//�����i���Y�{���j29
//						super.ExcelSetValue("����", toString(items[31]));
//						//��]�����i���Y�{���j30
//						super.ExcelSetValue("��]����", toString(items[32]));
//
//
//						//ADD 2012.4.30�@�yH24�N�x�Ή��z�̔�����  �̔����ԑI��(�ʔNor�X�|�b�g)+�̔�����+�̔����ԒP��
//						super.ExcelSetValue("�̔�����", toString(items[47]) + toString(items[46]) + toString(items[48]));
//						//ADD 2012.4.30�@�yH24�N�x�Ή��z�����P�ʂ����ꂼ�ꌴ���A���������ɐݒ�B
//						super.ExcelSetValue("�����P��", toString(items[49]));
//						super.ExcelSetValue("�����P��", toString(items[49]));
					//�ŏI���Z�p
					if ( toString(items[38]).equals(strShisakuSeq1) ){
						//�������b�g
						super.ExcelSetValue("�������b�g1", toString(items[53]));
						//���������i���Y�{���j27
						super.ExcelSetValue("��������1", toString(items[27]));
						//�z�蕨�ʁi���Y�{���j28
						super.ExcelSetValue("�z�蕨��1", toDouble(items[28]));
						super.ExcelSetValue("�z�蕨�ʒP��1", toString(items[29])+toString(items[30]));
						//�����i���Y�{���j29
						super.ExcelSetValue("����1", toString(items[31]));
						//��]�����i���Y�{���j30
						super.ExcelSetValue("��]����1", toString(items[32]));
						//�̔�����  �̔����ԑI��(�ʔNor�X�|�b�g)+�̔�����+�̔����ԒP��
						super.ExcelSetValue("�̔�����1", toString(items[47]) + toString(items[46]) + toString(items[48]));
						//�����P�ʂ����ꂼ�ꌴ���A���������ɐݒ�B
						super.ExcelSetValue("�����P��1", toString(items[49]));
						super.ExcelSetValue("�����P��1", toString(items[49]));

						//ADD 2014/04/04 shima �yQP@30154�zstart
						super.ExcelSetValue("���Z��1", toString(items[71]));
						//ADD 2014/04/04 shima �yQP@30154�zend

					} else if ( toString(items[38]).equals(strShisakuSeq2) ){
						//�������b�g
						super.ExcelSetValue("�������b�g2", toString(items[53]));
						//���������i���Y�{���j27
						super.ExcelSetValue("��������2", toString(items[27]));
						//�z�蕨�ʁi���Y�{���j28
						super.ExcelSetValue("�z�蕨��2", toDouble(items[28]));
						super.ExcelSetValue("�z�蕨�ʒP��2", toString(items[29])+toString(items[30]));
						//�����i���Y�{���j29
						super.ExcelSetValue("����2", toString(items[31]));
						//��]�����i���Y�{���j30
						super.ExcelSetValue("��]����2", toString(items[32]));
						//�̔�����  �̔����ԑI��(�ʔNor�X�|�b�g)+�̔�����+�̔����ԒP��
						super.ExcelSetValue("�̔�����2", toString(items[47]) + toString(items[46]) + toString(items[48]));
						//�����P�ʂ����ꂼ�ꌴ���A���������ɐݒ�B
						super.ExcelSetValue("�����P��2", toString(items[49]));
						super.ExcelSetValue("�����P��2", toString(items[49]));

						//ADD 2014/04/04 shima �yQP@30154�zstart
						super.ExcelSetValue("���Z��2", toString(items[71]));
						//ADD 2014/04/04 shima �yQP@30154�zend

					} else if ( toString(items[38]).equals(strShisakuSeq3) ){
						//�������b�g
						super.ExcelSetValue("�������b�g3", toString(items[53]));
						//���������i���Y�{���j27
						super.ExcelSetValue("��������3", toString(items[27]));
						//�z�蕨�ʁi���Y�{���j28
						super.ExcelSetValue("�z�蕨��3", toDouble(items[28]));
						super.ExcelSetValue("�z�蕨�ʒP��3", toString(items[29])+toString(items[30]));
						//�����i���Y�{���j29
						super.ExcelSetValue("����3", toString(items[31]));
						//��]�����i���Y�{���j30
						super.ExcelSetValue("��]����3", toString(items[32]));
						//�̔�����  �̔����ԑI��(�ʔNor�X�|�b�g)+�̔�����+�̔����ԒP��
						super.ExcelSetValue("�̔�����3", toString(items[47]) + toString(items[46]) + toString(items[48]));
						//�����P�ʂ����ꂼ�ꌴ���A���������ɐݒ�B
						super.ExcelSetValue("�����P��3", toString(items[49]));
						super.ExcelSetValue("�����P��3", toString(items[49]));

						//ADD 2014/04/04 shima �yQP@30154�zstart
						super.ExcelSetValue("���Z��3", toString(items[71]));
						//ADD 2014/04/04 shima �yQP@30154�zend

					}
					// MOD 2013/11/14 okano�yQP@30154�zend

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

					//�ŏI���Z�p
					if ( toString(items[38]).equals(strShisakuSeq1) ){
//2009/11/27�@isono�@�ۑ�Ǘ��F119
						//���Ϗ[�U��1�i���Y�{���j32
//						super.ExcelSetValue("���Ϗ[�U��1", toDouble(items[32]));
						super.ExcelSetValue("���Ϗ[�U��1", toDouble(items[34])*1000);
						//�L������1�i���Y�{���j33
						super.ExcelSetValue("�L������1", (toDouble(items[35]) / 100));
						//�Œ��1�i���Y�{���j34
						super.ExcelSetValue("�Œ��1", toDouble(items[36]));

						//ADD 2014/01/15 nishigawa �yQP@30154�z�ǉ��ۑ�No.11 start
						//�Œ荀�ڃ`�F�b�N����Ă���T���v����̏ꍇ�́A�c�a�l���v�Z�Z���ɏ㏑��
						if(toString(items[54]).equals("1")){
							super.ExcelSetValue("���x����(��)1", toDouble(items[59])*1000);
							super.ExcelSetValue("��d���Z�ʁi���j1", toDouble(items[60])*1000);
							super.ExcelSetValue("������/�P�[�X1", toDouble(items[61]));
							super.ExcelSetValue("�ޗ���/�P�[�X1", toDouble(items[62]));
							super.ExcelSetValue("�����v/�P�[�X1", toDouble(items[63]));
							super.ExcelSetValue("�����v/��1", toDouble(items[64]));
							super.ExcelSetValue("������/�j�f1", toDouble(items[65]));
							super.ExcelSetValue("�ޗ���/�j�f1", toDouble(items[66]));
							super.ExcelSetValue("�Œ��/�j�f1", toDouble(items[70]));
							super.ExcelSetValue("�����v/�j�f1", toDouble(items[67]));
						//ADD 2014/04/22 start�yQP@30154�z�ǉ�
							super.ExcelSetValue("��]����1", toDouble(items[68]));
							super.ExcelSetValue("�e��(��)1", toDouble(items[69]));
						//ADD 2014/04/22 end �yQP@30154�z�ǉ�
							//ADD 2014/05/05 start �yQP@30154�z�ǉ�
							super.ExcelSetValue("���ڌŒ�`�F�b�N1", "�����ڌŒ�T���v��NO");
							//ADD 2014/05/05 end �yQP@30154�z�ǉ�
						}
						//ADD 2014/01/15 nishigawa �yQP@30154�z�ǉ��ۑ�No.11 end

					} else if ( toString(items[38]).equals(strShisakuSeq2) ){
//2009/11/27�@isono�@�ۑ�Ǘ��F119
						//���Ϗ[�U��2�i���Y�{���j
//						super.ExcelSetValue("���Ϗ[�U��2", toDouble(items[32]));
						super.ExcelSetValue("���Ϗ[�U��2", toDouble(items[34])*1000);
						//�L������2�i���Y�{���j
						super.ExcelSetValue("�L������2", (toDouble(items[35]) / 100));
						//�Œ��2�i���Y�{���j
						super.ExcelSetValue("�Œ��2", toDouble(items[36]));

						//ADD 2014/01/15 nishigawa �yQP@30154�z�ǉ��ۑ�No.11 start
						//�Œ荀�ڃ`�F�b�N����Ă���T���v����̏ꍇ�́A�c�a�l���v�Z�Z���ɏ㏑��
						if(toString(items[54]).equals("1")){
							super.ExcelSetValue("���x����(��)2", toDouble(items[59])*1000);
							super.ExcelSetValue("��d���Z�ʁi���j2", toDouble(items[60])*1000);
							super.ExcelSetValue("������/�P�[�X2", toDouble(items[61]));
							super.ExcelSetValue("�ޗ���/�P�[�X2", toDouble(items[62]));
							super.ExcelSetValue("�����v/�P�[�X2", toDouble(items[63]));
							super.ExcelSetValue("�����v/��2", toDouble(items[64]));
							super.ExcelSetValue("������/�j�f2", toDouble(items[65]));
							super.ExcelSetValue("�ޗ���/�j�f2", toDouble(items[66]));
							super.ExcelSetValue("�Œ��/�j�f2", toDouble(items[70]));
							super.ExcelSetValue("�����v/�j�f2", toDouble(items[67]));
						//ADD 2014/04/22 start�yQP@30154�z�ǉ�
							super.ExcelSetValue("��]����2", toDouble(items[68]));
							super.ExcelSetValue("�e��(��)2", toDouble(items[69]));
						//ADD 2014/04/22 end �yQP@30154�z�ǉ�
							//ADD 2014/05/05 start �yQP@30154�z�ǉ�
							super.ExcelSetValue("���ڌŒ�`�F�b�N2", "�����ڌŒ�T���v��NO");
							//ADD 2014/05/05 end �yQP@30154�z�ǉ�
						}
						//ADD 2014/01/15 nishigawa �yQP@30154�z�ǉ��ۑ�No.11 end

					} else if ( toString(items[38]).equals(strShisakuSeq3) ){
//2009/11/27�@isono�@�ۑ�Ǘ��F119
						//���Ϗ[�U��3�i���Y�{���j
//						super.ExcelSetValue("���Ϗ[�U��3", toDouble(items[32]));
						super.ExcelSetValue("���Ϗ[�U��3", toDouble(items[34])*1000);
						//�L������3�i���Y�{���j
						super.ExcelSetValue("�L������3", (toDouble(items[35]) / 100));
						//�Œ��3�i���Y�{���j
						super.ExcelSetValue("�Œ��3", toDouble(items[36]));

						//ADD 2014/01/15 nishigawa �yQP@30154�z�ǉ��ۑ�No.11 start
						//�Œ荀�ڃ`�F�b�N����Ă���T���v����̏ꍇ�́A�c�a�l���v�Z�Z���ɏ㏑��
						if(toString(items[54]).equals("1")){
							super.ExcelSetValue("���x����(��)3", toDouble(items[59])*1000);
							super.ExcelSetValue("��d���Z�ʁi���j3", toDouble(items[60])*1000);
							super.ExcelSetValue("������/�P�[�X3", toDouble(items[61]));
							super.ExcelSetValue("�ޗ���/�P�[�X3", toDouble(items[62]));
							super.ExcelSetValue("�����v/�P�[�X3", toDouble(items[63]));
							super.ExcelSetValue("�����v/��3", toDouble(items[64]));
							super.ExcelSetValue("������/�j�f3", toDouble(items[65]));
							super.ExcelSetValue("�ޗ���/�j�f3", toDouble(items[66]));
							super.ExcelSetValue("�Œ��/�j�f3", toDouble(items[70]));
							super.ExcelSetValue("�����v/�j�f3", toDouble(items[67]));
						//ADD 2014/04/22 start�yQP@30154�z�ǉ�
							super.ExcelSetValue("��]����3", toDouble(items[68]));
							super.ExcelSetValue("�e��(��)3", toDouble(items[69]));
						//ADD 2014/04/22 end �yQP@30154�z�ǉ�
							//ADD 2014/05/05 start �yQP@30154�z�ǉ�
							super.ExcelSetValue("���ڌŒ�`�F�b�N3", "�����ڌŒ�T���v��NO");
							//ADD 2014/05/05 end �yQP@30154�z�ǉ�
						}
						//ADD 2014/01/15 nishigawa �yQP@30154�z�ǉ��ۑ�No.11 end

					}

				}catch(ExceptionWaning e){
					//�ő�s���𒴂���ƁAExceptionWaning��Throw�����̂Ń��[�v���I������B
					break;

				}finally{

				}

			}

			//���ޏ����Z�b�g����
			for (int i = 0; i < lstRecset_Shizai.size(); i++) {

				//�������ʂ�1�s�������o��
				Object[] items = (Object[]) lstRecset_Shizai.get(i);

				// 20160524  KPX@1600766 ADD start
				//�P���J���i�S�āj�ȊO�̎��A�P���E�������B��
				if (flgTanka < 9) {
					items[9] = 0;
					items[10] = 0;
					items[11] = 0;
				}
				// 20160524  KPX@1600766 ADD end

				try{
					//Excel�ɒl���Z�b�g����

					//����_�Ώێ��ށi�H��L���j
					super.ExcelSetValue("����_�Ώێ��ށi�H��L���j", toString(items[12]));
					//����_���ރR�[�h
					super.ExcelSetValue("����_���ރR�[�h", toString(items[7]));
					//����_���ޖ�
					super.ExcelSetValue("����_���ޖ�", toString(items[8]));
					//����_�P��
					super.ExcelSetValue("����_�P��", (toDouble(items[9])));
					//����_����
					super.ExcelSetValue("����_����", toDouble(items[10]) / 100);
					//����_�g�p��
					super.ExcelSetValue("����_�g�p��", toDouble(items[11]));

				}catch(ExceptionWaning e){
					//�ő�s���𒴂���ƁAExceptionWaning��Throw�����̂Ń��[�v���I������B
					break;

				}finally{

				}

			}

			//DEL 2012.4.30�@�yH24�N�x�Ή��zJEXCEL�Ή��̈׈ړ�
			//�G�N�Z���t�@�C�����_�E�����[�h�t�H���_�ɐ�������
//			ret = super.ExcelOutput_genka(
//					makeShisakuNo(
//							reqData.getFieldVale(0, 0, "cd_shain")
//							,reqData.getFieldVale(0, 0, "nen")
//							,reqData.getFieldVale(0, 0, "no_oi")
//							)
//					,hinNm
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

	// 20160524  KPX@1600766 MOD start
	/**
	 * ���̑�_�������Z�\(EXCEL)�𐶐�����
	 * @param lstRecset : �����f�[�^���X�g
	 * @param lstRecset_Shizai : ���ރf�[�^���X�g
	 * @param flgTanka: �P���J������ �i9�F�P���J���i�S�āj 1�F�P���J������   0�F�P���J���s�j
	 * @return�@String : �_�E�����[�h�p�̃p�X
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private String makeExcelFile2(

			List<?> lstRecset
			,List<?> lstRecset_Shizai
			, RequestResponsKindBean reqData
			, int flgTanka
	// 20160524  KPX@1600766 MOD end
			)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		String ret = "";
		String strShisakuSeq1 = "";
		String strShisakuSeq2 = "";
		String strShisakuSeq3 = "";
		String hinNm = "";
		int intChuijiko = 0;

		try {
			//EXCEL�e���v���[�g��ǂݍ���
			super.ExcelReadTemplate("���̑��������Z�\");

			// ADD 2015/06/17 E.kitazawa�yQP@40812�z  --- start
			//�I���T���v�������擾�i�e�[�u��Bean�̌����j
			int reqCnt = ((RequestResponsTableBean)reqData.GetItem(0)).GetCnt();
			// ADD 2015/06/17 E.kitazawa�yQP@40812�z  --- start


			//����SEQ���擾
			try{
				strShisakuSeq1 = toString( reqData.getFieldVale(0, 0, "seq_shisaku"));

			}catch(Exception e){
			}
			try{
				// MOD 2015/06/17 E.kitazawa�yQP@40812�z  --- start
				// ���N�G�X�g�f�[�^�����݂���Ƃ��̂ݒl���擾����
				if (reqCnt > 1) {
					strShisakuSeq2 = toString( reqData.getFieldVale(0, 1, "seq_shisaku"));
				}

			}catch(Exception e){
			}
			try{
				if (reqCnt > 2) {
					strShisakuSeq3 = toString( reqData.getFieldVale(0, 2, "seq_shisaku"));
				}
				// MOD 2015/06/17 E.kitazawa�yQP@40812�z  --- end

			}catch(Exception e){
			}

			//�_�E�����[�h�p��EXCEL�𐶐�����
			for (int i = 0; i < lstRecset.size(); i++) {

				//�������ʂ�1�s�������o��
				Object[] items = (Object[]) lstRecset.get(i);

				// 20160524  KPX@1600766 ADD start
				//�P���J�������ɂ�� 0�ݒ�
				items = setObj(items, flgTanka);
				// 20160524  KPX@1600766 ADD end

				//ADD 2012.4.30�@�yH24�N�x�Ή��zJEXCEL�Ή��̈גǉ�
				if(i==0){
					ret = super.ExcelOutput_genka(
							makeShisakuNo(
							reqData.getFieldVale(0, 0, "cd_shain")
							,reqData.getFieldVale(0, 0, "nen")
							,reqData.getFieldVale(0, 0, "no_oi")
							),
							this.replaceSymbol(toString(items[2])));	//�i��
				}

				try{
					//Excel�ɒl���Z�b�g����

					//�i��
					super.ExcelSetValue("�i��", toString(items[2]));
					hinNm = toString(items[2]);
					//�˗��ԍ�
					super.ExcelSetValue("�˗��ԍ�", toString(items[3]));
					//���t
					super.ExcelSetValue("���t", toString(items[4]));
					//�H��R�[�h�i���Y�{���j
					//super.ExcelSetValue("�H��R�[�h", toDouble(items[5]));		//DEL 2012.4.30�@�yH24�N�x�Ή��z�R�[�h�o�͂��疼�̏o�͂ɕύX
					//ADD&UPD 2012.4.30�@�yH24�N�x�Ή��z��������ŏI�A�N�Z�X�Ђ�S���҂Ƃ��ĕҏW�B�������A�c�ƁA���Y�Ǘ���
					//������
					super.ExcelSetValue("������", toString(items[50]));
					//�c��
					super.ExcelSetValue("�c��", toString(items[51]));
					//���Y�Ǘ���
					super.ExcelSetValue("���Y�Ǘ���", toString(items[52]));

					//ADD 2012.4.30�@�yH24�N�x�Ή��z�������Z�����A�������Z����(�c�Ɨp)
					super.ExcelSetValue("�������Z����", toString(items[44]));
					super.ExcelSetValue("�������Z����(�c�Ɨp)", toString(items[45]));

					//����SEQ���Ƃɕ���
					if ( toString(items[38]).equals(strShisakuSeq1) ){
						//�T���v��No1
						super.ExcelSetValue("�T���v��No1", toString(items[8]));

						//ADD 2012.4.30�@�yH24�N�x�Ή��z���i��񗓂ɍH����1�ҏW
						super.ExcelSetValue("���i_�T���v��No1","�T���v��:" + toString(items[8]));
						if ( !toString(items[39]).isEmpty() ) {
							super.ExcelSetValue("���i_�H����No1","�H����:" + toString(items[39]));
							super.ExcelSetValue("���i_���ӎ���1",toString(items[33]));
						}

					} else if ( toString(items[38]).equals(strShisakuSeq2) ){
						//�T���v��No2
						super.ExcelSetValue("�T���v��No2", toString(items[8]));

						//ADD 2012.4.30�@�yH24�N�x�Ή��z���i��񗓂ɍH����2�ҏW
						super.ExcelSetValue("���i_�T���v��No2","�T���v��:" + toString(items[8]));
						if ( !toString(items[39]).isEmpty() ) {
							super.ExcelSetValue("���i_�H����No2","�H����:" + toString(items[39]));
							super.ExcelSetValue("���i_���ӎ���2",toString(items[33]));
						}

					} else if ( toString(items[38]).equals(strShisakuSeq3) ){
						//�T���v��No3
						super.ExcelSetValue("�T���v��No3", toString(items[8]));

						//ADD 2012.4.30�@�yH24�N�x�Ή��z���i��񗓂ɍH����3�ҏW
						super.ExcelSetValue("���i_�T���v��No3","�T���v��:" + toString(items[8]));
						if ( !toString(items[39]).isEmpty() ) {
							super.ExcelSetValue("���i_�H����No3","�H����:" + toString(items[39]));
							super.ExcelSetValue("���i_���ӎ���3",toString(items[33]));
						}

					}

					//�H�����u�E�ے����t�v�̏ꍇ
					if (toString(items[1]).equals("1")) {
						//***** [�E�ے����t] *****

						if ( toString(items[38]).equals(strShisakuSeq1) ){
							//�V�K�����i���Y�{���j9
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
						if ( toString(items[38]).equals(strShisakuSeq1) ){
							//�z��1
							super.ExcelSetValue("�H��1_�z��1", toDouble(items[14]));

						} else if ( toString(items[38]).equals(strShisakuSeq2) ){
							//�z��2
							super.ExcelSetValue("�H��1_�z��2", toDouble(items[14]));

						} else if ( toString(items[38]).equals(strShisakuSeq3) ){
							//�z��3
							super.ExcelSetValue("�H��1_�z��3", toDouble(items[14]));

						}

					//�H�����u�����v�̏ꍇ
					} else if (toString(items[1]).equals("2")) {
						//***** [����] *****

						if ( toString(items[38]).equals(strShisakuSeq1) ){
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
						if ( toString(items[38]).equals(strShisakuSeq1) ){
							//�z��1
							super.ExcelSetValue("�H��2_�z��1", toDouble(items[14]));

						} else if ( toString(items[38]).equals(strShisakuSeq2) ){
							//�z��2
							super.ExcelSetValue("�H��2_�z��2", toDouble(items[14]));

						} else if ( toString(items[38]).equals(strShisakuSeq3) ){
							//�z��3
							super.ExcelSetValue("�H��2_�z��3", toDouble(items[14]));

						}

					}

					//�������
					if ( toString(items[38]).equals(strShisakuSeq1) ){
						//�d�オ�荇�v�d��1
						if ( !toString(items[37]).isEmpty() ) {
							super.ExcelSetValue("�d�オ�荇�v�d��1", toDouble(items[37]));

						}

					} else if ( toString(items[38]).equals(strShisakuSeq2) ){
						//�d�オ�荇�v�d��2
						if ( !toString(items[37]).isEmpty() ) {
							super.ExcelSetValue("�d�オ�荇�v�d��2", toDouble(items[37]));

						}

					} else if ( toString(items[38]).equals(strShisakuSeq3) ){
						//�d�オ�荇�v�d��3
						if ( !toString(items[37]).isEmpty() ) {
							super.ExcelSetValue("�d�オ�荇�v�d��3", toDouble(items[37]));

						}

					}

					//ADD 2012.4.30�@�yH24�N�x�Ή��z�H�ꖼ(������)�ǉ�
					super.ExcelSetValue("�H��", toString(items[43]));
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
					// MOD 2013/11/14 okano�yQP@30154�zstart
//						//�������b�g
//						super.ExcelSetValue("�������b�g", toString(items[53]));
//						//��������
//						super.ExcelSetValue("��������", toString(items[27]));
//						//�z�蕨��
//						// MOD 2013/9/6 okano�yQP@30151�zNo.30 start
//	//						super.ExcelSetValue("�z�蕨��", toString(items[28]));
//						super.ExcelSetValue("�z�蕨��", toDouble(items[28]));
//						super.ExcelSetValue("�z�蕨�ʒP��", toString(items[29])+toString(items[30]));
//						// MOD 2013/9/6 okano�yQP@30151�zNo.30 end
//						//����
//						super.ExcelSetValue("����", toString(items[31]));
//						//��]����
//						super.ExcelSetValue("��]����", toString(items[32]));
//
//						//ADD 2012.4.30�@�yH24�N�x�Ή��z�̔�����  �̔����ԑI��(�ʔNor�X�|�b�g)+�̔�����+�̔����ԒP��
//						super.ExcelSetValue("�̔�����", toString(items[47]) + toString(items[46]) + toString(items[48]));
//						//ADD 2012.4.30�@�yH24�N�x�Ή��z�����P�ʂ����ꂼ�ꌴ���A���������ɐݒ�B
//						super.ExcelSetValue("�����P��", toString(items[49]));
//						super.ExcelSetValue("�����P��", toString(items[49]));
					if ( toString(items[38]).equals(strShisakuSeq1) ){
						//�������b�g
						super.ExcelSetValue("�������b�g1", toString(items[53]));
						//��������
						super.ExcelSetValue("��������1", toString(items[27]));
						//�z�蕨��
						super.ExcelSetValue("�z�蕨��1", toDouble(items[28]));
						super.ExcelSetValue("�z�蕨�ʒP��1", toString(items[29])+toString(items[30]));
						//����
						super.ExcelSetValue("����1", toString(items[31]));
						//��]����
						super.ExcelSetValue("��]����1", toString(items[32]));

						//�̔�����  �̔����ԑI��(�ʔNor�X�|�b�g)+�̔�����+�̔����ԒP��
						super.ExcelSetValue("�̔�����1", toString(items[47]) + toString(items[46]) + toString(items[48]));
						//�����P�ʂ����ꂼ�ꌴ���A���������ɐݒ�B
						super.ExcelSetValue("�����P��1", toString(items[49]));
						super.ExcelSetValue("�����P��1", toString(items[49]));

						//ADD 2014/04/04 shima �yQP@30154�zstart
						super.ExcelSetValue("���Z��1", toString(items[71]));
						//ADD 2014/04/04 shima �yQP@30154�zend

					} else if ( toString(items[38]).equals(strShisakuSeq2) ){
						//�������b�g
						super.ExcelSetValue("�������b�g2", toString(items[53]));
						//��������
						super.ExcelSetValue("��������2", toString(items[27]));
						super.ExcelSetValue("�z�蕨��2", toDouble(items[28]));
						super.ExcelSetValue("�z�蕨�ʒP��2", toString(items[29])+toString(items[30]));
						//����
						super.ExcelSetValue("����2", toString(items[31]));
						//��]����
						super.ExcelSetValue("��]����2", toString(items[32]));

						//�̔�����  �̔����ԑI��(�ʔNor�X�|�b�g)+�̔�����+�̔����ԒP��
						super.ExcelSetValue("�̔�����2", toString(items[47]) + toString(items[46]) + toString(items[48]));
						//�����P�ʂ����ꂼ�ꌴ���A���������ɐݒ�B
						super.ExcelSetValue("�����P��2", toString(items[49]));
						super.ExcelSetValue("�����P��2", toString(items[49]));

						//ADD 2014/04/04 shima �yQP@30154�zstart
						super.ExcelSetValue("���Z��2", toString(items[71]));
						//ADD 2014/04/04 shima �yQP@30154�zend

					} else if ( toString(items[38]).equals(strShisakuSeq3) ){
						//�������b�g
						super.ExcelSetValue("�������b�g3", toString(items[53]));
						//��������
						super.ExcelSetValue("��������3", toString(items[27]));
						super.ExcelSetValue("�z�蕨��3", toDouble(items[28]));
						super.ExcelSetValue("�z�蕨�ʒP��3", toString(items[29])+toString(items[30]));
						//����
						super.ExcelSetValue("����3", toString(items[31]));
						//��]����
						super.ExcelSetValue("��]����3", toString(items[32]));

						//�̔�����  �̔����ԑI��(�ʔNor�X�|�b�g)+�̔�����+�̔����ԒP��
						super.ExcelSetValue("�̔�����3", toString(items[47]) + toString(items[46]) + toString(items[48]));
						//�����P�ʂ����ꂼ�ꌴ���A���������ɐݒ�B
						super.ExcelSetValue("�����P��3", toString(items[49]));
						super.ExcelSetValue("�����P��3", toString(items[49]));

						//ADD 2014/04/04 shima �yQP@30154�zstart
						super.ExcelSetValue("���Z��3", toString(items[71]));
						//ADD 2014/04/04 shima �yQP@30154�zend

					}
					// MOD 2013/11/14 okano�yQP@30154�zend

					//DEL 2012.4.30�@�yH24�N�x�Ή��z���i���̕ύX
					//�ŐV�̒��ӎ�����ݒ肷��
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

					//�ŏI���Z�p
					if ( toString(items[38]).equals(strShisakuSeq1) ){
//2009/11/27�@isono�@�ۑ�Ǘ��F119
						//���Ϗ[�U��1�i���Y�{���j32
//						super.ExcelSetValue("���Ϗ[�U��1", toDouble(items[32]));
						super.ExcelSetValue("���Ϗ[�U��1", toDouble(items[34])*1000);
						//�L������1�i���Y�{���j33
						super.ExcelSetValue("�L������1", (toDouble(items[35]) / 100));
						//�Œ��1�i���Y�{���j34
						super.ExcelSetValue("�Œ��1", toDouble(items[36]));

						//ADD 2014/01/15 nishigawa �yQP@30154�z�ǉ��ۑ�No.11 start
						//�Œ荀�ڃ`�F�b�N����Ă���T���v����̏ꍇ�́A�c�a�l���v�Z�Z���ɏ㏑��
						if(toString(items[54]).equals("1")){
							super.ExcelSetValue("���x����(g)1", toDouble(items[59])*1000);
							super.ExcelSetValue("������/�P�[�X1", toDouble(items[61]));
							super.ExcelSetValue("�ޗ���/�P�[�X1", toDouble(items[62]));
							super.ExcelSetValue("�����v/�P�[�X1", toDouble(items[63]));
							super.ExcelSetValue("�����v/��1", toDouble(items[64]));
							super.ExcelSetValue("������/�j�f1", toDouble(items[65]));
							super.ExcelSetValue("�ޗ���/�j�f1", toDouble(items[66]));
							super.ExcelSetValue("�Œ��/�j�f1", toDouble(items[70]));
							super.ExcelSetValue("�����v/�j�f1", toDouble(items[67]));
						//ADD 2014/04/22 start�yQP@30154�z�ǉ�
							super.ExcelSetValue("��]����1", toDouble(items[68]));
							super.ExcelSetValue("�e��1", toDouble(items[69]));
						//ADD 2014/04/22 end �yQP@30154�z�ǉ�
							//ADD 2014/05/05 start �yQP@30154�z�ǉ�
							super.ExcelSetValue("���ڌŒ�`�F�b�N1", "�����ڌŒ�T���v��NO");
							//ADD 2014/05/05 end �yQP@30154�z�ǉ�
						}
						//ADD 2014/01/15 nishigawa �yQP@30154�z�ǉ��ۑ�No.11 end

					} else if ( toString(items[38]).equals(strShisakuSeq2) ){
//2009/11/27�@isono�@�ۑ�Ǘ��F119
						//���Ϗ[�U��2�i���Y�{���j
//						super.ExcelSetValue("���Ϗ[�U��2", toDouble(items[32]));
						super.ExcelSetValue("���Ϗ[�U��2", toDouble(items[34])*1000);
						//�L������2�i���Y�{���j
						super.ExcelSetValue("�L������2", (toDouble(items[35]) / 100));
						//�Œ��2�i���Y�{���j
						super.ExcelSetValue("�Œ��2", toDouble(items[36]));

						//ADD 2014/01/15 nishigawa �yQP@30154�z�ǉ��ۑ�No.11 start
						//�Œ荀�ڃ`�F�b�N����Ă���T���v����̏ꍇ�́A�c�a�l���v�Z�Z���ɏ㏑��
						if(toString(items[54]).equals("1")){
							super.ExcelSetValue("���x����(g)2", toDouble(items[59])*1000);
							super.ExcelSetValue("������/�P�[�X2", toDouble(items[61]));
							super.ExcelSetValue("�ޗ���/�P�[�X2", toDouble(items[62]));
							super.ExcelSetValue("�����v/�P�[�X2", toDouble(items[63]));
							super.ExcelSetValue("�����v/��2", toDouble(items[64]));
							super.ExcelSetValue("������/�j�f2", toDouble(items[65]));
							super.ExcelSetValue("�ޗ���/�j�f2", toDouble(items[66]));
							super.ExcelSetValue("�Œ��/�j�f2", toDouble(items[70]));
							super.ExcelSetValue("�����v/�j�f2", toDouble(items[67]));
						//ADD 2014/04/22 start�yQP@30154�z�ǉ�
							super.ExcelSetValue("��]����2", toDouble(items[68]));
							super.ExcelSetValue("�e��2", toDouble(items[69]));
						//ADD 2014/04/22 end �yQP@30154�z�ǉ�
							//ADD 2014/05/05 start �yQP@30154�z�ǉ�
							super.ExcelSetValue("���ڌŒ�`�F�b�N2", "�����ڌŒ�T���v��NO");
							//ADD 2014/05/05 end �yQP@30154�z�ǉ�
						}
						//ADD 2014/01/15 nishigawa �yQP@30154�z�ǉ��ۑ�No.11 end

					} else if ( toString(items[38]).equals(strShisakuSeq3) ){
//2009/11/27�@isono�@�ۑ�Ǘ��F119
						//���Ϗ[�U��3�i���Y�{���j
//						super.ExcelSetValue("���Ϗ[�U��3", toDouble(items[32]));
						super.ExcelSetValue("���Ϗ[�U��3", toDouble(items[34])*1000);
						//�L������3�i���Y�{���j
						super.ExcelSetValue("�L������3", (toDouble(items[35]) / 100));
						//�Œ��3�i���Y�{���j
						super.ExcelSetValue("�Œ��3", toDouble(items[36]));

						//ADD 2014/01/15 nishigawa �yQP@30154�z�ǉ��ۑ�No.11 start
						//�Œ荀�ڃ`�F�b�N����Ă���T���v����̏ꍇ�́A�c�a�l���v�Z�Z���ɏ㏑��
						if(toString(items[54]).equals("1")){
							super.ExcelSetValue("���x����(g)3", toDouble(items[59])*1000);
							super.ExcelSetValue("������/�P�[�X3", toDouble(items[61]));
							super.ExcelSetValue("�ޗ���/�P�[�X3", toDouble(items[62]));
							super.ExcelSetValue("�����v/�P�[�X3", toDouble(items[63]));
							super.ExcelSetValue("�����v/��3", toDouble(items[64]));
							super.ExcelSetValue("������/�j�f3", toDouble(items[65]));
							super.ExcelSetValue("�ޗ���/�j�f3", toDouble(items[66]));
							super.ExcelSetValue("�Œ��/�j�f3", toDouble(items[70]));
							super.ExcelSetValue("�����v/�j�f3", toDouble(items[67]));
						//ADD 2014/04/22 start�yQP@30154�z�ǉ�
							super.ExcelSetValue("��]����3", toDouble(items[68]));
							super.ExcelSetValue("�e��3", toDouble(items[69]));
						//ADD 2014/04/22 end �yQP@30154�z�ǉ�
							//ADD 2014/05/05 start �yQP@30154�z�ǉ�
							super.ExcelSetValue("���ڌŒ�`�F�b�N3", "�����ڌŒ�T���v��NO");
							//ADD 2014/05/05 end �yQP@30154�z�ǉ�
						}
						//ADD 2014/01/15 nishigawa �yQP@30154�z�ǉ��ۑ�No.11 end

					}

				}catch(ExceptionWaning e){
					//�ő�s���𒴂���ƁAExceptionWaning��Throw�����̂Ń��[�v���I������B
					break;

				}finally{

				}

			}

			//���ޏ����Z�b�g����

			for (int i = 0; i < lstRecset_Shizai.size(); i++) {

				//�������ʂ�1�s�������o��
				Object[] items = (Object[]) lstRecset_Shizai.get(i);

				// 20160524  KPX@1600766 ADD start
				//�P���J���i�S�āj�ȊO�̎��A�P���E�������B��
				if (flgTanka < 9) {
					items[9] = 0;
					items[10] = 0;
					items[11] = 0;
				}
				// 20160524  KPX@1600766 ADD end

				try{
					//Excel�ɒl���Z�b�g����

					//����_�Ώێ��ށi�H��L���j
					super.ExcelSetValue("����_�Ώێ��ށi�H��L���j", toString(items[12]));
					//����_���ރR�[�h
					super.ExcelSetValue("����_���ރR�[�h", toString(items[7]));
					//����_���ޖ�
					super.ExcelSetValue("����_���ޖ�", toString(items[8]));
					//����_�P��
					super.ExcelSetValue("����_�P��", (toDouble(items[9])));
					//����_����
					super.ExcelSetValue("����_����", toDouble(items[10]) / 100);
					//����_�g�p��
					super.ExcelSetValue("����_�g�p��", toDouble(items[11]));

				}catch(ExceptionWaning e){
					//�ő�s���𒴂���ƁAExceptionWaning��Throw�����̂Ń��[�v���I������B
					break;

				}finally{

				}

			}

			//DEL 2012.4.30�@�yH24�N�x�Ή��zJEXCEL�Ή��̈׈ړ�
//			//�G�N�Z���t�@�C�����_�E�����[�h�t�H���_�ɐ�������
//			ret = super.ExcelOutput_genka(
//					makeShisakuNo(
//							reqData.getFieldVale(0, 0, "cd_shain")
//							,reqData.getFieldVale(0, 0, "nen")
//							,reqData.getFieldVale(0, 0, "no_oi")
//							)
//					,hinNm
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
	 * ���[��ޔ���
	 * @param KindBean : ���N�G�X�g�f�[�^�i�@�\�j
	 * @return String  : ���[��ށi1�F�������@2�F�������ȊO�j
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning : �Y���f�[�^����
	 */
	private String getSyurui(

			RequestResponsKindBean KindBean
			)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		//��������
		List<?> listRisult = null;
		//SQL�@StringBuffer
		StringBuffer strSQL = new StringBuffer();

		String ret = "";

		try {
			//SQL���̍쐬
			strSQL.append(" SELECT ");
			strSQL.append("  T120.cd_shain ");
			strSQL.append(" ,T120.nen ");
			strSQL.append(" ,T120.no_oi ");
			strSQL.append(" ,MAX(M302.value1) AS SYURUI ");
			strSQL.append(" FROM ");
			strSQL.append("           tr_haigo AS T120 ");
			strSQL.append(" LEFT JOIN ma_literal AS M302 ");
			strSQL.append(" ON  M302.cd_category = 'K_kote'  ");
			strSQL.append(" AND M302.cd_literal = T120.zoku_kotei ");
			strSQL.append(" WHERE ");
			strSQL.append("     T120.cd_shain = " + KindBean.getFieldVale("table", "rec", "cd_shain") + " ");
			strSQL.append(" AND T120.nen      = " + KindBean.getFieldVale("table", "rec", "nen") + " ");
			strSQL.append(" AND T120.no_oi    = " + KindBean.getFieldVale("table", "rec", "no_oi") + " ");
			strSQL.append(" GROUP BY  ");
			strSQL.append("  T120.cd_shain ");
			strSQL.append(" ,T120.nen ");
			strSQL.append(" ,T120.no_oi ");

			try{
				//SQL�����s
				listRisult = searchDB.dbSearch(strSQL.toString());

			}catch(ExceptionWaning ew){

			}
			if (listRisult == null){

			}else{
				//�_�E�����[�h�p��EXCEL�𐶐�����
				for (int i = 0; i < listRisult.size(); i++) {

					//�������ʂ�1�s�������o��
					Object[] items = (Object[]) listRisult.get(i);

					ret = toString(items[3], "1");

				}

			}

		} catch (Exception e) {
			em.ThrowException(e, "�������ޏ��ADB�����Ɏ��s���܂����B");

		} finally {
			//�ϐ��̍폜
			strSQL = null;

		}
		return ret;

	}
	/**
	 * �Ώۂ̌������Z�f�[�^�i���ށj����������
	 * @param KindBean : ���N�G�X�g�f�[�^�i�@�\�j
	 * @return List : �������ʂ̃��X�g
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning : �Y���f�[�^����
	 */
	private List<?> getData_shizai(

			RequestResponsKindBean KindBean
			)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		//��������
		List<?> ret = null;
		//SQL�@StringBuffer
		StringBuffer strSQL = new StringBuffer();

		try {
			//SQL���̍쐬
			strSQL.append(" SELECT ");
			strSQL.append("  T340.cd_shain ");		//0
			strSQL.append(" ,T340.nen ");			//1
			strSQL.append(" ,T340.no_oi ");			//2
			strSQL.append(" ,T340.seq_shizai ");	//3
			strSQL.append(" ,T340.no_sort ");		//4
			strSQL.append(" ,T340.cd_kaisha ");		//5
			strSQL.append(" ,T340.cd_busho  ");		//6
			strSQL.append(" ,T340.cd_shizai ");		//7
			strSQL.append(" ,T340.nm_shizai ");		//8
			strSQL.append(" ,T340.tanka ");			//9
			strSQL.append(" ,T340.budomari ");		//10
			strSQL.append(" ,T340.cs_siyou ");		//11
			strSQL.append(" ,M302.nm_literal ");	//12
//			strSQL.append(" ,ISNULL(T340.tanka, 0)/(ISNULL(T340.budomari, 0)/100)*ISNULL(T340.cs_siyou,0) AS KINGAKU ");//13
			strSQL.append(" FROM ");
			strSQL.append("           tr_shisan_shizai AS T340 ");
			strSQL.append(" LEFT JOIN ma_literal AS M302 ");
			strSQL.append(" ON  M302.cd_category = 'K_kigo_kojyo'  ");
			strSQL.append(" AND M302.cd_literal = CONVERT(varchar, T340.cd_kaisha) + '-' + CONVERT(varchar, T340.cd_busho) ");
			strSQL.append(" WHERE ");
			strSQL.append("     T340.cd_shain = " + KindBean.getFieldVale("table", "rec", "cd_shain") + " ");
			strSQL.append(" AND T340.nen      = " + KindBean.getFieldVale("table", "rec", "nen") + " ");
			strSQL.append(" AND T340.no_oi    = " + KindBean.getFieldVale("table", "rec", "no_oi") + " ");

			//�yQP@00342�z
			strSQL.append(" AND T340.no_eda    = " + KindBean.getFieldVale("table", "rec", "no_eda") + " ");

			strSQL.append(" ORDER BY ");
			strSQL.append("  T340.seq_shizai ");

			try{
				//SQL�����s
				ret = searchDB.dbSearch(strSQL.toString());

			}catch(ExceptionWaning ew){

			}

		} catch (Exception e) {
			em.ThrowException(e, "�������ޏ��ADB�����Ɏ��s���܂����B");

		} finally {
			//�ϐ��̍폜
			strSQL = null;

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

			//�yQP@00342�z
			//����iNo�@�}��		no_eda
			strNo_eda = reqData.getFieldVale(0, 0, "no_eda");

			// ADD 2015/06/17 E.kitazawa�yQP@40812�z  --- start
			//�I���T���v�������擾�i�e�[�u��Bean�̌����j
			int reqCnt = ((RequestResponsTableBean)reqData.GetItem(0)).GetCnt();
			// ADD 2015/06/17 E.kitazawa�yQP@40812�z  --- end


			//SQL���̍쐬
			ret.append(" SELECT ");
			ret.append("   ISNULL(CONVERT(VARCHAR,M302K.value1),'') AS kotei1 ");	//0
			ret.append("  ,M302K.value2 AS kotei2 ");								//1

			//�yH24�N�x�Ή��z2012/05/08 TT H.SHIMA mod Start
//			ret.append("  ,T110.nm_hin AS hinmei ");
			ret.append("  ,CASE WHEN T310.nm_edaShisaku IS NOT NULL ");
			ret.append("  	THEN ");
			ret.append("  	CASE RTRIM(T310.nm_edaShisaku) WHEN '' ");
			ret.append("  	THEN T110.nm_hin ");
			ret.append("  	ELSE T110.nm_hin + ' �y' + T310.nm_edaShisaku + '�z' ");
			ret.append("  	END ");
			ret.append("  	ELSE T110.nm_hin END AS hinmei ");						//2
			//�yH24�N�x�Ή��z2012/05/08 TT H.SHIMA mod end

			ret.append("  ,CONVERT(VARCHAR,T110.no_irai) AS no_irai ");				//3
			ret.append(" ,CONVERT(VARCHAR,YEAR(GETDATE())) + '�N' ");
			ret.append("   + CONVERT(VARCHAR,MONTH(GETDATE())) + '��' ");
			ret.append("   + CONVERT(VARCHAR,DAY(GETDATE())) + '��' AS dt_hizuke ");//4
			ret.append("  ,T310.cd_kojo AS cd_kojo ");								//5�� �H��R�[�h
			ret.append("  ,M101.nm_user AS kenkyusho ");							//6


			//�yQP@00342�z
			//ret.append("  ,T110.cd_eigyo AS eigyo ");								//7
			ret.append("  ,M110.nm_user AS eigyo ");



			ret.append("  ,T131.nm_sample AS nm_sample ");							//8�T���v��No
//2009/11/27 isono UPD START �ۑ�Ǘ��F123�̑Ή�
//			ret.append("  ,CASE ISNULL(LEFT(T120.cd_genryo,1),'') WHEN '' THEN ''  ");
//			ret.append("    WHEN 'N' THEN '��' ELSE '' END AS new_genryo ");			//9��
			ret.append(" ,CASE ");
			ret.append("       WHEN LEFT(ISNULL(T120.cd_genryo, ' '), 1) = 'N'  THEN '�m' ");
			ret.append("       WHEN LEFT(ISNULL(T320.nm_genryo, ' '), 1) = '��' THEN '��' ");
			ret.append("       WHEN LEFT(ISNULL(T320.nm_genryo, ' '), 1) = '��' THEN '��' ");
			ret.append("       ELSE '' ");
			ret.append(" END AS new_genryo ");						//9��
//2009/11/27 isono UPD END   �ۑ�Ǘ��F123�̑Ή�
			ret.append("  ,T120.cd_genryo AS cd_genryo ");			//10
			ret.append("  ,T320.nm_genryo AS nm_genryo ");			//11��
			ret.append("  ,ISNULL(T320.tanka_ins,T320.tanka_ma) AS tanka ");//12��
			ret.append("  ,ISNULL(T320.budomari_ins,T320.budomar_ma) AS budomari ");//13��
			ret.append("  ,T132.quantity AS haigoryo ");			//14
			ret.append("  ,T141.zyusui AS zyuten_suiso ");			//15
			ret.append("  ,T141.zyuabura AS zyuten_yuso ");			//16
			ret.append("  ,T131.hiju AS hiju ");					//17
			ret.append("  ,M3021.nm_literal AS nm_seizohoho ");		//18
			ret.append("  ,M3022.nm_literal AS nm_juten ");			//19
			ret.append("  ,T110.hoho_sakin AS hoho_sakin ");		//20
			ret.append("  ,M3023.nm_literal AS nm_ondo ");			//21
			ret.append("  ,T110.youki AS youki ");					//22
			ret.append("  ,T310.cd_nisugata AS nm_nisugata ");		//23��
			ret.append("  ,T310.yoryo AS yoryo ");					//24
			ret.append("  ,T310.su_iri AS su_iri ");				//25��
			// CHG 2019/09/17 BRC.ida start
			//ret.append("  ,T110.shomikikan AS shomikikan ");		//26
			ret.append("  ,CASE WHEN T110.shomikikan IS NULL THEN NULL ");
			ret.append("   ELSE T110.shomikikan + M3024.nm_literal END AS shomikikan ");//26
			// CHG 2019/09/17 BRC.ida start			

			// MOD 2013/7/2 shima�yQP@30151�zNo.37 start
//			ret.append("  ,T310.dt_hatubai AS dt_hatubai ");		//27��
//			ret.append("  ,T310.buturyo AS buturyo ");				//28��
//			ret.append("  ,T310.baika AS baika ");					//29��
//			ret.append("  ,T310.genka AS kibogenka ");				//30��
			ret.append("  ,T313_kihon_sub.dt_hatubai AS dt_hatubai ");//27��
			// MOD 2013/9/6 okano�yQP@30151�zNo.30 start
//				ret.append("  ,T313_kihon_sub.buturyo AS buturyo ");
			ret.append("  ,T313_kihon_sub.buturyo_suti AS buturyo_s ");//28��
			ret.append("  ,M302_Buturyo_U.nm_literal AS buturyo_u ");//29��
			ret.append("  ,M302_Buturyo_K.nm_literal AS buturyo_k ");//30��
			// MOD 2013/9/6 okano�yQP@30151�zNo.30 end
			ret.append("  ,T313_kihon_sub.baika AS baika ");		//31��
			ret.append("  ,T313_kihon_sub.genka AS kibogenka ");	//32��
			// MOD 2013/7/2 shima�yQP@30151�zNo.37 end

			ret.append("  ,T133.chuijiko AS chuijiko ");			//33 ���i���
			ret.append("  ,T331.heikinjuten AS heikinzyu ");		//34��
			ret.append("  ,T331.budomari AS yukobudomari ");		//35��
			ret.append("  ,T331.cs_kotei AS koteihi ");				//36��
			ret.append("  ,T131.juryo_shiagari_g AS shiagarijuryo ");//37
			ret.append("  ,T131.seq_shisaku AS seq_shisaku ");		//38
			ret.append("  ,T131.no_chui AS no_chui ");				//39�H����(���ӎ�����)
			ret.append("  ,T120.sort_kotei AS sort_kotei ");		//40
			ret.append("  ,T120.sort_genryo AS sort_genryo ");		//41
			ret.append("  ,T131.no_shisan AS no_shisan ");			//42

			//ADD 2012.4.30�@�yH24�N�x�Ή��z�H�ꖼ(�������A�������Z�����A����c�ƃ����ǉ��A�̔�����
			ret.append("  ,M104.nm_busho  AS nm_busho ");			//43�H�ꖼ(������)
			ret.append("  ,T311.memo AS memo ");					//44�������Z����
			ret.append("  ,T312.memo_eigyo AS memo_eigyo ");		//45�������Z����(�c�Ɨp)

			// MOD 2013/7/2 shima�yQP@30151�zNo.37 start
//			ret.append("  ,T310.kikan_hanbai_suti AS kikan_hanbai_suti ");	//44�̔�����
			ret.append("  ,T313_kihon_sub.kikan_hanbai_suti AS kikan_hanbai_suti ");//46�̔�����
			// MOD 2013/7/2 shima�yQP@30151�zNo.37 end

			//ADD 2012.4.30�@�yH24�N�x�Ή��z�̔����ԁA�̔��ۊ���(�ʔNor�X�|�b�g)�A�̔�����(�P��)�A�����P��
			ret.append("  ,M302_Hanbai_T.nm_literal AS kikan_hanbai_sen ");	//47�̔����ԑI��(�ʔNor�X�|�b�g)
			ret.append("  ,M302_Hanbai_K.nm_literal AS kikan_hanbai_tani ");//48�̔����ԒP��
			ret.append("  ,M302_TNI_G.nm_literal AS cd_genka_tani ");		//49�����P��

			//ADD 2012.4.30�@�yH24�N�x�Ή��z�X�e�[�^�X�ύX�������A�������A�c�ƁA���Y�Ǘ����̂��̂��̍ŏI�X�V�S���Җ����擾
			ret.append("  ,M101_nm_kenkyu.nm_user AS nm_kenkyu ");	//50�������@�ŏI�A�N�Z�X�S���Җ�
			ret.append("  ,M101_nm_eigyo.nm_user AS nm_eigyo ");	//51�c�Ɓ@�ŏI�A�N�Z�X�S���Җ�
			ret.append("  ,M101_nm_seisan.nm_user AS nm_seisan ");	//52���Y�Ǘ����@�ŏI�A�N�Z�X�S���Җ�

			// MOD 2013/7/2 shima�yQP@30151�zNo.37 start
//			ret.append("  ,T310.lot AS lot ");	                    //   51
			ret.append("  ,T313_kihon_sub.lot AS lot ");			//   53
			// MOD 2013/7/2 shima�yQP@30151�zNo.37 end

			//ADD 2014/01/15 nishigawa �yQP@30154�z�ǉ��ۑ�No.11 start
			ret.append(" ,T331.fg_koumokuchk ");	//54 ���ڌŒ�`�F�b�N
			ret.append(" ,T332.zyusui ");			//55 �[�U�ʐ����i���j
			ret.append(" ,T332.zyuabura ");			//56 �[�U�ʖ����i���j
			ret.append(" ,T332.gokei ");			//57 ���v�ʁi���j
			ret.append(" ,T332.hiju ");				//58 ��d
			ret.append(" ,T332.reberu ");			//59 ���x���ʁi�s�j
			ret.append(" ,T332.hijukasan ");		//60 ��d���Z�ʁi�s�j
			ret.append(" ,T332.cs_genryo ");		//61 ������i�~�j/�P�[�X
			ret.append(" ,T332.cs_zairyohi ");		//62 �ޗ���i�~�j/�P�[�X
			ret.append(" ,T332.cs_genka ");			//63 �����v�i�~�j/�P�[�X
			ret.append(" ,T332.ko_genka ");			//64 �����v�i�~�j/��
			ret.append(" ,T332.kg_genryo ");		//65 ������i�~�j/�s
			ret.append(" ,T332.kg_zairyohi ");		//66 �ޗ���i�~�j/�s
			ret.append(" ,T332.kg_genka ");			//67 �����v�i�~�j/�s
			ret.append(" ,T332.baika ");			//68 ����
			ret.append(" ,T332.arari ");			//69 �e���i���j
			ret.append(" ,T331.kg_kotei ");			//70 �Œ��/����
			//ADD 2014/01/15 nishigawa �yQP@30154�z�ǉ��ۑ�No.11 end

			// ADD 2014/04/04 shima �yQP@30154�zstart
			ret.append(" ,CONVERT(VARCHAR,T331.dt_shisan,111) dt_shisan ");	//71 ���Z��
			// ADD 2014/04/04 shima �yQP@30154�zend

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
			// ADD 2019/09/17 BRC.ida start
			//�ܖ����ԒP���擾
			ret.append(" LEFT JOIN ma_literal AS M3024 ");
			ret.append("  ON M3024.cd_category = '23' ");
			ret.append("  AND T110.shomikikan_tani = M3024.cd_literal ");
			// ADD 2019/09/17 BRC.ida end
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
			//���Z�@����i�iT310�j
			ret.append(" INNER JOIN tr_shisan_shisakuhin T310 ");
			ret.append("  ON T110.cd_shain = T310.cd_shain ");
			ret.append("  AND T110.nen = T310.nen ");
			ret.append("  AND T110.no_oi = T310.no_oi ");

			// ADD 2013/7/2 shima�yQP@30151�zNo.37 start
			ret.append(" INNER JOIN ( ");
			// MOD 2013/11/14 okano �yQP@30154�zstart
//				ret.append("  SELECT TOP(1) T313_s.cd_shain ");
			ret.append("  SELECT T313_s.cd_shain ");
			// MOD 2013/11/14 okano �yQP@30154�zend
			ret.append("   ,T313_s.nen ");
			ret.append("   ,T313_s.no_oi ");
			// ADD 2013/11/14 okano �yQP@30154�zstart
			ret.append("   ,T313_s.seq_shisaku ");
			// ADD 2013/11/14 okano �yQP@30154�zend
			ret.append("   ,T313_s.lot ");
			// MOD 2013/9/6 okano�yQP@30151�zNo.30 start
//				ret.append("   ,T313_s.buturyo ");
			ret.append("   ,T313_s.buturyo_suti ");
			ret.append("   ,T313_s.buturyo_seihin ");
			ret.append("   ,T313_s.buturyo_kikan ");
			// MOD 2013/9/6 okano�yQP@30151�zNo.30 end
			ret.append("   ,T313_s.genka ");
			ret.append("   ,T313_s.baika ");
			ret.append("   ,T313_s.dt_hatubai ");
			ret.append("   ,T313_s.kikan_hanbai_suti ");
			ret.append("   ,T313_s.kikan_hanbai_sen ");
			ret.append("   ,T313_s.kikan_hanbai_tani ");
			ret.append("   ,T313_s.cd_genka_tani ");
			ret.append("  FROM ");
			ret.append("   tr_shisan_kihonjoho T313_s ");
			ret.append("  LEFT JOIN tr_shisaku T131_s ");
			ret.append("   ON T313_s.cd_shain = T131_s.cd_shain ");
			ret.append("   AND T313_s.nen = T131_s.nen ");
			ret.append("   AND T313_s.no_oi = T131_s.no_oi ");
			ret.append("   AND T313_s.seq_shisaku = T131_s.seq_shisaku ");
			ret.append("  WHERE ");
			ret.append("   T313_s.cd_shain = " + strCd_shain );
			ret.append("   AND T313_s.nen = " + strNen );
			ret.append("   AND T313_s.no_oi = " + strNo_oi );
			ret.append("   AND T313_s.no_eda = " + strNo_eda );
			//����SEQ
			ret.append("  AND T313_s.seq_shisaku IN ( ");

			// MOD 2015/06/17 E.kitazawa�yQP@40812�z  --- start
//			for ( int i=0; i<3; i++ ) {
			for ( int i=0; i<reqCnt; i++ ) {
			// MOD 2015/06/17 E.kitazawa�yQP@40812�z  --- end

				//����SEQ�̎擾
				try{
					strShisakuSeq = toString(reqData.getFieldVale(0, i, "seq_shisaku"));

				}catch(Exception e){
					strShisakuSeq = "";

				}

				//����SEQ����ł͂Ȃ��ꍇ�A��������ǉ�����
				if ( !strShisakuSeq.isEmpty() ) {

					if ( i != 0 ) {
						ret.append(" , ");

					}
					ret.append(strShisakuSeq);

				}

			}
			ret.append(" ) ");

			// DEL 2013/11/14 okano �yQP@30154�zstart
//				ret.append("  ORDER BY T131_s.sort_shisaku DESC ");
			// DEL 2013/11/14 okano �yQP@30154�zend
			ret.append("  ) AS T313_kihon_sub ");
			// MOD 2013/11/14 okano �yQP@30154�zstart
//				ret.append("  ON T110.cd_shain = T313_kihon_sub.cd_shain ");
//				ret.append("  AND T110.nen = T313_kihon_sub.nen ");
//				ret.append("  AND T110.no_oi = T313_kihon_sub.no_oi ");
			ret.append("  ON T131.cd_shain = T313_kihon_sub.cd_shain ");
			ret.append("  AND T131.nen = T313_kihon_sub.nen ");
			ret.append("  AND T131.no_oi = T313_kihon_sub.no_oi ");
			ret.append("  AND T131.seq_shisaku = T313_kihon_sub.seq_shisaku ");
			// MOD 2013/11/14 okano �yQP@30154�zend
			// ADD 2013/7/2 shima�yQP@30151�zNo.37 end

			//���Z�@����iT331�j
			ret.append(" INNER JOIN tr_shisan_shisaku T331 ");
			ret.append("  ON T131.cd_shain = T331.cd_shain ");
			ret.append("  AND T131.nen = T331.nen ");
			ret.append("  AND T131.no_oi = T331.no_oi ");
			// ADD 2015/06/17 E.kitazawa�yQP@40812�z  --- start
			ret.append("  AND T310.no_eda = T331.no_eda ");
			// ADD 2015/06/17 E.kitazawa�yQP@40812�z  --- end
			ret.append("  AND T131.seq_shisaku = T331.seq_shisaku ");

			//ADD 2014/01/15 nishigawa �yQP@30154�z�ǉ��ۑ�No.11 start
			//���Z�@����i�Œ�`�F�b�N�j�iT332�j
			ret.append(" LEFT JOIN tr_shisan_shisaku_kotei T332 ");
			ret.append(" ON T131.cd_shain = T332.cd_shain ");
			ret.append(" AND T131.nen = T332.nen ");
			ret.append(" AND T131.no_oi = T332.no_oi ");
			// ADD 2015/06/17 E.kitazawa�yQP@40812�z  --- start
			ret.append(" AND T310.no_eda = T332.no_eda ");
			// ADD 2015/06/17 E.kitazawa�yQP@40812�z  --- end
			ret.append(" AND T131.seq_shisaku = T332.seq_shisaku ");
			//ADD 2014/01/15 nishigawa �yQP@30154�z�ǉ��ۑ�No.11 end

			//���Z�@�z���iT320�j
			ret.append(" INNER JOIN tr_shisan_haigo T320 ");
			ret.append("  ON T120.cd_shain = T320.cd_shain ");
			ret.append("  AND T120.nen = T320.nen ");
			ret.append("  AND T120.no_oi = T320.no_oi ");
			ret.append("  AND T120.cd_kotei = T320.cd_kotei ");
			ret.append("  AND T120.seq_kotei = T320.seq_kotei ");

			//�yQP@00342�z
			ret.append(" LEFT JOIN ma_user AS M110 ");
			ret.append("  ON T110.cd_eigyo = M110.id_user ");

			//ADD 2012.4.30�@�yH24�N�x�Ή��z�H�ꖼ(������)�A�������Z�����A�������Z����(�c�Ɨp)�ǉ�
			ret.append(" LEFT JOIN ma_busho AS M104 ");
			ret.append("  ON T310.cd_kaisha = M104.cd_kaisha ");
			ret.append("  AND T310.cd_kojo = M104.cd_busho ");
			ret.append(" LEFT JOIN tr_shisan_memo AS T311 ");
			ret.append("  ON T310.cd_shain = T311.cd_shain ");
			ret.append("  AND T310.nen = T311.nen ");
			ret.append("  AND T310.no_oi = T311.no_oi ");
			ret.append(" LEFT JOIN tr_shisan_memo_eigyo AS T312 ");
			ret.append("  ON T310.cd_shain = T312.cd_shain ");
			ret.append("  AND T310.nen = T312.nen ");
			ret.append("  AND T310.no_oi = T312.no_oi ");

			//ADD 2012.4.30�@�yH24�N�x�Ή��z�̔��ۊ���(�ʔNor�X�|�b�g)�A�̔��ۊ���(�P��)�A�����P��
			// MOD 2013/7/2 shima�yQP@30151�zNo.37 start
			ret.append("  LEFT JOIN ma_literal AS M302_Hanbai_T ON ");
			ret.append("   'hanbai_kikan_sentaku' = M302_Hanbai_T.cd_category ");
//			ret.append("   AND T310.kikan_hanbai_sen = M302_Hanbai_T.cd_literal ");
			ret.append("   AND T313_kihon_sub.kikan_hanbai_sen = M302_Hanbai_T.cd_literal ");
			ret.append("  LEFT JOIN ma_literal AS M302_Hanbai_K ON ");
			ret.append("   'hanbai_kikan_tani' = M302_Hanbai_K.cd_category ");
//			ret.append("   AND T310.kikan_hanbai_tani = M302_Hanbai_K.cd_literal ");
			ret.append("   AND T313_kihon_sub.kikan_hanbai_tani = M302_Hanbai_K.cd_literal ");

			ret.append("  LEFT JOIN ma_literal AS M302_TNI_G ON ");
			ret.append("   'K_tani_genka' = M302_TNI_G.cd_category ");
//			ret.append("   AND T310.cd_genka_tani = M302_TNI_G.cd_literal ");
			ret.append("   AND T313_kihon_sub.cd_genka_tani = M302_TNI_G.cd_literal ");
			// MOD 2013/7/2 shima�yQP@30151�zNo.37 end

			// ADD 2013/9/6 okano�yQP@30151�zNo.30 start
			// �z�蕨��(���i�P��)�A�z�蕨��(���ԒP��)
			ret.append("  LEFT JOIN ma_literal AS M302_Buturyo_U ON ");
			ret.append("   'sotei_buturyo_seihin' = M302_Buturyo_U.cd_category ");
			ret.append("   AND T313_kihon_sub.buturyo_seihin = M302_Buturyo_U.cd_literal ");
			ret.append("  LEFT JOIN ma_literal AS M302_Buturyo_K ON ");
			ret.append("   'sotei_buturyo_kikan' = M302_Buturyo_K.cd_category ");
			ret.append("   AND T313_kihon_sub.buturyo_kikan = M302_Buturyo_K.cd_literal ");
			// ADD 2013/9/6 okano�yQP@30151�zNo.30 end

			//ADD 2012.4.30�@�yH24�N�x�Ή��z�X�e�[�^�X�ύX�������A�������A�c�ƁA���Y�Ǘ����̂��̂��̍ŏI�X�V�S���Җ����擾
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



			//����SEQ1�`3�̔z���ʂ̎擾
			// MOD 2015/06/17 E.kitazawa�yQP@40812�z  --- start
//			for ( int i=0; i<3; i++ ) {
			for ( int i=0; i<reqCnt; i++ ) {
			// MOD 2015/06/17 E.kitazawa�yQP@40812�z  --- end

				//����SEQ�̎擾	seq_shisaku
				try{
					strShisakuSeq = toString(reqData.getFieldVale(0, i, "seq_shisaku"));

				}catch(Exception e){
					strShisakuSeq = "";

				}

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
			//�yQP@00342�z
//			ret.append(" WHERE T110.cd_shain = " + strCd_shain);
//			ret.append("  AND T110.nen = " + strNen);
//			ret.append("  AND T110.no_oi = " + strNo_oi);
			//�i�荞�݁i���Z�@����i�j
			ret.append(" WHERE ");
			ret.append(" T310.cd_shain = " + strCd_shain);
			ret.append(" AND T310.nen = " + strNen);
			ret.append(" AND T310.no_oi = " + strNo_oi);
			ret.append(" AND T310.no_eda = " + strNo_eda);
			//�i�荞�݁i���Z�@�z���j
			ret.append(" AND T320.cd_shain = " + strCd_shain);
			ret.append(" AND T320.nen = " + strNen);
			ret.append(" AND T320.no_oi = " + strNo_oi);
			ret.append(" AND T320.no_eda = " + strNo_eda);
			//�i�荞�݁i���Z�@����j
			ret.append(" AND T331.cd_shain = " + strCd_shain);
			ret.append(" AND T331.nen = " + strNen);
			ret.append(" AND T331.no_oi = " + strNo_oi);
			ret.append(" AND T331.no_eda = " + strNo_eda);

			//����SEQ
			ret.append("  AND T131.seq_shisaku IN ( ");

			// MOD 2015/06/17 E.kitazawa�yQP@40812�z  --- start
//			for ( int i=0; i<3; i++ ) {
			for ( int i=0; i<reqCnt; i++ ) {
			// MOD 2015/06/17 E.kitazawa�yQP@40812�z  --- end

				//����SEQ�̎擾
				try{
					strShisakuSeq = toString(reqData.getFieldVale(0, i, "seq_shisaku"));

				}catch(Exception e){
					strShisakuSeq = "";

				}

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
			// MOD 2015/06/17 E.kitazawa�yQP@40812�z  --- start
//			for ( int i=0; i<3; i++ ) {
			for ( int i=0; i<reqCnt; i++ ) {
			// MOD 2015/06/17 E.kitazawa�yQP@40812�z  --- end

				//����SEQ�̎擾
				try{
					strShisakuSeq = toString(reqData.getFieldVale(0, i, "seq_shisaku"));

				}catch(Exception e){
					strShisakuSeq = "";

				}

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

// 20160524  KPX@1600766 ADD start
	/**
	 * �P���J�������擾
	 * @param reqData : ���N�G�X�g�f�[�^�i�@�\�j
	 * @return int  : �P���J������ �i9�F�P���J���i�S�āj 1�F�P���J������   0�F�P���J���s�j
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private int getTankaHyouji_kengen(RequestResponsKindBean reqData
			,UserInfoData _userInfoData)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		//��������
		int ret = 0;			//�P���J���s��

		//���[�U�[���ޔ�
		userInfoData = _userInfoData;

		//���[�U�̕����t���O�擾
		FGEN2130_Logic flgBushoSearch = null;
		//��������
		RequestResponsKindBean resKind = null;

		//���e�����}�X�^�����N���X
		LiteralDataSearchLogic clsLiteralSearch = null;
		//���e�����}�X�^���N�G�X�g
		RequestResponsKindBean reqLiteral = null;
		//���e�����}�X�^�������X�|���X
		RequestResponsKindBean resLiteral = null;

		//���[�U�����t���O�i�������j
		String flg_kenkyu = "";
		//�P���J���t���O�i���e�����}�X�^�j
		String value1 = "";

		try {
			//���[�U�̕����t���O�擾
			flgBushoSearch = new FGEN2130_Logic();
			resKind = flgBushoSearch.ExecLogic(reqData, userInfoData);
			if (resKind != null) {
				flg_kenkyu = toString(resKind.getFieldVale(0, 0, "flg_kenkyu"));
			}

		} catch (Exception e) {

		} finally {
			//�ϐ��̍폜
			resKind = null;
		}

		if (flg_kenkyu.equals("")) {
			//�������ȊO�͒P���J������͂��Ȃ�
			ret = 9;
		} else {
			try {
				//���N�G�X�g�C���X�^���X
				reqLiteral = new RequestResponsKindBean();
				//���e�����}�X�^�������N�G�X�g����
				reqLiteral.addFieldVale("table", "rec", "cd_category", "K_tanka_hyoujigaisha");
				//��ЃR�[�h
				reqLiteral.addFieldVale("table", "rec", "cd_literal", toString(reqData.getFieldVale(0, 0, "cd_kaisya")));

				//���e�����}�X�^�����F�P���J������
				clsLiteralSearch = new LiteralDataSearchLogic();
				resLiteral = clsLiteralSearch.ExecLogic(reqLiteral, userInfoData);

				if (resLiteral != null) {
					value1 = toString(resLiteral.getFieldVale(0, 0, "value1"));
				}

				if (value1.equals("1") || value1.equals("9")) {
					ret = Integer.parseInt(value1);		//�P���J�����A�P���J���i�S�āj
				}

			} catch (Exception e) {
				//�Y���f�[�^�����̎��͊J���s��
				ret = 0;

			} finally {
				//�ϐ��̍폜
				reqLiteral = null;
				resLiteral = null;
			}
		}
		return ret;
	}

	/**
	 * �������Z�f�[�^��P���J���ҏW
	 * @param objItems : �������Z�f�[�^1�s��
	 * @param flg      : �P���J������ �i9�F�P���J���i�S�āj 1�F�P���J������   0�F�P���J���s�j
	 * @return Object[]  : �������Z�f�[�^
	 */
	private Object[] setObj(Object[] objItems ,int flg) {

		//�P���J���i�S�āj�̎��A���̂܂ܕԂ�
		if (flg == 9)	return objItems;

		//��\�����ڂ�0�ɒu������
		//�Œ��
		objItems[36] = 0;
		//�ޗ���/�P�[�X
		objItems[62] = 0;
		//�ޗ���/�j�f
		objItems[66] = 0;
		//�Œ��/�j�f
		objItems[70] = 0;

		//�P���J���s�̏ꍇ
		if (flg == 0) {
			//�����P��
			objItems[12] = 0;
			//��������
			objItems[13] = 0;

			//�L������
			objItems[35] = 0;
			//���x����(��)
			objItems[59] = 0;
			//���Ϗ[�U��
			objItems[34] = 0;
			//��d���Z�ʁi���j
			objItems[60] = 0;
			//������/�P�[�X
			objItems[61] = 0;
			//������/�j�f
			objItems[65] = 0;
		}

		return objItems;
	}
// 20160524  KPX@1600766 ADD end

	/**
	 * ���p�L����S�p�ɕϊ�
	 * @param str : �ϊ��Ώە�����
	 * @return String  : �ϊ��㕶����
	 */
	private String replaceSymbol(String str) {
		str = str.replace("%", "��");
		str = str.replace("=", "��");
		str = str.replace("?", "�H");
		str = str.replace("&", "��");
		str = str.replace("#", "��");

		return str;
	}
}