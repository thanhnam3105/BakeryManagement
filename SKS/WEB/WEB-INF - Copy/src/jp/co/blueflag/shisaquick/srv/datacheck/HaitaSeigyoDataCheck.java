package jp.co.blueflag.shisaquick.srv.datacheck;

import jp.co.blueflag.shisaquick.srv.base.DataCheck;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsKindBean;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;
import jp.co.blueflag.shisaquick.srv.common.ExceptionManager.ExceptionKind;

import java.util.List;

/**
 * 
 * �yS2- SA420�z �r�����䏈��DB�`�F�b�N
 * @author k-katayama
 * @since  2009/04/18
 */
public class HaitaSeigyoDataCheck extends DataCheck{

	/**
	 * �r�����䏈��DB�`�F�b�N�����p�R���X�g���N�^ 
	 */
	public HaitaSeigyoDataCheck() {
		//���N���X�̃R���X�g���N�^
		super();

	}

	/**
	 *�r�����䏈��DB�`�F�b�N
	 * @param reqData : ���N�G�X�g�f�[�^
	 * @param userInfoData : ���[�U�[���
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	public void execDataCheck(
			RequestResponsKindBean reqData
			,UserInfoData _userInfoData
			) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		//���[�U�[���ޔ�
		userInfoData = _userInfoData;

		StringBuffer strSql = new StringBuffer();
		List<?> lstRecset = null;

		try {

			// �@�\���N�G�X�g�f�[�^�̎擾
			String strReqZikoKbn = reqData.getFieldVale(0, 0, "kubun_ziko");
			String strReqShainCd = reqData.getFieldVale(0, 0, "cd_shain");
			String strReqNen = reqData.getFieldVale(0, 0, "nen");
			String strReqOiNo = reqData.getFieldVale(0, 0, "no_oi");

			//SQL���̍쐬
			if (strReqZikoKbn.equals("1")) {
				//SQL���쐬
				strSql = shisakuCheckSQL(strReqShainCd,strReqNen,strReqOiNo);
				
				//SQL�����s
				super.createSearchDB();
				lstRecset = searchBD.dbSearch(strSql.toString());
				
				//�������ʂ��Ȃ��ꍇ
				if (lstRecset.size() == 0){
					String strMsg = strReqShainCd + "-" + strReqNen + "-" + strReqOiNo;
					em.ThrowException(ExceptionKind.���Exception,"E000301", "����R�[�h", strMsg, "");
				}
			}

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {
			//���X�g�̔j��
			removeList(lstRecset);
			if (searchBD != null) {
				//�Z�b�V�����̃N���[�Y
				searchBD.Close();
				searchBD = null;
			}
			
			//�ϐ��̍폜
			strSql = null;
		}
		
	}
	
	/**
	 * ����R�[�h���݃`�F�b�NSQL�쐬
	 * @param strShainCd : ����CD-�Ј��R�[�h
	 * @param strNen : ����CD-�N
	 * @param strOiNo : ����CD-�ǔ�
	 * @return strSql�F�쐬SQL
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private StringBuffer shisakuCheckSQL(String strShainCd, String strNen, String strOiNo) 
		throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		StringBuffer strRetSQL = new StringBuffer(); 
		
		try {
			//SQL���̍쐬
			strRetSQL.append("SELECT cd_shain, nen, no_oi");
			strRetSQL.append(" FROM tr_shisakuhin");
			strRetSQL.append(" WHERE cd_shain = ");
			strRetSQL.append(strShainCd);
			strRetSQL.append(" AND nen = ");
			strRetSQL.append(strNen);
			strRetSQL.append(" AND no_oi = ");
			strRetSQL.append(strOiNo);

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {
		}
		
		return strRetSQL;
	}

}
