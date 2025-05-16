package jp.co.blueflag.shisaquick.srv.logic;

import jp.co.blueflag.shisaquick.srv.base.LogicBase;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsKindBean;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsTableBean;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;
import jp.co.blueflag.shisaquick.srv.common.ExceptionManager.ExceptionKind;

import java.util.List;

/**
 * 
 * �����}�X�^�����e�F������񌟍�DB����
 *  : �����}�X�^�����e�F����������������B
 * @author jinbo
 * @since  2009/04/09
 */
public class KengenDataSearchLogic extends LogicBase{
	
	/**
	 * �����}�X�^�����e�F�����񌟍�DB���� 
	 * : �C���X�^���X����
	 */
	public KengenDataSearchLogic() {
		//���N���X�̃R���X�g���N�^
		super();

	}

	/**
	 * �����}�X�^�����e�F�������擾SQL�쐬
	 *  : ���������擾����SQL���쐬�B
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

		StringBuffer strSql = new StringBuffer();
		List<?> lstRecset = null;

		//���X�|���X�f�[�^�N���X
		RequestResponsKindBean resKind = null;
		
		try {
			//���X�|���X�f�[�^�i�@�\�j����
			resKind = new RequestResponsKindBean();

			//SQL���̍쐬
			strSql = genryoDataCreateSQL(reqData, strSql);
			
			//SQL�����s
			super.createSearchDB();
			lstRecset = searchDB.dbSearch(strSql.toString());
			
			//�������ʂ��Ȃ��ꍇ
			if (lstRecset.size() == 0){
				em.ThrowException(ExceptionKind.�x��Exception,"W000401", strSql.toString(), "", "");
			}

			//�@�\ID�̐ݒ�
			String strKinoId = reqData.getID();
			resKind.setID(strKinoId);

			//�e�[�u�����̐ݒ�
			String strTableNm = reqData.getTableID(0);
			resKind.addTableItem(strTableNm);

			//���X�|���X�f�[�^�̌`��
			storageGenryouData(lstRecset, resKind.getTableItem(0));
			
		} catch (Exception e) {
			this.em.ThrowException(e, "�����f�[�^���������Ɏ��s���܂����B");
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
		return resKind;
	}
	
	/**
	 * �������擾SQL�쐬
	 *  : ���������擾����SQL���쐬�B
	 * @param reqData�F���N�G�X�g�f�[�^
	 * @param strSql�F��������SQL
	 * @return strSql�F��������SQL
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private StringBuffer genryoDataCreateSQL(RequestResponsKindBean reqData, StringBuffer strSql) 
		throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		try {
			String strKengenCd = null;
			String dataId = null;
			
			//�����R�[�h�̎擾
			strKengenCd = reqData.getFieldVale(0, 0, "cd_kengen");

			//�����f�[�^ID�擾
			for (int i = 0; i < userInfoData.getId_gamen().size(); i++) {
				if (userInfoData.getId_gamen().get(i).toString().equals("80")){
					//�����}�X�^�����e�i���X��ʂ̃f�[�^ID��ݒ�
					dataId = userInfoData.getId_data().get(i).toString();
				}
			}
			
			//SQL���̍쐬
			strSql.append("SELECT");
			strSql.append("  M102.cd_kengen");
			strSql.append(" ,M102.nm_kengen");
			strSql.append(" ,ISNULL(M103.nm_kino,'') as nm_kino");
			strSql.append(" ,ISNULL(M103.id_gamen,'') as id_gamen");
			strSql.append(" ,ISNULL(M103.id_kino,'') as id_kino");
			strSql.append(" ,ISNULL(M103.id_data,'') as id_data");
			strSql.append(" ,ISNULL(M103.biko,'') as biko");
			strSql.append(" FROM ma_kengen M102");
			strSql.append("      LEFT JOIN ma_kinou M103");
			strSql.append("      ON M102.cd_kengen = M103.cd_kengen");
			strSql.append(" WHERE M102.cd_kengen = ");
			strSql.append(strKengenCd);
			strSql.append(" ORDER BY ");
			strSql.append(" M103.id_gamen ");
			
		} catch (Exception e) {
			this.em.ThrowException(e, "�����f�[�^���������Ɏ��s���܂����B");
		} finally {

		}
		return strSql;
	}

	/**
	 * �����}�X�^�����e�F�������p�����[�^�[�i�[
	 *  : �����������X�|���X�f�[�^�֊i�[����B
	 * @param lstGenryouData : �������ʏ�񃊃X�g
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private void storageGenryouData(List<?> lstGenryouData, RequestResponsTableBean resTable) 
		throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try {
			
			for (int i = 0; i < lstGenryouData.size(); i++) {

				//�������ʂ̊i�[
				resTable.addFieldVale(i, "flg_return", "true");
				resTable.addFieldVale(i, "msg_error", "");
				resTable.addFieldVale(i, "no_errmsg", "");
				resTable.addFieldVale(i, "nm_class", "");
				resTable.addFieldVale(i, "cd_error", "");
				resTable.addFieldVale(i, "msg_system", "");
				
				Object[] items = (Object[]) lstGenryouData.get(i);

				//���ʂ����X�|���X�f�[�^�Ɋi�[
				resTable.addFieldVale(i, "cd_kengen", items[0].toString());
				resTable.addFieldVale(i, "nm_kengen", items[1].toString());
				resTable.addFieldVale(i, "nm_kino", items[2].toString());
				resTable.addFieldVale(i, "id_gamen", items[3].toString());
				resTable.addFieldVale(i, "id_kino", items[4].toString());
				resTable.addFieldVale(i, "id_data", items[5].toString());
				resTable.addFieldVale(i, "biko", items[6].toString());
				
			}
			
		} catch (Exception e) {
			this.em.ThrowException(e, "�����f�[�^���������Ɏ��s���܂����B");

		} finally {

		}

	}
	
}
