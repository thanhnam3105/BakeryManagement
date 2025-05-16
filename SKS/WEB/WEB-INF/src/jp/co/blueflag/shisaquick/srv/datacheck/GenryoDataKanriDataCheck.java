package jp.co.blueflag.shisaquick.srv.datacheck;

import jp.co.blueflag.shisaquick.srv.base.DataCheck;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsKindBean;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ConstManager;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;
import jp.co.blueflag.shisaquick.srv.common.ConstManager.Category;
import jp.co.blueflag.shisaquick.srv.common.ExceptionManager.ExceptionKind;

import java.util.List;

/**
 * 
 * �yS2-36 SA370�z �����f�[�^�Ǘ�����DB�`�F�b�N
 * @author k-katayama
 * @since  2009/04/17
 */
public class GenryoDataKanriDataCheck extends DataCheck{

	/**
	 * �����f�[�^�Ǘ�����DB�`�F�b�N�����p�R���X�g���N�^ 
	 */
	public GenryoDataKanriDataCheck() {
		//���N���X�̃R���X�g���N�^
		super();

	}

	/**
	 *�����f�[�^�Ǘ�����DB�`�F�b�N
	 * : �����f�[�^�폜����DB�`�F�b�N
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

		StringBuffer strSQL = new StringBuffer();
		List<?> lstRecset = null;

		try {
			String strKaishaCd = "";							//���N�G�X�g�f�[�^ : ��ЃR�[�h
			String strGenryoCd = "";							//���N�G�X�g�f�[�^ : �����R�[�h
//			String strHaishiFlg = "";							//���N�G�X�g�f�[�^ : �p�~�t���O

			// �@�\���N�G�X�g�f�[�^�̎擾
			strKaishaCd = reqData.getFieldVale(0, 0, "cd_kaisha");
			strGenryoCd = reqData.getFieldVale(0, 0, "cd_genryo");
//			strHaishiFlg = reqData.getFieldVale(0, 0, "flg_haishi");

			//�@�F�����f�[�^�Ǘ�DB�`�F�b�NSQL�쐬�������\�b�h���Ăяo���ADB�`�F�b�N�pSQL���擾����
			strSQL = this.genryoCheckSQL(strKaishaCd,strGenryoCd);
			
			//�A�F�擾����SQL���A�����������s���B
			super.createSearchDB();
			lstRecset = searchBD.dbSearch(strSQL.toString());

			//�B�F�������ʂ����݂��Ȃ��ꍇ�A���Exception�𔭐�������B
			if (lstRecset.size() == 0){
				String strData = strKaishaCd + "," + strGenryoCd;
				em.ThrowException(ExceptionKind.���Exception,"E000301", "��ЃR�[�h,�����R�[�h", strData, "");
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
			strSQL = null;
		}
		
	}

	/**
	 * �����f�[�^�Ǘ�DB�`�F�b�NSQL�쐬
	 *   �� �Ώۃe�[�u�� : ma_genryo, ma_genryokojo
	 * @param strKaishaCd : ��ЃR�[�h
	 * @param strGenryoCd : �����R�[�h
	 * @return �쐬����SQL
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private StringBuffer genryoCheckSQL(String strKaishaCd, String strGenryoCd)
			throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		StringBuffer strRetSQL = new StringBuffer();
		
		try {
			
			//�@�F���N�G�X�g�f�[�^���A�����f�[�^DB�`�F�b�N���s���ׂ�SQL���쐬����B
			
			//�����Ώ� �F �������͏��}�X�^�A�����}�X�^(�V�K�����̂�)
			strRetSQL.append(" SELECT M401.cd_kaisha, M401.cd_genryo, M402.cd_busho ");
			strRetSQL.append(" FROM ma_genryo M401 ");
			strRetSQL.append("  LEFT JOIN ma_genryokojo M402 ");
			strRetSQL.append("   ON   M402.cd_kaisha = M401.cd_kaisha " );
			strRetSQL.append("   AND M402.cd_genryo = M401.cd_genryo " );
			strRetSQL.append(" WHERE M401.cd_kaisha =" + strKaishaCd );
			strRetSQL.append("   AND  M401.cd_genryo ='" + strGenryoCd + "' ");
			strRetSQL.append("   AND  M402.cd_busho =" + ConstManager.getConstValue(Category.�ݒ�, "SHINKIGENRYO_BUSHOCD"));
			
		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {
		}
		
		return strRetSQL;
	}

}
