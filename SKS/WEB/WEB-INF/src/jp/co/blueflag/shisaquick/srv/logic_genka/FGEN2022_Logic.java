package jp.co.blueflag.shisaquick.srv.logic_genka;

import java.util.List;

import jp.co.blueflag.shisaquick.srv.base.LogicBase;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsKindBean;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;

/**
 *
 * �������Z���ڌŒ�l�o�^
 *  : �c�Ƃ̊m�F�����ς݃f�[�^�A�������Z��ʂ̍��ڌŒ�l�`�F�b�NON�ŌŒ�f�[�^�������ꍇ�̑Ή��B
 *    �����ڌŒ�l�`�F�b�N��OFF �ɂ��邱�ƂōČv�Z�f�[�^���擾�ł���
 *
 * @author TT.kitazawa
 * @since  2015/07/22
 *
 */

public class FGEN2022_Logic extends LogicBase {

	/**
	 * �R���X�g���N�^
	 */
	public FGEN2022_Logic() {
		//���N���X�̃R���X�g���N�^
		super();

	}

	//--------------------------------------------------------------------------------------------------------------
	//
	//              ExecLogic�i�����J�n�j
	//
	//--------------------------------------------------------------------------------------------------------------

	/**
	 * �������Z��� �Ǘ�����
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

			StringBuffer strSQL = new StringBuffer();
			List<?> lstRecset = null;

			//���N�G�X�g�f�[�^��莎��R�[�h�擾
			String strReqShainCd = reqData.getFieldVale("kihon", 0, "cd_shain");
			String strReqNen = reqData.getFieldVale("kihon", 0, "nen");
			String strReqOiNo = reqData.getFieldVale("kihon", 0, "no_oi");
			String strReqEda = reqData.getFieldVale("kihon", 0, "no_eda");
			String strRet = "0";

			//�X�V�pSQL�쐬
			for ( int i=0; i<reqData.getCntRow("keisan"); i++ ) {
				lstRecset = null;

				String strReqSeq = reqData.getFieldVale("keisan", i, "seq_shisaku");
				// ���ڌŒ�`�F�b�N
				String strKoteiChk = reqData.getFieldVale("keisan", i, "fg_koumokuchk");

				// ���ڌŒ�`�F�b�N ��OFF�̏ꍇ�͏������Ȃ�
				if (!strKoteiChk.equals("1")) {
					break;
				}

				//SELECT���쐬
				strSQL = new StringBuffer();
				strSQL.append(" SELECT cd_shain FROM");
				strSQL.append("    tr_shisan_shisaku_kotei");
				strSQL.append(" WHERE");
				strSQL.append("    cd_shain=" + strReqShainCd);
				strSQL.append(" AND nen=" + strReqNen);
				strSQL.append(" AND no_oi=" + strReqOiNo);
				strSQL.append(" AND seq_shisaku=" + strReqSeq);
				strSQL.append(" AND no_eda=" + strReqEda);

				//���ʃN���X�@�f�[�^�x�[�X�Ǘ���p���ASQL�����s
				lstRecset = this.searchDB.dbSearch(strSQL.toString());

				if( lstRecset.isEmpty() == false ){
					break;
				}

				//���ڌŒ�`�F�b�N����U �[���ɂ���
				strSQL = null;
				strSQL = new StringBuffer();
				strSQL.append(" UPDATE tr_shisan_shisaku ");
				strSQL.append(" SET fg_koumokuchk = 0 ");
				strSQL.append(" WHERE ");
				strSQL.append("     cd_shain=" + strReqShainCd);
				strSQL.append(" AND nen=" + strReqNen);
				strSQL.append(" AND no_oi=" + strReqOiNo);
				strSQL.append(" AND seq_shisaku=" + strReqSeq);
				strSQL.append(" AND no_eda=" + strReqEda);

				this.execDB.execSQL(strSQL.toString());
				// ��������
				strRet = "1";

			}
			// �R�~�b�g
			execDB.Commit();

			//�@�\ID�̐ݒ�
			String strKinoId = reqData.getID();
			resKind.setID(strKinoId);

			//�e�[�u�����̐ݒ�
			String strTableNm = reqData.getTableID(0);
			resKind.addTableItem(strTableNm);

			resKind.getTableItem(strTableNm);

			//�������ʂ̊i�[
			resKind.getTableItem(strTableNm).addFieldVale(0, "flg_return", "true");
			resKind.getTableItem(strTableNm).addFieldVale(0, "msg_error", "");
			resKind.getTableItem(strTableNm).addFieldVale(0, "no_errmsg", "");
			resKind.getTableItem(strTableNm).addFieldVale(0, "nm_class", "");
			resKind.getTableItem(strTableNm).addFieldVale(0, "cd_error", "");
			resKind.getTableItem(strTableNm).addFieldVale(0, "msg_system", "");
			resKind.getTableItem(strTableNm).addFieldVale(0, "msg_cd", strRet);

		} catch (Exception e) {
			// ���[���o�b�N
			execDB.Rollback();
			this.em.ThrowException(e, "�������Z��� �Ǘ����쏈�������s���܂����B");

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
}
