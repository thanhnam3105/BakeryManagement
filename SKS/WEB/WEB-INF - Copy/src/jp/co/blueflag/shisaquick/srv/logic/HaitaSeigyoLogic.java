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
 * �r������c�a�����̎���
 *  : �r�����䏈���̂c�a�ɑ΂���Ɩ����W�b�N�̎���
 *  
 * @author TT.k-katayama
 * @since  2009/04/14
 *
 */
public class HaitaSeigyoLogic extends LogicBase {
	
	/**
	 * �R���X�g���N�^
	 */
	public HaitaSeigyoLogic() {
		//���N���X�̃R���X�g���N�^
		super();

	}
	
	/**
	 * �r������c�a�������W�b�N�Ǘ�
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

		String[] strReqShisakuCd = new String[3];		//���N�G�X�g�f�[�^ : ����R�[�h(�Ј��R�[�h, �N, �ǔ�)
		String[] strResParam = new String[2];			//���X�|���X�p�����[�^

		//���X�|���X�f�[�^�N���X
		RequestResponsKindBean resKind = null;
		
		try {

			String strReqZikoKbn = "";							//���N�G�X�g�f�[�^ : ���s�敪
			String strReqHaitaKbn = "";							//���N�G�X�g�f�[�^ : �r���敪
			String strReqUserId = "";							//���N�G�X�g�f�[�^ : ���[�UID
			
			String strHaitaKbn = "";								//����i�e�[�u��.�r���敪
			strResParam[0] = "";
			strResParam[1] = "";

			//���X�|���X�f�[�^�i�@�\�j����
			resKind = new RequestResponsKindBean();
			//�@�\ID�̐ݒ�
			resKind.setID(reqData.getID());
			//�e�[�u�����̐ݒ�
			resKind.addTableItem(reqData.getTableID(0));
			
			// �@�\���N�G�X�g�f�[�^�̎擾
			strReqZikoKbn = reqData.getFieldVale(0, 0, "kubun_ziko");
			strReqHaitaKbn = reqData.getFieldVale(0, 0, "kubun_haita");
//			strReqUserId = reqData.getFieldVale(0, 0, "id_user");
			strReqShisakuCd[0] = reqData.getFieldVale(0, 0, "cd_shain");
			strReqShisakuCd[1] = reqData.getFieldVale(0, 0, "nen");
			strReqShisakuCd[2] = reqData.getFieldVale(0, 0, "no_oi");
			
			//���[�U���̃��[�UID���擾
			strReqUserId = userInfoData.getId_user();

			//�@:�@�\���N�G�X�g�f�[�^�̎��s�敪�ɂ��A�������s�����ǂ����𔻒肷�� (0�F�������s��Ȃ��A1�F��������)
			if ( strReqZikoKbn.equals("0") ) {
				//�������s��Ȃ�
				
			} else if ( strReqZikoKbn.equals("1") ) {				
				//�A:�@�\���N�G�X�g�f�[�^�ɂ��A�r�����䌟��SQL�쐬�������Ăяo���A����i�e�[�u��.�r���敪���擾����
				strHaitaKbn = this.haitaSearchSQL(strReqShisakuCd);
				
				//�B:�@�\���N�G�X�g�f�[�^�̔r���敪�ɂ��A�r���ݒ�/�r�����������𐧌䂷��
				if ( strReqHaitaKbn.equals("1") ) {
					//1:�r���ݒ� �E�E�E �r������ݒ菈�����Ăяo���A�r������̐ݒ���s��
					strResParam = this.haitaSet(strReqUserId, strReqShisakuCd, strHaitaKbn);
					
				} else if ( strReqHaitaKbn.equals("0") ) {
					//0:�r������ �E�E�E �r����������������Ăяo���A�r������̉������s��	
					strResParam = this.haitaKaijo(strReqUserId, strReqShisakuCd, strHaitaKbn);
					
				}	
			}

			//�C:�r������f�[�^�p�����[�^�[�i�[�������Ăяo���A���X�|���X�f�[�^�ɐݒ肷��
			this.storageHaitaData(strResParam[0], strResParam[1], resKind.getTableItem(0));
			
		} catch (Exception e) {
			this.em.ThrowException(e, "�r������c�a�������W�b�N�Ǘ����������s���܂����B");

		} finally {
			//�ϐ��̍폜
			strReqShisakuCd = null;
			strResParam = null;

		}
		return resKind;

	}

	/**
	 * �r�����䌟��SQL�쐬
	 *  : �r�����䌟��SQL�쐬 
	 * @param strShisakuCd : ����R�[�h
	 * @return : ����i�R�[�h.�r���敪
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private String haitaSearchSQL(String[] strShisakuCd) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		String strRetHaitaKbn = "";
		StringBuffer strSQL = new StringBuffer();
		List<?> lstSearchAry = null;

		try {
			
			//�@�F�������r�����䌟�������𒊏o���A����i�e�[�u��.�r���敪���擾����SQL���쐬����B
			strSQL.append(" SELECT ");
			strSQL.append("     ISNULL(CONVERT(VARCHAR,T110.kbn_haita),'') AS kbn_haita ");
			strSQL.append(" FROM ");
			strSQL.append("     tr_shisakuhin AS T110 ");
			strSQL.append(" WHERE  ");
			strSQL.append("   T110.cd_shain=" + strShisakuCd[0]);
			strSQL.append("   AND T110.nen=" + strShisakuCd[1]);
			strSQL.append("   AND T110.no_oi=" + strShisakuCd[2]);
			
			//�A�F����i�e�[�u���������s���A����i�e�[�u��.�r���敪���擾����
			super.createSearchDB();
			lstSearchAry = this.searchDB.dbSearch(strSQL.toString());
			if ( lstSearchAry.size() >= 0 ) {
				strRetHaitaKbn = lstSearchAry.get(0).toString();
			}
			
		} catch (Exception e) {
			this.em.ThrowException(e, "�r�����䌟��SQL�쐬���������s���܂����B");
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
		
		//�B�F����i�e�[�u��.�r���敪��ԋp����B
		return strRetHaitaKbn;
	}

	/**
	 * �r������X�VSQL�쐬
	 *  : �r������X�VSQL�쐬
	 * @param strShisakuCd : ����R�[�h
	 * @param strHaitaKbn : ����i�R�[�h.�r���敪
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private void haitaUpdateSQL(String[] strShisakuCd, String strHaitaKbn ) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		StringBuffer strSQL = new StringBuffer();

		try {
			
			//�@�F�����̃f�[�^��p���A�r���f�[�^�X�V�pSQL���쐬����B
			strSQL.append(" UPDATE tr_shisakuhin ");
			strSQL.append("   SET kbn_haita=" + strHaitaKbn);
			strSQL.append(" WHERE cd_shain=" + strShisakuCd[0]);
			strSQL.append("   AND nen=" + strShisakuCd[1]);
			strSQL.append("   AND no_oi=" + strShisakuCd[2]);
			
			//�A�F�f�[�^�x�[�X�Ǘ���p���A����i�e�[�u��.�r���敪�̍X�V���s���B
			super.createExecDB();							//DB�X�V�̐���
			this.execDB.BeginTran();						//�g�����U�N�V�����J�n
			
			try{
				this.execDB.execSQL(strSQL.toString());		//SQL���s
				this.execDB.Commit();							//�R�~�b�g

			}catch(Exception e){
				this.execDB.Rollback();							//���[���o�b�N
			}
			
		} catch (Exception e) {
			this.em.ThrowException(e, "�r������X�VSQL�쐬���������s���܂����B");
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
	 * �r������ݒ�
	 *  : �r�������ݒ肵�A���X�|���X�f�[�^�ɔr������p�����[�^���i�[����B
	 * @param strUserId : ���[�UID
	 * @param strShisakuCd : ����R�[�h
	 * @param strHaitaKbn : ����i�R�[�h.�r���敪
	 * @return ���X�|���X�p�����[�^ (0:�r������, 1:���[�UID)
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private String[] haitaSet(String strUserId, String[] strShisakuCd, String strHaitaKbn ) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		String[] strRetParam = new String[2];
		strRetParam[0] = "";
		strRetParam[1] = "";

		try {
			
			//�@�F����i�e�[�u��.�r���敪�ɂ�锻��i������i�e�[�u��.�r���敪�ɂ̓��[�UID���i�[����Ă���j
			if ( strHaitaKbn.equals("") ) {
				//���@���� ����i�e�[�u��.�r���敪��NULL�̏ꍇ
				
				//�r������X�VSQL�쐬�������ďo���A����i�e�[�u��.�r���敪�ɋ@�\���N�G�X�g�f�[�^�̃��[�UID��ݒ�
				this.haitaUpdateSQL(strShisakuCd, strUserId);
				//���X�|���X�f�[�^�̔r�����ʂ�true��ݒ�
				strRetParam[0] = "true";
				//���X�|���X�f�[�^�̃��[�UID�Ƀ��N�G�X�g�f�[�^�̃��[�UID��ݒ�
				strRetParam[1] = strUserId;
				
			} else if ( strHaitaKbn.equals(strUserId) ) {
				//���@���� ����i�e�[�u��.�r���敪�����[�UID�Ɠ���̏ꍇ

//				//���X�|���X�f�[�^�̔r�����ʂ�true��ݒ�
				strRetParam[0] = "true";
				//���X�|���X�f�[�^�̔r�����ʂ�false��ݒ�
//				strRetParam[0] = "false";
				//���X�|���X�f�[�^�̃��[�UID�Ƀ��N�G�X�g�f�[�^�̃��[�UID��ݒ�
				strRetParam[1] = strUserId;
				
			} else if ( !strHaitaKbn.equals(strUserId) ) {
				//���@���� ����i�e�[�u��.�r���敪�����[�UID�ƈقȂ�ꍇ

				//���X�|���X�f�[�^�̔r�����ʂ�false��ݒ�
				strRetParam[0] = "false";
				//���X�|���X�f�[�^�̃��[�UID�Ɉ��� ����i�e�[�u��.�r���敪��ݒ�
				strRetParam[1] = strHaitaKbn;
				
			}
			
		} catch (Exception e) {
			this.em.ThrowException(e, "�r������ݒ菈�������s���܂����B");
		} finally {
		}
		
		return strRetParam;
	}
	
	/**
	 * �r���������
	 *  : �r��������������A ���X�|���X�f�[�^�ɔr������p�����[�^���i�[����B
	 * @param strUserId : ���[�UID
	 * @param strShisakuCd : ����R�[�h
	 * @param strHaitaKbn : ����i�R�[�h.�r���敪
	 * @return ���X�|���X�p�����[�^ (0:�r������, 1:���[�UID)
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private String[] haitaKaijo(String strUserId, String[] strShisakuCd, String strHaitaKbn ) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		String[] strRetParam = new String[2];
		strRetParam[0] = "";
		strRetParam[1] = "";

		try {
			
			//�@�F����i�e�[�u��.�r���敪�ɂ�锻��
			if ( strHaitaKbn.equals(strUserId) ) {
				//���@���� ����i�e�[�u��.�r���敪�����[�UID�Ɠ���̏ꍇ
				
				//�r������X�VSQL�쐬�������ďo���A����i�e�[�u��.�r���敪��NULL��ݒ�
				this.haitaUpdateSQL(strShisakuCd, "NULL");
				//���X�|���X�f�[�^�̔r�����ʂ�true��ݒ�
				strRetParam[0] = "true";
				//���X�|���X�f�[�^�̃��[�UID��0��ݒ�
				strRetParam[1] = "0";
				
			} else {
				//���@���� ����i�e�[�u��.�r���敪�����[�UID�ƈقȂ�ꍇ�B�܂��́A�l����̏ꍇ
				
				//�������s��Ȃ��B
				
			}
			
		} catch (Exception e) {
			this.em.ThrowException(e, "�r������������������s���܂����B");
		} finally {
		}
		
		return strRetParam;		
	}
	
	/**
	 * �r������f�[�^�p�����[�^�[�i�[
	 *  : �r������f�[�^�������X�|���X�f�[�^�֊i�[����B 
	 * @param strHaitaKekka : �r������
	 * @param strUserId : ���[�UID
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private void storageHaitaData(String strHaitaKekka, String strUserId, RequestResponsTableBean resTable) 
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
			resTable.addFieldVale(0, "kekka_haita", strHaitaKekka);
			resTable.addFieldVale(0, "id_user", strUserId);
			
		} catch (Exception e) {
			this.em.ThrowException(e, "�r������f�[�^�p�����[�^�[�i�[���������s���܂����B");

		} finally {

		}
		
	}
	
}
