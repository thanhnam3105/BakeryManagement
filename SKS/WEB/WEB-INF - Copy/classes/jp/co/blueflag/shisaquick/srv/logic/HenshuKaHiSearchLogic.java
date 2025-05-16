package jp.co.blueflag.shisaquick.srv.logic;

import jp.co.blueflag.shisaquick.srv.base.LogicBase;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsKindBean;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsTableBean;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ConstManager;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;

/**
 * 
 * �R���{�p�F�ҏW�ۏ�񌟍�DB����
 *  : �R���{�p�F�ҏW�ۏ�����������B
 * @author jinbo
 * @since  2009/04/07
 */
public class HenshuKaHiSearchLogic extends LogicBase{
	
	/**
	 * �R���{�p�F�ҏW�ۏ�񌟍�DB���� 
	 * : �C���X�^���X����
	 */
	public HenshuKaHiSearchLogic() {
		//���N���X�̃R���X�g���N�^
		super();

	}

	/**
	 * �R���{�p�F�ҏW�ۏ��擾SQL�쐬
	 *  : �ҏW�ۃR���{�{�b�N�X�����擾����SQL���쐬�B
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

		//���X�|���X�f�[�^�N���X
		RequestResponsKindBean resKind = null;
		
		try {
			//���X�|���X�f�[�^�i�@�\�j����
			resKind = new RequestResponsKindBean();

			//�@�\ID�̐ݒ�
			String strKinoId = reqData.getID();
			resKind.setID(strKinoId);

			//�e�[�u�����̐ݒ�
			String strTableNm = reqData.getTableID(0);
			resKind.addTableItem(strTableNm);

			//���X�|���X�f�[�^�̌`��
			storageHenshuKaHiCmb(resKind.getTableItem(0));
			
		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
		return resKind;

	}	

	/**
	 * �R���{�p�F�ҏW�ۃp�����[�^�[�i�[
	 *  : �ҏW�ۃR���{�{�b�N�X�������X�|���X�f�[�^�֊i�[����B
	 * @param lstGroupCmb : �������ʏ�񃊃X�g
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private void storageHenshuKaHiCmb(RequestResponsTableBean resTable)
		throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try {
			String strEditFlg = null;
			
			for (int i = 0; i < 2; i++) {

				//�������ʂ̊i�[
				resTable.addFieldVale(i, "flg_return", "true");
				resTable.addFieldVale(i, "msg_error", "");
				resTable.addFieldVale(i, "no_errmsg", "");
				resTable.addFieldVale(i, "nm_class", "");
				resTable.addFieldVale(i, "cd_error", "");
				resTable.addFieldVale(i, "msg_system", "");
				
				//���ʂ����X�|���X�f�[�^�Ɋi�[
				if (i == 0) {
					//�ҏW�s��
					strEditFlg = ConstManager.getConstValue(ConstManager.Category.�ݒ�, "EDIT_FLG_IMPOSSIBLE");
				} else {
					//�ҏW��
					strEditFlg = ConstManager.getConstValue(ConstManager.Category.�ݒ�, "EDIT_FLG_POSSIBLE");
				}
				resTable.addFieldVale(i, "cd_editflg", strEditFlg.split(",")[0]);
				resTable.addFieldVale(i, "nm_editflg", strEditFlg.split(",")[1]);
	
			}
			
		} catch (Exception e) {
			this.em.ThrowException(e, "");

		} finally {

		}

	}
	
}
