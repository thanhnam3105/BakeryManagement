package jp.co.blueflag.shisaquick.srv.logic_genka;

import jp.co.blueflag.shisaquick.srv.base.LogicBase;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsKindBean;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsTableBean;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * �r������
 *  : �r���������s��
 *  �@�\ID�FFGEN0090
 *  
 * @author Nishigawa
 * @since  2010/02/17
 */
public class FGEN0090_Logic extends LogicBase{
	
	/**
	 * �r������
	 * : �C���X�^���X����
	 */
	public FGEN0090_Logic() {
		//���N���X�̃R���X�g���N�^
		super();

	}
	
	//--------------------------------------------------------------------------------------------------------------
	//
	//                                                                     ExecLogic�i�����J�n�j 
	//
	//--------------------------------------------------------------------------------------------------------------
	
	/**
	 * �r������
	 *  : �r���������s��
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

		List<?> lstRecset = null;

		RequestResponsKindBean resKind = null;
		
		try {
			
			//���X�|���X�f�[�^�i�@�\�j����
			resKind = new RequestResponsKindBean();
			
			//�@�\ID�̐ݒ�
			String strKinoId = reqData.getID();
			resKind.setID(strKinoId);
			
			//�r������[ table ]�@���X�|���X�f�[�^�̌`��
			this.haitaTableSetting(resKind, reqData);
			
		} catch (Exception e) {
			this.em.ThrowException(e, "");

		} finally {
			//���X�g�̔j��
			removeList(lstRecset);
		
			if (searchDB != null) {
				//�Z�b�V�����̃N���[�Y
				searchDB.Close();
				searchDB = null;
			}

			//�ϐ��̍폜
			
		}
		return resKind;
		
	}
	
	/**
	 * �r������[ table ]���X�|���X�f�[�^�̌`��
	 * @param resKind : ���X�|���X�f�[�^
	 * @param reqData : ���N�G�X�g�f�[�^
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 *  
	 * @author TT.Nishigawa
	 * @since  2010/02/17
	 */
	private void haitaTableSetting(
			
			 RequestResponsKindBean resKind
			,RequestResponsKindBean reqData 
			)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		//���R�[�h�l�i�[���X�g
		List<?> lstRecset = null;	
		
		//���R�[�h�l�i�[���X�g
		StringBuffer strSqlBuf = null;

		try {
			
			//�e�[�u����
			String strTblNm = "table";

			//�r������
			updateHaitaKbn(reqData);
			
			//���X�|���X�f�[�^�Ƀe�[�u���ǉ�
			resKind.addTableItem(strTblNm);
			
			//�ǉ������e�[�u���փ��R�[�h�i�[
			this.storageHaitakaizyo(resKind.getTableItem(strTblNm));
			
		} catch (Exception e) {
			this.em.ThrowException(e, "�r������[ table ]���X�|���X�f�[�^�̌`�����������s���܂����B");
			
		} finally {
			//���X�g�̔j��
			removeList(lstRecset);
			
			if (searchDB != null) {
				//�Z�b�V�����̉��
				this.searchDB.Close();
				searchDB = null;
				
			}

			//�ϐ��̍폜
			strSqlBuf = null;

		}
		
	}
	
	
	/**
	 * �r���敪�̍X�V
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private void updateHaitaKbn(RequestResponsKindBean reqData)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		StringBuffer strSQL = new StringBuffer();
		
		try {
			
			// �g�����U�N�V�����J�n
			super.createExecDB();
			execDB.BeginTran();
			
			try {
				strSQL.append(" UPDATE tr_shisan_shisakuhin");
				strSQL.append("   SET ");
				strSQL.append("        haita_id_user = NULL ");
				strSQL.append(" WHERE ");
				strSQL.append("     cd_shain = " + 
						toString(reqData.getFieldVale(0, 0, "cd_shain")) + " ");
				strSQL.append(" AND nen = " + 
						toString(reqData.getFieldVale(0, 0, "nen")) + " ");
				strSQL.append(" AND no_oi = " + 
						toString(reqData.getFieldVale(0, 0, "no_oi")) + " ");

				//�yQP@00342�z
				strSQL.append(" AND no_eda = " + 
						toString(reqData.getFieldVale(0, 0, "no_eda")) + " ");

				
				//���ʃN���X�@�f�[�^�x�[�X�Ǘ���p���A�r���敪�̍X�V���s��
				this.execDB.execSQL(strSQL.toString());
				
				// �R�~�b�g
				execDB.Commit();
				
			} catch(Exception e) {
				// ���[���o�b�N
				execDB.Rollback();
				this.em.ThrowException(e, "");
				
			} finally {
				if (execDB != null) {
					execDB.Close();				//�Z�b�V�����̃N���[�Y
					execDB = null;
				}
			}

		} catch (Exception e) {
			this.em.ThrowException(e, "�r���������������s���܂����B");
			
		} finally {
			//�ϐ��̍폜
			strSQL = null;
			
		}
	}
	
	
	
	/**
	 * �r������[ table ]�p�����[�^�[�i�[
	 * @param resTable : ���X�|���X�f�[�^
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private void storageHaitakaizyo(
				RequestResponsTableBean resTable
			) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try {
			
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
