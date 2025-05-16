package jp.co.blueflag.shisaquick.srv.logic;

import jp.co.blueflag.shisaquick.srv.base.LogicBase;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsKindBean;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsTableBean;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ConstManager;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;
import jp.co.blueflag.shisaquick.srv.common.ConstManager.Category;

import java.util.List;

/**
 * 
 * �H�ꌟ���i��ۯ���޳ݗp�j�c�a���� : ��ۯ���޳ݗp�@�H�ꌟ���i��ۯ���޳ݗp�j�f�[�^���oSQL���쐬����B
 * M104_busho�e�[�u������A�f�[�^�̒��o���s���B
 * �擾�����A���X�|���X�f�[�^�ێ��u�@�\ID�FSA180O�v�ɐݒ肷��B
 * @author itou
 * @since 2009/04/06
 */
public class KoujouSearchLogic extends LogicBase {

	/**
	 * �H�ꌟ���i��ۯ���޳ݗp�j�R���X�g���N�^ : �C���X�^���X����
	 */
	public KoujouSearchLogic() {
		//���N���X�̃R���X�g���N�^
		super();

	}

	/**
	 * �R���{�p�F�H����擾SQL�쐬 M104_busho�e�[�u������A�f�[�^�̒��o���s���B
	 * 
	 * @param reqData
	 *            : ���N�G�X�g�f�[�^
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

			String kaishaCd = "";
			String UserId = "";
			String GamenId = "";
			String DataId = "";
			
			//��ЃR�[�h�̎擾
			kaishaCd = reqData.getFieldVale(0, 0, "cd_kaisha");
			//���[�UID�̎擾
			UserId = reqData.getFieldVale(0, 0, "id_user");
			//���ID�̎擾
			GamenId = reqData.getFieldVale(0, 0, "id_gamen");

			//�����f�[�^ID�擾
			for (int i = 0; i < userInfoData.getId_gamen().size(); i++) {
				if (userInfoData.getId_gamen().get(i).toString().equals(GamenId)){
					//�f�[�^ID��ݒ�
					DataId = userInfoData.getId_data().get(i).toString();
				}
			}

//mod start -------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.6
			//strSql.append("SELECT cd_kaisha,cd_busho,nm_busho");
			strSql.append("SELECT cd_kaisha,cd_busho");
			strSql.append(",CASE cd_kaisha WHEN " + ConstManager.getConstValue(Category.�ݒ�, "CD_DAIHYO_KAISHA") + " THEN");
			strSql.append(" 	CASE cd_busho WHEN " + ConstManager.getConstValue(Category.�ݒ�, "CD_DAIHYO_KOJO") + " THEN '�V�K�o�^����'");
			strSql.append(" 	ELSE nm_busho");
			strSql.append("  	END");
			strSql.append(" ELSE nm_busho");
			strSql.append(" END AS nm_busho");
//mod end   -------------------------------------------------------------------------------
			
			strSql.append(" FROM ma_busho");
			strSql.append(" WHERE cd_kaisha = ");

			//�������͏��iJSP�j
			if ( GamenId.equals("20") || GamenId.equals("25") ) {
				if (DataId.equals("")) {
					//�����f�[�^ID�擾
					for (int i = 0; i < userInfoData.getId_gamen().size(); i++) {
						if (userInfoData.getId_gamen().get(i).toString().equals("25")){
							//�f�[�^ID��ݒ�
							DataId = userInfoData.getId_data().get(i).toString();
						}
					}
				}
				if ( DataId.equals("1") ) {
					//�S�����
					//��ЃR�[�h�擾
				    // MOD 2013/11/6 QP@30154 okano start
//						if (kaishaCd.equals("")) {
//							for (int i = 0; i < userInfoData.getCd_tantokaisha().size(); i++) {
//								if (userInfoData.getCd_tantokaisha().get(i).toString().equals(userInfoData.getCd_kaisha().toString())){
//									kaishaCd = userInfoData.getCd_tantokaisha().get(i).toString();
//									break;
//								}
//							}
//						}
//						if (kaishaCd.equals("")) {
//							kaishaCd = userInfoData.getCd_tantokaisha().get(0).toString();
//						}
					if (kaishaCd.equals("")) {
						kaishaCd = userInfoData.getCd_kaisha();
					}
				    // MOD 2013/11/6 QP@30154 okano end
					strSql.append(kaishaCd);
					
				} else if ( DataId.equals("2") ) {
					//���H�ꕪ
					if (kaishaCd.equals("")) {
						kaishaCd = userInfoData.getCd_kaisha();
					}
					strSql.append(kaishaCd);
					strSql.append(" AND cd_busho = ");
					strSql.append(userInfoData.getCd_busho());
					
				} else if ( DataId.equals("9") ) {
					//�S��
					if (kaishaCd.equals("")) {
						kaishaCd = userInfoData.getCd_kaisha();
					}
					strSql.append(kaishaCd);
				}
			}

//mod start -------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.6
			strSql.append("  AND fg_hyoji = 1");
//mod end   -------------------------------------------------------------------------------
			
			strSql.append(" ORDER BY cd_kaisha,cd_busho");
			
			// SQL�����s
			super.createSearchDB();
			lstRecset = searchDB.dbSearch(strSql.toString());

			// �@�\ID�̐ݒ�
			String strKinoId = reqData.getID();
			resKind.setID(strKinoId);

			// �e�[�u�����̐ݒ�
			String strTableNm = ((RequestResponsTableBean) reqData.GetItem(0)).getID();
			resKind.addTableItem(strTableNm);

			// ���X�|���X�f�[�^�̌`��
			storageKoujouCmb(lstRecset, resKind.getTableItem(0));

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
	 * �R���{�p�F��Ѓp�����[�^�[�i�[ : ��ЃR���{�{�b�N�X�������X�|���X�f�[�^�֊i�[����B
	 * �擾�����A���X�|���X�f�[�^�ێ��u�@�\ID�FSA140O�v�ɐݒ肷��B
	 * 
	 * @param lstGroupCmb
	 *            : �������ʏ�񃊃X�g
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private void storageKoujouCmb(List<?> lstGroupCmb, RequestResponsTableBean resTable)
	throws ExceptionSystem,	ExceptionUser, ExceptionWaning {

		try {
			
			for (int i = 0; i < lstGroupCmb.size(); i++) {

				//�������ʂ̊i�[
				resTable.addFieldVale(i, "flg_return", "true");
				resTable.addFieldVale(i, "msg_error", "");
				resTable.addFieldVale(i, "no_errmsg", "");
				resTable.addFieldVale(i, "nm_class", "");
				resTable.addFieldVale(i, "cd_error", "");
				resTable.addFieldVale(i, "msg_system", "");
				
				Object[] items = (Object[]) lstGroupCmb.get(i);

				//���ʂ����X�|���X�f�[�^�Ɋi�[
				resTable.addFieldVale(i, "cd_busho", items[1].toString());
				resTable.addFieldVale(i, "nm_busho", items[2].toString());

			}

			if (lstGroupCmb.size() == 0) {

				//�������ʂ̊i�[
				resTable.addFieldVale(0, "flg_return", "true");
				resTable.addFieldVale(0, "msg_error", "");
				resTable.addFieldVale(0, "no_errmsg", "");
				resTable.addFieldVale(0, "nm_class", "");
				resTable.addFieldVale(0, "cd_error", "");
				resTable.addFieldVale(0, "msg_system", "");

				//���ʂ����X�|���X�f�[�^�Ɋi�[
				resTable.addFieldVale(0, "cd_busho", "");
				resTable.addFieldVale(0, "nm_busho", "");

			}

		} catch (Exception e) {
			this.em.ThrowException(e, "");
			
		} finally {

		}

	}
	
}
