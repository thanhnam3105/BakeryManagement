package jp.co.blueflag.shisaquick.srv.logic_genka;

import jp.co.blueflag.shisaquick.srv.base.LogicBase;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsKindBean;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsTableBean;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ConstManager;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;
import java.util.List;

/**
 * �yQP@00342�z
 * �S���҃}�X�^�����e�i�c�Ɓj�@���������i�c�ƕ����̂݁j
 *  �@�\ID�FFGEN2070
 *  
 * @author Nishigawa
 * @since  2011/01/28
 */
public class FGEN2070_Logic extends LogicBase{
	
	/**
	 * �S���҃}�X�^�����e�i�c�Ɓj�@���������i�c�ƕ����̂݁j
	 * : �C���X�^���X����
	 */
	public FGEN2070_Logic() {
		//���N���X�̃R���X�g���N�^
		super();

	}
	
	//--------------------------------------------------------------------------------------------------------------
	//
	//                                                                     ExecLogic�i�����J�n�j 
	//
	//--------------------------------------------------------------------------------------------------------------
	
	/**
	 * �S���҃}�X�^�����e�i�c�Ɓj�@���������i�c�ƕ����̂݁j
	 *  :���������i�c�ƕ����̂݁j�����擾����B
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
			String strTblNm = "table";	
			
			//�f�[�^�擾SQL�쐬
			strSqlBuf = this.createGenkaKihonSQL(reqData);
			
			//���ʃN���X�@�f�[�^�x�[�X������p����SQL���s
			super.createSearchDB();
			lstRecset = searchDB.dbSearch(strSqlBuf.toString());
			
			//���X�|���X�f�[�^�Ƀe�[�u���ǉ�
			resKind.addTableItem(strTblNm);
			
			//�ǉ������e�[�u���փ��R�[�h�i�[
			this.storageGenkaKihon(lstRecset, resKind.getTableItem(strTblNm));
			
		} catch (Exception e) {
			this.em.ThrowException(e, "�S���҃}�X�^�����e�i�c�Ɓj�@���������i�c�ƕ����̂݁j���������s���܂����B");
			
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
			//���N�G�X�g����f�[�^���o
			String cd_kaisha = reqData.getFieldVale(0, 0, "cd_kaisha");
// �yQP@10713�z2011/10/28 TT H.SHIMA -ADD Start
			int eigyo_kengen = Integer.parseInt(reqData.getFieldVale(0, 0, "eigyo_kengen"));
// �yQP@10713�z2011/10/28 TT H.SHIMA -ADD End
			
			//SQL���̍쐬
			strSql.append(" SELECT    ");
			strSql.append(" 	cd_busho   ");
			strSql.append(" 	,nm_busho   ");
			strSql.append(" FROM    ");
			strSql.append(" 	ma_busho   ");
			strSql.append(" WHERE   ");
			strSql.append(" 	cd_kaisha = "+cd_kaisha);
			strSql.append(" 	AND flg_eigyo = 1   ");
// �yQP@10713�z2011/10/28 TT H.SHIMA -ADD Start
			if(eigyo_kengen == 2){
				strSql.append(" 	AND flg_honbu = 1   ");
			}
// �yQP@10713�z2011/10/28 TT H.SHIMA -ADD End
			
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
	 *  : ���X�|���X�f�[�^�֊i�[����B
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
			//�c�Ɓi��ʁj�����R�[�h�擾
			String strEigyoIppan = 
				ConstManager.getConstValue(ConstManager.Category.�ݒ�,"EIGYO_KENGEN_IPPAN");
			
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
				resTable.addFieldVale(i, "cd_busho", toString(items[0],""));
				resTable.addFieldVale(i, "nm_busho", toString(items[1],""));
			}

		} catch (Exception e) {
			this.em.ThrowException(e, "");
			
		} finally {

		}
		
	}
	
}
