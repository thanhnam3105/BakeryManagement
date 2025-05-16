package jp.co.blueflag.shisaquick.srv.logic_genka;

import java.util.List;

import jp.co.blueflag.shisaquick.srv.base.LogicBase;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsKindBean;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;

/**
 * �H��R���{�{�b�N�X����
 * @author isono
 * @create 2009/11/04
 */
public class FGEN1020_Logic extends LogicBase {

	/**
	 * �R���X�g���N�^
	 */
	public FGEN1020_Logic() {
		//���N���X�̃R���X�g���N�^
		super();

	}

	public RequestResponsKindBean ExecLogic(
			RequestResponsKindBean reqData
			,UserInfoData _userInfoData
			)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		//���[�U�[���ޔ�
		userInfoData = _userInfoData;
		//�����o�b�t�@
		StringBuffer strSql = new StringBuffer();
		List<?> lstRecset = null;
		//���X�|���X�f�[�^�N���X
		RequestResponsKindBean resKind = null;

		try {
			//���X�|���X�f�[�^�i�@�\�j����
			resKind = new RequestResponsKindBean();
			resKind.setID("FGEN1020");

			String kaishaCd = "";
			String DataId = "";
			String id_gamen="";

			//���N�G�X�g�f�[�^�̎擾
			kaishaCd = reqData.getFieldVale(0, 0, "cd_kaisya");
			DataId = userInfoData.getID_data("170");
			id_gamen = reqData.getFieldVale(0, 0, "id_gamen");


			// ------------------------ SQL���̍쐬 ---------------------------
			strSql.append("SELECT cd_kaisha, cd_busho, nm_busho, ISNULL(keta_genryo,6) AS keta_genryo");
			strSql.append(" FROM ma_busho");
			strSql.append(" WHERE cd_kaisha = ");

			//WHERE��̐���
			//�uSQ110�F�������Z��ʁv���烊�N�G�X�g���������ꍇ
			if(id_gamen.equals("SQ110")){

				//MOD 2015/03/03 TT.Kitazawa�yQP@40812�zNo.12 start
				//  ��DataId=4 �̎��A����Ђ�I���ł��Ȃ���
				if ( DataId.equals("4") ) {
					//���H�ꕪ
					strSql.append(userInfoData.getCd_kaisha());
					strSql.append(" AND cd_busho = ");
					strSql.append(userInfoData.getCd_busho());
				// ��ʁi170�j������ DataId=4 �ȊO�̏ꍇ�̑Ή�
//				} else if ( DataId.equals("9") ) {
				} else {
					//�S��
					strSql.append(kaishaCd);

					//�yQP@00342�z�H��t���O���P�̂��̂����\�� + ������
					strSql.append(" AND	flg_kojo = 1 ");
					// 2015/07/10 �L���[�s�[�̂� + ��������\�� add start
					if("1".equals(kaishaCd)){
						strSql.append(" OR (cd_kaisha = 1 AND cd_busho=1) ");
					}
					// 2015/07/10 �L���[�s�[�̂� + ��������\�� add end
				}
				//MOD 2015/03/03 TT.Kitazawa�yQP@40812�zNo.12 end

			}
			//�uSQ111�F�ގ��i������ʁv���烊�N�G�X�g���������ꍇ
			else if(id_gamen.equals("SQ111")){
				//�S��
				strSql.append(kaishaCd);

				//�yQP@00342�z�H��t���O���P�̂��̂����\�� + ������
				strSql.append(" AND	flg_kojo = 1 ");
				// 2015/07/10 �L���[�s�[�̂� + ��������\�� add start
				if("1".equals(kaishaCd)){
					strSql.append(" OR (cd_kaisha = 1 AND cd_busho=1) ");
				}
				// 2015/07/10 �L���[�s�[�̂� + ��������\�� add end
			}
			strSql.append(" ORDER BY cd_kaisha,cd_busho");


			// -------------------------- SQL�����s ------------------------------
			super.createSearchDB();
			try{
				lstRecset = searchDB.dbSearch(strSql.toString());

			}catch(ExceptionWaning e){

			}

			// ���X�|���X�f�[�^�̌`��
			storageKoujouCmb(lstRecset, resKind);

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {
			//���X�g�̔j��
			removeList(lstRecset);
			//�Z�b�V�����̃N���[�Y
			if (searchDB != null) {
				searchDB.Close();
				searchDB = null;
			}
			//�ϐ��̍폜
			strSql = null;
		}
		return resKind;
	}

	private void storageKoujouCmb(

			List<?> lstGroupCmb
			, RequestResponsKindBean resData
			)
	throws ExceptionSystem,	ExceptionUser, ExceptionWaning {

		try {

			if (lstGroupCmb == null){

				//�������ʇ@	flg_return
				resData.addFieldVale("kojyo", "rec" + "0", "flg_return", "true");
				//�������ʇA	msg_error
				resData.addFieldVale("kojyo", "rec" + "0", "msg_error", "�Y������H�ꂪ����܂���");
				//�������ʇB	nm_class
				resData.addFieldVale("kojyo", "rec" + "0", "nm_class", "");
				//�������ʇE	no_errmsg
				resData.addFieldVale("kojyo", "rec" + "0", "no_errmsg", "");
				//�������ʇC	cd_error
				resData.addFieldVale("kojyo", "rec" + "0", "cd_error", "");
				//�������ʇD	msg_system
				resData.addFieldVale("kojyo", "rec" + "0", "msg_system", "");
				//�H��CD	cd_kojyo
				resData.addFieldVale("kojyo", "rec" + "0", "cd_kojyo", "");
				//�H�ꖼ	nm_kojyo
				resData.addFieldVale("kojyo", "rec" + "0", "nm_kojyo", "");

			}else{

				for (int i = 0; i < lstGroupCmb.size(); i++) {

					//�������ʂ̊i�[
					Object[] items = (Object[]) lstGroupCmb.get(i);

					//�������ʇ@	flg_return
					resData.addFieldVale("kojyo", "rec" + toString(i), "flg_return", "true");
					//�������ʇA	msg_error
					resData.addFieldVale("kojyo", "rec" + toString(i), "msg_error", "");
					//�������ʇB	nm_class
					resData.addFieldVale("kojyo", "rec" + toString(i), "nm_class", "");
					//�������ʇE	no_errmsg
					resData.addFieldVale("kojyo", "rec" + toString(i), "no_errmsg", "");
					//�������ʇC	cd_error
					resData.addFieldVale("kojyo", "rec" + toString(i), "cd_error", "");
					//�������ʇD	msg_system
					resData.addFieldVale("kojyo", "rec" + toString(i), "msg_system", "");
					//�H��CD	cd_kojyo
					resData.addFieldVale("kojyo", "rec" + toString(i), "cd_kojyo", toString(items[1]));
					//�H�ꖼ	nm_kojyo
					resData.addFieldVale("kojyo", "rec" + toString(i), "nm_kojyo", toString(items[2]));
					//����	keta_genryo
					resData.addFieldVale("kojyo", "rec" + toString(i), "cd_keta", toString(items[3]));

				}

			}

		} catch (Exception e) {
			this.em.ThrowException(e, "");

		} finally {

		}

	}

}
