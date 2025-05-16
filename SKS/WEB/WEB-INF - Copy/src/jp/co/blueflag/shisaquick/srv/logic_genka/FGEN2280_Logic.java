package jp.co.blueflag.shisaquick.srv.logic_genka;

import java.math.BigDecimal;
import java.util.List;

import jp.co.blueflag.shisaquick.srv.base.LogicBaseJExcel;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsKindBean;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsTableBean;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;

/**
 * �yKPX@1502111_No.5�z
 *  �������Z�F���ƌ��������A���ƌ����P���ݒ�
 *  �@�\FGEN2280
 *
 * @author TT.Kitazawa
 * @since 2016/06/17
 */
public class FGEN2280_Logic extends LogicBaseJExcel {

	/**
	 * �R���X�g���N�^
	 */
	public FGEN2280_Logic() {
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
		// ���Z�z���̍X�V��
		String[] chkKosin = null;

		try {
			//�e�[�u����
			String strTblNm = "table";

			//�f�[�^�擾SQL�쐬
			//  ���ƌ����ɘA�g�o�^����Ă��錴���̘A�g��̏����擾����
			strSqlBuf = this.createSQL(reqData);

			// ���ʃN���X�@�f�[�^�x�[�X������p����SQL���s
			super.createSearchDB();
			lstRecset = searchDB.dbSearch(strSqlBuf.toString());

			if(lstRecset.size() > 0) {
				//���ƌ����̉c�ƃX�e�[�^�X��3(����)�̎��A�����P����ݒ肷��
				chkKosin = this.updateGenryoTanka(reqData, lstRecset);
			}

			// ���X�|���X�f�[�^�Ƀe�[�u���ǉ�
			resKind.addTableItem(strTblNm);

			// �ǉ������e�[�u���փ��R�[�h�i�[
			this.storageData(lstRecset, resKind.getTableItem(strTblNm), chkKosin);

		} catch (Exception e) {
			this.em.ThrowException(e, "���ƌ����A�g�f�[�^���������Ɏ��s���܂����B");

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
	private StringBuffer createSQL(
            RequestResponsKindBean reqData
			)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		// SQL�i�[�p
		StringBuffer strSql = new StringBuffer();

		try {

			//���ƌ����ɘA�g�o�^����Ă��錴���̘A�g��̏����擾����
			//����CD-�Ј�CD
			String strShainCd  = reqData.getFieldVale("table", 0, "cd_shain");
			//����CD-�N
			String strNen    = reqData.getFieldVale("table", 0, "nen");
			//����CD-�ǔ�
			String strNo_oi    = reqData.getFieldVale("table", 0, "no_oi");
			//����CD-�}��
			String strNo_eda    = reqData.getFieldVale("table", 0, "no_eda");

			// SQL���̍쐬
			strSql.append(" SELECT  ");
			strSql.append("       T120.cd_kotei  ");		//0:�s��CD
			strSql.append("       ,T120.seq_kotei  ");		//1:�s��SEQ
			strSql.append("       ,T120.cd_genryo  ");
			strSql.append("       ,T320.nm_genryo  ");
			strSql.append("       ,T320.tanka_ma  ");		//4:�}�X�^�P���i�u���O�j
			strSql.append("       ,T320.budomar_ma  ");		//5:�}�X�^�����i�u���O�j
			strSql.append("       ,T121.cd_shain  ");		//6:�z�������N�i�A�g��j
			strSql.append("       ,T121.nen  ");
			strSql.append("       ,T121.no_oi  ");
			strSql.append("       ,T121.seq_shisaku  ");
			strSql.append("       ,T121.no_eda  ");
			strSql.append("       ,T332.kg_genka  ");		//11:�����v�i�~�j/Kg
			strSql.append("       ,T441.st_eigyo  ");		//12:�c�ƃX�e�[�^�X
			strSql.append("       ,T320.tanka_ins  ");		//13:����͒P���i�u���O�j
			strSql.append("       ,T320.budomari_ins  ");	//14:����͕����i�u���O�j
			strSql.append(" FROM  tr_haigo T120 ");
			strSql.append(" INNER JOIN   tr_haigo_link T121  ");
			strSql.append("   ON  T120.cd_genryo = T121.cd_genryo  ");
			strSql.append(" LEFT JOIN   tr_shisan_haigo T320  ");
			strSql.append("   ON   T320.cd_shain   = T120.cd_shain");
			strSql.append("   AND  T320.nen     = T120.nen" );
			strSql.append("   AND  T320.no_oi   = T120.no_oi" );
			strSql.append("   AND  T320.no_eda  = " + strNo_eda );
			strSql.append("   AND  T320.cd_kotei = T120.cd_kotei" );
			strSql.append("   AND  T320.seq_kotei = T120.seq_kotei" );
			strSql.append(" LEFT JOIN   tr_shisan_shisaku_kotei T332  ");
			strSql.append("   ON   T121.cd_shain   = T332.cd_shain");
			strSql.append("   AND  T121.nen     = T332.nen" );
			strSql.append("   AND  T121.no_oi   = T332.no_oi" );
			strSql.append("   AND  T121.no_eda  = T332.no_eda" );
			strSql.append("   AND  T121.seq_shisaku = T332.seq_shisaku" );
			strSql.append(" LEFT JOIN   tr_shisan_status T441  ");
			strSql.append("   ON   T121.cd_shain   = T441.cd_shain");
			strSql.append("   AND  T121.nen     = T441.nen" );
			strSql.append("   AND  T121.nen     = T441.nen" );
			strSql.append("   AND  T121.no_oi   = T441.no_oi" );
			strSql.append("   AND  T121.no_eda  = T441.no_eda" );
			strSql.append(" WHERE  T120.cd_shain = " + strShainCd );
			strSql.append("   AND  T120.nen = " + strNen );
			strSql.append("   AND  T120.no_oi = " + strNo_oi );

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
		return strSql;
	}

	/**
	 * �p�����[�^�[�i�[
	 *  : ���X�|���X�f�[�^�֊i�[����B
	 * @param reqData�F���N�G�X�g�f�[�^
	 * @param lstRenkei : �������ʏ�񃊃X�g
	 * @return String[] : �X�V��
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private String[] updateGenryoTanka(
			RequestResponsKindBean reqData
			,List<?> lstRenkei
			)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		String[] retKosin = null;

		try {
			//����CD-�Ј�CD
			String strShainCd  = reqData.getFieldVale("table", 0, "cd_shain");
			//����CD-�N
			String strNen    = reqData.getFieldVale("table", 0, "nen");
			//����CD-�ǔ�
			String strNo_oi    = reqData.getFieldVale("table", 0, "no_oi");
			//����CD-�}��
			String strNo_eda    = reqData.getFieldVale("table", 0, "no_eda");

			// �g�����U�N�V�����J�n
			super.createExecDB();

			execDB.BeginTran();

			// SQL�i�[�p
			StringBuffer strSql = null;
			// �X�V��
			retKosin = new String[lstRenkei.size()];

			for (int i = 0; i < lstRenkei.size(); i++) {
				//�f�[�^�擾
				Object[] items = (Object[]) lstRenkei.get(i);

				//�c�ƃX�e�[�^�X
				String strStEigyo = toString(items[12],"");
				// �}�X�^�P���ݒ�l
				String strTanka_ma = "null";
				// ����͒P���ݒ�l
				String strTanka_ins = "null";

				//�ύX�`�F�b�N
				retKosin[i] = "";

				//�c�ƃX�e�[�^�X�F�����ȏ�̎��A�����v�i�~�j/Kg ���Z�b�g
				if (strStEigyo.equals("3") ||  strStEigyo.equals("4") ) {
					// �}�X�^�P���i�u���O�j�A�����v�i�~�j/Kg ���r
					strTanka_ma = toString(items[11],"null");
					if (toDecimal(items[4]).compareTo(toDecimal(items[11])) != 0) {
						retKosin[i] = "1";
					}
					// ����͒P���F�u�������O����͒P��
					strTanka_ins = toString(items[13],"null");

				} else {
					// �c�ƃX�e�[�^�X�F������ �̎��A����͒P��
					strTanka_ins = toString(items[13],"null");

					//  �}�X�^�P����0���Z�b�g
					strTanka_ma = "0";
					// �}�X�^�P���i�u���O�j�Ɣ�r
					if (new BigDecimal("0").compareTo(toDecimal(items[4])) != 0) {
						retKosin[i] = "1";
					}
				}

				//�ύX���̂ݍX�V���������s
				if (retKosin[i].equals("1")) {
					strSql = new StringBuffer();

					//�P���E�������X�V
					strSql.append(" UPDATE tr_shisan_haigo ");
					strSql.append(" SET tanka_ma = " + strTanka_ma); 			// �}�X�^�P��
					strSql.append("   , tanka_ins = " + strTanka_ins);			// ����͒P��
					strSql.append("   , id_koshin = " + userInfoData.getId_user());
					strSql.append("   , dt_koshin = GETDATE()");
					strSql.append(" WHERE  cd_shain = " + strShainCd );
					strSql.append("   AND  nen = " + strNen );
					strSql.append("   AND  no_oi = " + strNo_oi );
					strSql.append("   AND  cd_kotei =  " + toString(items[0],""));		// �s��CD
					strSql.append("   AND  seq_kotei = " + toString(items[1],""));		// �s��SEQ
					strSql.append("   AND  no_eda = " + strNo_eda );

					execDB.execSQL(strSql.toString());
				}
			}

			// �R�~�b�g
			execDB.Commit();

		} catch (Exception e) {
			if (execDB != null) {
				// ���[���o�b�N
				execDB.Rollback();
			}
			this.em.ThrowException(e, "");

		} finally {

		}

		return retKosin;
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
			, String[] chkKosin
			)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try {

			for (int i=0; i<lstRenkei.size() ; i++) {
				//�f�[�^�擾�F1���̂�
				Object[] items = (Object[]) lstRenkei.get(i);

				//���ʂ����X�|���X�f�[�^�Ɋi�[
				// �s��CD
				resTable.addFieldVale(i, "cd_kotei", toString(items[0],""));
				// �s��SEQ
				resTable.addFieldVale(i, "seq_kotei", toString(items[1],""));
				// ����CD
				resTable.addFieldVale(i, "cd_genryo", toString(items[2],""));
				// ������
				resTable.addFieldVale(i, "nm_genryo", toString(items[3],""));
				// ���Z�z���F�}�X�^�P��
				resTable.addFieldVale(i, "tanka_ma", toString(items[4],""));
				// ���Z�z���F�}�X�^����
				resTable.addFieldVale(i, "budomar_ma", toString(items[5],""));
				// �A�g��F����CD-�Ј�CD
				resTable.addFieldVale(i, "cd_shain", toString(items[6],""));
				// �A�g��F����CD-�N
				resTable.addFieldVale(i, "nen", toString(items[7],""));
				// �A�g��F����CD-�ǔ�
				resTable.addFieldVale(i, "no_oi", toString(items[8],""));
				// �A�g��F����SEQ
				resTable.addFieldVale(i, "seq_shisaku", toString(items[9],""));
				// �A�g��F����CD-�}��
				resTable.addFieldVale(i, "no_eda", toString(items[10],""));
				// �A�g��F�P���i�����v�i�~�j/Kg�j
				resTable.addFieldVale(i, "kg_genka", toString(items[11],""));
				// �A�g��F�c�ƃX�e�[�^�X
				resTable.addFieldVale(i, "st_eigyo", toString(items[12],""));
				// ���Z�z���X�V��
				resTable.addFieldVale(i, "chkKosin", chkKosin[i]);

				// �������ʂ̊i�[
				resTable.addFieldVale(i, "flg_return", "true");
				resTable.addFieldVale(i, "msg_error", "");
				resTable.addFieldVale(i, "no_errmsg", "");
				resTable.addFieldVale(i, "nm_class", "");
				resTable.addFieldVale(i, "cd_error", "");
				resTable.addFieldVale(i, "msg_system", "");
			}

			if (lstRenkei.size() == 0) {
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
