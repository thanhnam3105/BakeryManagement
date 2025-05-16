package jp.co.blueflag.shisaquick.srv.logic;

import jp.co.blueflag.shisaquick.srv.base.LogicBase;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsKindBean;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsTableBean;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;

/**
 * 
 * [SA820] JWS-���Z�m�藚��o�^�c�a�����̎���
 *  
 * @author TT.k-katayama
 * @since  2009/06/10
 *
 */
public class ShisanRirekiTorokuLogic extends LogicBase {
	
	/**
	 * �R���X�g���N�^
	 */
	public ShisanRirekiTorokuLogic() {
		//���N���X�̃R���X�g���N�^
		super();

	}
	
	/**
	 * JWS-���Z�m�藚��o�^�c�a�������W�b�N�Ǘ�
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

		//���X�|���X�f�[�^�N���X
		RequestResponsKindBean resKind = null;
		//�o�^�pSQL
		StringBuffer strSql = null;

		try {
			
			//���X�|���X�f�[�^�i�@�\�j����
			resKind = new RequestResponsKindBean();
			
			//�@:�g�����U�N�V�������J�n����
			super.createExecDB();								//DB�X�V�̐���
			this.execDB.BeginTran();							//�g�����U�N�V�����J�n			

			try {

				//�A : ���Z����o�^�pSQL���쐬
				strSql = this.createInsertSQL(reqData);
								
				//�B : ���Z����o�^�pSQL�����s
				this.execDB.execSQL(strSql.toString());
				
				//�C : :�R�~�b�g/���[���o�b�N���������s����
				this.execDB.Commit();							//�R�~�b�g
				
			} catch(Exception e) {
				this.execDB.Rollback();							//���[���o�b�N
				this.em.ThrowException(e, "");
				
			} finally {
				
			}

			//�D : �o�^���ʃp�����[�^�[�i�[�������Ăяo���A���X�|���X�f�[�^�ɐݒ肷��

			//�@�\ID�̐ݒ�
			String strKinoId = reqData.getID();
			resKind.setID(strKinoId);

			//�e�[�u�����̐ݒ�
			String strTableNm = reqData.getTableID(0);
			resKind.addTableItem(strTableNm);

			//�p�����[�^�i�[
			this.storageResponsData(resKind.getTableItem(strTableNm));

		} catch (Exception e) {
			this.em.ThrowException(e, "JWS-���Z�m�藚��o�^�c�a���������s���܂����B");

		} finally {			
			//�Z�b�V�����̉��
			if (this.execDB != null) {
				this.execDB.Close();
				this.execDB = null;
				
			}
			
			//�ϐ��̍폜
			strSql = null;

		}
		return resKind;

	}

	/**
	 * ���Z����o�^�pSQL�̍쐬
	 * @param reqData : �@�\���N�G�X�g�f�[�^
	 * @return strSql : �쐬SQL
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private StringBuffer createInsertSQL(RequestResponsKindBean reqData)
		throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		StringBuffer ret = new StringBuffer();
		
		try {

			//�o�^�pSQL�쐬
			ret.append(" INSERT INTO tr_shisan_rireki ");
			ret.append("  (cd_shain, nen, no_oi, seq_shisaku, sort_rireki, nm_sample, dt_shisan, id_toroku, dt_toroku) ");
			ret.append(" VALUES ( ");
			ret.append("  " + reqData.getFieldVale(0, 0, "cd_shain") );
			ret.append("  ," + reqData.getFieldVale(0, 0, "nen") );
			ret.append("  ," + reqData.getFieldVale(0, 0, "no_oi") );
			ret.append("  ," + reqData.getFieldVale(0, 0, "seq_shisaku") );
			ret.append("  ," + reqData.getFieldVale(0, 0, "sort_rireki") );
			ret.append("  ,'" + reqData.getFieldVale(0, 0, "nm_sample") + "'");
			ret.append("  ,GETDATE()" );
//			ret.append("  ," + reqData.getFieldVale(0, 0, "cd_shain"));
			ret.append("  ," + userInfoData.getId_user());
			ret.append("  ,GETDATE()" );
			ret.append(" ) ");
			
		} catch (Exception e) {
			this.em.ThrowException(e, "");
			
		} finally {
			
		}
		return ret;
		
	}

	/**
	 * �o�^���ʃp�����[�^�[�i�[����
	 * @param resTable : ���X�|���X�e�[�u��
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private void storageResponsData(RequestResponsTableBean resTable)
		throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		try {
			//�@�F���X�|���X�f�[�^���`������B

			//�������ʂ̊i�[
			resTable.addFieldVale(0, "flg_return", "true");
			resTable.addFieldVale(0, "msg_error", "");
			resTable.addFieldVale(0, "no_errmsg", "");
			resTable.addFieldVale(0, "nm_class", "");
			resTable.addFieldVale(0, "cd_error", "");
			resTable.addFieldVale(0, "msg_system", "");
			
		} catch (Exception e) {
			this.em.ThrowException(e, "");
			
		} finally {
			
		}
		
	}
	
}
