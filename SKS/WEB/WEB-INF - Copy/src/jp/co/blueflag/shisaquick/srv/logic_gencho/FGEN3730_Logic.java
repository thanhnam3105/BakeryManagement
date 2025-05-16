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
 * �yKPX@1602367�z
 *  ���ގ�z�Y�t�e�[�u���i�ꗗ�j
 *  : ���ގ�z�Y�t�����擾����B
 *  �@�\ID�FFGEN3730
 *
 * @author BRC t2nakamura
 * @since  2016/11/02
 */
public class FGEN3730_Logic extends LogicBase{

	/**
	 * ���ގ�z�˗����o�͎擾����
	 * : �C���X�^���X����
	 */
	public FGEN3730_Logic() {
		//���N���X�̃R���X�g���N�^
		super();

	}

	/**
	 * �x�[�X�P���擾
	 *  : �x�[�X�P�������擾����B
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

		RequestResponsKindBean resKind = null;

		try {

			//���X�|���X�f�[�^�i�@�\�j����
			resKind = new RequestResponsKindBean();

			//�@�\ID�̐ݒ�
			String strKinoId = reqData.getID();
			resKind.setID(strKinoId);

			//���X�|���X�f�[�^�̌`��
			this.setData(resKind, reqData);

		} catch (Exception e) {
			this.em.ThrowException(e, "");

		} finally {

			if (searchDB != null) {
				//�Z�b�V�����̃N���[�Y
				searchDB.Close();
				searchDB = null;
			}

			//�ϐ��̍폜

		}
		return resKind;

	}

	/**
	 * ���X�|���X�f�[�^�̌`��
	 * @param resKind : ���X�|���X�f�[�^
	 * @param reqData : ���N�G�X�g�f�[�^
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private void setData(
			 RequestResponsKindBean resKind
			,RequestResponsKindBean reqData
			)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		// ���R�[�h�l�i�[���X�g
		List<?> lstRecset = null;

		// ���R�[�h�l�i�[���X�g
		StringBuffer strSqlBuf = null;

		try {

			// �e�[�u����
			String strTblNm = "xmlFGEN3730";

			// �f�[�^�擾SQL�쐬
			strSqlBuf = this.createSQL(reqData);

			// ���ʃN���X�@�f�[�^�x�[�X������p����SQL���s
			super.createSearchDB();
			lstRecset = searchDB.dbSearch_notError(strSqlBuf.toString());

//			// �������ʂ��Ȃ��ꍇ
//			if (lstRecset.size() == 0){
//				em.ThrowException(ExceptionKind.�x��Exception,"W000401", lstRecset.toString(), "", "");
//			}

			// ���X�|���X�f�[�^�Ƀe�[�u���ǉ�
			resKind.addTableItem(strTblNm);

			// �ǉ������e�[�u���փ��R�[�h�i�[
			this.storageData(lstRecset, resKind.getTableItem(strTblNm));

		} catch (Exception e) {
			this.em.ThrowException(e, "���ގ�z�˗��o�� �f�[�^�������������s���܂����B");

		} finally {

			// ���X�g�̔j��
			removeList(lstRecset);

			if (searchDB != null) {
				// �Z�b�V�����̉��
				this.searchDB.Close();
				searchDB = null;
			}

			// �ϐ��̍폜
			strSqlBuf = null;
		}
	}

	/**
	 * �f�[�^�擾SQL�쐬
	 * @param reqData�F���N�G�X�g�f�[�^
	 * @param strSql�F��������SQL
	 * @return strSql�F��������SQL
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private StringBuffer createSQL(
			RequestResponsKindBean reqData
			)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		// SQL�i�[�p
		StringBuffer strSql = new StringBuffer();
		String allkey = getALLKey(reqData);
		try {
			for(int i = 0;i < reqData.getCntRow(reqData.getTableID(0)); i++){

				//
				String strKbn = reqData.getFieldVale(0, 0, "kbn");
				// �Ј��R�[�h
				String strCdShain = reqData.getFieldVale(0, 0, "cd_shain");
				// �N�R�[�h
				String strNen = reqData.getFieldVale(0, 0, "nen");
				// �ǔ�
				String strNooi = reqData.getFieldVale(0, 0, "no_oi");
				// seq����
				String strSeqShizai = reqData.getFieldVale(0, 0, "seq_shizai");
				// �}��
				String strNoeda = reqData.getFieldVale(0, 0, "no_eda");
				// ���i�R�[�h
				String strCdShohin = toString(reqData.getFieldVale(0, 0, "cd_shohin"));
				String strCdShohinFormat = get0SupressCode(strCdShohin);


				int intKbn = Integer.parseInt(strKbn);
				int intCdShain = Integer.parseInt(strCdShain);
				int intNen = Integer.parseInt(strNen);
				int intNooi = Integer.parseInt(strNooi);
				int intSeqShizai = Integer.parseInt(strSeqShizai);
				int intNoeda = Integer.parseInt(strNoeda);



				// SQL���̍쐬
				strSql.append("SELECT ");			// ��
				strSql.append("    han");
				strSql.append("    ,cd_��iteral_1");// ������R�[�h
				strSql.append("    ,cd_2nd_literal_1");// �S���҃R�[�h�P
				strSql.append("    ,cd_��iteral_2");	// ������R�[�h�Q
				strSql.append("    ,cd_2nd_literal_2");		// �S���҃R�[�h�Q
				strSql.append("    ,naiyo ");// ���e
				strSql.append("    ,cd_shohin ");// ���i�R�[�h
				strSql.append("    ,nm_shohin ");// ���i��
				strSql.append("    ,nisugata ");// �׎p
				strSql.append("    ,cd_taisyoshizai ");// �Ώێ��ރR�[�h
				strSql.append("    ,nounyusaki ");// �[����
				strSql.append("    ,cd_shizai ");// �����ރR�[�h
				strSql.append("    ,cd_shizai_new ");// �V���ރR�[�h
				strSql.append("    ,shiyohenko ");// �d�l�ύX
				strSql.append("    ,sekkei1 ");// �݌v�P
				strSql.append("    ,sekkei2 ");// �݌v�Q
				strSql.append("    ,sekkei3 ");// �݌v�R
				strSql.append("    ,zaishitsu ");// �ގ�
				strSql.append("    ,biko_tehai ");// ���l
				strSql.append("    ,printcolor ");// �v�����g�J���[
				strSql.append("    ,no_color ");//
				strSql.append("    ,henkounaiyoushosai");// �ύX���e�ڍ�
				strSql.append("    ,nouki");			// �[��
				strSql.append("    ,suryo");		// ����
				strSql.append("    ,old_sizaizaiko");	// �����ލ݌�
				strSql.append("    ,rakuhan");		// ����
				strSql.append("    ,hyoji_naiyo");	// �\�����e
				strSql.append("    ,hyoji_nisugata");	// �\���׎p
				strSql.append("    ,hyoji_nounyusaki");	// �\���[����
				strSql.append("    ,hyoji_cd_shizai");	// �\��������
				strSql.append("    ,hyoji_cd_shizai_new");// �\���V���ރR�[�h
				strSql.append("    ,cd_tmp_group_key");
				strSql.append(" FROM ");
				strSql.append("    tr_shizai_tehai_temp  ");
				strSql.append(" WHERE ");
				strSql.append(" cd_shain =" + intCdShain); //
				strSql.append(" AND nen =" + intNen);//
				strSql.append(" AND no_oi =" + intNooi);//
				strSql.append(" AND seq_shizai =" + intSeqShizai);//
				strSql.append(" AND no_eda =" + intNoeda);//
				strSql.append(" AND kbn_shizai="+ intKbn);//
				strSql.append(" AND cd_shohin =" + strCdShohinFormat);
				strSql.append(" AND cd_tmp_group_key='" + allkey +"'");//


			}

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
		return strSql;
	}

	/**
	 * �p�����[�^�[�i�[
	 *  : �X�e�[�^�X������ʂւ̃��X�|���X�f�[�^�֊i�[����B
	 * @param lstCostData : �������ʏ�񃊃X�g
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private void storageData(
			  List<?> lstCostData
			, RequestResponsTableBean resTable
			)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try {


				// �������ʂ̊i�[
				resTable.addFieldVale(0, "flg_return", "true");
				resTable.addFieldVale(0, "flg_return_success", "false");
				resTable.addFieldVale(0, "msg_error", "");
				resTable.addFieldVale(0, "no_errmsg", "");
				resTable.addFieldVale(0, "nm_class", "");
				resTable.addFieldVale(0, "cd_error", "");
				resTable.addFieldVale(0, "msg_system", "");

				if (lstCostData.size() != 0) {

					Object[] items = (Object[]) lstCostData.get(0);
					resTable.addFieldVale(0, "flg_return_success", "true");

					// ���ʂ����X�|���X�f�[�^�Ɋi�[
					resTable.addFieldVale(0, "han", toString(items[0]));					// ��
					resTable.addFieldVale(0, "cd_literal_1", toString(items[1]));		// ������R�[�h�P
					resTable.addFieldVale(0, "cd_2nd_literal_1", toString(items[2]));	// �S���҃R�[�h�P
					resTable.addFieldVale(0, "cd_literal_2", toString(items[3]));		// ������R�[�h�Q
					resTable.addFieldVale(0, "cd_2nd_literal_2", toString(items[4]));	// �S���҃R�[�h�Q
					resTable.addFieldVale(0, "naiyo", toString(items[5]));				// ���e
					resTable.addFieldVale(0, "cd_shohin", toString(items[6]));			// ���i�R�[�h
					resTable.addFieldVale(0, "nm_shohin", toString(items[7]));			// ���i��
					resTable.addFieldVale(0, "nisugata", toString(items[8]));				// �׎p
					resTable.addFieldVale(0, "cd_taisyoshizai", toString(items[9]));				// �Ώێ��ރR�[�h
					resTable.addFieldVale(0, "nounyusaki", toString(items[10]));				// �[����
					resTable.addFieldVale(0, "cd_shizai", toString(items[11]));				// �����ރR�[�h
					resTable.addFieldVale(0, "cd_shizai_new", toString(items[12]));				// �V���ރR�[�h
					resTable.addFieldVale(0, "shiyohenko", toString(items[13]));				// �d�l�ύX
					resTable.addFieldVale(0, "sekkei1", toString(items[14]));				// �݌v�P
					resTable.addFieldVale(0, "sekkei2", toString(items[15]));				// �݌v�Q
					resTable.addFieldVale(0, "sekkei3", toString(items[16]));				// �݌v�R
					resTable.addFieldVale(0, "zaishitsu", toString(items[17]));				// �ގ�
					resTable.addFieldVale(0, "biko_tehai", toString(items[18]));				// ���l
					resTable.addFieldVale(0, "printcolor", toString(items[19]));				// ����F
					resTable.addFieldVale(0, "no_color", toString(items[20]));				// �F�ԍ�
					resTable.addFieldVale(0, "henkounaiyoushosai", toString(items[21]));				// �ύX���e�ڍ�
					resTable.addFieldVale(0, "nouki", toString(items[22]));				// �[��
					resTable.addFieldVale(0, "suryo", toString(items[23]));				// ����
					resTable.addFieldVale(0, "old_sizaizaiko", toString(items[24]));				// �����ލ݌�
					resTable.addFieldVale(0, "rakuhan", toString(items[25]));				// ����
					resTable.addFieldVale(0, "hyoji_naiyo", toString(items[26]));				// �\�����e
					resTable.addFieldVale(0, "hyoji_nisugata", toString(items[27]));				// �\���׎p
					resTable.addFieldVale(0, "hyoji_nounyusaki", toString(items[28]));			// �\���[����
					resTable.addFieldVale(0, "hyoji_cd_shizai", toString(items[29]));			// �\�������ރR�[�h
					resTable.addFieldVale(0, "hyoji_cd_shizai_new", toString(items[30]));			// �\���V���ރR�[�h
					resTable.addFieldVale(0, "cd_tmp_group_key", toString(items[31]));			// �e���v�L�[
				}


		} catch (Exception e) {
			this.em.ThrowException(e, "");

		} finally {

		}
	}
	/**
	 * tempKEY�쐬
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

}
