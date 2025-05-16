package jp.co.blueflag.shisaquick.srv.logic_genka;

import jp.co.blueflag.shisaquick.srv.base.LogicBase;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsKindBean;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsTableBean;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;
import java.util.List;

/**
 * �yQP@00342�z
 * �X�e�[�^�X�N���A�@���݃X�e�[�^�X�擾
 *  : ���݃X�e�[�^�X�����擾����B
 *  �@�\ID�FFGEN2010
 *  
 * @author Nishigawa
 * @since  2011/01/25
 */
public class FGEN2020_Logic extends LogicBase{
	
	/**
	 * �X�e�[�^�X�N���A�@���݃X�e�[�^�X�擾����
	 * : �C���X�^���X����
	 */
	public FGEN2020_Logic() {
		//���N���X�̃R���X�g���N�^
		super();

	}
	
	//--------------------------------------------------------------------------------------------------------------
	//
	//                                                                     ExecLogic�i�����J�n�j 
	//
	//--------------------------------------------------------------------------------------------------------------
	
	/**
	 * �X�e�[�^�X�N���A�@���݃X�e�[�^�X�擾
	 *  : ���݃X�e�[�^�X�����擾����B
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
			
			//���X�|���X�f�[�^�̌`��
			this.genkaKihonSetting(resKind, reqData);
			
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
	
	//--------------------------------------------------------------------------------------------------------------
	//
	//                                                         DataSetting�i���X�|���X�f�[�^�̌`���j 
	//
	//--------------------------------------------------------------------------------------------------------------
	/**
	 * ���X�|���X�f�[�^�̌`��
	 * @param resKind : ���X�|���X�f�[�^
	 * @param reqData : ���N�G�X�g�f�[�^
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 *  
	 * @author TT.Nishigawa
	 * @since  2009/10/21
	 */
	private void genkaKihonSetting(
			
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
			String strTblNm = "kihon";	
			
			//�@�f�[�^�擾SQL�쐬
			strSqlBuf = this.createGenkaKihonSQL(reqData);
			
			//�A���ʃN���X�@�f�[�^�x�[�X������p����SQL���s
			super.createSearchDB();
			lstRecset = searchDB.dbSearch(strSqlBuf.toString());
			
			//�B���X�|���X�f�[�^�Ƀe�[�u���ǉ�
			resKind.addTableItem(strTblNm);
			
			//�C�ǉ������e�[�u���փ��R�[�h�i�[
			this.storageGenkaKihon(lstRecset, resKind.getTableItem(strTblNm));
			
		} catch (Exception e) {
			this.em.ThrowException(e, "�X�e�[�^�X�����@�f�[�^�������������s���܂����B");
			
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
	
	
	//--------------------------------------------------------------------------------------------------------------
	//
	//                                                                  createSQL�iSQL�������j 
	//
	//--------------------------------------------------------------------------------------------------------------
	/**
	 * �f�[�^�擾SQL�쐬
	 * @param reqData�F���N�G�X�g�f�[�^
	 * @param strSql�F��������SQL
	 * @return strSql�F��������SQL
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private StringBuffer createGenkaKihonSQL(
			
			RequestResponsKindBean reqData
			)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		//SQL�i�[�p
		StringBuffer strSql = new StringBuffer();
		
		try {
			//����R�[�h�ݒ�i���N�G�X�g�Ɏ���R�[�h���Ȃ��ꍇ�AUSERINFO��莎��R�[�h���擾�j
			if( toString(reqData.getFieldVale(0, 0, "cd_shain")) == "" ){
				
				//����R�[�h_�Ј�CD�A�N�A�ǔԁ@�ݒ�
				reqData.setFieldVale(0, 0, "cd_shain", toString(userInfoData.getMovement_condition().get(0)) );
				reqData.setFieldVale(0, 0, "nen", toString(userInfoData.getMovement_condition().get(1)) );
				reqData.setFieldVale(0, 0, "no_oi", toString(userInfoData.getMovement_condition().get(2)) );
				reqData.setFieldVale(0, 0, "no_eda", toString(userInfoData.getMovement_condition().get(3)) );
				
			}
			String strCdShain = reqData.getFieldVale(0, 0, "cd_shain");
			String strNen = reqData.getFieldVale(0, 0, "nen");
			String strNoOi = reqData.getFieldVale(0, 0, "no_oi");
			String strNoEda = reqData.getFieldVale(0, 0, "no_eda");
			
			//SQL���̍쐬
			strSql.append(" SELECT cd_shain  ");
			strSql.append("       ,nen  ");
			strSql.append("       ,no_oi  ");
			strSql.append("       ,no_eda  ");
			strSql.append("       ,st_kenkyu  ");
			strSql.append("       ,st_seisan  ");
			strSql.append("       ,st_gensizai  ");
			strSql.append("       ,st_kojo  ");
			strSql.append("       ,st_eigyo  ");
			strSql.append("       ,id_toroku  ");
			strSql.append("       ,dt_toroku  ");
			strSql.append("       ,id_koshin  ");
			strSql.append("       ,dt_koshin  ");
			strSql.append(" FROM tr_shisan_status  ");
			strSql.append(" WHERE   ");
			strSql.append(" 	cd_shain = " + strCdShain );
			strSql.append(" 	AND nen = " + strNen );
			strSql.append(" 	AND no_oi = " + strNoOi );
			strSql.append(" 	AND no_eda = " + strNoEda );

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
		return strSql;
	}
	
	//--------------------------------------------------------------------------------------------------------------
	//
	//                                                                  storageData�i�p�����[�^�[�i�[�j 
	//
	//--------------------------------------------------------------------------------------------------------------
	/**
	 * �p�����[�^�[�i�[
	 *  : �X�e�[�^�X������ʂւ̃��X�|���X�f�[�^�֊i�[����B
	 * @param lstGenkaHeader : �������ʏ�񃊃X�g
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private void storageGenkaKihon(
			
			  List<?> lstGenkaHeader
			, RequestResponsTableBean resTable
			) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try {
			for (int i = 0; i < lstGenkaHeader.size(); i++) {

				//�������ʂ̊i�[
				resTable.addFieldVale(i, "flg_return", "true");
				resTable.addFieldVale(i, "msg_error", "");
				resTable.addFieldVale(i, "no_errmsg", "");
				resTable.addFieldVale(i, "nm_class", "");
				resTable.addFieldVale(i, "cd_error", "");
				resTable.addFieldVale(i, "msg_system", "");
				
				Object[] items = (Object[]) lstGenkaHeader.get(i);

				//���ʂ����X�|���X�f�[�^�Ɋi�[
				resTable.addFieldVale(i, "cd_shain", toString(items[0],""));
				resTable.addFieldVale(i, "nen", toString(items[1],""));
				resTable.addFieldVale(i, "no_oi", toString(items[2],""));
				resTable.addFieldVale(i, "no_eda", toString(items[3],""));
				resTable.addFieldVale(i, "st_kenkyu", toString(items[4],""));
				resTable.addFieldVale(i, "st_seisan", toString(items[5],""));
				resTable.addFieldVale(i, "st_gensizai", toString(items[6],""));
				resTable.addFieldVale(i, "st_kojo", toString(items[7],""));
				resTable.addFieldVale(i, "st_eigyo", toString(items[8],""));
			}

		} catch (Exception e) {
			this.em.ThrowException(e, "");
			
		} finally {

		}
		
	}
	
}
