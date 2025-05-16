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
 * [SA870] JWS-�������ZNo�X�V�c�a�����̎���
 *  
 * @author TT.k-katayama
 * @since  2009/07/11
 *
 */
public class ShisanNoUpdateLogic extends LogicBase {
	
	/**
	 * �R���X�g���N�^
	 */
	public ShisanNoUpdateLogic() {
		//���N���X�̃R���X�g���N�^
		super();

	}
	
	/**
	 * JWS-�������ZNo�X�V�c�a�������W�b�N�Ǘ�
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

				//����SEQ1����ł͂Ȃ��ꍇ�A�������ZNo.�X�V�������s��
				if ( !toString(reqData.getFieldVale(0, 0, "seq_shisaku1")).isEmpty() ) {
					
					//�A : �������ZNo�X�V�pSQL���쐬
					strSql = this.createUpdateSQL(reqData, 1);
									
					//�B : �������ZNo�X�V�pSQL�����s
					this.execDB.execSQL(strSql.toString());
				
				}

				//����SEQ2����ł͂Ȃ��ꍇ�A�������ZNo.�X�V�������s��
				if ( !toString(reqData.getFieldVale(0, 0, "seq_shisaku2")).isEmpty() ) {

					//�C : �������ZNo�X�V�pSQL���쐬
					strSql = this.createUpdateSQL(reqData, 2);
									
					//�D : �������ZNo�X�V�pSQL�����s
					this.execDB.execSQL(strSql.toString());
										
				}

				//����SEQ3����ł͂Ȃ��ꍇ�A�������ZNo.�X�V�������s��
				if ( !toString(reqData.getFieldVale(0, 0, "seq_shisaku3")).isEmpty() ) {

					//�E : �������ZNo�X�V�pSQL���쐬
					strSql = this.createUpdateSQL(reqData, 3);
									
					//�F : �������ZNo�X�V�pSQL�����s
					this.execDB.execSQL(strSql.toString());
										
				}
				
				//�G :�R�~�b�g/���[���o�b�N���������s����
				this.execDB.Commit();							//�R�~�b�g
				
			} catch(Exception e) {
				this.execDB.Rollback();							//���[���o�b�N
				this.em.ThrowException(e, "");
				
			} finally {
				
			}

			//�H : �o�^���ʃp�����[�^�[�i�[�������Ăяo���A���X�|���X�f�[�^�ɐݒ肷��

			//�@�\ID�̐ݒ�
			String strKinoId = reqData.getID();
			resKind.setID(strKinoId);

			//�e�[�u�����̐ݒ�
			String strTableNm = reqData.getTableID(0);
			resKind.addTableItem(strTableNm);

			//�p�����[�^�i�[
			this.storageResponsData(resKind.getTableItem(strTableNm));

		} catch (Exception e) {
			this.em.ThrowException(e, "JWS-�������ZNo�X�V�c�a���������s���܂����B");

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
	 * �������ZNo�X�V�pSQL�̍쐬
	 * @param reqData : �@�\���N�G�X�g�f�[�^
	 * @param intShisakuNo : 1�`3�܂ł̌������ZNo
	 * @return strSql : �쐬SQL
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private StringBuffer createUpdateSQL(RequestResponsKindBean reqData, int intShisanNo)
		throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		StringBuffer ret = new StringBuffer();
		String strSeqShisaku;
		
		try {
			
			//����SEQ���擾
			strSeqShisaku = toString(reqData.getFieldVale(0, 0, "seq_shisaku" + intShisanNo));
			
			//����SEQ��NULL�܂��͋�ł͖����ꍇ�A�X�V�pSQL���쐬����
			if ( !strSeqShisaku.isEmpty() ) {
			
				//�X�V�pSQL�쐬
				ret.append(" UPDATE tr_shisaku ");
				ret.append("    SET no_shisan = " + intShisanNo);
				ret.append("       ,id_koshin = " + userInfoData.getId_user());
				ret.append("       ,dt_koshin = GETDATE() ");
				ret.append("  WHERE cd_shain=" + reqData.getFieldVale(0, 0, "cd_shain"));
				ret.append("   AND nen=" + reqData.getFieldVale(0, 0, "nen"));
				ret.append("   AND no_oi=" + reqData.getFieldVale(0, 0, "no_oi"));
				ret.append("   AND seq_shisaku="+ strSeqShisaku);
				
			}
			
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
