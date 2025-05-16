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
import jp.co.blueflag.shisaquick.srv.commonlogic.ImmGetConvList;


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
			//strSql = createShisakuSQL(arrCondition, strSql);
			
			
			
			if (arrCondition.get(17).equals("1")){
				strSql = createShisakuGenkaSQL(arrCondition, strSql);
			}else{
				strSql = createShisakuSQL(arrCondition, strSql);
			}
			
			
			
			
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
			//�������Z�ǉ�
			strAllSql.append("	,tbl.flg_shisanIrai AS flg_shisanIrai ");
			
			strAllSql.append("	FROM ( ");
			
			strSql.append("	SELECT ");
			strSql.append("	(CASE WHEN ROW_NUMBER() OVER (ORDER BY T1.cd_shain, T1.nen, T1.no_oi)%" + strListRowMax + " = 0 THEN " + strListRowMax + " ELSE ROW_NUMBER() OVER (ORDER BY T1.cd_shain, T1.nen, T1.no_oi)%" + strListRowMax + " END) AS no_row ");
			strSql.append("	,Convert(int,(ROW_NUMBER() OVER (ORDER BY T1.cd_shain, T1.nen, T1.no_oi)-1)/" + strListRowMax + " + 1) AS PageNO ");
			strSql.append("	,RIGHT('0000000000' + CONVERT(varchar,T1.cd_shain),10) + '-' +  ");
			strSql.append("	 RIGHT('00' + CONVERT(varchar,T1.nen),2) + '-' + ");
			strSql.append("	 RIGHT('000' + CONVERT(varchar,T1.no_oi),3) as no_shisaku ");
			strSql.append("	,ISNULL(T2.no_seiho1, '') as no_seiho ");
			strSql.append("	,ISNULL(T1.nm_hin, '') as nm_hin ");
			strSql.append("	,ISNULL(T1.id_toroku, 0) as id_toroku ");
			strSql.append("	,ISNULL(M2.nm_user,'') as nm_tanto ");
			strSql.append("	,ISNULL(MU.nm_literal, '') as nm_user ");
			strSql.append("	,ISNULL(MJ.nm_literal, '') as nm_genre ");
			strSql.append("	,ISNULL(MI.nm_literal, '') as nm_ikatu ");
//			strSql.append("	,MS.nm_literal as nm_youto ");
//			strSql.append("	,MT.nm_literal as nm_genryo ");
			strSql.append("	,ISNULL(T1.youto,'') as nm_youto ");
			strSql.append("	,ISNULL(T1.tokuchogenryo,'') as nm_genryo ");
			strSql.append("	,T1.kbn_haishi ");
			strSql.append("	,CASE T1.kbn_haishi WHEN 0 THEN '�g�p�\' WHEN 1 THEN '�p�~' END as nm_haishi ");
			
//2009/10/20 TT.A.ISONO ADD START [�������Z�F���Z�˗��i���j�ǉ�]
			
			strSql.append("	,CASE T4.flg_shisanIrai  WHEN 0 THEN ''  WHEN 1 THEN '$'  END as flg_shisanIrai ");
			
//2009/10/20 TT.A.ISONO ADD END   [�������Z�F���Z�˗��i���j�ǉ�]
						
			strSql.append("	FROM tr_shisakuhin T1 ");
			strSql.append("	LEFT JOIN tr_shisaku T2 ON T1.cd_shain = T2.cd_shain AND T1.nen = T2.nen AND T1.no_oi = T2.no_oi AND T1.seq_shisaku = T2.seq_shisaku ");
//			strSql.append("	LEFT JOIN tr_haigo T3 ON T1.cd_shain = T3.cd_shain AND T1.nen = T3.nen AND T1.no_oi = T3.no_oi ");
//			strSql.append("	LEFT JOIN ma_user M1 ON M1.id_user = ");
//			strSql.append(userInfoData.getId_user());
			strSql.append("	LEFT JOIN ma_user M2 ON T1.id_toroku = M2.id_user ");
			strSql.append("	LEFT JOIN ma_literal MU ON MU.cd_category = 'K_yuza' AND MU.cd_literal = T1.cd_user	");
			strSql.append("	LEFT JOIN ma_literal MJ ON MJ.cd_category = 'K_jyanru' AND MJ.cd_literal = T1.cd_genre ");
			strSql.append("	LEFT JOIN ma_literal MI ON MI.cd_category = 'K_ikatuhyouzi' AND MI.cd_literal = T1.cd_ikatu ");
//			strSql.append("	LEFT JOIN ma_literal MS ON MS.cd_category = 'K_yoto' AND MS.cd_literal = T1.youto ");
//			strSql.append("	LEFT JOIN ma_literal MT ON MT.cd_category = 'K_tokucyogenryo' AND MT.cd_literal = T1.tokuchogenryo ");

			
//2009/10/20 TT.A.ISONO ADD START [�������Z�F���Z�˗��f�[�^����p�̌�����ǉ�]
						
			strSql.append(" LEFT JOIN ( ");
			strSql.append("   SELECT  ");
			strSql.append("    cd_shain ");
			strSql.append("   ,nen ");
			strSql.append("   ,no_oi ");
			strSql.append("   ,MAX(flg_shisanIrai) AS flg_shisanIrai ");
			strSql.append("   FROM ");
			strSql.append("    tr_shisaku ");
			strSql.append("   GROUP BY  ");
			strSql.append("    cd_shain ");
			strSql.append("   ,nen ");
			strSql.append("   ,no_oi ");
			strSql.append("   ) T4 ");
			strSql.append(" ON T1.cd_shain = T4.cd_shain ");
			strSql.append(" AND T1.nen = T4.nen ");
			strSql.append(" AND T1.no_oi = T4.no_oi ");
			
