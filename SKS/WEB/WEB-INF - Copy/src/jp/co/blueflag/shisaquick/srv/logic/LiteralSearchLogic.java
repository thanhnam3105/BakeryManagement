package jp.co.blueflag.shisaquick.srv.logic;

import jp.co.blueflag.shisaquick.srv.base.LogicBase;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsKindBean;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsTableBean;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;

import java.util.List;

/**
 * 
 * �R���{�p�F���e������񌟍�DB����
 *  : �R���{�p�F���e����������������B
 * @author jinbo
 * @since  2009/04/07
 */
public class LiteralSearchLogic extends LogicBase{
	
	/**
	 * �R���{�p�F�J�e�S����񌟍�DB�����p�R���X�g���N�^ 
	 * : �C���X�^���X����
	 */
	public LiteralSearchLogic() {
		//���N���X�̃R���X�g���N�^
		super();

	}

	/**
	 * �R���{�p�F���e�������擾SQL�쐬
	 *  : ���e�����R���{�{�b�N�X�����擾����SQL���쐬�B
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
			strSql = createSQL(reqData, strSql);
			
			//SQL�����s
			super.createSearchDB();
			lstRecset = searchDB.dbSearch(strSql.toString());
			
			//�@�\ID�̐ݒ�
			String strKinoId = reqData.getID();
			resKind.setID(strKinoId);

			//�e�[�u�����̐ݒ�
			String strTableNm = ((RequestResponsTableBean) reqData.GetItem(0)).getID();
			resKind.addTableItem(strTableNm);

			//���X�|���X�f�[�^�̌`��
			storageLiteralCmb(lstRecset, resKind.getTableItem(0));
			
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
			strSql = null;

		}
		return resKind;
	}
	
	/**
	 * ���e�����擾SQL�쐬
	 *  : ���e�������擾����SQL���쐬�B
	 * @param reqData�F���N�G�X�g�f�[�^
	 * @param strSql�F��������SQL
	 * @return strSql�F��������SQL
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private StringBuffer createSQL(RequestResponsKindBean reqData, StringBuffer strSql) 
		throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		try {
			String categoryCd = null;
			String userId = null;
			String gamenId = null;
			// ADD 2013/11/8 QP@30154 okano start
			String kinoId = null;
			// ADD 2013/11/8 QP@30154 okano end
			String dataId = null;

			//�J�e�S���R�[�h�̎擾
			categoryCd = reqData.getFieldVale(0, 0, "cd_category");
			//���[�UID�̎擾
			userId = reqData.getFieldVale(0, 0, "id_user");
			//���ID�̎擾
			gamenId = reqData.getFieldVale(0, 0, "id_gamen");

			//�����f�[�^ID�擾
			for (int i = 0; i < userInfoData.getId_gamen().size(); i++) {
				if (userInfoData.getId_gamen().get(i).toString().equals(gamenId)){
					// ADD 2013/11/8 QP@30154 okano start
					//�@�\ID��ݒ�
					kinoId = userInfoData.getId_kino().get(i).toString();
					// ADD 2013/11/8 QP@30154 okano end
					//�f�[�^ID��ݒ�
					dataId = userInfoData.getId_data().get(i).toString();
				}
			}
			
			//SQL���̍쐬
			strSql.append("SELECT cd_literal, nm_literal");
			strSql.append(" FROM ma_literal ");
			// DEL 2013/11/8 QP@30154 okano start
//				strSql.append(" WHERE cd_category = '");
//				strSql.append(categoryCd);
//				strSql.append("' ");
			// DEL 2013/11/8 QP@30154 okano end

			//���e�����}�X�^�����e�i���X���
			if (gamenId.equals("60") || gamenId.equals("65")) {
				if (dataId == null) {
					//�����f�[�^ID�擾
					for (int i = 0; i < userInfoData.getId_gamen().size(); i++) {
						if (userInfoData.getId_gamen().get(i).toString().equals("65")){
							// ADD 2013/11/8 QP@30154 okano start
							//�@�\ID��ݒ�
							kinoId = userInfoData.getId_kino().get(i).toString();
							// ADD 2013/11/8 QP@30154 okano end
							//�f�[�^ID��ݒ�
							dataId = userInfoData.getId_data().get(i).toString();
						}
					}
				}
				
				// ADD 2013/11/8 QP@30154 okano start
				if(kinoId.equals("10") || kinoId.equals("20")){
				// ADD 2013/11/8 QP@30154 okano end
				if(dataId.equals("1")) {	//����O���[�v
					// ADD 2013/11/8 QP@30154 okano start
					strSql.append(" WHERE cd_category = '");
					strSql.append(categoryCd);
					strSql.append("' ");
					// ADD 2013/11/8 QP@30154 okano end
					strSql.append(" AND (cd_group IS NULL");
					strSql.append(" OR cd_group = ");
					strSql.append(userInfoData.getCd_group());
					strSql.append(" ) ");
				} else if (dataId.equals("9")) {	//�S��
					//�����Ȃ�
					// ADD 2013/11/8 QP@30154 okano start
					strSql.append(" LEFT JOIN ma_group M1 ");
					strSql.append(" ON M1.cd_group = ma_literal.cd_group ");
					strSql.append(" WHERE cd_category = '");
					strSql.append(categoryCd);
					strSql.append("' ");
					strSql.append(" AND (ma_literal.cd_group IS NULL");
					strSql.append(" OR M1.cd_kaisha = ");
					strSql.append(userInfoData.getCd_kaisha());
					strSql.append(" ) ");
					// ADD 2013/11/8 QP@30154 okano start
				}
				// ADD 2013/11/8 QP@30154 okano start
				} else {	//�V�X�e���Ǘ���
					strSql.append(" WHERE cd_category = '");
					strSql.append(categoryCd);
					strSql.append("' ");
				}
				// ADD 2013/11/8 QP@30154 okano end
			}
			strSql.append(" ORDER BY no_sort");
			
		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
		return strSql;
	}

	/**
	 * �R���{�p�F���e�����p�����[�^�[�i�[
	 *  : ���e�����R���{�{�b�N�X�������X�|���X�f�[�^�֊i�[����B
	 * @param lstLiteralCmb : �������ʏ�񃊃X�g
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private void storageLiteralCmb(List<?> lstLiteralCmb, RequestResponsTableBean resTable) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try {
			
			for (int i = 0; i < lstLiteralCmb.size(); i++) {

				//�������ʂ̊i�[
				resTable.addFieldVale(i, "flg_return", "true");
				resTable.addFieldVale(i, "msg_error", "");
				resTable.addFieldVale(i, "no_errmsg", "");
				resTable.addFieldVale(i, "nm_class", "");
				resTable.addFieldVale(i, "cd_error", "");
				resTable.addFieldVale(i, "msg_system", "");
				
				Object[] items = (Object[]) lstLiteralCmb.get(i);

				//���ʂ����X�|���X�f�[�^�Ɋi�[
				resTable.addFieldVale(i, "cd_literal", items[0].toString());
				resTable.addFieldVale(i, "nm_literal", items[1].toString());

			}

			if (lstLiteralCmb.size() == 0) {

				//�������ʂ̊i�[
				resTable.addFieldVale(0, "flg_return", "true");
				resTable.addFieldVale(0, "msg_error", "");
				resTable.addFieldVale(0, "no_errmsg", "");
				resTable.addFieldVale(0, "nm_class", "");
				resTable.addFieldVale(0, "cd_error", "");
				resTable.addFieldVale(0, "msg_system", "");

				//���ʂ����X�|���X�f�[�^�Ɋi�[
				resTable.addFieldVale(0, "cd_literal", "");
				resTable.addFieldVale(0, "nm_literal", "");

			}
			
		} catch (Exception e) {
			this.em.ThrowException(e, "");

		} finally {

		}

	}
	
}
