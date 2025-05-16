package jp.co.blueflag.shisaquick.srv.logic;

import jp.co.blueflag.shisaquick.srv.base.LogicBase;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsKindBean;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsRowBean;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsTableBean;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ConstManager;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;

import java.util.List;

/**
 * 
 * �R���{�p�F�S���ҏ�񌟍�DB����
 *  : �R���{�p�F�S���ҏ�����������B
 *  �@�\ID�FSA250�@
 *  
 * @author furuta
 * @since  2009/03/29
 */
public class TantoushaSearchLogic extends LogicBase{

	/**
	 * �R���{�p�F�S���ҏ�񌟍�DB�����p�R���X�g���N�^ 
	 * : �C���X�^���X����
	 */
	public TantoushaSearchLogic() {
		//���N���X�̃R���X�g���N�^
		super();

	}

	/**
	 * �R���{�p�F�S���ҏ��擾SQL�쐬
	 *  : �S���҃R���{�{�b�N�X�����擾����SQL���쐬�B
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

		RequestResponsKindBean resKind = null;
		
		try {
			//���X�|���X�f�[�^�i�@�\�j����
			resKind = new RequestResponsKindBean();

			//SQL���̍쐬
			strSql = createSQL(reqData, strSql);
			
			//SQL�����s
			super.createSearchDB();
			lstRecset = searchDB.dbSearch(strSql.toString());
			
			//�@�\ID�̐ݒ�
			String strKinoId = reqData.getID();
			resKind.setID(strKinoId);

			//�e�[�u�����̐ݒ�
			String strTableNm = ((RequestResponsTableBean) reqData.GetItem(0)).getID();
			resKind.addTableItem(strTableNm);

			//���X�|���X�f�[�^�̌`��
			storageTantoushaCmb(lstRecset, resKind.getTableItem(0));
			
		} catch (Exception e) {
			this.em.ThrowException(e, "");
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
	 * �S���Ҏ擾SQL�쐬
	 *  : �S���҂��擾����SQL���쐬�B
	 * @param reqData�F���N�G�X�g�f�[�^
	 * @param strSql�F��������SQL
	 * @return strSql�F��������SQL
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private StringBuffer createSQL(RequestResponsKindBean reqData, StringBuffer strSql) 
		throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		try {
			String groupCd = null;
			String teamCd = null;
			String userId = null;
			String gamenId = null;
			String ShoriKbn = null;
			String dataId = null;

			//�O���[�v�R�[�h�̎擾
			groupCd = ((RequestResponsRowBean) ((RequestResponsTableBean) reqData.GetItem(0)).GetItem(0)).GetItemValue("cd_group");
			//�`�[���R�[�h�̎擾
			teamCd = ((RequestResponsRowBean) ((RequestResponsTableBean) reqData.GetItem(0)).GetItem(0)).GetItemValue("cd_team");
			//�����敪�̎擾
			ShoriKbn = ((RequestResponsRowBean) ((RequestResponsTableBean) reqData.GetItem(0)).GetItem(0)).GetItemValue("kbn_shori");
			//���[�UID�̎擾
			userId = ((RequestResponsRowBean) ((RequestResponsTableBean) reqData.GetItem(0)).GetItem(0)).GetItemValue("id_user");
			//���ID�̎擾
			gamenId = ((RequestResponsRowBean) ((RequestResponsTableBean) reqData.GetItem(0)).GetItem(0)).GetItemValue("id_gamen");

			//�����f�[�^ID�擾
			for (int i = 0; i < userInfoData.getId_gamen().size(); i++) {
				if (userInfoData.getId_gamen().get(i).toString().equals(gamenId)){
					//�f�[�^ID��ݒ�
					dataId = userInfoData.getId_data().get(i).toString();
				}
			}

			//�Z�b�V�����g�p����ꍇ
			if (ShoriKbn.equals("2")) {
				//�O���[�v�R�[�h�ɃZ�b�V���������i�[
				groupCd = userInfoData.getCd_group();
				teamCd = userInfoData.getCd_team();
			}
			
			//SQL���̍쐬
			strSql.append("SELECT id_user, nm_user, cd_kaisha, cd_busho, cd_group, cd_team, cd_yakushoku");
			strSql.append(" FROM ma_user");
			strSql.append(" WHERE cd_group = " + groupCd);
			strSql.append(" AND   cd_team = " + teamCd);
			
			//����f�[�^�ꗗ���
			if (gamenId.equals("10")) {
				if(dataId.equals("1") || dataId.equals("3")) {	//����O���[�v���i�{�l+���ذ�ް�ȏ�j or ����O���[�v���S����Њ��i�{�l+���ذ�ް�ȏ�j
					strSql.append(" AND id_user = ");
					strSql.append(userInfoData.getId_user());
					
					//�Z�b�V�������D��E�R�[�h���`�[�����[�_�[�̏ꍇ�A�ȉ���OR�����Œǉ�
					if (Integer.parseInt(ConstManager.getConstValue(ConstManager.Category.�ݒ�, "TEAM_LEADER_CD"))
							<= Integer.parseInt(userInfoData.getCd_literal())) {
						
						strSql.append(" OR (");
						strSql.append(" cd_group = " + groupCd);
						strSql.append(" AND cd_team = " + teamCd);
						strSql.append(" AND cd_yakushoku <= '");
						strSql.append(userInfoData.getCd_literal());
						strSql.append("')");
					}
				} else if(dataId.equals("4")) {	//���H�ꕪ
					strSql.append(" AND cd_kaisha = ");
					strSql.append(userInfoData.getCd_kaisha());
					strSql.append(" AND cd_busho = ");
					strSql.append(userInfoData.getCd_busho());
				} else if (dataId.equals("2") || dataId.equals("9")) {	//����O���[�v���S�����  or �S��
					//�����Ȃ�
				}
			}
			strSql.append(" ORDER BY id_user");
			
		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
		return strSql;
	}

	/**
	 * �R���{�p�F�S���҃p�����[�^�[�i�[
	 *  : �S���҃R���{�{�b�N�X�������X�|���X�f�[�^�֊i�[����B
	 * @param lstTantoushaCmb : �������ʏ�񃊃X�g
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private void storageTantoushaCmb(List<?> lstTantoushaCmb, RequestResponsTableBean resTable) 
		throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try {
			
			for (int i = 0; i < lstTantoushaCmb.size(); i++) {
				//�Y���f�[�^���L��ꍇ

				//�������ʂ̊i�[
				resTable.addFieldVale(i, "flg_return", "true");
				resTable.addFieldVale(i, "msg_error", "");
				resTable.addFieldVale(i, "no_errmsg", "");
				resTable.addFieldVale(i, "nm_class", "");
				resTable.addFieldVale(i, "cd_error", "");
				resTable.addFieldVale(i, "msg_system", "");
				
				Object[] items = (Object[]) lstTantoushaCmb.get(i);

				//���ʂ����X�|���X�f�[�^�Ɋi�[
				resTable.addFieldVale(i, "id_user", items[0].toString());
				resTable.addFieldVale(i, "nm_user", items[1].toString());
				resTable.addFieldVale(i, "cd_kaisha", items[2].toString());
				resTable.addFieldVale(i, "cd_busho", items[3].toString());
				resTable.addFieldVale(i, "cd_group", items[4].toString());
				resTable.addFieldVale(i, "cd_team", items[5].toString());
				resTable.addFieldVale(i, "cd_yakushoku", items[6].toString());

			}

			if (lstTantoushaCmb.size() == 0) {
				//�������ʂ̊i�[
				
				//�������ʂ̊i�[
				resTable.addFieldVale(0, "flg_return", "true");
				resTable.addFieldVale(0, "msg_error", "");
				resTable.addFieldVale(0, "no_errmsg", "");
				resTable.addFieldVale(0, "nm_class", "");
				resTable.addFieldVale(0, "cd_error", "");
				resTable.addFieldVale(0, "msg_system", "");

				//���ʂ����X�|���X�f�[�^�Ɋi�[
				resTable.addFieldVale(0, "id_user", "");
				resTable.addFieldVale(0, "nm_user", "");
				resTable.addFieldVale(0, "cd_kaisha", "");
				resTable.addFieldVale(0, "cd_busho", "");
				resTable.addFieldVale(0, "cd_group", "");
				resTable.addFieldVale(0, "cd_team", "");
				resTable.addFieldVale(0, "cd_yakushoku", "");

			}

		} catch (Exception e) {
			this.em.ThrowException(e, "");
			
		} finally {
			
		}

	}
	
}
