package jp.co.blueflag.shisaquick.srv.commonlogic;

import jp.co.blueflag.shisaquick.srv.base.LogicBase;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsKindBean;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsTableBean;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionManager;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;

import java.util.List;

/**
 * 
 * JWS�p���e������񌟍�DB����
 *  : JWS�p���e������������������B
 *  
 * @author TT.katayama
 * @since  2009/04/07
 * 
 */
public class JWSLiteralSearchLogic extends LogicBase{
	
	/**
	 * JWS�p���e������񌟍�DB�����p�R���X�g���N�^ 
	 * : �C���X�^���X����
	 */
	public JWSLiteralSearchLogic() {
		//���N���X�̃R���X�g���N�^
		super();

	}

	/**
	 * ���e�������擾���W�b�N���s
	 * @param reqData : �@�\���N�G�X�g�f�[�^
	 * @param strCd_Categori : �J�e�S���R�[�h
	 * @param userInfoData : ���[�U�[���
	 * @return ���X�|���X�f�[�^�i�@�\�j
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	public RequestResponsKindBean ExecLogic(
			RequestResponsKindBean reqData
			, String strCd_Categori
			,UserInfoData _userInfoData
			) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		//���[�U�[���ޔ�
		userInfoData = _userInfoData;

		//���N�G�X�g�f�[�^
		String strReqKinoId = null;
		String strReqTableNm = null;
//		String strReqUserId = null;
		String strReqGamenId = null;
		//���[�U���
		String strGroupCd = null;
		String strDataId = null;
		String strKinoId = null;
		
		StringBuffer strSql = new StringBuffer();
		List<?> lstRecset = null;

		//���X�|���X�f�[�^�N���X
		RequestResponsKindBean resKind = null;
		
		try {
			//���X�|���X�f�[�^�i�@�\�j����
			resKind = new RequestResponsKindBean();

			//�@�F�@�\���N�G�X�g�f�[�^�N���X��p���A���e�����}�X�^�����擾����SQL���쐬����B
			
			//�@�\ID�̐ݒ�
			strReqKinoId = reqData.getID();
			resKind.setID(strReqKinoId);

			//�e�[�u�����̐ݒ�
			strReqTableNm = reqData.getTableID(0);
			resKind.addTableItem(strReqTableNm);
			
			//�@�\���N�G�X�g�f�[�^��胆�[�UID�Ɖ��ID���擾
//			strReqUserId = reqData.getFieldVale(0, 0, "id_user");
			strReqGamenId = reqData.getFieldVale(0, 0, "id_gamen");

			//���[�U���̃��[�UID���擾
//			strReqUserId = UserInfoData.getId_user();			
			//���[�U���̃O���[�v�R�[�h���擾
			strGroupCd = userInfoData.getCd_group();
			
			//�����f�[�^ID�擾
			for (int i = 0; i < userInfoData.getId_gamen().size(); i++) {
				if (userInfoData.getId_gamen().get(i).toString().equals(strReqGamenId)){
					//�@�\ID��ݒ�
					strKinoId = userInfoData.getId_kino().get(i).toString(); 
					//�f�[�^ID��ݒ�
					strDataId = userInfoData.getId_data().get(i).toString();
				}
			}
			
			//���ID�y�ы@�\ID�̔���
			if ( strReqGamenId == null ) {
				//��������
			} else if ( strReqGamenId.equals("100") ) {
				//���ID:����f�[�^���(�ڍ�)
				if ( !(strKinoId.equals("10") || strKinoId.equals("20") ) ) {
					this.em.ThrowException(ExceptionManager.ExceptionKind.�x��Exception, "W000400", strKinoId, "", "");
				}
			} else if ( strReqGamenId.equals("110") ) {
				//���ID:����f�[�^���(�V�K)
				if ( !strKinoId.equals("30") ) {
					this.em.ThrowException(ExceptionManager.ExceptionKind.�x��Exception, "W000400", strKinoId, "", "");
				}
			} else if ( strReqGamenId.equals("120") ) {
				//���ID:����f�[�^���(���@�R�s�[)
				if ( !strKinoId.equals("20") ) {
					this.em.ThrowException(ExceptionManager.ExceptionKind.�x��Exception, "W000400", strKinoId, "", "");
				}
			}
			
			//SQL���̍쐬
			strSql.append("SELECT cd_category, cd_literal, nm_literal,");
			strSql.append(" ISNULL(value1,0) as value1, ISNULL(value2,0) as value2,");
			strSql.append(" no_sort, ISNULL(cd_group,0) as cd_group");
			
//2011/05/30�@�yQP@10181_No.42_49_72�z�H���p�^�[������@TT.NISHIGAWA�@start
			strSql.append(" ,biko");
//2011/05/30�@�yQP@10181_No.42_49_72�z�H���p�^�[������@TT.NISHIGAWA�@end
			
			strSql.append(" FROM ma_literal");			
			//�����@�J�e�S���R�[�h�����������ɐݒ�
			strSql.append(" WHERE cd_category = '" + strCd_Categori + "'");
			strSql.append(" AND ( cd_group IS NULL ");
			
			//��������
			if ( strDataId == null ) {
				
				//��������
				
//2011/05/19�@�yQP@10181_No.11�z����R�s�[���[�h�����@TT.NISHIGAWA�@start	
			//} else if ( strDataId.equals("1") ) {
			} else if ( strDataId.equals("1") || strDataId.equals("5") ) {
//2011/05/19�@�yQP@10181_No.11�z����R�s�[���[�h�����@TT.NISHIGAWA�@start
				
				//�f�[�^ID = 1 : ����O���[�v���i�{�l+���ذ�ް�ȏ�j
				strSql.append(" OR cd_group = '" + strGroupCd + "'");
				
			} else if ( strDataId.equals("2") ) {
				
				//�f�[�^ID = 2 : ����O���[�v���S�����
				strSql.append(" OR cd_group = '" + strGroupCd + "'");
				
			} else if ( strDataId.equals("3") ) {
				
				//�f�[�^ID = 3 : ����O���[�v���S����Њ��i�{�l+���ذ�ް�ȏ�j
				strSql.append(" OR cd_group = '" + strGroupCd + "'");
				
			} else if ( strDataId.equals("4") ) {
				
				//�f�[�^ID = 4 : ���H�ꕪ
				//��������
				
			} else if ( strDataId.equals("9") ) {
				//�f�[�^ID = 9 : �S��
				strSql.append(" OR cd_group IS NOT NULL");
				
			}
			strSql.append(" ) ");
			strSql.append(" ORDER BY no_sort");
			
			//�A�F�f�[�^�x�[�X������p���A���e�����}�X�^�f�[�^���擾����B
			super.createSearchDB();
			lstRecset = this.searchDB.dbSearch(strSql.toString());
			
			//�B�F���e�����p�����[�^�[�i�[���\�b�h���ďo���A�@�\���X�|���X�f�[�^���`������B
			storageLiteralInfo(lstRecset, resKind.getTableItem(0));
			
		} catch (Exception e) {
			this.em.ThrowException(e, "JWS�p���e�������擾���������s���܂����B");
			
		} finally {
			//���X�g�̔j��
			removeList(lstRecset);
			if (searchDB != null) {
				//�Z�b�V�����̃N���[�Y
				searchDB.Close();
				searchDB = null;
			}
			//�ϐ��̍폜
			strSql = null;

		}
		//�C�F�@�\���X�|���X�f�[�^��ԋp����B
		return resKind;
	}

	/**
	 * ���e�����p�����[�^�[�i�[
	 *  : ���e�����}�X�^���i�[
	 * @param lstSearchData : �������ʏ�񃊃X�g
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	public void storageLiteralInfo(List<?> lstSearchData, RequestResponsTableBean resTable) 
			throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		//�@�F�����@�������ʏ�񃊃X�g�ɕێ����Ă���e�p�����[�^�[���@�\���X�|���X�f�[�^�֊i�[����B
		try {

			if ( lstSearchData.size() != 0 ) {
				for (int i = 0; i < lstSearchData.size(); i++) {

					//�������ʂ̊i�[
					resTable.addFieldVale("rec" + i, "flg_return", "true");
					resTable.addFieldVale("rec" + i, "msg_error", "");
					resTable.addFieldVale("rec" + i, "no_errmsg", "");
					resTable.addFieldVale("rec" + i, "nm_class", "");
					resTable.addFieldVale("rec" + i, "cd_error", "");
					resTable.addFieldVale("rec" + i, "msg_system", "");
	
					Object[] items = (Object[]) lstSearchData.get(i);
	
					//���ʂ����X�|���X�f�[�^�Ɋi�[
					resTable.addFieldVale("rec" + i, "cd_category", items[0].toString());
					resTable.addFieldVale("rec" + i, "cd_literal", items[1].toString());
					resTable.addFieldVale("rec" + i, "nm_literal", items[2].toString());
					resTable.addFieldVale("rec" + i, "value1", items[3].toString());
					resTable.addFieldVale("rec" + i, "value2", items[4].toString());
					resTable.addFieldVale("rec" + i, "no_sort", items[5].toString());
					resTable.addFieldVale("rec" + i, "cd_group", items[6].toString());
					
//2011/05/30�@�yQP@10181_No.42_49_72�z�H���p�^�[������@TT.NISHIGAWA�@start
					resTable.addFieldVale("rec" + i, "biko", toString(items[7]));
//2011/05/30�@�yQP@10181_No.42_49_72�z�H���p�^�[������@TT.NISHIGAWA�@start
	
				}
			} else {

				//�������ʂ̊i�[
				resTable.addFieldVale("rec0", "flg_return", "true");
				resTable.addFieldVale("rec0", "msg_error", "");
				resTable.addFieldVale("rec0", "no_errmsg", "");
				resTable.addFieldVale("rec0", "nm_class", "");
				resTable.addFieldVale("rec0", "cd_error", "");
				resTable.addFieldVale("rec0", "msg_system", "");

				//���ʂ����X�|���X�f�[�^�Ɋi�[
				resTable.addFieldVale("rec0", "cd_category", "");
				resTable.addFieldVale("rec0", "cd_literal", "");
				resTable.addFieldVale("rec0", "nm_literal", "");
				resTable.addFieldVale("rec0", "value1", "");
				resTable.addFieldVale("rec0", "value2", "");
				resTable.addFieldVale("rec0", "no_sort", "");
				resTable.addFieldVale("rec0", "cd_group", "");
				resTable.addFieldVale("rec0", "biko", "");

			}

		} catch (Exception e) {
			this.em.ThrowException(e, "���e�����}�X�^�������ʊi�[���������s���܂����B");

		} finally {

		}

	}
	
}
