package jp.co.blueflag.shisaquick.srv.logic_gencho;

import java.util.List;

import jp.co.blueflag.shisaquick.srv.base.LogicBaseJExcel;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsKindBean;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsTableBean;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionManager.ExceptionKind;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;

/**
 * ���ޏ��폜����
 *  �@�\ID�FFGEN3320�@
 *
 * @author TT.Shima
 * @since  2014/10/07
 */
public class FGEN3320_Logic extends LogicBaseJExcel {

	/**
	 * ���ގ�z���X�V�R���X�g���N�^
	 * : �C���X�^���X����
	 */
	public FGEN3320_Logic(){
		super();
	}

	/**
	 * ���ގ�z���폜�������s��
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

			// �폜����
			deleteSQL(reqData);

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
			this.em.ThrowException(e, "���ގ�z���폜�����Ɏ��s���܂����B");

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
	 * �폜����
	 *  : ���ގ�z���̍폜���s��
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private void deleteSQL(
			RequestResponsKindBean reqData)
					throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		StringBuffer strSQL;
		List<?> lstRecset;
		String allkey = "";

		try {
			for(int i = 0;i < reqData.getCntRow(reqData.getTableID(0)); i++){

				//���N�G�X�g�f�[�^��莎��R�[�h�擾
				String strReqShain           = toString(reqData.getFieldVale(0, i, "cd_shain"));
				String strReqNen             = toString(reqData.getFieldVale(0, i, "nen"));
				String strReqNoOi            = toString(reqData.getFieldVale(0, i, "no_oi"));
				String strReqShizai          = toString(reqData.getFieldVale(0, i, "seq_shizai"));
				String strReqEda             = toString(reqData.getFieldVale(0, i, "no_eda"));
				String strShohinCd           = toString(reqData.getFieldVale(0, i, "cd_shohin"));
				String strKbn                = toString(reqData.getFieldVale(0, i, "kbn"));
				int inteqShain = Integer.parseInt(strReqShain);
				int intReqNen = Integer.parseInt(strReqNen);
				int inteqNoOi =Integer.parseInt(strReqNoOi);
				int intReqShizai =Integer.parseInt(strReqShizai);
				int intReqEda =Integer.parseInt(strReqEda);


				//SELECT���쐬
				strSQL = new StringBuffer();
				strSQL.append(" SELECT ");
				strSQL.append("   cd_shain ");
				strSQL.append(" FROM ");
				strSQL.append("    tr_shizai_tehai ");
				strSQL.append(" WHERE ");
				strSQL.append("        cd_shain = " + inteqShain);
				strSQL.append("    AND nen = " + intReqNen);
				strSQL.append("    AND no_oi = " + inteqNoOi);
				strSQL.append("    AND seq_shizai = " + intReqShizai);
				strSQL.append("    AND no_eda = " + intReqEda);

				//���ʃN���X�@�f�[�^�x�[�X�Ǘ���p���ASQL�����s
				lstRecset = this.searchDB.dbSearch(strSQL.toString());

				// �f�[�^�����݂��Ȃ�
				if(lstRecset.isEmpty()){
					em.ThrowException(ExceptionKind.�x��Exception, "W000500", "", "", "");
				}

				// SQL�쐬
				strSQL = new StringBuffer();
				strSQL.append(" DELETE ");
				strSQL.append(" FROM ");
				strSQL.append("   tr_shizai_tehai ");
				strSQL.append(" WHERE ");
				strSQL.append("        cd_shain = " + inteqShain);
				strSQL.append("    AND nen = " + intReqNen);
				strSQL.append("    AND no_oi = " + inteqNoOi);
				strSQL.append("    AND seq_shizai = " + intReqShizai);
				strSQL.append("    AND no_eda = " + intReqEda);

				//���ʃN���X�@�f�[�^�x�[�X�Ǘ���p���ASQL�����s
				this.execDB.execSQL(strSQL.toString());

				allkey = getAllKey(reqData);

				strSQL = new StringBuffer();
				strSQL.append(" SELECT ");
				strSQL.append("   cd_shain ");
				strSQL.append(" FROM ");
				strSQL.append("    tr_shizai_tehai_temp ");
				strSQL.append(" WHERE ");
				strSQL.append("        cd_tmp_group_key = '" + allkey + "'");
				strSQL.append("    AND cd_shain = " + inteqShain);
				strSQL.append("    AND nen = " + intReqNen);
				strSQL.append("    AND no_oi = " + inteqNoOi);
				strSQL.append("    AND seq_shizai = " + intReqShizai);
				strSQL.append("    AND no_eda = " + intReqEda);
				strSQL.append("    AND kbn_shizai = '" + strKbn + "'");

				//���ʃN���X�@�f�[�^�x�[�X�Ǘ���p���ASQL�����s
				lstRecset = this.searchDB.dbSearch_notError(strSQL.toString());

				// �f�[�^�����݂��Ȃ�
				if(lstRecset.size() != 0){
					strSQL = new StringBuffer();
					strSQL.append(" DELETE ");
					strSQL.append(" FROM ");
					strSQL.append("   tr_shizai_tehai_temp ");
					strSQL.append(" WHERE ");
					strSQL.append("        cd_tmp_group_key = '" + allkey + "'");
					strSQL.append("    AND cd_shain = " + inteqShain);
					strSQL.append("    AND nen = " + intReqNen);
					strSQL.append("    AND no_oi = " + inteqNoOi);
					strSQL.append("    AND seq_shizai = " + intReqShizai);
					strSQL.append("    AND no_eda = " + intReqEda);
					strSQL.append("    AND kbn_shizai = '" + strKbn + "'");
					//���ʃN���X�@�f�[�^�x�[�X�Ǘ���p���ASQL�����s
					this.execDB.execSQL(strSQL.toString());
				}


				// �N���A
				strSQL = null;
			}

		} catch (Exception e) {

			em.ThrowException(e, "���ގ�z���폜DB�����Ɏ��s���܂����B");

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
	private String getAllKey(RequestResponsKindBean reqData)
			throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		String allkey = "";
		for(int j = 0;j < reqData.getCntRow(reqData.getTableID(0)); j++){
			//���N�G�X�g�f�[�^��莎��R�[�h�擾
			// �Ј��R�[�h
			String strReqShain           = toString(reqData.getFieldVale(0, j, "cd_shain"));
			// �N
			String strReqNen             = toString(reqData.getFieldVale(0, j, "nen"));
			// �ǔ�
			String strReqNoOi            = toString(reqData.getFieldVale(0, j, "no_oi"));
			// seq�ԍ�
			String strReqShizai          = toString(reqData.getFieldVale(0, j, "seq_shizai"));
			// �}��
			String strReqEda             = toString(reqData.getFieldVale(0, j, "no_eda"));
			// ���i�R�[�h
			String strShohinCd           = toString(reqData.getFieldVale(0, j, "cd_shohin"));

			int intShainCd = Integer.parseInt(strReqShain);
			String shainCd = String.valueOf(intShainCd);

			int intNen = Integer.parseInt(strReqNen);
			String nen = String.valueOf(intNen);

			int intNoOi = Integer.parseInt(strReqNoOi);
			String noOi =String.valueOf(intNoOi);

			int intSeqShizai = Integer.parseInt(strReqShizai);
			String seqShizai = String.valueOf(intSeqShizai);

			int intNoEda = Integer.parseInt(strReqEda);
			String noEda = String.valueOf(intNoEda);

			String tempkey = shainCd + "_" + nen + "_" +  noOi
					 + "_" + seqShizai + "_" + noEda + "_" + strShohinCd + "_" ;
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
			this.em.ThrowException(e, "���ގ�z���폜DB�����Ɏ��s���܂����B");

		} finally {

		}
	}
}
