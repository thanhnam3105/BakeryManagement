package jp.co.blueflag.shisaquick.srv.datacheck;

import jp.co.blueflag.shisaquick.srv.base.DataCheck;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsKindBean;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;
import jp.co.blueflag.shisaquick.srv.common.ExceptionManager.ExceptionKind;

import java.util.List;

/**
 *
 * �S���҃}�X�^�����e�F�S���ҏ��f�[�^�`�F�b�N����
 *  : �S���҃}�X�^�����e�F�S���ҏ��̃f�[�^�`�F�b�N���s���B
 * @author jinbo
 * @since  2009/04/18
 */
public class TantoushaDataSearch2DataCheck extends DataCheck{

	/**
	 * �S���҃}�X�^�����e�F�S���ҏ��f�[�^�`�F�b�N�����p�R���X�g���N�^
	 * : �C���X�^���X����
	 */
	public TantoushaDataSearch2DataCheck() {
		//���N���X�̃R���X�g���N�^
		super();

	}

	/**
	 * �S���҃}�X�^�����e�F�S���ҏ��f�[�^�`�F�b�N
	 *  : �S���ҏ��̃f�[�^�`�F�b�N���s���B
	 * @param reqData : ���N�G�X�g�f�[�^
	 * @param userInfoData : ���[�U�[���
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	public void execDataCheck(
			RequestResponsKindBean reqData
			,UserInfoData _userInfoData
			)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		//���[�U�[���ޔ�
		userInfoData = _userInfoData;

		StringBuffer strSql = new StringBuffer();
		List<?> lstRecset = null;

		try {
			String strUserId = null;
			// ADD 2013/12/18 QP@30154 okano start
			String strKinoId = null;
			// ADD 2013/12/18 QP@30154 okano end
			String strDataId = null;

			//�����f�[�^ID�擾
			for (int i = 0; i < userInfoData.getId_gamen().size(); i++) {
				if (userInfoData.getId_gamen().get(i).toString().equals("90")){
					// ADD 2013/12/18 QP@30154 okano start
					strKinoId = userInfoData.getId_kino().get(i).toString();
					// ADD 2013/12/18 QP@30154 okano end
					//�S���҃}�X�^�����e�i���X��ʂ̃f�[�^ID��ݒ�
					strDataId = userInfoData.getId_data().get(i).toString();
				}
			}

			//�����f�[�^���u�����̂݁v�̏ꍇ
			if (strDataId.equals("1")) {
				//���[�UID�̎擾
				strUserId = reqData.getFieldVale(0, 0, "id_user");

				//SQL���̍쐬
				strSql = TantoushaDataSearch2ExistenceCheckSQL(reqData, strSql);

				//SQL�����s
				super.createSearchDB();
				lstRecset = searchBD.dbSearch(strSql.toString());

				//�f�[�^�����݂��Ȃ��ꍇ
				if (lstRecset.size() == 0){
					em.ThrowException(ExceptionKind.���Exception,"E000303", "���[�UID", "", "");
				}
			}
			// ADD 2013/11/7 QP@30154 okano start
			//�����f�[�^���u������Ђ̂݁v�̏ꍇ
			else if (strDataId.equals("2")) {
				//���[�UID�̎擾
				strUserId = reqData.getFieldVale(0, 0, "id_user");

				//SQL���̍쐬
				strSql = TantoushaDataSearch2ExistenceCheckSQL2(reqData, strSql);

				//SQL�����s
				super.createSearchDB();
				lstRecset = searchBD.dbSearch(strSql.toString());

				//�f�[�^�����݂��Ȃ��ꍇ
				if (lstRecset.size() == 0){
					em.ThrowException(ExceptionKind.���Exception,"E000303", "���[�UID", "", "");
				}
				// ADD 2013/12/18 QP@30154 okano start
				//�ҏW�������u�����A�p�X���[�h�̂݁v�̂Ƃ��A�L���b�V�����X���[�U���������Ȃ�
				if(strKinoId.equals("21") == false && strKinoId.equals("20") == false){
					strSql = new StringBuffer();
					strSql = TantoushaDataSearch2ExistenceCheckSQLNew(reqData, strSql);

					// SQL�����s
					super.createSearchDB();
					lstRecset = searchBD.dbSearch(strSql.toString());
					//�f�[�^�����݂��Ȃ��ꍇ�͂��̂܂�
					if (lstRecset.size() == 0){

					//�f�[�^�����݂���ꍇ�ɃG���[�`�F�b�N
					} else {
						em.ThrowException(ExceptionKind.���Exception,"E000303", "���[�UID", "", "");
					}
				}
				// ADD 2013/12/18 QP@30154 okano end
			}
			// ADD 2013/11/7 QP@30154 okano end
//�yQP@10713�z2011/10/26 TT H.SHIMA ADD START �u�S���҃}�X�^(�c��)�̓o�^���e�͕\�����Ȃ��B�v
			else{
				//���[�UID�̎擾
				strUserId = reqData.getFieldVale(0, 0, "id_user");

				//���[�U����
				strSql.append(" SELECT cd_kengen,flg_eigyo	");
				strSql.append("	FROM ma_user ");
				strSql.append("	JOIN ma_busho ");
				strSql.append(" 	ON ma_user.cd_busho = ma_busho.cd_busho ");
				strSql.append("		AND ma_user.cd_kaisha = ma_busho.cd_kaisha ");
				strSql.append(" WHERE id_user= "+strUserId);

				// SQL�����s
				super.createSearchDB();
				lstRecset = searchBD.dbSearch(strSql.toString());

				//�f�[�^�����݂��Ȃ��ꍇ�͂��̂܂�
				if (lstRecset.size() == 0){
					// ADD 2013/12/18 QP@30154 okano start
					//�ҏW�������u�����A�p�X���[�h�̂݁v�̂Ƃ��A�L���b�V�����X���[�U���������Ȃ�
					if(strKinoId.equals("21") == false && strKinoId.equals("20") == false){
						strSql = new StringBuffer();
						strSql = TantoushaDataSearch2ExistenceCheckSQLNew(reqData, strSql);

						// SQL�����s
						super.createSearchDB();
						lstRecset = searchBD.dbSearch(strSql.toString());
						//�f�[�^�����݂��Ȃ��ꍇ�͂��̂܂�
						if (lstRecset.size() == 0){

						//�f�[�^�����݂���ꍇ�ɃG���[�`�F�b�N
						} else {
							em.ThrowException(ExceptionKind.���Exception,"E000303", "���[�UID", "", "");
						}
					}
					// ADD 2013/12/18 QP@30154 okano end

				}
				//�f�[�^�����݂���ꍇ�ɃG���[�`�F�b�N
				else{
					//�������ʎ擾
					Object[] items = (Object[]) lstRecset.get(0);
					String cd_kengen = toString(items[1],"");

					//�c�Ƃ̓G���[
					if( cd_kengen.equals("1")){
						em.ThrowException(ExceptionKind.���Exception,"E000330","���[�U ID", "", "");
					}
				}
			}
//�yQP@10713�z2011/10/26 TT H.SHIMA ADD END   �u�S���҃}�X�^(�c��)�̓o�^���e�͕\�����Ȃ��B�v

		} catch (Exception e) {
			this.em.ThrowException(e, "�S���ҊǗ��A�f�[�^�`�F�b�N�����Ɏ��s���܂����B");
		} finally {
			//���X�g�̔j��
			removeList(lstRecset);
			if (searchBD != null) {
				//�Z�b�V�����̃N���[�Y
				searchBD.Close();
				searchBD = null;
			}

			//�ϐ��̍폜
			strSql = null;
		}

	}

	/**
	 * ���[�UID���݃`�F�b�NSQL�쐬
	 *  : �S���҂̑��݃`�F�b�N������SQL���쐬�B
	 * @param reqData�F���N�G�X�g�f�[�^
	 * @param strSql�F��������SQL
	 * @return strSql�F��������SQL
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private StringBuffer TantoushaDataSearch2ExistenceCheckSQL(RequestResponsKindBean requestData, StringBuffer strSql)
		throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try {
			String strUserId = null;

			//���[�UID�̎擾
			strUserId = requestData.getFieldVale(0, 0, "id_user");

			//SQL���̍쐬
			strSql.append("SELECT id_user");
			strSql.append(" FROM ma_user");
			strSql.append(" WHERE id_user = ");
			strSql.append(strUserId);
			strSql.append(" AND id_user = ");
			strSql.append(userInfoData.getId_user());

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
		return strSql;
	}
	// ADD 2013/11/7 QP@30154 okano start
	/**
	 * ���[�UID���݃`�F�b�NSQL�쐬
	 *  : �S���҂̑��݃`�F�b�N������SQL���쐬�B
	 * @param reqData�F���N�G�X�g�f�[�^
	 * @param strSql�F��������SQL
	 * @return strSql�F��������SQL
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private StringBuffer TantoushaDataSearch2ExistenceCheckSQL2(RequestResponsKindBean requestData, StringBuffer strSql)
		throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try {
			String strUserId = null;

			//���[�UID�̎擾
			strUserId = requestData.getFieldVale(0, 0, "id_user");

			//SQL���̍쐬
			// MOD 2013/12/18 QP@30154 okano start
//				strSql.append("SELECT id_user");
//				strSql.append(" FROM ma_user");
//				strSql.append(" WHERE id_user = ");
//				strSql.append(strUserId);
//				strSql.append(" AND cd_kaisha = ");
//				strSql.append(userInfoData.getCd_kaisha());
			strSql.append("SELECT id_user ");
			strSql.append(" FROM ( ");
			strSql.append(" SELECT id_user,cd_kaisha,cd_busho ");
			strSql.append(" FROM ma_user ");
			strSql.append(" UNION ");
			strSql.append(" SELECT id_user,cd_kaisha,cd_busho ");
			strSql.append(" FROM ma_user_new ");
			strSql.append(" ) alluser ");
			strSql.append(" JOIN ma_busho ");
			strSql.append(" 	ON alluser.cd_busho = ma_busho.cd_busho ");
			strSql.append(" 	AND alluser.cd_kaisha = ma_busho.cd_kaisha ");
			strSql.append(" WHERE id_user = ");
			strSql.append(strUserId);
			strSql.append(" AND alluser.cd_kaisha = ");
			strSql.append(userInfoData.getCd_kaisha());
			strSql.append(" AND flg_eigyo IS NULL ");
			// MOD 2013/12/18 QP@30154 okano end

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
		return strSql;
	}
	// ADD 2013/11/7 QP@30154 okano end
	// ADD 2013/12/18 QP@30154 okano start
	/**
	 * ���[�UID���݃`�F�b�NSQL�쐬
	 *  : �S���҂̑��݃`�F�b�N������SQL���쐬�B
	 * @param reqData�F���N�G�X�g�f�[�^
	 * @param strSql�F��������SQL
	 * @return strSql�F��������SQL
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private StringBuffer TantoushaDataSearch2ExistenceCheckSQLNew(RequestResponsKindBean requestData, StringBuffer strSql)
		throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try {
			String strUserId = null;

			//���[�UID�̎擾
			strUserId = requestData.getFieldVale(0, 0, "id_user");

			//SQL���̍쐬
			strSql.append("SELECT id_user");
			strSql.append(" FROM ma_user_new");
			strSql.append(" WHERE id_user = ");
			strSql.append(strUserId);

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
		return strSql;
	}
	// ADD 2013/12/18 QP@30154 okano end

}
