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
 * ���ތ���
 *  : �ގ��i�����F���ޏ�����������B
 * @author nishigawa
 * @since  2009/11/05
 */
public class FGEN1040_Logic extends LogicBase{
	
	/**
	 * �ގ��i�����F���ޏ�����������B
	 * : �C���X�^���X����
	 */
	public FGEN1040_Logic() {
		//���N���X�̃R���X�g���N�^
		super();

	}

	/**
	 * �ގ��i�����F���ޏ�����������B
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
		List<?> lstRecset_CNT = null;
		List<?> lstRecset = null;

		//���X�|���X�f�[�^�N���X
		RequestResponsKindBean resKind = null;
		
		try {
			//���X�|���X�f�[�^�i�@�\�j����
			resKind = new RequestResponsKindBean();
			super.createSearchDB();

			//SQL���̍쐬�i�����j
			strSql = DataCreateSQL_CNT(reqData);
			//SQL�����s
			lstRecset_CNT = searchDB.dbSearch(strSql.toString());

			//SQL���̍쐬�i�f�[�^�j
			strSql = DataCreateSQL(reqData);
			//SQL�����s
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
			resKind.addTableItem("shizai");

			//���X�|���X�f�[�^�̌`��
			storageKihonData(lstRecset_CNT, reqData, resKind.getTableItem("kihon"));
			storageSeihinData(lstRecset, resKind.getTableItem("shizai"));
			
		} catch (Exception e) {
			this.em.ThrowException(e, "���ރf�[�^���������Ɏ��s���܂����B");
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
	 * ���ތ������i�����j�擾SQL�쐬
	 *  : ���ޏ����擾����SQL���쐬�B
	 * @param reqData�F���N�G�X�g�f�[�^
	 * @param strSql�F��������SQL
	 * @return strSql�F��������SQL
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private StringBuffer DataCreateSQL_CNT(
			
			RequestResponsKindBean reqData
			) 
		throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		StringBuffer strSQL = new StringBuffer();

		try {
			String strShizaiCd = null;
			String strShizaiName = null;
			String strKaishaCd = null;
			String strKojoCd = null;
			String strPageNo = null;
			
			String strListRowMax = ConstManager.getConstValue(ConstManager.Category.�ݒ�,"LIST_ROW_MAX");

			//��ЃR�[�h�̎擾
			strKaishaCd = reqData.getFieldVale(0, 0, "cd_kaisya");
			//�H��R�[�h�̎擾
			strKojoCd = reqData.getFieldVale(0, 0, "cd_kojyo");
			//���ރR�[�h�̎擾
			strShizaiCd = reqData.getFieldVale(0, 0, "cd_shizai");
			//���ޖ��̎擾
			strShizaiName = reqData.getFieldVale(0, 0, "nm_shizai");
			//�I���y�[�WNo�̎擾
			strPageNo = reqData.getFieldVale(0, 0, "no_page");

			strSQL.append(" SELECT ");
			strSQL.append("  COUNT(cd_kaisha) AS CNT ");
			strSQL.append(" FROM ");
			strSQL.append("           ma_shizai ");
			strSQL.append(" WHERE ");
			strSQL.append(" 1 = 1 ");
			if(!strKaishaCd.equals("")){
				
			}
			if(!strKaishaCd.equals("")){
				strSQL.append(" AND cd_kaisha = " + strKaishaCd + " ");
				
			}
			if(!strKojoCd.equals("")){
				strSQL.append(" AND cd_busho  = " + strKojoCd + " ");
				
			}
			if(!strShizaiCd.equals("")){
				strSQL.append(" AND cd_shizai = " + strShizaiCd + " ");
				
			}
			if(!strShizaiName.equals("")){
				strSQL.append(" AND nm_shizai LIKE '%" + strShizaiName + "%' ");
				
			}
			

		} catch (Exception e) {
			this.em.ThrowException(e, "���ރf�[�^���������Ɏ��s���܂����B");
		} finally {
			
		}
		return strSQL;
	}

	/**
	 * ���ތ������擾SQL�쐬
	 *  : ���ޏ����擾����SQL���쐬�B
	 * @param reqData�F���N�G�X�g�f�[�^
	 * @param strSql�F��������SQL
	 * @return strSql�F��������SQL
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private StringBuffer DataCreateSQL(
			
			RequestResponsKindBean reqData
			) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		StringBuffer strSQL = new StringBuffer();

		try {
			String strShizaiCd = null;
			String strShizaiName = null;
			String strKaishaCd = null;
			String strKojoCd = null;
			String strPageNo = null;
			
			String strListRowMax = ConstManager.getConstValue(ConstManager.Category.�ݒ�,"LIST_ROW_MAX");

			//��ЃR�[�h�̎擾
			strKaishaCd = reqData.getFieldVale(0, 0, "cd_kaisya");
			//�H��R�[�h�̎擾
			strKojoCd = reqData.getFieldVale(0, 0, "cd_kojyo");
			//���ރR�[�h�̎擾
			strShizaiCd = reqData.getFieldVale(0, 0, "cd_shizai");
			//���ޖ��̎擾
			strShizaiName = reqData.getFieldVale(0, 0, "nm_shizai");
			//�I���y�[�WNo�̎擾
			strPageNo = reqData.getFieldVale(0, 0, "no_page");

			//SQL���̍쐬
			strSQL.append(" SELECT TOP " + strListRowMax + " ");
			strSQL.append("  * ");
			strSQL.append(" FROM ");
			strSQL.append(" ( ");
			strSQL.append("  SELECT ");
			strSQL.append("   ROW_NUMBER() OVER (ORDER BY M901.cd_kaisha, M901.cd_busho, M901.cd_shizai ) AS NUM ");
			strSQL.append("  ,M901.cd_kaisha ");	//1
			strSQL.append("  ,M901.cd_busho ");		//2
			//strSQL.append("  ,M901.cd_shizai ");	//3
			strSQL.append(" ,RIGHT('0000000000' + CONVERT(varchar,M901.cd_shizai),ISNULL(M104.keta_genryo,6)) AS cd_shizai ");
			strSQL.append("  ,M901.nm_shizai ");	//4
			strSQL.append("  ,M901.tanka ");		//5
			strSQL.append("  ,M901.budomari ");		//6
			strSQL.append("  ,M302.nm_literal ");	//7
			strSQL.append("  FROM ");
			strSQL.append("            ma_shizai AS M901 ");
			strSQL.append(" LEFT JOIN ma_literal AS M302 ");
			strSQL.append(" ON  M302.cd_category = 'K_kigo_kojyo'  ");
			strSQL.append(" AND M302.cd_literal = CONVERT(varchar, M901.cd_kaisha) + '-' + CONVERT(varchar, M901.cd_busho) ");
			
			strSQL.append(" LEFT JOIN ma_busho AS M104 ");
			strSQL.append(" ON M901.cd_kaisha = M104.cd_kaisha ");
			strSQL.append(" AND M901.cd_busho = M104.cd_busho ");
			
			strSQL.append("  WHERE ");
			strSQL.append(" 1 = 1 ");
			if(!strKaishaCd.equals("")){
				
			}
			if(!strKaishaCd.equals("")){
				strSQL.append(" AND M901.cd_kaisha = " + strKaishaCd + " ");
				
			}
			if(!strKojoCd.equals("")){
				strSQL.append(" AND M901.cd_busho  = " + strKojoCd + " ");
				
			}
			if(!strShizaiCd.equals("")){
				strSQL.append(" AND M901.cd_shizai = " + strShizaiCd + " ");
				
			}
			if(!strShizaiName.equals("")){
				strSQL.append(" AND M901.nm_shizai LIKE '%" + strShizaiName + "%' ");
				
			}
			strSQL.append(" ) AS M901_1 ");
			strSQL.append(" WHERE ");
			strSQL.append("     M901_1.NUM >= " + 
					//�擪�s�ԍ��̌v�Z
					toString( (toInteger(strListRowMax) * (toInteger(strPageNo)-1) +1 ), 0, 1, false, "1")
					+ " ");

		} catch (Exception e) {
			this.em.ThrowException(e, "���ރf�[�^���������Ɏ��s���܂����B");
		} finally {
			//�ϐ��̍폜
		}
		return strSQL;
	}
	
	/**
	 * �ގ��i�����F���ޏ��p�����[�^�[�i�[
	 *  : ���i-���ޏ������X�|���X�f�[�^�֊i�[����B
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

			for (int i = 0; i < 1; i++) {

				//�������ʂ̊i�[
				resTable.addFieldVale(i, "flg_return", "true");
				resTable.addFieldVale(i, "msg_error", "");
				resTable.addFieldVale(i, "no_errmsg", "");
				resTable.addFieldVale(i, "nm_class", "");
				resTable.addFieldVale(i, "cd_error", "");
				resTable.addFieldVale(i, "msg_system", "");
				
				Object items = (Object) lstGenryouData.get(i);

				//���ʂ����X�|���X�f�[�^�Ɋi�[
				resTable.addFieldVale(i, "titol", "�h���ރ}�X�^��茟�����h");
				resTable.addFieldVale(i, "max_cnt", toString(items));
				resTable.addFieldVale(i, "no_page", reqData.getFieldVale(0, 0, "no_page"));
				resTable.addFieldVale(i, "max_page",  toString(
						(toInteger(items) / toInteger(strListRowMax))
						, 0
						, 2
						, false
						, "1"));
				resTable.addFieldVale(i, "disp_cnt", toString(ConstManager.getConstValue(ConstManager.Category.�ݒ�,"LIST_ROW_MAX"), "50"));
				
			}
			
		} catch (Exception e) {
			this.em.ThrowException(e, "���ރf�[�^���������Ɏ��s���܂����B");

		} finally {

		}

	}
	
	/**
	 * �ގ��i�����F���ޏ��p�����[�^�[�i�[
	 *  : ���ޏ������X�|���X�f�[�^�֊i�[����B
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
				resTable.addFieldVale("rec" + i, "kigo_kojyo", toString(items[7]));
				resTable.addFieldVale("rec" + i, "cd_shizai", toString(items[3]));
				resTable.addFieldVale("rec" + i, "nm_shizai", toString(items[4]));
				resTable.addFieldVale("rec" + i, "tanka", toString(toDouble(items[5]),2,2,true,""));
				resTable.addFieldVale("rec" + i, "budomari", toString(toDouble(items[6]),2,2,true,""));
				resTable.addFieldVale("rec" + i, "cd_seihin", "");
				resTable.addFieldVale("rec" + i, "ryo", "");
				
			}
			
		} catch (Exception e) {
			this.em.ThrowException(e, "���ރf�[�^���������Ɏ��s���܂����B");

		} finally {

		}

	}
	
}