//2009/10/20 TT.A.ISONO ADD END   [�������Z�F���Z�˗��f�[�^����p�̌�����ǉ�]
			
//�@�ȉ��A���������ɂ��t��-----------------------------------------------------------------			
			//�L�[���[�h���������͂���Ă���ꍇ
			//�L�[���[�h�����̃��W�b�N�ύX�@2009/7/15�@isono�@START
			//			if (!arrCondition.get(14).equals("")){
//				strSql.append("	LEFT JOIN (SELECT cd_shain, nen, no_oi ");
//				strSql.append("	FROM tr_haigo ");
//				strSql.append("	WHERE ");
//
//				//�L�[���[�h�𕪉�
//				String[] strKeyWord = arrCondition.get(14).toString().split(",");
//			
//				strSql.append(" ( ");
//				
//				for (int i=0;i<strKeyWord.length;i++){
//			
//					if (i != 0){
//						strSql.append(" AND ");
//					}
//					
//					//���l�̏ꍇ
//					if (strKeyWord[i].matches("[0-9]+")){
//						strSql.append(" (cd_genryo = '");
//						strSql.append(strKeyWord[i] + "'");
//						strSql.append(" OR nm_genryo LIKE '%");
//						strSql.append(strKeyWord[i] + "%') ");
//					//�����̏ꍇ
//					}else{
//						strSql.append(" nm_genryo LIKE '%");
//						strSql.append(strKeyWord[i] + "%' ");
//					}
//				}
//				
//				strSql.append(" ) ");
//				strSql.append(" GROUP BY cd_shain, nen, no_oi ");
//				strSql.append(" ) T3 ");
//				strSql.append("	ON T1.cd_shain = T3.cd_shain AND T1.nen = T3.nen AND T1.no_oi = T3.no_oi ");
//			}

			if (!arrCondition.get(14).equals("")){
				strSql.append("	LEFT JOIN (SELECT HIG_M.cd_shain, HIG_M.nen, HIG_M.no_oi ");
				strSql.append("	FROM tr_haigo  AS HIG_M");

				//�L�[���[�h�𕪉�
				String[] strKeyWord = arrCondition.get(14).toString().split(",");
				
				//IME�ϊ��擾�p�N���X����
				ImmGetConvList ImeSearch = new ImmGetConvList();
			
				for (int i=0;i<strKeyWord.length;i++){
			
					strSql.append("	    RIGHT JOIN (");
					strSql.append("        SELECT");
					strSql.append("              cd_shain");
					strSql.append("            , nen");
					strSql.append("            , no_oi");
					strSql.append("        FROM");
					strSql.append("            tr_haigo");
					strSql.append("        WHERE");
					
//					//����/�����𔻒�@�������̏ꍇ�A�����R�[�h�ƌ��Ȃ�
//					if (strKeyWord[i].matches("[0-9]+")){
//						//���l�̏ꍇ
//						strSql.append(" cd_genryo LIKE '%");
//						strSql.append(strKeyWord[i] + "%'");
//						strSql.append(" OR nm_genryo LIKE '%");
//						strSql.append(strKeyWord[i] + "%' ");
//					}else{
//						//�����̏ꍇ
//						strSql.append(" nm_genryo LIKE '%");
//						strSql.append(strKeyWord[i] + "%' ");
//					}
					
					
					//�L�[���[�h�����̃��W�b�N�ύX�@2009/8/26�@nishigawa�@START
					
					//�ϊ����s
					ArrayList arySearch = ImeSearch.ImmGetConvListChange(strKeyWord[i]);
					
					for(int j = 0; j < arySearch.size(); j++){
						
						//��╶���擾
						String getStr = (String)arySearch.get(j);
						
						//1���ڂ̏ꍇ
						if(j < 1){
							
						//2���ڈȍ~
						}else{
							
							strSql.append(" OR ");
							
						}
						
						//����/�����𔻒�@�������̏ꍇ�A�����R�[�h�ƌ��Ȃ�
						if (getStr.matches("[0-9]+")){
							
							//���l�̏ꍇ
							strSql.append(" cd_genryo LIKE '%");
							strSql.append(getStr + "%'");
							strSql.append(" OR nm_genryo LIKE '%");
							strSql.append(getStr + "%' ");
							
						}else{
							
							//�����̏ꍇ
							strSql.append(" nm_genryo LIKE '%");
							strSql.append(getStr + "%' ");
							
						}
						
					}
					
					//�L�[���[�h�����̃��W�b�N�ύX�@2009/8/26�@nishigawa�@End
					
					strSql.append("	        ) AS HIG_");
					strSql.append(toString(i));
					strSql.append("    ON");
					strSql.append("        HIG_M.cd_shain = HIG_");
					strSql.append(toString(i));
					strSql.append(".cd_shain");
					strSql.append("    AND HIG_M.nen = HIG_");
					strSql.append(toString(i));
					strSql.append(".nen");
					strSql.append("    AND HIG_M.no_oi = HIG_");
					strSql.append(toString(i));
					strSql.append(".no_oi");
				
				}
				
				strSql.append(" WHERE");
				strSql.append("     ISNULL(HIG_M.cd_shain,-1) != -1");
				strSql.append(" AND ISNULL(HIG_M.nen,-1) != -1");
				strSql.append(" AND ISNULL(HIG_M.no_oi,-1) != -1");
				strSql.append(" GROUP BY HIG_M.cd_shain, HIG_M.nen, HIG_M.no_oi ");
				
				strSql.append(" ) T3 ");
				strSql.append("	ON T1.cd_shain = T3.cd_shain AND T1.nen = T3.nen AND T1.no_oi = T3.no_oi ");
			}
			//�L�[���[�h�����̃��W�b�N�ύX�@2009/7/15�@isono�@END
			
			//�u�����ōi���݁v���`�F�b�N����Ă���ꍇ�AAND��ݒ�B
			if (!arrCondition.get(1).equals("")){
				strAndOr = "AND";
			}
 			
			
			
			
			//����No�����͂���Ă���ꍇ
			if (!arrCondition.get(3).equals("")){
				//����R�[�h�𕪉�
				String[] strShisaku = arrCondition.get(3).toString().split("-");
				
				//2009/08/04 UPD START �ۑ臂295�̑Ή�
//				if (!blAndOr){
//					strWhere.append(" ( ");
//					blAndOr = true;
//				} else {
//					strWhere.append(strAndOr);
//					blAndOr = true;
//				}
//				
//				strWhere.append(" ( ");
//				strWhere.append(" T1.cd_shain = ");
//				strWhere.append(strShisaku[0]);
//				strWhere.append(" AND T1.nen = ");
//				strWhere.append(strShisaku[1]);
//				strWhere.append(" AND T1.no_oi = ");
//				strWhere.append(strShisaku[2]);
//				strWhere.append(" ) ");

				if (strShisaku.length >= 1){
					if (!blAndOr){
						strWhere.append(" ( ");
						blAndOr = true;
					} else {
						strWhere.append(strAndOr);
						blAndOr = true;
					}
					
					strWhere.append(" ( ");
					//�Ј�CD�݂̂̏ꍇ
					if (strShisaku.length == 1){
						strWhere.append(" RIGHT('0000000000' + CAST(T1.cd_shain AS VARCHAR), 10) LIKE '%");
						strWhere.append(strShisaku[0] + "%' ");
					}
					//�Ј�CD�ƔN�݂̂̏ꍇ
					if (strShisaku.length == 2){
						if (!(strShisaku[0].equals(""))){
							strWhere.append(" RIGHT('0000000000' + CAST(T1.cd_shain AS VARCHAR), 10) LIKE '%");
							strWhere.append(strShisaku[0] + "%' ");
						}
						if (!(strShisaku[1].equals(""))){
							if (!(strShisaku[0].equals(""))){
								strWhere.append(" AND ");
							}
							strWhere.append(" RIGHT('00' + CAST(T1.nen AS VARCHAR), 2) LIKE '%");
							strWhere.append(strShisaku[1] + "%' ");
						}
					}
					//�Ј�CD�ƔN�ƒǔԂ̏ꍇ
					if (strShisaku.length == 3){
						if (!(strShisaku[0].equals(""))){
							strWhere.append(" RIGHT('0000000000' + CAST(T1.cd_shain AS VARCHAR), 10) LIKE '%");
							strWhere.append(strShisaku[0] + "%' ");
						}
						if (!(strShisaku[1].equals(""))){
							if (!(strShisaku[0].equals(""))){
								strWhere.append(" AND ");
							}
							strWhere.append(" RIGHT('00' + CAST(T1.nen AS VARCHAR), 2) LIKE '%");
							strWhere.append(strShisaku[1] + "%' ");
						}
						if (!(strShisaku[2].equals(""))){
							if (!(strShisaku[0].equals("") && strShisaku[1].equals(""))){
								strWhere.append(" AND ");
							}
							strWhere.append(" RIGHT('000' + CAST(T1.no_oi AS VARCHAR), 3) LIKE '%");
							strWhere.append(strShisaku[2] + "%' ");
						}
					}
					strWhere.append(" ) ");
				}
				//2009/08/04 UPD END
			}
	
			
			
			
			//���@No�����͂���Ă���A���u���@�������Ώہv���`�F�b�N����Ă���ꍇ
			if (!arrCondition.get(4).equals("") && !arrCondition.get(0).equals("")){
				
				//���@No�𕪊�
				String[] strSeiho = arrCondition.get(4).toString().split("-");
//				if (!blAndOr){
//					strWhere.append(" ( ");
//					blAndOr = true;
//				} else {
//					strWhere.append(strAndOr);
//					blAndOr = true;
//				}

//				strWhere.append(" ( ");
//				strWhere.append(" T2.cd_shain = T1.cd_shain ");
//				strWhere.append(" AND T2.nen  = T1.nen ");
//				strWhere.append(" AND T2.no_oi = T1.no_oi ");
//				strWhere.append(" AND T2.seq_shisaku = T1.seq_shisaku ");
//				strWhere.append(" AND T2.no_seiho1 = '");
//				strWhere.append(arrCondition.get(4).toString() + "' ");
//				strWhere.append(" ) ");
				
				if (strSeiho.length >= 1){
					if (!blAndOr){
						strWhere.append(" ( ");
						blAndOr = true;
					} else {
						strWhere.append(strAndOr);
						blAndOr = true;
					}
					strWhere.append(" ( ");
				
					//��ЃR�[�h�݂̂̏ꍇ
					if (strSeiho.length == 1){
						strWhere.append(" SUBSTRING(T2.no_seiho1,1,4) LIKE '%");
						strWhere.append(strSeiho[0] + "%' ");
					}
					//��ЃR�[�h�Ǝ�ʂ̏ꍇ
					if (strSeiho.length == 2){
						if (!(strSeiho[0].equals(""))){
							strWhere.append(" SUBSTRING(T2.no_seiho1,1,4) LIKE '%");
							strWhere.append(strSeiho[0] + "%' ");
						}
						if (!(strSeiho[1].equals(""))){
							if(!strSeiho[0].equals("")){
								strWhere.append(" AND ");
							}
							strWhere.append(" SUBSTRING(T2.no_seiho1,6,3) LIKE '%");
							strWhere.append(strSeiho[1] + "%' ");
						}
					}
					//��ЃR�[�h�Ǝ�ʂƔN�̏ꍇ
					if (strSeiho.length == 3){
						if (!(strSeiho[0].equals(""))){
							strWhere.append(" SUBSTRING(T2.no_seiho1,1,4) LIKE '%");
							strWhere.append(strSeiho[0] + "%' ");
						}
						if (!(strSeiho[1].equals(""))){
							if(!strSeiho[0].equals("")){
								strWhere.append(" AND ");
							}
							strWhere.append(" SUBSTRING(T2.no_seiho1,6,3) LIKE '%");
							strWhere.append(strSeiho[1] + "%' ");
						}
						if (!(strSeiho[2].equals(""))){
							if (!(strSeiho[0].equals("") && strSeiho[1].equals(""))){
								strWhere.append(" AND ");
							}
							strWhere.append(" SUBSTRING(T2.no_seiho1,10,2) LIKE '%");
							strWhere.append(strSeiho[2] + "%' ");
						}
					}
					//��ЃR�[�h�Ǝ�ʂƔN�ƒǔԂ̏ꍇ
					if (strSeiho.length == 4){
						if (!(strSeiho[0].equals(""))){
							strWhere.append(" SUBSTRING(T2.no_seiho1,1,4) LIKE '%");
							strWhere.append(strSeiho[0] + "%' ");
						}
						if (!(strSeiho[1].equals(""))){
							if(!strSeiho[0].equals("")){
								strWhere.append(" AND ");
							}
							strWhere.append(" SUBSTRING(T2.no_seiho1,6,3) LIKE '%");
							strWhere.append(strSeiho[1] + "%' ");
						}
						if (!(strSeiho[2].equals(""))){
							if (!(strSeiho[0].equals("") && strSeiho[1].equals(""))){
								strWhere.append(" AND ");
							}
							strWhere.append(" SUBSTRING(T2.no_seiho1,10,2) LIKE '%");
							strWhere.append(strSeiho[2] + "%' ");
						}
						if (!(strSeiho[3].equals(""))){
							if (!(strSeiho[0].equals("") && strSeiho[1].equals("") && strSeiho[2].equals(""))){
								strWhere.append(" AND ");
							}
							strWhere.append(" SUBSTRING(T2.no_seiho1,13,4) LIKE '%");
							strWhere.append(strSeiho[3] + "%' ");
						}
					}
					strWhere.append(" ) ");
				}
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
						strWhere.append(" AND ");
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
//					strWhere.append(" AND ");
//					strWhere.append(" M1.id_user = ");
//					strWhere.append(userInfoData.getId_user());
					strWhere.append(" AND ");
					strWhere.append(" M2.cd_yakushoku <= '");
					strWhere.append(userInfoData.getCd_literal());				
					strWhere.append("' ) ");									
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
					strWhere.append(" M2.cd_group = ");
					strWhere.append(userInfoData.getCd_group());
					strWhere.append(" AND ");
					strWhere.append(" M2.cd_team = ");
					strWhere.append(userInfoData.getCd_team());
					strWhere.append(" AND ");
					strWhere.append(" M2.cd_yakushoku <= '");
					strWhere.append(userInfoData.getCd_literal());
					strWhere.append("' AND ");
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
						strWhere.append(" AND ");
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

			//�������u���H�ꕪ�v�ȊO�̏ꍇ
			if (!arrCondition.get(16).equals("4")){

//2011/05/19�@�yQP@10181_No.11�z����R�s�[���[�h�����@TT.NISHIGAWA�@start	
				//����A�L���[�s�[�̕��̂݃V�T�N�C�b�N���g�p����ׁA���L�������R�����g�A�E�g
//				if (!blAndOr){
//					blAndOr = true;
//				} else {
//					if (!strWhere.toString().equals("")) {
//						strWhere.append(" AND ");
//						blAndOr = true;
//					}
//				}
//				strWhere.append(" T1.cd_shain IN ");
//				strWhere.append(" (SELECT id_user FROM ma_user WHERE cd_kaisha = " + userInfoData.getCd_kaisha() + ")");
//2011/05/19�@�yQP@10181_No.11�z����R�s�[���[�h�����@TT.NISHIGAWA�@end	
				
			}
			
//2009/10/20 TT.A.ISONO ADD START [�������Z�F�˗��f�[�^�̂݃`�F�b�N���I���̏ꍇ�̏�����ǉ�]

			if (arrCondition.get(17).equals("1")){
				if (!blAndOr){
					strWhere.append(" ");
					blAndOr = true;
					
				} else {
					strWhere.append(" AND ");
					blAndOr = true;
					
				}
				strWhere.append("(ISNULL(T4.flg_shisanIrai,0) = 1)");
				
			}

//2009/10/20 TT.A.ISONO ADD END   [�������Z�F�˗��f�[�^�̂݃`�F�b�N���I���̏ꍇ�̏�����ǉ�]
			
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

			String strKbn_joken4 = "";
			
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
			
			//�������e�C�ݒ�i�����˗��̂݃`�F�b�N�{�b�N�X�I�����ɐݒ�j
			try{
				strKbn_joken4 = reqRecBean.getFieldVale("kbn_joken4");					
				
			}catch(Exception e){
				
			}
			
			//�������������X�g�ɐݒ�
			arr = new ArrayList<Object>();
			//�������e�@�ݒ�		index:0
			arr.add(strKbn_joken1);		
			//�������e�A�ݒ�		index:1
			arr.add(strKbn_joken2);		
			//�������e�B�ݒ�		index:2
			arr.add(strKbn_joken3);		
			//���쇂�ݒ�			index:3
			arr.add(strNo_shisaku);		
			//���@���ݒ�			index:4
			arr.add(strNo_seiho);		
			//���얼�ݒ�			index:5
			arr.add(strNm_shisaku);		
			//�����O���[�v�R�[�h		index:6
			arr.add(strCd_group);		
			//�����`�[���R�[�h		index:7
			arr.add(strCd_team);		
			//�S���҃R�[�h			index:8
			arr.add(strCd_tanto);		
			//���[�U�[�R�[�h		index:9
			arr.add(strCd_user);		
			//�W�������R�[�h			index:10
			arr.add(strCd_genre);		
			//�ꊇ�\�����̃R�[�h	index:11
			arr.add(strCd_ikatu);		
			//���i�̗p�r			index:12
			arr.add(strCd_youto);		
			//��������			index:13
			arr.add(strCd_genryo);		
			//�L�[���[�h			index:14
			arr.add(strKeyword);		
			//�y�[�WNo			index:15
			arr.add(strNo_page);		
			
			//�����擾
			for (int i=0;i < userInfoData.getId_gamen().size();i++){
				
				if (userInfoData.getId_gamen().get(i).toString().equals("10")){
					//�f�[�^ID��ݒ�
					strKengen = userInfoData.getId_data().get(i).toString();
				}
			}
			//�������(�f�[�^ID)	index:16
			arr.add(strKengen);			
			
//2009/10/20 TT.A.ISONO ADD START [�������Z�F�����˗��̂݃`�F�b�N�{�b�N�X�̏�Ԃ�ݒ肷��]
			
			//�������e�C�ݒ�		index:17
			arr.add(strKbn_joken4);

//2009/10/20 TT.A.ISONO ADD END   [�������Z�F�����˗��̂݃`�F�b�N�{�b�N�X�̏�Ԃ�ݒ肷��]
			
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
//				resTable.addFieldVale(i, "no_row", items[j++].toString());
//				resTable.addFieldVale(i, "no_shisaku", items[j++].toString());
//				resTable.addFieldVale(i, "no_seiho", items[j++].toString());
//				resTable.addFieldVale(i, "nm_hin", items[j++].toString());
//				resTable.addFieldVale(i, "id_toroku", items[j++].toString());
//				resTable.addFieldVale(i, "nm_tanto", items[j++].toString());
//				resTable.addFieldVale(i, "nm_user", items[j++].toString());
//				resTable.addFieldVale(i, "nm_genre", items[j++].toString());
//				resTable.addFieldVale(i, "nm_ikatu", items[j++].toString());
//				resTable.addFieldVale(i, "nm_youto", items[j++].toString());
//				resTable.addFieldVale(i, "nm_genryo", items[j++].toString());
//				resTable.addFieldVale(i, "kbn_haishi", items[j++].toString());
//				resTable.addFieldVale(i, "nm_haishi", items[j++].toString());
//				resTable.addFieldVale(i, "list_max_row", items[j++].toString());
//				resTable.addFieldVale(i, "max_row", items[j++].toString());

				resTable.addFieldVale(i, "no_row", toString(items[j++]));
				resTable.addFieldVale(i, "no_shisaku", toString(items[j++]));
				resTable.addFieldVale(i, "no_seiho", toString(items[j++]));
				resTable.addFieldVale(i, "nm_hin", toString(items[j++]));
				resTable.addFieldVale(i, "id_toroku", toString(items[j++]));
				resTable.addFieldVale(i, "nm_tanto", toString(items[j++]));
				resTable.addFieldVale(i, "nm_user", toString(items[j++]));
				resTable.addFieldVale(i, "nm_genre", toString(items[j++]));
				resTable.addFieldVale(i, "nm_ikatu", toString(items[j++]));
				resTable.addFieldVale(i, "nm_youto", toString(items[j++]));
				resTable.addFieldVale(i, "nm_genryo", toString(items[j++]));
				resTable.addFieldVale(i, "kbn_haishi", toString(items[j++]));
				resTable.addFieldVale(i, "nm_haishi", toString(items[j++]));
				resTable.addFieldVale(i, "list_max_row", toString(items[j++]));
				resTable.addFieldVale(i, "max_row", toString(items[j++]));

//2009/10/20 TT.A.ISONO ADD START [�������Z�F�������Z�˗��}�[�N�i���j��ǉ�]
				
				resTable.addFieldVale(i, "dara", toString(items[j++]));

//2009/10/20 TT.A.ISONO ADD END   [�������Z�F�������Z�˗��}�[�N�i���j��ǉ�]
				
			}
			
		} catch (Exception e) {
			this.em.ThrowException(e, "����f�[�^���������Ɏ��s���܂����B");
			
		} finally {

		}

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
	private StringBuffer createShisakuGenkaSQL(ArrayList<Object> arrCondition, StringBuffer strSql) 
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
			//�������Z�ǉ�
			strAllSql.append("	,tbl.flg_shisanIrai AS flg_shisanIrai ");
			
			strAllSql.append("	FROM ( ");
			
			strSql.append("	SELECT ");
			strSql.append("	(CASE WHEN ROW_NUMBER() OVER (ORDER BY T1.cd_shain, T1.nen, T1.no_oi)%" + strListRowMax + " = 0 THEN " + strListRowMax + " ELSE ROW_NUMBER() OVER (ORDER BY T1.cd_shain, T1.nen, T1.no_oi)%" + strListRowMax + " END) AS no_row ");
			strSql.append("	,Convert(int,(ROW_NUMBER() OVER (ORDER BY T1.cd_shain, T1.nen, T1.no_oi)-1)/" + strListRowMax + " + 1) AS PageNO ");
			strSql.append("	,RIGHT('0000000000' + CONVERT(varchar,T1.cd_shain),10) + '-' +  ");
			strSql.append("	 RIGHT('00' + CONVERT(varchar,T1.nen),2) + '-' + ");
			strSql.append("	 RIGHT('000' + CONVERT(varchar,T1.no_oi),3) as no_shisaku ");
			strSql.append("	,ISNULL(T2.no_seiho1, '') as no_seiho ");
			strSql.append("	,ISNULL(T1.nm_hin, '') as nm_hin ");
			strSql.append("	,ISNULL(T1.id_toroku, 0) as id_toroku ");
			strSql.append("	,ISNULL(M2.nm_user,'') as nm_tanto ");
			strSql.append("	,ISNULL(MU.nm_literal, '') as nm_user ");
			strSql.append("	,ISNULL(MJ.nm_literal, '') as nm_genre ");
			strSql.append("	,ISNULL(MI.nm_literal, '') as nm_ikatu ");
			strSql.append("	,ISNULL(T1.youto,'') as nm_youto ");
			strSql.append("	,ISNULL(T1.tokuchogenryo,'') as nm_genryo ");
			strSql.append("	,T1.kbn_haishi ");
			strSql.append("	,CASE T1.kbn_haishi WHEN 0 THEN '�g�p�\' WHEN 1 THEN '�p�~' END as nm_haishi ");
			
