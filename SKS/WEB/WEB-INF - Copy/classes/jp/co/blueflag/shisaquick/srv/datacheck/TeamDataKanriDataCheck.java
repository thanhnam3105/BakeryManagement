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
 * �`�[���f�[�^�Ǘ� �F�`�[���f�[�^�Ǘ��`�F�b�N����
 * @author itou
 * @since  2009/04/20
 */
public class TeamDataKanriDataCheck extends DataCheck{
		
	/**
	 * �`�[���}�X�^�����e �F �`�[���f�[�^�Ǘ��`�F�b�N�����p�R���X�g���N�^ 
	 * : �C���X�^���X����
	 */
	public TeamDataKanriDataCheck() {
		//���N���X�̃R���X�g���N�^
		super();

	}

	/**
	 * �`�[���}�X�^�����e �F �`�[���}�X�^�f�[�^�`�F�b�N
	 *  : �`�[���f�[�^�Ǘ��̃f�[�^�`�F�b�N���s���B
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
			// �`�[��CD
			String strTeamCd = reqData.getFieldVale(0, 0, "cd_team");
			//�`�[��CD��small int�^�̂���if���ɂ��SQL�G���[��h��
			if (!(strTeamCd.equals(""))) {
				// SQL���̍쐬
				strSql = teamDataKanriExistenceCheckSQL(reqData, strSql);

				// SQL�����s
				super.createSearchDB();
				lstRecset = searchBD.dbSearch(strSql.toString());
			}
			//�o�^�̏ꍇ
			if (strShotriKbn.equals("1")) {
				//�`�[���R�[�h�����͂���Ă���ꍇ
				if (!(strTeamCd.equals(""))) {
					//�f�[�^�����݂���ꍇ
					if (lstRecset.size() != 0){
						em.ThrowException(ExceptionKind.���Exception,"E000304", "�O���[�v�R�[�h : " + strGroupCd.toString(), "�`�[���R�[�h : " + strTeamCd.toString(), "");
					}
				}
			//�X�V�E�폜�̏ꍇ
			} else {
				//�f�[�^�����݂��Ȃ��ꍇ
				if (lstRecset.size() == 0){
					em.ThrowException(ExceptionKind.���Exception,"E000305","�O���[�v�R�[�h : " + strGroupCd.toString(), "�`�[���R�[�h : " + strTeamCd.toString(), "");
				}
			}
		} catch (Exception e) {
			this.em.ThrowException(e, "�`�[���f�[�^�Ǘ��f�[�^�`�F�b�N�����Ɏ��s���܂����B");
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
	 * �`�[���f�[�^���݃`�F�b�NSQL�쐬
	 *  : �`�[���f�[�^�̑��݃`�F�b�N������SQL���쐬�B
	 * @param reqData�F���N�G�X�g�f�[�^
	 * @param strSql�F��������SQL
	 * @return strSql�F��������SQL
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private StringBuffer teamDataKanriExistenceCheckSQL(RequestResponsKindBean requestData, StringBuffer strSql) 
		throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		try {
			// �O���[�vCD
			String strGroupCd = requestData.getFieldVale(0, 0, "cd_group");
			// �`�[��CD
			String strTeamCd = requestData.getFieldVale(0, 0, "cd_team");

			//SQL���̍쐬
			strSql.append("SELECT cd_group");
			strSql.append(" FROM ma_team");
			strSql.append(" WHERE cd_group = ");
			strSql.append(strGroupCd);
			strSql.append(" AND cd_team = ");
			strSql.append(strTeamCd);

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
		return strSql;
	}
}
