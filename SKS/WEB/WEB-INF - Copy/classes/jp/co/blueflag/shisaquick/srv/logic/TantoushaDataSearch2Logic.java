package jp.co.blueflag.shisaquick.srv.logic;

import jp.co.blueflag.shisaquick.srv.base.LogicBase;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsKindBean;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsTableBean;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;

import java.util.List;

/**
 * 
 * �S���҃}�X�^�����e�F�S���ҏ�񌟍�DB����
 *  : �S���҃}�X�^�����e�F�S���ҏ�����������B
 * @author jinbo
 * @since  2009/04/07
 */
public class TantoushaDataSearch2Logic extends LogicBase{
	
	/**
	 * �S���҃}�X�^�����e�i���X�F�S���ҏ�񌟍�DB���� 
	 * : �C���X�^���X����
	 */
	public TantoushaDataSearch2Logic() {
		//���N���X�̃R���X�g���N�^
		super();

	}

	/**
	 * �S���҃}�X�^�����e�F�S���ҏ��擾SQL�쐬
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
				//em.ThrowException(ExceptionKind.�x��Exception,"W000401", strSql.toString(), "", "");
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
		
		try {
			String strUserId = null;
			String dataId = null;
			
			//���[�UID�̎擾
			strUserId = reqData.getFieldVale(0, 0, "id_user");

			//�����f�[�^ID�擾
			for (int i = 0; i < userInfoData.getId_gamen().size(); i++) {
				if (userInfoData.getId_gamen().get(i).toString().equals("90")){
					//�S���҃}�X�^�����e�i���X��ʂ̃f�[�^ID��ݒ�
					dataId = userInfoData.getId_data().get(i).toString();
				}
			}
			
			//SQL���̍쐬	
			strSql.append("SELECT");
			strSql.append("  nm_user");
			strSql.append(" ,password");
			strSql.append(" ,cd_kengen");
			strSql.append(" ,cd_kaisha");
			strSql.append(" ,cd_busho");
			strSql.append(" ,cd_group");
			strSql.append(" ,cd_team");
			strSql.append(" ,cd_yakushoku");
			strSql.append(" FROM ma_user");
			strSql.append(" WHERE id_user = ");
			strSql.append(strUserId);
			
			//�������������ݒ�
			//�����̂�
			if(dataId.equals("1")) {
				strSql.append(" AND id_user = ");
				strSql.append(userInfoData.getId_user());

			//�S��
			} else if (dataId.equals("9")) { 
				//�Ȃ�
			}
			
		} catch (Exception e) {
			this.em.ThrowException(e, "�S���҃f�[�^���������Ɏ��s���܂����B");
		} finally {

		}
		return strSql;
	}

	/**
	 * �S���҃}�X�^�����e�F�S���ҏ��p�����[�^�[�i�[
	 *  : �S���ҏ������X�|���X�f�[�^�֊i�[����B
	 * @param lstTantouShaData : �������ʏ�񃊃X�g
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private void storageTantoushaData(List<?> lstTantouShaData, RequestResponsTableBean resTable) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try {

			//�������ʂ̊i�[
			resTable.addFieldVale(0, "flg_return", "true");
			resTable.addFieldVale(0, "msg_error", "");
			resTable.addFieldVale(0, "no_errmsg", "");
			resTable.addFieldVale(0, "nm_class", "");
			resTable.addFieldVale(0, "cd_error", "");
			resTable.addFieldVale(0, "msg_system", "");
			
			if (lstTantouShaData.size() == 0){
				//�Ώۃf�[�^�������ꍇ
				
				//�󃌃R�[�h�𐶐�
				//���ʂ����X�|���X�f�[�^�Ɋi�[
				resTable.addFieldVale(0, "nm_user", "");
				resTable.addFieldVale(0, "password", "");
				resTable.addFieldVale(0, "cd_kengen", "");
				resTable.addFieldVale(0, "cd_kaisha", "");
				resTable.addFieldVale(0, "cd_busho", "");
				resTable.addFieldVale(0, "cd_group", "");
				resTable.addFieldVale(0, "cd_team", "");
				resTable.addFieldVale(0, "cd_yakushoku", "");

			}else{
				//�Ώۃf�[�^���L��ꍇ

				Object[] items = (Object[]) lstTantouShaData.get(0);
				
				//���ʂ����X�|���X�f�[�^�Ɋi�[
				resTable.addFieldVale(0, "nm_user", items[0].toString());
				resTable.addFieldVale(0, "password", items[1].toString());
				resTable.addFieldVale(0, "cd_kengen", items[2].toString());
				resTable.addFieldVale(0, "cd_kaisha", items[3].toString());
				resTable.addFieldVale(0, "cd_busho", items[4].toString());
				resTable.addFieldVale(0, "cd_group", items[5].toString());
				resTable.addFieldVale(0, "cd_team", items[6].toString());
				resTable.addFieldVale(0, "cd_yakushoku", items[7].toString());
				
			}
			
		} catch (Exception e) {
			this.em.ThrowException(e, "�S���҃f�[�^���������Ɏ��s���܂����B");

		} finally {

		}

	}
	
}
