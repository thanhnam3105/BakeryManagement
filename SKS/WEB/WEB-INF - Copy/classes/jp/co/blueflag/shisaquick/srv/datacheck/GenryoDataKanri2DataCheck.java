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
 * �yS2-37 SA380�z �����f�[�^�Ǘ��A����DB�`�F�b�N
 *  : �����f�[�^�̍X�V�E�o�^��DB�`�F�b�N
 * @author k-katayama
 * @since  2009/04/17
 */
public class GenryoDataKanri2DataCheck extends DataCheck{

	/**
	 * �����f�[�^�Ǘ�����DB�`�F�b�N�����p�R���X�g���N�^ 
	 */
	public GenryoDataKanri2DataCheck() {
		//���N���X�̃R���X�g���N�^
		super();

	}

	/**
	 *�����f�[�^�Ǘ�����DB�`�F�b�N
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
			
			//�@�\���N�G�X�g�f�[�^�̎擾
			String strShoriKbn = reqData.getFieldVale(0, 0, "kbn_shori");
			String strKaishaCd = reqData.getFieldVale(0, 0, "cd_kaisha");
			String strGenryoCd = reqData.getFieldVale(0, 0, "cd_genryo");
			String strKakuteiCd = reqData.getFieldVale(0, 0, "cd_kakutei");

			//����DB�Ǘ��𐶐�
			super.createSearchDB();
			
			//�@:�����敪�ɂ��A��������
			if ( strShoriKbn.equals("0") ) {				//�o�^��

				//�A:�����f�[�^�Ǘ��ADB�`�F�b�NSQL�쐬�������\�b�h���Ăяo���ADB�`�F�b�N�pSQL���擾����
				strSql = genryoKanriCheckSQL(strKaishaCd,strGenryoCd,strShoriKbn);

				//�B:�擾����SQL���A�����������s���B
				lstRecset = searchBD.dbSearch(strSql.toString());
				
				//�C:�������ʂ��d�����Ă���ꍇ�A���Exception�𔭐�������B
				if (lstRecset.size() != 0){
					String strData = strKaishaCd + "," + strGenryoCd;
					em.ThrowException(ExceptionKind.���Exception,"E000302", "��ЃR�[�h,�����R�[�h", strData, "");
				}
				
				//�������I����
			} else if ( strShoriKbn.equals("1") ) {	//�X�V��
				//�D:�y�X�V�p�z�����f�[�^�Ǘ��ADB�`�F�b�NSQL�쐬�������\�b�h���Ăяo���ADB�`�F�b�N�pSQL���擾����
				strSql = genryoKanriCheckSQL(strKaishaCd,strGenryoCd,strShoriKbn);
				
				//�E:�y�X�V�p�z�擾����SQL���A�����������s���B
				lstRecset = searchBD.dbSearch(strSql.toString());
				
				//�F:�y�X�V�p�z�������ʂ����݂��Ȃ��ꍇ�A���Exception�𔭐�������B
				if (lstRecset.size() == 0){
					String strData = strKaishaCd + "," + strGenryoCd;
					em.ThrowException(ExceptionKind.���Exception,"E000301", "��ЃR�[�h,�����R�[�h", strData, "");
				}
				
				//�G�F�m��R�[�h���͍ςݎ��̑��݃`�F�b�N
				if ( !(strKakuteiCd.equals(null) || strKakuteiCd.equals("")) ) {
					//�H:�y�o�^(�m��R�[�h)�p�z�����f�[�^�Ǘ��ADB�`�F�b�NSQL�쐬�������\�b�h���Ăяo���ADB�`�F�b�N�pSQL���擾����
					strSql = genryoKanriCheckSQL(strKaishaCd,strKakuteiCd,strShoriKbn);

					//�I:�y�o�^(�m��R�[�h)�p�z�擾����SQL���A�����������s���B
					lstRecset = searchBD.dbSearch(strSql.toString());
						
					//�J:�y�o�^(�m��R�[�h)�p�z�������ʂ����݂��Ă��Ȃ��ꍇ�A���Exception�𔭐�������B
					if (lstRecset.size() == 0){
						String strData = strKaishaCd + "," + strKakuteiCd;
						em.ThrowException(ExceptionKind.���Exception,"E000305", "��ЃR�[�h,�m��R�[�h", strData, "");
					}
				}
				
				//�������I����
				
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
	 * �����f�[�^�Ǘ��ADB�`�F�b�N�pSQL�쐬
	 * @param strKaishaCd : ��ЃR�[�h
	 * @param strGenryoCd : �����R�[�h
	 * @param strShoriKbn : �����敪
	 * @return �쐬����SQL
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private StringBuffer genryoKanriCheckSQL(String strKaishaCd, String strGenryoCd, String strShoriKbn) 
		throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		StringBuffer strRetSql = new StringBuffer();
		
		try {
			
			//�@�F���N�G�X�g�f�[�^���A�����f�[�^DB�`�F�b�N���s���ׂ�SQL���쐬����B
			
			//�����Ώ� �F �������͏��}�X�^�A�����}�X�^
			strRetSql.append(" SELECT M401.cd_kaisha, M401.cd_genryo, M402.cd_busho ");
			strRetSql.append(" FROM ma_genryo M401 ");
			strRetSql.append("  LEFT JOIN ma_genryokojo M402 ");
			strRetSql.append("   ON   M402.cd_kaisha = M401.cd_kaisha " );
			strRetSql.append("   AND M402.cd_genryo = M401.cd_genryo " );
			strRetSql.append(" WHERE M401.cd_kaisha =" + strKaishaCd );
			strRetSql.append("   AND  M401.cd_genryo ='" + strGenryoCd + "' ");
			if ( strShoriKbn.equals("0") ) {
				strRetSql.append("   AND  M402.cd_busho =" + ConstManager.getConstValue(Category.�ݒ�, "SHINKIGENRYO_BUSHOCD"));
			}
			
		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {
		}
		
		return strRetSql;
	}

}
