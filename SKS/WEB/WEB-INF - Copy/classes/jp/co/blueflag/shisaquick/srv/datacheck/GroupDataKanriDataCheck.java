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
 * �O���[�v�}�X�^�����e�F�O���[�v���f�[�^�`�F�b�N����
 *  : �O���[�v�}�X�^�����e�F�O���[�v���̃f�[�^�`�F�b�N���s���B
 * @author jinbo
 * @since  2009/04/17
 */
public class GroupDataKanriDataCheck extends DataCheck{

	/**
	 * �O���[�v�}�X�^�����e�F�O���[�v���f�[�^�`�F�b�N�����p�R���X�g���N�^ 
	 * : �C���X�^���X����
	 */
	public GroupDataKanriDataCheck() {
		//���N���X�̃R���X�g���N�^
		super();

	}

	/**
	 * �O���[�v�}�X�^�����e�F�O���[�v���f�[�^�`�F�b�N
	 *  : �O���[�v���̃f�[�^�`�F�b�N���s���B
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
			String strShotriKbn = null;
			String strGroupCd = null;

			//�����敪�̎擾
			strShotriKbn = reqData.getFieldVale(0, 0, "kbn_shori");
			//�O���[�v�R�[�h�̎擾
			strGroupCd = reqData.getFieldVale(0, 0, "cd_group");
			//DB�̃O���[�v�R�[�h��small int�^�̂���if���ɂ��SQL�G���[��h��
			if (!(strGroupCd.equals(""))) {
				// SQL���̍쐬
				strSql = groupKanriExistenceCheckSQL(reqData, strSql);

				// SQL�����s
				super.createSearchDB();
				lstRecset = searchBD.dbSearch(strSql.toString());
			}
			//�o�^�̏ꍇ
			if (strShotriKbn.equals("1")) {
				//�O���[�v�R�[�h�����͂���Ă���ꍇ
				if (!strGroupCd.equals("")) {
					//�f�[�^�����݂���ꍇ
					if (lstRecset.size() != 0){
						em.ThrowException(ExceptionKind.���Exception,"E000302", "�O���[�v�R�[�h", strGroupCd.toString(), "");
					}
				}
				
			//�X�V�E�폜�̏ꍇ
			} else {
				//�f�[�^�����݂��Ȃ��ꍇ
				if (lstRecset.size() == 0){
					em.ThrowException(ExceptionKind.���Exception,"E000301", "�O���[�v�R�[�h", strGroupCd.toString(), "");
				}
			}

		} catch (Exception e) {
			this.em.ThrowException(e, "�O���[�v�Ǘ��f�[�^�`�F�b�N�����Ɏ��s���܂����B");
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
	 * �O���[�v�R�[�h���݃`�F�b�NSQL�쐬
	 *  : �O���[�v�̑��݃`�F�b�N������SQL���쐬�B
	 * @param reqData�F���N�G�X�g�f�[�^
	 * @param strSql�F��������SQL
	 * @return strSql�F��������SQL
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private StringBuffer groupKanriExistenceCheckSQL(RequestResponsKindBean requestData, StringBuffer strSql) 
		throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		try {
			String strGroupCd = null;

			//�O���[�v�R�[�h�̎擾
			strGroupCd = requestData.getFieldVale(0, 0, "cd_group");

			//SQL���̍쐬
			strSql.append("SELECT cd_group");
			strSql.append(" FROM ma_group");
			strSql.append(" WHERE cd_group = ");
			strSql.append(strGroupCd);

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
		return strSql;
	}
}
