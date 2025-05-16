package jp.co.blueflag.shisaquick.srv.logic;

import java.util.ArrayList;
import java.util.List;

import jp.co.blueflag.shisaquick.srv.base.LogicBase;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsKindBean;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsRowBean;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsTableBean;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ConstManager;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;
import jp.co.blueflag.shisaquick.srv.common.ExceptionManager.ExceptionKind;

/**
 * 
 * ����f�[�^����DB����
 *  : ����f�[�^������������B
 *  �@�\ID�FSA200�@
 *  
 * @author TT.furuta
 * @since  2009/03/29
 */
public class ShisakuDataSearchLogic extends LogicBase {

	/**
	 * ����f�[�^�����R���X�g���N�^ 
	 * : �C���X�^���X����
	 */
	public ShisakuDataSearchLogic() {
		//���N���X�̃R���X�g���N�^
		super();
		
	}

	/**
	 * ����f�[�^�擾�Ǘ�
	 *  : ����f�[�^�擾�������Ǘ�����B
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
		List<?> lstRecset = null;
		ArrayList<Object> arrCondition = null;
		StringBuffer strSql = new StringBuffer();
		
		RequestResponsTableBean reqTableBean = null;
		RequestResponsRowBean reqRecBean = null;
		RequestResponsKindBean resKind = null;
		
		try {
			//���X�|���X�f�[�^�i�@�\�j����
			resKind = new RequestResponsKindBean();
			
			//�e�[�u��Bean�擾
			reqTableBean = (RequestResponsTableBean)reqData.GetItem(0);
			//�sBean�擾
			reqRecBean = (RequestResponsRowBean)reqTableBean.GetItem(0);
			
			//���������̎擾
			arrCondition = createSerchCondition(reqRecBean);
						
			//SQL�쐬
			strSql = createShisakuSQL(arrCondition, strSql);
			
			//DB�C���X�^���X����
			createSearchDB();
			//�������s
			lstRecset = searchDB.dbSearch(strSql.toString());
			
			//�������ʂ��Ȃ��ꍇ
			if (lstRecset.size() == 0){
				em.ThrowException(ExceptionKind.�x��Exception,"W000401", strSql.toString(), "", "");
			}
			
			//�@�\ID�̐ݒ�
			resKind.setID(reqData.getID());
			
			//�e�[�u�����̐ݒ�
			resKind.addTableItem(reqData.getTableID(0));

			//���X�|���X�f�[�^�̌`��
			storageShisakuData(lstRecset, resKind.getTableItem(0));
					
		} catch (Exception e) {
			this.em.ThrowException(e, "����f�[�^����DB�����Ɏ��s���܂����B");
		} finally {
			//���X�g�̔j��
			removeList(lstRecset);
			removeList(arrCondition);
			if (searchDB != null) {
				//�Z�b�V�����̃N���[�Y
				searchDB.Close();
				searchDB = null;
			}
			//�ϐ��̍폜
			strSql = null;
			reqTableBean = null;
			reqRecBean = null;
		}
		return resKind;
	}
	
	/**
	 * ����f�[�^�擾SQL�쐬
	 *  : ����f�[�^���擾����SQL���쐬�B
	 * @param strSql : ��������SQL
	 * @param arrCondition�F������������
	 * @return strSql�F��������SQL
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private StringBuffer createShisakuSQL(ArrayList<Object> arrCondition, StringBuffer strSql) 
		throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		StringBuffer strWhere = new StringBuffer();
		StringBuffer strAllSql = new StringBuffer();
		
		try {
		
			boolean blAndOr = false;
			
			String strAndOr = " OR ";
			String strSqlTanto = "SELECT Shin.cd_kaisha FROM tr_shisakuhin Shin JOIN ma_tantokaisya Tanto ON Shin.cd_kaisha = Tanto.cd_tantokaisha AND Tanto.id_user = " + userInfoData.getId_user();
			String strListRowMax = ConstManager.getConstValue(ConstManager.Category.�ݒ�,"LIST_ROW_MAX");
			
			//SQL���̍쐬
			strAllSql.append("	SELECT ");
			strAllSql.append("	tbl.no_row ");
			strAllSql.append("	,tbl.no_shisaku ");
			strAllSql.append("	,tbl.no_seiho ");
			strAllSql.append("	,tbl.nm_hin ");
			strAllSql.append("	,tbl.id_toroku ");
			strAllSql.append("	,tbl.nm_tanto ");
			strAllSql.append("	,tbl.nm_user ");
			strAllSql.append("	,tbl.nm_genre ");
			strAllSql.append("	,tbl.nm_ikatu ");
			strAllSql.append("	,tbl.nm_youto ");
			strAllSql.append("	,tbl.nm_genryo ");
			strAllSql.append("	,tbl.kbn_haishi ");
			strAllSql.append("	,tbl.nm_haishi ");
			strAllSql.append("	," + strListRowMax + " AS list_max_row");
			strAllSql.append("	,cnttbl.max_row ");
			strAllSql.append("	FROM ( ");
			
			strSql.append("	SELECT ");
			strSql.append("	(CASE WHEN ROW_NUMBER() OVER (ORDER BY T1.cd_shain)%" + strListRowMax + " = 0 THEN " + strListRowMax + " ELSE ROW_NUMBER() OVER (ORDER BY T1.cd_shain)%" + strListRowMax + " END) AS no_row ");
			strSql.append("	,Convert(int,(ROW_NUMBER() OVER (ORDER BY T1.cd_shain)-1)/" + strListRowMax + " + 1) AS PageNO ");
			strSql.append("	,RIGHT('0000000000' + CONVERT(varchar,T1.cd_shain),10) + '-' +  ");
			strSql.append("	 RIGHT('00' + CONVERT(varchar,T1.nen),2) + '-' + ");
			strSql.append("	 RIGHT('000' + CONVERT(varchar,T1.no_oi),3) as no_shisaku ");
			strSql.append("	,ISNULL(T2.no_seiho1, '') as no_seiho ");
			strSql.append("	,ISNULL(T1.nm_hin, '') as nm_hin ");
			strSql.append("	,ISNULL(T1.id_toroku, 0) as id_toroku ");
			strSql.append("	,M2.nm_user as nm_tanto ");
			strSql.append("	,ISNULL(MU.nm_literal, '') as nm_user ");
			strSql.append("	,ISNULL(MJ.nm_literal, '') as nm_genre ");
			strSql.append("	,ISNULL(MI.nm_literal, '') as nm_ikatu ");
//			strSql.append("	,MS.nm_literal as nm_youto ");
//			strSql.append("	,MT.nm_literal as nm_genryo ");
			strSql.append("	,T1.youto as nm_youto ");
			strSql.append("	,T1.tokuchogenryo as nm_genryo ");
			strSql.append("	,T1.kbn_haishi ");
			strSql.append("	,CASE T1.kbn_haishi WHEN 0 THEN '�g�p�\' WHEN 1 THEN '�p�~' END as nm_haishi ");
			strSql.append("	FROM tr_shisakuhin T1 ");
			strSql.append("	LEFT JOIN tr_shisaku T2 ON T1.cd_shain = T2.cd_shain AND T1.nen = T2.nen AND T1.no_oi = T2.no_oi AND T1.seq_shisaku = T2.seq_shisaku ");
//			strSql.append("	LEFT JOIN tr_haigo T3 ON T1.cd_shain = T3.cd_shain AND T1.nen = T3.nen AND T1.no_oi = T3.no_oi ");
			strSql.append("	LEFT JOIN ma_user M1 ON M1.id_user = ");
			strSql.append(userInfoData.getId_user());
			strSql.append("	LEFT JOIN ma_user M2 ON T1.id_toroku = M2.id_user ");
			strSql.append("	LEFT JOIN ma_literal MU ON MU.cd_category = 'K_yuza' AND MU.cd_literal = T1.cd_user	");
			strSql.append("	LEFT JOIN ma_literal MJ ON MJ.cd_category = 'K_jyanru' AND MJ.cd_literal = T1.cd_genre ");
			strSql.append("	LEFT JOIN ma_literal MI ON MI.cd_category = 'K_ikatuhyouzi' AND MI.cd_literal = T1.cd_ikatu ");
//			strSql.append("	LEFT JOIN ma_literal MS ON MS.cd_category = 'K_yoto' AND MS.cd_literal = T1.youto ");
//			strSql.append("	LEFT JOIN ma_literal MT ON MT.cd_category = 'K_tokucyogenryo' AND MT.cd_literal = T1.tokuchogenryo ");
			//�L�[���[�h���������͂���Ă���ꍇ
			if (!arrCondition.get(14).equals("")){
				strSql.append("	LEFT JOIN (SELECT cd_shain, nen, no_oi ");
				strSql.append("	FROM tr_haigo ");
				strSql.append("	WHERE ");

				//�L�[���[�h�𕪉�
				String[] strKeyWord = arrCondition.get(14).toString().split(",");
			
				strSql.append(" ( ");
				
				for (int i=0;i<strKeyWord.length;i++){
			
					if (i != 0){
						strSql.append(" AND ");
					}
					
					//���l�̏ꍇ
					if (strKeyWord[i].matches("[0-9]+")){
						strSql.append(" (cd_genryo = '");
						strSql.append(strKeyWord[i] + "'");
						strSql.append(" OR nm_genryo LIKE '%");
						strSql.append(strKeyWord[i] + "%') ");
					//�����̏ꍇ
					}else{
						strSql.append(" nm_genryo LIKE '%");
						strSql.append(strKeyWord[i] + "%' ");
					}
				}
				
				strSql.append(" ) ");
				strSql.append(" GROUP BY cd_shain, nen, no_oi ");
				strSql.append(" ) T3 ");
				strSql.append("	ON T1.cd_shain = T3.cd_shain AND T1.nen = T3.nen AND T1.no_oi = T3.no_oi ");
			}

			//�u�����ōi���݁v���`�F�b�N����Ă���ꍇ�AAND��ݒ�B
			if (!arrCondition.get(1).equals("")){
				strAndOr = "AND";
			}
 			
			//����No�����͂���Ă���ꍇ
			if (!arrCondition.get(3).equals("")){
				//����R�[�h�𕪉�
				String[] strShisaku = arrCondition.get(3).toString().split("-");
				
				if (!blAndOr){
					strWhere.append(" ( ");
					blAndOr = true;
				} else {
					strWhere.append(strAndOr);
					blAndOr = true;
				}
				
				strWhere.append(" ( ");
				strWhere.append(" T1.cd_shain = ");
				strWhere.append(strShisaku[0]);
				strWhere.append(" AND T1.nen = ");
				strWhere.append(strShisaku[1]);
				strWhere.append(" AND T1.no_oi = ");
				strWhere.append(strShisaku[2]);
				strWhere.append(" ) ");
				
			}
	
			//���@No�����͂���Ă���A���u���@�������Ώہv���`�F�b�N����Ă���ꍇ
			if (!arrCondition.get(4).equals("") && !arrCondition.get(0).equals("")){
				if (!blAndOr){
					strWhere.append(" ( ");
					blAndOr = true;
				} else {
					strWhere.append(strAndOr);
					blAndOr = true;
				}

				strWhere.append(" ( ");
				strWhere.append(" T2.cd_shain = T1.cd_shain ");
				strWhere.append(" AND T2.nen  = T1.nen ");
				strWhere.append(" AND T2.no_oi = T1.no_oi ");
				strWhere.append(" AND T2.seq_shisaku = T1.seq_shisaku ");
				strWhere.append(" AND T2.no_seiho1 = '");
				strWhere.append(arrCondition.get(4).toString() + "' ");
				strWhere.append(" ) ");
			}
			
			//���얼�����͂���Ă���ꍇ
			if (!arrCondition.get(5).equals("")){
				if (!blAndOr){
					strWhere.append(" ( ");
					blAndOr = true;
				} else {
					strWhere.append(strAndOr);
					blAndOr = true;
				}

				strWhere.append(" T1.nm_hin LIKE '%");
				strWhere.append(arrCondition.get(5).toString() + "%' ");
			}
			
			//�����O���[�v���I������Ă���ꍇ
			if (!arrCondition.get(6).equals("")){
				if (!blAndOr){
					strWhere.append(" ( ");
					blAndOr = true;
				} else {
					strWhere.append(strAndOr);
					blAndOr = true;
				}

				strWhere.append(" T1.cd_group = ");
				strWhere.append(arrCondition.get(6).toString());
			}
			
			//�����`�[�����I������Ă���ꍇ
			if (!arrCondition.get(7).equals("")){
				if (!blAndOr){
					strWhere.append(" ( ");
					blAndOr = true;
				} else {
					strWhere.append(strAndOr);
					blAndOr = true;
				}

				strWhere.append(" ( ");
				strWhere.append(" T1.cd_group = ");
				strWhere.append(arrCondition.get(6).toString());
				strWhere.append(" AND T1.cd_team = ");
				strWhere.append(arrCondition.get(7).toString());
				strWhere.append(" ) ");
			}
			
			//�S���҂��I������Ă���ꍇ
			if (!arrCondition.get(8).equals("")){
				if (!blAndOr){
					strWhere.append(" ( ");
					blAndOr = true;
				} else {
					strWhere.append(strAndOr);
					blAndOr = true;
				}

				strWhere.append(" T1.id_toroku = ");
				strWhere.append(arrCondition.get(8).toString());
			}
			
			//���[�U���I������Ă���ꍇ
			if (!arrCondition.get(9).equals("")){
				if (!blAndOr){
					strWhere.append(" ( ");
					blAndOr = true;
				} else {
					strWhere.append(strAndOr);
					blAndOr = true;
				}

				strWhere.append(" T1.cd_user = '");
				strWhere.append(arrCondition.get(9).toString());
				strWhere.append("'");
			}
			
			//�W���������I������Ă���ꍇ
			if (!arrCondition.get(10).equals("")){
				if (!blAndOr){
					strWhere.append(" ( ");
					blAndOr = true;
				} else {
					strWhere.append(strAndOr);
					blAndOr = true;
				}

				strWhere.append(" T1.cd_genre = '");
				strWhere.append(arrCondition.get(10).toString());
				strWhere.append("'");
			}
			
			//�ꊇ�\�����̂��I������Ă���ꍇ
			if (!arrCondition.get(11).equals("")){
				if (!blAndOr){
					strWhere.append(" ( ");
					blAndOr = true;
				} else {
					strWhere.append(strAndOr);
					blAndOr = true;
				}

				strWhere.append(" T1.cd_ikatu = '");
				strWhere.append(arrCondition.get(11).toString());
				strWhere.append("'");
			}
	
			//���i�̗p�r���I������Ă���ꍇ
			if (!arrCondition.get(12).equals("")){
				if (!blAndOr){
					strWhere.append(" ( ");
					blAndOr = true;
				} else {
					strWhere.append(strAndOr);
					blAndOr = true;
				}

//				strWhere.append(" T1.youto = ");
				strWhere.append(" T1.youto LIKE '%");
				strWhere.append(arrCondition.get(12).toString());
				strWhere.append("%'");
			}
	
			//�����������I������Ă���ꍇ
			if (!arrCondition.get(13).equals("")){
				if (!blAndOr){
					strWhere.append(" ( ");
					blAndOr = true;
				} else {
					strWhere.append(strAndOr);
					blAndOr = true;
				}

//				strWhere.append(" T1.tokuchogenryo = ");
				strWhere.append(" T1.tokuchogenryo LIKE '%");
				strWhere.append(arrCondition.get(13).toString());
				strWhere.append("%'");
			}
	
			//�L�[���[�h���������͂���Ă���ꍇ
			if (!arrCondition.get(14).equals("")){
//				//�L�[���[�h�𕪉�
//				String[] strKeyWord = arrCondition.get(14).toString().split(",");
//			
//				if (!blAndOr){
//					strWhere.append(" ( ");
//					blAndOr = true;
//				} else {
//					strWhere.append(strAndOr);
//					blAndOr = true;
//				}
//				
//				strWhere.append(" ( ");
//				
//				for (int i=0;i<strKeyWord.length;i++){
//			
//					if (i != 0){
//						strWhere.append(" AND ");
//					}
//					
//					//���l�̏ꍇ
//					if (strKeyWord[i].matches("[0-9]+")){
//						strWhere.append(" (T3.cd_genryo = ");
//						strWhere.append(strKeyWord[i]);
//						strWhere.append(" OR T3.nm_genryo LIKE '%");
//						strWhere.append(strKeyWord[i] + "%') ");
//					//�����̏ꍇ
//					}else{
//						strWhere.append(" T3.nm_genryo LIKE '%");
//						strWhere.append(strKeyWord[i] + "%' ");
//					}
//				}
//				
//				strWhere.append(" ) ");
				if (!blAndOr){
					strWhere.append(" ( ");
					blAndOr = true;
				} else {
					strWhere.append(strAndOr);
					blAndOr = true;
				}

				strWhere.append(" ( ");
				strWhere.append(" T1.cd_shain = T3.cd_shain");
				strWhere.append(" AND T1.nen = T3.nen");
				strWhere.append(" AND T1.no_oi = T3.no_oi");
				strWhere.append(" ) ");
			}

			//�u�p�~�������Ώہv���`�F�b�N����Ă��Ȃ��ꍇ
			if (arrCondition.get(2).equals("")){
				if (!blAndOr){
					blAndOr = true;
				} else {
					strWhere.append(" ) AND ");
					blAndOr = true;
				}

				strWhere.append(" T1.kbn_haishi = 0 ");
			} else {
				if (!blAndOr){
					blAndOr = true;
				} else {
					strWhere.append(" ) ");
					blAndOr = true;
				}

			}
			
			//�������������ݒ�
			//����O���[�v���i�{�l+���ذ�ް�ȏ�j
			if (arrCondition.get(16).equals("1")){
				if (!blAndOr){
					blAndOr = true;
				} else {
					if (!strWhere.toString().equals("")) {
						strWhere.append("AND");
						blAndOr = true;
					}
				}
				
				strWhere.append(" ( ");
				strWhere.append(" ( ");
				strWhere.append(" T1.cd_group = ");
				strWhere.append(userInfoData.getCd_group());
				strWhere.append(" AND ");
				strWhere.append(" T1.cd_team = ");
				strWhere.append(userInfoData.getCd_team());
				strWhere.append(" AND ");
				strWhere.append(" T1.id_toroku = ");
				strWhere.append(userInfoData.getId_user());				
				strWhere.append(" ) ");
				
				//�Z�b�V�������D��E�R�[�h���`�[�����[�_�[�̏ꍇ�A�ȉ���OR�����Œǉ�
				if (Integer.parseInt(ConstManager.getConstValue(ConstManager.Category.�ݒ�, "TEAM_LEADER_CD"))
						<= Integer.parseInt(userInfoData.getCd_literal())){
					strWhere.append(" OR ");				
					strWhere.append(" ( ");
					strWhere.append(" T1.cd_group = ");
					strWhere.append(userInfoData.getCd_group());
					strWhere.append(" AND ");
					strWhere.append(" T1.cd_team = ");
					strWhere.append(userInfoData.getCd_team());
					strWhere.append(" AND ");
					strWhere.append(" M1.id_user = ");
					strWhere.append(userInfoData.getId_user());
					strWhere.append(" AND ");
					strWhere.append(" M1.cd_yakushoku <= ");
					strWhere.append(userInfoData.getCd_literal());				
					strWhere.append(" ) ");									
				}
				strWhere.append(" ) ");
				
			//����O���[�v���S�����
			} else if (arrCondition.get(16).equals("2")){
				if (!blAndOr){
					blAndOr = true;
				} else {
					if (!strWhere.toString().equals("")) {
						strWhere.append("AND");
						blAndOr = true;
					}
				}

				strWhere.append(" ( ");
				strWhere.append(" T1.cd_group = ");
				strWhere.append(userInfoData.getCd_group());
//				strWhere.append(" AND ");
//				strWhere.append(" T1.cd_team = ");
//				strWhere.append(userInfoData.getCd_team());
				strWhere.append(" AND ");
				strWhere.append(" T1.cd_kaisha in ( ");
				strWhere.append(strSqlTanto + " ) ");
				strWhere.append(" ) ");

				

			//����O���[�v���S����Њ��i�{�l+���ذ�ް�ȏ�j
			} else if (arrCondition.get(16).equals("3")){
				if (!blAndOr){
					blAndOr = true;
				} else {
					if (!strWhere.toString().equals("")) {
						strWhere.append("AND");
						blAndOr = true;
					}
				}
								
				strWhere.append(" ( ");
				strWhere.append(" T1.cd_group = ");
				strWhere.append(userInfoData.getCd_group());
				strWhere.append(" AND ");
				strWhere.append(" T1.cd_team = ");
				strWhere.append(userInfoData.getCd_team());
				strWhere.append(" AND ");
				strWhere.append(" T1.id_toroku = ");
				strWhere.append(userInfoData.getId_user());
				strWhere.append(" AND ");
				strWhere.append(" T1.cd_kaisha in ( ");
				strWhere.append(strSqlTanto);
				strWhere.append(" ) ");

				//�Z�b�V�������D��E�R�[�h���`�[�����[�_�[�̏ꍇ�A�ȉ���OR�����Œǉ�
				if (Integer.parseInt(ConstManager.getConstValue(ConstManager.Category.�ݒ�, "TEAM_LEADER_CD"))
						<= Integer.parseInt(userInfoData.getCd_literal())){

					strWhere.append(" OR ");
					strWhere.append(" ( ");
					strWhere.append(" T1.cd_group = ");
					strWhere.append(userInfoData.getCd_group());
					strWhere.append(" AND ");
					strWhere.append(" T1.cd_team = ");
					strWhere.append(userInfoData.getCd_team());
					strWhere.append(" AND ");
					strWhere.append(" M1.cd_group = ");
					strWhere.append(userInfoData.getCd_group());
					strWhere.append(" AND ");
					strWhere.append(" M1.cd_team = ");
					strWhere.append(userInfoData.getCd_team());
					strWhere.append(" AND ");
					strWhere.append(" M1.cd_yakushoku <= ");
					strWhere.append(userInfoData.getCd_literal());
					strWhere.append(" AND ");
					strWhere.append(" T1.cd_kaisha in ( ");
					strWhere.append(strSqlTanto);
					strWhere.append(" ) ");
					strWhere.append(" ) ");
				}
				
				strWhere.append(" ) ");
			//���H�ꕪ
			} else if (arrCondition.get(16).equals("4")){
				if (!blAndOr){
					blAndOr = true;
				} else {
					if (!strWhere.toString().equals("")) {
						strWhere.append("AND");
						blAndOr = true;
					}
				}
				
				strWhere.append(" ( ");
				strWhere.append(" T1.cd_kaisha = ");
				strWhere.append(userInfoData.getCd_kaisha());
				strWhere.append(" AND ");
//				strWhere.append(" T1.cd_busho = ");
				strWhere.append(" T1.cd_kojo = ");
				strWhere.append(userInfoData.getCd_busho());
				strWhere.append(" ) ");
			
			}

			
			//�����L��̏ꍇWhere��ݒ�
			if (!strWhere.toString().equals("")){
				strSql.append("	WHERE ").append(strWhere);				
			}
			
			strAllSql.append(strSql);
			strAllSql.append("	) AS tbl ");
			strAllSql.append(",(SELECT COUNT(*) as max_row FROM (" + strSql + ") AS CT ) AS cnttbl ");
			strAllSql.append("	WHERE tbl.PageNO = " + arrCondition.get(15));
			strAllSql.append(" ORDER BY ");
			strAllSql.append(" tbl.no_shisaku ");
			
			strSql = strAllSql;
		} catch (Exception e) {
			
			em.ThrowException(e, "����f�[�^���������Ɏ��s���܂����B");
			
		} finally {
			//�ϐ��̍폜
			strWhere = null;
			strAllSql = null;
		}
		
		return strSql;
	}
	/**
	 * ���������擾
	 *  : ����f�[�^�̒��oSQL�p�̏������擾
	 * @param reqRecBean : �@�\���N�G�X�g�f�[�^
	 * @param arr�F�����������X�g
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private ArrayList<Object> createSerchCondition(RequestResponsRowBean reqRecBean) 
		throws ExceptionSystem, ExceptionUser, ExceptionWaning{
	
		ArrayList<Object> arr = null;
		
		try {
			
			//������������
			String strKbn_joken1 = "";
			String strKbn_joken2 = "";
			String strKbn_joken3 = "";
			String strNo_shisaku = "";
			String strNo_seiho = "";
			String strNm_shisaku = "";
			String strCd_group = "";
			String strCd_team = "";
			String strCd_tanto = "";
			String strCd_user = "";
			String strCd_genre = "";
			String strCd_ikatu = "";
			String strCd_youto = "";
			String strCd_genryo = "";
			String strKeyword = "";
			String strKengen = "";
			String strNo_page = "";
			
			//���������擾
			//�������e�@�ݒ�
			strKbn_joken1 = reqRecBean.getFieldVale("kbn_joken1");
			//�������e�A�ݒ�
			strKbn_joken2 = reqRecBean.getFieldVale("kbn_joken2");					
			//�������e�B�ݒ�
			strKbn_joken3 = reqRecBean.getFieldVale("kbn_joken3");					
			//���쇂�ݒ�
			strNo_shisaku = reqRecBean.getFieldVale("no_shisaku");					
			//���@���ݒ�
			strNo_seiho = reqRecBean.getFieldVale("no_seiho");					
			//���얼�ݒ�
			strNm_shisaku = reqRecBean.getFieldVale("nm_shisaku");					
			//�����O���[�v�R�[�h
			strCd_group = reqRecBean.getFieldVale("cd_group");				
			//�����`�[���R�[�h
			strCd_team = reqRecBean.getFieldVale("cd_team");						
			//�S���҃R�[�h
			strCd_tanto = reqRecBean.getFieldVale("cd_tanto");						
			//���[�U�[�R�[�h
			strCd_user = reqRecBean.getFieldVale("cd_user");							
			//�W�������R�[�h
			strCd_genre = reqRecBean.getFieldVale("cd_genre");						
			//�ꊇ�\�����̃R�[�h
			strCd_ikatu = reqRecBean.getFieldVale("cd_ikatu");					
			//���i�̗p�r
			strCd_youto = reqRecBean.getFieldVale("cd_youto");					
			//��������
			strCd_genryo = reqRecBean.getFieldVale("cd_genryo");				
			//�L�[���[�h
			strKeyword = reqRecBean.getFieldVale("keyword");					
			//�y�[�W�ԍ�
			strNo_page = reqRecBean.getFieldVale("no_page");				
			
			//�������������X�g�ɐݒ�
			arr = new ArrayList<Object>();
			arr.add(strKbn_joken1);		//�������e�@�ݒ�		index:0
			arr.add(strKbn_joken2);		//�������e�A�ݒ�		index:1
			arr.add(strKbn_joken3);		//�������e�B�ݒ�		index:2
			arr.add(strNo_shisaku);		//���쇂�ݒ�			index:3
			arr.add(strNo_seiho);		//���@���ݒ�			index:4
			arr.add(strNm_shisaku);		//���얼�ݒ�			index:5
			arr.add(strCd_group);		//�����O���[�v�R�[�h	index:6
			arr.add(strCd_team);		//�����`�[���R�[�h		index:7
			arr.add(strCd_tanto);		//�S���҃R�[�h			index:8
			arr.add(strCd_user);		//���[�U�[�R�[�h		index:9
			arr.add(strCd_genre);		//�W�������R�[�h		index:10
			arr.add(strCd_ikatu);		//�ꊇ�\�����̃R�[�h	index:11
			arr.add(strCd_youto);		//���i�̗p�r			index:12
			arr.add(strCd_genryo);		//��������				index:13
			arr.add(strKeyword);		//�L�[���[�h			index:14
			arr.add(strNo_page);		//�y�[�WNo			index:15
			
			//�����擾
			for (int i=0;i < userInfoData.getId_gamen().size();i++){
				
				if (userInfoData.getId_gamen().get(i).toString().equals("10")){
					//�f�[�^ID��ݒ�
					strKengen = userInfoData.getId_data().get(i).toString();
				}
			}
			
			arr.add(strKengen);			//�������(�f�[�^ID)	index:16
			
		} catch (Exception e) {
			
			em.ThrowException(e, "����f�[�^���������Ɏ��s���܂����B");
			
		} finally {
			
		}
		return arr;
	}

	/**
	 * ����f�[�^�p�����[�^�[�i�[
	 *  : ����f�[�^�������X�|���X�f�[�^�֊i�[����B
	 * @param lstGroupCmb : �������ʏ�񃊃X�g
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private void storageShisakuData(List<?> lstGroupCmb, RequestResponsTableBean resTable) 
		throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try {
			
			for (int i = 0; i < lstGroupCmb.size(); i++) {

				//�������ʂ̊i�[
				resTable.addFieldVale(i, "flg_return", "true");
				resTable.addFieldVale(i, "msg_error", "");
				resTable.addFieldVale(i, "no_errmsg", "");
				resTable.addFieldVale(i, "nm_class", "");
				resTable.addFieldVale(i, "cd_error", "");
				resTable.addFieldVale(i, "msg_system", "");
				
				Object[] items = (Object[]) lstGroupCmb.get(i);
				int j = 0;

				//���ʂ����X�|���X�f�[�^�Ɋi�[
				resTable.addFieldVale(i, "no_row", items[j++].toString());
				resTable.addFieldVale(i, "no_shisaku", items[j++].toString());
				resTable.addFieldVale(i, "no_seiho", items[j++].toString());
				resTable.addFieldVale(i, "nm_hin", items[j++].toString());
				resTable.addFieldVale(i, "id_toroku", items[j++].toString());
				resTable.addFieldVale(i, "nm_tanto", items[j++].toString());
				resTable.addFieldVale(i, "nm_user", items[j++].toString());
				resTable.addFieldVale(i, "nm_genre", items[j++].toString());
				resTable.addFieldVale(i, "nm_ikatu", items[j++].toString());
				resTable.addFieldVale(i, "nm_youto", items[j++].toString());
				resTable.addFieldVale(i, "nm_genryo", items[j++].toString());
				resTable.addFieldVale(i, "kbn_haishi", items[j++].toString());
				resTable.addFieldVale(i, "nm_haishi", items[j++].toString());
				resTable.addFieldVale(i, "list_max_row", items[j++].toString());
				resTable.addFieldVale(i, "max_row", items[j++].toString());

			}
			
		} catch (Exception e) {
			this.em.ThrowException(e, "����f�[�^���������Ɏ��s���܂����B");
			
		} finally {

		}

	}

}
