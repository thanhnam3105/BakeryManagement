package jp.co.blueflag.shisaquick.srv.logic;

import java.util.List;

import jp.co.blueflag.shisaquick.srv.base.LogicBase;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsKindBean;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsTableBean;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ConstManager;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;
import jp.co.blueflag.shisaquick.srv.common.ConstManager.Category;

/**
 * 
 * �R���X�g�f�[�^�擾
 * 	: QP@00412_�V�T�N�C�b�N���� No.4
 * @author TT.katayama
 * @since  2010/10/07
 */
public class JspConstDataLogic extends LogicBase{

	/**
	 * �R���X�g�f�[�^�擾�R���X�g���N�^ 
	 * : �C���X�^���X����
	 */
	public JspConstDataLogic() {
		//���N���X�̃R���X�g���N�^
		super();

	}
	
	/**
	 * �R���X�g�f�[�^�擾�������W�b�N�Ǘ�
	 * @param reqData : �@�\���N�G�X�g�f�[�^
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

		String strKinoId = "";
		String strTableNm = "";

		List<?> lstRecset = null;										//�������ʊi�[�p���X�g

		//���X�|���X�f�[�^�N���X
		RequestResponsKindBean resKind = null;
		
		try {
			
			//���X�|���X�f�[�^�i�@�\�j����
			resKind = new RequestResponsKindBean();

			// �@�\ID�̐ݒ�
			strKinoId = reqData.getID();
			resKind.setID(strKinoId);
			
			// �e�[�u�����̐ݒ�
			strTableNm = reqData.getTableID(0);
			resKind.addTableItem(strTableNm);

			//�������ʂ����X�|���X�f�[�^�Ɋi�[
			storageGenryoData(resKind.getTableItem(0));
						
		} catch (Exception e) {
			this.em.ThrowException(e, "�R���X�g�f�[�^�擾�����Ɏ��s���܂����B");

		} finally {
			//���X�g�̔j��
			removeList(lstRecset);
			if (searchDB != null) {
				//�Z�b�V�����̃N���[�Y
				searchDB.Close();
				searchDB = null;
			}

		}
		return resKind;

	}
	
	/**
	 * �R���X�g�f�[�^�擾�p�����[�^�[�i�[
	 *  : �R���X�g�f�[�^�������X�|���X�f�[�^�֊i�[����B
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private void storageGenryoData(RequestResponsTableBean resTable) 
			throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		//�@�F�����@�������ʏ�񃊃X�g�ɕێ����Ă���e�p�����[�^�[�����X�|���X�f�[�^�֊i�[����B
		try {

			//�������ʂ̊i�[
			resTable.addFieldVale("rec" + 0, "flg_return", "true");
			resTable.addFieldVale("rec" + 0, "msg_error", "");
			resTable.addFieldVale("rec" + 0, "no_errmsg", "");
			resTable.addFieldVale("rec" + 0, "nm_class", "");
			resTable.addFieldVale("rec" + 0, "cd_error", "");
			resTable.addFieldVale("rec" + 0, "msg_system", "");
			
			//���ʂ����X�|���X�f�[�^�Ɋi�[
			resTable.addFieldVale("rec" + 0, "nm_shiyo", ConstManager.getConstValue(Category.�ݒ�, "NM_SHIYO_JISEKI"));
			
		} catch (Exception e) {
			this.em.ThrowException(e, "�R���X�g�f�[�^�擾�����Ɏ��s���܂����B");
			
		} finally {

		}

	}

}
