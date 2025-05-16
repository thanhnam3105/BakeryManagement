package jp.co.blueflag.shisaquick.srv.logic_gencho;

import java.util.List;

import jp.co.blueflag.shisaquick.srv.base.LogicBaseJExcel;
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
 * @author TT.Shima
 * @since  2014/10/07
 */
public class FGEN3380_Logic extends LogicBaseJExcel {

	/**
	 * ���ޏ��X�V�R���X�g���N�^
	 * : �C���X�^���X����
	 */
	public FGEN3380_Logic(){
		super();
	}

	/**
	 * ���ޏ����X�V����
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
	private void updateSQL(
			RequestResponsKindBean reqData)
					throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		StringBuffer strSQL;
		List<?> lstRecset;
		List<?> lstRecset2 = null;
		int shizaiteahi_id_hukusuuMax = 0;
		try {
			shizaiteahi_id_hukusuuMax = getMaxIdHukusuuString();

			for(int i = 0;i < reqData.getCntRow(reqData.getTableID(0)); i++){
				boolean shizai_tehai_dataAri = false;
				boolean shiazn_shizai_dataAri= false;

				String strKbn = reqData.getFieldVale(0, 0, "kbn");
				// �Ј��R�[�h
				String strCdShain = reqData.getFieldVale(0, i, "cd_shain");
				// �N�R�[�h
				String strNen = reqData.getFieldVale(0, i, "nen");
				// �ǔ�
				String strNooi = reqData.getFieldVale(0, i, "no_oi");
				// seq����
				String strSeqShizai = reqData.getFieldVale(0, i, "seq_shizai");
				// �}��
				String strNoeda = reqData.getFieldVale(0, i, "no_eda");
				// ���i�R�[�h
				String strCdShohin = toString(reqData.getFieldVale(0, i, "cd_shohin"));
				// ������R�[�h
				String strHattyuusakiCd = toString(reqData.getFieldVale(0, 0, "cd_hattyuusaki"));
				// �Ώێ��ރR�[�h
				String strTaisyosizaiCd = toString(reqData.getFieldVale(0, 0, "cd_taisyoshizai"));

				String strCdShohinFormat = get0SupressCode(strCdShohin);
				// ���l�ɕϊ�
				int shainCd = Integer.parseInt(strCdShain);
				int nen = Integer.parseInt(strNen);
				int noOi = Integer.parseInt(strNooi);
				int seqShizai = Integer.parseInt(strSeqShizai);
				int noEda = Integer.parseInt(strNoeda);

				String allkey = getAllKey(reqData);

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
				strSQL.append("    AND seq_shizai = " + seqShizai);
				strSQL.append("    AND no_eda = " + noEda);
				strSQL.append("    AND cd_shohin = '" + strCdShohinFormat + "' ");

				//���ʃN���X�@�f�[�^�x�[�X�Ǘ���p���ASQL�����s
				lstRecset = this.searchDB.dbSearch_notError(strSQL.toString());
				if (lstRecset != null && lstRecset.size() != 0) {
					shizai_tehai_dataAri = true;
					//���Y���ނɃf�[�^�L���m�F�́ASQL�����s
					lstRecset2 = this.searchDB.dbSearch_notError(
							getTrshisanShizai(shainCd, nen, noOi, seqShizai, noEda, strCdShohinFormat));
					if (lstRecset2 != null && lstRecset2.size() != 0) {
						shiazn_shizai_dataAri =true;
						// SQL�쐬
						strSQL = new StringBuffer();
						strSQL.append(" UPDATE ");
						strSQL.append("   tr_shizai_tehai ");
						strSQL.append(" SET ");
						strSQL.append("   flg_tehai_status = '3'");
						strSQL.append(" WHERE ");
						strSQL.append("        cd_shain = " + shainCd);
						strSQL.append("    AND nen = " + nen);
						strSQL.append("    AND no_oi = " + noOi);
						strSQL.append("    AND seq_shizai = " + seqShizai);
						strSQL.append("    AND no_eda = " + noEda);
						strSQL.append("    AND cd_shohin = '" + strCdShohinFormat + "' ");
						strSQL.append("    AND flg_tehai_status >= '2'");

						//���ʃN���X�@�f�[�^�x�[�X�Ǘ���p���ASQL�����s
						this.execDB.execSQL(strSQL.toString());
						// ���Y���ރe�[�u���X�V

						// SQL�쐬
						strSQL = new StringBuffer();
						strSQL.append(" UPDATE ");
						strSQL.append("   tr_shisan_shizai ");
						strSQL.append(" SET ");
						strSQL.append(" cd_hattyusaki = '" + strHattyuusakiCd + "', ");
						strSQL.append("  cd_taisyoshizai = '" +  strTaisyosizaiCd + "',");//�Ώێ��ރR�[�h
						strSQL.append("   dt_hattyu = GETDATE(),  ");
						strSQL.append("   id_koshin = '" + userInfoData.getId_user() + "' ");
						strSQL.append(" WHERE ");
						strSQL.append("        cd_shain = " + shainCd);
						strSQL.append("    AND nen = " + nen);
						strSQL.append("    AND no_oi = " + noOi);
						strSQL.append("    AND seq_shizai = " + seqShizai);
						strSQL.append("    AND no_eda = " + noEda);
						strSQL.append("    AND cd_seihin = '" + strCdShohinFormat + "' ");

						this.execDB.execSQL(strSQL.toString());
					}
				}

				// �����I������̑J�ڂ̏ꍇ�Ŏ��ގ�z���͂Ƀf�[�^�����݂��A���A���Y���ނɃf�[�^�Ȃ��B
				// ���݂̎d�l�ł͊�{���݂��Ȃ��̂ŁA
				// NOEDA���X�V�����V�K���ׂ��쐬����B
//				System.out.println("�����I�����A�I�𖾍ׂ����݂��Ȃ��ꍇ[" + strKbn + "][" + shizai_tehai_dataAri + "][" + shiazn_shizai_dataAri + "]");
				if (!strKbn.equals("") && strKbn.equals("2") &&  shizai_tehai_dataAri && !shiazn_shizai_dataAri) {
					strSQL = new StringBuffer();
					strSQL.append("INSERT INTO ");
					strSQL.append("tr_shizai_tehai ");
					strSQL.append("SELECT ");
					strSQL.append("TEMP.cd_shain AS cd_shain,");
					strSQL.append("TEMP.nen AS nen,");
					strSQL.append("TEMP.no_oi AS no_oi,");
					strSQL.append("TEMP.seq_shizai AS seq_shizai,");
//					strSQL.append("TEMP.no_eda AS no_eda,");
					strSQL.append(" " +  (shizaiteahi_id_hukusuuMax+i) + " AS no_eda,");
					strSQL.append("TEMP.cd_shohin AS cd_shohin,");
					strSQL.append("TEMP.nm_shohin AS nm_shohin,");
					strSQL.append("CONVERT(int,TEMP.cd_shizai) AS cd_shizai,");
					strSQL.append("CONVERT(int,TEMP.cd_shizai_new) AS cd_shizai_new,");
					strSQL.append("TEMP.sekkei1 AS sekkei1,");
					strSQL.append("TEMP.sekkei2 AS sekkei2,");
					strSQL.append("TEMP.sekkei3 AS sekkei3,");
					strSQL.append("TEMP.zaishitsu AS zaishitsu,");
					strSQL.append("TEMP.biko_tehai AS biko_tehai,");
					strSQL.append("TEMP.printcolor AS printcolor,");
					strSQL.append("TEMP.no_color AS no_color,");
					strSQL.append("TEMP.henkounaiyoushosai AS henkounaiyoushosai,");
					strSQL.append("TEMP.nouki AS nouki,");
					strSQL.append("TEMP.suryo AS suryo,");
					strSQL.append("TEMP.old_sizaizaiko AS old_sizaizaiko,");
					strSQL.append("TEMP.rakuhan AS rakuhan,");
					strSQL.append(" " + userInfoData.getId_user() + " AS id_toroku,");
					strSQL.append("GETDATE() AS dt_toroku,");
					strSQL.append(" " + userInfoData.getId_user() + " AS id_koshin,");
					strSQL.append("GETDATE() AS dt_koshin,");
					strSQL.append("3  AS flg_tehai_status,");
					strSQL.append("NULL AS dt_han_payday,");
					strSQL.append("NULL AS han_pay,");
					strSQL.append("TEMP.hyoji_naiyo AS naiyo,");
					strSQL.append("TEMP.nounyusaki AS nounyusaki,");
					strSQL.append("NULL AS nm_file_aoyaki,");
					strSQL.append("NULL AS file_path_aoyaki");
					strSQL.append(" FROM ");
					strSQL.append(" tr_shizai_tehai_temp TEMP ");
					strSQL.append(" where ");
					strSQL.append("        cd_shain = " + shainCd);
					strSQL.append("    AND nen = " + nen);
					strSQL.append("    AND no_oi = " + noOi);
					strSQL.append("    AND seq_shizai = " + seqShizai);
					strSQL.append("    AND no_eda = " + noEda);
					strSQL.append("    AND cd_shohin = '" + strCdShohinFormat + "' ");
					strSQL.append("    AND cd_tmp_group_key = '" + allkey + "' ");
					strSQL.append("    AND kbn_shizai = '" + strKbn + "' ");

					this.execDB.execSQL(strSQL.toString());

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
	 * �}�Ԃ��̔Ԃ���B
	 * @return
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private int  getMaxIdHukusuuString() throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		String shizaiteahi_id_hukusuuMax = "";
		int shizaiteahi_id_hukusuuMaxInt = 0;
		int max = 1000;
		//SELECT���쐬
		StringBuffer strMaxGetSQL = new StringBuffer();
		strMaxGetSQL.append(" SELECT ");
		strMaxGetSQL.append("   MAX(no_eda)+1 ");
		strMaxGetSQL.append(" FROM ");
		strMaxGetSQL.append("    tr_shizai_tehai ");

		List<?> maxGetlstRecset = this.searchDB.dbSearch(strMaxGetSQL.toString());
		if (maxGetlstRecset != null && maxGetlstRecset.size() != 0) {
			Object items = (Object) maxGetlstRecset.get(0);
			 shizaiteahi_id_hukusuuMax  = toString(items);
			 shizaiteahi_id_hukusuuMaxInt = Integer.parseInt(shizaiteahi_id_hukusuuMax);
			 if (shizaiteahi_id_hukusuuMaxInt < max) {
				 shizaiteahi_id_hukusuuMaxInt = max;
			 }
			 System.out.println("MAXCOUNT:" + shizaiteahi_id_hukusuuMaxInt);
		}
		return shizaiteahi_id_hukusuuMaxInt;
	}

	private String getAllKey(RequestResponsKindBean reqData) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		String allkey = "";
		for(int j = 0;j < reqData.getCntRow(reqData.getTableID(0)); j++){
			//���N�G�X�g�f�[�^��莎��R�[�h�擾
			// �Ј��R�[�h
			String strReqShain   = toString(reqData.getFieldVale(0, j, "cd_shain"));
			// �N
			String strReqNen     = toString(reqData.getFieldVale(0, j, "nen"));
			// �ǔ�
			String strReqNoOi    = toString(reqData.getFieldVale(0, j, "no_oi"));
			// seq�ԍ�
			String strSeqShizai    = toString(reqData.getFieldVale(0, j, "seq_shizai"));
			// �}��
			String strReqNoEda   = toString(reqData.getFieldVale(0, j, "no_eda"));
			// ���i�R�[�h
			String strCdShohin = toString(reqData.getFieldVale(0, j, "cd_shohin"));

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


	private String  getTrshisanShizai(int strCdShain, int strNen, int strNooi, int strSeqShizai,
			int strNoeda, String strCdShohin) {
		StringBuffer strSQL;
		strSQL = new StringBuffer();
		strSQL.append("SELECT ");
		strSQL.append("  cd_shain");
		strSQL.append(" FROM  tr_shisan_shizai");
		strSQL.append(" WHERE ");
		strSQL.append("        cd_shain = " + strCdShain);
		strSQL.append("    AND nen = " + strNen);
		strSQL.append("    AND no_oi = " + strNooi);
		strSQL.append("    AND seq_shizai = " + strSeqShizai);
		strSQL.append("    AND no_eda = " + strNoeda);
		strSQL.append("    AND cd_seihin = '" + strCdShohin + "' ");
		return strSQL.toString();
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
}
