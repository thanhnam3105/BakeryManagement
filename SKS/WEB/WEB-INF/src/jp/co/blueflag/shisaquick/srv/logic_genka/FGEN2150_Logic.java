package jp.co.blueflag.shisaquick.srv.logic_genka;

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
 * �������Z�f�[�^����DB����
 *  �@�\ID�FFGEN2150�@
 *
 * @author TT.Nishigawa
 * @since  2011/01/31
 */
public class FGEN2150_Logic extends LogicBase {

	/**
	 * ����f�[�^�����R���X�g���N�^
	 * : �C���X�^���X����
	 */
	public FGEN2150_Logic() {
		//���N���X�̃R���X�g���N�^
		super();

	}

	/**
	 * �������Z�f�[�^�擾�Ǘ�
	 *  : �������Z�f�[�^�擾�������Ǘ�����B
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
		List<?> lstRecset2 = null;
		StringBuffer strSqlWhere = new StringBuffer();
		StringBuffer strSql = new StringBuffer();
		StringBuffer strSql2 = new StringBuffer();

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

			//SQL�쐬�iWhere��擾�j
			strSqlWhere = createWhereSQL(reqData,strSqlWhere);

			//SQL�쐬�i�������Z�f�[�^�擾�j
			strSql = createGenkaShisanSQL(reqData,strSql,strSqlWhere);

			//DB�C���X�^���X����
			createSearchDB();

			//�������s�i�������Z�f�[�^�擾�j
			lstRecset = searchDB.dbSearch(strSql.toString());

			//�������ʂ��Ȃ��ꍇ
			if (lstRecset.size() == 0){
				em.ThrowException(ExceptionKind.�x��Exception,"W000401", strSql.toString(), "", "");
			}

			//SQL�쐬�i���Z�˗������No�f�[�^�擾�j
			strSql2 = createGenkaShisanSampleSQL(lstRecset);

			//�������s�i�������Z�f�[�^�擾�j
			lstRecset2 = searchDB.dbSearch(strSql2.toString());

			//�@�\ID�̐ݒ�
			resKind.setID(reqData.getID());

			//�e�[�u�����̐ݒ�
			resKind.addTableItem(reqData.getTableID(0));

			//���X�|���X�f�[�^�̌`��
			storageShisakuData(lstRecset, lstRecset2, resKind.getTableItem(0));

		} catch (Exception e) {
			this.em.ThrowException(e, "�������Z�f�[�^����DB�����Ɏ��s���܂����B");
		} finally {
			//���X�g�̔j��
			removeList(lstRecset);
			removeList(lstRecset2);
			if (searchDB != null) {
				//�Z�b�V�����̃N���[�Y
				searchDB.Close();
				searchDB = null;
			}
			//�ϐ��̍폜
			strSqlWhere = null;
			strSql = null;
			strSql2 = null;
			reqTableBean = null;
			reqRecBean = null;
		}
		return resKind;
	}

	/**
	 *Where��擾SQL�쐬
	 * @param reqData : ���N�G�X�g�f�[�^
	 * @param strSql : ��������SQL
	 * @return strSql�F��������SQL
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private StringBuffer createWhereSQL(
			RequestResponsKindBean reqData
			,StringBuffer strSql
			)
		throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		StringBuffer strWhere = new StringBuffer();

		try {
			//���������擾---------------------------------------------------------------------
			//�������e�@�ݒ�
			String kbn_joken1 = toString(reqData.getFieldVale(0, 0, "kbn_joken1"));
			//�������e�A�ݒ�
			String kbn_joken2 = toString(reqData.getFieldVale(0, 0, "kbn_joken2"));
			//�������e�B�ݒ�
			String kbn_joken3 = toString(reqData.getFieldVale(0, 0, "kbn_joken3"));
			//���쇂�ݒ�
			String no_shisaku = toString(reqData.getFieldVale(0, 0, "no_shisaku"));
			//���얼
			String nm_shisaku = toString(reqData.getFieldVale(0, 0, "nm_shisaku"));
			//���Z����From
			String hi_kizitu_from = toString(reqData.getFieldVale(0, 0, "hi_kizitu_from"));
			//���Z����To
			String hi_kizitu_to = toString(reqData.getFieldVale(0, 0, "hi_kizitu_to"));
			//�����O���[�v�R�[�h
			String cd_group = toString(reqData.getFieldVale(0, 0, "cd_group"));
			//�����`�[���R�[�h
			String cd_team = toString(reqData.getFieldVale(0, 0, "cd_team"));
			//�yQP@20505�zNo.31 2012/09/14 TT H.Shima ADD Start
			//�S����
			String cd_tanto = toString(reqData.getFieldVale(0,0,"cd_tanto"));
			//�yQP@20505�zNo.31 2012/09/14 TT H.Shima ADD End
			//���[�U�[�R�[�h
			String cd_user = toString(reqData.getFieldVale(0, 0, "cd_user"));
			//��ЃR�[�h
			String cd_kaisha = toString(reqData.getFieldVale(0, 0, "cd_kaisha"));
			//�����R�[�h
			String cd_busho = toString(reqData.getFieldVale(0, 0, "cd_busho"));
			//�󋵕���
			String busho_zyokyou = toString(reqData.getFieldVale(0, 0, "busho_zyokyou"));
			//�X�e�[�^�X
			String status = toString(reqData.getFieldVale(0, 0, "status"));
			//�}�Ԏ��
			String eda_shurui = toString(reqData.getFieldVale(0, 0, "eda_shurui"));

			//WHERE��SQL�쐬----------------------------------------------------------------
			strWhere.append(" WHERE 1 = 1 ");

			//����No�����͂���Ă���ꍇ
			if (!no_shisaku.equals("")){
				//����R�[�h�𕪉�
				String[] strShisaku = no_shisaku.split("-");

				if (strShisaku.length >= 1){

					//�Ј�CD�݂̂̏ꍇ
					if (strShisaku.length == 1){
						strWhere.append(" AND RIGHT('0000000000' + CAST(T1.cd_shain AS VARCHAR), 10) LIKE '%");
						strWhere.append(strShisaku[0] + "%' ");
					}
					//�Ј�CD�ƔN�݂̂̏ꍇ
					if (strShisaku.length == 2){
						if (!(strShisaku[0].equals(""))){
							strWhere.append(" AND RIGHT('0000000000' + CAST(T1.cd_shain AS VARCHAR), 10) LIKE '%");
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
							strWhere.append(" AND RIGHT('0000000000' + CAST(T1.cd_shain AS VARCHAR), 10) LIKE '%");
							strWhere.append(strShisaku[0] + "%' ");
						}
						if (!(strShisaku[1].equals(""))){
							strWhere.append(" AND RIGHT('00' + CAST(T1.nen AS VARCHAR), 2) LIKE '%");
							strWhere.append(strShisaku[1] + "%' ");
						}
						if (!(strShisaku[2].equals(""))){
							strWhere.append(" AND RIGHT('000' + CAST(T1.no_oi AS VARCHAR), 3) LIKE '%");
							strWhere.append(strShisaku[2] + "%' ");
						}
					}
					//�Ј�CD�ƔN�ƒǔԂƎ}�Ԃ̏ꍇ
					if (strShisaku.length == 4){
						if (!(strShisaku[0].equals(""))){
							strWhere.append(" AND RIGHT('0000000000' + CAST(T1.cd_shain AS VARCHAR), 10) LIKE '%");
							strWhere.append(strShisaku[0] + "%' ");
						}
						if (!(strShisaku[1].equals(""))){
							strWhere.append(" AND RIGHT('00' + CAST(T1.nen AS VARCHAR), 2) LIKE '%");
							strWhere.append(strShisaku[1] + "%' ");
						}
						if (!(strShisaku[2].equals(""))){
							strWhere.append(" AND RIGHT('000' + CAST(T1.no_oi AS VARCHAR), 3) LIKE '%");
							strWhere.append(strShisaku[2] + "%' ");
						}
						if (!(strShisaku[3].equals(""))){
							strWhere.append(" AND RIGHT('000' + CAST(T1.no_eda AS VARCHAR), 3) LIKE '%");
							strWhere.append(strShisaku[3] + "%' ");
						}
					}
				}
			}

			//���얼�����͂���Ă���ꍇ
			if (!nm_shisaku.equals("")){
				// MOD H.SHIMA �yH24�N�x�Ή��zNo.16 Start
				String searchStr = nm_shisaku.replace("�@"," ");
				String[] multiple_search = searchStr.split(" ");
				for(int i = 0; i < multiple_search.length; i++){
// MOD start �yH24�N�x�Ή��z 20120416 hisahori
//					strWhere.append(" AND T3.nm_hin LIKE '%");
					strWhere.append(" AND ISNULL(T3.nm_hin,'') + ISNULL(T1.nm_edaShisaku,'') LIKE '%");
// MOD end �yH24�N�x�Ή��z 20120416 hisahori
					strWhere.append(multiple_search[i] + "%' ");
				}
//				strWhere.append(" AND T3.nm_hin LIKE '%");
//				strWhere.append(nm_shisaku + "%' ");
				// MOD H.SHIMA �yH24�N�x�Ή��zNo.16 End
			}

			//���Z����From�����͂���Ă���ꍇ
			if (!hi_kizitu_from.equals("")){
				strWhere.append(" AND T1.dt_kizitu >= '");
				strWhere.append(hi_kizitu_from + "' ");
			}

			//���Z����To�����͂���Ă���ꍇ
			if (!hi_kizitu_to.equals("")){
				strWhere.append(" AND T1.dt_kizitu <= '");
				strWhere.append(hi_kizitu_to + "' ");
			}

			//�O���[�v�����͂���Ă���ꍇ
			if (!cd_group.equals("")){
				strWhere.append(" AND T3.cd_group = ");
				strWhere.append(cd_group);
			}

			//�`�[�������͂���Ă���ꍇ
			if (!cd_team.equals("")){
				strWhere.append(" AND T3.cd_team = ");
				strWhere.append(cd_team);
			}

			//�yQP@20505�zNo.31 2012/09/14 TT H.Shima ADD Start
			//�S���҂����͂���Ă���ꍇ
			if(!cd_tanto.equals("")){
				strWhere.append(" AND T3.id_toroku = ");
				strWhere.append(cd_tanto);
			}
			//�yQP@20505�zNo.31 2012/09/14 TT H.Shima ADD Start

			//���[�U�����͂���Ă���ꍇ
			if (!cd_user.equals("")){
				strWhere.append(" AND T3.cd_user = '");
				strWhere.append(cd_user +"'");
			}

			//������Ђ����͂���Ă���ꍇ
			if (!cd_kaisha.equals("")){
				strWhere.append(" AND T1.cd_kaisha = ");
				strWhere.append(cd_kaisha);
			}

			//�����H�ꂪ���͂���Ă���ꍇ
			if (!cd_busho.equals("")){
				strWhere.append(" AND T1.cd_kojo = ");
				strWhere.append(cd_busho);
			}

			//�󋵕����A�X�e�[�^�X�����͂���Ă���ꍇ
			if (!busho_zyokyou.equals("") && !status.equals("")){
				//������
				if(busho_zyokyou.equals("1")){
					strWhere.append(" AND M2.st_kenkyu = ");
					strWhere.append(status);
				}
				//���Y�Ǘ���
				else if(busho_zyokyou.equals("2")){
					strWhere.append(" AND M2.st_seisan = ");
					strWhere.append(status);
				}
				//�����ޒ��B��
				else if(busho_zyokyou.equals("3")){
					strWhere.append(" AND M2.st_gensizai = ");
					strWhere.append(status);
				}
				//�H��
				else if(busho_zyokyou.equals("4")){
					strWhere.append(" AND M2.st_kojo = ");
					strWhere.append(status);
				}
				//�c��
				else if(busho_zyokyou.equals("5")){
					strWhere.append(" AND M2.st_eigyo = ");
					strWhere.append(status);
				}
			}

			//�}�Ԏ�ނ����͂���Ă���ꍇ
			if (!eda_shurui.equals("")){
				strWhere.append(" AND T1.shurui_eda = '");
				strWhere.append(eda_shurui + "'");
			}

			//TODO�^�u���I������Ă���ꍇ
			if (kbn_joken1.equals("0")){
				strWhere.append(" AND M2.st_eigyo < 4 ");
			}

			//�����̓`�F�b�N�{�b�N�X���I������Ă���ꍇ
			if (kbn_joken2.equals("1")){
				strWhere.append(" AND T1.dt_kizitu IS NULL ");
			}

			//�m�F�����`�F�b�N�{�b�N�X���I������Ă���ꍇ
			if(kbn_joken3.equals("1")){
				//�󋵕����A�X�e�[�^�X�����͂���Ă���ꍇ
				if (!busho_zyokyou.equals("") && !status.equals("")){
					strWhere.append(" OR M2.st_eigyo = 3 ");
				}
				else{
					strWhere.append(" AND M2.st_eigyo = 3 ");
				}
			}

			//�����ł̌�������-------------------------------------------------------------------

			//�������Z�ꗗ��ʂ̃f�[�^ID�擾
			String strKengen = "";
			for (int i=0;i < userInfoData.getId_gamen().size();i++){
				if (userInfoData.getId_gamen().get(i).toString().equals("180")){
					//�f�[�^ID��ݒ�
					strKengen = userInfoData.getId_data().get(i).toString();
				}
			}

			// MOD 2013/11/5 QP@30154 okano start
//				//����O���[�v
//				if(strKengen.equals("1")){
//					strWhere.append(" AND ( ");
//					strWhere.append(" ( ");
//					strWhere.append(" T3.cd_group = ");
//					strWhere.append(userInfoData.getCd_group());
//
//	//				strWhere.append(" AND ");
//	//				strWhere.append(" T3.cd_team = ");
//	//				strWhere.append(userInfoData.getCd_team());
//	//				strWhere.append(" AND ");
//	//				strWhere.append(" T3.id_toroku = ");
//	//				strWhere.append(userInfoData.getId_user());
//	//				strWhere.append(" AND ");
//	//				strWhere.append(" M6.cd_yakushoku = '001'");
//
//					strWhere.append(" ) ");
//
//	//
//	//				//�Z�b�V�������D��E�R�[�h���`�[�����[�_�[�̏ꍇ�A�ȉ���OR�����Œǉ�
//	//				int TEAM_LEADER_CD = Integer.parseInt(ConstManager.getConstValue(ConstManager.Category.�ݒ�, "TEAM_LEADER_CD"));
//	//				int user_yakushoku = Integer.parseInt(userInfoData.getCd_literal());
//	//				if( TEAM_LEADER_CD <= user_yakushoku ){
//	//					strWhere.append(" OR ");
//	//					strWhere.append(" ( ");
//	//					strWhere.append(" T3.cd_group = ");
//	//					strWhere.append(userInfoData.getCd_group());
//	//					strWhere.append(" AND ");
//	//					strWhere.append(" T3.cd_team = ");
//	//					strWhere.append(userInfoData.getCd_team());
//	//					strWhere.append(" AND ");
//	//					strWhere.append(" M6.cd_yakushoku <= '");
//	//					strWhere.append(userInfoData.getCd_literal());
//	//					strWhere.append("' ) ");
//	//				}
//	//
//	//				//�Z�b�V�������D��E�R�[�h���O���[�v���[�_�[�̏ꍇ�A�ȉ���OR�����Œǉ�
//	//				int TEAM_GROUP_CD = Integer.parseInt(ConstManager.getConstValue(ConstManager.Category.�ݒ�, "TEAM_LEADER_CD"));
//	//				if( TEAM_GROUP_CD <= user_yakushoku ){
//	//					strWhere.append(" OR ");
//	//					strWhere.append(" ( ");
//	//					strWhere.append(" T3.cd_group = ");
//	//					strWhere.append(userInfoData.getCd_group());
//	//					strWhere.append(" AND ");
//	//					strWhere.append(" M6.cd_yakushoku <= '");
//	//					strWhere.append(userInfoData.getCd_literal());
//	//					strWhere.append("' ) ");
//	//				}
//
//					strWhere.append(" ) ");
//
//				}
//				//���H�ꕪ
//				else if(strKengen.equals("2")){
//					strWhere.append(" AND ( ");
//					strWhere.append(" T1.cd_kaisha = ");
//					strWhere.append(userInfoData.getCd_kaisha());
//					strWhere.append(" AND ");
//					strWhere.append(" T1.cd_kojo = ");
//					strWhere.append(userInfoData.getCd_busho());
//					strWhere.append(" ) ");
//
//				}
//				//�c�ƒS���̂�
//				else if(strKengen.equals("4")){
//					strWhere.append(" AND ( ");
//					strWhere.append(" 	T3.cd_eigyo = " + userInfoData.getId_user());
//					strWhere.append(" 	OR T3.cd_eigyo IN( ");
//					strWhere.append(" 		SELECT DISTINCT id_user ");
//					strWhere.append(" 		FROM ma_user ");
//					strWhere.append(" 		WHERE id_user IN ( ");
//					strWhere.append(" 			SELECT id_user ");
//					strWhere.append(" 			FROM ma_user ");
//					strWhere.append(" 			WHERE id_josi = " + userInfoData.getId_user());
//					strWhere.append(" 		) ");
//					strWhere.append(" 		OR id_josi IN ( ");
//					strWhere.append(" 			SElECT id_user ");
//					strWhere.append(" 			FROM ma_user ");
//					strWhere.append(" 			WHERE id_josi = " + userInfoData.getId_user());
//					strWhere.append(" 		) ");
//					strWhere.append(" 	) ");
//					strWhere.append(" ) ");
//
//				}
//				//�S��
//				else if(strKengen.equals("5")){
//
//				}
			//������
			if(strKengen.equals("1")){
				strWhere.append(" AND ( ");
				strWhere.append(" 	T1.cd_kaisha = " + userInfoData.getCd_kaisha());
				strWhere.append(" 	OR T1.cd_hanseki = " + userInfoData.getCd_kaisha());
				strWhere.append(" ) ");

			}
			//�����H��
			else if(strKengen.equals("2")){
				strWhere.append(" AND ( ");
				strWhere.append(" T1.cd_kaisha = ");
				strWhere.append(userInfoData.getCd_kaisha());
				strWhere.append(" AND ");
				strWhere.append(" T1.cd_kojo = ");
				strWhere.append(userInfoData.getCd_busho());
				strWhere.append(" ) ");

			}
			//���Y�Ǘ���
			else if(strKengen.equals("3")){
				strWhere.append(" AND ( ");
				strWhere.append(" 	T1.cd_kaisha = " + userInfoData.getCd_kaisha());
				strWhere.append(" 	OR T1.cd_hanseki = " + userInfoData.getCd_kaisha());
				strWhere.append(" ) ");

			}
			//�������B��
			else if(strKengen.equals("4")){
				strWhere.append(" AND T1.cd_kaisha = " + userInfoData.getCd_kaisha());

			}
			//�c�Ɓi��ʁj
			else if(strKengen.equals("5")){
				strWhere.append(" AND ( ");
				strWhere.append(" 	T3.cd_eigyo = " + userInfoData.getId_user());
				strWhere.append(" 	OR T3.cd_eigyo IN( ");
				// MOD 2015/03/03 TT.Kitazawa�yQP@40812�zNo.19 start
//				strWhere.append(" 		SELECT DISTINCT id_user ");
//				strWhere.append(" 		FROM ma_user ");
//				strWhere.append(" 		WHERE id_user IN ( ");
//				strWhere.append(" 			SELECT id_user ");
//				strWhere.append(" 			FROM ma_user ");
//				strWhere.append(" 			WHERE id_josi = " + userInfoData.getId_user());
//				strWhere.append(" 		) ");
//				strWhere.append(" 		OR id_josi IN ( ");
//				strWhere.append(" 			SElECT id_user ");
//				strWhere.append(" 			FROM ma_user ");
//				strWhere.append(" 			WHERE id_josi = " + userInfoData.getId_user());
//				strWhere.append(" 		) ");

				// ���O�C�����[�U�̋��L�����o�[���{����
				strWhere.append(" 		SELECT id_member ");
				strWhere.append(" 		FROM ma_member  ");
				strWhere.append(" 		WHERE id_user = " + userInfoData.getId_user());
				// MOD 2015/03/03 TT.Kitazawa�yQP@40812�zNo.19 end
				strWhere.append(" 	) ");
				strWhere.append(" ) ");
				strWhere.append(" AND T1.cd_hanseki = " + userInfoData.getCd_kaisha());

			}
			//�c�Ɓi�{�������j
			else if(strKengen.equals("6")){
				strWhere.append(" AND T1.cd_hanseki = " + userInfoData.getCd_kaisha());

			}
			//�S��
			else if(strKengen.equals("7")){

			}
			// MOD 2013/11/5 QP@30154 okano end


		} catch (Exception e) {

			em.ThrowException(e, "�������Z�f�[�^���������Ɏ��s���܂����B");

		} finally {

		}

		return strWhere;
	}

	/**
	 * �������Z�f�[�^�擾SQL�쐬
	 *  : �������Z�f�[�^���擾����SQL���쐬�B
	 * @param strSql : ��������SQL
	 * @param arrCondition�F������������
	 * @return strSql�F��������SQL
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private StringBuffer createGenkaShisanSQL(
			RequestResponsKindBean reqData
			,StringBuffer strSql
			,StringBuffer strWhere
			)
		throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		StringBuffer strAllSql = new StringBuffer();

		try {
			//�y�[�W�ԍ�
			String no_page = toString(reqData.getFieldVale(0, 0, "no_page"));

			//�ő�s���̎擾
			String strListRowMax = ConstManager.getConstValue(ConstManager.Category.�ݒ�,"LIST_ROW_MAX");

			// ADD 2013/8/7 okano�yQP@30151�zNo.12 start
			String kbn_joken1 = toString(reqData.getFieldVale(0, 0, "kbn_joken1"));
			// ADD 2013/8/7 okano�yQP@30151�zNo.12 end

			//SQL���̍쐬
			strAllSql.append("	SELECT ");
			strAllSql.append("		 tbl.no_row ");
			strAllSql.append("		,tbl.dt_kizitu ");
			strAllSql.append("		,tbl.su_irai ");
			strAllSql.append("		,tbl.no_shisaku ");
			strAllSql.append("		,tbl.no_eda ");
			strAllSql.append("		,tbl.nm_hin ");
			strAllSql.append("		,tbl.nm_shurui ");
			strAllSql.append("		,tbl.st_kenkyu ");
			strAllSql.append("		,tbl.st_seisan ");
			strAllSql.append("		,tbl.st_gensizai ");
			strAllSql.append("		,tbl.st_kojo ");
			strAllSql.append("		,tbl.st_eigyo ");
			strAllSql.append("		,saiyo_sample ");
			strAllSql.append("		,nm_sample ");
			strAllSql.append("		,nm_team ");
			strAllSql.append("		,nm_liuser ");
			strAllSql.append("		,nm_kaisha ");
			strAllSql.append("		,nm_busho ");
			strAllSql.append("		,memo_eigyo ");
			//  �yQP@10713�z2011.10.28 ADD start hisahori
			strAllSql.append("		,cd_nisugata ");
			strAllSql.append("		,nm_ondo ");
			strAllSql.append("		,mn_tantoeigyo ");
			strAllSql.append("		,mn_tantosya ");
			//  �yQP@10713�z2011.10.28 ADD end
			strAllSql.append("		," + strListRowMax + " AS list_max_row ");
			strAllSql.append("		,cnttbl.max_row ");
			strAllSql.append("	 ");
			strAllSql.append("	FROM ");
			strAllSql.append("	( ");
			strAllSql.append("		SELECT  ");
			strAllSql.append("			(CASE ");
			strAllSql.append("				WHEN ROW_NUMBER() OVER ( ");
			strAllSql.append("				ORDER BY ");
			// MOD 2013/8/7 okano�yQP@30151�zNo.12 start
//				strAllSql.append("					TBL2.dt_kizitu ");
//				strAllSql.append("					,TBL2.no_shisaku ");
//				strAllSql.append("					,TBL2.no_eda ");
//				strAllSql.append("					)%" + strListRowMax + " = 0 THEN " + strListRowMax + " ");
//				strAllSql.append("			ELSE ROW_NUMBER() OVER ( ");
//				strAllSql.append("				ORDER BY  ");
//				strAllSql.append("					TBL2.dt_kizitu ");
//				strAllSql.append("					,TBL2.no_shisaku ");
//				strAllSql.append("					,TBL2.no_eda ");
			if(kbn_joken1.equals("0")){
				strAllSql.append("					TBL2.dt_kizitu ");
				strAllSql.append("					,TBL2.no_shisaku ");
				strAllSql.append("					,TBL2.no_eda ");
				strAllSql.append("					)%" + strListRowMax + " = 0 THEN " + strListRowMax + " ");
				strAllSql.append("			ELSE ROW_NUMBER() OVER ( ");
				strAllSql.append("				ORDER BY ");
				strAllSql.append("					TBL2.dt_kizitu ");
				strAllSql.append("					,TBL2.no_shisaku ");
				strAllSql.append("					,TBL2.no_eda ");
			} else {
				strAllSql.append("					case when TBL2.st_eigyo = 4 and TBL2.saiyo_sample < 0 then 4 ");
				strAllSql.append("						when TBL2.st_eigyo = 4 then 1 ");
				strAllSql.append("						when TBL2.st_eigyo = 3 then 2 ");
				strAllSql.append("						when TBL2.st_eigyo = 2 then 3 ");
				strAllSql.append("						else 5 end ");
				strAllSql.append("					,case when TBL2.fg_chusi IS NULL then 0 ");
				strAllSql.append("						else 1 end ");
				strAllSql.append("					,TBL2.dt_shisan desc ");
				strAllSql.append("					,TBL2.no_shisaku asc ");
				strAllSql.append("					,TBL2.no_eda asc ");
				strAllSql.append("					)%" + strListRowMax + " = 0 THEN " + strListRowMax + " ");
				strAllSql.append("			ELSE ROW_NUMBER() OVER ( ");
				strAllSql.append("				ORDER BY ");
				strAllSql.append("					case when TBL2.st_eigyo = 4 and TBL2.saiyo_sample < 0 then 4 ");
				strAllSql.append("						when TBL2.st_eigyo = 4 then 1 ");
				strAllSql.append("						when TBL2.st_eigyo = 3 then 2 ");
				strAllSql.append("						when TBL2.st_eigyo = 2 then 3 ");
				strAllSql.append("						else 5 end ");
				strAllSql.append("					,case when TBL2.fg_chusi IS NULL then 0 ");
				strAllSql.append("						else 1 end ");
				strAllSql.append("					,TBL2.dt_shisan desc ");
				strAllSql.append("					,TBL2.no_shisaku asc ");
				strAllSql.append("					,TBL2.no_eda asc ");
			}
			// MOD 2013/8/7 okano�yQP@30151�zNo.12 end
			strAllSql.append("					)%" + strListRowMax + " ");
			strAllSql.append("			END) AS no_row ");
			strAllSql.append("			,Convert(int ");
			strAllSql.append("				,(ROW_NUMBER() OVER ( ");
			strAllSql.append("					ORDER BY ");
			// MOD 2013/8/7 okano�yQP@30151�zNo.12 start
//				strAllSql.append("						TBL2.dt_kizitu ");
//				strAllSql.append("						,TBL2.no_shisaku ");
//				strAllSql.append("						,TBL2.no_eda ");
			if(kbn_joken1.equals("0")){
				strAllSql.append("						TBL2.dt_kizitu ");
				strAllSql.append("						,TBL2.no_shisaku ");
				strAllSql.append("						,TBL2.no_eda ");

			} else {
				strAllSql.append("						case when TBL2.st_eigyo = 4 and TBL2.saiyo_sample < 0 then 4 ");
				strAllSql.append("							when TBL2.st_eigyo = 4 then 1 ");
				strAllSql.append("							when TBL2.st_eigyo = 3 then 2 ");
				strAllSql.append("							when TBL2.st_eigyo = 2 then 3 ");
				strAllSql.append("							else 5 end ");
				strAllSql.append("						,case when TBL2.fg_chusi IS NULL then 0 ");
				strAllSql.append("							else 1 end ");
				strAllSql.append("						,TBL2.dt_shisan desc ");
				strAllSql.append("						,TBL2.no_shisaku asc ");
				strAllSql.append("						,TBL2.no_eda asc ");
			}
			// MOD 2013/8/7 okano�yQP@30151�zNo.12 end
			strAllSql.append("					)-1)/" + strListRowMax + " + 1) AS PageNO ");
			strAllSql.append("			,TBL2.dt_kizitu ");
			strAllSql.append("			,TBL2.su_irai ");
			strAllSql.append("			,TBL2.no_shisaku ");
			strAllSql.append("			,TBL2.no_eda ");
			strAllSql.append("			,TBL2.nm_shurui ");
			strAllSql.append("			,TBL2.nm_hin ");
			strAllSql.append("			,TBL2.st_kenkyu ");
			strAllSql.append("			,TBL2.st_seisan ");
			strAllSql.append("			,TBL2.st_gensizai ");
			strAllSql.append("			,TBL2.st_kojo ");
			strAllSql.append("			,TBL2.st_eigyo ");
			strAllSql.append("			,TBL2.saiyo_sample ");
			strAllSql.append("			,TBL2.nm_sample ");
			strAllSql.append("			,TBL2.nm_team ");
			strAllSql.append("			,TBL2.nm_liuser ");
			strAllSql.append("			,TBL2.nm_kaisha ");
			strAllSql.append("			,TBL2.nm_busho ");
			strAllSql.append("			,TBL2.memo_eigyo ");
			//  �yQP@10713�z2011.10.28 ADD start hisahori
			strAllSql.append("			,TBL2.cd_nisugata ");
			strAllSql.append("			,TBL2.nm_ondo ");
			strAllSql.append("			,TBL2.mn_tantoeigyo ");
			strAllSql.append("			,TBL2.mn_tantosya ");
			// ADD 2013/8/7 okano�yQP@30151�zNo.12 start
			strAllSql.append("			,TBL2.dt_shisan ");
			strAllSql.append("			,TBL2.fg_chusi ");
			// ADD 2013/8/7 okano�yQP@30151�zNo.12 end
			//  �yQP@10713�z2011.10.28 ADD end
			strAllSql.append("		FROM ");
			strAllSql.append("		( ");
			strAllSql.append("			SELECT ");
			strAllSql.append("				CASE M2.st_eigyo ");
			strAllSql.append("					WHEN 3 THEN '�m�F����' ");
			strAllSql.append("					WHEN 4 THEN '�̗p����' ");
			strAllSql.append("					ELSE ISNULL(CONVERT(VARCHAR,T1.dt_kizitu,111),'����') ");
			strAllSql.append("				END AS dt_kizitu ");
			strAllSql.append("				, T1.su_irai ");
			strAllSql.append("				, RIGHT('0000000000' + CONVERT(varchar ");
			strAllSql.append("						,T1.cd_shain) ");
			strAllSql.append("					,10) + '-' + RIGHT('00' + CONVERT(varchar ");
			strAllSql.append("						,T1.nen) ");
			strAllSql.append("					,2) + '-' + RIGHT('000' + CONVERT(varchar ");
			strAllSql.append("						,T1.no_oi) ");
			strAllSql.append("					,3) as no_shisaku ");
			strAllSql.append("				, T1.no_eda ");
			strAllSql.append("				, M1.nm_��iteral AS nm_shurui ");
// MOD start �yH24�N�x�Ή��z 20120416 hisahori
//			strAllSql.append("				, T3.nm_hin ");
			//TODO strAllSql.append("				, CASE WHEN T1.nm_edaShisaku IS NULL THEN T3.nm_hin ELSE T3.nm_hin + ' �y' + T1.nm_edaShisaku + '�z' END AS nm_hin");
			strAllSql.append(" , CASE WHEN T1.nm_edaShisaku IS NOT NULL ");
			strAllSql.append("  THEN ");
			strAllSql.append("	CASE RTRIM(T1.nm_edaShisaku) WHEN '' ");
			strAllSql.append("	THEN T3.nm_hin ");
			strAllSql.append("	ELSE T3.nm_hin + ' �y' + T1.nm_edaShisaku + '�z' END ");
			strAllSql.append(" ELSE T3.nm_hin END AS nm_hin ");
// MOD end �yH24�N�x�Ή��z 20120416 hisahori
			strAllSql.append("				, M2.st_kenkyu ");
			strAllSql.append("				, M2.st_seisan ");
			strAllSql.append("				, M2.st_gensizai ");
			strAllSql.append("				, M2.st_kojo ");
			strAllSql.append("				, M2.st_eigyo ");
			strAllSql.append("				, T1.saiyo_sample ");
			strAllSql.append("				, CASE T1.saiyo_sample ");
			strAllSql.append("				WHEN -1 THEN '�̗p�Ȃ�' ");
			strAllSql.append("				ELSE T2.nm_sample ");
			strAllSql.append("				END AS nm_sample ");
			strAllSql.append("				, M3.nm_team ");
			strAllSql.append("				, M4.nm_��iteral AS nm_liuser ");
			strAllSql.append("				, M5.nm_kaisha ");
			strAllSql.append("				, M5.nm_busho ");
			strAllSql.append("				, T4.memo_eigyo ");
			//  �yQP@10713�z2011.10.28 ADD start hisahori
			//2011/12/21 TT H.SHIMA MOD Start	�׎p�擾�e�[�u�������삩�猴�����Z��
//			strAllSql.append("				, T3.cd_nisugata ");
			strAllSql.append("				, T1.cd_nisugata ");
			//2011/12/21 TT H.SHIMA MOD End
			strAllSql.append("				, M111.nm_literal AS nm_ondo ");
			strAllSql.append("				, M113.nm_user AS mn_tantoeigyo ");
			strAllSql.append("				, M114.nm_user AS mn_tantosya ");
			// ADD 2013/8/7 okano�yQP@30151�zNo.12 start
			strAllSql.append("				, T5.dt_shisan ");
			strAllSql.append("				, T5.fg_chusi ");
			// ADD 2013/8/7 okano�yQP@30151�zNo.12 end
			//  �yQP@10713�z2011.10.28 ADD end
			strAllSql.append("				 ");
			strAllSql.append("			FROM tr_shisan_shisakuhin AS T1 ");
			strAllSql.append("				LEFT JOIN ma_literal AS M1 ");
			strAllSql.append("				ON 'shurui_eda' = M1.cd_category ");
			strAllSql.append("				AND T1.shurui_eda = M1.cd_��iteral ");
			strAllSql.append("				LEFT JOIN tr_shisan_status AS M2 ");
			strAllSql.append("				ON T1.cd_shain = M2.cd_shain ");
			strAllSql.append("				AND T1.nen = M2.nen ");
			strAllSql.append("				AND T1.no_oi = M2.no_oi ");
			strAllSql.append("				AND T1.no_eda = M2.no_eda ");
			strAllSql.append("				LEFT JOIN tr_shisaku AS T2 ");
			strAllSql.append("				ON T1.cd_shain = T2.cd_shain ");
			strAllSql.append("				AND T1.nen = T2.nen ");
			strAllSql.append("				AND T1.no_oi = T2.no_oi ");
			strAllSql.append("				AND T1.saiyo_sample = T2.seq_shisaku ");
			strAllSql.append("				LEFT JOIN tr_shisakuhin AS T3 ");
			strAllSql.append("				ON T1.cd_shain = T3.cd_shain ");
			strAllSql.append("				AND T1.nen = T3.nen ");
			strAllSql.append("				AND T1.no_oi = T3.no_oi ");
			strAllSql.append("				LEFT JOIN ma_team AS M3 ");
			strAllSql.append("				ON T3.cd_group = M3.cd_group ");
			strAllSql.append("				AND T3.cd_team = M3.cd_team ");
			strAllSql.append("				LEFT JOIN ma_literal AS M4 ");
			strAllSql.append("				ON 'K_yuza' = M4.cd_category ");
			strAllSql.append("				AND T3.cd_user = M4.cd_��iteral ");
			strAllSql.append("				LEFT JOIN ma_busho AS M5 ");
			strAllSql.append("				ON T1.cd_kaisha = M5.cd_kaisha ");
			strAllSql.append("				AND T1.cd_kojo = M5.cd_busho ");
			strAllSql.append("				LEFT JOIN tr_shisan_memo_eigyo AS T4 ");
			strAllSql.append("				ON T1.cd_shain = T4.cd_shain ");
			strAllSql.append("				AND T1.nen = T4.nen ");
			strAllSql.append("				AND T1.no_oi = T4.no_oi ");
			strAllSql.append("				LEFT JOIN ma_user M6 ");
			strAllSql.append("				ON T3.id_toroku = M6.id_user ");
			//  �yQP@10713�z2011.10.28 ADD start hisahori
			strAllSql.append("				LEFT JOIN ma_literal AS M111 ");
			strAllSql.append("				ON 'K_toriatukaiondo' = M111.cd_category AND T3.cd_ondo = M111.cd_literal ");
			strAllSql.append("				LEFT JOIN ma_user M113 ");
			strAllSql.append("				ON T3.cd_eigyo = M113.id_user ");
			strAllSql.append("				LEFT JOIN ma_user M114 ");
			strAllSql.append("				ON T1.cd_shain = M114.id_user ");
			// ADD 2013/8/7 okano�yQP@30151�zNo.12 start
			strAllSql.append("				left join tr_shisan_shisaku AS T5 ");
			strAllSql.append("				on T1.cd_shain = T5.cd_shain ");
			strAllSql.append("				and T1.nen = T5.nen ");
			strAllSql.append("				and T1.no_oi = T5.no_oi ");
			strAllSql.append("				and T1.no_eda = T5.no_eda ");
			strAllSql.append("				and T5.seq_shisaku = ( ");
			strAllSql.append("				select top 1 seq_shisaku from tr_shisan_shisaku ");
			strAllSql.append("				where cd_shain = T1.cd_shain ");
			strAllSql.append("				and nen = T1.nen ");
			strAllSql.append("				and no_oi = T1.no_oi ");
			strAllSql.append("				and no_eda = T1.no_eda ");
			strAllSql.append("				order by ");
			strAllSql.append("				case when fg_chusi IS NULL then 0 ");
			strAllSql.append("					else 1 end ");
			strAllSql.append("				,dt_shisan desc ");
			strAllSql.append("				,seq_shisaku ");
			strAllSql.append("				) ");
			// ADD 2013/8/7 okano�yQP@30151�zNo.12 end
			//  �yQP@10713�z2011.10.28 ADD end
			strAllSql.append(strWhere);
			strAllSql.append("		) AS TBL2 ");
			strAllSql.append("	) AS tbl ");
			strAllSql.append("	,( ");
			strAllSql.append("		SELECT ");
			strAllSql.append("			COUNT(*) as max_row ");
			strAllSql.append("		FROM  ");
			strAllSql.append("		( ");
			strAllSql.append("			SELECT  ");
			strAllSql.append("				(CASE ");
			strAllSql.append("					WHEN ROW_NUMBER() OVER ( ");
			strAllSql.append("					ORDER BY ");
			// MOD 2013/8/7 okano�yQP@30151�zNo.12 start
//				strAllSql.append("						TBL2.dt_kizitu ");
//				strAllSql.append("						,TBL2.no_shisaku ");
//				strAllSql.append("						,TBL2.no_eda ");
//				strAllSql.append("						)%" + strListRowMax + " = 0 THEN " + strListRowMax + " ");
//				strAllSql.append("				ELSE ROW_NUMBER() OVER ( ");
//				strAllSql.append("					ORDER BY  ");
//				strAllSql.append("						TBL2.dt_kizitu ");
//				strAllSql.append("						,TBL2.no_shisaku ");
//				strAllSql.append("						,TBL2.no_eda ");
			if(kbn_joken1.equals("0")){
				strAllSql.append("						TBL2.dt_kizitu ");
				strAllSql.append("						,TBL2.no_shisaku ");
				strAllSql.append("						,TBL2.no_eda ");
				strAllSql.append("						)%" + strListRowMax + " = 0 THEN " + strListRowMax + " ");
				strAllSql.append("				ELSE ROW_NUMBER() OVER ( ");
				strAllSql.append("					ORDER BY ");
				strAllSql.append("						TBL2.dt_kizitu ");
				strAllSql.append("						,TBL2.no_shisaku ");
				strAllSql.append("						,TBL2.no_eda ");
			} else {
				strAllSql.append("						case when TBL2.st_eigyo = 4 and TBL2.saiyo_sample < 0 then 4 ");
				strAllSql.append("							when TBL2.st_eigyo = 4 then 1 ");
				strAllSql.append("							when TBL2.st_eigyo = 3 then 2 ");
				strAllSql.append("							when TBL2.st_eigyo = 2 then 3 ");
				strAllSql.append("							else 5 end ");
				strAllSql.append("						,case when TBL2.fg_chusi IS NULL then 0 ");
				strAllSql.append("							else 1 end ");
				strAllSql.append("						,TBL2.dt_shisan desc ");
				strAllSql.append("						,TBL2.no_shisaku asc ");
				strAllSql.append("						,TBL2.no_eda asc ");
				strAllSql.append("						)%" + strListRowMax + " = 0 THEN " + strListRowMax + " ");
				strAllSql.append("				ELSE ROW_NUMBER() OVER ( ");
				strAllSql.append("					ORDER BY ");
				strAllSql.append("						case when TBL2.st_eigyo = 4 and TBL2.saiyo_sample < 0 then 4 ");
				strAllSql.append("							when TBL2.st_eigyo = 4 then 1 ");
				strAllSql.append("							when TBL2.st_eigyo = 3 then 2 ");
				strAllSql.append("							when TBL2.st_eigyo = 2 then 3 ");
				strAllSql.append("							else 5 end ");
				strAllSql.append("						,case when TBL2.fg_chusi IS NULL then 0 ");
				strAllSql.append("							else 1 end ");
				strAllSql.append("						,TBL2.dt_shisan desc ");
				strAllSql.append("						,TBL2.no_shisaku asc ");
				strAllSql.append("						,TBL2.no_eda asc ");
			}
			// MOD 2013/8/7 okano�yQP@30151�zNo.12 end
			strAllSql.append("						)%" + strListRowMax + " ");
			strAllSql.append("				END) AS no_row ");
			strAllSql.append("				,Convert(int ");
			strAllSql.append("					,(ROW_NUMBER() OVER ( ");
			strAllSql.append("						ORDER BY ");
			// MOD 2013/8/7 okano�yQP@30151�zNo.12 start
//				strAllSql.append("							TBL2.dt_kizitu ");
//				strAllSql.append("							,TBL2.no_shisaku ");
//				strAllSql.append("							,TBL2.no_eda ");
			if(kbn_joken1.equals("0")){
				strAllSql.append("							TBL2.dt_kizitu ");
				strAllSql.append("							,TBL2.no_shisaku ");
				strAllSql.append("							,TBL2.no_eda ");
			} else {
				strAllSql.append("							case when TBL2.st_eigyo = 4 and TBL2.saiyo_sample < 0 then 4 ");
				strAllSql.append("								when TBL2.st_eigyo = 4 then 1 ");
				strAllSql.append("								when TBL2.st_eigyo = 3 then 2 ");
				strAllSql.append("								when TBL2.st_eigyo = 2 then 3 ");
				strAllSql.append("								else 5 end ");
				strAllSql.append("							,case when TBL2.fg_chusi IS NULL then 0 ");
				strAllSql.append("								else 1 end ");
				strAllSql.append("							,TBL2.dt_shisan desc ");
				strAllSql.append("							,TBL2.no_shisaku asc ");
				strAllSql.append("							,TBL2.no_eda asc ");
			}
			// MOD 2013/8/7 okano�yQP@30151�zNo.12 end
			strAllSql.append("						)-1)/" + strListRowMax + " + 1) AS PageNO ");
			strAllSql.append("				,TBL2.dt_kizitu ");
			strAllSql.append("				,TBL2.su_irai ");
			strAllSql.append("				,TBL2.no_shisaku ");
			strAllSql.append("				,TBL2.no_eda ");
			strAllSql.append("				,TBL2.nm_shurui ");
			strAllSql.append("				,TBL2.nm_hin ");
			strAllSql.append("				,TBL2.st_kenkyu ");
			strAllSql.append("				,TBL2.st_seisan ");
			strAllSql.append("				,TBL2.st_gensizai ");
			strAllSql.append("				,TBL2.st_kojo ");
			strAllSql.append("				,TBL2.st_eigyo ");
			strAllSql.append("				,TBL2.saiyo_sample ");
			strAllSql.append("				,TBL2.nm_sample ");
			strAllSql.append("				,TBL2.nm_team ");
			strAllSql.append("				,TBL2.nm_liuser ");
			strAllSql.append("				,TBL2.nm_kaisha ");
			strAllSql.append("				,TBL2.nm_busho ");
			strAllSql.append("				,TBL2.memo_eigyo ");
			//  �yQP@10713�z2011.10.28 ADD start hisahori
			strAllSql.append("				,TBL2.cd_nisugata ");
			strAllSql.append("				,TBL2.nm_ondo ");
			strAllSql.append("				,TBL2.mn_tantoeigyo ");
			strAllSql.append("				,TBL2.mn_tantosya ");
			// ADD 2013/8/7 okano�yQP@30151�zNo.12 start
			strAllSql.append("				,TBL2.dt_shisan ");
			strAllSql.append("				,TBL2.fg_chusi ");
			// ADD 2013/8/7 okano�yQP@30151�zNo.12 end
			//  �yQP@10713�z2011.10.28 ADD end
			strAllSql.append("			FROM ");
			strAllSql.append("			( ");
			strAllSql.append("				SELECT ");
			strAllSql.append("					CASE M2.st_eigyo ");
			strAllSql.append("						WHEN 3 THEN '�m�F����' ");
			strAllSql.append("						WHEN 4 THEN '�̗p����' ");
			strAllSql.append("						ELSE ISNULL(CONVERT(VARCHAR,T1.dt_kizitu,111),'����') ");
			strAllSql.append("					END AS dt_kizitu ");
			strAllSql.append("					, T1.su_irai ");
			strAllSql.append("					, RIGHT('0000000000' + CONVERT(varchar ");
			strAllSql.append("							,T1.cd_shain) ");
			strAllSql.append("						,10) + '-' + RIGHT('00' + CONVERT(varchar ");
			strAllSql.append("							,T1.nen) ");
			strAllSql.append("						,2) + '-' + RIGHT('000' + CONVERT(varchar ");
			strAllSql.append("							,T1.no_oi) ");
			strAllSql.append("						,3) as no_shisaku ");
			strAllSql.append("					, T1.no_eda ");
			strAllSql.append("					, M1.nm_��iteral AS nm_shurui ");
			strAllSql.append("					, T3.nm_hin ");
			strAllSql.append("					, M2.st_kenkyu ");
			strAllSql.append("					, M2.st_seisan ");
			strAllSql.append("					, M2.st_gensizai ");
			strAllSql.append("					, M2.st_kojo ");
			strAllSql.append("					, M2.st_eigyo ");
			strAllSql.append("					, T1.saiyo_sample ");
			strAllSql.append("					, CASE T1.saiyo_sample ");
			strAllSql.append("					WHEN -1 THEN '�̗p�Ȃ�' ");
			strAllSql.append("					ELSE T2.nm_sample ");
			strAllSql.append("					END AS nm_sample ");
			strAllSql.append("					, M3.nm_team ");
			strAllSql.append("					, M4.nm_��iteral AS nm_liuser ");
			strAllSql.append("					, M5.nm_kaisha ");
			strAllSql.append("					, M5.nm_busho ");
			strAllSql.append("					, T4.memo_eigyo ");
			//  �yQP@10713�z2011.10.28 ADD start hisahori
			//2011/12/21 TT H.SHIMA MOD Start	�׎p�擾�e�[�u�������삩�猴�����Z��
//			strAllSql.append("					, T3.cd_nisugata ");
			strAllSql.append("			    	, T1.cd_nisugata ");
			//2011/12/21 TT H.SHIMA MOD End
			strAllSql.append("					, M111.nm_literal AS nm_ondo ");
			strAllSql.append("					, M113.nm_user AS mn_tantoeigyo ");
			strAllSql.append("					, M114.nm_user AS mn_tantosya ");
			// ADD 2013/8/7 okano�yQP@30151�zNo.12 start
			strAllSql.append("					, T5.dt_shisan ");
			strAllSql.append("					, T5.fg_chusi ");
			// ADD 2013/8/7 okano�yQP@30151�zNo.12 end
			//  �yQP@10713�z2011.10.28 ADD end
			strAllSql.append("					 ");
			strAllSql.append("				FROM tr_shisan_shisakuhin AS T1 ");
			strAllSql.append("					LEFT JOIN ma_literal AS M1 ");
			strAllSql.append("					ON 'shurui_eda' = M1.cd_category ");
			strAllSql.append("					AND T1.shurui_eda = M1.cd_��iteral ");
			strAllSql.append("					LEFT JOIN tr_shisan_status AS M2 ");
			strAllSql.append("					ON T1.cd_shain = M2.cd_shain ");
			strAllSql.append("					AND T1.nen = M2.nen ");
			strAllSql.append("					AND T1.no_oi = M2.no_oi ");
			strAllSql.append("					AND T1.no_eda = M2.no_eda ");
			strAllSql.append("					LEFT JOIN tr_shisaku AS T2 ");
			strAllSql.append("					ON T1.cd_shain = T2.cd_shain ");
			strAllSql.append("					AND T1.nen = T2.nen ");
			strAllSql.append("					AND T1.no_oi = T2.no_oi ");
			strAllSql.append("					AND T1.saiyo_sample = T2.seq_shisaku ");
			strAllSql.append("					LEFT JOIN tr_shisakuhin AS T3 ");
			strAllSql.append("					ON T1.cd_shain = T3.cd_shain ");
			strAllSql.append("					AND T1.nen = T3.nen ");
			strAllSql.append("					AND T1.no_oi = T3.no_oi ");
			strAllSql.append("					LEFT JOIN ma_team AS M3 ");
			strAllSql.append("					ON T3.cd_group = M3.cd_group ");
			strAllSql.append("					AND T3.cd_team = M3.cd_team ");
			strAllSql.append("					LEFT JOIN ma_literal AS M4 ");
			strAllSql.append("					ON 'K_yuza' = M4.cd_category ");
			strAllSql.append("					AND T3.cd_user = M4.cd_��iteral ");
			strAllSql.append("					LEFT JOIN ma_busho AS M5 ");
			strAllSql.append("					ON T1.cd_kaisha = M5.cd_kaisha ");
			strAllSql.append("					AND T1.cd_kojo = M5.cd_busho ");
			strAllSql.append("					LEFT JOIN tr_shisan_memo_eigyo AS T4 ");
			strAllSql.append("					ON T1.cd_shain = T4.cd_shain ");
			strAllSql.append("					AND T1.nen = T4.nen ");
			strAllSql.append("					AND T1.no_oi = T4.no_oi ");
			strAllSql.append("					LEFT JOIN ma_user M6 ");
			strAllSql.append("					ON T3.id_toroku = M6.id_user ");
			//  �yQP@10713�z2011.10.28 ADD start hisahori
			strAllSql.append("					LEFT JOIN ma_literal AS M111 ");
			strAllSql.append("					ON 'K_toriatukaiondo' = M111.cd_category AND T3.cd_ondo = M111.cd_literal ");
			strAllSql.append("					LEFT JOIN ma_user M113 ");
			strAllSql.append("					ON T3.cd_eigyo = M113.id_user ");
			strAllSql.append("					LEFT JOIN ma_user M114 ");
			strAllSql.append("					ON T1.cd_shain = M114.id_user ");
			// ADD 2013/8/7 okano�yQP@30151�zNo.12 start
			strAllSql.append("					left join tr_shisan_shisaku AS T5 ");
			strAllSql.append("					on T1.cd_shain = T5.cd_shain ");
			strAllSql.append("					and T1.nen = T5.nen ");
			strAllSql.append("					and T1.no_oi = T5.no_oi ");
			strAllSql.append("					and T1.no_eda = T5.no_eda ");
			strAllSql.append("					and T5.seq_shisaku = ( ");
			strAllSql.append("					select top 1 seq_shisaku from tr_shisan_shisaku ");
			strAllSql.append("					where cd_shain = T1.cd_shain ");
			strAllSql.append("					and nen = T1.nen ");
			strAllSql.append("					and no_oi = T1.no_oi ");
			strAllSql.append("					and no_eda = T1.no_eda ");
			strAllSql.append("					order by ");
			strAllSql.append("					case when fg_chusi IS NULL then 0 ");
			strAllSql.append("						else 1 end ");
			strAllSql.append("					,dt_shisan desc ");
			strAllSql.append("					,seq_shisaku ");
			strAllSql.append("					) ");
			// ADD 2013/8/7 okano�yQP@30151�zNo.12 end
			//  �yQP@10713�z2011.10.28 ADD end
			strAllSql.append(strWhere);
			strAllSql.append("			) AS TBL2 ");
			strAllSql.append("		) AS CT ");
			strAllSql.append("	) AS cnttbl ");
			strAllSql.append("	WHERE tbl.PageNO = " + no_page);
			// MOD 2013/8/7 okano�yQP@30151�zNo.12 start
//				strAllSql.append("	ORDER BY dt_kizitu ");
//				strAllSql.append("	,tbl.no_shisaku ");
//				strAllSql.append("	,tbl.no_eda ");
			if(kbn_joken1.equals("0")){
				strAllSql.append("	ORDER BY dt_kizitu ");
				strAllSql.append("	,tbl.no_shisaku ");
				strAllSql.append("	,tbl.no_eda ");
			}else{
				strAllSql.append("	ORDER BY ");
				strAllSql.append("	case when st_eigyo = 4 and saiyo_sample < 0 then 4 ");
				strAllSql.append("		when st_eigyo = 4 then 1 ");
				strAllSql.append("		when st_eigyo = 3 then 2 ");
				strAllSql.append("		when st_eigyo = 2 then 3 ");
				strAllSql.append("		else 5 end ");
				strAllSql.append("	,case when tbl.fg_chusi IS NULL then 0 ");
				strAllSql.append("		else 1 end ");
				strAllSql.append("	,tbl.dt_shisan desc ");
				strAllSql.append("	,tbl.no_shisaku asc ");
				strAllSql.append("	,tbl.no_eda asc ");
			}
			// MOD 2013/8/7 okano�yQP@30151�zNo.12 end

			strSql = strAllSql;
		} catch (Exception e) {

			em.ThrowException(e, "�������Z�f�[�^���������Ɏ��s���܂����B");

		} finally {
			//�ϐ��̍폜
			strAllSql = null;
		}

		return strSql;
	}

	/**
	 * ���Z�˗������No�f�[�^�擾SQL�쐬
	 *  : ���Z�˗������No�f�[�^���擾����SQL���쐬�B
	 * @param strSql : ��������SQL
	 * @param arrCondition�F������������
	 * @return strSql�F��������SQL
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private StringBuffer createGenkaShisanSampleSQL(
			List<?> lstGenkaShisan
			)
		throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		StringBuffer strSql = new StringBuffer();
		StringBuffer strSqlWhere = new StringBuffer();

		try {
			//IN��@��������
			for (int i = 0; i < lstGenkaShisan.size(); i++) {
				//�������ʎ擾�i����No�j
				Object[] items = (Object[]) lstGenkaShisan.get(i);
				String no_shisaku = toString(items[3]);

				//����
				if( i == 0){
					strSqlWhere.append( " '" + no_shisaku + "'" );
				}
				//����ȍ~
				else{
					strSqlWhere.append( " ,'" + no_shisaku + "'" );
				}

			}

			//SQL����
			strSql.append(" SELECT ");
			strSql.append(" 	TT.no_shisaku ");
			strSql.append(" 	,TT.no_eda ");
			strSql.append(" 	,TT.nm_sample ");
			strSql.append(" FROM  ");
			strSql.append(" 	( ");
			strSql.append(" 	SELECT ");
			strSql.append(" 		RIGHT('0000000000' + CONVERT(varchar,T1.cd_shain),10) ");
			strSql.append(" 			+ '-' + RIGHT('00' + CONVERT(varchar,T1.nen),2) ");
			strSql.append(" 			+ '-' + RIGHT('000' + CONVERT(varchar,T1.no_oi),3) AS no_shisaku ");
			strSql.append(" 		, T1.no_eda ");
			strSql.append(" 		, nm_sample ");
			strSql.append(" 		, sort_shisaku ");
			strSql.append(" 	FROM ");
			strSql.append(" 		tr_shisan_shisaku AS T1 ");
			strSql.append(" 		INNER JOIN tr_shisaku AS T2 ");
			strSql.append(" 				ON T1.cd_shain = T2.cd_shain ");
			strSql.append(" 			   AND T1.nen = T2.nen ");
			strSql.append(" 			   AND T1.no_oi = T2.no_oi ");
			strSql.append(" 			   AND T1.seq_shisaku = T2.seq_shisaku ");
			strSql.append(" 	WHERE  ");
			strSql.append(" 		T1.fg_chusi IS NULL and T1.dt_shisan IS NULL");
			strSql.append(" 	) AS TT ");
			strSql.append(" WHERE ");
			strSql.append(" 	TT.no_shisaku IN ( ");
			strSql.append(strSqlWhere);
			strSql.append(" 	) ");
			strSql.append(" ORDER BY sort_shisaku ");

		} catch (Exception e) {

			em.ThrowException(e, "�������Z�f�[�^���������Ɏ��s���܂����B");

		} finally {
			strSqlWhere = null;
		}

		return strSql;
	}

	/**
	 * ����f�[�^�p�����[�^�[�i�[
	 *  : ����f�[�^�������X�|���X�f�[�^�֊i�[����B
	 * @param lstGroupCmb : �������ʏ�񃊃X�g
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private void storageShisakuData(
							List<?> lstRecset
							, List<?> lstRecset2
							, RequestResponsTableBean resTable
						)
		throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try {

			//�������Z�f�[�^���[�v�i���Z�@����i�e�[�u�����j
			for (int i = 0; i < lstRecset.size(); i++) {


				//�������ʎ擾
				Object[] items = (Object[]) lstRecset.get(i);
				String no_row = toString(items[0]);
				String hi_kizitu = toString(items[1]);
				String cnt_irai = toString(items[2]);
				String no_shisaku = toString(items[3]);
				String no_eda = toString(items[4]);
				String nm_shisaku = toString(items[5]);
				String nm_shurui = toString(items[6]);
				String st_kenkyu = toString(items[7]);
				String st_seikan = toString(items[8]);
				String st_gentyo = toString(items[9]);
				String st_kojo = toString(items[10]);
				String st_eigyo = toString(items[11]);
				String cd_saiyou = toString(items[12]);
				String no_saiyou = toString(items[13]);
				String nm_team = toString(items[14]);
				String nm_liuser = toString(items[15]);
				String nm_kaisha_seizo = toString(items[16]);
				String nm_kaisha_busho = toString(items[17]);
				String memo_eigyo = toString(items[18]);
				//  �yQP@10713�z2011.10.28 ADD start hisahori
				String cd_nisugata = toString(items[19]);
				String nm_ondo = toString(items[20]);
				String nm_tantoeigyo = toString(items[21]);
				String nm_tantosha = toString(items[22]);
				//  �yQP@10713�z2011.10.28 ADD end
				String list_max_row = toString(items[23]);
				String max_row = toString(items[24]);

				//���Z�˗��T���v���f�[�^���[�v�i���Z�@����e�[�u�����j
				String no_iraisample = "";
				for (int j = 0; j < lstRecset2.size(); j++) {
					//�������ʎ擾
					Object[] items2 = (Object[]) lstRecset2.get(j);
					String no_shisaku_irai = toString(items2[0]);
					String no_eda_irai = toString(items2[1]);
					String nm_sample_irai = toString(items2[2]);

					//����No�A�}�Ԃ��������ꍇ�ɻ����NO�ǉ�
					if( no_shisaku.equals( no_shisaku_irai ) && no_eda.equals( no_eda_irai ) ){
						if( no_iraisample.equals("") ){
							no_iraisample = nm_sample_irai;
						}
						else{
							no_iraisample = no_iraisample + "," + nm_sample_irai;
						}
					}
				}

				//�������ʂ̊i�[
				resTable.addFieldVale(i, "flg_return", "true");
				resTable.addFieldVale(i, "msg_error", "");
				resTable.addFieldVale(i, "no_errmsg", "");
				resTable.addFieldVale(i, "nm_class", "");
				resTable.addFieldVale(i, "cd_error", "");
				resTable.addFieldVale(i, "msg_system", "");

				//���X�|���X����
				resTable.addFieldVale(i, "no_row", no_row);
				resTable.addFieldVale(i, "hi_kizitu", hi_kizitu);
				resTable.addFieldVale(i, "cnt_irai", cnt_irai);
				resTable.addFieldVale(i, "no_shisaku", no_shisaku);
				resTable.addFieldVale(i, "no_eda", no_eda);
				resTable.addFieldVale(i, "nm_shisaku", nm_shisaku);
				resTable.addFieldVale(i, "nm_shurui", nm_shurui);
				resTable.addFieldVale(i, "no_iraisample", no_iraisample);
				resTable.addFieldVale(i, "st_kenkyu", st_kenkyu);
				resTable.addFieldVale(i, "st_seikan", st_seikan);
				resTable.addFieldVale(i, "st_gentyo", st_gentyo);
				resTable.addFieldVale(i, "st_kojo", st_kojo);
				resTable.addFieldVale(i, "st_eigyo", st_eigyo);
				resTable.addFieldVale(i, "cd_saiyou", cd_saiyou);
				resTable.addFieldVale(i, "no_saiyou", no_saiyou);
				resTable.addFieldVale(i, "nm_team", nm_team);
				resTable.addFieldVale(i, "nm_liuser", nm_liuser);
				resTable.addFieldVale(i, "nm_kaisha_seizo", nm_kaisha_seizo);
				resTable.addFieldVale(i, "nm_kaisha_busho", nm_kaisha_busho);
				resTable.addFieldVale(i, "memo_eigyo", memo_eigyo);
				//  �yQP@10713�z2011.10.28 ADD start hisahori
				resTable.addFieldVale(i, "cd_nisugata", cd_nisugata);
				resTable.addFieldVale(i, "nm_ondo", nm_ondo);
				resTable.addFieldVale(i, "nm_tantoeigyo", nm_tantoeigyo);
				resTable.addFieldVale(i, "nm_tantosha", nm_tantosha);
				//  �yQP@10713�z2011.10.28 ADD end
				resTable.addFieldVale(i, "list_max_row", list_max_row);
				resTable.addFieldVale(i, "max_row", max_row);
				resTable.addFieldVale(i, "roop_cnt", Integer.toString(lstRecset.size()));

			}

		} catch (Exception e) {
			this.em.ThrowException(e, "�������Z�f�[�^���������Ɏ��s���܂����B");

		} finally {

		}

	}
}
