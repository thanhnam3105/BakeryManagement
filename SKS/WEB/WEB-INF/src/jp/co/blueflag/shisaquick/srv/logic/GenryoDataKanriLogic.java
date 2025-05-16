package jp.co.blueflag.shisaquick.srv.logic;

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
 * �����f�[�^�Ǘ�DB����
 *  : �����f�[�^�폜�����̂c�a�ɑ΂���Ɩ����W�b�N�̎���
 *  
 * @author TT.k-katayama
 * @since  2009/04/14
 *
 */
public class GenryoDataKanriLogic extends LogicBase {
	
	/**
	 * �R���X�g���N�^
	 */
	public GenryoDataKanriLogic() {
		//���N���X�̃R���X�g���N�^
		super();

	}
	
	/**
	 * �����f�[�^�폜�Ǘ�
	 * @param reqKind : �@�\���N�G�X�g�f�[�^
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

		RequestResponsKindBean resKind = null;
		
		try {

			String strReqKaishaCd = "";							//���N�G�X�g�f�[�^ : ��ЃR�[�h
			String strReqGenryoCd = "";							//���N�G�X�g�f�[�^ : �����R�[�h
//			String strReqHaishiFlg = "";							//���N�G�X�g�f�[�^ : �p�~�t���O

			// �@�\���N�G�X�g�f�[�^�̎擾
			strReqKaishaCd = reqData.getFieldVale(0, 0, "cd_kaisha");
			strReqGenryoCd = reqData.getFieldVale(0, 0, "cd_genryo");
//			strReqHaishiFlg = reqData.getFieldVale(0, 0, "flg_haishi");

			//�@�F�����f�[�^�폜SQL�쐬�������\�b�h���Ăяo���A�����f�[�^�̍폜�������s���B
			this.genryoDeleteSQL(strReqKaishaCd,strReqGenryoCd);
			
			//�A�F����I�����A�Ǘ����ʃp�����[�^�[�i�[���\�b�h���ďo���A���X�|���X�f�[�^���`������B
			
			//���X�|���X�f�[�^�i�@�\�j����
			resKind = new RequestResponsKindBean();
			//�@�\ID�̐ݒ�
			resKind.setID(reqData.getID());
			//�e�[�u�����̐ݒ�
			resKind.addTableItem(reqData.getTableID(0));
			
			this.storageGenryoDataKanri(resKind.getTableItem(0));
			
		} catch (Exception e) {
			this.em.ThrowException(e, "�����f�[�^�폜�Ǘ����������s���܂����B");
		} finally {
			if (searchDB != null) {
				//�Z�b�V�����̃N���[�Y
				searchDB.Close();
				searchDB = null;
			}
			if (execDB != null) {
				//�Z�b�V�����̃N���[�Y
				execDB.Close();
				execDB = null;
			}
		}

		return resKind;
	}

	/**
	 * �����f�[�^�폜SQL�쐬
	 *  : �폜�pSQL���쐬���A�������͏��}�X�^�y�ь����}�X�^���폜����
	 *   �� �Ώۃe�[�u�� : ma_genryo, ma_genryokojo
	 * @param strKaishaCd : ��ЃR�[�h
	 * @param strGenryoCd : �����R�[�h
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private void genryoDeleteSQL(String strKaishaCd, String strGenryoCd)
			throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		StringBuffer strSQL_M401 = new StringBuffer();
		StringBuffer strSQL_M402 = new StringBuffer();

		try {
			
			//�@�F���N�G�X�g�f�[�^���A�����f�[�^�폜���s���ׂ�SQL���쐬����B
			
			//ma_genryo : �������͏��}�X�^
			strSQL_M401.append(" DELETE FROM ma_genryo ");
			strSQL_M401.append("  WHERE cd_kaisha =" + strKaishaCd );
			strSQL_M401.append("   AND  cd_genryo ='" + strGenryoCd + "' ");
			
			//ma_genryokojo : �����}�X�^(�V�K�����̂�)
			strSQL_M402.append(" DELETE FROM ma_genryokojo ");
			strSQL_M402.append("  WHERE cd_kaisha =" + strKaishaCd );
			strSQL_M402.append("   AND  cd_busho =" + ConstManager.getConstValue(Category.�ݒ�, "SHINKIGENRYO_BUSHOCD"));
			strSQL_M402.append("   AND  cd_genryo ='" + strGenryoCd + "' ");
			
			//�A�F�f�[�^�x�[�X�Ǘ���p���A�����f�[�^�̍폜���s���B
			super.createExecDB();								//DB�X�V�̐���
			this.execDB.BeginTran();							//�g�����U�N�V�����J�n
			
			try{
				//M401 �������͏��}�X�^�@�폜�pSQL���s
				this.execDB.execSQL(strSQL_M401.toString());
				//M402 �����}�X�^�@�폜�pSQL���s			
				this.execDB.execSQL(strSQL_M402.toString());
				
				this.execDB.Commit();							//�R�~�b�g

			}catch(Exception e){
				this.execDB.Rollback();							//���[���o�b�N
			}
			
		} catch (Exception e) {
			this.em.ThrowException(e, "�����f�[�^�폜SQL�쐬���������s���܂����B");
		} finally {
			//�ϐ��̍폜
			strSQL_M401 = null;
			strSQL_M402 = null;
		}
		
	}
	
	/**
	 * �Ǘ����ʃp�����[�^�[�i�[
	 *  : �����f�[�^�̊Ǘ����ʏ������X�|���X�f�[�^�֊i�[����B
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private void storageGenryoDataKanri(RequestResponsTableBean resTable) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		try {

			//�������ʂ̊i�[
			resTable.addFieldVale(0, "flg_return", "true");
			resTable.addFieldVale(0, "msg_error", "");
			resTable.addFieldVale(0, "no_errmsg", "");
			resTable.addFieldVale(0, "nm_class", "");
			resTable.addFieldVale(0, "cd_error", "");
			resTable.addFieldVale(0, "msg_system", "");
			
		} catch (Exception e) {
			this.em.ThrowException(e, "�Ǘ����ʃp�����[�^�[�i�[���������s���܂����B");

		} finally {

		}
		
	}
	
}
