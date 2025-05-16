package jp.co.blueflag.shisaquick.srv.logic_gencho;

import java.util.List;

import jp.co.blueflag.shisaquick.srv.base.LogicBase;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsKindBean;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsTableBean;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;

/**
 * ���ޏ��X�V����
 *  �@�\ID�FFGEN3360�@
 *
 * @author t2nakamura
 * @since  2016/10/28
 */
public class FGEN3700_Logic extends LogicBase {

	/**
	 * ���ޏ��X�V�R���X�g���N�^
	 * : �C���X�^���X����
	 */
	public FGEN3700_Logic(){
		super();
	}

	/**
	 * ���ގ�z���X�V �Ǘ�����
	 * @param reqData : �@�\���N�G�X�g�f�[�^
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

		//���X�|���X�f�[�^�N���X
		RequestResponsKindBean resKind = null;

		try {
			//���X�|���X�f�[�^�i�@�\�j����
			resKind = new RequestResponsKindBean();

			// �g�����U�N�V�����J�n
			super.createExecDB();
			execDB.BeginTran();

			//DB�R�l�N�V����
			createSearchDB();

			// �X�V����
			updateSQL(reqData);

			// �R�~�b�g
			execDB.Commit();

			//�@�\ID�̐ݒ�
			String strKinoId = reqData.getID();
			resKind.setID(strKinoId);

			//�e�[�u�����̐ݒ�
			String strTableNm = reqData.getTableID(0);
			resKind.addTableItem(strTableNm);

			// ���X�|���X�f�[�^�̊i�[
			storageData(resKind.getTableItem(0));


		} catch (Exception e) {
			// ���[���o�b�N
			execDB.Rollback();
			this.em.ThrowException(e, "���ޏ��X�VDB�����Ɏ��s���܂����B");

		} finally {
			//�Z�b�V�����̃N���[�Y
			if (execDB != null) {
				execDB.Close();
				execDB = null;
			}
			if (searchDB != null) {
				searchDB.Close();
				searchDB = null;
			}
		}
		return resKind;
	}

	/**
	 * �X�V����
	 *  : ���ގ�z���̍X�V���s��
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */

	//==========================================================================================
	//���e�[�v���ɕۑ�
	// SQL�쐬