//2009/10/20 TT.A.ISONO ADD START [�������Z�F���Z�˗��i���j�ǉ�]
			
			strSql.append("	,CASE T4.flg_shisanIrai  WHEN 0 THEN ''  WHEN 1 THEN '$'  END as flg_shisanIrai ");
			
//2009/10/20 TT.A.ISONO ADD END   [�������Z�F���Z�˗��i���j�ǉ�]
						
			strSql.append("	FROM tr_shisakuhin T1 ");
			

//2009/11/07 TT.NISHIGAWA ADD START [�������Z]
			
			strSql.append("	INNER JOIN tr_shisan_shisakuhin T0 ON T1.cd_shain = T0.cd_shain AND T1.nen = T0.nen AND T1.no_oi = T0.no_oi ");
			
			//�yQP@00342�z���ł̃f�[�^�̂ݎ擾
			strSql.append("	AND T0.no_eda = 0 ");
			
//2009/11/07 TT.NISHIGAWA ADD END [�������Z]
			
			
			strSql.append("	LEFT JOIN tr_shisaku T2 ON T1.cd_shain = T2.cd_shain AND T1.nen = T2.nen AND T1.no_oi = T2.no_oi AND T1.seq_shisaku = T2.seq_shisaku ");
			strSql.append("	LEFT JOIN ma_user M2 ON T1.id_toroku = M2.id_user ");
			strSql.append("	LEFT JOIN ma_literal MU ON MU.cd_category = 'K_yuza' AND MU.cd_literal = T1.cd_user	");
			strSql.append("	LEFT JOIN ma_literal MJ ON MJ.cd_category = 'K_jyanru' AND MJ.cd_literal = T1.cd_genre ");
			strSql.append("	LEFT JOIN ma_literal MI ON MI.cd_category = 'K_ikatuhyouzi' AND MI.cd_literal = T1.cd_ikatu ");

			
