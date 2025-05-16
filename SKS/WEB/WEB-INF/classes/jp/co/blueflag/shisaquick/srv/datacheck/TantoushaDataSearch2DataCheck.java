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
			String strDataId = null;

			//�����f�[�^ID�擾
			for (int i = 0; i < userInfoData.getId_gamen().size(); i++) {
				if (userInfoData.getId_gamen().get(i).toString().equals("90")){
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

}
