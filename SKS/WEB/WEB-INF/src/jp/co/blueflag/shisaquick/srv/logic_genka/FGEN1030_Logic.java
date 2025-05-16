package jp.co.blueflag.shisaquick.srv.logic_genka;

import jp.co.blueflag.shisaquick.srv.base.LogicBase;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsKindBean;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsTableBean;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ConstManager;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;
import jp.co.blueflag.shisaquick.srv.common.ExceptionManager.ExceptionKind;

import java.util.List;

/**
 * 
 * ���i����
 *  : �ގ��i�����F���i������������B
 * @author nishigawa
 * @since  2009/11/05
 */
public class FGEN1030_Logic extends LogicBase{
	
	/**
	 * �ގ��i�����F���i������������B
	 * : �C���X�^���X����
	 */
	public FGEN1030_Logic() {
		//���N���X�̃R���X�g���N�^
		super();

	}

	/**
	 * �ގ��i�����F���i������������B
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
		//������
		List<?> lstRecset_cnt = null;
		//�Y�����ޏ��
		List<?> lstRecset = null;

		//���X�|���X�f�[�^�N���X
		RequestResponsKindBean resKind = null;
		
		try {
			//���X�|���X�f�[�^�i�@�\�j����
			resKind = new RequestResponsKindBean();
			super.createSearchDB();

			//SQL���̍쐬(������)
			strSql = genryoData_kensu_CreateSQL(reqData);
			//SQL�����s(������)
			lstRecset_cnt = searchDB.dbSearch(strSql.toString());

			//SQL���̍쐬�i�Y�����ށj
			strSql = genryoDataCreateSQL(reqData);
			//SQL�����s�i�Y�����ށj
			lstRecset = searchDB.dbSearch(strSql.toString());
			
			//�������ʂ��Ȃ��ꍇ
			if (lstRecset.size() == 0){
				em.ThrowException(ExceptionKind.�x��Exception,"W000401", strSql.toString(), "", "");
			}

			//�@�\ID�̐ݒ�
			String strKinoId = reqData.getID();
			resKind.setID(strKinoId);

			//�e�[�u�����̐ݒ�
			resKind.addTableItem("kihon");
			resKind.addTableItem("seihin");

			//���X�|���X�f�[�^�̌`��
			storageKihonData(lstRecset_cnt, reqData, resKind.getTableItem("kihon"));
			storageSeihinData(lstRecset, resKind.getTableItem("seihin"));
			
		} catch (Exception e) {
			this.em.ThrowException(e, "���i��񌟍������Ɏ��s���܂����B");
		} finally {
			//���X�g�̔j��
			removeList(lstRecset);
			removeList(lstRecset_cnt);
			
			//�Z�b�V�����̃N���[�Y
			if (searchDB != null) {
				searchDB.Close();
				searchDB = null;
			}

			//�ϐ��̍폜
			strSql = null;

		}
		return resKind;

	}
	
	/**
	 * ���i�������擾SQL�쐬
	 *  : ���i�����擾����SQL���쐬�B
	 * @param reqData�F���N�G�X�g�f�[�^
	 * @param strSql�F��������SQL
	 * @return strSql�F��������SQL
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private StringBuffer genryoData_kensu_CreateSQL(
			
			RequestResponsKindBean reqData
			) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		StringBuffer strSQL = new StringBuffer();

		try {
			String strSeihinCd = null;
			String strSeihinName = null;
			String strKaishaCd = null;
			String strKojoCd = null;
			String strPageNo = null;
			
			String strListRowMax = ConstManager.getConstValue(ConstManager.Category.�ݒ�,"LIST_ROW_MAX");

			//���i�R�[�h�̎擾
			strSeihinCd = toString(reqData.getFieldVale(0, 0, "cd_seihin"));
			//���i���̎擾
			strSeihinName = toString(reqData.getFieldVale(0, 0, "nm_seihin"));
			//��ЃR�[�h�̎擾
			strKaishaCd = toString(reqData.getFieldVale(0, 0, "cd_kaisya"));
			//�H��R�[�h�̎擾
			strKojoCd = toString(reqData.getFieldVale(0, 0, "cd_kojyo"));
			//�I���y�[�WNo�̎擾
			strPageNo = toString(reqData.getFieldVale(0, 0, "no_page"));

			//SQL���̍쐬
			
			strSQL.append(" SELECT ");
			strSQL.append(" COUNT(M801.cd_kaisha_seihin) AS CNT ");
			strSQL.append(" FROM ");
			strSQL.append(" ( ");
			strSQL.append(" SELECT ");
			strSQL.append("  cd_kaisha_seihin ");
			strSQL.append(" ,cd_busho_seihin ");
			strSQL.append(" ,cd_seihin ");
			strSQL.append(" ,nm_seihin ");
			strSQL.append(" FROM ");
			strSQL.append("           ma_seihin AS M01");
			strSQL.append(" WHERE ");
			strSQL.append("     1                     = 1 ");
			if (!strKaishaCd.equals("")){
				strSQL.append(" AND M01.cd_kaisha_seihin = " + strKaishaCd + " ");
				
			}
			if (!strKojoCd.equals("")){
				strSQL.append(" AND M01.cd_busho_seihin  = " + strKojoCd + " ");
				
			}
			if (!strSeihinCd.equals("")){
				strSQL.append(" AND M01.cd_seihin        = " + strSeihinCd + " ");
				
			}
			if (!strSeihinName.equals("")){
				strSQL.append(" AND M01.nm_seihin        LIKE '%" + strSeihinName + "%' ");
				
			}
			strSQL.append(" GROUP BY ");
			strSQL.append("  cd_kaisha_seihin ");
			strSQL.append(" ,cd_busho_seihin ");
			strSQL.append(" ,cd_seihin ");
			strSQL.append(" ,nm_seihin ");
			strSQL.append(" ) AS M801 ");

		} catch (Exception e) {
			this.em.ThrowException(e, "���i���f�[�^�����A���������Ɏ��s���܂����B");
		} finally {
			//�ϐ��̍폜

		}
		return strSQL;
	}
	/**
	 * ���i�������擾SQL�쐬
	 *  : ���i�����擾����SQL���쐬�B
	 * @param reqData�F���N�G�X�g�f�[�^
	 * @param strSql�F��������SQL
	 * @return strSql�F��������SQL
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private StringBuffer genryoDataCreateSQL(
			
			RequestResponsKindBean reqData
			) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		StringBuffer strSQL = new StringBuffer();

		try {
			String strSeihinCd = null;
			String strSeihinName = null;
			String strKaishaCd = null;
			String strKojoCd = null;
			String strPageNo = null;

			//MAX�߰�ގ擾
			String strListRowMax = toString(ConstManager.getConstValue(ConstManager.Category.�ݒ�,"LIST_ROW_MAX"), "50");
			//���i�R�[�h�̎擾
			strSeihinCd = reqData.getFieldVale(0, 0, "cd_seihin");
			//���i���̎擾
			strSeihinName = reqData.getFieldVale(0, 0, "nm_seihin");
			//��ЃR�[�h�̎擾
			strKaishaCd = reqData.getFieldVale(0, 0, "cd_kaisya");
			//�H��R�[�h�̎擾
			strKojoCd = reqData.getFieldVale(0, 0, "cd_kojyo");
			//�I���y�[�WNo�̎擾
			strPageNo = reqData.getFieldVale(0, 0, "no_page");

			//SQL���̍쐬

			strSQL.append(" SELECT TOP " + strListRowMax + " ");
			strSQL.append("  * ");
			strSQL.append(" FROM ");
			strSQL.append(" ( ");
			strSQL.append(" SELECT ");
			strSQL.append("  ROW_NUMBER() OVER (ORDER BY M801.cd_kaisha_seihin, M801.cd_busho_seihin, M801.cd_seihin ) AS NUM ");
			strSQL.append(" ,M801.cd_kaisha_seihin ");	//1
			strSQL.append(" ,M801.cd_busho_seihin ");	//2
			
			//strSQL.append(" ,M801.cd_seihin ");		//3
			strSQL.append(" ,RIGHT('0000000000' + CONVERT(varchar,M801.cd_seihin),ISNULL(M104.keta_genryo,6)) AS cd_seihin ");			//3
			
			strSQL.append(" ,M801.nm_seihin ");			//4
			strSQL.append(" ,M302.nm_literal ");		//5
			
			strSQL.append(" FROM ");
			strSQL.append(" ( ");
			strSQL.append(" SELECT ");
			strSQL.append("  cd_kaisha_seihin ");
			strSQL.append(" ,cd_busho_seihin ");
			strSQL.append(" ,cd_seihin ");
			strSQL.append(" ,nm_seihin ");
			strSQL.append(" FROM ");
			strSQL.append("           ma_seihin  AS M01");
			strSQL.append(" WHERE ");
			strSQL.append("     1                     = 1 ");
			if (!strKaishaCd.equals("")){
				strSQL.append(" AND M01.cd_kaisha_seihin = " + strKaishaCd + " ");
				
			}
			if (!strKojoCd.equals("")){
				strSQL.append(" AND M01.cd_busho_seihin  = " + strKojoCd + " ");
				
			}
			if (!strSeihinCd.equals("")){
				strSQL.append(" AND M01.cd_seihin        = " + strSeihinCd + " ");
				
			}
			if (!strSeihinName.equals("")){
				strSQL.append(" AND M01.nm_seihin        LIKE '%" + strSeihinName + "%' ");
				
			}
			strSQL.append(" GROUP BY ");
			strSQL.append("  cd_kaisha_seihin ");
			strSQL.append(" ,cd_busho_seihin ");
			strSQL.append(" ,cd_seihin ");
			strSQL.append(" ,nm_seihin ");
			strSQL.append(" ) AS M801 ");
			strSQL.append(" LEFT JOIN ma_literal AS M302 ");
			strSQL.append(" ON  M302.cd_category = 'K_kigo_kojyo'  ");
			strSQL.append(" AND M302.cd_literal = CONVERT(varchar, M801.cd_kaisha_seihin) + '-' + CONVERT(varchar, M801.cd_busho_seihin) ");
			
			strSQL.append(" LEFT JOIN ma_busho AS M104 ");
			strSQL.append(" ON cd_kaisha_seihin = M104.cd_kaisha ");
			strSQL.append(" AND cd_busho_seihin = M104.cd_busho ");
			
			strSQL.append(" ) AS M801_1 ");
			strSQL.append(" WHERE ");
			strSQL.append("     M801_1.NUM >= " + 
					//�擪�s�ԍ��̌v�Z
					toString( (toInteger(strListRowMax) * (toInteger(strPageNo)-1) +1 ), 0, 1, false, "1")
					+ " ");
			
		} catch (Exception e) {
			this.em.ThrowException(e, "���i���f�[�^���������Ɏ��s���܂����B");
		} finally {
			//�ϐ��̍폜

		}
		return strSQL;
	}

	/**
	 * �ގ��i�����F��{���p�����[�^�[�i�[
	 *  : ��{�������X�|���X�f�[�^�֊i�[����B
	 * @param lstGenryouData : �������ʏ�񃊃X�g
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private void storageKihonData(
			
			List<?> lstGenryouData
			, RequestResponsKindBean reqData
			, RequestResponsTableBean resTable
			)
		throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try {
			//�\��MAX�s��
			String strListRowMax = toString(ConstManager.getConstValue(ConstManager.Category.�ݒ�,"LIST_ROW_MAX"), "50");
			
			for (int i = 0; i < lstGenryouData.size(); i++) {

				//�������ʂ̊i�[
				resTable.addFieldVale(i, "flg_return", "true");
				resTable.addFieldVale(i, "msg_error", "");
				resTable.addFieldVale(i, "no_errmsg", "");
				resTable.addFieldVale(i, "nm_class", "");
				resTable.addFieldVale(i, "cd_error", "");
				resTable.addFieldVale(i, "msg_system", "");
				
				Object items = (Object) lstGenryouData.get(i);
				
				//���ʂ����X�|���X�f�[�^�Ɋi�[
				resTable.addFieldVale(i, "max_cnt", toString(items));
				resTable.addFieldVale(i, "no_page", reqData.getFieldVale(0, 0, "no_page"));
				resTable.addFieldVale(i, "max_page", toString(
						(toInteger(items) / toInteger(strListRowMax))
						, 0
						, 2
						, false
						, "1"));
				resTable.addFieldVale(i, "disp_cnt", toString(ConstManager.getConstValue(ConstManager.Category.�ݒ�,"LIST_ROW_MAX"), "50"));
				
			}
			
		} catch (Exception e) {
			this.em.ThrowException(e, "�����f�[�^���������Ɏ��s���܂����B");

		} finally {

		}

	}
	
	/**
	 * �ގ��i�����F���i���p�����[�^�[�i�[
	 *  : ���i�������X�|���X�f�[�^�֊i�[����B
	 * @param lstGenryouData : �������ʏ�񃊃X�g
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private void storageSeihinData(
			
			List<?> lstGenryouData
			, RequestResponsTableBean resTable
			)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try {
			
			for (int i = 0; i < lstGenryouData.size(); i++) {

				//�������ʂ̊i�[
				resTable.addFieldVale("rec" + i, "flg_return", "true");
				resTable.addFieldVale("rec" + i, "msg_error", "");
				resTable.addFieldVale("rec" + i, "no_errmsg", "");
				resTable.addFieldVale("rec" + i, "nm_class", "");
				resTable.addFieldVale("rec" + i, "cd_error", "");
				resTable.addFieldVale("rec" + i, "msg_system", "");
				
				Object[] items = (Object[]) lstGenryouData.get(i);

				//���ʂ����X�|���X�f�[�^�Ɋi�[
				resTable.addFieldVale("rec" + i, "cd_kaisya", toString(items[1]));
				resTable.addFieldVale("rec" + i, "cd_kojyyo", toString(items[2]));
				resTable.addFieldVale("rec" + i, "kigo_kojyo", toString(items[5]));
				
				//�w�茅���ɂ�0����
				resTable.addFieldVale("rec" + i, "cd_seihin", toString(items[3]));
				
				resTable.addFieldVale("rec" + i, "nm_seihin", toString(items[4]));
				
			}
			
		} catch (Exception e) {
			this.em.ThrowException(e, "���i���f�[�^���������Ɏ��s���܂����B");

		} finally {

		}

	}
	
}