//2009/10/20 TT.A.ISONO ADD START [�������Z�F���Z�˗��f�[�^����p�̌�����ǉ�]
						
			strSql.append(" LEFT JOIN ( ");
			strSql.append("   SELECT  ");
			strSql.append("    cd_shain ");
			strSql.append("   ,nen ");
			strSql.append("   ,no_oi ");
			strSql.append("   ,MAX(flg_shisanIrai) AS flg_shisanIrai ");
			strSql.append("   FROM ");
			strSql.append("    tr_shisaku ");
			strSql.append("   GROUP BY  ");
			strSql.append("    cd_shain ");
			strSql.append("   ,nen ");
			strSql.append("   ,no_oi ");
			strSql.append("   ) T4 ");
			strSql.append(" ON T1.cd_shain = T4.cd_shain ");
			strSql.append(" AND T1.nen = T4.nen ");
			strSql.append(" AND T1.no_oi = T4.no_oi ");
			
//2009/10/20 TT.A.ISONO ADD END   [�������Z�F���Z�˗��f�[�^����p�̌�����ǉ�]
			

			if (!arrCondition.get(14).equals("")){
				strSql.append("	LEFT JOIN (SELECT HIG_M.cd_shain, HIG_M.nen, HIG_M.no_oi ");
				strSql.append("	FROM tr_haigo  AS HIG_M");

				//�L�[���[�h�𕪉�
				String[] strKeyWord = arrCondition.get(14).toString().split(",");
				
				//IME�ϊ��擾�p�N���X����
				ImmGetConvList ImeSearch = new ImmGetConvList();
			
				for (int i=0;i<strKeyWord.length;i++){
			
					strSql.append("	    RIGHT JOIN (");
					strSql.append("        SELECT");
					strSql.append("              cd_shain");
					strSql.append("            , nen");
					strSql.append("            , no_oi");
					strSql.append("        FROM");
					strSql.append("            tr_haigo");
					strSql.append("        WHERE");
					
					//�L�[���[�h�����̃��W�b�N�ύX�@2009/8/26�@nishigawa�@START
					
					//�ϊ����s
					ArrayList arySearch = ImeSearch.ImmGetConvListChange(strKeyWord[i]);
					
					for(int j = 0; j < arySearch.size(); j++){
						
						//��╶���擾
						String getStr = (String)arySearch.get(j);
						
						//1���ڂ̏ꍇ
						if(j < 1){
							
						//2���ڈȍ~
						}else{
							
							strSql.append(" OR ");
							
						}
						
						//����/�����𔻒�@�������̏ꍇ�A�����R�[�h�ƌ��Ȃ�
						if (getStr.matches("[0-9]+")){
							
							//���l�̏ꍇ
							strSql.append(" cd_genryo LIKE '%");
							strSql.append(getStr + "%'");
							strSql.append(" OR nm_genryo LIKE '%");
							strSql.append(getStr + "%' ");
							
						}else{
							
							//�����̏ꍇ
							strSql.append(" nm_genryo LIKE '%");
							strSql.append(getStr + "%' ");
							
						}
						
					}
					
					//�L�[���[�h�����̃��W�b�N�ύX�@2009/8/26�@nishigawa�@End
					
					strSql.append("	        ) AS HIG_");
					strSql.append(toString(i));
					strSql.append("    ON");
					strSql.append("        HIG_M.cd_shain = HIG_");
					strSql.append(toString(i));
					strSql.append(".cd_shain");
					strSql.append("    AND HIG_M.nen = HIG_");
					strSql.append(toString(i));
					strSql.append(".nen");
					strSql.append("    AND HIG_M.no_oi = HIG_");
					strSql.append(toString(i));
					strSql.append(".no_oi");
				
				}
				
				strSql.append(" WHERE");
				strSql.append("     ISNULL(HIG_M.cd_shain,-1) != -1");
				strSql.append(" AND ISNULL(HIG_M.nen,-1) != -1");
				strSql.append(" AND ISNULL(HIG_M.no_oi,-1) != -1");
				strSql.append(" GROUP BY HIG_M.cd_shain, HIG_M.nen, HIG_M.no_oi ");
				
				strSql.append(" ) T3 ");
				strSql.append("	ON T1.cd_shain = T3.cd_shain AND T1.nen = T3.nen AND T1.no_oi = T3.no_oi ");
			}
			//�L�[���[�h�����̃��W�b�N�ύX�@2009/7/15�@isono�@END
			
			//�u�����ōi���݁v���`�F�b�N����Ă���ꍇ�AAND��ݒ�B
			if (!arrCondition.get(1).equals("")){
				strAndOr = "AND";
			}
 			
			
			
			
			//����No�����͂���Ă���ꍇ
			if (!arrCondition.get(3).equals("")){
				//����R�[�h�𕪉�
				String[] strShisaku = arrCondition.get(3).toString().split("-");
				
				if (strShisaku.length >= 1){
					if (!blAndOr){
						strWhere.append(" ( ");
						blAndOr = true;
					} else {
						strWhere.append(strAndOr);
						blAndOr = true;
					}
					
					strWhere.append(" ( ");
					//�Ј�CD�݂̂̏ꍇ
					if (strShisaku.length == 1){
						strWhere.append(" RIGHT('0000000000' + CAST(T1.cd_shain AS VARCHAR), 10) LIKE '%");
						strWhere.append(strShisaku[0] + "%' ");
					}
					//�Ј�CD�ƔN�݂̂̏ꍇ
					if (strShisaku.length == 2){
						if (!(strShisaku[0].equals(""))){
							strWhere.append(" RIGHT('0000000000' + CAST(T1.cd_shain AS VARCHAR), 10) LIKE '%");
							strWhere.append(strShisaku[0] + "%' ");
						}
						if (!(strShisaku[1].equals(""))){
							if (!(strShisaku[0].equals(""))){
								strWhere.append(" AND ");
							}
							strWhere.append(" RIGHT('00' + CAST(T1.nen AS VARCHAR), 2) LIKE '%");
							strWhere.append(strShisaku[1] + "%' ");
						}
					}
					//�Ј�CD�ƔN�ƒǔԂ̏ꍇ
					if (strShisaku.length == 3){
						if (!(strShisaku[0].equals(""))){
							strWhere.append(" RIGHT('0000000000' + CAST(T1.cd_shain AS VARCHAR), 10) LIKE '%");
							strWhere.append(strShisaku[0] + "%' ");
						}
						if (!(strShisaku[1].equals(""))){
							if (!(strShisaku[0].equals(""))){
								strWhere.append(" AND ");
							}
							strWhere.append(" RIGHT('00' + CAST(T1.nen AS VARCHAR), 2) LIKE '%");
							strWhere.append(strShisaku[1] + "%' ");
						}
						if (!(strShisaku[2].equals(""))){
							if (!(strShisaku[0].equals("") && strShisaku[1].equals(""))){
								strWhere.append(" AND ");
							}
							strWhere.append(" RIGHT('000' + CAST(T1.no_oi AS VARCHAR), 3) LIKE '%");
							strWhere.append(strShisaku[2] + "%' ");
						}
					}
					strWhere.append(" ) ");
				}
				//2009/08/04 UPD END
			}
	
			
			
			
			//���@No�����͂���Ă���A���u���@�������Ώہv���`�F�b�N����Ă���ꍇ
			if (!arrCondition.get(4).equals("") && !arrCondition.get(0).equals("")){
				
				//���@No�𕪊�
				String[] strSeiho = arrCondition.get(4).toString().split("-");
				
				if (strSeiho.length >= 1){
					if (!blAndOr){
						strWhere.append(" ( ");
						blAndOr = true;
					} else {
						strWhere.append(strAndOr);
						blAndOr = true;
					}
					strWhere.append(" ( ");
				
					//��ЃR�[�h�݂̂̏ꍇ
					if (strSeiho.length == 1){
						strWhere.append(" SUBSTRING(T2.no_seiho1,1,4) LIKE '%");
						strWhere.append(strSeiho[0] + "%' ");
					}
					//��ЃR�[�h�Ǝ�ʂ̏ꍇ
					if (strSeiho.length == 2){
						if (!(strSeiho[0].equals(""))){
							strWhere.append(" SUBSTRING(T2.no_seiho1,1,4) LIKE '%");
							strWhere.append(strSeiho[0] + "%' ");
						}
						if (!(strSeiho[1].equals(""))){
							if(!strSeiho[0].equals("")){
								strWhere.append(" AND ");
							}
							strWhere.append(" SUBSTRING(T2.no_seiho1,6,3) LIKE '%");
							strWhere.append(strSeiho[1] + "%' ");
						}
					}
					//��ЃR�[�h�Ǝ�ʂƔN�̏ꍇ
					if (strSeiho.length == 3){
						if (!(strSeiho[0].equals(""))){
							strWhere.append(" SUBSTRING(T2.no_seiho1,1,4) LIKE '%");
							strWhere.append(strSeiho[0] + "%' ");
						}
						if (!(strSeiho[1].equals(""))){
							if(!strSeiho[0].equals("")){
								strWhere.append(" AND ");
							}
							strWhere.append(" SUBSTRING(T2.no_seiho1,6,3) LIKE '%");
							strWhere.append(strSeiho[1] + "%' ");
						}
						if (!(strSeiho[2].equals(""))){
							if (!(strSeiho[0].equals("") && strSeiho[1].equals(""))){
								strWhere.append(" AND ");
							}
							strWhere.append(" SUBSTRING(T2.no_seiho1,10,2) LIKE '%");
							strWhere.append(strSeiho[2] + "%' ");
						}
					}
					//��ЃR�[�h�Ǝ�ʂƔN�ƒǔԂ̏ꍇ
					if (strSeiho.length == 4){
						if (!(strSeiho[0].equals(""))){
							strWhere.append(" SUBSTRING(T2.no_seiho1,1,4) LIKE '%");
							strWhere.append(strSeiho[0] + "%' ");
						}
						if (!(strSeiho[1].equals(""))){
							if(!strSeiho[0].equals("")){
								strWhere.append(" AND ");
							}
							strWhere.append(" SUBSTRING(T2.no_seiho1,6,3) LIKE '%");
							strWhere.append(strSeiho[1] + "%' ");
						}
						if (!(strSeiho[2].equals(""))){
							if (!(strSeiho[0].equals("") && strSeiho[1].equals(""))){
								strWhere.append(" AND ");
							}
							strWhere.append(" SUBSTRING(T2.no_seiho1,10,2) LIKE '%");
							strWhere.append(strSeiho[2] + "%' ");
						}
						if (!(strSeiho[3].equals(""))){
							if (!(strSeiho[0].equals("") && strSeiho[1].equals("") && strSeiho[2].equals(""))){
								strWhere.append(" AND ");
							}
							strWhere.append(" SUBSTRING(T2.no_seiho1,13,4) LIKE '%");
							strWhere.append(strSeiho[3] + "%' ");
						}
					}
					strWhere.append(" ) ");
				}
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

				strWhere.append(" T1.tokuchogenryo LIKE '%");
				strWhere.append(arrCondition.get(13).toString());
				strWhere.append("%'");
			}
	
			//�L�[���[�h���������͂���Ă���ꍇ
			if (!arrCondition.get(14).equals("")){
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
						strWhere.append(" AND ");
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
					strWhere.append(" M2.cd_yakushoku <= '");
					strWhere.append(userInfoData.getCd_literal());				
					strWhere.append("' ) ");									
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
					strWhere.append(" M2.cd_group = ");
					strWhere.append(userInfoData.getCd_group());
					strWhere.append(" AND ");
					strWhere.append(" M2.cd_team = ");
					strWhere.append(userInfoData.getCd_team());
					strWhere.append(" AND ");
					strWhere.append(" M2.cd_yakushoku <= '");
					strWhere.append(userInfoData.getCd_literal());
					strWhere.append("' AND ");
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
						strWhere.append(" AND ");
						blAndOr = true;
					}
				}
				
