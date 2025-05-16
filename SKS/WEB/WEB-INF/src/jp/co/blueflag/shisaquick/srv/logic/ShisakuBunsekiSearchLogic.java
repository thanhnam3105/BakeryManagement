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
import jp.co.blueflag.shisaquick.srv.commonlogic.ImmGetConvList;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * ���앪�̓f�[�^�m�F�F�������͏�񌟍�DB����
 *  : ���앪�̓f�[�^�m�F�F�������͏�����������B
 * @author jinbo
 * @since  2009/04/24
 */
public class ShisakuBunsekiSearchLogic extends LogicBase{
	
	/**
	 * ���앪�̓f�[�^�m�F�F�������͏�񌟍�DB���� 
	 * : �C���X�^���X����
	 */
	public ShisakuBunsekiSearchLogic() {
		//���N���X�̃R���X�g���N�^
		super();

	}

	/**
	 * ���앪�̓f�[�^�m�F�F�������͏��擾SQL�쐬
	 *  : �������͏����擾����SQL���쐬�B
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
			strSql = genryoDataCreateSQL(reqData, strSql);
			
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
			storageGenryouData(lstRecset, resKind.getTableItem(0));
			
		} catch (Exception e) {
			this.em.ThrowException(e, "�����f�[�^���������Ɏ��s���܂����B");
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
	 * �������͏��擾SQL�쐬
	 *  : �������͏����擾����SQL���쐬�B
	 * @param reqData�F���N�G�X�g�f�[�^
	 * @param strSql�F��������SQL
	 * @return strSql�F��������SQL
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private StringBuffer genryoDataCreateSQL(RequestResponsKindBean reqData, StringBuffer strSql) 
		throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		StringBuffer strAllSql = new StringBuffer();

		try {
			String strJokenKbn1 = null;
			String strJokenKbn2 = null;
			String strGenryoCd = null;
			String strGenryoName = null;
			String strKaishaCd = null;
			String strKojoCd = null;
			String strPageNo = null;
			String strShiyoFlg = null;		//QP@00412_�V�T�N�C�b�N���� No.4
			
			String strListRowMax = ConstManager.getConstValue(ConstManager.Category.�ݒ�,"LIST_ROW_MAX");

			//�Ώی����i�V�K�����j�̎擾
			strJokenKbn1 = reqData.getFieldVale(0, 0, "taisho_genryo1");
			//�Ώی����i���������j�̎擾
			strJokenKbn2 = reqData.getFieldVale(0, 0, "taisho_genryo2");
			//�����R�[�h�̎擾
			strGenryoCd = reqData.getFieldVale(0, 0, "cd_genryo");
			//�������̎擾
			strGenryoName = reqData.getFieldVale(0, 0, "nm_genryo");
			//��ЃR�[�h�̎擾
			strKaishaCd = reqData.getFieldVale(0, 0, "cd_kaisha");
			//�H��R�[�h�̎擾
			strKojoCd = reqData.getFieldVale(0, 0, "cd_busho");
			//�I���y�[�WNo�̎擾
			strPageNo = reqData.getFieldVale(0, 0, "num_selectRow");
//add start --------------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.4
			//�g�p���уt���O�̎擾
			strShiyoFlg = reqData.getFieldVale(0, 0, "flg_shiyo");
//add end --------------------------------------------------------------------------------------

			//SQL���̍쐬
			strAllSql.append("SELECT");
			strAllSql.append("  tbl.cd_kaisha");
			strAllSql.append(" ,tbl.cd_busho");
			strAllSql.append(" ,tbl.cd_genryo");
			strAllSql.append(" ,ISNULL(tbl.nm_kaisha,'') as nm_kaisha");
			strAllSql.append(" ,ISNULL(tbl.nm_busho,'') as nm_busho");
			strAllSql.append(" ,tbl.nm_genryo");
			strAllSql.append(" ,CASE WHEN tbl.ritu_sakusan IS NULL THEN ISNULL(CONVERT(VARCHAR,tbl.ritu_sakusan),'') ELSE CONVERT(VARCHAR,CONVERT(DECIMAL(5,2),tbl.ritu_sakusan)) END as ritu_sakusan");
			strAllSql.append(" ,CASE WHEN tbl.ritu_shokuen IS NULL THEN ISNULL(CONVERT(VARCHAR,tbl.ritu_shokuen),'') ELSE CONVERT(VARCHAR,CONVERT(DECIMAL(5,2),tbl.ritu_shokuen)) END as ritu_shokuen");
			strAllSql.append(" ,CASE WHEN tbl.ritu_sousan IS NULL THEN ISNULL(CONVERT(VARCHAR,tbl.ritu_sousan),'') ELSE CONVERT(VARCHAR,CONVERT(DECIMAL(5,2),tbl.ritu_sousan)) END as ritu_sousan");
			strAllSql.append(" ,CASE WHEN tbl.ritu_abura IS NULL THEN ISNULL(CONVERT(VARCHAR,tbl.ritu_abura),'') ELSE CONVERT(VARCHAR,CONVERT(DECIMAL(5,2),tbl.ritu_abura)) END as ritu_abura");
			strAllSql.append(" ,ISNULL(tbl.hyojian,'') as hyojian");
			strAllSql.append(" ,ISNULL(tbl.tenkabutu,'') as tenkabutu");
			strAllSql.append(" ,ISNULL(tbl.memo,'') as memo");
			strAllSql.append(" ,ISNULL(tbl.no_eiyo1,'') as no_eiyo1");
			strAllSql.append(" ,ISNULL(tbl.no_eiyo2,'') as no_eiyo2");
			strAllSql.append(" ,ISNULL(tbl.no_eiyo3,'') as no_eiyo3");
			strAllSql.append(" ,ISNULL(tbl.no_eiyo4,'') as no_eiyo4");
			strAllSql.append(" ,ISNULL(tbl.no_eiyo5,'') as no_eiyo5");
			strAllSql.append(" ,ISNULL(tbl.wariai1,'') as wariai1");
			strAllSql.append(" ,ISNULL(tbl.wariai2,'') as wariai2");
			strAllSql.append(" ,ISNULL(tbl.wariai3,'') as wariai3");
			strAllSql.append(" ,ISNULL(tbl.wariai4,'') as wariai4");
			strAllSql.append(" ,ISNULL(tbl.wariai5,'') as wariai5");
			strAllSql.append(" ,ISNULL(tbl.kbn_haishi,'') as kbn_haishi");
			strAllSql.append(" ,ISNULL(tbl.cd_kakutei, '') as cd_kakutei");
			strAllSql.append(" ,ISNULL(tbl.id_kakunin, '') as id_kakunin");
			strAllSql.append(" ,ISNULL(tbl.nm_kakunin, '') as nm_kakunin");
			strAllSql.append(" ,ISNULL(tbl.dt_kakunin, '') as dt_kakunin");
			strAllSql.append(" ,ISNULL(tbl.dt_konyu, '') as dt_konyu");
			strAllSql.append(" ,ISNULL(tbl.tanka, '') as tanka");
			strAllSql.append(" ,ISNULL(tbl.budomari, '') as budomari");
			strAllSql.append(" ,ISNULL(tbl.id_toroku, '') as id_toroku");
			strAllSql.append(" ,ISNULL(tbl.dt_toroku, '') as dt_toroku");
			strAllSql.append(" ,ISNULL(tbl.nm_toroku, '') as nm_toroku");
			strAllSql.append(" ,ISNULL(tbl.id_koshin, '') as id_koshin");
			strAllSql.append(" ,ISNULL(tbl.dt_koshin, '') as dt_koshin");
			strAllSql.append(" ,ISNULL(tbl.nm_koshin, '') as nm_koshin");
			strAllSql.append("	," + strListRowMax + " AS list_max_row");
			strAllSql.append("	,cnttbl.max_row ");
//add start --------------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.4
			strAllSql.append(" ,ISNULL(tbl.flg_shiyo, '') as flg_shiyo");
			strAllSql.append(" ,ISNULL(tbl.flg_mishiyo, '') as flg_mishiyo");
//add end --------------------------------------------------------------------------------------
			//QP@20505_No11 Start
			strAllSql.append(" ,CASE WHEN tbl.ritu_msg IS NULL THEN ISNULL(CONVERT(VARCHAR,tbl.ritu_msg),'') ELSE CONVERT(VARCHAR,CONVERT(DECIMAL(5,2),tbl.ritu_msg)) END as ritu_msg ");
			//QP@20505_No11 End
			strAllSql.append(" FROM (");
			
			strSql.append("SELECT");
			strSql.append("	(CASE WHEN ROW_NUMBER() OVER (ORDER BY M402.cd_genryo)%" + strListRowMax + " = 0 THEN " + strListRowMax + " ELSE ROW_NUMBER() OVER (ORDER BY M402.cd_genryo)%" + strListRowMax + " END) AS no_row ");
			strSql.append("	,Convert(int,(ROW_NUMBER() OVER (ORDER BY M402.cd_genryo)-1)/" + strListRowMax + " + 1) AS PageNO ");
			strSql.append(" ,M402.cd_kaisha");
			//�H�ꂪ�I������Ă���ꍇ
			if (!strKojoCd.equals("")) {
				strSql.append(" ,M402.cd_busho");
			} else {
				strSql.append(" ,'' as cd_busho");
			}
			strSql.append(" ,M402.cd_genryo");
			strSql.append(" ,M104.nm_kaisha");
			strSql.append(" ,M104.nm_busho");
			strSql.append(" ,M402.nm_genryo");
			strSql.append(" ,M401.ritu_sakusan");
			strSql.append(" ,M401.ritu_shokuen");
			strSql.append(" ,M401.ritu_sousan");
			strSql.append(" ,M401.ritu_abura");
			strSql.append(" ,M401.hyojian");
			strSql.append(" ,M401.tenkabutu");
			strSql.append(" ,M401.memo");
			strSql.append(" ,M401.no_eiyo1");
			strSql.append(" ,M401.no_eiyo2");
			strSql.append(" ,M401.no_eiyo3");
			strSql.append(" ,M401.no_eiyo4");
			strSql.append(" ,M401.no_eiyo5");
			strSql.append(" ,M401.wariai1");
			strSql.append(" ,M401.wariai2");
			strSql.append(" ,M401.wariai3");
			strSql.append(" ,M401.wariai4");
			strSql.append(" ,M401.wariai5");
			strSql.append(" ,M401.kbn_haishi");
			strSql.append(" ,convert(varchar,M401.cd_kakutei) as cd_kakutei");
			strSql.append(" ,convert(varchar,M401.id_kakunin) AS id_kakunin");
			strSql.append(" ,M1011.nm_user AS nm_kakunin ");
			strSql.append(" ,ISNULL(convert(varchar(10),M401.dt_kakunin,111),'') as dt_kakunin");
			//�H�ꂪ�I������Ă���ꍇ
			if (!strKojoCd.equals("")) {
				strSql.append(" ,convert(varchar(10),M402.dt_konyu,111) as dt_konyu");
			} else {
				strSql.append(" ,'' as dt_konyu");
			}
			strSql.append(" ,'' AS tanka ");
			strSql.append(" ,'' AS budomari ");
			strSql.append(" ,convert(varchar,M401.id_toroku) AS id_toroku ");
			strSql.append(" ,convert(varchar(10),M401.dt_toroku,111) AS dt_toroku ");
			strSql.append(" ,M1012.nm_user AS nm_toroku ");
			strSql.append(" ,convert(varchar,M401.id_koshin) AS id_koshin ");
//			strSql.append(" ,M401.dt_koshin AS dt_koshin ");
			strSql.append(" ,ISNULL(convert(varchar(10),M401.dt_koshin,111),'') as dt_koshin");
			strSql.append(" ,M1013.nm_user AS nm_koshin ");
//add start --------------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.4
			strSql.append(" ,M402.flg_shiyo AS flg_shiyo ");
			strSql.append(" ,M402.flg_mishiyo AS flg_mishiyo ");
//add end --------------------------------------------------------------------------------------
			//QP@20505_No11 Start
			strSql.append(" ,M401.ritu_msg");
			//QP@20505_No11 End
			//�H�ꂪ�I������Ă���ꍇ
			if (!strKojoCd.equals("")) {
				strSql.append(" FROM ma_genryokojo M402");
			} else {
				strSql.append(" FROM (");
//mod start --------------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.4
				strSql.append(" SELECT MG1.cd_kaisha, MG1.cd_genryo, MG1.cd_busho, MG2.nm_genryo, MG2.flg_shiyo, MG2.flg_mishiyo");
//mod end --------------------------------------------------------------------------------------
				strSql.append(" FROM (");
				strSql.append(" SELECT cd_kaisha, cd_genryo, MIN(cd_busho) as cd_busho");
				strSql.append(" FROM ma_genryokojo");
				strSql.append(" GROUP BY cd_kaisha, cd_genryo");
				strSql.append(" ) as MG1");
				strSql.append(" INNER JOIN ma_genryokojo MG2");
				strSql.append(" ON MG1.cd_kaisha = MG2.cd_kaisha");
				strSql.append(" AND MG1.cd_busho = MG2.cd_busho");
				strSql.append(" AND MG1.cd_genryo = MG2.cd_genryo");
				strSql.append(" ) M402");
			}
			
			strSql.append("      INNER JOIN ma_genryo M401");
			strSql.append("      ON M402.cd_kaisha = M401.cd_kaisha");
			strSql.append("      AND M402.cd_genryo = M401.cd_genryo");
			strSql.append("      LEFT JOIN ma_user M1011");
			strSql.append("      ON M401.cd_kaisha = M1011.cd_kaisha");
			strSql.append("      AND M401.id_kakunin = M1011.id_user");
			strSql.append("      LEFT JOIN ma_user M1012");
			strSql.append("      ON M401.cd_kaisha = M1012.cd_kaisha");
			strSql.append("      AND M401.id_toroku = M1012.id_user");
			strSql.append("      LEFT JOIN ma_user M1013");
			strSql.append("      ON M401.cd_kaisha = M1013.cd_kaisha");
			strSql.append("      AND M401.id_koshin = M1013.id_user");
			strSql.append("      LEFT JOIN ma_busho M104");
			strSql.append("      ON M402.cd_kaisha = M104.cd_kaisha");
			strSql.append("      AND M402.cd_busho = M104.cd_busho");
			strSql.append(" WHERE M402.cd_kaisha = ");
			strSql.append(strKaishaCd);

			//�H�ꂪ�I������Ă���ꍇ
			if (!strKojoCd.equals("")) {
				strSql.append(" AND M402.cd_busho IN (");
				strSql.append(strKojoCd);
				strSql.append("," + ConstManager.getConstValue(ConstManager.Category.�ݒ�, "SHINKIGENRYO_BUSHOCD") + ")");
			}

			//�����R�[�h�����͂���Ă���ꍇ
			if (!strGenryoCd.equals("")) {
				strSql.append(" AND M401.cd_genryo LIKE '%");
				strSql.append(strGenryoCd);
				strSql.append("%'");
			}
			
			//�����������͂���Ă���ꍇ
			if (!strGenryoName.equals("")) {
//				strSql.append(" AND M402.nm_genryo LIKE '%");
//				strSql.append(strGenryoName);
//				strSql.append("%'");
				
				strSql.append(" AND ( ");
				
				//�����A�J�i�������W�b�N�ǉ��@2009/8/28�@nishigawa�@START
				
				//IME�ϊ��擾�p�N���X����
				ImmGetConvList ImeSearch = new ImmGetConvList();
				
				//�ϊ����s
				ArrayList arySearch = ImeSearch.ImmGetConvListChange(strGenryoName);
				
				for(int j = 0; j < arySearch.size(); j++){
					
					//��╶���擾
					String getStr = (String)arySearch.get(j);
					
					//1���ڂ̏ꍇ
					if(j < 1){
						
					//2���ڈȍ~
					}else{
						
						strSql.append(" OR ");
						
					}
					
					//SQL�ݒ�
					strSql.append(" M402.nm_genryo LIKE '%" + getStr + "%'");
					
				}
				
				strSql.append(" ) ");

				//�����A�J�i�������W�b�N�ǉ��@2009/8/28�@nishigawa�@End
				
			}

			//�V�K�����������Ώۂ̏ꍇ
			if (!strJokenKbn1.equals("") && strJokenKbn2.equals("")) {
				strSql.append(" AND SUBSTRING(M401.cd_genryo,1,1) > '9'");
			
			//���������������Ώۂ̏ꍇ
			}else if (strJokenKbn1.equals("") && !strJokenKbn2.equals("")) {
				strSql.append(" AND SUBSTRING(M401.cd_genryo,1,1) <= '9'");
			}

//add start --------------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.4
			//�g�p���с�1�̏ꍇ�A�����ǉ�
			if(strShiyoFlg.equals("1")) {
				strSql.append(" AND M402.flg_shiyo = 1");
			}
//add end --------------------------------------------------------------------------------------
			
			strAllSql.append(strSql);
			strAllSql.append("	) AS tbl ");
			strAllSql.append(",(SELECT COUNT(*) as max_row FROM (" + strSql + ") AS CT ) AS cnttbl ");
			strAllSql.append("	WHERE tbl.PageNO = " + strPageNo);
			strAllSql.append(" ORDER BY ");
			strAllSql.append(" tbl.no_row ");
			
			strSql = strAllSql;

		} catch (Exception e) {
			this.em.ThrowException(e, "�����f�[�^���������Ɏ��s���܂����B");
		} finally {
			//�ϐ��̍폜
			strAllSql = null;
		}
		return strSql;
	}

	/**
	 * ���앪�̓f�[�^�m�F�F�������͏��p�����[�^�[�i�[
	 *  : �������͏������X�|���X�f�[�^�֊i�[����B
	 * @param lstGenryouData : �������ʏ�񃊃X�g
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private void storageGenryouData(List<?> lstGenryouData, RequestResponsTableBean resTable)
		throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try {
			
			for (int i = 0; i < lstGenryouData.size(); i++) {

				//�������ʂ̊i�[
				resTable.addFieldVale(i, "flg_return", "true");
				resTable.addFieldVale(i, "msg_error", "");
				resTable.addFieldVale(i, "no_errmsg", "");
				resTable.addFieldVale(i, "nm_class", "");
				resTable.addFieldVale(i, "cd_error", "");
				resTable.addFieldVale(i, "msg_system", "");
				
				Object[] items = (Object[]) lstGenryouData.get(i);

				//���ʂ����X�|���X�f�[�^�Ɋi�[
				resTable.addFieldVale(i, "cd_kaisha", items[0].toString());
				resTable.addFieldVale(i, "cd_busho", items[1].toString());
				resTable.addFieldVale(i, "cd_genryo", items[2].toString());
				resTable.addFieldVale(i, "nm_kaisha", items[3].toString());
				resTable.addFieldVale(i, "nm_busho", items[4].toString());
				resTable.addFieldVale(i, "nm_genryo", items[5].toString());
				resTable.addFieldVale(i, "ritu_sakusan", items[6].toString());
				resTable.addFieldVale(i, "ritu_shokuen", items[7].toString());
				resTable.addFieldVale(i, "ritu_sousan", items[8].toString());
				resTable.addFieldVale(i, "ritu_abura", items[9].toString());
				resTable.addFieldVale(i, "hyojian", items[10].toString());
				resTable.addFieldVale(i, "tenkabutu", items[11].toString());
				resTable.addFieldVale(i, "memo", items[12].toString());
				resTable.addFieldVale(i, "no_eiyo1", items[13].toString());
				resTable.addFieldVale(i, "no_eiyo2", items[14].toString());
				resTable.addFieldVale(i, "no_eiyo3", items[15].toString());
				resTable.addFieldVale(i, "no_eiyo4", items[16].toString());
				resTable.addFieldVale(i, "no_eiyo5", items[17].toString());
				resTable.addFieldVale(i, "wariai1", items[18].toString());
				resTable.addFieldVale(i, "wariai2", items[19].toString());
				resTable.addFieldVale(i, "wariai3", items[20].toString());
				resTable.addFieldVale(i, "wariai4", items[21].toString());
				resTable.addFieldVale(i, "wariai5", items[22].toString());
				resTable.addFieldVale(i, "kbn_haishi", items[23].toString());
				resTable.addFieldVale(i, "cd_kakutei", items[24].toString());
				resTable.addFieldVale(i, "id_kakunin", items[25].toString());
				resTable.addFieldVale(i, "nm_kakunin", items[26].toString());
				resTable.addFieldVale(i, "dt_kakunin", items[27].toString());
				resTable.addFieldVale(i, "dt_konyu", items[28].toString());
				resTable.addFieldVale(i, "tanka", items[29].toString());
				resTable.addFieldVale(i, "budomari", items[30].toString());
				resTable.addFieldVale(i, "id_toroku", items[31].toString());
				resTable.addFieldVale(i, "dt_toroku", items[32].toString());
				resTable.addFieldVale(i, "nm_toroku", items[33].toString());
				resTable.addFieldVale(i, "id_koshin", items[34].toString());
				resTable.addFieldVale(i, "dt_koshin", items[35].toString());
				resTable.addFieldVale(i, "nm_koshin", items[36].toString());
				resTable.addFieldVale(i, "list_max_row", items[37].toString());
				resTable.addFieldVale(i, "max_row", items[38].toString());
//add start --------------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.4
				resTable.addFieldVale(i, "flg_shiyo", items[39].toString());
				resTable.addFieldVale(i, "flg_mishiyo", items[40].toString());
//add end --------------------------------------------------------------------------------------
				//QP@20505_No11 Start
				resTable.addFieldVale(i, "ritu_msg", items[41].toString());
				//QP@20505_No11 End
			}
			
			int a;
			a = 0;

		} catch (Exception e) {
			this.em.ThrowException(e, "�����f�[�^���������Ɏ��s���܂����B");

		} finally {

		}

	}
	
}