package jp.co.blueflag.shisaquick.srv.logic;

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
 * �S���Ҍ����F�S���ҏ�񌟍�DB����
 *  : �S���Ҍ����F�S���ҏ�����������B
 * @author jinbo
 * @since  2009/04/07
 */
public class TantoushaDataSearchLogic extends LogicBase{

	/**
	 * �S���Ҍ����F�S���ҏ�񌟍�DB���� 
	 * : �C���X�^���X����
	 */
	public TantoushaDataSearchLogic() {
		//���N���X�̃R���X�g���N�^
		super();

	}

	/**
	 * �S���Ҍ����F�S���ҏ��擾SQL�쐬
	 *  : �S���ҏ����擾����SQL���쐬�B
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
			strSql = genryoData2CreateSQL(reqData, strSql);
			
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
			String strTableNm = reqData.getTableID(0);
			resKind.addTableItem(strTableNm);

			//���X�|���X�f�[�^�̌`��
			storageTantoushaData(lstRecset, resKind.getTableItem(strTableNm));
			
		} catch (Exception e) {
			this.em.ThrowException(e, "�S���҃f�[�^���������Ɏ��s���܂����B");
			
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
	 * �S���ҏ��擾SQL�쐬
	 *  : �S���ҏ����擾����SQL���쐬�B
	 * @param reqData�F���N�G�X�g�f�[�^
	 * @param strSql�F��������SQL
	 * @return strSql�F��������SQL
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private StringBuffer genryoData2CreateSQL(RequestResponsKindBean reqData, StringBuffer strSql) 
		throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		StringBuffer strAllSql = new StringBuffer();
		StringBuffer strWhere = new StringBuffer();
		
		try {
			String strListRowMax = ConstManager.getConstValue(ConstManager.Category.�ݒ�,"LIST_ROW_MAX");

			String strUserId = null;
			String strUserName = null;
			String strKaishaCd = null;
			String strBushoCd = null;
			String strGroupCd = null;
			String strTeamCd = null;
			String strPageNo = null;
			String dataId = null;
			
			//���[�UID�̎擾
			strUserId = reqData.getFieldVale(0, 0, "id_user");
			//���[�U���̎擾
			strUserName = reqData.getFieldVale(0, 0, "nm_user");
			//��ЃR�[�h�̎擾
			strKaishaCd = reqData.getFieldVale(0, 0, "cd_kaisha");
			//�����R�[�h�̎擾
			strBushoCd = reqData.getFieldVale(0, 0, "cd_busho");
			//�O���[�v�R�[�h�̎擾
			strGroupCd = reqData.getFieldVale(0, 0, "cd_group");
			//�����R�[�h�̎擾
			strTeamCd = reqData.getFieldVale(0, 0, "cd_team");
			//�I���y�[�WNo�̎擾
			strPageNo = reqData.getFieldVale(0, 0, "no_page");

			//�����f�[�^ID�擾
			for (int i = 0; i < userInfoData.getId_gamen().size(); i++) {
				if (userInfoData.getId_gamen().get(i).toString().equals("90")){
					//�S���҃}�X�^�����e�i���X��ʂ̃f�[�^ID��ݒ�
					dataId = userInfoData.getId_data().get(i).toString();
				}
			}
			
			//SQL���̍쐬	
			strAllSql.append(" SELECT ");
			strAllSql.append("	tbl.id_user");
			strAllSql.append(" ,tbl.nm_user");
			strAllSql.append(" ,tbl.cd_kengen");
			strAllSql.append(" ,ISNULL(tbl.nm_kengen,'') as nm_kengen");
			strAllSql.append(" ,tbl.cd_kaisha");
			strAllSql.append(" ,ISNULL(tbl.nm_kaisha,'') as nm_kaisha");
			strAllSql.append(" ,tbl.cd_busho");
			strAllSql.append(" ,ISNULL(tbl.nm_busho,'') as nm_busho");
			strAllSql.append(" ,tbl.cd_group");
			strAllSql.append(" ,ISNULL(tbl.nm_group,'') as nm_group");
			strAllSql.append(" ,tbl.cd_team");
			strAllSql.append(" ,ISNULL(tbl.nm_team,'') as nm_team");
			strAllSql.append(" ,tbl.cd_yakushoku");
			strAllSql.append(" ,ISNULL(tbl.nm_yakushoku, '') as nm_yakushoku");
			strAllSql.append(" ,ISNULL(CONVERT(VARCHAR,tbl.cd_tantokaisha), '') as cd_tantokaisha");
			strAllSql.append(" ,ISNULL(tbl.nm_tantokaisha, '') as nm_tantokaisha");
			strAllSql.append("	," + strListRowMax + " AS list_max_row");
			strAllSql.append("	,cnttbl.max_row ");
			strAllSql.append(" FROM (");
			
			strAllSql.append(" SELECT ");
			strAllSql.append("	datatbl.PageNO ");
			strAllSql.append(" ,datatbl.id_user");
			strAllSql.append(" ,datatbl.nm_user");
			strAllSql.append(" ,datatbl.cd_kengen");
			strAllSql.append(" ,datatbl.nm_kengen");
			strAllSql.append(" ,datatbl.cd_kaisha");
			strAllSql.append(" ,datatbl.nm_kaisha");
			strAllSql.append(" ,datatbl.cd_busho");
			strAllSql.append(" ,datatbl.nm_busho");
			strAllSql.append(" ,datatbl.cd_group");
			strAllSql.append(" ,datatbl.nm_group");
			strAllSql.append(" ,datatbl.cd_team");
			strAllSql.append(" ,datatbl.nm_team");
			strAllSql.append(" ,datatbl.cd_yakushoku");
			strAllSql.append(" ,datatbl.nm_yakushoku");
			strAllSql.append(" ,M107.cd_tantokaisha");
			strAllSql.append(" ,M1043.nm_kaisha as nm_tantokaisha");
			strAllSql.append(" FROM (");

			strSql.append(" SELECT ");
			strSql.append("	Convert(int,(ROW_NUMBER() OVER (ORDER BY M101.id_user)-1)/" + strListRowMax + " + 1) AS PageNO ");
			strSql.append(" ,RIGHT('0000000000' + CONVERT(varchar,M101.id_user),10) as id_user");
			strSql.append(" ,M101.nm_user");
			strSql.append(" ,M101.cd_kengen");
			strSql.append(" ,M102.nm_kengen");
			strSql.append(" ,M101.cd_kaisha");
			strSql.append(" ,M1041.nm_kaisha");
			strSql.append(" ,M101.cd_busho");
			strSql.append(" ,M1042.nm_busho");
			strSql.append(" ,M101.cd_group");
			strSql.append(" ,M105.nm_group");
			strSql.append(" ,M101.cd_team");
			strSql.append(" ,M106.nm_team");
			strSql.append(" ,M101.cd_yakushoku");
			strSql.append(" ,M302.nm_literal as nm_yakushoku");
//			strSql.append(" ,M107.cd_tantokaisha");
//			strSql.append(" ,M1043.nm_kaisha as nm_tantokaisha");
			strSql.append(" FROM ma_user M101");
			strSql.append("      LEFT JOIN (SELECT cd_kaisha, nm_kaisha FROM ma_busho GROUP BY cd_kaisha, nm_kaisha) M1041");
			strSql.append("      ON M1041.cd_kaisha = M101.cd_kaisha");
			strSql.append("      LEFT JOIN ma_busho M1042");
			strSql.append("      ON M1042.cd_kaisha = M101.cd_kaisha");
			strSql.append("      AND M1042.cd_busho = M101.cd_busho");
			strSql.append("      LEFT JOIN ma_kengen M102");
			strSql.append("      ON M101.cd_kengen = M102.cd_kengen");
			strSql.append("      LEFT JOIN ma_literal M302");
			strSql.append("      ON M302.cd_category = 'K_yakusyoku'");
			strSql.append("      AND M302.cd_literal = M101.cd_yakushoku");
			strSql.append("      LEFT JOIN ma_group M105");
			strSql.append("      ON M105.cd_group = M101.cd_group");
			strSql.append("      LEFT JOIN ma_team M106");
			strSql.append("      ON M106.cd_group = M101.cd_group");
			strSql.append("      AND M106.cd_team = M101.cd_team");
//			strSql.append("      LEFT JOIN ma_tantokaisya M107");
//			strSql.append("      ON M107.id_user = M101.id_user");
//			strSql.append("      LEFT JOIN (SELECT cd_kaisha, nm_kaisha FROM ma_busho GROUP BY cd_kaisha, nm_kaisha) M1043");
//			strSql.append("      ON M1043.cd_kaisha = M107.cd_tantokaisha");
			
			//���[�UID�����͂���Ă���ꍇ
			if (!strUserId.equals("")) {
				if (strWhere.toString().equals("")) {
					strWhere.append(" WHERE");
				} else {
					strWhere.append(" AND");
				}
				strWhere.append(" M101.id_user = '");
				strWhere.append(strUserId + "'");
			}
			
			//���[�U�������͂���Ă���ꍇ
			if (!strUserName.equals("")) {
				if (strWhere.toString().equals("")) {
					strWhere.append(" WHERE");
				} else {
					strWhere.append(" AND");
				}
				strWhere.append(" M101.nm_user = '");
				strWhere.append(strUserName + "'");
			}
			
			//��Ђ����͂���Ă���ꍇ
			if (!strKaishaCd.equals("")) {
				if (strWhere.toString().equals("")) {
					strWhere.append(" WHERE");
				} else {
					strWhere.append(" AND");
				}
				strWhere.append(" M101.cd_kaisha = ");
				strWhere.append(strKaishaCd);
			}

			//���������͂���Ă���ꍇ
			if (!strBushoCd.equals("")) {
				if (strWhere.toString().equals("")) {
					strWhere.append(" WHERE");
				} else {
					strWhere.append(" AND");
				}
				strWhere.append(" M101.cd_busho = ");
				strWhere.append(strBushoCd);
			}

			//�O���[�v�����͂���Ă���ꍇ
			if (!strGroupCd.equals("")) {
				if (strWhere.toString().equals("")) {
					strWhere.append(" WHERE");
				} else {
					strWhere.append(" AND");
				}
				strWhere.append(" M101.cd_group = ");
				strWhere.append(strGroupCd);
			}

			//�`�[�������͂���Ă���ꍇ
			if (!strTeamCd.equals("")) {
				if (strWhere.toString().equals("")) {
					strWhere.append(" WHERE");
				} else {
					strWhere.append(" AND");
				}
				strWhere.append(" M101.cd_team = ");
				strWhere.append(strTeamCd);
			}
			strSql.append(strWhere);
			
			//�������������ݒ�
			//�����̂�
			if(dataId.equals("1")) {
				if (strWhere.toString().equals("")) {
					strSql.append(" WHERE");
				} else {
					strSql.append(" AND");
				}
				strSql.append(" M101.id_user = ");
				strSql.append(userInfoData.getId_user());

			//�S��
			} else if (dataId.equals("9")) { 
				//�Ȃ�
			}
			
			strAllSql.append(strSql);
			strAllSql.append("	) AS datatbl ");
			
			strAllSql.append(" LEFT JOIN ma_tantokaisya M107");
			strAllSql.append(" ON M107.id_user = datatbl.id_user");
			strAllSql.append(" LEFT JOIN (SELECT cd_kaisha, nm_kaisha FROM ma_busho GROUP BY cd_kaisha, nm_kaisha) M1043");
			strAllSql.append(" ON M1043.cd_kaisha = M107.cd_tantokaisha");

			strAllSql.append("	) AS tbl ");
			strAllSql.append(",(SELECT COUNT(*) as max_row FROM (" + strSql + ") AS CT ) AS cnttbl ");
			strAllSql.append("	WHERE tbl.PageNO = " + strPageNo);
			strAllSql.append(" ORDER BY ");
			strAllSql.append(" tbl.id_user ");
			
			strSql = strAllSql;

		} catch (Exception e) {
			this.em.ThrowException(e, "�S���҃f�[�^���������Ɏ��s���܂����B");
		} finally {
			//�ϐ��̍폜
			strAllSql = null;
			strWhere = null;
		}
		return strSql;
	}

	/**
	 * �S���Ҍ����F�S���ҏ��p�����[�^�[�i�[
	 *  : �S���ҏ������X�|���X�f�[�^�֊i�[����B
	 * @param lstTantouShaData : �������ʏ�񃊃X�g
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private void storageTantoushaData(List<?> lstTantouShaData, RequestResponsTableBean resTable) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try {
			int iRow = 1;
			String strUserId = "";
			String strTantoKaishaCd = "";
			String strTantoKaishaName = "";

			for (int i = 0; i < lstTantouShaData.size(); i++) {

				//�������ʂ̊i�[
				resTable.addFieldVale(iRow-1, "flg_return", "true");
				resTable.addFieldVale(iRow-1, "msg_error", "");
				resTable.addFieldVale(iRow-1, "no_errmsg", "");
				resTable.addFieldVale(iRow-1, "nm_class", "");
				resTable.addFieldVale(iRow-1, "cd_error", "");
				resTable.addFieldVale(iRow-1, "msg_system", "");
				
				Object[] items = (Object[]) lstTantouShaData.get(i);

				//���ʂ����X�|���X�f�[�^�Ɋi�[
				if (!strUserId.equals(items[0].toString())) {
					//���[�UID���O���R�[�h�ƈقȂ�ꍇ
					
					if (i > 0 && i < lstTantouShaData.size()) {
						//�擪�s�ȊO�Ŋ��ŏI�s�ȊO
						
						//rec�ɒS����Ђ�ݒ�
						resTable.addFieldVale(iRow-1, "cd_tantokaisha", strTantoKaishaCd);
						resTable.addFieldVale(iRow-1, "nm_tantokaisha", strTantoKaishaName);

						iRow += 1;
					}

					resTable.addFieldVale(iRow-1, "no_row", Integer.toString(iRow));
					resTable.addFieldVale(iRow-1, "id_user", items[0].toString());
					resTable.addFieldVale(iRow-1, "nm_user", items[1].toString());
					resTable.addFieldVale(iRow-1, "cd_kengen", items[2].toString());
					resTable.addFieldVale(iRow-1, "nm_kengen", items[3].toString());
					resTable.addFieldVale(iRow-1, "cd_kaisha", items[4].toString());
					resTable.addFieldVale(iRow-1, "nm_kaisha", items[5].toString());
					resTable.addFieldVale(iRow-1, "cd_busho", items[6].toString());
					resTable.addFieldVale(iRow-1, "nm_busho", items[7].toString());
					resTable.addFieldVale(iRow-1, "cd_group", items[8].toString());
					resTable.addFieldVale(iRow-1, "nm_group", items[9].toString());
					resTable.addFieldVale(iRow-1, "cd_team", items[10].toString());
					resTable.addFieldVale(iRow-1, "nm_team", items[11].toString());
					resTable.addFieldVale(iRow-1, "cd_yakushoku", items[12].toString());
					resTable.addFieldVale(iRow-1, "nm_yakushoku", items[13].toString());
					resTable.addFieldVale(iRow-1, "list_max_row", items[16].toString());
					resTable.addFieldVale(iRow-1, "max_row", items[17].toString());
				}

				//���[�UID���O��̃��R�[�h�Ɠ����ꍇ
				if (strUserId.equals(items[0].toString())) {
					strTantoKaishaCd += "," + items[14].toString();
					strTantoKaishaName += "\n" + items[15].toString();

				//���[�UID���O��̃��R�[�h�ƈقȂ�ꍇ
				} else {
					//�f�[�^�̑ޔ�
					strUserId = items[0].toString();
					strTantoKaishaCd = items[14].toString();
					strTantoKaishaName = items[15].toString();
				}
			}

			resTable.addFieldVale(iRow-1, "cd_tantokaisha", strTantoKaishaCd);
			resTable.addFieldVale(iRow-1, "nm_tantokaisha", strTantoKaishaName);

		} catch (Exception e) {
			this.em.ThrowException(e, "�S���҃f�[�^���������Ɏ��s���܂����B");

		} finally {

		}

	}
	
}