//2009/11/7 TT.A.ISONO ADD START [�������Z�F�������Z�f�[�^�̍H����m�F]
				
				strWhere.append( " T0.cd_kaisha = "+ userInfoData.getCd_kaisha() );
				strWhere.append( " AND T0.cd_kojo = " + userInfoData.getCd_busho() );
				
//2009/11/7 TT.A.ISONO ADD END [�������Z�F�������Z�f�[�^�̍H����m�F]
				
			}

			//�������u���H�ꕪ�v�ȊO�̏ꍇ
			if (!arrCondition.get(16).equals("4")){
//2011/05/19�@�yQP@10181_No.11�z����R�s�[���[�h�����@TT.NISHIGAWA�@start	
				//����A�L���[�s�[�̕��̂݃V�T�N�C�b�N���g�p����ׁA���L�������R�����g�A�E�g
//				if (!blAndOr){
//					blAndOr = true;
//				} else {
//					if (!strWhere.toString().equals("")) {
//						strWhere.append(" AND ");
//						blAndOr = true;
//					}
//				}
//				strWhere.append(" T1.cd_shain IN ");
//				strWhere.append(" (SELECT id_user FROM ma_user WHERE cd_kaisha = " + userInfoData.getCd_kaisha() + ")");
//2011/05/19�@�yQP@10181_No.11�z����R�s�[���[�h�����@TT.NISHIGAWA�@end	
				
			}
			
//2009/10/20 TT.A.ISONO ADD START [�������Z�F�˗��f�[�^�̂݃`�F�b�N���I���̏ꍇ�̏�����ǉ�]

			if (arrCondition.get(17).equals("1")){
				if (!blAndOr){
					strWhere.append(" ");
					blAndOr = true;
					
				} else {
					strWhere.append(" AND ");
					blAndOr = true;
					
				}
				strWhere.append("(ISNULL(T4.flg_shisanIrai,0) = 1)");
			}

//2009/10/20 TT.A.ISONO ADD END   [�������Z�F�˗��f�[�^�̂݃`�F�b�N���I���̏ꍇ�̏�����ǉ�]
			
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
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
