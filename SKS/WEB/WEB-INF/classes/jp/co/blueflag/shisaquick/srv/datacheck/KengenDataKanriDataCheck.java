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
 * �����}�X�^�����e �F �����f�[�^�Ǘ��̃f�[�^�`�F�b�N���s���B
 * 
 * @author itou
 * @since 2009/04/20
 */
public class KengenDataKanriDataCheck extends DataCheck{
	
	/**
	 * �����}�X�^�����e �F �����f�[�^�Ǘ��f�[�^�`�F�b�N�����p�R���X�g���N�^ : �C���X�^���X����
	 */
	public KengenDataKanriDataCheck() {
		//���N���X�̃R���X�g���N�^
		super();

	}

	/**
	 * �����}�X�^�����e �F �����f�[�^�Ǘ��̃f�[�^�`�F�b�N���s���B
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

			// �����敪�̎擾
			strShotriKbn = reqData.getFieldVale("ma_kengen", 0, "kbn_shori");
			// �����R�[�h�̎擾
			String strKengenCd = reqData.getFieldVale("ma_kengen", 0, "cd_kengen");
			if (!(strKengenCd.equals(""))) {
				// SQL���̍쐬
				strSql = kengenDataKanriExistenceCheckSQL(reqData, strSql);
				
				// SQL�����s
				super.createSearchDB();
				lstRecset = searchBD.dbSearch(strSql.toString());
			}
			// �o�^�̏ꍇ
			if (strShotriKbn.equals("1")) {
				// �����R�[�h�����͂���Ă���ꍇ
				if (!strKengenCd.equals("")) {
					// �f�[�^�����݂���ꍇ
					if (lstRecset.size() != 0){
						em.ThrowException(ExceptionKind.���Exception,"E000302", "�����R�[�h", strKengenCd.toString(), "");
					}
				}
				
			// �X�V�E�폜�̏ꍇ
			} else {
				// �f�[�^�����݂��Ȃ��ꍇ
				if (lstRecset.size() == 0){
					em.ThrowException(ExceptionKind.���Exception,"E000301", "�����R�[�h", strKengenCd.toString(), "");
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
	 * �����R�[�h���݃`�F�b�NSQL�쐬
	 *  : �����̑��݃`�F�b�N������SQL���쐬�B
	 * @param reqData�F���N�G�X�g�f�[�^
	 * @param strSql�F��������SQL
	 * @return strSql�F��������SQL
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private StringBuffer kengenDataKanriExistenceCheckSQL(RequestResponsKindBean requestData, StringBuffer strSql) 
		throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		try {
			
			// �����R�[�h�̎擾
			String strKengenCd = requestData.getFieldVale("ma_kengen", 0, "cd_kengen");

			//SQL���̍쐬
			strSql.append("SELECT cd_kengen");
			strSql.append(" FROM ma_kengen");
			strSql.append(" WHERE cd_kengen = ");
			strSql.append(strKengenCd);

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
		return strSql;
	}
}
