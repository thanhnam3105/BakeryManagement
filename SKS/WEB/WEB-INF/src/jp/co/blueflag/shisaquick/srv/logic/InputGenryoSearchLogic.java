package jp.co.blueflag.shisaquick.srv.logic;

import java.util.List;

import jp.co.blueflag.shisaquick.srv.base.LogicBase;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsKindBean;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsTableBean;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ConstManager;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;
import jp.co.blueflag.shisaquick.srv.common.ConstManager.Category;

/**
 *
 * [S3-53 SA580] ����R�[�h���͌����c�a����
 * @author TT.katayama
 * @since 2009/04/08
 *
 */
public class InputGenryoSearchLogic extends LogicBase {

	/**
	 * �R���X�g���N�^
	 */
	public InputGenryoSearchLogic() {
		//���N���X�̃R���X�g���N�^
		super();

	}

	/**
	 * ����R�[�h���̓f�[�^�擾����
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

		//���N�G�X�g
		String strReqKaishaCd = "";
		String strReqBushoCd = "";
		String strReqGenryoCd = "";

		StringBuffer strSql = new StringBuffer();
		List<?> lstRecset = null;

		//���X�|���X�f�[�^�N���X
		RequestResponsKindBean resKind = null;

		try {
			//���X�|���X�f�[�^�i�@�\�j����
			resKind = new RequestResponsKindBean();

			//�@�F���N�G�X�g�f�[�^���A����R�[�h���͌��������𒊏o���A����R�[�h���͌����f�[�^�����擾����SQL���쐬����B

			//�@�\���N�G�X�g�f�[�^���擾
			strReqKaishaCd = reqData.getFieldVale(0, 0, "cd_kaisha");
			strReqBushoCd = reqData.getFieldVale(0, 0, "cd_busho");
			strReqGenryoCd = reqData.getFieldVale(0, 0, "cd_genryo");

			//SQL���̍쐬
			strSql = createSQL(strReqKaishaCd,strReqBushoCd,strReqGenryoCd);

			//�A�|�@�F�f�[�^�x�[�X������p���A���쌴���f�[�^���擾����B
			super.createSearchDB();
			lstRecset = this.searchDB.dbSearch(strSql.toString());

//mod start -------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.8
			//�A�|�A�F�������������ŊY���f�[�^�����������ꍇ�A���̎��_�ŕʍH��Ɍ��������݂��邩�������s�����쌴���f�[�^���擾����B
			if(strReqBushoCd.equals(ConstManager.getConstValue(Category.�ݒ�, "CD_DAIHYO_KOJO"))
					&& strReqKaishaCd.equals(ConstManager.getConstValue(Category.�ݒ�, "CD_DAIHYO_KAISHA"))
					&& lstRecset.size() == 0 ){
				strSql.setLength(0);
				strSql = createStudySQL(strReqKaishaCd,strReqGenryoCd);
				super.createSearchDB();
				lstRecset = this.searchDB.dbSearch(strSql.toString());
			}
//mod end --------------------------------------------------------------------------------

			//�B�F���쌴���f�[�^�p�����[�^�[�i�[���\�b�h���ďo���A���X�|���X�f�[�^���`������B

// 20160513  KPX@1600766 ADD start
			//�B�|�P�F���쌴���f�[�^�̉�Ђ̒P���J���������擾����i���e�����}�X�^�j
			boolean blnKengen = getTankaHyouji_kengen(reqData, userInfoData);

// 20160513  KPX@1600766 ADD end

			//�@�\ID�̐ݒ�
			String strKinoId = reqData.getID();
			resKind.setID(strKinoId);

			//�e�[�u�����̐ݒ�
			String strTableNm = reqData.getTableID(0);
			resKind.addTableItem(strTableNm);

// 20160513  KPX@1600766 MOD start
			//�B�|�Q�F�P���J���s�̉�Ђ̏ꍇ�A�擾�������쌴���f�[�^���P�����󔒂ɕύX���ĕԂ��B
			//���X�|���X�f�[�^�̌`��
//			storageInputGenryoData(lstRecset, resKind.getTableItem(0));
			storageInputGenryoData(lstRecset, resKind.getTableItem(0), blnKengen);
// 20160513  KPX@1600766 MOD end
		} catch (Exception e) {
			this.em.ThrowException(e, "����R�[�h���̓f�[�^�擾���������s���܂����B");

		} finally {
			//���X�g�̔j��
			removeList(lstRecset);

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
	 * �����pSQL�쐬
	 * @param strKaishaCd : ��ЃR�[�h�@
	 * @param strBushoCd : �����R�[�h
	 * @param strGenryoCd : �����R�[�h
	 * @return �쐬����SQL
	 */
	private StringBuffer createSQL(String strKaishaCd, String strBushoCd, String strGenryoCd) {
		StringBuffer strRetSql = new StringBuffer();;

		//�@�F����R�[�h���͏����擾���邽�߂�SQL���쐬
		strRetSql.append(" SELECT  ");
		strRetSql.append("   M401.cd_genryo AS cd_genryo ");
		strRetSql.append("  ,M402.nm_genryo AS nm_genryo ");
		strRetSql.append("  ,ISNULL(CONVERT(VARCHAR,M402.tanka),'') AS tanka ");
		strRetSql.append("  ,ISNULL(CONVERT(VARCHAR,M402.budomari),'') AS budomari ");
		strRetSql.append("  ,ISNULL(CONVERT(VARCHAR,M401.ritu_abura),'') AS ritu_abura ");
		strRetSql.append("  ,ISNULL(CONVERT(VARCHAR,M401.ritu_sakusan),'') AS ritu_sakusan ");
		strRetSql.append("  ,ISNULL(CONVERT(VARCHAR,M401.ritu_shokuen),'') AS ritu_shokuen ");
		strRetSql.append("  ,ISNULL(CONVERT(VARCHAR,M401.ritu_sousan),'') AS ritu_sousan ");
		strRetSql.append("  ,kbn_haishi AS kbn_haishi ");
//add start ---------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.8
		strRetSql.append("  ,M402.cd_busho AS cd_busho ");
//add end -----------------------------------------------------------------------------------
//add start ------------------------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.31
		strRetSql.append(" ,ISNULL(CONVERT(VARCHAR,M402.budomari),'') AS ma_budomari ");
//add end --------------------------------------------------------------------------------------------------
// ADD start 20121003 QP@20505 No.24
		strRetSql.append("  ,ISNULL(CONVERT(VARCHAR,M401.ritu_msg),'') AS ritu_msg ");
// ADD end 20121003 QP@20505 No.24
		strRetSql.append(" FROM ma_genryo M401 ");
		strRetSql.append("  LEFT JOIN ma_genryokojo M402 ");
		strRetSql.append("   ON  M402.cd_kaisha = M401.cd_kaisha ");
		strRetSql.append("   AND M402.cd_genryo = M401.cd_genryo ");
		strRetSql.append(" WHERE M401.cd_kaisha =" + strKaishaCd);
		strRetSql.append("  AND M402.cd_busho =" + strBushoCd);
		strRetSql.append("  AND M401.cd_genryo = '" + strGenryoCd + "' ");

		//�A:�쐬����SQL��ԋp
		return strRetSql;
	}


//add start ------------------------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.8
	/**
	 * �������p�E����SQL�쐬
	 * @param strKaishaCd : ��ЃR�[�h�@
	 * @param strBushoCd : �����R�[�h
	 * @param strGenryoCd : �����R�[�h
	 * @return �쐬����SQL
	 */
	private StringBuffer createStudySQL(String strKaishaCd, String strGenryoCd){
		StringBuffer strRetSql = new StringBuffer();;

		//�@�|�@�F����R�[�h���͏����擾���邽�߂�SQL���쐬(�P������ԍ������������擾���A�����݈̂�ԒႢ���̂�\��)
		strRetSql.append(" SELECT  ");
		strRetSql.append(" TOP 1 ");
		strRetSql.append("   M401.cd_genryo AS cd_genryo ");
		strRetSql.append("  ,('��' + M402.nm_genryo) AS nm_genryo ");
		strRetSql.append("  ,ISNULL(CONVERT(VARCHAR,M402.tanka),'') AS tanka ");

		//�@�|�A�F�����̎擾���@[�S�H��Ō������s����ԒႢ�������擾����](2009/12/18�ύX)
		strRetSql.append(" ,ISNULL(CONVERT(VARCHAR,( SELECT  ");
		strRetSql.append(" TOP 1 ");
		strRetSql.append("  M402_budomari.budomari AS budomari ");
		strRetSql.append(" FROM ma_genryo M401_budomari ");
		strRetSql.append("  LEFT JOIN ma_genryokojo M402_budomari ");
		strRetSql.append("   ON  M402_budomari.cd_kaisha = M401_budomari.cd_kaisha ");
		strRetSql.append("   AND M402_budomari.cd_genryo = M401_budomari.cd_genryo ");
		strRetSql.append(" WHERE M401_budomari.cd_kaisha =" + strKaishaCd);
		strRetSql.append("  AND M401_budomari.cd_genryo = '" + strGenryoCd + "' ");
		strRetSql.append("  AND M402_budomari.budomari IS NOT NULL ");
		strRetSql.append(" ORDER BY ");
		strRetSql.append(" budomari  )),'') AS budomari ");
		//------------------------------------------------------------------------------
		strRetSql.append("  ,ISNULL(CONVERT(VARCHAR,M401.ritu_abura),'') AS ritu_abura ");
		strRetSql.append("  ,ISNULL(CONVERT(VARCHAR,M401.ritu_sakusan),'') AS ritu_sakusan ");
		strRetSql.append("  ,ISNULL(CONVERT(VARCHAR,M401.ritu_shokuen),'') AS ritu_shokuen ");
		strRetSql.append("  ,ISNULL(CONVERT(VARCHAR,M401.ritu_sousan),'') AS ritu_sousan ");
		strRetSql.append("  ,kbn_haishi AS kbn_haishi ");
		strRetSql.append("  ,M402.cd_busho AS cd_busho ");
	//add start ------------------------------------------------------------------------------------------------
	//QP@00412_�V�T�N�C�b�N���� No.31
		strRetSql.append(" ,M403.budomari AS ma_budomari ");
	//add end --------------------------------------------------------------------------------------------------
// ADD start 20121003 QP@20505 No.24
		strRetSql.append("  ,ISNULL(CONVERT(VARCHAR,M401.ritu_msg),'') AS ritu_msg ");
// ADD end 20121003 QP@20505 No.24
		strRetSql.append(" FROM ma_genryo M401 ");
		strRetSql.append("  LEFT JOIN ma_genryokojo M402 ");
		strRetSql.append("   ON  M402.cd_kaisha = M401.cd_kaisha ");
		strRetSql.append("   AND M402.cd_genryo = M401.cd_genryo ");

	//add start ------------------------------------------------------------------------------------------------
	//QP@00412_�V�T�N�C�b�N���� No.31
		strRetSql.append(" LEFT JOIN ma_genryokojo M403 ");
		strRetSql.append(" ON M403.cd_kaisha = M402.cd_kaisha ");
		strRetSql.append(" AND M403.cd_busho = M402.cd_busho ");
		strRetSql.append(" AND M403.cd_genryo = M402.cd_genryo ");
	//add end --------------------------------------------------------------------------------------------------

		strRetSql.append(" WHERE M401.cd_kaisha =" + strKaishaCd);
		strRetSql.append("  AND M401.cd_genryo = '" + strGenryoCd + "' ");

		strRetSql.append(" ORDER BY ");
		strRetSql.append(" M402.tanka DESC ");

		//�A:�쐬����SQL��ԋp
		return strRetSql;
	}
//add end ----------------------------------------------------------------------------------------------

// 20160513  KPX@1600766 ADD start
	/**
	 * �P���J�������擾
	 * @param reqData : ���N�G�X�g�f�[�^�i�@�\�j
	 * @return boolean  : �P���J������ �itrue�F�P���J������  false�F�P���J���s�j
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private boolean getTankaHyouji_kengen(RequestResponsKindBean reqData
			,UserInfoData _userInfoData)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		//��������
		boolean ret = false;			//�P���J���s��

		//���[�U�[���ޔ�
		userInfoData = _userInfoData;

		//���e�����}�X�^�����N���X
		LiteralDataSearchLogic clsLiteralSearch = null;
		//���e�����}�X�^���N�G�X�g
		RequestResponsKindBean reqLiteral = null;
		//���e�����}�X�^�������X�|���X
		RequestResponsKindBean resLiteral = null;

		String value1 = "";

		try {
			//���N�G�X�g�C���X�^���X
			reqLiteral = new RequestResponsKindBean();
			//���e�����}�X�^�������N�G�X�g����
			reqLiteral.addFieldVale("table", "rec", "cd_category", "K_tanka_hyoujigaisha");
			//��ЃR�[�h
			reqLiteral.addFieldVale("table", "rec", "cd_literal", reqData.getFieldVale(0, 0, "cd_kaisha"));

			//���e�����}�X�^�����F�P���J������
			clsLiteralSearch = new LiteralDataSearchLogic();
			resLiteral = clsLiteralSearch.ExecLogic(reqLiteral, userInfoData);

			if (resLiteral != null) {
				value1 = toString(resLiteral.getFieldVale(0, 0, "value1"));
			}

			if (value1.equals("1") || value1.equals("9")) {
				ret = true;		//�P���J������
			}

		} catch (Exception e) {
			//�Y���f�[�^�����̎��͊J���s��
			ret = false;

		} finally {

		}
		return ret;
	}
// 20160513  KPX@1600766 ADD end

// 20160513  KPX@1600766 MOD start
	/**
	 * ����R�[�h���̓f�[�^�p�����[�^�[�i�[
	 * @param lstGenryoData : �������ʏ�񃊃X�g
	 * @param kengen  �F�P���J�������itrue�F���@false�F�J���s�j
	 */
//	private void storageInputGenryoData(List<?> lstGenryoData, RequestResponsTableBean resTable)
	private void storageInputGenryoData(List<?> lstGenryoData, RequestResponsTableBean resTable, boolean kengen)
			throws ExceptionSystem, ExceptionUser, ExceptionWaning {
// 20160513  KPX@1600766 MOD end
		//�@�F�����@�������ʏ�񃊃X�g�ɕێ����Ă���e�p�����[�^�[�����X�|���X�f�[�^�֊i�[����B
		try {

			for (int i = 0; i < lstGenryoData.size(); i++) {

				//�������ʂ̊i�[
				resTable.addFieldVale("rec" + i, "flg_return", "true");
				resTable.addFieldVale("rec" + i, "msg_error", "");
				resTable.addFieldVale("rec" + i, "no_errmsg", "");
				resTable.addFieldVale("rec" + i, "nm_class", "");
				resTable.addFieldVale("rec" + i, "cd_error", "");
				resTable.addFieldVale("rec" + i, "msg_system", "");

				Object[] items = (Object[]) lstGenryoData.get(i);

				//���ʂ����X�|���X�f�[�^�Ɋi�[
				resTable.addFieldVale("rec" + i, "cd_genryo", toString(items[0]));

				if(toString(items[1]).equals("")){
					resTable.addFieldVale("rec" + i, "nm_genryo", "��" + toString(items[1]));
				}
				else{
					resTable.addFieldVale("rec" + i, "nm_genryo", toString(items[1]));
				}

// 20160513  KPX@1600766 MOD start
//				resTable.addFieldVale("rec" + i, "tanka", toString(items[2]));
//				resTable.addFieldVale("rec" + i, "budomari", toString(items[3]));
				if (kengen) {
					resTable.addFieldVale("rec" + i, "tanka", toString(items[2]));
					resTable.addFieldVale("rec" + i, "budomari", toString(items[3]));
				} else {
					resTable.addFieldVale("rec" + i, "tanka", "");
					resTable.addFieldVale("rec" + i, "budomari", "");
				}
// 20160513  KPX@1600766 MOD end
				resTable.addFieldVale("rec" + i, "ritu_abura", toString(items[4]));
				resTable.addFieldVale("rec" + i, "ritu_sakusan", toString(items[5]));
				resTable.addFieldVale("rec" + i, "ritu_shokuen", toString(items[6]));
				resTable.addFieldVale("rec" + i, "ritu_sousan", toString(items[7]));
				resTable.addFieldVale("rec" + i, "kbn_haishi", toString(items[8]));
//add start ---------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.8
				resTable.addFieldVale("rec" + i, "cd_busho", toString(items[9]));
//add end ----------------------------------------------------------------------------------
//add start ---------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.8
// 20160513  KPX@1600766 MOD start
//				resTable.addFieldVale("rec" + i, "ma_budomari", toString(items[10]));
				if (kengen) {
					resTable.addFieldVale("rec" + i, "ma_budomari", toString(items[10]));
				} else {
					resTable.addFieldVale("rec" + i, "ma_budomari", "");
				}
// 20160513  KPX@1600766 MOD end
//add end ----------------------------------------------------------------------------------
// ADD start 20121003 QP@20505 No.24
				resTable.addFieldVale("rec" + i, "ritu_msg", toString(items[11]));
// ADD end 20121003 QP@20505 No.24
			}

			if (lstGenryoData.size() == 0) {

				//�������ʂ̊i�[
				resTable.addFieldVale("rec0", "flg_return", "true");
				resTable.addFieldVale("rec0", "msg_error", "");
				resTable.addFieldVale("rec0", "no_errmsg", "");
				resTable.addFieldVale("rec0", "nm_class", "");
				resTable.addFieldVale("rec0", "cd_error", "");
				resTable.addFieldVale("rec0", "msg_system", "");

				//���ʂ����X�|���X�f�[�^�Ɋi�[
				resTable.addFieldVale("rec0", "cd_genryo", "");
				resTable.addFieldVale("rec0", "nm_genryo", "");
				resTable.addFieldVale("rec0", "tanka", "");
				resTable.addFieldVale("rec0", "budomari", "");
				resTable.addFieldVale("rec0", "ritu_abura", "");
				resTable.addFieldVale("rec0", "ritu_sakusan", "");
				resTable.addFieldVale("rec0", "ritu_shokuen", "");
				resTable.addFieldVale("rec0", "ritu_sousan", "");
				resTable.addFieldVale("rec0", "kbn_haishi", "");
//add start ---------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.8
				resTable.addFieldVale("rec0", "cd_busho", "");
//add end -----------------------------------------------------------------------------------
//add start ---------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.31
				resTable.addFieldVale("rec0", "ma_budomari", "");
//add end -----------------------------------------------------------------------------------
// ADD start 20121003 QP@20505 No.24
				resTable.addFieldVale("rec0", "ritu_msg", "");
// ADD end 20121003 QP@20505 No.24
			}

		} catch (Exception e) {
			this.em.ThrowException(e, "����R�[�h���̓f�[�^�p�����[�^�[�i�[���������s���܂����B");

		} finally {

		}

	}

}