	//=========================================================================================
	private void updateSQL(
			RequestResponsKindBean reqData)
					throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		StringBuffer strSQL;
		List<?> lstRecset;
		List<?> lstRecset2;
		List<?> lstRecset3;
		String allkey = "";
		try {
			allkey = getALLKey(reqData);

			for(int i = 0;i < reqData.getCntRow(reqData.getTableID(0)); i++){


				//���N�G�X�g�f�[�^��莎��R�[�h�擾
				// �Ј��R�[�h
				String strReqShain   = toString(reqData.getFieldVale(0, i, "insert_cd_shain"));

				// �N
				String strReqNen     = toString(reqData.getFieldVale(0, i, "insert_nen"));
				// �ǔ�
				String strReqNoOi    = toString(reqData.getFieldVale(0, i, "insert_no_oi"));
				// seq�ԍ�
				String strSeqShizai    = toString(reqData.getFieldVale(0, i, "insert_seq_shizai"));
				// �}��
				String strReqNoEda   = toString(reqData.getFieldVale(0, i, "insert_no_eda"));
				// �敪
				String strKbnShizai = toString(reqData.getFieldVale(0, 0, "insert_kbn_shizai"));
				// ��
				String strHan = toString(reqData.getFieldVale(0, i, "insert_han"));
				// ������1
				String strCdLiteral1   = toString(reqData.getFieldVale(0, 0, "insert_cd_literal1"));
				int intCdLiteral1 = 0;
				if (!strCdLiteral1.equals("")) {
					intCdLiteral1 = Integer.parseInt(strCdLiteral1);
				}
				// �S����1
				String strCd2ndLiteral1  = toString(reqData.getFieldVale(0, 0, "insert_cd_2nd_literal1"));
				// ������Q
				String strCdLiteral2 = toString(reqData.getFieldVale(0, 0, "insert_cd_liiteral2"));
				int intCdLiteral2 = 0;
				if (!strCdLiteral2.equals("")) {
					intCdLiteral2 = Integer.parseInt(strCdLiteral2);
				}
				// �S���҂Q
				String strCd2ndLiteral2 = toString(reqData.getFieldVale(0, 0,"insert_cd_2nd_literal2"));
				// ���e
				String strNaiyo = toString(reqData.getFieldVale(0, i, "insert_naiyo"));
				// ���i�R�[�h
				String strCdShohin = toString(reqData.getFieldVale(0, i, "insert_cd_shohin"));
				String strCdShohinFormat = get0SupressCode(strCdShohin);
				// ���i��
				String strNmShohin = toString(reqData.getFieldVale(0, i, "insert_nm_shohin"));
				// �׎p
				String strNisugata = toString(reqData.getFieldVale(0, i, "insert_nisugata"));
				// �Ώێ���
				String strTaisyoshizai = toString(reqData.getFieldVale(0, 0, "insert_cd_taisyoshizai"));
				// �[����
				String strNounyudaki = toString(reqData.getFieldVale(0, i, "insert_nounyusaki"));
				// �����ރR�[�h
				String strCdShizai = toString(reqData.getFieldVale(0, i, "insert_cd_shizai"));
				// �V���ރR�[�h1
				String strCdShizaiNew = toString(reqData.getFieldVale(0, i, "insert_cd_shizai_new"));
				// �d�l�ύX
				String strShiyohenko = toString(reqData.getFieldVale(0, i, "insert_shiyohenko"));
				// �݌v�P
				String strSekkei1    = toString(reqData.getFieldVale(0, 0, "sekkei1"));
				// �݌v�Q
				String strSekkei2    = toString(reqData.getFieldVale(0, 0, "sekkei2"));
				// �݌v�R
				String strSekkei3    = toString(reqData.getFieldVale(0, 0, "sekkei3"));
				// �ގ�
				String strZaishitsu  = toString(reqData.getFieldVale(0, 0, "zaishitsu"));
				// ���l
				String strBiko = toString(reqData.getFieldVale(0, 0, "biko"));
				// ����F
				String strPrintColor = toString(reqData.getFieldVale(0, 0, "printcolor"));
				// �F�ԍ�
				String strNoColor    = toString(reqData.getFieldVale(0, 0, "no_color"));
				// �ύX���e�ڍ�
				String strHenkounaiyou = toString(reqData.getFieldVale(0, 0, "henkounaiyoushosai"));
				// �[��
				String strNouki = toString(reqData.getFieldVale(0, 0, "nouki"));
				// ����
				String strSuryo = toString(reqData.getFieldVale(0, 0, "suryo"));
				// �����ލ݌�
				String strOldShizaizaiko = toString(reqData.getFieldVale(0, 0, "old_sizaizaiko"));
				// ����
				String strRakuhan = toString(reqData.getFieldVale(0, 0, "rakuhan"));
				// �\�����e
				String strHyojiNaiyo = toString(reqData.getFieldVale(0, 0, "hyoji_naiyo"));
				// �\���׎p
				String strHyojiNisugata = toString(reqData.getFieldVale(0, 0, "hyoji_nisugata"));
				// �\���[����
				String strHyojiNounyusaki = toString(reqData.getFieldVale(0, 0, "hyoji_nounyusaki"));
				// �\�������ރR�[�h
				String strHyojiCdShizai = toString(reqData.getFieldVale(0, 0, "hyoji_cd_shizai"));
				// �\���V���ރR�[�h
				String strHyojiCdShizaiNew = toString(reqData.getFieldVale(0, 0, "hyoji_cd_shizai_new"));

				// �ő�x����
				String strHandaiPayDay = toString(reqData.getFieldVale(0, i, "insert_dt_han_payday"));
				// �ő�
				String strHandai = toString(reqData.getFieldVale(0, i, "insert_file_han_pay"));
				// �ăt�@�C����
				String strAoyakiFileName = toString(reqData.getFieldVale(0, i, "insert_nm_file_aoyaki"));
				// �ăt�@�C���p�X
				String strAoyakiFilePath = toString(reqData.getFieldVale(0, i, "insert_file_path_aoyaki"));
				// ���l�ɕϊ�
				int shainCd = Integer.parseInt(strReqShain);
				int nen = Integer.parseInt(strReqNen);
				int noOi = Integer.parseInt(strReqNoOi);
				int seqShizai = Integer.parseInt(strSeqShizai);
				int noEda = Integer.parseInt(strReqNoEda);

				StringBuffer strInsertSQL1 = new StringBuffer();

				strInsertSQL1.append(" SELECT ");
				strInsertSQL1.append(" cd_shain");
				strInsertSQL1.append(" FROM ");
				strInsertSQL1.append("    tr_shizai_tehai_temp ");
				strInsertSQL1.append(" WHERE ");
				strInsertSQL1.append("        cd_shain = " + shainCd);
				strInsertSQL1.append("    AND nen = " + nen);
				strInsertSQL1.append("    AND no_oi = " + noOi);
				strInsertSQL1.append("    AND no_eda = " + noEda);
				strInsertSQL1.append("    AND seq_shizai = " + seqShizai);
				strInsertSQL1.append("    AND kbn_shizai = "+ strKbnShizai);
				strInsertSQL1.append("    AND cd_tmp_group_key = '"+ allkey + "'");

				//���ʃN���X�@�f�[�^�x�[�X�Ǘ���p���ASQL�����s
				lstRecset = this.searchDB.dbSearch_notError(strInsertSQL1.toString());

				StringBuffer strInsertSQL2 = new StringBuffer();
				if (lstRecset.size() != 0) {

					//SELECT���쐬

					strInsertSQL2.append(" DELETE ");
					strInsertSQL2.append(" FROM ");
					strInsertSQL2.append("    tr_shizai_tehai_temp ");
					strInsertSQL2.append("   WHERE");
					strInsertSQL2.append("        cd_shain = " + shainCd);
					strInsertSQL2.append("    AND nen = " + nen);
					strInsertSQL2.append("    AND no_oi = " + noOi);
					strInsertSQL2.append("    AND no_eda = " + noEda);
					strInsertSQL2.append("    AND seq_shizai = " + seqShizai);
					strInsertSQL2.append("    AND kbn_shizai = "+ strKbnShizai);
					strInsertSQL2.append("    AND cd_tmp_group_key = '"+ allkey + "'");
					//���ʃN���X�@�f�[�^�x�[�X�Ǘ���p���ASQL�����s
					execDB.execSQL(strInsertSQL2.toString());
				}
				StringBuffer strInsertSQL = new StringBuffer();

				strInsertSQL.append("INSERT INTO tr_shizai_tehai_temp (");
				strInsertSQL.append(" cd_tmp_group_key");
				strInsertSQL.append(", cd_shain");
				strInsertSQL.append(", nen");
				strInsertSQL.append(", no_oi");
				strInsertSQL.append(", seq_shizai");
				strInsertSQL.append(", no_eda");
				strInsertSQL.append(", kbn_shizai");
				strInsertSQL.append(", han");
				strInsertSQL.append(", cd_literal_1");
				strInsertSQL.append(", cd_2nd_literal_1");
				strInsertSQL.append(", cd_literal_2");
				strInsertSQL.append(", cd_2nd_literal_2");
				strInsertSQL.append(", naiyo");
				strInsertSQL.append(", cd_shohin");
				strInsertSQL.append(", nm_shohin");
				strInsertSQL.append(", nisugata");
				strInsertSQL.append(", cd_taisyoshizai");
				strInsertSQL.append(", nounyusaki");
				strInsertSQL.append(", cd_shizai");
				strInsertSQL.append(", cd_shizai_new");
				strInsertSQL.append(", shiyohenko");
				strInsertSQL.append(", sekkei1");
				strInsertSQL.append(", sekkei2");
				strInsertSQL.append(", sekkei3");
				strInsertSQL.append(", zaishitsu");
				strInsertSQL.append(", biko_tehai");
				strInsertSQL.append(", printcolor");
				strInsertSQL.append(", no_color");
				strInsertSQL.append(", henkounaiyoushosai");
				strInsertSQL.append(", nouki");
				strInsertSQL.append(", suryo");
				strInsertSQL.append(", old_sizaizaiko");
				strInsertSQL.append(", rakuhan");
				strInsertSQL.append(", hyoji_naiyo");
				strInsertSQL.append(", hyoji_nisugata");
				strInsertSQL.append(", hyoji_nounyusaki");
				strInsertSQL.append(", hyoji_cd_shizai");
				strInsertSQL.append(", hyoji_cd_shizai_new");
				strInsertSQL.append(", dt_han_payday");	// �ő�x����
				strInsertSQL.append(", han_pay");	// �ő�
				strInsertSQL.append(", nm_file_aoyaki");	// �Ă��t�@�C����
				strInsertSQL.append(", file_path_aoyaki");	// �Ă��t�@�C���p�X
				strInsertSQL.append(", id_toroku");
				strInsertSQL.append(", dt_toroku");
				strInsertSQL.append(", id_koshin");
				strInsertSQL.append(", dt_koshin");
				strInsertSQL.append(", flg_tehai_status");
				strInsertSQL.append(" ) VALUES ( ");
				strInsertSQL.append(  "'" + allkey + "'");				// key
				strInsertSQL.append("  ," +  strReqShain );			// �Ј��R�[�h
				strInsertSQL.append("  ," + strReqNen);					// �N
				strInsertSQL.append("  ," + strReqNoOi);				// �ǔ�
				strInsertSQL.append("  ," + strSeqShizai);				// seq�ԍ�
				strInsertSQL.append("  ," + strReqNoEda);				// �}��
				strInsertSQL.append("  ," + strKbnShizai);				// �敪
				strInsertSQL.append("  ," + strHan);					// ��
				if (!strCd2ndLiteral1.equals("")) {
					strInsertSQL.append("  ,CONVERT(int, " + intCdLiteral1 + ")");		// ������1
				} else {
					strInsertSQL.append("  ,'" + strCdLiteral1 + "'");		// ������1
				}
				strInsertSQL.append("  ,'" + strCd2ndLiteral1 + "'");	// �S���҂P
				if (!strCd2ndLiteral2.equals("")) {
					strInsertSQL.append("  ,CONVERT(int, " + intCdLiteral2 + ")");		// ������Q
				} else {
					strInsertSQL.append("  ,'" + strCdLiteral2 + "'");		// ������Q
				}
				strInsertSQL.append("  ,'" + strCd2ndLiteral2 + "'");	// �S���҂Q
				strInsertSQL.append("  ,'" + strNaiyo + "'");			// ���e
				strInsertSQL.append("  ,'" + strCdShohinFormat + "'");		// ���i�R�[�h
				strInsertSQL.append("  ,'" + strNmShohin + "'");		// ���i��
				strInsertSQL.append("  ,'" + strNisugata + "'");		// �׎p
				strInsertSQL.append("  ,'" + strTaisyoshizai + "'");	// �Ώێ���
				strInsertSQL.append("  ,'" + strNounyudaki + "'");		// �[����
				strInsertSQL.append("  ,'" + strCdShizai + "'");		// �����ރR�[�h
				strInsertSQL.append("  ,'" + strCdShizaiNew + "'");		// �V���ރR�[�h
				strInsertSQL.append("  ," + strShiyohenko);				// �d�l�ύX
				strInsertSQL.append("  ,'" + strSekkei1 + "'");			// �݌v�P
				strInsertSQL.append("  ,'" + strSekkei2 + "'");			// �݌v�Q
				strInsertSQL.append("  ,'" + strSekkei3 + "'");			// �݌v�R
				strInsertSQL.append("  ,'" + strZaishitsu + "'");		// �ގ�
				strInsertSQL.append("  ,'" + strBiko + "'");			// ���l
				strInsertSQL.append("  ,'" + strPrintColor + "'");		// ����F
				strInsertSQL.append("  ,'" + strNoColor + "'");			// �F�ԍ�
				strInsertSQL.append("  ,'" + strHenkounaiyou + "'");	// �ύX���e�ڍ�
				strInsertSQL.append("  ,'" + strNouki + "'");			// �[��
				strInsertSQL.append("  ,'" + strSuryo + "'");			// ����
				strInsertSQL.append("  ,'" + strOldShizaizaiko +"'");	// �����ލ݌�
				strInsertSQL.append("  ,'" + strRakuhan + "'");			// ����
				// �\������Ă��鍀��
				strInsertSQL.append("  ,'" + strHyojiNaiyo + "'");		// �\�����e
				strInsertSQL.append("  ,'" + strHyojiNisugata + "'");	// �\���׎p
				strInsertSQL.append("  ,'" + strHyojiNounyusaki + "'");	// �\���[����
				strInsertSQL.append(",  '" + strHyojiCdShizai + "'");	// �\�������ރR�[�h
				strInsertSQL.append("  ,'" + strHyojiCdShizaiNew + "'");// �\�����ރR�[�h
				//�ǉ�
				strInsertSQL.append("  ,NULL");// �ő�x����
				strInsertSQL.append("  ,NULL");// �ő�
				strInsertSQL.append("  ,NULL");// �Ă��t�@�C����
				strInsertSQL.append("  ,NULL");// �Ă��t�@�C���p�X
//				strInsertSQL.append("  ,'" + strHandaiPayDay + "'");// �ő�x����
//				strInsertSQL.append("  ,'" + strHandai + "'");// �ő�
//				strInsertSQL.append("  ,'" + strAoyakiFileName + "'");// �Ă��t�@�C����
//				strInsertSQL.append("  ,'" + strAoyakiFilePath + "'");// �Ă��t�@�C���p�X
				strInsertSQL.append("  ," + userInfoData.getId_user());	// �o�^��ID
				strInsertSQL.append("  ,  GETDATE()");					// �o�^���t
				strInsertSQL.append("  ," + userInfoData.getId_user());	// �X�V��ID
				strInsertSQL.append("  , + GETDATE()");					// �X�V���t
				strInsertSQL.append("  , 2)");							//�X�e�[�^�X
				execDB.execSQL(strInsertSQL.toString());				// SQL���s

				// ���ގ�z�e�[�u���C���T�[�g

				//SELECT���쐬
				strSQL = new StringBuffer();
				strSQL.append(" SELECT ");
				strSQL.append("   cd_shain ");
				strSQL.append(" FROM ");
				strSQL.append("    tr_shizai_tehai ");
				strSQL.append(" WHERE ");
				strSQL.append("        cd_shain = " + shainCd);
				strSQL.append("    AND nen = " + nen);
				strSQL.append("    AND no_oi = " + noOi);
				strSQL.append("    AND no_eda = " + noEda);
				strSQL.append("    AND seq_shizai = " + seqShizai);
//				//2016.11.23 ���������Ώۂ̋敪��ύX����̂͂܂����̂Œǉ��i�����������͎��ގ�z�̃f�[�^�͍X�V���ꂸ�f�[�^�͍X�V����Ȃ��B
//				strSQL.append("    AND flg_tehai_status < 3 ");

				//���ʃN���X�@�f�[�^�x�[�X�Ǘ���p���ASQL�����s
				lstRecset2 = this.searchDB.dbSearch_notError(strSQL.toString());



				// ���Y���ރe�[�u���Ƀf�[�^�����݂���ꍇ�͍X�V����
				if(lstRecset2.size() != 0){
					//���ރe�[�u���̍X�V�́A���ގ�z�ς݂̃X�e�[�^�X�����ގ�z�ς݁i�R�j�łȂ����Ƃ��O��
					//�h�e�������������B
					StringBuffer strShisan = new StringBuffer();

					// ���Y���ރe�[�u���X�V
					strShisan.append("SELECT ");
					strShisan.append("  cd_shain");
					strShisan.append(" FROM  tr_shisan_shizai");
					strShisan.append(" WHERE ");
					strShisan.append("        cd_shain = " + shainCd);
					strShisan.append("    AND nen = " + nen);
					strShisan.append("    AND no_oi = " + noOi);
					strShisan.append("    AND no_eda = " + noEda);
					strShisan.append("    AND seq_shizai = " + seqShizai);
					strShisan.append("    AND cd_seihin = " + strCdShohinFormat);

					//���Y���ނɃf�[�^�L���m�F�́ASQL�����s
					lstRecset3 = this.searchDB.dbSearch_notError(strShisan.toString());

					if (lstRecset3.size() != 0 ) {

						// �X�e�[�^�X�̍X�V
						// SQL�쐬
						strSQL = new StringBuffer();
						strSQL.append(" UPDATE ");
						strSQL.append("   tr_shizai_tehai ");
						strSQL.append(" SET ");
						strSQL.append("   flg_tehai_status = 2 ");
						strSQL.append(" WHERE ");
						strSQL.append("        cd_shain = " + shainCd);
						strSQL.append("    AND nen = " + nen);
						strSQL.append("    AND no_oi = " + noOi);
						strSQL.append("    AND no_eda = " + noEda);
						strSQL.append("    AND seq_shizai = " + seqShizai);
						strSQL.append("    AND flg_tehai_status < 3 ");

						//���ʃN���X�@�f�[�^�x�[�X�Ǘ���p���ASQL�����s
						this.execDB.execSQL(strSQL.toString());

						// SQL�쐬
						strSQL = new StringBuffer();
						strSQL.append(" UPDATE ");
						strSQL.append("   tr_shizai_tehai ");
						strSQL.append(" SET ");
						strSQL.append("   naiyo   = '" + strHyojiNaiyo + "',");				// ���e�i���e�̓��͕͂\���p���e�Ƃ���i2016.11.23�j�j
						strSQL.append("   nm_shohin   = '" + strNmShohin + "',");			// ���i��
						strSQL.append("   nounyusaki   = '" + strNounyudaki + "',");		// �[����
						String strCdShizaiFormat = get0SupressShizaiCd(strCdShizai);
						strSQL.append("   cd_shizai   = '" + strCdShizaiFormat + "',");			// �����ރR�[�h
						String strCdShizaiNewFormat = get0SupressShizaiCd(strCdShizaiNew);
						strSQL.append("   cd_shizai_new   = '" + strCdShizaiNewFormat + "',");	// �V���ރR�[�h
						strSQL.append("   sekkei1 = '" + strSekkei1 + "', ");				// �݌v�P
						strSQL.append("   sekkei2 = '" + strSekkei2 + "', ");				// �݌v�Q
						strSQL.append("   sekkei3 = '" + strSekkei3 + "', ");				// �݌v�R
						strSQL.append("   zaishitsu = '" + strZaishitsu + "', ");			// �ގ�
						strSQL.append("   biko_tehai = '" + strBiko + "', ");					// ���l
						strSQL.append("   printcolor = '" + strPrintColor + "', ");			// ����F
						strSQL.append("   no_color = '" + strNoColor + "', ");				// �F�ԍ�
						strSQL.append("   henkounaiyoushosai = '" + strHenkounaiyou + "', ");	// �ύX���e�ڍ�
						strSQL.append("   nouki = '" + strNouki + "', ");						// �[��
						strSQL.append("   id_koshin = '" + userInfoData.getId_user() + "', ");//�X�V���[�U�i�ǉ��j
						strSQL.append("   rakuhan = '" + strRakuhan+ "', ");//���Œǉ�
						strSQL.append("   old_sizaizaiko = '" + strOldShizaizaiko + "', ");//������
						strSQL.append("   suryo='" + strSuryo + "' ");						// ����
						strSQL.append(" WHERE ");
						strSQL.append("        cd_shain = " + shainCd);
						strSQL.append("    AND nen = " + nen);
						strSQL.append("    AND no_oi = " + noOi);
						strSQL.append("    AND no_eda = " + noEda);
						strSQL.append("    AND seq_shizai = " + seqShizai);
						strSQL.append("    AND cd_shohin  = '" + strCdShohinFormat + "'");
//						//2016.11.23 ���������Ώۂ̋敪��ύX����̂͂܂����̂Œǉ��i�����������͎��ގ�z�̃f�[�^�͍X�V���ꂸ�f�[�^�͍X�V����Ȃ��B
//						strSQL.append("    AND flg_tehai_status < 3 ");
						//���ʃN���X�@�f�[�^�x�[�X�Ǘ���p���ASQL�����s
						this.execDB.execSQL(strSQL.toString());



						// �X�e�[�^�X�̍X�V
						// SQL�쐬
						strShisan = new StringBuffer();
						strShisan.append(" UPDATE ");
						strShisan.append("   tr_shisan_shizai ");
						strShisan.append(" SET ");
						strShisan.append("   cd_hattyu = " + userInfoData.getId_user() + ", ");
						strShisan.append("  cd_hattyusaki = '" +  strCdLiteral1 + "', ");//������R�[�h
						strShisan.append("  cd_taisyoshizai = '" +  strTaisyoshizai + "',");//�Ώێ��ރR�[�h
						strShisan.append("   id_koshin = '" + userInfoData.getId_user() + "', ");
						strShisan.append("   dt_koshin = GETDATE() ");
						strShisan.append(" WHERE ");
						strShisan.append("        cd_shain = " + shainCd);
						strShisan.append("    AND nen = " + nen);
						strShisan.append("    AND no_oi = " + noOi);
						strShisan.append("    AND no_eda = " + noEda);
						strShisan.append("    AND seq_shizai = " + seqShizai);
						strShisan.append("    AND cd_seihin = " + strCdShohinFormat);

						this.execDB.execSQL(strShisan.toString());
					}
				}
				// �N���A
				strSQL = null;
			}

		} catch (Exception e) {

			em.ThrowException(e, "���ޏ��X�VDB�����Ɏ��s���܂����B");

		} finally {

		}
	}
	/**
	 * �e���|�����[�p�L�[�쐬
	 * @param reqData
	 * @return
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private String getALLKey(RequestResponsKindBean reqData)
			throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		String allkey = "";
		for(int j = 0;j < reqData.getCntRow(reqData.getTableID(0)); j++){
			//���N�G�X�g�f�[�^��莎��R�[�h�擾
			// �Ј��R�[�h
			String strReqShain   = toString(reqData.getFieldVale(0, j, "insert_cd_shain"));
			// �N
			String strReqNen     = toString(reqData.getFieldVale(0, j, "insert_nen"));
			// �ǔ�
			String strReqNoOi    = toString(reqData.getFieldVale(0, j, "insert_no_oi"));
			// seq�ԍ�
			String strSeqShizai    = toString(reqData.getFieldVale(0, j, "insert_seq_shizai"));
			// �}��
			String strReqNoEda   = toString(reqData.getFieldVale(0, j, "insert_no_eda"));
			// ���i�R�[�h
			String strCdShohin = toString(reqData.getFieldVale(0, j, "insert_cd_shohin"));

			int intShainCd = Integer.parseInt(strReqShain);
			String shainCd = String.valueOf(intShainCd);

			int intNen = Integer.parseInt(strReqNen);
			String nen = String.valueOf(intNen);

			int intNoOi = Integer.parseInt(strReqNoOi);
			String noOi =String.valueOf(intNoOi);

			int intSeqShizai = Integer.parseInt(strSeqShizai);
			String seqShizai = String.valueOf(intSeqShizai);

			int intNoEda = Integer.parseInt(strReqNoEda);
			String noEda = String.valueOf(intNoEda);


			String tempkey = shainCd + "_" + nen + "_" +  noOi
					 + "_" + seqShizai + "_" + noEda + "_" + strCdShohin + "_" ;
			allkey += tempkey;
		}
		return allkey;
	}


	/**
     * �p�����[�^�[�i�[
     *  : ���ގ�z�˗����o�͉�ʂւ̃��X�|���X�f�[�^�֊i�[����B
     * @param lstGenkaHeader : �������ʏ�񃊃X�g
     * @throws ExceptionWaning
     * @throws ExceptionUser
     * @throws ExceptionSystem
     */
	private void storageData(RequestResponsTableBean resTable) throws ExceptionSystem,
			ExceptionUser, ExceptionWaning {

		try {
			//�������ʂ̊i�[
			resTable.addFieldVale(0, "flg_return", "true");
			resTable.addFieldVale(0, "msg_error", "");
			resTable.addFieldVale(0, "no_errmsg", "");
			resTable.addFieldVale(0, "nm_class", "");
			resTable.addFieldVale(0, "cd_error", "");
			resTable.addFieldVale(0, "msg_system", "");
			resTable.addFieldVale(0, "msg_cd", "0");

		} catch (Exception e) {
			this.em.ThrowException(e, "���ޏ��X�VDB�����Ɏ��s���܂����B");

		} finally {

		}
	}
	/**
	 * 0�T�v���X����
	 * @param strCdShohin
	 * @return
	 */
	public String  get0SupressCode(String strCdShohin) {
		int intCdShohin = 0;
		if (!strCdShohin.equals("")) {
			 intCdShohin = Integer.parseInt(strCdShohin);
		}
		return String.valueOf(intCdShohin);
	}

	/**
	 * 0�T�v���X����
	 * @param str
	 * @return convertShizai
	 */
	private String get0SupressShizaiCd(String str) {
		int intCdShizai = 0;
		if (!str.equals("")) {
			 intCdShizai = Integer.parseInt(str);
		}
		return String.valueOf(intCdShizai);
	}

}
