package jp.co.blueflag.shisaquick.srv.logic_genka;

import jp.co.blueflag.shisaquick.srv.base.LogicBase;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsKindBean;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsTableBean;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;
import java.util.List;

/**
 * �yQP@00342�z
 * �S���҃}�X�^�����e�i�c�Ɓj�@��Ќ����i�c�ƕ����̂݁j
 *  : ���݃X�e�[�^�X�����擾����B
 *  �@�\ID�FFGEN2050
 *  
 * @author Nishigawa
 * @since  2011/01/28
 */
public class FGEN2050_Logic extends LogicBase{
	
	/**
	 * �S���҃}�X�^�����e�i�c�Ɓj�@��Ќ����i�c�ƕ����̂݁j
	 * : �C���X�^���X����
	 */
	public FGEN2050_Logic() {
		//���N���X�̃R���X�g���N�^
		super();

	}
	
	//--------------------------------------------------------------------------------------------------------------
	//
	//                                                                     ExecLogic�i�����J�n�j 
	//
	//--------------------------------------------------------------------------------------------------------------
	
	/**
	 * �S���҃}�X�^�����e�i�c�Ɓj�@��Ќ����i�c�ƕ����̂݁j
	 *  : ��Ќ����i�c�ƕ����̂݁j�����擾����B
	 * @param reqData : ���N�G�X�g�f�[�^
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

		List<?> lstRecset = null;

		RequestResponsKindBean resKind = null;
		
		try {
			// ADD 2013/11/7 QP@30154 okano start
			//�@�\���N�G�X�g�f�[�^��胆�[�UID�Ɖ��ID���擾
			String GamenId = "200";
			String KinoId = null;
			
			//�����f�[�^ID�擾
			for (int i = 0; i < userInfoData.getId_gamen().size(); i++) {
				if (userInfoData.getId_gamen().get(i).toString().equals(GamenId)){
					//�@�\ID��ݒ�
					KinoId = userInfoData.getId_kino().get(i).toString();
				}
			}
			// ADD 2013/11/7 QP@30154 okano end
			
			//���X�|���X�f�[�^�i�@�\�j����
			resKind = new RequestResponsKindBean();
			
			//�@�\ID�̐ݒ�
			String strKinoId = reqData.getID();
			resKind.setID(strKinoId);
			
			//���X�|���X�f�[�^�̌`��
			// MOD 2013/11/7 QP@30154 okano start
//				this.genkaKihonSetting(resKind, reqData);
			this.genkaKihonSetting(resKind, reqData, KinoId, userInfoData);
			// MOD 2013/11/7 QP@30154 okano end
			
		} catch (Exception e) {
			this.em.ThrowException(e, "");

		} finally {
			//���X�g�̔j��
			removeList(lstRecset);
		
			if (searchDB != null) {
				//�Z�b�V�����̃N���[�Y
				searchDB.Close();
				searchDB = null;
			}

			//�ϐ��̍폜
			
		}
		return resKind;
		
	}
	
	//--------------------------------------------------------------------------------------------------------------
	//
	//                                                         DataSetting�i���X�|���X�f�[�^�̌`���j 
	//
	//--------------------------------------------------------------------------------------------------------------
	/**
	 * ���X�|���X�f�[�^�̌`��
	 * @param resKind : ���X�|���X�f�[�^
	 * @param reqData : ���N�G�X�g�f�[�^
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 *  
	 * @author TT.Nishigawa
	 * @since  2009/10/21
	 */
	private void genkaKihonSetting(
			
			 RequestResponsKindBean resKind
			,RequestResponsKindBean reqData 
			// ADD 2013/11/7 QP@30154 okano start
			,String KinoId
			,UserInfoData userInfoData
			// ADD 2013/11/7 QP@30154 okano end
			)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		//���R�[�h�l�i�[���X�g
		List<?> lstRecset = null;	
		
		//���R�[�h�l�i�[���X�g
		StringBuffer strSqlBuf = null;

