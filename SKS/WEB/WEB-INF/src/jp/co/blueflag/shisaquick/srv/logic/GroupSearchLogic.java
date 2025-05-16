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
 * �R���{�p�F�O���[�v��񌟍�DB����
 *  : �R���{�p�F�O���[�v������������B
 *  �@�\ID�FSA050�@
 *
 * @author furuta
 * @since  2009/03/29
 */
public class GroupSearchLogic extends LogicBase{

	/**
	 * �R���{�p�F�O���[�v��񌟍�DB����
	 * : �C���X�^���X����
	 */
	public GroupSearchLogic() {
		//���N���X�̃R���X�g���N�^
		super();

	}

	/**
	 * �R���{�p�F�O���[�v���擾SQL�쐬
	 *  : �O���[�v�R���{�{�b�N�X�����擾����SQL���쐬�B
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

		//���X�|���X�f�[�^�i�@�\�j
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
			String strTableNm = reqData.getTableID(0);
			resKind.addTableItem(strTableNm);

			//���X�|���X�f�[�^�̌`��
			storageGroupCmb(lstRecset, resKind.getTableItem(0));

		} catch (Exception e) {
			this.em.ThrowException(e, "�O���[�v�R���{�{�b�N�X���̎擾�Ɏ��s���܂����B");

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
	 * �O���[�v�擾SQL�쐬
	 *  : �O���[�v���擾����SQL���쐬�B
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
			String userId = null;
			String gamenId = null;
			String kinoId = null;
			String dataId = null;
            // ADD 2013/10/24 QP@30154 okano start
			String kaishaCd = null;
            // ADD 2013/10/24 QP@30154 okano end

			//���[�UID�̎擾
			userId = reqData.getFieldVale(0, 0, "id_user");
			//���ID�̎擾
			gamenId = reqData.getFieldVale(0, 0, "id_gamen");
            // ADD 2013/10/24 QP@30154 okano start
			//���ID�̎擾
			kaishaCd = reqData.getFieldVale(0, 0, "cd_kaisha");
            // ADD 2013/10/24 QP@30154 okano end

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
			strSql.append("SELECT ");
			strSql.append("  cd_group");
			strSql.append(" ,nm_group");
			// ADD 2013/10/24 QP@30154 okano start
			strSql.append(" ,cd_kaisha");
			// ADD 2013/10/24 QP@30154 okano end
			strSql.append(" FROM ma_group");

			//����f�[�^�ꗗ���
			if (gamenId.equals("10")) {


				//�@�\ID�F�{��
				if(kinoId.equals("10")){
					if(dataId.equals("1") || dataId.equals("2") || dataId.equals("3")) {	//����O���[�v���`
						strSql.append(" WHERE cd_group = ");
						strSql.append(userInfoData.getCd_group());
					} else if (dataId.equals("4") || dataId.equals("9")) {	//���H�ꕪ or �S��
						//�����Ȃ�
					}
				}
				//�@�\ID�F�������Z
				else if(kinoId.equals("60")){

					//1�����擾���Ȃ�
					strSql.append(" WHERE 1 = 0 ");
				}



			//���e�����}�X�^�����e���
			} else if (gamenId.equals("60") || gamenId.equals("65")) {
				if (dataId == null) {
					//�����f�[�^ID�擾
					for (int i = 0; i < userInfoData.getId_gamen().size(); i++) {
						if (userInfoData.getId_gamen().get(i).toString().equals("65")){
							// ADD 2013/11/8 QP@30154 okano start
							//�@�\ID��ݒ�
							kinoId = userInfoData.getId_kino().get(i).toString();
							// ADD 2013/11/8 QP@30154 okano end
							//�f�[�^ID��ݒ�
							dataId = userInfoData.getId_data().get(i).toString();
						}
					}
				}
				// ADD 2013/11/8 QP@30154 okano start
				if(kinoId.equals("10") || kinoId.equals("20")){
				// ADD 2013/11/8 QP@30154 okano end
				if(dataId.equals("1")) {	//����O���[�v
					strSql.append(" WHERE cd_group = ");
					strSql.append(userInfoData.getCd_group());
				} else if (dataId.equals("9")) {	//�S��
					//�����Ȃ�
					// ADD 2013/11/8 QP@30154 okano start
					strSql.append(" WHERE cd_kaisha = ");
					strSql.append(userInfoData.getCd_kaisha());
					// ADD 2013/11/8 QP@30154 okano end
				}
				// ADD 2013/11/8 QP@30154 okano start
				} else {	//�V�X�e���Ǘ���
					//�����Ȃ�
				}
				// ADD 2013/11/8 QP@30154 okano end

			//�O���[�v�}�X�^�����e���
			} else if (gamenId.equals("70")) {
				if(dataId.equals("9")) {	//�S��
					//�����Ȃ�
				}

			//�S���҃}�X�^�����e���
			} else if (gamenId.equals("90")) {
				if(dataId.equals("1")) {	//�����̂�
					strSql.append(" WHERE cd_group = ");
					strSql.append(userInfoData.getCd_group());
				// ADD 2013/11/7 QP@30154 okano start
					strSql.append(" AND cd_kaisha = ");
					if(kaishaCd.equals("")){
						strSql.append(" ( SELECT cd_kaisha FROM ma_user WHERE id_user = ");
						strSql.append(userId + ")");
					} else {
						strSql.append(kaishaCd);
					}
				} else if (dataId.equals("2")) {	//������Ђ̂�
					strSql.append(" WHERE cd_kaisha = ");
					if(kaishaCd.equals("")){
						strSql.append(" ( SELECT cd_kaisha FROM ma_user WHERE id_user = ");
						strSql.append(userId + ")");
					} else {
						strSql.append(kaishaCd);
					}
				// ADD 2013/11/7 QP@30154 okano end
				} else if (dataId.equals("9")) {	//�S��
					//�����Ȃ�
					// ADD 2013/11/7 QP@30154 okano start
					strSql.append(" WHERE cd_kaisha = ");
					if(kaishaCd.equals("")){
						// MOD 2013/11/20 okano�yQP@30151�zNo.28 start
//							strSql.append(" ( SELECT cd_kaisha FROM ma_user WHERE id_user = ");
//							strSql.append(userId + ")");
						strSql.append(" (SELECT CASE ");
						strSql.append(" 	WHEN (SELECT COUNT(cd_kaisha) FROM ma_user WHERE id_user = " + userId + " ) = 0");
						strSql.append(" 		THEN (SELECT cd_kaisha FROM ma_user_new WHERE id_user = " + userId + " ) ");
						strSql.append(" 	WHEN (SELECT COUNT(cd_kaisha) FROM ma_user WHERE id_user = " + userId + " ) > 0");
						strSql.append(" 		THEN (SELECT cd_kaisha FROM ma_user WHERE id_user = " + userId + " ) ");
						strSql.append(" 	END ) ");
						// MOD 2013/11/1 okano�yQP@30151�zNo.28 end
					} else {
						strSql.append(kaishaCd);
					}
					// ADD 2013/11/7 QP@30154 okano end
				}
			}

			//�yQP@00342�z�������Z�ꗗ���
			else if (gamenId.equals("180")) {

				//�@�\ID�F�{��
				if(kinoId.equals("80")){
					// DEL 2013/10/22 QP@30154 okano start
//						//����O���[�v���i�{�l�{�`�[�����[�_�[�ȏ�j
//						if(dataId.equals("1")) {
//							strSql.append(" WHERE cd_group = ");
//							strSql.append(userInfoData.getCd_group());
//							//���Y�{���ȊO
//							//strSql.append(" WHERE cd_group <> 99");
//						}
//						//����O���[�v���i�{�l�{�`�[�����[�_�[�ȏ�j�@�ȊO
//						else{
//							//���Y�{���ȊO
					// DEL 2013/10/22 QP@30154 okano end
						strSql.append(" WHERE cd_group <> 99");
					// DEL 2013/10/22 QP@30154 okano start
					//ADD 2015/04/17 TT.Kitazawa�yQP@40812�z�ǉ�-No.3 start
						strSql.append(" AND cd_kaisha = ");
						strSql.append(userInfoData.getCd_kaisha());
					//ADD 2015/04/17 TT.Kitazawa�yQP@40812�z�ǉ�-No.3 end
//						}
					// DEL 2013/10/22 QP@30154 okano end
				}
			}

//2011/05/19�@�yQP@10181_No.11�z�O���[�v�E�`�[���̕\�����ݒ�@TT.NISHIGAWA�@start
			//strSql.append(" ORDER BY cd_group");
			strSql.append(" ORDER BY flg_hyoji");
//2011/05/19�@�yQP@10181_No.11�z�O���[�v�E�`�[���̕\�����ݒ�@TT.NISHIGAWA�@end

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
		return strSql;
	}

	/**
	 * �R���{�p�F�O���[�v�p�����[�^�[�i�[
	 *  : �O���[�v�R���{�{�b�N�X�������X�|���X�f�[�^�֊i�[����B
	 * @param lstGroupCmb : �������ʏ�񃊃X�g
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private void storageGroupCmb(List<?> lstGroupCmb, RequestResponsTableBean resTable)
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

				//���ʂ����X�|���X�f�[�^�Ɋi�[
				resTable.addFieldVale(i, "cd_group", items[0].toString());
				resTable.addFieldVale(i, "nm_group", items[1].toString());
				// ADD 2013/10/24 QP@30154 okano start
				resTable.addFieldVale(i, "cd_kaisha", items[2].toString());
				// ADD 2013/10/24 QP@30154 okano end

			}

			if (lstGroupCmb.size() == 0) {
				//�������ʂ̊i�[
				resTable.addFieldVale(0, "flg_return", "true");
				resTable.addFieldVale(0, "msg_error", "");
				resTable.addFieldVale(0, "no_errmsg", "");
				resTable.addFieldVale(0, "nm_class", "");
				resTable.addFieldVale(0, "cd_error", "");
				resTable.addFieldVale(0, "msg_system", "");
				//���ʂ����X�|���X�f�[�^�Ɋi�[
				resTable.addFieldVale(0, "cd_group", "");
				resTable.addFieldVale(0, "nm_group", "");
				// ADD 2013/10/24 QP@30154 okano start
				resTable.addFieldVale(0, "cd_kaisha", "");
				// ADD 2013/10/24 QP@30154 okano end
			}

		} catch (Exception e) {
			this.em.ThrowException(e, "�O���[�v�R���{�{�b�N�X�̃��X�|���X�f�[�^�����Ɏ��s���܂����B");

		} finally {

		}

	}

}
