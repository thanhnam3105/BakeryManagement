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
	
	// ADD 2013/11/20 QP@30154 okano start
	String EditMode = "";
	// ADD 2013/11/20 QP@30154 okano end
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

		// ADD 2013/9/25 okano�yQP@30151�zNo.28 start
		List<?> lstRecset = null;
		// ADD 2013/9/25 okano�yQP@30151�zNo.28 end
		
		try {
			String strUserId = null;
			// ADD 2013/11/20 QP@30154 okano start
			String kinoId = null;
			// ADD 2013/11/20 QP@30154 okano end
			String dataId = null;
			
			//���[�UID�̎擾
			strUserId = reqData.getFieldVale(0, 0, "id_user");

			//�����f�[�^ID�擾
			for (int i = 0; i < userInfoData.getId_gamen().size(); i++) {
				if (userInfoData.getId_gamen().get(i).toString().equals("90")){
					//�S���҃}�X�^�����e�i���X��ʂ̃f�[�^ID��ݒ�
					// ADD 2013/11/20 QP@30154 okano start
					kinoId = userInfoData.getId_kino().get(i).toString();
					// ADD 2013/11/20 QP@30154 okano end
					dataId = userInfoData.getId_data().get(i).toString();
				}
			}
			
			// MOD 2013/9/25 okano�yQP@30151�zNo.28 start
			//SQL���̍쐬	
//			strSql.append("SELECT");
//			strSql.append("  nm_user");
//			strSql.append(" ,password");
//			strSql.append(" ,cd_kengen");
//			strSql.append(" ,cd_kaisha");
//			strSql.append(" ,cd_busho");
//			strSql.append(" ,cd_group");
//			strSql.append(" ,cd_team");
//			strSql.append(" ,cd_yakushoku");
//			strSql.append(" FROM ma_user");
//			strSql.append(" WHERE id_user = ");
//			strSql.append(strUserId);
			
			strSql.append("SELECT COUNT(*) FROM ma_user WHERE id_user = ");
			strSql.append(strUserId);
			
			createSearchDB();
			lstRecset = searchDB.dbSearch(strSql.toString());
			
			strSql = new StringBuffer();
			if (Integer.parseInt(lstRecset.get(0).toString()) == 0){
				strSql.append("SELECT");
				strSql.append("  nm_user");
				strSql.append(" ,password");
				strSql.append(" ,cd_kengen");
				strSql.append(" ,cd_kaisha");
				strSql.append(" ,cd_busho");
				strSql.append(" ,cd_group");
				strSql.append(" ,cd_team");
				strSql.append(" ,null");
				strSql.append(" FROM ma_user_new");
				strSql.append(" WHERE id_user = ");
				strSql.append(strUserId);
				// ADD 2013/11/20 QP@30154 okano start
				EditMode = "Cash";
				// ADD 2013/11/20 QP@30154 okano end
			} else {
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
			}
			// MOD 2013/9/25 okano�yQP@30151�zNo.28 end
			//�������������ݒ�
			//�����̂�
			if(dataId.equals("1")) {
				strSql.append(" AND id_user = ");
				strSql.append(userInfoData.getId_user());

			// ADD 2013/11/7 QP@30154 okano start
			//������Ђ̂�
			} else if (dataId.equals("2")) { 
				strSql.append(" AND cd_kaisha = ");
				strSql.append(userInfoData.getCd_kaisha());

			// ADD 2013/11/7 QP@30154 okano end
			//�S��
			} else if (dataId.equals("9")) { 
				//�Ȃ�
				// ADD 2013/11/20 QP@30154 okano start
				if(kinoId.equals("20") && !EditMode.equals("Cash")){
					EditMode = "System";
				}
				// ADD 2013/11/20 QP@30154 okano end
			}
			
		} catch (Exception e) {
			this.em.ThrowException(e, "�S���҃f�[�^���������Ɏ��s���܂����B");
		} finally {
			// ADD 2013/9/25 okano�yQP@30151�zNo.28 start
			//���X�g�̔j��
			removeList(lstRecset);
			if (searchDB != null) {
				//�Z�b�V�����̃N���[�Y
				searchDB.Close();
				searchDB = null;
			}
			// ADD 2013/9/25 okano�yQP@30151�zNo.28 end

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
			// ADD 2013/11/20 QP@30154 okano start
			if(EditMode.equals("Cash")){
				resTable.addFieldVale(0, "md_edit", "cash");
			} else if(EditMode.equals("System")) {
				resTable.addFieldVale(0, "md_edit", "system");
			} else {
				resTable.addFieldVale(0, "md_edit", "ippan");
			}
			// ADD 2013/11/20 QP@30154 okano end
			
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
				// MOD 2013/9/25 okano�yQP@30151�zNo.28 start
//				resTable.addFieldVale(0, "nm_user", items[0].toString());
//				resTable.addFieldVale(0, "password", items[1].toString());
//				resTable.addFieldVale(0, "cd_kengen", items[2].toString());
//				resTable.addFieldVale(0, "cd_kaisha", items[3].toString());
//				resTable.addFieldVale(0, "cd_busho", items[4].toString());
				
				resTable.addFieldVale(0, "nm_user", toString(items[0]));
				resTable.addFieldVale(0, "password", toString(items[1]));
				resTable.addFieldVale(0, "cd_kengen", toString(items[2]));
				resTable.addFieldVale(0, "cd_kaisha", toString(items[3]));
				resTable.addFieldVale(0, "cd_busho", toString(items[4]));
				// MOD 2013/9/25 okano�yQP@30151�zNo.28 start
				
				//�yQP@00342�z �O���[�v�A�`�[���A��E��NULL�̏ꍇ������̂ŏC��
				//resTable.addFieldVale(0, "cd_group", items[5].toString());
				//resTable.addFieldVale(0, "cd_team", items[6].toString());
				//resTable.addFieldVale(0, "cd_yakushoku", items[7].toString());
				resTable.addFieldVale(0, "cd_group", toString(items[5]));
				resTable.addFieldVale(0, "cd_team", toString(items[6]));
				resTable.addFieldVale(0, "cd_yakushoku", toString(items[7]));
				//�����܂�
				
			}
			
		} catch (Exception e) {
			this.em.ThrowException(e, "�S���҃f�[�^���������Ɏ��s���܂����B");

		} finally {

		}

	}
	
}
