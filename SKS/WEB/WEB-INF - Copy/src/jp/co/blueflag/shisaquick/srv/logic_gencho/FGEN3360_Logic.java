package jp.co.blueflag.shisaquick.srv.logic_gencho;

import java.util.List;

import jp.co.blueflag.shisaquick.srv.base.LogicBase;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsKindBean;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsTableBean;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionManager.ExceptionKind;
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
public class FGEN3360_Logic extends LogicBase {

	/**
	 * ���ޏ��X�V�R���X�g���N�^
	 * : �C���X�^���X����
	 */
	public FGEN3360_Logic(){
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

		try {
			for(int i = 0;i < reqData.getCntRow(reqData.getTableID(0)); i++){

				//���N�G�X�g�f�[�^��莎��R�[�h�擾
				// �Ј��R�[�h
				String strReqShain      = reqData.getFieldVale(0, i, "cd_shain");
				int intShain = Integer.parseInt(strReqShain);
				// �N
				String strReqNen        = reqData.getFieldVale(0, i, "nen");
				int intNen = Integer.parseInt(strReqNen);
				// �ǔ�
				String strReqNoOi       = reqData.getFieldVale(0, i, "no_oi");
				int intNoOi = Integer.parseInt(strReqNoOi);
				// �}��
				String strReqNoEda      = reqData.getFieldVale(0, i, "no_eda");
				int intNoEda = Integer.parseInt(strReqNoEda);
				// seq�ԍ�
				String strSeqNo         = reqData.getFieldVale(0, i, "seq_no");
				int intSeqNo = Integer.parseInt(strSeqNo);
				// ���e
				String strNaiyo         = toString(reqData.getFieldVale(0, i,"naiyo"));
				// ���i
				String strCdShohin      = toString(reqData.getFieldVale(0, i, "cd_shohin"));
				String strCdShohinFormat = get0SupressCode(strCdShohin);

				// ���i��
				String strNmShohin      = toString(reqData.getFieldVale(0, i, "nm_shohin"));
				// �[����
				String strNounyudaki    = toString(reqData.getFieldVale(0, i, "nounyusaki"));
				// �����ރR�[�h
				String strCdShizai      = toString(reqData.getFieldVale(0, i, "cd_shizai"));
				// �V���ރR�[�h
				String strCdShizaiNew   = toString(reqData.getFieldVale(0, i, "cd_shizai_new"));
				// �݌v�P
				String strSekkei1       = toString(reqData.getFieldVale(0, i, "sekkei1"));
				// �݌v�Q
				String strSekkei2       = toString(reqData.getFieldVale(0, i, "sekkei2"));
				// �݌v�R
				String strSekkei3       = toString(reqData.getFieldVale(0, i, "sekkei3"));
				// �ގ�
				String strZaishitsu     = toString(reqData.getFieldVale(0, i, "zaishitsu"));
				// ����F
				String strPrintColor    = toString(reqData.getFieldVale(0, i, "printcolor"));
				// �F�ԍ�
				String strNoColor       = toString(reqData.getFieldVale(0, i, "no_color"));
				// ���l
				String strBiko          = toString(reqData.getFieldVale(0, i, "biko"));
				// �ύX���e�ڍ�
				String strHenkounaiyou  = toString(reqData.getFieldVale(0, i, "henkou"));
				// �[��
				String strNouki         = toString(reqData.getFieldVale(0, i, "nouki"));
				// ����
				String strSuryo         = toString(reqData.getFieldVale(0, i, "suryo"));


				//SELECT���쐬
				strSQL = new StringBuffer();
				strSQL.append(" SELECT ");
				strSQL.append("   cd_shain ");
				strSQL.append(" FROM ");
				strSQL.append("    tr_shizai_tehai ");
				strSQL.append(" WHERE ");
				strSQL.append("        cd_shain = " + intShain);
				strSQL.append("    AND nen = " + intNen);
				strSQL.append("    AND no_oi = " + intNoOi);
				strSQL.append("    AND no_eda = " + intNoEda);
				strSQL.append("    AND seq_shizai = " + intSeqNo);
				strSQL.append("    AND cd_shohin  = " + strCdShohinFormat);

				//���ʃN���X�@�f�[�^�x�[�X�Ǘ���p���ASQL�����s
				lstRecset = this.searchDB.dbSearch(strSQL.toString());

				// �f�[�^�����݂��Ȃ�
				if(lstRecset.isEmpty()){
					em.ThrowException(ExceptionKind.�x��Exception, "W000500", "", "", "");
				}

				// �X�e�[�^�X�̍X�V(�e���|�����e�[�u���̏����Ɏ��{����B�`�F�b�N�݂̂Ƃ���B�j
				// SQL�쐬
				// �l�̍X�V
				// SQL�쐬
//				strSQL = new StringBuffer();
//				strSQL.append(" UPDATE ");
//				strSQL.append("   tr_shizai_tehai ");
//				strSQL.append(" SET ");
////				strSQL.append("   flg_tehai_status = 2 ,");
//				strSQL.append("   naiyo   = '" + strNaiyo + "',");					// ���e�i����̂܂܂��Ɠ��e�������Ȃ��B�j
//				strSQL.append("   nm_shohin   = '" + strNmShohin + "',");			// ���i��
//				strSQL.append("   nounyusaki   = '" + strNounyudaki + "',");		// �[����
//				String strCdShizaiFormat = get0SupressShizaiCd(strCdShizai);
//				strSQL.append("   cd_shizai   = '" + strCdShizaiFormat + "',");			// �����ރR�[�h
//				String strCdShizaiNewFormat = get0SupressShizaiCd(strCdShizaiNew);
//				strSQL.append("   cd_shizai_new   = '" + strCdShizaiNewFormat + "',");	// �V���ރR�[�h
//				strSQL.append("   sekkei1 = '" + strSekkei1 + "', ");
//				strSQL.append("   sekkei2 = '" + strSekkei2 + "', ");
//				strSQL.append("   sekkei3 = '" + strSekkei3 + "', ");
//				strSQL.append("   zaishitsu = '" + strZaishitsu + "', ");
//				strSQL.append("   printcolor = '" + strPrintColor + "', ");
//				strSQL.append("   no_color = '" + strNoColor + "', ");
//				strSQL.append("   henkounaiyoushosai = '" + strHenkounaiyou + "', ");	// �ύX���e�ڍ�
//				strSQL.append("   nouki = '" + strNouki + "', ");						// �[��
//				strSQL.append("   suryo='" + strSuryo + "', ");						// ����
//				strSQL.append("   id_koshin = '" + userInfoData.getId_user() + "' ");
//				strSQL.append(" WHERE ");
//				strSQL.append("        cd_shain = " + intShain);
//				strSQL.append("    AND nen = " + intNen);
//				strSQL.append("    AND no_oi = " + intNoOi);
//				strSQL.append("    AND no_eda = " + intNoEda);
//				strSQL.append("    AND seq_shizai = " + intSeqNo);
//				strSQL.append("    AND cd_shohin  = '" + strCdShohinFormat + "'");
//
//
//				//���ʃN���X�@�f�[�^�x�[�X�Ǘ���p���ASQL�����s
//				this.execDB.execSQL(strSQL.toString());



				// �N���A
				strSQL = null;
			}

		} catch (Exception e) {

			em.ThrowException(e, "���ޏ��X�VDB�����Ɏ��s���܂����B");

		} finally {

		}
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
}
