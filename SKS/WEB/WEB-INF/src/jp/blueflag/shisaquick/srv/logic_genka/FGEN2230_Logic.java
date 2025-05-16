package jp.co.blueflag.shisaquick.srv.logic_genka;

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
 *	�������Z�ꗗ�����`�F�b�N����
 *  �@�\ID�FFGEN2230
 *
 *	@author okano
 *	@since  2013/10/22
 */
public class FGEN2230_Logic extends LogicBase{

	/**
	 * �����m�F���W�b�N�p�R���X�g���N�^
	 * : �C���X�^���X����
	 */
	public FGEN2230_Logic() {
		//���N���X�̃R���X�g���N�^
		super();

	}

	/**
	 * �����m�FSQL�쐬
	 * : �����s���`�F�b�N���s���ׂ�SQL���쐬�B
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

			String strId_Gamen   = "";
			String strId_Kino   = "";
			String strId_Data   = "";
			String strId_User   = "";
			String strCd_Kaisha   = "";
			String strCd_Busho   = "";
			String strShain   = "";
			String strNen   = "";
			String strNo_oi   = "";
			String strNo_eda   = "";;
			String strKbnShori   = "";

			//���N�G�X�g���擾
			strId_Data = userInfoData.getID_data("180");
			strId_User = userInfoData.getId_user();
			strCd_Kaisha = userInfoData.getCd_kaisha();
			strCd_Busho = userInfoData.getCd_busho();
			strShain = reqRecBean.GetItemValue("cd_shain");
			strNen = reqRecBean.GetItemValue("nen");
			strNo_oi = reqRecBean.GetItemValue("no_oi");
			strNo_eda = reqRecBean.GetItemValue("no_eda");
			strKbnShori = reqRecBean.GetItemValue("mode");

			//���������ݒ�
			strSQL = new StringBuffer();
			strSQL.append(" SELECT ");
			strSQL.append(" CASE ");
			strSQL.append(" WHEN flg_kenkyu = 1 ");
			strSQL.append("  THEN 1 ");
			strSQL.append(" WHEN flg_seisan = 1 ");
			strSQL.append("  THEN 2 ");
			strSQL.append(" WHEN flg_genshizai = 1 ");
			strSQL.append("  THEN 3 ");
			strSQL.append(" WHEN flg_kojo = 1 ");
			strSQL.append("  THEN 4 ");
			strSQL.append(" WHEN flg_eigyo = 1 ");
			strSQL.append("  THEN 5 ");
			strSQL.append("  ELSE NULL ");
			strSQL.append(" END ");
			strSQL.append(" FROM ma_busho ");
			strSQL.append(" WHERE cd_kaisha = ");
			strSQL.append(strCd_Kaisha);
			strSQL.append(" AND cd_busho = ");
			strSQL.append(strCd_Busho);

			createSearchDB();
			lstRecset = searchDB.dbSearch(strSQL.toString());

			if(strId_Data.equals("7")){
			} else {
				if (toString(lstRecset.get(0)).equals("1")){
					if(strKbnShori.equals("1")){

						strSQL = new StringBuffer();
						strSQL.append(" SELECT COUNT(*) FROM tr_shisan_shisakuhin WHERE cd_kaisha = ");
						strSQL.append(strCd_Kaisha);
						strSQL.append(" AND cd_shain = ");
						strSQL.append(strShain);
						strSQL.append(" AND nen = ");
						strSQL.append(strNen);
						strSQL.append(" AND no_oi = ");
						strSQL.append(strNo_oi);
						strSQL.append(" AND no_eda = ");
						strSQL.append(strNo_eda);

						createSearchDB();
						lstRecset = searchDB.dbSearch(strSQL.toString());

						if (Integer.parseInt(lstRecset.get(0).toString()) == 0){
							em.ThrowException(ExceptionKind.���Exception, "E000335", reqRecBean.GetItemValue("no_shisaku"), "", "");
						}
					} else if(strKbnShori.equals("2")){

						strSQL = new StringBuffer();
						strSQL.append(" SELECT COUNT(*) FROM tr_shisan_shisakuhin WHERE cd_kaisha <> ");
						strSQL.append(strCd_Kaisha);
						strSQL.append(" AND cd_hanseki = ");
						strSQL.append(strCd_Kaisha);
						strSQL.append(" AND cd_shain = ");
						strSQL.append(strShain);
						strSQL.append(" AND nen = ");
						strSQL.append(strNen);
						strSQL.append(" AND no_oi = ");
						strSQL.append(strNo_oi);
						strSQL.append(" AND no_eda = ");
						strSQL.append(strNo_eda);

						createSearchDB();
						lstRecset = searchDB.dbSearch(strSQL.toString());

						if (Integer.parseInt(lstRecset.get(0).toString()) == 0){
							em.ThrowException(ExceptionKind.���Exception, "E000335", reqRecBean.GetItemValue("no_shisaku"), "", "");
						}
					}
				} else if (toString(lstRecset.get(0)).equals("2")){
					if(strKbnShori.equals("1")){

						strSQL = new StringBuffer();
						strSQL.append(" SELECT COUNT(*) FROM tr_shisan_shisakuhin WHERE cd_kaisha = ");
						strSQL.append(strCd_Kaisha);
						strSQL.append(" AND cd_shain = ");
						strSQL.append(strShain);
						strSQL.append(" AND nen = ");
						strSQL.append(strNen);
						strSQL.append(" AND no_oi = ");
						strSQL.append(strNo_oi);
						strSQL.append(" AND no_eda = ");
						strSQL.append(strNo_eda);

						createSearchDB();
						lstRecset = searchDB.dbSearch(strSQL.toString());

						if (Integer.parseInt(lstRecset.get(0).toString()) == 0){
							em.ThrowException(ExceptionKind.���Exception, "E000335", reqRecBean.GetItemValue("no_shisaku"), "", "");
						}
					} else if(strKbnShori.equals("2")){

						// ADD 2013/12/5 okano�yQP@30154�zstart
						strSQL = new StringBuffer();
						// MOD 2014/04/18 �yQP@30154�z�ۑ� start
						//strSQL.append(" SELECT COUNT(*) FROM tr_shisan_shisakuhin WHERE cd_hanseki = ");
						//strSQL.append(strCd_Kaisha);
						//strSQL.append(" AND cd_shain = ");
						strSQL.append(" SELECT COUNT(*) FROM tr_shisan_shisakuhin ");
						strSQL.append(" WHERE cd_shain = ");
						// MOD 2014/04/18 end
						strSQL.append(strShain);
						strSQL.append(" AND nen = ");
						strSQL.append(strNen);
						strSQL.append(" AND no_oi = ");
						strSQL.append(strNo_oi);
						strSQL.append(" AND no_eda = ");
						strSQL.append(strNo_eda);

						createSearchDB();
						lstRecset = searchDB.dbSearch(strSQL.toString());

						if (Integer.parseInt(lstRecset.get(0).toString()) == 0){
							em.ThrowException(ExceptionKind.���Exception, "E000335", reqRecBean.GetItemValue("no_shisaku"), "", "");
						}
						// ADD 2013/12/5 okano�yQP@30154�zend
					}
				}
			}

			//�@�\ID�̐ݒ�
			resKind.setID(reqData.getID());
			//�e�[�u�����̐ݒ�
			resKind.addTableItem(((RequestResponsTableBean) reqData.GetItem(0)).getID());

		} catch (Exception e) {

			em.ThrowException(e,"");

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

}
