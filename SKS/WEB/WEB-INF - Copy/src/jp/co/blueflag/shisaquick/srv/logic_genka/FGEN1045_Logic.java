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
 * ���ތ��]��
 *  : �ގ��i�����F���ތ��]��
 * @author nishigawa
 * @since  2009/11/06
 */
public class FGEN1045_Logic extends LogicBase{
	
	/**
	 * �ގ��i�����F���ތ��]�����s���B
	 * : �C���X�^���X����
	 */
	public FGEN1045_Logic() {
		//���N���X�̃R���X�g���N�^
		super();

	}

	/**
	 * �ގ��i�����F���ތ��]�����s���B
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
		List<?> lstRecset = null;

		//���X�|���X�f�[�^�N���X
		RequestResponsKindBean resKind = null;
		
		try {
			//���X�|���X�f�[�^�i�@�\�j����
			resKind = new RequestResponsKindBean();

			//SQL���̍쐬
			strSql = DataCreateSQL(reqData);
			
			//SQL�����s
			super.createSearchDB();
			lstRecset = searchDB.dbSearch(strSql.toString());
			
			//�������ʂ��Ȃ��ꍇ
			if (lstRecset.size() == 0){
				em.ThrowException(ExceptionKind.�x��Exception,"W000401", strSql.toString(), "", "");
			}

			//�@�\ID�̐ݒ�
			String strKinoId = reqData.getID();
			resKind.setID(strKinoId);

			//�e�[�u�����̐ݒ�
			resKind.addTableItem("shizai");

			//���X�|���X�f�[�^�̌`��
			storageSeihinData(lstRecset, resKind.getTableItem("shizai"), reqData);
			
		} catch (Exception e) {
			this.em.ThrowException(e, "���ތ��]�������Ɏ��s���܂����B");
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
	 * ���ތ��]�����擾SQL�쐬
	 *  : ���ތ��]�������擾����SQL���쐬�B
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
			String strKaishaCd = null;
			String strKojoCd = null;
			String strShizaiCd = null;
			String strTanto_kaisya = null;
			String strTanto_kojyo = null;
			String strSeihinCd = null;

			//�S�����CD
			strTanto_kaisya = reqData.getFieldVale(0, 0, "cd_tanto_kaisya");
			//�S���H��CD
			strTanto_kojyo = toString(reqData.getFieldVale(0, 0, "cd_tanto_kojyo"),"null");
			
			//SQL���̍쐬
			strSQL.append(" SELECT ");
			strSQL.append("  M_ALL.cd_kaisha AS cd_kaisha ");	//0
			strSQL.append(" ,M_ALL.cd_busho AS cd_busho ");		//1
			strSQL.append(" ,M_ALL.cd_seihin AS cd_seihin ");	//2
			strSQL.append(" ,M_ALL.cd_shizai AS cd_shizai ");	//3
			strSQL.append(" ,M_ALL.nm_shizai AS nm_shizai ");	//4
			strSQL.append(" ,M_ALL.tanka AS tanka ");			//5
			strSQL.append(" ,M_ALL.budomari AS budomari ");		//6
			strSQL.append(" ,M_ALL.siyoryo AS siyoryo ");		//7
			strSQL.append(" ,M_ALL.fg_seihin AS fg_seihin ");	//8
			strSQL.append(" ,M302_1.nm_literal AS nm_literal ");//9
			strSQL.append(" ,M_ALL.cd_kaisha2 AS cd_kaisha2 ");	//10
			strSQL.append(" ,M_ALL.cd_busho2 AS cd_busho2 ");	//11
			strSQL.append(" ,M_ALL.cd_seihin2 AS cd_seihin2 ");	//12
			strSQL.append(" ,M_ALL.cd_shizai2 AS cd_shizai2 ");	//13
			strSQL.append(" ,M_ALL.nm_shizai2 AS nm_shizai2 ");	//14
			strSQL.append(" ,M_ALL.tanka2 AS tanka2 ");			//15
			strSQL.append(" ,M_ALL.budomari2 AS budomari2 ");	//16
			strSQL.append(" ,M_ALL.siyoryo2 AS siyoryo2 ");		//17
			strSQL.append(" ,M_ALL.fg_seihin2 AS fg_seihin2 ");	//18
			strSQL.append(" ,M302_2.nm_literal AS nm_literal2 ");//19
			strSQL.append(" FROM ");
			strSQL.append(" ( ");
			//���i���ތ���
			strSQL.append("  SELECT ");
			strSQL.append("   M801.cd_kaisha_seihin AS cd_kaisha ");
			strSQL.append("  ,M801.cd_busho_seihin AS cd_busho ");
			strSQL.append("  ,RIGHT('0000000000' + CONVERT(varchar,M801.cd_seihin),ISNULL(M104.keta_genryo,6)) AS cd_seihin ");
			strSQL.append("  ,RIGHT('0000000000' + CONVERT(varchar,M801.cd_shizai),ISNULL(M104.keta_genryo,6)) AS cd_shizai  ");
			strSQL.append("  ,M801.nm_shizai AS nm_shizai ");
			strSQL.append("  ,M801.tanka AS tanka ");
			strSQL.append("  ,M801.budomari AS budomari ");
			strSQL.append("  ,M801.siyoryo AS siyoryo ");
			strSQL.append("  ,M801.fg_seihin AS fg_seihin ");
			strSQL.append("  ,NULL AS cd_kaisha2 ");
			strSQL.append("  ,NULL AS cd_busho2 ");
			strSQL.append("  ,NULL AS cd_seihin2 ");
			strSQL.append("  ,NULL AS cd_shizai2 ");
			strSQL.append("  ,NULL AS nm_shizai2 ");
			strSQL.append("  ,NULL AS tanka2 ");
			strSQL.append("  ,NULL AS budomari2 ");
			strSQL.append("  ,NULL AS siyoryo2 ");
			strSQL.append("  ,NULL AS fg_seihin2 ");
			strSQL.append("  FROM ");
			strSQL.append("           ma_seihin AS M801 ");
			
			strSQL.append("  LEFT JOIN ma_busho AS M104 ");
			strSQL.append("  ON M801.cd_kaisha_seihin = M104.cd_kaisha ");
			strSQL.append("  AND M801.cd_busho_seihin = M104.cd_busho ");
			
			strSQL.append("  WHERE ");
			strSQL.append("      1 > 1 ");
			for (int i = 0 ; i < reqData.getCntRow(0) ; i++){
				
				if (reqData.getCntField(0, i) == 0){
					break;
				}

				strSQL.append(" OR (");
				
				//��ЃR�[�h
				strKaishaCd = reqData.getFieldVale(0, i, "cd_kaisya");
				//�H��R�[�h
				strKojoCd = reqData.getFieldVale(0, i, "cd_kojyo");
				//���i�R�[�h
				strSeihinCd = toString(reqData.getFieldVale(0, i, "cd_seihin"),"null");
				//���ރR�[�h
				strShizaiCd = reqData.getFieldVale(0, i, "cd_shizai");
				
				strSQL.append("      M801.cd_kaisha_seihin = " + strKaishaCd + " ");
				strSQL.append("  AND M801.cd_busho_seihin = " + strKojoCd + " ");
				strSQL.append("  AND M801.cd_seihin = " + strSeihinCd + " ");
				strSQL.append("  AND M801.cd_shizai = " + strShizaiCd + " ");
				
				strSQL.append(" ) ");
				
			}
			strSQL.append("  ");
			strSQL.append(" UNION ALL ");
			strSQL.append("  ");
			//���ތ���
			strSQL.append("  SELECT ");
			strSQL.append("   M901_1.cd_kaisha AS cd_kaisha ");
			strSQL.append("  ,M901_1.cd_busho AS cd_busho ");
			strSQL.append("  ,NULL AS cd_seihin ");
			strSQL.append("  ,RIGHT('0000000000' + CONVERT(varchar,M901_1.cd_shizai),ISNULL(M104.keta_genryo,6)) AS cd_shizai ");
			strSQL.append("  ,M901_1.nm_shizai AS nm_shizai ");
			strSQL.append("  ,M901_1.tanka AS tanka ");
			strSQL.append("  ,M901_1.budomari AS budomari ");
			strSQL.append("  ,NULL AS siyoryo ");
			strSQL.append("  ,NULL AS fg_seihin ");
			strSQL.append("  ,M901_2.cd_kaisha AS cd_kaisha2 ");
			strSQL.append("  ,M901_2.cd_busho AS cd_busho2 ");
			strSQL.append("  ,NULL AS cd_seihin2 ");
			strSQL.append("  ,RIGHT('0000000000' + CONVERT(varchar,M901_2.cd_shizai),ISNULL(M104.keta_genryo,6)) AS cd_shizai2 ");
			strSQL.append("  ,M901_2.nm_shizai AS nm_shizai2 ");
			strSQL.append("  ,M901_2.tanka AS tanka2 ");
			strSQL.append("  ,M901_2.budomari AS budomari2 ");
			strSQL.append("  ,NULL AS siyoryo2 ");
			strSQL.append("  ,NULL AS fg_seihin2 ");
			strSQL.append("  FROM ");
			strSQL.append("            ma_shizai AS M901_1 ");
			strSQL.append("  LEFT JOIN ma_shizai AS M901_2 ");
			strSQL.append("  ON  M901_2.cd_kaisha = " + strTanto_kaisya + " ");
			strSQL.append("  AND M901_2.cd_busho  = " + strTanto_kojyo + " ");
			strSQL.append("  AND M901_1.cd_shizai = M901_2.cd_shizai ");
			
			strSQL.append("  LEFT JOIN ma_busho AS M104 ");
			strSQL.append("  ON M901_1.cd_kaisha = M104.cd_kaisha ");
			strSQL.append("  AND M901_1.cd_busho = M104.cd_busho ");
			
			strSQL.append("  WHERE ");
			strSQL.append("      1 > 1 ");
			
			for (int i = 0 ; i < reqData.getCntRow(0) ; i++){
				
				if (reqData.getCntField(0, i) == 0){
					break;
				}
				
				strSQL.append(" OR (");
				
				//��ЃR�[�h
				strKaishaCd = reqData.getFieldVale(0, i, "cd_kaisya");
				//�H��R�[�h
				strKojoCd = reqData.getFieldVale(0, i, "cd_kojyo");
				//���ރR�[�h
				strShizaiCd = reqData.getFieldVale(0, i, "cd_shizai");
				
				strSQL.append("      M901_1.cd_kaisha = " + strKaishaCd + " ");
				strSQL.append("  AND M901_1.cd_busho  = " + strKojoCd + " ");
				strSQL.append("  AND M901_1.cd_shizai = " + strShizaiCd + " ");
				
				strSQL.append(" ) ");

			}
			
			strSQL.append(" ) AS M_ALL ");
			
			strSQL.append(" LEFT JOIN ma_literal AS M302_1 ");
			strSQL.append(" ON  M302_1.cd_category = 'K_kigo_kojyo' ");
			strSQL.append(" AND M302_1.cd_literal = CONVERT(varchar, M_ALL.cd_kaisha) + '-' + CONVERT(varchar, M_ALL.cd_busho) ");
			strSQL.append(" LEFT JOIN ma_literal AS M302_2 ");
			strSQL.append(" ON  M302_2.cd_category = 'K_kigo_kojyo' ");
			strSQL.append(" AND M302_2.cd_literal = CONVERT(varchar, M_ALL.cd_kaisha2) + '-' + CONVERT(varchar, M_ALL.cd_busho2) ");

		} catch (Exception e) {
			this.em.ThrowException(e, "���ތ��]���f�[�^���������Ɏ��s���܂����B");
		} finally {
			//�ϐ��̍폜
		}
		return strSQL;
	}

	
	/**
	 * �ގ��i�����F���ތ��]���p�����[�^�[�i�[
	 *  : ���ތ��]���������X�|���X�f�[�^�֊i�[����B
	 * @param lstGenryouData : �������ʏ�񃊃X�g
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private void storageSeihinData(
			
			List<?> lstGenryouData
			, RequestResponsTableBean resTable
			, RequestResponsKindBean reqData
			)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		Object[] items = null;		
		
		try {
			for (int i = 0 ; i < reqData.getCntRow(0) ; i++){
	
				items = getShizaiDBData(
						toString(reqData.getFieldVale(0, i, "cd_kaisya"), "")
						, toString(reqData.getFieldVale(0, i, "cd_kojyo"), "")
						, toString(reqData.getFieldVale(0, i, "cd_seihin"), "")
						, toString(reqData.getFieldVale(0, i, "cd_shizai"), "")
						, lstGenryouData);
				
				if (items == null ){
					//�������ʂ̊i�[
					resTable.addFieldVale("rec" + i, "flg_return", "false");
					resTable.addFieldVale("rec" + i, "msg_error", "�Ώێ��ށA�擾���s");
					resTable.addFieldVale("rec" + i, "no_errmsg", "");
					resTable.addFieldVale("rec" + i, "nm_class", "");
					resTable.addFieldVale("rec" + i, "cd_error", "");
					resTable.addFieldVale("rec" + i, "msg_system", "");
					
				}else{
					//�������ʂ̊i�[
					resTable.addFieldVale("rec" + i, "flg_return", "true");
					resTable.addFieldVale("rec" + i, "msg_error", "");
					resTable.addFieldVale("rec" + i, "no_errmsg", "");
					resTable.addFieldVale("rec" + i, "nm_class", "");
					resTable.addFieldVale("rec" + i, "cd_error", "");
					resTable.addFieldVale("rec" + i, "msg_system", "");
					
					//���ʂ����X�|���X�f�[�^�Ɋi�[
					if(toString(items[10]).equals("")){
						//���Ў��H��Ɏ��ނ������ꍇ
						resTable.addFieldVale("rec" + i, "cd_kaisya", toString(items[0]) );
						resTable.addFieldVale("rec" + i, "cd_kojyyo", toString(items[1]));
						resTable.addFieldVale("rec" + i, "kigo_kojyo", toString(items[9]));
						resTable.addFieldVale("rec" + i, "cd_shizai", toString(items[3]));
						if(toString(items[8]).equals("1")){
							resTable.addFieldVale("rec" + i, "nm_shizai", toString(items[4]) + "");
							
						}else{
							resTable.addFieldVale("rec" + i, "nm_shizai", toString(items[4]));
							
						}
						resTable.addFieldVale("rec" + i, "tanka", toString(toDouble(items[5]),2,2,true,""));
						resTable.addFieldVale("rec" + i, "budomari", toString(toDouble(items[6]),2,2,true,""));
						resTable.addFieldVale("rec" + i, "cd_seihin", toString(items[2]));
						if (toDouble(items[7], -1) > -1){
							resTable.addFieldVale("rec" + i, "ryo", toString(toDouble(items[7]),6,2,true,""));
							
						}else{
							resTable.addFieldVale("rec" + i, "ryo", "");
							
						}
						
					}else{
						//���Ў��H��Ɏ��ނ��L��ꍇ
						resTable.addFieldVale("rec" + i, "cd_kaisya", toString(items[10]) );
						resTable.addFieldVale("rec" + i, "cd_kojyyo", toString(items[11]));
						resTable.addFieldVale("rec" + i, "kigo_kojyo", toString(items[19]));
						resTable.addFieldVale("rec" + i, "cd_shizai", toString(items[13]));
						if(toString(items[18]).equals("1")){
							resTable.addFieldVale("rec" + i, "nm_shizai", toString(items[14]) + "");
							
						}else{
							resTable.addFieldVale("rec" + i, "nm_shizai", toString(items[14]));
							
						}
						resTable.addFieldVale("rec" + i, "tanka", toString(toDouble(items[15]),2,2,true,""));
						resTable.addFieldVale("rec" + i, "budomari", toString(toDouble(items[16]),2,2,true,""));
						resTable.addFieldVale("rec" + i, "cd_seihin", toString(items[12]));
						resTable.addFieldVale("rec" + i, "ryo", "");
						
					}
					

				}
				
			}

//			for (int i = 0; i < lstGenryouData.size(); i++) {
//
//				//�������ʂ̊i�[
//				resTable.addFieldVale("rec" + i, "flg_return", "true");
//				resTable.addFieldVale("rec" + i, "msg_error", "");
//				resTable.addFieldVale("rec" + i, "no_errmsg", "");
//				resTable.addFieldVale("rec" + i, "nm_class", "");
//				resTable.addFieldVale("rec" + i, "cd_error", "");
//				resTable.addFieldVale("rec" + i, "msg_system", "");
//				
//				Object[] items = (Object[]) lstGenryouData.get(i);
//				
//				//���ʂ����X�|���X�f�[�^�Ɋi�[
//				if(toString(items[10]).equals("")){
//					//���Ў��H��Ɏ��ނ������ꍇ
//					resTable.addFieldVale("rec" + i, "cd_kaisya", toString(items[0]) );
//					resTable.addFieldVale("rec" + i, "cd_kojyyo", toString(items[1]));
//					resTable.addFieldVale("rec" + i, "kigo_kojyo", toString(items[9]));
//					resTable.addFieldVale("rec" + i, "cd_shizai", toString(items[3]));
//					if(toString(items[8]).equals("1")){
//						resTable.addFieldVale("rec" + i, "nm_shizai", toString(items[4]) + "");
//						
//					}else{
//						resTable.addFieldVale("rec" + i, "nm_shizai", toString(items[4]));
//						
//					}
//					resTable.addFieldVale("rec" + i, "tanka", toString(items[5]));
//					resTable.addFieldVale("rec" + i, "budomari", toString(items[6]));
//					resTable.addFieldVale("rec" + i, "cd_seihin", toString(items[2]));
//					resTable.addFieldVale("rec" + i, "ryo", toString(items[7]));
//					
//				}else{
//					//���Ў��H��Ɏ��ނ��L��ꍇ
//					resTable.addFieldVale("rec" + i, "cd_kaisya", toString(items[10]) );
//					resTable.addFieldVale("rec" + i, "cd_kojyyo", toString(items[11]));
//					resTable.addFieldVale("rec" + i, "kigo_kojyo", toString(items[19]));
//					resTable.addFieldVale("rec" + i, "cd_shizai", toString(items[13]));
//					if(toString(items[18]).equals("1")){
//						resTable.addFieldVale("rec" + i, "nm_shizai", toString(items[14]) + "");
//						
//					}else{
//						resTable.addFieldVale("rec" + i, "nm_shizai", toString(items[14]));
//						
//					}
//					resTable.addFieldVale("rec" + i, "tanka", toString(items[15]));
//					resTable.addFieldVale("rec" + i, "budomari", toString(items[16]));
//					resTable.addFieldVale("rec" + i, "cd_seihin", toString(items[12]));
//					resTable.addFieldVale("rec" + i, "ryo", toString(items[17]));
//					
//				}
//				
//			}
			
		} catch (Exception e) {
			this.em.ThrowException(e, "���ތ��]���f�[�^���������Ɏ��s���܂����B");

		} finally {

		}

	}
	/**
	 * �Ώۃf�[�^�T�[�`
	 * @param kaisya
	 * @param kojyo
	 * @param seihin
	 * @param shizai
	 * @param dataList
	 * @return Object[]
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private Object[] getShizaiDBData(
			
			String kaisya
			, String kojyo
			, String seihin
			, String shizai
			, List<?> dataList 
			)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		Object[] ret = null;
		
		try {

			for (int i = 0; i < dataList.size(); i++) {
				
				Object[] items = (Object[]) dataList.get(i);
				
				if (toString(items[0],"").equals(kaisya) 
						&& toString(items[1],"").equals(kojyo)
						&& toString(items[2],"").equals(seihin)
						&& toString(items[3],"").equals(shizai)
						){
					
					ret = items;
					break;
					
				}

			}
				
		} catch (Exception e) {
			this.em.ThrowException(e, "");

		} finally {

		}
		
		return ret;
		
	}
	
}