		try {
			//�e�[�u����
			String strTblNm = "table";	
			
			//�f�[�^�擾SQL�쐬
			// MOD 2013/11/7 QP@30154 okano start
//				strSqlBuf = this.createGenkaKihonSQL();
			strSqlBuf = this.createGenkaKihonSQL(KinoId, userInfoData);
			// MOD 2013/11/7 QP@30154 okano end
			
			//���ʃN���X�@�f�[�^�x�[�X������p����SQL���s
			super.createSearchDB();
			lstRecset = searchDB.dbSearch(strSqlBuf.toString());
			
			//���X�|���X�f�[�^�Ƀe�[�u���ǉ�
			resKind.addTableItem(strTblNm);
			
			//�ǉ������e�[�u���փ��R�[�h�i�[
			this.storageGenkaKihon(lstRecset, resKind.getTableItem(strTblNm));
			
		} catch (Exception e) {
			this.em.ThrowException(e, "�S���҃}�X�^�����e�i�c�Ɓj�@��Ќ����i�c�ƕ����̂݁j���������s���܂����B");
			
		} finally {
			//���X�g�̔j��
			removeList(lstRecset);
			
			if (searchDB != null) {
				//�Z�b�V�����̉��
				this.searchDB.Close();
				searchDB = null;
				
			}

			//�ϐ��̍폜
			strSqlBuf = null;

		}
		
	}
	
	
	//--------------------------------------------------------------------------------------------------------------
	//
	//                                                                  createSQL�iSQL�������j 
	//
	//--------------------------------------------------------------------------------------------------------------
	/**
	 * �f�[�^�擾SQL�쐬
	 * @param reqData�F���N�G�X�g�f�[�^
	 * @param strSql�F��������SQL
	 * @return strSql�F��������SQL
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	// MOD 2013/11/7 QP@30154 okano start
//		private StringBuffer createGenkaKihonSQL()
	private StringBuffer createGenkaKihonSQL(String KinoId, UserInfoData userInfoData)
	// MOD 2013/11/7 QP@30154 okano send
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		//SQL�i�[�p
		StringBuffer strSql = new StringBuffer();
		
		try {
			
			//SQL���̍쐬
			strSql.append(" SELECT DISTINCT  ");
			strSql.append(" 	   cd_kaisha  ");
			strSql.append("       ,nm_kaisha  ");
			strSql.append(" FROM ma_busho  ");
			strSql.append(" where flg_eigyo=1  ");
			// ADD 2013/11/7 QP@30154 okano start
			if(KinoId.equals("100")){
				strSql.append(" and cd_kaisha = ");
				strSql.append(userInfoData.getCd_kaisha());
				
			} else if(KinoId.equals("102")){
				strSql.append(" and cd_kaisha = ");
				strSql.append(userInfoData.getCd_kaisha());
				
			} else {
				
			}
			// ADD 2013/11/7 QP@30154 okano end
			
		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
		return strSql;
	}
	
	//--------------------------------------------------------------------------------------------------------------
	//
	//                                                                  storageData�i�p�����[�^�[�i�[�j 
	//
	//--------------------------------------------------------------------------------------------------------------
	/**
	 * �p�����[�^�[�i�[
	 *  : ���X�|���X�f�[�^�֊i�[����B
	 * @param lstGenkaHeader : �������ʏ�񃊃X�g
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private void storageGenkaKihon(
			
			  List<?> lstGenkaHeader
			, RequestResponsTableBean resTable
			) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try {
			for (int i = 0; i < lstGenkaHeader.size(); i++) {

				//�������ʂ̊i�[
				resTable.addFieldVale(i, "flg_return", "true");
				resTable.addFieldVale(i, "msg_error", "");
				resTable.addFieldVale(i, "no_errmsg", "");
				resTable.addFieldVale(i, "nm_class", "");
				resTable.addFieldVale(i, "cd_error", "");
				resTable.addFieldVale(i, "msg_system", "");
				
				Object[] items = (Object[]) lstGenkaHeader.get(i);

				//���ʂ����X�|���X�f�[�^�Ɋi�[
				resTable.addFieldVale(i, "cd_kaisha", toString(items[0],""));
				resTable.addFieldVale(i, "nm_kaisha", toString(items[1],""));
				resTable.addFieldVale(i, "roop_cnt", Integer.toString(lstGenkaHeader.size()));
			}

		} catch (Exception e) {
			this.em.ThrowException(e, "");
			
		} finally {

		}
		
	}
	
}
