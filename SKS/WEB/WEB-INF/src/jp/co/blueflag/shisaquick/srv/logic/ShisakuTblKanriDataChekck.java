package jp.co.blueflag.shisaquick.srv.logic;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.util.StringUtils;

import jp.co.blueflag.shisaquick.srv.base.LogicBase;
import jp.co.blueflag.shisaquick.srv.base.RequestData;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsKindBean;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsTableBean;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ConstManager;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;
import jp.co.blueflag.shisaquick.srv.common.ConstManager.Category;
import jp.co.blueflag.shisaquick.srv.common.ExceptionManager.ExceptionKind;
import jp.co.blueflag.shisaquick.srv.commonlogic_genka.CGEN2010_Logic;

/**
 *
 * JWS-����e�[�u���Ǘ�DB����
 *  : JWS-����e�[�u���Ǘ�DB�����̂c�a�ɑ΂���Ɩ����W�b�N�̎���
 *
 * @author TT.k-katayama
 * @since  2009/04/15
 *
 */
public class ShisakuTblKanriDataChekck extends LogicBase {

	/**
	 * �R���X�g���N�^
	 */
	public ShisakuTblKanriDataChekck() {
		//���N���X�̃R���X�g���N�^
		super();

	}

	/**
	 * ����e�[�u���Ǘ�����
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

		try {
			//���X�|���X�f�[�^�i�@�\�j����
			resKind = new RequestResponsKindBean();

			//�@:�g�����U�N�V�������J�n����
			//DB�X�V�̐���
			super.createExecDB();
			//DB�����̐���
			super.createSearchDB();
			//�g�����U�N�V�����J�n
			this.searchDB.BeginTran();
			//�g�����U�N�V������execDB�Ƌ��L����B
			execDB.setSession(searchDB.getSession());


			//�yQP@00342�z����ǉ��̎���SEQ�̂ݎ擾
			boolean flgCopy = false;
			BigDecimal  cd_shain = new BigDecimal("0");
			int     nen = 0;
			int     no_oi = 0;
			int     seq_shisaku=0;
			ArrayList<Integer> list_seq_shisaku = new ArrayList();
			StringBuffer strSQL = null;
			List<?> lstSearchAry = null;

			//2012/08/02
			String strTableName = "tr_shisaku_list";
			String strSeq_shisaku = "";//�u���[�N�L�[:����SEQ
			int intRecIndex = 0;//�擾���R�[�h�C���f�b�N�X

			String strFlg_shisanIrai = "";//����˗��t���O(1�ň˗��ς�)
			String strFlg_cancel = "";//����˗��L�����Z��
			String strCd_genryo = "";
			String strCd_kaisha = "";
			String strNm_genryo = "";
			// 20160913 ADD KPX@1600766 Start
			String nm_sample = "";
			// 20160913 ADD KPX@1600766 End

			try {

				// 20160915 MOD KPX@1600766 Start
				// 20160420 ADD KPX@1600766 Start
//				String errMsg = ""; // �G���[�������
				StringBuffer errMsg = new StringBuffer(""); // �G���[�������
				// 20160420 ADD KPX@1600766 End
				// 20160915 MOD KPX@1600766 End

				//2012/08/02 Add
				for(int i = 0 ; i < reqData.getCntRow("tr_shisaku_list") ; i++){

					String strShisakuSeq = reqData.getFieldVale(strTableName, i, "seq_shisaku");
					String strKouteiCd = reqData.getFieldVale(strTableName, i, "cd_kotei");
					String strKouteiSeq = reqData.getFieldVale(strTableName, i, "seq_kotei");

					if( !strSeq_shisaku.equals(strShisakuSeq)){

						intRecIndex = getTableIndex(reqData,"tr_shisaku","seq_shisaku",strShisakuSeq);
						strFlg_shisanIrai = reqData.getFieldVale("tr_shisaku",intRecIndex,"flg_shisanIrai");
						strFlg_cancel = reqData.getFieldVale("tr_shisaku",intRecIndex,"flg_cancel");

					}

					// 20160913 ADD KPX@1600766 Start
					nm_sample = reqData.getFieldVale("tr_shisaku",intRecIndex,"nm_sample");
					// 20160913 ADD KPX@1600766 End

					//���@���L�̍��ڂ͒l��NULL(�󕶎�)�ł͂Ȃ��ꍇ�A�`�F�b�N���s���B
					String strRyo = reqData.getFieldVale(strTableName, i, "quantity");
					if ( !(strRyo.isEmpty()) ) {

						//���Z�˗��ς݂Ŋ��L�����Z���t���O��off�̎���ɂ��āA�z���e�[�u���̌����`�F�b�N���s���B
						if( "1".equals(strFlg_shisanIrai) && !"1".equals(strFlg_cancel)){
							//�H��CD,�H��SEQ���z���e�[�u�����烌�R�[�h���擾
							intRecIndex = getTableIndex(reqData,"tr_haigo","cd_kotei",strKouteiCd,"seq_kotei",strKouteiSeq);
							//����CD�����͂���Ă��Ȃ������̓G���[
							strCd_genryo = reqData.getFieldVale("tr_haigo", intRecIndex, "cd_genryo");
							if(strCd_genryo == null || strCd_genryo.isEmpty()){
								em.ThrowException(
										ExceptionKind.���Exception,
										"E000222",
										"","","");
							}
							strNm_genryo = reqData.getFieldVale("tr_haigo",intRecIndex, "nm_genryo");
							if(!"".equals(strNm_genryo)
									 && !"N".equals(strCd_genryo.substring(0,1))
									 && !"n".equals(strCd_genryo.substring(0,1))
								){

								//strCd_genryo = toString(reqData.getFieldVale("tr_haigo",i,"cd_genryo"));
								//strCd_kaisha = toString(reqData.getFieldVale("tr_haigo",i,"cd_kaisha"));
								strCd_genryo = toString(reqData.getFieldVale("tr_haigo",intRecIndex,"cd_genryo"));
								// 20160915 MOD Start ��{���̐�����Ђ��擾
								//strCd_kaisha = toString(reqData.getFieldVale("tr_haigo",intRecIndex,"cd_kaisha"));
								strCd_kaisha = reqData.getFieldVale("tr_shisakuhin",0,"cd_kaisha");
								// 20160915 MOD End

								strSQL = new StringBuffer();
								strSQL.append("select ");
								strSQL.append("	M401.cd_genryo AS cd_genryo ");
								strSQL.append("FROM ");
								strSQL.append("  ma_genryo M401 ");
								strSQL.append("LEFT JOIN ");
								strSQL.append("  ma_genryokojo M402");
								strSQL.append(" ON M401.cd_genryo = M402.cd_genryo ");
								strSQL.append("WHERE");
								strSQL.append("  M401.cd_genryo = '" + strCd_genryo + "' ");
								strSQL.append("  and M401.cd_kaisha = '" + strCd_kaisha +"' ");

								lstSearchAry = this.searchDB.dbSearch_notError(strSQL.toString());

								//������Ђɑ����錴�������݂��Ȃ��ꍇ�G���[
								if(lstSearchAry.size() <= 0 ){

									// 20160420 MOD KPX@1600766 Start
//									em.ThrowException(
//											ExceptionKind.���Exception,
//											"E000223",
//											"","","");
									// 20160915 MOD KPX@1600766 Start
									// �G���[�������̐ݒ�
//									errMsg += System.getProperty("line.separator");
									// 20160913 ADD KPX@1600766 Start
//									errMsg += "�T���v��No�F" + nm_sample + "�@";
									// 20160913 ADD KPX@1600766 End
//									errMsg += strCd_genryo + "�F" + strNm_genryo;
									errMsg.append(System.getProperty("line.separator"));
									errMsg.append(nm_sample + "�@" + strCd_genryo + "�F" + strNm_genryo);
									// 20160420 ADD KPX@1600766 End
									// 20160915 MOD KPX@1600766 End
								}
							}
						}
					}
				}

				// 20160420 ADD KPX@1600766 Start
				try{
					// 20160915 MOD KPX@1600766 Start
					// �����G���[������ꍇ�G���[���b�Z�[�W�\��
//					if(!"".equals(errMsg)) {
					if(!"".equals(errMsg.toString())) {
					// 20160915 MOD KPX@1600766 End
						em.ThrowException(
							ExceptionKind.���Exception,
							"E000223",
							"","","");
					}
				} catch (ExceptionUser ex) {
					// 20160915 MOD KPX@1600766 Start
					// �G���[���b�Z�[�W+�G���[��������ݒ�
//					ex.setUserMsg(ex.getUserMsg() + errMsg.toString());
					ex.setUserMsg(ex.getUserMsg() + errMsg);
					// 20160915 MOD KPX@1600766 End

					throw ex;
				}
				// 20160420 ADD KPX@1600766 End

			//2012/08/02 Add

				//UPD 2012.4.30�@�yH24�N�x�Ή��z�X�V���Ŏ���i�̍X�V�r��������s�����ߍX�V�`�F�b�N
				//�X�V�����擾���A�@�\�V�̃`�F�b�N���s���B
				String strDtKosin = toString(reqData.getFieldVale("tr_shisakuhin", 0, "dt_koshin"));
				if( !"".equals(strDtKosin) ){
					//�Ј�ID
					cd_shain = new BigDecimal(reqData.getFieldVale("tr_shisakuhin", 0, "cd_shain"));
					//�N
					nen = toInteger(reqData.getFieldVale("tr_shisakuhin", 0,"nen"));
					//�ǔ�
					no_oi = toInteger(reqData.getFieldVale("tr_shisakuhin", 0, "no_oi"));
					//�ǔ�
					no_oi = toInteger(reqData.getFieldVale("tr_shisakuhin", 0, "no_oi"));

					//����SQL����
					strSQL = new StringBuffer();
					strSQL.append(" select ");
					strSQL.append(" 	CONVERT(VARCHAR,dt_koshin,20) ");
					strSQL.append(" from ");
					strSQL.append(" 	tr_shisakuhin ");
					strSQL.append(" where ");
					strSQL.append(" 	cd_shain = " + cd_shain);
					strSQL.append(" 	AND nen = " + nen);
					strSQL.append(" 	AND no_oi = " + no_oi);
					//DB����
					lstSearchAry = this.searchDB.dbSearch_notError(strSQL.toString());
					//���ɍX�V�ς݂̎��̓G���[���o�͂��ēo�^�𒆒f����B
					if(lstSearchAry.size() > 0 ){
						String strTableDtKoshin = toString(lstSearchAry.get(0),"");
						if( !strDtKosin.equals(strTableDtKoshin) ){
							// ���X�V
							em.ThrowException(ExceptionKind.���Exception, "E000333", "", "", "");
						}
					}
				}


//2009/09/30 TT.A.ISONO ADD START [����CD�ǔԂ͓o�^���ɍ̔Ԃ���悤�ύX����B]

				String oiban = reqData.getFieldVale("tr_shisakuhin", 0, "no_oi");

				//����CD�ǔԂ̍̔Ԃ��s���A���N�G�X�g�f�[�^�Ɏ���CD�ǔԂ��Z�b�g����B
				//�V�K�o�^�̏ꍇ�i�ǔԖ����j
				if(oiban == ""){

					this.saiban(reqData);

				//�X�V�̏ꍇ�i�ǔԗL��j
				}else{

				}

//2009/09/30 TT.A.ISONO ADD END   [����CD�ǔԂ͓o�^���ɍ̔Ԃ���悤�ύX����B]

				//�yQP@00342�z����ǉ��̎���SEQ�̂ݎ擾
				for(int i = 0; i < reqData.getCntRow("tr_shisaku"); i++){

					if (toString(reqData.getFieldVale("tr_shisaku", i, "flg_shisanIrai")).equals("1")){

						//����CD-�Ј�ID
						cd_shain = new BigDecimal(reqData.getFieldVale("tr_shisaku", i, "cd_shain"));
						//����CD-�N
						nen = toInteger(reqData.getFieldVale("tr_shisaku", i, "nen"));
						//����CD-�ǔ�
						no_oi = toInteger(reqData.getFieldVale("tr_shisaku", i, "no_oi"));
						//����SEQ
						seq_shisaku = toInteger(reqData.getFieldVale("tr_shisaku", i, "seq_shisaku"));

						//SQL�쐬
						strSQL = null;
						//����SQL����
						strSQL = new StringBuffer();
						strSQL.append(" select ");
						strSQL.append(" 	cd_shain ");
						strSQL.append(" 	,nen ");
						strSQL.append(" 	,no_oi ");
						strSQL.append(" 	,seq_shisaku ");
						strSQL.append(" from ");
						strSQL.append(" 	tr_shisaku ");
						strSQL.append(" where ");
						strSQL.append(" 	cd_shain = " + cd_shain);
						strSQL.append(" 	AND nen = " + nen);
						strSQL.append(" 	AND no_oi = " + no_oi);
						strSQL.append(" 	AND seq_shisaku = " + seq_shisaku);
						strSQL.append(" 	AND flg_shisanIrai = 1 ");
						//DB����
						lstSearchAry = this.searchDB.dbSearch_notError(strSQL.toString());

						//���Ɉ˗��ς݂̎���SEQ�̏ꍇ
						if(lstSearchAry.size() > 0){
							//�R�s�[�ΏۂƂ��Ȃ�

						}
						//�V�K�˗��̎���SEQ�̏ꍇ
						else{

							// ADD 2013/8/2 okano�yQP@30151�zNo.34 start
							String strKoteiPt = reqData.getFieldVale("tr_shisakuhin", 0, "pt_kotei");
							String strChkPrm;
							if(toInteger(strKoteiPt) == 1){
								//���i��d�̕K�{�`�F�b�N
								strChkPrm = reqData.getFieldVale("tr_shisaku", i, "hiju");

								// �`�F�b�N�p�����[�^�[�̕K�{���̓`�F�b�N���s���B
								if (strChkPrm.equals(null) || strChkPrm.equals("") || toDouble(strChkPrm) <= 0) {

									// �K�{���͕s�����X���[����B
									em.ThrowException(ExceptionKind.���Exception, "E000200", "����f�[�^��� �����l ���i��d", "", "");
								}
							}
							else if(toInteger(strKoteiPt) == 2){
								//������d�̕K�{�`�F�b�N
								strChkPrm = reqData.getFieldVale("tr_shisaku", i, "hiju_sui");

								// �`�F�b�N�p�����[�^�[�̕K�{���̓`�F�b�N���s���B
								if (strChkPrm.equals(null) || strChkPrm.equals("") || toDouble(strChkPrm) <= 0) {

									// �K�{���͕s�����X���[����B
									em.ThrowException(ExceptionKind.���Exception, "E000200", "����f�[�^��� �����l ������d", "", "");
								}
							}
							// ADD 2013/8/2 okano�yQP@30151�zNo.34 end

							//�V�K�˗��̎�����i�[---------------------------------------------------------------------------
							//�������W
							flgCopy = true;

							//����CD-�Ј�ID
							cd_shain = new BigDecimal(
									reqData.getFieldVale("tr_shisaku", i, "cd_shain"));
							//����CD-�N
							nen = toInteger(
									reqData.getFieldVale("tr_shisaku", i, "nen"));
							//����CD-�ǔ�
							no_oi = toInteger(
									reqData.getFieldVale("tr_shisaku", i, "no_oi"));
							//����SEQ
							list_seq_shisaku.add(toInteger(
									reqData.getFieldVale("tr_shisaku", i, "seq_shisaku")));
						}
					}
				}

				//�V�K�˗��̃T���v��������ꍇ�ɁA�e��`�F�b�N���s��
				if(list_seq_shisaku.size() > 0){

					 //�H�������̕K�{�`�F�b�N�i�V�K�˗�������ꍇ�j
					for(int i = 0; i < reqData.getCntRow("tr_haigo"); i++){

						String strChkPrm = reqData.getFieldVale("tr_haigo", i, "zoku_kotei");

						// �`�F�b�N�p�����[�^�[�̕K�{���̓`�F�b�N���s���B
						if (strChkPrm.equals(null) || strChkPrm.equals("")) {

							// �K�{���͕s�����X���[����B
							em.ThrowException(ExceptionKind.���Exception, "E000207", "����f�[�^��� �z���\ �H������", "", "");
						}
					}


					strSQL = null;

					//�֘A��Ђł̎��Z�˗��`�F�b�N----------------------------------------------------------------------
					String cd_kaisha = toString(reqData.getFieldVale("tr_shisakuhin", 0, "cd_kaisha"));
//MOD 2013/11/18 shima start
//					String kewpie = ConstManager.getConstValue(Category.�ݒ�, "CD_DAIHYO_KAISHA");
//
//					//�֘A��ЂłȂ��ꍇ
//					if(cd_kaisha.equals(kewpie)){
//
//					}
					reqData.addFieldVale(0,0,"cd_category","K_daihyogaisha");
					reqData.addFieldVale(0,0,"id_user","");
					reqData.addFieldVale(0,0,"id_gamen","");

					//��\��Ђ̎擾
					LiteralSearchLogic literalData = new LiteralSearchLogic();
					RequestResponsKindBean reqDaihyo = literalData.ExecLogic(reqData, _userInfoData);

					List<String> listDaihyo = new ArrayList<String>();
					for(int i = 0; i < reqDaihyo.getCntRow("tr_shisakuhin") ; i ++){
						listDaihyo.add(reqDaihyo.getFieldVale("tr_shisakuhin", i, "nm_literal"));
					}

					//�֘A��ЂłȂ��ꍇ
					if(listDaihyo.contains(cd_kaisha)){

					}
// MOD 2013/11/18 shima end
					//�֘A��Ђ̏ꍇ
					else{
						em.ThrowException(ExceptionKind.���Exception, "E000317", "", "", "");
					}

// ADD 2013/6/28 okano�yQP@30151�zNo.22 start --------------------------------------------
					//���Z�������N���A
					strSQL = null;
					strSQL = new StringBuffer();
					strSQL.append(" UPDATE tr_shisan_shisakuhin ");
					strSQL.append(" set dt_kizitu = NULL ");
					strSQL.append(" where ");
					strSQL.append(" cd_shain = " + cd_shain);
					strSQL.append(" and nen = " + nen);
					strSQL.append(" and no_oi = " + no_oi);
					this.execDB.execSQL(strSQL.toString());
// ADD 2013/6/28 okano�yQP@30151�zNo.22 end ----------------------------------------------

					//�r���`�F�b�N����-----------------------------------------------------------------------------------
					//SQL�쐬
					strSQL = null;
					//����SQL����
					strSQL = new StringBuffer();
					strSQL.append(" select ");
					strSQL.append(" 	haita_id_user ");
					strSQL.append(" from ");
					strSQL.append(" 	tr_shisan_shisakuhin ");
					strSQL.append(" where ");
					strSQL.append(" 	cd_shain = " + cd_shain);
					strSQL.append(" 	AND nen = " + nen);
					strSQL.append(" 	AND no_oi = " + no_oi);
					//DB����
					List<?> lstSearchAry_haita = this.searchDB.dbSearch_notError(strSQL.toString());
					if(lstSearchAry_haita.size() > 0){
						for(int j = 0; j < lstSearchAry_haita.size(); j++){
							Object items_haita = (Object)lstSearchAry_haita.get(j);
							if(toString(items_haita).equals("")){

							}
							else{
								//�����[�U���g�p��
								em.ThrowException(ExceptionKind.���Exception, "E000316", "", "", "");
							}
						}
					}

					//�Ώێ���̉c�ƃX�e�[�^�X���S��4�̏ꍇ-------------------------------------------------------------
					//SQL�쐬
					strSQL = null;
					//����SQL����
					strSQL = new StringBuffer();
					strSQL.append(" 	select ");
					strSQL.append(" 		st_eigyo ");
					strSQL.append(" 	from ");
					strSQL.append(" 		tr_shisan_status ");
					strSQL.append(" where ");
					strSQL.append(" 	cd_shain = " + cd_shain);
					strSQL.append(" 	AND nen = " + nen);
					strSQL.append(" 	AND no_oi = " + no_oi);
					//DB����
					List<?> lstSearchAry_st = this.searchDB.dbSearch_notError(strSQL.toString());
					if(lstSearchAry_st.size() > 0){
						int j = 0;
						for(j = 0; j < lstSearchAry_st.size(); j++){
							Object items_st_eigyo = (Object)lstSearchAry_st.get(j);
							if(toString(items_st_eigyo).equals("4")){

							}
							else{
								break;
							}
						}
						if(j == lstSearchAry_st.size()){
							//�����[�U���g�p��
							em.ThrowException(ExceptionKind.���Exception, "E000318", "", "", "");
						}
					}

				}



				//�A:T110 ����i�e�[�u��(tr_shisakuhin)�̍폜�E�o�^�������s��
				this.shisakuhinKanriDeleteSQL(reqData);
				this.shisakuhinKanriInsertSQL(reqData);
				//�B:T120 �z���e�[�u��(tr_haigo)�̍폜�E�o�^�������s��
				this.haigoKanriDeleteSQL(reqData);
				this.haigoKanriInsertSQL(reqData);
				//�C:T131 ����e�[�u��(tr_shisaku)�̍폜�E�o�^�������s��
				this.shisakuKanriDeleteSQL(reqData);
				this.shisakuKanriInsertSQL(reqData);
				//�D:T132 ���샊�X�g�e�[�u��(tr_shisaku_list)�̍폜�E�o�^�������s��
				this.shisakuListKanriDeleteSQL(reqData);
				this.shisakuListKanriInsertSQL(reqData);
				//�E:T133 �����H���e�[�u��(tr_cyuui)�̍폜�E�o�^�������s��
				this.seizoKouteiKanriDeleteSQL(reqData);
				this.seizoKouteiKanriInsertSQL(reqData);

//				//�F:T140 �������ރe�[�u��(tr_shizai)�̍폜�E�o�^�������s��(�񎟕�)
//				this.genkaShizaiKanriDeleteSQL();
//				this.genkaShizaiKanriInsertSQL();
				//�G:T141 ���������e�[�u��(tr_genryo)�̍폜�E�o�^�������s��(�񎟕�)
				this.genkaGenryoKanriDeleteSQL(reqData);
				this.genkaGenryoKanriInsertSQL(reqData);

				//�H:�R�~�b�g/���[���o�b�N���������s����
				//�R�~�b�g�@���g�����U�N�V������searchDB�ŊJ�n�iBeginTran�j���Ă���̂ŁA�R�~�b�g��searchDB�ōs���܂��B
				this.searchDB.Commit();

			} catch(Exception e) {
				//���[���o�b�N�@���g�����U�N�V������searchDB�ŊJ�n�iBeginTran�j���Ă���̂ŁA���[���o�b�N��searchDB�ōs���܂��B
				this.searchDB.Rollback();
				this.em.ThrowException(e, "");

			} finally {
			}

//2009/10/23 TT.A.ISONO ADD START [�������Z�F�˗��f�[�^���������Z�c�a��Copy����B]

//			boolean flgCopy = false;
//			BigDecimal  cd_shain = new BigDecimal("0");
//			int     nen = 0;
//			int     no_oi = 0;
//			ArrayList<Integer> list_seq_shisaku = new ArrayList();
//
//			try{
//
//				//���{����
//				try{
//					for(int i = 0; i < reqData.getCntRow("tr_shisaku"); i++){
//
//						if (toString(reqData.getFieldVale("tr_shisaku", i, "flg_shisanIrai")).equals("1")){
//
//							//�������W
//							flgCopy = true;
//
//							//����CD-�Ј�ID
//							cd_shain = new BigDecimal(
//									reqData.getFieldVale("tr_shisaku", i, "cd_shain"));
//							//����CD-�N
//							nen = toInteger(
//									reqData.getFieldVale("tr_shisaku", i, "nen"));
//							//����CD-�ǔ�
//							no_oi = toInteger(
//									reqData.getFieldVale("tr_shisaku", i, "no_oi"));
//							//����SEQ
//							list_seq_shisaku.add(toInteger(
//									reqData.getFieldVale("tr_shisaku", i, "seq_shisaku")));
//
//						}
//
//					}
//
//				}catch(Exception e){
//
//				}

			if (flgCopy){

				//�����˗��N���X
				//�yQP@00342�z�����Ɏ}�Ԓǉ��i���łȂ̂�0�j
				CGEN2010_Logic clsCGEN2010_Logic = new CGEN2010_Logic();
				clsCGEN2010_Logic.ExecLogic(
						cd_shain
						, nen
						, no_oi
						,0
						, list_seq_shisaku
						, userInfoData
						);
			}

//			}catch(Exception e){
//
//			}finally{
//				//���[�J���ϐ��̊J��
//				removeList(list_seq_shisaku);
//
//			}

//2009/10/23 TT.A.ISONO ADD END   [�������Z�F�˗��f�[�^���������Z�c�a��Copy����B]


//2011/04/12 QP@99999_No.67 TT Nishigawa Change Start -------------------------
			//SQL���i�[�p
			strSQL = new StringBuffer();

			//�L�����Z�����sFG
			boolean exec_cancel = false;

			//�������ʊi�[�p
			lstSearchAry = null;

			//�g�����U�N�V�����J�n
			execDB.BeginTran();

			try{

				//�����f�[�^���[�v
				for(int i = 0; i < reqData.getCntRow("tr_shisaku"); i++){

					//�L�����Z���˗����������ꍇ
					if (toString(reqData.getFieldVale("tr_shisaku", i, "flg_cancel")).equals("1")){

						//����CD-�Ј�ID
						String cd_shain_cancel = toString(reqData.getFieldVale("tr_shisaku", i, "cd_shain"));
						//����CD-�N
						String nen_cancel = toString(reqData.getFieldVale("tr_shisaku", i, "nen"));
						//����CD-�ǔ�
						String no_oi_cancel = toString(reqData.getFieldVale("tr_shisaku", i, "no_oi"));
						//����SEQ
						String seq_shisaku_cancel = toString(reqData.getFieldVale("tr_shisaku", i, "seq_shisaku"));

						// ADD 2013/7/2 shima�yQP@30151�zNo.37 start
						//���sSQL���i�������Z�̃T���v����폜�j
						strSQL = new StringBuffer();
						strSQL.append(" DELETE FROM tr_shisan_kihonjoho ");
						strSQL.append(" WHERE ");
						strSQL.append(" 	cd_shain = " + cd_shain_cancel);
						strSQL.append(" 	and nen = " + nen_cancel);
						strSQL.append(" 	and no_oi =  " + no_oi_cancel);
						strSQL.append(" 	and seq_shisaku = " + seq_shisaku_cancel);

						//SQL���s
						this.execDB.execSQL(strSQL.toString());
						// ADD 2013/7/2 shima�yQP@30151�zNo.37 end

						//���sSQL���i�������Z�̃T���v����폜�j
						strSQL = new StringBuffer();
						strSQL.append(" DELETE FROM tr_shisan_shisaku ");
						strSQL.append(" WHERE ");
						strSQL.append(" 	cd_shain = " + cd_shain_cancel);
						strSQL.append(" 	and nen = " + nen_cancel);
						strSQL.append(" 	and no_oi =  " + no_oi_cancel);
						strSQL.append(" 	and seq_shisaku = " + seq_shisaku_cancel);

						//SQL���s
						this.execDB.execSQL(strSQL.toString());

						//���sSQL���i����f�[�^�̈˗�FG�����j
						strSQL = new StringBuffer();
						strSQL.append(" UPDATE tr_shisaku ");
						strSQL.append("    SET  ");
						strSQL.append(" 	   [flg_shisanIrai] = NULL ");
						strSQL.append("  WHERE ");
						strSQL.append(" 	cd_shain = " + cd_shain_cancel);
						strSQL.append(" 	and nen =  " + nen_cancel);
						strSQL.append(" 	and no_oi =  " + no_oi_cancel);
						strSQL.append(" 	and seq_shisaku = " + seq_shisaku_cancel);

						//SQL���s
						this.execDB.execSQL(strSQL.toString());

						//���s�t���O�ݒ�
						exec_cancel = true;
					}
				}

				//�L�����Z�����s��
				if(exec_cancel){

					//�L�[���ڎ擾
					String strReqShainCd = reqData.getFieldVale("tr_shisakuhin", 0, "cd_shain");
					String strReqNen = reqData.getFieldVale("tr_shisakuhin", 0, "nen");
					String strReqOiNo = reqData.getFieldVale("tr_shisakuhin", 0, "no_oi");

					//�S��폜�`�F�b�NSQL��
					strSQL = new StringBuffer();
					strSQL.append(" select ");
					strSQL.append(" 	cd_shain ");
					strSQL.append(" 	,nen ");
					strSQL.append(" 	,no_oi ");
					strSQL.append(" 	,no_eda ");
					strSQL.append(" 	,seq_shisaku ");
					strSQL.append(" from ");
					strSQL.append(" 	tr_shisan_shisaku ");
					strSQL.append(" where ");
					strSQL.append(" 	cd_shain = " + strReqShainCd);
					strSQL.append(" 	and nen = " + strReqNen);
					strSQL.append(" 	and no_oi = " + strReqOiNo);

					//�������s
					lstSearchAry = this.searchDB.dbSearch(strSQL.toString());

					//�S��폜�̏ꍇ
					if(lstSearchAry.size() == 0){

						//�������Z�f�[�^�폜�i���Z_����i�j
						strSQL = new StringBuffer();
						strSQL.append(" DELETE FROM tr_shisan_shisakuhin ");
						strSQL.append(" where ");
						strSQL.append(" 	cd_shain = " + strReqShainCd);
						strSQL.append(" 	and nen = " + strReqNen);
						strSQL.append(" 	and no_oi = " + strReqOiNo);
						this.execDB.execSQL(strSQL.toString());

						//�������Z�f�[�^�폜�i���Z_�����j
						strSQL = new StringBuffer();
						strSQL.append(" DELETE FROM tr_shisan_memo ");
						strSQL.append(" where ");
						strSQL.append(" 	cd_shain = " + strReqShainCd);
						strSQL.append(" 	and nen = " + strReqNen);
						strSQL.append(" 	and no_oi = " + strReqOiNo);
						this.execDB.execSQL(strSQL.toString());

						//�������Z�f�[�^�폜�i���Z_�c�ƃ����j
						strSQL = new StringBuffer();
						strSQL.append(" DELETE FROM tr_shisan_memo_eigyo ");
						strSQL.append(" where ");
						strSQL.append(" 	cd_shain = " + strReqShainCd);
						strSQL.append(" 	and nen = " + strReqNen);
						strSQL.append(" 	and no_oi = " + strReqOiNo);
						this.execDB.execSQL(strSQL.toString());

						//�������Z�f�[�^�폜�i���Z_�z���j
						strSQL = new StringBuffer();
						strSQL.append(" DELETE FROM tr_shisan_haigo ");
						strSQL.append(" where ");
						strSQL.append(" 	cd_shain = " + strReqShainCd);
						strSQL.append(" 	and nen = " + strReqNen);
						strSQL.append(" 	and no_oi = " + strReqOiNo);
						this.execDB.execSQL(strSQL.toString());

						//�������Z�f�[�^�폜�i���Z_���ށj
						strSQL = new StringBuffer();
						strSQL.append(" DELETE FROM tr_shisan_shizai ");
						strSQL.append(" where ");
						strSQL.append(" 	cd_shain = " + strReqShainCd);
						strSQL.append(" 	and nen = " + strReqNen);
						strSQL.append(" 	and no_oi = " + strReqOiNo);
						this.execDB.execSQL(strSQL.toString());

						//�������Z�f�[�^�폜�i���Z_�X�e�[�^�X�j
						strSQL = new StringBuffer();
						strSQL.append(" DELETE FROM tr_shisan_status ");
						strSQL.append(" where ");
						strSQL.append(" 	cd_shain = " + strReqShainCd);
						strSQL.append(" 	and nen = " + strReqNen);
						strSQL.append(" 	and no_oi = " + strReqOiNo);
						this.execDB.execSQL(strSQL.toString());

						//�������Z�f�[�^�폜�i���Z_�X�e�[�^�X�����j�i���ňȊO�j
						strSQL = new StringBuffer();
						strSQL.append(" DELETE FROM tr_shisan_status_rireki ");
						strSQL.append(" where ");
						strSQL.append(" 	cd_shain = " + strReqShainCd);
						strSQL.append(" 	and nen = " + strReqNen);
						strSQL.append(" 	and no_oi = " + strReqOiNo);
						strSQL.append(" 	and no_eda <> 0");
						this.execDB.execSQL(strSQL.toString());

						//�X�e�[�^�X�����e�[�u���X�V
						strSQL = new StringBuffer();
						strSQL.append(" INSERT INTO tr_shisan_status_rireki ");
						strSQL.append("            (cd_shain ");
						strSQL.append("            ,nen ");
						strSQL.append("            ,no_oi ");
						strSQL.append("            ,no_eda ");
						strSQL.append("            ,dt_henkou ");
						strSQL.append("            ,cd_kaisha ");
						strSQL.append("            ,cd_busho ");
						strSQL.append("            ,id_henkou ");
						strSQL.append("            ,cd_zikko_ca ");
						strSQL.append("            ,cd_zikko_li ");
						strSQL.append("            ,st_kenkyu ");
						strSQL.append("            ,st_seisan ");
						strSQL.append("            ,st_gensizai ");
						strSQL.append("            ,st_kojo ");
						strSQL.append("            ,st_eigyo ");
						strSQL.append("            ,id_toroku ");
						strSQL.append("            ,dt_toroku) ");
						strSQL.append("      VALUES ");
						strSQL.append("            (" + cd_shain + " ");
						strSQL.append("            ," + nen + " ");
						strSQL.append("            ," + no_oi + " ");
						strSQL.append("            ," + 0 +" ");
						strSQL.append("            ,GETDATE() ");
						strSQL.append("            ," + userInfoData.getCd_kaisha() + " ");
						strSQL.append("            ," + userInfoData.getCd_busho() + " ");
						strSQL.append("            ," + userInfoData.getId_user() + " ");
						strSQL.append("            ,'wk_kenkyu'");
						strSQL.append("            ,'2' ");
						strSQL.append("            ,2 ");
						strSQL.append("            ,1 ");
						strSQL.append("            ,1 ");
						strSQL.append("            ,1 ");
						strSQL.append("            ,1 ");
						strSQL.append("            ," + userInfoData.getId_user() + " ");
						strSQL.append("            ,GETDATE() )");
						this.execDB.execSQL(strSQL.toString());

					}
					//�w���폜�̏ꍇ
					else{

						//�X�e�[�^�X�e�[�u���X�V
						strSQL = new StringBuffer();
						strSQL.append(" UPDATE tr_shisan_status ");
						strSQL.append("    SET st_kenkyu = 2 ");
						strSQL.append("       ,st_seisan = 1 ");
						strSQL.append("       ,st_gensizai = 1 ");
						strSQL.append("       ,st_kojo = 1 ");
						strSQL.append("       ,st_eigyo = 1 ");
						strSQL.append("       ,id_koshin = " + userInfoData.getId_user() + " ");
						strSQL.append("       ,dt_koshin = GETDATE() ");
						strSQL.append("  WHERE ");
						strSQL.append("    cd_shain = " + strReqShainCd);
						strSQL.append("    and nen = " + strReqNen);
						strSQL.append("    and no_oi = " + strReqOiNo);
						this.execDB.execSQL(strSQL.toString());

						//�X�e�[�^�X�����e�[�u���X�V
						strSQL = new StringBuffer();
						strSQL.append(" INSERT INTO tr_shisan_status_rireki ");
						strSQL.append("  SELECT ");
						strSQL.append("    A.cd_shain ");
						strSQL.append("   ,A.nen ");
						strSQL.append("   ,A.no_oi ");
						strSQL.append("   ,A.no_eda ");
						strSQL.append("   ,GETDATE() ");
						strSQL.append("   ," + userInfoData.getCd_kaisha() + " ");
						strSQL.append("   ," + userInfoData.getCd_busho() + " ");
						strSQL.append("   ," + userInfoData.getId_user() + " ");
						strSQL.append("   ,'wk_kenkyu' ");
						strSQL.append("   ,'3' ");
						strSQL.append("   ,2 ");
						strSQL.append("   ,1 ");
						strSQL.append("   ,1 ");
						strSQL.append("   ,1 ");
						strSQL.append("   ,1 ");
						strSQL.append("   ," + userInfoData.getId_user() + " ");
						strSQL.append("   ,GETDATE() ");
						strSQL.append("  FROM ");
						strSQL.append("   tr_shisan_shisakuhin AS A ");
						strSQL.append("   INNER JOIN ma_user AS B ");
						strSQL.append("   ON A.id_toroku = B.id_user ");
						strSQL.append("  WHERE ");
						strSQL.append("   A.cd_shain = " + strReqShainCd);
						strSQL.append("   and A.nen = " + strReqNen);
						strSQL.append("   and A.no_oi = " + strReqOiNo);
						this.execDB.execSQL(strSQL.toString());
					}

					//�R�~�b�g�@���g�����U�N�V������searchDB�ŊJ�n�iBeginTran�j���Ă���̂ŁA�R�~�b�g��searchDB�ōs���܂��B
					this.execDB.Commit();
				}

			}
			catch(Exception e) {
				//���[���o�b�N�@���g�����U�N�V������searchDB�ŊJ�n�iBeginTran�j���Ă���̂ŁA���[���o�b�N��searchDB�ōs���܂��B
				this.execDB.Rollback();
				this.em.ThrowException(e, "");

			}
			finally{
				lstSearchAry = null;

			}



//2011/04/12 QP@99999_No.67 TT Nishigawa Change Start -------------------------

			//�@�\ID�̐ݒ�
			String strKinoId = reqData.getID();
			resKind.setID(strKinoId);

			//�e�[�u�����̐ݒ�
			String strTableNm = reqData.getTableID(0);
			resKind.addTableItem(strTableNm);

			//�I�F����I�����A�Ǘ����ʃp�����[�^�[�i�[���\�b�h���ďo���A���X�|���X�f�[�^���`������B
			this.storageKengenDataKanri(resKind.getTableItem(strTableNm));

//2009/09/30 TT.A.ISONO ADD START [����CD�ǔԂ͓o�^���ɍ̔Ԃ���悤�ύX����B]
//�̔Ԃ�������CD�ǔԂ����X�|���X�ɒǉ�
			resKind.addFieldVale(strTableNm, 0, "new_code",
					reqData.getFieldVale("tr_shisakuhin", 0, "cd_shain") + "-" +
					reqData.getFieldVale("tr_shisakuhin", 0, "nen") + "-" +
					reqData.getFieldVale("tr_shisakuhin", 0, "no_oi")
					);

//2009/09/30 TT.A.ISONO ADD END   [����CD�ǔԂ͓o�^���ɍ̔Ԃ���悤�ύX����B]


		} catch (Exception e) {
			this.em.ThrowException(e, "����e�[�u���Ǘ����쏈�������s���܂����B");

		} finally {
			if (execDB != null) {
				execDB.Close();				//�Z�b�V�����̃N���[�Y
				execDB = null;

			}

		}
		return resKind;

	}

//2009/09/30 TT.A.ISONO ADD START [����CD�ǔԂ͓o�^���ɍ̔Ԃ���悤�ύX����B]

	/**
	 * ����CD�ǔԂ̍̔Ԃ��s���A���N�G�X�g�f�[�^�Ɏ���CD�ǔԂ��Z�b�g����B
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private void saiban(RequestResponsKindBean reqData)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		//���[�J���ϐ�
		//SQL
		StringBuffer strSQL = new StringBuffer();
		StringBuffer strSQL2 = new StringBuffer();
		//�������ʂ̊i�[
		List<?> lstSearchAry = null;
		//�V�K�A���캰�ޒǔԂ̊i�[
		String strRetNewCode = "";

		try {

			//������CD�ǔԂ̍̔Ԃ̎��{

			//���[�U���.���[�UID
			String strUserId = userInfoData.getId_user();

			//���L������p���āA����̔ԃ}�X�^�ima_shisakusaiban�j���A�V�K���s����R�[�h�����pSQL���쐬����B
			//����̔ԃ}�X�^.����CD-�Ј��R�[�h =  ���[�U���.���[�UID
			//����̔ԃ}�X�^.����CD-�N = �V�X�e��.�N�̉���
			strSQL.append(" SELECT  ");
			strSQL.append("   RIGHT(YEAR(GETDATE()),2) AS nen ");
			strSQL.append("  ,ISNULL(MAX(M602.no_oi)+1,1) AS no_oi ");
			strSQL.append(" FROM ma_shisaku_saiban M602 ");
			strSQL.append(" WHERE M602.cd_shain =" + strUserId);
			strSQL.append("   AND M602.nen = RIGHT(YEAR(GETDATE()),2) ");

			//�쐬����SQL��p���āA�����������s���A�V�K���s�R�[�h���擾����B
			lstSearchAry = this.searchDB.dbSearch(strSQL.toString());

			//�V�K���s�R�[�h�@�F�@"���[�U���.���[�UID" + "-" + "�V�X�e��.�N���̉���" + "-" + "����̔ԃ}�X�^.�ǔԂ̍ő�l+1"
			if ( lstSearchAry.size() >= 0 ) {
				Object[] items = (Object[]) lstSearchAry.get(0);
				//�V�K�A���캰�ޒǔԂ̑ޔ�
				strRetNewCode = items[1].toString();

			}

			//���̔Ԍ��ʂ�����̔ԃ}�X�^�ima_shisakusaiban�j�ɔ��f

			//�A:�@�̒ǔԂ̒l�ɂ��A������U�蕪����
			if ( strRetNewCode.equals("1") ) {
				//�B�F����̔ԃ}�X�^�ima_shisakusaiban�j�̓o�^�������s��

				strSQL2.append(" INSERT INTO ma_shisaku_saiban ");
				strSQL2.append("  (cd_shain, nen, no_oi, id_toroku, dt_toroku, id_koshin, dt_koshin) ");
				strSQL2.append("  VALUES ( ");
				strSQL2.append(" " + strUserId);
				strSQL2.append(" ,RIGHT(YEAR(GETDATE()),2)");
				strSQL2.append(" ," + strRetNewCode);
				strSQL2.append("  ," + strUserId + " ,GETDATE() ");		//�X�V��
				strSQL2.append("  ," + strUserId + " ,GETDATE() )");	//�o�^��

			} else {
				//�C�F����̔ԃ}�X�^�ima_shisakusaiban�j.�ǔԂ̍X�V�������s��

				strSQL2.append(" UPDATE ma_shisaku_saiban ");
				strSQL2.append("   SET no_oi =" + strRetNewCode);
				strSQL2.append("      ,id_koshin =" + strUserId);
				strSQL2.append("      ,dt_koshin = GETDATE() ");
				strSQL2.append(" WHERE cd_shain =" + strUserId);
				strSQL2.append("   AND nen =RIGHT(YEAR(GETDATE()),2)");

			}

			this.execDB.execSQL(strSQL2.toString());		//SQL���s

			//�����N�G�X�g�f�[�^�Ɏ���CD�ǔԂ𔽉f����B

			//T110 ����i�e�[�u��(tr_shisakuhin)
			for ( int i=0; i<reqData.getCntRow("tr_shisakuhin"); i++ ) {
				reqData.setFieldVale("tr_shisakuhin", i, "no_oi", strRetNewCode);
			}
			//T120 �z���e�[�u��(tr_haigo)
			for ( int i=0; i<reqData.getCntRow("tr_haigo"); i++ ) {
				reqData.setFieldVale("tr_haigo", i, "no_oi", strRetNewCode);
			}
			//T131 ����e�[�u��(tr_shisaku)
			for ( int i=0; i<reqData.getCntRow("tr_shisaku"); i++ ) {
				reqData.setFieldVale("tr_shisaku", i, "no_oi", strRetNewCode);
			}
			//T132 ���샊�X�g�e�[�u��(tr_shisaku_list)
			for ( int i=0; i<reqData.getCntRow("tr_shisaku_list"); i++ ) {
				reqData.setFieldVale("tr_shisaku_list", i, "no_oi", strRetNewCode);
			}
			//T133 �����H���e�[�u��(tr_cyuui)
				for ( int i=0; i<reqData.getCntRow("tr_cyuui"); i++ ) {
					reqData.setFieldVale("tr_cyuui", i, "no_oi", strRetNewCode);
				}
			//T141 ���������e�[�u��(tr_genryo)
			for ( int i=0; i<reqData.getCntRow("tr_genryo"); i++ ) {
				reqData.setFieldVale("tr_genryo", i, "no_oi", strRetNewCode);
			}

		} catch (Exception e) {
			this.em.ThrowException(e, "����R�[�h�̐V�K�̔ԂɎ��s���܂����B");

		} finally {
			//�ϐ��̍폜
			strSQL = null;
			strSQL2 = null;

		}

	}

//2009/09/30 TT.A.ISONO ADD END   [����CD�ǔԂ͓o�^���ɍ̔Ԃ���悤�ύX����B]

	/**
	 * ����i�f�[�^�폜SQL�쐬
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private void shisakuhinKanriDeleteSQL(RequestResponsKindBean reqData)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		StringBuffer strSQL = new StringBuffer();

		try {

			//�@�F���N�G�X�g�f�[�^��p���A����i�f�[�^�폜�pSQL���쐬����B

			//���N�G�X�g�f�[�^�擾
			String strReqShainCd = reqData.getFieldVale("tr_shisakuhin", 0, "cd_shain");
			String strReqNen = reqData.getFieldVale("tr_shisakuhin", 0, "nen");
			String strReqOiNo = reqData.getFieldVale("tr_shisakuhin", 0, "no_oi");

			//�폜�pSQL�쐬
			strSQL.append(" DELETE ");
			strSQL.append("  FROM tr_shisakuhin ");
			strSQL.append("  WHERE ");
			strSQL.append("   cd_shain= " + strReqShainCd);
			strSQL.append("   AND nen= " + strReqNen);
			strSQL.append("   AND no_oi= " + strReqOiNo);
			strSQL.append("  ");

			//�A�F���ʃN���X�@�f�[�^�x�[�X�Ǘ���p���A����i�f�[�^�̍폜���s���B
			this.execDB.execSQL(strSQL.toString());

		} catch (Exception e) {
			this.em.ThrowException(e, "����i�f�[�^�폜SQL�쐬���������s���܂����B");

		} finally {
			//�ϐ��̍폜
			strSQL = null;

		}
	}
	/**
	 * ����i�f�[�^�o�^SQL�쐬
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private void shisakuhinKanriInsertSQL(RequestResponsKindBean reqData)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		StringBuffer strSQL = new StringBuffer();
		StringBuffer strSQL_values = new StringBuffer();

		try {

			//�@�F���N�G�X�g�f�[�^��p���A����i�f�[�^�o�^�pSQL���쐬����B
			for ( int i=0; i<reqData.getCntRow("tr_shisakuhin"); i++ ) {

				if ( strSQL_values.length() != 0 ) {
					strSQL_values.append(" UNION ALL ");

				}
				strSQL_values.append(" SELECT ");

				//�l��SQL�ɐݒ肵�Ă���
				strSQL_values.append(" " + reqData.getFieldVale("tr_shisakuhin", i, "cd_shain") );
				strSQL_values.append(" ," + reqData.getFieldVale("tr_shisakuhin", i, "nen") );
				strSQL_values.append(" ," + reqData.getFieldVale("tr_shisakuhin", i, "no_oi") );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisakuhin", i, "no_irai") + "'") );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisakuhin", i, "nm_hin") + "'") );
				strSQL_values.append(" ," + checkNull(reqData.getFieldVale("tr_shisakuhin", i, "cd_kaisha")) );
				strSQL_values.append(" ," + checkNull(reqData.getFieldVale("tr_shisakuhin", i, "cd_kojo")) );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisakuhin", i, "cd_shubetu") + "'") );
				strSQL_values.append(" ," + checkNull(reqData.getFieldVale("tr_shisakuhin", i, "no_shubetu")) );
				strSQL_values.append(" ," + checkNull(reqData.getFieldVale("tr_shisakuhin", i, "cd_group")) );
				strSQL_values.append(" ," + checkNull(reqData.getFieldVale("tr_shisakuhin", i, "cd_team")) );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisakuhin", i, "cd_ikatu") + "'") );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisakuhin", i, "cd_genre") + "'") );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisakuhin", i, "cd_user") + "'") );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisakuhin", i, "tokuchogenryo") + "'") );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisakuhin", i, "youto") + "'") );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisakuhin", i, "cd_kakaku") + "'") );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisakuhin", i, "cd_eigyo") + "'") );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisakuhin", i, "cd_hoho") + "'") );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisakuhin", i, "cd_juten") + "'") );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisakuhin", i, "hoho_sakin") + "'") );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisakuhin", i, "youki") + "'") );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisakuhin", i, "yoryo") + "'") );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisakuhin", i, "cd_tani") + "'") );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisakuhin", i, "su_iri") + "'") );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisakuhin", i, "cd_ondo") + "'") );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisakuhin", i, "shomikikan") + "'") );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisakuhin", i, "genka") + "'") );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisakuhin", i, "baika") + "'") );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisakuhin", i, "buturyo") + "'") );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisakuhin", i, "dt_hatubai") + "'") );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisakuhin", i, "uriage_k") + "'") );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisakuhin", i, "rieki_k") + "'") );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisakuhin", i, "uriage_h") + "'") );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisakuhin", i, "rieki_h") + "'") );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisakuhin", i, "cd_nisugata") + "'") );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisakuhin", i, "memo") + "'") );
				strSQL_values.append(" ," + checkNull(reqData.getFieldVale("tr_shisakuhin", i, "keta_shosu")) );
				strSQL_values.append(" ," + checkNull(reqData.getFieldVale("tr_shisakuhin", i, "kbn_haishi")) );
//2009/09/30 TT.A.ISONO ADD START [����CD�ǔԂ͓o�^���ɍ̔Ԃ���悤�ύX����B]
//����̔r�����b�N�́A����f�[�^�o�^���Ɋ|����ɕύX
//				strSQL_values.append(" ," + checkNull(reqData.getFieldVale("tr_shisakuhin", i, "kbn_haita")) );
				strSQL_values.append(" ," + checkNull(reqData.getFieldVale("tr_shisakuhin", i, "id_koshin")) );
//2009/09/30 TT.A.ISONO ADD END   [����CD�ǔԂ͓o�^���ɍ̔Ԃ���悤�ύX����B]
				strSQL_values.append(" ," + checkNull(reqData.getFieldVale("tr_shisakuhin", i, "seq_shisaku")) );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisakuhin", i, "memo_shisaku") + "'") );
				strSQL_values.append(" ," + checkNull(reqData.getFieldVale("tr_shisakuhin", i, "flg_chui")) );
				strSQL_values.append(" ," + reqData.getFieldVale("tr_shisakuhin", i, "id_toroku") );
				strSQL_values.append(" ,'" + reqData.getFieldVale("tr_shisakuhin", i, "dt_toroku") + "'" );
				strSQL_values.append(" ," + reqData.getFieldVale("tr_shisakuhin", i, "id_koshin") );
				//UPD 2012.4.30�@�yH24�N�x�Ή��z�X�V���Ŏ���i�̍X�V�r��������s�����ߍX�V
//				strSQL_values.append(" ,'" + reqData.getFieldVale("tr_shisakuhin", i, "dt_koshin") + "'" );
				strSQL_values.append(" ,CONVERT (DATETIME, GETDATE(),20)");
//2011/05/11 QP@10181_No.42_49_72 TT Nishigawa Add Start -------------------------
				strSQL_values.append(" ,'" + toString(reqData.getFieldVale("tr_shisakuhin", i, "pt_kotei")) + "'");
//2011/05/11 QP@10181_No.42_49_72 TT Nishigawa Add End --------------------------
				//�yQP@20505_No.38�z2012/10/17 TT H.SHIMA ADD Start
				strSQL_values.append(" ," + checkNull(reqData.getFieldVale("tr_shisakuhin", i, "flg_secret")) );
				//�yQP@20505_No.38�z2012/10/17 TT H.SHIMA ADD End
				// ADD 2013/10/22 QP@30154 okano start
				strSQL_values.append(" ," + checkNull(reqData.getFieldVale("tr_shisakuhin", i, "cd_hanseki")) );
				// ADD 2013/10/22 QP@30154 okano end
			}

			//�o�^�pSQL�쐬
			strSQL.append(" INSERT INTO tr_shisakuhin ");
			strSQL.append(strSQL_values.toString());
			strSQL.append("");

			//�A�F���ʃN���X�@�f�[�^�x�[�X�Ǘ���p���A����i�f�[�^�̓o�^���s���B
			this.execDB.execSQL(strSQL.toString());

		} catch (Exception e) {
			this.em.ThrowException(e, "����i�f�[�^�o�^SQL�쐬���������s���܂����B");

		} finally {
			//�ϐ��̍폜
			strSQL = null;
			strSQL_values = null;

		}

	}

	/**
	 * �z���f�[�^�폜SQL�쐬
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private void haigoKanriDeleteSQL(RequestResponsKindBean reqData)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		StringBuffer strSQL = new StringBuffer();

		try {

			//�@�F���N�G�X�g�f�[�^��p���A�z���f�[�^�폜�pSQL���쐬����B

			//���N�G�X�g�f�[�^�擾
			String strReqShainCd = reqData.getFieldVale("tr_haigo", 0, "cd_shain");
			String strReqNen = reqData.getFieldVale("tr_haigo", 0, "nen");
			String strReqOiNo = reqData.getFieldVale("tr_haigo", 0, "no_oi");

			//�폜�pSQL�쐬
			strSQL.append(" DELETE ");
			strSQL.append("  FROM tr_haigo ");
			strSQL.append("  WHERE ");
			strSQL.append("   cd_shain= " + strReqShainCd);
			strSQL.append("   AND nen= " + strReqNen);
			strSQL.append("   AND no_oi= " + strReqOiNo);
			strSQL.append("  ");

			//�A�F���ʃN���X�@�f�[�^�x�[�X�Ǘ���p���A�z���f�[�^�̍폜���s���B
			this.execDB.execSQL(strSQL.toString());

		} catch (Exception e) {
			this.em.ThrowException(e, "�z���f�[�^�폜SQL�쐬���������s���܂����B");

		} finally {
			//�ϐ��̍폜
			strSQL = null;

		}
	}
	/**
	 * �z���f�[�^�o�^SQL�쐬
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private void haigoKanriInsertSQL(RequestResponsKindBean reqData)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		StringBuffer strSQL = new StringBuffer();
		StringBuffer strSQL_values = new StringBuffer();

		try {

			//�@�F���N�G�X�g�f�[�^��p���A�z���f�[�^�o�^�pSQL���쐬����B
			for ( int i=0; i<reqData.getCntRow("tr_haigo"); i++ ) {

				if ( strSQL_values.length() != 0 ) {
					strSQL_values.append(" UNION ALL ");

				}
				strSQL_values.append(" SELECT ");

				//�l��SQL�ɐݒ肵�Ă���
				strSQL_values.append(" " + reqData.getFieldVale("tr_haigo", i, "cd_shain") );
				strSQL_values.append(" ," + reqData.getFieldVale("tr_haigo", i, "nen") );
				strSQL_values.append(" ," + reqData.getFieldVale("tr_haigo", i, "no_oi") );
				strSQL_values.append(" ," + reqData.getFieldVale("tr_haigo", i, "cd_kotei") );
				strSQL_values.append(" ," + reqData.getFieldVale("tr_haigo", i, "seq_kotei") );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_haigo", i, "nm_kotei") + "'") );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_haigo", i, "zoku_kotei") + "'") );
				strSQL_values.append(" ," + checkNull(reqData.getFieldVale("tr_haigo", i, "sort_kotei")) );
				strSQL_values.append(" ," + checkNull(reqData.getFieldVale("tr_haigo", i, "sort_genryo")) );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_haigo", i, "cd_genryo") + "'") );
				strSQL_values.append(" ," + checkNull(reqData.getFieldVale("tr_haigo", i, "cd_kaisha")) );
				strSQL_values.append(" ," + checkNull(reqData.getFieldVale("tr_haigo", i, "cd_busho")) );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_haigo", i, "nm_genryo") + "'") );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_haigo", i, "tanka") + "'") );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_haigo", i, "budomari") + "'") );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_haigo", i, "ritu_abura") + "'") );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_haigo", i, "ritu_sakusan") + "'") );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_haigo", i, "ritu_shokuen") + "'") );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_haigo", i, "ritu_sousan") + "'") );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_haigo", i, "color") + "'") );
				strSQL_values.append(" ," + reqData.getFieldVale("tr_haigo", i, "id_toroku") );
				strSQL_values.append(" ,'" + reqData.getFieldVale("tr_haigo", i, "dt_toroku") + "'" );
//				strSQL_values.append(" ," + reqData.getFieldVale("tr_haigo", i, "cd_shain") );
//				strSQL_values.append(" ,GETDATE()" );
				strSQL_values.append(" ," + reqData.getFieldVale("tr_haigo", i, "id_koshin") );
				strSQL_values.append(" ,'" + reqData.getFieldVale("tr_haigo", i, "dt_koshin") + "'" );
// ADD start 20121003 QP@20505 No.24
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_haigo", i, "ritu_msg") + "'") );
// ADD end 20121003 QP@20505 No.24

			}

			//�o�^�pSQL�쐬
			strSQL.append(" INSERT INTO tr_haigo ");
			strSQL.append(strSQL_values.toString());
			strSQL.append("");

			//�A�F���ʃN���X�@�f�[�^�x�[�X�Ǘ���p���A�z���f�[�^�̓o�^���s���B
			this.execDB.execSQL(strSQL.toString());

		} catch (Exception e) {
			this.em.ThrowException(e, "�z���f�[�^�o�^SQL�쐬���������s���܂����B");

		} finally {
			//�ϐ��̍폜
			strSQL = null;
			strSQL_values = null;

		}

	}

	/**
	 * ����f�[�^�폜SQL�쐬
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private void shisakuKanriDeleteSQL(RequestResponsKindBean reqData)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		StringBuffer strSQL = new StringBuffer();

		try {

			//�@�F���N�G�X�g�f�[�^��p���A����f�[�^�폜�pSQL���쐬����B

			//���N�G�X�g�f�[�^�擾
			String strReqShainCd = reqData.getFieldVale("tr_shisaku", 0, "cd_shain");
			String strReqNen = reqData.getFieldVale("tr_shisaku", 0, "nen");
			String strReqOiNo = reqData.getFieldVale("tr_shisaku", 0, "no_oi");

			//�폜�pSQL�쐬
			strSQL.append(" DELETE ");
			strSQL.append("  FROM tr_shisaku ");
			strSQL.append("  WHERE ");
			strSQL.append("   cd_shain= " + strReqShainCd);
			strSQL.append("   AND nen= " + strReqNen);
			strSQL.append("   AND no_oi= " + strReqOiNo);
			strSQL.append("  ");

			//�A�F���ʃN���X�@�f�[�^�x�[�X�Ǘ���p���A����f�[�^�̍폜���s���B
			this.execDB.execSQL(strSQL.toString());

		} catch (Exception e) {
			this.em.ThrowException(e, "����f�[�^�폜SQL�쐬���������s���܂����B");

		} finally {
			//�ϐ��̍폜
			strSQL = null;

		}
	}
	/**
	 * ����f�[�^�o�^SQL�쐬
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private void shisakuKanriInsertSQL(RequestResponsKindBean reqData)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		StringBuffer strSQL = new StringBuffer();
		StringBuffer strSQL_values = new StringBuffer();

		try {

			//�@�F���N�G�X�g�f�[�^��p���A����f�[�^�o�^�pSQL���쐬����B
			for ( int i=0; i<reqData.getCntRow("tr_shisaku"); i++ ) {

				if ( strSQL_values.length() != 0 ) {
					strSQL_values.append(" UNION ALL ");

				}
				strSQL_values.append(" SELECT ");

				//�l��SQL�ɐݒ肵�Ă���
				strSQL_values.append(" " + reqData.getFieldVale("tr_shisaku", i, "cd_shain") );
				strSQL_values.append(" ," + reqData.getFieldVale("tr_shisaku", i, "nen") );
				strSQL_values.append(" ," + reqData.getFieldVale("tr_shisaku", i, "no_oi") );
				strSQL_values.append(" ," + reqData.getFieldVale("tr_shisaku", i, "seq_shisaku") );
				strSQL_values.append(" ," + checkNull(reqData.getFieldVale("tr_shisaku", i, "sort_shisaku")) );
				strSQL_values.append(" ," + checkNull(reqData.getFieldVale("tr_shisaku", i, "no_chui")) );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisaku", i, "nm_sample") + "'") );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisaku", i, "memo") + "'") );
				//strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisaku", i, "memo_shisaku") + "'") );
				strSQL_values.append(" ," + checkNull(reqData.getFieldVale("tr_shisaku", i, "flg_print")) );
				strSQL_values.append(" ," + checkNull(reqData.getFieldVale("tr_shisaku", i, "flg_auto")) );
				strSQL_values.append(" ," + checkNull(reqData.getFieldVale("tr_shisaku", i, "no_shisan")) );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisaku", i, "no_seiho1") + "'") );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisaku", i, "no_seiho2") + "'") );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisaku", i, "no_seiho3") + "'") );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisaku", i, "no_seiho4") + "'") );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisaku", i, "no_seiho5") + "'") );
				strSQL_values.append(" ," + checkNull(reqData.getFieldVale("tr_shisaku", i, "ritu_sousan")) );
				strSQL_values.append(" ," + checkNull(reqData.getFieldVale("tr_shisaku", i, "flg_sousan")) );
				strSQL_values.append(" ," + checkNull(reqData.getFieldVale("tr_shisaku", i, "ritu_shokuen")) );
				strSQL_values.append(" ," + checkNull(reqData.getFieldVale("tr_shisaku", i, "flg_shokuen")) );
				strSQL_values.append(" ," + checkNull(reqData.getFieldVale("tr_shisaku", i, "sando_suiso")) );
				strSQL_values.append(" ," + checkNull(reqData.getFieldVale("tr_shisaku", i, "flg_sando_suiso")) );
				strSQL_values.append(" ," + checkNull(reqData.getFieldVale("tr_shisaku", i, "shokuen_suiso")) );
				strSQL_values.append(" ," + checkNull(reqData.getFieldVale("tr_shisaku", i, "flg_shokuen_suiso")) );
				strSQL_values.append(" ," + checkNull(reqData.getFieldVale("tr_shisaku", i, "sakusan_suiso")) );
				strSQL_values.append(" ," + checkNull(reqData.getFieldVale("tr_shisaku", i, "flg_sakusan_suiso")) );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisaku", i, "toudo") + "'") );
				strSQL_values.append(" ," + checkNull(reqData.getFieldVale("tr_shisaku", i, "flg_toudo")) );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisaku", i, "nendo") + "'") );
				strSQL_values.append(" ," + checkNull(reqData.getFieldVale("tr_shisaku", i, "flg_nendo")) );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisaku", i, "ondo") + "'") );
				strSQL_values.append(" ," + checkNull(reqData.getFieldVale("tr_shisaku", i, "flg_ondo")) );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisaku", i, "ph") + "'") );
				strSQL_values.append(" ," + checkNull(reqData.getFieldVale("tr_shisaku", i, "flg_ph")) );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisaku", i, "ritu_sousan_bunseki") + "'") );
				strSQL_values.append(" ," + checkNull(reqData.getFieldVale("tr_shisaku", i, "flg_sousan_bunseki")) );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisaku", i, "ritu_shokuen_bunseki") + "'") );
				strSQL_values.append(" ," + checkNull(reqData.getFieldVale("tr_shisaku", i, "flg_shokuen_bunseki")) );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisaku", i, "hiju") + "'") );
				strSQL_values.append(" ," + checkNull(reqData.getFieldVale("tr_shisaku", i, "flg_hiju")) );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisaku", i, "suibun_kasei") + "'") );
				strSQL_values.append(" ," + checkNull(reqData.getFieldVale("tr_shisaku", i, "flg_suibun_kasei")) );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisaku", i, "alcohol") + "'") );
				strSQL_values.append(" ," + checkNull(reqData.getFieldVale("tr_shisaku", i, "flg_alcohol")) );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisaku", i, "memo_sakusei") + "'") );
				strSQL_values.append(" ," + checkNull(reqData.getFieldVale("tr_shisaku", i, "flg_memo")) );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisaku", i, "hyoka") + "'") );
				strSQL_values.append(" ," + checkNull(reqData.getFieldVale("tr_shisaku", i, "flg_hyoka")) );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisaku", i, "free_title1") + "'") );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisaku", i, "free_value1") + "'") );
				strSQL_values.append(" ," + checkNull(reqData.getFieldVale("tr_shisaku", i, "flg_free1")) );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisaku", i, "free_title2") + "'") );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisaku", i, "free_value2") + "'") );
				strSQL_values.append(" ," + checkNull(reqData.getFieldVale("tr_shisaku", i, "flg_free2")) );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisaku", i, "free_title3") + "'") );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisaku", i, "free_value3") + "'") );
				strSQL_values.append(" ," + checkNull(reqData.getFieldVale("tr_shisaku", i, "flg_free3")) );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisaku", i, "dt_shisaku") + "'") );
				strSQL_values.append(" ," + checkNull(reqData.getFieldVale("tr_shisaku", i, "juryo_shiagari_g")));
				strSQL_values.append(" ," + reqData.getFieldVale("tr_shisaku", i, "id_toroku") );
				strSQL_values.append(" ,'" + reqData.getFieldVale("tr_shisaku", i, "dt_toroku") + "'" );
//				strSQL_values.append(" ," + reqData.getFieldVale("tr_shisaku", i, "cd_shain") );
//				strSQL_values.append(" ,GETDATE()" );
				strSQL_values.append(" ," + reqData.getFieldVale("tr_shisaku", i, "id_koshin") );
				strSQL_values.append(" ,'" + reqData.getFieldVale("tr_shisaku", i, "dt_koshin") + "'" );
//2009/10/20 TT.A.ISONO ADD START [�������Z�F���Z�˗���������ǉ�]
				try{
					strSQL_values.append(" ,'" + reqData.getFieldVale("tr_shisaku", i, "flg_shisanIrai") + "'" );
				}catch(Exception e){

				}
//2009/10/20 TT.A.ISONO ADD END   [�������Z�F���Z�˗���������ǉ�]
//add start -------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.7
				strSQL_values.append(" ,'" + toString(reqData.getFieldVale("tr_shisaku", i, "siki_keisan"),"") + "'");
//add end   -------------------------------------------------------------------------------
//2011/05/11 QP@10181_No.42_49_72 TT Nishigawa Add Start -------------------------
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisaku", i, "hiju_sui") + "'") );
				strSQL_values.append(" ," + checkNull(reqData.getFieldVale("tr_shisaku", i, "flg_hiju_sui")) );
//2011/05/11 QP@10181_No.42_49_72 TT Nishigawa Add End --------------------------
// ADD start 20121003 QP@20505 No.24
				strSQL_values.append(" ," + checkNull(reqData.getFieldVale("tr_shisaku", i, "ritu_msg")) );
				strSQL_values.append(" ,0"); // flg_msg�i�����_�ł͉�ʕ\�����Ă��Ȃ����߁j
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisaku", i, "freetitle_nendo") + "'") );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisaku", i, "free_nendo") + "'") );
				strSQL_values.append(" ," + checkNull(reqData.getFieldVale("tr_shisaku", i, "flg_freeNendo")) );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisaku", i, "freetitle_ondo") + "'") );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisaku", i, "free_ondo") + "'") );
				strSQL_values.append(" ," + checkNull(reqData.getFieldVale("tr_shisaku", i, "flg_freeOndo")) );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisaku", i, "freetitle_suibun_kasei") + "'") );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisaku", i, "free_suibun_kasei") + "'") );
				strSQL_values.append(" ," + checkNull(reqData.getFieldVale("tr_shisaku", i, "flg_freeSuibunKasei")) );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisaku", i, "freetitle_alcohol") + "'") );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisaku", i, "free_alcohol") + "'") );
				strSQL_values.append(" ," + checkNull(reqData.getFieldVale("tr_shisaku", i, "flg_freeAlchol")) );
				strSQL_values.append(" ," + checkNull(reqData.getFieldVale("tr_shisaku", i, "jikkoSakusanNodo")) );
				strSQL_values.append(" ," + checkNull(reqData.getFieldVale("tr_shisaku", i, "flg_jikkoSakusanNodo")) );
				strSQL_values.append(" ," + checkNull(reqData.getFieldVale("tr_shisaku", i, "msg_suiso")) );
				strSQL_values.append(" ," + checkNull(reqData.getFieldVale("tr_shisaku", i, "flg_msg_suiso")) );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisaku", i, "free_title4") + "'") );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisaku", i, "free_value4") + "'") );
				strSQL_values.append(" ," + checkNull(reqData.getFieldVale("tr_shisaku", i, "flg_free4")) );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisaku", i, "free_title5") + "'") );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisaku", i, "free_value5") + "'") );
				strSQL_values.append(" ," + checkNull(reqData.getFieldVale("tr_shisaku", i, "flg_free5")) );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisaku", i, "free_title6") + "'") );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisaku", i, "free_value6") + "'") );
				strSQL_values.append(" ," + checkNull(reqData.getFieldVale("tr_shisaku", i, "flg_free6")) );
// ADD end 20121003 QP@20505 No.24
			}

			//�o�^�pSQL�쐬
			strSQL.append(" INSERT INTO tr_shisaku ");
			strSQL.append(strSQL_values.toString());
			strSQL.append("");

			//�A�F���ʃN���X�@�f�[�^�x�[�X�Ǘ���p���A����f�[�^�̓o�^���s���B
			this.execDB.execSQL(strSQL.toString());

		} catch (Exception e) {
			this.em.ThrowException(e, "����f�[�^�o�^SQL�쐬���������s���܂����B");

		} finally {
			//�ϐ��̍폜
			strSQL = null;
			strSQL_values = null;

		}

	}

	/**
	 * ���샊�X�g�f�[�^�폜SQL�쐬
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private void shisakuListKanriDeleteSQL(RequestResponsKindBean reqData)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		StringBuffer strSQL = new StringBuffer();

		try {

			//�@�F���N�G�X�g�f�[�^��p���A���샊�X�g�f�[�^�폜�pSQL���쐬����B

			//���N�G�X�g�f�[�^�擾
			String strReqShainCd = reqData.getFieldVale("tr_shisaku_list", 0, "cd_shain");
			String strReqNen = reqData.getFieldVale("tr_shisaku_list", 0, "nen");
			String strReqOiNo = reqData.getFieldVale("tr_shisaku_list", 0, "no_oi");

			//�폜�pSQL�쐬
			strSQL.append(" DELETE ");
			strSQL.append("  FROM tr_shisaku_list ");
			strSQL.append("  WHERE ");
			strSQL.append("   cd_shain= " + strReqShainCd);
			strSQL.append("   AND nen= " + strReqNen);
			strSQL.append("   AND no_oi= " + strReqOiNo);
			strSQL.append("  ");

			//�A�F���ʃN���X�@�f�[�^�x�[�X�Ǘ���p���A���샊�X�g�f�[�^�̍폜���s���B
			this.execDB.execSQL(strSQL.toString());

		} catch (Exception e) {
			this.em.ThrowException(e, "���샊�X�g�f�[�^�폜SQL�쐬���������s���܂����B");

		} finally {
			//�ϐ��̍폜
			strSQL = null;

		}

	}
	/**
	 * ���샊�X�g�f�[�^�o�^SQL�쐬
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private void shisakuListKanriInsertSQL(RequestResponsKindBean reqData)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		StringBuffer strSQL = new StringBuffer();
		StringBuffer strSQL_values = new StringBuffer();

		try {

			//�@�F���N�G�X�g�f�[�^��p���A���샊�X�g�f�[�^�o�^�pSQL���쐬����B
			for ( int i=0; i<reqData.getCntRow("tr_shisaku_list"); i++ ) {
				if ( strSQL_values.length() != 0 ) {
					strSQL_values.append(" UNION ALL ");
				}
				strSQL_values.append(" SELECT ");

				//�l��SQL�ɐݒ肵�Ă���
				strSQL_values.append(" " + reqData.getFieldVale("tr_shisaku_list", i, "cd_shain") );
				strSQL_values.append(" ," + reqData.getFieldVale("tr_shisaku_list", i, "nen") );
				strSQL_values.append(" ," + reqData.getFieldVale("tr_shisaku_list", i, "no_oi") );
				strSQL_values.append(" ," + reqData.getFieldVale("tr_shisaku_list", i, "seq_shisaku") );
				strSQL_values.append(" ," + reqData.getFieldVale("tr_shisaku_list", i, "cd_kotei") );
				strSQL_values.append(" ," + reqData.getFieldVale("tr_shisaku_list", i, "seq_kotei") );
				strSQL_values.append(" ," + checkNull(reqData.getFieldVale("tr_shisaku_list", i, "quantity")) );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisaku_list", i, "color") + "'") );
				strSQL_values.append(" ," + reqData.getFieldVale("tr_shisaku_list", i, "id_toroku") );
				strSQL_values.append(" ,'" + reqData.getFieldVale("tr_shisaku_list", i, "dt_toroku") + "'" );
//				strSQL_values.append(" ," + reqData.getFieldVale("tr_shisaku_list", i, "cd_shain") );
//				strSQL_values.append(" ,GETDATE()" );
				strSQL_values.append(" ," + reqData.getFieldVale("tr_shisaku_list", i, "id_koshin") );
				strSQL_values.append(" ,'" + reqData.getFieldVale("tr_shisaku_list", i, "dt_koshin") + "'" );
// ADD start 20120914 QP@20505 No.1
				strSQL_values.append(" ," + checkNull(reqData.getFieldVale("tr_shisaku_list", i, "juryo_shiagari_seq")) );
// ADD end 20120914 QP@20505 No.1

			}

			//�o�^�pSQL�쐬
			strSQL.append(" INSERT INTO tr_shisaku_list ");
			strSQL.append(strSQL_values.toString());
			strSQL.append("");

			//�A�F���ʃN���X�@�f�[�^�x�[�X�Ǘ���p���A���샊�X�g�f�[�^�̓o�^���s���B
			this.execDB.execSQL(strSQL.toString());

		} catch (Exception e) {
			this.em.ThrowException(e, "���샊�X�g�f�[�^�o�^SQL�쐬���������s���܂����B");

		} finally {
			//�ϐ��̍폜
			strSQL = null;
			strSQL_values = null;

		}

	}

	/**
	 * �����H���f�[�^�폜SQL�쐬
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private void seizoKouteiKanriDeleteSQL(RequestResponsKindBean reqData)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		StringBuffer strSQL = new StringBuffer();

		try {

			//�����H���̃e�[�u��������0���ł͂Ȃ��ꍇ�A�����𑱍s����
			if ( reqData.getCntRow("tr_cyuui") != 0 ) {

				//�@�F���N�G�X�g�f�[�^��p���A�����H���f�[�^�폜�pSQL���쐬����B

				//���N�G�X�g�f�[�^�擾
				String strReqShainCd = reqData.getFieldVale("tr_cyuui", 0, "cd_shain");
				String strReqNen = reqData.getFieldVale("tr_cyuui", 0, "nen");
				String strReqOiNo = reqData.getFieldVale("tr_cyuui", 0, "no_oi");

				//�폜�pSQL�쐬
				strSQL.append(" DELETE ");
				strSQL.append("  FROM tr_cyuui ");
				strSQL.append("  WHERE ");
				strSQL.append("   cd_shain= " + strReqShainCd);
				strSQL.append("   AND nen= " + strReqNen);
				strSQL.append("   AND no_oi= " + strReqOiNo);
				strSQL.append("  ");

				//�A�F���ʃN���X�@�f�[�^�x�[�X�Ǘ���p���A�����H���f�[�^�̍폜���s���B
				this.execDB.execSQL(strSQL.toString());

			}

		} catch (Exception e) {
			this.em.ThrowException(e, "�����H���f�[�^�폜SQL�쐬���������s���܂����B");

		} finally {
			//�ϐ��̍폜
			strSQL = null;

		}

	}
	/**
	 * �����H���f�[�^�o�^SQL�쐬
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private void seizoKouteiKanriInsertSQL(RequestResponsKindBean reqData)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		StringBuffer strSQL = new StringBuffer();
		StringBuffer strSQL_values = new StringBuffer();

		try {

			//�����H���̃e�[�u��������0���ł͂Ȃ��ꍇ�A�����𑱍s����
			if ( reqData.getCntRow("tr_cyuui") != 0 ) {

				//�@�F���N�G�X�g�f�[�^��p���A�����H���f�[�^�o�^�pSQL���쐬����B
				for ( int i=0; i<reqData.getCntRow("tr_cyuui"); i++ ) {
					if ( strSQL_values.length() != 0 ) {
						strSQL_values.append(" UNION ALL ");
					}
					strSQL_values.append(" SELECT ");

					//�l��SQL�ɐݒ肵�Ă���
					strSQL_values.append(" " + reqData.getFieldVale("tr_cyuui", i, "cd_shain") );
					strSQL_values.append(" ," + reqData.getFieldVale("tr_cyuui", i, "nen") );
					strSQL_values.append(" ," + reqData.getFieldVale("tr_cyuui", i, "no_oi") );
					//strSQL_values.append(" ," + reqData.getFieldVale("tr_cyuui", i, "seq_shisaku") );
					strSQL_values.append(" ," + reqData.getFieldVale("tr_cyuui", i, "no_chui") );
					strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_cyuui", i, "chuijiko") + "'") );
					strSQL_values.append(" ," + reqData.getFieldVale("tr_cyuui", i, "id_toroku") );
					strSQL_values.append(" ,'" + reqData.getFieldVale("tr_cyuui", i, "dt_toroku") + "'" );
//					strSQL_values.append(" ," + reqData.getFieldVale("tr_cyuui", i, "cd_shain") );
//					strSQL_values.append(" ,GETDATE()" );
					strSQL_values.append(" ," + reqData.getFieldVale("tr_cyuui", i, "id_koshin") );
					strSQL_values.append(" ,'" + reqData.getFieldVale("tr_cyuui", i, "dt_koshin") + "'" );

				}

				//�o�^�pSQL�쐬
				strSQL.append(" INSERT INTO tr_cyuui ");
				strSQL.append(strSQL_values.toString());
				strSQL.append("");

				//�A�F���ʃN���X�@�f�[�^�x�[�X�Ǘ���p���A�����H���f�[�^�̓o�^���s���B
				this.execDB.execSQL(strSQL.toString());

			}

		} catch (Exception e) {
			this.em.ThrowException(e, "�����H���f�[�^�o�^SQL�쐬���������s���܂����B");

		} finally {
			//�ϐ��̍폜
			strSQL = null;
			strSQL_values = null;

		}

	}

//	/**
//	 * �������ރf�[�^�폜SQL�쐬
//	 * @throws ExceptionSystem
//	 * @throws ExceptionUser
//	 * @throws ExceptionWaning
//	 */
//	private void genkaShizaiKanriDeleteSQL() throws ExceptionSystem, ExceptionUser, ExceptionWaning {
//		try {
//			StringBuffer strSQL = new StringBuffer();
//
//			//�@�F���N�G�X�g�f�[�^��p���A�������ރf�[�^�폜�pSQL���쐬����B
//			String strReqShoriKbn = reqData.getFieldVale(0, 0, "kbn_shori");
//
//			//�A�F���ʃN���X�@�f�[�^�x�[�X�Ǘ���p���A�������ރf�[�^�̍폜���s���B
//			this.execDB.execSQL(strSQL.toString());
//
//		} catch (Exception e) {
//			this.em.ThrowException(e, "�������ރf�[�^�폜SQL�쐬���������s���܂����B");
//		} finally {
//		}
//	}
//	/**
//	 * �������ރf�[�^�o�^SQL�쐬
//	 * @throws ExceptionSystem
//	 * @throws ExceptionUser
//	 * @throws ExceptionWaning
//	 */
//	private void genkaShizaiKanriInsertSQL() throws ExceptionSystem, ExceptionUser, ExceptionWaning {
//		try {
//			StringBuffer strSQL = new StringBuffer();
//
//			//�@�F���N�G�X�g�f�[�^��p���A�������ރf�[�^�o�^�pSQL���쐬����B
//			String strReqShoriKbn = reqData.getFieldVale(0, 0, "kbn_shori");
//
//			//�A�F���ʃN���X�@�f�[�^�x�[�X�Ǘ���p���A�������ރf�[�^�̓o�^���s���B
//			this.execDB.execSQL(strSQL.toString());
//
//		} catch (Exception e) {
//			this.em.ThrowException(e, "�������ރf�[�^�o�^SQL�쐬���������s���܂����B");
//		} finally {
//		}
//	}

