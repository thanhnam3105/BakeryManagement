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
import jp.co.blueflag.shisaquick.srv.common.ExceptionManager.ExceptionKind;

/**
 * 
 * ���͌�������DB����
 *  : ���͌���������������B
 * @author TT.katayama
 * @since  2009/04/13
 */
public class ShisakuGenryoSearchLogic extends LogicBase{

	/**
	 * ���͌��������R���X�g���N�^ 
	 * : �C���X�^���X����
	 */
	public ShisakuGenryoSearchLogic() {
		//���N���X�̃R���X�g���N�^
		super();

	}
	
	/**
	 * ���͌���DB�����������W�b�N�Ǘ�
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

		String strKinoId = "";
		String strTableNm = "";

		StringBuffer strSql = new StringBuffer();		//�����pSQL�i�[�p������
		List<?> lstRecset = null;										//�������ʊi�[�p���X�g

		//���X�|���X�f�[�^�N���X
		RequestResponsKindBean resKind = null;
		
		try {
			
			//���X�|���X�f�[�^�i�@�\�j����
			resKind = new RequestResponsKindBean();

			//�@�F���͌����f�[�^�擾SQL�쐬�������Ăяo���A���͌��������pSQL���쐬����B
			strSql = this.��enryoDataCreateSQL(reqData);
			
			//�A:�쐬�������͌��������pSQL�Ńf�[�^�x�[�X������p���A���쌴���f�[�^���擾����B
			
			//�����pSQL�̎擾
			this.createSearchDB();
			//�������s
			lstRecset = this.searchDB.dbSearch(strSql.toString());

			//�������ʂ��Ȃ��ꍇ
			if (lstRecset.size() == 0){
				em.ThrowException(ExceptionKind.�x��Exception,"W000401", strSql.toString(), "", "");
				
			}
			
			//�B�F���͌����Ѓf�[�^�p�����[�^�[�i�[���\�b�h���ďo���A�@�\���X�|���X�f�[�^���`������B

			// �@�\ID�̐ݒ�
			strKinoId = reqData.getID();
			resKind.setID(strKinoId);
			
			// �e�[�u�����̐ݒ�
			strTableNm = reqData.getTableID(0);
			resKind.addTableItem(strTableNm);

			//�������ʂ����X�|���X�f�[�^�Ɋi�[
			storageGenryoData(lstRecset, resKind.getTableItem(0));
						
		} catch (Exception e) {
			this.em.ThrowException(e, "���͌�������DB�����Ɏ��s���܂����B");

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
	 * ���͌����f�[�^�擾SQL�쐬
	 * @param reqData : �@�\���N�G�X�g�f�[�^
	 * @return ���͌��������pSQL
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public StringBuffer ��enryoDataCreateSQL(RequestResponsKindBean reqData)
			throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		StringBuffer strRetSQL = new StringBuffer();
		StringBuffer strSQL_select = new StringBuffer();
		StringBuffer strSQL_where = new StringBuffer();

		try {
			
			//�@�F�@�\���N�G�X�g�f�[�^���A���͌������������𒊏o���A���͌��������擾����SQL���쐬����B

			//�@�\���N�G�X�g�f�[�^���A�f�[�^���擾
			String strReqSelectRowNum = reqData.getFieldVale(0, 0, "num_selectRow");
			String strReqBushoCd = reqData.getFieldVale(0, 0, "cd_busho");
			//�ő�\�����ڐ���ݒ�
			String strListRowMax = ConstManager.getConstValue(ConstManager.Category.�ݒ�,"LIST_ROW_MAX");
			
			//SQL�̎擾
			strSQL_select = createSQL_select();				//SELECT
			strSQL_where = createSQL_where(reqData);		//WHERE
			
			//SQL�̍쐬
			//�擾����
			strRetSQL.append(" SELECT ");
			strRetSQL.append("   tbl.cd_kaisha AS cd_kaisha ");
			strRetSQL.append("  ,tbl.cd_busho AS cd_busho ");
			strRetSQL.append("  ,tbl.cd_genryo AS cd_genryo ");
			strRetSQL.append("  ,tbl.nm_kaisha AS nm_kaisha ");
			strRetSQL.append("  ,tbl.nm_busho AS nm_busho ");
			strRetSQL.append("  ,tbl.nm_genryo AS nm_genryo ");
			strRetSQL.append("  ,tbl.ritu_sakusan AS ritu_sakusan ");
			strRetSQL.append("  ,tbl.ritu_shokuen AS ritu_shokuen ");
			strRetSQL.append("  ,tbl.ritu_sousan AS ritu_sousan ");
			strRetSQL.append("  ,tbl.ritu_abura AS ritu_abura ");
			strRetSQL.append("  ,tbl.hyojian AS hyojian ");
			strRetSQL.append("  ,tbl.tenkabutu AS tenkabutu ");
			strRetSQL.append("  ,tbl.memo AS memo ");
			strRetSQL.append("  ,tbl.no_eiyo1 AS no_eiyo1 ");
			strRetSQL.append("  ,tbl.no_eiyo2 AS no_eiyo2 ");
			strRetSQL.append("  ,tbl.no_eiyo3 AS no_eiyo3 ");
			strRetSQL.append("  ,tbl.no_eiyo4 AS no_eiyo4 ");
			strRetSQL.append("  ,tbl.no_eiyo5 AS no_eiyo5 ");
			strRetSQL.append("  ,tbl.wariai1 AS wariai1 ");
			strRetSQL.append("  ,tbl.wariai2 AS wariai2 ");
			strRetSQL.append("  ,tbl.wariai3 AS wariai3 ");
			strRetSQL.append("  ,tbl.wariai4 AS wariai4 ");
			strRetSQL.append("  ,tbl.wariai5 AS wariai5 ");
			strRetSQL.append("  ,tbl.kbn_haishi AS kbn_haishi ");
			strRetSQL.append("  ,tbl.cd_kakutei AS cd_kakutei ");
			strRetSQL.append("  ,tbl.id_kakunin AS id_kakunin ");
			strRetSQL.append("  ,tbl.dt_kakunin AS dt_kakunin ");
			strRetSQL.append("  ,tbl.tanka AS tanka ");
			strRetSQL.append("  ,tbl.budomari AS budomari ");
			
			strRetSQL.append("  ,tbl.id_toroku AS id_toroku ");
			strRetSQL.append("  ,tbl.dt_toroku AS dt_toroku ");
			strRetSQL.append("  ,tbl.nm_toroku AS nm_toroku ");
			strRetSQL.append("  ,tbl.id_koshin AS id_koshin ");
			strRetSQL.append("  ,tbl.dt_koshin AS dt_koshin ");
			strRetSQL.append("  ,tbl.nm_koshin AS nm_koshin ");

			strRetSQL.append("  ,tbl.list_max_row AS list_max_row ");
			strRetSQL.append("  ,cnttbl.max_row AS max_row ");
			
			//�l�擾�pSQL�̒ǉ�[tbl]
			strRetSQL.append(" FROM ( ");
			strRetSQL.append(strSQL_select.toString());
			strRetSQL.append(strSQL_where.toString());
			strRetSQL.append(" ) AS tbl ");

			//�S�H��̏ꍇ
			if ( strReqBushoCd.equals("zenkojo") ) {
				strRetSQL.append(" LEFT JOIN (   ");
				strRetSQL.append(" SELECT DISTINCT  ");	
				strRetSQL.append("    M401.cd_kaisha AS cd_kaisha ");
				strRetSQL.append("    ,M401.cd_genryo AS cd_genryo ");
				strRetSQL.append("    ,M402.cd_busho AS cd_busho ");	
				strRetSQL.append("    ,Convert(int,(ROW_NUMBER() OVER  ");
				strRetSQL.append("      (ORDER BY M401.cd_kaisha,M401.cd_genryo,M402.cd_busho)-1)/" + strListRowMax + "+ 1) AS PageNO ");
				strRetSQL.append(" FROM ma_genryokojo M402 ");
				strRetSQL.append("  INNER JOIN ma_genryo M401 ");
				strRetSQL.append("   ON M402.cd_kaisha = M401.cd_kaisha ");
				strRetSQL.append("   AND M402.cd_genryo = M401.cd_genryo ");
				strRetSQL.append(strSQL_where.toString());
				strRetSQL.append(" ) AS gentbl ");	
				strRetSQL.append(" ON gentbl.cd_genryo = tbl.cd_genryo ");
				strRetSQL.append(" AND gentbl.cd_kaisha = tbl.cd_kaisha ");	
				strRetSQL.append(" AND gentbl.cd_busho = tbl.cd_busho ");	
				
			} 
			
			//�ő匏���擾�pSQL�̒ǉ�[cnttbl]
			strRetSQL.append(" ,( SELECT COUNT(*) AS max_row  ");
			strRetSQL.append(" FROM (  ");
			
			if ( !strReqBushoCd.equals("zenkojo") ) {
				
				//�S�H��ł͂Ȃ��ꍇ
				strRetSQL.append(strSQL_select.toString());
				strRetSQL.append(strSQL_where.toString());
				
			} else {
				
				//�S�H��̏ꍇ�A��ЃR�[�h�ɕR�t�������R�[�h�̍ő匏��������o��
//				strRetSQL.append(" SELECT DISTINCT M401.cd_kaisha AS cd_kaisha ,M401.cd_genryo AS cd_genryo ");
				strRetSQL.append(" SELECT DISTINCT  ");	
				strRetSQL.append("    M401.cd_kaisha AS cd_kaisha ");
				strRetSQL.append("    ,M401.cd_genryo AS cd_genryo ");
				strRetSQL.append("    ,M402.cd_busho AS cd_busho ");	
				strRetSQL.append(" FROM ma_genryokojo M402 ");
				strRetSQL.append("  INNER JOIN ma_genryo M401 ");
				strRetSQL.append("   ON M402.cd_kaisha = M401.cd_kaisha ");
				strRetSQL.append("   AND M402.cd_genryo = M401.cd_genryo ");
				strRetSQL.append(strSQL_where.toString());
				
			}
			strRetSQL.append(" ) AS CT ");			
			strRetSQL.append(" ) AS cnttbl ");

			//��������(�I���y�[�W�ԍ��w��)
			if ( strReqBushoCd.equals("zenkojo") ) {
				
				//�S�H��̏ꍇ
				strRetSQL.append(" WHERE gentbl.PageNo =" + strReqSelectRowNum);			
				
			} else {
				
				//�S�H��ł͂Ȃ��ꍇ
				strRetSQL.append(" WHERE tbl.PageNo =" + strReqSelectRowNum);	
				
			}
			
		} catch (Exception e) {
			this.em.ThrowException(e, "���͌�������DB�����Ɏ��s���܂����B");
			
		} finally {
			//�ϐ��̍폜
			strSQL_select = null;
			strSQL_where = null;
			
		}
		//�A:�쐬����SQL��ԋp����B
		return strRetSQL;
		
	}
	
	/**
	 * �����pSQL�쐬(SELECT)
	 * @return �쐬SQL
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private StringBuffer createSQL_select()
			throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		StringBuffer strRetSQL = new StringBuffer();

		try {
			
			//�ő�\�����ڐ���ݒ�
			String strListRowMax = ConstManager.getConstValue(ConstManager.Category.�ݒ�,"LIST_ROW_MAX");
			
			//SQL�̍쐬
			strRetSQL.append(" SELECT  ");
						
			strRetSQL.append("   M401.cd_kaisha AS cd_kaisha ");
			strRetSQL.append("  ,M402.cd_busho AS cd_busho ");
			strRetSQL.append("  ,M401.cd_genryo AS cd_genryo ");

			strRetSQL.append("  ,ISNULL(M104.nm_kaisha,'') AS nm_kaisha ");
			strRetSQL.append("  ,ISNULL(M104.nm_busho,'') AS nm_busho ");
			strRetSQL.append("  ,ISNULL(M402.nm_genryo,'') AS nm_genryo ");
			
			strRetSQL.append("  ,ISNULL(CONVERT(VARCHAR,M401.ritu_sakusan),'') AS ritu_sakusan ");
			strRetSQL.append("  ,ISNULL(CONVERT(VARCHAR,M401.ritu_shokuen),'') AS ritu_shokuen ");
			strRetSQL.append("  ,ISNULL(CONVERT(VARCHAR,M401.ritu_sousan),'') AS ritu_sousan ");
			strRetSQL.append("  ,ISNULL(CONVERT(VARCHAR,M401.ritu_abura),'') AS ritu_abura ");
			
			strRetSQL.append("  ,ISNULL(M401.hyojian,'') AS hyojian ");
			strRetSQL.append("  ,ISNULL(M401.tenkabutu,'') AS tenkabutu ");
			strRetSQL.append("  ,ISNULL(M401.no_eiyo1,'') AS no_eiyo1 ");
			strRetSQL.append("  ,ISNULL(M401.no_eiyo2,'') AS no_eiyo2 ");
			strRetSQL.append("  ,ISNULL(M401.no_eiyo3,'') AS no_eiyo3 ");
			strRetSQL.append("  ,ISNULL(M401.no_eiyo4,'') AS no_eiyo4 ");
			strRetSQL.append("  ,ISNULL(M401.no_eiyo5,'') AS no_eiyo5 ");
			strRetSQL.append("  ,ISNULL(M401.wariai1,'') AS wariai1 ");
			strRetSQL.append("  ,ISNULL(M401.wariai2,'') AS wariai2 ");
			strRetSQL.append("  ,ISNULL(M401.wariai3,'') AS wariai3 ");
			strRetSQL.append("  ,ISNULL(M401.wariai4,'') AS wariai4 ");
			strRetSQL.append("  ,ISNULL(M401.wariai5,'') AS wariai5 ");
			strRetSQL.append("  ,ISNULL(M401.memo,'') AS memo ");
			strRetSQL.append("  ,ISNULL(M401.kbn_haishi,0) AS kbn_haishi ");
			strRetSQL.append("  ,ISNULL(M401.dt_kakunin,'') AS dt_kakunin ");
			
			strRetSQL.append("  ,ISNULL(CONVERT(VARCHAR,M401.id_kakunin),'') AS id_kakunin ");
			strRetSQL.append("  ,ISNULL(M401.cd_kakutei,'') AS cd_kakutei ");

			//�P���E����
			strRetSQL.append(" ,CASE WHEN M402.tanka IS NULL THEN ISNULL(CONVERT(VARCHAR,M402.tanka),'') ELSE CONVERT(VARCHAR,CONVERT(DECIMAL(7,2),M402.tanka)) END AS tanka ");
			strRetSQL.append(" ,CASE WHEN M402.budomari IS NULL THEN ISNULL(CONVERT(VARCHAR,M402.budomari),'') ELSE CONVERT(VARCHAR,CONVERT(DECIMAL(5,2),M402.budomari)) END AS budomari ");
			
			strRetSQL.append("  ,M401.id_toroku AS id_toroku ");
			strRetSQL.append("  ,M401.dt_toroku AS dt_toroku ");
			strRetSQL.append("  ,ISNULL(M101_t.nm_user,'') AS nm_toroku ");
			strRetSQL.append("  ,M401.id_koshin AS id_koshin ");
			strRetSQL.append("  ,M401.dt_koshin AS dt_koshin ");
			strRetSQL.append("  ,ISNULL(M101_k.nm_user,'') AS nm_koshin ");
			
			strRetSQL.append("  ,(CASE ");
			strRetSQL.append("     WHEN ROW_NUMBER() OVER ( ORDER BY M401.cd_genryo)%" + strListRowMax + " = 0 THEN " + strListRowMax);
			strRetSQL.append("      ELSE ROW_NUMBER() OVER ( ORDER BY M401.cd_genryo)%" + strListRowMax);
			strRetSQL.append("    END) AS no_row ");
			strRetSQL.append("  ,Convert(int,(ROW_NUMBER() OVER (ORDER BY M401.cd_genryo)-1)/" + strListRowMax + " + 1) AS PageNO ");
			strRetSQL.append("  ," + strListRowMax + " AS list_max_row ");
			
			strRetSQL.append(" FROM ma_genryokojo M402 ");
			strRetSQL.append("  INNER JOIN ma_genryo M401 ");
			strRetSQL.append("   ON M402.cd_kaisha = M401.cd_kaisha ");
			strRetSQL.append("   AND M402.cd_genryo = M401.cd_genryo ");
			strRetSQL.append("  LEFT JOIN ma_user M101_t ");
			strRetSQL.append("   ON M101_t.id_user = M401.id_toroku ");
			strRetSQL.append("  LEFT JOIN ma_user M101_k ");
			strRetSQL.append("   ON M101_k.id_user = M401.id_koshin ");
			//�����}�X�^
			strRetSQL.append("  LEFT JOIN ma_busho M104 ");
			strRetSQL.append("   ON  M104.cd_kaisha = M401.cd_kaisha ");
			strRetSQL.append("   AND M104.cd_busho = M402.cd_busho ");

		} catch (Exception e) {
			this.em.ThrowException(e, "");
			
		} finally {
			
		}
		return strRetSQL;
		
	}
	/**
	 * �����pSQL�쐬(WHERE)
	 * @param reqData : �@�\���N�G�X�g�f�[�^
	 * @return �쐬SQL
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private StringBuffer createSQL_where(RequestResponsKindBean reqData) 
			throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		StringBuffer strRetSQL = new StringBuffer();

		try {
			//�@�\���N�G�X�g�f�[�^���A�f�[�^���擾
			String strReqTaishoGenryo1 = reqData.getFieldVale(0, 0, "taisho_genryo1");
			String strReqTaishoGenryo2 = reqData.getFieldVale(0, 0, "taisho_genryo2");
			String strReqGenryoCd = reqData.getFieldVale(0, 0, "cd_genryo");
			String strReqGenryoNm = reqData.getFieldVale(0, 0, "nm_genryo");
			String strReqKaishaCd = reqData.getFieldVale(0, 0, "cd_kaisha");
			String strReqBushoCd = reqData.getFieldVale(0, 0, "cd_busho");	
		
			/// ��������
			strRetSQL.append("  WHERE ");
			strRetSQL.append("  M401.cd_kaisha =" + strReqKaishaCd);
			
			//�����R�[�h
			if ( strReqGenryoCd != "" ) {
				strRetSQL.append("  AND M401.cd_genryo ='" + strReqGenryoCd + "'");
				
			}
			
			//��������
			if ( strReqGenryoNm != "" ) {
				strRetSQL.append("  AND M402.nm_genryo LIKE '%" + strReqGenryoNm + "%'");
				
			}
			
			 if ( strReqTaishoGenryo1.equals("true") && strReqTaishoGenryo2.equals("false") ) {
				 
				//�Ώی����@�i�V�K�����j���`�F�b�N����Ă���ꍇ
				
				//�����񂪊܂܂�Ă��錴���R�[�h
				strRetSQL.append("  AND ISNUMERIC(M401.cd_genryo) = 0");

				//�����R�[�h�ɂ��V�K��������
				strRetSQL.append("  AND M402.cd_busho =" + ConstManager.getConstValue(Category.�ݒ�, "SHINKIGENRYO_BUSHOCD"));
				
			} else if ( strReqTaishoGenryo1.equals("false") && strReqTaishoGenryo2.equals("true") ) {
				
				//�Ώی����A�i�V�K�����j���`�F�b�N����Ă���ꍇ

				//�����񂪊܂܂�Ă��Ȃ������R�[�h
				strRetSQL.append("  AND ISNUMERIC(M401.cd_genryo) = 1");

				//�����R�[�h�Ɂu�S�H��v�ȊO���ݒ肳��Ă���ꍇ
				if ( !strReqBushoCd.equals("zenkojo") ) {
					strRetSQL.append("  AND M402.cd_busho =" + strReqBushoCd);
					
				}
				
			} else {
				
				//�����R�[�h�Ɂu�S�H��v�ȊO���ݒ肳��Ă���ꍇ
				if ( !strReqBushoCd.equals("zenkojo") ) {
					strRetSQL.append("  AND ( M402.cd_busho =" + strReqBushoCd);
					strRetSQL.append("  OR M402.cd_busho =0 )");
					
				}
				
			}
			
		} catch (Exception e) {
			this.em.ThrowException(e, "");
			
		} finally {
			
		}
		return strRetSQL;
		
	}

	
	/**
	 * ���͌����f�[�^�p�����[�^�[�i�[
	 *  : ���͌����f�[�^�������X�|���X�f�[�^�֊i�[����B
	 * @param lstGenryoData : �������ʏ�񃊃X�g
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private void storageGenryoData(List<?> lstGenryoData, RequestResponsTableBean resTable) 
			throws ExceptionSystem, ExceptionUser, ExceptionWaning{

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
				resTable.addFieldVale("rec" + i, "cd_kaisha", items[0].toString());
				resTable.addFieldVale("rec" + i, "cd_busho", items[1].toString());
				resTable.addFieldVale("rec" + i, "cd_genryo", items[2].toString());
				resTable.addFieldVale("rec" + i, "nm_kaisha", items[3].toString());
				resTable.addFieldVale("rec" + i, "nm_busho", items[4].toString());
				resTable.addFieldVale("rec" + i, "nm_genryo", items[5].toString());
				resTable.addFieldVale("rec" + i, "ritu_sakusan", items[6].toString());
				resTable.addFieldVale("rec" + i, "ritu_shokuen", items[7].toString());
				resTable.addFieldVale("rec" + i, "ritu_sousan", items[8].toString());
				resTable.addFieldVale("rec" + i, "ritu_abura", items[9].toString());
				resTable.addFieldVale("rec" + i, "hyojian", items[10].toString());
				resTable.addFieldVale("rec" + i, "tenkabutu", items[11].toString());
				resTable.addFieldVale("rec" + i, "memo", items[12].toString());
				resTable.addFieldVale("rec" + i, "no_eiyo1", items[13].toString());
				resTable.addFieldVale("rec" + i, "no_eiyo2", items[14].toString());
				resTable.addFieldVale("rec" + i, "no_eiyo3", items[15].toString());
				resTable.addFieldVale("rec" + i, "no_eiyo4", items[16].toString());
				resTable.addFieldVale("rec" + i, "no_eiyo5", items[17].toString());
				resTable.addFieldVale("rec" + i, "wariai1", items[18].toString());
				resTable.addFieldVale("rec" + i, "wariai2", items[19].toString());
				resTable.addFieldVale("rec" + i, "wariai3", items[20].toString());
				resTable.addFieldVale("rec" + i, "wariai4", items[21].toString());
				resTable.addFieldVale("rec" + i, "wariai5", items[22].toString());
				resTable.addFieldVale("rec" + i, "kbn_haishi", items[23].toString());
				resTable.addFieldVale("rec" + i, "cd_kakutei", items[24].toString());
				resTable.addFieldVale("rec" + i, "id_kakunin", items[25].toString());
				resTable.addFieldVale("rec" + i, "dt_kakunin", items[26].toString());
				resTable.addFieldVale("rec" + i, "tanka", items[27].toString());
				resTable.addFieldVale("rec" + i, "budomari", items[28].toString());
				resTable.addFieldVale("rec" + i, "id_toroku", items[29].toString());
				resTable.addFieldVale("rec" + i, "dt_toroku", items[30].toString());
				resTable.addFieldVale("rec" + i, "nm_toroku", items[31].toString());
				resTable.addFieldVale("rec" + i, "id_koshin", items[32].toString());
				resTable.addFieldVale("rec" + i, "dt_koshin", items[33].toString());
				resTable.addFieldVale("rec" + i, "nm_koshin", items[34].toString());
				resTable.addFieldVale("rec" + i, "list_max_row", items[35].toString());
				resTable.addFieldVale("rec" + i, "max_row", items[36].toString());
				
			}
			
		} catch (Exception e) {
			this.em.ThrowException(e, "���͌�������DB�����Ɏ��s���܂����B");
			
		} finally {

		}

	}

}
