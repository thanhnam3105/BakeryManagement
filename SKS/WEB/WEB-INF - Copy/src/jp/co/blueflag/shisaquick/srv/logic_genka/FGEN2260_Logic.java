package jp.co.blueflag.shisaquick.srv.logic_genka;

import java.util.List;

import jp.co.blueflag.shisaquick.srv.base.LogicBaseJExcel;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsKindBean;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsTableBean;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ConstManager;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;
import jp.co.blueflag.shisaquick.srv.common.ConstManager.Category;

/**
 * �yKPX@1502111_No.5�z
 *  ���͒l���́F�z�������N�c�a����
 *  �@�\FGEN2260
 *
 * @author TT.Kitazawa
 * @since 2016/06/07
 */
public class FGEN2260_Logic extends LogicBaseJExcel {

	/**
	 * �R���X�g���N�^
	 */
	public FGEN2260_Logic() {
		//�X�[�p�[�N���X�̃R���X�g���N�^�Ăяo��
		super();

	}
	/**
	 * ���͒l���́F�z�������N�c�a����
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
		RequestResponsKindBean resKind = null;

		try {

			// ���X�|���X�f�[�^�i�@�\�j����
			resKind = new RequestResponsKindBean();

			// �@�\ID�̐ݒ�
			String strKinoId = reqData.getID();
			resKind.setID(strKinoId);

			// ���X�|���X�f�[�^�̌`��
			this.setData(resKind, reqData);

		} catch (Exception e) {
			em.ThrowException(e, "");

		} finally {

			if (searchDB != null) {
				// �Z�b�V�����̃N���[�Y
				searchDB.Close();
				searchDB = null;
			}
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
			//�e�[�u����
			String strTblNm = "table";

            //�������[�h
			String strMode  = reqData.getFieldVale("table", 0, "syoriMode");

			if (strMode.equals("0")) {
				// �f�[�^�擾SQL�쐬�i�����R�[�h��茟���j
				strSqlBuf = this.createSQLgenryo(reqData);
			} else {
				// �f�[�^�擾SQL�쐬�i����m����茟���j
				strSqlBuf = this.createSQLshisaku(reqData);
			}

			// ���ʃN���X�@�f�[�^�x�[�X������p����SQL���s
			super.createSearchDB();
			lstRecset = searchDB.dbSearch(strSqlBuf.toString());

			// ���X�|���X�f�[�^�Ƀe�[�u���ǉ�
			resKind.addTableItem(strTblNm);

			// �ǉ������e�[�u���փ��R�[�h�i�[
			this.storageData(lstRecset, resKind.getTableItem(strTblNm));

		} catch (Exception e) {
			this.em.ThrowException(e, "�z�������N�������������s���܂����B");

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
	 * @return strSql�F��������SQL
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private StringBuffer createSQLgenryo(
            RequestResponsKindBean reqData
			)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		// SQL�i�[�p
		StringBuffer strSql = new StringBuffer();

		try {

            //��ЃR�[�h
			String strKaishaCd   = reqData.getFieldVale("table", 0, "cd_kaisha");
			//�����R�[�h
			String strGenryoCd   = reqData.getFieldVale("table", 0, "cd_genryo");
			//�����R�[�h
			String strBushoCd    = reqData.getFieldVale("table", 0, "cd_busho");
			if (strBushoCd.equals("")) {
				strBushoCd = ConstManager.getConstValue(Category.�ݒ�, "SHINKIGENRYO_BUSHOCD");
			}

			// SQL���̍쐬
			strSql.append(" SELECT cd_kaisha  ");
			strSql.append("       ,cd_genryo  ");
			strSql.append("       ,cd_busho  ");
			strSql.append("       ,cd_shain  ");
			strSql.append("       ,nen  ");
			strSql.append("       ,no_oi  ");
			strSql.append("       ,no_eda  ");
			strSql.append("       ,seq_shisaku  ");
			strSql.append(" FROM  tr_haigo_link  ");
			strSql.append(" WHERE  cd_kaisha = " + strKaishaCd );
			strSql.append("   AND  cd_genryo = '" + strGenryoCd + "'");
			strSql.append("   AND  cd_busho = " + strBushoCd );

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
		return strSql;
	}

	/**
	 * �f�[�^�擾SQL�쐬
	 * @param reqData�F���N�G�X�g�f�[�^
	 * @return strSql�F��������SQL
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private StringBuffer createSQLshisaku(
            RequestResponsKindBean reqData
			)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		// SQL�i�[�p
		StringBuffer strSql = new StringBuffer();

		try {

			//����CD-�Ј�CD
			String strShainCd  = reqData.getFieldVale("table", 0, "cd_shain");
			//����CD-�N
			String strNen    = reqData.getFieldVale("table", 0, "nen");
			//����CD-�ǔ�
			String strNo_oi    = reqData.getFieldVale("table", 0, "no_oi");
			//����CD-�}��
			String strNoEda    = reqData.getFieldVale("table", 0, "no_eda");

			// SQL���̍쐬
			strSql.append(" SELECT cd_kaisha  ");
			strSql.append("       ,cd_genryo  ");
			strSql.append("       ,cd_busho  ");
			strSql.append("       ,cd_shain  ");
			strSql.append("       ,nen  ");
			strSql.append("       ,no_oi  ");
			strSql.append("       ,no_eda  ");
			strSql.append("       ,seq_shisaku  ");
			strSql.append(" FROM  tr_haigo_link  ");
			strSql.append(" WHERE  cd_shain = " + strShainCd );
			strSql.append("   AND  nen = " + strNen );
			strSql.append("   AND  no_oi = " + strNo_oi );
			strSql.append("   AND  no_eda = " + strNoEda );

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
		return strSql;
	}

	/**
	 * �p�����[�^�[�i�[
	 *  : ���X�|���X�f�[�^�֊i�[����B
	 * @param lstRenkei : �������ʏ�񃊃X�g
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private void storageData(
			  List<?> lstRenkei
			, RequestResponsTableBean resTable
			)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try {

			if (lstRenkei.size() > 0) {
				//�f�[�^�擾�F1���̂�
				Object[] items = (Object[]) lstRenkei.get(0);

				// ���ʂ����X�|���X�f�[�^�Ɋi�[
				resTable.addFieldVale(0, "cd_kaisha", toString(items[0],""));
				resTable.addFieldVale(0, "cd_genryo", toString(items[1],""));
				resTable.addFieldVale(0, "cd_busho", toString(items[2],""));
				resTable.addFieldVale(0, "cd_shain", toString(items[3],""));
				resTable.addFieldVale(0, "nen", toString(items[4],""));
				resTable.addFieldVale(0, "no_oi", toString(items[5],""));
				resTable.addFieldVale(0, "no_eda", toString(items[6],""));
				resTable.addFieldVale(0, "seq_shisaku", toString(items[7],""));

				// �������ʂ̊i�[
				resTable.addFieldVale(0, "flg_return", "true");
				resTable.addFieldVale(0, "msg_error", "");
				resTable.addFieldVale(0, "no_errmsg", "");
				resTable.addFieldVale(0, "nm_class", "");
				resTable.addFieldVale(0, "cd_error", "");
				resTable.addFieldVale(0, "msg_system", "");

			} else {
				// �������ʂ̊i�[
				resTable.addFieldVale(0, "flg_return", "");
				resTable.addFieldVale(0, "msg_error", "");
				resTable.addFieldVale(0, "no_errmsg", "");
				resTable.addFieldVale(0, "nm_class", "");
				resTable.addFieldVale(0, "cd_error", "");
				resTable.addFieldVale(0, "msg_system", "");

			}

		} catch (Exception e) {
			this.em.ThrowException(e, "");

		} finally {

		}
	}
}