	/**
	 * ���������f�[�^�폜SQL�쐬
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private void genkaGenryoKanriDeleteSQL(RequestResponsKindBean reqData)
		throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		try {
			StringBuffer strSQL = new StringBuffer();

			//�@�F���N�G�X�g�f�[�^��p���A���������f�[�^�폜�pSQL���쐬����B

			//���N�G�X�g�f�[�^�擾
			String strReqShainCd = reqData.getFieldVale("tr_genryo", 0, "cd_shain");
			String strReqNen = reqData.getFieldVale("tr_genryo", 0, "nen");
			String strReqOiNo = reqData.getFieldVale("tr_genryo", 0, "no_oi");

			//�폜�pSQL�쐬
			strSQL.append(" DELETE ");
			strSQL.append("  FROM tr_genryo ");
			strSQL.append("  WHERE ");
			strSQL.append("   cd_shain= " + strReqShainCd);
			strSQL.append("   AND nen= " + strReqNen);
			strSQL.append("   AND no_oi= " + strReqOiNo);
			strSQL.append("  ");

			//�A�F���ʃN���X�@�f�[�^�x�[�X�Ǘ���p���A���������f�[�^�̍폜���s���B
			this.execDB.execSQL(strSQL.toString());

		} catch (Exception e) {
			this.em.ThrowException(e, "���������f�[�^�폜SQL�쐬���������s���܂����B");

		} finally {

		}

	}
	/**
	 * ���������f�[�^�o�^SQL�쐬
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private void genkaGenryoKanriInsertSQL(RequestResponsKindBean reqData)
		throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		StringBuffer strSQL = new StringBuffer();
		StringBuffer strSQL_values = new StringBuffer();

		try {

			//�@�F���N�G�X�g�f�[�^��p���A���샊�X�g�f�[�^�o�^�pSQL���쐬����B
			for ( int i=0; i<reqData.getCntRow("tr_genryo"); i++ ) {

				if ( strSQL_values.length() != 0 ) {
					strSQL_values.append(" UNION ALL ");

				}

				strSQL_values.append(" SELECT ");

				//�l��SQL�ɐݒ肵�Ă���
				strSQL_values.append(" " + reqData.getFieldVale("tr_genryo", i, "cd_shain") );
				strSQL_values.append(" ," + reqData.getFieldVale("tr_genryo", i, "nen") );
				strSQL_values.append(" ," + reqData.getFieldVale("tr_genryo", i, "no_oi") );
				strSQL_values.append(" ," + reqData.getFieldVale("tr_genryo", i, "seq_shisaku") );
				//���Fg
				if ( !toString(reqData.getFieldVale("tr_genryo", i, "flg_print")).isEmpty() ) {
					strSQL_values.append(" ," +  reqData.getFieldVale("tr_genryo", i, "flg_print"));

				} else {
					strSQL_values.append(" ,0 ");

				}
				strSQL_values.append(" ,'" + reqData.getFieldVale("tr_genryo", i, "zyusui") + "'" );
				strSQL_values.append(" ,'" + reqData.getFieldVale("tr_genryo", i, "zyuabura") + "'" );
				strSQL_values.append(" ,'" + reqData.getFieldVale("tr_genryo", i, "gokei") + "'" );
				strSQL_values.append(" ,'" + reqData.getFieldVale("tr_genryo", i, "genryohi") + "'" );
				strSQL_values.append(" ,'" + reqData.getFieldVale("tr_genryo", i, "genryohi1") + "'" );
				strSQL_values.append(" ,'" + reqData.getFieldVale("tr_genryo", i, "hiju") + "'" );
				strSQL_values.append(" ,'" + reqData.getFieldVale("tr_genryo", i, "yoryo") + "'" );
				strSQL_values.append(" ,'" + reqData.getFieldVale("tr_genryo", i, "irisu") + "'" );
				strSQL_values.append(" ,'" + reqData.getFieldVale("tr_genryo", i, "yukobudomari") + "'" );
				strSQL_values.append(" ,'" + reqData.getFieldVale("tr_genryo", i, "reberu") + "'" );
				strSQL_values.append(" ,'" + reqData.getFieldVale("tr_genryo", i, "hizyubudomari") + "'" );
				strSQL_values.append(" ,'" + reqData.getFieldVale("tr_genryo", i, "heikinzyu") + "'" );
				strSQL_values.append(" ,'" + reqData.getFieldVale("tr_genryo", i, "cs_genryo") + "'" );
				strSQL_values.append(" ,'" + reqData.getFieldVale("tr_genryo", i, "cs_zairyohi") + "'" );
				strSQL_values.append(" ,'" + reqData.getFieldVale("tr_genryo", i, "cs_keihi") + "'" );
				strSQL_values.append(" ,'" + reqData.getFieldVale("tr_genryo", i, "cs_genka") + "'" );
				strSQL_values.append(" ,'" + reqData.getFieldVale("tr_genryo", i, "ko_genka") + "'" );
				strSQL_values.append(" ,'" + reqData.getFieldVale("tr_genryo", i, "ko_baika") + "'" );
				strSQL_values.append(" ,'" + reqData.getFieldVale("tr_genryo", i, "ko_riritu") + "'" );
				strSQL_values.append(" ," + reqData.getFieldVale("tr_genryo", i, "id_toroku") );
				strSQL_values.append(" ,'" + reqData.getFieldVale("tr_genryo", i, "dt_toroku") + "'" );
//				strSQL_values.append(" ," + reqData.getFieldVale("tr_genryo", i, "cd_shain") );
//				strSQL_values.append(" ,GETDATE()" );
				strSQL_values.append(" ," + reqData.getFieldVale("tr_genryo", i, "id_koshin") );
				strSQL_values.append(" ,'" + reqData.getFieldVale("tr_genryo", i, "dt_koshin") + "'" );

			}

			//�o�^�pSQL�쐬
			strSQL.append(" INSERT INTO tr_genryo ");
			strSQL.append(strSQL_values.toString());
			strSQL.append("");

			//�A�F���ʃN���X�@�f�[�^�x�[�X�Ǘ���p���A���샊�X�g�f�[�^�̓o�^���s���B
			this.execDB.execSQL(strSQL.toString());

		} catch (Exception e) {
			this.em.ThrowException(e, "���������f�[�^�o�^SQL�쐬���������s���܂����B");

		} finally {
			//�ϐ��̍폜
			strSQL = null;
			strSQL_values = null;

		}

	}

	/**
	 * �Ǘ����ʃp�����[�^�[�i�[
	 *  : ����e�[�u���f�[�^�̊Ǘ����ʏ������X�|���X�f�[�^�֊i�[����B
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private void storageKengenDataKanri(RequestResponsTableBean resTable)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		try {
			//�@�F���X�|���X�f�[�^���`������B

			//�������ʂ̊i�[
			resTable.addFieldVale(0, "flg_return", "true");
			resTable.addFieldVale(0, "msg_error", "");
			resTable.addFieldVale(0, "no_errmsg", "");
			resTable.addFieldVale(0, "nm_class", "");
			resTable.addFieldVale(0, "cd_error", "");
			resTable.addFieldVale(0, "msg_system", "");

		} catch (Exception e) {
			this.em.ThrowException(e, "�Ǘ����ʃp�����[�^�[�i�[���������s���܂����B");

		} finally {

		}

	}

	/**
	 * Null�`�F�b�N
	 * @param strValue �F �`�F�b�N�l
	 * @return ����(�l����̏ꍇ�ANULL��Ԃ�)
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private String checkNull(String strChkValue) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		String strRetValue = strChkValue;

		try {
			if ( strRetValue.equals("") ) {
				strRetValue = "NULL";
			} else if ( strRetValue.equals("''")) {
				strRetValue = "NULL";
			}
		} catch(Exception e) {
			this.em.ThrowException(e, "Null�`�F�b�N���������s���܂����B");
		} finally {
		}

		return strRetValue;
	}

	//ADD 2012/08/02
	/**
	 * �w��e�[�u�����w��L�[�̎w��f�[�^�ŃT�[�`���Aindex�����^�[������B
	 * @param checkData : ���N�G�X�g�f�[�^
	 * @param strTableNm_sub : �e�[�u����
	 * @param strSearchKey : �T�[�`����...
	 * @param strSearchData : �T�[�`�f�[�^(null�ȊO)...
	 * @return �e�[�u����index
	 * @throws ExceptionSystem
	 * @throws ExceptionUser	������Ȃ��ꍇ�����B
	 * @throws ExceptionWaning
	 */
	private int getTableIndex(RequestResponsKindBean checkData,String strTableNm_sub,String... strKeyData)
		throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		try {

	    	//�f�[�^�T�[�`
			int intParaCounter = 0;
			for ( int j=0; j<checkData.getCntRow( strTableNm_sub); j++ ) {

				for ( intParaCounter=0; intParaCounter<strKeyData.length; intParaCounter=intParaCounter+2){
					if( strKeyData[intParaCounter+1] == null){
						break;
					}else if ( !strKeyData[intParaCounter+1].equals(checkData.getFieldVale(strTableNm_sub, j, strKeyData[intParaCounter]))){
						break;
					}
				}
				if( intParaCounter >= strKeyData.length){
					return j;
				}

			}
			//�C���^�[�t�F�[�X�㌩����Ȃ����Ƃ͂Ȃ��͂�
			em.ThrowException(
	    			ExceptionKind.���Exception,
	    			"E000224",
	    			strTableNm_sub,StringUtils.arrayToCommaDelimitedString(strKeyData),"");

		} catch (Exception e) {
			em.ThrowException(e, "");

		}
		return 0;
	}

}
