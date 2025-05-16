package jp.co.blueflag.shisaquick.srv.logic;

import java.util.List;

import jp.co.blueflag.shisaquick.srv.base.LogicBase;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsKindBean;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsTableBean;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;
import jp.co.blueflag.shisaquick.srv.common.ExceptionManager.ExceptionKind;

/**
 * 
 * [SA840] JWS-���Z�m�藚�������c�a�����̎���
 *  
 * @author TT.k-katayama
 * @since  2009/06/10
 *
 */
public class ShisanRirekiSearchLogic extends LogicBase {
	
	/**
	 * �R���X�g���N�^
	 */
	public ShisanRirekiSearchLogic() {
		//���N���X�̃R���X�g���N�^
		super();

	}
	
	/**
	 * JWS-���Z�m�藚�������c�a�������W�b�N�Ǘ�
	 * @param reqData : �@�\���N�G�X�g�f�[�^
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

		//���X�|���X�f�[�^�N���X
		RequestResponsKindBean resKind = null;
		
		//�����pSQL
		StringBuffer strSql = null;
		//�������ʃf�[�^���X�g
		List<?> lstRecset = null;
		
		try {
			//���X�|���X�f�[�^�i�@�\�j����
			resKind = new RequestResponsKindBean();

			//�@ : ���Z�m�藚�������pSQL���쐬
			strSql = this.createRirekiSearchSQL(reqData);
			
			//�A : SQL�����s
			super.createSearchDB();
			lstRecset = this.searchDB.dbSearch(strSql.toString());
						
			//�B : �������ʂ������ꍇ
			if ( lstRecset.size() == 0 ) {
				this.em.ThrowException(ExceptionKind.�x��Exception,"W000401", strSql.toString(), "", "");
				
			}
			
			//�C : ���Z�����f�[�^�p�����[�^�[�i�[�������Ăяo���A���X�|���X�f�[�^�ɐݒ肷��
			
			//�@�\ID�̐ݒ�
			String strKinoId = reqData.getID();
			resKind.setID(strKinoId);

			//�e�[�u�����̐ݒ�
			String strTableNm = reqData.getTableID(0);
			resKind.addTableItem(strTableNm);

			//�p�����[�^�i�[
			this.storageResponsData(lstRecset, resKind.getTableItem(strTableNm));

		} catch (Exception e) {
			this.em.ThrowException(e, "JWS-���Z�m�藚�������c�a���������s���܂����B");

		} finally {
			//���X�g�̔j��
			removeList(lstRecset);
			
			//�Z�b�V�����̉��
			if (this.searchDB != null) {
				this.searchDB.Close();
				this.searchDB = null;
				
			}
			
			//�ϐ��̍폜
			strSql = null;

		}
		return resKind;

	}

	/**
	 * ���Z���������pSQL�̍쐬
	 * @param reqData : �@�\���N�G�X�g�f�[�^
	 * @return strSql : �쐬SQL
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private StringBuffer createRirekiSearchSQL(RequestResponsKindBean reqData) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		StringBuffer ret = new StringBuffer();
		
		String strShainCd = null;
		String strNen = null;
		String strNoOi = null;		
		
		try { 

			//���N�G�X�g�f�[�^��茟���p�p�����[�^���擾����
			strShainCd = reqData.getFieldVale(0, 0, "cd_shain");
			strNen = reqData.getFieldVale(0, 0, "nen");
			strNoOi = reqData.getFieldVale(0, 0, "no_oi");
			
			//SQL���쐬����
			ret.append(" SELECT DISTINCT ");
			ret.append("   T142.cd_shain AS cd_shain ");
			ret.append("  ,T142.nen AS nen ");
			ret.append("  ,T142.no_oi AS no_oi ");
			ret.append("  ,T142.seq_shisaku AS seq_shisaku ");
			ret.append("  ,T142.sort_rireki AS sort_rireki ");
			ret.append("  ,T142.nm_sample AS nm_sample ");
			ret.append("  ,CONVERT(VARCHAR,T142.dt_shisan,111) AS dt_shisan ");
			ret.append("  ,T142.id_toroku AS id_toroku ");
			ret.append("  ,T142.dt_toroku AS dt_toroku ");
			ret.append(" FROM tr_shisan_rireki T142 ");
			ret.append(" WHERE T142.cd_shain=");
			ret.append(strShainCd);
			ret.append("  AND T142.nen=");
			ret.append(strNen);
			ret.append("  AND T142.no_oi=");
			ret.append(strNoOi);
			ret.append("  ORDER BY T142.sort_rireki, T142.seq_shisaku ");
			
		} catch(Exception e) {
			this.em.ThrowException(e, "");			
			
		} finally {
			
		}
		return ret;
		
	}

	/**
	 * ���Z�����f�[�^�p�����[�^�[�i�[����
	 * @param lstRecdata : �������ʃf�[�^���X�g
	 * @param resTable : ���X�|���X�e�[�u��
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private void storageResponsData(List<?> lstRecdata, RequestResponsTableBean resTable)
		throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		try { 
			//�@�F���X�|���X�f�[�^���`������B
			
			for ( int i=0; i<lstRecdata.size(); i++ ) {
				
				//�������ʂ̊i�[
				resTable.addFieldVale(i, "flg_return", "true");
				resTable.addFieldVale(i, "msg_error", "");
				resTable.addFieldVale(i, "no_errmsg", "");
				resTable.addFieldVale(i, "nm_class", "");
				resTable.addFieldVale(i, "cd_error", "");
				resTable.addFieldVale(i, "msg_system", "");
				
				Object[] items = (Object[])lstRecdata.get(i);
				
				resTable.addFieldVale(i, "cd_shain", toString(items[0]));
				resTable.addFieldVale(i, "nen", toString(items[1]));
				resTable.addFieldVale(i, "no_oi", toString(items[2]));
				resTable.addFieldVale(i, "seq_shisaku", toString(items[3]));
				resTable.addFieldVale(i, "sort_rireki", toString(items[4]));
				resTable.addFieldVale(i, "nm_sample", toString(items[5]));;
				resTable.addFieldVale(i, "dt_shisan", toString(items[6]));
				resTable.addFieldVale(i, "id_toroku", toString(items[7]));
				resTable.addFieldVale(i, "dt_toroku", toString(items[8]));
				
			}			
			
		} catch(Exception e) {
			this.em.ThrowException(e, "");			
			
		} finally {
			
		}
				
	}
	
}
