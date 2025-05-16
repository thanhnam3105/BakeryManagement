package jp.co.blueflag.shisaquick.srv.datacheck;

import jp.co.blueflag.shisaquick.srv.base.DataCheck;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsKindBean;
import jp.co.blueflag.shisaquick.srv.base.SearchBaseDao;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.base.BaseDao.DBCategory;
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
			//��ʃR�[�h�̃`�F�b�N�i���@�x���ɖ��o�^�̎�ʃR�[�h�̓G���[�j
			checkShubetuCd(reqData);
			//�����R�[�h�̃`�F�b�N	//2009/08/03 ADD �ۑ�285�̑Ή�
			checkGenryoCd(reqData);
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
			//���N�G�X�g�f�[�^�擾
			String strCd_shubetu = reqData.getFieldVale(0, 0, "cd_shubetu");
			String strNo_shubetu = reqData.getFieldVale(0, 0, "no_shubetu");
			
			//DB�Z�b�V�����̐ݒ�
			searchDB_Seiho = new SearchBaseDao(DBCategory.DB2);
			super.createSearchDB();

			//��ʃR�[�h�i1���ځj�`�F�b�N�pSQL���쐬
			strSql2 = createCheckShubetuCd1SQL(strCd_shubetu, strSql2);
			
			//��ʃR�[�h�i1���ځj�`�F�b�N�pSQL�����s
			lstRecset = searchDB_Seiho.dbSearch(strSql2.toString());
			
			//�������ʂ��Ȃ��ꍇ
			if (lstRecset.size() == 0){
				em.ThrowException(ExceptionKind.���Exception,"E000306", "��ʃR�[�h", strCd_shubetu + strNo_shubetu, "");
			}
			
			//��ʃR�[�h�i2�`3���ځj�`�F�b�N�pSQL���쐬
			strSql3 = createCheckShubetuCd2SQL(strNo_shubetu, strSql3);
			//��ʃR�[�h�i2�`3���ځj�`�F�b�N�pSQL�����s
			lstRecset = searchBD.dbSearch(strSql3.toString());
			
			//�������ʂ��Ȃ��ꍇ
			if (lstRecset.size() == 0){
				em.ThrowException(ExceptionKind.���Exception,"E000306", "��ʔԍ�", strCd_shubetu + strNo_shubetu, "");
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
			strSql.append("  nm_literal");
			strSql.append(" FROM");
			strSql.append("  ma_literal");
			strSql.append(" WHERE cd_category = 'K_syubetuno'");
			strSql.append(" AND nm_literal =  '" + strShubetuCd + "'");

		} catch (Exception e) {
			this.em.ThrowException(e, "��ʃR�[�h�i2�`3���ځj���݃`�F�b�NSQL�쐬���������s���܂����B");
		} finally {
		}
		
		return strSql;
	}

	/**
	 * 2009/08/03 ADD �ۑ�285�̑Ή�
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
			//���샊�X�g�f�[�^�擾SQL�쐬���\�b�h���Ăяo���B
			strSql = searchShisakuListSQL(reqData);

			//�擾����SQL�������s����B
			super.createSearchDB();
			lstRecset = searchBD.dbSearch(strSql.toString());

			//���s���ʂɊY���f�[�^�����݂���ꍇ�A�G���[���X���[����B
			if (lstRecset.size() != 0){
				em.ThrowException(ExceptionKind.���Exception, "E000307", "����f�[�^��� �z���\ �����R�[�h", "", "");
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
	 * 2009/08/03 ADD �ۑ�285�̑Ή�
	 * ���샊�X�g�f�[�^�擾SQL�쐬
	 * @param reqData : ���N�G�X�g�f�[�^
	 * @return strSql�F�쐬SQL
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private StringBuffer searchShisakuListSQL(RequestResponsKindBean reqData) 
		throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		StringBuffer strSql = new StringBuffer();

		String strGenryocd = "";
		
		try {
			//SQL���̍쐬
			strSql.append("SELECT");
			strSql.append("  TH.cd_shain");
			strSql.append(" ,TH.nen");
			strSql.append(" ,TH.no_oi");
			strSql.append(" ,TSL.seq_shisaku");
			strSql.append(" ,TSL.quantity");
			strSql.append(" FROM");
			strSql.append("  tr_haigo TH");
			strSql.append(" INNER JOIN");
			strSql.append("  tr_shisaku_list TSL");
			strSql.append(" ON  TH.cd_shain = TSL.cd_shain");
			strSql.append(" AND TH.nen = TSL.nen");
			strSql.append(" AND TH.no_oi = TSL.no_oi");
			strSql.append(" AND TH.cd_kotei = TSL.cd_kotei");
			strSql.append(" AND TH.seq_kotei = TSL.seq_kotei");
			strSql.append(" WHERE TH.cd_shain = " + reqData.getFieldVale(0, 0, "cd_shain") );
			strSql.append(" AND TH.nen = " + reqData.getFieldVale(0, 0, "nen") );
			strSql.append(" AND TH.no_oi = " + reqData.getFieldVale(0, 0, "no_oi") );
			strSql.append(" AND TSL.seq_shisaku = " + reqData.getFieldVale(0, 0, "seq_shisaku") );
			for ( int i=0; i<reqData.getCntRow("tr_haigo"); i++ ) {
				if (!(strGenryocd.equals(""))) {
					strGenryocd = strGenryocd + ",";
				}
				strGenryocd = strGenryocd + "'" + reqData.getFieldVale("tr_haigo", i, "cd_genryo") + "'";
			}
			strSql.append(" AND TH.cd_genryo IN (" + strGenryocd + ")" );
			strSql.append(" AND LEFT(TH.cd_genryo, 1) = 'N'");
			strSql.append(" AND ISNULL(TSL.quantity, 0) <> 0");

		} catch (Exception e) {
			this.em.ThrowException(e, "���샊�X�g�f�[�^�擾SQL�쐬���������s���܂����B");
		} finally {
		}
		
		return strSql;
	}

}
