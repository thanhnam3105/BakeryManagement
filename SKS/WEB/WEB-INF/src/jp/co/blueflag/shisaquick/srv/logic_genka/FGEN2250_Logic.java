package jp.co.blueflag.shisaquick.srv.logic_genka;

import java.util.List;

import jp.co.blueflag.shisaquick.srv.base.LogicBaseJExcel;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsKindBean;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsTableBean;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ConstManager;
import jp.co.blueflag.shisaquick.srv.common.ConstManager.Category;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;

/**
 * �yKPX@1502111_No.5�z
 *  ���͒l���́F�z�������N�c�a����
 *  �@�\FGEN2250
 *
 * @author TT.Kitazawa
 * @since 2016/06/07
 */
public class FGEN2250_Logic extends LogicBaseJExcel {

	/**
	 * �R���X�g���N�^
	 */
	public FGEN2250_Logic() {
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

		StringBuffer strSql = new StringBuffer();

		//���X�|���X�f�[�^�i�@�\�j
		RequestResponsKindBean resKind = null;

		try {

			// ���X�|���X�f�[�^�i�@�\�j����
			resKind = new RequestResponsKindBean();

			// �g�����U�N�V�����J�n
			super.createExecDB();

			execDB.BeginTran();

			strSql = createSQL(reqData);

			if (strSql != null) {
				// �R�~�b�g
				execDB.Commit();
			}

			//�@�\ID�̐ݒ�
			String strKinoId = reqData.getID();

			resKind.setID(strKinoId);

            // �e�[�u�����̐ݒ�
            String strTableNm = reqData.getTableID(0);

            resKind.addTableItem(strTableNm);

            // ���X�|���X�f�[�^�̌`��
            this.storageData(resKind.getTableItem(strTableNm));

		} catch (Exception e) {
			if (execDB != null) {
				// ���[���o�b�N
				execDB.Rollback();
			}

			this.em.ThrowException(e, "");

		} finally {
			if (searchDB != null) {
				//�Z�b�V�����̃N���[�Y
				searchDB.Close();
				searchDB = null;
			}

			//�ϐ��̍폜
			strSql = null;

		}
		return resKind;

	}

	/**
	 * �f�[�^�擾SQL�쐬
	 * @param reqData�F���N�G�X�g�f�[�^
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
		StringBuffer strSql = null;

		try {

			//�����敪
			String strKbn      = reqData.getFieldVale("table", 0, "kbn_shori");
			//�����V�K�o�^�F�����R�[�h�̔ԑO�̎��A�����𔲂���
			if (strKbn.equals("0")) {
				return null;
			}

            //��ЃR�[�h
			String strKaishaCd  = reqData.getFieldVale("table", 0, "cd_kaisha");
			//�����R�[�h
			String strGenryoCd  = reqData.getFieldVale("table", 0, "cd_genryo");
			//�����R�[�h
			String strBushoCd   = reqData.getFieldVale("table", 0, "cd_busho");
			if (strBushoCd.equals("")) {
				strBushoCd = ConstManager.getConstValue(Category.�ݒ�, "SHINKIGENRYO_BUSHOCD");
			}
			//����CD-�Ј�CD
			String strShainCd   = reqData.getFieldVale("table", 0, "cd_shain");
			//����CD-�N
			String strNen       = reqData.getFieldVale("table", 0, "nen");
			//����CD-�ǔ�
			String strNo_oi     = reqData.getFieldVale("table", 0, "no_oi");
			//����SEQ
			String strSeqShisaku  = reqData.getFieldVale("table", 0, "seq_shisaku");
			//�}�ԍ�
			String strNoEda     = reqData.getFieldVale("table", 0, "no_eda");

            strSql = new StringBuffer();
			// SQL���̍쐬
			strSql.append(" SELECT cd_shain  ");
			strSql.append("       ,nen  ");
			strSql.append("       ,no_oi  ");
			strSql.append("       ,seq_shisaku  ");
			strSql.append("       ,no_eda  ");
			strSql.append(" FROM  tr_haigo_link  ");
			strSql.append(" WHERE  cd_kaisha = " + strKaishaCd );
			strSql.append("   AND  cd_genryo = '" + strGenryoCd + "'");
			strSql.append("   AND  cd_busho = " + strBushoCd );

			super.createSearchDB();

			List<?> lstRecset = searchDB.dbSearch(strSql.toString());

			if(lstRecset.size() > 0) {
				strSql = new StringBuffer();

				// �폜SQL���̍쐬
				strSql.append(" DELETE  ");
				strSql.append(" FROM  tr_haigo_link  ");
				strSql.append(" WHERE  cd_kaisha = " + strKaishaCd );
				strSql.append("   AND  cd_genryo = '" + strGenryoCd + "'");
				strSql.append("   AND  cd_busho = " + strBushoCd );

				execDB.execSQL(strSql.toString());
			 }

			// ����CD����i����SEQ=""�j �̎��폜
//			if (!strSeqShisaku.equals("") ) {
			// �����敪�F�폜�i=3�j�ȊO�̎�
			if (!strKbn.equals("3") ) {
				strSql = new StringBuffer();

				// �o�^SQL���̍쐬
				strSql.append("INSERT INTO tr_haigo_link (  ");
				strSql.append("  cd_kaisha,  ");
				strSql.append("  cd_genryo,  ");
				strSql.append("  cd_busho,  ");
				strSql.append("  cd_shain,  ");
				strSql.append("  nen,  ");
				strSql.append("  no_oi,  ");
				strSql.append("  seq_shisaku,  ");
				strSql.append("  no_eda,  ");
				strSql.append("  id_toroku,  ");
				strSql.append("  dt_toroku,  ");
				strSql.append("  id_koshin,  ");
				strSql.append("  dt_koshin  ");
				strSql.append(")  ");
				strSql.append("VALUES  ");
				strSql.append("(  ");
				strSql.append(      strKaishaCd  + ",  " );
				strSql.append("  '" + strGenryoCd  + "',  ");
				strSql.append(      strBushoCd  + ",  " );
				strSql.append(      strShainCd  + ",  " );
				strSql.append(      strNen  + ",  " );
				strSql.append(      strNo_oi  + ",  " );
				strSql.append(      strSeqShisaku  + ",  " );
				strSql.append(      strNoEda  + ",  " );

				strSql.append("  " + userInfoData.getId_user() + ",  ");
				strSql.append("  GETDATE(),  ");
				strSql.append("  " + userInfoData.getId_user() + ",  ");
				strSql.append("  GETDATE()   ");
				strSql.append(")  ");

				execDB.execSQL(strSql.toString());

            }

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
		return strSql;
	}

	/**
	 * �p�����[�^�[�i�[
	 *  : ���X�|���X�f�[�^�֊i�[����B
	 * @param resTable : ���X�|���X�f�[�^
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private void storageData(
			RequestResponsTableBean resTable
			)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try {
            // �������ʂ̊i�[
            resTable.addFieldVale(0, "flg_return", "true");
            resTable.addFieldVale(0, "msg_error", "");
            resTable.addFieldVale(0, "no_errmsg", "");
            resTable.addFieldVale(0, "nm_class", "");
            resTable.addFieldVale(0, "cd_error", "");
            resTable.addFieldVale(0, "msg_system", "");


		} catch (Exception e) {
			this.em.ThrowException(e, "");

		} finally {

		}

	}
}
