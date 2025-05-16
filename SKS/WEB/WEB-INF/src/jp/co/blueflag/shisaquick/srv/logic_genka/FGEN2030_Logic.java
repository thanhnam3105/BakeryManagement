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
 * �X�e�[�^�X�N���A�@�X�e�[�^�X�N���A���s
 *  : �X�e�[�^�X�N���A�����s����B
 *  �@�\ID�FFGEN2030
 *  
 * @author Nishigawa
 * @since  2011/01/25
 */
public class FGEN2030_Logic extends LogicBase{
	
	/**
	 * �X�e�[�^�X�N���A�@���݃X�e�[�^�X�擾����
	 * : �C���X�^���X����
	 */
	public FGEN2030_Logic() {
		//���N���X�̃R���X�g���N�^
		super();

	}
	
	//--------------------------------------------------------------------------------------------------------------
	//
	//                                                                     ExecLogic�i�����J�n�j 
	//
	//--------------------------------------------------------------------------------------------------------------
	
	/**
	 * �X�e�[�^�X�N���A�@���݃X�e�[�^�X�擾
	 *  : ���݃X�e�[�^�X�����擾����B
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
			String strTblNm = "kihon";	
			
			//�f�[�^�擾SQL�쐬
			strSqlBuf = this.createGenkaKihonSQL(reqData);
			
			//���ʃN���X�@�f�[�^�x�[�X������p����SQL���s
			super.createSearchDB();
			lstRecset = searchDB.dbSearch(strSqlBuf.toString());
			
			//DB�R�l�N�V��������
			super.createExecDB();		
			//�g�����U�N�V�����J�n
			this.searchDB.BeginTran();
			//�g�����U�N�V������execDB�Ƌ��L����B
			execDB.setSession(searchDB.getSession());
			
			//DB�X�V
			//SQL�����i�X�e�[�^�X����ð��ٍX�V�j
			strSqlBuf = null;
			strSqlBuf = makeExecSQL_StatusRireki(reqData,lstRecset);
			this.execDB.execSQL(strSqlBuf.toString());
			
			//SQL�����i�������Z�X�e�[�^�Xð��ٍX�V�j
			strSqlBuf = null;
			strSqlBuf = makeExecSQL_StatusGenka(reqData,lstRecset);
			this.execDB.execSQL(strSqlBuf.toString());
			
			//SQL�����i���Z�@����i�e�[�u���X�V�j�i�̗p�����NO�������j
			strSqlBuf = null;
			strSqlBuf = makeExecSQL_sisanShisakuhin(reqData,lstRecset);
			this.execDB.execSQL(strSqlBuf.toString());
			
			//�yH24�N�x�Ή��z2012/05/29 TT H.SHIMA ADD Start
			//SQL�����i���Z�@����e�[�u���X�V�j�i���Z���������j
			strSqlBuf = null;
			strSqlBuf = makeExecSQL_sisanShisaku(reqData,lstRecset);
			if(strSqlBuf != null && strSqlBuf.length() > 0){
				this.execDB.execSQL(strSqlBuf.toString());
			}
			//�yH24�N�x�Ή��z2012/05/29 TT H.SHIMA ADD Start
			
			//DB�R�~�b�g
			this.searchDB.Commit();
			
			//�B���X�|���X�f�[�^�Ƀe�[�u���ǉ�
			resKind.addTableItem(strTblNm);
			
			//�C�ǉ������e�[�u���փ��R�[�h�i�[
			this.storageGenkaKihon(lstRecset, resKind.getTableItem(strTblNm));
			
		} catch (Exception e) {
			//DB���[���o�b�N
			this.searchDB.Rollback();
			this.em.ThrowException(e, "�X�e�[�^�X�N���A�@�N���A���s���������s���܂����B");
			
		} finally {
			//���X�g�̔j��
			removeList(lstRecset);
			
			//DB�R�l�N�V�����J��
			if (searchDB != null) {
				searchDB.Close();
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
	private StringBuffer createGenkaKihonSQL(
			
			RequestResponsKindBean reqData
			)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		//SQL�i�[�p
		StringBuffer strSql = new StringBuffer();
		
		try {
			//���N�G�X�g����f�[�^���o
			String cd_clear = reqData.getFieldVale(0, 0, "cd_clear");
			
			//SQL���̍쐬
			strSql.append(" SELECT  ");
			strSql.append("   st_kenkyu  ");
			strSql.append("   ,st_seisan  ");
			strSql.append("   ,st_gensizai  ");
			strSql.append("   ,st_kojo  ");
			strSql.append("   ,st_eigyo  ");
			strSql.append(" FROM ma_status_clear  ");
			strSql.append(" WHERE cd_clear='" + cd_clear + "'  ");
			
		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
		return strSql;
	}
	
	/**
	 * �X�e�[�^�X����ð��ٍX�V
	 * @param reqData�F���N�G�X�g�f�[�^
	 * @param lstRecset�F��������
	 * @return strSql�F�X�VSQL
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private StringBuffer makeExecSQL_StatusRireki(
			RequestResponsKindBean reqData,
			List<?>  lstRecset
			)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		//SQL�i�[�p
		StringBuffer strSql = new StringBuffer();
		
		try {
			//���N�G�X�g����f�[�^���o
			String strCdShain = reqData.getFieldVale(0, 0, "cd_shain");
			String strNen = reqData.getFieldVale(0, 0, "nen");
			String strNoOi = reqData.getFieldVale(0, 0, "no_oi");
			String strNoEda = reqData.getFieldVale(0, 0, "no_eda");
			String cd_clear = reqData.getFieldVale(0, 0, "cd_clear");
			
			//���R�[�h�Z�b�g����f�[�^���o
			Object[] items = (Object[]) lstRecset.get(0);
			String st_kenkyu = toString(items[0],"");
			String st_seikan = toString(items[1],"");
			String st_gentyo = toString(items[2],"");
			String st_kojo = toString(items[3],"");
			String st_eigyo = toString(items[4],"");
			
			//�����Ɩ��@���e����CD�ݒ�
			String cd_li = "";
			if(cd_clear.equals("1")){
				cd_li = "90";
			}
			else if(cd_clear.equals("2")){
				cd_li = "91";
			}
			else if(cd_clear.equals("3")){
				cd_li = "92";
			}
			else if(cd_clear.equals("4")){
				cd_li = "93";
			}
			else if(cd_clear.equals("5")){
				cd_li = "94";
			}
			else if(cd_clear.equals("6")){
				cd_li = "95";
			}
			else if(cd_clear.equals("7")){
				cd_li = "98";
			}
			else if(cd_clear.equals("8")){
				cd_li = "99";
			}
			else if(cd_clear.equals("9")){
				cd_li = "96";
			}
			else if(cd_clear.equals("10")){
				cd_li = "97";
			}
			
			//SQL���̍쐬
			strSql.append(" INSERT INTO tr_shisan_status_rireki  ");
			strSql.append("            (cd_shain  ");
			strSql.append("            ,nen  ");
			strSql.append("            ,no_oi  ");
			strSql.append("            ,no_eda  ");
			strSql.append("            ,dt_henkou  ");
			strSql.append("            ,cd_kaisha  ");
			strSql.append("            ,cd_busho  ");
			strSql.append("            ,id_henkou  ");
			strSql.append("            ,cd_zikko_ca  ");
			strSql.append("            ,cd_zikko_li  ");
			strSql.append("            ,st_kenkyu  ");
			strSql.append("            ,st_seisan  ");
			strSql.append("            ,st_gensizai  ");
			strSql.append("            ,st_kojo  ");
			strSql.append("            ,st_eigyo  ");
			strSql.append("            ,id_toroku  ");
			strSql.append("            ,dt_toroku)  ");
			strSql.append("      VALUES  ");
			strSql.append("            (" + strCdShain);
			strSql.append("            ," + strNen);
			strSql.append("            ," + strNoOi);
			strSql.append("            ," + strNoEda);
			strSql.append("            ,GETDATE() ");
			strSql.append("            ,"  + userInfoData.getCd_kaisha());
			strSql.append("            ,"  + userInfoData.getCd_busho());
			strSql.append("            ,"  + userInfoData.getId_user());
			strSql.append("            ,'wk_seisan' ");
			strSql.append("            ," + cd_li);
			strSql.append("            ," + st_kenkyu);
			strSql.append("            ," + st_seikan);
			strSql.append("            ," + st_gentyo);
			strSql.append("            ," + st_kojo);
			strSql.append("            ," + st_eigyo);
			strSql.append("            ," + userInfoData.getId_user());
			strSql.append("            ,GETDATE() )  ");
			
		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
		return strSql;
	}
	
	/**
	 * �������Z�X�e�[�^�Xð��ٍX�V
	 * @param reqData�F���N�G�X�g�f�[�^
	 * @param lstRecset�F��������
	 * @return strSql�F�X�VSQL
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private StringBuffer makeExecSQL_StatusGenka(
			RequestResponsKindBean reqData,
			List<?>  lstRecset
			)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		//SQL�i�[�p
		StringBuffer strSql = new StringBuffer();
		
		try {
			//���N�G�X�g����f�[�^���o
			String strCdShain = reqData.getFieldVale(0, 0, "cd_shain");
			String strNen = reqData.getFieldVale(0, 0, "nen");
			String strNoOi = reqData.getFieldVale(0, 0, "no_oi");
			String strNoEda = reqData.getFieldVale(0, 0, "no_eda");
			
			//���R�[�h�Z�b�g����f�[�^���o
			Object[] items = (Object[]) lstRecset.get(0);
			String st_kenkyu = toString(items[0],"");
			String st_seikan = toString(items[1],"");
			String st_gentyo = toString(items[2],"");
			String st_kojo = toString(items[3],"");
			String st_eigyo = toString(items[4],"");
			
			//SQL���̍쐬
			strSql.append(" UPDATE tr_shisan_status  ");
			strSql.append(" SET   ");
			strSql.append("        st_kenkyu = " + st_kenkyu);
			strSql.append("       ,st_seisan = " + st_seikan);
			strSql.append("       ,st_gensizai = " + st_gentyo);
			strSql.append("       ,st_kojo = " + st_kojo);
			strSql.append("       ,st_eigyo = " + st_eigyo);
			strSql.append("       ,id_koshin = " + userInfoData.getId_user());
			strSql.append("       ,dt_koshin = GETDATE()  ");
			strSql.append(" WHERE   ");
			strSql.append("       cd_shain = " + strCdShain );
			strSql.append("       AND nen = " + strNen );
			strSql.append("       AND no_oi = " + strNoOi );
			strSql.append("       AND no_eda = " + strNoEda );
			
		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
		return strSql;
	}
	
	/**
	 * ���Z�@����ið��ٍX�V
	 * @param reqData�F���N�G�X�g�f�[�^
	 * @param lstRecset�F��������
	 * @return strSql�F�X�VSQL
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private StringBuffer makeExecSQL_sisanShisakuhin(
			RequestResponsKindBean reqData,
			List<?>  lstRecset
			)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		//SQL�i�[�p
		StringBuffer strSql = new StringBuffer();
		
		try {
			//���N�G�X�g����f�[�^���o
			String strCdShain = reqData.getFieldVale(0, 0, "cd_shain");
			String strNen = reqData.getFieldVale(0, 0, "nen");
			String strNoOi = reqData.getFieldVale(0, 0, "no_oi");
			String strNoEda = reqData.getFieldVale(0, 0, "no_eda");
			String cd_clear = reqData.getFieldVale(0, 0, "cd_clear");
			
			
			//SQL���̍쐬
			strSql.append(" UPDATE tr_shisan_shisakuhin  ");
			strSql.append(" SET   ");
			strSql.append("        saiyo_sample = NULL");
			
			//�c�ƃX�e�[�^�X���P�ɂȂ���͎̂��Z������������
			if(cd_clear.equals("1") || cd_clear.equals("2")){
				strSql.append("       ,dt_kizitu = NULL");
			}
			
			strSql.append("       ,id_koshin = " + userInfoData.getId_user());
			strSql.append("       ,dt_koshin = GETDATE()  ");
			strSql.append(" WHERE   ");
			strSql.append("       cd_shain = " + strCdShain );
			strSql.append("       AND nen = " + strNen );
			strSql.append("       AND no_oi = " + strNoOi );
			strSql.append("       AND no_eda = " + strNoEda );
			
		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
		return strSql;
	}
	
//�yH24�N�x�Ή��z2012/05/29 TT H.SHIMA ADD Start
	/**
	 * ���Z�@����ð��ٍX�V
	 * @param reqData�F���N�G�X�g�f�[�^
	 * @param lstRecset�F��������
	 * @return strSql�F�X�VSQL
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private StringBuffer makeExecSQL_sisanShisaku(
			RequestResponsKindBean reqData,
			List<?>  lstRecset
			)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		//SQL�i�[�p
		StringBuffer strSql = new StringBuffer();
		
		try {

			//���N�G�X�g����f�[�^���o
			String strCdShain = reqData.getFieldVale(0, 0, "cd_shain");
			String strNen = reqData.getFieldVale(0, 0, "nen");
			String strNoOi = reqData.getFieldVale(0, 0, "no_oi");
			String strNoEda = reqData.getFieldVale(0, 0, "no_eda");
			Object[] items = (Object[]) lstRecset.get(0);
			String st_kojo = toString(items[3],"");
// add start 20120620 hisahori
			String no_clearcheck = reqData.getFieldVale(0, 0, "no_clearcheck");
			String arrClearNo[] = no_clearcheck.split("chk");
			int cntClearNo = arrClearNo.length;
// add end 20120620 hisahori
				if(st_kojo.equals("1")){
					//SQL���̍쐬
	// mod start 20120620 hisahori
	//				strSql.append("UPDATE tr_shisan_shisaku ");
	//				strSql.append("SET ");
	//				strSql.append("       dt_shisan = NULL ");
	//				strSql.append("WHERE ");
	//				strSql.append("       cd_shain = " + strCdShain );
	//				strSql.append("       AND nen = " + strNen );
	//				strSql.append("       AND no_oi = " + strNoOi );
	//				strSql.append("       AND no_eda = " + strNoEda );
	//				strSql.append("       AND seq_shisaku BETWEEN 1 AND ");
	//				strSql.append("              (SELECT COUNT(*) ");
	//				strSql.append("               FROM tr_shisan_shisaku ");
	//				strSql.append("               WHERE");
	//				strSql.append("                      cd_shain = " + strCdShain );
	//				strSql.append("                      AND nen = " + strNen );
	//				strSql.append("                      AND no_oi = " + strNoOi );
	//				strSql.append("                      AND no_eda = " + strNoEda + ")");
					for (int i = 0; i < cntClearNo; i++) {
						strSql.append("UPDATE tr_shisan_shisaku ");
						strSql.append("SET ");
						strSql.append("       dt_shisan = NULL ");
						strSql.append("WHERE ");
						strSql.append("       cd_shain = " + strCdShain );
						strSql.append("       AND nen = " + strNen );
						strSql.append("       AND no_oi = " + strNoOi );
						strSql.append("       AND no_eda = " + strNoEda );
						if (arrClearNo[i] == ""){
							arrClearNo[i] = "''";
						}
						strSql.append("       AND seq_shisaku = " + arrClearNo[i] );
						strSql.append(" ; ");
					}
	// mod end 20120620 hisahori
				}
		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
		return strSql;
	}
//�yH24�N�x�Ή��z2012/05/29 TT H.SHIMA ADD End
	
	//--------------------------------------------------------------------------------------------------------------
	//
	//                                                                  storageData�i�p�����[�^�[�i�[�j 
	//
	//--------------------------------------------------------------------------------------------------------------
	/**
	 * �p�����[�^�[�i�[
	 *  : �X�e�[�^�X������ʂւ̃��X�|���X�f�[�^�֊i�[����B
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
			}

		} catch (Exception e) {
			this.em.ThrowException(e, "");
			
		} finally {

		}
		
	}
	
}
