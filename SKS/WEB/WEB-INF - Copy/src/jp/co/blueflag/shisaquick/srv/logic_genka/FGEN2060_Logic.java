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
 * �S���҃}�X�^�����e�i�c�Ɓj�@�S���Ҍ����i�c�Ɓj
 *  : ���݃X�e�[�^�X�����擾����B
 *  �@�\ID�FFGEN2060
 *
 * @author Nishigawa
 * @since  2011/01/28
 */
public class FGEN2060_Logic extends LogicBase{

	/**
	 * �S���҃}�X�^�����e�i�c�Ɓj�@�S���Ҍ����i�c�Ɓj
	 * : �C���X�^���X����
	 */
	public FGEN2060_Logic() {
		//���N���X�̃R���X�g���N�^
		super();

	}

	//--------------------------------------------------------------------------------------------------------------
	//
	//                                                                     ExecLogic�i�����J�n�j
	//
	//--------------------------------------------------------------------------------------------------------------

	/**
	 * �S���҃}�X�^�����e�i�c�Ɓj�@��Ќ����i�c�ƕ����̂݁j
	 *  : �S���Ҍ����i�c�Ɓj�����擾����B
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
			this.em.ThrowException(e, "�S���҃}�X�^�����e�i�c�Ɓj�@�S���Ҍ����i�c�Ɓj���������s���܂����B");

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

		// ADD 2013/9/25 okano�yQP@30151�zNo.28 start
		List<?> lstRecset = null;
		// ADD 2013/9/25 okano�yQP@30151�zNo.28 end

		try {
			//���N�G�X�g����f�[�^���o
			String id_user = reqData.getFieldVale(0, 0, "id_user");

			// MOD 2013/9/25 okano�yQP@30151�zNo.28 start
			//SQL���̍쐬
//			strSql.append(" SELECT   ");
//			strSql.append(" 	   M1.password  ");
//			strSql.append("       ,M1.cd_kengen  ");
//			strSql.append("       ,M1.nm_user  ");
//			strSql.append("       ,M1.cd_kaisha  ");
//			strSql.append("       ,M1.cd_busho  ");
//			strSql.append("       ,M1.id_josi  ");
//			strSql.append(" 	  ,M2.nm_user AS nm_josi  ");
//			strSql.append(" FROM   ");
//			strSql.append(" 	   ma_user AS M1  ");
//			strSql.append(" 	   LEFT JOIN ma_user AS M2  ");
//			strSql.append(" 	   ON M1.id_josi = M2.id_user  ");
//			strSql.append(" WHERE  ");
//			strSql.append(" 	   M1.id_user = " + id_user);

			strSql.append("SELECT COUNT(*) FROM ma_user WHERE id_user = ");
			strSql.append(id_user);

			createSearchDB();
			lstRecset = searchDB.dbSearch(strSql.toString());

			strSql = new StringBuffer();
			if (Integer.parseInt(lstRecset.get(0).toString()) == 0){
				strSql.append("SELECT");
				strSql.append("  password");
				strSql.append(" ,cd_kengen");
				strSql.append(" ,nm_user");
				strSql.append(" ,cd_kaisha");
				strSql.append(" ,cd_busho");
				strSql.append(" ,NULL");
				strSql.append(" ,NULL");
				strSql.append(" FROM ma_user_new");
				strSql.append(" WHERE id_user = ");
				strSql.append(id_user);
			} else {
				strSql.append(" SELECT   ");
				strSql.append(" 	   M1.password  ");
				strSql.append("       ,M1.cd_kengen  ");
				strSql.append("       ,M1.nm_user  ");
				strSql.append("       ,M1.cd_kaisha  ");
				strSql.append("       ,M1.cd_busho  ");
				// MOD 2015/03/03 TT.Kitazawa�yQP@40812�zNo.19 start
//				strSql.append("       ,M1.id_josi  ");
//				strSql.append(" 	  ,M2.nm_user AS nm_josi  ");
				strSql.append("       ,MEMBER.id_member  ");
				strSql.append("       ,M2.nm_user AS nm_member ");
				strSql.append(" FROM   ");
				strSql.append(" 	   ma_user AS M1  ");

				strSql.append("        LEFT JOIN ma_member AS MEMBER  ");
				strSql.append("        ON M1.id_user = MEMBER.id_user  ");
				strSql.append(" 	   LEFT JOIN ma_user AS M2  ");
//				strSql.append(" 	   ON M1.id_josi = M2.id_user  ");
				strSql.append("        ON MEMBER.id_member = M2.id_user ");

				strSql.append(" WHERE  ");
				strSql.append(" 	   M1.id_user = " + id_user);
				strSql.append(" ORDER BY  MEMBER.no_sort ");
				// MOD 2015/03/03 TT.Kitazawa�yQP@40812�zNo.19 end
			}
			// MOD 2013/9/25 okano�yQP@30151�zNo.28 end
		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {
			// ADD 2013/9/25 okano�yQP@30151�zNo.28 start
			removeList(lstRecset);
			if (searchDB != null) {
				//�Z�b�V�����̉��
				this.searchDB.Close();
				searchDB = null;

			}
			// ADD 2013/9/25 okano�yQP@30151�zNo.28 end
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
//�yQP@10713�z2011/10/28 TT H.SHIMA -ADD Start
			//�c�Ɓi�{���j�����R�[�h�擾
			String strEigyoHonbu =
				ConstManager.getConstValue(ConstManager.Category.�ݒ�,"EIGYO_KENGEN_HONBU");
			//�c�Ɓi�Ǘ��҃V�X�e���j�����R�[�h�擾
			String strEigyoSystem =
				ConstManager.getConstValue(ConstManager.Category.�ݒ�,"EIGYO_KENGEN_SYSTEM");
			//�c�Ɓi���j�����R�[�h�擾
			String strEigyoKari =
				ConstManager.getConstValue(ConstManager.Category.�ݒ�,"EIGYO_KENGEN_KARI");
			// ADD 2013/9/25 okano�yQP@30151�zNo.28 start
			//�c�Ɓi���j�����R�[�h�擾
			String strEigyoPass =
				ConstManager.getConstValue(ConstManager.Category.�ݒ�,"EIGYO_KENGEN_PASS");
			// ADD 2013/9/25 okano�yQP@30151�zNo.28 end
//�yQP@10713�z2011/10/28 TT H.SHIMA -ADD End

			// ADD 2015/03/03 TT.Kitazawa�yQP@40812�zstart�iJava�G���[�Ή��j
			if (lstGenkaHeader.size() == 0) {
				//�������ʂ̊i�[
				resTable.addFieldVale(0, "flg_return", "true");
				resTable.addFieldVale(0, "msg_error", "");
				resTable.addFieldVale(0, "no_errmsg", "");
				resTable.addFieldVale(0, "nm_class", "");
				resTable.addFieldVale(0, "cd_error", "");
				resTable.addFieldVale(0, "msg_system", "");
			}
			// ADD 2015/03/03 TT.Kitazawa�yQP@40812�zend

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
				resTable.addFieldVale(i, "password", toString(items[0],""));
				resTable.addFieldVale(i, "cd_kengen", toString(items[1],""));
				resTable.addFieldVale(i, "nm_user", toString(items[2],""));
				resTable.addFieldVale(i, "cd_kaisha", toString(items[3],""));
				resTable.addFieldVale(i, "cd_busho", toString(items[4],""));
				// MOD 2015/03/03 TT.Kitazawa�yQP@40812�zNo.19 start
//				resTable.addFieldVale(i, "id_josi", toString(items[5],""));
//				resTable.addFieldVale(i, "nm_josi", toString(items[6],""));
				resTable.addFieldVale(i, "id_member", toString(items[5],""));
				resTable.addFieldVale(i, "nm_member", toString(items[6],""));
				// MOD 2015/03/03 TT.Kitazawa�yQP@40812�zNo.19 end

				//�c�Ɓi��ʁj�����̏ꍇ
				if(toString(items[1],"").equals(strEigyoIppan)){
//�yQP@10713�z2011/10/28 TT H.SHIMA -MOD Start
					//resTable.addFieldVale(i, "kengen_ippan", "1");
					resTable.addFieldVale(i, "eigyo_kengen", "1");
				}
				//�c�Ɓi��ʁj�����ȊO�̏ꍇ
				else if(toString(items[1],"").equals(strEigyoHonbu) || toString(items[1],"").equals(strEigyoSystem)){
					resTable.addFieldVale(i, "eigyo_kengen", "2");
				}
				// ADD 2013/9/25 okano�yQP@30151�zNo.28 start
				else if(toString(items[1],"").equals(strEigyoPass)){
					resTable.addFieldVale(i, "eigyo_kengen", "3");
				}
				// ADD 2013/9/25 okano�yQP@30151�zNo.28 end
				else{
					//resTable.addFieldVale(i, "kengen_ippan", "0");
					resTable.addFieldVale(i, "eigyo_kengen", "0");
//�yQP@10713�z2011/10/28 TT H.SHIMA -MOD Start
				}
			}

		} catch (Exception e) {
			this.em.ThrowException(e, "");

		} finally {

		}

	}

}
