package jp.co.blueflag.shisaquick.srv.datacheck;

import jp.co.blueflag.shisaquick.srv.base.DataCheck;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsKindBean;
import jp.co.blueflag.shisaquick.srv.base.SearchBaseDao;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ConstManager;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;
import jp.co.blueflag.shisaquick.srv.common.ExceptionManager.ExceptionKind;

import java.util.List;

/**
 * 
 * �yS2- SA500�z �o�^�i���@�R�s�[�jDB�`�F�b�N
 * @author k-katayama
 * @since  2009/05/21
 */
public class SeihoNoTourokuDataCheck extends DataCheck{

	private SearchBaseDao searchDB_Seiho = null;

	/**
	 *  �o�^�i���@�R�s�[�j����DB�`�F�b�N�����p�R���X�g���N�^ 
	 */
	public SeihoNoTourokuDataCheck() {
		//���N���X�̃R���X�g���N�^
		super();

		//�C���X�^���X�ϐ��̏�����
		searchDB_Seiho = null;
	}

	/**
	 * �o�^�i���@�R�s�[�j����DB�`�F�b�N
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

		try {
			//�����R�[�h�̃`�F�b�N�i���������ȊO�̓G���[�j
			checkGenryoCd(reqData);

			//��ʃR�[�h�̃`�F�b�N�i���@�x���ɖ��o�^�̎�ʃR�[�h�̓G���[�j
			checkShubetuCd(reqData);
			
		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {
		}
		
	}
	
	/**
	 * ��ʃR�[�h�̃`�F�b�N
	 * @param reqData : ���N�G�X�g�f�[�^
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private void checkShubetuCd(
			RequestResponsKindBean reqData
			) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		StringBuffer strSql1 = new StringBuffer();
		StringBuffer strSql2 = new StringBuffer();
		StringBuffer strSql3 = new StringBuffer();
		List<?> lstRecset = null;

		try {
			//����i�f�[�^�擾�pSQL���쐬
			strSql1 = createShisakuSQL(reqData, strSql1);
			
			//����i�f�[�^�擾�pSQL�����s
			super.createSearchDB();
			lstRecset = searchBD.dbSearch(strSql1.toString());
			Object[] items = (Object[]) lstRecset.get(0);
			
			//DB�Z�b�V�����̐ݒ�
			searchDB_Seiho = new SearchBaseDao(ConstManager.CONST_XML_PATH_DB2);

			//��ʃR�[�h�i1���ځj�`�F�b�N�pSQL���쐬
			strSql2 = createCheckShubetuCd1SQL(items[0].toString(), strSql2);
			
			//��ʃR�[�h�i1���ځj�`�F�b�N�pSQL�����s
			lstRecset = searchDB_Seiho.dbSearch(strSql2.toString());
			
			//�������ʂ��Ȃ��ꍇ
			if (lstRecset.size() == 0){
				em.ThrowException(ExceptionKind.���Exception,"E000306", "��ʃR�[�h", items[0].toString() + items[1].toString(), "");
			}

			//��ʃR�[�h�i2�`3���ځj�`�F�b�N�pSQL���쐬
			strSql3 = createCheckShubetuCd2SQL(items[1].toString(), strSql3);
			
			//��ʃR�[�h�i2�`3���ځj�`�F�b�N�pSQL�����s
			lstRecset = searchDB_Seiho.dbSearch(strSql3.toString());
			
			//�������ʂ��Ȃ��ꍇ
			if (lstRecset.size() == 0){
				em.ThrowException(ExceptionKind.���Exception,"E000306", "��ʃR�[�h", items[0].toString() + items[1].toString(), "");
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
			if (searchDB_Seiho != null) {
				//�Z�b�V�����̃N���[�Y
				searchDB_Seiho.Close();
				searchDB_Seiho = null;
			}
			
			//�ϐ��̍폜
			strSql1 = null;
			strSql2 = null;
			strSql3 = null;
		}
		
	}

	/**
	 * �����R�[�h�̃`�F�b�N
	 * @param reqData : ���N�G�X�g�f�[�^
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private void checkGenryoCd(
			RequestResponsKindBean reqData
			) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		StringBuffer strSql = new StringBuffer();
		List<?> lstRecset = null;

		try {
			//�z���f�[�^�擾�pSQL���쐬
			strSql = createCheckGenryoCdSQL(reqData, strSql);
			
			//�z���f�[�^�擾�pSQL�����s
			super.createSearchDB();
			lstRecset = searchBD.dbSearch(strSql.toString());

			//�V�K���������݂���ꍇ
			if (lstRecset.size() > 0) {
				em.ThrowException(ExceptionKind.���Exception,"E000307", "", "", "");
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
	 * ����i�f�[�^�擾SQL�쐬
	 * @param reqData�F���N�G�X�g�f�[�^
	 * @param strSql�F�쐬SQL
	 * @return strSql�F�쐬SQL
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private StringBuffer createShisakuSQL(RequestResponsKindBean reqData, StringBuffer strSql) 
		throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		try {
			// �@�\���N�G�X�g�f�[�^�̎擾
			String strShainCd = reqData.getFieldVale(0, 0, "cd_shain");
			String strNen = reqData.getFieldVale(0, 0, "nen");
			String strOiNo = reqData.getFieldVale(0, 0, "no_oi");
			
			//SQL���̍쐬
			strSql.append("SELECT");
			strSql.append("  cd_shubetu");
			strSql.append(" ,RIGHT('00' + CONVERT(VARCHAR,no_shubetu),2) as no_shubetu");
			strSql.append(" FROM");
			strSql.append("  tr_shisakuhin");
			strSql.append(" WHERE cd_shain = " + strShainCd);
			strSql.append(" AND nen = " + strNen);
			strSql.append(" AND no_oi = " + strOiNo);

		} catch (Exception e) {
			this.em.ThrowException(e, "����i�f�[�^�擾SQL�쐬���������s���܂����B");
		} finally {
		}
		
		return strSql;
	}

	/**
	 * ��ʃR�[�h�i1���ځj���݃`�F�b�NSQL�쐬
	 * @param strShubetuCd�F��ʃR�[�h�i1���ځj
	 * @param strSql�F�쐬SQL
	 * @return strSql�F�쐬SQL
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private StringBuffer createCheckShubetuCd1SQL(String strShubetuCd, StringBuffer strSql) 
		throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		try {
			//SQL���̍쐬
			strSql.append("SELECT");
			strSql.append("  cd_hin_syurui");
			strSql.append(" FROM");
			strSql.append("  ma_hin_syurui");
			strSql.append(" WHERE cd_hin_syurui = '" + strShubetuCd + "' COLLATE Japanese_CS_AS");

		} catch (Exception e) {
			this.em.ThrowException(e, "��ʃR�[�h�i1���ځj���݃`�F�b�NSQL�쐬���������s���܂����B");
		} finally {
		}
		
		return strSql;
	}

	/**
	 * ��ʃR�[�h�i2�`3���ځj���݃`�F�b�NSQL�쐬
	 * @param strShubetuCd�F��ʃR�[�h�i2�`3���ځj
	 * @param strSql�F�쐬SQL
	 * @return strSql�F�쐬SQL
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private StringBuffer createCheckShubetuCd2SQL(String strShubetuCd, StringBuffer strSql) 
		throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		try {
			//SQL���̍쐬
			strSql.append("SELECT");
			strSql.append("  cd_code");
			strSql.append(" FROM");
			strSql.append("  ma_name");
			strSql.append(" WHERE cd_bunrui = 25");
			strSql.append(" AND cd_code = RIGHT('00' + '" + strShubetuCd + "',2)");

		} catch (Exception e) {
			this.em.ThrowException(e, "��ʃR�[�h�i2�`3���ځj���݃`�F�b�NSQL�쐬���������s���܂����B");
		} finally {
		}
		
		return strSql;
	}

	/**
	 * �V�K�����R�[�h���݃`�F�b�NSQL�쐬
	 * @param reqData�F���N�G�X�g�f�[�^
	 * @param strSql�F�쐬SQL
	 * @return strSql�F�쐬SQL
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private StringBuffer createCheckGenryoCdSQL(RequestResponsKindBean reqData, StringBuffer strSql)
		throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		try {
			// �@�\���N�G�X�g�f�[�^�̎擾
			String strShainCd = reqData.getFieldVale(0, 0, "cd_shain");
			String strNen = reqData.getFieldVale(0, 0, "nen");
			String strOiNo = reqData.getFieldVale(0, 0, "no_oi");
			
			//SQL���̍쐬
			strSql.append("SELECT");
			strSql.append("  cd_genryo");
			strSql.append(" FROM");
			strSql.append("  tr_haigo");
			strSql.append(" WHERE cd_shain = " + strShainCd);
			strSql.append(" AND nen = " + strNen);
			strSql.append(" AND no_oi = " + strOiNo);
			strSql.append(" AND LEFT(cd_genryo,1) = 'N'");

		} catch (Exception e) {
			this.em.ThrowException(e, "�V�K�����R�[�h���݃`�F�b�NSQL�쐬���������s���܂����B");
		} finally {
		}
		
		return strSql;
	}

}
