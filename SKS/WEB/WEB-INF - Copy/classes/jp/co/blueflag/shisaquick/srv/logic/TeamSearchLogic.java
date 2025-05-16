package jp.co.blueflag.shisaquick.srv.logic;

import jp.co.blueflag.shisaquick.srv.base.LogicBase;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsKindBean;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsRowBean;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsTableBean;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;

import java.util.List;

/**
 * 
 * �R���{�p�F�`�[����񌟍�DB����
 *  : �R���{�p�F�`�[��������������B
 *  �@�\ID�FSA080�@
 *  
 * @author furuta
 * @since  2009/03/29
 */
public class TeamSearchLogic extends LogicBase{
	
	/**
	 * �R���{�p�F�`�[����񌟍�DB���� 
	 * : �C���X�^���X����
	 */
	public TeamSearchLogic() {
		//���N���X�̃R���X�g���N�^
		super();

	}

	/**
	 * �R���{�p�F�`�[�����擾SQL�쐬
	 *  : �`�[���R���{�{�b�N�X�����擾����SQL���쐬�B
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
			storageTeamCmb(lstRecset, resKind.getTableItem(0));
			
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
	 * �`�[���擾SQL�쐬
	 *  : �`�[�����擾����SQL���쐬�B
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
			String userId = null;
			String gamenId = null;
			String ShoriKbn = null;
			String dataId = null;

			//�O���[�v�R�[�h�̎擾
			groupCd = ((RequestResponsRowBean) ((RequestResponsTableBean) reqData.GetItem(0)).GetItem(0)).GetItemValue("cd_group");
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
			}
			
			//SQL���̍쐬
			strSql.append("SELECT ");
			strSql.append("  cd_team");
			strSql.append(" ,nm_team");
			strSql.append(" FROM ma_team");
			
			//�S���҃}�X�^�����e�ȊO�̏ꍇ
			if (!gamenId.equals("90")) {
				strSql.append(" WHERE cd_group = ");
				strSql.append(groupCd);

				//����f�[�^�ꗗ���
				if (gamenId.equals("10")) {
					if(dataId.equals("1") || dataId.equals("3")) {	//����O���[�v���i�{�l+���ذ�ް�ȏ�j or ����O���[�v���S����Њ��i�{�l+���ذ�ް�ȏ�j
						strSql.append(" AND cd_team = ");
						strSql.append(userInfoData.getCd_team());
					} else if (dataId.equals("2") || dataId.equals("4") || dataId.equals("9")) {	//����O���[�v���S����� or ���H�ꕪ or �S��
						//�����Ȃ�
					}
	
				//���e�����}�X�^�����e���
				} else if (gamenId.equals("60") || gamenId.equals("65")) {
					if (dataId == null) {
						//�����f�[�^ID�擾
						for (int i = 0; i < userInfoData.getId_gamen().size(); i++) {
							if (userInfoData.getId_gamen().get(i).toString().equals("65")){
								//�f�[�^ID��ݒ�
								dataId = userInfoData.getId_data().get(i).toString();
							}
						}			
					}
					if(dataId.equals("1") || dataId.equals("9")) {	//����O���[�v or �S��
						//�����Ȃ�
					}
	
				//�O���[�v�}�X�^�����e���
				} else if (gamenId.equals("70")) {
					if(dataId.equals("9")) {	//�S��
						//�����Ȃ�
					}
				}
			
			//�S���҃}�X�^�����e��ʂ̏ꍇ
			} else  {
				//�Z�b�V�����g�p����ꍇ
				if (ShoriKbn.equals("2")) {
					strSql.append(" WHERE cd_group = (");
					strSql.append(" SELECT cd_group FROM ma_user WHERE id_user = ");
					strSql.append(userId + ")");
				} else {
					strSql.append(" WHERE cd_group = ");
					strSql.append(groupCd);
				}
				
				if(dataId.equals("1")) {    //�����̂�
					strSql.append(" AND cd_team = ");
					strSql.append(userInfoData.getCd_team());

				} else if(dataId.equals("9")) {	//�S��
					//�����Ȃ�
				}
			}
			strSql.append(" ORDER BY cd_team");
			
		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
		return strSql;
	}

	/**
	 * �R���{�p�F�`�[���p�����[�^�[�i�[
	 *  : �`�[���R���{�{�b�N�X�������X�|���X�f�[�^�֊i�[����B
	 * @param lstTeamCmb : �������ʏ�񃊃X�g
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private void storageTeamCmb(List<?> lstTeamCmb, RequestResponsTableBean resTable)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try {
			
			for (int i = 0; i < lstTeamCmb.size(); i++) {

				//�������ʂ̊i�[
				resTable.addFieldVale(i, "flg_return", "true");
				resTable.addFieldVale(i, "msg_error", "");
				resTable.addFieldVale(i, "no_errmsg", "");
				resTable.addFieldVale(i, "nm_class", "");
				resTable.addFieldVale(i, "cd_error", "");
				resTable.addFieldVale(i, "msg_system", "");
				
				Object[] items = (Object[]) lstTeamCmb.get(i);

				//���ʂ����X�|���X�f�[�^�Ɋi�[
				resTable.addFieldVale(i, "cd_team", items[0].toString());
				resTable.addFieldVale(i, "nm_team", items[1].toString());

			}

			if (lstTeamCmb.size() == 0) {

				//�������ʂ̊i�[
				resTable.addFieldVale(0, "flg_return", "true");
				resTable.addFieldVale(0, "msg_error", "");
				resTable.addFieldVale(0, "no_errmsg", "");
				resTable.addFieldVale(0, "nm_class", "");
				resTable.addFieldVale(0, "cd_error", "");
				resTable.addFieldVale(0, "msg_system", "");

				//���ʂ����X�|���X�f�[�^�Ɋi�[
				resTable.addFieldVale(0, "cd_team", "");
				resTable.addFieldVale(0, "nm_team", "");

			}

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}

	}
	
}
