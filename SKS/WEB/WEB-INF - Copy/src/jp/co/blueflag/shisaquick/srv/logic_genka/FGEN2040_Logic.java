package jp.co.blueflag.shisaquick.srv.logic_genka;

import jp.co.blueflag.shisaquick.srv.base.LogicBase;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsKindBean;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsTableBean;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ConstManager;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;
import java.util.List;

/**
 * �yQP@00342�z
 * �S���҃}�X�^�����e�i�c�Ɓj�@��������
 *  : ���݃X�e�[�^�X�����擾����B
 *  �@�\ID�FFGEN2040
 *  
 * @author Nishigawa
 * @since  2011/01/28
 */
public class FGEN2040_Logic extends LogicBase{
	
	/**
	 * �S���҃}�X�^�����e�i�c�Ɓj�@��������
	 * : �C���X�^���X����
	 */
	public FGEN2040_Logic() {
		//���N���X�̃R���X�g���N�^
		super();

	}
	
	//--------------------------------------------------------------------------------------------------------------
	//
	//                                                                     ExecLogic�i�����J�n�j 
	//
	//--------------------------------------------------------------------------------------------------------------
	
	/**
	 * �S���҃}�X�^�����e�i�c�Ɓj�@��������
	 *  : �������������擾����B
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
			
			//���X�|���X�f�[�^�i�@�\�j����
			resKind = new RequestResponsKindBean();
			
			//�@�\ID�̐ݒ�
			String strKinoId = reqData.getID();
			resKind.setID(strKinoId);
			
			//���X�|���X�f�[�^�̌`��
			this.genkaKihonSetting(resKind, reqData);
			
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
			strSqlBuf = this.createGenkaKihonSQL(reqData);
			
			//���ʃN���X�@�f�[�^�x�[�X������p����SQL���s
			super.createSearchDB();
			lstRecset = searchDB.dbSearch(strSqlBuf.toString());
			
			//���X�|���X�f�[�^�Ƀe�[�u���ǉ�
			resKind.addTableItem(strTblNm);
			
			//�ǉ������e�[�u���փ��R�[�h�i�[
			this.storageGenkaKihon(lstRecset, resKind.getTableItem(strTblNm));
			
		} catch (Exception e) {
			this.em.ThrowException(e, "�S���҃}�X�^�����e�i�c�Ɓj�@�����������������s���܂����B");
			
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
	private StringBuffer createGenkaKihonSQL(RequestResponsKindBean reqData )
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		//SQL�i�[�p
		StringBuffer strSql = new StringBuffer();
		String strUKinoId  = "";
		
		try {
			
			//SQL���̍쐬
			strSql.append(" SELECT cd_kengen  ");
			strSql.append("       ,nm_kengen  ");
			strSql.append(" FROM ma_kengen  ");
			
			//�c�Ɓi��ʁj�����R�[�h�擾
			String strEigyoIppan = 
				ConstManager.getConstValue(ConstManager.Category.�ݒ�,"EIGYO_KENGEN_IPPAN");
			//�c�Ɓi�{�������j�����R�[�h�擾
			String strEigyoHonbu = 
				ConstManager.getConstValue(ConstManager.Category.�ݒ�,"EIGYO_KENGEN_HONBU");
			//�c�Ɓi�V�X�e���Ǘ��ҁj�����R�[�h�擾
			String strEigyoSystem = 
				ConstManager.getConstValue(ConstManager.Category.�ݒ�,"EIGYO_KENGEN_SYSTEM");
			
			//�����f�[�^ID�擾
			for (int i = 0; i < userInfoData.getId_gamen().size(); i++) {
				if (userInfoData.getId_gamen().get(i).toString().equals("200")){
					//�@�\ID��ݒ�
					strUKinoId = userInfoData.getId_kino().get(i).toString(); 
				}
			}
			
			//�������u�ҏW�i��ʁj�v�̏ꍇ
			if(strUKinoId.equals("100")){
				//WHERE��ݒ�
				strSql.append(" WHERE cd_kengen = " + strEigyoIppan);
				
			}
			//�������u�ҏW�i���o�^���[�U�j�v�̏ꍇ
			else if(strUKinoId.equals("101")){
				//WHERE��ݒ�
				strSql.append(" WHERE cd_kengen = " + strEigyoIppan);
				
			}
			//�������u�ҏW�i�{�������j�v�̏ꍇ
			else if(strUKinoId.equals("102")){
				//���N�G�X�g����f�[�^���o
				String id_user = reqData.getFieldVale(0, 0, "id_user");
				if(id_user.equals(userInfoData.getId_user())){
					//WHERE��ݒ�
					strSql.append(" WHERE cd_kengen = " + strEigyoHonbu);
				}
				else{
					//WHERE��ݒ�
					strSql.append(" WHERE cd_kengen = " + strEigyoIppan);
				}
			}
			//�������u�ҏW�i�V�X�e���Ǘ��ҁj�v�̏ꍇ
			else if(strUKinoId.equals("103")){
				//WHERE��ݒ�
				strSql.append(" WHERE cd_kengen = " + strEigyoIppan);
				strSql.append(" OR cd_kengen = " + strEigyoHonbu);
				strSql.append(" OR cd_kengen = " + strEigyoSystem);
				
			}
			
			
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
			//�c�Ɓi��ʁj�����R�[�h�擾
			String strEigyoIppan = 
				ConstManager.getConstValue(ConstManager.Category.�ݒ�,"EIGYO_KENGEN_IPPAN");
			//�c�Ɓi�{�������j�����R�[�h�擾
			String strEigyoHonbu = 
				ConstManager.getConstValue(ConstManager.Category.�ݒ�,"EIGYO_KENGEN_HONBU");
			//�c�Ɓi�V�X�e���Ǘ��ҁj�����R�[�h�擾
			String strEigyoSystem = 
				ConstManager.getConstValue(ConstManager.Category.�ݒ�,"EIGYO_KENGEN_SYSTEM");
			
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
				resTable.addFieldVale(i, "cd_kengen", toString(items[0],""));
				resTable.addFieldVale(i, "nm_kengen", toString(items[1],""));
				resTable.addFieldVale(i, "roop_cnt", Integer.toString(lstGenkaHeader.size()));
				resTable.addFieldVale(i, "cd_kengen_ippan", strEigyoIppan);
				resTable.addFieldVale(i, "cd_kengen_honbu", strEigyoHonbu);
				resTable.addFieldVale(i, "cd_kengen_system", strEigyoSystem);
			}

		} catch (Exception e) {
			this.em.ThrowException(e, "");
			
		} finally {

		}
		
	}
	
}
