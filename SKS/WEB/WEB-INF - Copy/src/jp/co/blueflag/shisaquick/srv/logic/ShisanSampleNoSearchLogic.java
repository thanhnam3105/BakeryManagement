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
 * [SA830] JWS-���Z�m��T���v��No�����c�a�����̎���
 *  
 * @author TT.k-katayama
 * @since  2009/06/10
 *
 */
public class ShisanSampleNoSearchLogic extends LogicBase {
	
	/**
	 * �R���X�g���N�^
	 */
	public ShisanSampleNoSearchLogic() {
		//���N���X�̃R���X�g���N�^
		super();

	}
	
	/**
	 * JWS-���Z�m��T���v��No�����c�a�������W�b�N�Ǘ�
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

			//�@ : ���Z�m��T���v��No�����pSQL���쐬
			strSql = this.createShisanNoSearchSQL(reqData);
			
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
			this.em.ThrowException(e, "JWS-���Z�m��T���v��No�����c�a���������s���܂����B");

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
		 * ���Z�m��T���v��No�����pSQL�̍쐬
		 * @param reqData : �@�\���N�G�X�g�f�[�^
		 * @return strSql : �쐬SQL
		 * @throws ExceptionWaning 
		 * @throws ExceptionUser 
		 * @throws ExceptionSystem 
		 */
		private StringBuffer createShisanNoSearchSQL(RequestResponsKindBean reqData) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
			
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
				ret.append(" SELECT  ");
				ret.append("  T141.cd_shain AS cd_shain ");
				ret.append(" ,T141.nen AS nen ");
				ret.append(" ,T141.no_oi AS no_oi ");
				ret.append(" ,T141.seq_shisaku AS seq_shisaku ");
				ret.append(" ,ISNULL(T141.nm_sample,'') AS nm_sample ");
				ret.append(" ,ISNULL(MAX(CONVERT(VARCHAR,T142.dt_shisan,111)),'') AS dt_shisan ");
				ret.append(" ,ISNULL(MAX(CONVERT(VARCHAR,T142.dt_shisan,20)),'') AS dt_shisan_time ");
				ret.append(" FROM tr_shisaku T141 ");
				ret.append(" LEFT JOIN tr_shisan_rireki T142 ");
				ret.append("  ON  T142.cd_shain = T141.cd_shain ");
				ret.append("  AND T142.nen = T141.nen ");
				ret.append("  AND T142.no_oi = T141.no_oi ");
				ret.append("  AND T142.seq_shisaku = T141.seq_shisaku ");
				ret.append(" WHERE T141.cd_shain=");
				ret.append(strShainCd);
				ret.append("  AND T141.nen=");
				ret.append(strNen);
				ret.append("  AND T141.no_oi=");
				ret.append(strNoOi);
				ret.append("  GROUP BY T141.cd_shain, T141.nen, T141.no_oi, ");
				ret.append("   T141.seq_shisaku, T141.nm_sample, T141.sort_shisaku ");
				ret.append("  ORDER BY T141.sort_shisaku ");
				
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
			
			String strLastShisakuSeq = "";
			long lngLastShisanDate = 0;
			
			try { 

				//�@�F�ŏI���Z�m��@����SEQ���擾����

				for ( int i=0; i<lstRecdata.size(); i++ ) {

					Object[] items = (Object[])lstRecdata.get(i);

					//���t���r���A�ł��傫�����t���擾����ŏI���Z����SEQ�̐ݒ�
					if ( !toString(items[6]).isEmpty() ) {
						
						//�擾�������t��[yyyyMMddhhmmss]�̌`���ɕϊ�
						String strDate = toString(items[6]);
						strDate = strDate.replaceAll(":", "");
						strDate = strDate.replaceAll("-", "");
						strDate = strDate.replaceAll(" ", "");
						long dblShisanDate = Long.parseLong(strDate);
						
						//���t���r���A�傫�����t�̎���SEQ��ݒ肷��
						if ( lngLastShisanDate < dblShisanDate ) {
							lngLastShisanDate = dblShisanDate;			//���t
							strLastShisakuSeq = toString(items[3]);		//����SEQ
							
						}
						
					}
				}
				
				
				//�A�F���X�|���X�f�[�^���`������B
				
				for ( int i=0; i<lstRecdata.size(); i++ ) {
					
					//�������ʂ̊i�[
					resTable.addFieldVale(i, "flg_return", "true");
					resTable.addFieldVale(i, "msg_error", "");
					resTable.addFieldVale(i, "no_errmsg", "");
					resTable.addFieldVale(i, "nm_class", "");
					resTable.addFieldVale(i, "cd_error", "");
					resTable.addFieldVale(i, "msg_system", "");
					
					Object[] items = (Object[])lstRecdata.get(i);

					resTable.addFieldVale(i, "seq_shisaku", toString(items[3]));
					resTable.addFieldVale(i, "nm_sample", toString(items[4]));
					resTable.addFieldVale(i, "dt_shisan", toString(items[5]));

					//�ŏI���Z����SEQ�̐ݒ�
					resTable.addFieldVale(i, "seq_shisaku_last", strLastShisakuSeq);
					
				}
				
			} catch(Exception e) {
				this.em.ThrowException(e, "");			
				
			} finally {
				
			}
					
		}
			
}
