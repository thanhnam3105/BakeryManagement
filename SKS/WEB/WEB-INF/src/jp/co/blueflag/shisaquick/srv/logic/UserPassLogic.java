package jp.co.blueflag.shisaquick.srv.logic;

import java.util.List;

import jp.co.blueflag.shisaquick.srv.base.LogicBase;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsKindBean;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsRowBean;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsTableBean;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;
import jp.co.blueflag.shisaquick.srv.common.ExceptionManager.ExceptionKind;

/**
 *
 * �p�X���[�h�m�FSQL�쐬
 *  : �p�X���[�h�ɕs�����Ȃ����m�F����B
 * @author okano
 * @since  2013/09/18
 */
public class UserPassLogic extends LogicBase{

	/**
	 * �p�X���[�h�m�F���W�b�N�p�R���X�g���N�^
	 * : �C���X�^���X����
	 */
	public UserPassLogic() {
		//���N���X�̃R���X�g���N�^
		super();

	}

	/**
	 * �p�X���[�h�m�FSQL�쐬
	 * : �p�X���[�h�s���`�F�b�N���s���ׂ�SQL���쐬�B
	 * @param reqData�F���N�G�X�g�f�[�^
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

		RequestResponsTableBean reqTableBean = null;
		RequestResponsRowBean reqRecBean = null;
		StringBuffer strSQL = new StringBuffer();
		List<?> lstRecset = null;

		//���X�|���X�f�[�^�N���X
		RequestResponsKindBean resKind = null;

		try {
			//���X�|���X�f�[�^�i�@�\�j����
			resKind = new RequestResponsKindBean();

			//�e�[�u��Bean�擾
			reqTableBean = reqData.getTableItem(0);
			//�sBean�擾
			reqRecBean = reqTableBean.getRowItem(0);

			String strId_User   = "";
			String strKbn_Login = "";
			String strPassword  = "";

			//�s�f�[�^��胊�N�G�X�g���擾
			//���[�U�[ID�ݒ�
			strId_User = reqRecBean.GetItemValue("id_user");
			//���O�C���敪�ݒ�
			strKbn_Login = reqRecBean.GetItemValue("kbn_login");
			//�p�X���[�h�ݒ�
			//�V���O���T�C���I���p�p�X���[�h�擾SQL�쐬
			if (strKbn_Login.equals("1")){

				strSQL = new StringBuffer();
				strSQL.append("SELECT password FROM ma_user WHERE id_user = ");
				strSQL.append(strId_User);

				createSearchDB();
				lstRecset = searchDB.dbSearch(strSQL.toString());

				//List��object�֕ϊ�
				Object[] objs = lstRecset.toArray(new String[lstRecset.size()]);

				//Object��String�֕ϊ�
				strPassword = objs[0].toString();

			//���O�C���p�p�X���[�h�擾
			} else if (strKbn_Login.equals("2")){

				strPassword = reqRecBean.GetItemValue("password");
			}

			//�@�\ID�̐ݒ�
			resKind.setID(reqData.getID());
			//�e�[�u�����̐ݒ�
			resKind.addTableItem(((RequestResponsTableBean) reqData.GetItem(0)).getID());

			strSQL = new StringBuffer();
			strSQL.append("SELECT COUNT(*) FROM ma_user WHERE id_user = ");
			strSQL.append(strId_User);
			strSQL.append(" AND dt_password > DATEADD(MONTH,-6,GETDATE()) ");

			createSearchDB();
			lstRecset = searchDB.dbSearch(strSQL.toString());

			// MOD 2014/12/05 TT.Kitazawa �������ʂ̊i�[��ǉ� start
//			checkUserPass(lstRecset);
			checkUserPass(lstRecset, resKind.getTableItem(0));
			// MOD 2014/12/05 TT.Kitazawa end

			sizeCheckPass(strPassword, 6);
			strCheckPass(strPassword);



		} catch (Exception e) {

			em.ThrowException(e,"���[�U�[�F�؂Ɏ��s���܂����B");

		} finally {
			//���X�g�̔j��
			removeList(lstRecset);
			if (searchDB != null) {
				//�Z�b�V�����̃N���[�Y
				searchDB.Close();
				searchDB = null;
			}

			//�ϐ��̍폜
			strSQL = null;
			//�N���X�̔j��
			reqTableBean = null;
			reqRecBean = null;
		}

		return resKind;
	}

	// MOD 2014/12/05 TT.Kitazawa �������ʂ̊i�[��ǉ� start
	/**
	 * �F�،��ʃp�����[�^�[�i�[
	 * : ���[�U�[�F�؂̔F�،��ʏ����@�\���X�|���X�f�[�^�֊i�[����B
	 * @param lstNinshoKekka�F�F�،���
	 * @param resTable�F���X�|���X�f�[�^
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
//	private void checkUserPass(List<?> lstNinshoKekka)
	private void checkUserPass(List<?> lstNinshoKekka, RequestResponsTableBean resTable)
			throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try {
			//�������ʂ̊i�[
			resTable.addFieldVale(0, "flg_return", "true");
			resTable.addFieldVale(0, "msg_error", "");
			resTable.addFieldVale(0, "no_errmsg", "");
			resTable.addFieldVale(0, "nm_class", "");
			resTable.addFieldVale(0, "cd_error", "");
			resTable.addFieldVale(0, "msg_system", "");
			// MOD 2014/12/05 TT.Kitazawa end

			if (Integer.parseInt(lstNinshoKekka.get(0).toString()) == 0){

				//�x����O��Throw����B
				em.ThrowException(ExceptionKind.���Exception, "E000225", "�p�X���[�h", "", "");

			}

		} catch (Exception e) {
			em.ThrowException(e,"���[�U�[�F�؂Ɏ��s���܂����B");

		} finally {

		}

	}

	protected void sizeCheckPass(String strChkPrm, int iMinLen) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		try {

			int chkLen = strChkPrm.length();
			// �@:�ŏ�������p���A���͌����`�F�b�N���s���B
			if (iMinLen > chkLen) {

				// ���͌����s�����X���[����B
				em.ThrowException(ExceptionKind.���Exception,"E000226","�p�X���[�h",Integer.toString(iMinLen),"");
			}

		} catch (Exception e) {
			em.ThrowException(e, "���[�U�[�F�؂Ɏ��s���܂����B");

		} finally {

		}
	}

	protected void strCheckPass(String strChkPrm) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		try {

			//�󔒃X�y�[�X�`�F�b�N
			if(strChkPrm.indexOf(" ") != -1){

				em.ThrowException(ExceptionKind.���Exception, "E000227", "�p�X���[�h", "", "");
			}

			//���p�p�������݃`�F�b�N
			if(!strChkPrm.matches("^[0-9]+[0-9a-zA-Z]*[a-zA-Z]+[0-9a-zA-Z]*$|^[a-zA-Z]+[0-9a-zA-Z]*[0-9]+[0-9a-zA-Z]*$")){

				em.ThrowException(ExceptionKind.���Exception, "E000227", "�p�X���[�h", "", "");
			}

		} catch (Exception e) {
			em.ThrowException(e, "���[�U�[�F�؂Ɏ��s���܂����B");

		} finally {

		}
	}

}
