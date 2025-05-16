package jp.co.blueflag.shisaquick.srv.logic;


import java.util.List;

import jp.co.blueflag.shisaquick.srv.base.LogicBase;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsKindBean;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsTableBean;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;
import jp.co.blueflag.shisaquick.srv.common.ExceptionManager.ExceptionKind;

/**
 * QP@10181_No.42_49_72
 * �ySA930�z JWS���e���������i�������P�t�^�C�v�@�H�������j�c�a�����̎���
 * 
 * @author TT.Nishigawa
 * @since 2011/05/30
 *
 */
public class SA930 extends LogicBase {

	/**
	 * �R���X�g���N�^
	 */
	public SA930() {
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
			,UserInfoData _userInfoData
			) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		//���[�U�[���ޔ�
		userInfoData = _userInfoData;

		//���N�G�X�g�f�[�^
		String strReqKinoId = null;
		String strReqTableNm = null;
		
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
			
			//SQL���̍쐬
			strSql.append(" SELECT ");
			strSql.append(" 	biko ");
			strSql.append(" FROM ");
			strSql.append(" 	ma_literal ");
			strSql.append(" WHERE ");
			strSql.append(" 	cd_category = 'K_kote_ptn' ");
			strSql.append(" 	and value1 = 1 ");
			
			//�A�F�f�[�^�x�[�X������p���A���e�����}�X�^�f�[�^���擾����B
			super.createSearchDB();
			lstRecset = this.searchDB.dbSearch(strSql.toString());
			
			//�f�[�^���擾�ł��Ȃ������ꍇ
			if(lstRecset.size() == 0){
				//���e�����}�X�^�̐ݒ肪�������Ȃ�
				em.ThrowException(ExceptionKind.���Exception, "E000408", "", "", "");
			}
			
			//���l���R�[�h�擾
			String biko = toString(lstRecset.get(0));
			
			//���l�ɉ����ݒ肳��Ă��Ȃ��ꍇ
			if(biko.length() == 0){
				//���e�����}�X�^�̐ݒ肪�������Ȃ�
				em.ThrowException(ExceptionKind.���Exception, "E000408", "", "", "");
			}
			
			//���l�ɐݒ肳�ꂽ���e�����R�[�h�擾
			String[] aryBiko = biko.split(",");
			
			//�������P�t�^�C�v�̍H�������擾�pSQL����
			strSql = new StringBuffer();
			//SQL���̍쐬
			strSql.append("SELECT cd_category, cd_literal, nm_literal,");
			strSql.append(" ISNULL(value1,0) as value1, ISNULL(value2,0) as value2,");
			strSql.append(" no_sort, ISNULL(cd_group,0) as cd_group");
			strSql.append(" ,biko");
			strSql.append(" FROM ma_literal");			
			strSql.append(" WHERE cd_category = 'K_kote'");
			strSql.append(" AND cd_��iteral IN( ");
			for(int i = 0; i < aryBiko.length; i++){
				if(i == 0){
					strSql.append("'" + aryBiko[i] + "'");
				}
				else{
					strSql.append(",'" + aryBiko[i] + "'");
				}
			}
			strSql.append(" )");
			strSql.append(" ORDER BY no_sort");
			
			//�f�[�^�x�[�X������p���A���e�����}�X�^�f�[�^���擾����B
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
					resTable.addFieldVale("rec" + i, "biko", toString(items[7]));
	
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
