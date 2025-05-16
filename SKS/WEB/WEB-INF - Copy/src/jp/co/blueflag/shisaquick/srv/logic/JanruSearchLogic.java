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
 * �R���{�p�F�W��������񌟍�DB����
 *  : �R���{�p�F�W������������������B
 *  �@�\ID�FSA070�@
 *  
 * @author furuta
 * @since  2009/03/29
 */
public class JanruSearchLogic extends LogicBase{
	
	/**
	 * �R���{�p�F�W��������񌟍�DB�����p�R���X�g���N�^ 
	 * : �C���X�^���X����
	 */
	public JanruSearchLogic() {
		//���N���X�̃R���X�g���N�^
		super();

	}

	/**
	 * �R���{�p�F�W���������擾SQL�쐬
	 *  : �W�������R���{�{�b�N�X�����擾����SQL���쐬�B
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
			storageJanruCmb(lstRecset, resKind.getTableItem(0));
			
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
	 * �W�������擾SQL�쐬
	 *  : �W���������擾����SQL���쐬�B
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
			String categoryCd = null;
			String userId = null;
			String kinoId = null;
			String gamenId = null;
			String dataId = null;

			//�J�e�S���R�[�h�̎擾
			categoryCd = ((RequestResponsRowBean) ((RequestResponsTableBean) reqData.GetItem(0)).GetItem(0)).GetItemValue("cd_category");
			//���[�UID�̎擾
			userId = ((RequestResponsRowBean) ((RequestResponsTableBean) reqData.GetItem(0)).GetItem(0)).GetItemValue("id_user");
			//���ID�̎擾
			gamenId = ((RequestResponsRowBean) ((RequestResponsTableBean) reqData.GetItem(0)).GetItem(0)).GetItemValue("id_gamen");

			//�����f�[�^ID�擾
			for (int i = 0; i < userInfoData.getId_gamen().size(); i++) {
				if (userInfoData.getId_gamen().get(i).toString().equals(gamenId)){
					
					//�@�\ID��ݒ�
					kinoId = userInfoData.getId_kino().get(i).toString();
					
					//�f�[�^ID��ݒ�
					dataId = userInfoData.getId_data().get(i).toString();
				}
			}
			
			//SQL���̍쐬
			strSql.append("SELECT M302.cd_literal, M302.nm_literal, ISNULL(M302.cd_group, 0) ");
			strSql.append(" FROM ma_literal M302");

			//����f�[�^�ꗗ���
			if (gamenId.equals("10")) {
				
				//�@�\ID�F�{��
				if(kinoId.equals("10")){
					if(dataId.equals("1") || dataId.equals("2") || dataId.equals("3")) {	//����O���[�v���`
						strSql.append(" ,ma_user M101");
						strSql.append(" WHERE M302.cd_category = '");
						strSql.append(categoryCd);
						strSql.append("' ");
						strSql.append(" AND (M302.cd_group IS NULL");
						strSql.append(" OR M302.cd_group = M101.cd_group)");
						strSql.append(" AND M101.id_user = ");
						strSql.append(userInfoData.getId_user());
					} else if (dataId.equals("4") || dataId.equals("9")) {	//���H�ꕪ or �S��
						strSql.append(" WHERE M302.cd_category = '");
						strSql.append(categoryCd);
						strSql.append("' ");
					}
				}
				//�@�\ID�F�������Z
				else if(kinoId.equals("60")){
					
					//1�����擾���Ȃ�
					strSql.append(" WHERE 1 = 0");
				}
				
				
			}
			strSql.append(" ORDER BY M302.no_sort");
			
		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
		return strSql;
	}

	/**
	 * �R���{�p�F�W�������p�����[�^�[�i�[
	 *  : �W�������R���{�{�b�N�X�������X�|���X�f�[�^�֊i�[����B
	 * @param lstJanruCmb : �������ʏ�񃊃X�g
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private void storageJanruCmb(List<?> lstJanruCmb, RequestResponsTableBean resTable) 
		throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try {
			
			for (int i = 0; i < lstJanruCmb.size(); i++) {

				//�������ʂ̊i�[
				resTable.addFieldVale(i, "flg_return", "true");
				resTable.addFieldVale(i, "msg_error", "");
				resTable.addFieldVale(i, "no_errmsg", "");
				resTable.addFieldVale(i, "nm_class", "");
				resTable.addFieldVale(i, "cd_error", "");
				resTable.addFieldVale(i, "msg_system", "");
				
				Object[] items = (Object[]) lstJanruCmb.get(i);

				//���ʂ����X�|���X�f�[�^�Ɋi�[
				resTable.addFieldVale(i, "cd_literal", items[0].toString());
				resTable.addFieldVale(i, "nm_literal", items[1].toString());
				resTable.addFieldVale(i, "cd_group"  , items[2].toString());

			}

			if (lstJanruCmb.size() == 0) {

				//�������ʂ̊i�[
				resTable.addFieldVale(0, "flg_return", "true");
				resTable.addFieldVale(0, "msg_error", "");
				resTable.addFieldVale(0, "no_errmsg", "");
				resTable.addFieldVale(0, "nm_class", "");
				resTable.addFieldVale(0, "cd_error", "");
				resTable.addFieldVale(0, "msg_system", "");

				//���ʂ����X�|���X�f�[�^�Ɋi�[
				resTable.addFieldVale(0, "cd_literal", "");
				resTable.addFieldVale(0, "nm_literal", "");
				resTable.addFieldVale(0, "cd_group"  , "");

			}
			
		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}

	}
	
}
