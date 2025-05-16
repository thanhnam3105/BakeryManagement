package jp.co.blueflag.shisaquick.srv.logic;

import java.util.List;

import jp.co.blueflag.shisaquick.srv.base.LogicBase;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsKindBean;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsTableBean;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;

/**
 * 
 * �����̔ԏ����̎���
 *  : �����̔ԏ����̂c�a�ɑ΂���Ɩ����W�b�N�̎���
 *  
 * @author TT.k-katayama
 * @since  2009/04/14
 *
 */
public class JidouSaibanLogic extends LogicBase {
	
	/**
	 * �R���X�g���N�^
	 */
	public JidouSaibanLogic() {
		//���N���X�̃R���X�g���N�^
		super();

	}
	
	/**
	 * �����̔ԃf�[�^�Ǘ�
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
		
		try {
			//���X�|���X�f�[�^�i�@�\�j����
			resKind = new RequestResponsKindBean();

			String strReqShoriKbn = "";					//���N�G�X�g�f�[�^ : �����敪
			String strNewCode = "";						//�V�K���s�R�[�h
			
			//�@�\ID�̐ݒ�
			resKind.setID(reqData.getID());
			//�e�[�u�����̐ݒ�
			resKind.addTableItem(reqData.getTableID(0));
			
			// �@�\���N�G�X�g�f�[�^�̎擾
			strReqShoriKbn = reqData.getFieldVale(0, 0, "kbn_shori");
			
			//�@�F���N�G�X�g�f�[�^���A�����敪�𒊏o���A������U�蕪����B
			
			if ( strReqShoriKbn.equals("cd_shisaku") ) {
				//�����敪�F����R�[�h��(cd_shisaku)
				
				//����R�[�h�p�̔Ԍ����������\�b�h���ďo���A�V�K���s�R�[�h���擾����
				strNewCode = this.shisakuSelect();
				//�V�K���s�R�[�h��p���āA����R�[�h�p�̔ԍX�V�������\�b�h�ɂĎ����̔ԏ������s���B
				this.shisakuUpdate(strNewCode);
				
			} else if ( strReqShoriKbn.equals("cd_genryo") ) {
				//�����敪�F�����R�[�h��(cd_genryo)

				//�����R�[�h�p�̔Ԍ����������\�b�h���ďo���A�V�K���s�R�[�h���擾����
				strNewCode = this.genryoSelect();
				//�V�K���s�R�[�h��p���āA�����R�[�h�p�̔ԍX�V�������\�b�h�ɂĎ����̔ԏ������s���B
				this.genryoUpdate(strNewCode);
				
			}
			//�A�F�@�ɂĕԋp���ꂽ�V�K���s�R�[�h��p���A�����̔ԃf�[�^�p�����[�^�[�i�[���ďo���A���N�G�X�g�f�[�^���`������B
			this.storageJidouSaibanData(strNewCode, resKind.getTableItem(0));
			
		} catch (Exception e) {
			this.em.ThrowException(e, "�����̔ԃf�[�^�Ǘ����������s���܂����B");
		
		} finally {
	
		}
		return resKind;

	}

	/**
	 * �����R�[�h�p�̔Ԍ�������
	 *  : �̔ԃ}�X�^���A�����R�[�h�p�̐V�K���s�R�[�h���擾����B
	 * @return �V�K���s�R�[�h
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private String genryoSelect() throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		String strRetNewCode = "";
		
		StringBuffer strSQL = new StringBuffer();
		List<?> lstSearchAry = null;
		
		try {
			//�@�F���L������p���āA�̔ԃ}�X�^�ima_saiban�j���A�V�K���s�R�[�h�����pSQL���쐬����B

			strSQL.append(" SELECT MAX(M601.no_seq)+1 AS no_seq_max ");
			strSQL.append(" FROM ma_saiban M601 ");
			//�̔ԃ}�X�^.�L�[1 = �����R�[�h
			//�̔ԃ}�X�^.�L�[2 = N
			strSQL.append(" WHERE M601.key1='�����R�[�h' AND M601.key2='N' ");
			
			//�A�F�쐬����SQL��p���āA�����������s���A�V�K���s�R�[�h���擾����B
			super.createSearchDB();
			lstSearchAry = this.searchDB.dbSearch(strSQL.toString());

			if ( lstSearchAry.size() >= 0 ) {
				//�V�K���s�R�[�h�@�F�@�̔ԃ}�X�^.�̔Ԃ̍ő�l+1
				strRetNewCode = lstSearchAry.get(0).toString();
				
			} else {
				//�������ʂ����݂��Ȃ������ꍇ
				strRetNewCode = "1";		//�V�K���s�R�[�h��1���i�[����
			}
			
		} catch (Exception e) {
			this.em.ThrowException(e, "�����R�[�h�p�̔Ԍ������������s���܂����B");
		} finally {
			//���X�g�̔j��
			removeList(lstSearchAry);
			if (searchDB != null) {
				//�Z�b�V�����̃N���[�Y
				searchDB.Close();
				searchDB = null;
			}
			
			//�ϐ��̍폜
			strSQL = null;
		}
		
		//�B:�擾�����V�K���s�R�[�h��ԋp����B
		return strRetNewCode;
	}

	/**
	 * ����R�[�h�p�̔Ԍ�������
	 *  : ����̔ԃ}�X�^���A����R�[�h�p�̐V�K���s�R�[�h���擾����B
	 * @return �V�K���s�R�[�h
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private String shisakuSelect() throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		String strRetNewCode = "";
		StringBuffer strSQL = new StringBuffer();
		List<?> lstSearchAry = null;
		
		try {
			String strUserId = userInfoData.getId_user();		//���[�U���.���[�UID
			
			//�@�F���L������p���āA����̔ԃ}�X�^�ima_shisakusaiban�j���A�V�K���s����R�[�h�����pSQL���쐬����B
			
			strSQL.append(" SELECT  ");
			strSQL.append("   RIGHT(YEAR(GETDATE()),2) AS nen ");
			strSQL.append("  ,ISNULL(MAX(M602.no_oi)+1,1) AS no_oi ");
			strSQL.append(" FROM ma_shisaku_saiban M602 ");
			//����̔ԃ}�X�^.����CD-�Ј��R�[�h =  ���[�U���.���[�UID
			strSQL.append(" WHERE M602.cd_shain =" + strUserId);
			//����̔ԃ}�X�^.����CD-�N = �V�X�e��.�N�̉���
			strSQL.append("   AND M602.nen = RIGHT(YEAR(GETDATE()),2) ");
			
			//�A�F�쐬����SQL��p���āA�����������s���A�V�K���s�R�[�h���擾����B
			super.createSearchDB();
			lstSearchAry = this.searchDB.dbSearch(strSQL.toString());
			
			//�V�K���s�R�[�h�@�F�@"���[�U���.���[�UID" + "-" + "�V�X�e��.�N���̉���" + "-" + "����̔ԃ}�X�^.�ǔԂ̍ő�l+1"
			if ( lstSearchAry.size() >= 0 ) {
				Object[] items = (Object[]) lstSearchAry.get(0);
				strRetNewCode += strUserId + "-";
				strRetNewCode += items[0].toString() + "-";
				strRetNewCode += items[1].toString();
			}
			
		} catch (Exception e) {
			this.em.ThrowException(e, "����R�[�h�p�̔Ԍ������������s���܂����B");
		} finally {
			//���X�g�̔j��
			removeList(lstSearchAry);
			if (searchDB != null) {
				//�Z�b�V�����̃N���[�Y
				searchDB.Close();
				searchDB = null;
			}

			//�ϐ��̍폜
			strSQL = null;
		}

		//�B:�擾�����V�K���s�R�[�h��ԋp����B
		return strRetNewCode;
	}

	/**
	 * �����R�[�h�p�̔ԍX�V����
	 *  : �����R�[�h�p�̍̔ԃ}�X�^�f�[�^�̍X�V�������s���B
	 * @param strNewCode : �V�K���s�R�[�h
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private void genryoUpdate(String strNewCode) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		StringBuffer strSQL = new StringBuffer();

		try {
			String strUserId = "";							//���[�U��� : ���[�UID
			
			//���[�U���̃��[�UID���擾
			strUserId = userInfoData.getId_user();
			
			//�@�F���L������p���āA�̔ԃ}�X�^�ima_saiban�j.�̔Ԃ̎����̔ԗpSQL���쐬����B

			strSQL.append(" UPDATE ma_saiban ");
			strSQL.append("   SET no_seq =" + strNewCode);
			strSQL.append("      ,id_koshin =" + strUserId);
			strSQL.append("      ,dt_koshin = GETDATE() ");
			//�̔ԃ}�X�^.�L�[1 = �����R�[�h
			//�̔ԃ}�X�^.�L�[2 = N
			strSQL.append(" WHERE key1='�����R�[�h' AND key2='N' ");
			
			//�A�F�쐬����SQL��p���āA�X�V�������s���B
			super.createExecDB();							//DB�X�V�̐���
			this.execDB.BeginTran();						//�g�����U�N�V�����J�n
			
			try{
				this.execDB.execSQL(strSQL.toString());		//SQL���s
				this.execDB.Commit();							//�R�~�b�g

			}catch(Exception e){
				this.execDB.Rollback();							//���[���o�b�N
			}

		} catch (Exception e) {
			this.em.ThrowException(e, "�����R�[�h�p�̔ԍX�V���������s���܂����B");
		} finally {
			if (execDB != null) {
				//�Z�b�V�����̃N���[�Y
				execDB.Close();
				execDB = null;
			}

			//�ϐ��̍폜
			strSQL = null;
		}
		
	}

	/**
	 * ����R�[�h�p�̔ԍX�V����
	 *  : ����̔ԃ}�X�^�Ƀf�[�^�����݂��Ȃ��ꍇ�́A�o�^�������s���B
	 *   ���݂���ꍇ�́A�X�V�������s���A�����̔ԏ������s���B
	 * @param strNewCode : �V�K���s�R�[�h
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private void shisakuUpdate(String strNewCode) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		StringBuffer strSQL = new StringBuffer();
		
		try {
			//�@�F����.�V�K���s�R�[�h���u-�v���Ƃ̕�����ɕ������A�Ј��R�[�h�E�N�E�ǔԂ��擾����
			String strShainCd = strNewCode.split("-")[0];
			String strNen = strNewCode.split("-")[1];
			String strOiNo = strNewCode.split("-")[2];  
			
			//�A:�@�̒ǔԂ̒l�ɂ��A������U�蕪����
			if ( strOiNo.equals("1") ) {
				//�B�F����̔ԃ}�X�^�ima_shisakusaiban�j�̓o�^�������s��

				strSQL.append(" INSERT INTO ma_shisaku_saiban ");
				strSQL.append("  (cd_shain, nen, no_oi, id_toroku, dt_toroku, id_koshin, dt_koshin) ");
				strSQL.append("  VALUES ( ");
				strSQL.append(" " + strShainCd);
				strSQL.append(" ," + strNen);
				strSQL.append(" ," + strOiNo);
				strSQL.append("  ," + strShainCd + " ,GETDATE() ");		//�X�V��
				strSQL.append("  ," + strShainCd + " ,GETDATE() )");		//�o�^��
			
			} else {
				//�C�F����̔ԃ}�X�^�ima_shisakusaiban�j.�ǔԂ̍X�V�������s��

				strSQL.append(" UPDATE ma_shisaku_saiban ");
				strSQL.append("   SET no_oi =" + strOiNo);
				strSQL.append("      ,id_koshin =" + strShainCd);
				strSQL.append("      ,dt_koshin = GETDATE() ");
				strSQL.append(" WHERE cd_shain =" + strShainCd);
				strSQL.append("   AND nen =" + strNen);
				
			}
			
			//�D:�쐬����SQL��p���āA�o�^�E�X�V�������s���B
			super.createExecDB();							//DB�X�V�̐���
			this.execDB.BeginTran();						//�g�����U�N�V�����J�n
			
			try{
				this.execDB.execSQL(strSQL.toString());		//SQL���s
				this.execDB.Commit();							//�R�~�b�g

			}catch(Exception e){
				this.execDB.Rollback();							//���[���o�b�N
			}
			
		} catch (Exception e) {
			this.em.ThrowException(e, "����R�[�h�p�̔ԍX�V���������s���܂����B");
		} finally {
			if (execDB != null) {
				//�Z�b�V�����̃N���[�Y
				execDB.Close();
				execDB = null;
			}

			//�ϐ��̍폜
			strSQL = null;
		}
		
	}

	/**
	 * �����̔ԃf�[�^�p�����[�^�[�i�[
	 *  : �����̔ԃf�[�^�������X�|���X�f�[�^�֊i�[����B
	 * @param strNewCode : �V�K���s�R�[�h
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private void storageJidouSaibanData(String strNewCode, RequestResponsTableBean resTable)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		try {

			//�������ʂ̊i�[
			resTable.addFieldVale(0, "flg_return", "true");
			resTable.addFieldVale(0, "msg_error", "");
			resTable.addFieldVale(0, "no_errmsg", "");
			resTable.addFieldVale(0, "nm_class", "");
			resTable.addFieldVale(0, "cd_error", "");
			resTable.addFieldVale(0, "msg_system", "");
			
			//���ʂ����X�|���X�f�[�^�Ɋi�[
			resTable.addFieldVale(0, "new_code", strNewCode );
			
		} catch (Exception e) {
			this.em.ThrowException(e, "�����̔ԃf�[�^�p�����[�^�[�i�[���������s���܂����B");

		} finally {

		}
		
	}
		
}
